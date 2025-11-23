package co.edu.unal.hermes.modelo;

public class ModalidadCriterioTipoPregunta {

	public static long cualitaticacuantivativa=1;
	public static long cualitativa=2;
	public static long seleccionUnica=3;
	
	
	private Long orden;
	private Long id;
	private TipoPregunta tipoPregunta;
	private Modalidad modalidad;
	private CriterioEvaluacion criterio;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TipoPregunta getTipoPregunta() {
		return tipoPregunta;
	}
	public void setTipoPregunta(TipoPregunta tipoPregunta) {
		this.tipoPregunta = tipoPregunta;
	}
	public Modalidad getModalidad() {
		return modalidad;
	}
	public void setModalidad(Modalidad modalidad) {
		this.modalidad = modalidad;
	}
	public CriterioEvaluacion getCriterio() {
		return criterio;
	}
	public void setCriterio(CriterioEvaluacion criterio) {
		this.criterio = criterio;
	}
	public Long getOrden() {
		return orden;
	}
	public void setOrden(Long orden) {
		this.orden = orden;
	} 

}
