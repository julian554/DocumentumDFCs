package es.documentum.pruebas;

import com.documentum.fc.client.DfQuery;
import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfQuery;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfTypedObject;
import com.documentum.fc.common.DfException;
import es.documentum.utilidades.ClassPathUpdater;
import es.documentum.utilidades.MiProperties;
import es.documentum.utilidades.Utilidades;
import es.documentum.utilidades.UtilidadesDocumentum;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class LeerTipos {

    private static IDfSession session = null;
    private static final String servidor = "vilcs405";
    private static final String userName = "dmadmin";
    private static final String password = "documentum";
    private static final String docbase = "prudcm1";
    private static final String NEWLINE = "\r\n";
    private static FileWriter fichero = null;
    private static Date fecha = new Date();

    public LeerTipos() {
        try {
            fichero = new FileWriter(System.getProperty("user.home").replace("\\", "/") + "/leerTipos.log");
            Utilidades util = new Utilidades();
            String dirdfc = util.usuarioHome() + util.separador() + "documentumdfcs" + util.separador() + "documentum" + util.separador() + "shared" + util.separador();
            try {
                ClassPathUpdater.add(dirdfc);
                ClassPathUpdater.add(dirdfc + "lib" + util.separador() + "jcmFIPS.jar");
            } catch (Exception ex) {
                Utilidades.escribeLog("Error al actualizar el Classpath  - Error: " + ex.getMessage());
            }
            MiProperties prop = util.leerPropeties(dirdfc + "dfc.properties");
            prop.setProperty("dfc.docbroker.host[0]", servidor);
            prop.setProperty("dfc.docbroker.port[0]", "1489");
            util.escribirProperties(dirdfc + "dfc.properties", prop);

            UtilidadesDocumentum utildocum = new UtilidadesDocumentum(dirdfc + "dfc.properties");
            session = utildocum.conectarDocumentum(userName, password, docbase, servidor, "1489");
        } catch (Exception ex) {

        }
    }

    public static Boolean esPadre(String tipo) {
        String dql = "select name from dm_type where super_name ='" + tipo + "'";
        try {
            String resultado = execQuery(dql, session);
            return !resultado.isEmpty();
        } catch (Exception ex) {
            log_f("Error al consultar esPadre - Tipo: " + tipo + " - Error: " + ex.getMessage());
        }
        return false;
    }

    public static String execQuery(String query, IDfSession sesion) {
        log_f("execQuery: " + query + NEWLINE);
        String retVal = "";
        IDfCollection col = null;
        try {
            IDfQuery q = new DfQuery();
            q.setDQL(query);
            col = q.execute(sesion, DfQuery.EXEC_QUERY);
            if (col.next()) {
                IDfTypedObject r = col.getTypedObject();
                retVal = r.getValueAt(0).asString();
            }
        } catch (DfException ex) {
            log_f("execQuery.error al ejecutar DQL: " + ex.getMessage() + NEWLINE);
        }
        try {
            col.close();
        } catch (DfException e) {
            log_f("execQuery.error General: " + e.getMessage() + NEWLINE);
        }
        return retVal;
    }

    private Boolean DameTipos(String tipo) {
        String dql = "select name,super_name from dm_type where super_name='" + tipo + "' and super_name<>' ' order by 2,1";
        IDfCollection col = null;
        try {
            IDfQuery q = new DfQuery();
            q.setDQL(dql);
            col = q.execute(session, DfQuery.EXEC_QUERY);
            String padre=tipo;
            while (col.next()) {
                System.out.print(padre + " --> " );
                IDfTypedObject r = col.getTypedObject();
                String hijo = r.getValueAt(0).asString();
                if (esPadre(hijo)) {
                    System.out.println(hijo );
                    DameTipos(hijo);
                } else {
                    System.out.println(hijo );
//                    if (!padre.equals(hijo)) {
//                   
//                    }
                }
            }
//            System.out.println();
        } catch (DfException ex) {
            log_f("DameTipos. Error al ejecutar DQL: " + ex.getMessage() + NEWLINE);
        }

        return true;
    }

    private static String usuarioHome() {
        return System.getProperty("user.home").replace("\\", "/");
    }

    private Boolean crearDirectorio(String dir) {
        if (dir.isEmpty()) {
            return false;
        }
        File directorio = new File(dir);
        return directorio.mkdirs();
    }

    private static String fecha_log() {
        Calendar cal = Calendar.getInstance();
        String hora = String.valueOf(cal.get(Calendar.HOUR_OF_DAY)).length() == 1 ? "0"
                + String.valueOf(cal.get(Calendar.HOUR_OF_DAY)) : String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
        String minuto = String.valueOf(cal.get(Calendar.MINUTE)).length() == 1 ? "0"
                + String.valueOf(cal.get(Calendar.MINUTE)) : String.valueOf(cal.get(Calendar.MINUTE));
        String segundo = String.valueOf(cal.get(Calendar.SECOND)).length() == 1 ? "0"
                + String.valueOf(cal.get(Calendar.SECOND)) : String.valueOf(cal.get(Calendar.SECOND));
        String anio = String.valueOf(cal.get(Calendar.YEAR));
        String mes = String.valueOf((cal.get(Calendar.MONTH) + 1)).length() == 1 ? "0"
                + String.valueOf((cal.get(Calendar.MONTH) + 1)) : String.valueOf((cal.get(Calendar.MONTH) + 1));
        String dia = String.valueOf(cal.get(Calendar.DAY_OF_MONTH)).length() == 1 ? "0"
                + String.valueOf(cal.get(Calendar.DAY_OF_MONTH)) : String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        String fecha = anio + mes + dia + "_" + hora + minuto + segundo;
        return fecha;
    }

    private static void log_f(String message) {
        try {
            fichero.write(fecha + " \t ->\t" + message + "\n");
            fichero.flush();
        } catch (Exception e) {
            System.out.println("Excepcion en fichero de log: " + e.getMessage());
        }

    }

    public static void main(String[] args) {
        LeerTipos leertipos = new LeerTipos();
        leertipos.DameTipos("dm_sysobject");
    }
}
