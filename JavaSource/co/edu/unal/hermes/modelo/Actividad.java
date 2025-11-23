package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Set;

/**
 * Específica la actividad asociada al proyecto.
 * 
 */
public class Actividad implements Comparator, Serializable {

	private Long id;
	private String descripcion;
	private Integer semanaInicial;
	private Integer mesInicial;
	private Integer duracionSemanas;
	private Integer duracionMeses;
	private Proyecto proyecto;
	private boolean borrable = false;
	private Set actividadesPersona;
	private String meta;
	private ObjetivoEspecifico objetivoEspecifico;
	private MetaProyecto metaObjeto;
	private ResultadoProyecto resultado;
	private Long numeroOrden;
	private String idInv;

	public Set getActividadesPersona() {
		return actividadesPersona;
	}

	public void setActividadesPersona(Set actividadesPersona) {
		this.actividadesPersona = actividadesPersona;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getDuracionSemanas() {
		return duracionSemanas;
	}

	public void setDuracionSemanas(Integer duracionSemanas) {
		this.duracionSemanas = duracionSemanas;
	}

	public Integer getSemanaInicial() {
		return semanaInicial;
	}

	public void setSemanaInicial(Integer semanaInicial) {
		this.semanaInicial = semanaInicial;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public Integer getDuracionMeses() {
		return duracionMeses;
	}

	public void setDuracionMeses(Integer duracionMeses) {
		this.duracionMeses = duracionMeses;
		setDuracionSemanas(new Integer(4 * this.duracionMeses.intValue()));
	}

	public Integer getMesInicial() {
		return mesInicial;
	}

	public void setMesInicial(Integer mesInicial) {
		this.mesInicial = mesInicial;
		setSemanaInicial(new Integer(4 * (mesInicial.intValue() - 1) + 1));
	}

	public void pasarSemanasMeses() {
		mesInicial = new Integer((new Double(((semanaInicial.intValue() - 1) / 4) + 1)).intValue());
		duracionMeses = new Integer((new Double((duracionSemanas.intValue() / 4))).intValue());
	}

	public boolean isBorrable() {
		return borrable;
	}

	public void setBorrable(boolean borrable) {
		this.borrable = borrable;
	}

	public int compare(Object arg0, Object arg1) {
		Actividad a1 = (Actividad) arg0;
		Actividad a2 = (Actividad) arg1;
		if (a1.getId() != null && a2.getId() != null) {
			if ((a1.getId()).longValue() < a1.getId().longValue())
				return -1;
			if ((a1.getId()).longValue() == a1.getId().longValue())
				return 0;
			if ((a1.getId()).longValue() > a1.getId().longValue())
				return 1;
		}
		return 0;
	}

	public boolean equals(Object a) {
		if (!(a instanceof Actividad)) {
			return false;
		}
		Actividad act = (Actividad) a;
		if (act.id == null || this.id == null) {
			return false;
		}
		return (act.getId().equals(this.getId()));
	}

	public String getMeta() {
		return meta;
	}

	public void setMeta(String meta) {
		this.meta = meta;
	}

	public ObjetivoEspecifico getObjetivoEspecifico() {
		return objetivoEspecifico;
	}

	public void setObjetivoEspecifico(ObjetivoEspecifico objetivoEspecifico) {
		this.objetivoEspecifico = objetivoEspecifico;
	}

	public MetaProyecto getMetaObjeto() {
		return metaObjeto;
	}

	public void setMetaObjeto(MetaProyecto metaObjeto) {
		this.metaObjeto = metaObjeto;
	}

	public ResultadoProyecto getResultado() {
		return resultado;
	}

	public void setResultado(ResultadoProyecto resultado) {
		this.resultado = resultado;
	}

	public Long getNumeroOrden() {
		return numeroOrden;
	}

	public void setNumeroOrden(Long numeroOrden) {
		this.numeroOrden = numeroOrden;
	}

	public String getIdInv() {
		return idInv;
	}

	public void setIdInv(String idInv) {
		this.idInv = idInv;
	}

	public String getResponsable() {
		String type = "";
		if (actividadesPersona == null || actividadesPersona.isEmpty()) {
			return "Sin Responsable Asignado";
		} else {
			for (InvestigadorProyecto ip : proyecto.getInvestigadoresProyecto()) {
				if (ip.getInvestigador()
						.equals(((ActividadPersona) actividadesPersona.toArray()[0]).getInvestigador())) {
					System.out.println(ip.getTipo().getNombre());
					type = " (" + ip.getTipo().getNombre() + ")";
				}
			}
			return ((ActividadPersona) actividadesPersona.toArray()[0]).getInvestigador().getNombreCompleto() + type;
		}
	}
}
