package com.example.proyecto_dam_202510.Dash;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.proyecto_dam_202510.Funciones;
import com.example.proyecto_dam_202510.R;
import com.example.proyecto_dam_202510.data.pojo.Coleccion;
import com.example.proyecto_dam_202510.data.viewdata.Coleccion_vm;
import com.example.proyecto_dam_202510.data.viewdata.UserColecciones_vm;
import com.example.proyecto_dam_202510.databinding.FragmentMisColeccionesBinding;

import java.util.ArrayList;
import java.util.List;


public class MisColeccionesFragment extends Fragment {

    private Coleccion_vm coleccionVm;
    private MisColeccionesAdapter adapter;
    private List<Coleccion> listaColecciones = new ArrayList<>();



    private FragmentMisColeccionesBinding binding;



    public static MisColeccionesFragment newInstance(String param1, String param2) {
        MisColeccionesFragment fragment = new MisColeccionesFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        coleccionVm = new Coleccion_vm();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMisColeccionesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        adapter = new MisColeccionesAdapter();
        binding.recyclerViewMisColecciones.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.recyclerViewMisColecciones.setAdapter(adapter);
        coleccionVm.getColecciones().observe(getViewLifecycleOwner(), new Observer<List<Coleccion>>() {
            @Override
            public void onChanged(List<Coleccion> coleccions) {
                adapter.setDatos(coleccions);
            }
        });
        adapter.setOnItemClickListener(new MisColeccionesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Coleccion coleccion) {
                Funciones.añadirColeccion(coleccion);
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.nav_userColecciones);


            }
        });
    }



}