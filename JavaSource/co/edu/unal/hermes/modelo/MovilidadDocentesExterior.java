/*
 * Created on 9-dic-2007
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;

/**
 * @author Ing. Wilver Alexander Martinez Martinez -wam²
 */
public class MovilidadDocentesExterior extends MovilidadInvestigador implements Serializable{

	private static final long serialVersionUID = -1165935457034600582L;
	public static String EVENTO = "E";
	public static String TESIS = "T";
	public static String VISITANTE = "V";

	public static String LECTURA_FACULTAD = "L";
	public static String DEVUELTO = "D";

    public static String EVENTODOCENTE = "B1";

	private Long idtipo;
	private String idReg;
	private String tipoDocumentoReg;
	private String tipoDocumentoPersona;
	private Long idpersona;
	private Long idprograma;
	byte[] hojavida;
	private String plan;
	private Long costotiquete;
	private Long monto;
	private Integer numerodias;
	private Integer idinvestigador;
	private String resumen;
	private Integer idgrupo;
    private String tipoMovilidadCadena;
	private Proyecto proyecto;
	private Long idplanAccion;
	private String evento;
	private String titulo;
	private Pais pais;
	private String ciudad;
	private String inscripcionevento;
	private Long costoevento;
	private Long aportefacultad;
	private String solicituddocente;
	private String resolucionviaje;
	private Date fechasalida;
	private Date fechallegada;
	private String programa;
	private String universidad;
	private String nombreDocente;
	private String documentoVisitante;
	private String nacionalidad;
	private TipoPonencia Ponencia;
	private String otroTipoPonencia;
	private String nombreArchivo;
	private String tesis;
	private Grupo grupo;

	private String nombreVicerrectoria;
	private String nombreDireccion;
	private String nombreFacultad;
	private String nombreDepartamento;
	private String tituloAspira;

	private String infoConvocatoriaExterna;
	private String descConvocatoriaExterna;
	private String caracterEvento;
	private String medioTransporte;
	private String nombreUniversidad;

	private Set<ArchivoMovilidadDE> archivos = new HashSet<ArchivoMovilidadDE>();
	private Set<MovilidadPonencia> ponencias    = new HashSet<MovilidadPonencia>();
	private Set <PalabraClave>palabrasClaves = new HashSet<PalabraClave>();

	private Long proyectoFicha;
	private List<ArchivoMovilidadDE> archivosSeguimiento;
	private String movilidadConvocatoriaFacultad;
	private Long valorViaticos;
	private String valorOtrosAportes;
	private String dependenciaOtrosAportes;
	private String pasaporteInvestigador;
	private String subModalidadConvocatoria;
    private Long valorTotalMovilidadSolicitado;

	private Set<ActividadMovilidad> actividades = new HashSet<ActividadMovilidad>();

	Blob  alDiaVicerrectoria;
	Blob  alDiaDireccion;
	Blob  alDiaFacultad;
	Blob alDiaDepartamento;

	//MODIFICACIONES ING MILENA
	private List<ArchivoMovilidadDE> archivosRevisionRequisitos;
	 private List<ArchivoMovilidadDE> archivosRevisionRequisitosSede;
	private String error;
	private Long valorTotalEvento;
	private String tituloTrabajoPresentado;
	private String experienciasTrabajoPresentado;
	private String descripcionRevision;
	private Dependencia dependencia;
	
	private Long montoAdicionalEjecutado;
	
	private String movilidadTipo;

	private String movilidadDirigidaOtraDependencia;
	
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

	public Long getIdprograma() {
		return idprograma;
	}

	public void setIdprograma(Long idprograma) {
		this.idprograma = idprograma;
	}

	public byte[] getHojavida() {
		return hojavida;
	}

