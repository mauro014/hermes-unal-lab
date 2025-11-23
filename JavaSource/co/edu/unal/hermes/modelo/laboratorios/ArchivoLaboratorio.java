package co.edu.unal.hermes.modelo.laboratorios;

import java.util.Date;

import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ArchivoLaboratorio {

	private Long id;
	private Long idDetalle;
	private Long idLab;
	private String nombreArchivo;
	private Tipos tipoArchivo;
//	private LaboratorioActividadEquipo actividad;
	private Date fechaArchivo;
	
//	private Long idSol;
//	private Long idActividad;
	
	private LaboratorioSolicitud solicitud;
	private LaboratorioActividadEquipo actividad;
	private LaboratorioEquipoReporteDanio reporteDanio;
	private InsumoLaboratorio insumo;

	public static String TABLA_ARCHIVOS_LABORATORIOS = "HER_ARCHIVO_LABORATORIO";

	public static String DIRECTORIO_ARCHIVOS = ManejadorBase.RUTA_ARCHIVOS+ TABLA_ARCHIVOS_LABORATORIOS;

	public ArchivoLaboratorio() {
	}

	/*
	 * @Override public boolean equals(Object otroObjeto) { boolean igual =
	 * false; if (otroObjeto != null && otroObjeto instanceof
	 * ArchivoLaboratorio) { ArchivoLaboratorio otroDetalle =
	 * (ArchivoLaboratorio) otroObjeto;
	 * 
	 * if (otroDetalle.id.equals(this.id)) { igual = true; } }
	 * System.out.println("ArchivoLaboratorio equals: " + igual); return igual;
	 * }
	 */

	@Override
	public String toString() {
		String cadena = "ArchivoLaboratorio: " + nombreArchivo;
		return cadena;
	}

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
	 * @return the idDetalle
	 */
	public Long getIdDetalle() {
		return idDetalle;
	}

	/**
	 * @param idDetalle
	 *            the idDetalle to set
	 */
	public void setIdDetalle(Long idDetalle) {
		this.idDetalle = idDetalle;
	}

	/**
	 * @return the fechaArchivo
	 */
	public Date getFechaArchivo() {
		return fechaArchivo;
	}

	/**
	 * @param fechaArchivo
	 *            the fechaArchivo to set
	 */
	public void setFechaArchivo(Date fechaArchivo) {
		this.fechaArchivo = fechaArchivo;
	}

	/**
	 * @return the idLab
	 */
	public Long getIdLab() {
		return idLab;
	}

	/**
	 * @param idLab
	 *            the idLab to set
	 */
	public void setIdLab(Long idLab) {
		this.idLab = idLab;
	}

	/**
	 * @return the nombreArchivo
	 */
	public String getNombreArchivo() {
		return nombreArchivo;
	}

	/**
	 * @param nombreArchivo
	 *            the nombreArchivo to set
	 */
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	/**
	 * @return the tipoArchivo
	 */
	public Tipos getTipoArchivo() {
		return tipoArchivo;
	}

	/**
	 * @param tipoArchivo
	 *            the tipoArchivo to set
	 */
	public void setTipoArchivo(Tipos tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}

//	public Long getIdSol() {
//		return idSol;
//	}
//
//	public void setIdSol(Long idSol) {
//		this.idSol = idSol;
//	}
//
//	public Long getIdActividad() {
//		return idActividad;
//	}
//
//	public void setIdActividad(Long idActividad) {
//		this.idActividad = idActividad;
//	}

	public LaboratorioSolicitud getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(LaboratorioSolicitud solicitud) {
		this.solicitud = solicitud;
	}

	public LaboratorioActividadEquipo getActividad() {
		return actividad;
	}

	public void setActividad(LaboratorioActividadEquipo actividad) {
		this.actividad = actividad;
	}

	public LaboratorioEquipoReporteDanio getReporteDanio() {
		return reporteDanio;
	}

	public void setReporteDanio(LaboratorioEquipoReporteDanio reporteDanio) {
		this.reporteDanio = reporteDanio;
	}

	public InsumoLaboratorio getInsumo() {
		return insumo;
	}

	public void setInsumo(InsumoLaboratorio insumo) {
		this.insumo = insumo;
	}
}
