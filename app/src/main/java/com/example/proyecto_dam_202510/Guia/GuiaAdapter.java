package com.example.proyecto_dam_202510.Guia;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;


/**
 * Adaptador de la guia donde se establecen las pantallas de la guia .
 */
public class GuiaAdapter extends FragmentStateAdapter {

    private static final int NUM_PANTALLAS = 9;

    public GuiaAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new GuiaPantalla1Fragment();
            case 1:
                return new GuiaPantalla2Fragment();
            case 2:
                return new GuiaPantalla3Fragment();
            case 3:
                return new GuiaPantalla4Fragment();
            case 4:
                return new GuiaPantalla5Fragment();
            case 5:
                return new GuiaPantalla6Fragment();
            case 6:
                return new GuiaPantalla6_5Fragment();
            case 7:
                return new GuiaPantalla7Fragment();
            case 8:
                return new GuiaPantalla8Fragment();
            default:
                return new GuiaPantalla1Fragment();
        }

    }

    @Override
    public int getItemCount() {
        return NUM_PANTALLAS;
    }
}
