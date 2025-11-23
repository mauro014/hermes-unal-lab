package co.edu.unal.hermes.modelo;

import java.util.Date;

public class TerminosConvocatoriaExterna implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String descripcion;
    private Date vigenciaInicio;
    private Date vigenciaTermina;
    private Persona personaAbre;
    private Persona personaCierra;
    private String estado; // A:activo I:inactivo

    /** default constructor */
    public TerminosConvocatoriaExterna() {
        // Para objeto vacio
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getVigenciaInicio() {
        return vigenciaInicio;
    }

    public void setVigenciaInicio(Date vigenciaInicio) {
        this.vigenciaInicio = vigenciaInicio;
    }

    public Date getVigenciaTermina() {
        return vigenciaTermina;
    }

    public void setVigenciaTermina(Date vigenciaTermina) {
        this.vigenciaTermina = vigenciaTermina;
    }

    public Persona getPersonaAbre() {
        return personaAbre;
    }

    public void setPersonaAbre(Persona personaAbre) {
        this.personaAbre = personaAbre;
    }

    public Persona getPersonaCierra() {
        return personaCierra;
    }

    public void setPersonaCierra(Persona personaCierra) {
        this.personaCierra = personaCierra;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}