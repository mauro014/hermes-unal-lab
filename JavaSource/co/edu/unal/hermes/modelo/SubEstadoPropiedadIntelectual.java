/**
 * @author Martha Liliana Correa O.
 * @date 21/07/2016
 */

package co.edu.unal.hermes.modelo;

public class SubEstadoPropiedadIntelectual implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public static final String PERMISO_EDICION = "E";
    public static final String PROCESO_TRAMITE = "T";
    public static final String PROCESO_CERRADO = "C";

    public static final Long PROPIEDAD_INDUSTRIAL_FORMULACION = 1L;
    public static final Long DERECHOS_AUTOR_FORMULACION = 31L;
    public static final Long DERECHOS_OBTENTOR_FORMULACION = 47L;

    public static final Long PROPIEDAD_INDUSTRIAL_ENVIADO = 2L;
    public static final Long DERECHOS_AUTOR_ENVIADO = 32L;
    public static final Long DERECHOS_OBTENTOR_ENVIADO = 48L;
    
    public static final Long CONCESION = 21L;
    public static final Long CONCESION_PARCIAL = 22L;
    
    public static final String SUB_ESTADO_CONCESION = "'21','22'";
    public static final String SUB_ESTADO_DOMINIO = "'30','46'";
    

    private Long id;
    private String nombre;
    private String descripcion;
    private EstadoPropiedadIntelectual estado;
    private String tramite; // Con el fin de determinar permisos de edicion y
                            // trámite (E: Edición; T:Trámite; C: Cerrado)
    private Long orden;

    /** default constructor */
    public SubEstadoPropiedadIntelectual() {
        /*
         * Constructor para crear el objeto subestado propiedad vacio
         */
    }

    public boolean isEsEditable() {
        if (this.tramite != null) {
            if (this.tramite.equals(PERMISO_EDICION)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEsTramite() {
        if (this.tramite != null) {
            if (this.tramite.equals(PROCESO_TRAMITE)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEsCerrado() {
        if (this.tramite != null) {
            if (this.tramite.equals(PROCESO_CERRADO)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isEsConcedida(){
        if(this.id!=null && (this.id.equals(CONCESION) || this.id.equals(CONCESION_PARCIAL))){
            return true;
        }
        return false;
    }

    public SubEstadoPropiedadIntelectual(Long id) {
        this.setId(id);
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

    public EstadoPropiedadIntelectual getEstado() {
        return estado;
    }

    public void setEstado(EstadoPropiedadIntelectual estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTramite() {
        return tramite;
    }

    public void setTramite(String tramite) {
        this.tramite = tramite;
    }

    public Long getOrden() {
        return orden;
    }

    public void setOrden(Long orden) {
        this.orden = orden;
    }

}