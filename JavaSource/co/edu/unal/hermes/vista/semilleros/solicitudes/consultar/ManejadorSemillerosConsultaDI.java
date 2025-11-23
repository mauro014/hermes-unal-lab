package co.edu.unal.hermes.vista.semilleros.solicitudes.consultar;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.Semillero;
import co.edu.unal.hermes.modelo.SemilleroSolicitud;

public class ManejadorSemillerosConsultaDI extends ManejadorSemillerosConsultaBase {

	private static final long serialVersionUID = 1L;

	public ManejadorSemillerosConsultaDI() {
		super();
		init();
	}

	private void init() {
		semilleros = servicioGeneral.obtenerObjetos(Semillero.class, "select s from Semillero s, SemilleroIntegrante si, InvestigadorInterno ii where si.semillero.id = s.id and si.tipo.id = 'DD' "
				+ " and ii.id.documento = si.integrante.id.documento and ii.dependencia.sede.id = '"+investigadorInterno.getDependencia2().getSede().getId()+"'" );
		setListaSolicitudes(new ArrayList<SemilleroSolicitud>());
		for (Semillero s : semilleros) {
			if (Sede.SEDES_PRESENCIA_NACIONAL.contains(s.getLider().getDependencia().getSede().getId().toString())
					&& investigadorInterno.getDependencia2().getSede().getId().equals(s.getLider().getDependencia().getSede().getId())) {
				for (SemilleroSolicitud ssol : s.getSolicitudes()) {
					if (ssol.getFechaRespuestaVifDi() != null) {
						getListaSolicitudes().add(ssol);
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
