/**
 * Modificado por Mauricio
 */

package co.edu.unal.hermes.vista.grupos;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import co.edu.unal.hermes.modelo.AreaTematicaGrupo;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EstadoGrupo;
import co.edu.unal.hermes.modelo.Estudiante;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.GrupoAgenda;
import co.edu.unal.hermes.modelo.GrupoIntersedes;
import co.edu.unal.hermes.modelo.GrupoProductoSara;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorGrupo;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PlanEstudios;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * The Class ManejadorGrupoBusqueda.
 */
public class ManejadorGrupoBusqueda extends ManejadorBase {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6626227945403608239L;

	/** The Constant VARIABLE_SESION_IMAGEN_GRUPO. */
	private static final String VARIABLE_SESION_IMAGEN_GRUPO = "imagenGrupo";

	/** The Constant PARAMETRO_ID_GRUPO. */
	private static final String PARAMETRO_ID_GRUPO = "idGrupo";

	/** The opcion. */
	private int opcion = 1;

	/** The pagina actual. */
	private String paginaActual;

	/** The grupo actual. */
	private Grupo grupoActual = null;

	/** The nombre grupo. */
	private String nombreGrupo = "";

	/** The proyecto actual. */
	private Proyecto proyectoActual;

	/** The lider. */
	private InvestigadorGrupo lider = null;

	/** The nombre responsable. */
	private String nombreResponsable;

	/** The lista integrantes estudiantes doctorado. */
	private List<InvestigadorGrupo> listaIntegrantesEstudiantesDoctorado;

	/** The lista integrantes estudiantes maestria. */
	private List<InvestigadorGrupo> listaIntegrantesEstudiantesMaestria;

	/** The lista integrantes estudiantes pregrado. */
	private List<InvestigadorGrupo> listaIntegrantesEstudiantesPregrado;

	/** The lista integrantes estudiantes visistantes. */
	private List<InvestigadorGrupo> listaIntegrantesEstudiantesVisitantes;

	/** The lista integrantes estudiantes visistantes. */
	private List<InvestigadorGrupo> listaIntegrantesEstudiantesLider;

	/** The lista integrantes codirecto. */
	private List<InvestigadorGrupo> listaIntegrantesCodirecto;

	/** The lista integrantes docente. */
	private List<InvestigadorGrupo> listaIntegrantesDocente;

	/** The lista integrantes externo. */
	private List<InvestigadorGrupo> listaIntegrantesExterno;

	/** The lista sedes grupo. */
	private List<GrupoIntersedes> listaSedesGrupo;

	/** The lista agendas grupo. */
	private List<GrupoAgenda> listaAgendasGrupo;

	/** The lista productos grupo. */
	private List<GrupoProductoSara> listaProductosGrupo;

	/** The es intersede. */
	private boolean esIntersede = false;

	/** The visible planes. */
	private boolean visiblePlanes = false;

	/** The visible otras agendas. */
	private boolean visibleOtrasAgendas = false;

	/** The visible otras areas. */
	private boolean visibleOtrasAreas = false;

	/** The visible productos. */
	private boolean visibleProductos = false;

	/** The area ciencia. */
	private String areaCiencia;

	/** The error. */
	private boolean error;

	/** The persona seleccionada. */
	private Persona personaSeleccionada;

	/** The email persona. */
	private String emailPersona;

	/** The id grupo. */
	private Long idGrupo;

	/** The imagen. */
	private StreamedContent imagen;

	/** The actual. */
	File actual;

	/** The path. */
	String path = RUTA_ARCHIVOS + File.separator + "HER_GRUPO" + File.separator;

	/** The bandera. */
	boolean bandera = true;
	
	private List<InvestigadorGrupo> listaEgresados;


	/**
	 * Instantiates a new manejador grupo busqueda.
	 *
	 * @throws SQLException
	 *             the SQL exception
	 */
	public ManejadorGrupoBusqueda() throws SQLException {
		nombreResponsable = "";

		if (idGrupo != null) {
			grupoActual = servicioGrupo.obtenerGrupoDatosBasicos(idGrupo);

			reporteGrupoBusqueda();
			idGrupo = null;

		} else {

			cargarTipoPagina();

			cargarGrupo();

			if (!error) {

				cargarNombreGrupo();
				cargarLider();

				int arroba = lider.getInvestigador().getEmail().indexOf("@");
				if (arroba != -1) {

					setEmailPersona(lider.getInvestigador().getEmail().substring(0, arroba));
				} else {
					setEmailPersona(lider.getInvestigador().getEmail());
				}
				cargarSedesGrupo();
				cargarPlanesGrupo();
				cargarAgendasGrupo();
				cargarAreasOCDE();
				cargarProductosGrupo();
				cargarListasIntegrantes();
				emailDocentes();
				programaEstudiantes();

				cargarPaginaActual();

			}
		}

	}

