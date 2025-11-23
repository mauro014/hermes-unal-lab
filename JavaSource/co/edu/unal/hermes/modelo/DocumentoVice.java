/*
 * Created on 18-ago-2005
 */
package co.edu.unal.hermes.modelo;

public class DocumentoVice {

    private Long id;    
    private String nombre;
    private String estado;
    private String esArchivo;
    private Long idPadre;
    
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNombre() {
		return nombre;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getEstado() {
		return estado;
	}
	public void setIdPadre(Long idPadre) {
		this.idPadre = idPadre;
	}
	public Long getIdPadre() {
		return idPadre;
	}
	public String getEsArchivo() {
		return esArchivo;
	}
	public void setEsArchivo(String esArchivo) {
		this.esArchivo = esArchivo;
	}
	
	public void setArchivoBoolean(Boolean esArchivoBoolean){
		if(esArchivoBoolean){
			esArchivo = "Y";
		}
		else esArchivo = "N";
	}
	
	public Boolean getEsArchivoBoolean() {
		if(esArchivo != null && esArchivo.equals("Y")){
			return true;
		}
		else return false;
	}
    
}
