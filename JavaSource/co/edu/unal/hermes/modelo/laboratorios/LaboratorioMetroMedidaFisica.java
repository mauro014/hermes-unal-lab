
package co.edu.unal.hermes.modelo.laboratorios;

import java.util.Date;

import co.edu.unal.hermes.modelo.Tipos;

public class LaboratorioMetroMedidaFisica{
        
    private Long id;  
    private Laboratorio laboratorio;
    
    private String descripcionMensurando;//
    private Tipos magnitudFisica;//
    private Tipos campoAplicacion;//
    private String intervaloPuntoMedMinimo;//
    private String intervaloPuntoMedMaximo;//
    private Tipos unidadesIntervaloPrefijo;
    private Tipos unidadesIntervaloUnidad;//
    private String unidadesIntervaloSimbolo;//
    private Tipos incertidumbreMedicionIncertidumbre;
    private String incertidumbreMedicionValor;
    private Tipos incertidumbreMedicionPrefijo;//
    private Tipos incertidumbreMedicionUnidad;//
    private String incertidumbreMedicionSimbolo;//
    private String factorCoberturaK;//
    private String normaDocumentosMetodosReferencia;//
    private Boolean acreditado;
    private String entidadAcreditadoraTipoDoc;
    private String entidadAcreditadoraNumDoc;
    private String entidadAcreditadoraNombre;
    private Date fechaRegistro;
	
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
	public String getDescripcionMensurando() {
		return descripcionMensurando;
	}
	public void setDescripcionMensurando(String descripcionMensurando) {
		this.descripcionMensurando = descripcionMensurando;
	}
	public Tipos getMagnitudFisica() {
		return magnitudFisica;
	}
	public void setMagnitudFisica(Tipos magnitudFisica) {
		this.magnitudFisica = magnitudFisica;
	}
	public Tipos getCampoAplicacion() {
		return campoAplicacion;
	}
	public void setCampoAplicacion(Tipos campoAplicacion) {
		this.campoAplicacion = campoAplicacion;
	}
	public String getIntervaloPuntoMedMinimo() {
		return intervaloPuntoMedMinimo;
	}
	public void setIntervaloPuntoMedMinimo(String intervaloPuntoMedMinimo) {
		this.intervaloPuntoMedMinimo = intervaloPuntoMedMinimo;
	}
	public String getIntervaloPuntoMedMaximo() {
		return intervaloPuntoMedMaximo;
	}
	public void setIntervaloPuntoMedMaximo(String intervaloPuntoMedMaximo) {
		this.intervaloPuntoMedMaximo = intervaloPuntoMedMaximo;
	}
	public Tipos getUnidadesIntervaloPrefijo() {
		return unidadesIntervaloPrefijo;
	}
	public void setUnidadesIntervaloPrefijo(Tipos unidadesIntervaloPrefijo) {
		this.unidadesIntervaloPrefijo = unidadesIntervaloPrefijo;
	}
	public Tipos getUnidadesIntervaloUnidad() {
		return unidadesIntervaloUnidad;
	}
	public void setUnidadesIntervaloUnidad(Tipos unidadesIntervaloUnidad) {
		this.unidadesIntervaloUnidad = unidadesIntervaloUnidad;
	}
	public String getUnidadesIntervaloSimbolo() {
		return unidadesIntervaloSimbolo;
	}
	public void setUnidadesIntervaloSimbolo(String unidadesIntervaloSimbolo) {
		this.unidadesIntervaloSimbolo = unidadesIntervaloSimbolo;
	}
	public Tipos getIncertidumbreMedicionIncertidumbre() {
		return incertidumbreMedicionIncertidumbre;
	}
	public void setIncertidumbreMedicionIncertidumbre(Tipos incertidumbreMedicionIncertidumbre) {
		this.incertidumbreMedicionIncertidumbre = incertidumbreMedicionIncertidumbre;
	}
	public String getIncertidumbreMedicionValor() {
		return incertidumbreMedicionValor;
	}
	public void setIncertidumbreMedicionValor(String incertidumbreMedicionValor) {
		this.incertidumbreMedicionValor = incertidumbreMedicionValor;
	}
	public Tipos getIncertidumbreMedicionPrefijo() {
		return incertidumbreMedicionPrefijo;
	}
	public void setIncertidumbreMedicionPrefijo(Tipos incertidumbreMedicionPrefijo) {
		this.incertidumbreMedicionPrefijo = incertidumbreMedicionPrefijo;
	}
	public Tipos getIncertidumbreMedicionUnidad() {
		return incertidumbreMedicionUnidad;
	}
	public void setIncertidumbreMedicionUnidad(Tipos incertidumbreMedicionUnidad) {
		this.incertidumbreMedicionUnidad = incertidumbreMedicionUnidad;
	}
	public String getIncertidumbreMedicionSimbolo() {
		return incertidumbreMedicionSimbolo;
	}
	public void setIncertidumbreMedicionSimbolo(String incertidumbreMedicionSimbolo) {
		this.incertidumbreMedicionSimbolo = incertidumbreMedicionSimbolo;
	}
	public String getFactorCoberturaK() {
		return factorCoberturaK;
	}
	public void setFactorCoberturaK(String factorCoberturaK) {
		this.factorCoberturaK = factorCoberturaK;
	}
	public String getNormaDocumentosMetodosReferencia() {
		return normaDocumentosMetodosReferencia;
	}
	public void setNormaDocumentosMetodosReferencia(String normaDocumentosMetodosReferencia) {
		this.normaDocumentosMetodosReferencia = normaDocumentosMetodosReferencia;
	}
	public Boolean getAcreditado() {
		return acreditado;
	}
	public void setAcreditado(Boolean acreditado) {
		this.acreditado = acreditado;
	}
	public String getEntidadAcreditadoraTipoDoc() {
		return entidadAcreditadoraTipoDoc;
	}
	public void setEntidadAcreditadoraTipoDoc(String entidadAcreditadoraTipoDoc) {
		this.entidadAcreditadoraTipoDoc = entidadAcreditadoraTipoDoc;
	}
	public String getEntidadAcreditadoraNumDoc() {
		return entidadAcreditadoraNumDoc;
	}
	public void setEntidadAcreditadoraNumDoc(String entidadAcreditadoraNumDoc) {
		this.entidadAcreditadoraNumDoc = entidadAcreditadoraNumDoc;
	}
	public String getEntidadAcreditadoraNombre() {
		return entidadAcreditadoraNombre;
	}
	public void setEntidadAcreditadoraNombre(String entidadAcreditadoraNombre) {
		this.entidadAcreditadoraNombre = entidadAcreditadoraNombre;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
}
