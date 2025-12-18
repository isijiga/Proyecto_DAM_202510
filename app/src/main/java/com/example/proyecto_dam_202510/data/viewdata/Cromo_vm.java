package com.example.proyecto_dam_202510.data.viewdata;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyecto_dam_202510.data.pojo.Cromo;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * ViewModel de la clase Cromo. Contacta con la base de datos y los añade a una lsita del tipo MutableLiveData.
 */
public class Cromo_vm extends ViewModel {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final MutableLiveData<List<Cromo>> listaCromoLiveData = new MutableLiveData<>();
    private ListenerRegistration cromoListener;

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

        cromoListener = db.collection("colecciones")
                .document(coleccion).
                collection("cromos")

                .addSnapshotListener((dato, error) -> {
                    if (error != null) {
                        Log.e("Firestore", "Error al escuchar cambios", error);
                        return;
                    }
                    if (dato != null) {
                        List<Cromo> listaTemporal = new ArrayList<>();
                        for (QueryDocumentSnapshot document : dato) {
                            Cromo cromo = (Cromo) document.toObject(Cromo.class);
                            cromo.setId(document.getId());
                            listaTemporal.add(cromo);
                        }
                        listaCromoLiveData.setValue(listaTemporal);
                    }

                });
    }


}
