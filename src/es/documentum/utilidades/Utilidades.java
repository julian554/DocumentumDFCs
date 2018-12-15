package es.documentum.utilidades;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.zehon.FileTransferStatus;
import com.zehon.exception.FileTransferException;
import com.zehon.scp.SCP;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipInputStream;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

/**
 *
 * @author julian
 */
public class Utilidades {

    public String DIGIERROR = "";
    public String usuarioldap = "";

    public Utilidades() {
        super();
    }

    public Utilidades(String usuario) {
        super();
        usuarioldap = usuario;
    }

    public boolean comprobarFecha(String fecha) {
        if (fecha == null) {
            return false;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        if (fecha.trim().length() != dateFormat.toPattern().length()) {
            return false;
        }
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(fecha.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    public Boolean crearDirectorio(String dir) {
        if (dir.isEmpty()) {
            return false;
        }
        File directorio = new File(dir);
        return directorio.mkdirs();
    }

    public Boolean existeDirectorio(String dir) {
        if (dir.isEmpty()) {
            return false;
        }
        File directorio = new File(dir);
        return directorio.exists();
    }

    public String crearDirBase() {
        String separador = separador();
        String dirbase;
        dirbase = usuarioHome() + separador + "DocumentumDFCs";
        // Siempre tiene que existir la ruta "DocumentumDFCs" en el "home" del usuario
        crearDirectorio(dirbase);
        crearDirectorio(dirbase + separador + "logs");
        return dirbase;
    }

    public boolean borrarDirectorio(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return true;
        }
        if (!file.isDirectory()) {
            return file.delete();
        }
        return this.borrarDirHijo(file) && file.delete();
    }

    public boolean borrarFichero(String directorio, String nombre) {
        File dir = new File(directorio);
        String ficheros[] = dir.list();
        Boolean resultado = true;

        for (int i = 0; i < ficheros.length; i++) {
            if (nombre.equals("*")) {
                File fich = new File(directorio + this.separador() + ficheros[i]);
                resultado = fich.delete();
                if (!resultado) {
                    return resultado;
                }
            } else if (nombre.startsWith("*") && StringUtils.countMatches(nombre, "*") == 1) {
                if (ficheros[i].endsWith(nombre.substring(1, nombre.length()))) {
                    File fich = new File(directorio + this.separador() + ficheros[i]);
                    resultado = fich.delete();
                    if (!resultado) {
                        return resultado;
                    }
                }
            } else if (nombre.endsWith("*") && StringUtils.countMatches(nombre, "*") == 1) {
                if (ficheros[i].startsWith(nombre.substring(0, nombre.length() - 1))) {
                    File fich = new File(directorio + this.separador() + ficheros[i]);
                    resultado = fich.delete();
                    if (!resultado) {
                        return resultado;
                    }
                }
            } else if (nombre.contains("*")) {
                if (nombre.indexOf("*") == nombre.lastIndexOf("*")) {
                    String inicio = nombre.substring(0, nombre.indexOf("*"));
                    String fin = nombre.substring((nombre.indexOf("*") + 1), nombre.length());
                    if (ficheros[i].startsWith(inicio) && ficheros[i].endsWith(fin)) {
                        File fich = new File(directorio + this.separador() + ficheros[i]);
                        resultado = fich.delete();
                        if (!resultado) {
                            return resultado;
                        }
                    }
                } else {
                    String condicion = nombre;
                    if (nombre.startsWith("*")) {
                        condicion = nombre.substring(1, nombre.length());
                    }
                    String[] cachos = condicion.split("\\*");
                    Boolean encontrado = true;
                    for (int n = 0; n < cachos.length && encontrado; n++) {
                        if (!cachos[n].isEmpty()) {
                            if (!ficheros[i].contains(cachos[n])) {
                                encontrado = false;
                            }
                        }
                    }
                    if (encontrado) {
                        File fich = new File(directorio + this.separador() + ficheros[i]);
                        resultado = fich.delete();
                        if (!resultado) {
                            return resultado;
                        }
                    }

                }
            }
        }

        return resultado;
    }

    public boolean borrarFichero(String nombre) {
        File fich = new File(nombre);
        return fich.delete();
    }

    public long discoLibre(String ruta) {
        DIGIERROR = "";
        long espacio = 0;
        try {
            File dir = new File(ruta);
            espacio = dir.getFreeSpace();
        } catch (Exception ex) {
            DIGIERROR = ex.getMessage();
        }
        return espacio;
    }

    public static void copiarTextoPortapapeles(String cadena) {
        StringSelection selection = new StringSelection(cadena);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);

    }

    public static String pegarTextoPortapapeles() {
        String resultado = "";
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        //odd: the Object param of getContents is not currently used
        Transferable contents = clipboard.getContents(null);
        boolean hasTransferableText = (contents != null)
                && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
        if (hasTransferableText) {
            try {
                resultado = (String) contents.getTransferData(DataFlavor.stringFlavor);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        return resultado;
    }

    private boolean borrarDirHijo(File dir) {
        File[] children = dir.listFiles();
        boolean childrenDeleted = true;
        for (int i = 0; children != null && i < children.length; i++) {
            File child = children[i];
            if (child.isDirectory()) {
                childrenDeleted = this.borrarDirHijo(child) && childrenDeleted;
            }
            if (child.exists()) {
                childrenDeleted = child.delete() && childrenDeleted;
            }
        }
        return childrenDeleted;
    }

    public String dirBase() {
        String SO = so();
        String separador = "\\";
        if (!SO.toLowerCase().contains("windows")) {
            separador = "/";
        }
        String dirbase = usuarioHome() + separador + "DocumentumDFCs";
        /*
        if (!SO.toLowerCase().contains("windows")) {
            dirbase = usuarioHome() + separador + "DocumentumDFCs";
        } else {
            dirbase = usuarioHome() + separador + "DocumentumDFCs";
        }
         */
        return dirbase;
    }

    public void crearFichero(String nombrefichero, String tipo) {
        DIGIERROR = "";
        if (!existeFichero(nombrefichero)) {
            try {
                String cabecera = "";
                BufferedWriter bw = new BufferedWriter(new FileWriter(nombrefichero));
                if (tipo.toLowerCase().equals("xml")) {
                    cabecera = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
                }
                bw.write(cabecera);
                bw.close();
            } catch (IOException ioe) {
                DIGIERROR = ioe.getMessage();
            }
        }
    }

    public void escribeFichero(String nombrefichero, String linea) {
        DIGIERROR = "";
        if (existeFichero(nombrefichero)) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(nombrefichero, true));
                bw.newLine();
                bw.write(linea);
                bw.close();
            } catch (IOException ioe) {
                DIGIERROR = ioe.getMessage();
            }
        }
    }

