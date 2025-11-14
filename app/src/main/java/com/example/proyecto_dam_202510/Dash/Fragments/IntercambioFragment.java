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

import com.example.proyecto_dam_202510.Dash.Adapters.IntercambioAdapter;
import com.example.proyecto_dam_202510.data.pojo.CromoPosesionAgrupadoIntercambio;
import com.example.proyecto_dam_202510.data.viewdata.Intercambio_vm;
import com.example.proyecto_dam_202510.databinding.FragmentIntercambioBinding;

import java.util.List;

public class IntercambioFragment extends Fragment {


    private FragmentIntercambioBinding binding;
    private Intercambio_vm viewModel;
    private IntercambioAdapter adapter;

    public IntercambioFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(Intercambio_vm.class);
        adapter = new IntercambioAdapter();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentIntercambioBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        viewModel.getCromoPosesionAgrupadoList().observe(getViewLifecycleOwner(), new Observer<List<CromoPosesionAgrupadoIntercambio>>() {
            @Override
            public void onChanged(List<CromoPosesionAgrupadoIntercambio> listaCromos) {


                if (listaCromos != null) {
                    adapter.setCromos(listaCromos);
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Evita fugas de memoria
    }
}