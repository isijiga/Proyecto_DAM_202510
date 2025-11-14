package com.example.proyecto_dam_202510.data.viewdata;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.FirebaseFirestore;

public class Estadisticas_vm extends ViewModel {


    private FirebaseFirestore db;
    MutableLiveData<Long> countColecciones = new MutableLiveData<>(0L);
    MutableLiveData<Long> countUsuarios = new MutableLiveData<>(0L);
    MutableLiveData<Long> countUsers_Colecciones = new MutableLiveData<>(0L);

    public MutableLiveData<Long> getCountUsuarios() {
        return countUsuarios;
    }

    public MutableLiveData<Long> getCountUsers_Colecciones() {
        return countUsers_Colecciones;
    }

    public MutableLiveData<Long> getCountColecciones() {
        return countColecciones;
    }


    public void cargarEstadisticas(){
        db = FirebaseFirestore.getInstance();
        AggregateQuery conteo_Users = db.collection("users").
                count();
        AggregateQuery conteo_Coleccioens = db.collection("colecciones").
                count();
        AggregateQuery conteo_Users_Colecciones = db.collection("users_colecciones").
                count();

        conteo_Users.get(AggregateSource.SERVER).addOnSuccessListener(new OnSuccessListener<AggregateQuerySnapshot>() {
            @Override
            public void onSuccess(AggregateQuerySnapshot aggregateQuerySnapshot) {
                countUsuarios.setValue(aggregateQuerySnapshot.getCount());
        }});
        conteo_Coleccioens.get(AggregateSource.SERVER).addOnSuccessListener(new OnSuccessListener<AggregateQuerySnapshot>() {
            @Override
            public void onSuccess(AggregateQuerySnapshot aggregateQuerySnapshot) {
                countColecciones.setValue(aggregateQuerySnapshot.getCount());
            }});
        conteo_Users_Colecciones.get(AggregateSource.SERVER).addOnSuccessListener(new OnSuccessListener<AggregateQuerySnapshot>() {
            @Override
            public void onSuccess(AggregateQuerySnapshot aggregateQuerySnapshot) {
                countUsers_Colecciones.setValue(aggregateQuerySnapshot.getCount());
            }});

    }

}
