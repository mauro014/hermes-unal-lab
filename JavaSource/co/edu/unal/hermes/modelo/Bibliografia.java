/*
 * Created on 18-ago-2005
 */
package co.edu.unal.hermes.modelo;

/**
 * Especifica la información del libro, revista u otro. 
 */
public class Bibliografia {
    
    private Long id;    
    private String valor;
    private Proyecto proyecto;
    private boolean borrable=false;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }   
    
    public String getValor() {
        return valor;
    }
    public void setValor(String valor) {
        this.valor = valor;
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
}
