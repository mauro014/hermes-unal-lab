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
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.dao.DataIntegrityViolationException;

import co.edu.unal.hermes.modelo.ActividadMovilidad;
import co.edu.unal.hermes.modelo.ArchivoMovilidad;
import co.edu.unal.hermes.modelo.ArchivoMovilidadDE;
import co.edu.unal.hermes.modelo.ArchivoMovilidadEP;
import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.Continente;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.ConvocatoriaPadreParametrizacion;
import co.edu.unal.hermes.modelo.ConvocatoriaParametrizacion;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Departamento;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.Estudiante;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Institucion;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorGrupo;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.MovilidadArchivo;
import co.edu.unal.hermes.modelo.MovilidadEstudiantesPosgrado;
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

public class ManejadorCrearEditarMovilidadPosgradoInvestigacion extends ManejadorBaseMovilidad {

	private static final long serialVersionUID = 2842308676947576233L;
	private String aceptacionDIB;
	private String aceptacionFacultad;
	private String aceptacionPonencia;
	private Long aporteFacultad;
	private final String ID_RESTRICCION_ESTUDIATE_PONENCIA = "MOV3";
	private final String ID_RESTRICCION_ESTUDIATE_PASANTIA = "MOV4";
	private SelectItem[] aprobacion = { new SelectItem("NO", "NO"), new SelectItem("SI", "SI") };

	private boolean bFinanciacion;
	private boolean bOtroTipoPonencia;
	private String categoriaInvestigador;
	private List ciudades;
	private String departamentoDocente;
	private String carreraDocente;
	private String correoDocente;
	private String departamentoSeleccionado = "";
	private Dependencia dependencia;
	private String dependenciaId;
	private SelectItem[] depsItem;
	private Long destinado;
	private String documento;
	private String documentoEstudiante;
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
	private boolean mostrarEstudiante;
	private String nombreDepartamentoSeleccionado;
	private String nombreDocente;
	private Estudiante estudiante;

	private String nombreEvento;
	private String nombreFacultadSeleccionada;
	private String nombreLiderGrupo;
	private String nombreSedeSeleccionada;
	private boolean esPonencia;

	private String otroTipoPonencia;

	private List paises;

	private HtmlPanelGroup panelArchivos;
	private boolean panelMasDatos;
	private boolean panelMasDatos2;
	private boolean panelNoExiste;
	private boolean mostrarDatosEstudiante = false;
	private Persona persona;
	private List proyectosInvestigador;
	private String resolucionViaje;
	private Investigador responsableGrupo;
	private String resumenPonencia;

	private String sedeSeleccionada = "";

	private String solDocente;
	private String solInscripcion;

	private TipoDocumento tipoDocumento;
	private TipoDocumento tipoDocumentoEstudiante;
	private UploadedFile archivoObligatorio;
	private HtmlDataTable tablaArchivosObligatoriosSel;
	private List listaArchivosObligatorios;
	private String nombreObligatorio;

	private SelectItem[] tipoDocumentoItem;
	private SelectItem[] tipoDocumentoEstudianteItem;

	private String tipoInvestigador = new String();
	private SelectItem[] tipoPonencia = { new SelectItem("1", "Conferencia Magistral"), new SelectItem("2", "Póster"),
			new SelectItem("3", "Otro tipo de ponencia") };
	private String tipoPonenciaActual;
	private String tituloPonencia;
	private String errores[];
	private boolean bErrorProyecto;
	private boolean bErrorEvento;
	private boolean bErrorTitulo;
	private boolean bErrorPais;
	private boolean bErrorPapa;
	private boolean bErrorDocumentos;
	private boolean bErrorCiudad;
	private boolean bErrorPonencia;

	private boolean bErrorResumen;
	private boolean bErrorResolucion;
	private boolean bErrorTiquete;
	private boolean bErrorInscripcion;
	private boolean mostrarError = false;
	private String msgError = "Su solicitud NO ha sido enviada, por favor verifique la información y vuelva a GUARDAR. Debe seleccionar SI desea confirmar el envio para enviar la movilidad o NO en caso de guardar parcialmente.";
	private String noExiste;
	private String noExiste1;

	private SelectItem[] tipoDocumentoSelItem;
	private String tipoDocumentoSel;
	private boolean bImprimirReporte;
	private Float papaEstudiante;

	private final static String RUTA_ADJUNTO = "/pages/Movilidad";
	private MovilidadEstudiantesPosgrado mep;

	private UploadedFile archivoCargar;
	private HtmlDataTable tablaArchivos;
	private HtmlDataTable tablaEntidadInvestigacion;
	List listaEntidadInvestigacion;

	private String aprobacionInvestigacionUN;
	private SelectItem[] aprobacionInvestigacionUNItem;

	CorreoPlantilla correoActual = new CorreoPlantilla();
	String cuerpoCorreo = "";
	private Date fechaMinimaInicio;
	private boolean puedeSubirArchivos = false;
	private ArchivoMovilidadEP archivoMovilidadEPSeleccionado;

	// lmom
	private Long MOD_MOVILIDAD_PASANTIA = 423L;
	private Long idContinente;
	private List continentes;
	int africaDocente = 0;
	int americaDocente = 0;
	int asiaDocente = 0;
	int europaDocente = 0;
	int oceaniaDocente = 0;
	int salario = 1;
	private ArchivoMovilidadEP documentoSeleccionado;

	private String restriccionArchivosConv;
	private boolean mostrarValorViaticos = false;
	private Long restriccionTiquetes;
	private String msgErrorPasaporte;
	private boolean errorPasaporte = false;
	private boolean esConvocatoriaFacultad = false;
	private boolean esConvocatoriaSede = false;
	private boolean esConvocatoriaNacional= false;
	private ActividadMovilidad actMovi;
	private boolean bErrorActividades;
	private ActividadMovilidad actividadMovilidadSeleccionada;
	private String estado = "";
	private SelectItem[] medioTransporteItem = { new SelectItem("1", "Terrestre"), new SelectItem("2", "Aereo") };
	private boolean mostrarAportesOtrasDependencias = false;
	private boolean esParaPregrado = false;
	private boolean siColombia = false;
	private SelectItem[] ciudadItem; // CIUDADES A MOSTRAR
	private List listaCiudades; // LISTA DE CIUDADES DEL DEPARTAMENTO
	private Departamento departamentoActual; // DEPARTAMENTO ACTUAL
	private List listaDepartamentos; // LISTA DE DEPARTAMENTOS
	private Ciudad ciudadActual; // CIUDAD SELECCIONADA
	private SelectItem[] departamentoItem; // DEPARTAMENTOS A MOSTRAR
	private String ciudadEvento;
	private boolean mostrarSubModalidades = false;
	private SelectItem[] listaSubmodalidades;
	private String submodalidad;
	private boolean errorSubmodalidad = false;
	private String validadcionCostosSubmodalidad;
	private Integer tiempoRealizacionSol;
	private Long duracionMaximaMovilidad;
    private String tipoEstudianteMovilidad;    
    private String dependenciasEstudiantesMovilidad;
    private String dependenciaRevisionMovilidadesEst;
    private Dependencia depProfesor;
    private Dependencia depEstudiante;
    private Dependencia depConvocatoria;
    
    private boolean mostrarPasaporte;
	private boolean mostrarGrupo;
	
	private Long idConvocatoria;
	
	private boolean esMovilidadNacional = false;
	private Convocatoria convActual;

	public void seleccionarTipoDocumento() {
		System.out.println("puedeSubirArchivos: " + puedeSubirArchivos);
		puedeSubirArchivos = false;
		if (!tipoDocumentoSel.equals("")) {
			puedeSubirArchivos = true;
		}
		System.out.println("tipoDocumentoSel: " + tipoDocumentoSel);
		System.out.println("puedeSubirArchivos: " + puedeSubirArchivos);
	}

	public void calcularFechaMinimaInicio() {
		Calendar fechaActual = Calendar.getInstance();
		fechaActual.add(Calendar.DATE, tiempoRealizacionSol);
		fechaMinimaInicio = fechaActual.getTime();
		System.out.print("fechaMinimaInicio: " + fechaMinimaInicio);
	}

