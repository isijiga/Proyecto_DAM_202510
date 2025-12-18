package com.example.proyecto_dam_202510.Dash.Fragments;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer; // <-- Asegúrate de importar Observer
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto_dam_202510.Dash.Adapters.IntercambioAdapter;
import com.example.proyecto_dam_202510.R;
import com.example.proyecto_dam_202510.data.pojo.CromoPosesionAgrupadoIntercambio;
import com.example.proyecto_dam_202510.data.viewdata.Intercambio_vm;
import com.example.proyecto_dam_202510.databinding.FragmentIntercambioBinding;

import java.util.ArrayList;
import java.util.List;
import com.example.proyecto_dam_202510.Funciones;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Fragmento que muestra la seccion de Intercambio de cromos.
 */
public class IntercambioFragment extends Fragment implements IntercambioAdapter.OnItemClickListener {
    private FragmentIntercambioBinding binding;
    private Intercambio_vm viewModel;
    private IntercambioAdapter adapter;
    private List<String> coleccionLista = new ArrayList<>();
    private ArrayAdapter<String> coleccionAdapter;
    private String seleccion;

    public IntercambioFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        coleccionAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, coleccionLista);
        //viewModel = new ViewModelProvider(this).get(Intercambio_vm.class);
        /*implementar aqui la coleccion a filtrar a traves de una lista*/
        viewModel =new ViewModelProvider(this).get(Intercambio_vm.class);
        adapter = new IntercambioAdapter();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentIntercambioBinding.inflate(inflater, container, false);
        binding.listView.setAdapter(coleccionAdapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);


        viewModel.getCromoPosesionAgrupadoList().observe(getViewLifecycleOwner(), new Observer<List<CromoPosesionAgrupadoIntercambio>>() {
            @Override
            public void onChanged(List<CromoPosesionAgrupadoIntercambio> listaCromos) {

                if (listaCromos != null) {
                    adapter.setCromos(listaCromos);
                }
            }
        });



        viewModel.getListaColecciones()

                .observe(getViewLifecycleOwner(), listaColecciones -> {
            if (listaColecciones != null && !listaColecciones.isEmpty()) {

                coleccionLista.clear();
                coleccionLista.addAll(listaColecciones);
                coleccionAdapter.notifyDataSetChanged();

                if (viewModel.getCromoPosesionAgrupadoList().getValue() == null) {
                    viewModel.setColeccionRecarga(listaColecciones.get(0));
                }
            } else {

                coleccionLista.clear();
                coleccionAdapter.notifyDataSetChanged();
            }
        });
        binding.listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleccion = (String) parent.getItemAtPosition(position);
                viewModel.setColeccionRecarga(seleccion);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }


    @Override
    public void onItemClick(CromoPosesionAgrupadoIntercambio cromo) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_mensaje_intercambio, null);
        TextInputEditText inputMensaje = dialogView.findViewById(R.id.etMensaje);
         AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Pedir Carta")
                .setView(dialogView)
                .setMessage("¿Quieres pedir esta carta?")
                        .setCancelable(false)
                                .setPositiveButton("Confirmar Petición", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String mensaje = inputMensaje.getText().toString();

                                        Funciones.pedirCarta(cromo,requireContext(),mensaje);
                                    }
                                })
                                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                .show();






    }
}