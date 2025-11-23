package co.edu.unal.hermes.modelo;

import java.util.Date;
import java.util.Set;

public class Equipo2 {

	private Long id;
	private String nombreEquipo;
	private String modelo;
	private String marca;
	private String numeroSerie;
	private String placa;
	private Date fechaCompra;
	private String proveedor;
	private Tipos poseeGarantia;
	private Date fechaVencimientoGarantia;
	private Tipos poseeLicenciaFuncionamiento;
	private String licenciaExpedidaPor;
	private Double valorCompra;
	private Tipos monedaExtranjera;
	private TipoDocumento tipoDocumentoFuncionario;
	private String documentoFuncionarioAsignado;
	private String nombreFuncionarioAsignado;
	private String cargoFuncionarioAsignado;
	private String telefonoFuncionarioAsignado;
	private String extencionFuncionarioAsignado;
	private String faxFuncionarioAsignado;
	private Sede sedeFuncionarioAsignado;
	private Edificio edificioFuncionarioAsignado;
	private String salonFuncionarioAsignado;
	private String laboratorioFuncionarioAsignado;
	private Tipos poseeDocumentacion;
	private Tipos estadoEquipo;
	private Tipos tipoMantenimiento;
	private Double numeroUsuariosPregrado;
	private Double numeroUsuariosMaestria;
	private Double numeroUsuariosDoctorado;
	private Tipos frecuenciaMantenimiento;
	private Tipos requiereMantenimientoEspecializado;
	private Tipos tipoMantenimientoEspecializado;
	private TipoDocumento tipoDocumentoPersonaMantenimiento;
	private String documentoPersonaMantenimiento;
	private Dependencia dependenciaMantenimiento;
	private String observacionesGenerales;
	private Proyecto proyecto;
	private Set tiposEquipo;
	private Set documentacionEquipo;
	
