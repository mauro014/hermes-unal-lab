package co.edu.unal.hermes.modelo;

public class EntidadArticulo implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3569263948469305324L;
    Long id;
    String nombre = "";
    String tipo = "";
    Long especie = 0L;
    Long frescos = 0L;
    String fuente = "";
    Aval aval;
    TipoRubro tipoRubro;
    String descripcionRubro;
    Long porcentaje = 0L;
    private String noPermitirEliminar;
    private FuenteFinanciacion entidad;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getEspecie() {
        if (this.especie != null) {
            return especie;
        } else {
            return 0L;
        }
    }

    public void setEspecie(Long especie) {
        this.especie = especie;
    }

    public Long getFrescos() {
        if (this.frescos != null) {
            return frescos;
        } else {
            return 0L;
        }
    }

    public void setFrescos(Long fescos) {
        this.frescos = fescos;
    }

    public Long getId() {
        return id;
    }

    public Aval getAval() {
        return aval;
    }

    public void setAval(Aval aval) {
        this.aval = aval;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    public TipoRubro getTipoRubro() {
        return tipoRubro;
    }

    public void setTipoRubro(TipoRubro tipoRubro) {
        this.tipoRubro = tipoRubro;
    }

    public String getNoPermitirEliminar() {
        return noPermitirEliminar;
    }

    public void setNoPermitirEliminar(String noPermitirEliminar) {
        this.noPermitirEliminar = noPermitirEliminar;
    }

    public Long getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Long porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getDescripcionRubro() {
        return descripcionRubro;
    }

    public void setDescripcionRubro(String descripcionRubro) {
        this.descripcionRubro = descripcionRubro;
    }

    public FuenteFinanciacion getEntidad() {
        return entidad;
    }

    public void setEntidad(FuenteFinanciacion entidad) {
        this.entidad = entidad;
    }

}
