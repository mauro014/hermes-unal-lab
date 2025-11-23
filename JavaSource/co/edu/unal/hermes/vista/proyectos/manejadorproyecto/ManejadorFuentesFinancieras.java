/**
* @author  Ing Hernán Darío Bernal Parra
*/

package co.edu.unal.hermes.vista.proyectos.manejadorproyecto;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.menuitem.MenuItem;

import co.edu.unal.hermes.bd.imp.ModalidadDAOHibernate;
import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.Contrapartida;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.Financiacion;
import co.edu.unal.hermes.modelo.Formulario;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.JornadaDocente;
import co.edu.unal.hermes.modelo.Modalidad;
import co.edu.unal.hermes.modelo.ModalidadFuenteFinanciacion;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.ProyectoProducto;
import co.edu.unal.hermes.modelo.Registro;
import co.edu.unal.hermes.modelo.RestriccionConvocatoria;
import co.edu.unal.hermes.vista.proyectos.ManejadorMenuFormularios;

public class ManejadorFuentesFinancieras extends ManejadorProyecto{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DataTable          tablaFinanciacion;       //TABLA PARA MOSTRAR LAS FUENTES DE FINANCIACION ASOCIADAS AL PROYECTO
    private Integer            tipoFuenteActual;        //TIPO DE FUENTE DE FINANCIACION SELECIONADA

    private FuenteFinanciacion fuenteFinancieraActual;  //FUENTE FINANCIERA ESCOGIDA
    private Financiacion       financiacionActual;      //DATOS DE LA FINANCIACION DADA POR LA FUENTE FINANCIERA ACTUAL
    private SelectItem[]       fuentesFinancierasItem;  //LISTA DE FUENTES FINANCIERAS A MOSTRAR
    private SelectItem[]       tiposFuenteItem=null ;
    private List      listaFinanciaciones ;             //LISTA DE FINANCIACIONES ASOCIDAS AL PROYECTO
    private List      listaFuentesInternas;             //LISTA GENERAL DE FUENTES DE FINANCIACION INTERNAS
    private List      listaFuentesExternas;             //LISTA GENERAL DE FUENTES DE FINANCIACION EXTERNAS
    private List      listaAuxiliarFuentes;             //LISTA GENERAL DE FUENTES DE FINANCIACION
    private String    mensajeMontoConvocatoria="";
    private long      sumaFinanciacionInterna =0;
    private long      sumaFinanciacionExterna =0;
    private long      sumaFinanciacionTotal   =0;

    List listaExternas;
    private DataTable tablaExternas;
    private String nombreFuenteExterna;

    private FuenteFinanciacion fuenteFinanciacionSeleccionada;
    private Financiacion fuenteSeleccionada;
    private Financiacion fuenteExternaSeleccionada;


    private String 	  titulo1;
    private String       titulo2;
    private boolean banderaExterna = false;
    private String nombreExterna = "";
    private String mensajeExterna = "";
    private String mensajeExternaUno = "";
    
    private boolean esExtensionSolidaria = false;
    private List listaRol;
    private String rol;

   

	public ManejadorFuentesFinancieras()
	{
		 super();
		 
		 titulo1 ="Proyecto:";
		 titulo2="Búsqueda de Integrantes del Proyecto";
		 
		 idManejador            = FUENTES_FINANCIERAS;
		 fuenteFinancieraActual = new FuenteFinanciacion();
	     financiacionActual     = new Financiacion();
	     listaFuentesExternas   = new ArrayList();
	     listaFuentesInternas   = new ArrayList();
	     listaAuxiliarFuentes   = new ArrayList();
	     listaFinanciaciones    = new ArrayList();
	     tipoFuenteActual       = new Integer(1);	    
         tiposFuenteItem= new SelectItem[2];
         tiposFuenteItem[0]=new SelectItem(new Integer(1), "Interna");
         tiposFuenteItem[1]=new SelectItem(new Integer(2), "Externa");                                          //LISTA DE TIPOS DE FUENTES DE FINANCIACION
	     financiacionActual.setValor(new Long(0));
	     cargarFuentesFinancieras();
	     proyectoActual=servicioProyecto.obtenerProyecto(proyectoActual.getId(), ProyectoDAOHibernate.FUENTES_FINANCIACION);	                
	     listaFinanciaciones.addAll(proyectoActual.getFinanciaciones());	   
	     
	     if(proyectoActual.getModalidad().getTipo().getId().compareTo("PN") == 0){
			  titulo1 ="Programa:";
			  titulo2="Integrantes del programa";
		  }else{
			  titulo1 ="Proyecto:";
			  titulo2="Integrantes del proyecto de investigación";
		  }
	     if (proyectoActual.getModalidad() instanceof Convocatoria) {
	    	 System.out.println("Extensión Solidaria");
	    	 RestriccionConvocatoria r = ((Convocatoria) proyectoActual.getModalidad()).getRestriccion();
	    	 if (r != null) {
	    		 System.out.println(r.getId());
	    		 if (r.getId().equals(RestriccionConvocatoria.CONV_EXT_SOL)) {
	    			 
	    			 esExtensionSolidaria = true;
	    			 cargarRol();

	    		 }
	    	 }
	 	} else {
	 	    System.out.println("no es Extensión Solidaria");
	 	}
	     
	     listaExternas = new ArrayList();
	}

