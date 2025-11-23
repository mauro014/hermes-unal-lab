package co.edu.unal.hermes.modelo.laboratorios;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import co.edu.unal.hermes.modelo.Campus;
import co.edu.unal.hermes.modelo.ClasificacionConocimiento;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.Edificio;
import co.edu.unal.hermes.modelo.LaboratorioAval;
import co.edu.unal.hermes.modelo.LaboratorioODSSec;
import co.edu.unal.hermes.modelo.ObjetivoEspecifico;
import co.edu.unal.hermes.modelo.ProyectoProducto;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.Tipos;

/**
 * @author dgbenitezc
 */
public class Laboratorio {

	Integer etapaRegistro;
	Boolean activo;
	Long id;
	Sede sede;
	String nombre;
	Dependencia facultad;
	Dependencia departamento;
	Campus campus;
	Tipos deptoCiudad;
	Edificio edificio;
	String salon;
	Tipos tipo;
	Boolean tipoLabEnsayo;
	Boolean tipoLabMuestreo;
	Boolean tipoLabCalibracion;
	Boolean tipoLabOtro;
	String tipoLabOtroCual;
	
	String email;
	String paginaWeb;
	String piso;
	Float areaM2;
	String tipoTelefono;
	String telefono;
	String tipoTelefono2;
	String telefono2;
	String telefonoMask;
	String fax;
	String descripcion;
	Float dedicacionDocencia;
	Float dedicacionInvestigacion;
	Float dedicacionExtension;
	Date fechaCreacionRegistro;
	String registroDocumentoPersona;
	String registroTipoDocumentoPersona;

	String tipoActoCreacion;
	String numeroActoCreacion;
	String emanadaPorActoCreacion;
	Date fechaActoCreacion;

	Boolean personalCalificado;
	Boolean personalSuficiente;
	Tipos portafolioServicios;
	Boolean elementosProteccionPersonal;
	String elementosProteccionPersonalCual;

	Integer funcionariosPlanta;
	Integer funcionariosProvisionales;
	Integer funcionariosODS;
	Integer estudiantesAuxiliares;
	Integer estudiantesBecarios;
	Integer estudiantesMonitores;
	String observaciones;

	Long estadoInstalacionesFisicas;
	Long estadoClimatizacion;
	Long estadoVentilacion;
	Long estadoIluminacion;
	Long estadoRuido;

	Boolean requiereAmpliacion;
	Boolean requiereAdecuacion;
	Boolean requiereRedistribucion;
	Boolean requiereParedes;
	Boolean requiereCubiertas;
	Boolean requierePisos;
	String requiereOtros;
	Boolean requiereEquipos;

	Boolean riesgoBiologico;
	String riesgoBiologicoCual;
	Boolean riesgoQuimico;
	String riesgoQuimicoCual;
	Boolean riesgoElectrico;
	String riesgoElectricoCual;
	Boolean emisionesAtmosfericas;
	String emisionesAtmosfericasCual;
	Boolean exposicionAltaTension;
	String exposicionAltaTensionCual;

	Boolean residuosSolidosBiologicos;
	Boolean residuosSolidosQuimicos;
	Boolean residuosSolidosRadioactivos;
	Boolean residuosSolidosCitotoxicos;
	Boolean residuosSolidosElectricos;
	String residuosSolidosOtros;
	Boolean residuosLiquidosBiologicos;
	Boolean residuosLiquidosQuimicos;
	Boolean residuosLiquidosRadioactivos;
	String residuosLiquidosOtros;
	Integer seguridadExtintores;
	Boolean seguridadBotiquin;
	Boolean seguridadDuchaEmergencia;
	Boolean seguridadSenalizacionGeneral;
	Boolean seguridadSenalizacionSeguridad;
	Boolean seguridadSenalizacionEmergencia;

	Boolean radiacionExposicionIonizante;
	Boolean radiacionEquipoEmisor;
	String radiacionEquipoEmisorCual;
	Boolean radiacionFuente;
	String radiacionFuenteCual;
	Boolean radiacionMaterial;
	String radiacionMaterialCual;
	Boolean radiacionExposicionNoIonizante;
	String radiacionExposicionNoIonizanteCual;
	Boolean radiacionResiduosRadioactivos;
	String radiacionResiduosRadioactivosCual;
	String radiacionObservaciones;
	Boolean radiacionEmisionDiario;
	Boolean radiacionEmisionSemanal;
	Boolean radiacionEmisionMensual;
	Integer radiacionDosimetrosPersonales;
	Integer radiacionDosimetrosAmbientales;
	Integer radiacionDosimetrosDeControl;
	Boolean radiacionProgramaMonitoreo;
	Boolean radiacionCarneProteccionRadiologica;
	Boolean radiacionLicenciaManejoFuncionamiento;
	Boolean radiacionCuartoDecaimiento;

	Boolean manualProteccionRadiologica;
	Boolean manualProteccionBiologica;
	Boolean manualProteccionQuimica;
	Boolean manualProteccionElectrica;

	Boolean almacenamientoAdecuadoReactivos;

	Long gestionAcreditacion;
	Boolean gestionAcreditacionInteresado;
	String gestionAcreditacionNorma;
	Long gestionCertificacion;
	Boolean gestionCertificacionInteresado;
	String gestionCertificacionNorma;
	Long gestionHabilitacion;
	Boolean gestionHabilitacionRequiere;
	String gestionHabilitacionNorma;
	Long gestionRegistroIca;
	Boolean gestionRegistroIcaRequiere;
	String gestionRegistroIcaNorma;
	Long gestionLicencias;
	Boolean gestionLicenciasRequiere;
	String gestionLicenciasCual;
	
	Long gestionRequiere;
	String gestionRequiereNorma;
	Long gestionInteresado;
	String gestionInteresadoNorma;

	String equiposObservaciones;

	// Ensayos y Servicios:
	String horarioAtencion;
	String ensayosUsuariosActuales;
	String ensayosUsuariosPotenciales;

	// Investigación:
	String areaGeneralInvestigacion;
	String areasAplicacionInvestigacion;
	
	Integer totalEquipos;
	Integer totalEquiposMantto;
	Integer totalEquiposCalib;
	
	Boolean usaSustanciasControladas;

	private Set<ClasificacionConocimiento> clasificacionConocimientoLab = new HashSet<ClasificacionConocimiento>();
	
	private Set<LaboratorioAval> avales = new HashSet<LaboratorioAval>();

	public static String FALSO = "0";
	public static String VERDADERO = "1";
	public static String DEDICACION_DESCONOCIDA = "Desconocida";

	public Boolean personaActualEsCoordinador;

	private Float porcentajeCompletitud;
	private String informacionFaltante;

	// Solicitudes:
	public Tipos tipoSolicitud;
	public String justificacionSolicitud;
	public String infraestructuraSolicitud;
	public String personalSolicitud;
	public String sostenibilidad;
	
	private Set<Tipos> subredes = new HashSet<Tipos>();
	private Set<Tipos> capacitaciones = new HashSet<Tipos>();
	private Set<Tipos> actividades = new HashSet<Tipos>();
	private Set<Tipos> muestreos = new HashSet<Tipos>();
	private Set<Tipos> sistemasGestion = new HashSet<Tipos>();
	
	//Pestaña TARIFAS E/S
	private String linkBrochure;
	private String tipoActoAdminTarifas;
	private String numeroActoAdminTarifas;
	private String emitidaActoAdminTarifas;
	private Date fechaActoAdminTarifas;
	private String condicionesServicio;
	private String linkCondiciones;
	
	//Metrologia
	private Boolean aplicaMetrología;
	
	//Reglamento
	private	Tipos tipoReglamento;
	private	String linkReglamento;
	
	//ODS
	private Tipos ODSPrincipal;
	private Set<LaboratorioODSSec> ODSSecundarios = new HashSet<LaboratorioODSSec>();
	
	//Areas OCDE
	private Tipos areaPrincipal;
	private Tipos subAreaPrincipal;
	private Tipos objetivoSocioEconomico;
	
	Boolean validaInformacionLabXCoordinador;
	Date fechaValidaInformacionLabXCoordinador;
	
	Boolean ser_agua;
	Boolean ser_gas;
	Boolean ser_vacio;
	Boolean ser_aire_comprimido;
	Boolean ser_control_iluminacion;
	Boolean ser_aire_acondicionado;
	Boolean ser_insonorizacion;
	Boolean ser_circuito_cerrado_de_tv;
	Boolean ser_puntos_de_red;
	Boolean ser_otro;
	Boolean ser_110v;
	Boolean ser_220v;
	String ser_tension_requerida_en_voltios;
	String ser_agua_detalle;
	String ser_gas_detalle;
	String ser_vacio_detalle;
	String ser_aire_comprimido_detalle;
	String ser_control_iluminacion_detalle;
	String ser_aire_acondicionado_detalle;
	String ser_insonorizacion_detalle;
	String ser_circuito_cerrado_de_tv_detalle;
	String ser_puntos_de_red_detalle;
	String ser_otro_detalle;
	Boolean sga_biodegradables;
	Boolean sga_reciclables;
	Boolean sga_inertes;
	Boolean sga_ordinarios;
	Boolean sga_grasas_y_aceites;
	Boolean sga_lixiviados;
	Boolean sga_residuos_infecciosos;
	Boolean sga_residuos_quimicos;
	Boolean sga_residuos_radioactivos;
	Boolean sga_derivados_de_combustion;
	Boolean sga_no_derivados_de_combustion;
	Boolean sga_ruido;
	Boolean sga_olores_ofensivos;
	Boolean sga_aguas_domesticas;
	Boolean sga_aguas_de_interes_ambiental;
	Boolean sga_aguas_de_interes_sanitario;
	Boolean sga_otro;
	String sri_almacenamientoadecuadoreactivosdetalle;
	String sri_radiacionexposicionionizantedetalle;
	Boolean sri_animales;
	Boolean sri_humanos;
	Boolean sri_medicamentos_controlados;
	Boolean sri_gases_comprimidos;
	Boolean sri_material_biologico;
	Boolean sri_equipos_y_o_material_generador_de_radiacion_ionizante;
	Boolean sri_equipos_generadores_de_ruido_vibracion;
	Boolean sri_otro;
	String sri_animales_detalle;
	String sri_humanos_detalle;
	String sri_medicamentos_controlados_detalle;
	String sri_gases_comprimidos_detalle;
	String sri_material_biologico_detalle;
	String sri_equipos_y_o_material_generador_de_radiacion_ionizante_detalle;
	String sri_equipos_generadores_de_ruido_vibracion_detalle;
	String sri_otro_detalle;
	
	private Set<LaboratorioObservaciones> listaObservaciones = new HashSet<LaboratorioObservaciones>();
	
	// Criticidad equipos
	Float critEquiposImpactoOperaLab;
	Float critEquiposImpactoSegUsuarios;
	Float critEquiposImpactoDaniosInfra;
	Float critEquiposImpactoDaniosAmbient;
	Float critEquiposImpactoImagenUN;
	Float critEquiposImpactoQuejas;
	Float critEquiposImpactoEconomicos;
	
	Float critEquiposProbRepFalla;
	Float critEquiposProbTiempoTrabajo;
	Float critEquiposProbCondAmbient;
	Float critEquiposProbMetrologia;
	
	Date fechaInactivo;
	String justificacionInactivo;

	public Laboratorio() {
		super();
		// Valores por defecto:
		this.areaM2 = 0F;
		this.dedicacionDocencia = 0.0F;
		this.dedicacionExtension = 0.0F;
		this.dedicacionInvestigacion = 0.0F;

		this.funcionariosODS = 0;
		this.funcionariosPlanta = 0;
		this.funcionariosProvisionales = 0;
		this.estudiantesAuxiliares = 0;
		this.estudiantesBecarios = 0;
		this.estudiantesMonitores = 0;

		this.seguridadExtintores = 0;

		this.radiacionDosimetrosPersonales = 0;
		this.radiacionDosimetrosAmbientales = 0;
		this.radiacionDosimetrosDeControl = 0;

		this.radiacionDosimetrosAmbientales = 0;
		this.radiacionDosimetrosPersonales = 0;
		this.radiacionDosimetrosDeControl = 0;

		this.gestionAcreditacion = Tipos.TIPO_GESTION_LABORATORIO_NO;
		this.gestionCertificacion = Tipos.TIPO_GESTION_LABORATORIO_NO;
		this.gestionHabilitacion = Tipos.TIPO_GESTION_LABORATORIO_NO;
		this.gestionLicencias = Tipos.TIPO_GESTION_LABORATORIO_NO;
		this.gestionRegistroIca = Tipos.TIPO_GESTION_LABORATORIO_NO;

		this.portafolioServicios = new Tipos(Tipos.TIPOS_PORTAFOLIO_NINGUNO);

	}

