package com.example.proyecto_dam_202510.Dash.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import com.example.proyecto_dam_202510.Funciones;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.proyecto_dam_202510.Dash.Adapters.TransaccionAdapter;
import com.example.proyecto_dam_202510.Dash.Adapters.TransaccionEmisorAdapter;

import com.example.proyecto_dam_202510.R;
import com.example.proyecto_dam_202510.data.pojo.Transaccion;
import com.example.proyecto_dam_202510.data.viewdata.Intercambio_vm;
import com.example.proyecto_dam_202510.data.viewdata.Transaccion_vm;
import com.example.proyecto_dam_202510.databinding.FragmentTransaccionBinding;
import com.example.proyecto_dam_202510.databinding.FragmentTransaccionEmisorBinding;

import java.util.List;


/**
 * Clase fragment para la lista de transacciones de peticiones enviadas.
 */
public class TransaccionEmisorFragment extends Fragment implements TransaccionEmisorAdapter.OnItemClickListener, TransaccionEmisorAdapter.OnItemLongClickListener {

    FragmentTransaccionEmisorBinding binding;
    Transaccion_vm transacion_vm;
    TransaccionEmisorAdapter adapter;

    public TransaccionEmisorFragment() {
    }


    public static TransaccionEmisorFragment newInstance(String param1, String param2) {
        TransaccionEmisorFragment fragment = new TransaccionEmisorFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transacion_vm = new ViewModelProvider(this).get(Transaccion_vm.class);
        adapter = new TransaccionEmisorAdapter();
        adapter.setOnItemClickListener(this);
        adapter.setOnItemLongClickListener(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTransaccionEmisorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        transacion_vm.getListaTransaccionesEmisor().observe(getViewLifecycleOwner(), new Observer<List<Transaccion>>() {
            @Override
            public void onChanged(List<Transaccion> transaccion) {
                adapter.setListaTransacciones(transaccion);



            }
        });

    }

    @Override
    public void onItemClick(Transaccion transaccion) {

        if(transaccion.getEstado().equals("ACEPTADA. Pdte Envio")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                    .setTitle("Mensaje");
            if (!transaccion.getMensajeRespuesta().isEmpty()) {
                builder.setMessage(transaccion.getEmailPedidoA() + ":\n"+"\""+transaccion.getMensajeRespuesta()+"\"");
                builder.setIcon(R.drawable.message);
            } else {
                builder.setMessage(("Estado: " + transaccion.getEstado()) + "\n"+ "Sin respuesta");
 }
            AlertDialog dialog = builder.show();
        }
        else if(transaccion.getEstado().equals("ENVIADA")){
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("La carta ha sido enviada por "+transaccion.getEmailPedidoA());
            builder.setIcon(R.drawable.send);
            builder.setPositiveButton("Carta Recibida",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Funciones.cartaRecibida(transaccion,requireContext());
                }
            });
            AlertDialog dialog = builder.show();


        }


    }

    @Override
    public void onItemLongClick(Transaccion transaccion) {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(requireContext())
                .setMessage("¿Quieres eliminar la transacción")
                .setTitle("Eliminar Transacción")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(Funciones.eliminarTransaccion(transaccion)){
                            Toast.makeText(requireContext(),"Transaccion "+transaccion.getIdTransaccion()+" cancelada correctamente",Toast.LENGTH_LONG).show();
                        };
                    }

                });
        builder.show();
    }
}