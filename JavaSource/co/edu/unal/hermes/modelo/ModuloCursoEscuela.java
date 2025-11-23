/*
 * Created on 06-Mayo-2010
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package co.edu.unal.hermes.modelo;

import java.util.Date;
import java.util.List;

/**
 * @author Ing. Wilver Alexander Martínez Martínez (wam²)
 *
 */
public class ModuloCursoEscuela implements IIdentidad{
    
    public static final String RAIZ = "0";
    
		
	private String id;
	private String nombre;
	private String nombrePop;	
	private ModuloCursoEscuela padre;	
	private String descripcion;
	private String nivel;
	private List hijos;
	private boolean tieneHijos;
	private boolean mostrarHijos;
	
	private String curso;  
	private String modulo; 
	private String tipoEstudiante; 
	     
	private Date fechaInicial; 
	private Date fechaFinal; 
	private String valor;
	
	private String tipo;
	private String completo;
	private String dominio;
	private Long egresado_un; 
	private Long pregrado; 
	private Long posgrado; 
	
	private Date fechaInicial2; 
	private Date fechaFinal2; 
	private Long egresado_un2; 
	private Long pregrado2; 
	private Long posgrado2; 
	private String horario;
	private String  valorDos;
	private String  requisitos;	
	private String codigoSIA;
	private String documentosAdjuntar;
	
	private Date fecIniCancelaSinPerdida;
	private Date fecFinCancelaSinPerdida;
	private Date fecIniCancelaConPerdida;
	private Date fecFinCancelaConPerdida;
	private Date fecIniCancelaOtro;
	private Date fecFinCancelaOtro;
	
	private Date fecIniCurso;
	private Date fecFinCurso;
	private String estado;

	
	
	public boolean isTieneHijos() {
		return tieneHijos;
	}

	public void setTieneHijos(boolean tieneHijos) {
		this.tieneHijos = tieneHijos;
	}

	public List getHijos() {
		return hijos;
	}

	public void setHijos(List hijos) {
		this.hijos = hijos;
	}

	public ModuloCursoEscuela()
	{	
	}
	
	public ModuloCursoEscuela(String id)
	{	
		this.id=id;
	}

	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	public String getNombre() {	    
	    return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public ModuloCursoEscuela getPadre() {
		return padre;
	}
	public void setPadre(ModuloCursoEscuela padre) {
		this.padre = padre;
	}
	/**
	 * @return Returns the nombrePop.
	 */
	public String getNombrePop() {
		return nombrePop;
	}
	/**
	 * @param nombrePop The nombrePop to set.
	 */
	public void setNombrePop(String nombrePop) {
		this.nombrePop = nombrePop;
	}

	public boolean isMostrarHijos() {
		return mostrarHijos;
	}

	public void setMostrarHijos(boolean mostrarHijos) {
		this.mostrarHijos = mostrarHijos;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public String getTipoEstudiante() {
		return tipoEstudiante;
	}

	public void setTipoEstudiante(String tipoEstudiante) {
		this.tipoEstudiante = tipoEstudiante;
	}

	public Date getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCompleto() {
		return completo;
	}

	public void setCompleto(String completo) {
		this.completo = completo;
	}

	public String getDominio() {
		return dominio;
	}

	public void setDominio(String dominio) {
		this.dominio = dominio;
	}

	public Long getPregrado() {
		return pregrado;
	}

	public void setPregrado(Long pregrado) {
		this.pregrado = pregrado;
	}

	public Long getPosgrado() {
		return posgrado;
	}

	public void setPosgrado(Long posgrado) {
		this.posgrado = posgrado;
	}

	public Long getEgresado_un() {
		return egresado_un;
	}

	public void setEgresado_un(Long egresado_un) {
		this.egresado_un = egresado_un;
	}

	public Date getFechaInicial2() {
		return fechaInicial2;
	}

	public void setFechaInicial2(Date fechaInicial2) {
		this.fechaInicial2 = fechaInicial2;
	}

	public Date getFechaFinal2() {
		return fechaFinal2;
	}

	public void setFechaFinal2(Date fechaFinal2) {
		this.fechaFinal2 = fechaFinal2;
	}

	public Long getEgresado_un2() {
		return egresado_un2;
	}

	public void setEgresado_un2(Long egresado_un2) {
		this.egresado_un2 = egresado_un2;
	}

	public Long getPregrado2() {
		return pregrado2;
	}

	public void setPregrado2(Long pregrado2) {
		this.pregrado2 = pregrado2;
	}

	public Long getPosgrado2() {
		return posgrado2;
	}

	public void setPosgrado2(Long posgrado2) {
		this.posgrado2 = posgrado2;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getCodigoSIA() {
		return codigoSIA;
	}

	public void setCodigoSIA(String codigoSIA) {
		this.codigoSIA = codigoSIA;
	}

	public String getValorDos() {
		return valorDos;
	}

	public void setValorDos(String valorDos) {
		this.valorDos = valorDos;
	}

	public String getRequisitos() {
		return requisitos;
	}

	public void setRequisitos(String requisitos) {
		this.requisitos = requisitos;
	}

	public String getDocumentosAdjuntar() {
		return documentosAdjuntar;
	}

	public void setDocumentosAdjuntar(String documentosAdjuntar) {
		this.documentosAdjuntar = documentosAdjuntar;
	}

	public Date getFecIniCancelaSinPerdida() {
		return fecIniCancelaSinPerdida;
	}

	public void setFecIniCancelaSinPerdida(Date fecIniCancelaSinPerdida) {
		this.fecIniCancelaSinPerdida = fecIniCancelaSinPerdida;
	}

	public Date getFecFinCancelaSinPerdida() {
		return fecFinCancelaSinPerdida;
	}

	public void setFecFinCancelaSinPerdida(Date fecFinCancelaSinPerdida) {
		this.fecFinCancelaSinPerdida = fecFinCancelaSinPerdida;
	}

	public Date getFecIniCancelaConPerdida() {
		return fecIniCancelaConPerdida;
	}

	public void setFecIniCancelaConPerdida(Date fecIniCancelaConPerdida) {
		this.fecIniCancelaConPerdida = fecIniCancelaConPerdida;
	}

	public Date getFecFinCancelaConPerdida() {
		return fecFinCancelaConPerdida;
	}

	public void setFecFinCancelaConPerdida(Date fecFinCancelaConPerdida) {
		this.fecFinCancelaConPerdida = fecFinCancelaConPerdida;
	}

	public Date getFecIniCancelaOtro() {
		return fecIniCancelaOtro;
	}

	public void setFecIniCancelaOtro(Date fecIniCancelaOtro) {
		this.fecIniCancelaOtro = fecIniCancelaOtro;
	}

	public Date getFecFinCancelaOtro() {
		return fecFinCancelaOtro;
	}

	public void setFecFinCancelaOtro(Date fecFinCancelaOtro) {
		this.fecFinCancelaOtro = fecFinCancelaOtro;
	}

	public Date getFecIniCurso() {
		return fecIniCurso;
	}

	public void setFecIniCurso(Date fecIniCurso) {
		this.fecIniCurso = fecIniCurso;
	}

	public Date getFecFinCurso() {
		return fecFinCurso;
	}

	public void setFecFinCurso(Date fecFinCurso) {
		this.fecFinCurso = fecFinCurso;
	}

	/**
	 * @return the estado
	 */
	public String getEstado()
	{
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado)
	{
		this.estado = estado;
	}
	
	
	
	
	
}
