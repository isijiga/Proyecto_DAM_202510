package com.example.proyecto_dam_202510.data.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


/**
 * Clase POJO de un CromoPosesionAgrupadoIntercambio. Se diferencia de las otras clases Pojos en que se utiliza para
 * la pantalla de Intercambio.
 */
public class CromoPosesionAgrupadoIntercambio {

    private String id;
    private int repetida;
    private String imagen;
    private String nombre;
    private String numero;
    private String tipo;
    private int valor;
    private String coleccionId;

    private Date fechaAdquisicion;
    private Set<String> usuarioPoseedor;
    public String getColeccionId() {
        return coleccionId;
    }

    public void setColeccionId(String coleccionId) {
        this.coleccionId = coleccionId;
    }

    public Date getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(Date fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }



    public Set<String> getUsuarioPoseedor() {
        return usuarioPoseedor;
    }

    public void setUsuarioPoseedor(Set<String> usuarioPoseedor) {
        this.usuarioPoseedor = usuarioPoseedor;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CromoPosesionAgrupadoIntercambio that = (CromoPosesionAgrupadoIntercambio) o;
        return Objects.equals(nombre, that.nombre) && Objects.equals(numero, that.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, numero);
    }


    public CromoPosesionAgrupadoIntercambio(Date fechaAdquisicion,
                                            String id, String imagen, String nombre,
                                            String numero, int repetida, String tipo, int valor, String usuarioPoseedor) {

        this.fechaAdquisicion = fechaAdquisicion;
        this.id = id;
        this.imagen = imagen;
        this.nombre = nombre;
        this.numero = numero;
        this.repetida = repetida;
        this.tipo = tipo;
        this.valor = valor;

    }


    public int getRepetida() {
        return repetida;
    }

    public void setRepetida(int repetida) {
        this.repetida = repetida;
    }

    public CromoPosesionAgrupadoIntercambio() {
        usuarioPoseedor = new HashSet<>();
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
