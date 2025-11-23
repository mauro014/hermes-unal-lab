package co.edu.unal.hermes.modelo;

import java.util.HashSet;
import java.util.Set;

import co.edu.unal.hermes.modelo.anotaciones.FieldProperties;
import co.edu.unal.hermes.modelo.anotaciones.NotNull;

public class ConvocatoriaAlianzas {
	private static final long serialVersionUID = 1L;

	public static String REF = "ConvocatoriaAlianzas";
	public static String PROP_COA_FECHA_CONV_EXTERNA = "coaFechaConvExterna";
	public static String PROP_COA_NIT = "coaNit";
	public static String PROP_COA_ID_LIDER_ENTIDAD = "coaIdLiderEntidad";
	public static String PROP_COA_ACTIVIDADES = "coaActividades";
	public static String PROP_COA_PRODUCTOS_ESPERADOS = "coaProductosEsperados";
	public static String PROP_COA_GRUPOS_UN_ALIANZA = "coaGruposUnAlianza";
	public static String PROP_COA_ID_REPRESENTANTE_LEGAL = "coaIdRepresentanteLegal";
	public static String PROP_COA_OBJETIVO = "coaObjetivo";
	public static String PROP_COA_RESULTADOS = "coaResultados";
	public static String PROP_GRUPO_RESPONSABLE = "grupoResponsable";
	public static String PROP_COA_GRUPO_PARTICIPANTE = "coaGrupoParticipante";
	public static String PROP_COA_CONTRIBUCION = "coaContribucion";
	public static String PROP_COA_MOD_ID = "coaModId";
	public static String PROP_COA_NOMBRE_LIDER_ENTIDAD = "coaNombreLiderEntidad";
	public static String PROP_COA_TRAYECTORIA_GRUPO = "coaTrayectoriaGrupo";
	public static String PROP_COA_REPRESENTANTE_LEGAL = "coaRepresentanteLegal";
	public static String PROP_COA_NOMBRE_PROYECTO = "coaNombreProyecto";
	public static String PROP_COA_CONVOCATORIA_EXTERNA = "coaConvocatoriaExterna";
	public static String PROP_COA_LINEAS_INVESTIGACION = "coaLineasInvestigacion";
	public static String PROP_COA_TRAYECTORIA_ENTIDAD = "coaTrayectoriaEntidad";
	public static String PROP_INVESTIGADOR = "investigador";
	public static String PROP_COA_FECHA_SOLICITUD = "coaFechaSolicitud";
	public static String PROP_ID = "id";
	public static String PROP_COA_PROPUESTA_PRESUPUESTAL = "coaPropuestaPresupuestal";
	public static String PROP_COA_CONTRAPARTIDA = "coaContrapartida";
	public static String PROP_COA_ENTIDAD_ALIADA = "coaEntidadAliada";
	public static String PROP_COA_PLAN_FORMACION = "coaPlanFormacion";
	public static String PROP_COA_DURACION_PROPUESTA = "coaDuracionPropuesta";
	public static String PROP_COA_ETAPA = "coaEtapa";
	public static String PROP_COA_ESTADO = "coaEstado";
	public static String PROP_FORMACION_EST = "coaFormacionEstudiantes";
	public static String PROP_PRODUCCION_ACADEMICA = "coaProduccionAcademica";
	public static String PROP_DOCENTES_PARTICIPANTES = "coaDocentesParticipantes";
	public static String PROP_PARTICIPACION_EVENTOS = "coaParticipacionEventos";
	public static String PROP_AGENDAS_CONOCIMIENTO = "coaAgendasConocimiento";

	// constructors
	public ConvocatoriaAlianzas() {
		this(null);
	}

	public ConvocatoriaAlianzas(Long id) {
		this(new Investigador(), new Grupo(), id);
	}

	/**
	 * Constructor for required fields
	 */
	public ConvocatoriaAlianzas(co.edu.unal.hermes.modelo.Investigador investigador, co.edu.unal.hermes.modelo.Grupo grupoResponsable, Long id) {
		super();
		this.setInvestigador(investigador);
		this.setGrupoResponsable(grupoResponsable);
		this.setId(id);
		initialize();
	}

	protected void initialize() {
		archivos = new HashSet<ArchivoConvocatoria>();
	}

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	@NotNull
	private Long id;

