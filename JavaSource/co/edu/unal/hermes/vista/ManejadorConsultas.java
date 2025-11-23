package co.edu.unal.hermes.vista;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.faces.component.UIData;
import javax.faces.component.UIPanel;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.CategoriaGrupo;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.utils.Navegacion;

public class ManejadorConsultas extends ManejadorBase 
{

	private List     listaProyectos;         //MANTIENE LA LISTA DE PROYECTOS CONSULTADOS
	private List     listaGrupos;	         //MANTIENE LA LISTA DE GRUPOS CONSULTADOS
	private List     listaInvestigadores;    //MANTIENE LA LISTA DE PERSONAS CONSULTADAS
	private List	 listaAreasConocimiento;
			
	private Proyecto proyectoActual;         //PROYECTO ACTUALMENTE CARGADO
	private Grupo    grupoActual;            //GRUPO ACTUALMENTE CARGADO
	private Investigador investigadorActual; //INVESTIGADOR ACTUALMENTE CARGADO
	private long     valorTotalInvestigacion;   

	private String  claveBusqueda;           //CADENA CLAVE PARA LA BÚSQUEDA EN LA BASE DE DATOS 
	private String  nombreBusqueda;           //CADENA CLAVE PARA LA BÚSQUEDA DE LOS NOMBRES DE LOS INVESTIGADORES
	private String  apellidoBusqueda;           //CADENA CLAVE PARA LA BÚSQUEDA DE LOS APELLIDOS DE LOS INVESTIGADORES
	private UIData  tablaConsultas;          //OBJETO GRÁFICO PARA EL MANEJO DE LAS LISTAS  	 
	private Integer categoria;               //CATEGORÍA DE BÚSQUEDA	 
	private UIPanel panelInvestigadores;
	private UIPanel panelPalabra;
	private UIPanel panelCategoria;
	private UIPanel panelGruposProyectos;	
	private SelectItem[] categoriaItems = {
 		new SelectItem(new Integer(1), "Grupos de Investigación"), 
 		new SelectItem(new Integer(2), "Investigadores"),
 		new SelectItem(new Integer(3), "Proyectos de Investigación"),	 
		new SelectItem(new Integer(4), "Investigadores según area de conocimiento"),
 		};	 		
	Integer id_nuevo=new Integer(1);;
	
	/*
	 *Declaraciones generales (Consult Soft S.A.)
	 */
	private List listaSedes;
	private SelectItem[] sedesItem;
	private List listaFacultades;
	private SelectItem[] facultadesItem;
	private SelectItem[] facultadesSedeItem;
	private List listaCategoriasGrupo;
	private SelectItem[] categoriasGrupoItem;
	
	/*
	 * Opciones de consulta (Consult Soft S.A.)
	 */
	private String idSede;
	private String idFacultad;
	private String idCategoriaGrupo;
	
	/*
	 * DML (Consult Soft S.A.)
	 */
	public void cargarListas()
	{
		listaSedes = this.servicioGeneral.obtenerSedes();
		sedesItem = new SelectItem[listaSedes.size() + 1];
		sedesItem[0] = new SelectItem("","");
		idSede = "";
		for(int i = 1; i < listaSedes.size() + 1; i++)
		{
			Dependencia sede = (Dependencia) listaSedes.get(i - 1);
			sedesItem[i] = new SelectItem(sede.getId(), sede.getNombre());
		}
		
		listaFacultades = this.servicioGeneral.obtenerFacultades();
		facultadesItem = new SelectItem[listaFacultades.size() + 1];		
		facultadesItem[0] = new SelectItem("","");
		this.idFacultad = "";
		for(int i = 1; i < listaFacultades.size() + 1; i++)
		{
			Dependencia facultad = (Dependencia) listaFacultades.get(i - 1);
			facultadesItem[i] = new SelectItem(facultad.getId(),facultad.getNombre());
		}		
		facultadesSedeItem = facultadesItem;
		
		listaCategoriasGrupo = this.servicioGeneral.obtenerListaObjetos("CategoriaGrupo");
		categoriasGrupoItem = new SelectItem[listaCategoriasGrupo.size() + 1];
		categoriasGrupoItem[0] = new SelectItem("","");
		idCategoriaGrupo = "";
		for(int i = 1; i < listaCategoriasGrupo.size() + 1; i++)
		{
			CategoriaGrupo categoria = (CategoriaGrupo) listaCategoriasGrupo.get(i - 1);
			categoriasGrupoItem[i] = new SelectItem(categoria.getId(), categoria.getNombre());
		}
	}
	