	//DEFINICION DE FUNCIONES BASICAS
	protected void  cargarValoresIniciales()
	{		 	 
	}


	public String atras(){
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
				
				if(lis[i].getOutcome().equals("irFuentes")){
					bandera = true;
				}
				
			}
			}
		}		
		//////////////////
		return "irEvaluadores" ;
	}

	public String salir(){
		sesion.removeAttribute("proyecto");
		borrarManejadoresInsercionProyecto();
		return "misProyectos";
	}

	public String salirGuardar(){
		if(validarMontoConvocatoria())
		{
			
		/////MODIFICADO GIOVANNI
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
					
					if(lis[i].getOutcome().equals("irFuentes")){
						bandera = true;
					}
					if(lis[i].isRendered() ){
						pos++;
					}
				}
				}
			}
			
			
		   if((proyectoActual.getEstadoProyecto().getId()).equals("I") && (pos-1) >= proyectoActual.getFase().intValue())
           {proyectoActual.setFase(new Integer((proyectoActual.getFase()).intValue() + 1));}
		   
		   
		   if (proyectoActual.getId() != null) {
				//Ing. Wilver Alexander Martínez Martínez -wam²
				//Cambio - Registro de cambios
				Persona personaAux = new Persona();
				personaAux= (Persona) sesion.getAttribute("persona");
				
				Formulario formulario = new Formulario();
				List listaFormulario = new ArrayList();
				

				listaFormulario = servicioGeneral
						.obtenerListaObjetos("Formulario where id ='280'");
				formulario = (Formulario) listaFormulario.get(0);
				
				/*EstudianteProyecto estudianteProyecto = new EstudianteProyecto();
				estudianteProyecto.setEstudiante(personaAux.getId().getDocumento());
				estudianteProyecto.setTipoDocumentoEst(personaAux.getId().getTipoDocumento());
				estudianteProyecto.setFormulario(formulario);
				estudianteProyecto.setProyecto(proyectoActual);
				estudianteProyecto.setFechaCambio(new Date());
				servicioGeneral.guardarObjeto(estudianteProyecto);*/
			}
		   
		   
		   
		   
           servicioProyecto.ingresarProyecto(proyectoActual);
           sesion.removeAttribute("proyecto");
	       sesion.removeAttribute("manejadorMenuFormularios");
           borrarManejadoresInsercionProyecto();
   		   return "misProyectos";
	    }
		return "";
	}

	public String siguiente(){
		
	    Modalidad m =proyectoActual.getModalidad();
	    if(m instanceof Convocatoria || m instanceof Registro)
	    {
	        
		    if(validarMontoConvocatoria())
			{
		    	
		    	
		    /////MODIFICADO GIOVANNI
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
						
						if(lis[i].getOutcome().equals("irFuentes")){
							bandera = true;
						}
						if(lis[i].isRendered() ){
							pos++;
						}
					}
					}
				}
		    	
			   if((proyectoActual.getEstadoProyecto().getId()).equals("I") && (pos - 1) == proyectoActual.getFase().intValue())
	           {proyectoActual.setFase(new Integer((proyectoActual.getFase()).intValue() + 1));}
			   
			   
			   if (proyectoActual.getId() != null) {
					//Ing. Wilver Alexander Martínez Martínez -wam²
					//Cambio - Registro de cambios
					Persona personaAux = new Persona();
					personaAux= (Persona) sesion.getAttribute("persona");
					
					Formulario formulario = new Formulario();
					List listaFormulario = new ArrayList();
					

					listaFormulario = servicioGeneral
							.obtenerListaObjetos("Formulario where id ='280'");
					formulario = (Formulario) listaFormulario.get(0);
					
					/*EstudianteProyecto estudianteProyecto = new EstudianteProyecto();
					estudianteProyecto.setEstudiante(personaAux.getId().getDocumento());
					estudianteProyecto.setTipoDocumentoEst(personaAux.getId().getTipoDocumento());
					estudianteProyecto.setFormulario(formulario);
					estudianteProyecto.setProyecto(proyectoActual);
					estudianteProyecto.setFechaCambio(new Date());
					servicioGeneral.guardarObjeto(estudianteProyecto);*/
				}
			   
			   
			   
			   
			   
	           servicioProyecto.ingresarProyecto(proyectoActual);
	           sesion.setAttribute("proyecto",proyectoActual);
		      
			   sesion.removeAttribute("manejadorFuentesFinancieras");
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
						
						if(lis[i].getOutcome().equals("irFuentes")){
							bandera = true;
						}
						
					}
					}
				}		
				//////////////////
				 sesion.removeAttribute("manejadorMenuFormularios");
			   return "irRubros";
		    }
	    }
	    else
	    {
		    if(validarMontoConvocatoria())
			{
			   if( (proyectoActual.getEstadoProyecto().getId()).equals("I"))
	           {proyectoActual.setFase(new Integer(proyectoActual.getFase().intValue() + 1));}
	           servicioProyecto.ingresarProyecto(proyectoActual);
	           sesion.setAttribute("proyecto",proyectoActual);
		      
			   sesion.removeAttribute("manejadorFuentesFinancieras");
				/////////MODIFICADO GIOVANNI
				ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
				boolean bandera = false;			
				if(man.getItemProyecto() != null ){
					//NavigationMenuItem lis[] = man.getItemProyecto()[0].getNavigationMenuItems();
					MenuItem lis[] = man.getMenuItemArray();
					if(lis != null){
					for (int i = 0; i < lis.length; i++) {									
						if(bandera){	
							if(lis[i].isRendered()){
								sesion.removeAttribute("manejadorMenuFormularios");
							return lis[i].getOutcome();
							}
						}
						
						if(lis[i].getOutcome().equals("irFuentes")){
							bandera = true;
						}
						
					}
					}
				}		
				//////////////////
				 sesion.removeAttribute("manejadorMenuFormularios");
			   return "irSubirArchivo";
		    }
	    }
		return "";
	}
	
