package com.example.proyecto_dam_202510.Dash;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.proyecto_dam_202510.MainActivity;
import com.example.proyecto_dam_202510.R;
import com.example.proyecto_dam_202510.data.pojo.Coleccion;
import com.example.proyecto_dam_202510.data.pojo.UsersColecciones;
import com.example.proyecto_dam_202510.data.viewdata.Coleccion_vm;
import com.example.proyecto_dam_202510.data.viewdata.UserColecciones_vm;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class DashboardActivity extends AppCompatActivity {
private FirebaseAuth mAuth;
private FirebaseUser user;

private UserColecciones_vm usercoleccionVm ;


@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    usercoleccionVm = new UserColecciones_vm();
        setContentView(R.layout.activity_dashboard);

    BottomNavigationView bottomNav = findViewById(R.id.toolbar);

    NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
    NavController navController = navHostFragment.getNavController();
    NavigationUI.setupWithNavController(bottomNav, navController);
        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signOut();

                Intent inten = new Intent(getApplicationContext(), MainActivity.class);
                inten.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(inten);
                finish();
            }});

/*para pruebas userColeccioens*/
    usercoleccionVm.getUsersColecciones().observe(this, new Observer<List<UsersColecciones>>() {
        @Override
        public void onChanged(List<UsersColecciones> userColeccions) {
            for(UsersColecciones userColeccion : userColeccions){
                Log.d("Colecciones", userColeccion.getNombreColeccion()+"");

            }
        }
    });

    }


}