package co.edu.unal.hermes.modelo.laboratorios;

import java.util.Date;
import java.util.List;

import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.Edificio;
import co.edu.unal.hermes.modelo.Empresa;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.servicioGeneral.ServicioGeneral;

public class LaboratorioDetalleEquipos {

	private Long id;
	private Laboratorio laboratorio;
	private String placa;
	private String serial;
	private String equipo;
	private String marca;
	private String modelo;
	private Boolean especializado;
	private Long estadoFisico;
	private Boolean mayorDiezAnnios;
	private Date fechaRegistro;
	private Boolean enUso;
	private Long estadoFisicoCenso;
	private Long mantenimiento;
	private Long calibracion;
	private String nombreEstadoFisico;
	private String idInterna;
	
	private String hermesQuipu;

	private Integer idResponsable;
	private String responsable;
	private String responsableEmail;
	private Date fechaServicio;
	private Date fechaAdquisicion;
	private Long valor;

	private Integer annioFabricacion;
	private Integer mesesGarantia;
	private Integer vidaUtilAnnios;
	private String accesorios;
	private String software;
	
	private String firmware;
	private String ubicacionEspecifica;

	private Empresa empresaFabricante;
	private Empresa empresaDistribuidora;
	private String nombreVendendor;
	private String emailVendedor;
	private String telefonoVendedor;
	private String nombreInstructor;

	private String sistemaAlimentacion;
	private Integer potenciaW;
	private Integer voltajeV;
	private Float largoCm;
	private Float anchoCm;
	private Float altoCm;
	private Float pesoKg;
	private String instalaciones;
	private String condicionesAlmacenamiento;
	private String observaciones;

	private String proceso;
	private String ordenDeCompra;
	private Tipos mecanismoAdquisicion;

	private Boolean manualesOperacion;
	private Boolean manualesInstalacion;
	private Date fechaInstalacion;

	private String documentoPersonaRegistro;
	private String tipoDocumentoPersonaRegistro;

	private String ubicacion;
	private Sede sede;
	private Dependencia facultad;
	private Dependencia departamento;
	private Edificio edificio;
	private String salon;
	private String telefono;

	private Boolean instrumentoMedicion;
	private Boolean requiereMantenimiento;

	private Boolean tieneHojaDeVida;

	private Boolean existeEnBienes;
	private Date fechaBusquedaEnBienes;
	private String diferenciasConBienes;
	private Boolean dadoDeBaja;

	private Integer porcentajeCompletitud;
	private String informacionFaltante;
	
	private Boolean estaCalibrado;
	private Date fechaCalibrado;
	private String resolucion;
	private Tipos dondeCalibracion;
	private Tipos incertidumbre;
	private String valorCalibracion;
	private String factorCoberturaK;
	private String entidadCalibra;
	private String tipoDocEntidadCalibra;
	private String numDocumentoEntidadCalibra;
	
	private Boolean danado;
	private Boolean perdido;
	
	private String sedeEquipoInventarios;
	
//	private Tipos razonNoQuipu;
	private String razonNoQuipuOtros;
	private Long razonNoQuipu;
	
	private Boolean noAplicaVoltaje;
	private Boolean noAplicaPotencia;
	
	//Solicitudes
	private LaboratorioSolicitud solicitudEliminarActiva;
	
	//ROBUSTO
	private Boolean robusto;
	private Boolean robustoEspecialidadCalidadAnalitica;
	private Boolean robustoMayorValorReferencia;
	private Boolean robustoAltaPrecision;
	private Boolean robustoAltaExactitud;
	
	private Date fechaDadoBaja;
	private String motivoDadoBaja;
	
	//Criticidad Equipos
	private Tipos critEquiposImpactoOperaLab;
	private Tipos critEquiposImpactoSegUsuarios;
	private	Tipos critEquiposImpactoDaniosInfra;
	private	Tipos critEquiposImpactoDaniosAmbient;
	private	Tipos critEquiposImpactoImagenUN;
	private	Tipos critEquiposImpactoQuejas;
	private	Tipos critEquiposImpactoEconomicos;
		
	private	Tipos critEquiposProbRepFalla;
	private	Tipos critEquiposProbTiempoTrabajo;
	private	Tipos critEquiposProbCondAmbient;
	private	Tipos critEquiposProbMetrologia;
	
	private	Float critEquiposValorImpacto;
	private	Float critEquiposValorProbabilidad;
	private	Float critEquiposValorRiesgo;
	
	private String colorValorRiesgo;

	public LaboratorioDetalleEquipos() {
	}

	@Override
	public boolean equals(Object otroObjeto) {
		LaboratorioDetalleEquipos otroEquipo = (LaboratorioDetalleEquipos) otroObjeto;
		if (id != null && otroEquipo.getId() != null) {
			return id.equals(otroEquipo.getId());
		}
		return placa.equals(otroEquipo.getPlaca());
	}

