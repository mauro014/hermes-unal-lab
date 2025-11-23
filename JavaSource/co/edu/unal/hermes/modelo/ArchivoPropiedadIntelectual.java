package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;

public class ArchivoPropiedadIntelectual implements Serializable {

    private static final long serialVersionUID = 478891960712405536L;
    private Long id;
    private String nombre;
    private String descripcion;
    private TipoArchivo tipo;
    private Date fechaCreacion;
    private Date fechaBorrado;
    private PropiedadIntelectual propiedad;
    private String estado;
    private Persona personaCarga;
    private Persona personaElimina;
    private Long propiedadBorrado;
    private boolean visibleDocente;
    
    public static final Short ARCHIVO_SOLICITUD = 82;
    public static final Short ARCHIVO_TRAMITE = 83;
    public static final Short ARCHIVO_FECHA_DERECHO = 84;
    public static final String PARAMETRO_TIPO_ARCHIVO = "PI";

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaBorrado() {
        return fechaBorrado;
    }

    public void setFechaBorrado(Date fechaBorrado) {
        this.fechaBorrado = fechaBorrado;
    }

    public PropiedadIntelectual getPropiedad() {
        return propiedad;
    }

    public void setPropiedad(PropiedadIntelectual propiedad) {
        this.propiedad = propiedad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public Long getPropiedadBorrado() {
        return propiedadBorrado;
    }

    public void setPropiedadBorrado(Long propiedadBorrado) {
        this.propiedadBorrado = propiedadBorrado;
    }

    public boolean isVisibleDocente() {
        return visibleDocente;
    }

    public void setVisibleDocente(boolean visibleDocente) {
        this.visibleDocente = visibleDocente;
    }

    public TipoArchivo getTipo() {
        return tipo;
    }

    public void setTipo(TipoArchivo tipo) {
        this.tipo = tipo;
    }

}
