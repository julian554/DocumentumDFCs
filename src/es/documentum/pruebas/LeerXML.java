package es.documentum.pruebas;

import es.documentum.Beans.Pistas;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LeerXML {

    public LeerXML() {
    }

    public ArrayList<Pistas> leerXMLPistas(InputStream archivo) {
        ArrayList<Pistas> pistas = new ArrayList<>();
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            Document document = documentBuilder.parse(archivo);
            document.getDocumentElement().normalize();
            NodeList listaPistas = document.getElementsByTagName("pista");
            for (int temp = 0; temp < listaPistas.getLength(); temp++) {
                Node nodo = listaPistas.item(temp);
                //        System.out.println("Elemento:" + nodo.getNodeName());
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nodo;
                    Pistas mipista = new Pistas();
                    mipista.setTipo(element.getElementsByTagName("tipo").item(0).getTextContent());
                    mipista.setTexto(element.getElementsByTagName("texto").item(0).getTextContent());
                    mipista.setSintaxis(element.getElementsByTagName("sintaxis").item(0).getTextContent());
                    pistas.add(mipista);
                }
            }
        } catch (IOException | ParserConfigurationException | DOMException | SAXException ex) {
            System.out.println("Error al leer fichero XML (leerXMLPistas) - Error: " + ex.getMessage());
        }
        return pistas;
    }

    public static void main(String args[]) {


    }
}
