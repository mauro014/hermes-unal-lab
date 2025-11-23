/**
 * @author Martha Liliana Correa O.
 * @date 30/03/2017
 */

package co.edu.unal.hermes.modelo;

public class AvalReporte implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private Reporte reporte;
    private DominioDetalle tipoAval;

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

    public DominioDetalle getTipoAval() {
        return tipoAval;
    }

    public void setTipoAval(DominioDetalle tipoAval) {
        this.tipoAval = tipoAval;
    }



}