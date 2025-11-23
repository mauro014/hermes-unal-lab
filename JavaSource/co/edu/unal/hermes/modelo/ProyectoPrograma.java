package co.edu.unal.hermes.modelo;


/**
 * Suministra la información de los proyectos asociados a un programa 
 */
public class ProyectoPrograma{

	private Long id;
	private String nombre;
	private FuenteFinanciacion fuente;
	private Dependencia dependencia;
	private Proyecto proyecto;
	
	/**
     * Sobreescritura del metodo equals() de "object", especifica para programa 
     */
    public boolean equals(Object object){        
        if(object instanceof ProyectoPrograma){
            ProyectoPrograma programa = (ProyectoPrograma) object;
            if(this.nombre.equals(programa.getNombre())){
                return true;                
            }else{
                return false;                
            }            
        }else{
            return false;
        }        
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public FuenteFinanciacion getFuente() {
		return fuente;
	}

	public void setFuente(FuenteFinanciacion fuente) {
		this.fuente = fuente;
	}

	public Dependencia getDependencia() {
		return dependencia;
	}

	public void setDependencia(Dependencia dependencia) {
		this.dependencia = dependencia;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
	
	
	
	
}