	// fields
	// @NotNull
	private String coaTrayectoriaGrupo;
	private String coaGruposUnAlianza;
	private String coaFormacionEstudiantes;
	private String coaProduccionAcademica;
	private String coaParticipacionEventos;
	private Integer coaDuracionPropuesta;
	// @NotNull
	private String coaEntidadAliada;
	@NotNull
	private String coaNombreProyecto;
	@NotNull
	@FieldProperties(amountWords = 100)
	private String coaObjetivo;
	@NotNull
	@FieldProperties(amountWords = 100)
	private String coaContribucion;
	@NotNull
	@FieldProperties(amountWords = 100)
	private String coaLineasInvestigacion;
	private String coaConvocatoriaExterna;
	// @NotNull
	// @FieldProperties(amountWords = 500)
	private String coaResultados;
	// @NotNull
	private String coaActividades;
	// @NotNull
	private String coaProductosEsperados;
	// @NotNull
	private String coaPropuestaPresupuestal;
	private String coaContrapartida;
	// @NotNull
	private String coaPlanFormacion;
	// @NotNull
	private String coaModId;
	private java.util.Date coaFechaConvExterna;
	// @NotNull
	private java.util.Date coaFechaSolicitud;
	private String coaDocentesParticipantes;
	private String coaAgendasConocimiento;

	private Integer coaEtapa;
	private String coaEstado;

	// many to one
	private co.edu.unal.hermes.modelo.Grupo grupoResponsable;
	private co.edu.unal.hermes.modelo.Investigador investigador;
	private Set<ArchivoConvocatoria> archivos;

	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id generator-class="sequence" column="COA_ID"
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * 
	 * @param id
	 *            the new ID
	 */
	public void setId(Long id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}

	/**
	 * Return the value associated with the column: COA_TRAYECTORIA_GRUPO
	 */
	public String getCoaTrayectoriaGrupo() {
		return coaTrayectoriaGrupo;
	}

	/**
	 * Set the value related to the column: COA_TRAYECTORIA_GRUPO
	 * 
	 * @param coaTrayectoriaGrupo
	 *            the COA_TRAYECTORIA_GRUPO value
	 */
	public void setCoaTrayectoriaGrupo(String coaTrayectoriaGrupo) {
		this.coaTrayectoriaGrupo = coaTrayectoriaGrupo;
	}

	/**
	 * Return the value associated with the column: COA_GRUPOS_UN_ALIANZA
	 */
	public String getCoaGruposUnAlianza() {
		return coaGruposUnAlianza;
	}

	/**
	 * Set the value related to the column: COA_GRUPOS_UN_ALIANZA
	 * 
	 * @param coaGruposUnAlianza
	 *            the COA_GRUPOS_UN_ALIANZA value
	 */
	public void setCoaGruposUnAlianza(String coaGruposUnAlianza) {
		this.coaGruposUnAlianza = coaGruposUnAlianza;
	}

	/**
	 * Return the value associated with the column: COA_ENTIDAD_ALIADA
	 */
	public String getCoaEntidadAliada() {
		return coaEntidadAliada;
	}

	/**
	 * Set the value related to the column: COA_ENTIDAD_ALIADA
	 * 
	 * @param coaEntidadAliada
	 *            the COA_ENTIDAD_ALIADA value
	 */
	public void setCoaEntidadAliada(String coaEntidadAliada) {
		this.coaEntidadAliada = coaEntidadAliada;
	}

	/**
	 * Return the value associated with the column: COA_NOMBRE_PROYECTO
	 */
	public String getCoaNombreProyecto() {
		return coaNombreProyecto;
	}

	/**
	 * Set the value related to the column: COA_NOMBRE_PROYECTO
	 * 
	 * @param coaNombreProyecto
	 *            the COA_NOMBRE_PROYECTO value
	 */
	public void setCoaNombreProyecto(String coaNombreProyecto) {
		this.coaNombreProyecto = coaNombreProyecto;
	}

	/**
	 * Return the value associated with the column: COA_OBJETIVO
	 */
	public String getCoaObjetivo() {
		return coaObjetivo;
	}

	/**
	 * Set the value related to the column: COA_OBJETIVO
	 * 
	 * @param coaObjetivo
	 *            the COA_OBJETIVO value
	 */
	public void setCoaObjetivo(String coaObjetivo) {
		this.coaObjetivo = coaObjetivo;
	}

	/**
	 * Return the value associated with the column: COA_CONTRIBUCION
	 */
	public String getCoaContribucion() {
		return coaContribucion;
	}

