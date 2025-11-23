/*
 * Created on 9-dic-2007
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author Ing. Wilver Alexander Martinez Martinez -wam²
 */
public class MovilidadEstudiantesPosgrado extends MovilidadInvestigador implements Serializable {

    private static final long serialVersionUID = 5589066857302920036L;
    public static String EVENTO = "E";
    public static String TESIS = "T";
    public static String VISITANTE = "V";

    public static String LECTURA_FACULTAD = "L";
    public static String DEVUELTO = "D";
    
    public static String ESTUDIANTEPOSGRADO ="D1";
    
    public static String ESTUDIANTEPOSGRADOEVENTOS ="MOV3_IN";
    
    private Long idtipo;
    private String tipoDocumentoPersona;
    private Long idpersona;
    byte[] hojavida;
    private Long costotiquete;
    private Long monto;
    private Integer idinvestigador;
    private String resumen;
    private Proyecto proyecto;
    private String evento;
    private String titulo;
    private Pais pais;
    private String ciudad;
    private String inscripcionevento;
    private Long costoevento;
    private Date fechasalida;
    private Date fechallegada;
    private String tituloAspira;
    private String documentoVisitante;
    private TipoPonencia Ponencia;
    private String otroTipoPonencia;
    private Float papaEstudiante;
    private String movilidadConvocatoriaFacultad;
    private Long valorViaticos;
    private String valorOtrosAportes;
    private String dependenciaOtrosAportes;
    private Long proyectoFicha;
    private Long valorTotalMovilidadSolicitado;
    private String subModalidadConvocatoria;
    private String caracterEvento;
	private String medioTransporte;
	private Grupo grupo;

    private String tipoMovilidadCadena;
    private Estudiante estudianteInv;

    private String universidad;

    private Set<ArchivoMovilidadEP> archivos = new HashSet<ArchivoMovilidadEP>();
    private Set<PalabraClave> palabrasClaves = new HashSet<PalabraClave>();

    // MODIFICACIONES ING MILENA
    private List<ArchivoMovilidadEP> archivosSeguimiento;
    private List<ArchivoMovilidadEP> archivosRevisionRequisitos;
    private List<ArchivoMovilidadEP> archivosRevisionRequisitosSede;
    private String error;
    private Long valorTotalEvento;
    private String tituloTrabajoPresentado;
    private String experienciasTrabajoPresentado;
    private String descripcionRevision;
    private Dependencia dependencia;
    private String pasaporteEstudiante;
    private Set<ActividadMovilidad> actividades = new HashSet<ActividadMovilidad>();
    
    private Long montoAdicionalEjecutado;
    private Dependencia dependenciaRevisionMovilidadesEst;
    
    private String movilidadTipo;
    
    private Long materialesTalleresSocializacion; 

    public void adicionarActividad(ActividadMovilidad actividad){
		actividades.add(actividad);
	}
	
	public void borrarActividad(ActividadMovilidad actividad){
		actividades.remove(actividad);
	}
	
	public List<ActividadMovilidad> getListaActividades(){
		List<ActividadMovilidad> listaActividades = new ArrayList<ActividadMovilidad>();
		listaActividades.addAll(actividades);
		return listaActividades;
	}	
    
    public Long getIdtipo() {
        return idtipo;
    }

    public void setIdtipo(Long idtipo) {
        this.idtipo = idtipo;
    }

    public Long getIdpersona() {
        return idpersona;
    }

    public void setIdpersona(Long idpersona) {
        this.idpersona = idpersona;
    }

    public byte[] getHojavida() {
        return hojavida;
    }

    public void setHojavida(byte[] hojavida) {
        this.hojavida = hojavida;
    }

    public Long getCostotiquete() {
        return costotiquete;
    }

    public void setCostotiquete(Long costotiquete) {
        this.costotiquete = costotiquete;
    }

    public Long getMonto() {
        return monto;
    }

    public void setMonto(Long monto) {
        this.monto = monto;
    }

    public Integer getIdinvestigador() {
        return idinvestigador;
    }

