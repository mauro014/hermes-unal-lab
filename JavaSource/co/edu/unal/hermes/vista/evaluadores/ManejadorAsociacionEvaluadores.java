
package co.edu.unal.hermes.vista.evaluadores;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import org.apache.myfaces.component.html.ext.HtmlDataTable;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.CalificacionEvaluacion;
import co.edu.unal.hermes.modelo.CriterioEvaluacion;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.HistoricoEstadoProyecto;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.ModalidadCriterioTipoPregunta;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.ProyectoEvaluador;
import co.edu.unal.hermes.modelo.TipoFinanciacion;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.utils.Navegacion;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * @author Ing Hernán Darío Bernal Parra 
 */

public class ManejadorAsociacionEvaluadores extends ManejadorBase{
	
	ProyectoEvaluador         evaluacionMesaSeleccion=null;	
	private SelectItem[]      recomendaciones; //Posibles recomendaciones para el proyecto
	private Map               mValueMap = new HashMap();
	private List              listaProyectoEvaluador;
	private List              listaCriteriosCalificacion;
    private Proyecto          proyectoActual;
    private HtmlSelectOneMenu menuConceptoMesaSeleccion;
    		List			  listaAuxiliarCriterios;
    
    private DataModel      columnasEvaluadores;
    private DataModel      listaCriterios;
    
    private String estadoProyecto = "";

    private HtmlDataTable  tablaResumen;    
    private HtmlDataTable  tablaProyectoEvaluador;
    
