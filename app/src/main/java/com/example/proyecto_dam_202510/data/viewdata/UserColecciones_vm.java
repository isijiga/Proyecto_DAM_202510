package com.example.proyecto_dam_202510.data.viewdata;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.proyecto_dam_202510.data.pojo.UsersColecciones;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;


/**
 * Clase que sirve para obtener de la base de datos
 * datos de las colecciones que tiene cada usuario.
 */
public class UserColecciones_vm extends ViewModel {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private final MutableLiveData<List<UsersColecciones>> listaUsersColeccionesLiveData = new MutableLiveData<>();
    private String uid;
    private DocumentReference userRef;
    private DocumentReference colecRef;
    private ListenerRegistration userscoleccionesListener;

    public LiveData<List<UsersColecciones>> getUsersColecciones() {
        return listaUsersColeccionesLiveData;
    }

    public UserColecciones_vm() {
        cargaColecciones();
    }


    private void cargaColecciones() {
        uid = user.getUid();
        userRef = db.collection("users").document(uid);

        userscoleccionesListener = db.collection("users_colecciones")
                .whereEqualTo("user", userRef).addSnapshotListener((dato, error) -> {

                    List<UsersColecciones> listaTemporal = new ArrayList<>();
                    if (error != null) {
                        Log.e("Firestore", "Error al escuchar cambios", error);
                        return;
                    }
                    if (dato != null) {
                    for (QueryDocumentSnapshot document : dato) {


                        UsersColecciones usercoleccion = (UsersColecciones) document.toObject(UsersColecciones.class);
                        usercoleccion.setId(document.getId());
                        colecRef = document.getDocumentReference("coleccion");

                        listaTemporal.add(usercoleccion);
                    }}
                    listaUsersColeccionesLiveData.setValue(listaTemporal);
                });
    }


}
