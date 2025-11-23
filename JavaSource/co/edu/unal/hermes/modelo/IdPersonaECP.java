package co.edu.unal.hermes.modelo;

public class IdPersonaECP implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3719677344272403140L;
	private String tipoDocumento;
	private String documento;
	private Long idOferta;

	public IdPersonaECP(){
		
	}
	
	public IdPersonaECP(String pDocumento, String pTipoDocumento, Long idOferta){
		this.documento=pDocumento;
		this.tipoDocumento=pTipoDocumento;
		this.idOferta = idOferta;
	}
	
	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
	public Long getIdOferta() {
		return idOferta;
	}

	public void setIdOferta(Long idOferta) {
		this.idOferta = idOferta;
	}
	
	public boolean equals(Object obj){
	    if(obj instanceof IdPersona){
	        IdPersonaECP idPersona = (IdPersonaECP)obj;
	        if(idPersona.getDocumento().equals(documento) && idPersona.getTipoDocumento().equals(tipoDocumento) && idPersona.getIdOferta() == idOferta){
	            return true;
	        }
	    }
		return false;
	}
	
	public int hashCode(){
		return 1;
	}
}
