/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.documentum.utilidades;

import com.documentum.com.*;
import com.documentum.com.IDfClientX;
import com.documentum.fc.client.*;
import com.documentum.fc.common.*;
import com.documentum.operations.*;
import com.google.common.io.Files;
import es.documentum.Beans.AtributosDocumentum;
import es.documentum.Beans.ResultadoGDBean;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

public class UtilidadesDocumentum {

    Properties pro = new Properties();
    String usuario = "";
    String password = "";
    String docbase = "";
    String ficheropropiedades = "";
    String ERROR = "";

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
//        System.out.println(usuario + " - " + password + " - " + docbase + " - " + docbroker);

        ERROR = "";

        IDfSession sesion = null;
//        IDfSessionManager sessionManager = null;
        try {
            IDfClientX clientx = new DfClientX();
            IDfClient client = clientx.getLocalClient();
            clientx.getLoginInfo();
//            sessionManager = client.newSessionManager();
            IDfLoginInfo loginInfoObj = clientx.getLoginInfo();
            loginInfoObj.setUser(usuario);
            loginInfoObj.setPassword(password);
            //    loginInfoObj.setDomain(docbroker);

            sesion = client.newSession(docbase, loginInfoObj);
//            sessionManager.setIdentity(docbase, loginInfoObj);
//            sesion = sessionManager.getSession(docbase);

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

    private IDfSessionManager createSessionManager(String docbase, String user, String pass) {
        //create Client objects
        IDfClientX clientx = new DfClientX();
        IDfSessionManager sMgr = null;
        ERROR = "";
        try {
            IDfClient client = clientx.getLocalClient();

            //create a Session Manager object
            sMgr = client.newSessionManager();

            //create an IDfLoginInfo object named loginInfoObj
            IDfLoginInfo loginInfoObj = clientx.getLoginInfo();
            loginInfoObj.setUser(user);
            loginInfoObj.setPassword(pass);
            loginInfoObj.setDomain(null);

            //bind the Session Manager to the login info
            sMgr.setIdentity(docbase, loginInfoObj);

        } catch (Exception ex) {
            ERROR = "Error al Conectar con Documentum (createSessionManager) - Error: " + ex.getMessage();
        }

        //could also set identity for more than one Docbases:
        // sMgr.setIdentity( strDocbase2, loginInfoObj );
        //use the following to use the same loginInfObj for all Docbases (DFC 5.2 or later)
        // sMgr.setIdentity( * , loginInfoObj );
        return sMgr;
    }

//    public IDfSession conectarDocumentum() {
//        cargarConfiguraciones();
//
//        usuario = pro.getProperty("usuario");
//        password = pro.getProperty("password");
//        docbase = pro.getProperty("repositorio");
//        String docbroker = pro.getProperty("dfc.docbroker.host[0]");
//
//        System.out.println(usuario + " - " + password + " - " + docbase + " - " + docbroker);
//
//        IDfSession sesion = null;
//
//        try {
//            IDfClientX clientx = null;
//            IDfSessionManager sessionManager = null;
//            clientx = new DfClientX();
//            clientx.getLocalClient();
//            try {
//                sessionManager = createSessionManager(docbase, usuario, password);
//            } catch (Exception ex) {
//                Utilidades.escribeLog("Error crear sesión en Documentum - Docbase: " + docbase + " - " + ex.getMessage());
//                ERROR = "No se pudo obtener Sesión Mánager de Documentum (conectarDocumentum)";
//                return null;
//            }
//            sesion = sessionManager.getSession(docbase);
//
//            if (!sesion.isConnected()) {
//                ERROR = "No se pudo obtener sesión de Documentum (conectarDocumentum)";
//                return null;
//            }
//
//        } catch (DfException dfe) {
//            Utilidades.escribeLog("Error al conectar con Documentum: " + dfe.toString());
//            ERROR = "Error al conectar con Documentum (conectarDocumentum): " + dfe.toString();
//        }
//        return sesion;
//    }
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
            IDfId idObj = sesion.getIdByQualification("dm_sysobject where r_object_id='" + r_object_id + "'");
            // Instantiate an object from the ID.
            IDfSysObject sysObj = (IDfSysObject) sesion.getObject(idObj);
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
        IDfClientX clientx = new DfClientX();
        String version = clientx.getDFCVersion();
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
            resultado = sesion.getDocbaseId();
        } catch (Exception ex) {
            ERROR = "Error al obtener versión de Documentum (DameIdRepositorio) - Error: " + ex.getMessage();
            return "";
        }

        try {
            sesion.disconnect();
        } catch (DfException ex) {
            Utilidades.escribeLog("Error al desconectar la sesión en Documentum " + " - " + ex.getMessage());
            ERROR = "Error al desconectar la sesión en Documentum  (DameIdRepositorio). - " + ex.getMessage();
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
            // Instantiate an object from the ID.
            IDfSysObject sysObj = (IDfSysObject) sesion.getObject(idObj);
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
                resultado.add(sysObj.getValue(atributo.toLowerCase()).toString());
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
            IDfFolder folder = (IDfFolder) sesion.getObjectByPath(ruta);
            IDfId idfolder = folder.getId("r_object_id");
            IDfId idObj = sesion.getIdByQualification("dm_sysobject where r_object_id='" + idfolder.toString() + "'");
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
                String fechacreacion = resultado.get(0).toString();
                datos.setFechacreacion(fechacreacion);
                listaficheros.add(datos);
            }

        } catch (Exception ex) {
            Utilidades.escribeLog("Error al listar directorio " + ruta + " (ListarFicherosRuta) - Error: " + ex.getMessage());
            if (ex.getMessage() != null) {
                ERROR = "Error al listar directorio " + ruta + " (ListarFicherosRuta) - Error: " + ex.getMessage();
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
            } else {
                if (Escabinet) {
                    myFolder = sesion.getFolderByPath(sysObj.getRepeatingString("r_folder_path", 0));
                } else {
                    myFolder = sesion.getFolderBySpecification(sysObj.getRepeatingString("i_folder_id", 0));
                }
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
// Create a new client instance.
            IDfClientX clientx = new DfClientX();
// Use the factory method to create an IDfExportOperation instance.
            IDfExportOperation eo = clientx.getExportOperation();
// Create a document object that represents the document being exported.
            IDfDocument doc = (IDfDocument) sesion.getObject(new DfId(r_object_id));
// Create an export node, adding the document to the export operation object.
            IDfExportNode node = (IDfExportNode) eo.add(doc);
// Get the document's format.
            IDfFormat format = doc.getFormat();
// If necessary, append a path separator to the targetLocalDirectory value.
            if (directorio.lastIndexOf("/")
                    != directorio.length() - 1
                    && directorio.lastIndexOf("\\")
                    != directorio.length() - 1) {
                directorio += "/";
            }
// Set the full file path on the local system.

            String nombre = doc.getObjectName();
            nombre = nombre.replaceAll("/", "-").replaceAll(":", "-");
            if (doc.getObjectName().endsWith(format.getDOSExtension())) {
                node.setFilePath(directorio + nombre);
            } else {
                node.setFilePath(directorio + nombre + "." + format.getDOSExtension());
            }
// Execute and return results
            if (eo.execute()) {
                if (doc.getObjectName().endsWith(format.getDOSExtension())) {
                    return "OK. Exportado " + directorio + nombre;
                } else {
                    return "OK. Exportado " + directorio + nombre + "." + format.getDOSExtension();
                }
            } else {
                return "Exportación fallida.";
            }
        } catch (Exception ex) {
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

    public static void main(String s[]) {
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
}
