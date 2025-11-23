package co.edu.unal.hermes.modelo;

import java.io.Serializable;


public class AgendaConocimiento implements Serializable, Comparable {
    
    private String id;

    private String nombre;
   
    private AgendaConocimiento padre;
    
    private int orden;
    
    private String estado;
    
   
	public AgendaConocimiento()
    {}
    
    public AgendaConocimiento(String id){
    	this.id=id;
    }
    
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
    public AgendaConocimiento getPadre() {
        return padre;
    }
    public void setPadre(AgendaConocimiento padre) {
        this.padre = padre;
    }     
       
    public int compareTo(Object obj) {        
        if(obj != null && obj instanceof AgendaConocimiento){
            AgendaConocimiento d = (AgendaConocimiento)obj;
            String s = d.getNombre();
            int i = s.compareTo(nombre);
            if(i < 0){
                return +1;
            }else if(i > 0){
                return -1;
            }
        }
        return 0;
    }
    
    public boolean equals(Object o)
    {
        if(!(o instanceof AgendaConocimiento))
        {
            return false;
        }
        AgendaConocimiento d= (AgendaConocimiento) o;
        if(d.getId()==null || this.getId()==null)
            return false;
        
        return d.getId().equals(this.getId());
        
    }

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}	
    
    
}
