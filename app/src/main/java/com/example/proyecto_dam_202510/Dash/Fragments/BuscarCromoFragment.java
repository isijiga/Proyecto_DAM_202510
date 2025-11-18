package com.example.proyecto_dam_202510.Dash.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.proyecto_dam_202510.Dash.Adapters.CromosAdapter;
import com.example.proyecto_dam_202510.Funciones;
import com.example.proyecto_dam_202510.R;
import com.example.proyecto_dam_202510.data.pojo.Cromo;
import com.example.proyecto_dam_202510.data.viewdata.Cromo_vm;
import com.example.proyecto_dam_202510.databinding.FragmentBuscarCromoBinding;
import java.util.ArrayList;
import java.util.List;


/**
 * Fragmento que muestra el recycledView de la cartas actuales de la colección. Cualquier usuario
 * puede aportar foto de una carta a una colección especifica.
 */
public class BuscarCromoFragment extends Fragment {

    private FragmentBuscarCromoBinding binding;
    private CromosAdapter adapter;
    private Cromo_vm cromo_vm;
    private String idColeccion;

    public BuscarCromoFragment() {

    }

    public static BuscarCromoFragment newInstance(String param1, String param2) {
        return new BuscarCromoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new CromosAdapter();
        idColeccion = getArguments().getString("coleccion");
        cromo_vm = new Cromo_vm(idColeccion);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBuscarCromoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setHasFixedSize(true);

        adapter.setOnItemClickListener(new CromosAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Cromo cromo) {
                Log.d("Cromo", cromo.getNombre());
                Funciones.agregarCromoPosesion(idColeccion, cromo.getId(), cromo.getNombre(), cromo.getNumero(),
                        null, cromo.getValor(), cromo.getImagen(), Funciones.ahora());

                NavController navController = Navigation.findNavController(view);
                navController.popBackStack();
                navController.navigate(R.id.nav_userColecciones);
                Toast.makeText(requireContext(), "Carta añadida a coleccion!", Toast.LENGTH_LONG).show();

            }

        });


        cromo_vm.getCromos().observe(getViewLifecycleOwner(), new Observer<List<Cromo>>() {
            @Override
            public void onChanged(List<Cromo> cromos) {
                adapter.setDatos(cromos);
            }
        });

        binding.btnAnadirCarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("coleccion", idColeccion);
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_buscarCromoFragment_to_anadirCromoFragment, bundle);
            }
        });

    }
}