package es.documentum.utilidades;

import com.documentum.com.*;
import com.documentum.com.IDfClientX;
import com.documentum.fc.client.*;
import com.documentum.fc.commands.admin.DfAdminCommand;
import com.documentum.fc.commands.admin.IDfAdminCommand;
import com.documentum.fc.commands.admin.IDfApplyDoMethod;
import com.documentum.fc.common.*;
import com.documentum.fc.tools.RegistryPasswordUtils;
import com.documentum.operations.*;
import com.documentum.xml.xdql.IDfXmlQuery;
import com.google.common.io.Files;
import es.documentum.Beans.AtributosDocumentum;
import es.documentum.Beans.ResultadoGDBean;
import es.documentum.pantalla.PantallaBarra;
import es.documentum.pantalla.PantallaDocumentum;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.apache.commons.lang.exception.ExceptionUtils.getStackTrace;

public class UtilidadesDocumentum {

    Utilidades util = new Utilidades();
    Properties pro = new Properties();
    String idControl = "";
    public PantallaDocumentum ventanapadre = null;

    public PantallaDocumentum getVentanapadre() {
        return ventanapadre;
    }

    public void setVentanapadre(PantallaDocumentum ventanapadre) {
        this.ventanapadre = ventanapadre;
    }

    public Properties getPro() {
        return pro;
    }

    public void setPro(Properties pro) {
        this.pro = pro;
    }
    String usuario = "";
    String password = "";
    String docbase = "";
    String ficheropropiedades = "";
    String ERROR = "";
    String dir = util.usuarioHome() + util.separador() + "documentumdfcs" + util.separador() + "renditions" + util.separador();

    private final Set m_childIds = new HashSet(5);

    public String dameError() {
        return ERROR;
    }

    public UtilidadesDocumentum() {
    }

    public UtilidadesDocumentum(String fichero) {
        ficheropropiedades = fichero;
    }

    public void cargarConfiguraciones() {
        ERROR = "";
        if (!ficheropropiedades.isEmpty()) {
            try {
                InputStream in = new FileInputStream(ficheropropiedades);
                if (in == null) {
                    ERROR = "Error al cargar el fichero de propiedades de Documentum (cargarConfiguraciones)";
                    Utilidades.escribeLog("Error al cargar el fichero de propiedades de Documentum (cargarConfiguraciones)");
                }
                pro = new java.util.Properties();
                pro.load(in);
            } catch (IOException ex) {
                Utilidades.escribeLog("Error al cargar el fichero de propiedades. (cargarConfiguraciones) Error: " + ex.getMessage());
                ERROR = "Error al cargar el fichero de propiedades. (cargarConfiguraciones) Error: " + ex.getMessage();
            }
        } else {
            try {
                InputStream in = UtilidadesDocumentum.class.getClassLoader().getResourceAsStream("es/documentum/propiedades/documentum.properties");
                if (in == null) {
                    Utilidades.escribeLog("Error al cargar el fichero de propiedades de Documentum (cargarConfiguraciones)");
                    ERROR = "Error al cargar el fichero de propiedades de Documentum (cargarConfiguraciones)";
                }
                pro = new java.util.Properties();
                pro.load(in);
            } catch (IOException ex) {
                Utilidades.escribeLog("Error al cargar el fichero de propiedades. (cargarConfiguraciones) Error: " + ex.getMessage());
                ERROR = "Error al cargar el fichero de propiedades. (cargarConfiguraciones) Error: " + ex.getMessage();
            }
        }

    }

    public IDfSession conectarDocumentum() {
        cargarConfiguraciones();
        usuario = pro.getProperty("usuario");
        password = pro.getProperty("password");
        docbase = pro.getProperty("repositorio");
        String docbroker = pro.getProperty("dfc.docbroker.host[0]");
        String puerto = pro.getProperty("dfc.docbroker.port[0]");
//        System.out.println(usuario + " - " + password + " - " + docbase + " - " + docbroker);

        ERROR = "";
        IDfSession sesion = null;

        if (puerto == null || docbroker == null || puerto.isEmpty() || docbroker.isEmpty()) {
            ERROR = "No hay datos de conexión";
            return null;
        }

        try {
            //         IDfClient client = DfClient.getLocalClient();
            //         IDfTypedObject config = client.getClientConfig();
            //         config.setString("primary_host", docbroker);
            //         config.setInt("primary_port", Integer.parseInt(puerto));
            //         IDfLoginInfo loginInfoObj = new DfLoginInfo();
            //         loginInfoObj.setUser(usuario);
            //         loginInfoObj.setPassword(password);
            //         //sesion = client.newSession(docbase, loginInfoObj);
            //         docbroker = docbroker.contains(".") ? docbroker.substring(0, docbroker.indexOf(".")) : docbroker;

            IDfClient client = DfClient.getLocalClient();
            IDfSessionManager sesMgr = client.newSessionManager();
            IDfLoginInfo loginInfo = new DfLoginInfo();
            loginInfo.setUser(usuario);
            loginInfo.setPassword(password);
            // sesMgr.setIdentity(docbase + "@" + docbroker, loginInfo);
            sesMgr.setIdentity(IDfSessionManager.ALL_DOCBASES, loginInfo);
            docbroker = docbroker.contains(".") ? docbroker.substring(0, docbroker.indexOf(".")) : docbroker;
            sesion = sesMgr.getSession(docbase);
            //     sesion = client.newSession(docbase + "@" + docbroker, loginInfoObj);
            if (!sesion.isConnected()) {
                ERROR = "No se pudo obtener sesión de Documentum (conectarDocumentum)";
                return null;
            }
        } catch (Exception dfe) {
            Utilidades.escribeLog("Error al conectar con Documentum (conectarDocumentum): " + dfe.toString());
            ERROR = "Error al conectar con Documentum (conectarDocumentum): " + dfe.toString();
        }
        return sesion;
    }

    public IDfSession conectarDocumentum(String usu, String clave, String repo, String server, String port) {
        usuario = usu;
        password = clave;
        docbase = repo;
        String docbroker = server;
        String puerto = port;
//        System.out.println(usuario + " - " + password + " - " + docbase + " - " + docbroker);

        ERROR = "";
        IDfSession sesion = null;

        if (puerto == null || docbroker == null || puerto.isEmpty() || docbroker.isEmpty()) {
            ERROR = "No hay datos de conexión";
            return null;
        }

        try {
            /*
            IDfClient client = DfClient.getLocalClient();
            IDfTypedObject config = client.getClientConfig();
            config.setString("primary_host", docbroker);
            config.setInt("primary_port", Integer.parseInt(puerto));
            IDfLoginInfo loginInfoObj = new DfLoginInfo();
            loginInfoObj.setUser(usuario);
            loginInfoObj.setPassword(password);
            docbroker = docbroker.contains(".") ? docbroker.substring(0, docbroker.indexOf(".")) : docbroker;
            sesion = client.newSession(docbase , loginInfoObj);
             */

            Utilidades.escribeLog("Obteniendo sesión ... " + Utilidades.today() + " - " + Utilidades.now());
            Utilidades.escribeLog("Repositorio: " + docbase + " - Docbroker: " + docbroker + " - Puerto: " + puerto);
            IDfClient client = DfClient.getLocalClient();
            IDfSessionManager sesMgr = client.newSessionManager();
            IDfLoginInfo loginInfo = new DfLoginInfo();
            loginInfo.setUser(usuario);
            loginInfo.setPassword(password);
            // sesMgr.setIdentity(docbase + "@" + docbroker, loginInfo);
            sesMgr.setIdentity(IDfSessionManager.ALL_DOCBASES, loginInfo);
            docbroker = docbroker.contains(".") ? docbroker.substring(0, docbroker.indexOf(".")) : docbroker;
            sesion = sesMgr.getSession(docbase);

            // sesion = sesMgr.getSession(docbase + "@" + docbroker);
            if (!sesion.isConnected()) {
                ERROR = "No se pudo obtener sesión de Documentum (conectarDocumentum)";
                return null;
            }
            Utilidades.escribeLog("Sesión obtenida... " + Utilidades.today() + " - " + Utilidades.now());
        } catch (DfException dfe) {
            Utilidades.escribeLog("Error al conectar con Documentum (conectarDocumentum): " + dfe.toString());
            ERROR = "Error al conectar con Documentum (conectarDocumentum): " + dfe.toString();
        }
        return sesion;
    }

    public IDfCollection ejecutarDql(String dql) {
        IDfCollection coleccion = null;
        IDfSession sesion = conectarDocumentum();
        if (sesion == null) {
            if (ERROR.isEmpty()) {
                ERROR = "No se pudo obtener sesión de Documentum (ejecutarDql)";
            }
            return coleccion;
        }

        ERROR = "";

        IDfQuery query = new DfClientX().getQuery();
        query.setDQL(dql);
        try {
            coleccion = query.execute(sesion, IDfQuery.DF_EXEC_QUERY);
//            if (sesion.isConnected()) {
//                sesion.disconnect();
//            }
        } catch (DfException ex) {
            ERROR = "Error al ejecutar DQL (ejecutarDql) - Error: " + ex.getMessage();
            Utilidades.escribeLog("Error al ejecutar DQL '" + dql + "' (ejecutarDql) - Error: " + ex.getMessage());
            return coleccion;
        }

        return coleccion;
    }

    public Boolean esTablaRegistrada(String tabla, IDfSession sesion) {
        Boolean registrada = false;
        String dql = "select table_name from dm_registered where lower(table_name)='" + tabla.toLowerCase() + "'";
        IDfCollection coleccion = ejecutarDql(dql, sesion);
        if (coleccion != null) {
            try {
                coleccion.next();
                String valor = coleccion.getString("table_name");
                if (valor.equalsIgnoreCase(tabla)) {
                    registrada = true;
                }
            } catch (Exception ex) {
            }
        }
        return registrada;
    }

    public IDfCollection ejecutarDql(String dql, IDfSession sesion) {
        IDfCollection coleccion = null;
        if (sesion == null) {
            if (ERROR.isEmpty()) {
                ERROR = "No se pudo obtener sesión de Documentum (ejecutarDql)";
            }
            return coleccion;
        }
        IDfQuery query = new DfClientX().getQuery();
        query.setDQL(dql);
        try {
            coleccion = query.execute(sesion, IDfQuery.DF_EXEC_QUERY);
        } catch (DfException ex) {
            ERROR = "Error al ejecutar DQL (ejecutarDql) - Error: " + ex.getMessage();
            Utilidades.escribeLog("Error al ejecutar DQL '" + dql + "' (ejecutarDql) - Error: " + ex.getMessage() + " - " + getStackTrace(ex));
            return coleccion;
        }

        ERROR = "";

        return coleccion;
    }

    public String dameSql(IDfSession sesion) {
        String sqlResult = null;
        try {
            IDfSession session = sesion;
            String collId = session.apiGet("apply", "NULL,GET_LAST_SQL");
            if (!collId.equals("")) {
                session.apiExec("next", collId);
                sqlResult = session.apiGet("get", collId + ",result");
                session.apiExec("close", collId);
            }
//            if (session.isConnected()) {
//                session.disconnect();
//            }
        } catch (DfException ex) {
            ERROR = "Error al obtener SQL (getSqlQuery) - Error: " + ex.getMessage();
            Utilidades.escribeLog("Error al obtener SQL (getSqlQuery) - Error: " + ex.getMessage() + " - " + getStackTrace(ex));
        }
        return sqlResult;
    }

    public ArrayList<ResultadoGDBean> subirDocumentosDocumentum(String rutacarpetadocumentum, String rutacarpeta) {
        ArrayList listaresultados = new ArrayList();
        ResultadoGDBean resultadogd = new ResultadoGDBean();
        IDfSession sesion = conectarDocumentum();
        if (sesion == null) {
            listaresultados.add(resultadogd);
            if (ERROR.isEmpty()) {
                ERROR = "No se pudo obtener sesión de Documentum (subirDocumentosDocumentum)";
            }
            return listaresultados;
        }
        ERROR = "";
        IDfFolder carpeta = dameCarpeta(rutacarpetadocumentum);

        if (carpeta == null) {
            listaresultados.add(resultadogd);
            ERROR = "No existe la carpeta en Documentum " + rutacarpetadocumentum;
            return listaresultados;
        }
        //    String nombredir = "C:\\Documents and Settings\\julian.collado\\digita\\pendientes\\20120822-111919-010001074110\\";
        File dir = new File(rutacarpeta);
        String ficheros[] = dir.list();
        int numficheros = ficheros.length;
        listaresultados.ensureCapacity(numficheros);

        for (int i = 0; i < numficheros; i++) {
            resultadogd = new ResultadoGDBean();
            String resultado = "";
            String ruta = "";
            try {
                ruta = subirDocumento(sesion, rutacarpeta + ficheros[i], rutacarpetadocumentum);
                resultado = "OK";
                if (sesion.isConnected()) {
                    sesion.disconnect();
                }
            } catch (DfException ex) {
                resultado = "Error al subir fichero a Documentum - " + ex.getMessage();
                Utilidades.escribeLog(ex.getMessage());
                ERROR = "Error al subir fichero a Documentum - " + ex.getMessage();
            }
            resultadogd.setFichero(rutacarpeta + ficheros[i]);
            resultadogd.setId(ruta);
            resultadogd.setResultado(resultado);
            resultadogd.setCarpeta(rutacarpetadocumentum);
            listaresultados.add(resultadogd);

        }

        return listaresultados;
    }

    public String importarADocumentum(String nombrefichero, String rutafichero, String carpetadocumentum, String tipodocumental) {
        IDfSession sesion = conectarDocumentum();
        Utilidades pc = new Utilidades();
        if (sesion == null) {
            if (ERROR.isEmpty()) {
                ERROR = "No se pudo obtener sesión de Documentum (importarADocumentum)";
            }
            return ERROR;
        }
        ERROR = "";
        try {
            IDfSysObject sysObj = (IDfSysObject) sesion.newObject(tipodocumental);
            sysObj.setObjectName(nombrefichero);
            String tipo = dameTipo(rutafichero);
            if (tipo.equals("-1") || tipo.isEmpty()) {
                ERROR = "Error - Imposible obtener el tipo de documento en Documentum para la extensión de este archivo";
            } else {
                sysObj.setContentType(tipo);
                sysObj.setFile(rutafichero);
                IDfFolder carpeta = dameCarpeta(carpetadocumentum);
                System.out.println(ERROR);
                sysObj.link(carpetadocumentum);
                sysObj.setString("title", nombrefichero);
                sysObj.setRepeatingString("authors", 0, pc.usuario());
                sysObj.save();
                ERROR = sysObj.getObjectId().toString();
            }
            if (sesion.isConnected()) {
                sesion.disconnect();
            }
        } catch (DfException e) {
            ERROR = e.getMessage();
        }
        return ERROR;
    }

