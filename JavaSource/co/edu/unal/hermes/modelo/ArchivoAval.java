/********************************************************************************
Autor 		: Oscar Javier Castro Medina - Consult Soft S.A.
Clase    	: co.edu.unal.hermes.modelo.ArchivoAval
Objetivo 	: Clase POJO para la tabla HER_ARCHIVO_GRUPO en la cual se encuentran  
			  los archivos publicados como descargas por los grupos de investigación.
Creación	: Octubre 04 de 2007
Modificación:
Detalle		:
 ********************************************************************************/

package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;

public class ArchivoAval implements Serializable {

    private static final long serialVersionUID = 478891960712405536L;
    private Long id;
    private String nombre;
    private String descripcion;
    private String tipo;
    private Date fechaCreacion;
    private Date fechaBorrado;
    private TipoArchivo tipoArchivo;

    private Long aval;
    // REQ 26 Miguel Cubides habrá varios archivos por aval agregados por el
    // coordinador. Este identificador los establecerá
    private Long avalCoor;

    // Fin de REQ 26

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

    public Long getAval() {
        return aval;
    }

    public void setAval(Long aval) {
        this.aval = aval;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getAvalCoor() {
        return avalCoor;
    }

    public void setAvalCoor(Long avalCoor) {
        this.avalCoor = avalCoor;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaBorrado() {
        return fechaBorrado;
    }

    public void setFechaBorrado(Date fechaBorrado) {
        this.fechaBorrado = fechaBorrado;
    }
    
	public String getNombreTipo() {
		if (tipo != null) {
			if (tipo.equals("1")) {
				return "Carta de Viabilidad Financiera";
			} else if (tipo.equals("2")) {
				return "Aval del Comité de ética";
			} else if (tipo.equals("3")) {
				return "Borrador de carta de aval institucional en español";
			} else if (tipo.equals("4")) {
				return "Carta de autorización de las horas de los docentes participantes especificando el valor de la dedicación";
			} else if (tipo.equals("5")) {
				return "Formato estándar de la UNAL en español";
			} else if (tipo.equals("6")) {
				return "Formato estándar de la UNAL en inglés";
			} else if (tipo.equals("DRE")) {
				return "Concepto favorable de la DRE";
			} else if (tipo.equals("FE")) {
				return "Borrador de carta de aval institucional en inglés";
			}  else if (tipo.equals("REC")) {
				return "Documeto de Soporte adjuntado por Rectoría";
			} else {
				return "Documento de Soporte del Aval";
			}
		} else {
			return "Sin tipo asociado";
		}
	}

	public TipoArchivo getTipoArchivo() {
		return tipoArchivo;
	}

	public void setTipoArchivo(TipoArchivo tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}

}
