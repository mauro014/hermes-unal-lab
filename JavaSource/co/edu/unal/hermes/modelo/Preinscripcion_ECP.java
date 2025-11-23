package co.edu.unal.hermes.modelo;

import java.util.Date;

public class Preinscripcion_ECP {

	// private IdPersonaECP idPersonaECP;

	private Long id_pre;
	private Persona investigador;
	// private OfertaECP curso;
	private Proyecto curso;

	// private TipoDocumento tipo_id;
	// private String num_doc;

	// private String nombres;
	// private String primer_apellido;
	// private String segundo_apellido;
	// private String correo_electronico;
	// private String tel_fijo;

	// private String direccion;
	private Pais pais;
	private Departamento departamento;
	private Ciudad ciudad;
	private TipoFormacion formacion_academica;
	private AgendaConocimiento otros_intereses;
	// private DominioDetalle tipo_vinculacion;
	private String tipo_vinculacion;
	private String tipoVinculacionNombre;

	private String entidad;
	private String profesion;
	private String empresa;
	private String cargo;
	private String telefono_empresa;
	private String correo_empresa;
	private String grupo_sanguineo;
	private String rh;
	private String eps;

	private String tel_movil; // *
	private Date fecNacimiento; // *
	private EstadoCivil estado_civil; // *
	private String sector_empresa; // *
	private String tipo_empresa; // *
	private String medio_publicidad; // *
	private String ocupacion; // *
	private String estudianteUN; // *
	private String esDocente;
	private String esEmpleado;
	private String esContratista;
	private String esEgresado;
	private String esExterno;

	private Date feccre;
	private long id_oferta;
	private String estado;

	private String persona_contacto;
	private String tel_contacto;
	private String direccion_empresa;

	private String nitEmpresa;
	private String nombresRepLegal;
	private String apellidosRepLegal;
	private String numDocRepLegal;
	private String tipoDocRepLegal;

	private String nombreProblema;
	private String descripcionProblema;
	private String ubicacionProblema;
	private String comunidadObjetivoProblema;

	private Departamento departamentoProblema;
	private Ciudad ciudadProblema;
	private String otroMedioPublicidad;
	
	private boolean tieneArchivos;

	public String getNitEmpresa() {
		return nitEmpresa;
	}

	public void setNitEmpresa(String nitEmpresa) {
		this.nitEmpresa = nitEmpresa;
	}

	public String getNombresRepLegal() {
		return nombresRepLegal;
	}

	public void setNombresRepLegal(String nombresRepLegal) {
		this.nombresRepLegal = nombresRepLegal;
	}

	public String getApellidosRepLegal() {
		return apellidosRepLegal;
	}

	public void setApellidosRepLegal(String apellidosRepLegal) {
		this.apellidosRepLegal = apellidosRepLegal;
	}

	public String getNumDocRepLegal() {
		return numDocRepLegal;
	}

	public void setNumDocRepLegal(String numDocRepLegal) {
		this.numDocRepLegal = numDocRepLegal;
	}

	public String getTipoDocRepLegal() {
		return tipoDocRepLegal;
	}

	public void setTipoDocRepLegal(String tipoDocRepLegal) {
		this.tipoDocRepLegal = tipoDocRepLegal;
	}

	private String sedeFecha;

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	public TipoFormacion getFormacion_academica() {
		return formacion_academica;
	}

