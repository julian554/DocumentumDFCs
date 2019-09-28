/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.documentum.pruebas;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.ArrayUtils;

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
        String mitexto = "select * from (select dm_sysobject.r_object_id \n"
                + "from dm_sysobject_s  dm_sysobject, dm_sysobject_r dm_sysobject_r2 \n"
                + "where dm_sysobject.r_object_id = dm_sysobject_r2.r_object_id \n"
                + "and dm_sysobject_r2.i_folder_id = (select r_object_id \n"
                + "			       from dm_sysobject_s \n"
                + "			       where object_name='S000501' \n"
                + "			       and r_object_type='dm_cabinet')  order by r_object_id desc) where rownum <= 99;";
        List<HiliteWord> list = palabrasEntreCaracteres(mitexto, "\'", "\'");

        System.out.println("");
    }

    public static String[] substringsBetween(final String str, final String open, final String close) {
        if (str == null || isEmpty(open) || isEmpty(close)) {
            return null;
        }
        final int strLen = str.length();
        if (strLen == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final int closeLen = close.length();
        final int openLen = open.length();
        final List<String> list = new ArrayList<>();
        int pos = 0;
        while (pos < strLen - closeLen) {
            int start = str.indexOf(open, pos);
            if (start < 0) {
                break;
            }
            start += openLen;
            final int end = str.indexOf(close, start);
            if (end < 0) {
                break;
            }
            list.add(str.substring(start, end));
            pos = end + closeLen;
        }
        if (list.isEmpty()) {
            return null;
        }
        return list.toArray(new String[list.size()]);
    }

    public static List<HiliteWord> palabrasEntreCaracteres(final String str, final String open, final String close) {
        List<HiliteWord> hiliteWords = new ArrayList<>();
        if (str == null || isEmpty(open) || isEmpty(close)) {
            return hiliteWords;
        }
        final int strLen = str.length();
        if (strLen == 0) {
            return hiliteWords;
        }
        final int closeLen = close.length();
        final int openLen = open.length();
//        final List<String> list = new ArrayList<>();
        int pos = 0;
        while (pos < strLen - closeLen) {
            int start = str.indexOf(open, pos);
            if (start < 0) {
                break;
            }
            start += openLen;
            final int end = str.indexOf(close, start);
            if (end < 0) {
                break;
            }

            hiliteWords.add(new HiliteWord(str.substring(start, end), start));
//            list.add(str.substring(start, end));
            pos = end + closeLen;
        }

        return hiliteWords;
    }

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }
}

class HiliteWord {

    int _position;
    String _word;

    public HiliteWord(String word, int position) {
        _position = position;
        _word = word;
    }
}
