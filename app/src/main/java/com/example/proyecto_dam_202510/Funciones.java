package com.example.proyecto_dam_202510;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.proyecto_dam_202510.data.pojo.Coleccion;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Funciones {

    /*Crear usuario*/
    public static void crearUsuario(FirebaseUser user, Context context, FirebaseFirestore db) {

        Map<String, Object> userNew = new HashMap<>();
        userNew.put("username", user.getEmail());
        userNew.put("fechaCreacion", Timestamp.now());


        db.collection("users").document(user.getUid()).set(userNew)
                .addOnSuccessListener(aVoid -> {
                    Log.d("ColacTrade","Usuario creado: " + user.getEmail());

                })
                .addOnFailureListener(e -> {
                    Log.e("ColacTrade","Error al crear usuario: " + e.getMessage());
                });
    }

    /*Crear colección referenciando al usuario*/
    public static void crearColeccion(
            String idColeccion,
            String nombre,
            int totalCartas,
            int cartasPorSobre,
            String imagenPortada,
            String usuarioCreador) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference refUsuario = db.collection("users").document(usuarioCreador);
        Map<String, Object> coleccion = new HashMap<>();

        coleccion.put("nombre", nombre);
        coleccion.put("totalCartas", totalCartas);
        coleccion.put("cartasPorSobre", cartasPorSobre);
        coleccion.put("imagenPortada", imagenPortada);
        coleccion.put("usuarioCreador", refUsuario);

        db.collection("colecciones").document(idColeccion)
                .set(coleccion)
                .addOnSuccessListener(aVoid -> {
                    Log.d("ColacTrade", "Colección creada exitosamente: " + idColeccion);
                })
                .addOnFailureListener(e -> {
                    Log.e("ColacTrade", "Error al crear colección: " + idColeccion, e);
                });
    }

   /*Crear colección carta  dentro del documento de la colección*/
   public static void agregarCromo(
           String idColeccion,
           String idCromo,
           String nombre,
           int numero,
           String tipo,
           double valor,
           String imagen) {

       FirebaseFirestore db = FirebaseFirestore.getInstance();
       Map<String, Object> cromo = new HashMap<>();

       cromo.put("nombre", nombre);
       cromo.put("numero", numero);
       cromo.put("tipo", tipo);
       cromo.put("valor", valor);
       cromo.put("imagen", imagen);

       db.collection("colecciones").document(idColeccion)
               .collection("cromos").document(idCromo)
               .set(cromo)
               .addOnSuccessListener(aVoid -> {
                   Log.d("ColacTrade", "Carta agregada: " + idCromo);
               })
               .addOnFailureListener(e -> {
                   Log.e("ColacTrade", "Error al agregar carta: " + idCromo, e);
               });
   }

    public static void añadirColeccion(Coleccion coleccion) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        DocumentReference refUsuario = db.collection("users").document(user.getUid());
        DocumentReference refColeccion = db.collection("colecciones").document(coleccion.getId());

        Map<String, Object> coleccionMap = new HashMap<>();
        coleccionMap.put("user", refUsuario);
        coleccionMap.put("nombreColeccion", coleccion.getNombre());
        coleccionMap.put("progreso", 0);
        coleccionMap.put("inicioColeccion", Timestamp.now());
        coleccionMap.put("coleccion", refColeccion);

        db.collection("users_colecciones").add(coleccionMap)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("ColacTrade", "Coleccion añadida correctamente");
                    }
                });


    }
}
