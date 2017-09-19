package es.documentum.utilidades;


import java.util.Properties;

public class CorreoPropiedades {

    Properties propiedades;
    private String servidorsmtp;
    private String usuario;
    private String clave;
    private String puertosmtp;

    public Properties getPropiedades() {
        return propiedades;
    }

    public void setPropiedades(Properties propiedades) {
    //    propiedades.put("mail.debug", "true");
        propiedades.put("mail.smtp.host", servidorsmtp);
        propiedades.put("mail.smtp.port", puertosmtp);
        propiedades.put("mail.transport.protocol", "smtp");
        if (!usuario.isEmpty() && usuario != null && !usuario.equals("null")) {
            propiedades.put("mail.smtp.auth", "true");
            propiedades.put("mail.smtp.user", usuario);
            propiedades.put("mail.smtp.password", clave);
            propiedades.put("mail.smtp.starttls.enable", "true");
            propiedades.put("mail.smtp.starttls.required", "true");
            propiedades.put("mail.smtp.ssl.trust", servidorsmtp);
        }else{
            propiedades.put("mail.smtp.auth", "false");
            propiedades.put("mail.smtp.starttls.enable", "false");
            propiedades.put("mail.smtp.starttls.required", "false");
        }
        this.propiedades = propiedades;
    }

    public String getServidorsmtp() {
        return servidorsmtp;
    }

    public void setServidorsmtp(String servidorsmtp) {
        this.servidorsmtp = servidorsmtp;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getPuertosmtp() {
        return puertosmtp;
    }

    public void setPuertosmtp(String puertosmtp) {
        this.puertosmtp = puertosmtp;
    }
}
