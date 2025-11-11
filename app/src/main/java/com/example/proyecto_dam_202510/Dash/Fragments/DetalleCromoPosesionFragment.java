package com.example.proyecto_dam_202510.Dash.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyecto_dam_202510.R;
import com.example.proyecto_dam_202510.databinding.FragmentDetalleColeccionBinding;


public class DetalleCromoPosesionFragment extends Fragment {
 FragmentDetalleColeccionBinding binding;

    public DetalleCromoPosesionFragment() {

    }


    public static DetalleCromoPosesionFragment newInstance(String param1, String param2) {
        DetalleCromoPosesionFragment fragment = new DetalleCromoPosesionFragment();
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
       binding = FragmentDetalleColeccionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}