	@Override
	public String toString() {
		return placa + " " + equipo;
	}
	
	public void obtenerEstiloValorRiesgo() {
        if (critEquiposValorRiesgo == null) {
        	colorValorRiesgo = "";
        } else if (critEquiposValorRiesgo < 5) {
        	colorValorRiesgo = "#84e8aa;";
        } else if (critEquiposValorRiesgo < 10) {
        	colorValorRiesgo = "#f0e948;";
        } else if (critEquiposValorRiesgo < 30) {
        	colorValorRiesgo = "#f0a24e;";
        } else {
        	colorValorRiesgo = "#fc5d5d;";
        }
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
	 * @return the laboratorio
	 */
	public Laboratorio getLaboratorio() {
		return laboratorio;
	}

	/**
	 * @param laboratorio
	 *            the laboratorio to set
	 */
	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
	}

	/**
	 * @return the placa
	 */
	public String getPlaca() {
		return placa;
	}

	/**
	 * @param placa
	 *            the placa to set
	 */
	public void setPlaca(String placa) {
		this.placa = placa;
	}

	/**
	 * @return the serial
	 */
	public String getSerial() {
		return serial;
	}

	/**
	 * @param serial
	 *            the serial to set
	 */
	public void setSerial(String serial) {
		this.serial = serial;
	}

	/**
	 * @return the equipo
	 */
	public String getEquipo() {
		return equipo;
	}

