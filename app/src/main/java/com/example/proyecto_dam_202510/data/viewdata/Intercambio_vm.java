package com.example.proyecto_dam_202510.data.viewdata;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.proyecto_dam_202510.Funciones;
import com.example.proyecto_dam_202510.data.pojo.CromoPosesion;
import com.example.proyecto_dam_202510.data.pojo.CromoPosesionAgrupadoIntercambio;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 *Clase ViewModel que contacta con la base de datos y descarga los cromos en posesion de cada user para mostrarlo
 * por el tablon.
 */
public class Intercambio_vm extends ViewModel {
    private int totalCromosSistema;
    private String coleccionid;
    private final MutableLiveData<List<String>> listaColecciones = new MutableLiveData<>();
    Map<CromoPosesionAgrupadoIntercambio, Integer> mapaCromoRepetidos = new HashMap<>();
    private final MutableLiveData<List<CromoPosesionAgrupadoIntercambio>> cromoPosesionAgrupadoList = new MutableLiveData<>();
    private final Set<String> clavesCromosUsuarioActual = new HashSet<>();
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
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseUser user = mAuth.getCurrentUser();

    private final MutableLiveData<List<CromoPosesion>> listaIntercambiosLiveData = new MutableLiveData<>();
    private ListenerRegistration intercambiosListener;
    private ListenerRegistration coleccionesListener;
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
        cargaListaColecciones();
    }

    private void cargaListaColecciones() {
        /*coleccionesListener = db.collection("colecciones").addSnapshotListener(new EventListener<QuerySnapshot>() {*/
        /*Solo las mias*/
        coleccionesListener = db.collection("users_colecciones").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
             if (error !=null){
                 listaColecciones.setValue(new ArrayList<>());
                 return;
             }

             if (value == null){
                 listaColecciones.setValue(new ArrayList<>());

             }
                List<String> nombresColecciones = new ArrayList<>();

                for (QueryDocumentSnapshot document : value) {
                    String id = document.getId();
                    String coleccionIndex = id.substring(28, id.length());
                    String usuario = id.substring(0, 28);
                    if (user != null && user.getUid().equals(usuario)) {

                        nombresColecciones.add(coleccionIndex);
                    }


                }
                listaColecciones.setValue(nombresColecciones);
            }
        });

    }

    public MutableLiveData<List<String>> getListaColecciones() {
        return listaColecciones;
    }
    private void cargaCartas() {

        /*uso de collectionGroup para no anidar consultas en firebase */
        listaAgrupada.clear();
        mapaCromoRepetidos.clear();
        listaUsuariosColecciones.clear();
        clavesCromosUsuarioActual.clear();
        intercambiosListener = db.collectionGroup("cromosPosesion")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            return;
                        }
                        if (value == null) {
                            return;
                        }

                        totalCromosSistema = value.size();


                        Map<String, CromoPosesionAgrupadoIntercambio> mapaAgrupacion = new HashMap<>();

                        for (QueryDocumentSnapshot document : value) {
                            String userPosesion = document.getReference().getParent().getParent().getId();
                            CromoPosesionAgrupadoIntercambio cromoActual = document.toObject(CromoPosesionAgrupadoIntercambio.class);
                            String cromoPosesionId = document.getId();
                            /*desnormalizo y añado para luego poder buscar facilmete*/
                            String coleccionIndex = userPosesion.substring(28, userPosesion.length());
                            String usuario = userPosesion.substring(0, 28);
                            /*clave unica para crear la lista de cromos, de esta manera los cromos repetidos se agrupan*/
                            String claveCromo = usuario+cromoActual.getNombre() + cromoActual.getNumero();

                           if(user!=null && user.getUid().equals(usuario)){
                               clavesCromosUsuarioActual.add(claveCromo);

                            }
                            if (mapaAgrupacion.containsKey(claveCromo)) {
                                CromoPosesionAgrupadoIntercambio cromoExistente = mapaAgrupacion.get(claveCromo);
                                cromoExistente.getUsuarioPoseedor().add(usuario);
                                cromoExistente.setColeccionId(coleccionIndex);
                                cromoExistente.setRepetida(cromoExistente.getRepetida() + 1);

                                /*aplicar el tipo dependiendo de las cartas repetidas. lo idel es usar percentiles*/
                              // Funciones.actualizarCarta(coleccionIndex, cromoExistente.getNumero(), cromoExistente.getNombre(), cromoExistente.getRepetida(), cromoPosesionId);


                            } else {

                                cromoActual.getUsuarioPoseedor().clear();
                                /*desnormalizo y añado para luego poder buscar facilmete*/
                                //String coleccionIndex = userPosesion.substring(28, userPosesion.length());
                                //String usuario = userPosesion.substring(0, 28);

                                cromoActual.getUsuarioPoseedor().add(usuario);
                                cromoActual.setColeccionId(coleccionIndex);
                                cromoActual.setRepetida(1);

                               //Funciones.actualizarCarta(coleccionIndex, cromoActual.getNumero(), cromoActual.getNombre(), cromoActual.getRepetida(), cromoPosesionId);
                                mapaAgrupacion.put(claveCromo, cromoActual);

                            }

                        }

                        List<CromoPosesionAgrupadoIntercambio> listaFinal = new ArrayList<>(mapaAgrupacion.values());
                        List<CromoPosesionAgrupadoIntercambio> listaFinalFiltrada = new ArrayList<>();



                        for (CromoPosesionAgrupadoIntercambio cromo : listaFinal) {

                                if (cromo.getColeccionId().equals(coleccionid)) {
                                    if (cromo.getRepetida()>1) {
                                        String claveCromo = user.getUid()+cromo.getNombre() + cromo.getNumero();
                                        if (!clavesCromosUsuarioActual.contains(claveCromo)) {
                                            listaFinalFiltrada.add(cromo);
                                        }
                                    }
                            }

                        }

                        cromoPosesionAgrupadoList.setValue(listaFinalFiltrada);

                    }
                });


    }

    public void setColeccionRecarga(String seleccion) {
        coleccionid = seleccion;
        cargaCartas();
    }
}
