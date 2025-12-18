package com.example.proyecto_dam_202510;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.proyecto_dam_202510.data.pojo.Coleccion;
import com.example.proyecto_dam_202510.data.pojo.CromoPosesionAgrupadoIntercambio;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;


/**
 * @author Isidoro Jiménez García
 * Clase  que se encarga haacer de intermediario con la grabación de Firebase. De esta manera está centralizado todas
 * las operaciones de escritura en esta clase.
 */
public class Funciones {
    private static final String CORREO_EXP =
            "^[^@]+@[^@]+\\.[a-zA-Z]{2,}$";
    /**
     * @brief Metodo para crear usuario en la coleccion /users de Firebase.
     *
     * @param user    FirebaseUser con el email del usuario.
     * @param context Contexto de la aplicación para lanzar Toast.
     * @param db      Instancia de FirebaseFirestore para establecer la conexion con la base de datos.
     */
    public static void crearUsuario(FirebaseUser user, Context context, FirebaseFirestore db) {
        Map<String, Object> userNew = new HashMap<>();
        userNew.put("username", user.getEmail());
        userNew.put("fechaCreacion", Timestamp.now());
        db.collection("users").document(user.getUid()).set(userNew)
                .addOnSuccessListener(aVoid -> {
                    Log.d("ColacTrade", "Usuario creado: " + user.getEmail());
                })
                .addOnFailureListener(e -> {
                    Log.e("ColacTrade", "Error al crear usuario: " + e.getMessage());
                });
    }

    /**
     * Este metodo es el encargado de crear la coleccion dentro de la bd.
     *
     * @param idColeccion id unico de la coleccion
     * @param nombre nombre de la coleccion
     * @param totalCartas El numero total de cartas para efectos estadisticos.
     * @param cartasPorSobre El numero de cartas por sobre para calcular el valor todal.
     * @param coste Coste del sobre para calcular el precio / unitario.
     * @param imagenPortada Imagen de portada de la coleccion.
     * @param usuarioCreador Referencia a la ruta del user creador.
     */
    public static void crearColeccion(
            String idColeccion,
            String nombre,
            int totalCartas,
            int cartasPorSobre,
            double coste,
            String imagenPortada,
            String usuarioCreador) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference refUsuario = db.collection("users").document(usuarioCreador);
        Map<String, Object> coleccion = new HashMap<>();
        coleccion.put("nombre", nombre);
        coleccion.put("totalCartas", totalCartas);
        coleccion.put("cartasPorSobre", cartasPorSobre);
        coleccion.put("coste", coste);
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

    /**
     * Metodo para añadir un cromo de una coleccion especifica a la base de datos.
     * @param idColeccion id unico de la coleccion
     * @param idCromo   id unico del cromo
     * @param nombre    nombre del cromo
     * @param numero numero del cromo, siendo por defecto el nº de cromo seguido de los 3 primeros caracteres
     *              del equipo en caso de que no tengan numeros consecutivos.
     * @param tipo el tipo de cromo siendo las posibilidaddes:  "Único", "Muy raro", "Raro", "Poco común", "Común".
     * @param valor el valor del cromo en euros.
     * @param imagen Ruta de Firestore de la imagen del cromo Jpg .
     */
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
        cromo.put("id", idCromo);

