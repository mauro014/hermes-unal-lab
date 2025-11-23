package co.edu.unal.hermes.vista.busqueda;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import org.apache.jasper.tagplugins.jstl.core.ForEach;

import co.edu.unal.hermes.modelo.ConvocatoriaExterna;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.HistoricoBusqueda;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.Parametro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.Semillero;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleEnsayosServicios;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.utils.ReemplazaAcentos;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorBusqueda extends ManejadorBase implements Serializable {

	private static final long serialVersionUID = 1L;

	// Determina si la busqueda ya fue realizada
	boolean banderaResultadosBusqueda;

	// Busqueda segun opciones
	int tipo;
	private String sedeAgregar;
	private String facultadAgregar;
	private String areaAgregar;
	private String lineaAgregar;
	private String areaTematicaAgregar;
	private String campoBusqueda;
	private String nombreLiderGrupo;
	private String apellidoLiderGrupo;
	private String nombreLiderProyecto;
	private String apellidoLiderProyecto;
	private String estadoAgregar;
	private String entidadAgregar;

	// Opciones de buscador
	private static final int PALABRASCLAVE = 0;
	private static final int TIPOINVESTIGADORES = 1;
	private static final int TIPOPROYECTOS = 2;
	private static final int TIPOGRUPOS = 3;
	private static final int TIPOLABORATORIOS = 4;
	private static final int TIPOCONVOCATORIAS = 6;
	private static final int TIPOCOLECCIONES = 7;
	private static final int TIPOECP = 8;
	private static final int TIPOSEMILLEROS = 9;

	// Opciones filtros
	private boolean filtroLiderGrupo;
	private boolean filtroLiderProyecto;

	// Opciones tipo busqueda
	private SelectItem[] tipoItem = { new SelectItem(new Integer(0), "Todas las categorías"),
			new SelectItem(new Integer(1), "Investigadores"), new SelectItem(new Integer(2), "Proyectos"),
			new SelectItem(new Integer(3), "Grupos"), new SelectItem(new Integer(4), "Laboratorios"),
			new SelectItem(new Integer(6), "Convocatorias"), new SelectItem(new Integer(7), "Colecciones científicas"),
			new SelectItem(new Integer(8), "Cursos de Educación Continua"),new SelectItem(new Integer(9), "Semilleros") };
	private SelectItem[] listaSedesItem;
	private SelectItem[] listaFacultadesItem;
	private SelectItem[] listaAreaTematicaItem;
	private SelectItem[] listaEntidadesItem;
	private String[] selectedDedicacion;
	List<SelectItem> listaTiposDedicacion = new ArrayList<SelectItem>();
	private Long dedicacion;

	public static final String ACTIVA = "Activa";
	public static final String INACTIVA = "Inactiva";

	private SelectItem[] estadoItem = { new SelectItem(new String("0"), "Seleccione estado"),
			new SelectItem(new String(ACTIVA), ACTIVA), new SelectItem(new String(INACTIVA), INACTIVA) };

	// Listado de filtros aplicados
	private List<Dependencia> listaFacultadesFiltro;
	private List<Dependencia> listaSedesFiltro;
	private List<String> listaAreasFiltro;
	private List<String> listaLineasFiltro;
	private List<String> listaAreasTematicasFiltro;
	private List<String> listaEstadosFiltro;
	private List<FuenteFinanciacion> listaEntidadesFiltro;

	// Listados encontrados
	private List<Persona> listaPersonas;
	private List<Proyecto> listaProyecto;
	private List<ConvocatoriaExterna> listaConvocatorias;
	private List<Grupo> listaGrupos;
	private List<Semillero> listaSemilleros;
	private List<LaboratorioDetalleEnsayosServicios> listaLaboratorios;

	// Objetos de busqueda
	private Persona personaSeleccionada;
	private Proyecto proyectoSeleccionado;
	private Grupo grupoSeleccionado;
	private LaboratorioDetalleEnsayosServicios ensayoSeleccionado;
	private ConvocatoriaExterna convocatoriaSeleccionada;
	private Semillero semilleroActual;
	private List<Dependencia> listaSedes;
	private List<Dependencia> listaFacultades;
	private List<FuenteFinanciacion> listaEntidades;
	private Dependencia sedeSeleccionada;
	private Dependencia facultadSeleccionada;
	private String areaSeleccionada;
	private String lineaSeleccionada;
	private String estadoSeleccionado;
	private FuenteFinanciacion entidadSeleccionada;

	// Mostrar opciones
	private Boolean mostrarAgregarSede;
	private Boolean mostrarAgregarFacultad;
	private Boolean mostrarAgregarArea;
	private Boolean mostrarAgregarLinea;
	private Boolean mostrarAgregarAreaTematica;
	private Boolean mostrarAgregarDedicacion;
	private Boolean mostrarAgregarEstado;
	private Boolean mostrarAgregarEntidad;
	private Boolean nombreExacto;
	private Boolean nombreExacto2;

	// Laboratorios
	private Boolean laboratorioAcreditado = false;
	private Boolean soloEquiposRobustos = false;
	private Boolean laboratorioInterfacultades = false;
	public static final String TIPO_DEDICACION_INVESTIGACION = "1";
	public static final String TIPO_DEDICACION_EXTENSION = "2";
	public static final String TIPO_DEDICACION_DOCENCIA = "3";
	private String dedicacionFiltro = "";

	boolean banderaBusqueda = false;
	boolean busquedaTodos = true;
	int tipoPalabraClave = -1;
	boolean mostrarResultadosInvestigador = false;
	boolean mostrarResultadosProyectos = false;
	boolean mostrarResultadosGrupos = false;
	boolean mostrarResultadosLaboratorios = false;
	boolean mostrarResultadosConvocatorias = false;
	boolean mostrarResultadosSemilleros = false;
	int resultadosInvestigadores = 0;
	int resultadosProyectos = 0;
	int resultadosGrupos = 0;
	int resultadosLaboratorios = 0;
	int resultadosConvocatorias = 0;
	int resultadosSemilleros = 0;
	boolean mostrarAreaTematicaTexto = false;
	private String textoMostrarAreaTematica = "";

	private HistoricoBusqueda historicoBusqueda;

	// Para buscadores independientes
	private boolean busquedaLaboratorios = false;
	private boolean busquedaInvestigadores = false;
	private boolean busquedaConvocatorias = false;
	private boolean busquedaProyectos = false;
	private boolean busquedaGrupos = false;
	private boolean busquedaSemilleros = false;

	private String currURL, oldURL;

	public ManejadorBusqueda() {

		// Inicialización valores básicos
		banderaResultadosBusqueda = false;
		mostrarAgregarSede = false;
		mostrarAgregarFacultad = false;
		mostrarAgregarLinea = false;
		mostrarAgregarArea = false;
		mostrarAgregarAreaTematica = false;
		mostrarAgregarDedicacion = false;
		mostrarAgregarEstado = false;
		mostrarAgregarEntidad = false;
		nombreExacto = false;
		nombreExacto2 = false;
		reiniciarBusqueda();
		currURL = request.getRequestURL().toString();
		oldURL = "";
	}

	public String getVerificaParametroUrl() {
		try {

			FacesContext context = FacesContext.getCurrentInstance();
			request = (HttpServletRequest) context.getExternalContext().getRequest();

			String url = request.getQueryString();

			if (url != null && request.getParameter("busq") != null && !"".equals(request.getParameter("busq"))) {
				tipo = PALABRASCLAVE;
				campoBusqueda = new String(request.getParameter("busq"));
				request = null;

				buscar();
			}
		} catch (Exception e) {
			return "";
		}

		return "";

	}
	
	public String getPruebaConexionManejador() {
		List<TipoDocumento> tipoDoc = this.servicioGeneral.obtenerObjetos(TipoDocumento.class,
				"from TipoDocumento t where t.id = '" + TipoDocumento.CEDULA + "'");
		return "";
	}

	public void buscar() {

		historicoBusqueda = new HistoricoBusqueda();
		historicoBusqueda.setFechaBusqueda(new Date());

		campoBusqueda = campoBusqueda.trim();

		banderaBusqueda = true;
		if (mostrarAgregarSede || mostrarAgregarFacultad) {
			if (!"".equals(sedeAgregar.trim())) {
				boolean esta = false;
				if (listaSedesFiltro == null) {
					listaSedesFiltro = new ArrayList<Dependencia>();
				} else {
					for (Dependencia sedeBucle : listaSedesFiltro) {
						if (sedeBucle.getId().trim().equals(sedeAgregar.trim())) {
							esta = true;
							break;
						}
					}
				}
				if (!esta) {
					Dependencia sede = encontrarSede(sedeAgregar);
					if (sede != null) {
						listaSedesFiltro.add(sede);
					}
				}
			}
			mostrarAgregarSede = false;
		}
		if (mostrarAgregarFacultad) {
			if (!"".equals(facultadAgregar.trim())) {
				boolean esta = false;
				if (listaFacultadesFiltro == null) {
					listaFacultadesFiltro = new ArrayList<Dependencia>();
				} else {
					for (Dependencia facultadBucle : listaFacultadesFiltro) {
						if (facultadBucle.getId().trim().equals(facultadAgregar.trim())) {
							esta = true;
							break;
						}
					}
				}
				if (!esta) {
					Dependencia facultad = encontrarFacultad(facultadAgregar);
					if (facultad != null) {
						listaFacultadesFiltro.add(facultad);
					}
				}
			}
			mostrarAgregarFacultad = false;
		}
		if (mostrarAgregarArea) {
			if (!"".equals(areaAgregar.trim())) {
				boolean esta = false;
				if (listaAreasFiltro == null) {
					listaAreasFiltro = new ArrayList<String>();
				} else {
					for (String areaBucle : listaAreasFiltro) {
						if (areaBucle.trim().equals(areaAgregar.trim())) {
							esta = true;
							break;
						}
					}
				}
				if (!esta) {
					String nuevaArea = areaAgregar;
					listaAreasFiltro.add(nuevaArea);
				}
				mostrarAgregarArea = false;
				areaAgregar = "";
			}
		}
		if (mostrarAgregarLinea) {
			if (!"".equals(lineaAgregar.trim())) {
				boolean esta = false;
				if (listaLineasFiltro == null) {
					listaLineasFiltro = new ArrayList<String>();
				} else {
					for (String areaBucle : listaLineasFiltro) {
						if (areaBucle.trim().equals(lineaAgregar.trim())) {
							esta = true;
							break;
						}
					}
				}
				if (!esta) {
					String nuevaArea = lineaAgregar;
					listaLineasFiltro.add(nuevaArea);
				}
				mostrarAgregarLinea = false;
				lineaAgregar = "";
			}
		}
		if (mostrarAgregarAreaTematica) {
			if (areaTematicaAgregar != null && !"".equals(areaTematicaAgregar.trim())) {
				mostrarAgregarAreaTematica = false;
				mostrarAreaTematicaTexto = true;

				String consulta = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and dd.identificador.tipo = '"
						+ areaTematicaAgregar + "' and d.tipo = 'EJE_TEMATICO' order by dd.descripcion";
				List<DominioDetalle> lista = servicioGeneral.obtenerObjetos(DominioDetalle.class, consulta);

				if (lista != null && !lista.isEmpty()) {
					DominioDetalle dd = (DominioDetalle) lista.get(0);
					textoMostrarAreaTematica = dd.getDescripcion();
				}
			}
		}
		if (mostrarAgregarEstado) {
			if (!"".equals(estadoAgregar.trim()) && !"0".equals(estadoAgregar.trim())) {
				boolean esta = false;
				if (listaEstadosFiltro == null) {
					listaEstadosFiltro = new ArrayList<String>();
				} else {
					for (String areaBucle : listaEstadosFiltro) {
						if (areaBucle.trim().equals(estadoAgregar.trim())) {
							esta = true;
							break;
						}
					}
				}
				if (!esta) {
					String nuevoEstado = estadoAgregar;
					listaEstadosFiltro.add(nuevoEstado);
				}
				mostrarAgregarEstado = false;
				estadoAgregar = "";
			}
		}
		if (mostrarAgregarEntidad) {
			if (!"".equals(entidadAgregar.trim()) && !"0".equals(entidadAgregar.trim())) {
				boolean esta = false;
				if (listaEntidadesFiltro == null) {
					listaEntidadesFiltro = new ArrayList<FuenteFinanciacion>();
				} else {
					for (FuenteFinanciacion entidadBucle : listaEntidadesFiltro) {
						if (entidadBucle.getId().equals(entidadAgregar.trim())) {
							esta = true;
							break;
						}
					}
				}
				if (!esta) {
					FuenteFinanciacion entidad = encontrarEntidad(entidadAgregar);
					listaEntidadesFiltro.add(entidad);
				}
				mostrarAgregarEntidad = false;
				entidadAgregar = "";
			}
		}

		if ((tipo == PALABRASCLAVE && !"".equals(campoBusqueda.trim())
				&& (!busquedaLaboratorios && !busquedaInvestigadores && !busquedaConvocatorias))) {
			historicoBusqueda.setCategoria(String.valueOf(PALABRASCLAVE));
			historicoBusqueda.setPalabra(campoBusqueda.trim());
			busquedaInvestigadores();
			busquedaProyectos();
			busquedaGrupos();
			busquedaLaboratorios();
			busquedaConvocatorias();
			busquedaSemilleros();
			banderaResultadosBusqueda = true;
			if (getResultadosInvestigadores() > 0) {
				mostrarResultadosInvestigador = true;
				cargarResultadosInvestigador();
			} else if (getResultadosProyectos() > 0) {
				mostrarResultadosProyectos = true;
				cargarResultadosProyecto();
			} else if (getResultadosGrupos() > 0) {
				mostrarResultadosGrupos = true;
				cargarResultadosGrupo();
			} else if (getResultadosLaboratorios() > 0) {
				mostrarResultadosLaboratorios = true;
				cargarResultadosLaboratorio();
			} else if (getResultadosConvocatorias() > 0) {
				mostrarResultadosConvocatorias = true;
				cargarResultadosConvocatoria();
			} else if (getResultadosSemilleros() > 0) {
				mostrarResultadosSemilleros = true;
				cargarResultadosSemilleros();
			}
		}

		if (tipo == TIPOINVESTIGADORES) {
			historicoBusqueda.setCategoria(String.valueOf(TIPOINVESTIGADORES));
			busquedaInvestigadores();
			ocultarResultados();
			banderaResultadosBusqueda = true;
			mostrarResultadosInvestigador = true;

		} else if (tipo == TIPOPROYECTOS) {
			historicoBusqueda.setCategoria(String.valueOf(TIPOPROYECTOS));
			busquedaProyectos();
			ocultarResultados();
			banderaResultadosBusqueda = true;
			mostrarResultadosProyectos = true;
		} else if (tipo == TIPOGRUPOS) {
			historicoBusqueda.setCategoria(String.valueOf(TIPOGRUPOS));
			busquedaGrupos();
			ocultarResultados();
			banderaResultadosBusqueda = true;
			mostrarResultadosGrupos = true;
		} else if (tipo == TIPOLABORATORIOS) {
			historicoBusqueda.setCategoria(String.valueOf(TIPOLABORATORIOS));
			busquedaLaboratorios();
			ocultarResultados();
			banderaResultadosBusqueda = true;
			mostrarResultadosLaboratorios = true;
		} else if (tipo == TIPOCONVOCATORIAS) {
			historicoBusqueda.setCategoria(String.valueOf(TIPOCONVOCATORIAS));
			busquedaConvocatorias();
			ocultarResultados();
			banderaResultadosBusqueda = true;
			mostrarResultadosConvocatorias = true;
		} else if (tipo == TIPOSEMILLEROS) {
			historicoBusqueda.setCategoria(String.valueOf(TIPOSEMILLEROS));
			busquedaSemilleros();
			ocultarResultados();
			banderaResultadosBusqueda = true;
			mostrarResultadosSemilleros = true;
		} else if ("".equals(campoBusqueda.trim())) {
			banderaResultadosBusqueda = false;
		}

		// Para buscadores independientes

		if (busquedaLaboratorios) {
			historicoBusqueda.setCategoria(String.valueOf(TIPOLABORATORIOS));
			if (campoBusqueda != null && !"".equals(campoBusqueda.trim())) {
				historicoBusqueda.setPalabra(campoBusqueda);
			}
			busquedaLaboratorios();
			banderaResultadosBusqueda = true;
			mostrarResultadosLaboratorios = true;
			busquedaLaboratorios = false;
		}

		if (busquedaInvestigadores) {
			historicoBusqueda.setCategoria(String.valueOf(TIPOINVESTIGADORES));
			if (campoBusqueda != null && !"".equals(campoBusqueda.trim())) {
				historicoBusqueda.setPalabra(campoBusqueda.trim());
			}
			busquedaInvestigadores();
			banderaResultadosBusqueda = true;
			mostrarResultadosInvestigador = true;
			busquedaInvestigadores = false;
		}

		if (busquedaConvocatorias) {
			historicoBusqueda.setCategoria(String.valueOf(TIPOCONVOCATORIAS));
			if (campoBusqueda != null && !"".equals(campoBusqueda.trim())) {
				historicoBusqueda.setPalabra(campoBusqueda);
			}
			busquedaConvocatorias();
			banderaResultadosBusqueda = true;
			mostrarResultadosConvocatorias = true;
			busquedaConvocatorias = false;
		}

		if (busquedaProyectos) {
			historicoBusqueda.setCategoria(String.valueOf(TIPOPROYECTOS));
			if (campoBusqueda != null && !"".equals(campoBusqueda.trim())) {
				historicoBusqueda.setPalabra(campoBusqueda);
			}
			busquedaProyectos();
			banderaResultadosBusqueda = true;
			mostrarResultadosProyectos = true;
			busquedaProyectos = false;
		}

		if (busquedaGrupos) {
			historicoBusqueda.setCategoria(String.valueOf(TIPOGRUPOS));
			if (campoBusqueda != null && !"".equals(campoBusqueda.trim())) {
				historicoBusqueda.setPalabra(campoBusqueda);
			}
			busquedaGrupos();
			banderaResultadosBusqueda = true;
			mostrarResultadosGrupos = true;
			busquedaGrupos = false;
		}
		
		if (busquedaSemilleros) {
			historicoBusqueda.setCategoria(String.valueOf(TIPOSEMILLEROS));
			if (campoBusqueda != null && !"".equals(campoBusqueda.trim())) {
				historicoBusqueda.setPalabra(campoBusqueda);
			}
			busquedaSemilleros();
			banderaResultadosBusqueda = true;
			mostrarResultadosSemilleros = true;
			busquedaSemilleros = false;
		}

		servicioGeneral.guardarObjeto(historicoBusqueda);

	}

	public int getTipoPalabraClave() {
		return tipoPalabraClave;
	}

	public void setTipoPalabraClave(int tipoPalabraClave) {
		this.tipoPalabraClave = tipoPalabraClave;
	}

	public void busquedaGrupos() {
		String sql = "";

		// Busqueda por sede
		if (listaSedesFiltro != null && !listaSedesFiltro.isEmpty()) {
			if (sql.length() > 0) {
				sql = sql + " and ";
			}
			sql += " ( ";
			boolean primero = true;
			for (Dependencia sede : listaSedesFiltro) {
				if (!primero) {
					sql += " or ";
				}
				primero = false;
				sql += " HER_DEPENDENCIA.SED_ID = " + sede.getId();
			}
			sql += " ) ";
		}

		// Busqueda por facultad
		if (listaFacultadesFiltro != null && !listaFacultadesFiltro.isEmpty()) {
			if (sql.length() > 0) {
				sql = sql + " and ";
			}
			sql += " ( ";
			boolean primero = true;
			for (Dependencia facultad : listaFacultadesFiltro) {
				if (!primero) {
					sql += " or ";
				}
				primero = false;
				sql += " HER_GRUPO.DPN_ID= " + facultad.getId() + " or HER_DEPENDENCIA.DPN_FACULTAD = "
						+ facultad.getId();
			}
			sql += " ) ";
		}

		// Busqueda por nombre
		if (campoBusqueda != null && campoBusqueda.trim().length() > 0) {
			if (sql.length() > 0)
				sql = sql + " and ";
			if (nombreExacto) {
				String nombreExacto = this.campoBusqueda.toUpperCase();
				sql = sql + "((UPPER(HER_GRUPO.GRU_NOMBRE) like '% " + nombreExacto + " %') or "
						+ "(UPPER(HER_GRUPO.GRU_NOMBRE) like '% " + nombreExacto + "') or "
						+ "(UPPER(HER_GRUPO.GRU_NOMBRE) like '" + nombreExacto + " %'))";
			} else {
				List<String> cadena = ReemplazaAcentos.listaPalabrasConTildes(this.campoBusqueda.toUpperCase());
				String palabra2 = ReemplazaAcentos.quitarTildes(this.campoBusqueda.toUpperCase());
				cadena.add(palabra2);
				if (!cadena.isEmpty()) {
					sql += "(";
					boolean primera = true;
					for (String palabra : cadena) {
						if (!primera) {
							sql += " or ";
						} else
							primera = false;
						sql += " (upper(HER_GRUPO.GRU_NOMBRE) like '%" + palabra + "%'"
								+ " or upper(HER_GRUPO.GRU_PRESENTACION) like '%" + palabra + "%') ";
					}
					sql += ")";
				}
			}
		}

		boolean opcionLider = false;
		String fromSql = "";

		if (filtroLiderGrupo) {

			if (this.nombreLiderGrupo.length() > 0) {
				opcionLider = true;
				if (sql.length() > 0) {
					sql = sql + " and ";
				}
				sql += " ( upper( HER_PERSONA.PER_NOMBRE1)  like  '%" + nombreLiderGrupo.trim().toUpperCase() + "%'"
						+ " or ( upper( HER_PERSONA.PER_NOMBRE2)  like  '%" + nombreLiderGrupo.trim().toUpperCase()
						+ "%' "
						+ " or ( upper( HER_PERSONA.PER_NOMBRE1) || ' ' || upper( HER_PERSONA.PER_NOMBRE2)) like  '%"
						+ nombreLiderGrupo.trim().toUpperCase() + "%') )";
			}

			if (this.apellidoLiderGrupo.length() > 0) {
				if (sql.length() > 0) {
					sql = sql + " and ";
				}
				opcionLider = true;
				sql += " ( upper( HER_PERSONA.PER_APELLIDO1)  like  '%" + apellidoLiderGrupo.trim().toUpperCase() + "%'"
						+ " or ( upper( HER_PERSONA.PER_APELLIDO2)  like  '%" + apellidoLiderGrupo.trim().toUpperCase()
						+ "%' "
						+ " or ( upper( HER_PERSONA.PER_APELLIDO1) || ' ' || upper( HER_PERSONA.PER_APELLIDO2)) like  '%"
						+ apellidoLiderGrupo.trim().toUpperCase() + "%')) ";

			}

			if (opcionLider) {
				sql += " and HER_GRUPO.GRU_ID = HER_INVESTIGADOR_GRUPO.GRU_ID "
						+ "and HER_INVESTIGADOR_GRUPO.INV_ID = HER_PERSONA.PER_ID and HER_INVESTIGADOR_GRUPO.TDO_ID = HER_PERSONA.TDO_ID "
						+ "and HER_INVESTIGADOR_GRUPO.ING_TIPO = 'L'";
				fromSql += ", HER_INVESTIGADOR_GRUPO , HER_PERSONA ";
			}
		}

		if (listaLineasFiltro != null && !listaLineasFiltro.isEmpty()) {
			if (sql.length() > 0) {
				sql = sql + " and ";
			}
			sql += " ( ";
			boolean primero = true;
			for (String linea : listaLineasFiltro) {
				if (!primero) {
					sql += " or ";
				}
				primero = false;
				sql += " upper (HER_LINEA_INVESTIGACION.LIN_NOMBRE) like '%" + linea.toUpperCase() + "%' ";
			}
			sql += " ) ";
			sql = sql
					+ " and HER_GRUPO_LINEA.LIN_ID = HER_LINEA_INVESTIGACION.LIN_ID and HER_GRUPO_LINEA.GRU_ID = HER_GRUPO.GRU_ID";
			fromSql += ", HER_GRUPO_LINEA, HER_LINEA_INVESTIGACION ";
		}

		if (sql.length() > 0)
			sql = fromSql + " where " + sql;

		listaGrupos = this.servicioGrupo.obtenerGrupoBuscador(sql);
	}

	public void busquedaInvestigadores() {
		String sql = "";
		this.listaPersonas = null;

		if (listaSedesFiltro != null && !listaSedesFiltro.isEmpty()) {
			if (sql.length() > 0) {
				sql = sql + " and ";
			}
			sql += " ( ";
			boolean primero = true;
			for (Dependencia sede : listaSedesFiltro) {
				if (!primero) {
					sql += " or ";
				}
				primero = false;
				sql += " HER_DEPENDENCIA.SED_ID = " + sede.getId();
			}
			sql += " ) ";
		}

		if (listaFacultadesFiltro != null && !listaFacultadesFiltro.isEmpty()) {
			if (sql.length() > 0) {
				sql = sql + " and ";
			}

			sql += " ( ";
			boolean primero = true;
			for (Dependencia facultad : listaFacultadesFiltro) {
				if (!primero) {
					sql += " or ";
				}
				primero = false;
				sql += " HER_INVESTIGADOR_INTERNO.DPN_ID= " + facultad.getId() + " or HER_DEPENDENCIA.DPN_ID_2 = "
						+ facultad.getId();
			}
			sql += " ) ";
		}
		String fromSql = "";
		if (this.campoBusqueda.length() > 0) {
			if (sql.length() > 0) {
				sql = sql + " and ";
			}
			sql += " ( ";
			String[] str1Array = campoBusqueda.split(" ");
			int tamano = str1Array.length;
			List<String> cadena = ReemplazaAcentos.listaPalabrasConTildes(this.campoBusqueda.toUpperCase());
			String palabra2 = ReemplazaAcentos.quitarTildes(this.campoBusqueda.toUpperCase());
			cadena.add(palabra2);

			if (!nombreExacto && !nombreExacto2) {
				if (!cadena.isEmpty()) {
					sql += "( ";
					boolean primera = true;
					for (String palabra : cadena) {
						if (!primera) {
							sql += " or ";
						} else
							primera = false;
						sql += " ( ";
						if (tamano == 1) {
							sql += "upper( HER_PERSONA.PER_NOMBRE1)  like  '%" + palabra + "%'"
									+ " or  upper( HER_PERSONA.PER_NOMBRE2)  like  '%" + palabra + "%' "
									+ " or upper( HER_PERSONA.PER_APELLIDO1)  like  '%" + palabra + "%'"
									+ " or  upper( HER_PERSONA.PER_APELLIDO2) like  '%" + palabra + "%' ";
						}
						if (tamano == 2) {
							sql += "( upper( HER_PERSONA.PER_NOMBRE1) || ' ' || upper( HER_PERSONA.PER_NOMBRE2)) like  '%"
									+ palabra + "%' "
									+ " or ( upper( HER_PERSONA.PER_APELLIDO1) || ' ' || upper( HER_PERSONA.PER_APELLIDO2)) like  '%"
									+ palabra + "%' "
									+ " or ( upper( HER_PERSONA.PER_NOMBRE1) || ' ' || upper( HER_PERSONA.PER_APELLIDO1)) like  '%"
									+ palabra + "%' "
									+ " or ( upper( HER_PERSONA.PER_NOMBRE1) || ' ' || upper( HER_PERSONA.PER_APELLIDO2)) like  '%"
									+ palabra + "%' "
									+ " or ( upper( HER_PERSONA.PER_NOMBRE2) || ' ' || upper( HER_PERSONA.PER_APELLIDO1)) like  '%"
									+ palabra + "%' "
									+ " or ( upper( HER_PERSONA.PER_NOMBRE2) || ' ' || upper( HER_PERSONA.PER_APELLIDO2)) like  '%"
									+ palabra + "%' ";
						}
						if (tamano > 2) {
							sql += "( upper( HER_PERSONA.PER_NOMBRE1) || ' ' || upper( HER_PERSONA.PER_NOMBRE2) || ' ' || upper( HER_PERSONA.PER_APELLIDO1)) like  '%"
									+ palabra + "%' "
									+ " or ( upper( HER_PERSONA.PER_NOMBRE1) || ' ' || upper( HER_PERSONA.PER_NOMBRE2) || ' ' || upper( HER_PERSONA.PER_APELLIDO2)) like  '%"
									+ palabra + "%' "
									+ " or ( upper( HER_PERSONA.PER_NOMBRE1) || ' ' || upper( HER_PERSONA.PER_APELLIDO1) || ' ' || upper( HER_PERSONA.PER_APELLIDO2)) like  '%"
									+ palabra + "%' "
									+ " or ( upper( HER_PERSONA.PER_NOMBRE2) || ' ' || upper( HER_PERSONA.PER_APELLIDO1) || ' ' || upper( HER_PERSONA.PER_APELLIDO2)) like  '%"
									+ palabra + "%' "
									+ " or ( upper( HER_PERSONA.PER_NOMBRE1) || ' ' || upper( HER_PERSONA.PER_NOMBRE2) || ' ' || upper( HER_PERSONA.PER_APELLIDO1) || ' ' || upper( HER_PERSONA.PER_APELLIDO2)) like  '%"
									+ palabra + "%' ";
						}
						sql += " ) ";

						sql += " or (upper(HER_LINEA_INVESTIGACION.LIN_NOMBRE) LIKE '% " + palabra.toUpperCase() + "') "
								+ "or ( upper( HER_PROYECTO.PRY_NOMBRE)  like  '% " + palabra.toUpperCase() + "')"
								+ " or ( upper( HER_GRUPO.GRU_NOMBRE)  like  '% " + palabra.toUpperCase() + "')";

						sql += " or (upper(HER_LINEA_INVESTIGACION.LIN_NOMBRE) LIKE '" + palabra.toUpperCase() + " %') "
								+ "or ( upper( HER_PROYECTO.PRY_NOMBRE)  like  '" + palabra.toUpperCase() + " %')"
								+ " or ( upper( HER_GRUPO.GRU_NOMBRE)  like  '" + palabra.toUpperCase() + " %')";

						sql += " or (upper(HER_LINEA_INVESTIGACION.LIN_NOMBRE) LIKE '% " + palabra.toUpperCase()
								+ " %') " + "or ( upper( HER_PROYECTO.PRY_NOMBRE)  like  '% " + palabra.toUpperCase()
								+ " %')" + " or ( upper( HER_GRUPO.GRU_NOMBRE)  like  '% " + palabra.toUpperCase()
								+ " %') ";

					}
					sql += " ) ";
				}
			} else {
				if (!cadena.isEmpty()) {
					sql += "( ";
					boolean primera = true;

					for (String palabra : cadena) {
						if (!primera) {
							sql += " or ";
						} else
							primera = false;
						sql += " ( ";
						if (nombreExacto2) {
							sql += " upper(HER_LINEA_INVESTIGACION.LIN_NOMBRE) LIKE '% " + palabra.toUpperCase() + "') "
									+ "or ( upper( HER_PROYECTO.PRY_NOMBRE)  like  '% " + palabra.toUpperCase() + "')"
									+ " or ( upper( HER_GRUPO.GRU_NOMBRE)  like  '% " + palabra.toUpperCase() + "')";

							sql += " or (upper(HER_LINEA_INVESTIGACION.LIN_NOMBRE) LIKE '" + palabra.toUpperCase()
									+ " %') " + "or ( upper( HER_PROYECTO.PRY_NOMBRE)  like  '" + palabra.toUpperCase()
									+ " %')" + " or ( upper( HER_GRUPO.GRU_NOMBRE)  like  '" + palabra.toUpperCase()
									+ " %')";

							sql += " or (upper(HER_LINEA_INVESTIGACION.LIN_NOMBRE) LIKE '% " + palabra.toUpperCase()
									+ " %') " + "or ( upper( HER_PROYECTO.PRY_NOMBRE)  like  '% "
									+ palabra.toUpperCase() + " %')" + " or ( upper( HER_GRUPO.GRU_NOMBRE)  like  '% "
									+ palabra.toUpperCase() + " %')";
						}
						if (nombreExacto) {
							if (tamano == 1) {
								sql += "upper( HER_PERSONA.PER_NOMBRE1)  =  '" + palabra.toUpperCase() + "'"
										+ " or  upper( HER_PERSONA.PER_NOMBRE2)  =  '" + palabra.toUpperCase() + "' "
										+ " or upper( HER_PERSONA.PER_APELLIDO1)  =  '" + palabra.toUpperCase() + "'"
										+ " or  upper( HER_PERSONA.PER_APELLIDO2) =  '" + palabra.toUpperCase() + "' ";
							}
							if (tamano == 2) {
								sql += "( upper( HER_PERSONA.PER_NOMBRE1) || ' ' || upper( HER_PERSONA.PER_NOMBRE2)) = '"
										+ palabra.toUpperCase() + "' "
										+ " or ( upper( HER_PERSONA.PER_APELLIDO1) || ' ' || upper( HER_PERSONA.PER_APELLIDO2)) =  '"
										+ palabra.toUpperCase() + "' "
										+ " or ( upper( HER_PERSONA.PER_NOMBRE1) || ' ' || upper( HER_PERSONA.PER_APELLIDO1)) =  '"
										+ palabra.toUpperCase() + "' "
										+ " or ( upper( HER_PERSONA.PER_NOMBRE1) || ' ' || upper( HER_PERSONA.PER_APELLIDO2)) =  '"
										+ palabra.toUpperCase() + "' "
										+ " or ( upper( HER_PERSONA.PER_NOMBRE2) || ' ' || upper( HER_PERSONA.PER_APELLIDO1)) =  '"
										+ palabra.toUpperCase() + "' "
										+ " or ( upper( HER_PERSONA.PER_NOMBRE2) || ' ' || upper( HER_PERSONA.PER_APELLIDO2)) =  '"
										+ palabra.toUpperCase() + "' ";
							}
							if (tamano > 2) {
								sql += "( upper( HER_PERSONA.PER_NOMBRE1) || ' ' || upper( HER_PERSONA.PER_NOMBRE2) || ' ' || upper( HER_PERSONA.PER_APELLIDO1)) =  '"
										+ palabra + "' "
										+ " or ( upper( HER_PERSONA.PER_NOMBRE1) || ' ' || upper( HER_PERSONA.PER_NOMBRE2) || ' ' || upper( HER_PERSONA.PER_APELLIDO2)) =  '"
										+ palabra + "' "
										+ " or ( upper( HER_PERSONA.PER_NOMBRE1) || ' ' || upper( HER_PERSONA.PER_APELLIDO1) || ' ' || upper( HER_PERSONA.PER_APELLIDO2)) =  '"
										+ palabra + "' "
										+ " or ( upper( HER_PERSONA.PER_NOMBRE2) || ' ' || upper( HER_PERSONA.PER_APELLIDO1) || ' ' || upper( HER_PERSONA.PER_APELLIDO2)) =  '"
										+ palabra + "' "
										+ " or ( upper( HER_PERSONA.PER_NOMBRE1) || ' ' || upper( HER_PERSONA.PER_NOMBRE2) || ' ' || upper( HER_PERSONA.PER_APELLIDO1) || ' ' || upper( HER_PERSONA.PER_APELLIDO2)) =  '"
										+ palabra + "' ";
							}
							sql += " ) ";
						}
					}
					sql += " ) ";
				}
			}

			sql += " ) ";
		}

		if (listaAreasFiltro != null && !listaAreasFiltro.isEmpty()) {
			if (sql.length() > 0) {
				sql = sql + " and ";
			}
			sql += " ( ";
			boolean primero = true;
			for (String linea : listaAreasFiltro) {
				if (!primero) {
					sql += " or ";
				}
				sql += "(";
				List<String> cadena = ReemplazaAcentos.listaPalabrasConTildes(linea.toUpperCase());
				String palabra2 = ReemplazaAcentos.quitarTildes(linea.toUpperCase());
				cadena.add(palabra2);
				boolean primera2 = true;
				for (String palabra : cadena) {
					if (!primera2) {
						sql += " or ";
					} else
						primera2 = false;
					sql += " ( upper(HER_LINEA_INVESTIGACION.LIN_NOMBRE) LIKE '% " + palabra + "') "
							+ "or ( upper( HER_PROYECTO.PRY_NOMBRE)  like  '% " + palabra + "')"
							+ " or ( upper( HER_GRUPO.GRU_NOMBRE)  like  '% " + palabra + "')";

					sql += " or (upper(HER_LINEA_INVESTIGACION.LIN_NOMBRE) LIKE '" + palabra + " %') "
							+ "or ( upper( HER_PROYECTO.PRY_NOMBRE)  like  '" + palabra + " %')"
							+ " or ( upper( HER_GRUPO.GRU_NOMBRE)  like  '" + palabra + " %')";

					sql += " or (upper(HER_LINEA_INVESTIGACION.LIN_NOMBRE) LIKE '% " + palabra + " %') "
							+ "or ( upper( HER_PROYECTO.PRY_NOMBRE)  like  '% " + palabra + " %')"
							+ " or ( upper( HER_GRUPO.GRU_NOMBRE)  like  '% " + palabra + " %')";
				}
				primero = false;
				sql += ")";
			}
			sql += " ) ";
		}

		if (sql.length() > 0
				&& (campoBusqueda.length() > 0 || (listaAreasFiltro != null && !listaAreasFiltro.isEmpty()))) {
			sql = sql + " and HER_PROYECTO.PRY_ID = HER_INVESTIGADOR_PROYECTO.PRY_ID "
					+ " AND HER_INVESTIGADOR_INTERNO.INV_ID = HER_INVESTIGADOR_PROYECTO.INV_ID "
					+ " AND HER_INVESTIGADOR_INTERNO.TDO_ID = HER_INVESTIGADOR_PROYECTO.TDO_ID ";
			fromSql += ", HER_PROYECTO, HER_INVESTIGADOR_PROYECTO";
		}

		if (sql.length() > 0)
			sql = fromSql + " where " + sql;
		listaPersonas = servicioPersona.obtenerInvestigadoresBuscador(sql);
	}

	public void controlCoincidenciaNombre() {
		if (nombreExacto && nombreExacto2) {
			nombreExacto2 = false;
		}
	}

	public void controlCoincidenciaArea() {
		if (nombreExacto && nombreExacto2) {
			nombreExacto = false;
		}
	}

	public void busquedaLaboratorios() {
		String sql = "";
		String sql2 = "";
		if (listaSedesFiltro != null && !listaSedesFiltro.isEmpty()) {
			if (sql.length() > 0) {
				sql = sql + " and ";
			}
			sql += " ( ";
			boolean primero = true;
			for (Dependencia sede : listaSedesFiltro) {
				if (!primero) {
					sql += " or ";
				}
				primero = false;
				sql += " l.SED_ID = '" + sede.getId() + "' ";
			}
			sql += " ) ";
		}
		if (listaFacultadesFiltro != null && !listaFacultadesFiltro.isEmpty()) {
			if (sql.length() > 0) {
				sql = sql + " and ";
			}
			sql += " ( ";
			boolean primero = true;
			for (Dependencia facultad : listaFacultadesFiltro) {
				if (!primero) {
					sql += " or ";
				}
				primero = false;
				sql += " l.LAB_FACULTAD= " + facultad.getId();
			}
			sql += " ) ";
		}
		if (this.campoBusqueda.length() > 0) {
			if (sql.length() > 0) {
				sql += " and ";
			}
			if (nombreExacto) {
				String nombreExacto = this.campoBusqueda.toUpperCase();
				sql = sql + "((UPPER(l.LAB_NOMBRE) like '% " + nombreExacto + " %') or "
						+ "(UPPER(l.LAB_NOMBRE) like '% " + nombreExacto + "') or " + "(UPPER(l.LAB_NOMBRE) like '"
						+ nombreExacto + " %'))";
				sql2 = sql2 + "((UPPER(l.LAB_NOMBRE) like '%" + nombreExacto + " %') or "
						+ "(UPPER(l.LAB_NOMBRE) like '% " + nombreExacto + "') or " + "(UPPER(l.LAB_NOMBRE) like '"
						+ nombreExacto + " %'))";
			} else {
				List<String> cadena = ReemplazaAcentos.listaPalabrasConTildes(this.campoBusqueda.toUpperCase());
				String palabra2 = ReemplazaAcentos.quitarTildes(this.campoBusqueda.toUpperCase());
				cadena.add(palabra2);
				sql2 = sql;
				if (!cadena.isEmpty()) {
					sql += "(";
					sql2 += "(de.LDE_ESPECIALIZADO = 1 AND de.LDE_EN_USO = 1 AND (";
					boolean primera = true;
					for (String palabra : cadena) {
						if (!primera) {
							sql += " or ";
							sql2 += " or ";
						} else
							primera = false;
						sql += " upper(de.LDENS_NOMBRE) LIKE '%" + palabra + "%' OR " + "upper(l.LAB_NOMBRE) LIKE '%"
								+ palabra + "%' OR " + "upper(l.LAB_DESCRIPCION) LIKE '%" + palabra + "%' ";
						sql2 += " upper(de.LDE_EQUIPO) LIKE '%" + palabra + "%'";
					}
					sql += ")";
					sql2 += "))";
				}
			}
		}
		if (laboratorioAcreditado) {

			if (!"".equals(sql.trim())) {
				sql = sql + " and (l.lab_gestion_acreditacion = 50)";
			} else {
				sql = sql + " (l.lab_gestion_acreditacion = 50)";
			}

			if (!"".equals(sql2.trim())) {
				sql2 = sql2 + " and (l.lab_gestion_acreditacion = 50)";
			} else {
				sql2 = sql2 + " (l.lab_gestion_acreditacion = 50)";
			}

		}
		
		if (soloEquiposRobustos) {
			
			String robustos = "L.LAB_ID IN (SELECT LDE.LAB_ID FROM HER_LABORATORIO_DETALLE_EQUIPO LDE WHERE LDE.LAB_ID = L.LAB_ID AND LDE.LDE_ROBUSTO = '1' AND LDE.LDE_DADO_DE_BAJA = 0)"; 

			if (!"".equals(sql.trim())) {
				sql = sql + " and (" + robustos + ")";
			} else {
				sql = sql + " (" + robustos + ")";
			}

			if (!"".equals(sql2.trim())) {
				sql2 = sql2 + " and (" + robustos + ")";
			} else {
				sql2 = sql2 + " (" + robustos + ")";
			}

		}

		if (mostrarAgregarDedicacion) {

			int dedicacionSeleccionada = 0;

			for (String ded : selectedDedicacion) {

				dedicacionSeleccionada++;
				break;

			}

			if (dedicacionSeleccionada != 0) {

				if (!"".equals(sql.trim())) {
					sql = sql + " and (";
				} else {
					sql = sql + "(";
				}
				if (!"".equals(sql2.trim())) {
					sql2 = sql2 + " and (";
				} else {
					sql2 = sql2 + "(";
				}

				dedicacion = 0L;
				dedicacionFiltro = "";

				for (String valor : selectedDedicacion) {

					if (valor.equals(TIPO_DEDICACION_INVESTIGACION)) {

						if (dedicacion > 0) {
							sql = sql + " or ";
							sql2 = sql2 + " or ";
							dedicacionFiltro = dedicacionFiltro + " - Investigación";
						} else {
							dedicacionFiltro = dedicacionFiltro + "Investigación";
						}
						sql = sql + "(l.LAB_DEDICACION_INV > 0)";
						sql2 = sql2 + "(l.LAB_DEDICACION_INV > 0)";
						dedicacion = dedicacion + 1;

					}
					if (valor.equals(TIPO_DEDICACION_EXTENSION)) {
						if (dedicacion > 0) {
							sql = sql + " or ";
							sql2 = sql2 + " or ";
							dedicacionFiltro = dedicacionFiltro + " - Extensión";
						} else {
							dedicacionFiltro = dedicacionFiltro + "Extensión";
						}
						sql = sql + "(l.LAB_DEDICACION_EXT > 0)";
						sql2 = sql2 + "(l.LAB_DEDICACION_EXT > 0)";
						dedicacion = dedicacion + 1;

					}
					if (valor.equals(TIPO_DEDICACION_DOCENCIA)) {
						if (dedicacion > 0) {
							sql = sql + " or ";
							sql2 = sql2 + " or ";
							dedicacionFiltro = dedicacionFiltro + " - Docencia";
						} else {
							dedicacionFiltro = dedicacionFiltro + "Docencia";
						}
						sql = sql + "(l.LAB_DEDICACION_DOC > 0)";
						sql2 = sql2 + "(l.LAB_DEDICACION_DOC > 0)";
						dedicacion = dedicacion + 1;

					}
				}

				sql = sql + ")";
				sql2 = sql2 + ")";
			}

		}
		if (laboratorioInterfacultades && (listaFacultadesFiltro == null
				|| (listaFacultadesFiltro != null && listaFacultadesFiltro.isEmpty()))) {

			if (!"".equals(sql.trim())) {
				sql = sql
						+ " and ( l.lab_facultad in (select dpn_id from her_dependencia where upper(dpn_nombre) like '%LABORATORIOS%'))";
			} else {
				sql = sql
						+ " (l.lab_facultad in (select dpn_id from her_dependencia where upper(dpn_nombre) like '%LABORATORIOS%'))";
			}

			if (!"".equals(sql2.trim())) {
				sql2 = sql2
						+ " and (l.lab_facultad in (select dpn_id from her_dependencia where upper(dpn_nombre) like '%LABORATORIOS%'))";
			} else {
				sql2 = sql2
						+ " (l.lab_facultad in (select dpn_id from her_dependencia where upper(dpn_nombre) like '%LABORATORIOS%'))";
			}
		}
		listaLaboratorios = servicioGeneral.obtenerEnsayosLaboratorioBuscador(sql, sql2, "", listaSedesFiltro,listaFacultadesFiltro);
	}

	public void busquedaProyectos() {
		String sql = "";
		if (this.campoBusqueda.length() > 0) {

			if (nombreExacto) {
				String nombreExacto = this.campoBusqueda.toUpperCase();
				sql = sql + "((UPPER(p.PRY_NOMBRE) like '% " + nombreExacto + " %') or "
						+ "(UPPER(p.PRY_NOMBRE) like '% " + nombreExacto + "') or " + "(UPPER(p.PRY_NOMBRE) like '"
						+ nombreExacto + " %'))";
			} else {

				String tempKey = this.campoBusqueda.toUpperCase();
				String[] keyArray = tempKey.split(" ");

				ArrayList<String> array = eliminarPalabrasComunes(keyArray);

				String likeNombre = "";
				String likeAbstract = "";

				for (int i = 0; i < array.size(); i++) {
					if (i == 0) {
						likeNombre = likeNombre + " UPPER(p.PRY_NOMBRE) like '%" + (String) array.get(i) + "%'";
					} else {
						likeNombre = likeNombre + " or UPPER(p.PRY_NOMBRE) like '%" + (String) array.get(i) + "%'";
					}

				}

				sql = sql + "(UPPER(p.PRY_NOMBRE) like '%" + tempKey + "%' OR " + likeAbstract + likeNombre + ")";
			}

		}
		String responsable = "";
		if (this.apellidoLiderProyecto != null || this.nombreLiderProyecto != null) {
			if (this.apellidoLiderProyecto.length() > 0 || this.nombreLiderProyecto.length() > 0) {

				if (this.nombreLiderProyecto.length() > 0) {
					responsable = nombreLiderProyecto + " ";
					this.nombreLiderProyecto = this.nombreLiderProyecto.trim();
					if (this.nombreLiderProyecto.indexOf(" ") > 0) {
						String cadenaNombres[] = this.nombreLiderProyecto.split(" ");
						String tempNombre1 = "";
						String tempNombre2 = "";
						if (cadenaNombres != null && cadenaNombres.length > 0) {
							if (sql.length() > 0) {
								sql = sql + " and ";
							}
							if (cadenaNombres.length == 1) {
								tempNombre1 = cadenaNombres[0];
								tempNombre1 = tempNombre1.trim();
								sql = sql + " (upper(inves.per_nombre1) like '%" + tempNombre1.toUpperCase() + "%'"
										+ " or upper(inves.per_nombre2) like '%" + tempNombre1.toUpperCase() + "%') ";
							}
							if (cadenaNombres.length == 2) {
								tempNombre1 = cadenaNombres[0];
								tempNombre2 = cadenaNombres[1];
								tempNombre1 = tempNombre1.trim();
								tempNombre2 = tempNombre2.trim();
								sql = sql + " (upper(inves.per_nombre1) like '%" + tempNombre1.toUpperCase()
										+ "%' or upper(inves.per_nombre2) like '%" + tempNombre2.toUpperCase() + "%' )";
							}
						}
					} else {
						if (sql.length() > 0) {
							sql = sql + " and ";
						}
						sql = sql + " (upper(inves.per_nombre1) like '%" + nombreLiderProyecto.toUpperCase() + "%'"
								+ " or upper(inves.per_nombre2) like '%" + nombreLiderProyecto.toUpperCase() + "%') ";
					}
				}

				if (this.apellidoLiderProyecto != null && this.apellidoLiderProyecto.length() > 0) {
					this.apellidoLiderProyecto = this.apellidoLiderProyecto.trim();
					responsable = responsable + apellidoLiderProyecto;
					if (this.apellidoLiderProyecto.indexOf(" ") > 0) {
						String cadenaApellidos[] = this.apellidoLiderProyecto.split(" ");
						String tempApellido1 = "";
						String tempApellido2 = "";
						if (cadenaApellidos != null && cadenaApellidos.length > 0) {
							if (sql.length() > 0) {
								sql = sql + " and ";
							}
							if (cadenaApellidos.length == 1) {
								tempApellido1 = cadenaApellidos[0];
								tempApellido1 = tempApellido1.trim();
								sql = sql + "  (upper(inves.per_apellido1) like '%" + tempApellido1.toUpperCase()
										+ "%' " + " or upper(inves.per_apellido2) like '%" + tempApellido1.toUpperCase()
										+ "%') ";
							}
							if (cadenaApellidos.length == 2) {
								tempApellido1 = cadenaApellidos[0];
								tempApellido2 = cadenaApellidos[1];
								tempApellido1 = tempApellido1.trim();
								tempApellido2 = tempApellido2.trim();
								sql = sql + " (upper(inves.per_apellido1) like '%" + tempApellido1.toUpperCase() + "%'"
										+ " or upper(inves.per_apellido2) like '%" + tempApellido2.toUpperCase()
										+ "%') ";
							}
						}
					} else {
						if (sql.length() > 0) {
							sql = sql + " and ";
						}
						sql = sql + " (upper(inves.per_apellido1) like '%" + apellidoLiderProyecto.toUpperCase() + "%'"
								+ " or upper(inves.per_apellido2) like '%" + apellidoLiderProyecto.toUpperCase()
								+ "%') ";
					}

				}
			}
		}
		String sedep = "";
		if (listaSedesFiltro != null && !listaSedesFiltro.isEmpty()) {
			if (sql.length() > 0) {
				sql = sql + " and ";
			}
			sql += " ( ";
			boolean primero = true;
			for (Dependencia sede : listaSedesFiltro) {
				if (!primero) {
					sql += " or ";
				}
				primero = false;
				sql += " s.SED_ID = '" + sede.getId() + "'";
				sedep = sede.getSede().getCiudad();
			}
			sql += " ) ";
		}

		String facultadp = "";
		if (listaFacultadesFiltro != null && !listaFacultadesFiltro.isEmpty()) {
			if (sql.length() > 0) {
				sql = sql + " and ";
			}
			sql += " ( ";
			boolean primero = true;
			for (Dependencia facultad : listaFacultadesFiltro) {
				if (!primero) {
					sql += " or ";
				}
				primero = false;
				sql += " d.DPN_ID= '" + facultad.getId() + "' " + " or d.DPN_ID_2 = '" + facultad.getId() + "' ";
				facultadp = facultad.getNombre();
			}
			sql += " ) ";
		}

		String areap = "";
		if (listaAreasFiltro != null && !listaAreasFiltro.isEmpty()) {
			if (sql.length() > 0) {
				sql = sql + " and ";
			}
			sql = sql
					+ " Exists ( select * from her_proyecto_clas_con pclc,her_clasificacion_conocimiento clc where p.PRY_ID  = pclc.PRY_ID and clc.CLC_ID = pclc.CLC_ID and ";
			sql += " ( ";
			boolean primero = true;
			for (String area : listaAreasFiltro) {
				if (!primero) {
					sql += " or ";
				}
				primero = false;
				sql += " clc.CLC_NOMBRE like '%" + area.toUpperCase() + "%' ";
				areap = area.toUpperCase();
			}
			sql += " ) )";
		}

		listaProyecto = servicioProyecto.obtenerProyectoBuscador(sql, facultadp.toUpperCase(), sedep.toUpperCase(),
				responsable.toUpperCase(), areap, campoBusqueda.toUpperCase());
		Collections.sort(listaProyecto, Proyecto.PryNombreComparator);
	}

	public void busquedaConvocatorias() {
		String sqlInternas = "";
		String sqlExternas = "";

		if (!esListaVacia(listaSedesFiltro) && esListaVacia(listaFacultadesFiltro)) {
			listaEntidadesFiltro = new ArrayList<FuenteFinanciacion>();
			if (sqlInternas.length() > 0) {
				sqlInternas = sqlInternas + " and ";
			}
			sqlInternas += " ( ";
			boolean primero = true;
			for (Dependencia sede : listaSedesFiltro) {
				if (!primero) {
					sqlInternas += " or ";
				}
				primero = false;
				sqlInternas += " HER_CONVOCATORIA_PADRE.DNP_ID = '" + sede.getId() + "'";
			}
			sqlInternas += " ) ";

		}

		if (!esListaVacia(listaFacultadesFiltro)) {
			listaEntidadesFiltro = new ArrayList<FuenteFinanciacion>();
			if (sqlInternas.length() > 0) {
				sqlInternas = sqlInternas + " and ";
			}
			sqlInternas += " ( ";
			boolean primero = true;
			for (Dependencia facultad : listaFacultadesFiltro) {
				if (!primero) {
					sqlInternas += " or ";
				}
				primero = false;
				sqlInternas += " HER_CONVOCATORIA_PADRE.DNP_ID = '" + facultad.getId() + "'";
			}
			sqlInternas += " ) ";
		}

		if (esListaVacia(listaSedesFiltro) && esListaVacia(listaFacultadesFiltro)) {
			if (!esListaVacia(listaEntidadesFiltro)) {
				if (sqlExternas.length() > 0) {
					sqlExternas = sqlExternas + " and ";
				}
				sqlExternas += " ( ";
				boolean primero = true;
				for (FuenteFinanciacion entidad : listaEntidadesFiltro) {
					if (!primero) {
						sqlExternas += " or ";
					}
					primero = false;
					sqlExternas += " HER_CONVOCATORIA_EXTERNA.FFI_ID = '" + entidad.getId() + "'";
				}
				sqlExternas += " ) ";

				if (!esListaVacia(listaEstadosFiltro)) {
					if (sqlExternas.length() > 0) {
						sqlExternas = sqlExternas + " and ";
					}

					sqlExternas += " ( ";

					for (String estadoBusqueda : listaEstadosFiltro) {
						String estado = "";
						if (estadoBusqueda.equals(ACTIVA))
							estado = "A";
						if (estadoBusqueda.equals(INACTIVA))
							estado = "I";
						sqlExternas += " HER_CONVOCATORIA_EXTERNA.CEX_ESTADO ='" + estado + "' ";
					}
					sqlExternas += " ) ";
				}
				if (this.campoBusqueda.length() > 0) {
					if (sqlExternas.length() > 0) {
						sqlExternas = sqlExternas + " and (";
					}
					sqlExternas += " upper(HER_CONVOCATORIA_EXTERNA.CEX_NOMBRE) LIKE '%"
							+ this.campoBusqueda.toUpperCase()
							+ "%' OR upper(HER_CONVOCATORIA_EXTERNA.CEX_NUMERO) LIKE '%"
							+ this.campoBusqueda.toUpperCase() + "%')";
				}
			}
		}
		if (esListaVacia(listaSedesFiltro) && esListaVacia(listaFacultadesFiltro)
				&& esListaVacia(listaEntidadesFiltro)) {
			if (!esListaVacia(listaEstadosFiltro)) {
				if (sqlInternas.length() > 0) {
					sqlInternas = sqlInternas + " and ";
				}
				if (sqlExternas.length() > 0) {
					sqlExternas = sqlExternas + " and ";
				}
				sqlInternas += " ( ";
				sqlExternas += " ( ";
				for (String estadoBusqueda : listaEstadosFiltro) {
					String estado = "";
					if (estadoBusqueda.equals(ACTIVA))
						estado = "A";
					if (estadoBusqueda.equals(INACTIVA))
						estado = "I";
					sqlInternas += " HER_CONVOCATORIA_PADRE.CNP_ESTADO = '" + estado + "' ";
					sqlExternas += " HER_CONVOCATORIA_EXTERNA.CEX_ESTADO ='" + estado + "' ";
				}
				sqlInternas += " ) ";
				sqlExternas += " ) ";
			}
			if (this.campoBusqueda.length() > 0) {
				if (sqlInternas.length() > 0) {
					sqlInternas += " and ";
				}
				if (sqlExternas.length() > 0) {
					sqlExternas = sqlExternas + " and (";
				} else {
					sqlExternas = sqlExternas + "(";
				}
				sqlInternas += " upper(HER_CONVOCATORIA_PADRE.CNP_TITULO) LIKE '%" + this.campoBusqueda.toUpperCase()
						+ "%' ";
				sqlExternas += " upper(HER_CONVOCATORIA_EXTERNA.CEX_NOMBRE) LIKE '%" + this.campoBusqueda.toUpperCase()
						+ "%' OR upper(HER_CONVOCATORIA_EXTERNA.CEX_NUMERO) LIKE '%" + this.campoBusqueda.toUpperCase()
						+ "%')";
			}
		}

		if (!esListaVacia(listaSedesFiltro) || !esListaVacia(listaFacultadesFiltro)) {
			if (!esListaVacia(listaEstadosFiltro)) {
				if (sqlInternas.length() > 0) {
					sqlInternas = sqlInternas + " and ";
				}

				sqlInternas += " ( ";

				for (String estadoBusqueda : listaEstadosFiltro) {
					String estado = "";
					if (estadoBusqueda.equals(ACTIVA))
						estado = "A";
					if (estadoBusqueda.equals(INACTIVA))
						estado = "I";
					sqlInternas += " HER_CONVOCATORIA_PADRE.CNP_ESTADO = '" + estado + "' ";
				}
				sqlInternas += " ) ";
			}

			if (this.campoBusqueda.length() > 0) {
				if (sqlInternas.length() > 0) {
					sqlInternas += " and ";
				}
				sqlInternas += " upper(HER_CONVOCATORIA_PADRE.CNP_TITULO) LIKE '%" + this.campoBusqueda.toUpperCase()
						+ "%' ";
			}
		}

		if ("".equals(sqlInternas.trim()) && "".equals(sqlExternas.trim())) {
			sqlInternas = " HER_CONVOCATORIA_PADRE.CNP_TITULO LIKE '%%' ";
			sqlExternas = " (HER_CONVOCATORIA_EXTERNA.CEX_NOMBRE LIKE '%%')";
		}

		listaConvocatorias = servicioProyecto.obtenerConvocatorias(sqlInternas, sqlExternas);
		Collections.sort(listaConvocatorias, ConvocatoriaExterna.ConvNombreEstado);
	}

	public void busquedaSemilleros() {
		String sql = "";
		if (campoBusqueda != null && campoBusqueda.trim().length() > 0) {
			if (sql.length() > 0)
				sql = sql + " and ";
			List<String> cadena = ReemplazaAcentos.listaPalabrasConTildes(this.campoBusqueda.toUpperCase());
			String palabra2 = ReemplazaAcentos.quitarTildes(this.campoBusqueda.toUpperCase());
			cadena.add(palabra2);
			if (!cadena.isEmpty()) {
				sql += "(";
				boolean primera = true;
				for (String palabra : cadena) {
					if (!primera) {
						sql += " or ";
					} else
						primera = false;
					sql += "upper(hs.nombre) like '%" + palabra + "%'" + " or upper(hs.presentacion) like '%" + palabra
							+ "%'" + " or upper(hs.objetivoGeneral) like '%" + palabra + "%'"
							+ " or upper(hs.enfoque) like '%" + palabra + "%'";
				}
				sql += ")";
			}
		}
		boolean first = true;
		if (filtroLiderGrupo) {
			if (this.nombreLiderGrupo.length() > 0) {
				if (sql.length() > 0) {
					sql = sql + " and ";
				}
				for (String cadena : nombreLiderGrupo.split(" ")) {
					if (!first) {
						sql += " or";
					}
					sql += " lower(hsi.integrante.nombre1) like '%" + cadena.toLowerCase()
							+ "%' or lower(hsi.integrante.nombre2) like '%" + cadena.toLowerCase() + "%'";
					if (first) {
						first = false;
					}
				}
			}
			if (this.apellidoLiderGrupo.length() > 0) {
				if (sql.length() > 0) {
					sql = sql + " and ";
				}
				first = true;
				for (String cadena : apellidoLiderGrupo.split(" ")) {
					if (!first) {
						sql += " or";
					}
					sql += " lower(hsi.integrante.nombre1) like '%" + cadena.toLowerCase()
							+ "%' or lower(hsi.integrante.nombre2) like '%" + cadena.toLowerCase() + "%'";
					if (first) {
						first = false;
					}
				}
			}
		}
		if (sql != "" || listaSedesFiltro != null || listaFacultadesFiltro != null) {
			String query = "select hs from Semillero hs, SemilleroIntegrante hsi where hs.id=hsi.semillero.id and hsi.tipo in ('DD')";
			if (sql.length() > 0)
				query += " and " + sql;
			listaSemilleros = new ArrayList<Semillero>();
			List<Semillero> results = this.servicioGeneral.obtenerObjetos(Semillero.class, query);
			String ids = "";
			if (listaSedesFiltro.size() == 0 && listaFacultadesFiltro.size() == 0) {
				listaSemilleros.addAll(results);
			}
			if (listaFacultadesFiltro != null && !listaFacultadesFiltro.isEmpty()) {
				if (!listaSemilleros.isEmpty()) {
					listaSemilleros = new ArrayList<Semillero>();
				}
				for (Semillero s : results) {
					for (Dependencia facultad : listaFacultadesFiltro) {
						if (s.getLider().getDependencia().getFacultad().getId().equals(facultad.getId())) {
							if (!ids.contains(s.getId().toString())) {
								listaSemilleros.add(s);
								ids += s.getId() + " ";
								break;
							}
						}
					}
				}
			} else if (listaSedesFiltro != null && !listaSedesFiltro.isEmpty()) {
				if (!listaSemilleros.isEmpty()) {
					listaSemilleros = new ArrayList<Semillero>();
				}
				for (Semillero s : results) {
					for (Dependencia sede : listaSedesFiltro) {
						if (s.getLider().getDependencia().getSede().getId().equals(sede.getSede().getId())) {
							if (!ids.contains(s.getId().toString())) {
								listaSemilleros.add(s);
								ids += s.getId() + " ";
								break;
							}
						}
					}
				}
			}
		}
	}

	public ArrayList<String> eliminarPalabrasComunes(String[] arreglo) {

		ArrayList<String> palabrasComunes = new ArrayList<String>();
		String fuente = ("a adonde al ante así aunque bajo bien cabe como con contra cuando de desde donde durante e el en entre ésta éstas éste esto éstos hacia hasta la las los luego más mediante ni o para pero por porque puesto que según si sin sino so sobre tan tras u un una unas unos y ya");
		String[] arregloComunes = fuente.split(" ");
		for (int i = 0; i < arregloComunes.length; i++) {
			palabrasComunes.add(arregloComunes[i].toUpperCase());
		}
		ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i < arreglo.length; i++) {
			if (!palabrasComunes.contains(arreglo[i])) {
				result.add(arreglo[i]);
			}
		}
		return result;
	}

	public void cargarFacultadesSede() {
		facultadAgregar = "";
		if (!esCadenaVacia(sedeAgregar)) {
			listaFacultades = this.servicioGeneral.obtenerFacultades(new Dependencia(sedeAgregar.trim()));
			listaFacultadesItem = new SelectItem[listaFacultades.size() + 1];
			listaFacultadesItem[0] = new SelectItem("", "Seleccione facultad");
			for (int i = 1; i < listaFacultades.size() + 1; i++) {
				Dependencia facultad = (Dependencia) listaFacultades.get(i - 1);
				listaFacultadesItem[i] = new SelectItem(facultad.getId(), facultad.getNombre());
			}
		} else
			listaFacultadesItem = null;
	}

	public void cargarNuevaArea() {
		desactivarAgregarGeneral();
		mostrarAgregarArea = true;
		areaAgregar = "";
	}

	public void cargarNuevaAreaTematica() {
		mostrarAgregarAreaTematica = true;
		mostrarAreaTematicaTexto = false;
		areaTematicaAgregar = "";
		cargarOpcionesAreasTematicas();
	}

	public void quitarAreaTematica() {
		mostrarAgregarAreaTematica = false;
		areaTematicaAgregar = "";
	}

	public void cargarNuevaFacultad() {
		desactivarAgregarGeneral();
		mostrarAgregarFacultad = true;
		facultadAgregar = "";
		cargarOpcionesSedes();
	}

	public void cargarLiderSemillero() {
		desactivarAgregarGeneral();
		filtroLiderGrupo = true;
//		facultadAgregar = "";
//		cargarOpcionesSedes();
	}

	public void cargarNuevaLinea() {
		desactivarAgregarGeneral();
		mostrarAgregarLinea = true;
		lineaAgregar = "";
	}

	public void cargarNuevaSede() {
		desactivarAgregarGeneral();
		mostrarAgregarSede = true;
		sedeAgregar = "";
		cargarOpcionesSedes();
	}

	public void cargarDedicacionLab() {
		desactivarAgregarGeneral();
		mostrarAgregarDedicacion = true;
		cargarTiposDedicacion();
	}

	private void cargarTiposDedicacion() {

		listaTiposDedicacion = new ArrayList<SelectItem>();

		listaTiposDedicacion.add(new SelectItem(TIPO_DEDICACION_DOCENCIA, "Docencia"));
		listaTiposDedicacion.add(new SelectItem(TIPO_DEDICACION_EXTENSION, "Extensión"));
		listaTiposDedicacion.add(new SelectItem(TIPO_DEDICACION_INVESTIGACION, "Investigación"));

	}

	private void cargarOpcionesAreasTematicas() {
		if (listaAreaTematicaItem != null && listaAreaTematicaItem.length > 0) {
			return;
		} else {
			String consulta = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo = 'EJE_TEMATICO' order by dd.descripcion";
			List<DominioDetalle> lista = servicioGeneral.obtenerObjetos(DominioDetalle.class, consulta);
			listaAreaTematicaItem = new SelectItem[lista.size() + 1];
			listaAreaTematicaItem[0] = new SelectItem("", "Seleccione un eje temático");
			for (int i = 1; i < lista.size() + 1; i++) {
				DominioDetalle dd = (DominioDetalle) lista.get(i - 1);
				listaAreaTematicaItem[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
			}
		}
	}

	private void cargarOpcionesSedes() {
		if (listaSedesItem != null && listaSedesItem.length > 0) {
			return;
		} else {
			listaSedes = (List<Dependencia>) servicioGeneral.obtenerSedes();
			listaSedesItem = new SelectItem[listaSedes.size() + 1];
			listaSedesItem[0] = new SelectItem("", "Seleccione sede");
			for (int i = 1; i < listaSedes.size() + 1; i++) {
				Dependencia sede = (Dependencia) listaSedes.get(i - 1);
				String nombreSede = sede.getNombre().substring(0, 1).toUpperCase()
						+ sede.getNombre().substring(1, sede.getNombre().length());
				listaSedesItem[i] = new SelectItem(sede.getId(), nombreSede);
			}
		}
	}

	private void cargarOpcionesEntidades() {
		if (listaEntidadesItem != null && listaEntidadesItem.length > 0) {
			return;
		} else {
			listaEntidades = cargarEntidadesExternas(CONSULTA_ENTIDADES_EXTERNAS_SIN_UNAL);
			listaEntidadesItem = new SelectItem[listaEntidades.size() + 1];
			listaEntidadesItem[0] = new SelectItem("", "Seleccione entidad");
			for (int i = 1; i < listaEntidades.size() + 1; i++) {
				FuenteFinanciacion entidad = (FuenteFinanciacion) listaEntidades.get(i - 1);
				String nombreEntidad = entidad.getDescripcion().substring(0, 1).toUpperCase()
						+ entidad.getDescripcion().substring(1, entidad.getDescripcion().length());
				listaEntidadesItem[i] = new SelectItem(entidad.getId(), nombreEntidad);
			}
		}
	}

	private void ocultarResultados() {
		mostrarResultadosInvestigador = false;
		mostrarResultadosProyectos = false;
		mostrarResultadosGrupos = false;
		mostrarResultadosLaboratorios = false;
		mostrarResultadosConvocatorias = false;
		mostrarResultadosSemilleros = false;
	}

	public void cargarResultadosInvestigador() {
		tipoPalabraClave = TIPOINVESTIGADORES;
		ocultarResultados();
		mostrarResultadosInvestigador = true;
		mostrarAgregarAreaTematica = false;
	}

	public void cargarResultadosProyecto() {
		tipoPalabraClave = TIPOPROYECTOS;
		ocultarResultados();
		mostrarResultadosProyectos = true;
		mostrarAgregarAreaTematica = false;
	}

	public void cargarResultadosGrupo() {
		tipoPalabraClave = TIPOGRUPOS;
		ocultarResultados();
		mostrarResultadosGrupos = true;
		mostrarAgregarAreaTematica = false;
	}

	public void cargarResultadosLaboratorio() {
		tipoPalabraClave = TIPOLABORATORIOS;
		ocultarResultados();
		mostrarResultadosLaboratorios = true;
		mostrarAgregarAreaTematica = false;
	}

	public void cargarResultadosConvocatoria() {
		tipoPalabraClave = TIPOCONVOCATORIAS;
		ocultarResultados();
		mostrarResultadosConvocatorias = true;
		mostrarAgregarAreaTematica = false;
	}
	
	public void cargarResultadosSemilleros() {
		tipoPalabraClave = TIPOSEMILLEROS;
		ocultarResultados();
		mostrarResultadosSemilleros = true;
	}

	public boolean isMostrarResultadosInvestigador() {
		return mostrarResultadosInvestigador;
	}

	public void setMostrarResultadosInvestigador(boolean mostrarResultadosInvestigador) {
		this.mostrarResultadosInvestigador = mostrarResultadosInvestigador;
	}

	public boolean isMostrarResultadosProyectos() {
		return mostrarResultadosProyectos;
	}

	public void setMostrarResultadosProyectos(boolean mostrarResultadosProyectos) {
		this.mostrarResultadosProyectos = mostrarResultadosProyectos;
	}

	public boolean isMostrarResultadosGrupos() {
		return mostrarResultadosGrupos;
	}

	public void setMostrarResultadosGrupos(boolean mostrarResultadosGrupos) {
		this.mostrarResultadosGrupos = mostrarResultadosGrupos;
	}

	public boolean isMostrarResultadosLaboratorios() {
		return mostrarResultadosLaboratorios;
	}

	public void setMostrarResultadosLaboratorios(boolean mostrarResultadosLaboratorios) {
		this.mostrarResultadosLaboratorios = mostrarResultadosLaboratorios;
	}

	public String consultarPaginaPersona() {

		Persona persona = (Persona) personaSeleccionada;

		String emailPersona;
		int arroba = persona.getEmail().indexOf("@");
		if (arroba != -1) {

			emailPersona = persona.getEmail().substring(0, arroba);
		} else {
			emailPersona = persona.getEmail();
		}

		sesion.removeAttribute("manejadorPersonaBusqueda");
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext extContext = context.getExternalContext();
		String viewId = "/pages/Docentes/Docente.xhtml";
		try {
			viewId = extContext.getRequestContextPath() + viewId + '?' + "u" + "=" + emailPersona;
			String urlLink = context.getExternalContext().encodeActionURL(viewId);
			extContext.redirect(urlLink);
		} catch (IOException e) {
			extContext.log(getClass().getName() + ".invokeRedirect", e);
		}
		return null;

	}

	public String consultarPaginaInvestigador() {

		Persona persona = (Persona) personaSeleccionada;

		IdPersona idInvestigador = persona.getId();

		Investigador investigadorActual;
		investigadorActual = servicioPersona.obtenerProyectosGruposInvestigador(idInvestigador);

		sesion.setAttribute("investigadorBusqueda", investigadorActual);

		return "successPersonaBuscador";

	}

	public String consultarPaginaLaboratorios() {

		LaboratorioDetalleEnsayosServicios ensayoAcual = ensayoSeleccionado;

		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext extContext = context.getExternalContext();
		String viewId = "/pages/Consultas/EnsayoLaboratorio.xhtml";
		try {
			viewId = extContext.getRequestContextPath() + viewId + '?' + "idEnsayo" + "=" + ensayoAcual.getId();
			String urlLink = context.getExternalContext().encodeActionURL(viewId);
			extContext.redirect(urlLink);
		} catch (IOException e) {
			extContext.log(getClass().getName() + ".invokeRedirect", e);
		}
		return null;

	}

	public String consultarPaginaGrupos() {

		Grupo grupoActual = (Grupo) grupoSeleccionado;

		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext extContext = context.getExternalContext();
		String viewId = "/pages/Consultas/Grupo.jsf";
		try {
			viewId = extContext.getRequestContextPath() + viewId + '?' + "idGrupo" + "=" + grupoActual.getId();
			String urlLink = context.getExternalContext().encodeActionURL(viewId);
			extContext.redirect(urlLink);
		} catch (IOException e) {
			extContext.log(getClass().getName() + ".invokeRedirect", e);
		}
		return null;
	}

	public String consultarPaginaProyectos() {

		Proyecto proyectoActual = proyectoSeleccionado;

		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext extContext = context.getExternalContext();
		String viewId = "/pages/Consultas/Proyecto.xhtml";
		try {
			viewId = extContext.getRequestContextPath() + viewId + '?' + "idProyecto" + "=" + proyectoActual.getId()
					+ "&opcion=1";
			String urlLink = context.getExternalContext().encodeActionURL(viewId);
			extContext.redirect(urlLink);
		} catch (IOException e) {
			extContext.log(getClass().getName() + ".invokeRedirect", e);
		}
		return null;

	}

	public void cargarNuevoEstado() {
		desactivarAgregarGeneral();
		mostrarAgregarEstado = true;
		setEstadoAgregar("");
	}

	public void cargarNuevaEntidad() {
		desactivarAgregarGeneral();
		mostrarAgregarEntidad = true;
		cargarOpcionesEntidades();
		setEntidadAgregar("");
	}

	public void eliminarEstado() {
		reiniciarOpcionesBusqueda();
		listaEstadosFiltro.remove(estadoSeleccionado);
		buscar();
	}

	public void eliminarEntidad() {
		reiniciarOpcionesBusqueda();
		listaEntidadesFiltro.remove(entidadSeleccionada);
		buscar();
	}

	public void desactivarAgregarGeneral() {
		mostrarAgregarFacultad = false;
		mostrarAgregarSede = false;
		mostrarAgregarArea = false;
		mostrarAgregarAreaTematica = false;
		mostrarAgregarDedicacion = false;
		mostrarAgregarEstado = false;
		mostrarAgregarEntidad = false;

	}

	public void eliminarFacultad() {
		reiniciarOpcionesBusqueda();
		Dependencia facultadActual = facultadSeleccionada;
		listaFacultadesFiltro.remove(facultadActual);
		listaFacultadesFiltro = new ArrayList<Dependencia>();
		buscar();
	}

	public void eliminarAreaTematica() {
		reiniciarOpcionesBusqueda();
		mostrarAreaTematicaTexto = false;
		areaTematicaAgregar = "";
		textoMostrarAreaTematica = "";
		buscar();
	}

	public void eliminarArea() {
		reiniciarOpcionesBusqueda();
		listaAreasFiltro.remove(areaSeleccionada);
		buscar();
	}

	public void eliminarLinea() {
		reiniciarOpcionesBusqueda();
		listaLineasFiltro.remove(lineaSeleccionada);
		buscar();
	}

	public void eliminarSede() {
		reiniciarOpcionesBusqueda();
		Dependencia sedeActual = sedeSeleccionada;
		listaSedesFiltro.remove(sedeActual);
		listaFacultadesFiltro = new ArrayList<Dependencia>();
		buscar();
	}

	public Dependencia encontrarFacultad(String id) {
		if (listaFacultades != null) {
			for (Dependencia facultad : listaFacultades) {
				if (facultad.getId().equals(id)) {
					return facultad;
				}
			}
		}
		return null;
	}

	public Dependencia encontrarSede(String id) {
		if (listaSedes != null) {
			for (Dependencia sede : listaSedes) {
				if (sede.getId().equals(id)) {
					return sede;
				}
			}
		}
		if ("1".equals(id)) {
			Dependencia sede = new Dependencia();
			sede.setId(id);
			sede.setNombre("Nivel nacional");
			return sede;
		}
		return null;
	}

	public FuenteFinanciacion encontrarEntidad(String id) {
		if (listaEntidades != null) {
			for (FuenteFinanciacion entidad : listaEntidades) {
				if (entidad.getId().equals(id)) {
					return entidad;
				}
			}
		}
		return null;
	}

	public List<String> obtenerPalabraClaves(String nombre) {
		return (List<String>) servicioGeneral.obtenerLineasEmpezandoCon(nombre);
	}

	public void reiniciarOpcionesBusqueda() {
		sedeAgregar = "";
		facultadAgregar = "";
		areaAgregar = "";
		lineaAgregar = "";
		nombreLiderGrupo = "";
		apellidoLiderGrupo = "";
		nombreLiderProyecto = "";
		apellidoLiderProyecto = "";
		areaTematicaAgregar = "";
		estadoAgregar = "";
	}

	public void cambiarCategoria() {
		try {
			if (tipo == PALABRASCLAVE) {
				reiniciarBusqueda();
			} else if (tipo == TIPOCOLECCIONES) {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("http://www.hermes.unal.edu.co/pages/Consultas/BuscadorColecciones.xhtml");
			} else if (tipo == TIPOECP) {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("https://hermesextension.unal.edu.co/ords/f?p=116:8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getNumeroResultados() {
		int $total = 0;
		if (tipo == PALABRASCLAVE) {

			if (listaPersonas != null) {
				$total = $total + listaPersonas.size();
			}

			if (listaProyecto != null) {
				$total = $total + listaProyecto.size();
			}
			if (listaGrupos != null) {
				$total = $total + listaGrupos.size();
			}

			if (listaLaboratorios != null) {
				$total = $total + listaLaboratorios.size();
			}

			if (listaConvocatorias != null) {
				$total = $total + listaConvocatorias.size();
			}
			
			if (listaSemilleros != null) {
				$total = $total + listaSemilleros.size();
			}
		}
		return $total;
	}

	public void reiniciarBusqueda() {
		tipo = 0;
		reiniciarOpcionesBusqueda();
		campoBusqueda = "";
		listaLineasFiltro = new ArrayList<String>();
		listaAreasFiltro = new ArrayList<String>();
		listaSedesFiltro = new ArrayList<Dependencia>();
		listaFacultadesFiltro = new ArrayList<Dependencia>();
		listaPersonas = new ArrayList<Persona>();
		listaProyecto = new ArrayList<Proyecto>();
		listaConvocatorias = new ArrayList<ConvocatoriaExterna>();
		listaGrupos = new ArrayList<Grupo>();
		listaLaboratorios = new ArrayList<LaboratorioDetalleEnsayosServicios>();
		listaEstadosFiltro = new ArrayList<String>();
		banderaResultadosBusqueda = false;
		filtroLiderGrupo = false;
		filtroLiderProyecto = false;
		banderaBusqueda = false;
		areaTematicaAgregar = "";
		textoMostrarAreaTematica = "";
		mostrarAgregarAreaTematica = false;
		mostrarAreaTematicaTexto = false;
	}

	// Reportes en pdf
	public void reporteInvestigadores() throws SQLException {

		Persona inv = new Persona();
		Iterator<Persona> i;
		String ids = "";
		i = listaPersonas.iterator();
		int contador = 1;
		while (i.hasNext() && contador <= 200) {
			inv = (Persona) i.next();
			ids = ids + " " + inv.getId().getDocumento().toString();
			contador++;
		}

		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("ListaId", ids);
		String sede = "0";
		if (sedeAgregar != null && !"".equals(sedeAgregar.trim())) {
			sede = sedeAgregar;
		}

		String facultad = "1";
		if (facultadAgregar != null && !"".equals(facultadAgregar.trim())) {
			facultad = facultadAgregar;
		} else if (sedeAgregar != null && !"".equals(sedeAgregar.trim())) {
			facultad = "0";
		}

		String areas = "";
		if (!esListaVacia(listaAreasFiltro)) {
			String area;
			Iterator<String> a;
			a = listaAreasFiltro.iterator();
			int contadorAreasIngresadas = 1;
			while (a.hasNext()) {
				area = (String) a.next();
				areas = areas + " " + area;
				contadorAreasIngresadas++;
			}
		} else {
			areas = "Todas";
		}

		String totalResultados = "0";
		if (listaPersonas != null) {
			Integer tot = listaPersonas.size();
			totalResultados = tot.toString();
		}

		Integer totalReporte = contador - 1;

		r.adicionarParametro("sed", sede);
		r.adicionarParametro("fac", facultad);
		r.adicionarParametro("area", areas);
		r.adicionarParametro("total", totalResultados);
		r.adicionarParametro("totalRep", totalReporte.toString());

		r.setNombreReporte("/portafolio/reporte_investigadores");
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
	}

	// Reportes proyectos
	public void reporteProyectos() throws SQLException {

		Proyecto pry = new Proyecto();
		Iterator<Proyecto> i;
		String ids = "";
		String ids2 = "";
		i = listaProyecto.iterator();
		int contador = 1;
		while (i.hasNext() && contador <= 200) {
			pry = (Proyecto) i.next();
			if (pry.isEsInvestigacion()) {
				ids = ids + " " + pry.getId().toString();
			} else {
				ids2 = ids2 + " " + pry.getId().toString();
			}
			contador++;
		}

		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("ListaId", ids);
		r.adicionarParametro("ListaId2", ids2);
		String sede = "0";
		if (sedeAgregar != null && !"".equals(sedeAgregar.trim())) {
			sede = sedeAgregar;
		}

		String facultad = "1";
		if (facultadAgregar != null && !"".equals(facultadAgregar.trim())) {
			facultad = facultadAgregar;
		} else if (sedeAgregar != null && !"".equals(sedeAgregar.trim())) {
			facultad = "0";
		}

		String areas = "";
		if (!esListaVacia(listaAreasFiltro)) {
			String area;
			Iterator<String> a;
			a = listaAreasFiltro.iterator();
			int contadorAreas = 1;
			while (a.hasNext() && contador <= 10) {
				area = (String) a.next();
				areas = areas + " " + area;
				contadorAreas++;
			}
		} else {
			areas = "Todas";
		}

		String totalResultados = "0";
		if (listaProyecto != null) {
			Integer tot = listaProyecto.size();
			totalResultados = tot.toString();
		}

		String lider = "No";
		if ((nombreLiderProyecto != null && !"".equals(nombreLiderProyecto.trim()))
				|| (apellidoLiderProyecto != null && !"".equals(apellidoLiderProyecto.trim()))) {
			lider = "Sí";
		}

		Integer totalReporte = contador - 1;

		r.adicionarParametro("sed", sede);
		r.adicionarParametro("fac", facultad);
		r.adicionarParametro("area", areas);
		r.adicionarParametro("total", totalResultados);
		r.adicionarParametro("totalRep", totalReporte.toString());
		r.adicionarParametro("lider", lider);

		r.setNombreReporte("/portafolio/reporte_proyectos");
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
	}

	// Reportes grupos
	public void reporteGrupos() throws SQLException {

		Grupo gru = new Grupo();
		Iterator<Grupo> i;
		String ids = "";
		i = listaGrupos.iterator();
		int contador = 1;
		while (i.hasNext() && contador <= 200) {
			gru = (Grupo) i.next();
			ids = ids + " " + gru.getId().toString();
			contador++;
		}

		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("ListaId", ids);
		String sede = "0";
		if (sedeAgregar != null && !"".equals(sedeAgregar.trim())) {
			sede = sedeAgregar;
		}

		String facultad = "1";
		if (facultadAgregar != null && !"".equals(facultadAgregar.trim())) {
			facultad = facultadAgregar;
		} else if (sedeAgregar != null && !"".equals(sedeAgregar.trim())) {
			facultad = "0";
		}

		String lineas = "";
		if (listaLineasFiltro != null && !listaLineasFiltro.isEmpty()) {
			String area;
			Iterator<String> a;
			a = listaLineasFiltro.iterator();
			int contadorAreas = 1;
			while (a.hasNext() && contador <= 10) {
				area = (String) a.next();
				lineas = lineas + " " + area;
				contadorAreas++;
			}
		} else {
			lineas = "Todas";
		}

		String totalResultados = "0";
		if (listaGrupos != null) {
			Integer tot = listaGrupos.size();
			totalResultados = tot.toString();
		}

		String lider = "No";
		if ((nombreLiderGrupo != null && !"".equals(nombreLiderGrupo.trim()))
				|| (apellidoLiderGrupo != null && !"".equals(apellidoLiderGrupo.trim()))) {
			lider = "Sí";
		}

		Integer totalReporte = contador - 1;

		r.adicionarParametro("sed", sede);
		r.adicionarParametro("fac", facultad);
		r.adicionarParametro("linea", lineas);
		r.adicionarParametro("total", totalResultados);
		r.adicionarParametro("totalRep", totalReporte.toString());
		r.adicionarParametro("lider", lider);

		r.setNombreReporte("/portafolio/reporte_grupos");
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
	}

	// Reportes laboratorios
	public void reporteLaboratorios() throws SQLException {

		LaboratorioDetalleEnsayosServicios lab = new LaboratorioDetalleEnsayosServicios();
		Iterator<LaboratorioDetalleEnsayosServicios> i;
		String ids = "";
		i = listaLaboratorios.iterator();
		int contador = 1;
		while (i.hasNext() && contador <= 200) {
			lab = (LaboratorioDetalleEnsayosServicios) i.next();
			ids = ids + " " + lab.getId().toString();
			contador++;
		}

		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("ListaId", ids);
		String sede = "0";
		if (sedeAgregar != null && !"".equals(sedeAgregar.trim())) {
			sede = sedeAgregar;
		}

		String facultad = "1";
		if (facultadAgregar != null && !"".equals(facultadAgregar.trim())) {
			facultad = facultadAgregar;
		} else if (sedeAgregar != null && !"".equals(sedeAgregar.trim())) {
			facultad = "0";
		}

		String totalResultados = "0";
		if (listaLaboratorios != null) {
			Integer tot = listaLaboratorios.size();
			totalResultados = tot.toString();
		}

		Integer totalReporte = contador - 1;

		r.adicionarParametro("sed", sede);
		r.adicionarParametro("fac", facultad);

		if (laboratorioAcreditado) {
			r.adicionarParametro("acred", "Sí");
		} else {
			r.adicionarParametro("acred", "No aplica");
		}

		if (laboratorioInterfacultades && (listaFacultadesFiltro == null
				|| (listaFacultadesFiltro != null && listaFacultadesFiltro.size() == 0))) {
			r.adicionarParametro("interfac", "Sí");
		} else {
			r.adicionarParametro("interfac", "No aplica");
		}

		if ("".equals(dedicacionFiltro.trim())) {
			r.adicionarParametro("dedicacion", "Todas");
		} else {
			r.adicionarParametro("dedicacion", dedicacionFiltro);
		}

		r.adicionarParametro("total", totalResultados);
		r.adicionarParametro("totalRep", totalReporte.toString());

		r.setNombreReporte("/portafolio/reporte_laboratorios");
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
	}

	public void buscarLaboratorios() {
		busquedaLaboratorios = true;
		buscar();
	}

	public void buscarInvestigadores() {
		busquedaInvestigadores = true;
		buscar();
	}

	public void buscarProyectos() {
		busquedaProyectos = true;
		buscar();
	}

	public void buscarGrupos() {
		busquedaGrupos = true;
		buscar();
	}

	public void buscarConvocatorias() {
		busquedaConvocatorias = true;
		buscar();
	}
	
	public void buscarSemilleros() {
		busquedaSemilleros = true;
		buscar();
	}

	public boolean isBanderaResultadosBusqueda() {
		return banderaResultadosBusqueda;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public void setTipoItem(SelectItem[] tipoItem) {
		this.tipoItem = tipoItem;
	}

	public SelectItem[] getTipoItem() {
		return tipoItem;
	}

	public int getTIPOINVESTIGADORES() {
		return TIPOINVESTIGADORES;
	}

	public int getTIPOPROYECTOS() {
		return TIPOPROYECTOS;
	}

	public int getTIPOGRUPOS() {
		return TIPOGRUPOS;
	}

	public int getTIPOLABORATORIOS() {
		return TIPOLABORATORIOS;
	}

	public boolean isFiltroLiderGrupo() {
		return filtroLiderGrupo;
	}

	public void setFiltroLiderGrupo(boolean filtroLiderGrupo) {
		this.filtroLiderGrupo = filtroLiderGrupo;
	}

	public boolean isFiltroLiderProyecto() {
		return filtroLiderProyecto;
	}

	public void setFiltroLiderProyecto(boolean filtroLiderProyecto) {
		this.filtroLiderProyecto = filtroLiderProyecto;
	}

	public void setListaPersonas(List<Persona> listaPersonas) {
		this.listaPersonas = listaPersonas;
	}

	public List<Persona> getListaPersonas() {
		return listaPersonas;
	}

	public void setListaProyecto(List<Proyecto> listaProyecto) {
		this.listaProyecto = listaProyecto;
	}

	public List<Proyecto> getListaProyecto() {
		return listaProyecto;
	}

	public void setListaGrupos(List<Grupo> listaGrupos) {
		this.listaGrupos = listaGrupos;
	}

	public List<Grupo> getListaGrupos() {
		return listaGrupos;
	}

	public List<LaboratorioDetalleEnsayosServicios> getListaLaboratorios() {
		return listaLaboratorios;
	}

	public void setListaLaboratorios(List<LaboratorioDetalleEnsayosServicios> listaLaboratorios) {
		this.listaLaboratorios = listaLaboratorios;
	}

	public Proyecto getProyectoSeleccionado() {
		return proyectoSeleccionado;
	}

	public void setProyectoSeleccionado(Proyecto proyectoSeleccionado) {
		this.proyectoSeleccionado = proyectoSeleccionado;
	}

	public Grupo getGrupoSeleccionado() {
		return grupoSeleccionado;
	}

	public void setGrupoSeleccionado(Grupo grupoSeleccionado) {
		this.grupoSeleccionado = grupoSeleccionado;
	}

	public void setListaFacultadesFiltro(List<Dependencia> listaFacultadesFiltro) {
		this.listaFacultadesFiltro = listaFacultadesFiltro;
	}

	public List<Dependencia> getListaFacultadesFiltro() {
		return listaFacultadesFiltro;
	}

	public SelectItem[] getListaSedesItem() {
		return listaSedesItem;
	}

	public void setListaSedesItem(SelectItem[] listaSedesItem) {
		this.listaSedesItem = listaSedesItem;
	}

	public Boolean getMostrarAgregarSede() {
		return mostrarAgregarSede;
	}

	public String getSedeAgregar() {
		return sedeAgregar;
	}

	public void setSedeAgregar(String sedeAgregar) {
		this.sedeAgregar = sedeAgregar;
	}

	public List<Dependencia> getListaSedesFiltro() {
		return listaSedesFiltro;
	}

	public void setSedeSeleccionada(Dependencia sedeSeleccionada) {
		this.sedeSeleccionada = sedeSeleccionada;
	}

	public void setFacultadSeleccionada(Dependencia facultadSeleccionada) {
		this.facultadSeleccionada = facultadSeleccionada;
	}

	public Boolean getMostrarAgregarFacultad() {
		return mostrarAgregarFacultad;
	}

	public String getFacultadAgregar() {
		return facultadAgregar;
	}

	public void setFacultadAgregar(String facultadAgregar) {
		this.facultadAgregar = facultadAgregar;
	}

	public SelectItem[] getListaFacultadesItem() {
		return listaFacultadesItem;
	}

	public Boolean getMostrarAgregarArea() {
		return mostrarAgregarArea;
	}

	public String getAreaAgregar() {
		return areaAgregar;
	}

	public void setAreaAgregar(String areaAgregar) {
		this.areaAgregar = areaAgregar;
	}

	public List<String> getListaAreasFiltro() {
		return listaAreasFiltro;
	}

	public void setListaAreasFiltro(List<String> listaAreasFiltro) {
		this.listaAreasFiltro = listaAreasFiltro;
	}

	public String getAreaSeleccionada() {
		return areaSeleccionada;
	}

	public void setAreaSeleccionada(String areaSeleccionada) {
		this.areaSeleccionada = areaSeleccionada;
	}

	public Boolean getMostrarAgregarLinea() {
		return mostrarAgregarLinea;
	}

	public void setLineaAgregar(String lineaAgregar) {
		this.lineaAgregar = lineaAgregar;
	}

	public String getLineaAgregar() {
		return lineaAgregar;
	}

	public String getLineaSeleccionada() {
		return lineaSeleccionada;
	}

	public void setLineaSeleccionada(String lineaSeleccionada) {
		this.lineaSeleccionada = lineaSeleccionada;
	}

	public List<String> getListaLineasFiltro() {
		return listaLineasFiltro;
	}

	public void setListaLineasFiltro(List<String> listaLineasFiltro) {
		this.listaLineasFiltro = listaLineasFiltro;
	}

	public void setCampoBusqueda(String campoBusqueda) {
		this.campoBusqueda = campoBusqueda;
	}

	public String getCampoBusqueda() {
		return campoBusqueda;
	}

	public void setNombreLiderGrupo(String nombreLiderGrupo) {
		this.nombreLiderGrupo = nombreLiderGrupo;
	}

	public String getNombreLiderGrupo() {
		return nombreLiderGrupo;
	}

	public void setApellidoLiderGrupo(String apellidoLiderGrupo) {
		this.apellidoLiderGrupo = apellidoLiderGrupo;
	}

	public String getApellidoLiderGrupo() {
		return apellidoLiderGrupo;
	}

	public void setNombreLiderProyecto(String nombreLiderProyecto) {
		this.nombreLiderProyecto = nombreLiderProyecto;
	}

	public String getNombreLiderProyecto() {
		return nombreLiderProyecto;
	}

	public void setApellidoLiderProyecto(String apellidoLiderProyecto) {
		this.apellidoLiderProyecto = apellidoLiderProyecto;
	}

	public String getApellidoLiderProyecto() {
		return apellidoLiderProyecto;
	}

	public Persona getPersonaSeleccionada() {
		return personaSeleccionada;
	}

	public void setPersonaSeleccionada(Persona personaSeleccionada) {
		this.personaSeleccionada = personaSeleccionada;
	}

	public LaboratorioDetalleEnsayosServicios getEnsayoSeleccionado() {
		return ensayoSeleccionado;
	}

	public void setEnsayoSeleccionado(LaboratorioDetalleEnsayosServicios ensayoSeleccionado) {
		this.ensayoSeleccionado = ensayoSeleccionado;
	}

	public int getPALABRASCLAVE() {
		return PALABRASCLAVE;
	}

	public boolean isBanderaBusqueda() {
		return banderaBusqueda;
	}

	public void setBanderaBusqueda(boolean banderaBusqueda) {
		this.banderaBusqueda = banderaBusqueda;
	}

	public int getResultadosInvestigadores() {
		if (listaPersonas != null) {
			resultadosInvestigadores = listaPersonas.size();
			return resultadosInvestigadores;
		} else {
			return 0;
		}
	}

	public void setResultadosInvestigadores(int resultadosInvestigadores) {
		this.resultadosInvestigadores = resultadosInvestigadores;
	}

	public int getResultadosProyectos() {
		if (listaProyecto != null) {
			resultadosProyectos = listaProyecto.size();
			return resultadosProyectos;
		} else {
			return 0;
		}
	}

	public void setResultadosProyectos(int resultadosProyectos) {
		this.resultadosProyectos = resultadosProyectos;
	}

	public int getResultadosGrupos() {
		if (listaGrupos != null) {
			resultadosGrupos = listaGrupos.size();
			return resultadosGrupos;
		} else {
			return 0;
		}
	}

	public void setResultadosGrupos(int resultadosGrupos) {
		this.resultadosGrupos = resultadosGrupos;
	}

	public int getResultadosLaboratorios() {
		if (listaLaboratorios != null) {
			resultadosLaboratorios = listaLaboratorios.size();
			return resultadosLaboratorios;
		} else {
			return 0;
		}

	}

	public void setResultadosLaboratorios(int resultadosLaboratorios) {
		this.resultadosLaboratorios = resultadosLaboratorios;
	}

	public Boolean getMostrarAgregarAreaTematica() {
		return mostrarAgregarAreaTematica;
	}

	public void setMostrarAgregarAreaTematica(Boolean mostrarAgregarAreaTematica) {
		this.mostrarAgregarAreaTematica = mostrarAgregarAreaTematica;
	}

	public List<String> getListaAreasTematicasFiltro() {
		return listaAreasTematicasFiltro;
	}

	public void setListaAreasTematicasFiltro(List<String> listaAreasTematicasFiltro) {
		this.listaAreasTematicasFiltro = listaAreasTematicasFiltro;
	}

	public String getAreaTematicaAgregar() {
		return areaTematicaAgregar;
	}

	public void setAreaTematicaAgregar(String areaTematicaAgregar) {
		this.areaTematicaAgregar = areaTematicaAgregar;
	}

	public SelectItem[] getListaAreaTematicaItem() {
		return listaAreaTematicaItem;
	}

	public void setListaAreaTematicaItem(SelectItem[] listaAreaTematicaItem) {
		this.listaAreaTematicaItem = listaAreaTematicaItem;
	}

	public boolean isMostrarAreaTematicaTexto() {
		return mostrarAreaTematicaTexto;
	}

	public void setMostrarAreaTematicaTexto(boolean mostrarAreaTematicaTexto) {
		this.mostrarAreaTematicaTexto = mostrarAreaTematicaTexto;
	}

	public String getTextoMostrarAreaTematica() {
		return textoMostrarAreaTematica;
	}

	public void setTextoMostrarAreaTematica(String textoMostrarAreaTematica) {
		this.textoMostrarAreaTematica = textoMostrarAreaTematica;
	}

	public Boolean getNombreExacto() {
		return nombreExacto;
	}

	public void setNombreExacto(Boolean nombreExacto) {
		this.nombreExacto = nombreExacto;
	}

	public boolean isBusquedaLaboratorios() {
		return busquedaLaboratorios;
	}

	public void setBusquedaLaboratorios(boolean busquedaLaboratorios) {
		this.busquedaLaboratorios = busquedaLaboratorios;
	}

	public HistoricoBusqueda getHistoricoBusqueda() {
		return historicoBusqueda;
	}

	public void setHistoricoBusqueda(HistoricoBusqueda historicoBusqueda) {
		this.historicoBusqueda = historicoBusqueda;
	}

	public Boolean getLaboratorioAcreditado() {
		return laboratorioAcreditado;
	}

	public void setLaboratorioAcreditado(Boolean laboratorioAcreditado) {
		this.laboratorioAcreditado = laboratorioAcreditado;
	}

	public Boolean getMostrarAgregarDedicacion() {
		return mostrarAgregarDedicacion;
	}

	public void setMostrarAgregarDedicacion(Boolean mostrarAgregarDedicacion) {
		this.mostrarAgregarDedicacion = mostrarAgregarDedicacion;
	}

	public String[] getSelectedDedicacion() {
		return selectedDedicacion;
	}

	public void setSelectedDedicacion(String[] selectedDedicacion) {
		this.selectedDedicacion = selectedDedicacion;
	}

	public List<SelectItem> getListaTiposDedicacion() {
		return listaTiposDedicacion;
	}

	public void setListaTiposDedicacion(List<SelectItem> listaTiposDedicacion) {
		this.listaTiposDedicacion = listaTiposDedicacion;
	}

	public Long getDedicacion() {
		return dedicacion;
	}

	public void setDedicacion(Long dedicacion) {
		this.dedicacion = dedicacion;
	}

	public Boolean getLaboratorioInterfacultades() {
		return laboratorioInterfacultades;
	}

	public void setLaboratorioInterfacultades(Boolean laboratorioInterfacultades) {
		this.laboratorioInterfacultades = laboratorioInterfacultades;
	}

	public String getDedicacionFiltro() {
		return dedicacionFiltro;
	}

	public void setDedicacionFiltro(String dedicacionFiltro) {
		this.dedicacionFiltro = dedicacionFiltro;
	}

	public Boolean getNombreExacto2() {
		return nombreExacto2;
	}

	public void setNombreExacto2(Boolean nombreExacto2) {
		this.nombreExacto2 = nombreExacto2;
	}

	public boolean isBusquedaInvestigadores() {
		return busquedaInvestigadores;
	}

	public void setBusquedaInvestigadores(boolean busquedaInvestigadores) {
		this.busquedaInvestigadores = busquedaInvestigadores;
	}

	public int getTIPOCONVOCATORIAS() {
		return TIPOCONVOCATORIAS;
	}

	public int getResultadosConvocatorias() {
		if (listaConvocatorias != null) {
			resultadosConvocatorias = listaConvocatorias.size();
			return resultadosConvocatorias;
		} else {
			return 0;
		}
	}

	public String consultarPaginaConvocatoria() {

		ConvocatoriaExterna convocatoria = convocatoriaSeleccionada;

		if (convocatoria.getVinculo() != null) {
			String url = convocatoria.getVinculo() + "#convocatoria";

			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect(url);
			} catch (IOException e) {
				e.printStackTrace();
				return "";
			}
			return "";
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext extContext = context.getExternalContext();
			String viewId = "";
			if (convocatoria.getTipo().equals("I")) {
				viewId = "/pages/Consultas/Convocatoria.xhtml";
			} else {
				viewId = "/pages/Consultas/ConvocatoriaExterna.xhtml";
			}
			try {
				viewId = extContext.getRequestContextPath() + viewId + '?' + "idConvocatoria" + "="
						+ convocatoria.getId() + "&tipo=" + convocatoria.getTipo();
				String urlLink = context.getExternalContext().encodeActionURL(viewId);
				extContext.redirect(urlLink);
			} catch (IOException e) {
				extContext.log(getClass().getName() + ".invokeRedirect", e);
			}
		}

		return null;
	}
	
	public String consultarPaginaSemilleros() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext extContext = context.getExternalContext();
		String viewId = "";
		viewId = "/pages/Consultas/Semillero.xhtml";
		try {
			viewId = extContext.getRequestContextPath() + viewId + '?' + "id" + "=" + semilleroActual.getId();
			String urlLink = context.getExternalContext().encodeActionURL(viewId);
			extContext.redirect(urlLink);
		} catch (IOException e) {
			extContext.log(getClass().getName() + ".invokeRedirect", e);
		}
		return null;
	}

	public void setResultadosConvocatorias(int resultadosConvocatorias) {
		this.resultadosConvocatorias = resultadosConvocatorias;
	}

	public ConvocatoriaExterna getConvocatoriaSeleccionada() {
		return convocatoriaSeleccionada;
	}

	public void setConvocatoriaSeleccionada(ConvocatoriaExterna convocatoriaSeleccionada) {
		this.convocatoriaSeleccionada = convocatoriaSeleccionada;
	}

	public Boolean getMostrarAgregarEstado() {
		return mostrarAgregarEstado;
	}

	public void setMostrarAgregarEstado(Boolean mostrarAgregarEstado) {
		this.mostrarAgregarEstado = mostrarAgregarEstado;
	}

	public List<String> getListaEstadosFiltro() {
		return listaEstadosFiltro;
	}

	public void setListaEstadosFiltro(List<String> listaEstadosFiltro) {
		this.listaEstadosFiltro = listaEstadosFiltro;
	}

	public String getEstadoAgregar() {
		return estadoAgregar;
	}

	public void setEstadoAgregar(String estadoAgregar) {
		this.estadoAgregar = estadoAgregar;
	}

	public void setEstadoItem(SelectItem[] estadoItem) {
		this.estadoItem = estadoItem;
	}

	public SelectItem[] getEstadoItem() {
		return estadoItem;
	}

	public List<ConvocatoriaExterna> getListaConvocatorias() {
		return listaConvocatorias;
	}

	public void setListaConvocatorias(List<ConvocatoriaExterna> listaConvocatorias) {
		this.listaConvocatorias = listaConvocatorias;
	}

	public boolean isMostrarResultadosConvocatorias() {
		return mostrarResultadosConvocatorias;
	}

	public void setMostrarResultadosConvocatorias(boolean mostrarResultadosConvocatorias) {
		this.mostrarResultadosConvocatorias = mostrarResultadosConvocatorias;
	}

	public void setEstadoSeleccionado(String estadoSeleccionado) {
		this.estadoSeleccionado = estadoSeleccionado;
	}

	public Boolean getMostrarAgregarEntidad() {
		return mostrarAgregarEntidad;
	}

	public void setMostrarAgregarEntidad(Boolean mostrarAgregarEntidad) {
		this.mostrarAgregarEntidad = mostrarAgregarEntidad;
	}

	public List<FuenteFinanciacion> getListaEntidadesFiltro() {
		return listaEntidadesFiltro;
	}

	public void setListaEntidadesFiltro(List<FuenteFinanciacion> listaEntidadesFiltro) {
		this.listaEntidadesFiltro = listaEntidadesFiltro;
	}

	public String getEntidadAgregar() {
		return entidadAgregar;
	}

	public void setEntidadAgregar(String entidadAgregar) {
		this.entidadAgregar = entidadAgregar;
	}

	public FuenteFinanciacion getEntidadSeleccionada() {
		return entidadSeleccionada;
	}

	public void setEntidadSeleccionada(FuenteFinanciacion entidadSeleccionada) {
		this.entidadSeleccionada = entidadSeleccionada;
	}

	public SelectItem[] getListaEntidadesItem() {
		return listaEntidadesItem;
	}

	public void setListaEntidadesItem(SelectItem[] listaEntidadesItem) {
		this.listaEntidadesItem = listaEntidadesItem;
	}

	public List<FuenteFinanciacion> getListaEntidades() {
		return listaEntidades;
	}

	public void setListaEntidades(List<FuenteFinanciacion> listaEntidades) {
		this.listaEntidades = listaEntidades;
	}

	public boolean isBusquedaConvocatorias() {
		return busquedaConvocatorias;
	}

	public void setBusquedaConvocatorias(boolean busquedaConvocatorias) {
		this.busquedaConvocatorias = busquedaConvocatorias;
	}

	public void refrescar() {
		try {
			oldURL = currURL;
			currURL = javax.faces.context.FacesContext.getCurrentInstance().getExternalContext()
					.getRequestServletPath();
		} catch (Exception e) {
			currURL = oldURL;
		}
		if (!oldURL.contains(currURL)) {
			reiniciarBusqueda();
		}
	}

	public int getTIPOSEMILLEROS() {
		return TIPOSEMILLEROS;
	}

	public List<Semillero> getListaSemilleros() {
		return listaSemilleros;
	}

	public void setListaSemilleros(List<Semillero> listaSemilleros) {
		this.listaSemilleros = listaSemilleros;
	}

	public boolean isMostrarResultadosSemilleros() {
		return mostrarResultadosSemilleros;
	}

	public void setMostrarResultadosSemilleros(boolean mostrarResultadosSemilleros) {
		this.mostrarResultadosSemilleros = mostrarResultadosSemilleros;
	}

	public int getResultadosSemilleros() {
		if (listaSemilleros != null) {
			resultadosSemilleros = listaSemilleros.size();
			return resultadosSemilleros;
		} else {
			return 0;
		}
	}

	public void setResultadosSemilleros(int resultadosSemilleros) {
		this.resultadosSemilleros = resultadosSemilleros;
	}

	public Semillero getSemilleroActual() {
		return semilleroActual;
	}

	public void setSemilleroActual(Semillero semilleroActual) {
		this.semilleroActual = semilleroActual;
	}

	public Boolean getSoloEquiposRobustos() {
		return soloEquiposRobustos;
	}

	public void setSoloEquiposRobustos(Boolean soloEquiposRobustos) {
		this.soloEquiposRobustos = soloEquiposRobustos;
	}
}