    public Boolean existeFichero(String nombrefichero) {
        File fichero = new File(nombrefichero);
        return fichero.exists();
    }

    public Boolean copiarFichero(String origen, String destino) {
        String comando;

        if (so().toLowerCase().startsWith("windows")) {
            comando = "cmd /c copy /Y \"" + origen + "\"" + " \"" + destino + "\"";
        } else {
            comando = "cp -f " + origen + " " + destino;
        }
        int resp = llamarSO(comando);
        return resp == 0;
    }

    public Boolean renombrarFichero(String origen, String destino) {
        String comando;

        if (so().toLowerCase().startsWith("windows")) {
            comando = "ren \"" + origen + "\"" + " \"" + destino + "\"";
        } else {
            comando = "mv " + origen + " " + destino;
        }

        int resp = llamarSO(comando);
        return resp == 0;
    }

    public boolean zipArchivos(String archivozip, String carpeta, String extension) {
        int BUFFER = 1024;
        DIGIERROR = "";
        //Declaramos el FileOutputStream para guardar el archivo
        FileOutputStream dest;
        try {
            //Nuestro InputStream
            BufferedInputStream origen;
            dest = new FileOutputStream(archivozip);
            //Indicamos que será un archivo ZIP
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
            //Indicamos que el archivo tendrá compresión
            out.setMethod(ZipOutputStream.DEFLATED);
            byte data[] = new byte[BUFFER];
            // Creamos la referencia de la carpeta a leer
            File dir = new File(carpeta);
            // Guarda los nombres de los archivos de la carpeta a leer
            String files[] = dir.list();
            File[] listaFicheros = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (new EvaluaExtension().accept(listaFicheros[i], "." + extension) || extension.endsWith("*")) {

                    //Creamos el objeto a guardar para cada uno de los elementos del listado
                    FileInputStream fi = new FileInputStream(carpeta + separador() + files[i]);
                    origen = new BufferedInputStream(fi, BUFFER);
                    ZipEntry entry = new ZipEntry(files[i]);
                    //Guardamos el objeto en el ZIP
                    out.putNextEntry(entry);
                    int count;
                    //Escribimos el objeto en el ZIP
                    while ((count = origen.read(data, 0, BUFFER)) != -1) {
                        out.write(data, 0, count);
                    }
                    origen.close();
                }
            }
            out.close();
        } catch (IOException ex) {
            escribeLog("Error al crear el fichero ZIP -zipArchivos- '" + archivozip + "'. Error - " + ex.getMessage());
            DIGIERROR = ex.getMessage();
            return false;
        }
        return true;
    }

    public boolean zipArchivo(String archivozip, String nombre) {
        int BUFFER = 1024;
        DIGIERROR = "";
        //Declaramos el FileOutputStream para guardar el archivo
        FileOutputStream dest;
        try {
            //Nuestro InputStream
            BufferedInputStream origen;
            dest = new FileOutputStream(archivozip);
            //Indicamos que será un archivo ZIP
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
            //Indicamos que el archivo tendrá compresión
            out.setMethod(ZipOutputStream.DEFLATED);
            byte data[] = new byte[BUFFER];
            FileInputStream fi = new FileInputStream(nombre);
            origen = new BufferedInputStream(fi, BUFFER);
            ZipEntry entry = new ZipEntry(nombre.substring(nombre.lastIndexOf(separador()) + 1, nombre.length()));
            //Guardamos el objeto en el ZIP
            out.putNextEntry(entry);
            int count;
            //Escribimos el objeto en el ZIP
            while ((count = origen.read(data, 0, BUFFER)) != -1) {
                out.write(data, 0, count);
            }
            origen.close();
            out.close();
        } catch (IOException ex) {
            escribeLog("Error al crear el fichero ZIP -zipArchivo- '" + archivozip + "'. Error - " + ex.getMessage());
            DIGIERROR = ex.getMessage();
            return false;
        }
        return true;
    }
    private static final int BUFFER_SIZE = 4096;

    public void unzip(String zipFilePath, String destDirectory) {
        DIGIERROR = "";
        try {
            ZipInputStream zipIn;
            File destDir = new File(destDirectory);
            if (!destDir.exists()) {
                destDir.mkdir();
            }
            zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
            ZipEntry entry = zipIn.getNextEntry();
            // iterates over entries in the zip file
            while (entry != null) {
                String filePath = destDirectory + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    // if the entry is a file, extracts it
                    extractFile(zipIn, filePath);
                } else {
                    // if the entry is a directory, make the directory
                    File dir = new File(filePath);
                    dir.mkdir();
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
            zipIn.close();
        } catch (IOException ex) {
            escribeLog("Error al extraer -unzip- del fichero ZIP " + zipFilePath + " - " + ex.getMessage());
            DIGIERROR = ex.getMessage();
        }
    }

    public void unzip(String zipFilePath, String destDirectory, String fichero) {
        DIGIERROR = "";
        try {
            ZipInputStream zipIn;
            File destDir = new File(destDirectory);
            if (!destDir.exists()) {
                destDir.mkdir();
            }
            zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
            ZipEntry entry = zipIn.getNextEntry();
            // iterates over entries in the zip file
            while (entry != null) {
                if (!entry.getName().toLowerCase().equals(fichero.toLowerCase())) {
                    entry = zipIn.getNextEntry();
                } else {
                    String filePath = destDirectory + File.separator + entry.getName();
                    if (!entry.isDirectory()) {
                        // if the entry is a file, extracts it
                        extractFile(zipIn, filePath);
                    } else {
                        // if the entry is a directory, make the directory
                        File dir = new File(filePath);
                        dir.mkdir();
                    }
                    zipIn.closeEntry();
                    break;
                }
            }
            zipIn.close();
        } catch (IOException ex) {
            escribeLog("Error al extraer " + fichero + " -unzip- del fichero ZIP " + zipFilePath + " - " + ex.getMessage());
            DIGIERROR = ex.getMessage();
        }
    }

    private void extractFile(ZipInputStream zipIn, String filePath) {
        DIGIERROR = "";
        BufferedOutputStream bos;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(filePath));
            byte[] bytesIn = new byte[BUFFER_SIZE];
            int read = 0;
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
            bos.close();
        } catch (IOException ex) {
            escribeLog("Error al extraer -extractFile- ... - " + ex.getMessage());
            DIGIERROR = ex.getMessage();
        }
    }

    public MiProperties leerPropeties(String archivo) {
        DIGIERROR = "";
        MiProperties props = null;
        try {
            //Cargamos el archivo 
            FileInputStream ini = new FileInputStream(archivo);
            props = new MiProperties();
            props.load(ini);
            ini.close();
        } catch (IOException ex) {
            escribeLog("Error al leer el fichero de propiedades " + archivo + " - " + ex.getMessage());
            DIGIERROR = ex.getMessage();
        }
        return props;
    }

    public void escribirProperties(String archivo, MiProperties props) {
        DIGIERROR = "";
        try {
            //Cargamos el archivo 
            File fichero = new File(archivo);
            // Properties props = new Properties();
            FileOutputStream ops = new FileOutputStream(fichero, false);
            props.store(ops, "\n");
            ops.flush();
            ops.close();
        } catch (IOException ex) {
            escribeLog("Error al escribir en el fichero de propiedades " + archivo + " - " + ex.getMessage());
            DIGIERROR = ex.getMessage();
        }

    }

    public String separador() {
        String SO = so();
        String separador = "/";
        if (SO.toLowerCase().contains("windows")) {
            separador = "/";
        }
        return separador;
    }

    public String nombrePC() {
        return System.getenv("COMPUTERNAME");
    }

    public String usuario() {
        if (!usuarioldap.isEmpty()) {
            return usuarioldap;
        } else {
            return System.getProperty("user.name");
        }
    }

    public String procesador() {
        return System.getenv("PROCESSOR_IDENTIFIER");
    }

    public String so() {
        return System.getProperty("os.name");
    }

    public String soBits() {
//        for (Map.Entry<Object, Object> e : System.getProperties().entrySet()) {
//            System.out.println(e);
//        }

        String version = " x86";

        if (System.getenv("ProgramW6432") != null) {
            version = " x64";
        }

        return version;
    }

    public String versionJDK() {
        return System.getProperty("java.version");
    }

    public String usuarioHome() {
        return System.getProperty("user.home").replace("\\", "/");
    }

    public String usuarioDir() {
        return System.getProperty("user.dir").replace("\\", "/");
    }

