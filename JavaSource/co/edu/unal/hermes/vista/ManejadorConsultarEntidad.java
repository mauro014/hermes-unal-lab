package co.edu.unal.hermes.vista;

import co.edu.unal.hermes.modelo.FuenteFinanciacion;

public class ManejadorConsultarEntidad extends ManejadorBase{
	
    protected String id;
    protected String descripcion;
    protected String internaExterna;
    protected String id_sige;
    protected String nit;
    protected String direccion;
    protected String telefono;
    protected String fax;
    protected String pais;
    protected String departamento;
    protected String ciudad;
    protected String pagweb;
    protected String naturaleza;
    protected String sector;
    protected String nvlterritorial;
    protected String estado;
    protected String est_entidad;
    protected String reg_completo;
    protected String fec_registro;
    protected String fec_solicitud;
    protected String est_solicitud;
    protected String fec_respuesta;
    protected String observaciones;
	protected boolean mostrarDeptoCiud;
	protected boolean cargarNvlT;
	protected FuenteFinanciacion entidad;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getInternaExterna() {
		return internaExterna;
	}
	public void setInternaExterna(String internaExterna) {
		this.internaExterna = internaExterna;
	}
	public String getId_sige() {
		return id_sige;
	}
	public void setId_sige(String id_sige) {
		this.id_sige = id_sige;
	}
	public String getNit() {
		return nit;
	}
	public void setNit(String nit) {
		this.nit = nit;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getPagweb() {
		return pagweb;
	}
	public void setPagweb(String pagweb) {
		this.pagweb = pagweb;
	}
	public String getNaturaleza() {
		return naturaleza;
	}
	public void setNaturaleza(String naturaleza) {
		this.naturaleza = naturaleza;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public String getNvlterritorial() {
		return nvlterritorial;
	}
	public void setNvlterritorial(String nvlterritorial) {
		this.nvlterritorial = nvlterritorial;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getEst_entidad() {
		return est_entidad;
	}
	public void setEst_entidad(String est_entidad) {
		this.est_entidad = est_entidad;
	}
	public String getReg_completo() {
		return reg_completo;
	}
	public void setReg_completo(String reg_completo) {
		this.reg_completo = reg_completo;
	}
	public String getFec_registro() {
		return fec_registro;
	}
	public void setFec_registro(String fec_registro) {
		this.fec_registro = fec_registro;
	}
	public String getFec_solicitud() {
		return fec_solicitud;
	}
	public void setFec_solicitud(String fec_solicitud) {
		this.fec_solicitud = fec_solicitud;
	}
	public String getEst_solicitud() {
		return est_solicitud;
	}
	public void setEst_solicitud(String est_solicitud) {
		this.est_solicitud = est_solicitud;
	}
	public String getFec_respuesta() {
		return fec_respuesta;
	}
	public void setFec_respuesta(String fec_respuesta) {
		this.fec_respuesta = fec_respuesta;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public boolean isMostrarDeptoCiud() {
		return mostrarDeptoCiud;
	}
	public void setMostrarDeptoCiud(boolean mostrarDeptoCiud) {
		this.mostrarDeptoCiud = mostrarDeptoCiud;
	}
	public boolean isCargarNvlT() {
		return cargarNvlT;
	}
	public void setCargarNvlT(boolean cargarNvlT) {
		this.cargarNvlT = cargarNvlT;
	}
	public FuenteFinanciacion getEntidad() {
		return entidad;
	}
	public void setEntidad(FuenteFinanciacion entidad) {
		this.entidad = entidad;
	}
	
	public ManejadorConsultarEntidad(){		
		entidad = new FuenteFinanciacion();										
	}
	
	public void cargarEntidad(){
		
	}

}
