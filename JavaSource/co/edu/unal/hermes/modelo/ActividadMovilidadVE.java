/*
 * Created on 18-ago-2005
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Específica la actividad asociada a la movilidad de una visita.
 * 
 */
public class ActividadMovilidadVE implements Serializable{
        
    private Long id;    
    private String descripcion;
    private Integer duracion;
    private Date fecha;
    private boolean borrable=false;
    private String errorDescripcion;
    private String errorFecha;
    private String errorDuracion;
    private boolean bErrorDescripcion = false;
    private boolean bErrorFecha = false;
    private boolean bErrorDuracion = false;
    private MovilidadVisitanteExterior movilidad;
    private TipoActividadMovilidadVE tipoActividad;
    
    //MODIFICACIÓN ING MILENA
    private String nomTesista;
    private String codEstudiante;
    private String programa;
    private String nomTesis;
    private String nomCursoEvento;
    private String nomArea;
    private String asistentes;
    //asistentes
	private Set asistentesn = new HashSet();
	private List tipoAsistentes = new ArrayList();
    //
    
    //nueva actividad de las nuevas movilidades
    //Ing. Wilver Alexander Martínez Martínez  - wam²
    //2010-05-31
    


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getDuracion() {
		return duracion;
	}

	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public boolean isBorrable() {
		return borrable;
	}
	public void setBorrable(boolean borrable) {
		this.borrable = borrable;
	}


	public MovilidadVisitanteExterior getMovilidad() {
		return movilidad;
	}

	public void setMovilidad(MovilidadVisitanteExterior movilidad) {
		this.movilidad = movilidad;
	}

	public String getErrorDescripcion() {
		return errorDescripcion;
	}

	public void setErrorDescripcion(String errorDescripcion) {
		this.errorDescripcion = errorDescripcion;
	}

	public String getErrorFecha() {
		return errorFecha;
	}

	public void setErrorFecha(String errorFecha) {
		this.errorFecha = errorFecha;
	}

	public String getErrorDuracion() {
		return errorDuracion;
	}

	public void setErrorDuracion(String errorDuracion) {
		this.errorDuracion = errorDuracion;
	}

	public boolean isBErrorDescripcion() {
		return bErrorDescripcion;
	}

	public void setBErrorDescripcion(boolean errorDescripcion) {
		bErrorDescripcion = errorDescripcion;
	}

	public boolean isBErrorFecha() {
		return bErrorFecha;
	}

	public void setBErrorFecha(boolean errorFecha) {
		bErrorFecha = errorFecha;
	}

	public boolean isBErrorDuracion() {
		return bErrorDuracion;
	}

	public void setBErrorDuracion(boolean errorDuracion) {
		bErrorDuracion = errorDuracion;
	}

	public TipoActividadMovilidadVE getTipoActividad() {
		return tipoActividad;
	}

	public void setTipoActividad(TipoActividadMovilidadVE tipoActividad) {
		this.tipoActividad = tipoActividad;
	}

	public boolean isbErrorDescripcion() {
		return bErrorDescripcion;
	}

	public void setbErrorDescripcion(boolean bErrorDescripcion) {
		this.bErrorDescripcion = bErrorDescripcion;
	}

	public boolean isbErrorFecha() {
		return bErrorFecha;
	}

	public void setbErrorFecha(boolean bErrorFecha) {
		this.bErrorFecha = bErrorFecha;
	}

	public boolean isbErrorDuracion() {
		return bErrorDuracion;
	}

	public void setbErrorDuracion(boolean bErrorDuracion) {
		this.bErrorDuracion = bErrorDuracion;
	}

	public String getNomTesista() {
		return nomTesista;
	}

	public void setNomTesista(String nomTesista) {
		this.nomTesista = nomTesista;
	}

	public String getCodEstudiante() {
		return codEstudiante;
	}

	public void setCodEstudiante(String codEstudiante) {
		this.codEstudiante = codEstudiante;
	}

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public String getNomTesis() {
		return nomTesis;
	}

	public void setNomTesis(String nomTesis) {
		this.nomTesis = nomTesis;
	}

	public String getNomCursoEvento() {
		return nomCursoEvento;
	}

	public void setNomCursoEvento(String nomCursoEvento) {
		this.nomCursoEvento = nomCursoEvento;
	}

	public String getNomArea() {
		return nomArea;
	}

	public void setNomArea(String nomArea) {
		this.nomArea = nomArea;
	}

	public String getAsistentes() {
		return asistentes;
	}

	public void setAsistentes(String asistentes) {
		this.asistentes = asistentes;
	}

	public Set getAsistentesn() {
		return asistentesn;
	}

	public void setAsistentesn(Set asistentesn) {
		this.asistentesn = asistentesn;
	}

	public List getTipoAsistentes() {
		return tipoAsistentes;
	}

	public void setTipoAsistentes(List tipoAsistentes) {
		this.tipoAsistentes = tipoAsistentes;
	}
	
	
}
