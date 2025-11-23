package co.edu.unal.hermes.modelo;

import java.io.Serializable;


public class ColeccionPersona implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The Constant Curador General. */
    public static final String CURADOR_GENERAL = "CU1";
    /** The Constant Curador */
    public static final String CURADOR = "CU";
	
	// Atributos de la clase ColeccionPersona, correspondientes a la tabla HER_COLECCION_PERSONA
	private Long id;
	private Persona persona;
	private Coleccion coleccion ;
	private String tipoPersona;
	private boolean primeraPersona = false;
	private String especialidad;
	private String perfilEnLinea;
	private DominioDetalle subGrupoBiologicoCurador;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}
	public String getTipoPersona() {
		return tipoPersona;
	}
	public void setColeccion(Coleccion coleccion) {
		this.coleccion = coleccion;
	}
	public Coleccion getColeccion() {
		return coleccion;
	}
	public boolean isPrimeraPersona() {
		return primeraPersona;
	}
	public void setPrimeraPersona(boolean primeraPersona) {
		this.primeraPersona = primeraPersona;
	}
    public String getEspecialidad() {
        return especialidad;
    }
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    public String getPerfilEnLinea() {
        return perfilEnLinea;
    }
    public void setPerfilEnLinea(String perfilEnLinea) {
        this.perfilEnLinea = perfilEnLinea;
    }
    public DominioDetalle getSubGrupoBiologicoCurador() {
        return subGrupoBiologicoCurador;
    }
    public void setSubGrupoBiologicoCurador(DominioDetalle subGrupoBiologicoCurador) {
        this.subGrupoBiologicoCurador = subGrupoBiologicoCurador;
    }
    public String getNombreTipoPersona(){
        String nombreTipo = "";
        if(this.tipoPersona!=null){
            if(CURADOR_GENERAL.equals(this.tipoPersona)){
                nombreTipo = "Director(a) principal";
            }else if(CURADOR.equals(this.tipoPersona)){
                nombreTipo = "Co-Director(a)";
            }else if("EST".equals(this.tipoPersona)){
                nombreTipo = "Estudiante";
            }else if("EXT".equals(this.tipoPersona)){
                nombreTipo = "Externo";
            }else if("I".equals(this.tipoPersona)){
                nombreTipo = "Investigador(a)";
            }else if("A".equals(this.tipoPersona)){
                nombreTipo = "Administrativo(a)";
            }
        }
        return nombreTipo;
    }
}
