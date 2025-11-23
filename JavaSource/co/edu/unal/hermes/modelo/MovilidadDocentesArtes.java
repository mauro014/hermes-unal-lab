/*
 * Created on 9-dic-2007
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Ing. Wilver Alexander Martinez Martinez -wam²
 */
public class MovilidadDocentesArtes extends Movilidad implements Serializable {

    public static String ARTISTASDOCENTES = "C1";

    private static final long serialVersionUID = -6859667511657729583L;
    // INFORMACIÓN PERSONAL
    private String nombreArtistico;
    private Pais nacionalidad;
    private String pasaporte;
    // INFORMACIÓN DE CONTACTO
    private String paginaWeb;
    private String telefonoFijo;
    private String telefonoMovil;
    private String direccion;
    // INFORMACIÓN GENERAL DEL PROYECTO
    private String nombreProyecto;
    private String statementArtista;
    private String descripcionProyecto;
    private String objetivoGeneral;
    private String objetivoEspecifico;
    private String proyectoRelacionLocal;
    // DATOS DE DESARROLLO DE LA RESIDENCIA
    private Pais pais;
    private String ciudad;
    private String areaArte;
    private String seleccionInstitucion;
    private String institucion;
    private String tipoInstitucion;
    private String anteriorResidencia;
    private String descripcionPrograma;

    private String descripcionFacultad;
    private String descripcionSede;
    
    private String dispPresupuestal;
    private String dispPresupuestalSede;
    
    private String estadoRevisionSeguimiento;

    // CRONOGRAMA
    private Set actividades = new HashSet();

    // PRESUPUESTO
    private Continente continente;

    private String tiquetesUN = "0";
    private String tallerUN = "0";
    private String alojamientoUN = "0";
    private String alimentacionUN = "0";
    private String materialesUN = "0";
    private String transporteUN = "0";
    private String socializacionUN = "0";
    private String tiquetesP = "0";
    private String tallerP = "0";
    private String alojamientoP = "0";
    private String alimentacionP = "0";
    private String materialesP = "0";
    private String transporteP = "0";
    private String socializacionP = "0";

    private String planSocializacionResidencia;
    private String planSocializacionUN;
    // INFORMACION ADICIONAL
    private String comoEntero;
    private String aporteRedArtistas;
    private String contribucion;
    private String especialistasArea;
    private String trabajos;

    private String aceptacionFacultad;
    private String aprobacionSede;

    private String totalUN;
    private String totalPropio;

    // INFORMACION ARCHIVOS
    private Set<ArchivoMovilidad> archivos = new HashSet<ArchivoMovilidad>();

    // ETAPA Y ESTADO
    private String etapa;
    private String confirmarResidencia;

    // VARIBLES TEMPORALES
    private String idPersonaAprobacionSede;
    private String tipoIdPersonaAprobacionSede;
    private Long proyectoFicha;
    private List<ArchivoMovilidad> archivosRevisionRequisitos;
    
	 //SEGUIMIENTO
	private Date segMovFecha;
	private String segMovDescripcion;
	private String calificacionA;
	private String calificacionB;
	private String calificacionC; 
	private String realizacionMovilidad;
	private String razonesNoRealizacion;
	private String estadoSeguimiento;
	private String totalTiquetesUN = "0";
	private String totalTallerUN= "0";
	private String totalAlojamientoUN= "0";
	private String totalAlimentacionUN= "0";
	private String totalMaterialesUN= "0";
	private String totalTransporteUN= "0";
	private String totalSocializacionUN= "0";
	private String totalApoyoUN = "0";
	private String experienciaResidencia;

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

    public Continente getContinente() {
        return continente;
    }

    public void setContinente(Continente continente) {
        this.continente = continente;
    }

    public String getNombreArtistico() {
        return nombreArtistico;
    }

    public void setNombreArtistico(String nombreArtistico) {
        this.nombreArtistico = nombreArtistico;
    }

