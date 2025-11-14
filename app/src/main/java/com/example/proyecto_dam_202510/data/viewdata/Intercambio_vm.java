package com.example.proyecto_dam_202510.data.viewdata;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyecto_dam_202510.Funciones;
import com.example.proyecto_dam_202510.data.pojo.CromoPosesion;
import com.example.proyecto_dam_202510.data.pojo.CromoPosesionAgrupadoIntercambio;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Intercambio_vm extends ViewModel {
    private int totalCromosSistema ;
    Map<CromoPosesionAgrupadoIntercambio,Integer> mapaCromoRepetidos = new HashMap<>();
    private final  MutableLiveData<List<CromoPosesionAgrupadoIntercambio>> cromoPosesionAgrupadoList = new MutableLiveData<>();

    public int getTotalCromosSistema() {
        return totalCromosSistema;
    }

    public MutableLiveData<List<CromoPosesionAgrupadoIntercambio>> getCromoPosesionAgrupadoList() {
        return cromoPosesionAgrupadoList;
    }

    public void setTotalCromosSistema(int totalCromosSistema) {
        this.totalCromosSistema = totalCromosSistema;
    }

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final MutableLiveData<List<CromoPosesion>> listaIntercambiosLiveData = new MutableLiveData<>();
    private ListenerRegistration intercambiosListener;
    private List<String> listaUsuariosColecciones = new ArrayList<>();
    List<CromoPosesionAgrupadoIntercambio> listaAgrupada;
    public List<String> getUsuariosColecciones() {
        return listaUsuariosColecciones;
    }
public void setUsuariosColecciones(List<String> listaUsuariosColecciones) {
        this.listaUsuariosColecciones = listaUsuariosColecciones;
    }

    public Intercambio_vm() {
        listaAgrupada = new ArrayList<>();
        cargaCartas();
    }

    private void cargaCartas() {

        /*uso de collectionGroup para no anidar consultas en firebase */

        listaAgrupada.clear();
        mapaCromoRepetidos.clear();

        listaUsuariosColecciones.clear();

        intercambiosListener = db.collectionGroup("cromosPosesion")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) { /* ... maneja error ... */ return; }
                        if (value == null) { return; }

                        totalCromosSistema = value.size();


                        Map<String, CromoPosesionAgrupadoIntercambio> mapaAgrupacion = new HashMap<>();

                        for (QueryDocumentSnapshot document : value) {

                            String userPosesion = document.getReference().getParent().getParent().getId();
                            CromoPosesionAgrupadoIntercambio cromoActual = document.toObject(CromoPosesionAgrupadoIntercambio.class);
                            String cromoPosesionId = document.getId();
                            String claveCromo = cromoActual.getNombre() + cromoActual.getNumero();

                            if (mapaAgrupacion.containsKey(claveCromo)) {
                                CromoPosesionAgrupadoIntercambio cromoExistente = mapaAgrupacion.get(claveCromo);

                                /*desnormalizo y añado para luego poder buscar facilmete*/
                                String coleccionIndex = userPosesion.substring(28,userPosesion.length());
                                String usuario = userPosesion.substring(0, 28);

                                cromoExistente.getUsuarioPoseedor().add(usuario);
                                cromoExistente.setColeccionId(coleccionIndex);
                                cromoExistente.setRepetida(cromoExistente.getRepetida() + 1);

                                /*aplicar el tipo dependiendo de las cartas repetidas. lo idel es usar percentiles*/
                                Funciones.actualizarCarta(coleccionIndex,cromoExistente.getNumero(),cromoExistente.getRepetida(),cromoPosesionId);




                            } else {

                                cromoActual.getUsuarioPoseedor().clear();
                                /*desnormalizo y añado para luego poder buscar facilmete*/
                                String coleccionIndex = userPosesion.substring(28,userPosesion.length());
                                String usuario = userPosesion.substring(0, 28);
                                cromoActual.getUsuarioPoseedor().add(usuario);
                                cromoActual.setColeccionId(coleccionIndex);
                                cromoActual.setRepetida(1);

                                Funciones.actualizarCarta(coleccionIndex,cromoActual.getNumero(),cromoActual.getRepetida(),cromoPosesionId);
                                mapaAgrupacion.put(claveCromo, cromoActual);

                            }

                        }

                        List<CromoPosesionAgrupadoIntercambio> listaFinal = new ArrayList<>(mapaAgrupacion.values());
                        cromoPosesionAgrupadoList.setValue(listaFinal);

                    }
                });



        }

    }
