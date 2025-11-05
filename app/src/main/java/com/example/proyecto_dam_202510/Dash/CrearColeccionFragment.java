package com.example.proyecto_dam_202510.Dash;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyecto_dam_202510.Funciones;
import com.example.proyecto_dam_202510.R;
import com.example.proyecto_dam_202510.databinding.FragmentNuevaColeccionBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrearColeccionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearColeccionFragment extends Fragment {

   private FragmentNuevaColeccionBinding binding;
   private FirebaseAuth mAuth;
   private FirebaseFirestore db;
   private FirebaseUser user;


    public CrearColeccionFragment() {

    }

    public static CrearColeccionFragment newInstance(String param1, String param2) {
        CrearColeccionFragment fragment = new CrearColeccionFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNuevaColeccionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnCrearColeccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = binding.etNombreColeccion.getText().toString();
                String anno = binding.etAO.getText().toString();
                String num = binding.etNumeroCromos.getText().toString();
                float costeInput = binding.sliderCosteSobre.getValues().get(0);
                int cartas = Math.round(binding.sliderCartasSobre.getValues().get(0));
                String coste = String.valueOf(costeInput);

                String userId = user.getUid();
                //Log.d("crear","Nombre: "+nombre+" Anno: "+anno+" Num: "+num+" Coste: "+coste+" Cartas: "+cartas);

                Funciones.crearColeccion(nombre,nombre,Integer.parseInt(num),cartas,null,userId);
                limpiarFormulario();
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.nav_userColecciones);

            }


        });
    }
    private void limpiarFormulario() {
        binding.etNombreColeccion.setText("");
        binding.etAO.setText("");
        binding.etNumeroCromos.setText("");
        binding.sliderCosteSobre.setValueFrom(0);
        binding.sliderCartasSobre.setValueFrom(0);

    }
}