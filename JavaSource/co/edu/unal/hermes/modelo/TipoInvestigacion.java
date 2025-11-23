package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class TipoInvestigacion implements Serializable, Comparable {

	private static final long serialVersionUID = 1303148379185036078L;

	public static String CREACION_ARTISTICA = "2040100";
	public static String ACREDITACION_LABORATORIOS = "4020204";

	private String id;

	private String nombre;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * 
	 * @param tm
	 *            objeto con el que se comparan los tipos investigacion
	 * @return int Retorna 1 si el objeto invocante es mayor que el parametro,
	 *         -1 si es menor y cero si son iguales.
	 * @author Eddixon Castillo y David Santana
	 * 
	 * */
	public int compareTo(Object tm) {
		final int BEFORE = -1;
		final int EQUAL = 0;
		final int AFTER = 1;
		String tmid = ((TipoInvestigacion) tm).id;
		try {
			if (this == tm)
				return EQUAL;
			if (tmid != null) {
				if (Integer.parseInt(this.id) < Integer
						.parseInt(((TipoInvestigacion) tm).id))
					return BEFORE;
				if (Integer.parseInt(this.id) > Integer
						.parseInt(((TipoInvestigacion) tm).id))
					return AFTER;
			}
		} catch (ClassCastException e) {
			e.printStackTrace();
			return EQUAL;
		}
		return EQUAL;
	}
}
