package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SemilleroInforme implements Serializable, Comparable<SemilleroInforme> {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Semillero semillero;
	private Date fechaCompromiso;
	private Integer numeroNotificaciones;
	private Date fechaUltimaNotificacion;
	private Boolean cumple;
	private Date fechaEntrega;
	private Date fechaDesde;
	private Date fechaHasta;
	private String proyectos;
	private Set<SemilleroHistoricoInforme> historico;
	private Set<SemilleroInformeActividad> actividades;
	private Set<SemilleroInformeResultado> resultados;
	private Set<SemilleroInformeArchivo> archivos;
	private SimpleDateFormat format;
	private String guardadoParcial;
	private String observaciones;
	private SemilleroSolicitudRespuesta respuesta;
	private Date fechaRespuesta;
	private boolean informeSiguienteRegistro;

	public SemilleroInforme() {
		format = new SimpleDateFormat("yyyy-MM-dd");
		historico = new HashSet<SemilleroHistoricoInforme>();
		actividades = new HashSet<SemilleroInformeActividad>();
		resultados = new HashSet<SemilleroInformeResultado>();
		archivos = new HashSet<SemilleroInformeArchivo>();
		respuesta=new SemilleroSolicitudRespuesta();
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Semillero getSemillero() {
		return semillero;
	}

	public void setSemillero(Semillero semillero) {
		this.semillero = semillero;
	}

	public Date getFechaCompromiso() {
		return fechaCompromiso;
	}

	public void setFechaCompromiso(Date fechaCompromiso) {
		this.fechaCompromiso = fechaCompromiso;
	}

	public String getCompromiso() {
		return (getFechaCompromiso() != null) ? format.format(getFechaCompromiso()) : "";
	}

	public Integer getNumeroNotificaciones() {
		return numeroNotificaciones;
	}

	public void setNumeroNotificaciones(Integer numNotificaciones) {
		this.numeroNotificaciones = numNotificaciones;
	}

	public Date getFechaUltimaNotificacion() {
		return fechaUltimaNotificacion;
	}

	public void setFechaUltimaNotificacion(Date fechaUltimaNotificacion) {
		this.fechaUltimaNotificacion = fechaUltimaNotificacion;
	}

	public String getUltimaNotificacion() {
		return (getFechaUltimaNotificacion() != null) ? format.format(getFechaUltimaNotificacion()) : "";
	}

	public Boolean getCumple() {
		return cumple;
	}

	public void setCumple(Boolean cumple) {
		this.cumple = cumple;
	}

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public String getEntrega() {
		return (getFechaEntrega() != null) ? format.format(getFechaEntrega()) : "";
	}

	public Set<SemilleroHistoricoInforme> getHistorico() {
		return historico;
	}

	public void setHistorico(Set<SemilleroHistoricoInforme> historico) {
		this.historico = historico;
	}

	public ArrayList<SemilleroHistoricoInforme> getListaHistorico() {
		ArrayList<SemilleroHistoricoInforme> retVal = new ArrayList<SemilleroHistoricoInforme>();
		for (SemilleroHistoricoInforme shi : getHistorico()) {
			retVal.add(shi);
		}
		Collections.sort(retVal);
		return retVal;
	}

	public Set<SemilleroInformeActividad> getActividades() {
		return actividades;
	}

	public void setActividades(Set<SemilleroInformeActividad> actividades) {
		this.actividades = actividades;
	}

	public ArrayList<SemilleroInformeActividad> getListaActividades() {
		ArrayList<SemilleroInformeActividad> retVal = new ArrayList<SemilleroInformeActividad>();
		for (SemilleroInformeActividad sia : getActividades()) {
			retVal.add(sia);
		}
		return retVal;
	}

	public Set<SemilleroInformeResultado> getResultados() {
		return resultados;
	}

	public void setResultados(Set<SemilleroInformeResultado> resultados) {
		this.resultados = resultados;
	}

	public ArrayList<SemilleroInformeResultado> getListaResultados() {
		ArrayList<SemilleroInformeResultado> retVal = new ArrayList<SemilleroInformeResultado>();
		for (SemilleroInformeResultado sia : getResultados()) {
			retVal.add(sia);
		}
		return retVal;
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

	public String getProyectos() {
		return proyectos;
	}

	public void setProyectos(String proyectos) {
		this.proyectos = proyectos;
	}

	public Set<SemilleroInformeArchivo> getArchivos() {
		return archivos;
	}

	public void setArchivos(Set<SemilleroInformeArchivo> archivos) {
		this.archivos = archivos;
	}

	public ArrayList<SemilleroInformeArchivo> getListaArchivos() {
		ArrayList<SemilleroInformeArchivo> retVal = new ArrayList<SemilleroInformeArchivo>();
		for (SemilleroInformeArchivo sia : getArchivos()) {
			retVal.add(sia);
		}
		return retVal;
	}

	public String getGuardadoParcial() {
		return guardadoParcial;
	}

	public void setGuardadoParcial(String guardadoParcial) {
		this.guardadoParcial = guardadoParcial;
	}

	public SimpleDateFormat getFormat() {
		return format;
	}

	public void setFormat(SimpleDateFormat format) {
		this.format = format;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public SemilleroSolicitudRespuesta getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(SemilleroSolicitudRespuesta respuesta) {
		this.respuesta = respuesta;
	}

	public Date getFechaRespuesta() {
		return fechaRespuesta;
	}

	public void setFechaRespuesta(Date fechaRespuesta) {
		this.fechaRespuesta = fechaRespuesta;
	}
	
	public String getFechaAprobacion() {
		return (getFechaRespuesta() != null) ? format.format(getFechaRespuesta()) : "";
	}

	@Override
	public int compareTo(SemilleroInforme arg0) {
		if (arg0.getFechaCompromiso() == null) {
			return -1;
		}
		if (this.getFechaCompromiso() == null) {
			return 1;
		}
		if (this.getFechaCompromiso().before(arg0.getFechaCompromiso())) {
			return 1;
		}
		if (this.getFechaCompromiso().after(arg0.getFechaCompromiso())) {
			return -1;
		}
		return 0;
	}
	
	public boolean isEsInformeEditable() {
		if(this.guardadoParcial == null ||"S".equals(this.guardadoParcial)) {
			return true;
		}
		return false;
	}

	public boolean isInformeSiguienteRegistro() {
		return informeSiguienteRegistro;
	}

	public void setInformeSiguienteRegistro(boolean informeSiguienteRegistro) {
		this.informeSiguienteRegistro = informeSiguienteRegistro;
	}
}