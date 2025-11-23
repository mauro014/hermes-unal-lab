package co.edu.unal.hermes.vista;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;

public class ManejadorCursosAvaladosECP extends ManejadorBase {
	private List listaCursosAvaladosECP;
	private List<Proyecto> cursosFiltrados;
	private InvestigadorInterno investigadorInterno;
	private Proyecto cursoSeleccionado;
	public Investigador investigadorActual;
	public List<SelectItem> listaEstadosProyectoItem;

	public ManejadorCursosAvaladosECP() {
		listaCursosAvaladosECP = new ArrayList<Proyecto>();
		listaEstadosProyectoItem = new ArrayList<SelectItem>();

		Investigador investigadorActual = new Investigador();
		investigadorActual.setId(((Persona) sesion.getAttribute("persona"))
				.getId());
		this.investigadorInterno = servicioPersona
				.obtenerInvestigadorInterno(investigadorActual.getId());
		
		this.investigadorActual = servicioPersona
					.obtenerInvestigadorProyectos(((Persona) sesion
							.getAttribute("persona")).getId());

		cargarCursosECP();
		cargarEstados();
	}
	
	private void cargarEstados()
	{
		String hql = "select pp from EstadoProyecto pp where pp.id in ('A','AP','PB','CN','F')";
		List lista = servicioGeneral.obtenerObjetos(hql);
		
		if(lista.size() != 0 && listaEstadosProyectoItem != null)
		{	
			listaEstadosProyectoItem.add(new SelectItem("","Todos"));
			for(int i=0; i<lista.size();i++)
			{	
				EstadoProyecto estado = (EstadoProyecto) lista.get(i);
				listaEstadosProyectoItem.add(new SelectItem(estado.getNombre(), estado.getNombre()));
			}	
		}
	}

