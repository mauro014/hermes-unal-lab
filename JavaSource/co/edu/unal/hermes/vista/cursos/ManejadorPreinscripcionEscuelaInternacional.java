package co.edu.unal.hermes.vista.cursos;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.DefaultUploadedFile;

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
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorPreinscripcionEscuelaInternacional extends ManejadorBase {

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
	private DefaultUploadedFile archivoObligatorio;
	private DefaultUploadedFile archivoObligatorioSeleccionado;
	private InscripcionEscuelaInternacional archivoObligatorioSeleccionado2;
	private List listaArchivosObligatoriosSel;
	private List listaArchivosObligatorios;
	private DataTable tablaArchivosObligatoriosSel;
	private boolean habilitaDocumento;
	private String modalidad;
	private List ListaModalidad;
	private boolean mostrarModalidad;
	private boolean preinscripcionYaExiste;
	private String mensajeCancelacion;
	private boolean mostrarPanelCancelacion;
    private boolean esCursoBio = true;
    private boolean mostrarBotonSalir; 
	

	public ManejadorPreinscripcionEscuelaInternacional() {
		cargarValoresIniciales();
		cargarTiposDocumento();
		cargarTipoArchivos();
	}

	private void cargarValoresIniciales() {

		errores = new String[60];
		panelRender = new boolean[20];
		panelRenderError = new boolean[60];
		iei = new InscripcionEscuelaInternacional();
		personaActual = (Persona) sesion.getAttribute("persona");

		ListaTipoParticipacion1 = new Vector();
		ListaTipoParticipacion1.add(new SelectItem("Seleccione", "Seleccione"));
		ListaTipoParticipacion1.add(new SelectItem("0",
				"Estudiante de Pregrado UN"));
		ListaTipoParticipacion1.add(new SelectItem("1",
				"Estudiante de Posgrado UN"));
		ListaTipoParticipacion1.add(new SelectItem("2", "Egresado UN"));
		ListaTipoParticipacion1.add(new SelectItem("6","Docente UN"));

		ListaTipoParticipacion = new Vector();
		ListaTipoParticipacion.add(new SelectItem("Seleccione", "Seleccione"));
		ListaTipoParticipacion.add(new SelectItem("2", "Egresado UN"));
		ListaTipoParticipacion.add(new SelectItem("6","Docente UN"));
		ListaTipoParticipacion.add(new SelectItem("3",
				"Estudiante de Pregrado de otra universidad"));
		ListaTipoParticipacion.add(new SelectItem("4",
				"Estudiante de Posgrado de otra universidad"));
		ListaTipoParticipacion.add(new SelectItem("5", "Público General"));

		ListaModalidad = new Vector();
		ListaModalidad.add(new SelectItem("CREDITOS", "Créditos"));
		ListaModalidad.add(new SelectItem("CERTIFICADO", "Certificado"));

		nombres = "";
		apellidos = "";
		ciudad = "";
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
		link = "";

		tipoParticipacion = "";
		tipoParticipacion1 = "";

		setMostrarDatosPersona(false);
		setMostrarDatosPersona2(false);

		listaArchivosObligatoriosSel = new ArrayList();
		// setMostrarForm(false);
		// cargarCursos();
		// if(cursoActual == null)
		cargarCursoInicial();

		habilitarDocumento();

		mostrarModalidad = false;
		preinscripcionYaExiste = false;
		mostrarPanelCancelacion = false;
		mostrarBotonSalir = false;
		if(cursoActual != null){
			if(cursoActual.getId().equals("1007"))
			{
				esCursoBio = true;
				modalidad = "CERTIFICADO";
			}
			else
			{
				esCursoBio = false;
				modalidad = "CREDITOS";
			}
		}
		
		
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
	
	public void cancelarCurso()
	{
		Date hoy = new Date();
		String fechaFormato = cambiarFormatoFecha(hoy,2);
		
		if(iei_existente != null)
		{
			if(iei_existente.getEstado().equals("PREINSCRITO"))
			{	
				iei_existente.setFechaCancela(hoy);
				
				String mensajeConfirmacion = "Su solicitud de cancelación ha sido procesada con éxito"
						+ "\n"
						+ "Estos son los datos que ha ingresado: "
						+ "\n"
						+ "\n"
						+ "Curso / Exámen: "
						+ cursoNombre
						+ "\n"
						+ "Nombre: "
						+ iei_existente.getNombres()
						+ " "
						+ iei_existente.getApellidos()
						+ "\n"
						+ "Documento: "
						+ iei_existente.getTipoDocumento()
						+ ". "
						+ iei_existente.getDocumento()
						+ "\n"
						+ "Fecha cancelación: "
						+ fechaFormato
						+ "\n";
				
				if (hoy.after(cursoActual.getFecIniCancelaSinPerdida())
						&& hoy.before(cursoActual.getFecFinCancelaSinPerdida())) 
				{
					iei_existente.setEstado("CANCELA SIN PERDIDA");
					mensajeConfirmacion = mensajeConfirmacion + "\n De acuerdo a las fechas establecidas, se ha realizado la cancelación SIN perdida de créditos";
				} else if (hoy.after(cursoActual.getFecIniCancelaConPerdida())
						&& hoy.before(cursoActual.getFecFinCancelaConPerdida())) {
					iei_existente.setEstado("CANCELA CON PERDIDA");
					mensajeConfirmacion = mensajeConfirmacion + "\n De acuerdo a las fechas establecidas, se ha realizado la cancelación CON perdida de créditos";
				} else if (hoy.after(cursoActual.getFecIniCancelaOtro())
						&& hoy.before(cursoActual.getFecFinCancelaOtro())) {
					iei_existente.setEstado("CANCELA OTRO");
					mensajeConfirmacion = mensajeConfirmacion + "\n De acuerdo a las fechas establecidas, se ha realizado la cancelación CON perdida de créditos por motivos de fuerza mayor";
				} else {
					mensajeCancelacion = "";
				}
				
				try{
					servicioGeneral.guardarObjeto(iei_existente);
					
					Correo correo = new Correo();
					correo.setOrigen(Correo.CORREO_HERMES);
					correo.adicionarDireccion(iei_existente.getCorreo());
		
					// String consulta =
					// "select dd from DominioDetalle dd where dd.identificador.tipo = '"
					// + personaActual.getId().getDocumento() + "'";
					String consulta = "select dd from DominioDetalle dd where dd.identificador.tipo = '"
							+ iei_existente.getDocumento() + "'";
					List lista = servicioGeneral.obtenerObjetos(consulta);
		
					if (lista.size() > 0) {
						DominioDetalle dominio = (DominioDetalle) lista.get(0);
						String correoUsuario = dominio.getEstado();
						correo.adicionarCopiaOculta(correoUsuario);
					}
		
					correo.adicionarCopiaOculta("sisii_nal@unal.edu.co");
					correo.adicionarCopiaOculta("escuelaint@unal.edu.co");
		
					correo.setAsunto("Cancelacion Realizada - Escuela Internacional");
					correo.setCuerpo(mensajeConfirmacion);
					servicioCorreo.enviarCorreo(correo);
					
					limpiar();
					
					mostrarBotonSalir = true;
					
					FacesContext
					.getCurrentInstance()
					.addMessage(
							"mensajeGrowl",
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Solicitud de cancelacion procesada con exito",
									"Se ha enviado un correo electronico a su cuenta con los datos registrados"));
					
				}
				catch(Exception e)
				{
					FacesContext
					.getCurrentInstance()
					.addMessage(
							"mensajeGrowl",
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Solicitud de cancelacion no ha sido procesada",
									"Ha ocurrido un problema con la solicitud de cancelación. Por favor intentelo de nuevo"));
					limpiar();
					System.out.println("Cancelar curso: " + e.toString());
				}
			
			}
			else
			{
				FacesContext
				.getCurrentInstance()
				.addMessage(
						"mensajeGrowl",
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Solicitud de cancelacion no ha sido procesada",
								"Usted ya realizo una solicitud de cancelacion en la fecha: " + cambiarFormatoFecha(iei_existente.getFechaCancela(),1)));
				
				limpiar();
			}
		}
	}

	public void habilitarDocumento() {
		habilitaDocumento = false;
	}

	public boolean existePreinscripcion(TipoDocumento tipoDoc, String doc,
			String idCurso) {
		String consulta = "Select pp from InscripcionEscuelaInternacional pp where pp.tipoDocumento like '"
				+ tipoDoc.getId()
				+ "' and pp.documento like '"
				+ doc
				+ "' and pp.curso = " + Long.parseLong(idCurso);
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

		preinscripcionYaExiste = existePreinscripcion(tipoDocumento, documento,
				cursoActual.getId());
		Date hoy = new Date();

		if (!preinscripcionYaExiste) {
			reiniciarVariables();
			IdPersona id = new IdPersona();
			id.setDocumento(documento);
			id.setTipoDocumento(tipoDocumento.getId());

			Estudiante estudiante = servicioPersona.obtenerEstudiante(id);

			if (estudiante != null) {

				// setIdCiudad(persona.getCiudadDomicilio() == null ? null :
				// persona.getCiudadDomicilio().getId());
				nombres = estudiante.getNombre1() + " "
						+ estudiante.getNombre2();
				apellidos = estudiante.getApellido1() + " "
						+ estudiante.getApellido2();
				// ciudad = estudiante.getCiudadDomicilio().getNombre();

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
				} catch (Exception e) {
					direccion = "";
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

			} else {
				estudiante = new Estudiante();
				IdPersona idP = new IdPersona();
				idP.setDocumento(documento);
				idP.setTipoDocumento(tipoDocumento.getId());
				estudiante.setId(idP);
				// errores[2] = "El documento ingresado no existe";
				// panelRenderError[2] = true;
				setMostrarDatosPersona(false);
				setMostrarDatosPersona2(true);
				panelRender[2] = true;
			}

			habilitaDocumento = true;

		} else {
			FacesContext
					.getCurrentInstance()
					.addMessage(
							"mensajeGrowl",
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"",
									"El numero de documento "
											+ documento
											+ " ya tiene asociada una preinscripcion para el curso '"
											+ cursoActual.getNombre() + "'"));

			if (hoy.after(cursoActual.getFecIniCancelaSinPerdida())
					&& hoy.before(cursoActual.getFecFinCancelaSinPerdida())) {
				mensajeCancelacion = "El curso se encuentra en el periodo de cancelaciones SIN perdida de creditos. \n\n ¿Desea cancelar el curso?";
				mostrarPanelCancelacion = true;
			} else if (hoy.after(cursoActual.getFecIniCancelaConPerdida())
					&& hoy.before(cursoActual.getFecFinCancelaConPerdida())) {
				mensajeCancelacion = "El curso se encuentra en el periodo de cancelaciones CON perdida de creditos. \n\n ¿Desea cancelar el curso?";
				mostrarPanelCancelacion = true;
			} else if (hoy.after(cursoActual.getFecIniCancelaOtro())
					&& hoy.before(cursoActual.getFecFinCancelaOtro())) {
				mensajeCancelacion = "El curso se encuentra en el periodo de cancelaciones CON perdida de creditos bajo circunstancias de fuerza mayor. \n\n ¿Desea cancelar el curso CON perdida de créditos?";
				mostrarPanelCancelacion = true;
			} else {
				mensajeCancelacion = "";
			}
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
		iei = new InscripcionEscuelaInternacional();
		iei_existente = new InscripcionEscuelaInternacional();
		documento = "";
		cargarValoresIniciales();
		ocultarPaneles();
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
		List listaTipoDocumento = servicioGeneral
				.obtenerListaObjetos("TipoDocumento");
		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			TipoDocumento td = (TipoDocumento) listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
		}
		tipoDocumento = (TipoDocumento) listaTipoDocumento.get(0);
	}

	/*
	 * private void cargarCursos(){ errores[2]=""; List listaCurso; List
	 * listaCursoAux; listaCurso = new ArrayList<ModuloCursoEscuela>();
	 * listaCursoAux = new ArrayList();
	 * 
	 * ModuloCursoEscuela mce = new ModuloCursoEscuela();
	 * 
	 * Calendar actual = Calendar.getInstance(); int annoActual =
	 * actual.get(Calendar.YEAR); SimpleDateFormat Formato = new
	 * SimpleDateFormat("dd/MM/yyyy"); String fechaActual =
	 * Formato.format(Calendar.getInstance().getTime());
	 * 
	 * //String hql = "select d from Dominio d where d.tipo like '%_" +
	 * annoActual +"' and d.estado='A'" ; String hql =
	 * "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo = 'CURSOS' and dd.estado='A'"
	 * ;
	 * 
	 * List l = servicioGeneral.obtenerObjetos(hql);
	 * System.out.println("LISTA DOMINIO" + l.size());
	 * 
	 * List lids = new ArrayList(); // Lista con los identificadores de los
	 * cursos vigentes
	 * 
	 * for (int i=0;i<l.size();i++){ DominioDetalle dom = new DominioDetalle();
	 * dom = (DominioDetalle) l.get(i);
	 * lids.add(dom.getIdentificador().getTipo()); }
	 * 
	 * String consultaCursos =
	 * "select c from ModuloCursoEscuela c where c.dominio = '";
	 * 
	 * for (int j=0; j<lids.size();j++){ //List l2 =
	 * servicioGeneral.obtenerObjetos(consultaCursos + lids.get(j) +
	 * "' and c.fechaFinal > TO_DATE('" + fechaActual + "')" ); List l2 =
	 * servicioGeneral.obtenerObjetos(consultaCursos + lids.get(j) +
	 * "' order by c.id" );
	 * 
	 * for(int k=0;k<l2.size();k++){ ModuloCursoEscuela mc = new
	 * ModuloCursoEscuela(); mc = (ModuloCursoEscuela) l2.get(k);
	 * listaCurso.add(mc); }
	 * 
	 * }
	 * 
	 * if (listaCurso != null ){ cursoItem = new SelectItem[listaCurso.size()];
	 * int z=0; for (int i = 0; i < listaCurso.size(); i++) { ModuloCursoEscuela
	 * mce1 = (ModuloCursoEscuela) listaCurso.get(i); cursoItem[i] = new
	 * SelectItem(mce1.getId(), mce1.getNombre());
	 * 
	 * z=z+1; } }
	 * 
	 * }
	 */

	public String regresarCursos() {
		sesion.removeAttribute("ManejadorPreinscripcionEscuelaInternacional");
		return "escuelaInternacional2015";
	}

	private void cargarCursos() {
		errores[2] = "";
		List listaCurso;
		List listaCursoAux;
		listaCurso = new ArrayList<ModuloCursoEscuela>();
		listaCursoAux = new ArrayList();

		listaCurso = servicioGeneral
				.obtenerObjetoXID("ModuloCursoEscuela", "0");

		ModuloCursoEscuela mce = new ModuloCursoEscuela();

		Calendar actual = Calendar.getInstance();
		int annoActual = actual.get(Calendar.YEAR);
		SimpleDateFormat Formato = new SimpleDateFormat("dd/MM/yyyy");
		String fechaActual = Formato.format(Calendar.getInstance().getTime());

		// String hql =
		// "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo = 'CURSOS' and dd.estado='A'"
		// ;
		String hql = "select dd from Dominio d, DominioDetalle dd, Parametro p where d.id = dd.identificador.id and d.tipo = 'CURSOS' and dd.estado='A' and p.nombre = dd.identificador.tipo  and p.descripcion = '"
				+ personaActual.getId().getDocumento() + "'";
		List l = servicioGeneral.obtenerObjetos(hql);

		String hqlPar = "select p from Parametro p where p.descripcion = '"
				+ personaActual.getId().getDocumento() + "'";
		List lp = servicioGeneral.obtenerObjetos(hqlPar);
		if (lp.size() > 0) {
			Parametro param = (Parametro) lp.get(0);
			setCursoTitulo(param.getValor());
			cursoLabel = param.getProfesion();
		}

		List lids = new ArrayList(); // Lista con los identificadores de los
		// cursos vigentes

		for (int i = 0; i < l.size(); i++) {
			DominioDetalle dom = new DominioDetalle();
			dom = (DominioDetalle) l.get(i);
			cursoPadre = dom.getDescripcion();
			lids.add(dom.getIdentificador().getTipo());
		}

		String consultaCursos = "select c from ModuloCursoEscuela c where c.dominio = '";

		for (int j = 0; j < lids.size(); j++) {
			// List l2 = servicioGeneral.obtenerObjetos(consultaCursos +
			// lids.get(j) + "' and c.fechaFinal > TO_DATE('" + fechaActual +
			// "')" );
			List l2 = servicioGeneral.obtenerObjetos(consultaCursos
					+ lids.get(j) + "' order by c.id");

			for (int k = 0; k < l2.size(); k++) {
				ModuloCursoEscuela mc = new ModuloCursoEscuela();
				mc = (ModuloCursoEscuela) l2.get(k);
				listaCurso.add(mc);
			}

		}

		if (listaCurso != null) {
			cursoItem = new SelectItem[listaCurso.size()];
			int z = 0;
			for (int i = 0; i < listaCurso.size(); i++) {
				ModuloCursoEscuela mce1 = (ModuloCursoEscuela) listaCurso
						.get(i);
				cursoItem[i] = new SelectItem(mce1.getId(), mce1.getNombre());

				z = z + 1;
			}
		}

	}

	public void registro() {
		setMostrarForm(true);
		mensaje = "";
		panelMensaje = false;
	}

	public void verArchivos() {
		panelRender[7] = true;
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
					this.iei.setBytes(archivoObligatorio.getContents());
					this.iei.setSoporte1Nombre(archivoObligatorio.getFileName()
							.substring(i + 1));
					this.iei.setFecha(new Date());
					// ieiAux.setTipoArchivo(tipoDocumentoSel);
					listaArchivosObligatoriosSel.add(iei);
				}

			}

		} catch (Exception x) {
			System.out.println(x.toString());

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

			String idCurso = "";

			if (cursoActual == null) {
				if (this.request.getParameter("idCurso") != null
						&& !this.request.getParameter("idCurso").equals("")) {
					idCurso = request.getParameter("idCurso");
					System.out.println("this.request.getParameter(idCurso): "
							+ this.request.getParameter("idCurso"));
				} else {
					idCurso = "";
					System.out.println("this.request.getParameter(idCurso): "
							+ this.request.getParameter("idCurso"));
				}
			} else
				idCurso = cursoActual.getId();
			System.out.println("idCurso: " + idCurso);

			if (!idCurso.equals("0")) {
				if (cursoActual == null) {
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
				}

				if (cursoCodigoSIA.equals("")) {
					System.out.println("cursoCodigoSIA: " + cursoCodigoSIA);
					panelRender[3] = false;
				} else {
					panelRender[3] = true;
				}

				if (cursoRequisitos.equals("")) {
					System.out.println("cursoRequisitos: " + cursoRequisitos);
					panelRender[4] = false;
				} else {
					panelRender[4] = true;
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

	public void cambiarTipoParticipacion(ValueChangeEvent participac) {
		String participacion = (String) participac.getNewValue();
		if (participacion.equals("5") || participacion.equals("2") || participacion.equals("6")) {
			this.publico = true;
		} else {
			this.publico = false;
		}
		// deshabilitarPanelRender();
		actualizarValor(participac);
	}

	public void deshabilitarPanelRender() {
		panelRender[5] = false;
	}

	public void actualizarValor(ValueChangeEvent participac) {
		String participacion = (String) participac.getNewValue();

		if (mostrarDatosPersona) {
			if (participacion.equals("Seleccione") || participacion.equals("0")
					|| participacion.equals("1")) {
				setValorCurso(0L);
				setValorCurso2(0L);

				mostrarModalidad = true;
			}else if(participacion.equals("6"))
			{
				setValorCurso(0L);
				setValorCurso2(0L);
				mostrarModalidad = false;
			} 
			else if (participacion.equals("2")) {
				setValorCurso(this.cursoActual.getEgresado_un());
				setValorCurso2(this.cursoActual.getEgresado_un2());

				mostrarModalidad = false;
			} else {
				mostrarModalidad = false;
			}
		} else {
			if (participacion.equals("2")) {
				setValorCurso(this.cursoActual.getEgresado_un());
				setValorCurso2(this.cursoActual.getEgresado_un2());
			} else if (participacion.equals("3")) {
				setValorCurso(this.cursoActual.getPregrado());
				setValorCurso2(this.cursoActual.getPregrado2());
			} else if (participacion.equals("4")) {
				setValorCurso(this.cursoActual.getPosgrado());
				setValorCurso2(this.cursoActual.getPosgrado2());
			} else if (participacion.equals("5")) {
				setValorCurso(Long.parseLong(this.cursoActual.getValor()));
				setValorCurso2(Long.parseLong(this.cursoActual.getValorDos()));
			} else if(participacion.equals("6")){
				setValorCurso(0L);
				setValorCurso2(0L);
			}	
			else {
					setValorCurso(0L);
					setValorCurso2(0L);
			}

		}

	}

	public void calcularValor() {

		if (mostrarDatosPersona) {
			if (tipoParticipacion1.equals("Seleccione")) {
				errores[4] = "Por favor seleccione el tipo de participante";
				FacesContext.getCurrentInstance().addMessage(
						"errores4",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
								"Seleccione el tipo de participante"));
				panelRenderError[4] = true;
				panelRender[5] = false;
			} else {
				errores[4] = "";
				panelRenderError[4] = false;
				panelRender[5] = true;
			}

			if (tipoParticipacion1.equals("2")) {
				setValorCurso(this.cursoActual.getEgresado_un());
				setValorCurso2(this.cursoActual.getEgresado_un2());
			} else {
				setValorCurso(0L);
				setValorCurso2(0L);
			}
		} else {

			boolean b = true;
			b = validar();
			if (b && panelRenderError[5] == false) {
				if (tipoParticipacion.equals("2")) {
					setValorCurso(this.cursoActual.getEgresado_un());
					setValorCurso2(this.cursoActual.getEgresado_un2());
				} else if (tipoParticipacion.equals("3")) {
					setValorCurso(this.cursoActual.getPregrado());
					setValorCurso2(this.cursoActual.getPregrado2());
				} else if (tipoParticipacion.equals("4")) {
					setValorCurso(this.cursoActual.getPosgrado());
					setValorCurso2(this.cursoActual.getPosgrado2());
				} else if (tipoParticipacion.equals("5")) {
					setValorCurso(Long.parseLong(this.cursoActual.getValor()));
					setValorCurso2(Long.parseLong(this.cursoActual
							.getValorDos()));
				} else if(tipoParticipacion.equals("6"))
				{
					setValorCurso(0L);
					setValorCurso2(0L);
				}

				errores[5] = "";
				panelRenderError[5] = false;
				panelRender[5] = true;
			} else {
				panelRender[5] = false;
			}

			if (tipoParticipacion.equals("Seleccione")) {
				errores[5] = "Por favor seleccione el tipo de participante";
				FacesContext.getCurrentInstance().addMessage(
						"erroresMostrarDatosPersona2",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
								"Seleccione el tipo de participante"));
				panelRenderError[5] = true;
				panelRender[5] = false;
			} else {
				errores[5] = "";
				panelRenderError[5] = false;

			}
		}
	}

	public void limpiarErrores() {
		errores = new String[60];
		panelRenderError = new boolean[60];
	}

	public boolean validar() {
		limpiarErrores();
		boolean b = true;

		if (nombres.equals("") || nombres.equals(" ") || nombres == null
				|| nombres.length() < 2) {
			errores[6] = "Los nombres NO son válidos";
			FacesContext.getCurrentInstance().addMessage(
					"erroresMostrarDatosPersona2",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
							"Los nombres NO son válidos"));
			panelRenderError[6] = true;
			b = false;
		} else {
			errores[6] = "";
			panelRenderError[6] = false;
		}
		
		if (apellidos.equals("") || apellidos.equals(" ") || apellidos == null) {
			errores[7] = "Los apellidos NO son válidos";
			FacesContext.getCurrentInstance().addMessage(
					"erroresMostrarDatosPersona2",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
							"Los apellidos NO son válidos"));
			panelRenderError[7] = true;
			b = false;
		} else {
			errores[7] = "";
			panelRenderError[7] = false;
		}

		if (this.tipoParticipacion.equals("5")
				|| this.tipoParticipacion.equals("2") || this.tipoParticipacion.equals("6")) {
			if (empresa.equals("") || empresa.equals(" ") || empresa == null) {
				errores[28] = "La universidad/ empresa NO es válida";
				FacesContext.getCurrentInstance().addMessage(
						"erroresMostrarDatosPersona2",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
								"La universidad/ empresa NO es válida"));
				panelRenderError[28] = true;
				b = false;
			} else {
				errores[28] = "";
				panelRenderError[28] = false;
			}
			if (cargo.equals("") || cargo.equals(" ") || cargo == null) {
				errores[29] = "El cargo NO es válido";
				FacesContext.getCurrentInstance().addMessage(
						"erroresMostrarDatosPersona2",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
								"El cargo NO es válido"));
				panelRenderError[29] = true;
				b = false;
			} else {
				errores[29] = "";
				panelRenderError[29] = false;
			}
			if (profesion.equals("") || profesion.equals(" ")
					|| profesion == null) {
				errores[30] = "La profesion NO es válida";
				FacesContext.getCurrentInstance().addMessage(
						"erroresMostrarDatosPersona2",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
								"La profesion NO es válida"));
				panelRenderError[30] = true;
				b = false;
			} else {
				errores[30] = "";
				panelRenderError[30] = false;
			}
		} else {
			if (semestre == null || semestre < 0) {
				errores[8] = "El semestre NO es válido";
				FacesContext.getCurrentInstance().addMessage(
						"erroresMostrarDatosPersona2",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
								"El semestre NO es válido"));
				panelRenderError[8] = true;
				b = false;
			} else {
				errores[8] = "";
				panelRenderError[8] = false;
			}
			
			if (carrera.equals("") || carrera.equals(" ") || carrera == null) {
				errores[9] = "La carrera NO es válida";
				FacesContext.getCurrentInstance().addMessage(
						"erroresMostrarDatosPersona2",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
								"La carrera NO es válida"));
				panelRenderError[9] = true;
				b = false;
			} else {
				errores[9] = "";
				panelRenderError[9] = false;
			}
			
			if (facultad.equals("") || facultad.equals(" ") || facultad == null) {
				errores[10] = "La facultad NO es válida";
				FacesContext.getCurrentInstance().addMessage(
						"erroresMostrarDatosPersona2",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
								"La facultad NO es válida"));
				panelRenderError[10] = true;
				b = false;
			} else {
				errores[10] = "";
				panelRenderError[10] = false;
			}
			
			if (universidad.equals("") || universidad.equals(" ")
					|| universidad == null) {
				errores[11] = "La universidad NO es válida";
				FacesContext.getCurrentInstance().addMessage(
						"erroresMostrarDatosPersona2",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
								"La universidad NO es válida"));
				panelRenderError[11] = true;
				b = false;
			} else {
				errores[11] = "";
				panelRenderError[11] = false;
			}
			
			if (promedio == null || promedio.equals("") || promedio.equals(" ")) {
				errores[12] = "El valor del promedio no es válido";
				FacesContext.getCurrentInstance().addMessage(
						"erroresMostrarDatosPersona2",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
								"El valor del promedio no es válido"));
				panelRenderError[12] = true;
				b = false;
			} else {
				try {
					float prom = Float.parseFloat(promedio);
					if (prom <= 0 || prom > 5) {
						errores[12] = "El valor del promedio no es válido";
						FacesContext
								.getCurrentInstance()
								.addMessage(
										"erroresMostrarDatosPersona2",
										new FacesMessage(
												FacesMessage.SEVERITY_ERROR,
												"",
												"El valor del promedio debe estar ser minimo 0.0 y maximo 5.0"));
						panelRenderError[12] = true;
						b = false;
					}
				} catch (Exception e) {
					errores[12] = "";
					FacesContext.getCurrentInstance().addMessage(
							"erroresMostrarDatosPersona2",
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
									"El valor del promedio debe ser numérico"));
					panelRenderError[12] = false;
				}
			}
		}

		if (ciudad.equals("") || ciudad.equals(" ") || ciudad == null) {
			errores[13] = "La ciudad NO  es válida";
			FacesContext.getCurrentInstance().addMessage(
					"erroresMostrarDatosPersona2",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
							"La ciudad NO  es válida"));
			panelRenderError[13] = true;
			b = false;
		} else {
			errores[13] = "";
			panelRenderError[13] = false;
		}
		if (direccion.equals("") || direccion.equals(" ") || direccion == null) {
			errores[14] = "La dirección NO es válida";
			FacesContext.getCurrentInstance().addMessage(
					"erroresMostrarDatosPersona2",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
							"La dirección NO es válida"));
			panelRenderError[14] = true;
			b = false;
		} else {
			errores[14] = "";
			panelRenderError[14] = false;
		}
		if (telefonoFijo.equals("") || telefonoFijo.equals(" ")
				|| telefonoFijo == null) {
			errores[15] = "El telefono fijo NO es válido";
			FacesContext.getCurrentInstance().addMessage(
					"erroresMostrarDatosPersona2",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
							"El telefono fijo NO es válido"));
			panelRenderError[15] = true;
			b = false;
		} else {
			try {
				Long tel = Long.parseLong(telefonoFijo);
				if (tel < 0) {
					errores[15] = "El telefono fijo NO es válido";
					FacesContext.getCurrentInstance().addMessage(
							"erroresMostrarDatosPersona2",
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
									"El telefono fijo NO es válido"));
					panelRenderError[15] = true;
					b = false;
				}
			} catch (Exception e) {
				errores[15] = "El telefono fijo NO es válido";
				FacesContext.getCurrentInstance().addMessage(
						"erroresMostrarDatosPersona2",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
								"El telefono fijo NO es válido"));
				panelRenderError[15] = true;
				b = false;
			}
		}
		if (telefonoMovil.equals("") || telefonoMovil.equals(" ")
				|| telefonoMovil == null) {
			errores[16] = "El telefono Movil NO es válido";
			FacesContext.getCurrentInstance().addMessage(
					"erroresMostrarDatosPersona2",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
							"El telefono movil NO es válido"));
			panelRenderError[16] = true;
			b = false;
		} else {
			try {
				Long tel = Long.parseLong(telefonoMovil);
				if (tel < 0) {
					errores[16] = "El telefono movil NO es válido";
					FacesContext.getCurrentInstance().addMessage(
							"erroresMostrarDatosPersona2",
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
									"El telefono movil NO es válido"));
					panelRenderError[16] = true;
					b = false;
				}
			} catch (Exception e) {
				errores[16] = "El telefono movil NO es válido";
				FacesContext.getCurrentInstance().addMessage(
						"erroresMostrarDatosPersona2",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
								"El telefono movil NO es válido"));
				panelRenderError[16] = true;
				b = false;
			}
		}

		// validar Correo

		if (correo.equals("") || correo.equals(" ") || correo == null) {
			errores[17] = "El correo NO es válido";
			FacesContext.getCurrentInstance().addMessage(
					"erroresMostrarDatosPersona2",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
							"El correo NO es válido"));
			panelRenderError[17] = true;
			b = false;
		} else {

			String input = correo;
			// comprueba que no empieze por punto o @
			// Pattern p =
			// Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
			Pattern p = Pattern
					.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
			Matcher m = p.matcher(input);
			if (!m.matches()) {
				errores[17] = "El correo NO es válido";
				FacesContext.getCurrentInstance().addMessage(
						"erroresMostrarDatosPersona2",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
								"El correo NO es válido"));
				panelRenderError[17] = true;
				b = false;
			}

			if (b == false) {
				// errores[17]= "El correo NO es válido";
				// panelRenderError[17]=true;
			} else {
				errores[17] = "";
				panelRenderError[17] = false;
			}
		}

		//
		if (pais.equals("") || pais.equals(" ") || pais == null) {
			errores[18] = "El país NO es válido";
			FacesContext.getCurrentInstance().addMessage(
					"erroresMostrarDatosPersona2",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
							"El país NO es válido"));
			panelRenderError[18] = true;
			b = false;
		} else {
			errores[18] = "";
			panelRenderError[18] = false;
		}
		
		if((tipoParticipacion1.equals("0") || tipoParticipacion1.equals("1")) && modalidad == null){
			
			FacesContext.getCurrentInstance().addMessage(
					"errores4",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
							"Debe seleccionar una modalidad [CRÉDITOS O CERTIFICADO]"));
		}

		return b;
	}

	public void eliminarArchivoObligatorio() {
		// InscripcionEscuelaInternacional amv =
		// (InscripcionEscuelaInternacional)
		// tablaArchivosObligatoriosSel.getRowData();
		// listaArchivosObligatoriosSel.remove(tablaArchivosObligatoriosSel.getRowIndex());
		listaArchivosObligatoriosSel.remove(archivoObligatorioSeleccionado2);
	}

	public void enviar() {
		// iei = new InscripcionEscuelaInternacional();

		String participante = "";
		limpiarErrores();
		boolean bandera = true;
		boolean bandera2 = true;

		// Validar archivos
		/*
		 * Se comenta temporalmente if (listaArchivosObligatoriosSel.size() == 0
		 * || listaArchivosObligatoriosSel.size() > 1) { errores[26] =
		 * "Por favor adjunte el documento de soporte, si es más de uno adjuntelos en un archivo comprimido (.zip o .rar)"
		 * ; panelRenderError[26] = true; bandera2 = false; } else { errores[26]
		 * = ""; panelRenderError[26] = false; bandera2 = true; }
		 */

		if (mostrarDatosPersona) {
			iei.setEstudianteUN("S");
			iei.setUniversidad("UNIVERSIDAD NACIONAL DE COLOMBIA");
			TipoPersonaEscuela tpe = new TipoPersonaEscuela(
					Long.parseLong(tipoParticipacion1));
			iei.setTipoPersona(tpe);
			iei.setSemestre(semestre);
			iei.setCarrera(carrera);
			iei.setFacultad(facultad);
			iei.setPromedio(promedio);
			
			if(modalidad != null)
			{
				if(tipoParticipacion1.equals("0") || tipoParticipacion1.equals("1"))
					iei.setModalidad(modalidad);
				else
					iei.setModalidad("NO APLICA");
			}	
			else
				iei.setModalidad("NO APLICA");
			// bandera = true;

		} else {
			iei.setEstudianteUN("N");
			TipoPersonaEscuela tpe = new TipoPersonaEscuela(
					Long.parseLong(tipoParticipacion));
			iei.setTipoPersona(tpe);
			bandera = validar();
			iei.setPais(pais);
			if (this.tipoParticipacion.equals("5")
					|| this.tipoParticipacion.equals("2") || this.tipoParticipacion.equals("6")) {
				iei.setEmpresa(empresa);
				iei.setCargo(cargo);
				iei.setProfesion(profesion);
			} else {
				iei.setSemestre(semestre);
				iei.setCarrera(carrera);
				iei.setFacultad(facultad);
				iei.setPromedio(promedio);
				iei.setUniversidad(universidad);
			}
			
			iei.setModalidad("NO APLICA");
		}

		if (bandera && bandera2) {
			iei.setDocumento(documento);
			iei.setTipoDocumento(tipoDocumento.getNombre());
			iei.setNombres(nombres);
			iei.setApellidos(apellidos);
			iei.setCiudad(ciudad);
			iei.setDireccion(direccion);
			iei.setTelefonoFijo(telefonoFijo);
			iei.setTelefonoMovil(telefonoMovil);
			iei.setCorreo(correo);
			iei.setDocumento(documento);
			iei.setTipoDocumento(tipoDocumento.getId());
			iei.setCurso(new Long(cursoId));
			iei.setEstado("PREINSCRITO");
			if (link != null && !link.equals(""))
				iei.setLink(link);

			try {
				servicioGeneral.guardarObjeto(iei);

				// Envio de correo
				String mensajeConfirmacion = "Su preinscipción ha sido enviada con éxito"
						+ "\n"
						+ "Estos son los datos que ha ingresado: "
						+ "\n"
						+ "\n"
						+ "Curso / Exámen: "
						+ cursoNombre
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

				if (this.tipoParticipacion.equals("5")
						|| this.tipoParticipacion.equals("2") || this.tipoParticipacion.equals("6")) {
					mensajeConfirmacion = mensajeConfirmacion + "Empresa:"
							+ iei.getEmpresa() + "\n" + "Cargo: "
							+ iei.getCargo() + "\n" + "Profesion: "
							+ iei.getProfesion() + "\n";
				} else {
					mensajeConfirmacion = mensajeConfirmacion + "Semestre: "
							+ iei.getSemestre() + "\n" + "Carrera: "
							+ iei.getCarrera() + "\n" + "Facultad: "
							+ iei.getFacultad() + "\n" + "Universidad: "
							+ iei.getUniversidad() + "\n" + "Promedio "
							+ iei.getPromedio() + "\n";
				}

				mensajeConfirmacion = mensajeConfirmacion + "Ciudad: "
						+ iei.getCiudad() + "\n" + "Dirección: "
						+ iei.getDireccion() + "\n" + "Teléfono fijo: "
						+ iei.getTelefonoFijo() + "\n" + "Teléfono móvil: "
						+ iei.getTelefonoMovil() + "\n" + "Correo: "
						+ iei.getCorreo() + "\n" + "Modalidad: "
						+ iei.getModalidad() + "\n";

				// + "Tipo aspirante: " + + "\n"

				if (this.tipoParticipacion1.equals("0")
						|| this.tipoParticipacion1.equals("1")) {
					if (panelRender[10]) {
						mensajeConfirmacion = mensajeConfirmacion
								+ " \n RECUERDE: \n\n Si seleccionó la modalidad CRÉDITOS  debe tener en cuenta que atendiendo a la resolución 245 de 2013 de rectoría,"
								+ "la cual deja sin vigencia el periodo intersemestral para el año 2013, la nota final del curso,"
								+ "se ingresará en su historia académica en el segundo periodo académico de 2013 (2013-03), sujeto a"
								+ "que usted se matricule en dicho periodo a cualquier programa curricular de la sede Bogotá."
								+ "Una vez iniciado el curso y si por alguna razón usted no puede asistir, recuerde que debe cancelarlo"
								+ "en las fechas establecidas; de lo contrario su nota final a reportar será de cero (0)."
								+ "\n \n"
								+ "Si seleccionó la modalidad CERTIFICADO, obtendrá un certificado por asistencia al curso de la Escuela Internacional 2013."
								+ "Es necesario haber adjuntado el formato de carta de compromiso para tomar el curso.";
					}

				}

				Correo correo = new Correo();
				correo.setOrigen(Correo.CORREO_HERMES);
				correo.adicionarDireccion(iei.getCorreo());

				// String consulta =
				// "select dd from DominioDetalle dd where dd.identificador.tipo = '"
				// + personaActual.getId().getDocumento() + "'";
				String consulta = "select dd from DominioDetalle dd where dd.identificador.tipo = '"
						+ iei.getDocumento() + "'";
				List lista = servicioGeneral.obtenerObjetos(consulta);

				if (lista.size() > 0) {
					DominioDetalle dominio = (DominioDetalle) lista.get(0);
					String correoUsuario = dominio.getEstado();
					correo.adicionarCopiaOculta(correoUsuario);
				}

				correo.adicionarCopiaOculta("sisii_nal@unal.edu.co");
				correo.adicionarCopiaOculta("escuelaint@unal.edu.co");

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
		} else {
			errores[27] = ("Por favor verifique la información que está ingresando y por favor adjunte el archivo obligatorio");
			panelRenderError[27] = true;
			FacesContext
					.getCurrentInstance()
					.addMessage(
							"errores27",
							new FacesMessage(
									FacesMessage.SEVERITY_INFO,
									"",
									"Por favor verifique la información que está ingresando y por favor adjunte el archivo obligatorio"));
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

	public DefaultUploadedFile getArchivoObligatorio() {
		return archivoObligatorio;
	}

	public void setArchivoObligatorio(DefaultUploadedFile archivoObligatorio) {
		this.archivoObligatorio = archivoObligatorio;
	}

	public List getListaArchivosObligatoriosSel() {
		return listaArchivosObligatoriosSel;
	}

	public void setListaArchivosObligatoriosSel(
			List listaArchivosObligatoriosSel) {
		this.listaArchivosObligatoriosSel = listaArchivosObligatoriosSel;
	}

	public List getListaArchivosObligatorios() {
		return listaArchivosObligatorios;
	}

	public void setListaArchivosObligatorios(List listaArchivosObligatorios) {
		this.listaArchivosObligatorios = listaArchivosObligatorios;
	}

	public DataTable getTablaArchivosObligatoriosSel() {
		return tablaArchivosObligatoriosSel;
	}

	public void setTablaArchivosObligatoriosSel(
			DataTable tablaArchivosObligatoriosSel) {
		this.tablaArchivosObligatoriosSel = tablaArchivosObligatoriosSel;
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

	public DefaultUploadedFile getArchivoObligatorioSeleccionado() {
		return archivoObligatorioSeleccionado;
	}

	public void setArchivoObligatorioSeleccionado(
			DefaultUploadedFile archivoObligatorioSeleccionado) {
		this.archivoObligatorioSeleccionado = archivoObligatorioSeleccionado;
	}

	public InscripcionEscuelaInternacional getArchivoObligatorioSeleccionado2() {
		return archivoObligatorioSeleccionado2;
	}

	public void setArchivoObligatorioSeleccionado2(
			InscripcionEscuelaInternacional archivoObligatorioSeleccionado2) {
		this.archivoObligatorioSeleccionado2 = archivoObligatorioSeleccionado2;
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
	
		

}
