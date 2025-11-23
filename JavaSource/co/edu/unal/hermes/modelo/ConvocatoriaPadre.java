package co.edu.unal.hermes.modelo;

import java.util.Date;
import java.util.List;


/**
 * Especifíca las información principal de la convocatoria.
 */
public class ConvocatoriaPadre extends Modalidad {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3140913717074313304L;

    /** The id. */
    private Long id;

    /** The Constant TIPO_ARCHIVOS. */
    public static final String TIPO_ARCHIVOS = "AR";
    
    /** The Constant TIPO_CONVOCATORIA_LABORATORIOS. */
    public static final String TIPO_CONVOCATORIA_LABORATORIOS = "10";
    
    /** The Constant TIPO_CONVOCATORIA_LABORATORIOS. */
    public static final String TIPO_CONVOCATORIA_LIBROS = "11";

    /** The Constant TIPO_FORMULARIO_COMPLETO. */
    public static final String TIPO_FORMULARIO_COMPLETO = "FC";

    /** The Constant PERMANENTE. */
    public static final String PERMANENTE = "Y";

    /** The Constant FICHA_MINIMA. */
    public static final Long FICHA_MINIMA = 11L;
    public static final Long CONV_LAB_2024 = 682L;
    public static final Long CONV_PROY_2022_4 = 689L;
    public static final Long CONV_ALIANZ_2022_4 = 687L;
    public static final Long CONV_EXCELENCIA_2022_4 = 692L;
    public static final Long CONV_REDES_2022_24 = 697L;

    /**
     * Representa el estado actual de la convocatoria estos están: Activa,
     * Inactiva, Cancelado,.
     */
    private EstadoConvocatoria estadoConvocatoria;

    /** The titulo. */
    private String titulo;

    /** The ano. */
    private String ano;

    /** The dependencia. */
    private Dependencia dependencia;

    /** The tipo convocatoria. */
    private String tipoConvocatoria; // ES IGUAL A ECP SI ES DE EDUCACIÓN
                                     // CONTINUA

    /** The es permanente. */
    private String esPermanente;

    /** The es corte. */
    private String esCorte;

    /** The tipo proyecto. */
    private String tipoProyecto;

    /** The informacion. */
    private String informacion;

    /** The elegible asesor. */
    boolean elegibleAsesor = true;

    /** The categoria. */
    private Long categoria;

    /** The vinculo. */
    private String vinculo;

    /** The tipo formulario informes. */
    private String tipoFormularioInformes;

    /** The informacion adicional registro. */
    private String informacionAdicionalRegistro;

    /** The informe coordinador. */
    private String informeCoordinador;

    /** The informe coordinador. */
    private String informeSedeMovilidad;

    /** The informe coordinador. */
    private boolean mostrarPropiedadIntelectual;

    private List<Convocatoria> ListaConvocatorias;
    
    private Boolean tieneOficioElegibles;
    
    private String textoOficioElegibles;
    
    private Date fechaOficioElegibles;
    
    private String idPersonaOficioElegibles;
    
    private String tipoIdPersonaOficioElegibles;
    
    private Boolean tieneOficioAprobados;
    
    private String textoOficioAprobados;
    
    private Date fechaOficioAprobados;
    
    private String idPersonaOficioAprobados;
    
    private String tipoIdPersonaOficioAprobados;
    
	private Boolean mostrarVigenciaAprobados;
	
	private Boolean mostrarFirmaAprobados;
	
	private String responsableOficioAprobados;
	
	private String cargoResponsableOficioAprobados;
	private String numeroOficioAprobados;
	
	private Dependencia dependenciaGeneradoraOficioAprobados;
	
	private Date fechaGeneracionOficioAprobados;
	
	private Long vigenciaAprobados;
	
	private Long numeroProyectosPorProfesor;
	
	private Long numeroMovilidadesPorEstudiante;
	
	private boolean esConvocatoriaLaboratorios;
	private boolean esConvocatoriaLaboratorios2024;

