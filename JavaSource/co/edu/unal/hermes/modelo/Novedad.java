/**
 * @author Martha Liliana Correa O.
 * @date 09/11/2016
 */

package co.edu.unal.hermes.modelo;

import java.util.Date;

public class Novedad implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nombre;
    private String descripcion;
    private String url;
    private NovedadClasificacion clasificacion;
    private Persona personaCrea;
    private Persona personaElimina;
    private String estado;
    private Date fecha;
    private Date fechaNovedad;

    /** default constructor */
    public Novedad() {
        // Para crear objeto pregunta
    }

    public Novedad(Long id) {
        this.id = id;
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

    public Persona getPersonaCrea() {
        return personaCrea;
    }

    public void setPersonaCrea(Persona personaCrea) {
        this.personaCrea = personaCrea;
    }

    public NovedadClasificacion getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(NovedadClasificacion clasificacion) {
        this.clasificacion = clasificacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getFechaNovedad() {
        return fechaNovedad;
    }

    public void setFechaNovedad(Date fechaNovedad) {
        this.fechaNovedad = fechaNovedad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}