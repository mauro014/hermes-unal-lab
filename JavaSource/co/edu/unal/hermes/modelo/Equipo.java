/*
 * Created on 24-abr-2006
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package co.edu.unal.hermes.modelo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jassar David Issa Co
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Equipo {

		private	String	id;
		private	String	genero;
		private	String	marca;
		private	String	modelo;
		private	String	numSerieFabrica;
		private String	nombreProveedor;
		private	String	nombreGeneradorLicencia;
		
		private	String	mantenimientoAmbito;
		private String	mantenimientoTipo;
		private	String  observaciones;
		
		private	String	mantenimientoFrecuencia;
		private Long	duracionGarantia;
		private	Double	valorCompra;
		private	Double	valorTRM;
		private Date 	fechaCompra;
		private	String	numSalon;
		
		private String  nombreFuncionarioResponsable;
		private String  idFuncionarioResponsable;
		private String  telefonoFuncionarioResponsable;
		private String  emailFuncionarioResponsable; 
		private String  cargoFuncionarioResponsable;
		private String  dependenciaFuncionarioResponsable;
		private String 	extencionFuncionarioResponsable;
		private String  faxFuncionarioResponsable;
		private String  numDocumentoDocenteResponsable;
		private String  tipoDocumentoDocenteResponsable;//se trabajará así, en vez de jun objeto investigador, ya que por razonez de falla o deconociemitno de hibernet, no se logró hacer quer funcionara con el objeto investigador, com foránea de la tabla Equipo
		private String  nombreDocenteResponsable;
		private Set tipos;
		private Tipos requiereMantenimientoEspecializado;
		private Tipos tipoMantenimientoEspecializado;
		private String  tipoDocumentoFuncionarioResponsable;
		private Date fechaVencimientoGarantia;
		
		
		//relaciones
		 EquipoTipo equipoTipo;
		 Moneda moneda;
		 Sede sede=new Sede();
		 Edificio edificio;
		 Proyecto proyecto;
		 EquipoCondicion equipoCondicion;
		    
	
		
		 private Set equipoUsuarios = new HashSet();
	
		 private Set documentacion= new HashSet();  //set de objetos DocumentDeEquipo
		
	
		 public void adicionarUsuario(EquipoUsuarios usuarioEquipo)
		 {
		 	usuarioEquipo.setEquipo(this);
		 	equipoUsuarios.add(usuarioEquipo);
		 }
		 public void borrarUsuario(EquipoUsuarios usuarioEquipo) {        
		 	equipoUsuarios.remove(usuarioEquipo);
	    }
		 
		 
		 
		public Long getDuracionGarantia() {
			return duracionGarantia;
		}
		public void setDuracionGarantia(Long duracionGarantia) {
			this.duracionGarantia = duracionGarantia;
		}
		public Date getFechaCompra() {
			return fechaCompra;
		}
		public void setFechaCompra(Date fechaCompra) {
			this.fechaCompra = fechaCompra;
		}
		public String getGenero() {
			return genero;
		}
		public void setGenero(String genero) {
			this.genero = genero;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		
		
		public String getMantenimientoTipo() {
			return mantenimientoTipo;
		}
		public void setMantenimientoTipo(String mantenimientoTipo) {
			this.mantenimientoTipo = mantenimientoTipo;
		}
		public String getMarca() {
			return marca;
		}
		public void setMarca(String marca) {
			this.marca = marca;
		}
		public String getModelo() {
			return modelo;
		}
		public void setModelo(String modelo) {
			this.modelo = modelo;
		}
		public String getNombreGeneradorLicencia() {
			return nombreGeneradorLicencia;
		}
		public void setNombreGeneradorLicencia(String nombreGeneradorLicencia) {
			this.nombreGeneradorLicencia = nombreGeneradorLicencia;
		}
		public String getNombreProveedor() {
			return nombreProveedor;
		}
		public void setNombreProveedor(String nombreProveedor) {
			this.nombreProveedor = nombreProveedor;
		}
		public String getNumSerieFabrica() {
			return numSerieFabrica;
		}
		public void setNumSerieFabrica(String numSerieFabrica) {
			this.numSerieFabrica = numSerieFabrica;
		}
	
		public Double getValorCompra() {
			return valorCompra;
		}
		public void setValorCompra(Double valorCompra) {
			this.valorCompra = valorCompra;
		}
		public Double getValorTRM() {
			return valorTRM;
		}
		public void setValorTRM(Double valorTRM) {
			this.valorTRM = valorTRM;
		}
		public Edificio getEdificio() {
			return edificio;
		}
		public void setEdificio(Edificio edificio) {
			this.edificio = edificio;
		}
		public EquipoCondicion getEquipoCondicion() {
			return equipoCondicion;
		}
		public void setEquipoCondicion(EquipoCondicion equipoCondicion) {
			this.equipoCondicion = equipoCondicion;
		}
		public EquipoTipo getEquipoTipo() {
			return equipoTipo;
		}
		public void setEquipoTipo(EquipoTipo equipoTipo) {
			this.equipoTipo = equipoTipo;
		}
		
		public Moneda getMoneda() {
			return moneda;
		}
		public void setMoneda(Moneda moneda) {
			this.moneda = moneda;
		}
		public Proyecto getProyecto() {
			return proyecto;
		}
		public void setProyecto(Proyecto proyecto) {
			this.proyecto = proyecto;
		}
		public Set getEquipoUsuarios() {
			return equipoUsuarios;
		}
		public void setEquipoUsuarios(Set equipoUsuarios) {
			this.equipoUsuarios = equipoUsuarios;
		}
		public Sede getSede() {
			return sede;
		}
		public void setSede(Sede sede) {
			this.sede = sede;
		}
		public String getNumSalon() {
			return numSalon;
		}
		public void setNumSalon(String numSalon) {
			this.numSalon = numSalon;
		}
		public String getMantenimientoFrecuencia() {
			return mantenimientoFrecuencia;
		}
		public void setMantenimientoFrecuencia(String mantenimientoFrecuencia) {
			this.mantenimientoFrecuencia = mantenimientoFrecuencia;
		}
		public String getMantenimientoAmbito() {
			return mantenimientoAmbito;
		}
		public void setMantenimientoAmbito(String mantenimientoAmbito) {
			this.mantenimientoAmbito = mantenimientoAmbito;
		}
		public String getObservaciones() {
			return observaciones;
		}
		public void setObservaciones(String observaciones) {
			this.observaciones = observaciones;
		}
		
		
		public Set getDocumentacion() {
			return documentacion;
		}
		public void setDocumentacion(Set documentacion) {
			this.documentacion = documentacion;
		}
		
		public String getEmailFuncionarioResponsable() {
			return emailFuncionarioResponsable;
		}
		public void setEmailFuncionarioResponsable(
				String emailFuncionarioResponsable) {
			this.emailFuncionarioResponsable = emailFuncionarioResponsable;
		}
		public String getIdFuncionarioResponsable() {
			return idFuncionarioResponsable;
		}
		public void setIdFuncionarioResponsable(String idFuncionarioResponsable) {
			this.idFuncionarioResponsable = idFuncionarioResponsable;
		}
		public String getNombreFuncionarioResponsable() {
			return nombreFuncionarioResponsable;
		}
		public void setNombreFuncionarioResponsable(
				String nombreFuncionarioResponsable) {
			this.nombreFuncionarioResponsable = nombreFuncionarioResponsable;
		}
		public String getTelefonoFuncionarioResponsable() {
			return telefonoFuncionarioResponsable;
		}
		public void setTelefonoFuncionarioResponsable(
				String telefonoFuncionarioResponsable) {
			this.telefonoFuncionarioResponsable = telefonoFuncionarioResponsable;
		}
		
	
		public String getCargoFuncionarioResponsable() {
			return cargoFuncionarioResponsable;
		}
		public void setCargoFuncionarioResponsable(
				String cargoFuncionarioResponsable) {
			this.cargoFuncionarioResponsable = cargoFuncionarioResponsable;
		}
		public String getDependenciaFuncionarioResponsable() {
			return dependenciaFuncionarioResponsable;
		}
		public void setDependenciaFuncionarioResponsable(
				String dependenciaFuncionarioResponsable) {
			this.dependenciaFuncionarioResponsable = dependenciaFuncionarioResponsable;
		}
	
	 
		public String getNombreDocenteResponsable() {
			return nombreDocenteResponsable;
		}
		public void setNombreDocenteResponsable(String nombreDocenteResponsable) {
			this.nombreDocenteResponsable = nombreDocenteResponsable;
		}
		public String getNumDocumentoDocenteResponsable() {
			return numDocumentoDocenteResponsable;
		}
		public void setNumDocumentoDocenteResponsable(
				String numDocumentoDocenteResponsable) {
			this.numDocumentoDocenteResponsable = numDocumentoDocenteResponsable;
		}
		public String getTipoDocumentoDocenteResponsable() {
			return tipoDocumentoDocenteResponsable;
		}
		public void setTipoDocumentoDocenteResponsable(
				String tipoDocumentoDocenteResponsable) {
			this.tipoDocumentoDocenteResponsable = tipoDocumentoDocenteResponsable;
		}
		public Set getTipos() {
			return tipos;
		}
		public void setTipos(Set tipos) {
			this.tipos = tipos;
		}
	
		
	
		public Date getFechaVencimientoGarantia() {
			return fechaVencimientoGarantia;
		}
		public void setFechaVencimientoGarantia(Date fechaVencimientoGarantia) {
			this.fechaVencimientoGarantia = fechaVencimientoGarantia;
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
		
		public String getTipoDocumentoFuncionarioResponsable() {
			return tipoDocumentoFuncionarioResponsable;
		}
		public void setTipoDocumentoFuncionarioResponsable(
				String tipoDocumentoFuncionarioResponsable) {
			this.tipoDocumentoFuncionarioResponsable = tipoDocumentoFuncionarioResponsable;
		}
		public String getExtencionFuncionarioResponsable() {
			return extencionFuncionarioResponsable;
		}
		public void setExtencionFuncionarioResponsable(
				String extencionFuncionarioResponsable) {
			this.extencionFuncionarioResponsable = extencionFuncionarioResponsable;
		}
		public String getFaxFuncionarioResponsable() {
			return faxFuncionarioResponsable;
		}
		public void setFaxFuncionarioResponsable(String faxFuncionarioResponsable) {
			this.faxFuncionarioResponsable = faxFuncionarioResponsable;
		}
}
