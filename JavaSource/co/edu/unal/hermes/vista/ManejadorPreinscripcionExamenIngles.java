package co.edu.unal.hermes.vista;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.primefaces.model.DefaultUploadedFile;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.Estudiante;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.InscripcionEscuelaInternacional;
import co.edu.unal.hermes.modelo.ModuloCursoEscuela;
import co.edu.unal.hermes.modelo.Parametro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoPersonaEscuela;
import co.edu.unal.hermes.modelo.correo.Correo;

public class ManejadorPreinscripcionExamenIngles extends ManejadorBase{

	private String errores[];
	private boolean panelRender[];
	private boolean panelRenderError[];
	private String documento;
	private Persona persona;
	private List listaCursos;
	private TipoDocumento tipoDocumento;
	private SelectItem[] tipoDocumentoItem;
	private Persona personaActual;
	private boolean mostrarDatosPersona;
	private boolean mostrarDatosPersona2;
	private String idCiudad;
	private String cursoPadre;
	private String cursoTitulo;
	private String cursoLabel;
	private InscripcionEscuelaInternacional iei;
	private InscripcionEscuelaInternacional iei_existente; //Cuando ya realizó la preinscripcion - Cancela

	private String nombres;
	private String apellidos;
	private String telefonoFijo;
	private String telefonoMovil;
	private String facultad;
	private String tipoAspirante;
	private SelectItem[] tipoAspiranteItem;

	private Long semestre;
	private String carrera;
	private String universidad;
	private String promedio;
	private SelectItem[] promedioItem;
	private String ciudad;
	private String direccion;
	private String correo;
	private String actividad;
	private String institucion;
	private List ListaTipoParticipacion1;
	private String tipoParticipacion1;
	private List ListaTipoParticipacion;
	private String tipoParticipacion;
	private Long cursoId;
	private String cursoNombre;
	private Date cursoFInicial;
	private Date cursoFFinal;
	private Date cursoFInicial2;
	private Date cursoFFinal2;
	private Long valorCurso;
	private Long valorCurso2;
	private String cursoHorario;
	private String cursoCodigoSIA;
	private String pais;
	private String link;
	private String cursoRequisitos;

	private String empresa;
	private String cargo;
	private String profesion;
	private boolean publico;

	private String mensaje;
	private boolean panelMensaje;

	InscripcionEscuelaInternacional ieiAux = new InscripcionEscuelaInternacional();

	private SelectItem[] cursoItem;
	private String curso;
	private boolean mostrarForm;
	ModuloCursoEscuela cursoActual;

	private String tipoDocumentoSel;
	private SelectItem[] tipoDocumentoSelItem;
	private InscripcionEscuelaInternacional archivoObligatorioSeleccionado2;
	private UploadedFile archivo;
	private boolean habilitaDocumento;
	private String modalidad;
	private List ListaModalidad;
	private boolean mostrarModalidad;
	private boolean preinscripcionYaExiste;
	private String mensajeCancelacion;
	private boolean mostrarPanelCancelacion;
    private boolean esCursoBio = true;
    private boolean mostrarBotonSalir; 
    
    private List listaExamen;
    private String idCurso;
    private boolean aceptaFormulario;
    private String motivacion;
	
    private boolean bloquearExamen = false;
    
    private List listaArchivosObligatorios;
    private DefaultUploadedFile archivoObligatorio;
	private DefaultUploadedFile archivoObligatorioSeleccionado;
	private List listaArchivosObligatoriosSel;


	public ManejadorPreinscripcionExamenIngles() {
		cargarValoresIniciales();
		cargarTiposDocumento();
		aceptaFormulario = false;
	}

