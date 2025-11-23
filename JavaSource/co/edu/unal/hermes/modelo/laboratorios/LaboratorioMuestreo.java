
package co.edu.unal.hermes.modelo.laboratorios;

import co.edu.unal.hermes.modelo.Tipos;

public class LaboratorioMuestreo{
        
    private Long id;  
    private Laboratorio laboratorio;
    private Tipos muestreo;

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
	public Tipos getMuestreo() {
		return muestreo;
	}
	public void setMuestreo(Tipos muestreo) {
		this.muestreo = muestreo;
	}
	
    
}
