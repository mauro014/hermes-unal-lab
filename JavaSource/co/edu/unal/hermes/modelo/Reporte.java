/**
 * @author Martha Liliana Correa O.
 * @date 30/03/2017
 */

package co.edu.unal.hermes.modelo;

public class Reporte implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String ruta;
    private String nombreExterno;
    private String consultaIndicador;
    private String consultaDetalle;
    private Boolean requiereSede;
    private Boolean requiereFacultad;
    private Boolean requiereUab;
    private Boolean requiereConvocatoriaPry;
    private Boolean requiereAno;
    private Boolean requiereConvocatoriaMov;
    private Boolean graficoPastel;
    private String tituloGrafico;
    private String tituloRegistrosTotales;
    private Boolean totalMoneda;
    private String ejeHorizontal;
    private String observacion;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getNombreExterno() {
        return nombreExterno;
    }

    public void setNombreExterno(String nombreExterno) {
        this.nombreExterno = nombreExterno;
    }

    public String getConsultaIndicador() {
        return consultaIndicador;
    }

    public void setConsultaIndicador(String consultaIndicador) {
        this.consultaIndicador = consultaIndicador;
    }

    public String getConsultaDetalle() {
        return consultaDetalle;
    }

    public void setConsultaDetalle(String consultaDetalle) {
        this.consultaDetalle = consultaDetalle;
    }

    public Boolean getRequiereSede() {
        return requiereSede;
    }

    public void setRequiereSede(Boolean requiereSede) {
        this.requiereSede = requiereSede;
    }

    public Boolean getRequiereAno() {
        return requiereAno;
    }

    public void setRequiereAno(Boolean requiereAno) {
        this.requiereAno = requiereAno;
    }

    public Boolean getRequiereConvocatoriaPry() {
        return requiereConvocatoriaPry;
    }

    public void setRequiereConvocatoriaPry(Boolean requiereConvocatoriaPry) {
        this.requiereConvocatoriaPry = requiereConvocatoriaPry;
    }

    public Boolean getRequiereConvocatoriaMov() {
        return requiereConvocatoriaMov;
    }

    public void setRequiereConvocatoriaMov(Boolean requiereConvocatoriaMov) {
        this.requiereConvocatoriaMov = requiereConvocatoriaMov;
    }

    public Boolean getGraficoPastel() {
        return graficoPastel;
    }

    public void setGraficoPastel(Boolean graficoPastel) {
        this.graficoPastel = graficoPastel;
    }

    public String getTituloGrafico() {
        return tituloGrafico;
    }

    public void setTituloGrafico(String tituloGrafico) {
        this.tituloGrafico = tituloGrafico;
    }

    public String getTituloRegistrosTotales() {
        return tituloRegistrosTotales;
    }

    public void setTituloRegistrosTotales(String tituloRegistrosTotales) {
        this.tituloRegistrosTotales = tituloRegistrosTotales;
    }

    public Boolean getTotalMoneda() {
        return totalMoneda;
    }

    public void setTotalMoneda(Boolean totalMoneda) {
        this.totalMoneda = totalMoneda;
    }

    public String getEjeHorizontal() {
        return ejeHorizontal;
    }

    public void setEjeHorizontal(String ejeHorizontal) {
        this.ejeHorizontal = ejeHorizontal;
    }

    public Boolean getRequiereFacultad() {
        return requiereFacultad;
    }

    public void setRequiereFacultad(Boolean requiereFacultad) {
        this.requiereFacultad = requiereFacultad;
    }

    public Boolean getRequiereUab() {
        return requiereUab;
    }

    public void setRequiereUab(Boolean requiereUab) {
        this.requiereUab = requiereUab;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}