package com.example.proyecto_dam_202510;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.proyecto_dam_202510.Dash.DashboardActivity;
import com.example.proyecto_dam_202510.login.RecordarContrasenaFragment;
import com.example.proyecto_dam_202510.login.LoginFragment;
import com.example.proyecto_dam_202510.login.RegistroFragment;
import com.google.firebase.auth.FirebaseAuth;

/**
 * @brief
 * @author IsidoroJiménezGarcia
 *
 * Esta clase principal se encarga de gestionar el acceso al usuario a la app.
 *
 *
 */
public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.login);
        //lo primero que realiza es el metodo checkEstado();
        checkEstado();
    }

    /**
     * Este metodo se encarga de comprobar si existe algun usuario logado en la aplicación
     * si hay un usuario logado lo envia al metodo navegarDash y muestra bienvenida, sino lo redirigia
     * la pantalla de Login.
     */
    private void checkEstado() {
        if (mAuth.getCurrentUser() != null) {
            String[] nombre = mAuth.getCurrentUser().getEmail().split("@");
            Toast.makeText(this, "Hola de nuevo " + nombre[0], Toast.LENGTH_LONG).show();
            navegarDash();
        } else {
            if (getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView) == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, new LoginFragment())
                        .commit();
            }
            ;
        }
    }

    /**
     * Metodo que redirige al usuario al comienzo de la actividad DashboardActivity
     * limpia la pila de pantallas y a traves del metodo finish() se impide al usuario que pueda volver hacia atras.
     */
    private void navegarDash() {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    /**
     * Metodo que redirige al usuario a la pantalla de RegistroFragment
     */
    public void registroUsuario() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, new RegistroFragment())
                .addToBackStack(null)
                .commit();
    }

    /**
     * Metodo que redirige al usuario a la pantalla de RecordarContrasenaFragment
     */
    public void recordarContraseña() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, new RecordarContrasenaFragment())
                .addToBackStack(null)
                .commit();
    }
}