    public ManejadorAsociacionEvaluadores()
    {
    	super();
        //SE CREA LA LISTA DE POSIBLES RECOMENDACIONES PARA EL PROYECTO
      	recomendaciones    = new SelectItem[3];
      	recomendaciones[0] = new SelectItem("AP","APROBADO");//RECORDAR QUE AL ACTUALIZAR EL ESTADO SE DEBE HACER LA EQUIVALENCIA CON EL RESPECTIVO ESTADO
      	recomendaciones[1] = new SelectItem("E","ELEGIBLE");
      	recomendaciones[2] = new SelectItem("N","NO APROBADO");
        tablaProyectoEvaluador     = new HtmlDataTable();                
        tablaResumen               = new HtmlDataTable();
        listaProyectoEvaluador     = new ArrayList();
        listaCriterios             = new ListDataModel();
        listaCriteriosCalificacion = new ArrayList();
        
        menuConceptoMesaSeleccion  = new HtmlSelectOneMenu();        
        try
		{
           //Obtener el proyecto actual de la sesión
           sesion.removeAttribute("manejadorAsociacionEvaluadores");
           proyectoActual=(Proyecto)sesion.getAttribute("proyecto");
           proyectoActual    = servicioProyecto.obtenerProyecto(proyectoActual.getId(), ProyectoDAOHibernate.EVALUADORES);
           estadoProyecto = proyectoActual.getEstadoProyecto().getId();
           //Obtener los evaluadores asociados al proyecto                      
           List listaAuxiliar=new ArrayList();
           listaAuxiliar.addAll(proyectoActual.getEvaluadoresProyecto());
           //listaProyectoEvaluador.addAll(proyectoActual.getEvaluadoresProyecto());
           //Crear las etiquetas de las columnas y se crean las columnas           
           listaAuxiliarCriterios=servicioModalidad.listaCriteriosResumen(proyectoActual.getModalidad(),proyectoActual.getId().toString());           
           CriterioEvaluacion cr=new CriterioEvaluacion();
           cr.setId(new Long(-2));           
           cr.setNombre("Observación Final");
           listaAuxiliarCriterios.add(cr);
           cr=new CriterioEvaluacion();
           cr.setId(new Long(-1));           
           cr.setNombre("Ponderación(Puntaje total sobre 100)");
           listaAuxiliarCriterios.add(cr);
           listaCriterios = new ListDataModel(listaAuxiliarCriterios);
           List listaNombresEvaluadores=new ArrayList();
           ProyectoEvaluador evaluacionMesaTrabajo=null;
           //En este for ademas de asignar las etiquetas se aprovecha para revisar si el proyecto ha sido evaluado
           //por el comite de seleccion                                 
           for(int i=0;i<listaAuxiliar.size();i++)
           {
	           	ProyectoEvaluador pe=(ProyectoEvaluador)listaAuxiliar.get(i);           	
	       		if(pe.getEvaluador().getId().getDocumento().equals("COMITESELECCION"))
	       		{
	           	 	evaluacionMesaSeleccion=servicioProyecto.obtenerProyectoEvaluadorConTipoFinancioacion(pe);	
	           	 	if(evaluacionMesaSeleccion.getTipoFinanciacion()==null || evaluacionMesaSeleccion.getTipoFinanciacion().getId() == null )
	           	 	{
	           	 	    TipoFinanciacion tc= (TipoFinanciacion) servicioGeneral.obtenerObjeto(new TipoFinanciacion(),"N");
	           	 	    evaluacionMesaSeleccion.setTipoFinanciacion(tc);
	           	 	}
	       		}
	       		if(pe.getEvaluador().getId().getDocumento().equals("MESATRABAJO"))
	       		{
	       			evaluacionMesaTrabajo=pe;	       				
	       		}
	       		if(!pe.getEvaluador().getId().getDocumento().equals("COMITESELECCION") && !pe.getEvaluador().getId().getDocumento().equals("MESATRABAJO"))
	       		{
	       			listaProyectoEvaluador.add(pe);
	       			listaNombresEvaluadores.add(pe.getEvaluador().getNombre1()+" "+pe.getEvaluador().getApellido1());
//	       			listaNombresEvaluadores.add("Descripcion");
	       		}	       			       			                       	                	
           }                      
           //Si el proyecto no ha sido evaluado por el comite de seleccion, se guarda la respectiva evaluacion asociada           
           if(evaluacionMesaSeleccion==null)
           {	     
           	     IdPersona idEvaluador=new IdPersona();
   	           	 idEvaluador.setDocumento("COMITESELECCION");
   	           	 idEvaluador.setTipoDocumento("C");
   	           	 Investigador e =servicioPersona.obtenerInvestigador(idEvaluador);
   	           	 evaluacionMesaSeleccion=new ProyectoEvaluador();
   	           	 evaluacionMesaSeleccion.setEvaluador(e);
   	           	 evaluacionMesaSeleccion.setProyecto(proyectoActual);
   	           	 TipoFinanciacion tc= (TipoFinanciacion) servicioGeneral.obtenerObjeto(new TipoFinanciacion(),"N");
   	           	 evaluacionMesaSeleccion.setTipoFinanciacion(tc);
   	           	 proyectoActual.adicionarEvaluadorProyecto(evaluacionMesaSeleccion);   	           	 
           } 
           if(evaluacionMesaTrabajo!=null)
           {
              listaProyectoEvaluador.add(evaluacionMesaTrabajo);	
           }           
           columnasEvaluadores    = new ListDataModel(listaNombresEvaluadores);           
//           menuConceptoMesaSeleccion.setValue(evaluacionMesaSeleccion.getTipoFinanciacion().getId());                      	
        }
        catch(Exception e)
		{
        	e.printStackTrace();
		}        
    }
	    
    public void cambiarEstado(ValueChangeEvent event){
    	Boolean newValue = (Boolean) event.getNewValue();
    	
		ProyectoEvaluador e=(ProyectoEvaluador)tablaProyectoEvaluador.getRowData();
		
		
		if(newValue.booleanValue()){
			e.setActivo("");
			this.servicioGeneral.actualizarEvaluacion(e.getId().intValue(), "");
		}
		else{
			e.setActivo("N");
			this.servicioGeneral.actualizarEvaluacion(e.getId().intValue(), "N");
			
		}
		/*if(e != null && e.getActivo() != null && e.equals("N")){
			e.setActivo("");
			this.servicioGeneral.actualizarEvaluacion(e.getId().intValue(), "");
		}
		
		if(e != null && (e.getActivo() == null ||e.getActivo().length() == 0)){
			e.setActivo("N");
			this.servicioGeneral.actualizarEvaluacion(e.getId().intValue(), "N");
		}*/
		
		
	}
    
