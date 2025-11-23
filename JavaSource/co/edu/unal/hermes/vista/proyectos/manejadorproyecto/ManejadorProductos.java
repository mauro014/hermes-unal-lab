/**
* @author  Ing Hernán Darío Bernal Parra
*/

package co.edu.unal.hermes.vista.proyectos.manejadorproyecto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.menuitem.MenuItem;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.Formulario;
import co.edu.unal.hermes.modelo.HistoricoFormularioProyecto;
import co.edu.unal.hermes.modelo.Modalidad;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.ProductoTipo;
import co.edu.unal.hermes.modelo.ProyectoProducto;
import co.edu.unal.hermes.modelo.TipoModalidad;
import co.edu.unal.hermes.vista.proyectos.ManejadorMenuFormularios;


public class ManejadorProductos extends ManejadorProyecto{

   
	private String avisoProductos;
	
	private String descripcion;
	
	private String       productoNivel1;
	private String       productoNivel2;
	private String       productoNivel3;
	
	private ProductoTipo productoNivel1Actual;
	private ProductoTipo productoNivel2Actual;
	private ProductoTipo productoNivel3Actual;
	
	private SelectItem[] productoNivel1Item;
	private SelectItem[] productoNivel2Item;
	private SelectItem[] productoNivel3Item;
	
	private List         listaProductosNivel1;
	private List         listaProductosNivel2;
	private List         listaProductosNivel3;
	private List         productosConvocatoria;
	
	private UISelectOne manejadorProductoNivel1;
	private UISelectOne manejadorProductoNivel2;
	private UISelectOne manejadorProductoNivel3;
	
	private DataTable    tablaProductos;
	private String 		 cantidad;
	private String 		 mensajeErrorCantidad;
	private String 		 mensajeError;
	
	 private String 	  titulo1;
	 private String       titulo2;
	 
		private boolean esProgramaNacional = false;
	
	private ProyectoProducto productoSeleccionado;
	
	public ManejadorProductos()
	{
	    
	    super();
	    idManejador = PRODUCTOS;
	    
		titulo1 ="Proyecto:";
		titulo2="Productos";
	    
	    manejadorProductoNivel1 = new UISelectOne();
		manejadorProductoNivel2 = new UISelectOne();
		manejadorProductoNivel3 = new UISelectOne();
		listaProductosNivel1 = new ArrayList();    
		listaProductosNivel2 = new ArrayList();    
		listaProductosNivel3 = new ArrayList();    
	    proyectoActual = servicioProyecto.obtenerProyecto(proyectoActual.getId(), ProyectoDAOHibernate.PRODUCTOS);
	    cargarValoresIniciales();
	    
	    if(proyectoActual.getModalidad().getTipo().getId().compareTo("PN") == 0){
			  titulo1 = "Programa:";
			  titulo2 = "Compromisos esperados";
			  this.esProgramaNacional = true;	
		  }else{
			  titulo1 ="Proyecto:";
			  titulo2 ="Productos";
			  this.esProgramaNacional = false;	
		  }
	}
	