	private void cargarValoresIniciales() {

		errores = new String[60];
		panelRender = new boolean[20];
		panelRenderError = new boolean[60];
		iei = new InscripcionEscuelaInternacional();
		personaActual = (Persona) sesion.getAttribute("persona");

		ListaTipoParticipacion = new Vector();
//		ListaTipoParticipacion.add(new SelectItem("7","Beneficiario convocatoria 'Apoyo de la DIB a tesis de   investigación en posgrados' del 2012"));
//		ListaTipoParticipacion.add(new SelectItem("8","Estudiante Maestría en cualquier área del conocimiento y de especialidades en el área de la salud vinculados a Proyectos Activos con la DIB"));
//		ListaTipoParticipacion.add(new SelectItem("9","Estudiante Doctorado en cualquier área del conocimiento y de especialidades en el área de la salud"));
//		ListaTipoParticipacion.add(new SelectItem("10","Beneficiario convocatoria de Jóvenes Investigadores e Innovadores de Colciencias del 2012"));
		ListaTipoParticipacion.add(new SelectItem("7","Estudiantes de doctorado de la Sede Bogotá de la Universidad Nacional de Colombia"));
		ListaTipoParticipacion.add(new SelectItem("8","Estudiantes de maestría en cualquier área del conocimiento y de especialidades en el área de la salud de la Sede Bogotá de la Universidad Nacional de Colombia"));
		//ListaTipoParticipacion.add(new SelectItem("9","Beneficiarios de la convocatoria Programa Nacional de Proyectos para el Fortalecimiento de la Investigación, Creación e Innovación en Posgrados de la Universidad Nacional de Colombia 2013-2015"));
		//ListaTipoParticipacion.add(new SelectItem("9","Estudiantes vinculados a proyectos activos financiados por el Sistema de Investigación o convocatorias con recursos externos nacionales o internacionales."));
		
		listaExamen = new ArrayList<SelectItem>();
		listaExamen.add(new SelectItem("1028", "Michigan"));
		listaExamen.add(new SelectItem("1029", "TOEFL"));
		
		bloquearExamen = false;

		nombres = "";
		apellidos = "";
		ciudad = "";
		idCiudad = "";
		carrera = "";
		facultad = "";
		promedio = "";
		semestre = new Long(0);
		correo = "";
		telefonoFijo = "";
		direccion = "";
		setPais("");
		universidad = "";
		telefonoMovil = "";
		documento = "";
		empresa = "";
		cargo = "";
		profesion = "";

		tipoParticipacion = "";
		motivacion="";
		aceptaFormulario = false;
		
		String consulta = "select mc from ModuloCursoEscuela mc where mc.id='1028' ";
		List l = servicioGeneral.obtenerObjetos(consulta);
		cursoActual = (ModuloCursoEscuela)l.get(0);

		setMostrarDatosPersona(false);
		cargarCursoInicial();
		habilitarDocumento();
		
		listaArchivosObligatoriosSel = new ArrayList();
		
		preinscripcionYaExiste = false;
		mostrarPanelCancelacion = false;
		mostrarBotonSalir = false;
		
		cargarTipoArchivos();
		
	}
	
	public void habilitarDocumento() {
		habilitaDocumento = false;
	}
	
	private String cambiarFormatoFecha(java.util.Date date,int tipo)
	{
		//DateFormat df3 = DateFormat.getDateInstance(DateFormat.LONG);
		String patron_hora = "dd/MM/yyyy hh:mm:ss";
		String patron = "dd/MM/yyyy";
		SimpleDateFormat formato;
		if(tipo == 1)
			formato = new SimpleDateFormat(patron);
		else
			formato = new SimpleDateFormat(patron_hora);
		
		//DateFormat df3 = DateFormat.getDateInstance("dd/MM/yyyy HH:MM");
		
		return formato.format(date);

	}

	public boolean existePreinscripcion(TipoDocumento tipoDoc, String doc) {
		String consulta = "Select pp from InscripcionEscuelaInternacional pp where pp.tipoDocumento like '"
				+ tipoDoc.getId()
				+ "' and pp.documento like '"
				+ doc
				+ "' and pp.curso in ('1028', '1029')"
				+ " and pp.fecha > to_date('16/09/2015','dd/mm/yyyy')";
		List lista = servicioGeneral.obtenerObjetos(consulta);
		
		if (lista.size() == 0)
			return false;
		else
		{
			iei_existente = (InscripcionEscuelaInternacional)lista.get(0);
			return true;
		}	
	}