    public String subirDocumento(IDfSession sesion, String srcFileOrDir, String destFolderPath)
            throws DfException {
        IDfClientX clientx = new DfClientX();
        IDfImportOperation operation = clientx.getImportOperation();
        operation.setSession(sesion);
        IDfFolder folder;
        folder = sesion.getFolderByPath(destFolderPath);

        if (folder == null) {
            Utilidades.escribeLog("No existe la carpeta " + destFolderPath + " en la Docbase. Se crea.");
            folder = dameCarpeta(destFolderPath);
        }
        operation.setDestinationFolderId(folder.getObjectId());
        IDfFile myFile = clientx.getFile(srcFileOrDir);

        if (myFile.exists() == false) {
            Utilidades.escribeLog("No existe el fichero o directorio " + srcFileOrDir);
            return "";
        }
        operation.disableRegistryUpdates(true);
//add the file or directory to the operation
        IDfImportNode node = (IDfImportNode) operation.add(srcFileOrDir);

        executeOperation(operation);

        IDfList myNodes = operation.getNodes();
        int iCount = myNodes.getCount();
//        Utilidades.escribeLog("Número de nodos: " + iCount);
        String id = "";

        for (int i = 0; i < iCount; ++i) {
            IDfImportNode aNode = (IDfImportNode) myNodes.get(i);
            System.out.print("r_object_id: " + aNode.getNewObjectId().toString() + " ");
            Utilidades.escribeLog(" - Nombre: " + aNode.getNewObject().getObjectName());
            //     Utilidades.escribeLog("Carpeta: " + pro.getProperty("DIRECTORIO_BASE") + "/" + destFolderPath);
            Utilidades.escribeLog("Storage: " + aNode.getNewObject().getStorageType());
            id = aNode.getNewObjectId().toString();
        }

//        try {
//            if (sesion.isConnected()) {
//                sesion.disconnect();
//            }
//        } catch (DfException ex) {
//        }
        return id;
    }

    public IDfFolder dameCarpeta(String nombre) {
        IDfSession sesion = conectarDocumentum();
        if (sesion == null) {
            if (ERROR.isEmpty()) {
                ERROR = "No se pudo obtener sesión de Documentum (dameCarpeta)";
            }
            return null;
        }
        ERROR = "";
        IDfFolder carpeta = null;
        //       String dirbase = pro.getProperty("DIRECTORIO_BASE");
        String dirbase = "";
        if (dirbase == null) {
            dirbase = "";
        }
        String dir = dirbase;
        IDfFolder carpetaraiz = null;
        StringTokenizer path = new StringTokenizer(nombre, "/");
        while (path.hasMoreElements()) {
            try {
                carpetaraiz = (IDfFolder) sesion.getObjectByPath(dirbase);
            } catch (DfException ex) {
                Utilidades.escribeLog("Error al recuperar directorio raiz " + dirbase + " Error - " + ex.getMessage());
                ERROR = "Error al recuperar directorio raiz " + dirbase + " (dameCarpeta) - Error - " + ex.getMessage();
                try {
                    sesion.disconnect();
                } catch (DfException ex1) {
                    Utilidades.escribeLog("Error al desconectar la sesión en Documentum " + " - " + ex1.getMessage());
                    ERROR = "Error al desconectar la sesión en Documentum (dameCarpeta) " + " - " + ex1.getMessage();
                }

                return null;
            }
            dir = path.nextElement().toString();

//            Utilidades.escribeLog(dir);
            try {
                carpeta = sesion.getFolderByPath(dirbase + "/" + dir);
                if (carpeta == null) {
                    carpeta = (IDfFolder) sesion.newObject("dm_folder");
                    carpeta.setObjectName(dir);
                    carpeta.link(dirbase);
                    carpeta.save();
                }
            } catch (DfException ex1) {
                Utilidades.escribeLog("Error al crear el directorio " + dirbase + "/" + dir + ". - " + ex1.getMessage());
                ERROR = "Error al crear el directorio " + dirbase + "/" + dir + " (dameCarpeta). - " + ex1.getMessage();
            }
            dirbase = dirbase + "/" + dir;
        }
        try {
            if (sesion.isConnected()) {
                sesion.disconnect();
            }
        } catch (DfException ex) {
        }

        return carpeta;
    }

    public Boolean existeCarpeta(String nombre) {
        Boolean respuesta = false;
        if (nombre.equals("/")) {
            return true;
        }
        IDfSession sesion = conectarDocumentum();
        if (sesion == null) {
            if (ERROR.isEmpty()) {
                ERROR = "No se pudo obtener sesión de Documentum (existeCarpeta)";
            }
            return respuesta;
        }
        ERROR = "";
        IDfFolder carpeta = null;
        try {
            carpeta = sesion.getFolderByPath(nombre);
        } catch (DfException ex) {
            Utilidades.escribeLog("Error al comprobar si existe la carpeta " + " - " + ex.getMessage());
            ERROR = "Error al comprobar si existe la carpeta (existeCarpeta). - " + ex.getMessage();
        }
        if (carpeta != null) {
            return true;
        }

        try {
            sesion.disconnect();
        } catch (DfException ex) {
            Utilidades.escribeLog("Error al desconectar la sesión en Documentum " + " - " + ex.getMessage());
            ERROR = "Error al desconectar la sesión en Documentum (existeCarpeta). - " + ex.getMessage();
        }

        return respuesta;
    }

    public static void executeOperation(IDfOperation operation) {
        try {
            boolean executeFlag = operation.execute();
            // Check if any errors occured during the execution of the operation
            if (executeFlag == false) {
                // Get the list of errors
                IDfList errorList = operation.getErrors();
                String message = "";
                IDfOperationError error;
                // Iterate through the errors and concatenate the error messages
                for (int i = 0; i < errorList.getCount(); i++) {
                    error = (IDfOperationError) errorList.get(i);
                    message += error.getMessage();
                }
                Utilidades.escribeLog("Errores: " + message);
            }
        } catch (DfException ex) {

        }
    }

    public String DumpAtributos(String r_object_id) {
        String resultado = "";

        IDfSession sesion = conectarDocumentum();

        if (sesion == null) {
            if (ERROR.isEmpty()) {
                ERROR = "Error al crear sesión en Documentum (DumpAtributos)";
            }
            return resultado;
        }
        ERROR = "";
        try {
            //object ID based on the object ID string.
            //         IDfId idObj = sesion.getIdByQualification("dm_sysobject where r_object_id='" + r_object_id + "'");
            //     IDfId idObj = new DfId(r_object_id);   

            DfId dfId = new DfId(r_object_id);

            IDfSysObject sysObj = (IDfSysObject) sesion.getObject(dfId);

// Instantiate an object from the ID.
            //         IDfSysObject sysObj = (IDfSysObject) sesion.getObject(idObj);
            Utilidades.escribeLog("Nº de atributos: " + sysObj.getAttrCount());
            String nombre = "";
            for (int i = 0; i < sysObj.getAttrCount(); i++) {
                nombre = sysObj.getAttr(i).getName();
//                Utilidades.escribeLog(nombre+" : ");
                if (sysObj.getAttr(i).isRepeating()) {
                    for (int n = 0; n < sysObj.getValueCount(nombre); n++) {
                        String valor = sysObj.getRepeatingString(nombre, n);
                        System.out.println(nombre + "[" + n + "] : " + valor);
                    }
                } else {
                    System.out.println(nombre + " : " + sysObj.getValue(nombre));
                }
            }
            resultado = sysObj.dump();

        } catch (DfException ex) {
            ERROR = "Error al hacer dump de " + r_object_id + " (DumpAtributos) - Error: " + ex.getMessage();
            return "";
        }

        try {
            sesion.disconnect();
        } catch (DfException ex) {
            Utilidades.escribeLog("Error al desconectar la sesión en Documentum " + " - " + ex.getMessage());
            ERROR = "Error al desconectar la sesión en Documentum  (DumpAtributos). - " + ex.getMessage();
        }

        return resultado;
    }

    public String DameVersionDocumentum() {
        String resultado = "";

        IDfSession sesion = conectarDocumentum();

        if (sesion == null) {
            if (ERROR.isEmpty()) {
                ERROR = "Error al crear sesión en Documentum (DameVersionDocumentum)";
            }
            return resultado;
        }
        ERROR = "";
        try {
            //object ID based on the object ID string.
            IDfId idObj = sesion.getIdByQualification("dm_server_config ");
            // Instantiate an object from the ID.
            IDfSysObject sysObj = (IDfSysObject) sesion.getObject(idObj);
            resultado = sysObj.getValue("r_server_version").toString();

        } catch (DfException ex) {
            ERROR = "Error al obtener versión de Documentum (DameVersionDocumentum) - Error: " + ex.getMessage();
            return "";
        }

        try {
            sesion.disconnect();
        } catch (DfException ex) {
            Utilidades.escribeLog("Error al desconectar la sesión en Documentum " + " - " + ex.getMessage());
            ERROR = "Error al desconectar la sesión en Documentum  (DameVersionDocumentum). - " + ex.getMessage();
        }

        return resultado;
    }

    public String DameVersionDFC() {
        String version = "";
        try {
            IDfClientX clientx = new DfClientX();
            version = clientx.getDFCVersion();
        } catch (Exception e) {
            System.out.println("Error al recoger versión de DFC. - Error: " + e.getMessage());
        }
        return version;
    }

    public String DameIdRepositorio() {
        String resultado = "";

        IDfSession sesion = conectarDocumentum();

        if (sesion == null) {
            if (ERROR.isEmpty()) {
                ERROR = "Error al crear sesión en Documentum (DameIdRepositorio)";
            }
            return resultado;
        }
        ERROR = "";

        try {
            IDfTypedObject config = sesion.getDocbaseConfig();
            resultado = config.getValue("r_docbase_id").asString();
            // resultado = sesion.getDocbaseId();
        } catch (DfException ex) {
            ERROR = "Error al obtener versión de Documentum (DameIdRepositorio) - Error: " + ex.getMessage();
            return "";
        }
        return resultado;
    }

    public String DameDocbroker() {
        String resultado = "";
        try {

            IDfSession sesion = conectarDocumentum();
            if (sesion == null) {
                if (ERROR.isEmpty()) {
                    ERROR = "Error al crear sesión en Documentum (DameIdRepositorio)";
                }
                return resultado;
            }
            ERROR = "";
            resultado = sesion.getClientConfig().getString("primary_host");

        } catch (DfException ex) {
            ERROR = "Error al obtener Docbroker (DameDocbroker) - Error: " + ex.getMessage();
        }

        return resultado;

    }

    public String DamePuertoDocbroker() {
        String resultado = "";
        try {

            IDfSession sesion = conectarDocumentum();
            if (sesion == null) {
                if (ERROR.isEmpty()) {
                    ERROR = "Error al crear sesión en Documentum (DameIdRepositorio)";
                }
                return resultado;
            }
            ERROR = "";
            resultado = sesion.getClientConfig().getInt("primary_port") + "";

        } catch (DfException ex) {
            ERROR = "Error al obtener Docbroker (DameDocbroker) - Error: " + ex.getMessage();
        }

        return resultado;
    }

    public ArrayList<AtributosDocumentum> DameTodosAtributos(String r_object_id) {
        ArrayList<AtributosDocumentum> resultado = new ArrayList<>();
        IDfSession sesion = conectarDocumentum();

        if (sesion == null) {
            if (ERROR.isEmpty()) {
                ERROR = "Error al crear sesión en Documentum (DameTodosAtributos)";
            }
            return resultado;
        }
        ERROR = "";
        try {
            //object ID based on the object ID string.
            IDfId idObj = sesion.getIdByQualification("dm_sysobject where r_object_id='" + r_object_id + "'");
            DfId dfId = new DfId(r_object_id);
            // Instantiate an object from the ID.
            //   IDfSysObject sysObj = (IDfSysObject) sesion.getObject(idObj);
            IDfSysObject sysObj = (IDfSysObject) sesion.getObject(dfId);
//            Utilidades.escribeLog("Nº de atributos: " + sysObj.getAttrCount());
            String nombre = "";
            for (int i = 0; i < sysObj.getAttrCount(); i++) {
                nombre = sysObj.getAttr(i).getName();
                AtributosDocumentum atri = new AtributosDocumentum();
//                Utilidades.escribeLog(nombre+" : ");
                if (sysObj.getAttr(i).isRepeating()) {
                    int j = sysObj.getValueCount(nombre);
                    if (j == 0) {
                        String valor = sysObj.getRepeatingString(nombre, 0);
//                        System.out.println(nombre + "[" + 0 + "] : " + valor);
                        atri.setNombre(nombre + "[" + 0 + "]");
                        atri.setValor(valor);
                        atri.setMultivalor(true);
                        atri.setTipo(sysObj.getAttr(i).getDataType());
                        atri.setLongitud(sysObj.getAttr(i).getLength());
                        resultado.add(atri);
                    } else {
                        for (int n = 0; n < sysObj.getValueCount(nombre); n++) {
                            atri = new AtributosDocumentum();
                            String valor = sysObj.getRepeatingString(nombre, n);
//                            System.out.println(nombre + "[" + n + "] : " + valor);
                            atri.setNombre(nombre + "[" + n + "]");
                            atri.setValor(valor);
                            atri.setMultivalor(true);
                            atri.setTipo(sysObj.getAttr(i).getDataType());
                            atri.setLongitud(sysObj.getAttr(i).getLength());
                            resultado.add(atri);
                        }
                    }
                } else {
                    atri.setNombre(nombre);
                    if (sysObj.getAttrDataType(nombre) == IDfValue.DF_TIME) {
                        atri.setValor(sysObj.getValue(nombre).asTime().asString("yyyy/mm/dd hh:mi:ss"));
                    } else {
                        atri.setValor(sysObj.getValue(nombre).toString());
                    }
                    atri.setMultivalor(false);
                    atri.setTipo(sysObj.getAttrDataType(nombre));
                    atri.setLongitud(sysObj.getAttr(i).getLength());
//                    System.out.println(nombre + " : " + sysObj.getValue(nombre));
                    resultado.add(atri);
                }
            }
        } catch (DfException ex) {
            ERROR = "Error al recuperar atributos de " + r_object_id + " (DameTodosAtributos) - Error: " + ex.getMessage();
            return new ArrayList<>();
        }

        try {
            sesion.disconnect();
        } catch (DfException ex) {
            Utilidades.escribeLog("Error al desconectar la sesión en Documentum " + " - " + ex.getMessage());
            ERROR = "Error al desconectar la sesión en Documentum  (DameTodosAtributos). - " + ex.getMessage();
        }
        return resultado;
    }