	private void cargarCursosECP() {
		try {
			
			//List listaInvestigadorProyecto = new ArrayList<InvestigadorProyecto>();
			//listaInvestigadorProyecto.addAll(investigadorActual.getProyectosInvestigador());
			
			//String sql = "select pp from Proyecto pp, Dependencia dd where pp.responsable.dependencia.facultad.id = dd.facultad.id and pp.responsable.dependencia.facultad.id = "+investigadorActual.getDependencia().getFacultad().getId();
			//String sql = "select pp from Proyecto pp where pp.responsable.id.documento = '1032434473'";
			
			//String sql = "select pp from Dependencia dd, InvestigadorProyecto pp where pp.investigador.dependencia.facultad.id = dd.facultad.id ";
			
//			String sql = "select pp from InvestigadorProyecto pp where pp.investigador.dependencia.id = '4036'";
			
			
			Persona coordinadorCurso = (Persona) sesion.getAttribute("persona");

			String coordinadorCursoCons = "select pp from GruposCursosECP pp where pp.proyecto.id = 19981 and pp.idCoordinadorGrupo like '%" + coordinadorCurso.getId().getDocumento() + "%'";
			List listaGrupos = servicioGeneral
					.obtenerObjetos(coordinadorCursoCons);
			
			if (listaGrupos != null && listaGrupos.size() > 0) {

					String sql = "select pp from InvestigadorProyecto pp, InvestigadorInterno ii " +
							"where pp.investigador.id.documento = ii.id.documento " +
							"and pp.investigador.id.tipoDocumento = ii.id.tipoDocumento " +
							"and pp.proyecto.id = 19981 " +
							"and pp.tipo.id = 'P' " +
							"and ii.dependencia.id = '3295'";
					
					List lista = servicioGeneral.obtenerObjetos(sql);
					
					for (int i = 0; i < lista.size(); i++) 
					{
						InvestigadorProyecto invPro = (InvestigadorProyecto) lista.get(i);
						Proyecto cursoECP = (Proyecto) invPro.getProyecto();
						String estado = cursoECP.getEstadoProyecto().getId();
						

						
						if((estado.equals("A") || estado.equals("AP") || estado.equals("PB") || estado.equals("CN") || estado.equals("F"))
								//&& cursoECP.getModalidad().getTipo().getId().equals("CFM")
								//&& cursoECP.getModalidad().getId() == 2
								//&& cursoECP.getModalidad().getId() == 405
								&& cursoECP.getTipoActividad().equals("ECP")
//								&& dpnCurso.equals(dpnInvestigador)
								)
						{	
//							System.out.print("ENTRAAAAA - dpnCurso: "+dpnCurso+" - dpnInvestigador: "+dpnInvestigador);
							listaCursosAvaladosECP.add(cursoECP);
							//listaFichasItems.add(new SelectItem(cursoECP.getId(), cursoECP.getNombre()));
						}	
					}			

			}else{
				
				String sql = "select pp from InvestigadorProyecto pp, InvestigadorInterno ii " +
						"where pp.investigador.id.documento = ii.id.documento " +
						"and pp.investigador.id.tipoDocumento = ii.id.tipoDocumento " +
						"and pp.proyecto.tipoActividad = 'ECP' " +
						"and pp.tipo.id = 'P' " +
						"and ii.dependencia.facultad.id = '"+ investigadorActual.getDependencia().getFacultad().getId()+"'";
				
				List lista = servicioGeneral.obtenerObjetos(sql);
				
				for (int i = 0; i < lista.size(); i++) 
				{
					InvestigadorProyecto invPro = (InvestigadorProyecto) lista.get(i);
					Proyecto cursoECP = (Proyecto) invPro.getProyecto();
					String estado = cursoECP.getEstadoProyecto().getId();
					
//					String dpnCurso="";
//					String dpnInvestigador="";
					
//					if(cursoECP.getResponsable().getDependencia() != null && investigadorActual.getDependencia() != null)
//					{	
//						//dpnCurso = cursoECP.getDependencia().getFacultad().getId();
//						dpnCurso = cursoECP.getResponsable().getDependencia().getFacultad().getId();
//						dpnInvestigador = investigadorActual.getDependencia().getFacultad().getId();
//					}
//					else
//						System.out.print("Dependencia NULA");
					
//					System.out.print("dpnCurso: "+dpnCurso+" - dpnInvestigador: "+dpnInvestigador);
					
					if((estado.equals("A") || estado.equals("AP") || estado.equals("PB") || estado.equals("CN") || estado.equals("F"))
							//&& cursoECP.getModalidad().getTipo().getId().equals("CFM")
							//&& cursoECP.getModalidad().getId() == 2
							//&& cursoECP.getModalidad().getId() == 405
							&& cursoECP.getTipoActividad().equals("ECP")
//							&& dpnCurso.equals(dpnInvestigador)
							)
					{	
//						System.out.print("ENTRAAAAA - dpnCurso: "+dpnCurso+" - dpnInvestigador: "+dpnInvestigador);
						listaCursosAvaladosECP.add(cursoECP);
						//listaFichasItems.add(new SelectItem(cursoECP.getId(), cursoECP.getNombre()));
					}	
				}
				
			}
			
			

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}
	
	public String editarCurso()
	{
		sesion.removeAttribute("ManejadorEditarCursoECP");
		
		sesion.setAttribute("cursoECP", cursoSeleccionado);
		
		return "editarCursoECP";
	}
	
	public String listaCurso()
	{
		sesion.removeAttribute("ManejadorEditarCursoECP");
		sesion.removeAttribute("ManejadorListaPreinscritosECP");
		sesion.removeAttribute("ManejadorDescuentosCursoECP");
		
		sesion.setAttribute("cursoECP", cursoSeleccionado);
		
		return "verListaInscritos";
	}
	
	public String descuentosCursoECP()
	{
		sesion.removeAttribute("ManejadorEditarCursoECP");
		sesion.removeAttribute("ManejadorListaPreinscritosECP");
		sesion.removeAttribute("ManejadorDescuentosCursoECP");
		
		sesion.setAttribute("cursoECP", cursoSeleccionado);
		
		return "verDescuentosCursosECP";
	}
	
	public String InfoPagoVCursoECP()
	{
		sesion.removeAttribute("ManejadorEditarCursoECP");
		sesion.removeAttribute("ManejadorListaPreinscritosECP");
		sesion.removeAttribute("ManejadorDescuentosCursoECP");
		
		sesion.setAttribute("cursoECP", cursoSeleccionado);
		
		return "verInfoPagoVirtualCursoECP";
	}
	
	public String InfoFacultadECP()
	{
		sesion.removeAttribute("ManejadorEditarCursoECP");
		sesion.removeAttribute("ManejadorListaPreinscritosECP");
		sesion.removeAttribute("ManejadorDescuentosCursoECP");
		
		sesion.setAttribute("cursoECP", cursoSeleccionado);
		
		return "verInfoFacultadECP";
	}
	
	public String divulgarCurso()
	{
		sesion.removeAttribute("ManejadorEditarCursoECP");
		sesion.removeAttribute("ManejadorListaPreinscritosECP");
		sesion.removeAttribute("ManejadorDescuentosCursoECP");
		
		sesion.setAttribute("cursoECP", cursoSeleccionado);
		
		return "divulgarCursoECP";
	}

//	private boolean validarVinculacionPersona(){	    	
//	    	InvestigadorInterno invI = investigadorInterno;
//	        if(invI != null && invI.getTipoVinculacion() != null && (invI.getTipoVinculacion().getId().equals("16") || invI.getTipoVinculacion().getId().equals("30") || invI.getTipoVinculacion().getId().equals("29"))){
//	            return true;
//	        }
//	        return false;
//	}
//	
//	public String solicitarAval()
//    {   
//	
//		if (validarVinculacionPersona()){
//			sesion.setAttribute("idAval",null);
//			sesion.removeAttribute("manejadorSolicitudAval");
//			sesion.removeAttribute("manejadorConsultarAval");
//			sesion.removeAttribute("manejadorSolicitarAval");
//			sesion.removeAttribute("manejadorSolicitarAvalFacultad");
//			sesion.removeAttribute("manejadorSolicitarAvalDireccion");		
//	     	return "solicitudAval";
//		}else{
//	        return "";
//		}
//		
//		
//			
//    }
//	
//	public String editarMovilidadAval() {
//		sesion.setAttribute("idAval",null);
//		sesion.removeAttribute("manejadorSolicitudAval");
//		sesion.removeAttribute("manejadorConsultarAval");
//		sesion.removeAttribute("manejadorSolicitarAval");
//		sesion.removeAttribute("manejadorSolicitarAvalFacultad");
//		sesion.removeAttribute("manejadorSolicitarAvalDireccion");
//		FacesContext context = FacesContext.getCurrentInstance();
//		borrarManejadoresInsercionProyecto();
//		Map map = context.getExternalContext().getRequestParameterMap();
//		Object o = (Object) map.get("idAvalConsulta");
//		Long id = Long.valueOf((String) o);
//		sesion.setAttribute("idAval", id);
//		return "avalarConsulta";
//	}
//
//	public String editarAval() {
//		sesion.setAttribute("idAval",null);
//		sesion.removeAttribute("manejadorSolicitudAval");
//		sesion.removeAttribute("manejadorConsultarAval");
//		sesion.removeAttribute("manejadorSolicitarAval");
//		sesion.removeAttribute("manejadorSolicitarAvalFacultad");
//		sesion.removeAttribute("manejadorSolicitarAvalDireccion");
//		FacesContext context = FacesContext.getCurrentInstance();
//		borrarManejadoresInsercionProyecto();
//		Map map = context.getExternalContext().getRequestParameterMap();
//		Object o = (Object) map.get("idAvalConsulta");
//		Long id = Long.valueOf((String) o);
//		sesion.setAttribute("idAval", id);
//		return "avalarEditar";
//	}

	public List getListaCursosAvaladosECP() {
		return listaCursosAvaladosECP;
	}

	public void setListaCursosAvaladosECP(List listaCursosAvaladosECP) {
		this.listaCursosAvaladosECP = listaCursosAvaladosECP;
	}

	public InvestigadorInterno getInvestigadorInterno() {
		return investigadorInterno;
	}

	public void setInvestigadorInterno(InvestigadorInterno investigadorInterno) {
		this.investigadorInterno = investigadorInterno;
	}

	public Proyecto getCursoSeleccionado() {
		return cursoSeleccionado;
	}

	public void setCursoSeleccionado(Proyecto cursoSeleccionado) {
		this.cursoSeleccionado = cursoSeleccionado;
	}

	public List<Proyecto> getCursosFiltrados() {
		return cursosFiltrados;
	}

	public void setCursosFiltrados(List<Proyecto> cursosFiltrados) {
		this.cursosFiltrados = cursosFiltrados;
	}

	public Investigador getInvestigadorActual() {
		return investigadorActual;
	}

	public void setInvestigadorActual(Investigador investigadorActual) {
		this.investigadorActual = investigadorActual;
	}

	public List<SelectItem> getListaEstadosProyectoItem() {
		return listaEstadosProyectoItem;
	}

	public void setListaEstadosProyectoItem(
			List<SelectItem> listaEstadosProyectoItem) {
		this.listaEstadosProyectoItem = listaEstadosProyectoItem;
	}
	
	
}
