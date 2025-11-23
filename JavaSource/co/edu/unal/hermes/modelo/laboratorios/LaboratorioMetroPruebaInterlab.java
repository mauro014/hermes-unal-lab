
package co.edu.unal.hermes.modelo.laboratorios;

import java.util.Date;

import co.edu.unal.hermes.modelo.Tipos;

public class LaboratorioMetroPruebaInterlab{
        
    private Long id;  
    private Laboratorio laboratorio;
    private Boolean participa;
    private String cual;
    private Tipos desempenoObtenido;
    private Tipos proveedor;
    private String nombreProveedor;
    private String tipoDocumento;
    private String identificacion;    
    
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
	public Boolean getParticipa() {
		return participa;
	}
	public void setParticipa(Boolean participa) {
		this.participa = participa;
	}
	public String getCual() {
		return cual;
	}
	public void setCual(String cual) {
		this.cual = cual;
	}
	public Tipos getDesempenoObtenido() {
		return desempenoObtenido;
	}
	public void setDesempenoObtenido(Tipos desempenoObtenido) {
		this.desempenoObtenido = desempenoObtenido;
	}
	public Tipos getProveedor() {
		return proveedor;
	}
	public void setProveedor(Tipos proveedor) {
		this.proveedor = proveedor;
	}
	public String getNombreProveedor() {
		return nombreProveedor;
	}
	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getIdentificacion() {
		return identificacion;
	}
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
}
