/**
 * @author Martha Liliana Correa O.
 * @date 09/11/2015
 */

package co.edu.unal.hermes.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConceptoContratoBiodiversidad implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String FORMULACION = "F";
	public static final String ENVIADO = "E";
	public static final String REQUIERE_SUSCRIPCION = "A";
	public static final String NO_REQUIERE_SUSCRIPCION = "N";
	public static final String RECHAZADO = "R";
	public static final String EN_TRAMITE = "T";
	public static final String DEVUELTA = "C";

	private Long id;
	private String estado; 
	private Convocatoria modalidad;
	private InvestigadorInterno investigador;
	private Proyecto proyecto;
	private String descripcionActividad;
	private Date fechaSolicitud;
	private Date fechaTramite;
	private Persona personaRevisa;
	private String observaciones;
	private Proyecto proyectoContrato;
	private String numeroRadicado;
	private TipoTramiteBiodiversidad tipoTramite;
	private Dependencia dependencia;
	
	/**
	 * Actividades asociadas al concepto
	 */
	private Set<ActividadRecursoGenetico> actividades = new HashSet<ActividadRecursoGenetico>();
	
	/**
	 * Entidades asociadas al concepto
	 */
	private Set<FuenteFinanciacion> entidades = new HashSet<FuenteFinanciacion>();

	
	/** default constructor */
	public ConceptoContratoBiodiversidad() {
		
	}
	
	public ConceptoContratoBiodiversidad(Long id) {
		this.setId(id);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNombreEstado(){
		if(this.estado!=null){
			if(this.estado.equals(FORMULACION)){
				return "Formulación";
			}else if(this.estado.equals(ENVIADO)){
				return "Enviado";
			}else if(this.estado.equals(REQUIERE_SUSCRIPCION)){
				return "Es necesaria la suscripción de contrato u otrosí.";
			}else if(this.estado.equals(NO_REQUIERE_SUSCRIPCION)){
				return "No es necesaria la suscripción de contrato u otrosí.";
			}else if(this.estado.equals(RECHAZADO)){
				return "Rechazado";
			}else if(this.estado.equals(EN_TRAMITE)){
                return "En trámite";
			}else if(this.estado.equals(DEVUELTA)){
                return "Devuelta para correcciones";
            }else{
				return "";
			}
		}else{
			return "";
		}
	}
	
	public String getDescripcionActividad() {
		return descripcionActividad;
	}

	public void setDescripcionActividad(String descripcionActividad) {
		this.descripcionActividad = descripcionActividad;
	}

	public InvestigadorInterno getInvestigador() {
		return investigador;
	}

	public void setInvestigador(InvestigadorInterno investigador) {
		this.investigador = investigador;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public Date getFechaTramite() {
		return fechaTramite;
	}

	public void setFechaTramite(Date fechaTramite) {
		this.fechaTramite = fechaTramite;
	}

	public Persona getPersonaRevisa() {
		return personaRevisa;
	}

	public void setPersonaRevisa(Persona personaRevisa) {
		this.personaRevisa = personaRevisa;
	}

	public Convocatoria getModalidad() {
		return modalidad;
	}

	public void setModalidad(Convocatoria modalidad) {
		this.modalidad = modalidad;
	}

	public Set<ActividadRecursoGenetico> getActividades() {
		return actividades;
	}

	public void setActividades(Set<ActividadRecursoGenetico> actividades) {
		this.actividades = actividades;
	}
	
	public void adicionarActividad(ActividadRecursoGenetico actividad) {
		actividades.add(actividad);
	}

	public void borrarActividad(ActividadRecursoGenetico actividad) {
		actividades.remove(actividad);
	}
	
	public List<ActividadRecursoGenetico> getListaActividades() {
		List<ActividadRecursoGenetico> listaActividades = new ArrayList<ActividadRecursoGenetico>();
		listaActividades.addAll(actividades);
		return listaActividades;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Proyecto getProyectoContrato() {
		return proyectoContrato;
	}

	public void setProyectoContrato(Proyecto proyectoContrato) {
		this.proyectoContrato = proyectoContrato;
	}

	public Set<FuenteFinanciacion> getEntidades() {
		return entidades;
	}

	public void setEntidades(Set<FuenteFinanciacion> entidades) {
		this.entidades = entidades;
	}
	
	public void adicionarEntidad(FuenteFinanciacion entidad) {
		entidades.add(entidad);
	}

	public void borrarEntidad(FuenteFinanciacion entidad) {
		entidades.remove(entidad);
	}
	
	public List<FuenteFinanciacion> getListaEntidades() {
		List<FuenteFinanciacion> listaEntidades = new ArrayList<FuenteFinanciacion>();
		listaEntidades.addAll(entidades);
		return listaEntidades;
	}

    public String getNumeroRadicado() {
        return numeroRadicado;
    }

    public void setNumeroRadicado(String numeroRadicado) {
        this.numeroRadicado = numeroRadicado;
    }

    public TipoTramiteBiodiversidad getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(TipoTramiteBiodiversidad tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public Dependencia getDependencia() {
        return dependencia;
    }

    public void setDependencia(Dependencia dependencia) {
        this.dependencia = dependencia;
    }

}