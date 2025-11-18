package com.example.proyecto_dam_202510.login;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.proyecto_dam_202510.R;

/**
 * La clase Login es encargada de portar un FragmentContainerView en el layout que servirá
 * para mostrar las diferentes partes del proceso de Login.
 */
public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }
}