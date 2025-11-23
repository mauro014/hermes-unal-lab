/*
 * Created on 18-ago-2005
 */
package co.edu.unal.hermes.modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Iterator;


/**
 * Maneja los objetivos específicos que enmarcan cada proyecto
 */
public class ObjetivoEspecifico{
    
    private Long id;   
    private String nombre;  
    private Proyecto proyecto;
    private Set<MetaProyecto> metasObjetivo = new HashSet<MetaProyecto>();
    private boolean  borrable=false;
    private Long numeroOrden; 
    private String medioVerificacion;
    
    private Set actividades = new HashSet();
    private List listaActividades;
    
    public List<MetaProyecto> getListaMetas() {
      List<MetaProyecto> listaMetas = new ArrayList<MetaProyecto>();
      listaMetas.addAll(metasObjetivo);
      Iterator<MetaProyecto> i = metasObjetivo.iterator();
      Long value = 1L;
      while (i.hasNext()) {
      	MetaProyecto metaProyecto = i.next();
      	metaProyecto.setNumeroOrden(value++);
      }
      return listaMetas;
    }
    
	public void adicionarMeta(MetaProyecto meta) {
	  if (metasObjetivo == null) {
	  	metasObjetivo = new HashSet<MetaProyecto>();
	  }
	  meta.setObjetivo(this);
	  metasObjetivo.add(meta);
	}
	
	public void borrarMetaProyecto(MetaProyecto meta) {
		metasObjetivo.remove(meta);
	}
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
   
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Proyecto getProyecto() {
        return proyecto;
    }
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
    
	public boolean isBorrable() {
		return borrable;
	}
	public void setBorrable(boolean borrable) {
		this.borrable = borrable;
	}
	public Set getActividades() {
		return actividades;
	}
	public void setActividades(Set actividades) {
		this.actividades = actividades;
	}
	public List getListaActividades() {		
		return listaActividades;
	}
	public void setListaActividades(java.util.List listaActividades) {
		this.listaActividades = listaActividades;
	}
	
	public Set<MetaProyecto> getMetasProyecto() {
		return metasObjetivo;
	}
	public void setMetasProyecto(Set<MetaProyecto> metasProyecto) {
		this.metasObjetivo = metasProyecto;
	}
	public void insertarActividad(){
    	ActividadObjetivo a=new ActividadObjetivo ();
    	a.setDescripcion("");
    	a.setMesInicial(new Integer(1));
    	a.setDuracionMeses(new Integer(1));
    	listaActividades.add(a);
    	a=null;
    }
	
	public boolean getTieneActividades(){
		if (listaActividades.size() > 0){
			return true;
		}
		else{
			return false;
		}
	}
    /**
     * @return the numero_orden
     */
    public Long getNumeroOrden() {
        return numeroOrden;
    }
    /**
     * @param numero_orden the numero_orden to set
     */
    public void setNumeroOrden(Long numeroOrden) {
        this.numeroOrden = numeroOrden;
    }

	public String getMedioVerificacion() {
		return medioVerificacion;
	}

	public void setMedioVerificacion(String medioVerificacion) {
		this.medioVerificacion = medioVerificacion;
	}
	
	
}