    public Pais getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(Pais nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getPasaporte() {
        return pasaporte;
    }

    public void setPasaporte(String pasaporte) {
        this.pasaporte = pasaporte;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public String getStatementArtista() {
        return statementArtista;
    }

    public void setStatementArtista(String statementArtista) {
        this.statementArtista = statementArtista;
    }

    public String getDescripcionProyecto() {
        return descripcionProyecto;
    }

    public void setDescripcionProyecto(String descripcionProyecto) {
        this.descripcionProyecto = descripcionProyecto;
    }

    public String getObjetivoGeneral() {
        return objetivoGeneral;
    }

    public void setObjetivoGeneral(String objetivoGeneral) {
        this.objetivoGeneral = objetivoGeneral;
    }

    public String getObjetivoEspecifico() {
        return objetivoEspecifico;
    }

    public void setObjetivoEspecifico(String objetivoEspecifico) {
        this.objetivoEspecifico = objetivoEspecifico;
    }

    public String getProyectoRelacionLocal() {
        return proyectoRelacionLocal;
    }

    public void setProyectoRelacionLocal(String proyectoRelacionLocal) {
        this.proyectoRelacionLocal = proyectoRelacionLocal;
    }

    public String getAreaArte() {
        return areaArte;
    }

    public void setAreaArte(String areaArte) {
        this.areaArte = areaArte;
    }

    public String getSeleccionInstitucion() {
        return seleccionInstitucion;
    }

    public void setSeleccionInstitucion(String seleccionInstitucion) {
        this.seleccionInstitucion = seleccionInstitucion;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getTipoInstitucion() {
        return tipoInstitucion;
    }

    public void setTipoInstitucion(String tipoInstitucion) {
        this.tipoInstitucion = tipoInstitucion;
    }

    public String getAnteriorResidencia() {
        return anteriorResidencia;
    }

    public void setAnteriorResidencia(String anteriorResidencia) {
        this.anteriorResidencia = anteriorResidencia;
    }

    public String getDescripcionPrograma() {
        return descripcionPrograma;
    }

    public void setDescripcionPrograma(String descripcionPrograma) {
        this.descripcionPrograma = descripcionPrograma;
    }

    public Set getActividades() {
        return actividades;
    }

    public void setActividades(Set actividades) {
        this.actividades = actividades;
    }

    public String getTiquetesUN() {
        return tiquetesUN;
    }

    public void setTiquetesUN(String tiquetesUN) {
        this.tiquetesUN = tiquetesUN;
    }

    public String getTallerUN() {
        return tallerUN;
    }

    public void setTallerUN(String tallerUN) {
        this.tallerUN = tallerUN;
    }

    public String getAlojamientoUN() {
        return alojamientoUN;
    }

    public void setAlojamientoUN(String alojamientoUN) {
        this.alojamientoUN = alojamientoUN;
    }

    public String getAlimentacionUN() {
        return alimentacionUN;
    }

    public void setAlimentacionUN(String alimentacionUN) {
        this.alimentacionUN = alimentacionUN;
    }

    public String getMaterialesUN() {
        return materialesUN;
    }

    public void setMaterialesUN(String materialesUN) {
        this.materialesUN = materialesUN;
    }

    public String getTransporteUN() {
        return transporteUN;
    }

    public void setTransporteUN(String transporteUN) {
        this.transporteUN = transporteUN;
    }

    public String getSocializacionUN() {
        return socializacionUN;
    }

    public void setSocializacionUN(String socializacionUN) {
        this.socializacionUN = socializacionUN;
    }

    public String getTiquetesP() {
        return tiquetesP;
    }

    public void setTiquetesP(String tiquetesP) {
        this.tiquetesP = tiquetesP;
    }

    public String getTallerP() {
        return tallerP;
    }

    public void setTallerP(String tallerP) {
        this.tallerP = tallerP;
    }

    public String getAlojamientoP() {
        return alojamientoP;
    }

    public void setAlojamientoP(String alojamientoP) {
        this.alojamientoP = alojamientoP;
    }

    public String getAlimentacionP() {
        return alimentacionP;
    }

    public void setAlimentacionP(String alimentacionP) {
        this.alimentacionP = alimentacionP;
    }

    public String getMaterialesP() {
        return materialesP;
    }

    public void setMaterialesP(String materialesP) {
        this.materialesP = materialesP;
    }

    public String getTransporteP() {
        return transporteP;
    }

    public void setTransporteP(String transporteP) {
        this.transporteP = transporteP;
    }

    public String getSocializacionP() {
        return socializacionP;
    }

    public void setSocializacionP(String socializacionP) {
        this.socializacionP = socializacionP;
    }

    public String getComoEntero() {
        return comoEntero;
    }

    public void setComoEntero(String comoEntero) {
        this.comoEntero = comoEntero;
    }

    public String getAporteRedArtistas() {
        return aporteRedArtistas;
    }

    public void setAporteRedArtistas(String aporteRedArtistas) {
        this.aporteRedArtistas = aporteRedArtistas;
    }

    public String getContribucion() {
        return contribucion;
    }

    public void setContribucion(String contribucion) {
        this.contribucion = contribucion;
    }

    public String getEspecialistasArea() {
        return especialistasArea;
    }

    public void setEspecialistasArea(String especialistasArea) {
        this.especialistasArea = especialistasArea;
    }

    public String getAceptacionFacultad() {
        return aceptacionFacultad;
    }

    public void setAceptacionFacultad(String aceptacionFacultad) {
        this.aceptacionFacultad = aceptacionFacultad;
    }

    public String getAprobacionSede() {
        return aprobacionSede;
    }

    public void setAprobacionSede(String aprobacionSede) {
        this.aprobacionSede = aprobacionSede;
    }

    public String getTotalUN() {
        return totalUN;
    }

    public void setTotalUN(String totalUN) {
        this.totalUN = totalUN;
    }

    public String getTotalPropio() {
        return totalPropio;
    }

    public void setTotalPropio(String totalPropio) {
        this.totalPropio = totalPropio;
    }

    public Set<ArchivoMovilidad> getArchivos() {
        return archivos;
    }

    public void setArchivos(Set<ArchivoMovilidad> archivos) {
        this.archivos = archivos;
    }

    public String getPlanSocializacionResidencia() {
        return planSocializacionResidencia;
    }

    public void setPlanSocializacionResidencia(String planSocializacionResidencia) {
        this.planSocializacionResidencia = planSocializacionResidencia;
    }

    public String getPlanSocializacionUN() {
        return planSocializacionUN;
    }

    public void setPlanSocializacionUN(String planSocializacionUN) {
        this.planSocializacionUN = planSocializacionUN;
    }

    public String getDescripcionFacultad() {
        return descripcionFacultad;
    }

    public void setDescripcionFacultad(String descripcionFacultad) {
        this.descripcionFacultad = descripcionFacultad;
    }

    public String getDescripcionSede() {
        return descripcionSede;
    }

    public void setDescripcionSede(String descripcionSede) {
        this.descripcionSede = descripcionSede;
    }

    public String getTrabajos() {
        return trabajos;
    }

    public void setTrabajos(String trabajos) {
        this.trabajos = trabajos;
    }

    public String getConfirmarResidencia() {
        return confirmarResidencia;
    }

    public void setConfirmarResidencia(String confirmarResidencia) {
        this.confirmarResidencia = confirmarResidencia;
    }

    public List getListaArchivo() {
        List listaArchivo = new ArrayList();
        listaArchivo.addAll(archivos);
        return listaArchivo;
    }

    public void setIdPersonaAprobacionSede(String idPersonaAprobacionSede) {
        this.idPersonaAprobacionSede = idPersonaAprobacionSede;
    }

    public String getIdPersonaAprobacionSede() {
        return idPersonaAprobacionSede;
    }

    public void setTipoIdPersonaAprobacionSede(String tipoIdPersonaAprobacionSede) {
        this.tipoIdPersonaAprobacionSede = tipoIdPersonaAprobacionSede;
    }

    public String getTipoIdPersonaAprobacionSede() {
        return tipoIdPersonaAprobacionSede;
    }

    public Long getProyectoFicha() {
        return proyectoFicha;
    }

    public void setProyectoFicha(Long proyectoFicha) {
        this.proyectoFicha = proyectoFicha;
    }

    public List<ArchivoMovilidad> getArchivosRevisionRequisitos() {
        return archivosRevisionRequisitos;
    }

    public void setArchivosRevisionRequisitos(List<ArchivoMovilidad> archivosRevisionRequisitos) {
        this.archivosRevisionRequisitos = archivosRevisionRequisitos;
    }

    public void agregarArchivoRequisitos(ArchivoMovilidad archivoMovilidad) {
        if (archivosRevisionRequisitos == null) {
            archivosRevisionRequisitos = new ArrayList<ArchivoMovilidad>();
        }
        archivosRevisionRequisitos.add(archivoMovilidad);
    }

    public String getEstadoSeguimiento() {
        return estadoSeguimiento;
    }

    public void setEstadoSeguimiento(String estadoSeguimiento) {
        this.estadoSeguimiento = estadoSeguimiento;
    }

    public String getTotalTiquetesUN() {
        return totalTiquetesUN;
    }

    public void setTotalTiquetesUN(String totalTiquetesUN) {
        this.totalTiquetesUN = totalTiquetesUN;
    }

    public String getTotalTallerUN() {
        return totalTallerUN;
    }

    public void setTotalTallerUN(String totalTallerUN) {
        this.totalTallerUN = totalTallerUN;
    }

    public String getTotalAlojamientoUN() {
        return totalAlojamientoUN;
    }

    public void setTotalAlojamientoUN(String totalAlojamientoUN) {
        this.totalAlojamientoUN = totalAlojamientoUN;
    }

    public String getTotalAlimentacionUN() {
        return totalAlimentacionUN;
    }

    public void setTotalAlimentacionUN(String totalAlimentacionUN) {
        this.totalAlimentacionUN = totalAlimentacionUN;
    }

    public String getTotalMaterialesUN() {
        return totalMaterialesUN;
    }

    public void setTotalMaterialesUN(String totalMaterialesUN) {
        this.totalMaterialesUN = totalMaterialesUN;
    }

    public String getTotalTransporteUN() {
        return totalTransporteUN;
    }

    public void setTotalTransporteUN(String totalTransporteUN) {
        this.totalTransporteUN = totalTransporteUN;
    }

    public String getTotalSocializacionUN() {
        return totalSocializacionUN;
    }

    public void setTotalSocializacionUN(String totalSocializacionUN) {
        this.totalSocializacionUN = totalSocializacionUN;
    }

    public String getTotalApoyoUN() {
        return totalApoyoUN;
    }

    public void setTotalApoyoUN(String totalApoyoUN) {
        this.totalApoyoUN = totalApoyoUN;
    }

    public String getExperienciaResidencia() {
        return experienciaResidencia;
    }

    public void setExperienciaResidencia(String experienciaResidencia) {
        this.experienciaResidencia = experienciaResidencia;
    }

    /**
     * @return the etapa
     */
    public String getEtapa() {
        return etapa;
    }

    /**
     * @param etapa the etapa to set
     */
    public void setEtapa(String etapa) {
        this.etapa = etapa;
    }

	public Date getSegMovFecha() {
		return segMovFecha;
	}

	public void setSegMovFecha(Date segMovFecha) {
		this.segMovFecha = segMovFecha;
	}

	public String getSegMovDescripcion() {
		return segMovDescripcion;
	}

	public void setSegMovDescripcion(String segMovDescripcion) {
		this.segMovDescripcion = segMovDescripcion;
	}

	public String getCalificacionA() {
		return calificacionA;
	}

	public void setCalificacionA(String calificacionA) {
		this.calificacionA = calificacionA;
	}

	public String getCalificacionB() {
		return calificacionB;
	}

	public void setCalificacionB(String calificacionB) {
		this.calificacionB = calificacionB;
	}

	public String getCalificacionC() {
		return calificacionC;
	}

	public void setCalificacionC(String calificacionC) {
		this.calificacionC = calificacionC;
	}

	public String getRealizacionMovilidad() {
		return realizacionMovilidad;
	}

	public void setRealizacionMovilidad(String realizacionMovilidad) {
		this.realizacionMovilidad = realizacionMovilidad;
	}

	public String getRazonesNoRealizacion() {
		return razonesNoRealizacion;
	}

	public void setRazonesNoRealizacion(String razonesNoRealizacion) {
		this.razonesNoRealizacion = razonesNoRealizacion;
	}

	public String getDispPresupuestal() {
		return dispPresupuestal;
	}

	public void setDispPresupuestal(String dispPresupuestal) {
		this.dispPresupuestal = dispPresupuestal;
	}

	public String getDispPresupuestalSede() {
		return dispPresupuestalSede;
	}

	public void setDispPresupuestalSede(String dispPresupuestalSede) {
		this.dispPresupuestalSede = dispPresupuestalSede;
	}

	public String getEstadoRevisionSeguimiento() {
		return estadoRevisionSeguimiento;
	}

	public void setEstadoRevisionSeguimiento(String estadoRevisionSeguimiento) {
		this.estadoRevisionSeguimiento = estadoRevisionSeguimiento;
	}
}
