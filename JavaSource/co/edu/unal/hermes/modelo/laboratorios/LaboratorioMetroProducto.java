
package co.edu.unal.hermes.modelo.laboratorios;

import java.util.Date;

import co.edu.unal.hermes.modelo.Pais;
import co.edu.unal.hermes.modelo.Tipos;

public class LaboratorioMetroProducto{
        
    private Long id;  
    private Laboratorio laboratorio;
    private String producto;
    private Tipos subred;
    private Boolean exportacion;
    private Pais pais;
    private String ciudad;
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
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public Tipos getSubred() {
		return subred;
	}
	public void setSubred(Tipos subred) {
		this.subred = subred;
	}
	public Boolean getExportacion() {
		return exportacion;
	}
	public void setExportacion(Boolean exportacion) {
		this.exportacion = exportacion;
	}
	public Pais getPais() {
		return pais;
	}
	public void setPais(Pais pais) {
		this.pais = pais;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
}
