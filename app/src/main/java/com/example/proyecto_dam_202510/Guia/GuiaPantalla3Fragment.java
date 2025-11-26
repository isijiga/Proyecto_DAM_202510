package com.example.proyecto_dam_202510.Guia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.proyecto_dam_202510.databinding.FragmentGuiaPantalla2Binding;
import com.example.proyecto_dam_202510.databinding.FragmentGuiaPantalla3Binding;


public class GuiaPantalla3Fragment extends Fragment {
    FragmentGuiaPantalla3Binding binding;


    public GuiaPantalla3Fragment() {

    }

        public static GuiaPantalla3Fragment newInstance(String param1, String param2) {
        GuiaPantalla3Fragment fragment = new GuiaPantalla3Fragment();
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
        binding= FragmentGuiaPantalla3Binding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.textoGuia.setTranslationY(500);
        binding.textoGuia.animate()
                .translationY(0f)
                .setDuration(2000)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
    }
}