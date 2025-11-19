package com.example.proyecto_dam_202510.data.pojo;

import java.util.Objects;


/**
 * Clase POJO de un CromoPosesion donde se indica la estructura de una instancia de un Cromo en posesion.
 * A diferencia de la clase Cromo, se establecen más caracteristicas como el numero de veces que se repite.
 * Se podría utilizar como una clase hija de Cromo.
 */
public class CromoPosesion {

    private String id;
    private String imagen;
    private String nombre;
    private String numero;
    private String tipo;
    private int valor;
    private int repetida;
    private String fechaAdquisicion;
    private String coleccionId;

    public String getColeccionId() {
        return coleccionId;
    }

    public void setColeccionId(String coleccionId) {
        this.coleccionId = coleccionId;
    }


    public String getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(String fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    /**
     * Metodo para comparar dos cromos en posesion. La idea es que cuando dos instancias comparte
     * la misma imagen se referencia como "iguales".
     * @param o objeto a comparar
     * @return true si son iguales
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CromoPosesion that = (CromoPosesion) o;
        return Objects.equals(imagen, that.imagen);
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(imagen);
    }

    public int getRepetida() {
        return repetida;
    }

    public void setRepetida(int repetida) {
        this.repetida = repetida;
    }

    public CromoPosesion() {
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