    public ArrayList DameAtributo(String r_object_id, String atributo) {
        IDfSession sesion = conectarDocumentum();
        ArrayList resultado = new ArrayList();
        if (sesion == null) {
            if (ERROR.isEmpty()) {
                ERROR = "Error al crear sesión en Documentum (DameAtributo)";
            }
            return resultado;
        }
        ERROR = "";

        try {
            //object ID based on the object ID string.
            IDfId idObj = sesion.getIdByQualification("dm_sysobject where r_object_id='" + r_object_id + "'");
            // Instantiate an object from the ID.
            IDfSysObject sysObj = (IDfSysObject) sesion.getObject(idObj);

            if (atributo.toLowerCase().equals("r_folder_path")) {
                idObj = sesion.getIdByQualification("dm_sysobject where r_object_id='" + sysObj.getRepeatingString("i_folder_id", 0) + "'");
                sysObj = (IDfSysObject) sesion.getObject(idObj);
                for (int i = 0; i < sysObj.getValueCount("r_folder_path"); i++) {
                    String valores = sysObj.getRepeatingString("r_folder_path", i);
                    resultado.add(valores);
                }
            } else if (!sysObj.isAttrRepeating(atributo)) {
                if (atributo.toLowerCase().contains("date")) {
                    resultado.add(sysObj.getValue(atributo.toLowerCase()).asTime().asString("yyyy/mm/dd hh:mi:ss"));
                } else {
                    resultado.add(sysObj.getValue(atributo.toLowerCase()).asString());
                }
            } else {
                for (int i = 0; i < sysObj.getValueCount(atributo); i++) {
                    String valores = sysObj.getRepeatingString(atributo, i);
                    resultado.add(valores);
//                    Utilidades.escribeLog(atributo + "[" + i + "]: " + valores);
                }
            }
        } catch (DfException ex) {
            Utilidades.escribeLog("Error al leer atributos (" + r_object_id + " - " + atributo + "): " + ex.getMessage());
            ERROR = "Error al leer el atributo " + atributo + " (DameAtributo): " + ex.getMessage();
            return new ArrayList();
        }

        try {
            sesion.disconnect();
        } catch (DfException ex) {
            Utilidades.escribeLog("Error al cerrar la sesión - " + ex.getMessage());
            ERROR = "Error al cerrar la sesión (DameAtributo) - " + ex.getMessage();
            return new ArrayList();
        }

        return resultado;
    }

    public String ActualizarAtributo(int tipo, String r_object_id, String nombre, String valor, int pos) {
        String resultado = "";

        IDfSession sesion = conectarDocumentum();
        if (sesion == null) {
            if (ERROR.isEmpty()) {
                ERROR = "Error al crear sesión en Documentum (ActualizarAtributo)";
            }
            return ERROR;
        }

        try {
            IDfId idObj = sesion.getIdByQualification("dm_sysobject where r_object_id='" + r_object_id + "'");
            IDfDocument Documento = (IDfDocument) sesion.getObject(idObj);

            //   Documento.setRepeatingString(nombre,pos,valor);
            switch (tipo) {
                case IDfValue.DF_BOOLEAN:
                    if (pos < 0) {
                        Documento.setBoolean(nombre, valor.equals("true"));
                    } else {
                        Documento.setRepeatingBoolean(nombre, pos, valor.equals("true"));
                    }
                    break;
                case IDfValue.DF_DOUBLE:
                    if (pos < 0) {
                        Documento.setDouble(nombre, Double.parseDouble(valor));
                    } else {
                        Documento.setRepeatingDouble(nombre, pos, Double.parseDouble(valor));
                    }
                    break;
                case IDfValue.DF_INTEGER:
                    if (pos < 0) {
                        Documento.setInt(nombre, Integer.parseInt(valor));
                    } else {
                        Documento.setRepeatingInt(nombre, pos, Integer.parseInt(valor));
                    }
                    break;
                case IDfValue.DF_STRING:
                    if (pos < 0) {
                        Documento.setString(nombre, valor);
                    } else {
                        Documento.setRepeatingString(nombre, pos, valor);
                    }
                    break;
                case IDfValue.DF_TIME:
                    if (pos < 0) {
                        Documento.setTime(nombre, new DfTime(valor));
                    } else {
                        Documento.setRepeatingTime(nombre, pos, new DfTime(valor));
                    }
                    break;
                case IDfValue.DF_ID:
                    if (pos < 0) {
                        Documento.setId(nombre, Documento.getId(valor));
                    } else {
                        Documento.setRepeatingId(nombre, pos, Documento.getId(valor));
                    }
                    break;
                case IDfValue.DF_UNDEFINED:
                    if (pos < 0) {
                        Documento.setString(nombre, valor);
                    } else {
                        Documento.setRepeatingString(nombre, pos, valor);
                    }
                    break;
            }
            Documento.save();
        } catch (DfException | NumberFormatException ex) {
            resultado = "Error al actualizar atributo " + nombre + " (ActualizarAtributo) - Error " + ex.getMessage();
        }
        ERROR = resultado;

        try {
            sesion.disconnect();
        } catch (DfException ex) {
            Utilidades.escribeLog("Error al desconectar la sesión en Documentum " + " - " + ex.getMessage());
            ERROR = "Error al desconectar la sesión en Documentum  (ActualizarAtributo). - " + ex.getMessage();
        }
        return resultado;
    }

    public IDfFolder crearCarpeta(IDfSession session, String path) {
        IDfFolder folder = null;
        try {
            folder = session.getFolderByPath(path);
            if (folder != null) {
                return folder;
            }

            int slashIndex = path.lastIndexOf('/');
            String name = path.substring(slashIndex + 1);
            String parent = path.substring(0, slashIndex);
            synchronized (("lock-" + path).intern()) {
                folder = session.getFolderByPath(path);
                if (folder != null) {
                    return folder;
                }
                if ("".equals(parent) || "/".equals(parent)) {
                    folder = (IDfFolder) session.newObject("dm_cabinet");
                    folder.setObjectName(name);
                    folder.save();
                } else {
                    crearCarpeta(session, parent);
                    folder = (IDfFolder) session.newObject("dm_folder");
                    folder.setObjectName(name);
                    folder.link(parent);
                    folder.save();
                }
            }
        } catch (DfException ex) {
            Utilidades.escribeLog("Error al crear carpeta en Documentum () - Error: " + ex.getMessage());
        }
        return folder;
    }

    public IDfFolder crearCarpeta(String path) {
        IDfSession session = conectarDocumentum();
        if (session == null) {
            if (ERROR.isEmpty()) {
                ERROR = "Error al crear sesión en Documentum (crearCarpeta)";
            }
            return null;
        }

        IDfFolder folder = null;
        try {
            folder = session.getFolderByPath(path);
            if (folder != null) {
                session.disconnect();
                return folder;
            }

            int slashIndex = path.lastIndexOf('/');
            String name = path.substring(slashIndex + 1);
            String parent = path.substring(0, slashIndex);
            synchronized (("lock-" + path).intern()) {
                folder = session.getFolderByPath(path);
                if (folder != null) {
                    session.disconnect();
                    return folder;
                }
                if ("".equals(parent) || "/".equals(parent)) {
                    folder = (IDfFolder) session.newObject("dm_cabinet");
                    folder.setObjectName(name);
                    folder.save();
                } else {
                    crearCarpeta(session, parent);
                    folder = (IDfFolder) session.newObject("dm_folder");
                    folder.setObjectName(name);
                    folder.link(parent);
                    folder.save();
                }
                if (session.isConnected()) {
                    session.disconnect();
                }
            }
        } catch (DfException ex) {
            Utilidades.escribeLog("Error al crear carpeta en Documentum () - Error: " + ex.getMessage());
        }

        return folder;
    }

    public boolean EsUsuarioAdmin(String usuario) {
        boolean resultado = false;

        if (usuario.isEmpty()) {
            return resultado;
        }

        IDfCollection coleccion = ejecutarDql("Select user_privileges From dm_user where user_name='" + usuario + "' ");
        String valor = "0";
        try {
            coleccion.next();
            valor = "" + coleccion.getInt("user_privileges");
        } catch (DfException ex) {
            Utilidades.escribeLog("Error al comprobar usuario Administrador (EsUsuarioAdmin): " + ex.getMessage());
            ERROR = "Error al comprobar si " + usuario + " es Administrador (EsUsuarioAdmin): " + ex.getMessage();
        }
        resultado = valor.equals("16");
        return resultado;
    }

    public boolean BorrarDocumento(String r_object_id) {
        boolean resultado = true;
        IDfSession sesion = conectarDocumentum();
        if (sesion == null) {
            if (this.ERROR.isEmpty()) {
                this.ERROR = "Error al crear sesi�n en Documentum (BorrarDocumento)";
            }
            return false;
        }
        try {
            IDfId idObj = sesion.getIdByQualification("dm_sysobject where r_object_id='" + r_object_id + "'");
            IDfDocument Documento = (IDfDocument) sesion.getObject(idObj);
            Documento.destroyAllVersions();
        } catch (DfException ex) {
            this.ERROR = ("Error al Borrar el documento con ID: " + r_object_id + " - Error: " + ex.getMessage());
            Utilidades.escribeLog(this.ERROR);
            return false;
        }
        this.ERROR = "";
        try {
            sesion.disconnect();
        } catch (DfException ex) {
            Utilidades.escribeLog("Error al desconectar la sesi�n en Documentum  - " + ex.getMessage());
            this.ERROR = ("Error al desconectar la sesi�n en Documentum  (BorrarDocumento). - " + ex.getMessage());
        }
        return resultado;
    }

    public ArrayList<AtributosDocumentum> ListarFicheros(String carpeta) {
        IDfCollection ficheros;
        ArrayList<AtributosDocumentum> listaficheros = new ArrayList<>();
        IDfSession sesion = conectarDocumentum();
        if (sesion == null) {
            if (ERROR.isEmpty()) {
                ERROR = "Error al crear sesión en Documentum (ListarFicheros)";
            }
            return listaficheros;
        }
        ERROR = "";
        try {
            IDfId idObj = sesion.getIdByQualification("dm_sysobject where object_name='" + carpeta + "'");
            IDfSysObject sysObj = (IDfSysObject) sesion.getObject(idObj);
            IDfFolder myFolder = sesion.getFolderByPath(sysObj.getRepeatingString("r_folder_path", 0));
            ficheros = myFolder.getContents(null);

            int conta = 0;
            Boolean seguir = true;
            while (ficheros.next() && seguir) {
                conta++;
                AtributosDocumentum datos = new AtributosDocumentum();
                IDfTypedObject doc = ficheros.getTypedObject();
//                Utilidades.escribeLog(doc.getString("object_name") + " - " + doc.getString("r_object_id"));
                datos.setNombre(doc.getString("object_name"));
                datos.setValor(doc.getString("r_object_id"));
                datos.setTipoobjeto(doc.getString("r_object_type"));
                ventanapadre.EtiquetaEstado.setText(doc.getString("object_name"));
                ventanapadre.getBarradocum().labelMensa.setText("Registro(s): " + conta);
                if (ventanapadre.getBarradocum().PARAR) {
                    seguir = false;
                    ventanapadre.getBarradocum().setPARAR(false);
                }
                ArrayList<AtributosDocumentum> atris = DameTodosAtributos(doc.getString("r_object_id"));
                if (atris.size() > 0) {
                    for (int n = 0; n < atris.size(); n++) {
                        if (atris.get(n).getNombre().equalsIgnoreCase("owner_name")) {
                            // datos[n][1] = atributos.get(n).getValor();
                            datos.setUsuario(atris.get(n).getValor());
                        }
                        if (atris.get(n).getNombre().equalsIgnoreCase("r_creation_date")) {
                            // datos[n][1] = atributos.get(n).getValor();
                            datos.setFechacreacion(atris.get(n).getValor());
                        }
                    }
                    datos.setCheckin(!((IDfSysObject) sesion.getObject(new DfId(doc.getString("r_object_id")))).isCheckedOut());
                    listaficheros.add(datos);
                }
//                ArrayList resultado = DameAtributo(doc.getString("r_object_id"), "r_creation_date");
//                if (resultado.size() > 0) {
//                    datos.setFechacreacion(resultado.get(0).toString());
//                    datos.setCheckin(!((IDfSysObject) sesion.getObject(new DfId(doc.getString("r_object_id")))).isCheckedOut());
//                    listaficheros.add(datos);
//                }
            }

        } catch (DfException ex) {
            if (!ex.getMessage().contains("Bad ID given: 0000000000000000")) {
                Utilidades.escribeLog("Error al listar directorio: " + ex.getMessage());
                ERROR = "Error al listar directorio " + carpeta + " (ListarFicheros) - Error: " + ex.getMessage();
            } else {
                Utilidades.escribeLog("No existe la carpeta " + carpeta + " en Documentum");
                ERROR = "No existe la carpeta " + carpeta + " en Documentum";
            }
            return new ArrayList<>();
        }

        try {
            sesion.disconnect();
        } catch (DfException ex) {
            Utilidades.escribeLog("Error al desconectar la sesión en Documentum " + " - " + ex.getMessage());
            ERROR = "Error al desconectar la sesión en Documentum (ListarFicheros)  - Error: " + ex.getMessage();
            return new ArrayList<>();
        }
        return listaficheros;
    }

