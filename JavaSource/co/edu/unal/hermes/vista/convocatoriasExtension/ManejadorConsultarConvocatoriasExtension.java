package co.edu.unal.hermes.vista.convocatoriasExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.component.UIData;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import co.edu.unal.hermes.modelo.ConvocatoriasExtension;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.ParticipantesConvocatoriasExtension;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorConsultarConvocatoriasExtension extends ManejadorBase {

	private List<ConvocatoriasExtension> listaConvocatoriasActivas;
	private UIData tablaConvocatoriasActivas;
	private Persona personaActual;
	private Long codigoConvocatoria = -1L;
	private ConvocatoriasExtension convExt;
	private List<ConvocatoriasExtension> listaArchivosObligatoriosSel;
	private HtmlDataTable tablaArchivosObligatoriosSel;
	private boolean mostrarParticipacion;
	private String comentarioParticipacion;
	private String comentario;
	private String errorComentario;
	private String errorParticipacion;
	private List<ParticipantesConvocatoriasExtension> listaComentarios;
	private HtmlDataTable tablaComentarios;
	private boolean mostrarArchivos;
	private boolean mostrarComentarios;
	private ConvocatoriasExtension convocatoriasExtensionSeleccionada;

	@SuppressWarnings({ "unchecked", "deprecation" })
	public ManejadorConsultarConvocatoriasExtension() {
		super();
		personaActual = (Persona) sesion.getAttribute("persona");
		convExt = new ConvocatoriasExtension();
		listaConvocatoriasActivas = new ArrayList<ConvocatoriasExtension>();
		listaConvocatoriasActivas = servicioGeneral
				.obtenerObjetos("select s from ConvocatoriasExtension s where s.estadoConvocatoria = 'A' order by s.id DESC");
		mostrarParticipacion = true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public String consultarConvocatorias() {
		// asignar codigo de la convocatoria
		codigoConvocatoria = convocatoriasExtensionSeleccionada.getId();
		List solSel = servicioGeneral.obtenerObjetoXID(
				"ConvocatoriasExtension", codigoConvocatoria.toString());
		convExt = (ConvocatoriasExtension) solSel.get(0);

		listaArchivosObligatoriosSel = servicioGeneral
				.obtenerObjetos("select e from ConvocatoriasExtension e where e.id = '"
						+ codigoConvocatoria + "'");

		List participanteConv = servicioGeneral
				.obtenerObjetos("select s from ParticipantesConvocatoriasExtension s where s.investigador.id.documento = '"
						+ personaActual.getId().getDocumento()
						+ "' and s.convocatoriaDNE = '"
						+ convExt.getId()
						+ "'"
						+ " and s.intencion = 'S'");

		if (participanteConv.size() > 0 && participanteConv != null) {
			mostrarParticipacion = false;
		} else {
			List consultaConv = servicioGeneral
					.obtenerObjetos("select s from ParticipantesConvocatoriasExtension s where s.investigador.id.documento = '"
							+ personaActual.getId().getDocumento()
							+ "' and s.convocatoriaDNE = '"
							+ convExt.getId()
							+ "'" + " and s.intencion = 'C'");
			if (consultaConv.size() > 0 && consultaConv != null) {

			} else {
				reportarConsultaConvocatoria();
			}

		}

		cargarComentarios();

		mostrarArchivos = false;
		if (listaArchivosObligatoriosSel != null
				&& listaArchivosObligatoriosSel.size() == 1
				&& listaArchivosObligatoriosSel.get(0).getTerminosReferencia() != null) {
			mostrarArchivos = true;
		}

		return "ParticiparConvocatoriaExtension";
	}

	void cargarComentarios() {
		listaComentarios = servicioGeneral
				.obtenerObjetos("select s from ParticipantesConvocatoriasExtension s where s.investigador.id.documento = '"
						+ personaActual.getId().getDocumento()
						+ "' and s.convocatoriaDNE = '"
						+ convExt.getId()
						+ "'"
						+ " and s.intencion is NULL order by s.id DESC");
		mostrarComentarios = false;
		if (listaComentarios != null && listaComentarios.size() > 0) {
			mostrarComentarios = true;
		}
	}

	public void descargarArchivo() {

		FacesContext ctx = FacesContext.getCurrentInstance();

		try {
			if (!ctx.getResponseComplete()) {
				HttpServletResponse response = (HttpServletResponse) ctx
						.getExternalContext

						().getResponse();
				response.setContentType("text/plain");
				response.setHeader("Content-Disposition",
						"attachment;filename=\"" +

						convExt.getNombreTerminosReferencia() + "\"");
				ServletOutputStream out = response.getOutputStream();
				out.write(convExt.getBytes());
				out.flush();
				ctx.responseComplete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void confirmarParticipacion() {
		errorParticipacion = "";
		ParticipantesConvocatoriasExtension pce = new ParticipantesConvocatoriasExtension();

		List participanteConv = servicioGeneral
				.obtenerObjetos("select s from ParticipantesConvocatoriasExtension s where s.investigador.id.documento = '"
						+ personaActual.getId().getDocumento()
						+ "' and s.convocatoriaDNE = '"
						+ convExt.getId()
						+ "'"
						+ " and s.intencion = 'C'");

		if (participanteConv.size() > 0 && participanteConv != null) {
			pce = (ParticipantesConvocatoriasExtension) participanteConv.get(0);
		}

		Investigador inv = servicioPersona.obtenerInvestigador(personaActual
				.getId());
		if (inv != null && inv instanceof InvestigadorInterno) {
			Dependencia dep = inv.getDependencia();
			pce.setDependencia(dep);
			pce.setInvestigador(inv);
			this.comentarioParticipacion = this.comentarioParticipacion.trim();
			pce.setComentarioParticipacion(this.comentarioParticipacion);
			pce.setConvocatoriaDNE(convExt);
			pce.setFechaRegistro(new Date());
			pce.setIntencion("S");
			servicioGeneral.guardarObjeto(pce);
			mostrarParticipacion = false;
		} else {
			errorParticipacion = "Error al participar, comuníquese con HERMES, extensión 20048.";
		}

	}

	public void reportarConsultaConvocatoria() {
		ParticipantesConvocatoriasExtension pce = new ParticipantesConvocatoriasExtension();
		Investigador inv = servicioPersona.obtenerInvestigador(personaActual
				.getId());
		if (inv != null && inv instanceof InvestigadorInterno) {
			Dependencia dep = inv.getDependencia();
			pce.setDependencia(dep);
			pce.setInvestigador(inv);
			pce.setConvocatoriaDNE(convExt);
			pce.setFechaRegistro(new Date());
			pce.setIntencion("C");
			servicioGeneral.guardarObjeto(pce);
		} else {

		}
	}

	public void agregarComentario() {
		this.comentario = this.comentario.trim();
		errorComentario = "";
		if (this.comentario.equals("")) {
			errorComentario = "El comentario no puede ser nulo.";
		}
		if (this.comentario.length() > 4000) {
			errorComentario = "La longitud máxima del comentario es de 4000 caracteres.";
		}

		if (errorComentario.equals("")) {
			ParticipantesConvocatoriasExtension pce = new

			ParticipantesConvocatoriasExtension();
			Investigador inv = servicioPersona
					.obtenerInvestigador(personaActual.getId());
			if (inv != null && inv instanceof InvestigadorInterno) {
				Dependencia dep = inv.getDependencia();
				pce.setDependencia(dep);
				pce.setInvestigador(inv);
				pce.setComentarioParticipacion(this.comentario);
				pce.setConvocatoriaDNE(convExt);
				pce.setFechaRegistro(new Date());
				servicioGeneral.guardarObjeto(pce);
				this.comentario = "";
				cargarComentarios();
			} else {
				errorComentario = "Error al agregar comentarios, comuníquese con HERMES, extensión 18416.";
			}
		}
	}

	public void limpiar() {
		sesion.removeAttribute("ManejadorConsultarConvocatoriasExtension");
	}

	public String atras() {
		limpiar();
		return "ConsultarConvocatoriaExtension";
	}

	public List<ConvocatoriasExtension> getListaConvocatoriasActivas() {
		return listaConvocatoriasActivas;
	}

	public void setListaConvocatoriasActivas(List<ConvocatoriasExtension>

	listaConvocatoriasActivas) {
		this.listaConvocatoriasActivas = listaConvocatoriasActivas;
	}

	public UIData getTablaConvocatoriasActivas() {
		return tablaConvocatoriasActivas;
	}

	public void setTablaConvocatoriasActivas(UIData tablaConvocatoriasActivas) {
		this.tablaConvocatoriasActivas = tablaConvocatoriasActivas;
	}

	public Persona getPersonaActual() {
		return personaActual;
	}

	public void setPersonaActual(Persona personaActual) {
		this.personaActual = personaActual;
	}

	public Long getCodigoConvocatoria() {
		return codigoConvocatoria;
	}

	public void setCodigoConvocatoria(Long codigoConvocatoria) {
		this.codigoConvocatoria = codigoConvocatoria;
	}

	public ConvocatoriasExtension getConvExt() {
		return convExt;
	}

	public void setConvExt(ConvocatoriasExtension convExt) {
		this.convExt = convExt;
	}

	public List<ConvocatoriasExtension> getListaArchivosObligatoriosSel() {
		return listaArchivosObligatoriosSel;
	}

	public void setListaArchivosObligatoriosSel(List<ConvocatoriasExtension>

	listaArchivosObligatoriosSel) {
		this.listaArchivosObligatoriosSel = listaArchivosObligatoriosSel;
	}

	public HtmlDataTable getTablaArchivosObligatoriosSel() {
		return tablaArchivosObligatoriosSel;
	}

	public void setTablaArchivosObligatoriosSel(
			HtmlDataTable tablaArchivosObligatoriosSel) {
		this.tablaArchivosObligatoriosSel = tablaArchivosObligatoriosSel;
	}

	public boolean isMostrarParticipacion() {
		return mostrarParticipacion;
	}

	public void setMostrarParticipacion(boolean mostrarParticipacion) {
		this.mostrarParticipacion = mostrarParticipacion;
	}

	public String getComentarioParticipacion() {
		return comentarioParticipacion;
	}

	public void setComentarioParticipacion(String comentarioParticipacion) {
		this.comentarioParticipacion = comentarioParticipacion;
	}

	/**
	 * @return the errorComentario
	 */
	public String getErrorComentario() {
		return errorComentario;
	}

	/**
	 * @param errorComentario
	 *            the errorComentario to set
	 */
	public void setErrorComentario(String errorComentario) {
		this.errorComentario = errorComentario;
	}

	/**
	 * @return the listaComentarios
	 */
	public List<ParticipantesConvocatoriasExtension> getListaComentarios() {
		return listaComentarios;
	}

	/**
	 * @param listaComentarios
	 *            the listaComentarios to set
	 */
	public void setListaComentarios(
			List<ParticipantesConvocatoriasExtension> listaComentarios) {
		this.listaComentarios = listaComentarios;
	}

	/**
	 * @return the tablaComentarios
	 */
	public HtmlDataTable getTablaComentarios() {
		return tablaComentarios;
	}

	/**
	 * @param tablaComentarios
	 *            the tablaComentarios to set
	 */
	public void setTablaComentarios(HtmlDataTable tablaComentarios) {
		this.tablaComentarios = tablaComentarios;
	}

	/**
	 * @return the comentario
	 */
	public String getComentario() {
		return comentario;
	}

	/**
	 * @param comentario
	 *            the comentario to set
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	/**
	 * @return the mostrarArchivos
	 */
	public boolean isMostrarArchivos() {
		return mostrarArchivos;
	}

	/**
	 * @param mostrarArchivos
	 *            the mostrarArchivos to set
	 */
	public void setMostrarArchivos(boolean mostrarArchivos) {
		this.mostrarArchivos = mostrarArchivos;
	}

	/**
	 * @return the mostrarComentarios
	 */
	public boolean isMostrarComentarios() {
		return mostrarComentarios;
	}

	/**
	 * @param mostrarComentarios
	 *            the mostrarComentarios to set
	 */
	public void setMostrarComentarios(boolean mostrarComentarios) {
		this.mostrarComentarios = mostrarComentarios;
	}

	/**
	 * @return the errorParticipacion
	 */
	public String getErrorParticipacion() {
		return errorParticipacion;
	}

	/**
	 * @param errorParticipacion
	 *            the errorParticipacion to set
	 */
	public void setErrorParticipacion(String errorParticipacion) {
		this.errorParticipacion = errorParticipacion;
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
}
