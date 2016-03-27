package es.documentum.utilidades;


import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Correo {

    private CorreoPropiedades correopropiedades;
    private String enviarA;
    private String enviarCC;
    private String enviarBCC;
    private String cuerpo;
    private String asunto;
    private String enviadoDesde;

    public CorreoPropiedades getCorreopropiedades() {
        return correopropiedades;
    }

    public void setCorreopropiedades(CorreoPropiedades correopropiedades) {
        this.correopropiedades = correopropiedades;
    }

    public String getEnviadoDesde() {
        return enviadoDesde;
    }

    public void setEnviadoDesde(String enviadoDesde) {
        this.enviadoDesde = enviadoDesde;
    }
    private String[] adjuntos;

    public CorreoPropiedades getCorreoPropiedades() {
        return correopropiedades;
    }

    public void setCorreoPropiedades(CorreoPropiedades propiedades) {
        this.correopropiedades = propiedades;
    }

    public String getEnviarA() {
        return enviarA;
    }

    public void setEnviarA(String enviarA) {
        this.enviarA = enviarA;
    }

    public String getEnviarCC() {
        return enviarCC;
    }

    public void setEnviarCC(String enviarCC) {
        this.enviarCC = enviarCC;
    }

    public String getEnviarBCC() {
        return enviarBCC;
    }

    public void setEnviarBCC(String enviarBCC) {
        this.enviarBCC = enviarBCC;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String[] getAdjuntos() {
        return adjuntos;
    }

    public void setAdjuntos(String[] adjuntos) {
        this.adjuntos = adjuntos;
    }

    public String Enviar() {
        String respuesta = "OK";
        // Preparamos la sesion
        Session session = null;

        if (getCorreoPropiedades().getUsuario() == null || getCorreoPropiedades().getUsuario().isEmpty() || getCorreoPropiedades().getUsuario().equals("null")) {
            session = Session.getInstance(getCorreoPropiedades().getPropiedades(), null);
        } else {
            session = Session.getInstance(getCorreoPropiedades().getPropiedades(),
                    new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(getCorreoPropiedades().getUsuario(), getCorreoPropiedades().getClave());
                }
            });
        }

        try {
            // Cuerpo del mensaje
            BodyPart texto = new MimeBodyPart();
            texto.setText(getCuerpo());

            // Una MultiParte para agrupar texto e imagen.
            MimeMultipart multiParte = new MimeMultipart();
            multiParte.addBodyPart(texto);

            for (String nombrefichero : getAdjuntos()) {
                nombrefichero = nombrefichero.replace("\\", "/");
                addAdjunto(multiParte, nombrefichero);
                System.out.println(nombrefichero);
            }

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(getEnviadoDesde()));
            if (getEnviarA() == null || getEnviarA().isEmpty()) {
                respuesta = "Error, no existe dirección de correo de destino";
                return respuesta;
            }
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(getEnviarA()));
            if (getEnviarBCC() != null && !getEnviarBCC().isEmpty()) {
                message.addRecipient(Message.RecipientType.BCC, new InternetAddress(getEnviarBCC()));
            }
            if (getEnviarCC() != null && !getEnviarCC().isEmpty()) {
                message.addRecipient(Message.RecipientType.CC, new InternetAddress(getEnviarCC()));
            }
            message.setSubject(getAsunto());
            message.setContent(multiParte);
            if (getCorreoPropiedades().getUsuario() == null || getCorreoPropiedades().getUsuario().isEmpty() || getCorreoPropiedades().getUsuario().equals("null")) {
                Transport.send(message);
            } else {
                Transport t = session.getTransport("smtp");
                t.connect(getCorreoPropiedades().getUsuario(), getCorreoPropiedades().getClave());
                t.sendMessage(message, message.getAllRecipients());
                t.close();
            }
        } catch (MessagingException ex) {
            respuesta = "Error al enviar el correo: " + ex.getMessage() + " - " + ex.getLocalizedMessage();
        }

        return respuesta;
    }

    private static void addAdjunto(MimeMultipart multiParte, String archivo) {
        try {
            BodyPart adjunto = new MimeBodyPart();
            adjunto.setDataHandler(new DataHandler(new FileDataSource(archivo)));
            adjunto.setFileName(archivo.substring(archivo.lastIndexOf("/") + 1, archivo.length()));
            multiParte.addBodyPart(adjunto);
        } catch (MessagingException ex) {

        }
    }
}
