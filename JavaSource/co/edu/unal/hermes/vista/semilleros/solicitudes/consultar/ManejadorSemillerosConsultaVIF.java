package co.edu.unal.hermes.vista.semilleros.solicitudes.consultar;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.Semillero;
import co.edu.unal.hermes.modelo.SemilleroSolicitud;

public class ManejadorSemillerosConsultaVIF extends ManejadorSemillerosConsultaBase {

	private static final long serialVersionUID = 1L;

	public ManejadorSemillerosConsultaVIF() {
		super();
		init();
	}

	private void init() {
		semilleros = servicioGeneral.obtenerObjetos(Semillero.class, "select s from Semillero s, SemilleroIntegrante si, InvestigadorInterno ii where si.semillero.id = s.id and si.tipo.id = 'DD' "
				+ "and ii.id.documento = si.integrante.id.documento and ii.dependencia.facultad.id = '"+investigadorInterno.getDependencia2().getFacultad().getId()+"'" );
		setListaSolicitudes(new ArrayList<SemilleroSolicitud>());
		for (Semillero s : semilleros) {

			if (s.getLider() != null && s.getLider().getDependencia() != null
					&& investigadorInterno.getDependencia2() != null) {

				if (Sede.SEDES_ANDINAS.contains(s.getLider().getDependencia().getSede().getId().toString())
						&& investigadorInterno.getDependencia2().getFacultad().getId()
								.equals(s.getLider().getDependencia().getFacultad().getId())) {
					for (SemilleroSolicitud ssol : s.getSolicitudes()) {
						if (ssol.getFechaRespuestaVifDi() != null) {
							getListaSolicitudes().add(ssol);
						}
					}
				}
			}
		}
	}

	public List<SemilleroSolicitud> getListaSolicitudes() {
		return listaSolicitudes;
	}

	public void setListaSolicitudes(List<SemilleroSolicitud> listaSolicitudes) {
		this.listaSolicitudes = listaSolicitudes;
	}

	public SemilleroSolicitud getSolicitudActual() {
		return solicitudActual;
	}

	public void setSolicitudActual(SemilleroSolicitud solicitudActual) {
		this.solicitudActual = solicitudActual;
	}

	public ArrayList<SelectItem> getRespuestasSolicitud() {
		return respuestasSolicitud;
	}

	public void setRespuestasSolicitud(ArrayList<SelectItem> respuestasSolicitud) {
		this.respuestasSolicitud = respuestasSolicitud;
	}
}
