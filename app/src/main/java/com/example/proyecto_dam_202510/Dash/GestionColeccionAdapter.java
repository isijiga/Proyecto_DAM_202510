package com.example.proyecto_dam_202510.Dash;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class GestionColeccionAdapter extends FragmentStateAdapter {
    public GestionColeccionAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new CrearColeccionFragment();
            case 1:
                return new MisColeccionesFragment();
        }
        return null;
    }


    @Override
    public int getItemCount() {
        return 2;
    }
}
