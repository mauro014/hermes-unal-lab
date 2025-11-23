package co.edu.unal.hermes.modelo;

/**
 * The Class CorreoPlantilla.
 * 
 * @author Juan Pablo Duque G.
 */
public class CorreoPlantilla {
	
	public static final int INICIO_BANCO_PROYECTOS = 0;

    public static final int INICIO_FIRMADO = 1;
    
    public static final int USUARIO_CONTRASENIA = 6;
    
    public static final Long INICIO_PROYECTO_LABORATORIOS = 16L;
    
    /** The Constant COMPROMISO_EXTERNAS. */
    public static final Long RESOLUCION_MODIFICACION = 22L;
    
	/** The Constant INICIO_GRUPO. */
	public static final int INICIO_GRUPO = 52;

	/** The Constant INICIO_PRINCIPAL. */
	public static final int INICIO_PRINCIPAL = 53;

	/** The Constant INICIO_ESTUDIANTE. */
	public static final int INICIO_ESTUDIANTE = 54;

	/** The Constant FINALIZACION_PRINCIPAL. */
	public static final int FINALIZACION_PRINCIPAL = 55;

	/** The Constant FINALIZACION_ESTUDIANTE. */
	public static final int FINALIZACION_ESTUDIANTE = 56;

	/** The Constant CAMBIO_RUBRO. */
	public static final int CAMBIO_RUBRO = 58;

	/** The Constant PRORROGA. */
	public static final int PRORROGA = 60;

	/** The Constant CANCELACION_PRINCIPAL. */
	public static final int CANCELACION_PRINCIPAL = 92;

	/** The Constant CANCELACION_ESTUDIANTE. */
	public static final int CANCELACION_ESTUDIANTE = 93;

	/** The Constant CERTIFICADO_MOVILIZACION. */
	public static final int CERTIFICADO_MOVILIZACION = 164;

	/** The Constant INICIO_JOVENES_INVESTIGADORES. */
	public static final int INICIO_JOVENES_INVESTIGADORES = 178;

	/** The Constant INICIO_SEMILLEROS. */
	public static final int INICIO_SEMILLEROS = 179;

	/** The Constant INICIO_JOVENES_INVESTIGADOR_TUTOR. */
	public static final int INICIO_JOVENES_INVESTIGADOR_TUTOR = 187;
	
	/** The Constant INICIO_SIN_FINANCIACION. */
	public static final int INICIO_SIN_FINANCIACION = 201;
	
	/** The Constant FINALIZACION_JOVENES_INVESTIGADORES. */
    public static final int FINALIZACION_JOVENES_INVESTIGADORES = 220;

	/** The Constant INICIO_JOVENES_INVESTIGADOR_TUTOR_2014. */
	public static final int INICIO_JOVENES_INVESTIGADOR_TUTOR_2014 = 222;
	
	/** The Constant FINALIZACION_PRINCIPAL_EXTERNA. */
	public static final Long FINALIZACION_PRINCIPAL_EXTERNA = 223L;

	/** The Constant INICIO_TRADUCCION_ARTICULOS. */
	public static final int INICIO_TRADUCCION_ARTICULOS = 233;

	/** The Constant COMPROMISO_EXTERNAS. */
	public static final int COMPROMISO_EXTERNAS = 234;
	
	/** The Constant FINALIZACION_JOVENES_INVESTIGADORES. */
    public static final int FINALIZACION_SIN_FINANCIACION = 273;
    
    /** The Constant COMPROMISO_EXTERNAS. */
    public static final Long RESOLUCION_EXTERNOS = 285L;
    
    /** The Constant REGISTRO_SOLICITUD_SNL. */
	public static final int REGISTRO_SOLICITUD_SNL = 254;

	/** The Constant CAMBIO_INVESTIGADOR_PRINCIPAL. */
	public static final int CAMBIO_INVESTIGADOR_PRINCIPAL = 260;

