/**
 * @author Martha Liliana Correa O.
 * @date 09/11/2015
 */

package co.edu.unal.hermes.modelo;

import java.util.Date;

public class Pregunta implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String descripcion;
    private String respuesta;
    private String etiqueta;
    private PreguntaClasificacion clasificacion;
    private Persona personaCrea;
    private Persona personaElimina;
    private String estado;
    private Date fecha;

    /**
     * Variables no mapeadas en la base de datos Se usan para la selección del
     * archivo a descargar
     */
    private Long idArchivo;
    private String extensionArchivo;

    /** default constructor */
    public Pregunta() {
        // Para crear objeto pregunta
    }

    public Pregunta(Long id) {
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

    public Long getIdArchivo() {
        return idArchivo;
    }

    public void setIdArchivo(Long idArchivo) {
        this.idArchivo = idArchivo;
    }

    public String getExtensionArchivo() {
        return extensionArchivo;
    }

    public void setExtensionArchivo(String extensionArchivo) {
        this.extensionArchivo = extensionArchivo;
    }

    public Persona getPersonaCrea() {
        return personaCrea;
    }

    public void setPersonaCrea(Persona personaCrea) {
        this.personaCrea = personaCrea;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public PreguntaClasificacion getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(PreguntaClasificacion clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}