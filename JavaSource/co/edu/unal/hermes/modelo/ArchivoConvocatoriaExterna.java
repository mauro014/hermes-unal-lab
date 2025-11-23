package co.edu.unal.hermes.modelo;

import java.util.Date;

public class ArchivoConvocatoriaExterna implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String convocatoria;
    private String nombre;
    private Date fechaCarga;
    private Date fechaElimina;
    private Persona personaCarga;
    private Persona personaElimina;
    private String estado;

    /** default constructor */
    public ArchivoConvocatoriaExterna() {
        // para objeto vacio
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getConvocatoria() {
        return convocatoria;
    }

    public void setConvocatoria(String convocatoria) {
        this.convocatoria = convocatoria;
    }

    public Date getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(Date fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public Date getFechaElimina() {
        return fechaElimina;
    }

    public void setFechaElimina(Date fechaElimina) {
        this.fechaElimina = fechaElimina;
    }

    public Persona getPersonaCarga() {
        return personaCarga;
    }

    public void setPersonaCarga(Persona personaCarga) {
        this.personaCarga = personaCarga;
    }

    public Persona getPersonaElimina() {
        return personaElimina;
    }

    public void setPersonaElimina(Persona personaElimina) {
        this.personaElimina = personaElimina;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}