package es.documentum.pruebas;

import es.documentum.utilidades.Utilidades;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

public class LeerUltimasLineas {

    public LeerUltimasLineas() {
        Utilidades util = new Utilidades();
        String fichero = "c:\\tmp\\fichero.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(fichero));
            Locale locale = new Locale(System.getProperty("user.language"), System.getProperty("user.country"));
            String idioma = locale.getLanguage() + "-" + locale.getCountry();
            String os = util.so();
            String encoding = System.getProperty("file.encoding");
            //   BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fichero), (idioma.equals("es-ES") && os.toLowerCase().contains("windows")) ? "8859_1" : encoding));
            String[] lineas = new String[4];
            String linea;

            int pos = 0;
            while ((linea = br.readLine()) != null) {
                lineas[pos] = linea;
                pos++;
                if (pos > 3) {
                    pos = 0;
                }
            }

            //           System.out.println(lineas[0]);
            System.out.println(lineas[1]);
            System.out.println(lineas[1].substring(lineas[1].length() - "Consistency Checker".length() - 3, lineas[1].length()));
            System.out.println(lineas[2]);
            System.out.println(lineas[2].substring(lineas[2].indexOf(":") + 1, lineas[2].length()).trim());
            System.out.println(lineas[3]);
            System.out.println(lineas[3].substring(lineas[3].indexOf("  "), lineas[3].length()).trim());
        } catch (FileNotFoundException ex) {
            System.out.println("Fichero de log " + fichero + " no encontrado - Error " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error al leer el Fichero de log " + fichero + " - Error " + ex.getMessage());
        }

    }

    public static void main(String args[]) {
        LeerUltimasLineas lul = new LeerUltimasLineas();
    }
}
