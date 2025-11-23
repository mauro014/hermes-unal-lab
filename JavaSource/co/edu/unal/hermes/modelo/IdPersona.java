package co.edu.unal.hermes.modelo;

public class IdPersona implements java.io.Serializable{

	private static final long serialVersionUID = 1833036180834852093L;
	private String tipoDocumento;
	private String documento;
	
	public IdPersona(){
		
	}
	
	public String toString() {
        return "IdPersona{tdoId='" + tipoDocumento + "', perId=" + documento + "}";
    }
	
	public IdPersona(String pDocumento, String pTipoDocumento){
		this.documento=pDocumento;
		this.tipoDocumento=pTipoDocumento;
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
	
	public boolean equals(Object obj){
	    if(obj instanceof IdPersona){
	        IdPersona idPersona = (IdPersona)obj;
	        if(idPersona.getDocumento().equals(documento) && idPersona.getTipoDocumento().equals(tipoDocumento)){
	            return true;
	        }
	    }
		return false;
	}
	
}