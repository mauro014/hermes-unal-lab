/*
 * Created on 21-jun-2011
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Ing. Wilver Alexander Martinez Martinez -wam²
 */
public class ConvocatoriaRevista implements Serializable{


	//INFORMACIÓN GENERAL
	private Long id;
	private String nombreRevista;
	private String editor;
	
	private String issn;
	private String dependencia;
	
	private Long numeroAbstract;
	private Long numeroArticulos;
 	private Long numeroPromedioCuartillas;
 	private String revisor;
  	private Long precioCuartilla;
  	private Long precioRevision;
 	private Long cantidadEdiciones;
 	private Long precioTotal;
 	
 	private Date fechaSolicitud;
 	
 	private String perDocumento; 
 	private String tipoDocumento;     

	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreRevista() {
		return nombreRevista;
	}

	public void setNombreRevista(String nombreRevista) {
		this.nombreRevista = nombreRevista;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	public String getDependencia() {
		return dependencia;
	}

	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}

	public Long getNumeroAbstract() {
		return numeroAbstract;
	}

	public void setNumeroAbstract(Long numeroAbstract) {
		this.numeroAbstract = numeroAbstract;
	}

	public Long getNumeroArticulos() {
		return numeroArticulos;
	}

	public void setNumeroArticulos(Long numeroArticulos) {
		this.numeroArticulos = numeroArticulos;
	}

	public Long getNumeroPromedioCuartillas() {
		return numeroPromedioCuartillas;
	}

	public void setNumeroPromedioCuartillas(Long numeroPromedioCuartillas) {
		this.numeroPromedioCuartillas = numeroPromedioCuartillas;
	}

	public String getRevisor() {
		return revisor;
	}

	public void setRevisor(String revisor) {
		this.revisor = revisor;
	}

	public Long getPrecioCuartilla() {
		return precioCuartilla;
	}

	public void setPrecioCuartilla(Long precioCuartilla) {
		this.precioCuartilla = precioCuartilla;
	}

	public Long getPrecioRevision() {
		return precioRevision;
	}

	public void setPrecioRevision(Long precioRevision) {
		this.precioRevision = precioRevision;
	}

	public Long getCantidadEdiciones() {
		return cantidadEdiciones;
	}

	public void setCantidadEdiciones(Long cantidadEdiciones) {
		this.cantidadEdiciones = cantidadEdiciones;
	}

	public Long getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(Long precioTotal) {
		this.precioTotal = precioTotal;
	}

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public String getPerDocumento() {
		return perDocumento;
	}

	public void setPerDocumento(String perDocumento) {
		this.perDocumento = perDocumento;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}



	
}
