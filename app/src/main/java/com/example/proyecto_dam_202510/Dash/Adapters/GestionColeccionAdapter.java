package com.example.proyecto_dam_202510.Dash.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.proyecto_dam_202510.Dash.Fragments.CrearColeccionFragment;
import com.example.proyecto_dam_202510.Dash.Fragments.MisColeccionesFragment;


/**
 * Adaptador para gestionar los dos fragments de mis colecciones.
 *  */
public class GestionColeccionAdapter extends FragmentStateAdapter {
    public GestionColeccionAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }


    /**
     * Este metodo pinta el fragment de la posición que le pasen por parametro
     * @param position Posicion del fragment
     * @return
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new MisColeccionesFragment();

            case 1:
                return new CrearColeccionFragment();
        }
        return null;
    }


    @Override
    public int getItemCount() {
        return 2;
    }
}
