package co.edu.unal.hermes.modelo.laboratorios;

import java.util.Date;

import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ArchivoLaboratorioPersona {

	private Long id;
	private Persona persona;
	private Laboratorio laboratorio;
	private String nombreArchivo;
	private Tipos tipoArchivo;
	private Date fechaRegistro;

	public static String TABLA_ARCHIVOS_LABORATORIOS_PERSONA = "HER_ARCHIVO_LAB_PERSONA";

	public static String DIRECTORIO_ARCHIVOS = ManejadorBase.RUTA_ARCHIVOS + TABLA_ARCHIVOS_LABORATORIOS_PERSONA;

	public ArchivoLaboratorioPersona() {
	}

	@Override
	public String toString() {
		String cadena = "ArchivoLaboratorio: " + nombreArchivo;
		return cadena;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Laboratorio getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public Tipos getTipoArchivo() {
		return tipoArchivo;
	}

	public void setTipoArchivo(Tipos tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

}
