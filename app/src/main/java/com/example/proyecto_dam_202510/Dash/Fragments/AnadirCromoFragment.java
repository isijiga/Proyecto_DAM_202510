package com.example.proyecto_dam_202510.Dash.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyecto_dam_202510.Funciones;
import com.example.proyecto_dam_202510.R;
import com.example.proyecto_dam_202510.databinding.FragmentAnadirCromoBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;


public class AnadirCromoFragment extends Fragment {
FragmentAnadirCromoBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser user;
    String coleccionId;

    public AnadirCromoFragment() {


    }

    public static AnadirCromoFragment newInstance(String param1, String param2) {
        AnadirCromoFragment fragment = new AnadirCromoFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();



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
        binding.btnAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String nombre = binding.etNombre.getText().toString();
                String equipo = binding.etEquipo.getText().toString();
                String valor = binding.actvNumeroCarta.getText().toString();
                String numero = binding.actvNumeroCarta.getText().toString();
                String userid = user.getUid();


               Funciones.agregarCromo(coleccionId,numero,nombre,0+"",null,0,null);
               Funciones.agregarCromoPosesion(coleccionId,numero,nombre,0+"",null,0,null,
                       Funciones.ahora());

               NavController navController = Navigation.findNavController(v);
               navController.popBackStack();
               navController.navigate(R.id.nav_userColecciones);

            }
        });


    }
}