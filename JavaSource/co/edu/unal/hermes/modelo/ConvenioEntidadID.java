package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class ConvenioEntidadID  implements Serializable {
	
	public int idEntidad;
	public int idConvenio;
	
	public int getIdEntidad() {
		return idEntidad;
	}
	public void setIdEntidad(int idEntidad) {
		this.idEntidad = idEntidad;
	}
	public int getIdConvenio() {
		return idConvenio;
	}
	public void setIdConvenio(int idConvenio) {
		this.idConvenio = idConvenio;
	}

}
