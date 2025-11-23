package co.edu.unal.hermes.vista.aval;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.ArchivoAval;
import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.modelo.ConvocatoriaExterna;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.HistoricoHabilitacionEdicionProyecto;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.vista.aval.base.BaseManejadorSolicitarAvalFacultad;
import co.edu.unal.hermes.vista.evaluadores.ProyectoCoordinador;

public class ManejadorSolicitarAvalFacultad extends BaseManejadorSolicitarAvalFacultad {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String categoria = "";
	private SelectItem[] coordinadoresItem;
	private List coordinadoresFacultad;
	private String documentoCoordinador;
	private Dependencia dependenciaAval;
	private String veracidadCumplimiento;
	private ConvocatoriaExterna convocatoriaExterna;
	private boolean habilitarEdicion;

	public ManejadorSolicitarAvalFacultad() {
		sesion.removeAttribute("manejadorSemillerosSolicitudVIF");
		sesion.removeAttribute("manejadorSemillerosConsultaVIF");

		listaplantilla = servicioGeneral.obtenerListaObjetosWhere(CorreoPlantilla.class, "where c.tipo = 'A'");
		if (esListaVacia(listaplantilla)) {
			listaplantilla = new ArrayList<CorreoPlantilla>();
			correoActual = new CorreoPlantilla();
		} else {
			correoActual = listaplantilla.get(0);
		}

		// Plantillas Aval
		plantillas = new SelectItem[listaplantilla.size()];

		int i = 0;

		for (Iterator<CorreoPlantilla> ic = listaplantilla.iterator(); ic.hasNext();) {
			CorreoPlantilla p = ic.next();
			plantillas[i] = new SelectItem(p.getId().toString(), p.getNombre());
			i++;
		}
	}

	public List<Aval> getListaAval() {
		InvestigadorInterno investigadorInterno = servicioPersona
				.obtenerInvestigadorInterno(investigadorActual.getId());
		return servicioGeneral.obtenerAvales(investigadorInterno.getDependencia2().getFacultad().getId());
	}

	public void imprimirGrupo() {
		String id = aval.getNombreGrupoPINV();
		imprimirReporteFormatoGrupo(id);
	}

	public void imprimirAval() {
		if (aval != null) {
			servicioAval.imprimirReporteAval(aval.getAviId(), sesion);
		}

	}

	public void imprimirReporteRelacionado() throws SQLException {

		List<Aval> listaAvalesRelacionados = servicioGeneral.obtenerObjetosLimitado(Aval.class,
				"select #aviId a.aviId, #tipo a.tipo from Aval a where a.aviId = '" + aval.getAvalRelacionado()
						+ "' order by a.aviId asc");
		if (!esListaVacia(listaAvalesRelacionados)) {
			servicioAval.imprimirReporteAval(listaAvalesRelacionados.get(0).getAviId(), sesion);
		}
	}

	public void revisionVeracidad() {

	}

