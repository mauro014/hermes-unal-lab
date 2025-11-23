package co.edu.unal.hermes.vista.movilidad;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.dao.DataIntegrityViolationException;

import co.edu.unal.hermes.modelo.ActividadMovilidad;
import co.edu.unal.hermes.modelo.ActividadMovilidadVE;
import co.edu.unal.hermes.modelo.ArchivoMovilidadDE;
import co.edu.unal.hermes.modelo.ArchivoMovilidadVE;
import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.ConvocatoriaPadreParametrizacion;
import co.edu.unal.hermes.modelo.ConvocatoriaParametrizacion;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Departamento;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Institucion;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorGrupo;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.MovilidadArchivo;
import co.edu.unal.hermes.modelo.MovilidadDocentesExterior;
import co.edu.unal.hermes.modelo.MovilidadEstudiantesPosgrado;
import co.edu.unal.hermes.modelo.MovilidadPonencia;
import co.edu.unal.hermes.modelo.Pais;
import co.edu.unal.hermes.modelo.Parametro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.TipoArchivoMovilidad;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoEntidadInvestigacion;
import co.edu.unal.hermes.modelo.TipoInforme;
import co.edu.unal.hermes.modelo.TipoMovilidad;
import co.edu.unal.hermes.modelo.TipoPonencia;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.utils.Navegacion;

public class ManejadorCrearEditarMovilidadEvento extends ManejadorBaseMovilidad {

	private static final long serialVersionUID = -1175829549241190714L;
	private String aceptacionDIB;
	private String aceptacionFacultad;
	private String aceptacionPonencia;
	private Long aporteFacultad;
	private SelectItem[] aprobacion = { new SelectItem("NO", "NO"), new SelectItem("SI", "SI") };
	private UploadedFile archivoObligatorio;
	private boolean bFinanciacion;
	private boolean bOtroTipoPonencia;
	private String categoriaInvestigador;
	private String departamentoDocente;
	private String departamentoSeleccionado = "";
	private Dependencia dependencia;
	private String dependenciaId;
	private SelectItem[] depsItem;
	private Long destinado;
	private String documento;
	private String documentoDocente;
	private String tipoDocDocente;
	private String facultadDocente;
	private String facultadSeleccionada = "";
	private boolean fechaIncorrecta;
	private boolean fechaIncorrecta2;
	private String financiacionActual;
	private Grupo grupo;
	private Grupo grupoActual;
	private String idCiudad;
	private String idcreador;
	private String tipoDocCreador;
	private boolean identificacion;
	private String idgrupo;
	private String idInstitucion;
	private String idInvestigador;
	private String idPais;
	private String idProyecto;
	private String idPlanAccion;
	private String idTipoPonencia;
	private SelectItem[] institucionItem;
	private Investigador investigadorActual;
	private SelectItem[] investigadorItem;
	private List listaArchivos;
	private List listaCategoriaInvestigador;
	private List listaDependencias;
	private List listaGrupos;
	private List listaGruposInv;
	private List listaInstitucion;
	private List listaIntegrantesGrupo;
	private List listaTipoArchivo;
	private boolean mostrarDatosBasicos;
	private boolean mostrarDatosEvento;
	private boolean mostrarDatosEventoAux;
	private boolean bErrorDocumentos;
	private String nombreDepartamentoSeleccionado;
	private String nombreDocente;
	private String nombreObligatorio;
	private boolean esConvocatoriaFacultad = false;

	private String nombreEvento;
	private String nombreFacultadSeleccionada;
	private String nombreLiderGrupo;
	private String nombreSedeSeleccionada;

	private String otroTipoPonencia;

	private List paises;

	private HtmlPanelGroup panelArchivos;
	private boolean panelMasDatos;
	private boolean panelMasDatos2;
	private boolean panelNoExiste;
	private Persona persona;
	private List proyectosInvestigador;
	private List planesAccionInvestigador;
	private String resolucionViaje;
	private Investigador responsableGrupo;

	private String sedeSeleccionada = "";
	private UIData tablaPonencias;

	private String solDocente;
	private String solInscripcion;
	private List listaPonenciasObligatorias;

	private List listaArchivosObligatorios;

	private HtmlDataTable tablaArchivosObligatorios;
	private HtmlDataTable tablaArchivosObligatoriosSel;

	private TipoDocumento tipoDocumento;
	private SelectItem[] tipoDocumentoItem;
	private String tipoInvestigador = new String();
	private SelectItem[] tipoPonencia;
	private String tipoPonenciaActual;
	private String tituloPonencia;
	private String errores[];
	private boolean bErrorProyecto;
	private boolean bErrorEvento;
	private boolean bErrorTitulo;
	private boolean bErrorPais;
	private boolean bErrorCiudad;
	private boolean bErrorNombreUniversidad;
	private boolean bErrorPonencia;
	private boolean bErrorResumen;
	private boolean bErrorResolucion;
	private boolean bErrorListaPonencia;
	private boolean bErrorTiquete;
	private boolean bErrorInscripcion;
	private boolean bPlanAccion;
	private boolean mostrarError = false;
	private String msgError = "Su solicitud NO ha sido enviada, por favor verifique la información y vuelva a GUARDAR. Debe seleccionar SI desea confirmar el envio para enviar la movilidad o NO en caso de guardar parcialmente.";

	private String noExiste;
	private String noExiste1;
	private boolean bImprimirReporte;
	private UploadedFile archivoVicerectoria;
	private UploadedFile archivoDireccion;
	private UploadedFile archivoFacultad;
	private UploadedFile archivoDepartamento;
	private final static String RUTA_ADJUNTO = "/pages/Movilidad";
	private MovilidadDocentesExterior mde;
	private String nombreVicerrectoria = "";
	private String nombreSede = "";
	private String nombreFacultad = "";
	private String nombreDepartamento = "";
	private UploadedFile archivoCargar;
	private HtmlDataTable tablaArchivos;
	private HtmlDataTable tablaEntidadInvestigacion;
	List listaEntidadInvestigacion;

	private SelectItem[] tipoDocumentoSelItem;
	private String tipoDocumentoSel;

	private String aprobacionInvestigacionUN;
	private SelectItem[] aprobacionInvestigacionUNItem;

	CorreoPlantilla correoActual = new CorreoPlantilla();
	String cuerpoCorreo = "";

	private MovilidadPonencia movilidadPonenciaSeleccionada;
	private boolean puedeSubirArchivos = false;
	private ArchivoMovilidadDE archivoMovilidadDESeleccionado;
	private Date fechaMinimaInicio;
	private boolean siConvExt = false;
	private boolean siColombia = false;
	private SelectItem[] ciudadItem; // CIUDADES A MOSTRAR
	private List listaCiudades; // LISTA DE CIUDADES DEL DEPARTAMENTO
	private Departamento departamentoActual; // DEPARTAMENTO ACTUAL
	private List listaDepartamentos; // LISTA DE DEPARTAMENTOS
	private Ciudad ciudadActual; // CIUDAD SELECCIONADA
	private SelectItem[] departamentoItem; // DEPARTAMENTOS A MOSTRAR

	private SelectItem[] medioTransporteItem = { new SelectItem("1", "Terrestre"), new SelectItem("2", "Aereo") };
	private SelectItem[] caracterEventoItem;
	private ArchivoMovilidadDE documentoSeleccionado;
	
	private Long idConvocatoria;
	

	public void seleccionarTipoDocumento() {
		puedeSubirArchivos = true;
		if (tipoDocumentoSel.equals("")) {
			puedeSubirArchivos = false;
		}
		System.out.println("tipoDocumentoSel: " + tipoDocumentoSel);
	}

	private String restriccionArchivosConv;
	private boolean mostrarValorViaticos = false;
	private Long restriccionTiquetes;
	private String msgErrorPasaporte;
	private boolean errorPasaporte = false;
	private boolean esEstanciaInvestigacionDocente = false;
	private boolean esMovMegaproyectosConcursos = false;
	private boolean esMovPonencias = false;
	private ActividadMovilidad actMovi;
	private boolean bErrorActividades;
	private ActividadMovilidad actividadMovilidadSeleccionada;
	private String idSubmodalidadConv;
	private String ciudadEvento;
	private String estado;
	private String tipoConvocatoria;
	private boolean mostrarValorInscripcionEvento = false;
	private boolean mostrarValorTiquetes = false;
	private boolean mostrarValorTotalSol = false;
	private boolean mostrarSubModalidades = false;
	private boolean mostrarAportesOtrasDependencias = false;
	private boolean esEstancia = false;
	private String valorAporteOtrasDep;
	private String depValorAporteOtrasDep;
	private String tipoPonenciaPar;
	private Integer tiempoValidacionMovilidad;
	private SelectItem[] listaSubmodalidades;
	private String submodalidad;
	private Long valorTotalMovSolicitado;
	private String parCaracterEvento;
	private boolean validarGrupo = false;
	private String msgErrorGrupo;
	private boolean errorGrupo = false;

	private boolean errorSubmodalidad = false;
	private String validadcionCostosSubmodalidad;
	private boolean esMovilidadNacional = false;

	private String duracionMaximaMovilidad;

	private boolean mostrarPasaporte;
	private Long maxTrabajos;
	
	private long tiempoEjecucion;
	
	private UIComponent uiMovOtraDep;
	private UIComponent uiSedeOtraDep;
	private UIComponent uiFacOtraDep;
	private SelectItem[] sedeSolicitudItem;
	private SelectItem[] facultadItem;
	private String sedeSolicitud;
	private String facultadSolicitud;
	
	private Boolean mostrarTipoMovilidad = false;
	private Convocatoria convActual;

