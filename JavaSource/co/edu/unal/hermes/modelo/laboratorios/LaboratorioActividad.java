
package co.edu.unal.hermes.modelo.laboratorios;

import co.edu.unal.hermes.modelo.Tipos;

public class LaboratorioActividad{
        
    private Long id;  
    private Laboratorio laboratorio;
    private Tipos actividad;

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
	public Tipos getActividad() {
		return actividad;
	}
	public void setActividad(Tipos actividad) {
		this.actividad = actividad;
	}
	
    
}
