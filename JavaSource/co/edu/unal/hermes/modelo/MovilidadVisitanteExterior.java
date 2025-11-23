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

import co.edu.unal.hermes.utils.UtilPalabraClave;

public class MovilidadVisitanteExterior extends MovilidadInvestigador implements Serializable {

	private static final long serialVersionUID = 2683006092488251832L;
	public static String EVENTO = "E";
	public static String TESIS = "T";
	public static String VISITANTE = "V";

    public static String LECTURA_FACULTAD = "L";
    public static String APROBACION_SEDE= "A";
	public static String DEVUELTO = "D";
	
    public static String VISITANTEEXTERIOR = "A1";

	private Long idtipo;
	private String tipoDocumentoPersona;
	private Long idpersona;
	private PlanEstudios idprograma;
	byte[] hojavida;
	private String plan;
	private Long costotiquete;
	private Long monto;
	private Double numerodias;
	private Integer idinvestigador;
	private String resumen;
	private Grupo grupo;
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
	private String universidad;
	private String tituloAspira;

	private String documentoVisitante;
	private String nacionalidad;
	private TipoPonencia Ponencia;
	private String otroTipoPonencia;
	private String nombreArchivo;

	private String nombreVicerrectoria;
	private String nombreDireccion;
	private String nombreFacultad;
	private String nombreDepartamento;
	
    private String tipoMovilidadCadena;
	private Sede sede;
	private String nombreVisitante;

	private String aportePrograma;

	private Set<ArchivoMovilidadVE> archivos = new HashSet<ArchivoMovilidadVE>();
	private Set<PalabraClave> palabrasClaves = new HashSet<PalabraClave>();

	private Long proyectoFicha;
	private List<ArchivoMovilidadVE> archivosSeguimiento;

	private Dependencia dependencia;
	private String tipoVisitante;

	private Set<ActividadMovilidadVE> actividades = new HashSet<ActividadMovilidadVE>();

	Blob alDiaVicerrectoria;
	Blob alDiaDireccion;
	Blob alDiaFacultad;
	Blob alDiaDepartamento;

	// MODIFICACIONES ING MILENA
	private String emailVisitante;
	private String movilidadConvocatoriaFacultad;
	private Long valorViaticos;
	private String valorOtrosAportes;
	private List<ArchivoMovilidadVE> archivosRevisionRequisitos;
	private List<ArchivoMovilidadVE> archivosRevisionRequisitosSede;
	private String error;
	private String experienciasVisitante;
	private String descripcionRevision;
	private TipoMovilidad tipoMovilidad;
	private Long montoAdicionalEjecutado;
	
	private String tipoSolicitante;
	
	private String movilidadTipo;
	
	private Long costoEstimuloVirtual;
	
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

	public PlanEstudios getIdprograma() {
		return idprograma;
	}