	/*
	 * Manejo de eventos (Consult Soft S.A.)
	 */
	public void cargarFacultadesSede(ValueChangeEvent valorEvento)
	{
		this.idFacultad = "";
		if(valorEvento.getNewValue().toString() != null && !valorEvento.getNewValue().toString().equals(""))
		{
			this.idSede = valorEvento.getNewValue().toString();
			listaFacultades = this.servicioGeneral.obtenerFacultades(new Dependencia(this.idSede));
			facultadesSedeItem = new SelectItem[listaFacultades.size() + 1];		
			facultadesSedeItem[0] = new SelectItem("","");
			for(int i = 1; i < listaFacultades.size() + 1; i++)
			{
				Dependencia facultad = (Dependencia) listaFacultades.get(i - 1);
				facultadesSedeItem[i] = new SelectItem(facultad.getId(),facultad.getNombre());
			}
		}		
		else
			facultadesSedeItem = facultadesItem;
	}
	
	public ManejadorConsultas() 
	{
	    super();
	    categoria=new Integer(1);   
        panelGruposProyectos = new UIPanel();
        panelInvestigadores = new UIPanel();
        panelPalabra = new UIPanel();
        panelCategoria = new UIPanel();
        panelGruposProyectos.setRendered(true);
        panelInvestigadores.setRendered(true);        
        panelCategoria.setRendered(true);        
        panelPalabra.setRendered(true);        
        this.cargarListas();
	}	
	
	public String setObjetoActual() 
	{	
		switch (categoria.intValue())
		{
		 case 1:
		 	  try
			  {		 	   
			 	   Long idGrupo =((Grupo) tablaConsultas.getRowData()).getId();
			 	   this.grupoActual = servicioGrupo.obtenerResumenGrupo(idGrupo);		 	  
			 	   // Se guarda en sesion el grupo
			 	   sesion.setAttribute("grupoBusqueda", grupoActual);	
			 	   
			 	   return "successGrupo";
			  }
		 	  catch (Exception e)
			  {
		 	      e.printStackTrace();	
			  }
		 case 2:
		 	try
			  {		 	   		 	   		 	   
			 	   IdPersona idInvestigador =((Investigador) tablaConsultas.getRowData()).getId();
			 	   this.investigadorActual = servicioPersona.obtenerResumenInvestigador(idInvestigador);	
			 	  investigadorActual.setClasificacionesConocimiento(new HashSet());
			 	   // Se guarda en sesion el investigador
			 	   sesion.setAttribute("investigadorBusqueda", investigadorActual);
			 	  	
			 	   return "successPersona";
			  }
		 	  catch (Exception e)
			  {
		 	      e.printStackTrace();
			  }		 	  
		 case 3:
		 	    try
				{		 	      		 	     
		 	      Long idProyecto =((Proyecto) tablaConsultas.getRowData()).getId();
		 	      this.proyectoActual = servicioProyecto.obtenerResumenProyecto(idProyecto);
		 	      IdPersona idInvestigador = proyectoActual.getResponsable().getId();
		 	      InvestigadorInterno investigadorInterno = servicioPersona.obtenerInvestigadorInterno(idInvestigador);
		 	      proyectoActual.setDependenciaPrincipal(investigadorInterno.getDependencia());
		 	      // Se guarda en sesion el proyecto 
		 	      sesion.setAttribute("proyectoBusqueda", proyectoActual);	
	 	     
		   	      return "successProyecto";
				}
		 	   catch (Exception e)
		 	   {
			 	   e.printStackTrace();	
			   }	
		 case 4:
		 	try
			  {		 	   		 	   		 	   
			 	   IdPersona idInvestigador =((Investigador) tablaConsultas.getRowData()).getId();
			 	   this.investigadorActual = servicioPersona.obtenerResumenInvestigador(idInvestigador);
			 	   InvestigadorInterno investigadorInterno = new InvestigadorInterno();
			 	   investigadorInterno = servicioPersona.obtenerInvestigadorClasificacionConocimiento(idInvestigador);
			 	   investigadorActual.setClasificacionesConocimiento(investigadorInterno.getClasificacionesConocimiento());
			 	   investigadorInterno = null;
			 	   // Se guarda en sesion el investigador
			 	   sesion.setAttribute("investigadorBusqueda", investigadorActual);
			 	  	
			 	   return "successPersonaAreaConocimiento";
			  }
		 	  catch (Exception e)
			  {
		 	      e.printStackTrace();
			  }		   
		 	   
			
		}	 			
		return "salir";
	}
		
