package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;

public class Bien implements Serializable, Comparable<Bien> {

	private static final long serialVersionUID = 3733166989170424110L;
	private Long id;
	private String placa;
	private String equipo;
	private String marca;
	private String modelo;
	private String serial;
	private String descripcion;
	private Long valor;
	private String idUbicacion;
	private String ubicacion;
	private Integer idResponsable;
	private String responsable;
	private Integer idEstado;
	private String estado;
	private ProyectoInforme informe;
	private Integer idEstadoFisico;
	private String estadoFisico;
	private Date fechaServicio;
	private Date fechaAdquisicion;
	private String sedeEquipoInventarios;

	public Bien(Long id) {
		this.id = id;
		placa = "";
		descripcion = "";
		ubicacion = "";
		responsable = "";
		valor = 0L;
		informe = new ProyectoInforme();
	}

	public Bien() {
		this(null);
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public Long getValor() {
		return valor;
	}

	public void setValor(Long valor) {
		this.valor = valor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProyectoInforme getInforme() {
		return informe;
	}

	public void setInforme(ProyectoInforme informe) {
		this.informe = informe;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getIdUbicacion() {
		return idUbicacion;
	}

	public void setIdUbicacion(String idUbicacion) {
		this.idUbicacion = idUbicacion;
	}

	public Integer getIdResponsable() {
		return idResponsable;
	}

	public void setIdResponsable(Integer idResponsable) {
		this.idResponsable = idResponsable;
	}

	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return placa + " " + descripcion;
	}

	public int compareTo(Bien otroBien) {
		return placa.compareTo(otroBien.getPlaca());
	}

	/**
	 * @return the idEstadoFisico
	 */
	public Integer getIdEstadoFisico() {
		return idEstadoFisico;
	}

	/**
	 * @param idEstadoFisico the idEstadoFisico to set
	 */
	public void setIdEstadoFisico(Integer idEstadoFisico) {
		this.idEstadoFisico = idEstadoFisico;
	}

	/**
	 * @return the estadoFisico
	 */
	public String getEstadoFisico() {
		return estadoFisico;
	}

	/**
	 * @param estadoFisico the estadoFisico to set
	 */
	public void setEstadoFisico(String estadoFisico) {
		this.estadoFisico = estadoFisico;
	}

	/**
	 * @return the fechaServicio
	 */
	public Date getFechaServicio() {
		return fechaServicio;
	}

	/**
	 * @param fechaServicio the fechaServicio to set
	 */
	public void setFechaServicio(Date fechaServicio) {
		this.fechaServicio = fechaServicio;
	}

	/**
	 * @return the fechaAdquisicion
	 */
	public Date getFechaAdquisicion() {
		return fechaAdquisicion;
	}

	/**
	 * @param fechaAdquisicion the fechaAdquisicion to set
	 */
	public void setFechaAdquisicion(Date fechaAdquisicion) {
		this.fechaAdquisicion = fechaAdquisicion;
	}

	public String getEquipo() {
		return equipo;
	}

	public void setEquipo(String equipo) {
		this.equipo = equipo;
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

	public String getSedeEquipoInventarios() {
		return sedeEquipoInventarios;
	}

	public void setSedeEquipoInventarios(String sedeEquipoInventarios) {
		this.sedeEquipoInventarios = sedeEquipoInventarios;
	}

}
