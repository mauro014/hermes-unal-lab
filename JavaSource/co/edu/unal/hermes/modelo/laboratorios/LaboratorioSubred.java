
package co.edu.unal.hermes.modelo.laboratorios;

import co.edu.unal.hermes.modelo.Tipos;

public class LaboratorioSubred{
        
    private Long id;  
    private Laboratorio laboratorio;
    private Tipos subred;

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
	public Tipos getSubred() {
		return subred;
	}
	public void setSubred(Tipos subred) {
		this.subred = subred;
	}
    
}
