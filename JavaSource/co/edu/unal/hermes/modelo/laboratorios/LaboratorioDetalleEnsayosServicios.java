/**
} * @author dgbenitezc
 */

package co.edu.unal.hermes.modelo.laboratorios;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import co.edu.unal.hermes.modelo.Tipos;

public class LaboratorioDetalleEnsayosServicios implements Cloneable{

	private Long id;
	private Laboratorio laboratorio;
	private String nombre;
	private Boolean docencia;
	private Boolean investigacion;
	private Boolean extension;
	private Integer valorServicio;
	private Integer ensayosMes;
	private Integer tiempoTotalEstimado;
	private Tipos unidadTiempoTotalEstimado;
	private Integer personas;
	private Boolean acreditado;
	private Date fechaRegistro;
	private Set<LaboratorioDetalleEquipos> equipos = new HashSet<LaboratorioDetalleEquipos>();
	private Tipos tipoNormaTecnica;
	private String numeroNormaTecnica;
	private Tipos tipoEnsayo;
	private String tipoEnsayoOtros;
	
	private String descripcion;
	private Tipos calibMagnitudArea;
	private String calibEquipo;
	private String versionNormaTecnica;
	private Tipos acreditadoOrganismo;
	
	private String intervaloPuntoMedMinimo;
    private String intervaloPuntoMedMaximo;
    private Tipos unidadesIntervaloPrefijo;
    private Tipos unidadesIntervaloUnidad;
    private String unidadesIntervaloSimbolo;
    private Tipos incertidumbreMedicionIncertidumbre;
    private String incertidumbreMedicionValor;
    private Tipos incertidumbreMedicionPrefijo;
    private Tipos incertidumbreMedicionUnidad;
    private String incertidumbreMedicionSimbolo;

	public LaboratorioDetalleEnsayosServicios() {
	}

	@Override
	public boolean equals(Object otroObjeto) {
		LaboratorioDetalleEnsayosServicios otroDetalle = (LaboratorioDetalleEnsayosServicios) otroObjeto;
		return nombre.equals(otroDetalle.nombre);
	}
	
	public Object clone() throws CloneNotSupportedException {
	    return super.clone();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the laboratorio
	 */
	public Laboratorio getLaboratorio() {
		return laboratorio;
	}

	/**
	 * @param laboratorio
	 *            the laboratorio to set
	 */
	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
	}

	/**
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @param fechaRegistro
	 *            the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the docencia
	 */
	public Boolean getDocencia() {
		return docencia;
	}

	/**
	 * @param docencia
	 *            the docencia to set
	 */
	public void setDocencia(Boolean docencia) {
		this.docencia = docencia;
	}

	/**
	 * @return the investigacion
	 */
	public Boolean getInvestigacion() {
		return investigacion;
	}

	/**
	 * @param investigacion
	 *            the investigacion to set
	 */
	public void setInvestigacion(Boolean investigacion) {
		this.investigacion = investigacion;
	}

	/**
	 * @return the extension
	 */
	public Boolean getExtension() {
		return extension;
	}

	/**
	 * @param extension
	 *            the extension to set
	 */
	public void setExtension(Boolean extension) {
		this.extension = extension;
	}

	/**
	 * @return the valorServicio
	 */
	public Integer getValorServicio() {
		return valorServicio;
	}

	/**
	 * @param valorServicio
	 *            the valorServicio to set
	 */
	public void setValorServicio(Integer valorServicio) {
		this.valorServicio = valorServicio;
	}

	/**
	 * @return the ensayosMes
	 */
	public Integer getEnsayosMes() {
		return ensayosMes;
	}

	/**
	 * @param ensayosMes
	 *            the ensayosMes to set
	 */
	public void setEnsayosMes(Integer ensayosMes) {
		this.ensayosMes = ensayosMes;
	}

	/**
	 * @return the tiempoTotalEstimado
	 */
	public Integer getTiempoTotalEstimado() {
		return tiempoTotalEstimado;
	}

	/**
	 * @param tiempoTotalEstimado
	 *            the tiempoTotalEstimado to set
	 */
	public void setTiempoTotalEstimado(Integer tiempoTotalEstimado) {
		this.tiempoTotalEstimado = tiempoTotalEstimado;
	}

	/**
	 * @return the personas
	 */
	public Integer getPersonas() {
		return personas;
	}

	/**
	 * @param personas
	 *            the personas to set
	 */
	public void setPersonas(Integer personas) {
		this.personas = personas;
	}

	/**
	 * @return the unidadTiempoTotalEstimado
	 */
	public Tipos getUnidadTiempoTotalEstimado() {
		return unidadTiempoTotalEstimado;
	}

	/**
	 * @param unidadTiempoTotalEstimado
	 *            the unidadTiempoTotalEstimado to set
	 */
	public void setUnidadTiempoTotalEstimado(Tipos unidadTiempoTotalEstimado) {
		this.unidadTiempoTotalEstimado = unidadTiempoTotalEstimado;
	}

	/**
	 * @return the equipos
	 */
	public Set<LaboratorioDetalleEquipos> getEquipos() {
		return equipos;
	}

	/**
	 * @param equipos
	 *            the equipos to set
	 */
	public void setEquipos(Set<LaboratorioDetalleEquipos> equipos) {
		this.equipos = equipos;
	}