    public ArrayList<AtributosDocumentum> ListarFicherosRuta(String ruta) {
        IDfCollection ficheros;
        ArrayList<AtributosDocumentum> listaficheros = new ArrayList<>();
        IDfSession sesion = conectarDocumentum();
        if (sesion == null) {
            if (ERROR.isEmpty()) {
                ERROR = "Error al crear sesión en Documentum (ListarFicherosRuta)";
            }
            return listaficheros;
        }
        ERROR = "";
        try {
            String dql;
            if (ruta.equals("/")) {
                dql = "select * From dm_sysobject where r_object_type='dm_cabinet'";
                ficheros = ejecutarDql(dql, sesion);
            } else {
                IDfFolder folder = (IDfFolder) sesion.getObjectByPath(ruta);
                IDfId idfolder = folder.getId("r_object_id");
                dql = "dm_sysobject where r_object_id='" + idfolder.toString() + "'";
                IDfId idObj = sesion.getIdByQualification(dql);
                IDfSysObject sysObj = (IDfSysObject) sesion.getObject(idObj);
                IDfFolder myFolder = sesion.getFolderByPath(sysObj.getRepeatingString("r_folder_path", 0));
                ficheros = myFolder.getContents(null);
            }
            int conta = 0;
            Boolean seguir = true;

            Long valor = numeroObjetosDirectorio(sesion, ruta);
            String numficheros = String.valueOf(valor);

            while (ficheros.next() && seguir) {
                conta++;
                AtributosDocumentum datos = new AtributosDocumentum();
                IDfTypedObject doc = ficheros.getTypedObject();
//                Utilidades.escribeLog(doc.getString("object_name") + " - " + doc.getString("r_object_id"));
                datos.setNombre(doc.getString("object_name"));
                datos.setValor(doc.getString("r_object_id"));
                datos.setTipoobjeto(doc.getString("r_object_type"));
                ventanapadre.EtiquetaEstado.setText(doc.getString("object_name"));
                //   String textocuenta = numficheros.equals("0") ? "Registro(s): " + conta : "Registro(s): " + conta + " de " + numficheros;
                String textocuenta = "Registro(s): " + conta + " de " + numficheros;
                ventanapadre.getBarradocum().labelMensa.setText(textocuenta);
                if (ventanapadre.getBarradocum().PARAR) {
                    seguir = false;
                    ventanapadre.getBarradocum().setPARAR(false);
                    ventanapadre.getBarradocum().dispose();
                }
                ArrayList<AtributosDocumentum> atris = DameTodosAtributos(doc.getString("r_object_id"));
                if (atris.size() > 0) {
                    for (int n = 0; n < atris.size(); n++) {
                        if (atris.get(n).getNombre().equalsIgnoreCase("owner_name")) {
                            // datos[n][1] = atributos.get(n).getValor();
                            datos.setUsuario(atris.get(n).getValor());
                        }
                        if (atris.get(n).getNombre().equalsIgnoreCase("r_creation_date")) {
                            // datos[n][1] = atributos.get(n).getValor();
                            datos.setFechacreacion(atris.get(n).getValor());
                        }
                    }
                    datos.setCheckin(!((IDfSysObject) sesion.getObject(new DfId(doc.getString("r_object_id")))).isCheckedOut());
                    listaficheros.add(datos);
                }
//                ArrayList resultado = DameAtributo(doc.getString("r_object_id"), "r_creation_date");
//                if (resultado.size() > 0) {
//                    String fechacreacion = resultado.get(0).toString();
//                    datos.setFechacreacion(fechacreacion);
//                    datos.setCheckin(!((IDfSysObject) sesion.getObject(new DfId(doc.getString("r_object_id")))).isCheckedOut());
//                    listaficheros.add(datos);
//                }
            }

        } catch (DfException ex) {
            Utilidades.escribeLog("Error al listar directorio " + ruta + " (ListarFicherosRuta) - Error: " + ex.getMessage());
            if (ex.getMessage() != null) {
                ERROR = "Error al listar directorio " + ruta + " (ListarFicherosRuta) - Error: " + ex.getMessage();
                if (ex.getMessage().contains("[DM_API_E_EXIST]") || ex.getMessage().contains("Index: 0, Size: 0")) {
                    ERROR = "Error al leer atributos: " + ex.getMessage();
                    return listaficheros;
                }
            } else {
                ERROR = "Ruta " + ruta + " no encontrada en Documentum.";
            }
            return new ArrayList<>();
        }

        try {
            sesion.disconnect();
        } catch (DfException ex) {
            Utilidades.escribeLog("Error al desconectar la sesión en Documentum (ListarFicherosRuta) - Error: " + ex.getMessage());
            ERROR = "Error al desconectar la sesión en Documentum (ListarFicherosRuta) - Error: " + ex.getMessage();
            return new ArrayList<>();
        }

        return listaficheros;
    }

    public ArrayList<AtributosDocumentum> ListarFicherosId(String id) {
        Boolean Escarpeta = id.toLowerCase().startsWith("0b");
        Boolean Escabinet = id.toLowerCase().startsWith("0c");
        IDfCollection ficheros;
        ArrayList<AtributosDocumentum> listaficheros = new ArrayList<>();
        IDfSession sesion = conectarDocumentum();
        if (sesion == null) {
            if (ERROR.isEmpty()) {
                ERROR = "Error al crear sesión en Documentum (ListarFicheros)";
            }
            return listaficheros;
        }
        ERROR = "";
        try {
            IDfId idObj = sesion.getIdByQualification("dm_sysobject where r_object_id='" + id + "'");
            IDfSysObject sysObj = (IDfSysObject) sesion.getObject(idObj);
            IDfFolder myFolder;
            if (Escarpeta) {
                myFolder = sesion.getFolderBySpecification(id);
            } else if (Escabinet) {
                myFolder = sesion.getFolderByPath(sysObj.getRepeatingString("r_folder_path", 0));
            } else {
                myFolder = sesion.getFolderBySpecification(sysObj.getRepeatingString("i_folder_id", 0));
            }
            ficheros = myFolder.getContents(null);

            while (ficheros.next()) {
                AtributosDocumentum datos = new AtributosDocumentum();
                IDfTypedObject doc = ficheros.getTypedObject();

                if (!doc.getString("r_object_id").equals(id) && !Escarpeta && !Escabinet) {
                    //     continue;
                } else {
//                Utilidades.escribeLog(doc.getString("object_name") + " - " + doc.getString("r_object_id"));
                    datos.setNombre(doc.getString("object_name"));
                    datos.setValor(doc.getString("r_object_id"));
                    datos.setTipoobjeto(doc.getString("r_object_type"));
                    ArrayList<AtributosDocumentum> atris = DameTodosAtributos(doc.getString("r_object_id"));
                    if (atris.size() > 0) {
                        for (int n = 0; n < atris.size(); n++) {
                            if (atris.get(n).getNombre().equalsIgnoreCase("owner_name")) {
                                // datos[n][1] = atributos.get(n).getValor();
                                datos.setUsuario(atris.get(n).getValor());
                            }
                            if (atris.get(n).getNombre().equalsIgnoreCase("r_creation_date")) {
                                // datos[n][1] = atributos.get(n).getValor();
                                datos.setFechacreacion(atris.get(n).getValor());
                            }
                        }
                        datos.setCheckin(!((IDfSysObject) sesion.getObject(new DfId(doc.getString("r_object_id")))).isCheckedOut());
                        listaficheros.add(datos);
                    }
//                    ArrayList resultado = DameAtributo(doc.getString("r_object_id"), "r_creation_date");
//                    if (resultado.size() > 0) {
//                        String fechacreacion = resultado.get(0).toString();
//                        datos.setFechacreacion(fechacreacion);
//                        datos.setCheckin(!((IDfSysObject) sesion.getObject(new DfId(doc.getString("r_object_id")))).isCheckedOut());
//                        listaficheros.add(datos);
//                    }
                    if (!Escarpeta && !Escabinet) {
                        break;
                    }
                }
            }

        } catch (DfException ex) {
            Utilidades.escribeLog("Error al buscar por el id " + id + "  - Error: " + ex.getMessage());
            ERROR = "Error al buscar por el id " + id + " (ListarFicherosID) - Error: " + ex.getMessage();
            return new ArrayList<>();
        }

        try {
            sesion.disconnect();
        } catch (DfException ex) {
            Utilidades.escribeLog("Error al desconectar la sesión en Documentum " + " - " + ex.getMessage());
            ERROR = "Error al desconectar la sesión en Documentum (ListarFicherosID)  - Error: " + ex.getMessage();
            return new ArrayList<>();
        }
        return listaficheros;
    }

    public String GuardarFichero(String r_object_id, String directorio) {
        IDfSession sesion = conectarDocumentum();
        if (sesion == null) {
            if (ERROR.isEmpty()) {
                ERROR = "No se pudo obtener sesión de Documentum (GuardarFichero)";
            }
            return "No se pudo obtener sesión de Documentum (GuardarFichero)";
        }
        ERROR = "";
        try {
            // Creamos una instancia del cliente.
            IDfClientX clientx = new DfClientX();
            // Creamos una instancia para exportar del tipo  IDfExportOperation
            IDfExportOperation eo = clientx.getExportOperation();
            // Obtenemos el id del documeto
            IDfId id = new DfId(r_object_id);
            //  IDfDocument doc = (IDfDocument) sesion.getObject(id);
            // Creamos un objeto IDfPersistentObject para asignarle el documento
            IDfPersistentObject doc = (IDfPersistentObject) sesion.getObject(id);
            // Creamos un nodo de exportación y se añade el documento
            IDfExportNode node = (IDfExportNode) eo.add(doc);
            // Se añade el separador a la ruta si es necesario
            if (directorio.lastIndexOf("/")
                    != directorio.length() - 1
                    && directorio.lastIndexOf("\\")
                    != directorio.length() - 1) {
                directorio += util.separador();
            }

            // Formato y extensión del documento
            String tipoFichero = doc.getValue("a_content_type").asString();
            String formato = dameExtension(tipoFichero);
            // Nombre del documento  
            String nombre = doc.getValue("object_name").asString();
            // En el nombre del documento cambiamos caracteres no validos ("/" y ":") por "-"
            nombre = nombre.replaceAll("/", "-").replaceAll(":", "-");
            // Si el documento no lleva extensión se la ponemos
            if (nombre.endsWith(formato)) {
                node.setFilePath(directorio + nombre);
            } else {
                node.setFilePath(directorio + nombre + "." + formato);
            }
            // Se ejecuta la exportación y se recoge el resultado
            if (eo.execute()) {
                IDfXmlQuery xmlQuery = clientx.getXmlQuery();
                String tipodocu = doc.getValue("r_object_type").asString();
                xmlQuery.setDql("select * from " + tipodocu + " where r_object_id='" + r_object_id + "'");
                xmlQuery.includeContent(false);
                xmlQuery.execute(IDfQuery.DF_READ_QUERY, sesion);
                FileOutputStream fos = new FileOutputStream(directorio + nombre + "-metadatos.xml");
                xmlQuery.getXMLString(fos);
                fos.flush();
                fos.close();
                if (nombre.endsWith(formato)) {
                    return "OK. Exportado " + directorio + nombre;
                } else {
                    return "OK. Exportado " + directorio + nombre + "." + formato;
                }
            } else {
                return "Exportación fallida.";
            }
        } catch (DfException | IOException ex) {
            Utilidades.escribeLog("Error al exportar de Documentum - Error: " + ex.getMessage());
            ERROR = "Error al exportar de Documentum (GuardarFichero) - Error: " + ex.getMessage();
        }

        try {
            sesion.disconnect();
        } catch (DfException ex) {
            Utilidades.escribeLog("Error al desconectar la sesión en Documentum (GuardarFichero) " + " - " + ex.getMessage());
            ERROR = "Error al desconectar la sesión en Documentum (GuardarFichero) " + " - " + ex.getMessage();
        }

        return "Exportado de Documentum";
    }

//    public void checkoutDoc(String objectId, IDfSession sesion) {
//
//        try {
//            IDfSysObject sysObject = (IDfSysObject) sesion.getObject(new DfId(objectId));
//            if (!sysObject.isCheckedOut()) // if it is not checked out
//            {
//                String directorio = util.dirBase() + util.separador() + "Documentum" + util.separador()
//                        + "Checkout" + util.separador();
//                util.crearDirectorio(directorio);
//                GuardarFichero(objectId, directorio);
//                
//                sysObject.checkout();
//            }
//
//            //   System.out.println("is Check out " + sysObject.isCheckedOut());
//        } catch (DfException ex) {
//            Utilidades.escribeLog("Error al hacer check out de " + objectId + "  -  Error " + ex.getMessage());
//        }
//    }
    public String checkoutDoc(String objectId, IDfSession sesion) {
        String resultado;
        try {
            IDfClientX clientx = new DfClientX();
// Use the factory method to create a checkout operation object.
            IDfCheckoutOperation coOp = clientx.getCheckoutOperation();
// Set the location where the local copy of the checked out file is stored.
            coOp.setDestinationDirectory(util.dirBase() + util.separador() + "Documentum" + util.separador()
                    + "Checkout" + util.separador());
// Get the document instance using the document ID.
            IDfDocument doc = (IDfDocument) sesion.getObject(new DfId(objectId));
// Create an empty checkout node object.
            IDfCheckoutNode coNode;
// If the doc is a virtual document, instantiate it as a virtual document
// object and add it to the checkout operation. Otherwise, add the document
// object to the checkout operation.
            if (doc.isVirtualDocument()) {
                IDfVirtualDocument vDoc = doc.asVirtualDocument("CURRENT", false);
                coNode = (IDfCheckoutNode) coOp.add(vDoc);
            } else {
                coNode = (IDfCheckoutNode) coOp.add(doc);
            }

            if (coNode == null) {
                resultado = ("coNode is null");
            }
// Execute the checkout operation. Return the result.
            if (coOp.execute()) {
                resultado = "Successfully checked out file ID: " + objectId;
            } else {
                resultado = ("Checkout failed.");
            }

        } catch (DfException ex) {
            Utilidades.escribeLog("Error al hacer checkout de " + objectId + " - Error: " + ex.getMessage());
            return "Error al hacer checkout de " + objectId + " - Error: " + ex.getMessage();
        }

        return resultado;
    }