	/**
	 * Buscar estudiante.
	 *
	 * @param ig
	 *            the ig
	 * @return the estudiante
	 * @throws SQLException
	 *             the SQL exception
	 */
	public Estudiante buscarEstudiante(InvestigadorGrupo ig) throws SQLException {
		Estudiante estudiante;
		List<Estudiante> est = servicioGeneral.obtenerListaObjetosWhere("Estudiante e",
				"where e.id.documento='" + ig.getInvestigador().getId().getDocumento() + "' and e.id.tipoDocumento='"
						+ ig.getInvestigador().getId().getTipoDocumento() + "'");
		if (!esListaVacia(est)) {
			estudiante = est.get(0);
			return estudiante;
		}
		return null;
	}

	/**
	 * Cargar agendas grupo.
	 */
	public void cargarAgendasGrupo() {
		listaAgendasGrupo = servicioGeneral.obtenerObjetos(GrupoAgenda.class,
				"from GrupoAgenda ga where ga.grupo.id = '" + grupoActual.getId() + "'");

		if (!esListaVacia(listaAgendasGrupo)) {
			visibleOtrasAgendas = true;
		}
	}

	/**
	 * Cargar areas ocde.
	 */
	public void cargarAreasOCDE() {

		String consulta = "select dd from Dominio d, DominioDetalle dd "
				+ "where d.id = dd.identificador.id and d.tipo ='AREA_CIENCIA'";

		List<DominioDetalle> listaAreaCiencia = servicioGeneral.obtenerObjetos(DominioDetalle.class, consulta);

		Iterator<DominioDetalle> a = listaAreaCiencia.iterator();
		if (!esListaVacia(listaAreaCiencia)) {
			while (a.hasNext()) {
				DominioDetalle dominioDetalle = a.next();
				if (grupoActual.getSubAreaTematica() != null && grupoActual.getSubAreaTematica().getEstado()
						.equals(dominioDetalle.getIdentificador().getTipo())) {
					areaCiencia = dominioDetalle.getDescripcion();
					break;
				}
			}
		}

		if (!esListaVacia(grupoActual.getListaAreas())) {

			Iterator<AreaTematicaGrupo> i = grupoActual.getListaAreas().iterator();
			while (i.hasNext()) {
				AreaTematicaGrupo areaTematicaGrupo = i.next();
				Iterator<DominioDetalle> j = listaAreaCiencia.iterator();
				while (j.hasNext()) {
					DominioDetalle dominioDetalle = j.next();
					if (areaTematicaGrupo.getAreaTematica().getEstado()
							.equals(dominioDetalle.getIdentificador().getTipo())) {
						areaTematicaGrupo.setAreaTematicaPadre(dominioDetalle);
					}
				}
			}
			visibleOtrasAreas = true;
		}
	}

	/**
	 * Cargar grupo.
	 */
	private void cargarGrupo() {
		sesion = request.getSession();
		this.error = false;
		if (StringUtils.isNotEmpty(this.request.getParameter(PARAMETRO_ID_GRUPO))) {
			try {
				grupoActual = servicioGrupo
						.obtenerGrupoDatosBasicos(new Long(this.request.getParameter(PARAMETRO_ID_GRUPO)));
				actual = new File(path + grupoActual.getId() + ".jpg");
				
				if(actual!=null && actual.exists()) {
					imagen = new DefaultStreamedContent(
							new ByteArrayInputStream(org.apache.commons.io.FileUtils.readFileToByteArray(actual)),
							"image/jpg");
				}

				if (imagen != null) {
					sesion.setAttribute(VARIABLE_SESION_IMAGEN_GRUPO, imagen);
				}

			} catch (NumberFormatException nfe) {
				this.error = true;
			} catch (Exception i) {
				i.printStackTrace();
				bandera = false;
			}
			if (grupoActual == null
					|| (grupoActual != null && !grupoActual.getEstadoGrupo().getId().equals(EstadoGrupo.ACTIVO))) {
				this.error = true;
			}
		} else {
			grupoActual = (Grupo) sesion.getAttribute("grupoBusqueda");
			sesion.removeAttribute("grupoBusqueda");
			if (grupoActual == null
					|| (grupoActual != null && !grupoActual.getEstadoGrupo().getId().equals(EstadoGrupo.ACTIVO))) {
				this.error = true;
			}
		}

		if (grupoActual != null && !error) {
			actual = new File(path + grupoActual.getId() + ".jpg");
			if (actual.exists()) {
				bandera = true;
			} else {
				bandera = false;
			}
		}

		if (this.error) {
			this.opcion = 0;
		}
	}

	/**
	 * Cargar lider.
	 */
	private void cargarLider() {
		lider = grupoActual.getLider();
		if (lider != null && lider.getInvestigador().getNombre2() == null) {
			lider.getInvestigador().setNombre2("");
		}
	}

