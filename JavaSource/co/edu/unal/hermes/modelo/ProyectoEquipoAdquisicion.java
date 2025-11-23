/*
 * Created on 04-ago-2022
 */
package co.edu.unal.hermes.modelo;


public class ProyectoEquipoAdquisicion {
    
    private Long id;    
    private Proyecto proyecto;
    private String nombreEquipo;
    private String ubicacion;
    private String responsable; 
    private String servicio; 
    private String justificacion;
    private Long valor;
    
	/**
	 * Constructo tradicional
	 */
	public ProyectoEquipoAdquisicion(){
		
	}
	
	/**
	 * Constructor que recibe como parametro el id, para inicializar
	 * el id del objeto.
	 * 
	 * @param LongpIdTipoDato
	 */
	public ProyectoEquipoAdquisicion(Long pId){
		this.id = pId;
	}
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
   
    public Proyecto getProyecto() {
        return proyecto;
    }
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

	public String getNombreEquipo() {
		return nombreEquipo;
	}

	public void setNombreEquipo(String nombreEquipo) {
		this.nombreEquipo = nombreEquipo;
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

	public String getServicio() {
		return servicio;
	}

	public void setServicio(String servicio) {
		this.servicio = servicio;
	}

	public String getJustificacion() {
		return justificacion;
	}

	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	public Long getValor() {
		return valor;
	}

	public void setValor(Long valor) {
		this.valor = valor;
	}    
      	
    
	
}