	/**
	 * Set the value related to the column: COA_CONTRIBUCION
	 * 
	 * @param coaContribucion
	 *            the COA_CONTRIBUCION value
	 */
	public void setCoaContribucion(String coaContribucion) {
		this.coaContribucion = coaContribucion;
	}

	/**
	 * Return the value associated with the column: COA_LINEAS_INVESTIGACION
	 */
	public String getCoaLineasInvestigacion() {
		return coaLineasInvestigacion;
	}

	/**
	 * Set the value related to the column: COA_LINEAS_INVESTIGACION
	 * 
	 * @param coaLineasInvestigacion
	 *            the COA_LINEAS_INVESTIGACION value
	 */
	public void setCoaLineasInvestigacion(String coaLineasInvestigacion) {
		this.coaLineasInvestigacion = coaLineasInvestigacion;
	}

	/**
	 * Return the value associated with the column: COA_CONVOCATORIA_EXTERNA
	 */
	public String getCoaConvocatoriaExterna() {
		return coaConvocatoriaExterna;
	}

	/**
	 * Set the value related to the column: COA_CONVOCATORIA_EXTERNA
	 * 
	 * @param coaConvocatoriaExterna
	 *            the COA_CONVOCATORIA_EXTERNA value
	 */
	public void setCoaConvocatoriaExterna(String coaConvocatoriaExterna) {
		this.coaConvocatoriaExterna = coaConvocatoriaExterna;
	}

	/**
	 * Return the value associated with the column: COA_RESULTADOS
	 */
	public String getCoaResultados() {
		return coaResultados;
	}

	/**
	 * Set the value related to the column: COA_RESULTADOS
	 * 
	 * @param coaResultados
	 *            the COA_RESULTADOS value
	 */
	public void setCoaResultados(String coaResultados) {
		this.coaResultados = coaResultados;
	}

	/**
	 * Return the value associated with the column: COA_ACTIVIDADES
	 */
	public String getCoaActividades() {
		return coaActividades;
	}

	/**
	 * Set the value related to the column: COA_ACTIVIDADES
	 * 
	 * @param coaActividades
	 *            the COA_ACTIVIDADES value
	 */
	public void setCoaActividades(String coaActividades) {
		this.coaActividades = coaActividades;
	}

	/**
	 * Return the value associated with the column: COA_PRODUCTOS_ESPERADOS
	 */
	public String getCoaProductosEsperados() {
		return coaProductosEsperados;
	}

	/**
	 * Set the value related to the column: COA_PRODUCTOS_ESPERADOS
	 * 
	 * @param coaProductosEsperados
	 *            the COA_PRODUCTOS_ESPERADOS value
	 */
	public void setCoaProductosEsperados(String coaProductosEsperados) {
		this.coaProductosEsperados = coaProductosEsperados;
	}

	/**
	 * Return the value associated with the column: COA_PROPUESTA_PRESUPUESTAL
	 */
	public String getCoaPropuestaPresupuestal() {
		return coaPropuestaPresupuestal;
	}

	/**
	 * Set the value related to the column: COA_PROPUESTA_PRESUPUESTAL
	 * 
	 * @param coaPropuestaPresupuestal
	 *            the COA_PROPUESTA_PRESUPUESTAL value
	 */
	public void setCoaPropuestaPresupuestal(String coaPropuestaPresupuestal) {
		this.coaPropuestaPresupuestal = coaPropuestaPresupuestal;
	}

	/**
	 * Return the value associated with the column: COA_MOD_ID
	 */
	public String getCoaModId() {
		return coaModId;
	}

	/**
	 * Set the value related to the column: COA_MOD_ID
	 * 
	 * @param coaModId
	 *            the COA_MOD_ID value
	 */
	public void setCoaModId(String coaModId) {
		this.coaModId = coaModId;
	}

	/**
	 * Return the value associated with the column: COA_FECHA_CONV_EXTERNA
	 */
	public java.util.Date getCoaFechaConvExterna() {
		return coaFechaConvExterna;
	}

	/**
	 * Set the value related to the column: COA_FECHA_CONV_EXTERNA
	 * 
	 * @param coaFechaConvExterna
	 *            the COA_FECHA_CONV_EXTERNA value
	 */
	public void setCoaFechaConvExterna(java.util.Date coaFechaConvExterna) {
		this.coaFechaConvExterna = coaFechaConvExterna;
	}

