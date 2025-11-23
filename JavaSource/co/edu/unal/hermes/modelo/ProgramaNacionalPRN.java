package co.edu.unal.hermes.modelo;

import java.io.Serializable;

/**
 * Suministra la información principal 
 * de los programas académicos de posgrado 
 */
public class ProgramaNacionalPRN implements Serializable{

	

	private Long id;
	private String name;
	private String resumen;
	private String antecedentes;
	private String planteamiento;
	private String investigador;
	private String integracion;
	private String difusion;
	private String internacionalizacion;
	private String financiaCompleta;
	private Long grupoPrincipal;
	/*private Proyecto proyecto;
	 
		public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
*/
	public ProgramaNacionalPRN(){
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getResumen() {
		return resumen;
	}
	public void setResumen(String resumen) {
		this.resumen = resumen;
	}
	public String getAntecedentes() {
		return antecedentes;
	}
	public void setAntecedentes(String antecedentes) {
		this.antecedentes = antecedentes;
	}
	public String getPlanteamiento() {
		return planteamiento;
	}
	public void setPlanteamiento(String plateamiento) {
		this.planteamiento = plateamiento;
	}
	public String getInvestigador() {
		return investigador;
	}
	public void setInvestigador(String investigador) {
		this.investigador = investigador;
	}
	public String getIntegracion() {
		return integracion;
	}
	public void setIntegracion(String integracion) {
		this.integracion = integracion;
	}
	public String getDifusion() {
		return difusion;
	}
	public void setDifusion(String difusion) {
		this.difusion = difusion;
	}
	public String getInternacionalizacion() {
		return internacionalizacion;
	}
	public void setInternacionalizacion(String internacionalizacion) {
		this.internacionalizacion = internacionalizacion;
	}
	public String getFinanciaCompleta() {
		return financiaCompleta;
	}
	public void setFinanciaCompleta(String financiaCompleta) {
		this.financiaCompleta = financiaCompleta;
	}

	public Long getGrupoPrincipal() {
		return grupoPrincipal;
	}

	public void setGrupoPrincipal(Long grupoPrincipal) {
		this.grupoPrincipal = grupoPrincipal;
	}
	
	
	
	
	
}
