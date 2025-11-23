/*
 * Created on 19-sep-2005
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author jpduqueg
 */
public class Modalidad implements Serializable{
          
    private Long id;
    private TipoModalidad tipo;
    private Set proyectos = new HashSet();
    private Set criteriosTipoPregunta = new HashSet();
    private Set rubrosFinanciables=new HashSet();
    private String cambioRubroFacultad;
    
    
    public List getListaRubrosFinanciables() {
    	List listaRubrosFinanciables = new ArrayList();
    	listaRubrosFinanciables.addAll(rubrosFinanciables);
        return listaRubrosFinanciables;
    }
    
    public void adicionarRubroFinanciable(RubroFinanciable rubroFinanciable){
	    rubroFinanciable.setModalidad(this);
	    rubrosFinanciables.add(rubroFinanciable);
	}
	
	public void borrarRubroFinanciable(RubroFinanciable rubroFinanciable){
	    rubrosFinanciables.remove(rubroFinanciable);
	}
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
  
    public TipoModalidad getTipo() {
        return tipo;
    }
    public void setTipo(TipoModalidad tipo) {
        this.tipo = tipo;
    }
    public Set getProyectos() {
        return proyectos;
    }
    public void setProyectos(Set proyectos) {
        this.proyectos = proyectos;
    }
    
    public Set getCriteriosTipoPregunta() {
        return criteriosTipoPregunta;
    }
    public void setCriteriosTipoPregunta(Set criterios) {
        this.criteriosTipoPregunta = criterios;
    }

	public Set getRubrosFinanciables() {
		return rubrosFinanciables;
	}

	public void setRubrosFinanciables(Set rubrosFinanciables) {
		this.rubrosFinanciables = rubrosFinanciables;
	}
		
	public String getCambioRubroFacultad() {
		return cambioRubroFacultad;
	}
	
	public void setCambioRubroFacultad(String cambioRubroFacultad) {
		this.cambioRubroFacultad = cambioRubroFacultad;
	}
	
    public boolean isEsConvocatoriaLegalizacion() {
        return this.getId().equals(Convocatoria.MODALIDAD_CONVOCATORIA_EXTERNA)
                || this.getId().equals(Convocatoria.MODALIDAD_FICHA_MINIMA_ID);
    }
	
}
