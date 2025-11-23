package co.edu.unal.hermes.modelo;

import java.util.Date;

public class ConvenioOtrosi {

	private Long id;
	
	private Convenio convenio;
	
	private String numero;
	
	private Date fecha;
	
	public ConvenioOtrosi(){
		
	}

	public Convenio getConvenio() {
		return convenio;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setConvenio(Convenio convenio) {
		this.convenio = convenio;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Long getId() {
		return id;
	}
	
}
