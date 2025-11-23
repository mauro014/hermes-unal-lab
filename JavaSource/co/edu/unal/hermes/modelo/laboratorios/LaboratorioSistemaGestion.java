
package co.edu.unal.hermes.modelo.laboratorios;

import co.edu.unal.hermes.modelo.Tipos;

public class LaboratorioSistemaGestion{
        
    private Long id;  
    private Laboratorio laboratorio;
    private Tipos sistemaGestion;

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
	public Tipos getSistemaGestion() {
		return sistemaGestion;
	}
	public void setSistemaGestion(Tipos sistemaGestion) {
		this.sistemaGestion = sistemaGestion;
	}
	
    
}
