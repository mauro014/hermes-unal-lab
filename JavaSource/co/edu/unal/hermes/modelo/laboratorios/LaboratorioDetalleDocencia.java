package co.edu.unal.hermes.modelo.laboratorios;

import java.util.Date;

import co.edu.unal.hermes.modelo.VAsignaturasSIA;

/**
 * @author dgbenitezc
 */
public class LaboratorioDetalleDocencia {

	private Long id;
	private Laboratorio laboratorio;
	private Float practicasSemanales;
	private Float horasSemana;
	private Date fechaRegistro;
	private VAsignaturasSIA asignatura;
	private Boolean modalidadVirtual;
	private Boolean modalidadPresencial;

	public LaboratorioDetalleDocencia() {
	}

	@Override
	public boolean equals(Object otroObjeto) {
		try {
			LaboratorioDetalleDocencia otroDetalle = (LaboratorioDetalleDocencia) otroObjeto;
			return (asignatura.getId().equals(otroDetalle.asignatura.getId()));
		} catch (Exception e) {
			System.out.println("Error comparando LaboratorioDetalleDocencia:");
			e.printStackTrace();
			return false;
		}
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
	 * @return the practicasSemanales
	 */
	public Float getPracticasSemanales() {
		return practicasSemanales;
	}

	/**
	 * @param practicasSemanales
	 *            the practicasSemanales to set
	 */
	public void setPracticasSemanales(Float practicasSemanales) {
		this.practicasSemanales = practicasSemanales;
	}

	/**
	 * @return the horasSemana
	 */
	public Float getHorasSemana() {
		return horasSemana;
	}

	/**
	 * @param horasSemana
	 *            the horasSemana to set
	 */
	public void setHorasSemana(Float horasSemana) {
		this.horasSemana = horasSemana;
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

	/**
	 * @return the asignatura
	 */
	public VAsignaturasSIA getAsignatura() {
		return asignatura;
	}

	/**
	 * @param asignatura
	 *            the asignatura to set
	 */
	public void setAsignatura(VAsignaturasSIA asignatura) {
		this.asignatura = asignatura;
	}

	public Boolean getModalidadVirtual() {
		return modalidadVirtual;
	}

	public void setModalidadVirtual(Boolean modalidadVirtual) {
		this.modalidadVirtual = modalidadVirtual;
	}

	public Boolean getModalidadPresencial() {
		return modalidadPresencial;
	}

	public void setModalidadPresencial(Boolean modalidadPresencial) {
		this.modalidadPresencial = modalidadPresencial;
	}

}
