/*
 * Created on 11-jun-2013
 */
package co.edu.unal.hermes.vista.proyectos;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorElegirMod_JI_SEM_2014 extends ManejadorBase
{

	private String tipoBusqueda;
	private boolean panelAval = false;
	private boolean panelProyecto = false;
	private boolean panelInfoAval;
	private String avalSeleccionado;
	private Aval avalAsociar;
	private boolean mostrarAval;
	private Long codigoAval = 0L;
	private ArrayList<SelectItem> avalesInvestigador;
	private boolean puedeSubirArchivos = false;
	private boolean mostrarGuardarPryGrupo = false;
	private Grupo grupo;
	private boolean mostrarBotonGrupo;
	private boolean mostrarBotonSoloA1;
	
	public ManejadorElegirMod_JI_SEM_2014()
	{
		grupo = (Grupo)sesion.getAttribute("grupoJI");
		if (grupo.getCategoria().getId().equals("A1")) {
			mostrarBotonGrupo = true;
			mostrarBotonSoloA1 = true;	
		}else{
			mostrarBotonGrupo = false;
			mostrarBotonSoloA1 = true;
		}	

		sesion.removeAttribute("ManejadorElegirMod_JI_SEM_2014");
		mostrarInfoGrupo();
	}
	
	public void mostrarInfoGrupo(){
	
	System.out.println(grupo.getIdColciencias());
	} 

	public String cambiarForm()
	{
		String irForm = "";
		String tipoB = tipoBusqueda;
		if (tipoB.equals("Grupo")) {
			mostrarGuardarPryGrupo = true;
			panelAval = false;
			mostrarBotonGrupo=true;
			panelInfoAval = false;
			irForm = "irMod_JI_SEM";			
		}
		else
		{
			
			panelAval = true;
			mostrarGuardarPryGrupo = false;
			mostrarBotonGrupo=false;
			cargarAvales();
			irForm =  "";
		}
		
		return irForm;
	}


	
	
	public void buscarAval()
	{
		mostrarBotonGrupo = false;
		
		List listAval = null;

		if (avalSeleccionado != null && !avalSeleccionado.equals(""))
		{

			listAval = servicioGeneral.obtenerObjetos("select e from Aval e where e.id = " + avalSeleccionado + "");

		}
		if (listAval != null && listAval.size() > 0)
		{
			avalAsociar = (Aval) listAval.get(0);
			mostrarAval = true;
			sesion.setAttribute("AvalAsociar", avalAsociar);
			panelInfoAval = true;

		}
		else
		{
			FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Proyecto no encontrado", "Proyecto no encontrado"));
			mostrarAval = false;

		}

	}

	public void cargarAvales()
	{
		avalesInvestigador = new ArrayList<SelectItem>();
		personaActual = (Persona) sesion.getAttribute("persona");
		SelectItem item2 = new SelectItem("", "Seleccionar aval");
		avalesInvestigador.add(item2);
		if (personaActual != null)
		{

			String consulta = "select e.aviId, e.aviTitulo from Aval e where e.documento ='" + personaActual.getId().getDocumento() + "' and e.aviEstado='D' and e.aviAvalfacultad='S' and e.aviAvaldireccion='S'";
			// revisar consulta
			List<Object[]> listAval = servicioGeneral.obtenerObjetos(consulta);
			if (listAval.size() > 0)
			{

				for (int i = 0; i < listAval.size(); i++)
				{
					SelectItem item = new SelectItem(listAval.get(i)[0].toString(), listAval.get(i)[0].toString() + " - " + listAval.get(i)[1].toString());

					avalesInvestigador.add(item);
				}
			}
		}
	}
	
	public String registrarProyectoGrupo(){
		sesion.removeAttribute("Aval_JI");
		sesion.removeAttribute("idProyecto_JI");
		return "irMod_JI_SEM";
	}
	
	public String registrarProyectoAval(){
		sesion.setAttribute("Aval_JI", avalAsociar.getAviId());
		sesion.setAttribute("idProyecto_JI", avalAsociar.getIdProyecto());
		return "irMod_JI_SEM";
	}

	public String asociarAval()
	{
		sesion.setAttribute("Aval_JI", avalAsociar);
		sesion.setAttribute("idProyecto_JI", avalAsociar.getIdProyecto());

		System.out.println("******************************");
		System.out.println(((Aval) sesion.getAttribute("Aval")).getAviId());

		return "";
	}

	public String getTipoBusqueda()
	{
		return tipoBusqueda;
	}

	public void setTipoBusqueda(String tipoBusqueda)
	{
		this.tipoBusqueda = tipoBusqueda;
	}

	public boolean isPanelAval()
	{
		return panelAval;
	}

	public void setPanelAval(boolean panelAval)
	{
		this.panelAval = panelAval;
	}

	public boolean isPanelProyecto()
	{
		return panelProyecto;
	}

	public void setPanelProyecto(boolean panelProyecto)
	{
		this.panelProyecto = panelProyecto;
	}

	public Long getCodigoAval()
	{
		return codigoAval;
	}

	public void setCodigoAval(Long codigoAval)
	{
		this.codigoAval = codigoAval;
	}

	public String getAvalSeleccionado()
	{
		return avalSeleccionado;
	}

	public void setAvalSeleccionado(String avalSeleccionado)
	{
		this.avalSeleccionado = avalSeleccionado;
	}

	public boolean isMostrarAval()
	{
		return mostrarAval;
	}

	public void setMostrarAval(boolean mostrarAval)
	{
		this.mostrarAval = mostrarAval;
	}

	public ArrayList<SelectItem> getAvalesInvestigador()
	{
		return avalesInvestigador;
	}

	public void setAvalesInvestigador(ArrayList<SelectItem> avalesInvestigador)
	{
		this.avalesInvestigador = avalesInvestigador;
	}

	public boolean isPanelInfoAval()
	{
		return panelInfoAval;
	}

	public void setPanelInfoAval(boolean panelInfoAval)
	{
		this.panelInfoAval = panelInfoAval;
	}

	public Aval getAvalAsociar()
	{
		return avalAsociar;
	}

	public void setAvalAsociar(Aval avalAsociar)
	{
		this.avalAsociar = avalAsociar;
	}

	public boolean isPuedeSubirArchivos()
	{
		return puedeSubirArchivos;
	}

	public void setPuedeSubirArchivos(boolean puedeSubirArchivos)
	{
		this.puedeSubirArchivos = puedeSubirArchivos;
	}

	public boolean isMostrarGuardarPryGrupo()
	{
		return mostrarGuardarPryGrupo;
	}

	public void setMostrarGuardarPryGrupo(boolean mostrarGuardarPryGrupo)
	{
		this.mostrarGuardarPryGrupo = mostrarGuardarPryGrupo;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public boolean isMostrarBotonGrupo() {
		return mostrarBotonGrupo;
	}

	public void setMostrarBotonGrupo(boolean mostrarBotonGrupo) {
		this.mostrarBotonGrupo = mostrarBotonGrupo;
	}

	public boolean isMostrarBotonSoloA1()
	{
		return mostrarBotonSoloA1;
	}

	public void setMostrarBotonSoloA1(boolean mostrarBotonSoloA1)
	{
		this.mostrarBotonSoloA1 = mostrarBotonSoloA1;
	}

}
