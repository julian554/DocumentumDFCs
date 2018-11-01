package es.documentum.pruebas;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.documentum.fc.client.DfQuery;
import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfQuery;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSessionManager;
import com.documentum.fc.client.IDfSysObject;
import com.documentum.fc.client.IDfTypedObject;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.IDfId;
import com.documentum.fc.common.IDfTime;
import com.documentum.fc.methodserver.DfMethodArgumentException;
import com.documentum.fc.methodserver.DfStandardJobArguments;
import com.documentum.fc.methodserver.IDfMethodArgumentManager;
import es.documentum.utilidades.ClassPathUpdater;
import es.documentum.utilidades.MiProperties;
import es.documentum.utilidades.Utilidades;
import es.documentum.utilidades.UtilidadesDocumentum;

public class reasignar {

    private String patron_tipodoc = "correos";
    private static Date fecha = new Date();
    private static FileWriter fichero = null;
    protected static String m_docbase = "cyt_part02_test";
    protected static String m_userName = "S000144";
    protected static String m_password = "Prueba2007";
    protected static String m_domain = "";
    static String servidor = "tmpdla707.correospre.es";
    protected String m_jobid = null;
    protected String m_mtl = "0";
    protected static String m_filestore_destino = "filestore_system";
    // Default parameters passed by invocation of job
    private static final String USER_KEY = "S000144";
    private static final String DOCBASE_KEY = "cyt_part02_test";
    private static final String PASSWORD_KEY = "Prueba2007";
    private static final String DOMAIN_KEY = "";
    private static final String JOBID = "job_id";
    private static final String MTL = "method_trace_level";
    private static final String FILESTORE_DESTINO = "filestore_system";
    private static final String NEWLINE = "\r\n";
    static IDfSessionManager sessionManager = null;
    static IDfSession session = null;
    static String separador = "/";

    /**
     * @param args
     */
    public static void main(String[] args) {
        principal();

    }

    private static void log_f(String message) {
        try {
            fichero.write(fecha + " \t ->\t" + message + "\n");
            fichero.flush();
        } catch (Exception e) {
            System.out.println("Excepcion en fichero de log: " + e.getMessage());
        }

    }

