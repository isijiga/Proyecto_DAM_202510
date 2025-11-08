package com.example.proyecto_dam_202510.data.viewdata;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyecto_dam_202510.data.pojo.CromoPosesion;
import com.example.proyecto_dam_202510.data.pojo.CromoPosesionAgrupado;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CromoPosesion_vm extends ViewModel {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final MutableLiveData<List<CromoPosesion>> listaCromoPosesionLiveData = new MutableLiveData<>();
    private MutableLiveData<List<CromoPosesionAgrupado>> listaCromoPosesionAgrupadoLiveData = new MutableLiveData<>();

    public LiveData<List<CromoPosesionAgrupado>> getListaCromoPosesionAgrupado(){
        return listaCromoPosesionAgrupadoLiveData;
        };

    private ListenerRegistration cromoPosesionListener;

    public LiveData<List<CromoPosesion>> getCromosPosesion  () {
        return listaCromoPosesionLiveData;
    }

    public CromoPosesion_vm(String userColeccion) {
        cargaCromosPosesion(userColeccion);
    }


    private void cargaCromosPosesion(String userColeccion) {

        cromoPosesionListener = db.collection("users_colecciones")
                .document(userColeccion).
                collection("cromosPosesion")

                        .addSnapshotListener((dato, error) -> {

            List<CromoPosesion> listaTemporal = new ArrayList<>();
            for (QueryDocumentSnapshot document : dato) {
                CromoPosesion cromo = (CromoPosesion) document.toObject(CromoPosesion.class);
                cromo.setId(document.getId());
                listaTemporal.add(cromo);
            }
           //listaCromoPosesionLiveData.setValue(listaTemporal);
            Map<CromoPosesion,Integer> mapConteo = new HashMap<>();
            for(CromoPosesion carta : listaTemporal ){
                CromoPosesion idCarta = carta;
                int count = mapConteo.getOrDefault(idCarta,0);
                mapConteo.put(carta,count+1);
            }
            List<CromoPosesionAgrupado> listaAgrupada = new ArrayList<>();

                for(Map.Entry<CromoPosesion, Integer> carta : mapConteo.entrySet())
                {
                    CromoPosesion name = carta.getKey();
                    int count = carta.getValue();
                    String id = name.getId();
                    String imagen = name.getImagen();
                    String nombre = name.getNombre();
                    String numero = name.getNumero();
                    List<String> tipo = name.getTipo();
                    int valor = name.getValor();

                    listaAgrupada.add(new CromoPosesionAgrupado(null,id,imagen,nombre,numero,count,tipo,valor));


                }

                listaCromoPosesionAgrupadoLiveData.setValue(listaAgrupada);


        });
    }


    }
