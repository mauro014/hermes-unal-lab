/*
 * Created on 28-feb-2023
 */
package co.edu.unal.hermes.modelo;

/**
 * Maneja el impacto del proyecto
 */
public class ProyectoInformeImpacto {

	private Long id;
	private ProyectoInforme informe;
	private Long ods;
	private String nombreOds; // variable no mapeada, usada para la vista
	private String tipo;
	private String otroTipo;
	private String medicion;
	private String detalleImpacto;
	private String comentariosCoordinador;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProyectoInforme getInforme() {
		return informe;
	}

	public void setInforme(ProyectoInforme informe) {
		this.informe = informe;
	}

	public String getComentariosCoordinador() {
		return comentariosCoordinador;
	}

	public void setComentariosCoordinador(String comentariosCoordinador) {
		this.comentariosCoordinador = comentariosCoordinador;
	}

	public Long getOds() {
		return ods;
	}

	public void setOds(Long ods) {
		this.ods = ods;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getMedicion() {
		return medicion;
	}

	public void setMedicion(String medicion) {
		this.medicion = medicion;
	}

	public String getDetalleImpacto() {
		return detalleImpacto;
	}

	public void setDetalleImpacto(String detalleImpacto) {
		this.detalleImpacto = detalleImpacto;
	}

	public String getOtroTipo() {
		return otroTipo;
	}

	public void setOtroTipo(String otroTipo) {
		this.otroTipo = otroTipo;
	}

	public String getNombreOds() {
		return nombreOds;
	}

	public void setNombreOds(String nombreOds) {
		this.nombreOds = nombreOds;
	}

}