	public String setListaObjetos() 
	{
	  this.tablaConsultas=null;	
	  if(categoria!=null)
	  {
		switch (categoria.intValue())
		{
		 case 1:
		 	try 
			{
		 	   //this.listaGrupos = obtenerGruposResultado(this.claveBusqueda);
		 		this.listaGrupos = obtenerGruposPorCriterio(this.claveBusqueda);
				if (this.listaGrupos == null || this.listaGrupos.size() < 0){	
				 	return "successGrupo";
				}
			} 
		 	catch (Exception e) 
			{
				 e.printStackTrace();
			        return "volver_busqueda";
			}		 	
		 	return "successGrupo";
		 case 2:		    		     
		 	try 
			{		 						
		 	    this.listaInvestigadores = obtenerInvestigadoresResultado(this.nombreBusqueda, this.apellidoBusqueda);
		 	    		 	    
				if (this.listaInvestigadores.size() <= 0)
				{						
				 	return "successPersona";
				}
			} catch (Exception e) {
				e.printStackTrace();
		        return "volver_busqueda";
			}
		 	return "successPersona";
		 	
		 case 3:
		 	try 
			{
		 		this.listaProyectos = obtenerProyectosPorCriterio(this.claveBusqueda);
		 	    //this.listaProyectos = obtenerProyectosResultado(this.claveBusqueda);		
				if (this.listaProyectos.size() <= 0)
				{
					return "successProyecto";
				}
			} 		 	
			catch (Exception e) 
			{
				e.printStackTrace();
		        return "volver_busqueda";
			}			
			return "successProyecto";
		
		 case 4:
		 	try 
			{	this.listaInvestigadores= obtenerAreasConocimientoResultado(this.claveBusqueda);		
				if (this.listaInvestigadores.size() <= 0)
				{
					return "successPersonaAreaConocimiento";		
				}
			} 		 	
			catch (Exception e) 
			{
				e.printStackTrace();
		        return "volver_busqueda";
			}			
			return "successPersonaAreaConocimiento";		
			
			
		 default:
		        return "volver_busqueda";
		}
	  }
	  else
	        return "volver_busqueda";
	}
		
	public void seleccionBusqueda(ValueChangeEvent event){
	    try{    
		    id_nuevo = (Integer) event.getNewValue();	
		    if(id_nuevo.intValue() == 1){          		 //para grupos
		        panelGruposProyectos.setRendered(true);
		        panelCategoria.setRendered(true);
				panelInvestigadores.setRendered(true);
		        panelPalabra.setRendered(true);
		    }else if(id_nuevo.intValue() == 2){          //investigadores
		        panelCategoria.setRendered(false);
		        panelGruposProyectos.setRendered(false);
		        panelPalabra.setRendered(false);
		        panelInvestigadores.setRendered(true);
		    }else if(id_nuevo.intValue() == 3){     	 //proyectos
		        panelCategoria.setRendered(false);
		        panelGruposProyectos.setRendered(true);
		        panelPalabra.setRendered(true);
		        panelInvestigadores.setRendered(false);
		    }else if(id_nuevo.intValue() == 4){     	 //area de conocimiento
		        panelCategoria.setRendered(false);
		        panelGruposProyectos.setRendered(false);
		        panelPalabra.setRendered(true);
		        panelInvestigadores.setRendered(false);
		    }else{           							
		        panelCategoria.setRendered(true);
		        panelGruposProyectos.setRendered(true);
		        panelInvestigadores.setRendered(true);
		        panelPalabra.setRendered(true);
		    }		    
	    }
	    catch(Exception e){
	        e.printStackTrace();
	    }
	    
	}

