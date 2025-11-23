package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * The Class Grupo.
 */
public class Grupo implements Serializable {

	// Atributos de la clase Grupo, correspondientes a la tabla HER_GRUPO

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -15288144995155125L;

	public static final String VARIABLE_SESION_GRUPO = "grupo";

	/** The id. */
	private Long id;

	/**
	 * Son los estados en los cuales se puede encontrar un grupo. Dentro de
	 * estos están: Propuesto, Activo, Inactivo y Disuelto
	 */
	private EstadoGrupo estadoGrupo;

	/** The estado grupo colciencias. */
	private EstadoGrupoColciencias estadoGrupoColciencias;

	/** The dependencia. */
	private Dependencia dependencia;

	/** The sub area tematica. */
	private DominioDetalle subAreaTematica;

	/** The sede. */
	private Sede sede;

	/** The nombre. */
	private String nombre;

	/** The presentacion. */
	private String presentacion;

	/** The email. */
	private String email;

	/** The fecha creacion. */
	private Date fechaCreacion;

	/** The direccion. */
	private String direccion;

	/** The telefono. */
	private String telefono;

	/** The tel extension. */
	private String telExtension;

	/** The fax. */
	private String fax;

	/** The url. */
	private String url;

	/** The id colciencias. */
	private String idColciencias;

	/** The historia. */
	private String historia;

	/** The experiencia. */
	private String experiencia;

	/** The vision. */
	private String vision;

	/** The perspectiva. */
	private String perspectiva;

	/** The intersedes. */
	private String intersedes;

	/** The interfacultades. */
	private String interfacultades;

	/** The prioridad. */
	private String prioridad;

	/** The enlace_colciencias. */
	private String enlaceColciencias;

	/** The ano clasificacion colciencias. */
	private Integer anoClasificacionColciencias;

	/** The indice scienticol. */
	private Double indiceScienticol;

	/** The plan accion ver. */
	private boolean planAccionVer = false;

	/** The estado_menu. */
	private Integer estadoMenu;
	
	private String archivoAval;

	/** The categoria. */
	private CategoriaGrupo categoria;

	/** The responsable. */
	// Responsable del grupo
	private Investigador responsable;

	/** Proyectos asociados al grupo. */
	private Set<Proyecto> proyectos = new HashSet<Proyecto>();

	/** Dependencias asociadas al grupo. */
	private Set<Dependencia> dependencias = new HashSet<Dependencia>();

	/** The planes. */
	private Set<PlanEstudios> planes = new HashSet<PlanEstudios>();

	/**
	 * Investigadores asociados al grupo clasificados segun si es principal o
	 * no.
	 */
	private Set<InvestigadorGrupo> investigadoresGrupo = new HashSet<InvestigadorGrupo>();

	/** The lineas. */
	private Set<LineaInvestigacion> lineas = new HashSet<LineaInvestigacion>();

	/** The historico. */
	private Set<HistoricoEstadoGrupo> historico = new HashSet<HistoricoEstadoGrupo>();

	/** The elegible. */
	private boolean elegible = true;

	/** The publicaciones. */
	private Set<PublicacionGrupo> publicaciones = new HashSet<PublicacionGrupo>();

	/** The programas. */
	private Set<Programa> programas = new HashSet<Programa>();

	/** The clasificacion conocimiento. */
	private Set<ClasificacionConocimiento> clasificacionConocimiento = new HashSet<ClasificacionConocimiento>();

	/** The enlace gruplac. */
	private String enlaceGruplac;

	/** The agenda principal. */
	private PosibleAgendaGrupo agendaPrincipal;

	/** The areas tematicas. */
	private Set<AreaTematicaGrupo> areasTematicas = new HashSet<AreaTematicaGrupo>();
	
	private String objetivoDesarrolloSosteniblePrincipal;
	private String objetivoDesarrolloSostenibleSecundario;
	
	private String interinstitucion;
	private Set<GrupoInstitucion> instituciones = new HashSet<GrupoInstitucion>();
	private Set<GrupoLaboratorio> laboratorios = new HashSet<GrupoLaboratorio>();
	