	public void setIdprograma(PlanEstudios idprograma) {
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

	public Double getNumerodias() {
		return numerodias;
	}

	public void setNumerodias(Double numerodias) {
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

	public Set<ActividadMovilidadVE> getActividades() {
		return actividades;
	}

	public void setActividades(Set<ActividadMovilidadVE> actividades) {
		this.actividades = actividades;
	}

	public List<ActividadMovilidadVE> getListaActividades() {
		List<ActividadMovilidadVE> listaActividades = new ArrayList<ActividadMovilidadVE>();
		listaActividades.addAll(actividades);
		return listaActividades;
	}

	public List<ArchivoMovilidadVE> getListaArchivo() {
		List<ArchivoMovilidadVE> listaArchivo = new ArrayList<ArchivoMovilidadVE>();
		listaArchivo.addAll(archivos);
		return listaArchivo;
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

	public void setBytesAlDiaVicerrectoria(byte[] bytes) {
		alDiaVicerrectoria = Hibernate.createBlob(bytes);
	}

	public byte[] getBytesAlDiaVicerrectoria() {
		byte[] resultado = null;
		try {
			resultado = alDiaVicerrectoria.getBytes(1,
					(int) alDiaVicerrectoria.length());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}

	public void setBytesAlDiaDireccion(byte[] bytes) {
		alDiaDireccion = Hibernate.createBlob(bytes);
	}

	public byte[] getBytesAlDiaDirecccion() {
		byte[] resultado = null;
		try {
			resultado = alDiaDireccion.getBytes(1,
					(int) alDiaDireccion.length());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}

	public void setBytesAlDiaFacultad(byte[] bytes) {
		alDiaFacultad = Hibernate.createBlob(bytes);
	}

	public byte[] getBytesAlDiaFacultad() {
		byte[] resultado = null;
		try {
			resultado = alDiaFacultad.getBytes(1, (int) alDiaFacultad.length());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}

	public void setBytesAlDiaDepartamento(byte[] bytes) {
		alDiaDepartamento = Hibernate.createBlob(bytes);
	}

	public byte[] getBytesAlDiaDepartamento() {
		byte[] resultado = null;
		try {
			resultado = alDiaDepartamento.getBytes(1,
					(int) alDiaDepartamento.length());
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

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Sede getSede() {
		return sede;
	}

	public void setSede(Sede sede) {
		this.sede = sede;
	}

	public String getNombreVisitante() {
		return nombreVisitante;
	}

	public void setNombreVisitante(String nombreVisitante) {
		this.nombreVisitante = nombreVisitante;
	}

	public Set<ArchivoMovilidadVE> getArchivos() {
		return archivos;
	}

	public void setArchivos(Set<ArchivoMovilidadVE> archivos) {
		this.archivos = archivos;
	}

	public void setPalabrasClaves(Set<PalabraClave> palabrasClaves) {
		this.palabrasClaves = palabrasClaves;
	}

	public Set<PalabraClave> getPalabrasClaves() {
		return palabrasClaves;
	}

	public void adicionarPalabraClave(PalabraClave palabra) {
		UtilPalabraClave.limpiaPalabraClave(palabra);
		palabrasClaves.add(palabra);
	}
	
	public void adicionarActividad(ActividadMovilidadVE act){
		actividades.add(act);
	}
	
	public void borrarActividad(ActividadMovilidadVE act){
		actividades.remove(act);
	}
	
	public void adicionarArchivo(ArchivoMovilidadVE archivo){
		archivos.add(archivo);
	}
	
	public void borrarArchivo(ArchivoMovilidadVE archivo){
		archivos.remove(archivo);
	}

	public void borrarPalabraClave(PalabraClave palabra) {
		palabrasClaves.remove(palabra);
	}

	public void setAportePrograma(String aportePrograma) {
		this.aportePrograma = aportePrograma;
	}

	public String getAportePrograma() {
		return aportePrograma;
	}

	public Long getProyectoFicha() {
		return proyectoFicha;
	}

	public void setProyectoFicha(Long proyectoFicha) {
		this.proyectoFicha = proyectoFicha;
	}

	public void setArchivosSeguimiento(
			List<ArchivoMovilidadVE> archivosSeguimiento) {
		this.archivosSeguimiento = archivosSeguimiento;
	}

	public List<ArchivoMovilidadVE> getArchivosSeguimiento() {
		return archivosSeguimiento;
	}

	public String getEmailVisitante() {
		return emailVisitante;
	}

	public void setEmailVisitante(String emailVisitante) {
		this.emailVisitante = emailVisitante;
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

	public Dependencia getDependencia() {
		return dependencia;
	}

	public void setDependencia(Dependencia dependencia) {
		this.dependencia = dependencia;
	}

	public List<ArchivoMovilidadVE> getArchivosRevisionRequisitos() {
		archivosRevisionRequisitos = obtenerListaArchivoEspecifico(
				TipoArchivoMovilidad.REVISION_REQUISITOS,
				archivosRevisionRequisitos);
		return archivosRevisionRequisitos;
	}

	public List<ArchivoMovilidadVE> getArchivosRevisionRequisitosSede() {
		archivosRevisionRequisitosSede = obtenerListaArchivoEspecifico(
				TipoArchivoMovilidad.REVISION_REQUISITOS_SEDE,
				archivosRevisionRequisitosSede);
		return archivosRevisionRequisitosSede;
	}

	private List<ArchivoMovilidadVE> obtenerListaArchivoEspecifico(String tipo,
			List<ArchivoMovilidadVE> listaArchivo) {
		if (listaArchivo == null
				|| (listaArchivo != null && listaArchivo.size() == 0)) {
			if (archivos != null && archivos.size() > 0) {
				Iterator<ArchivoMovilidadVE> i = archivos.iterator();
				while (i.hasNext()) {
					ArchivoMovilidadVE archivoMovilidadVE = i.next();
					if (archivoMovilidadVE.getTipoArchivo().getId().toString()
							.equals(tipo)) {
						listaArchivo = agregarArchivo(archivoMovilidadVE,
								listaArchivo);
					}
				}
			}
		}
		return listaArchivo;
	}

	public List<ArchivoMovilidadVE> agregarArchivo(
			ArchivoMovilidadVE archivoMovilidadVE,
			List<ArchivoMovilidadVE> listaArchivo) {
		if (listaArchivo == null) {
			listaArchivo = new ArrayList<ArchivoMovilidadVE>();
		}
		listaArchivo.add(archivoMovilidadVE);
		return listaArchivo;
	}

	public void agregarArchivoRequisitosFacultad(
			ArchivoMovilidadVE archivoMovilidadVE) {
		archivosRevisionRequisitos = agregarArchivo(archivoMovilidadVE,
				archivosRevisionRequisitos);
	}

	public void agregarArchivoRequisitosSede(
			ArchivoMovilidadVE archivoMovilidadVE) {
		archivosRevisionRequisitosSede = agregarArchivo(archivoMovilidadVE,
				archivosRevisionRequisitosSede);
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getExperienciasVisitante() {
		return experienciasVisitante;
	}

	public void setExperienciasVisitante(String experienciasVisitante) {
		this.experienciasVisitante = experienciasVisitante;
	}

	public String getDescripcionRevision() {
		return descripcionRevision;
	}

	public void setDescripcionRevision(String descripcionRevision) {
		this.descripcionRevision = descripcionRevision;
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

	public TipoMovilidad getTipoMovilidad() {
		return tipoMovilidad;
	}

	public void setTipoMovilidad(TipoMovilidad tipoMovilidad) {
		this.tipoMovilidad = tipoMovilidad;
	}

	public Long getMontoAdicionalEjecutado() {
		return montoAdicionalEjecutado;
	}

	public void setMontoAdicionalEjecutado(Long montoAdicionalEjecutado) {
		this.montoAdicionalEjecutado = montoAdicionalEjecutado;
	}

	public String getTipoVisitante() {
		return tipoVisitante;
	}

	public void setTipoVisitante(String tipoVisitante) {
		this.tipoVisitante = tipoVisitante;
	}

	public String getTipoSolicitante() {
		return tipoSolicitante;
	}

	public void setTipoSolicitante(String tipoSolicitante) {
		this.tipoSolicitante = tipoSolicitante;
	}

	public String getMovilidadTipo() {
		return movilidadTipo;
	}

	public void setMovilidadTipo(String movilidadTipo) {
		this.movilidadTipo = movilidadTipo;
	}

	public Long getCostoEstimuloVirtual() {
		return costoEstimuloVirtual;
	}

	public void setCostoEstimuloVirtual(Long costoEstimuloVirtual) {
		this.costoEstimuloVirtual = costoEstimuloVirtual;
	}

	public String getMovilidadDirigidaOtraDependencia() {
		return movilidadDirigidaOtraDependencia;
	}

	public void setMovilidadDirigidaOtraDependencia(String movilidadDirigidaOtraDependencia) {
		this.movilidadDirigidaOtraDependencia = movilidadDirigidaOtraDependencia;
	}
}
