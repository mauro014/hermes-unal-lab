package co.edu.unal.hermes.modelo;

import java.util.Date;

import co.edu.unal.hermes.modelo.servicioGeneral.ServicioGeneral;

/**
 * The Class HistoricoCambioIntegrantes.
 */
public class HistoricoCambioIntegrantes {

	private Long id;
	private Grupo grupo;
	private Date fechaIngreso;
	private Date fechaRetiro;
	private Persona integrante;

	private String tipo;
	private String tipoVinculacionGrupo;
	private Dependencia dependencia;
	private Sede sede;
	private TipoVinculacion tipoVinculacion;
	private TipoDedicacion tipoDedicacion;
	private TipoFormacion tipoFormacion;
	private PlanEstudios planEstudios;
	private String semestreActual;
	private String documento;

	private Proyecto proyecto;
	private TipoInvestigador tipoInvestigadorProyecto;
	private Double mesesDedidacion;
	private double horasDedidacion;
	private EstadoCivil estadoCivil;
	
	private Semillero semillero;
	private SemilleroIntegranteTipo tipoInvestigadorSemillero;
	private Long valorDedicacion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Date getFechaRetiro() {
		return fechaRetiro;
	}

	public void setFechaRetiro(Date fechaRetiro) {
		this.fechaRetiro = fechaRetiro;
	}

	public Persona getIntegrante() {
		return integrante;
	}

	public void setIntegrante(Persona integrante) {
		this.integrante = integrante;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTipoVinculacionGrupo() {
		return tipoVinculacionGrupo;
	}

	public void setTipoVinculacionGrupo(String tipoVinculacionGrupo) {
		this.tipoVinculacionGrupo = tipoVinculacionGrupo;
	}

	public Dependencia getDependencia() {
		return dependencia;
	}

	public void setDependencia(Dependencia dependencia) {
		this.dependencia = dependencia;
	}

	public Sede getSede() {
		return sede;
	}

	public void setSede(Sede sede) {
		this.sede = sede;
	}

	public TipoVinculacion getTipoVinculacion() {
		return tipoVinculacion;
	}

	public void setTipoVinculacion(TipoVinculacion tipoVinculacion) {
		this.tipoVinculacion = tipoVinculacion;
	}

	public TipoDedicacion getTipoDedicacion() {
		return tipoDedicacion;
	}

	public void setTipoDedicacion(TipoDedicacion tipoDedicacion) {
		this.tipoDedicacion = tipoDedicacion;
	}

	public TipoFormacion getTipoFormacion() {
		return tipoFormacion;
	}

	public void setTipoFormacion(TipoFormacion tipoFormacion) {
		this.tipoFormacion = tipoFormacion;
	}

	public PlanEstudios getPlanEstudios() {
		return planEstudios;
	}

	public void setPlanEstudios(PlanEstudios planEstudios) {
		this.planEstudios = planEstudios;
	}

	public String getSemestreActual() {
		return semestreActual;
	}

	public void setSemestreActual(String semestreActual) {
		this.semestreActual = semestreActual;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public TipoInvestigador getTipoInvestigadorProyecto() {
		return tipoInvestigadorProyecto;
	}

	public void setTipoInvestigadorProyecto(TipoInvestigador tipoInvestigadorProyecto) {
		this.tipoInvestigadorProyecto = tipoInvestigadorProyecto;
	}

	public Double getMesesDedidacion() {
		return mesesDedidacion;
	}

	public void setMesesDedidacion(Double mesesDedidacion) {
		this.mesesDedidacion = mesesDedidacion;
	}

	public double getHorasDedidacion() {
		return horasDedidacion;
	}

	public void setHorasDedidacion(double horasDedidacion) {
		this.horasDedidacion = horasDedidacion;
	}

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public Semillero getSemillero() {
		return semillero;
	}

	public void setSemillero(Semillero semillero) {
		this.semillero = semillero;
	}

	public SemilleroIntegranteTipo getTipoInvestigadorSemillero() {
		return tipoInvestigadorSemillero;
	}

	public void setTipoInvestigadorSemillero(SemilleroIntegranteTipo tipoInvestigadorSemillero) {
		this.tipoInvestigadorSemillero = tipoInvestigadorSemillero;
	}

	public Long getValorDedicacion() {
		return valorDedicacion;
	}

	public void setValorDedicacion(Long valorDedicacion) {
		this.valorDedicacion = valorDedicacion;
	}
}