    public static void principal() {
        fecha = new Date();
        try {

            // El log del tbo se crea en el pc/servidor que ejecuta la
            // creación / importación del documento
            fichero = new FileWriter(System.getProperty("user.home").replace("\\", "/") + "/reasignar.log");

            Utilidades util = new Utilidades();
            String dirdfc = util.usuarioHome() + util.separador() + "documentumdfcs" + util.separador() + "documentum" + util.separador() + "shared" + util.separador();
            try {
                ClassPathUpdater.add(dirdfc);
                ClassPathUpdater.add(dirdfc + "lib" + util.separador() + "jsafeFIPS.jar");
            } catch (Exception ex) {
                Utilidades.escribeLog("Error al actualizar el Classpath  - Error: " + ex.getMessage());
            }
            MiProperties prop = util.leerPropeties(dirdfc + "dfc.properties");
            prop.setProperty("dfc.docbroker.host[0]", servidor);
            prop.setProperty("dfc.docbroker.port[0]", "1489");
            util.escribirProperties(dirdfc + "dfc.properties", prop);

            UtilidadesDocumentum utildocum = new UtilidadesDocumentum(dirdfc + "dfc.properties");
            session = utildocum.conectarDocumentum(m_userName, m_password, m_docbase, servidor, "1489");

            log_f("Inicio del job: " + fecha.toString() + NEWLINE);
            log_f("Repositorio: " + session.getDocbaseName() + NEWLINE);
            String dql = "select r_object_id, i_partition,r_object_type,r_creation_date from dm_document_sp where r_object_type like 'correos%' and a_storage_type='filestore_system'";
            log_f("DQL: " + dql + NEWLINE);

            IDfQuery query = new DfQuery();
            query.setDQL(dql);
            IDfCollection coleccion = query.execute(session, DfQuery.DF_READ_QUERY);

            String directorio_log = usuarioHome() + separador + "jobs" + separador + "recolocar_hvs" + separador;
            String fichero_log = directorio_log + "recolocar_documentos_" + fecha_log() + ".log";

            while (coleccion.next()) {
                IDfTypedObject r = coleccion.getTypedObject();
                IDfTime fecha_creacion = r.getValue("r_creation_date").asTime();
                DateFormat dateFormatCompleta = new SimpleDateFormat("yyMMdd");
                DateFormat dateFormatMes = new SimpleDateFormat("MM");
                DateFormat dateFormatAnio = new SimpleDateFormat("yy");
                String mes = dateFormatMes.format(fecha_creacion.getDate());
                String anio = dateFormatAnio.format(fecha_creacion.getDate());
                String fechahoy = dateFormatCompleta.format(fecha_creacion.getDate());
                String id = r.getValue("r_object_id").asString();
                String tipo = r.getValue("r_object_type").asString();
                String retencion = DameCustodia(tipo);
                String split_mode = DameSplit_mode(tipo);
                String carIpartition = retencion + fechahoy;
                log_f("DameIpartition - En String - i_partition: " + carIpartition);
                int valor = Integer.parseInt(carIpartition);
                IDfId idObj = session.getIdByQualification("dm_sysobject where r_object_id='" + id + "'");
                IDfSysObject Documento = (IDfSysObject) session.getObject(idObj);
                String area_almacenamiento = CalcularAreaAlmacenamiento(Integer.parseInt(split_mode), mes);
                String storage_type = "filestore_" + retencion + anio + area_almacenamiento;
                //Documento.setStorageType(storage_type);
                //Documento.setString("a_storage_type",storage_type);
                Documento.setPartition(valor);
            //    Documento.setInt("i_partition", valor);
                Documento.save();
                log_f("r_object_id: " + id + "  -  i_partition: " + valor + "  -  r_object_type: " + tipo
                        + "  -  Filestore: " + storage_type + NEWLINE);
                String dql_move = "EXECUTE migrate_content FOR '" + id + "' WITH TARGET_STORE='" + storage_type + "',REMOVE_ORIGINAL=TRUE,LOG_FILE='/tmp/migrate_filestore_xxxxx.log',LOG_LEVEL=1,SOURCE_DIRECT_ACCESS_OK=TRUE,IGNORE_CONTENT_VALIDATION_FAILURE=FALSE";
                String resultado_move = execQuery(dql_move, session);
                log_f("  -  Resultado migrate: " + resultado_move + NEWLINE);

            }
            coleccion.close();
        } catch (Exception e) {
            log_f("Error al ejecutar la query inicial: " + e.getMessage() + NEWLINE);

        }
        fecha = new Date();
        log_f("Fin del job: " + fecha.toString() + NEWLINE);

    }