    public void setIdinvestigador(Integer idinvestigador) {
        this.idinvestigador = idinvestigador;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getInscripcionevento() {
        return inscripcionevento;
    }

    public void setInscripcionevento(String inscripcionevento) {
        this.inscripcionevento = inscripcionevento;
    }

    public Long getCostoevento() {
        return costoevento;
    }

    public void setCostoevento(Long costoevento) {
        this.costoevento = costoevento;
    }
    
    public Date getFechasalida() {
        return fechasalida;
    }

    public void setFechasalida(Date fechasalida) {
        this.fechasalida = fechasalida;
    }

    public Date getFechallegada() {
        return fechallegada;
    }

    public void setFechallegada(Date fechallegada) {
        this.fechallegada = fechallegada;
    }

    public String getTituloAspira() {
        return tituloAspira;
    }

    public void setTituloAspira(String tituloAspira) {
        this.tituloAspira = tituloAspira;
    }

    public String getDocumentoVisitante() {
        return documentoVisitante;
    }

    public void setDocumentoVisitante(String documentoVisitante) {
        this.documentoVisitante = documentoVisitante;
    }

    public TipoPonencia getPonencia() {
        return Ponencia;
    }

    public void setPonencia(TipoPonencia ponencia) {
        Ponencia = ponencia;
    }

    public String getOtroTipoPonencia() {
        return otroTipoPonencia;
    }

    public void setOtroTipoPonencia(String otroTipoPonencia) {
        this.otroTipoPonencia = otroTipoPonencia;
    }

    public String getTipoDocumentoPersona() {
        return tipoDocumentoPersona;
    }

    public void setTipoDocumentoPersona(String tipoDocumentoPersona) {
        this.tipoDocumentoPersona = tipoDocumentoPersona;
    }

    public Float getPapaEstudiante() {
        return papaEstudiante;
    }

    public void setPapaEstudiante(Float papaEstudiante) {
        this.papaEstudiante = papaEstudiante;
    }

    public Estudiante getEstudianteInv() {
        return estudianteInv;
    }

    public void setEstudianteInv(Estudiante estudianteInv) {
        this.estudianteInv = estudianteInv;
    }

    public List<ArchivoMovilidadEP> getListaArchivo() {
        List<ArchivoMovilidadEP> listaArchivo = new ArrayList<ArchivoMovilidadEP>();
        listaArchivo.addAll(archivos);
        return listaArchivo;
    }    

    public Set<ArchivoMovilidadEP> getArchivos() {
        return archivos;
    }

    public void setArchivos(Set<ArchivoMovilidadEP> archivos) {
        this.archivos = archivos;
    }
    
    public void adicionarArchivo(ArchivoMovilidadEP archivo){
		archivos.add(archivo);
	}
	
	public void borrarArchivo(ArchivoMovilidadEP archivo){
		archivos.remove(archivo);
	}

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }
    
    public static String getEVENTO() {
        return EVENTO;
    }

    public static void setEVENTO(String eVENTO) {
        EVENTO = eVENTO;
    }

    public static String getTESIS() {
        return TESIS;
    }

    public static void setTESIS(String tESIS) {
        TESIS = tESIS;
    }

    public static String getVISITANTE() {
        return VISITANTE;
    }

    public static void setVISITANTE(String vISITANTE) {
        VISITANTE = vISITANTE;
    }

    public void setPalabrasClaves(Set<PalabraClave> palabrasClaves) {
        this.palabrasClaves = palabrasClaves;
    }

    public Set<PalabraClave> getPalabrasClaves() {
        return palabrasClaves;
    }

    public Long getProyectoFicha() {
        return proyectoFicha;
    }

    public void setProyectoFicha(Long proyectoFicha) {
        this.proyectoFicha = proyectoFicha;
    }

    public void setArchivosSeguimiento(List<ArchivoMovilidadEP> archivosSeguimiento) {
        this.archivosSeguimiento = archivosSeguimiento;
    }

    public List<ArchivoMovilidadEP> getArchivosSeguimiento() {
        return archivosSeguimiento;
    }

    public String getMovilidadConvocatoriaFacultad() {
        return movilidadConvocatoriaFacultad;
    }

    public void setMovilidadConvocatoriaFacultad(String movilidadConvocatoriaFacultad) {
        this.movilidadConvocatoriaFacultad = movilidadConvocatoriaFacultad;
    }

