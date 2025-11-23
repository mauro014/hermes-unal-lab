/*
 * Created on 17-ago-2006
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class TipoDedicacion implements Serializable {

	private static final long serialVersionUID = -8802937294262894102L;

	String id;
	String nombre;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		if (id.equals("3")) {
			nombre = "Exclusiva";
		} else if (id.equals("4")) {
			nombre = "Tiempo completo";
		} else if (id.equals("5")) {
			nombre = "Medio tiempo";
		} else if (id.equals("6") || id.equals("7") || id.equals("24")
				|| id.equals("25") || id.equals("26") || id.equals("27")
				|| id.equals("28")) {
			nombre = "Cátedra";
		} else if (id.equals("8")) {
			nombre = "Educador cátedra";
		} else {
			nombre = "sindefinir";
		}

		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