	public void setHojavida(byte[] hojavida) {
		this.hojavida = hojavida;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
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

	public Integer getNumerodias() {
		return numerodias;
	}

	public void setNumerodias(Integer numerodias) {
		this.numerodias = numerodias;
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

	public Integer getIdgrupo() {
		return idgrupo;
	}

	public void setIdgrupo(Integer idgrupo) {
		this.idgrupo = idgrupo;
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

	public void adicionarPonencias(MovilidadPonencia ponencia){
		ponencias.add(ponencia);
	}
	
	public void borrarPonencias(MovilidadPonencia ponencia){
		ponencias.remove(ponencia);
	}
	
	public void adicionarActividad(ActividadMovilidad actividad){
		actividades.add(actividad);
	}
	
	public void borrarActividad(ActividadMovilidad actividad){
		actividades.remove(actividad);
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

	public Long getAportefacultad() {
		return aportefacultad;
	}

	public void setAportefacultad(Long aportefacultad) {
		this.aportefacultad = aportefacultad;
	}

	public String getSolicituddocente() {
		return solicituddocente;
	}

	public void setSolicituddocente(String solicituddocente) {
		this.solicituddocente = solicituddocente;
	}

	public String getResolucionviaje() {
		return resolucionviaje;
	}

	public void setResolucionviaje(String resolucionviaje) {
		this.resolucionviaje = resolucionviaje;
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

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public String getUniversidad() {
		return universidad;
	}

	public void setUniversidad(String universidad) {
		this.universidad = universidad;
	}

	public String getTituloAspira() {
		return tituloAspira;
	}

	public void setTituloAspira(String tituloAspira) {
		this.tituloAspira = tituloAspira;
	}

	public String getNombreDocente() {
		return nombreDocente;
	}

	public void setNombreDocente(String nombreDocente) {
		this.nombreDocente = nombreDocente;
	}

	public String getDocumentoVisitante() {
		return documentoVisitante;
	}

	public void setDocumentoVisitante(String documentoVisitante) {
		this.documentoVisitante = documentoVisitante;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
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

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getTipoDocumentoPersona() {
		return tipoDocumentoPersona;
	}

	public void setTipoDocumentoPersona(String tipoDocumentoPersona) {
		this.tipoDocumentoPersona = tipoDocumentoPersona;
	}

	public Long getIdplanAccion() {
		return idplanAccion;
	}

	public void setIdplanAccion(Long idplanAccion) {
		this.idplanAccion = idplanAccion;
	}

	public Blob getAlDiaVicerrectoria() {
		return alDiaVicerrectoria;
	}

	public void setAlDiaVicerrectoria(Blob alDiaVicerrectoria) {
		this.alDiaVicerrectoria = alDiaVicerrectoria;
	}

	public Blob getAlDiaDireccion() {
		return alDiaDireccion;
	}

	public void setAlDiaDireccion(Blob alDiaDireccion) {
		this.alDiaDireccion = alDiaDireccion;
	}

	public Blob getAlDiaFacultad() {
		return alDiaFacultad;
	}

	public void setAlDiaFacultad(Blob alDiaFacultad) {
		this.alDiaFacultad = alDiaFacultad;
	}

	public Blob getAlDiaDepartamento() {
		return alDiaDepartamento;
	}

	public void setAlDiaDepartamento(Blob alDiaDepartamento) {
		this.alDiaDepartamento = alDiaDepartamento;
	}

	public void setBytesAlDiaVicerrectoria(byte[] bytes){
		alDiaVicerrectoria= Hibernate.createBlob(bytes);
	}

	public byte[] getBytesAlDiaVicerrectoria(){
		byte[] resultado = null;
		try {
			resultado = alDiaVicerrectoria.getBytes(1, (int) alDiaVicerrectoria.length());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}

	public void setBytesAlDiaDireccion(byte[] bytes){
		alDiaDireccion= Hibernate.createBlob(bytes);
	}

	public byte[] getBytesAlDiaDirecccion(){
		byte[] resultado = null;
		try {
			resultado = alDiaDireccion.getBytes(1, (int) alDiaDireccion.length());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}


	public void setBytesAlDiaFacultad(byte[] bytes){
		alDiaFacultad= Hibernate.createBlob(bytes);
	}

	public byte[] getBytesAlDiaFacultad(){
		byte[] resultado = null;
		try {
			resultado = alDiaFacultad.getBytes(1, (int) alDiaFacultad.length());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}


	public void setBytesAlDiaDepartamento(byte[] bytes){
		alDiaDepartamento= Hibernate.createBlob(bytes);
	}

	public byte[] getBytesAlDiaDepartamento(){
		byte[] resultado = null;
		try {
			resultado = alDiaDepartamento.getBytes(1, (int) alDiaDepartamento.length());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}

	public String getNombreVicerrectoria() {
		return nombreVicerrectoria;
	}

	public void setNombreVicerrectoria(String nombreVicerrectoria) {
		this.nombreVicerrectoria = nombreVicerrectoria;
	}

	public String getNombreDireccion() {
		return nombreDireccion;
	}

	public void setNombreDireccion(String nombreDireccion) {
		this.nombreDireccion = nombreDireccion;
	}

	public String getNombreFacultad() {
		return nombreFacultad;
	}

	public void setNombreFacultad(String nombreFacultad) {
		this.nombreFacultad = nombreFacultad;
	}

	public String getNombreDepartamento() {
		return nombreDepartamento;
	}

	public void setNombreDepartamento(String nombreDepartamento) {
		this.nombreDepartamento = nombreDepartamento;
	}

	public List<ArchivoMovilidadDE> getListaArchivo(){
		List<ArchivoMovilidadDE> listaArchivo=new ArrayList<ArchivoMovilidadDE>();
		listaArchivo.addAll(archivos);
		return 	listaArchivo;
	}

	public Set<ArchivoMovilidadDE> getArchivos() {
		return archivos;
	}

	public void setArchivos(Set<ArchivoMovilidadDE> archivos) {
		this.archivos = archivos;
	}
	
	public void adicionarArchivo(ArchivoMovilidadDE archivo){
		archivos.add(archivo);
	}
	
	public void borrarArchivo(ArchivoMovilidadDE archivo){
		archivos.remove(archivo);
	}
	public List<MovilidadPonencia> getListaPonencias(){
		List<MovilidadPonencia> listaPonencias=new ArrayList<MovilidadPonencia>();
		listaPonencias.addAll(ponencias);
		return 	listaPonencias;
	}
	
	public List<ActividadMovilidad> getListaActividades(){
		List<ActividadMovilidad> listaActividades = new ArrayList<ActividadMovilidad>();
		listaActividades.addAll(actividades);
		return listaActividades;
	}

	public Set<MovilidadPonencia> getPonencias() {
		return ponencias;
	}

	public void setPonencias(Set<MovilidadPonencia> ponencias) {
		this.ponencias = ponencias;
	}

	public String getTesis() {
		return tesis;
	}

	public void setTesis(String tesis) {
		this.tesis = tesis;
	}

	public String getIdReg() {
		return idReg;
	}

	public void setIdReg(String idReg) {
		this.idReg = idReg;
	}

	public String getTipoDocumentoReg() {
		return tipoDocumentoReg;
	}

	public void setTipoDocumentoReg(String tipoDocumentoReg) {
		this.tipoDocumentoReg = tipoDocumentoReg;
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

	public void setPalabrasClaves(Set <PalabraClave> palabrasClaves) {
		this.palabrasClaves = palabrasClaves;
	}

	public Set <PalabraClave> getPalabrasClaves() {
		return palabrasClaves;
	}

	public String getInfoConvocatoriaExterna() {
		return infoConvocatoriaExterna;
	}

	public void setInfoConvocatoriaExterna(String infoConvocatoriaExterna) {
		this.infoConvocatoriaExterna = infoConvocatoriaExterna;
	}

	public String getDescConvocatoriaExterna() {
		return descConvocatoriaExterna;
	}

	public void setDescConvocatoriaExterna(String descConvocatoriaExterna) {
		this.descConvocatoriaExterna = descConvocatoriaExterna;
	}

	public String getCaracterEvento() {
		return caracterEvento;
	}

	public void setCaracterEvento(String caracterEvento) {
		this.caracterEvento = caracterEvento;
	}

	public String getMedioTransporte() {
		return medioTransporte;
	}

	public void setMedioTransporte(String medioTransporte) {
		this.medioTransporte = medioTransporte;
	}

	public Long getProyectoFicha() {
		return proyectoFicha;
	}

	public void setProyectoFicha(Long proyectoFicha) {
		this.proyectoFicha = proyectoFicha;
	}

	public void setArchivosSeguimiento(List<ArchivoMovilidadDE> archivosSeguimiento) {
		this.archivosSeguimiento = archivosSeguimiento;
	}

	public List<ArchivoMovilidadDE> getArchivosSeguimiento() {
		return archivosSeguimiento;
	}

	public String getNombreUniversidad() {
		return nombreUniversidad;
	}

	public void setNombreUniversidad(String nombreUniversidad) {
		this.nombreUniversidad = nombreUniversidad;
	}

	public String getMovilidadConvocatoriaFacultad() {
		return movilidadConvocatoriaFacultad;
	}

	public void setMovilidadConvocatoriaFacultad(
			String movilidadConvocatoriaFacultad) {
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

	public List<ArchivoMovilidadDE> getArchivosRevisionRequisitos() {
		archivosRevisionRequisitos = obtenerListaArchivoEspecifico(
				TipoArchivoMovilidad.REVISION_REQUISITOS, archivosRevisionRequisitos);
		return archivosRevisionRequisitos;
	}
	
	public List<ArchivoMovilidadDE> getArchivosRevisionRequisitosSede() {
		archivosRevisionRequisitosSede = obtenerListaArchivoEspecifico(
				TipoArchivoMovilidad.REVISION_REQUISITOS_SEDE, archivosRevisionRequisitosSede);
		 return archivosRevisionRequisitosSede;
	}
	
	private List<ArchivoMovilidadDE>obtenerListaArchivoEspecifico(String tipo,
										List<ArchivoMovilidadDE> listaArchivo){
		if(listaArchivo == null || (
				listaArchivo != null && 
				listaArchivo.size() == 0)){
			if(archivos != null && archivos.size() > 0){
				Iterator<ArchivoMovilidadDE> i = archivos.iterator();
				while(i.hasNext()){
					ArchivoMovilidadDE archivoMovilidadDE = i.next();
					if(archivoMovilidadDE.getTipoArchivo().getId()
							.toString().equals(	tipo)){
						listaArchivo = agregarArchivo(archivoMovilidadDE,listaArchivo);
					}
				}
			}
		}
		return listaArchivo;
	}
	
	public List<ArchivoMovilidadDE> agregarArchivo(
			ArchivoMovilidadDE archivoMovilidadDE, 
			List<ArchivoMovilidadDE> listaArchivo){
		if(listaArchivo == null){
			listaArchivo = new ArrayList<ArchivoMovilidadDE>();
		}
		listaArchivo.add(archivoMovilidadDE);
		return listaArchivo;
	}
	
	public void agregarArchivoRequisitosFacultad(ArchivoMovilidadDE archivoMovilidadDE){
		archivosRevisionRequisitos = agregarArchivo(archivoMovilidadDE, archivosRevisionRequisitos);
	}
	
	public void agregarArchivoRequisitosSede(ArchivoMovilidadDE archivoMovilidadDE){
		archivosRevisionRequisitosSede = agregarArchivo(archivoMovilidadDE, archivosRevisionRequisitosSede);
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public void setArchivosRevisionRequisitosSede(
			List<ArchivoMovilidadDE> archivosRevisionRequisitosSede) {
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

	public void setExperienciasTrabajoPresentado(
			String experienciasTrabajoPresentado) {
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
	 * @param dependencia the dependencia to set
	 */
	public void setDependencia(Dependencia dependencia) {
		this.dependencia = dependencia;
	}

	public String getPasaporteInvestigador() {
		return pasaporteInvestigador;
	}

	public void setPasaporteInvestigador(String pasaporteInvestigador) {
		this.pasaporteInvestigador = pasaporteInvestigador;
	}

    /**
     * @return the tipoMovilidadCadena
     */
    public String getTipoMovilidadCadena() {
        return tipoMovilidadCadena;
    }

    /**
     * @param tipoMovilidadCadena the tipoMovilidadCadena to set
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

	public String getSubModalidadConvocatoria() {
		return subModalidadConvocatoria;
	}

	public void setSubModalidadConvocatoria(String subModalidadConvocatoria) {
		this.subModalidadConvocatoria = subModalidadConvocatoria;
	}

	public Long getValorTotalMovilidadSolicitado() {
		return valorTotalMovilidadSolicitado;
	}

	public void setValorTotalMovilidadSolicitado(Long valorTotalMovilidadSolicitado) {
		this.valorTotalMovilidadSolicitado = valorTotalMovilidadSolicitado;
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

	public String getMovilidadTipo() {
		return movilidadTipo;
	}

	public void setMovilidadTipo(String movilidadTipo) {
		this.movilidadTipo = movilidadTipo;
	}

	public String getMovilidadDirigidaOtraDependencia() {
		return movilidadDirigidaOtraDependencia;
	}

	public void setMovilidadDirigidaOtraDependencia(String movilidadDirigidaOtraDependencia) {
		this.movilidadDirigidaOtraDependencia = movilidadDirigidaOtraDependencia;
	}

}
