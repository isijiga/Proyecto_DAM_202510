package com.example.proyecto_dam_202510.Dash.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyecto_dam_202510.Dash.Adapters.TransaccionAdapter;
import com.example.proyecto_dam_202510.R;
import com.example.proyecto_dam_202510.data.pojo.Transaccion;
import com.example.proyecto_dam_202510.data.viewdata.Intercambio_vm;
import com.example.proyecto_dam_202510.data.viewdata.Transaccion_vm;
import com.example.proyecto_dam_202510.databinding.FragmentTransaccionBinding;

import java.util.List;


public class TransaccionFragment extends Fragment implements TransaccionAdapter.OnItemClickListener {

    FragmentTransaccionBinding binding;
    Transaccion_vm transacion_vm;
    TransaccionAdapter adapter;
    public TransaccionFragment() {
    }


    public static TransaccionFragment newInstance(String param1, String param2) {
        TransaccionFragment fragment = new TransaccionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transacion_vm = new ViewModelProvider(this).get(Transaccion_vm.class);
        adapter = new TransaccionAdapter(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTransaccionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        super.onViewCreated(view, savedInstanceState);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        transacion_vm.getListaTransacciones().observe(getViewLifecycleOwner(), new Observer<List<Transaccion>>() {
            @Override
            public void onChanged(List<Transaccion> transaccion) {

            adapter.setListaTransacciones(transaccion);

            }
        });

    }

    @Override
    public void onItemClick(Transaccion transaccion) {
        Log.d("Prueba", "Se ha seleccionado la transaccion "+ transaccion.getIdTransaccion());
        mostrarDialogo(transaccion);
    }

    private void mostrarDialogo(Transaccion transaccion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        if(transaccion.getEstado().equals("pendiente")) {
            builder.setTitle("Cambiar Cromo");
            builder.setMessage("¿Quieres aceptar la transacción?");
            builder.setPositiveButton("Aceptar Petición", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    transacion_vm.aceptarPeticion(transaccion);
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }
        if(transaccion.getEstado().equals("ACEPTADA. Pdte Envio")) {
            builder.setTitle("Cambiar Cromo");
            builder.setMessage("¿Quieres enviar el cromo a "+transaccion.getEmailPedidoPor()+ "?\n"+"Cuando lo envie se eliminará de su lista de cromos.");
            builder.setPositiveButton("Aceptar Petición", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    transacion_vm.enviarPeticion(transaccion);
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();



        }


    }
}