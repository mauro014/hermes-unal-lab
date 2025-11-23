/*
 * Created on 18-ago-2005
 */
package co.edu.unal.hermes.modelo;

/**
 * Referencia posibles resultados de una movilidad, al solicitar un aval 
 */ 
public class ResultadoAval implements java.io.Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 7754370097500868348L;
    private Long id;
    private Aval aval;      
    private String descripcion;
    private boolean borrable=false;
    
    public Long getId() {
        return id;
    }
    
    private void setId(Long id) {
        this.id = id;
    }
     
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }      
    
    public Aval getAval() {
        return aval;
    }
    public void setAval(Aval aval) {
        this.aval = aval;
    }       
	public boolean isBorrable() {
		return borrable;
	}
	public void setBorrable(boolean borrable) {
		this.borrable = borrable;
	}
}
