package co.edu.unal.hermes.modelo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ParticipantesConvocatoriasExtension {

	public ParticipantesConvocatoriasExtension() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ParticipantesConvocatoriasExtension(Long id,
			Investigador investigador, Dependencia dependencia,
			String comentarioParticipacion,
			ConvocatoriasExtension convocatoriaDNE, String intencion,
			Date fechaRegistro) {
		super();
		this.id = id;
		this.investigador = investigador;
		this.dependencia = dependencia;
		this.comentarioParticipacion = comentarioParticipacion;
		this.convocatoriaDNE = convocatoriaDNE;
		this.intencion = intencion;
		this.fechaRegistro = fechaRegistro;
	}

	private Long id;
	private Investigador investigador;
	private Dependencia dependencia;
	private String comentarioParticipacion;
	private ConvocatoriasExtension convocatoriaDNE;
	private String intencion;
	private Date fechaRegistro;
	private String stringFechaRegistro;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Investigador getInvestigador() {
		return investigador;
	}

	public void setInvestigador(Investigador investigador) {
		this.investigador = investigador;
	}

	public String getComentarioParticipacion() {
		return comentarioParticipacion;
	}

	public void setComentarioParticipacion(String comentarioParticipacion) {
		this.comentarioParticipacion = comentarioParticipacion;
	}

	public Dependencia getDependencia() {
		return dependencia;
	}

	public void setDependencia(Dependencia dependencia) {
		this.dependencia = dependencia;
	}

	public ConvocatoriasExtension getConvocatoriaDNE() {
		return convocatoriaDNE;
	}

	public void setConvocatoriaDNE(ConvocatoriasExtension convocatoriaDNE) {
		this.convocatoriaDNE = convocatoriaDNE;
	}

	/**
	 * @return the intencion
	 */
	public String getIntencion() {
		return intencion;
	}

	/**
	 * @param intencion
	 *            the intencion to set
	 */
	public void setIntencion(String intencion) {
		this.intencion = intencion;
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
	 * @return the stringFechaRegistro
	 */
	public String getStringFechaRegistro() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        stringFechaRegistro = dateFormat.format(fechaRegistro);
		return stringFechaRegistro;
	}

	/**
	 * @param stringFechaRegistro the stringFechaRegistro to set
	 */
	public void setStringFechaRegistro(String stringFechaRegistro) {
		this.stringFechaRegistro = stringFechaRegistro;
	}

}
