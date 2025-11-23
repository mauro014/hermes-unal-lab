/*
 * Created on 27-ene-2006
 */
package co.edu.unal.hermes.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The Class ProyectoEvaluador.
 *
 * @author jpduqueg
 * @modified Rodrigo Gallo
 */
public class ProyectoEvaluador implements Comparable {

    public static final String ESTADO_PARCIAL = "P";
    
    public static final String MESA_TRABAJO_ID = "MESATRABAJO";
    
    public static final String COMITE_SELECCION_ID = "COMITESELECCION";
    
    /** The id. */
    private Long id;
    
    /** The proyecto. */
    private Proyecto proyecto;

    /** The evaluador. */
    private Investigador evaluador;
    
    /** The observaciones. */
    private String observaciones;
    
    /** The activo. */
    private String activo = "";
    
    /** The concepto. */
    private TipoConcepto concepto;
    
    /** The calificacion final. */
    private Float calificacionFinal;
    
    /** The tipo financiacion. */
    private TipoFinanciacion tipoFinanciacion;
    
    /** The estado. */
    private String estado;
    
    /** The fecha. */
    private Date fecha;
    
    /** The fecha bd. */
    static Date fechaBD;
    
    /** The elegible. */
    boolean elegible = true;
    
    /** The lista archivos convocatoria padre. */
    private List<ArchivoConvocatoriaPadre> listaArchivosConvocatoriaPadre;
    
    /** The documento evaluador. */
    private String documentoEvaluador;
    
    /** The mostrar archivos evaluador. */
    private boolean mostrarArchivosEvaluador;

    /** Atributos utilizados para la carga de los archivos cuando un evaluador va a evaluar un proyecto List listaArchivos boolean mostrarArchivos. */
    private List listaArchivos;
    
    /** The mostrar archivos. */
    private boolean mostrarArchivos;
    
    /** The mostrar evaluacion. */
    private boolean mostrarEvaluacion;
    
    // seleccion
    private Date fechaSeleccion;
    
    private String observacionesSeleccion;
    
    private Float calificacionSeleccion;
    
    private Float calificacionFinalSeleccion;
    
    private TipoConcepto conceptoSeleccion;
    
    private String estadoSeleccion;
    
    private String documentoSeleccion;
    
    private String tipoEvaluacion;
    
    private String justificacionBancoFinanciable;
    
    private Date fechaLimite;
    
    private String comentariosCoordinador;
    
    private boolean banderaSeleccion; // se usa para identificar en la vista puesto que la seleccion se guarda como mesadetrabajo