	private String necesidad;
	private String pertinencia;
	private String explicacionGrupoSimilar;
	private String grupoSimilar;

	/**
	 * Adicionar investigador grupo.
	 *
	 * @param investigadorGrupo
	 *            the investigador grupo
	 */
	public void adicionarInvestigadorGrupo(InvestigadorGrupo investigadorGrupo) {
		investigadorGrupo.setGrupo(this);
		investigadoresGrupo.add(investigadorGrupo);
	}

	/**
	 * Borrar investigador grupo.
	 *
	 * @param investigadorGrupo
	 *            the investigador grupo
	 */
	public void borrarInvestigadorGrupo(InvestigadorGrupo investigadorGrupo) {
		investigadoresGrupo.remove(investigadorGrupo);
	}

	/**
	 * Adicionar linea investigacion.
	 *
	 * @param linea
	 *            the linea
	 */
	public void adicionarLineaInvestigacion(LineaInvestigacion linea) {
		boolean encontro = false;
		Iterator<LineaInvestigacion> it = lineas.iterator();
		while (it.hasNext()) {
			LineaInvestigacion l = (LineaInvestigacion) it.next();
			if (l.getId().equals(linea.getId())) {
				encontro = true;
				break;
			}
		}
		if (!encontro) {
			lineas.add(linea);
		}
	}

	/**
	 * Adicionar area tematica.
	 *
	 * @param area
	 *            the area
	 */
	public void adicionarAreaTematica(AreaTematicaGrupo area) {
		boolean encontro = false;
		Iterator<AreaTematicaGrupo> it = areasTematicas.iterator();
		while (it.hasNext()) {
			AreaTematicaGrupo atg = (AreaTematicaGrupo) it.next();
			if (atg.getAreaTematica().getIdentificador().equals(area.getAreaTematica().getIdentificador())) {
				encontro = true;
				break;
			}
		}
		if (!encontro) {
			areasTematicas.add(area);
		}
	}

	/**
	 * Borrar linea investigacion.
	 *
	 * @param linea
	 *            the linea
	 */
	public void borrarLineaInvestigacion(LineaInvestigacion linea) {
		lineas.remove(linea);
	}

	/**
	 * Borrar area.
	 *
	 * @param areaTematicaGrupo
	 *            the area tematica grupo
	 */
	public void borrarArea(AreaTematicaGrupo areaTematicaGrupo) {
		areasTematicas.remove(areaTematicaGrupo);
	}

	/**
	 * Gets the direccion.
	 *
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * Sets the direccion.
	 *
	 * @param direccion
	 *            the new direccion
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email
	 *            the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the nombre.
	 *
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Sets the nombre.
	 *
	 * @param nombre
	 *            the new nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Gets the presentacion.
	 *
	 * @return the presentacion
	 */
	public String getPresentacion() {
		return presentacion;
	}

