package com.example.proyecto_dam_202510.data.viewdata;

import android.util.Log;
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


/**
 * VM  para obtener datos que son instanciados a partir de la clase CromoPosesion.
 */
public class CromoPosesion_vm extends ViewModel {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final MutableLiveData<List<CromoPosesion>> listaCromoPosesionLiveData = new MutableLiveData<>();
    private MutableLiveData<List<CromoPosesionAgrupado>> listaCromoPosesionAgrupadoLiveData = new MutableLiveData<>();

    public LiveData<List<CromoPosesionAgrupado>> getListaCromoPosesionAgrupado() {
        return listaCromoPosesionAgrupadoLiveData;
    }

    ;

    private ListenerRegistration cromoPosesionListener;

    public LiveData<List<CromoPosesion>> getCromosPosesion() {
        return listaCromoPosesionLiveData;
    }

    public CromoPosesion_vm(String userColeccion) {
        cargaCromosPosesion(userColeccion);
    }

    /**
     * Para obtener los cromos especificos del usuario logueado  se hace uso de este metodo
     * que contacta con la base de datos y se le pasa por parametro la concatenacion del usuario y el id de la coleccion
     * Como los cromos pueden estar repetidos, se crea una unica lista a partir de la lista original.
     * La lista agrupada contiene los cromos agrupados por numero y el numero de repeticiones.
     *
     * @param userColeccion concatenacion del usuario y el id de la coleccion
     */

    private void cargaCromosPosesion(String userColeccion) {

        cromoPosesionListener = db.collection("users_colecciones")
                .document(userColeccion).
                collection("cromosPosesion")
                .addSnapshotListener((dato, error) -> {
                    List<CromoPosesion> listaTemporal = new ArrayList<>();
                    /**
                     *añade cada cromo a la lista temporal y actualiza la lista de cromos.
                     */
                    for (QueryDocumentSnapshot document : dato) {
                        CromoPosesion cromo = (CromoPosesion) document.toObject(CromoPosesion.class);
                        cromo.setId(document.getId());
                        listaTemporal.add(cromo);
                    }
                    //listaCromoPosesionLiveData.setValue(listaTemporal);

                    /**
                     * Mapa hashmap para contar cada cromo individualmente
                     */
                    Map<CromoPosesion, Integer> mapConteo = new HashMap<>();

                    /**
                     * se itera por cada carta de listaTemporal y se cuenta el numero de veces que se repite
                      */
                    for (CromoPosesion carta : listaTemporal) {
                        CromoPosesion idCarta = carta;
                        int count = mapConteo.getOrDefault(idCarta, 0);
                        mapConteo.put(carta, count + 1);
                    }
                    List<CromoPosesionAgrupado> listaAgrupada = new ArrayList<>();

                    for (Map.Entry<CromoPosesion, Integer> carta : mapConteo.entrySet()) {
                        CromoPosesion name = carta.getKey();
                        int count = carta.getValue();
                        String id = name.getId();
                        String imagen = name.getImagen();
                        String nombre = name.getNombre();
                        String numero = name.getNumero();
                        String tipo = name.getTipo();
                        int valor = name.getValor();
                        String fechaAdquisicion = name.getFechaAdquisicion();
                        String coleccionId = name.getColeccionId();


                        listaAgrupada.add(new CromoPosesionAgrupado(
                                fechaAdquisicion, id, imagen, nombre, numero, count, tipo, valor, coleccionId));
                    }
                    /**
                     * se ordenan por el campo numero
                     */
                    listaAgrupada.sort((cromo1, cromo2) -> {
                        return cromo1.getNumero().compareTo(cromo2.getNumero());
                    });

                    Log.d("cromos", "cargaCromosPosesion: " + listaAgrupada.size());
                    /**
                     * actualiza el progreso de la coleccion
                     */
                    db.collection("users_colecciones").
                            document(userColeccion).update("progreso", listaAgrupada.size());

                    listaCromoPosesionAgrupadoLiveData.setValue(listaAgrupada);
                });
    }


}
