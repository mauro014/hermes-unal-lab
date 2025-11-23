package co.edu.unal.hermes.modelo.seguimiento;

import java.io.Serializable;
import java.util.Date;

import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.TipoInvestigador;

/**
 * The Class SolicitudInvestigador.
 * @author: Mauricio Amaya Rios date: 27-11-2015
 */
public class SolicitudInvestigador implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7645771893131276707L;

	/** The id. */
	private Long id;

	/** The investigador. */
	private Investigador investigador;

	/** The solicitud. */
	private Solicitud solicitud;

	/** The tipo. */
	private TipoInvestigador tipo;

	/** The dedicacion horas semana. */
	private double dedicacionHorasSemana;

	/** The numero meses. */
	private Short numeroMeses;

	/** The funcion. */
	private String funcion;
	
	private Long valorPagar;
	private Long valorDedicacionOriginal; // Se tiene en cuenta cuando se va a eliminar un integrante
	private long mesesFaltantes;
	private long valorDevengado;
	

	/**
	 * The tipo solicitud. A: Agregar integrante, R: Retirar integrante, C: Cambiar integrante, aplica para investigador principal.
	 */
	public static final String AGREGAR = "A";
	public static final String RETIRAR = "R";
	public static final String ELIMINAR_DIRECTOR = "E";
	public static final String CAMBIAR_DIRECTOR_A_COINVESTIGADOR = "C";
	public static final String ELIMINAR_INTEGRANTE = "I";
	private String tipoSolicitud;

	/**
	 * The estado. A:Aprobado, P: Procesado, cambios aplicados en el proyecto.
	 * Si esta en null no se ha realizado tramite.
	 */
	public static final String NO_TRAMITADO = "N";
	public static final String APROBADO = "A";
	public static final String PROCESADO = "P";
	public static final String BORRADO = "B";
	private String estado;

	/** The fecha. 
	 * Fecha en la que se aplican los cambios al proyecto*/
	private Date fechaProcesamiento;

	/** The fecha. 
	 * Fecha en la que se aplican los cambios al proyecto*/
	private Date fechaBorrado;

	public static final String ELIMINAR = "E";
	public static final String AGREGAR_COINVESTIGADOR = "A";
	private String accionInvestigadorAnterior;
	
	/**
	 * Este campo no se guarda en base de datos.
	 */
	private boolean aplicar;
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the investigador.
	 *
	 * @return the investigador
	 */
	public Investigador getInvestigador() {
		return investigador;
	}

	/**
	 * Sets the investigador.
	 *
	 * @param investigador
	 */
	public void setInvestigador(Investigador investigador) {
		this.investigador = investigador;
	}

	/**
	 * Gets the solicitud.
	 *
	 * @return the solicitud
	 */
	public Solicitud getSolicitud() {
		return solicitud;
	}

	/**
	 * Sets the solicitud.
	 *
	 * @param solicitud
	 */
	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	/**
	 * Gets the tipo.
	 *
	 * @return the tipo
	 */
	public TipoInvestigador getTipo() {
		return tipo;
	}

	/**
	 * Sets the tipo.
	 *
	 * @param tipo
	 */
	public void setTipo(TipoInvestigador tipo) {
		this.tipo = tipo;
	}

	/**
	 * Gets the dedicacion horas semana.
	 *
	 * @return the dedicacionHorasSemana
	 */
	public double getDedicacionHorasSemana() {
		return dedicacionHorasSemana;
	}

	/**
	 * Sets the dedicacion horas semana.
	 *
	 * @param dedicacionHorasSemana
	 */
	public void setDedicacionHorasSemana(double dedicacionHorasSemana) {
		this.dedicacionHorasSemana = dedicacionHorasSemana;
	}

	/**
	 * Gets the numero meses.
	 *
	 * @return the numeroMeses
	 */
	public Short getNumeroMeses() {
		return numeroMeses;
	}

	/**
	 * Sets the numero meses.
	 *
	 * @param numeroMeses
	 */
	public void setNumeroMeses(Short numeroMeses) {
		this.numeroMeses = numeroMeses;
	}

	/**
	 * Gets the funcion.
	 *
	 * @return the funcion
	 */
	public String getFuncion() {
		return funcion;
	}

	/**
	 * Sets the funcion.
	 *
	 * @param funcion
	 */
	public void setFuncion(String funcion) {
		this.funcion = funcion;
	}

	/**
	 * Gets the tipo solicitud.
	 *
	 * @return the tipoSolicitud
	 */
	public String getTipoSolicitud() {
		return tipoSolicitud;
	}

	/**
	 * Sets the tipo solicitud.
	 *
	 * @param tipoSolicitud
	 */
	public void setTipoSolicitud(String tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}

	/**
	 * Gets the estado.
	 *
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * Sets the estado.
	 *
	 * @param estado
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the fechaProcesamiento
	 */
	public Date getFechaProcesamiento() {
		return fechaProcesamiento;
	}

	/**
	 * @param fechaProcesamiento the fechaProcesamiento to set
	 */
	public void setFechaProcesamiento(Date fechaProcesamiento) {
		this.fechaProcesamiento = fechaProcesamiento;
	}

	/**
	 * @return the fechaBorrado
	 */
	public Date getFechaBorrado() {
		return fechaBorrado;
	}

	/**
	 * @param fechaBorrado the fechaBorrado to set
	 */
	public void setFechaBorrado(Date fechaBorrado) {
		this.fechaBorrado = fechaBorrado;
	}

	/**
	 * @return the accionInvestigadorAnterior
	 */
	public String getAccionInvestigadorAnterior() {
		return accionInvestigadorAnterior;
	}

	/**
	 * @param accionInvestigadorAnterior the accionInvestigadorAnterior to set
	 */
	public void setAccionInvestigadorAnterior(String accionInvestigadorAnterior) {
		this.accionInvestigadorAnterior = accionInvestigadorAnterior;
	}

	/**
	 * @return the aplicar
	 */
	public boolean isAplicar() {
		return aplicar;
	}

	/**
	 * @param aplicar the aplicar to set
	 */
	public void setAplicar(boolean aplicar) {
		this.aplicar = aplicar;
	}

	public Long getValorPagar() {
		return valorPagar;
	}

	public void setValorPagar(Long valorPagar) {
		this.valorPagar = valorPagar;
	}

	public Long getValorDedicacionOriginal() {
		return valorDedicacionOriginal;
	}

	public void setValorDedicacionOriginal(Long valorDedicacionOriginal) {
		this.valorDedicacionOriginal = valorDedicacionOriginal;
	}

	public long getValorDevengado() {
		return valorDevengado;
	}

	public void setValorDevengado(long valorDevengado) {
		this.valorDevengado = valorDevengado;
	}

	public long getMesesFaltantes() {
		return mesesFaltantes;
	}

	public void setMesesFaltantes(long mesesFaltantes) {
		this.mesesFaltantes = mesesFaltantes;
	}

}
