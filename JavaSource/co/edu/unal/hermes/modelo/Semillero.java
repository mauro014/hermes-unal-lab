package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Semillero implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nombre;
	private String presentacion;
	private Date fechaRegistro;
	private Date fechaCreacion;
	private String email;
	private Boolean interfacultades;
	private Boolean intersedes;
	private String pertinencia;
	private String objetivoGeneral;
	private String justificacion;
	private String enfoque;
	private Set<SemilleroIntegrante> integrantes;
	private Set<SemilleroSede> sedes;
	private Set<SemilleroFacultad> facultades;
	private Set<SemilleroObjetivo> objetivosEspecificos;
	private Set<SemilleroGrupo> grupos;
	private Set<SemilleroLaboratorio> laboratorios;
	private String areaOCDEPrincipal;
	private String subAreaOCDEPrincipal;
	private Set<SemilleroAreaOCDE> areasOCDESecundarias;
	private PosibleAgendaGrupo agendaPrincipal;
	private Set<SemilleroAgenda> agendasSecundarias;
	private String objetivoDesarrolloSosteniblePrincipal;
	private String objetivoDesarrolloSostenibleSecundario;
	private Set<SemilleroLinea> lineasInvestigacion;
	private String metodologia;
	private Set<SemilleroResultado> resultados;
	private Set<SemilleroArchivo> archivos;
	private Integer fase;
	private Set<SemilleroHistoricoEstado> historicoEstados;
	private Set<SemilleroSolicitud> solicitudes;
	private String documentoCoordinador;
	private String tipoDocumentoCoordinador;
	private Investigador coordinador;
	private Set<SemilleroInforme> informes;
	private Set<SemilleroProyecto> proyectos;
	private Boolean activoSinInforme;
	private Set<SemilleroHistoricoCambios> historicoCambios;
	private String documentoAsesor;
	private String tipoDocAsesor;
	private Date fechaCambioCoordinador;

	public Semillero() {
		integrantes = new HashSet<SemilleroIntegrante>();
		sedes = new HashSet<SemilleroSede>();
		facultades = new HashSet<SemilleroFacultad>();
		grupos = new HashSet<SemilleroGrupo>();
		objetivosEspecificos = new HashSet<SemilleroObjetivo>();
		laboratorios = new HashSet<SemilleroLaboratorio>();
		areasOCDESecundarias = new HashSet<SemilleroAreaOCDE>();
		agendasSecundarias = new HashSet<SemilleroAgenda>();
		lineasInvestigacion = new HashSet<SemilleroLinea>();
		resultados = new HashSet<SemilleroResultado>();
		archivos = new HashSet<SemilleroArchivo>();
		historicoEstados = new HashSet<SemilleroHistoricoEstado>();
		solicitudes = new HashSet<SemilleroSolicitud>();
		informes = new HashSet<SemilleroInforme>();
		proyectos = new HashSet<SemilleroProyecto>();
		activoSinInforme = false;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPresentacion() {
		return presentacion;
	}

	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIntersedes() {
		return intersedes;
	}

	public void setIntersedes(Boolean intersedes) {
		this.intersedes = intersedes;
	}

	public Boolean getInterfacultades() {
		return interfacultades;
	}

	public void setInterfacultades(Boolean interfacultades) {
		this.interfacultades = interfacultades;
	}

	public String getPertinencia() {
		return pertinencia;
	}

	public void setPertinencia(String perspectiva) {
		this.pertinencia = perspectiva;
	}

	public String getObjetivoGeneral() {
		return objetivoGeneral;
	}

	public void setObjetivoGeneral(String objetivoGeneral) {
		this.objetivoGeneral = objetivoGeneral;
	}

	public String getJustificacion() {
		return justificacion;
	}

	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	public String getEnfoque() {
		return enfoque;
	}

	public void setEnfoque(String enfoque) {
		this.enfoque = enfoque;
	}

	public Investigador getLider() {
		Iterator<SemilleroIntegrante> it = this.getIntegrantes().iterator();
		while (it.hasNext()) {
			SemilleroIntegrante invG = (SemilleroIntegrante) it.next();
			if (invG.esLider()) {
				return invG.getIntegrante();
			}
		}
		return null;
	}
	
	public Investigador getEstudianteLider() {
		Iterator<SemilleroIntegrante> it = this.getIntegrantes().iterator();
		while (it.hasNext()) {
			SemilleroIntegrante invG = (SemilleroIntegrante) it.next();
			if (invG.esEstudianteLider()) {
				return invG.getIntegrante();
			}
		}
		return null;
	}

	public Set<SemilleroIntegrante> getIntegrantes() {
		return integrantes;
	}

	public void setIntegrantes(Set<SemilleroIntegrante> integrantes) {
		this.integrantes = integrantes;
	}

	public ArrayList<SemilleroIntegrante> getListaIntegrantes() {
		ArrayList<SemilleroIntegrante> retVal = new ArrayList<SemilleroIntegrante>();
		for (SemilleroIntegrante sf : getIntegrantes()) {
			retVal.add(sf);
		}
		return retVal;
	}

	public Set<SemilleroSede> getSedes() {
		return sedes;
	}

	public void setSedes(Set<SemilleroSede> sedes) {
		this.sedes = sedes;
	}

	public Set<SemilleroFacultad> getFacultades() {
		return facultades;
	}

	public void setFacultades(Set<SemilleroFacultad> facultades) {
		this.facultades = facultades;
	}

	public Set<SemilleroObjetivo> getObjetivosEspecificos() {
		return objetivosEspecificos;
	}

	public void setObjetivosEspecificos(Set<SemilleroObjetivo> objetivosEspecificos) {
		this.objetivosEspecificos = objetivosEspecificos;
	}

	public Set<SemilleroGrupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(Set<SemilleroGrupo> grupos) {
		this.grupos = grupos;
	}

	public Set<SemilleroLaboratorio> getLaboratorios() {
		return laboratorios;
	}

	public void setLaboratorios(Set<SemilleroLaboratorio> laboratorios) {
		this.laboratorios = laboratorios;
	}

	public ArrayList<SemilleroFacultad> getListaFacultades() {
		ArrayList<SemilleroFacultad> retVal = new ArrayList<SemilleroFacultad>();
		for (SemilleroFacultad sf : getFacultades()) {
			retVal.add(sf);
		}
		return retVal;
	}

	public ArrayList<SemilleroSede> getListaSedes() {
		ArrayList<SemilleroSede> retVal = new ArrayList<SemilleroSede>();
		for (SemilleroSede ss : getSedes()) {
			retVal.add(ss);
		}
		return retVal;
	}

	public ArrayList<SemilleroGrupo> getListaGrupos() {
		ArrayList<SemilleroGrupo> retVal = new ArrayList<SemilleroGrupo>();
		for (SemilleroGrupo sg : getGrupos()) {
			retVal.add(sg);
		}
		return retVal;
	}

	public ArrayList<SemilleroLaboratorio> getListaLaboratorios() {
		ArrayList<SemilleroLaboratorio> retVal = new ArrayList<SemilleroLaboratorio>();
		for (SemilleroLaboratorio sl : getLaboratorios()) {
			retVal.add(sl);
		}
		return retVal;
	}

	public String getAreaOCDEPrincipal() {
		return areaOCDEPrincipal;
	}

	public void setAreaOCDEPrincipal(String areaOCDEPrincipal) {
		this.areaOCDEPrincipal = areaOCDEPrincipal;
	}

	public String getSubAreaOCDEPrincipal() {
		return subAreaOCDEPrincipal;
	}

	public void setSubAreaOCDEPrincipal(String subAreaOCDEPrincipal) {
		this.subAreaOCDEPrincipal = subAreaOCDEPrincipal;
	}

	public Set<SemilleroAreaOCDE> getAreasOCDESecundarias() {
		return areasOCDESecundarias;
	}

	public void setAreasOCDESecundarias(Set<SemilleroAreaOCDE> areasOCDESecundarias) {
		this.areasOCDESecundarias = areasOCDESecundarias;
	}

	public ArrayList<SemilleroAreaOCDE> getListaAreasOCDESecundarias() {
		ArrayList<SemilleroAreaOCDE> retVal = new ArrayList<SemilleroAreaOCDE>();
		for (SemilleroAreaOCDE sl : getAreasOCDESecundarias()) {
			retVal.add(sl);
		}
		return retVal;
	}

	public PosibleAgendaGrupo getAgendaPrincipal() {
		return agendaPrincipal;
	}

	public void setAgendaPrincipal(PosibleAgendaGrupo agendaPrincipal) {
		this.agendaPrincipal = agendaPrincipal;
	}

	public Set<SemilleroAgenda> getAgendasSecundarias() {
		return agendasSecundarias;
	}

	public void setAgendasSecundarias(Set<SemilleroAgenda> agendasSecundarias) {
		this.agendasSecundarias = agendasSecundarias;
	}

	public ArrayList<SemilleroAgenda> getListaAgendasSecundarias() {
		ArrayList<SemilleroAgenda> retVal = new ArrayList<SemilleroAgenda>();
		for (SemilleroAgenda sa : getAgendasSecundarias()) {
			retVal.add(sa);
		}
		return retVal;
	}

	public String getObjetivoDesarrolloSosteniblePrincipal() {
		return objetivoDesarrolloSosteniblePrincipal;
	}

	public void setObjetivoDesarrolloSosteniblePrincipal(String objetivoDesarrolloSosteniblePrincipal) {
		this.objetivoDesarrolloSosteniblePrincipal = objetivoDesarrolloSosteniblePrincipal;
	}

	public String getObjetivoDesarrolloSostenibleSecundario() {
		return objetivoDesarrolloSostenibleSecundario;
	}

	public void setObjetivoDesarrolloSostenibleSecundario(String objetivoDesarrolloSostenibleSecundario) {
		this.objetivoDesarrolloSostenibleSecundario = objetivoDesarrolloSostenibleSecundario;
	}

	public Set<SemilleroLinea> getLineasInvestigacion() {
		return lineasInvestigacion;
	}

	public void setLineasInvestigacion(Set<SemilleroLinea> lineasInvestigacion) {
		this.lineasInvestigacion = lineasInvestigacion;
	}

	public ArrayList<SemilleroLinea> getListaLineasInvestigacion() {
		ArrayList<SemilleroLinea> retVal = new ArrayList<SemilleroLinea>();
		for (SemilleroLinea sli : getLineasInvestigacion()) {
			retVal.add(sli);
		}
		return retVal;
	}

	public String getMetodologia() {
		return metodologia;
	}

	public void setMetodologia(String metodologia) {
		this.metodologia = metodologia;
	}

	public Set<SemilleroResultado> getResultados() {
		return resultados;
	}

	public void setResultados(Set<SemilleroResultado> resultados) {
		this.resultados = resultados;
	}

	public ArrayList<SemilleroResultado> getListaResultados() {
		ArrayList<SemilleroResultado> retVal = new ArrayList<SemilleroResultado>();
		for (SemilleroResultado sr : getResultados()) {
			retVal.add(sr);
		}
		return retVal;
	}

	public Set<SemilleroArchivo> getArchivos() {
		return archivos;
	}

	public void setArchivos(Set<SemilleroArchivo> archivos) {
		this.archivos = archivos;
	}

	public ArrayList<SemilleroArchivo> getListaArchivos() {
		ArrayList<SemilleroArchivo> retVal = new ArrayList<SemilleroArchivo>();
		for (SemilleroArchivo sr : getArchivos()) {
			retVal.add(sr);
		}
		return retVal;
	}

	public Integer getFase() {
		return fase == null ? 0 : fase;
	}

	public void setFase(Integer fase) {
		this.fase = fase;
	}

	public Set<SemilleroHistoricoEstado> getHistoricoEstados() {
		return historicoEstados;
	}

	public void setHistoricoEstados(Set<SemilleroHistoricoEstado> historicoEstados) {
		this.historicoEstados = historicoEstados;
	}

	public ArrayList<SemilleroHistoricoEstado> getListaHistoricoEstados() {
		ArrayList<SemilleroHistoricoEstado> retVal = new ArrayList<SemilleroHistoricoEstado>();
		for (SemilleroHistoricoEstado she : getHistoricoEstados()) {
			retVal.add(she);
		}
		return retVal;
	}

	public SemilleroEstado getEstadoActual() {
		SemilleroEstado se = new SemilleroEstado();
		Date fechaEstado = new Date();
		Integer idReciente = null;
		for (SemilleroHistoricoEstado she : getHistoricoEstados()) {
			if (idReciente == null) {
				idReciente = she.getId();
				se = she.getEstado();
				fechaEstado = she.getFecha();
			} else if (she.getId() > idReciente) {
				idReciente = she.getId();
				se = she.getEstado();
				fechaEstado = she.getFecha();
			}
		}
		if (se.getId()!=null && se.getId().equals(5)) {
			long diffInMillies = Math.abs(fechaEstado.getTime() - (new Date()).getTime());
			if (diffInMillies / (1000 * 60 * 60 * 24) >= 15 && getListaInformes().isEmpty()) {
				activoSinInforme = true;
			}
		}
		return se;
	}

	public Set<SemilleroSolicitud> getSolicitudes() {
		return solicitudes;
	}

	public void setSolicitudes(Set<SemilleroSolicitud> solicitudes) {
		this.solicitudes = solicitudes;
	}

	public ArrayList<SemilleroSolicitud> getListaSolicitudes() {
		ArrayList<SemilleroSolicitud> retVal = new ArrayList<SemilleroSolicitud>();
		for (SemilleroSolicitud ss : getSolicitudes()) {
			retVal.add(ss);
		}
		return retVal;
	}

	public ArrayList<SemilleroSolicitud> getListaSolicitudesTramitadas() {
		ArrayList<SemilleroSolicitud> retVal = new ArrayList<SemilleroSolicitud>();
		for (SemilleroSolicitud ss : getListaSolicitudes()) {
			if (ss.getTipo().getId().equals(1) && !ss.getRespuestaUAB().getId().equals(1)) {
				retVal.add(ss);
			}
		}
		return retVal;
	}

	public String getDocumentoCoordinador() {
		return documentoCoordinador;
	}

	public void setDocumentoCoordinador(String documentoCoordinador) {
		this.documentoCoordinador = documentoCoordinador;
	}

	public String getTipoDocumentoCoordinador() {
		return tipoDocumentoCoordinador;
	}

	public void setTipoDocumentoCoordinador(String tipoDocumentoCoordinador) {
		this.tipoDocumentoCoordinador = tipoDocumentoCoordinador;
	}

	public Date getFechaActivacion() {
		for (SemilleroSolicitud sSol : getListaSolicitudes()) {
			if (sSol.getFechaRespuestaVifDi() != null && sSol.getRespuestaVifDi() != null
					&& sSol.getRespuestaVifDi().getId().equals(7)) {
				return sSol.getFechaRespuestaVifDi();
			}
		}
		return null;
	}

	public Investigador getCoordinador() {
		return coordinador;
	}

	public void setCoordinador(Investigador coordinador) {
		this.coordinador = coordinador;
	}

	public Set<SemilleroInforme> getInformes() {
		return informes;
	}

	public void setInformes(Set<SemilleroInforme> informes) {
		this.informes = informes;
	}

	public ArrayList<SemilleroInforme> getListaInformes() {
		ArrayList<SemilleroInforme> retVal = getListaInformesCompleta();
		
		Date fechaCompromiso = null;
		Integer positionNextInforme = null;
		
		for (int i=0; i<retVal.size();i++) {
			retVal.get(i).setInformeSiguienteRegistro(false);
			SemilleroInforme ss = retVal.get(i);
			
			if(ss.isEsInformeEditable()) {
				if(fechaCompromiso == null) {
					fechaCompromiso = ss.getFechaCompromiso();
				}
				if(ss.getFechaCompromiso().before(fechaCompromiso) || fechaCompromiso.equals(ss.getFechaCompromiso()) ) {
					positionNextInforme = i;
				}
			}			
		}
		if(positionNextInforme !=null) {
			retVal.get(positionNextInforme).setInformeSiguienteRegistro(true);
		}

		return retVal;
	}
	
	public ArrayList<SemilleroInforme> getListaInformesCompleta() {
		ArrayList<SemilleroInforme> retVal = new ArrayList<SemilleroInforme>();

		for (SemilleroInforme ss : getInformes()) {
			retVal.add(ss);
			
		}
		Collections.sort(retVal);
		return retVal;
	}

	public Set<SemilleroProyecto> getProyectos() {
		return proyectos;
	}

	public void setProyectos(Set<SemilleroProyecto> proyectos) {
		this.proyectos = proyectos;
	}

	public ArrayList<SemilleroProyecto> getListaProyectos() {
		ArrayList<SemilleroProyecto> retVal = new ArrayList<SemilleroProyecto>();
		for (SemilleroProyecto ss : getProyectos()) {
			retVal.add(ss);
		}
		return retVal;
	}

	public String getUsuarioLider() {
		int arroba = getLider().getEmail().indexOf("@");
		if (arroba != -1) {
			return getLider().getEmail().substring(0, arroba);
		} else {
			return getLider().getEmail();
		}
	}

	public Boolean getActivoSinInforme() {
		return activoSinInforme;
	}

	public void setActivoSinInforme(Boolean activoSinInforme) {
		this.activoSinInforme = activoSinInforme;
	}

	public Set<SemilleroHistoricoCambios> getHistoricoCambios() {
		return historicoCambios;
	}

	public void setHistoricoCambios(Set<SemilleroHistoricoCambios> historicoCambios) {
		this.historicoCambios = historicoCambios;
	}
	
	public ArrayList<SemilleroHistoricoCambios> getListaHistoricoCambios() {
		ArrayList<SemilleroHistoricoCambios> retVal = new ArrayList<SemilleroHistoricoCambios>();
		for (SemilleroHistoricoCambios ss : getHistoricoCambios()) {
			retVal.add(ss);
		}
		return retVal;
	}

	public String getDocumentoAsesor() {
		return documentoAsesor;
	}

	public void setDocumentoAsesor(String documentoAsesor) {
		this.documentoAsesor = documentoAsesor;
	}

	public String getTipoDocAsesor() {
		return tipoDocAsesor;
	}

	public void setTipoDocAsesor(String tipoDocAsesor) {
		this.tipoDocAsesor = tipoDocAsesor;
	}

	public Date getFechaCambioCoordinador() {
		return fechaCambioCoordinador;
	}

	public void setFechaCambioCoordinador(Date fechaCambioCoordinador) {
		this.fechaCambioCoordinador = fechaCambioCoordinador;
	}
}
