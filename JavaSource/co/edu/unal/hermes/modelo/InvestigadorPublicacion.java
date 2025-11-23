/*
 * Created on 26-may-2014
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class InvestigadorPublicacion implements Serializable {

	private static final long serialVersionUID = 2002899580919088371L;

	private Long id;
	private String tipo;
	private String autores;
	private String publicacion;
	private Investigador investigador;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getPublicacion() {
		return publicacion;
	}

	public void setPublicacion(String publicacion) {
		this.publicacion = publicacion;
	}

	public Investigador getInvestigador() {
		return investigador;
	}

	public void setInvestigador(Investigador investigador) {
		this.investigador = investigador;
	}

	public String getNombrePublicacion() {
		String nombrePublicacion = "";
		if (tipo.equals("PUB_INT")) {
			nombrePublicacion = "Publicación internacional";
		} else if (tipo.equals("PUB_NAC")) {
			nombrePublicacion = "Publicación nacional";
		} else if (tipo.equals("PUB_LIB")) {
			nombrePublicacion = "Libro";
		} else if (tipo.equals("PUB_TES")) {
			nombrePublicacion = "Tesis de posgrado";
		} else if (tipo.equals("PUB_TES_PR")) {
			nombrePublicacion = "Tesis de pregrado";
		}
		return nombrePublicacion;
	}

	public String getAutores() {
		return autores;
	}

	public void setAutores(String autores) {
		this.autores = autores;
	}

}
