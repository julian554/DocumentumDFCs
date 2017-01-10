package es.documentum.utilidades;

import com.documentum.com.*;
import com.documentum.com.IDfClientX;
import com.documentum.fc.client.*;
import com.documentum.fc.commands.admin.DfAdminCommand;
import com.documentum.fc.commands.admin.IDfAdminCommand;
import com.documentum.fc.commands.admin.IDfApplyDoMethod;
import com.documentum.fc.common.*;
import com.documentum.operations.*;
import com.google.common.io.Files;
import es.documentum.Beans.AtributosDocumentum;
import es.documentum.Beans.ResultadoGDBean;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.apache.commons.lang.exception.ExceptionUtils.getStackTrace;

public class UtilidadesDocumentum {

    Properties pro = new Properties();

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

    private Set m_childIds = new HashSet(5);

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
            } catch (Exception ex) {
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
            } catch (Exception ex) {
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
            IDfClient client = DfClient.getLocalClient();
            IDfTypedObject config = client.getClientConfig();
            config.setString("primary_host", docbroker);
            config.setInt("primary_port", Integer.parseInt(puerto));
            IDfLoginInfo loginInfoObj = new DfLoginInfo();
            loginInfoObj.setUser(usuario);
            loginInfoObj.setPassword(password);
            //sesion = client.newSession(docbase, loginInfoObj);
            docbroker = docbroker.contains(".") ? docbroker.substring(0, docbroker.indexOf(".")) : docbroker;
            sesion = client.newSession(docbase + "@" + docbroker, loginInfoObj);
            if (!sesion.isConnected()) {
                ERROR = "No se pudo obtener sesión de Documentum (conectarDocumentum)";
                return null;
            }
        } catch (DfException dfe) {
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
            IDfClient client = DfClient.getLocalClient();
            IDfTypedObject config = client.getClientConfig();
            config.setString("primary_host", docbroker);
            config.setInt("primary_port", Integer.parseInt(puerto));
            IDfLoginInfo loginInfoObj = new DfLoginInfo();
            loginInfoObj.setUser(usuario);
            loginInfoObj.setPassword(password);
            //sesion = client.newSession(docbase, loginInfoObj);
            docbroker = docbroker.contains(".") ? docbroker.substring(0, docbroker.indexOf(".")) : docbroker;
            sesion = client.newSession(docbase + "@" + docbroker, loginInfoObj);
            if (!sesion.isConnected()) {
                ERROR = "No se pudo obtener sesión de Documentum (conectarDocumentum)";
                return null;
            }
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
        IDfQuery query = new DfClientX().getQuery();
        query.setDQL(dql);
        try {
            coleccion = query.execute(sesion, IDfQuery.DF_EXEC_QUERY);
        } catch (Exception ex) {
            ERROR = "Error al ejecutar DQL (ejecutarDql) - Error: " + ex.getMessage();
            Utilidades.escribeLog("Error al ejecutar DQL (ejecutarDql) - Error: " + ex.getMessage());
            return coleccion;
        }

        ERROR = "";
        return coleccion;
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
        } catch (Exception ex) {
            ERROR = "Error al ejecutar DQL (ejecutarDql) - Error: " + ex.getMessage();
            Utilidades.escribeLog("Error al ejecutar DQL (ejecutarDql) - Error: " + ex.getMessage() + " - " + getStackTrace(ex));
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
        } catch (Exception ex) {
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
            } catch (Exception ex) {
                resultado = "Error al subir fichero a Documentum - " + ex.getMessage();
                Utilidades.escribeLog(ex.getMessage());
                ERROR = "Error al subir fichero a Documentum - " + ex.getMessage();;
            }
            resultadogd.setFichero(rutacarpeta + ficheros[i]);
            resultadogd.setId(ruta);
            resultadogd.setResultado(resultado);
            resultadogd.setCarpeta(rutacarpetadocumentum);
            listaresultados.add(resultadogd);
        }
        try {
            sesion.disconnect();
        } catch (DfException ex) {
            Utilidades.escribeLog("Error al desconectar la sesión en Documentum " + " - " + ex.getMessage());
            ERROR = "Error al desconectar la sesión en Documentum " + " - " + ex.getMessage();
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
        } catch (DfException e) {
            ERROR = e.getMessage();
        }
        try {
            sesion.disconnect();
        } catch (DfException ex) {
            Utilidades.escribeLog("Error al desconectar la sesión en Documentum " + " - " + ex.getMessage());
            ERROR = "Error al desconectar la sesión en Documentum  (ImportarADocumentum). - " + ex.getMessage();
        }
        return ERROR;
    }

    public String subirDocumento(IDfSession sesion, String srcFileOrDir, String destFolderPath)
            throws DfException {
        IDfClientX clientx = new DfClientX();
        IDfImportOperation operation = clientx.getImportOperation();
        operation.setSession(sesion);
        IDfFolder folder = null;
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
            Utilidades.escribeLog("Carpeta: " + pro.getProperty("DIRECTORIO_BASE") + "/" + destFolderPath);
            Utilidades.escribeLog("Storage: " + aNode.getNewObject().getStorageType());
            id = aNode.getNewObjectId().toString();
        }
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
        String dirbase = pro.getProperty("DIRECTORIO_BASE");
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
                    Utilidades.escribeLog("Error al desconectar la sesión en Documentum " + " - " + ex.getMessage());
                    ERROR = "Error al desconectar la sesión en Documentum (dameCarpeta) " + " - " + ex.getMessage();
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
            sesion.disconnect();
        } catch (DfException ex) {
            Utilidades.escribeLog("Error al desconectar la sesión en Documentum " + " - " + ex.getMessage());
            ERROR = "Error al desconectar la sesión en Documentum  (dameCarpeta). - " + ex.getMessage();
        }

        return carpeta;
    }

    public Boolean existeCarpeta(String nombre) {
        Boolean respuesta = false;
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

    public static void executeOperation(IDfOperation operation)
            throws DfException {
        // Execute the operation
        boolean executeFlag = operation.execute();
        // Check if any errors occured during the execution of the operation
        if (executeFlag == false) {
            // Get the list of errors
            IDfList errorList = operation.getErrors();
            String message = "";
            IDfOperationError error = null;
            // Iterate through the errors and concatenate the error messages
            for (int i = 0; i < errorList.getCount(); i++) {
                error = (IDfOperationError) errorList.get(i);
                message += error.getMessage();
            }
            Utilidades.escribeLog("Errores: " + message);
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

        } catch (Exception ex) {
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

        } catch (Exception ex) {
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
        } catch (Exception ex) {
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
        ArrayList<AtributosDocumentum> resultado = new ArrayList<AtributosDocumentum>();
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
                    atri.setValor(sysObj.getValue(nombre).toString());
                    atri.setMultivalor(false);
                    atri.setTipo(sysObj.getAttrDataType(nombre));
                    atri.setLongitud(sysObj.getAttr(i).getLength());
//                    System.out.println(nombre + " : " + sysObj.getValue(nombre));
                    resultado.add(atri);
                }
            }
        } catch (Exception ex) {
            ERROR = "Error al recuperar atributos de " + r_object_id + " (DameTodosAtributos) - Error: " + ex.getMessage();
            return new ArrayList<AtributosDocumentum>();
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

//            Utilidades.escribeLog("r_object_id: " + r_object_id);
//            Utilidades.escribeLog("Nombre: " + sysObj.getObjectName());
//            Utilidades.escribeLog("Subject: " + sysObj.getSubject());
//            Utilidades.escribeLog("Title: " + sysObj.getTitle());
//            StringBuffer atributos = new StringBuffer("");
//            atributos.append(sysObj.dump());
//            Utilidades.escribeLog("Atributos: " + atributos.toString());
//            Utilidades.escribeLog("MAP_ATR_NUM_EXP: " + sysObj.getValue("MAP_ATR_NUM_EXP".toLowerCase()).toString());
//
//            for (int i = 0; i < sysObj.getValueCount("map_atr_estado"); i++) {
//                String valores = sysObj.getRepeatingString("map_atr_estado", i);
//                Utilidades.escribeLog("map_atr_estado[" + i + "]: " + valores);
//            }
//
//            idObj = sesion.getIdByQualification("dm_sysobject where r_object_id='" + sysObj.getRepeatingString("i_folder_id", 0) + "'");
//            sysObj = (IDfSysObject) sesion.getObject(idObj);
//            atributos = new StringBuffer("");
//            atributos.append(sysObj.dump());
//            //  Utilidades.escribeLog("Atributos: " + atributos.toString());
//            Utilidades.escribeLog("Carpeta: " + sysObj.getRepeatingString("r_folder_path", 0));
        } catch (Exception ex) {
            Utilidades.escribeLog("Error al leer atributos: " + ex.getMessage());
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
            sesion.disconnect();
        } catch (Exception ex) {
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

    public boolean EsUsuarioAdmin(String usuario) {
        boolean resultado = false;

        if (usuario.isEmpty()) {
            return resultado;
        }

        IDfCollection coleccion = ejecutarDql("Select user_privileges From dm_user where user_name='" + usuario + "' ");
        String valor = "0";

//        IDfSession sesion = conectarDocumentum();
//        if (sesion == null) {
//            if (ERROR.isEmpty()) {
//                ERROR = "Error al crear sesión en Documentum (EsUsuarioAdmin)";
//            }
//            return false;
//        }
        try {
//            IDfId idObj = sesion.getIdByQualification("dm_user where user_name='" + usuario + "' ");
//            IDfSysObject sysObj = (IDfSysObject) sesion.getObject(idObj);
//            valor = sysObj.getValue("user_privileges").toString();
            coleccion.next();
            valor = "" + coleccion.getInt("user_privileges");
        } catch (Exception ex) {
            Utilidades.escribeLog("Error al comprobar usuario Administrador (EsUsuarioAdmin): " + ex.getMessage());
            ERROR = "Error al comprobar si " + usuario + " es Administrador (EsUsuarioAdmin): " + ex.getMessage();
        }

        resultado = valor.equals("16");

//        try {
//            sesion.disconnect();
//        } catch (DfException ex) {
//            Utilidades.escribeLog("Error al desconectar la sesión en Documentum (EsUsuarioAdmin)  - Error: " + ex.getMessage());
//            ERROR = "Error al desconectar la sesión en Documentum (EsUsuarioAdmin)  - Error: " + ex.getMessage();
//        }
        return resultado;
    }

    public boolean BorrarDocumento(String r_object_id) {
        boolean resultado = true;
        IDfSession sesion = conectarDocumentum();
        if (sesion == null) {
            if (ERROR.isEmpty()) {
                ERROR = "Error al crear sesión en Documentum (BorrarDocumento)";
            }
            return false;
        }
        try {
            IDfId idObj = sesion.getIdByQualification("dm_sysobject where r_object_id='" + r_object_id + "'");
            IDfDocument Documento = (IDfDocument) sesion.getObject(idObj);
            Documento.destroyAllVersions();
        } catch (Exception ex) {
            ERROR = "Error al Borrar el documento con ID: " + r_object_id + " - Error: " + ex.getMessage();
            Utilidades.escribeLog(ERROR);
            return false;
        }

        ERROR = "";
        try {
            sesion.disconnect();
        } catch (DfException ex) {
            Utilidades.escribeLog("Error al desconectar la sesión en Documentum " + " - " + ex.getMessage());
            ERROR = "Error al desconectar la sesión en Documentum  (BorrarDocumento). - " + ex.getMessage();
        }
        return resultado;
    }

    public ArrayList<AtributosDocumentum> ListarFicheros(String carpeta) {
        IDfCollection ficheros = null;
        ArrayList<AtributosDocumentum> listaficheros = new ArrayList<AtributosDocumentum>();
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

            while (ficheros.next()) {
                AtributosDocumentum datos = new AtributosDocumentum();
                IDfTypedObject doc = ficheros.getTypedObject();
//                Utilidades.escribeLog(doc.getString("object_name") + " - " + doc.getString("r_object_id"));
                datos.setNombre(doc.getString("object_name"));
                datos.setValor(doc.getString("r_object_id"));
                datos.setTipoobjeto(doc.getString("r_object_type"));
                ArrayList resultado = DameAtributo(doc.getString("r_object_id"), "r_creation_date");
                datos.setFechacreacion(resultado.get(0).toString());
                if (doc.getString("r_object_type").equals("map_t_doc_extranjeria")) {
                    resultado = DameAtributo(doc.getString("r_object_id"), "map_atr_aplicacion");
                    if (resultado.size() > 0) {
                        datos.setAplicacion(resultado.get(0).toString());
                    }
                    resultado = DameAtributo(doc.getString("r_object_id"), "map_atr_usuario");
                    if (resultado.size() > 0) {
                        datos.setUsuario(resultado.get(0).toString());
                    }
                }

                listaficheros.add(datos);
            }

        } catch (Exception ex) {
            if (!ex.getMessage().contains("Bad ID given: 0000000000000000")) {
                Utilidades.escribeLog("Error al listar directorio: " + ex.getMessage());
                ERROR = "Error al listar directorio " + carpeta + " (ListarFicheros) - Error: " + ex.getMessage();
            } else {
                Utilidades.escribeLog("No existe la carpeta " + carpeta + " en Documentum");
                ERROR = "No existe la carpeta " + carpeta + " en Documentum";
            }
            return new ArrayList<AtributosDocumentum>();
        }

        try {
            sesion.disconnect();
        } catch (DfException ex) {
            Utilidades.escribeLog("Error al desconectar la sesión en Documentum " + " - " + ex.getMessage());
            ERROR = "Error al desconectar la sesión en Documentum (ListarFicheros)  - Error: " + ex.getMessage();
            return new ArrayList<AtributosDocumentum>();
        }
        return listaficheros;
    }

    public ArrayList<AtributosDocumentum> ListarFicherosRuta(String ruta) {
        IDfCollection ficheros = null;
        ArrayList<AtributosDocumentum> listaficheros = new ArrayList<AtributosDocumentum>();
        IDfSession sesion = conectarDocumentum();
        if (sesion == null) {
            if (ERROR.isEmpty()) {
                ERROR = "Error al crear sesión en Documentum (ListarFicherosRuta)";
            }
            return listaficheros;
        }
        ERROR = "";
        try {
            String dql = "";
            if (ruta.equals("/")) {
                dql = "select * From dm_sysobject where r_object_type='dm_cabinet'";
                ficheros = ejecutarDql(dql);
            } else {
                IDfFolder folder = (IDfFolder) sesion.getObjectByPath(ruta);
                IDfId idfolder = folder.getId("r_object_id");
                dql = "dm_sysobject where r_object_id='" + idfolder.toString() + "'";
                IDfId idObj = sesion.getIdByQualification(dql);
                IDfSysObject sysObj = (IDfSysObject) sesion.getObject(idObj);
                IDfFolder myFolder = sesion.getFolderByPath(sysObj.getRepeatingString("r_folder_path", 0));
                ficheros = myFolder.getContents(null);
            }
            while (ficheros.next()) {
                AtributosDocumentum datos = new AtributosDocumentum();
                IDfTypedObject doc = ficheros.getTypedObject();
//                Utilidades.escribeLog(doc.getString("object_name") + " - " + doc.getString("r_object_id"));
                datos.setNombre(doc.getString("object_name"));
                datos.setValor(doc.getString("r_object_id"));
                datos.setTipoobjeto(doc.getString("r_object_type"));
                ArrayList resultado = DameAtributo(doc.getString("r_object_id"), "r_creation_date");
                String fechacreacion = resultado.get(0).toString();
                datos.setFechacreacion(fechacreacion);
                listaficheros.add(datos);
            }

        } catch (Exception ex) {
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
            return new ArrayList<AtributosDocumentum>();
        }

        try {
            sesion.disconnect();
        } catch (DfException ex) {
            Utilidades.escribeLog("Error al desconectar la sesión en Documentum (ListarFicherosRuta) - Error: " + ex.getMessage());
            ERROR = "Error al desconectar la sesión en Documentum (ListarFicherosRuta) - Error: " + ex.getMessage();
            return new ArrayList<AtributosDocumentum>();
        }

        return listaficheros;
    }

    public ArrayList<AtributosDocumentum> ListarFicherosId(String id) {
        Boolean Escarpeta = id.toLowerCase().startsWith("0b");
        Boolean Escabinet = id.toLowerCase().startsWith("0c");
        IDfCollection ficheros = null;
        ArrayList<AtributosDocumentum> listaficheros = new ArrayList<AtributosDocumentum>();
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
            IDfFolder myFolder = null;
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
                    continue;
                } else {
//                Utilidades.escribeLog(doc.getString("object_name") + " - " + doc.getString("r_object_id"));
                    datos.setNombre(doc.getString("object_name"));
                    datos.setValor(doc.getString("r_object_id"));
                    datos.setTipoobjeto(doc.getString("r_object_type"));
                    ArrayList resultado = DameAtributo(doc.getString("r_object_id"), "r_creation_date");
                    String fechacreacion = resultado.get(0).toString();
                    datos.setFechacreacion(fechacreacion);
                    if (doc.getString("r_object_type").equals("map_t_doc_extranjeria")) {
                        resultado = DameAtributo(doc.getString("r_object_id"), "map_atr_aplicacion");
                        if (resultado.size() > 0) {
                            datos.setAplicacion(resultado.get(0).toString());
                        }
                        resultado = DameAtributo(doc.getString("r_object_id"), "map_atr_usuario");
                        if (resultado.size() > 0) {
                            datos.setUsuario(resultado.get(0).toString());
                        }
                    }

                    listaficheros.add(datos);
                    if (!Escarpeta && !Escabinet) {
                        break;
                    }
                }
            }

        } catch (Exception ex) {
            Utilidades.escribeLog("Error al buscar por el id " + id + "  - Error: " + ex.getMessage());
            ERROR = "Error al buscar por el id " + id + " (ListarFicherosID) - Error: " + ex.getMessage();
            return new ArrayList<AtributosDocumentum>();
        }

        try {
            sesion.disconnect();
        } catch (DfException ex) {
            Utilidades.escribeLog("Error al desconectar la sesión en Documentum " + " - " + ex.getMessage());
            ERROR = "Error al desconectar la sesión en Documentum (ListarFicherosID)  - Error: " + ex.getMessage();
            return new ArrayList<AtributosDocumentum>();
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
                directorio += "/";
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
                if (nombre.endsWith(formato)) {
                    return "OK. Exportado " + directorio + nombre;
                } else {
                    return "OK. Exportado " + directorio + nombre + "." + formato;
                }
            } else {
                return "Exportación fallida.";
            }
        } catch (DfException ex) {
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

    public void checkoutDoc(String objectId, IDfSession sesion) throws Exception {

        IDfSysObject sysObject = (IDfSysObject) sesion.getObject(new DfId(objectId));
        if (!sysObject.isCheckedOut()) // if it is not checked out
        {
            sysObject.checkout();
        }

        System.out.println("is Check out " + sysObject.isCheckedOut());
    }

    public String checkinDoc(String objectId, IDfSession sesion) {
        try {
            IDfSysObject sysObject = (IDfSysObject) sesion.getObject(new DfId(objectId));
            if (sysObject.isCheckedOut()) { // if it is checked out
                return sysObject.checkin(false, "CURRENT").getId();
            }

        } catch (DfException ex) {
            Utilidades.escribeLog("Error al hacer checkin de " + objectId + "  -  Error " + ex.getMessage());
        }
        return null;
    }

    public static void main(String s[]) {

        prueba();

        Utilidades util = new Utilidades();
        String dirdfc = util.usuarioHome() + util.separador() + "documentumdcfs" + util.separador() + "documentum" + util.separador() + "shared" + util.separador();

        try {
            ClassPathUpdater.add(dirdfc);
            ClassPathUpdater.add(dirdfc + "lib" + util.separador() + "jsafeFIPS.jar");
        } catch (Exception ex) {
            Utilidades.escribeLog("Error al actualizar el Classpath  - Error: " + ex.getMessage());
        }
        UtilidadesDocumentum ed = new UtilidadesDocumentum(dirdfc + "dfc.properties");
        IDfSession sesion = ed.conectarDocumentum();

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
        try {
            String desencriptarPassword;
            desencriptarPassword = desencriptarPassword("GrRNPhLJrkoTDAZE0RGJow\\=\\=");

        } catch (Exception ex) {

        }

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
            Vector filas = new Vector();
            while (col.next()) {
                filas.addElement(col.getTypedObject());
            }

            int cont = filas.size();
            IDfTypedObject primerafila = (IDfTypedObject) filas.elementAt(0);
            int tam = primerafila.getAttrCount();
            Object[] cabecera = new Object[tam];
            Object[][] datos = new Object[cont][tam];
            for (int l = 0; l < tam; l++) {
                cabecera[l] = primerafila.getAttr(l).getName();
            }

            for (int i = 0; i < cont; i++) {
                IDfTypedObject row = (IDfTypedObject) filas.elementAt(i);

                for (int n = 0; n < row.getAttrCount(); n++) {
                    IDfAttr attr = row.getAttr(n);
                    IDfValue attrValue = row.getValue(attr.getName());

                    datos[i][n] = getDfObjectValue(attrValue);
                }

            }

            col.close();

            sesion.disconnect();

        } catch (Exception ex) {
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
                return new Double(value.asDouble()).toString();

            case IDfValue.DF_ID:
                return value.asId().toString();

            case IDfValue.DF_INTEGER:
                return new Integer(value.asInteger()).toString();

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
        try {
            IDfCollection col = ejecutarDql("SELECT name from dm_format WHERE dos_extension = lower('" + extension + "')");
            col.next();
            IDfTypedObject row = (IDfTypedObject) col.getTypedObject();
            IDfValue attrValue = row.getValue("name");
            tipo = getDfObjectValue(attrValue).toString();
        } catch (Exception e) {
            tipo = "-1";
        }
        return tipo;
    }

    private String dameExtension(String tipo) {
        String extension = "";
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

    public static String desencriptarPassword(String clave) {
        String valor = "";
        try {
            valor = com.documentum.fc.tools.RegistryPasswordUtils.decrypt(clave);
            System.out.println(valor);
        } catch (Exception e) {
        }
        // try decrypting with API - longer, dm_encrypt_password passwords
        try {
            valor = com.documentum.dmcl.impl.DmclApi.getInstance().get("decrypttext,c,DM_ENCR_TEXT=" + clave);
            System.out.println(valor);
        } catch (Exception e) {
        }
        return valor;
    }

    public void setLdapPassword(String password)
            throws DfException {

        IDfSession session = conectarDocumentum();
        if (session == null) {
            if (ERROR.isEmpty()) {
                ERROR = "No se pudo obtener sesión de Documentum (setLdapPassword)";
            }
        }
        ERROR = "";
        String ldapId = "";
        try {
            IDfCollection col = ejecutarDql("SELECT r_object_id  from dm_sysobject WHERE r_object_type='dm_ldap_config'");
            col.next();
            IDfTypedObject row = (IDfTypedObject) col.getTypedObject();
            IDfValue attrValue = row.getValue("r_object_id");
            ldapId = getDfObjectValue(attrValue).toString();
        } catch (DfException dfException) {
        }

        //   String ldapId = session.apiGet("retrieve", "dm_ldap_config");
        IDfLocation location = (IDfLocation) session
                .getObjectByQualification("dm_location where object_name='config'");
        if (location == null) {
            throw new DfException(
                    "Unable to find dm_location with object_name='config'");
        }
        String serverVersion = session.getServerVersion();
        String pathSeparator;
        if (serverVersion.contains("Win")) {
            pathSeparator = "\\";
        } else {
            pathSeparator = "/";
        }

        String argumento = "mkfile_encrypt_text " + "documentum" + " "
                + location.getFileSystemPath() + pathSeparator
                + session.getDocbaseName() + pathSeparator + "ldap_"
                + ldapId + ".cnt";
        IDfApplyDoMethod cmd = (IDfApplyDoMethod) DfAdminCommand
                .getCommand(IDfAdminCommand.APPLY_DO_METHOD);
        cmd.setLaunchAsync(false);
        cmd.setMethod("replicate_setup_methods");
        cmd.setArguments(argumento);
        cmd.execute(session);
    }

    public void createFolderPath(String myFilesystemDirectory) {
        File dir = new File(myFilesystemDirectory);
        boolean res = dir.mkdirs();
        if (!res) {
            if (dir.exists()) {
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
            bufFolderPath.append(pathSeparator);
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

    public void doExport(
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

            String qualification
                    = "select * from dm_document where FOLDER(ID('"
                    + exportFolder.getObjectId()
                    + "'))";

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

                    if (!m_childIds.contains(idObj.getId())) //do not export children of vDocs
                    {
                        // add the IDfSysObject to the operation
                        operation.add(myObj);
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

    public IDfCollection execQuery(IDfSession sess, String queryString)
            throws DfException {
        IDfCollection col = null; //For the result

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

            String qualification
                    = "select * from dm_document where FOLDER(ID('"
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
        } catch (DfException ex) {
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
        String elementName = "";
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
            Utilidades.escribeLog("Error parar el Index Agent de " + repositorio + ": " + ex.getMessage());;
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
            Utilidades.escribeLog("Error parar el Index Agent de " + repositorio + ": " + ex.getMessage());;
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
            Utilidades.escribeLog("Error parar el Index Agent de " + repositorio + ": " + ex.getMessage());;
        }
    }
}
