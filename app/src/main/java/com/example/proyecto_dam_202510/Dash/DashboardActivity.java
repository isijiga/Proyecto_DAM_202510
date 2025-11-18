package com.example.proyecto_dam_202510.Dash;


import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.example.proyecto_dam_202510.R;
import com.example.proyecto_dam_202510.data.pojo.UsersColecciones;
import com.example.proyecto_dam_202510.data.viewdata.UserColecciones_vm;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.List;

/**
 * @author Isidoro Jiménez García
 * Clase principal de la app que muestra las colecciones que un usuario logueado posee.
 * Es la Activity que posee el FragmentContainerView, el navControler y el menu inferior de la app.
 *
 *
 */
public class DashboardActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private UserColecciones_vm usercoleccionVm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        usercoleccionVm = new UserColecciones_vm();
        setContentView(R.layout.activity_dashboard);
        BottomNavigationView bottomNav = findViewById(R.id.toolbar);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().
                findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNav, navController);
        bottomNav.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                navController.popBackStack(item.getItemId(), false);
            }
        });

        /**
         * escuchador en el navController para ocultar/mostrar el menu inferior de la app cuando interese.
         * por ejemplo en el detalle de carta o al añadir un nuevo Cromo.
         */
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                if (navDestination.getId() == R.id.detalleCromoPosesionFragment) {
                    bottomNav.setVisibility(View.GONE);
                } else if (navDestination.getId() == R.id.anadirCromoFragment) {
                    bottomNav.setVisibility(View.GONE);
                } else {
                    bottomNav.setVisibility(View.VISIBLE);
                }
            }
        });

        /*para pruebas userColeccioens*/
        usercoleccionVm.getUsersColecciones().observe(this, new Observer<List<UsersColecciones>>() {
            @Override
            public void onChanged(List<UsersColecciones> userColeccions) {
                for (UsersColecciones userColeccion : userColeccions) {
                    Log.d("Colecciones", userColeccion.getNombreColeccion() + "");

                }
            }
        });

    }
}