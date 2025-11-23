/*
 * Created on 18-ago-2005
 */
package co.edu.unal.hermes.modelo;

/**
 * Maneja las metas que se asocian a cada proyecto
 */
public class MetaProyecto{
    
    private Long id;   
    private String nombre;
//    private Proyecto proyecto;
    private ObjetivoEspecifico objetivo;
    private boolean  borrable=false;
    private Long numeroOrden;   
    
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
    
	public boolean isBorrable() {
		return borrable;
	}
	public void setBorrable(boolean borrable) {
		this.borrable = borrable;
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
	public ObjetivoEspecifico getObjetivo() {
		return objetivo;
	}
	public void setObjetivo(ObjetivoEspecifico objetivo) {
		this.objetivo = objetivo;
	}
//	public Proyecto getProyecto() {
//		return proyecto;
//	}
//	public void setProyecto(Proyecto proyecto) {
//		this.proyecto = proyecto;
//	}
    	
}