	protected void cargarValoresIniciales() {
	    descripcion = "";
	    cantidad = "1";
		mensajeErrorCantidad = "";
		mensajeError = "";
		
	    // Cuando la modalidad del proyecto es una convocatoria, se buscan los productos asociados a ella
		Modalidad mod = proyectoActual.getModalidad();
		
		if(mod instanceof Convocatoria){
		    
		    	if (mod.getTipo().getId().equals("J")){
		    	 // Se carga el valor del primer nivel
			    ProductoTipo pro1 = new ProductoTipo();
			    pro1.setId(ProductoTipo.RAIZ);
			    listaProductosNivel1 = servicioGeneral.obtenerHijos(pro1);
			    productosConvocatoria = servicioGeneral.obtenerListaObjetos("ProductoTipo");
			    
		    	}else{
        		    	Convocatoria con = (Convocatoria)mod;
        			productosConvocatoria = servicioModalidad.obtenerProductosConvocatoria(con);
        			if(productosConvocatoria != null && productosConvocatoria.size() != 0){
        				for(Iterator it = productosConvocatoria.iterator(); it.hasNext();){
        					ProductoTipo pt = (ProductoTipo) it.next();
        					if(pt.getNivel().equals("0")){
        						listaProductosNivel1.add(pt);
        					}
        				}
        			}  
		    	}
						
		}else{
		    // Se carga el valor del primer nivel
		    ProductoTipo pro1 = new ProductoTipo();
		    pro1.setId(ProductoTipo.RAIZ);
		    listaProductosNivel1 = servicioGeneral.obtenerHijos(pro1);
		    productosConvocatoria = servicioGeneral.obtenerListaObjetos("ProductoTipo");
		}
		
		Collections.sort(listaProductosNivel1, new Comparator() {  
			  
            public int compare(Object o1, Object o2) {  
                ProductoTipo e1 = (ProductoTipo) o1;  
                ProductoTipo e2 = (ProductoTipo) o2;  
                return e1.getNombre().compareTo(e2.getNombre());  
            }  
        });
		
	    productoNivel1Item = crearSelectItem(listaProductosNivel1);
	    productoNivel1 = ((ProductoTipo)listaProductosNivel1.get(0)).getId();
	    productoNivel1Actual = (ProductoTipo)listaProductosNivel1.get(0);
	    //String a = productoNivel1Actual.nombre;
	    
	    manejadorProductoNivel1.setValue(productoNivel1);
	    
	    
	    //valores para el segundo nivel
	    ProductoTipo pro2 = new ProductoTipo();
	    pro2.setId(productoNivel1);
	    List listaProductosNivel2Aux = servicioGeneral.obtenerHijos(pro2);
	    if(productosConvocatoria != null && productosConvocatoria.size() != 0 && listaProductosNivel2Aux!=null){
			for(Iterator it = productosConvocatoria.iterator(); it.hasNext();){
				ProductoTipo pt = (ProductoTipo) it.next();
				for(Iterator ite = listaProductosNivel2Aux.iterator(); ite.hasNext();){
					ProductoTipo hijo = (ProductoTipo) ite.next();
					if(pt.getId().equals(hijo.getId())){
					    listaProductosNivel2.add(hijo);
					}
				}
			}			
		}
	    
	    Collections.sort(listaProductosNivel2, new Comparator() {  
			  
            public int compare(Object o1, Object o2) {  
                ProductoTipo e1 = (ProductoTipo) o1;  
                ProductoTipo e2 = (ProductoTipo) o2;  
                return e1.getNombre().compareTo(e2.getNombre());
//                return e1.getId().compareTo(e2.getId());  
            }  
        });
	    
	    productoNivel2Item = crearSelectItem(listaProductosNivel2);
	    if(listaProductosNivel2.size()!=0){
		    productoNivel2 = ((ProductoTipo)listaProductosNivel2.get(0)).getId();
		    productoNivel2Actual = (ProductoTipo)listaProductosNivel2.get(0);
		    manejadorProductoNivel2.setValue(productoNivel2);
		    
		    // valores iniciales para el ultimo nivel	    
		    ProductoTipo pro3 = new ProductoTipo();
		    pro3.setId(productoNivel2);	    
		    List listaProductosNivel3Aux = servicioGeneral.obtenerHijos(pro3);
		    if(productosConvocatoria.size() != 0 && listaProductosNivel3Aux!=null){
				for(Iterator it = productosConvocatoria.iterator(); it.hasNext();){
					ProductoTipo pt = (ProductoTipo) it.next();
					for(Iterator ite = listaProductosNivel3Aux.iterator(); ite.hasNext();){
						ProductoTipo hijo = (ProductoTipo) ite.next();
						if(pt.getId().equals(hijo.getId())){
						    listaProductosNivel3.add(hijo);
						}
					}
				}
			}
		    
		    Collections.sort(listaProductosNivel3, new Comparator() {  
				  
	            public int compare(Object o1, Object o2) {  
	                ProductoTipo e1 = (ProductoTipo) o1;  
	                ProductoTipo e2 = (ProductoTipo) o2;  
	                return e1.getNombre().compareTo(e2.getNombre()); 
	            }  
	        });

		    productoNivel3Item = crearSelectItem(listaProductosNivel3);
		    if(listaProductosNivel3.size()!=0){
				productoNivel3 = ((ProductoTipo)listaProductosNivel3.get(0)).getId();  
			    productoNivel3Actual = (ProductoTipo)listaProductosNivel3.get(0);
			    manejadorProductoNivel3.setValue(productoNivel3);
		    }
	    }else{
	    	listaProductosNivel3 = new ArrayList();
	    	productoNivel3Item = crearSelectItem(listaProductosNivel3);
	    }
    }
	