    public String cancelCheckout(String objectId, IDfSession sesion) {
        String resultado = "";
        try {
            IDfSysObject sysObject = (IDfSysObject) sesion.getObject(new DfId(objectId));
            IDfClientX clientx = new DfClientX();
            IDfCancelCheckoutOperation cco = clientx.getCancelCheckoutOperation();
            IDfDocument doc = (IDfDocument) sesion.getObject(new DfId(objectId));
// Indicate whether to keep the local file.
            cco.setKeepLocalFile(false);
            IDfCancelCheckoutNode node;
            node = (IDfCancelCheckoutNode) cco.add(doc);
            if (node == null) {
                resultado = "Node is null";
            }
            if (!cco.execute()) {
                resultado = "Operation failed";
            }
        } catch (DfException ex) {
            Utilidades.escribeLog("Error al cancelar check out de " + objectId + "  -  Error " + ex.getMessage());
        }
        return resultado;
    }

    public String checkinDoc(String objectId, IDfSession sesion, String fichero, String version, String descripcion, Boolean indexar) {
        String resultado = "";
        try {
            IDfSysObject sysObject = (IDfSysObject) sesion.getObject(new DfId(objectId));
            if (sysObject.isCheckedOut()) {
                sysObject.setObjectName(fichero);
                sysObject.setTitle(descripcion);
                sysObject.setFullText(indexar);
                IDfVersionPolicy vp = sysObject.getVersionPolicy();
                IDfClientX clientx = new DfClientX();
                IDfCheckinOperation operation = clientx.getCheckinOperation();
                IDfCheckinNode node;

                if (sysObject.isVirtualDocument()) {
                    IDfVirtualDocument vDoc = sysObject.asVirtualDocument("CURRENT", false);
                    node = (IDfCheckinNode) operation.add(vDoc);
                } else {
                    IDfDocument doc = (IDfDocument) sesion.getObject(new DfId(objectId));
                    node = (IDfCheckinNode) operation.add(doc);
                }
                node.setFilePath(util.dirBase() + util.separador() + "Documentum" + util.separador()
                        + "Checkout" + util.separador() + fichero);
                switch (version) {
                    case "igual":
                        node.setCheckinVersion(IDfCheckinOperation.SAME_VERSION);
                        break;
                    case "mayor":
                        node.setCheckinVersion(IDfCheckinOperation.NEXT_MAJOR);
                        break;
                    case "menor":
                        node.setCheckinVersion(IDfCheckinOperation.NEXT_MINOR);
                        break;
                }
                node.setVersionLabels("CURRENT");
                if (operation.execute()) {
                    resultado = "Check In realizado";
                } else {
                    resultado = "Check In fallido";
                }
                IDfId newId = node.getNewObjectId();
                Utilidades.escribeLog("Nuevo ID tras el check in: " + newId.getId());
            }
        } catch (DfException ex) {
            Utilidades.escribeLog("Error al hacer check in de " + objectId + "  -  Error " + ex.getMessage());
            resultado = "Check In fallido";
        }
        return resultado;
    }

    public boolean estaCheckin(String r_object_id) {
        IDfSession sesion = conectarDocumentum();
        if (sesion == null) {
            Utilidades.escribeLog("No se pudo obtener sesión de Documentum (estaCheckin)");

        }
        try {
            IDfSysObject sysObject = (IDfSysObject) sesion.getObject(new DfId(r_object_id));
            if (sysObject.isCheckedOut()) {
                return false;
            }
        } catch (DfException ex) {
            Utilidades.escribeLog("Error al comprobar check in de " + r_object_id + "  -  Error " + ex.getMessage());
        }
        return true;
    }

    public static void main(String s[]) {
        //    prueba();
        Utilidades util = new Utilidades();
        String dirdfc = util.usuarioHome() + util.separador() + "documentumdfcs" + util.separador() + "documentum" + util.separador() + "shared" + util.separador();
        String clave = "";
        try {
            clave = RegistryPasswordUtils.encrypt("dm_bof_registry");
            ClassPathUpdater.add(dirdfc);
            ClassPathUpdater.add(dirdfc + "lib" + util.separador() + "jsafeFIPS.jar");
            System.out.println("Encriptado (dm_bof_registry) :" + clave);
            System.out.println("Desencriptado: " + RegistryPasswordUtils.decrypt(clave));
            System.out.println("Desencriptado de AAAAEDTO1gR1FkBjTjXeAgcuyWkdJowaHnWB9Bq8BYZNTBzm --> " + RegistryPasswordUtils.decrypt("AAAAEDTO1gR1FkBjTjXeAgcuyWkdJowaHnWB9Bq8BYZNTBzm"));
            String desencriptarPassword = desencriptarPassword("AAAAEDTO1gR1FkBjTjXeAgcuyWkdJowaHnWB9Bq8BYZNTBzm");
        } catch (IOException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
            Utilidades.escribeLog("Error al actualizar el Classpath  - Error: " + ex.getMessage());
        } catch (DfException ex) {
            Logger.getLogger(UtilidadesDocumentum.class.getName()).log(Level.SEVERE, null, ex);
        }

        UtilidadesDocumentum ed = new UtilidadesDocumentum(dirdfc + "dfc.properties");
        //IDfSession sesion = ed.conectarDocumentum("dmadmin", "documentum", "I_A1_RFC", "vilcs270.dcsi.adif", "1489");
//        IDfSession sesion = ed.conectarDocumentum("dmadmin", "documentum", "prudcm1", "vilcs405", "1489");
        IDfSession sesion = ed.conectarDocumentum("dmadmin", "password", "MyRepo", "demo-server", "1489");

        Boolean es = ed.esTablaRegistrada("xxxtblxxx_39015038", sesion);

        Map<String, String> relaciones = new HashMap<>();

        //     relaciones = ed.DameRelation("0900d8ff8000aac7", sesion);
        System.out.println(relaciones.get("Tipo"));
        System.out.println(relaciones.get("NombreRelacion"));
        System.out.println(relaciones.get("IdRelacion"));
        System.out.println(relaciones.get("IdPadre"));
        System.out.println(relaciones.get("IdHijo"));
        System.out.println(relaciones.get("RutaPadreDocumentum"));
        System.out.println(relaciones.get("RutaHijoDocumentum"));
        System.out.println(relaciones.get("RutaPadreSSOO"));
        System.out.println(relaciones.get("RutaHijoSSOO"));

        /*

        Long valor = ed.numeroObjetosDirectorio(sesion, "/Archivo Digital/Documentacion Administrativa/CEDICO/Giros/2017/08/19");

        ArrayList renditions = ed.dameRenditions(sesion, "09000816802c8d48");

        for (int n = 0; n < renditions.size(); n++) {
            ArrayList datos = (ArrayList) renditions.get(n);
            for (int i = 0; i < datos.size(); i++) {
                System.out.println(datos.get(i));
            }
        }

        String resul = "";
        try {
            resul = ed.estadoIndexAgent(sesion);

        } catch (DfException ex) {
            Logger.getLogger(UtilidadesDocumentum.class.getName()).log(Level.SEVERE, null, ex);
        }
         */
        //    ed.exportarCarpeta("/System", "f:\\tmp");
        /*
        IDfFolder carpeta = null;
        try {
            carpeta = sesion.getFolderByPath("/JMETER");
        } catch (DfException ex) {
            Utilidades.escribeLog("Error recuperar carpeta - Error: " + ex.getMessage());
        }

        try {
            ed.deepExportFolder(carpeta.getObjectId(), "c:/tmp");
        } catch (DfException ex) {
            Utilidades.escribeLog("Error exportar carpeta - Error: " + ex.getMessage());
        }
         */
//        try {
//            String desencriptarPassword;
//            desencriptarPassword = desencriptarPassword("GrRNPhLJrkoTDAZE0RGJow\\=\\=");
//
//        } catch (Exception ex) {
//
//        }
//        String fulldir = "C:\\Users\\julian\\digita\\pendientes\\20120825-124031-192168001027\\";
//        String filename = "Comunicados_Recepción.pdf";
//        String fullPath = fulldir + filename;
//        Utilidades.escribeLog("full path = " + fullPath);
        try {
//            ed.subirDocumento( sesion,fullPath, "2012/08/29/20120825-124031-192168001027");
            String directorio = "2012/08/29/20120825-124031-192168001027";
//        Folder dir = ea.dameCarpeta("2012/08/29/20120822-111919-010001074110");
//        if (dir != null) {
//            Utilidades.escribeLog(dir.getName());
//        }
            //    ed.subirDocumentosDocumentum(directorio, "C:\\Users\\julian\\digita\\pendientes\\20120825-124031-192168001027\\");
//        ed.subirDocumentosDocumentum(directorio, "C:\\Documents and Settings\\julian.collado\\digita\\pendientes\\20120822-111919-010001074110\\");

            //   ed.LeerDocumento("0901e3758006f7f8");
            Utilidades.escribeLog(ed.DameVersionDocumentum());

            ArrayList resultado = ed.DameAtributo("090fa3598000511c", "title");
            for (int i = 0; i < resultado.size(); i++) {
                Utilidades.escribeLog((String) resultado.get(i));
            }

            resultado = ed.DameAtributo("090fa3598000511c", "r_folder_path");
            for (int i = 0; i < resultado.size(); i++) {
                Utilidades.escribeLog((String) resultado.get(i));
            }

            ArrayList<AtributosDocumentum> atri = ed.DameTodosAtributos("090fa3598000511c");
            Utilidades.escribeLog(ed.DumpAtributos("090fa3598000511c"));
            System.out.println(util.codificaBase64(util.asciiToHex("21thcfox")));

//            ed.ListarFicheros("289920100141552");
//            Utilidades.escribeLog(ed.GuardarFichero("0901e3758006f7f8", "c:\\tmp"));
            IDfCollection col = ed.ejecutarDql("Select * from dm_format");
            ArrayList filas = new ArrayList();
            while (col.next()) {
                filas.add(col.getTypedObject());
            }

            int cont = filas.size();
            IDfTypedObject primerafila = (IDfTypedObject) filas.get(0);
            int tam = primerafila.getAttrCount();
            Object[] cabecera = new Object[tam];
            Object[][] datos = new Object[cont][tam];
            for (int l = 0; l < tam; l++) {
                cabecera[l] = primerafila.getAttr(l).getName();
            }

            for (int i = 0; i < cont; i++) {
                IDfTypedObject row = (IDfTypedObject) filas.get(i);

                for (int n = 0; n < row.getAttrCount(); n++) {
                    IDfAttr attr = row.getAttr(n);
                    IDfValue attrValue = row.getValue(attr.getName());

                    datos[i][n] = getDfObjectValue(attrValue);
                }

            }

            col.close();

            sesion.disconnect();

        } catch (DfException ex) {
            Utilidades.escribeLog("Error al ejecutar 'subirDocumentosDocumentum' " + " - " + ex.getMessage());
        } finally {
        }
    }

    public static Object getDfObjectValue(IDfValue value) {
        // Sanity check
        if (value == null) {
            return null;
        }

        // Recover the IDfValue's data type, so the correct 
        // method can be called to extract the value.
        switch (value.getDataType()) {
            case IDfValue.DF_BOOLEAN:
                return value.asBoolean() ? "true" : "false";

            case IDfValue.DF_DOUBLE:
                return Double.toString(value.asDouble());

            case IDfValue.DF_ID:
                return value.asId().toString();

            case IDfValue.DF_INTEGER:
                return Integer.toString(value.asInteger());

            case IDfValue.DF_STRING:
                return value.asString();

            case IDfValue.DF_TIME:
                return value.asTime().toString();

            case IDfValue.DF_UNDEFINED:
                return value;

            default:
                return value;
        }
    }

    private String dameTipo(String fichero) {
        String tipo = "";
        String extension = Files.getFileExtension(fichero);

        switch (extension) {
            case "txt":
                tipo = "crtext";
                break;
            case "xls":
                tipo = "excel8book";
                break;
            case "doc":
                tipo = "msw8";
                break;
            case "ppt":
                tipo = "ppt8";
                break;
            case "vsd":
                tipo = "vsd";
                break;
            case "zip":
                tipo = "zip";
                break;
            case "wpd":
                tipo = "wp8";
                break;
            case "psd":
                tipo = "photoshop6";
                break;
            case "au":
                tipo = "audio";
                break;
            case "jpeg":
                tipo = "jpeg";
                break;
            case "jpg":
                tipo = "jpeg";
                break;
            case "html":
                tipo = "html";
                break;
            case "htm":
                tipo = "html";
                break;
            case "ai":
                tipo = "illustrator10";
                break;
            default:
                try {
                    IDfCollection col = ejecutarDql("SELECT name from dm_format WHERE dos_extension = lower('" + extension + "')");
                    col.next();
                    IDfTypedObject row = (IDfTypedObject) col.getTypedObject();
                    IDfValue attrValue = row.getValue("name");
                    tipo = getDfObjectValue(attrValue).toString();
                } catch (Exception e) {
                    tipo = "unknown";
                }
        }
        return tipo;
    }

    private String dameExtension(String tipo) {
        String extension;
        try {
            IDfCollection col = ejecutarDql("SELECT dos_extension  from dm_format WHERE name = lower('" + tipo + "')");
            col.next();
            IDfTypedObject row = (IDfTypedObject) col.getTypedObject();
            IDfValue attrValue = row.getValue("dos_extension");
            extension = getDfObjectValue(attrValue).toString();
        } catch (Exception e) {
            extension = "";
        }
        return extension;
    }