public String siguienteUno(){
		
	    Modalidad m =proyectoActual.getModalidad();
	    if(m instanceof Convocatoria || m instanceof Registro)
	    {
	        
		    if(validarMontoConvocatoriaUno())
			{
		    	
		    	
		    /////MODIFICADO GIOVANNI
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
						
						if(lis[i].getOutcome().equals("irFuentes")){
							bandera = true;
						}
						if(lis[i].isRendered() ){
							pos++;
						}
					}
					}
				}
		    	
			   if((proyectoActual.getEstadoProyecto().getId()).equals("I") && (pos - 1) == proyectoActual.getFase().intValue())
	           {proyectoActual.setFase(new Integer((proyectoActual.getFase()).intValue() + 1));}
	           servicioProyecto.ingresarProyecto(proyectoActual);
	           sesion.setAttribute("proyecto",proyectoActual);
		      
			   sesion.removeAttribute("manejadorFuentesFinancieras");
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
						
						if(lis[i].getOutcome().equals("irFuentes")){
							bandera = true;
						}
						
					}
					}
				}		
				//////////////////
				 sesion.removeAttribute("manejadorMenuFormularios");
			   return "irRubros";
		    }
	    }
	    else
	    {
		    if(validarMontoConvocatoriaUno())
			{
			   if( (proyectoActual.getEstadoProyecto().getId()).equals("I"))
	           {proyectoActual.setFase(new Integer(proyectoActual.getFase().intValue() + 1));}
	           servicioProyecto.ingresarProyecto(proyectoActual);
	           sesion.setAttribute("proyecto",proyectoActual);
		      
			   sesion.removeAttribute("manejadorFuentesFinancieras");
				/////////MODIFICADO GIOVANNI
				ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
				boolean bandera = false;			
				if(man.getItemProyecto() != null ){
					//NavigationMenuItem lis[] = man.getItemProyecto()[0].getNavigationMenuItems();
					MenuItem lis[] = man.getMenuItemArray();
					if(lis != null){
					for (int i = 0; i < lis.length; i++) {									
						if(bandera){	
							if(lis[i].isRendered()){
								sesion.removeAttribute("manejadorMenuFormularios");
							return lis[i].getOutcome();
							}
						}
						
						if(lis[i].getOutcome().equals("irFuentes")){
							bandera = true;
						}
						
					}
					}
				}		
				//////////////////
				 sesion.removeAttribute("manejadorMenuFormularios");
			   return "irSubirArchivo";
		    }
	    }
		return "";
	}

	//FUNCIONES ESPECIFICAS DE LA CLASE
	public void cambiarFuentes(ValueChangeEvent event){
     intercambiarFuentes((Integer.valueOf((event.getNewValue()).toString())).intValue());
    }

	private void intercambiarFuentes(int tipoFuente){
    	//CAMBIANDO FUENTES FINANCIERAS DE ACUERDO AL TIPO DE FUENTE (INTERNA O EXTERNA)
        tipoFuenteActual=new Integer(tipoFuente);
        try{
        	if(tipoFuenteActual.intValue()==1)
        	{
        		//fuentesFinancierasItem = new SelectItem[1];
        		if(listaFuentesInternas.size()>0) {
        			fuentesFinancierasItem = new SelectItem[listaFuentesInternas.size()];
        			for (int i = 0; i < listaFuentesInternas.size(); i++) {           
        				FuenteFinanciacion ff = (FuenteFinanciacion) listaFuentesInternas.get(i);
        				//                   if(ff.getId().equals("1")){
        				//                     fuentesFinancierasItem[0] = new SelectItem(ff.getId(), ff.getDescripcion());
        				//                   }
        				//                   ff=null;
        				fuentesFinancierasItem[i]=new SelectItem(ff.getId(),ff.getDescripcion());
        			}
        			fuenteFinancieraActual =(FuenteFinanciacion)(((FuenteFinanciacion)(listaFuentesInternas.get(0))).clone());
        		}else{
        			fuentesFinancierasItem = new SelectItem[1];
        			fuentesFinancierasItem[0]=new SelectItem("0","");
        			//fuenteFinancieraActual =(FuenteFinanciacion)(((FuenteFinanciacion)(listaFuentesInternas.get(0))).clone());
        		}

        	}

        	if(tipoFuenteActual.intValue()==2)
        	{
        		if(listaFuentesExternas.size()>0) {
        			fuentesFinancierasItem = new SelectItem[listaFuentesExternas.size()];
        			for (int i = 0; i < listaFuentesExternas.size(); i++) {
        				FuenteFinanciacion ff = (FuenteFinanciacion) listaFuentesExternas.get(i);
        				fuentesFinancierasItem[i] = new SelectItem(ff.getId(), ff.getDescripcion());
        				ff=null;
        			}
        			fuenteFinancieraActual =(FuenteFinanciacion)(((FuenteFinanciacion)(listaFuentesExternas.get(0))).clone());
        		}
        		else{
            		fuentesFinancierasItem = new SelectItem[1];
            		fuentesFinancierasItem[0]=new SelectItem("0","");
            		//fuenteFinancieraActual =(FuenteFinanciacion)(((FuenteFinanciacion)(listaFuentesInternas.get(0))).clone());
            	}
        	}        	
        	
        }
        catch(Exception e)
		{
         System.out.println("ManejadorInfoFinanciera:intercambiarFuentes:Error Intercambiando Las Fuentes Financieras");
         e.printStackTrace();
	    }
    }

	private void cargarFuentesFinancieras(){
//    	if(proyectoActual.getModalidad().getTipo().getId().equals(ModalidadDAOHibernate.Contrapartida))
//    	{
//    	    Contrapartida contrapartida=(Contrapartida) proyectoActual.getModalidad();
//    	    FuenteFinanciacion f=(FuenteFinanciacion) servicioGeneral.obtenerObjeto(new FuenteFinanciacion(),contrapartida.getFinanciacionExterna().getId());
//    	    listaFuentesExternas= new Vector();
//    	    listaFuentesExternas.add(f);
//    	}
//    	else
//    	{
//    	    listaFuentesExternas = servicioProyecto.obtenerFuenteFinanciacionExterna();
//    	}
		// listaFuentesExternas = servicioProyecto.obtenerFuenteFinanciacionExterna();
    	// listaFuentesInternas = servicioProyecto.obtenerFuenteFinanciacionInterna();
		
    	Modalidad modalidad = proyectoActual.getModalidad();
        List listaFinanciacionesDeConvocatoria=servicioModalidad.listaModFuenteFinXModalidad(modalidad.getId());
        
        if(listaFinanciacionesDeConvocatoria.size()>0){

            for(Iterator it = listaFinanciacionesDeConvocatoria.iterator(); it.hasNext();){
            	ModalidadFuenteFinanciacion mff= (ModalidadFuenteFinanciacion) it.next();
            	FuenteFinanciacion ff = (FuenteFinanciacion)(servicioGeneral.obtenerObjeto(new FuenteFinanciacion(),mff.getFuenteFinanciacion().getId()));
            	
            	if(ff.getInternaExterna()!=null){        	    
            	    if(ff.getInternaExterna().equalsIgnoreCase(FuenteFinanciacion.interna))	
            		listaFuentesInternas.add(ff);
            	    else listaFuentesExternas.add(ff);
            	}
            }
        	
            listaAuxiliarFuentes.addAll(listaFuentesInternas);
            listaAuxiliarFuentes.addAll(listaFuentesExternas);
            
            FuenteFinanciacion fuenteFinanciacion = (FuenteFinanciacion) listaAuxiliarFuentes.get(0);
           
                
                if(fuenteFinanciacion.getInternaExterna().equalsIgnoreCase(FuenteFinanciacion.interna)) 
                	intercambiarFuentes(1);                    	
                else 
                	intercambiarFuentes(2);
             
            financiacionActual.setFuente(fuenteFinanciacion);
            financiacionActual.setValor(new Long(0));
        }
        
    }

	private FuenteFinanciacion buscarFuente(String id){
		 //BUSCA UNA FUENTE DE FINANCIACION DE ACUERDO AL ID
	     int i=0;
	     FuenteFinanciacion ff=new FuenteFinanciacion();
	     while(i<listaAuxiliarFuentes.size())
	     {
	      ff = (FuenteFinanciacion) listaAuxiliarFuentes.get(i);
	      if(id.equals(ff.getId()))
	       break;
	      else
	       ff=null;
	      i=i+1;
	     }
	     return ff;
    }

	private Financiacion buscarFinanciacionPorFuente(Long idTipo){
		 //BUSCA UNA FINANCIACION DE ACUERDO AL ID DE LA FUENTE
	     Financiacion f=null;
	     int i=0;
	     while(i<listaFinanciaciones.size())
	     {
	      f=(Financiacion) listaFinanciaciones.get(i);
	      FuenteFinanciacion ff = f.getFuente();
	      if(idTipo.longValue()==Long.parseLong(ff.getId()))
	      	break;
	      else
	      	f=null;
	      i=i+1;
	     }
	     return f;
    }

	 public void ingresarFuenteExterna(){
		 banderaExterna = true;
	 }
	 
	 public void consultarFuentesExternas(){
		 this.mensajeExterna ="";
		 if(nombreExterna!=null && nombreExterna.length()>0){
				listaExternas = servicioGeneral.obtenerListaObjetos("FuenteFinanciacion where upper(descripcion) like '%"+nombreExterna.toUpperCase()+"%' order by id");
			}else{
				listaExternas = new ArrayList();
			}
		 
	 }
	 
	 public void cargarRol(){
		 
		 listaRol	 = new ArrayList<SelectItem>();
		 String consulta = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.id = 48";
		 List lista = servicioGeneral.obtenerObjetos(consulta);	    

		 for (int i=0; i<lista.size();i++){
			 DominioDetalle dominio = (DominioDetalle) lista.get(i);
			 listaRol.add(new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion()));
		 }	    	    
		 
	 }
	 
	 public void agregarNuevaFuentesExterna(){
		 this.mensajeExterna = "";
		 if(nombreFuenteExterna !=null && nombreFuenteExterna.length()>0){
			 FuenteFinanciacion fuenteNueva = new FuenteFinanciacion();
			 fuenteNueva.setInternaExterna("E");
			 fuenteNueva.setDescripcion(nombreFuenteExterna.toUpperCase());
			 
			 String hql = "select ff Fuente_financiacion ff where UPPER(ff.FFI_DESCRIPCION) = UPPER('" + nombreFuenteExterna + "')";
			 List lista = servicioGeneral.obtenerObjetos(hql);
			 if(lista.size()>0){
			     	this.mensajeExterna = "La fuente ya se encuentra registrada en el sistema. Por favor vaya a la opción de 'Buscar fuente'.";
				FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_INFO, "La fuente ya se encuentra registrada en el sistema. Por favor vaya a la opción de 'Buscar fuente'", ""));
				banderaExterna = false; 
			 }else{
			 
			 //if(listaFinanciaciones !=null && listaFinanciaciones.size()>0){
			     try { //Financiacion finan = (Financiacion) listaFinanciaciones.get(0);
			    	servicioGeneral.guardarObjeto(fuenteNueva);
			    
				 ModalidadFuenteFinanciacion modalidadFuente = new ModalidadFuenteFinanciacion();
				 
					modalidadFuente.setFuenteFinanciacion(fuenteNueva);
					modalidadFuente.setModalidad(this.proyectoActual.getModalidad());
					modalidadFuente.setPorcentaje(new Double("100"));
					this.servicioGeneral.guardarObjeto(modalidadFuente);	
					
					boolean existeFuente = existeFuenteEnSet(listaAuxiliarFuentes,fuenteNueva);
					if(!existeFuente){
					    listaAuxiliarFuentes.add(fuenteNueva);
					    listaFuentesExternas.add(fuenteNueva);	
					 
					}
					
					   fuentesFinancierasItem = new SelectItem[listaFuentesExternas.size()];	
					    
					    for (int i = 0; i < listaFuentesExternas.size(); i++) {
					               FuenteFinanciacion ff = (FuenteFinanciacion) listaFuentesExternas.get(i);
					               fuentesFinancierasItem[i] = new SelectItem(ff.getId(), ff.getDescripcion());
					               ff=null;
					    }
					    fuenteFinancieraActual =(FuenteFinanciacion)(((FuenteFinanciacion)(listaFuentesExternas.get(0))).clone());
				
		           
					this.mensajeExterna = "La fuente se agrego satisfactoriamente";
					FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_INFO, "La fuente se agrego satisfactoriamente", ""));
					banderaExterna = false;
					
				} catch (CloneNotSupportedException e) {
					System.out.println(e.toString());
				}
			// }
			 
			 }
		 }
	 }
	 
	 public boolean existeFuenteEnSet(List fuentes,  FuenteFinanciacion fuenteNueva ){
	        Iterator it = fuentes.iterator();
	        while(it.hasNext()){
	            Object obj = it.next();
	            if( obj instanceof ProyectoProducto ){
	                if( ((FuenteFinanciacion)obj).getDescripcion().toUpperCase().equals(fuenteNueva.getDescripcion().toUpperCase())){
	                    return true;
	                }
	            }
	        }
	        return false;
	}
	 
	 public void agregarFuentesExternas(){
		 //FuenteFinanciacion fuente = (FuenteFinanciacion) listaExternas.get(tablaExternas.getRowIndex());
		 boolean banderaExiste = true;
		 
		 if(listaFuentesExternas != null && listaFuentesExternas.size() > 0){
			 for(int i=0; i< listaFuentesExternas.size(); i++){
				 FuenteFinanciacion actual = (FuenteFinanciacion) listaFuentesExternas.get(i);
				 if(fuenteFinanciacionSeleccionada.getId().equals(actual.getId()) || 
					 fuenteFinanciacionSeleccionada.getDescripcion().toUpperCase().equals(actual.getDescripcion().toUpperCase())){
					banderaExiste = false;					
				 }
			 }
		 }
			
		 if(banderaExiste){
				 try {
					 //if(listaFinanciaciones != null && listaFinanciaciones.size() > 0){
					 listaFuentesExternas.add(fuenteFinanciacionSeleccionada);	
					
					// Financiacion finan = (Financiacion) listaFinanciaciones.get(0);
					
					 ModalidadFuenteFinanciacion modalidadFuente = new ModalidadFuenteFinanciacion();
						modalidadFuente.setFuenteFinanciacion(fuenteFinanciacionSeleccionada);
						//modalidadFuente.setModalidad(finan.getProyecto().getModalidad());
						modalidadFuente.setModalidad(this.proyectoActual.getModalidad());
						modalidadFuente.setPorcentaje(new Double("100"));
						this.servicioGeneral.guardarObjeto(modalidadFuente);							
						this.mensajeExterna = "La fuente se agrego satisfactoriamente";
						FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_INFO, "La fuente se agrego satisfactoriamente", ""));
						banderaExterna = false;
						listaAuxiliarFuentes.add(fuenteFinanciacionSeleccionada);
					 
					 
        					 fuentesFinancierasItem = new SelectItem[listaFuentesExternas.size()];
        			           for (int i = 0; i < listaFuentesExternas.size(); i++) {
        			               FuenteFinanciacion ff = (FuenteFinanciacion) listaFuentesExternas.get(i);
        			               fuentesFinancierasItem[i] = new SelectItem(ff.getId(), ff.getDescripcion());
        			               ff=null;
        			           }
		           
					fuenteFinancieraActual =(FuenteFinanciacion)(((FuenteFinanciacion)(listaFuentesExternas.get(0))).clone());
					 
					// }
					
				} catch (CloneNotSupportedException e) {
					System.out.println(e.toString());
				}
			 }else{
				 this.mensajeExterna = "La fuente ya se encuentra agregada";
				 FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR, "La fuente ya se encuentra agregada", ""));
			 }
		
	 }
	
    //FUNCIONES DE INSERCION Y BORRADO EN LAS LISTAS ASOCIADAS
    public void ingresarFinanciacion(){
      Financiacion f=new Financiacion();
   
      try{
       FuenteFinanciacion ff=(FuenteFinanciacion)(buscarFuente(fuenteFinancieraActual.getId())).clone();
       f.setFuente(ff);
       f.setValor(new Long(0));
       f.setRol(rol);
       Financiacion ff1=(Financiacion)buscarFinanciacionPorFuente(Long.valueOf(fuenteFinancieraActual.getId()));
       if(ff1==null)
         {
    	      if(listaFinanciaciones==null ){
    	    	  listaFinanciaciones = new ArrayList();
    	    	  listaFinanciaciones.add(f);
    	      }else{
    	    	  listaFinanciaciones.add(f);  
    	      }       	      
       	      //intercambiarFuentes(1);
       	      
       	      mensajeMontoConvocatoria="";
         }       
      }
      catch(Exception e)
	  {
      	System.out.println("ManejadorInfoFinanciera:asociarFuente:Error hallando la fuente de financiacion especificada");
      	e.printStackTrace();
      }
    }

    public void eliminarFinanciacion(){
	fuenteSeleccionada.setBorrable(true);
    	if(fuenteSeleccionada != null){
    		listaFinanciaciones.remove(fuenteSeleccionada);
    		proyectoActual.borrarFinanciacion(fuenteSeleccionada);
    	}
    	
    }

    private boolean validarMontoConvocatoriaUno(){
    	return true;
    }
    
    //VALIDADORES
	private boolean validarMontoConvocatoria(){	    	        
         // VALIDA QUE LA SUMA DEL DINERO PEDIDO A LAS FUENTES DE FINANCIACION NO SEA MAYOR
		 //AL MONTO DE APOYO A GANADORES DE LA CONVOCATORIA
		 List listaAuxiliar=new ArrayList();
		 for(int i=0;i<listaFinanciaciones.size();i++)
		 {
		  Financiacion oe=(Financiacion)listaFinanciaciones.get(i);
		  if(oe.isBorrable())
		  {
		  	listaAuxiliar.add(oe);
		  	proyectoActual.borrarFinanciacion(oe);
		  }
		  else{proyectoActual.adicionarFinanciacion(oe);}
		 }
		 listaFinanciaciones.removeAll(listaAuxiliar);
	     if(listaFinanciaciones.isEmpty())
	     {
	         Modalidad modalidad = proyectoActual.getModalidad();
			  if(!(modalidad  instanceof JornadaDocente)){
			      this.mensajeMontoConvocatoria="No se han agregado fuentes financieras al proyecto";
			      FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se han agregado fuentes financieras al proyecto", ""));
			      return false;
			  }
	     }
	     else
	     {
	      sumaFinanciacionInterna=0;
	      sumaFinanciacionExterna=0;
	      sumaFinanciacionTotal  =0;
	      for(int i=0;i<listaFinanciaciones.size();i++)
	      {
	      	Financiacion f=(Financiacion)listaFinanciaciones.get(i);
	      	FuenteFinanciacion ff=f.getFuente();
	      	sumaFinanciacionTotal=sumaFinanciacionTotal+(f.getValor()).longValue();
	        if(ff.getInternaExterna().equals("I"))
	        {
	        	sumaFinanciacionInterna=sumaFinanciacionInterna+(f.getValor()).longValue();
	        }
	        else
	        {
	            sumaFinanciacionExterna+=(f.getValor()).longValue();
	        }
	      }
	      
	      // Juan Pablo:    Comprobar que sea una convocatoria
		  Modalidad modalidad = proyectoActual.getModalidad();
		  if(modalidad instanceof Convocatoria){
		      Convocatoria c=(Convocatoria)modalidad;		      
		      List listaFinanciacionesDeConvocatoria=servicioModalidad.listaModFuenteFinXModalidad(modalidad.getId());
		      for(Iterator itListaFuenteFinanciaciones=listaFinanciacionesDeConvocatoria.iterator();itListaFuenteFinanciaciones.hasNext();)
		      {
		          ModalidadFuenteFinanciacion mff= (ModalidadFuenteFinanciacion) itListaFuenteFinanciaciones.next();
		          double totalXFuenteFinanciacion=servicioModalidad.obtenerTotalDeListaFinanciacionXFuenteFinanciacion(listaFinanciaciones,mff.getFuenteFinanciacion().getId());
		          double montoApoyoGanador=new Double(c.getMontoApoyoGanadores()).doubleValue();
		          if(totalXFuenteFinanciacion > (mff.getPorcentaje().doubleValue()/100)*montoApoyoGanador)
		          {
		              FuenteFinanciacion ff=(FuenteFinanciacion) servicioGeneral.obtenerObjeto(new FuenteFinanciacion(),mff.getFuenteFinanciacion().getId());
		              double td=mff.getPorcentaje().doubleValue()*montoApoyoGanador/100;
		              NumberFormat nf= NumberFormat.getInstance();
		              
		              nf.setMaximumFractionDigits(2);
		              mensajeMontoConvocatoria="el total para la fuente de financiacion "+ff.getDescripcion()+" supera el disponible de "+(nf.format(td));
		              FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR, "El total para la fuente de financiacion "+ff.getDescripcion()+" supera el disponible de "+(nf.format(td)), ""));
		              return false;
		          }
		      }
		      
		      boolean tieneFuente=false;
		      List listaFuenteFinanciacion=servicioModalidad.obtenerListaFuentesFinanciacionXConvocatoria(c.getId());
		      for(Iterator it=listaFinanciaciones.iterator();it.hasNext();)
		      {
		          Financiacion f=(Financiacion) it.next();
		          if(listaFuenteFinanciacion.contains(f.getFuente())&& f.getValor().longValue()>0)
		          {
		              tieneFuente=true;
		          }
		      }
		      if(!tieneFuente)
		      {
		          mensajeMontoConvocatoria="No se ha agregado la fuente de financiación seleccionada, o no tiene un valor";
		          FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se ha agregado la fuente de financiación seleccionada, o no tiene un valor", ""));
		          return false;
		      }
		  
		  }      	      
		  if(modalidad instanceof Contrapartida)
	      {
	        /* if(sumaFinanciacionInterna > 25000000)
	         {
	             this.mensajeMontoConvocatoria="La financiacion interna no puede superar los 25 millones";
	             return false;
	         }
		      Contrapartida contrapartida= (Contrapartida)modalidad;
	         if((sumaFinanciacionExterna*0.5) < sumaFinanciacionInterna)
	         {
	             this.mensajeMontoConvocatoria="La financiacion Interna no puede ser mayor a el 50% de la financiacion externa";
	             return false;
	         }*/
	         
	      }
	     }	
	     this.mensajeMontoConvocatoria="";
	     return true;
    }

    //METODOS SET Y GET
	public boolean getEsContrapartida()
	{
	    if(proyectoActual.getModalidad().getTipo().getId().equals(ModalidadDAOHibernate.Contrapartida))
	    {
	        return true;
	    }
	    return false;
	}
	
	public Financiacion getFinanciacionActual() {
		return financiacionActual;
	}
	public void setFinanciacionActual(Financiacion financiacionActual) {
		this.financiacionActual = financiacionActual;
	}
	public FuenteFinanciacion getFuenteFinancieraActual() {
		return fuenteFinancieraActual;
	}
	public void setFuenteFinancieraActual(
			FuenteFinanciacion fuenteFinancieraActual) {
		this.fuenteFinancieraActual = fuenteFinancieraActual;
	}
	public SelectItem[] getFuentesFinancierasItem() {
		return fuentesFinancierasItem;
	}
	public void setFuentesFinancierasItem(SelectItem[] fuentesFinancierasItem) {
		this.fuentesFinancierasItem = fuentesFinancierasItem;
	}
	public List getListaFinanciaciones() {
		return listaFinanciaciones;
	}
	public void setListaFinanciaciones(List listaFinanciaciones) {
		this.listaFinanciaciones = listaFinanciaciones;
	}
	public List getListaFuentesExternas() {
		return listaFuentesExternas;
	}
	public void setListaFuentesExternas(List listaFuentesExternas) {
		this.listaFuentesExternas = listaFuentesExternas;
	}
	public List getListaFuentesInternas() {
		return listaFuentesInternas;
	}
	public void setListaFuentesInternas(List listaFuentesInternas) {
		this.listaFuentesInternas = listaFuentesInternas;
	}	
	public String getMensajeMontoConvocatoria() {
		return mensajeMontoConvocatoria;
	}
	public void setMensajeMontoConvocatoria(String mensajeMontoConvocatoria) {
		this.mensajeMontoConvocatoria = mensajeMontoConvocatoria;
	}
	public long getSumaFinanciacionInterna() {
		return sumaFinanciacionInterna;
	}
	public void setSumaFinanciacionInterna(long sumaFinanciacionInterna) {
		this.sumaFinanciacionInterna = sumaFinanciacionInterna;
	}
	public long getSumaFinanciacionTotal() {
		return sumaFinanciacionTotal;
	}
	public void setSumaFinanciacionTotal(long sumaFinanciacionTotal) {
		this.sumaFinanciacionTotal = sumaFinanciacionTotal;
	}
	public DataTable getTablaFinanciacion() {
		return tablaFinanciacion;
	}
	public void setTablaFinanciacion(DataTable tablaFinanciacion) {
		this.tablaFinanciacion = tablaFinanciacion;
	}
	public Integer getTipoFuenteActual() {
		return tipoFuenteActual;
	}
	public void setTipoFuenteActual(Integer tipoFuenteActual) {
		this.tipoFuenteActual = tipoFuenteActual;
	}
	public SelectItem[] getTiposFuenteItem() {
		return tiposFuenteItem;
	}
	public void setTiposFuenteItem(SelectItem[] tiposFuenteItem) {
		this.tiposFuenteItem = tiposFuenteItem;
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

	public boolean isBanderaExterna() {
		return banderaExterna;
	}

	public void setBanderaExterna(boolean banderaExterna) {
		this.banderaExterna = banderaExterna;
	}

	public String getNombreExterna() {
		return nombreExterna;
	}

	public void setNombreExterna(String nombreExterna) {
		this.nombreExterna = nombreExterna;
	}

	public List getListaAuxiliarFuentes() {
		return listaAuxiliarFuentes;
	}

	public void setListaAuxiliarFuentes(List listaAuxiliarFuentes) {
		this.listaAuxiliarFuentes = listaAuxiliarFuentes;
	}

	public long getSumaFinanciacionExterna() {
		return sumaFinanciacionExterna;
	}

	public void setSumaFinanciacionExterna(long sumaFinanciacionExterna) {
		this.sumaFinanciacionExterna = sumaFinanciacionExterna;
	}

	public List getListaExternas() {
		return listaExternas;
	}

	public void setListaExternas(List listaExternas) {
		this.listaExternas = listaExternas;
	}

	public DataTable getTablaExternas() {
		return tablaExternas;
	}

	public void setTablaExternas(DataTable tablaExternas) {
		this.tablaExternas = tablaExternas;
	}	
	
	 public String getMensajeExterna() {
	     return mensajeExterna;
	 }

	 public void setMensajeExterna(String mensajeExterna) {
	     this.mensajeExterna = mensajeExterna;
	 }

	 public String getNombreFuenteExterna() {
	     return nombreFuenteExterna;
	 }

	 public void setNombreFuenteExterna(String nombreFuenteExterna) {
	     this.nombreFuenteExterna = nombreFuenteExterna;
	 }

	 public String getMensajeExternaUno() {
	     return mensajeExternaUno;
	 }

	 public void setMensajeExternaUno(String mensajeExternaUno) {
	     this.mensajeExternaUno = mensajeExternaUno;
	 }

	 public Financiacion getFuenteSeleccionada() {
	     return fuenteSeleccionada;
	 }

	 public void setFuenteSeleccionada(Financiacion fuenteSeleccionada) {
	     this.fuenteSeleccionada = fuenteSeleccionada;
	 }

	public Financiacion getFuenteExternaSeleccionada() {
	    return fuenteExternaSeleccionada;
	}

	public void setFuenteExternaSeleccionada(Financiacion fuenteExternaSeleccionada) {
	    this.fuenteExternaSeleccionada = fuenteExternaSeleccionada;
	}

	public FuenteFinanciacion getFuenteFinanciacionSeleccionada() {
	    return fuenteFinanciacionSeleccionada;
	}

	public void setFuenteFinanciacionSeleccionada(FuenteFinanciacion fuenteFinanciacionSeleccionada) {
	    this.fuenteFinanciacionSeleccionada = fuenteFinanciacionSeleccionada;
	}

	public boolean isEsExtensionSolidaria() {
		return esExtensionSolidaria;
	}

	public void setEsExtensionSolidaria(boolean esExtensionSolidaria) {
		this.esExtensionSolidaria = esExtensionSolidaria;
	}

	public List getListaRol() {
		return listaRol;
	}

	public void setListaRol(List listaRol) {
		this.listaRol = listaRol;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}	
}
