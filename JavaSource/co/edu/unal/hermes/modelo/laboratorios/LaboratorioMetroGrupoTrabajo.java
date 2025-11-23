
package co.edu.unal.hermes.modelo.laboratorios;

import java.util.Date;

import co.edu.unal.hermes.modelo.Tipos;

public class LaboratorioMetroGrupoTrabajo{
        
    private Long id;  
    private Laboratorio laboratorio;
    private Tipos tipoGrupo;
    private String nombre;
    private Tipos periodoParticipacion;
    private Date fechaRegistro;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Laboratorio getLaboratorio() {
		return laboratorio;
	}
	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
	}
	public Tipos getTipoGrupo() {
		return tipoGrupo;
	}
	public void setTipoGrupo(Tipos tipoGrupo) {
		this.tipoGrupo = tipoGrupo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Tipos getPeriodoParticipacion() {
		return periodoParticipacion;
	}
	public void setPeriodoParticipacion(Tipos periodoParticipacion) {
		this.periodoParticipacion = periodoParticipacion;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
}