    public Long getValorViaticos() {
        return valorViaticos;
    }

    public void setValorViaticos(Long valorViaticos) {
        this.valorViaticos = valorViaticos;
    }

    public String getValorOtrosAportes() {
        return valorOtrosAportes;
    }

    public void setValorOtrosAportes(String valorOtrosAportes) {
        this.valorOtrosAportes = valorOtrosAportes;
    }

    public List<ArchivoMovilidadEP> getArchivosRevisionRequisitos() {
        archivosRevisionRequisitos = obtenerListaArchivoEspecifico(TipoArchivoMovilidad.REVISION_REQUISITOS,
                archivosRevisionRequisitos);
        return archivosRevisionRequisitos;
    }

    public List<ArchivoMovilidadEP> getArchivosRevisionRequisitosSede() {
        archivosRevisionRequisitosSede = obtenerListaArchivoEspecifico(TipoArchivoMovilidad.REVISION_REQUISITOS_SEDE,
                archivosRevisionRequisitosSede);
        return archivosRevisionRequisitosSede;
    }

    private List<ArchivoMovilidadEP> obtenerListaArchivoEspecifico(String tipo, List<ArchivoMovilidadEP> listaArchivo) {
        if (listaArchivo == null || (listaArchivo != null && listaArchivo.size() == 0)) {
            if (archivos != null && archivos.size() > 0) {
                Iterator<ArchivoMovilidadEP> i = archivos.iterator();
                while (i.hasNext()) {
                    ArchivoMovilidadEP archivoMovilidadEP = i.next();
                    if (archivoMovilidadEP.getTipoArchivo().getId().toString().equals(tipo)) {
                        listaArchivo = agregarArchivo(archivoMovilidadEP, listaArchivo);
                    }
                }
            }
        }
        return listaArchivo;
    }

    public List<ArchivoMovilidadEP> agregarArchivo(ArchivoMovilidadEP archivoMovilidadEP,
            List<ArchivoMovilidadEP> listaArchivo) {
        if (listaArchivo == null) {
            listaArchivo = new ArrayList<ArchivoMovilidadEP>();
        }
        listaArchivo.add(archivoMovilidadEP);
        return listaArchivo;
    }

    public void agregarArchivoRequisitosFacultad(ArchivoMovilidadEP archivoMovilidadEP) {
        archivosRevisionRequisitos = agregarArchivo(archivoMovilidadEP, archivosRevisionRequisitos);
    }

    public void agregarArchivoRequisitosSede(ArchivoMovilidadEP archivoMovilidadEP) {
        archivosRevisionRequisitosSede = agregarArchivo(archivoMovilidadEP, archivosRevisionRequisitosSede);
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setArchivosRevisionRequisitosSede(List<ArchivoMovilidadEP> archivosRevisionRequisitosSede) {
        this.archivosRevisionRequisitosSede = archivosRevisionRequisitosSede;
    }

    public Long getValorTotalEvento() {
        return valorTotalEvento;
    }

    public void setValorTotalEvento(Long valorTotalEvento) {
        this.valorTotalEvento = valorTotalEvento;
    }

    public String getTituloTrabajoPresentado() {
        return tituloTrabajoPresentado;
    }

    public void setTituloTrabajoPresentado(String tituloTrabajoPresentado) {
        this.tituloTrabajoPresentado = tituloTrabajoPresentado;
    }

    public String getExperienciasTrabajoPresentado() {
        return experienciasTrabajoPresentado;
    }

    public void setExperienciasTrabajoPresentado(String experienciasTrabajoPresentado) {
        this.experienciasTrabajoPresentado = experienciasTrabajoPresentado;
    }

    public String getDescripcionRevision() {
        return descripcionRevision;
    }

    public void setDescripcionRevision(String descripcionRevision) {
        this.descripcionRevision = descripcionRevision;
    }

    /**
     * @return the dependencia
     */
    public Dependencia getDependencia() {
        return dependencia;
    }

    /**
     * @param dependencia
     *            the dependencia to set
     */
    public void setDependencia(Dependencia dependencia) {
        this.dependencia = dependencia;
    }

    public String getPasaporteEstudiante() {
        return pasaporteEstudiante;
    }

    public void setPasaporteEstudiante(String pasaporteEstudiante) {
        this.pasaporteEstudiante = pasaporteEstudiante;
    }

    /**
     * @return the tipoMovilidadCadena
     */
    public String getTipoMovilidadCadena() {
        return tipoMovilidadCadena;
    }

    /**
     * @param tipoMovilidadCadena
     *            the tipoMovilidadCadena to set
     */
    public void setTipoMovilidadCadena(String tipoMovilidadCadena) {
        this.tipoMovilidadCadena = tipoMovilidadCadena;
    }

    public Set<ActividadMovilidad> getActividades() {
        return actividades;
    }

    public void setActividades(Set<ActividadMovilidad> actividades) {
        this.actividades = actividades;
    }

    public Date getFechaMinimaInicial() {
        GregorianCalendar calendar = new GregorianCalendar();
        if (getFechasolicitud() != null) {
            calendar.setTime(getFechasolicitud());
        }
        calendar.add(GregorianCalendar.DATE, 30);
        return calendar.getTime();
    }

    public Date getFechaMinimaFinal() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(getFechaMinimaInicial());
        calendar.add(GregorianCalendar.DATE, 1);
        return calendar.getTime();
    }

