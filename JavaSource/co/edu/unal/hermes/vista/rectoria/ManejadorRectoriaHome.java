package co.edu.unal.hermes.vista.rectoria;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.hibernate.HibernateException;

import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.vista.aval.base.BaseManejadorSolicitarAvalDireccion;

public class ManejadorRectoriaHome extends BaseManejadorSolicitarAvalDireccion {

	private static final long serialVersionUID = 1L;
	
	public ManejadorRectoriaHome() {
		super();
		List<Aval> listaAvalAux = new ArrayList<Aval>();
		listaAvalAux = servicioGeneral.obtenerAvalesRectoria();
		organizarListaRevisionAval(listaAvalAux);
		categoriaItems = new SelectItem[2];
		categoriaItems[0] = new SelectItem(new Integer(1), "Aprobado");
		categoriaItems[1] = new SelectItem(new Integer(2), "Devolver para correcciones");
	}

	public int getTamañoLista() {
		if (listaAval != null) {
			return listaAval.size();
		}
		return 0;
	}

	public String editarAval() {
		consultarAvalTramitar();
		return "revisarAvalRectoria";
	}

	public String guardarRevision() {
		if (aval == null) {
			return "";
		} else if (selItem == 0) {
			mensajeError("Por favor, indique el concepto de la Rectoría para este aval.");
			return "";
		}
		personaActual = (Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL);
		boolean temp = false;
		aval.setAvalRectoria("");
		if (this.selItem == 1) {
			this.aval.setAviEstado(Aval.REVISADO_RECTORIA);
			aval.setAvalRectoria(Aval.APROBADO);
			aval.setFechaAvalRectoria(getToday());
			temp = true;
			if (aval.getArchivoAval() != null && aval.getArchivoAval().length() <= 0) {
				mensajeError("Si la decisión es aprobar por favor adjunte el documento del aval.");
				return "";
			}
		} else if (this.selItem == 2) {
			if (esCadenaVacia(aval.getTextoRectoria())) {
				mensajeError("Debe ingresar los comentarios de la DEVOLUCIÓN del aval");
				return "";
			}
			if (aval.getEsAvalParaRevisionVice().equals("S")) {
				aval.setAviEstado(Aval.REVISADO_VICERRECTORIA);

			} else {
				aval.setAviEstado(Aval.REVISADO_DIRECCION);
			}
			aval.setAvalDRE(null);
			aval.setFechaAvalDRE(null);
			temp = true;
		}
		if (temp) {
			Persona personaAux = servicioPersona
					.obtenerPersona(new IdPersona(aval.getDocumento(), aval.getTipoDocumento()));
			aval.setFechaAvalRectoria(new Date());
			this.servicioGeneral.guardarObjeto(this.aval);
			if (aval.getAvalRectoria() != null && aval.getAvalRectoria().equals(Aval.APROBADO)) {
				correoActual = cargarPlantilla(366);
				Correo correo = editarCorreo(personaAux, aval);
				InvestigadorInterno docenteAval = servicioPersona.obtenerInvestigadorInterno(personaAux.getId());
				String sql = " JOIN i.roles r WHERE r.id = 'AD' AND i.dependencia.sede.id = '"
						+ docenteAval.getDependencia().getSede().getId() + "'";
				for (InvestigadorInterno p : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class, sql)) {
					if (p.getEmail() != null) {
						correo.adicionarDireccion(p.getEmail());
					}
				}
				sql = " JOIN i.roles r WHERE r.id = 'AI'";
				for (InvestigadorInterno p : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class, sql)) {
					if (p.getEmail() != null) {
						correo.adicionarDireccion(p.getEmail());
					}
				}
				correo.adicionarDireccion(docenteAval.getEmail());
				try {
					if(servicioGeneral.esAmbienteProduccion()) {
						correo.adicionarDireccion("rectoriaun@unal.edu.co");
						correo.adicionarDireccion("dre_nal@unal.edu.co");
					}
				} catch (HibernateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				servicioCorreo.enviarCorreo(correo);
			} else if (this.selItem == 2) {
				correoActual = cargarPlantilla(365);
				Correo correo = editarCorreo(personaAux, aval);
				String sql = " JOIN i.roles r WHERE r.id = 'AI'";
				for (InvestigadorInterno p : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class, sql)) {
					if (p.getEmail() != null) {
						correo.adicionarDireccion(p.getEmail());
					}
				}
				servicioCorreo.enviarCorreo(correo);
			}
			crearHistoricoEstadoAval(aval, personaActual, "REC");
			listaAval = servicioGeneral.obtenerAvalesDRE();
			mensajeInfo("Guardado con éxito");
			sesion.removeAttribute("manejadorRectoriaHome");
		}

		return "rectoria";
	}

	public String anterior() {
		sesion.removeAttribute("manejadorRectoriaHome");
		return "rectoria";
	}

	public Correo editarCorreo(Persona personaAux, Aval nAval) {
		Correo correoElectronico = new Correo();
		try {
			String correo = correoActual.getCuerpo();
			correo = correo.replaceAll("<<INVESTIGADOR>>", personaAux.getNombreCompletoMinusculas());
			correo = correo.replaceAll("<<ID_AVAL>>", nAval.getAviId().toString());
			correo = correo.replaceAll("<<OBSERVACIONES>>", eliminarCaracterSinReplace("$", aval.getTextoRectoria()));
			correoElectronico.setOrigen(Correo.CORREO_HERMES);
			//correoElectronico.adicionarCopiaOculta(Correo.CORREO_HERMES);
			correoElectronico.adicionarCopiaOculta(personaActual.getEmail());
			correoElectronico.setAsunto(correoActual.getAsunto());
			correoElectronico.setCuerpo(correo);
		} catch (Exception e) {
			return correoElectronico;
		}
		return correoElectronico;
	}
}