        db.collection("colecciones").document(idColeccion)
                .collection("cromos")
                .document(numero + nombre).set(cromo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("ColecTrade", "Escritura correcta con id...: " + numero);
                    }
                });
    }

    /**
     * Metodo para añadir un cromo especifico de una coleccion especifica a la coleccion users_colecciones.
     * @param idColeccion id unico de la coleccion
     * @param idCromo id unico del cromo
     * @param nombre nombre del cromo
     * @param numero numero del cromo
     * @param tipo tipo del cromo
     * @param valor valor del cromo
     * @param imagen ruta de la imagen del cromo
     * @param fechaAdquisicion fecha de adquisicion del cromo
     */
    public static void agregarCromoPosesion(
            String idColeccion,
            String idCromo,
            String nombre,
            String numero,
            String tipo,
            double valor,
            String imagen,
            Date fechaAdquisicion
    ) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        Map<String, Object> cromo = new HashMap<>();
        CollectionReference coleccionRef = db.collection("users_colecciones")
                .document(user.getUid() + idColeccion)
                .collection("cromosPosesion");
        DocumentReference cromoPosesionRef = coleccionRef.document();
        String idcromoPosesion = cromoPosesionRef.getId();

        cromo.put("id", idcromoPosesion);
        cromo.put("coleccionId", idColeccion);
        cromo.put("nombre", nombre);
        cromo.put("numero", numero);
        cromo.put("tipo", tipo);
        cromo.put("valor", valor);
        cromo.put("imagen", imagen);
        cromo.put("fechaAdquisicion", fechaAdquisicion);

        cromoPosesionRef.set(cromo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("cromoPosesion", "Escritura correcta con  ID: " + idcromoPosesion);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("ColecTrade", "Error al añadir el documento", e);
                    }
                });
    }

    /**
     * Metodo para añadir una coleccion a users_colecciones, el user será el id del usuario en sistema.
     * @param coleccion es el id de la coleccion que se va a añadir.
     *
     */
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


        db.collection("users_colecciones").document(refUsuario.getId() + coleccion.getNombre()).set(coleccionMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("ColecTrade", "Documento creado/actualizado con ID: " + coleccion.getNombre());
                    }
                });


    }

    /**
     * Metodo para borrar un cromo de la base de datos.
     * @param documento id unico del cromo
     * @param coleccion is de la coleccion
     * @param contexto  recibimos el contexto para mostrar pantalla Toast.
     */
    public static void borrarCarta(String documento, String coleccion, Context contexto) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        db.collection("users_colecciones").document(user.getUid() + coleccion)
                .collection("cromosPosesion").document(documento).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(contexto, "Carta borrada", Toast.LENGTH_LONG).show();
                    }
                });


    }

    /**
     * Metodo para obtener la fecha actual en formato especifico (dd/MM/YY HH:mm).
     * @return fecha y hora  actual en formato String.
     */
    public static String ahora() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/YY HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * Metodo para actualizar el tipo de un cromo en la base de datos.
     * @param coleccionIndex id de la coleccion
     * @param numero numero del cromo
     * @param nombre nombre del cromo
     * @param repetida numero de veces que se repite el cromo
     * @param cromoColeccionId id del cromo en la coleccion.
     */
    public static void actualizarCarta(String coleccionIndex, String numero, String nombre, int repetida, String cromoColeccionId) {
        String tipo;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String documento = numero + nombre;
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
/**
 * actualizamos tanto en coleccion, como en users_colecciones
 */
        db.collection("colecciones")
                .document(coleccionIndex)
                .collection("cromos")
                .document(documento)
                .update("tipo", tipo);

        db.collection("users_colecciones")
                .document(user.getUid() + coleccionIndex)
                .collection("cromosPosesion")
                .document(cromoColeccionId)
                .update("tipo", tipo);
    }
    /**
     * comprueba si el correo es valido
     * @param correo correo electronico
     * @return
     */
    public static boolean comprobarCorreoValido(String correo){

                    if (correo == null) {
                return false;
            }
            return correo.matches(CORREO_EXP);


    }
    public static Map<String,Object> difDias(@NonNull Date fechaInicio) throws ParseException {
        Map<String,Object> mapa = new HashMap<>();
        SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String fechaFormateada = formatoSalida.format(fechaInicio);
        String hoyFormateada = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        Date fechaIni = formatoSalida.parse(fechaFormateada);
        Date fechaHoy = formatoSalida.parse(hoyFormateada);
        long diffMillis = fechaHoy.getTime() - fechaIni.getTime();
        long diffDias = diffMillis / (24 * 60 * 60 * 1000);
        mapa.put("fechaInicio",fechaIni.getTime());
        mapa.put("diffDias",diffDias);
        return mapa;
    }

    public static void pedirCarta(CromoPosesionAgrupadoIntercambio cromo, Context context) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        final String emailPedidoPor = user.getEmail();
        final String refPedidoPor = user.getUid();
        CollectionReference transacionRef = db.collection("transacciones");

        Map<String,Object> cromoPedidoMap = new HashMap<>();
        cromoPedidoMap.put("imagen",cromo.getImagen());
        cromoPedidoMap.put("nombre",cromo.getNombre());
        cromoPedidoMap.put("numero",cromo.getNumero());
        cromoPedidoMap.put("coleccionId",cromo.getColeccionId());
        cromoPedidoMap.put("pedidoPor",refPedidoPor);
        cromoPedidoMap.put("fechaAdquisicion",Timestamp.now());
        cromoPedidoMap.put("estado","pendiente");
        cromoPedidoMap.put("emailPedidoPor", emailPedidoPor);
        cromoPedidoMap.put("id",cromo.getId());
        List<Task<Void>> tasks = new ArrayList<>();

        //for(int i=0;i<cromo.getUsuarioPoseedor().size();i++){
        Set<String> poseedoresUnicos = cromo.getUsuarioPoseedor();
        for(final String userIdPedidoA : poseedoresUnicos){
//            final String userIdPedidoA = cromo.getUsuarioPoseedor().get(i);
            Task<DocumentSnapshot> mailTask = db.collection("users").document(userIdPedidoA).get();

            Task<Void> transaccionTask = mailTask.continueWithTask(new Continuation<DocumentSnapshot, Task<Void>>() {
                @Override
                public Task<Void> then(@NonNull Task<DocumentSnapshot> task) throws Exception {
                    DocumentSnapshot documentSnapshot = task.getResult();

                    String emailPedidoPor = (String) documentSnapshot.get("username");
                    Log.d("transaccion", "Email Receptor Obtenido: " + emailPedidoPor);
                    Map<String, Object> cromoPedido = new HashMap<>(cromoPedidoMap);
                    cromoPedido.put("pedidoA",userIdPedidoA);
                    cromoPedido.put("emailPedidoA",emailPedidoPor);
                    DocumentReference nuevaTransaccionRef = transacionRef.document();
                    String idTransaccion = nuevaTransaccionRef.getId();
                    cromoPedido.put("idTransaccion", idTransaccion);
                    return transacionRef.document(idTransaccion).set(cromoPedido);

                }
            });

            tasks.add(transaccionTask);

        }
        Tasks.whenAll(tasks).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Todas las cartas han sido pedidas con éxito.", Toast.LENGTH_LONG).show();
            }
        });



    }
}