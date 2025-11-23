package co.edu.unal.hermes.vista.convocatoriasExtension;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIData;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import co.edu.unal.hermes.modelo.ConvocatoriasExtension;
import co.edu.unal.hermes.modelo.EstadoConvocatoria;
import co.edu.unal.hermes.modelo.ParticipantesConvocatoriasExtension;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorAdministrarConvocatoriasExtension extends ManejadorBase {
	private List<ConvocatoriasExtension> listaConvocatoriasActivas;
	private Persona personaActual;
	private Long codigoConvocatoria = -1L;
	private ConvocatoriasExtension convExt;
	private List<ConvocatoriasExtension> listaArchivosObligatoriosSel;
	private HtmlDataTable tablaArchivosObligatoriosSel;
	private boolean mostrarParticipacion;
	private String comentarioParticipacion;
	private boolean mostrarActivo;
	private List<ParticipantesConvocatoriasExtension> listaParticipantes;
	private UIData tablaParticipantes;
	private List<ParticipantesConvocatoriasExtension> listaVerConsultaConv;
	private UIData tablaVerConsultaConv;
	private List<ConvocatoriasExtension> listaComentarios;
	private HtmlDataTable tablaComentarios;
	private ConvocatoriasExtension convocatoriasExtensionSeleccionada;
	private boolean mostrarArchivos;

	@SuppressWarnings({ "unchecked", "deprecation" })
	public ManejadorAdministrarConvocatoriasExtension() {
		super();
		personaActual = (Persona) sesion.getAttribute("persona");
		convExt = new ConvocatoriasExtension();
		listaConvocatoriasActivas = new ArrayList<ConvocatoriasExtension>();
		listaConvocatoriasActivas = servicioGeneral
				.obtenerObjetos("select s from ConvocatoriasExtension s order by s.id DESC");
		mostrarParticipacion = true;
		listaParticipantes = new ArrayList<ParticipantesConvocatoriasExtension>();
		listaVerConsultaConv = new ArrayList<ParticipantesConvocatoriasExtension>();
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public String consultarConvocatorias() {
		// asignar codigo de la convocatoria
		codigoConvocatoria = convocatoriasExtensionSeleccionada.getId();
		List solSel = servicioGeneral.obtenerObjetoXID(
				"ConvocatoriasExtension", codigoConvocatoria.toString());
		convExt = (ConvocatoriasExtension) solSel.get(0);

		if (convExt.getEstadoConvocatoria().getId()
				.equals(EstadoConvocatoria.ACTIVA)) {
			mostrarActivo = false;
		} else {
			mostrarActivo = true;
		}

		listaArchivosObligatoriosSel = servicioGeneral
				.obtenerObjetos("select e from ConvocatoriasExtension e where e.id = '"
						+ codigoConvocatoria + "'");

		/*
		 * List participanteConv = servicioGeneral.obtenerObjetos(
		 * "select s from ParticipantesConvocatoriasExtension s where s.investigador.id.documento = '"
		 * + personaActual.getId().getDocumento() +
		 * "' and s.convocatoriaDNE = '" + convExt.getId() + "'");
		 */

		listaParticipantes = servicioGeneral
				.obtenerObjetos("select s from ParticipantesConvocatoriasExtension s where s.convocatoriaDNE = '"
						+ convExt.getId()
						+ "'"
						+ "and s.intencion = 'S' order by s.id DESC");

		listaVerConsultaConv = servicioGeneral
				.obtenerObjetos("select s from ParticipantesConvocatoriasExtension s where s.convocatoriaDNE = '"
						+ convExt.getId()
						+ "'"
						+ "and s.intencion = 'C' order by s.id DESC");

		/*
		 * if (participanteConv.size() > 0 && participanteConv != null) {
		 * mostrarParticipacion = false; }
		 */

		// ParticipantesConvocatoriasExtension ddd;
		// ddd.getInvestigador().getId().getDocumento()

		listaComentarios = servicioGeneral
				.obtenerObjetos("select s from ParticipantesConvocatoriasExtension s where s.convocatoriaDNE = '"
						+ convExt.getId()
						+ "'"
						+ "and s.intencion is null order by s.fechaRegistro DESC"); // order
		// by
		// s.investigador.id.documento
		// ,
		// s.fechaRegistro
		
		mostrarArchivos = false;
		if (listaArchivosObligatoriosSel != null
				&& listaArchivosObligatoriosSel.size() == 1
				&& listaArchivosObligatoriosSel.get(0).getTerminosReferencia() != null) {
			mostrarArchivos = true;
		}

		return "ConsultarParticipantesConvocatoriaExtension";
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

	public void confirmarParticipacion() {
		if (convExt.getEstadoConvocatoria().getId()
				.equals(EstadoConvocatoria.ACTIVA)) {
			EstadoConvocatoria eci = new EstadoConvocatoria();
			eci = servicioGeneral.obtenerObjetoXID(eci.getClass(),
					EstadoConvocatoria.INACTIVA).get(0);
			convExt.setEstadoConvocatoria(eci);
			servicioGeneral.guardarObjeto(convExt);
			mostrarActivo = true;
		} else {
			EstadoConvocatoria eca = new EstadoConvocatoria();
			eca = servicioGeneral.obtenerObjetoXID(eca.getClass(),
					EstadoConvocatoria.ACTIVA).get(0);
			convExt.setEstadoConvocatoria(eca);
			servicioGeneral.guardarObjeto(convExt);
			mostrarActivo = false;
		}

	}

	public void limpiar() {
		sesion.removeAttribute("ManejadorAdministrarConvocatoriasExtension");
		sesion.removeAttribute("ManejadorConsultarConvocatoriasExtension");
	}

	public String atras() {
		limpiar();
		return "AdministrarConvocatoriaExtension";
	}

	public List<ConvocatoriasExtension> getListaConvocatoriasActivas() {
		return listaConvocatoriasActivas;
	}

	public void setListaConvocatoriasActivas(
			List<ConvocatoriasExtension> listaConvocatoriasActivas) {
		this.listaConvocatoriasActivas = listaConvocatoriasActivas;
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

	public void setListaArchivosObligatoriosSel(
			List<ConvocatoriasExtension> listaArchivosObligatoriosSel) {
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

	public boolean isMostrarActivo() {
		return mostrarActivo;
	}

	public void setMostrarActivo(boolean mostrarActivo) {
		this.mostrarActivo = mostrarActivo;
	}

	public List<ParticipantesConvocatoriasExtension> getListaParticipantes() {
		return listaParticipantes;
	}

	public void setListaParticipantes(
			List<ParticipantesConvocatoriasExtension> listaParticipantes) {
		this.listaParticipantes = listaParticipantes;
	}

	public UIData getTablaParticipantes() {
		return tablaParticipantes;
	}

	public void setTablaParticipantes(UIData tablaParticipantes) {
		this.tablaParticipantes = tablaParticipantes;
	}

	/**
	 * @return the listaComentarios
	 */
	public List<ConvocatoriasExtension> getListaComentarios() {
		return listaComentarios;
	}

	/**
	 * @param listaComentarios
	 *            the listaComentarios to set
	 */
	public void setListaComentarios(
			List<ConvocatoriasExtension> listaComentarios) {
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

	public List<ParticipantesConvocatoriasExtension> getListaVerConsultaConv() {
		return listaVerConsultaConv;
	}

	public void setListaVerConsultaConv(
			List<ParticipantesConvocatoriasExtension> listaVerConsultaConv) {
		this.listaVerConsultaConv = listaVerConsultaConv;
	}

	public UIData getTablaVerConsultaConv() {
		return tablaVerConsultaConv;
	}

	public void setTablaVerConsultaConv(UIData tablaVerConsultaConv) {
		this.tablaVerConsultaConv = tablaVerConsultaConv;
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
	 * @return the mostrarArchivos
	 */
	public boolean isMostrarArchivos() {
		return mostrarArchivos;
	}
}
