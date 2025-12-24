package com.example.proyecto_dam_202510.data.viewdata;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyecto_dam_202510.data.pojo.Cromo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * ViewModel de la clase Cromo. Contacta con la base de datos y los añade a una lsita del tipo MutableLiveData.
 */
public class Cromo_vm extends ViewModel {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final MutableLiveData<List<Cromo>> listaCromoLiveData = new MutableLiveData<>();
    private ListenerRegistration cromoListener;
    private List<Cromo> listaCromos = new ArrayList<>();
    private Set<String> setCromosPosesionIds = new HashSet<>();
    private ListenerRegistration cromosPosesionListener;
    public LiveData<List<Cromo>> getCromos() {
        return listaCromoLiveData;
    }

    /**
     * Constructor que cuando es instanciado desde el Adapter llama directamente al metodo que carga las colecciones.
     *
     * @param coleccion el nombre de la colección para pasarselo por metodo a la base de datos.
     */
    public Cromo_vm(String coleccion) {

        cargaColecciones(coleccion);
    }


    /**
     * Metodo principal del VM donde se contacta con la base de datos y actualiza la lista de cromos.
     *
     * @param coleccion
     */
    private void cargaColecciones(String coleccion) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        cromosPosesionListener = db.collection("users_colecciones").document(userId+coleccion)
                .collection("cromosPosesion").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                   setCromosPosesionIds.clear();
                        if (value != null) {
                            for (QueryDocumentSnapshot document : value) {
                                String numero = document.get("numero").toString();
                                String nombre = document.get("nombre").toString();
                                setCromosPosesionIds.add(numero+nombre);
                            }
                            actualizarLista();
                        }


                    }
                });





        cromoListener = db.collection("colecciones")
                .document(coleccion).
                collection("cromos")

                .addSnapshotListener((dato, error) -> {
                    if (error != null) {
                        Log.e("Firestore", "Error al escuchar cambios", error);
                        return;
                    }
                    if (dato != null) {
                        listaCromos.clear();
                        for (QueryDocumentSnapshot document : dato) {
                            Cromo cromo = (Cromo) document.toObject(Cromo.class);
                            cromo.setId(document.getId());
                            listaCromos.add(cromo);

                        }
                        actualizarLista();

                    }

                });
    }

    private void actualizarLista() {
        List<Cromo> listaParaFragment = new ArrayList<>();
        for (Cromo cromo : listaCromos) {
            if (setCromosPosesionIds.contains(cromo.getId())) {
                cromo.setLoTengo(true);
            } else {
                cromo.setLoTengo(false);
            }
            listaParaFragment.add(cromo);
        }
        listaCromoLiveData.setValue(listaParaFragment);


    }


}
