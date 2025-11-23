package co.edu.unal.hermes.modelo;

import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;

public class LaboratorioODSSec {
    
    private Long id;
    private Laboratorio laboratorio;
    private Tipos ODS;
 
	public LaboratorioODSSec(){
	}

	public LaboratorioODSSec(Long pId){
		this.id = pId;
	}
    
    public Long getId() {
        return id;
    }

	public Laboratorio getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
	}

	public Tipos getODS() {
		return ODS;
	}

	public void setODS(Tipos oDS) {
		ODS = oDS;
	}

	public void setId(Long id) {
		this.id = id;
	}    
}
