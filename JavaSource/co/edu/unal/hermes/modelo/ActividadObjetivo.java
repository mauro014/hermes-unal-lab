/*
 * Created on 18-ago-2005
 */
package co.edu.unal.hermes.modelo;

import java.util.HashSet;
import java.util.Set;

/**
 * Específica la actividad asociada al proyecto.
 * 
 */
public class ActividadObjetivo {

    /** The id. */
    private Long id;

    /** The descripcion. */
    private String descripcion;

    /** The semana inicial. */
    private Integer semanaInicial;

    /** The mes inicial. */
    private Integer mesInicial;

    /** The duracion semanas. */
    private Integer duracionSemanas;

    /** The duracion meses. */
    private Integer duracionMeses;

    /** The objetivo especifico. */
    private ObjetivoEspecifico objetivoEspecifico;

    /** The resultado proyecto. */
    private ResultadoProyecto resultadoProyecto;

    /** The borrable. */
    private boolean borrable = false;

    /** The actividades. */
    private Set actividades = new HashSet();

    /** The investigador. */
    private String investigador;

    /** The investigador pro. */
    private Investigador investigadorPro;

    /** The tipodoc. */
    private String tipodoc;

    /** The identificador. */
    private String identificador;

    /** The proyecto. */
    private Proyecto proyecto;

    /**
     * Gets the tipodoc.
     *
     * @return the tipodoc
     */
    public String getTipodoc() {
        return tipodoc;
    }

    /**
     * Sets the tipodoc.
     *
     * @param tipodoc
     *            the new tipodoc
     */
    public void setTipodoc(String tipodoc) {
        this.tipodoc = tipodoc;
    }

    /**
     * Gets the actividades.
     *
     * @return the actividades
     */
    public Set getActividades() {
        return actividades;
    }

    /**
     * Sets the actividades.
     *
     * @param actividades
     *            the new actividades
     */
    public void setActividades(Set actividades) {
        this.actividades = actividades;
    }

    /**
     * Gets the investigador.
     *
     * @return the investigador
     */
    public String getInvestigador() {
        return investigador;
    }

    /**
     * Sets the investigador.
     *
     * @param investigador
     *            the new investigador
     */
    public void setInvestigador(String investigador) {
        this.investigador = investigador;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the descripcion.
     *
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Sets the descripcion.
     *
     * @param descripcion
     *            the new descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Gets the duracion semanas.
     *
     * @return the duracion semanas
     */
    public Integer getDuracionSemanas() {
        return duracionSemanas;
    }

    /**
     * Sets the duracion semanas.
     *
     * @param duracionSemanas
     *            the new duracion semanas
     */
    public void setDuracionSemanas(Integer duracionSemanas) {
        this.duracionSemanas = duracionSemanas;
    }

    /**
     * Gets the semana inicial.
     *
     * @return the semana inicial
     */
    public Integer getSemanaInicial() {
        return semanaInicial;
    }

    /**
     * Sets the semana inicial.
     *
     * @param semanaInicial
     *            the new semana inicial
     */
    public void setSemanaInicial(Integer semanaInicial) {
        this.semanaInicial = semanaInicial;
    }

    /**
     * Gets the duracion meses.
     *
     * @return the duracion meses
     */
    public Integer getDuracionMeses() {
        return duracionMeses;
    }

    /**
     * Sets the duracion meses.
     *
     * @param duracionMeses
     *            the new duracion meses
     */
    public void setDuracionMeses(Integer duracionMeses) {
        this.duracionMeses = duracionMeses;
        setDuracionSemanas(new Integer(4 * this.duracionMeses.intValue()));
    }

    /**
     * Gets the mes inicial.
     *
     * @return the mes inicial
     */
    public Integer getMesInicial() {
        return mesInicial;
    }

    /**
     * Sets the mes inicial.
     *
     * @param mesInicial
     *            the new mes inicial
     */
    public void setMesInicial(Integer mesInicial) {
        this.mesInicial = mesInicial;
        setSemanaInicial(new Integer(4 * (mesInicial.intValue() - 1) + 1));
    }

    /**
     * Pasar semanas meses.
     */
    public void pasarSemanasMeses() {
        mesInicial = new Integer((new Double(((semanaInicial.intValue() - 1) / 4) + 1)).intValue());
        duracionMeses = new Integer((new Double((duracionSemanas.intValue() / 4))).intValue());
    }

    /**
     * Checks if is borrable.
     *
     * @return true, if is borrable
     */
    public boolean isBorrable() {
        return borrable;
    }

    /**
     * Sets the borrable.
     *
     * @param borrable
     *            the new borrable
     */
    public void setBorrable(boolean borrable) {
        this.borrable = borrable;
    }

    /**
     * Gets the objetivo especifico.
     *
     * @return the objetivo especifico
     */
    public ObjetivoEspecifico getObjetivoEspecifico() {
        return objetivoEspecifico;
    }

    /**
     * Sets the objetivo especifico.
     *
     * @param objetivoEspecifico
     *            the new objetivo especifico
     */
    public void setObjetivoEspecifico(ObjetivoEspecifico objetivoEspecifico) {
        this.objetivoEspecifico = objetivoEspecifico;
    }

    /**
     * Gets the identificador.
     *
     * @return the identificador
     */
    public String getIdentificador() {
        if (this.investigador != null && this.tipodoc != null) {
            identificador = this.investigador + "@@" + this.tipodoc;
        }
        return identificador;
    }

    /**
     * Sets the identificador.
     *
     * @param identificador
     *            the new identificador
     */
    public void setIdentificador(String identificador) {

        int n = identificador.indexOf("@@");

        if (n > 0) {
            this.investigador = identificador.substring(0, n);
            this.tipodoc = identificador.substring(n + 2, identificador.length());
        }

        this.identificador = identificador;
    }

    /**
     * Gets the investigador pro.
     *
     * @return the investigador pro
     */
    public Investigador getInvestigadorPro() {
        return investigadorPro;
    }

    /**
     * Sets the investigador pro.
     *
     * @param investigadorPro
     *            the new investigador pro
     */
    public void setInvestigadorPro(Investigador investigadorPro) {
        this.investigadorPro = investigadorPro;
    }

    /**
     * Gets the proyecto.
     *
     * @return the proyecto
     */
    public Proyecto getProyecto() {
        return proyecto;
    }

    /**
     * Sets the proyecto.
     *
     * @param proyecto
     *            the new proyecto
     */
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    /**
     * Gets the resultado proyecto.
     *
     * @return the resultado proyecto
     */
    public ResultadoProyecto getResultadoProyecto() {
        return resultadoProyecto;
    }

    /**
     * Sets the resultado proyecto.
     *
     * @param resultadoProyecto
     *            the new resultado proyecto
     */
    public void setResultadoProyecto(ResultadoProyecto resultadoProyecto) {
        this.resultadoProyecto = resultadoProyecto;
    }

}