	private SelectItem[] crearSelectItem(List lista){
		SelectItem[] elementos;
		elementos = new SelectItem[lista.size()];
	   	for(int i=0; i<lista.size(); i++){
	   		ProductoTipo pro = (ProductoTipo)lista.get(i);
	   		String nombre = pro.getNombre();
	   		
	   		
			if(pro.getNombre().length()>50){
				nombre = pro.getNombre().substring(0,50)+"...";
				if(pro.getNombre().length()<115 && pro.getNombre().length()>75)	pro.setNombrePop(pro.getNombre()+ "_____________________________________________________________");
				else pro.setNombrePop(pro.getNombre()+" ");
			}
			
	   		elementos[i] = new SelectItem(pro.getId(), nombre);	   		
	   	}
	   	return elementos;
	}
	
	private ProductoTipo buscarProductoTipoNivel1(String id){
		ProductoTipo prT= new ProductoTipo();
	     int i=0;
	     while(i<this.listaProductosNivel1.size())
	     {
	     	prT= (ProductoTipo) this.listaProductosNivel1.get(i);
	     	if(id.equals(prT.getId())){
	     	   break;
	     	}
	     	i=i+1;
	     }	    
	     return prT;
	}	

	private ProductoTipo buscarProductoTipoNivel2(String id){
		ProductoTipo prT= new ProductoTipo();
	     int i=0;
	     while(i<this.listaProductosNivel2.size())
	     {
	     	prT= (ProductoTipo) this.listaProductosNivel2.get(i);
	     	if(id.equals(prT.getId())){
	     	   break;
	     	}
	     	i=i+1;
	     }	    
	     return prT;
	}
	

	private ProductoTipo buscarProductoTipoNivel3(String id){
		ProductoTipo prT= new ProductoTipo();
	     int i=0;
	     while(i<this.listaProductosNivel3.size())
	     {
	     	prT= (ProductoTipo) this.listaProductosNivel3.get(i);
	     	if(id.equals(prT.getId())){
	     	   break;
	     	}
	     	i=i+1;
	     }	    
	     return prT;
	}
	
