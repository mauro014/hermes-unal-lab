package co.edu.unal.hermes.modelo.laboratorios;

/**
Objeto que encapsula toda la información necesaria para envio de alertas
Relaciona informacion de las actividades, equipo y coordinadores.
 * 
 * @author cazapatamar
 */
public class LaboratorioActividadEquipoInterfazAlerta {

	private String idActividad;
	private String estadoActividad;
	private String nombreTipoActividad;
	private String fechaActividad;
	private String nombreEquipo;
	private String placaEquipo;
	private String nombreLaboratorio;
	private String nombreCoordinador;
	private String emailCoordinador;
	private String emailsOtros;
	private String alertaEnviada;
	
	public LaboratorioActividadEquipoInterfazAlerta() {

	}

	public String getIdActividad() {
		return idActividad;
	}

	public void setIdActividad(String idActividad) {
		this.idActividad = idActividad;
	}

	public String getEstadoActividad() {
		return estadoActividad;
	}

	public void setEstadoActividad(String estadoActividad) {
		this.estadoActividad = estadoActividad;
	}

	public String getNombreTipoActividad() {
		return nombreTipoActividad;
	}

	public void setNombreTipoActividad(String nombreTipoActividad) {
		this.nombreTipoActividad = nombreTipoActividad;
	}

	public String getFechaActividad() {
		return fechaActividad;
	}

	public void setFechaActividad(String fechaActividad) {
		this.fechaActividad = fechaActividad;
	}

	public String getNombreEquipo() {
		return nombreEquipo;
	}

	public void setNombreEquipo(String nombreEquipo) {
		this.nombreEquipo = nombreEquipo;
	}

	public String getPlacaEquipo() {
		return placaEquipo;
	}

	public void setPlacaEquipo(String placaEquipo) {
		this.placaEquipo = placaEquipo;
	}

	public String getNombreLaboratorio() {
		return nombreLaboratorio;
	}

	public void setNombreLaboratorio(String nombreLaboratorio) {
		this.nombreLaboratorio = nombreLaboratorio;
	}

	public String getNombreCoordinador() {
		return nombreCoordinador;
	}

	public void setNombreCoordinador(String nombreCoordinador) {
		this.nombreCoordinador = nombreCoordinador;
	}

	public String getEmailCoordinador() {
		return emailCoordinador;
	}

	public void setEmailCoordinador(String emailCoordinador) {
		this.emailCoordinador = emailCoordinador;
	}

	public String getEmailsOtros() {
		return emailsOtros;
	}

	public void setEmailsOtros(String emailsOtros) {
		this.emailsOtros = emailsOtros;
	}

	public String getAlertaEnviada() {
		return alertaEnviada;
	}

	public void setAlertaEnviada(String alertaEnviada) {
		this.alertaEnviada = alertaEnviada;
	}
	
}
