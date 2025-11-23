
package co.edu.unal.hermes.modelo.laboratorios;

import java.util.Date;

import co.edu.unal.hermes.modelo.Tipos;

public class LaboratorioMetroMaterialReferencia{
        
    private Long id;  
    private Laboratorio laboratorio;
    
    private String nombreMaterialReferencia;
    private String productor;
    private String proveedor;
    private Tipos usaMaterialReferencia;
    private Tipos tipo;
    private Boolean materialDificilAdquisicion;
    private Boolean usoAseguraTrazabilildadMedicion;
    private Date fechaRegistro;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Laboratorio getLaboratorio() {
		return laboratorio;
	}
	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
	}
	public String getNombreMaterialReferencia() {
		return nombreMaterialReferencia;
	}
	public void setNombreMaterialReferencia(String nombreMaterialReferencia) {
		this.nombreMaterialReferencia = nombreMaterialReferencia;
	}
	public String getProductor() {
		return productor;
	}
	public void setProductor(String productor) {
		this.productor = productor;
	}
	public String getProveedor() {
		return proveedor;
	}
	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}
	public Tipos getUsaMaterialReferencia() {
		return usaMaterialReferencia;
	}
	public void setUsaMaterialReferencia(Tipos usaMaterialReferencia) {
		this.usaMaterialReferencia = usaMaterialReferencia;
	}
	public Tipos getTipo() {
		return tipo;
	}
	public void setTipo(Tipos tipo) {
		this.tipo = tipo;
	}
	public Boolean getMaterialDificilAdquisicion() {
		return materialDificilAdquisicion;
	}
	public void setMaterialDificilAdquisicion(Boolean materialDificilAdquisicion) {
		this.materialDificilAdquisicion = materialDificilAdquisicion;
	}
	public Boolean getUsoAseguraTrazabilildadMedicion() {
		return usoAseguraTrazabilildadMedicion;
	}
	public void setUsoAseguraTrazabilildadMedicion(Boolean usoAseguraTrazabilildadMedicion) {
		this.usoAseguraTrazabilildadMedicion = usoAseguraTrazabilildadMedicion;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
}
