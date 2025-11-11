package com.example.proyecto_dam_202510.Dash.Fragments;

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

import com.example.proyecto_dam_202510.Dash.Adapters.IntercambioAdapter;
import com.example.proyecto_dam_202510.data.pojo.CromoPosesionAgrupadoIntercambio;
import com.example.proyecto_dam_202510.data.viewdata.Intercambio_vm;
import com.example.proyecto_dam_202510.databinding.FragmentIntercambioBinding;

import java.util.List;

public class IntercambioFragment extends Fragment {

    // <-- 1. Corrige la errata: biding -> binding
    private FragmentIntercambioBinding binding;
    private Intercambio_vm viewModel;
    private IntercambioAdapter adapter;

    // <-- 2. No necesitas estas variables aquí
    // MutableLiveData<List<CromoPosesionAgrupadoIntercambio>> cromoPosesionAgrupadoList;
    // RecyclerView recyclerView;

    public IntercambioFragment() {
        // Constructor vacío requerido
    }

    // (El método newInstance está bien si lo usas)

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // --- 3. INICIALIZA EL VIEWMODEL (¡SOLO AQUÍ!) ---
        // Esta es la forma correcta, vinculada al ciclo de vida del Fragment
        viewModel = new ViewModelProvider(this).get(Intercambio_vm.class);

        // --- 4. INICIALIZA EL ADAPTADOR ---
        // Puedes hacerlo aquí o en onViewCreated
        adapter = new IntercambioAdapter();

        // <-- 5. ERROR GRAVE: QUITAR ESTO DE AQUÍ
        // La vista (recyclerView) AÚN NO EXISTE. Esto causa un NullPointerException.
        // recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // --- 6. INFLA EL BINDING (¡DEBE SER LO PRIMERO!) ---
        binding = FragmentIntercambioBinding.inflate(inflater, container, false);

        // <-- 7. ERROR GRAVE: QUITAR ESTO
        // Esto crea una instancia NUEVA y huérfana del VM, rompiendo todo.
        // viewModel = new Intercambio_vm();

        // <-- 8. ERROR GRAVE: QUITAR ESTO DE AQUÍ
        // 'binding' acababa de ser inflado, pero si lo hacías antes, era null.
        // biding.recyclerView.setAdapter(adapter);

        // Devuelve la raíz del binding
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // --- 9. CONFIGURA EL RECYCLERVIEW (AQUÍ SÍ) ---
        // Ahora 'binding' y 'adapter' existen y son seguros de usar.
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        // --- 10. ¡OBSERVA EL LIVEDATA! (El paso que faltaba) ---
        // Le decimos al fragment que "escuche" los cambios en el LiveData del ViewModel
        viewModel.getCromoPosesionAgrupadoList().observe(getViewLifecycleOwner(), new Observer<List<CromoPosesionAgrupadoIntercambio>>() {
            @Override
            public void onChanged(List<CromoPosesionAgrupadoIntercambio> listaCromos) {
                // Cuando el ViewModel informe de datos nuevos...
                // ... los pasamos al adaptador.
                if (listaCromos != null) {
                    // (Asegúrate de que tu IntercambioAdapter tiene este método)
                    adapter.setCromos(listaCromos);
                }
            }
        });
    }

    // --- 11. BUENA PRÁCTICA: Limpiar el binding ---
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Evita fugas de memoria
    }
}