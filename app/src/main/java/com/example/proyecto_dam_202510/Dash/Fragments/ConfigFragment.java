package com.example.proyecto_dam_202510.Dash.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.util.MutableDouble;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyecto_dam_202510.Dash.Adapters.IntercambioAdapter;
import com.example.proyecto_dam_202510.R;
import com.example.proyecto_dam_202510.data.pojo.CromoPosesionAgrupadoIntercambio;
import com.example.proyecto_dam_202510.data.viewdata.CromoPosesion_vm;
import com.example.proyecto_dam_202510.data.viewdata.Estadisticas_vm;
import com.example.proyecto_dam_202510.data.viewdata.Intercambio_vm;
import com.example.proyecto_dam_202510.databinding.FragmentConfigBinding;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * Fragmento que muestra la seccion de Estadistica de la aplicacion. Se crean 4 visuales con unformación general
 * de la aplicación.
 */
public class ConfigFragment extends Fragment {
    private FragmentConfigBinding binding;
    private Estadisticas_vm estadisticas_vm;
    private Intercambio_vm intercambio_vm;
    private List<CromoPosesionAgrupadoIntercambio> cromoPosesionAgrupadoList;

    public ConfigFragment() {

    }

    public static ConfigFragment newInstance(String param1, String param2) {
        ConfigFragment fragment = new ConfigFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        estadisticas_vm = new Estadisticas_vm();
        intercambio_vm = new Intercambio_vm();
        estadisticas_vm.cargarEstadisticas();
        cromoPosesionAgrupadoList = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentConfigBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intercambio_vm.getCromoPosesionAgrupadoList().observe(getViewLifecycleOwner(), new Observer<List<CromoPosesionAgrupadoIntercambio>>() {
            @Override
            public void onChanged(List<CromoPosesionAgrupadoIntercambio> cromoPosesionAgrupadoIntercambios) {
                cromoPosesionAgrupadoList.addAll(cromoPosesionAgrupadoIntercambios);
                cromoPosesionAgrupadoList.sort(Comparator.comparing(CromoPosesionAgrupadoIntercambio::getRepetida).reversed());
               /* binding.tvMasRepetida.setText(cromoPosesionAgrupadoList.get(0).getNombre() + " - " + cromoPosesionAgrupadoList.get(0).getRepetida());*/
            }
        });


        estadisticas_vm.getPrecioPorCarta().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.tvTotalPrecio.setText(String.format("%.2f €", aDouble));
            }
        });

        estadisticas_vm.getCountUsuarios().observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                binding.tvTotalUsuarios.setText(String.format("%d", aLong));
            }
        });
        estadisticas_vm.getCountCromosPosesion().observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                binding.tvTotalCromos.setText(String.format("%d", aLong));
            }
        });


    }
}