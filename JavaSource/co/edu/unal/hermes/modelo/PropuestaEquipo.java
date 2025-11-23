package co.edu.unal.hermes.modelo;

import java.util.List;

public class PropuestaEquipo {

	private String nombreEquipo;
	private String descripcionEquipo;
	private Long valorEquipo;
	private String mecanismosParticip;
	private String justificacion;
	private String potencialUso;
	private String coherencia;
	private String planSostenibilidad;
	private String compromisos;
	private String impacto;
	private String personaResponsable;

	public PropuestaEquipo() {
		nombreEquipo = "";
		descripcionEquipo = "";
		valorEquipo = -1L;
		mecanismosParticip = "";
		justificacion = "";
		potencialUso = "";
		coherencia = "";
		planSostenibilidad = "";
		compromisos = "";
		impacto = "";
		personaResponsable = "";
	}

	public PropuestaEquipo(String toString) {
		this();
		String[] data = toString.split(SEPARADOR_COLUMNA);
		nombreEquipo = data[0];
		descripcionEquipo = data[1];
		valorEquipo = Long.parseLong(data[2]);
		mecanismosParticip = data[3];
		justificacion = data[4];
		potencialUso = data[5];
		coherencia = data[6];
		planSostenibilidad = data[7];
		compromisos = data[8];
		impacto = data[9];
		personaResponsable = data[10];
	}
	
	public static PropuestaEquipo toPropuestaEquipoModDosTres(String toString) {
		PropuestaEquipo pe = new PropuestaEquipo();
		String[] data = toString.split(SEPARADOR_COLUMNA);
		pe.setNombreEquipo(data[0]);
		pe.setDescripcionEquipo(data[1]);
		pe.setValorEquipo(Long.parseLong(data[2]));
		pe.setJustificacion(data[3]);
		pe.setPotencialUso(data[4]);
		pe.setCoherencia(data[5]);
		pe.setPlanSostenibilidad(data[6]);
		pe.setCompromisos(data[7]);
		pe.setPersonaResponsable(data[8]);
		return pe;
	}

	protected boolean validarDatos(Object... obs) {
		for (Object o : obs) {
			if (o instanceof String && o != null) {
				String str = (String) o;
				if (str.contains(SEPARADOR_COLUMNA) || str.contains(SEPARADOR_REGISTRO)) {
					return false;
				}
			}
		}
		return true;
	}

	public String producirToString(List<PropuestaEquipo> listas) throws Exception {
		String ret = "";
		for (PropuestaEquipo ppa : listas) {
			ret += ppa.toString().replace("\r", " ").replace("\n", " ") + SEPARADOR_REGISTRO;
		}
		return ret;
	}

	public String producirToStringModDosTres(List<PropuestaEquipo> listas) throws Exception {
		String ret = "";
		for (PropuestaEquipo ppa : listas) {
			ret += toStringModDosTres(ppa).replace("\r", " ").replace("\n", " ") + SEPARADOR_REGISTRO;
		}
		return ret;
	}


	public static String toStringModDosTres(PropuestaEquipo fe) {
		String est = "";

		est = fe.getNombreEquipo().replace("\r", " ").replace("\n", " ") + SEPARADOR_COLUMNA + fe.getDescripcionEquipo().replace("\r", " ").replace("\n", " ") + SEPARADOR_COLUMNA + fe.getValorEquipo() + SEPARADOR_COLUMNA + fe.getJustificacion().replace("\r", " ").replace("\n", " ")
		+ SEPARADOR_COLUMNA + fe.getPotencialUso().replace("\r", " ").replace("\n", " ") + SEPARADOR_COLUMNA + fe.coherencia.replace("\r", " ").replace("\n", " ")
		+ SEPARADOR_COLUMNA + fe.getPlanSostenibilidad().replace("\r", " ").replace("\n", " ")
		+ SEPARADOR_COLUMNA + fe.getCompromisos().replace("\r", " ").replace("\n", " ") + SEPARADOR_COLUMNA + fe.getPersonaResponsable().replace("\r", " ").replace("\n", " ");

		return est;
	}

	public String getNombreEquipo() {
		return nombreEquipo;
	}

	public void setNombreEquipo(String nombreEquipo) {
		this.nombreEquipo = nombreEquipo;
	}

	public String getDescripcionEquipo() {
		return descripcionEquipo;
	}

	public void setDescripcionEquipo(String descripcionEquipo) {
		this.descripcionEquipo = descripcionEquipo;
	}

	public Long getValorEquipo() {
		return valorEquipo;
	}

	public void setValorEquipo(Long valorEquipo) {
		this.valorEquipo = valorEquipo;
	}

	public String getMecanismosParticip() {
		return mecanismosParticip;
	}

	public void setMecanismosParticip(String mecanismosParticip) {
		this.mecanismosParticip = mecanismosParticip;
	}

	public String getJustificacion() {
		return justificacion;
	}

	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	public String getPotencialUso() {
		return potencialUso;
	}

	public void setPotencialUso(String potencialUso) {
		this.potencialUso = potencialUso;
	}

	public String getCoherencia() {
		return coherencia;
	}

	public void setCoherencia(String coherencia) {
		this.coherencia = coherencia;
	}

	public String getPlanSostenibilidad() {
		return planSostenibilidad;
	}

	public void setPlanSostenibilidad(String planSostenibilidad) {
		this.planSostenibilidad = planSostenibilidad;
	}

	public String getCompromisos() {
		return compromisos;
	}

	public void setCompromisos(String compromisos) {
		this.compromisos = compromisos;
	}

	public String getImpacto() {
		return impacto;
	}

	public void setImpacto(String impacto) {
		this.impacto = impacto;
	}

	public String getPersonaResponsable() {
		return personaResponsable;
	}

	public void setPersonaResponsable(String personaResponsable) {
		this.personaResponsable = personaResponsable;
	}

	public final static String SEPARADOR_COLUMNA = "~";
	public final static String SEPARADOR_REGISTRO = "\r\n";

}
