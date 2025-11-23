package co.edu.unal.hermes.modelo;


public class SostenibilidadProyecto {
    
    private Long id;    
    private String nivelSostenibilidad;
    private String porqueNivelSostenibilidad;
    private String continuidadProyecto;
    private String porqueContinuidadProyecto;
    private String articulacionIniciativas;
    private String aliados;
    private String observaciones;
    private Proyecto proyecto;
    
        
    public SostenibilidadProyecto() {
	super();
	// TODO Auto-generated constructor stub
    }    
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNivelSostenibilidad() {
        return nivelSostenibilidad;
    }
    public void setNivelSostenibilidad(String nivelSostenibilidad) {
        this.nivelSostenibilidad = nivelSostenibilidad;
    }
    public String getPorqueNivelSostenibilidad() {
        return porqueNivelSostenibilidad;
    }
    public void setPorqueNivelSostenibilidad(String porqueNivelSostenibilidad) {
        this.porqueNivelSostenibilidad = porqueNivelSostenibilidad;
    }
    public String getContinuidadProyecto() {
        return continuidadProyecto;
    }
    public void setContinuidadProyecto(String continuidadProyecto) {
        this.continuidadProyecto = continuidadProyecto;
    }
    public String getPorqueContinuidadProyecto() {
        return porqueContinuidadProyecto;
    }
    public void setPorqueContinuidadProyecto(String porqueContinuidadProyecto) {
        this.porqueContinuidadProyecto = porqueContinuidadProyecto;
    }
    public String getArticulacionIniciativas() {
        return articulacionIniciativas;
    }
    public void setArticulacionIniciativas(String articulacionIniciativas) {
        this.articulacionIniciativas = articulacionIniciativas;
    }
    public String getAliados() {
        return aliados;
    }
    public void setAliados(String aliados) {
        this.aliados = aliados;
    }
    public String getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    public Proyecto getProyecto() {
        return proyecto;
    }
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

}
