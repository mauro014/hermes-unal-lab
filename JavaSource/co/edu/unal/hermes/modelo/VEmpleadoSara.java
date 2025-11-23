package co.edu.unal.hermes.modelo;

import java.sql.Date;

public class VEmpleadoSara {

	private IdPersona id;
	// private String tipoDocumento;
	// private Long documento;
	private String direccion;
	private String ciudad;//
	private String nombres;
	private String apellido;
	private String apellidos;
	private String estudios;
	private String estado;//
	private String descripcionEstado;
	// private TipoVinculacion vinculacionObj; //
	private String vinculacion;
	private String nombreVinculacion;
	private Date fIngreso;
	private String cargo;//
	private String nombreCargo;
	private String dedicacion;
	private Date fRetiro;
	private Date fFinContrato;
	private String sucursal;//
	private String nombreSucursal;
	private String unidad;
	private String nombreUnidad;
	private String zonaEconomica;
	private String nombreZona;
	private String centroCosto;
	private String nombreCentro;
	private String email;
	private String genero;
	private Date fechaNacimiento;
	private String telefono;

	public VEmpleadoSara() {
		super();
	}

	public IdPersona getId() {
		return id;
	}

	public void setId(IdPersona id) {
		this.id = id;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEstudios() {
		return estudios;
	}

	public void setEstudios(String estudios) {
		this.estudios = estudios;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getDescripcionEstado() {
		return descripcionEstado;
	}

	public void setDescripcionEstado(String descripcionEstado) {
		this.descripcionEstado = descripcionEstado;
	}

	// public TipoVinculacion getVinculacionObj() {
	// return vinculacionObj;
	// }
	//
	//
	// public void setVinculacionObj(TipoVinculacion vinculacionObj) {
	// this.vinculacionObj = vinculacionObj;
	// }

	public String getVinculacion() {
		return vinculacion;
	}

	public void setVinculacion(String vinculacion) {
		this.vinculacion = vinculacion;
	}

	public String getNombreVinculacion() {
		return nombreVinculacion;
	}

	public void setNombreVinculacion(String nombreVinculacion) {
		this.nombreVinculacion = nombreVinculacion;
	}

	public Date getfIngreso() {
		return fIngreso;
	}

	public void setfIngreso(Date fIngreso) {
		this.fIngreso = fIngreso;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getNombreCargo() {
		return nombreCargo;
	}

	public void setNombreCargo(String nombreCargo) {
		this.nombreCargo = nombreCargo;
	}

	public String getDedicacion() {
		return dedicacion;
	}

	public void setDedicacion(String dedicacion) {
		this.dedicacion = dedicacion;
	}

	public Date getfRetiro() {
		return fRetiro;
	}

	public void setfRetiro(Date fRetiro) {
		this.fRetiro = fRetiro;
	}

	public Date getfFinContrato() {
		return fFinContrato;
	}

	public void setfFinContrato(Date fFinContrato) {
		this.fFinContrato = fFinContrato;
	}

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public String getNombreSucursal() {
		return nombreSucursal;
	}

	public void setNombreSucursal(String nombreSucursal) {
		this.nombreSucursal = nombreSucursal;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public String getNombreUnidad() {
		return nombreUnidad;
	}

	public void setNombreUnidad(String nombreUnidad) {
		this.nombreUnidad = nombreUnidad;
	}

	public String getZonaEconomica() {
		return zonaEconomica;
	}

	public void setZonaEconomica(String zonaEconomica) {
		this.zonaEconomica = zonaEconomica;
	}

	public String getNombreZona() {
		return nombreZona;
	}

	public void setNombreZona(String nombreZona) {
		this.nombreZona = nombreZona;
	}

	public String getCentroCosto() {
		return centroCosto;
	}

	public void setCentroCosto(String centroCosto) {
		this.centroCosto = centroCosto;
	}

	public String getNombreCentro() {
		return nombreCentro;
	}

	public void setNombreCentro(String nombreCentro) {
		this.nombreCentro = nombreCentro;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	/**
	 * @return the fechaNacimiento
	 */
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * @param fechaNacimiento
	 *            the fechaNacimiento to set
	 */
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono
	 *            the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}