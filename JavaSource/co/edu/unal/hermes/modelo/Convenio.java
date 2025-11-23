package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The Class Convenio.
 */
public class Convenio implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2539677239646091632L;

    /** The id. */
    private Long id;

    /** The tipo. */
    private TipoConvenio tipo;

    /** The nombre. */
    private String nombre;

    /** The objeto. */
    private String objeto;

    /** The entidad. */
    private FuenteFinanciacion entidad;

    /** The fecha inicio. */
    private Date fechaInicio;

    /** The fecha finalizacion. */
    private Date fechaFinalizacion;

    /** The estado. */
    private String estado;

    /** The archivo. */
    private String archivo;

    /** The id proyecto. */
    // Legalización Proyectos
    private Long idProyecto;

    /** The dependencia firma. */
    private String dependenciaFirma;

    /** The designado firma. */
    private String designadoFirma;

    /** The cargo designado firma. */
    private String cargoDesignadoFirma;

    /** The numero contrato. */
    private String numeroContrato;

    /** The vigencia contrato. */
    private String vigenciaContrato;

    /** The cod actividad. */
    private Long codActividad;

    /** The especialidad RUP. */
    private String especialidadRUP;

    /** The propiedad intelectual. */
    private String propiedadIntelectual;

    /** The confidencialidad. */
    private String confidencialidad;

    /** The clausula penal. */
    private String clausulaPenal;

    /** The clausula multas. */
    private String clausulaMultas;

    /** The propiedad bienes. */
    private String propiedadBienes;

    /** The valor total contrato. */
    private Long valorTotalContrato;

    /** The valor efectivo contrato. */
    private Long valorEfectivoContrato;

    /** The valor especie contrato. */
    private Long valorEspecieContrato;

    /** The monto ejecutar UN. */
    private Long montoEjecutarUN = 0L;

    /** The valor ejecucion UN. */
    private Long valorEjecucionUN = 0L;

    /** The cod entidad externa. */
    private Long codEntidadExterna = 0l;

    /** The supervisor externo. */
    private String supervisorExterno;

    /** The supervisor ext cargo. */
    private String supervisorExtCargo;

    /** The supervisor entidad. */
    private String supervisorEntidad;

    /** The supervisor int cargo. */
    private String supervisorIntCargo;

    /** The asistente proyecto. */
    private String asistenteProyecto;

    /** The requiere autorizac CR. */
    private boolean requiereAutorizacCR;

    /** The requiere autorizac CC. */
    private boolean requiereAutorizacCC;

    /** The requiere autorizac CI. */
    private boolean requiereAutorizacCI;

    /** The supervisor interno. */
    private String supervisorInterno;

    /** The asistente extension. */
    private String asistenteExtension;

    /** The asistente cargo. */
    private String asistenteCargo;

    /** The modalidad contratacion. */
    private String modalidadContratacion;

    /** The vigencia legalizacion. */
    private Long vigenciaLegalizacion;

    /** The obligaciones. */
    private Set<ConvenioObligacion> obligaciones = new HashSet<ConvenioObligacion>();
    
    private Set<ConvenioOtrosi> otrosi = new HashSet<ConvenioOtrosi>();

    /**
     * Instantiates a new convenio.
     */
    public Convenio() {
        this.entidad = new FuenteFinanciacion();
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the tipo.
     *
     * @return the tipo
     */
    public TipoConvenio getTipo() {
        return tipo;
    }

    /**
     * Sets the tipo.
     *
     * @param tipo
     *            the new tipo
     */
    public void setTipo(TipoConvenio tipo) {
        this.tipo = tipo;
    }

    /**
     * Gets the nombre.
     *
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the nombre.
     *
     * @param nombre
     *            the new nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Gets the objeto.
     *
     * @return the objeto
     */
    public String getObjeto() {
        return objeto;
    }

    /**
     * Sets the objeto.
     *
     * @param objeto
     *            the new objeto
     */
    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    /**
     * Gets the entidad.
     *
     * @return the entidad
     */
    public FuenteFinanciacion getEntidad() {
        return entidad;
    }

    /**
     * Sets the entidad.
     *
     * @param entidad
     *            the new entidad
     */
    public void setEntidad(FuenteFinanciacion entidad) {
        this.entidad = entidad;
    }

    /**
     * Gets the fecha inicio.
     *
     * @return the fecha inicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Sets the fecha inicio.
     *
     * @param fechaInicio
     *            the new fecha inicio
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Gets the fecha finalizacion.
     *
     * @return the fecha finalizacion
     */
    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    /**
     * Sets the fecha finalizacion.
     *
     * @param fechaFinalizacion
     *            the new fecha finalizacion
     */
    public void setFechaFinalizacion(Date fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    /**
     * Gets the estado.
     *
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Sets the estado.
     *
     * @param estado
     *            the new estado
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Gets the archivo.
     *
     * @return the archivo
     */
    public String getArchivo() {
        return archivo;
    }

    /**
     * Sets the archivo.
     *
     * @param archivo
     *            the new archivo
     */
    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    /**
     * Gets the dependencia firma.
     *
     * @return the dependencia firma
     */
    public String getDependenciaFirma() {
        return dependenciaFirma;
    }

    /**
     * Sets the dependencia firma.
     *
     * @param dependenciaFirma
     *            the new dependencia firma
     */
    public void setDependenciaFirma(String dependenciaFirma) {
        this.dependenciaFirma = dependenciaFirma;
    }

    /**
     * Gets the designado firma.
     *
     * @return the designado firma
     */
    public String getDesignadoFirma() {
        return designadoFirma;
    }

    /**
     * Sets the designado firma.
     *
     * @param designadoFirma
     *            the new designado firma
     */
    public void setDesignadoFirma(String designadoFirma) {
        this.designadoFirma = designadoFirma;
    }

    /**
     * Gets the numero contrato.
     *
     * @return the numero contrato
     */
    public String getNumeroContrato() {
        return numeroContrato;
    }

    /**
     * Sets the numero contrato.
     *
     * @param numeroContrato
     *            the new numero contrato
     */
    public void setNumeroContrato(String numeroContrato) {
        this.numeroContrato = numeroContrato;
    }

    /**
     * Gets the vigencia contrato.
     *
     * @return the vigencia contrato
     */
    public String getVigenciaContrato() {
        return vigenciaContrato;
    }

    /**
     * Sets the vigencia contrato.
     *
     * @param vigenciaContrato
     *            the new vigencia contrato
     */
    public void setVigenciaContrato(String vigenciaContrato) {
        this.vigenciaContrato = vigenciaContrato;
    }

    /**
     * Gets the id proyecto.
     *
     * @return the id proyecto
     */
    public Long getIdProyecto() {
        return idProyecto;
    }

    /**
     * Sets the id proyecto.
     *
     * @param idProyecto
     *            the new id proyecto
     */
    public void setIdProyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }

    /**
     * Gets the cod actividad.
     *
     * @return the cod actividad
     */
    public Long getCodActividad() {
        return codActividad;
    }

    /**
     * Sets the cod actividad.
     *
     * @param codActividad
     *            the new cod actividad
     */
    public void setCodActividad(Long codActividad) {
        this.codActividad = codActividad;
    }

    /**
     * Gets the especialidad RUP.
     *
     * @return the especialidad RUP
     */
    public String getEspecialidadRUP() {
        return especialidadRUP;
    }

    /**
     * Sets the especialidad RUP.
     *
     * @param especialidadRUP
     *            the new especialidad RUP
     */
    public void setEspecialidadRUP(String especialidadRUP) {
        this.especialidadRUP = especialidadRUP;
    }

    /**
     * Gets the propiedad intelectual.
     *
     * @return the propiedad intelectual
     */
    public String getPropiedadIntelectual() {
        return propiedadIntelectual;
    }

    /**
     * Sets the propiedad intelectual.
     *
     * @param propiedadIntelectual
     *            the new propiedad intelectual
     */
    public void setPropiedadIntelectual(String propiedadIntelectual) {
        this.propiedadIntelectual = propiedadIntelectual;
    }

    /**
     * Gets the confidencialidad.
     *
     * @return the confidencialidad
     */
    public String getConfidencialidad() {
        return confidencialidad;
    }

    /**
     * Sets the confidencialidad.
     *
     * @param confidencialidad
     *            the new confidencialidad
     */
    public void setConfidencialidad(String confidencialidad) {
        this.confidencialidad = confidencialidad;
    }

    /**
     * Gets the clausula penal.
     *
     * @return the clausula penal
     */
    public String getClausulaPenal() {
        return clausulaPenal;
    }

    /**
     * Sets the clausula penal.
     *
     * @param clausulaPenal
     *            the new clausula penal
     */
    public void setClausulaPenal(String clausulaPenal) {
        this.clausulaPenal = clausulaPenal;
    }

    /**
     * Gets the clausula multas.
     *
     * @return the clausula multas
     */
    public String getClausulaMultas() {
        return clausulaMultas;
    }

    /**
     * Sets the clausula multas.
     *
     * @param clausulaMultas
     *            the new clausula multas
     */
    public void setClausulaMultas(String clausulaMultas) {
        this.clausulaMultas = clausulaMultas;
    }

    /**
     * Gets the propiedad bienes.
     *
     * @return the propiedad bienes
     */
    public String getPropiedadBienes() {
        return propiedadBienes;
    }

    /**
     * Sets the propiedad bienes.
     *
     * @param propiedadBienes
     *            the new propiedad bienes
     */
    public void setPropiedadBienes(String propiedadBienes) {
        this.propiedadBienes = propiedadBienes;
    }

    /**
     * Gets the valor total contrato.
     *
     * @return the valor total contrato
     */
    public Long getValorTotalContrato() {
        if (valorTotalContrato == null) {
            return 0L;
        } else {
            return valorTotalContrato;
        }

    }

    /**
     * Sets the valor total contrato.
     *
     * @param valorTotalContrato
     *            the new valor total contrato
     */
    public void setValorTotalContrato(Long valorTotalContrato) {
        this.valorTotalContrato = valorTotalContrato;
    }

    /**
     * Gets the valor efectivo contrato.
     *
     * @return the valor efectivo contrato
     */
    public Long getValorEfectivoContrato() {
        if (valorEfectivoContrato == null) {
            return 0L;
        } else {
            return valorEfectivoContrato;
        }
    }

    /**
     * Sets the valor efectivo contrato.
     *
     * @param valorEfectivoContrato
     *            the new valor efectivo contrato
     */
    public void setValorEfectivoContrato(Long valorEfectivoContrato) {
        this.valorEfectivoContrato = valorEfectivoContrato;
    }

    /**
     * Gets the valor especie contrato.
     *
     * @return the valor especie contrato
     */
    public Long getValorEspecieContrato() {
        if (valorEspecieContrato == null) {
            return 0L;
        } else {
            return valorEspecieContrato;
        }
    }

    /**
     * Sets the valor especie contrato.
     *
     * @param valorEspecieContrato
     *            the new valor especie contrato
     */
    public void setValorEspecieContrato(Long valorEspecieContrato) {
        this.valorEspecieContrato = valorEspecieContrato;
    }

    /**
     * Gets the monto ejecutar UN.
     *
     * @return the monto ejecutar UN
     */
    public Long getMontoEjecutarUN() {
        if (montoEjecutarUN == null) {
            return 0L;
        } else {
            return montoEjecutarUN;
        }
    }

    /**
     * Sets the monto ejecutar UN.
     *
     * @param montoEjecutarUN
     *            the new monto ejecutar UN
     */
    public void setMontoEjecutarUN(Long montoEjecutarUN) {
        this.montoEjecutarUN = montoEjecutarUN;
    }

    /**
     * Gets the valor ejecucion UN.
     *
     * @return the valor ejecucion UN
     */
    public Long getValorEjecucionUN() {
        if (valorEjecucionUN == null) {
            return 0L;
        } else {
            return valorEjecucionUN;
        }
    }

    /**
     * Sets the valor ejecucion UN.
     *
     * @param valorEjecucionUN
     *            the new valor ejecucion UN
     */
    public void setValorEjecucionUN(Long valorEjecucionUN) {
        this.valorEjecucionUN = valorEjecucionUN;
    }

    /**
     * Gets the supervisor externo.
     *
     * @return the supervisor externo
     */
    public String getSupervisorExterno() {
        return supervisorExterno;
    }

    /**
     * Sets the supervisor externo.
     *
     * @param supervisorExterno
     *            the new supervisor externo
     */
    public void setSupervisorExterno(String supervisorExterno) {
        this.supervisorExterno = supervisorExterno;
    }

    /**
     * Gets the supervisor ext cargo.
     *
     * @return the supervisor ext cargo
     */
    public String getSupervisorExtCargo() {
        return supervisorExtCargo;
    }

    /**
     * Sets the supervisor ext cargo.
     *
     * @param supervisorExtCargo
     *            the new supervisor ext cargo
     */
    public void setSupervisorExtCargo(String supervisorExtCargo) {
        this.supervisorExtCargo = supervisorExtCargo;
    }

    /**
     * Gets the supervisor entidad.
     *
     * @return the supervisor entidad
     */
    public String getSupervisorEntidad() {
        return supervisorEntidad;
    }

    /**
     * Sets the supervisor entidad.
     *
     * @param supervisorEntidad
     *            the new supervisor entidad
     */
    public void setSupervisorEntidad(String supervisorEntidad) {
        this.supervisorEntidad = supervisorEntidad;
    }

    /**
     * Gets the supervisor int cargo.
     *
     * @return the supervisor int cargo
     */
    public String getSupervisorIntCargo() {
        return supervisorIntCargo;
    }

    /**
     * Sets the supervisor int cargo.
     *
     * @param supervisorIntCargo
     *            the new supervisor int cargo
     */
    public void setSupervisorIntCargo(String supervisorIntCargo) {
        this.supervisorIntCargo = supervisorIntCargo;
    }

    /**
     * Gets the asistente proyecto.
     *
     * @return the asistente proyecto
     */
    public String getAsistenteProyecto() {
        return asistenteProyecto;
    }

    /**
     * Sets the asistente proyecto.
     *
     * @param asistenteProyecto
     *            the new asistente proyecto
     */
    public void setAsistenteProyecto(String asistenteProyecto) {
        this.asistenteProyecto = asistenteProyecto;
    }

    /**
     * Gets the cod entidad externa.
     *
     * @return the cod entidad externa
     */
    public Long getCodEntidadExterna() {
        return codEntidadExterna;
    }

    /**
     * Sets the cod entidad externa.
     *
     * @param codEntidadExterna
     *            the new cod entidad externa
     */
    public void setCodEntidadExterna(Long codEntidadExterna) {
        this.codEntidadExterna = codEntidadExterna;
    }

    /**
     * Checks if is requiere autorizac CR.
     *
     * @return true, if is requiere autorizac CR
     */
    public boolean isRequiereAutorizacCR() {
        return requiereAutorizacCR;
    }

    /**
     * Sets the requiere autorizac CR.
     *
     * @param requiereAutorizacCR
     *            the new requiere autorizac CR
     */
    public void setRequiereAutorizacCR(boolean requiereAutorizacCR) {
        this.requiereAutorizacCR = requiereAutorizacCR;
    }

    /**
     * Gets the supervisor interno.
     *
     * @return the supervisor interno
     */
    public String getSupervisorInterno() {
        return supervisorInterno;
    }

    /**
     * Sets the supervisor interno.
     *
     * @param supervisorInterno
     *            the new supervisor interno
     */
    public void setSupervisorInterno(String supervisorInterno) {
        this.supervisorInterno = supervisorInterno;
    }

    /**
     * Gets the asistente extension.
     *
     * @return the asistente extension
     */
    public String getAsistenteExtension() {
        return asistenteExtension;
    }

    /**
     * Sets the asistente extension.
     *
     * @param asistenteExtension
     *            the new asistente extension
     */
    public void setAsistenteExtension(String asistenteExtension) {
        this.asistenteExtension = asistenteExtension;
    }

    /**
     * Gets the cargo designado firma.
     *
     * @return the cargo designado firma
     */
    public String getCargoDesignadoFirma() {
        return cargoDesignadoFirma;
    }

    /**
     * Sets the cargo designado firma.
     *
     * @param cargoDesignadoFirma
     *            the new cargo designado firma
     */
    public void setCargoDesignadoFirma(String cargoDesignadoFirma) {
        this.cargoDesignadoFirma = cargoDesignadoFirma;
    }

    /**
     * Gets the asistente cargo.
     *
     * @return the asistente cargo
     */
    public String getAsistenteCargo() {
        return asistenteCargo;
    }

    /**
     * Sets the asistente cargo.
     *
     * @param asistenteCargo
     *            the new asistente cargo
     */
    public void setAsistenteCargo(String asistenteCargo) {
        this.asistenteCargo = asistenteCargo;
    }

    /**
     * Checks if is requiere autorizac CC.
     *
     * @return true, if is requiere autorizac CC
     */
    public boolean isRequiereAutorizacCC() {
        return requiereAutorizacCC;
    }

    /**
     * Sets the requiere autorizac CC.
     *
     * @param requiereAutorizacCC
     *            the new requiere autorizac CC
     */
    public void setRequiereAutorizacCC(boolean requiereAutorizacCC) {
        this.requiereAutorizacCC = requiereAutorizacCC;
    }

    /**
     * Checks if is requiere autorizac CI.
     *
     * @return true, if is requiere autorizac CI
     */
    public boolean isRequiereAutorizacCI() {
        return requiereAutorizacCI;
    }

    /**
     * Sets the requiere autorizac CI.
     *
     * @param requiereAutorizacCI
     *            the new requiere autorizac CI
     */
    public void setRequiereAutorizacCI(boolean requiereAutorizacCI) {
        this.requiereAutorizacCI = requiereAutorizacCI;
    }

    /**
     * Gets the modalidad contratacion.
     *
     * @return the modalidad contratacion
     */
    public String getModalidadContratacion() {
        return modalidadContratacion;
    }

    /**
     * Sets the modalidad contratacion.
     *
     * @param modalidadContratacion
     *            the new modalidad contratacion
     */
    public void setModalidadContratacion(String modalidadContratacion) {
        this.modalidadContratacion = modalidadContratacion;
    }

    /**
     * Gets the vigencia legalizacion.
     *
     * @return the vigencia legalizacion
     */
    public Long getVigenciaLegalizacion() {
        return vigenciaLegalizacion;
    }

    /**
     * Sets the vigencia legalizacion.
     *
     * @param vigenciaLegalizacion
     *            the new vigencia legalizacion
     */
    public void setVigenciaLegalizacion(Long vigenciaLegalizacion) {
        this.vigenciaLegalizacion = vigenciaLegalizacion;
    }

    /**
     * Gets the obligaciones.
     *
     * @return the obligaciones
     */
    public Set<ConvenioObligacion> getObligaciones() {
        return obligaciones;
    }
    
    /**
     * Gets the lista obligaciones.
     *
     * @return the lista obligaciones
     */
    public List<ConvenioObligacion> getListaObligaciones(){
        List<ConvenioObligacion> listaObligaciones = new ArrayList<ConvenioObligacion>(obligaciones);
        return listaObligaciones;        
    }

    /**
     * Sets the obligaciones.
     *
     * @param obligaciones
     *            the obligaciones to set
     */
    public void setObligaciones(Set<ConvenioObligacion> obligaciones) {
        this.obligaciones = obligaciones;
    }

    /**
     * Adicionar obligacion.
     *
     * @param convenioObligacion the convenio obligacion
     */
    public void adicionarObligacion(ConvenioObligacion convenioObligacion) {
        convenioObligacion.setConvenio(this);

        if (obligaciones == null) {
            obligaciones = new HashSet<ConvenioObligacion>();
        }

        obligaciones.add(convenioObligacion);
    }

	public Set<ConvenioOtrosi> getOtrosi() {
		return otrosi;
	}

	public void setOtrosi(Set<ConvenioOtrosi> otrosi) {
		this.otrosi = otrosi;
	}
    
    public void adicionarOtrosi(ConvenioOtrosi convenioOtrosi) {
    	convenioOtrosi.setConvenio(this);

        if (otrosi == null) {
        	otrosi = new HashSet<ConvenioOtrosi>();
        }

        otrosi.add(convenioOtrosi);
    }
    

}
