package com.example.proyecto_dam_202510.Dash;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyecto_dam_202510.R;
import com.example.proyecto_dam_202510.data.pojo.CromoPosesionAgrupado;
import com.example.proyecto_dam_202510.data.viewdata.CromoPosesion_vm;
import com.example.proyecto_dam_202510.databinding.FragmentDetalleColeccionBinding;

import java.util.List;


public class DetalleColeccionFragment extends Fragment {

    private FragmentDetalleColeccionBinding binding;
    private CromosPosesionAdapter adapter;
    private CromoPosesion_vm cromoPosesion_vm;
    String idColeccion;
    String idUsuario;
    String nombre;
    int id;
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
        nombre = getArguments().getString("nombre");
        idColeccion = getArguments().getString("idColeccion");
        idUsuario = getArguments().getString("idUsuario");
        id = getArguments().getInt("id");
        cromoPosesion_vm = new CromoPosesion_vm(idUsuario+idColeccion);

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

        binding.rvDetalleItems.setAdapter(new CromosPosesionAdapter());
        binding.rvDetalleItems.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvDetalleItems.setHasFixedSize(true);
        adapter =new CromosPosesionAdapter();
        binding.rvDetalleItems.setAdapter(adapter);

        cromoPosesion_vm.getListaCromoPosesionAgrupado().observe(getViewLifecycleOwner(), new Observer<List<CromoPosesionAgrupado>>() {
            @Override
            public void onChanged(List<CromoPosesionAgrupado> cromoPosesions) {
                adapter.setDatos(cromoPosesions);;
            }
        });




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
                navController.navigate(R.id.action_detalleColeccionFragment_to_buscarCromoFragment,bundle );
            }
        });
    }
}