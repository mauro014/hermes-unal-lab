package co.edu.unal.hermes.modelo;

/**
 * 
 * @author Ing. Wilver Alexander Martínez Martínez - wam² - UN.
 */
public class SolicitudInforme {

	// llave primaria
	private Long id;
	private Long solicitudID;
	private Long informeID;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSolicitudID() {
		return solicitudID;
	}

	public void setSolicitudID(Long solicitudID) {
		this.solicitudID = solicitudID;
	}

	public Long getInformeID() {
		return informeID;
	}

	public void setInformeID(Long informeID) {
		this.informeID = informeID;
	}

}
