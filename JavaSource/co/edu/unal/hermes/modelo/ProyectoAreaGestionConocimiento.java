/**
 * @author Camilo Zapata.
 * @date 08/2019
 */

package co.edu.unal.hermes.modelo;

public class ProyectoAreaGestionConocimiento implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Proyecto proyecto;
	private DominioDetalle areaGestionConocimiento;
	
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
	public DominioDetalle getAreaGestionConocimiento() {
		return areaGestionConocimiento;
	}
	public void setAreaGestionConocimiento(DominioDetalle areaGestionConocimiento) {
		this.areaGestionConocimiento = areaGestionConocimiento;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}