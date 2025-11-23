
package co.edu.unal.hermes.modelo.laboratorios;

import java.util.Date;

import co.edu.unal.hermes.modelo.Tipos;

public class LaboratorioMetroMedidaQuimica{
        
    private Long id;  
    private Laboratorio laboratorio;
    private Tipos sector;
    private Tipos matriz;
    private Tipos mensurando;
    private Tipos tecnica;
    private Tipos concentracionintervaloMedicion;
    private String metodoValidado;
    private String metodoValidadoDesc;
    private Boolean materialesReferencia;
    private String normaMetodoReferencia;
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
	public Tipos getMensurando() {
		return mensurando;
	}
	public void setMensurando(Tipos mensurando) {
		this.mensurando = mensurando;
	}
	public Tipos getTecnica() {
		return tecnica;
	}
	public void setTecnica(Tipos tecnica) {
		this.tecnica = tecnica;
	}
	public Tipos getConcentracionintervaloMedicion() {
		return concentracionintervaloMedicion;
	}
	public void setConcentracionintervaloMedicion(Tipos concentracionintervaloMedicion) {
		this.concentracionintervaloMedicion = concentracionintervaloMedicion;
	}
	public Boolean getMaterialesReferencia() {
		return materialesReferencia;
	}
	public void setMaterialesReferencia(Boolean materialesReferencia) {
		this.materialesReferencia = materialesReferencia;
	}
	public String getNormaMetodoReferencia() {
		return normaMetodoReferencia;
	}
	public void setNormaMetodoReferencia(String normaMetodoReferencia) {
		this.normaMetodoReferencia = normaMetodoReferencia;
	}
	public String getMetodoValidado() {
		return metodoValidado;
	}
	public void setMetodoValidado(String metodoValidado) {
		this.metodoValidado = metodoValidado;
	}
	public String getMetodoValidadoDesc() {
		return metodoValidadoDesc;
	}
	public void setMetodoValidadoDesc(String metodoValidadoDesc) {
		this.metodoValidadoDesc = metodoValidadoDesc;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
}
