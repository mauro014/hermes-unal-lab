package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class OfertaHorariosECP implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    private IdHorariosECP id;
    private String horaInicio;
    private String horaFin;

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

    public IdHorariosECP getId() {
	return id;
    }

    public void setId(IdHorariosECP id) {
	this.id = id;
    }

}
