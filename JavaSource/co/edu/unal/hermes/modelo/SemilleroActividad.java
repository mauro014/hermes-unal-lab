package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SemilleroActividad implements Serializable {

	private static final long serialVersionUID = 1L;
	private String descripcion;
	private Date fechaInicio;
	private Integer duracion;
	private SemilleroIntegrante responsable;
	private boolean actividadVencida;
	private Date fechaRegistro;
	private Integer id;

	public SemilleroActividad() {
		setResponsable(new SemilleroIntegrante());
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	public String getFecha() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(getFechaInicio());
	}

	public Integer getDuracion() {
		return duracion;
	}

	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}

	public SemilleroIntegrante getResponsable() {
		return responsable;
	}

	public void setResponsable(SemilleroIntegrante responsable) {
		this.responsable = responsable;
	}
	
	public String getFechaFin() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
        c.setTime(getFechaInicio());
        c.add(Calendar.MONTH, getDuracion());
		return format.format(c.getTime());
	}

	public boolean isActividadVencida() {		
		// Get the current date
        Date today = new Date();
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
        c.setTime(getFechaInicio());
        c.add(Calendar.MONTH, getDuracion());
		

        // Create a date object to compare
        Date dateToCompare = c.getTime();

        // Compare the dates
        if (today.after(dateToCompare)) {
            System.out.println("The date is after today.");
            actividadVencida = true;
        } else if (today.equals(dateToCompare)) {
        	actividadVencida = true;
        } else {
        	actividadVencida = false;
        }
        
        return actividadVencida;
	}

	public void setActividadVencida(boolean actividadVencida) {
		this.actividadVencida = actividadVencida;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}