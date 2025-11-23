package co.edu.unal.hermes.vista.convocatorias;

import java.util.List;

import javax.faces.model.SelectItem;

public class VistaModalidadCriterioTipoPregunta {

	SelectItem criterio;
	Long idTipoPregunta;
	List criterioSeleccionado;
	private long idCriterio;
	private String nombreTipoInvestigacion;
	
	public String getNombreTipoInvestigacion() 
	{
		return nombreTipoInvestigacion;
	}
	
	public void setNombreTipoInvestigacion(String nombreTipoInvestigacion) 
	{
		this.nombreTipoInvestigacion = nombreTipoInvestigacion;
	}
	
	public List getCriterioSeleccionado() {
		return criterioSeleccionado;
	}
	public void setCriterioSeleccionado(List criterioSeleccionado) {
		this.criterioSeleccionado = criterioSeleccionado;
	}
	public SelectItem getCriterio() {
		return criterio;
	}
	public void setCriterio(SelectItem criterio) {
		this.criterio = criterio;
	}
	public Long getIdTipoPregunta() {
		return idTipoPregunta;
	}
	public void setIdTipoPregunta(Long idTipoPregunta) {
		this.idTipoPregunta = idTipoPregunta;
	}
	
	public Long getIdCriterio()
	{
		if(criterio!=null)
		{
			return new Long((String)criterio.getValue());
		}
		return null;
	}	
}
