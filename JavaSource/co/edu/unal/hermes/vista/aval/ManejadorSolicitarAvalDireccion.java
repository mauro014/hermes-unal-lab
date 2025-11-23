package co.edu.unal.hermes.vista.aval;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.ArchivoAval;
import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.Reporte;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.aval.base.BaseManejadorSolicitarAvalDireccion;
import co.edu.unal.hermes.vista.evaluadores.ProyectoCoordinador;

public class ManejadorSolicitarAvalDireccion extends BaseManejadorSolicitarAvalDireccion {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean verRevision;
	boolean verEvaluacion;
	String categoria; // para evaluacion de la ruta de revision
	DominioDetalle dominio; // para evaluacion de la ruta de revision
	String nombreDecano = "";
	String valorLetras = "";
	boolean contratacionDecanos;
	private SelectItem[] coordinadoresItem;
	private List coordinadores;
	private String documentoCoordinador;

	public ManejadorSolicitarAvalDireccion() {
		sesion.removeAttribute("manejadorSemillerosSolicitudDI");
		sesion.removeAttribute("manejadorSemillerosConsultaDI");
		InvestigadorInterno investigadorInterno = servicioPersona
				.obtenerInvestigadorInterno(investigadorActual.getId());

		List<Aval> listaAvalAux = new ArrayList<Aval>();
		listaAvalAux = servicioGeneral
				.obtenerAvalesDireccion(String.valueOf(investigadorInterno.getDependencia2().getSede().getId()));
		organizarListaRevisionAval(listaAvalAux);
	}

	public String editarAval() {
		consultarAvalTramitar();
		String consulta = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo like 'ACTIVIDAD_AVAL_NEW' "
				+ " and dd.identificador.tipo = '" + aval.getTipo() + "' order by dd.descripcion";

		List<DominioDetalle> listaAvalDominio = servicioGeneral.obtenerObjetos(DominioDetalle.class, consulta);

		if (!esListaVacia(listaAvalDominio)) {
			dominio = (DominioDetalle) listaAvalDominio.get(0);
			categoria = dominio.getEstado();
			verEvaluacion = true;
			verRevision = false;

			if ((categoria.startsWith("DI-")) || (categoria.endsWith("-DI") && aval.getInterSedes() != null
					&& "S".equals(aval.getInterSedes()))) {
				verRevision = true;
				verEvaluacion = false;
			}

			if (aval.isEsJornadaDocente() || aval.isEsConvocatoriaRegalias()) {
				consultarCoordinadores();
				selItem = 2;
			}

		}

		listaplantilla = servicioAval.obtenerListaCartas(aval.getTipo());
		if (!esListaVacia(listaplantilla)) {
			plantillas = new SelectItem[listaplantilla.size()];
			int i = 0;
			for (Iterator<Reporte> ic = listaplantilla.iterator(); ic.hasNext();) {
				Reporte p = ic.next();
				plantillas[i] = new SelectItem(p.getId().toString(), p.getNombreExterno());
				i++;
			}
		}
		return "consultaAvalGenerar";
	}

	public void validarSiEsParaVice() {
		if (aval.getEsAvalParaRevisionVice().equals("S")) {
			verRevision = true;
			verEvaluacion = false;
		} else {
			verEvaluacion = true;
			verRevision = false;
		}
	}

