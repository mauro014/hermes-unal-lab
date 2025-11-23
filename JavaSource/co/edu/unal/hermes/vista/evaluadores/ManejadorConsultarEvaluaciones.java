package co.edu.unal.hermes.vista.evaluadores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.ProyectoEvaluador;
import co.edu.unal.hermes.modelo.ProyectoEvaluadorVistaCert;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorConsultarEvaluaciones extends ManejadorBase implements Serializable{
	private List evaluacionesLista;
    private ProyectoEvaluadorVistaCert evaluacionSeleccionada;
    private ProyectoEvaluador proyectoEvaluado;
    
    public  ManejadorConsultarEvaluaciones(){    	    
    	Persona personaActual = servicioPersona.obtenerPersona(((Persona) sesion.getAttribute("persona")).getId());    	
    	
    	evaluacionesLista = new ArrayList<ProyectoEvaluadorVistaCert>();
    	evaluacionesLista = servicioProyecto.obtenerProyectosXEvaluador(personaActual.getId());
    }
    
    public String consultarEvaluacion(){
    	Long id = Long.parseLong(evaluacionSeleccionada.getIdEvaluacion());
    	proyectoEvaluado = (ProyectoEvaluador) servicioGeneral.obtenerObjetos("select pe from ProyectoEvaluador pe where pe.id = " + id).get(0);
    	sesion.setAttribute("proyectoEvaluador", proyectoEvaluado);
    	sesion.setAttribute("manejadorEvaluacion",new ManejadorEvaluacion());
		return "consultarEvaluacion";
    }

	public List getEvaluacionesLista() {
		return evaluacionesLista;
	}

	public void setEvaluacionesLista(List evaluacionesLista) {
		this.evaluacionesLista = evaluacionesLista;
	}

	public ProyectoEvaluadorVistaCert getEvaluacionSeleccionada() {
		return evaluacionSeleccionada;
	}

	public void setEvaluacionSeleccionada(ProyectoEvaluadorVistaCert evaluacionSeleccionada) {
		this.evaluacionSeleccionada = evaluacionSeleccionada;
	}

	public ProyectoEvaluador getProyectoEvaluado() {
		return proyectoEvaluado;
	}

	public void setProyectoEvaluado(ProyectoEvaluador proyectoEvaluado) {
		this.proyectoEvaluado = proyectoEvaluado;
	}
    
}