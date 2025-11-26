package com.example.proyecto_dam_202510;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Funciones_UnitTest {
    int longitudFormato = "dd/MM/YY HH:mm".length();
    String correovalido = "prueba@prueba.com";
    String correoinvalido = "prueba@prueba";
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    Date fechaInicio = formato.parse("01/11/2025");

    public Funciones_UnitTest() throws ParseException {
    }


    @Test
    public void ahora_esFormatoCorrecto() {
        String resultado = Funciones.ahora();
         assertEquals(longitudFormato, resultado.length());
    }

    @Test
    public void comprobarCorreoValido_esValido() {
        boolean resultado = Funciones.comprobarCorreoValido(correovalido);
        assertEquals(true, resultado);

    }
    @Test
    public void comprobarCorreoValido_esInvalido() {

        boolean resultado = Funciones.comprobarCorreoValido(correoinvalido);
        assertEquals(false, resultado);
    }

    @Test
    public void comprobarDiferenciaDias_esValido() throws ParseException {
        Map<String,Object> mapaTest = new HashMap<>();
         mapaTest= Funciones.difDias(fechaInicio);
         long diferencia = (Long) mapaTest.get("diffDias");
         assertEquals(22,diferencia);

    }

}
