package co.edu.unal.hermes.vista.convocatorias.terminosReferencia;

import java.util.List;

import javax.faces.context.FacesContext;

import co.edu.unal.hermes.modelo.ConvocatoriaTerminosReferencia;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorConsultarTerminosReferenciaConvocatoria extends ManejadorBase 
{

	/**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    private List<ConvocatoriaTerminosReferencia> listaTerminosReferenciaConv;
    private Persona personaActual;
    private Dependencia dependenciaActual;
    private ConvocatoriaTerminosReferencia convocatoriaSeleccionada;
    
	public ManejadorConsultarTerminosReferenciaConvocatoria() {
	    super();
	    personaActual = (Persona) sesion.getAttribute("persona");
		if(personaActual != null){
			InvestigadorInterno ii = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());
			if(ii != null){
				dependenciaActual = ii.getDependencia();
			}
		}
	    String hqlTerRef = "select #id e.id, #titulo e.titulo, #estado e.estado from ConvocatoriaTerminosReferencia e where e.dependenciaPrincipal.id = '" + dependenciaActual.getId() + "' order by e.id desc";
		listaTerminosReferenciaConv = servicioGeneral.obtenerObjetosLimitado(ConvocatoriaTerminosReferencia.class, hqlTerRef);
    }
	
	public String editarConsultarConvocatoria(){
		sesion.setAttribute("convocatoriaTRSel", convocatoriaSeleccionada.getId());
		return "crearTerminiosReferenciaConvocatoria";
	}
	
	public void imprimirTerminosReferenciaConvocatoria(){
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("con_tr_id", String.valueOf(convocatoriaSeleccionada.getId()));
	    r.setFormato(ReporteBirt.FORMATO_PDF);
	    r.setNombreReporte("/convocatoria/terminosReferenciaConvocatoria");
	    sesion.setAttribute("reporte", r);
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            context.getExternalContext().dispatch("/ReporteEngineServlet");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            context.responseComplete();
        }
	}
	
	public String solicitarParametrizacionConvocatoria(){
		ConvocatoriaTerminosReferencia convocatoria = servicioModalidad.obtenerConvocatoriaTerminosReferenciaPorId(convocatoriaSeleccionada.getId());
		convocatoria.setEstado("P");
		servicioGeneral.guardarObjeto(convocatoria);
		return "";
	}

	public List<ConvocatoriaTerminosReferencia> getListaTerminosReferenciaConv() {
		return listaTerminosReferenciaConv;
	}


	public void setListaTerminosReferenciaConv(List<ConvocatoriaTerminosReferencia> listaTerminosReferenciaConv) {
		this.listaTerminosReferenciaConv = listaTerminosReferenciaConv;
	}


	public Persona getPersonaActual() {
		return personaActual;
	}


	public void setPersonaActual(Persona personaActual) {
		this.personaActual = personaActual;
	}


	public Dependencia getDependenciaActual() {
		return dependenciaActual;
	}


	public void setDependenciaActual(Dependencia dependenciaActual) {
		this.dependenciaActual = dependenciaActual;
	}


	public ConvocatoriaTerminosReferencia getConvocatoriaSeleccionada() {
		return convocatoriaSeleccionada;
	}


	public void setConvocatoriaSeleccionada(ConvocatoriaTerminosReferencia convocatoriaSeleccionada) {
		this.convocatoriaSeleccionada = convocatoriaSeleccionada;
	}
	
}