    private List obtenerAreasConocimientoResultado(String nombreAreaConocimiento) {
        
    	List result = new ArrayList();        
 	    String s_facultad = (String) sesion.getAttribute("facultad");
 	    
 	    if(s_facultad != null){ 	
 	        Dependencia dependencia = servicioGeneral.obtenerDependenciaPorPaginaWeb(s_facultad); 
 	        if(dependencia != null){
 	           if(dependencia.getEsFacultad().booleanValue()){
 	               result = servicioPersona.obtenerInvestigadoresPorDependenciaAreaDeConocimiento(nombreAreaConocimiento, dependencia, Dependencia.TIPO_DEPENDENCIA_FACULTAD); 	               
 	           }else if(dependencia.getEsSede().booleanValue()){
 	               result = servicioPersona.obtenerInvestigadoresPorDependenciaAreaDeConocimiento(nombreAreaConocimiento, dependencia, Dependencia.TIPO_DEPENDENCIA_SEDE);
	           } 	            	            
 	        }	         	        
 	    }else{
 	       result = servicioPersona.obtenerInvestigadoresPorAreaDeConocimiento(nombreAreaConocimiento);

 	    }     
        return result;
        
    }
    
    
    //************************************************//
    /******* Agregado por jmesa ***************/

    // TODO Esta funcion deberia estar en el modelo y no en la vista
    private List obtenerGruposPorCriterio(String nombreGrupo) {
                
        List result = new ArrayList();        
 	    Grupo grupo = new Grupo();
 	    Sede sede = new Sede();
 	    Dependencia dependencia = new Dependencia();
 	    Investigador responsable = new Investigador();
 	    CategoriaGrupo categoria = new CategoriaGrupo();
	    categoria.setId(this.idCategoriaGrupo);
 	    dependencia.setId(this.idFacultad);
	    responsable.setNombre1(this.nombreBusqueda);
	    responsable.setApellido1(this.apellidoBusqueda);
 	    if(!this.idSede.equals("")){
 	 	    sede.setId(new Long(this.idSede)); 	     	    	
 	    }
 	    
  	    dependencia.setSede(sede);
 	    grupo.setNombre(nombreGrupo);
 	    grupo.setDependencia(dependencia);
 	    grupo.setCategoria(categoria);
 	    grupo.setResponsable(responsable);
        result = servicioGrupo.obtenerGruposPorCriterio(grupo);
 
        return result;
    }

    private List obtenerProyectosPorCriterio(String nombreProyecto) {
        
        List result = new ArrayList();        
 	    Sede sede = new Sede();
 	    Dependencia dependencia = new Dependencia();
	    dependencia.setId(this.idFacultad);
	    if(!this.idSede.equals("")){
 	 	    sede.setId(new Long(this.idSede)); 	     	    	
 	    }
  	    dependencia.setSede(sede);
  	    Proyecto proyecto = new Proyecto();
  	    proyecto.setNombre(nombreProyecto);
  	    proyecto.setDependenciaPrincipal(dependencia);
  	    
 	    result = servicioProyecto.obtenerProyectosPorCriterio(proyecto);
 
        return result;
    }
   