	/**
	 * Return the value associated with the column: COA_FECHA_SOLICITUD
	 */
	public java.util.Date getCoaFechaSolicitud() {
		return coaFechaSolicitud;
	}

	/**
	 * Set the value related to the column: COA_FECHA_SOLICITUD
	 * 
	 * @param coaFechaSolicitud
	 *            the COA_FECHA_SOLICITUD value
	 */
	public void setCoaFechaSolicitud(java.util.Date coaFechaSolicitud) {
		this.coaFechaSolicitud = coaFechaSolicitud;
	}

	/**
	 * Return the value associated with the column: GRU_ID
	 */
	public co.edu.unal.hermes.modelo.Grupo getGrupoResponsable() {
		return grupoResponsable;
	}

	/**
	 * Set the value related to the column: GRU_ID
	 * 
	 * @param grupoResponsable
	 *            the GRU_ID value
	 */
	public void setGrupoResponsable(co.edu.unal.hermes.modelo.Grupo grupoResponsable) {
		this.grupoResponsable = grupoResponsable;
	}

	/**
	 * Return the value associated with the column: TDO_ID
	 */
	public co.edu.unal.hermes.modelo.Investigador getInvestigador() {
		return investigador;
	}

	/**
	 * Set the value related to the column: TDO_ID
	 * 
	 * @param investigador
	 *            the TDO_ID value
	 */
	public void setInvestigador(co.edu.unal.hermes.modelo.Investigador investigador) {
		this.investigador = investigador;
	}

	public Set<ArchivoConvocatoria> getArchivos() {
		return archivos;
	}

	public void setArchivos(Set<ArchivoConvocatoria> archivos) {
		this.archivos = archivos;
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof co.edu.unal.hermes.modelo.ConvocatoriaAlianzas))
			return false;
		else {
			co.edu.unal.hermes.modelo.ConvocatoriaAlianzas convocatoriaAlianzas = (co.edu.unal.hermes.modelo.ConvocatoriaAlianzas) obj;
			return (this.getId() == convocatoriaAlianzas.getId());
		}
	}

	@Override
	public int hashCode() {
		if (Integer.MIN_VALUE == this.hashCode) {
			return (int) (long) this.getId();
		}
		return this.hashCode;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	public String getCoaContrapartida() {
		return coaContrapartida;
	}

	public void setCoaContrapartida(String coaContrapartida) {
		this.coaContrapartida = coaContrapartida;
	}

	public String getCoaPlanFormacion() {
		return coaPlanFormacion;
	}

	public void setCoaPlanFormacion(String coaPlanFormacion) {
		this.coaPlanFormacion = coaPlanFormacion;
	}

	public Integer getCoaDuracionPropuesta() {
		return coaDuracionPropuesta;
	}

	public void setCoaDuracionPropuesta(Integer coaDuracionPropuesta) {
		this.coaDuracionPropuesta = coaDuracionPropuesta;
	}

	public Integer getCoaEtapa() {
		return coaEtapa;
	}

	public void setCoaEtapa(Integer coaEtapa) {
		this.coaEtapa = coaEtapa;
	}

	public String getCoaEstado() {
		return coaEstado;
	}

	public void setCoaEstado(String coaEstado) {
		this.coaEstado = coaEstado;
	}

	public String getCoaFormacionEstudiantes() {
		return coaFormacionEstudiantes;
	}

	public void setCoaFormacionEstudiantes(String coaFormacionEstudiantes) {
		this.coaFormacionEstudiantes = coaFormacionEstudiantes;
	}

	public String getCoaProduccionAcademica() {
		return coaProduccionAcademica;
	}

	public void setCoaProduccionAcademica(String coaProduccionAcademica) {
		this.coaProduccionAcademica = coaProduccionAcademica;
	}

	public String getCoaDocentesParticipantes() {
		return coaDocentesParticipantes;
	}

	public void setCoaDocentesParticipantes(String coaDocentesParticipantes) {
		this.coaDocentesParticipantes = coaDocentesParticipantes;
	}

	public String getCoaAgendasConocimiento() {
		return coaAgendasConocimiento;
	}

	public void setCoaAgendasConocimiento(String coaAgendasConocimiento) {
		this.coaAgendasConocimiento = coaAgendasConocimiento;
	}

	public String getCoaParticipacionEventos() {
		return coaParticipacionEventos;
	}

	public void setCoaParticipacionEventos(String coaParticipacionEventos) {
		this.coaParticipacionEventos = coaParticipacionEventos;
	}

}