package com.example.proyecto_dam_202510.Guia;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.proyecto_dam_202510.R;
import com.example.proyecto_dam_202510.databinding.FragmentGuiaPantalla1Binding;
import com.example.proyecto_dam_202510.databinding.FragmentGuiaPantalla2Binding;


public class GuiaPantalla2Fragment extends Fragment {
    FragmentGuiaPantalla2Binding binding;


    public GuiaPantalla2Fragment() {

    }

    public static GuiaPantalla2Fragment newInstance(String param1, String param2) {
        GuiaPantalla2Fragment fragment = new GuiaPantalla2Fragment();
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
        binding= FragmentGuiaPantalla2Binding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.textoGuia.setTranslationY(-500);
        binding.textoGuia.animate()
                .translationY(700f)
                .setDuration(2000)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
    }
}