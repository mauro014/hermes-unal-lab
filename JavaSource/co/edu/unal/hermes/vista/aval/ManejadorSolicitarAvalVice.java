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
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.Reporte;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.aval.base.BaseManejadorSolicitarAvalDireccion;

public class ManejadorSolicitarAvalVice extends BaseManejadorSolicitarAvalDireccion {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ManejadorSolicitarAvalVice() {
		List<Aval> listaAvalAux = new ArrayList<Aval>();
		listaAvalAux = servicioGeneral.obtenerAvalesVice();
		organizarListaRevisionAval(listaAvalAux);
	}

	public String editarAval() {
		consultarAvalTramitar();
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
		return "consultaAvalGenerarVice";
	}

	public void reporteAval() throws SQLException {
		if (esCadenaVacia(avalId)) {
			mensajeError("Debe seleccionar una plantilla de carta para generarla.");
			return;
		}

		ReporteBirt r = new ReporteBirt();
		personaActual = (Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL);
		InvestigadorInterno invInterno = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());

		servicioGeneral.ejecutarSentencia("UPDATE HER_AVAL SET AVI_TEXTO_COMP_COOR = '" + aval.getAviTextoCompCoor()
				+ "' WHERE AVI_ID = '" + aval.getAviId().toString() + "'");
		String duracionMeses = "---";
		String valorContrapartidaLetras = "";
		if (aval.isEsRegalias()) {
			valorContrapartidaLetras = convertir(
					String.valueOf(aval.getAviEspecieunal() + aval.getValorPersonalTotal()), false);

			Proyecto proyecto = servicioProyecto.obtenerProyecto(aval.getIdProyecto(),
					ProyectoDAOHibernate.DATOS_BASICOS);
			duracionMeses = convertirDuracionMeses(proyecto.getDuracionTipo(), proyecto.getDuracion());
		}

