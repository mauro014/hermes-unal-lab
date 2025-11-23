/**
 * @author Ing Hernán Darío Bernal Parra
 *         http://www.unal.edu.co/quipu/documentos/pglobal.xls
 */

// TODO: ARREGLAR EL CARGUE DEL PLAN GLOBAL DE DESARROLLO
package co.edu.unal.hermes.vista.proyectos.manejadorproyecto;

import java.sql.SQLException;
import java.util.List;

import javax.faces.component.UIData;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.myfaces.custom.navmenu.NavigationMenuItem;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.AgendaConocimiento;
import co.edu.unal.hermes.modelo.AgendaProyecto;
import co.edu.unal.hermes.vista.proyectos.ManejadorMenuFormularios;

public class ManejadorAgendaConocimientos extends ManejadorProyecto {

	private String mensajeValidacion;
	private String errorValidacion;
	private SelectItem[] agendaConocimiento1Item;
	private SelectItem[] agendaConocimiento2Item;
	private SelectItem[] agendaConocimiento3Item;
	
	private SelectItem[] agendaSubConocimiento1Item;
	private SelectItem[] agendaSubConocimiento2Item;
	private SelectItem[] agendaSubConocimiento3Item;

	private String agendaConocimiento1 = "";
	private String agendaConocimiento2 = "";
	private String agendaConocimiento3 = "";
	
	private String agendaSubConocimiento1 = "";
	private String agendaSubConocimiento2 = "";
	private String agendaSubConocimiento3 = "";

	private String textoBuscar;


	private UIData tablaAreas;

	public ManejadorAgendaConocimientos() {
		super();
		idManejador = AGENDAS;
		

		proyectoActual = servicioProyecto.obtenerProyecto(proyectoActual
				.getId(), ProyectoDAOHibernate.AGENDAS);
		cargarValoresIniciales();

	}

	private void cargarNivelPadreUno() {
		List listaArea = servicioGeneral.obtenerListaObjetos("AgendaConocimiento where padre.id.padre.id = '0' ");
		agendaConocimiento1Item = new SelectItem[listaArea.size()+1];
		agendaConocimiento1Item[0] = new SelectItem("", "seleccione");
		for (int i = 0; i < listaArea.size(); i++) {
			AgendaConocimiento ac = (AgendaConocimiento) listaArea.get(i);
			agendaConocimiento1Item[i+1] = new SelectItem(ac.getId(), ac.getNombre());
		}
		
		
	}
	
	private void cargarNivelPadreDos() {
		List listaArea = servicioGeneral.obtenerListaObjetos("AgendaConocimiento where padre.id.padre.id = '0' ");
		agendaConocimiento2Item = new SelectItem[listaArea.size()+1];
		agendaConocimiento2Item[0] = new SelectItem("", "seleccione");
		for (int i = 0; i < listaArea.size(); i++) {
			AgendaConocimiento ac = (AgendaConocimiento) listaArea.get(i);
			agendaConocimiento2Item[i+1] = new SelectItem(ac.getId(), ac.getNombre());
		}
		
		
	}
	
	private void cargarNivelPadreTres() {
		List listaArea = servicioGeneral.obtenerListaObjetos("AgendaConocimiento where padre.id.padre.id = '0' ");
		agendaConocimiento3Item = new SelectItem[listaArea.size()+1];
		agendaConocimiento3Item[0] = new SelectItem("", "seleccione");
		for (int i = 0; i < listaArea.size(); i++) {
			AgendaConocimiento ac = (AgendaConocimiento) listaArea.get(i);
			agendaConocimiento3Item[i+1] = new SelectItem(ac.getId(), ac.getNombre());
		}
		
	}
	
	private void cargarSubNivelPadreUno(String padre) {
		List listaArea = servicioGeneral.obtenerListaObjetos("AgendaConocimiento where padre.id.padre.id = '"+padre+"' ");
		agendaSubConocimiento1Item = new SelectItem[listaArea.size()+1];
		agendaSubConocimiento1Item[0] = new SelectItem("", "seleccione");
		for (int i = 0; i < listaArea.size(); i++) {
			AgendaConocimiento ac = (AgendaConocimiento) listaArea.get(i);
			agendaSubConocimiento1Item[i+1] = new SelectItem(ac.getId(), ac.getNombre());
		}		
	}
	