//    public String wsServidor() {
//        Utilidades util = new Utilidades();
//        Properties prop = util.leerConfiguracion("es/seap/minhap/ws/propiedades/ws-servidor.properties");
//        String servidor = prop.getProperty("servidor");
//        return servidor;
//    }
//
//    public String wsUrl() {
//        Utilidades util = new Utilidades();
//        Properties prop = util.leerConfiguracion("es/seap/minhap/ws/propiedades/ws-servidor.properties");
//        String wsurl = prop.getProperty("wsurl");
//        return wsurl;
//    }
    public String versionJavaBits() {
        return System.getProperty("os.arch");
    }

    public String ip() {
        String ip = "";
        DIGIERROR = "";
        try {
            //     InetAddress address = InetAddress.getByName("localhost");
            InetAddress address = InetAddress.getLocalHost();
            //     address = InetAddress.getLocalHost();
            // Coge la dirección ip como un array de bytes
            byte[] bytes = address.getAddress();
            // Convierte los bytes de la dirección ip a valores sin
            // signo y los presenta separados por espacios
            for (int cnt = 0; cnt < bytes.length; cnt++) {
                int uByte = bytes[cnt] < 0 ? bytes[cnt] + 256 : bytes[cnt];
                if (ip.equals("")) {
                    ip = ip + uByte;
                } else {
                    ip = ip + "." + uByte;
                }
            }

            StringTokenizer st = new StringTokenizer(ip, ".");
            ip = "";
            while (st.hasMoreTokens()) {
                String ip3 = st.nextToken();
                int tam = ip3.length();
                for (int i = tam; i < 3; i++) {
                    ip3 = "0" + ip3;
                }
                if (ip.equals("")) {
                    ip = ip3;
                } else {
                    ip = ip + "." + ip3;
                }
            }
        } catch (UnknownHostException ex) {
            ip = "000.000.000.000";
            DIGIERROR = ex.getMessage();
        }
        return ip;
    }

    public void sacarArchivoJar(String Archivo, String RutaDescarga) {
        DIGIERROR = "";
        try {
            InputStream in = getClass().getResourceAsStream(Archivo);
            OutputStream out = new FileOutputStream(RutaDescarga);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();

        } catch (IOException ex) {
            escribeLog(ex.getMessage());
            DIGIERROR = ex.getMessage();
        }
    }
    // Carga Una DLL, en este caso la coge del directorio "drivers" del "home" del usuario

    static public boolean load(Class cl, String name) {
        String libname = System.mapLibraryName(name);
        Utilidades soutil = new Utilidades();
        String ruta = soutil.dirBase() + soutil.separador() + "drivers" + soutil.separador() + libname;
        if (!soutil.versionJavaBits().equals("x86")) {
            ruta = soutil.dirBase() + soutil.separador() + "drivers" + soutil.separador() + "x64" + soutil.separador() + libname;
        }

        try {
            System.load(new File(ruta).getAbsolutePath());

        } catch (Exception ex) {
            escribeLog(Utilidades.class.getName() + ".load:\n\tError: " + ex.getMessage() + " - No se ha cargado la librería [" + ruta + "]");
            return false;
        }
        return true;
    }

    @SuppressWarnings("SuspiciousSystemArraycopy")
    public Object resizeArray(Object oldArray, int newSize) {
        int oldSize = java.lang.reflect.Array.getLength(oldArray);
        Class elementType = oldArray.getClass().getComponentType();
        Object newArray = java.lang.reflect.Array.newInstance(
                elementType, newSize);
        int preserveLength = Math.min(oldSize, newSize);
        if (preserveLength > 0) {
            System.arraycopy(oldArray, 0, newArray, 0, preserveLength);
        }
        return newArray;
    }

    public static int llamarSO(String comando) {
        String s = null;
        try {
            // Ejecutamos el comando
            Process p = Runtime.getRuntime().exec(comando);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(
                    p.getErrorStream()));

            // Leemos la salida del comando
            // escribeLog("Ésta es la salida standard del comando:\n");
            while ((s = stdInput.readLine()) != null) {
                //     escribeLog(s);
            }

            return 0;
        } catch (IOException e) {
            escribeLog("Excepción en -llamarSO- : " + e.getMessage());
            return -1;
        }
    }

    public static void escribeLog(String texto) {
        String fecha = today() + " " + now();
        System.out.println(fecha + " - " + texto);
    }

    public static String now() {
        Calendar cal = Calendar.getInstance();
        String hora = String.valueOf(cal.get(Calendar.HOUR_OF_DAY)).length() == 1 ? "0" + String.valueOf(cal.get(Calendar.HOUR_OF_DAY)) : String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
        String minuto = String.valueOf(cal.get(Calendar.MINUTE)).length() == 1 ? "0" + String.valueOf(cal.get(Calendar.MINUTE)) : String.valueOf(cal.get(Calendar.MINUTE));
        String segundo = String.valueOf(cal.get(Calendar.SECOND)).length() == 1 ? "0" + String.valueOf(cal.get(Calendar.SECOND)) : String.valueOf(cal.get(Calendar.SECOND));
        String horaactual = hora + ":" + minuto + ":" + segundo;
        return horaactual;
    }

    public static String today() {
        Calendar cal = Calendar.getInstance();
        String anio = String.valueOf(cal.get(Calendar.YEAR));
        String mes = String.valueOf((cal.get(Calendar.MONTH) + 1)).length() == 1 ? "0" + String.valueOf((cal.get(Calendar.MONTH) + 1)) : String.valueOf((cal.get(Calendar.MONTH) + 1));
        String dia = String.valueOf(cal.get(Calendar.DAY_OF_MONTH)).length() == 1 ? "0" + String.valueOf(cal.get(Calendar.DAY_OF_MONTH)) : String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        String fecha = dia + "/" + mes + "/" + anio;
        return fecha;
    }

    public static byte[] getBytesFromFileName(String fichero) throws IOException {
        File file = new File(fichero);
        InputStream is = new FileInputStream(file);
        long length = file.length();

        if (length > Integer.MAX_VALUE) {
        }

        byte[] bytes = new byte[(int) length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        is.close();
        return bytes;
    }

    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        long length = file.length();

        if (length > Integer.MAX_VALUE) {
        }

        byte[] bytes = new byte[(int) length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        is.close();
        return bytes;
    }

    public MiProperties leerConfiguracion(String ruta) {
        MiProperties props = new MiProperties();

        DIGIERROR = "";
        try {
            InputStream in = Utilidades.class
                    .getClassLoader().getResourceAsStream(ruta);
            if (in == null) {
                escribeLog("Error al cargar el fichero de propiedades: " + ruta);
            } else {
                props = new MiProperties();
                props.load(in);
            }
        } catch (IOException ex) {
            escribeLog("Error al cargar el fichero de propiedades " + ruta + ". Error: " + ex.getMessage());
            DIGIERROR = ex.getMessage();
        }
        return props;
    }

    public String scp(String fichero, String fichero_destino) {
        String resultado = "";
        Properties props = leerConfiguracion("../propiedades/scp.properties");
        String servidor = props.getProperty("servidor");
        String usuario = props.getProperty("usuario");
        usuario = decodificaBase64(hexToASCII(usuario));
        String clave = props.getProperty("clave");
        clave = decodificaBase64(hexToASCII(clave));
        String carpeta_destino = props.getProperty("carpeta_destino");
        carpeta_destino = decodificaBase64(hexToASCII(carpeta_destino));
        DIGIERROR = "";
        try {
            int status = SCP.sendFile(fichero, carpeta_destino, fichero_destino, servidor, usuario, clave);
            if (FileTransferStatus.SUCCESS == status) {
                resultado = "OK";
            } else if (FileTransferStatus.FAILURE == status) {
                escribeLog("Fallo al copiar -scp- " + fichero + " al servidor " + servidor + " en el directorio " + carpeta_destino);
                resultado = "" + status;
            }
        } catch (FileTransferException ex) {
            escribeLog("Error al copiar -scp- el fichero " + fichero + " al servidor " + servidor + " en el directorio " + carpeta_destino + " - " + ex.getMessage());
            DIGIERROR = ex.getMessage();
        }
        return resultado;
    }

    public Boolean cambiarPermisoRemoto(String servidor, String usuario, String clave, String nombre, int permisos) {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(usuario, servidor, 22);
            session.setPassword(clave);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp cSftp = (ChannelSftp) channel;
            cSftp.chmod(permisos, nombre);
            channel.disconnect();
            session.disconnect();
        } catch (JSchException | SftpException ex) {
            System.out.println("Error al cambiar permisos de " + nombre + " en " + servidor + " - Error - " + ex.getMessage());
            return false;
        }
        return true;
    }

    public Boolean ejecutarComandoRemoto(String servidor, String usuario, String clave, String comando) {
        return ejecutarComandoRemoto(servidor, usuario, clave, comando, 22);
    }

    public Boolean ejecutarComandoRemoto(String servidor, String usuario, String clave, String comando, int puerto) {
        try {
            JSch jsch = new JSch();
            Session sesion = jsch.getSession(usuario, servidor, puerto);
            sesion.setPassword(clave);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            sesion.setConfig(config);
            sesion.connect();
            Channel canal = sesion.openChannel("exec");
            ChannelExec canalexec = (ChannelExec) canal;
            canalexec.setCommand(comando);
            canalexec.setErrStream(System.err);
            canalexec.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(canalexec.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            canalexec.disconnect();
            sesion.disconnect();
            return canalexec.getExitStatus() == 0;
        } catch (JSchException ex) {
            escribeLog("Error al establecer sesión remota. Error - " + ex.getMessage());
            return false;
        } catch (IOException ex) {
            escribeLog("Error obtener stream remoto. Error - " + ex.getMessage());
            return false;
        }
    }

    public List<String> comandoRemoto(String servidor, String usuario, String clave, String comando) {
        return comandoRemoto(servidor, usuario, clave, comando, 22);
    }

    public List<String> comandoRemoto(String servidor, String usuario, String clave, String comando, int puerto) {
        List<String> resultado = new ArrayList<>();
        try {
            JSch jsch = new JSch();
            Session sesion = jsch.getSession(usuario, servidor, puerto);
            sesion.setPassword(clave);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            sesion.setConfig(config);
            sesion.connect();
            Channel canal = sesion.openChannel("exec");
            ChannelExec canalexec = (ChannelExec) canal;
            canalexec.setCommand(comando);
            canalexec.setErrStream(System.err);
            canalexec.connect();

            BufferedReader readererro = new BufferedReader(new InputStreamReader(canalexec.getErrStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(canalexec.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                resultado.add(line);
            }

            if (readererro.ready()) {
                System.out.println("Salida de error ...");
                resultado.add("Salida de error ...");
                while ((line = readererro.readLine()) != null) {
                    System.out.println(line);
                    resultado.add(line);
                }
            }

            canalexec.disconnect();
            sesion.disconnect();
//            return canalexec.getExitStatus() == 0;
            //          return resultado;
        } catch (JSchException ex) {
            escribeLog("Error al establecer sesión remota. Error - " + ex.getMessage());
            resultado.add("Error al establecer sesión remota. Error - " + ex.getMessage());

        } catch (IOException ex) {
            escribeLog("Error obtener stream remoto. Error - " + ex.getMessage());
            resultado.add("Error obtener stream remoto. Error - " + ex.getMessage());
        }
        return resultado;
    }

    public Boolean validarNombreLote(String fichero) {
        Boolean resultado = false;

        if (fichero == null) {
            return resultado;
        }
        if (fichero.length() != 32) {
            return resultado;
        }
        if (!fichero.contains("-")) {
            return resultado;
        }
        Utilidades util = new Utilidades();
        String ip = util.ip();
        ip = ip.replace(".", "");
        String nombre = fichero.substring(1, 28);
        if (!nombre.contains(ip)) {
            return resultado;
        }

        resultado = true;
        return resultado;
    }

    public String codificaBase64(String cadena) {
        String resultado = "";
        DIGIERROR = "";
        try {
            byte[] codificado = Base64.encodeBase64(cadena.getBytes());
            resultado = new String(codificado);
        } catch (Exception ex) {
            escribeLog("Error al codificar en Base64 '" + cadena + "' - " + ex.getMessage());
            DIGIERROR = ex.getMessage();
        }
        return resultado;
    }

    public String decodificaBase64(String cadena) {
        String resultado = "";
        DIGIERROR = "";
        try {
            byte[] decodificado = Base64.decodeBase64(cadena);
            resultado = new String(decodificado);
        } catch (Exception ex) {
            escribeLog("Error al decodificar de Base64 '" + cadena + "' - " + ex.getMessage());
            DIGIERROR = ex.getMessage();
        }
        return resultado;
    }

    public ArrayList<String> listaTextoSinDuplicados(ArrayList<String> lista) {
        ArrayList<String> listasin = new ArrayList<>(new HashSet<>(lista));
        Collections.sort(listasin);
        return listasin;
    }

    public String hexToASCII(String hex) {
        if (hex.length() % 2 != 0) {
            escribeLog("La cadena tiene que tener un número par de caracteres");
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hex.length() - 1; i += 2) {
            String output = hex.substring(i, (i + 2));
            int decimal = Integer.parseInt(output, 16);
            sb.append((char) decimal);
        }
        return sb.toString();
    }

    public String asciiToHex(String ascii) {
        StringBuilder hex = new StringBuilder();
        for (int i = 0; i < ascii.length(); i++) {
            hex.append(Integer.toHexString(ascii.charAt(i)));
        }
        return hex.toString();
    }

    public void exportaExcel(JTable table, String fichero) {
        try {
            TableModel modelo = table.getModel();
            OutputStreamWriter ficheroexcel = new OutputStreamWriter(new FileOutputStream(fichero), "ISO-8859-15");

            for (int i = 0; i < modelo.getColumnCount(); i++) {
                ficheroexcel.write(modelo.getColumnName(i) + "\t");
            }
            ficheroexcel.write("\n");

            for (int i = 0; i < modelo.getRowCount(); i++) {
                for (int j = 0; j < modelo.getColumnCount(); j++) {
                    ficheroexcel.write((modelo.getValueAt(i, j) == null ? "" : modelo.getValueAt(i, j).toString()) + "\t");
                }
                ficheroexcel.write("\n");
            }
            ficheroexcel.close();

        } catch (IOException ex) {
            escribeLog("Error al generar el fichero Excel de salida (exportaExcel) Error - " + ex.getMessage());
        }
    }

    public void exportarAExcel(JTable table, String ruta, String hoja) {
        try {
            //   new WorkbookFactory();
            Workbook wb = new XSSFWorkbook(); //Excell workbook

            Sheet sheet = (Sheet) wb.createSheet(); //WorkSheet
            wb.setSheetName(0, hoja);
            Row row = sheet.createRow(2); //Row created at line 3
            TableModel model = table.getModel(); //Table model

            Row headerRow = sheet.createRow(0); //Create row at line 0
            for (int headings = 0; headings < model.getColumnCount(); headings++) { //For each column
                String nombrecol = model.getColumnName(headings) == null ? "" : model.getColumnName(headings);
                headerRow.createCell(headings).setCellValue(nombrecol);//Write column name
            }

            for (int rows = 0; rows < model.getRowCount(); rows++) { //For each table row
                for (int cols = 0; cols < table.getColumnCount(); cols++) { //For each table column
                    String valor = model.getValueAt(rows, cols) == null ? "" : model.getValueAt(rows, cols).toString();
                    row.createCell(cols).setCellValue(valor); //Write value
                    sheet.autoSizeColumn(cols);
                }

                //Set the row to the next one in the sequence
                row = sheet.createRow((rows + 3));

            }

            FileOutputStream fichero = new FileOutputStream(ruta);
            wb.write(fichero);//Save the file     
            wb.close();
            fichero.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) {
            return bytes + " B";
        }
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "KMGTPE" : "KMGTPE").charAt(exp - 1) + "";
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    public static int OctalToDecimal(String octo) {
        int number = 0;      // init result
        for (int i = 0; i < octo.length(); i++) { // pass through all input characters
            char digit = octo.charAt(i);            // fetch octal digit
            digit -= '0';                           // translate to number (integer)
            if (digit < 0 || digit > 7) {          // validate user inpu
                System.out.println("No es un numero octal válido");
                return -1;
            }
            number *= 8;                            // shift to left what I already ahve
            number += digit;                        // add new number
        }
        return number;
    }

    public static void recorrerDir(String ruta) {
        File dir = new File(ruta);
        File listFile[] = dir.listFiles();
        if (listFile != null) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                    recorrerDir(listFile[i].getPath());
                } else {
                    System.out.println(listFile[i].getPath());
                }
            }
        }
    }

    public String rellenarPorIzquierda(String cadena, String valor, int numveces) {
        String resultado = "";
        resultado = org.apache.commons.lang.StringUtils.leftPad(cadena, numveces, valor);
        return resultado;
    }

    public static void main(String args[]) {
        Utilidades util = new Utilidades();
    }
}

class EvaluaExtension implements FilenameFilter {

    @Override
    public boolean accept(File dir, String extension) {
        return dir.getName().endsWith(extension);
    }
}
