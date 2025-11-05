package com.example.proyecto_dam_202510.Dash;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyecto_dam_202510.databinding.FragmentGestionColeccionBinding;
import com.example.proyecto_dam_202510.databinding.FragmentNuevaColeccionBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GestionColeccionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GestionColeccionFragment extends Fragment {

    private FragmentGestionColeccionBinding binding;
    private GestionColeccionAdapter adapter;



    public GestionColeccionFragment() {

    }

      public static GestionColeccionFragment newInstance(String param1, String param2) {
        GestionColeccionFragment fragment = new GestionColeccionFragment();
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
        binding = FragmentGestionColeccionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
                super.onViewCreated(view, savedInstanceState);
                adapter= new GestionColeccionAdapter(this);
                binding.pager.setAdapter(adapter);

                new TabLayoutMediator(binding.tabLayout, binding.pager, new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                   if (position==0){
                       tab.setText("Crear Coleccion");
                   }else{
                       tab.setText("Buscar Coleccion");
                   }
                    }
                }).attach();

    }
}