    public String asociarEvaluador()
    {    	    	   
    	ProyectoEvaluador e=(ProyectoEvaluador)tablaProyectoEvaluador.getRowData();
    	sesion.setAttribute("proyectoEvaluador",e);
//        sesion.removeAttribute("manejadorAsociacionEvaluadores");
        sesion.removeAttribute("manejadorResumenEvaluacion");
    	if(e.getEvaluador().getId().getDocumento().equals("MESATRABAJO"))
		    return "evaluarProyectoMesa";
		else		
	        return "evaluarProyectoResumen";	
    }
    
    
    public String asociarEvaluadorEvaluar()
    {    	    	   
    	ProyectoEvaluador e=(ProyectoEvaluador)tablaProyectoEvaluador.getRowData();
    	sesion.setAttribute("proyectoEvaluador",e);
    	sesion.setAttribute("evaluadorCoordinador",true);
//        sesion.removeAttribute("manejadorAsociacionEvaluadores");
        sesion.removeAttribute("manejadorEvaluacion");
    	if(e.getEvaluador().getId().getDocumento().equals("MESATRABAJO")){
        	sesion.setAttribute("evaluadorCoordinador",true);
		    return "evaluarProyectoMesa";
    	}
		else		
	        return "evaluarProyecto";	
    }
       
    private CalificacionEvaluacion buscarEvaluacion(ProyectoEvaluador evaluacionProyecto,long id)
    {
    	Set listaEvaluaciones=evaluacionProyecto.getCalificaciones();
    	Iterator it=listaEvaluaciones.iterator();
  	    CalificacionEvaluacion d=new CalificacionEvaluacion();  	    
        int i=0;         
        while(it.hasNext())
         {
          d = (CalificacionEvaluacion)it.next();
          if(id==(d.getCriterio().getId()).longValue())     
          {
             return d;
          }
          i=i+1;	
         }  
         return null;
    }
    
    public String guardarConceptoMesaSeleccion()
    {
    	try
		{
    	    System.out.println("guardar ");
    	   String v=evaluacionMesaSeleccion.getTipoFinanciacion().getId();//menuConceptoMesaSeleccion.getValue().toString(); 
    	   if(v!=null && v.length()>0)
    	   {
    	       System.out.println("guardando concepto"+v);//menuConceptoMesaSeleccion.getValue().toString());
    	     TipoFinanciacion tc= (TipoFinanciacion) servicioGeneral.obtenerObjeto(new TipoFinanciacion(),v);//menuConceptoMesaSeleccion.getValue().toString());
    	     evaluacionMesaSeleccion.setTipoFinanciacion(tc);    	     
    	     //Guardar la evaluacion de la mesa de seleccion    	    	     
//    	     servicioProyecto.guardarEvaluacionProyectoConTipoFinanciacion(evaluacionMesaSeleccion);    	     
             evaluacionMesaSeleccion.setCalificaciones(null);
             servicioGeneral.guardarObjeto(evaluacionMesaSeleccion);
    	     //Se cambia el estado del proyecto y se crea un nuevo historico
  	         EstadoProyecto estadoProyectoActual = new EstadoProyecto();
  	         /*if(evaluacionMesaSeleccion.getTipoFinanciacion().getId().equals("AP"))
  	         	estadoProyectoActual.setId("AP");
  	         if(evaluacionMesaSeleccion.getTipoFinanciacion().getId().equals("E"))
	         	estadoProyectoActual.setId("E");
  	         if(evaluacionMesaSeleccion.getTipoFinanciacion().getId().equals("N"))
	         	estadoProyectoActual.setId("N");*/
  	       if(estadoProyecto.equals("AP"))
 	         	estadoProyectoActual.setId("AP");
 	         if(estadoProyecto.equals("E"))
	         	estadoProyectoActual.setId("E");
 	         if(estadoProyecto.equals("N"))
	         	estadoProyectoActual.setId("N");
  	         // Si no hay un cambio de estado, no se actualiza el historico
  	         if(!proyectoActual.getEstadoProyecto().getId().equals(estadoProyectoActual.getId()))
  	         {
  	               //Historico del proyecto para convertir proyecto a propuesto
     	           HistoricoEstadoProyecto hepry = new HistoricoEstadoProyecto();
     	           Date fechaHoy = new Date();
     	           hepry.setEstadoProyecto(estadoProyectoActual);
     	           hepry.setFecha(fechaHoy);
     	           proyectoActual.adicionarHistorico(hepry);
     	           proyectoActual.setEstadoProyecto(estadoProyectoActual);
     	           servicioGeneral.guardarObjeto(proyectoActual);
  	         }
    	   }
		}
    	catch(Exception e)
		{
    	 e.printStackTrace();	
		}
    	return "";
    }
    
