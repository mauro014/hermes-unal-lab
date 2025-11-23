package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class GruposCursosECP implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8986289847308019893L;
	
	private Long id;
	private String descripcionGrupo;
	private int numeroAsistentesGrupo;
	private Proyecto proyecto;
	private Integer grupoCurso;
	private Set horarioGrupo = new HashSet();
	private String idCoordinadorGrupo;
	private String tipoDocCoordinadorGrupo;
	
	
	
	public boolean equals(Object obj){
	    if(obj instanceof GruposCursosECP){
	    	GruposCursosECP idGrupoECP = (GruposCursosECP)obj;
	        if(idGrupoECP.getGrupoCurso() == grupoCurso && idGrupoECP.getProyecto().getId() == proyecto.getId()){
	            return true;
	        }
	    }
		return false;
	}

	

	public String getDescripcionGrupo() {
		return descripcionGrupo;
	}

	public void setDescripcionGrupo(String descripcionGrupo) {
		this.descripcionGrupo = descripcionGrupo;
	}

	public int getNumeroAsistentesGrupo() {
		return numeroAsistentesGrupo;
	}

	public void setNumeroAsistentesGrupo(int numeroAsistentesGrupo) {
		this.numeroAsistentesGrupo = numeroAsistentesGrupo;
	}

	public Set getHorarioGrupo() {
		return horarioGrupo;
	}

	public void setHorarioGrupo(Set horarioGrupo) {
		this.horarioGrupo = horarioGrupo;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Integer getGrupoCurso() {
		return grupoCurso;
	}



	public void setGrupoCurso(Integer grupoCurso) {
		this.grupoCurso = grupoCurso;
	}



	public String getIdCoordinadorGrupo() {
		return idCoordinadorGrupo;
	}



	public void setIdCoordinadorGrupo(String idCoordinadorGrupo) {
		this.idCoordinadorGrupo = idCoordinadorGrupo;
	}



	public String getTipoDocCoordinadorGrupo() {
		return tipoDocCoordinadorGrupo;
	}



	public void setTipoDocCoordinadorGrupo(String tipoDocCoordinadorGrupo) {
		this.tipoDocCoordinadorGrupo = tipoDocCoordinadorGrupo;
	}

}
