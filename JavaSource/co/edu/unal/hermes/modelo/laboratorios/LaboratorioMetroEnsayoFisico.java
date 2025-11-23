
package co.edu.unal.hermes.modelo.laboratorios;

import java.util.Date;

import co.edu.unal.hermes.modelo.Tipos;

public class LaboratorioMetroEnsayoFisico{
        
    private Long id;  
    private Laboratorio laboratorio;
    private String nombreEnsayo;
    private String productosMaterialAEnsayar;
    private String propiedadesMedibles;
    private String minimo;
    private String maximo;
    private String unidades;
    private String descripcion;
    private String normaTecnicaProcedimiento;
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
	public String getNombreEnsayo() {
		return nombreEnsayo;
	}
	public void setNombreEnsayo(String nombreEnsayo) {
		this.nombreEnsayo = nombreEnsayo;
	}
	public String getProductosMaterialAEnsayar() {
		return productosMaterialAEnsayar;
	}
	public void setProductosMaterialAEnsayar(String productosMaterialAEnsayar) {
		this.productosMaterialAEnsayar = productosMaterialAEnsayar;
	}
	public String getPropiedadesMedibles() {
		return propiedadesMedibles;
	}
	public void setPropiedadesMedibles(String propiedadesMedibles) {
		this.propiedadesMedibles = propiedadesMedibles;
	}
	public String getMinimo() {
		return minimo;
	}
	public void setMinimo(String minimo) {
		this.minimo = minimo;
	}
	public String getMaximo() {
		return maximo;
	}
	public void setMaximo(String maximo) {
		this.maximo = maximo;
	}
	public String getUnidades() {
		return unidades;
	}
	public void setUnidades(String unidades) {
		this.unidades = unidades;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getNormaTecnicaProcedimiento() {
		return normaTecnicaProcedimiento;
	}
	public void setNormaTecnicaProcedimiento(String normaTecnicaProcedimiento) {
		this.normaTecnicaProcedimiento = normaTecnicaProcedimiento;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	
    
}