    private float calcularSumaPonderaciones(ProyectoEvaluador pe)
    {
    	float valorTotalPonderacion=0;
    	Iterator itCalifi=listaAuxiliarCriterios.iterator();
    	for(int i=1;i<=listaCriterios.getRowCount()-1 && itCalifi.hasNext();i++)
    	{
    	    CriterioEvaluacion criterio= (CriterioEvaluacion) (itCalifi.next());
    	    System.out.println("buscando calificcion evaluacion para criterio "+criterio.getId());
    		CalificacionEvaluacion calificacionE=buscarEvaluacion(pe,criterio.getId().longValue() );
    		System.out.println("se encontro "+(calificacionE==null?"null":String.valueOf(calificacionE.getCuantitativa().floatValue())));
    		if(calificacionE!=null && calificacionE.getCuantitativa()!=null)
    		{
    			ModalidadCriterioTipoPregunta mctp=servicioModalidad.obtenerModalidadCriterioTipoPreguntaXProyectoEvaluadorYCriterio(pe.getProyecto().getModalidad().getId(),criterio.getId() );
    			if(mctp.getTipoPregunta().getSeTieneParaPromedio().equals("S"))
    			{
    				
    			System.out.println("calcula parcial "+valorTotalPonderacion+ " * cuantativa "+calificacionE.getCuantitativa().floatValue()+" criterio "+ calificacionE.getCriterio().getFactor().floatValue()+ " da "+ (calificacionE.getCuantitativa().floatValue()*calificacionE.getCriterio().getFactor().floatValue()));
    				valorTotalPonderacion+=(calificacionE.getCuantitativa().floatValue()*calificacionE.getCriterio().getFactor().floatValue());
    		    System.out.println(" el nuevo parcial "+valorTotalPonderacion);
    			}
    		}
    	}
    	return valorTotalPonderacion;
    }
    
