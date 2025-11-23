/*
 * Created on 9-dic-2007
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * @author gacantorm
 */
public class EstanciaPosdoctorado implements Serializable{


	//INFORMACIÓN GENERAL
	private Long id;
	private Persona personaInv;	
	private String esAlianza;	
	private String modalidad;
	private String justificacion;
	private String mecanismos;
	private String nombrePropuesta;
	private Grupo grupo;
	private String programa;
	private String estado;
	private String etapa;
	private Long convocatoria;
	
	//INFORMACION PRESUPUESTO
	private String gastoTiquetes="0";
	private String gastoIdioma="0";
	private String gastoDomicilio="0";
	private String gastoPoliza="0";
	private String gastoSubtotal="0";
	private String gastoSubtotalExt="0";


	private String gastoMensual="0";
	private String gastoSolicitado="0";
	private String gastoMeses;
	
	private Date fechaRegistro;
	
	private String perfil;
	

	
	//ACTIVIDADES
	private Set actividades = new HashSet();
	//CANDIDATOS
	private Set candidatos = new HashSet();
	//GRUPOS
	private Set grupos = new HashSet();
	//ARCHIVOS
	private Set archivos = new HashSet();
	
	//ARCHIVOS
	private Set financiacion = new HashSet();
	
	
	//CAMPOS EVALUACIÓN FACULTAD
	private String item1;
	private String item2;
	private String item3;
	private String valor1;
	private String valor2;
	private String valor3;
	private String valorFacultad;
	
	//CAMPOS SEGUIMIENTO
	
	private Date fechaPrimerInforme;
	private Date fechaSegundoInforme;

	
	private String observacionesActividadesSeguimientoUno;
	private String observacionesActividadesSeguimientoDos;
	private String conceptoDireccionInformeUno;
	private String conceptoDireccionInformeDos;
	private String fortalecimiento;
	private String logros;
	private String dificultades; 
	private String sugerencias;
	
	private String conceptoFacultadInformeUno;
	private String conceptoFacultadInformeDos;
	  
	private String avalDireccionInformeUno;  
	private String avalDireccionInformeDos;
	  
	private String avalFacultadInformeUno;  
	private String avalFacultadInformeDos;
	
	private Date fechaPrimerInformeFacultad;
	private Date fechaSegundoInformeFacultad;
	private Date fechaPrimerInformeDireccion;
	private Date fechaSegundoInformeDireccion;
	
	
	

	public EstanciaPosdoctorado() {
		grupo = new Grupo();
	}

