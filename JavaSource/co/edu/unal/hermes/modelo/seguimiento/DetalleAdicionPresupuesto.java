package co.edu.unal.hermes.modelo.seguimiento;

import co.edu.unal.hermes.modelo.Gasto;
import co.edu.unal.hermes.modelo.TipoRubro;

public class DetalleAdicionPresupuesto {
	
	private Long id;
	private SolicitudAdicionPresupuestal solicitudAdicion;
	private Gasto gasto;
	private TipoRubro tipoRubro;
	private Long valorAdicionRubro;
	private Long valorRubroAntesAdicion;
	private Long valorRubroDespuesAdicion;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public SolicitudAdicionPresupuestal getSolicitudAdicion() {
		return solicitudAdicion;
	}
	public void setSolicitudAdicion(SolicitudAdicionPresupuestal solicitudAdicion) {
		this.solicitudAdicion = solicitudAdicion;
	}
	public Gasto getGasto() {
		return gasto;
	}
	public void setGasto(Gasto gasto) {
		this.gasto = gasto;
	}
	public TipoRubro getTipoRubro() {
		return tipoRubro;
	}
	public void setTipoRubro(TipoRubro tipoRubro) {
		this.tipoRubro = tipoRubro;
	}
	public Long getValorAdicionRubro() {
		return valorAdicionRubro;
	}
	public void setValorAdicionRubro(Long valorAdicionRubro) {
		this.valorAdicionRubro = valorAdicionRubro;
	}
	public Long getValorRubroAntesAdicion() {
		return valorRubroAntesAdicion;
	}
	public void setValorRubroAntesAdicion(Long valorRubroAntesAdicion) {
		this.valorRubroAntesAdicion = valorRubroAntesAdicion;
	}
	public Long getValorRubroDespuesAdicion() {
		return valorRubroDespuesAdicion;
	}
	public void setValorRubroDespuesAdicion(Long valorRubroDespuesAdicion) {
		this.valorRubroDespuesAdicion = valorRubroDespuesAdicion;
	}	

}
