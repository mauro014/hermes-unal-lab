/**
 * @author Martha Liliana Correa O.
 * @date 12/04/2017
 */

package co.edu.unal.hermes.modelo;

public class IndicadorReporte implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public static final String NACIONAL = "N";
    public static final String SEDE = "S";
    public static final String FACULTAD = "F";
    
    private Long id;
    private Reporte reporte;
    private String nivelIndicador;
    private Tipos categoria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Reporte getReporte() {
        return reporte;
    }

    public void setReporte(Reporte reporte) {
        this.reporte = reporte;
    }

    public String getNivelIndicador() {
        return nivelIndicador;
    }

    public void setNivelIndicador(String nivelIndicador) {
        this.nivelIndicador = nivelIndicador;
    }

    public Tipos getCategoria() {
        return categoria;
    }

    public void setCategoria(Tipos categoria) {
        this.categoria = categoria;
    }

}