package co.edu.unal.hermes.vista.editorial.proyectos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.Archivo;
import co.edu.unal.hermes.modelo.ArchivoResumen;
import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Departamento;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Estudiante;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.HistoricoEstadoProyecto;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Institucion;
import co.edu.unal.hermes.modelo.editorial.CantidadMaterialGrafico;
import co.edu.unal.hermes.utils.VariablesEstaticas;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorExterno;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.Pais;
import co.edu.unal.hermes.modelo.PalabraClave;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.ProyectoEditorial;
import co.edu.unal.hermes.modelo.ProyectoEditorialTitulo;
import co.edu.unal.hermes.modelo.Region;
import co.edu.unal.hermes.modelo.Rol;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.TipoArchivo;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoInvestigador;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.vista.convocatorias.ManejadorConsultaConvocatoriasBase;
import co.edu.unal.hermes.vista.proyectos.manejadorproyecto.InvestigadorProyectoVista;

public class ManejadorEditorialProyectosRegistro extends ManejadorConsultaConvocatoriasBase {

	private static final long serialVersionUID = 1573648775482142384L;

	public static final String DOMINIO_ISBN_AUDIENCIA_2018 = "ISBN_AUDIENCIA_2018";

	// FORMULARIO 1
	private List<Ciudad> listaCiudades;
	private List<Departamento> listaDepartamentos;
	private boolean bloquearFormulario2;
	private String tipoUsuario;
	private String sedeSolicitante;
	private String facultadSolicitante;
	private String rolEditorial;
	private boolean edicion;
	private Persona solicitante;

	private SelectItem[] tipoDocumentoItem;
	private SelectItem[] paisesItem;
	private SelectItem[] departamentosItem;
	private SelectItem[] departamentosItem2;
	private SelectItem[] ciudadesItem;
	private SelectItem[] ciudadesItem2;
	private SelectItem[] sedesItem;
	private SelectItem[] facultadesItem;
	private SelectItem[] tipoUsuarioItem;
	private SelectItem[] rolEditorialItem;
	private List<SelectItem> listaAudienciaISBN;

	// FORMULARIO 2
	private boolean bloquearFormulario3;
	private boolean bloquearEnvio;
	private Proyecto registro;
	private List<SelectItem> listaTemaProyecto;
	private PalabraClave palabraClave;
	private PalabraClave palabraClaveTabla;
	private ProyectoEditorialTitulo tituloActual;
	private ProyectoEditorialTitulo tituloEliminar;
	private UploadedFile tablaContenido;
	private List<Archivo> archivosTablaContenido;
	private List<Archivo> archivosAdjuntos;
	private Archivo archivoEliminar;
	// FORMULARIO 3
	private boolean bloquearFormulario4;
	private SelectItem[] origenProyectoItem;
	private SelectItem[] proyectosAsociadostem;
	private SelectItem[] caracterProyectoItem;
	private List<SelectItem> listaGrupos;
	private String tipoInvestigador;
	private String tipoDocumentoInvestigador;
	private SelectItem[] autores;
	private String documentoCoinv;
	private SelectItem[] paisItem;
	private String pais;
	private String rolAutorISBN;
	private List<SelectItem> listaRolAutorISBN;
	private boolean esOtraVinculacion = false;
	private InvestigadorExterno investigadorExterno = new InvestigadorExterno();
	private SelectItem[] generoItem = {
			new SelectItem(VariablesEstaticas.GENERO_FEMENINO, VariablesEstaticas.GENERO_FEMENINO),
			new SelectItem(VariablesEstaticas.GENERO_MASCULINO, VariablesEstaticas.GENERO_MASCULINO) };
	private String insitucionNombre;
	private Date fechaNacimiento;
	private InvestigadorProyecto participante = new InvestigadorProyecto();

	private List<CantidadMaterialGrafico> listaCantidadMaterialBibliografico;
	private List<InvestigadorProyecto> listaParticipantes;
	private List<InvestigadorProyecto> listaParticipantesBorrados;
	private UploadedFile archivo;
	private String idArchivoAdjunto;
	private String idArchivoEliminar;
	private SelectItem[] tipoArchivoItem;
	private boolean proyectoExiste = false;
	private Convocatoria convocatoriaActual;

	String resumen;

	// Clasificadores ISBN
	private SelectItem[] listaClasificacionTHEMA_N1;
	private SelectItem[] listaClasificacionTHEMA_N2;
	private SelectItem[] listaClasificacionTHEMA_N3;
	private SelectItem[] listaClasificacionTHEMA_N4;
	private SelectItem[] listaClasificacionTHEMA_N5;
	private SelectItem[] listaClasificacionTHEMA_N6;

	private List<TipoArchivo> listaTipoArchivo = new ArrayList<TipoArchivo>();

	public List<CantidadMaterialGrafico> getListaCantidadMaterialBibliografico() {
		return listaCantidadMaterialBibliografico;
	}

	public void setListaCantidadMaterialBibliografico(
			List<CantidadMaterialGrafico> listaCantidadMaterialBibliografico) {
		this.listaCantidadMaterialBibliografico = listaCantidadMaterialBibliografico;
	}

	public ManejadorEditorialProyectosRegistro() {
		super();
		init();
	}

