package com.example.proyecto_dam_202510.Dash.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.proyecto_dam_202510.Dash.Fragments.CrearColeccionFragment;
import com.example.proyecto_dam_202510.Dash.Fragments.MisColeccionesFragment;

public class GestionColeccionAdapter extends FragmentStateAdapter {
    public GestionColeccionAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new MisColeccionesFragment();

            case 1:
                return new CrearColeccionFragment();        }
        return null;
    }


    @Override
    public int getItemCount() {
        return 2;
    }
}
