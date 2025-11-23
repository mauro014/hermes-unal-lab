package co.edu.unal.hermes.modelo.laboratorios;

import java.util.Date;

import co.edu.unal.hermes.modelo.Empresa;
import co.edu.unal.hermes.modelo.Tipos;

public class LaboratorioDetalleInsumos {

	private Long id;
	private Laboratorio laboratorio;
	private InsumoLaboratorio insumo;
	private Double consumo;
	private String almacenamiento;
	private String mes;
	private String annio;
	private Date fechaRegistro;
	private Date fechaActualizacion;
	
	private Tipos actividadManejo;
	private Empresa proveedor;
	private String descActividadConsumo;
	private String almacenamientoDetalle;
	
	private Tipos unidadMedida;
	private Tipos unidadConcentracion;
	private Double valorConcentracion;

	public LaboratorioDetalleInsumos() {
	}

//	@Override
//	public boolean equals(Object otroObjeto) {
//		LaboratorioDetalleInsumos otroDetalle = (LaboratorioDetalleInsumos) otroObjeto;
//		return lineaInvestigacion.getId().equals(
//				otroDetalle.getLineaInvestigacion().getId());
//	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the laboratorio
	 */
	public Laboratorio getLaboratorio() {
		return laboratorio;
	}

	/**
	 * @param laboratorio
	 *            the laboratorio to set
	 */
	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
	}

	/**
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @param fechaRegistro
	 *            the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public InsumoLaboratorio getInsumo() {
		return insumo;
	}

	public void setInsumo(InsumoLaboratorio insumo) {
		this.insumo = insumo;
	}

	public Double getConsumo() {
		return consumo;
	}

	public void setConsumo(Double consumo) {
		this.consumo = consumo;
	}

	public String getAlmacenamiento() {
		return almacenamiento;
	}

	public void setAlmacenamiento(String almacenamiento) {
		this.almacenamiento = almacenamiento;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getAnnio() {
		return annio;
	}

	public void setAnnio(String annio) {
		this.annio = annio;
	}

	public Tipos getActividadManejo() {
		return actividadManejo;
	}

	public void setActividadManejo(Tipos actividadManejo) {
		this.actividadManejo = actividadManejo;
	}

	public Empresa getProveedor() {
		return proveedor;
	}

	public void setProveedor(Empresa proveedor) {
		this.proveedor = proveedor;
	}

	public String getDescActividadConsumo() {
		return descActividadConsumo;
	}

	public void setDescActividadConsumo(String descActividadConsumo) {
		this.descActividadConsumo = descActividadConsumo;
	}

	public String getAlmacenamientoDetalle() {
		return almacenamientoDetalle;
	}

	public void setAlmacenamientoDetalle(String almacenamientoDetalle) {
		this.almacenamientoDetalle = almacenamientoDetalle;
	}

	public Tipos getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(Tipos unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public Tipos getUnidadConcentracion() {
		return unidadConcentracion;
	}

	public void setUnidadConcentracion(Tipos unidadConcentracion) {
		this.unidadConcentracion = unidadConcentracion;
	}

	public Double getValorConcentracion() {
		return valorConcentracion;
	}

	public void setValorConcentracion(Double valorConcentracion) {
		this.valorConcentracion = valorConcentracion;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}	
}