    public static String execQuery(String query, IDfSession session) throws IOException {
        log_f("execQuery: " + query + NEWLINE);
        String retVal = "";
        IDfCollection col = null;
        try {
            IDfQuery q = new DfQuery();
            q.setDQL(query);
            col = q.execute(session, DfQuery.EXEC_QUERY);
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

    protected static String DameCustodia(String tipo) {
        String valor = "";
        String consulta = "select num_custodia from dm_dbo.TB_EDOC_CUSTODIA where des_tipo_documental = '" + tipo + "'";
        try {
            IDfQuery q = new DfQuery();
            q.setDQL(consulta);
            IDfCollection col = q.execute(session, IDfQuery.DF_READ_QUERY);
            if (col.next()) {
                IDfTypedObject r = col.getTypedObject();
                valor = r.getValueAt(0).asString();
            }
            col.close();
        } catch (Exception ex) {

        }
        return valor;
    }

    protected static String DameSplit_mode(String tipo) {
        String valor = "";
        String consulta = "select split_mode from dm_dbo.TB_EDOC_CUSTODIA where des_tipo_documental = '" + tipo + "'";
        try {
            IDfQuery q = new DfQuery();
            q.setDQL(consulta);
            IDfCollection col = q.execute(session, IDfQuery.DF_READ_QUERY);
            if (col.next()) {
                IDfTypedObject r = col.getTypedObject();
                valor = r.getValueAt(0).asString();
            }
            col.close();
        } catch (Exception ex) {

        }
        return valor;
    }

    protected static void AsignarStorageType(String valor, String r_object_id, PrintWriter writer) {
        IDfSession idfDfcSesion = session;
        try {
            IDfId idObj = session.getIdByQualification("dm_sysobject where r_object_id='" + r_object_id + "'");
            IDfSysObject idfSysObject = (IDfSysObject) idfDfcSesion.getObject(idObj);
            idfSysObject.setString("a_storage_type", valor);
            idfSysObject.save();
            log_f("AsignarStorageType - a_storage_type: " + valor);
        } catch (DfException e) {
            System.out.println("Error al asignar a_storage_type: " + e.getMessage());
            log_f("AsignarStorageType - Error en AsignarStorageType " + e.getMessage());
        }
    }

    protected static String CalcularAreaAlmacenamiento(int split_mode, String mes) {

        // split_mode
        // MM| 1| 2| 3| 4| 6|12
        // 01|12|06|04|03|02|01
        // 02|12|06|04|03|02|02
        // 03|12|06|04|03|04|03
        // 04|12|06|04|06|04|04
        // 05|12|06|08|06|06|05
        // 06|12|06|08|06|06|06
        // 07|12|12|08|09|08|07
        // 08|12|12|08|09|08|08
        // 09|12|12|12|09|10|09
        // 10|12|12|12|12|10|10
        // 11|12|12|12|12|12|11
        // 12|12|12|12|12|12|12
        String resultado = "";
        switch (split_mode) {
            case 1:
                resultado = "12";
                break;
            case 2:
                switch (mes) {
                    case "01":
                    case "02":
                    case "03":
                    case "04":
                    case "05":
                    case "06":
                        resultado = "06";
                        break;
                    default:
                        resultado = "12";
                }
            case 3:
                switch (mes) {
                    case "01":
                    case "02":
                    case "03":
                    case "04":
                        resultado = "04";
                        break;
                    case "05":
                    case "06":
                    case "07":
                    case "08":
                        resultado = "08";
                        break;
                    default:
                        resultado = "12";
                }
            case 4:
                switch (mes) {
                    case "01":
                    case "02":
                    case "03":
                        resultado = "03";
                        break;
                    case "04":
                    case "05":
                    case "06":
                        resultado = "06";
                        break;
                    case "07":
                    case "08":
                    case "09":
                        resultado = "09";
                        break;
                    default:
                        resultado = "12";
                }
            case 6:
                switch (mes) {
                    case "01":
                    case "02":
                        resultado = "02";
                        break;
                    case "03":
                    case "04":
                        resultado = "04";
                        break;
                    case "05":
                    case "06":
                        resultado = "06";
                        break;
                    case "07":
                    case "08":
                        resultado = "08";
                        break;
                    case "09":
                    case "10":
                        resultado = "10";
                        break;
                    default:
                        resultado = "12";
                }
            case 12:
                resultado = mes;
        }
        return resultado;
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

    public class CustomJobArguments extends DfStandardJobArguments {

        protected IDfMethodArgumentManager methodArgumentManager;

        public CustomJobArguments(IDfMethodArgumentManager manager) throws DfMethodArgumentException {
            super(manager);
            methodArgumentManager = manager;
        }

        public String getString(String paramName) throws DfMethodArgumentException {
            return methodArgumentManager.getString(paramName);
        }

        public int getInt(String paramName) throws DfMethodArgumentException {
            return methodArgumentManager.getInt(paramName).intValue();
        }
    }
}
