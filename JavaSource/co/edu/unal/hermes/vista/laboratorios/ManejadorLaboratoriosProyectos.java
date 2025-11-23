package co.edu.unal.hermes.vista.laboratorios;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.ProyectoLaboratorioVista;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleEquipos;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioProyectoEquipo;

public class ManejadorLaboratoriosProyectos extends ManejadorLaboratorios {
	
	private static final long serialVersionUID = 7477723662388350301L;
	private ProyectoLaboratorioVista proyectoInvestigacionSel;
	private ArrayList<ProyectoLaboratorioVista> listaProyectos;
	private ArrayList<ProyectoLaboratorioVista> listaProyectosFiltrados;
	
	private List<LaboratorioDetalleEquipos> listaEquiposLaboratorio;
	private List<LaboratorioProyectoEquipo> listaEquiposProyectoVista;
	private ArrayList<LaboratorioProyectoEquipo> listaEquiposFiltrados;

	public ManejadorLaboratoriosProyectos() {
		idManejador = PROYECTOS;
		listaProyectos = (ArrayList<ProyectoLaboratorioVista>) servicioGeneral.consultaProyectosAsociadosLaboratorio(laboratorioActual.getId());
		obtenerEquiposLaboratorio();
		listaEquiposProyectoVista = new ArrayList<LaboratorioProyectoEquipo>();
	}
	
	public void obtenerEquiposLaboratorio() {
		Long idLab = laboratorioActual.getId();
		String hqlQuery = "FROM LaboratorioDetalleEquipos WHERE laboratorio = " + idLab + " AND enUso = 1 ORDER BY equipo";
		listaEquiposLaboratorio = servicioGeneral.obtenerObjetos(LaboratorioDetalleEquipos.class, hqlQuery);
	}
	
//	public void consultarProyectoInvestigacion() {
//		System.out.println("proyectoInvestigacionSel: " + proyectoInvestigacionSel.getId() + " - " + proyectoInvestigacionSel.getNombre());
//	}
	
	public void equiposProyectoInvestigacion() {
		listaEquiposProyectoVista = new ArrayList<LaboratorioProyectoEquipo>();
		Long idLab = laboratorioActual.getId();
		Long idPry = Long.parseLong(proyectoInvestigacionSel.getId()); 
		String hqlQuery = "FROM LaboratorioProyectoEquipo WHERE laboratorio = " + idLab + " AND proyecto = " + idPry;
		System.out.println("hqlQuery:" + hqlQuery);
		List<LaboratorioProyectoEquipo> listaEquiposProyectoSel = servicioGeneral.obtenerObjetos(LaboratorioProyectoEquipo.class, hqlQuery);
		
//		for (LaboratorioProyectoEquipo equipo : listaEquiposProyectoSel) {
//			System.out.println("equipo:" + equipo.getEquipo().getId() + " LAB: " + equipo.getLaboratorio().getId() + " PRY: " + equipo.getProyecto().getId());
//		}
		
		Proyecto pry = servicioGeneral.obtenerObjetoXID(Proyecto.class, proyectoInvestigacionSel.getId()).get(0);
		for (LaboratorioDetalleEquipos equipo : listaEquiposLaboratorio) {
//			Boolean asociado = esEquipoAsociado(listaEquiposProyectoSel, equipo.getId());
			LaboratorioProyectoEquipo equipoAsociado = esEquipoAsociado(listaEquiposProyectoSel, equipo.getId());
			if(esNulo(equipoAsociado))
				listaEquiposProyectoVista.add(new LaboratorioProyectoEquipo(laboratorioActual, pry, equipo, false));
			else {
				equipoAsociado.setAsociado(true);
				listaEquiposProyectoVista.add(equipoAsociado);
			}
		}
		
		for (LaboratorioProyectoEquipo equipo : listaEquiposProyectoVista) {
			System.out.println("ID: " + equipo.getId() + " equipo:" + equipo.getEquipo().getId() + " LAB: " + equipo.getLaboratorio().getId() + " PRY: " + equipo.getProyecto().getId() + " ASOCIADO: " + equipo.getAsociado());
		}
	}
	
//	public Boolean esEquipoAsociado(List<LaboratorioProyectoEquipo> listaEquiposProyectoSel, Long idEquipo) {
//		for (LaboratorioProyectoEquipo equipo : listaEquiposProyectoSel) {
//			System.out.println("ASOCIADO: equipo.getId() " + equipo.getId() + " - idEquipo: " + idEquipo);
//			if(equipo.getEquipo().getId().equals(idEquipo))
//				return true;
//		}
//		return false;
//	}
	
