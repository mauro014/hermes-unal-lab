package co.edu.unal.hermes.modelo;

import java.io.Serializable;


public class AgendaProyecto implements Serializable, Comparable {
    
    private String orden;

    private Proyecto proyecto;
   
    private AgendaConocimiento agenda;
    
   
    
   
	public AgendaProyecto()
    {}
    
   
       
    public int compareTo(Object obj) {        
        if(obj != null && obj instanceof AgendaProyecto){
            AgendaProyecto d = (AgendaProyecto)obj;
            String s = d.getAgenda().getNombre();
            int i = s.compareTo(agenda.getNombre());
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
        if(!(o instanceof AgendaProyecto))
        {
            return false;
        }
        AgendaProyecto d= (AgendaProyecto) o;
        if(d.getAgenda().getId()==null || this.getAgenda().getId()==null)
            return false;
        
        return d.getAgenda().getId().equals(this.getAgenda().getId());
        
    }



	public String getOrden() {
		return orden;
	}



	public void setOrden(String orden) {
		this.orden = orden;
	}



	public Proyecto getProyecto() {
		return proyecto;
	}



	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}



	public AgendaConocimiento getAgenda() {
		return agenda;
	}



	public void setAgenda(AgendaConocimiento agenda) {
		this.agenda = agenda;
	}	
    
    
}
