package co.edu.unal.hermes.modelo;

public class Vigencia{
    
	private Long id;	
	private Proyecto proyecto;
	private Short vigencia;
	private Long valor;
	
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Proyecto getProyecto() {
        return proyecto;
    }
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
    
    public Long getValor() {
        return valor;
    }
    public void setValor(Long valor) {
        this.valor = valor;
    }
    
    public Short getVigencia() {
        return vigencia;
    }
    public void setVigencia(Short vigencia) {
        this.vigencia = vigencia;
    }
}
