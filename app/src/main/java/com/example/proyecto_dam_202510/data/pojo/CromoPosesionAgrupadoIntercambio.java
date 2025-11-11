package com.example.proyecto_dam_202510.data.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class CromoPosesionAgrupadoIntercambio {

    private String id;
    private int repetida;
    private String imagen;
    private String nombre;
    private String numero;
    private List<String> tipo;
    private int valor;
    private List<String> usuarioPoseedor;

    public List<String> getUsuarioPoseedor() {
        return usuarioPoseedor;
    }

    public void setUsuarioPoseedor(List<String> usuarioPoseedor) {
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





    public CromoPosesionAgrupadoIntercambio(Date fechaAdquisicion, String id, String imagen, String nombre, String numero, int repetida, List<String> tipo, int valor,String usuarioPoseedor) {

        this.fechaAdquisicion = fechaAdquisicion;
        this.id = id;
        this.imagen = imagen;
        this.nombre = nombre;
        this.numero = numero;
        this.repetida = repetida;
        this.tipo = tipo;
        this.valor = valor;
    }

    private Date fechaAdquisicion;

    public Date getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(Date fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public int getRepetida() {
        return repetida;
    }

    public void setRepetida(int repetida) {
        this.repetida = repetida;
    }

    public CromoPosesionAgrupadoIntercambio() {
        usuarioPoseedor = new ArrayList<>();
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

    public List<String> getTipo() {
        return tipo;
    }

    public void setTipo(List<String> tipo) {
        this.tipo = tipo;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