	public String salirGuardar(){
		try
		{
		    if(validarProductos()){
		    	
		    	
		    	
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
								link = lis[i].getOutcome();
								break;
							}
						}
						
						if(lis[i].getOutcome().equals("irProductos")){
							bandera = true;
						}
						if(lis[i].isRendered() ){
							pos++;
						}
					}
					}
				}
		    	
		        if((proyectoActual.getEstadoProyecto().getId()).equals("I") && (pos-1) >= proyectoActual.getFase().intValue() )
	            {
	          	    //proyectoActual.setFase(new Integer((proyectoActual.getFase()).intValue() + 1));
	          	    proyectoActual.setFase(new Integer((proyectoActual.getFase()).intValue() + 1));
	          	}        
		        
				if (proyectoActual.getId() != null) {
					//Ing. Wilver Alexander Mart�nez Mart�nez -wam�
					//Cambio - Registro de cambios
					Persona personaAux = new Persona();
					personaAux= (Persona) sesion.getAttribute("persona");
					
					Formulario formulario = new Formulario();
					List listaFormulario = new ArrayList();
					

					listaFormulario = servicioGeneral
							.obtenerListaObjetos("Formulario where id ='200'");
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
	          	sesion.removeAttribute("manejadorProductos");
	          	return "misProyectos" ;
		    }
		    else
		    	FacesContext.getCurrentInstance().addMessage("mensajeError", new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se han adicionado productos", ""));
		            
	    }
        catch(Exception e)
		{
        	e.printStackTrace();
		}
        return "";
	}
	
	public void cambiarProductoNivel1(ValueChangeEvent event) {		
	    productoNivel1Actual = buscarProductoTipoNivel1((String)event.getNewValue());
	    // valores para el segundo nivel
	    ProductoTipo pro2 = new ProductoTipo();
	    pro2.setId(event.getNewValue().toString());
	    listaProductosNivel2 = new ArrayList();
	    List listaProductosNivel2Aux = servicioGeneral.obtenerHijos(pro2);
	    if(productosConvocatoria.size() != 0 && listaProductosNivel2Aux!=null){
			for(Iterator it = productosConvocatoria.iterator(); it.hasNext();){
				ProductoTipo pt = (ProductoTipo) it.next();
				for(Iterator ite = listaProductosNivel2Aux.iterator(); ite.hasNext();){
					ProductoTipo hijo = (ProductoTipo) ite.next();
					if(pt.getId().equals(hijo.getId())){
					    listaProductosNivel2.add(hijo);
					}
				}
			}
		}
	    
	    Collections.sort(listaProductosNivel2, new Comparator() {  
			  
            public int compare(Object o1, Object o2) {  
                ProductoTipo e1 = (ProductoTipo) o1;  
                ProductoTipo e2 = (ProductoTipo) o2;  
                return e1.getNombre().compareTo(e2.getNombre()); 
            }  
        });
	    
	    productoNivel2Item = crearSelectItem(listaProductosNivel2);
	    productoNivel2 = ((ProductoTipo)listaProductosNivel2.get(0)).getId();
	    productoNivel2Actual = (ProductoTipo)listaProductosNivel2.get(0);
	    manejadorProductoNivel2.setValue(productoNivel2);
	    	   
	    // valores para el ultimo nivel	    
	    ProductoTipo pro3 = new ProductoTipo();
	    pro3.setId(productoNivel2);	    
	    listaProductosNivel3 = new ArrayList();
	    List listaProductosNivel3Aux = servicioGeneral.obtenerHijos(pro3);
	    if(productosConvocatoria.size() != 0 && listaProductosNivel3Aux!=null){
			for(Iterator it = productosConvocatoria.iterator(); it.hasNext();){
				ProductoTipo pt = (ProductoTipo) it.next();
				for(Iterator ite = listaProductosNivel3Aux.iterator(); ite.hasNext();){
					ProductoTipo hijo = (ProductoTipo) ite.next();
					if(pt.getId().equals(hijo.getId())){
					    listaProductosNivel3.add(hijo);
					}
				}
			}
		}
	    
	    Collections.sort(listaProductosNivel3, new Comparator() {  
			  
            public int compare(Object o1, Object o2) {  
                ProductoTipo e1 = (ProductoTipo) o1;  
                ProductoTipo e2 = (ProductoTipo) o2;  
                return e1.getNombre().compareTo(e2.getNombre()); 
            }  
        });
	    
	    productoNivel3Item = crearSelectItem(listaProductosNivel3);
	    productoNivel3 = ((ProductoTipo)listaProductosNivel3.get(0)).getId(); 
	    productoNivel3Actual = (ProductoTipo)listaProductosNivel3.get(0);
				
	}
	
	public void cambiarProductoNivel2(ValueChangeEvent event) {		
	    productoNivel2Actual = buscarProductoTipoNivel2((String)event.getNewValue());
	    
	    ProductoTipo pro3 = new ProductoTipo();
	    pro3.setId(event.getNewValue().toString());	    
	    listaProductosNivel3 = new ArrayList();
	    List listaProductosNivel3Aux = servicioGeneral.obtenerHijos(pro3);
	    if(productosConvocatoria.size() != 0 && listaProductosNivel3Aux!=null){
			for(Iterator it = productosConvocatoria.iterator(); it.hasNext();){
				ProductoTipo pt = (ProductoTipo) it.next();
				for(Iterator ite = listaProductosNivel3Aux.iterator(); ite.hasNext();){
					ProductoTipo hijo = (ProductoTipo) ite.next();
					if(pt.getId().equals(hijo.getId())){
					    listaProductosNivel3.add(hijo);
					}
				}
			}
		}
	    
	    Collections.sort(listaProductosNivel3, new Comparator() {  
			  
            public int compare(Object o1, Object o2) {  
                ProductoTipo e1 = (ProductoTipo) o1;  
                ProductoTipo e2 = (ProductoTipo) o2;  
                return e1.getNombre().compareTo(e2.getNombre()); 
            }  
        });
	    
	    productoNivel3Item = crearSelectItem(listaProductosNivel3);
	    productoNivel3 = ((ProductoTipo)listaProductosNivel3.get(0)).getId(); 
	    productoNivel3Actual = (ProductoTipo)listaProductosNivel3.get(0);
	    manejadorProductoNivel3.setValue(productoNivel3);
	}
	
	public void cambiarProductoNivel3(ValueChangeEvent event) {		
		productoNivel3Actual = buscarProductoTipoNivel3((String)event.getNewValue());			    
	}

	public String siguiente()
	{
		try
		{
		    if(proyectoActual.getModalidad().getTipo().getId().equals(TipoModalidad.REGISTRO))
		    {

				if (proyectoActual.getId() != null) {
					//Ing. Wilver Alexander Mart�nez Mart�nez -wam�
					//Cambio - Registro de cambios
					Persona personaAux = new Persona();
					personaAux= (Persona) sesion.getAttribute("persona");
					
					Formulario formulario = new Formulario();
					List listaFormulario = new ArrayList();
					

					listaFormulario = servicioGeneral
							.obtenerListaObjetos("Formulario where id ='200'");
					formulario = (Formulario) listaFormulario.get(0);
					
					HistoricoFormularioProyecto historicoFormualrioProyecto = new HistoricoFormularioProyecto();
	                historicoFormualrioProyecto.setDocPersona(personaAux.getId().getDocumento());
	                historicoFormualrioProyecto.setTipoDocumentoPersona(personaAux.getId().getTipoDocumento());
	                historicoFormualrioProyecto.setFormulario(formulario);
	                historicoFormualrioProyecto.setProyecto(proyectoActual);
	                historicoFormualrioProyecto.setFechaCambio(new Date());
	                servicioGeneral.guardarObjeto(historicoFormualrioProyecto);
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
								link = lis[i].getOutcome();
								break;
							}
						}
						
						if(lis[i].getOutcome().equals("irProductos")){
							bandera = true;
						}
						if(lis[i].isRendered() ){
							pos++;
						}
					}
					}
				}
		    	
		    		    	
		        if((proyectoActual.getEstadoProyecto().getId()).equals("I") && (pos - 1) == proyectoActual.getFase().intValue())
	            {
		            //proyectoActual.setFase(new Integer((proyectoActual.getFase()).intValue() + 1));
		            proyectoActual.setFase(new Integer(proyectoActual.getFase().intValue() + 1));
		        }            
	            servicioProyecto.ingresarProyecto(proyectoActual);            
	            sesion.setAttribute("proyecto",proyectoActual);
	            
	          	sesion.removeAttribute("manejadorProductos");	          	
	          	// TODO juan pablo se valida que la modalidad sea 6 produccion 78 para q no vaya a evaluadores
	          	Modalidad m = proyectoActual.getModalidad();
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
						
						if(lis[i].getOutcome().equals("irProductos")){
							bandera = true;
						}
						
					}
					}
				}		
				//////////////////
				sesion.removeAttribute("manejadorMenuFormularios");
	          	if(m != null && m.getId().longValue() == 78){
	          	    return "irFuentes";
	          	}
	          	if(m.getTipo().getId().equals(TipoModalidad.REGISTRO))
	          	{
	          		return "irFuentes";
	          	}
	          		
	          	return "" ;
		    
		    }
		    else
		    {
		    	
				if(validarProductos()){
					 /////////MODIFICADO GIOVANNI
					
					if (proyectoActual.getId() != null) {
						//Ing. Wilver Alexander Mart�nez Mart�nez -wam�
						//Cambio - Registro de cambios
						Persona personaAux = new Persona();
						personaAux= (Persona) sesion.getAttribute("persona");
						
						Formulario formulario = new Formulario();
						List listaFormulario = new ArrayList();
						

						listaFormulario = servicioGeneral
								.obtenerListaObjetos("Formulario where id ='200'");
						formulario = (Formulario) listaFormulario.get(0);
						
						HistoricoFormularioProyecto historicoFormualrioProyecto = new HistoricoFormularioProyecto();
		                historicoFormualrioProyecto.setDocPersona(personaAux.getId().getDocumento());
		                historicoFormualrioProyecto.setTipoDocumentoPersona(personaAux.getId().getTipoDocumento());
		                historicoFormualrioProyecto.setFormulario(formulario);
		                historicoFormualrioProyecto.setProyecto(proyectoActual);
		                historicoFormualrioProyecto.setFechaCambio(new Date());
		                servicioGeneral.guardarObjeto(historicoFormualrioProyecto);
					}
					
					
										
					
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
									link = lis[i].getOutcome();
									break;
								}
							}
							
							if(lis[i].getOutcome().equals("irProductos")){
								bandera = true;
							}
							if(lis[i].isRendered() ){
								pos++;
							}
						}
						}
					}
			    	
			    		    	
			        if((proyectoActual.getEstadoProyecto().getId()).equals("I") && (pos - 1) == proyectoActual.getFase().intValue())
		            {
			            //proyectoActual.setFase(new Integer((proyectoActual.getFase()).intValue() + 1));
			            proyectoActual.setFase(new Integer((proyectoActual.getFase()).intValue() + 1));
			        }            
		            servicioProyecto.ingresarProyecto(proyectoActual);            
		            sesion.setAttribute("proyecto",proyectoActual);
		           
		          	sesion.removeAttribute("manejadorProductos");	
		          	
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
							
							if(lis[i].getOutcome().equals("irProductos")){
								bandera = true;
							}
							
						}
						}
					}		
					//////////////////
					 sesion.removeAttribute("manejadorMenuFormularios");
		          	
		          	// TODO juan pablo se valida que la modalidad sea 6 produccion 78 para q no vaya a evaluadores
		          	Modalidad m = proyectoActual.getModalidad();
		          	if(m != null && m.getId().longValue() == 78){
		          	    return "irFuentes";
		          	}
		          	if(m.getTipo().getId().equals(TipoModalidad.REGISTRO))
		          	{
		          		return "irFuentes";
		          	}
		          	return "" ;
			    }else{
			    	FacesContext.getCurrentInstance().addMessage("mensajeError", new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se han adicionado productos", ""));
			        mensajeError = "No se han adicionado productos";
			    }
                    
		    }
	    }
        catch(Exception e)
		{
        	e.printStackTrace();
		}
        return "";
	}

	 
	public void adicionarProducto(){
	    int c;
	    try{
	        c = Integer.parseInt(cantidad);
	            ProyectoProducto productoProyecto = new ProyectoProducto();
		    ProductoTipo p = buscarProductoTipoNivel3(productoNivel3);
		    productoProyecto.setProducto(p);
		    productoProyecto.setCantidad(c);
		    if(this.esProgramaNacional){
		    	productoProyecto.setDescripcion(descripcion);
		    }
		    
		    Set listaProductos = proyectoActual.getProductosProyecto();
		    boolean existeProducto = existeProductoEnSet(listaProductos,productoProyecto);
		    if(!existeProducto){
			 	proyectoActual.adicionarProductoProyecto(productoProyecto);
			        cantidad = "1";
			        descripcion = "";
			        mensajeErrorCantidad = "";
			        mensajeError = "";
		    }
		    
	       
	    }catch(Exception e){
	    	FacesContext.getCurrentInstance().addMessage("mensajeErrorCantidad", new FacesMessage(FacesMessage.SEVERITY_ERROR,"La cantidad no es un n�mero valido", ""));
	        mensajeErrorCantidad = "La cantidad no es un n�mero valido";
	    }
	    
	}
	
	public boolean existeProductoEnSet(Set productos,  ProyectoProducto productoProyecto ){
	        Iterator it = productos.iterator();
	        while(it.hasNext()){
	            Object obj = it.next();
	            if( obj instanceof ProyectoProducto ){
	                if( ((ProyectoProducto)obj).getProducto().getNombre().toUpperCase().equals(productoProyecto.getProducto().getNombre().toUpperCase())){
	                    return true;
	                }
	            }
	        }
	        return false;
	}
	
	public void eliminarProducto(){
	    /*
		ProyectoProducto producto = (ProyectoProducto)(tablaProductos.getRowData());
	    //((ProyectoProducto)listaActividades.get(tablaActividades.getRowIndex())).setBorrable(true);
    	proyectoActual.borrarProductoProyecto(producto);
    	*/
		proyectoActual.borrarProductoProyecto(productoSeleccionado);
	}
	
    private boolean validarProductos() {            
        if(proyectoActual.getProductosProyecto().size() == 0){
            return false;
        }
        return true;
    }


    public String getAvisoProductos() {
		return avisoProductos;
	}
	public void setAvisoProductos(String avisoProductos) {
		this.avisoProductos = avisoProductos;
	}
	

    public String atras()
	{
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
				
				if(lis[i].getOutcome().equals("irProductos")){
					bandera = true;
				}
				
			}
			}
		}		
		//////////////////
        return "irAreasTematicas" ;		
	}

	public String salir()
	{
		sesion.removeAttribute("proyecto");
		borrarManejadoresInsercionProyecto();
		return "misProyectos" ;
	}
	
	    
    public String getProductoNivel1() {
        return productoNivel1;
    }
    public void setProductoNivel1(String productoNivel1) {
        this.productoNivel1 = productoNivel1;
    }
    public SelectItem[] getProductoNivel1Item() {
        return productoNivel1Item;
    }
    public void setProductoNivel1Item(SelectItem[] productoNivel1Item) {
        this.productoNivel1Item = productoNivel1Item;
    }
    public String getProductoNivel2() {
        return productoNivel2;
    }
    public void setProductoNivel2(String productoNivel2) {
        this.productoNivel2 = productoNivel2;
    }
    public SelectItem[] getProductoNivel2Item() {
        return productoNivel2Item;
    }
    public void setProductoNivel2Item(SelectItem[] productoNivel2Item) {
        this.productoNivel2Item = productoNivel2Item;
    }
    public String getProductoNivel3() {
        return productoNivel3;
    }
    public void setProductoNivel3(String productoNivel3) {
        this.productoNivel3 = productoNivel3;
    }
    public SelectItem[] getProductoNivel3Item() {
        return productoNivel3Item;
    }
    public void setProductoNivel3Item(SelectItem[] productoNivel3Item) {
        this.productoNivel3Item = productoNivel3Item;
    }
    public UISelectOne getManejadorProductoNivel1() {
        return manejadorProductoNivel1;
    }
    public void setManejadorProductoNivel1(UISelectOne manejadorProductoNivel1) {
        this.manejadorProductoNivel1 = manejadorProductoNivel1;
    }
    public UISelectOne getManejadorProductoNivel2() {
        return manejadorProductoNivel2;
    }
    public void setManejadorProductoNivel2(UISelectOne manejadorProductoNivel2) {
        this.manejadorProductoNivel2 = manejadorProductoNivel2;
    }
    public UISelectOne getManejadorProductoNivel3() {
        return manejadorProductoNivel3;
    }
    public void setManejadorProductoNivel3(UISelectOne manejadorProductoNivel3) {
        this.manejadorProductoNivel3 = manejadorProductoNivel3;
    }