	/**
	 * Sets the presentacion.
	 *
	 * @param presentacion
	 *            the new presentacion
	 */
	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}

	/**
	 * Gets the telefono.
	 *
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Sets the telefono.
	 *
	 * @param telefono
	 *            the new telefono
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * Gets the proyectos.
	 *
	 * @return the proyectos
	 */
	public Set<Proyecto> getProyectos() {
		return proyectos;
	}

	/**
	 * Sets the proyectos.
	 *
	 * @param proyectos
	 *            the new proyectos
	 */
	public void setProyectos(Set<Proyecto> proyectos) {
		this.proyectos = proyectos;
	}

	/**
	 * Gets the investigadores grupo.
	 *
	 * @return the investigadores grupo
	 */
	public Set<InvestigadorGrupo> getInvestigadoresGrupo() {
		return investigadoresGrupo;
	}
	
    public List<InvestigadorGrupo> getListaInvestigadoresGrupo() {
        List<InvestigadorGrupo> listaCC = new ArrayList<InvestigadorGrupo>();
        listaCC.addAll(investigadoresGrupo);
        return listaCC;
    }
    

	/**
	 * Sets the investigadores grupo.
	 *
	 * @param investigadoresGrupo
	 *            the new investigadores grupo
	 */
	public void setInvestigadoresGrupo(Set<InvestigadorGrupo> investigadoresGrupo) {
		this.investigadoresGrupo = investigadoresGrupo;
	}

	/**
	 * Gets the fax.
	 *
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * Sets the fax.
	 *
	 * @param fax
	 *            the new fax
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * Gets the id colciencias.
	 *
	 * @return the id colciencias
	 */
	public String getIdColciencias() {
		return idColciencias;
	}

	/**
	 * Sets the id colciencias.
	 *
	 * @param idColciencias
	 *            the new id colciencias
	 */
	public void setIdColciencias(String idColciencias) {
		this.idColciencias = idColciencias;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 *
	 * @param url
	 *            the new url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the fecha creacion.
	 *
	 * @return the fecha creacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * Sets the fecha creacion.
	 *
	 * @param fechaCreacion
	 *            the new fecha creacion
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * Gets the tel extension.
	 *
	 * @return the tel extension
	 */
	public String getTelExtension() {
		return telExtension;
	}

	/**
	 * Sets the tel extension.
	 *
	 * @param telExtension
	 *            the new tel extension
	 */
	public void setTelExtension(String telExtension) {
		this.telExtension = telExtension;
	}

	/**
	 * Gets the estado grupo.
	 *
	 * @return the estado grupo
	 */
	public EstadoGrupo getEstadoGrupo() {
		return estadoGrupo;
	}

	/**
	 * Sets the estado grupo.
	 *
	 * @param estadoGrupo
	 *            the new estado grupo
	 */
	public void setEstadoGrupo(EstadoGrupo estadoGrupo) {
		this.estadoGrupo = estadoGrupo;
	}

	/**
	 * Gets the lineas.
	 *
	 * @return the lineas
	 */
	public Set<LineaInvestigacion> getLineas() {
		return lineas;
	}

	/**
	 * Sets the lineas.
	 *
	 * @param lineas
	 *            the new lineas
	 */
	public void setLineas(Set<LineaInvestigacion> lineas) {
		this.lineas = lineas;
	}

	/**
	 * Gets the lista lineas.
	 *
	 * @return the lista lineas
	 */
	public List<LineaInvestigacion> getListaLineas() {
		List<LineaInvestigacion> listaLineas = new ArrayList<LineaInvestigacion>();
		listaLineas.addAll(lineas);
		return listaLineas;
	}

	/**
	 * Gets the lista areas.
	 *
	 * @return the lista areas
	 */
	public List<AreaTematicaGrupo> getListaAreas() {
		List<AreaTematicaGrupo> listaAreas = new ArrayList<AreaTematicaGrupo>();
		listaAreas.addAll(areasTematicas);
		return listaAreas;
	}

	/**
	 * Gets the lista dependencias.
	 *
	 * @return the lista dependencias
	 */
	public List<Dependencia> getListaDependencias() {
		List<Dependencia> listaDependencias = new ArrayList<Dependencia>();
		listaDependencias.addAll(dependencias);
		return listaDependencias;
	}

	/**
	 * Gets the lista planes.
	 *
	 * @return the lista planes
	 */
	public List<PlanEstudios> getListaPlanes() {
		List<PlanEstudios> listaPlanes = new ArrayList<PlanEstudios>();
		listaPlanes.addAll(planes);
		return listaPlanes;
	}

	/**
	 * Gets the historico.
	 *
	 * @return the historico
	 */
	public Set<HistoricoEstadoGrupo> getHistorico() {
		return historico;
	}

	/**
	 * Sets the historico.
	 *
	 * @param historico
	 *            the new historico
	 */
	public void setHistorico(Set<HistoricoEstadoGrupo> historico) {
		this.historico = historico;
	}

	/**
	 * Gets the responsable.
	 *
	 * @return the responsable
	 */
	public Investigador getResponsable() {
		Iterator<InvestigadorGrupo> it = this.investigadoresGrupo.iterator();
		while (it.hasNext()) {
			InvestigadorGrupo invG = (InvestigadorGrupo) it.next();
			if (invG.getTipo().equals(InvestigadorGrupo.LIDER)) {
				Investigador inv = invG.getInvestigador();
				if (inv != null) {
					responsable = inv;
				}
			}
		}
		return responsable;
	}

	/**
	 * Gets the lider.
	 *
	 * @return the lider
	 */
	public InvestigadorGrupo getLider() {
		InvestigadorGrupo lider = null;
		Iterator<InvestigadorGrupo> it = this.investigadoresGrupo.iterator();
		while (it.hasNext()) {
			InvestigadorGrupo invG = (InvestigadorGrupo) it.next();
			if (invG.getTipo().equals(InvestigadorGrupo.LIDER)) {
				Investigador inv = invG.getInvestigador();
				if (inv != null) {
					lider = invG;
				}
			}
		}
		return lider;
	}

	/**
	 * Sets the responsable.
	 *
	 * @param responsable
	 *            the new responsable
	 */
	public void setResponsable(Investigador responsable) {
		this.responsable = responsable;
	}

	/**
	 * Gets the experiencia.
	 *
	 * @return the experiencia
	 */
	public String getExperiencia() {
		return experiencia;
	}

	/**
	 * Sets the experiencia.
	 *
	 * @param experiencia
	 *            the new experiencia
	 */
	public void setExperiencia(String experiencia) {
		this.experiencia = experiencia;
	}

	/**
	 * Gets the historia.
	 *
	 * @return the historia
	 */
	public String getHistoria() {
		return historia;
	}

	/**
	 * Sets the historia.
	 *
	 * @param historia
	 *            the new historia
	 */
	public void setHistoria(String historia) {
		this.historia = historia;
	}

	/**
	 * Gets the ano clasificacion colciencias.
	 *
	 * @return the ano clasificacion colciencias
	 */
	public Integer getAnoClasificacionColciencias() {
		return anoClasificacionColciencias;
	}

	/**
	 * Sets the ano clasificacion colciencias.
	 *
	 * @param anoClasificacionColciencias
	 *            the new ano clasificacion colciencias
	 */
	public void setAnoClasificacionColciencias(Integer anoClasificacionColciencias) {
		this.anoClasificacionColciencias = anoClasificacionColciencias;
	}

	/**
	 * Gets the indice scienticol.
	 *
	 * @return the indice scienticol
	 */
	public Double getIndiceScienticol() {
		return indiceScienticol;
	}

	/**
	 * Sets the indice scienticol.
	 *
	 * @param indiceScienticol
	 *            the new indice scienticol
	 */
	public void setIndiceScienticol(Double indiceScienticol) {
		this.indiceScienticol = indiceScienticol;
	}

	/**
	 * Gets the categoria.
	 *
	 * @return the categoria
	 */
	public CategoriaGrupo getCategoria() {
		return categoria;
	}

	/**
	 * Sets the categoria.
	 *
	 * @param categoria
	 *            the new categoria
	 */
	public void setCategoria(CategoriaGrupo categoria) {
		this.categoria = categoria;
	}

	/**
	 * Gets the dependencia.
	 *
	 * @return the dependencia
	 */
	public Dependencia getDependencia() {
		return dependencia;
	}

	/**
	 * Sets the dependencia.
	 *
	 * @param dependencia
	 *            the new dependencia
	 */
	public void setDependencia(Dependencia dependencia) {
		this.dependencia = dependencia;
	}

	/**
	 * Checks if is elegible.
	 *
	 * @return true, if is elegible
	 */
	public boolean isElegible() {
		return elegible;
	}

	/**
	 * Sets the elegible.
	 *
	 * @param elegible
	 *            the new elegible
	 */
	public void setElegible(boolean elegible) {
		this.elegible = elegible;
	}

	/**
	 * Gets the programas.
	 *
	 * @return the programas
	 */
	public Set<Programa> getProgramas() {
		return programas;
	}

	/**
	 * Sets the programas.
	 *
	 * @param programas
	 *            the new programas
	 */
	public void setProgramas(Set<Programa> programas) {
		this.programas = programas;
	}

	/**
	 * Gets the publicaciones.
	 *
	 * @return the publicaciones
	 */
	public Set<PublicacionGrupo> getPublicaciones() {
		return publicaciones;
	}

	/**
	 * Sets the publicaciones.
	 *
	 * @param publicaciones
	 *            the new publicaciones
	 */
	public void setPublicaciones(Set<PublicacionGrupo> publicaciones) {
		this.publicaciones = publicaciones;
	}

	/**
	 * Gets the perspectiva.
	 *
	 * @return the perspectiva
	 */
	public String getPerspectiva() {
		return perspectiva;
	}

	/**
	 * Sets the perspectiva.
	 *
	 * @param perspectiva
	 *            the new perspectiva
	 */
	public void setPerspectiva(String perspectiva) {
		this.perspectiva = perspectiva;
	}

	/**
	 * Gets the prioridad.
	 *
	 * @return the prioridad
	 */
	public String getPrioridad() {
		return prioridad;
	}

	/**
	 * Sets the prioridad.
	 *
	 * @param prioridad
	 *            the new prioridad
	 */
	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}

	/**
	 * Gets the vision.
	 *
	 * @return the vision
	 */
	public String getVision() {
		return vision;
	}

	/**
	 * Sets the vision.
	 *
	 * @param vision
	 *            the new vision
	 */
	public void setVision(String vision) {
		this.vision = vision;
	}

	/**
	 * Gets the lista publicaciones.
	 *
	 * @return the lista publicaciones
	 */
	public List<PublicacionGrupo> getListaPublicaciones() {
		return new Vector<PublicacionGrupo>(publicaciones);
	}

	/**
	 * Gets the lista programas.
	 *
	 * @return the lista programas
	 */
	public List<Programa> getListaProgramas() {
		return new Vector<Programa>(programas);
	}

	/**
	 * Gets the lista proyectos.
	 *
	 * @return the lista proyectos
	 */
	public List<Proyecto> getListaProyectos() {
		List<Proyecto> proyectos = new ArrayList<Proyecto>();
		proyectos.addAll(this.proyectos);
		return proyectos;
	}

	/**
	 * Gets the clasificacion conocimiento.
	 *
	 * @return the clasificacion conocimiento
	 */
	public Set<ClasificacionConocimiento> getClasificacionConocimiento() {
		return clasificacionConocimiento;
	}

	/**
	 * Sets the clasificacion conocimiento.
	 *
	 * @param clasificacionConocimiento
	 *            the new clasificacion conocimiento
	 */
	public void setClasificacionConocimiento(Set<ClasificacionConocimiento> clasificacionConocimiento) {
		this.clasificacionConocimiento = clasificacionConocimiento;
	}

	/**
	 * Gets the dependencias.
	 *
	 * @return the dependencias
	 */
	public Set<Dependencia> getDependencias() {
		return dependencias;
	}

	/**
	 * Sets the dependencias.
	 *
	 * @param dependencias
	 *            the new dependencias
	 */
	public void setDependencias(Set<Dependencia> dependencias) {
		this.dependencias = dependencias;
	}

	/**
	 * Adicionar dependencia.
	 *
	 * @param dependencia
	 *            the dependencia
	 */
	public void adicionarDependencia(Dependencia dependencia) {
		this.dependencias.add(dependencia);
	}

	/**
	 * Borrar dependencia.
	 *
	 * @param dependencia
	 *            the dependencia
	 */
	public void borrarDependencia(Dependencia dependencia) {
		this.dependencias.remove(dependencia);
	}

	/**
	 * Borrar plan.
	 *
	 * @param planEstudios
	 *            the plan estudios
	 */
	public void borrarPlan(PlanEstudios planEstudios) {
		this.planes.remove(planEstudios);
	}

	/**
	 * Adicionar plan.
	 *
	 * @param planEstudios
	 *            the plan estudios
	 */
	public void adicionarPlan(PlanEstudios planEstudios) {
		this.planes.add(planEstudios);
	}

	/**
	 * Gets the plan.
	 *
	 * @return the plan
	 */
	public boolean getPlan() {
		return planAccionVer;
	}

	/**
	 * Sets the plan accion ver.
	 *
	 * @param planAccionVer
	 *            the new plan accion ver
	 */
	public void setPlanAccionVer(boolean planAccionVer) {
		this.planAccionVer = planAccionVer;
	}

	/**
	 * Gets the enlace_colciencias.
	 *
	 * @return the enlace_colciencias
	 */
	public String getEnlaceColciencias() {
		return enlaceColciencias;
	}

	/**
	 * Sets the enlace_colciencias.
	 *
	 * @param enlaceColciencias
	 *            the new enlace_colciencias
	 */
	public void setEnlaceColciencias(String enlaceColciencias) {
		this.enlaceColciencias = enlaceColciencias;
	}

	/**
	 * Gets the estado_menu.
	 *
	 * @return the estado_menu
	 */
	public int getEstadoMenu() {
		return estadoMenu;
	}

	/**
	 * Sets the estado_menu.
	 *
	 * @param estadoMenu
	 *            the new estado_menu
	 */
	public void setEstadoMenu(Integer estadoMenu) {
		this.estadoMenu = estadoMenu;
	}

	/**
	 * Gets the planes.
	 *
	 * @return the planes
	 */
	public Set<PlanEstudios> getPlanes() {
		return planes;
	}

	/**
	 * Sets the planes.
	 *
	 * @param planes
	 *            the new planes
	 */
	public void setPlanes(Set<PlanEstudios> planes) {
		this.planes = planes;
	}

	/**
	 * Gets the enlace gruplac.
	 *
	 * @return the enlace gruplac
	 */
	public String getEnlaceGruplac() {
		return enlaceGruplac;
	}

	/**
	 * Sets the enlace gruplac.
	 *
	 * @param enlaceGruplac
	 *            the new enlace gruplac
	 */
	public void setEnlaceGruplac(String enlaceGruplac) {
		this.enlaceGruplac = enlaceGruplac;
	}

	/**
	 * Gets the sede.
	 *
	 * @return the sede
	 */
	public Sede getSede() {
		return sede;
	}

	/**
	 * Sets the sede.
	 *
	 * @param sede
	 *            the new sede
	 */
	public void setSede(Sede sede) {
		this.sede = sede;
	}

	/**
	 * Gets the intersedes.
	 *
	 * @return the intersedes
	 */
	public String getIntersedes() {
		return intersedes;
	}

	/**
	 * Sets the intersedes.
	 *
	 * @param intersedes
	 *            the new intersedes
	 */
	public void setIntersedes(String intersedes) {
		this.intersedes = intersedes;
	}

	/**
	 * Gets the interfacultades.
	 *
	 * @return the interfacultades
	 */
	public String getInterfacultades() {
		return interfacultades;
	}

	/**
	 * Sets the interfacultades.
	 *
	 * @param interfacultades
	 *            the new interfacultades
	 */
	public void setInterfacultades(String interfacultades) {
		this.interfacultades = interfacultades;
	}

	/**
	 * Gets the estado grupo colciencias.
	 *
	 * @return the estado grupo colciencias
	 */
	public EstadoGrupoColciencias getEstadoGrupoColciencias() {
		return estadoGrupoColciencias;
	}

	/**
	 * Sets the estado grupo colciencias.
	 *
	 * @param estadoGrupoColciencias
	 *            the new estado grupo colciencias
	 */
	public void setEstadoGrupoColciencias(EstadoGrupoColciencias estadoGrupoColciencias) {
		this.estadoGrupoColciencias = estadoGrupoColciencias;
	}

	/**
	 * Gets the areas tematicas.
	 *
	 * @return the areas tematicas
	 */
	public Set<AreaTematicaGrupo> getAreasTematicas() {
		return areasTematicas;
	}

	/**
	 * Sets the areas tematicas.
	 *
	 * @param areasTematicas
	 *            the new areas tematicas
	 */
	public void setAreasTematicas(Set<AreaTematicaGrupo> areasTematicas) {
		this.areasTematicas = areasTematicas;
	}

	/**
	 * Gets the sub area tematica.
	 *
	 * @return the sub area tematica
	 */
	public DominioDetalle getSubAreaTematica() {
		return subAreaTematica;
	}

	/**
	 * Sets the sub area tematica.
	 *
	 * @param subAreaTematica
	 *            the new sub area tematica
	 */
	public void setSubAreaTematica(DominioDetalle subAreaTematica) {
		this.subAreaTematica = subAreaTematica;
	}

	/**
	 * Gets the agenda principal.
	 *
	 * @return the agenda principal
	 */
	public PosibleAgendaGrupo getAgendaPrincipal() {
		return agendaPrincipal;
	}

	/**
	 * Sets the agenda principal.
	 *
	 * @param agendaPrincipal
	 *            the new agenda principal
	 */
	public void setAgendaPrincipal(PosibleAgendaGrupo agendaPrincipal) {
		this.agendaPrincipal = agendaPrincipal;
	}

	/**
	 * Asignar nuevo estado menu.
	 *
	 * @param estado
	 *            the estado
	 */
	public void asignarNuevoEstadoMenu(int estado) {
		if (getEstadoMenu() < estado) {
			setEstadoMenu(estado);
		}
	}

	public boolean equals(Object object) {
		if (object instanceof Grupo) {
			Grupo grupo = (Grupo) object;
			if (this.id.equals(grupo.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
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

	public String getInterinstitucion() {
		return interinstitucion;
	}

	public void setInterinstitucion(String interinstitucion) {
		this.interinstitucion = interinstitucion;
	}

	public Set<GrupoInstitucion> getInstituciones() {
		return instituciones;
	}

	public void setInstituciones(Set<GrupoInstitucion> instituciones) {
		this.instituciones = instituciones;
	}
	
	public ArrayList<GrupoInstitucion> getListaInstituciones() {
		ArrayList<GrupoInstitucion> retVal = new ArrayList<GrupoInstitucion>();
		for (GrupoInstitucion gi : getInstituciones()) {
			retVal.add(gi);
		}
		return retVal;
	}

	public Set<GrupoLaboratorio> getLaboratorios() {
		return laboratorios;
	}

	public void setLaboratorios(Set<GrupoLaboratorio> laboratorios) {
		this.laboratorios = laboratorios;
	}

	public ArrayList<GrupoLaboratorio> getListaLaboratorios() {
		ArrayList<GrupoLaboratorio> retVal = new ArrayList<GrupoLaboratorio>();
		for (GrupoLaboratorio gl : getLaboratorios()) {
			retVal.add(gl);
		}
		return retVal;
	}

	public boolean esInterfacultades() {
		if(this.interfacultades!=null && this.interfacultades.equals("S")) {
			return true;
		}
		return false;
	}
	
	public boolean esIntersedes() {
		if(this.intersedes!=null && this.intersedes.equals("S")) {
			return true;
		}
		return false;
	}
	
	public boolean esInsterinstitucional() {
		if(this.interinstitucion!=null && this.interinstitucion.equals("S")) {
			return true;
		}
		return false;
	}

	public String getNecesidad() {
		return necesidad;
	}

	public void setNecesidad(String necesidad) {
		this.necesidad = necesidad;
	}

	public String getPertinencia() {
		return pertinencia;
	}

	public void setPertinencia(String pertinencia) {
		this.pertinencia = pertinencia;
	}

	public String getExplicacionGrupoSimilar() {
		return explicacionGrupoSimilar;
	}

	public void setExplicacionGrupoSimilar(String explicacionGrupoSimilar) {
		this.explicacionGrupoSimilar = explicacionGrupoSimilar;
	}

	public String getGrupoSimilar() {
		return grupoSimilar;
	}

	public void setGrupoSimilar(String grupoSimilar) {
		this.grupoSimilar = grupoSimilar;
	}

	public String getArchivoAval() {
		return archivoAval;
	}

	public void setArchivoAval(String archivoAval) {
		this.archivoAval = archivoAval;
	}


}