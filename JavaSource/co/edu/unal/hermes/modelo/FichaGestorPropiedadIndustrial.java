package co.edu.unal.hermes.modelo;

public class FichaGestorPropiedadIndustrial implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Long idPropiedad;
    private String objetoProteccion;
    private String descripcion;
    private String ventajas;
    private String impactoSolucion;
    private String monitoreo;
    private String difusion;
    private String mercadoPoblacion;
    private PropiedadIntelectual propiedad;

    public String getMonitoreo() {
        return monitoreo;
    }

    public void setMonitoreo(String monitoreo) {
        this.monitoreo = monitoreo;
    }

    public String getDifusion() {
        return difusion;
    }

    public void setDifusion(String difusion) {
        this.difusion = difusion;
    }
    
    public String getVentajas() {
        return ventajas;
    }

    public void setVentajas(String ventajas) {
        this.ventajas = ventajas;
    }

    public String getImpactoSolucion() {
        return impactoSolucion;
    }

    public void setImpactoSolucion(String impactoSolucion) {
        this.impactoSolucion = impactoSolucion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMercadoPoblacion() {
        return mercadoPoblacion;
    }

    public void setMercadoPoblacion(String mercadoPoblacion) {
        this.mercadoPoblacion = mercadoPoblacion;
    }

    public String getObjetoProteccion() {
        return objetoProteccion;
    }

    public void setObjetoProteccion(String objetoProteccion) {
        this.objetoProteccion = objetoProteccion;
    }

    public PropiedadIntelectual getPropiedad() {
        return propiedad;
    }

    public void setPropiedad(PropiedadIntelectual propiedad) {
        this.propiedad = propiedad;
    }

    public Long getIdPropiedad() {
        return idPropiedad;
    }

    public void setIdPropiedad(Long idPropiedad) {
        this.idPropiedad = idPropiedad;
    }

}