	private void cargarSubNivelPadreDos(String padre) {
		List listaArea = servicioGeneral.obtenerListaObjetos("AgendaConocimiento where padre.id.padre.id = '"+padre+"' ");
		agendaSubConocimiento2Item = new SelectItem[listaArea.size()+1];
		agendaSubConocimiento2Item[0] = new SelectItem("", "seleccione");
		for (int i = 0; i < listaArea.size(); i++) {
			AgendaConocimiento ac = (AgendaConocimiento) listaArea.get(i);
			agendaSubConocimiento2Item[i+1] = new SelectItem(ac.getId(), ac.getNombre());
		}		
	}
	
	private void cargarSubNivelPadreTres(String padre) {
		List listaArea = servicioGeneral.obtenerListaObjetos("AgendaConocimiento where padre.id.padre.id = '"+padre+"' ");
		agendaSubConocimiento3Item = new SelectItem[listaArea.size()+1];
		agendaSubConocimiento3Item[0] = new SelectItem("", "seleccione");
		for (int i = 0; i < listaArea.size(); i++) {
			AgendaConocimiento ac = (AgendaConocimiento) listaArea.get(i);
			agendaSubConocimiento3Item[i+1] = new SelectItem(ac.getId(), ac.getNombre());
		}		
	}
	
	// DEFINICION DE FUNCIONES BASICAS
	protected void cargarValoresIniciales() {
			
		cargarNivelPadreUno();
		cargarNivelPadreDos();
		cargarNivelPadreTres();
		cargarSubNivelPadreUno("-1");
		cargarSubNivelPadreDos("-1");
		cargarSubNivelPadreTres("-1");
		
		if(this.proyectoActual.getAgendas() != null && this.proyectoActual.getAgendas().size() > 0) {
			try {
				AgendaProyecto uno = this.proyectoActual.obtenerAgenda("1");
				agendaConocimiento1 = uno.getAgenda().getPadre().getId();
				agendaSubConocimiento1 = uno.getAgenda().getId();
			} catch (Exception e) {
				agendaConocimiento1 = "";
				agendaSubConocimiento1 = "";
				cargarSubNivelPadreUno("-1");
			}
			
			try {
				AgendaProyecto dos = this.proyectoActual.obtenerAgenda("2");
				agendaConocimiento2 = dos.getAgenda().getPadre().getId();
				agendaSubConocimiento2 = dos.getAgenda().getId();

			} catch (Exception e) {
				agendaConocimiento2 = "";
				agendaSubConocimiento2 = "";
				cargarSubNivelPadreDos("-1");
			}
			
			try {
				AgendaProyecto tres = this.proyectoActual.obtenerAgenda("3");
				agendaConocimiento3 = tres.getAgenda().getPadre().getId();
				agendaSubConocimiento3 = tres.getAgenda().getId();
			} catch (Exception e) {
				agendaConocimiento3 = "";
				agendaSubConocimiento3 = "";
				cargarSubNivelPadreTres("-1");
			}
					
			cargarSubNivelPadreUno(agendaConocimiento1);
			cargarSubNivelPadreDos(agendaConocimiento2);
			cargarSubNivelPadreTres(agendaConocimiento3);
		}
	
	}
	
	public void cambiarAreaNivel1(ValueChangeEvent event) {				
		cargarSubNivelPadreUno((String)event.getNewValue()) ;
	}
	
	public void cambiarAreaNivel2(ValueChangeEvent event) {				
		cargarSubNivelPadreDos((String)event.getNewValue()) ;	
	}
	
	public void cambiarAreaNivel3(ValueChangeEvent event) {				
		cargarSubNivelPadreTres((String)event.getNewValue()) ;	
	}


	public String atras() {
		// ///////MODIFICADO GIOVANNI
		ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion
				.getAttribute("manejadorMenuFormularios");
		boolean bandera = false;
		if (man.getItemProyecto() != null) {
			NavigationMenuItem lis[] = man.getItemProyecto()[0]
					.getNavigationMenuItems();
			if (lis != null) {
				for (int i = lis.length - 1; i >= 0; i--) {
					if (bandera) {
						if (lis[i].isRendered()) {

							return lis[i].getAction();
						}
					}

					if (lis[i].getAction().equals("irAgendaConocimiento")) {
						bandera = true;
					}

				}
			}
		}
		// ////////////////
		return "irBibliografia";
	}
	
