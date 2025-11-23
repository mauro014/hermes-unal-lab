package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The Class TipoModalidad.
 */
public class TipoModalidad implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2049565500637444289L;

	/** The Constant CONVOCATORIA. */
	public static final String CONVOCATORIA = "C";
	
	/** The Constant JORNADA_DOCENTE. */
	public static  final String JORNADA_DOCENTE = "J";
	
	/** The Constant CONTRAPARTIDA. */
	public static  final String CONTRAPARTIDA = "T";
	
	/** The Constant SENA. */
	public static  final String SENA = "SE";
	
	/** The Constant REGISTRO. */
	public static  final String REGISTRO = "R";
	
	/** The Constant INTERINSTUCIONAL. */
	public static  final String INTERINSTUCIONAL = "I";
	
	/** The Constant ECP. */
	public static  final String ECP = "ECP";
	
	/** The Constant PERMISOMARCO. */
	public static  final String PERMISOMARCO = "PM";
	
	/** The Constant PERMISOMARCOASIGNATURA. */
	public static  final String PERMISOMARCOASIGNATURA = "PMA";
	
	/** The Constant BANCO_PROYECTOS. */
	public static  final String BANCO_PROYECTOS = "CBP";
	
	/** The Constant REGISTRO_PROYECTOS_LABORATORIOS. */
	public static  final String REGISTRO_PROYECTOS_LABORATORIOS = "RPL";
	
	public static  final String CONVOCATORIA_PROYECTOS_TESIS_POSGRADO = "CTP";
	
	/** The Constant REGISTRO_CONVOCATORIA_ESCUELA_INTERNACIONAL. */
	public static  final String REGISTRO_CONVOCATORIA_ESCUELA_INTERNACIONAL = "CEI";
	
	/** The Constant REGISTRO_CONVOCATORIA_EVENTOS. */
	public static  final String REGISTRO_CONVOCATORIA_EVENTOS = "CE";
	
	/** The Constant CONVOCATORIA_FICHA_MINIMA_HOME. */
	public static  final String CONVOCATORIA_FICHA_MINIMA_HOME = "FMH";
	
	/** The Constant CONVOCATORIA_JOVENES_INVESTIGADORES. */
	public static  final String CONVOCATORIA_JOVENES_INVESTIGADORES = "CJI";
	
	/** The Constant CONVOCATORIA_LIBROS. */
	public static  final String CONVOCATORIA_LIBROS = "CL";
	
	/** The Constant CONVOCATORIA_MOVILIDAD. */
	public static  final String CONVOCATORIA_MOVILIDAD = "CM";
	
	/** The Constant CONVOCATORIA_PURDUE. */
	public static  final String CONVOCATORIA_PURDUE = "CPU";
	
	/** The Constant CONVOCATORIA_SOLO_FICHA_MINIMA. */
	public static  final String CONVOCATORIA_SOLO_FICHA_MINIMA = "CSF";
	
	/** The Constant REGISTRO_EXTENSION_SOLIDARIA_INNOVACION_SOCIAL. */
	public static  final String EXTENSION_SOLIDARIA_INNOVACION_SOCIAL = "ESI";
	
	/** The Constant REGISTRO_SOLICITUD_ISBN. */
	public static  final String SOLICITUD_ISBN = "SIS";

	/** The id. */
	private String id;
	
	/** The nombre. */
	private String nombre;
	
	/** The codigo division investigacion. */
	private String estado;
	
	/** The tipos investigacion. */
	private Set<TipoInvestigacion> tiposInvestigacion;
	
	/** The formularios. */
	private Set<Formulario> formularios = new HashSet<Formulario>();

	/**
	 * Gets the tipos investigacion.
	 *
	 * @return the tipos investigacion
	 */
	public Set<TipoInvestigacion> getTiposInvestigacion() {
		return tiposInvestigacion;
	}

	/**
	 * Sets the tipos investigacion.
	 *
	 * @param tiposInvestigacion the new tipos investigacion
	 */
	public void setTiposInvestigacion(Set<TipoInvestigacion> tiposInvestigacion) {
		this.tiposInvestigacion = tiposInvestigacion;
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
	 * @param nombre the new nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the formularios.
	 *
	 * @return the formularios
	 */
	public Set<Formulario> getFormularios() {
		return formularios;
	}

	/**
	 * Sets the formularios.
	 *
	 * @param formularios the new formularios
	 */
	public void setFormularios(Set<Formulario> formularios) {
		this.formularios = formularios;
	}

	/**
	 * @return the estado
	 */
	public String getEstado()
	{
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado)
	{
		this.estado = estado;
	}
}