	public void reporteAval() throws SQLException {
		if (esCadenaVacia(avalId)) {
			mensajeError("Debe seleccionar una plantilla de carta para generarla.");
			return;
		}

		ReporteBirt r = new ReporteBirt();
		String textoAval = aval.getAviTextoCompCoor() != null ? aval.getAviTextoCompCoor() : "-";
		servicioGeneral.ejecutarSentencia("UPDATE HER_AVAL SET AVI_TEXTO_COMP_COOR = '" + textoAval
				+ "' WHERE AVI_ID = '" + aval.getAviId().toString() + "'");
		r.adicionarParametro("Decano", "NO");

		if (contratacionDecanos) {
			r.adicionarParametro("Decano", nombreDecano);
		}

		String duracionMeses = "---";
		String valorContrapartidaLetras = "-";
		if (aval.isEsRegalias() || aval.isEsConvocatoriaRegalias()) {
			valorContrapartidaLetras = convertir(
					String.valueOf(aval.getAviEspecieunal() + (aval.getValorPersonalTotal())), false);
			Proyecto proyecto = servicioProyecto.obtenerProyecto(aval.getIdProyecto(),
					ProyectoDAOHibernate.DATOS_BASICOS);
			duracionMeses = convertirDuracionMeses(proyecto.getDuracionTipo(), proyecto.getDuracion());
		}
		r.adicionarParametro("Id", aval.getAviId().toString());
		r.adicionarParametro("valorletras", valorLetras);
		r.adicionarParametro("meses", duracionMeses);
		Calendar calendar = Calendar.getInstance();
		int mes = calendar.get(Calendar.MONTH);
		r.adicionarParametro("valorletras", valorContrapartidaLetras);
		r.adicionarParametro("mes", getNombreMes(mes));
		r.adicionarParametro("valorNumero", String.valueOf(aval.getAviEspecieunal() + (aval.getValorPersonalTotal())));
		r.adicionarParametro("d", investigadorActual.getDependencia2().getSede().getId().toString());
		r.adicionarParametro("Ciu", investigadorActual.getDependencia2().getSede().getNombre());
		r.adicionarParametro("Sed", investigadorActual.getDependencia2().getSede().getNombre());
		r.setNombreReporte(obtenerNombreCarta(avalId));

		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
	}

	public String guardarRevision() {
		if (aval == null) {
			return "";
		}
		personaActual = (Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL);
		boolean temp = false;
		aval.setAviAvaldireccion("");

		if (this.selItem == 2) {
			if (aval.isEsConvocatoriaRegalias() && esCadenaVacia(documentoCoordinador)) {
				mensajeError("Debe seleccionar un coordinador en la dependencia para el seguimiento del proyecto");
				return "";
			}
			aval.setAviEstado(Aval.REVISADO_DIRECCION);
			aval.setAviAvaldireccion(Aval.APROBADO);
			this.aval.setAviFechaAvalCoor(new Date());
			if (aval.getEsAvalParaRevisionDRE() != null && aval.getEsAvalParaRevisionDRE().equals("S")) {
				aval.setEsAvalParaRevisionVice("N");
				aval.setAviFechaAvalVice(null);
				aval.setTextoVicerrectoria(null);
			}
			temp = true;
		}
		if (this.selItem == 3) {
			aval.setAviEstado(Aval.REVISADO_DIRECCION);
			aval.setAviAvaldireccion(Aval.NEGADO);
			temp = true;
		}

		if (this.selItem == 4) {
			aval.setAviEstado(Aval.DEVUELTO);
			aval.setAviAvalfacultad(null);
			aval.setAviAvaldireccion(null);
			this.aval.setAviFechaAvalCoor(new Date());
			verificarCambioEstadoProyecto();
			temp = true;
		}

		if (temp) {
			this.aval.setAviFechaAvalCoor(new Date());
			this.servicioGeneral.guardarObjeto(this.aval);
			// Guardar archivos coordinador
			for (ArchivoAval aa : aval.getArchivosCoorList()) {
				aa.setAvalCoor(aval.getAviId());
				servicioGeneral.guardarObjeto(aa);
			}
			// ENVIAR CORREO
			Persona solicitanteAval = servicioPersona
					.obtenerPersona(new IdPersona(aval.getDocumento(), aval.getTipoDocumento()));

			if (this.aval.getAviAvaldireccion() != null && this.aval.getAviAvaldireccion().equals(Aval.APROBADO)) {
				if (aval.isEsConvocatoriaRegalias()) {
					asignarCoordinador();
				}
				verificarPasoAVicerrectoria(solicitanteAval);
				if (aval.getEsAvalParaRevisionDRE() != null && aval.getEsAvalParaRevisionDRE().equals("S")) {
					CorreoPlantilla cpNotificacion = cargarPlantilla(335);
					InvestigadorInterno ii = servicioPersona
							.obtenerInvestigadorInterno(new IdPersona(aval.getDocumento(), aval.getTipoDocumento()));
					Correo correoNotificacion = editarCorreo(cpNotificacion, ii, aval);
					String sql = "JOIN i.roles r WHERE r.id = 'AI'";

					for (InvestigadorInterno p : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class,
							sql)) {
						if (tieneRolVigente(p, "AI") && p.getEmail() != null) {
							correoNotificacion.adicionarDireccion(p.getEmail());
						}
					}

					servicioCorreo.enviarCorreo(correoNotificacion);
					correoActual = cargarPlantilla(334);
					Correo correo1 = editarCorreo(correoActual, solicitanteAval, aval);
					servicioCorreo.enviarCorreo(correo1);
				}
			}

			if (this.aval.getAviAvaldireccion() != null && this.aval.getAviAvaldireccion().equals(Aval.NEGADO)) {

				correoActual = cargarPlantilla(78);
				Correo correo = editarCorreo(correoActual, solicitanteAval, aval);
				servicioCorreo.enviarCorreo(correo);
			}

			if (this.aval.getAviEstado().equals(Aval.DEVUELTO)) {

				correoActual = cargarPlantilla(170);
				Correo correo = editarCorreo(correoActual, solicitanteAval, aval);
				servicioCorreo.enviarCorreo(correo);
			}

			InvestigadorInterno investigadorInterno = servicioPersona
					.obtenerInvestigadorInterno(investigadorActual.getId());
			listaAval = servicioGeneral.obtenerAvales(investigadorInterno.getDependencia2().getId());
			mensajeInfo("Revisión guardada satisfactoriamente");
			crearHistoricoEstadoAval(aval, cargarPersonaActual(), "DI");

		} else {
			return "";
		}