//    public UIData getTablaProductos() {
//        return tablaProductos;
//    }
//    public void setTablaProductos(UIData tablaProductos) {
//        this.tablaProductos = tablaProductos;
//    }
    
    
    
    public ProductoTipo getProductoNivel1Actual() {
        return productoNivel1Actual;
    }
    public DataTable getTablaProductos() {
		return tablaProductos;
	}

	public void setTablaProductos(DataTable tablaProductos) {
		this.tablaProductos = tablaProductos;
	}

	public void setProductoNivel1Actual(ProductoTipo productoNivel1Actual) {
        this.productoNivel1Actual = productoNivel1Actual;
    }
    public ProductoTipo getProductoNivel2Actual() {
        return productoNivel2Actual;
    }
    public void setProductoNivel2Actual(ProductoTipo productoNivel2Actual) {
        this.productoNivel2Actual = productoNivel2Actual;
    }
    public ProductoTipo getProductoNivel3Actual() {
        return productoNivel3Actual;
    }
    public void setProductoNivel3Actual(ProductoTipo productoNivel3Actual) {
        this.productoNivel3Actual = productoNivel3Actual;
    }
    
    public String getCantidad() {
        return cantidad;
    }
    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
    public String getMensajeErrorCantidad() {
        return mensajeErrorCantidad;
    }
    public void setMensajeErrorCantidad(String mensajeErrorCantidad) {
        this.mensajeErrorCantidad = mensajeErrorCantidad;
    }
    public String getMensajeError() {
        return mensajeError;
    }
    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
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

	public boolean isEsProgramaNacional() {
		return esProgramaNacional;
	}

	public void setEsProgramaNacional(boolean esProgramaNacional) {
		this.esProgramaNacional = esProgramaNacional;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public ProyectoProducto getProductoSeleccionado() {
		return productoSeleccionado;
	}

	public void setProductoSeleccionado(ProyectoProducto productoSeleccionado) {
		this.productoSeleccionado = productoSeleccionado;
	}
    
}
