package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SemilleroSolicitud implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Semillero semillero;
	private Date fechaSolicitud;
	private SemilleroSolicitudTipo tipo;
	private SemilleroSolicitudRespuesta respuestaUAB;
	private Date fechaRespuestaUAB;
	private String idResponsableUAB;
	private String tipoDocResponsableUAB;
	private String comentariosUAB;
	private Set<SemilleroSolicitudArchivo> archivos;
	private SemilleroSolicitudRespuesta respuestaVifDi;
	private Date fechaRespuestaVifDi;
	private Set<SemilleroRevisionRequisito> revisionRequisitos;
	private String idResponsableVifDi;
	private String tipoDocResponsableVifDi;
	private String comentariosVifDi;
	private String justificacion;
	private String detalle;
	private SemilleroSolicitudRespuesta respuestaCoordinador;
	private Date fechaRespuestaCoordinador;
	private String comentariosCoordinador;
	private String idResponsableCoordinador;
	private String tipoDocResponsableCoordinador;
	
	
	//Variables temporales no mapeadas para listar alerta de revisión de informe de semillero
	private String idInforme;
	private Persona coordinador; // variable no mapeada para conocer el nombre del coordinador que tramitó la solicitud

	public SemilleroSolicitud() {
		tipo = new SemilleroSolicitudTipo();
		respuestaUAB = new SemilleroSolicitudRespuesta();
		archivos = new HashSet<SemilleroSolicitudArchivo>();
		respuestaVifDi = new SemilleroSolicitudRespuesta();
		revisionRequisitos = new HashSet<SemilleroRevisionRequisito>();
		respuestaCoordinador = new SemilleroSolicitudRespuesta();
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

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public Date getFechaRespuestaUAB() {
		return fechaRespuestaUAB;
	}

	public void setFechaRespuestaUAB(Date fechaRespuesta) {
		this.fechaRespuestaUAB = fechaRespuesta;
	}

	public String getIdResponsableUAB() {
		return idResponsableUAB;
	}

	public void setIdResponsableUAB(String idResponsableUAB) {
		this.idResponsableUAB = idResponsableUAB;
	}

	public String getTipoDocResponsableUAB() {
		return tipoDocResponsableUAB;
	}

	public void setTipoDocResponsableUAB(String tipoDocResponsableUAB) {
		this.tipoDocResponsableUAB = tipoDocResponsableUAB;
	}

	public String getComentariosUAB() {
		return comentariosUAB;
	}

	public void setComentariosUAB(String comentarios) {
		this.comentariosUAB = comentarios;
	}

	public SemilleroSolicitudTipo getTipo() {
		return tipo;
	}

	public void setTipo(SemilleroSolicitudTipo tipo) {
		this.tipo = tipo;
	}

	public SemilleroSolicitudRespuesta getRespuestaUAB() {
		return respuestaUAB;
	}

	public void setRespuestaUAB(SemilleroSolicitudRespuesta respuesta) {
		this.respuestaUAB = respuesta;
	}

	public Set<SemilleroSolicitudArchivo> getArchivos() {
		return archivos;
	}

	public void setArchivos(Set<SemilleroSolicitudArchivo> archivos) {
		this.archivos = archivos;
	}

	public ArrayList<SemilleroSolicitudArchivo> getListaArchivos() {
		ArrayList<SemilleroSolicitudArchivo> retVal = new ArrayList<SemilleroSolicitudArchivo>();
		for (SemilleroSolicitudArchivo sf : getArchivos()) {
			retVal.add(sf);
		}
		return retVal;
	}

	public ArrayList<SemilleroSolicitudArchivo> getListaArchivosUAB() {
		ArrayList<SemilleroSolicitudArchivo> retVal = new ArrayList<SemilleroSolicitudArchivo>();
		for (SemilleroSolicitudArchivo sf : getListaArchivos()) {
			if (sf.getTipo().equals("D")) {
				retVal.add(sf);
			}
		}
		return retVal;
	}

	public ArrayList<SemilleroSolicitudArchivo> getListaArchivosVIF() {
		ArrayList<SemilleroSolicitudArchivo> retVal = new ArrayList<SemilleroSolicitudArchivo>();
		for (SemilleroSolicitudArchivo sf : getListaArchivos()) {
			if (sf.getTipo().equals("F")) {
				retVal.add(sf);
			}
		}
		return retVal;
	}

	public ArrayList<SemilleroSolicitudArchivo> getListaArchivosDI() {
		ArrayList<SemilleroSolicitudArchivo> retVal = new ArrayList<SemilleroSolicitudArchivo>();
		for (SemilleroSolicitudArchivo sf : getListaArchivos()) {
			if (sf.getTipo().equals("S")) {
				retVal.add(sf);
			}
		}
		return retVal;
	}

	public ArrayList<SemilleroSolicitudArchivo> getListaArchivosSolicitud() {
		ArrayList<SemilleroSolicitudArchivo> retVal = new ArrayList<SemilleroSolicitudArchivo>();
		for (SemilleroSolicitudArchivo sf : getListaArchivos()) {
			if (sf.getTipo().equals("O")) {
				retVal.add(sf);
			}
		}
		return retVal;
	}

	public SemilleroSolicitudRespuesta getRespuestaVifDi() {
		return respuestaVifDi;
	}

	public void setRespuestaVifDi(SemilleroSolicitudRespuesta respuestaVifDi) {
		this.respuestaVifDi = respuestaVifDi;
	}

	public Date getFechaRespuestaVifDi() {
		return fechaRespuestaVifDi;
	}

	public void setFechaRespuestaVifDi(Date fechaRespuestaVifDi) {
		this.fechaRespuestaVifDi = fechaRespuestaVifDi;
	}

	public Set<SemilleroRevisionRequisito> getRevisionRequisitos() {
		return revisionRequisitos;
	}

	public void setRevisionRequisitos(Set<SemilleroRevisionRequisito> revisionRequisitos) {
		this.revisionRequisitos = revisionRequisitos;
	}

	public ArrayList<SemilleroRevisionRequisito> getListaRevisionRequisitos() {
		ArrayList<SemilleroRevisionRequisito> retVal = new ArrayList<SemilleroRevisionRequisito>();
		for (SemilleroRevisionRequisito sf : getRevisionRequisitos()) {
			retVal.add(sf);
		}
		return retVal;
	}

	public String getIdResponsableVifDi() {
		return idResponsableVifDi;
	}

	public void setIdResponsableVifDi(String idResponsableVifDi) {
		this.idResponsableVifDi = idResponsableVifDi;
	}

	public String getTipoDocResponsableVifDi() {
		return tipoDocResponsableVifDi;
	}

	public void setTipoDocResponsableVifDi(String tipoDocResponsableVifDi) {
		this.tipoDocResponsableVifDi = tipoDocResponsableVifDi;
	}

	public String getComentariosVifDi() {
		return comentariosVifDi;
	}

	public void setComentariosVifDi(String comentariosVifDi) {
		this.comentariosVifDi = comentariosVifDi;
	}

	public String getJustificacion() {
		return justificacion;
	}

	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public SemilleroSolicitudRespuesta getRespuestaCoordinador() {
		return respuestaCoordinador;
	}

	public void setRespuestaCoordinador(SemilleroSolicitudRespuesta respuestaCoordinador) {
		this.respuestaCoordinador = respuestaCoordinador;
	}

	public Date getFechaRespuestaCoordinador() {
		return fechaRespuestaCoordinador;
	}

	public void setFechaRespuestaCoordinador(Date fechaRespuestaCoordinador) {
		this.fechaRespuestaCoordinador = fechaRespuestaCoordinador;
	}

	public String getComentariosCoordinador() {
		return comentariosCoordinador;
	}

	public void setComentariosCoordinador(String comentariosCoordinador) {
		this.comentariosCoordinador = comentariosCoordinador;
	}

	public String getIdResponsableCoordinador() {
		return idResponsableCoordinador;
	}

	public void setIdResponsableCoordinador(String idResponsableCoordinador) {
		this.idResponsableCoordinador = idResponsableCoordinador;
	}

	public String getTipoDocResponsableCoordinador() {
		return tipoDocResponsableCoordinador;
	}

	public void setTipoDocResponsableCoordinador(String tipoDocResponsableCoordinador) {
		this.tipoDocResponsableCoordinador = tipoDocResponsableCoordinador;
	}

	public String getIdInforme() {
		return idInforme;
	}

	public void setIdInforme(String idInforme) {
		this.idInforme = idInforme;
	}

	public Persona getCoordinador() {
		return coordinador;
	}

	public void setCoordinador(Persona coordinador) {
		this.coordinador = coordinador;
	}
}