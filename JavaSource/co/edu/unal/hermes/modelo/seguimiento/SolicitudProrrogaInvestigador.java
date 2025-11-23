package co.edu.unal.hermes.modelo.seguimiento;

import java.io.Serializable;
import java.util.Date;

import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.TipoInvestigador;

/**
 * The Class SolicitudInvestigador.
 * @author: Martha Correa date: 11-08-2023
 */
public class SolicitudProrrogaInvestigador implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8062874571423003255L;

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
	private Short numeroSemanasAdicional;

	
	private Long valorPagar;
	private Date fechaProrroga;


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
	public Short getNumeroSemanasAdicional() {
		return numeroSemanasAdicional;
	}

	/**
	 * Sets the numero meses.
	 *
	 * @param numeroMeses
	 */
	public void setNumeroSemanasAdicional(Short numeroSemanasAdicional) {
		this.numeroSemanasAdicional = numeroSemanasAdicional;
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

	public Date getFechaProrroga() {
		return fechaProrroga;
	}

	public void setFechaProrroga(Date fechaProrroga) {
		this.fechaProrroga = fechaProrroga;
	}

}
