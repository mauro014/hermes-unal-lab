/*
 * Created on 14-dic-2005
 */
package co.edu.unal.hermes.vista.proyectos;

public class ProyectosVistaOficiosConvocatoria{
	private Long idProyecto;
	private String nombreProyecto;
	private String investigadorPrincipal;
	private String facultad;
	private String sede;    	
	private String modalidad;
	
	public ProyectosVistaOficiosConvocatoria()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the idProyecto
	 */
	public Long getIdProyecto()
	{
		return idProyecto;
	}
	/**
	 * @param idProyecto the idProyecto to set
	 */
	public void setIdProyecto(Long idProyecto)
	{
		this.idProyecto = idProyecto;
	}
	/**
	 * @return the nombreProyecto
	 */
	public String getNombreProyecto()
	{
		return nombreProyecto;
	}
	/**
	 * @param nombreProyecto the nombreProyecto to set
	 */
	public void setNombreProyecto(String nombreProyecto)
	{
		this.nombreProyecto = nombreProyecto;
	}
	/**
	 * @return the investigadorPrincipal
	 */
	public String getInvestigadorPrincipal()
	{
		return investigadorPrincipal;
	}
	/**
	 * @param investigadorPrincipal the investigadorPrincipal to set
	 */
	public void setInvestigadorPrincipal(String investigadorPrincipal)
	{
		this.investigadorPrincipal = investigadorPrincipal;
	}
	/**
	 * @return the facultad
	 */
	public String getFacultad()
	{
		return facultad;
	}
	/**
	 * @param facultad the facultad to set
	 */
	public void setFacultad(String facultad)
	{
		this.facultad = facultad;
	}
	/**
	 * @return the sede
	 */
	public String getSede()
	{
		return sede;
	}
	/**
	 * @param sede the sede to set
	 */
	public void setSede(String sede)
	{
		this.sede = sede;
	}

	/**
	 * @return the modalidad
	 */
	public String getModalidad()
	{
		return modalidad;
	}

	/**
	 * @param modalidad the modalidad to set
	 */
	public void setModalidad(String modalidad)
	{
		this.modalidad = modalidad;
	}
}

