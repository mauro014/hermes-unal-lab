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
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.dao.DataIntegrityViolationException;

import co.edu.unal.hermes.modelo.ArchivoMovilidadDE;
import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Departamento;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Institucion;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorGrupo;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.MovilidadArchivo;
import co.edu.unal.hermes.modelo.MovilidadDocentesExterior;
import co.edu.unal.hermes.modelo.MovilidadPonencia;
import co.edu.unal.hermes.modelo.Pais;
import co.edu.unal.hermes.modelo.Parametro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.TipoArchivoMovilidad;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoEntidadInvestigacion;
import co.edu.unal.hermes.modelo.TipoInforme;
import co.edu.unal.hermes.modelo.TipoMovilidad;
import co.edu.unal.hermes.modelo.TipoPonencia;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.utils.Navegacion;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorCrearEditarMovilidadEventoConvFac extends ManejadorBase {

	private String aceptacionDIB;
	private String aceptacionFacultad;
	private String aceptacionPonencia;
	private Long aporteFacultad;
	private SelectItem[] aprobacion = { new SelectItem("NO", "NO"),
			new SelectItem("SI", "SI") };
	private UploadedFile archivoObligatorio;
	private boolean bFinanciacion;
	private boolean bOtroTipoPonencia;
	private String categoriaInvestigador;
	private List ciudades;
	private String ciudadEvento;
	private Long costoInscripcion;
	private Long costoTiquetes;
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
	private Date fechaFinEvento;
	private boolean fechaIncorrecta;
	private boolean fechaIncorrecta2;
	private Date fechaInicioEvento;
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
	private String universidad;
	private String tituloAspira;

	private String otroTipoPonencia;

	private String nombreUniversidadPonencia;

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
	private String resumenPonencia;

	private String sedeSeleccionada = "";
	private UIData tablaPonencias;

	private String solDocente;
	private String solInscripcion;

	private List listaPonencias;
	private List listaPonenciasObligatorias;

	private List listaArchivosObligatorios;
	private List listaArchivosObligatoriosSel;

	private HtmlDataTable tablaArchivosObligatorios;
	private HtmlDataTable tablaArchivosObligatoriosSel;

	private TipoDocumento tipoDocumento;
	private SelectItem[] tipoDocumentoItem;
	private String tipoInvestigador = new String();
	private SelectItem[] tipoPonencia = {
			new SelectItem("1", "Conferencia Magistral"),
			new SelectItem("2", "Póster"),
			new SelectItem("3", "Otro tipo de ponencia") };
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
	private String infoConvocatoriasExternas;
	private String detalleConvocatoriaExterna;
	private boolean siConvExt = false;
	private boolean siColombia = false;
	private SelectItem[] ciudadItem; // CIUDADES A MOSTRAR
	private List listaCiudades; // LISTA DE CIUDADES DEL DEPARTAMENTO
	private Departamento departamentoActual; // DEPARTAMENTO ACTUAL
	private List listaDepartamentos; // LISTA DE DEPARTAMENTOS
	private Ciudad ciudadActual; // CIUDAD SELECCIONADA
	private SelectItem[] departamentoItem; // DEPARTAMENTOS A MOSTRAR
	
	private Long valorViaticos;
	private String valorOtrosAportes;
	
	private String medioTransporte;
	private SelectItem[] medioTransporteItem = {new SelectItem("1","Terrestre"),new SelectItem("2","Aereo")};
	private String caracterEvento;
	private SelectItem[] caracterEventoItem = {new SelectItem("1","Internacional en Colombia"),new SelectItem("2","Internacional en el extranjero")};
	private String idModMovi = "";

	public void seleccionarTipoDocumento() {
		puedeSubirArchivos = true;
		if (tipoDocumentoSel.equals("")) {
			puedeSubirArchivos = false;
		}
		System.out.println("tipoDocumentoSel: " + tipoDocumentoSel);
	}

	public ManejadorCrearEditarMovilidadEventoConvFac() {
		ocultarPaneles();
		cargarValoresIniciales();
		
		obtenerListaDepartamentos();
		obtenerListaCiudades();
		personaActual = (Persona) sesion.getAttribute("persona");
		if(personaActual != null){
			documento = personaActual.getId().getDocumento();
			TipoDocumento td = new TipoDocumento();
			td.setId(personaActual.getId().getTipoDocumento());
		}
		
		//para saber si es de facultad
		if(sesion.getAttribute("idConvocatoriaActual") != null){
			Long idConvocatoria = (Long) sesion.getAttribute("idConvocatoriaActual");
			List listConvocatorias = servicioGeneral
					.obtenerObjetos("select e from Convocatoria e where e.id = "+ idConvocatoria);
			if (listConvocatorias.size() > 0 && listConvocatorias != null) {
				Convocatoria conv = (Convocatoria) listConvocatorias.get(0);
						
				if(conv.getPadre().getDependencia().getId().equals("1") ||conv.getPadre().getDependencia().getId().equals("2") ||conv.getPadre().getDependencia().getId().equals("3") ||conv.getPadre().getDependencia().getId().equals("4") ||conv.getPadre().getDependencia().getId().equals("5") ||conv.getPadre().getDependencia().getId().equals("6") ||conv.getPadre().getDependencia().getId().equals("7") ||conv.getPadre().getDependencia().getId().equals("8")){
					esConvocatoriaFacultad = false;
				}else{
					esConvocatoriaFacultad = true;
				}
				
				if(conv.getRestriccion().getId() != null && conv.getRestriccion().getId().equals("CF_MOV2")){
					idModMovi = "CF_MOV2";					
				}else{
					idModMovi = "CF_MOV4";	
				}
						
			}
		}		

		mde = new MovilidadDocentesExterior();
		listaPonencias = new ArrayList();
		listaPonenciasObligatorias = new ArrayList();
		listaArchivosObligatoriosSel = new ArrayList();
		listaArchivosObligatorios = new ArrayList();
		
		 Proyecto p = (Proyecto)sesion.getAttribute("proyectoMovilidad");
		 if(p != null){
			 mde.setProyectoFicha(p.getId());
		 }
		List listaTipoPonencia;
		listaTipoPonencia = new ArrayList();
//==========================CAMBIO DE LISTA DE PONENCIAS======================================
		//listaTipoPonencia = servicioGeneral.obtenerListaObjetos("TipoPonencia where mostrar = 'S' ");
		listaTipoPonencia = servicioGeneral.obtenerListaObjetos("TipoPonencia where id in (2, 3, 4)");
		
		tipoPonencia = new SelectItem[listaTipoPonencia.size()];
		for (int i = 0; i < listaTipoPonencia.size(); i++) {
			TipoPonencia tpo = (TipoPonencia) listaTipoPonencia.get(i);
			tipoPonencia[i] = new SelectItem(tpo.getId().toString(),
					tpo.getDescripcion());
		}
		listaArchivos = new ArrayList();

		Long idModalidad_ = (Long) super.sesion.getAttribute("idMovilidad");
		if (idModalidad_ != null) {
			infoModalidad(idModalidad_);
			// super.sesion.removeAttribute("idProyecto");
		}

		cargarTiposDocumentos();

		calcularFechaMinimaInicio();		


	}

	public void eliminarPonencia() {
		listaPonencias.remove(movilidadPonenciaSeleccionada);
		if (listaPonencias.size() == 0) {
			mostrarDatosEventoAux = false;
		}
	}
	
	public void revisarInfoConvExt(){
	    
	    if(infoConvocatoriasExternas != null && infoConvocatoriasExternas.equals("SI")){
		siConvExt = true;
	    }else{
		siConvExt = false;
	    }
	    
	}
	
	public void revisarPais(){
	    
	    if(idPais != null && !idPais.equals("") && idPais.equals("CO")){
		siColombia = true;
	    }else{
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
		    ciudadItem[i] = new SelectItem(ci.getNombre(), ci.getNombre());
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

	    private Ciudad buscarCiudad(String id) {
		// BUSCA UNA CIUDAD DE ACUERDO A SU ID
		Ciudad c = new Ciudad();
		int i = 0;
		while (i < listaCiudades.size()) {
		    c = (Ciudad) listaCiudades.get(i);
		    if (id.equals(c.getId()))
			break;
		    i = i + 1;
		}
		return c;
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
		listaCiudades = servicioGeneral.obtenerUbicacion("Ciudad", "", "departamento", (Departamento) listaDepartamentos.get(0),false);
		ciudadItem = new SelectItem[listaCiudades.size()];
		for (int i = 0; i < listaCiudades.size(); i++) {
		    Ciudad ci = (Ciudad) listaCiudades.get(i);
		    ciudadItem[i] = new SelectItem(ci.getId(), ci.getNombre());
		    ci = null;
		}
		ciudadActual = (Ciudad) listaCiudades.get(0);
	    }

	private void cargarTiposDocumentos() {
		listaArchivosObligatorios = new ArrayList();
		listaArchivosObligatorios = servicioGeneral.obtenerListaArchivosMovilidad("CF_MOV2");
		
		if (listaArchivosObligatorios != null
				&& listaArchivosObligatorios.size() > 0) {
			tipoDocumentoSelItem = new SelectItem[listaArchivosObligatorios
					.size() + 1];
			tipoDocumentoSelItem[0] = new SelectItem("",
					"Seleccione un tipo de documento");
			for (int i = 0; i < listaArchivosObligatorios.size(); i++) {
				MovilidadArchivo mva = (MovilidadArchivo) listaArchivosObligatorios
						.get(i);
				tipoDocumentoSelItem[i + 1] = new SelectItem(mva
						.getTipoArchivo().getId().toString(), mva
						.getTipoArchivo().getNombre());
			}
		}
	}

	public void guardarArchivoObligatorio(FileUploadEvent event) {
		try {

			archivoObligatorio = event.getFile();
			if (archivoObligatorio.getContents() != null) {

				int i = archivoObligatorio.getFileName().lastIndexOf("\\");
				nombreObligatorio = archivoObligatorio.getFileName().substring(
						i + 1);

				List listaTipoMovilidad = new ArrayList();
				listaTipoMovilidad = servicioGeneral
						.obtenerListaObjetos("TipoMovilidad where id ='CF_MOV2'");
				TipoMovilidad tipoMovilidad = (TipoMovilidad) listaTipoMovilidad
						.get(0);

				ArchivoMovilidadDE archivoMovilidad = new ArchivoMovilidadDE();
				archivoMovilidad.setBytes(archivoObligatorio.getContents());
				archivoMovilidad.setNombre(archivoObligatorio.getFileName()
						.substring(i + 1));
				archivoMovilidad.setFecha(new Date());
				archivoMovilidad.setTipoMovilidad(tipoMovilidad);

				List listaTipoArchivo;
				listaTipoArchivo = new ArrayList();
				listaTipoArchivo = servicioGeneral
						.obtenerListaObjetos("TipoArchivoMovilidad where id ='"
								+ tipoDocumentoSel + "'");

				TipoArchivoMovilidad tipoArchivo = (TipoArchivoMovilidad) listaTipoArchivo
						.get(0);
				archivoMovilidad.setTipoArchivo(tipoArchivo);

				listaArchivosObligatoriosSel.add(archivoMovilidad);

				List listaMovilidadArchivo;
				listaMovilidadArchivo = new ArrayList();
				listaMovilidadArchivo = servicioGeneral
						.obtenerListaArchivosMovilidad("CF_MOV2", new Long(
								tipoDocumentoSel));

				MovilidadArchivo mva1 = (MovilidadArchivo) listaMovilidadArchivo
						.get(0);

				
			}
		} catch (Exception x) {
			x.printStackTrace();
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_FATAL, x.getClass().getName(),
					x.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	public void eliminarArchivoObligatorio() {
		ArchivoMovilidadDE amv = archivoMovilidadDESeleccionado;
		listaArchivosObligatoriosSel.remove(archivoMovilidadDESeleccionado);

		List listaTipoArchivo;
		listaTipoArchivo = new ArrayList();
		listaTipoArchivo = servicioGeneral
				.obtenerListaObjetos("TipoArchivoMovilidad where id ='"
						+ amv.getTipoArchivo().getId() + "'");
		TipoArchivoMovilidad tipoArchivo = (TipoArchivoMovilidad) listaTipoArchivo
				.get(0);

		List listaMovilidadArchivo;
		listaMovilidadArchivo = new ArrayList();
		listaMovilidadArchivo = servicioGeneral.obtenerListaArchivosMovilidad(
				"CF_MOV2", amv.getTipoArchivo().getId());

		MovilidadArchivo mva1 = (MovilidadArchivo) listaMovilidadArchivo.get(0);

	}

	public void adicionarPonencia() {
		mde.setEvento(nombreEvento);
		mde.setTitulo(tituloPonencia);
		if (validarTituloYEvento(mde)) {
			List listaTipoMovilidad = new ArrayList();
			listaTipoMovilidad = servicioGeneral
					.obtenerListaObjetos("TipoMovilidad where id ='CF_MOV2'");
			TipoMovilidad tipoMovilidad = (TipoMovilidad) listaTipoMovilidad
					.get(0);

			List listaPonencia = new ArrayList();
			listaPonencia = servicioGeneral
					.obtenerListaObjetos("TipoPonencia where id = '"
							+ idTipoPonencia + "'");
			TipoPonencia tipoPonencia = (TipoPonencia) listaPonencia.get(0);

			MovilidadPonencia movilidadPonencia = new MovilidadPonencia();
			movilidadPonencia.setEvento(nombreEvento);
			movilidadPonencia.setTitulo(tituloPonencia);
			movilidadPonencia.setTipoMovilidad(tipoMovilidad);
			movilidadPonencia.setPonencia(tipoPonencia);
			movilidadPonencia.setMovilidad(mde);

			if (tipoPonencia.getId().toString().equals("3")) {
				movilidadPonencia.setOtra(otroTipoPonencia);
			}

			listaPonencias.add(movilidadPonencia);
			mostrarDatosEventoAux = true;
		}
	}

	private void infoModalidad(Long idModalidad_) {
		List listaMovilidadConsulta;
		listaMovilidadConsulta = new ArrayList();

		listaMovilidadConsulta = servicioGeneral
				.obtenerListaObjetos("MovilidadDocentesExterior where id = '"
						+ idModalidad_ + "'");
		if (listaMovilidadConsulta != null && listaMovilidadConsulta.size() > 0) {
			mde = (MovilidadDocentesExterior) listaMovilidadConsulta.get(0);
		}

		tipoDocumento.setId(mde.getTipoDocumentoPersona().toString());

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

		ArchivoMovilidadDE ain = (ArchivoMovilidadDE) tablaArchivos
				.getRowData();
		FacesContext ctx = FacesContext.getCurrentInstance();

		if (ain != null && ain.getArchivo() != null) {

			try {
				if (!ctx.getResponseComplete()) {
					HttpServletResponse response = (HttpServletResponse) ctx
							.getExternalContext().getResponse();
					response.setContentType("text/plain");
					response.setHeader("Content-Disposition",
							"attachment;filename=\"" + ain.getNombre() + "\"");
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
		persona = servicioPersona.obtenerPersonaRoles(id);

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
		if(sesion.getAttribute("idConvocatoriaActual") != null){
			Long idConvocatoria = (Long) sesion.getAttribute("idConvocatoriaActual");
			if(idConvocatoria != null){
				sSql = " SELECT COUNT(1) " + " FROM "
				+ " HER_MOVILIDAD_DOCENTES_EVENTOS " + " WHERE "
				+ " MOV_ID_PER ='" + documento + "'" + " AND MOV_APROB = 'SI' "
				+ "AND MOV_FEC_SOL BETWEEN TO_DATE('12/01/" + anoAnt
				+ "', 'MM/DD/YYYY') AND TO_DATE('11/30/" + anoAct
				+ "', 'MM/DD/YYYY') and CON_ID <> '" +idConvocatoria+ "'";
				bandera = true;
			}
		}
		
		
		if(!bandera){
			sSql = " SELECT COUNT(1) " + " FROM "
				+ " HER_MOVILIDAD_DOCENTES_EVENTOS " + " WHERE "
				+ " MOV_ID_PER ='" + documento + "'" + " AND MOV_APROB = 'SI' "
				+ "AND MOV_FEC_SOL BETWEEN TO_DATE('12/01/" + anoAnt
				+ "', 'MM/DD/YYYY') AND TO_DATE('11/30/" + anoAct
				+ "', 'MM/DD/YYYY')";
		}

		nExiste = servicioGeneral.existeMovilidad(sSql);

		if (nExiste < 0) {
			noExiste = "El investigador ya tiene aprobada una movilidad para este año.";
			mostrarPanelNoExiste();
		} else {

			if (persona != null) {
				idCiudad = persona.getCiudadDomicilio() == null ? null
						: persona.getCiudadDomicilio().getId();
				if (persona instanceof Investigador) {
					if (persona instanceof InvestigadorInterno) {
						persona = servicioPersona
								.obtenerInvestigadorInternoCompleto(persona
										.getId());
						InvestigadorInterno investigadorInterno = (InvestigadorInterno) persona;
						dependencia = servicioDependencia
								.obtenerDependencia(investigadorInterno.getId());
						String nombre1, nombre2, apellido1, apellido2;
						// Ing. Wilver Alexander Martínez Martínez (wam²)
						// Cambio para aceptar docentes de medio tiempo
						if (investigadorInterno.getTipoDedicacion() != null
								&& (investigadorInterno.getTipoDedicacion()
										.getId().equals(Investigador.EXCLUSIVA)
										|| investigadorInterno
												.getTipoDedicacion()
												.getId()
												.equals(Investigador.TIEMPOCOMPLETO) || investigadorInterno
										.getTipoDedicacion().getId()
										.equals(Investigador.MEDIOTIEMPO) || investigadorInterno
										.getTipoDedicacion()
										.getId()
										.equals(Investigador.CATEDRA_0_1)  || 
										investigadorInterno
										.getTipoDedicacion()
										.getId()
										.equals(Investigador.CATEDRA_0_2 )|| 
										investigadorInterno
										.getTipoDedicacion()
										.getId()
										.equals(Investigador.CATEDRA_0_3 )|| 
										investigadorInterno
										.getTipoDedicacion()
										.getId()
										.equals(Investigador.CATEDRA_0_4 )|| 
										investigadorInterno
										.getTipoDedicacion()
										.getId()
										.equals(Investigador.CATEDRA_0_5 )|| 
										investigadorInterno
										.getTipoDedicacion()
										.getId()
										.equals(Investigador.CATEDRA_0_6 )|| 
										investigadorInterno
										.getTipoDedicacion()
										.getId()
										.equals(Investigador.CATEDRA_0_7 ))
										) {
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
							nombreDocente = nombre1 + " " + nombre2 + " "
									+ apellido1 + " " + apellido2;
							documentoDocente = investigadorInterno.getId()
									.getDocumento();
							tipoDocDocente = investigadorInterno.getId()
									.getTipoDocumento();
							if (dependencia.getFacultad() != null) {
								facultadDocente = dependencia.getFacultad()
										.getNombre();
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
		nombreDocente = nombrel1 + " " + nombrel2 + " " + apellidol1 + " "
				+ apellidol2;
		documentoDocente = investigadorActual.getId().getDocumento();

		facultadDocente = "";
		departamentoDocente = "";
		mostrarDatosEvento = true;
	}

	public void cargarDatosInvestigadores() {
		listaIntegrantesGrupo = new ArrayList();
		List integrantesGrupo = this.servicioGrupo
				.obtenerIntegrantesGrupo(grupoActual.getId());
		for (int i = 0; i < integrantesGrupo.size(); i++) {
			InvestigadorGrupo investigadorGrupo = (InvestigadorGrupo) integrantesGrupo
					.get(i);
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
			String nombre = sede.getNombre().length() > 55 ? sede.getNombre()
					.substring(0, 55) + "..." : sede.getNombre();
			depsItem[i] = new SelectItem(sede.getId(), nombre);
		}
	}
	
	public void guardarConvocatoriaFacultad(){
	    
		Calendar actual = Calendar.getInstance();
		Date date = actual.getTime();

		mde.setFechasolicitud(date);
		mde.setEvento(nombreEvento);
		mde.setTitulo(tituloPonencia);
		mde.setCiudad(ciudadEvento);
		mde.setCostotiquete(costoTiquetes);
		mde.setResumen(resumenPonencia);
		mde.setInscripcionevento("NO");
		mde.setOtroTipoPonencia(otroTipoPonencia);
		mde.setCostoevento(costoInscripcion);
		mde.setAportefacultad(aporteFacultad);
		mde.setFechainicial(fechaInicioEvento);
		mde.setFechafinal(fechaFinEvento);
		mde.setAceptacion(aceptacionFacultad);
		mde.setUniversidad(universidad);
		mde.setTituloAspira(tituloAspira);
		mde.setInfoConvocatoriaExterna(infoConvocatoriasExternas != null ? infoConvocatoriasExternas : "");
		mde.setDescConvocatoriaExterna(detalleConvocatoriaExterna != null ? detalleConvocatoriaExterna : "");
		mde.setCaracterEvento(caracterEvento != null ? caracterEvento : "" );
		mde.setMedioTransporte(medioTransporte != null ? medioTransporte : "");
		mde.setNombreUniversidad(nombreUniversidadPonencia);
		mde.setValorViaticos(valorViaticos);
		mde.setValorOtrosAportes(valorOtrosAportes);
		
		if(esConvocatoriaFacultad){
			mde.setMovilidadConvocatoriaFacultad("S");
		}else{
			mde.setMovilidadConvocatoriaFacultad("N");
		}

		List listaPonencia = new ArrayList();
		listaPonencia = servicioGeneral
				.obtenerListaObjetos("TipoPonencia where id ='"
						+ idTipoPonencia + "'");
		TipoPonencia tpn = new TipoPonencia();
		tpn = (TipoPonencia) listaPonencia.get(0);

		mde.setPonencia(tpn);

		List listaTipoMovilidad = new ArrayList();
		listaTipoMovilidad = servicioGeneral
				.obtenerListaObjetos("TipoMovilidad where id ='" + idModMovi + "'");
		TipoMovilidad tm = new TipoMovilidad();

		tm = (TipoMovilidad) listaTipoMovilidad.get(0);

		mde.setTipoMovilidad(tm);

		if (validar(mde)) {

			Persona personaAux = new Persona();
			IdPersona idp = new IdPersona();

			idp.setDocumento(documentoDocente);
			idp.setTipoDocumento(tipoDocDocente);
			personaAux = servicioPersona.obtenerPersona(idp);

			mde.setPersonaInv(personaAux);

			List listaPais = new ArrayList();
			listaPais = servicioGeneral.obtenerListaObjetos("Pais where id ='"
					+ idPais + "'");
			Pais pais = new Pais();
			pais = (Pais) listaPais.get(0);

			mde.setPais(pais);
			if(sesion.getAttribute("idConvocatoriaActual") != null){
				Long idConvocatoria = (Long) sesion.getAttribute("idConvocatoriaActual");
				mde.setConvocatoriaId(idConvocatoria.toString());
			}



			Dependencia dependencia;
			dependencia = new Dependencia();

			if (personaAux instanceof Investigador) {
				if (personaAux instanceof InvestigadorInterno) {
					personaAux = servicioPersona
							.obtenerInvestigadorInternoCompleto(personaAux
									.getId());
					InvestigadorInterno investigadorInterno = (InvestigadorInterno) personaAux;
					dependencia = servicioDependencia
							.obtenerDependencia(investigadorInterno.getId());
				}
			}

			
			servicioGeneral.guardarObjeto(mde);

			if (listaPonencias.size() > 0) {
				for (int i = 0; i < listaPonencias.size(); i++) {
					MovilidadPonencia mop = (MovilidadPonencia) listaPonencias
							.get(i);
					mop.setMovilidad(mde);
					servicioGeneral.guardarObjeto(mop);
				}
			}

			if (listaArchivosObligatoriosSel != null
					&& listaArchivosObligatoriosSel.size() > 0) {
				for (int i = 0; i < listaArchivosObligatoriosSel.size(); i++) {
					ArchivoMovilidadDE amovAux1 = (ArchivoMovilidadDE) listaArchivosObligatoriosSel
							.get(i);
					amovAux1.setMovilidad(mde);
					servicioGeneral.guardarObjeto(amovAux1);
				}
			}

			personaActual = (Persona) sesion.getAttribute("persona");
			String dirCorreoConfirmacion = personaActual.getEmail();

			correoActual = cargarPlantilla(65);// 42
			editarCorreo(personaAux, mde);
			Correo correo = new Correo();
			correo.setOrigen(Correo.CORREO_HERMES);
			String dirCorreo = personaAux.getEmail();
			correo.adicionarDireccion(dirCorreo);
			correo.adicionarCopiaOculta(new String(personaAux.getEmail()));
			correo.setAsunto(correoActual.getAsunto());
			correo.setCuerpo(cuerpoCorreo);
			servicioCorreo.enviarCorreo(correo);

			List listaCorreoEncargado = new ArrayList();
			List listaParametroAux;
			listaParametroAux = new ArrayList();

			Dependencia dependenciaAux;
			dependenciaAux = new Dependencia();
			Persona personaEnvio = new Persona();

			personaEnvio = servicioPersona
					.obtenerInvestigadorInternoCompleto(mde.getPersonaInv()
							.getId());

			InvestigadorInterno investigadorInterno = (InvestigadorInterno) personaEnvio;
			dependenciaAux = servicioDependencia
					.obtenerDependencia(investigadorInterno.getId());

			listaParametroAux = this.servicioGeneral
					.obtenerObjetos("FROM Parametro WHERE nombre = '"
							+ dependencia.getSede().getId() + "'");
			correoActual = cargarPlantilla(85);

			try{
				String consult = "select i from InvestigadorInterno i, "
						+ " PersonaRol pr "
						+ " where i.id.documento= pr.documento "
						+ " and i.id.tipoDocumento= pr.tipoDocumento "
						+ " and pr.nombre = 'MF' and i.dependencia2.facultad.id = '"
						+ dependencia.getFacultad().getId() + "' "
						+ " and i.dependencia2.facultad.esFacultad = 'Y' and (pr.fechaFinRol IS NULL OR pr.fechaFinRol >= CURRENT_DATE) ";
				listaCorreoEncargado = servicioGeneral.obtenerObjetos(consult);
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			String correoEnvio = "sisii_nal@unal.edu.co";
			Persona personaActualAux2 = new Persona();

			if (listaCorreoEncargado != null && listaCorreoEncargado.size() > 0) {
				int numCoord = listaCorreoEncargado.size();

				for (int i = 0; i < numCoord; i++) {

					InvestigadorInterno paActual = (InvestigadorInterno) listaCorreoEncargado
							.get(i);
					String numeroDocumento = "0";
					numeroDocumento = paActual.getId().getDocumento();
					IdPersona id = new IdPersona();
					id.setDocumento(numeroDocumento);
					id.setTipoDocumento(paActual.getId().getTipoDocumento());
					personaActualAux2 = servicioPersona.obtenerPersona(id);

					if (personaActualAux2.getEmail() != null
							&& !personaActualAux2.getEmail().equals("")) {

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
					servicioCorreo.enviarCorreo(correo);
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
				servicioCorreo.enviarCorreo(correo);
			}

			limpiar();

			mostrarPanelNoExiste();
			bImprimirReporte = true;
			noExiste1 = "Solicitud de movilidad guardada correctamente, con el número " + mde.getId()
			+ ".  Se ha enviado a su correo electrónico un mensaje confirmando el registro. Si lo considera necesario comuníquese con la "
			+ "Coordinación de Investigación de la facultad o sede a la que pertenece para la revisión de la movilidad. ";
			
			
			/*noExiste1 = "La movilidad con código" + mde.getId() + "se ha guardado y enviado correctamente al usuario a través del "
					+ "sistema Hermes; sin embargo, no se ha enviado correo, les recomendamos comunicarse con "
					+ "la Coordinación de Investigación de la facultad o sede a la que pertenece para realizar "
					+ "la revisión de la movilidad. No hay necesidad de enviar nuevamente la movilidad";*/
		} else {
			mostrarError = true;
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					msgError, msgError);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
	}

	public void guardar() {

		Calendar actual = Calendar.getInstance();
		Date date = actual.getTime();

		mde.setFechasolicitud(date);
		mde.setEvento(nombreEvento);
		mde.setTitulo(tituloPonencia);
		mde.setCiudad(ciudadEvento);
		mde.setCostotiquete(costoTiquetes);
		mde.setResumen(resumenPonencia);
		mde.setInscripcionevento("NO");
		mde.setOtroTipoPonencia(otroTipoPonencia);
		mde.setCostoevento(costoInscripcion);
		mde.setAportefacultad(aporteFacultad);
		mde.setFechainicial(fechaInicioEvento);
		mde.setFechafinal(fechaFinEvento);
		mde.setAceptacion(aceptacionFacultad);
		mde.setUniversidad(universidad);
		mde.setTituloAspira(tituloAspira);
		mde.setInfoConvocatoriaExterna(infoConvocatoriasExternas != null ? infoConvocatoriasExternas : "");
		mde.setDescConvocatoriaExterna(detalleConvocatoriaExterna != null ? detalleConvocatoriaExterna : "");
		mde.setCaracterEvento(caracterEvento != null ? caracterEvento : "" );
		mde.setMedioTransporte(medioTransporte != null ? medioTransporte : "");
		mde.setNombreUniversidad(nombreUniversidadPonencia);

		List listaPonencia = new ArrayList();
		listaPonencia = servicioGeneral
				.obtenerListaObjetos("TipoPonencia where id ='"
						+ idTipoPonencia + "'");
		TipoPonencia tpn = new TipoPonencia();
		tpn = (TipoPonencia) listaPonencia.get(0);

		mde.setPonencia(tpn);

		List listaTipoMovilidad = new ArrayList();
		listaTipoMovilidad = servicioGeneral
				.obtenerListaObjetos("TipoMovilidad where id ='CF_MOV2'");
		TipoMovilidad tm = new TipoMovilidad();

		tm = (TipoMovilidad) listaTipoMovilidad.get(0);

		mde.setTipoMovilidad(tm);

		if (validar(mde)) {

			Persona personaAux = new Persona();
			IdPersona idp = new IdPersona();

			idp.setDocumento(documentoDocente);
			idp.setTipoDocumento(tipoDocDocente);
			personaAux = servicioPersona.obtenerPersona(idp);

			mde.setPersonaInv(personaAux);

			List listaPais = new ArrayList();
			listaPais = servicioGeneral.obtenerListaObjetos("Pais where id ='"
					+ idPais + "'");
			Pais pais = new Pais();
			pais = (Pais) listaPais.get(0);

			mde.setPais(pais);
			if(sesion.getAttribute("idConvocatoriaActual") != null){
				Long idConvocatoria = (Long) sesion.getAttribute("idConvocatoriaActual");
				mde.setConvocatoriaId(idConvocatoria.toString());
			}



			Dependencia dependencia;
			dependencia = new Dependencia();

			if (personaAux instanceof Investigador) {
				if (personaAux instanceof InvestigadorInterno) {
					personaAux = servicioPersona
							.obtenerInvestigadorInternoCompleto(personaAux
									.getId());
					InvestigadorInterno investigadorInterno = (InvestigadorInterno) personaAux;
					dependencia = servicioDependencia
							.obtenerDependencia(investigadorInterno.getId());
				}
			}

			List listaParametro;
			listaParametro = new ArrayList();

			listaParametro = this.servicioGeneral
					.obtenerObjetos("FROM Parametro WHERE nombre = '"
							+ dependencia.getSede().getId() + "'");

			if (listaParametro == null || listaParametro.size() == 0) {
				mde.setAceptacion("SI");
			}

			servicioGeneral.guardarObjeto(mde);

			if (listaPonencias.size() > 0) {
				for (int i = 0; i < listaPonencias.size(); i++) {
					MovilidadPonencia mop = (MovilidadPonencia) listaPonencias
							.get(i);
					mop.setMovilidad(mde);
					servicioGeneral.guardarObjeto(mop);
				}
			}

			if (listaArchivosObligatoriosSel != null
					&& listaArchivosObligatoriosSel.size() > 0) {
				for (int i = 0; i < listaArchivosObligatoriosSel.size(); i++) {
					ArchivoMovilidadDE amovAux1 = (ArchivoMovilidadDE) listaArchivosObligatoriosSel
							.get(i);
					amovAux1.setMovilidad(mde);
					servicioGeneral.guardarObjeto(amovAux1);
				}
			}

			personaActual = (Persona) sesion.getAttribute("persona");
			String dirCorreoConfirmacion = personaActual.getEmail();

			correoActual = cargarPlantilla(65);// 42
			editarCorreo(personaAux, mde);
			Correo correo = new Correo();
			correo.setOrigen(Correo.CORREO_HERMES);
			String dirCorreo = personaAux.getEmail();
			correo.adicionarDireccion(dirCorreo);
			correo.adicionarCopiaOculta(new String(personaAux.getEmail()));
			// correo.adicionarCopiaOculta(dirCorreoConfirmacion);
			correo.setAsunto(correoActual.getAsunto());
			correo.setCuerpo(cuerpoCorreo);
			servicioCorreo.enviarCorreo(correo);
			// resultadoIngresoInforme = "El informe se ha aprobado con éxito";

			List listaCorreoEncargado = new ArrayList();
			List listaParametroAux;
			listaParametroAux = new ArrayList();

			Dependencia dependenciaAux;
			dependenciaAux = new Dependencia();
			Persona personaEnvio = new Persona();

			personaEnvio = servicioPersona
					.obtenerInvestigadorInternoCompleto(mde.getPersonaInv()
							.getId());

			InvestigadorInterno investigadorInterno = (InvestigadorInterno) personaEnvio;
			dependenciaAux = servicioDependencia
					.obtenerDependencia(investigadorInterno.getId());

			if (listaParametro == null || listaParametro.size() == 0) {
					listaParametroAux = this.servicioGeneral
							.obtenerObjetos("FROM Parametro WHERE nombre = '"
									+ dependencia.getSede().getId() + "'");
					correoActual = cargarPlantilla(86);
	
					listaCorreoEncargado = this.servicioGeneral
							.obtenerObjetos("FROM Parametro WHERE nombre = 'R_MOVILIDAD'    AND DESCRIPCION= '"
									+ dependenciaAux.getSede().getId() + "'");
					InvestigadorInterno ii; 
					if(listaCorreoEncargado != null && listaCorreoEncargado.size() > 0){	
						Parametro para = (Parametro)listaCorreoEncargado.get(0);	
						ii = servicioPersona.obtenerInvestigadorInterno(new IdPersona(para.getValor(),para.getDescripcion()));
						listaCorreoEncargado = new ArrayList();
						listaCorreoEncargado.add(ii);
					}
	
					

			} else {
				listaParametroAux = this.servicioGeneral
						.obtenerObjetos("FROM Parametro WHERE nombre = '"
								+ dependencia.getSede().getId() + "'");
				correoActual = cargarPlantilla(85);

				/*
				 * listaCorreoEncargado = this.servicioGeneral .obtenerObjetos(
				 * "FROM Parametro WHERE nombre = 'R_MOVILIDAD'    AND DESCRIPCION= '"
				 * + dependenciaAux.getFacultad().getId() + "'");
				 */
				try{
					String consult = "select i from InvestigadorInterno i, "
							+ " PersonaRol pr "
							+ " where i.id.documento= pr.documento "
							+ " and i.id.tipoDocumento= pr.tipoDocumento "
							+ " and pr.nombre = 'MF' and i.dependencia2.facultad.id = '"
							+ dependencia.getFacultad().getId() + "' "
							+ " and i.dependencia2.facultad.esFacultad = 'Y' and (pr.fechaFinRol IS NULL OR pr.fechaFinRol >= CURRENT_DATE) ";
					listaCorreoEncargado = servicioGeneral.obtenerObjetos(consult);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}

			String correoEnvio = "sisii_nal@unal.edu.co";
			Persona personaActualAux2 = new Persona();

			if (listaCorreoEncargado != null && listaCorreoEncargado.size() > 0) {
				// Parametro paActual = (Parametro) listaCorreoEncargado.get(0);
				int numCoord = listaCorreoEncargado.size();

				for (int i = 0; i < numCoord; i++) {

					InvestigadorInterno paActual = (InvestigadorInterno) listaCorreoEncargado
							.get(i);
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
					if (personaActualAux2.getEmail() != null
							&& !personaActualAux2.getEmail().equals("")) {

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
					servicioCorreo.enviarCorreo(correo);
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
				servicioCorreo.enviarCorreo(correo);
			}

			limpiar();

			mostrarPanelNoExiste();
			bImprimirReporte = true;
//			noExiste1 = "Solicitud de movilidad guardada correctamente. Un correo electrónico confirmando su registro se ha enviado a su cuenta de correo electrónico.";
			noExiste1 = "La movilidad con código" + mde.getId() + "se ha guardado y enviado correctamente al usuario a través del "
					+ "sistema Hermes; sin embargo, no se ha enviado correo, les recomendamos comunicarse con "
					+ "la Coordinación de Investigación de la facultad o sede a la que pertenece para realizar "
					+ "la revisión de la movilidad. No hay necesidad de enviar nuevamente la movilidad";
		} else {
			mostrarError = true;
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					msgError, msgError);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public String editarCorreo(Persona personaAux, MovilidadDocentesExterior mov) {

		try {
			String coinvNombre = "";

			String correo = correoActual.getCuerpo();

			String investigador = "";

			investigador = personaAux.getNombre1() + " "
					+ personaAux.getApellido1() + " "
					+ personaAux.getApellido2();
			correo = correo.replaceAll("<<INVESTIGADOR>>", investigador);

			correo = correo.replaceAll("<<IDMOVILIDAD>>", mov.getId()
					.toString());
			correo = correo.replaceAll("<<TIPO>>", mov.getTipoMovilidad()
					.getNombre());

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
		List lista = servicioGeneral
				.obtenerObjetos("select c from CorreoPlantilla c where c.id='"
						+ cod_id + "'");
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
				nombreArchivo = archivoVicerectoria.getFileName().substring(
						i + 1);

				FacesContext ctx = FacesContext.getCurrentInstance();
				ServletContext sc = (ServletContext) ctx.getExternalContext()
						.getContext();
				rutaArchivo = sc.getRealPath(RUTA_ADJUNTO);

				rutaArchivo += "\\" + nombreArchivo;

				try {
					mde.setBytesAlDiaVicerrectoria(archivoVicerectoria
							.getContents());
					mde.setNombreVicerrectoria(nombreArchivo);

					nombreVicerrectoria = nombreArchivo;

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
				ServletContext sc = (ServletContext) ctx.getExternalContext()
						.getContext();
				rutaArchivo = sc.getRealPath(RUTA_ADJUNTO);

				rutaArchivo += "\\" + nombreArchivo;

				try {
					mde.setBytesAlDiaDireccion(archivoDireccion.getContents());
					mde.setNombreDireccion(nombreArchivo);
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
				ServletContext sc = (ServletContext) ctx.getExternalContext()
						.getContext();
				rutaArchivo = sc.getRealPath(RUTA_ADJUNTO);

				rutaArchivo += "\\" + nombreArchivo;

				try {
					mde.setBytesAlDiaFacultad(archivoFacultad.getContents());
					mde.setNombreFacultad(nombreArchivo);
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
				nombreArchivo = archivoDepartamento.getFileName().substring(
						i + 1);

				FacesContext ctx = FacesContext.getCurrentInstance();
				ServletContext sc = (ServletContext) ctx.getExternalContext()
						.getContext();
				rutaArchivo = sc.getRealPath(RUTA_ADJUNTO);

				rutaArchivo += "\\" + nombreArchivo;

				try {
					mde.setBytesAlDiaDepartamento(archivoDepartamento
							.getContents());
					mde.setNombreDepartamento(nombreArchivo);
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

		result = validarTituloYEvento(mov);

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
			//result = false;
		}
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
		if (mov.getCostotiquete() == null) {
			String error = "El costo de tiquete es obligatorio \n";
			errores[8] = error;
			bErrorTiquete = true;
			result = false;
		} else if (mov.getCostotiquete().longValue() > 100000000) {
			String error = "El costo del tiquete no puede ser superior a 100.000.000 \n";
			errores[8] = error;
			bErrorTiquete = true;
			result = false;
		}
		if (mov.getInscripcionevento().equals("SI")) {
			if (mov.getCostoevento() == null) {
				String error = "El costo de inscripción es obligatorio \n";
				errores[9] = error;
				bErrorInscripcion = true;
				result = false;
			} else if (mov.getCostoevento().longValue() > 100000000) {
				String error = "El costo de inscripción no puede ser superior a 100.000.000 \n";
				errores[9] = error;
				bErrorInscripcion = true;
				result = false;
			}
		}

		if (listaPonencias == null || listaPonencias.size() == 0) {
			String error = "Debe ingresar al menos ponencia oral. \n";
			errores[10] = error;
			bErrorListaPonencia = true;
			result = false;
		}

		if (listaArchivosObligatoriosSel == null
				|| listaArchivosObligatoriosSel.size() == 0) {
			String error = "Debe adjuntar los documentos necesarios para esta modalidad. \n";
			errores[13] = error;
			bErrorDocumentos = true;
			result = false;
		} else {

			int total;
			total = 1;

			for (int j = 0; j < listaArchivosObligatoriosSel.size(); j++) {

				ArchivoMovilidadDE mva = (ArchivoMovilidadDE) listaArchivosObligatoriosSel
						.get(j);

				for (int i = 0; i < listaArchivosObligatorios.size(); i++) {
					MovilidadArchivo mva1 = (MovilidadArchivo) listaArchivosObligatorios
							.get(i);

					if (!mva.getTipoArchivo().getId().toString().equals("0")) {
						if (mva1.getTipoArchivo()
								.getId()
								.toString()
								.equals(mva.getTipoArchivo().getId().toString())) {
							total = total + 1;

						}
					}
				}
			}

			int minArchivosObligatorios = listaArchivosObligatorios.size() - 2;
			if (total < listaArchivosObligatorios.size() - 1) {
				String error = "Debe adjuntar los documentos necesarios (mínimo "
						+ minArchivosObligatorios
						+ ", todos ellos de un tipo distinto a Otros). \n";
				errores[13] = error;
				bErrorDocumentos = true;
				result = false;
			}
		}

		return result;
	}

	private boolean validarTituloYEvento(MovilidadDocentesExterior mov) {
		boolean result = true;
		bErrorEvento = false;
		bErrorTitulo = false;

		if (mov.getEvento().equals("")) {
			String error = "El nombre del evento es obligatorio \n";
			errores[1] = error;
			bErrorEvento = true;
			result = false;
		}
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
		fechaActual.add(Calendar.DATE, 30);
		fechaMinimaInicio = fechaActual.getTime();
		System.out
				.print("ManejadorCrearEditarMovilidadEvento fechaMinimaInicio: "
						+ fechaMinimaInicio);
	}

	public void limpiar() {
		persona = new Persona();
		documento = new String("");
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

		cargarTiposDocumento();
	}

	public void validarFecha(ValueChangeEvent event) {
		Date fechaEvento = (Date) event.getNewValue();

		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(fechaEvento);
		int diff = 0;
		while (cal1.before(cal2) || cal1.equals(cal2)) // (c) on its own = 3
		// days diff
		{
			SimpleDateFormat dia = new SimpleDateFormat("EEEE");
			int diaEntero = cal1.get(Calendar.DAY_OF_WEEK);

			
			boolean esFestivo = false;
			SimpleDateFormat spd = new SimpleDateFormat("dd");
			SimpleDateFormat spm = new SimpleDateFormat("MM");
			SimpleDateFormat spy = new SimpleDateFormat("yyyy");

			

			cal1.add(Calendar.DATE, 1);
			
			diff++;

		}
		if (true) {
			mostrarMasDatos();
		} else {
			ocultarMasDatos();
		}

	}

	public void validarFechaLlegada(ValueChangeEvent event) {
		Date fechaEvento = (Date) event.getNewValue();

		Calendar c = Calendar.getInstance();
		c.setTime(fechaInicioEvento);

		c.add(Calendar.DATE, 9);

		if (!fechaEvento.before(this.fechaInicioEvento)) {
			mostrarMasDatos2();

		} else {
			ocultarMasDatos2();
		}

	}

	private void cargarCiudades() {
		List listaCiudades = servicioGeneral.obtenerListaObjetosOrdenadosAsc(
				new Ciudad(), "nombre");
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

	private void cargarPaises() {
		List listaPaises = servicioGeneral.obtenerListaObjetosOrdenadosAsc(
				new Pais(), "nombre");
		paises = new Vector();
		for (Iterator it = listaPaises.iterator(); it.hasNext();) {
			Pais p = (Pais) it.next();
			SelectItem s = new SelectItem(p.getId(), p.getNombre());
			paises.add(s);
		}
	}

	private void cargarTiposDocumento() {
		List listaTipoDocumento = servicioGeneral
				.obtenerListaObjetos("TipoDocumento");
		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			TipoDocumento td = (TipoDocumento) listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
		}
		tipoDocumento = (TipoDocumento) listaTipoDocumento.get(0);
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
		universidad = "";
		tituloAspira = "";
		aporteFacultad = new Long(0);
		costoInscripcion = new Long(0);
		costoTiquetes = new Long(0);
		costoInscripcion = new Long(0);
		personaActual = (Persona) sesion.getAttribute("persona");
		idcreador = personaActual.getId().getDocumento();
		tipoDocCreador = personaActual.getId().getTipoDocumento();
		idPlanAccion = "0";
		persona = new Persona();
		documento = new String();
		cargarTiposDocumento();
		cargarCiudades();
		cargarPaises();
		cargarInstituciones();

		listaEntidadInvestigacion = new ArrayList();

		listaEntidadInvestigacion = servicioGeneral
				.obtenerListaObjetos("TipoEntidadInvestigacion");

		if (listaEntidadInvestigacion != null) {
			aprobacionInvestigacionUNItem = new SelectItem[listaEntidadInvestigacion
					.size()];

			for (int i = 0; i < listaEntidadInvestigacion.size(); i++) {
				TipoEntidadInvestigacion tei = (TipoEntidadInvestigacion) listaEntidadInvestigacion
						.get(i);
				aprobacionInvestigacionUNItem[i] = new SelectItem(tei.getId()
						.toString(), tei.getNombre());

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
		TipoInforme tinAux = (TipoInforme) tablaEntidadInvestigacion
				.getRowData();
		FacesContext ctx = FacesContext.getCurrentInstance();

		if (tinAux != null && tinAux.getArchivoInforme() != null) {
			try {
				if (!ctx.getResponseComplete()) {
					HttpServletResponse response = (HttpServletResponse) ctx
							.getExternalContext().getResponse();
					response.setContentType("text/plain");
					response.setHeader(
							"Content-Disposition",
							"attachment;filename=\""
									+ tinAux.getNombreArchivo() + "\"");
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
				listaTipoMovilidad = servicioGeneral
						.obtenerListaObjetos("TipoMovilidad where id ='CF_MOV2'");
				TipoMovilidad tipoMovilidad = (TipoMovilidad) listaTipoMovilidad
						.get(0);

				ArchivoMovilidadDE archivoMovilidad = new ArchivoMovilidadDE();
				archivoMovilidad.setBytes(archivoCargar.getContents());
				archivoMovilidad.setNombre(archivoCargar.getFileName()
						.substring(i + 1));
				archivoMovilidad.setFecha(new Date());
				archivoMovilidad.setTipoMovilidad(tipoMovilidad);
				// listaArchivos = new ArrayList();aa

				listaArchivos.add(archivoMovilidad);
			}

		} catch (DataIntegrityViolationException ex) {
			System.out.println(ex.toString());
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}

	public void enviarCorreo() {
		Correo correo = new Correo();
		correo.adicionarDireccion("iabohorquezc@unal.edu.co");
		correo.setOrigen(personaActual.getEmail());
		correo.adicionarCopiaOculta(new String(personaActual.getEmail()));
		String asunto = "Nueva movilidad para evento internacional";

		correo.setAsunto(asunto);
		String cuerpo = "Se ha adicionado una nueva movilidad para evento internacional.";
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm.ss");

		Calendar actual = Calendar.getInstance();
		Date date = actual.getTime();
		String strDate = formatter.format(date);
		cuerpo = cuerpo + strDate;
		correo.setCuerpo(cuerpo);
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

	public List getCiudades() {
		return ciudades;
	}

	public String getCiudadEvento() {
		return ciudadEvento;
	}

	public Long getCostoInscripcion() {
		return costoInscripcion;
	}

	public Long getCostoTiquetes() {
		return costoTiquetes;
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

	public Date getFechaFinEvento() {
		return fechaFinEvento;
	}

	public Date getFechaInicioEvento() {
		return fechaInicioEvento;
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

	public void setCiudadEvento(String ciudadEvento) {
		this.ciudadEvento = ciudadEvento;
	}

	public void setCostoInscripcion(Long costoInscripcion) {
		this.costoInscripcion = costoInscripcion;
	}

	public void setCostoTiquetes(Long costoTiquetes) {
		this.costoTiquetes = costoTiquetes;
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

	public void setFechaFinEvento(Date fechaFinEvento) {
		this.fechaFinEvento = fechaFinEvento;
	}

	public void setFechaIncorrecta(boolean fechaIncorrecta) {
		this.fechaIncorrecta = fechaIncorrecta;
	}

	public void setFechaIncorrecta2(boolean fechaIncorrecta2) {
		this.fechaIncorrecta2 = fechaIncorrecta2;
	}

	public void setFechaInicioEvento(Date fechaInicioEvento) {
		this.fechaInicioEvento = fechaInicioEvento;
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

	public void setNombreDepartamentoSeleccionado(
			String nombreDepartamentoSeleccionado) {
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

	public void setAprobacionInvestigacionUNItem(
			SelectItem[] aprobacionInvestigacionUNItem) {
		this.aprobacionInvestigacionUNItem = aprobacionInvestigacionUNItem;
	}

	public HtmlDataTable getTablaEntidadInvestigacion() {
		return tablaEntidadInvestigacion;
	}

	public void setTablaEntidadInvestigacion(
			HtmlDataTable tablaEntidadInvestigacion) {
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
		return listaPonencias;
	}

	public void setListaPonencias(List listaPonencias) {
		this.listaPonencias = listaPonencias;
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

	public List getListaArchivosObligatoriosSel() {
		return listaArchivosObligatoriosSel;
	}

	public void setListaArchivosObligatoriosSel(
			List listaArchivosObligatoriosSel) {
		this.listaArchivosObligatoriosSel = listaArchivosObligatoriosSel;
	}

	public HtmlDataTable getTablaArchivosObligatorios() {
		return tablaArchivosObligatorios;
	}

	public void setTablaArchivosObligatorios(
			HtmlDataTable tablaArchivosObligatorios) {
		this.tablaArchivosObligatorios = tablaArchivosObligatorios;
	}

	public HtmlDataTable getTablaArchivosObligatoriosSel() {
		return tablaArchivosObligatoriosSel;
	}

	public void setTablaArchivosObligatoriosSel(
			HtmlDataTable tablaArchivosObligatoriosSel) {
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

	public String getUniversidad() {
		return universidad;
	}

	public void setUniversidad(String universidad) {
		this.universidad = universidad;
	}

	public String getTituloAspira() {
		return tituloAspira;
	}

	public void setTituloAspira(String tituloAspira) {
		this.tituloAspira = tituloAspira;
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
	public void setMovilidadPonenciaSeleccionada(
			MovilidadPonencia movilidadPonenciaSeleccionada) {
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
	public void setArchivoMovilidadDESeleccionado(
			ArchivoMovilidadDE archivoMovilidadDESeleccionado) {
		this.archivoMovilidadDESeleccionado = archivoMovilidadDESeleccionado;
	}

	/**
	 * @return the fechaMinimaInicio
	 */
	public Date getFechaMinimaInicio() {
		return fechaMinimaInicio;
	}

	public String getInfoConvocatoriasExternas() {
	    return infoConvocatoriasExternas;
	}

	public void setInfoConvocatoriasExternas(String infoConvocatoriasExternas) {
	    this.infoConvocatoriasExternas = infoConvocatoriasExternas;
	}

	public String getDetalleConvocatoriaExterna() {
	    return detalleConvocatoriaExterna;
	}

	public void setDetalleConvocatoriaExterna(String detalleConvocatoriaExterna) {
	    this.detalleConvocatoriaExterna = detalleConvocatoriaExterna;
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

	public String getCaracterEvento() {
	    return caracterEvento;
	}

	public void setCaracterEvento(String caracterEvento) {
	    this.caracterEvento = caracterEvento;
	}

	public SelectItem[] getCaracterEventoItem() {
	    return caracterEventoItem;
	}

	public void setCaracterEventoItem(SelectItem[] caracterEventoItem) {
	    this.caracterEventoItem = caracterEventoItem;
	}

	public String getMedioTransporte() {
	    return medioTransporte;
	}

	public void setMedioTransporte(String medioTransporte) {
	    this.medioTransporte = medioTransporte;
	}

	public SelectItem[] getMedioTransporteItem() {
	    return medioTransporteItem;
	}

	public void setMedioTransporteItem(SelectItem[] medioTransporteItem) {
	    this.medioTransporteItem = medioTransporteItem;
	}

	public String getNombreUniversidadPonencia() {
		return nombreUniversidadPonencia;
	}

	public void setNombreUniversidadPonencia(String nombreUniversidadPonencia) {
		this.nombreUniversidadPonencia = nombreUniversidadPonencia;
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

	public Long getValorViaticos() {
		return valorViaticos;
	}

	public void setValorViaticos(Long valorViaticos) {
		this.valorViaticos = valorViaticos;
	}

	public String getValorOtrosAportes() {
		return valorOtrosAportes;
	}

	public void setValorOtrosAportes(String valorOtrosAportes) {
		this.valorOtrosAportes = valorOtrosAportes;
	}

	public String getIdModMovi() {
		return idModMovi;
	}

	public void setIdModMovi(String idModMovi) {
		this.idModMovi = idModMovi;
	}

}
