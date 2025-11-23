

package co.edu.unal.hermes.modelo;


import java.util.Date;



public class ArchivoInformeAnla{
		
	private Long id;
	private String nombre;
	private Date fecha;
	private PermisoInformeAnla informe;


	private Long aval;

	public Long getId() 
	{
		return id;
	}

	public void setId(Long id) 
	{
		this.id = id;
	}

	public String getNombre() 
	{
		return nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}	
	
	public Long getAval() {
		return aval;
	}

	public void setAval(Long aval) {
		this.aval = aval;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public PermisoInformeAnla getInforme() {
		return informe;
	}

	public void setInforme(PermisoInformeAnla informe) {
		this.informe = informe;
	}

	
	
}