	public ManejadorCrearEditarMovilidadEvento() {
		convActual = new Convocatoria();
		Long idMovilidadEd = (Long) sesion.getAttribute("movilidadDocEvSel");
		ocultarPaneles();
		cargarValoresIniciales();

		obtenerListaDepartamentos();
		obtenerListaCiudades();
		personaActual = (Persona) sesion.getAttribute("persona");

		mde = new MovilidadDocentesExterior();
		listaPonenciasObligatorias = new ArrayList();
		listaArchivosObligatorios = new ArrayList();
		
		if (personaActual != null) {
			documento = personaActual.getId().getDocumento();
			TipoDocumento td = new TipoDocumento();
			td.setId(personaActual.getId().getTipoDocumento());
			tipoDocumento.setId(personaActual.getId().getTipoDocumento());
		}

		if (idMovilidadEd != null) {
			mde = servicioMovilidad.obtenerMovilidadesDocentes(idMovilidadEd);
			sesion.setAttribute("idConvocatoriaActual", mde.getConvocatoria().getId());
			idConvocatoria = mde.getConvocatoria().getId();
			try {
				buscarPersona();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			if (mde.getGrupo() != null) {
				idgrupo = mde.getGrupo().getId().toString();
			}

			if (mde.getSubModalidadConvocatoria() != null) {
				idSubmodalidadConv = mde.getSubModalidadConvocatoria();
				cargarSubModalidad();
			}
			
			if(mde.getMovilidadDirigidaOtraDependencia().equals("S")) {
				sedeSolicitud = mde.getDependencia().getSede().getId().toString();
				cargarFacultadSolcitud();
				facultadSolicitud = mde.getDependencia().getId();
			}

			if (mde.getPais() != null) {
				idPais = mde.getPais().getId();
				revisarPais();

				if (siColombia) {
					if (mde.getCiudad() != null) {
						ciudadEvento = mde.getCiudad();
						String hqlDepto = "select e from Ciudad e where e.id = '" + ciudadEvento + "'";
						List<Ciudad> listaCiudad = servicioGeneral.obtenerObjetos(hqlDepto);
						Ciudad ciudadActual = listaCiudad.get(0);
						departamentoActual = ciudadActual.getDepartamento();
						cambiarDepartamento();
					}
				}
			}

			nombreEvento = mde.getEvento() != null ? mde.getEvento() : "";
			tituloPonencia = mde.getTitulo() != null ? mde.getTitulo() : "";
			estado = "";

			mostrarDatosEvento = true;
			mostrarDatosEventoAux = true;
			panelMasDatos = true;
			panelMasDatos2 = true;

		} else {
			idSubmodalidadConv = (String) sesion.getAttribute("idSubmodalidadConv");
			cargarSubModalidad();
		}

		Proyecto p = (Proyecto) sesion.getAttribute("proyectoMovilidad");
		if (p != null) {
			mde.setProyectoFicha(p.getId());
		}

		// para saber si es de facultad
		if (sesion.getAttribute("idConvocatoriaActual") != null) {
			idConvocatoria = (Long) sesion.getAttribute("idConvocatoriaActual");
			List listConvocatorias = servicioGeneral.obtenerConvocatoriaMovilidades(idConvocatoria.toString());
			if (listConvocatorias.size() > 0 && listConvocatorias != null) {
				Convocatoria conv = (Convocatoria) listConvocatorias.get(0);

				if (conv.getPadre().getDependencia().getId().equals("1")
						|| conv.getPadre().getDependencia().getId().equals("2")
						|| conv.getPadre().getDependencia().getId().equals("3")
						|| conv.getPadre().getDependencia().getId().equals("4")
						|| conv.getPadre().getDependencia().getId().equals("5")
						|| conv.getPadre().getDependencia().getId().equals("6")
						|| conv.getPadre().getDependencia().getId().equals("7")
						|| conv.getPadre().getDependencia().getId().equals("8")) {
					esConvocatoriaFacultad = false;
				} else {
					esConvocatoriaFacultad = true;

				}

				String sqlBuscaConvocatoria = "select #id e.id, #requisitosConvTexto e.requisitosConvTexto, #esParaGrupos e.esParaGrupos, #gruposCategoriaA e.gruposCategoriaA, #gruposCategoriaB e.gruposCategoriaB, #gruposCategoriaC e.gruposCategoriaC, #gruposCategoriaD e.gruposCategoriaD, #compromisosTexto e.compromisosTexto, #tiempoEjecucionProyecto e.tiempoEjecucionProyecto, #montoApoyoGanadores e.montoApoyoGanadores, #informacionAdicional e.informacionAdicional, #areaDirigida e.areaDirigida, #gruposReconocidos e.gruposReconocidos, #restriccion e.restriccion, #caracterEventoConvocatoriaMovilidad e.caracterEventoConvocatoriaMovilidad, #submodalidadConvocatoriaMovilidad e.submodalidadConvocatoriaMovilidad, #validacionCostosConvocatoriaMovilidad e.validacionCostosConvocatoriaMovilidad, #mostrarColombiaMovilidad e.mostrarColombiaMovilidad"
						+ ", #mostrarPasaporteMovilidad e.mostrarPasaporteMovilidad, #maxTrabajosMovilidad e.maxTrabajosMovilidad, #validacionDuracionMovilidad e.validacionDuracionMovilidad, #mostrarTipoMovilidad e.mostrarTipoMovilidad, #infoAdicionalFormulario e.infoAdicionalFormulario from Convocatoria e where e.id = "
						+ idConvocatoria + "";

				List<Convocatoria> listConvs = servicioGeneral.obtenerObjetosLimitado(Convocatoria.class,
						sqlBuscaConvocatoria);
				convActual = listConvs.get(0);

				tipoConvocatoria = convActual.getRestriccion().getId();

				if (convActual.getGruposCategoriaA() != null) {
					if (convActual.getGruposCategoriaA()) {
						mostrarValorViaticos = true;
					} else {
						mostrarValorViaticos = false;
					}
				}
				if (convActual.getGruposCategoriaB() != null) {
					if (convActual.getGruposCategoriaB()) {
						mostrarValorInscripcionEvento = true;
					} else {
						mostrarValorInscripcionEvento = false;
					}
				}
				if (convActual.getGruposCategoriaC() != null) {
					if (convActual.getGruposCategoriaC()) {
						mostrarValorTiquetes = true;
					} else {
						mostrarValorTiquetes = false;
					}
				}
				if (convActual.getGruposReconocidos() != null) {
					if (convActual.getGruposReconocidos()) {
						mostrarAportesOtrasDependencias = true;
					} else {
						mostrarAportesOtrasDependencias = false;
					}
				}

				if (convActual.getGruposCategoriaD() != null) {
					if (convActual.getGruposCategoriaD()) {
						mostrarValorTotalSol = true;
					} else {
						mostrarValorTotalSol = false;
					}
				}

				if (convActual.getValidacionDuracionMovilidad() != null) {
					duracionMaximaMovilidad = convActual.getValidacionDuracionMovilidad();
				}
				
				if (convActual.getTiempoEjecucionProyecto() != null) {
					tiempoEjecucion = convActual.getTiempoEjecucionProyecto();
				}

				if (convActual.getAreaDirigida() != null) {
					if (convActual.getAreaDirigida().equals("ESTANCIA")) {
						esEstancia = true;
					} else {
						esEstancia = false;
					}
				}

				if (convActual.getSubmodalidadConvocatoriaMovilidad() != null) {
					mostrarSubModalidades = true;
					List listaOpcionsSeleDos = new ArrayList<DominioDetalle>();
					listaOpcionsSeleDos = servicioGeneral.obtenerObjetos(
							"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
									+ convActual.getSubmodalidadConvocatoriaMovilidad() + "' order by dd.descripcion");
					setListaSubmodalidades(new SelectItem[listaOpcionsSeleDos.size()]);
					for (int i = 0; i < listaOpcionsSeleDos.size(); i++) {
						DominioDetalle dd = (DominioDetalle) listaOpcionsSeleDos.get(i);
						listaSubmodalidades[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
						dd = null;
					}
				} else {
					mostrarSubModalidades = false;
				}

				if (convActual.getValidacionCostosConvocatoriaMovilidad() != null) {
					validadcionCostosSubmodalidad = convActual.getValidacionCostosConvocatoriaMovilidad();
				}

				if (convActual.getEsParaGrupos() != null) {
					if (convActual.getEsParaGrupos()) {
						validarGrupo = true;
					} else {
						validarGrupo = false;
					}
				} else {
					validarGrupo = false;
				}

				if (convActual.getMontoApoyoGanadores() != null) {
					restriccionTiquetes = Long.parseLong(convActual.getMontoApoyoGanadores());
				}

				if (convActual.getCompromisosTexto() != null) {
					if (!convActual.getCompromisosTexto().equals("")) {
						tipoPonenciaPar = convActual.getCompromisosTexto();
					}
				}

				if (convActual.getTiempoEjecucionProyecto() != null) {
					if (!convActual.getTiempoEjecucionProyecto().equals("")) {
						tiempoValidacionMovilidad = convActual.getTiempoEjecucionProyecto();
					}
				} else {
					tiempoValidacionMovilidad = 30;
				}

				if (convActual.getCaracterEventoConvocatoriaMovilidad() != null) {
					if (!convActual.getCaracterEventoConvocatoriaMovilidad().equals("")) {
						parCaracterEvento = convActual.getCaracterEventoConvocatoriaMovilidad();
					} else {
						parCaracterEvento = "'1','2'";
					}
				} else {
					parCaracterEvento = "'1','2'";
				}

				if (convActual.getMostrarColombiaMovilidad() != null) {
					if (convActual.getMostrarColombiaMovilidad().equals("SC")) {
						esMovilidadNacional = true;
						cargarPaises();
						idPais = "CO";
						revisarPais();
					}
				}

				if (convActual.getMostrarPasaporteMovilidad() == null
						|| convActual.getMostrarPasaporteMovilidad().equals("1")) {
					mostrarPasaporte = true;
				} else {
					mostrarPasaporte = false;
				}
				if (convActual.getMaxTrabajosMovilidad() != null) {
					maxTrabajos = convActual.getMaxTrabajosMovilidad();
				} else {
					maxTrabajos = 1000l;
				}
				
				if (!esNulo(convActual.getMostrarTipoMovilidad())) {
					if (convActual.getMostrarTipoMovilidad()) {
						mostrarTipoMovilidad = true;
					}
				}

			}
		}

		List listaTipoPonencia;
		listaTipoPonencia = new ArrayList();
		if (tipoPonenciaPar != null) {
			if (!tipoPonenciaPar.equals("")) {
				listaTipoPonencia = servicioGeneral
						.obtenerListaObjetos("TipoPonencia where id in (" + tipoPonenciaPar + ") order by descripcion");
			}
		} else {
			listaTipoPonencia = servicioGeneral
					.obtenerListaObjetos("TipoPonencia where id in (2,4,9) order by descripcion");
		}

		tipoPonencia = new SelectItem[listaTipoPonencia.size()];
		for (int i = 0; i < listaTipoPonencia.size(); i++) {
			TipoPonencia tpo = (TipoPonencia) listaTipoPonencia.get(i);
			tipoPonencia[i] = new SelectItem(tpo.getId().toString(), tpo.getDescripcion());
		}

		listaArchivos = new ArrayList();

		Long idModalidad_ = (Long) super.sesion.getAttribute("idMovilidad");
		if (idModalidad_ != null) {
			infoModalidad(idModalidad_);
		}

		cargarTiposDocumentos();

		calcularFechaMinimaInicio();
		cargarCaracterEvento();

		actMovi = new ActividadMovilidad();
		definirInicio4049();
		
	}
	
	private void cargarSedeSolicitud() {
		System.err.println("Entramos a cargar sede solicitud");
		List listaSede = servicioGeneral.obtenerObjetos("from Sede where id != 0 order by nombre asc");
		if (listaSede != null && listaSede.size() > 0) {
			sedeSolicitudItem = new SelectItem[listaSede.size()];
			for (int i = 0; i < listaSede.size(); i++) {
				Sede sed = (Sede) listaSede.get(i);
				sedeSolicitudItem[i] = new SelectItem(sed.getId().toString(), sed.getNombre());
			}
		}
		sedeSolicitud = "2";
		cargarFacultadSolcitud();
	}
	
	public void cargarFacultadSolcitud() {
		/* and sede.id= '"+sede+"' */
		System.err.println("Entramos a cargar facultad solicitud" + sedeSolicitud);
		String sedeSeleccionada = "";
		if (sedeSolicitud == null) {
			sedeSeleccionada = "2";
		} else {
			sedeSeleccionada = sedeSolicitud;
		}
		if(sedeSeleccionada.equals("2") || sedeSeleccionada.equals("3") || sedeSeleccionada.equals("4") || sedeSeleccionada.equals("5")) {
			List listaFacultad = servicioGeneral
					.obtenerObjetos("from Dependencia d where d.esFacultad = 'Y' and d.sede.id= '" + sedeSeleccionada
							+ "' order by d.nombre asc");
			if (listaFacultad != null && listaFacultad.size() > 0) {
				facultadItem = new SelectItem[listaFacultad.size()];
				for (int i = 0; i < listaFacultad.size(); i++) {
					Dependencia dep = (Dependencia) listaFacultad.get(i);
					facultadItem[i] = new SelectItem(dep.getId().toString(), dep.getNombre());
				}
			} else {
				facultadItem = new SelectItem[0];
			}
		}else {
			facultadItem = new SelectItem[0];
			facultadSolicitud = sedeSeleccionada;
		}
	}
	
	public void definirInicio4049() {
		Long idConvocatoria=(Long) sesion.getAttribute("idConvocatoriaActual");
		if (idConvocatoria.equals(898L)) {
			if (mde.getCaracterEvento() == null
					|| (mde.getCaracterEvento() != null && !mde.getCaracterEvento().equals("7"))) {
				tiempoValidacionMovilidad = 30;
			} else {
				tiempoValidacionMovilidad = 15;
			}
			calcularFechaMinimaInicio();
			mde.setFechainicial(null);
			mde.setFechafinal(null);
			panelMasDatos = false;
			panelMasDatos = false;
			fechaIncorrecta2 = false;
			panelMasDatos2 = false;
		}
	}
	
	public void definirInicioConvNal2022_2024() {
//		Long idConvocatoria=(Long) sesion.getAttribute("idConvocatoriaActual");
//		if (idConvocatoria.equals(1179L)) {
		if (mostrarTipoMovilidad) {
			if (mde.getMovilidadTipo() == null
					|| (mde.getMovilidadTipo() != null && mde.getMovilidadTipo().equals("P"))) {
				tiempoValidacionMovilidad = 30;
			} else if (mde.getMovilidadTipo().equals("V")) {
				tiempoValidacionMovilidad = 30;
			}
			/*if(mde.getId() == null) {
				//mde.setActividades(null);
				mde.getActividades().clear();
			}else {
				if (mde.getActividades() != null) {
					for (ActividadMovilidadVE act : mde.getActividades()) {
						servicioGeneral.eliminarObjeto(act);
					}
					mde.getActividades().clear();
				}				
			}*/
			
			calcularFechaMinimaInicio();
			mde.setFechainicial(null);
			mde.setFechafinal(null);
			panelMasDatos = false;
			panelMasDatos = false;
			fechaIncorrecta2 = false;
			panelMasDatos2 = false;
		}		
	}

	public void cargarCaracterEvento() {
		String hql = "select det from Dominio dom, DominioDetalle det where det.identificador.id = dom.id and dom.tipo = 'CONV_MOV_CARACTER_EVENTO' and det.identificador.tipo in ("
				+ parCaracterEvento + ")";
		List<DominioDetalle> listaCaracter = servicioGeneral.obtenerObjetos(DominioDetalle.class, hql);
		caracterEventoItem = crearListaItems(listaCaracter);
	}

	public void cargarSubModalidad() {
		if (idSubmodalidadConv != null) {
			if (idSubmodalidadConv.equals("MOV_ESTAN")) {
				esEstanciaInvestigacionDocente = true;
				esMovMegaproyectosConcursos = false;
				esMovPonencias = false;
			} else {
				if (idSubmodalidadConv.equals("MOV_MP_CON")) {
					esMovMegaproyectosConcursos = true;
					esEstanciaInvestigacionDocente = false;
					esMovPonencias = false;
				} else {
					if (idSubmodalidadConv.equals("MOV_PONEN")) {
						esMovPonencias = true;
						esEstanciaInvestigacionDocente = false;
						esMovMegaproyectosConcursos = false;
					}
				}
			}
		}
	}

	public void adicionarActividad() {
		bErrorActividades = false;
		try {

			actMovi.setbErrorDescripcion(false);
			actMovi.setbErrorDuracion(false);
			actMovi.setbErrorFecha(false);
			if (actMovi.getDescripcion().equals("")) {
				actMovi.setbErrorDescripcion(true);
				actMovi.setErrorDescripcion("La descripción es obligatoria");
				bErrorActividades = true;
			} else {
				actMovi.setDescripcion(cortarCadena(actMovi.getDescripcion(), 200));
			}
			if (actMovi.getDuracion() == null || actMovi.getDuracion().intValue() == 0) {
				actMovi.setbErrorDuracion(true);
				actMovi.setErrorDuracion("La duración es obligatoria");
				bErrorActividades = true;
			}
			if (actMovi.getFecha() == null) {
				actMovi.setbErrorFecha(true);
				actMovi.setErrorFecha("La fecha es obligatoria");
				bErrorActividades = true;
			} else {
				if (actMovi.getFecha().before(this.mde.getFechainicial())) {
					bErrorActividades = true;
					actMovi.setbErrorFecha(true);

					actMovi.setErrorFecha("La fecha debe ser posterior a la fecha de inicio del viaje");
				} else if (actMovi.getFecha().after(this.mde.getFechafinal())) {
					bErrorActividades = true;
					actMovi.setbErrorFecha(true);
					actMovi.setErrorFecha("La fecha debe ser anterior a la fecha de fin del viaje");
				}
			}

			if (!bErrorActividades) {
				mde.adicionarActividad(actMovi);
				actMovi = new ActividadMovilidad();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void eliminarActividad() {
		mde.borrarActividad(actividadMovilidadSeleccionada);
	}

	public void eliminarPonencia() {
		mde.borrarPonencias(movilidadPonenciaSeleccionada);
		if (getListaPonencias().size() == 0) {
			mostrarDatosEventoAux = false;
			panelMasDatos2 = false;
			panelMasDatos = false;
		}
	}

	public void revisarInfoConvExt() {

		if (mde.getInfoConvocatoriaExterna() != null && mde.getInfoConvocatoriaExterna().equals("SI")) {
			siConvExt = true;
		} else {
			siConvExt = false;
		}

	}

	public void revisarPais() {

		if (idPais != null && !idPais.equals("") && idPais.equals("CO")) {
			siColombia = true;
		} else {
			siColombia = false;
		}

	}

	public void cambiarDepartamento() {
		// VINCULACION DE LOS DEPARTAMENTOS CONTENIDOS EN LA BASE DE DATOS CON
		// EL COMPONENTE
		// WEB QUE PERMITE LA ESCOGENCIA DE UN DEPARTAMENTO Y LA RECARGA DE LAS
		// CIUDADES
		departamentoActual = buscarDepartamento((departamentoActual.getId()));
		listaCiudades = servicioGeneral.obtenerUbicacion("Ciudad", "", "departamento", departamentoActual,false);
		ciudadItem = new SelectItem[listaCiudades.size()];
		for (int i = 0; i < listaCiudades.size(); i++) {
			Ciudad ci = (Ciudad) listaCiudades.get(i);
			ciudadItem[i] = new SelectItem(ci.getId(), ci.getNombre());
			ci = null;
		}
		ciudadActual = (Ciudad) listaCiudades.get(0);
	}

	// DEFINICION DE FUNCIONES MISCELANEAS
	private Departamento buscarDepartamento(String id) {
		// BUSCA UN DEPARTAMENTO DE ACUERDO A SU ID
		Departamento d = new Departamento();
		int i = 0;
		while (i < listaDepartamentos.size()) {
			d = (Departamento) listaDepartamentos.get(i);
			if (id.equals(d.getId()))
				break;
			i = i + 1;
		}
		return d;
	}

	private void obtenerListaDepartamentos() {
		// VINCULACION DE LOS DEPARTAMENTOS CONTENIDOS EN LA BASE DE DATOS CON
		// EL COMPONENTE
		// WEB QUE PERMITE LA ESCOGENCIA DE UN DEPARTAMENTO
		listaDepartamentos = servicioGeneral.obtenerUbicacion("Departamento", "", "", null,false);
		departamentoItem = new SelectItem[listaDepartamentos.size()];
		for (int i = 0; i < listaDepartamentos.size(); i++) {
			Departamento dep = (Departamento) listaDepartamentos.get(i);
			departamentoItem[i] = new SelectItem(dep.getId(), dep.getNombre());
			dep = null;
		}
		departamentoActual = (Departamento) listaDepartamentos.get(0);
	}

	private void obtenerListaCiudades() {
		listaCiudades = servicioGeneral.obtenerUbicacion("Ciudad", "", "departamento",
				(Departamento) listaDepartamentos.get(0),false);
		ciudadItem = new SelectItem[listaCiudades.size()];
		for (int i = 0; i < listaCiudades.size(); i++) {
			Ciudad ci = (Ciudad) listaCiudades.get(i);
			ciudadItem[i] = new SelectItem(ci.getId(), ci.getNombre());
			ci = null;
		}
		ciudadActual = (Ciudad) listaCiudades.get(0);
	}

	private void cargarTiposDocumentos() {

		if (sesion.getAttribute("idConvocatoriaActual") != null) {
			Long idConvocatoria = (Long) sesion.getAttribute("idConvocatoriaActual");

			List listConvocatorias = servicioGeneral
					.obtenerObjetos("select e from Convocatoria e where e.id = " + idConvocatoria);
			if (listConvocatorias.size() > 0 && listConvocatorias != null) {
				Convocatoria convocatoriaActual = (Convocatoria) listConvocatorias.get(0);
				restriccionArchivosConv = convocatoriaActual.getRequisitosConvTexto() != null
						? convocatoriaActual.getRequisitosConvTexto() : "";
			}
		}

		listaArchivosObligatorios = new ArrayList();
		if (esEstanciaInvestigacionDocente) {
			listaArchivosObligatorios = servicioGeneral.obtenerListaArchivosMovilidad("CF_705E");
		} else {
			listaArchivosObligatorios = servicioGeneral.obtenerListaArchivosMovilidad(restriccionArchivosConv);
		}

		// listaArchivosObligatorios =
		// servicioGeneral.obtenerListaArchivosMovilidad("MOV2_IN");
		if (listaArchivosObligatorios != null && listaArchivosObligatorios.size() > 0) {
			tipoDocumentoSelItem = new SelectItem[listaArchivosObligatorios.size() + 1];
			tipoDocumentoSelItem[0] = new SelectItem("", "Seleccione un tipo de documento");
			for (int i = 0; i < listaArchivosObligatorios.size(); i++) {
				MovilidadArchivo mva = (MovilidadArchivo) listaArchivosObligatorios.get(i);
				tipoDocumentoSelItem[i + 1] = new SelectItem(mva.getTipoArchivo().getId().toString(),
						mva.getTipoArchivo().getNombre());
			}
		}
	}

	public void guardarArchivoMovDE(FileUploadEvent event) {
		archivoObligatorio = event.getFile();
		if (tipoDocumentoSel == null || tipoDocumentoSel.equals("")) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar un tipo de archivo", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		for (ArchivoMovilidadDE file : getListaArchivosObligatoriosSel()) {
			if (getTipoDocumentoSel()!=null && file.getTipoArchivo()!=null && file.getTipoArchivo().getId().toString().equals(getTipoDocumentoSel())) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Ya existe un documento adjunto bajo el mismo tipo.", "");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return;
			}
		}
		ArchivoMovilidadDE archivoMovilidad = new ArchivoMovilidadDE();
		archivoMovilidad = insertarArchivoMovilidadDEGenerico(1, archivoObligatorio, tipoDocumentoSel);
		mde.adicionarArchivo(archivoMovilidad);

	}

	public void descargarArchivoMovDE() {
		ArchivoMovilidadDE archivo = documentoSeleccionado;
		descargarArchivoMovilidadDEGenerico(archivo.getId());
	}

	public void eliminarArchivoObligatorio() {
		ArchivoMovilidadDE amv = archivoMovilidadDESeleccionado;
		mde.borrarArchivo(archivoMovilidadDESeleccionado);
		//
		// List listaTipoArchivo;
		// listaTipoArchivo = new ArrayList();
		// listaTipoArchivo = servicioGeneral
		// .obtenerListaObjetos("TipoArchivoMovilidad where id ='" +
		// amv.getTipoArchivo().getId() + "'");
		// TipoArchivoMovilidad tipoArchivo = (TipoArchivoMovilidad)
		// listaTipoArchivo.get(0);
		//
		// List listaMovilidadArchivo;
		// listaMovilidadArchivo = new ArrayList();
		// listaMovilidadArchivo =
		// servicioGeneral.obtenerListaArchivosMovilidad(restriccionArchivosConv,
		// amv.getTipoArchivo().getId());

	}

	public void adicionarPonencia() {
		mde.setEvento(cortarCadena(nombreEvento, 2000));
		mde.setTitulo(cortarCadena(tituloPonencia, 2000));
		if (validarTituloYEvento(mde)) {
			List listaTipoMovilidad = new ArrayList();
			listaTipoMovilidad = servicioGeneral.obtenerListaObjetos("TipoMovilidad where id ='B1'");
			TipoMovilidad tipoMovilidad = (TipoMovilidad) listaTipoMovilidad.get(0);

			List listaPonencia = new ArrayList();
			listaPonencia = servicioGeneral.obtenerListaObjetos("TipoPonencia where id = '" + idTipoPonencia + "'");
			TipoPonencia tipoPonencia = (TipoPonencia) listaPonencia.get(0);

			MovilidadPonencia movilidadPonencia = new MovilidadPonencia();
			movilidadPonencia.setEvento(cortarCadena(nombreEvento, 2000));
			movilidadPonencia.setTitulo(cortarCadena(tituloPonencia, 2000));
			movilidadPonencia.setTipoMovilidad(tipoMovilidad);
			movilidadPonencia.setPonencia(tipoPonencia);
			movilidadPonencia.setMovilidad(mde);

			if (tipoPonencia.getId().toString().equals("3")) {
				movilidadPonencia.setOtra(otroTipoPonencia);
			}

			mde.adicionarPonencias(movilidadPonencia);
			mde.setSubModalidadConvocatoria(tipoPonencia.getId().toString());
			mostrarDatosEventoAux = true;
		}
	}

	private void infoModalidad(Long idModalidad_) {
		List listaMovilidadConsulta;
		listaMovilidadConsulta = new ArrayList();

		listaMovilidadConsulta = servicioGeneral
				.obtenerListaObjetos("MovilidadDocentesExterior where id = '" + idModalidad_ + "'");
		if (listaMovilidadConsulta != null && listaMovilidadConsulta.size() > 0) {
			mde = (MovilidadDocentesExterior) listaMovilidadConsulta.get(0);
		}

		tipoDocumento.setId(mde.getTipoDocumentoPersona().toString());
		// documento=mde.getIdinvestigador();

	}

	public void activarFinanciacion(ValueChangeEvent event) {
		financiacionActual = event.getNewValue().toString();

		if (financiacionActual.equals("SI")) {
			bFinanciacion = true;
		} else {
			bFinanciacion = false;
		}
	}

	public void activarOtrotipoPonencia() {
		tipoPonenciaActual = idTipoPonencia;

		if (tipoPonenciaActual.equals("3")) {
			bOtroTipoPonencia = true;
		} else {
			bOtroTipoPonencia = false;
		}
	}

	public void verArchivo() {

		ArchivoMovilidadDE ain = (ArchivoMovilidadDE) tablaArchivos.getRowData();
		FacesContext ctx = FacesContext.getCurrentInstance();

		if (ain != null && ain.getArchivo() != null) {

			try {
				if (!ctx.getResponseComplete()) {
					HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
					response.setContentType("text/plain");
					response.setHeader("Content-Disposition", "attachment;filename=\"" + ain.getNombre() + "\"");
					ServletOutputStream out = response.getOutputStream();
					out.write(ain.getBytes());
					out.flush();
					ctx.responseComplete();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void eliminarArchivo() {
		listaArchivos.remove(tablaArchivos.getRowIndex());

	}

	public void buscarPersona() throws SQLException {
		noExiste1 = "";
		reiniciarVariables();
		IdPersona id = new IdPersona();
		id.setDocumento(documento);
		id.setTipoDocumento(tipoDocumento.getId());
		persona = servicioPersona.obtenerPersona(id);

		// existeMovilidad

		String sSql = "";
		int nExiste = 0;
		Date fechaActual = new Date();

		SimpleDateFormat spd = new SimpleDateFormat("dd");
		SimpleDateFormat spm = new SimpleDateFormat("MM");
		SimpleDateFormat spy = new SimpleDateFormat("yyyy");

		Long anoAct = Long.parseLong(spy.format(fechaActual));
		Long mesAct = Long.parseLong(spm.format(fechaActual));

		if (mesAct == 12) {
			anoAct = anoAct + 1;
		}

		Long anoAnt = anoAct - 1;
		boolean bandera = false;
		if (sesion.getAttribute("idConvocatoriaActual") != null) {
			Long idConvocatoria = (Long) sesion.getAttribute("idConvocatoriaActual");
			if (idConvocatoria != null) {
				sSql = " SELECT COUNT(1) " + " FROM " + " HER_MOVILIDAD_DOCENTES_EVENTOS " + " WHERE "
						+ " MOV_ID_PER ='" + documento + "'" + " AND ("
						+ "(MOV_APROB = 'SI' AND MOV_REALIZACION_MOVILIDAD IS NULL)" + " OR "
						+ "(MOV_APROB = 'SI' AND MOV_REALIZACION_MOVILIDAD = 'SI')" + ") "
						+ "AND MOV_FEC_INI_EV BETWEEN TO_DATE('01/01/" + anoAct + "', 'MM/DD/YYYY') AND TO_DATE('12/31/"
						+ anoAct + "', 'MM/DD/YYYY') and CON_ID = '" + idConvocatoria + "'";
				bandera = true;
			}
		}

		if (!bandera) {
			sSql = " SELECT COUNT(1) " + " FROM " + " HER_MOVILIDAD_DOCENTES_EVENTOS " + " WHERE " + " MOV_ID_PER ='"
					+ documento + "'" + " AND MOV_APROB = 'SI' " + "AND MOV_FEC_INI_EV BETWEEN TO_DATE('01/01/" + anoAct
					+ "', 'MM/DD/YYYY') AND TO_DATE('12/31/" + anoAct + "', 'MM/DD/YYYY')";
		}

		nExiste = servicioGeneral.existeMovilidad(sSql);
		//nExiste = 0;
		if (nExiste > 1) {
			noExiste = "El docente ya tiene aprobadas " + nExiste + " movilidades para esta convocatoria.";
			mostrarPanelNoExiste();
		} else {

			if (persona != null) {
				idCiudad = persona.getCiudadDomicilio() == null ? null : persona.getCiudadDomicilio().getId();
				if (persona instanceof Investigador) {
					if (persona instanceof InvestigadorInterno) {
						InvestigadorInterno investigadorInterno = (InvestigadorInterno) persona;
						dependencia = servicioDependencia.obtenerDependencia(investigadorInterno.getId());
						String nombre1, nombre2, apellido1, apellido2;
						listaGrupos = new Vector();
						cargarGrupos(investigadorInterno, documento);
						// Ing. Wilver Alexander Martínez Martínez (wam²)
						// Cambio para aceptar docentes de medio tiempo
						if (investigadorInterno.getTipoDedicacion() != null && (investigadorInterno.getTipoDedicacion()
								.getId().equals(Investigador.EXCLUSIVA)
								|| investigadorInterno.getTipoDedicacion().getId().equals(Investigador.TIEMPOCOMPLETO)
								|| investigadorInterno.getTipoDedicacion().getId().equals(Investigador.MEDIOTIEMPO)
								|| investigadorInterno.getTipoDedicacion().getId().equals(Investigador.CATEDRA_0_1)
								|| investigadorInterno.getTipoDedicacion().getId().equals(Investigador.CATEDRA_0_2)
								|| investigadorInterno.getTipoDedicacion().getId().equals(Investigador.CATEDRA_0_3)
								|| investigadorInterno.getTipoDedicacion().getId().equals(Investigador.CATEDRA_0_4)
								|| investigadorInterno.getTipoDedicacion().getId().equals(Investigador.CATEDRA_0_5)
								|| investigadorInterno.getTipoDedicacion().getId().equals(Investigador.CATEDRA_0_6)
								|| investigadorInterno.getTipoDedicacion().getId().equals(Investigador.CATEDRA_0_7)
								|| investigadorInterno.getTipoDedicacion().getId()
										.equals(Investigador.TIEMPO_COMPLETO_ADMINISTRATIVO))) {
							if (investigadorInterno.getNombre1() != null) {
								nombre1 = investigadorInterno.getNombre1();
							} else {
								nombre1 = "";
							}
							if (investigadorInterno.getNombre2() != null) {
								nombre2 = investigadorInterno.getNombre2();
							} else {
								nombre2 = "";
							}
							if (investigadorInterno.getApellido1() != null) {
								apellido1 = investigadorInterno.getApellido1();
							} else {
								apellido1 = "";
							}
							if (investigadorInterno.getApellido2() != null) {
								apellido2 = investigadorInterno.getApellido2();
							} else {
								apellido2 = "";
							}
							nombreDocente = nombre1 + " " + nombre2 + " " + apellido1 + " " + apellido2;
							documentoDocente = investigadorInterno.getId().getDocumento();
							tipoDocDocente = investigadorInterno.getId().getTipoDocumento();
							if (dependencia.getFacultad() != null) {
								facultadDocente = dependencia.getFacultad().getNombre();
								departamentoDocente = dependencia.getNombre();
								mostrarDatosPersona();
							} else {
								noExiste = "La dependencia del investigador no tiene una facultad asociada";
								mostrarPanelNoExiste();
							}
						} else {
							noExiste = "El investigador debe ser de dedicación exclusiva o tiempo completo de la Universidad Nacional de Colombia";
							mostrarPanelNoExiste();
						}
					} else {
						noExiste = "El documento ingresado no corresponde a un investigador";
						mostrarPanelNoExiste();
					}
				} else {
					persona = new Persona();
					IdPersona idP = new IdPersona();
					idP.setDocumento(documento);
					idP.setTipoDocumento(tipoDocumento.getId());
					persona.setId(idP);
					noExiste = "El documento ingresado no corresponde a un investigador";
					mostrarPanelNoExiste();
				}
			} else {
				persona = new Persona();
				IdPersona idP = new IdPersona();
				idP.setDocumento(documento);
				idP.setTipoDocumento(tipoDocumento.getId());
				persona.setId(idP);
				noExiste = "El documento ingresado no existe";
				mostrarPanelNoExiste();
			}
		}

		idTipoPonencia = "4";
		activarOtrotipoPonencia();
	}

	private void cargarGrupos(InvestigadorInterno investigadorInterno, String doc) {
		SelectItem stemp = new SelectItem("0", "Seleccione un grupo");
		listaGrupos.add(stemp);
		try {

			List listGruposInvestigador = servicioPersona.obtenerGruposInvestigadorMovilidades(investigadorInterno);

			if (!listGruposInvestigador.isEmpty()) {
				Iterator itgrupos = listGruposInvestigador.iterator();
				while (itgrupos.hasNext()) {
					InvestigadorGrupo invG = (InvestigadorGrupo) itgrupos.next();
					Grupo g = (Grupo) invG.getGrupo();

					SelectItem s = new SelectItem(String.valueOf(g.getId()), g.getNombre());
					listaGrupos.add(s);

				}
			} else {
				System.out.println("ManejadorGruposInvestigador:ManejadorGruposInvestigador:Lista de Grupos Vacia");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cambiarInvestigador(ValueChangeEvent event) {
		// VINCULACION DE LOS DEPARTAMENTOS CONTENIDOS EN LA BASE DE DATOS CON
		// EL COMPONENTE
		// WEB QUE PERMITE LA ESCOGENCIA DE UN DEPARTAMENTO Y LA RECARGA DE LAS
		// CIUDADES
		String nombrel1, nombrel2, apellidol1, apellidol2;
		if (investigadorActual.getNombre1() != null) {
			nombrel1 = investigadorActual.getNombre1();
		} else {
			nombrel1 = "";
		}
		if (investigadorActual.getNombre2() != null) {
			nombrel2 = investigadorActual.getNombre2();
		} else {
			nombrel2 = "";
		}
		if (investigadorActual.getApellido1() != null) {
			apellidol1 = investigadorActual.getApellido1();
		} else {
			apellidol1 = "";
		}
		if (investigadorActual.getApellido2() != null) {
			apellidol2 = investigadorActual.getApellido2();
		} else {
			apellidol2 = "";
		}
		nombreDocente = nombrel1 + " " + nombrel2 + " " + apellidol1 + " " + apellidol2;
		documentoDocente = investigadorActual.getId().getDocumento();

		facultadDocente = "";
		departamentoDocente = "";
		mostrarDatosEvento = true;
	}

	public void cargarDatosInvestigadores() {
		listaIntegrantesGrupo = new ArrayList();
		List integrantesGrupo = this.servicioGrupo.obtenerIntegrantesGrupo(grupoActual.getId());
		for (int i = 0; i < integrantesGrupo.size(); i++) {
			InvestigadorGrupo investigadorGrupo = (InvestigadorGrupo) integrantesGrupo.get(i);
			if (investigadorGrupo.getTipo().equals(InvestigadorGrupo.LIDER))
				responsableGrupo = investigadorGrupo.getInvestigador();
			else
				listaIntegrantesGrupo.add(investigadorGrupo.getInvestigador());
		}
	}

	/*
	 * DML Editor DEPENDENCIA
	 */
	public void cargarDependencias() {
		Dependencia sede = new Dependencia();
		sedeSeleccionada = "";
		nombreSedeSeleccionada = "";
		facultadSeleccionada = "";
		nombreFacultadSeleccionada = "";
		departamentoSeleccionado = "";
		nombreDepartamentoSeleccionado = "";
		depsItem = new SelectItem[listaDependencias.size() + 1];
		depsItem[0] = new SelectItem("", "");
		for (int i = 1; i < listaDependencias.size() + 1; i++) {
			sede = null;
			sede = (Dependencia) listaDependencias.get(i - 1);
			String nombre = sede.getNombre().length() > 55 ? sede.getNombre().substring(0, 55) + "..."
					: sede.getNombre();
			depsItem[i] = new SelectItem(sede.getId(), nombre);
		}
	}

	public void guardar() {
		if (this.estado.equals("")) {
			mensajeError("Por favor confirme si desea enviar la solicitud de movilidad para revisión.");
			panelMasDatos2 = true;
		} else {
			if (this.estado.equals("P")) {
				mde.setEstado("P");
				guardarPropuesto();
			} else {
				mde.setEstado("I");
				guardarIngresando();
			}

		}
	}

	public void guardarIngresando() {
		Calendar actual = Calendar.getInstance();
		Date date = actual.getTime();
		mde.setFechasolicitud(date);
		mde.setEvento(nombreEvento);
		mde.setTitulo(cortarCadena(tituloPonencia, 2000));
		try {
			if (mde.getInscripcionevento() != null) {
				mde.setCostoevento(Long.parseLong(mde.getInscripcionevento()));
			} else {
				mde.setCostoevento(0L);
			}

		} catch (Exception e) {
			mde.setCostoevento(0L);
			e.getMessage();
		}

		mde.setAportefacultad(aporteFacultad);
		mde.setAceptacion(aceptacionFacultad);
		mde.setSubModalidadConvocatoria(idSubmodalidadConv);

		if (esConvocatoriaFacultad) {
			mde.setMovilidadConvocatoriaFacultad("S");
		} else {
			mde.setMovilidadConvocatoriaFacultad("N");
		}

		List listaGrupo = new ArrayList();
		listaGrupo = servicioGeneral.obtenerListaObjetos("Grupo where id ='" + idgrupo + "'");
		if (!listaGrupo.isEmpty()) {
			Grupo grupo = new Grupo();
			grupo = (Grupo) listaGrupo.get(0);
			mde.setGrupo(grupo);
		}

		List listaPonencia = new ArrayList();
		listaPonencia = servicioGeneral.obtenerListaObjetos("TipoPonencia where id ='" + idTipoPonencia + "'");
		TipoPonencia tpn = new TipoPonencia();
		tpn = (TipoPonencia) listaPonencia.get(0);

		mde.setPonencia(tpn);

		List listaTipoMovilidad = new ArrayList();
		listaTipoMovilidad = servicioGeneral.obtenerListaObjetos("TipoMovilidad where id ='B1'");
		TipoMovilidad tm = new TipoMovilidad();

		tm = (TipoMovilidad) listaTipoMovilidad.get(0);

		mde.setTipoMovilidad(tm);

		Persona personaAux = new Persona();
		IdPersona idp = new IdPersona();

		idp.setDocumento(documentoDocente);
		idp.setTipoDocumento(tipoDocDocente);
		personaAux = servicioPersona.obtenerPersona(idp);

		mde.setPersonaInv(personaAux);

		List listaPais = new ArrayList();
		listaPais = servicioGeneral.obtenerListaObjetos("Pais where id ='" + idPais + "'");
		Pais pais = new Pais();
		pais = (Pais) listaPais.get(0);

		mde.setPais(pais);

		if (siColombia) {
			mde.setCiudad(ciudadEvento);
		}

		if (sesion.getAttribute("idConvocatoriaActual") != null) {
			Long idConvocatoria = (Long) sesion.getAttribute("idConvocatoriaActual");
			mde.setConvocatoriaId(idConvocatoria.toString());
		}
		
		Dependencia dependencia = new Dependencia();
		
		if(mde.getMovilidadDirigidaOtraDependencia() != null && mde.getMovilidadDirigidaOtraDependencia().equals("S")) {
			dependencia = servicioDependencia.obtenerDependencia(facultadSolicitud);
		}else {
			if (personaAux instanceof Investigador) {
				if (personaAux instanceof InvestigadorInterno) {
					personaAux = servicioPersona.obtenerInvestigadorInternoCompleto(personaAux.getId());
					InvestigadorInterno investigadorInterno = (InvestigadorInterno) personaAux;
					dependencia = servicioDependencia.obtenerDependencia(investigadorInterno.getId());
				}
			}
		}


		mde.setDependencia(dependencia);

		if (getListaArchivosObligatoriosSel() != null && getListaArchivosObligatoriosSel().size() > 0) {
			for (int i = 0; i < getListaArchivosObligatoriosSel().size(); i++) {
				getListaArchivosObligatoriosSel().get(i).setMovilidad(mde);
			}
		}

		if (esConvocatoriaFacultad) {
			mde.setMovilidadConvocatoriaFacultad("S");
		} else {
			mde.setMovilidadConvocatoriaFacultad("N");
		}

		servicioGeneral.guardarObjeto(mde);

		limpiar();

		mostrarPanelNoExiste();
		bImprimirReporte = true;
		noExiste1 = "Solicitud de movilidad guardada correctamente con el código " + mde.getId() + ".";
	}

	public void guardarPropuesto() {

		Calendar actual = Calendar.getInstance();
		Date date = actual.getTime();

		mde.setFechasolicitud(date);
		mde.setEvento(cortarCadena(nombreEvento, 2000));
		mde.setTitulo(cortarCadena(tituloPonencia, 2000));
		mde.setOtroTipoPonencia(otroTipoPonencia);
		try {
			if (mde.getInscripcionevento() != null) {
				mde.setCostoevento(Long.parseLong(mde.getInscripcionevento()));
			} else {
				mde.setCostoevento(0L);
			}

		} catch (Exception e) {
			mde.setCostoevento(0L);
			e.getMessage();
		}
		mde.setAportefacultad(aporteFacultad);
		mde.setAceptacion(aceptacionFacultad);
		// mde.setSubModalidadConvocatoria(idSubmodalidadConv);

		if (validarGrupo) {
			List listaGrupo = new ArrayList();
			listaGrupo = servicioGeneral.obtenerListaObjetos("Grupo where id ='" + idgrupo + "'");
			Grupo grupo = new Grupo();
			grupo = (Grupo) listaGrupo.get(0);

			mde.setGrupo(grupo);
		}

		List listaPonencia = new ArrayList();
		listaPonencia = servicioGeneral.obtenerListaObjetos("TipoPonencia where id ='" + idTipoPonencia + "'");
		TipoPonencia tpn = new TipoPonencia();
		tpn = (TipoPonencia) listaPonencia.get(0);

		mde.setPonencia(tpn);

		if (esConvocatoriaFacultad) {
			mde.setMovilidadConvocatoriaFacultad("S");
		} else {
			mde.setMovilidadConvocatoriaFacultad("N");
		}

		List listaTipoMovilidad = new ArrayList();
		listaTipoMovilidad = servicioGeneral.obtenerListaObjetos("TipoMovilidad where id ='B1'");
		TipoMovilidad tm = new TipoMovilidad();

		tm = (TipoMovilidad) listaTipoMovilidad.get(0);

		mde.setTipoMovilidad(tm);
		boolean opcion1 = false;
		boolean opcion2 = false;

		List listaPais = new ArrayList();
		listaPais = servicioGeneral.obtenerListaObjetos("Pais where id ='" + idPais + "'");
		Pais pais = new Pais();
		pais = (Pais) listaPais.get(0);

		mde.setPais(pais);

		if (siColombia) {
			mde.setCiudad(ciudadEvento);
		}

		if (validar(mde)) {

			Persona personaAux = new Persona();
			IdPersona idp = new IdPersona();

			idp.setDocumento(documentoDocente);
			idp.setTipoDocumento(tipoDocDocente);
			personaAux = servicioPersona.obtenerPersona(idp);

			mde.setPersonaInv(personaAux);

			if (sesion.getAttribute("idConvocatoriaActual") != null) {
				Long idConvocatoria = (Long) sesion.getAttribute("idConvocatoriaActual");
				mde.setConvocatoriaId(idConvocatoria.toString());
			}

			Dependencia dependencia;
			dependencia = new Dependencia();

			/*if (personaAux instanceof Investigador) {
				if (personaAux instanceof InvestigadorInterno) {
					personaAux = servicioPersona.obtenerInvestigadorInternoCompleto(personaAux.getId());
					InvestigadorInterno investigadorInterno = (InvestigadorInterno) personaAux;
					dependencia = servicioDependencia.obtenerDependencia(investigadorInterno.getId());
				}
			}*/
			
			
			if(mde.getMovilidadDirigidaOtraDependencia() != null && mde.getMovilidadDirigidaOtraDependencia().equals("S")) {
				dependencia = servicioDependencia.obtenerDependencia(facultadSolicitud);
			}else {
				if (personaAux instanceof Investigador) {
					if (personaAux instanceof InvestigadorInterno) {
						personaAux = servicioPersona.obtenerInvestigadorInternoCompleto(personaAux.getId());
						InvestigadorInterno investigadorInterno = (InvestigadorInterno) personaAux;
						dependencia = servicioDependencia.obtenerDependencia(investigadorInterno.getId());
					}
				}
			}

			mde.setDependencia(dependencia);

			List listaParametro;
			listaParametro = new ArrayList();

			listaParametro = this.servicioGeneral
					.obtenerObjetos("FROM Parametro WHERE nombre = '" + dependencia.getSede().getId() + "'");

			if (listaParametro == null || listaParametro.size() == 0) {
				mde.setAceptacion("SI");
			}

			if (getListaArchivosObligatoriosSel() != null && getListaArchivosObligatoriosSel().size() > 0) {
				for (int i = 0; i < getListaArchivosObligatoriosSel().size(); i++) {
					getListaArchivosObligatoriosSel().get(i).setMovilidad(mde);
				}
			}
			
			InvestigadorInterno ii=servicioPersona.obtenerInvestigadorInternoCompleto(personaAux.getId());
			if (!Sede.SEDES_ANDINAS.contains(ii.getDependencia().getSede().getId().toString())
					&& !ii.getDependencia().getSede().getId().equals(1L)) {
				mde.setAceptacion("SI");
			}

			servicioGeneral.guardarObjeto(mde);
			
			crearHistoricoEstadoMovilidadInvestigador(mde,"Movilidad enviada por parte del docente para revisión");

			personaActual = (Persona) sesion.getAttribute("persona");

			correoActual = cargarPlantilla(65);// 42
			editarCorreo(personaAux, mde);
			Correo correo = new Correo();
			correo.setOrigen(Correo.CORREO_HERMES);
			String dirCorreo = personaAux.getEmail();
			correo.adicionarDireccion(dirCorreo);
			correo.adicionarCopiaOculta(new String(personaAux.getEmail()));
			correo.setAsunto(correoActual.getAsunto());
			correo.setCuerpo(cuerpoCorreo);
			opcion1 = servicioCorreo.enviarCorreo(correo);

			List listaCorreoEncargado = new ArrayList();
			List listaParametroAux;
			listaParametroAux = new ArrayList();

			Dependencia dependenciaAux;
			dependenciaAux = new Dependencia();
			Persona personaEnvio = new Persona();

			personaEnvio = servicioPersona.obtenerInvestigadorInternoCompleto(mde.getPersonaInv().getId());

			InvestigadorInterno investigadorInterno = (InvestigadorInterno) personaEnvio;
			dependenciaAux = servicioDependencia.obtenerDependencia(investigadorInterno.getId());

			if (listaParametro == null || listaParametro.size() == 0) {
				listaParametroAux = this.servicioGeneral
						.obtenerObjetos("FROM Parametro WHERE nombre = '" + dependencia.getSede().getId() + "'");
				correoActual = cargarPlantilla(86);

				listaCorreoEncargado = this.servicioGeneral
						.obtenerObjetos("FROM Parametro WHERE nombre = 'R_MOVILIDAD'    AND DESCRIPCION= '"
								+ dependenciaAux.getSede().getId() + "'");
				if (listaCorreoEncargado != null && listaCorreoEncargado.size() > 0) {
					Parametro para = (Parametro) listaCorreoEncargado.get(0);
					ii = servicioPersona
							.obtenerInvestigadorInterno(new IdPersona(para.getValor(), para.getProfesion()));
					if (ii != null) {
						listaCorreoEncargado = new ArrayList();
						listaCorreoEncargado.add(ii);
					}
				}

			} else {
				listaParametroAux = this.servicioGeneral
						.obtenerObjetos("FROM Parametro WHERE nombre = '" + dependencia.getSede().getId() + "'");
				correoActual = cargarPlantilla(85);

				/*
				 * listaCorreoEncargado = this.servicioGeneral .obtenerObjetos(
				 * "FROM Parametro WHERE nombre = 'R_MOVILIDAD'    AND DESCRIPCION= '"
				 * + dependenciaAux.getFacultad().getId() + "'");
				 */
				try {
					String consult = "select i from InvestigadorInterno i, " + " PersonaRol pr "
							+ " where i.id.documento= pr.documento " + " and i.id.tipoDocumento= pr.tipoDocumento "
							+ " and pr.nombre = 'MF' and i.dependencia.facultad.id = '"
							+ dependencia.getFacultad().getId() + "' "
							+ " and i.dependencia.facultad.esFacultad = 'Y' and (pr.fechaFinRol IS NULL OR pr.fechaFinRol >= CURRENT_DATE) ";
					listaCorreoEncargado = servicioGeneral.obtenerObjetos(consult);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			String correoEnvio = Correo.CORREO_HERMES_COMUNICACIONES;
			Persona personaActualAux2 = new Persona();

			if (listaCorreoEncargado != null && listaCorreoEncargado.size() > 0) {
				// Parametro paActual = (Parametro) listaCorreoEncargado.get(0);
				int numCoord = listaCorreoEncargado.size();

				for (int i = 0; i < numCoord; i++) {

					InvestigadorInterno paActual = (InvestigadorInterno) listaCorreoEncargado.get(i);
					String numeroDocumento = "0";
					// numeroDocumento = paActual.getValor();
					numeroDocumento = paActual.getId().getDocumento();
					IdPersona id = new IdPersona();
					id.setDocumento(numeroDocumento);
					// id.setTipoDocumento(paActual.getProfesion());
					id.setTipoDocumento(paActual.getId().getTipoDocumento());
					personaActualAux2 = servicioPersona.obtenerPersona(id);

					// System.out.println("personaActualAux2 "+
					// personaActualAux2.getEmail());
					if (personaActualAux2.getEmail() != null && !personaActualAux2.getEmail().equals("")) {

						correoEnvio = personaActualAux2.getEmail();
					} else {
						correoActual = cargarPlantilla(87);
					}

					editarCorreo(personaActualAux2, mde);
					correo = new Correo();
					correo.setOrigen(Correo.CORREO_HERMES);
					dirCorreo = correoEnvio;
					correo.adicionarDireccion(dirCorreo);
					correo.adicionarCopiaOculta(dirCorreo);
					correo.setAsunto(correoActual.getAsunto());
					correo.setCuerpo(cuerpoCorreo);
					opcion2 = servicioCorreo.enviarCorreo(correo);
				}

			} else {
				correoActual = cargarPlantilla(87);
				editarCorreo(personaActualAux2, mde);
				correo = new Correo();
				correo.setOrigen(Correo.CORREO_HERMES);
				dirCorreo = correoEnvio;
				correo.adicionarDireccion(dirCorreo);
				correo.adicionarCopiaOculta(dirCorreo);
				correo.setAsunto(correoActual.getAsunto());
				correo.setCuerpo(cuerpoCorreo);
				opcion2 = servicioCorreo.enviarCorreo(correo);
			}

			limpiar();

			mostrarPanelNoExiste();
			bImprimirReporte = true;
//			if (opcion1 && opcion2) {
//				noExiste1 = "Solicitud de movilidad guardada correctamente con el número " + mde.getId()
//						+ ". Un correo electrónico confirmando su registro se ha enviado a su cuenta de correo electrónico.";
//			} else {
//				noExiste1 = "Ha ocurrido un error al enviar el correo electrónico de confirmación del registro de la movilidad. ";
//			}
			
			noExiste = "Solicitud de movilidad guardada correctamente, con el número " + mde.getId()
			+ ".  Se ha enviado a su correo electrónico un mensaje confirmando el registro. Si lo considera necesario comuníquese con la "
			+ "Coordinación de Investigación de la facultad o sede a la que pertenece para la revisión de la movilidad. ";
			
			/*noExiste = "La movilidad con código" + mde.getId() + "se ha guardado y enviado correctamente al usuario a través del "
					+ "sistema Hermes; sin embargo, no se ha enviado correo, les recomendamos comunicarse con "
					+ "la Coordinación de Investigación de la facultad o sede a la que pertenece para realizar "
					+ "la revisión de la movilidad. No hay necesidad de enviar nuevamente la movilidad";*/
		} else {
			mostrarError = true;
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msgError, msgError);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public String editarCorreo(Persona personaAux, MovilidadDocentesExterior mov) {

		try {
			String coinvNombre = "";

			String correo = correoActual.getCuerpo();

			String investigador = "";

			investigador = personaAux.getNombre1() + " " + personaAux.getApellido1() + " " + personaAux.getApellido2();
			correo = correo.replaceAll("<<INVESTIGADOR>>", investigador);

			correo = correo.replaceAll("<<IDMOVILIDAD>>", mov.getId().toString());
			correo = correo.replaceAll("<<TIPO>>", mov.getTipoMovilidad().getNombre());

			cuerpoCorreo = correo;

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return "";
	}

	public CorreoPlantilla cargarPlantilla(int cod_id) {

		CorreoPlantilla correoActualAux = new CorreoPlantilla();
		// CorreoPlantilla correoActualAux=(CorreoPlantilla)
		CorreoPlantilla a = new CorreoPlantilla();
		// CorreoPlantilla correoActualAux3 = (CorreoPlantilla) servicioGeneral
		// .obtenerObjeto(a, Long.valueOf(String.valueOf(cod_id)));
		List lista = servicioGeneral.obtenerObjetos("select c from CorreoPlantilla c where c.id='" + cod_id + "'");
		if (lista != null && lista.size() > 0) {
			correoActualAux = (CorreoPlantilla) lista.get(0);
		}
		// String
		// correo=correoActual.getCuerpo().replaceAll("<<fecha>>",Fecha.fechaActual());
		return correoActualAux;
	}

	public String imprimirReporte() {
		long movId = mde.getId(); // movilidadGlobal.getId().longValue();
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("id", Long.toString(movId));
		r.setNombreReporte("/movilidad/MovilidadEventos");
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		System.out.println("mirar");
		System.out.println(Navegacion.REPORTE);
		return Navegacion.REPORTE;
	}

	public void cargarVicerrectoria() {
		try {
			String nombreArchivo = "";
			String rutaArchivo = "";

			if (archivoVicerectoria != null) {
				// Se recorta el nombre del archivo para que no tenga la
				// ruta absoluta
				int i = archivoVicerectoria.getFileName().lastIndexOf("\\");
				nombreArchivo = archivoVicerectoria.getFileName().substring(i + 1);

				FacesContext ctx = FacesContext.getCurrentInstance();
				ServletContext sc = (ServletContext) ctx.getExternalContext().getContext();
				rutaArchivo = sc.getRealPath(RUTA_ADJUNTO);

				rutaArchivo += "\\" + nombreArchivo;

				try {
					// servicioCorreo.crearArchivo(rutaArchivo,
					// archivoCuadroResultados.getBytes());
					mde.setBytesAlDiaVicerrectoria(archivoVicerectoria.getContents());
					mde.setNombreVicerrectoria(nombreArchivo);

					nombreVicerrectoria = nombreArchivo;

					// nombreCuadroResultados=nombreArchivo;

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception x) {
			x.printStackTrace();

		}
	}

	public void cargarDireccion() {
		try {
			String nombreArchivo = "";
			String rutaArchivo = "";

			if (archivoDireccion != null) {
				// Se recorta el nombre del archivo para que no tenga la
				// ruta absoluta
				int i = archivoDireccion.getFileName().lastIndexOf("\\");
				nombreArchivo = archivoDireccion.getFileName().substring(i + 1);

				FacesContext ctx = FacesContext.getCurrentInstance();
				ServletContext sc = (ServletContext) ctx.getExternalContext().getContext();
				rutaArchivo = sc.getRealPath(RUTA_ADJUNTO);

				rutaArchivo += "\\" + nombreArchivo;

				try {
					// servicioCorreo.crearArchivo(rutaArchivo,
					// archivoCuadroResultados.getBytes());
					mde.setBytesAlDiaDireccion(archivoDireccion.getContents());
					mde.setNombreDireccion(nombreArchivo);
					// nombreCuadroResultados=nombreArchivo;
					nombreSede = nombreArchivo;

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception x) {
			x.printStackTrace();

		}
	}

	public void cargarFacultad() {
		try {
			String nombreArchivo = "";
			String rutaArchivo = "";

			if (archivoFacultad != null) {
				// Se recorta el nombre del archivo para que no tenga la
				// ruta absoluta
				int i = archivoFacultad.getFileName().lastIndexOf("\\");
				nombreArchivo = archivoFacultad.getFileName().substring(i + 1);

				FacesContext ctx = FacesContext.getCurrentInstance();
				ServletContext sc = (ServletContext) ctx.getExternalContext().getContext();
				rutaArchivo = sc.getRealPath(RUTA_ADJUNTO);

				rutaArchivo += "\\" + nombreArchivo;

				try {
					// servicioCorreo.crearArchivo(rutaArchivo,
					// archivoCuadroResultados.getBytes());
					mde.setBytesAlDiaFacultad(archivoFacultad.getContents());
					mde.setNombreFacultad(nombreArchivo);
					// nombreCuadroResultados=nombreArchivo;

					nombreFacultad = nombreArchivo;

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception x) {
			x.printStackTrace();

		}
	}

	public void cargarDepartamento() {
		try {
			String nombreArchivo = "";
			String rutaArchivo = "";

			if (archivoDepartamento != null) {
				// Se recorta el nombre del archivo para que no tenga la
				// ruta absoluta
				int i = archivoDepartamento.getFileName().lastIndexOf("\\");
				nombreArchivo = archivoDepartamento.getFileName().substring(i + 1);

				FacesContext ctx = FacesContext.getCurrentInstance();
				ServletContext sc = (ServletContext) ctx.getExternalContext().getContext();
				rutaArchivo = sc.getRealPath(RUTA_ADJUNTO);

				rutaArchivo += "\\" + nombreArchivo;

				try {
					// servicioCorreo.crearArchivo(rutaArchivo,
					// archivoCuadroResultados.getBytes());
					mde.setBytesAlDiaDepartamento(archivoDepartamento.getContents());
					mde.setNombreDepartamento(nombreArchivo);

					// nombreCuadroResultados=nombreArchivo;

					nombreDepartamento = nombreArchivo;

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception x) {
			x.printStackTrace();

		}
	}

	private boolean validar(MovilidadDocentesExterior mov) {
		mostrarError = false;
		boolean result = true;
		bErrorProyecto = false;
		bErrorEvento = false;
		bErrorTitulo = false;
		bErrorPais = false;
		bErrorCiudad = false;
		bErrorPonencia = false;
		bErrorResumen = false;
		bErrorResolucion = false;
		bErrorListaPonencia = false;
		bErrorTiquete = false;
		bErrorInscripcion = false;
		bImprimirReporte = false;
		bErrorDocumentos = false;
		bErrorNombreUniversidad = false;
		errorPasaporte = false;

		if (mostrarSubModalidades) {
			if (mov.getSubModalidadConvocatoria().equals("")) {
				errorSubmodalidad = true;
				result = false;
			} else {
				errorSubmodalidad = false;
			}
		}

		if (validarGrupo) {
			if (idgrupo.equals("0")) {
				msgErrorGrupo = "Por favor seleccione un grupo de investigación";
				errorGrupo = true;
				result = false;
			}
		}

		if (!esEstanciaInvestigacionDocente) {
			result = validarTituloYEvento(mov);
			if (mov.getEvento().equals("")) {
				String error = "El nombre del evento es obligatorio \n";
				errores[1] = error;
				bErrorEvento = true;
				result = false;
			}
		}

		revisarPais();
		if (!siColombia && mostrarPasaporte) {
			if (mov.getPasaporteInvestigador() == null || mov.getPasaporteInvestigador().trim().equals("")) {
				msgErrorPasaporte = "El número de pasaporte es obligatorio";
				errorPasaporte = true;
				result = false;
			}
		}

		if (idPais.equals("00")) {
			String error = "El país es obligatorio \n";
			errores[3] = error;
			bErrorPais = true;
			result = false;
		}
		if (mov.getCiudad().equals("")) {
			String error = "La ciudad es obligatoria \n";
			errores[4] = error;
			bErrorCiudad = true;
			result = false;
		}
		if (mov.getNombreUniversidad().trim().equals("")) {
			String error = "La universidad del evento es obligatoria \n";
			errores[14] = error;
			result = false;
			bErrorNombreUniversidad = true;
		}
		if (mov.getPonencia().getId() == 3) {
			if (mov.getOtroTipoPonencia().equals("")) {
				String error = "Otro tipo de Ponencia es obligatorio";
				errores[5] = error;
				bErrorPonencia = true;
				result = false;
			}
		}

		if (!esEstanciaInvestigacionDocente) {
			if (mov.getResumen().equals("")) {
				String error = "El resumen es obligatorio \n";
				errores[6] = error;
				bErrorResumen = true;
				result = false;
			} else if (mov.getResumen().length() > 4000) {
				String error = "El resumen supera los 4000 carácteres \n";
				errores[6] = error;
				bErrorResumen = true;
				result = false;
			}
		}

		if (mov.getCostotiquete() == null) {
			String error = "El costo de tiquete es obligatorio \n";
			errores[8] = error;
			bErrorTiquete = true;
			result = false;
		}

		else if (mov.getCostotiquete().longValue() > 100000000) {
			String error = "El costo del tiquete no puede ser superior a 100.000.000 \n";
			errores[8] = error;
			bErrorTiquete = true;
			result = false;
		}

		if (mov.getInscripcionevento() == null || mov.getInscripcionevento().equals("")) {
			mov.setInscripcionevento("0");
		}

		if (mov.getCostotiquete() == null) {
			mov.setCostotiquete(0L);
		}

		if (mov.getValorViaticos() == null) {
			mov.setValorViaticos(0L);
		}

		if (mostrarSubModalidades || mov.getSubModalidadConvocatoria() != null) {
			if (getValidadcionCostosSubmodalidad() != null) {
				String[] valMov = getValidadcionCostosSubmodalidad().split(",");
				for (int i = 0; i < valMov.length; i++) {
					String[] valSubmov = valMov[i].split("=");
					if (valSubmov[0].equals(mov.getSubModalidadConvocatoria())) {
						restriccionTiquetes = Long.parseLong(valSubmov[1]);
					}
				}
			}
		}

		if (mov.getInscripcionevento() != null && mov.getCostotiquete() != null && mov.getValorViaticos() != null) {
			if (Long.parseLong(mov.getInscripcionevento()) + mov.getCostotiquete().longValue()
					+ mov.getValorViaticos().longValue() > restriccionTiquetes) {
				String error = "El valor máximo que cubre la convocatoria es de $" + restriccionTiquetes + ". \n";
				errores[8] = error;
				bErrorTiquete = true;
				result = false;
			}
		}

		if (mov.getInscripcionevento() != null && mov.getCostotiquete() != null && mov.getValorViaticos() != null) {
			if (Long.parseLong(mov.getInscripcionevento()) + mov.getCostotiquete().longValue()
					+ mov.getValorViaticos().longValue() <= 0) {
				String error = "Por favor registre la información relacionada con el apoyo económico. \n";
				errores[8] = error;
				bErrorTiquete = true;
				result = false;
			}
		}

		if (!esEstanciaInvestigacionDocente) {
			if (getListaPonencias() == null || getListaPonencias().size() == 0) {
				String error = "Debe ingresar al menos un trabajo a presentar. \n";
				errores[10] = error;
				bErrorListaPonencia = true;
				result = false;
			}
		}

		ArrayList<TipoArchivoMovilidad> listaTipoArchObligatorios = new ArrayList<TipoArchivoMovilidad>();
		ArrayList<TipoArchivoMovilidad> listaTipoArchObligatoriosReg = new ArrayList<TipoArchivoMovilidad>();
		ArrayList<TipoArchivoMovilidad> listaTipoArchObligatoriosFaltantes = new ArrayList<TipoArchivoMovilidad>();

		for (int i = 0; i < listaArchivosObligatorios.size(); i++) {
			MovilidadArchivo mam = (MovilidadArchivo) listaArchivosObligatorios.get(i);
			if (mam.getEsObligatorio().equals(1L)) {
				listaTipoArchObligatorios.add(mam.getTipoArchivo());
			}
		}

		for (int j = 0; j < getListaArchivosObligatoriosSel().size(); j++) {
			ArchivoMovilidadDE mva = (ArchivoMovilidadDE) getListaArchivosObligatoriosSel().get(j);
			TipoArchivoMovilidad tam = mva.getTipoArchivo();
			listaTipoArchObligatoriosReg.add(tam);
		}

		for (int j = 0; j < listaTipoArchObligatorios.size(); j++) {
			TipoArchivoMovilidad tam = listaTipoArchObligatorios.get(j);
			if (!listaTipoArchObligatoriosReg.contains(tam)) {
				listaTipoArchObligatoriosFaltantes.add(tam);
			}
		}

		if (listaTipoArchObligatoriosFaltantes.size() > 0) {
			String error = "Debe adjuntar los documentos necesarios para esta modalidad: \n";
			for (int j = 0; j < listaTipoArchObligatoriosFaltantes.size(); j++) {
				TipoArchivoMovilidad tam = listaTipoArchObligatoriosFaltantes.get(j);
				error += tam.getNombre() + " \n";
			}
			errores[13] = error;
			bErrorDocumentos = true;
			result = false;
		}
		
		if(!validarNumeroMaxAplicacionesDocente())
			result = false;

		return result;
	}
	
	public boolean validarNumeroMaxAplicacionesDocente() {
		//VALIDACIONES CONVOCATORIA PADRE
		idConvocatoria = (Long) sesion.getAttribute("idConvocatoriaActual");
		convocatoriaActual = (Convocatoria) servicioGeneral.obtenerObjeto(new Convocatoria(), idConvocatoria);// id)(((Convocatoria)
		ConvocatoriaPadre convPadre = convocatoriaActual.getPadre();
		List<Convocatoria> mods= servicioModalidad.obtenerConvocatoriasxPadre(convPadre);
		InvestigadorInterno invI = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());
		Dependencia dependenciaMov = obtenerDependenciaMovilidad(personaActual);
		Sede sedeMov = dependenciaMov.getSede();
		Long tipoParam = 0L;
		
		tipoParam = Tipos.TIPOS_PARAM_CONV_MOV_DOCENTE_CONV_PADRE_SEDE;
		listaParametrosConvPadre = servicioModalidad.obtenerParametrosConvocatoriasPadre(convPadre, tipoParam);
		
		for (ConvocatoriaPadreParametrizacion convParam : listaParametrosConvPadre) {
			Long movilidadesDocente = 0L;
			Long movilidadesConvocatoria = convParam.getValorParametro();
			Sede sedeParamConvocatoria = convParam.getDependencia().getSede();
			
			if(sedeParamConvocatoria.equals(sedeMov)) {
				for (Convocatoria convocatoria : mods)
					movilidadesDocente+=obtenerNumMovilidadesXModadlidadXDocente(invI, convocatoria, sedeMov, null);
				
				if (movilidadesDocente >= movilidadesConvocatoria) {
					String mensajeError = "";
					if (movilidadesDocente == 1) {
						mensajeError = "El docente ya tiene " + movilidadesDocente + " movilidad aprobada y/o ejecutada, y solo se permite " + movilidadesConvocatoria + " en la CONVOCATORIA para la SEDE " + sedeMov.getNombre();
					} else {
						mensajeError = "El docente ya tiene " + movilidadesDocente + " movilidades aprobadas y/o ejecutadas, y solo se permite " + movilidadesConvocatoria + " en la CONVOCATORIA para la SEDE " + sedeMov.getNombre();
					}
					errores[14] = mensajeError;
					mensajeError(mensajeError);
					return false;
				}
			}
		}
		
		tipoParam = Tipos.TIPOS_PARAM_CONV_MOV_DOCENTE_CONV_SEDE;
		listaParametrosConv = servicioModalidad.obtenerParametrosConvocatorias(convocatoriaActual, tipoParam);
		
		for (ConvocatoriaParametrizacion convParam : listaParametrosConv) {
			Integer movilidadesDocente = 0;
			Long movilidadesConvocatoria = convParam.getValorParametro();
			Sede sedeParamConvocatoria = convParam.getDependencia().getSede();
			
			if(sedeParamConvocatoria.equals(sedeMov)) {
				
				movilidadesDocente=obtenerNumMovilidadesXModadlidadXDocente(invI, convocatoriaActual, sedeMov, null);
				
				if (movilidadesDocente >= movilidadesConvocatoria) {
					String mensajeError = "";
					if (movilidadesDocente == 1) {
						mensajeError = "El docente ya tiene " + movilidadesDocente + " movilidad aprobada y/o ejecutada, y solo se permite " + movilidadesConvocatoria + " en esta MODALIDAD para la SEDE " + sedeMov.getNombre() ;
					} else {
						mensajeError = "El docente ya tiene " + movilidadesDocente + " movilidades aprobadas y/o ejecutadas, y solo se permite " + movilidadesConvocatoria + " en esta MODALIDAD para la SEDE " + sedeMov.getNombre();
					}
					errores[14] = mensajeError;
					mensajeError(mensajeError);
					return false;
				}
			}
		}
		
		tipoParam = Tipos.TIPOS_PARAM_CONV_MOV_DOCENTE_CONV_ANIO;
		listaParametrosConv = servicioModalidad.obtenerParametrosConvocatorias(convocatoriaActual, tipoParam);
		
		for (ConvocatoriaParametrizacion convParam : listaParametrosConv) {
			Integer movilidadesDocente = 0;
			Long movilidadesConvocatoria = convParam.getValorParametro();
			String anioParam = convParam.getAño();
			String anioFechaInicio = obtenerAño(mde.getFechainicial());
			
			if(anioParam.equals(anioFechaInicio)) {
				movilidadesDocente = obtenerNumMovilidadesXModadlidadXDocente(invI, convocatoriaActual, null, anioFechaInicio);
				if(movilidadesDocente >= movilidadesConvocatoria) {
					String mensajeError = "";
					if (movilidadesDocente == 1) {
						mensajeError = "El docente ya tiene " + movilidadesDocente + " movilidad aprobada y/o ejecutada, en el año "+ anioParam +" y solo se permite " + movilidadesConvocatoria + " en esta MODALIDAD para el AÑO " + anioFechaInicio;
					} else {
						mensajeError = "El docente ya tiene " + movilidadesDocente + " movilidades aprobadas y/o ejecutadas, en el año "+ anioParam +" y solo se permite " + movilidadesConvocatoria + " en esta MODALIDAD para el AÑO " + anioFechaInicio;
					}
					errores[14] = mensajeError;
					mensajeError(mensajeError);
					return false;
				}
			}
		}
		
		tipoParam = Tipos.TIPOS_PARAM_CONV_MOV_DOCENTE_CONV_PONENCIA;
		listaParametrosConv = servicioModalidad.obtenerParametrosConvocatorias(convocatoriaActual, tipoParam);
		
		for (ConvocatoriaParametrizacion convParam : listaParametrosConv) {
			Integer movilidadesDocente = 0;
			Long movilidadesConvocatoria = convParam.getValorParametro();
			String tipoPonenciaParam = convParam.getObjetoMovilidad().getId().toString();
			List<MovilidadPonencia> listaPonenciasMov = mde.getListaPonencias();
			
			for (MovilidadPonencia movPonencia : listaPonenciasMov) {
				String tipoPonenciaMov = movPonencia.getPonencia().getId().toString();
				
				if(tipoPonenciaParam.equals(tipoPonenciaMov)) {
					List<MovilidadDocentesExterior> listaMovilidadesDocente = cargarMovilidadesXDocenteXTipoPonencia(MovilidadDocentesExterior.class, invI, convocatoriaActual,tipoPonenciaMov);
					movilidadesDocente = !esNulo(listaMovilidadesDocente) ? listaMovilidadesDocente.size() : 0;
					if(movilidadesDocente >= movilidadesConvocatoria) {
						String mensajeError = "";
						if (movilidadesDocente == 1) {
							mensajeError = "El docente ya tiene " + movilidadesDocente + " movilidad aprobada y/o ejecutada para alguno de los objetos de la movilidad seleccionado y solo se permite " + movilidadesConvocatoria + " en esta MODALIDAD";
						} else {
							mensajeError = "El docente ya tiene " + movilidadesDocente + " movilidades aprobadas y/o ejecutadas para alguno de los objetos de la movilidad seleccionado y solo se permite " + movilidadesConvocatoria + " en esta MODALIDAD";
						}
						errores[14] = mensajeError;
						mensajeError(mensajeError);
						return false;
					}
				}		
			}	
		}
		
		return true;
	}
	
	public Dependencia obtenerDependenciaMovilidad(Persona personaAux) {
		Dependencia dependencia = new Dependencia();
		
		if(mde.getMovilidadDirigidaOtraDependencia() != null && mde.getMovilidadDirigidaOtraDependencia().equals("S")) {
			dependencia = servicioDependencia.obtenerDependencia(facultadSolicitud);
		}else {
			if (personaAux instanceof Investigador) {
				if (personaAux instanceof InvestigadorInterno) {
					personaAux = servicioPersona.obtenerInvestigadorInternoCompleto(personaAux.getId());
					InvestigadorInterno investigadorInterno = (InvestigadorInterno) personaAux;
					dependencia = servicioDependencia.obtenerDependencia(investigadorInterno.getId());
				}
			}
		}
		
		return dependencia;
	}

	private boolean validarTituloYEvento(MovilidadDocentesExterior mov) {
		boolean result = true;
		bErrorEvento = false;
		bErrorTitulo = false;
		if (mov.getTitulo().equals("")) {
			String error = "El nombre de la ponencia es obligatorio \n";
			errores[2] = error;
			bErrorTitulo = true;
			result = false;
		}

		return result;
	}

	public void calcularFechaMinimaInicio() {
		Calendar fechaActual = Calendar.getInstance();
		fechaActual.add(Calendar.DATE, tiempoValidacionMovilidad);
		fechaMinimaInicio = fechaActual.getTime();
		System.out.print("ManejadorCrearEditarMovilidadEvento fechaMinimaInicio: " + fechaMinimaInicio);
	}

	public void limpiar() {
		persona = new Persona();
		documento = new String("");
		// sesion.removeAttribute("ManejadorCrearEditarMovilidadEvento");
		cargarValoresIniciales();
		ocultarPaneles();
	}

	void processChild(List childList) {
		for (int i = 0; i < childList.size(); i++) {
			UIComponent component = (UIComponent) childList.get(i);
			try {
				UIInput input = (UIInput) component;
				input.setSubmittedValue(null);
			} catch (Exception ex) {

			}
			List childList2 = component.getChildren();
			processChild(childList2);
		}
	}

	public void cancelAction(ActionEvent event) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot viewRoot = facesContext.getViewRoot();
		List childList = viewRoot.getChildren();
		processChild(childList);
	}

	public void ocultarPaneles() {
		mostrarDatosBasicos = false;
		mostrarDatosEvento = false;
		mostrarDatosEventoAux = false;
		panelMasDatos = false;
		panelNoExiste = false;
		identificacion = false;
		fechaIncorrecta2 = false;
		panelMasDatos2 = false;
	}

	public void validarFecha(ValueChangeEvent event) {
		Date fechaEvento = (Date) event.getNewValue();

		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(fechaEvento);
		int diff = 0;
		/*
		 * java.util.Date fechaActual = new Date(); if
		 * (fechaActual.before(fechaEvento)) { mostrarMasDatos(); } else {
		 * ocultarMasDatos(); }
		 */
		while (cal1.before(cal2) || cal1.equals(cal2)) // (c) on its own = 3
		// days diff
		{
			SimpleDateFormat dia = new SimpleDateFormat("EEEE");
			int diaEntero = cal1.get(Calendar.DAY_OF_WEEK);

			// System.out.println("DIA DE LA SEMANA ----------------------: " +
			// dia.format(cal1.getTime()) + "entero del dia::" + diaEntero);
			boolean esFestivo = false;
			SimpleDateFormat spd = new SimpleDateFormat("dd");
			SimpleDateFormat spm = new SimpleDateFormat("MM");
			SimpleDateFormat spy = new SimpleDateFormat("yyyy");

			/*
			 * List listaFestivos = new ArrayList(); listaFestivos =
			 * servicioGeneral .obtenerListaObjetos("Festivo where ano ='" +
			 * Integer.parseInt(spy.format(cal1.getTime())) + "' and mes ='" +
			 * Integer.parseInt(spm.format(cal1.getTime())) + "'  and dia ='" +
			 * Integer.parseInt(spd.format(cal1.getTime())) + "' ");
			 * 
			 * if (listaFestivos != null && listaFestivos.size() > 0) {
			 * esFestivo = true; // System.out.println(
			 * "ES FESTIVO ----------------------: " + //
			 * dia.format(cal1.getTime()) + " ** entero del dia::" + //
			 * diaEntero + " fECHA COMPLETA " + cal1.getTime() ); }
			 */

			cal1.add(Calendar.DATE, 1);
			// if ((diaEntero != 7 && diaEntero != 1) && !esFestivo) {
			diff++;
			// }

		}
		// System.out.print("Esta es la diferencia de días****************** " +
		// diff + "***");
		if (diff >= 30 || (tiempoEjecucion != 0
				&& diff >= tiempoEjecucion)) {
			mostrarMasDatos();
		} else {
			ocultarMasDatos();
		}

	}

//	public void validarFechaLlegadaEstancia(ValueChangeEvent event) {
//		Date fechaEvento = (Date) event.getNewValue();
//		Calendar cal1 = Calendar.getInstance();
//		cal1.setTime(mde.getFechainicial());
//		Calendar cal2 = Calendar.getInstance();
//		cal2.setTime(fechaEvento);
//		int diff = 0;
//		while (cal1.before(cal2) || cal1.equals(cal2)) {
//			SimpleDateFormat dia = new SimpleDateFormat("EEEE");
//			SimpleDateFormat spd = new SimpleDateFormat("dd");
//			SimpleDateFormat spm = new SimpleDateFormat("MM");
//			SimpleDateFormat spy = new SimpleDateFormat("yyyy");
//			cal1.add(Calendar.DATE, 1);
//			diff++;
//		}
//		if (diff >= 30) {
//			mostrarMasDatos2();
//		} else {
//			ocultarMasDatos2();
//		}
//	}

	public void validarFechaLlegada(ValueChangeEvent event) {
		Date fechaEvento = (Date) event.getNewValue();

		Calendar c = Calendar.getInstance();
		c.setTime(mde.getFechainicial());

		c.add(Calendar.DATE, 9);
		boolean bandera = true;
		if (fechaEvento.before(this.mde.getFechainicial())) {
			bandera = false;
		}
		Long diferencia = (fechaEvento.getTime() - mde.getFechainicial().getTime());
		double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
		if (duracionMaximaMovilidad != null) {
			if (duracionMaximaMovilidad.split(":").length != 1) {
				String[] cond = duracionMaximaMovilidad.split(":");
				for (int i = 0; i < cond.length; i += 2) {
					String param = cond[i];
					String[] opt = cond[i + 1].split(",");
					if (param.equals("OBJETIVO") && maxTrabajos == 1) {
						Long valIf = ((MovilidadPonencia) getListaPonencias().get(0)).getPonencia().getId();
						for (String aCase : opt) {
							if (aCase.contains("<=") && aCase.split("<=")[0].equals(valIf.toString())
									&& !(dias <= Long.valueOf(aCase.split("<=")[1]))) {
								bandera = false;
								break;
							} else if (aCase.contains(">=") && aCase.split(">=")[0].equals(valIf.toString())
									&& !(dias >= Long.valueOf(aCase.split(">=")[1]))) {
								bandera = false;
								break;
							}
						}
					}
				}
			} else if (dias > Long.valueOf(duracionMaximaMovilidad)) {
				bandera = false;
			}
		} else {
//			if (dias < 30) {
//				bandera = false;
//			}
			bandera = true;
		}
		if (bandera) {
			mostrarMasDatos2();

		} else {
			ocultarMasDatos2();
		}

	}

	private void cargarInstituciones() {
		listaInstitucion = servicioGeneral.obtenerListaInstituciones();
		institucionItem = new SelectItem[listaInstitucion.size()];
		for (int i = 0; i < listaInstitucion.size(); i++) {
			Institucion in = (Institucion) listaInstitucion.get(i);
			institucionItem[i] = new SelectItem(in.getId(), in.getNombre());
		}
	}

	private void cargarPaises() {
		if (esMovilidadNacional) {
			List listaPaises = servicioGeneral.obtenerListaObjetosOrdenadosAsc(new Pais(), "nombre");
			paises = new Vector();
			for (Iterator it = listaPaises.iterator(); it.hasNext();) {
				Pais p = (Pais) it.next();
				if (p.getId().equals("CO")) {
					SelectItem s = new SelectItem(p.getId(), p.getNombre());
					paises.add(s);
				}
			}
		} else {
			List listaPaises = servicioGeneral.obtenerListaObjetosOrdenadosAsc(new Pais(), "nombre");
			paises = new Vector();
			for (Iterator it = listaPaises.iterator(); it.hasNext();) {
				Pais p = (Pais) it.next();
				SelectItem s = new SelectItem(p.getId(), p.getNombre());
				paises.add(s);
			}
		}

	}

	private void cargarTiposDocumento() {
		if (tipoDocumentoItem == null || (tipoDocumentoItem != null && tipoDocumentoItem.length == 0)) {
			List listaTipoDocumento = servicioGeneral.obtenerListaObjetos("TipoDocumento");
			tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
			for (int i = 0; i < listaTipoDocumento.size(); i++) {
				TipoDocumento td = (TipoDocumento) listaTipoDocumento.get(i);
				tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
			}
			tipoDocumento = (TipoDocumento) listaTipoDocumento.get(0);
		}
	}

	private void cargarValoresIniciales() {
		errores = new String[15];
		bErrorProyecto = false;
		bErrorEvento = false;
		bErrorTitulo = false;
		bErrorPais = false;
		bErrorCiudad = false;
		bErrorPonencia = false;
		bErrorResumen = false;
		bErrorResolucion = false;
		bErrorListaPonencia = false;
		bErrorDocumentos = false;
		bErrorTiquete = false;
		bErrorInscripcion = false;
		bPlanAccion = false;
		mostrarDatosBasicos = false;
		mostrarDatosEvento = false;
		mostrarDatosEventoAux = false;
		fechaIncorrecta = false;
		panelNoExiste = false;
		panelMasDatos = false;
		identificacion = true;
		panelArchivos = new HtmlPanelGroup();
		bOtroTipoPonencia = false;
		fechaIncorrecta2 = false;
		panelMasDatos2 = false;
		noExiste = "";
		aporteFacultad = new Long(0);
		personaActual = (Persona) sesion.getAttribute("persona");
		idcreador = personaActual.getId().getDocumento();
		tipoDocCreador = personaActual.getId().getTipoDocumento();
		idPlanAccion = "0";
		persona = new Persona();
		documento = new String();
		estado = "";
		cargarTiposDocumento();
		cargarPaises();
		cargarSedeSolicitud();
		// cargarInstituciones();

		listaEntidadInvestigacion = new ArrayList();

		listaEntidadInvestigacion = servicioGeneral.obtenerListaObjetos("TipoEntidadInvestigacion");

		if (listaEntidadInvestigacion != null) {
			aprobacionInvestigacionUNItem = new SelectItem[listaEntidadInvestigacion.size()];

			for (int i = 0; i < listaEntidadInvestigacion.size(); i++) {
				TipoEntidadInvestigacion tei = (TipoEntidadInvestigacion) listaEntidadInvestigacion.get(i);
				aprobacionInvestigacionUNItem[i] = new SelectItem(tei.getId().toString(), tei.getNombre());

			}
		}

	}

	private void reiniciarVariables() {
		errores = new String[15];
		bErrorProyecto = false;
		bErrorEvento = false;
		bErrorTitulo = false;
		bErrorPais = false;
		bErrorCiudad = false;
		bErrorPonencia = false;
		bErrorResumen = false;
		bErrorResolucion = false;
		bErrorListaPonencia = false;
		bErrorTiquete = false;
		bErrorInscripcion = false;
		bPlanAccion = false;
		bErrorDocumentos = false;
		mostrarDatosBasicos = false;
		mostrarDatosEvento = false;
		mostrarDatosEventoAux = false;
		fechaIncorrecta = false;
		panelNoExiste = false;
		panelMasDatos = false;
		identificacion = true;
		panelArchivos = new HtmlPanelGroup();
		bOtroTipoPonencia = false;
		fechaIncorrecta2 = false;
		panelMasDatos2 = false;
		noExiste = "";
		aporteFacultad = new Long(0);
		nombreVicerrectoria = "";
		nombreSede = "";
		nombreFacultad = "";
		nombreDepartamento = "";
		mostrarError = false;
	}

	private void mostrarDatosPersona() {
		mostrarDatosBasicos = true;
		mostrarDatosEvento = true;
	}

	private void mostrarMasDatos() {
		panelMasDatos = true;
		fechaIncorrecta = false;
		fechaIncorrecta2 = false;
		panelMasDatos2 = false;

	}

	private void mostrarMasDatos2() {
		panelMasDatos = true;
		panelMasDatos2 = true;
		fechaIncorrecta = false;
		fechaIncorrecta2 = false;
	}

	private void mostrarPanelNoExiste() {
		panelNoExiste = true;
	}

	private void ocultarMasDatos() {
		fechaIncorrecta = true;
		panelMasDatos = false;
		fechaIncorrecta2 = false;
		panelMasDatos2 = false;

	}

	private void ocultarMasDatos2() {
		fechaIncorrecta2 = true;
		panelMasDatos2 = false;
	}

	public void apruebaEntidad() {
		TipoInforme tinAux = (TipoInforme) tablaEntidadInvestigacion.getRowData();
		FacesContext ctx = FacesContext.getCurrentInstance();

		// listaInformes = new ArrayList();
		// listaInformes =
		// servicioGeneral.obtenerListaObjetos("ProyectoInforme where id
		// ='"+idInforme+"'");
		// ProyectoInforme pinAux = new ProyectoInforme();
		if (tinAux != null && tinAux.getArchivoInforme() != null) {
			// pinAux = (ProyectoInforme)listaInformes.get(0);
			try {
				if (!ctx.getResponseComplete()) {
					HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
					response.setContentType("text/plain");
					response.setHeader("Content-Disposition",
							"attachment;filename=\"" + tinAux.getNombreArchivo() + "\"");
					ServletOutputStream out = response.getOutputStream();
					out.write(tinAux.getBytesArchivoInforme());
					out.flush();
					ctx.responseComplete();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void guardarArchivo() {

		try {

			if (archivoCargar.getContents() != null) {
				int i = archivoCargar.getFileName().lastIndexOf("\\");

				List listaTipoMovilidad = new ArrayList();
				listaTipoMovilidad = servicioGeneral.obtenerListaObjetos("TipoMovilidad where id ='B1'");
				TipoMovilidad tipoMovilidad = (TipoMovilidad) listaTipoMovilidad.get(0);

				ArchivoMovilidadDE archivoMovilidad = new ArchivoMovilidadDE();
				archivoMovilidad.setBytes(archivoCargar.getContents());
				archivoMovilidad.setNombre(archivoCargar.getFileName().substring(i + 1));
				archivoMovilidad.setFecha(new Date());
				archivoMovilidad.setTipoMovilidad(tipoMovilidad);
				// listaArchivos = new ArrayList();aa

				listaArchivos.add(archivoMovilidad);
			}

		} catch (DataIntegrityViolationException ex) {
			System.out.println(ex.toString());
			// if (ex.getMessage().indexOf("AD_COMBINACION01_UK") > 0)
			// resultadoIngresoInforme = "Ya existe un archivo con este nombre";
			// else
			// resultadoIngresoInforme =
			// "Ocurrio un error inesperado al publicar el archivo";
		} catch (Exception ex) {
			System.out.println(ex.toString());
			// resultadoIngresoInforme =
			// "Ocurrio un error inesperado al publicar el archivo";
		}
	}

	public void enviarCorreo() {
		Correo correo = new Correo();
		correo.adicionarDireccion("iabohorquezc@unal.edu.co");
		correo.setOrigen(personaActual.getEmail());
		correo.adicionarCopiaOculta(new String(personaActual.getEmail()));
		String asunto = "Nueva movilidad para evento internacional";

		correo.setAsunto(asunto);// getCorreoActual().getAsunto());//"Quiere
		// ser posible evaluador");
		String cuerpo = "Se ha adicionado una nueva movilidad para evento internacional.";
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm.ss");

		Calendar actual = Calendar.getInstance();
		Date date = actual.getTime();
		String strDate = formatter.format(date);
		cuerpo = cuerpo + strDate;
		correo.setCuerpo(cuerpo);// "por favoooooooor, le pagamos y todo");
		String mensajeCorreo;
		if (servicioCorreo.enviarCorreo(correo)) {
			mensajeCorreo = "Su correo ha sido enviado con exito al posible evaluador con copia a "
					+ persona.getEmail();
		} else {
			mensajeCorreo = "No se pudo enviar el correo, por favor verifique la dirección electronica";
		}
		System.out.println(mensajeCorreo);
	}

	public String getAceptacionDIB() {
		return aceptacionDIB;
	}

	public String getAceptacionFacultad() {
		return aceptacionFacultad;
	}

	public String getAceptacionPonencia() {
		return aceptacionPonencia;
	}

	public Long getAporteFacultad() {
		return aporteFacultad;
	}

	public SelectItem[] getAprobacion() {
		return aprobacion;
	}

	public String getCategoriaInvestigador() {
		return categoriaInvestigador;
	}

	public String getDepartamentoDocente() {
		return departamentoDocente;
	}

	public String getDepartamentoSeleccionado() {
		return departamentoSeleccionado;
	}

	public Dependencia getDependencia() {
		return dependencia;
	}

	public String getDependenciaId() {
		return dependenciaId;
	}

	public SelectItem[] getDepsItem() {
		return depsItem;
	}

	public Long getDestinado() {
		return destinado;
	}

	public String getDocumento() {
		return documento;
	}

	public String getDocumentoDocente() {
		return documentoDocente;
	}

	public String getFacultadDocente() {
		return facultadDocente;
	}

	public String getFacultadSeleccionada() {
		return facultadSeleccionada;
	}

	public String getFinanciacionActual() {
		return financiacionActual;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public Grupo getGrupoActual() {
		return grupoActual;
	}

	public String getIdCiudad() {
		return idCiudad;
	}

	public String getIdcreador() {
		return idcreador;
	}

	public String getIdgrupo() {
		return idgrupo;
	}

	public String getIdInstitucion() {
		return idInstitucion;
	}

	public String getIdInvestigador() {
		return idInvestigador;
	}

	public String getIdPais() {
		return idPais;
	}

	public String getIdProyecto() {
		return idProyecto;
	}

	public String getIdTipoPonencia() {
		return idTipoPonencia;
	}

	public SelectItem[] getInstitucionItem() {
		return institucionItem;
	}

	public Investigador getInvestigadorActual() {
		return investigadorActual;
	}

	public SelectItem[] getInvestigadorItem() {
		return investigadorItem;
	}

	public List getListaArchivos() {
		return listaArchivos;
	}

	public List getListaCategoriaInvestigador() {
		return listaCategoriaInvestigador;
	}

	public List getListaDependencias() {
		return listaDependencias;
	}

	public List getListaGrupos() {
		return listaGrupos;
	}

	public List getListaGruposInv() {
		return listaGruposInv;
	}

	public List getListaInstitucion() {
		return listaInstitucion;
	}

	public List getListaIntegrantesGrupo() {
		return listaIntegrantesGrupo;
	}

	public List getListaTipoArchivo() {
		return listaTipoArchivo;
	}

	public String getNombreDepartamentoSeleccionado() {
		return nombreDepartamentoSeleccionado;
	}

	public String getNombreDocente() {
		return nombreDocente;
	}

	public String getNombreEvento() {
		return nombreEvento;
	}

	public String getNombreFacultadSeleccionada() {
		return nombreFacultadSeleccionada;
	}

	public String getNombreLiderGrupo() {
		return nombreLiderGrupo;
	}

	public String getNombreSedeSeleccionada() {
		return nombreSedeSeleccionada;
	}

	public String getOtroTipoPonencia() {
		return otroTipoPonencia;
	}

	public List getPaises() {
		return paises;
	}

	public HtmlPanelGroup getPanelArchivos() {
		return panelArchivos;
	}

	public Persona getPersona() {
		return persona;
	}

	public List getProyectosInvestigador() {
		return proyectosInvestigador;
	}

	public String getResolucionViaje() {
		return resolucionViaje;
	}

	public Investigador getResponsableGrupo() {
		return responsableGrupo;
	}

	public String getSedeSeleccionada() {
		return sedeSeleccionada;
	}

	public String getSolDocente() {
		return solDocente;
	}

	public String getSolinscripcion() {
		return solInscripcion;
	}

	public String getSolInscripcion() {
		return solInscripcion;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public SelectItem[] getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}

	public String getTipoInvestigador() {
		return tipoInvestigador;
	}

	public SelectItem[] getTipoPonencia() {
		return tipoPonencia;
	}

	public String getTipoPonenciaActual() {
		return tipoPonenciaActual;
	}

	public String getTituloPonencia() {
		return tituloPonencia;
	}

	public boolean isBFinanciacion() {
		return bFinanciacion;
	}

	public boolean isBOtroTipoPonencia() {
		return bOtroTipoPonencia;
	}

	public boolean isFechaIncorrecta() {
		return fechaIncorrecta;
	}

	public boolean isFechaIncorrecta2() {
		return fechaIncorrecta2;
	}

	public boolean isIdentificacion() {
		return identificacion;
	}

	public boolean isMostrarDatosBasicos() {
		return mostrarDatosBasicos;
	}

	public boolean isMostrarDatosEvento() {
		return mostrarDatosEvento;
	}

	public boolean isPanelMasDatos() {
		return panelMasDatos;
	}

	public boolean isPanelMasDatos2() {
		return panelMasDatos2;
	}

	public boolean isPanelNoExiste() {
		return panelNoExiste;
	}

	public void setAceptacionDIB(String aceptacionDIB) {
		this.aceptacionDIB = aceptacionDIB;
	}

	public void setAceptacionFacultad(String aceptacionFacultad) {
		this.aceptacionFacultad = aceptacionFacultad;
	}

	public void setAceptacionPonencia(String aceptacionPonencia) {
		this.aceptacionPonencia = aceptacionPonencia;
	}

	public void setAporteFacultad(Long aporteFacultad) {
		this.aporteFacultad = aporteFacultad;
	}

	public void setAprobacion(SelectItem[] aprobacion) {
		this.aprobacion = aprobacion;
	}

	public void setBFinanciacion(boolean financiacion) {
		bFinanciacion = financiacion;
	}

	public void setBOtroTipoPonencia(boolean otroTipoPonencia) {
		bOtroTipoPonencia = otroTipoPonencia;
	}

	public void setCategoriaInvestigador(String categoriaInvestigador) {
		this.categoriaInvestigador = categoriaInvestigador;
	}

	public void setDepartamentoDocente(String departamentoDocente) {
		this.departamentoDocente = departamentoDocente;
	}

	public void setDepartamentoSeleccionado(String departamentoSeleccionado) {
		this.departamentoSeleccionado = departamentoSeleccionado;
	}

	public void setDependencia(Dependencia dependencia) {
		this.dependencia = dependencia;
	}

	public void setDependenciaId(String dependenciaId) {
		this.dependenciaId = dependenciaId;
	}

	public void setDepsItem(SelectItem[] depsItem) {
		this.depsItem = depsItem;
	}

	public void setDestinado(Long destinado) {
		this.destinado = destinado;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public void setDocumentoDocente(String documentoDocente) {
		this.documentoDocente = documentoDocente;
	}

	public void setFacultadDocente(String facultadDocente) {
		this.facultadDocente = facultadDocente;
	}

	public void setFacultadSeleccionada(String facultadSeleccionada) {
		this.facultadSeleccionada = facultadSeleccionada;
	}

	public void setFechaIncorrecta(boolean fechaIncorrecta) {
		this.fechaIncorrecta = fechaIncorrecta;
	}

	public void setFechaIncorrecta2(boolean fechaIncorrecta2) {
		this.fechaIncorrecta2 = fechaIncorrecta2;
	}

	public void setFinanciacionActual(String financiacionActual) {
		this.financiacionActual = financiacionActual;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public void setGrupoActual(Grupo grupoActual) {
		this.grupoActual = grupoActual;
	}

	public void setIdCiudad(String idCiudad) {
		this.idCiudad = idCiudad;
	}

	public void setIdcreador(String idcreador) {
		this.idcreador = idcreador;
	}

	public void setIdentificacion(boolean identificacion) {
		this.identificacion = identificacion;
	}

	public void setIdgrupo(String idgrupo) {
		this.idgrupo = idgrupo;
	}

	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	public void setIdInvestigador(String idInvestigador) {
		this.idInvestigador = idInvestigador;
	}

	public void setIdPais(String idPais) {
		this.idPais = idPais;
	}

	public void setIdProyecto(String idProyecto) {
		this.idProyecto = idProyecto;
	}

	public void setIdTipoPonencia(String idTipoPonencia) {
		this.idTipoPonencia = idTipoPonencia;
	}

	public void setInstitucionItem(SelectItem[] institucionItem) {
		this.institucionItem = institucionItem;
	}

	public void setInvestigadorActual(Investigador investigadorActual) {
		this.investigadorActual = investigadorActual;
	}

	public void setInvestigadorItem(SelectItem[] investigadorItem) {
		this.investigadorItem = investigadorItem;
	}

	public void setListaArchivos(List listaArchivos) {
		this.listaArchivos = listaArchivos;
	}

	public void setListaCategoriaInvestigador(List listaCategoriaInvestigador) {
		this.listaCategoriaInvestigador = listaCategoriaInvestigador;
	}

	public void setListaDependencias(List listaDependencias) {
		this.listaDependencias = listaDependencias;
	}

	public void setListaGrupos(List listaGrupos) {
		this.listaGrupos = listaGrupos;
	}

	public void setListaGruposInv(List listaGruposInv) {
		this.listaGruposInv = listaGruposInv;
	}

	public void setListaInstitucion(List listaInstitucion) {
		this.listaInstitucion = listaInstitucion;
	}

	public void setListaIntegrantesGrupo(List listaIntegrantesGrupo) {
		this.listaIntegrantesGrupo = listaIntegrantesGrupo;
	}

	public void setListaTipoArchivo(List listaTipoArchivo) {
		this.listaTipoArchivo = listaTipoArchivo;
	}

	public void setMostrarDatosBasicos(boolean mostrarDatosBasicos) {
		this.mostrarDatosBasicos = mostrarDatosBasicos;
	}

	public void setMostrarDatosEvento(boolean mostrarDatosEvento) {
		this.mostrarDatosEvento = mostrarDatosEvento;
	}

	public void setNombreDepartamentoSeleccionado(String nombreDepartamentoSeleccionado) {
		this.nombreDepartamentoSeleccionado = nombreDepartamentoSeleccionado;
	}

	public void setNombreDocente(String nombreDocente) {
		this.nombreDocente = nombreDocente;
	}

	public void setNombreEvento(String nombreEvento) {
		this.nombreEvento = nombreEvento;
	}

	public void setNombreFacultadSeleccionada(String nombreFacultadSeleccionada) {
		this.nombreFacultadSeleccionada = nombreFacultadSeleccionada;
	}

	public void setNombreLiderGrupo(String nombreLiderGrupo) {
		this.nombreLiderGrupo = nombreLiderGrupo;
	}

	public void setNombreSedeSeleccionada(String nombreSedeSeleccionada) {
		this.nombreSedeSeleccionada = nombreSedeSeleccionada;
	}

	public void setOtroTipoPonencia(String otroTipoPonencia) {
		this.otroTipoPonencia = otroTipoPonencia;
	}

	public void setPaises(List paises) {
		this.paises = paises;
	}

	public void setPanelArchivos(HtmlPanelGroup panelArchivos) {
		this.panelArchivos = panelArchivos;
	}

	public void setPanelMasDatos(boolean panelMasDatos) {
		this.panelMasDatos = panelMasDatos;
	}

	public void setPanelMasDatos2(boolean panelMasDatos2) {
		this.panelMasDatos2 = panelMasDatos2;
	}

	public void setPanelNoExiste(boolean panelNoExiste) {
		this.panelNoExiste = panelNoExiste;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public void setProyectosInvestigador(List proyectosInvestigador) {
		this.proyectosInvestigador = proyectosInvestigador;
	}

	public void setResolucionViaje(String resolucionViaje) {
		this.resolucionViaje = resolucionViaje;
	}

	public void setResponsableGrupo(Investigador responsableGrupo) {
		this.responsableGrupo = responsableGrupo;
	}

	public void setSedeSeleccionada(String sedeSeleccionada) {
		this.sedeSeleccionada = sedeSeleccionada;
	}

	public void setSolDocente(String solDocente) {
		this.solDocente = solDocente;
	}

	public void setSolinscripcion(String solInscripcion) {
		this.solInscripcion = solInscripcion;
	}

	public void setSolInscripcion(String solInscripcion) {
		this.solInscripcion = solInscripcion;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
		this.tipoDocumentoItem = tipoDocumentoItem;
	}

	public void setTipoInvestigador(String tipoInvestigador) {
		this.tipoInvestigador = tipoInvestigador;
	}

	public void setTipoPonencia(SelectItem[] tipoPonencia) {
		this.tipoPonencia = tipoPonencia;
	}

	public void setTipoPonenciaActual(String tipoPonenciaActual) {
		this.tipoPonenciaActual = tipoPonenciaActual;
	}

	public void setTituloPonencia(String tituloPonencia) {
		this.tituloPonencia = tituloPonencia;
	}

	public String getTipoDocDocente() {
		return tipoDocDocente;
	}

	public void setTipoDocDocente(String tipoDocDocente) {
		this.tipoDocDocente = tipoDocDocente;
	}

	public String getTipoDocCreador() {
		return tipoDocCreador;
	}

	public void setTipoDocCreador(String tipoDocCreador) {
		this.tipoDocCreador = tipoDocCreador;
	}

	public String[] getErrores() {
		return errores;
	}

	public void setErrores(String[] errores) {
		this.errores = errores;
	}

	public boolean isBErrorProyecto() {
		return bErrorProyecto;
	}

	public void setBErrorProyecto(boolean errorProyecto) {
		bErrorProyecto = errorProyecto;
	}

	public boolean isBErrorEvento() {
		return bErrorEvento;
	}

	public void setBErrorEvento(boolean errorEvento) {
		bErrorEvento = errorEvento;
	}

	public boolean isBErrorTitulo() {
		return bErrorTitulo;
	}

	public void setBErrorTitulo(boolean errorTitulo) {
		bErrorTitulo = errorTitulo;
	}

	public boolean isBErrorPais() {
		return bErrorPais;
	}

	public void setBErrorPais(boolean errorPais) {
		bErrorPais = errorPais;
	}

	public boolean isBErrorCiudad() {
		return bErrorCiudad;
	}

	public void setBErrorCiudad(boolean errorCiudad) {
		bErrorCiudad = errorCiudad;
	}

	public boolean isBErrorPonencia() {
		return bErrorPonencia;
	}

	public void setBErrorPonencia(boolean errorPonencia) {
		bErrorPonencia = errorPonencia;
	}

	public boolean isBErrorResumen() {
		return bErrorResumen;
	}

	public void setBErrorResumen(boolean errorResumen) {
		bErrorResumen = errorResumen;
	}

	public boolean isBErrorResolucion() {
		return bErrorResolucion;
	}

	public void setBErrorListaPonencia(boolean errorListaPoencia) {
		bErrorListaPonencia = errorListaPoencia;
	}

	public boolean isBErrorListaPonencia() {
		return bErrorListaPonencia;
	}

	public void setBErrorResolucion(boolean errorResolucion) {
		bErrorResolucion = errorResolucion;
	}

	public boolean isBErrorTiquete() {
		return bErrorTiquete;
	}

	public void setBErrorTiquete(boolean errorTiquete) {
		bErrorTiquete = errorTiquete;
	}

	public boolean isBErrorInscripcion() {
		return bErrorInscripcion;
	}

	public void setBErrorInscripcion(boolean errorInscripcion) {
		bErrorInscripcion = errorInscripcion;
	}

	public String getNoExiste() {
		return noExiste;
	}

	public void setNoExiste(String noExiste) {
		this.noExiste = noExiste;
	}

	public String getIdPlanAccion() {
		return idPlanAccion;
	}

	public void setIdPlanAccion(String idPlanAccion) {
		this.idPlanAccion = idPlanAccion;
	}

	public List getPlanesAccionInvestigador() {
		return planesAccionInvestigador;
	}

	public void setPlanesAccionInvestigador(List planesAccionInvestigador) {
		this.planesAccionInvestigador = planesAccionInvestigador;
	}

	public boolean isBPlanAccion() {
		return bPlanAccion;
	}

	public void setBPlanAccion(boolean planAccion) {
		bPlanAccion = planAccion;
	}

	public boolean isBImprimirReporte() {
		return bImprimirReporte;
	}

	public void setBImprimirReporte(boolean imprimirReporte) {
		bImprimirReporte = imprimirReporte;
	}

	public String getAprobacionInvestigacionUN() {
		return aprobacionInvestigacionUN;
	}

	public void setAprobacionInvestigacionUN(String aprobacionInvestigacionUN) {
		this.aprobacionInvestigacionUN = aprobacionInvestigacionUN;
	}

	public SelectItem[] getAprobacionInvestigacionUNItem() {
		return aprobacionInvestigacionUNItem;
	}

	public void setAprobacionInvestigacionUNItem(SelectItem[] aprobacionInvestigacionUNItem) {
		this.aprobacionInvestigacionUNItem = aprobacionInvestigacionUNItem;
	}

	public HtmlDataTable getTablaEntidadInvestigacion() {
		return tablaEntidadInvestigacion;
	}

	public void setTablaEntidadInvestigacion(HtmlDataTable tablaEntidadInvestigacion) {
		this.tablaEntidadInvestigacion = tablaEntidadInvestigacion;
	}

	public List getListaEntidadInvestigacion() {
		return listaEntidadInvestigacion;
	}

	public void setListaEntidadInvestigacion(List listaEntidadInvestigacion) {
		this.listaEntidadInvestigacion = listaEntidadInvestigacion;
	}

	public UploadedFile getArchivoVicerectoria() {
		return archivoVicerectoria;
	}

	public void setArchivoVicerectoria(UploadedFile archivoVicerectoria) {
		this.archivoVicerectoria = archivoVicerectoria;
	}

	public UploadedFile getArchivoDireccion() {
		return archivoDireccion;
	}

	public void setArchivoDireccion(UploadedFile archivoDireccion) {
		this.archivoDireccion = archivoDireccion;
	}

	public UploadedFile getArchivoFacultad() {
		return archivoFacultad;
	}

	public void setArchivoFacultad(UploadedFile archivoFacultad) {
		this.archivoFacultad = archivoFacultad;
	}

	public UploadedFile getArchivoDepartamento() {
		return archivoDepartamento;
	}

	public void setArchivoDepartamento(UploadedFile archivoDepartamento) {
		this.archivoDepartamento = archivoDepartamento;
	}

	public String getNombreVicerrectoria() {
		return nombreVicerrectoria;
	}

	public void setNombreVicerrectoria(String nombreVicerrectoria) {
		this.nombreVicerrectoria = nombreVicerrectoria;
	}

	public String getNombreSede() {
		return nombreSede;
	}

	public void setNombreSede(String nombreSede) {
		this.nombreSede = nombreSede;
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

	public UploadedFile getArchivoCargar() {
		return archivoCargar;
	}

	public void setArchivoCargar(UploadedFile archivoCargar) {
		this.archivoCargar = archivoCargar;
	}

	public HtmlDataTable getTablaArchivos() {
		return tablaArchivos;
	}

	public void setTablaArchivos(HtmlDataTable tablaArchivos) {
		this.tablaArchivos = tablaArchivos;
	}

	public List getListaPonencias() {
		List<MovilidadPonencia> listaPonencias = mde.getListaPonencias();
		return listaPonencias;
	}

	public List getListaPonenciasObligatorias() {
		return listaPonenciasObligatorias;
	}

	public void setListaPonenciasObligatorias(List listaPonenciasObligatorias) {
		this.listaPonenciasObligatorias = listaPonenciasObligatorias;
	}

	public UIData getTablaPonencias() {
		return tablaPonencias;
	}

	public void setTablaPonencias(UIData tablaPonencias) {
		this.tablaPonencias = tablaPonencias;
	}

	public String getNoExiste1() {
		return noExiste1;
	}

	public void setNoExiste1(String noExiste1) {
		this.noExiste1 = noExiste1;
	}

	public boolean isBErrorDocumentos() {
		return bErrorDocumentos;
	}

	public void setBErrorDocumentos(boolean errorDocumentos) {
		bErrorDocumentos = errorDocumentos;
	}

	public List getListaArchivosObligatorios() {
		return listaArchivosObligatorios;
	}

	public void setListaArchivosObligatorios(List listaArchivosObligatorios) {
		this.listaArchivosObligatorios = listaArchivosObligatorios;
	}

	public List<ArchivoMovilidadDE> getListaArchivosObligatoriosSel() {
		List<ArchivoMovilidadDE> listaArchivos = mde.getListaArchivo();
		return listaArchivos;
	}

	public HtmlDataTable getTablaArchivosObligatorios() {
		return tablaArchivosObligatorios;
	}

	public void setTablaArchivosObligatorios(HtmlDataTable tablaArchivosObligatorios) {
		this.tablaArchivosObligatorios = tablaArchivosObligatorios;
	}

	public HtmlDataTable getTablaArchivosObligatoriosSel() {
		return tablaArchivosObligatoriosSel;
	}

	public void setTablaArchivosObligatoriosSel(HtmlDataTable tablaArchivosObligatoriosSel) {
		this.tablaArchivosObligatoriosSel = tablaArchivosObligatoriosSel;
	}

	public SelectItem[] getTipoDocumentoSelItem() {
		return tipoDocumentoSelItem;
	}

	public void setTipoDocumentoSelItem(SelectItem[] tipoDocumentoSelItem) {
		this.tipoDocumentoSelItem = tipoDocumentoSelItem;
	}

	public String getTipoDocumentoSel() {
		return tipoDocumentoSel;
	}

	public void setTipoDocumentoSel(String tipoDocumentoSel) {
		this.tipoDocumentoSel = tipoDocumentoSel;
	}

	public UploadedFile getArchivoObligatorio() {
		return archivoObligatorio;
	}

	public void setArchivoObligatorio(UploadedFile archivoObligatorio) {
		this.archivoObligatorio = archivoObligatorio;
	}

	public String getNombreObligatorio() {
		return nombreObligatorio;
	}

	public void setNombreObligatorio(String nombreObligatorio) {
		this.nombreObligatorio = nombreObligatorio;
	}

	public CorreoPlantilla getCorreoActual() {
		return correoActual;
	}

	public void setCorreoActual(CorreoPlantilla correoActual) {
		this.correoActual = correoActual;
	}

	public String getCuerpoCorreo() {
		return cuerpoCorreo;
	}

	public void setCuerpoCorreo(String cuerpoCorreo) {
		this.cuerpoCorreo = cuerpoCorreo;
	}

	public boolean isMostrarDatosEventoAux() {
		return mostrarDatosEventoAux;
	}

	public void setMostrarDatosEventoAux(boolean mostrarDatosEventoAux) {
		this.mostrarDatosEventoAux = mostrarDatosEventoAux;
	}

	public boolean isMostrarError() {
		return mostrarError;
	}

	public void setMostrarError(boolean mostrarError) {
		this.mostrarError = mostrarError;
	}

	public String getMsgError() {
		return msgError;
	}

	public void setMsgError(String msgError) {
		this.msgError = msgError;
	}

	/**
	 * @return the movilidadPonenciaSeleccionada
	 */
	public MovilidadPonencia getMovilidadPonenciaSeleccionada() {
		return movilidadPonenciaSeleccionada;
	}

	/**
	 * @param movilidadPonenciaSeleccionada
	 *            the movilidadPonenciaSeleccionada to set
	 */
	public void setMovilidadPonenciaSeleccionada(MovilidadPonencia movilidadPonenciaSeleccionada) {
		this.movilidadPonenciaSeleccionada = movilidadPonenciaSeleccionada;
	}

	/**
	 * @return the puedeSubirArchivos
	 */
	public boolean isPuedeSubirArchivos() {
		return puedeSubirArchivos;
	}

	/**
	 * @param puedeSubirArchivos
	 *            the puedeSubirArchivos to set
	 */
	public void setPuedeSubirArchivos(boolean puedeSubirArchivos) {
		this.puedeSubirArchivos = puedeSubirArchivos;
	}

	/**
	 * @return the archivoMovilidadDESeleccionado
	 */
	public ArchivoMovilidadDE getArchivoMovilidadDESeleccionado() {
		return archivoMovilidadDESeleccionado;
	}

	/**
	 * @param archivoMovilidadDESeleccionado
	 *            the archivoMovilidadDESeleccionado to set
	 */
	public void setArchivoMovilidadDESeleccionado(ArchivoMovilidadDE archivoMovilidadDESeleccionado) {
		this.archivoMovilidadDESeleccionado = archivoMovilidadDESeleccionado;
	}

	/**
	 * @return the fechaMinimaInicio
	 */
	public Date getFechaMinimaInicio() {
		return fechaMinimaInicio;
	}

	public boolean isSiConvExt() {
		return siConvExt;
	}

	public void setSiConvExt(boolean siConvExt) {
		this.siConvExt = siConvExt;
	}

	public boolean isSiColombia() {
		return siColombia;
	}

	public void setSiColombia(boolean siColombia) {
		this.siColombia = siColombia;
	}

	public SelectItem[] getCiudadItem() {
		return ciudadItem;
	}

	public void setCiudadItem(SelectItem[] ciudadItem) {
		this.ciudadItem = ciudadItem;
	}

	public List getListaCiudades() {
		return listaCiudades;
	}

	public void setListaCiudades(List listaCiudades) {
		this.listaCiudades = listaCiudades;
	}

	public Departamento getDepartamentoActual() {
		return departamentoActual;
	}

	public void setDepartamentoActual(Departamento departamentoActual) {
		this.departamentoActual = departamentoActual;
	}

	public List getListaDepartamentos() {
		return listaDepartamentos;
	}

	public void setListaDepartamentos(List listaDepartamentos) {
		this.listaDepartamentos = listaDepartamentos;
	}

	public Ciudad getCiudadActual() {
		return ciudadActual;
	}

	public void setCiudadActual(Ciudad ciudadActual) {
		this.ciudadActual = ciudadActual;
	}

	public SelectItem[] getDepartamentoItem() {
		return departamentoItem;
	}

	public void setDepartamentoItem(SelectItem[] departamentoItem) {
		this.departamentoItem = departamentoItem;
	}

	public SelectItem[] getCaracterEventoItem() {
		return caracterEventoItem;
	}

	public void setCaracterEventoItem(SelectItem[] caracterEventoItem) {
		this.caracterEventoItem = caracterEventoItem;
	}

	public SelectItem[] getMedioTransporteItem() {
		return medioTransporteItem;
	}

	public void setMedioTransporteItem(SelectItem[] medioTransporteItem) {
		this.medioTransporteItem = medioTransporteItem;
	}

	public boolean isbErrorNombreUniversidad() {
		return bErrorNombreUniversidad;
	}

	public void setbErrorNombreUniversidad(boolean bErrorNombreUniversidad) {
		this.bErrorNombreUniversidad = bErrorNombreUniversidad;
	}

	public boolean isEsConvocatoriaFacultad() {
		return esConvocatoriaFacultad;
	}

	public void setEsConvocatoriaFacultad(boolean esConvocatoriaFacultad) {
		this.esConvocatoriaFacultad = esConvocatoriaFacultad;
	}

	public String getRestriccionArchivosConv() {
		return restriccionArchivosConv;
	}

	public void setRestriccionArchivosConv(String restriccionArchivosConv) {
		this.restriccionArchivosConv = restriccionArchivosConv;
	}

	public boolean isMostrarValorViaticos() {
		return mostrarValorViaticos;
	}

	public void setMostrarValorViaticos(boolean mostrarValorViaticos) {
		this.mostrarValorViaticos = mostrarValorViaticos;
	}

	public Long getRestriccionTiquetes() {
		return restriccionTiquetes;
	}

	public void setRestriccionTiquetes(Long restriccionTiquetes) {
		this.restriccionTiquetes = restriccionTiquetes;
	}

	public String getMsgErrorPasaporte() {
		return msgErrorPasaporte;
	}

	public void setMsgErrorPasaporte(String msgErrorPasaporte) {
		this.msgErrorPasaporte = msgErrorPasaporte;
	}

	public boolean isErrorPasaporte() {
		return errorPasaporte;
	}

	public void setErrorPasaporte(boolean errorPasaporte) {
		this.errorPasaporte = errorPasaporte;
	}

	public boolean isEsEstanciaInvestigacionDocente() {
		return esEstanciaInvestigacionDocente;
	}

	public void setEsEstanciaInvestigacionDocente(boolean esEstanciaInvestigacionDocente) {
		this.esEstanciaInvestigacionDocente = esEstanciaInvestigacionDocente;
	}

	public ActividadMovilidad getActMovi() {
		return actMovi;
	}

	public void setActMovi(ActividadMovilidad actMovi) {
		this.actMovi = actMovi;
	}

	public boolean isbErrorActividades() {
		return bErrorActividades;
	}

	public void setbErrorActividades(boolean bErrorActividades) {
		this.bErrorActividades = bErrorActividades;
	}

	public List<ActividadMovilidad> getListaActividades() {
		List<ActividadMovilidad> listaActividades = mde.getListaActividades();
		return listaActividades;
	}

	public ActividadMovilidad getActividadMovilidadSeleccionada() {
		return actividadMovilidadSeleccionada;
	}

	public void setActividadMovilidadSeleccionada(ActividadMovilidad actividadMovilidadSeleccionada) {
		this.actividadMovilidadSeleccionada = actividadMovilidadSeleccionada;
	}

	public boolean isEsMovMegaproyectosConcursos() {
		return esMovMegaproyectosConcursos;
	}

	public void setEsMovMegaproyectosConcursos(boolean esMovMegaproyectosConcursos) {
		this.esMovMegaproyectosConcursos = esMovMegaproyectosConcursos;
	}

	public boolean isEsMovPonencias() {
		return esMovPonencias;
	}

	public void setEsMovPonencias(boolean esMovPonencias) {
		this.esMovPonencias = esMovPonencias;
	}

	public String getIdSubmodalidadConv() {
		return idSubmodalidadConv;
	}

	public void setIdSubmodalidadConv(String idSubmodalidadConv) {
		this.idSubmodalidadConv = idSubmodalidadConv;
	}

	public String getCiudadEvento() {
		return ciudadEvento;
	}

	public void setCiudadEvento(String ciudadEvento) {
		this.ciudadEvento = ciudadEvento;
	}

	public ArchivoMovilidadDE getDocumentoSeleccionado() {
		return documentoSeleccionado;
	}

	public void setDocumentoSeleccionado(ArchivoMovilidadDE documentoSeleccionado) {
		this.documentoSeleccionado = documentoSeleccionado;
	}

	public MovilidadDocentesExterior getMde() {
		return mde;
	}

	public void setMde(MovilidadDocentesExterior mde) {
		this.mde = mde;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public boolean isMostrarAportesOtrasDependencias() {
		return mostrarAportesOtrasDependencias;
	}

	public void setMostrarAportesOtrasDependencias(boolean mostrarAportesOtrasDependencias) {
		this.mostrarAportesOtrasDependencias = mostrarAportesOtrasDependencias;
	}

	public String getTipoConvocatoria() {
		return tipoConvocatoria;
	}

	public void setTipoConvocatoria(String tipoConvocatoria) {
		this.tipoConvocatoria = tipoConvocatoria;
	}

	public boolean isMostrarValorInscripcionEvento() {
		return mostrarValorInscripcionEvento;
	}

	public void setMostrarValorInscripcionEvento(boolean mostrarValorInscripcionEvento) {
		this.mostrarValorInscripcionEvento = mostrarValorInscripcionEvento;
	}

	public boolean isMostrarValorTiquetes() {
		return mostrarValorTiquetes;
	}

	public void setMostrarValorTiquetes(boolean mostrarValorTiquetes) {
		this.mostrarValorTiquetes = mostrarValorTiquetes;
	}

	public boolean isMostrarValorTotalSol() {
		return mostrarValorTotalSol;
	}

	public void setMostrarValorTotalSol(boolean mostrarValorTotalSol) {
		this.mostrarValorTotalSol = mostrarValorTotalSol;
	}

	public boolean isMostrarSubModalidades() {
		return mostrarSubModalidades;
	}

	public void setMostrarSubModalidades(boolean mostrarSubModalidades) {
		this.mostrarSubModalidades = mostrarSubModalidades;
	}

	public String getValorAporteOtrasDep() {
		return valorAporteOtrasDep;
	}

	public void setValorAporteOtrasDep(String valorAporteOtrasDep) {
		this.valorAporteOtrasDep = valorAporteOtrasDep;
	}

	public String getDepValorAporteOtrasDep() {
		return depValorAporteOtrasDep;
	}

	public void setDepValorAporteOtrasDep(String depValorAporteOtrasDep) {
		this.depValorAporteOtrasDep = depValorAporteOtrasDep;
	}

	public String getTipoPonenciaPar() {
		return tipoPonenciaPar;
	}

	public void setTipoPonenciaPar(String tipoPonenciaPar) {
		this.tipoPonenciaPar = tipoPonenciaPar;
	}

	public Integer getTiempoValidacionMovilidad() {
		return tiempoValidacionMovilidad;
	}

	public void setTiempoValidacionMovilidad(Integer tiempoValidacionMovilidad) {
		this.tiempoValidacionMovilidad = tiempoValidacionMovilidad;
	}

	public SelectItem[] getListaSubmodalidades() {
		return listaSubmodalidades;
	}

	public void setListaSubmodalidades(SelectItem[] listaSubmodalidades) {
		this.listaSubmodalidades = listaSubmodalidades;
	}

	public String getSubmodalidad() {
		return submodalidad;
	}

	public void setSubmodalidad(String submodalidad) {
		this.submodalidad = submodalidad;
	}

	public Long getValorTotalMovSolicitado() {
		return valorTotalMovSolicitado;
	}

	public void setValorTotalMovSolicitado(Long valorTotalMovSolicitado) {
		this.valorTotalMovSolicitado = valorTotalMovSolicitado;
	}

	public boolean isEsEstancia() {
		return esEstancia;
	}

	public void setEsEstancia(boolean esEstancia) {
		this.esEstancia = esEstancia;
	}

	public boolean isValidarGrupo() {
		return validarGrupo;
	}

	public void setValidarGrupo(boolean validarGrupo) {
		this.validarGrupo = validarGrupo;
	}

	public String getMsgErrorGrupo() {
		return msgErrorGrupo;
	}

	public void setMsgErrorGrupo(String msgErrorGrupo) {
		this.msgErrorGrupo = msgErrorGrupo;
	}

	public boolean isErrorGrupo() {
		return errorGrupo;
	}

	public void setErrorGrupo(boolean errorGrupo) {
		this.errorGrupo = errorGrupo;
	}

	public boolean isErrorSubmodalidad() {
		return errorSubmodalidad;
	}

	public void setErrorSubmodalidad(boolean errorSubmodalidad) {
		this.errorSubmodalidad = errorSubmodalidad;
	}

	public String getValidadcionCostosSubmodalidad() {
		return validadcionCostosSubmodalidad;
	}

	public void setValidadcionCostosSubmodalidad(String validadcionCostosSubmodalidad) {
		this.validadcionCostosSubmodalidad = validadcionCostosSubmodalidad;
	}

	public boolean isEsMovilidadNacional() {
		return esMovilidadNacional;
	}

	public void setEsMovilidadNacional(boolean esMovilidadNacional) {
		this.esMovilidadNacional = esMovilidadNacional;
	}

	public String getDuracionMaximaMovilidad() {
		return duracionMaximaMovilidad;
	}

	public void setDuracionMaximaMovilidad(String duracionMaximaMovilidad) {
		this.duracionMaximaMovilidad = duracionMaximaMovilidad;
	}

	public boolean isMostrarPasaporte() {
		return mostrarPasaporte;
	}

	public void setMostrarPasaporte(boolean mostrarPasaporte) {
		this.mostrarPasaporte = mostrarPasaporte;
	}

	public Long getMaxTrabajos() {
		return maxTrabajos;
	}

	public void setMaxTrabajos(Long maxTrabajos) {
		this.maxTrabajos = maxTrabajos;
	}

	public boolean isTrabajosDisponibles() {
		return maxTrabajos > getListaPonencias().size();
	}

	public long getTiempoEjecucion() {
		return tiempoEjecucion;
	}

	public void setTiempoEjecucion(long tiempoEjecucion) {
		this.tiempoEjecucion = tiempoEjecucion;
	}

	public Long getIdConvocatoria() {
		return idConvocatoria;
	}

	public void setIdConvocatoria(Long idConvocatoria) {
		this.idConvocatoria = idConvocatoria;
	}

	public UIComponent getUiMovOtraDep() {
		return uiMovOtraDep;
	}

	public void setUiMovOtraDep(UIComponent uiMovOtraDep) {
		this.uiMovOtraDep = uiMovOtraDep;
	}

	public UIComponent getUiSedeOtraDep() {
		return uiSedeOtraDep;
	}

	public void setUiSedeOtraDep(UIComponent uiSedeOtraDep) {
		this.uiSedeOtraDep = uiSedeOtraDep;
	}

	public UIComponent getUiFacOtraDep() {
		return uiFacOtraDep;
	}

	public void setUiFacOtraDep(UIComponent uiFacOtraDep) {
		this.uiFacOtraDep = uiFacOtraDep;
	}

	public SelectItem[] getSedeSolicitudItem() {
		return sedeSolicitudItem;
	}

	public void setSedeSolicitudItem(SelectItem[] sedeSolicitudItem) {
		this.sedeSolicitudItem = sedeSolicitudItem;
	}

	public SelectItem[] getFacultadItem() {
		return facultadItem;
	}

	public void setFacultadItem(SelectItem[] facultadItem) {
		this.facultadItem = facultadItem;
	}

	public String getSedeSolicitud() {
		return sedeSolicitud;
	}

	public void setSedeSolicitud(String sedeSolicitud) {
		this.sedeSolicitud = sedeSolicitud;
	}

	public String getFacultadSolicitud() {
		return facultadSolicitud;
	}

	public void setFacultadSolicitud(String facultadSolicitud) {
		this.facultadSolicitud = facultadSolicitud;
	}

	public Boolean getMostrarTipoMovilidad() {
		return mostrarTipoMovilidad;
	}

	public void setMostrarTipoMovilidad(Boolean mostrarTipoMovilidad) {
		this.mostrarTipoMovilidad = mostrarTipoMovilidad;
	}

	public Convocatoria getConvActual() {
		return convActual;
	}

	public void setConvActual(Convocatoria convActual) {
		this.convActual = convActual;
	}

}