	public String editarAval() {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			borrarManejadoresInsercionProyecto();
			@SuppressWarnings("unchecked")
			Map<String, String> map = context.getExternalContext().getRequestParameterMap();
			Object o = map.get("idAval");
			Long id = Long.valueOf((String) o);
			List<Aval> lista = servicioGeneral.obtenerAval(id.toString());
			nombreInvestigador = "";

			if (!esListaVacia(lista)) {
				aval = lista.get(0);
				Persona personaAux = servicioPersona
						.obtenerPersona(new IdPersona(aval.getDocumento(), aval.getTipoDocumento()));
				nombreInvestigador = personaAux.getNombreCompletoMinusculas();
				dependenciaAval = aval.getDependencia();
				if (!esCadenaVacia(aval.getAviConvocatoria())) {
					convocatoriaExterna = obtenerConvocatoriaExterna(Long.parseLong(aval.getAviConvocatoria()));
				} else {
					convocatoriaExterna = new ConvocatoriaExterna();
				}

				if (aval.isEsJornadaDocente()) {
					selItem = 2;
				}

				consultarArchivosAdjuntos();
				verificarRevisionEvaluacion();
			}
		} catch (Exception e) {
			return "";
		}
		return "consultaAvalFacultadGenerar";
	}

	private void verificarRevisionEvaluacion() {
		// Configuración de Vista

		String consulta = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo like 'ACTIVIDAD_AVAL_NEW' "
				+ " and dd.identificador.tipo = '" + aval.getTipo() + "' order by dd.descripcion";
		List listaAvalDominio = servicioGeneral.obtenerObjetos(DominioDetalle.class, consulta);

		if (!esListaVacia(listaAvalDominio)) {
			DominioDetalle dominio = (DominioDetalle) listaAvalDominio.get(0);
			categoria = dominio.getEstado();

			if ("FAC-".startsWith(categoria)) {
				verRevision = true;
				verEvaluacion = false;
			}

			if ("FAC".equals(categoria) || categoria.endsWith("-FAC") || aval.isEsProyectoContrapartida()) {
				verRevision = false;
				verEvaluacion = true;
				consultarCoordinadoresFacultad();
			}
		} else {
			verRevision = true;
			verEvaluacion = false;
		}
	}

	private void consultarCoordinadoresFacultad() {
		coordinadoresFacultad = servicioGeneral.obtenerObjetos(InvestigadorInterno.class,
				"select ii from PersonaRol pr, InvestigadorInterno ii "
						+ "where pr.nombre = 'C' and ii.id.documento = pr.documento and ii.id.tipoDocumento = pr.tipoDocumento and "
						+ "ii.dependencia.facultad.id = '" + investigadorActual.getDependencia2().getFacultad().getId()
						+ "'");
		if (!esListaVacia(coordinadoresFacultad)) {
			coordinadoresItem = new SelectItem[coordinadoresFacultad.size()];
			for (int i = 0; i < coordinadoresFacultad.size(); i++) {
				InvestigadorInterno coordinador = (InvestigadorInterno) coordinadoresFacultad.get(i);
				coordinadoresItem[i] = new SelectItem(coordinador.getId().getDocumento(),
						coordinador.getNombreCompletoMinusculas());
			}
		}
	}

	public String guardar() {
		if (aval == null) {
			return "";
		}
		personaActual = (Persona) sesion.getAttribute("persona");

		if (verificarDecision()) {

			// Guardar Aval
			if (!aval.getAviEstado().equals(Aval.DEVUELTO)) {
				this.aval.setAviEstado(Aval.REVISADO_FACULTAD);
			}

			aval.setAviFechaAvalFac(new Date());
			this.servicioGeneral.guardarObjeto(this.aval);
			guardarArchivosSoporte();

			Persona personaAux = servicioPersona
					.obtenerPersona(new IdPersona(aval.getDocumento(), aval.getTipoDocumento()));

			if (this.aval.getAviAvalfacultad() != null && this.aval.getAviAvalfacultad().equals(Aval.APROBADO)) {

				correoActual = cargarPlantilla(77);
				editarCorreo(personaAux, aval);
				Correo correo = new Correo();
				correo.setOrigen(Correo.CORREO_HERMES);
				correo.adicionarDireccion(personaAux.getEmail());
				// correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
				correo.adicionarCopiaOculta(personaActual.getEmail());
				correo.setAsunto(correoActual.getAsunto());
				correo.setCuerpo(cuerpoCorreo);

				if (categoria.endsWith("-DI")) {

					String sql = "JOIN i.roles r " + "WHERE r.id = 'AD' " + "AND i.dependencia.sede.id = '"
							+ this.investigadorActual.getDependencia2().getSede().getId() + "'";

					for (InvestigadorInterno inv : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class,
							sql)) {
						if (tieneRolVigente(inv, "AD")) {
							correo.adicionarCopiaOculta(inv.getEmail());
						}
					}

				}
				servicioCorreo.enviarCorreo(correo);
			}

			if (this.aval.getAviAvalfacultad() != null && this.aval.getAviAvalfacultad().equals(Aval.NEGADO)) {

				correoActual = cargarPlantilla(78);
				enviarCorreoGenerico(correoActual, personaAux, false);
			}

			if (this.aval.getAviEstado().equals(Aval.DEVUELTO)) {
				correoActual = cargarPlantilla(170);
				enviarCorreoGenerico(correoActual, personaAux, true);
			}

			InvestigadorInterno investigadorInterno = servicioPersona
					.obtenerInvestigadorInterno(investigadorActual.getId());
			if (investigadorInterno.getDependencia2().getId() != null) {
				listaAval = servicioGeneral.obtenerAvales(investigadorInterno.getDependencia2().getId());
			}
			crearHistoricoEstadoAval(aval, personaActual, "FA");
			sesion.removeAttribute("manejadorProyectosInvestigador");
		} else {
			return "";
		}

		sesion.removeAttribute("manejadorSolicitarAvalFacultad");
		return "consultaAvalFacultad";
	}

	private boolean verificarDecision() {

		boolean temp = false;
		if (this.selItem == 1) {
			aval.setAviAvalfacultad("");
		}
		if (this.selItem == 2) {
			aval.setAviAvalfacultad(Aval.APROBADO);
			temp = true;
		}
		if (this.selItem == 3) {
			if (esCadenaVacia(aval.getDescripcionfacultad())) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage("msgs",
						new FacesMessage("Debe ingresar los comentarios de la NO APROBACIÓN del aval", ""));
				temp = false;
			} else {
				aval.setAviAvalfacultad(Aval.NEGADO);
				temp = true;
			}
		}
		if (this.selItem == 4) {
			if (esCadenaVacia(aval.getDescripcionfacultad())) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage("msgs",
						new FacesMessage("Debe ingresar los comentarios de la DEVOLUCIÓN del aval", ""));
				temp = false;
			} else {
				aval.setAviEstado(Aval.DEVUELTO);
				verificarCambioEstadoProyecto();
				temp = true;
			}
		}
		return temp;
	}

	public void guardarArchivoAval(FileUploadEvent event) {
		archivoCargar = event.getFile();
		ArchivoAval aa = insertarArchivoAvalGenerico(aval.getAviId(), archivoCargar, true, null);
		if (aa != null) {
			aval.getArchivos().add(aa);
		}
	}

	private Persona consultarCoordinador() {
		if (!esListaVacia(coordinadoresFacultad)) {
			for (int i = 0; i < coordinadoresFacultad.size(); i++) {
				InvestigadorInterno ii = (InvestigadorInterno) coordinadoresFacultad.get(i);
				if (documentoCoordinador.equals(ii.getId().getDocumento())) {
					return (Persona) coordinadoresFacultad.get(i);
				}
			}
		}
		return null;
	}

	private void verificarCambioEstadoProyecto() {
		if (edicionProyecto) {
			Proyecto proyectoAval = servicioProyecto.obtenerProyecto(aval.getIdProyecto(),
					ProyectoDAOHibernate.DATOS_BASICOS);
			if (proyectoAval.getModalidad().getId().equals(MODALIDAD_FICHA_MINIMA_ID)
					&& proyectoAval.getEstadoProyecto().getId().equals(EstadoProyecto.PROPUESTO)) {
				proyectoAval.cambiarEstadoPersona(EstadoProyecto.INGRESANDO, cargarPersonaActual(),
						"Devolución aval desde la Vicedecanatura de la Facultad.");
				servicioGeneral.guardarObjeto(proyectoAval);
			} else {
				edicionProyecto = false;
			}
		}
	}

	public String guardarAvalGenerado() {
		if (aval == null) {
			return "";
		}
		personaActual = (Persona) sesion.getAttribute("persona");
		boolean temp = false;
		if (this.selItem == 1) {
			aval.setAviAvalfacultad("");
		}
		if (this.selItem == 2) {
			aval.setAviAvalfacultad(Aval.APROBADO);

			if (aval.isEsJornadaDocente()) {

				if (!habilitarEdicion) {

					if (esCadenaVacia(documentoCoordinador)) {
						mensajeError(
								"Debe seleccionar un coordinador en la Facultad para el seguimiento del proyecto aprobado");
						return "";
					}
					aprobarProyectoJornadaDocente();
					asignarCoordinadorJornada();
				} else {
					habilitarEdicionProyecto();
					aval.setAviAvalfacultad(null);
				}
			}
			temp = true;
		}
		if (this.selItem == 3) {
			if (esCadenaVacia(aval.getDescripcionfacultad())) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage("msgs",
						new FacesMessage("Debe ingresar los comentarios de la NO APROBACIÓN del aval", ""));
				return "";
			}
			aval.setAviAvalfacultad(Aval.NEGADO);
			temp = true;
		}
		if (this.selItem == 4) {
			if (esCadenaVacia(aval.getDescripcionfacultad())) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage("msgs",
						new FacesMessage("Debe ingresar los comentarios de la DEVOLUCIÓN del aval", ""));
				temp = false;
				return "";
			}
			aval.setAviEstado(Aval.DEVUELTO);
			verificarCambioEstadoProyecto();
			temp = true;
		}

		// Guardar archivos soporte
		guardarArchivosSoporte();

		if (temp) {

			// Guardar Aval
			if (!aval.getAviEstado().equals(Aval.DEVUELTO) && !aval.isEsJornadaDocente()) {
				this.aval.setAviEstado(Aval.REVISADO_FACULTAD);
			}

			aval.setAviFechaAvalFac(new Date());
			this.servicioGeneral.guardarObjeto(this.aval);

			// Enviar correos

			IdPersona idAux = new IdPersona();
			idAux.setDocumento(aval.getDocumento());
			idAux.setTipoDocumento(aval.getTipoDocumento());
			Persona personaAux = servicioPersona.obtenerPersona(idAux);

			if (this.aval.getAviAvalfacultad() != null && this.aval.getAviAvalfacultad().equals(Aval.APROBADO)
					&& !aval.isEsJornadaDocente()) {

				correoActual = cargarPlantilla(150);
				enviarCorreoGenerico(correoActual, personaAux, false);
			}

			if (this.aval.getAviAvalfacultad() != null && this.aval.getAviAvalfacultad().equals(Aval.NEGADO)) {

				correoActual = cargarPlantilla(151);
				enviarCorreoGenerico(correoActual, personaAux, false);
			}

			if (this.aval.getAviEstado().equals(Aval.DEVUELTO)) {
				correoActual = cargarPlantilla(170);
				enviarCorreoGenerico(correoActual, personaAux, true);
			}
			crearHistoricoEstadoAval(aval, personaActual, "FA");
		}

		return anterior();
	}

	private void asignarCoordinadorJornada() {
		InvestigadorInterno revisor = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());
		Proyecto proyectoAval = servicioProyecto.obtenerProyecto(aval.getIdProyecto(),
				ProyectoDAOHibernate.DATOS_BASICOS);
		if (proyectoAval.getModalidad().getId().equals(MODALIDAD_FICHA_MINIMA_ID)) {
			if (consultarCoordinador() != null) {

				String consulta = " from ProyectoCoordinador pc where pc.idProyecto = '"
						+ proyectoAval.getId().toString() + "'";
				List<ProyectoCoordinador> lista = servicioGeneral.obtenerObjetos(ProyectoCoordinador.class, consulta);
				if (!esListaVacia(lista)) {
					servicioProyecto.modificarCoordinadorEnProyecto(ProyectoDAOHibernate.UPDATE, "S",
							proyectoAval.getId().toString(), consultarCoordinador().getId(), cargarPersonaActual(), "S",
							true);
				} else {
					servicioProyecto.modificarCoordinadorEnProyecto(ProyectoDAOHibernate.INSERT, "S",
							proyectoAval.getId().toString(), consultarCoordinador().getId(), cargarPersonaActual(), "S",
							true);
				}

				// Enviar correo
				correoActual = cargarPlantilla(280);
				Correo correo = new Correo();
				correo.setAsunto(correoActual.getAsunto());
				correo.setCuerpo(correoActual.getCuerpo()
						.replaceAll("<<PERSONA>>", consultarCoordinador().getNombreCompletoMinusculas())
						.replaceAll("<<PROYECTO>>", proyectoAval.getId().toString() + " - " + proyectoAval.getNombre())
						.replaceAll("<<DEPENDENCIA>>",
								"Facultad de " + revisor.getDependencia2().getFacultad().getNombre())
						.replaceAll("<<OBSERVACIONES>>", aval.getDescripcionfacultad()));
				correo.setOrigen(Correo.CORREO_HERMES);
				correo.adicionarDireccion(consultarCoordinador().getEmail());
				correo.adicionarDireccion(personaActual.getEmail());
				// correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
				servicioCorreo.enviarCorreo(correo);
			}
			servicioGeneral.guardarObjeto(proyectoAval);
		}
	}

	private InvestigadorInterno consultarDirectorUAB(Aval aval) {

		InvestigadorInterno directorUab = new InvestigadorInterno();

		try {

			List<InvestigadorInterno> uab = servicioGeneral.obtenerObjetos(InvestigadorInterno.class,
					"select ii from Persona p, PersonaRol pr, Aval a, InvestigadorInterno ii "
							+ " where pr.nombre = 'DD' and a.dependencia.id = ii.dependencia2.id and p.id.documento = ii.id.documento and "
							+ "ii.id.tipoDocumento = p.id.tipoDocumento and p.id.documento = pr.documento and p.id.tipoDocumento = pr.tipoDocumento and a.aviId = '"
							+ aval.getAviId() + "' " + "and p.id.documento not in (" + DOCUMENTOS_PRUEBAS_DESARROLLO
							+ ")");

			directorUab = uab.get(0);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			directorUab.setEmail(Correo.CORREO_HERMES);
		} finally {
			return directorUab;
		}

	}

	private void habilitarEdicionProyecto() {
		InvestigadorInterno solicitante = servicioPersona
				.obtenerInvestigadorInterno(new IdPersona(aval.getDocumento(), aval.getTipoDocumento()));

		Proyecto proyectoAval = servicioProyecto.obtenerProyecto(aval.getIdProyecto(),
				ProyectoDAOHibernate.DATOS_BASICOS);
		proyectoAval.setPermitirModificacion("S");
		guardarHistoricoEdicionProyecto(proyectoAval);
		servicioProyecto.actualizarProyecto(proyectoAval);

		// Enviar correo
		correoActual = cargarPlantilla(393);
		Correo correo = new Correo();
		correo.setAsunto(correoActual.getAsunto());
		correo.setCuerpo(correoActual.getCuerpo().replaceAll("<<PROYECTO>>", proyectoAval.getNombre())
				.replaceAll("<<ID>>", proyectoAval.getId().toString())
				.replaceAll("<<DEPENDENCIA>>", solicitante.getDependencia().getFacultad().getNombre())
				.replaceAll("<<COMENTARIOS>>", aval.getDescripcionfacultad()));
		correo.setOrigen(Correo.CORREO_HERMES);
		correo.adicionarDireccion(consultarDirectorUAB(aval).getEmail());
		correo.adicionarDireccion(solicitante.getEmail());
		// correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
		servicioCorreo.enviarCorreo(correo);

	}

	private void guardarHistoricoEdicionProyecto(Proyecto proyectoActual) {
		HistoricoHabilitacionEdicionProyecto historicoHabilitacionEdicionProyecto = new HistoricoHabilitacionEdicionProyecto(
				proyectoActual, personaActual, proyectoActual.getPermitirModificacion());
		historicoHabilitacionEdicionProyecto
				.setComentarios("VIF - Aval jornada docente: " + aval.getDescripcionfacultad());
		servicioGeneral.guardarObjeto(historicoHabilitacionEdicionProyecto);
	}

	public String anterior() {
		sesion.removeAttribute("manejadorSolicitarAvalFacultad");
		return "consultaAvalFacultad";
	}

	public SelectItem[] getCoordinadoresItem() {
		return coordinadoresItem;
	}

	public void setCoordinadoresItem(SelectItem[] coordinadoresItem) {
		this.coordinadoresItem = coordinadoresItem;
	}

	public String getDocumentoCoordinador() {
		return documentoCoordinador;
	}

	public void setDocumentoCoordinador(String documentoCoordinador) {
		this.documentoCoordinador = documentoCoordinador;
	}

	public Dependencia getDependenciaAval() {
		return dependenciaAval;
	}

	public void setDependenciaAval(Dependencia dependenciaAval) {
		this.dependenciaAval = dependenciaAval;
	}

	public String getVeracidadCumplimiento() {
		return veracidadCumplimiento;
	}

	public void setVeracidadCumplimiento(String veracidadCumplimiento) {
		this.veracidadCumplimiento = veracidadCumplimiento;
	}

	public ConvocatoriaExterna getConvocatoriaExterna() {
		return convocatoriaExterna;
	}

	public void setConvocatoriaExterna(ConvocatoriaExterna convocatoriaExterna) {
		this.convocatoriaExterna = convocatoriaExterna;
	}

	public boolean isHabilitarEdicion() {
		return habilitarEdicion;
	}

	public void setHabilitarEdicion(boolean habilitarEdicion) {
		this.habilitarEdicion = habilitarEdicion;
	}
}