	public String getDedicacion() {
		try {
			if ((dedicacionDocencia + dedicacionExtension + dedicacionInvestigacion) < 99) {
				return DEDICACION_DESCONOCIDA;
			}
			if (dedicacionDocencia == 0 && dedicacionExtension == 0
					&& dedicacionInvestigacion > 0) {
				return "Exclusiva Investigación";
			}
			if (dedicacionDocencia > 0 && dedicacionExtension == 0
					&& dedicacionInvestigacion == 0) {
				return "Exclusiva Docencia";
			}
			if (dedicacionDocencia == 0 && dedicacionExtension > 0
					&& dedicacionInvestigacion == 0) {
				return "Exclusiva Extensión";
			}
			if (dedicacionDocencia > 0 && dedicacionExtension > 0
					&& dedicacionInvestigacion == 0) {
				return "Docencia-Extensión";
			}
			if (dedicacionDocencia > 0 && dedicacionExtension == 0
					&& dedicacionInvestigacion > 0) {
				return "Docencia-Investigación";
			}
			if (dedicacionDocencia == 0 && dedicacionExtension > 0
					&& dedicacionInvestigacion > 0) {
				return "Extensión-Investigación";
			}
			if (dedicacionDocencia > 0 && dedicacionExtension > 0
					&& dedicacionInvestigacion > 0) {
				return "Docencia-Extensión-Investigación";
			}
			return DEDICACION_DESCONOCIDA;
		} catch (Exception e) {
			return DEDICACION_DESCONOCIDA;
		}
	}
	
	public void adicionarObservacion(LaboratorioObservaciones observacion) {
        if (listaObservaciones == null) listaObservaciones = new HashSet<LaboratorioObservaciones>();
        observacion.setLaboratorio(this);
        listaObservaciones.add(observacion);
    }
	
	public void borrarObservacion(LaboratorioObservaciones observacion) {
		listaObservaciones.remove(observacion);
    }
	
	public List<LaboratorioObservaciones> getListaObservacionesXTipo() {
        List<LaboratorioObservaciones> lista = new ArrayList<LaboratorioObservaciones>();
        lista.addAll(listaObservaciones);
        return lista;
    }

	public void adicionarODSSec(LaboratorioODSSec ODS) {
        if (ODSSecundarios == null) ODSSecundarios = new HashSet<LaboratorioODSSec>();
        ODS.setLaboratorio(this);
        ODSSecundarios.add(ODS);
    }
	
	public void borrarODSSec(LaboratorioODSSec ODS) {
		ODSSecundarios.remove(ODS);
    }
	
	public List getListaODSSec() {
        List<LaboratorioODSSec> lista = new ArrayList<LaboratorioODSSec>();
        lista.addAll(ODSSecundarios);
        return lista;
    }

	public Tipos getODSPrincipal() {
		return ODSPrincipal;
	}

	public void setODSPrincipal(Tipos oDSPrincipal) {
		ODSPrincipal = oDSPrincipal;
	}

	public Set<LaboratorioODSSec> getODSSecundarios() {
		return ODSSecundarios;
	}

