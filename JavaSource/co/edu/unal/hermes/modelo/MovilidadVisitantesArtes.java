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
public class MovilidadVisitantesArtes extends Movilidad implements Serializable{

    public static String ARTISTASVISITANTES = "C3";
    
	private static final long serialVersionUID = 7027390529481907698L;
	//INFORMACIÓN PERSONAL
	private String nombreArtistico;
	private Pais nacionalidad;
	private String pasaporte;
	//INFORMACIÓN DE CONTACTO
	private String paginaWeb;
	private String telefonoFijo;
	private String telefonoMovil;
	private String direccion;
	//INFORMACIÓN GENERAL DEL PROYECTO
	private String nombreProyecto;
    private String tipoMovilidadCadena;
	private String statementArtista;
	private String descripcionProyecto;
	private String objetivoGeneral;
	private String objetivoEspecifico;
	private String proyectoRelacionLocal;
	//DATOS DE DESARROLLO DE LA RESIDENCIA
	
	private String descripcionFacultad;
	private String descripcionSede;
	
	private String dependencia;


	private String areaArte;
	private String sedeFrontera;
	//private String tipoInstitucion;
	private String convenio;
	private String convenioDescripcion;
	//private String descripcionPrograma;
	private String nombresVisitante;
	private String apellidosVisitante;
	
	//CRONOGRAMA
	private Set actividades = new HashSet();
	
	//PRESUPUESTO
	private Continente continente;	
	private TipoFormacion formacion;	
	
	private String tiquetesUN = "0";
	private String tallerUN= "0";
	private String alojamientoUN= "0";
	private String alimentacionUN= "0";
	private String materialesUN= "0";
	private String transporteUN= "0";
	private String socializacionUN= "0";
	private String tiquetesP= "0";
	private String tallerP= "0";
	private String alojamientoP= "0";
	private String alimentacionP= "0";
	private String materialesP= "0";
	private String transporteP= "0";
	private String socializacionP= "0";
	
	private String planSocializacionResidencia;
	private String planSocializacionUN;
	//INFORMACION ADICIONAL
	private String comoEntero;
	private String aporteRedArtistas;
	private String contribucion;
	private String especialistasArea;	
	private String trabajos;
	private String idFacultad;	
	private String tipoDocumentoFacultad;

	private String aceptacionFacultad;
	private String aprobacionSede;
	private Date fechasolicitud;
	
	private String totalUN;
	private String totalPropio;
	
	//INFORMACION ARCHIVOS
    private Set archivos = new HashSet();
    
  //ETAPA Y ESTADO
    private String etapa;
    private String confirmarResidencia;
    private String comentariosFac;
    
    private String dispPresupuestal;
	private String dispPresupuestalSede;

	public Date getFechasolicitud() {
		return fechasolicitud;
	}

	public void setFechasolicitud(Date fechasolicitud) {
		this.fechasolicitud = fechasolicitud;
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

	public String getSedeFrontera() {
		return sedeFrontera;
	}

	public void setSedeFrontera(String sedeFrontera) {
		this.sedeFrontera = sedeFrontera;
	}

	/*public String getTipoInstitucion() {
		return tipoInstitucion;
	}

	public void setTipoInstitucion(String tipoInstitucion) {
		this.tipoInstitucion = tipoInstitucion;
	}*/

	public String getConvenio() {
		return convenio;
	}

	public void setConvenio(String convenio) {
		this.convenio = convenio;
	}

/*	public String getDescripcionPrograma() {
		return descripcionPrograma;
	}

	public void setDescripcionPrograma(String descripcionPrograma) {
		this.descripcionPrograma = descripcionPrograma;
	}*/

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
	
	public List getListaArchivo(){
		List listaArchivo=new ArrayList();
		listaArchivo.addAll(archivos);
		return 	listaArchivo;
	}

	public Set getArchivos() {
		return archivos;
	}

	public void setArchivos(Set archivos) {
		this.archivos = archivos;
	}

	public TipoFormacion getFormacion() {
		return formacion;
	}

	public void setFormacion(TipoFormacion formacion) {
		this.formacion = formacion;
	}

	public String getNombresVisitante() {
		return nombresVisitante;
	}

	public void setNombresVisitante(String nombresVisitante) {
		this.nombresVisitante = nombresVisitante;
	}

	public String getApellidosVisitante() {
		return apellidosVisitante;
	}

	public void setApellidosVisitante(String apellidosVisitante) {
		this.apellidosVisitante = apellidosVisitante;
	}

	public String getConvenioDescripcion() {
		return convenioDescripcion;
	}

	public void setConvenioDescripcion(String convenioDescripcion) {
		this.convenioDescripcion = convenioDescripcion;
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

	public String getTrabajos() {
		return trabajos;
	}

	public void setTrabajos(String trabajos) {
		this.trabajos = trabajos;
	}

	public String getEtapa() {
		return etapa;
	}

	public void setEtapa(String etapa) {
		this.etapa = etapa;
	}
	
	public String getConfirmarResidencia() {
		return confirmarResidencia;
	}

	public void setConfirmarResidencia(String confirmarResidencia) {
		this.confirmarResidencia = confirmarResidencia;
	}

	public String getDependencia() {
		return dependencia;
	}

	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}

	public void setIdFacultad(String idFacultad) {
		this.idFacultad = idFacultad;
	}

	public String getIdFacultad() {
		return idFacultad;
	}

	public void setTipoDocumentoFacultad(String tipoDocumentoFacultad) {
		this.tipoDocumentoFacultad = tipoDocumentoFacultad;
	}

	public String getTipoDocumentoFacultad() {
		return tipoDocumentoFacultad;
	}

	public void setComentariosFac(String comentariosFac) {
		this.comentariosFac = comentariosFac;
	}

	public String getComentariosFac() {
		return comentariosFac;
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
}