    private List obtenerInvestigadoresResultado(String nombreInvestigador, String apellidoInvestigador) {
                
        List result = new ArrayList();        
 	    String s_facultad = (String) sesion.getAttribute("facultad");
 	     	   
 	    if(s_facultad != null){ 	
	        Dependencia dependencia = servicioGeneral.obtenerDependenciaPorPaginaWeb(s_facultad); 
	        if(dependencia != null){
	            if(dependencia.getEsFacultad().booleanValue()){
	                result = servicioPersona.obtenerInvestigadoresPorNombresApellidosDependenciaIndiferenteTildesYMayusculas(nombreInvestigador, apellidoInvestigador, dependencia, Dependencia.TIPO_DEPENDENCIA_FACULTAD);	                
	            }else if(dependencia.getEsSede().booleanValue()){
	                result = servicioPersona.obtenerInvestigadoresPorNombresApellidosDependenciaIndiferenteTildesYMayusculas(nombreInvestigador, apellidoInvestigador, dependencia, Dependencia.TIPO_DEPENDENCIA_SEDE);
	            }
	            
	        }	         	        
	    }else{
	        result = servicioPersona.obtenerInvestigadoresPorNombresYApellidosIndiferenteTildesYMayusculas(nombreInvestigador, apellidoInvestigador);
	    }
        return result;
 	    
    }
    
    /**
     * juan pablo funcion de salir pero dependiendo de la pagina 
     * para que vuelva a la pagina de la facultad reespectiva
     */ 
    public String salir(){
        String s_facultad = (String) sesion.getAttribute("facultad");
        if(s_facultad != null){
            if(s_facultad.endsWith(".jsf")){
                String [] split_facultad = s_facultad.split(".jsf", 2); 
                return "salir_" + split_facultad[0];                
            }
            return "salir_"+s_facultad;
        }else{
            return "salir";
        }        
    }
	
    public String volver_busqueda(){
    	sesion.removeAttribute("manejadorConsultas");
        return "volver_busqueda";
    }

    public String reporte(){
        Long id = ((Grupo)(this.tablaConsultas.getRowData())).getId();
	    ReporteBirt r = new ReporteBirt();	    	    
	    r.adicionarParametro("id", id.toString());	
	    r.setNombreReporte("/grupo/ReportePlanDeAccion");
	    r.setFormato(ReporteBirt.FORMATO_PDF);
	    sesion.setAttribute("reporte", r);
        return Navegacion.REPORTE; 	    
	}
    public int getTamañoListaGrupos(){
        return listaGrupos.size();
    }
    
    public int getTamañoListaInvestigadores(){
        return listaInvestigadores.size();
    }
    
    public int getTamañoListaProyectos(){
        return listaProyectos.size();
    }
    public int getTamañoListaAreasConocimiento(){
        return listaAreasConocimiento.size();
    }
    
	public Integer getCategoria() {
		return categoria;
	}
	public void setCategoria(Integer categoria) {
		this.categoria = categoria;
	}
	public SelectItem[] getCategoriaItems() {
		return categoriaItems;
	}
	public void setCategoriaItems(SelectItem[] categoriaItems) {
		this.categoriaItems = categoriaItems;
	}
	public String getClaveBusqueda() {
		return claveBusqueda;
	}
	public void setClaveBusqueda(String claveBusqueda) {
		this.claveBusqueda = claveBusqueda;
	}		
    public String getApellidoBusqueda() {
        return apellidoBusqueda;
    }
    public void setApellidoBusqueda(String apellidoBusqueda) {
        this.apellidoBusqueda = apellidoBusqueda;
    }
    public String getNombreBusqueda() {
        return nombreBusqueda;
    }
    public void setNombreBusqueda(String nombreBusqueda) {
        this.nombreBusqueda = nombreBusqueda;
    }
    