	/** The Constant CAMBIO_INTEGRANTES. */
	public static final int CAMBIO_INTEGRANTES = 262;

	/** The Constant CAMBIO_CONTENIDO. */
	public static final int CAMBIO_CONTENIDO = 263;

	/** The Constant REACTIVACION. */
	public static final int REACTIVACION = 264;
	
	/** The Constant RESPUESTA_SOLICITUD_SNL. */
    public static final int RESPUESTA_SOLICITUD_SNL = 265;
    
    public static final Long INICIO_CONVOCATORIA_REPO_EQUIPOS = 303L;
	
	public static final int SUSPENSION = 312;
	
	public static final int ADICION_PRESUPUESTAL = 313;
    
    public static final Long CORREO_ALERTA_EQUIPO_DANADO_PERDIDO = 314L;
    
    public static final Long CORREO_NOTIFICACION_EQUIPO_ASOCIADO = 318L;
    
    public static final Long CORREO_NOTIFICACION_EQUIPO_DESVINCULADO = 323L;
    public static final Long CORREO_NOTIFICACION_EQUIPO_TRANSLADADO = 322L;
    public static final Long CORREO_NOTIFICACION_SOLICITUD_DESVINCULAR_EQUIPO_DLS = 321L;
    
    public static final Long CORREO_NOTIFICACION_PREASOCIAR_LABORATORIO_A_PROYECTO = 324L;
    
    public static final int CORREO_NOTIFICACION_VINCULACION_DOCENTES_PRY = 329;
    
    public static final Long RESOLUCION_EXTERNOS_CONTRAPARTIDA = 330L;
    
    public static final Long CORREO_NOTIFICACION_EQUIPO_DANADO_PERDIDO = 333L;
    
    public static final Long CORREO_NOTIFICACION_ASOCIACION_LAB_PROYECTO_APROBADO = 358L;
    public static final Long CORREO_NOTIFICACION_ELIMINACION_LAB_PROYECTO_ACTIVO = 359L;
    public static final Long CORREO_NOTIFICACION_ASOCIACION_LAB_PROYECTO_ACTIVO = 360L;
    
    public static final int CORREO_EDICION_PROYECTO_REGALIAS = 408;

	/** The Constant CONECTOR. */
	public static final String CONECTOR = "--";

	/** The Constant CARTAS_UNICAS. */
	public static final String CARTAS_FINANCIACION_OBLIGATORIA = CONECTOR
			+ CANCELACION_PRINCIPAL + CONECTOR + CANCELACION_ESTUDIANTE
			+ CONECTOR + INICIO_PRINCIPAL + CONECTOR
			+ FINALIZACION_PRINCIPAL + CONECTOR + FINALIZACION_ESTUDIANTE
			+ CONECTOR;

	/** The Constant CARTAS_UNICAS. */
	public static final String CARTAS_GRUPO_OBLIGATORIA = CONECTOR
			+ INICIO_JOVENES_INVESTIGADORES + CONECTOR + INICIO_GRUPO
			+ CONECTOR;

	/** The id. */
	// llave primaria
	private Long id;

	/** The nombre. */
	private String nombre;

	/** The asunto. */
	private String asunto;

	/** The cuerpo. */
	private String cuerpo;

	/** The descripcion. */
	private String descripcion;

	/** The nombre adjunto. */
	private String nombreAdjunto;

	/** The adjunto. */
	private ArchivoAdjunto adjunto;

	/** The plantilla. */
	private String plantilla;

	/** The tipo. */
	private String tipo;

	/** The evaluacion. */
	private String evaluacion;

    /** The padre. */
    private String padre;

    /** The padre. */
    private String tipoModalidad;

    /** The padre. */
    private Reporte reporte;