	private boolean esConvocatoriaEditorial;
	private boolean esConvocatoriaProyectos2022_4;
	private boolean esConvocatoriaAlianzas2022_4;
	private boolean esConvocatoriaExcelencia2022_4;
	private boolean esConvocatoriaRedes2022_4;
	
    /**
     * Checks if is elegible asesor.
     *
     * @return true, if is elegible asesor
     */
    public boolean isElegibleAsesor() {
        return elegibleAsesor;
    }

    /**
     * Sets the elegible asesor.
     *
     * @param elegibleAsesor
     *            the new elegible asesor
     */
    public void setElegibleAsesor(boolean elegibleAsesor) {
        this.elegibleAsesor = elegibleAsesor;
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.Modalidad#getId()
     */
    public Long getId() {
        return id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.Modalidad#setId(java.lang.Long)
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the estado convocatoria.
     *
     * @return the estado convocatoria
     */
    public EstadoConvocatoria getEstadoConvocatoria() {
        return estadoConvocatoria;
    }

    /**
     * Sets the estado convocatoria.
     *
     * @param estadoConvocatoria
     *            the new estado convocatoria
     */
    public void setEstadoConvocatoria(EstadoConvocatoria estadoConvocatoria) {
        this.estadoConvocatoria = estadoConvocatoria;
    }

    /**
     * Gets the titulo.
     *
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Sets the titulo.
     *
     * @param titulo
     *            the new titulo
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Gets the ano.
     *
     * @return the ano
     */
    public String getAno() {
        return ano;
    }

    /**
     * Sets the ano.
     *
     * @param a
     *            the new ano
     */
    public void setAno(String a) {
        ano = a;
    }

    /**
     * Sets the dependencia.
     *
     * @param dependencia
     *            the new dependencia
     */
    public void setDependencia(Dependencia dependencia) {
        this.dependencia = dependencia;
    }

    /**
     * Gets the dependencia.
     *
     * @return the dependencia
     */
    public Dependencia getDependencia() {
        return dependencia;
    }

    /**
     * Gets the tipo convocatoria.
     *
     * @return the tipo convocatoria
     */
    public String getTipoConvocatoria() {
        return tipoConvocatoria;
    }

    /**
     * Sets the tipo convocatoria.
     *
     * @param tipoConvocatoria
     *            the new tipo convocatoria
     */
    public void setTipoConvocatoria(String tipoConvocatoria) {
        this.tipoConvocatoria = tipoConvocatoria;
    }

    /**
     * Sets the es permanente.
     *
     * @param esPermanente
     *            the new es permanente
     */
    public void setEsPermanente(String esPermanente) {
        this.esPermanente = esPermanente;
    }

    /**
     * Gets the es permanente.
     *
     * @return the es permanente
     */
    public String getEsPermanente() {
        return esPermanente;
    }

    /**
     * Sets the informacion.
     *
     * @param informacion
     *            the new informacion
     */
    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    /**
     * Gets the informacion.
     *
     * @return the informacion
     */
    public String getInformacion() {
        return informacion;
    }

    /**
     * Sets the es corte.
     *
     * @param esCorte
     *            the new es corte
     */
    public void setEsCorte(String esCorte) {
        this.esCorte = esCorte;
    }

    /**
     * Gets the es corte.
     *
     * @return the es corte
     */
    public String getEsCorte() {
        return esCorte;
    }

    /**
     * Gets the tipo proyecto.
     *
     * @return the tipo proyecto
     */
    public String getTipoProyecto() {
        return tipoProyecto;
    }

    /**
     * Sets the tipo proyecto.
     *
     * @param tipoProyecto
     *            the new tipo proyecto
     */
    public void setTipoProyecto(String tipoProyecto) {
        this.tipoProyecto = tipoProyecto;
    }

    /**
     * Gets the categoria.
     *
     * @return the categoria
     */
    public Long getCategoria() {
        return categoria;
    }

    /**
     * Sets the categoria.
     *
     * @param categoria
     *            the new categoria
     */
    public void setCategoria(Long categoria) {
        this.categoria = categoria;
    }

    /**
     * Gets the vinculo.
     *
     * @return the vinculo
     */
    public String getVinculo() {
        return vinculo;
    }

    /**
     * Sets the vinculo.
     *
     * @param vinculo
     *            the new vinculo
     */
    public void setVinculo(String vinculo) {
        this.vinculo = vinculo;
    }

    /**
     * Gets the tipo formulario informes.
     *
     * @return the tipo formulario informes
     */
    public String getTipoFormularioInformes() {
        return tipoFormularioInformes;
    }

    /**
     * Sets the tipo formulario informes.
     *
     * @param tipoFormularioInformes
     *            the new tipo formulario informes
     */
    public void setTipoFormularioInformes(String tipoFormularioInformes) {
        this.tipoFormularioInformes = tipoFormularioInformes;
    }

    /**
     * Gets the informacion adicional registro.
     *
     * @return the informacion adicional registro
     */
    public String getInformacionAdicionalRegistro() {
        return informacionAdicionalRegistro;
    }

    /**
     * Sets the informacion adicional registro.
     *
     * @param informacionAdiconalRegistro
     *            the new informacion adicional registro
     */
    public void setInformacionAdicionalRegistro(String informacionAdiconalRegistro) {
        this.informacionAdicionalRegistro = informacionAdiconalRegistro;
    }

    /**
     * Gets the informe coordinador.
     *
     * @return the informe coordinador
     */
    public String getInformeCoordinador() {
        return informeCoordinador;
    }

    /**
     * Sets the informe coordinador.
     *
     * @param informeCoordinador
     *            the new informe coordinador
     */
    public void setInformeCoordinador(String informeCoordinador) {
        this.informeCoordinador = informeCoordinador;
    }

    /**
     * @return the informeSedeMovilidad
     */
    public String getInformeSedeMovilidad() {
        return informeSedeMovilidad;
    }

    /**
     * @param informeSedeMovilidad
     *            the informeSedeMovilidad to set
     */
    public void setInformeSedeMovilidad(String informeSedeMovilidad) {
        this.informeSedeMovilidad = informeSedeMovilidad;
    }

    /**
     * @return the listaConvocatorias
     */
    public List<Convocatoria> getListaConvocatorias() {
        return ListaConvocatorias;
    }

    /**
     * @param listaConvocatorias
     *            the listaConvocatorias to set
     */
    public void setListaConvocatorias(List<Convocatoria> listaConvocatorias) {
        ListaConvocatorias = listaConvocatorias;
    }

    /**
     * @return the mostrarPropiedadIntelectual
     */
    public boolean isMostrarPropiedadIntelectual() {
        return mostrarPropiedadIntelectual;
    }

    /**
     * @param mostrarPropiedadIntelectual the mostrarPropiedadIntelectual to set
     */
    public void setMostrarPropiedadIntelectual(boolean mostrarPropiedadIntelectual) {
        this.mostrarPropiedadIntelectual = mostrarPropiedadIntelectual;
    }

	/**
	 * @return the textoOficioElegibles
	 */
	public String getTextoOficioElegibles()
	{
		return textoOficioElegibles;
	}

	/**
	 * @param textoOficioElegibles the textoOficioElegibles to set
	 */
	public void setTextoOficioElegibles(String textoOficioElegibles)
	{
		this.textoOficioElegibles = textoOficioElegibles;
	}

	/**
	 * @return the fechaOficioElegibles
	 */
	public Date getFechaOficioElegibles()
	{
		return fechaOficioElegibles;
	}

	/**
	 * @param fechaOficioElegibles the fechaOficioElegibles to set
	 */
	public void setFechaOficioElegibles(Date fechaOficioElegibles)
	{
		this.fechaOficioElegibles = fechaOficioElegibles;
	}

	/**
	 * @return the idPersonaOficioElegibles
	 */
	public String getIdPersonaOficioElegibles()
	{
		return idPersonaOficioElegibles;
	}

	/**
	 * @param idPersonaOficioElegibles the idPersonaOficioElegibles to set
	 */
	public void setIdPersonaOficioElegibles(String idPersonaOficioElegibles)
	{
		this.idPersonaOficioElegibles = idPersonaOficioElegibles;
	}

	/**
	 * @return the tipoIdPersonaOficioElegibles
	 */
	public String getTipoIdPersonaOficioElegibles()
	{
		return tipoIdPersonaOficioElegibles;
	}

	/**
	 * @param tipoIdPersonaOficioElegibles the tipoIdPersonaOficioElegibles to set
	 */
	public void setTipoIdPersonaOficioElegibles(String tipoIdPersonaOficioElegibles)
	{
		this.tipoIdPersonaOficioElegibles = tipoIdPersonaOficioElegibles;
	}

	/**
	 * @return the tieneOficioElegibles
	 */
	public Boolean getTieneOficioElegibles()
	{
		return tieneOficioElegibles;
	}

	/**
	 * @param tieneOficioElegibles the tieneOficioElegibles to set
	 */
	public void setTieneOficioElegibles(Boolean tieneOficioElegibles)
	{
		this.tieneOficioElegibles = tieneOficioElegibles;
	}

	/**
	 * @return the tieneOficioAprobados
	 */
	public Boolean getTieneOficioAprobados()
	{
		return tieneOficioAprobados;
	}

	/**
	 * @param tieneOficioAprobados the tieneOficioAprobados to set
	 */
	public void setTieneOficioAprobados(Boolean tieneOficioAprobados)
	{
		this.tieneOficioAprobados = tieneOficioAprobados;
	}

	/**
	 * @return the textoOficioAprobados
	 */
	public String getTextoOficioAprobados()
	{
		return textoOficioAprobados;
	}

	/**
	 * @param textoOficioAprobados the textoOficioAprobados to set
	 */
	public void setTextoOficioAprobados(String textoOficioAprobados)
	{
		this.textoOficioAprobados = textoOficioAprobados;
	}

	/**
	 * @return the fechaOficioAprobados
	 */
	public Date getFechaOficioAprobados()
	{
		return fechaOficioAprobados;
	}

	/**
	 * @param fechaOficioAprobados the fechaOficioAprobados to set
	 */
	public void setFechaOficioAprobados(Date fechaOficioAprobados)
	{
		this.fechaOficioAprobados = fechaOficioAprobados;
	}

	/**
	 * @return the idPersonaOficioAprobados
	 */
	public String getIdPersonaOficioAprobados()
	{
		return idPersonaOficioAprobados;
	}

	/**
	 * @param idPersonaOficioAprobados the idPersonaOficioAprobados to set
	 */
	public void setIdPersonaOficioAprobados(String idPersonaOficioAprobados)
	{
		this.idPersonaOficioAprobados = idPersonaOficioAprobados;
	}

	/**
	 * @return the tipoIdPersonaOficioAprobados
	 */
	public String getTipoIdPersonaOficioAprobados()
	{
		return tipoIdPersonaOficioAprobados;
	}

	/**
	 * @param tipoIdPersonaOficioAprobados the tipoIdPersonaOficioAprobados to set
	 */
	public void setTipoIdPersonaOficioAprobados(String tipoIdPersonaOficioAprobados)
	{
		this.tipoIdPersonaOficioAprobados = tipoIdPersonaOficioAprobados;
	}

	/**
	 * @return the mostrarVigenciaAprobados
	 */
	public Boolean getMostrarVigenciaAprobados()
	{
		return mostrarVigenciaAprobados;
	}

	/**
	 * @param mostrarVigenciaAprobados the mostrarVigenciaAprobados to set
	 */
	public void setMostrarVigenciaAprobados(Boolean mostrarVigenciaAprobados)
	{
		this.mostrarVigenciaAprobados = mostrarVigenciaAprobados;
	}

	/**
	 * @return the mostrarFirmaAprobados
	 */
	public Boolean getMostrarFirmaAprobados()
	{
		return mostrarFirmaAprobados;
	}

	/**
	 * @param mostrarFirmaAprobados the mostrarFirmaAprobados to set
	 */
	public void setMostrarFirmaAprobados(Boolean mostrarFirmaAprobados)
	{
		this.mostrarFirmaAprobados = mostrarFirmaAprobados;
	}

	/**
	 * @return the responsableOficioAprobados
	 */
	public String getResponsableOficioAprobados()
	{
		return responsableOficioAprobados;
	}

	/**
	 * @param responsableOficioAprobados the responsableOficioAprobados to set
	 */
	public void setResponsableOficioAprobados(String responsableOficioAprobados)
	{
		this.responsableOficioAprobados = responsableOficioAprobados;
	}

	/**
	 * @return the cargoResponsableOficioAprobados
	 */
	public String getCargoResponsableOficioAprobados()
	{
		return cargoResponsableOficioAprobados;
	}

	/**
	 * @param cargoResponsableOficioAprobados the cargoResponsableOficioAprobados to set
	 */
	public void setCargoResponsableOficioAprobados(String cargoResponsableOficioAprobados)
	{
		this.cargoResponsableOficioAprobados = cargoResponsableOficioAprobados;
	}

	/**
	 * @return the numeroOficioAprobados
	 */
	public String getNumeroOficioAprobados()
	{
		return numeroOficioAprobados;
	}

	/**
	 * @param numeroOficioAprobados the numeroOficioAprobados to set
	 */
	public void setNumeroOficioAprobados(String numeroOficioAprobados)
	{
		this.numeroOficioAprobados = numeroOficioAprobados;
	}

	/**
	 * @return the dependenciaGeneradoraOficioAprobados
	 */
	public Dependencia getDependenciaGeneradoraOficioAprobados()
	{
		return dependenciaGeneradoraOficioAprobados;
	}

	/**
	 * @param dependenciaGeneradoraOficioAprobados the dependenciaGeneradoraOficioAprobados to set
	 */
	public void setDependenciaGeneradoraOficioAprobados(Dependencia dependenciaGeneradoraOficioAprobados)
	{
		this.dependenciaGeneradoraOficioAprobados = dependenciaGeneradoraOficioAprobados;
	}

	/**
	 * @return the fechaGeneracionOficioAprobados
	 */
	public Date getFechaGeneracionOficioAprobados()
	{
		return fechaGeneracionOficioAprobados;
	}

	/**
	 * @param fechaGeneracionOficioAprobados the fechaGeneracionOficioAprobados to set
	 */
	public void setFechaGeneracionOficioAprobados(Date fechaGeneracionOficioAprobados)
	{
		this.fechaGeneracionOficioAprobados = fechaGeneracionOficioAprobados;
	}

	/**
	 * @return the vigenciaAprobados
	 */
	public Long getVigenciaAprobados()
	{
		return vigenciaAprobados;
	}

	/**
	 * @param vigenciaAprobados the vigenciaAprobados to set
	 */
	public void setVigenciaAprobados(Long vigenciaAprobados)
	{
		this.vigenciaAprobados = vigenciaAprobados;
	}

	public Long getNumeroProyectosPorProfesor() {
		return numeroProyectosPorProfesor;
	}

	public void setNumeroProyectosPorProfesor(Long numeroProyectosPorProfesor) {
		this.numeroProyectosPorProfesor = numeroProyectosPorProfesor;
	}

	public boolean getEsConvocatoriaLaboratorios() {
		if(this.tipoConvocatoria.equals(TIPO_CONVOCATORIA_LABORATORIOS)) {
			this.esConvocatoriaLaboratorios = true;
		}else {
		this.esConvocatoriaLaboratorios = false;}
		return esConvocatoriaLaboratorios;
	}
	
	public boolean getEsConvocatoriaLaboratorios2024() {
		if(this.id.equals(CONV_LAB_2024) || this.id.equals(713L) || this.id.equals(714L)) {
			this.esConvocatoriaLaboratorios2024 = true;
		}else {
		this.esConvocatoriaLaboratorios2024 = false;}
		return esConvocatoriaLaboratorios2024;
	}
	
	public boolean getEsConvocatoriaEnfermeriaAlianzas2023() {
		if(this.id.equals(723L)) {
			return true;
		}
		
		return false;
	}
	
	public boolean getEsConvocatoriaLaboratorios2023() {
		if(this.id.equals(713L) || this.id.equals(714L)) {
			return true;
		}
		return false;

	}

	public void setEsConvocatoriaLaboratorios2024(boolean esConvocatoriaLaboratorios2024) {
		this.esConvocatoriaLaboratorios2024 = esConvocatoriaLaboratorios2024;
	}

	public boolean isEsConvocatoriaEditorial() {
		if(this.tipoConvocatoria!=null && this.tipoConvocatoria.equals(TIPO_CONVOCATORIA_LIBROS)) {
			this.esConvocatoriaEditorial = true;
		}else {
		this.esConvocatoriaEditorial = false;}
		return esConvocatoriaEditorial;
	}

	public void setEsConvocatoriaEditorial(boolean esConvocatoriaEditorial) {
		this.esConvocatoriaEditorial = esConvocatoriaEditorial;
	}

	public boolean getEsConvocatoriaProyectos2022_4() {
		if(this.id.equals(CONV_PROY_2022_4)) {
			esConvocatoriaProyectos2022_4 = true;
		}else {
			esConvocatoriaProyectos2022_4 = false;
		}
		return esConvocatoriaProyectos2022_4;
	}

	public void setEsConvocatoriaProyectos2022_4(boolean esConvocatoriaProyectos2022_4) {
		this.esConvocatoriaProyectos2022_4 = esConvocatoriaProyectos2022_4;
	}

	public boolean getEsConvocatoriaAlianzas2022_4() {
		if(this.id.equals(CONV_ALIANZ_2022_4)) {
			esConvocatoriaAlianzas2022_4=true;
		}else {
			esConvocatoriaAlianzas2022_4=false;
		}
		return esConvocatoriaAlianzas2022_4;
	}

	public void setEsConvocatoriaAlianzas2022_4(boolean esConvocatoriaAlianzas2022_4) {
		this.esConvocatoriaAlianzas2022_4 = esConvocatoriaAlianzas2022_4;
	}

	public boolean getEsConvocatoriaExcelencia2022_4() {
		if(this.id.equals(CONV_EXCELENCIA_2022_4)) {
			esConvocatoriaExcelencia2022_4=true;
		}else {
			esConvocatoriaExcelencia2022_4=false;
		}
		return esConvocatoriaExcelencia2022_4;
	}

	public void setEsConvocatoriaExcelencia2022_4(boolean esConvocatoriaExcelencia2022_4) {
		this.esConvocatoriaExcelencia2022_4 = esConvocatoriaExcelencia2022_4;
	}
	
	public boolean getEsConvocatoriaRedes2022_4() {
		if(this.id.equals(CONV_REDES_2022_24)) {
			esConvocatoriaRedes2022_4=true;
		}else {
			esConvocatoriaRedes2022_4=false;
		}
		return esConvocatoriaRedes2022_4;
	}

	public void setEsConvocatoriaLaboratorios(boolean esConvocatoriaLaboratorios) {
		this.esConvocatoriaLaboratorios = esConvocatoriaLaboratorios;
	}

	public void setEsConvocatoriaRedes2022_4(boolean esConvocatoriaRedes2022_4) {
		this.esConvocatoriaRedes2022_4 = esConvocatoriaRedes2022_4;
	}

	public Long getNumeroMovilidadesPorEstudiante() {
		return numeroMovilidadesPorEstudiante;
	}

	public void setNumeroMovilidadesPorEstudiante(Long numeroMovilidadesPorEstudiante) {
		this.numeroMovilidadesPorEstudiante = numeroMovilidadesPorEstudiante;
	}
	
}