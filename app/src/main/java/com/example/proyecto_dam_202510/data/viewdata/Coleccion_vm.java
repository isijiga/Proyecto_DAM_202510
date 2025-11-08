package com.example.proyecto_dam_202510.data.viewdata;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyecto_dam_202510.data.pojo.Coleccion;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Coleccion_vm extends ViewModel {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private final MutableLiveData<List<Coleccion>> listaColeccionesLiveData = new MutableLiveData<>();
    private ListenerRegistration coleccionListener;

    public LiveData<List<Coleccion>> getColecciones() {
        return listaColeccionesLiveData;
    }

    public Coleccion_vm() {
        cargaColecciones();
    }


    private void cargaColecciones() {

        coleccionListener = db.collection("colecciones")
                .addSnapshotListener((dato, error) -> {

            List<Coleccion> listaTemporal = new ArrayList<>();
            for (QueryDocumentSnapshot document : dato) {
                Coleccion coleccion = (Coleccion) document.toObject(Coleccion.class);
                coleccion.setId(document.getId());
                listaTemporal.add(coleccion);
            }
           listaColeccionesLiveData.setValue(listaTemporal);
        });
    }


    }
