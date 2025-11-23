package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;

public class SemilleroInformeResultado implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private SemilleroInforme informe;
	private SemilleroResultado resultado;
	private String avanceResultado;
	private ProductoTipo producto;
	private String nombreProducto;
	private String lugarDeposito;
	private Date fechaEntrega;
	private String proteccion;
	private String tipoProducto;
	private String nombreTipoProducto;

	public String getLugarDeposito() {
		return lugarDeposito;
	}

	public void setLugarDeposito(String lugarDeposito) {
		this.lugarDeposito = lugarDeposito;
	}

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public String getProteccion() {
		return proteccion;
	}

	public void setProteccion(String proteccion) {
		this.proteccion = proteccion;
	}

	public String getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(String tipoProducto) {
		this.tipoProducto = tipoProducto;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public SemilleroInformeResultado() {
		informe = new SemilleroInforme();
		resultado = new SemilleroResultado();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SemilleroInforme getInforme() {
		return informe;
	}

	public void setInforme(SemilleroInforme informe) {
		this.informe = informe;
	}

	public SemilleroResultado getResultado() {
		return resultado;
	}

	public void setResultado(SemilleroResultado resultado) {
		this.resultado = resultado;
	}

	public String getAvanceResultado() {
		return avanceResultado;
	}

	public void setAvanceResultado(String avanceResultado) {
		this.avanceResultado = avanceResultado;
	}

	public String getNombreTipoProducto() {
		return nombreTipoProducto;
	}

	public void setNombreTipoProducto(String nombreTipoProducto) {
		this.nombreTipoProducto = nombreTipoProducto;
	}

	public ProductoTipo getProducto() {
		return producto;
	}

	public void setProducto(ProductoTipo producto) {
		this.producto = producto;
	}
}