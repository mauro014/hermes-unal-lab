package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;


public class PermisoInformeAnla implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private int numeroInformeAnla;
    private String codigoInformeAnla;
    private Date fechaEnvioInformeAnla;
    private int numeroOficioVri;
    private String eliminado;
    private Date fechaEliminado;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getNumeroInformeAnla() {
		return numeroInformeAnla;
	}
	public void setNumeroInformeAnla(int numeroInformeAnla) {
		this.numeroInformeAnla = numeroInformeAnla;
	}
	public String getCodigoInformeAnla() {
		return codigoInformeAnla;
	}
	public void setCodigoInformeAnla(String codigoInformeAnla) {
		this.codigoInformeAnla = codigoInformeAnla;
	}
	public Date getFechaEnvioInformeAnla() {
		return fechaEnvioInformeAnla;
	}
	public void setFechaEnvioInformeAnla(Date fechaEnvioInformeAnla) {
		this.fechaEnvioInformeAnla = fechaEnvioInformeAnla;
	}
	public int getNumeroOficioVri() {
		return numeroOficioVri;
	}
	public void setNumeroOficioVri(int numeroOficioVri) {
		this.numeroOficioVri = numeroOficioVri;
	}
	public String getEliminado() {
		return eliminado;
	}
	public void setEliminado(String eliminado) {
		this.eliminado = eliminado;
	}
	public Date getFechaEliminado() {
		return fechaEliminado;
	}
	public void setFechaEliminado(Date fechaEliminado) {
		this.fechaEliminado = fechaEliminado;
	}
	

}
