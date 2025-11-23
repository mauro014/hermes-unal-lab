package co.edu.unal.hermes.modelo.laboratorios;

/**
 * Información acerca de información personal del usuario investigador.
 */
public class LaboratorioSolicitudCreacionLaboratorio extends LaboratorioSolicitud {

	private Long id;
	private String infraestructura;
	private String sostenibilidad;
	private String docenciaDescActividades;
	private String investigacionDescActividades;
	private String extensionDescActividades;
	
	//Mobiliario Requerido
	
	private Boolean mesasPC;
	private Long mesasPCCantidad;
	private Boolean mesasMovibles;
	private Long mesasMoviblesCantidad;
	private Boolean puestosTrabajo;
	private Boolean puestosTrabajoDetalle;
	
	private Long reactivosQuimicosArmarioSeguridad;
	private Long reactivosQuimicosVitrina;
	private Long reactivosQuimicosCajonera;
	private Long reactivosQuimicosEstanteria;
	private Long reactivosQuimicosOtro;
	
	private Long instrumentalArmarioSeguridad;
	private Long instrumentalVitrina;
	private Long instrumentalCajonera;
	private Long instrumentalEstanteria;
	private Long instrumentalOtro;
	
	private Long herramientasArmarioSeguridad;
	private Long herramientasVitrina;
	private Long herramientasCajonera;
	private Long herramientasEstanteria;
	private Long herramientasOtro;
	
	private Long insumosArmarioSeguridad;
	private Long insumosVitrina;
	private Long insumosCajonera;
	private Long insumosEstanteria;
	private Long insumosOtro;
	
	private Long otroArmarioSeguridad;
	private Long otroVitrina;
	private Long otroCajonera;
	private Long otroEstanteria;
	private Long otroOtro;
	
	private Boolean cabinaExtraccion;
	private Long cabinaExtraccionCantidad;
	private String cabinaExtraccionDetalle;
	
	private Boolean cabinaSeguridad;
	private Long cabinaSeguridadCantidad;
	private String cabinaSeguridadDetalle;
	
