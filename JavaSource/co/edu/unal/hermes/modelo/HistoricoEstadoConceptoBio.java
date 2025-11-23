/**
 * @author: Martha Liliana Correa O.
 * @date: 14/06/2016
 */

package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;

public class HistoricoEstadoConceptoBio implements Serializable  {

	private static final long serialVersionUID = 2215328871659263784L;
	
	private Long id;
	
    private ConceptoContratoBiodiversidad concepto;
    private String estadoConcepto;
    private String justificacion;
    private Date fecha;
    private Persona revisor;
   
	public HistoricoEstadoConceptoBio() {
		super();
	}
	
	public HistoricoEstadoConceptoBio(Persona revisor) {
		super();
		this.fecha = new Date();
		this.revisor = revisor;
	}
	
	public String getNombreEstado(){
        if(this.estadoConcepto!=null){
            if(this.estadoConcepto.equals(ConceptoContratoBiodiversidad.FORMULACION)){
                return "Formulación";
            }else if(this.estadoConcepto.equals(ConceptoContratoBiodiversidad.ENVIADO)){
                return "Enviado";
            }else if(this.estadoConcepto.equals(ConceptoContratoBiodiversidad.REQUIERE_SUSCRIPCION)){
                return "Es necesaria la suscripción de contrato u otrosí.";
            }else if(this.estadoConcepto.equals(ConceptoContratoBiodiversidad.NO_REQUIERE_SUSCRIPCION)){
                return "No es necesaria la suscripción de contrato u otrosí.";
            }else if(this.estadoConcepto.equals(ConceptoContratoBiodiversidad.RECHAZADO)){
                return "Rechazado";
            }else if(this.estadoConcepto.equals(ConceptoContratoBiodiversidad.EN_TRAMITE)){
                return "En trámite";
            }else if(this.estadoConcepto.equals(ConceptoContratoBiodiversidad.DEVUELTA)){
                return "Devuelta para correcciones";
            }else{
                return "";
            }
        }else{
            return "";
        }
    }

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getFecha() {
		return fecha;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Persona getRevisor() {
		return revisor;
	}

	public void setRevisor(Persona revisor) {
		this.revisor = revisor;
	}

	public String getJustificacion() {
		return justificacion;
	}

	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

    public ConceptoContratoBiodiversidad getConcepto() {
        return concepto;
    }

    public void setConcepto(ConceptoContratoBiodiversidad concepto) {
        this.concepto = concepto;
    }

    public String getEstadoConcepto() {
        return estadoConcepto;
    }

    public void setEstadoConcepto(String estadoConcepto) {
        this.estadoConcepto = estadoConcepto;
    }
}
