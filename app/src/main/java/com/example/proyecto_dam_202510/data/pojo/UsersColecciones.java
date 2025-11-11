package com.example.proyecto_dam_202510.data.pojo;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;
import com.google.type.DateTime;

import java.util.Date;

public class UsersColecciones {

    private DocumentReference coleccion;
    private Date inicioColeccion;
    private String nombreColeccion;
    private int progreso;
    private int totalCromos;
    private String imagen;

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getTotalCromos() {
        return totalCromos;
    }

    public void setTotalCromos(int totalCromos) {
        this.totalCromos = totalCromos;
    }

    private DocumentReference user;

    @Exclude
    private String id;


    public UsersColecciones() {
    }

    public int getProgreso() {
        return progreso;
    }

    public void setProgreso(int progreso) {
        this.progreso = progreso;
    }

    public DocumentReference getColeccion() {
        return coleccion;
    }

    public void setColeccion(DocumentReference coleccion) {
        this.coleccion = coleccion;
    }

    public Date getInicioColeccion() {
        return inicioColeccion;
    }

    public void setInicioColeccion(Date inicioColeccion) {
        this.inicioColeccion = inicioColeccion;
    }

    public String getNombreColeccion() {
        return nombreColeccion;
    }

    public void setNombreColeccion(String nombreColeccion) {
        this.nombreColeccion = nombreColeccion;
    }

    public DocumentReference getUser() {
        return user;
    }

    public void setUsuario(DocumentReference user) {
        this.user = user;
    }

    public void setId(String id) {
    }
    public String getId() {
        return id;
    }
}
