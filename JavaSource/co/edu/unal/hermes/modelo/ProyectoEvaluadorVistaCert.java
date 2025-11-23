///////////
// Clase utilizada para consultar las evaluaciones de proyectos realizadas por un evaluador.
// También es usada para generar certificados de evaluaciones.
///////////

package co.edu.unal.hermes.modelo;

import java.util.Date;

public class ProyectoEvaluadorVistaCert {
	private String idProyecto;
	private String nombreProyecto;
	private String nombreConvocatoria;
	private String dependenciaConvocatoria;
	private Date fechaEvaluacion;
	private String invId;
	private String invTpDoc;
	private String idEvaluacion;
	
	public ProyectoEvaluadorVistaCert(){
		
	}

	public String getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(String idProyecto) {
		this.idProyecto = idProyecto;
	}

	public String getNombreProyecto() {
		return nombreProyecto;
	}

	public void setNombreProyecto(String nombreProyecto) {
		this.nombreProyecto = nombreProyecto;
	}

	public String getNombreConvocatoria() {
		return nombreConvocatoria;
	}

	public void setNombreConvocatoria(String nombreConvocatoria) {
		this.nombreConvocatoria = nombreConvocatoria;
	}

	public Date getFechaEvaluacion() {
		return fechaEvaluacion;
	}

	public void setFechaEvaluacion(Date fechaEvaluacion) {
		this.fechaEvaluacion = fechaEvaluacion;
	}

	public String getInvId() {
		return invId;
	}

	public void setInvId(String invId) {
		this.invId = invId;
	}

	public String getInvTpDoc() {
		return invTpDoc;
	}

	public void setInvTpDoc(String invTpDoc) {
		this.invTpDoc = invTpDoc;
	}

	public String getIdEvaluacion() {
		return idEvaluacion;
	}

	public void setIdEvaluacion(String idEvaluacion) {
		this.idEvaluacion = idEvaluacion;
	}

	public String getDependenciaConvocatoria() {
		return dependenciaConvocatoria;
	}

	public void setDependenciaConvocatoria(String dependenciaConvocatoria) {
		this.dependenciaConvocatoria = dependenciaConvocatoria;
	}
	
}
