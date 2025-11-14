package com.example.proyecto_dam_202510;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.proyecto_dam_202510.data.pojo.Coleccion;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Funciones {
    public interface OnUploadCallback {
        void onSuccess(String imageUrl); // Notifica con la URL
        void onFailure(Exception e);     // Notifica si hay error
    }
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
           String numero,
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
       cromo.put("repetida", 0);
       cromo.put("id", idCromo) ;

               db.collection("colecciones").document(idColeccion)
               .collection("cromos")
               .document(idCromo).set(cromo)
               .addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void unused) {
                  Log.d("ColacTrade", "DocumentSnapshot written with ID: " + numero) ;
                   }
               });

   }

    public static void agregarCromoPosesion(
            String idColeccion,
            String idCromo,
            String nombre,
            String numero,
            String tipo,
            double valor,
            String imagen,
            String fechaAdquisicion
            ) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        Map<String, Object> cromo = new HashMap<>();

        cromo.put("coleccionId",idColeccion);
        cromo.put("nombre", nombre);
        cromo.put("numero", numero);
        cromo.put("tipo", tipo);
        cromo.put("valor", valor);
        cromo.put("imagen", imagen);
        cromo.put("fechaAdquisicion", fechaAdquisicion);

        db.collection("users_colecciones").document(user.getUid()+idColeccion)
                .collection("cromosPosesion")
                .add(cromo)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("trade", "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("trade", "Error adding document", e);
                    }
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
        coleccionMap.put("totalCromos", coleccion.getTotalCartas());
        coleccionMap.put("imagen", coleccion.getImagenPortada());





        db.collection("users_colecciones").document(refUsuario.getId()+coleccion.getNombre()).set(coleccionMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("ColacTrade", "Documento creado/actualizado con ID: " + coleccion.getNombre());
                    }
                });


    }

    public static void borrarCarta(String documento, String coleccion,Context contexto){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        db.collection("users_colecciones").document(user.getUid()+coleccion)
                .collection("cromosPosesion").document(documento).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(contexto, "Carta borrada", Toast.LENGTH_LONG).show();
                    }
                });






    }

    public static String ahora() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/YY HH:mm");
        Date date = new Date();
       return dateFormat.format(date) ;

    }

    public static void actualizarCarta(String coleccionIndex, String numero, int repetida,String cromoColeccionId) {
       String tipo;

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
       FirebaseFirestore db = FirebaseFirestore.getInstance();


        if (repetida == 1) {
            tipo = "Único";
        } else if (repetida >= 2 && repetida <= 5) {
            tipo = "Muy raro";
        } else if (repetida >= 6 && repetida <= 10) {
            tipo = "Raro";
        } else if (repetida >= 11 && repetida <= 25) {
            tipo = "Poco común";
        } else {
            tipo = "Común";
        }

       db.collection("colecciones")
                .document(coleccionIndex)
                .collection("cromos")
                .document(numero)
                .update("tipo",tipo);

        db.collection("users_colecciones")
                .document(user.getUid()+coleccionIndex)
                .collection("cromosPosesion")
                .document(cromoColeccionId)
                .update("tipo",tipo);
    }





}