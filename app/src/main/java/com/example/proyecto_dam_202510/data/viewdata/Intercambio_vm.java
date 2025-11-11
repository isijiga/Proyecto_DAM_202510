package com.example.proyecto_dam_202510.data.viewdata;

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

                        // --- 1. CREA EL MAPA DE AGRUPACIÓN AQUÍ DENTRO ---
                        // Clave: String (nombre+numero), Valor: el objeto cromo que acumula datos
                        Map<String, CromoPosesionAgrupadoIntercambio> mapaAgrupacion = new HashMap<>();

                        // --- 2. UN SOLO BUCLE PARA PROCESAR TODO ---
                        for (QueryDocumentSnapshot document : value) {
                            String userPosesion = document.getReference().getParent().getParent().getId();

                            // Convierte el documento al POJO
                            CromoPosesionAgrupadoIntercambio cromoActual = document.toObject(CromoPosesionAgrupadoIntercambio.class);

                            // --- 3. CREA LA CLAVE ÚNICA (como en tu 'equals') ---
                            String claveCromo = cromoActual.getNombre() + cromoActual.getNumero();

                            if (mapaAgrupacion.containsKey(claveCromo)) {
                                // --- SI YA LO TENEMOS EN EL MAPA ---
                                CromoPosesionAgrupadoIntercambio cromoExistente = mapaAgrupacion.get(claveCromo);

                                // 1. Añade el NUEVO poseedor a la lista del cromo EXISTENTE


                                cromoExistente.getUsuarioPoseedor().add(userPosesion.substring(0, 28));

                                // 2. Incrementa su contador
                                cromoExistente.setRepetida(cromoExistente.getRepetida() + 1);

                            } else {
                                // --- SI ES LA PRIMERA VEZ QUE VEMOS ESTE CROMO ---

                                // 1. Limpia la lista de poseedores (puede tener datos basura del 'toObject')
                                cromoActual.getUsuarioPoseedor().clear();

                                // 2. Añade el *primer* poseedor
                                //convierno la ref por el correo


                                cromoActual.getUsuarioPoseedor().add(userPosesion.substring(0, 28));

                                // 3. Establece el contador en 1
                                cromoActual.setRepetida(1);

                                // 4. Añade el cromo (como VALOR) al mapa
                                mapaAgrupacion.put(claveCromo, cromoActual);
                            }
                        }

                        // --- 4. PREPARA LA LISTA FINAL ---
                        List<CromoPosesionAgrupadoIntercambio> listaFinal = new ArrayList<>(mapaAgrupacion.values());

                        // --- 5. ACTUALIZA EL LIVEDATA ---
                        cromoPosesionAgrupadoList.setValue(listaFinal);

                    }
                });



        }

    }
