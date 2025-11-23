package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class ColeccionGestion implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String ESTADO_INGRESANDO = "I";
	public static final String ESTADO_ENVIADA = "E";
	public static final String ESTADO_ELIMINADA = "Z";
	public static final String ESTADO_RECHAZADA = "R";
	public static final String ESTADO_DEVUELTA = "D";
	public static final String ESTADO_EN_PROCESO = "P";
	public static final String ESTADO_APROBADA = "A";
	
	private Long id;
	
	private Long idColeccion ;
	private DominioDetalle estado;
	private Date fechaRegistro;
	private Date fechaRevision;
	private Date fechaElimina;
	private String comentariosRevision;
	private String justificacion;
	private Persona personaRegistra;
	private Persona personaRevisa;
	private Persona personaElimina;
	private Persona directorAnterior;
	private Persona directorNuevo;
	private DominioDetalle tipo;
	private FuenteFinanciacion autoridad;
	private String aceptaPublicacion;
	private DominioDetalle estadoAutoridad;
	private String comentariosAutoridad;

    //private Persona curadorGeneral;
	
	private Set<ArchivoGestionColeccion> archivos = new HashSet<ArchivoGestionColeccion>();
	private Set<ColeccionRequisito> requisitos = new HashSet<ColeccionRequisito>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public DominioDetalle getEstado() {
		return estado;
	}
	public void setEstado(DominioDetalle estado) {
		this.estado = estado;
	}
	
	public Long getIdColeccion() {
		return idColeccion;
	}
	public void setIdColeccion(Long idColeccion) {
		this.idColeccion = idColeccion;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public Date getFechaRevision() {
		return fechaRevision;
	}
	public void setFechaRevision(Date fechaRevision) {
		this.fechaRevision = fechaRevision;
	}
	public String getComentariosRevision() {
		return comentariosRevision;
	}
	public void setComentariosRevision(String comentariosRevision) {
		this.comentariosRevision = comentariosRevision;
	}
	public String getJustificacion() {
		return justificacion;
	}
	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}
	public DominioDetalle getTipo() {
		return tipo;
	}
	public void setTipo(DominioDetalle tipo) {
		this.tipo = tipo;
	}
	public Set<ArchivoGestionColeccion> getArchivos() {
		return archivos;
	}
	public void setArchivos(Set<ArchivoGestionColeccion> archivos) {
		this.archivos = archivos;
	}
	public FuenteFinanciacion getAutoridad() {
		return autoridad;
	}
	public void setAutoridad(FuenteFinanciacion autoridad) {
		this.autoridad = autoridad;
	}
	public Persona getPersonaRegistra() {
		return personaRegistra;
	}
	public void setPersonaRegistra(Persona personaRegistra) {
		this.personaRegistra = personaRegistra;
	}
	public Persona getPersonaRevisa() {
		return personaRevisa;
	}
	public void setPersonaRevisa(Persona personaRevisa) {
		this.personaRevisa = personaRevisa;
	}
	public Date getFechaElimina() {
		return fechaElimina;
	}
	public void setFechaElimina(Date fechaElimina) {
		this.fechaElimina = fechaElimina;
	}
	public Persona getPersonaElimina() {
		return personaElimina;
	}
	public void setPersonaElimina(Persona personaElimina) {
		this.personaElimina = personaElimina;
	}
	public Persona getDirectorAnterior() {
		return directorAnterior;
	}
	public void setDirectorAnterior(Persona directorAnterior) {
		this.directorAnterior = directorAnterior;
	}
	public Persona getDirectorNuevo() {
		return directorNuevo;
	}
	public void setDirectorNuevo(Persona directorNuevo) {
		this.directorNuevo = directorNuevo;
	}
	public String getAceptaPublicacion() {
		return aceptaPublicacion;
	}
	public void setAceptaPublicacion(String aceptaPublicacion) {
		this.aceptaPublicacion = aceptaPublicacion;
	}
	public DominioDetalle getEstadoAutoridad() {
		return estadoAutoridad;
	}
	public void setEstadoAutoridad(DominioDetalle estadoAutoridad) {
		this.estadoAutoridad = estadoAutoridad;
	}
	public String getComentariosAutoridad() {
		return comentariosAutoridad;
	}
	public void setComentariosAutoridad(String comentariosAutoridad) {
		this.comentariosAutoridad = comentariosAutoridad;
	}
	public Set<ColeccionRequisito> getRequisitos() {
		return requisitos;
	}
	public void setRequisitos(Set<ColeccionRequisito> requisitos) {
		this.requisitos = requisitos;
	}
    public ArrayList<ColeccionRequisito> getListaRequisitos() {
        ArrayList<ColeccionRequisito> lista = new ArrayList<ColeccionRequisito>();
        lista.addAll(requisitos);
        return lista;
    }
    
    public void adicionarRequisito(ColeccionRequisito ca) {
        ca.setGestion(this);
        requisitos.add(ca);
    }
	

}
