package com.example.proyecto_dam_202510.Dash.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyecto_dam_202510.R;
import com.example.proyecto_dam_202510.databinding.FragmentDetalleColeccionBinding;
import com.example.proyecto_dam_202510.databinding.FragmentDetalleCromoPosesionBinding;
import com.squareup.picasso.Picasso;


public class DetalleCromoPosesionFragment extends Fragment {
 FragmentDetalleCromoPosesionBinding binding;
 String nombre;
 String imagen;
 String numero;
 int valor;
 String id;
 String coleccion;
 int repetida;
 String fechaAdquisicion;
 String tipo;


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
        nombre = getArguments().getString("nombre");
        imagen = getArguments().getString("imagen");
        numero = getArguments().getString("numero");
        valor = getArguments().getInt("valor");
        id = getArguments().getString("id");
        coleccion = getArguments().getString("coleccion");
        repetida = getArguments().getInt("repetida");
        fechaAdquisicion = getArguments().getString("fechaAdquisicion");
        tipo = getArguments().getString("tipo");



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = FragmentDetalleCromoPosesionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Picasso.get().load(imagen).into(binding.imageView);
        binding.tvNombre.setText(nombre);
        binding.tvColeccion.setText(coleccion);
        binding.tvFechaAdquisision.setText(fechaAdquisicion);
        binding.tvRepetidas.setText(repetida+" Repetida");
        binding.tvTipo.setText(tipo);

        binding.btnAtrasManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController navController = Navigation.findNavController(v);
                navController.popBackStack();
            }
        });
            }
}