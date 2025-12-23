package com.example.proyecto_dam_202510.Dash.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.proyecto_dam_202510.Dash.Adapters.GestionColeccionAdapter;
import com.example.proyecto_dam_202510.Dash.Adapters.GestionTransaccionAdapter;
import com.example.proyecto_dam_202510.R;
import com.example.proyecto_dam_202510.databinding.FragmentGestionColeccionBinding;
import com.example.proyecto_dam_202510.databinding.FragmentGestionTransaccionBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class GestionTransaccionFragment extends Fragment {

    private FragmentGestionTransaccionBinding binding;
    private GestionTransaccionAdapter adapter;

    public GestionTransaccionFragment() {

    }

    public static GestionTransaccionFragment newInstance(String param1, String param2) {
        GestionTransaccionFragment fragment = new GestionTransaccionFragment();
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
        binding = FragmentGestionTransaccionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter= new GestionTransaccionAdapter(this);
        binding.pager.setAdapter(adapter);
        /**
         * segun la posición de la pestaña, se mostrará el fragmento correspondiente.
         */
        new TabLayoutMediator(binding.tabLayout, binding.pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position==0){
                    tab.setIcon(R.drawable.move_to_inbox);
                    tab.setText("Peticiones Recibidas");

                }else{
                    tab.setIcon(R.drawable.outbox);
                    tab.setText("Peticiones Enviadas");
                }
            }
        }).attach();

    }



}
