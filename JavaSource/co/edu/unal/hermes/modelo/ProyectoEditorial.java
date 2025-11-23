package co.edu.unal.hermes.modelo;

public class ProyectoEditorial implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long idProyecto;
	private Proyecto proyecto;
	private String tipoPublicacion;
	private Boolean comitePuntaje;
	private Boolean indicesAnaliticos;
	private String textoOtroIdioma;
	private Boolean otroIdioma;
	private Boolean materialGraficoBN;
	private Boolean materialGraficoColor;
	private Boolean materialGraficoAjeno;
	private Boolean licenciaReproduccion;
	private Boolean involucraGrupos;
	private Integer origenProyecto;
	private String otroOrigenProyecto;
	private Integer caracterProyecto;
	private Integer numeroAutores;
	private Integer idGrupo;
	private Boolean aceptaTerminos;
	private String breveDescripcionAudiencia;

	public Long getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public String getTipoPublicacion() {
		return tipoPublicacion;
	}

	public void setTipoPublicacion(String tipoPublicacion) {
		this.tipoPublicacion = tipoPublicacion;
	}

	public Boolean getComitePuntaje() {
		return comitePuntaje;
	}

	public void setComitePuntaje(Boolean comitePuntaje) {
		this.comitePuntaje = comitePuntaje;
	}

	public Boolean getIndicesAnaliticos() {
		return indicesAnaliticos;
	}

	public void setIndicesAnaliticos(Boolean indicesAnaliticos) {
		this.indicesAnaliticos = indicesAnaliticos;
	}

	public Boolean getMaterialGraficoBN() {
		return materialGraficoBN;
	}

	public void setMaterialGraficoBN(Boolean materialGraficoBN) {
		this.materialGraficoBN = materialGraficoBN;
	}

	public Boolean getMaterialGraficoColor() {
		return materialGraficoColor;
	}

	public void setMaterialGraficoColor(Boolean materialGraficoColor) {
		this.materialGraficoColor = materialGraficoColor;
	}

	public Boolean getMaterialGraficoAjeno() {
		return materialGraficoAjeno;
	}

	public void setMaterialGraficoAjeno(Boolean materialGraficoAjeno) {
		this.materialGraficoAjeno = materialGraficoAjeno;
	}

	public Boolean getLicenciaReproduccion() {
		return licenciaReproduccion;
	}

	public void setLicenciaReproduccion(Boolean licenciaReproduccion) {
		this.licenciaReproduccion = licenciaReproduccion;
	}

	public Integer getOrigenProyecto() {
		return origenProyecto;
	}

	public void setOrigenProyecto(Integer origenProyecto) {
		this.origenProyecto = origenProyecto;
	}

	public String getOtroOrigenProyecto() {
		return otroOrigenProyecto;
	}

	public void setOtroOrigenProyecto(String otroOrigenProyecto) {
		this.otroOrigenProyecto = otroOrigenProyecto;
	}

	public Integer getCaracterProyecto() {
		return caracterProyecto;
	}

	public void setCaracterProyecto(Integer origeProyecto) {
		this.caracterProyecto = origeProyecto;
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public Integer getNumeroAutores() {
		return numeroAutores;
	}

	public void setNumeroAutores(Integer numeroAutores) {
		this.numeroAutores = numeroAutores;
	}

	public Boolean getInvolucraGrupos() {
		return involucraGrupos;
	}

	public void setInvolucraGrupos(Boolean involucraGrupos) {
		this.involucraGrupos = involucraGrupos;
	}

	public String getTextoOtroIdioma() {
		return textoOtroIdioma;
	}

	public void setTextoOtroIdioma(String textoOtroIdioma) {
		this.textoOtroIdioma = textoOtroIdioma;
	}

	public Boolean getOtroIdioma() {
		return otroIdioma;
	}

	public void setOtroIdioma(Boolean otroIdioma) {
		this.otroIdioma = otroIdioma;
	}

	public Boolean getAceptaTerminos() {
		return aceptaTerminos;
	}

	public void setAceptaTerminos(Boolean aceptaTerminos) {
		this.aceptaTerminos = aceptaTerminos;
	}

	public String getBreveDescripcionAudiencia() {
		return breveDescripcionAudiencia;
	}

	public void setBreveDescripcionAudiencia(String breveDescripcionAudiencia) {
		this.breveDescripcionAudiencia = breveDescripcionAudiencia;
	}
}