
/**
* @author  Ing Hernán Darío Bernal Parra
*/

package co.edu.unal.hermes.vista.proyectos.manejadorproyecto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.menuitem.MenuItem;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.Formulario;
import co.edu.unal.hermes.modelo.HistoricoFormularioProyecto;
import co.edu.unal.hermes.modelo.ObjetivoEspecifico;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.RestriccionConvocatoria;
import co.edu.unal.hermes.modelo.ResultadoProyecto;
import co.edu.unal.hermes.vista.proyectos.ManejadorMenuFormularios;

public class ManejadorObjetivosResultados extends ManejadorProyecto{

    //COMPONENTES PARA OBJETIVOS ESPECIFICOS
    private List listaObjetivos;
    private DataTable tablaObjetivos;
    private String mensajeErrorObjetivos = "";

    //COMPONENTES PARA RESULTADOS
    private List listaResultados;
    private DataTable tablaResultados;
    private String mensajeErrorResultados = "";
    private boolean esProgramaNacional = false;
    private String 	  titulo1;
	private String       titulo2;
	private String       titulo3;
	private ObjetivoEspecifico objEspecificoSel;
	private ResultadoProyecto resultadoSel;
	private boolean esExtensionSolidaria = false;
	private List listaBeneficiosTodos;
	private List listaBeneficiosSeleccionados;
	private List listaBeneficiosSeleccionadosDescri;
	private String beneficiosSeleccionados;

	public ManejadorObjetivosResultados()
	{
	   super();
	   idManejador          = OBJETIVOS_RESULTADOS;
	   listaObjetivos       = new ArrayList();
       listaResultados      = new ArrayList();
       
       titulo1 ="Proyecto:";
       titulo2	="Objetivos y Resultados";
       titulo3	="Metodología/Estratégias";
       
       proyectoActual=servicioProyecto.obtenerProyecto(proyectoActual.getId(), ProyectoDAOHibernate.OBJETIVOS_RESULTADOS);    
       cargarValoresIniciales();
       
		 if(proyectoActual.getModalidad().getTipo().getId().compareTo("PN") == 0){
			this.esProgramaNacional = true;		
			titulo1 ="Programa:";
			titulo2	="Objetivo General";
			titulo3	="Estrategias/Mecanismos";
			
		 }else{
			 this.esProgramaNacional = false;	
			 titulo1 ="Proyecto:";
			 titulo2	="Objetivos y Resultados";
			 titulo3	="Metodología/Estratégias";
		 }
	  
		if (proyectoActual.getModalidad() instanceof Convocatoria) {
		    System.out.println("Extensión Solidaria");
		    RestriccionConvocatoria r = ((Convocatoria) proyectoActual.getModalidad()).getRestriccion();
		    if (r != null) 
		    {
				System.out.println(r.getId());
				if (r.getId().equals(RestriccionConvocatoria.CONV_EXT_SOL)) 
				{
				    esExtensionSolidaria = true;
				    beneficiosSeleccionados = proyectoActual.getBeneficiosExtSol();
			    	cargarBeneficiosTodos();
			    	
			    	if(beneficiosSeleccionados != null)
				    {
				    	if(!beneficiosSeleccionados.equals(""))
				    	{	
						    listaBeneficiosSeleccionados = new ArrayList();
						    listaBeneficiosSeleccionadosDescri = new ArrayList();
						    String[] listaString = listaString = beneficiosSeleccionados.split(";");
						    
						    for (int i = 0; i < listaString.length; i++) 
						    {
						    	String consulta = "select dd from DominioDetalle dd where dd.identificador.id = '"
										+ 47 + "' and dd.identificador.tipo like '"+listaString[i]+"'";
								List lista = servicioGeneral.obtenerObjetos(consulta);
								DominioDetalle dominio = (DominioDetalle) lista.get(0);
								
						    	listaBeneficiosSeleccionados.add(listaString[i]);
						    	listaBeneficiosSeleccionadosDescri.add(dominio.getDescripcion());
							}
				    	}    
				    }
				}
		    }
		} else {
		    System.out.println("no es Extensión Solidaria");
		}
		 
	}