    /**
     * Checks if is evaluacion.
     *
     * @return true, if is evaluacion
     */
    public boolean isEvaluacion() {
        Date fechaI = ((Convocatoria) this.proyecto.getModalidad()).getFechaInicioEval();

        Date fechaF = ((Convocatoria) this.proyecto.getModalidad()).getFechaFinalEval();
        try {
            ConvocatoriaPadre padre = ((Convocatoria) this.proyecto.getModalidad()).getPadre();
            if (padre.getEsPermanente() != null && padre.getEsPermanente().equals("Y")) {
                if (fechaI == null || fechaF == null) {
                    return false;
                }

                if (fechaBD.after(fechaI) && fechaBD.before(fechaF) || fechaBD.equals(fechaI)
                        || fechaBD.equals(fechaF)) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }

        if (fechaI == null || fechaF == null) {
            return false;
        }

        if (fechaBD.after(fechaI) && fechaBD.before(fechaF) || fechaBD.equals(fechaI) || fechaBD.equals(fechaF)) {
            return true;
        }

        return false;
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
     * Gets the tipo financiacion.
     *
     * @return the tipo financiacion
     */
    public TipoFinanciacion getTipoFinanciacion() {
        return tipoFinanciacion;
    }

    /**
     * Sets the tipo financiacion.
     *
     * @param tipoFinanciacion the new tipo financiacion
     */
    public void setTipoFinanciacion(TipoFinanciacion tipoFinanciacion) {
        this.tipoFinanciacion = tipoFinanciacion;
    }

    /** The calificaciones. */
    private Set calificaciones = new HashSet();

    /**
     * Adicionar calificacion.
     *
     * @param calificacion the calificacion
     */
    public void adicionarCalificacion(CalificacionEvaluacion calificacion) {
        calificacion.setProyectoEvaluador(this);
        calificaciones.add(calificacion);
    }

    /**
     * Borrar calificacion.
     *
     * @param calificacion the calificacion
     */
    public void borrarCalificacion(CalificacionEvaluacion calificacion) {
        calificaciones.remove(calificacion);
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
     * Gets the calificacion final.
     *
     * @return the calificacion final
     */
    public Float getCalificacionFinal() {
        return calificacionFinal;
    }

    /**
     * Sets the calificacion final.
     *
     * @param calificacionFinal the new calificacion final
     */
    public void setCalificacionFinal(Float calificacionFinal) {
        this.calificacionFinal = calificacionFinal;
    }

    /**
     * Gets the concepto.
     *
     * @return the concepto
     */
    public TipoConcepto getConcepto() {
        return concepto;
    }

    /**
     * Sets the concepto.
     *
     * @param concepto the new concepto
     */
    public void setConcepto(TipoConcepto concepto) {
        this.concepto = concepto;
    }

    /**
     * Gets the observaciones.
     *
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * Sets the observaciones.
     *
     * @param observaciones the new observaciones
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * Gets the proyecto.
     *
     * @return the proyecto
     */
    public Proyecto getProyecto() {
        return proyecto;
    }

    /**
     * Sets the proyecto.
     *
     * @param proyecto the new proyecto
     */
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    /**
     * Gets the calificaciones.
     *
     * @return the calificaciones
     */
    public Set getCalificaciones() {
        return calificaciones;
    }

    /**
     * Sets the calificaciones.
     *
     * @param calificaciones the new calificaciones
     */
    public void setCalificaciones(Set calificaciones) {
        this.calificaciones = calificaciones;
    }

    /**
     * Gets the lista calificaciones.
     *
     * @return the lista calificaciones
     */
    public List getListaCalificaciones() {
        List listaC = new ArrayList();
        listaC.addAll(calificaciones);
        return listaC;
    }

    /**
     * Gets the evaluador.
     *
     * @return the evaluador
     */
    public Investigador getEvaluador() {
        return evaluador;
    }

    /**
     * Sets the evaluador.
     *
     * @param evaluador the new evaluador
     */
    public void setEvaluador(Investigador evaluador) {
        this.evaluador = evaluador;
    }

    /**
     * Gets the fecha.
     *
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Sets the fecha.
     *
     * @param fecha the new fecha
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Gets the lista archivos.
     *
     * @return the lista archivos
     */
    public List getListaArchivos() {
        return listaArchivos;
    }

    /**
     * Sets the lista archivos.
     *
     * @param listaArchivos the new lista archivos
     */
    public void setListaArchivos(List listaArchivos) {
        this.listaArchivos = listaArchivos;
    }

    /**
     * Checks if is mostrar archivos.
     *
     * @return true, if is mostrar archivos
     */
    public boolean isMostrarArchivos() {
        return mostrarArchivos;
    }

    /**
     * Sets the mostrar archivos.
     *
     * @param mostrarArchivos the new mostrar archivos
     */
    public void setMostrarArchivos(boolean mostrarArchivos) {
        this.mostrarArchivos = mostrarArchivos;
    }

    /**
     * Gets the nombre modalidad.
     *
     * @return the nombre modalidad
     */
    public String getNombreModalidad() {

        String nombreModalidad = " ";
        Modalidad mod = this.proyecto.getModalidad();
        if (mod instanceof Convocatoria) {
            nombreModalidad = ((Convocatoria) mod).getTitulo();

        } else if (mod instanceof JornadaDocente) {
            nombreModalidad = ((JornadaDocente) mod).getDescripcion();
        } else if (mod instanceof Contrapartida) {
            nombreModalidad = ((Contrapartida) mod).getNombre();
        } else if (mod instanceof Registro) {
            nombreModalidad = ((Registro) mod).getNombre();
        } else {
            nombreModalidad = "sin nombre";
        }

        return nombreModalidad;

    }

    /**
     * Gets the nombre convocatoria.
     *
     * @return the nombre convocatoria
     */
    public String getNombreConvocatoria() {

        String nombreModalidad = " ";
        Modalidad mod = this.proyecto.getModalidad();
        if (mod instanceof Convocatoria) {
            nombreModalidad = ((Convocatoria) mod).getPadre().getTitulo();
        } else if (mod instanceof JornadaDocente) {
            nombreModalidad = ((JornadaDocente) mod).getDescripcion();
        } else if (mod instanceof Contrapartida) {
            nombreModalidad = ((Contrapartida) mod).getNombre();
        } else if (mod instanceof Registro) {
            nombreModalidad = ((Registro) mod).getNombre();
        } else {
            nombreModalidad = "sin nombre";
        }

        return nombreModalidad;

    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object proy) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;

        if (this == proy)
            return EQUAL;

        if (this.proyecto.getId().longValue() < ((ProyectoEvaluador) proy).proyecto.getId().longValue())
            return BEFORE;
        if (this.proyecto.getId().longValue() > ((ProyectoEvaluador) proy).proyecto.getId().longValue())
            return AFTER;

        return EQUAL;

    }

    /**
     * Checks if is mostrar evaluacion.
     *
     * @return true, if is mostrar evaluacion
     */
    public boolean isMostrarEvaluacion() {
        mostrarEvaluacion = isEvaluacion();
        return mostrarEvaluacion;
    }

    /**
     * Sets the mostrarevaluacion.
     *
     * @param mostrarevaluacion the new mostrarevaluacion
     */
    public void setMostrarevaluacion(boolean mostrarevaluacion) {
        this.mostrarEvaluacion = mostrarevaluacion;
    }

    /**
     * Gets the fecha bd.
     *
     * @return the fecha bd
     */
    public static Date getFechaBD() {
        return fechaBD;
    }

    /**
     * Sets the fecha bd.
     *
     * @param fechaBD the new fecha bd
     */
    public static void setFechaBD(Date fechaBD) {
        ProyectoEvaluador.fechaBD = fechaBD;
    }

    /**
     * Gets the activo.
     *
     * @return the activo
     */
    public String getActivo() {
        return activo;
    }

    /**
     * Sets the activo.
     *
     * @param activo the new activo
     */
    public void setActivo(String activo) {
        this.activo = activo;
    }

    /**
     * Checks if is elegible.
     *
     * @return true, if is elegible
     */
    public boolean isElegible() {
        if (this.activo != null && this.activo.equals("N")) {
            elegible = false;

        }
        if (this.activo == null || this.activo.length() == 0) {
            elegible = true;
        }
        return elegible;
    }

    /**
     * Sets the elegible.
     *
     * @param elegible the new elegible
     */
    public void setElegible(boolean elegible) {
        this.elegible = elegible;
    }

    /**
     * Gets the lista convotoria padre.
     *
     * @return the lista convotoria padre
     */
    public List getListaConvotoriaPadre() {
        ConvocatoriaPadre padre = ((Convocatoria) this.proyecto.getModalidad()).getPadre();
        if (padre != null) {
        }
        return null;
    }

    /**
     * Sets the lista archivos convocatoria padre.
     *
     * @param listaArchivosConvocatoriaPadre the new lista archivos convocatoria padre
     */
    public void setListaArchivosConvocatoriaPadre(List<ArchivoConvocatoriaPadre> listaArchivosConvocatoriaPadre) {
        this.listaArchivosConvocatoriaPadre = listaArchivosConvocatoriaPadre;
    }

    /**
     * Gets the lista archivos convocatoria padre.
     *
     * @return the lista archivos convocatoria padre
     */
    public List<ArchivoConvocatoriaPadre> getListaArchivosConvocatoriaPadre() {
        return listaArchivosConvocatoriaPadre;
    }

    /**
     * Gets the documento evaluador.
     *
     * @return the documento evaluador
     */
    public String getDocumentoEvaluador() {
        return documentoEvaluador;
    }

    /**
     * Sets the documento evaluador.
     *
     * @param documentoEvaluador the new documento evaluador
     */
    public void setDocumentoEvaluador(String documentoEvaluador) {
        this.documentoEvaluador = documentoEvaluador;
    }

    /**
     * Checks if is mostrar archivos evaluador.
     *
     * @return true, if is mostrar archivos evaluador
     */
    public boolean isMostrarArchivosEvaluador() {
        return mostrarArchivosEvaluador;
    }

    /**
     * Sets the mostrar archivos evaluador.
     *
     * @param mostrarArchivosEvaluador the new mostrar archivos evaluador
     */
    public void setMostrarArchivosEvaluador(boolean mostrarArchivosEvaluador) {
        this.mostrarArchivosEvaluador = mostrarArchivosEvaluador;
    }

	public Date getFechaSeleccion() {
		return fechaSeleccion;
	}

	public void setFechaSeleccion(Date fechaSeleccion) {
		this.fechaSeleccion = fechaSeleccion;
	}

	public String getObservacionesSeleccion() {
		return observacionesSeleccion;
	}

	public void setObservacionesSeleccion(String observacionesSeleccion) {
		this.observacionesSeleccion = observacionesSeleccion;
	}

	public TipoConcepto getConceptoSeleccion() {
		return conceptoSeleccion;
	}

	public void setConceptoSeleccion(TipoConcepto conceptoSeleccion) {
		this.conceptoSeleccion = conceptoSeleccion;
	}

	public String getEstadoSeleccion() {
		return estadoSeleccion;
	}

	public void setEstadoSeleccion(String estadoSeleccion) {
		this.estadoSeleccion = estadoSeleccion;
	}

	public String getDocumentoSeleccion() {
		return documentoSeleccion;
	}

	public void setDocumentoSeleccion(String documentoSeleccion) {
		this.documentoSeleccion = documentoSeleccion;
	}

	public Float getCalificacionSeleccion() {
		return calificacionSeleccion;
	}

	public void setCalificacionSeleccion(Float calificacionSeleccion) {
		this.calificacionSeleccion = calificacionSeleccion;
	}

	public Float getCalificacionFinalSeleccion() {
		return calificacionFinalSeleccion;
	}

	public void setCalificacionFinalSeleccion(Float calificacionFinalSeleccion) {
		this.calificacionFinalSeleccion = calificacionFinalSeleccion;
	}

	public String getTipoEvaluacion() {
		return tipoEvaluacion;
	}

	public void setTipoEvaluacion(String tipoEvaluacion) {
		this.tipoEvaluacion = tipoEvaluacion;
	}
	
	public String getJustificacionBancoFinanciable() {
		return justificacionBancoFinanciable;
	}

	public void setJustificacionBancoFinanciable(
			String justificacionBancoFinanciable) {
		this.justificacionBancoFinanciable = justificacionBancoFinanciable;
	}

	public Date getFechaLimite() {
		return fechaLimite;
	}

	public void setFechaLimite(Date fechaLimite) {
		this.fechaLimite = fechaLimite;
	}

	public String getComentariosCoordinador() {
		return comentariosCoordinador;
	}

	public void setComentariosCoordinador(String comentariosCoordinador) {
		this.comentariosCoordinador = comentariosCoordinador;
	}

	public boolean isBanderaSeleccion() {
		return banderaSeleccion;
	}

	public void setBanderaSeleccion(boolean banderaSeleccion) {
		this.banderaSeleccion = banderaSeleccion;
	}

}
