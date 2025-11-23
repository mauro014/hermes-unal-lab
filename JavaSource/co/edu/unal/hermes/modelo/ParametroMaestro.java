/*
 * Created on 23-dic-2023
 */
package co.edu.unal.hermes.modelo;

/**
 * Específica la parametros usados en el sistema para conexión con aplicaciones externas.
 * 
 */
public class ParametroMaestro {

	public static final String LDAP_SERVER = "LDAP_SERVER";
	public static final String LDAP_PORT = "LDAP_PORT";
	public static final String EMAIL_SERVER = "EMAIL_SERVER";
	public static final String EMAIL_BOLETIN = "DIR_EMAIL_BOLETIN";
	public static final String PASS_BOLETIN = "PASS_EMAIL_BOLETIN";

    /** The id. */
    private Long id;

    /** The descripcion. */
    private String descripcion;

    /** The valor. */
    private String valor;

    /** The nombre. */
    private String nombre;
    
    private String infoAdicional;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getInfoAdicional() {
		return infoAdicional;
	}

	public void setInfoAdicional(String infoAdicional) {
		this.infoAdicional = infoAdicional;
	}

   

}
