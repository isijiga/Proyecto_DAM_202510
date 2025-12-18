package com.example.proyecto_dam_202510.Dash.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.proyecto_dam_202510.Funciones;
import com.example.proyecto_dam_202510.R;
import com.example.proyecto_dam_202510.databinding.FragmentAnadirCromoBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import java.util.Date;

/**
 * Fragmento que muestra el alta de Carta en una colección. Tiene la particularidad
 * de poder tomar una foto de la carta y guardarla en la base de datos. Esta carta estara accesible a todos
 * los usuarios de la app.
 * Para poder realizar la foto, Android solicita permisos de la camara. Es necesario tener los
 * permisos concedidos para poder acceder al servicio.
 */
public class AnadirCromoFragment extends Fragment {

    /**
     * Interfaz que maneja el resultado de la subida de la foto.
     */
    private interface OnUploadCallback {
        void onSuccess(String imageUrl);
        void onFailure(Exception e);
    }

    /**
     * Launcher para capturar la imagen.
     */
    private ActivityResultLauncher<Intent> cameraLauncher;
    /**
     * Launcher para solicitar permisos de la camara.
     */
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private FragmentAnadirCromoBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser user;
    private FirebaseStorage storage;
    private String coleccionId;
    private Bitmap imageBitmap;

    public AnadirCromoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

/**
 * Launcher para capturar la imagen. Para poder tomar la foto es necesario tener los permisos concedidos.
 */
        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                o -> {
                    if (o.getResultCode() == Activity.RESULT_OK && o.getData() != null) {
                        Bundle extras = o.getData().getExtras();
                        imageBitmap = (Bitmap) extras.get("data");
                        binding.ibFotoPreview.setImageBitmap(imageBitmap);
                        Toast.makeText(requireContext(), "Foto capturada", Toast.LENGTH_SHORT).show();
                    }
                });

        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        cameraLauncher.launch(intent);
                    } else {
                        Toast.makeText(requireContext(), "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAnadirCromoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        coleccionId = getArguments().getString("coleccion");

        binding.btnAnadir.setOnClickListener(v -> {
            String nombre = binding.etNombre.getText().toString();
            String numero = binding.etNumeroCarta.getText().toString();
            binding.btnAnadir.setEnabled(false);

            if (imageBitmap != null) {
                subirfoto(imageBitmap, new OnUploadCallback() {
                    @Override
                    public void onSuccess(String imageUrl) {
                        Log.d("AnadirCromo", "Foto subida, URL: " + imageUrl);
                        Funciones.agregarCromo(coleccionId, nombre, nombre, numero + "", null, 0, imageUrl);
                        Funciones.agregarCromoPosesion(coleccionId, nombre, nombre, numero + "", null, 0, imageUrl, new Date());
                        Toast.makeText(requireContext(), "Cromo Añadida a la coleccion!", Toast.LENGTH_LONG).show();
                        NavController navController = Navigation.findNavController(requireView());
                        navController.popBackStack();
                        navController.navigate(R.id.nav_userColecciones);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.e("AnadirCromo", "Error al subir foto", e);
                        Toast.makeText(requireContext(), "Error al subir: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        binding.btnAnadir.setEnabled(true);
                    }
                });

            } else {
                Log.d("AnadirCromo", "No se tomó foto, guardando sin imagen.");
                Funciones.agregarCromo(coleccionId, nombre, nombre, numero, null, 0, null);
                Funciones.agregarCromoPosesion(coleccionId, nombre, nombre, numero, null, 0, null, new Date());
                NavController navController = Navigation.findNavController(requireView());
                navController.popBackStack();
                navController.navigate(R.id.nav_userColecciones);
            }
        });

        binding.btnFoto.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraLauncher.launch(intent);
            } else {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA);
            }
        });
        binding.btnAtrasManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController navController = Navigation.findNavController(v);
                navController.popBackStack();
            }
        });
    }

    /**
     * Este metodo sube la foto a Firebase Storage y devuelve la URL de la imagen. Ya que Firebase
     * trabaja de manera asincrona, es necesario utilizar el callback onSuccess, en caso contrario llama al callback onFailure. El callback
     * se encarga de notificar al fragmento que se ha completado la tarea.
     * @param imageBitmap imagen tomada de la camara del usuario. Se reducirá a un 50% de su tamaño original.
     * @param callback interfaz que maneja el resultado de la subida de la foto.
     */

    private void subirfoto(Bitmap imageBitmap, OnUploadCallback callback) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos); // 80% calidad
        byte[] data = baos.toByteArray();

        String fileName = "cromo_" + System.currentTimeMillis() + ".jpg";
        StorageReference cromoRef = storage.getReference().child("cromos/" + user.getUid() + "/" + fileName);


        UploadTask uploadTask = cromoRef.putBytes(data);

        uploadTask.addOnSuccessListener(taskSnapshot -> {
            cromoRef.getDownloadUrl().addOnSuccessListener(downloadUri -> {

                String imageUrl = downloadUri.toString();
                callback.onSuccess(imageUrl);

            }).addOnFailureListener(e -> {
                callback.onFailure(e);
            });
        }).addOnFailureListener(e -> {
            callback.onFailure(e);
        });
    }
}