	public void buscarPersona() throws SQLException {
		
		bloquearExamen = true;

		//preinscripcionYaExiste = existePreinscripcion(tipoDocumento, documento,cursoActual.getId());
		preinscripcionYaExiste = existePreinscripcion(tipoDocumento, documento);
		
		
		Date hoy = new Date();

		if (!preinscripcionYaExiste) {
			reiniciarVariables();
			IdPersona id = new IdPersona();
			id.setDocumento(documento);
			id.setTipoDocumento(tipoDocumento.getId());			

			Estudiante estudiante = servicioPersona.obtenerEstudiante(id);

			if (estudiante != null && estudiante.esInterno()) {
				
				nombres = estudiante.getNombre1() + " "
						+ estudiante.getNombre2();
				apellidos = estudiante.getApellido1() + " "
						+ estudiante.getApellido2();
				try {
					carrera = estudiante.getPlan().getNombre();
				} catch (Exception e) {
					carrera = "";
				}
				try {
					facultad = estudiante.getDependencia().getFacultad()
							.getNombre();
				} catch (Exception e) {
					facultad = "";
				}
				try {
					promedio = estudiante.getPapa().toString();
				} catch (Exception e) {
					promedio = "";
				}
				try {
					System.out.println("estudiante.getSemestreActual(): "+estudiante.getSemestreActual());
					
					semestre = Long.parseLong(estudiante.getSemestreActual());
					System.out.println("semestre: "+semestre);
				} catch (Exception e) {
					semestre = new Long(0);
				}
				try {
					correo = estudiante.getEmail();
				} catch (Exception e) {
					correo = "";
				}
				try {
					telefonoFijo = estudiante.getTelefono();
				} catch (Exception e) {
					telefonoFijo = "";
				}

				try {
					Ciudad estCiudad = estudiante.getCiudadDomicilio();
					ciudad = estCiudad.getNombre();
					idCiudad = estudiante.getCiudadDomicilio().getId();
				} catch (Exception e) {
					Ciudad estCiudad = new Ciudad();
					estCiudad.setId("CO-");
					estCiudad.setNombre("No definido");
					ciudad = estCiudad.getNombre();
					idCiudad = estudiante.getCiudadDomicilio().getId();
				}

				try {
					direccion = estudiante.getDireccion();
				} catch (Exception e) {
					direccion = "";
				}

				setMostrarDatosPersona(true);
				setMostrarDatosPersona2(false);
				panelRender[2] = true;
				if (cursoLabel.startsWith("Curso")) {
					panelRender[10] = true;
				} else {
					panelRender[10] = false;
				}
						
				String consulta = "select mc from ModuloCursoEscuela mc where mc.id='" + idCurso + "' ";
				List l = servicioGeneral.obtenerObjetos(consulta);
				cursoActual = (ModuloCursoEscuela)l.get(0);

			} else {
				FacesContext
				.getCurrentInstance()
				.addMessage(
						"mensajeGrowl",
						new FacesMessage(
								FacesMessage.SEVERITY_ERROR,
								"",
								"No existe un estudiante con documento "+ documento));
			}

			habilitaDocumento = true;

		} else {						
			String consulta_inscripcion = "select iei from InscripcionEscuelaInternacional iei where iei.documento ='"+ documento + "' and iei.curso = '1028'";
			List l = servicioGeneral.obtenerObjetos(consulta_inscripcion);
			
			if(l.size()>0){
				FacesContext
				.getCurrentInstance()
				.addMessage(
						"mensajeGrowl",
						new FacesMessage(
								FacesMessage.SEVERITY_ERROR,
								"",
								"El numero de documento "
										+ documento
										+ " ya tiene asociada una preinscripcion para el 'Examen Michigan'"));
			}else{
				FacesContext
				.getCurrentInstance()
				.addMessage(
						"mensajeGrowl",
						new FacesMessage(
								FacesMessage.SEVERITY_ERROR,
								"",
								"El numero de documento "
										+ documento
										+ " ya tiene asociada una preinscripcion para el 'Examen TOEFL'"));
			}												
			documento="";
			habilitaDocumento = false;
			bloquearExamen = true;
		}

	}
	
	public void eliminarArchivoObligatorio() {
		listaArchivosObligatoriosSel.remove(archivoObligatorioSeleccionado2);
	}
	
	public void guardarArchivoObligatorio() {
		try {
			if (archivoObligatorio.getContents() != null) {

				boolean bandera2 = true;

				// Validar archivos
				if (listaArchivosObligatoriosSel.size() == 1) {
					errores[26] = "Por favor adjunte el documento de soporte, si es más de uno adjuntelos en un archivo comprimido (.zip o .rar)";
					panelRenderError[26] = true;
					bandera2 = false;
				} else {
					errores[26] = "";
					panelRenderError[26] = false;
					bandera2 = true;
				}

				if (bandera2) {
					int i = archivoObligatorio.getFileName().lastIndexOf("\\");

					// this.iei = new InscripcionEscuelaInternacional();

					// ArchivoConvocatoria archivo = new ArchivoConvocatoria();
					//this.iei.setBytes(archivoObligatorio.getContents());
					this.iei.setSoporte1Nombre(archivoObligatorio.getFileName()
							.substring(i + 1));
					this.iei.setFecha(new Date());
					// ieiAux.setTipoArchivo(tipoDocumentoSel);
					listaArchivosObligatoriosSel.add(iei);
					
					archivo = archivoObligatorio;
				}

			}

		} catch (Exception x) {
			System.out.println(x.toString());

		}
	}

	private void reiniciarVariables() {

		errores = new String[60];
		// panelRender = new boolean[10];
		panelRenderError = new boolean[60];
		personaActual = (Persona) sesion.getAttribute("persona");
		iei = new InscripcionEscuelaInternacional();
	}

	public void limpiar() {
//		iei = new InscripcionEscuelaInternacional();
//		iei_existente = new InscripcionEscuelaInternacional();
//		documento = "";
//		cargarValoresIniciales();
//		ocultarPaneles();
		sesion.removeAttribute("ManejadorPreinscripcionExamenIngles");
	}

	public void ocultarPaneles() {
		mostrarDatosPersona = false;
		panelRender[3] = false;
		// panelRender[4] = false;
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

	private void cargarTiposDocumento() {
		List listaTipoDocumento = servicioGeneral.obtenerListaObjetos("TipoDocumento");
		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			TipoDocumento td = (TipoDocumento) listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
		}
		tipoDocumento = (TipoDocumento) listaTipoDocumento.get(0);
	}