	@SuppressWarnings("deprecation")
	private void init() {
		bloquearFormulario2 = true;
		bloquearFormulario3 = true;
		bloquearFormulario4 = true;
		palabraClave = new PalabraClave();
		listaParticipantes = new ArrayList<InvestigadorProyecto>();
		listaParticipantesBorrados = new ArrayList<InvestigadorProyecto>();

		if (sesion.getAttribute("Pro_Editorial_ID") != null) {
			Long idRegistro = Long.parseLong(sesion.getAttribute("Pro_Editorial_ID").toString());
			if (sesion.getAttribute("Pro_Editorial_Editar") != null) {
				edicion = sesion.getAttribute("Pro_Editorial_Editar").toString().equals("Editar-" + idRegistro);
			} else {
				edicion = false;
			}
			registro = servicioProyecto.obtenerProyecto(idRegistro, ProyectoDAOHibernate.TODO_POR_ID);
		}
		if (registro == null) {
			edicion = true;
			registro = new Proyecto();

			Persona persona = cargarPersonaActual();

			registro.setCreadorDocumento(persona.getId().getTipoDocumento());
			registro.setCreadorId(persona.getId().getDocumento());

			crearSolicitanteNuevo();
		} else {
			solicitante = servicioPersona
					.obtenerPersona(registro.getListaInvestigadorPrincipal().get(0).getInvestigador().getId());
			String[] datos = registro.getListaInvestigadorPrincipal().get(0).getFuncion().split("-");
			tipoUsuario = datos[0];
			rolEditorial = datos[1];

			sedeSolicitante = registro.getListaInvestigadorPrincipal().get(0).getInvestigador().getDependencia().getSede().getId()
					.toString();
			facultadSolicitante = registro.getListaInvestigadorPrincipal().get(0).getInvestigador().getDependencia().getFacultad()
					.getId().toString();
			validarFormulario1(false);
			proyectoExiste = true;

			List<InvestigadorProyectoVista> listaInvestigadoresVista = registro.getObtenerListaInvestigadoresVista();

			if (listaInvestigadoresVista != null) {
				for (int i = 0; i < listaInvestigadoresVista.size(); i++) {
					InvestigadorProyectoVista ipv = (InvestigadorProyectoVista) listaInvestigadoresVista.get(i);
					InvestigadorProyecto ip = ipv.getInvestigadorProyecto();
					if (!ip.getTipo().getId().equals(TipoInvestigador.Principal)) {
						listaParticipantes.add(ip);
					}
				}
			}
			cargarArchivosAdjuntos();
		}
		if (registro.getFichaEditorial() == null) {
			registro.setFichaEditorial(new ProyectoEditorial());
			registro.getFichaEditorial().setProyecto(registro);
		}

		cargarCantidadMaterialBibliografico(registro.getId());
		cargarListaTipoArchivos();

		List<DominioDetalle> ssr = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				"select dd from DominioDetalle dd where dd.identificador.id='277' order by dd.estado");
		tipoUsuarioItem = new SelectItem[ssr.size()];
		int index = 0;
		for (DominioDetalle resp : ssr) {
			tipoUsuarioItem[index] = new SelectItem(resp.getIdentificador().getTipo(), resp.getDescripcion());
			index++;
		}
		ssr = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				"select dd from DominioDetalle dd where dd.identificador.id='279' order by dd.estado");
		rolEditorialItem = new SelectItem[ssr.size()];
		index = 0;
		for (DominioDetalle resp : ssr) {
			rolEditorialItem[index] = new SelectItem(resp.getIdentificador().getTipo(), resp.getDescripcion());
			index++;
		}
		ssr = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				"select dd from DominioDetalle dd where dd.identificador.id='280' order by dd.estado");

		origenProyectoItem = new SelectItem[ssr.size()];
		index = 0;
		for (DominioDetalle resp : ssr) {
			origenProyectoItem[index] = new SelectItem(resp.getIdentificador().getTipo(), resp.getDescripcion());
			index++;
		}

		ssr = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				"select dd from DominioDetalle dd where dd.identificador.id='281' order by dd.estado");
		caracterProyectoItem = new SelectItem[ssr.size()];
		index = 0;
		for (DominioDetalle resp : ssr) {
			caracterProyectoItem[index] = new SelectItem(resp.getIdentificador().getTipo(), resp.getDescripcion());
			index++;
		}
		List<TipoDocumento> listaTipoDocumento = servicioGeneral.obtenerTiposDeDocumento();
		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			TipoDocumento td = listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
		}
		List<Pais> listaPaises = servicioGeneral.obtenerListaObjetosOrdenadosAscG(Pais.class, "nombre");
		paisesItem = new SelectItem[listaPaises.size()];
		for (int i = 0; i < listaPaises.size(); i++) {
			Pais p = listaPaises.get(i);
			paisesItem[i] = new SelectItem(p.getId(), p.getNombre());
		}
		List<Sede> listaSede = servicioGeneral.obtenerObjetos(Sede.class,
				"from Sede s where s.id not in ('" + Sede.NIVEL_NACIONAL + "') order by s.id");
		sedesItem = new SelectItem[listaSede.size()];
		for (int i = 0; i < listaSede.size(); i++) {
			Sede sede = listaSede.get(i);
			sedesItem[i] = new SelectItem(sede.getId(), sede.getNombre());
		}
		listaDepartamentos = servicioGeneral.obtenerObjetosLimitado(Departamento.class,
				"select #id e.id, #nombre e.nombre from Departamento e where e.id like 'CO%' order by e.nombre asc");
		departamentosItem2 = new SelectItem[listaDepartamentos.size()];
		index = 0;
		for (Departamento departamento : listaDepartamentos) {
			departamentosItem2[index] = new SelectItem(departamento.getId(), departamento.getNombre());
			index++;
		}
		revisarPais();
		cambiarDepartamento2();
		cambiarSede();
		listaTemaProyecto = new ArrayList<SelectItem>();
		for (Object item : servicioGeneral.obtenerObjetos(
				"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='ISBN_MATERIA' order by dd.identificador.id")) {
			DominioDetalle dominio = (DominioDetalle) item;
			listaTemaProyecto.add(new SelectItem(dominio.getIdentificador().getTipo(),
					dominio.getIdentificador().getTipo().substring(3) + " - " + dominio.getDescripcion()));
		}

		listaGrupos = new ArrayList<SelectItem>();
		List<Grupo> grupos = servicioGeneral.obtenerObjetosLimitado(Grupo.class,
				"select #id g.id, #nombre g.nombre from Grupo g "
						+ " where g.estadoGrupo.id in ('A') order by g.nombre ");

		if (!esListaVacia(grupos)) {
			for (int i = 0; i < grupos.size(); i++) {
				Grupo grupo = (Grupo) grupos.get(i);
				listaGrupos.add(new SelectItem(grupo.getId(), grupo.getNombre()));

			}
		}

		String consulta4 = "select dd from TipoInvestigador dd where dd.tipoModalidad = 'SIS' and dd.esVisible = 1 order by dd.nombre";
		List list4 = servicioGeneral.obtenerObjetos(consulta4);

		if (list4.size() > 0) {
			listaRolAutorISBN = new ArrayList<SelectItem>();
			listaRolAutorISBN.add(new SelectItem("0", "Seleccione una opción ..."));
			for (int i = 0; i < list4.size(); i++) {
				TipoInvestigador tipoInv = (TipoInvestigador) list4.get(i);
				listaRolAutorISBN.add(new SelectItem(tipoInv.getId(), tipoInv.getNombre()));
			}
		} else
			listaRolAutorISBN.add(new SelectItem("0", " - "));

		cargarPaises();

		List listaAutores = servicioGeneral
				.obtenerObjetos("select e from TipoInvestigador e where e.tipoModalidad = 'CL'");
		autores = new SelectItem[listaAutores.size()];
		for (int i = 0; i < listaAutores.size(); i++) {
			TipoInvestigador ta = (TipoInvestigador) listaAutores.get(i);
			autores[i] = new SelectItem(ta.getId(), ta.getNombre());
			ta = null;
		}

		tituloActual = new ProyectoEditorialTitulo();
		archivosTablaContenido = new ArrayList<Archivo>();
		for (Archivo archivo : registro.getArchivos()) {
			if (archivo.getTipoArchivo().getId().equals(Short.parseShort("118"))) {
				archivosTablaContenido.add(archivo);
				break;
			}
		}
		validarFormulario2Automatico();
		if (!bloquearFormulario3) {
			validarFormulario3Automatico();
		}

		cargarListasCalificadores();

		cargarListas();
		convocatoriaActual = (Convocatoria) registro.getModalidad();

	}

	public void cargarProyectosInvestigaroPrincipal() {
		try {

			List<Proyecto> listaProyectos = servicioGeneral.obtenerObjetosLimitado(Proyecto.class,
					"select #id p.id, #nombre p.nombre from Proyecto p , InvestigadorProyecto ip where "
							+ " ip.proyecto.id=p.id and " + " p.estadoProyecto.id in ('" + EstadoProyecto.ACTIVO + "','"
							+ EstadoProyecto.FINALIZADO + "','" + EstadoProyecto.POR_FINALIZAR + "') "
							+ " and ip.investigador.id.tipoDocumento like '%" + solicitante.getId().getTipoDocumento()
							+ "%' " + " and ip.investigador.id.documento like '%" + solicitante.getId().getDocumento()
							+ "%' " + " order by p.nombre asc ");
			proyectosAsociadostem = new SelectItem[listaProyectos.size()];
			for (int i = 0; i < listaProyectos.size(); i++) {
				Proyecto proyecto = listaProyectos.get(i);
				proyectosAsociadostem[i] = new SelectItem(proyecto.getId(),
						proyecto.getId() + " - " + proyecto.getNombre());
			}
		} catch (Exception e) {
		}
	}

	public void cargarListasCalificadores() {
		// THEMA
		listaClasificacionTHEMA_N1 = servicioGeneral
				.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPOS_ISBN_CLASIFICACION_THEMA);

		if (registro.getClasificacionThemaISBNNivel2() != null) {
			listaClasificacionTHEMA_N2 = !registro.getClasificacionThemaISBNNivel2().equals(0L)
					|| (registro.getClasificacionThemaISBNNivel2().equals(0L)
							&& !registro.getClasificacionThemaISBNNivel1().equals(0L))
									? servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(
											registro.getClasificacionThemaISBNNivel1())
									: null;
		}

		if (registro.getClasificacionThemaISBNNivel3() != null) {
			listaClasificacionTHEMA_N3 = !registro.getClasificacionThemaISBNNivel3().equals(0L)
					|| (registro.getClasificacionThemaISBNNivel3().equals(0L)
							&& !registro.getClasificacionThemaISBNNivel2().equals(0L))
									? servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(
											registro.getClasificacionThemaISBNNivel2())
									: null;
		}

		if (registro.getClasificacionThemaISBNNivel4() != null) {
			listaClasificacionTHEMA_N4 = !registro.getClasificacionThemaISBNNivel4().equals(0L)
					|| (registro.getClasificacionThemaISBNNivel4().equals(0L)
							&& !registro.getClasificacionThemaISBNNivel3().equals(0L))
									? servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(
											registro.getClasificacionThemaISBNNivel3())
									: null;
		}

		if (registro.getClasificacionThemaISBNNivel5() != null) {
			listaClasificacionTHEMA_N5 = !registro.getClasificacionThemaISBNNivel5().equals(0L)
					|| (registro.getClasificacionThemaISBNNivel5().equals(0L)
							&& !registro.getClasificacionThemaISBNNivel4().equals(0L))
									? servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(
											registro.getClasificacionThemaISBNNivel4())
									: null;
		}

		if (registro.getClasificacionThemaISBNNivel6() != null) {
			listaClasificacionTHEMA_N6 = !registro.getClasificacionThemaISBNNivel6().equals(0L)
					|| (registro.getClasificacionThemaISBNNivel6().equals(0L)
							&& !registro.getClasificacionThemaISBNNivel5().equals(0L))
									? servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(
											registro.getClasificacionThemaISBNNivel5())
									: null;
		}
	}

	public void cargarListas() {
		listaAudienciaISBN = servicioGeneral.obtenerDominioDetalleSelectItem(DOMINIO_ISBN_AUDIENCIA_2018, false);
	}

	public void cambiarClaThemaN1() {
		if (!registro.getClasificacionThemaISBNNivel1().toString().equals("0"))
			listaClasificacionTHEMA_N2 = servicioGeneral
					.retornaSelectItemArregloDeHijosDeTiposOrdenABC(registro.getClasificacionThemaISBNNivel1());

		registro.setClasificacionThemaISBNNivel2(null);
		listaClasificacionTHEMA_N3 = null;
		cambiarClaThemaN2();
	}

	public void cambiarClaThemaN2() {
		if (registro.getClasificacionThemaISBNNivel2() != null) {
			if (!registro.getClasificacionThemaISBNNivel2().toString().equals("0"))
				listaClasificacionTHEMA_N3 = servicioGeneral
						.retornaSelectItemArregloDeHijosDeTiposOrdenABC(registro.getClasificacionThemaISBNNivel2());
		}
		registro.setClasificacionThemaISBNNivel3(null);
		listaClasificacionTHEMA_N4 = null;
		cambiarClaThemaN3();
	}

	public void cambiarClaThemaN3() {
		if (registro.getClasificacionThemaISBNNivel3() != null) {
			if (!registro.getClasificacionThemaISBNNivel3().toString().equals("0"))
				listaClasificacionTHEMA_N4 = servicioGeneral
						.retornaSelectItemArregloDeHijosDeTiposOrdenABC(registro.getClasificacionThemaISBNNivel3());
		}
		registro.setClasificacionThemaISBNNivel4(null);
		listaClasificacionTHEMA_N5 = null;
		cambiarClaThemaN4();
	}

	public void cambiarClaThemaN4() {
		if (registro.getClasificacionThemaISBNNivel4() != null) {
			if (!registro.getClasificacionThemaISBNNivel4().toString().equals("0"))
				listaClasificacionTHEMA_N5 = servicioGeneral
						.retornaSelectItemArregloDeHijosDeTiposOrdenABC(registro.getClasificacionThemaISBNNivel4());
		}
		registro.setClasificacionThemaISBNNivel5(null);
		listaClasificacionTHEMA_N6 = null;
		cambiarClaThemaN5();
	}

	public void cambiarClaThemaN5() {
		if (registro.getClasificacionThemaISBNNivel5() != null) {
			if (!registro.getClasificacionThemaISBNNivel5().toString().equals("0"))
				listaClasificacionTHEMA_N6 = servicioGeneral
						.retornaSelectItemArregloDeHijosDeTiposOrdenABC(registro.getClasificacionThemaISBNNivel5());
		}
		registro.setClasificacionThemaISBNNivel6(null);
	}

	private void cargarArchivosAdjuntos() {
		List<Archivo> archivos = servicioProyecto.obtenerNombresArchivosConTipos(registro);
		if (archivosAdjuntos == null) {
			archivosAdjuntos = new ArrayList<Archivo>();
		} else {
			archivosAdjuntos.clear();
		}

		for (int i = 0; i < archivos.size(); i++) {
			if (!archivos.get(i).getTipoArchivo().getId().toString().equals("118")) {
				archivosAdjuntos.add(archivos.get(i));
			}
		}
	}

	private void cargarPaises() {
		// JOptionPane.showMessageDialog(null, "Entro CP");
		List listaPaises = servicioGeneral.obtenerListaObjetos("Pais");
		paisItem = new SelectItem[listaPaises.size()];
		for (int i = 0; i < listaPaises.size(); i++) {
			Pais paisObjeto = (Pais) listaPaises.get(i);
			paisItem[i] = new SelectItem(paisObjeto.getId(), paisObjeto.getNombre());
		}
		// tipo_documento = (TipoDocumento) listaTipoDocumento.get(0);
	}

	public void eliminarArchivo() {
		archivosTablaContenido.clear();
		registro.getArchivos().remove(archivoEliminar);
		guardarProyecto(2);
	}

	public void descargarArchivo() {
		Long id = Long.parseLong(idArchivoEliminar);
		descargarArchivoProyectoGenerico(id, registro.getId());
	}

	public void eliminarArchivoAdjunto() {
		// Long id = ((ArchivoResumen) (tablaArchivos.getRowData())).getId();
		// registro.getArchivos().remove(archivoAdjuntoEliminar);
		Long id = Long.parseLong(idArchivoEliminar);
		Archivo archivo = servicioProyecto.obtenerArchivo(id);
		if (archivo != null && (archivo.getDatos() == null || archivo.getBytes().length <= 1)) {
			eliminarArchivoProyectoGenerico(archivo.getId(), registro, archivo.getNombre());
			servicioGeneral.eliminarObjeto(archivo);
		}
		cargarArchivosAdjuntos();
	}

	public void agregarArchivo(FileUploadEvent event) {
		tablaContenido = event.getFile();
		agregarArchivo();
	}

	private void agregarArchivo() {
		TipoArchivo tipoAr = (TipoArchivo) (servicioGeneral.obtenerObjetoXID("TipoArchivo", "118")).get(0);
		insertarArchivoProyectoGenericoConTipos(tablaContenido, registro, archivosTablaContenido, tipoAr);
		guardarProyecto(2);
	}

	public void agregarArchivoAdjunto(FileUploadEvent event) {
		archivo = event.getFile();
		TipoArchivo tipoAr = (TipoArchivo) (servicioGeneral.obtenerObjetoXID("TipoArchivo", idArchivoAdjunto)).get(0);
		insertarArchivoProyectoGenericoConTipos(archivo, registro, archivosAdjuntos, tipoAr);
		guardarProyecto(3);
	}

	public void guardarProyectoFormulario2() {
		guardarProyecto(2);
	}

	public void eliminarTitulo() {
		registro.getTitulosEditorial().remove(tituloEliminar);
		guardarProyecto(2);
		tituloEliminar = new ProyectoEditorialTitulo();
	}

	public void insertarTitulo() {
		if (!tituloActual.getTitulo().trim().equals("")) {
			tituloActual.setProyecto(registro);
			registro.getTitulosEditorial().add(tituloActual);
			guardarProyecto(2);
			tituloActual = new ProyectoEditorialTitulo();
		}
	}

	public List<String> obtenerPalabraClavesSugeridas(String nombre) {
		return servicioGeneral.obtenerPalabrasClaveEmpezandoCon(nombre);
	}

	public void eliminarPalabraClave() {
		registro.borrarPalabraClave(palabraClaveTabla);
		palabraClaveTabla = new PalabraClave();
		guardarProyecto(2);
	}

	public void insertarPalabraClave() {
		boolean existePalabra = palabraClave.existePalabraEnSet(registro.getPalabrasClaves());
		if ((!palabraClave.getPalabra().equals("")) && (!existePalabra)) {
			PalabraClave pc = new PalabraClave();
			pc.setPalabra(palabraClave.getPalabra().toUpperCase());
			pc.setPalabraOriginal(palabraClave.getPalabra());
			try {
				PalabraClave pc1 = servicioGeneral.obtenerPalabraClave(pc.getPalabra());
				if (pc1 == null) {
					pc.setIdioma("ES");
					servicioGeneral.guardarObjeto(pc);
				} else {
					pc = (PalabraClave) pc1.clone();
				}
				registro.adicionarPalabraClave(pc);
				palabraClave.setPalabra("");
				pc1 = null;
				pc = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		palabraClave = new PalabraClave();
		guardarProyecto(2);
	}

	public void guardarProyecto(int form) {
		servicioGeneral.guardarObjeto(registro);
		if (form == 2) {
			validarFormulario2Automatico();
		}
		if (form == 3) {
			// validarFormulario2Automatico();
		}
	}

	public void cargarInformacionSolicitante() {
		IdPersona documento = solicitante.getId();
		if (!esCadenaVacia(solicitante.getId().getTipoDocumento())
				&& !esCadenaVacia(solicitante.getId().getDocumento())) {
			solicitante = servicioPersona.obtenerPersona(documento);
			if (solicitante != null) {
				sedeSolicitante = "";
				if (solicitante instanceof Investigador && ((Investigador) solicitante).getInterno().equals("S")) {
					if (isUsuarioExterno()) {
						generarMsg(2,
								"El usuario se encuentra registrado en la plataforma como investigador interno, por lo cual no puede ser vinculado como solicitante externo.",
								"msgsForm1");
						crearSolicitanteNuevo();
						return;
					} else {
						sedeSolicitante = ((InvestigadorInterno) solicitante).getDependencia().getSede().getId()
								.toString();
						facultadSolicitante = ((InvestigadorInterno) solicitante).getDependencia().getId().toString();
					}
				}
				if (solicitante instanceof Investigador && ((Investigador) solicitante).getInterno().equals("N")) {
					if (!isUsuarioExterno()) {
						generarMsg(2,
								"El usuario se encuentra registrado en la plataforma como investigador externo, por lo cual no puede ser vinculado como solicitante interno.",
								"msgsForm1");
						crearSolicitanteNuevo();
						return;
					}
				}
				if (solicitante.getCiudadExpedicion() == null) {
					solicitante.setCiudadExpedicion(new Ciudad());
					solicitante.getCiudadExpedicion().setDepartamento(new Departamento());
					solicitante.getCiudadExpedicion().getDepartamento().setRegion(new Region());
					solicitante.getCiudadExpedicion().getDepartamento().getRegion().setPais(new Pais());
				}
				if (solicitante.getCiudadDomicilio() == null) {
					solicitante.setCiudadDomicilio(new Ciudad());
					solicitante.getCiudadDomicilio().setDepartamento(new Departamento());
				}
				revisarPais();
				cambiarDepartamento2();
				cambiarSede();
			} else if (!isUsuarioExterno()) {
				generarMsg(2,
						"El usuario no se encuentra registrado en la plataforma, por lo cual no puede ser vinculado como solicitante interno.",
						"msgsForm1");
				crearSolicitanteNuevo();
				return;
			} else {
				crearSolicitanteNuevo();
				solicitante.setId(documento);
			}
		} else {
			crearSolicitanteNuevo();
			solicitante.setId(documento);
		}
	}

	private void crearSolicitanteNuevo() {
		solicitante = new Investigador();
		solicitante.setCiudadExpedicion(new Ciudad());
		solicitante.getCiudadExpedicion().setDepartamento(new Departamento());
		solicitante.getCiudadExpedicion().getDepartamento().setRegion(new Region());
		solicitante.getCiudadExpedicion().getDepartamento().getRegion().setPais(new Pais());
		solicitante.setCiudadDomicilio(new Ciudad());
		solicitante.getCiudadDomicilio().setDepartamento(new Departamento());
		bloquearFormulario2 = true;
	}

	public void revisarPais() {
		if (solicitante.getCiudadExpedicion() != null && solicitante.getCiudadExpedicion().getDepartamento() != null) {
			String paisExpedicion = solicitante.getCiudadExpedicion().getDepartamento().getRegion().getPais().getId();

			if (paisExpedicion != null) {
				if (paisExpedicion.equals("")) {
					ciudadesItem = null;
				} else if (!paisExpedicion.equals("") && paisExpedicion.equals("CO")) {
					obtenerListaDepartamentos();
				} else {
					cambiarDepartamento(false);
				}
			} else {
				ciudadesItem = null;
			}
		} else {
			ciudadesItem = null;
		}
	}

	public void cambiarDepartamento() {
		cambiarDepartamento(true);
	}

	public void cambiarDepartamento2() {
		listaCiudades = null;
		String departamentoDomicilio = null;
		if (solicitante.getCiudadDomicilio() != null) {

			departamentoDomicilio = solicitante.getCiudadDomicilio().getDepartamento().getId();
		}
		if (departamentoDomicilio != null) {
			listaCiudades = servicioGeneral.obtenerObjetosLimitado(Ciudad.class,
					"select #id e.id, #nombre e.nombre from Ciudad e where e.departamento.id like '"
							+ departamentoDomicilio + "' and e.sigla is not null order by e.nombre asc");
		}
		if (listaCiudades != null) {
			ciudadesItem2 = new SelectItem[listaCiudades.size()];
			int index = 0;
			for (Ciudad ciudad : listaCiudades) {
				ciudadesItem2[index] = new SelectItem(ciudad.getId(), ciudad.getNombre());
				index++;
			}
		} else {
			ciudadesItem2 = null;
		}
	}

	private void cambiarDepartamento(boolean nacionales) {
		listaCiudades = null;
		if (nacionales) {
			String departamentoExpedicion = solicitante.getCiudadExpedicion().getDepartamento().getId();
			if (departamentoExpedicion != null) {
				listaCiudades = servicioGeneral.obtenerObjetosLimitado(Ciudad.class,
						"select #id e.id, #nombre e.nombre from Ciudad e where e.departamento.id like '"
								+ departamentoExpedicion + "' and e.sigla is not null order by e.nombre asc");
			}

		} else {
			String paisExpedicion = solicitante.getCiudadExpedicion().getDepartamento().getRegion().getPais().getId();
			String idPaisBus = "";
			try {
				idPaisBus = paisExpedicion.substring(0, 2);
			} catch (Exception e) {
				idPaisBus = paisExpedicion;
			}
			listaCiudades = servicioGeneral.obtenerObjetosLimitado(Ciudad.class,
					"select #id e.id, #nombre e.nombre from Ciudad e where e.id like ('" + idPaisBus + "%')");
		}
		if (listaCiudades != null) {
			ciudadesItem = new SelectItem[listaCiudades.size()];
			int index = 0;
			for (Ciudad ciudad : listaCiudades) {
				ciudadesItem[index] = new SelectItem(ciudad.getId(), ciudad.getNombre());
				index++;
			}
		} else {
			ciudadesItem = null;
		}
	}

	private void obtenerListaDepartamentos() {
		listaDepartamentos = servicioGeneral.obtenerObjetosLimitado(Departamento.class,
				"select #id e.id, #nombre e.nombre from Departamento e where e.id like 'CO%' order by e.nombre asc");
		departamentosItem = new SelectItem[listaDepartamentos.size()];
		int index = 0;
		for (Departamento departamento : listaDepartamentos) {
			departamentosItem[index] = new SelectItem(departamento.getId(), departamento.getNombre());
			index++;
		}
		cambiarDepartamento(true);
	}

	public void cambiarSede() {
		List<Dependencia> facultadesUN = servicioDependencia.obtenerFacultadesXSede(sedeSolicitante);
		facultadesItem = new SelectItem[facultadesUN.size()];
		int index = 0;
		for (Dependencia facultad : facultadesUN) {
			facultadesItem[index] = new SelectItem(facultad.getId(), facultad.getNombre());
			index++;
		}
	}

	public void validarFormulario1Automatico() {
		validarFormulario1(false);
	}

	public void validarFormulario1Manual() {
		validarFormulario1(true);
	}

	private void validarFormulario1(boolean mostrarMensajes) {
		bloquearFormulario2 = false;
		List<String> errores = new ArrayList<String>();

		if (esCadenaVacia(tipoUsuario)) {
			errores.add("Por favor, indique el usuario asociado a la solicitud.");
			bloquearFormulario2 = true;
		} else {
			if (esCadenaVacia(solicitante.getId().getTipoDocumento())) {
				errores.add("Por favor, indique el tipo de documento del solicitante.");
				bloquearFormulario2 = true;
			}
			if (esCadenaVacia(solicitante.getId().getDocumento())) {
				errores.add("Por favor, indique el documento del solicitante.");
				bloquearFormulario2 = true;
			}
			if (solicitante.getCiudadExpedicion() == null && isUsuarioExterno()) {
				errores.add("Por favor, indique la ciudad de expedición del documento del solicitante.");
				bloquearFormulario2 = true;
			}
			if (esCadenaVacia(solicitante.getNombre1()) && isUsuarioExterno()) {
				errores.add("Por favor, indique el primer nombre del solicitante.");
				bloquearFormulario2 = true;
			}
			if (esCadenaVacia(solicitante.getApellido1()) && isUsuarioExterno()) {
				errores.add("Por favor, indique el primer apellido del solicitante.");
				bloquearFormulario2 = true;
			}
			if (solicitante.getFechaNacimiento() == null && isUsuarioExterno()) {
				errores.add("Por favor, indique la fecha de nacimiento del solicitante.");
				bloquearFormulario2 = true;
			}
			if (esCadenaVacia(solicitante.getTelefono()) && isUsuarioExterno()) {
				errores.add("Por favor, indique el teléfono del solicitante.");
				bloquearFormulario2 = true;
			} else if (!cadenaEsValorNumerico(solicitante.getTelefono()) && isUsuarioExterno()) {
				errores.add("Por favor, indique un teléfono válido para el solicitante.");
				bloquearFormulario2 = true;
			}
			if (esCadenaVacia(solicitante.getEmail()) && isUsuarioExterno()) {
				errores.add("Por favor, indique el e-mail del solicitante.");
				bloquearFormulario2 = true;
			} else if (!validarEmail(solicitante.getEmail()) && isUsuarioExterno()) {
				errores.add("Por favor, indique un e-mail válido para el solicitante.");
				bloquearFormulario2 = true;
			}
			if (esCadenaVacia(solicitante.getDireccion()) && isUsuarioExterno()) {
				errores.add("Por favor, indique la dirección del solicitante.");
				bloquearFormulario2 = true;
			}
			if (solicitante.getCiudadDomicilio() == null && isUsuarioExterno()) {
				errores.add("Por favor, indique la ciudad de domicilio del solicitante.");
				bloquearFormulario2 = true;
			}
			if (esCadenaVacia(facultadSolicitante) && isUsuarioExterno()) {
				errores.add("Por favor, indique la facultad del solicitante.");
				bloquearFormulario2 = true;
			}
			if (esCadenaVacia(rolEditorial)) {
				errores.add("Por favor, indique el rol editorial del solicitante.");
				bloquearFormulario2 = true;
			}
		}
		if (bloquearFormulario2 && mostrarMensajes) {
			for (String mensaje : errores) {
				generarMsg(2, mensaje, "msgsForm1");
			}
			return;
		}
		if (!bloquearFormulario2) {
			if (registro.getId() == null) {
				if (servicioPersona.obtenerPersona(solicitante.getId()) == null) {
					servicioPersona.insertarNuevaPersona(solicitante);
				} else {
					servicioPersona.actualizarPersonaDatosBasicos(solicitante);
				}
				if (!(solicitante instanceof InvestigadorExterno) && isUsuarioExterno()) {
					InvestigadorExterno invExterno = new InvestigadorExterno();
					invExterno.setId(solicitante.getId());
					invExterno.setInterno("N");
					invExterno.setEvaluador("N");
					servicioPersona.insertarNuevoInvestigador(invExterno);
					invExterno.setInstitucion((Institucion) servicioGeneral.obtenerObjeto(new Institucion(), "1"));
					servicioPersona.insertarNuevoInvestigadorExterno(invExterno);
				}

				Long idConvocatoria = (Long) sesion.getAttribute("idConvocatoriaActual");

				if (idConvocatoria != null) {
					convocatoriaActual = (Convocatoria) servicioGeneral.obtenerObjeto(new Convocatoria(),
							idConvocatoria);
				} else {
					convocatoriaActual = (Convocatoria) servicioGeneral.obtenerObjeto(new Convocatoria(), 1L);
				}

				registro.setModalidad(convocatoriaActual);
				InvestigadorProyecto ip = new InvestigadorProyecto();
				ip.setProyecto(registro);
				ip.setTipo((TipoInvestigador) servicioGeneral.obtenerObjeto(new TipoInvestigador(), "P"));
				ip.setInvestigador(servicioPersona.obtenerInvestigador(solicitante.getId()));
				ip.setDependencia((Dependencia) servicioGeneral.obtenerObjeto(new Dependencia(), facultadSolicitante));
				ip.setFuncion(tipoUsuario + "-" + rolEditorial);
				registro.adicionarInvestigadorProyecto(ip);
				registro.setEstadoProyecto((EstadoProyecto) servicioGeneral.obtenerObjeto(new EstadoProyecto(), "I"));
			} else {
				InvestigadorProyecto ip = registro.getListaInvestigadorPrincipal().get(0);
				ip.setDependencia((Dependencia) servicioGeneral.obtenerObjeto(new Dependencia(), facultadSolicitante));
				ip.setFuncion(tipoUsuario + "-" + rolEditorial);
				servicioGeneral.guardarObjeto(ip);
			}
			HistoricoEstadoProyecto hepry = new HistoricoEstadoProyecto();
			Date fechaHoy = new Date();
			hepry.setEstadoProyecto(registro.getEstadoProyecto());
			hepry.setFecha(fechaHoy);
			hepry.setJustificacion("");
			hepry.setResponsable(cargarPersonaActual());
			registro.adicionarHistorico(hepry);
			servicioGeneral.guardarObjeto(registro);
			generarMsg(1,
					"Formulario SOLICITANTE validado correctamente, ya puede acceder a la pestaña de INFORMACIÓN GENERAL.",
					"msgsForm1");
		}

		cargarProyectosInvestigaroPrincipal();
	}

	public void validarFormulario2Automatico() {
		validarFormulario2(false);
	}

	public void validarFormulario2Manual() {
		validarFormulario2(true);
	}

	private void validarFormulario2(boolean mostrarMensajes) {
		bloquearFormulario3 = false;
		List<String> errores = new ArrayList<String>();
		if (esCadenaVacia(registro.getNombre())) {
			errores.add("Por favor, indique el título del proyecto.");
			bloquearFormulario3 = true;
		}
		if (esCadenaVacia(registro.getMateriaISBN())) {
			errores.add("Por favor, indique el tema del proyecto DEWEY.");
			bloquearFormulario3 = true;
		}

		/****
		 * inicia validación Clasificacion THEMA.
		 */
		if (registro.getClasificacionThemaISBNNivel1() == null
				|| registro.getClasificacionThemaISBNNivel1().toString().equals("0")) {
			errores.add("Por favor, indique la Clasificación THEMA.");
			bloquearFormulario3 = true;
		}
		if (listaClasificacionTHEMA_N2 != null && listaClasificacionTHEMA_N2.length > 0) {
			if (registro.getClasificacionThemaISBNNivel2() == null
					|| registro.getClasificacionThemaISBNNivel2().toString().equals("0")) {
				errores.add("Por favor, indique el nivel 2 de la Clasificación THEMA.");
				bloquearFormulario3 = true;
			}
		}
		if (listaClasificacionTHEMA_N3 != null && listaClasificacionTHEMA_N3.length > 0) {
			if (registro.getClasificacionThemaISBNNivel3() == null
					|| registro.getClasificacionThemaISBNNivel3().toString().equals("0")) {
				errores.add("Por favor, indique el nivel 3 de la Clasificación THEMA.");
				bloquearFormulario3 = true;
			}
		}
		if (listaClasificacionTHEMA_N4 != null && listaClasificacionTHEMA_N4.length > 0) {
			if (registro.getClasificacionThemaISBNNivel4() == null
					|| registro.getClasificacionThemaISBNNivel4().toString().equals("0")) {
				errores.add("Por favor, indique el nivel 4 de la Clasificación THEMA.");
				bloquearFormulario3 = true;
			}
		}
		if (listaClasificacionTHEMA_N5 != null && listaClasificacionTHEMA_N5.length > 0) {
			if (registro.getClasificacionThemaISBNNivel5() == null
					|| registro.getClasificacionThemaISBNNivel5().toString().equals("0")) {
				errores.add("Por favor, indique el nivel 5 de la Clasificación THEMA.");
				bloquearFormulario3 = true;
			}
		}
		if (listaClasificacionTHEMA_N6 != null && listaClasificacionTHEMA_N6.length > 0) {
			if (registro.getClasificacionThemaISBNNivel6() == null
					|| registro.getClasificacionThemaISBNNivel6().toString().equals("0")) {
				errores.add("Por favor, indique el nivel 6 de la Clasificación THEMA.");
				bloquearFormulario3 = true;
			}
		}
		/****
		 * Finaliza validación clasificación THEMA.
		 * 
		 */

		if (registro.getNumPaginasISBN() != null
				&& (registro.getNumPaginasISBN() > 10000000L || registro.getNumPaginasISBN() < 0L)) {
			errores.add("La extension del proyecto debe estar entre 0 y 10000000 caracteres.");
			bloquearFormulario3 = true;
		}
		if (esCadenaVacia(registro.getResumen())) {
			errores.add("Por favor, indique el resumen del proyecto.");
			bloquearFormulario3 = true;
		}
		if (registro.getFichaEditorial() == null) {
			bloquearFormulario3 = true;
		} else if (esCadenaVacia(registro.getFichaEditorial().getTipoPublicacion())) {
			errores.add("Por favor, indique el tipo de publicación del proyecto.");
			bloquearFormulario3 = true;
		}
		if (registro.getFichaEditorial().getOtroIdioma() == null) {
			bloquearFormulario3 = true;
		} else if (registro.getFichaEditorial().getOtroIdioma()
				&& esCadenaVacia(registro.getFichaEditorial().getTextoOtroIdioma())) {
			errores.add("Por favor, indique el idioma del proyecto.");
			bloquearFormulario3 = true;
		}
		if (registro.getPalabrasClaves().isEmpty()) {
			errores.add("Por favor, indique al menos una palabra clave relacionada al proyecto.");
			bloquearFormulario3 = true;
		}
		if (esCadenaVacia(registro.getAudiencia())) {
			errores.add("Por favor, indique el público objetivo del proyecto.");
			bloquearFormulario3 = true;
		}

		if (archivosTablaContenido.isEmpty()) {
			errores.add("Por favor, adjunte la tabla de contenido del proyecto.");
			bloquearFormulario3 = true;
		}
		if (bloquearFormulario3 && mostrarMensajes) {
			for (String mensaje : errores) {
				generarMsg(2, mensaje, "msgsForm2");
			}
			return;
		}
		if (!bloquearFormulario3) {
			generarMsg(1,
					"Formulario INFORMACIÓN GENERAL validado correctamente, ya puede acceder a la pestaña de INFORMACIÓN DE PROYECTO.",
					"msgsForm2");
		}
	}

	private void validarFormulario3(boolean mostrarMensajes) {
		List<String> errores = new ArrayList<String>();
		if (registro != null && registro.getFichaEditorial() != null) {
			bloquearFormulario4 = false;
			if (registro.getFichaEditorial().getOrigenProyecto() == null
					|| registro.getFichaEditorial().getOrigenProyecto() == 0) {
				errores.add("Por favor, indique el Origen del proyecto editorial.");
				bloquearFormulario4 = true;
			} else {
				if (registro.getFichaEditorial().getOrigenProyecto() == 8
						&& (registro.getFichaEditorial().getOtroOrigenProyecto() == null
								|| registro.getFichaEditorial().getOtroOrigenProyecto().equals(("")))) {
					errores.add("Por favor, indique el OTRO Origen del proyecto editorial.");
					bloquearFormulario4 = true;
				}
			}
			if (registro.getFichaEditorial().getCaracterProyecto() != null
					&& registro.getFichaEditorial().getCaracterProyecto() == 0) {
				errores.add("Por favor, indique el Carácter del proyecto editorial.");
				bloquearFormulario4 = true;
			}
			int sumaCantidadMaterialBibliografico = 0;
			for (int i = 0; i < listaCantidadMaterialBibliografico.size(); i++) {
				sumaCantidadMaterialBibliografico += listaCantidadMaterialBibliografico.get(i).getTotal();
			}
			if (sumaCantidadMaterialBibliografico == 0) {
				errores.add("Por favor, indique la Cantidad de Material Gráfico.");
				bloquearFormulario4 = true;
			}
			if (registro.getFichaEditorial().getInvolucraGrupos() != null
					&& registro.getFichaEditorial().getInvolucraGrupos()) {
				if (registro.getFichaEditorial().getIdGrupo() == null
						|| registro.getFichaEditorial().getIdGrupo() == null
						|| registro.getFichaEditorial().getIdGrupo() == 0) {
					errores.add("Por favor, seleccione el Grupo de Investigación.");
					bloquearFormulario4 = true;
				}
			} else {
				registro.getFichaEditorial().setIdGrupo(0);
			}
		}
		if (bloquearFormulario4 && mostrarMensajes) {
			for (String mensaje : errores) {
				generarMsg(2, mensaje, "msgsForm3");
			}
			return;
		}

		if (!bloquearFormulario4) {
			servicioGeneral.guardarObjeto(registro);

			for (int i = 0; i < listaCantidadMaterialBibliografico.size(); i++) {
				CantidadMaterialGrafico cmg = listaCantidadMaterialBibliografico.get(i);
				if (cmg.getProyecto() == null) {
					cmg.setProyecto(registro);
				}
				servicioGeneral.guardarObjeto(cmg);
			}

			if (proyectoExiste) {
				for (InvestigadorProyecto inv : listaParticipantesBorrados)
					servicioGeneral.eliminarObjeto(inv);
				servicioProyecto.ingresarProyecto(registro);
			}

			if (listaParticipantes.size() > 0) {
				for (int i = 0; i < listaParticipantes.size(); i++)
					registro.adicionarInvestigadorProyecto(listaParticipantes.get(i));
			}
			servicioProyecto.ingresarProyecto(registro);

			generarMsg(1,
					"Formulario INFORMACIÓN DE PROYECTO validado correctamente, ya puede acceder a la pestaña de ADJUNTAR ARCHIVOS Y ENVIAR.",
					"msgsForm2");
		}
	}

	private boolean validarFormulario4(boolean mostrarMensajes) {
		List<String> errores = new ArrayList<String>();
		boolean valido = true;
		if (!registro.getFichaEditorial().getAceptaTerminos()) {
			errores.add("Por favor debe aceptar los términos.");
			valido = false;
		}
		if (archivosAdjuntos.size() == 0) {
			errores.add("Por favor debe adjuntar los archivos asociados al proyecto.");
			valido = false;
		} else {

			for (int i = 0; i < listaTipoArchivo.size(); i++) {
				boolean loEncontro = false;
				for (int j = 0; j < archivosAdjuntos.size(); j++) {
					if (listaTipoArchivo.get(i).getId().equals(archivosAdjuntos.get(j).getTipoArchivo().getId())
							&& listaTipoArchivo.get(i).isObligatorio()) {
						loEncontro = true;
						break;
					}
				}
				if (!loEncontro && listaTipoArchivo.get(i).isObligatorio()) {
					errores.add("Por favor debe adjuntar el " + listaTipoArchivo.get(i).getNombre() + "");
					valido = false;
				}

			}
		}
		if (!valido) {
			for (String mensaje : errores) {
				generarMsg(2, mensaje, "msgsForm4");
			}
		}
		return valido;
	}

	public void validarFormulario3Manual() {
		validarFormulario3(true);
	}

	public void validarFormulario3Automatico() {
		validarFormulario3(false);
	}

	private void cargarCantidadMaterialBibliografico(Long idProyecto) {

		if (idProyecto != null) {
			listaCantidadMaterialBibliografico = servicioProyecto.getCantidadesMaterialGraficoProyecto(idProyecto);
		}

		if (listaCantidadMaterialBibliografico == null
				|| (listaCantidadMaterialBibliografico != null && listaCantidadMaterialBibliografico.size() == 0)) {
			listaCantidadMaterialBibliografico = servicioProyecto.getCantidadesMaterialGraficoNuevo();
		}
	}

	private void generarMsg(int tipo, String mensaje, String idForm) {
		switch (tipo) {
		case 1:
			FacesContext.getCurrentInstance().addMessage(idForm,
					new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, null));
			break;
		case 2:
			FacesContext.getCurrentInstance().addMessage(idForm,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, null));
			break;
		}
	}

	public boolean isTitulosCompletos() {
		return registro.getListaTitulosEditorial().size() == 5;
	}

	public boolean isPalabrasClaveCompletas() {
		return registro.getListaPalabras().size() == 5;
	}

	public boolean isUsuarioExterno() {
		return tipoUsuario != null && (tipoUsuario.equals("EGR") || tipoUsuario.equals("PEN"));
	}

	public boolean isBloquearCiudadExpedicion() {
		return tipoUsuario != null && (isUsuarioExterno() || tipoUsuario.equals("EST"));
	}

	public boolean isBloquearFormulario2() {
		return bloquearFormulario2;
	}

	public void setBloquearFormulario2(boolean bloquearFormulario2) {
		this.bloquearFormulario2 = bloquearFormulario2;
	}

	public boolean isBloquearFormulario3() {
		return bloquearFormulario3 || bloquearFormulario2;
	}

	public void setBloquearFormulario3(boolean bloquearFormulario3) {
		this.bloquearFormulario3 = bloquearFormulario3;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String getSedeSolicitante() {
		return sedeSolicitante;
	}

	public void setSedeSolicitante(String sedeSolicitante) {
		this.sedeSolicitante = sedeSolicitante;
	}

	public String getFacultadSolicitante() {
		return facultadSolicitante;
	}

	public void setFacultadSolicitante(String facultadSolicitante) {
		this.facultadSolicitante = facultadSolicitante;
	}

	public String getRolEditorial() {
		return rolEditorial;
	}

	public void setRolEditorial(String rolEditorial) {
		this.rolEditorial = rolEditorial;
	}

	public Persona getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Persona solicitante) {
		this.solicitante = solicitante;
	}

	public SelectItem[] getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}

	public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
		this.tipoDocumentoItem = tipoDocumentoItem;
	}

	public SelectItem[] getPaisesItem() {
		return paisesItem;
	}

	public void setPaisesItem(SelectItem[] paisesItem) {
		this.paisesItem = paisesItem;
	}

	public SelectItem[] getDepartamentosItem() {
		return departamentosItem;
	}

	public void setDepartamentosItem(SelectItem[] departamentosItem) {
		this.departamentosItem = departamentosItem;
	}

	public SelectItem[] getDepartamentosItem2() {
		return departamentosItem2;
	}

	public void setDepartamentosItem2(SelectItem[] departamentosItem2) {
		this.departamentosItem2 = departamentosItem2;
	}

	public SelectItem[] getCiudadesItem() {
		return ciudadesItem;
	}

	public void setCiudadesItem(SelectItem[] ciudadesItem) {
		this.ciudadesItem = ciudadesItem;
	}

	public SelectItem[] getCiudadesItem2() {
		return ciudadesItem2;
	}

	public void setCiudadesItem2(SelectItem[] ciudadesItem2) {
		this.ciudadesItem2 = ciudadesItem2;
	}

	public SelectItem[] getSedesItem() {
		return sedesItem;
	}

	public void setSedesItem(SelectItem[] sedesItem) {
		this.sedesItem = sedesItem;
	}

	public SelectItem[] getFacultadesItem() {
		return facultadesItem;
	}

	public void setFacultadesItem(SelectItem[] facultadesItem) {
		this.facultadesItem = facultadesItem;
	}

	public SelectItem[] getTipoUsuarioItem() {
		return tipoUsuarioItem;
	}

	public void setTipoUsuarioItem(SelectItem[] tipoUsuarioItem) {
		this.tipoUsuarioItem = tipoUsuarioItem;
	}

	public SelectItem[] getRolEditorialItem() {
		return rolEditorialItem;
	}

	public void setRolEditorialItem(SelectItem[] rolEditorialItem) {
		this.rolEditorialItem = rolEditorialItem;
	}

	public Proyecto getRegistro() {
		return registro;
	}

	public void setRegistro(Proyecto registro) {
		this.registro = registro;
	}

	public List<SelectItem> getListaTemaProyecto() {
		return listaTemaProyecto;
	}

	public void setListaTemaProyecto(List<SelectItem> listaTemaProyecto) {
		this.listaTemaProyecto = listaTemaProyecto;
	}

	public PalabraClave getPalabraClave() {
		return palabraClave;
	}

	public void setPalabraClave(PalabraClave palabraClave) {
		this.palabraClave = palabraClave;
	}

	public PalabraClave getPalabraClaveTabla() {
		return palabraClaveTabla;
	}

	public void setPalabraClaveTabla(PalabraClave palabraClaveTabla) {
		this.palabraClaveTabla = palabraClaveTabla;
	}

	public ProyectoEditorialTitulo getTituloActual() {
		return tituloActual;
	}

	public void setTituloActual(ProyectoEditorialTitulo titulo) {
		this.tituloActual = titulo;
	}

	public ProyectoEditorialTitulo getTituloEliminar() {
		return tituloEliminar;
	}

	public void setTituloEliminar(ProyectoEditorialTitulo tituloEliminar) {
		this.tituloEliminar = tituloEliminar;
	}

	public UploadedFile getTablaContenido() {
		return tablaContenido;
	}

	public void setTablaContenido(UploadedFile tablaContenido) {
		this.tablaContenido = tablaContenido;
	}

	public List<Archivo> getArchivosTablaContenido() {
		return archivosTablaContenido;
	}

	public void setArchivosTablaContenido(List<Archivo> archivosTablaContenido) {
		this.archivosTablaContenido = archivosTablaContenido;
	}

	public Archivo getArchivoEliminar() {
		return archivoEliminar;
	}

	public void setArchivoEliminar(Archivo archivoEliminar) {
		this.archivoEliminar = archivoEliminar;
	}

	public boolean isBloquearFormulario4() {
		return bloquearFormulario4;
	}

	public void setBloquearFormulario4(boolean bloquearFormulario4) {
		this.bloquearFormulario4 = bloquearFormulario4;
	}

	public SelectItem[] getOrigenProyectoItem() {
		return origenProyectoItem;
	}

	public void setOrigenProyectoItem(SelectItem[] origenProyectoItem) {
		this.origenProyectoItem = origenProyectoItem;
	}

	public SelectItem[] getCaracterProyectoItem() {
		return caracterProyectoItem;
	}

	public void setCaracterProyectoItem(SelectItem[] caracterProyectoItem) {
		this.caracterProyectoItem = caracterProyectoItem;
	}

	public List<SelectItem> getListaGrupos() {
		return listaGrupos;
	}

	public String getTipoInvestigador() {
		return tipoInvestigador;
	}

	public void setTipoInvestigador(String tipoInvestigador) {
		this.tipoInvestigador = tipoInvestigador;
	}

	public SelectItem[] getAutores() {
		return autores;
	}

	public void setAutores(SelectItem[] autores) {
		this.autores = autores;
	}

	public String getTipoDocumentoInvestigador() {
		return tipoDocumentoInvestigador;
	}

	public void setTipoDocumentoInvestigador(String tipoDocumentoInvestigador) {
		this.tipoDocumentoInvestigador = tipoDocumentoInvestigador;
	}

	public String getDocumentoCoinv() {
		return documentoCoinv;
	}

	public void setDocumentoCoinv(String documentoCoinv) {
		this.documentoCoinv = documentoCoinv;
	}

	public SelectItem[] getPaisItem() {
		return paisItem;
	}

	public void setPaisItem(SelectItem[] paisItem) {
		this.paisItem = paisItem;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getRolAutorISBN() {
		return rolAutorISBN;
	}

	public void setRolAutorISBN(String rolAutorISBN) {
		this.rolAutorISBN = rolAutorISBN;
	}

	public List<SelectItem> getListaRolAutorISBN() {
		return listaRolAutorISBN;
	}

	public void setListaRolAutorISBN(List<SelectItem> listaRolAutorISBN) {
		this.listaRolAutorISBN = listaRolAutorISBN;
	}

	public boolean isEsOtraVinculacion() {
		return esOtraVinculacion;
	}

	public void setEsOtraVinculacion(boolean esOtraVinculacion) {
		this.esOtraVinculacion = esOtraVinculacion;
	}

	public InvestigadorExterno getInvestigadorExterno() {
		return investigadorExterno;
	}

	public void setInvestigadorExterno(InvestigadorExterno investigadorExterno) {
		this.investigadorExterno = investigadorExterno;
	}

	public SelectItem[] getGeneroItem() {
		return generoItem;
	}

	public String getInsitucionNombre() {
		return insitucionNombre;
	}

	public void setInsitucionNombre(String insitucionNombre) {
		this.insitucionNombre = insitucionNombre;
	}

	public List<InvestigadorProyecto> getListaParticipantes() {
		return listaParticipantes;
	}

	public void setListaParticipantes(List<InvestigadorProyecto> listaParticipantes) {
		this.listaParticipantes = listaParticipantes;
	}

	public void cambiarVinculacion() {

		if (tipoInvestigador.equals("AEL")) {
			esOtraVinculacion = true;
			cargarFuentesFinanciacionExternas();
		} else {
			esOtraVinculacion = false;
		}

	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public void eliminarParticipante() {

		System.out.println("id del participante: " + participante.getInvestigador().getId().getDocumento());

		try {

			listaParticipantes.remove(participante);
			registro.getInvestigadoresProyecto().remove(participante);

			listaParticipantesBorrados.add(participante);

			participante = new InvestigadorProyecto();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void agregarParticipante() {
		Investigador nuevoInvestigador = new Investigador();
		boolean encuentraParticipante = false;

		if (documentoCoinv != null && !documentoCoinv.equals("") && !documentoCoinv.equals(" ") && !pais.equals(null)
				&& !pais.equals("") && !pais.equals("00") && !rolAutorISBN.equals("0") && !rolAutorISBN.equals(null)) {

			if (listaParticipantes.size() > 0) {

				for (int i = 0; i < listaParticipantes.size(); i++) {
					InvestigadorProyecto invpry = listaParticipantes.get(i);
					if (invpry.getInvestigador().getId().getDocumento().equals(documentoCoinv)
							&& invpry.getInvestigador().getId().getTipoDocumento().equals(tipoDocumentoInvestigador)) {

						encuentraParticipante = true;
						break;
					}
				}
			}

			if (!encuentraParticipante) {
				try {

					if (tipoInvestigador.equals("AI")) { // si es interno

						InvestigadorInterno investigadorInterno = servicioPersona.obtenerInvestigadorInterno(
								new IdPersona(this.documentoCoinv, tipoDocumentoInvestigador));

						if (investigadorInterno != null) {

							// Nacionalidad y Rol - Si es interno
							investigadorInterno.setPaisOrigen(pais);
							// investigadorInterno.setFechaNacimiento(fechaNacimiento);
							Persona persona = servicioPersona.obtenerPersona(investigadorInterno.getId());
							if (persona == null) {
								servicioPersona.insertaInterno(investigadorInterno);
							}
							// Agregar participante a la listaparticipante
							InvestigadorProyecto participante = new InvestigadorProyecto();
							participante.setInvestigador(investigadorInterno);
							participante.setDedicacionHorasSemana(Short.parseShort("0"));
							participante.setProyecto(registro);

							/*if (personaActual.getId().getTipoDocumento().equals(tipoDocumentoInvestigador)
									&& personaActual.getId().getDocumento().equals(documentoCoinv)) {
								TipoInvestigador ti = (TipoInvestigador) servicioGeneral
										.obtenerObjeto(new TipoInvestigador(), "AI");
								participante.setTipo(ti);

								String sql = "select pp from TipoInvestigador pp where pp.id ='" + rolAutorISBN + "'";
								List lista = servicioGeneral.obtenerObjetos(sql);

								if (lista.size() > 0) {
									TipoInvestigador rol = (TipoInvestigador) lista.get(0);
									participante.setFuncion(rol.getNombre());
								}
							} else {*/
								TipoInvestigador ti = new TipoInvestigador();
								ti = (TipoInvestigador) servicioGeneral.obtenerObjeto(new TipoInvestigador(),
										rolAutorISBN);
								participante.setTipo(ti);

								participante.setFuncion("Autor Interno");
							//}

							listaParticipantes.add(participante);

						} else { // No se encontró como investigador interno
									// si es estudiante
							IdPersona idEst = new IdPersona(this.documentoCoinv, tipoDocumentoInvestigador);
							Estudiante e = servicioPersona.obtenerEstudiante(idEst);

							if (e != null) {

								nuevoInvestigador = servicioPersona.obtenerInvestigador(idEst);
								if (nuevoInvestigador == null) {
									InvestigadorInterno nvoinv = e.convertirAInvestigador();
									if (e != null && e.getDependencia() != null) {
										nvoinv.setDependencia(e.getDependencia());
									}
									Persona per = servicioPersona.obtenerPersona(nvoinv.getId());

									if (per != null) {
										servicioPersona.insertarNuevoInvestigador(nvoinv);
										servicioPersona.insertaInterno(nvoinv);
									} else {
										servicioPersona.guardarInvestigador(nvoinv);
									}
									nuevoInvestigador = servicioPersona.obtenerInvestigador(nvoinv.getId());
								}

								InvestigadorInterno nvoinv = e.convertirAInvestigador();
								nvoinv.setDependencia(e.getDependencia());

								// Nacionalidad y Rol - Si es estudiantes
								nvoinv.setPaisOrigen(pais);
								// nvoinv.setFechaNacimiento(fechaNacimiento);
								servicioGeneral.guardarObjeto(nvoinv);

								// Agregar participante a la listaparticipante
								InvestigadorProyecto participante = new InvestigadorProyecto();
								participante.setInvestigador(nvoinv);
								participante.setDedicacionHorasSemana(Short.parseShort("0"));
								participante.setFuncion("Participante");
								participante.setProyecto(registro);
								TipoInvestigador ti = new TipoInvestigador();
								ti = (TipoInvestigador) servicioGeneral.obtenerObjeto(new TipoInvestigador(),
										rolAutorISBN);
								participante.setTipo(ti);

								listaParticipantes.add(participante);

							} else { // si no se encontró como estudiante
								FacesContext.getCurrentInstance().addMessage(null,
										new FacesMessage(FacesMessage.SEVERITY_FATAL,
												"La persona con el número de documento " + documentoCoinv
														+ " no fue encontrado",
												""));
							}

						}

					} else { // si es otro

						if (fechaNacimiento == null) {
							FacesContext.getCurrentInstance().addMessage(null,
									new FacesMessage(FacesMessage.SEVERITY_FATAL,
											"Debe ingresar la fecha de nacimiento",
											"Debe ingresar la fecha de nacimiento"));

							calcularNumeroAutores();

							return;
						}

						if (investigadorExterno.getNombre1() != null && !investigadorExterno.getNombre1().equals("")
								&& investigadorExterno.getApellido1() != null
								&& !investigadorExterno.getApellido1().equals("")) {

							IdPersona id = new IdPersona(this.documentoCoinv, tipoDocumentoInvestigador);
							investigadorExterno.setId(id);
							investigadorExterno.setInterno(Investigador.EXTERNO);
							investigadorExterno.setEvaluador(Investigador.NO_EVALUADOR);
							investigadorExterno.setPaisOrigen(pais);
							investigadorExterno.setFechaNacimiento(fechaNacimiento);

							if (!esCadenaVacia(insitucionNombre)) {
								FuenteFinanciacion institucion = (FuenteFinanciacion) servicioGeneral
										.obtenerObjeto(FuenteFinanciacion.class, insitucionNombre);
								investigadorExterno.setInstitucionInvestigador(institucion);
							}

							Persona nuevaPersona = servicioPersona.obtenerPersona(id);

							if (nuevaPersona == null) {
								try {
									servicioPersona.guardarInvestigador(investigadorExterno);

								} catch (Exception e) {
								}

							} else {
								InvestigadorExterno persona = servicioPersona.obtenerInvestigadorExterno(id);
								if (persona == null) {
									servicioPersona.insertarExterno(investigadorExterno);
								}
							}

							// Agregar participante a la listaparticipante
							InvestigadorProyecto participante = new InvestigadorProyecto();
							participante.setInvestigador(investigadorExterno);
							participante.setDedicacionHorasSemana(Short.parseShort("0"));
							participante.setFuncion("Autor Externo");
							participante.setProyecto(registro);
							participante.setInstitucionInvestigador(investigadorExterno.getInstitucionInvestigador());
							TipoInvestigador ti = new TipoInvestigador();
							ti = (TipoInvestigador) servicioGeneral.obtenerObjeto(new TipoInvestigador(), rolAutorISBN);
							participante.setTipo(ti);
							listaParticipantes.add(participante);
							investigadorExterno = new InvestigadorExterno();
							esOtraVinculacion = false;

						} else {
							FacesContext.getCurrentInstance().addMessage(null,
									new FacesMessage(FacesMessage.SEVERITY_FATAL,
											"Por favor ingresar nombres y apellidos de la persona a registrar", ""));
						}

					}

					documentoCoinv = "";
					tipoInvestigador = "AI";

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL, "La persona indicada ya se encuentra registrada.",
								"La persona indicada ya se encuentra registrada."));
			}

		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
					"Por favor ingrese el número de identificación, país de procedencia, rol y fecha de nacimiento",
					""));
		}

		calcularNumeroAutores();

	}

	public void calcularNumeroAutores() {
		int numeroParticipanes = 0;
		if (listaParticipantes != null) {
			numeroParticipanes = listaParticipantes.size();
		}
		if (registro != null && registro.getFichaEditorial() != null) {
			registro.getFichaEditorial().setNumeroAutores(numeroParticipanes);
		}
	}

	private void cargarListaTipoArchivos() {
		listaTipoArchivo = servicioGeneral
				.obtenerListaObjetos("TipoArchivo e where e.parametro in ('ARCHIVO_EDITORIAL')");
		tipoArchivoItem = new SelectItem[listaTipoArchivo.size()];
		for (int i = 0; i < listaTipoArchivo.size(); i++) {
			TipoArchivo ta = (TipoArchivo) listaTipoArchivo.get(i);
			tipoArchivoItem[i] = new SelectItem(ta.getId(), ta.getNombre());
		}
		TipoArchivo tipoArchivo = (TipoArchivo) listaTipoArchivo.get(0);
		idArchivoAdjunto = tipoArchivo.getId().toString();
	}

	public void enviarSolicitud() {
		validarFormulario1(true);
		validarFormulario2(true);
		validarFormulario3(true);
		if (validarFormulario4(true) && !bloquearFormulario4) {
			registro.cambiarEstadoPersona(EstadoProyecto.PROPUESTO, cargarPersonaActual());
			servicioProyecto.ingresarProyecto(registro);
			sesion.removeAttribute("manejadorEditorialProyectosHome");

			edicion = false;

			Correo correoNotificacion = new Correo();
			CorreoPlantilla cpNotificacion = cargarPlantilla(388);
			correoNotificacion.setOrigen(Correo.CORREO_HERMES_SOLICITUDES);
			//correoNotificacion.adicionarCopiaOculta(Correo.CORREO_HERMES_SOLICITUDES);

			correoNotificacion.setAsunto(cpNotificacion.getAsunto().replaceAll("<<ID>>", registro.getId().toString()));
			correoNotificacion.setCuerpo(cpNotificacion.getCuerpo()
					.replaceAll("<<SOLICITANTE>>", personaActual.getNombreCompletoMinusculas())
					.replaceAll("<<ID>>", registro.getId().toString()));

			InvestigadorInterno solicitante = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());

			for (InvestigadorInterno per : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class,
					" JOIN i.roles r WHERE r.id = '" + Rol.ASESOR_EDITORIAL + "' and i.dependencia.sede.id = '"
							+ solicitante.getDependencia().getSede().getId() + "'")) {

				if (per.getDependencia().getId().equals(solicitante.getDependencia().getId())) {
					correoNotificacion.adicionarDireccion(per.getEmail());
					correoNotificacion.setCuerpo(
							reemplazarNombreAsesor(cpNotificacion.getCuerpo(), per.getNombreCompletoMinusculas()));
					break;
				} else if (per.getDependencia().getFacultad().getId()
						.equals(solicitante.getDependencia().getFacultad().getId())) {
					correoNotificacion.adicionarDireccion(per.getEmail());
					correoNotificacion.setCuerpo(
							reemplazarNombreAsesor(cpNotificacion.getCuerpo(), per.getNombreCompletoMinusculas()));
					break;
				} else {
					correoNotificacion.adicionarDireccion(per.getEmail());
					correoNotificacion.setCuerpo(
							reemplazarNombreAsesor(cpNotificacion.getCuerpo(), per.getNombreCompletoMinusculas()));
					break;
				}
			}

			if (correoNotificacion.getDirecciones() != null) {

				servicioCorreo.enviarCorreo(correoNotificacion);
			}

			generarMsg(1, "Su proyecto se ingresó satisfactoriamente con el número: " + this.registro.getId(),
					"msgsForm4");
		}
	}

	public String reemplazarNombreAsesor(String cuerpoCorreo, String nombreAsesor) {
		return cuerpoCorreo.replaceAll("<<ASESOR>>", nombreAsesor);
	}

	public InvestigadorProyecto getParticipante() {
		return participante;
	}

	public void setParticipante(InvestigadorProyecto participante) {
		this.participante = participante;
	}

	public UploadedFile getArchivo() {
		return archivo;
	}

	public void setArchivo(UploadedFile archivo) {
		this.archivo = archivo;
	}

	public List<Archivo> getArchivosAdjuntos() {
		return archivosAdjuntos;
	}

	public void setArchivosAdjuntos(List<Archivo> archivosAdjuntos) {
		this.archivosAdjuntos = archivosAdjuntos;
	}

	public String getIdArchivoAdjunto() {
		return idArchivoAdjunto;
	}

	public void setIdArchivoAdjunto(String idArchivoAdjunto) {
		this.idArchivoAdjunto = idArchivoAdjunto;
	}

	public SelectItem[] getTipoArchivoItem() {
		return tipoArchivoItem;
	}

	public void setTipoArchivoItem(SelectItem[] tipoArchivoItem) {
		this.tipoArchivoItem = tipoArchivoItem;
	}

	public boolean isBloquearEnvio() {
		return bloquearEnvio;
	}

	public void setBloquearEnvio(boolean bloquearEnvio) {
		this.bloquearEnvio = bloquearEnvio;
	}

	public String getIdArchivoEliminar() {
		return idArchivoEliminar;
	}

	public void setIdArchivoEliminar(String idArchivoEliminar) {
		this.idArchivoEliminar = idArchivoEliminar;
	}

	public boolean isEdicion() {
		return edicion;
	}

	public void setEdicion(boolean isEdicion) {
		this.edicion = isEdicion;
	}

	public SelectItem[] getListaClasificacionTHEMA_N1() {
		return listaClasificacionTHEMA_N1;
	}

	public SelectItem[] getListaClasificacionTHEMA_N2() {
		return listaClasificacionTHEMA_N2;
	}

	public SelectItem[] getListaClasificacionTHEMA_N3() {
		return listaClasificacionTHEMA_N3;
	}

	public SelectItem[] getListaClasificacionTHEMA_N4() {
		return listaClasificacionTHEMA_N4;
	}

	public SelectItem[] getListaClasificacionTHEMA_N5() {
		return listaClasificacionTHEMA_N5;
	}

	public SelectItem[] getListaClasificacionTHEMA_N6() {
		return listaClasificacionTHEMA_N6;
	}

	public List<SelectItem> getListaAudienciaISBN() {
		return listaAudienciaISBN;
	}

	public SelectItem[] getProyectosAsociadostem() {
		return proyectosAsociadostem;
	}

	public Convocatoria getConvocatoriaActual() {
		return convocatoriaActual;
	}

	public void setConvocatoriaActual(Convocatoria convocatoriaActual) {
		this.convocatoriaActual = convocatoriaActual;
	}

	public List<TipoArchivo> getListaTipoArchivo() {
		return listaTipoArchivo;
	}

	public void setListaTipoArchivo(List<TipoArchivo> listaTipoArchivo) {
		this.listaTipoArchivo = listaTipoArchivo;
	}

}