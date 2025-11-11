package com.example.proyecto_dam_202510.Dash.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyecto_dam_202510.Dash.Adapters.CromosPosesionAdapter;
import com.example.proyecto_dam_202510.R;
import com.example.proyecto_dam_202510.data.pojo.CromoPosesionAgrupado;
import com.example.proyecto_dam_202510.data.viewdata.CromoPosesion_vm;
import com.example.proyecto_dam_202510.databinding.FragmentDetalleColeccionBinding;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;


public class DetalleColeccionFragment extends Fragment {

    private FragmentDetalleColeccionBinding binding;
    private CromosPosesionAdapter adapter;
    private CromoPosesion_vm cromoPosesion_vm;
    String idColeccion;
    String idUsuario;
    String nombre;
    String imagen;
    String fechaAlta;
    int progress;
    int totalCartas;

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
        progress = getArguments().getInt("progress");
        imagen = getArguments().getString("imagen");
        fechaAlta = getArguments().getString("fechaAlta");
        cromoPosesion_vm = new CromoPosesion_vm(idUsuario+idColeccion);
        totalCartas = getArguments().getInt("totalCartas");
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
        adapter =new CromosPosesionAdapter();
        binding.rvDetalleItems.setAdapter(new CromosPosesionAdapter());
        binding.rvDetalleItems.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvDetalleItems.setHasFixedSize(true);
        binding.rvDetalleItems.setAdapter(adapter);

        cromoPosesion_vm.getListaCromoPosesionAgrupado()
                .observe(getViewLifecycleOwner(), new Observer<List<CromoPosesionAgrupado>>() {
            @Override
            public void onChanged(List<CromoPosesionAgrupado> cromoPosesions) {
                adapter.setDatos(cromoPosesions);;
            }
        });




        binding.tvColeccionTitulo.setText(nombre);
        binding.tvColeccionProgreso.setProgress(progress);
        binding.tvColeccionProgreso.setMax(totalCartas);
       /*aqui la foto de portada..*/
        Picasso.get().load(imagen)
                .fit()
                .into(binding.ivPortada);
        binding.tvSubhead.setText(fechaAlta);
        binding.tvProgress.setText(String.format(Locale.getDefault(), "%.2f%%",  (progress * 100.0) / totalCartas));


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
        adapter.setOnItemClickListener(new CromosPosesionAdapter.onItemClickListener() {
            @Override
            public void onItemClick(CromoPosesionAgrupado cromoPosesionAgrupado) {
                Log.d("Cromo", cromoPosesionAgrupado.getNombre()+" "+cromoPosesionAgrupado.getRepetida());
                Bundle bundle = new Bundle();
                bundle.putString("nombre", cromoPosesionAgrupado.getNombre());
                bundle.putString("imagen", cromoPosesionAgrupado.getImagen());
                 bundle.putString("numero", cromoPosesionAgrupado.getNumero());
                 bundle.putInt("valor", cromoPosesionAgrupado.getValor());
                 bundle.putString("id", cromoPosesionAgrupado.getId());

                NavController navController = Navigation.findNavController(view);

                navController.navigate(R.id.detalleCromoPosesionFragment,bundle );



            }
        });
    }
}