package co.edu.unal.hermes.modelo;

public class AvalComiteEtica {

    private Long id;
    private Long tipo;
    private String nombre;
    private Dependencia dependencia;
    private AvalComiteEtica cesi;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTipo() {
		return tipo;
	}
	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Dependencia getDependencia() {
		return dependencia;
	}
	public void setDependencia(Dependencia dependencia) {
		this.dependencia = dependencia;
	}
	public AvalComiteEtica getCesi() {
		return cesi;
	}
	public void setCesi(AvalComiteEtica cesi) {
		this.cesi = cesi;
	}
}