	/**
	 * @param equipo
	 *            the equipo to set
	 */
	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}

	/**
	 * @return the marca
	 */
	public String getMarca() {
		return marca;
	}

	/**
	 * @param marca
	 *            the marca to set
	 */
	public void setMarca(String marca) {
		this.marca = marca;
	}

	/**
	 * @return the modelo
	 */
	public String getModelo() {
		return modelo;
	}

	/**
	 * @param modelo
	 *            the modelo to set
	 */
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	/**
	 * @return the especializado
	 */
	public Boolean getEspecializado() {
		return especializado;
	}

	/**
	 * @param especializado
	 *            the especializado to set
	 */
	public void setEspecializado(Boolean especializado) {
		this.especializado = especializado;
	}

	/**
	 * @return the mayorDiezAnnios
	 */
	public Boolean getMayorDiezAnnios() {
		return mayorDiezAnnios;
	}

	/**
	 * @param mayorDiezAnnios
	 *            the mayorDiezAnnios to set
	 */
	public void setMayorDiezAnnios(Boolean mayorDiezAnnios) {
		this.mayorDiezAnnios = mayorDiezAnnios;
	}

	/**
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @param fechaRegistro
	 *            the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	/**
	 * @return the enUso
	 */
	public Boolean getEnUso() {
		return enUso;
	}

	/**
	 * @param enUso
	 *            the enUso to set
	 */
	public void setEnUso(Boolean enUso) {
		this.enUso = enUso;
	}

	/**
	 * @return the estadoFisico
	 */
	public Long getEstadoFisico() {
		return estadoFisico;
	}

	/**
	 * @param estadoFisico
	 *            the estadoFisico to set
	 */
	public void setEstadoFisico(Long estadoFisico) {
		this.estadoFisico = estadoFisico;
	}

	/**
	 * @return the estadoFisicoCenso
	 */
	public Long getEstadoFisicoCenso() {
		return estadoFisicoCenso;
	}

	/**
	 * @param estadoFisicoCenso
	 *            the estadoFisicoCenso to set
	 */
	public void setEstadoFisicoCenso(Long estadoFisicoCenso) {
		this.estadoFisicoCenso = estadoFisicoCenso;
	}

	/**
	 * @return the mantenimiento
	 */
	public Long getMantenimiento() {
		return mantenimiento;
	}

	/**
	 * @param mantenimiento
	 *            the mantenimiento to set
	 */
	public void setMantenimiento(Long mantenimiento) {
		this.mantenimiento = mantenimiento;
	}

	/**
	 * @return the calibracion
	 */
	public Long getCalibracion() {
		return calibracion;
	}

	/**
	 * @param calibracion
	 *            the calibracion to set
	 */
	public void setCalibracion(Long calibracion) {
		this.calibracion = calibracion;
	}

	/**
	 * @return the nombreEstadoFisico
	 */
	public String getNombreEstadoFisico() {
		Tipos tipo = new Tipos(estadoFisico);
		nombreEstadoFisico = tipo.getNombreTipo();
		return nombreEstadoFisico;
	}

	/**
	 * @return the idResponsable
	 */
	public Integer getIdResponsable() {
		return idResponsable;
	}

	/**
	 * @param idResponsable
	 *            the idResponsable to set
	 */
	public void setIdResponsable(Integer idResponsable) {
		this.idResponsable = idResponsable;
	}

	/**
	 * @return the responsable
	 */
	public String getResponsable() {
		return responsable;
	}

	/**
	 * @param responsable
	 *            the responsable to set
	 */
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	/**
	 * @return the fechaServicio
	 */
	public Date getFechaServicio() {
		return fechaServicio;
	}

	/**
	 * @param fechaServicio
	 *            the fechaServicio to set
	 */
	public void setFechaServicio(Date fechaServicio) {
		this.fechaServicio = fechaServicio;
	}

	/**
	 * @return the fechaAdquisicion
	 */
	public Date getFechaAdquisicion() {
		return fechaAdquisicion;
	}

	/**
	 * @param fechaAdquisicion
	 *            the fechaAdquisicion to set
	 */
	public void setFechaAdquisicion(Date fechaAdquisicion) {
		this.fechaAdquisicion = fechaAdquisicion;
	}

	/**
	 * @return the valor
	 */
	public Long getValor() {
		return valor;
	}

	/**
	 * @param valor
	 *            the valor to set
	 */
	public void setValor(Long valor) {
		this.valor = valor;
	}

	/**
	 * @return the annioFabricacion
	 */
	public Integer getAnnioFabricacion() {
		return annioFabricacion;
	}

	/**
	 * @param annioFabricacion
	 *            the annioFabricacion to set
	 */
	public void setAnnioFabricacion(Integer annioFabricacion) {
		this.annioFabricacion = annioFabricacion;
	}

	/**
	 * @return the mesesGarantia
	 */
	public Integer getMesesGarantia() {
		return mesesGarantia;
	}

	/**
	 * @param mesesGarantia
	 *            the mesesGarantia to set
	 */
	public void setMesesGarantia(Integer mesesGarantia) {
		this.mesesGarantia = mesesGarantia;
	}

	/**
	 * @return the vidaUtilAnnios
	 */
	public Integer getVidaUtilAnnios() {
		return vidaUtilAnnios;
	}

	/**
	 * @param vidaUtilAnnios
	 *            the vidaUtilAnnios to set
	 */
	public void setVidaUtilAnnios(Integer vidaUtilAnnios) {
		this.vidaUtilAnnios = vidaUtilAnnios;
	}

	/**
	 * @return the accesorios
	 */
	public String getAccesorios() {
		return accesorios;
	}

	/**
	 * @param accesorios
	 *            the accesorios to set
	 */
	public void setAccesorios(String accesorios) {
		this.accesorios = accesorios;
	}

	/**
	 * @return the software
	 */
	public String getSoftware() {
		return software;
	}

	/**
	 * @param software
	 *            the software to set
	 */
	public void setSoftware(String software) {
		this.software = software;
	}

	/**
	 * @return the empresaFabricante
	 */
	public Empresa getEmpresaFabricante() {
		return empresaFabricante;
	}

	/**
	 * @param empresaFabricante
	 *            the empresaFabricante to set
	 */
	public void setEmpresaFabricante(Empresa empresaFabricante) {
		this.empresaFabricante = empresaFabricante;
	}

	/**
	 * @return the empresaDistribuidora
	 */
	public Empresa getEmpresaDistribuidora() {
		return empresaDistribuidora;
	}

	/**
	 * @param empresaDistribuidora
	 *            the empresaDistribuidora to set
	 */
	public void setEmpresaDistribuidora(Empresa empresaDistribuidora) {
		this.empresaDistribuidora = empresaDistribuidora;
	}

	/**
	 * @return the nombreVendendor
	 */
	public String getNombreVendendor() {
		return nombreVendendor;
	}

	/**
	 * @param nombreVendendor
	 *            the nombreVendendor to set
	 */
	public void setNombreVendendor(String nombreVendendor) {
		this.nombreVendendor = nombreVendendor;
	}

	/**
	 * @return the emailVendedor
	 */
	public String getEmailVendedor() {
		return emailVendedor;
	}

	/**
	 * @param emailVendedor
	 *            the emailVendedor to set
	 */
	public void setEmailVendedor(String emailVendedor) {
		this.emailVendedor = emailVendedor;
	}

	/**
	 * @return the nombreInstructor
	 */
	public String getNombreInstructor() {
		return nombreInstructor;
	}

	/**
	 * @param nombreInstructor
	 *            the nombreInstructor to set
	 */
	public void setNombreInstructor(String nombreInstructor) {
		this.nombreInstructor = nombreInstructor;
	}

	/**
	 * @return the largoCm
	 */
	public Float getLargoCm() {
		return largoCm;
	}

	/**
	 * @param largoCm
	 *            the largoCm to set
	 */
	public void setLargoCm(Float largoCm) {
		this.largoCm = largoCm;
	}

	/**
	 * @return the anchoCm
	 */
	public Float getAnchoCm() {
		return anchoCm;
	}

	/**
	 * @param anchoCm
	 *            the anchoCm to set
	 */
	public void setAnchoCm(Float anchoCm) {
		this.anchoCm = anchoCm;
	}

	/**
	 * @return the altoCm
	 */
	public Float getAltoCm() {
		return altoCm;
	}

	/**
	 * @param altoCm
	 *            the altoCm to set
	 */
	public void setAltoCm(Float altoCm) {
		this.altoCm = altoCm;
	}

	/**
	 * @return the pesoKg
	 */
	public Float getPesoKg() {
		return pesoKg;
	}

	/**
	 * @param pesoKg
	 *            the pesoKg to set
	 */
	public void setPesoKg(Float pesoKg) {
		this.pesoKg = pesoKg;
	}

	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones
	 *            the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	/**
	 * @param nombreEstadoFisico
	 *            the nombreEstadoFisico to set
	 */
	public void setNombreEstadoFisico(String nombreEstadoFisico) {
		this.nombreEstadoFisico = nombreEstadoFisico;
	}

	/**
	 * @return the sistemaAlimentacion
	 */
	public String getSistemaAlimentacion() {
		return sistemaAlimentacion;
	}

	/**
	 * @param sistemaAlimentacion
	 *            the sistemaAlimentacion to set
	 */
	public void setSistemaAlimentacion(String sistemaAlimentacion) {
		this.sistemaAlimentacion = sistemaAlimentacion;
	}

	/**
	 * @return the instalaciones
	 */
	public String getInstalaciones() {
		return instalaciones;
	}

	/**
	 * @param instalaciones
	 *            the instalaciones to set
	 */
	public void setInstalaciones(String instalaciones) {
		this.instalaciones = instalaciones;
	}

	/**
	 * @return the condicionesAlmacenamiento
	 */
	public String getCondicionesAlmacenamiento() {
		return condicionesAlmacenamiento;
	}

	/**
	 * @param condicionesAlmacenamiento
	 *            the condicionesAlmacenamiento to set
	 */
	public void setCondicionesAlmacenamiento(String condicionesAlmacenamiento) {
		this.condicionesAlmacenamiento = condicionesAlmacenamiento;
	}

	/**
	 * @return the telefonoVendedor
	 */
	public String getTelefonoVendedor() {
		return telefonoVendedor;
	}

	/**
	 * @param telefonoVendedor
	 *            the telefonoVendedor to set
	 */
	public void setTelefonoVendedor(String telefonoVendedor) {
		this.telefonoVendedor = telefonoVendedor;
	}

	/**
	 * @return the proceso
	 */
	public String getProceso() {
		return proceso;
	}

	/**
	 * @param proceso
	 *            the proceso to set
	 */
	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	/**
	 * @return the ordenDeCompra
	 */
	public String getOrdenDeCompra() {
		return ordenDeCompra;
	}

	/**
	 * @param ordenDeCompra
	 *            the ordenDeCompra to set
	 */
	public void setOrdenDeCompra(String ordenDeCompra) {
		this.ordenDeCompra = ordenDeCompra;
	}

	/**
	 * @return the mecanismoAdquisicion
	 */
	public Tipos getMecanismoAdquisicion() {
		return mecanismoAdquisicion;
	}

	/**
	 * @param mecanismoAdquisicion
	 *            the mecanismoAdquisicion to set
	 */
	public void setMecanismoAdquisicion(Tipos mecanismoAdquisicion) {
		this.mecanismoAdquisicion = mecanismoAdquisicion;
	}

	/**
	 * @return the manualesOperacion
	 */
	public Boolean getManualesOperacion() {
		return manualesOperacion;
	}

	/**
	 * @param manualesOperacion
	 *            the manualesOperacion to set
	 */
	public void setManualesOperacion(Boolean manualesOperacion) {
		this.manualesOperacion = manualesOperacion;
	}

	/**
	 * @return the manualesInstalacion
	 */
	public Boolean getManualesInstalacion() {
		return manualesInstalacion;
	}

	/**
	 * @param manualesInstalacion
	 *            the manualesInstalacion to set
	 */
	public void setManualesInstalacion(Boolean manualesInstalacion) {
		this.manualesInstalacion = manualesInstalacion;
	}

	/**
	 * @return the fechaInstalacion
	 */
	public Date getFechaInstalacion() {
		return fechaInstalacion;
	}

	/**
	 * @param fechaInstalacion
	 *            the fechaInstalacion to set
	 */
	public void setFechaInstalacion(Date fechaInstalacion) {
		this.fechaInstalacion = fechaInstalacion;
	}

	/**
	 * @return the documentoPersonaRegistro
	 */
	public String getDocumentoPersonaRegistro() {
		return documentoPersonaRegistro;
	}

	/**
	 * @param documentoPersonaRegistro
	 *            the documentoPersonaRegistro to set
	 */
	public void setDocumentoPersonaRegistro(String documentoPersonaRegistro) {
		this.documentoPersonaRegistro = documentoPersonaRegistro;
	}

	/**
	 * @return the tipoDocumentoPersonaRegistro
	 */
	public String getTipoDocumentoPersonaRegistro() {
		return tipoDocumentoPersonaRegistro;
	}

	/**
	 * @param tipoDocumentoPersonaRegistro
	 *            the tipoDocumentoPersonaRegistro to set
	 */
	public void setTipoDocumentoPersonaRegistro(
			String tipoDocumentoPersonaRegistro) {
		this.tipoDocumentoPersonaRegistro = tipoDocumentoPersonaRegistro;
	}

	/**
	 * @return the ubicacion
	 */
	public String getUbicacion() {
		return ubicacion;
	}

	public String getUbicacionEquipo() {
		if (laboratorio != null) {
			return laboratorio.getNombreCorto();
		} else {
			return ubicacion;
		}
	}

	/**
	 * @param ubicacion
	 *            the ubicacion to set
	 */
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	/**
	 * @return the sede
	 */
	public Sede getSede() {
		return sede;
	}

	public Sede getSedeEquipo() {
		if (laboratorio != null) {
			return laboratorio.getSede();
		} else {
			return sede;
		}
	}

	/**
	 * @param sede
	 *            the sede to set
	 */
	public void setSede(Sede sede) {
		this.sede = sede;
	}

	/**
	 * @return the departamento
	 */
	public Dependencia getDepartamento() {
		return departamento;
	}

	/**
	 * @param departamento
	 *            the departamento to set
	 */
	public void setDepartamento(Dependencia departamento) {
		this.departamento = departamento;
	}

	/**
	 * @return the facultad
	 */
	public Dependencia getFacultad() {
		return facultad;
	}

	public Dependencia getFacultadEquipo() {
		if (laboratorio != null) {
			return laboratorio.getFacultad();
		} else {
			return facultad;
		}
	}

	/**
	 * @param facultad
	 *            the facultad to set
	 */
	public void setFacultad(Dependencia facultad) {
		this.facultad = facultad;
	}

	/**
	 * @return the edificio
	 */
	public Edificio getEdificio() {
		return edificio;
	}

	/**
	 * @param edificio
	 *            the edificio to set
	 */
	public void setEdificio(Edificio edificio) {
		this.edificio = edificio;
	}

	/**
	 * @return the salon
	 */
	public String getSalon() {
		return salon;
	}

	/**
	 * @param salon
	 *            the salon to set
	 */
	public void setSalon(String salon) {
		this.salon = salon;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono
	 *            the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the instrumentoMedicion
	 */
	public Boolean getInstrumentoMedicion() {
//		if (instrumentoMedicion == null) {
//			instrumentoMedicion = false;
//		}
		return instrumentoMedicion;
	}

	/**
	 * @param instrumentoMedicion
	 *            the instrumentoMedicion to set
	 */
	public void setInstrumentoMedicion(Boolean instrumentoMedicion) {
		this.instrumentoMedicion = instrumentoMedicion;
	}

	/**
	 * @return the tieneHojaDeVida
	 */
	public Boolean getTieneHojaDeVida() {
		if (tieneHojaDeVida == null) {
			tieneHojaDeVida = false;
		}
		return tieneHojaDeVida;
	}

	/**
	 * @param tieneHojaDeVida
	 *            the tieneHojaDeVida to set
	 */
	public void setTieneHojaDeVida(Boolean tieneHojaDeVida) {
		this.tieneHojaDeVida = tieneHojaDeVida;
	}

	/**
	 * @return the potenciaW
	 */
	public Integer getPotenciaW() {
		return potenciaW;
	}

	/**
	 * @param potenciaW
	 *            the potenciaW to set
	 */
	public void setPotenciaW(Integer potenciaW) {
		this.potenciaW = potenciaW;
	}

	/**
	 * @return the voltajeV
	 */
	public Integer getVoltajeV() {
		return voltajeV;
	}

	/**
	 * @param voltajeV
	 *            the voltajeV to set
	 */
	public void setVoltajeV(Integer voltajeV) {
		this.voltajeV = voltajeV;
	}

	/**
	 * @return the idInterna
	 */
	public String getIdInterna() {
		return idInterna;
	}

	/**
	 * @param idInterna
	 *            the idInterna to set
	 */
	public void setIdInterna(String idInterna) {
		this.idInterna = idInterna;
	}

	/**
	 * @return the existeEnBienes
	 */
	public Boolean getExisteEnBienes() {
		return existeEnBienes;
	}

	/**
	 * @param existeEnBienes
	 *            the existeEnBienes to set
	 */
	public void setExisteEnBienes(Boolean existeEnBienes) {
		this.existeEnBienes = existeEnBienes;
	}

	/**
	 * @return the fechaBusquedaEnBienes
	 */
	public Date getFechaBusquedaEnBienes() {
		return fechaBusquedaEnBienes;
	}

	/**
	 * @param fechaBusquedaEnBienes
	 *            the fechaBusquedaEnBienes to set
	 */
	public void setFechaBusquedaEnBienes(Date fechaBusquedaEnBienes) {
		this.fechaBusquedaEnBienes = fechaBusquedaEnBienes;
	}

	/**
	 * @return the diferenciasConBienes
	 */
	public String getDiferenciasConBienes() {
		return diferenciasConBienes;
	}

	/**
	 * @param diferenciasConBienes
	 *            the diferenciasConBienes to set
	 */
	public void setDiferenciasConBienes(String diferenciasConBienes) {
		this.diferenciasConBienes = diferenciasConBienes;
	}

	/**
	 * @return the dadoDeBaja
	 */
	public Boolean getDadoDeBaja() {
		return dadoDeBaja;
	}

	/**
	 * @param dadoDeBaja
	 *            the dadoDeBaja to set
	 */
	public void setDadoDeBaja(Boolean dadoDeBaja) {
		this.dadoDeBaja = dadoDeBaja;
	}

	public String getNombreCorto() {
		int longitudNombre = equipo.length();
		String nombreCorto;
		if (longitudNombre > 12) {
			nombreCorto = equipo.substring(0, 12) + "...";
		} else {
			nombreCorto = equipo;
		}
		return nombreCorto;
	}

	/**
	 * @return the porcentajeCompletitud
	 */
	public Integer getPorcentajeCompletitud() {
		return porcentajeCompletitud;
	}

	/**
	 * @param porcentajeCompletitud
	 *            the porcentajeCompletitud to set
	 */
	public void setPorcentajeCompletitud(Integer porcentajeCompletitud) {
		this.porcentajeCompletitud = porcentajeCompletitud;
	}

	/**
	 * @return the informacionFaltante
	 */
	public String getInformacionFaltante() {
		return informacionFaltante;
	}

	/**
	 * @param informacionFaltante
	 *            the informacionFaltante to set
	 */
	public void setInformacionFaltante(String informacionFaltante) {
		this.informacionFaltante = informacionFaltante;
	}

	public Boolean getRequiereMantenimiento() {
//		if (requiereMantenimiento == null) {
//			requiereMantenimiento = false;
//		}
			return requiereMantenimiento;
	}

	public void setRequiereMantenimiento(Boolean requiereMantenimiento) {
		this.requiereMantenimiento = requiereMantenimiento;
	}

	public Boolean getEstaCalibrado() {
		return estaCalibrado;
	}

	public void setEstaCalibrado(Boolean estaCalibrado) {
		this.estaCalibrado = estaCalibrado;
	}

	public Date getFechaCalibrado() {
		return fechaCalibrado;
	}

	public void setFechaCalibrado(Date fechaCalibrado) {
		this.fechaCalibrado = fechaCalibrado;
	}

	public String getResolucion() {
		return resolucion;
	}

	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}

	public Tipos getDondeCalibracion() {
		return dondeCalibracion;
	}

	public void setDondeCalibracion(Tipos dondeCalibracion) {
		this.dondeCalibracion = dondeCalibracion;
	}

	public Tipos getIncertidumbre() {
		return incertidumbre;
	}

	public void setIncertidumbre(Tipos incertidumbre) {
		this.incertidumbre = incertidumbre;
	}

	public String getValorCalibracion() {
		return valorCalibracion;
	}

	public void setValorCalibracion(String valorCalibracion) {
		this.valorCalibracion = valorCalibracion;
	}

	public String getFactorCoberturaK() {
		return factorCoberturaK;
	}

	public void setFactorCoberturaK(String factorCoberturaK) {
		this.factorCoberturaK = factorCoberturaK;
	}

	public String getEntidadCalibra() {
		return entidadCalibra;
	}

	public void setEntidadCalibra(String entidadCalibra) {
		this.entidadCalibra = entidadCalibra;
	}

	public String getTipoDocEntidadCalibra() {
		return tipoDocEntidadCalibra;
	}

	public void setTipoDocEntidadCalibra(String tipoDocEntidadCalibra) {
		this.tipoDocEntidadCalibra = tipoDocEntidadCalibra;
	}

	public String getNumDocumentoEntidadCalibra() {
		return numDocumentoEntidadCalibra;
	}

	public void setNumDocumentoEntidadCalibra(String numDocumentoEntidadCalibra) {
		this.numDocumentoEntidadCalibra = numDocumentoEntidadCalibra;
	}

	public Boolean getDanado() {
		return danado;
	}

	public void setDanado(Boolean danado) {
		this.danado = danado;
	}

	public Boolean getPerdido() {
		return perdido;
	}

	public void setPerdido(Boolean perdido) {
		this.perdido = perdido;
	}

