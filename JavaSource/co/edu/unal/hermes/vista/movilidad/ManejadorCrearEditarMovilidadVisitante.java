package co.edu.unal.hermes.vista.movilidad;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
import javax.faces.model.SelectItem;

import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.ActividadMovilidadVE;
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
import co.edu.unal.hermes.modelo.MovilidadVisitanteExterior;
import co.edu.unal.hermes.modelo.Pais;
import co.edu.unal.hermes.modelo.PalabraClave;
import co.edu.unal.hermes.modelo.Parametro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PlanEstudios;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.TipoActividadMovilidadVE;
import co.edu.unal.hermes.modelo.TipoArchivo;
import co.edu.unal.hermes.modelo.TipoArchivoMovilidad;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoMovilidad;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.correo.Correo;

public class ManejadorCrearEditarMovilidadVisitante extends ManejadorBaseMovilidad {

	private static final long serialVersionUID = -6575626233185250896L;
	private Long costoTiquetes;
	private Long costoInscripcion;
	private Long aporteFacultad;
	private Long destinado;
	private String idcreador;
	private String solDocente;
	private boolean mostrarDatosBasicos;
	private boolean mostrarDatosEvento;
	private boolean mostrarDatosEventoGrupo;
	private boolean panelNoExiste;
	private UIData tablaActividades;
	private Date fechaMinimaInicio;
	byte[] datos;
	private boolean identificacion;
	private SelectItem[] depsItem;
	private List listaDependencias;
	private String sedeSeleccionada = "";
	private String nombreSedeSeleccionada;
	private String documentoDocente;
	private String nombreArchivo;
	private String facultadSeleccionada = "";
	private String nombreFacultadSeleccionada;
	private String departamentoSeleccionado = "";
	private String nombreDepartamentoSeleccionado;
	private Persona persona;
	private String idCiudad;
	private String nombreEvento;
	private SelectItem[] institucionItem;
	private List ciudades;
	private List listaInstitucion;
	private List listaGrupos;
	private List listaIntegrantesGrupo;
	private Investigador responsableGrupo;
	private Grupo grupo;
	private String actividadSel;
	private PalabraClave palabraClave;
	private List<PalabraClave> listaPalabrasClave;

	private SelectItem[] sedeItem;
	private SelectItem[] sedeSolicitudItem;
	private SelectItem[] facultadItem;
	private String sede;
	private String sedeSolicitud;
	private String facultadSolicitud;
	private UploadedFile archivoCargar;
	private SelectItem[] tipoDocumentoItem;
	private SelectItem[] actividadSelItem;
	private TipoDocumento tipoDocumento;
	private String idgrupo;
	private String idPrograma;
	private String nombreDocente;
	private String documentoVisitante;
	private String emailVisitante;
	private String nacionalidad;
	private String paisProcedencia;
	private String institucion;
	private String ruta;
	private String cronograma;
	private String eventoDifusion;
	private String montoDiario;
	private String numeroDias;
	private Date fechaInicioEvento;
	private Date fechaFinEvento;
	private Date fechaActividad;
	private String duracionActividad;
	private String nombreLider;
	private String pMontoDiario;
	private boolean panelMasDatos;
	private boolean panelMasDatos2;
	private boolean panelErrorGrupo;
	private boolean panelEmailInvestigador;
	private boolean panelMasDatos3;
	private boolean errorArchivo;
	private boolean archivoCargado;
	private boolean errorMontoDiario;
	private boolean bImprimirReporte;
	private ActividadMovilidadVE temp;
	private InvestigadorInterno investigadorInterno;
	Long anoAnt;
	Long anoAct;
	Long mesAct;
	private ActividadMovilidadVE actividadMovilidadVESeleccionada;
	private ArchivoMovilidadVE archivoMovilidadVESeleccionado;
	private boolean puedeSubirArchivos = false;

	private MovilidadVisitanteExterior mve;

	private String tipoDocDocente;
	private String tipoDocCreador;
	// private InvestigadorInterno investigadorInterno;

	private SelectItem[] aprobacion = { new SelectItem("NO", "NO"), new SelectItem("SI", "SI") };
	private SelectItem[] tipoVisitanteItem = { new SelectItem("Investigador", "Investigador"),
			new SelectItem("Artista", "Artista") };

	private List programas;

	private List paises;

	private SelectItem[] tipoDocumentoSelItem;
	private String tipoDocumentoSel;

	private String solInscripcion;
	private String resolucionViaje;
	private String aceptacionDIB;
	private String aceptacionFacultad;
	private String aceptacionPonencia;
	private String documento;
	private String tituloPonencia;
	private String resumenPonencia;
	private String mensajeErrorActividad = "";

	private UploadedFile archivoObligatorio;
	private TipoArchivo tipoArchivo;

	private List listaTipoArchivo;
	public SelectItem[] listaActividadesItem;

	private SelectItem[] tipoArchivoItem;

	private List listaArchivos;
	private List listaArchivosObligatorios;

	private HtmlPanelGroup panelArchivos;
	private HtmlDataTable tablaArchivos;
	private HtmlDataTable tablaArchivosObligatorios;
	CorreoPlantilla correoActual = new CorreoPlantilla();
	String cuerpoCorreo = "";

	private String errores[];
	private boolean bErrorGrupo;
	private boolean bErrorPrograma;
	private boolean bErrorDocente;
	private boolean bErrorEmail;
	private boolean bErrorDocumento;
	private boolean bErrorNacionalidad;
	private boolean bErrorPais;
	private boolean bErrorInstitucion;
	private boolean bErrorRuta;
	private boolean bErrorActividades;
	private boolean bErrorEvento;
	private boolean bErrorTiquete;
	private boolean bErrorMonto;
	private boolean bErrorDias;
	private boolean bErrorDocumentos;
	private boolean bErrorHoja;
	private boolean mostrarError = false;
	private String msgError = "Su solicitud NO ha sido enviada, por favor verifique la información y vuelva a GUARDAR. Debe seleccionar SI desea confirmar el envio para enviar la movilidad o NO en caso de guardar parcialmente.";
	private boolean esConvocatoriaFacultad = false;
	
	private String noExiste;
	private String nombreObligatorio;
	private ArchivoMovilidadVE documentoSeleccionado;

	private String restriccionArchivosConv;
	private boolean restriccionLiderGrupo;
	private Long restriccionTiquetes;
	private Integer restriccionTiempoSolicitud;
	private Long idConvocatoria;
	private String estado = "";
	// para los mensajes de error
	private UIComponent uiSubmodalidadVisitante;
	private UIComponent uiProgramaAcademico;
	private UIComponent uiNombreDocente;
	private UIComponent uiEmailDocente;
	private UIComponent uiDocumentoVisitante;
	private UIComponent uiNacionalidad;
	private UIComponent uiPaisProcedencia;
	private UIComponent uiDepProcedencia;
	private UIComponent uiCiuProcedencia;
	private UIComponent uiInstitucion;
	private UIComponent uiRuta;
	private UIComponent uiFechainicio;
	private UIComponent uiFechafin;
	private UIComponent uiMovOtraDep;
	private UIComponent uiSedeOtraDep;
	private UIComponent uiFacOtraDep;
	private Long valorTotalApoyo;
	private String tipoPonenciaPar;
	private boolean mostrarSubModalidades = false;
	private SelectItem[] listaSubmodalidades;
	private String submodalidad;
	private boolean errorSubmodalidad = false;
	private String validadcionCostosSubmodalidad;

	private boolean validarGrupo = false;
	private Integer tiempoValidacionMovilidad;
	private String ciudadEvento;
	private boolean siColombia = false;
	private SelectItem[] ciudadItem; // CIUDADES A MOSTRAR
	private List listaCiudades; // LISTA DE CIUDADES DEL DEPARTAMENTO
	private Departamento departamentoActual; // DEPARTAMENTO ACTUAL
	private List listaDepartamentos; // LISTA DE DEPARTAMENTOS
	private Ciudad ciudadActual; // CIUDAD SELECCIONADA
	private SelectItem[] departamentoItem; // DEPARTAMENTOS A MOSTRAR

	private Boolean mostrarEventoMovilidad;
	private ArrayList<SelectItem> listaInstitucionesItem;
	private List<Institucion> listaInstituciones;
	private Convocatoria convActual;
	
	
	public ManejadorCrearEditarMovilidadVisitante() {
		convActual = new Convocatoria();
		Long idMovilidadEd = (Long) sesion.getAttribute("movilidadVisExtSel");

		palabraClave = new PalabraClave();
		ocultarPaneles();
		cargarValoresIniciales();
		personaActual = (Persona) sesion.getAttribute("persona");
		if (personaActual != null) {
			documento = personaActual.getId().getDocumento();
			tipoDocumento = new TipoDocumento();
			tipoDocumento.setId(personaActual.getId().getTipoDocumento());
		}

		obtenerListaDepartamentos();
		obtenerListaCiudades();
		consultarListaInstituciones();

		mve = new MovilidadVisitanteExterior();
		listaArchivos = new ArrayList();
		listaArchivosObligatorios = new ArrayList();

		if (idMovilidadEd != null) {
			mve = servicioMovilidad.obtenerMovilidadesVisExt(idMovilidadEd);
			sesion.setAttribute("idConvocatoriaActual", mve.getConvocatoria().getId());
			idConvocatoria = mve.getConvocatoria().getId();
			try {
				buscarPersona();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			if (mve.getPais() != null) {
				paisProcedencia = mve.getPais().getId();
			}

			sede = mve.getSede().getId().toString();
			
			idPrograma = mve.getIdprograma().getId().toString();
			
			if(mve.getMovilidadDirigidaOtraDependencia().equals("S")) {
				sedeSolicitud = mve.getDependencia().getSede().getId().toString();
				cargarFacultadSolcitud();
				facultadSolicitud = mve.getDependencia().getId();
			}
			
			calcularTotalesMov();

			mostrarDatosEventoGrupo = true;
			mostrarDatosEvento = true;
			panelMasDatos = true;
			panelMasDatos2 = true;
			panelMasDatos3 = true;

			if (mve.getPais() != null) {
				paisProcedencia = mve.getPais().getId();
				revisarPais();

				if (isSiColombia()) {
					if (mve.getCiudad() != null) {
						ciudadEvento = mve.getCiudad();
						String hqlDepto = "select e from Ciudad e where e.id = '" + ciudadEvento + "'";
						List<Ciudad> listaCiudad = servicioGeneral.obtenerObjetos(hqlDepto);
						Ciudad ciudadActual = listaCiudad.get(0);
						setDepartamentoActual(ciudadActual.getDepartamento());
						cambiarDepartamento();
					}
				}
			}
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

			}

			String sqlBuscaConvocatoria = "select #id e.id, #gruposCategoriaA e.gruposCategoriaA, #montoApoyoGanadores e.montoApoyoGanadores, #requisitosConvTexto e.requisitosConvTexto, #tiempoEjecucionProyecto e.tiempoEjecucionProyecto, #compromisosTexto e.compromisosTexto, #esParaGrupos e.esParaGrupos, #submodalidadConvocatoriaMovilidad e.submodalidadConvocatoriaMovilidad, #validacionCostosConvocatoriaMovilidad e.validacionCostosConvocatoriaMovilidad, #mostrarEventoMovilidad e.mostrarEventoMovilidad, #mostrarGrupoMovilidad e.mostrarGrupoMovilidad, #infoAdicionalFormulario e.infoAdicionalFormulario from Convocatoria e where e.id = "
					+ idConvocatoria + "";

			List<Convocatoria> listConvs = servicioGeneral.obtenerObjetosLimitado(Convocatoria.class,
					sqlBuscaConvocatoria);
			convActual = listConvs.get(0);
			if (convActual.getMontoApoyoGanadores() != null) {
				restriccionTiquetes = Long.parseLong(convActual.getMontoApoyoGanadores());
			}

			if (convActual.getCompromisosTexto() != null) {
				if (!convActual.getCompromisosTexto().equals("")) {
					tipoPonenciaPar = convActual.getCompromisosTexto();
				}
			}

			restriccionArchivosConv = convActual.getRequisitosConvTexto() != null ? convActual.getRequisitosConvTexto()
					: "";
			restriccionLiderGrupo = convActual.getGruposRegistrados() != null ? convActual.getGruposRegistrados()
					: false;
			restriccionTiempoSolicitud = convActual.getTiempoEjecucionProyecto();

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
				if (convActual.getEsParaGrupos().booleanValue()) {
					setValidarGrupo(true);
					idgrupo = mve.getGrupo().getId().toString();
				} else {
					setValidarGrupo(false);
				}
			} else {
				setValidarGrupo(false);
			}
			
			if (!esNulo(convActual.getMostrarGrupoMovilidad()) && convActual.getMostrarGrupoMovilidad().equals("1")) {
				setValidarGrupo(true);
				if(!esNulo(mve.getGrupo()))
					idgrupo = mve.getGrupo().getId().toString();
			}
			mostrarDatosEventoGrupo = true;

			if (convActual.getTiempoEjecucionProyecto() != null) {
				if (!convActual.getTiempoEjecucionProyecto().equals("")) {
					restriccionTiempoSolicitud = convActual.getTiempoEjecucionProyecto();
				}
			} else {
				restriccionTiempoSolicitud = 30;
			}

			if (convActual.getMostrarEventoMovilidad() != null) {
				if (convActual.getMostrarEventoMovilidad().equals("0")) {
					setMostrarEventoMovilidad(false);
				} else {
					setMostrarEventoMovilidad(true);
				}
			} else {
				setMostrarEventoMovilidad(false);
			}

			cargarPaises();
			revisarPais();
		}