	//DEFINICION DE FUNCIONES BASICAS
	protected void  cargarValoresIniciales(){
		listaObjetivos.addAll(proyectoActual.getObjetivosEspecificos());
	    listaResultados.addAll(proyectoActual.getResultados());
	}
	
	private void cargarBeneficiosTodos()
	{
		listaBeneficiosTodos = new ArrayList<SelectItem>();
		
		String consulta = "select dd from DominioDetalle dd where dd.identificador.id = '"
				+ 47 + "'";
		List lista = servicioGeneral.obtenerObjetos(consulta);
		
		for (int i = 0; i < lista.size(); i++) {
			DominioDetalle dominio = (DominioDetalle) lista.get(i);
			listaBeneficiosTodos.add(new SelectItem(dominio.getIdentificador().getTipo(),dominio.getDescripcion()));
		}
		
		
		
	}
	
	private void concatenarBeneficiosSeleccionados()
	{
		
		if(listaBeneficiosSeleccionados != null)
		{
			DominioDetalle dominio;
			beneficiosSeleccionados="";
			
			SelectItem select;
			
			for (int i = 0; i < listaBeneficiosSeleccionados.size(); i++) 
			{
//				dominio = (DominioDetalle) listaBeneficiosSeleccionados.get(i);
//				beneficiosSeleccionados = beneficiosSeleccionados + dominio.getIdentificador().getTipo() + ";";
				
//				select = (SelectItem) listaBeneficiosSeleccionados.get(i);
//				beneficiosSeleccionados = beneficiosSeleccionados + select.getValue() + ";";

				beneficiosSeleccionados = beneficiosSeleccionados + listaBeneficiosSeleccionados.get(i) + ";";
			}
		}	
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
					//return lis[i].getAction();
					return lis[i].getOutcome();
					}
				}
				
				if(lis[i].getOutcome().equals("irObjetivosResultados")){
					bandera = true;
				}
				
			}
			}
		}		
		//////////////////
		return "irLineas" ;
	}

	public String salir(){
		sesion.removeAttribute("proyecto");
		borrarManejadoresInsercionProyecto();
		return "misProyectos" ;
	}

	public String salirGuardar(){
		
		concatenarBeneficiosSeleccionados();
		proyectoActual.setBeneficiosExtSol(beneficiosSeleccionados);
		
		boolean seValidaRequisitos=false;
		
		if(esProgramaNacional){
			seValidaRequisitos=true;
		}else{
			if(validarObjetivos() && validarResultados()){
				seValidaRequisitos=true;
			}else{
				seValidaRequisitos=false;
			}

		}
		

		
		
		if(seValidaRequisitos){
			
			
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
					
					if(lis[i].getOutcome().equals("irObjetivosResultados")){
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
						.obtenerListaObjetos("Formulario where id ='80'");
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
            borrarManejadoresInsercionProyecto();
    		return "misProyectos" ;
		}
		return "";
	}

	public String siguiente(){
		
		boolean seValidaRequisitos=false;
		
		if(esProgramaNacional){
			seValidaRequisitos=true;
		}else{
			if(validarObjetivos() && validarResultados()){
				seValidaRequisitos=true;
			}else{
				seValidaRequisitos=false;
			}

		}
		

			if(seValidaRequisitos){
			
			
			
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
							
							if(lis[i].getOutcome().equals("irObjetivosResultados")){
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
							.obtenerListaObjetos("Formulario where id ='80'");
					formulario = (Formulario) listaFormulario.get(0);
					
					HistoricoFormularioProyecto historicoFormualrioProyecto = new HistoricoFormularioProyecto();
	                historicoFormualrioProyecto.setDocPersona(personaAux.getId().getDocumento());
	                historicoFormualrioProyecto.setTipoDocumentoPersona(personaAux.getId().getTipoDocumento());
	                historicoFormualrioProyecto.setFormulario(formulario);
	                historicoFormualrioProyecto.setProyecto(proyectoActual);
	                historicoFormualrioProyecto.setFechaCambio(new Date());
	                servicioGeneral.guardarObjeto(historicoFormualrioProyecto);
				}

			concatenarBeneficiosSeleccionados();
			proyectoActual.setBeneficiosExtSol(beneficiosSeleccionados);
		
            servicioProyecto.ingresarProyecto(proyectoActual);            
            sesion.setAttribute("proyecto",proyectoActual);
           
            sesion.removeAttribute("manejadorObjetivosResultados");
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
	    				
	    				if(lis[i].getOutcome().equals("irObjetivosResultados")){
	    					bandera = true;
	    				}
	    			}
    			}
    		}		
    		//////////////////
    		 sesion.removeAttribute("manejadorMenuFormularios");
			return "irInformacionEspecifica";
			}
		
		return "";
	}

	//FUNCIONES PROPIAS DE LA CLASE
	public void insertarObjetivo(){
    	ObjetivoEspecifico oe=new ObjetivoEspecifico();
    	oe.setNombre("");
    	listaObjetivos.add(oe);
    	mensajeErrorObjetivos="";
    }

    public void eliminarObjetivo()
    {
    	((ObjetivoEspecifico)listaObjetivos.get(tablaObjetivos.getRowIndex())).setBorrable(true);
    }

    public void insertarResultado()
    {
    	ResultadoProyecto rp=new ResultadoProyecto();
    	rp.setDescripcion("");
    	listaResultados.add(rp);
    	mensajeErrorResultados="";
    }

    public void eliminarResultado()
    {
    	((ResultadoProyecto)listaResultados.get(tablaResultados.getRowIndex())).setBorrable(true);
    	
    	//((ResultadoProyecto)listaResultados.get(listaResultados.indexOf(resultadoSel))).setBorrable(true);
    	
    	//ResultadoProyecto rp = ((ResultadoProyecto)listaResultados.get(listaResultados.indexOf(resultadoSel)));
    	//rp.setBorrable(true);
    }

    //VALIDADORES
    private boolean validarObjetivos() {
    	//VALIDA LA EXISTENCIA DE OBJETIVOS EN LA RESPECTIVA LISTA
    	List listaAuxiliar=new ArrayList();
    	for(int i=0;i<listaObjetivos.size();i++)
    	{
    	 ObjetivoEspecifico oe=(ObjetivoEspecifico)listaObjetivos.get(i);
    	 if(oe.isBorrable())
    	 {
    	 	listaAuxiliar.add(oe);
    	 	proyectoActual.borrarObjetivoEspecifico(oe);
    	 }
    	 else{proyectoActual.adicionarObjetivoEspecifico(oe);}
    	}
    	listaObjetivos.removeAll(listaAuxiliar);
    	if(listaObjetivos.isEmpty())
    	{
    		mensajeErrorObjetivos="No se encuentran objetivos específicos asociados al proyecto";
    		FacesContext.getCurrentInstance().addMessage("msgObj", new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeErrorObjetivos, ""));
    		return false;
    	}
    	mensajeErrorObjetivos=" ";
    	return true;
    }

    private boolean validarResultados() {
        //VALIDA LA EXISTENCIA DE RESULTADOS EN LA RESPECTIVA LISTA
    	List listaAuxiliar=new ArrayList();
    	for(int i=0;i<listaResultados.size();i++)
    	{
    	 ResultadoProyecto rp=(ResultadoProyecto)listaResultados.get(i);
    	 if(rp.isBorrable())
    	 {
    	 	listaAuxiliar.add(rp);
    	 	proyectoActual.borrarResultado(rp);
    	 }
    	 else{proyectoActual.adicionarResultado(rp);}
    	}
    	listaResultados.removeAll(listaAuxiliar);
    	if(listaResultados.isEmpty())
    	{
    		mensajeErrorResultados="No se encuentran resultados asociados al proyecto";
    		FacesContext.getCurrentInstance().addMessage("msgObj", new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeErrorResultados, ""));
    		return false;
    	}
    	mensajeErrorResultados=" ";
    	return true;
    }

    //METODOS SET Y GET
	public List getListaObjetivos() {
		return listaObjetivos;
	}
	public void setListaObjetivos(List listaObjetivos) {
		this.listaObjetivos = listaObjetivos;
	}
	public List getListaResultados() {
		return listaResultados;
	}
	public void setListaResultados(List listaResultados) {
		this.listaResultados = listaResultados;
	}
	public String getMensajeErrorObjetivos() {
		return mensajeErrorObjetivos;
	}
	public void setMensajeErrorObjetivos(String mensajeErrorObjetivos) {
		this.mensajeErrorObjetivos = mensajeErrorObjetivos;
	}
	public String getMensajeErrorResultados() {
		return mensajeErrorResultados;
	}
	public void setMensajeErrorResultados(String mensajeErrorResultados) {
		this.mensajeErrorResultados = mensajeErrorResultados;
	}

	public DataTable getTablaObjetivos() {
		return tablaObjetivos;
	}

	public void setTablaObjetivos(DataTable tablaObjetivos) {
		this.tablaObjetivos = tablaObjetivos;
	}

	public DataTable getTablaResultados() {
		return tablaResultados;
	}

	public void setTablaResultados(DataTable tablaResultados) {
		this.tablaResultados = tablaResultados;
	}

	public boolean isEsProgramaNacional() {
		return esProgramaNacional;
	}

	public void setEsProgramaNacional(boolean esProgramaNacional) {
		this.esProgramaNacional = esProgramaNacional;
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

	public String getTitulo3() {
		return titulo3;
	}

	public void setTitulo3(String titulo3) {
		this.titulo3 = titulo3;
	}

	public ObjetivoEspecifico getObjEspecificoSel() {
		return objEspecificoSel;
	}

	public void setObjEspecificoSel(ObjetivoEspecifico objEspecificoSel) {
		this.objEspecificoSel = objEspecificoSel;
	}

	public ResultadoProyecto getResultadoSel() {
		return resultadoSel;
	}

	public void setResultadoSel(ResultadoProyecto resultadoSel) {
		this.resultadoSel = resultadoSel;
	}

	public boolean isEsExtensionSolidaria() {
		return esExtensionSolidaria;
	}

	public void setEsExtensionSolidaria(boolean esExtensionSolidaria) {
		this.esExtensionSolidaria = esExtensionSolidaria;
	}

	public List getListaBeneficios() {
		return listaBeneficiosTodos;
	}

	public void setListaBeneficios(List listaBeneficios) {
		this.listaBeneficiosTodos = listaBeneficios;
	}

	public String getBeneficiosSeleccionados() {
		return beneficiosSeleccionados;
	}

	public void setBeneficiosSeleccionados(String beneficiosSeleccionados) {
		this.beneficiosSeleccionados = beneficiosSeleccionados;
	}

	public List getListaBeneficiosTodos() {
		return listaBeneficiosTodos;
	}

	public void setListaBeneficiosTodos(List listaBeneficiosTodos) {
		this.listaBeneficiosTodos = listaBeneficiosTodos;
	}

	public List getListaBeneficiosSeleccionados() {
		return listaBeneficiosSeleccionados;
	}

	public void setListaBeneficiosSeleccionados(List listaBeneficiosSeleccionados) {
		this.listaBeneficiosSeleccionados = listaBeneficiosSeleccionados;
	}

	public List getListaBeneficiosSeleccionadosDescri() {
		return listaBeneficiosSeleccionadosDescri;
	}

	public void setListaBeneficiosSeleccionadosDescri(
			List listaBeneficiosSeleccionadosDescri) {
		this.listaBeneficiosSeleccionadosDescri = listaBeneficiosSeleccionadosDescri;
	}
	
   }
