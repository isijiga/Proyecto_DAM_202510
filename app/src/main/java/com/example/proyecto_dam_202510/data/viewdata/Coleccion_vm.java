package com.example.proyecto_dam_202510.data.viewdata;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.proyecto_dam_202510.data.pojo.Coleccion;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;


/**
 * Para seguir el patron MVVM recomendado por Google es necesario crear una clase que herede de ViewModel.
 * Esta clase es la encargada de llamar a la base de datos y crear una lista de instancias de la clase Coleccion
 */
public class Coleccion_vm extends ViewModel {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final MutableLiveData<List<Coleccion>> listaColeccionesLiveData = new MutableLiveData<>();
    private ListenerRegistration coleccionListener;

    public LiveData<List<Coleccion>> getColecciones() {
        return listaColeccionesLiveData;
    }


    /**
     * Constructor que cuando es instanciado desde el Adapter llama directamente al metodo que carga las colecciones.
     */
    public Coleccion_vm() {
        cargaColecciones();
    }

    /**
     * metodo principal del View model donde se conecta con la base de datos de firebase y
     * actualiza la lista de colecciones.
     */
    private void cargaColecciones() {

        coleccionListener = db.collection("colecciones")
                .addSnapshotListener((dato, error) -> {
                    if (error != null) {
                        Log.e("Firestore", "Error al escuchar cambios", error);
                        return;
                    }
                    List<Coleccion> listaTemporal = new ArrayList<>();
                    if (dato != null) {
                        for (QueryDocumentSnapshot document : dato) {
                            Coleccion coleccion = (Coleccion) document.toObject(Coleccion.class);
                            coleccion.setId(document.getId());
                            listaTemporal.add(coleccion);
                        }
                        listaColeccionesLiveData.setValue(listaTemporal);
                    }
                });
    }


}