	public LaboratorioProyectoEquipo esEquipoAsociado(List<LaboratorioProyectoEquipo> listaEquiposProyectoSel, Long idEquipo) {
		for (LaboratorioProyectoEquipo equipo : listaEquiposProyectoSel) {
			if(equipo.getEquipo().getId().equals(idEquipo))
				return equipo;
		}
		return null;
	}
	
	public void guardarEquiposProyectoSeleccionado(){
		RequestContext context = RequestContext.getCurrentInstance();
		
		for (LaboratorioProyectoEquipo equipoLPE : listaEquiposProyectoVista) {
			//Equipos eliminados
			if(!equipoLPE.getAsociado() && !esNulo(equipoLPE.getId()))
				servicioGeneral.eliminarObjeto(equipoLPE);
				
			//Equipos agregados
			if(equipoLPE.getAsociado() && esNulo(equipoLPE.getId()))
				servicioGeneral.guardarObjeto(equipoLPE);
		}
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Lista de equipos actualizada para el proyecto " + proyectoInvestigacionSel.getId(), "");
		FacesContext.getCurrentInstance().addMessage("btnGuardarEquiposProyecto", msg);
		
		context.execute("dialogEquipos.hide();");
	}

	@Override
	public String salirGuardar() {
		if (validar()) {
			guardar();
			return (salir());
		} else {
			return null;
		}
	}

	public void guardar() {
		guardarLaboratorioActual(idManejador);
		calcularCompletitud();
	}

	@Override
	public String siguiente() {
		if (validar()) {
			if (idManejador == laboratorioActual.getEtapaRegistro().intValue()) {
				laboratorioActual.setEtapaRegistro(new Integer(
						(laboratorioActual.getEtapaRegistro()).intValue() + 1));
			}
			guardar();
			limpiarSesion();
			sesion.setAttribute("Laboratorio", laboratorioActual);
			sesion.setAttribute("solicitudLaboratorio", solicitudLab);
			sesion.removeAttribute("ManejadorMenuFormularioLaboratorios");
			return "laboratorioInvestigacion";
		} else {
			return null;
		}
	}

	public Boolean validar() {
		return true;
	}

	public ProyectoLaboratorioVista getProyectoInvestigacionSel() {
		return proyectoInvestigacionSel;
	}

	public void setProyectoInvestigacionSel(ProyectoLaboratorioVista proyectoInvestigacionSel) {
		this.proyectoInvestigacionSel = proyectoInvestigacionSel;
	}

	public ArrayList<ProyectoLaboratorioVista> getListaProyectos() {
		return listaProyectos;
	}

	public void setListaProyectos(ArrayList<ProyectoLaboratorioVista> listaProyectos) {
		this.listaProyectos = listaProyectos;
	}

	public ArrayList<ProyectoLaboratorioVista> getListaProyectosFiltrados() {
		return listaProyectosFiltrados;
	}

	public void setListaProyectosFiltrados(ArrayList<ProyectoLaboratorioVista> listaProyectosFiltrados) {
		this.listaProyectosFiltrados = listaProyectosFiltrados;
	}

	public List<LaboratorioDetalleEquipos> getListaEquiposLaboratorio() {
		return listaEquiposLaboratorio;
	}

	public void setListaEquiposLaboratorio(List<LaboratorioDetalleEquipos> listaEquiposLaboratorio) {
		this.listaEquiposLaboratorio = listaEquiposLaboratorio;
	}

	public List<LaboratorioProyectoEquipo> getListaEquiposProyectoVista() {
		return listaEquiposProyectoVista;
	}

	public void setListaEquiposProyectoVista(List<LaboratorioProyectoEquipo> listaEquiposProyectoVista) {
		this.listaEquiposProyectoVista = listaEquiposProyectoVista;
	}

	public ArrayList<LaboratorioProyectoEquipo> getListaEquiposFiltrados() {
		return listaEquiposFiltrados;
	}

	public void setListaEquiposFiltrados(ArrayList<LaboratorioProyectoEquipo> listaEquiposFiltrados) {
		this.listaEquiposFiltrados = listaEquiposFiltrados;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