	/**
	 * Gets the tipo.
	 * 
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Sets the tipo.
	 * 
	 * @param tipo
	 *            the new tipo
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Instantiates a new correo plantilla.
	 */
	public CorreoPlantilla() {
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
	 * Gets the asunto.
	 * 
	 * @return the asunto
	 */
	public String getAsunto() {
		return asunto;
	}

	/**
	 * Sets the asunto.
	 * 
	 * @param asunto
	 *            the new asunto
	 */
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	/**
	 * Gets the cuerpo.
	 * 
	 * @return the cuerpo
	 */
	public String getCuerpo() {
		return cuerpo;
	}

	/**
	 * Sets the cuerpo.
	 * 
	 * @param cuerpo
	 *            the new cuerpo
	 */
	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

	/**
	 * Gets the descripcion.
	 * 
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Sets the descripcion.
	 * 
	 * @param descripcion
	 *            the new descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Gets the adjunto.
	 * 
	 * @return the adjunto
	 */
	public ArchivoAdjunto getAdjunto() {
		return adjunto;
	}

	/**
	 * Sets the adjunto.
	 * 
	 * @param adjunto
	 *            the new adjunto
	 */
	public void setAdjunto(ArchivoAdjunto adjunto) {
		this.adjunto = adjunto;
	}

	/**
	 * Gets the nombre adjunto.
	 * 
	 * @return the nombre adjunto
	 */
	public String getNombreAdjunto() {
		return nombreAdjunto;
	}

	/**
	 * Sets the nombre adjunto.
	 * 
	 * @param nombreAdjunto
	 *            the new nombre adjunto
	 */
	public void setNombreAdjunto(String nombreAdjunto) {
		this.nombreAdjunto = nombreAdjunto;
	}

	/**
	 * Gets the plantilla.
	 * 
	 * @return the plantilla
	 */
	public String getPlantilla() {
		return plantilla;
	}

	/**
	 * Sets the plantilla.
	 * 
	 * @param plantilla
	 *            the new plantilla
	 */
	public void setPlantilla(String plantilla) {
		this.plantilla = plantilla;
	}

	/**
	 * Sets the evaluacion.
	 * 
	 * @param evaluacion
	 *            the new evaluacion
	 */
	public void setEvaluacion(String evaluacion) {
		this.evaluacion = evaluacion;
	}

	/**
	 * Gets the evaluacion.
	 * 
	 * @return the evaluacion
	 */
	public String getEvaluacion() {
		return evaluacion;
	}

	/**
	 * Gets the padre.
	 * 
	 * @return the padre
	 */
	public String getPadre() {
		return padre;
	}

	/**
	 * Sets the padre.
	 * 
	 * @param padre
	 *            the new padre
	 */
	public void setPadre(String padre) {
		this.padre = padre;
	}

    public String getTipoModalidad() {
        return tipoModalidad;
    }

    public void setTipoModalidad(String tipoModalidad) {
        this.tipoModalidad = tipoModalidad;
    }

    /**
     * @return the reporte
     */
    public Reporte getReporte() {
        return reporte;
    }

    /**
     * @param reporte the reporte to set
     */
    public void setReporte(Reporte reporte) {
        this.reporte = reporte;
    }
    
    public static boolean isEsCartaEstudiante(Long id) {
        return id != null && (id.equals(Long.valueOf(CorreoPlantilla.FINALIZACION_ESTUDIANTE))
                || id.equals(Long.valueOf(CorreoPlantilla.INICIO_ESTUDIANTE))
                );
    }
        
    public static boolean isEsCartaEstudianteExterno(Long id) {
        return id != null && (id.equals(Long.valueOf(CorreoPlantilla.FINALIZACION_JOVENES_INVESTIGADORES)));
    }
    
    public static boolean isEsResolucion(Long id){
        return id != null && (id.equals(CorreoPlantilla.RESOLUCION_EXTERNOS)
        		|| id.equals(CorreoPlantilla.RESOLUCION_EXTERNOS_CONTRAPARTIDA)
                || id.equals(CorreoPlantilla.RESOLUCION_MODIFICACION)
                || id.equals(CorreoPlantilla.FINALIZACION_PRINCIPAL_EXTERNA));
    }
}
