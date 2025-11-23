package co.edu.unal.hermes.vista;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Proyecto;

public class ManejadorEditarInfoFacultadECP extends ManejadorBase{
	
	private Proyecto curso;
	public List<SelectItem> listaEstadosProyectoItem;

	public ManejadorEditarInfoFacultadECP()
	{	
		//sesion.removeAttribute("ManejadorCursosAvaladosECP");
		curso = (Proyecto) sesion.getAttribute("cursoECP");

		listaEstadosProyectoItem = new ArrayList<SelectItem>();
		
		if(curso != null)
		{
			cargarEstadosCurso();
		}	
		else
			System.out.println("Curso parametro en sesion NULO");
		

		
	}
	
	
	public void guardar()
	{
		
	
		FacesMessage msg = new FacesMessage( FacesMessage.SEVERITY_INFO, "","La informacion del curso ha sido guardada correctamente");
		FacesContext.getCurrentInstance().addMessage("msgs", msg);

	}
	
	public void cargarEstadosCurso()
	{
		String hql = "select pp from EstadoProyecto pp where pp.id in ('A','AP','PB','CN','F')";
		List lista = servicioGeneral.obtenerObjetos(hql);
		
		if(lista.size() != 0 && listaEstadosProyectoItem != null)
		{	
			for(int i=0; i<lista.size();i++)
			{	
				EstadoProyecto estado = (EstadoProyecto) lista.get(i);
				listaEstadosProyectoItem.add(new SelectItem(estado.getId(), estado.getNombre()));
			}	
		}	
	}


	public Proyecto getCurso() {
		return curso;
	}


	public void setCurso(Proyecto curso) {
		this.curso = curso;
	}


	public List<SelectItem> getListaEstadosProyectoItem() {
		return listaEstadosProyectoItem;
	}


	public void setListaEstadosProyectoItem(
			List<SelectItem> listaEstadosProyectoItem) {
		this.listaEstadosProyectoItem = listaEstadosProyectoItem;
	}
	

}