    public String getColumnValue()
    {    	
    try
	{
      if (listaCriterios.isRowAvailable())
      {      	
        CriterioEvaluacion row = (CriterioEvaluacion) listaCriterios.getRowData();        
        if (columnasEvaluadores.isRowAvailable())
        {           	               
          Object column = columnasEvaluadores.getRowData();
          Object key = new RowColumnKey(row.getId(), column);
          if (!mValueMap.containsKey(key))
          {          	
          	String nombreColumna= (String)column;
          	System.out.println(((CriterioEvaluacion)listaCriterios.getRowData()).getNombre());
//          	if(nombreColumna.equals("Descripcion"))
//          	{
//          	  int indice=(columnasEvaluadores.getRowIndex()-1)/2;
//          	  ProyectoEvaluador pe = servicioProyecto.obtenerProyectoEvaluador((ProyectoEvaluador)listaProyectoEvaluador.get(indice));
//	          if(listaCriterios.getRowIndex()!=9 && (listaCriterios.getRowCount()-1)!=listaCriterios.getRowIndex())
//	          {
//	              
//	            CalificacionEvaluacion calificacionE=buscarEvaluacion(pe,row.getId().longValue());
//	            String descripcion=null;
//	            if(calificacionE!=null)
//	            {
//		            descripcion= calificacionE.getCualitativa();
//	                
//	            }
//	            if(descripcion==null)
//	            {
//	                descripcion="";
//	            }
//	            mValueMap.put(key,descripcion);
//	          }
//	          else
//	          {
//	              mValueMap.put(key,"");
//	          }
//          	}
//	        else
//	        {
//	            int indice=columnasEvaluadores.getRowIndex()/2;
	            ProyectoEvaluador pe = servicioProyecto.obtenerProyectoEvaluador((ProyectoEvaluador)listaProyectoEvaluador.get(columnasEvaluadores.getRowIndex()));          	          	          	
	          	//De dicho objeto obtener la calificacion asociada a la columna del respectivo investigador
	//          	CalificacionEvaluacion calificacionE=buscarEvaluacion(pe,listaCriterios.getRowIndex()+1);
	        	System.out.println("buscar calificacion para criterio "+row.getId().longValue());
	          	CalificacionEvaluacion calificacionE=buscarEvaluacion(pe,row.getId().longValue());
	          	System.out.println("se encontro "+(calificacionE==null?"nulo":""+calificacionE.getCuantitativa().floatValue()));
	      		NumberFormat df = NumberFormat.getInstance();          	     
	      		df.setMaximumFractionDigits(3);
	      		NumberFormat df2= NumberFormat.getInstance();
	      		df2.setMaximumFractionDigits(2);
	      		String calificacion=" ";
	      		if((listaCriterios.getRowCount()-2)==listaCriterios.getRowIndex())
      			{
	      		    calificacion= pe.getConcepto().getNombre();	
	      		}else if((listaCriterios.getRowCount()-1)==listaCriterios.getRowIndex())
      			{
	      		    calificacion=df2.format(calcularSumaPonderaciones(pe));	
	      		}else{
	      		    if(calificacionE!=null && calificacionE.getCuantitativa()!=null)
	      		    {
	      		        
	//      	        calificacion=df.format(calificacionE.getCuantitativa().floatValue()*calificacionE.getCriterio().getFactor().floatValue());
	      		        calificacion=df.format(calificacionE.getCuantitativa().floatValue());
	      		    }
	      		    ModalidadCriterioTipoPregunta mctp=servicioModalidad.obtenerModalidadCriterioTipoPreguntaXProyectoEvaluadorYCriterio(pe.getProyecto().getModalidad().getId(), row.getId());
    		    	if(mctp.getTipoPregunta().getSeTieneParaPromedio().equals("N"))
    		    	{
    		    		System.out.println("no aplica cuantitativa"); 
    		    		calificacion="No aplica";
    		    	}
	      		    
	      	    }
	      		System.out.println("Fila: "+listaCriterios.getRowIndex()+" "+"Columna: "+columnasEvaluadores.getRowIndex()+" Calificacion: "+calificacion);          	          	          	          	
	          	if(calificacion == null || calificacion.length() == 0)
	          		calificacion = new String(" ");
	            mValueMap.put(key, calificacion);
//            }
          }
          System.out.println("colum valor: "+ (String) mValueMap.get(key));
          return (String) mValueMap.get(key);
        }
      }
      return null;
      }
      catch(Exception e)
	  {
          e.printStackTrace();
       return null;	
	  }
    }
    
