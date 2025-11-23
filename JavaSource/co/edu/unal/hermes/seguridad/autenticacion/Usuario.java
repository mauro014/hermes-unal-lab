package co.edu.unal.hermes.seguridad.autenticacion;
/*
 * Created on 31-ago-2005
 */
/**
 * @author Juan Pablo
 */
public class Usuario {
    private String uid;
    private String nombre;
    private String rol;
    private String cedula;
    private String apellidos;

    public String getCedula() {
        return cedula;
    }
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
}
