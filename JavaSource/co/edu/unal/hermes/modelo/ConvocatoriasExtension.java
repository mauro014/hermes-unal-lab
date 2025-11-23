package co.edu.unal.hermes.modelo;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;

import org.hibernate.Hibernate;

public class ConvocatoriasExtension {

    public ConvocatoriasExtension() {
	super();
    }

    
    public ConvocatoriasExtension(Long id, String nombre, String entidad, Date fechaCierre, String objeto, Blob terminosReferencia, EstadoConvocatoria estadoConvocatoria, String nombreTerminosReferencia,
	    Date fechaRegistro) {
	super();
	this.id = id;
	this.nombre = nombre;
	this.entidad = entidad;
	this.fechaCierre = fechaCierre;
	this.objeto = objeto;
	this.terminosReferencia = terminosReferencia;
	this.estadoConvocatoria = estadoConvocatoria;
	this.nombreTerminosReferencia = nombreTerminosReferencia;
	this.fechaRegistro = fechaRegistro;
    }


    private Long id;
    private String nombre;
    private String entidad;
    private Date fechaCierre;
    private String objeto;
    private Blob terminosReferencia;
    private EstadoConvocatoria estadoConvocatoria;
    private String nombreTerminosReferencia;
    private Date fechaRegistro;

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

    public String getEntidad() {
	return entidad;
    }

    public void setEntidad(String entidad) {
	this.entidad = entidad;
    }

    public Date getFechaCierre() {
	return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
	this.fechaCierre = fechaCierre;
    }

    public String getObjeto() {
	return objeto;
    }

    public void setObjeto(String objeto) {
	this.objeto = objeto;
    }

    public EstadoConvocatoria getEstadoConvocatoria() {
	return estadoConvocatoria;
    }

    public void setEstadoConvocatoria(EstadoConvocatoria estadoConvocatoria) {
	this.estadoConvocatoria = estadoConvocatoria;
    }

    public void setBytes(byte[] bytes) {
	terminosReferencia = Hibernate.createBlob(bytes);
    }

    public byte[] getBytes() {
	byte[] resultado = null;
	try {
	    resultado = terminosReferencia.getBytes(1, (int) terminosReferencia.length());
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return resultado;
    }

    public Blob getTerminosReferencia() {
	return terminosReferencia;
    }

    public void setTerminosReferencia(Blob terminosReferencia) {
	this.terminosReferencia = terminosReferencia;
    }

    public String getNombreTerminosReferencia() {
	return nombreTerminosReferencia;
    }

    public void setNombreTerminosReferencia(String nombreTerminosReferencia) {
	this.nombreTerminosReferencia = nombreTerminosReferencia;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

}
