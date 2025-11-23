/**
* @author  Ing Juan Pablo Duque
*/

package co.edu.unal.hermes.vista.proyectos.manejadorproyecto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.primefaces.component.menuitem.MenuItem;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DependenciaAreaResponsabilidad;
import co.edu.unal.hermes.modelo.Formulario;
import co.edu.unal.hermes.modelo.HistoricoFormularioProyecto;
import co.edu.unal.hermes.modelo.LineaInvestigacion;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.vista.proyectos.ManejadorMenuFormularios;

public class ManejadorLineasProyecto extends ManejadorProyecto{
    
    private String    	 lineaInvestigacionAProponer;  //cuando no se encuentra la linea solicitada por el usuario, se le da la opcion de proporner y agregar una linea, por ahora solo se recoge el nombre de la linea
    private String 		 lineaInvestigacion;    
    private String 		 mensajeError;
    private UIData       tablaLineas;
    private UIData       tablaDependencias;
    private List 		 listaDependencia;
	private DependenciaAreaResponsabilidad dependenciaAreaResponsabilidad;
    private String 		 dependenciaId;    
    private SelectItem[]  dependenciaItem;
    private Dependencia   dependenciaActual;
    private String linkLineas;
    private String link;
    
    private LineaInvestigacion lineaSeleccionada;
    private DependenciaAreaResponsabilidad dependenciaAreaResponsabilidadSeleccionada;
    
   
    public ManejadorLineasProyecto(){       
      idManejador=LINEAS_INVESTIGACION;
      long now = System.currentTimeMillis();
            
      proyectoActual   = servicioProyecto.obtenerProyecto(proyectoActual.getId(), ProyectoDAOHibernate.LINEAS_DEPENDENCIAS);        
      mensajeError = "";             
      
      //SE CARGAN LAS DEPENDENCIAS   	  	
      dependenciaAreaResponsabilidad = new DependenciaAreaResponsabilidad();                
	  listaDependencia = servicioGeneral.obtenerListaObjetosOrdenadosAsc(new Dependencia(), "nombre");
	  
	  dependenciaItem = new SelectItem[listaDependencia.size()];
	  for (int i = 0; i < listaDependencia.size(); i++) 
	  {
	  	Dependencia d = (Dependencia)listaDependencia.get(i);
	  	String nombre = d.getNombre();
		if(nombre.length()>50){
			nombre = nombre.substring(0,50)+"...";
		}
	    dependenciaItem[i] = new SelectItem(d.getId(), nombre);      
	  }	
	  dependenciaId = ((Dependencia)listaDependencia.get(0)).getId();
	  dependenciaActual = (Dependencia)listaDependencia.get(0);        
	  
	  linkLineas = "http://www.hermes.unal.edu.co/pages/html/descargas/c2008areastematicasunesco.pdf";
	  link="Descargar";
    }
        
 	private Dependencia buscarDependencia(String id)
 	{
         //BUSCA UNA DEPENDENCIA DE ACUERDO A SU ID
  	     Dependencia d=new Dependencia();
	     int i=0;         
	     while(i<listaDependencia.size())
	        {
	         d = (Dependencia)listaDependencia.get(i);
	         if(id.equals(d.getId()))     
	         	break;     	      	
	         i=i+1;	
	        }     
	     return d;
 	}
     	
