/*
 * Created on 27-oct-2006
 * @authorIng. Wilver Alexander Martínez Martínez - wam² - UN.
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package co.edu.unal.hermes.modelo;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;

import org.hibernate.Hibernate;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public class InscripcionEscuelaInternacional   implements Serializable{
	
    Long id;
    private String nombres;
    private String apellidos;
    private String tipoDocumento;
    private String documento;
    private String carrera;
    private String universidad;
    private String promedio;
    private String credito;
    private Long  modulo;
    private Long curso;
	private String ciudad;
	private String direccion;
	private String pais;
	private String link;
	private String soporte1Nombre;
	private Blob soporte1;
	private StreamedContent soporte1_prime;
    
    private String actividad;
    private String institucion;
    
	private Date fecha;   
    
    private String telefonoFijo; 
    private String telefonoMovil;
    private String Facultad;    
   
	private String certificado;
    private String experiencia;
    private String relacion;
    private String correo;
    private TipoPersonaEscuela tipoPersona;
    private Long semestre;
    private String estudianteUN;
    
    private String empresa;
    private String cargo;
    private String profesion;
    private String modalidad;
    private String estado;
    private Date fechaCancela;
    private boolean aceptado;
    
    private String motivacion;
    private String aceptaFormulario;
    
    
    
    
    public InscripcionEscuelaInternacional() {
		
    	//aceptado=false;
    	
	}
    
    public void cargarStreamedContent()
    {
    	//InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream("/images/optimusprime.jpg");  
    	//soporte1_prime = new DefaultStreamedContent(stream, "image/jpg", "downloaded_optimus.jpg");
    	
    	try {
    		
    		if(soporte1 != null)
    		{	
    			InputStream stream = soporte1.getBinaryStream();
    			soporte1_prime = new DefaultStreamedContent(stream,"text/plain",soporte1Nombre);
    		}	
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    }

	public void setBytes(byte[] bytes){
    	soporte1 = Hibernate.createBlob(bytes);
	    }
	 
	public byte[] getBytes(){
	        byte[] resultado = null;
	        try {
	            resultado = soporte1.getBytes(1, (int) soporte1.length());
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return resultado;
	 }
    
    //
    public String getTelefonoFijo() {
		return telefonoFijo;
	}
	public void setTelefonoFijo(String telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}
	public String getTelefonoMovil() {
		return telefonoMovil;
	}
	public void setTelefonoMovil(String telefonoMovil) {
		this.telefonoMovil = telefonoMovil;
	}
	public String getFacultad() {
		return Facultad;
	}
	public void setFacultad(String facultad) {
		Facultad = facultad;
	}
    
    public String getEstudianteUN() {
		return estudianteUN;
	}
	public void setEstudianteUN(String estudianteUN) {
		this.estudianteUN = estudianteUN;
	}
	    
    public String getActividad() {
		return actividad;
	}
	public void setActividad(String actividad) {
		this.actividad = actividad;
	}
	public String getInstitucion() {
		return institucion;
	}
	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getCarrera() {
		return carrera;
	}
	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}
	public String getUniversidad() {
		return universidad;
	}
	public void setUniversidad(String universidad) {
		this.universidad = universidad;
	}
	public String getPromedio() {
		return promedio;
	}
	public void setPromedio(String promedio) {
		this.promedio = promedio;
	}
	public String getCredito() {
		return credito;
	}
	public void setCredito(String credito) {
		this.credito = credito;
	}
	public String getCertificado() {
		return certificado;
	}
	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}
	
	public String getExperiencia() {
		return experiencia;
	}
	public void setExperiencia(String experiencia) {
		this.experiencia = experiencia;
	}
	public String getRelacion() {
		return relacion;
	}
	public void setRelacion(String relacion) {
		this.relacion = relacion;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public TipoPersonaEscuela getTipoPersona() {
		return tipoPersona;
	}
	public void setTipoPersona(TipoPersonaEscuela tipoPersona) {
		this.tipoPersona = tipoPersona;
	}
	public Long getSemestre() {
		return semestre;
	}
	public void setSemestre(Long semestre) {
		this.semestre = semestre;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Long getModulo() {
		return modulo;
	}
	public void setModulo(Long modulo) {
		this.modulo = modulo;
	}
	public Long getCurso() {
		return curso;
	}
	public void setCurso(Long curso) {
		this.curso = curso;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getSoporte1Nombre() {
		return soporte1Nombre;
	}
	public void setSoporte1Nombre(String soporte1Nombre) {
		this.soporte1Nombre = soporte1Nombre;
	}
	public Blob getSoporte1() {
		return soporte1;
	}
	public void setSoporte1(Blob soporte1) {
		this.soporte1 = soporte1;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getProfesion() {
		return profesion;
	}

	public void setProfesion(String profesion) {
		this.profesion = profesion;
	}

	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaCancela() {
		return fechaCancela;
	}

	public void setFechaCancela(Date fechaCancela) {
		this.fechaCancela = fechaCancela;
	}

	public StreamedContent getSoporte1_prime() {
		return soporte1_prime;
	}

	public void setSoporte1_prime(StreamedContent soporte1_prime) {
		this.soporte1_prime = soporte1_prime;
	}

	public boolean isAceptado() {
		return aceptado;
	}

	public void setAceptado(boolean aceptado) {
		this.aceptado = aceptado;
	}

	public String getMotivacion() {
		return motivacion;
	}

	public void setMotivacion(String motivacion) {
		this.motivacion = motivacion;
	}

	public String getAceptaFormulario() {
		return aceptaFormulario;
	}

	public void setAceptaFormulario(String aceptaFormulario) {
		this.aceptaFormulario = aceptaFormulario;
	}
	
}
