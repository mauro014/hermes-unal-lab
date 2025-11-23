/**
} * @author dgbenitezc
 */

package co.edu.unal.hermes.modelo.laboratorios;

import java.util.Date;

import co.edu.unal.hermes.modelo.LineaInvestigacion;

public class LaboratorioDetalleLineasInvestigacion {

	private Long id;
	private Laboratorio laboratorio;
	private LineaInvestigacion lineaInvestigacion;
	private Date fechaRegistro;

	public LaboratorioDetalleLineasInvestigacion() {
	}

	@Override
	public boolean equals(Object otroObjeto) {
		LaboratorioDetalleLineasInvestigacion otroDetalle = (LaboratorioDetalleLineasInvestigacion) otroObjeto;
		return lineaInvestigacion.getId().equals(
				otroDetalle.getLineaInvestigacion().getId());
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the laboratorio
	 */
	public Laboratorio getLaboratorio() {
		return laboratorio;
	}

	/**
	 * @param laboratorio
	 *            the laboratorio to set
	 */
	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
	}

	/**
	 * @return the lineaInvestigacion
	 */
	public LineaInvestigacion getLineaInvestigacion() {
		return lineaInvestigacion;
	}

	/**
	 * @param lineaInvestigacion
	 *            the lineaInvestigacion to set
	 */
	public void setLineaInvestigacion(LineaInvestigacion lineaInvestigacion) {
		this.lineaInvestigacion = lineaInvestigacion;
	}

	/**
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @param fechaRegistro
	 *            the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

}