    public void adicionarDependencia() {
        mensajeError = "";    	
    	if(dependenciaAreaResponsabilidad.getAreaResponsabilidad().equals(""))
    	{
    		FacesContext.getCurrentInstance().addMessage("msgDep", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Tiene que escribir un área de responsabilidad", ""));
    	}
    	else
    	{
	        Dependencia dep = buscarDependencia(dependenciaId);
	    	dependenciaAreaResponsabilidad.setDependencia(dep);
			proyectoActual.adicionarDependencia(dependenciaAreaResponsabilidad);
			dependenciaAreaResponsabilidad = new DependenciaAreaResponsabilidad();
	        
	        dependenciaId = ((Dependencia)listaDependencia.get(0)).getId();
	        dependenciaActual = (Dependencia)listaDependencia.get(0);
	    }
    }
    
    public void eliminarDependencia() {
//       	proyectoActual.borrarDependencia((DependenciaAreaResponsabilidad)tablaDependencias.getRowData());
    	proyectoActual.borrarDependencia(dependenciaAreaResponsabilidadSeleccionada);
    }
    
    public void adicionarLinea(){
        if(lineaInvestigacion.equals("")){
//            mensajeError = "Tiene que escribir una linea de investigación";
        	  FacesContext.getCurrentInstance().addMessage("msgLinea", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Tiene que escribir una linea de investigación", ""));
        }else{
            mensajeError = "";
            LineaInvestigacion lineaNueva = servicioLineaInvestigacion.adicionarLinea(lineaInvestigacion);
            
            proyectoActual.adicionarLinea(lineaNueva); 
            
            lineaInvestigacion = "";
        }        
    }
         
    public void eliminarLinea(){        
    	//LineaInvestigacion linea = (LineaInvestigacion)(tablaLineas.getRowData());
    	//System.out.println("lineaSeleccionada: "+lineaSeleccionada);
    	LineaInvestigacion linea = lineaSeleccionada;
    	proyectoActual.borrarLinea(linea);
	}
        
    public String atras(){
    	
    	System.out.println("Entra Atras");
    	
        servicioGeneral.guardarObjeto(proyectoActual);
        sesion.removeAttribute("manejadorLineasProyecto");
        
/////////MODIFICADO GIOVANNI
		ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
		boolean bandera = false;			
		if(man.getItemProyecto() != null ){
			//NavigationMenuItem lis[] = man.getItemProyecto()[0].getNavigationMenuItems();
			MenuItem lis[] = man.getMenuItemArray();
			if(lis != null){
			for (int i = lis.length - 1; i >=0; i--) {									
				if(bandera){	
					if(lis[i].isRendered()){
						
					return lis[i].getOutcome();
					}
				}
				
				if(lis[i].getOutcome().equals("irLineas")){
					bandera = true;
				}
				
			}
			}
		}		
		//////////////////
        
        return "irInvestigadores";
    }
           
    public String salir() {        
        return null;
    }
    
    public String salirGuardar() {
        
    	System.out.println("Entra SalirGuardar");
    	 
        /////////MODIFICADO GIOVANNI
		String link = "";
		ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
		boolean bandera = false;			
		int pos = 0;
		if(man.getItemProyecto() != null ){
			//NavigationMenuItem lis[] = man.getItemProyecto()[0].getNavigationMenuItems();
			MenuItem lis[] = man.getMenuItemArray();
			if(lis != null){
			for (int i = 0; i < lis.length; i++) {									
				if(bandera){	
					if(lis[i].isRendered()){
						sesion.removeAttribute("manejadorMenuFormularios");
						//link = lis[i].getAction();
						link = lis[i].getOutcome();
						break;
					}
				}
				
				if(lis[i].getOutcome().equals("irLineas")){
					bandera = true;
				}
				if(lis[i].isRendered() ){
					pos++;
				}
			}
			}
		}
    	
    	
    	if((proyectoActual.getEstadoProyecto().getId()).equals("I") && (pos-1) >= proyectoActual.getFase().intValue() ){
            proyectoActual.setFase(new Integer((proyectoActual.getFase()).intValue() + 1));
        }
    	
    	if (proyectoActual.getId() != null) {
			//Ing. Wilver Alexander Martínez Martínez -wam²
			//Cambio - Registro de cambios
			Persona personaAux = new Persona();
			personaAux= (Persona) sesion.getAttribute("persona");
			
			Formulario formulario = new Formulario();
			List listaFormulario = new ArrayList();
			

			listaFormulario = servicioGeneral
					.obtenerListaObjetos("Formulario where id ='60'");
			formulario = (Formulario) listaFormulario.get(0);
			
			HistoricoFormularioProyecto historicoFormualrioProyecto = new HistoricoFormularioProyecto();
            historicoFormualrioProyecto.setDocPersona(personaAux.getId().getDocumento());
            historicoFormualrioProyecto.setTipoDocumentoPersona(personaAux.getId().getTipoDocumento());
            historicoFormualrioProyecto.setFormulario(formulario);
            historicoFormualrioProyecto.setProyecto(proyectoActual);
            historicoFormualrioProyecto.setFechaCambio(new Date());
            servicioGeneral.guardarObjeto(historicoFormualrioProyecto);
		}
    	
    	
    	
    	
    	
        servicioGeneral.guardarObjeto(proyectoActual);        
        sesion.removeAttribute("proyecto");
        sesion.removeAttribute("manejadorMenuFormularios");
        this.borrarManejadoresInsercionProyecto();
        return "misProyectos";
    }

    public String siguiente() {
    	System.out.println("Entra Siguiente");

    	if(proyectoActual.getLineas().size() == 0 && proyectoActual.getDependenciasAreaResponsabilidad().size() == 0){
            mensajeError = "El proyecto no tiene lineas de investigación ni dependencias asociadas";
        	FacesContext.getCurrentInstance().addMessage("msgForm", new FacesMessage(FacesMessage.SEVERITY_ERROR,"El proyecto no tiene lineas de investigación ni dependencias asociadas", ""));
            return "";
        }
    	if(proyectoActual.getLineas().size() == 0){
            mensajeError = "El proyecto no tiene lineas de investigación asociadas.";
        	FacesContext.getCurrentInstance().addMessage("msgForm", new FacesMessage(FacesMessage.SEVERITY_ERROR,"El proyecto no tiene lineas de investigación asociadas", ""));
            return "";
        }
        if(proyectoActual.getDependenciasAreaResponsabilidad().size() == 0){
            mensajeError = "El proyecto no tiene dependencias asociadas.";
            FacesContext.getCurrentInstance().addMessage("msgForm", new FacesMessage(FacesMessage.SEVERITY_ERROR,"El proyecto no tiene dependencias asociadas", ""));
            return "";
        }        
        
        
        /////////MODIFICADO GIOVANNI
		String link = "";
		ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
		boolean bandera = false;			
		int pos = 0;
		if(man.getItemProyecto() != null ){
			//NavigationMenuItem lis[] = man.getItemProyecto()[0].getNavigationMenuItems();
			MenuItem lis[] = man.getMenuItemArray();
			if(lis != null){
			for (int i = 0; i < lis.length; i++) {									
				if(bandera){	
					if(lis[i].isRendered()){
						//sesion.removeAttribute("manejadorMenuFormularios");
						//link = lis[i].g;
						link = lis[i].getOutcome();
						break;
					}
				}
				
				if(lis[i].getOutcome().equals("irLineas")){
					bandera = true;
				}
				if(lis[i].isRendered() ){
					pos++;
				}
				
			}
			}
		}
        
        if((proyectoActual.getEstadoProyecto().getId()).equals("I") && (pos - 1) == proyectoActual.getFase().intValue() ){            
            proyectoActual.setFase(new Integer((proyectoActual.getFase()).intValue() + 1));
        }              
        
        
        
        if (proyectoActual.getId() != null) {
			//Ing. Wilver Alexander Martínez Martínez -wam²
			//Cambio - Registro de cambios
			Persona personaAux = new Persona();
			personaAux= (Persona) sesion.getAttribute("persona");
			
			Formulario formulario = new Formulario();
			List listaFormulario = new ArrayList();
			

			listaFormulario = servicioGeneral
					.obtenerListaObjetos("Formulario where id ='60'");
			formulario = (Formulario) listaFormulario.get(0);
			
			HistoricoFormularioProyecto historicoFormualrioProyecto = new HistoricoFormularioProyecto();
            historicoFormualrioProyecto.setDocPersona(personaAux.getId().getDocumento());
            historicoFormualrioProyecto.setTipoDocumentoPersona(personaAux.getId().getTipoDocumento());
            historicoFormualrioProyecto.setFormulario(formulario);
            historicoFormualrioProyecto.setProyecto(proyectoActual);
            historicoFormualrioProyecto.setFechaCambio(new Date());
            servicioGeneral.guardarObjeto(historicoFormualrioProyecto);
		}
    	
        
        
        
        
        
        
        
        
        
        
        
        servicioGeneral.guardarObjeto(proyectoActual);
        sesion.setAttribute("proyecto",proyectoActual);
      
        sesion.removeAttribute("manejadorLineasProyecto");
    	 /////////MODIFICADO GIOVANNI
		man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
		bandera = false;			
		if(man.getItemProyecto() != null ){
			//NavigationMenuItem lis[] = man.getItemProyecto()[0].getNavigationMenuItems();
			MenuItem lis[] = man.getMenuItemArray();
			if(lis != null){
			for (int i = 0; i < lis.length; i++) {									
				if(bandera){	
					if(lis[i].isRendered()){
						sesion.removeAttribute("manejadorMenuFormularios");
						borrarManejadoresInsercionProyecto();
					return lis[i].getOutcome();
					}
				}
				
				if(lis[i].getOutcome().equals("irLineas")){
					bandera = true;
				}
				
			}
			}
		}		
		
		
    	
		
		//////////////////
		  sesion.removeAttribute("manejadorMenuFormularios");
        return "irObjetivosResultados";
    } 
    
    public List obtenerListaSugeridas(String linea)
    {
    	if ( linea == null || linea.trim().equals(""))
    	return new ArrayList();
    	else
    	return servicioLineaInvestigacion.listaNombreLineaXEmpienzaCon(linea);
    }
    
    protected void cargarValoresIniciales() {        
    }
    
    public void cambiarDependencia(ValueChangeEvent event) {		
		dependenciaActual = buscarDependencia((String)event.getNewValue());			    
	}
        
    public String getLineaInvestigacion() {
        return lineaInvestigacion;
    }
    public void setLineaInvestigacion(String lineaInvestigacion) {
        this.lineaInvestigacion = lineaInvestigacion;
    }
    
    public String getLineaInvestigacionAProponer() {
        return lineaInvestigacionAProponer;
    }
    public void setLineaInvestigacionAProponer(
            String lineaInvestigacionAProponer) {
        this.lineaInvestigacionAProponer = lineaInvestigacionAProponer;
    }
    
    public String getMensajeError() {
        return mensajeError;
    }
    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }
    public UIData getTablaLineas() {
        return tablaLineas;
    }
    public void setTablaLineas(UIData tablaLineas) {
        this.tablaLineas = tablaLineas;
    }

    public Proyecto getProyectoActual() {
        return proyectoActual;
    }
    public void setProyectoActual(Proyecto proyectoActual) {
        this.proyectoActual = proyectoActual;
    }
    
    public UIData getTablaDependencias() {
		return tablaDependencias;
	}
	public void setTablaDependencias(UIData tablaDependencias) {
		this.tablaDependencias = tablaDependencias;
	}	 
	
    public SelectItem[] getDependenciaItem() {
		return dependenciaItem;
	}
    public void setDependenciaItem(SelectItem[] dependenciaItem) {
		this.dependenciaItem = dependenciaItem;
	}
    
    
    public Dependencia getDependenciaActual() {
        return dependenciaActual;
    }
    public void setDependenciaActual(Dependencia dependenciaActual) {
        this.dependenciaActual = dependenciaActual;
    }
    public String getDependenciaId() {
        return dependenciaId;
    }
    public void setDependenciaId(String dependenciaId) {
        this.dependenciaId = dependenciaId;
    }
	public DependenciaAreaResponsabilidad getDependenciaAreaResponsabilidad() {
		return dependenciaAreaResponsabilidad;
	}
	public void setDependenciaAreaResponsabilidad(
			DependenciaAreaResponsabilidad dependenciaAreaResponsabilidad) {
		this.dependenciaAreaResponsabilidad = dependenciaAreaResponsabilidad;
	}

	public void setLinkLineas(String linkLineas) {
		this.linkLineas = linkLineas;
	}

	public String getLinkLineas() {
		return linkLineas;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getLink() {
		return link;
	}

	public LineaInvestigacion getLineaSeleccionada() {
		return lineaSeleccionada;
	}

	public void setLineaSeleccionada(LineaInvestigacion lineaSeleccionada) {
		this.lineaSeleccionada = lineaSeleccionada;
	}

	public DependenciaAreaResponsabilidad getDependenciaAreaResponsabilidadSeleccionada() {
		return dependenciaAreaResponsabilidadSeleccionada;
	}

	public void setDependenciaAreaResponsabilidadSeleccionada(
			DependenciaAreaResponsabilidad dependenciaAreaResponsabilidadSeleccionada) {
		this.dependenciaAreaResponsabilidadSeleccionada = dependenciaAreaResponsabilidadSeleccionada;
	}
	
}
