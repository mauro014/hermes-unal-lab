/*
 * @autor: Mauricio Amaya Ríos
 * Created on 01-ene-2005
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;

/**
 * The Class CorteConvocatoria.
 */
public class CorteConvocatoria implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2588329384736011597L;
    
    /** The Constant ESTADO_BORRADO. */
    public static final String ESTADO_BORRADO = "B";
    
    /** The Constant ESTADO_POR_DEFECTO. */
    public static final String ESTADO_POR_DEFECTO = "A";
    
    /** The Constant ESTADO_POR_DEFECTO. */
    public static final String ID_CORTE_CONVOCATORIA_SESSION = "corteConvocatoraId";
    
    /** The id. */
    private Long id;
    
    /** The convocatoria padre. */
    private ConvocatoriaPadre convocatoriaPadre;
    
    /** The numero. */
    private Long numero;
    
    /** The fecha inicial. */
    private Date fechaInicial;
    
    /** The fecha final. */
    private Date fechaFinal;
    
    /** The fecha creacion. */
    private Date fechaCreacion;
    
    /** The fecha eliminacion. */
    private Date fechaEliminacion;
    
    /** The documento. */
    private String documento;
    
    /** The tipo documento. */
    private String tipoDocumento;
    
    /** The estado. */
    private String estado;
    
    /** The sede. */
    private Sede sede;
    /** The fecha inicio reclamacion. */
    private Date fechaInicioReclamacion;

    /** The fecha final reclamacion. */
    private Date fechaFinalReclamacion;

    /** The fecha inicio reclamacion evaluación. */
    private Date fechaInicioReclamacionEvaluacion;

    /** The fecha final reclamacion evaluación. */
    private Date fechaFinalReclamacionEvaluacion;

    /** The tiene oficio elegibles. */
    private Boolean tieneOficioElegibles;

    /** The texto oficio elegibles. */
    private String textoOficioElegibles;

    /** The fecha oficio elegibles. */
    private Date fechaOficioElegibles;

    /** The id persona oficio elegibles. */
    private String idPersonaOficioElegibles;

    /** The tipo id persona oficio elegibles. */
    private String tipoIdPersonaOficioElegibles;

    /** The tiene oficio aprobados. */
    private Boolean tieneOficioAprobados;

    /** The texto oficio aprobados. */
    private String textoOficioAprobados;

    /** The fecha oficio aprobados. */
    private Date fechaOficioAprobados;

    /** The id persona oficio aprobados. */
    private String idPersonaOficioAprobados;

    /** The tipo id persona oficio aprobados. */
    private String tipoIdPersonaOficioAprobados;

    /** The mostrar vigencia aprobados. */
    private Boolean mostrarVigenciaAprobados;

    /** The mostrar firma aprobados. */
    private Boolean mostrarFirmaAprobados;

    /** The responsable oficio aprobados. */
    private String responsableOficioAprobados;

    /** The cargo responsable oficio aprobados. */
    private String cargoResponsableOficioAprobados;

    /** The numero oficio aprobados. */
    private String numeroOficioAprobados;

    /** The dependencia generadora oficio aprobados. */
    private Dependencia dependenciaGeneradoraOficioAprobados;

    /** The fecha generacion oficio aprobados. */
    private Date fechaGeneracionOficioAprobados;

    /** The vigencia aprobados. */
    private Long vigenciaAprobados;
    
    private Date fechaPublicacionResultados;
    
    private Long numProyPorInvPorConvPorCorte;

    /**
     * Instantiates a new corte convocatoria.
     */
    public CorteConvocatoria() {
        /**
         * Empty method.
         */
    }

    /**
     * Instantiates a new corte convocatoria.
     *
     * @param id the id
     */
    public CorteConvocatoria(Long id) {
        this.id = id;
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
     * @param id the new id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the numero.
     *
     * @return the numero
     */
    public Long getNumero() {
        return numero;
    }

    /**
     * Sets the numero.
     *
     * @param numero the new numero
     */
    public void setNumero(Long numero) {
        this.numero = numero;
    }

    /**
     * Gets the fecha inicial.
     *
     * @return the fecha inicial
     */
    public Date getFechaInicial() {
        return fechaInicial;
    }

    /**
     * Sets the fecha inicial.
     *
     * @param fechaInicial the new fecha inicial
     */
    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    /**
     * Gets the fecha final.
     *
     * @return the fecha final
     */
    public Date getFechaFinal() {
        return fechaFinal;
    }

    /**
     * Sets the fecha final.
     *
     * @param fechaFinal the new fecha final
     */
    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    /**
     * Gets the convocatoria padre.
     *
     * @return the convocatoria padre
     */
    public ConvocatoriaPadre getConvocatoriaPadre() {
        return convocatoriaPadre;
    }

    /**
     * Sets the convocatoria padre.
     *
     * @param convocatoriaPadre the new convocatoria padre
     */
    public void setConvocatoriaPadre(ConvocatoriaPadre convocatoriaPadre) {
        this.convocatoriaPadre = convocatoriaPadre;
    }

    /**
     * Gets the documento.
     *
     * @return the documento
     */
    public String getDocumento() {
        return documento;
    }

    /**
     * Sets the documento.
     *
     * @param documento the new documento
     */
    public void setDocumento(String documento) {
        this.documento = documento;
    }

    /**
     * Gets the tipo documento.
     *
     * @return the tipo documento
     */
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * Sets the tipo documento.
     *
     * @param tipoDocumento the new tipo documento
     */
    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
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
     * @param estado the new estado
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Gets the sede.
     *
     * @return the sede
     */
    public Sede getSede() {
        return sede;
    }

    /**
     * Sets the sede.
     *
     * @param sede the new sede
     */
    public void setSede(Sede sede) {
        this.sede = sede;
    }

    /**
     * Gets the fecha creacion.
     *
     * @return the fecha creacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Sets the fecha creacion.
     *
     * @param fechaCreacion the new fecha creacion
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * Gets the fecha eliminacion.
     *
     * @return the fecha eliminacion
     */
    public Date getFechaEliminacion() {
        return fechaEliminacion;
    }

    /**
     * Sets the fecha eliminacion.
     *
     * @param fechaEliminacion the new fecha eliminacion
     */
    public void setFechaEliminacion(Date fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }

    /**
     * Gets the fecha inicio reclamacion.
     *
     * @return the fecha inicio reclamacion
     */
    public Date getFechaInicioReclamacion() {
        return fechaInicioReclamacion;
    }

    /**
     * Sets the fecha inicio reclamacion.
     *
     * @param fechaInicioReclamacion the new fecha inicio reclamacion
     */
    public void setFechaInicioReclamacion(Date fechaInicioReclamacion) {
        this.fechaInicioReclamacion = fechaInicioReclamacion;
    }

    /**
     * Gets the fecha final reclamacion.
     *
     * @return the fecha final reclamacion
     */
    public Date getFechaFinalReclamacion() {
        return fechaFinalReclamacion;
    }

    /**
     * Sets the fecha final reclamacion.
     *
     * @param fechaFinalReclamacion the new fecha final reclamacion
     */
    public void setFechaFinalReclamacion(Date fechaFinalReclamacion) {
        this.fechaFinalReclamacion = fechaFinalReclamacion;
    }

    /**
     * Gets the fecha inicio reclamacion evaluacion.
     *
     * @return the fecha inicio reclamacion evaluacion
     */
    public Date getFechaInicioReclamacionEvaluacion() {
        return fechaInicioReclamacionEvaluacion;
    }

    /**
     * Sets the fecha inicio reclamacion evaluacion.
     *
     * @param fechaInicioReclamacionEvaluacion the new fecha inicio reclamacion evaluacion
     */
    public void setFechaInicioReclamacionEvaluacion(Date fechaInicioReclamacionEvaluacion) {
        this.fechaInicioReclamacionEvaluacion = fechaInicioReclamacionEvaluacion;
    }

    /**
     * Gets the fecha final reclamacion evaluacion.
     *
     * @return the fecha final reclamacion evaluacion
     */
    public Date getFechaFinalReclamacionEvaluacion() {
        return fechaFinalReclamacionEvaluacion;
    }

    /**
     * Sets the fecha final reclamacion evaluacion.
     *
     * @param fechaFinalReclamacionEvaluacion the new fecha final reclamacion evaluacion
     */
    public void setFechaFinalReclamacionEvaluacion(Date fechaFinalReclamacionEvaluacion) {
        this.fechaFinalReclamacionEvaluacion = fechaFinalReclamacionEvaluacion;
    }

    /**
     * Gets the tiene oficio elegibles.
     *
     * @return the tieneOficioElegibles
     */
    public Boolean getTieneOficioElegibles() {
        return tieneOficioElegibles;
    }

    /**
     * Sets the tiene oficio elegibles.
     *
     * @param tieneOficioElegibles            the tieneOficioElegibles to set
     */
    public void setTieneOficioElegibles(Boolean tieneOficioElegibles) {
        this.tieneOficioElegibles = tieneOficioElegibles;
    }

    /**
     * Gets the texto oficio elegibles.
     *
     * @return the textoOficioElegibles
     */
    public String getTextoOficioElegibles() {
        return textoOficioElegibles;
    }

    /**
     * Sets the texto oficio elegibles.
     *
     * @param textoOficioElegibles            the textoOficioElegibles to set
     */
    public void setTextoOficioElegibles(String textoOficioElegibles) {
        this.textoOficioElegibles = textoOficioElegibles;
    }

    /**
     * Gets the fecha oficio elegibles.
     *
     * @return the fechaOficioElegibles
     */
    public Date getFechaOficioElegibles() {
        return fechaOficioElegibles;
    }

    /**
     * Sets the fecha oficio elegibles.
     *
     * @param fechaOficioElegibles            the fechaOficioElegibles to set
     */
    public void setFechaOficioElegibles(Date fechaOficioElegibles) {
        this.fechaOficioElegibles = fechaOficioElegibles;
    }

    /**
     * Gets the id persona oficio elegibles.
     *
     * @return the idPersonaOficioElegibles
     */
    public String getIdPersonaOficioElegibles() {
        return idPersonaOficioElegibles;
    }

    /**
     * Sets the id persona oficio elegibles.
     *
     * @param idPersonaOficioElegibles            the idPersonaOficioElegibles to set
     */
    public void setIdPersonaOficioElegibles(String idPersonaOficioElegibles) {
        this.idPersonaOficioElegibles = idPersonaOficioElegibles;
    }

    /**
     * Gets the tipo id persona oficio elegibles.
     *
     * @return the tipoIdPersonaOficioElegibles
     */
    public String getTipoIdPersonaOficioElegibles() {
        return tipoIdPersonaOficioElegibles;
    }

    /**
     * Sets the tipo id persona oficio elegibles.
     *
     * @param tipoIdPersonaOficioElegibles            the tipoIdPersonaOficioElegibles to set
     */
    public void setTipoIdPersonaOficioElegibles(String tipoIdPersonaOficioElegibles) {
        this.tipoIdPersonaOficioElegibles = tipoIdPersonaOficioElegibles;
    }

    /**
     * Gets the tiene oficio aprobados.
     *
     * @return the tieneOficioAprobados
     */
    public Boolean getTieneOficioAprobados() {
        return tieneOficioAprobados;
    }

    /**
     * Sets the tiene oficio aprobados.
     *
     * @param tieneOficioAprobados            the tieneOficioAprobados to set
     */
    public void setTieneOficioAprobados(Boolean tieneOficioAprobados) {
        this.tieneOficioAprobados = tieneOficioAprobados;
    }

    /**
     * Gets the texto oficio aprobados.
     *
     * @return the textoOficioAprobados
     */
    public String getTextoOficioAprobados() {
        return textoOficioAprobados;
    }

    /**
     * Sets the texto oficio aprobados.
     *
     * @param textoOficioAprobados            the textoOficioAprobados to set
     */
    public void setTextoOficioAprobados(String textoOficioAprobados) {
        this.textoOficioAprobados = textoOficioAprobados;
    }

    /**
     * Gets the fecha oficio aprobados.
     *
     * @return the fechaOficioAprobados
     */
    public Date getFechaOficioAprobados() {
        return fechaOficioAprobados;
    }

    /**
     * Sets the fecha oficio aprobados.
     *
     * @param fechaOficioAprobados            the fechaOficioAprobados to set
     */
    public void setFechaOficioAprobados(Date fechaOficioAprobados) {
        this.fechaOficioAprobados = fechaOficioAprobados;
    }

    /**
     * Gets the id persona oficio aprobados.
     *
     * @return the idPersonaOficioAprobados
     */
    public String getIdPersonaOficioAprobados() {
        return idPersonaOficioAprobados;
    }

    /**
     * Sets the id persona oficio aprobados.
     *
     * @param idPersonaOficioAprobados            the idPersonaOficioAprobados to set
     */
    public void setIdPersonaOficioAprobados(String idPersonaOficioAprobados) {
        this.idPersonaOficioAprobados = idPersonaOficioAprobados;
    }

    /**
     * Gets the tipo id persona oficio aprobados.
     *
     * @return the tipoIdPersonaOficioAprobados
     */
    public String getTipoIdPersonaOficioAprobados() {
        return tipoIdPersonaOficioAprobados;
    }

    /**
     * Sets the tipo id persona oficio aprobados.
     *
     * @param tipoIdPersonaOficioAprobados            the tipoIdPersonaOficioAprobados to set
     */
    public void setTipoIdPersonaOficioAprobados(String tipoIdPersonaOficioAprobados) {
        this.tipoIdPersonaOficioAprobados = tipoIdPersonaOficioAprobados;
    }

    /**
     * Gets the mostrar vigencia aprobados.
     *
     * @return the mostrarVigenciaAprobados
     */
    public Boolean getMostrarVigenciaAprobados() {
        return mostrarVigenciaAprobados;
    }

    /**
     * Sets the mostrar vigencia aprobados.
     *
     * @param mostrarVigenciaAprobados            the mostrarVigenciaAprobados to set
     */
    public void setMostrarVigenciaAprobados(Boolean mostrarVigenciaAprobados) {
        this.mostrarVigenciaAprobados = mostrarVigenciaAprobados;
    }

    /**
     * Gets the mostrar firma aprobados.
     *
     * @return the mostrarFirmaAprobados
     */
    public Boolean getMostrarFirmaAprobados() {
        return mostrarFirmaAprobados;
    }

    /**
     * Sets the mostrar firma aprobados.
     *
     * @param mostrarFirmaAprobados            the mostrarFirmaAprobados to set
     */
    public void setMostrarFirmaAprobados(Boolean mostrarFirmaAprobados) {
        this.mostrarFirmaAprobados = mostrarFirmaAprobados;
    }

    /**
     * Gets the responsable oficio aprobados.
     *
     * @return the responsableOficioAprobados
     */
    public String getResponsableOficioAprobados() {
        return responsableOficioAprobados;
    }

    /**
     * Sets the responsable oficio aprobados.
     *
     * @param responsableOficioAprobados            the responsableOficioAprobados to set
     */
    public void setResponsableOficioAprobados(String responsableOficioAprobados) {
        this.responsableOficioAprobados = responsableOficioAprobados;
    }

    /**
     * Gets the cargo responsable oficio aprobados.
     *
     * @return the cargoResponsableOficioAprobados
     */
    public String getCargoResponsableOficioAprobados() {
        return cargoResponsableOficioAprobados;
    }

    /**
     * Sets the cargo responsable oficio aprobados.
     *
     * @param cargoResponsableOficioAprobados            the cargoResponsableOficioAprobados to set
     */
    public void setCargoResponsableOficioAprobados(String cargoResponsableOficioAprobados) {
        this.cargoResponsableOficioAprobados = cargoResponsableOficioAprobados;
    }

    /**
     * Gets the numero oficio aprobados.
     *
     * @return the numeroOficioAprobados
     */
    public String getNumeroOficioAprobados() {
        return numeroOficioAprobados;
    }

    /**
     * Sets the numero oficio aprobados.
     *
     * @param numeroOficioAprobados            the numeroOficioAprobados to set
     */
    public void setNumeroOficioAprobados(String numeroOficioAprobados) {
        this.numeroOficioAprobados = numeroOficioAprobados;
    }

    /**
     * Gets the dependencia generadora oficio aprobados.
     *
     * @return the dependenciaGeneradoraOficioAprobados
     */
    public Dependencia getDependenciaGeneradoraOficioAprobados() {
        return dependenciaGeneradoraOficioAprobados;
    }

    /**
     * Sets the dependencia generadora oficio aprobados.
     *
     * @param dependenciaGeneradoraOficioAprobados            the dependenciaGeneradoraOficioAprobados to set
     */
    public void setDependenciaGeneradoraOficioAprobados(Dependencia dependenciaGeneradoraOficioAprobados) {
        this.dependenciaGeneradoraOficioAprobados = dependenciaGeneradoraOficioAprobados;
    }

    /**
     * Gets the fecha generacion oficio aprobados.
     *
     * @return the fechaGeneracionOficioAprobados
     */
    public Date getFechaGeneracionOficioAprobados() {
        return fechaGeneracionOficioAprobados;
    }

    /**
     * Sets the fecha generacion oficio aprobados.
     *
     * @param fechaGeneracionOficioAprobados            the fechaGeneracionOficioAprobados to set
     */
    public void setFechaGeneracionOficioAprobados(Date fechaGeneracionOficioAprobados) {
        this.fechaGeneracionOficioAprobados = fechaGeneracionOficioAprobados;
    }

    /**
     * Gets the vigencia aprobados.
     *
     * @return the vigenciaAprobados
     */
    public Long getVigenciaAprobados() {
        return vigenciaAprobados;
    }

    /**
     * Sets the vigencia aprobados.
     *
     * @param vigenciaAprobados            the vigenciaAprobados to set
     */
    public void setVigenciaAprobados(Long vigenciaAprobados) {
        this.vigenciaAprobados = vigenciaAprobados;
    }

	public Date getFechaPublicacionResultados() {
		return fechaPublicacionResultados;
	}

	public void setFechaPublicacionResultados(Date fechaPublicacionResultados) {
		this.fechaPublicacionResultados = fechaPublicacionResultados;
	}

	public Long getNumProyPorInvPorConvPorCorte() {
		return numProyPorInvPorConvPorCorte;
	}

	public void setNumProyPorInvPorConvPorCorte(Long numProyPorInvPorConvPorCorte) {
		this.numProyPorInvPorConvPorCorte = numProyPorInvPorConvPorCorte;
	}
}