	/**
	 * Constructor que recibe como parametro el id, para inicializar
	 * el id del objeto.
	 * 
	 * @param LongpIdTipoDato
	 */
	public EstanciaPosdoctorado(Long pId){
		this.id = pId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Persona getPersonaInv() {
		return personaInv;
	}

	public void setPersonaInv(Persona personaInv) {
		this.personaInv = personaInv;
	}

	public String getEsAlianza() {
		return esAlianza;
	}

	public void setEsAlianza(String esAlianza) {
		this.esAlianza = esAlianza;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public String getJustificacion() {
		return justificacion;
	}

	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	public String getMecanismos() {
		return mecanismos;
	}

	public void setMecanismos(String mecanismos) {
		this.mecanismos = mecanismos;
	}

	public String getNombrePropuesta() {
		return nombrePropuesta;
	}

	public void setNombrePropuesta(String nombrePropuesta) {
		this.nombrePropuesta = nombrePropuesta;
	}

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public String getGastoTiquetes() {
		return gastoTiquetes;
	}

	public void setGastoTiquetes(String gastoTiquetes) {
		this.gastoTiquetes = gastoTiquetes;
	}

	public String getGastoIdioma() {
		return gastoIdioma;
	}

	public void setGastoIdioma(String gastoIdioma) {
		this.gastoIdioma = gastoIdioma;
	}

	public String getGastoDomicilio() {
		return gastoDomicilio;
	}

	public void setGastoDomicilio(String gastoDomicilio) {
		this.gastoDomicilio = gastoDomicilio;
	}

	public String getGastoPoliza() {
		return gastoPoliza;
	}

	public void setGastoPoliza(String gastoPoliza) {
		this.gastoPoliza = gastoPoliza;
	}

	public String getGastoSubtotal() {
		return gastoSubtotal;
	}

	public void setGastoSubtotal(String gastoSubtotal) {
		this.gastoSubtotal = gastoSubtotal;
	}

	public String getGastoMensual() {
		return gastoMensual;
	}

	public void setGastoMensual(String gastoMensual) {
		this.gastoMensual = gastoMensual;
	}

	public String getGastoSolicitado() {
		return gastoSolicitado;
	}

	public void setGastoSolicitado(String gastoSolicitado) {
		this.gastoSolicitado = gastoSolicitado;
	}

	public Set getActividades() {
		return actividades;
	}

	public void setActividades(Set actividades) {
		this.actividades = actividades;
	}

	public Set getCandidatos() {
		return candidatos;
	}

	public void setCandidatos(Set candidatos) {
		this.candidatos = candidatos;
	}

	public Set getGrupos() {
		return grupos;
	}

	public void setGrupos(Set grupos) {
		this.grupos = grupos;
	}

	public Set getArchivos() {
		return archivos;
	}

	public void setArchivos(Set archivos) {
		this.archivos = archivos;
	}

	public String getGastoMeses() {
		return gastoMeses;
	}

	public void setGastoMeses(String gastoMeses) {
		this.gastoMeses = gastoMeses;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public Long getConvocatoria() {
		return convocatoria;
	}

	public void setConvocatoria(Long convocatoria) {
		this.convocatoria = convocatoria;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getEtapa() {
		return etapa;
	}

	public void setEtapa(String etapa) {
		this.etapa = etapa;
	}

	public String getItem1() {
		return item1;
	}

	public void setItem1(String item1) {
		this.item1 = item1;
	}

	public String getItem2() {
		return item2;
	}

	public void setItem2(String item2) {
		this.item2 = item2;
	}

	public String getItem3() {
		return item3;
	}

	public void setItem3(String item3) {
		this.item3 = item3;
	}

	public String getValor1() {
		return valor1;
	}

	public void setValor1(String valor1) {
		this.valor1 = valor1;
	}

	public String getValor2() {
		return valor2;
	}

	public void setValor2(String valor2) {
		this.valor2 = valor2;
	}

	public String getValor3() {
		return valor3;
	}

	public void setValor3(String valor3) {
		this.valor3 = valor3;
	}

	public String getValorFacultad() {
		return valorFacultad;
	}

	public void setValorFacultad(String valorFacultad) {
		this.valorFacultad = valorFacultad;
	}

	public Set getFinanciacion() {
		return financiacion;
	}

	public void setFinanciacion(Set financiacion) {
		this.financiacion = financiacion;
	}
	
	public String getGastoSubtotalExt() {
		return gastoSubtotalExt;
	}

	public void setGastoSubtotalExt(String gastoSubtotalExt) {
		this.gastoSubtotalExt = gastoSubtotalExt;
	}

	public String getFortalecimiento() {
		return fortalecimiento;
	}

	public void setFortalecimiento(String fortalecimiento) {
		this.fortalecimiento = fortalecimiento;
	}

	public String getDificultades() {
		return dificultades;
	}

	public void setDificultades(String dificultades) {
		this.dificultades = dificultades;
	}

	public String getSugerencias() {
		return sugerencias;
	}

	public void setSugerencias(String sugerencias) {
		this.sugerencias = sugerencias;
	}

	public Date getFechaPrimerInforme() {
		return fechaPrimerInforme;
	}

	public void setFechaPrimerInforme(Date fechaPrimerInforme) {
		this.fechaPrimerInforme = fechaPrimerInforme;
	}

	public Date getFechaSegundoInforme() {
		return fechaSegundoInforme;
	}

	public void setFechaSegundoInforme(Date fechaSegundoInforme) {
		this.fechaSegundoInforme = fechaSegundoInforme;
	}

	public String getObservacionesActividadesSeguimientoUno() {
		return observacionesActividadesSeguimientoUno;
	}

	public void setObservacionesActividadesSeguimientoUno(
			String observacionesActividadesSeguimientoUno) {
		this.observacionesActividadesSeguimientoUno = observacionesActividadesSeguimientoUno;
	}

	public String getObservacionesActividadesSeguimientoDos() {
		return observacionesActividadesSeguimientoDos;
	}

	public void setObservacionesActividadesSeguimientoDos(
			String observacionesActividadesSeguimientoDos) {
		this.observacionesActividadesSeguimientoDos = observacionesActividadesSeguimientoDos;
	}

	public String getConceptoDireccionInformeUno() {
		return conceptoDireccionInformeUno;
	}

	public void setConceptoDireccionInformeUno(String conceptoDireccionInformeUno) {
		this.conceptoDireccionInformeUno = conceptoDireccionInformeUno;
	}

	public String getConceptoDireccionInformeDos() {
		return conceptoDireccionInformeDos;
	}

	public void setConceptoDireccionInformeDos(String conceptoDireccionInformeDos) {
		this.conceptoDireccionInformeDos = conceptoDireccionInformeDos;
	}

	public String getLogros() {
		return logros;
	}

	public void setLogros(String logros) {
		this.logros = logros;
	}

	public String getConceptoFacultadInformeUno() {
	    return conceptoFacultadInformeUno;
	}

	public void setConceptoFacultadInformeUno(String conceptoFacultadInformeUno) {
	    this.conceptoFacultadInformeUno = conceptoFacultadInformeUno;
	}

	public String getConceptoFacultadInformeDos() {
	    return conceptoFacultadInformeDos;
	}

	public void setConceptoFacultadInformeDos(String conceptoFacultadInformeDos) {
	    this.conceptoFacultadInformeDos = conceptoFacultadInformeDos;
	}

	public String getAvalDireccionInformeUno() {
	    return avalDireccionInformeUno;
	}

	public void setAvalDireccionInformeUno(String avalDireccionInformeUno) {
	    this.avalDireccionInformeUno = avalDireccionInformeUno;
	}

	public String getAvalDireccionInformeDos() {
	    return avalDireccionInformeDos;
	}

	public void setAvalDireccionInformeDos(String avalDireccionInformeDos) {
	    this.avalDireccionInformeDos = avalDireccionInformeDos;
	}

	public String getAvalFacultadInformeUno() {
	    return avalFacultadInformeUno;
	}

	public void setAvalFacultadInformeUno(String avalFacultadInformeUno) {
	    this.avalFacultadInformeUno = avalFacultadInformeUno;
	}

	public String getAvalFacultadInformeDos() {
	    return avalFacultadInformeDos;
	}

	public void setAvalFacultadInformeDos(String avalFacultadInformeDos) {
	    this.avalFacultadInformeDos = avalFacultadInformeDos;
	}

	public Date getFechaPrimerInformeFacultad() {
	    return fechaPrimerInformeFacultad;
	}

	public void setFechaPrimerInformeFacultad(Date fechaPrimerInformeFacultad) {
	    this.fechaPrimerInformeFacultad = fechaPrimerInformeFacultad;
	}

	public Date getFechaSegundoInformeFacultad() {
	    return fechaSegundoInformeFacultad;
	}

	public void setFechaSegundoInformeFacultad(Date fechaSegundoInformeFacultad) {
	    this.fechaSegundoInformeFacultad = fechaSegundoInformeFacultad;
	}

	public Date getFechaPrimerInformeDireccion() {
	    return fechaPrimerInformeDireccion;
	}

	public void setFechaPrimerInformeDireccion(Date fechaPrimerInformeDireccion) {
	    this.fechaPrimerInformeDireccion = fechaPrimerInformeDireccion;
	}

	public Date getFechaSegundoInformeDireccion() {
	    return fechaSegundoInformeDireccion;
	}

	public void setFechaSegundoInformeDireccion(Date fechaSegundoInformeDireccion) {
	    this.fechaSegundoInformeDireccion = fechaSegundoInformeDireccion;
	}

	
	
}
