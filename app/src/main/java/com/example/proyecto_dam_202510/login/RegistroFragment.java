package com.example.proyecto_dam_202510.login;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.example.proyecto_dam_202510.Auth;
import com.example.proyecto_dam_202510.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;


/**
 * Este fragment se encarga de gestionar el registro de un nuevo usuario en el sistema de autenticación de Firebase.
 *
 */
public class RegistroFragment extends Fragment {
    private Auth firebaseAuth;
    private TextInputEditText inputMail;
    private TextInputEditText inputPass;
    private TextInputEditText inputRepPass;
    private Button botonRegistrar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registro, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = new Auth(FirebaseAuth.getInstance());
        inputMail = view.findViewById(R.id.et_usuario);
        inputPass = view.findViewById(R.id.et_contrasena);
        inputRepPass = view.findViewById(R.id.et_rep_contrasena);
        botonRegistrar = view.findViewById(R.id.btn_registrar);

        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                botonRegistrar.setEnabled(false);
                registrarUsuario();
            }


        });

    }

    /**
     * Metodo que permite registgrar a un usuario en el sistema.
     */
    private void registrarUsuario() {
        String mail = inputMail.getText().toString();
        String pass = inputPass.getText().toString();
        String repPass = inputRepPass.getText().toString();


        /*si no coinciden no llega a crear la cuenta*/
        if (!pass.equals(repPass)) {
            Toast.makeText(getActivity(), "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
        } else {
            firebaseAuth.crearCuenta(mail, pass, getActivity());

            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().popBackStack();
            }


        }


    }
}