	//Otros Requerimientos
	private Boolean requierePocetas;
	private String requierePocetasDetalle;
	private Boolean requiereMesonesLavado;
	private String requiereMesonesLavadoDetalle;
	private Boolean requiereMesasAntivibracion;
	private String requiereMesasAntivibracionDetalle;
	private Boolean requiereSifones;
	private String requiereSifonesDetalle;
	private Boolean requiereConexionGas;
	private String requiereConexionGasDetalle;
	private Boolean requiereDucha;
	private String requiereDuchaDetalle;
	private Boolean requiereLavaOjos;
	private String requiereLavaOjosDetalle;
	private Boolean requiereOtros;
	private String requiereOtrosDetalle;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getInfraestructura() {
		return infraestructura;
	}
	public void setInfraestructura(String infraestructura) {
		this.infraestructura = infraestructura;
	}
	public String getSostenibilidad() {
		return sostenibilidad;
	}
	public void setSostenibilidad(String sostenibilidad) {
		this.sostenibilidad = sostenibilidad;
	}
	public String getInvestigacionDescActividades() {
		return investigacionDescActividades;
	}
	public void setInvestigacionDescActividades(String investigacionDescActividades) {
		this.investigacionDescActividades = investigacionDescActividades;
	}
	public String getExtensionDescActividades() {
		return extensionDescActividades;
	}
	public void setExtensionDescActividades(String extensionDescActividades) {
		this.extensionDescActividades = extensionDescActividades;
	}
	public Boolean getMesasPC() {
		return mesasPC;
	}
	public void setMesasPC(Boolean mesasPC) {
		this.mesasPC = mesasPC;
	}
	public Long getMesasPCCantidad() {
		return mesasPCCantidad;
	}
	public void setMesasPCCantidad(Long mesasPCCantidad) {
		this.mesasPCCantidad = mesasPCCantidad;
	}
	public Boolean getMesasMovibles() {
		return mesasMovibles;
	}
	public void setMesasMovibles(Boolean mesasMovibles) {
		this.mesasMovibles = mesasMovibles;
	}
	public Long getMesasMoviblesCantidad() {
		return mesasMoviblesCantidad;
	}
	public void setMesasMoviblesCantidad(Long mesasMoviblesCantidad) {
		this.mesasMoviblesCantidad = mesasMoviblesCantidad;
	}
	public Long getReactivosQuimicosArmarioSeguridad() {
		if(reactivosQuimicosArmarioSeguridad != null)
			return reactivosQuimicosArmarioSeguridad;
		else
			return 0L;
	}
	public void setReactivosQuimicosArmarioSeguridad(Long reactivosQuimicosArmarioSeguridad) {
		this.reactivosQuimicosArmarioSeguridad = reactivosQuimicosArmarioSeguridad;
	}
	public Long getReactivosQuimicosVitrina() {
		
		if(reactivosQuimicosVitrina != null)
			return reactivosQuimicosVitrina;
		else
			return 0L;
	}
	public void setReactivosQuimicosVitrina(Long reactivosQuimicosVitrina) {
		this.reactivosQuimicosVitrina = reactivosQuimicosVitrina;
	}
	public Long getReactivosQuimicosCajonera() {
		
		if(reactivosQuimicosCajonera != null)
			return reactivosQuimicosCajonera;
		else
			return 0L;
	}
	public void setReactivosQuimicosCajonera(Long reactivosQuimicosCajonera) {
		this.reactivosQuimicosCajonera = reactivosQuimicosCajonera;
	}
	public Long getReactivosQuimicosEstanteria() {
		
		if(reactivosQuimicosEstanteria != null)
			return reactivosQuimicosEstanteria;
		else
			return 0L;
	}
	public void setReactivosQuimicosEstanteria(Long reactivosQuimicosEstanteria) {
		this.reactivosQuimicosEstanteria = reactivosQuimicosEstanteria;
	}
	public Long getReactivosQuimicosOtro() {
		
		if(reactivosQuimicosOtro != null)
			return reactivosQuimicosOtro;
		else
			return 0L;
	}
	public void setReactivosQuimicosOtro(Long reactivosQuimicosOtro) {
		this.reactivosQuimicosOtro = reactivosQuimicosOtro;
	}
	public Long getInstrumentalArmarioSeguridad() {
		
		if(instrumentalArmarioSeguridad != null)
			return instrumentalArmarioSeguridad;
		else
			return 0L;
	}
	public void setInstrumentalArmarioSeguridad(Long instrumentalArmarioSeguridad) {
		this.instrumentalArmarioSeguridad = instrumentalArmarioSeguridad;
	}
	public Long getInstrumentalVitrina() {
		
		if(instrumentalVitrina != null)
			return instrumentalVitrina;
		else
			return 0L;
	}
	public void setInstrumentalVitrina(Long instrumentalVitrina) {
		this.instrumentalVitrina = instrumentalVitrina;
	}
	public Long getInstrumentalCajonera() {
		
		if(instrumentalCajonera != null)
			return instrumentalCajonera;
		else
			return 0L;
	}
	public void setInstrumentalCajonera(Long instrumentalCajonera) {
		this.instrumentalCajonera = instrumentalCajonera;
	}
	public Long getInstrumentalEstanteria() {
		
		if(instrumentalEstanteria != null)
			return instrumentalEstanteria;
		else
			return 0L;
	}
	public void setInstrumentalEstanteria(Long instrumentalEstanteria) {
		this.instrumentalEstanteria = instrumentalEstanteria;
	}
	public Long getInstrumentalOtro() {
		
		if(instrumentalOtro != null)
			return instrumentalOtro;
		else
			return 0L;
	}
	public void setInstrumentalOtro(Long instrumentalOtro) {
		this.instrumentalOtro = instrumentalOtro;
	}
	public Long getHerramientasArmarioSeguridad() {
		
		if(herramientasArmarioSeguridad != null)
			return herramientasArmarioSeguridad;
		else
			return 0L;
	}
	public void setHerramientasArmarioSeguridad(Long herramientasArmarioSeguridad) {
		this.herramientasArmarioSeguridad = herramientasArmarioSeguridad;
	}
	public Long getHerramientasVitrina() {
		
		if(herramientasVitrina != null)
			return herramientasVitrina;
		else
			return 0L;
	}
	public void setHerramientasVitrina(Long herramientasVitrina) {
		this.herramientasVitrina = herramientasVitrina;
	}
	public Long getHerramientasCajonera() {
		
		if(herramientasCajonera != null)
			return herramientasCajonera;
		else
			return 0L;
	}
	public void setHerramientasCajonera(Long herramientasCajonera) {
		this.herramientasCajonera = herramientasCajonera;
	}
	public Long getHerramientasEstanteria() {
		
		if(herramientasEstanteria != null)
			return herramientasEstanteria;
		else
			return 0L;
	}
	public void setHerramientasEstanteria(Long herramientasEstanteria) {
		this.herramientasEstanteria = herramientasEstanteria;
	}
	public Long getHerramientasOtro() {
		
		if(herramientasOtro != null)
			return herramientasOtro;
		else
			return 0L;
	}
	public void setHerramientasOtro(Long herramientasOtro) {
		this.herramientasOtro = herramientasOtro;
	}
	public Long getInsumosArmarioSeguridad() {
		
		if(insumosArmarioSeguridad != null)
			return insumosArmarioSeguridad;
		else
			return 0L;
	}
	public void setInsumosArmarioSeguridad(Long insumosArmarioSeguridad) {
		this.insumosArmarioSeguridad = insumosArmarioSeguridad;
	}
	public Long getInsumosVitrina() {
		
		if(insumosVitrina != null)
			return insumosVitrina;
		else
			return 0L;
	}
	public void setInsumosVitrina(Long insumosVitrina) {
		this.insumosVitrina = insumosVitrina;
	}
	public Long getInsumosCajonera() {
		
		if(insumosCajonera != null)
			return insumosCajonera;
		else
			return 0L;
	}
	public void setInsumosCajonera(Long insumosCajonera) {
		this.insumosCajonera = insumosCajonera;
	}
	public Long getInsumosEstanteria() {
		
		if(insumosEstanteria != null)
			return insumosEstanteria;
		else
			return 0L;
	}
	public void setInsumosEstanteria(Long insumosEstanteria) {
		this.insumosEstanteria = insumosEstanteria;
	}
	public Long getInsumosOtro() {
		
		if(insumosOtro != null)
			return insumosOtro;
		else
			return 0L;
	}
	public void setInsumosOtro(Long insumosOtro) {
		this.insumosOtro = insumosOtro;
	}
	public Long getOtroArmarioSeguridad() {
		
		if(otroArmarioSeguridad != null)
			return otroArmarioSeguridad;
		else
			return 0L;
	}
	public void setOtroArmarioSeguridad(Long otroArmarioSeguridad) {
		this.otroArmarioSeguridad = otroArmarioSeguridad;
	}
	public Long getOtroVitrina() {
		
		if(otroVitrina != null)
			return otroVitrina;
		else
			return 0L;
	}
	public void setOtroVitrina(Long otroVitrina) {
		this.otroVitrina = otroVitrina;
	}
	public Long getOtroCajonera() {
		
		if(otroCajonera != null)
			return otroCajonera;
		else
			return 0L;
	}
	public void setOtroCajonera(Long otroCajonera) {
		this.otroCajonera = otroCajonera;
	}
	public Long getOtroEstanteria() {
		
		if(otroEstanteria != null)
			return otroEstanteria;
		else
			return 0L;
	}
	public void setOtroEstanteria(Long otroEstanteria) {
		this.otroEstanteria = otroEstanteria;
	}
	public Long getOtroOtro() {
		
		if(otroOtro != null)
			return otroOtro;
		else
			return 0L;
	}
	public void setOtroOtro(Long otroOtro) {
		this.otroOtro = otroOtro;
	}
	public Boolean getCabinaExtraccion() {
		return cabinaExtraccion;
	}
	public void setCabinaExtraccion(Boolean cabinaExtraccion) {
		this.cabinaExtraccion = cabinaExtraccion;
	}
	public Long getCabinaExtraccionCantidad() {
		return cabinaExtraccionCantidad;
	}
	public void setCabinaExtraccionCantidad(Long cabinaExtraccionCantidad) {
		this.cabinaExtraccionCantidad = cabinaExtraccionCantidad;
	}
	public String getCabinaExtraccionDetalle() {
		return cabinaExtraccionDetalle;
	}
	public void setCabinaExtraccionDetalle(String cabinaExtraccionDetalle) {
		this.cabinaExtraccionDetalle = cabinaExtraccionDetalle;
	}
	public Boolean getCabinaSeguridad() {
		return cabinaSeguridad;
	}
	public void setCabinaSeguridad(Boolean cabinaSeguridad) {
		this.cabinaSeguridad = cabinaSeguridad;
	}
	public Long getCabinaSeguridadCantidad() {
		return cabinaSeguridadCantidad;
	}
	public void setCabinaSeguridadCantidad(Long cabinaSeguridadCantidad) {
		this.cabinaSeguridadCantidad = cabinaSeguridadCantidad;
	}
	public String getCabinaSeguridadDetalle() {
		return cabinaSeguridadDetalle;
	}
	public void setCabinaSeguridadDetalle(String cabinaSeguridadDetalle) {
		this.cabinaSeguridadDetalle = cabinaSeguridadDetalle;
	}
	public Boolean getRequierePocetas() {
		return requierePocetas;
	}
	public void setRequierePocetas(Boolean requierePocetas) {
		this.requierePocetas = requierePocetas;
	}
	public String getRequierePocetasDetalle() {
		return requierePocetasDetalle;
	}
	public void setRequierePocetasDetalle(String requierePocetasDetalle) {
		this.requierePocetasDetalle = requierePocetasDetalle;
	}
	public Boolean getRequiereMesonesLavado() {
		return requiereMesonesLavado;
	}
	public void setRequiereMesonesLavado(Boolean requiereMesonesLavado) {
		this.requiereMesonesLavado = requiereMesonesLavado;
	}
	public String getRequiereMesonesLavadoDetalle() {
		return requiereMesonesLavadoDetalle;
	}
	public void setRequiereMesonesLavadoDetalle(String requiereMesonesLavadoDetalle) {
		this.requiereMesonesLavadoDetalle = requiereMesonesLavadoDetalle;
	}
	public Boolean getRequiereMesasAntivibracion() {
		return requiereMesasAntivibracion;
	}
	public void setRequiereMesasAntivibracion(Boolean requiereMesasAntivibracion) {
		this.requiereMesasAntivibracion = requiereMesasAntivibracion;
	}
	public String getRequiereMesasAntivibracionDetalle() {
		return requiereMesasAntivibracionDetalle;
	}
	public void setRequiereMesasAntivibracionDetalle(String requiereMesasAntivibracionDetalle) {
		this.requiereMesasAntivibracionDetalle = requiereMesasAntivibracionDetalle;
	}
	public Boolean getRequiereSifones() {
		return requiereSifones;
	}
	public void setRequiereSifones(Boolean requiereSifones) {
		this.requiereSifones = requiereSifones;
	}
	public String getRequiereSifonesDetalle() {
		return requiereSifonesDetalle;
	}
	public void setRequiereSifonesDetalle(String requiereSifonesDetalle) {
		this.requiereSifonesDetalle = requiereSifonesDetalle;
	}
	public Boolean getRequiereConexionGas() {
		return requiereConexionGas;
	}
	public void setRequiereConexionGas(Boolean requiereConexionGas) {
		this.requiereConexionGas = requiereConexionGas;
	}
	public String getRequiereConexionGasDetalle() {
		return requiereConexionGasDetalle;
	}
	public void setRequiereConexionGasDetalle(String requiereConexionGasDetalle) {
		this.requiereConexionGasDetalle = requiereConexionGasDetalle;
	}
	public Boolean getRequiereDucha() {
		return requiereDucha;
	}
	public void setRequiereDucha(Boolean requiereDucha) {
		this.requiereDucha = requiereDucha;
	}
	public String getRequiereDuchaDetalle() {
		return requiereDuchaDetalle;
	}
	public void setRequiereDuchaDetalle(String requiereDuchaDetalle) {
		this.requiereDuchaDetalle = requiereDuchaDetalle;
	}
	public Boolean getRequiereLavaOjos() {
		return requiereLavaOjos;
	}
	public void setRequiereLavaOjos(Boolean requiereLavaOjos) {
		this.requiereLavaOjos = requiereLavaOjos;
	}
	public String getRequiereLavaOjosDetalle() {
		return requiereLavaOjosDetalle;
	}
	public void setRequiereLavaOjosDetalle(String requiereLavaOjosDetalle) {
		this.requiereLavaOjosDetalle = requiereLavaOjosDetalle;
	}
	public Boolean getRequiereOtros() {
		return requiereOtros;
	}
	public void setRequiereOtros(Boolean requiereOtros) {
		this.requiereOtros = requiereOtros;
	}
	public String getRequiereOtrosDetalle() {
		return requiereOtrosDetalle;
	}
	public void setRequiereOtrosDetalle(String requiereOtrosDetalle) {
		this.requiereOtrosDetalle = requiereOtrosDetalle;
	}
	public Boolean getPuestosTrabajo() {
		return puestosTrabajo;
	}
	public void setPuestosTrabajo(Boolean puestosTrabajo) {
		this.puestosTrabajo = puestosTrabajo;
	}
	public Boolean getPuestosTrabajoDetalle() {
		return puestosTrabajoDetalle;
	}
	public void setPuestosTrabajoDetalle(Boolean puestosTrabajoDetalle) {
		this.puestosTrabajoDetalle = puestosTrabajoDetalle;
	}
	public String getDocenciaDescActividades() {
		return docenciaDescActividades;
	}
	public void setDocenciaDescActividades(String docenciaDescActividades) {
		this.docenciaDescActividades = docenciaDescActividades;
	}
	
	
}
