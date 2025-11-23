/**
 * @author Martha Liliana Correa O.
 * @date 10/11/2015
 */

package co.edu.unal.hermes.vista.administrador;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Pregunta;
import co.edu.unal.hermes.modelo.PreguntaClasificacion;

public class ManejadorCrearEditarPreguntas extends ManejadorAdministrarPreguntas{
	
	/**
     * 
     */
    private static final long serialVersionUID = -8339876891551006712L;
    private boolean esEdicion;
	private boolean esNuevo;
	private Pregunta pregunta;
	private SelectItem[] estadoItem  = { 
			new SelectItem("A", "Activa"), 
			new SelectItem("I", "Inactiva")};

	public ManejadorCrearEditarPreguntas(){
		Long id = null;
		personaActual = (Persona)sesion.getAttribute("persona");
		try {
			id = (Long) sesion.getAttribute("idPregunta");
		} catch (Exception e) {
			esEdicion = false;
		}		

		if(id!=null){
				
				String consulta = "select p from Pregunta p where p.id = '"+id+"'";
				List<Pregunta> preguntas = servicioGeneral.obtenerObjetos(Pregunta.class, consulta);

				if (!esListaVacia(preguntas)) {
					pregunta = (Pregunta) preguntas.get(0);
				}

		}else{
			esNuevo = true;
			pregunta = new Pregunta();
			PreguntaClasificacion clas = new PreguntaClasificacion();
			pregunta.setClasificacion(clas);
		}
		consultarClasificacionesActivasPreguntas();
	}

	
	public void guardar(){
		
		boolean validada = true;
		
		if(pregunta.getClasificacion().getId()==null || "".equals(pregunta.getClasificacion().getId().toString()) 
				|| pregunta.getClasificacion().getId() == 0L){
			FacesContext
			.getCurrentInstance()
			.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Debe seleccionar una clasificación para la pregunta.",
							""));
			validada = false;
		}else if(pregunta.getDescripcion()==null || "".equals(pregunta.getDescripcion().trim())){
			FacesContext
			.getCurrentInstance()
			.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Debe ingresar la pregunta frecuente.",
							""));
			validada = false;
		}else if(pregunta.getRespuesta()==null || "".equals(pregunta.getRespuesta().trim())){
			FacesContext
			.getCurrentInstance()
			.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Debe ingresar la respuesta para la pregunta.",
							""));
			validada = false;
		}else if(pregunta.getEstado()==null || "".equals(pregunta.getEstado().trim())){
			FacesContext
			.getCurrentInstance()
			.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Debe asignar un estado a la pregunta.",
							""));
			validada = false;
		}
		
		if(validada){
			
			if(pregunta.getRespuesta().length()>3800){
				pregunta.setRespuesta(pregunta.getRespuesta().substring(0, 3900));
			}
			
			//Guardar persona que crea o elimina
			if("I".equals(pregunta.getEstado())){
				pregunta.setPersonaElimina(personaActual);
			}else if("A".equals(pregunta.getEstado())){
				pregunta.setPersonaCrea(personaActual);
			}
			
			pregunta.setFecha(new Date());
			
			//Guardar pregunta
			servicioGeneral.guardarObjeto(pregunta);
						
			sesion.removeAttribute("manejadorAdministrarPreguntas");
			FacesContext
			.getCurrentInstance()
			.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_INFO,
							"La pregunta se ha guardado correctamente.",
							""));
		}else{
			return;
		}		
	}

	public boolean isEsEdicion() {
		return esEdicion;
	}

	public void setEsEdicion(boolean esEdicion) {
		this.esEdicion = esEdicion;
	}

	public boolean isEsNuevo() {
		return esNuevo;
	}

	public void setEsNuevo(boolean esNuevo) {
		this.esNuevo = esNuevo;
	}

	public SelectItem[] getEstadoItem() {
		return estadoItem;
	}

	public void setEstadoItem(SelectItem[] estadoItem) {
		this.estadoItem = estadoItem;
	}

	public Pregunta getPregunta() {
		return pregunta;
	}

	public void setPregunta(Pregunta pregunta) {
		this.pregunta = pregunta;
	}

}