	public String regresarCursos() {
		sesion.removeAttribute("ManejadorPreinscripcionEscuelaInternacional");
		return "escuelaInternacional2013";
	}

	public void registro() {
		
		String sqlEstado = "select #id e.id, #estado e.estado from ModuloCursoEscuela e where e.id = " + idCurso;
		List<ModuloCursoEscuela> listaMce = servicioGeneral.obtenerObjetosLimitado(ModuloCursoEscuela.class, sqlEstado);
		ModuloCursoEscuela mce = listaMce.get(0);
		if(mce.getEstado().equals("A")){
			setMostrarForm(true);
			//bloquearExamen = true;
			mensaje = "";
			panelMensaje = false;			
		}else{
			setMostrarForm(false);
			//bloquearExamen = true;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "La preinscripción a este examen no se encuentra activa", ""));
			panelMensaje = true;
		}						
	}

	public void cargarCursoInicial() {
		try {
			String hqlPar = "select p from Parametro p where p.descripcion = 'EscuelaInt'";
			System.out.println("hqlPar: " + hqlPar);
			List lp = servicioGeneral.obtenerObjetos(hqlPar);
			System.out.println("lp: " + lp);
			if (lp.size() > 0) {
				Parametro param = (Parametro) lp.get(0);
				setCursoTitulo(param.getValor());
				cursoLabel = param.getProfesion();
			}

			String idCurso = "1028";

			if (cursoActual == null) {
				if (this.request.getParameter("idCurso") != null && !this.request.getParameter("idCurso").equals("")) {
					idCurso = request.getParameter("idCurso");
					System.out.println("this.request.getParameter(idCurso): "+ this.request.getParameter("idCurso"));
				} else {
					idCurso = "";
					System.out.println("this.request.getParameter(idCurso): "+ this.request.getParameter("idCurso"));
				}
			} else
				idCurso = cursoActual.getId();
			System.out.println("idCurso: " + idCurso);

			if (!idCurso.equals("0")) {
				if (cursoActual == null) {
					panelRender[2] = true;
					String consulta = "select mc from ModuloCursoEscuela mc where mc.id='"+ idCurso + "' ";
					List l = servicioGeneral.obtenerObjetos(consulta);
					this.cursoActual = (ModuloCursoEscuela) l.get(0);
					cursoId = Long.parseLong(cursoActual.getId());
					cursoNombre = cursoActual.getNombre();
					cursoHorario = cursoActual.getHorario() != null ? cursoActual.getHorario() : "";
					cursoCodigoSIA = cursoActual.getCodigoSIA() != null ? cursoActual.getCodigoSIA() : "";
					cursoFInicial = cursoActual.getFechaInicial();
					cursoFFinal = cursoActual.getFechaFinal();
					setCursoFInicial2(cursoActual.getFechaInicial2());
					setCursoFFinal2(cursoActual.getFechaFinal2());
					setCursoRequisitos(cursoActual.getRequisitos() != null ? cursoActual.getRequisitos() : "");
				}				

				if (cursoFInicial == null & cursoFFinal == null) {
					System.out.println("cursoFInicial: " + cursoFInicial);
					System.out.println("cursoFFinal: " + cursoFFinal);
					panelRender[9] = false;
				} else {
					panelRender[9] = true;
				}

				panelRender[2] = true;
				mensaje = "";
				panelMensaje = false;

			} else {
				panelRender[2] = false;
				setMostrarForm(false);
			}

		} catch (Exception e) {
			System.out.println("cargarCursoInicial: " + e.toString());
		}

	}

	public void cambiarCurso(ValueChangeEvent curso) {
		String idCurso = (String) curso.getNewValue();
		if (!idCurso.equals("0")) {
			panelRender[2] = true;
			String consulta = "select mc from ModuloCursoEscuela mc where mc.id='"
					+ idCurso + "' ";
			List l = servicioGeneral.obtenerObjetos(consulta);
			this.cursoActual = (ModuloCursoEscuela) l.get(0);
			cursoId = Long.parseLong(cursoActual.getId());
			cursoNombre = cursoActual.getNombre();
			cursoHorario = cursoActual.getHorario() != null ? cursoActual
					.getHorario() : "";
			cursoCodigoSIA = cursoActual.getCodigoSIA() != null ? cursoActual
					.getCodigoSIA() : "";
			cursoFInicial = cursoActual.getFechaInicial();
			cursoFFinal = cursoActual.getFechaFinal();
			setCursoFInicial2(cursoActual.getFechaInicial2());
			setCursoFFinal2(cursoActual.getFechaFinal2());
			setCursoRequisitos(cursoActual.getRequisitos() != null ? cursoActual
					.getRequisitos() : "");

			if (cursoCodigoSIA.equals("")) {
				panelRender[3] = false;
			} else {
				panelRender[3] = true;
			}

			if (cursoRequisitos.equals("")) {
				panelRender[4] = false;
			} else {
				panelRender[4] = true;
			}

			if (cursoFInicial == null & cursoFFinal == null) {
				panelRender[9] = false;
			} else {
				panelRender[9] = true;
			}

			panelRender[2] = true;
			mensaje = "";
			panelMensaje = false;
		} else {
			panelRender[2] = false;
			setMostrarForm(false);
		}
	}

