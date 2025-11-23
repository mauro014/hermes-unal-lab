package co.edu.unal.hermes.vista.aval;

import java.util.Date;
import java.util.List;

import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.vista.aval.base.BaseManejadorSolicitarAvalDireccion;

public class ManejadorSolicitarAvalCEPI extends BaseManejadorSolicitarAvalDireccion {

	private static final long serialVersionUID = 1L;
	private String rolRevisionAvales = null;

	public ManejadorSolicitarAvalCEPI() {
		InvestigadorInterno invInterno = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());
		List<Aval> listaAvalAux = servicioGeneral.obtenerAvalesCEPI(invInterno.getDependencia2().getId());
		organizarListaRevisionAval(listaAvalAux);
	}

	public String editarAval() {
		consultarAvalTramitar();
		return "consultaAvalGenerarCEPI";
	}

	public String guardarRevision() {
		if (aval == null) {
			return "";
		}
		personaActual = (Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL);
		boolean temp = false;
		aval.setAvalCEPI("");

		if (esNulo(selItem) || selItem == 0) {
			mensajeError("Debe seleccionar si aprueba, no aprueba o devuelve para correcciones el aval");
			return "";
		}

		if (this.selItem == 2) {
			this.aval.setAviEstado(Aval.ETICO_APROBADO_CEPI);
			aval.setAvalCEPI(Aval.APROBADO);
			temp = true;
			if (aval.getArchivoAval() == null || aval.getArchivoAval().length() <= 0) {
				mensajeError("Si la decisión es aprobar por favor adjunte el documento del aval");
				return "";
			}
		} else if (this.selItem == 3) {
			if (esCadenaVacia(aval.getTextoCEPI())) {
				mensajeError("Debe ingresar los comentarios de la NO APROBACIÓN del aval");
				return "";
			}
			this.aval.setAviEstado(Aval.ETICO_NO_APROBADO_CEPI);
			aval.setAvalCEPI(Aval.NEGADO);
			temp = true;
		} else if (this.selItem == 4) {
			if (esCadenaVacia(aval.getTextoCEPI())) {
				mensajeError("Debe ingresar los comentarios de la DEVOLUCIÓN del aval");
				return "";
			}
			aval.setAviEstado(Aval.ETICO_DEVUELTO_CEPI);
			aval.setAvalCEPI(Aval.NEGADO);
			temp = true;
		}

		if (temp) {
			Persona personaAux = servicioPersona
					.obtenerPersona(new IdPersona(aval.getDocumento(), aval.getTipoDocumento()));
			aval.setFechaAvalCEPI(new Date());
			this.servicioGeneral.guardarObjeto(this.aval);

			if (!esNulo(aval.getAvalCEPI()) && aval.getAviEstado().equals(Aval.ETICO_APROBADO_CEPI)) {
				correoActual = cargarPlantilla(370);
				Correo correo = editarCorreo(personaAux, aval);
				servicioCorreo.enviarCorreo(correo);
			} else if (!esNulo(aval.getAvalCEPI()) && aval.getAviEstado().equals(Aval.ETICO_NO_APROBADO_CEPI)) {
				correoActual = cargarPlantilla(369);
				Correo correo = editarCorreo(personaAux, aval);
				servicioCorreo.enviarCorreo(correo);
			} else if (!esNulo(aval.getAvalCEPI()) && aval.getAviEstado().equals(Aval.ETICO_DEVUELTO_CEPI)) {
				correoActual = cargarPlantilla(368);
				Correo correo = editarCorreo(personaAux, aval);
				servicioCorreo.enviarCorreo(correo);
			}
			crearHistoricoEstadoAval(aval, personaActual, "CEPI");
			listaAval = servicioGeneral.obtenerAvalesCEPI(aval.getCepi().getDependencia().getId());
			mensajeInfo("Guardado con éxito");
			sesion.removeAttribute("manejadorSolicitarAvalCEPI");
		}
		return "avalarCEPI";
	}

	public String guardarRevisionRecurso() {
		if (aval == null) {
			return "";
		}
		personaActual = (Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL);
		boolean temp = false;
		aval.setAvalCEPIRecurso("");

		if (aval.getTipoRecursoAvalEtico().getId().equals(Tipos.TIPOS_TIPO_RECURSO_AVAL_ETICO_Reposicion)) {
			if (esNulo(selItem) || selItem == 0) {
				mensajeError("Debe seleccionar si aprueba o no el aval luego de la revisión del recurso");
				return "";
			}
			if (this.selItem == 2) {
				this.aval.setAviEstado(Aval.ETICO_APROBADO_CEPI);
				aval.setAvalCEPIRecurso(Aval.APROBADO);
				temp = true;
				if (aval.getArchivoAval() == null || aval.getArchivoAval().length() <= 0) {
					mensajeError("Si la decisión es aprobar por favor adjunte el documento del aval");
					return "";
				}
			} else if (this.selItem == 3) {
				if (esCadenaVacia(aval.getTextoCEPI())) {
					mensajeError(
							"Debe ingresar los comentarios de la NO APROBACIÓN del aval luego de la revisión del recurso");
					return "";
				}
				this.aval.setAviEstado(Aval.ETICO_NO_APROBADO_CEPI);
				aval.setAvalCEPIRecurso(Aval.NEGADO);
				temp = true;
			}
		} else if (aval.getTipoRecursoAvalEtico().getId()
				.equals(Tipos.TIPOS_TIPO_RECURSO_AVAL_ETICO_Reposición_Apelación)) {
			if (esNulo(selItem) || selItem == 0) {
				mensajeError("Debe seleccionar si aprueba o no el aval luego de la revisión del recurso");
				return "";
			}
			if (this.selItem == 2) {
				this.aval.setAviEstado(Aval.ETICO_APROBADO_CEPI);
				aval.setAvalCEPIRecurso(Aval.APROBADO);
				temp = true;
				if (aval.getArchivoAval() == null || aval.getArchivoAval().length() <= 0) {
					mensajeError("Si la decisión es aprobar por favor adjunte el documento del aval");
					return "";
				}
			} else if (this.selItem == 3) {
				if (esCadenaVacia(aval.getTextoCEPI())) {
					mensajeError(
							"Debe ingresar los comentarios de la NO APROBACIÓN del aval luego de la revisión del recurso");
					return "";
				}
				this.aval.setAviEstado(Aval.ETICO_ENVIADO_CESI);
				aval.setAvalCEPIRecurso(Aval.NEGADO);
				temp = true;
			}
		} else if (aval.getTipoRecursoAvalEtico().getId().equals(Tipos.TIPOS_TIPO_RECURSO_AVAL_ETICO_Apelacion)) {

			if (esNulo(selItem) || selItem == 0) {
				mensajeError("Debe seleccionar si el recurso de apelación es procedente o no");
				return "";
			}

			if (this.selItem == 2) {
				this.aval.setAviEstado(Aval.ETICO_ENVIADO_CESI);
				aval.setAvalCEPIRecurso(Aval.APROBADO);
				temp = true;
			} else if (this.selItem == 3) {
				aval.setAvalCEPIRecurso(Aval.NEGADO);
				temp = true;
			}
		}

		if (temp) {
			Persona personaAux = servicioPersona
					.obtenerPersona(new IdPersona(aval.getDocumento(), aval.getTipoDocumento()));
			aval.setFechaAvalCEPIRecurso(new Date());
			this.servicioGeneral.guardarObjeto(this.aval);

			int tipoRecursoId = !esNulo(aval.getTipoRecursoAvalEtico())
					? aval.getTipoRecursoAvalEtico().getId().intValue()
					: 0;

			switch (tipoRecursoId) {
			// Reposición
			case 7685:
				if (!esNulo(aval.getAvalCEPIRecurso()) && aval.getAviEstado().equals(Aval.ETICO_APROBADO_CEPI)) {
					correoActual = cargarPlantilla(372);
					Correo correo = editarCorreo(personaAux, aval);
					servicioCorreo.enviarCorreo(correo);
				} else if (!esNulo(aval.getAvalCEPIRecurso())
						&& aval.getAviEstado().equals(Aval.ETICO_NO_APROBADO_CEPI)) {
					correoActual = cargarPlantilla(373);
					Correo correo = editarCorreo(personaAux, aval);
					servicioCorreo.enviarCorreo(correo);
				}
				break;
			// Reposición con apelación
			case 7686:
				if (!esNulo(aval.getAvalCEPIRecurso()) && aval.getAviEstado().equals(Aval.ETICO_APROBADO_CEPI)) {
					correoActual = cargarPlantilla(374);
					Correo correo = editarCorreo(personaAux, aval);
					servicioCorreo.enviarCorreo(correo);
				} else if (!esNulo(aval.getAvalCEPIRecurso()) && aval.getAviEstado().equals(Aval.ETICO_ENVIADO_CESI)) {
					correoActual = cargarPlantilla(375);
					Correo correo = editarCorreo(personaAux, aval);
					servicioCorreo.enviarCorreo(correo);
					// Correo CESI
					correoActual = cargarPlantilla(376);
					Correo correoCESI = editarCorreoCESI(personaAux, aval);
					servicioCorreo.enviarCorreo(correoCESI);
				}
				break;
			// Apelación
			case 7687:
				if (!esNulo(aval.getAvalCEPIRecurso()) && aval.getAvalCEPIRecurso().equals(Aval.APROBADO)) {
					correoActual = cargarPlantilla(377);
					Correo correo = editarCorreo(personaAux, aval);
					servicioCorreo.enviarCorreo(correo);
					// Correo CESI
					correoActual = cargarPlantilla(378);
					Correo correoCESI = editarCorreoCESI(personaAux, aval);
					servicioCorreo.enviarCorreo(correoCESI);
				} else if (!esNulo(aval.getAvalCEPIRecurso()) && aval.getAvalCEPIRecurso().equals(Aval.NEGADO)) {
					correoActual = cargarPlantilla(379);
					Correo correo = editarCorreo(personaAux, aval);
					servicioCorreo.enviarCorreo(correo);

				}
				break;
			// Default
			default:
				break;
			}
			crearHistoricoEstadoAval(aval, personaActual, "CEPI-Recurso");
			listaAval = servicioGeneral.obtenerAvalesCEPI(aval.getCepi().getDependencia().getId());
			mensajeInfo("Guardado con éxito");
			sesion.removeAttribute("manejadorSolicitarAvalCEPI");
		}
		return "avalarCEPI";
	}

	public Correo editarCorreo(Persona personaAux, Aval nAval) {

		Correo correoElectronico = new Correo();

		try {
			String correo = correoActual.getCuerpo();
			correo = correo.replaceAll("<<INVESTIGADOR>>", personaAux.getNombreCompletoMinusculas());
			correo = correo.replaceAll("<<ID_AVAL>>", nAval.getAviId().toString());
			correo = correo.replaceAll("<<OBSERVACIONES>>", eliminarCaracterSinReplace("$", aval.getTextoCEPI()));
			correo = correo.replaceAll("<<NOMBRE_DEPENDENCIA>>",
					!esNulo(nAval.getCepi()) ? nAval.getCepi().getDependencia().getNombre() : "ERROR DEPENDENCIA CEPI");
			correo = correo.replaceAll("<<NOMBRE_CEPI>>",
					!esNulo(nAval.getCepi()) ? nAval.getCepi().getNombre() : "ERROR NOMBRE CEPI");

			correoElectronico.setOrigen(Correo.CORREO_HERMES);
			// correoElectronico.adicionarCopiaOculta(Correo.CORREO_HERMES_SOLICITUDES);
			correoElectronico.adicionarDireccion(personaAux.getEmail());

			String asunto = correoActual.getAsunto();
			asunto = asunto.replaceAll("<<ID_AVAL>>", nAval.getAviId().toString());

			correoElectronico.setAsunto(asunto);
			correoElectronico.setCuerpo(correo);

		} catch (Exception e) {
			return correoElectronico;
		}
		return correoElectronico;
	}

	public Correo editarCorreoCESI(Persona personaAux, Aval nAval) {

		Correo correoElectronico = new Correo();

		try {
			String correo = correoActual.getCuerpo();
			correo = correo.replaceAll("<<INVESTIGADOR>>", personaAux.getNombreCompletoMinusculas());
			correo = correo.replaceAll("<<ID_AVAL>>", nAval.getAviId().toString());
			correo = correo.replaceAll("<<OBSERVACIONES>>", eliminarCaracterSinReplace("$", nAval.getTextoCEPI()));
			correo = correo.replaceAll("<<NOMBRE_DEPENDENCIA>>",
					!esNulo(nAval.getCepi()) ? nAval.getCepi().getDependencia().getNombre() : "ERROR DEPENDENCIA CEPI");
			correo = correo.replaceAll("<<NOMBRE_CEPI>>",
					!esNulo(nAval.getCepi()) ? nAval.getCepi().getNombre() : "ERROR NOMBRE CEPI");

			correoElectronico.setOrigen(Correo.CORREO_HERMES);

			String sql = "JOIN i.roles r " + "WHERE r.id = 'CESI' " + "AND i.dependencia2.id = '"
					+ nAval.getCepi().getCesi().getDependencia().getId().toString() + "'";

			for (InvestigadorInterno inv : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class, sql)) {
				if (tieneRolVigente(inv, "CESI") && !esNulo(inv.getEmail())) {
					correoElectronico.adicionarDireccion(inv.getEmail());
				}
			}

			String asunto = correoActual.getAsunto();
			asunto = asunto.replaceAll("<<ID_AVAL>>", nAval.getAviId().toString());

			correoElectronico.setAsunto(asunto);
			correoElectronico.setCuerpo(correo);

		} catch (Exception e) {
			return correoElectronico;
		}
		return correoElectronico;
	}

	public String anterior() {
		sesion.removeAttribute("manejadorSolicitarAvalCEPI");
		return "avalarCEPI";
	}

	public int getTamañoLista() {
		if (listaAval != null) {
			return listaAval.size();
		}
		return 0;
	}

	public String getRolRevisionAvales() {
		return rolRevisionAvales;
	}

	public void setRolRevisionAvales(String rolRevisionAvales) {
		this.rolRevisionAvales = rolRevisionAvales;
	}
}
