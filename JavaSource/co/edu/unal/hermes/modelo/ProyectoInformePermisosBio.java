package co.edu.unal.hermes.modelo;

import java.util.Date;

/**
 * Maneja los objetivos específicos que enmarcan cada proyecto
 */
public class ProyectoInformePermisosBio {

	private Long id;
	private ProyectoInforme informe;
	private String areaProtegida;
	private String nroPermiso;
	private Date fechaDesde;
	private Date fechaHasta;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAreaProtegida() {
		return areaProtegida;
	}
	public void setAreaProtegida(String areaProtegida) {
		this.areaProtegida = areaProtegida;
	}
	public String getNroPermiso() {
		return nroPermiso;
	}
	public void setNroPermiso(String nroPermiso) {
		this.nroPermiso = nroPermiso;
	}
	public Date getFechaDesde() {
		return fechaDesde;
	}
	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}
	public Date getFechaHasta() {
		return fechaHasta;
	}
	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
	public ProyectoInforme getInforme() {
		return informe;
	}
	public void setInforme(ProyectoInforme informe) {
		this.informe = informe;
	}

}
