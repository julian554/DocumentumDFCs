package es.documentum.pruebas;

import com.documentum.com.DfClientX;
import com.documentum.com.IDfClientX;
import com.documentum.fc.client.*;
import com.documentum.fc.client.acs.*;
import com.documentum.fc.common.*;
import es.documentum.utilidades.ClassPathUpdater;
import es.documentum.utilidades.MiProperties;
import es.documentum.utilidades.Utilidades;
import es.documentum.utilidades.UtilidadesDocumentum;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;

public class TestACSURLs {

    static String servidor = "vilcs470";

    public static void main(String[] args) {

        String strDocbase = "D_A1_CYC";
        String strUser = "dmadmin";
        String strPassword = "documentum";

        String r_object_id = "";

        ArrayList<String> lista = new ArrayList<String>();
        lista.add("090003fc8057e9cf");
        lista.add("090003fc8057e9d0");
        lista.add("090003fc8057e9d1");
        lista.add("090003fc8057e9d2");

        Iterator<String> nombreIterator = lista.iterator();

        try {
            while (nombreIterator.hasNext()) {
                printAcsURLs(strDocbase, strUser, strPassword, nombreIterator.next());
            }
        } catch (DfException ex) {
            ex.printStackTrace();
        }

    }

    private static void printAcsURLs(String strDocbase, String strUser, String strPassword, String objId) throws DfException {
        try {
            Utilidades util = new Utilidades();
            String dirdfc = util.usuarioHome() + util.separador() + "documentumdfcs" + util.separador() + "documentum" + util.separador() + "shared" + util.separador();
            try {
                ClassPathUpdater.add(dirdfc);
                ClassPathUpdater.add(dirdfc + "lib" + util.separador() + "jcmFIPS.jar");
            } catch (IOException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
                Utilidades.escribeLog("Error al actualizar el Classpath  - Error: " + ex.getMessage());
            }
            MiProperties prop = util.leerPropeties(dirdfc + "dfc.properties");
            prop.setProperty("dfc.docbroker.host[0]", servidor);
            prop.setProperty("dfc.docbroker.port[0]", "1489");
            prop.setProperty("dfc.acs.request.expiration_interval", "3");
            util.escribirProperties(dirdfc + "dfc.properties", prop);

            UtilidadesDocumentum utildocum = new UtilidadesDocumentum(dirdfc + "dfc.properties");
            IDfSession session = null;
//            IDfClient client = DfClient.getLocalClient();
//            IDfSessionManager sessionManager = client.newSessionManager();
//            sessionManager.setIdentity(strDocbase, new DfLoginInfo(strUser, strPassword));
//            session = sessionManager.newSession(strDocbase);
            session = utildocum.conectarDocumentum(strUser, strPassword, strDocbase, servidor, "1489");

            if (session.isConnected()) {
                String miURL = "";
                IDfSysObject sysObject = (IDfSysObject) session.getObject(new DfId(objId));
                String formatName = sysObject.getFormat().getName();
                IDfEnumeration acsRequests = sysObject.getAcsRequests(formatName, 0, null, constructPreferences());
                if (acsRequests.hasMoreElements()) {
                    while (acsRequests.hasMoreElements()) {
                        IDfAcsRequest acsRequest = (IDfAcsRequest) acsRequests.nextElement();
                        String acsUrl = acsRequest.makeURL();
                        miURL = acsUrl;
                        //            System.out.println(" URL for object id " + objId + " = " + acsUrl);
                    }
                } else {
                    System.out.println(" No URL for " + objId);
                }
                String nombre = "&transient=Content-Disposition::attachment;filename=" + "\"" + sysObject.getObjectName() + "\"";
                miURL = miURL + nombre;
                System.out.println(miURL);
                session.disconnect();
            }
        } catch (DfException e) {
            System.out.println(e.toString());
        }
    }

    private static IDfAcsTransferPreferences constructPreferences() {
        IDfClientX clientX = new DfClientX();
        IDfAcsTransferPreferences transferPrefs = clientX.getAcsTransferPreferences();
        transferPrefs.preferAcsTransfer(true);
        return transferPrefs;
    }
}
