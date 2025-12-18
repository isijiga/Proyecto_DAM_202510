package com.example.proyecto_dam_202510.Dash.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyecto_dam_202510.Dash.Adapters.TransaccionAdapter;
import com.example.proyecto_dam_202510.Dash.Adapters.TransaccionEmisorAdapter;
import com.example.proyecto_dam_202510.R;
import com.example.proyecto_dam_202510.data.pojo.Transaccion;
import com.example.proyecto_dam_202510.data.viewdata.Intercambio_vm;
import com.example.proyecto_dam_202510.data.viewdata.Transaccion_vm;
import com.example.proyecto_dam_202510.databinding.FragmentTransaccionBinding;
import com.example.proyecto_dam_202510.databinding.FragmentTransaccionEmisorBinding;

import java.util.List;


public class TransaccionEmisorFragment extends Fragment {

    FragmentTransaccionEmisorBinding binding;
    Transaccion_vm transacion_vm;
    TransaccionEmisorAdapter adapter;
    public TransaccionEmisorFragment() {
    }


    public static TransaccionEmisorFragment newInstance(String param1, String param2) {
        TransaccionEmisorFragment fragment = new TransaccionEmisorFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transacion_vm = new ViewModelProvider(this).get(Transaccion_vm.class);
        adapter = new TransaccionEmisorAdapter();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTransaccionEmisorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        super.onViewCreated(view, savedInstanceState);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        transacion_vm.getListaTransaccionesEmisor().observe(getViewLifecycleOwner(), new Observer<List<Transaccion>>() {
            @Override
            public void onChanged(List<Transaccion> transaccion) {

                adapter.setListaTransacciones(transaccion);

            }
        });

    }
}