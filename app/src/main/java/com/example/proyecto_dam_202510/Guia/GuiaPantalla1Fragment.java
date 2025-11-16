package com.example.proyecto_dam_202510.Guia;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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




public class GuiaPantalla1Fragment extends Fragment {
    FragmentGuiaPantalla1Binding binding;

    public GuiaPantalla1Fragment() {

    }

    public static GuiaPantalla1Fragment newInstance(String param1, String param2) {
        GuiaPantalla1Fragment fragment = new GuiaPantalla1Fragment();
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
        binding= FragmentGuiaPantalla1Binding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(binding.ivLogoGuia, "scaleX", 1.0f, 1.05f);
        scaleX.setDuration(1000);
        scaleX.setRepeatCount(10);
        scaleX.setRepeatMode(ObjectAnimator.REVERSE);
        scaleX.setInterpolator(new AccelerateDecelerateInterpolator());
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(binding.ivLogoGuia, "scaleY", 1.0f, 1.05f);
        scaleY.setDuration(1000);
        scaleY.setRepeatCount(10);
        scaleY.setRepeatMode(ObjectAnimator.REVERSE);
        scaleY.setInterpolator(new AccelerateDecelerateInterpolator());
        AnimatorSet zoomSet = new AnimatorSet();
        zoomSet.playTogether(scaleX, scaleY);
        zoomSet.start();

        binding.ivFlecha.setTranslationX(500);
        binding.ivFlecha.animate()
                .translationX(0f)
                .setDuration(2000)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
    }
}