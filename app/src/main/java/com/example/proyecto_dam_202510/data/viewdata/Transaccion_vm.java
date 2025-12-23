package com.example.proyecto_dam_202510.data.viewdata;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyecto_dam_202510.data.pojo.Transaccion;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Transaccion_vm extends ViewModel {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseUser user = mAuth.getCurrentUser();
    private final MutableLiveData<List<Transaccion>> listaTransacciones = new MutableLiveData<>();
    private final MutableLiveData<List<Transaccion>> listaTransaccionesEmisor = new MutableLiveData<>();
    private ListenerRegistration transaccionListener;


    public MutableLiveData<List<Transaccion>> getListaTransacciones() {
        return listaTransacciones;
    }

    public MutableLiveData<List<Transaccion>> getListaTransaccionesEmisor() {
        return listaTransaccionesEmisor;
    }

    public Transaccion_vm() {

        cargardatos();

    }

    private void cargardatos() {

        transaccionListener = db.collection("transacciones").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                List<Transaccion> listaTemporal = new ArrayList<>();
                List<Transaccion> listaTemporalEmisor = new ArrayList<>();
                if (error != null) {
                    Log.e("Firestore", "Error al escuchar cambios", error);
                    return;
                }
                if (value != null) {
                for (QueryDocumentSnapshot document : value) {


                    Transaccion transaccion = document.toObject(Transaccion.class);
                    String usuarioReceptor = transaccion.getPedidoA();
                    String usuarioemisor = transaccion.getPedidoPor();

                    if (usuarioReceptor.equals(user.getUid())) {
                        listaTemporal.add(transaccion);
                        Log.d("transaccion", "has recibido una peticion de :" + transaccion.getPedidoPor() + " por la carta " + document.get("nombre"));
                    }
                    if (usuarioemisor.equals(user.getUid())) {
                        listaTemporalEmisor.add(transaccion);
                        Log.d("transaccion", "has enviado una peticion a :" + transaccion.getPedidoA() + " por la carta " + document.get("nombre"));

                    }

                }

                }
                listaTransaccionesEmisor.setValue(listaTemporalEmisor);
                listaTransacciones.setValue(listaTemporal);
            }
        });

    }

    public void aceptarPeticion(Transaccion transaccion, String mensaje) {
        String idTransaccion = transaccion.getIdTransaccion();
        db.collection("transacciones").document(idTransaccion).update("mensajeRespuesta", mensaje);
        db.collection("transacciones").document(idTransaccion).update("estado", "ACEPTADA. Pdte Envio");


    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public void enviarPeticion(Transaccion transaccion) {
        String idTransaccion = transaccion.getIdTransaccion();
        db.collection("transacciones").document(idTransaccion).update("estado", "ENVIADA");
       /*20251214  IMPLEMENTAR LOGICA DEL CAMBIO DE CROMO ... ELIMINAR EN UN UNO.. AÑADIR EN OTRO*/

       db.collection("users_colecciones").
               document(user.getUid()+transaccion.getColeccionId()).
               collection("cromosPosesion").document(transaccion.getId())
               .delete();

        /*insertar en la coleccion de la transaccion*/

        /*insertar en la coleccion de la transaccion
        db.collection("users_colecciones").document(transaccion.getPedidoPor()+transaccion.getColeccionId()).
                collection("cromosPosesion").document()
               .set(transaccion);

       /*por ultimo borrar la transacion.
        db.collection("transacciones").document(transaccion.getIdTransaccion()).delete();
        */






    }
}
