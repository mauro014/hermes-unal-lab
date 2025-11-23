package co.edu.unal.hermes.vista.convocatoriasExtension;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.ConvocatoriasExtension;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.EstadoConvocatoria;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Rol;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorCrearConvocatoriaExternaExtension extends ManejadorBase {

	private ConvocatoriasExtension convExt = new ConvocatoriasExtension();
	private UploadedFile archivoObligatorio;
	private List<ConvocatoriasExtension> listaArchivosObligatoriosSel;
	private String errores[];
	private boolean panelRender[];
	private boolean panelRenderError[];
	private String mensajeEnvioCorreos = "";
	private ConvocatoriasExtension convocatoriasExtensionSeleccionada;
	private Date fechaMinimaCierre;
	private boolean puedeSubirArchivos;

	public ManejadorCrearConvocatoriaExternaExtension() {
		super();
		inicializarValores();
	}

	public void inicializarValores() {
		convExt = new ConvocatoriasExtension();
		listaArchivosObligatoriosSel = new ArrayList<ConvocatoriasExtension>();
		errores = new String[60];
		panelRender = new boolean[20];
		panelRenderError = new boolean[60];
		panelRender[0] = true;
		panelRender[1] = false;
		puedeSubirArchivos = true;
	}

	public void guardar() {
		if (validarValores()) {
			Calendar actual = Calendar.getInstance();
			Date date = actual.getTime();
			convExt.setFechaRegistro(date);

			EstadoConvocatoria ec = new EstadoConvocatoria();
			ec.setId(EstadoConvocatoria.ACTIVA);
			ec.setNombre("Activa");

			convExt.setEstadoConvocatoria(ec);
			servicioGeneral.guardarObjeto(convExt);
			enviarCorreo(138); // plantilla 138: Información Convocatorias
			// Activas - DNE
			panelRender[0] = false;
			panelRender[1] = true;

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"La convocatoria se ha creado correctamente, con el numero "
									+ convExt.getId() + ".  "
									+ mensajeEnvioCorreos, null));
		}
	}

	@SuppressWarnings("rawtypes")
	public void enviarCorreo(final int numPlantilla) {
		CorreoPlantilla correoActual = new CorreoPlantilla();
		List personaRol = new ArrayList<Persona>();
		personaActual = (Persona) sesion.getAttribute("persona");
		Rol r = new Rol();
		r.setId("OE");
		r.setNombre("OFICINA DE EXTENSIÓN");
		personaRol = servicioPersona.obtenerPersonasxRol(r);

		List<String> listaCorreo = new ArrayList<String>();
		if (personaRol != null && personaRol.size() > 0) {

			listaCorreo.add(personaActual.getEmail());

			for (int i = 0; i < personaRol.size(); i++) {
				Persona per = (Persona) personaRol.get(i);
				String eMail = per.getEmail();
				if (!listaCorreo.contains(eMail)) {
					listaCorreo.add(eMail);
					System.out.println("correo1: " + eMail);
				}

				String direccion = per.getDireccion();
				if (direccion != null && direccion.contains("@")
						&& !listaCorreo.contains(direccion)) {
					System.out.println("correo2: " + direccion);
					listaCorreo.add(direccion);
				}
			}

			correoActual = cargarPlantilla(numPlantilla);
			String asunto = correoActual.getAsunto();
			asunto = asunto.replaceAll("<<NOMBRE_CONVOCATORIA>>",
					convExt.getNombre());
			String cuerpoCorreo = correoActual.getCuerpo();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String fechaCierre = sdf.format(convExt.getFechaCierre());
			cuerpoCorreo = cuerpoCorreo.replaceAll("<<NOMBRE_CONVOCATORIA>>",
					convExt.getNombre());
			cuerpoCorreo = cuerpoCorreo.replaceAll("<<ENTIDAD_CONVOCANTE>>",
					convExt.getEntidad());
			cuerpoCorreo = cuerpoCorreo.replaceAll("<<FECHA_CIERRE>>",
					fechaCierre + " (día/mes/año)");
			cuerpoCorreo = cuerpoCorreo.replaceAll("<<OBJETO>>",
					convExt.getObjeto());

			/*
			 * listaCorreo = new ArrayList<String>(); for (int j = 0; j < 150;
			 * j++) { listaCorreo.add("dgbenitezc@unal.edu.co"); }
			 */

			int enviados = 0;
			String falla = "";
			for (String destinatario : listaCorreo) {
				System.out.println("destinatario: " + destinatario);
				Correo correo = new Correo();
				correo.setOrigen(Correo.CORREO_HERMES);
				//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
				correo.setAsunto(asunto);
				correo.setCuerpo(cuerpoCorreo);
				correo.adicionarDireccion(destinatario);
				if (servicioCorreo.enviarCorreo(correo)) {
					enviados++;
				} else {
					falla += destinatario + ",";
				}
			}

			int total = listaCorreo.size();
			mensajeEnvioCorreos = "Correos enviados: " + enviados + " de "
					+ total + ".";
			if (total > enviados) {
				mensajeEnvioCorreos += " Errores en: " + falla;
			}
			System.out.println(mensajeEnvioCorreos);
		}
	}

	@SuppressWarnings({ "rawtypes", "deprecation" })
	public CorreoPlantilla cargarPlantilla(int cod_id) {

		CorreoPlantilla correoActualAux = new CorreoPlantilla();

		List lista = servicioGeneral
				.obtenerObjetos("select c from CorreoPlantilla c where c.id='"
						+ cod_id + "'");
		if (lista != null && lista.size() > 0) {
			correoActualAux = (CorreoPlantilla) lista.get(0);
		}

		return correoActualAux;
	}

	public String regresarInicio() {
		sesion.removeAttribute("ManejadorCrearConvocatoriaExternaExtension");
		sesion.removeAttribute("ManejadorAdministrarConvocatoriasExtension");
		sesion.removeAttribute("ManejadorConsultarConvocatoriasExtension");
		// return "misProyectos";
		return "AdministrarConvocatoriaExtension";
	}

	public boolean validarValores() {
		boolean val = true;
		for (int i = 0; i < panelRenderError.length; i++) {
			panelRenderError[i] = false;
		}

		convExt.setNombre(convExt.getNombre().trim());
		if (convExt.getNombre().length() == 0
				|| convExt.getNombre().length() > 2000) {
			val = false;
			panelRenderError[3] = true;
			errores[3] = "Longitud permitida 1 a 2000 caracteres.";
		}

		convExt.setEntidad(convExt.getEntidad().trim());
		if (convExt.getEntidad().length() == 0
				|| convExt.getEntidad().length() > 2000) {
			val = false;
			panelRenderError[4] = true;
			errores[4] = "Longitud permitida 1 a 2000 caracteres.";
		}

		convExt.setObjeto(convExt.getObjeto().trim());
		if (convExt.getObjeto().length() == 0
				|| convExt.getObjeto().length() > 2000) {
			val = false;
			panelRenderError[5] = true;
			errores[5] = "Longitud permitida 1 a 2000 caracteres.";
		}

		if (convExt.getFechaCierre() == null
				|| convExt.getFechaCierre().before(new Date())) {
			val = false;
			panelRenderError[2] = true;
			errores[2] = "La fecha de cierre debe ser posterior a la fecha actual.";
		}

		if (convExt.getNombreTerminosReferencia() != null
				&& convExt.getNombreTerminosReferencia().length() > 100) {
			val = false;
			panelRenderError[11] = true;
			errores[11] = "La longitud máxima del nombre de archivo es de 100 caracteres.";
		}

		return val;
	}

	public void guardarArchivoObligatorio(FileUploadEvent event) {
		try {
			archivoObligatorio = event.getFile();
			if (archivoObligatorio.getContents() != null) {

				puedeSubirArchivos = false;

				// Validar archivos
				if (listaArchivosObligatoriosSel.size() == 1) {
					errores[26] = "Por favor adjunte los términos de referencia de la convocatoria, si es más de un archivo, adjuntelos en un archivo comprimido (.zip o .rar)";
					panelRenderError[26] = true;
					puedeSubirArchivos = false;
				} else {
					errores[26] = "";
					panelRenderError[26] = false;
					puedeSubirArchivos = true;
				}

				if (puedeSubirArchivos) {
					int i = archivoObligatorio.getFileName().lastIndexOf("\\");
					this.convExt.setBytes(archivoObligatorio.getContents());
					this.convExt.setNombreTerminosReferencia(archivoObligatorio
							.getFileName().substring(i + 1));
					listaArchivosObligatoriosSel.add(convExt);
					puedeSubirArchivos = false;
				}

			}

		} catch (Exception x) {
			System.out.println(x.toString());
		}
	}

	public void descargarArchivo() {

		FacesContext ctx = FacesContext.getCurrentInstance();

		try {
			if (!ctx.getResponseComplete()) {
				HttpServletResponse response = (HttpServletResponse) ctx
						.getExternalContext().getResponse();
				response.setContentType("text/plain");
				response.setHeader(
						"Content-Disposition",
						"attachment;filename=\""
								+ convExt.getNombreTerminosReferencia() + "\"");
				ServletOutputStream out = response.getOutputStream();
				out.write(convExt.getBytes());
				out.flush();
				ctx.responseComplete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void eliminarArchivoObligatorio() {
		listaArchivosObligatoriosSel.remove(convocatoriasExtensionSeleccionada);
		puedeSubirArchivos = true;
	}

	public ConvocatoriasExtension getConvExt() {
		return convExt;
	}

	public void setConvExt(ConvocatoriasExtension convExt) {
		this.convExt = convExt;
	}

	public UploadedFile getArchivoObligatorio() {
		return archivoObligatorio;
	}

	public void setArchivoObligatorio(UploadedFile archivoObligatorio) {
		this.archivoObligatorio = archivoObligatorio;
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

	public List<ConvocatoriasExtension> getListaArchivosObligatoriosSel() {
		return listaArchivosObligatoriosSel;
	}

	public void setListaArchivosObligatoriosSel(
			List<ConvocatoriasExtension> listaArchivosObligatoriosSel) {
		this.listaArchivosObligatoriosSel = listaArchivosObligatoriosSel;
	}

	/**
	 * @return the mensajeEnvioCorreos
	 */
	public String getMensajeEnvioCorreos() {
		return mensajeEnvioCorreos;
	}

	/**
	 * @return the fechaMinimaCierre
	 */
	public Date getFechaMinimaCierre() {
		Calendar fechaActual = Calendar.getInstance();
		fechaActual.add(Calendar.DATE, 1);
		fechaMinimaCierre = fechaActual.getTime();
		return fechaMinimaCierre;
	}

	/**
	 * @return the convocatoriasExtensionSeleccionada
	 */
	public ConvocatoriasExtension getConvocatoriasExtensionSeleccionada() {
		return convocatoriasExtensionSeleccionada;
	}

	/**
	 * @param convocatoriasExtensionSeleccionada
	 *            the convocatoriasExtensionSeleccionada to set
	 */
	public void setConvocatoriasExtensionSeleccionada(
			ConvocatoriasExtension convocatoriasExtensionSeleccionada) {
		this.convocatoriasExtensionSeleccionada = convocatoriasExtensionSeleccionada;
	}

	/**
	 * @return the puedeSubirArchivos
	 */
	public boolean isPuedeSubirArchivos() {
		return puedeSubirArchivos;
	}

}