	/**
	 * Cargar listas integrantes.
	 */
	public void cargarListasIntegrantes() {
		listaIntegrantesCodirecto = new ArrayList<InvestigadorGrupo>();
		listaIntegrantesDocente = new ArrayList<InvestigadorGrupo>();
		listaIntegrantesEstudiantesDoctorado = new ArrayList<InvestigadorGrupo>();
		listaIntegrantesEstudiantesMaestria = new ArrayList<InvestigadorGrupo>();
		listaIntegrantesEstudiantesPregrado = new ArrayList<InvestigadorGrupo>();
		listaIntegrantesEstudiantesVisitantes = new ArrayList<InvestigadorGrupo>();
		listaIntegrantesEstudiantesLider = new ArrayList<InvestigadorGrupo>();
		listaIntegrantesExterno = new ArrayList<InvestigadorGrupo>();
		listaEgresados=new ArrayList<InvestigadorGrupo>();
		int doctorado = 7;
		int especializacion = 4;
		int especialidad = 5;
		int maestria = 6;
		int pregrado = 3;
		String tipoInvestigador;
		Iterator<InvestigadorGrupo> it = grupoActual.getInvestigadoresGrupo().iterator();
		while (it.hasNext()) {
			InvestigadorGrupo ig = it.next();
			tipoInvestigador = ig.getTipo();
			if ("E".equals(
					tipoInvestigador)/*
										 * || ig.getInvestigador().getInterno().
										 * equals("N")
										 */) {
				listaIntegrantesExterno.add(ig);
			} else if ("O".equals(tipoInvestigador)) {
				listaEgresados.add(ig);
			} else if ("D".equals(tipoInvestigador)) {
				listaIntegrantesDocente.add(ig);
			} else if ("C".equals(tipoInvestigador)) {
				listaIntegrantesCodirecto.add(ig);
			} else if ("A".equals(tipoInvestigador) || "AL".equals(tipoInvestigador) || "AV".equals(tipoInvestigador)) {
				try {
					Estudiante estudiante = buscarEstudiante(ig);
					if (estudiante != null) {
						if (estudiante.getPlan() != null) {
							if ("AL".equals(tipoInvestigador)) {
								listaIntegrantesEstudiantesLider.add(ig);
							} else if (estudiante.getPlan().getTipo() == doctorado) {
								listaIntegrantesEstudiantesDoctorado.add(ig);
							} else if (estudiante.getPlan().getTipo() == maestria
									|| estudiante.getPlan().getTipo() == especializacion
									|| estudiante.getPlan().getTipo() == especialidad) {
								listaIntegrantesEstudiantesMaestria.add(ig);
							} else if (estudiante.getPlan().getTipo() == pregrado) {
								listaIntegrantesEstudiantesPregrado.add(ig);
							} else if (estudiante.getPlan().getTipo() == 8) {
								listaIntegrantesEstudiantesVisitantes.add(ig);
							}
						}
					}
				} catch (SQLException se) {
					System.out.println(se.getMessage());
				}
			}
		}
	}

	/**
	 * Cargar nombre grupo.
	 */
	private void cargarNombreGrupo() {
		nombreGrupo = grupoActual.getNombre();
	}

	/**
	 * Cargar pagina actual.
	 */
	private void cargarPaginaActual() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext extContext = context.getExternalContext();

		String viewId = "/pages/Consultas/Grupo.jsf";

		viewId = extContext.getRequestContextPath() + viewId + '?' + PARAMETRO_ID_GRUPO + "=" + grupoActual.getId();

