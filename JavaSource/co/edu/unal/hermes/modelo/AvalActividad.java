/*
 * Created on 02-abril-2014
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;

/**
 * Específica la actividad asociada al aval.
 * 
 */
public class AvalActividad implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1366984569645819208L;
    private Long id;
    private String descripcion;
    private Long mesInicial;
    private Long duracionMeses;
    private Aval aval;
    private boolean borrable = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDuracionMeses(Long duracionMeses) {
        this.duracionMeses = duracionMeses;
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

    public Long getMesInicial() {
        return mesInicial;
    }

    public void setMesInicial(Long mesInicial) {
        this.mesInicial = mesInicial;
    }

    public Long getDuracionMeses() {
        return duracionMeses;
    }

}
