package com.example.proyecto_dam_202510.Dash.Fragments;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer; // <-- Asegúrate de importar Observer
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.proyecto_dam_202510.Dash.Adapters.IntercambioAdapter;
import com.example.proyecto_dam_202510.data.pojo.CromoPosesionAgrupadoIntercambio;
import com.example.proyecto_dam_202510.data.viewdata.Intercambio_vm;
import com.example.proyecto_dam_202510.databinding.FragmentIntercambioBinding;

import java.util.ArrayList;
import java.util.List;
import com.example.proyecto_dam_202510.Funciones;

/**
 * Fragmento que muestra la seccion de Intercambio de cromos.
 */
public class IntercambioFragment extends Fragment implements IntercambioAdapter.OnItemClickListener {
    private FragmentIntercambioBinding binding;
    private Intercambio_vm viewModel;
    private IntercambioAdapter adapter;
    private List<String> coleccionLista = new ArrayList<>();
    private ArrayAdapter<String> coleccionAdapter;
    private String seleccion;
    public IntercambioFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        coleccionAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, coleccionLista);
        //viewModel = new ViewModelProvider(this).get(Intercambio_vm.class);
        /*implementar aqui la coleccion a filtrar a traves de una lista*/
        viewModel =new ViewModelProvider(this).get(Intercambio_vm.class);
        adapter = new IntercambioAdapter();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentIntercambioBinding.inflate(inflater, container, false);
        binding.listView.setAdapter(coleccionAdapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        viewModel.getCromoPosesionAgrupadoList().observe(getViewLifecycleOwner(), new Observer<List<CromoPosesionAgrupadoIntercambio>>() {
            @Override
            public void onChanged(List<CromoPosesionAgrupadoIntercambio> listaCromos) {

                if (listaCromos != null) {
                    adapter.setCromos(listaCromos);
                }
            }
        });



        viewModel.getListaColecciones()

                .observe(getViewLifecycleOwner(), listaColecciones -> {
            if (listaColecciones != null && !listaColecciones.isEmpty()) {

                // 1. Limpiamos la lista interna del Adapter
                coleccionLista.clear();

                // 2. Añadimos los nuevos datos de Firebase
                coleccionLista.addAll(listaColecciones);

                // 3. Notificamos al Adapter del ListView que los datos han cambiado
                coleccionAdapter.notifyDataSetChanged();

                // 4. Cargamos la primera colección por defecto (Solo si es la primera carga)
                if (viewModel.getCromoPosesionAgrupadoList().getValue() == null) {
                    viewModel.setColeccionRecarga(listaColecciones.get(0));
                }
            } else {
                // Si la lista está vacía o nula, limpiamos el adapter
                coleccionLista.clear();
                coleccionAdapter.notifyDataSetChanged();
            }
        });
        binding.listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleccion = (String) parent.getItemAtPosition(position);
                viewModel.setColeccionRecarga(seleccion);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }


    @Override
    public void onItemClick(CromoPosesionAgrupadoIntercambio cromo) {

        Funciones.pedirCarta(cromo,requireContext());

    }
}