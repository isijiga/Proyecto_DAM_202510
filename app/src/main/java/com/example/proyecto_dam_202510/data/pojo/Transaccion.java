package com.example.proyecto_dam_202510.data.pojo;

import java.util.Date;

public class Transaccion {

private String coleccionId;
private String estado;
private Date fechaAdquisicion;
private String idTransaccion;
private String nombre;
private String numero;
private String pedidoA;
private String pedidoPor;
private String id;
private String imagen;
private String mensaje;
private String mensajeRespuesta;

    public String getMensajeRespuesta() {
        return mensajeRespuesta;
    }

    public void setMensajeRespuesta(String mensajeRespuesta) {
        this.mensajeRespuesta = mensajeRespuesta;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getId() {
        return id;
    }

    public void setCromoPosesionId(String cromoPosesionId) {
        this.id = cromoPosesionId;
    }

    private String emailPedidoA;
    private String emailPedidoPor;

    public String getEmailPedidoPor() {
        return emailPedidoPor;
    }

    public void setEmailPedidoPor(String emailPedidoPor) {
        this.emailPedidoPor = emailPedidoPor;
    }

    public String getEmailPedidoA() {
        return emailPedidoA;
    }

    public void setEmailPedidoA(String emailPedidoA) {
        this.emailPedidoA = emailPedidoA;
    }

    public String getColeccionId() {
        return coleccionId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setColeccionId(String coleccion) {
        this.coleccionId = coleccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(Date fechaPeticion) {
        this.fechaAdquisicion = fechaPeticion;
    }

    public String getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(String idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPedidoA() {
        return pedidoA;
    }

    public void setPedidoA(String pedidoA) {
        this.pedidoA = pedidoA;
    }

    public String getPedidoPor() {
        return pedidoPor;
    }

    public void setPedidoPor(String pedidoPor) {
        this.pedidoPor = pedidoPor;
    }
}
