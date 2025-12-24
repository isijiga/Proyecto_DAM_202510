package com.example.proyecto_dam_202510.data.pojo;


/**
 * Clase POJO de la estrucura que tiene un Cromo en la base de datos .
 */
public class Cromo {

    private String id;
    private String imagen;
    private String nombre;
    private String numero;
    private String tipo;
    private int valor;

    public Boolean getLoTengo() {
        return loTengo;
    }

    public void setLoTengo(Boolean loTengo) {
        this.loTengo = loTengo;
    }

    private Boolean loTengo;

    public Cromo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
