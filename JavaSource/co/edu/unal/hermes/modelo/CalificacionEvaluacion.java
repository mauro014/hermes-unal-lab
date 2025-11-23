/*
 * Created on 27-ene-2006
 */
package co.edu.unal.hermes.modelo;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.servicios.IServicioEvaluacion;

/**
 * @author jpduqueg
 */
public class CalificacionEvaluacion {
    
    private Long id;
    private ProyectoEvaluador proyectoEvaluador;
    private CriterioEvaluacion criterio;
   
    private String cualitativa;
    private Float cuantitativa;
    private Double calificacionSeleccion;
    
    public Double minimoVista;
    public Double maximmoVista;
    private Long modalidadId;
    private ModalidadCriterioTipoPregunta mctp;
	private IServicioEvaluacion servicioEvaluacion;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }        
    
    public String getCualitativa() {
        return cualitativa;
    }
    public void setCualitativa(String cualitativa) {
        this.cualitativa = cualitativa;
    }
    public Float getCuantitativa() {
        return cuantitativa;
    }
    public void setCuantitativa(Float cuantitativa) {
        this.cuantitativa = cuantitativa;
    }
    public CriterioEvaluacion getCriterio() {
        return criterio;
    }
    public void setCriterio(CriterioEvaluacion criterio) {
        this.criterio = criterio;
    }
    public ProyectoEvaluador getProyectoEvaluador() {
        return proyectoEvaluador;
    }
    public void setProyectoEvaluador(ProyectoEvaluador proyectoEvaluador) {
        this.proyectoEvaluador = proyectoEvaluador;
    }
	public Double getMinimoVista() {
		return minimoVista;
	}
	public void setMinimoVista(Double minimoVista) {
		this.minimoVista = minimoVista;
	}
	public Double getMaximmoVista() {
		return maximmoVista;
	}
	public void setMaximmoVista(Double maximmoVista) {
		this.maximmoVista = maximmoVista;
	}
	public void setModalidadId(Long modalidadId) {
		this.modalidadId = modalidadId;
	}
	public Long getModalidadId() {
		return modalidadId;
	}
	public void setMctp(ModalidadCriterioTipoPregunta mctp) {
		this.mctp = mctp;
	}
	public ModalidadCriterioTipoPregunta getMctp() {
		return mctp;
	}
	
	public boolean isPreguntaCualitativa(){
		if(mctp != null){
			if(mctp.getTipoPregunta().getPagina().equals("/pages/Evaluacion/cualitativa.jsp"))
				return true;
			else
				return false;
		}else{
			return false;
		}
	}
	
	public boolean isPreguntaCuantitativaCualitativa(){
		if(mctp != null){
			if(mctp.getTipoPregunta().getPagina().equals("/pages/Evaluacion/cualitativaCuantitativa.jsp"))
				return true;
			else
				return false;
		}else{
			return false;
		}
	}
	
	public boolean isPreguntaCuantitativa(){
		if(mctp != null){
			if(mctp.getTipoPregunta().getPagina().equals("/pages/Evaluacion/cuantitativa.jsp"))
				return true;
			else
				return false;
		}else{
			return false;
		}
	}
	
	public boolean isPreguntaSeleccionUnica(){
		if(mctp != null){
			if(mctp.getTipoPregunta().getPagina().equals("/pages/Evaluacion/seleccionUnica.jsp"))
				return true;
			else
				return false;
		}else{
			return false;
		}
	}
	
	public String getCriterioModalidadTipoPreguntaMaximo(){
		return mctp.getTipoPregunta().getMaximo();
	}
	public void setServicioEvaluacion(IServicioEvaluacion servicioEvaluacion) {
		this.servicioEvaluacion = servicioEvaluacion;
	}
	public IServicioEvaluacion getServicioEvaluacion() {
		return servicioEvaluacion;
	}
	
	public List getListaOpciones(){
		if(servicioEvaluacion != null){
			ModalidadCriterioTipoPregunta mctp=servicioEvaluacion.obtenerModalidadTipoPreguntaXModalidadYCriterio(
					this.getModalidadId(),
					this.getCriterio().getId());
			List listaAuxiliar=new Vector();
			for(Iterator it=mctp.getTipoPregunta().getOpciones().iterator();it.hasNext();)
			{
				OpcionMultiple om=(OpcionMultiple) it.next();
				listaAuxiliar.add(new SelectItem(new Float( om.getId().floatValue()),om.getNombre())); 			
			}
			return listaAuxiliar;
		}
		return null;
	}
    /**
     * @return the calificacionSeleccion
     */
    public Double getCalificacionSeleccion() {
        return calificacionSeleccion;
    }
    /**
     * @param calificacionSeleccion the calificacionSeleccion to set
     */
    public void setCalificacionSeleccion(Double calificacionSeleccion) {
        this.calificacionSeleccion = calificacionSeleccion;
    }
	
}
