/*
 * Created on 18-ago-2005
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;

/**
 * Maneja el equipo de trabaja que participan en cada proyecto.
 */
public class InvestigadorProyecto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3254645658286890664L;

    /** The Constant FUNCION_PARTICIPANTE_BASICO. */
    public static final String FUNCION_PARTICIPANTE_BASICO = "Participante";

    /** The principal. */
    public static String PRINCIPAL = "P";

    /** The coninvestigador. */
    public static String CONINVESTIGADOR = "C";
    
    /** The id. */
    private Long id;

    /** The investigador. */
    private Investigador investigador;

    /** The proyecto. */
    private Proyecto proyecto;

    /** The tipo. */
    private TipoInvestigador tipo;

    /** The dedicacion horas semana. */
    private double dedicacionHorasSemana;

    /** The total horas vinculacion. */
    private Double totalHorasVinculacion;

    /** The horas jornada docente. */
    private Integer horasJornadaDocente;

    /** The funcion. */
    private String funcion;

    /** The empresa. */
    private Empresa empresa;

    /** The programa estudiante. */
    private String programaEstudiante;

    /** The visible. */
    private String visible;

    /** The dependencia. */
    private Dependencia dependencia;

    /** The plan. */
    private PlanEstudios plan;

    /** The institucion. */
    private Institucion institucion;
    
    private FuenteFinanciacion institucionInvestigador;

    /** The valor pagar. */
    // lmom
    private Long valorPagar;

    /** The grupo. */
    private Grupo grupo;
    
    private Date fechaVinculacion;

    /**
     * Copiar datos.
     *
     * @param ipry
     *            the ipry
     */
    public void copiarDatos(InvestigadorProyecto ipry) {
        this.setDedicacionHorasSemana(ipry.getDedicacionHorasSemana());
        this.setFuncion(ipry.getFuncion());
        this.setHorasJornadaDocente(ipry.getHorasJornadaDocente());
        this.setInvestigador(ipry.getInvestigador());
        this.setProyecto(ipry.getProyecto());
        this.setTipo(ipry.getTipo());
        this.setTotalHorasVinculacion(ipry.getTotalHorasVinculacion());
        this.setValorPagar(ipry.getValorPagar());
        this.setEmpresa(ipry.getEmpresa());
        this.setValorPagar(ipry.getValorPagar());
    }
    
    public Estudiante convertirAEstudiante() {
    	Estudiante estudiante = new Estudiante();
		IdPersona idInvestigador = new IdPersona();
		idInvestigador.setDocumento(investigador.getId().getDocumento());
		idInvestigador.setTipoDocumento(investigador.getId().getTipoDocumento());
		estudiante.setId(idInvestigador);
		estudiante.setApellido1(investigador.getApellido1());
		estudiante.setApellido2(investigador.getApellido2());
		estudiante.setNombre1(investigador.getNombre1());
		estudiante.setNombre2(investigador.getNombre2());
		estudiante.setPlan(this.plan);

		return estudiante;
	}

    /**
     * Gets the dedicacion horas semana.
     *
     * @return the dedicacion horas semana
     */
    public double getDedicacionHorasSemana() {
        return dedicacionHorasSemana;
    }

    /**
     * Sets the dedicacion horas semana.
     *
     * @param dedicacionHorasSemana
     *            the new dedicacion horas semana
     */
    public void setDedicacionHorasSemana(double dedicacionHorasSemana) {
        this.dedicacionHorasSemana = dedicacionHorasSemana;
    }

    /**
     * Gets the total horas vinculacion.
     *
     * @return the total horas vinculacion
     */
    public Double getTotalHorasVinculacion() {
        return totalHorasVinculacion;
    }

    /**
     * Sets the total horas vinculacion.
     *
     * @param totalHorasVinculacion
     *            the new total horas vinculacion
     */
    public void setTotalHorasVinculacion(Double totalHorasVinculacion) {
        this.totalHorasVinculacion = totalHorasVinculacion;
    }

    /**
     * Gets the tipo.
     *
     * @return the tipo
     */
    public TipoInvestigador getTipo() {
        return tipo;
    }

    /**
     * Sets the tipo.
     *
     * @param tipo
     *            the new tipo
     */
    public void setTipo(TipoInvestigador tipo) {
        this.tipo = tipo;
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
     * Gets the investigador.
     *
     * @return the investigador
     */
    public Investigador getInvestigador() {
        return investigador;
    }

    /**
     * Sets the investigador.
     *
     * @param investigador
     *            the new investigador
     */
    public void setInvestigador(Investigador investigador) {
        this.investigador = investigador;
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
     * @param proyecto
     *            the new proyecto
     */
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    /**
     * Gets the funcion.
     *
     * @return the funcion
     */
    public String getFuncion() {
        return funcion;
    }

    /**
     * Sets the funcion.
     *
     * @param funcion
     *            the new funcion
     */
    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    /**
     * Gets the horas jornada docente.
     *
     * @return the horas jornada docente
     */
    public Integer getHorasJornadaDocente() {
        return horasJornadaDocente;
    }

    /**
     * Sets the horas jornada docente.
     *
     * @param horasJornadaDocente
     *            the new horas jornada docente
     */
    public void setHorasJornadaDocente(Integer horasJornadaDocente) {
        this.horasJornadaDocente = horasJornadaDocente;
    }

    /**
     * Gets the empresa.
     *
     * @return the empresa
     */
    public Empresa getEmpresa() {
        return empresa;
    }

    /**
     * Sets the empresa.
     *
     * @param empresa
     *            the new empresa
     */
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    /**
     * Gets the programa estudiante.
     *
     * @return the programa estudiante
     */
    public String getProgramaEstudiante() {
        return programaEstudiante;
    }

    /**
     * Sets the programa estudiante.
     *
     * @param programaEstudiante
     *            the new programa estudiante
     */
    public void setProgramaEstudiante(String programaEstudiante) {
        this.programaEstudiante = programaEstudiante;
    }

    /**
     * Gets the valor pagar.
     *
     * @return the valor pagar
     */
    public Long getValorPagar() {
        return valorPagar;
    }

    /**
     * Sets the valor pagar.
     *
     * @param valorPagar
     *            the new valor pagar
     */
    public void setValorPagar(Long valorPagar) {
        this.valorPagar = valorPagar;
    }

    /**
     * Gets the visible.
     *
     * @return the visible
     */
    public String getVisible() {
        return visible;
    }

    /**
     * Sets the visible.
     *
     * @param visible
     *            the new visible
     */
    public void setVisible(String visible) {
        this.visible = visible;
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
     * Sets the dependencia.
     *
     * @param dependencia
     *            the dependencia to set
     */
    public void setDependencia(Dependencia dependencia) {
        this.dependencia = dependencia;
    }

    /**
     * Gets the plan.
     *
     * @return the plan
     */
    public PlanEstudios getPlan() {
        return plan;
    }

    /**
     * Sets the plan.
     *
     * @param plan
     *            the plan to set
     */
    public void setPlan(PlanEstudios plan) {
        this.plan = plan;
    }

    /**
     * Gets the institucion.
     *
     * @return the institucion
     */
    public Institucion getInstitucion() {
        return institucion;
    }

    /**
     * Sets the institucion.
     *
     * @param institucion
     *            the institucion to set
     */
    public void setInstitucion(Institucion institucion) {
        this.institucion = institucion;
    }

    /**
     * Crear copia.
     *
     * @return the investigador proyecto
     */
    public InvestigadorProyecto crearCopia() {

        InvestigadorProyecto ipNew = new InvestigadorProyecto();

        ipNew.setDedicacionHorasSemana(this.getDedicacionHorasSemana());
        ipNew.setEmpresa(this.getEmpresa());
        ipNew.setFuncion(this.getFuncion());
        ipNew.setHorasJornadaDocente(this.getHorasJornadaDocente());
        ipNew.setInvestigador(this.getInvestigador());
        ipNew.setProgramaEstudiante(this.getProgramaEstudiante());
        ipNew.setTipo(this.getTipo());
        ipNew.setTotalHorasVinculacion(this.getTotalHorasVinculacion());
        ipNew.setValorPagar(this.getValorPagar());
        ipNew.setVisible(this.getVisible());

        return ipNew;

    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

	public FuenteFinanciacion getInstitucionInvestigador() {
		return institucionInvestigador;
	}

	public void setInstitucionInvestigador(FuenteFinanciacion institucionInvestigador) {
		this.institucionInvestigador = institucionInvestigador;
	}

	public Date getFechaVinculacion() {
		return fechaVinculacion;
	}

	public void setFechaVinculacion(Date fechaVinculacion) {
		this.fechaVinculacion = fechaVinculacion;
	}

}
