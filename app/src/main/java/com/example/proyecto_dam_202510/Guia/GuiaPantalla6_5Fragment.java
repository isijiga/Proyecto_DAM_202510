package com.example.proyecto_dam_202510.Guia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.proyecto_dam_202510.databinding.FragmentGuiaPantalla4Binding;
import com.example.proyecto_dam_202510.databinding.FragmentGuiaPantalla5Binding;
import com.example.proyecto_dam_202510.databinding.FragmentGuiaPantalla65Binding;


public class GuiaPantalla6_5Fragment extends Fragment {
    FragmentGuiaPantalla65Binding binding;


    public GuiaPantalla6_5Fragment() {

    }

    public static GuiaPantalla6_5Fragment newInstance(String param1, String param2) {
        GuiaPantalla6_5Fragment fragment = new GuiaPantalla6_5Fragment();
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
        binding= FragmentGuiaPantalla65Binding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.textoGuia.setTranslationY(0);
        binding.textoGuia.animate()
                .translationY(450f)
                .setDuration(2000)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
    }
}