	public List<LaboratorioDetalleEquipos> getListaEquipos() {
		List<LaboratorioDetalleEquipos> listaEquipos = new ArrayList<LaboratorioDetalleEquipos>();
		listaEquipos.addAll(equipos);
		return listaEquipos;
	}

	public Boolean getAcreditado() {
		return acreditado;
	}

	public void setAcreditado(Boolean acreditado) {
		this.acreditado = acreditado;
	}

	public Tipos getTipoNormaTecnica() {
		return tipoNormaTecnica;
	}

	public void setTipoNormaTecnica(Tipos tipoNormaTecnica) {
		this.tipoNormaTecnica = tipoNormaTecnica;
	}

	public String getNumeroNormaTecnica() {
		return numeroNormaTecnica;
	}

	public void setNumeroNormaTecnica(String numeroNormaTecnica) {
		this.numeroNormaTecnica = numeroNormaTecnica;
	}

	public Tipos getTipoEnsayo() {
		return tipoEnsayo;
	}

	public void setTipoEnsayo(Tipos tipoEnsayo) {
		this.tipoEnsayo = tipoEnsayo;
	}

	public String getTipoEnsayoOtros() {
		return tipoEnsayoOtros;
	}

	public void setTipoEnsayoOtros(String tipoEnsayoOtros) {
		this.tipoEnsayoOtros = tipoEnsayoOtros;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Tipos getCalibMagnitudArea() {
		return calibMagnitudArea;
	}

	public void setCalibMagnitudArea(Tipos calibMagnitudArea) {
		this.calibMagnitudArea = calibMagnitudArea;
	}

	public String getCalibEquipo() {
		return calibEquipo;
	}

	public void setCalibEquipo(String calibEquipo) {
		this.calibEquipo = calibEquipo;
	}

	public String getVersionNormaTecnica() {
		return versionNormaTecnica;
	}

	public void setVersionNormaTecnica(String versionNormaTecnica) {
		this.versionNormaTecnica = versionNormaTecnica;
	}

	public Tipos getAcreditadoOrganismo() {
		return acreditadoOrganismo;
	}

	public void setAcreditadoOrganismo(Tipos acreditadoOrganismo) {
		this.acreditadoOrganismo = acreditadoOrganismo;
	}

	public String getIntervaloPuntoMedMinimo() {
		return intervaloPuntoMedMinimo;
	}

	public void setIntervaloPuntoMedMinimo(String intervaloPuntoMedMinimo) {
		this.intervaloPuntoMedMinimo = intervaloPuntoMedMinimo;
	}

	public String getIntervaloPuntoMedMaximo() {
		return intervaloPuntoMedMaximo;
	}

	public void setIntervaloPuntoMedMaximo(String intervaloPuntoMedMaximo) {
		this.intervaloPuntoMedMaximo = intervaloPuntoMedMaximo;
	}

	public Tipos getUnidadesIntervaloPrefijo() {
		return unidadesIntervaloPrefijo;
	}

	public void setUnidadesIntervaloPrefijo(Tipos unidadesIntervaloPrefijo) {
		this.unidadesIntervaloPrefijo = unidadesIntervaloPrefijo;
	}

	public Tipos getUnidadesIntervaloUnidad() {
		return unidadesIntervaloUnidad;
	}

	public void setUnidadesIntervaloUnidad(Tipos unidadesIntervaloUnidad) {
		this.unidadesIntervaloUnidad = unidadesIntervaloUnidad;
	}

	public String getUnidadesIntervaloSimbolo() {
		return unidadesIntervaloSimbolo;
	}

	public void setUnidadesIntervaloSimbolo(String unidadesIntervaloSimbolo) {
		this.unidadesIntervaloSimbolo = unidadesIntervaloSimbolo;
	}

	public Tipos getIncertidumbreMedicionIncertidumbre() {
		return incertidumbreMedicionIncertidumbre;
	}

	public void setIncertidumbreMedicionIncertidumbre(Tipos incertidumbreMedicionIncertidumbre) {
		this.incertidumbreMedicionIncertidumbre = incertidumbreMedicionIncertidumbre;
	}

	public String getIncertidumbreMedicionValor() {
		return incertidumbreMedicionValor;
	}

	public void setIncertidumbreMedicionValor(String incertidumbreMedicionValor) {
		this.incertidumbreMedicionValor = incertidumbreMedicionValor;
	}

	public Tipos getIncertidumbreMedicionPrefijo() {
		return incertidumbreMedicionPrefijo;
	}

	public void setIncertidumbreMedicionPrefijo(Tipos incertidumbreMedicionPrefijo) {
		this.incertidumbreMedicionPrefijo = incertidumbreMedicionPrefijo;
	}

	public Tipos getIncertidumbreMedicionUnidad() {
		return incertidumbreMedicionUnidad;
	}

	public void setIncertidumbreMedicionUnidad(Tipos incertidumbreMedicionUnidad) {
		this.incertidumbreMedicionUnidad = incertidumbreMedicionUnidad;
	}

	public String getIncertidumbreMedicionSimbolo() {
		return incertidumbreMedicionSimbolo;
	}

	public void setIncertidumbreMedicionSimbolo(String incertidumbreMedicionSimbolo) {
		this.incertidumbreMedicionSimbolo = incertidumbreMedicionSimbolo;
	}
}
