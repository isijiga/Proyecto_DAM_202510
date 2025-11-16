package com.example.proyecto_dam_202510;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_dam_202510.Dash.DashboardActivity;
import com.example.proyecto_dam_202510.login.RecordarContrasenaFragment;
import com.example.proyecto_dam_202510.login.LoginFragment;
import com.example.proyecto_dam_202510.login.RegistroFragment;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

        private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.login);
        checkEstado();



    }

    private void checkEstado() {


        if(mAuth.getCurrentUser() != null){
            String[] nombre = mAuth.getCurrentUser().getEmail().split("@");
            Toast.makeText(this,"Hola de nuevo "+nombre[0] ,Toast.LENGTH_LONG).show();
            navegarDash();
        }
        else{

            if(getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView) == null){
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, new LoginFragment())
                        .commit();

            };

        }

    }

    private void navegarDash() {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    public void registroUsuario() {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, new RegistroFragment())
                    .addToBackStack(null)
                    .commit();


    }

    public void recordarContraseña() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, new RecordarContrasenaFragment())
                .addToBackStack(null)
                .commit();

    }
}




