package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class GruposHorariosECP implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String dia;
    private GruposCursosECP grupo;
    private Proyecto proyecto;
    private String horaInicio;
    private String horaFin;
    
    public boolean equals(Object obj){
	    if(obj instanceof GruposHorariosECP){
	    	GruposHorariosECP gruHora = (GruposHorariosECP)obj;
	        if(gruHora.getDia().equals(dia) && gruHora.getGrupo().getId() == grupo.getId() && gruHora.getProyecto().getId() == proyecto.getId()){
	            return true;
	        }
	    }
		return false;
	}


    public String getHoraInicio() {
	return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
	this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
	return horaFin;
    }

    public void setHoraFin(String horaFin) {
	this.horaFin = horaFin;
    }

    public String obtenerDiaSemana(final Long dia) {
	String diaSemana = "";

	switch (dia.intValue()) {

	case 1:
	    diaSemana = "Lunes";
	    break;

	case 2:
	    diaSemana = "Martes";
	    break;

	case 3:
	    diaSemana = "Miércoles";
	    break;

	case 4:
	    diaSemana = "Jueves";
	    break;

	case 5:
	    diaSemana = "Viernes";
	    break;

	case 6:
	    diaSemana = "Sábado";
	    break;

	case 7:
	    diaSemana = "Domingo";
	    break;

	}

	return diaSemana;
    }

	public String getDia() {
		return dia;
	}


	public void setDia(String dia) {
		this.dia = dia;
	}


	public Proyecto getProyecto() {
		return proyecto;
	}


	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public GruposCursosECP getGrupo() {
		return grupo;
	}


	public void setGrupo(GruposCursosECP grupo) {
		this.grupo = grupo;
	}


	public Long getId() {
		return id;
	}

	

}
