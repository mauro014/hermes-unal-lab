/**
 * @author Ing Hernán Darío Bernal Parra
 *         http://www.unal.edu.co/quipu/documentos/pglobal.xls
 */

// TODO: ARREGLAR EL CARGUE DEL PLAN GLOBAL DE DESARROLLO
package co.edu.unal.hermes.vista.proyectos.manejadorproyecto;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.menuitem.MenuItem;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.ClasificacionConocimiento;
import co.edu.unal.hermes.modelo.Formulario;
import co.edu.unal.hermes.modelo.HistoricoFormularioProyecto;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.utils.ReemplazaAcentos;
import co.edu.unal.hermes.vista.proyectos.ManejadorMenuFormularios;

public class ManejadorAreasTematicas extends ManejadorProyecto {

	private String mensajeValidacion;
	private String errorValidacion;
	private SelectItem[] areaNivel1Item;
	private SelectItem[] areaNivel2Item;
	private SelectItem[] areaNivel3Item;
	private SelectItem[] areaNivel4Item;
	private String areaNivel1;
	private String areaNivel2;
	private String areaNivel3;
	private String areaNivel4;
	private String textoBuscar;
	private List listaAreasNivel1;
	private List listaAreasNivel2;
	private List listaAreasNivel3;
	private List listaAreasNivel4;
	private List listaAreasNivel3y4;
	private DataTable tablaAreas;
	private ClasificacionConocimiento areaSeleccionada;
	private String nombrePadreDetalle;
	private String nombreAbueloDetalle;
	private String nombreBisabueloDetalle;
	
	
	  
    private String 	 titulo1;
	private String   titulo2;
	 
	 

	public ManejadorAreasTematicas() {
		super();
		
		
		titulo1 ="Proyecto:";
		 titulo2="Búsqueda de Integrantes del Proyecto";
		  
		
		idManejador = AREAS_TEMATICAS;
		listaAreasNivel1 = new ArrayList();
		listaAreasNivel2 = new ArrayList();
		listaAreasNivel3 = new ArrayList();
		listaAreasNivel4 = new ArrayList();
		obtenerListasAreas("0");
		proyectoActual = servicioProyecto.obtenerProyecto(proyectoActual
				.getId(), ProyectoDAOHibernate.AREAS_TEMATICAS);
		cargarValoresIniciales();
		obtenerListasAreas3y4();
		
		  if(proyectoActual.getModalidad().getTipo().getId().compareTo("PN") == 0){
			  titulo1 ="Programa:";
			  titulo2="Integrantes del programa";
		  }else{
			  titulo1 ="Proyecto:";
			  titulo2="Integrantes del proyecto de investigación";
		  }
	 
	}

	// DEFINICION DE FUNCIONES BASICAS
	protected void cargarValoresIniciales() {
	}

