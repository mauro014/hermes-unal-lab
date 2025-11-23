/*
 * Created on 03-ago-2005
 */
package co.edu.unal.hermes.modelo;

import java.util.Date;


/**
 * Guarda un Histórico de edición del grupo por formulario 
 */
public class HistoricoFormularioGrupo {

    private Long id;
    private Grupo grupo;
    private String numero;
    private Persona responsable;
    private Date fecha;
    
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

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Persona getResponsable() {
		return responsable;
	}

	public void setResponsable(Persona responsable) {
		this.responsable = responsable;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public String getNombreFormulario() {
		String nombre = "";
		if(numero!=null) {
			if(numero.equals("1")) {
				nombre = "Información General";
			}else if(numero.equals("2")) {
				nombre = "Integrantes";
			}else if(numero.equals("3")) {
				nombre = "Áreas y Líneas de Investigación";
			}else if (numero.equals("4")){
				nombre = "Información espefícica";
			}else {
				nombre="Desconocido";
			}
		}			
		return nombre;
	}
    
}
