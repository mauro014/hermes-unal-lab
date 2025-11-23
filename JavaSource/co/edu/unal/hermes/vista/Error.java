/*
 * Created on 23-sep-2005
 */
package co.edu.unal.hermes.vista;

/**
 * @author jpduqueg
 */
public class Error {
    
    private String mensaje;
    private String origen;
    private String tipo;
    public static String TIPO_OPERACION="Operación Restringida";

    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    public String getOrigen() {
        return origen;
    }
    public void setOrigen(String origen) {
        this.origen = origen;
    }
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