	public ManejadorCrearEditarMovilidadPosgradoInvestigacion() {
		convActual = new Convocatoria();
		Long idMovilidadEd = (Long) sesion.getAttribute("movilidadEstPasSel");
		ocultarPaneles();
		cargarValoresIniciales();

		obtenerListaDepartamentos();
		obtenerListaCiudades();

		personaActual = (Persona) sesion.getAttribute("persona");
		if (personaActual != null) {
			documento = personaActual.getId().getDocumento();
			TipoDocumento td = new TipoDocumento();
			td.setId(personaActual.getId().getTipoDocumento());
		}
		String restriccionConvocatoria = "";
		mep = new MovilidadEstudiantesPosgrado();
		mostrarDatosEstudiante = false;
		listaArchivosObligatorios = new ArrayList();
		cargarContinentes();

		if (idMovilidadEd != null) {
			mep = servicioMovilidad.obtenerMovilidadesEstudiantes(idMovilidadEd);
			sesion.setAttribute("idConvocatoriaActual", mep.getConvocatoria().getId());
			idConvocatoria = mep.getConvocatoria().getId();
			sesion.setAttribute("convocatoriaMovilidadResticcion", mep.getConvocatoria().getRestriccion().getId());
			try {
				buscarPersona();
			} catch (SQLException e) {
				e.getMessage();
			}

			documentoEstudiante = mep.getEstudianteInv().getId().getDocumento();
			tipoDocumentoEstudiante = new TipoDocumento();
			tipoDocumentoEstudiante.setId(mep.getEstudianteInv().getId().getTipoDocumento());
			papaEstudiante = mep.getPapaEstudiante();

			try {
				buscarEstudiante();
			} catch (SQLException e) {
				e.getMessage();
			}

			if (this.mep.getPais() != null) {
				String hqlPais = "select #id e.id, #continente e.continente from Pais e where e.id = '"
						+ mep.getPais().getId() + "'";
				List<Pais> listaPaisSel = servicioGeneral.obtenerObjetosLimitado(Pais.class, hqlPais);
				Pais p = listaPaisSel.get(0);
				idContinente = p.getContinente();
				cargarPaises();
				this.idPais = p.getId();
			}

			if (mep.getGrupo() != null) {
				idgrupo = mep.getGrupo().getId().toString();
			}

			if (mep.getPais() != null) {
				idPais = mep.getPais().getId();
				revisarPais();

				if (siColombia) {
					if (mep.getCiudad() != null) {
						ciudadEvento = mep.getCiudad();
						String hqlDepto = "select e from Ciudad e where e.id = '" + ciudadEvento + "'";
						List<Ciudad> listaCiudad = servicioGeneral.obtenerObjetos(hqlDepto);
						Ciudad ciudadActual = listaCiudad.get(0);
						departamentoActual = ciudadActual.getDepartamento();
						cambiarDepartamento();
					}
				}
			}

			mostrarDatosEvento = true;
			mostrarDatosEstudiante = true;
			panelMasDatos2 = true;
			panelMasDatos = true;
		}

		// para saber si es de facultad
		// para saber si es de facultad
		if (sesion.getAttribute("idConvocatoriaActual") != null) {
			idConvocatoria = (Long) sesion.getAttribute("idConvocatoriaActual");
			List listConvocatorias = servicioGeneral.obtenerConvocatoriaMovilidades(idConvocatoria.toString());
			if (listConvocatorias.size() > 0 && listConvocatorias != null) {
				Convocatoria conv = (Convocatoria) listConvocatorias.get(0);
				mep.setConvocatoria(conv);
				mep.getConvocatoria().setId(idConvocatoria);
				if (conv.getPadre().getDependencia().getId().equals("1")) {
					esConvocatoriaNacional = true;
				}else{
					if(conv.getPadre().getDependencia().getId().equals("2") || conv.getPadre().getDependencia().getId().equals("3") || conv.getPadre().getDependencia().getId().equals("4") || conv.getPadre().getDependencia().getId().equals("5")
					        || conv.getPadre().getDependencia().getId().equals("6") || conv.getPadre().getDependencia().getId().equals("7") || conv.getPadre().getDependencia().getId().equals("8")){
						esConvocatoriaSede = true;
					}else{
						esConvocatoriaFacultad = true;
					}
				}
				
				String sqlBuscaConvocatoria = "select #id e.id, #dependencia e.dependencia, #gruposCategoriaA e.gruposCategoriaA, #gruposReconocidos e.gruposReconocidos, #areaDirigida e.areaDirigida, #restriccion e.restriccion, #montoApoyoGanadores e.montoApoyoGanadores, #esParaGrupos e.esParaGrupos, #submodalidadConvocatoriaMovilidad e.submodalidadConvocatoriaMovilidad, #validacionCostosConvocatoriaMovilidad e.validacionCostosConvocatoriaMovilidad, #validacionDuracionMovilidad e.validacionDuracionMovilidad, #tiempoEjecucionProyecto e.tiempoEjecucionProyecto, #tipoEstudianteMovilidad e.tipoEstudianteMovilidad, #dependenciasEstudiantesMovilidad e.dependenciasEstudiantesMovilidad, #dependenciaRevisionMovilidadesEst e.dependenciaRevisionMovilidadesEst"
						+ ", #mostrarPasaporteMovilidad e.mostrarPasaporteMovilidad, #mostrarGrupoMovilidad e.mostrarGrupoMovilidad, #mostrarColombiaMovilidad e.mostrarColombiaMovilidad, #infoAdicionalFormulario e.infoAdicionalFormulario from Convocatoria e where e.id = "
						+ idConvocatoria + "";

				List<Convocatoria> listConvs = servicioGeneral.obtenerObjetosLimitado(Convocatoria.class,
						sqlBuscaConvocatoria);
				convActual = listConvs.get(0);
				
				depConvocatoria = convActual.getDependencia();
				
				if (convActual.getGruposCategoriaA() != null) {
					if (convActual.getGruposCategoriaA()) {
						mostrarValorViaticos = true;
					} else {
						mostrarValorViaticos = false;
					}
				}

				if (convActual.getGruposReconocidos() != null) {
					if (convActual.getGruposReconocidos()) {
						mostrarAportesOtrasDependencias = true;
					} else {
						mostrarAportesOtrasDependencias = false;
					}
				}

				if (convActual.getAreaDirigida() != null) {
					if (convActual.getAreaDirigida().equals("PREGRADO")) {
						esParaPregrado = true;
					} else {
						esParaPregrado = false;
					}
				}

				if (convActual.getMontoApoyoGanadores() != null) {
					restriccionTiquetes = Long.parseLong(convActual.getMontoApoyoGanadores());
				}

				if (convActual.getSubmodalidadConvocatoriaMovilidad() != null) {
					mostrarSubModalidades = true;
					List listaOpcionsSeleDos = new ArrayList<DominioDetalle>();
					listaOpcionsSeleDos = servicioGeneral.obtenerObjetos(
							"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
									+ convActual.getSubmodalidadConvocatoriaMovilidad()
									+ "' order by dd.descripcion");
					setListaSubmodalidades(new SelectItem[listaOpcionsSeleDos.size()]);
					for (int i = 0; i < listaOpcionsSeleDos.size(); i++) {
						DominioDetalle dd = (DominioDetalle) listaOpcionsSeleDos.get(i);
						listaSubmodalidades[i] = new SelectItem(dd.getIdentificador().getTipo(),
								dd.getDescripcion());
						dd = null;
					}
				} else {
					mostrarSubModalidades = false;
				}

				if (convActual.getValidacionCostosConvocatoriaMovilidad() != null) {
					validadcionCostosSubmodalidad = convActual.getValidacionCostosConvocatoriaMovilidad();
				}

				if (convActual.getTiempoEjecucionProyecto() != null) {
					tiempoRealizacionSol = convActual.getTiempoEjecucionProyecto();
				} else {
					tiempoRealizacionSol = 30;
				}

				if (convActual.getValidacionDuracionMovilidad() != null) {
					duracionMaximaMovilidad = Long.valueOf(convActual.getValidacionDuracionMovilidad());
				}
				
				if (convActual.getTipoEstudianteMovilidad() != null) {
					if (!convActual.getTipoEstudianteMovilidad().equals("")) {
						tipoEstudianteMovilidad = convActual.getTipoEstudianteMovilidad();
					}else{
						tipoEstudianteMovilidad = null;
					}
				}else{
					tipoEstudianteMovilidad = null;
				}
				
				if (convActual.getDependenciasEstudiantesMovilidad() != null) {
					if (!convActual.getDependenciasEstudiantesMovilidad().equals("")) {
						dependenciasEstudiantesMovilidad = convActual.getDependenciasEstudiantesMovilidad();
					}else{
						dependenciasEstudiantesMovilidad = null;
					}
				}else{
					dependenciasEstudiantesMovilidad = null;
				}
				
				if (convActual.getDependenciaRevisionMovilidadesEst() != null) {
					if (!convActual.getDependenciaRevisionMovilidadesEst().equals("")) {
						dependenciaRevisionMovilidadesEst = convActual.getDependenciaRevisionMovilidadesEst();
					}else{
						dependenciaRevisionMovilidadesEst = null;
					}
				}else{
					dependenciaRevisionMovilidadesEst = null;
				}

				if (idMovilidadEd != null) {
					restriccionConvocatoria = convActual.getRestriccion().getId();
				} else {
					restriccionConvocatoria = (String) sesion.getAttribute("convocatoriaMovilidadResticcion");
				}

				if (convActual.getMostrarPasaporteMovilidad() == null
						|| convActual.getMostrarPasaporteMovilidad().equals("1")) {
					setMostrarPasaporte(true);
				} else {
					setMostrarPasaporte(false);
				}
				if (convActual.getMostrarGrupoMovilidad() == null
						|| convActual.getMostrarGrupoMovilidad().equals("1")) {
					setMostrarGrupo(true);
				} else {
					setMostrarGrupo(false);
				}
				
				if (convActual.getMostrarColombiaMovilidad() != null) {
					if (convActual.getMostrarColombiaMovilidad().equals("SC")) {
						setEsMovilidadNacional(true);
						cargarPaises();
						idPais = "CO";
						revisarPais();
					}
				}
			}
		}

		cargarTiposDocumentos();

		Proyecto p = (Proyecto) sesion.getAttribute("proyectoMovilidad");
		if (p != null) {
			mep.setProyectoFicha(p.getId());
		}
		List listaTipoPonencia;
		listaTipoPonencia = new ArrayList();

		listaTipoPonencia = servicioGeneral.obtenerListaObjetos("TipoPonencia where mostrar = 'P' ");
		tipoPonencia = new SelectItem[listaTipoPonencia.size()];
		int j = 0;
		for (int i = 0; i < listaTipoPonencia.size(); i++) {
			TipoPonencia tpo = (TipoPonencia) listaTipoPonencia.get(i);
			if (restriccionConvocatoria.equals(ID_RESTRICCION_ESTUDIATE_PONENCIA)
					&& (tpo.getId().equals((long) 12) || tpo.getId().equals((long) 13)))
				tipoPonencia[j++] = new SelectItem(tpo.getId().toString(), tpo.getDescripcion());
			if (restriccionConvocatoria.equals(ID_RESTRICCION_ESTUDIATE_PASANTIA)
					&& (tpo.getId().equals((long) 10) || tpo.getId().equals((long) 11)))
				tipoPonencia[j++] = new SelectItem(tpo.getId().toString(), tpo.getDescripcion());
		}
		SelectItem[] listaAuxiliar = new SelectItem[j];
		for (int i = 0; i < j; i++) {
			listaAuxiliar[i] = tipoPonencia[i];
		}
		tipoPonencia = listaAuxiliar;

		Long idModalidad_ = (Long) super.sesion.getAttribute("idMovilidad");
		if (idModalidad_ != null) {
			infoModalidad(idModalidad_);
			// super.sesion.removeAttribute("idProyecto");
		}
		calcularFechaMinimaInicio();

		List listaParametroUno = this.servicioGeneral
				.obtenerObjetos("FROM Parametro WHERE nombre = 'AFRICA_ESTUDIANTE'");
		List listaParametroDos = this.servicioGeneral
				.obtenerObjetos("FROM Parametro WHERE nombre = 'AMERICA_ESTUDIANTE'");
		List listaParametroTres = this.servicioGeneral
				.obtenerObjetos("FROM Parametro WHERE nombre = 'ASIA_ESTUDIANTE'");
		List listaParametroCuatro = this.servicioGeneral
				.obtenerObjetos("FROM Parametro WHERE nombre = 'EUROPA_ESTUDIANTE'");
		List listaParametroCinco = this.servicioGeneral
				.obtenerObjetos("FROM Parametro WHERE nombre = 'OCEANIA_ESTUDIANTE'");

		List listaParametroSalario = this.servicioGeneral.obtenerObjetos("FROM Parametro WHERE id = 43");

		if (listaParametroUno != null && listaParametroUno.size() > 0) {
			Parametro par = (Parametro) listaParametroUno.get(0);
			africaDocente = Integer.valueOf((par.getValor())).intValue();
		}

		if (listaParametroDos != null && listaParametroDos.size() > 0) {
			Parametro par = (Parametro) listaParametroDos.get(0);
			americaDocente = Integer.valueOf((par.getValor())).intValue();
		}

		if (listaParametroTres != null && listaParametroTres.size() > 0) {
			Parametro par = (Parametro) listaParametroTres.get(0);
			asiaDocente = Integer.valueOf((par.getValor())).intValue();
		}

		if (listaParametroCuatro != null && listaParametroCuatro.size() > 0) {
			Parametro par = (Parametro) listaParametroCuatro.get(0);
			europaDocente = Integer.valueOf((par.getValor())).intValue();
		}

		if (listaParametroCinco != null && listaParametroCinco.size() > 0) {
			Parametro par = (Parametro) listaParametroCinco.get(0);
			oceaniaDocente = Integer.valueOf((par.getValor())).intValue();
		}

		if (listaParametroSalario != null && listaParametroSalario.size() > 0) {
			Parametro par = (Parametro) listaParametroSalario.get(0);
			salario = Integer.valueOf((par.getValor())).intValue();
		}

		actMovi = new ActividadMovilidad();
		definirInicio4049();
	}
	
