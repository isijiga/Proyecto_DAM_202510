package com.example.proyecto_dam_202510.data.viewdata;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyecto_dam_202510.data.pojo.Coleccion;
import com.example.proyecto_dam_202510.data.pojo.Cromo;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Cromo_vm extends ViewModel {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private final MutableLiveData<List<Cromo>> listaCromoLiveData = new MutableLiveData<>();
    private ListenerRegistration cromoListener;

    public LiveData<List<Cromo>> getCromos  () {
        return listaCromoLiveData;
    }

    public Cromo_vm(String coleccion) {
        cargaColecciones(coleccion);
    }


    private void cargaColecciones(String coleccion) {

        cromoListener = db.collection("colecciones")
                .document(coleccion).
                collection("cromos")

                        .addSnapshotListener((dato, error) -> {

            List<Cromo> listaTemporal = new ArrayList<>();
            for (QueryDocumentSnapshot document : dato) {
                Cromo cromo = (Cromo) document.toObject(Cromo.class);
                cromo.setId(document.getId());
                listaTemporal.add(cromo);
            }
           listaCromoLiveData.setValue(listaTemporal);
        });
    }


    }
