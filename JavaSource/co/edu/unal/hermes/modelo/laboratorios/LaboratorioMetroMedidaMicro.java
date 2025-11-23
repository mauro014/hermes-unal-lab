
package co.edu.unal.hermes.modelo.laboratorios;

import java.util.Date;

import co.edu.unal.hermes.modelo.Tipos;

public class LaboratorioMetroMedidaMicro{
        
    private Long id;  
    private Laboratorio laboratorio;
    private String materialReferenciaCepaUtilizada;
    private Tipos sector;
    private Tipos matriz;
    private Tipos tipoEnsayo;
    private Tipos nombreEnsayo;
    private Tipos tecnica;
    private Tipos normaTecnica;
    private Tipos materialReferenciaCepaCertificada;
    private String validacion;
    private String validacionDesc;
    private String controCalidadMediosCultivo;
    private String controCalidadMediosCultivoDesc;
    private String condicionesAmbientales;
    private String condicionesAmbientalesDesc;
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
	public String getMaterialReferenciaCepaUtilizada() {
		return materialReferenciaCepaUtilizada;
	}
	public void setMaterialReferenciaCepaUtilizada(String materialReferenciaCepaUtilizada) {
		this.materialReferenciaCepaUtilizada = materialReferenciaCepaUtilizada;
	}
	public Tipos getSector() {
		return sector;
	}
	public void setSector(Tipos sector) {
		this.sector = sector;
	}
	public Tipos getMatriz() {
		return matriz;
	}
	public void setMatriz(Tipos matriz) {
		this.matriz = matriz;
	}
	public Tipos getTipoEnsayo() {
		return tipoEnsayo;
	}
	public void setTipoEnsayo(Tipos tipoEnsayo) {
		this.tipoEnsayo = tipoEnsayo;
	}
	public Tipos getNombreEnsayo() {
		return nombreEnsayo;
	}
	public void setNombreEnsayo(Tipos nombreEnsayo) {
		this.nombreEnsayo = nombreEnsayo;
	}
	public Tipos getTecnica() {
		return tecnica;
	}
	public void setTecnica(Tipos tecnica) {
		this.tecnica = tecnica;
	}
	public Tipos getNormaTecnica() {
		return normaTecnica;
	}
	public void setNormaTecnica(Tipos normaTecnica) {
		this.normaTecnica = normaTecnica;
	}
	public Tipos getMaterialReferenciaCepaCertificada() {
		return materialReferenciaCepaCertificada;
	}
	public void setMaterialReferenciaCepaCertificada(Tipos materialReferenciaCepaCertificada) {
		this.materialReferenciaCepaCertificada = materialReferenciaCepaCertificada;
	}
	public String getValidacion() {
		return validacion;
	}
	public void setValidacion(String validacion) {
		this.validacion = validacion;
	}
	public String getControCalidadMediosCultivo() {
		return controCalidadMediosCultivo;
	}
	public void setControCalidadMediosCultivo(String controCalidadMediosCultivo) {
		this.controCalidadMediosCultivo = controCalidadMediosCultivo;
	}
	public String getCondicionesAmbientales() {
		return condicionesAmbientales;
	}
	public void setCondicionesAmbientales(String condicionesAmbientales) {
		this.condicionesAmbientales = condicionesAmbientales;
	}
	public String getValidacionDesc() {
		return validacionDesc;
	}
	public void setValidacionDesc(String validacionDesc) {
		this.validacionDesc = validacionDesc;
	}
	public String getControCalidadMediosCultivoDesc() {
		return controCalidadMediosCultivoDesc;
	}
	public void setControCalidadMediosCultivoDesc(String controCalidadMediosCultivoDesc) {
		this.controCalidadMediosCultivoDesc = controCalidadMediosCultivoDesc;
	}
	public String getCondicionesAmbientalesDesc() {
		return condicionesAmbientalesDesc;
	}
	public void setCondicionesAmbientalesDesc(String condicionesAmbientalesDesc) {
		this.condicionesAmbientalesDesc = condicionesAmbientalesDesc;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
}