	public void definirInicio4049() {
		Long idConvocatoria = (Long) sesion.getAttribute("idConvocatoriaActual");
		if (idConvocatoria.equals(900L)) {
			if (mep.getMovilidadTipo() == null
					|| (mep.getMovilidadTipo() != null && !mep.getMovilidadTipo().equals("V"))) {
				tiempoRealizacionSol = 30;
			} else {
				tiempoRealizacionSol = 15;
			}
			calcularFechaMinimaInicio();
			mep.setFechainicial(null);
			mep.setFechafinal(null);
			panelMasDatos = false;
			fechaIncorrecta2 = false;
			panelMasDatos2 = false;
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

	public void revisarPais() {

		if (idPais != null && !idPais.equals("") && idPais.equals("CO")) {
			siColombia = true;
		} else {
			siColombia = false;
		}

	}

	// lmom
	private void cargarContinentes() {
		List listaContinentes = servicioGeneral.obtenerListaObjetosOrdenadosAsc(new Continente(), "nombre");
		continentes = new Vector();
		for (Iterator it = listaContinentes.iterator(); it.hasNext();) {
			Continente p = (Continente) it.next();
			SelectItem s = new SelectItem(p.getId(), p.getNombre());
			continentes.add(s);
		}
		idContinente = 1L;
	}

	private void infoModalidad(Long idModalidad_) {
		List listaMovilidadConsulta;
		listaMovilidadConsulta = new ArrayList();

		listaMovilidadConsulta = servicioGeneral
				.obtenerListaObjetos("MovilidadDocentesExterior where id = '" + idModalidad_ + "'");
		if (listaMovilidadConsulta != null && listaMovilidadConsulta.size() > 0) {
			mep = (MovilidadEstudiantesPosgrado) listaMovilidadConsulta.get(0);
		}

		tipoDocumento.setId(mep.getTipoDocumentoPersona().toString());
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

	public void activarOtrotipoPonencia() { // ValueChangeEvent event) {
		// tipoPonenciaActual = event.getNewValue().toString();
		tipoPonenciaActual = idTipoPonencia;

		if (tipoPonenciaActual.equals("3")) {
			bOtroTipoPonencia = true;
		} else {
			bOtroTipoPonencia = false;
		}

		if (tipoPonenciaActual.equals("10") || tipoPonenciaActual.equals("11")) {
			esPonencia = false;
		} else {
			esPonencia = true;
		}
	}

	public void verArchivo() {

		ArchivoMovilidad ain = (ArchivoMovilidad) tablaArchivos.getRowData();
		FacesContext ctx = FacesContext.getCurrentInstance();

		// listaInformes = new ArrayList();
		// listaInformes =
		// servicioGeneral.obtenerListaObjetos("ProyectoInforme where id
		// ='"+pin.getId()+"'");
		// ProyectoInforme pinAux = new ProyectoInforme();
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
		// ArchivoInforme ain = ((ArchivoInforme) (tablaArchivos.getRowData()));
		// ain.eliminarArchivo(ain);

	}

	public void buscarEstudiante() throws SQLException {
		reiniciarVariables();
		IdPersona id = new IdPersona();

		mostrarDatosEstudiante = false;

		List est = servicioGeneral.obtenerListaObjetosWhere("Estudiante e", "where e.id.documento='"
				+ this.documentoEstudiante + "' and e.id.tipoDocumento='" + this.tipoDocumentoEstudiante.getId() + "'");
		if (est != null && est.size() > 0) {
			estudiante = (Estudiante) est.get(0);
		} else {
			noExiste = "El documento buscado no ha sido encontrado en el sistema";
			mostrarPanelNoExiste();
			return;
		}

		id.setDocumento(documentoEstudiante);
		id.setTipoDocumento(tipoDocumentoEstudiante.getId());

		String sSql = "";
		int nExiste = 0;
		Date fechaActual = new Date();

		SimpleDateFormat spy = new SimpleDateFormat("yyyy");

		boolean bandera = false;
		if (sesion.getAttribute("idConvocatoriaActual") != null) {
			Long idConvocatoria = (Long) sesion.getAttribute("idConvocatoriaActual");
			if (idConvocatoria != null) {
				sSql = " SELECT COUNT(1) " + " FROM " + " HER_MOVILIDAD_ESTUDIANTE_POS " + " WHERE " + " MOV_ID_EST ='"
						+ estudiante.getId().getDocumento() + "'" + " AND ("
						+ "(MOV_APROB = 'SI' AND MOV_REALIZACION_MOVILIDAD IS NULL)" + " OR "
						+ "(MOV_APROB = 'SI' AND MOV_REALIZACION_MOVILIDAD = 'SI')" + ") " + " AND CON_ID = '"
						+ idConvocatoria + "'";
				bandera = true;
			}
		}
		if (!bandera) {
			sSql = " SELECT COUNT(1) " + " FROM " + " HER_MOVILIDAD_ESTUDIANTE_POS " + " WHERE " + " MOV_ID_EST ='"
					+ estudiante.getId().getDocumento() + "'" + " AND ("
					+ "(MOV_APROB = 'SI' AND MOV_REALIZACION_MOVILIDAD IS NULL)" + " OR "
					+ "(MOV_APROB = 'SI' AND MOV_REALIZACION_MOVILIDAD = 'SI')" + ") "
					+ " AND EXTRACT(YEAR FROM MOV_FEC_SOL) ='" + Long.parseLong(spy.format(fechaActual)) + "'";
		}

		nExiste = servicioGeneral.existeMovilidad(sSql);
		nExiste = 0;
		if (nExiste > 0) {
			noExiste = "El estudiante ya tiene aprobada una movilidad para esta convocatoria.";
			mostrarPanelNoExiste();
		} else {
			if (estudiante != null) {
				
				if(validarEstudiantePostulacion() && validarDependenciaEstudiantePostulacion()){
					
//					if(!validarNumeroMaxAplicacionesEstudiante())
//						return;
					
					dependencia = estudiante.getDependencia();// servicioPersona
					this.depEstudiante = estudiante.getDependencia();
					carreraDocente = estudiante.getPlan().getNombre();
					correoDocente = estudiante.getEmail();
					if (!correoDocente.contains("unal.edu.co")) {
						correoDocente += "@unal.edu.co";
					}
					String nombre1, nombre2, apellido1, apellido2;
					if (estudiante.getNombre1() != null) {
						nombre1 = estudiante.getNombre1();
					} else {
						nombre1 = "";
					}
					if (estudiante.getNombre2() != null) {
						nombre2 = estudiante.getNombre2();
					} else {
						nombre2 = "";
					}
					if (estudiante.getApellido1() != null) {
						apellido1 = estudiante.getApellido1();
					} else {
						apellido1 = "";
					}
					if (estudiante.getApellido2() != null) {
						apellido2 = estudiante.getApellido2();
					} else {
						apellido2 = "";
					}
					nombreDocente = nombre1 + " " + nombre2 + " " + apellido1 + " " + apellido2;
					documentoDocente = estudiante.getId().getDocumento();
					tipoDocDocente = estudiante.getId().getTipoDocumento();

					if (estudiante.getPapa() == null) {
						papaEstudiante = Float.parseFloat("0");
					} else {
						papaEstudiante = estudiante.getPapa();
					}

					if (papaEstudiante < 0.0) {
						String error = "El Promedio aritmético del estudiante " + nombreDocente + " es "
								+ papaEstudiante + ", y debe ser igual o mayor a 4.0  \n";
						noExiste = error;
						mostrarPanelNoExiste();
						return;
					} else {
						bErrorPapa = false;
					}

					if (dependencia != null) {
						if (dependencia.getFacultad() != null) {
							facultadDocente = dependencia.getFacultad().getNombre();
							departamentoDocente = dependencia.getNombre();
						}
					} else {
						facultadDocente = "";
						departamentoDocente = "";
					}

					mostrarDatosPersona();
					mostrarDatosEstudiante = true;

				}else{
					noExiste = "Por favor verificar la dependencia y el nivel del programa al que pertenece el estudiante (pregrado o posgrado).";
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
	
//	public boolean validarNumeroMaxAplicacionesEstudiante() {
//		Long idConvocatoria = (Long) sesion.getAttribute("idConvocatoriaActual");
//		convocatoriaActual = (Convocatoria) servicioGeneral.obtenerObjeto(new Convocatoria(), idConvocatoria);
//		Integer movilidadesEstudiante = 0;
//		
//		Long numeroMaximoConvocatoriaPadre = convocatoriaActual.getPadre().getNumeroMovilidadesPorEstudiante();
//		Long numeroMaximoConvocatoria = convocatoriaActual.getNumeroMovilidadesPorEstudiante();
//		
//		if(!esNulo(numeroMaximoConvocatoriaPadre)) {
//			List<MovilidadEstudiantesPosgrado> movsEstudiante = cargarMovilidadesXEstudiante(estudiante, null, null);
//			if(!esNulo(movsEstudiante) && movsEstudiante.size() > 0) {
//				movilidadesEstudiante = movsEstudiante.size();
//				if(movilidadesEstudiante >= numeroMaximoConvocatoriaPadre) {
//					noExiste = "El estudiante se encuentra asociado a " + movilidadesEstudiante + " movilidades y esta convocatoria sólo permite aplicar máximo a " + numeroMaximoConvocatoriaPadre;
//					mostrarPanelErrorEstudiante();
//					return false;
//				}
//			}
//		} else if(!esNulo(numeroMaximoConvocatoria)) {
//			List<MovilidadEstudiantesPosgrado> movsEstudiante = cargarMovilidadesXEstudiante(estudiante, convocatoriaActual, null);
//			if(!esNulo(movsEstudiante) && movsEstudiante.size() > 0) {
//				movilidadesEstudiante = movsEstudiante.size();
//				if(movilidadesEstudiante >= numeroMaximoConvocatoria) {
//					noExiste = "El estudiante se encuentra asociado a " + movilidadesEstudiante + " movilidades y esta modalidad sólo permite aplicar máximo a " + numeroMaximoConvocatoria;
//					mostrarPanelErrorEstudiante();
//					return false;
//				}
//			}
//		} else {
//			Long tipoParam = Tipos.TIPOS_PARAM_CONV_MOV_ESTUDIANTE_CONV_ANIO;
//			List<ConvocatoriaParametrizacion> listaParamEstConvAnio = servicioModalidad.obtenerParametrosConvocatorias(convocatoriaActual, tipoParam);
//			
//			if(!esNulo(listaParamEstConvAnio) && listaParamEstConvAnio.size() > 0) {
//				for (ConvocatoriaParametrizacion convocatoriaParametrizacion : listaParamEstConvAnio) {
//					Long movilidadesConvocatoria = convocatoriaParametrizacion.getValorParametro();
//					String anioParam = convocatoriaParametrizacion.getAño();
//					String anioFechaInicio = obtenerAño(mep.getFechainicial());
//					if(anioParam.equals(anioFechaInicio)) {
//						List<MovilidadEstudiantesPosgrado> movsEstudiante = cargarMovilidadesXEstudiante(estudiante, convocatoriaActual, anioFechaInicio);
//						if(!esNulo(movsEstudiante) && movsEstudiante.size() > 0) {
//							movilidadesEstudiante = movsEstudiante.size();
//							if(movilidadesEstudiante >= movilidadesConvocatoria) {
//								noExiste = "El estudiante se encuentra asociado a " + movilidadesEstudiante + " movilidades en el año "+ anioParam +" y esta modalidad sólo permite aplicar máximo a " + movilidadesConvocatoria + " en el año " + anioParam;
//								mostrarPanelErrorEstudiante();
//								return false;
//							}
//						}
//					}
//				}
//			}
//		}
//		
//		return true;
//	}
	
	private void mostrarPanelErrorEstudiante() {
		panelNoExiste = true;
		panelMasDatos = false;
		panelMasDatos2 = false;
		mostrarDatosEvento = true;
	}
	
	public boolean validarEstudiantePostulacion(){
		if(tipoEstudianteMovilidad != null){
			if(tipoEstudianteMovilidad.equals("PREGRADO")){
				if(estudiante.getPlan().getTipo().equals(3L)){
					return true;
				}else{
					return false;
				}
			}else{
				if(tipoEstudianteMovilidad.equals("POSGRADO")){
					if(estudiante.getPlan().getTipo().equals(4L) || estudiante.getPlan().getTipo().equals(5L) || estudiante.getPlan().getTipo().equals(6L) || estudiante.getPlan().getTipo().equals(7L)){
						return true;
					}else{
						return false;
					}
				}else{
					return true;
				}
			}
		}else{
			return true;
		}
		
	}
	
	public boolean validarDependenciaEstudiantePostulacion(){
		if(dependenciasEstudiantesMovilidad != null){
			if(dependenciasEstudiantesMovilidad.contains(estudiante.getDependencia().getId())){
				return true;
			}else{
				return false;
			}	
		}else{
			return true;
		}
			
	}

	public void buscarPersona() throws SQLException {
		noExiste1 = "";
		reiniciarVariables();
		IdPersona id = new IdPersona();
		id.setDocumento(documento);
		id.setTipoDocumento(tipoDocumento.getId());
		persona = servicioPersona.obtenerPersonaRoles(id);

		// existeMovilidad

		String sSql = "";
		int nExiste = 0;
		Date fechaActual = new Date();

		SimpleDateFormat spd = new SimpleDateFormat("dd");
		SimpleDateFormat spm = new SimpleDateFormat("MM");
		SimpleDateFormat spy = new SimpleDateFormat("yyyy");

		mostrarDatosEstudiante = false;

		if (persona != null) {
			idCiudad = persona.getCiudadDomicilio() == null ? null : persona.getCiudadDomicilio().getId();
			if (persona instanceof Investigador) {
				if (persona instanceof InvestigadorInterno) {
					persona = servicioPersona.obtenerInvestigadorInternoCompleto(persona.getId());
					InvestigadorInterno investigadorInterno = (InvestigadorInterno) persona;
					dependencia = servicioDependencia.obtenerDependencia(investigadorInterno.getId());
					this.depProfesor = servicioDependencia.obtenerDependencia(investigadorInterno.getId());
					String nombre1, nombre2, apellido1, apellido2;
					// Ing. Wilver Alexander Martínez Martínez (wam²)
					// Cambio para aceptar docentes de medio tiempo
					if (investigadorInterno.getTipoDedicacion() != null) {
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
							// }
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
		// }
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
		// listaDependencias = this.servicioMovilidad.obtenerDependencias(id);
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
		} else {
			if (this.estado.equals("P")) {
				mep.setEstado("P");
				guardarPropuesto();
			} else {
				mep.setEstado("I");
				guardarIngresando();
			}

		}
	}

	public void guardarIngresando() {

		Calendar actual = Calendar.getInstance();
		Date date = actual.getTime();

		mep.setFechasolicitud(date);
		TipoPonencia tm2 = new TipoPonencia();
		tm2.setId(Long.parseLong("11"));
		mep.setPonencia(tm2);
		mep.setAceptacion(aceptacionFacultad);
		if (sesion.getAttribute("idConvocatoriaActual") != null) {
			Long idConvocatoria = (Long) sesion.getAttribute("idConvocatoriaActual");
			mep.setConvocatoriaId(idConvocatoria.toString());
		}

		Long costoTiquete = 0L;
		Long costoevento = 0L;
		if (mep.getCostotiquete() != null) {
			costoTiquete = mep.getCostotiquete();
		}
		if (mep.getCostoevento() != null) {
			costoevento = mep.getCostoevento();
		}

		Long costoTotal = costoTiquete + costoevento;
		Long costoMaximo = 0L;

		if (idContinente.equals(1L)) {
			costoMaximo = Long.parseLong(String.valueOf(africaDocente * salario));
		}
		if (idContinente.equals(2L)) {
			costoMaximo = Long.parseLong(String.valueOf(americaDocente * salario));
		}
		if (idContinente.equals(3L)) {
			costoMaximo = Long.parseLong(String.valueOf(asiaDocente * salario));
		}
		if (idContinente.equals(4L)) {
			costoMaximo = Long.parseLong(String.valueOf(europaDocente * salario));
		}
		if (idContinente.equals(5L)) {
			costoMaximo = Long.parseLong(String.valueOf(oceaniaDocente * salario));
		}

		String detalleError = "";

		List listaTipoMovilidad = new ArrayList();
		listaTipoMovilidad = servicioGeneral.obtenerListaObjetos("TipoMovilidad where id ='D1'");
		TipoMovilidad tm = new TipoMovilidad();
		tm = (TipoMovilidad) listaTipoMovilidad.get(0);
		if (tm != null) {
			mep.setTipoMovilidad(tm);
		}
		Estudiante e = null;
		List est = servicioGeneral.obtenerListaObjetosWhere("Estudiante e", "where e.id.documento='"
				+ this.documentoEstudiante + "' and e.id.tipoDocumento='" + this.tipoDocumentoEstudiante.getId() + "'");
		if (est != null && est.size() > 0) {
			e = (Estudiante) est.get(0);
		}
		mep.setEstudianteInv(estudiante);
		mep.setPapaEstudiante(papaEstudiante);

		Persona personaAux = new Persona();
		IdPersona idp = new IdPersona();

		idp.setDocumento(documento);
		idp.setTipoDocumento(tipoDocumento.getId());
		personaAux = servicioPersona.obtenerPersona(idp);

		mep.setPersonaInv(personaAux);

		List listaPais = new ArrayList();
		listaPais = servicioGeneral.obtenerListaObjetos("Pais where id ='" + idPais + "'");
		Pais pais = new Pais();
		pais = (Pais) listaPais.get(0);

		mep.setPais(pais);

		if (siColombia) {
			mep.setCiudad(ciudadEvento);
		}

		if (esConvocatoriaFacultad) {
			mep.setMovilidadConvocatoriaFacultad("S");
		} else {
			mep.setMovilidadConvocatoriaFacultad("N");
		}

		if (getListaArchivosObligatoriosSel() != null && getListaArchivosObligatoriosSel().size() > 0) {
			for (int i = 0; i < getListaArchivosObligatoriosSel().size(); i++) {
				getListaArchivosObligatoriosSel().get(i).setMovilidad(mep);
			}
		}

		servicioGeneral.guardarObjeto(mep);

		noExiste1 = "Solicitud de movilidad guardada correctamente, con el número " + mep.getId() + ".";
		limpiar();
		mostrarPanelNoExiste();
		bImprimirReporte = true;
	}

	public void guardarPropuesto() {

		Calendar actual = Calendar.getInstance();
		Date date = actual.getTime();

		mep.setFechasolicitud(date);
		TipoPonencia tm2 = new TipoPonencia();
		tm2.setId(Long.parseLong("11"));
		mep.setPonencia(tm2);
		mep.setAceptacion(aceptacionFacultad);
		if (sesion.getAttribute("idConvocatoriaActual") != null) {
			Long idConvocatoria = (Long) sesion.getAttribute("idConvocatoriaActual");
			mep.setConvocatoriaId(idConvocatoria.toString());
		}

		Long costoTiquete = 0L;
		Long costoevento = 0L;
		if (mep.getCostotiquete() != null) {
			costoTiquete = mep.getCostotiquete();
		}
		if (mep.getCostoevento() != null) {
			costoevento = mep.getCostoevento();
		}

		Long costoTotal = costoTiquete + costoevento;
		Long costoMaximo = 0L;

		if (idContinente.equals(1L)) {
			costoMaximo = Long.parseLong(String.valueOf(africaDocente * salario));
		}
		if (idContinente.equals(2L)) {
			costoMaximo = Long.parseLong(String.valueOf(americaDocente * salario));
		}
		if (idContinente.equals(3L)) {
			costoMaximo = Long.parseLong(String.valueOf(asiaDocente * salario));
		}
		if (idContinente.equals(4L)) {
			costoMaximo = Long.parseLong(String.valueOf(europaDocente * salario));
		}
		if (idContinente.equals(5L)) {
			costoMaximo = Long.parseLong(String.valueOf(oceaniaDocente * salario));
		}

		String detalleError = "";

		List listaTipoMovilidad = new ArrayList();
		listaTipoMovilidad = servicioGeneral.obtenerListaObjetos("TipoMovilidad where id ='D1'");
		TipoMovilidad tm = new TipoMovilidad();
		tm = (TipoMovilidad) listaTipoMovilidad.get(0);
		if (tm != null) {
			mep.setTipoMovilidad(tm);
		}
		Estudiante e = null;
		List est = servicioGeneral.obtenerListaObjetosWhere("Estudiante e", "where e.id.documento='"
				+ this.documentoEstudiante + "' and e.id.tipoDocumento='" + this.tipoDocumentoEstudiante.getId() + "'");
		if (est != null && est.size() > 0) {
			e = (Estudiante) est.get(0);
		}
		mep.setEstudianteInv(estudiante);
		mep.setPapaEstudiante(papaEstudiante);
		
		if(esConvocatoriaNacional){
			if(dependenciaRevisionMovilidadesEst != null){
				if(dependenciaRevisionMovilidadesEst.equals("EST")){
					mep.setDependenciaRevisionMovilidadesEst(this.depEstudiante);
				}else{
					if(dependenciaRevisionMovilidadesEst.equals("DOC")){
						mep.setDependenciaRevisionMovilidadesEst(this.depProfesor);
					}else{
						mep.setDependenciaRevisionMovilidadesEst(this.depConvocatoria);
					}
				}
				
			}
		}else{
			mep.setDependenciaRevisionMovilidadesEst(this.depConvocatoria);
		}

		if (validar(mep)) {

			Persona personaAux = new Persona();
			IdPersona idp = new IdPersona();

			idp.setDocumento(documento);
			idp.setTipoDocumento(tipoDocumento.getId());
			personaAux = servicioPersona.obtenerPersona(idp);

			mep.setPersonaInv(personaAux);

			List listaPais = new ArrayList();
			listaPais = servicioGeneral.obtenerListaObjetos("Pais where id ='" + idPais + "'");
			Pais pais = new Pais();
			pais = (Pais) listaPais.get(0);

			mep.setPais(pais);

			if (siColombia) {
				mep.setCiudad(ciudadEvento);
			}

			if (esConvocatoriaFacultad) {
				mep.setMovilidadConvocatoriaFacultad("S");
			} else {
				mep.setMovilidadConvocatoriaFacultad("N");
			}

			Dependencia dependencia;
			dependencia = new Dependencia();

			if (personaAux instanceof Investigador) {
				if (personaAux instanceof InvestigadorInterno) {
					personaAux = servicioPersona.obtenerInvestigadorInternoCompleto(personaAux.getId());
					InvestigadorInterno investigadorInterno = (InvestigadorInterno) personaAux;
					if (e != null && e.getDependencia() != null) {
						dependencia = e.getDependencia();
					} else {
						dependencia = servicioDependencia.obtenerDependencia(investigadorInterno.getId());
					}
				}
			}

			List listaParametro;
			listaParametro = new ArrayList();

			listaParametro = this.servicioGeneral
					.obtenerObjetos("FROM Parametro WHERE nombre = '" + dependencia.getSede().getId() + "'");

			if (listaParametro == null || listaParametro.size() == 0) {
				mep.setAceptacion("SI");
			}

			if (getListaArchivosObligatoriosSel() != null && getListaArchivosObligatoriosSel().size() > 0) {
				for (int i = 0; i < getListaArchivosObligatoriosSel().size(); i++) {
					getListaArchivosObligatoriosSel().get(i).setMovilidad(mep);
				}
			}
			
			if(!esNulo(mep.getDependenciaRevisionMovilidadesEst())) {
				if (!Sede.SEDES_ANDINAS.contains(mep.getDependenciaRevisionMovilidadesEst().getSede().getId().toString())
						&& !mep.getDependenciaRevisionMovilidadesEst().getSede().getId().equals(1L)) {
					mep.setAceptacion("SI");
				}
			}

			servicioGeneral.guardarObjeto(mep);
			
			crearHistoricoEstadoMovilidadInvestigador(mep,"Movilidad enviada por parte del docente para revisión");

			correoActual = cargarPlantilla(65);// 42
			editarCorreo(personaAux, mep);
			Correo correo = new Correo();
			correo.setOrigen(Correo.CORREO_HERMES);
			String dirCorreo = personaAux.getEmail();
			correo.adicionarDireccion(dirCorreo);
			correo.adicionarCopiaOculta(new String(personaAux.getEmail()));
			// correo.adicionarCopiaOculta(dirCorreoConfirmacion);
			correo.setAsunto(correoActual.getAsunto());
			correo.setCuerpo(cuerpoCorreo);
			servicioCorreo.enviarCorreo(correo);

			List listaCorreoEncargado = new ArrayList();
			List listaParametroAux;
			listaParametroAux = new ArrayList();

			Dependencia dependenciaAux;
			dependenciaAux = new Dependencia();
			Persona personaEnvio = new Persona();

			personaEnvio = servicioPersona.obtenerInvestigadorInternoCompleto(mep.getPersonaInv().getId());

			InvestigadorInterno investigadorInterno = (InvestigadorInterno) personaEnvio;
			dependenciaAux = servicioDependencia.obtenerDependencia(investigadorInterno.getId());

			if (listaParametro == null || listaParametro.size() == 0) {
				listaParametroAux = this.servicioGeneral
						.obtenerObjetos("FROM Parametro WHERE nombre = '" + dependencia.getSede().getId() + "'");
				correoActual = cargarPlantilla(86);

				listaCorreoEncargado = this.servicioGeneral
						.obtenerObjetos("FROM Parametro WHERE nombre = 'R_MOVILIDAD'    AND DESCRIPCION= '"
								+ dependenciaAux.getSede().getId() + "'");

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
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			String correoEnvio = "sisii_nal@unal.edu.co";
			Persona personaActualAux2 = new Persona();

			if (listaCorreoEncargado != null && listaCorreoEncargado.size() > 0) {

				boolean bandera = true;
				Parametro paraActual = new Parametro();
				try {
					paraActual = (Parametro) listaCorreoEncargado.get(0);
				} catch (Exception ex) {
					bandera = false;
				}

				int numCoord = listaCorreoEncargado.size();

				for (int i = 0; i < numCoord; i++) {
					InvestigadorInterno paActual;
					if (bandera) {
						IdPersona id = new IdPersona();
						id.setDocumento(paraActual.getValor());
						id.setTipoDocumento(paraActual.getProfesion());
						paActual = servicioPersona.obtenerInvestigadorInterno(id);
					} else {
						paActual = (InvestigadorInterno) listaCorreoEncargado.get(i);
					}

					String numeroDocumento = "0";
					// numeroDocumento = paActual.getValor();
					numeroDocumento = paActual.getId().getDocumento();
					IdPersona id = new IdPersona();
					id.setDocumento(numeroDocumento);
					id.setTipoDocumento(paActual.getId().getTipoDocumento());
					personaActualAux2 = servicioPersona.obtenerPersona(id);
					if (personaActualAux2.getEmail() != null && !personaActualAux2.getEmail().equals("")) {

						correoEnvio = personaActualAux2.getEmail();
					} else {
						correoActual = cargarPlantilla(87);
					}

					editarCorreo(personaActualAux2, mep);
					correo = new Correo();
					correo.setOrigen(Correo.CORREO_HERMES);
					dirCorreo = correoEnvio;
					correo.adicionarDireccion(dirCorreo);
					correo.adicionarCopiaOculta(dirCorreo);
					correo.setAsunto(correoActual.getAsunto());
					correo.setCuerpo(cuerpoCorreo);
					servicioCorreo.enviarCorreo(correo);
				}

			} else {
				correoActual = cargarPlantilla(87);
				editarCorreo(personaActualAux2, mep);
				correo = new Correo();
				correo.setOrigen(Correo.CORREO_HERMES);
				dirCorreo = correoEnvio;
				correo.adicionarDireccion(dirCorreo);
				correo.adicionarCopiaOculta(dirCorreo);
				correo.setAsunto(correoActual.getAsunto());
				correo.setCuerpo(cuerpoCorreo);
				servicioCorreo.enviarCorreo(correo);
			}
			noExiste1 = "Solicitud de movilidad guardada correctamente, con el número " + mep.getId()
			+ ".  Se ha enviado a su correo electrónico un mensaje confirmando el registro. Si lo considera necesario comuníquese con la "
			+ "Coordinación de Investigación de la facultad o sede a la que pertenece para la revisión de la movilidad. ";
			
			/*noExiste1 = "La movilidad con código" + mep.getId() + "se ha guardado y enviado correctamente al usuario a través del "
					+ "sistema Hermes; sin embargo, no se ha enviado correo, les recomendamos comunicarse con "
					+ "la Coordinación de Investigación de la facultad o sede a la que pertenece para realizar "
					+ "la revisión de la movilidad. No hay necesidad de enviar nuevamente la movilidad";*/
			limpiar();
			mostrarPanelNoExiste();
			// enviarCorreo();
			bImprimirReporte = true;
		} else {
			mostrarError = true;
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Ha ocurrido un error, por favor revise los datos ingresados.", msgError);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public String editarCorreo(Persona personaAux, MovilidadEstudiantesPosgrado mov) {

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

	public String imprimirReporte() {
		long movId = mep.getId(); // movilidadGlobal.getId().longValue();
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("id", Long.toString(movId));
		r.setNombreReporte("/movilidad/MovilidadEventos");
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		System.out.println("mirar");
		System.out.println(Navegacion.REPORTE);
		return Navegacion.REPORTE;
	}

	private boolean validar(MovilidadEstudiantesPosgrado mov) {
		boolean result = true;
		mostrarError = false;
		bErrorProyecto = false;
		bErrorEvento = false;
		bErrorTitulo = false;
		bErrorPais = false;
		bErrorCiudad = false;
		bErrorPonencia = false;
		bErrorResumen = false;
		bErrorResolucion = false;
		bErrorTiquete = false;
		bErrorInscripcion = false;
		bImprimirReporte = false;
		bErrorPais = false;
		errorPasaporte = false;

		if (mostrarSubModalidades) {
			if (mov.getSubModalidadConvocatoria().equals("")) {
				errorSubmodalidad = true;
				result = false;
			} else {
				errorSubmodalidad = false;
			}
		}

		if (mostrarPasaporte && (mov.getPasaporteEstudiante() == null || mov.getPasaporteEstudiante().trim().equals(""))) {
			msgErrorPasaporte = "El número de pasaporte es obligatorio";
			errorPasaporte = true;
			result = false;
		}

		if (idPais.equals("00")) {
			String error = "El país es obligatorio \n";
			errores[3] = error;
			bErrorPais = true;
			result = false;
		}

		if (mov.getCostotiquete() == null) {
			String error = "El costo de tiquete es obligatorio \n";
			errores[8] = error;
			bErrorTiquete = true;
			result = false;
		}

		if (mov.getCostotiquete() == null) {
			mov.setCostotiquete(0L);
		}

		if (mov.getValorViaticos() == null) {
			mov.setValorViaticos(0L);
		}

		if (mostrarSubModalidades) {
			if (validadcionCostosSubmodalidad != null) {
				String[] valMov = validadcionCostosSubmodalidad.split(",");
				for (int i = 0; i < valMov.length; i++) {
					String[] valSubmov = valMov[i].split("=");
					if (valSubmov[0].equals(mov.getSubModalidadConvocatoria())) {
						restriccionTiquetes = Long.parseLong(valSubmov[1]);
					}
				}
			}
		}

		if (mov.getValorViaticos() != null && mov.getCostotiquete() != null) {
			if (mov.getValorViaticos() + mov.getCostotiquete() > restriccionTiquetes) {
				String error = "El valor máximo que cubre la convocatoria es de $" + restriccionTiquetes + ". \n";
				errores[8] = error;
				bErrorTiquete = true;
				result = false;
			}
		}
		//

		if (mov.getCostoevento() == null) {
			mov.setCostoevento(0L);
		}

		if (mov.getCostotiquete() == null) {
			mov.setCostotiquete(0L);
		}

		if (mov.getValorViaticos() == null) {
			mov.setValorViaticos(0L);
		}
		
		if (mov.getMaterialesTalleresSocializacion() == null) {
			mov.setMaterialesTalleresSocializacion(0L);
		}

		if (mov.getCostoevento() != null && mov.getCostotiquete() != null && mov.getValorViaticos() != null && mov.getMaterialesTalleresSocializacion() != null) {
			if (mov.getCostoevento().longValue() + mov.getCostotiquete().longValue()
					+ mov.getValorViaticos().longValue() + mov.getMaterialesTalleresSocializacion().longValue() > restriccionTiquetes) {
				String error = "El valor máximo que cubre la convocatoria es de $" + restriccionTiquetes + ". \n";
				errores[8] = error;
				bErrorTiquete = true;
				result = false;
			}
		}

		if (mov.getCostoevento() != null && mov.getCostotiquete() != null && mov.getValorViaticos() != null && mov.getMaterialesTalleresSocializacion() != null) {
			if (mov.getCostoevento().longValue() + mov.getCostotiquete().longValue()
					+ mov.getValorViaticos().longValue() + mov.getMaterialesTalleresSocializacion().longValue() <= 0) {
				String error = "Por favor registre la información del apoyo económico. \n";
				errores[8] = error;
				bErrorTiquete = true;
				result = false;
			}
		}

		//
		
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
			ArchivoMovilidadEP mva = getListaArchivosObligatoriosSel().get(j);
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
			errores[11] = error;
			bErrorDocumentos = true;
			result = false;
		}
		
		if (mep.getConvocatoria().getId().equals(900L) && esCadenaVacia(mep.getMovilidadTipo())) {
			mensajeError("", "El tipo de movilidad es obligatorio.");
			result = false;
		}
		
		if(!validarNumeroMaxAplicacionesDocente())
			result = false;
		
		if(!validarNumeroMaxAplicacionesEstudiante())
			result = false;

		return result;
	}
	
	public boolean validarNumeroMaxAplicacionesEstudiante() {
		Long idConvocatoria = (Long) sesion.getAttribute("idConvocatoriaActual");
		convocatoriaActual = (Convocatoria) servicioGeneral.obtenerObjeto(new Convocatoria(), idConvocatoria);
		Integer movilidadesEstudiante = 0;
		
		Long numeroMaximoConvocatoriaPadre = convocatoriaActual.getPadre().getNumeroMovilidadesPorEstudiante();
		Long numeroMaximoConvocatoria = convocatoriaActual.getNumeroMovilidadesPorEstudiante();
		
		if(!esNulo(numeroMaximoConvocatoriaPadre)) {
			List<MovilidadEstudiantesPosgrado> movsEstudiante = cargarMovilidadesXEstudiante(estudiante, null, null);
			if(!esNulo(movsEstudiante) && movsEstudiante.size() > 0) {
				movilidadesEstudiante = movsEstudiante.size();
				if(movilidadesEstudiante >= numeroMaximoConvocatoriaPadre) {
					String mensajeError = "";
					if (movilidadesEstudiante == 1) {
						mensajeError = "El estudiante se encuentra asociado a " + movilidadesEstudiante + " movilidad y esta convocatoria sólo permite aplicar máximo a " + numeroMaximoConvocatoriaPadre;
					} else {
						mensajeError = "El estudiante se encuentra asociado a " + movilidadesEstudiante + " movilidades y esta convocatoria sólo permite aplicar máximo a " + numeroMaximoConvocatoriaPadre;
					}
					errores[12] = mensajeError;
					mensajeError(mensajeError);
					return false;
				}
			}
		}
		
		if(!esNulo(numeroMaximoConvocatoria)) {
			List<MovilidadEstudiantesPosgrado> movsEstudiante = cargarMovilidadesXEstudiante(estudiante, convocatoriaActual, null);
			if(!esNulo(movsEstudiante) && movsEstudiante.size() > 0) {
				movilidadesEstudiante = movsEstudiante.size();
				if(movilidadesEstudiante >= numeroMaximoConvocatoria) {				
					String mensajeError = "";
					if (movilidadesEstudiante == 1) {
						mensajeError = "El estudiante se encuentra asociado a " + movilidadesEstudiante + " movilidad y esta modalidad sólo permite aplicar máximo a " + numeroMaximoConvocatoria;
					} else {
						mensajeError = "El estudiante se encuentra asociado a " + movilidadesEstudiante + " movilidades y esta modalidad sólo permite aplicar máximo a " + numeroMaximoConvocatoria;
					}
					errores[12] = mensajeError;
					mensajeError(mensajeError);
					return false;
				}
			}
		} 
		
		Long tipoParam = Tipos.TIPOS_PARAM_CONV_MOV_ESTUDIANTE_CONV_ANIO;
		List<ConvocatoriaParametrizacion> listaParamEstConvAnio = servicioModalidad.obtenerParametrosConvocatorias(convocatoriaActual, tipoParam);
		
		for (ConvocatoriaParametrizacion convocatoriaParametrizacion : listaParamEstConvAnio) {
			Long movilidadesConvocatoria = convocatoriaParametrizacion.getValorParametro();
			String anioParam = convocatoriaParametrizacion.getAño();
			String anioFechaInicio = obtenerAño(mep.getFechainicial());
			if(anioParam.equals(anioFechaInicio)) {
				List<MovilidadEstudiantesPosgrado> movsEstudiante = cargarMovilidadesXEstudiante(estudiante, convocatoriaActual, anioFechaInicio);
				if(!esNulo(movsEstudiante) && movsEstudiante.size() > 0) {
					movilidadesEstudiante = movsEstudiante.size();
					if(movilidadesEstudiante >= movilidadesConvocatoria) {						
						String mensajeError = "";
						if (movilidadesEstudiante == 1) {
							mensajeError = "El estudiante se encuentra asociado a " + movilidadesEstudiante + " movilidad en el año "+ anioParam +" y esta modalidad sólo permite tener máximo " + movilidadesConvocatoria + " aplicacion(es) en ese año";
						} else {
							mensajeError = "El estudiante se encuentra asociado a " + movilidadesEstudiante + " movilidades en el año "+ anioParam +" y esta modalidad sólo permite tener máximo " + movilidadesConvocatoria + " aplicacion(es) en ese año";
						}
						errores[12] = mensajeError;
						mensajeError(mensajeError);
						return false;
					}
				}
			}
		}
		
		return true;
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
					errores[12] = mensajeError;
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
					errores[12] = mensajeError;
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
			String anioFechaInicio = obtenerAño(mep.getFechainicial());
			
			if(anioParam.equals(anioFechaInicio)) {
				movilidadesDocente = obtenerNumMovilidadesXModadlidadXDocente(invI, convocatoriaActual, null, anioFechaInicio);
				if(movilidadesDocente >= movilidadesConvocatoria) {
					String mensajeError = "";
					if (movilidadesDocente == 1) {
						mensajeError = "El docente ya tiene " + movilidadesDocente + " movilidad aprobada y/o ejecutada, en el año "+ anioParam +" y solo se permite " + movilidadesConvocatoria + " en esta MODALIDAD para el AÑO " + anioFechaInicio;
					} else {
						mensajeError = "El docente ya tiene " + movilidadesDocente + " movilidades aprobadas y/o ejecutadas, en el año "+ anioParam +" y solo se permite " + movilidadesConvocatoria + " en esta MODALIDAD para el AÑO " + anioFechaInicio;
					}
					errores[12] = mensajeError;
					mensajeError(mensajeError);
					return false;
				}
			}
		}
		
		return true;
	}
	
	public Dependencia obtenerDependenciaMovilidad(Persona personaAux) {
		Dependencia dependencia = new Dependencia();
		
		if (personaAux instanceof Investigador) {
			if (personaAux instanceof InvestigadorInterno) {
				personaAux = servicioPersona.obtenerInvestigadorInternoCompleto(personaAux.getId());
				InvestigadorInterno investigadorInterno = (InvestigadorInterno) personaAux;
				dependencia = servicioDependencia.obtenerDependencia(investigadorInterno.getId());
			}
		}
		
		return dependencia;
	}

	public void limpiar() {
		persona = new Persona();
		documento = new String("");
		// sesion
		// .removeAttribute("ManejadorCrearEditarMovilidadPosgradoInvestigacion");
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
		panelMasDatos = false;
		panelNoExiste = false;
		identificacion = false;
		fechaIncorrecta2 = false;
		panelMasDatos2 = false;

		cargarTiposDocumento();
	}

	public void validarFecha(ValueChangeEvent event) {
		Date fechaEvento = (Date) event.getNewValue();

		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(fechaEvento);
		int diff = 0;

		while (cal1.before(cal2) || cal1.equals(cal2)) {
			SimpleDateFormat dia = new SimpleDateFormat("EEEE");
			int diaEntero = cal1.get(Calendar.DAY_OF_WEEK);

			boolean esFestivo = false;
			SimpleDateFormat spd = new SimpleDateFormat("dd");
			SimpleDateFormat spm = new SimpleDateFormat("MM");
			SimpleDateFormat spy = new SimpleDateFormat("yyyy");

			cal1.add(Calendar.DATE, 1);
			diff++;

		}
		if (diff >= tiempoRealizacionSol) {
			mostrarMasDatos();
		} else {
			ocultarMasDatos();
		}

	}

	public void validarFechaLlegada(ValueChangeEvent event) {
		Date fechaEvento = (Date) event.getNewValue();

		Calendar c = Calendar.getInstance();
		c.setTime(mep.getFechainicial());

		c.add(Calendar.DATE, 9);

		boolean bandera = true;

		if (fechaEvento.before(this.mep.getFechainicial())) {
			bandera = false;
		}

		Long diferencia = (fechaEvento.getTime() - mep.getFechainicial().getTime());
		double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
		if (mep.getMovilidadTipo() == null || (mep.getMovilidadTipo() != null && !mep.getMovilidadTipo().equals("V"))) {
			if (duracionMaximaMovilidad != null) {
				if (dias > duracionMaximaMovilidad) {
					bandera = false;
				}
			}
		}
		
		if(mep.getConvocatoria().getId().equals(1181L)){
			if(mep.getSubModalidadConvocatoria().equals("P")) {
				if (dias < 30) {
					bandera = false;
				}
			}			
		}

		if (bandera) {
			mostrarMasDatos2();
		} else {
			ocultarMasDatos2();
		}

	}

	private void cargarCiudades() {
		List listaCiudades = servicioGeneral.obtenerListaObjetosOrdenadosAsc(new Ciudad(), "nombre");
		ciudades = new Vector();
		for (Iterator it = listaCiudades.iterator(); it.hasNext();) {
			Ciudad c = (Ciudad) it.next();
			SelectItem s = new SelectItem(c.getId(), c.getNombre());
			ciudades.add(s);
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
	
	public void cargarPaises() {
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
			String query = "select e from Pais e where e.continente = " + idContinente + "and e.sigla is not null";
			Long idConvocatoria = (Long) sesion.getAttribute("idConvocatoriaActual");
			List listConvocatorias = servicioGeneral
					.obtenerObjetos("select e from Convocatoria e where e.id = " + idConvocatoria);
			if (listConvocatorias.size() > 0 && listConvocatorias != null) {
				Convocatoria convocatoriaActual = (Convocatoria) listConvocatorias.get(0);
				if (convocatoriaActual.getMostrarColombiaMovilidad() != null
						&& convocatoriaActual.getMostrarColombiaMovilidad().equals("Y")) {
					query += " and e.id!='CO'";
				}
			}

			List listaPaises = servicioGeneral.obtenerObjetos(query);
			paises = new Vector();
			for (Iterator it = listaPaises.iterator(); it.hasNext();) {
				Pais p = (Pais) it.next();
				SelectItem s = new SelectItem(p.getId(), p.getNombre());
				paises.add(s);
			}
		}

	}

//	public void cargarPaises() {
//		String query = "select e from Pais e where e.continente = " + idContinente + "and e.sigla is not null";
//		Long idConvocatoria = (Long) sesion.getAttribute("idConvocatoriaActual");
//		List listConvocatorias = servicioGeneral
//				.obtenerObjetos("select e from Convocatoria e where e.id = " + idConvocatoria);
//		if (listConvocatorias.size() > 0 && listConvocatorias != null) {
//			Convocatoria convocatoriaActual = (Convocatoria) listConvocatorias.get(0);
//			if (convocatoriaActual.getMostrarColombiaMovilidad() != null
//					&& convocatoriaActual.getMostrarColombiaMovilidad().equals("Y")) {
//				query += " and e.id!='CO'";
//			}
//		}
//
//		List listaPaises = servicioGeneral.obtenerObjetos(query);
//		paises = new Vector();
//		for (Iterator it = listaPaises.iterator(); it.hasNext();) {
//			Pais p = (Pais) it.next();
//			SelectItem s = new SelectItem(p.getId(), p.getNombre());
//			paises.add(s);
//		}
//	}

	private void cargarTiposDocumento() {
		List listaTipoDocumento = servicioGeneral.obtenerListaObjetos("TipoDocumento");
		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			TipoDocumento td = (TipoDocumento) listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
		}
		tipoDocumento = (TipoDocumento) listaTipoDocumento.get(0);

		List listaTipoDocumentoEstudiante = servicioGeneral.obtenerListaObjetos("TipoDocumento");

		tipoDocumentoEstudianteItem = new SelectItem[listaTipoDocumentoEstudiante.size()];
		for (int i = 0; i < listaTipoDocumentoEstudiante.size(); i++) {
			TipoDocumento td1 = (TipoDocumento) listaTipoDocumentoEstudiante.get(i);
			tipoDocumentoEstudianteItem[i] = new SelectItem(td1.getId(), td1.getNombre());
		}
		tipoDocumentoEstudiante = (TipoDocumento) listaTipoDocumentoEstudiante.get(0);

	}

	private void cargarValoresIniciales() {
		errores = new String[13];
		bErrorProyecto = false;
		bErrorEvento = false;
		bErrorTitulo = false;
		bErrorPais = false;
		bErrorPapa = false;
		bErrorDocumentos = false;
		bErrorCiudad = false;
		bErrorPonencia = false;
		bErrorResumen = false;
		bErrorResolucion = false;
		bErrorTiquete = false;
		bErrorInscripcion = false;
		mostrarDatosBasicos = false;
		mostrarDatosEvento = false;
		fechaIncorrecta = false;
		panelNoExiste = false;
		panelMasDatos = false;
		identificacion = true;
		panelArchivos = new HtmlPanelGroup();
		bOtroTipoPonencia = false;
		fechaIncorrecta2 = false;
		panelMasDatos2 = false;
		esPonencia = false;
		noExiste = "";
		aporteFacultad = new Long(0);
		personaActual = (Persona) sesion.getAttribute("persona");
		idcreador = personaActual.getId().getDocumento();
		tipoDocCreador = personaActual.getId().getTipoDocumento();
		persona = new Persona();
		documento = new String();
		tituloPonencia = "";
		resumenPonencia = "";
		cargarTiposDocumento();
		cargarCiudades();
		cargarContinentes();
		cargarPaises();
		cargarInstituciones();

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
		errores = new String[13];
		bErrorProyecto = false;
		bErrorEvento = false;
		bErrorTitulo = false;
		bErrorPais = false;
		bErrorPapa = false;
		bErrorDocumentos = false;
		bErrorCiudad = false;
		bErrorPonencia = false;
		bErrorResumen = false;
		bErrorResolucion = false;
		bErrorTiquete = false;
		bErrorInscripcion = false;
		mostrarDatosBasicos = false;
		mostrarDatosEvento = false;
		fechaIncorrecta = false;
		panelNoExiste = false;
		panelMasDatos = false;
		identificacion = true;
		panelArchivos = new HtmlPanelGroup();
		bOtroTipoPonencia = false;
		fechaIncorrecta2 = false;
		panelMasDatos2 = false;
		esPonencia = false;
		noExiste = "";
		aporteFacultad = new Long(0);
		tituloPonencia = "";
		resumenPonencia = "";
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
		panelMasDatos = false;
		panelMasDatos2 = false;
		mostrarDatosEstudiante = false;
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
				if (actMovi.getFecha().before(this.mep.getFechainicial())) {
					bErrorActividades = true;
					actMovi.setbErrorFecha(true);

					actMovi.setErrorFecha("La fecha debe ser posterior a la fecha de inicio del viaje");
				} else if (actMovi.getFecha().after(this.mep.getFechafinal())) {
					bErrorActividades = true;
					actMovi.setbErrorFecha(true);
					actMovi.setErrorFecha("La fecha debe ser anterior a la fecha de fin del viaje");
				}
			}

			if (!bErrorActividades) {
				mep.adicionarActividad(actMovi);
				actMovi = new ActividadMovilidad();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void eliminarActividad() {
		mep.borrarActividad(actividadMovilidadSeleccionada);
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
				listaTipoMovilidad = servicioGeneral.obtenerListaObjetos("TipoMovilidad where id ='D1'");
				TipoMovilidad tipoMovilidad = (TipoMovilidad) listaTipoMovilidad.get(0);

				ArchivoMovilidad archivoMovilidad = new ArchivoMovilidad();
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
		String mensajeCorreo = "";
		/*
		 * if (servicioCorreo.enviarCorreo(correo)) { mensajeCorreo =
		 * "Su correo ha sido enviado con exito al posible evaluador con copia a "
		 * + persona.getEmail(); } else { mensajeCorreo =
		 * "No se pudo enviar el correo, por favor verifique la dirección electronica"
		 * ; }
		 */
		System.out.println(mensajeCorreo);
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

	public void guardarArchivoMovEP(FileUploadEvent event) {

		archivoObligatorio = event.getFile();
		System.out.println("tipoDocumentoSel: " + tipoDocumentoSel);
		if (tipoDocumentoSel == null || tipoDocumentoSel.equals("")) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar un tipo de archivo", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		for (ArchivoMovilidadEP file : getListaArchivosObligatoriosSel()) {
			if (file.getTipoArchivo().getId().toString().equals(getTipoDocumentoSel())) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Ya existe un documento adjunto bajo el mismo tipo.", "");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return;
			}
		}
		ArchivoMovilidadEP archivoMovilidad = new ArchivoMovilidadEP();
		archivoMovilidad = insertarArchivoMovilidadEPGenerico(1, archivoObligatorio, tipoDocumentoSel);
		mep.adicionarArchivo(archivoMovilidad);
	}

	public void descargarArchivoMovEP() {
		ArchivoMovilidadEP archivo = documentoSeleccionado;
		descargarArchivoMovilidadEPGenerico(archivo.getId());
	}

	public void eliminarArchivoObligatorio() {
		mep.borrarArchivo(archivoMovilidadEPSeleccionado);
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

	public List getCiudades() {
		return ciudades;
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

	public String getResumenPonencia() {
		return resumenPonencia;
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

	public void setCiudades(List ciudades) {
		this.ciudades = ciudades;
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

	public void setResumenPonencia(String resumenPonencia) {
		this.resumenPonencia = resumenPonencia;
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

	public boolean isBErrorDocumentos() {
		return bErrorDocumentos;
	}

	public void setBErrorDocumentos(boolean errorDocumentos) {
		bErrorDocumentos = errorDocumentos;
	}

	public boolean isBErrorPapa() {
		return bErrorPapa;
	}

	public void setBErrorPapa(boolean errorPapa) {
		bErrorPapa = errorPapa;
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

	public String getDocumentoEstudiante() {
		return documentoEstudiante;
	}

	public void setDocumentoEstudiante(String documentoEstudiante) {
		this.documentoEstudiante = documentoEstudiante;
	}

	public TipoDocumento getTipoDocumentoEstudiante() {
		return tipoDocumentoEstudiante;
	}

	public void setTipoDocumentoEstudiante(TipoDocumento tipoDocumentoEstudiante) {
		this.tipoDocumentoEstudiante = tipoDocumentoEstudiante;
	}

	public SelectItem[] getTipoDocumentoEstudianteItem() {
		return tipoDocumentoEstudianteItem;
	}

	public void setTipoDocumentoEstudianteItem(SelectItem[] tipoDocumentoEstudianteItem) {
		this.tipoDocumentoEstudianteItem = tipoDocumentoEstudianteItem;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public boolean isMostrarEstudiante() {
		return mostrarEstudiante;
	}

	public void setMostrarEstudiante(boolean mostrarEstudiante) {
		this.mostrarEstudiante = mostrarEstudiante;
	}

	public boolean isMostrarDatosEstudiante() {
		return mostrarDatosEstudiante;
	}

	public void setMostrarDatosEstudiante(boolean mostrarDatosEstudiante) {
		this.mostrarDatosEstudiante = mostrarDatosEstudiante;
	}

	public Float getPapaEstudiante() {
		return papaEstudiante;
	}

	public void setPapaEstudiante(Float papaEstudiante) {
		this.papaEstudiante = papaEstudiante;
	}

	public String getNoExiste1() {
		return noExiste1;
	}

	public void setNoExiste1(String noExiste1) {
		this.noExiste1 = noExiste1;
	}

	public boolean isEsPonencia() {
		return esPonencia;
	}

	public void setEsPonencia(boolean esPonencia) {
		this.esPonencia = esPonencia;
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

	public HtmlDataTable getTablaArchivosObligatoriosSel() {
		return tablaArchivosObligatoriosSel;
	}

	public void setTablaArchivosObligatoriosSel(HtmlDataTable tablaArchivosObligatoriosSel) {
		this.tablaArchivosObligatoriosSel = tablaArchivosObligatoriosSel;
	}

	public List getListaArchivosObligatorios() {
		return listaArchivosObligatorios;
	}

	public void setListaArchivosObligatorios(List listaArchivosObligatorios) {
		this.listaArchivosObligatorios = listaArchivosObligatorios;
	}

	public List<ArchivoMovilidadEP> getListaArchivosObligatoriosSel() {
		List<ArchivoMovilidadEP> listaArchivos = mep.getListaArchivo();
		return listaArchivos;
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
	 * @return the fechaMinimaInicio
	 */
	public Date getFechaMinimaInicio() {
		return fechaMinimaInicio;
	}

	/**
	 * @param fechaMinimaInicio
	 *            the fechaMinimaInicio to set
	 */
	public void setFechaMinimaInicio(Date fechaMinimaInicio) {
		this.fechaMinimaInicio = fechaMinimaInicio;
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
	 * @return the archivoMovilidadEPSeleccionado
	 */
	public ArchivoMovilidadEP getArchivoMovilidadEPSeleccionado() {
		return archivoMovilidadEPSeleccionado;
	}

	/**
	 * @param archivoMovilidadEPSeleccionado
	 *            the archivoMovilidadEPSeleccionado to set
	 */
	public void setArchivoMovilidadEPSeleccionado(ArchivoMovilidadEP archivoMovilidadEPSeleccionado) {
		this.archivoMovilidadEPSeleccionado = archivoMovilidadEPSeleccionado;
	}

	public Long getIdContinente() {
		return idContinente;
	}

	public void setIdContinente(Long idContinente) {
		this.idContinente = idContinente;
	}

	public List getContinentes() {
		return continentes;
	}

	public void setContinentes(List continentes) {
		this.continentes = continentes;
	}

	public String getCarreraDocente() {
		return carreraDocente;
	}

	public void setCarreraDocente(String carreraDocente) {
		this.carreraDocente = carreraDocente;
	}

	public String getCorreoDocente() {
		return correoDocente;
	}

	public void setCorreoDocente(String correoDocente) {
		this.correoDocente = correoDocente;
	}

	public ArchivoMovilidadEP getDocumentoSeleccionado() {
		return documentoSeleccionado;
	}

	public void setDocumentoSeleccionado(ArchivoMovilidadEP documentoSeleccionado) {
		this.documentoSeleccionado = documentoSeleccionado;
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

	public boolean isEsConvocatoriaFacultad() {
		return esConvocatoriaFacultad;
	}

	public void setEsConvocatoriaFacultad(boolean esConvocatoriaFacultad) {
		this.esConvocatoriaFacultad = esConvocatoriaFacultad;
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
		List<ActividadMovilidad> listaActividades = mep.getListaActividades();
		return listaActividades;
	}

	public ActividadMovilidad getActividadMovilidadSeleccionada() {
		return actividadMovilidadSeleccionada;
	}

	public void setActividadMovilidadSeleccionada(ActividadMovilidad actividadMovilidadSeleccionada) {
		this.actividadMovilidadSeleccionada = actividadMovilidadSeleccionada;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public MovilidadEstudiantesPosgrado getMep() {
		return mep;
	}

	public void setMep(MovilidadEstudiantesPosgrado mep) {
		this.mep = mep;
	}

	public SelectItem[] getMedioTransporteItem() {
		return medioTransporteItem;
	}

	public void setMedioTransporteItem(SelectItem[] medioTransporteItem) {
		this.medioTransporteItem = medioTransporteItem;
	}

	public boolean isMostrarAportesOtrasDependencias() {
		return mostrarAportesOtrasDependencias;
	}

	public void setMostrarAportesOtrasDependencias(boolean mostrarAportesOtrasDependencias) {
		this.mostrarAportesOtrasDependencias = mostrarAportesOtrasDependencias;
	}

	public boolean isEsParaPregrado() {
		return esParaPregrado;
	}

	public void setEsParaPregrado(boolean esParaPregrado) {
		this.esParaPregrado = esParaPregrado;
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

	public String getCiudadEvento() {
		return ciudadEvento;
	}

	public void setCiudadEvento(String ciudadEvento) {
		this.ciudadEvento = ciudadEvento;
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

	public Integer getTiempoRealizacionSol() {
		return tiempoRealizacionSol;
	}

	public void setTiempoRealizacionSol(Integer tiempoRealiacionSol) {
		this.tiempoRealizacionSol = tiempoRealiacionSol;
	}

	public Long getDuracionMaximaMovilidad() {
		return duracionMaximaMovilidad;
	}

	public void setDuracionMaximaMovilidad(Long duracionMaximaMovilidad) {
		this.duracionMaximaMovilidad = duracionMaximaMovilidad;
	}

	public String getTipoEstudianteMovilidad() {
		return tipoEstudianteMovilidad;
	}

	public void setTipoEstudianteMovilidad(String tipoEstudianteMovilidad) {
		this.tipoEstudianteMovilidad = tipoEstudianteMovilidad;
	}

	public String getDependenciasEstudiantesMovilidad() {
		return dependenciasEstudiantesMovilidad;
	}

	public void setDependenciasEstudiantesMovilidad(String dependenciasEstudiantesMovilidad) {
		this.dependenciasEstudiantesMovilidad = dependenciasEstudiantesMovilidad;
	}

	public boolean isEsConvocatoriaSede() {
		return esConvocatoriaSede;
	}

	public void setEsConvocatoriaSede(boolean esConvocatoriaSede) {
		this.esConvocatoriaSede = esConvocatoriaSede;
	}

	public boolean isEsConvocatoriaNacional() {
		return esConvocatoriaNacional;
	}

	public void setEsConvocatoriaNacional(boolean esConvocatoriaNacional) {
		this.esConvocatoriaNacional = esConvocatoriaNacional;
	}

	public Dependencia getDepProfesor() {
		return depProfesor;
	}

	public void setDepProfesor(Dependencia depProfesor) {
		this.depProfesor = depProfesor;
	}

	public Dependencia getDepEstudiante() {
		return depEstudiante;
	}

	public void setDepEstudiante(Dependencia depEstudiante) {
		this.depEstudiante = depEstudiante;
	}

	public Dependencia getDepConvocatoria() {
		return depConvocatoria;
	}

	public void setDepConvocatoria(Dependencia depConvocatoria) {
		this.depConvocatoria = depConvocatoria;
	}

	public String getDependenciaRevisionMovilidadesEst() {
		return dependenciaRevisionMovilidadesEst;
	}

	public void setDependenciaRevisionMovilidadesEst(String dependenciaRevisionMovilidadesEst) {
		this.dependenciaRevisionMovilidadesEst = dependenciaRevisionMovilidadesEst;
	}

	public boolean isMostrarPasaporte() {
		return mostrarPasaporte;
	}

	public void setMostrarPasaporte(boolean mostrarPasaporte) {
		this.mostrarPasaporte = mostrarPasaporte;
	}

	public boolean isMostrarGrupo() {
		return mostrarGrupo;
	}

	public void setMostrarGrupo(boolean mostrarGrupo) {
		this.mostrarGrupo = mostrarGrupo;
	}

	public Long getIdConvocatoria() {
		return idConvocatoria;
	}

	public void setIdConvocatoria(Long idConvocatoria) {
		this.idConvocatoria = idConvocatoria;
	}

	public boolean isEsMovilidadNacional() {
		return esMovilidadNacional;
	}

	public void setEsMovilidadNacional(boolean esMovilidadNacional) {
		this.esMovilidadNacional = esMovilidadNacional;
	}

	public Convocatoria getConvActual() {
		return convActual;
	}

	public void setConvActual(Convocatoria convActual) {
		this.convActual = convActual;
	}

}