    public String reporte(){
	    ReporteBirt r = new ReporteBirt();
	    ProyectoEvaluador pe = (ProyectoEvaluador) tablaProyectoEvaluador.getRowData();
	    r.adicionarParametro("id", pe.getId().toString());
	    r.setFormato(ReporteBirt.FORMATO_PDF);
	    if(proyectoActual.getModalidad().getTipo().getId().compareTo("PN") == 0){
	    	r.setNombreReporte("/evaluacion/EvaluacionPrograma");		
	    }else{
	    	r.setNombreReporte("/evaluacion/Evaluacion");
	    }
	    sesion.setAttribute("reporte", r);
        return Navegacion.REPORTE; 	    
	}
  
    
	public List getListaProyectoEvaluador() {
		return listaProyectoEvaluador;
	}
	public void setListaProyectoEvaluador(List listaProyectoEvaluador) {
		this.listaProyectoEvaluador = listaProyectoEvaluador;
	}
	public Proyecto getProyectoActual() {
		return proyectoActual;
	}
	public void setProyectoActual(Proyecto proyectoActual) {
		this.proyectoActual = proyectoActual;
	}
	public HtmlDataTable getTablaProyectoEvaluador() {
		return tablaProyectoEvaluador;
	}
	public void setTablaProyectoEvaluador(HtmlDataTable tablaProyectoEvaluador) {
		this.tablaProyectoEvaluador = tablaProyectoEvaluador;
	}		
	public HtmlDataTable getTablaResumen() {
		return tablaResumen;
	}
	public void setTablaResumen(HtmlDataTable tablaResumen) {
		this.tablaResumen = tablaResumen;
	}					
	public DataModel getColumnasEvaluadores() {		
		return columnasEvaluadores;
	}
	public void setColumnasEvaluadores(DataModel columnasEvaluadores) {
		this.columnasEvaluadores = columnasEvaluadores;
	}
	public DataModel getListaCriterios() {		
		return listaCriterios;
	}
	public void setListaCriterios(DataModel listaCriterios) {
		this.listaCriterios = listaCriterios;
	}
	public SelectItem[] getRecomendaciones() {
		return recomendaciones;
	}
	public void setRecomendaciones(SelectItem[] recomendaciones) {
		this.recomendaciones = recomendaciones;
	}	
	public ProyectoEvaluador getEvaluacionMesaSeleccion() {
		return evaluacionMesaSeleccion;
	}
	public void setEvaluacionMesaSeleccion(
			ProyectoEvaluador evaluacionMesaSeleccion) {
		this.evaluacionMesaSeleccion = evaluacionMesaSeleccion;
	}	
	public HtmlSelectOneMenu getMenuConceptoMesaSeleccion() {
		return menuConceptoMesaSeleccion;
	}
	public void setMenuConceptoMesaSeleccion(
			HtmlSelectOneMenu menuConceptoMesaSeleccion) {
		this.menuConceptoMesaSeleccion = menuConceptoMesaSeleccion;
	}
	  private class RowColumnKey
	  {
	    private final Object mRow;
	    private final Object mColumn;

	    /**
	     * @param row
	     * @param column
	     */
	    public RowColumnKey(Object row, Object column)
	    {
	      mRow = row;
	      mColumn = column;
	    }

	    /**
	     * @see java.lang.Object#equals(java.lang.Object)
	     */
	    public boolean equals(Object obj)
	    {
	      if (obj == null)
	      {
	        return false;
	      }
	      if (obj == this)
	      {
	        return true;
	      }
	      if (obj instanceof RowColumnKey)
	      {
	        RowColumnKey other = (RowColumnKey) obj;
	        return other.mRow.equals(mRow) && other.mColumn.equals(mColumn);
	      }
	      return super.equals(obj);
	    }

	    /**
	     * @see java.lang.Object#hashCode()
	     */
	    public int hashCode()
	    {
	      return (37 * 3 + mRow.hashCode()) * (37 * 3 + mColumn.hashCode());
	    }

	    /**
	     * @see java.lang.Object#toString()
	     */
	    public String toString()
	    {
	      return mRow.toString() + "," + mColumn.toString();
	    }
	  }
	public String getEstadoProyecto() {
		return estadoProyecto;
	}

	public void setEstadoProyecto(String estadoProyecto) {
		this.estadoProyecto = estadoProyecto;
	}
	  
		
}