	public Long getValorTotalMovilidadSolicitado() {
		return valorTotalMovilidadSolicitado;
	}

	public void setValorTotalMovilidadSolicitado(Long valorTotalMovilidadSolicitado) {
		this.valorTotalMovilidadSolicitado = valorTotalMovilidadSolicitado;
	}

	public String getSubModalidadConvocatoria() {
		return subModalidadConvocatoria;
	}

	public void setSubModalidadConvocatoria(String subModalidadConvocatoria) {
		this.subModalidadConvocatoria = subModalidadConvocatoria;
	}

	/**
	 * @return the caracterEvento
	 */
	public String getCaracterEvento()
	{
		return caracterEvento;
	}

	/**
	 * @param caracterEvento the caracterEvento to set
	 */
	public void setCaracterEvento(String caracterEvento)
	{
		this.caracterEvento = caracterEvento;
	}

	/**
	 * @return the medioTransporte
	 */
	public String getMedioTransporte()
	{
		return medioTransporte;
	}

	/**
	 * @param medioTransporte the medioTransporte to set
	 */
	public void setMedioTransporte(String medioTransporte)
	{
		this.medioTransporte = medioTransporte;
	}

	/**
	 * @return the dependenciaOtrosAportes
	 */
	public String getDependenciaOtrosAportes()
	{
		return dependenciaOtrosAportes;
	}

	/**
	 * @param dependenciaOtrosAportes the dependenciaOtrosAportes to set
	 */
	public void setDependenciaOtrosAportes(String dependenciaOtrosAportes)
	{
		this.dependenciaOtrosAportes = dependenciaOtrosAportes;
	}

	/**
	 * @return the grupo
	 */
	public Grupo getGrupo()
	{
		return grupo;
	}

	/**
	 * @param grupo the grupo to set
	 */
	public void setGrupo(Grupo grupo)
	{
		this.grupo = grupo;
	}

	public Long getMontoAdicionalEjecutado() {
		return montoAdicionalEjecutado;
	}

	public void setMontoAdicionalEjecutado(Long montoAdicionalEjecutado) {
		this.montoAdicionalEjecutado = montoAdicionalEjecutado;
	}

	public Dependencia getDependenciaRevisionMovilidadesEst() {
		return dependenciaRevisionMovilidadesEst;
	}

	public void setDependenciaRevisionMovilidadesEst(Dependencia dependenciaRevisionMovilidadesEst) {
		this.dependenciaRevisionMovilidadesEst = dependenciaRevisionMovilidadesEst;
	}

	public String getMovilidadTipo() {
		return movilidadTipo;
	}

	public void setMovilidadTipo(String movilidadTipo) {
		this.movilidadTipo = movilidadTipo;
	}

	public Long getMaterialesTalleresSocializacion() {
		return materialesTalleresSocializacion;
	}

	public void setMaterialesTalleresSocializacion(Long materialesTalleresSocializacion) {
		this.materialesTalleresSocializacion = materialesTalleresSocializacion;
	}

}