	public void deshabilitarPanelRender() {
		panelRender[5] = false;
	}

	public void limpiarErrores() {
		errores = new String[60];
		panelRenderError = new boolean[60];
	}
	
	public void cambiaAceptaFormulario() {
		System.out.println("usaSoftwareInvestigacionBoolean:"+ aceptaFormulario);
	}

	public void enviar() {

		String participante = "";
		limpiarErrores();
		boolean bandera = true;
		boolean bandera2 = true;
		
		Date hoy = new Date();
		String fechaFormato = cambiarFormatoFecha(hoy,2);

		if (mostrarDatosPersona) { //ESTUDIANTE UN
			iei.setEstudianteUN("S");
			iei.setUniversidad("UNIVERSIDAD NACIONAL DE COLOMBIA");
			TipoPersonaEscuela tpe = new TipoPersonaEscuela(Long.parseLong(tipoParticipacion));
			iei.setTipoPersona(tpe);
			iei.setSemestre(semestre);
			iei.setCarrera(carrera);
			iei.setFacultad(facultad);
			iei.setPromedio(promedio);						

		}
		
		String consulta = "select mc from ModuloCursoEscuela mc where mc.id='" + idCurso + "'";
		List l = servicioGeneral.obtenerObjetos(consulta);
		cursoActual = (ModuloCursoEscuela)l.get(0);

		if (bandera && bandera2) {
			iei.setDocumento(documento);
			iei.setTipoDocumento(tipoDocumento.getNombre());
			iei.setNombres(nombres);
			iei.setApellidos(apellidos);
			iei.setCiudad(idCiudad);
			iei.setDireccion(direccion);
			iei.setTelefonoFijo(telefonoFijo);
			iei.setTelefonoMovil(telefonoMovil);
			iei.setCorreo(correo);
			iei.setDocumento(documento);
			iei.setTipoDocumento(tipoDocumento.getId());											
			iei.setCurso(new Long(Long.parseLong(cursoActual.getId())));
			iei.setEstado("PREINSCRITO");
			iei.setFecha(hoy);
			
			if(aceptaFormulario){
				String acepta = "1";
				iei.setAceptaFormulario(acepta);
			}else{
				String acepta = "0";
				iei.setAceptaFormulario(acepta);
			}
						
			iei.setMotivacion(motivacion);
			
			if (link != null && !link.equals(""))
				iei.setLink(link);

			try {
				servicioGeneral.guardarObjeto(iei);
				
				
				for (int i = 0; i < listaArchivosObligatoriosSel.size(); i++) {
					
					if (archivo.getContents() != null) {
						
						String directorio = "HER_INSCRIPCION_ESC_INT"  ;
						cargarArchivoDisco(archivo.getInputstream(), directorio, iei.getId().toString());
						
					}
				
				}

				// Envio de correo
				String mensajeConfirmacion = "Su preinscipción ha sido enviada con éxito"
						+ "\n"
						+ "Estos son los datos que ha ingresado: "
						+ "\n"
						+ "\n"
						+ "Exámen: "
						+ cursoActual.getNombre()
						+ "\n"
						+ "Nombre: "
						+ iei.getNombres()
						+ " "
						+ iei.getApellidos()
						+ "\n"
						+ "Documento: "
						+ iei.getTipoDocumento()
						+ ". "
						+ iei.getDocumento()
						+ "\n";
				
				mensajeConfirmacion = mensajeConfirmacion + "Semestre: "
						+ iei.getSemestre() + "\n" + "Carrera: "
						+ iei.getCarrera() + "\n" + "Facultad: "
						+ iei.getFacultad() + "\n" + "Universidad: "
						+ iei.getUniversidad() + "\n" + "Promedio "
						+ iei.getPromedio() + "\n" + "Ciudad: "
						+ ciudad + "\n" + "Dirección: "
						+ iei.getDireccion() + "\n" + "Teléfono fijo: "
						+ iei.getTelefonoFijo() + "\n" + "Teléfono móvil: "
						+ iei.getTelefonoMovil() + "\n" + "Correo: "
						+ iei.getCorreo() + "\n";			

				Correo correo = new Correo();
				correo.setOrigen(Correo.CORREO_HERMES);
				correo.adicionarDireccion(iei.getCorreo());
				
				String consulta2 = "select dd from DominioDetalle dd where dd.identificador.tipo = '"+ iei.getDocumento() + "'";
				List lista = servicioGeneral.obtenerObjetos(consulta2);

				if (lista.size() > 0) {
					DominioDetalle dominio = (DominioDetalle) lista.get(0);
					String correoUsuario = dominio.getEstado();
					correo.adicionarCopiaOculta(correoUsuario);
				}
				//correo.adicionarCopiaOculta("sisii_nal@unal.edu.co");

				correo.setAsunto("Preinscripción Realizada");
				correo.setCuerpo(mensajeConfirmacion);
				servicioCorreo.enviarCorreo(correo);

				setMostrarDatosPersona(false);
				setMostrarDatosPersona2(false);
				setMostrarForm(false);
				panelRender[6] = true;
				panelRender[5] = false;
				panelRender[7] = false;
				cargarValoresIniciales();
				
				mostrarBotonSalir = true;
				
				// limpiarErrores();
				// iei = new InscripcionEscuelaInternacional();
				mensaje = "Preinscripción guardada con éxito";
				FacesContext.getCurrentInstance().addMessage(
						"mensaje",
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Preinscripción guardada con éxito", ""));
				FacesContext
						.getCurrentInstance()
						.addMessage(
								"mensajeGrowl",
								new FacesMessage(FacesMessage.SEVERITY_INFO,
										"Preinscripcion guardada con exito",
										"Se ha enviado un correo electronico a su cuenta con los datos registrados"));
				panelMensaje = true;

			} catch (Exception e) {
				System.out.println(e.toString());
			}
		} 
	}
	
	private void cargarTipoArchivos() {
		listaArchivosObligatorios = servicioGeneral
				.obtenerListaObjetos("DominioDetalle where identificador.id = '18' ORDER BY identificador.tipo");

		if (listaArchivosObligatorios != null
				&& listaArchivosObligatorios.size() > 0) {
			tipoDocumentoSelItem = new SelectItem[listaArchivosObligatorios
					.size()];
			for (int i = 0; i < listaArchivosObligatorios.size(); i++) {
				DominioDetalle tae = (DominioDetalle) listaArchivosObligatorios
						.get(i);
				tipoDocumentoSelItem[i] = new SelectItem(tae.getIdentificador()
						.getTipo(), tae.getDescripcion());
			}
		}
	}

	// get & set

	public String[] getErrores() {
		return errores;
	}

	public void setErrores(String[] errores) {
		this.errores = errores;
	}

	public boolean[] getPanelRender() {
		return panelRender;
	}

	public void setPanelRender(boolean[] panelRender) {
		this.panelRender = panelRender;
	}

	public boolean[] getPanelRenderError() {
		return panelRenderError;
	}

	public void setPanelRenderError(boolean[] panelRenderError) {
		this.panelRenderError = panelRenderError;
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

	public List getListaCursos() {
		return listaCursos;
	}

	public void setListaCursos(List listaCursos) {
		this.listaCursos = listaCursos;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public SelectItem[] getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}

	public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
		this.tipoDocumentoItem = tipoDocumentoItem;
	}

	public Persona getPersonaActual() {
		return personaActual;
	}

	public void setPersonaActual(Persona personaActual) {
		this.personaActual = personaActual;
	}

	public boolean isMostrarDatosPersona() {
		return mostrarDatosPersona;
	}

	public void setMostrarDatosPersona(boolean mostrarDatosPersona) {
		this.mostrarDatosPersona = mostrarDatosPersona;
	}

	public String getIdCiudad() {
		return idCiudad;
	}

	public void setIdCiudad(String idCiudad) {
		this.idCiudad = idCiudad;
	}

	public InscripcionEscuelaInternacional getIei() {
		return iei;
	}

	public void setIei(InscripcionEscuelaInternacional iei) {
		this.iei = iei;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getTelefonoFijo() {
		return telefonoFijo;
	}

	public void setTelefonoFijo(String telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}

	public String getTelefonoMovil() {
		return telefonoMovil;
	}

	public void setTelefonoMovil(String telefonoMovil) {
		this.telefonoMovil = telefonoMovil;
	}

	public String getFacultad() {
		return facultad;
	}

	public void setFacultad(String facultad) {
		this.facultad = facultad;
	}

	public String getTipoAspirante() {
		return tipoAspirante;
	}

	public void setTipoAspirante(String tipoAspirante) {
		this.tipoAspirante = tipoAspirante;
	}

	public SelectItem[] getTipoAspiranteItem() {
		return tipoAspiranteItem;
	}

	public void setTipoAspiranteItem(SelectItem[] tipoAspiranteItem) {
		this.tipoAspiranteItem = tipoAspiranteItem;
	}

	public Long getSemestre() {
		return semestre;
	}

	public void setSemestre(Long semestre) {
		this.semestre = semestre;
	}

	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	public String getUniversidad() {
		return universidad;
	}

	public void setUniversidad(String universidad) {
		this.universidad = universidad;
	}

	public String getPromedio() {
		return promedio;
	}

	public void setPromedio(String promedio) {
		this.promedio = promedio;
	}

	public SelectItem[] getPromedioItem() {
		return promedioItem;
	}

	public void setPromedioItem(SelectItem[] promedioItem) {
		this.promedioItem = promedioItem;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public boolean isMostrarDatosPersona2() {
		return mostrarDatosPersona2;
	}

	public void setMostrarDatosPersona2(boolean mostrarDatosPersona2) {
		this.mostrarDatosPersona2 = mostrarDatosPersona2;
	}

	public List getListaTipoParticipacion() {
		return ListaTipoParticipacion;
	}

	public void setListaTipoParticipacion(List listaTipoParticipacion) {
		ListaTipoParticipacion = listaTipoParticipacion;
	}

	public String getTipoParticipacion() {
		return tipoParticipacion;
	}

	public void setTipoParticipacion(String tipoParticipacion) {
		this.tipoParticipacion = tipoParticipacion;
	}

	public SelectItem[] getCursoItem() {
		return cursoItem;
	}

	public void setCursoItem(SelectItem[] cursoItem) {
		this.cursoItem = cursoItem;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public boolean isMostrarForm() {
		return mostrarForm;
	}

	public void setMostrarForm(boolean mostrarForm) {
		this.mostrarForm = mostrarForm;
	}

	public String getCursoNombre() {
		return cursoNombre;
	}

	public void setCursoNombre(String cursoNombre) {
		this.cursoNombre = cursoNombre;
	}

	public Date getCursoFInicial() {
		return cursoFInicial;
	}

	public void setCursoFInicial(Date cursoFInicial) {
		this.cursoFInicial = cursoFInicial;
	}

	public Date getCursoFFinal() {
		return cursoFFinal;
	}

	public void setCursoFFinal(Date cursoFFinal) {
		this.cursoFFinal = cursoFFinal;
	}

	public List getListaTipoParticipacion1() {
		return ListaTipoParticipacion1;
	}

	public void setListaTipoParticipacion1(List listaTipoParticipacion1) {
		ListaTipoParticipacion1 = listaTipoParticipacion1;
	}

	public String getTipoParticipacion1() {
		return tipoParticipacion1;
	}

	public void setTipoParticipacion1(String tipoParticipacion1) {
		this.tipoParticipacion1 = tipoParticipacion1;
	}

	public Long getCursoId() {
		return cursoId;
	}

	public void setCursoId(Long cursoId) {
		this.cursoId = cursoId;
	}

	public Long getValorCurso() {
		return valorCurso;
	}

	public void setValorCurso(Long valorCurso) {
		this.valorCurso = valorCurso;
	}

	public ModuloCursoEscuela getCursoActual() {
		return cursoActual;
	}

	public void setCursoActual(ModuloCursoEscuela cursoActual) {
		this.cursoActual = cursoActual;
	}

	public String getCursoHorario() {
		return cursoHorario;
	}

	public void setCursoHorario(String cursoHorario) {
		this.cursoHorario = cursoHorario;
	}

	public String getCursoCodigoSIA() {
		return cursoCodigoSIA;
	}

	public void setCursoCodigoSIA(String cursoCodigoSIA) {
		this.cursoCodigoSIA = cursoCodigoSIA;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public Date getCursoFInicial2() {
		return cursoFInicial2;
	}

	public void setCursoFInicial2(Date cursoFInicial2) {
		this.cursoFInicial2 = cursoFInicial2;
	}

	public Date getCursoFFinal2() {
		return cursoFFinal2;
	}

	public void setCursoFFinal2(Date cursoFFinal2) {
		this.cursoFFinal2 = cursoFFinal2;
	}

	public Long getValorCurso2() {
		return valorCurso2;
	}

	public void setValorCurso2(Long valorCurso2) {
		this.valorCurso2 = valorCurso2;
	}

	public String getTipoDocumentoSel() {
		return tipoDocumentoSel;
	}

	public void setTipoDocumentoSel(String tipoDocumentoSel) {
		this.tipoDocumentoSel = tipoDocumentoSel;
	}

	public SelectItem[] getTipoDocumentoSelItem() {
		return tipoDocumentoSelItem;
	}

	public void setTipoDocumentoSelItem(SelectItem[] tipoDocumentoSelItem) {
		this.tipoDocumentoSelItem = tipoDocumentoSelItem;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public boolean isPanelMensaje() {
		return panelMensaje;
	}

	public void setPanelMensaje(boolean panelMensaje) {
		this.panelMensaje = panelMensaje;
	}

	public String getCursoRequisitos() {
		return cursoRequisitos;
	}

	public void setCursoRequisitos(String cursoRequisitos) {
		this.cursoRequisitos = cursoRequisitos;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public boolean isPublico() {
		return publico;
	}

	public void setPublico(boolean publico) {
		this.publico = publico;
	}

	public String getProfesion() {
		return profesion;
	}

	public void setProfesion(String profesion) {
		this.profesion = profesion;
	}

	public String getCursoPadre() {
		return cursoPadre;
	}

	public void setCursoPadre(String cursoPadre) {
		this.cursoPadre = cursoPadre;
	}

	public String getCursoTitulo() {
		return cursoTitulo;
	}

	public void setCursoTitulo(String cursoTitulo) {
		this.cursoTitulo = cursoTitulo;
	}

	public String getCursoLabel() {
		return cursoLabel;
	}

	public void setCursoLabel(String cursoLabel) {
		this.cursoLabel = cursoLabel;
	}	

	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public List getListaModalidad() {
		return ListaModalidad;
	}

	public void setListaModalidad(List listaModalidad) {
		ListaModalidad = listaModalidad;
	}

	public boolean isMostrarModalidad() {
		return mostrarModalidad;
	}

	public void setMostrarModalidad(boolean mostrarModalidad) {
		this.mostrarModalidad = mostrarModalidad;
	}

	public boolean isPreinscripcionYaExiste() {
		return preinscripcionYaExiste;
	}

	public void setPreinscripcionYaExiste(boolean preinscripcionYaExiste) {
		this.preinscripcionYaExiste = preinscripcionYaExiste;
	}

	public String getMensajeCancelacion() {
		return mensajeCancelacion;
	}

	public void setMensajeCancelacion(String mensajeCancelacion) {
		this.mensajeCancelacion = mensajeCancelacion;
	}

	public boolean isMostrarPanelCancelacion() {
		return mostrarPanelCancelacion;
	}

	public void setMostrarPanelCancelacion(boolean mostrarPanelCancelacion) {
		this.mostrarPanelCancelacion = mostrarPanelCancelacion;
	}

	public boolean isEsCursoBio() {
		return esCursoBio;
	}

	public void setEsCursoBio(boolean esCursoBio) {
		this.esCursoBio = esCursoBio;
	}

	public InscripcionEscuelaInternacional getIei_existente() {
		return iei_existente;
	}

	public void setIei_existente(InscripcionEscuelaInternacional iei_existente) {
		this.iei_existente = iei_existente;
	}

	public boolean isMostrarBotonSalir() {
		return mostrarBotonSalir;
	}

	public void setMostrarBotonSalir(boolean mostrarBotonSalir) {
		this.mostrarBotonSalir = mostrarBotonSalir;
	}
	
	public boolean isDeshabilitaDocumento() {
		return habilitaDocumento;
	}

	public void setDeshabilitaDocumento(boolean deshabilitaDocumento) {
		this.habilitaDocumento = deshabilitaDocumento;
	}

	public boolean isHabilitaDocumento() {
		return habilitaDocumento;
	}

	public void setHabilitaDocumento(boolean habilitaDocumento) {
		this.habilitaDocumento = habilitaDocumento;
	}

	public List getListaExamen() {
		return listaExamen;
	}

	public void setListaExamen(List listaExamen) {
		this.listaExamen = listaExamen;
	}

	public String getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(String idCurso) {
		this.idCurso = idCurso;
	}

	public boolean isAceptaFormulario() {
		return aceptaFormulario;
	}

	public void setAceptaFormulario(boolean aceptaFormulario) {
		this.aceptaFormulario = aceptaFormulario;
	}

	public String getMotivacion() {
		return motivacion;
	}

	public void setMotivacion(String motivacion) {
		this.motivacion = motivacion;
	}

	public boolean isBloquearExamen() {
		return bloquearExamen;
	}

	public void setBloquearExamen(boolean bloquearExamen) {
		this.bloquearExamen = bloquearExamen;
	}

	public List getListaArchivosObligatorios()
	{
		return listaArchivosObligatorios;
	}

	public void setListaArchivosObligatorios(List listaArchivosObligatorios)
	{
		this.listaArchivosObligatorios = listaArchivosObligatorios;
	}

	public DefaultUploadedFile getArchivoObligatorio()
	{
		return archivoObligatorio;
	}

	public void setArchivoObligatorio(DefaultUploadedFile archivoObligatorio)
	{
		this.archivoObligatorio = archivoObligatorio;
	}

	public DefaultUploadedFile getArchivoObligatorioSeleccionado()
	{
		return archivoObligatorioSeleccionado;
	}

	public void setArchivoObligatorioSeleccionado(DefaultUploadedFile archivoObligatorioSeleccionado)
	{
		this.archivoObligatorioSeleccionado = archivoObligatorioSeleccionado;
	}

	public List getListaArchivosObligatoriosSel()
	{
		return listaArchivosObligatoriosSel;
	}

	public void setListaArchivosObligatoriosSel(List listaArchivosObligatoriosSel)
	{
		this.listaArchivosObligatoriosSel = listaArchivosObligatoriosSel;
	}

	public UploadedFile getArchivo()
	{
		return archivo;
	}

	public void setArchivo(UploadedFile archivo)
	{
		this.archivo = archivo;
	}
	
	

}
