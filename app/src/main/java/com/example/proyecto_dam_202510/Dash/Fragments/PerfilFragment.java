package com.example.proyecto_dam_202510.Dash.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.proyecto_dam_202510.MainActivity;
import com.example.proyecto_dam_202510.R;
import com.example.proyecto_dam_202510.databinding.FragmentPerfilBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * Clase-Fragmento que visaliza la seccion de Perfil de usuario desde donde se puede resetear la contraseña
 * y Cerrar Sesion
 */
public class PerfilFragment extends Fragment {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FragmentPerfilBinding binding;
    public PerfilFragment() {

    }


    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tvUserEmail.setText(user.getEmail());
/**
 * Boton para cerrar sesion. Se crea un AlertDialog para confirmar la accion.
 */
        binding.btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Atención: Cerrar Sesión");
                builder.setMessage("¿Estas seguro?");
                builder.setPositiveButton("Si", (dialog, which) -> {
                    mAuth.signOut();
                    Intent inten = new Intent(requireContext(), MainActivity.class);
                    inten.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(inten);
                    //finish();
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });
/**
 * Boton para resetear la contraseña. Se crea un AlertDialog para confirmar la accion.
 */
        binding.btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Atención: Va a solicitar un nueva contraseña");
                builder.setMessage("¿Estas seguro?");
                builder.setPositiveButton("Si", (dialog, which) -> {
                    mAuth.sendPasswordResetEmail(user.getEmail())
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(requireContext(), "Se ha enviado un correo para restablecer la contraseña", Toast.LENGTH_LONG).show();
                                }
                                ;
                            });

                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}