	public Grupo getGrupoActual() {
		return grupoActual;
	}
	public void setGrupoActual(Grupo grupoActual) {
		this.grupoActual = grupoActual;
	}
	public Investigador getInvestigadorActual() {
		return investigadorActual;
	}
	public void setInvestigadorActual(Investigador investigadorActual) {
		this.investigadorActual = investigadorActual;
	}
	public List getListaGrupos() {
		return listaGrupos;
	}
	public void setListaGrupos(List listaGrupos) {
		this.listaGrupos = listaGrupos;
	}
	public List getListaInvestigadores() {
		return listaInvestigadores;
	}
	public void setListaInvestigadores(List listaInvestigadores) {
		this.listaInvestigadores = listaInvestigadores;
	}
	public List getListaProyectos() {
		return listaProyectos;
	}
	public void setListaProyectos(List listaProyectos) {
		this.listaProyectos = listaProyectos;
	}	
	public List getListaAreasConocimiento() {
		return listaAreasConocimiento;
	}
	public void setListaAreasConocimiento(List listaAreasConocimiento) {
		this.listaAreasConocimiento = listaAreasConocimiento;
	}
	public Proyecto getProyectoActual() {
		return proyectoActual;
	}
	public void setProyectoActual(Proyecto proyectoActual) {
		this.proyectoActual = proyectoActual;
	}
	public UIData getTablaConsultas() {
		return tablaConsultas;
	}
	public void setTablaConsultas(UIData tablaConsultas) {
		this.tablaConsultas = tablaConsultas;
	}
	public long getValorTotalInvestigacion() {
		return valorTotalInvestigacion;
	}
	public void setValorTotalInvestigacion(long valorTotalInvestigacion) {
		this.valorTotalInvestigacion = valorTotalInvestigacion;
	} 
    public UIPanel getPanelGruposProyectos() {
        return panelGruposProyectos;
    }
    public void setPanelGruposProyectos(UIPanel panelGruposProyectos) {
        this.panelGruposProyectos = panelGruposProyectos;
    }
    public UIPanel getPanelPalabra() {
        return panelPalabra;
    }
    public void setPanelPalabra(UIPanel panelPalabra) {
        this.panelPalabra = panelPalabra;
    }
    public UIPanel getPanelInvestigadores() {
        return panelInvestigadores;
    }
    public void setPanelInvestigadores(UIPanel panelInvestigadores) {
        this.panelInvestigadores = panelInvestigadores;
    }
    public UIPanel getPanelCategoria() {
        return panelCategoria;
    }
    public void setPanelCategoria(UIPanel panelCategoria) {
        this.panelCategoria = panelCategoria;
    }
    
    /*
	 * Constructores (Consult Soft S.A.)
	 */

	public List getListaSedes() 
	{
		return listaSedes;
	}

	public void setListaSedes(List listaSedes) 
	{
		this.listaSedes = listaSedes;
	}

	public SelectItem[] getSedesItem() 
	{
		return sedesItem;
	}

	public void setSedesItem(SelectItem[] sedesItem) 
	{
		this.sedesItem = sedesItem;
	}

	public List getListaFacultades() 
	{
		return listaFacultades;
	}

	public void setListaFacultades(List listaFacultades) 
	{
		this.listaFacultades = listaFacultades;
	}

	public SelectItem[] getFacultadesItem() 
	{
		return facultadesItem;
	}

	public void setFacultadesItem(SelectItem[] facultadesItem) 
	{
		this.facultadesItem = facultadesItem;
	}

	public SelectItem[] getFacultadesSedeItem() 
	{
		return facultadesSedeItem;
	}

	public void setFacultadesSedeItem(SelectItem[] facultadesSedeItem) 
	{
		this.facultadesSedeItem = facultadesSedeItem;
	}

	public String getIdSede() 
	{
		return idSede;
	}

	public void setIdSede(String idSede) 
	{
		this.idSede = idSede;
	}

	public String getIdFacultad() 
	{
		return idFacultad;
	}

	public void setIdFacultad(String idFacultad) 
	{
		this.idFacultad = idFacultad;
	}

	public List getListaCategoriasGrupo() 
	{
		return listaCategoriasGrupo;
	}

	public void setListaCategoriasGrupo(List listaCategoriasGrupo) 
	{
		this.listaCategoriasGrupo = listaCategoriasGrupo;
	}

	public SelectItem[] getCategoriasGrupoItem() 
	{
		return categoriasGrupoItem;
	}

	public void setCategoriasGrupoItem(SelectItem[] categoriasGrupoItem) 
	{
		this.categoriasGrupoItem = categoriasGrupoItem;
	}

	public String getIdCategoriaGrupo() 
	{
		return idCategoriaGrupo;
	}

	public void setIdCategoriaGrupo(String idCategoriaGrupo) 
	{
		this.idCategoriaGrupo = idCategoriaGrupo;
	}
}
