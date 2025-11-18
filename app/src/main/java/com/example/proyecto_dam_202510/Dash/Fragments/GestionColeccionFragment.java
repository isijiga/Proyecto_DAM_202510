package com.example.proyecto_dam_202510.Dash.Fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.proyecto_dam_202510.Dash.Adapters.GestionColeccionAdapter;
import com.example.proyecto_dam_202510.databinding.FragmentGestionColeccionBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


/**
 * Fragmento que muestra el alta de Carta en una colección. El fragmento se basa en TabLayout y  ViewPager2 para mostrar
 * la posibilidad de Buscar Carta o Crear Carta.
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
        /**
         * segun la posición de la pestaña, se mostrará el fragmento correspondiente.
         */
                new TabLayoutMediator(binding.tabLayout, binding.pager, new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                   if (position==0){
                       tab.setText("BUSCAR");

                   }else{
                       tab.setText("CREAR");
                   }
                    }
                }).attach();

    }
}