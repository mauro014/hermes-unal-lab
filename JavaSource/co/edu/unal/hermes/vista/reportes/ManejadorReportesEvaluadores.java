package co.edu.unal.hermes.vista.reportes;

import javax.faces.context.FacesContext;

import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorReportesEvaluadores extends ManejadorBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean evaluadoresInternos;
	private boolean evaluadoresExternos;
	

	public ManejadorReportesEvaluadores() {
		super();

	}



	public String generarReporteEvaluadores() {
		
		if(evaluadoresInternos && evaluadoresExternos) {
			 mensajeError("Solo uno de los tipos de evaluador puede ser seleccionado a la vez.");
		}else if(!evaluadoresInternos && !evaluadoresExternos){
			mensajeError("Uno de los tipo de evaluador debe ser seleccionado.");
		}else {
			personaActual = (Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL);
			InvestigadorInterno ii = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());

			ReporteBirt r = new ReporteBirt();
			r.adicionarParametro("s", ii.getDependencia().getSede().getId().toString());
			if (evaluadoresInternos) {
				r.setNombreReporte("/evaluacion/reporteEvaluadoresInternos");
			} else {
				r.setNombreReporte("/evaluacion/reporteEvaluadoresExternos");
			}
			r.adicionarParametro("lineas", "S");
			r.setFormato(ReporteBirt.FORMATO_XLS);
			sesion.setAttribute("reporte", r);
			FacesContext context = FacesContext.getCurrentInstance();
			try {
				context.getExternalContext().dispatch("/ReporteEngineServlet");
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				context.responseComplete();
			}
		}
		
		return "";
		
		
	}

	
	public boolean isEvaluadoresInternos() {
		return evaluadoresInternos;
	}



	public void setEvaluadoresInternos(boolean evaluadoresInternos) {
		this.evaluadoresInternos = evaluadoresInternos;
	}



	public boolean isEvaluadoresExternos() {
		return evaluadoresExternos;
	}



	public void setEvaluadoresExternos(boolean evaluadoresExternos) {
		this.evaluadoresExternos = evaluadoresExternos;
	}

}