//	public Tipos getRazonNoQuipu() {
//		return razonNoQuipu;
//	}
//
//	public void setRazonNoQuipu(Tipos razonNoQuipu) {
//		this.razonNoQuipu = razonNoQuipu;
//	}
	
	

	public String getRazonNoQuipuOtros() {
		return razonNoQuipuOtros;
	}

	public Long getRazonNoQuipu() {
		return razonNoQuipu;
	}

	public void setRazonNoQuipu(Long razonNoQuipu) {
		this.razonNoQuipu = razonNoQuipu;
	}

	public void setRazonNoQuipuOtros(String razonNoQuipuOtros) {
		this.razonNoQuipuOtros = razonNoQuipuOtros;
	}

	public String getSedeEquipoInventarios() {
		return sedeEquipoInventarios;
	}

	public void setSedeEquipoInventarios(String sedeEquipoInventarios) {
		this.sedeEquipoInventarios = sedeEquipoInventarios;
	}

	public Boolean getNoAplicaVoltaje() {
		return noAplicaVoltaje;
	}

	public void setNoAplicaVoltaje(Boolean noAplicaVoltaje) {
		this.noAplicaVoltaje = noAplicaVoltaje;
	}

	public Boolean getNoAplicaPotencia() {
		return noAplicaPotencia;
	}
	
	public String getHermesQuipu() {
		return hermesQuipu;
	}

	public void setHermesQuipu(String hermesQuipu) {
		this.hermesQuipu = hermesQuipu;
	}

	public void setNoAplicaPotencia(Boolean noAplicaPotencia) {
		this.noAplicaPotencia = noAplicaPotencia;
	}

	public LaboratorioSolicitud getSolicitudEliminarActiva() {
		return solicitudEliminarActiva;
	}

	public void setSolicitudEliminarActiva(LaboratorioSolicitud solicitudEliminarActiva) {
		this.solicitudEliminarActiva = solicitudEliminarActiva;
	}

	public String getResponsableEmail() {
		return responsableEmail;
	}

	public void setResponsableEmail(String responsableEmail) {
		this.responsableEmail = responsableEmail;
	}

	public String getFirmware() {
		return firmware;
	}

	public void setFirmware(String firmware) {
		this.firmware = firmware;
	}

	public String getUbicacionEspecifica() {
		return ubicacionEspecifica;
	}

	public void setUbicacionEspecifica(String ubicacionEspecifica) {
		this.ubicacionEspecifica = ubicacionEspecifica;
	}

	public Boolean getRobusto() {
		return robusto;
	}

	public void setRobusto(Boolean robusto) {
		this.robusto = robusto;
	}

	public Boolean getRobustoEspecialidadCalidadAnalitica() {
		return robustoEspecialidadCalidadAnalitica;
	}

	public void setRobustoEspecialidadCalidadAnalitica(Boolean robustoEspecialidadCalidadAnalitica) {
		this.robustoEspecialidadCalidadAnalitica = robustoEspecialidadCalidadAnalitica;
	}

	public Boolean getRobustoMayorValorReferencia() {
		return robustoMayorValorReferencia;
	}

	public void setRobustoMayorValorReferencia(Boolean robustoMayorValorReferencia) {
		this.robustoMayorValorReferencia = robustoMayorValorReferencia;
	}

	public Boolean getRobustoAltaPrecision() {
		return robustoAltaPrecision;
	}

	public void setRobustoAltaPrecision(Boolean robustoAltaPrecision) {
		this.robustoAltaPrecision = robustoAltaPrecision;
	}

	public Boolean getRobustoAltaExactitud() {
		return robustoAltaExactitud;
	}

	public void setRobustoAltaExactitud(Boolean robustoAltaExactitud) {
		this.robustoAltaExactitud = robustoAltaExactitud;
	}

	public Date getFechaDadoBaja() {
		return fechaDadoBaja;
	}

	public void setFechaDadoBaja(Date fechaDadoBaja) {
		this.fechaDadoBaja = fechaDadoBaja;
	}

	public String getMotivoDadoBaja() {
		return motivoDadoBaja;
	}

	public void setMotivoDadoBaja(String motivoDadoBaja) {
		this.motivoDadoBaja = motivoDadoBaja;
	}

	public Tipos getCritEquiposImpactoOperaLab() {
		return critEquiposImpactoOperaLab;
	}

	public void setCritEquiposImpactoOperaLab(Tipos critEquiposImpactoOperaLab) {
		this.critEquiposImpactoOperaLab = critEquiposImpactoOperaLab;
	}

	public Tipos getCritEquiposImpactoSegUsuarios() {
		return critEquiposImpactoSegUsuarios;
	}

	public void setCritEquiposImpactoSegUsuarios(Tipos critEquiposImpactoSegUsuarios) {
		this.critEquiposImpactoSegUsuarios = critEquiposImpactoSegUsuarios;
	}

	public Tipos getCritEquiposImpactoDaniosInfra() {
		return critEquiposImpactoDaniosInfra;
	}

	public void setCritEquiposImpactoDaniosInfra(Tipos critEquiposImpactoDaniosInfra) {
		this.critEquiposImpactoDaniosInfra = critEquiposImpactoDaniosInfra;
	}

	public Tipos getCritEquiposImpactoDaniosAmbient() {
		return critEquiposImpactoDaniosAmbient;
	}

	public void setCritEquiposImpactoDaniosAmbient(Tipos critEquiposImpactoDaniosAmbient) {
		this.critEquiposImpactoDaniosAmbient = critEquiposImpactoDaniosAmbient;
	}

	public Tipos getCritEquiposImpactoImagenUN() {
		return critEquiposImpactoImagenUN;
	}

	public void setCritEquiposImpactoImagenUN(Tipos critEquiposImpactoImagenUN) {
		this.critEquiposImpactoImagenUN = critEquiposImpactoImagenUN;
	}

	public Tipos getCritEquiposImpactoQuejas() {
		return critEquiposImpactoQuejas;
	}

	public void setCritEquiposImpactoQuejas(Tipos critEquiposImpactoQuejas) {
		this.critEquiposImpactoQuejas = critEquiposImpactoQuejas;
	}

	public Tipos getCritEquiposImpactoEconomicos() {
		return critEquiposImpactoEconomicos;
	}

	public void setCritEquiposImpactoEconomicos(Tipos critEquiposImpactoEconomicos) {
		this.critEquiposImpactoEconomicos = critEquiposImpactoEconomicos;
	}

	public Tipos getCritEquiposProbRepFalla() {
		return critEquiposProbRepFalla;
	}

	public void setCritEquiposProbRepFalla(Tipos critEquiposProbRepFalla) {
		this.critEquiposProbRepFalla = critEquiposProbRepFalla;
	}

	public Tipos getCritEquiposProbTiempoTrabajo() {
		return critEquiposProbTiempoTrabajo;
	}

	public void setCritEquiposProbTiempoTrabajo(Tipos critEquiposProbTiempoTrabajo) {
		this.critEquiposProbTiempoTrabajo = critEquiposProbTiempoTrabajo;
	}

	public Tipos getCritEquiposProbCondAmbient() {
		return critEquiposProbCondAmbient;
	}

	public void setCritEquiposProbCondAmbient(Tipos critEquiposProbCondAmbient) {
		this.critEquiposProbCondAmbient = critEquiposProbCondAmbient;
	}

	public Tipos getCritEquiposProbMetrologia() {
		return critEquiposProbMetrologia;
	}

	public void setCritEquiposProbMetrologia(Tipos critEquiposProbMetrologia) {
		this.critEquiposProbMetrologia = critEquiposProbMetrologia;
	}

	public Float getCritEquiposValorImpacto() {
		return critEquiposValorImpacto;
	}

	public void setCritEquiposValorImpacto(Float critEquiposValorImpacto) {
		this.critEquiposValorImpacto = critEquiposValorImpacto;
	}

	public Float getCritEquiposValorProbabilidad() {
		return critEquiposValorProbabilidad;
	}

	public void setCritEquiposValorProbabilidad(Float critEquiposValorProbabilidad) {
		this.critEquiposValorProbabilidad = critEquiposValorProbabilidad;
	}

	public Float getCritEquiposValorRiesgo() {
		return critEquiposValorRiesgo;
	}

	public void setCritEquiposValorRiesgo(Float critEquiposValorRiesgo) {
		this.critEquiposValorRiesgo = critEquiposValorRiesgo;
		obtenerEstiloValorRiesgo();
	}

	public String getColorValorRiesgo() {
		return colorValorRiesgo;
	}

	public void setColorValorRiesgo(String colorValorRiesgo) {
		this.colorValorRiesgo = colorValorRiesgo;
	}
	
}