		this.paginaActual = context.getExternalContext().encodeActionURL(viewId);
	}

	/**
	 * Cargar planes grupo.
	 */
	public void cargarPlanesGrupo() {
		if (!esListaVacia(new ArrayList<PlanEstudios>(grupoActual.getPlanes()))) {
			visiblePlanes = true;
		}
	}

	/**
	 * Cargar productos grupo.
	 */
	public void cargarProductosGrupo() {
		setListaProductosGrupo(new ArrayList<GrupoProductoSara>());
		setListaProductosGrupo(servicioGeneral.obtenerObjetosLimitado(GrupoProductoSara.class,
				"select #nombreProducto gps.nombreProducto, #tipoProducto gps.tipoProducto from GrupoProductoSara gps where "
						+ " gps.grupo.id = '" + grupoActual.getId() + "' order by gps.tipoProducto asc"));

		if (!esListaVacia(listaProductosGrupo)) {
			visibleProductos = true;
		}
	}

	/**
	 * Cargar sedes grupo.
	 */
	public void cargarSedesGrupo() {
		listaSedesGrupo = servicioGeneral.obtenerObjetosLimitado(GrupoIntersedes.class,
				"select #sede s from GrupoIntersedes g, Sede s where " + " g.grupo.id = '" + grupoActual.getId()
						+ "' and g.sede.id = s.id");

		if (!esListaVacia(listaSedesGrupo) && grupoActual.getIntersedes() != null
				&& grupoActual.getIntersedes().equals("S")) {
			esIntersede = true;
		}
	}

	/**
	 * Cargar tipo pagina.
	 */
	private void cargarTipoPagina() {
		if (StringUtils.isNotEmpty(this.request.getParameter("opcion"))) {
			try {
				this.opcion = Integer.parseInt(this.request.getParameter("opcion"));
				if (this.opcion > 4 || this.opcion < 1)
					this.opcion = 1;
			} catch (NumberFormatException nfe) {
				this.opcion = 1;
			}
		} else if (sesion.getAttribute("opcionGrupos") != null) {
			try {
				this.opcion = (Integer) sesion.getAttribute("opcionGrupos");
				if (this.opcion > 4 || this.opcion < 1)
					this.opcion = 1;
			} catch (NumberFormatException nfe) {
				this.opcion = 1;
			}
		}
	}

	/**
	 * Email docentes.
	 */
	public void emailDocentes() {
		String emailDocente;
		if (listaIntegrantesDocente != null) {
			for (int i = 0; i < listaIntegrantesDocente.size(); i++) {
				InvestigadorGrupo ig = listaIntegrantesDocente.get(i);
				emailDocente = ig.getInvestigador().getEmail();
				int arroba = emailDocente.indexOf("@");
				if (arroba != -1) {
					ig.getInvestigador().setEmail(emailDocente.substring(0, arroba));
				}
			}
		}
	}

	/**
	 * Gets the actual.
	 *
	 * @return the actual
	 */
	public File getActual() {
		return actual;
	}

	/**
	 * Gets the area ciencia.
	 *
	 * @return the area ciencia
	 */
	public String getAreaCiencia() {
		return areaCiencia;
	}

	/**
	 * Gets the buscar lider.
	 *
	 * @return the buscar lider
	 */
	public String getBuscarLider() {
		if (lider != null) {
			Investigador investigador = lider.getInvestigador();
			investigador = servicioPersona.obtenerProyectosGruposInvestigador(investigador.getId());
			InvestigadorInterno investigadorInterno = new InvestigadorInterno();
			investigadorInterno = servicioPersona.obtenerInvestigadorClasificacionConocimiento(investigador.getId());
			investigador.setClasificacionesConocimiento(investigadorInterno.getClasificacionesConocimiento());
			if (investigador != null) {
				sesion.setAttribute("investigadorBusqueda", investigador);
				return "successPersonaBuscador";
			} else
				return "";
		} else
			return "";
	}

	/**
	 * Gets the email persona.
	 *
	 * @return the email persona
	 */
	public String getEmailPersona() {
		return emailPersona;
	}

	/**
	 * Gets the grupo actual.
	 *
	 * @return the grupo actual
	 */
	public Grupo getGrupoActual() {
		return grupoActual;
	}

	/**
	 * Gets the id grupo.
	 *
	 * @return the id grupo
	 */
	public Long getIdGrupo() {
		return idGrupo;
	}

	/**
	 * Gets the imagen.
	 *
	 * @return the imagen
	 */
	public StreamedContent getImagen() {
		StreamedContent imagen2 = null;
		try {
			imagen2 = (StreamedContent) sesion.getAttribute(VARIABLE_SESION_IMAGEN_GRUPO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (imagen2 != null) {
			return imagen2;
		} else {
			try {
				imagen = new DefaultStreamedContent(
						new ByteArrayInputStream(org.apache.commons.io.FileUtils.readFileToByteArray(actual)),
						"image/jpg");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return imagen;
		}

	}

	/**
	 * Gets the informacion basica.
	 *
	 * @return the informacion basica
	 */
	public String getInformacionBasica() {
		return this.paginaActual + "&opcion=1";
	}

	/**
	 * Gets the integrantes.
	 *
	 * @return the integrantes
	 */
	public String getIntegrantes() {
		return this.paginaActual + "&opcion=2";
	}

	/**
	 * Gets the lider.
	 *
	 * @return the lider
	 */
	public InvestigadorGrupo getLider() {
		return lider;
	}

	/**
	 * Gets the lista agendas grupo.
	 *
	 * @return the lista agendas grupo
	 */
	public List<GrupoAgenda> getListaAgendasGrupo() {
		return listaAgendasGrupo;
	}

	/**
	 * Gets the lista integrantes codirecto.
	 *
	 * @return the lista integrantes codirecto
	 */
	public List<InvestigadorGrupo> getListaIntegrantesCodirecto() {
		return listaIntegrantesCodirecto;
	}

	/**
	 * Gets the lista integrantes docente.
	 *
	 * @return the lista integrantes docente
	 */
	public List<InvestigadorGrupo> getListaIntegrantesDocente() {
		return listaIntegrantesDocente;
	}

	/**
	 * Gets the lista integrantes estudiante doctorado.
	 *
	 * @return the lista integrantes estudiante doctorado
	 */
	public List<InvestigadorGrupo> getListaIntegrantesEstudianteDoctorado() {
		return listaIntegrantesEstudiantesDoctorado;
	}

	/**
	 * Gets the lista integrantes estudiante maestria.
	 *
	 * @return the lista integrantes estudiante maestria
	 */
	public List<InvestigadorGrupo> getListaIntegrantesEstudianteMaestria() {
		return listaIntegrantesEstudiantesMaestria;
	}

	/**
	 * Gets the lista integrantes estudiante pregrado.
	 *
	 * @return the lista integrantes estudiante pregrado
	 */
	public List<InvestigadorGrupo> getListaIntegrantesEstudiantePregrado() {
		return listaIntegrantesEstudiantesPregrado;
	}

	/**
	 * Gets the lista integrantes estudiantes doctorado.
	 *
	 * @return the lista integrantes estudiantes doctorado
	 */
	public List<InvestigadorGrupo> getListaIntegrantesEstudiantesDoctorado() {
		return listaIntegrantesEstudiantesDoctorado;
	}

	/**
	 * Gets the lista integrantes estudiantes maestria.
	 *
	 * @return the lista integrantes estudiantes maestria
	 */
	public List<InvestigadorGrupo> getListaIntegrantesEstudiantesMaestria() {
		return listaIntegrantesEstudiantesMaestria;
	}

	/**
	 * Gets the lista integrantes estudiantes pregrado.
	 *
	 * @return the lista integrantes estudiantes pregrado
	 */
	public List<InvestigadorGrupo> getListaIntegrantesEstudiantesPregrado() {
		return listaIntegrantesEstudiantesPregrado;
	}

	/**
	 * Gets the lista integrantes externo.
	 *
	 * @return the lista integrantes externo
	 */
	public List<InvestigadorGrupo> getListaIntegrantesExterno() {
		return listaIntegrantesExterno;
	}

	/**
	 * Gets the lista productos grupo.
	 *
	 * @return the lista productos grupo
	 */
	public List<GrupoProductoSara> getListaProductosGrupo() {
		return listaProductosGrupo;
	}

	/**
	 * Gets the lista sedes grupo.
	 *
	 * @return the lista sedes grupo
	 */
	public List<GrupoIntersedes> getListaSedesGrupo() {
		return listaSedesGrupo;
	}

	/**
	 * Gets the nombre grupo.
	 *
	 * @return the nombre grupo
	 */
	public String getNombreGrupo() {
		return nombreGrupo;
	}

	/**
	 * Gets the nombre responsable.
	 *
	 * @return the nombre responsable
	 */
	public String getNombreResponsable() {
		return nombreResponsable;
	}

	/**
	 * Gets the opcion1.
	 *
	 * @return the opcion1
	 */
	public boolean getOpcion1() {
		if (this.opcion == 1)
			return true;
		return false;
	}

	/**
	 * Gets the opcion2.
	 *
	 * @return the opcion2
	 */
	public boolean getOpcion2() {
		if (this.opcion == 2)
			return true;
		return false;
	}

	/**
	 * Gets the opcion3.
	 *
	 * @return the opcion3
	 */
	public boolean getOpcion3() {
		if (this.opcion == 3 && !error) {
			cargarListasIntegrantes();
			return true;

		}
		return false;
	}

	/**
	 * Gets the opcion4.
	 *
	 * @return the opcion4
	 */
	public boolean getOpcion4() {
		if (this.opcion == 4)
			return true;
		return false;
	}

	/**
	 * Gets the pagina actual.
	 *
	 * @return the pagina actual
	 */
	public String getPaginaActual() {
		return paginaActual;
	}

	/**
	 * Gets the persona seleccionada.
	 *
	 * @return the persona seleccionada
	 */
	public Persona getPersonaSeleccionada() {
		return personaSeleccionada;
	}

	/**
	 * Gets the proyecto actual.
	 *
	 * @return the proyecto actual
	 */
	public Proyecto getProyectoActual() {
		return proyectoActual;
	}

	/**
	 * Gets the proyectos.
	 *
	 * @return the proyectos
	 */
	public String getProyectos() {
		return this.paginaActual + "&opcion=3";
	}

	/**
	 * Gets the titulo actual.
	 *
	 * @return the titulo actual
	 */
	public String getTituloActual() {
		if (getOpcion1())
			return "titulo_info_general.png";
		if (getOpcion2())
			return "titulo_integrantes.png";
		if (getOpcion3())
			return "titulo_proyectos.png";
		if (getOpcion4())
			return "titulo_noticias.png";
		return "";
	}

	/**
	 * Gets the todas noticias.
	 *
	 * @return the todas noticias
	 */
	public String getTodasNoticias() {
		return this.paginaActual + "&opcion=4";
	}

	/**
	 * Gets the visible codirector.
	 *
	 * @return the visible codirector
	 */
	public boolean getVisibleCodirector() {
		return !esListaVacia(listaIntegrantesCodirecto);
	}

	/**
	 * Gets the visible direccion.
	 *
	 * @return the visible direccion
	 */
	public boolean getVisibleDireccion() {
		return StringUtils.isNotEmpty(grupoActual.getDireccion());
	}

	/**
	 * Gets the visible docente.
	 *
	 * @return the visible docente
	 */
	public boolean getVisibleDocente() {
		return !esListaVacia(listaIntegrantesDocente);
	}

	/**
	 * Gets the visible email.
	 *
	 * @return the visible email
	 */
	public boolean getVisibleEmail() {
		return StringUtils.isNotEmpty(grupoActual.getEmail());
	}

	/**
	 * Gets the visible enfoque.
	 *
	 * @return the visible enfoque
	 */
	public boolean getVisibleEnfoque() {
		return StringUtils.isNotEmpty(grupoActual.getVision());
	}

	/**
	 * Gets the visible estudiante doctorado.
	 *
	 * @return the visible estudiante doctorado
	 */
	public boolean getVisibleEstudianteDoctorado() {
		return !esListaVacia(listaIntegrantesEstudiantesDoctorado);
	}

	/**
	 * Gets the visible estudiante maestria.
	 *
	 * @return the visible estudiante maestria
	 */
	public boolean getVisibleEstudianteMaestria() {
		return !esListaVacia(listaIntegrantesEstudiantesMaestria);
	}

	/**
	 * Gets the visible estudiante pregrado.
	 *
	 * @return the visible estudiante pregrado
	 */
	public boolean getVisibleEstudiantePregrado() {
		return !esListaVacia(listaIntegrantesEstudiantesPregrado);
	}

	public boolean getVisibleEstudianteVisitantes() {
		return !esListaVacia(listaIntegrantesEstudiantesVisitantes);
	}

	public boolean getVisibleEstudianteLider() {
		return !esListaVacia(listaIntegrantesEstudiantesLider);
	}
	
	public boolean getVisibleEgresado() {
		return !esListaVacia(listaEgresados);
	}

	/**
	 * Gets the visible externo.
	 *
	 * @return the visible externo
	 */
	public boolean getVisibleExterno() {
		return !esListaVacia(listaIntegrantesExterno);
	}

	/**
	 * Gets the visible lineas.
	 *
	 * @return the visible lineas
	 */
	public boolean getVisibleLineas() {
		if (grupoActual.getLineas().size() > 0)
			return true;
		return false;
	}

	/**
	 * Gets the visible otras dependencias.
	 *
	 * @return the visible otras dependencias
	 */
	public boolean getVisibleOtrasDependencias() {
		if (grupoActual.getDependencias().size() > 0)
			return true;
		return false;
	}

	/**
	 * Gets the visible pagina externa.
	 *
	 * @return the visible pagina externa
	 */
	public boolean getVisiblePaginaExterna() {
		return StringUtils.isNotEmpty(grupoActual.getDireccion());
	}

	/**
	 * Gets the visible perspectiva.
	 *
	 * @return the visible perspectiva
	 */
	public boolean getVisiblePerspectiva() {
		return StringUtils.isNotEmpty(grupoActual.getPerspectiva());
	}

	/**
	 * Gets the visible presentacion.
	 *
	 * @return the visible presentacion
	 */
	public boolean getVisiblePresentacion() {
		if (grupoActual.getPresentacion().trim().length() > 0)
			return true;
		return false;
	}

	/**
	 * Gets the visible prioridad.
	 *
	 * @return the visible prioridad
	 */
	public boolean getVisiblePrioridad() {
		return StringUtils.isNotEmpty(grupoActual.getPrioridad());
	}

	/**
	 * Gets the visible proyectos.
	 *
	 * @return the visible proyectos
	 */
	public boolean getVisibleProyectos() {
		return !esListaVacia(new ArrayList<Proyecto>(grupoActual.getProyectos()));
	}

	/**
	 * Gets the visible telefono.
	 *
	 * @return the visible telefono
	 */
	public boolean getVisibleTelefono() {
		return StringUtils.isNotEmpty(grupoActual.getTelefono());
	}

	/**
	 * Checks if is bandera.
	 *
	 * @return true, if is bandera
	 */
	public boolean isBandera() {
		return bandera;
	}

	/**
	 * Checks if is error.
	 *
	 * @return true, if is error
	 */
	public boolean isError() {
		return error;
	}

	/**
	 * Checks if is es intersede.
	 *
	 * @return true, if is es intersede
	 */
	public boolean isEsIntersede() {
		return esIntersede;
	}

	/**
	 * Checks if is visible otras agendas.
	 *
	 * @return true, if is visible otras agendas
	 */
	public boolean isVisibleOtrasAgendas() {
		return visibleOtrasAgendas;
	}

	/**
	 * Checks if is visible otras areas.
	 *
	 * @return true, if is visible otras areas
	 */
	public boolean isVisibleOtrasAreas() {
		return visibleOtrasAreas;
	}

	/**
	 * Checks if is visible planes.
	 *
	 * @return true, if is visible planes
	 */
	public boolean isVisiblePlanes() {
		return visiblePlanes;
	}

	/**
	 * Checks if is visible productos.
	 *
	 * @return true, if is visible productos
	 */
	public boolean isVisibleProductos() {
		return visibleProductos;
	}

	/**
	 * Programa academico doctorado.
	 *
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void programaAcademicoDoctorado() throws SQLException {
		if (!esListaVacia(listaIntegrantesEstudiantesDoctorado)) {
			for (int i = 0; i < listaIntegrantesEstudiantesDoctorado.size(); i++) {
				InvestigadorGrupo ig = listaIntegrantesEstudiantesDoctorado.get(i);
				Estudiante estudiante = buscarEstudiante(ig);
				ig.setPlanEstudiante(estudiante.getPlan().getNombre());
			}
		}
	}

	/**
	 * Programa academico maestria.
	 *
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void programaAcademicoMaestria() throws SQLException {

		if (!esListaVacia(listaIntegrantesEstudiantesMaestria)) {
			for (int i = 0; i < listaIntegrantesEstudiantesMaestria.size(); i++) {
				InvestigadorGrupo ig = listaIntegrantesEstudiantesMaestria.get(i);
				Estudiante estudiante = buscarEstudiante(ig);
				ig.setPlanEstudiante(estudiante.getPlan().getNombre());
			}
		}
	}

	/**
	 * Programa academico pregrado.
	 *
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void programaAcademicoPregrado() throws SQLException {

		if (!esListaVacia(listaIntegrantesEstudiantesPregrado)) {
			for (int i = 0; i < listaIntegrantesEstudiantesPregrado.size(); i++) {
				InvestigadorGrupo ig = listaIntegrantesEstudiantesPregrado.get(i);
				Estudiante estudiante = buscarEstudiante(ig);
				ig.setPlanEstudiante(estudiante.getPlan().getNombre());
			}
		}
	}

	public void programaAcademicoVisitantes() throws SQLException {

		if (!esListaVacia(listaIntegrantesEstudiantesVisitantes)) {
			for (int i = 0; i < listaIntegrantesEstudiantesVisitantes.size(); i++) {
				InvestigadorGrupo ig = listaIntegrantesEstudiantesVisitantes.get(i);
				Estudiante estudiante = buscarEstudiante(ig);
				ig.setPlanEstudiante(estudiante.getPlan().getNombre());
			}
		}
	}

	public void programaAcademicoLider() throws SQLException {

		if (!esListaVacia(listaIntegrantesEstudiantesLider)) {
			for (int i = 0; i < listaIntegrantesEstudiantesLider.size(); i++) {
				InvestigadorGrupo ig = listaIntegrantesEstudiantesLider.get(i);
				Estudiante estudiante = buscarEstudiante(ig);
				ig.setPlanEstudiante(estudiante.getPlan().getNombre());
			}
		}
	}

	/**
	 * Programa estudiantes.
	 *
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void programaEstudiantes() throws SQLException {
		programaAcademicoDoctorado();
		programaAcademicoMaestria();
		programaAcademicoPregrado();
		programaAcademicoVisitantes();
		programaAcademicoLider();
	}

	/**
	 * Reporte grupo busqueda.
	 *
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void reporteGrupoBusqueda() throws SQLException {

		String id = grupoActual.getId().toString();

		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("gru", id);
		r.setNombreReporte("/portafolio/Grupo");
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
	}

	/**
	 * Sets the actual.
	 *
	 * @param actual
	 *            the new actual
	 */
	public void setActual(File actual) {
		this.actual = actual;
	}

	/**
	 * Sets the area ciencia.
	 *
	 * @param areaCiencia
	 *            the new area ciencia
	 */
	public void setAreaCiencia(String areaCiencia) {
		this.areaCiencia = areaCiencia;
	}

	/**
	 * Sets the bandera.
	 *
	 * @param bandera
	 *            the new bandera
	 */
	public void setBandera(boolean bandera) {
		this.bandera = bandera;
	}

	/**
	 * Sets the email persona.
	 *
	 * @param emailPersona
	 *            the new email persona
	 */
	public void setEmailPersona(String emailPersona) {
		this.emailPersona = emailPersona;
	}

	/**
	 * Sets the error.
	 *
	 * @param error
	 *            the new error
	 */
	public void setError(boolean error) {
		this.error = error;
	}

	/**
	 * Sets the es intersede.
	 *
	 * @param esIntersede
	 *            the new es intersede
	 */
	public void setEsIntersede(boolean esIntersede) {
		this.esIntersede = esIntersede;
	}

	/**
	 * Sets the grupo actual.
	 *
	 * @param grupoActual
	 *            the new grupo actual
	 */
	public void setGrupoActual(Grupo grupoActual) {
		this.grupoActual = grupoActual;
	}

	/**
	 * Sets the id grupo.
	 *
	 * @param idGrupo
	 *            the new id grupo
	 */
	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}

	/**
	 * Sets the imagen.
	 *
	 * @param imagen
	 *            the new imagen
	 */
	public void setImagen(StreamedContent imagen) {
		this.imagen = imagen;
	}

	/**
	 * Sets the lider.
	 *
	 * @param lider
	 *            the new lider
	 */
	public void setLider(InvestigadorGrupo lider) {
		this.lider = lider;
	}

	/**
	 * Sets the lista agendas grupo.
	 *
	 * @param listaAgendasGrupo
	 *            the new lista agendas grupo
	 */
	public void setListaAgendasGrupo(List<GrupoAgenda> listaAgendasGrupo) {
		this.listaAgendasGrupo = listaAgendasGrupo;
	}

	/**
	 * Sets the lista integrantes codirecto.
	 *
	 * @param listaIntegrantesCodirecto
	 *            the new lista integrantes codirecto
	 */
	public void setListaIntegrantesCodirecto(List<InvestigadorGrupo> listaIntegrantesCodirecto) {
		this.listaIntegrantesCodirecto = listaIntegrantesCodirecto;
	}

	/**
	 * Sets the lista integrantes docente.
	 *
	 * @param listaIntegrantesDocente
	 *            the new lista integrantes docente
	 */
	public void setListaIntegrantesDocente(List<InvestigadorGrupo> listaIntegrantesDocente) {
		this.listaIntegrantesDocente = listaIntegrantesDocente;
	}

	/**
	 * Sets the lista integrantes estudiantes doctorado.
	 *
	 * @param listaIntegrantesEstudiantesDoctorado
	 *            the new lista integrantes estudiantes doctorado
	 */
	public void setListaIntegrantesEstudiantesDoctorado(List<InvestigadorGrupo> listaIntegrantesEstudiantesDoctorado) {
		this.listaIntegrantesEstudiantesDoctorado = listaIntegrantesEstudiantesDoctorado;
	}

	/**
	 * Sets the lista integrantes estudiantes maestria.
	 *
	 * @param listaIntegrantesEstudiantesMaestria
	 *            the new lista integrantes estudiantes maestria
	 */
	public void setListaIntegrantesEstudiantesMaestria(List<InvestigadorGrupo> listaIntegrantesEstudiantesMaestria) {
		this.listaIntegrantesEstudiantesMaestria = listaIntegrantesEstudiantesMaestria;
	}

	/**
	 * Sets the lista integrantes estudiantes pregrado.
	 *
	 * @param listaIntegrantesEstudiantesPregrado
	 *            the new lista integrantes estudiantes pregrado
	 */
	public void setListaIntegrantesEstudiantesPregrado(List<InvestigadorGrupo> listaIntegrantesEstudiantesPregrado) {
		this.listaIntegrantesEstudiantesPregrado = listaIntegrantesEstudiantesPregrado;
	}

	/**
	 * Sets the lista integrantes externo.
	 *
	 * @param listaIntegrantesExterno
	 *            the new lista integrantes externo
	 */
	public void setListaIntegrantesExterno(List<InvestigadorGrupo> listaIntegrantesExterno) {
		this.listaIntegrantesExterno = listaIntegrantesExterno;
	}

	/**
	 * Sets the lista productos grupo.
	 *
	 * @param listaProductosGrupo
	 *            the new lista productos grupo
	 */
	public void setListaProductosGrupo(List<GrupoProductoSara> listaProductosGrupo) {
		this.listaProductosGrupo = listaProductosGrupo;
	}

	/**
	 * Sets the lista sedes grupo.
	 *
	 * @param listaSedesGrupo
	 *            the new lista sedes grupo
	 */
	public void setListaSedesGrupo(List<GrupoIntersedes> listaSedesGrupo) {
		this.listaSedesGrupo = listaSedesGrupo;
	}

	/**
	 * Sets the nombre grupo.
	 *
	 * @param nombreGrupo
	 *            the new nombre grupo
	 */
	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}

	/**
	 * Sets the nombre responsable.
	 *
	 * @param nombreResponsable
	 *            the new nombre responsable
	 */
	public void setNombreResponsable(String nombreResponsable) {
		this.nombreResponsable = nombreResponsable;
	}

	/**
	 * Sets the pagina actual.
	 *
	 * @param paginaActual
	 *            the new pagina actual
	 */
	public void setPaginaActual(String paginaActual) {
		this.paginaActual = paginaActual;
	}

	/**
	 * Sets the persona seleccionada.
	 *
	 * @param personaSeleccionada
	 *            the new persona seleccionada
	 */
	public void setPersonaSeleccionada(Persona personaSeleccionada) {
		this.personaSeleccionada = personaSeleccionada;
	}

	/**
	 * Sets the proyecto actual.
	 *
	 * @param proyectoActual
	 *            the new proyecto actual
	 */
	public void setProyectoActual(Proyecto proyectoActual) {
		this.proyectoActual = proyectoActual;
	}

	/**
	 * Sets the visible otras agendas.
	 *
	 * @param visibleOtrasAgendas
	 *            the new visible otras agendas
	 */
	public void setVisibleOtrasAgendas(boolean visibleOtrasAgendas) {
		this.visibleOtrasAgendas = visibleOtrasAgendas;
	}

	/**
	 * Sets the visible otras areas.
	 *
	 * @param visibleOtrasAreas
	 *            the new visible otras areas
	 */
	public void setVisibleOtrasAreas(boolean visibleOtrasAreas) {
		this.visibleOtrasAreas = visibleOtrasAreas;
	}

	/**
	 * Sets the visible planes.
	 *
	 * @param visiblePlanes
	 *            the new visible planes
	 */
	public void setVisiblePlanes(boolean visiblePlanes) {
		this.visiblePlanes = visiblePlanes;
	}

	/**
	 * Sets the visible productos.
	 *
	 * @param visibleProductos
	 *            the new visible productos
	 */
	public void setVisibleProductos(boolean visibleProductos) {
		this.visibleProductos = visibleProductos;
	}

	public List<InvestigadorGrupo> getListaIntegrantesEstudiantesVisitantes() {
		return listaIntegrantesEstudiantesVisitantes;
	}

	public void setListaIntegrantesEstudiantesVisitantes(
			List<InvestigadorGrupo> listaIntegrantesEstudiantesVisitantes) {
		this.listaIntegrantesEstudiantesVisitantes = listaIntegrantesEstudiantesVisitantes;
	}

	public List<InvestigadorGrupo> getListaIntegrantesEstudiantesLider() {
		return listaIntegrantesEstudiantesLider;
	}

	public void setListaIntegrantesEstudiantesLider(List<InvestigadorGrupo> listaIntegrantesEstudianteslider) {
		this.listaIntegrantesEstudiantesLider = listaIntegrantesEstudianteslider;
	}

	public List<InvestigadorGrupo> getListaEgresados() {
		return listaEgresados;
	}

	public void setListaEgresados(List<InvestigadorGrupo> listaEgresados) {
		this.listaEgresados = listaEgresados;
	}

}
