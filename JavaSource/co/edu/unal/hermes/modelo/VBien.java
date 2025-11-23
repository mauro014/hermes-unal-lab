package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class VBien implements Serializable, Comparable<VBien> {

	private static final long serialVersionUID = 3733166989170424110L;
	private Long id;
	private String placa;
	private String serial;
	private String descripcion;
	private Integer valor;
	private String idUbicacion;
	private String ubicacion;
	private Integer idResponsable;
	private String responsable;
	private Integer idEstado;
	private String estado;

	public VBien(Long id) {
		this.id = id;
		placa = "";
		descripcion = "";
		ubicacion = "";
		responsable = "";
		valor = 0;
	}

	public VBien() {
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

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public int compareTo(VBien otroBien) {
		return placa.compareTo(otroBien.getPlaca());
	}

}