	public void setFormacion_academica(TipoFormacion formacion_academica) {
		this.formacion_academica = formacion_academica;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public String getProfesion() {
		return profesion;
	}

	public void setProfesion(String profesion) {
		this.profesion = profesion;
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

	public String getTelefono_empresa() {
		return telefono_empresa;
	}

	public void setTelefono_empresa(String telefono_empresa) {
		this.telefono_empresa = telefono_empresa;
	}

	public String getCorreo_empresa() {
		return correo_empresa;
	}

	public void setCorreo_empresa(String correo_empresa) {
		this.correo_empresa = correo_empresa;
	}

	public String getGrupo_sanguineo() {
		return grupo_sanguineo;
	}

	public void setGrupo_sanguineo(String grupo_sanguineo) {
		this.grupo_sanguineo = grupo_sanguineo;
	}

	public String getRh() {
		return rh;
	}

	public void setRh(String rh) {
		this.rh = rh;
	}

	public String getEps() {
		return eps;
	}

	public void setEps(String eps) {
		this.eps = eps;
	}

	public Date getFeccre() {
		return feccre;
	}

	public void setFeccre(Date feccre) {
		this.feccre = feccre;
	}

	public long getId_oferta() {
		return id_oferta;
	}

	public void setId_oferta(long id_oferta) {
		this.id_oferta = id_oferta;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public AgendaConocimiento getOtros_intereses() {
		return otros_intereses;
	}

	public void setOtros_intereses(AgendaConocimiento otros_intereses) {
		this.otros_intereses = otros_intereses;
	}

	public String getTipo_vinculacion() {
		return tipo_vinculacion;
	}

	public void setTipo_vinculacion(String tipo_vinculacion) {
		this.tipo_vinculacion = tipo_vinculacion;
	}

	public Long getId_pre() {
		return id_pre;
	}

	public void setId_pre(Long id_pre) {
		this.id_pre = id_pre;
	}

	public Proyecto getCurso() {
		return curso;
	}

	public void setCurso(Proyecto curso) {
		this.curso = curso;
	}

	public Persona getInvestigador() {
		return investigador;
	}

	public void setInvestigador(Persona investigador) {
		this.investigador = investigador;
	}

	public Date getFecNacimiento() {
		return fecNacimiento;
	}

	public void setFecNacimiento(Date fecNacimiento) {
		this.fecNacimiento = fecNacimiento;
	}

	public EstadoCivil getEstado_civil() {
		return estado_civil;
	}

	public void setEstado_civil(EstadoCivil estado_civil) {
		this.estado_civil = estado_civil;
	}

	public String getSector_empresa() {
		return sector_empresa;
	}

	public void setSector_empresa(String sector_empresa) {
		this.sector_empresa = sector_empresa;
	}

	public String getTipo_empresa() {
		return tipo_empresa;
	}

	public void setTipo_empresa(String tipo_empresa) {
		this.tipo_empresa = tipo_empresa;
	}

	public String getMedio_publicidad() {
		return medio_publicidad;
	}

	public void setMedio_publicidad(String medio_publicidad) {
		this.medio_publicidad = medio_publicidad;
	}

	public String getTel_movil() {
		return tel_movil;
	}

	public void setTel_movil(String tel_movil) {
		this.tel_movil = tel_movil;
	}

	public String getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(String ocupacion) {
		this.ocupacion = ocupacion;
	}

	public String getEstudianteUN() {
		return estudianteUN;
	}

	public void setEstudianteUN(String estudianteUN) {
		this.estudianteUN = estudianteUN;
	}

	public String getPersona_contacto() {
		return persona_contacto;
	}

	public void setPersona_contacto(String persona_contacto) {
		this.persona_contacto = persona_contacto;
	}

	public String getTel_contacto() {
		return tel_contacto;
	}

	public void setTel_contacto(String tel_contacto) {
		this.tel_contacto = tel_contacto;
	}

	public String getDireccion_empresa() {
		return direccion_empresa;
	}

	public void setDireccion_empresa(String direccion_empresa) {
		this.direccion_empresa = direccion_empresa;
	}

	public String getEsDocente() {
		return esDocente;
	}

	public void setEsDocente(String esDocente) {
		this.esDocente = esDocente;
	}

	public String getEsEmpleado() {
		return esEmpleado;
	}

	public void setEsEmpleado(String esEmpleado) {
		this.esEmpleado = esEmpleado;
	}

	public String getEsContratista() {
		return esContratista;
	}

	public void setEsContratista(String esContratista) {
		this.esContratista = esContratista;
	}

	public String getEsEgresado() {
		return esEgresado;
	}

	public void setEsEgresado(String esEgresado) {
		this.esEgresado = esEgresado;
	}

	public String getEsExterno() {
		return esExterno;
	}

	public void setEsExterno(String esExterno) {
		this.esExterno = esExterno;
	}

	public String getSedeFecha() {
		return sedeFecha;
	}

	public void setSedeFecha(String sedeFecha) {
		this.sedeFecha = sedeFecha;
	}

	public String getNombreProblema() {
		return nombreProblema;
	}

	public void setNombreProblema(String nombreProblema) {
		this.nombreProblema = nombreProblema;
	}

	public String getDescripcionProblema() {
		return descripcionProblema;
	}

	public void setDescripcionProblema(String descripcionProblema) {
		this.descripcionProblema = descripcionProblema;
	}

	public String getUbicacionProblema() {
		return ubicacionProblema;
	}

	public void setUbicacionProblema(String ubicacionProblema) {
		this.ubicacionProblema = ubicacionProblema;
	}

	public String getComunidadObjetivoProblema() {
		return comunidadObjetivoProblema;
	}

	public void setComunidadObjetivoProblema(String comunidadObjetivoProblema) {
		this.comunidadObjetivoProblema = comunidadObjetivoProblema;
	}

	public Departamento getDepartamentoProblema() {
		return departamentoProblema;
	}

	public void setDepartamentoProblema(Departamento departamentoProblema) {
		this.departamentoProblema = departamentoProblema;
	}

	public Ciudad getCiudadProblema() {
		return ciudadProblema;
	}

	public void setCiudadProblema(Ciudad ciudadProblema) {
		this.ciudadProblema = ciudadProblema;
	}

	public boolean isTieneArchivos() {
		return tieneArchivos;
	}

	public void setTieneArchivos(boolean tieneArchivos) {
		this.tieneArchivos = tieneArchivos;
	}

	public String getTipoVinculacionNombre() {
		return tipoVinculacionNombre;
	}

	public void setTipoVinculacionNombre(String tipoVinculacionNombre) {
		this.tipoVinculacionNombre = tipoVinculacionNombre;
	}

	public String getOtroMedioPublicidad() {
		return otroMedioPublicidad;
	}

	public void setOtroMedioPublicidad(String otroMedioPublicidad) {
		this.otroMedioPublicidad = otroMedioPublicidad;
	}

}