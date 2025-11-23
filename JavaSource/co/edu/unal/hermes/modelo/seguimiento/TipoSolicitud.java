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
import java.util.Date;

public class TipoSolicitud implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nombre;
	private String descripcion;
	private String movimientoRubros;
	private String vigencia;
	private Date desdeVigencia;
	private Date hastaVigencia;
	public static final long CAMBIO_CONTENIDO = 4;
	public static final long CAMBIO_INTEGRANTES = 5;
	public static final long SUSPENSION = 6;
	public static final long CANCELACION = 7;
	public static final long ADICION_PRESPUESTAL = 8;
	public static final long PRORROGA = 22;
	public static final long CAMBIO_RUBROS = 23;
	public static final long CAMBIO_INVESTIGADOR_PRINCIPAL = 24;
	public static final long INFORME_AVANCE = 25;
	public static final long INFORME_FINAL = 26;
	public static final long CERTIFICADO_MOVILIZACION = 29;
	public static final long RENOCACION = 30;
	public static final long REACTIVACION = 31;
	public static final long CONTRATO_INDIVIDUAL = 32;
	public static final long CONTRATO_MARCO = 33;
	public static final long EXPORTACION_IMPORTACION = 34;
	public static final long RECOLECTA_PNN = 35;

	private boolean detallaRubros = false;
	
	public TipoSolicitud() {
	    /**
	     * Empty constructor.
	     */
	}
	
	public TipoSolicitud(Long id) {
	    super();
	    this.id = id;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getVigencia() {
		return vigencia;
	}

	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}

	public Date getDesdeVigencia() {
		return desdeVigencia;
	}

	public void setDesdeVigencia(Date desdeVigencia) {
		this.desdeVigencia = desdeVigencia;
	}

	public Date getHastaVigencia() {
		return hastaVigencia;
	}

	public void setHastaVigencia(Date hastaVigencia) {
		this.hastaVigencia = hastaVigencia;
	}

	public String getMovimientoRubros() {
		return movimientoRubros;
	}

	public void setMovimientoRubros(String movimientoRubros) {
		if (movimientoRubros != null && movimientoRubros.equals("S")){
			detallaRubros = true;
		}
		this.movimientoRubros = movimientoRubros;
	}

	public boolean getDetallaRubros() {
		return detallaRubros;
	}

	public boolean getDetallaInvestigadores() {
		return detallaRubros;
	}
	
	public boolean isEsSolicitudBiodiversidadVicerrectoria(){
	    if(this.id!=null && (this.id.equals(CERTIFICADO_MOVILIZACION) || this.id.equals(EXPORTACION_IMPORTACION) || this.id.equals(RECOLECTA_PNN))){
	        return true;
	    }
	    return false;
	}
	
	public boolean isEsSolicitudCertificadoMovilizacion() {
	    if(this.id!=null && (this.id.equals(CERTIFICADO_MOVILIZACION))){
	        return true;
	    }
	    return false;
	}
}
