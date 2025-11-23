/**
 * @author Camilo Zapata.
 * @date 08/2019
 */

package co.edu.unal.hermes.modelo;

public class PoblacionObjetivoEvento implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Proyecto proyecto;
	private DominioDetalle poblacionObjetivo;
	private String observaciones;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Proyecto getProyecto() {
		return proyecto;
	}
	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
	public DominioDetalle getPoblacionObjetivo() {
		return poblacionObjetivo;
	}
	public void setPoblacionObjetivo(DominioDetalle poblacionObjetivo) {
		this.poblacionObjetivo = poblacionObjetivo;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}