	public void setODSSecundarios(Set<LaboratorioODSSec> oDSSecundarios) {
		ODSSecundarios = oDSSecundarios;
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
	 * @return the sede
	 */
	public Sede getSede() {
		return sede;
	}

	/**
	 * @param sede
	 *            the sede to set
	 */
	public void setSede(Sede sede) {
		this.sede = sede;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the facultad
	 */
	public Dependencia getFacultad() {
		return facultad;
	}

	/**
	 * @param facultad
	 *            the facultad to set
	 */
	public void setFacultad(Dependencia facultad) {
		this.facultad = facultad;
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
	 * @return the campus
	 */
	public Campus getCampus() {
		return campus;
	}

	/**
	 * @param campus
	 *            the campus to set
	 */
	public void setCampus(Campus campus) {
		this.campus = campus;
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the paginaWeb
	 */
	public String getPaginaWeb() {
		return paginaWeb;
	}

	/**
	 * @param paginaWeb
	 *            the paginaWeb to set
	 */
	public void setPaginaWeb(String paginaWeb) {
		this.paginaWeb = paginaWeb;
	}

	/**
	 * @return the piso
	 */
	public String getPiso() {
		return piso;
	}

	/**
	 * @param piso
	 *            the piso to set
	 */
	public void setPiso(String piso) {
		this.piso = piso;
	}

	/**
	 * @return the areaM2
	 */
	public Float getAreaM2() {
		return areaM2;
	}

	/**
	 * @param areaM2
	 *            the areaM2 to set
	 */
	public void setAreaM2(Float areaM2) {
		this.areaM2 = areaM2;
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
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax
	 *            the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the fechaCreacionRegistro
	 */
	public Date getFechaCreacionRegistro() {
		return fechaCreacionRegistro;
	}

	/**
	 * @param fechaCreacionRegistro
	 *            the fechaCreacionRegistro to set
	 */
	public void setFechaCreacionRegistro(Date fechaCreacionRegistro) {
		this.fechaCreacionRegistro = fechaCreacionRegistro;
	}

	/**
	 * @return the tipoActoCreacion
	 */
	public String getTipoActoCreacion() {
		return tipoActoCreacion;
	}

	/**
	 * @param tipoActoCreacion
	 *            the tipoActoCreacion to set
	 */
	public void setTipoActoCreacion(String tipoActoCreacion) {
		this.tipoActoCreacion = tipoActoCreacion;
	}

	/**
	 * @return the numeroActoCreacion
	 */
	public String getNumeroActoCreacion() {
		return numeroActoCreacion;
	}

	/**
	 * @param numeroActoCreacion
	 *            the numeroActoCreacion to set
	 */
	public void setNumeroActoCreacion(String numeroActoCreacion) {
		this.numeroActoCreacion = numeroActoCreacion;
	}

	/**
	 * @return the emanadaPorActoCreacion
	 */
	public String getEmanadaPorActoCreacion() {
		return emanadaPorActoCreacion;
	}

	/**
	 * @param emanadaPorActoCreacion
	 *            the emanadaPorActoCreacion to set
	 */
	public void setEmanadaPorActoCreacion(String emanadaPorActoCreacion) {
		this.emanadaPorActoCreacion = emanadaPorActoCreacion;
	}

	/**
	 * @return the fechaActoCreacion
	 */
	public Date getFechaActoCreacion() {
		return fechaActoCreacion;
	}

	/**
	 * @param fechaActoCreacion
	 *            the fechaActoCreacion to set
	 */
	public void setFechaActoCreacion(Date fechaActoCreacion) {
		this.fechaActoCreacion = fechaActoCreacion;
	}

	/**
	 * @return the etapaRegistro
	 */
	public Integer getEtapaRegistro() {
		return etapaRegistro;
	}

	/**
	 * @param etapaRegistro
	 *            the etapaRegistro to set
	 */
	public void setEtapaRegistro(Integer etapaRegistro) {
		this.etapaRegistro = etapaRegistro;
	}

	/**
	 * @return the funcionariosPlanta
	 */
	public Integer getFuncionariosPlanta() {
		return funcionariosPlanta;
	}

	/**
	 * @param funcionariosPlanta
	 *            the funcionariosPlanta to set
	 */
	public void setFuncionariosPlanta(Integer funcionariosPlanta) {
		this.funcionariosPlanta = funcionariosPlanta;
	}

	/**
	 * @return the funcionariosProvisionales
	 */
	public Integer getFuncionariosProvisionales() {
		return funcionariosProvisionales;
	}

	/**
	 * @param funcionariosProvisionales
	 *            the funcionariosProvisionales to set
	 */
	public void setFuncionariosProvisionales(Integer funcionariosProvisionales) {
		this.funcionariosProvisionales = funcionariosProvisionales;
	}

	/**
	 * @return the funcionariosODS
	 */
	public Integer getFuncionariosODS() {
		return funcionariosODS;
	}

	/**
	 * @param funcionariosODS
	 *            the funcionariosODS to set
	 */
	public void setFuncionariosODS(Integer funcionariosODS) {
		this.funcionariosODS = funcionariosODS;
	}

	/**
	 * @return the estudiantesAuxiliares
	 */
	public Integer getEstudiantesAuxiliares() {
		return estudiantesAuxiliares;
	}

	/**
	 * @param estudiantesAuxiliares
	 *            the estudiantesAuxiliares to set
	 */
	public void setEstudiantesAuxiliares(Integer estudiantesAuxiliares) {
		this.estudiantesAuxiliares = estudiantesAuxiliares;
	}

	/**
	 * @return the estudiantesBecarios
	 */
	public Integer getEstudiantesBecarios() {
		return estudiantesBecarios;
	}

	/**
	 * @param estudiantesBecarios
	 *            the estudiantesBecarios to set
	 */
	public void setEstudiantesBecarios(Integer estudiantesBecarios) {
		this.estudiantesBecarios = estudiantesBecarios;
	}

	/**
	 * @return the estudiantesMonitores
	 */
	public Integer getEstudiantesMonitores() {
		return estudiantesMonitores;
	}

	/**
	 * @param estudiantesMonitores
	 *            the estudiantesMonitores to set
	 */
	public void setEstudiantesMonitores(Integer estudiantesMonitores) {
		this.estudiantesMonitores = estudiantesMonitores;
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
	 * @return the dedicacionDocencia
	 */
	public Float getDedicacionDocencia() {
		return dedicacionDocencia;
	}

	/**
	 * @param dedicacionDocencia
	 *            the dedicacionDocencia to set
	 */
	public void setDedicacionDocencia(Float dedicacionDocencia) {
		this.dedicacionDocencia = dedicacionDocencia;
	}

	/**
	 * @return the dedicacionInvestigacion
	 */
	public Float getDedicacionInvestigacion() {
		return dedicacionInvestigacion;
	}

	/**
	 * @param dedicacionInvestigacion
	 *            the dedicacionInvestigacion to set
	 */
	public void setDedicacionInvestigacion(Float dedicacionInvestigacion) {
		this.dedicacionInvestigacion = dedicacionInvestigacion;
	}

	/**
	 * @return the dedicacionExtension
	 */
	public Float getDedicacionExtension() {
		return dedicacionExtension;
	}

	/**
	 * @param dedicacionExtension
	 *            the dedicacionExtension to set
	 */
	public void setDedicacionExtension(Float dedicacionExtension) {
		this.dedicacionExtension = dedicacionExtension;
	}

	/**
	 * @return the requiereAmpliacion
	 */
	public Boolean getRequiereAmpliacion() {
		return requiereAmpliacion;
	}

	/**
	 * @param requiereAmpliacion
	 *            the requiereAmpliacion to set
	 */
	public void setRequiereAmpliacion(Boolean requiereAmpliacion) {
		this.requiereAmpliacion = requiereAmpliacion;
	}

	/**
	 * @return the requiereAdecuacion
	 */
	public Boolean getRequiereAdecuacion() {
		return requiereAdecuacion;
	}

	/**
	 * @param requiereAdecuacion
	 *            the requiereAdecuacion to set
	 */
	public void setRequiereAdecuacion(Boolean requiereAdecuacion) {
		this.requiereAdecuacion = requiereAdecuacion;
	}

	/**
	 * @return the requiereRedistribucion
	 */
	public Boolean getRequiereRedistribucion() {
		return requiereRedistribucion;
	}

	/**
	 * @param requiereRedistribucion
	 *            the requiereRedistribucion to set
	 */
	public void setRequiereRedistribucion(Boolean requiereRedistribucion) {
		this.requiereRedistribucion = requiereRedistribucion;
	}

	/**
	 * @return the requiereParedes
	 */
	public Boolean getRequiereParedes() {
		return requiereParedes;
	}

	/**
	 * @param requiereParedes
	 *            the requiereParedes to set
	 */
	public void setRequiereParedes(Boolean requiereParedes) {
		this.requiereParedes = requiereParedes;
	}

	/**
	 * @return the requiereCubiertas
	 */
	public Boolean getRequiereCubiertas() {
		return requiereCubiertas;
	}

	/**
	 * @param requiereCubiertas
	 *            the requiereCubiertas to set
	 */
	public void setRequiereCubiertas(Boolean requiereCubiertas) {
		this.requiereCubiertas = requiereCubiertas;
	}

	/**
	 * @return the requierePisos
	 */
	public Boolean getRequierePisos() {
		return requierePisos;
	}

	/**
	 * @param requierePisos
	 *            the requierePisos to set
	 */
	public void setRequierePisos(Boolean requierePisos) {
		this.requierePisos = requierePisos;
	}

	/**
	 * @return the requiereOtros
	 */
	public String getRequiereOtros() {
		return requiereOtros;
	}

	/**
	 * @param requiereOtros
	 *            the requiereOtros to set
	 */
	public void setRequiereOtros(String requiereOtros) {
		this.requiereOtros = requiereOtros;
	}

	/**
	 * @return the riesgoBiologico
	 */
	public Boolean getRiesgoBiologico() {
		return riesgoBiologico;
	}

	/**
	 * @param riesgoBiologico
	 *            the riesgoBiologico to set
	 */
	public void setRiesgoBiologico(Boolean riesgoBiologico) {
		this.riesgoBiologico = riesgoBiologico;
	}

	/**
	 * @return the riesgoBiologicoCual
	 */
	public String getRiesgoBiologicoCual() {
		return riesgoBiologicoCual;
	}

	/**
	 * @param riesgoBiologicoCual
	 *            the riesgoBiologicoCual to set
	 */
	public void setRiesgoBiologicoCual(String riesgoBiologicoCual) {
		this.riesgoBiologicoCual = riesgoBiologicoCual;
	}

	/**
	 * @return the riesgoQuimico
	 */
	public Boolean getRiesgoQuimico() {
		return riesgoQuimico;
	}

	/**
	 * @param riesgoQuimico
	 *            the riesgoQuimico to set
	 */
	public void setRiesgoQuimico(Boolean riesgoQuimico) {
		this.riesgoQuimico = riesgoQuimico;
	}

	/**
	 * @return the riesgoQuimicoCual
	 */
	public String getRiesgoQuimicoCual() {
		return riesgoQuimicoCual;
	}

	/**
	 * @param riesgoQuimicoCual
	 *            the riesgoQuimicoCual to set
	 */
	public void setRiesgoQuimicoCual(String riesgoQuimicoCual) {
		this.riesgoQuimicoCual = riesgoQuimicoCual;
	}

	/**
	 * @return the riesgoElectrico
	 */
	public Boolean getRiesgoElectrico() {
		return riesgoElectrico;
	}

	/**
	 * @param riesgoElectrico
	 *            the riesgoElectrico to set
	 */
	public void setRiesgoElectrico(Boolean riesgoElectrico) {
		this.riesgoElectrico = riesgoElectrico;
	}

	/**
	 * @return the riesgoElectricoCual
	 */
	public String getRiesgoElectricoCual() {
		return riesgoElectricoCual;
	}

	/**
	 * @param riesgoElectricoCual
	 *            the riesgoElectricoCual to set
	 */
	public void setRiesgoElectricoCual(String riesgoElectricoCual) {
		this.riesgoElectricoCual = riesgoElectricoCual;
	}

	/**
	 * @return the emisionesAtmosfericas
	 */
	public Boolean getEmisionesAtmosfericas() {
		return emisionesAtmosfericas;
	}

	/**
	 * @param emisionesAtmosfericas
	 *            the emisionesAtmosfericas to set
	 */
	public void setEmisionesAtmosfericas(Boolean emisionesAtmosfericas) {
		this.emisionesAtmosfericas = emisionesAtmosfericas;
	}

	/**
	 * @return the emisionesAtmosfericasCual
	 */
	public String getEmisionesAtmosfericasCual() {
		return emisionesAtmosfericasCual;
	}

	/**
	 * @param emisionesAtmosfericasCual
	 *            the emisionesAtmosfericasCual to set
	 */
	public void setEmisionesAtmosfericasCual(String emisionesAtmosfericasCual) {
		this.emisionesAtmosfericasCual = emisionesAtmosfericasCual;
	}

	/**
	 * @return the exposicionAltaTension
	 */
	public Boolean getExposicionAltaTension() {
		return exposicionAltaTension;
	}

	/**
	 * @param exposicionAltaTension
	 *            the exposicionAltaTension to set
	 */
	public void setExposicionAltaTension(Boolean exposicionAltaTension) {
		this.exposicionAltaTension = exposicionAltaTension;
	}

	/**
	 * @return the exposicionAltaTensionCual
	 */
	public String getExposicionAltaTensionCual() {
		return exposicionAltaTensionCual;
	}

	/**
	 * @param exposicionAltaTensionCual
	 *            the exposicionAltaTensionCual to set
	 */
	public void setExposicionAltaTensionCual(String exposicionAltaTensionCual) {
		this.exposicionAltaTensionCual = exposicionAltaTensionCual;
	}

	/**
	 * @return the residuosSolidosBiologicos
	 */
	public Boolean getResiduosSolidosBiologicos() {
		return residuosSolidosBiologicos;
	}

	/**
	 * @param residuosSolidosBiologicos
	 *            the residuosSolidosBiologicos to set
	 */
	public void setResiduosSolidosBiologicos(Boolean residuosSolidosBiologicos) {
		this.residuosSolidosBiologicos = residuosSolidosBiologicos;
	}

	/**
	 * @return the residuosSolidosQuimicos
	 */
	public Boolean getResiduosSolidosQuimicos() {
		return residuosSolidosQuimicos;
	}

	/**
	 * @param residuosSolidosQuimicos
	 *            the residuosSolidosQuimicos to set
	 */
	public void setResiduosSolidosQuimicos(Boolean residuosSolidosQuimicos) {
		this.residuosSolidosQuimicos = residuosSolidosQuimicos;
	}

	/**
	 * @return the residuosSolidosRadioactivos
	 */
	public Boolean getResiduosSolidosRadioactivos() {
		return residuosSolidosRadioactivos;
	}

	/**
	 * @param residuosSolidosRadioactivos
	 *            the residuosSolidosRadioactivos to set
	 */
	public void setResiduosSolidosRadioactivos(
			Boolean residuosSolidosRadioactivos) {
		this.residuosSolidosRadioactivos = residuosSolidosRadioactivos;
	}

	/**
	 * @return the residuosSolidosCitotoxicos
	 */
	public Boolean getResiduosSolidosCitotoxicos() {
		return residuosSolidosCitotoxicos;
	}

	/**
	 * @param residuosSolidosCitotoxicos
	 *            the residuosSolidosCitotoxicos to set
	 */
	public void setResiduosSolidosCitotoxicos(Boolean residuosSolidosCitotoxicos) {
		this.residuosSolidosCitotoxicos = residuosSolidosCitotoxicos;
	}

	/**
	 * @return the residuosLiquidosBiologicos
	 */
	public Boolean getResiduosLiquidosBiologicos() {
		return residuosLiquidosBiologicos;
	}

	/**
	 * @param residuosLiquidosBiologicos
	 *            the residuosLiquidosBiologicos to set
	 */
	public void setResiduosLiquidosBiologicos(Boolean residuosLiquidosBiologicos) {
		this.residuosLiquidosBiologicos = residuosLiquidosBiologicos;
	}

	/**
	 * @return the residuosLiquidosQuimicos
	 */
	public Boolean getResiduosLiquidosQuimicos() {
		return residuosLiquidosQuimicos;
	}

	/**
	 * @param residuosLiquidosQuimicos
	 *            the residuosLiquidosQuimicos to set
	 */
	public void setResiduosLiquidosQuimicos(Boolean residuosLiquidosQuimicos) {
		this.residuosLiquidosQuimicos = residuosLiquidosQuimicos;
	}

	/**
	 * @return the residuosLiquidosRadioactivos
	 */
	public Boolean getResiduosLiquidosRadioactivos() {
		return residuosLiquidosRadioactivos;
	}

	/**
	 * @param residuosLiquidosRadioactivos
	 *            the residuosLiquidosRadioactivos to set
	 */
	public void setResiduosLiquidosRadioactivos(
			Boolean residuosLiquidosRadioactivos) {
		this.residuosLiquidosRadioactivos = residuosLiquidosRadioactivos;
	}

	/**
	 * @return the seguridadExtintores
	 */
	public Integer getSeguridadExtintores() {
		return seguridadExtintores;
	}

	/**
	 * @param seguridadExtintores
	 *            the seguridadExtintores to set
	 */
	public void setSeguridadExtintores(Integer seguridadExtintores) {
		this.seguridadExtintores = seguridadExtintores;
	}

	/**
	 * @return the seguridadBotiquin
	 */
	public Boolean getSeguridadBotiquin() {
		return seguridadBotiquin;
	}

	/**
	 * @param seguridadBotiquin
	 *            the seguridadBotiquin to set
	 */
	public void setSeguridadBotiquin(Boolean seguridadBotiquin) {
		this.seguridadBotiquin = seguridadBotiquin;
	}

	/**
	 * @return the seguridadDuchaEmergencia
	 */
	public Boolean getSeguridadDuchaEmergencia() {
		return seguridadDuchaEmergencia;
	}

	/**
	 * @param seguridadDuchaEmergencia
	 *            the seguridadDuchaEmergencia to set
	 */
	public void setSeguridadDuchaEmergencia(Boolean seguridadDuchaEmergencia) {
		this.seguridadDuchaEmergencia = seguridadDuchaEmergencia;
	}

	/**
	 * @return the seguridadSenalizacionGeneral
	 */
	public Boolean getSeguridadSenalizacionGeneral() {
		return seguridadSenalizacionGeneral;
	}

	/**
	 * @param seguridadSenalizacionGeneral
	 *            the seguridadSenalizacionGeneral to set
	 */
	public void setSeguridadSenalizacionGeneral(
			Boolean seguridadSenalizacionGeneral) {
		this.seguridadSenalizacionGeneral = seguridadSenalizacionGeneral;
	}

	/**
	 * @return the seguridadSenalizacionSeguridad
	 */
	public Boolean getSeguridadSenalizacionSeguridad() {
		return seguridadSenalizacionSeguridad;
	}

	/**
	 * @param seguridadSenalizacionSeguridad
	 *            the seguridadSenalizacionSeguridad to set
	 */
	public void setSeguridadSenalizacionSeguridad(
			Boolean seguridadSenalizacionSeguridad) {
		this.seguridadSenalizacionSeguridad = seguridadSenalizacionSeguridad;
	}

	/**
	 * @return the seguridadSenalizacionEmergencia
	 */
	public Boolean getSeguridadSenalizacionEmergencia() {
		return seguridadSenalizacionEmergencia;
	}

	/**
	 * @param seguridadSenalizacionEmergencia
	 *            the seguridadSenalizacionEmergencia to set
	 */
	public void setSeguridadSenalizacionEmergencia(
			Boolean seguridadSenalizacionEmergencia) {
		this.seguridadSenalizacionEmergencia = seguridadSenalizacionEmergencia;
	}

	/**
	 * @return the residuosSolidosOtros
	 */
	public String getResiduosSolidosOtros() {
		return residuosSolidosOtros;
	}

	/**
	 * @param residuosSolidosOtros
	 *            the residuosSolidosOtros to set
	 */
	public void setResiduosSolidosOtros(String residuosSolidosOtros) {
		this.residuosSolidosOtros = residuosSolidosOtros;
	}

	/**
	 * @return the residuosLiquidosOtros
	 */
	public String getResiduosLiquidosOtros() {
		return residuosLiquidosOtros;
	}

	/**
	 * @param residuosLiquidosOtros
	 *            the residuosLiquidosOtros to set
	 */
	public void setResiduosLiquidosOtros(String residuosLiquidosOtros) {
		this.residuosLiquidosOtros = residuosLiquidosOtros;
	}

	/**
	 * @return the radiacionExposicionIonizante
	 */
	public Boolean getRadiacionExposicionIonizante() {
		return radiacionExposicionIonizante;
	}

	/**
	 * @param radiacionExposicionIonizante
	 *            the radiacionExposicionIonizante to set
	 */
	public void setRadiacionExposicionIonizante(
			Boolean radiacionExposicionIonizante) {
		this.radiacionExposicionIonizante = radiacionExposicionIonizante;
	}

	/**
	 * @return the radiacionEquipoEmisor
	 */
	public Boolean getRadiacionEquipoEmisor() {
		return radiacionEquipoEmisor;
	}

	/**
	 * @param radiacionEquipoEmisor
	 *            the radiacionEquipoEmisor to set
	 */
	public void setRadiacionEquipoEmisor(Boolean radiacionEquipoEmisor) {
		this.radiacionEquipoEmisor = radiacionEquipoEmisor;
	}

	/**
	 * @return the radiacionEquipoEmisorCual
	 */
	public String getRadiacionEquipoEmisorCual() {
		return radiacionEquipoEmisorCual;
	}

	/**
	 * @param radiacionEquipoEmisorCual
	 *            the radiacionEquipoEmisorCual to set
	 */
	public void setRadiacionEquipoEmisorCual(String radiacionEquipoEmisorCual) {
		this.radiacionEquipoEmisorCual = radiacionEquipoEmisorCual;
	}

	/**
	 * @return the radiacionFuente
	 */
	public Boolean getRadiacionFuente() {
		return radiacionFuente;
	}

	/**
	 * @param radiacionFuente
	 *            the radiacionFuente to set
	 */
	public void setRadiacionFuente(Boolean radiacionFuente) {
		this.radiacionFuente = radiacionFuente;
	}

	/**
	 * @return the radiacionFuenteCual
	 */
	public String getRadiacionFuenteCual() {
		return radiacionFuenteCual;
	}

	/**
	 * @param radiacionFuenteCual
	 *            the radiacionFuenteCual to set
	 */
	public void setRadiacionFuenteCual(String radiacionFuenteCual) {
		this.radiacionFuenteCual = radiacionFuenteCual;
	}

	/**
	 * @return the radiacionMaterial
	 */
	public Boolean getRadiacionMaterial() {
		return radiacionMaterial;
	}

	/**
	 * @param radiacionMaterial
	 *            the radiacionMaterial to set
	 */
	public void setRadiacionMaterial(Boolean radiacionMaterial) {
		this.radiacionMaterial = radiacionMaterial;
	}

	/**
	 * @return the radiacionMaterialCual
	 */
	public String getRadiacionMaterialCual() {
		return radiacionMaterialCual;
	}

	/**
	 * @param radiacionMaterialCual
	 *            the radiacionMaterialCual to set
	 */
	public void setRadiacionMaterialCual(String radiacionMaterialCual) {
		this.radiacionMaterialCual = radiacionMaterialCual;
	}

	/**
	 * @return the radiacionExposicionNoIonizante
	 */
	public Boolean getRadiacionExposicionNoIonizante() {
		return radiacionExposicionNoIonizante;
	}

	/**
	 * @param radiacionExposicionNoIonizante
	 *            the radiacionExposicionNoIonizante to set
	 */
	public void setRadiacionExposicionNoIonizante(
			Boolean radiacionExposicionNoIonizante) {
		this.radiacionExposicionNoIonizante = radiacionExposicionNoIonizante;
	}

	/**
	 * @return the radiacionExposicionNoIonizanteCual
	 */
	public String getRadiacionExposicionNoIonizanteCual() {
		return radiacionExposicionNoIonizanteCual;
	}

	/**
	 * @param radiacionExposicionNoIonizanteCual
	 *            the radiacionExposicionNoIonizanteCual to set
	 */
	public void setRadiacionExposicionNoIonizanteCual(
			String radiacionExposicionNoIonizanteCual) {
		this.radiacionExposicionNoIonizanteCual = radiacionExposicionNoIonizanteCual;
	}

	/**
	 * @return the radiacionResiduosRadioactivos
	 */
	public Boolean getRadiacionResiduosRadioactivos() {
		return radiacionResiduosRadioactivos;
	}

	/**
	 * @param radiacionResiduosRadioactivos
	 *            the radiacionResiduosRadioactivos to set
	 */
	public void setRadiacionResiduosRadioactivos(
			Boolean radiacionResiduosRadioactivos) {
		this.radiacionResiduosRadioactivos = radiacionResiduosRadioactivos;
	}

	/**
	 * @return the radiacionResiduosRadioactivosCual
	 */
	public String getRadiacionResiduosRadioactivosCual() {
		return radiacionResiduosRadioactivosCual;
	}

	/**
	 * @param radiacionResiduosRadioactivosCual
	 *            the radiacionResiduosRadioactivosCual to set
	 */
	public void setRadiacionResiduosRadioactivosCual(
			String radiacionResiduosRadioactivosCual) {
		this.radiacionResiduosRadioactivosCual = radiacionResiduosRadioactivosCual;
	}

	/**
	 * @return the radiacionObservaciones
	 */
	public String getRadiacionObservaciones() {
		return radiacionObservaciones;
	}

	/**
	 * @param radiacionObservaciones
	 *            the radiacionObservaciones to set
	 */
	public void setRadiacionObservaciones(String radiacionObservaciones) {
		this.radiacionObservaciones = radiacionObservaciones;
	}

	/**
	 * @return the radiacionEmisionDiario
	 */
	public Boolean getRadiacionEmisionDiario() {
		return radiacionEmisionDiario;
	}

	/**
	 * @param radiacionEmisionDiario
	 *            the radiacionEmisionDiario to set
	 */
	public void setRadiacionEmisionDiario(Boolean radiacionEmisionDiario) {
		this.radiacionEmisionDiario = radiacionEmisionDiario;
	}

	/**
	 * @return the radiacionEmisionSemanal
	 */
	public Boolean getRadiacionEmisionSemanal() {
		return radiacionEmisionSemanal;
	}

	/**
	 * @param radiacionEmisionSemanal
	 *            the radiacionEmisionSemanal to set
	 */
	public void setRadiacionEmisionSemanal(Boolean radiacionEmisionSemanal) {
		this.radiacionEmisionSemanal = radiacionEmisionSemanal;
	}

	/**
	 * @return the radiacionEmisionMensual
	 */
	public Boolean getRadiacionEmisionMensual() {
		return radiacionEmisionMensual;
	}

	/**
	 * @param radiacionEmisionMensual
	 *            the radiacionEmisionMensual to set
	 */
	public void setRadiacionEmisionMensual(Boolean radiacionEmisionMensual) {
		this.radiacionEmisionMensual = radiacionEmisionMensual;
	}

	/**
	 * @return the radiacionDosimetrosPersonales
	 */
	public Integer getRadiacionDosimetrosPersonales() {
		return radiacionDosimetrosPersonales;
	}

	/**
	 * @param radiacionDosimetrosPersonales
	 *            the radiacionDosimetrosPersonales to set
	 */
	public void setRadiacionDosimetrosPersonales(
			Integer radiacionDosimetrosPersonales) {
		this.radiacionDosimetrosPersonales = radiacionDosimetrosPersonales;
	}

	/**
	 * @return the radiacionDosimetrosAmbientales
	 */
	public Integer getRadiacionDosimetrosAmbientales() {
		return radiacionDosimetrosAmbientales;
	}

	/**
	 * @param radiacionDosimetrosAmbientales
	 *            the radiacionDosimetrosAmbientales to set
	 */
	public void setRadiacionDosimetrosAmbientales(
			Integer radiacionDosimetrosAmbientales) {
		this.radiacionDosimetrosAmbientales = radiacionDosimetrosAmbientales;
	}

	/**
	 * @return the radiacionDosimetrosDeControl
	 */
	public Integer getRadiacionDosimetrosDeControl() {
		return radiacionDosimetrosDeControl;
	}

	/**
	 * @param radiacionDosimetrosDeControl
	 *            the radiacionDosimetrosDeControl to set
	 */
	public void setRadiacionDosimetrosDeControl(
			Integer radiacionDosimetrosDeControl) {
		this.radiacionDosimetrosDeControl = radiacionDosimetrosDeControl;
	}

	/**
	 * @return the radiacionProgramaMonitoreo
	 */
	public Boolean getRadiacionProgramaMonitoreo() {
		return radiacionProgramaMonitoreo;
	}

	/**
	 * @param radiacionProgramaMonitoreo
	 *            the radiacionProgramaMonitoreo to set
	 */
	public void setRadiacionProgramaMonitoreo(Boolean radiacionProgramaMonitoreo) {
		this.radiacionProgramaMonitoreo = radiacionProgramaMonitoreo;
	}

	/**
	 * @return the radiacionCarneProteccionRadiologica
	 */
	public Boolean getRadiacionCarneProteccionRadiologica() {
		return radiacionCarneProteccionRadiologica;
	}

	/**
	 * @param radiacionCarneProteccionRadiologica
	 *            the radiacionCarneProteccionRadiologica to set
	 */
	public void setRadiacionCarneProteccionRadiologica(
			Boolean radiacionCarneProteccionRadiologica) {
		this.radiacionCarneProteccionRadiologica = radiacionCarneProteccionRadiologica;
	}

	/**
	 * @return the radiacionLicenciaManejoFuncionamiento
	 */
	public Boolean getRadiacionLicenciaManejoFuncionamiento() {
		return radiacionLicenciaManejoFuncionamiento;
	}

	/**
	 * @param radiacionLicenciaManejoFuncionamiento
	 *            the radiacionLicenciaManejoFuncionamiento to set
	 */
	public void setRadiacionLicenciaManejoFuncionamiento(
			Boolean radiacionLicenciaManejoFuncionamiento) {
		this.radiacionLicenciaManejoFuncionamiento = radiacionLicenciaManejoFuncionamiento;
	}

	/**
	 * @return the radiacionCuartoDecaimiento
	 */
	public Boolean getRadiacionCuartoDecaimiento() {
		return radiacionCuartoDecaimiento;
	}

	/**
	 * @param radiacionCuartoDecaimiento
	 *            the radiacionCuartoDecaimiento to set
	 */
	public void setRadiacionCuartoDecaimiento(Boolean radiacionCuartoDecaimiento) {
		this.radiacionCuartoDecaimiento = radiacionCuartoDecaimiento;
	}

	/**
	 * @return the residuosSolidosElectricos
	 */
	public Boolean getResiduosSolidosElectricos() {
		return residuosSolidosElectricos;
	}

	/**
	 * @param residuosSolidosElectricos
	 *            the residuosSolidosElectricos to set
	 */
	public void setResiduosSolidosElectricos(Boolean residuosSolidosElectricos) {
		this.residuosSolidosElectricos = residuosSolidosElectricos;
	}

	/**
	 * @return the manualProteccionRadiologica
	 */
	public Boolean getManualProteccionRadiologica() {
		return manualProteccionRadiologica;
	}

	/**
	 * @param manualProteccionRadiologica
	 *            the manualProteccionRadiologica to set
	 */
	public void setManualProteccionRadiologica(
			Boolean manualProteccionRadiologica) {
		this.manualProteccionRadiologica = manualProteccionRadiologica;
	}

	/**
	 * @return the manualProteccionBiologica
	 */
	public Boolean getManualProteccionBiologica() {
		return manualProteccionBiologica;
	}

	/**
	 * @param manualProteccionBiologica
	 *            the manualProteccionBiologica to set
	 */
	public void setManualProteccionBiologica(Boolean manualProteccionBiologica) {
		this.manualProteccionBiologica = manualProteccionBiologica;
	}

	/**
	 * @return the manualProteccionQuimica
	 */
	public Boolean getManualProteccionQuimica() {
		return manualProteccionQuimica;
	}

	/**
	 * @param manualProteccionQuimica
	 *            the manualProteccionQuimica to set
	 */
	public void setManualProteccionQuimica(Boolean manualProteccionQuimica) {
		this.manualProteccionQuimica = manualProteccionQuimica;
	}

	/**
	 * @return the manualProteccionElectrica
	 */
	public Boolean getManualProteccionElectrica() {
		return manualProteccionElectrica;
	}

	/**
	 * @param manualProteccionElectrica
	 *            the manualProteccionElectrica to set
	 */
	public void setManualProteccionElectrica(Boolean manualProteccionElectrica) {
		this.manualProteccionElectrica = manualProteccionElectrica;
	}

	/**
	 * @return the almacenamientoAdecuadoReactivos
	 */
	public Boolean getAlmacenamientoAdecuadoReactivos() {
		return almacenamientoAdecuadoReactivos;
	}

	/**
	 * @param almacenamientoAdecuadoReactivos
	 *            the almacenamientoAdecuadoReactivos to set
	 */
	public void setAlmacenamientoAdecuadoReactivos(
			Boolean almacenamientoAdecuadoReactivos) {
		this.almacenamientoAdecuadoReactivos = almacenamientoAdecuadoReactivos;
	}

	/**
	 * @return the gestionAcreditacionInteresado
	 */
	public Boolean getGestionAcreditacionInteresado() {
		return gestionAcreditacionInteresado;
	}

	/**
	 * @param gestionAcreditacionInteresado
	 *            the gestionAcreditacionInteresado to set
	 */
	public void setGestionAcreditacionInteresado(
			Boolean gestionAcreditacionInteresado) {
		this.gestionAcreditacionInteresado = gestionAcreditacionInteresado;
	}

	/**
	 * @return the gestionAcreditacionNorma
	 */
	public String getGestionAcreditacionNorma() {
		return gestionAcreditacionNorma;
	}

	/**
	 * @param gestionAcreditacionNorma
	 *            the gestionAcreditacionNorma to set
	 */
	public void setGestionAcreditacionNorma(String gestionAcreditacionNorma) {
		this.gestionAcreditacionNorma = gestionAcreditacionNorma;
	}

	/**
	 * @return the gestionCertificacionInteresado
	 */
	public Boolean getGestionCertificacionInteresado() {
		return gestionCertificacionInteresado;
	}

	/**
	 * @param gestionCertificacionInteresado
	 *            the gestionCertificacionInteresado to set
	 */
	public void setGestionCertificacionInteresado(
			Boolean gestionCertificacionInteresado) {
		this.gestionCertificacionInteresado = gestionCertificacionInteresado;
	}

	/**
	 * @return the gestionCertificacionNorma
	 */
	public String getGestionCertificacionNorma() {
		return gestionCertificacionNorma;
	}

	/**
	 * @param gestionCertificacionNorma
	 *            the gestionCertificacionNorma to set
	 */
	public void setGestionCertificacionNorma(String gestionCertificacionNorma) {
		this.gestionCertificacionNorma = gestionCertificacionNorma;
	}

	/**
	 * @return the gestionHabilitacionRequiere
	 */
	public Boolean getGestionHabilitacionRequiere() {
		return gestionHabilitacionRequiere;
	}

	/**
	 * @param gestionHabilitacionRequiere
	 *            the gestionHabilitacionRequiere to set
	 */
	public void setGestionHabilitacionRequiere(
			Boolean gestionHabilitacionRequiere) {
		this.gestionHabilitacionRequiere = gestionHabilitacionRequiere;
	}

	/**
	 * @return the gestionHabilitacionNorma
	 */
	public String getGestionHabilitacionNorma() {
		return gestionHabilitacionNorma;
	}

	/**
	 * @param gestionHabilitacionNorma
	 *            the gestionHabilitacionNorma to set
	 */
	public void setGestionHabilitacionNorma(String gestionHabilitacionNorma) {
		this.gestionHabilitacionNorma = gestionHabilitacionNorma;
	}

	/**
	 * @return the gestionRegistroIcaRequiere
	 */
	public Boolean getGestionRegistroIcaRequiere() {
		return gestionRegistroIcaRequiere;
	}

	/**
	 * @param gestionRegistroIcaRequiere
	 *            the gestionRegistroIcaRequiere to set
	 */
	public void setGestionRegistroIcaRequiere(Boolean gestionRegistroIcaRequiere) {
		this.gestionRegistroIcaRequiere = gestionRegistroIcaRequiere;
	}

	/**
	 * @return the gestionRegistroIcaNorma
	 */
	public String getGestionRegistroIcaNorma() {
		return gestionRegistroIcaNorma;
	}

	/**
	 * @param gestionRegistroIcaNorma
	 *            the gestionRegistroIcaNorma to set
	 */
	public void setGestionRegistroIcaNorma(String gestionRegistroIcaNorma) {
		this.gestionRegistroIcaNorma = gestionRegistroIcaNorma;
	}

	/**
	 * @return the gestionLicenciasRequiere
	 */
	public Boolean getGestionLicenciasRequiere() {
		return gestionLicenciasRequiere;
	}

	/**
	 * @param gestionLicenciasRequiere
	 *            the gestionLicenciasRequiere to set
	 */
	public void setGestionLicenciasRequiere(Boolean gestionLicenciasRequiere) {
		this.gestionLicenciasRequiere = gestionLicenciasRequiere;
	}

	/**
	 * @return the gestionLicenciasCual
	 */
	public String getGestionLicenciasCual() {
		return gestionLicenciasCual;
	}

	/**
	 * @param gestionLicenciasCual
	 *            the gestionLicenciasCual to set
	 */
	public void setGestionLicenciasCual(String gestionLicenciasCual) {
		this.gestionLicenciasCual = gestionLicenciasCual;
	}

	/**
	 * @return the personalCalificado
	 */
	public Boolean getPersonalCalificado() {
		return personalCalificado;
	}

	/**
	 * @param personalCalificado
	 *            the personalCalificado to set
	 */
	public void setPersonalCalificado(Boolean personalCalificado) {
		this.personalCalificado = personalCalificado;
	}

	/**
	 * @return the personalSuficiente
	 */
	public Boolean getPersonalSuficiente() {
		return personalSuficiente;
	}

	/**
	 * @param personalSuficiente
	 *            the personalSuficiente to set
	 */
	public void setPersonalSuficiente(Boolean personalSuficiente) {
		this.personalSuficiente = personalSuficiente;
	}

	/**
	 * @return the portafolioServicios
	 */
	public Tipos getPortafolioServicios() {
		return portafolioServicios;
	}

	/**
	 * @param portafolioServicios
	 *            the portafolioServicios to set
	 */
	public void setPortafolioServicios(Tipos portafolioServicios) {
		this.portafolioServicios = portafolioServicios;
	}

	/**
	 * @return the elementosProteccionPersonal
	 */
	public Boolean getElementosProteccionPersonal() {
		return elementosProteccionPersonal;
	}

	/**
	 * @param elementosProteccionPersonal
	 *            the elementosProteccionPersonal to set
	 */
	public void setElementosProteccionPersonal(
			Boolean elementosProteccionPersonal) {
		this.elementosProteccionPersonal = elementosProteccionPersonal;
	}

	/**
	 * @return the elementosProteccionPersonalCual
	 */
	public String getElementosProteccionPersonalCual() {
		return elementosProteccionPersonalCual;
	}

	/**
	 * @param elementosProteccionPersonalCual
	 *            the elementosProteccionPersonalCual to set
	 */
	public void setElementosProteccionPersonalCual(
			String elementosProteccionPersonalCual) {
		this.elementosProteccionPersonalCual = elementosProteccionPersonalCual;
	}

	/**
	 * @return the equiposObservaciones
	 */
	public String getEquiposObservaciones() {
		return equiposObservaciones;
	}

	/**
	 * @param equiposObservaciones
	 *            the equiposObservaciones to set
	 */
	public void setEquiposObservaciones(String equiposObservaciones) {
		this.equiposObservaciones = equiposObservaciones;
	}

	/**
	 * @return the horarioAtencion
	 */
	public String getHorarioAtencion() {
		return horarioAtencion;
	}

	/**
	 * @param horarioAtencion
	 *            the horarioAtencion to set
	 */
	public void setHorarioAtencion(String horarioAtencion) {
		this.horarioAtencion = horarioAtencion;
	}

	/**
	 * @return the ensayosUsuariosActuales
	 */
	public String getEnsayosUsuariosActuales() {
		return ensayosUsuariosActuales;
	}

	/**
	 * @param ensayosUsuariosActuales
	 *            the ensayosUsuariosActuales to set
	 */
	public void setEnsayosUsuariosActuales(String ensayosUsuariosActuales) {
		this.ensayosUsuariosActuales = ensayosUsuariosActuales;
	}

	/**
	 * @return the ensayosUsuariosPotenciales
	 */
	public String getEnsayosUsuariosPotenciales() {
		return ensayosUsuariosPotenciales;
	}

	/**
	 * @param ensayosUsuariosPotenciales
	 *            the ensayosUsuariosPotenciales to set
	 */
	public void setEnsayosUsuariosPotenciales(String ensayosUsuariosPotenciales) {
		this.ensayosUsuariosPotenciales = ensayosUsuariosPotenciales;
	}

	/**
	 * @return the areaGeneralInvestigacion
	 */
	public String getAreaGeneralInvestigacion() {
		return areaGeneralInvestigacion;
	}

	/**
	 * @param areaGeneralInvestigacion
	 *            the areaGeneralInvestigacion to set
	 */
	public void setAreaGeneralInvestigacion(String areaGeneralInvestigacion) {
		this.areaGeneralInvestigacion = areaGeneralInvestigacion;
	}

	/**
	 * @return the areasAplicacionInvestigacion
	 */
	public String getAreasAplicacionInvestigacion() {
		return areasAplicacionInvestigacion;
	}

	/**
	 * @param areasAplicacionInvestigacion
	 *            the areasAplicacionInvestigacion to set
	 */
	public void setAreasAplicacionInvestigacion(
			String areasAplicacionInvestigacion) {
		this.areasAplicacionInvestigacion = areasAplicacionInvestigacion;
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
	 * @return the activo
	 */
	public Boolean getActivo() {
		return activo;
	}

	/**
	 * @param activo
	 *            the activo to set
	 */
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	/**
	 * @return the estadoIluminacion
	 */
	public Long getEstadoIluminacion() {
		return estadoIluminacion;
	}

	/**
	 * @param estadoIluminacion
	 *            the estadoIluminacion to set
	 */
	public void setEstadoIluminacion(Long estadoIluminacion) {
		this.estadoIluminacion = estadoIluminacion;
	}

	/**
	 * @return the estadoRuido
	 */
	public Long getEstadoRuido() {
		return estadoRuido;
	}

	/**
	 * @param estadoRuido
	 *            the estadoRuido to set
	 */
	public void setEstadoRuido(Long estadoRuido) {
		this.estadoRuido = estadoRuido;
	}

	/**
	 * @return the estadoInstalacionesFisicas
	 */
	public Long getEstadoInstalacionesFisicas() {
		return estadoInstalacionesFisicas;
	}

	/**
	 * @param estadoInstalacionesFisicas
	 *            the estadoInstalacionesFisicas to set
	 */
	public void setEstadoInstalacionesFisicas(Long estadoInstalacionesFisicas) {
		this.estadoInstalacionesFisicas = estadoInstalacionesFisicas;
	}

	/**
	 * @return the estadoClimatizacion
	 */
	public Long getEstadoClimatizacion() {
		return estadoClimatizacion;
	}

	/**
	 * @param estadoClimatizacion
	 *            the estadoClimatizacion to set
	 */
	public void setEstadoClimatizacion(Long estadoClimatizacion) {
		this.estadoClimatizacion = estadoClimatizacion;
	}

	/**
	 * @return the estadoVentilacion
	 */
	public Long getEstadoVentilacion() {
		return estadoVentilacion;
	}

	/**
	 * @param estadoVentilacion
	 *            the estadoVentilacion to set
	 */
	public void setEstadoVentilacion(Long estadoVentilacion) {
		this.estadoVentilacion = estadoVentilacion;
	}

	/**
	 * @return the gestionAcreditacion
	 */
	public Long getGestionAcreditacion() {
		return gestionAcreditacion;
	}

	/**
	 * @param gestionAcreditacion
	 *            the gestionAcreditacion to set
	 */
	public void setGestionAcreditacion(Long gestionAcreditacion) {
		this.gestionAcreditacion = gestionAcreditacion;
	}

	/**
	 * @return the gestionCertificacion
	 */
	public Long getGestionCertificacion() {
		return gestionCertificacion;
	}

	/**
	 * @param gestionCertificacion
	 *            the gestionCertificacion to set
	 */
	public void setGestionCertificacion(Long gestionCertificacion) {
		this.gestionCertificacion = gestionCertificacion;
	}

	/**
	 * @return the gestionHabilitacion
	 */
	public Long getGestionHabilitacion() {
		return gestionHabilitacion;
	}

	/**
	 * @param gestionHabilitacion
	 *            the gestionHabilitacion to set
	 */
	public void setGestionHabilitacion(Long gestionHabilitacion) {
		this.gestionHabilitacion = gestionHabilitacion;
	}

	/**
	 * @return the gestionRegistroIca
	 */
	public Long getGestionRegistroIca() {
		return gestionRegistroIca;
	}

	/**
	 * @param gestionRegistroIca
	 *            the gestionRegistroIca to set
	 */
	public void setGestionRegistroIca(Long gestionRegistroIca) {
		this.gestionRegistroIca = gestionRegistroIca;
	}

	/**
	 * @return the gestionLicencias
	 */
	public Long getGestionLicencias() {
		return gestionLicencias;
	}

	/**
	 * @param gestionLicencias
	 *            the gestionLicencias to set
	 */
	public void setGestionLicencias(Long gestionLicencias) {
		this.gestionLicencias = gestionLicencias;
	}

	public void adicionarClasificacionConocimientoLab(
			ClasificacionConocimiento conocimiento) {
		this.clasificacionConocimientoLab.add(conocimiento);
	}

	public void borrarClasificacionConocimientoLab(
			ClasificacionConocimiento conocimiento) {
		this.clasificacionConocimientoLab.remove(conocimiento);
	}

	public List<ClasificacionConocimiento> getListaClasificacionConocimientoLab() {
		List<ClasificacionConocimiento> listaCC = new ArrayList<ClasificacionConocimiento>();
		listaCC.addAll(clasificacionConocimientoLab);
		return listaCC;
	}

	/*
	 * public List getListaClasificacionConocimientoLab() { List listaCC=new
	 * ArrayList(); listaCC.addAll(clasificacionConocimientoLab); return
	 * listaCC; }
	 */

	/**
	 * @return the clasificacionConocimientoLab
	 */
	public Set<ClasificacionConocimiento> getClasificacionConocimientoLab() {
		return clasificacionConocimientoLab;
	}

	/**
	 * @param clasificacionConocimientoLab
	 *            the clasificacionConocimientoLab to set
	 */
	public void setClasificacionConocimientoLab(
			Set<ClasificacionConocimiento> clasificacionConocimientoLab) {
		this.clasificacionConocimientoLab = clasificacionConocimientoLab;
	}

	/**
	 * @return the registroDocumentoPersona
	 */
	public String getRegistroDocumentoPersona() {
		return registroDocumentoPersona;
	}

	/**
	 * @param registroDocumentoPersona
	 *            the registroDocumentoPersona to set
	 */
	public void setRegistroDocumentoPersona(String registroDocumentoPersona) {
		this.registroDocumentoPersona = registroDocumentoPersona;
	}

	/**
	 * @return the registroTipoDocumentoPersona
	 */
	public String getRegistroTipoDocumentoPersona() {
		return registroTipoDocumentoPersona;
	}

	/**
	 * @param registroTipoDocumentoPersona
	 *            the registroTipoDocumentoPersona to set
	 */
	public void setRegistroTipoDocumentoPersona(
			String registroTipoDocumentoPersona) {
		this.registroTipoDocumentoPersona = registroTipoDocumentoPersona;
	}

	/**
	 * @return el nombre corto de laboratorio: id - nombre
	 */
	public String getNombreCorto() {
		int maxCar = 45;
		int longitudNombre = nombre.length();

		String nombreCorto = "Lab. " + id + " - ";
		if (longitudNombre < maxCar) {
			nombreCorto += nombre;
		} else {
			nombreCorto += nombre.substring(0, maxCar - 2) + "...";
		}
		return nombreCorto;
	}

	/**
	 * @return the personaActualEsCoordinador
	 */
	public Boolean getPersonaActualEsCoordinador() {
		return personaActualEsCoordinador;
	}

	/**
	 * @param personaActualEsCoordinador
	 *            the personaActualEsCoordinador to set
	 */
	public void setPersonaActualEsCoordinador(Boolean personaActualEsCoordinador) {
		this.personaActualEsCoordinador = personaActualEsCoordinador;
	}

	/**
	 * @return the requiereEquipos
	 */
	public Boolean getRequiereEquipos() {
		return requiereEquipos;
	}

	/**
	 * @param requiereEquipos
	 *            the requiereEquipos to set
	 */
	public void setRequiereEquipos(Boolean requiereEquipos) {
		this.requiereEquipos = requiereEquipos;
	}

	/**
	 * @return the tipoSolicitud
	 */
	public Tipos getTipoSolicitud() {
		return tipoSolicitud;
	}

	/**
	 * @param tipoSolicitud
	 *            the tipoSolicitud to set
	 */
	public void setTipoSolicitud(Tipos tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}

	/**
	 * @return the justificacionSolicitud
	 */
	public String getJustificacionSolicitud() {
		return justificacionSolicitud;
	}

	/**
	 * @param justificacionSolicitud
	 *            the justificacionSolicitud to set
	 */
	public void setJustificacionSolicitud(String justificacionSolicitud) {
		this.justificacionSolicitud = justificacionSolicitud;
	}

	/**
	 * @return the personalSolicitud
	 */
	public String getPersonalSolicitud() {
		return personalSolicitud;
	}

	/**
	 * @param personalSolicitud
	 *            the personalSolicitud to set
	 */
	public void setPersonalSolicitud(String personalSolicitud) {
		this.personalSolicitud = personalSolicitud;
	}

	/**
	 * @return the infraestructuraSolicitud
	 */
	public String getInfraestructuraSolicitud() {
		return infraestructuraSolicitud;
	}

	/**
	 * @param infraestructuraSolicitud
	 *            the infraestructuraSolicitud to set
	 */
	public void setInfraestructuraSolicitud(String infraestructuraSolicitud) {
		this.infraestructuraSolicitud = infraestructuraSolicitud;
	}

	public String getAcreditacion() {
		if (gestionAcreditacion.equals(Tipos.TIPO_GESTION_LABORATORIO_SI)) {
			String acreditacion = "Acreditación " + gestionAcreditacionNorma;
			return acreditacion;
		} else {
			// return "No acreditado.";
			return null;
		}
	}

	/**
	 * @return the porcentajeCompletitud
	 */
	public Float getPorcentajeCompletitud() {
		if (porcentajeCompletitud == null) {
			porcentajeCompletitud = new Float(0);
		}
		return porcentajeCompletitud;
	}

	/**
	 * @param porcentajeCompletitud
	 *            the porcentajeCompletitud to set
	 */
	public void setPorcentajeCompletitud(Float porcentajeCompletitud) {
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

	/**
	 * @return the sostenibilidad
	 */
	public String getSostenibilidad() {
		return sostenibilidad;
	}

	/**
	 * @param sostenibilidad
	 *            the sostenibilidad to set
	 */
	public void setSostenibilidad(String sostenibilidad) {
		this.sostenibilidad = sostenibilidad;
	}
	
	public Integer getTotalEquipos() {
		return totalEquipos;
	}

	public void setTotalEquipos(Integer totalEquipos) {
		this.totalEquipos = totalEquipos;
	}

	public Integer getTotalEquiposMantto() {
		return totalEquiposMantto;
	}

	public void setTotalEquiposMantto(Integer totalEquiposMantto) {
		this.totalEquiposMantto = totalEquiposMantto;
	}

	public Integer getTotalEquiposCalib() {
		return totalEquiposCalib;
	}

	public void setTotalEquiposCalib(Integer totalEquiposCalib) {
		this.totalEquiposCalib = totalEquiposCalib;
	}

	public Boolean getUsaSustanciasControladas() {
		return usaSustanciasControladas;
	}

	public void setUsaSustanciasControladas(Boolean usaSustanciasControladas) {
		this.usaSustanciasControladas = usaSustanciasControladas;
	}

//	public Set<Tipos> getListaSubredes() {
//		return listaSubredes;
//	}
//
//	public void setListaSubredes(Set<Tipos> listaSubredes) {
//		this.listaSubredes = listaSubredes;
//	}
//
//	public Set<Tipos> getListaCapacitacion() {
//		return listaCapacitacion;
//	}
//
//	public void setListaCapacitacion(Set<Tipos> listaCapacitacion) {
//		this.listaCapacitacion = listaCapacitacion;
//	}
//
//	public Set<Tipos> getListaActividades() {
//		return listaActividades;
//	}
//
//	public void setListaActividades(Set<Tipos> listaActividades) {
//		this.listaActividades = listaActividades;
//	}
//
//	public Set<Tipos> getListaMuestreo() {
//		return listaMuestreo;
//	}
//
//	public void setListaMuestreo(Set<Tipos> listaMuestreo) {
//		this.listaMuestreo = listaMuestreo;
//	}
//
//	public Set<Tipos> getListaSistemasGestion() {
//		return listaSistemasGestion;
//	}
//
//	public void setListaSistemasGestion(Set<Tipos> listaSistemasGestion) {
//		this.listaSistemasGestion = listaSistemasGestion;
//	}

	public Set<Tipos> getSubredes() {
		return subredes;
	}

	public void setSubredes(Set<Tipos> subredes) {
		this.subredes = subredes;
	}

	public Set<Tipos> getCapacitaciones() {
		return capacitaciones;
	}

	public void setCapacitaciones(Set<Tipos> capacitaciones) {
		this.capacitaciones = capacitaciones;
	}

	public Set<Tipos> getActividades() {
		return actividades;
	}

	public void setActividades(Set<Tipos> actividades) {
		this.actividades = actividades;
	}

	public Set<Tipos> getMuestreos() {
		return muestreos;
	}

	public void setMuestreos(Set<Tipos> muestreos) {
		this.muestreos = muestreos;
	}

	public Set<Tipos> getSistemasGestion() {
		return sistemasGestion;
	}

	public void setSistemasGestion(Set<Tipos> sistemasGestion) {
		this.sistemasGestion = sistemasGestion;
	}

	public String getLinkBrochure() {
		return linkBrochure;
	}

	public void setLinkBrochure(String linkBrochure) {
		this.linkBrochure = linkBrochure;
	}

	public String getTipoActoAdminTarifas() {
		return tipoActoAdminTarifas;
	}

	public void setTipoActoAdminTarifas(String tipoActoAdminTarifas) {
		this.tipoActoAdminTarifas = tipoActoAdminTarifas;
	}

	public String getNumeroActoAdminTarifas() {
		return numeroActoAdminTarifas;
	}

	public void setNumeroActoAdminTarifas(String numeroActoAdminTarifas) {
		this.numeroActoAdminTarifas = numeroActoAdminTarifas;
	}

	public String getEmitidaActoAdminTarifas() {
		return emitidaActoAdminTarifas;
	}

	public void setEmitidaActoAdminTarifas(String emitidaActoAdminTarifas) {
		this.emitidaActoAdminTarifas = emitidaActoAdminTarifas;
	}

	public Date getFechaActoAdminTarifas() {
		return fechaActoAdminTarifas;
	}

	public void setFechaActoAdminTarifas(Date fechaActoAdminTarifas) {
		this.fechaActoAdminTarifas = fechaActoAdminTarifas;
	}

	public String getCondicionesServicio() {
		return condicionesServicio;
	}

	public void setCondicionesServicio(String condicionesServicio) {
		this.condicionesServicio = condicionesServicio;
	}

	public Tipos getTipo() {
		return tipo;
	}

	public void setTipo(Tipos tipo) {
		this.tipo = tipo;
	}

	public Tipos getAreaPrincipal() {
		return areaPrincipal;
	}

	public void setAreaPrincipal(Tipos areaPrincipal) {
		this.areaPrincipal = areaPrincipal;
	}

	public Tipos getSubAreaPrincipal() {
		return subAreaPrincipal;
	}

	public void setSubAreaPrincipal(Tipos subAreaPrincipal) {
		this.subAreaPrincipal = subAreaPrincipal;
	}

	public Tipos getObjetivoSocioEconomico() {
		return objetivoSocioEconomico;
	}

	public void setObjetivoSocioEconomico(Tipos objetivoSocioEconomico) {
		this.objetivoSocioEconomico = objetivoSocioEconomico;
	}

	public Long getGestionRequiere() {
		return gestionRequiere;
	}

	public void setGestionRequiere(Long gestionRequiere) {
		this.gestionRequiere = gestionRequiere;
	}

	public String getGestionRequiereNorma() {
		return gestionRequiereNorma;
	}

	public void setGestionRequiereNorma(String gestionRequiereNorma) {
		this.gestionRequiereNorma = gestionRequiereNorma;
	}
	
	public Boolean getAplicaMetrología() {
		return aplicaMetrología;
	}

	public void setAplicaMetrología(Boolean aplicaMetrología) {
		this.aplicaMetrología = aplicaMetrología;
	}

	public Long getGestionInteresado() {
		return gestionInteresado;
	}

	public void setGestionInteresado(Long gestionInteresado) {
		this.gestionInteresado = gestionInteresado;
	}

	public String getGestionInteresadoNorma() {
		return gestionInteresadoNorma;
	}

	public void setGestionInteresadoNorma(String gestionInteresadoNorma) {
		this.gestionInteresadoNorma = gestionInteresadoNorma;
	}

	public static String getFALSO() {
		return FALSO;
	}

	public static void setFALSO(String fALSO) {
		FALSO = fALSO;
	}

	public static String getVERDADERO() {
		return VERDADERO;
	}

	public static void setVERDADERO(String vERDADERO) {
		VERDADERO = vERDADERO;
	}

	public static String getDEDICACION_DESCONOCIDA() {
		return DEDICACION_DESCONOCIDA;
	}

	public static void setDEDICACION_DESCONOCIDA(String dEDICACION_DESCONOCIDA) {
		DEDICACION_DESCONOCIDA = dEDICACION_DESCONOCIDA;
	}

	public Boolean getValidaInformacionLabXCoordinador() {
		return validaInformacionLabXCoordinador;
	}

	public void setValidaInformacionLabXCoordinador(Boolean validaInformacionLabXCoordinador) {
		this.validaInformacionLabXCoordinador = validaInformacionLabXCoordinador;
	}

	public Date getFechaValidaInformacionLabXCoordinador() {
		return fechaValidaInformacionLabXCoordinador;
	}

	public void setFechaValidaInformacionLabXCoordinador(Date fechaValidaInformacionLabXCoordinador) {
		this.fechaValidaInformacionLabXCoordinador = fechaValidaInformacionLabXCoordinador;
	}

	public Tipos getTipoReglamento() {
		return tipoReglamento;
	}

	public void setTipoReglamento(Tipos tipoReglamento) {
		this.tipoReglamento = tipoReglamento;
	}

	public String getLinkReglamento() {
		return linkReglamento;
	}

	public void setLinkReglamento(String linkReglamento) {
		this.linkReglamento = linkReglamento;
	}

	public Boolean getSer_agua() {
		return ser_agua;
	}

	public void setSer_agua(Boolean ser_agua) {
		this.ser_agua = ser_agua;
	}

	public Boolean getSer_gas() {
		return ser_gas;
	}

	public void setSer_gas(Boolean ser_gas) {
		this.ser_gas = ser_gas;
	}

	public Boolean getSer_vacio() {
		return ser_vacio;
	}

	public void setSer_vacio(Boolean ser_vacio) {
		this.ser_vacio = ser_vacio;
	}

	public Boolean getSer_aire_comprimido() {
		return ser_aire_comprimido;
	}

	public void setSer_aire_comprimido(Boolean ser_aire_comprimido) {
		this.ser_aire_comprimido = ser_aire_comprimido;
	}

	public Boolean getSer_control_iluminacion() {
		return ser_control_iluminacion;
	}

	public void setSer_control_iluminacion(Boolean ser_control_iluminacion) {
		this.ser_control_iluminacion = ser_control_iluminacion;
	}

	public Boolean getSer_aire_acondicionado() {
		return ser_aire_acondicionado;
	}

	public void setSer_aire_acondicionado(Boolean ser_aire_acondicionado) {
		this.ser_aire_acondicionado = ser_aire_acondicionado;
	}

	public Boolean getSer_insonorizacion() {
		return ser_insonorizacion;
	}

	public void setSer_insonorizacion(Boolean ser_insonorizacion) {
		this.ser_insonorizacion = ser_insonorizacion;
	}

	public Boolean getSer_circuito_cerrado_de_tv() {
		return ser_circuito_cerrado_de_tv;
	}

	public void setSer_circuito_cerrado_de_tv(Boolean ser_circuito_cerrado_de_tv) {
		this.ser_circuito_cerrado_de_tv = ser_circuito_cerrado_de_tv;
	}

	public Boolean getSer_puntos_de_red() {
		return ser_puntos_de_red;
	}

	public void setSer_puntos_de_red(Boolean ser_puntos_de_red) {
		this.ser_puntos_de_red = ser_puntos_de_red;
	}

	public Boolean getSer_otro() {
		return ser_otro;
	}

	public void setSer_otro(Boolean ser_otro) {
		this.ser_otro = ser_otro;
	}

	public Boolean getSer_110v() {
		return ser_110v;
	}

	public void setSer_110v(Boolean ser_110v) {
		this.ser_110v = ser_110v;
	}

	public Boolean getSer_220v() {
		return ser_220v;
	}

	public void setSer_220v(Boolean ser_220v) {
		this.ser_220v = ser_220v;
	}

	public String getSer_tension_requerida_en_voltios() {
		return ser_tension_requerida_en_voltios;
	}

	public void setSer_tension_requerida_en_voltios(String ser_tension_requerida_en_voltios) {
		this.ser_tension_requerida_en_voltios = ser_tension_requerida_en_voltios;
	}

	public String getSer_agua_detalle() {
		return ser_agua_detalle;
	}

	public void setSer_agua_detalle(String ser_agua_detalle) {
		this.ser_agua_detalle = ser_agua_detalle;
	}

	public String getSer_gas_detalle() {
		return ser_gas_detalle;
	}

	public void setSer_gas_detalle(String ser_gas_detalle) {
		this.ser_gas_detalle = ser_gas_detalle;
	}

	public String getSer_vacio_detalle() {
		return ser_vacio_detalle;
	}

	public void setSer_vacio_detalle(String ser_vacio_detalle) {
		this.ser_vacio_detalle = ser_vacio_detalle;
	}

	public String getSer_aire_comprimido_detalle() {
		return ser_aire_comprimido_detalle;
	}

	public void setSer_aire_comprimido_detalle(String ser_aire_comprimido_detalle) {
		this.ser_aire_comprimido_detalle = ser_aire_comprimido_detalle;
	}

	public String getSer_control_iluminacion_detalle() {
		return ser_control_iluminacion_detalle;
	}

	public void setSer_control_iluminacion_detalle(String ser_control_iluminacion_detalle) {
		this.ser_control_iluminacion_detalle = ser_control_iluminacion_detalle;
	}

	public String getSer_aire_acondicionado_detalle() {
		return ser_aire_acondicionado_detalle;
	}

	public void setSer_aire_acondicionado_detalle(String ser_aire_acondicionado_detalle) {
		this.ser_aire_acondicionado_detalle = ser_aire_acondicionado_detalle;
	}

	public String getSer_insonorizacion_detalle() {
		return ser_insonorizacion_detalle;
	}

	public void setSer_insonorizacion_detalle(String ser_insonorizacion_detalle) {
		this.ser_insonorizacion_detalle = ser_insonorizacion_detalle;
	}

	public String getSer_circuito_cerrado_de_tv_detalle() {
		return ser_circuito_cerrado_de_tv_detalle;
	}

	public void setSer_circuito_cerrado_de_tv_detalle(String ser_circuito_cerrado_de_tv_detalle) {
		this.ser_circuito_cerrado_de_tv_detalle = ser_circuito_cerrado_de_tv_detalle;
	}

	public String getSer_puntos_de_red_detalle() {
		return ser_puntos_de_red_detalle;
	}

	public void setSer_puntos_de_red_detalle(String ser_puntos_de_red_detalle) {
		this.ser_puntos_de_red_detalle = ser_puntos_de_red_detalle;
	}

	public String getSer_otro_detalle() {
		return ser_otro_detalle;
	}

	public void setSer_otro_detalle(String ser_otro_detalle) {
		this.ser_otro_detalle = ser_otro_detalle;
	}

	public Boolean getSga_biodegradables() {
		return sga_biodegradables;
	}

	public void setSga_biodegradables(Boolean sga_biodegradables) {
		this.sga_biodegradables = sga_biodegradables;
	}

	public Boolean getSga_reciclables() {
		return sga_reciclables;
	}

	public void setSga_reciclables(Boolean sga_reciclables) {
		this.sga_reciclables = sga_reciclables;
	}

	public Boolean getSga_inertes() {
		return sga_inertes;
	}

	public void setSga_inertes(Boolean sga_inertes) {
		this.sga_inertes = sga_inertes;
	}

	public Boolean getSga_ordinarios() {
		return sga_ordinarios;
	}

	public void setSga_ordinarios(Boolean sga_ordinarios) {
		this.sga_ordinarios = sga_ordinarios;
	}

	public Boolean getSga_grasas_y_aceites() {
		return sga_grasas_y_aceites;
	}

	public void setSga_grasas_y_aceites(Boolean sga_grasas_y_aceites) {
		this.sga_grasas_y_aceites = sga_grasas_y_aceites;
	}

	public Boolean getSga_lixiviados() {
		return sga_lixiviados;
	}

	public void setSga_lixiviados(Boolean sga_lixiviados) {
		this.sga_lixiviados = sga_lixiviados;
	}

	public Boolean getSga_residuos_infecciosos() {
		return sga_residuos_infecciosos;
	}

	public void setSga_residuos_infecciosos(Boolean sga_residuos_infecciosos) {
		this.sga_residuos_infecciosos = sga_residuos_infecciosos;
	}

	public Boolean getSga_residuos_quimicos() {
		return sga_residuos_quimicos;
	}

	public void setSga_residuos_quimicos(Boolean sga_residuos_quimicos) {
		this.sga_residuos_quimicos = sga_residuos_quimicos;
	}

	public Boolean getSga_residuos_radioactivos() {
		return sga_residuos_radioactivos;
	}

	public void setSga_residuos_radioactivos(Boolean sga_residuos_radioactivos) {
		this.sga_residuos_radioactivos = sga_residuos_radioactivos;
	}

	public Boolean getSga_derivados_de_combustion() {
		return sga_derivados_de_combustion;
	}

	public void setSga_derivados_de_combustion(Boolean sga_derivados_de_combustion) {
		this.sga_derivados_de_combustion = sga_derivados_de_combustion;
	}

	public Boolean getSga_no_derivados_de_combustion() {
		return sga_no_derivados_de_combustion;
	}

	public void setSga_no_derivados_de_combustion(Boolean sga_no_derivados_de_combustion) {
		this.sga_no_derivados_de_combustion = sga_no_derivados_de_combustion;
	}

	public Boolean getSga_ruido() {
		return sga_ruido;
	}

	public void setSga_ruido(Boolean sga_ruido) {
		this.sga_ruido = sga_ruido;
	}

	public Boolean getSga_olores_ofensivos() {
		return sga_olores_ofensivos;
	}

	public void setSga_olores_ofensivos(Boolean sga_olores_ofensivos) {
		this.sga_olores_ofensivos = sga_olores_ofensivos;
	}

	public Boolean getSga_aguas_domesticas() {
		return sga_aguas_domesticas;
	}

	public void setSga_aguas_domesticas(Boolean sga_aguas_domesticas) {
		this.sga_aguas_domesticas = sga_aguas_domesticas;
	}

	public Boolean getSga_aguas_de_interes_ambiental() {
		return sga_aguas_de_interes_ambiental;
	}

	public void setSga_aguas_de_interes_ambiental(Boolean sga_aguas_de_interes_ambiental) {
		this.sga_aguas_de_interes_ambiental = sga_aguas_de_interes_ambiental;
	}

	public Boolean getSga_aguas_de_interes_sanitario() {
		return sga_aguas_de_interes_sanitario;
	}

	public void setSga_aguas_de_interes_sanitario(Boolean sga_aguas_de_interes_sanitario) {
		this.sga_aguas_de_interes_sanitario = sga_aguas_de_interes_sanitario;
	}

	public Boolean getSga_otro() {
		return sga_otro;
	}

	public void setSga_otro(Boolean sga_otro) {
		this.sga_otro = sga_otro;
	}

	public String getSri_almacenamientoadecuadoreactivosdetalle() {
		return sri_almacenamientoadecuadoreactivosdetalle;
	}

	public void setSri_almacenamientoadecuadoreactivosdetalle(String sri_almacenamientoadecuadoreactivosdetalle) {
		this.sri_almacenamientoadecuadoreactivosdetalle = sri_almacenamientoadecuadoreactivosdetalle;
	}

	public String getSri_radiacionexposicionionizantedetalle() {
		return sri_radiacionexposicionionizantedetalle;
	}

	public void setSri_radiacionexposicionionizantedetalle(String sri_radiacionexposicionionizantedetalle) {
		this.sri_radiacionexposicionionizantedetalle = sri_radiacionexposicionionizantedetalle;
	}

	public Boolean getSri_animales() {
		return sri_animales;
	}

	public void setSri_animales(Boolean sri_animales) {
		this.sri_animales = sri_animales;
	}

	public Boolean getSri_humanos() {
		return sri_humanos;
	}

	public void setSri_humanos(Boolean sri_humanos) {
		this.sri_humanos = sri_humanos;
	}

	public Boolean getSri_medicamentos_controlados() {
		return sri_medicamentos_controlados;
	}

	public void setSri_medicamentos_controlados(Boolean sri_medicamentos_controlados) {
		this.sri_medicamentos_controlados = sri_medicamentos_controlados;
	}

	public Boolean getSri_gases_comprimidos() {
		return sri_gases_comprimidos;
	}

	public void setSri_gases_comprimidos(Boolean sri_gases_comprimidos) {
		this.sri_gases_comprimidos = sri_gases_comprimidos;
	}

	public Boolean getSri_material_biologico() {
		return sri_material_biologico;
	}

	public void setSri_material_biologico(Boolean sri_material_biologico) {
		this.sri_material_biologico = sri_material_biologico;
	}

	public Boolean getSri_equipos_y_o_material_generador_de_radiacion_ionizante() {
		return sri_equipos_y_o_material_generador_de_radiacion_ionizante;
	}

	public void setSri_equipos_y_o_material_generador_de_radiacion_ionizante(
			Boolean sri_equipos_y_o_material_generador_de_radiacion_ionizante) {
		this.sri_equipos_y_o_material_generador_de_radiacion_ionizante = sri_equipos_y_o_material_generador_de_radiacion_ionizante;
	}

	public Boolean getSri_equipos_generadores_de_ruido_vibracion() {
		return sri_equipos_generadores_de_ruido_vibracion;
	}

	public void setSri_equipos_generadores_de_ruido_vibracion(Boolean sri_equipos_generadores_de_ruido_vibracion) {
		this.sri_equipos_generadores_de_ruido_vibracion = sri_equipos_generadores_de_ruido_vibracion;
	}

	public Boolean getSri_otro() {
		return sri_otro;
	}

	public void setSri_otro(Boolean sri_otro) {
		this.sri_otro = sri_otro;
	}

	public String getSri_animales_detalle() {
		return sri_animales_detalle;
	}

	public void setSri_animales_detalle(String sri_animales_detalle) {
		this.sri_animales_detalle = sri_animales_detalle;
	}

	public String getSri_humanos_detalle() {
		return sri_humanos_detalle;
	}

	public void setSri_humanos_detalle(String sri_humanos_detalle) {
		this.sri_humanos_detalle = sri_humanos_detalle;
	}

	public String getSri_medicamentos_controlados_detalle() {
		return sri_medicamentos_controlados_detalle;
	}

	public void setSri_medicamentos_controlados_detalle(String sri_medicamentos_controlados_detalle) {
		this.sri_medicamentos_controlados_detalle = sri_medicamentos_controlados_detalle;
	}

	public String getSri_gases_comprimidos_detalle() {
		return sri_gases_comprimidos_detalle;
	}

	public void setSri_gases_comprimidos_detalle(String sri_gases_comprimidos_detalle) {
		this.sri_gases_comprimidos_detalle = sri_gases_comprimidos_detalle;
	}

	public String getSri_material_biologico_detalle() {
		return sri_material_biologico_detalle;
	}

	public void setSri_material_biologico_detalle(String sri_material_biologico_detalle) {
		this.sri_material_biologico_detalle = sri_material_biologico_detalle;
	}

	public String getSri_equipos_y_o_material_generador_de_radiacion_ionizante_detalle() {
		return sri_equipos_y_o_material_generador_de_radiacion_ionizante_detalle;
	}

	public void setSri_equipos_y_o_material_generador_de_radiacion_ionizante_detalle(
			String sri_equipos_y_o_material_generador_de_radiacion_ionizante_detalle) {
		this.sri_equipos_y_o_material_generador_de_radiacion_ionizante_detalle = sri_equipos_y_o_material_generador_de_radiacion_ionizante_detalle;
	}

	public String getSri_equipos_generadores_de_ruido_vibracion_detalle() {
		return sri_equipos_generadores_de_ruido_vibracion_detalle;
	}

	public void setSri_equipos_generadores_de_ruido_vibracion_detalle(
			String sri_equipos_generadores_de_ruido_vibracion_detalle) {
		this.sri_equipos_generadores_de_ruido_vibracion_detalle = sri_equipos_generadores_de_ruido_vibracion_detalle;
	}

	public String getSri_otro_detalle() {
		return sri_otro_detalle;
	}

	public void setSri_otro_detalle(String sri_otro_detalle) {
		this.sri_otro_detalle = sri_otro_detalle;
	}

	public Set<LaboratorioAval> getAvales() {
		return avales;
	}

	public void setAvales(Set<LaboratorioAval> avales) {
		this.avales = avales;
	}

	public Boolean getTipoLabEnsayo() {
		return tipoLabEnsayo;
	}

	public void setTipoLabEnsayo(Boolean tipoLabEnsayo) {
		this.tipoLabEnsayo = tipoLabEnsayo;
	}

	public Boolean getTipoLabMuestreo() {
		return tipoLabMuestreo;
	}

	public void setTipoLabMuestreo(Boolean tipoLabMuestreo) {
		this.tipoLabMuestreo = tipoLabMuestreo;
	}

	public Boolean getTipoLabCalibracion() {
		return tipoLabCalibracion;
	}

	public void setTipoLabCalibracion(Boolean tipoLabCalibracion) {
		this.tipoLabCalibracion = tipoLabCalibracion;
	}

	public Boolean getTipoLabOtro() {
		return tipoLabOtro;
	}

	public void setTipoLabOtro(Boolean tipoLabOtro) {
		this.tipoLabOtro = tipoLabOtro;
	}

	public String getTipoLabOtroCual() {
		return tipoLabOtroCual;
	}

	public void setTipoLabOtroCual(String tipoLabOtroCual) {
		this.tipoLabOtroCual = tipoLabOtroCual;
	}

	public Tipos getDeptoCiudad() {
		return deptoCiudad;
	}

	public void setDeptoCiudad(Tipos deptoCiudad) {
		this.deptoCiudad = deptoCiudad;
	}

	public String getTelefonoMask() {
		return telefonoMask;
	}

	public void setTelefonoMask(String telefonoMask) {
		this.telefonoMask = telefonoMask;
	}

	public String getTipoTelefono() {
		return tipoTelefono;
	}

	public void setTipoTelefono(String tipoTelefono) {
		this.tipoTelefono = tipoTelefono;
	}

	public String getTipoTelefono2() {
		return tipoTelefono2;
	}

	public void setTipoTelefono2(String tipoTelefono2) {
		this.tipoTelefono2 = tipoTelefono2;
	}

	public String getTelefono2() {
		return telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}

	public String getLinkCondiciones() {
		return linkCondiciones;
	}

	public void setLinkCondiciones(String linkCondiciones) {
		this.linkCondiciones = linkCondiciones;
	}

	public void setListaObservaciones(Set<LaboratorioObservaciones> listaObservaciones) {
		this.listaObservaciones = listaObservaciones;
	}
	
	public Set<LaboratorioObservaciones> getListaObservaciones() {
		return listaObservaciones;
	}

	public Float getCritEquiposImpactoOperaLab() {
		return critEquiposImpactoOperaLab;
	}

	public void setCritEquiposImpactoOperaLab(Float critEquiposImpactoOperaLab) {
		this.critEquiposImpactoOperaLab = critEquiposImpactoOperaLab;
	}

	public Float getCritEquiposImpactoSegUsuarios() {
		return critEquiposImpactoSegUsuarios;
	}

	public void setCritEquiposImpactoSegUsuarios(Float critEquiposImpactoSegUsuarios) {
		this.critEquiposImpactoSegUsuarios = critEquiposImpactoSegUsuarios;
	}

	public Float getCritEquiposImpactoDaniosInfra() {
		return critEquiposImpactoDaniosInfra;
	}

	public void setCritEquiposImpactoDaniosInfra(Float critEquiposImpactoDaniosInfra) {
		this.critEquiposImpactoDaniosInfra = critEquiposImpactoDaniosInfra;
	}

	public Float getCritEquiposImpactoDaniosAmbient() {
		return critEquiposImpactoDaniosAmbient;
	}

	public void setCritEquiposImpactoDaniosAmbient(Float critEquiposImpactoDaniosAmbient) {
		this.critEquiposImpactoDaniosAmbient = critEquiposImpactoDaniosAmbient;
	}

	public Float getCritEquiposImpactoImagenUN() {
		return critEquiposImpactoImagenUN;
	}

	public void setCritEquiposImpactoImagenUN(Float critEquiposImpactoImagenUN) {
		this.critEquiposImpactoImagenUN = critEquiposImpactoImagenUN;
	}

	public Float getCritEquiposImpactoQuejas() {
		return critEquiposImpactoQuejas;
	}

	public void setCritEquiposImpactoQuejas(Float critEquiposImpactoQuejas) {
		this.critEquiposImpactoQuejas = critEquiposImpactoQuejas;
	}

	public Float getCritEquiposImpactoEconomicos() {
		return critEquiposImpactoEconomicos;
	}

	public void setCritEquiposImpactoEconomicos(Float critEquiposImpactoEconomicos) {
		this.critEquiposImpactoEconomicos = critEquiposImpactoEconomicos;
	}

	public Float getCritEquiposProbRepFalla() {
		return critEquiposProbRepFalla;
	}

	public void setCritEquiposProbRepFalla(Float critEquiposProbRepFalla) {
		this.critEquiposProbRepFalla = critEquiposProbRepFalla;
	}

	public Float getCritEquiposProbTiempoTrabajo() {
		return critEquiposProbTiempoTrabajo;
	}

	public void setCritEquiposProbTiempoTrabajo(Float critEquiposProbTiempoTrabajo) {
		this.critEquiposProbTiempoTrabajo = critEquiposProbTiempoTrabajo;
	}

	public Float getCritEquiposProbCondAmbient() {
		return critEquiposProbCondAmbient;
	}

	public void setCritEquiposProbCondAmbient(Float critEquiposProbCondAmbient) {
		this.critEquiposProbCondAmbient = critEquiposProbCondAmbient;
	}

	public Float getCritEquiposProbMetrologia() {
		return critEquiposProbMetrologia;
	}

	public void setCritEquiposProbMetrologia(Float critEquiposProbMetrologia) {
		this.critEquiposProbMetrologia = critEquiposProbMetrologia;
	}

	public Date getFechaInactivo() {
		return fechaInactivo;
	}

	public void setFechaInactivo(Date fechaInactivo) {
		this.fechaInactivo = fechaInactivo;
	}

	public String getJustificacionInactivo() {
		return justificacionInactivo;
	}

	public void setJustificacionInactivo(String justificacionInactivo) {
		this.justificacionInactivo = justificacionInactivo;
	}
	
}