		Calendar calendar = Calendar.getInstance();
		r.adicionarParametro("valorletras", valorContrapartidaLetras);
		r.adicionarParametro("mes", getNombreMes(calendar.get(Calendar.MONTH)));
		r.adicionarParametro("valorNumero", String.valueOf(aval.getAviEspecieunal() + (aval.getValorPersonalTotal())));
		r.adicionarParametro("Id", aval.getAviId().toString());
		r.adicionarParametro("Decano", "NO");
		r.adicionarParametro("Ciu", "Bogotá D.C.");
		r.adicionarParametro("Sed", invInterno.getDependencia().getSede().getNombre());
		r.adicionarParametro("meses", duracionMeses);
		r.adicionarParametro("d", "1");
		r.setNombreReporte(obtenerNombreCarta(avalId));
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
	}

	private void verificarCambioEstadoProyecto() {
		if (edicionProyecto) {
			Proyecto proyectoAval = servicioProyecto.obtenerProyecto(aval.getIdProyecto(),
					ProyectoDAOHibernate.DATOS_BASICOS);
			if (proyectoAval.getModalidad().getId().equals(MODALIDAD_FICHA_MINIMA_ID)
					&& proyectoAval.getEstadoProyecto().getId().equals(EstadoProyecto.PROPUESTO)) {
				proyectoAval.cambiarEstadoPersona(EstadoProyecto.INGRESANDO, cargarPersonaActual(),
						"Devolución aval desde la Vicerrectoría de Investigación.");
				servicioGeneral.guardarObjeto(proyectoAval);
			} else {
				edicionProyecto = false;
			}
		}
	}

	public void cambiarEstadoProyecto() {
		Long idProyecto = aval.getIdProyecto();
		Proyecto p = servicioProyecto.obtenerProyecto(idProyecto, ProyectoDAOHibernate.DATOS_BASICOS);
		p.cambiarEstadoPersona(EstadoProyecto.AVAL_APROBADO, personaActual, "Aval aprobado con este proyecto");
		servicioGeneral.guardarObjeto(p);
	}

	public String guardarRevision() {

		if (aval == null) {
			return "";
		}
		personaActual = (Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL);
		boolean temp = false;
		aval.setAvalVice("");
		if (this.selItem == 2) {
			this.aval.setAviEstado(Aval.REVISADO_VICERRECTORIA);
			aval.setAvalVice(Aval.APROBADO);
			temp = true;
			if (aval.isEsRegalias() && (aval.getArchivoAval() == null || aval.getArchivoAval().length() <= 0)) {
				mensajeError("Si la decisión es aprobar por favor adjunte el documento del aval");
				return "";
			}
		}
		if (this.selItem == 3) {
			if (esCadenaVacia(aval.getTextoVicerrectoria())) {
				mensajeError("Debe ingresar los comentarios de la NO APROBACIÓN del aval");
				return "";
			}
			this.aval.setAviEstado(Aval.REVISADO_VICERRECTORIA);
			aval.setAvalVice(Aval.NEGADO);
			temp = true;
		}

		if (this.selItem == 4) {
			if (esCadenaVacia(aval.getTextoVicerrectoria())) {
				mensajeError("Debe ingresar los comentarios de la DEVOLUCIÓN del aval");
				return "";
			}
			aval.setAviEstado(Aval.DEVUELTO);
			aval.setAviAvalfacultad(null);
			aval.setAviAvaldireccion(null);
			aval.setAviFechaAvalFac(null);
			aval.setAviFechaAvalCoor(null);
			verificarCambioEstadoProyecto();
			temp = true;
		}

		if (temp) {
			Persona personaAux = servicioPersona
					.obtenerPersona(new IdPersona(aval.getDocumento(), aval.getTipoDocumento()));
			aval.setAviFechaAvalVice(new Date());

			this.servicioGeneral.guardarObjeto(this.aval);
			if (this.aval.getAvalVice().equals(Aval.APROBADO) && aval.getIdProyecto() != null
					&& !aval.getIdProyecto().equals(0L)) {
				cambiarEstadoProyecto();
			}
			for (ArchivoAval aa : aval.getArchivosCoorList()) {
				aa.setAvalCoor(aval.getAviId());
				servicioGeneral.guardarObjeto(aa);
			}

			if (aval.getAvalVice() != null && aval.getAvalVice().equals(Aval.NEGADO)) {
				correoActual = cargarPlantilla(144);
				Correo correo = editarCorreo(personaAux, aval);
				servicioCorreo.enviarCorreo(correo);
			}

			if (aval.getAvalVice() != null && aval.getAvalVice().equals(Aval.APROBADO)
					&& aval.getEsAvalParaRevisionDRE() != null && aval.getEsAvalParaRevisionDRE().equals("N")) {
				if (aval.isEsGrupoInvestigacion() || aval.isEsInvestigadorIndependiente()) {
					correoActual = cargarPlantilla(259);
					Correo correo1 = editarCorreo(personaAux, aval);
					String asunto1 = correoActual.getAsunto();
					asunto1 = asunto1.replaceAll("<<TIPO>>", aval.getNombreTipo());
					correo1.setAsunto(asunto1);
					servicioCorreo.enviarCorreo(correo1);
				} else {
					correoActual = cargarPlantilla(152);
					Correo correo = editarCorreo(personaAux, aval);
					servicioCorreo.enviarCorreo(correo);

					// TODO Verificar si es Regalias o paed y enviar copia a las
					// sedes involucradas
				}
			} else if (aval.getEsAvalParaRevisionDRE() != null && aval.getEsAvalParaRevisionDRE().equals("S")) {
				correoActual = cargarPlantilla(339);
				Correo correoNotificacion = editarCorreo(personaAux, aval);
				String sql = "JOIN i.roles r WHERE r.id = 'AI'";

				for (InvestigadorInterno p : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class, sql)) {
					if (tieneRolVigente(p, "AI") && p.getEmail() != null) {
						correoNotificacion.adicionarDireccion(p.getEmail());
					}
				}

				servicioCorreo.enviarCorreo(correoNotificacion);
				correoActual = cargarPlantilla(340);
				Correo correo1 = editarCorreo(personaAux, aval);
				servicioCorreo.enviarCorreo(correo1);
			}

			if (this.aval.getAviEstado().equals(Aval.DEVUELTO)) {

				correoActual = cargarPlantilla(205);
				Correo correo = editarCorreo(personaAux, aval);
				servicioCorreo.enviarCorreo(correo);
				InvestigadorInterno docenteAval = servicioPersona.obtenerInvestigadorInterno(personaAux.getId());

				CorreoPlantilla cpDos = cargarPlantilla(206);
				Correo correoDos = new Correo();
				correoDos.setOrigen(Correo.CORREO_HERMES_SOLICITUDES);
				correoDos.setAsunto(cpDos.getAsunto());
				correoDos.setCuerpo(cpDos.getCuerpo().replaceAll("<<RAZON_DEVOLUCION>>", aval.getTextoVicerrectoria())
						.replaceAll("<<INVESTIGADOR>>", docenteAval.getNombreCompletoMinusculas())
						.replaceAll("<<TIPO_AVAL>>", aval.getNombreTipo())
						.replaceAll("<<ID_AVAL>>", aval.getAviId().toString()));
				// correoDos.adicionarCopiaOculta(Correo.CORREO_HERMES_SOLICITUDES);

				String sql = "JOIN i.roles r " + "WHERE r.id = 'AD' " + "AND i.dependencia.sede.id = '"
						+ docenteAval.getDependencia().getSede().getId() + "'";

				for (InvestigadorInterno inv : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class,
						sql)) {
					if (tieneRolVigente(inv, "AD") && inv.getEmail() != null) {
						correoDos.adicionarDireccion(inv.getEmail());
					}
				}

				List listaPersonasVIF = servicioPersona.obtenerPersonasxRolIdxDpnId("AF",
						docenteAval.getDependencia().getFacultad().getId());

				for (Iterator iterator = listaPersonasVIF.iterator(); iterator.hasNext();) {
					Persona p = (Persona) iterator.next();
					correo.adicionarDireccion(p.getEmail());
				}

				servicioCorreo.enviarCorreo(correoDos);
			}

			crearHistoricoEstadoAval(aval, personaActual, "VI");
			listaAval = servicioGeneral.obtenerAvalesVice();
			mensajeInfo("Guardado con éxito");
			sesion.removeAttribute("manejadorSolicitarAvalVice");
		}
		return "avalarVice";
	}

	public Correo editarCorreo(Persona personaAux, Aval nAval) {

		Correo correoElectronico = new Correo();

		try {
			String correo = correoActual.getCuerpo();
			correo = correo.replaceAll("<<INVESTIGADOR>>", personaAux.getNombreCompletoMinusculas());
			correo = correo.replaceAll("<<TIPO>>", nAval.getNombreTipo());
			correo = correo.replaceAll("<<ID_AVAL>>", nAval.getAviId().toString());
			correo = correo.replaceAll("<<SEDE>>", aval.getDependencia().getSede().getNombre());
			correo = correo.replaceAll("<<SEDE_INV>>",
					(((InvestigadorInterno) personaAux).getDependencia().getSede().getNombre()));
			correo = correo.replaceAll("<<TIPOAVAL>>", nAval.getNombreTipo());
			if (nAval.isEsGrupoInvestigacion()) {
				correo = correo.replaceAll("<<COMPLEMENTO>>",
						"De esta manera, se habilitó en el InstituLAC de la plataforma ScienTI de Minciencias.");
			} else if (nAval.isEsInvestigadorIndependiente()) {
				correo = correo.replaceAll("<<COMPLEMENTO>>",
						"Para el caso de investigador independiente se habilitó en el InstituLAC de la plataforma ScienTI de Colciencias y en becas doctorales se ha aprobado y adjuntado el aval por parte de cada una de las sedes, usted puede descargar el aval, dando clic en avales, en el aval y opción descargar.");
			}

			if (edicionProyecto) {
				correo = correo.replaceAll("<<PROYECTO>>",
						"El proyecto asociado al aval ha sido habilitado para edición con el fin de que aplique los cambios indicados, recuerde que debe enviarlo de nuevo para su revisión");
			} else {
				correo = correo.replaceAll("<<PROYECTO>>", nAval.getAviTitulo());
			}

			if (!esCadenaVacia(aval.getTextoVicerrectoria())) {
				correo = correo.replaceAll("<<RAZON_RECHAZO>>",
						eliminarCaracterSinReplace("$", aval.getTextoVicerrectoria()));
			} else {
				aval.setTextoVicerrectoria(
						"Comunicarse con el personal de regalías de la Vicerrectoría de Investigación para conocer los detalles del rechazo");
				correo = correo.replaceAll("<<RAZON_RECHAZO>>",
						eliminarCaracterSinReplace("$", aval.getTextoVicerrectoria()));
			}

			correoElectronico.setOrigen(Correo.CORREO_HERMES);
			// correoElectronico.adicionarCopiaOculta(Correo.CORREO_HERMES);
			correoElectronico.adicionarCopiaOculta(personaActual.getEmail());
			correoElectronico.adicionarDireccion(personaAux.getEmail());
			correoElectronico.setAsunto(correoActual.getAsunto());
			correoElectronico.setCuerpo(correo);

		} catch (Exception e) {
			return correoElectronico;
		}
		return correoElectronico;
	}

	public String anterior() {
		sesion.removeAttribute("manejadorSolicitarAvalVice");
		return "avalarVice";
	}

	public int getTamañoLista() {
		if (listaAval != null) {
			return listaAval.size();
		}
		return 0;
	}

}
