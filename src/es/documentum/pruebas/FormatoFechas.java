/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.documentum.pruebas;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatoFechas {

    private static final Date fecha = new Date();

    public static void main(String argv[]) throws IOException {
        DateFormat dateFormatCompleta = new SimpleDateFormat("yyMMdd");
        DateFormat dateFormatMes = new SimpleDateFormat("MM");
        DateFormat dateFormatAnio = new SimpleDateFormat("yy");
        //	Date fecha = cal.getTime();
        String fechahoy = dateFormatCompleta.format(fecha);
        String mes = dateFormatMes.format(fecha);
        String anio = dateFormatAnio.format(fecha);
        System.out.println("Anio: " + anio + " - Mes: " + mes + " - Fechahoy: " + fechahoy);
    }
}
