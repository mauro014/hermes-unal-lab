/*
 * Created on 18-ago-2005
 */
package co.edu.unal.hermes.modelo;

/**
 * Maneja los objetivos específicos que enmarcan cada proyecto
 */
public class CreacionArtistica{
    
    private Long id; 
    private Proyecto proyecto;
    private String areaTematica;
    private String tecnicasEmplear;
    private String trabajosAnteriores;
    private String aporteExperiencia;
    
    
    public Long getId() {
        return id;
    }
    private void setId(Long id) {
        this.id = id;
    }
   
    public Proyecto getProyecto() {
        return proyecto;
    }
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
    
    public String getAporteExperiencia() {
        return aporteExperiencia;
    }
    public void setAporteExperiencia(String aporteExperiencia) {
        this.aporteExperiencia = aporteExperiencia;
    }
    public String getAreaTematica() {
        return areaTematica;
    }
    public void setAreaTematica(String areaTematica) {
        this.areaTematica = areaTematica;
    }
    public String getTecnicasEmplear() {
        return tecnicasEmplear;
    }
    public void setTecnicasEmplear(String tecnicasEmplear) {
        this.tecnicasEmplear = tecnicasEmplear;
    }
    public String getTrabajosAnteriores() {
        return trabajosAnteriores;
    }
    public void setTrabajosAnteriores(String trabajosAnteriores) {
        this.trabajosAnteriores = trabajosAnteriores;
    }
    
}