	public boolean validadAgenda(){
		boolean bandera = true;
		this.errorValidacion = "";
		
		if(agendaSubConocimiento1.equals(agendaSubConocimiento2) || agendaSubConocimiento1.equals(agendaSubConocimiento3) || agendaSubConocimiento2.equals(agendaSubConocimiento3)){
			this.errorValidacion = "Debe seleccionar tres sub-áreas diferentes";
			bandera = false;
		}
		
		if(agendaConocimiento1.length()<=0 || agendaConocimiento2.length()<=0 || agendaConocimiento3.length()<=0 || agendaSubConocimiento1.length()<=0 || agendaSubConocimiento2.length()<=0 || agendaSubConocimiento3.length()<=0){
			this.errorValidacion = "Debe seleccionar las tres áreas y sub-áreas de las agendas del conocimiento";
			bandera = false;
		}
		
		
		
		return bandera;
	}

	public String salir() {
		sesion.removeAttribute("proyecto");
		borrarManejadoresInsercionProyecto();
		return "misProyectos";
	}

	public String salirGuardar() {

		// ///MODIFICADO GIOVANNI
		String link = "";
		ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion
				.getAttribute("manejadorMenuFormularios");
		boolean bandera = false;
		int pos = 0;
		if (man.getItemProyecto() != null) {
			NavigationMenuItem lis[] = man.getItemProyecto()[0]
					.getNavigationMenuItems();
			if (lis != null) {
				for (int i = 0; i < lis.length; i++) {
					if (bandera) {
						if (lis[i].isRendered()) {
							// sesion.removeAttribute("manejadorMenuFormularios");
							link = lis[i].getAction();
							break;
						}
					}

					if (lis[i].getAction().equals("irAgendaConocimiento")) {
						bandera = true;
					}
					if (lis[i].isRendered()) {
						pos++;
					}
				}
			}
		}

		if ((proyectoActual.getEstadoProyecto().getId()).equals("I")
				&& (pos - 1) >= proyectoActual.getFase().intValue()) {
			proyectoActual.setFase(new Integer(proyectoActual.getFase()
					.intValue() + 1));
		}
		if (validadAgenda()) {
			
			List listaArea1 = servicioGeneral.obtenerListaObjetos("AgendaConocimiento where id = '"+this.agendaSubConocimiento1+"' ");
			List listaArea2 = servicioGeneral.obtenerListaObjetos("AgendaConocimiento where id = '"+this.agendaSubConocimiento2+"' ");
			List listaArea3 = servicioGeneral.obtenerListaObjetos("AgendaConocimiento where id = '"+this.agendaSubConocimiento3+"' ");
			
			try {
				servicioGeneral.eliminar("delete HER_PROYECTO_AGENDA WHERE PRY_ID=" + String.valueOf(proyectoActual.getId()));
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			if(listaArea1 != null && listaArea1.size()>0){
				AgendaConocimiento agenda1 = (AgendaConocimiento) listaArea1.get(0);
				AgendaProyecto agenda = new AgendaProyecto();
				agenda.setAgenda(agenda1);
				agenda.setProyecto(proyectoActual);
				agenda.setOrden("1");
				servicioGeneral.guardarObjeto(agenda);			
			}
			
			if(listaArea2 != null && listaArea2.size()>0){
				AgendaConocimiento agenda2 = (AgendaConocimiento) listaArea2.get(0);
				AgendaProyecto agenda = new AgendaProyecto();
				agenda.setAgenda(agenda2);
				agenda.setProyecto(proyectoActual);
				agenda.setOrden("2");
				servicioGeneral.guardarObjeto(agenda);
				
			}
			
			if(listaArea3 != null && listaArea3.size()>0){
				AgendaConocimiento agenda3 = (AgendaConocimiento) listaArea3.get(0);
				AgendaProyecto agenda = new AgendaProyecto();
				agenda.setAgenda(agenda3);
				agenda.setProyecto(proyectoActual);
				agenda.setOrden("3");
				servicioGeneral.guardarObjeto(agenda);				
			}
			
			servicioProyecto.ingresarProyecto(proyectoActual);
			sesion.removeAttribute("proyecto");
			sesion.removeAttribute("manejadorMenuFormularios");
			sesion.removeAttribute("manejadorAgendaConocimientos");
			borrarManejadoresInsercionProyecto();
			return "misProyectos";
		}
		return "";
	}

	public String siguiente() {

		// ///MODIFICADO GIOVANNI
		String link = "";
		ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion
				.getAttribute("manejadorMenuFormularios");
		boolean bandera = false;
		int pos = 0;
		if (man.getItemProyecto() != null) {
			NavigationMenuItem lis[] = man.getItemProyecto()[0]
					.getNavigationMenuItems();
			if (lis != null) {
				for (int i = 0; i < lis.length; i++) {
					if (bandera) {
						if (lis[i].isRendered()) {
							// sesion.removeAttribute("manejadorMenuFormularios");
							link = lis[i].getAction();
							break;
						}
					}

					if (lis[i].getAction().equals("irAgendaConocimiento")) {
						bandera = true;
					}
					if (lis[i].isRendered()) {
						pos++;
					}
				}
			}
		}

		if ((proyectoActual.getEstadoProyecto().getId()).equals("I")
				&& (pos - 1) == proyectoActual.getFase().intValue()) {
			proyectoActual.setFase(new Integer(proyectoActual.getFase()
					.intValue() + 1));
		}
		if (validadAgenda()) {

			List listaArea1 = servicioGeneral.obtenerListaObjetos("AgendaConocimiento where id = '"+this.agendaSubConocimiento1+"' ");
			List listaArea2 = servicioGeneral.obtenerListaObjetos("AgendaConocimiento where id = '"+this.agendaSubConocimiento2+"' ");
			List listaArea3 = servicioGeneral.obtenerListaObjetos("AgendaConocimiento where id = '"+this.agendaSubConocimiento3+"' ");
			
			try {
				servicioGeneral.eliminar("delete HER_PROYECTO_AGENDA WHERE PRY_ID=" + String.valueOf(proyectoActual.getId()));
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			if(listaArea1 != null && listaArea1.size()>0){
				AgendaConocimiento agenda1 = (AgendaConocimiento) listaArea1.get(0);
				AgendaProyecto agenda = new AgendaProyecto();
				agenda.setAgenda(agenda1);
				agenda.setProyecto(proyectoActual);
				agenda.setOrden("1");
				servicioGeneral.guardarObjeto(agenda);			
			}
			
			if(listaArea2 != null && listaArea2.size()>0){
				AgendaConocimiento agenda2 = (AgendaConocimiento) listaArea2.get(0);
				AgendaProyecto agenda = new AgendaProyecto();
				agenda.setAgenda(agenda2);
				agenda.setProyecto(proyectoActual);
				agenda.setOrden("2");
				servicioGeneral.guardarObjeto(agenda);
				
			}
			
			if(listaArea3 != null && listaArea3.size()>0){
				AgendaConocimiento agenda3 = (AgendaConocimiento) listaArea3.get(0);
				AgendaProyecto agenda = new AgendaProyecto();
				agenda.setAgenda(agenda3);
				agenda.setProyecto(proyectoActual);
				agenda.setOrden("3");
				servicioGeneral.guardarObjeto(agenda);				
			}
			
			servicioProyecto.ingresarProyecto(proyectoActual);
			sesion.setAttribute("proyecto", proyectoActual);

			sesion.removeAttribute("manejadorAgendaConocimientos");
			// ///////MODIFICADO GIOVANNI
			man = (ManejadorMenuFormularios) sesion
					.getAttribute("manejadorMenuFormularios");
			bandera = false;
			if (man.getItemProyecto() != null) {
				NavigationMenuItem lis[] = man.getItemProyecto()[0]
						.getNavigationMenuItems();
				if (lis != null) {
					for (int i = 0; i < lis.length; i++) {
						if (bandera) {
							if (lis[i].isRendered()) {
								sesion
										.removeAttribute("manejadorMenuFormularios");
								borrarManejadoresInsercionProyecto();
								return lis[i].getAction();
							}
						}

						if (lis[i].getAction().equals("irAgendaConocimiento")) {
							bandera = true;
						}

					}
				}
			}
			// ////////////////
			sesion.removeAttribute("manejadorMenuFormularios");
			return "irProductos";
		}
		return "";
	}

	public String getMensajeValidacion() {
		return mensajeValidacion;
	}

	public void setMensajeValidacion(String mensajeValidacion) {
		this.mensajeValidacion = mensajeValidacion;
	}

	public UIData getTablaAreas() {
		return tablaAreas;
	}

	public void setTablaAreas(UIData tablaAreas) {
		this.tablaAreas = tablaAreas;
	}

	public String getTextoBuscar() {
		return textoBuscar;
	}

	public void setTextoBuscar(String textoBuscar) {
		this.textoBuscar = textoBuscar;
	}

	public String getErrorValidacion() {
		return errorValidacion;
	}

	public void setErrorValidacion(String errorValidacion) {
		this.errorValidacion = errorValidacion;
	}

	public SelectItem[] getAgendaConocimiento1Item() {
		return agendaConocimiento1Item;
	}

	public void setAgendaConocimiento1Item(SelectItem[] agendaConocimiento1Item) {
		this.agendaConocimiento1Item = agendaConocimiento1Item;
	}

	public SelectItem[] getAgendaConocimiento2Item() {
		return agendaConocimiento2Item;
	}

	public void setAgendaConocimiento2Item(SelectItem[] agendaConocimiento2Item) {
		this.agendaConocimiento2Item = agendaConocimiento2Item;
	}

	public SelectItem[] getAgendaConocimiento3Item() {
		return agendaConocimiento3Item;
	}

	public void setAgendaConocimiento3Item(SelectItem[] agendaConocimiento3Item) {
		this.agendaConocimiento3Item = agendaConocimiento3Item;
	}

	public String getAgendaConocimiento1() {
		return agendaConocimiento1;
	}

	public void setAgendaConocimiento1(String agendaConocimiento1) {
		this.agendaConocimiento1 = agendaConocimiento1;
	}

	public String getAgendaConocimiento2() {
		return agendaConocimiento2;
	}

	public void setAgendaConocimiento2(String agendaConocimiento2) {
		this.agendaConocimiento2 = agendaConocimiento2;
	}

	public String getAgendaConocimiento3() {
		return agendaConocimiento3;
	}

	public void setAgendaConocimiento3(String agendaConocimiento3) {
		this.agendaConocimiento3 = agendaConocimiento3;
	}

	public SelectItem[] getAgendaSubConocimiento1Item() {
		return agendaSubConocimiento1Item;
	}

	public void setAgendaSubConocimiento1Item(
			SelectItem[] agendaSubConocimiento1Item) {
		this.agendaSubConocimiento1Item = agendaSubConocimiento1Item;
	}

	public SelectItem[] getAgendaSubConocimiento2Item() {
		return agendaSubConocimiento2Item;
	}

	public void setAgendaSubConocimiento2Item(
			SelectItem[] agendaSubConocimiento2Item) {
		this.agendaSubConocimiento2Item = agendaSubConocimiento2Item;
	}

	public SelectItem[] getAgendaSubConocimiento3Item() {
		return agendaSubConocimiento3Item;
	}

	public void setAgendaSubConocimiento3Item(
			SelectItem[] agendaSubConocimiento3Item) {
		this.agendaSubConocimiento3Item = agendaSubConocimiento3Item;
	}

	public String getAgendaSubConocimiento1() {
		return agendaSubConocimiento1;
	}

	public void setAgendaSubConocimiento1(String agendaSubConocimiento1) {
		this.agendaSubConocimiento1 = agendaSubConocimiento1;
	}

	public String getAgendaSubConocimiento2() {
		return agendaSubConocimiento2;
	}

	public void setAgendaSubConocimiento2(String agendaSubConocimiento2) {
		this.agendaSubConocimiento2 = agendaSubConocimiento2;
	}

	public String getAgendaSubConocimiento3() {
		return agendaSubConocimiento3;
	}

	public void setAgendaSubConocimiento3(String agendaSubConocimiento3) {
		this.agendaSubConocimiento3 = agendaSubConocimiento3;
	}	

}