    public ArrayList dameRenditions(IDfSession sesion, String r_object_id) {
        createFolder(dir);

        ArrayList lista = new ArrayList();
        try {
            IDfSysObject doc = (IDfSysObject) sesion.getObject(new DfId(r_object_id));
            IDfCollection myColl = doc.getRenditions("full_format,r_object_id");
            while (myColl.next()) {
                IDfFormat myFormat = sesion.getFormat(myColl.getString("full_format"));
//                System.out.println("Rendition format: " + myFormat.getDescription() + "   -   " + myFormat.getName());
//                System.out.println("Object id: " + myColl.getString("r_object_id"));
                IDfPersistentObject rendDoc = (IDfPersistentObject) sesion.getObject(new DfId(myColl.getString("r_object_id")));
                int pageNbr = rendDoc.getInt("page");
                String pageModifier = rendDoc.getString("page_modifier");
                String nombre = doc.getFileEx2(dir + "rendition-" + r_object_id + "-" + myFormat.getName() + "-" + rendDoc.getString("page_modifier") + "." + myFormat.getDOSExtension(), myFormat.getName(), pageNbr, pageModifier, false);
//                System.out.println("Nombre: " + nombre);
//                System.out.println("Tamaño del fichero: " + rendDoc.getString("full_content_size"));
//                System.out.println("Fecha modificación: " + rendDoc.getString("set_time"));
//                System.out.println(rendDoc.getString("storage_id"));
//                System.out.println("Filestore: " + dameFilestore(sesion, rendDoc.getString("storage_id")));
                ArrayList datos = new ArrayList();
                datos.add(myFormat.getDescription());
                datos.add(myFormat.getName());
                datos.add(myColl.getString("r_object_id"));
                datos.add(nombre);
                datos.add(rendDoc.getString("full_content_size"));
                datos.add(rendDoc.getString("set_time"));
                datos.add(dameFilestore(sesion, rendDoc.getString("storage_id")));
                lista.add(datos);
            }
            myColl.close();
        } catch (DfException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return lista;

        /*
        
              for (int n = 0; n < renditions.size(); n++) {
            ArrayList datos = (ArrayList) renditions.get(n);
            for (int i = 0; i < datos.size(); i++) {
                System.out.println(datos.get(i));
            }
        }
         */
    }

    public ArrayList dameJobs(IDfSession sesion) {
        ArrayList lista = new ArrayList();
        try {
            String dql = "select object_name,subject,title,a_last_completion,is_inactive,a_current_status,r_object_id from dm_job_sp order by object_name";
            IDfCollection myColl = ejecutarDql(dql, sesion);
            if (myColl == null) {
                return lista;
            }
            while (myColl.next()) {
                ArrayList datos = new ArrayList();
                datos.add(myColl.getString("object_name"));
                datos.add(myColl.getString("subject"));
                datos.add(myColl.getString("title"));
                datos.add(myColl.getString("a_last_completion").equals("nulldate") ? "" : myColl.getString("a_last_completion"));
                datos.add(myColl.getString("is_inactive").equals("0") ? "Activo" : "Inactivo");
                datos.add(myColl.getString("a_current_status"));
                datos.add(myColl.getString("r_object_id"));
                lista.add(datos);
            }
            myColl.close();
        } catch (DfException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return lista;
    }

    public String dameFilestore(IDfSession sesion, String r_object_id) {
        String filestore = "";
        IDfCollection resultado = ejecutarDql("select name from dm_filestore where r_object_id='" + r_object_id + "'", sesion);
        try {
            resultado.next();
            filestore = resultado.getString("name");
        } catch (DfException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return filestore;
    }

    public Long numeroObjetosDirectorio(IDfSession sesion, String dir) {
        Long valor = 0L;
        Long valordir = 0L;
        String dql = "SELECT count(*) as numero FROM dm_sysobject WHERE FOLDER('" + dir + "')";
//        String dql = "SELECT count(r_object_id) as numero FROM dm_document(all) WHERE FOLDER('" + dir + "')";
        if (dir.equals("/")) {
            dql = "select count(*) as numero From dm_sysobject where r_object_type='dm_cabinet'";
        }
        IDfCollection resultado = ejecutarDql(dql, sesion);
        try {
            resultado.next();
            valor = Long.parseLong(resultado.getString("numero"));
        } catch (DfException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
//        String dql2 = "SELECT count(r_object_id) as numero FROM dm_folder(all) WHERE FOLDER('" + dir + "')";
//        if (dir.equals("/")) {
//            dql2 = "select count(*) as numero From dm_sysobject where r_object_type='dm_cabinet'";
//        }
//        IDfCollection resultado2 = ejecutarDql(dql2, sesion);
//        try {
//            resultado2.next();
//            valordir = Long.parseLong(resultado2.getString("numero"));
//        } catch (DfException ex) {
//            System.out.println("Error: " + ex.getMessage());
//        }
        valor = valor + valordir;
        return valor;
    }

    public Boolean convierteDocumento(IDfSession sesion, String r_object_id, String formato) {
        Boolean resultado = true;
        try {
            IDfSysObject sysObj = (IDfSysObject) sesion.getObject(new DfId(r_object_id));
            IDfId RenderedQueueID = sysObj.queue("dm_autorender_win31", "rendition", 0, false, new com.documentum.fc.common.DfTime("nulldate"), "rendition_req_ps_pdf");

            if (RenderedQueueID.isNull()) {
                resultado = false;
            }
        } catch (DfException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return resultado;
    }

    public Boolean hayADTS() {
        try {
            IDfCollection col = ejecutarDql("select status from cts_instance_info");
            if (col == null) {
                return false;
            }
            String valor = "";

            while (col.next()) {
                IDfTypedObject row = (IDfTypedObject) col.getTypedObject();
                IDfValue attrValue = row.getValue("status");
                valor = getDfObjectValue(attrValue).toString();
            }
            col.close();
            if (valor.equals("") || !valor.equals("1")) {
                return false;
            }
        } catch (DfException Ex) {
            return false;
        }
        return true;
    }

    public String dameFTIndex(IDfSession sesion) {
        String nombre = "";
        IDfCollection col = ejecutarDql("select index_name from dm_fulltext_index", sesion);
        try {
            while (col.next()) {
                nombre = col.getValue(col.getAttr(0).getName()).toString();
            }
            col.close();
        } catch (DfException ex) {
            Utilidades.escribeLog("Error al consultar el FTIndex name. (dameFTIndex) Error: " + ex.getMessage());
        }

        return nombre;
    }

    public String dameIndexAgent(IDfSession sesion) {
        String nombre = "";
        IDfCollection col = ejecutarDql("select object_name from dm_ftindex_agent_config", sesion);
        try {
            while (col.next()) {
                nombre = col.getValue(col.getAttr(0).getName()).toString();
            }
            col.close();
        } catch (DfException ex) {
            Utilidades.escribeLog("Error al consultar el FTIndex name. (dameFTIndex) Error: " + ex.getMessage());
        }

        return nombre;
    }

    public ArrayList relanzarTareasFallidasIndexador(IDfSession sesion) {
        IDfCollection col = ejecutarDql("select item_id from dmi_queue_item where name = 'dm_fulltext_index_user' and  task_state='failed'", sesion);
        ArrayList salida = new ArrayList();
        if (col == null) {
            return salida;
        }
        try {
            while (col.next()) {
                IDfTypedObject row = (IDfTypedObject) col.getTypedObject();
                String r_object_id = row.getValueAt(0).asString();
                //  System.out.println("r_object_id: " + r_object_id);
                salida.add("r_object_id: " + r_object_id);
                String dql = "update dmi_queue_item objects set task_state ='' where name = 'dm_fulltext_index_user' and task_state = 'failed' and item_id='" + r_object_id + "'";
                ejecutarDql(dql, sesion);
            }
            col.close();
        } catch (DfException ex) {
            Utilidades.escribeLog("Error al relanzar tareas del indexador - Error: " + ex.getMessage());
        }
        return salida;
    }

    public static String desencriptarPassword(String clave) {
        String valor = "";
        try {
            valor = com.documentum.fc.tools.RegistryPasswordUtils.decrypt(clave);
            System.out.println(valor);
//            valor = com.documentum.dmcl.impl.DmclApi.getInstance().get("decrypttext,c,DM_ENCR_TEXT=" + clave);
//            System.out.println(valor);
        } catch (DfException e) {
            System.out.println("Error al dessencriptar (desencriptarPassword): " + e.getMessage());
        }
        return valor;
    }

    public void setLdapPassword(String password) {
        IDfSession session = conectarDocumentum();
        if (session == null) {
            if (ERROR.isEmpty()) {
                ERROR = "No se pudo obtener sesión de Documentum (setLdapPassword)";
            }
            return;
        }
        ERROR = "";
        String ldapId;
        try {
            IDfCollection col = ejecutarDql("SELECT r_object_id  from dm_sysobject WHERE r_object_type='dm_ldap_config'");
            col.next();
            IDfTypedObject row = (IDfTypedObject) col.getTypedObject();
            IDfValue attrValue = row.getValue("r_object_id");
            ldapId = getDfObjectValue(attrValue).toString();

            //   String ldapId = session.apiGet("retrieve", "dm_ldap_config");
            IDfLocation location = (IDfLocation) session.getObjectByQualification("dm_location where object_name='config'");
            String serverVersion = session.getServerVersion();
            String pathSeparator;
            if (serverVersion.contains("Win")) {
                pathSeparator = "\\";
            } else {
                pathSeparator = "/";
            }

            String argumento = "mkfile_encrypt_text " + password + " "
                    + location.getFileSystemPath() + pathSeparator
                    + session.getDocbaseName() + pathSeparator + "ldap_"
                    + ldapId + ".cnt";
            IDfApplyDoMethod cmd = (IDfApplyDoMethod) DfAdminCommand
                    .getCommand(IDfAdminCommand.APPLY_DO_METHOD);
            cmd.setLaunchAsync(false);
            cmd.setMethod("replicate_setup_methods");
            cmd.setArguments(argumento);
            cmd.execute(session);
        } catch (DfException dfException) {
        }
    }

    public void createFolderPath(String myFilesystemDirectory) {
        File directorio = new File(myFilesystemDirectory);
        boolean res = directorio.mkdirs();
        if (!res) {
            if (directorio.exists()) {
                Utilidades.escribeLog("La carpeta de destino " + myFilesystemDirectory + " ya existe");
            } else {
                Utilidades.escribeLog("Error al crear la ruta de la carpeta: " + myFilesystemDirectory);
            }
        }
        StringBuilder bufFolderPath = new StringBuilder(64);

        String pathSeparator = System.getProperty("file.separator");
        java.util.StringTokenizer st
                = new java.util.StringTokenizer(myFilesystemDirectory, pathSeparator);

        while (st.hasMoreTokens()) {
            bufFolderPath.append(st.nextToken());
            bufFolderPath.append(util.separador());
            createFolder(bufFolderPath.toString());
        }
    }

    private void createFolder(String myFilesystemDirectory) {
        //if the directory doesn't exist, make it

        File file = new File(myFilesystemDirectory);
        try {
            if (!file.exists()) {
                //modified 
                file.mkdirs();
            }
        } catch (SecurityException e) {
            Utilidades.escribeLog("Error al crear carpeta: " + e.getMessage());
        }
    }

    public void doExport(IDfSession session, IDfFolder exportFolder, String myFilesystemDirectory, PantallaBarra barra) {
        IDfCollection col = null;
        try {

            IDfClientX clientx = new DfClientX();
            IDfExportOperation operation = clientx.getExportOperation();

            operation.setDestinationDirectory(myFilesystemDirectory);

            // Do not need to call createFolderPath, just createFolder as we know that the rest 
            // of the path will already be there already.
            createFolder(myFilesystemDirectory);

            String qualification = "select * from dm_document where FOLDER(ID('" + exportFolder.getObjectId() + "')) order by object_name";

            IDfQuery q = clientx.getQuery(); //Create query object
            q.setDQL(qualification); //Give it the query
            col = q.execute(session, IDfQuery.DF_READ_QUERY); //Execute synchronously

            while (col.next() && barra.PARAR == false) {
                String name = col.getString("object_name");
                name = name.replaceAll("/", "-").replaceAll(":", "-");
//                ventanapadre.barradocum.setLabelMensa(ventanapadre.barradocum.getlabelMensa() + util.separador() + name);
//                ventanapadre.barradocum.validate();
                IDfFile destFile = clientx.getFile(myFilesystemDirectory + util.separador() + name);
                //check if the file has been exported (exists) to avoid another export
                if (!destFile.exists()) {
                    //Create an IDfSysObject representing the object in the collection
                    String id = col.getString("r_object_id");
                    IDfId idObj = clientx.getId(id);
                    IDfSysObject myObj = (IDfSysObject) session.getObject(idObj);

                    if (!m_childIds.contains(idObj.getId())) //do not export children of vDocs
                    {
                        // add the IDfSysObject to the operation
                        barra.setLabelMensa(myFilesystemDirectory + util.separador() + name);
                        myObj.setString("object_name", name);
                        operation.add(myObj);
                        executeOperation(operation);

                        IDfXmlQuery xmlQuery = clientx.getXmlQuery();
                        String tipodocu = col.getValue("r_object_type").asString();
                        xmlQuery.setDql("select * from " + tipodocu + " where r_object_id='" + id + "'");
                        xmlQuery.includeContent(false);
                        xmlQuery.execute(IDfQuery.DF_READ_QUERY, session);
                        FileOutputStream fos = new FileOutputStream(myFilesystemDirectory + util.separador() + name + "-metadatos.xml");
                        xmlQuery.getXMLString(fos);
                        fos.flush();
                        fos.close();
                    }
                    System.gc();
                }
            }

            if (col != null) {
                col.close();
            }
        } catch (DfException | IOException ex) {

        }
    }

    public IDfCollection execQuery(IDfSession sess, String queryString)
            throws DfException {
        IDfCollection col; //For the result

        IDfQuery q = new DfQuery(); //Create query object
        q.setDQL(queryString); //Give it the query
        col = q.execute(sess, DfQuery.DF_READ_QUERY); //Execute synchronously

        return col;
    }

    public void doVirtualDocExport(
            IDfSession sess,
            IDfFolder exportFolder,
            String myFilesystemDirectory)
            throws DfException {
        IDfCollection col = null;

        try {

            IDfClientX m_clientx = new DfClientX();
            IDfExportOperation operation = m_clientx.getExportOperation();

            operation.setDestinationDirectory(myFilesystemDirectory);

            // Do not need to call createFolderPath, just createFolder as we know that the rest 
            // of the path will already be there already.
            createFolder(myFilesystemDirectory);

            String qualification = "select * from dm_document where FOLDER(ID('"
                    + exportFolder.getObjectId()
                    + "')) AND ( (r_is_virtual_doc = 1) OR (r_link_cnt > 0))";

            IDfQuery q = m_clientx.getQuery(); //Create query object
            q.setDQL(qualification); //Give it the query
            col = q.execute(sess, IDfQuery.DF_READ_QUERY); //Execute synchronously

            while (col.next()) {
                String name = col.getString("object_name");
                IDfFile destFile = m_clientx.getFile(myFilesystemDirectory + name);
                //check if the file has been exported (exists) to avoid another export
                if (!destFile.exists()) {
                    //Create an IDfSysObject representing the object in the collection
                    String id = col.getString("r_object_id");
                    IDfId idObj = m_clientx.getId(id);
                    IDfSysObject myObj = (IDfSysObject) sess.getObject(idObj);
                    if (myObj.isVirtualDocument()) {
                        DfLogger.debug(this, "Found a vDoc", null, null);
                        IDfVirtualDocument vDoc
                                = myObj.asVirtualDocument("CURRENT", false);
                        operation.add(vDoc);
                        int childCount = vDoc.getUniqueObjectIdCount();
                        //store ids of all children. when adding a dm_document 
                        //these children will skipped since they have been already
                        //added as part of assembly of a vDoc.
                        for (int ctrChild = 0; ctrChild < childCount; ctrChild++) {
                            IDfId childId = vDoc.getUniqueObjectId(ctrChild);
                            m_childIds.add(childId.getId());
                        }
                    }

                }
            }

            //see sample: Operations- Execute and Check Errors
            executeOperation(operation);
        } finally {
            if (col != null) {
                col.close();
            }

        }

    }

    public IDfClient dameCliente() {
        IDfClient cliente = null;
        try {
            cargarConfiguraciones();
            cliente = DfClient.getLocalClient();
            usuario = pro.getProperty("usuario");
            password = pro.getProperty("password");
            docbase = pro.getProperty("repositorio");
            String docbroker = pro.getProperty("dfc.docbroker.host[0]");
            String puerto = pro.getProperty("dfc.docbroker.port[0]");
            IDfTypedObject config = cliente.getClientConfig();
            config.setString("primary_host", docbroker);
            config.setInt("primary_port", Integer.parseInt(puerto));
            IDfLoginInfo loginInfoObj = new DfLoginInfo();
            loginInfoObj.setUser(usuario);
            loginInfoObj.setPassword(password);
        } catch (Exception ex) {
            Utilidades.escribeLog("Error al solicitar cliente a Documentum: " + ex.getMessage());
        }
        return cliente;
    }

    public static void prueba() {
        try {
            //  IDfClient dfClient = new DfClientX().getLocalClient();
            IDfClient dfClient = DfClient.getLocalClient();

            IDfDocbaseMap dfDocbaseMap = dfClient.getDocbaseMap();
            for (int i = 0; i < dfDocbaseMap.getDocbaseCount(); i++) {
                String docbaseName = dfDocbaseMap.getDocbaseName(i);
                String docbaseDescription = dfDocbaseMap.getDocbaseDescription(i);
                System.out.println(docbaseName + " | " + docbaseDescription);

            }
        } catch (DfException ex) {
            Logger.getLogger(UtilidadesDocumentum.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void FolderNavigate(String usu, String clave, String repo, String server, String port, String folderPath) {
        try {
            // call function to generate XML content
            navigate(folderPath, "", conectarDocumentum(usu, clave, repo, server, port));
        } catch (DfException ex) {
            Utilidades.escribeLog("Error navegar por carpeta: " + ex.getMessage());
        }

    }

    /**
     * Recursive function for generating map of folder structure and documents
     *
     * @param rootPath absolute path to repository cabinet or folder (i.e.
     * "/Temp/myfolder")
     * @param indent string containing indentation tabs
     * @param session repository session
     *
     * @throws DfException
     */
    public void navigate(String rootPath, String indent, IDfSession session)
            throws DfException {

        IDfId idObj = session.getIdByQualification("dm_sysobject where object_name='" + rootPath + "'");
        IDfSysObject sysObj = (IDfSysObject) session.getObject(idObj);
        IDfFolder root = session.getFolderByPath(sysObj.getRepeatingString("r_folder_path", 0));
        // Folder representation of absolute path
        //    IDfFolder root = session.getFolderByPath(rootPath);

        // These rows are being stored first because
        // if you make your recursive call within the 
        // loop, you will quickly run out of collections
        List rows = new ArrayList();
        IDfCollection coll = null;
        try {
            // get all objects in folder
            coll = root.getContents("");
            while (coll.next()) {
                rows.add(coll.getTypedObject());
            }
        } finally {
            if (coll != null) {
                coll.close();
            }
        }

        // write beginning XML element
        String relementName = resolveElementName(
                root.getString("r_object_id"),
                root.getString("r_object_type"));
        System.out.println(
                createStartElement(
                        indent,
                        relementName,
                        root.getString("r_object_id"),
                        root.getString("object_name")
                )
        );

        // iterate through results of folder
        for (int i = 0; i < rows.size(); i++) {
            IDfTypedObject row = (IDfTypedObject) rows.get(i);
            String id = row.getString("r_object_id");

            // if cabinet or folder, recursively navigate contents
            if (id.startsWith("0c") || id.startsWith("0b")) {
                navigate(rootPath + "/" + row.getString("object_name"), indent + "\t", session);
            } else {

                // resolve element name
                String elementName = resolveElementName(id, row.getString("r_object_type"));

                // write object XML element
                System.out.println(
                        createObjectElement(
                                indent + "\t",
                                elementName,
                                row.getString("r_object_id"),
                                row.getString("object_name")
                        )
                );
            }
        } // results list
        // write matching end XML element
        System.out.println(
                createEndElement(
                        indent,
                        relementName
                )
        );

    } // navigate

    // resolves a DCTM type to a name
    public String resolveElementName(String id, String type) {
        // resolve element name
        String elementName;
        if (id.startsWith("0c")) {
            elementName = "cabinet";
        } else if (id.startsWith("0b")) {
            elementName = "folder";
        } else if (id.startsWith("09")) {
            elementName = "document";
        } else {
            elementName = type;
        }
        return elementName;
    }

    // starting XML element
    public String createStartElement(String indent, String elementName, String id, String name) {
        StringBuilder sbuf = new StringBuilder(indent);
        sbuf.append("<\"").append(id).append("\" ");
        sbuf.append("name=\"").append(name).append("\">");
        return sbuf.toString();
    }

    // closing XML element
    public String createEndElement(String indent, String elementName) {
        StringBuilder sbuf = new StringBuilder(indent);
        sbuf.append("</");
        return sbuf.toString();
    }

    // Self-contained object XML element
    public String createObjectElement(String indent, String elementName, String id, String name) {
        StringBuilder sbuf = new StringBuilder(indent);
        sbuf.append("<\" id=\"").append(id).append("\" ");
        sbuf.append("name=\"").append(name).append("\" />");
        return sbuf.toString();
    }

    public void arrancarIndexAgent(IDfSession sesion) throws DfException {
        IDfPersistentObject FTIndexObj = (IDfPersistentObject) sesion.getObjectByQualification("dm_fulltext_index where is_standby = false ");
        String indexName = FTIndexObj.getString("index_name");
        String query = "NULL,FTINDEX_AGENT_ADMIN,NAME,S," + indexName + ",AGENT_INSTANCE_NAME,S,all,ACTION,S,start";
        DfClientX clientX = new DfClientX();
        String repositorio = sesion.getDocbaseName();
        IDfQuery q = clientX.getQuery();
        q.setDQL(query);
        try {
            IDfCollection col = q.execute(sesion, IDfQuery.DF_APPLY);
            col.next();
            String resul = col.getTypedObject().getRepeatingString("status", 0);
        } catch (DfException ex) {
            Utilidades.escribeLog("Error parar el Index Agent de " + repositorio + ": " + ex.getMessage());
        }
    }

    public String estadoIndexAgent(IDfSession sesion) throws DfException {
        IDfPersistentObject FTIndexObj = (IDfPersistentObject) sesion.getObjectByQualification("dm_fulltext_index where is_standby = false ");
        String indexName = FTIndexObj.getString("index_name");
        String query = "NULL,FTINDEX_AGENT_ADMIN,NAME,S," + indexName + ",AGENT_INSTANCE_NAME,S,all,ACTION,S,status";
        DfClientX clientX = new DfClientX();
        String repositorio = sesion.getDocbaseName();
        IDfQuery q = clientX.getQuery();
        q.setDQL(query);
        String resultado = "";
        try {
            IDfCollection col = q.execute(sesion, IDfQuery.DF_APPLY);
            col.next();
            String status = col.getRepeatingString("status", 0);
            String indexAgentName = col.getRepeatingString("name", 0);

            switch (Integer.parseInt(status)) {
                case 200:
                    resultado = "No hay respuesta de la intancia de index agent '" + indexAgentName + "'";
                    System.out.println("No hay respuesta de la intancia de index agent '" + indexAgentName + "'");
                    break;
                case 100:
                    resultado = "La instancia de index agent '" + indexAgentName + "' está parada";
                    System.out.println("La instancia de index agent '" + indexAgentName + "' está parada");
                    break;
                default:
                    resultado = "La instancia de index agent '" + indexAgentName + "' está en ejecución";
                    System.out.println("La instancia de index agent '" + indexAgentName + "' está en ejecución");
                    break;
            }

        } catch (DfException ex) {
            Utilidades.escribeLog("Error parar el Index Agent de " + repositorio + ": " + ex.getMessage());
        }
        return resultado;
    }

    public void pararIndexAgent(IDfSession sesion) throws DfException {
        IDfPersistentObject FTIndexObj = (IDfPersistentObject) sesion.getObjectByQualification("dm_fulltext_index where is_standby = false ");
        String indexName = FTIndexObj.getString("index_name");
        String query = "NULL,FTINDEX_AGENT_ADMIN,NAME,S," + indexName + ",AGENT_INSTANCE_NAME,S,all,ACTION,S,shutdown";
        DfClientX clientX = new DfClientX();
        String repositorio = sesion.getDocbaseName();
        IDfQuery q = clientX.getQuery();
        q.setDQL(query);
        try {
            IDfCollection col = q.execute(sesion, IDfQuery.DF_APPLY);
            col.next();
            String resul = col.getTypedObject().getRepeatingString("status", 0);
        } catch (DfException ex) {
            Utilidades.escribeLog("Error parar el Index Agent de " + repositorio + ": " + ex.getMessage());
        }
    }

    public String ejecutarAPI(String ComandoApi, String datosApi, IDfSession sesion) {
        Properties promensajes = new Properties();

        try {
            InputStream in = UtilidadesDocumentum.class
                    .getClassLoader().getResourceAsStream("es/documentum/utilidades/mensajes-api.properties");
            if (in == null) {
                Utilidades.escribeLog("Error al cargar el fichero de propiedades de Documentum (cargarConfiguraciones)");
            }
            promensajes = new java.util.Properties();
            promensajes.load(in);
        } catch (IOException ex) {
            Utilidades.escribeLog("Error al cargar el fichero de propiedades. (cargarConfiguraciones) Error: " + ex.getMessage());
        }

        String apiCommand = ComandoApi;
        String apiDataCtl = datosApi;
        String batchStr;
        boolean abortScript = false;

        batchStr = apiCommand;
        //   String idControl = null;
        String lastId = null;
        String methodStr;
        String methodData;
        String status;
        String cmdResult = null;
        boolean b_result = false;
        String dummy;
        String dummyC = ",c,";
        String dummyCurrent = ",current,";

        int cmdId = 0;
        int cmdCallType = 0;
        int cmdSession = 0;

        int getCounter = 0;
        int execCounter = 0;
        int setCounter = 0;

        String currToken;

        StringBuilder resultsBuf = new StringBuilder(1024);
        try {
            //  lastId = idControl;
            if ((lastId != null) && (lastId.length() > 0)) {
                b_result = sesion.apiExec("fetch", lastId);
            }
        } catch (DfException exp) {
        }
        if ((batchStr != null) && (batchStr.length() != 0)) {
            StringTokenizer batchTokener = new StringTokenizer(batchStr, "\n\r");
            while ((batchTokener.hasMoreTokens()) && (abortScript != true)) {
                methodStr = batchTokener.nextToken();
                if ((methodStr == null) && (batchTokener.hasMoreTokens())) {
                    methodStr = batchTokener.nextToken();
                }
                StringTokenizer lineTokener = new StringTokenizer(methodStr, ",");

                String methodStr1 = lineTokener.nextToken();
                if ((methodStr1.equals("connect")) || (methodStr1.contains(" "))) {
                    /*
                    resultsBuf.append(promensajes.getProperty("MSG_API") + methodStr1);
                    if (methodStr1.equals("connect")) {
                        resultsBuf.append("\n...\n" + promensajes.getProperty("MSG_NO_CONNECT") + "\n");
                    } else {
                        resultsBuf.append("\n...\n" + promensajes.getProperty("MSG_API_NOT_VALID") + "\n");
                    }
                     */
                } else {
                    String methodStr2 = null;
                    if (lineTokener.hasMoreTokens()) {
                        dummy = lineTokener.nextToken();
                        if (lineTokener.hasMoreTokens()) {
                            if ((dummy != null) && (dummy.length() > 0)) {
                                int index = 0;
                                if (dummy.equalsIgnoreCase("current")) {
                                    index = methodStr.toLowerCase().indexOf(dummyCurrent);
                                } else if (dummy.equalsIgnoreCase("c")) {
                                    index = methodStr.toLowerCase().indexOf(dummyC);
                                }
                                methodStr2 = methodStr.substring(index + dummy.length() + 2);
                            }
                        }
                    }
                    //      resultsBuf.append(promensajes.getProperty("MSG_API") + methodStr);
                    try {
                        IDfList list = sesion.apiDesc(methodStr1 + ",c,");
                        status = list.getString(0);
                        cmdId = list.getInt(1);
                        cmdCallType = list.getInt(2);
                        cmdSession = list.getInt(3);
                        switch (cmdCallType) {
                            case 0:
                                if ((methodStr1.equals("getservermap")) || (methodStr1.equals("getdocbasemap"))) {
                                    StringTokenizer tokenizer = null;
                                    if ((methodStr2 != null) && (methodStr2.length() > 0)) {
                                        tokenizer = new StringTokenizer(methodStr2, ",");
                                    }
                                    if ((tokenizer == null) || (tokenizer.countTokens() <= 1)) {
                                        String strDocbroker = DameDocbroker();
                                        String docbrokerPort = DamePuertoDocbroker();
                                        StringBuilder additionalParamBuffer = new StringBuilder();
                                        if ((methodStr2 != null) && (methodStr2.length() > 0)) {
                                            additionalParamBuffer.append(",");
                                        }
                                        additionalParamBuffer.append(",");
                                        additionalParamBuffer.append(strDocbroker);
                                        additionalParamBuffer.append(",");
                                        additionalParamBuffer.append(docbrokerPort);
                                        if ((methodStr2 != null) && (methodStr2.length() > 0)) {
                                            methodStr2 = methodStr2 + additionalParamBuffer.toString();
                                        } else {
                                            methodStr2 = additionalParamBuffer.toString();
                                        }
                                    }
                                }
                                cmdResult = sesion.apiGet(methodStr1, methodStr2);
                                if ((methodStr1.equals("create")) || (methodStr1.equals("checkin")) || (methodStr1.equals("retrieve")) || (methodStr1.equals("id")) || (methodStr1.equals("getdocbasemap")) || (methodStr1.equals("getservermap")) || (methodStr1.equals("getdocbrokermap"))) {
                                    if (cmdResult != null) {
                                        if (cmdResult.length() != 16) {
                                            abortScript = true;
                                            lastId = "";
                                        } else {
                                            if ((setCounter + execCounter > 0) && (!methodStr1.equals("checkin"))) {
                                            }
                                            lastId = cmdResult;
                                            idControl = cmdResult;
                                        }
                                        execCounter = 0;
                                        setCounter = 0;
                                    } else {
                                        String errorMessage = sesion.apiGet("getmessage", null);
                                        //        resultsBuf.append(errorMessage);
                                    }
                                }
                                getCounter++;
                                break;
                            case 1:

                                methodData = apiDataCtl;

                                b_result = sesion.apiSet(methodStr1, methodStr2, methodData);

                                //      resultsBuf.append("\n" + promensajes.getProperty("MSG_SET") + methodData);
                                if (b_result) {
                                    cmdResult = promensajes.getProperty("MSG_OK");
                                    setCounter++;
                                } else {
                                    abortScript = true;
                                    setCounter = 0;
                                }
                                break;
                            case 2:
                                b_result = sesion.apiExec(methodStr1, methodStr2);
                                if (b_result) {
                                    if (methodStr1.equals("fetch")) {
                                        lastId = methodStr2;
                                        idControl = methodStr2;
                                    }
                                    cmdResult = promensajes.getProperty("MSG_OK");
                                    if (methodStr1.equals("save")) {
                                        execCounter = 0;
                                        setCounter = 0;
                                    } else {
                                        execCounter++;
                                    }
                                } else {
                                    abortScript = true;
                                    execCounter = 0;
                                    setCounter = 0;
                                }
                                break;
                        }
                        b_result = false;
                    } catch (DfException exp) {
                        cmdResult = promensajes.getProperty("MSG_ERROR_PROCESSING") + exp.toString();
                        abortScript = true;
                    } finally {
                        //      resultsBuf.append("\n...\n" + cmdResult + "\n");
                        resultsBuf.append(cmdResult);
                    }
                }
            }
            if (setCounter + execCounter > 0) {
            }
            // String output = resultsBuf.toString();

        }
        return resultsBuf.toString();
    }

    public static boolean isWindowsServer(IDfSession dfSession) {
        try {
            IDfTypedObject serverConfigObject = dfSession.getServerConfig();
            String serverVersion = serverConfigObject.getString("r_server_version");
            if (serverVersion.contains("Win")) {
                return true;
            }
        } catch (DfException ex) {
            Utilidades.escribeLog("Error al comprobar si el servidor es Windows. (isWindowsServer) Error: " + ex.getMessage());
        }
        return false;
    }

    public Boolean BorrarRuta(IDfSysObject sysObj) {
        boolean resultado = true;
        try {
            IDfClientX clientx = new DfClientX();
            IDfDeleteOperation deleteOperation = clientx.getDeleteOperation();
            deleteOperation.setVersionDeletionPolicy(IDfDeleteOperation.ALL_VERSIONS);
            deleteOperation.enablePopulateWithReferences(true);
            deleteOperation.add(sysObj);
            sysObj.getSession().getMessage(2);
            resultado = deleteOperation.execute();
        } catch (DfException e) {

        }

        return resultado;
    }

    public String DameTipoDocumental(String r_object_id) {
        String tipodocumental = "";
        tipodocumental = DameAtributo(r_object_id, "r_object_type").get(0).toString();
        return tipodocumental;
    }

    public Boolean CambiarTipoDocumental(String r_object_id, String tipodocumentalnuevo) {
        Boolean respuesta = false;
        String tipodocumentalactual = DameTipoDocumental(r_object_id);

        if (tipodocumentalactual.isEmpty()) {
            return respuesta;
        }

        String dql = "CHANGE " + tipodocumentalactual + " OBJECT TO " + tipodocumentalnuevo + " WHERE r_object_id = '" + r_object_id + "'";
        try {
            IDfCollection resultado = ejecutarDql(dql);

            if (resultado != null) {
                resultado.next();
                if (resultado.getInt("objects_changed") == 1) {
                    respuesta = true;
                }
            } else {

            }
        } catch (DfException ex) {
            ERROR = ex.getMessage();
        }
        return respuesta;
    }

    public ArrayList<Map<String, String>> DameRelation(String r_object_id, IDfSession sesion) {
        ArrayList<Map<String, String>> listarelaciones = new ArrayList<Map<String, String>>();
        Boolean esHijo = false;
        String dqlrelahijo = "select child_id, r_object_id, relation_name from dm_relation where parent_id ='" + r_object_id + "'";
        IDfCollection colrelahijo = ejecutarDql(dqlrelahijo, sesion);
        try {
            while (colrelahijo.next()) {
                esHijo = true;
                IDfTypedObject rowrela = (IDfTypedObject) colrelahijo.getTypedObject();
                String child_id = rowrela.getValueAt(0).asString();
                String r_object_id_rela = rowrela.getValueAt(1).asString();
                String relation_name = rowrela.getValueAt(2).asString();
                String rutahijo = dameRutaDocumentum(child_id, sesion);
                String resultadoAPIhijo = dameRutaSO(child_id, sesion);
                String rutapadre = dameRutaDocumentum(r_object_id, sesion);
                String resultadoAPIpadre = dameRutaSO(r_object_id, sesion);
                Map<String, String> relaciones = new HashMap<>();
//                System.out.println(child_id + " - " + rutahijo + "/" + " - " + resultadoAPIhijo);
//                System.out.println(r_object_id + " - " + rutapadre + "/" + " - " + resultadoAPIpadre);
                relaciones.put("Tipo", "Padre (" + r_object_id + ") -> Hijo (" + child_id + ")");
                relaciones.put("NombreRelacion", relation_name);
                relaciones.put("IdRelacion", r_object_id_rela);
                relaciones.put("IdPadre", r_object_id);
                relaciones.put("IdHijo", child_id);
                relaciones.put("RutaPadreDocumentum", rutapadre);
                relaciones.put("RutaHijoDocumentum", rutahijo);
                relaciones.put("RutaPadreSSOO", resultadoAPIpadre.contains("DM_SYSOBJECT_E_INVALID_PAGE_NUM") ? "" : resultadoAPIpadre);
                relaciones.put("RutaHijoSSOO", resultadoAPIhijo.contains("DM_SYSOBJECT_E_INVALID_PAGE_NUM") ? "" : resultadoAPIhijo);
                listarelaciones.add(relaciones);
            }
            colrelahijo.close();
        } catch (DfException exhijo) {
        }

        if (!esHijo) {
            try {
                String dqlrelapadre = "select parent_id, r_object_id, relation_name from dm_relation where child_id ='" + r_object_id + "'";
                IDfCollection colrelapadre = ejecutarDql(dqlrelapadre, sesion);
                if (colrelapadre != null) {
                    while (colrelapadre.next()) {
                        IDfTypedObject rowrela = (IDfTypedObject) colrelapadre.getTypedObject();
                        String parent_id = rowrela.getValueAt(0).asString();
                        String r_object_id_rela = rowrela.getValueAt(1).asString();
                        String relation_name = rowrela.getValueAt(2).asString();
                        String rutahijo = dameRutaDocumentum(r_object_id, sesion);
                        String resultadoAPIhijo = dameRutaSO(r_object_id, sesion);
                        String rutapadre = dameRutaDocumentum(parent_id, sesion);
                        String resultadoAPIpadre = dameRutaSO(parent_id, sesion);
                        Map<String, String> relaciones = new HashMap<>();
//                        System.out.println(parent_id + " - " + rutahijo + "/" + " - " + resultadoAPIhijo);
//                        System.out.println(r_object_id + " - " + rutapadre + "/" + " - " + resultadoAPIpadre);
                        relaciones.put("Tipo", "Hijo (" + r_object_id + ") <- Padre (" + parent_id + ")");
                        relaciones.put("NombreRelacion", relation_name);
                        relaciones.put("IdRelacion", r_object_id_rela);
                        relaciones.put("IdPadre", parent_id);
                        relaciones.put("IdHijo", r_object_id);
                        relaciones.put("RutaPadreDocumentum", rutapadre);
                        relaciones.put("RutaHijoDocumentum", rutahijo);
                        relaciones.put("RutaPadreSSOO", resultadoAPIpadre.contains("DM_SYSOBJECT_E_INVALID_PAGE_NUM") ? "" : resultadoAPIpadre);
                        relaciones.put("RutaHijoSSOO", resultadoAPIhijo.contains("DM_SYSOBJECT_E_INVALID_PAGE_NUM") ? "" : resultadoAPIhijo);
                        listarelaciones.add(relaciones);
                    }
                }
                colrelapadre.close();
            } catch (DfException ex) {
            }
        }

        return listarelaciones;
    }

    public String dameRutaDocumentum(String r_object_id, IDfSession sesion) {
        String ruta = "";
        IDfCollection colruta = ejecutarDql("select r_folder_path from dm_folder_r where r_object_id in (select i_folder_id from dm_sysobject_r where i_position=-1 and r_object_id='" + r_object_id + "') and i_position=-1", sesion);
        if (colruta != null) {
            try {
                colruta.next();
                IDfTypedObject rowruta = (IDfTypedObject) colruta.getTypedObject();
                ruta = rowruta.getValueAt(0).asString();
                colruta.close();
            } catch (Exception ex) {
                System.out.println("Error al recuperar la ruta al r_object_id " + r_object_id + " - " + ex.getMessage());
            }
        }
        return ruta;
    }

    public String dameRutaSO(String r_object_id, IDfSession sesion) {
        String ruta = "";
        if (r_object_id.startsWith("09")) {
            ruta = ejecutarAPI("getpath,c," + r_object_id, "", sesion);
        }
        return ruta;
    }

    public String DameTiposPadre(String tipo, IDfSession sesion) {
        String supertipos = "";

        try {
            String dql = "select h.r_supertype,h.i_position from dmi_type_info_s p, dmi_type_info_r h "
                    + "where p.r_object_id=h.r_object_id and lower(p.r_type_name)='" + tipo.toLowerCase() + "' order by 2 desc";
            IDfCollection col = ejecutarDql(dql, sesion);
            if (col != null) {
                while (col.next()) {
                    IDfTypedObject r = col.getTypedObject();
                    String valor = r.getValueAt(0).asString();

                    if (!valor.toLowerCase().equalsIgnoreCase(tipo)) {
                        if (supertipos.isEmpty()) {
                            supertipos = valor;
                        } else {
                            supertipos = supertipos + ", " + valor;
                        }
                    }
                }
            }

        } catch (Exception ex) {
        }

        return supertipos;
    }

    public String DameFilestoreDeTipo(String tipo, IDfSession sesion) {
        String filestore = "";
        try {
            String dql = "Select f.name from dmi_type_info t,dm_filestore f where t.r_type_name='" + tipo + "' and t.default_storage=f.r_object_id";
            IDfCollection col = ejecutarDql(dql, sesion);
            if (col != null) {
                while (col.next()) {
                    IDfTypedObject r = col.getTypedObject();
                    filestore = r.getValueAt(0).asString();
                }
            }
        } catch (DfException ex) {
        }
        return filestore;
    }

    public String DameSuperTipo(String tipo, IDfSession sesion) {
        String supertipo = "";
        try {
            String dql = "Select super_name from dm_type where name='" + tipo + "'";
            IDfCollection col = ejecutarDql(dql, sesion);
            if (col != null) {
                while (col.next()) {
                    IDfTypedObject r = col.getTypedObject();
                    supertipo = r.getValueAt(0).asString();
                }
            }
        } catch (DfException ex) {
        }
        return supertipo;
    }

    public ArrayList<String> DameTiposHijos(String tipo, IDfSession sesion) {
        ArrayList<String> hijos = new ArrayList<>();
        try {
            String dql = "Select name from dm_type where super_name='" + tipo + "' order by 1";
            IDfCollection col = ejecutarDql(dql, sesion);
            if (col != null) {
                while (col.next()) {
                    IDfTypedObject r = col.getTypedObject();
                    String hijo = r.getValueAt(0).asString();
                    hijos.add(hijo);
                }
            }
        } catch (DfException ex) {
        }
        return hijos;
    }

    public String DameRobjectidDeTipo(String tipo, IDfSession sesion) {
        String id = "";
        try {
            String dql = "Select r_object_id from dm_type where name='" + tipo + "'";
            IDfCollection col = ejecutarDql(dql, sesion);
            if (col != null) {
                while (col.next()) {
                    IDfTypedObject r = col.getTypedObject();
                    id = r.getValueAt(0).asString();
                }
            }
        } catch (DfException ex) {
        }
        return id;
    }

    public String ArrancarIndexAgent(IDfSession sesion) {
        String resultado = "";
        String comandoAPI = "";
        comandoAPI = "apply,c,,FTINDEX_AGENT_ADMIN,NAME,S," + dameFTIndex(sesion)
                + ",AGENT_INSTANCE_NAME,S," + dameIndexAgent(sesion) + ",ACTION,S,start";

        String resul = ejecutarAPI(comandoAPI, "", sesion);
        comandoAPI = "next,c," + resul;
        ejecutarAPI(comandoAPI, "", sesion);
        comandoAPI = "get,c," + resul + ",status";
        resul = ejecutarAPI(comandoAPI, "", sesion);

        if (resul.equals("100")) {
            resultado = "Parado";
        } else if (resul.equals("200")) {
            resultado = "Sin respuesta";
        } else {
            resultado = "En ejecución";
        }

        return resultado;
    }

}
