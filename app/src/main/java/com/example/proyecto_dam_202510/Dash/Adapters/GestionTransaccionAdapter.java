package com.example.proyecto_dam_202510.Dash.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.proyecto_dam_202510.Dash.Fragments.CrearColeccionFragment;
import com.example.proyecto_dam_202510.Dash.Fragments.MisColeccionesFragment;
import com.example.proyecto_dam_202510.Dash.Fragments.TransaccionEmisorFragment;
import com.example.proyecto_dam_202510.Dash.Fragments.TransaccionFragment;

/**
 * Adaptador para gestionar los dos fragments de las pantallas de transacciones.
 */
public class GestionTransaccionAdapter extends FragmentStateAdapter {

    public GestionTransaccionAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new TransaccionFragment();

            case 1:
                return new TransaccionEmisorFragment();
        }
        return null;

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
