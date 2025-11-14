package com.example.proyecto_dam_202510.data.pojo;

import java.util.Date;
import java.util.List;

public class CromoPosesionAgrupado {

    private String id;
    private int repetida;
    private String imagen;
    private String nombre;
    private String numero;
    private String tipo;
    private int valor;
    private String fechaAdquisicion;
    private String coleccionId;

    public String getColeccionId() {
        return coleccionId;
    }

    public void setColeccionId(String coleccionId) {
        this.coleccionId = coleccionId;
    }



    public CromoPosesionAgrupado(String fechaAdquisicion,
                                 String id, String imagen,
                                 String nombre, String numero, int repetida,
                                 String tipo, int valor,String coleccionId) {
        this.fechaAdquisicion = fechaAdquisicion;
        this.id = id;
        this.imagen = imagen;
        this.nombre = nombre;
        this.numero = numero;
        this.repetida = repetida;
        this.tipo = tipo;
        this.valor = valor;
        this.coleccionId = coleccionId;
    }



    public String getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(String fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public int getRepetida() {
        return repetida;
    }

    public void setRepetida(int repetida) {
        this.repetida = repetida;
    }

    public CromoPosesionAgrupado() {
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
