/**
 * @author Martha Liliana Correa O.
 * @date 23/12/2016
 */

package co.edu.unal.hermes.modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TipoTramiteBiodiversidad implements java.io.Serializable {

    public static final String VICERRECTORIA = "V";
    public static final String SEDE = "S";
    public static final String FACULTAD = "F";
    
    public static final Long INCLUSION_INVESTIGADOR_PM = 1L;
    public static final Long CONCEPTO_NECESIDAD_CARG = 8L;
    
    public static final Long REVISION_REQUISITOS_PM = 2L;
    public static final Long SEGUIMIENTO_PM = 3L;
    public static final Long ASIGNATURAS_PM = 4L;
    
    public static final Long PNN_CERT_MOV_EXP_ESPECIMENES = 5L;
    
    public static final Long SEGUIMIENTO_CONTRATO_INDIVIDUAL = 9L;
    public static final Long SEGUIMIENTO_CONTRATO_MARCO = 10L;
    
	/**
     * 
     */
    private static final long serialVersionUID = -5436274822524455298L;
    private Long id;
	private String nombre;
	private String descripcion;
	private String nivelTramite;
	private String estado; 
	
	private Set<PersonaTramiteBiodiversidad> personalEncargado = new HashSet<PersonaTramiteBiodiversidad>();

	
	/** default constructor */
	public TipoTramiteBiodiversidad() {
		
	}
	
	public TipoTramiteBiodiversidad(Long id) {
		this.setId(id);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNivelTramite() {
        return nivelTramite;
    }

    public void setNivelTramite(String nivelTramite) {
        this.nivelTramite = nivelTramite;
    }

    public Set<PersonaTramiteBiodiversidad> getPersonalEncargado() {
        return personalEncargado;
    }

    public void setPersonalEncargado(Set<PersonaTramiteBiodiversidad> personalEncargado) {
        this.personalEncargado = personalEncargado;
    }
    
    public List<PersonaTramiteBiodiversidad> getListaPersonas() {
        ArrayList<PersonaTramiteBiodiversidad> listaPersonal = new ArrayList<PersonaTramiteBiodiversidad>();
        listaPersonal.addAll(this.personalEncargado);
        return listaPersonal;
    }

    public void adicionarPersona(PersonaTramiteBiodiversidad per) {
        this.personalEncargado.add(per);
    }

    public void borrarPersona(PersonaTramiteBiodiversidad per) {
        this.personalEncargado.remove(per);
    }
    
    public boolean isEsVariosEncargados(){
        if(this.id==null){
            return false;
        }
        
        if(this.id.equals(INCLUSION_INVESTIGADOR_PM) || this.id.equals(CONCEPTO_NECESIDAD_CARG)){
            return true;
        }
        
        return false;
    }
    
    public boolean isEsTramiteVicerrectoria(){
        if(this.id==null){
            return false;
        }
        
        if(this.id.equals(PNN_CERT_MOV_EXP_ESPECIMENES) || (this.nivelTramite != null && this.nivelTramite.equals(VICERRECTORIA))){
            return true;
        }
        
        return false;
    }
    
    public boolean isEsTramiteSede(){
        if(this.id==null){
            return false;
        }
        
        if(this.nivelTramite != null && this.nivelTramite.equals(SEDE)){
            return true;
        }
        
        return false;
    }
    
    public boolean isEsTramiteFacultad(){
        if(this.id==null){
            return false;
        }
        
        if(this.nivelTramite != null && this.nivelTramite.equals(FACULTAD)){
            return true;
        }
        
        return false;
    }
    
    public boolean isEsMenuAdicionalCoordinador(){
        if(this.id==null){
            return false;
        }
        
        if(this.id.equals(INCLUSION_INVESTIGADOR_PM) || this.id.equals(CONCEPTO_NECESIDAD_CARG)){
            return true;
        }
        
        return false;
    }

}