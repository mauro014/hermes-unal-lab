/**
* @author  Ing Hernán Darío Bernal Parra
*/

package co.edu.unal.hermes.vista.proyectos.manejadorproyecto;

import java.util.HashSet;

import javax.faces.component.html.HtmlDataTable;

import org.apache.myfaces.custom.navmenu.NavigationMenuItem;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.Empresa;
import co.edu.unal.hermes.vista.proyectos.ManejadorMenuFormularios;


public class ManejadorEmpresas extends ManejadorProyecto{

	HtmlDataTable tablaEmpresas = new HtmlDataTable(); 
	String nit;
	Empresa empresaActual;
	Empresa empresaNueva;
	
	public ManejadorEmpresas()
	{ 
	 super();
	empresaNueva=new Empresa(); 
	 
     sesion.removeAttribute("manejadorDatosBasicos");     
     if(proyectoActual.getId()!=null){     	
 	    proyectoActual = servicioProyecto.obtenerProyecto(proyectoActual.getId(), ProyectoDAOHibernate.EMPRESAS); 
     }
     else{

     }
     System.out.println("manejador empresas");
	}

	public void crearEmpresa()
	{
	    servicioGeneral.guardarObjeto(empresaNueva);
	}
	public void buscar()
	{
	    Empresa empresa=servicioGeneral.buscarEmpresaXNIT(nit);
	    empresaActual=empresa;
	}
	
	public void asociar()
	{
	    if (empresaActual !=null)
	    {
	        if(    proyectoActual.getEmpresas()==null)
	        {
	            proyectoActual.setEmpresas(new HashSet());
	        }
	        proyectoActual.getEmpresas().add(empresaActual);
	    }
	}
	public void eliminar()
	{
	    Empresa e= (Empresa) tablaEmpresas.getRowData();
	    
	    proyectoActual.getEmpresas().remove(e);
	}
	
	//
	public String atras(){
		 /////////MODIFICADO GIOVANNI
		ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
		
		boolean bandera = false;			
		if(man.getItemProyecto() != null ){
			NavigationMenuItem lis[] = man.getItemProyecto()[0].getNavigationMenuItems();
			if(lis != null){
				for (int i = lis.length - 1; i >=0; i--) {									
				if(bandera){	
					if(lis[i].isRendered()){						
					return lis[i].getAction();
					}
				}
				
				if(lis[i].getAction().equals("irEmpresas")){
					bandera = true;
				}
				
			}
			}
		}
		//////////////////
		return "irDatosBasicos";
	}

	public String salir(){
		sesion.removeAttribute("proyecto");
		borrarManejadoresInsercionProyecto();
		return "misProyectos" ;
	}

	public String salirGuardar(){
//		try
//		{
//		  //AL IR AL SIGUIENTE FORMULARIO SE ACTUALIZA LA CIUDAD DEL PROYECTO
//          {
          	
	/////MODIFICADO GIOVANNI
		String link = "";
		ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
		boolean bandera = false;			
		int pos = 0;
		if(man.getItemProyecto() != null ){
			NavigationMenuItem lis[] = man.getItemProyecto()[0].getNavigationMenuItems();
			if(lis != null){
			for (int i = 0; i < lis.length; i++) {									
				if(bandera){	
					if(lis[i].isRendered()){
						sesion.removeAttribute("manejadorMenuFormularios");
						link = lis[i].getAction();
						break;
					}
				}
				
				if(lis[i].getAction().equals("irEmpresas")){
					bandera = true;
				}
				if(lis[i].isRendered() ){
					pos++;
				}
			}
			}
		}
		
		
		if( (pos-1) >= proyectoActual.getFase().intValue() && (proyectoActual.getEstadoProyecto().getId()).equals("I"))
            {
          	    proyectoActual.setFase(new Integer((proyectoActual.getFase()).intValue() + 1));
          	}          	
	            servicioProyecto.ingresarProyecto(proyectoActual);
	            sesion.removeAttribute("proyecto");
	            sesion.removeAttribute("manejadorMenuFormularios");
	          	borrarManejadoresInsercionProyecto();
	          	return "misProyectos" ;
          	
//	    }
//        catch(Exception e)
//		{
//        	e.printStackTrace();
//		}
//        return "";
	}

	public String siguiente(){
		
//		try
//		{
//		
//		  //AL IR AL SIGUIENTE FORMULARIO SE ACTUALIZA LA CIUDAD DEL PROYECTO

		

		/////MODIFICADO GIOVANNI
			String link = "";
			ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
			boolean bandera = false;			
			int pos = 0;
			if(man.getItemProyecto() != null ){
				NavigationMenuItem lis[] = man.getItemProyecto()[0].getNavigationMenuItems();
				if(lis != null){
				for (int i = 0; i < lis.length; i++) {									
					if(bandera){	
						if(lis[i].isRendered()){
							//sesion.removeAttribute("manejadorMenuFormularios");
							link = lis[i].getAction();
							break;
						}
					}
					
					if(lis[i].getAction().equals("irEmpresas")){
						bandera = true;
					}
					if(lis[i].isRendered() ){
						pos++;
					}
				}
				}
			}
			
		
          	if( (pos - 1) == proyectoActual.getFase().intValue() && (proyectoActual.getEstadoProyecto().getId()).equals("I"))
            {
          	    proyectoActual.setFase(new Integer((proyectoActual.getFase()).intValue() + 1));
          	}            
            servicioProyecto.ingresarProyecto(proyectoActual);                        
            sesion.setAttribute("proyecto",proyectoActual);
          
          	sesion.removeAttribute("manejadorEmpresas");
          	
          	 /////////MODIFICADO GIOVANNI
			man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
			bandera = false;			
			if(man.getItemProyecto() != null ){
				NavigationMenuItem lis[] = man.getItemProyecto()[0].getNavigationMenuItems();
				if(lis != null){
				for (int i = 0; i < lis.length; i++) {									
					if(bandera){	
						if(lis[i].isRendered()){
							sesion.removeAttribute("manejadorMenuFormularios");
						return lis[i].getAction();
						}
					}
					
					if(lis[i].getAction().equals("irEmpresas")){
						bandera = true;
					}
					
				}
				}
			}
			//////////////////
			  sesion.removeAttribute("manejadorMenuFormularios");
          	return "irInvestigadores" ;
          	
//          }
//	    }
//        catch(Exception e)
//		{
//        	e.printStackTrace();
//		}
//        return "";
	}

    protected void cargarValoresIniciales() {
        
        
    }

    public Empresa getEmpresaActual() {
        return empresaActual;
    }
    public void setEmpresaActual(Empresa empresaActual) {
        this.empresaActual = empresaActual;
    }
    public String getNit() {
        return nit;
    }
    public void setNit(String nit) {
        this.nit = nit;
    }
    public HtmlDataTable getTablaEmpresas() {
        return tablaEmpresas;
    }
    public void setTablaEmpresas(HtmlDataTable tablaEmpresas) {
        this.tablaEmpresas = tablaEmpresas;
    }
    public Empresa getEmpresaNueva() {
        return empresaNueva;
    }
    public void setEmpresaNueva(Empresa empresaNueva) {
        this.empresaNueva = empresaNueva;
    }
}