	public Set getDocumentacionEquipo() {
		return documentacionEquipo;
	}
	public void setDocumentacionEquipo(Set documentacionEquipo) {
		this.documentacionEquipo = documentacionEquipo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombreEquipo() {
		return nombreEquipo;
	}
	public void setNombreEquipo(String nombreEquipo) {
		this.nombreEquipo = nombreEquipo;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getNumeroSerie() {
		return numeroSerie;
	}
	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public Date getFechaCompra() {
		return fechaCompra;
	}
	public void setFechaCompra(Date fechaCompra) {
		this.fechaCompra = fechaCompra;
	}
	public String getProveedor() {
		return proveedor;
	}
	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}
	public Tipos getPoseeGarantia() {
		return poseeGarantia;
	}
	public void setPoseeGarantia(Tipos poseeGarantia) {
		this.poseeGarantia = poseeGarantia;
	}
	public Date getFechaVencimientoGarantia() {
		return fechaVencimientoGarantia;
	}
	public void setFechaVencimientoGarantia(Date fechaVencimientoGarantia) {
		this.fechaVencimientoGarantia = fechaVencimientoGarantia;
	}
	public Tipos getPoseeLicenciaFuncionamiento() {
		return poseeLicenciaFuncionamiento;
	}
	public void setPoseeLicenciaFuncionamiento(Tipos poseeLicenciaFuncionamiento) {
		this.poseeLicenciaFuncionamiento = poseeLicenciaFuncionamiento;
	}
	public String getLicenciaExpedidaPor() {
		return licenciaExpedidaPor;
	}
	public void setLicenciaExpedidaPor(String licenciaExpedidaPor) {
		this.licenciaExpedidaPor = licenciaExpedidaPor;
	}
	public Double getValorCompra() {
		return valorCompra;
	}
	public void setValorCompra(Double valorCompra) {
		this.valorCompra = valorCompra;
	}
	public Tipos getMonedaExtranjera() {
		return monedaExtranjera;
	}
	public void setMonedaExtranjera(Tipos monedaExtranjera) {
		this.monedaExtranjera = monedaExtranjera;
	}
	public TipoDocumento getTipoDocumentoFuncionario() {
		return tipoDocumentoFuncionario;
	}
	public void setTipoDocumentoFuncionario(TipoDocumento tipoDocumentoFuncionario) {
		this.tipoDocumentoFuncionario = tipoDocumentoFuncionario;
	}
	public String getDocumentoFuncionarioAsignado() {
		return documentoFuncionarioAsignado;
	}
	public void setDocumentoFuncionarioAsignado(String documentoFuncionarioAsignado) {
		this.documentoFuncionarioAsignado = documentoFuncionarioAsignado;
	}
	public String getNombreFuncionarioAsignado() {
		return nombreFuncionarioAsignado;
	}
	public void setNombreFuncionarioAsignado(String nombreFuncionarioAsignado) {
		this.nombreFuncionarioAsignado = nombreFuncionarioAsignado;
	}
	public String getCargoFuncionarioAsignado() {
		return cargoFuncionarioAsignado;
	}
	public void setCargoFuncionarioAsignado(String cargoFuncionarioAsignado) {
		this.cargoFuncionarioAsignado = cargoFuncionarioAsignado;
	}
	public String getTelefonoFuncionarioAsignado() {
		return telefonoFuncionarioAsignado;
	}
	public void setTelefonoFuncionarioAsignado(String telefonoFuncionarioAsignado) {
		this.telefonoFuncionarioAsignado = telefonoFuncionarioAsignado;
	}
	public String getExtencionFuncionarioAsignado() {
		return extencionFuncionarioAsignado;
	}
	public void setExtencionFuncionarioAsignado(String extencionFuncionarioAsignado) {
		this.extencionFuncionarioAsignado = extencionFuncionarioAsignado;
	}
	public String getFaxFuncionarioAsignado() {
		return faxFuncionarioAsignado;
	}
	public void setFaxFuncionarioAsignado(String faxFuncionarioAsignado) {
		this.faxFuncionarioAsignado = faxFuncionarioAsignado;
	}
	public Sede getSedeFuncionarioAsignado() {
		return sedeFuncionarioAsignado;
	}
	public void setSedeFuncionarioAsignado(Sede sedeFuncionarioAsignado) {
		this.sedeFuncionarioAsignado = sedeFuncionarioAsignado;
	}
	public Edificio getEdificioFuncionarioAsignado() {
		return edificioFuncionarioAsignado;
	}
	public void setEdificioFuncionarioAsignado(Edificio edificioFuncionarioAsignado) {
		this.edificioFuncionarioAsignado = edificioFuncionarioAsignado;
	}
	public String getSalonFuncionarioAsignado() {
		return salonFuncionarioAsignado;
	}
	public void setSalonFuncionarioAsignado(String salonFuncionarioAsignado) {
		this.salonFuncionarioAsignado = salonFuncionarioAsignado;
	}
	public String getLaboratorioFuncionarioAsignado() {
		return laboratorioFuncionarioAsignado;
	}
	public void setLaboratorioFuncionarioAsignado(
			String laboratorioFuncionarioAsignado) {
		this.laboratorioFuncionarioAsignado = laboratorioFuncionarioAsignado;
	}
	public Tipos getPoseeDocumentacion() {
		return poseeDocumentacion;
	}
	public void setPoseeDocumentacion(Tipos poseeDocumentacion) {
		this.poseeDocumentacion = poseeDocumentacion;
	}
	public Tipos getEstadoEquipo() {
		return estadoEquipo;
	}
	public void setEstadoEquipo(Tipos estadoEquipo) {
		this.estadoEquipo = estadoEquipo;
	}
	public Tipos getTipoMantenimiento() {
		return tipoMantenimiento;
	}
	public void setTipoMantenimiento(Tipos tipoMantenimiento) {
		this.tipoMantenimiento = tipoMantenimiento;
	}
	public Double getNumeroUsuariosPregrado() {
		return numeroUsuariosPregrado;
	}
	public void setNumeroUsuariosPregrado(Double numeroUsuariosPregrado) {
		this.numeroUsuariosPregrado = numeroUsuariosPregrado;
	}
	public Double getNumeroUsuariosMaestria() {
		return numeroUsuariosMaestria;
	}
	public void setNumeroUsuariosMaestria(Double numeroUsuariosMaestria) {
		this.numeroUsuariosMaestria = numeroUsuariosMaestria;
	}
	public Double getNumeroUsuariosDoctorado() {
		return numeroUsuariosDoctorado;
	}
	public void setNumeroUsuariosDoctorado(Double numeroUsuariosDoctorado) {
		this.numeroUsuariosDoctorado = numeroUsuariosDoctorado;
	}
	public Tipos getFrecuenciaMantenimiento() {
		return frecuenciaMantenimiento;
	}
	public void setFrecuenciaMantenimiento(
			Tipos frecuenciaMantenimiento) {
		this.frecuenciaMantenimiento= frecuenciaMantenimiento;
	}
	public Tipos getRequiereMantenimientoEspecializado() {
		return requiereMantenimientoEspecializado;
	}
	public void setRequiereMantenimientoEspecializado(
			Tipos requiereMantenimientoEspecializado) {
		this.requiereMantenimientoEspecializado = requiereMantenimientoEspecializado;
	}
	public Tipos getTipoMantenimientoEspecializado() {
		return tipoMantenimientoEspecializado;
	}
	public void setTipoMantenimientoEspecializado(
			Tipos tipoMantenimientoEspecializado) {
		this.tipoMantenimientoEspecializado = tipoMantenimientoEspecializado;
	}
	public TipoDocumento getTipoDocumentoPersonaMantenimiento() {
		return tipoDocumentoPersonaMantenimiento;
	}
	public void setTipoDocumentoPersonaMantenimiento(
			TipoDocumento tipoDocumentoPersonaMantenimiento) {
		this.tipoDocumentoPersonaMantenimiento= tipoDocumentoPersonaMantenimiento;
	}
	public String getDocumentoPersonaMantenimiento() {
		return documentoPersonaMantenimiento;
	}
	public void setDocumentoPersonaMantenimiento(
			String documentoPersonaMantenimiento) {
		this.documentoPersonaMantenimiento = documentoPersonaMantenimiento;
	}
	public Dependencia getDependenciaMantenimiento() {
		return dependenciaMantenimiento;
	}
	public void setDependenciaMantenimiento(Dependencia dependenciaMantenimiento) {
		this.dependenciaMantenimiento = dependenciaMantenimiento;
	}
	public String getObservacionesGenerales() {
		return observacionesGenerales;
	}
	public void setObservacionesGenerales(String observacionesGenerales) {
		this.observacionesGenerales = observacionesGenerales;
	}
	public Proyecto getProyecto() {
		return proyecto;
	}
	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
	public Set getTiposEquipo() {
		return tiposEquipo;
	}
	public void setTiposEquipo(Set tiposEquipo) {
		this.tiposEquipo = tiposEquipo;
	}
}
