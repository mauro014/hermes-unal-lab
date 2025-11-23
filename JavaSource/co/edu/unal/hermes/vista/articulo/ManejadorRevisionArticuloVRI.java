package co.edu.unal.hermes.vista.articulo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import co.edu.unal.hermes.modelo.ArchivoConvocatoria;
import co.edu.unal.hermes.modelo.ArchivoResumen;
import co.edu.unal.hermes.modelo.ConvocatoriaArticulo;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Parametro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.utils.Navegacion;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorRevisionArticuloVRI extends ManejadorBase {

	private SelectItem[] articuloItem;
	private Long codigoArticulo = Long.parseLong("-1");
	private String errores[];
	private boolean panelRender[];
	private boolean panelRenderError[];
	private ConvocatoriaArticulo ca;

	private String nombreDocente = "";
	private String coautores = "";
	private String modalidad = "";
	private String idioma = "";
	private String estado = "";
	private Dependencia dependencia;
	private String sede = "";

	private List listaArchivosConvocatoriaArticulos;
	private HtmlDataTable tablaArchivosConvocatoriaArticulos;
	private List<ArchivoResumen> listaArchivoResumen;

	private boolean mostrarInformacionGeneral = false;
	private boolean aprobadoSede = false;
	private boolean noAprobadoSede = false;

	private String evaluacionVRI = "";
	private String evaluacionVRIN = "";
	private String comentariosVRI = "";
	private String comentariosVRIN = "";
	private Date fechaEstimada;
	private Date fechaEnvio;
	private String evalSede = "";

	private List listaEval;

	private boolean mostrarSiAPV = false;
	private boolean mostrarSiNAPV = false;
	private boolean mostrarComentario = false;
	private boolean mostrarComentarioNA = false;
	private boolean mostrarEvaluarN = false;

	public ManejadorRevisionArticuloVRI() {
		List listaArticulos = servicioGeneral
				.obtenerListaObjetos("ConvocatoriaArticulo e where (e.estado = 'AP' or e.estado = 'NAP') ORDER BY e.id");

		if (listaArticulos != null && listaArticulos.size() > 0) {
			articuloItem = new SelectItem[listaArticulos.size()];
			for (int i = 0; i < listaArticulos.size(); i++) {
				ConvocatoriaArticulo articulo = (ConvocatoriaArticulo) listaArticulos
						.get(i);
				articuloItem[i] = new SelectItem(articulo.getId(), "Id="
						+ articulo.getId() + " - Fecha Registro("
						+ articulo.getFechaRegistro() + ")");
			}
			codigoArticulo = ((ConvocatoriaArticulo) listaArticulos.get(0))
					.getId();
		} else {
			articuloItem = new SelectItem[0];
		}
		ca = new ConvocatoriaArticulo();//
		reiniciarVariables();

		listaEval = new Vector();

		listaEval.add(new SelectItem("0", "Si"));
		listaEval.add(new SelectItem("1", "No"));

	}

	public void buscarSolicitudConvocatoriaArticulos() {
		mostrarInformacionGeneral = false;
		aprobadoSede = false;
		noAprobadoSede = false;
		if (codigoArticulo != -1) {
			List articuloSeleccionado = servicioGeneral.obtenerObjetoXID(
					"ConvocatoriaArticulo", codigoArticulo.toString());
			ca = (ConvocatoriaArticulo) articuloSeleccionado.get(0);
			mostrarInformacionGeneral = true;
			nombreDocente = ca.getPersonaInv().getNombre1() + " "
					+ ca.getPersonaInv().getNombre2() + " "
					+ ca.getPersonaInv().getApellido1() + " "
					+ ca.getPersonaInv().getApellido2();
			modalidad = (ca.getModalidad());
			
			idioma = ca.getIdioma();
			
			estado = (ca.getEstado().equals(EstadoProyecto.APROBADO) ? "Aprobado en la Sede"
					: "No aprobado en la Sede");
			Persona profSol = ca.getPersonaInv();
			dependencia = servicioDependencia.obtenerDependencia(ca.getPersonaInv()
					.getId());
			sede = dependencia.getSede().getNombre();
			listaArchivosConvocatoriaArticulos = servicioGeneral
					.obtenerListaObjetos("ArchivoConvocatoria e where e.convocatoria = '"
							+ codigoArticulo + "'");
			if (ca.getEstado().equals(EstadoProyecto.APROBADO)) {
				aprobadoSede = true;
			} else {
				noAprobadoSede = true;
			}

		}
	}

	public void descargarArchivoArticulo() {

		FacesContext ctx = FacesContext.getCurrentInstance();

		Long id = ((ArchivoConvocatoria) (tablaArchivosConvocatoriaArticulos
				.getRowData())).getId();

		List archivos = servicioGeneral.obtenerObjetoXID("ArchivoConvocatoria",
				id.toString());

		ArchivoConvocatoria archivo = (ArchivoConvocatoria) archivos.get(0);

		try {
			if (!ctx.getResponseComplete()) {
				HttpServletResponse response = (HttpServletResponse) ctx
						.getExternalContext().getResponse();
				response.setContentType("text/plain");
				response.setHeader("Content-Disposition",
						"attachment;filename=\"" + archivo.getNombre() + "\"");
				ServletOutputStream out = response.getOutputStream();
				out.write(archivo.getBytes());
				out.flush();
				ctx.responseComplete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cambiarFormAPS(ValueChangeEvent evaVri) {

		String eval = (String) evaVri.getNewValue();
		errores = new String[10];
		panelRenderError = new boolean[10];
		if (eval!=null && eval.equals(EstadoProyecto.APROBADO_VICERRECTORIA)) {
			mostrarSiAPV = true;
			mostrarSiNAPV = false;
			mostrarComentario = true;
			mostrarEvaluarN = false;
		}
		if (eval!=null && eval.equals(EstadoProyecto.NO_APROBADO_VICERRECTORIA)) {
			mostrarSiNAPV = true;
			mostrarSiAPV = false;
			mostrarComentario = true;
			mostrarEvaluarN = true;
		}
	}

	public void cambiarFormNAPS(ValueChangeEvent evaVri) {

		String eval = (String) evaVri.getNewValue();
		if (eval != null && eval.equals(EstadoProyecto.APROBADO_VICERRECTORIA)) {
			mostrarSiAPV = false;
			mostrarSiNAPV = false;
			mostrarComentario = false;
			mostrarComentarioNA = true;
			mostrarEvaluarN = false;
		}
		if (eval != null && eval.equals(EstadoProyecto.NO_APROBADO_VICERRECTORIA)) {
			mostrarSiAPV = false;
			mostrarSiNAPV = false;
			mostrarComentario = false;
			mostrarComentarioNA = true;
			mostrarEvaluarN = false;
		}
	}
	
	public String imprimirReporteConvocatoriaArticulo() {

		if (codigoArticulo != -1 && codigoArticulo != null) {
			return imprimirReporteConvArticulos();
		} 
		return "";
	}

	public String imprimirReporteConvArticulos() {
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("id", codigoArticulo.toString());
		r.setNombreReporte("/convocatoriaArticulos/ConvocatoriaArticulos");
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		return Navegacion.REPORTE;
	}
	// mostrarComentarioNA

	public void guardar() {
		errores = new String[10];
		panelRenderError = new boolean[10];
		if(validar()){
			if (ca.getEstado().equals(EstadoProyecto.APROBADO)) {
				if (evaluacionVRI.equals(EstadoProyecto.APROBADO_VICERRECTORIA)) {
					ca.setEstado(EstadoProyecto.APROBADO_VICERRECTORIA);
					ca.setFechaEstimadaVri(fechaEstimada);
					ca.setComentarioVri(comentariosVRI);
					ca.setFechaEnvio(fechaEnvio);
					servicioGeneral.guardarObjeto(ca);
					enviarCorreoVIR(106, 1);
					limpiar();
				} else {
					if (evalSede.equals("0")) {
						ca.setEstado(EstadoProyecto.PROPUESTO);
						ca.setComentarioVri(comentariosVRI);
						servicioGeneral.guardarObjeto(ca);
						enviarCorreoVIR(108, 3);
						limpiar();
					} else {
						ca.setEstado(EstadoProyecto.NO_APROBADO_VICERRECTORIA);
						ca.setComentarioVri(comentariosVRI);
						servicioGeneral.guardarObjeto(ca);
						enviarCorreoVIR(107, 2);
						limpiar();
					}
				}
			} else {
				if (ca.getEstado().equals(EstadoProyecto.NO_APROBADO)) {
					System.out.println("/**/*/*/*/ No aprobo evaluacionVRIN: " + evaluacionVRIN);
					if (evaluacionVRIN.equals(EstadoProyecto.APROBADO_VICERRECTORIA)) {
						ca.setEstado("P");
						ca.setComentarioVri(comentariosVRIN);
						servicioGeneral.guardarObjeto(ca);
						enviarCorreoVIR(108, 3);
						limpiar();
					} else {
						ca.setEstado(EstadoProyecto.NO_APROBADO_VICERRECTORIA);
						ca.setComentarioVri(comentariosVRIN);
						servicioGeneral.guardarObjeto(ca);
						enviarCorreoVIR(107, 2);
						limpiar();
					}
				}
			}
			
		}
		
	}
	
	public boolean validar(){
		boolean bandera = true;
		
		if (ca.getEstado().equals(EstadoProyecto.APROBADO)){
			
			if(this.evaluacionVRI.equals("")){
				bandera = false;
				this.errores[4] = "Por favor seleccione una opción.";
				this.panelRenderError[4] = true;
			}
			
			if(this.evaluacionVRI.equals(EstadoProyecto.APROBADO_VICERRECTORIA)){
				//fecha envio
				if (this.fechaEnvio == null) {
					bandera = false;
					this.errores[1] = "La fecha es NO valida";
					this.panelRenderError[1] = true;
				} else {
					if (this.fechaEstimada != null) {
						if (this.fechaEnvio.after(this.fechaEstimada)){
							bandera = false;
							this.errores[1] = "La fecha debe ser anterior a la fecha estimada de entrega.";
							this.panelRenderError[1] = true;
							
						}
					}
				}
				
				//fecha estimada entrega			
				if (this.fechaEstimada == null) {
					bandera = false;
					this.errores[2] = "La fecha es NO valida";
					this.panelRenderError[2] = true;
				} else {
					if (this.fechaEstimada.before(new Date())) {
						bandera = false;
						this.errores[2] = "La fecha debe ser posterior a la fecha de hoy";
						this.panelRenderError[2] = true;
					} else if (this.fechaEnvio != null) {
						if(this.fechaEnvio.before(this.fechaEnvio)){
							bandera = false;
							this.errores[2] = "La fecha debe ser posterior a la fecha de envío.";
							this.panelRenderError[2] = true;
						}
						
					}
				}
			}else{
				//validar comentario		
				if(this.comentariosVRI.equals("") ){
					bandera = false;
					this.errores[3] = "Se debe llenar el comentario de la VRI";
					this.panelRenderError[3] = true;
				}	
			}
			
			
			
		}else{
			if(ca.getEstado().equals(EstadoProyecto.NO_APROBADO)){
				
				if(this.evaluacionVRIN.equals("")){
					bandera = false;
					this.errores[4] = "Por favor seleccione una opción.";
					this.panelRenderError[4] = true;
				}
				//validar comentario		
				if(this.comentariosVRIN.equals("") ){
					bandera = false;
					this.errores[3] = "Se debe llenar el comentario de la VRI";
					this.panelRenderError[3] = true;
				}				
			}
		}
		
		return bandera;
	}

	public CorreoPlantilla cargarPlantilla(int cod_id) {

		CorreoPlantilla correoActualAux = new CorreoPlantilla();
		CorreoPlantilla a = new CorreoPlantilla();

		List lista = servicioGeneral
				.obtenerObjetos("select c from CorreoPlantilla c where c.id='"
						+ cod_id + "'");
		if (lista != null && lista.size() > 0) {
			correoActualAux = (CorreoPlantilla) lista.get(0);
		}

		return correoActualAux;
	}

	public void enviarCorreoVIR(final int numPlantilla, final int opcion) {
		IdPersona idPer = new IdPersona();
		idPer.setDocumento(ca.getPersonaInv().getId().getDocumento());
		idPer.setTipoDocumento(ca.getPersonaInv().getId().getTipoDocumento());
		//Persona per = servicioPersona.obtenerPersona(idPer);
		Dependencia depPer = new Dependencia();
		depPer = servicioDependencia.obtenerDependencia(idPer);
		Long idSedePer = depPer.getSede().getId();
		List evaluadoresArticulos = servicioGeneral.obtenerObjetos("select e from Parametro e where e.nombre = 'CON_ART_COORD' and e.descripcion = '"+ idSedePer.toString() +"'");
		Parametro parEval = (Parametro)evaluadoresArticulos.get(0);
		IdPersona idEvaluador = new IdPersona(parEval.getValor(), parEval.getProfesion());
		Persona evaluador = servicioPersona.obtenerPersona(idEvaluador);
		String nombreInv = ca.getPersonaInv().getNombre1() + " " + ca.getPersonaInv().getNombre2() + " " + ca.getPersonaInv().getApellido1() + " " + ca.getPersonaInv().getApellido2();
		String nombreSede = depPer.getSede().getNombre();
		String diSede = obtenerNombreDireccionInv(depPer.getSede().getId());
		personaActual = (Persona) sesion.getAttribute("persona");
		switch (opcion) {
		case 1:
		{
			//La VRI da visto bueno a una solicitud aprobada por la sede
			CorreoPlantilla correoActual = new CorreoPlantilla();
			if (ca.getPersonaInv().getEmail() != null
					&& !ca.getPersonaInv().getEmail().equals("")) {
				
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
				String sFechaEnvio = formato.format(ca.getFechaEnvio());
				String sFechaEstimanda = formato.format(ca.getFechaEstimadaVri());
				
				correoActual = cargarPlantilla(numPlantilla);
				Correo correo = new Correo();
				correo.setOrigen(Correo.CORREO_HERMES);
				String dirCorreo = Correo.CORREO_HERMES;
				correo.adicionarDireccion(ca.getPersonaInv().getEmail());
				correo.adicionarCopiaOculta(dirCorreo);
				correo.adicionarCopiaOculta(personaActual.getEmail());
				correo.setAsunto(correoActual.getAsunto());				
				String cuerpoCorreo = correoActual.getCuerpo();
				cuerpoCorreo = cuerpoCorreo.replaceAll("<<INVESTIGADOR>>", nombreInv);
				cuerpoCorreo = cuerpoCorreo.replaceAll("<<SEDE>>", diSede);
				cuerpoCorreo = cuerpoCorreo.replaceAll("<<SOLICITUD>>", ca.getId().toString());
				cuerpoCorreo = cuerpoCorreo.replaceAll("<<FECHA_ENVIO>>", sFechaEnvio);
				cuerpoCorreo = cuerpoCorreo.replaceAll("<<FECHA_ENTREGA>>", sFechaEstimanda);
				correo.setCuerpo(cuerpoCorreo);
				System.out.println();
				System.out.println();
				System.out.println(cuerpoCorreo);
				servicioCorreo.enviarCorreo(correo);
			} else {
				System.out.println("===[No existe el correo electrónico destino:ManejadorRevisionArticuloVRI]===");
			}
			
			break;
		}		
		case 2:
		{ 	
			//La VRI no da visto bueno a una solicitud aprobada por la sede, la niega definitivamente
			CorreoPlantilla correoActual = new CorreoPlantilla();
			if (ca.getPersonaInv().getEmail() != null
					&& !ca.getPersonaInv().getEmail().equals("")) {
				correoActual = cargarPlantilla(numPlantilla);
				Correo correo = new Correo();
				correo.setOrigen(Correo.CORREO_HERMES);
				String dirCorreo = Correo.CORREO_HERMES;
				correo.adicionarDireccion(ca.getPersonaInv().getEmail());
				correo.adicionarCopiaOculta(dirCorreo);
				correo.adicionarCopiaOculta(personaActual.getEmail());
				correo.setAsunto(correoActual.getAsunto());
				String cuerpoCorreo = correoActual.getCuerpo();
				cuerpoCorreo = cuerpoCorreo.replaceAll("<<INVESTIGADOR>>", nombreInv);
				cuerpoCorreo = cuerpoCorreo.replaceAll("<<SEDE>>", diSede);
				cuerpoCorreo = cuerpoCorreo.replaceAll("<<SOLICITUD>>", ca.getId().toString());
				cuerpoCorreo = cuerpoCorreo.replaceAll("<<COMENTARIOS_VRI>>", ca.getComentarioVri());
				correo.setCuerpo(cuerpoCorreo);
				servicioCorreo.enviarCorreo(correo);
			} else {
				System.out.println("===[No existe el correo electrónico destino:ManejadorRevisionArticuloVRI]===");
			}
			break;
		}
		case 3:
		{
			//La VRI regresa a la sede una solicitud no aprobada en la sede
			CorreoPlantilla correoActual = new CorreoPlantilla();
			if (evaluador.getEmail() != null
					&& !evaluador.getEmail().equals("")) {
				correoActual = cargarPlantilla(numPlantilla);
				Correo correo = new Correo();
				correo.setOrigen(Correo.CORREO_HERMES);
				String dirCorreo = Correo.CORREO_HERMES;
				correo.adicionarDireccion(evaluador.getEmail());
				correo.adicionarDireccion(ca.getPersonaInv().getEmail());
				correo.adicionarCopiaOculta(dirCorreo);
				correo.adicionarCopiaOculta(personaActual.getEmail());
				correo.setAsunto(correoActual.getAsunto());
				String cuerpoCorreo = correoActual.getCuerpo();
				cuerpoCorreo = cuerpoCorreo.replaceAll("<<INVESTIGADOR>>", nombreInv);
				cuerpoCorreo = cuerpoCorreo.replaceAll("<<SEDE>>", diSede);
				cuerpoCorreo = cuerpoCorreo.replaceAll("<<SOLICITUD>>", ca.getId().toString());
				cuerpoCorreo = cuerpoCorreo.replaceAll("<<COMENTARIOS_VRI>>", ca.getComentarioVri());
				correo.setCuerpo(cuerpoCorreo);
				servicioCorreo.enviarCorreo(correo);
			} else {
				System.out.println("===[No existe el correo electrónico destino:ManejadorRevisionArticuloVRI]===");
			}
			break;
		}			
		}
	}
	
	public String obtenerNombreDireccionInv(final Long idSede){
		String nombreDI = "";
		String sIdSede = idSede.toString();
		switch(Integer.parseInt(sIdSede)){
		case 2:
		{
			nombreDI = "Dirección de Investigación de la sede Bogotá";
			break;
		}
		case 3:
		{
			nombreDI = "Dirección de Investigación de la sede Medellín";
			break;
		}
		case 4:
		{
			nombreDI = "Dirección de Investigación de la sede Manizales";
			break;
		}
		case 5:
		{
			nombreDI = "Dirección de Investigación de la sede Palmira";
			break;
		}
		case 6:
		{
			nombreDI = "Coordinación de Investigación de la Sede Amazonia de Presencia Nacional";
			break;
		}
		case 7:
		{
			nombreDI = "Coordinación de Investigación de la Sede Orinoquia de Presencia Nacional";
			break;
		}
		case 8:
		{
			nombreDI = "Coordinación de Investigación de la Sede Caribe de Presencia Nacional";
			break;
		}
		case 9:
		{
			nombreDI = "Coordinación de Investigación de la Sede Tumaco de Presencia Nacional";
			break;
		}
		
		}
		//Dirección de Investigación de sede <<SEDE>> (o coordinación de Investigación de la Sede <<SEDE>> de Presencia Nacional
		/*if(idSede == 2){
			nombreDI = "Dirección de Investigación de Bogotá";
		}else{
			if(idSede == 3){
				nombreDI = "Dirección de Investigación de Medellín";
			}else{
				if(idSede == 4){
					nombreDI = "Dirección de Investigación de Manizales";
				}else{
					if(idSede == 5){
						
					}
				}
			}
		}*/
		
		return nombreDI;
	}


	public void limpiar() {

		sesion.removeAttribute("ManejadorRevisionArticuloVRI");

	}

	private void reiniciarVariables() {
		// ca = new ConvocatoriaArticulo();
		codigoArticulo = Long.parseLong("-1");
		errores = new String[60];
		panelRender = new boolean[10];
		panelRenderError = new boolean[60];
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

	public SelectItem[] getArticuloItem() {
		return articuloItem;
	}

	public void setArticuloItem(SelectItem[] articuloItem) {
		this.articuloItem = articuloItem;
	}

	public Long getCodigoArticulo() {
		return codigoArticulo;
	}

	public void setCodigoArticulo(Long codigoArticulo) {
		this.codigoArticulo = codigoArticulo;
	}

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

	public ConvocatoriaArticulo getCa() {
		return ca;
	}

	public void setCa(ConvocatoriaArticulo ca) {
		this.ca = ca;
	}

	public String getNombreDocente() {
		return nombreDocente;
	}

	public void setNombreDocente(String nombreDocente) {
		this.nombreDocente = nombreDocente;
	}

	public String getCoautores() {
		return coautores;
	}

	public void setCoautores(String coautores) {
		this.coautores = coautores;
	}

	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Dependencia getDependencia() {
		return dependencia;
	}

	public void setDependencia(Dependencia dependencia) {
		this.dependencia = dependencia;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	public List getListaArchivosConvocatoriaArticulos() {
		return listaArchivosConvocatoriaArticulos;
	}

	public void setListaArchivosConvocatoriaArticulos(
			List listaArchivosConvocatoriaArticulos) {
		this.listaArchivosConvocatoriaArticulos = listaArchivosConvocatoriaArticulos;
	}

	public HtmlDataTable getTablaArchivosConvocatoriaArticulos() {
		return tablaArchivosConvocatoriaArticulos;
	}

	public void setTablaArchivosConvocatoriaArticulos(
			HtmlDataTable tablaArchivosConvocatoriaArticulos) {
		this.tablaArchivosConvocatoriaArticulos = tablaArchivosConvocatoriaArticulos;
	}

	public List<ArchivoResumen> getListaArchivoResumen() {
		return listaArchivoResumen;
	}

	public void setListaArchivoResumen(List<ArchivoResumen> listaArchivoResumen) {
		this.listaArchivoResumen = listaArchivoResumen;
	}

	public boolean isMostrarInformacionGeneral() {
		return mostrarInformacionGeneral;
	}

	public void setMostrarInformacionGeneral(boolean mostrarInformacionGeneral) {
		this.mostrarInformacionGeneral = mostrarInformacionGeneral;
	}

	public boolean isAprobadoSede() {
		return aprobadoSede;
	}

	public void setAprobadoSede(boolean aprobadoSede) {
		this.aprobadoSede = aprobadoSede;
	}

	public boolean isNoAprobadoSede() {
		return noAprobadoSede;
	}

	public void setNoAprobadoSede(boolean noAprobadoSede) {
		this.noAprobadoSede = noAprobadoSede;
	}

	public String getEvaluacionVRI() {
		return evaluacionVRI;
	}

	public void setEvaluacionVRI(String evaluacionVRI) {
		this.evaluacionVRI = evaluacionVRI;
	}

	public String getComentariosVRI() {
		return comentariosVRI;
	}

	public void setComentariosVRI(String comentariosVRI) {
		this.comentariosVRI = comentariosVRI;
	}

	public Date getFechaEstimada() {
		return fechaEstimada;
	}

	public void setFechaEstimada(Date fechaEstimada) {
		this.fechaEstimada = fechaEstimada;
	}

	public boolean isMostrarSiAPV() {
		return mostrarSiAPV;
	}

	public void setMostrarSiAPV(boolean mostrarSiAPV) {
		this.mostrarSiAPV = mostrarSiAPV;
	}

	public boolean isMostrarSiNAPV() {
		return mostrarSiNAPV;
	}

	public void setMostrarSiNAPV(boolean mostrarSiNAPV) {
		this.mostrarSiNAPV = mostrarSiNAPV;
	}

	public boolean isMostrarComentario() {
		return mostrarComentario;
	}

	public void setMostrarComentario(boolean mostrarComentario) {
		this.mostrarComentario = mostrarComentario;
	}

	public String getComentariosVRIN() {
		return comentariosVRIN;
	}

	public void setComentariosVRIN(String comentariosVRIN) {
		this.comentariosVRIN = comentariosVRIN;
	}

	public String getEvaluacionVRIN() {
		return evaluacionVRIN;
	}

	public void setEvaluacionVRIN(String evaluacionVRIN) {
		this.evaluacionVRIN = evaluacionVRIN;
	}

	public boolean isMostrarComentarioNA() {
		return mostrarComentarioNA;
	}

	public void setMostrarComentarioNA(boolean mostrarComentarioNA) {
		this.mostrarComentarioNA = mostrarComentarioNA;
	}

	public String getEvalSede() {
		return evalSede;
	}

	public void setEvalSede(String evalSede) {
		this.evalSede = evalSede;
	}

	public List getListaEval() {
		return listaEval;
	}

	public void setListaEval(List listaEval) {
		this.listaEval = listaEval;
	}

	public boolean isMostrarEvaluarN() {
		return mostrarEvaluarN;
	}

	public void setMostrarEvaluarN(boolean mostrarEvaluarN) {
		this.mostrarEvaluarN = mostrarEvaluarN;
	}

	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

}