		return anterior();
	}

	public void cambiarEstadoProyecto() {
		Long idProyecto = aval.getIdProyecto();
		Proyecto p = servicioProyecto.obtenerProyecto(idProyecto, ProyectoDAOHibernate.DATOS_BASICOS);
		if (p != null) {
			p.cambiarEstadoPersona(EstadoProyecto.AVAL_APROBADO, personaActual, "Aval aprobado con este proyecto");
			servicioGeneral.guardarObjeto(p);
		}
	}

	private void verificarPasoAVicerrectoria(Persona solicitanteAval) {
		if (categoria.endsWith("-VRIE") || aval.getEsAvalParaRevisionVice().equals("S")) {
			correoActual = cargarPlantilla(156);
			Correo correo = editarCorreo(correoActual, solicitanteAval, aval);
			servicioCorreo.enviarCorreo(correo);

			// Correo a la VRIE
			correoActual = cargarPlantilla(142);
			Correo correo2 = editarCorreo(correoActual, solicitanteAval, aval);
			// Correo a la vicerrectoría de Investigación
			String sql = "JOIN i.roles r WHERE r.id = 'AV'";

			for (InvestigadorInterno p : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class, sql)) {
				if (tieneRolVigente(p, "AV") && p.getEmail() != null) {
					correo2.adicionarDireccion(p.getEmail());
				}
			}

			servicioCorreo.enviarCorreo(correo2);
		}
	}

	private void verificarCambioEstadoProyecto() {
		if (edicionProyecto) {
			Proyecto proyectoAval = servicioProyecto.obtenerProyecto(aval.getIdProyecto(),
					ProyectoDAOHibernate.DATOS_BASICOS);
			if (proyectoAval.getModalidad().getId().equals(MODALIDAD_FICHA_MINIMA_ID)
					&& proyectoAval.getEstadoProyecto().getId().equals(EstadoProyecto.PROPUESTO)) {
				proyectoAval.cambiarEstadoPersona(EstadoProyecto.INGRESANDO, cargarPersonaActual(),
						"Devolución aval desde Dirección de Investigación.");
				servicioGeneral.guardarObjeto(proyectoAval);
			} else {
				edicionProyecto = false;
			}
		}
	}

	private void consultarCoordinadores() {
		coordinadores = servicioGeneral.obtenerObjetos(InvestigadorInterno.class,
				"select ii from PersonaRol pr, InvestigadorInterno ii "
						+ "where pr.nombre = 'C' and ii.id.documento = pr.documento and ii.id.tipoDocumento = pr.tipoDocumento and "
						+ "ii.dependencia.sede.id = '" + investigadorActual.getDependencia2().getSede().getId()
						+ "' and (pr.fechaFinRol is null or pr.fechaFinRol > SYSDATE - 1) order by ii.nombre1 asc");
		if (!esListaVacia(coordinadores)) {
			coordinadoresItem = new SelectItem[coordinadores.size()];
			for (int i = 0; i < coordinadores.size(); i++) {
				InvestigadorInterno coordinador = (InvestigadorInterno) coordinadores.get(i);
				coordinadoresItem[i] = new SelectItem(coordinador.getId().getDocumento(),
						coordinador.getNombreCompletoMinusculas());
			}
		}
	}

	public String guardarEvaluacion() {

		if (aval == null) {
			return "";
		}

		personaActual = (Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL);
		if (!aval.isEsJornadaDocente()) {
			this.aval.setAviEstado(Aval.REVISADO_DIRECCION);
		}
		this.aval.setAviFechaAvalCoor(new Date());
		aval.setAviAvaldireccion("");
		boolean temp = false;

		if (this.selItem == 2) {
			aval.setAviAvaldireccion(Aval.APROBADO);

			if (aval.isEsJornadaDocente() || aval.isEsConvocatoriaRegalias()) {

				if (esCadenaVacia(documentoCoordinador)) {
					mensajeError("Debe seleccionar un coordinador en la dependencia para el seguimiento del proyecto");
					return "";
				} else {
					asignarCoordinador();
				}

				if (aval.isEsJornadaDocente()) {
					aprobarProyectoJornadaDocente();
				}

				temp = true;
			} else {
				if (aval.getArchivoAval() != null && aval.getArchivoAval().length() > 0) {
					temp = true;
				} else if (aval.isEsProyectoContrapartida() || aval.isEsGrupoInvestigacion()
						|| aval.isEsInvestigadorIndependiente() || aval.isEsCentroInvestigacion()
						|| (aval.getEsAvalParaRevisionDRE() != null && aval.getEsAvalParaRevisionDRE().equals("S"))
						|| (!esCadenaVacia(aval.getAviNumeroConvocatoria()) && aval.getAviNumeroConvocatoria().trim()
								.equals(CONVOCATORIA_JOVENES_INVESTIGADORES))) {
					temp = true;
				} else {
					mensajeError("Si la decisión es aprobar por favor adjunte el documento del aval");
					return "";
				}
			}
			if (aval.getEsAvalParaRevisionDRE() != null && aval.getEsAvalParaRevisionDRE().equals("S")) {
				aval.setEsAvalParaRevisionVice("N");
				aval.setAviFechaAvalVice(null);
				aval.setTextoVicerrectoria(null);
			}
		}

		if (this.selItem == 3) {
			if (esCadenaVacia(aval.getAviTextoCompCoor())) {
				mensajeError("Debe ingresar los comentarios de la NO APROBACIÓN del aval");
				return "";
			}
			aval.setAviAvaldireccion(Aval.NEGADO);
			temp = true;
		}

		if (this.selItem == 4) {
			if (esCadenaVacia(aval.getAviTextoCompCoor())) {
				mensajeError("Debe ingresar los comentarios de la DEVOLUCIÓN del aval");
				return "";
			}
			aval.setAviEstado(Aval.DEVUELTO);
			aval.setAviAvalfacultad(null);
			aval.setAviAvaldireccion(null);
			verificarCambioEstadoProyecto();
			temp = true;
		}
		if (temp) {
			this.servicioGeneral.guardarObjeto(this.aval);
			// Guardar archivos revision
			for (ArchivoAval aa : aval.getArchivosCoorList()) {
				aa.setAvalCoor(aval.getAviId());
				servicioGeneral.guardarObjeto(aa);
			}

			// Enviar correos
			Persona solicitanteAval = servicioPersona
					.obtenerPersona(new IdPersona(aval.getDocumento(), aval.getTipoDocumento()));

			// Notificación de negación al investigador
			if (!esCadenaVacia(aval.getAviAvaldireccion()) && aval.getAviAvaldireccion().equals(Aval.NEGADO)) {
				correoActual = cargarPlantilla(143);
				Correo correo = editarCorreo(correoActual, solicitanteAval, aval);
				servicioCorreo.enviarCorreo(correo);
			}
			// Notificación de aprobación
			if (!esCadenaVacia(aval.getAviAvaldireccion()) && aval.getAviAvaldireccion().equals(Aval.APROBADO)
					&& !aval.isEsJornadaDocente()) {

				cambiarEstadoProyecto();
				verificarProyectoContrapartida();

				if (aval.isEsGrupoInvestigacion() || aval.isEsInvestigadorIndependiente()) {
					correoActual = cargarPlantilla(258);
					Correo correo1 = editarCorreo(correoActual, solicitanteAval, aval);
					String asunto1 = correoActual.getAsunto();
					asunto1 = asunto1.replaceAll("<<TIPO>>", aval.getNombreTipo());
					asunto1 = asunto1.replaceAll("<<VRI>>", "");
					if (aval.getEsAvalParaRevisionVice().equals("S")) {
						asunto1 = asunto1.replaceAll("<<VRI>>",
								"Su solicitud ha sido remitida a la Vicerrectoría de Investigación para la activación del aval en la aplicación InstituLAC de la plataforma ScienTI de Colciencias, lo cual será notificado a su correo electrónico.");
					}

					correo1.setAsunto(asunto1);
					servicioCorreo.enviarCorreo(correo1);

				} else if (aval.getEsAvalParaRevisionDRE() != null && aval.getEsAvalParaRevisionDRE().equals("N")
						&& (esCadenaVacia(aval.getAviNumeroConvocatoria())
								|| (aval.getAviNumeroConvocatoria() != null && !aval.getAviNumeroConvocatoria().trim()
										.equals(CONVOCATORIA_JOVENES_INVESTIGADORES)))) {
					correoActual = cargarPlantilla(5);
					Correo correo1 = editarCorreo(correoActual, solicitanteAval, aval);
					servicioCorreo.enviarCorreo(correo1);
				} else if (aval.getEsAvalParaRevisionDRE() != null && aval.getEsAvalParaRevisionDRE().equals("S")) {
					CorreoPlantilla cpNotificacion = cargarPlantilla(335);
					InvestigadorInterno ii = servicioPersona
							.obtenerInvestigadorInterno(new IdPersona(aval.getDocumento(), aval.getTipoDocumento()));
					Correo correoNotificacion = editarCorreo(cpNotificacion, ii, aval);
					String sql = "JOIN i.roles r WHERE r.id = 'AI'";

					for (InvestigadorInterno p : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class,
							sql)) {
						if (tieneRolVigente(p, "AI") && p.getEmail() != null) {
							correoNotificacion.adicionarDireccion(p.getEmail());
						}
					}

					servicioCorreo.enviarCorreo(correoNotificacion);
					correoActual = cargarPlantilla(334);
					Correo correo1 = editarCorreo(correoActual, solicitanteAval, aval);
					servicioCorreo.enviarCorreo(correo1);
				} else {
					correoActual = cargarPlantilla(175);
					Correo correo1 = editarCorreo(correoActual, solicitanteAval, aval);
					servicioCorreo.enviarCorreo(correo1);
				}
				if (aval.getEsAvalParaRevisionDRE() != null && aval.getEsAvalParaRevisionDRE().equals("N")) {
					enviarNotificacionAprobacionFacultad();
				}
			}

			if (this.aval.getAviEstado().equals(Aval.DEVUELTO)) {

				correoActual = cargarPlantilla(203);
				Correo correo = editarCorreo(correoActual, solicitanteAval, aval);
				servicioCorreo.enviarCorreo(correo);

				if (!this.aval.isEsProyectoContrapartida()) {
					InvestigadorInterno docenteAval = servicioPersona
							.obtenerInvestigadorInterno(solicitanteAval.getId());

					String sql = "JOIN i.roles r " + "WHERE r.id = 'AF' " + "AND i.dependencia2.sede.id = '"
							+ docenteAval.getDependencia().getSede().getId() + "' "
							+ "AND i.dependencia2.facultad.id = '" + docenteAval.getDependencia().getFacultad().getId()
							+ "'";

					CorreoPlantilla cpDos = cargarPlantilla(204);
					Correo correoDos = editarCorreo(cpDos, solicitanteAval, aval);

					for (InvestigadorInterno p : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class,
							sql)) {
						if (tieneRolVigente(p, "AF") && p.getEmail() != null) {
							correoDos.adicionarDireccion(p.getEmail());
						}
					}

					servicioCorreo.enviarCorreo(correoDos);

				}
			}
			if (aval.isEsRequisitosRegalias() || aval.isEsConvocatoriaRegalias() || aval.isEsRegalias()
					|| aval.getEsAvalParaRevisionVice().equals("S")) {
				// Correo informativo de decision a la vri
				CorreoPlantilla correovri = cargarPlantilla(224);
				Correo correoInformativoVRI = editarCorreo(correovri, solicitanteAval, aval);
				String sql = "JOIN i.roles r WHERE r.id = 'AV'";

				for (InvestigadorInterno p : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class, sql)) {
				    if (tieneRolVigente(p, "AV") && p.getEmail() != null) {
				        correoInformativoVRI.adicionarDireccion(p.getEmail());
				    }
				}

				servicioCorreo.enviarCorreo(correoInformativoVRI);
			}

			// Actualizar lista de avales
			InvestigadorInterno investigadorInterno = servicioPersona
					.obtenerInvestigadorInterno(investigadorActual.getId());
			listaAval = servicioGeneral.obtenerAvalesDireccion(investigadorInterno.getDependencia2().getId());
			crearHistoricoEstadoAval(aval, cargarPersonaActual(), "DI");
		}
		return anterior();
	}

	private Persona consultarCoordinador() {
		if (!esListaVacia(coordinadores)) {
			for (int i = 0; i < coordinadores.size(); i++) {
				InvestigadorInterno ii = (InvestigadorInterno) coordinadores.get(i);
				if (documentoCoordinador.equals(ii.getId().getDocumento())) {
					return (Persona) coordinadores.get(i);
				}
			}
		}
		return null;
	}

	private void asignarCoordinador() {
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
					if (aval.isEsConvocatoriaRegalias()) {
						servicioProyecto.modificarCoordinadorEnProyecto(ProyectoDAOHibernate.UPDATE, "R",
								proyectoAval.getId().toString(), consultarCoordinador().getId(), cargarPersonaActual(),
								"R", true);
					}
				} else {
					servicioProyecto.modificarCoordinadorEnProyecto(ProyectoDAOHibernate.INSERT, "S",
							proyectoAval.getId().toString(), consultarCoordinador().getId(), cargarPersonaActual(), "S",
							true);
					if (aval.isEsConvocatoriaRegalias()) {
						servicioProyecto.modificarCoordinadorEnProyecto(ProyectoDAOHibernate.INSERT, "R",
								proyectoAval.getId().toString(), consultarCoordinador().getId(), cargarPersonaActual(),
								"R", true);
					}
				}

				// Enviar correo
				correoActual = cargarPlantilla(280);
				Correo correo = new Correo();
				correo.setAsunto(correoActual.getAsunto());
				correo.setCuerpo(correoActual.getCuerpo()
						.replaceAll("<<PERSONA>>", consultarCoordinador().getNombreCompletoMinusculas())
						.replaceAll("<<PROYECTO>>", proyectoAval.getId().toString() + " - " + proyectoAval.getNombre())
						.replaceAll("<<DEPENDENCIA>>", "Sede " + revisor.getDependencia2().getSede().getNombre())
						.replaceAll("<<OBSERVACIONES>>", aval.getAviTextoCompCoor()));
				if (aval.isEsConvocatoriaRegalias()) {
					correo.setCuerpo(correo.getCuerpo().replaceAll("<<ADICIONAL>>",
							"El proyecto está asociado al aval ID " + aval.getAviId() + " de tipo "
									+ aval.getNombreTipo()
									+ " y será encargado de confirmar si el proyecto fue aprobado o no en la convocatoria y que continúe con la solicitud de aval para la etapa verificación de requisitos SGR- Regalías."));
				} else {
					correo.setCuerpo(correo.getCuerpo().replaceAll("<<ADICIONAL>>", ""));
				}
				correo.setOrigen(Correo.CORREO_HERMES);
				correo.adicionarDireccion(consultarCoordinador().getEmail());
				correo.adicionarDireccion(personaActual.getEmail());
				// correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
				servicioCorreo.enviarCorreo(correo);
			}
			servicioGeneral.guardarObjeto(proyectoAval);
		}
	}

	private void aprobarProyectoJornadaDocente() {

		Proyecto proyectoAval = servicioProyecto.obtenerProyecto(aval.getIdProyecto(),
				ProyectoDAOHibernate.DATOS_BASICOS);
		if (proyectoAval.getModalidad().getId().equals(MODALIDAD_FICHA_MINIMA_ID)
				&& proyectoAval.getEstadoProyecto().getId().equals(EstadoProyecto.PROPUESTO)) {
			proyectoAval.cambiarEstadoPersona(EstadoProyecto.APROBADO, cargarPersonaActual(),
					"Aprobación de proyecto para jornada docente");
			proyectoAval.setEsJornadaDocente("Y");
			servicioGeneral.guardarObjeto(proyectoAval);

			// Notificacion labs asociados
			if (!proyectoAval.getLaboratorios().isEmpty()) {
				for (Laboratorio lab : (ArrayList<Laboratorio>) proyectoAval.getListaLaboratorios()) {
					enviarCorreoLaboratoriosProyecto(lab, proyectoAval,
							CorreoPlantilla.CORREO_NOTIFICACION_ASOCIACION_LAB_PROYECTO_APROBADO);
				}
			}
		}
	}

	private void enviarNotificacionAprobacionFacultad() {
		/*
		 * Notificación de revisión de aval a la dependencia donde inicia el trámite.
		 */
		CorreoPlantilla cpNotificacion = cargarPlantilla(286);
		InvestigadorInterno ii = servicioPersona
				.obtenerInvestigadorInterno(new IdPersona(aval.getDocumento(), aval.getTipoDocumento()));
		Correo correoNotificacion = editarCorreo(cpNotificacion, ii, aval);
		/*
		 * Si la revisión inicia en la Facultad del docente se envia el correo de la
		 * plantilla
		 */
		if (categoria.startsWith("FAC-") && !aval.getTipo().equals(Aval.TIPO_INVESTIGACION_CONT)) {
		    String sql = "JOIN i.roles r " +
		                 "WHERE r.id = 'AF' " +
		                 "AND i.dependencia2.facultad.id = '" + aval.getDependencia().getFacultad().getId() + "' " +
		                 "AND i.dependencia2.id <> '0'";

		    for (InvestigadorInterno p : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class, sql)) {
		        if (tieneRolVigente(p, "AF") && p.getEmail() != null) {
		            correoNotificacion.adicionarDireccion(p.getEmail());
		        }
		    }

		    servicioCorreo.enviarCorreo(correoNotificacion);
		}

	}

	public Correo editarCorreo(CorreoPlantilla plantilla, Persona personaAux, Aval nAval) {

		String cuerpo = plantilla.getCuerpo();
		cuerpo = cuerpo.replaceAll("<<INVESTIGADOR>>", personaAux.getNombreCompletoMinusculas());
		cuerpo = cuerpo.replaceAll("<<ID_AVAL>>", nAval.getAviId().toString());
		cuerpo = cuerpo.replaceAll("<<RAZON_RECHAZO>>", eliminarCaracterSinReplace("$", aval.getAviTextoCompCoor()));
		cuerpo = cuerpo.replaceAll("<<TIPOAVAL>>", dominio.getDescripcion());
		cuerpo = cuerpo.replaceAll("<<RAZON>>", eliminarCaracterSinReplace("$", aval.getAviTextoCompCoor()));
		cuerpo = cuerpo.replaceAll("<<RAZON_DEVOLUCION>>", eliminarCaracterSinReplace("$", aval.getAviTextoCompCoor()));
		cuerpo = cuerpo.replaceAll("<<FECHA>>", String.valueOf(this.aval.getAviFechaAvalCoor()));
		if (!esCadenaVacia(aval.getTextoVicerrectoria())) {
			cuerpo = cuerpo.replaceAll("<<OBSERVACIONES>>", this.aval.getTextoVicerrectoria());
		}

		cuerpo = cuerpo.replaceAll("<<SEDE>>", aval.getDependencia().getSede().getNombre());
		cuerpo = cuerpo.replaceAll("<<SEDE_INV>>",
				(((InvestigadorInterno) personaAux).getDependencia().getSede().getNombre()));

		String decision = "";
		if (aval.getAviAvaldireccion() != null && aval.getAviAvaldireccion().equals(Aval.NEGADO)) {
			decision = "NO APROBAR";
		} else if (aval.getAviAvaldireccion() != null && aval.getAviAvaldireccion().equals(Aval.APROBADO)) {
			decision = "APROBAR";
		} else if (aval.getAviEstado() != null && aval.getAviEstado().equals(Aval.DEVUELTO)) {
			decision = "DEVOLVER";
		}
		cuerpo = cuerpo.replaceAll("<<DECISION>>", decision);
		cuerpo = cuerpo.replaceAll("<<PROYECTO>>", nAval.getAviTitulo());
		if (edicionProyecto) {
			cuerpo = cuerpo.replaceAll("<<PROYECTO>>",
					"El proyecto asociado al aval ha sido habilitado para edición con el fin de que aplique los cambios indicados, recuerde que debe enviarlo de nuevo para su revisión");
		}

		Correo correo = new Correo();
		correo.setOrigen(Correo.CORREO_HERMES);
		/*
		 * Correos para la facultad por devolución o para la vicerrectoría de
		 * investigación si debe continuar
		 */
		if (!plantilla.getId().equals(204L) && !plantilla.getId().equals(142L) && !plantilla.getId().equals(286L)) {
			correo.adicionarDireccion(personaAux.getEmail());
		}

		// correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
		correo.adicionarCopiaOculta(personaActual.getEmail());
		correo.setAsunto(plantilla.getAsunto());
		correo.setCuerpo(cuerpo);
		return correo;
	}

	private void verificarProyectoContrapartida() {
		if (aval.isEsProyectoContrapartida()) {
			Proyecto proyectoAval = servicioProyecto.obtenerProyecto(aval.getIdProyecto(),
					ProyectoDAOHibernate.DATOS_BASICOS);
			proyectoAval.setEsPryContrapartida("Y");
			servicioGeneral.guardarObjeto(proyectoAval);
		}
	}

	public String anterior() {
		sesion.removeAttribute("manejadorSolicitarAvalDireccion");
		return "avalarDireccion";
	}

	public int getTamañoLista() {
		if (!esListaVacia(listaAval)) {
			return listaAval.size();
		}
		return 0;
	}

	public boolean isVerRevision() {
		return verRevision;
	}

	public void setVerRevision(boolean verRevision) {
		this.verRevision = verRevision;
	}

	public boolean isVerEvaluacion() {
		return verEvaluacion;
	}

	public void setVerEvaluacion(boolean verEvaluacion) {
		this.verEvaluacion = verEvaluacion;
	}

	public String getNombreDecano() {
		return nombreDecano;
	}

	public void setNombreDecano(String nombreDecano) {
		this.nombreDecano = nombreDecano;
	}

	public boolean isContratacionDecanos() {
		return contratacionDecanos;
	}

	public void setContratacionDecanos(boolean contratacionDecanos) {
		this.contratacionDecanos = contratacionDecanos;
	}

	public String getValorLetras() {
		return valorLetras;
	}

	public void setValorLetras(String valorLetras) {
		this.valorLetras = valorLetras;
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

}
