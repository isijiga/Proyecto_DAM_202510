package com.example.proyecto_dam_202510.data.pojo;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;
import java.util.List;


/**
 * Clase POJO que hace de 'molde' para la clase Coleccion.
 * Se establecen las propiedades que se van a guardar en la base de datos.
 */
public class Coleccion {

    private int cartasporSobre;
    private String imagenPortada;
    private String nombre;
    private int totalCartas;
    private DocumentReference usuarioCreador;


    @Exclude
    private String id;

    @Exclude
    private List<Cromo> cromos;

    public Coleccion() {

    }

    public List<Cromo> getCromos() {
        return cromos;
    }

    public void setCromos(List<Cromo> cromos) {
        this.cromos = cromos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCartasporSobre() {
        return cartasporSobre;
    }

    public String getImagenPortada() {
        return imagenPortada;
    }

    public String getNombre() {
        return nombre;
    }

    public int getTotalCartas() {
        return totalCartas;
    }

    public DocumentReference getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setCartasporSobre(int cartasporSobre) {
        this.cartasporSobre = cartasporSobre;
    }

    public void setImagenPortada(String imagenPortada) {
        this.imagenPortada = imagenPortada;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTotalCartas(int totalCartas) {
        this.totalCartas = totalCartas;
    }

    public void setUsuarioCreador(DocumentReference usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }
}
