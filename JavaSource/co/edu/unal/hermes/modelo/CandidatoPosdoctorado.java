/*
 * Created on 18-ago-2005
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;

/**
 * Específica la actividad asociada a la movilidad de una visita.
 * 
 */
public class CandidatoPosdoctorado implements Serializable {

    private static final long serialVersionUID = -3624847898530537391L;

    /** The id. */
    private Long id;
    
    /** The estancia. */
    private String estancia;
    
    /** The candidato. */
    private String candidato;
    
    /** The nacionalidad. */
    private String nacionalidad;
    
    /** The ciudad. */
    private String ciudad;
    
    /** The titulo. */
    private String titulo;
    
    /** The grado. */
    private String grado;
    
    /** The universidad. */
    private String universidad;
    
    /** The institucion. */
    private String institucion;
    
    /** The perfil. */
    private String perfil;
    
    /** The pais. */
    private String pais;
    
    /** The pais doctorado. */
    private String paisDoctorado;

    /**
     * Candidato posdoctorado.
     */
    public void CandidatoPosdoctorado() {
        estancia = "";
        candidato = "";
        nacionalidad = "";
        ciudad = "";
        titulo = "";
        grado = "";
        universidad = "";
        institucion = "";
        perfil = "";
        pais = "";
        paisDoctorado = "";
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object a) {
        if (!(a instanceof CandidatoPosdoctorado)) {
            return false;
        }
        CandidatoPosdoctorado act = (CandidatoPosdoctorado) a;

        if (act.id == null || this.id == null) {
            if (act.candidato.equals(this.candidato) && act.perfil.equals(this.perfil)
                    && act.universidad.equals(this.universidad) && act.institucion.equals(this.institucion)) {
                return true;
            }
        } else {
            return (act.getId().equals(this.getId()));
        }

        return false;
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
     * @param id the new id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the estancia.
     *
     * @return the estancia
     */
    public String getEstancia() {
        return estancia;
    }

    /**
     * Sets the estancia.
     *
     * @param estancia the new estancia
     */
    public void setEstancia(String estancia) {
        this.estancia = estancia;
    }

    /**
     * Gets the candidato.
     *
     * @return the candidato
     */
    public String getCandidato() {
        return candidato;
    }

    /**
     * Sets the candidato.
     *
     * @param candidato the new candidato
     */
    public void setCandidato(String candidato) {
        this.candidato = candidato;
    }

    /**
     * Gets the nacionalidad.
     *
     * @return the nacionalidad
     */
    public String getNacionalidad() {
        return nacionalidad;
    }

    /**
     * Sets the nacionalidad.
     *
     * @param nacionalidad the new nacionalidad
     */
    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    /**
     * Gets the ciudad.
     *
     * @return the ciudad
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * Sets the ciudad.
     *
     * @param ciudad the new ciudad
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * Gets the titulo.
     *
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Sets the titulo.
     *
     * @param titulo the new titulo
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Gets the grado.
     *
     * @return the grado
     */
    public String getGrado() {
        return grado;
    }

    /**
     * Sets the grado.
     *
     * @param grado the new grado
     */
    public void setGrado(String grado) {
        this.grado = grado;
    }

    /**
     * Gets the universidad.
     *
     * @return the universidad
     */
    public String getUniversidad() {
        return universidad;
    }

    /**
     * Sets the universidad.
     *
     * @param universidad the new universidad
     */
    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }

    /**
     * Gets the institucion.
     *
     * @return the institucion
     */
    public String getInstitucion() {
        return institucion;
    }

    /**
     * Sets the institucion.
     *
     * @param institucion the new institucion
     */
    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    /**
     * Gets the perfil.
     *
     * @return the perfil
     */
    public String getPerfil() {
        return perfil;
    }

    /**
     * Sets the perfil.
     *
     * @param perfil the new perfil
     */
    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    /**
     * Gets the pais.
     *
     * @return the pais
     */
    public String getPais() {
        return pais;
    }

    /**
     * Sets the pais.
     *
     * @param pais the new pais
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * Gets the pais doctorado.
     *
     * @return the pais doctorado
     */
    public String getPaisDoctorado() {
        return paisDoctorado;
    }

    /**
     * Sets the pais doctorado.
     *
     * @param paisDoctorado the new pais doctorado
     */
    public void setPaisDoctorado(String paisDoctorado) {
        this.paisDoctorado = paisDoctorado;
    }

}
