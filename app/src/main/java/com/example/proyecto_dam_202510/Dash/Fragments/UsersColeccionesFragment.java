package com.example.proyecto_dam_202510.Dash.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.proyecto_dam_202510.Dash.Adapters.UserColeccionesAdapter;
import com.example.proyecto_dam_202510.R;
import com.example.proyecto_dam_202510.data.pojo.UsersColecciones;
import com.example.proyecto_dam_202510.data.viewdata.UserColecciones_vm;
import com.example.proyecto_dam_202510.databinding.FragmentUsersColeccionesBinding;
import com.google.firebase.firestore.DocumentReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UsersColeccionesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsersColeccionesFragment extends Fragment {


    private UserColecciones_vm userColeccionVm;
    private UserColeccionesAdapter adapter;


    private FragmentUsersColeccionesBinding binding;


    public UsersColeccionesFragment() {

    }

    // TODO: Rename and change types and number of parameters
    public static UsersColeccionesFragment newInstance(String param1, String param2) {
        UsersColeccionesFragment fragment = new UsersColeccionesFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userColeccionVm = new UserColecciones_vm();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUsersColeccionesBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.recyclerViewUsersColecciones.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new UserColeccionesAdapter();

        binding.recyclerViewUsersColecciones.setAdapter(adapter);

        userColeccionVm.getUsersColecciones().observe(getViewLifecycleOwner(), new Observer<List<UsersColecciones>>() {
            @Override
            public void onChanged(List<UsersColecciones> coleccions) {
                adapter.setDatos(coleccions);
            }
        });

        binding.btAgregarColeccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.gestionColeccionFragment);
            }
        });
        adapter.setOnItemClickListener(new UserColeccionesAdapter.onItemClickListener() {
            @Override
            public void onItemClick(UsersColecciones userColeccion) {
                DocumentReference doc = userColeccion.getUser();
                UsersColecciones item = userColeccion;
                Bundle bundle = new Bundle();
                SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String fechaFormateada = formatoSalida.format(item.getInicioColeccion());
                bundle.putString("fechaAlta", fechaFormateada);
                bundle.putString("nombre", item.getNombreColeccion());
                bundle.putInt("progress", item.getProgreso());
                bundle.putInt("totalCartas",item.getTotalCromos());
                bundle.putString("idColeccion", item.getColeccion().getId());
                bundle.putString("idUsuario", doc.getId());
                bundle.putString("imagen", item.getImagen());


                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_nav_userColecciones_to_detalleColeccionFragment, bundle);



            }
        });

    }


}