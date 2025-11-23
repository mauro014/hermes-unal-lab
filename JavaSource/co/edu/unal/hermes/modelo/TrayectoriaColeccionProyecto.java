package co.edu.unal.hermes.modelo;

public class TrayectoriaColeccionProyecto{
		
	private Long id;
	private String trayectoria;
	private String descripcion;
	private Proyecto proyecto;

	public TrayectoriaColeccionProyecto()
	{
		super();
	}
	
	/**
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id)
	{
		this.id = id;
	}
	/**
	 * @return the trayectoria
	 */
	public String getTrayectoria()
	{
		return trayectoria;
	}
	/**
	 * @param trayectoria the trayectoria to set
	 */
	public void setTrayectoria(String trayectoria)
	{
		this.trayectoria = trayectoria;
	}
	/**
	 * @return the descripcion
	 */
	public String getDescripcion()
	{
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}
	/**
	 * @return the proyecto
	 */
	public Proyecto getProyecto()
	{
		return proyecto;
	}
	/**
	 * @param proyecto the proyecto to set
	 */
	public void setProyecto(Proyecto proyecto)
	{
		this.proyecto = proyecto;
	}
	
	
}
