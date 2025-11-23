
package co.edu.unal.hermes.modelo.laboratorios;

import java.util.Date;

import co.edu.unal.hermes.modelo.Tipos;

public class LaboratorioAreasSecundariasOCDE{
        
    private Long id;  
    private Laboratorio laboratorio;
    private Tipos area;
    private Tipos subarea;
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
	public Tipos getArea() {
		return area;
	}
	public void setArea(Tipos area) {
		this.area = area;
	}
	public Tipos getSubarea() {
		return subarea;
	}
	public void setSubarea(Tipos subarea) {
		this.subarea = subarea;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
    
    

}
