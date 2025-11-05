package com.example.proyecto_dam_202510.Dash;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyecto_dam_202510.R;
import com.example.proyecto_dam_202510.databinding.FragmentDetalleColeccionBinding;


public class DetalleColeccionFragment extends Fragment {

    private FragmentDetalleColeccionBinding binding;
    String idColeccion;
    public DetalleColeccionFragment() {

    }

    public static DetalleColeccionFragment newInstance(String param1, String param2) {
        DetalleColeccionFragment fragment = new DetalleColeccionFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetalleColeccionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String nombre = getArguments().getString("nombre");
        idColeccion = getArguments().getString("idColeccion");
        int id = getArguments().getInt("id");

        binding.tvColeccionTitulo.setText(nombre);
        binding.tvColeccionProgreso.setProgress(id);

        binding.btnAtrasManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController navController = Navigation.findNavController(v);
                 navController.popBackStack();
            }
        });

        binding.fabAgregarCromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("coleccion", idColeccion);
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_detalleColeccionFragment_to_anadirCromoFragment, bundle);
            }
        });
    }
}