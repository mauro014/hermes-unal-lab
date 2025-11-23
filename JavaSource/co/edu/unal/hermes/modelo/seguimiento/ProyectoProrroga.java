/********************************************************************************
Autor 		: Oscar Javier Castro Medina - Consult Soft S.A.
Clase    	: co.edu.unal.hermes.modelo.seguimiento.ProyectoProrroga
Objetivo 	: Clase  POJO  para  la  tabla  HER_PROYECTO_PRORROGA, en la  cual se  
			  encuentran las prorrogas aprobadas por proyecto de investigación.
Creación	: Septiembre 04 de 2007
Modificación:
Detalle		:
********************************************************************************/

package co.edu.unal.hermes.modelo.seguimiento;

import java.io.Serializable;
import java.util.Date;

import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;

public class ProyectoProrroga implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long duracion;
	private Date fecha;
	private Proyecto proyecto;
	private Date nuevaFechaFinal;
	private Long dias;
	private Persona responsable;
	private Persona responsableEliminacion;
	private Date fechaEliminacion;
	private String estado;
	private String esPeriodoSuspension;
	private String diasVista;
	private String mesesVista;
	private Long aplicadaABiodiversidad; // 0 no, 1, si, y el id de la prórroga si es la prórooga de biodiversidad
	
	private boolean nueva = true;
	
	public static String BORRADO = "B";

	public Long getId() 
	{
		return id;
	}

	public void setId(Long id) 
	{
		this.id = id;
		this.nueva = false;
	}

	public Long getDuracion() 
	{
		if(duracion==null){
			duracion = 0L;
		}
		return duracion;
		
	}

	public void setDuracion(Long duracion) 
	{
		
		this.duracion = duracion;
	}

	public Date getFecha() 
	{
		return fecha;
	}

	public void setFecha(Date fecha) 
	{
		this.fecha = fecha;
	}

	public Proyecto getProyecto() 
	{
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) 
	{
		this.proyecto = proyecto;
	}

	public boolean isNueva() 
	{
		return nueva;
	}

	public void setNueva(boolean nueva) 
	{
		this.nueva = nueva;
	}

	public void setNuevaFechaFinal(Date nuevaFechaFinal) {
		this.nuevaFechaFinal = nuevaFechaFinal;
	}

	public Date getNuevaFechaFinal() {
		return nuevaFechaFinal;
	}

	public Long getDias() {
		if(dias==null){
			dias = 0L;
		}
		return dias;
	}

	public void setDias(Long dias) {
		this.dias = dias;
	}

	public Persona getResponsable() {
		return responsable;
	}

	public void setResponsable(Persona responsable) {
		this.responsable = responsable;
	}

	/**
	 * @return the responsableEliminacion
	 */
	public Persona getResponsableEliminacion() {
		return responsableEliminacion;
	}

	/**
	 * @param responsableEliminacion the responsableEliminacion to set
	 */
	public void setResponsableEliminacion(Persona responsableEliminacion) {
		this.responsableEliminacion = responsableEliminacion;
	}

	/**
	 * @return the fechaEliminacion
	 */
	public Date getFechaEliminacion() {
		return fechaEliminacion;
	}

	/**
	 * @param fechaEliminacion the fechaEliminacion to set
	 */
	public void setFechaEliminacion(Date fechaEliminacion) {
		this.fechaEliminacion = fechaEliminacion;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the esPeriodoSuspension
	 */
	public String getEsPeriodoSuspension() {
		return esPeriodoSuspension;
	}

	/**
	 * @param esPeriodoSuspension the esPeriodoSuspension to set
	 */
	public void setEsPeriodoSuspension(String esPeriodoSuspension) {
		this.esPeriodoSuspension = esPeriodoSuspension;
	}

	public String getDiasVista() {
		return diasVista;
	}

	public void setDiasVista(String diasVista) {
		this.diasVista = diasVista;
	}

	public String getMesesVista() {
		return mesesVista;
	}

	public void setMesesVista(String mesesVista) {
		this.mesesVista = mesesVista;
	}

	public Long getAplicadaABiodiversidad() {
		return aplicadaABiodiversidad;
	}

	public void setAplicadaABiodiversidad(Long aplicadaABiodiversidad) {
		this.aplicadaABiodiversidad = aplicadaABiodiversidad;
	}

}
