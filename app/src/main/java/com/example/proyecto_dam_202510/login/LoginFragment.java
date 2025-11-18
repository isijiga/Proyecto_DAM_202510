package com.example.proyecto_dam_202510.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.proyecto_dam_202510.Auth;
import com.example.proyecto_dam_202510.Dash.DashboardActivity;
import com.example.proyecto_dam_202510.MainActivity;
import com.example.proyecto_dam_202510.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;


/**
 * Fragmento que recibe los datos del view  y los procesa mediante el metodo signin de la clase Auth.
 */
public class LoginFragment extends Fragment implements Auth.AuthListener {
    private Auth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseAuth = new Auth(FirebaseAuth.getInstance());
        TextView tvRegistro = view.findViewById(R.id.tv_registrar);
        TextView tvRecordar = view.findViewById(R.id.tv_recordar_contrasena);
        TextInputEditText mail = view.findViewById(R.id.et_usuario);
        TextInputEditText pass = view.findViewById(R.id.et_contrasena);
        Button btnAceptar = view.findViewById(R.id.btn_aceptar);

        MainActivity ma = (MainActivity) requireActivity();

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.signin(mail.getText().toString(), pass.getText().toString(), LoginFragment.this, requireContext());


            }
        });

        tvRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ma.registroUsuario();
            }
        });

        tvRecordar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ma.recordarContraseña();
            }
        });

    }

    /**
     * implemetancion del evento onAuthSuccess de la interfaz AuthListener, donde le decimos que si el login ha sido correcto
     * se lanzará el Intent DashboardActivity.
     */
    @Override
    public void onAuthSuccess() {
        Intent intent = new Intent(getContext(), DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onAuthFailure(String errorMessage) {

    }
}