		Proyecto p = (Proyecto) sesion.getAttribute("proyectoMovilidad");
		if (p != null) {
			mve.setProyectoFicha(p.getId());
		}

		temp = new ActividadMovilidadVE();

		cargarTiposDocumentos();
		calcularFechaMinimaInicio();
		cargarTiposDocumento();
		cargarTiposActividades();
		definirInicio4049();

	}

	public void definirInicio4049() {
		if (idConvocatoria.equals(897L)) {
			if (mve.getMovilidadTipo() == null
					|| (mve.getMovilidadTipo() != null && mve.getMovilidadTipo().equals("P"))) {
				restriccionTiempoSolicitud = 30;
			} else if (mve.getMovilidadTipo().equals("V")) {
				restriccionTiempoSolicitud = 15;
			}
			calcularFechaMinimaInicio();
			mve.setFechainicial(null);
			mve.setFechafinal(null);
			if (mve.getActividades() != null) {
				for (ActividadMovilidadVE act : mve.getActividades()) {
					servicioGeneral.eliminarObjeto(act);
				}
			}
			mve.getActividades().clear();
			panelMasDatos = false;
			panelMasDatos3 = false;
			archivoCargado = false;
			errorMontoDiario = false;
		}
	}
	
	public void definirInicioConvNal2022_2024() {
		if (idConvocatoria.equals(1178L)) {
			if (mve.getMovilidadTipo() == null
					|| (mve.getMovilidadTipo() != null && mve.getMovilidadTipo().equals("P"))) {
				restriccionTiempoSolicitud = 30;
			} else if (mve.getMovilidadTipo().equals("V")) {
				restriccionTiempoSolicitud = 30;
			}
			calcularFechaMinimaInicio();
			mve.setFechainicial(null);
			mve.setFechafinal(null);
			if(mve.getId() == null) {
				//mve.setActividades(null);
				mve.getActividades().clear();
			}else {
				if (mve.getActividades() != null) {
					for (ActividadMovilidadVE act : mve.getActividades()) {
						servicioGeneral.eliminarObjeto(act);
					}
					mve.getActividades().clear();
				}				
			}		
			
			/*if (mve.getActividades() != null) {
				for (ActividadMovilidadVE act : mve.getActividades()) {
					servicioGeneral.eliminarObjeto(act);
				}
			}*/
			
			//mve.getActividades().clear();
			mve.setCostoEstimuloVirtual(0L);
			calcularTotalesMov();
			panelMasDatos = false;
			panelMasDatos3 = false;
			archivoCargado = false;
			errorMontoDiario = false;
		}		
	}

	public void calcularTotalesMov() {
		Long cosT = 0L;
		Long mon = 0L;
		Long estV = 0L;
		Double numDia = (double) 0;
		if (mve.getCostotiquete() != null) {
			cosT = mve.getCostotiquete();
		}

		if (mve.getMonto() != null) {
			mon = mve.getMonto();
		}

		if (mve.getNumerodias() != null) {
			numDia = mve.getNumerodias();
		}
		if (idConvocatoria.equals(897L) || idConvocatoria.equals(1178L)) {
			if (mve.getCostoEstimuloVirtual() != null) {
				estV = mve.getCostoEstimuloVirtual();
			}
		}
		valorTotalApoyo = (cosT + estV) + (mon * numDia.longValue());
	}

	private void cargarTiposDocumentos() {
		if (listaArchivosObligatorios == null
				|| (listaArchivosObligatorios != null && listaArchivosObligatorios.size() == 0)) {
			listaArchivosObligatorios = new ArrayList();
			listaArchivosObligatorios = servicioGeneral.obtenerListaArchivosMovilidad(restriccionArchivosConv);
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
	}

	public void revisarPais() {
		if (paisProcedencia != null && !paisProcedencia.equals("") && paisProcedencia.equals("CO")) {
			setSiColombia(true);
		} else {
			setSiColombia(false);
			mve.setCiudad(null);
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

	private void cargarValoresIniciales() {
		errores = new String[15];
		bErrorGrupo = false;
		bErrorPrograma = false;
		bErrorDocente = false;
		bErrorEmail = false;
		bErrorDocumento = false;
		bErrorNacionalidad = false;
		bErrorPais = false;
		bErrorInstitucion = false;
		bErrorRuta = false;
		bErrorActividades = false;
		bErrorEvento = false;
		bErrorTiquete = false;
		bErrorMonto = false;
		bErrorDias = false;
		bErrorDocumentos = false;
		bErrorHoja = false;
		mostrarDatosBasicos = true;
		mostrarDatosEvento = false;
		errorArchivo = false;
		panelNoExiste = false;
		panelMasDatos = false;
		panelMasDatos2 = false;
		panelMasDatos3 = false;
		archivoCargado = false;
		errorMontoDiario = false;
		montoDiario = "0";
		numeroDias = "0";
		costoTiquetes = 0L;
		identificacion = true;
		panelArchivos = new HtmlPanelGroup();
		personaActual = (Persona) sesion.getAttribute("persona");
		persona = new Persona();
		idcreador = personaActual.getId().getDocumento();
		tipoDocCreador = personaActual.getId().getTipoDocumento();
		documento = new String();
		mostrarDatosEventoGrupo = false;

		cargarSede();
		cargarSedeSolicitud();
	}

	private void cargarSede() {
		List listaSede = servicioGeneral.obtenerObjetos("from Sede where id != 0 order by nombre asc");
		if (listaSede != null && listaSede.size() > 0) {
			sedeItem = new SelectItem[listaSede.size()];
			for (int i = 0; i < listaSede.size(); i++) {
				Sede sed = (Sede) listaSede.get(i);
				sedeItem[i] = new SelectItem(sed.getId().toString(), sed.getNombre());
			}
		}
		sede = "2";
		// cargarFacultad();
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

	private void reiniciarVariables() {
		errores = new String[15];
		bErrorGrupo = false;
		bErrorPrograma = false;
		bErrorDocente = false;
		bErrorEmail = false;
		bErrorDocumento = false;
		bErrorNacionalidad = false;
		bErrorPais = false;
		bErrorInstitucion = false;
		bErrorRuta = false;
		bErrorActividades = false;
		bErrorEvento = false;
		bErrorTiquete = false;
		bErrorMonto = false;
		bErrorDias = false;
		bErrorDocumentos = false;
		bErrorHoja = false;
		mostrarDatosBasicos = true;
		mostrarDatosEvento = false;
		errorArchivo = false;
		panelNoExiste = false;
		panelMasDatos = false;
		panelMasDatos2 = false;
		panelMasDatos3 = false;
		archivoCargado = false;
		errorMontoDiario = false;
		montoDiario = "0";
		numeroDias = "0";
		identificacion = true;
		mostrarError = false;
	}

	public void ocultarPaneles() {
		mostrarDatosBasicos = false;
		mostrarDatosEvento = false;
		panelMasDatos = false;
		panelMasDatos2 = false;
		panelMasDatos3 = false;
		panelNoExiste = false;
		archivoCargado = false;
		errorMontoDiario = false;
	}

	public void buscarPersona() throws SQLException {
		reiniciarVariables();
		IdPersona id = new IdPersona();
		id.setDocumento(documento);
		id.setTipoDocumento(tipoDocumento.getId());
		persona = servicioPersona.obtenerPersona(id);
		String sSql = "";

		int nExiste = 0;
		Date fechaActual = new Date();

		SimpleDateFormat spd = new SimpleDateFormat("dd");
		SimpleDateFormat spm = new SimpleDateFormat("MM");
		SimpleDateFormat spy = new SimpleDateFormat("yyyy");

		anoAct = Long.parseLong(spy.format(fechaActual));
		mesAct = Long.parseLong(spm.format(fechaActual));

		if (mesAct == 12) {
			anoAct = anoAct + 1;
		}

		anoAnt = anoAct - 1;

		sSql = " SELECT COUNT(1) " + " FROM " + 
				"HER_MOVILIDAD_VISITANTES_EXT " + " WHERE " + " MOV_ID_PER ='" +
		documento + "'" + " AND MOV_APROB = 'SI' and MOV_REALIZACION_MOVILIDAD = 'S' "
		+ " AND CON_ID = " + idConvocatoria;
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
						investigadorInterno = (InvestigadorInterno) persona;
						/*
						 * investigadorInterno = servicioPersona
						 * .obtenerInvestigadorInternoCompleto(persona .getId());
						 */
						String nombre1, nombre2, apellido1, apellido2;
						listaGrupos = new Vector();
						cargarGrupos(investigadorInterno, documento);
						if (listaGrupos.size() > 1) {
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
							nombreLider = nombre1 + " " + nombre2 + " " + apellido1 + " " + apellido2;
							documentoDocente = investigadorInterno.getId().getDocumento();
							tipoDocDocente = investigadorInterno.getId().getTipoDocumento();
							mostrarDatosPersona();
//							if (!validarGrupo) {
//								mostrarDatosEventoGrupo = true;
//							}
						} 
//						else {
//							noExiste = "El investigador no es integrante de un grupo de investigación registrado en el Sistema de Información Hermes.";
//							mostrarPanelNoExiste();
//						}
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
	}

	private void mostrarPanelNoExiste() {
		panelNoExiste = true;
	}

	private void mostrarMasDatos() {
		panelMasDatos = true;
		panelMasDatos2 = false;
		panelMasDatos3 = false;
		archivoCargado = false;
		errorMontoDiario = false;
	}

	private void mostrarMasDatos2() {
		panelMasDatos = true;
		panelMasDatos2 = true;

		if (getListaActividades() == null || getListaActividades().size() == 0) {
			panelMasDatos3 = false;
		} else {
			panelMasDatos3 = true;
		}

		archivoCargado = false;
		errorMontoDiario = false;
	}

	private void ocultarMasDatos() {
		mensajeError(uiFechainicio, "La fecha del viaje debe ser superior a la fecha actual en 30 días calendario.");
		panelMasDatos = false;
		panelMasDatos3 = false;
		archivoCargado = false;
		errorMontoDiario = false;
	}

	private void ocultarMasDatos2() {
		mensajeError(uiFechafin,
				"La fecha de finalización viaje no es válida. La fecha debe ser superior a la fecha de inicio y no mayor a 10 días de la fecha inicial.");
		panelMasDatos2 = false;

		panelMasDatos3 = false;
		errorArchivo = false;
		archivoCargado = false;
		errorMontoDiario = false;
	}

	private void errorArchivo() {
		errorArchivo = true;
		archivoCargado = false;
	}

	private void errorMontoDiario() {
		errorMontoDiario = true;
	}

	private void archivoCargo() {
		archivoCargado = true;
	}

	private void cargarProgramasPosgrado() {

		// String consulta = "select p from Programa p where p.idDependencia
		// like '" + investigadorInterno.getDependencia().getSede().getId() +
		// "%' order by p.nombre";
		// String consulta = "select p from PlanEstudios p where p.tipo in
		// (4,5,6,7) and p.id in (select prog.id from Programa prog) order by
		// p.id ";
		String consulta = "select p from PlanEstudios p where p.tipo in (3,4,5,6,7) order by p.id ";
		List listaProgramas = servicioGeneral.obtenerObjetos(consulta);

		programas = new Vector();
		SelectItem stemp = new SelectItem("0", "Seleccione un programa");
		programas.add(stemp);
		for (Iterator it = listaProgramas.iterator(); it.hasNext();) {
			PlanEstudios p = (PlanEstudios) it.next();
			String nombrePlan = "";
			if (p.getNombre() != null && p.getNombre().length() > 0) {
				boolean bandera = false;
				String ini = p.getId().substring(0, 1);
				if (ini.equals("2")) {
					nombrePlan = "Sede Bogotá - ";
					bandera = true;
				}
				if (ini.equals("3")) {
					nombrePlan = "Sede Medellín - ";
					bandera = true;
				}
				if (ini.equals("4")) {
					nombrePlan = "Sede Manizales - ";
					bandera = true;
				}
				if (ini.equals("5")) {
					nombrePlan = "Sede Palmira - ";
					bandera = true;
				}
				if (ini.equals("6")) {
					nombrePlan = "Sede Amazonia - ";
					bandera = true;
				}
				if (ini.equals("7")) {

				}
				if (ini.equals("8")) {
					nombrePlan = "Sede Caribe - ";
					bandera = true;
				}
				if (bandera) {
					nombrePlan = nombrePlan + p.getNombre();
					SelectItem s = new SelectItem(p.getId(), nombrePlan);
					programas.add(s);
				}
			}
		}
	}

	public void seleccionarTipoDocumento() {
		puedeSubirArchivos = true;
		if (tipoDocumentoSel.equals("")) {
			puedeSubirArchivos = false;
		}
	}

	public void eliminarArchivo() {
		listaArchivos.remove(tablaArchivos.getRowIndex());
	}

	public void eliminarArchivoObligatorio() {
		ArchivoMovilidadVE amv = archivoMovilidadVESeleccionado;
		mve.borrarArchivo(amv);
	}


	public void guardarArchivoMovVE(FileUploadEvent event) {
		archivoObligatorio = event.getFile();
		if (tipoDocumentoSel == null || tipoDocumentoSel.equals("")) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar un tipo de archivo", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		for (ArchivoMovilidadVE file : getListaArchivosObligatoriosSel()) {
			if (file.getTipoArchivo()!=null && file.getTipoArchivo().getId().toString().equals(getTipoDocumentoSel())) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Ya existe un documento adjunto bajo el mismo tipo.", "");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return;
			}
		}
		ArchivoMovilidadVE archivoMovilidad = new ArchivoMovilidadVE();
		archivoMovilidad = insertarArchivoMovilidadVEGenerico(1, archivoObligatorio, tipoDocumentoSel);
		mve.adicionarArchivo(archivoMovilidad);
	}

	public void validarFecha(DateSelectEvent event) throws SQLException {
		Date fechaEvento = event.getDate();
		
		if(!esNulo(fechaEvento)) {
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(fechaEvento);
			int diff = 0;
			while (cal1.before(cal2) || cal1.equals(cal2)) {
				SimpleDateFormat dia = new SimpleDateFormat("EEEE");
				SimpleDateFormat spd = new SimpleDateFormat("dd");
				SimpleDateFormat spm = new SimpleDateFormat("MM");
				SimpleDateFormat spy = new SimpleDateFormat("yyyy");
				cal1.add(Calendar.DATE, 1);
				diff++;
			}
			
			if (diff >= restriccionTiempoSolicitud) {
				mostrarMasDatos();
			} else {
				ocultarMasDatos();
			}
		} else
			ocultarMasDatos();
	}

	public void validarFechaLlegada(DateSelectEvent event) {
		Date fechaEvento = event.getDate();

		Calendar c = Calendar.getInstance();
		if (mve.getFechainicial() != null) {
			c.setTime(mve.getFechainicial());

			c.add(Calendar.DATE, 9);

			mostrarMasDatos2();
		}
		// ocultarMasDatos2();
		// if (!fechaEvento.before(this.fechaInicioEvento) &&
		// !fechaEvento.after(c.getTime())) {
		// mostrarMasDatos2();
		//
		// } else {
		// ocultarMasDatos2();
		// }

	}

	private void mostrarDatosPersona() {
		mostrarDatosBasicos = true;
		mostrarDatosEvento = true;
		cargarPaises();
		cargarInstituciones();
		cargarProgramasPosgrado();
	}

	private void cargarPaises() {
		List listaPaises = servicioGeneral.obtenerListaObjetosOrdenadosAsc(new Pais(), "nombre");
		paises = new Vector();
		for (Iterator it = listaPaises.iterator(); it.hasNext();) {
			Pais p = (Pais) it.next();
			SelectItem s = new SelectItem(p.getId(), p.getNombre());
			paises.add(s);
		}
	}

	private void cargarTiposActividades() {
		List<TipoActividadMovilidadVE> listaTipoActividad;
		if (tipoPonenciaPar != null) {
			listaTipoActividad = servicioGeneral.obtenerObjetos(TipoActividadMovilidadVE.class,
					"from TipoActividadMovilidadVE e where e.id in (" + tipoPonenciaPar + ")");
		} else {
			listaTipoActividad = servicioGeneral.obtenerObjetos(TipoActividadMovilidadVE.class,
					//"from TipoActividadMovilidadVE e where e.id in (1,2,3,12,6)");
					"from TipoActividadMovilidadVE e where e.id in (23,3,24,17,20,21)");
		}
		actividadSelItem = new SelectItem[listaTipoActividad.size()];
		for (int i = 0; i < listaTipoActividad.size(); i++) {
			TipoActividadMovilidadVE tipoActividad = listaTipoActividad.get(i);
			actividadSelItem[i] = new SelectItem(tipoActividad.getId().toString(), tipoActividad.getNombre());
		}
	}

	private void cargarTiposDocumento() {
		// List<TipoDocumento> listaTipoDocumento =
		// servicioGeneral.obtenerObjetos(TipoDocumento.class, "from
		// TipoDocumento");

		List<TipoDocumento> listaTipoDocumento = servicioGeneral.obtenerTiposDeDocumento();

		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			TipoDocumento td = (TipoDocumento) listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
		}
		tipoDocumento = (TipoDocumento) listaTipoDocumento.get(0);
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
					Grupo g = servicioGrupo.obtenerGrupoDatosBasicos(invG.getGrupo().getId());
					if (g.getEstadoGrupo().getId().equals("A")) {
						SelectItem s = new SelectItem(String.valueOf(g.getId()), g.getNombre());
						listaGrupos.add(s);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void adicionarActividad() {
		bErrorActividades = false;
		try {

			temp.setBErrorDescripcion(false);
			temp.setBErrorDuracion(false);
			temp.setBErrorFecha(false);
			if (temp.getDescripcion().equals("")) {
				temp.setBErrorDescripcion(true);
				temp.setErrorDescripcion("La descripción es obligatoria");
				bErrorActividades = true;
			} else {
				temp.setDescripcion(cortarCadena(temp.getDescripcion(), 200));
			}
			if (temp.getDuracion() == null || temp.getDuracion().intValue() == 0) {
				temp.setBErrorDuracion(true);
				temp.setErrorDuracion("La duración es obligatoria");
				bErrorActividades = true;
			}
			if (temp.getFecha() == null) {
				temp.setBErrorFecha(true);
				temp.setErrorFecha("La fecha es obligatoria");
				bErrorActividades = true;
			} else {
				if (temp.getFecha().before(mve.getFechainicial())) {
					bErrorActividades = true;
					temp.setBErrorFecha(true);

					temp.setErrorFecha("La fecha debe ser posterior a la fecha de inicio del viaje");
				} else if (temp.getFecha().after(mve.getFechafinal())) {
					bErrorActividades = true;
					temp.setBErrorFecha(true);
					temp.setErrorFecha("La fecha debe ser anterior a la fecha de fin del viaje");
				} else if (!validarFechaActividad()) {
					bErrorActividades = true;
					temp.setBErrorFecha(true);
					temp.setErrorFecha("La duración de la activida supera la fecha de finalización de la movilidad.");
				}
			}

			if (!bErrorActividades) {
				List<TipoActividadMovilidadVE> listaTipoActividad = servicioGeneral.obtenerObjetos(
						TipoActividadMovilidadVE.class,
						"from TipoActividadMovilidadVE where id ='" + actividadSel + "'");
				TipoActividadMovilidadVE sd = new TipoActividadMovilidadVE();
				sd = (TipoActividadMovilidadVE) listaTipoActividad.get(0);
				temp.setTipoActividad(sd);
				mve.adicionarActividad(temp);
				temp = new ActividadMovilidadVE();
				panelMasDatos3 = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean validarFechaActividad() {

		// if(temp.getFecha().equals(mve.getFechafinal())){
		// if(temp.getDuracion() == 1){
		// return true;
		// }else{
		// return false;
		// }
		// }else{
		//
		// }

		Date fechaActividad = temp.getFecha();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(fechaActividad);
		cal1.add(Calendar.DATE, temp.getDuracion() - 1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(mve.getFechafinal());
		if (cal1.after(cal2)) {
			return false;
		} else {
			return true;
		}

	}

	public void eliminarActividad() {
		mve.borrarActividad(actividadMovilidadVESeleccionada);
		if (mve.getActividades().size() == 0) {
			panelMasDatos3 = false;
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

	public void cargarDatosInvestigadores() {
		listaIntegrantesGrupo = new ArrayList();
		List integrantesGrupo = this.servicioGrupo.obtenerIntegrantesGrupo(grupo.getId());
		for (int i = 0; i < integrantesGrupo.size(); i++) {
			InvestigadorGrupo investigadorGrupo = (InvestigadorGrupo) integrantesGrupo.get(i);
			if (investigadorGrupo.getTipo().equals(InvestigadorGrupo.LIDER))
				responsableGrupo = investigadorGrupo.getInvestigador();
			else
				listaIntegrantesGrupo.add(investigadorGrupo.getInvestigador());
		}

	}

	public void limpiar() {
		persona = new Persona();
		documento = new String("");
		// sesion.removeAttribute("ManejadorCrearEditarMovilidadVisitante");
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

	public boolean montoDiarioDolares() {
		boolean result = true;/*
								 * List listaParametros = servicioGeneral.obtenerListaObjetos( "Parametro" );
								 * for (Iterator itParametro = listaParametros.iterator(); itParametro
								 * .hasNext();) { Parametro p = (Parametro) itParametro.next(); Long id = new
								 * Long("4"); if (p.getId().longValue() == id.longValue()) { pMontoDiario =
								 * p.getValor(); } } Long temp = new Long(montoDiario); Long temp1 = new
								 * Long(pMontoDiario);
								 * 
								 * if (temp.longValue() <= temp1.longValue()) { result = true; } else { result =
								 * false; }
								 */

		return result;
	}

	public void guardar() {
		if (this.estado.equals("")) {
			mensajeError("Por favor confirme si desea enviar la solicitud de movilidad para revisión.");
		} else {
			if (this.estado.equals("P")) {
				mve.setEstado("P");
				guardarPropuesto();
			} else {
				mve.setEstado("I");
				guardarIngresando();
			}
		}
	}

	public void guardarPropuesto() {
		errorMontoDiario = false;
		if (montoDiarioDolares()) {
			Calendar actual = Calendar.getInstance();
			Date date = actual.getTime();
			mve.setEstado("P");
			mve.setFechasolicitud(date);

			List listaTipoMovilidad = new ArrayList();
			listaTipoMovilidad = servicioGeneral.obtenerListaObjetos("TipoMovilidad where id ='A1'");
			TipoMovilidad tm = new TipoMovilidad();
			tm = (TipoMovilidad) listaTipoMovilidad.get(0);
			mve.setTipoMovilidad(tm);

			List listaSede = new ArrayList();
			listaSede = servicioGeneral.obtenerListaObjetos("Sede where id ='" + sede + "'");
			Sede sd = new Sede();
			sd = (Sede) listaSede.get(0);

			mve.setSede(sd);

			if (esConvocatoriaFacultad) {
				mve.setMovilidadConvocatoriaFacultad("S");
			} else {
				mve.setMovilidadConvocatoriaFacultad("N");
			}

			if (sesion.getAttribute("idConvocatoriaActual") != null) {
				Long idConvocatoria = (Long) sesion.getAttribute("idConvocatoriaActual");
				mve.setConvocatoriaId(idConvocatoria.toString());
			}

			if (validar(mve) && !errorMontoDiario) {
				Persona personaAux = new Persona();
				IdPersona idp = new IdPersona();

				idp.setDocumento(documentoDocente);
				idp.setTipoDocumento(tipoDocDocente);
				personaAux = servicioPersona.obtenerPersona(idp);

				mve.setPersonaInv(personaAux);

				List listaPais = new ArrayList();
				listaPais = servicioGeneral.obtenerListaObjetos("Pais where id ='" + paisProcedencia + "'");
				Pais pais = new Pais();
				pais = (Pais) listaPais.get(0);

				mve.setPais(pais);

				List listaPrograma = new ArrayList();
				listaPrograma = servicioGeneral.obtenerListaObjetos("PlanEstudios where id ='" + idPrograma + "'");
				PlanEstudios programa = new PlanEstudios();
				programa = (PlanEstudios) listaPrograma.get(0);

				mve.setIdprograma(programa);

				if (validarGrupo) {
					List listaGrupo = new ArrayList();
					listaGrupo = servicioGeneral.obtenerListaObjetos("Grupo where id ='" + idgrupo + "'");
					Grupo grupo = new Grupo();
					grupo = (Grupo) listaGrupo.get(0);
					mve.setGrupo(grupo);
				}

				if (sesion.getAttribute("idConvocatoriaActual") != null) {
					Long idConvocatoria = (Long) sesion.getAttribute("idConvocatoriaActual");
					mve.setConvocatoriaId(idConvocatoria.toString());
				}

				Dependencia dependencia = new Dependencia();
				
				if(mve.getMovilidadDirigidaOtraDependencia() != null && mve.getMovilidadDirigidaOtraDependencia().equals("S")) {
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


				mve.setDependencia(dependencia);

				List listaParametro;
				listaParametro = new ArrayList();

				listaParametro = this.servicioGeneral
						.obtenerObjetos("FROM Parametro WHERE nombre = '" + dependencia.getSede().getId() + "'");

				if (listaParametro == null || listaParametro.size() == 0) {
					mve.setAceptacion("SI");
				}

				InvestigadorInterno ii = servicioPersona.obtenerInvestigadorInternoCompleto(personaAux.getId());
				if (!Sede.SEDES_ANDINAS.contains(ii.getDependencia().getSede().getId().toString())
						&& !ii.getDependencia().getSede().getId().equals(1L)) {
					mve.setAceptacion("SI");
				}

				servicioGeneral.guardarObjeto(mve);
				
				crearHistoricoEstadoMovilidadInvestigador(mve,"Movilidad enviada por parte del docente para revisión");

				if (getListaArchivosObligatoriosSel() != null && getListaArchivosObligatoriosSel().size() > 0) {
					for (int i = 0; i < getListaArchivosObligatoriosSel().size(); i++) {
						ArchivoMovilidadVE amovAux1 = (ArchivoMovilidadVE) getListaArchivosObligatoriosSel().get(i);
						amovAux1.setMovilidad(mve);
						servicioGeneral.guardarObjeto(amovAux1);
					}
				}

				personaActual = (Persona) sesion.getAttribute("persona");
				String dirCorreoConfirmacion = personaActual.getEmail();

				correoActual = cargarPlantilla(65);
				editarCorreo(personaAux, mve);
				Correo correo = new Correo();
				correo.setOrigen(Correo.CORREO_HERMES);
				String dirCorreo = personaAux.getEmail();
				correo.adicionarDireccion(dirCorreo);
				correo.adicionarCopiaOculta(new String(personaAux.getEmail()));

				correo.adicionarCopiaOculta(dirCorreoConfirmacion);
				correo.setAsunto(correoActual.getAsunto());
				correo.setCuerpo(cuerpoCorreo);
				boolean correo2 = false;
				boolean correo1 = servicioCorreo.enviarCorreo(correo);

				List listaCorreoEncargado = new ArrayList();
				List listaParametroAux;
				listaParametroAux = new ArrayList();

				Dependencia dependenciaAux;
				dependenciaAux = new Dependencia();
				Persona personaEnvio = new Persona();

				personaEnvio = servicioPersona.obtenerInvestigadorInternoCompleto(mve.getPersonaInv().getId());

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
					} else {
						ii = servicioPersona.obtenerInvestigadorInterno(new IdPersona("19380666", "C"));
					}

					listaCorreoEncargado = new ArrayList();
					listaCorreoEncargado.add(ii);

				} else {

					listaParametroAux = this.servicioGeneral
							.obtenerObjetos("FROM Parametro WHERE nombre = '" + dependencia.getSede().getId() + "'");
					correoActual = cargarPlantilla(85);

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

				String correoEnvio = "sisii_nal@unal.edu.co";
				Persona personaActualAux2 = new Persona();

				if (listaCorreoEncargado != null && listaCorreoEncargado.size() > 0) {
					// Parametro paActual = (Parametro)
					// listaCorreoEncargado.get(0);
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

						editarCorreo(personaActualAux2, mve);
						correo = new Correo();
						correo.setOrigen(Correo.CORREO_HERMES);
						dirCorreo = correoEnvio;
						correo.adicionarDireccion(dirCorreo);
						correo.adicionarCopiaOculta(dirCorreo);
						correo.setAsunto(correoActual.getAsunto());
						correo.setCuerpo(cuerpoCorreo);
						correo2 = servicioCorreo.enviarCorreo(correo);
					}

				} else {
					correoActual = cargarPlantilla(87);
					editarCorreo(personaActualAux2, mve);
					correo = new Correo();
					correo.setOrigen(Correo.CORREO_HERMES);
					dirCorreo = correoEnvio;
					correo.adicionarDireccion(dirCorreo);
					correo.adicionarCopiaOculta(dirCorreo);
					correo.setAsunto(correoActual.getAsunto());
					correo.setCuerpo(cuerpoCorreo);
					correo2 = servicioCorreo.enviarCorreo(correo);
					servicioCorreo.enviarCorreo(correo);
				}

//				if (correo1 && correo2) {
//					noExiste = "Solicitud de movilidad guardada correctamente con el número " + mve.getId()
//							+ ". Un correo electrónico confirmando su registro se ha enviado a su cuenta de correo electrónico.";
//				} else {
//					noExiste = "Solicitud de movilidad guardada correctamente con el número " + mve.getId()
//							+ ". Ha ocurrido un error en el momento de envio del correo electrónico.";
//				}
				
				noExiste = "Solicitud de movilidad guardada correctamente, con el número " + mve.getId()
				+ ".  Se ha enviado a su correo electrónico un mensaje confirmando el registro. Si lo considera necesario comuníquese con la "
				+ "Coordinación de Investigación de la facultad o sede a la que pertenece para la revisión de la movilidad. ";
				
				/*noExiste = "La movilidad con código" + mve.getId() + "se ha guardado y enviado correctamente al usuario a través del "
						+ "sistema Hermes; sin embargo, no se ha enviado correo, les recomendamos comunicarse con "
						+ "la Coordinación de Investigación de la facultad o sede a la que pertenece para realizar "
						+ "la revisión de la movilidad. No hay necesidad de enviar nuevamente la movilidad";*/

				limpiar();
				mostrarPanelNoExiste();

				bImprimirReporte = true;
			} else {
				mostrarError = true;
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msgError, msgError);
				mve.setEstado("");
				this.estado = "";
//				servicioGeneral.guardarObjeto(mve);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		} else {
			errorMontoDiario();
		}

	}

	public void guardarIngresando() {
		errorMontoDiario = false;
		if (montoDiarioDolares()) {
			Calendar actual = Calendar.getInstance();
			Date date = actual.getTime();
			mve.setEstado("I");
			mve.setFechasolicitud(date);

			List listaTipoMovilidad = new ArrayList();
			listaTipoMovilidad = servicioGeneral.obtenerListaObjetos("TipoMovilidad where id ='A1'");
			TipoMovilidad tm = new TipoMovilidad();
			tm = (TipoMovilidad) listaTipoMovilidad.get(0);
			mve.setTipoMovilidad(tm);

			List listaSede = new ArrayList();
			listaSede = servicioGeneral.obtenerListaObjetos("Sede where id ='" + sede + "'");
			Sede sd = new Sede();
			sd = (Sede) listaSede.get(0);

			mve.setSede(sd);

			if (esConvocatoriaFacultad) {
				mve.setMovilidadConvocatoriaFacultad("S");
			} else {
				mve.setMovilidadConvocatoriaFacultad("N");
			}

			if (!errorMontoDiario) {
				Persona personaAux = new Persona();
				IdPersona idp = new IdPersona();

				idp.setDocumento(documentoDocente);
				idp.setTipoDocumento(tipoDocDocente);
				personaAux = servicioPersona.obtenerPersona(idp);

				mve.setPersonaInv(personaAux);

				List listaPais = new ArrayList();
				listaPais = servicioGeneral.obtenerListaObjetos("Pais where id ='" + paisProcedencia + "'");
				Pais pais = new Pais();
				pais = (Pais) listaPais.get(0);

				mve.setPais(pais);

				List listaPrograma = new ArrayList();
				listaPrograma = servicioGeneral.obtenerListaObjetos("PlanEstudios where id ='" + idPrograma + "'");
				PlanEstudios programa = new PlanEstudios();
				programa = (PlanEstudios) listaPrograma.get(0);

				mve.setIdprograma(programa);

				if (validarGrupo) {
					List listaGrupo = new ArrayList();
					listaGrupo = servicioGeneral.obtenerListaObjetos("Grupo where id ='" + idgrupo + "'");
					Grupo grupo = new Grupo();
					grupo = (Grupo) listaGrupo.get(0);
					mve.setGrupo(grupo);
				}

				if (sesion.getAttribute("idConvocatoriaActual") != null) {
					Long idConvocatoria = (Long) sesion.getAttribute("idConvocatoriaActual");
					mve.setConvocatoriaId(idConvocatoria.toString());
				}

				Dependencia dependencia = new Dependencia();
				
				if(mve.getMovilidadDirigidaOtraDependencia() != null && mve.getMovilidadDirigidaOtraDependencia().equals("S")) {
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

				mve.setDependencia(dependencia);

				List listaParametro;
				listaParametro = new ArrayList();

				listaParametro = this.servicioGeneral
						.obtenerObjetos("FROM Parametro WHERE nombre = '" + dependencia.getSede().getId() + "'");

				if (listaParametro == null || listaParametro.size() == 0) {
					mve.setAceptacion("SI");
				}

				servicioGeneral.guardarObjeto(mve);

				if (getListaArchivosObligatoriosSel() != null && getListaArchivosObligatoriosSel().size() > 0) {
					for (int i = 0; i < getListaArchivosObligatoriosSel().size(); i++) {
						ArchivoMovilidadVE amovAux1 = (ArchivoMovilidadVE) getListaArchivosObligatoriosSel().get(i);
						amovAux1.setMovilidad(mve);
						servicioGeneral.guardarObjeto(amovAux1);
					}
				}

				noExiste = "Solicitud de movilidad guardada correctamente con el número " + mve.getId() + ".";
				limpiar();
				mostrarPanelNoExiste();
				bImprimirReporte = true;
			} else {
				mostrarError = true;
				mve.setEstado("");
				this.estado = "";
//				servicioGeneral.guardarObjeto(mve);
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msgError, msgError);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		} else {
			errorMontoDiario();
		}

	}

	public String editarCorreo(Persona personaAux, MovilidadVisitanteExterior mov) {

		try {
			String coinvNombre = "";
			// CorreoPlantilla cp = CorreoPlantilla(57);
			// String correo = correoActual.getCuerpo().replaceAll("<<FECHA>>",
			// Fecha.fechaActual());
			String correo = correoActual.getCuerpo();

			String investigador = "";
			// if (inv != null && inv.size() > 0) {
			// Persona e = (Persona) inv.get(0);
			investigador = personaAux.getNombre1() + " " + personaAux.getApellido1() + " " + personaAux.getApellido2();
			correo = correo.replaceAll("<<INVESTIGADOR>>", investigador);
			// }

			correo = correo.replaceAll("<<IDMOVILIDAD>>", mov.getId().toString());
			correo = correo.replaceAll("<<TIPO>>", mov.getTipoMovilidad().getNombre());

			cuerpoCorreo = correo;

			// cuerpoCorreo2 = investigador + " " + coinvNombre;
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

	private boolean validar(MovilidadVisitanteExterior mov) {
		mostrarError = false;
		boolean result = true;
		bErrorGrupo = false;
		bErrorPrograma = false;
		bErrorDocente = false;
		bErrorEmail = false;
		bErrorDocumento = false;
		bErrorNacionalidad = false;
		bErrorPais = false;
		bErrorInstitucion = false;
		bErrorRuta = false;
		bErrorActividades = false;
		bErrorEvento = false;
		bErrorTiquete = false;
		bErrorMonto = false;
		bErrorDias = false;
		bErrorDocumentos = false;
		bErrorHoja = false;
		bImprimirReporte = false;

		if (!esNulo(mov.getMovilidadTipo()) && mov.getMovilidadTipo().equals("V")
				&& (mov.getCostoEstimuloVirtual() != null && mov.getCostoEstimuloVirtual() > 3511208L)) {
			mensajeError("El estímulo para modalidad virtual no puede ser mayor a $3'511.208");
			result = false;
		}

		// if (idPrograma.equals("0")) {
		// String error = "El nombre del programa es obligatorio \n";
		// errores[1] = error;
		// bErrorPrograma = true;
		// result = false;
		// }
		
		if (mov.getNombreVisitante().equals("")) {
			String error = "El nombre del docente visitante es obligatorio \n";
			errores[2] = error;
			mensajeError(error);
			bErrorDocente = true;
			result = false;
		}
		if (mov.getEmailVisitante().equals("")) {
			String error = "El email del docente visitante es obligatorio \n";
			errores[14] = error;
			mensajeError(error);
			bErrorEmail = true;
			result = false;
		}
		if (mov.getDocumentoVisitante().equals("")) {
			String error = "El documento es obligatorio \n";
			errores[3] = error;
			mensajeError(error);
			bErrorDocumento = true;
			result = false;
		}
		// if (mov.getNacionalidad().equals(""))
		// {
		// String error = "La nacionalidad es obligatoria \n";
		// errores[4] = error;
		// bErrorNacionalidad = true;
		// result = false;
		// }
		if (paisProcedencia.equals("00")) {
			String error = "El pais es obligatorio";
			errores[5] = error;
			bErrorPais = true;
			mensajeError(error);
			result = false;
		}
		if (mov.getUniversidad().equals("")) {
			String error = "La institución es obligatoria";
			errores[6] = error;
			mensajeError(error);
			bErrorInstitucion = true;
			result = false;
		}
		if (mov.getPlan().equals("")) {
			String error = "El plan es obligatorio";
			errores[7] = error;
			mensajeError(error);
			bErrorRuta = true;
			result = false;
		}

		if (!idConvocatoria.equals(1178L) && !idConvocatoria.equals(1234L)) {
			if (mostrarEventoMovilidad && mov.getEvento().equals("")) {
				String error = "El evento es obligatorio";
				errores[9] = error;
				mensajeError(error);
				bErrorEvento = true;
				result = false;
			}
		}
		
		if (mov.getCostotiquete() == null) {
			String error = "El costo de tiquete es obligatorio \n";
			errores[10] = error;
			mensajeError(error);
			bErrorTiquete = true;
			result = false;
		}

		if (mov.getMonto() == null) {
			String error = "El monto diario es obligatorio";
			errores[11] = error;
			mensajeError(error);
			bErrorMonto = true;
			result = false;
		}

		if (mov.getNumerodias() == null) {
			String error = "El número de días es obligatorio \n";
			errores[12] = error;
			mensajeError(error);
			bErrorDias = true;
			result = false;
		}

		if (mov.getMonto() != null && mov.getNumerodias() != null) {
			if ((mov.getMonto() * mov.getNumerodias()) + mov.getCostotiquete() > restriccionTiquetes) {
				String error = "El valor máximo que cubre la convocatoria es de $" + restriccionTiquetes + ". \n";
				errores[12] = error;
				mensajeError(error);
				bErrorDias = true;
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
			ArchivoMovilidadVE mva = (ArchivoMovilidadVE) getListaArchivosObligatoriosSel().get(j);
			if(!esNulo(mva)) {
				TipoArchivoMovilidad tam = mva.getTipoArchivo();
				listaTipoArchObligatoriosReg.add(tam);
			}
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

		if (mve.getDocumentoVisitante() != null && !mve.getDocumentoVisitante().equals("")) {
			if (!validarDocumentoParticipante("P", mve.getDocumentoVisitante())) {
				mensajeError(uiDocumentoVisitante, "El documento ingresado no es válido.");
				result = false;
			}
		}

		if (mve.getConvocatoria().getId().equals(897L) && esCadenaVacia(mve.getMovilidadTipo())) {
			mensajeError("", "El tipo de movilidad es obligatorio.");
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
		
		//1-Numero de veces que puede aplicar un docente en una convocatoria padre
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
		
		//2-Numero de veces que puede aplicar un docente en una convocatoria (modalidad)
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
		
		//2-Numero de veces que puede aplicar un docente en una convocatoria (modalidad)
		tipoParam = Tipos.TIPOS_PARAM_CONV_MOV_DOCENTE_CONV_ANIO;
		listaParametrosConv = servicioModalidad.obtenerParametrosConvocatorias(convocatoriaActual, tipoParam);
		
		for (ConvocatoriaParametrizacion convParam : listaParametrosConv) {
			Integer movilidadesDocente = 0;
			Long movilidadesConvocatoria = convParam.getValorParametro();
			String anioParam = convParam.getAño();
			String anioFechaInicio = obtenerAño(mve.getFechainicial());
			
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
		
		return true;
	}
	
	public Dependencia obtenerDependenciaMovilidad(Persona personaAux) {
		Dependencia dependencia = new Dependencia();
		
		if(mve.getMovilidadDirigidaOtraDependencia() != null && mve.getMovilidadDirigidaOtraDependencia().equals("S")) {
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

	public void enviarCorreo() {
		Correo correo = new Correo();
		correo.adicionarDireccion("iabohorquezc@unal.edu.co");
		correo.setOrigen(personaActual.getEmail());
		correo.adicionarCopiaOculta(new String(personaActual.getEmail()));
		String asunto = "Nueva movilidad de visitante internacional";

		correo.setAsunto(asunto);// getCorreoActual().getAsunto());//"Quiere ser
									// posible evaluador");
		String cuerpo = "Se ha adicionado una nueva movilidad para visitante internacional.";
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm.ss");

		Calendar actual = Calendar.getInstance();
		Date date = actual.getTime();
		String strDate = formatter.format(date);
		cuerpo = cuerpo + strDate;
		correo.setCuerpo(cuerpo);// "por favoooooooor, le pagamos y todo");
		String mensajeCorreo;
		/*
		 * if(servicioCorreo.enviarCorreo(correo)){ mensajeCorreo =
		 * "Su correo ha sido enviado con exito al posible evaluador con copia a "
		 * +persona.getEmail(); }else{ mensajeCorreo =
		 * "No se pudo enviar el correo, por favor verifique la dirección electronica" ;
		 * }
		 */
		// System.out.println(mensajeCorreo);
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

	private void consultarListaInstituciones() {
		try {
			listaInstituciones = servicioGeneral.obtenerObjetosLimitado(Institucion.class,
					"select #id ins.id, #nombre ins.nombre  " + "from Institucion ins " + "order by ins.nombre asc");

			setListaInstitucionesItem(new ArrayList<SelectItem>());
			for (int i = 0; i < listaInstituciones.size(); i++) {
				Institucion institucion = (Institucion) listaInstituciones.get(i);
				getListaInstitucionesItem().add(new SelectItem(institucion.getId(), institucion.getNombre()));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Long getDestinado() {
		return destinado;
	}

	public void setDestinado(Long destinado) {
		this.destinado = destinado;
	}

	public boolean isMostrarDatosBasicos() {
		return mostrarDatosBasicos;
	}

	public void setMostrarDatosBasicos(boolean mostrarDatosBasicos) {
		this.mostrarDatosBasicos = mostrarDatosBasicos;
	}

	public boolean isIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(boolean identificacion) {
		this.identificacion = identificacion;
	}

	public SelectItem[] getDepsItem() {
		return depsItem;
	}

	public void setDepsItem(SelectItem[] depsItem) {
		this.depsItem = depsItem;
	}

	public List getListaDependencias() {
		return listaDependencias;
	}

	public void setListaDependencias(List listaDependencias) {
		this.listaDependencias = listaDependencias;
	}

	public String getSedeSeleccionada() {
		return sedeSeleccionada;
	}

	public void setSedeSeleccionada(String sedeSeleccionada) {
		this.sedeSeleccionada = sedeSeleccionada;
	}

	public String getNombreSedeSeleccionada() {
		return nombreSedeSeleccionada;
	}

	public void setNombreSedeSeleccionada(String nombreSedeSeleccionada) {
		this.nombreSedeSeleccionada = nombreSedeSeleccionada;
	}

	public String getFacultadSeleccionada() {
		return facultadSeleccionada;
	}

	public void setFacultadSeleccionada(String facultadSeleccionada) {
		this.facultadSeleccionada = facultadSeleccionada;
	}

	public String getNombreFacultadSeleccionada() {
		return nombreFacultadSeleccionada;
	}

	public void setNombreFacultadSeleccionada(String nombreFacultadSeleccionada) {
		this.nombreFacultadSeleccionada = nombreFacultadSeleccionada;
	}

	public String getDepartamentoSeleccionado() {
		return departamentoSeleccionado;
	}

	public void setDepartamentoSeleccionado(String departamentoSeleccionado) {
		this.departamentoSeleccionado = departamentoSeleccionado;
	}

	public String getNombreDepartamentoSeleccionado() {
		return nombreDepartamentoSeleccionado;
	}

	public void setNombreDepartamentoSeleccionado(String nombreDepartamentoSeleccionado) {
		this.nombreDepartamentoSeleccionado = nombreDepartamentoSeleccionado;
	}

	public SelectItem[] getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}

	public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
		this.tipoDocumentoItem = tipoDocumentoItem;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public String getIdCiudad() {
		return idCiudad;
	}

	public void setIdCiudad(String idCiudad) {
		this.idCiudad = idCiudad;
	}

	public SelectItem[] getInstitucionItem() {
		return institucionItem;
	}

	public void setInstitucionItem(SelectItem[] institucionItem) {
		this.institucionItem = institucionItem;
	}

	public List getCiudades() {
		return ciudades;
	}

	public void setCiudades(List ciudades) {
		this.ciudades = ciudades;
	}

	public List getListaInstitucion() {
		return listaInstitucion;
	}

	public void setListaInstitucion(List listaInstitucion) {
		this.listaInstitucion = listaInstitucion;
	}

	public List getListaGrupos() {
		return listaGrupos;
	}

	public void setListaGrupos(List listaGrupos) {
		this.listaGrupos = listaGrupos;
	}

	public String getIdgrupo() {
		return idgrupo;
	}

	public void setIdgrupo(String idgrupo) {
		this.idgrupo = idgrupo;
	}

	public String getNombreEvento() {
		return nombreEvento;
	}

	public void setNombreEvento(String nombreEvento) {
		this.nombreEvento = nombreEvento;
	}

	public List getListaIntegrantesGrupo() {
		return listaIntegrantesGrupo;
	}

	public void setListaIntegrantesGrupo(List listaIntegrantesGrupo) {
		this.listaIntegrantesGrupo = listaIntegrantesGrupo;
	}

	public Investigador getResponsableGrupo() {
		return responsableGrupo;
	}

	public void setResponsableGrupo(Investigador responsableGrupo) {
		this.responsableGrupo = responsableGrupo;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public SelectItem[] getAprobacion() {
		return aprobacion;
	}

	public void setAprobacion(SelectItem[] aprobacion) {
		this.aprobacion = aprobacion;
	}

	public Long getCostoTiquetes() {
		return costoTiquetes;
	}

	public void setCostoTiquetes(Long costoTiquetes) {
		this.costoTiquetes = costoTiquetes;
	}

	public Long getCostoInscripcion() {
		return costoInscripcion;
	}

	public void setCostoInscripcion(Long costoInscripcion) {
		this.costoInscripcion = costoInscripcion;
	}

	public String getSolinscripcion() {
		return solInscripcion;
	}

	public void setSolinscripcion(String solInscripcion) {
		this.solInscripcion = solInscripcion;
	}

	public Long getAporteFacultad() {
		return aporteFacultad;
	}

	public void setAporteFacultad(Long aporteFacultad) {
		this.aporteFacultad = aporteFacultad;
	}

	public String getSolDocente() {
		return solDocente;
	}

	public void setSolDocente(String solDocente) {
		this.solDocente = solDocente;
	}

	public String getSolInscripcion() {
		return solInscripcion;
	}

	public void setSolInscripcion(String solInscripcion) {
		this.solInscripcion = solInscripcion;
	}

	public String getTituloPonencia() {
		return tituloPonencia;
	}

	public void setTituloPonencia(String tituloPonencia) {
		this.tituloPonencia = tituloPonencia;
	}

	public String getResumenPonencia() {
		return resumenPonencia;
	}

	public void setResumenPonencia(String resumenPonencia) {
		this.resumenPonencia = resumenPonencia;
	}

	public String getIdPais() {
		return paisProcedencia;
	}

	public void setIdPais(String idPais) {
		this.paisProcedencia = idPais;
	}

	public List getPaises() {
		return paises;
	}

	public void setPaises(List paises) {
		this.paises = paises;
	}

	public String getResolucionViaje() {
		return resolucionViaje;
	}

	public void setResolucionViaje(String resolucionViaje) {
		this.resolucionViaje = resolucionViaje;
	}

	public String getAceptacionDIB() {
		return aceptacionDIB;
	}

	public void setAceptacionDIB(String aceptacionDIB) {
		this.aceptacionDIB = aceptacionDIB;
	}

	public String getAceptacionPonencia() {
		return aceptacionPonencia;
	}

	public void setAceptacionPonencia(String aceptacionPonencia) {
		this.aceptacionPonencia = aceptacionPonencia;
	}

	/*
	 * public UploadedFile getArchivo() { return archivo; }
	 * 
	 * public void setArchivo(UploadedFile archivo) { this.archivo = archivo; }
	 */

	public TipoArchivo getTipoArchivo() {
		return tipoArchivo;
	}

	public void setTipoArchivo(TipoArchivo tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}

	public List getListaTipoArchivo() {
		return listaTipoArchivo;
	}

	public void setListaTipoArchivo(List listaTipoArchivo) {
		this.listaTipoArchivo = listaTipoArchivo;
	}

	public SelectItem[] getTipoArchivoItem() {
		return tipoArchivoItem;
	}

	public void setTipoArchivoItem(SelectItem[] tipoArchivoItem) {
		this.tipoArchivoItem = tipoArchivoItem;
	}

	public List getListaArchivos() {
		return listaArchivos;
	}

	public void setListaArchivos(List listaArchivos) {
		this.listaArchivos = listaArchivos;
	}

	public HtmlPanelGroup getPanelArchivos() {
		return panelArchivos;
	}

	public void setPanelArchivos(HtmlPanelGroup panelArchivos) {
		this.panelArchivos = panelArchivos;
	}

	public HtmlDataTable getTablaArchivos() {
		return tablaArchivos;
	}

	public void setTablaArchivos(HtmlDataTable tablaArchivos) {
		this.tablaArchivos = tablaArchivos;
	}

	public List getProgramas() {
		return programas;
	}

	public void setProgramas(List programas) {
		this.programas = programas;
	}

	public String getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(String idPrograma) {
		this.idPrograma = idPrograma;
	}

	public String getNombreDocente() {
		return nombreDocente;
	}

	public void setNombreDocente(String nombreDocente) {
		this.nombreDocente = nombreDocente;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getPaisProcedencia() {
		return paisProcedencia;
	}

	public void setPaisProcedencia(String paisProcedencia) {
		this.paisProcedencia = paisProcedencia;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getCronograma() {
		return cronograma;
	}

	public void setCronograma(String cronograma) {
		this.cronograma = cronograma;
	}

	public String getEventoDifusion() {
		return eventoDifusion;
	}

	public void setEventoDifusion(String eventoDifusion) {
		this.eventoDifusion = eventoDifusion;
	}

	public String getMontoDiario() {
		return montoDiario;
	}

	public void setMontoDiario(String montoDiario) {
		this.montoDiario = montoDiario;
	}

	public String getNumeroDias() {
		return numeroDias;
	}

	public void setNumeroDias(String numeroDias) {
		this.numeroDias = numeroDias;
	}

	public String getAceptacionFacultad() {
		return aceptacionFacultad;
	}

	public void setAceptacionFacultad(String aceptacionFacultad) {
		this.aceptacionFacultad = aceptacionFacultad;
	}

	public String getDocumentoVisitante() {
		return documentoVisitante;
	}

	public void setDocumentoVisitante(String documentoVisitante) {
		this.documentoVisitante = documentoVisitante;
	}

	public Date getFechaInicioEvento() {
		return fechaInicioEvento;
	}

	public void setFechaInicioEvento(Date fechaInicioEvento) {
		this.fechaInicioEvento = fechaInicioEvento;
	}

	public Date getFechaFinEvento() {
		return fechaFinEvento;
	}

	public void setFechaFinEvento(Date fechaFinEvento) {
		this.fechaFinEvento = fechaFinEvento;
	}

	public Date getFechaActividad() {
		return fechaActividad;
	}

	public void setFechaActividad(Date fechaActividad) {
		this.fechaActividad = fechaActividad;
	}

	public String getDuracionActividad() {
		return duracionActividad;
	}

	public void setDuracionActividad(String duracionActividad) {
		this.duracionActividad = duracionActividad;
	}

	public boolean isMostrarDatosEvento() {
		return mostrarDatosEvento;
	}

	public void setMostrarDatosEvento(boolean mostrarDatosEvento) {
		this.mostrarDatosEvento = mostrarDatosEvento;
	}

	public boolean isPanelNoExiste() {
		return panelNoExiste;
	}

	public void setPanelNoExiste(boolean panelNoExiste) {
		this.panelNoExiste = panelNoExiste;
	}

	public String getDocumentoDocente() {
		return documentoDocente;
	}

	public void setDocumentoDocente(String documentoDocente) {
		this.documentoDocente = documentoDocente;
	}

	public String getNombreLider() {
		return nombreLider;
	}

	public void setNombreLider(String nombreLider) {
		this.nombreLider = nombreLider;
	}

	public boolean isPanelMasDatos() {
		return panelMasDatos;
	}

	public void setPanelMasDatos(boolean panelMasDatos) {
		this.panelMasDatos = panelMasDatos;
	}

	public List<ActividadMovilidadVE> getListaActividades() {
		List<ActividadMovilidadVE> listaActividades = mve.getListaActividades();
		return listaActividades;
	}

	public String getMensajeErrorActividad() {
		return mensajeErrorActividad;
	}

	public void setMensajeErrorActividad(String mensajeErrorActividad) {
		this.mensajeErrorActividad = mensajeErrorActividad;
	}

	public UIData getTablaActividades() {
		return tablaActividades;
	}

	public void setTablaActividades(UIData tablaActividades) {
		this.tablaActividades = tablaActividades;
	}

	public SelectItem[] getListaActividadesItem() {
		return listaActividadesItem;
	}

	public void setListaActividadesItem(SelectItem[] listaActividadesItem) {
		this.listaActividadesItem = listaActividadesItem;
	}

	public boolean isPanelMasDatos2() {
		return panelMasDatos2;
	}

	public void setPanelMasDatos2(boolean panelMasDatos2) {
		this.panelMasDatos2 = panelMasDatos2;
	}

	public byte[] getDatos() {
		return datos;
	}

	public void setDatos(byte[] datos) {
		this.datos = datos;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public boolean isErrorArchivo() {
		return errorArchivo;
	}

	public void setErrorArchivo(boolean errorArchivo) {
		this.errorArchivo = errorArchivo;
	}

	public boolean isArchivoCargado() {
		return archivoCargado;
	}

	public void setArchivoCargado(boolean archivoCargado) {
		this.archivoCargado = archivoCargado;
	}

	public String getPMontoDiario() {
		return pMontoDiario;
	}

	public void setPMontoDiario(String montoDiario) {
		pMontoDiario = montoDiario;
	}

	public boolean isErrorMontoDiario() {
		return errorMontoDiario;
	}

	public void setErrorMontoDiario(boolean errorMontoDiario) {
		this.errorMontoDiario = errorMontoDiario;
	}

	public String getIdcreador() {
		return idcreador;
	}

	public void setIdcreador(String idcreador) {
		this.idcreador = idcreador;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
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

	public boolean isBErrorGrupo() {
		return bErrorGrupo;
	}

	public void setBErrorGrupo(boolean errorGrupo) {
		bErrorGrupo = errorGrupo;
	}

	public boolean isBErrorPrograma() {
		return bErrorPrograma;
	}

	public void setBErrorPrograma(boolean errorPrograma) {
		bErrorPrograma = errorPrograma;
	}

	public boolean isBErrorDocente() {
		return bErrorDocente;
	}

	public void setBErrorDocente(boolean errorDocente) {
		bErrorDocente = errorDocente;
	}

	public boolean isBErrorDocumento() {
		return bErrorDocumento;
	}

	public void setBErrorDocumento(boolean errorDocumento) {
		bErrorDocumento = errorDocumento;
	}

	public boolean isBErrorNacionalidad() {
		return bErrorNacionalidad;
	}

	public void setBErrorNacionalidad(boolean errorNacionalidad) {
		bErrorNacionalidad = errorNacionalidad;
	}

	public boolean isBErrorPais() {
		return bErrorPais;
	}

	public void setBErrorPais(boolean errorPais) {
		bErrorPais = errorPais;
	}

	public boolean isBErrorInstitucion() {
		return bErrorInstitucion;
	}

	public void setBErrorInstitucion(boolean errorInstitucion) {
		bErrorInstitucion = errorInstitucion;
	}

	public boolean isBErrorRuta() {
		return bErrorRuta;
	}

	public void setBErrorRuta(boolean errorRuta) {
		bErrorRuta = errorRuta;
	}

	public boolean isBErrorActividades() {
		return bErrorActividades;
	}

	public void setBErrorActividades(boolean errorActividades) {
		bErrorActividades = errorActividades;
	}

	public boolean isBErrorEvento() {
		return bErrorEvento;
	}

	public void setBErrorEvento(boolean errorEvento) {
		bErrorEvento = errorEvento;
	}

	public boolean isBErrorTiquete() {
		return bErrorTiquete;
	}

	public void setBErrorTiquete(boolean errorTiquete) {
		bErrorTiquete = errorTiquete;
	}

	public boolean isBErrorMonto() {
		return bErrorMonto;
	}

	public void setBErrorMonto(boolean errorMonto) {
		bErrorMonto = errorMonto;
	}

	public boolean isBErrorDias() {
		return bErrorDias;
	}

	public void setBErrorDias(boolean errorDias) {
		bErrorDias = errorDias;
	}

	public boolean isBErrorDocumentos() {
		return bErrorDocumentos;
	}

	public void setBErrorDocumentos(boolean errorDocumentos) {
		bErrorDocumentos = errorDocumentos;
	}

	public boolean isBErrorHoja() {
		return bErrorHoja;
	}

	public void setBErrorHoja(boolean errorHoja) {
		bErrorHoja = errorHoja;
	}

	public String getNoExiste() {
		return noExiste;
	}

	public void setNoExiste(String noExiste) {
		this.noExiste = noExiste;
	}

	public boolean isBImprimirReporte() {
		return bImprimirReporte;
	}

	public void setBImprimirReporte(boolean imprimirReporte) {
		bImprimirReporte = imprimirReporte;
	}

	public MovilidadVisitanteExterior getMve() {
		return mve;
	}

	public void setMve(MovilidadVisitanteExterior mve) {
		this.mve = mve;
	}

	public SelectItem[] getSedeItem() {
		return sedeItem;
	}

	public void setSedeItem(SelectItem[] sedeItem) {
		this.sedeItem = sedeItem;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	public UploadedFile getArchivoCargar() {
		return archivoCargar;
	}

	public void setArchivoCargar(UploadedFile archivoCargar) {
		this.archivoCargar = archivoCargar;
	}

	public List getListaArchivosObligatorios() {
		return listaArchivosObligatorios;
	}

	public void setListaArchivosObligatorios(List listaArchivosObligatorios) {
		this.listaArchivosObligatorios = listaArchivosObligatorios;
	}

	public HtmlDataTable getTablaArchivosObligatorios() {
		return tablaArchivosObligatorios;
	}

	public void setTablaArchivosObligatorios(HtmlDataTable tablaArchivosObligatorios) {
		this.tablaArchivosObligatorios = tablaArchivosObligatorios;
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

	public List<ArchivoMovilidadVE> getListaArchivosObligatoriosSel() {
		List<ArchivoMovilidadVE> listaArchivos = mve.getListaArchivo();
		return listaArchivos;
	}

	public String getActividadSel() {
		return actividadSel;
	}

	public void setActividadSel(String actividadSel) {
		this.actividadSel = actividadSel;
	}

	public SelectItem[] getActividadSelItem() {
		return actividadSelItem;
	}

	public void setActividadSelItem(SelectItem[] actividadSelItem) {
		this.actividadSelItem = actividadSelItem;
	}

	public ActividadMovilidadVE getTemp() {
		return temp;
	}

	public void setTemp(ActividadMovilidadVE temp) {
		this.temp = temp;
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

	public boolean isPanelMasDatos3() {
		return panelMasDatos3;
	}

	public void setPanelMasDatos3(boolean panelMasDatos3) {
		this.panelMasDatos3 = panelMasDatos3;
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

	public boolean isMostrarDatosEventoGrupo() {
		return mostrarDatosEventoGrupo;
	}

	public void setMostrarDatosEventoGrupo(boolean mostrarDatosEventoGrupo) {
		this.mostrarDatosEventoGrupo = mostrarDatosEventoGrupo;
	}

	public boolean isPanelErrorGrupo() {
		return panelErrorGrupo;
	}

	public void setPanelErrorGrupo(boolean panelErrorGrupo) {
		this.panelErrorGrupo = panelErrorGrupo;
	}

	/**
	 * @return the actividadMovilidadVESeleccionada
	 */
	public ActividadMovilidadVE getActividadMovilidadVESeleccionada() {
		return actividadMovilidadVESeleccionada;
	}

	/**
	 * @param actividadMovilidadVESeleccionada the actividadMovilidadVESeleccionada
	 *                                         to set
	 */
	public void setActividadMovilidadVESeleccionada(ActividadMovilidadVE actividadMovilidadVESeleccionada) {
		this.actividadMovilidadVESeleccionada = actividadMovilidadVESeleccionada;
	}

	/**
	 * @return the archivoMovilidadVESeleccionado
	 */
	public ArchivoMovilidadVE getArchivoMovilidadVESeleccionado() {
		return archivoMovilidadVESeleccionado;
	}

	/**
	 * @param archivoMovilidadVESeleccionado the archivoMovilidadVESeleccionado to
	 *                                       set
	 */
	public void setArchivoMovilidadVESeleccionado(ArchivoMovilidadVE archivoMovilidadVESeleccionado) {
		this.archivoMovilidadVESeleccionado = archivoMovilidadVESeleccionado;
	}

	/**
	 * @return the puedeSubirArchivos
	 */
	public boolean isPuedeSubirArchivos() {
		return puedeSubirArchivos;
	}

	/**
	 * @param puedeSubirArchivos the puedeSubirArchivos to set
	 */
	public void setPuedeSubirArchivos(boolean puedeSubirArchivos) {
		this.puedeSubirArchivos = puedeSubirArchivos;
	}

	public void setPalabraClave(PalabraClave palabraClave) {
		this.palabraClave = palabraClave;
	}

	public PalabraClave getPalabraClave() {
		return palabraClave;
	}

	public List obtenerPalabraClavesSugeridas(String nombre) {
		return servicioGeneral.obtenerPalabrasClaveEmpezandoCon(nombre);
	}

	public void insertarPalabraClave() {

		// System.out.println("inserta " + palabraClave.getPalabra());
		if ((!palabraClave.getPalabra().equals(""))) {
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
				// listaPalabrasClave.add(pc);
				mve.adicionarPalabraClave(pc);
				palabraClave.setPalabra("");
				pc1 = null;
				pc = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		palabraClave = new PalabraClave();
	}

	public void setListaPalabrasClave(List<PalabraClave> listaPalabrasClave) {
		this.listaPalabrasClave = listaPalabrasClave;
	}

	public List<PalabraClave> getListaPalabrasClave() {
		return listaPalabrasClave;
	}

	public void eliminarPalabraClave() {
		mve.borrarPalabraClave(this.palabraClave);
		// listaPalabrasClave.remove(this.palabraClave);
	}

	public String getEmailVisitante() {
		return emailVisitante;
	}

	public void setEmailVisitante(String emailVisitante) {
		this.emailVisitante = emailVisitante;
	}

	public boolean isPanelEmailInvestigador() {
		return panelEmailInvestigador;
	}

	public void setPanelEmailInvestigador(boolean panelEmailInvestigador) {
		this.panelEmailInvestigador = panelEmailInvestigador;
	}

	public boolean isbErrorEmail() {
		return bErrorEmail;
	}

	public void setbErrorEmail(boolean bErrorEmail) {
		this.bErrorEmail = bErrorEmail;
	}

	public boolean isEsConvocatoriaFacultad() {
		return esConvocatoriaFacultad;
	}

	public void setEsConvocatoriaFacultad(boolean esConvocatoriaFacultad) {
		this.esConvocatoriaFacultad = esConvocatoriaFacultad;
	}

	public void calcularFechaMinimaInicio() {
		Calendar fechaActual = Calendar.getInstance();
		fechaActual.add(Calendar.DATE, restriccionTiempoSolicitud);
		fechaMinimaInicio = fechaActual.getTime();
	}

	public Date getFechaMinimaInicio() {
		return fechaMinimaInicio;
	}

	public void setFechaMinimaInicio(Date fechaMinimaInicio) {
		this.fechaMinimaInicio = fechaMinimaInicio;
	}

	public void descargarArchivoMovVE() {
		ArchivoMovilidadVE archivo = documentoSeleccionado;
		descargarArchivoMovilidadVEGenerico(archivo.getId());
	}

	public String getRestriccionArchivosConv() {
		return restriccionArchivosConv;
	}

	public void setRestriccionArchivosConv(String restriccionArchivosConv) {
		this.restriccionArchivosConv = restriccionArchivosConv;
	}

	public boolean isRestriccionLiderGrupo() {
		return restriccionLiderGrupo;
	}

	public void setRestriccionLiderGrupo(boolean restriccionLiderGrupo) {
		this.restriccionLiderGrupo = restriccionLiderGrupo;
	}

	public Long getRestriccionTiquetes() {
		return restriccionTiquetes;
	}

	public void setRestriccionTiquetes(Long restriccionTiquetes) {
		this.restriccionTiquetes = restriccionTiquetes;
	}

	public boolean isbErrorGrupo() {
		return bErrorGrupo;
	}

	public void setbErrorGrupo(boolean bErrorGrupo) {
		this.bErrorGrupo = bErrorGrupo;
	}

	/**
	 * @return the idConvocatoria
	 */
	public Long getIdConvocatoria() {
		return idConvocatoria;
	}

	/**
	 * @param idConvocatoria the idConvocatoria to set
	 */
	public void setIdConvocatoria(Long idConvocatoria) {
		this.idConvocatoria = idConvocatoria;
	}

	/**
	 * @return the documentoSeleccionado
	 */
	public ArchivoMovilidadVE getDocumentoSeleccionado() {
		return documentoSeleccionado;
	}

	/**
	 * @param documentoSeleccionado the documentoSeleccionado to set
	 */
	public void setDocumentoSeleccionado(ArchivoMovilidadVE documentoSeleccionado) {
		this.documentoSeleccionado = documentoSeleccionado;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public UIComponent getUiSubmodalidadVisitante() {
		return uiSubmodalidadVisitante;
	}

	public void setUiSubmodalidadVisitante(UIComponent uiSubmodalidadVisitante) {
		this.uiSubmodalidadVisitante = uiSubmodalidadVisitante;
	}

	public UIComponent getUiProgramaAcademico() {
		return uiProgramaAcademico;
	}

	public void setUiProgramaAcademico(UIComponent uiProgramaAcademico) {
		this.uiProgramaAcademico = uiProgramaAcademico;
	}

	public UIComponent getUiNombreDocente() {
		return uiNombreDocente;
	}

	public void setUiNombreDocente(UIComponent uiNombreDocente) {
		this.uiNombreDocente = uiNombreDocente;
	}

	public UIComponent getUiEmailDocente() {
		return uiEmailDocente;
	}

	public void setUiEmailDocente(UIComponent uiEmailDocente) {
		this.uiEmailDocente = uiEmailDocente;
	}

	public UIComponent getUiDocumentoVisitante() {
		return uiDocumentoVisitante;
	}

	public void setUiDocumentoVisitante(UIComponent uiDocumentoVisitante) {
		this.uiDocumentoVisitante = uiDocumentoVisitante;
	}

	public UIComponent getUiNacionalidad() {
		return uiNacionalidad;
	}

	public void setUiNacionalidad(UIComponent uiNacionalidad) {
		this.uiNacionalidad = uiNacionalidad;
	}

	public UIComponent getUiPaisProcedencia() {
		return uiPaisProcedencia;
	}

	public void setUiPaisProcedencia(UIComponent uiPaisProcedencia) {
		this.uiPaisProcedencia = uiPaisProcedencia;
	}

	public UIComponent getUiInstitucion() {
		return uiInstitucion;
	}

	public void setUiInstitucion(UIComponent uiInstitucion) {
		this.uiInstitucion = uiInstitucion;
	}

	public UIComponent getUiRuta() {
		return uiRuta;
	}

	public void setUiRuta(UIComponent uiRuta) {
		this.uiRuta = uiRuta;
	}

	public UIComponent getUiFechainicio() {
		return uiFechainicio;
	}

	public void setUiFechainicio(UIComponent uiFechainicio) {
		this.uiFechainicio = uiFechainicio;
	}

	public UIComponent getUiFechafin() {
		return uiFechafin;
	}

	public void setUiFechafin(UIComponent uiFechafin) {
		this.uiFechafin = uiFechafin;
	}

	public SelectItem[] getTipoVisitanteItem() {
		return tipoVisitanteItem;
	}

	public void setTipoVisitanteItem(SelectItem[] tipoVisitanteItem) {
		this.tipoVisitanteItem = tipoVisitanteItem;
	}

	public Integer getRestriccionTiempoSolicitud() {
		return restriccionTiempoSolicitud;
	}

	public void setRestriccionTiempoSolicitud(Integer restriccionTiempoSolicitud) {
		this.restriccionTiempoSolicitud = restriccionTiempoSolicitud;
	}

	public Long getValorTotalApoyo() {
		return valorTotalApoyo;
	}

	public void setValorTotalApoyo(Long valorTotalApoyo) {
		this.valorTotalApoyo = valorTotalApoyo;
	}

	public String getTipoPonenciaPar() {
		return tipoPonenciaPar;
	}

	public void setTipoPonenciaPar(String tipoPonenciaPar) {
		this.tipoPonenciaPar = tipoPonenciaPar;
	}

	public boolean isMostrarSubModalidades() {
		return mostrarSubModalidades;
	}

	public void setMostrarSubModalidades(boolean mostrarSubModalidades) {
		this.mostrarSubModalidades = mostrarSubModalidades;
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

	public boolean isValidarGrupo() {
		return validarGrupo;
	}

	public void setValidarGrupo(boolean validarGrupo) {
		this.validarGrupo = validarGrupo;
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

	public UIComponent getUiDepProcedencia() {
		return uiDepProcedencia;
	}

	public void setUiDepProcedencia(UIComponent uiDepProcedencia) {
		this.uiDepProcedencia = uiDepProcedencia;
	}

	public boolean isSiColombia() {
		return siColombia;
	}

	public void setSiColombia(boolean siColombia) {
		this.siColombia = siColombia;
	}

	public UIComponent getUiCiuProcedencia() {
		return uiCiuProcedencia;
	}

	public void setUiCiuProcedencia(UIComponent uiCiuProcedencia) {
		this.uiCiuProcedencia = uiCiuProcedencia;
	}

	public Boolean getMostrarEventoMovilidad() {
		return mostrarEventoMovilidad;
	}

	public void setMostrarEventoMovilidad(Boolean mostrarEventoMovilidad) {
		this.mostrarEventoMovilidad = mostrarEventoMovilidad;
	}

	public ArrayList<SelectItem> getListaInstitucionesItem() {
		return listaInstitucionesItem;
	}

	public void setListaInstitucionesItem(ArrayList<SelectItem> listaInstitucionesItem) {
		this.listaInstitucionesItem = listaInstitucionesItem;
	}

	public SelectItem[] getSedeSolicitudItem() {
		return sedeSolicitudItem;
	}

	public void setSedeSolicitudItem(SelectItem[] sedeSolicitudItem) {
		this.sedeSolicitudItem = sedeSolicitudItem;
	}

	public String getSedeSolicitud() {
		return sedeSolicitud;
	}

	public void setSedeSolicitud(String sedeSolicitud) {
		this.sedeSolicitud = sedeSolicitud;
	}

	public SelectItem[] getFacultadItem() {
		return facultadItem;
	}

	public void setFacultadItem(SelectItem[] facultadItem) {
		this.facultadItem = facultadItem;
	}

	public String getFacultadSolicitud() {
		return facultadSolicitud;
	}

	public void setFacultadSolicitud(String facultadSolicitud) {
		this.facultadSolicitud = facultadSolicitud;
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

	public Convocatoria getConvActual() {
		return convActual;
	}

	public void setConvActual(Convocatoria convActual) {
		this.convActual = convActual;
	}
}
