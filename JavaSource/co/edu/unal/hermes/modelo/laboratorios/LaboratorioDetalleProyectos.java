package co.edu.unal.hermes.modelo.laboratorios;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.Proyecto;

public class LaboratorioDetalleProyectos {

	private Long id;
	private Laboratorio laboratorio;
	private Proyecto proyecto;
	private Grupo grupoInvestigacion;
	private Integer personasPregrado;
	private Integer personasEspecializacion;
	private Integer personasMaestria;
	private Integer personasDoctorado;
	private Integer horasSemanales;
	private Date fechaRegistro;
	private Boolean esProgramaLaboratorios;
	private String estado;
	private Set<LaboratorioDetalleEquipos> equipos = new HashSet<LaboratorioDetalleEquipos>();

	public LaboratorioDetalleProyectos() {
	}

//	@Override
//	public boolean equals(Object otroObjeto) {
//		LaboratorioDetalleProyectos otroDetalle = (LaboratorioDetalleProyectos) otroObjeto;
//		
//		if(laboratorio == null)
//			return false;
//		else
//		{	
//			if(laboratorio.getId().equals(otroDetalle.getLaboratorio().getId())){
//				return true;
//			}else{
//				return false;
//			}
//		}
//		
//	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the laboratorio
	 */
	public Laboratorio getLaboratorio() {
		return laboratorio;
	}

	/**
	 * @param laboratorio
	 *            the laboratorio to set
	 */
	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
	}

	/**
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @param fechaRegistro
	 *            the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Boolean getEsProgramaLaboratorios() {
		return esProgramaLaboratorios;
	}

	public void setEsProgramaLaboratorios(Boolean esProgramaLaboratorios) {
		this.esProgramaLaboratorios = esProgramaLaboratorios;
	}

	/**
	 * @return the proyecto
	 */
	public Proyecto getProyecto() {
		return proyecto;
	}

	/**
	 * @param proyecto
	 *            the proyecto to set
	 */
	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	/**
	 * @return the personasPregrado
	 */
	public Integer getPersonasPregrado() {
		return personasPregrado;
	}

	/**
	 * @param personasPregrado
	 *            the personasPregrado to set
	 */
	public void setPersonasPregrado(Integer personasPregrado) {
		this.personasPregrado = personasPregrado;
	}

	/**
	 * @return the personasEspecializacion
	 */
	public Integer getPersonasEspecializacion() {
		return personasEspecializacion;
	}

	/**
	 * @param personasEspecializacion
	 *            the personasEspecializacion to set
	 */
	public void setPersonasEspecializacion(Integer personasEspecializacion) {
		this.personasEspecializacion = personasEspecializacion;
	}

	/**
	 * @return the personasMaestria
	 */
	public Integer getPersonasMaestria() {
		return personasMaestria;
	}

	/**
	 * @param personasMaestria
	 *            the personasMaestria to set
	 */
	public void setPersonasMaestria(Integer personasMaestria) {
		this.personasMaestria = personasMaestria;
	}

	/**
	 * @return the personasDoctorado
	 */
	public Integer getPersonasDoctorado() {
		return personasDoctorado;
	}

	/**
	 * @param personasDoctorado
	 *            the personasDoctorado to set
	 */
	public void setPersonasDoctorado(Integer personasDoctorado) {
		this.personasDoctorado = personasDoctorado;
	}

	/**
	 * @return the horasSemanales
	 */
	public Integer getHorasSemanales() {
		return horasSemanales;
	}

	/**
	 * @param horasSemanales
	 *            the horasSemanales to set
	 */
	public void setHorasSemanales(Integer horasSemanales) {
		this.horasSemanales = horasSemanales;
	}

	/**
	 * @return the grupoInvestigacion
	 */
	public Grupo getGrupoInvestigacion() {
		return grupoInvestigacion;
	}

	/**
	 * @param grupoInvestigacion
	 *            the grupoInvestigacion to set
	 */
	public void setGrupoInvestigacion(Grupo grupoInvestigacion) {
		this.grupoInvestigacion = grupoInvestigacion;
	}

	/**
	 * @return the equipos
	 */
	public Set<LaboratorioDetalleEquipos> getEquipos() {
		return equipos;
	}

	/**
	 * @param equipos
	 *            the equipos to set
	 */
	public void setEquipos(Set<LaboratorioDetalleEquipos> equipos) {
		this.equipos = equipos;
	}
	
	public List<LaboratorioDetalleEquipos> getListaEquipos() {
		List<LaboratorioDetalleEquipos> listaEquipos = new ArrayList<LaboratorioDetalleEquipos>();
		listaEquipos.addAll(equipos);
		return listaEquipos;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
