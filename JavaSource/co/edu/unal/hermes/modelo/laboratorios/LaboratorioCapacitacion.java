
package co.edu.unal.hermes.modelo.laboratorios;

import co.edu.unal.hermes.modelo.Tipos;

public class LaboratorioCapacitacion{
        
    private Long id;  
    private Laboratorio laboratorio;
    private Tipos capacitacion;

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
	public Tipos getCapacitacion() {
		return capacitacion;
	}
	public void setCapacitacion(Tipos capacitacion) {
		this.capacitacion = capacitacion;
	}
	
    
}
