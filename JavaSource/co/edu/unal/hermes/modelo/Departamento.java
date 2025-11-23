package co.edu.unal.hermes.modelo;

import java.io.Serializable;

/**
 *Especifica el departamento en el cual se realiza el proyecto. 
 */
public class Departamento implements Serializable{

	private String id;
	private String nombre;
	private String sigla;
	
	/**
	 * Es la región que afecta el proyecto. 
	 * Dentro de las regiones están: Andina, Atlántica, Pacífica,
	 * Orinoquía, Amazonía, Caribe Insular, Pacífica Insular.
	 */
	private Region region;
	private Ciudad capital;
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Region getRegion() {
		return region;
	}
	public void setRegion(Region region) {
		this.region = region;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Ciudad getCapital() {
		return capital;
	}
	public void setCapital(Ciudad capital) {
		this.capital = capital;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
}
