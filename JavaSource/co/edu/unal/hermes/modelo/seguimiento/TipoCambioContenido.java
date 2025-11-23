/********************************************************************************
Autor 		: Oscar Javier Castro Medina - Consult Soft S.A.
Clase    	: co.edu.unal.hermes.modelo.seguimiento.TipoSolicitud
Objetivo 	: Clase   POJO   para   la  tabla  HER_TIPO_SOLICITUD,  en  la   cual
  			  se almacenan los tipos de solicitudes que  se  pueden  realizar por 
  			  parte de los investigadores para un proyecto  de investigaciòn.
Creación	: Agosto 15 de 2007
Modificación:
Detalle		:
********************************************************************************/
package co.edu.unal.hermes.modelo.seguimiento;

import java.io.Serializable;

public class TipoCambioContenido implements Serializable {
	
	
	private static final long serialVersionUID = 6056523267313955015L;
	
	private Long id;
	private String nombre;

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
	
}
