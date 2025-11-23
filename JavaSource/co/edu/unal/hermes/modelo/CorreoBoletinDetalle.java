package co.edu.unal.hermes.modelo;

/**
 * 
 * @author Wilver Alexander Martínez Martínez - wam².
 */
public class CorreoBoletinDetalle{

    // llave primaria
    private Long id;
    
    private long tipoPersonaCorreo;
    private String documentoPersona;
    private String tipoDocumento;
    private String fecha;
    private String ultimaDireccion;
    private long idUltimaDireccion;
    private long tipo;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public long getTipoPersonaCorreo() {
		return tipoPersonaCorreo;
	}
	public void setTipoPersonaCorreo(long tipoPersonaCorreo) {
		this.tipoPersonaCorreo = tipoPersonaCorreo;
	}
	public String getDocumentoPersona() {
		return documentoPersona;
	}
	public void setDocumentoPersona(String documentoPersona) {
		this.documentoPersona = documentoPersona;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public long getTipo() {
		return tipo;
	}
	public void setTipo(long tipo) {
		this.tipo = tipo;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getUltimaDireccion() {
		return ultimaDireccion;
	}
	public void setUltimaDireccion(String ultimaDireccion) {
		this.ultimaDireccion = ultimaDireccion;
	}
	public long getIdUltimaDireccion() {
		return idUltimaDireccion;
	}
	public void setIdUltimaDireccion(long idUltimaDireccion) {
		this.idUltimaDireccion = idUltimaDireccion;
	}
    
	
   
   
  
    
}
