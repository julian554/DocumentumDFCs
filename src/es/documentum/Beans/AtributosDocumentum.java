package es.documentum.Beans;

import java.io.Serializable;

public class AtributosDocumentum implements Serializable {

    private String nombre;
    private String valor;
    private boolean multivalor;
    private String tipoobjeto; 
    private String aplicacion;
    private String fechacreacion;
    private String usuario;
    private int tipo;
    private int longitud;
    private boolean checkin;

    public boolean isCheckin() {
        return checkin;
    }

    public void setCheckin(boolean checkin) {
        this.checkin = checkin;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public boolean isMultivalor() {
        return multivalor;
    }

    public void setMultivalor(boolean multivalor) {
        this.multivalor = multivalor;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    public String getTipoobjeto() {
        return tipoobjeto;
    }

    public void setTipoobjeto(String tipoobjeto) {
        this.tipoobjeto = tipoobjeto;
    }

    public String getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(String aplicacion) {
        this.aplicacion = aplicacion;
    }

    public String getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(String fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    

}