	public String atras() {
		// ///////MODIFICADO GIOVANNI
		ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion
				.getAttribute("manejadorMenuFormularios");
		boolean bandera = false;
		if (man.getItemProyecto() != null) {
			//NavigationMenuItem lis[] = man.getItemProyecto()[0].getNavigationMenuItems();
			MenuItem lis[] = man.getMenuItemArray();
			if (lis != null) {
				for (int i = lis.length - 1; i >= 0; i--) {
					if (bandera) {
						if (lis[i].isRendered()) {

							//return lis[i].getAction();
							return lis[i].getOutcome();
						}
					}

					if (lis[i].getOutcome().equals("irAreasTematicas")) {
						bandera = true;
					}

				}
			}
		}
		// ////////////////
		return "irBibliografia";
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
			//NavigationMenuItem lis[] = man.getItemProyecto()[0].getNavigationMenuItems();
			MenuItem lis[] = man.getMenuItemArray();
			if (lis != null) {
				for (int i = 0; i < lis.length; i++) {
					if (bandera) {
						if (lis[i].isRendered()) {
							// sesion.removeAttribute("manejadorMenuFormularios");
							//link = lis[i].getAction();
							link = lis[i].getOutcome();
							break;
						}
					}

					if (lis[i].getOutcome().equals("irAreasTematicas")) {
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
		if (validarListaAreas()) {
			
			
			if (proyectoActual.getId() != null) {
				//Ing. Wilver Alexander Martínez Martínez -wam²
				//Cambio - Registro de cambios
				Persona personaAux = new Persona();
				personaAux= (Persona) sesion.getAttribute("persona");
				
				Formulario formulario = new Formulario();
				List listaFormulario = new ArrayList();
				

				listaFormulario = servicioGeneral
						.obtenerListaObjetos("Formulario where id ='180'");
				formulario = (Formulario) listaFormulario.get(0);
				
				HistoricoFormularioProyecto historicoFormualrioProyecto = new HistoricoFormularioProyecto();
				historicoFormualrioProyecto.setDocPersona(personaAux.getId().getDocumento());
				historicoFormualrioProyecto.setTipoDocumentoPersona(personaAux.getId().getTipoDocumento());
				historicoFormualrioProyecto.setFormulario(formulario);
				historicoFormualrioProyecto.setProyecto(proyectoActual);
				historicoFormualrioProyecto.setFechaCambio(new Date());
				servicioGeneral.guardarObjeto(historicoFormualrioProyecto);
			}
			
						
			
			servicioProyecto.ingresarProyecto(proyectoActual);
			sesion.removeAttribute("proyecto");
			sesion.removeAttribute("manejadorMenuFormularios");
			sesion.removeAttribute("manejadorAreasTematicas");
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
			//NavigationMenuItem lis[] = man.getItemProyecto()[0].getNavigationMenuItems();
			MenuItem lis[] = man.getMenuItemArray();
			if (lis != null) {
				for (int i = 0; i < lis.length; i++) {
					if (bandera) {
						if (lis[i].isRendered()) {
							// sesion.removeAttribute("manejadorMenuFormularios");
							//link = lis[i].getAction();
							link = lis [i].getOutcome();
							break;
						}
					}

					if (lis[i].getOutcome().equals("irAreasTematicas")) {
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
		if (validarListaAreas()) {
			
			
			
			if (proyectoActual.getId() != null) {
				//Ing. Wilver Alexander Martínez Martínez -wam²
				//Cambio - Registro de cambios
				Persona personaAux = new Persona();
				personaAux= (Persona) sesion.getAttribute("persona");
				
				Formulario formulario = new Formulario();
				List listaFormulario = new ArrayList();
				

				listaFormulario = servicioGeneral
						.obtenerListaObjetos("Formulario where id ='180'");
				formulario = (Formulario) listaFormulario.get(0);
				
				HistoricoFormularioProyecto historicoFormualrioProyecto = new HistoricoFormularioProyecto();
                historicoFormualrioProyecto.setDocPersona(personaAux.getId().getDocumento());
                historicoFormualrioProyecto.setTipoDocumentoPersona(personaAux.getId().getTipoDocumento());
                historicoFormualrioProyecto.setFormulario(formulario);
                historicoFormualrioProyecto.setProyecto(proyectoActual);
                historicoFormualrioProyecto.setFechaCambio(new Date());
				servicioGeneral.guardarObjeto(historicoFormualrioProyecto);
			}
			
			
			
			
			servicioProyecto.ingresarProyecto(proyectoActual);
			sesion.setAttribute("proyecto", proyectoActual);

			sesion.removeAttribute("manejadorAreasTematicas");
			// ///////MODIFICADO GIOVANNI
			man = (ManejadorMenuFormularios) sesion
					.getAttribute("manejadorMenuFormularios");
			bandera = false;
			if (man.getItemProyecto() != null) {
				//NavigationMenuItem lis[] = man.getItemProyecto()[0].getNavigationMenuItems();
				MenuItem lis[] = man.getMenuItemArray();
				if (lis != null) {
					for (int i = 0; i < lis.length; i++) {
						if (bandera) {
							if (lis[i].isRendered()) {
								sesion
										.removeAttribute("manejadorMenuFormularios");
								borrarManejadoresInsercionProyecto();
								return lis[i].getOutcome();
							}
						}

						if (lis[i].getOutcome().equals("irAreasTematicas")) {
							bandera = true;
						}

					}
				}
			}
			// ////////////////
			sesion.removeAttribute("manejadorMenuFormularios");
		
			
			if(proyectoActual.getModalidad().getTipo().getId().compareTo("PN") == 0){
				return "irAgendaConocimiento";
			}else{
				return "irProductos";
			}
			
		}
		return "";
	}

	private void obtenerListaAreasNivel1(List listaHijos) {
		listaAreasNivel1.clear();
		listaAreasNivel1.addAll(listaHijos);
		areaNivel1Item = new SelectItem[listaAreasNivel1.size()];
		for (int i = 0; i < listaAreasNivel1.size(); i++) {
			ClasificacionConocimiento c = (ClasificacionConocimiento) listaAreasNivel1
					.get(i);
			areaNivel1Item[i] = new SelectItem(c.getId(), c.getNombre());
		}
		areaNivel1 = ((ClasificacionConocimiento) listaAreasNivel1.get(0))
				.getId();
		obtenerListasAreas(areaNivel1);
	}

	private void obtenerListaAreasNivel2(List listaHijos) {
		listaAreasNivel2.clear();
		listaAreasNivel2.addAll(listaHijos);
		if (listaAreasNivel2.size() > 0) {
			areaNivel2Item = new SelectItem[listaAreasNivel2.size()];
			for (int i = 0; i < listaAreasNivel2.size(); i++) {
				ClasificacionConocimiento c = (ClasificacionConocimiento) listaAreasNivel2
						.get(i);
				areaNivel2Item[i] = new SelectItem(c.getId(), c.getNombre());
			}
			areaNivel2 = ((ClasificacionConocimiento) listaAreasNivel2.get(0))
					.getId();
			obtenerListasAreas(areaNivel2);
		}
	}

	private void obtenerListaAreasNivel3(List listaHijos) {
		listaAreasNivel3.clear();
		listaAreasNivel3.addAll(listaHijos);
		if (listaAreasNivel3.size() > 0) {
			areaNivel3Item = new SelectItem[listaAreasNivel3.size()];
			for (int i = 0; i < listaAreasNivel3.size(); i++) {
				ClasificacionConocimiento c = (ClasificacionConocimiento) listaAreasNivel3
						.get(i);
				areaNivel3Item[i] = new SelectItem(c.getId(), c.getNombre());
			}
			areaNivel3 = ((ClasificacionConocimiento) listaAreasNivel3.get(0))
					.getId();
			obtenerListasAreas(areaNivel3);
		}
	}

	private void obtenerListaAreasNivel4(List listaHijos) {
		listaAreasNivel4.clear();
		listaAreasNivel4.addAll(listaHijos);
		if (listaAreasNivel4.size() > 0) {
			areaNivel4Item = new SelectItem[listaAreasNivel4.size()];
			for (int i = 0; i < listaAreasNivel4.size(); i++) {
				ClasificacionConocimiento c = (ClasificacionConocimiento) listaAreasNivel4
						.get(i);
				areaNivel4Item[i] = new SelectItem(c.getId(), c.getNombre());
			}
			areaNivel4 = ((ClasificacionConocimiento) listaAreasNivel4.get(0))
					.getId();
		}
	}

	private void obtenerListasAreas(String idArea) {
		int nivel = 0;
		List listaAreasxNivel = null;
		ClasificacionConocimiento cc = null;
		if (idArea.equals("0")) {
			cc = new ClasificacionConocimiento(idArea);
			cc.setNivel(new Integer(0));
		} else {
			cc = buscarAreaxId(idArea);
		}
		listaAreasxNivel = servicioGeneral.obtenerHijos(cc);
		if (!listaAreasxNivel.isEmpty()) {
			nivel = ((ClasificacionConocimiento) (listaAreasxNivel.get(0)))
					.getNivel().intValue();
			switch (nivel) {
			case 0:
				obtenerListaAreasNivel1(listaAreasxNivel);
				break;
			case 1:
				obtenerListaAreasNivel2(listaAreasxNivel);
				break;
			case 2:
				obtenerListaAreasNivel3(listaAreasxNivel);
				break;
			case 3:
				obtenerListaAreasNivel4(listaAreasxNivel);
				break;
			}
		} else {
			nivel = cc.getNivel().intValue();
			switch (nivel) {
			case 0:
				areaNivel2 = null;
				areaNivel3 = null;
				areaNivel4 = null;
			case 1:
				areaNivel3 = null;
				areaNivel4 = null;
			case 2:
				areaNivel4 = null;
			}
		}
	}

	private void obtenerListasAreas3y4() {
		List lstAreasxNivel2 = null;
		List lstAreasxNivel3 = null;
		List lstAreasxNivel4 = null;
		listaAreasNivel3y4 = new ArrayList();  

		if (listaAreasNivel1 != null)
			for (Iterator ite1 = listaAreasNivel1.iterator(); ite1.hasNext();) {
				ClasificacionConocimiento cc1 = (ClasificacionConocimiento) ite1.next();
				lstAreasxNivel2 = servicioGeneral.obtenerHijos(cc1);
				if (!lstAreasxNivel2.isEmpty()) {
					for (Iterator ite2 = lstAreasxNivel2.iterator(); ite2.hasNext();) {
						ClasificacionConocimiento cc2 = (ClasificacionConocimiento) ite2.next();
						lstAreasxNivel3 = servicioGeneral.obtenerHijos(cc2);
						if (lstAreasxNivel3 != null)
							for (Iterator ite3 = lstAreasxNivel3.iterator(); ite3.hasNext();) {
								ClasificacionConocimiento cc3 = (ClasificacionConocimiento) ite3.next();
								lstAreasxNivel4 = servicioGeneral.obtenerHijos(cc3);
								
								if (!lstAreasxNivel4.isEmpty()) {
									listaAreasNivel3y4.addAll(lstAreasxNivel4);
								} else {
									listaAreasNivel3y4.add(cc3);

								}
							}
					}

				}
			}
	}

	public void adicionarAreaTematica() {
		/*String areaActual = "";
		if (areaNivel4 != null) {
			areaActual = areaNivel4;
		} else {
			if (areaNivel3 != null) {
				areaActual = areaNivel3;
			} else {
				if (areaNivel2 != null) {
					areaActual = areaNivel2;
				} else {
					areaActual = areaNivel1;
				}
			}
		}*/
		ClasificacionConocimiento cc = null ;
		boolean encontro = false;
		if (textoBuscar != null && textoBuscar.trim() != "")
			for (Iterator ite = listaAreasNivel3y4.iterator(); ite.hasNext();) {
				 cc = (ClasificacionConocimiento) ite.next();
				if (ReemplazaAcentos.quitarTildes(cc.getNombre()).trim().toUpperCase().equals(ReemplazaAcentos.quitarTildes(textoBuscar).trim().toUpperCase()) ) {
					encontro = true;
					break;
				}
			}

		if (!encontro){
			errorValidacion = "Por favor seleccione una Área Temática de las sugeridas, no se puede adicionar una nueva.";
			FacesContext
			.getCurrentInstance()
			.addMessage(
					"msgs",
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							errorValidacion,
							""));
			return;
		}	
		errorValidacion = "";
		textoBuscar = "";
		//ClasificacionConocimiento cc = buscarAreaxId(areaActual);
		if (validarAreaDuplicada(cc)) {
			proyectoActual.adicionarClasificacionConocimiento(cc);
			//listaAreasTematicasProyecto.add(cc);
		}
	}

	private boolean validarListaAreas() {
		if (proyectoActual.getListaClasificacionConocimiento().size() == 0) {
			mensajeValidacion = "La lista de áreas temáticas se encuentra vacía";
			FacesContext
			.getCurrentInstance()
			.addMessage(
					"msgs",
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							mensajeValidacion,
							""));
			return false;
		} else {
			mensajeValidacion = "";
			return true;
		}
	}

	private boolean validarAreaDuplicada(ClasificacionConocimiento cc) {
		Iterator it = proyectoActual.getClasificacionConocimiento().iterator();
		while (it.hasNext()) {
			ClasificacionConocimiento ccaux = (ClasificacionConocimiento) it
					.next();
			if (ccaux.getId().equals(cc.getId())) {
				return false;
			}
		}
		return true;
	}

	public void eliminarClasificacionConocimiento() {
		//proyectoActual.borrarClasificacionConocimiento((ClasificacionConocimiento)tablaAreas.getRowData());
		proyectoActual.borrarClasificacionConocimiento((ClasificacionConocimiento)areaSeleccionada);
	}

	private ClasificacionConocimiento buscarAreaxId(String idArea) {
		List listaAuxiliar = new ArrayList();
		if (listaAreasNivel1 != null) {
			listaAuxiliar.addAll(listaAreasNivel1);
		}
		if (listaAreasNivel2 != null) {
			listaAuxiliar.addAll(listaAreasNivel2);
		}
		if (listaAreasNivel3 != null) {
			listaAuxiliar.addAll(listaAreasNivel3);
		}
		if (listaAreasNivel4 != null) {
			listaAuxiliar.addAll(listaAreasNivel4);
		}
		// listaAuxiliar.addAll(listaAreasNivel2);
		// listaAuxiliar.addAll(listaAreasNivel3);
		// listaAuxiliar.addAll(listaAreasNivel4);
		Iterator it = listaAuxiliar.iterator();
		ClasificacionConocimiento c = null;
		boolean clasificacionEncontrada = false;
		while (it.hasNext() && !clasificacionEncontrada) {
			c = (ClasificacionConocimiento) it.next();
			if (c.getId().equals(idArea)) {
				clasificacionEncontrada = true;
			}
		}
		listaAuxiliar = null;
		return c;
	}

	public String getNombrePadre() {
		ClasificacionConocimiento cla;
	    	cla = areaSeleccionada;
		cla = (ClasificacionConocimiento) servicioGeneral.obtenerObjetoYPadre(
				cla, cla.getId());

		cla = cla.getPadre();
		if (cla != null) {
			return cla.getNombre();
		} else {
			return "";
		}

	}

	public String getNombreAbuelo() {
		ClasificacionConocimiento cla;
		
		cla = areaSeleccionada;

		cla = (ClasificacionConocimiento) servicioGeneral.obtenerObjetoYPadre(
				cla, cla.getId());
		cla = cla.getPadre();
		if (cla != null) {
			cla = (ClasificacionConocimiento) servicioGeneral
					.obtenerObjetoYPadre(cla, cla.getId());
		}

		cla = cla.getPadre();
		if (cla != null) {
			return cla.getNombre();
		} else {
			return "";
		}
	}

	public String getNombreBisabuelo() {
		ClasificacionConocimiento cla;
		
		cla = areaSeleccionada;

		cla = (ClasificacionConocimiento) servicioGeneral.obtenerObjetoYPadre(
				cla, cla.getId());
		cla = cla.getPadre();
		if (cla != null) {
			cla = (ClasificacionConocimiento) servicioGeneral
					.obtenerObjetoYPadre(cla, cla.getId());
		}
		cla = cla.getPadre();
		if (cla != null) {
			cla = (ClasificacionConocimiento) servicioGeneral
					.obtenerObjetoYPadre(cla, cla.getId());
		}

		cla = cla.getPadre();
		if (cla != null && !cla.getId().equals(ClasificacionConocimiento.RAIZ)) {
			return cla.getNombre();
		} else {
			return "";
		}
	}
	
	public void verDetalle(){
	    nombrePadreDetalle = getNombrePadre();
	    nombreAbueloDetalle = getNombreAbuelo();	    
	    nombreBisabueloDetalle = getNombreBisabuelo();
	}

	public void cambiarAreaNivel1(ValueChangeEvent event) {
		obtenerListasAreas((String) event.getNewValue());
	}

	public void cambiarAreaNivel2(ValueChangeEvent event) {
		obtenerListasAreas((String) event.getNewValue());
	}

	public void cambiarAreaNivel3(ValueChangeEvent event) {
		obtenerListasAreas((String) event.getNewValue());
	}

	public List obtenerAreasTematicasSugeridas(String nombre) {
		List lstAreas = new ArrayList();
		if (nombre != null && nombre.trim() != "")
			for (Iterator ite = listaAreasNivel3y4.iterator(); ite.hasNext();) {
				ClasificacionConocimiento cc = (ClasificacionConocimiento) ite.next();
				if (ReemplazaAcentos.quitarTildes(cc.getNombre()).trim().toUpperCase().indexOf(ReemplazaAcentos.quitarTildes(nombre).trim().toUpperCase()) >= 0) {
					lstAreas.add(cc.getNombre());
				}

			}
		return lstAreas;
	}

	public String getAreaNivel1() {
		return areaNivel1;
	}

	public void setAreaNivel1(String areaNivel1) {
		this.areaNivel1 = areaNivel1;
	}

	public SelectItem[] getAreaNivel1Item() {
		return areaNivel1Item;
	}

	public void setAreaNivel1Item(SelectItem[] areaNivel1Item) {
		this.areaNivel1Item = areaNivel1Item;
	}

	public String getAreaNivel2() {
		return areaNivel2;
	}

	public void setAreaNivel2(String areaNivel2) {
		this.areaNivel2 = areaNivel2;
	}

	public SelectItem[] getAreaNivel2Item() {
		return areaNivel2Item;
	}

	public void setAreaNivel2Item(SelectItem[] areaNivel2Item) {
		this.areaNivel2Item = areaNivel2Item;
	}

	public String getAreaNivel3() {
		return areaNivel3;
	}

	public void setAreaNivel3(String areaNivel3) {
		this.areaNivel3 = areaNivel3;
	}

	public SelectItem[] getAreaNivel3Item() {
		return areaNivel3Item;
	}

	public void setAreaNivel3Item(SelectItem[] areaNivel3Item) {
		this.areaNivel3Item = areaNivel3Item;
	}

	public String getAreaNivel4() {
		return areaNivel4;
	}

	public void setAreaNivel4(String areaNivel4) {
		this.areaNivel4 = areaNivel4;
	}

	public SelectItem[] getAreaNivel4Item() {
		return areaNivel4Item;
	}

	public void setAreaNivel4Item(SelectItem[] areaNivel4Item) {
		this.areaNivel4Item = areaNivel4Item;
	}

	public List getListaAreasNivel1() {
		return listaAreasNivel1;
	}

	public void setListaAreasNivel1(List listaAreasNivel1) {
		this.listaAreasNivel1 = listaAreasNivel1;
	}

	public List getListaAreasNivel2() {
		return listaAreasNivel2;
	}

	public void setListaAreasNivel2(List listaAreasNivel2) {
		this.listaAreasNivel2 = listaAreasNivel2;
	}

	public List getListaAreasNivel3() {
		return listaAreasNivel3;
	}

	public void setListaAreasNivel3(List listaAreasNivel3) {
		this.listaAreasNivel3 = listaAreasNivel3;
	}

	public List getListaAreasNivel4() {
		return listaAreasNivel4;
	}

	public void setListaAreasNivel4(List listaAreasNivel4) {
		this.listaAreasNivel4 = listaAreasNivel4;
	}

	public String getMensajeValidacion() {
		return mensajeValidacion;
	}

	public void setMensajeValidacion(String mensajeValidacion) {
		this.mensajeValidacion = mensajeValidacion;
	}

	public DataTable getTablaAreas() {
		return tablaAreas;
	}

	public void setTablaAreas(DataTable tablaAreas) {
		this.tablaAreas = tablaAreas;
	}

	public boolean isListaAreasNivel2Existe() {
		if (areaNivel2 == null)
			return false;
		return true;
	}

	public boolean isListaAreasNivel3Existe() {
		if (areaNivel3 == null)
			return false;
		return true;
	}

	public boolean isListaAreasNivel4Existe() {
		if (areaNivel4 == null)
			return false;
		return true;
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

	public String getTitulo1() {
		return titulo1;
	}

	public void setTitulo1(String titulo1) {
		this.titulo1 = titulo1;
	}

	public String getTitulo2() {
		return titulo2;
	}

	public void setTitulo2(String titulo2) {
		this.titulo2 = titulo2;
	}

	public ClasificacionConocimiento getAreaSeleccionada() {
		return areaSeleccionada;
	}

	public void setAreaSeleccionada(ClasificacionConocimiento areaSeleccionada) {
		this.areaSeleccionada = areaSeleccionada;
	}

	public String getNombrePadreDetalle() {
	    return nombrePadreDetalle;
	}

	public void setNombrePadreDetalle(String nombrePadreDetalle) {
	    this.nombrePadreDetalle = nombrePadreDetalle;
	}

	public String getNombreAbueloDetalle() {
	    return nombreAbueloDetalle;
	}

	public void setNombreAbueloDetalle(String nombreAbueloDetalle) {
	    this.nombreAbueloDetalle = nombreAbueloDetalle;
	}

	public String getNombreBisabueloDetalle() {
	    return nombreBisabueloDetalle;
	}

	public void setNombreBisabueloDetalle(String nombreBisabueloDetalle) {
	    this.nombreBisabueloDetalle = nombreBisabueloDetalle;
	}
	
	
	
	
	
}
