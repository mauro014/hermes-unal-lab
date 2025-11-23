package co.edu.unal.hermes.vista;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.DescuentoProyectoECP;
import co.edu.unal.hermes.modelo.Descuento_ECP;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Proyecto;

public class ManejadorDescuentosCursoECP extends ManejadorBase{
	
	private Proyecto curso;
	public List<SelectItem> listaEstadosProyectoItem;
	public List<DescuentoProyectoECP> listaDescuentosProyecto;
	public List<Descuento_ECP> listaDescuentosDeterminados;

	public ManejadorDescuentosCursoECP()
	{	
		//sesion.removeAttribute("ManejadorCursosAvaladosECP");
		curso = (Proyecto) sesion.getAttribute("cursoECP");

		listaEstadosProyectoItem = new ArrayList<SelectItem>();
		
		listaDescuentosProyecto = new ArrayList<DescuentoProyectoECP>();
		listaDescuentosProyecto.addAll(curso.getDescuentosECP());
		
		if(listaDescuentosProyecto.size()==0)
			inicializarDescuentos();
		
		if(curso != null)
		{
			cargarEstadosCurso();
		}	
		else
			System.out.println("Curso parametro en sesion NULO");
		
		
		
	}
	
//	public void actualizarNumDisponible()
//	{
//		
//	}
	
	public void inicializarDescuentos()
	{
		String query = "select pp from Descuento_ECP pp";
		//List lista = servicioGeneral.obtenerObjetos(query);
		ArrayList<Descuento_ECP> listaDescuentosDeterminados = new ArrayList<Descuento_ECP>();
		listaDescuentosDeterminados = (ArrayList<Descuento_ECP>) servicioGeneral.obtenerObjetos(query);
		
		if(listaDescuentosDeterminados != null)
		{
			for(int i=0;i<listaDescuentosDeterminados.size();i++)
			{
				DescuentoProyectoECP descuentoProy = new DescuentoProyectoECP();
				descuentoProy.setDescuento(listaDescuentosDeterminados.get(i));
				descuentoProy.setNumParticipantesDisponibles(0L);
				descuentoProy.setNumParticipantesEsperado(0L);
				descuentoProy.setPorcentaje(0L);
				descuentoProy.setProyecto(curso);
				
				listaDescuentosProyecto.add(descuentoProy);
				
			}
		}
		
	}
	
	public void guardar()
	{
		try
		{
			Set descuentosSet = new HashSet();
			descuentosSet.addAll(listaDescuentosProyecto);
			
			curso.setDescuentosECP(descuentosSet);
			
			servicioGeneral.guardarObjeto(curso);
			FacesMessage msg = new FacesMessage( FacesMessage.SEVERITY_INFO, "","La informacion de los descuentos ha sido registrada correctamente");
			FacesContext.getCurrentInstance().addMessage("growl", msg);
		}
		catch (Exception e) {
			System.out.println("Proyecto - Descuentos No Guardados" + e.getMessage());
			FacesMessage msg = new FacesMessage( FacesMessage.SEVERITY_INFO, "","Ha ocurrido algun problema al guardar los descuentos");
			FacesContext.getCurrentInstance().addMessage("growl", msg);
		}
		
	
		

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

	public List<DescuentoProyectoECP> getListaDescuentosProyecto() {
		return listaDescuentosProyecto;
	}

	public void setListaDescuentosProyecto(
			List<DescuentoProyectoECP> listaDescuentosProyecto) {
		this.listaDescuentosProyecto = listaDescuentosProyecto;
	}

	public List<Descuento_ECP> getListaDescuentosDeterminados() {
		return listaDescuentosDeterminados;
	}

	public void setListaDescuentosDeterminados(
			List<Descuento_ECP> listaDescuentosDeterminados) {
		this.listaDescuentosDeterminados = listaDescuentosDeterminados;
	}

	

}