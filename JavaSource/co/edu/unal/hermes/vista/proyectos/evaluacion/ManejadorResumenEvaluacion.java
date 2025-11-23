package co.edu.unal.hermes.vista.proyectos.evaluacion;


import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.CalificacionEvaluacion;
import co.edu.unal.hermes.modelo.ProyectoEvaluador;
import co.edu.unal.hermes.utils.ComparadorCriterioEvaluacionPorId;
import co.edu.unal.hermes.vista.ManejadorBase;
;

public class ManejadorResumenEvaluacion extends ManejadorBase
{
  
  private SelectItem[]       recomendaciones; //Posibles recomendaciones para el proyecto
    
  ProyectoEvaluador         evaluacionProyecto;  
  CalificacionEvaluacion    evaluacionPlanteamiento;
  CalificacionEvaluacion    evaluacionObjetivos;
  CalificacionEvaluacion    evaluacionMetodologia;
  CalificacionEvaluacion    evaluacionResultados;
  CalificacionEvaluacion    evaluacionImpacto;
  CalificacionEvaluacion    evaluacionGrupo;
  CalificacionEvaluacion    evaluacionPresupuesto;
  CalificacionEvaluacion    evaluacionCoherencia;
  CalificacionEvaluacion    evaluacionPertinencia;
  
  HtmlPanelGrid panelGridEvaluadorBasico;
  //HtmlPanelGrid panelGridEvaluadorMesaTrabajo;
  String nombreEvaluador = "";
    
  float    promedioCalificacion=0;
  List 	   listaCC;
  
  public ManejadorResumenEvaluacion()	
  {
  	super();
  	//SE CREA LA LISTA DE POSIBLES RECOMENDACIONES PARA EL PROYECTO
  	panelGridEvaluadorBasico = new HtmlPanelGrid();
  	//panelGridEvaluadorMesaTrabajo = new HtmlPanelGrid();
  	
  	recomendaciones    = new SelectItem[3];
  	recomendaciones[0] = new SelectItem("A","Aprobar");
  	recomendaciones[1] = new SelectItem("M","Aprobar con modificaciones");
  	recomendaciones[2] = new SelectItem("N","No aprobar");
    //SE OBTIENE EL OBJETO PROYECTO EVALUACION  	    
    //evaluacionProyecto     = servicioProyecto.obtenerProyectoEvaluador((ProyectoEvaluador)sesion.getAttribute("proyectoEvaluador"));
  	evaluacionProyecto     = ((ProyectoEvaluador)sesion.getAttribute("proyectoEvaluador"));
  	 evaluacionProyecto=servicioProyecto.obtenerProyectoEvaluador(evaluacionProyecto);
  	listaCC = new Vector(evaluacionProyecto.getCalificaciones());
    Collections.sort(listaCC,new ComparadorCriterioEvaluacionPorId());
    // TODO Si el evaluador es de mesa de trabajo, se ocultan todos los otros atributos
    if(evaluacionProyecto.getEvaluador().getId().getDocumento().equals("MESATRABAJO")){
        panelGridEvaluadorBasico.setRendered(false);
        // Juan Pablo: Se asigna el nombre del evaluador dependiendo del tipo de evaluador
        nombreEvaluador = "Mesa de trabajo";
    }else{
        //panelGridEvaluadorMesaTrabajo.setRendered(false);
        nombreEvaluador = "Evaluador " + evaluacionProyecto.getId();
//        if(evaluacionProyecto.getCalificaciones().isEmpty()){
//    	  	//SI LA EVALUACION DEL PROYECTO ES NUEVA SE CREAN LAS DIFERENTES CALIFICACIONES
//    	  	evaluacionPlanteamiento=new CalificacionEvaluacion();
//    	  	evaluacionPlanteamiento.setCriterio(new CriterioEvaluacion(new Long(1)));	    
//    	  	evaluacionObjetivos    =new CalificacionEvaluacion();
//    	    evaluacionObjetivos.setCriterio(new CriterioEvaluacion(new Long(2)));
//    	    evaluacionMetodologia  =new CalificacionEvaluacion();
//    	    evaluacionMetodologia.setCriterio(new CriterioEvaluacion(new Long(3)));
//    	    evaluacionResultados   =new CalificacionEvaluacion();
//    	    evaluacionResultados.setCriterio(new CriterioEvaluacion(new Long(4)));
//    	    evaluacionImpacto      =new CalificacionEvaluacion();
//    	    evaluacionImpacto.setCriterio(new CriterioEvaluacion(new Long(5)));
//    	    evaluacionGrupo        =new CalificacionEvaluacion();
//    	    evaluacionGrupo.setCriterio(new CriterioEvaluacion(new Long(6)));
//    	    evaluacionPresupuesto  =new CalificacionEvaluacion();
//    	    evaluacionPresupuesto.setCriterio(new CriterioEvaluacion(new Long(7)));
//    	    evaluacionCoherencia   =new CalificacionEvaluacion();
//    	    evaluacionCoherencia.setCriterio(new CriterioEvaluacion(new Long(8)));
//    	    evaluacionPertinencia  =new CalificacionEvaluacion();
//    	    evaluacionPertinencia.setCriterio(new CriterioEvaluacion(new Long(9)));	    
//        }
//        else{
//        	//SE CARGAN LAS DIFERENTES CALIFICACIONES Y SE ASIGNAN A LAS CALIFICACIONES RESPECTIVAS EN EL JSF
//        	evaluacionPlanteamiento=buscarEvaluacion(1);
//        	if(evaluacionPlanteamiento==null)
//        	{
//        		evaluacionPlanteamiento=new CalificacionEvaluacion();
//        	  	evaluacionPlanteamiento.setCriterio(new CriterioEvaluacion(new Long(1)));	
//        	}
//    	    evaluacionObjetivos    =buscarEvaluacion(2);
//        	if(evaluacionObjetivos==null)
//        	{
//        		evaluacionObjetivos    =new CalificacionEvaluacion();
//        	    evaluacionObjetivos.setCriterio(new CriterioEvaluacion(new Long(2)));	
//        	}
//    	    evaluacionMetodologia  =buscarEvaluacion(3);
//        	if(evaluacionMetodologia==null)
//        	{
//        		evaluacionMetodologia  =new CalificacionEvaluacion();
//        	    evaluacionMetodologia.setCriterio(new CriterioEvaluacion(new Long(3)));	
//        	}
//    	    evaluacionResultados   =buscarEvaluacion(4);
//        	if(evaluacionResultados==null)
//        	{
//        		evaluacionResultados   =new CalificacionEvaluacion();
//        	    evaluacionResultados.setCriterio(new CriterioEvaluacion(new Long(4)));	
//        	}
//    	    evaluacionImpacto      =buscarEvaluacion(5);
//        	if(evaluacionImpacto==null)
//        	{
//        		evaluacionImpacto      =new CalificacionEvaluacion();
//        	    evaluacionImpacto.setCriterio(new CriterioEvaluacion(new Long(5)));	
//        	}
//    	    evaluacionGrupo        =buscarEvaluacion(6);
//        	if(evaluacionGrupo==null)
//        	{
//        		evaluacionGrupo        =new CalificacionEvaluacion();
//        	    evaluacionGrupo.setCriterio(new CriterioEvaluacion(new Long(6)));	
//        	}
//    	    evaluacionPresupuesto  =buscarEvaluacion(7);
//        	if(evaluacionPresupuesto==null)
//        	{
//        		evaluacionPresupuesto  =new CalificacionEvaluacion();
//        	    evaluacionPresupuesto.setCriterio(new CriterioEvaluacion(new Long(7)));
//        	}
//    	    evaluacionCoherencia   =buscarEvaluacion(8);
//        	if(evaluacionCoherencia==null)
//        	{
//        		evaluacionCoherencia   =new CalificacionEvaluacion();
//        	    evaluacionCoherencia.setCriterio(new CriterioEvaluacion(new Long(8)));	
//        	}
//    	    evaluacionPertinencia  =buscarEvaluacion(9);
//        	if(evaluacionPertinencia==null)
//        	{
//        		evaluacionPertinencia  =new CalificacionEvaluacion();
//        	    evaluacionPertinencia.setCriterio(new CriterioEvaluacion(new Long(9)));	
//        	}
    	    calcularPromedioInicial();
//        }
        
    }    
             
  }
   
  private CalificacionEvaluacion buscarEvaluacion(long id)
  {
  	Set listaEvaluaciones=evaluacionProyecto.getCalificaciones();
  	Iterator it=listaEvaluaciones.iterator();
	CalificacionEvaluacion d=null;
    int i=0;         
    while(it.hasNext())
       {
        d = (CalificacionEvaluacion)it.next();
        if(id==(d.getCriterio().getId()).longValue())     
        	break;  
        else
          d=null;
        i=i+1;	
       }     
    return d;    	
  }
    
  private void calcularPromedioInicial()
  {
  	Set listaEvaluaciones=evaluacionProyecto.getCalificaciones();
  	Iterator it=listaEvaluaciones.iterator();
	CalificacionEvaluacion d=new CalificacionEvaluacion();
    int i=0;         
    while(it.hasNext())
       {
        d = (CalificacionEvaluacion)it.next();
        if(d.getCuantitativa()!=null)
        {
        	promedioCalificacion=promedioCalificacion+((d.getCuantitativa()).floatValue()*d.getCriterio().getFactor().floatValue());
        }
        i=i+1;	
       }
//    promedioCalificacion=promedioCalificacion/9;
  }
  
  public String verEvaluadores(){
      sesion.removeAttribute("manejadorResumenEvaluacion");
      sesion.removeAttribute("manejadorConsultaEvaluadores");
      sesion.removeAttribute("proyectoEvaluador");
      return "evaluadores";
  }
     
	public CalificacionEvaluacion getEvaluacionCoherencia() {
		return evaluacionCoherencia;
	}
	public void setEvaluacionCoherencia(CalificacionEvaluacion evaluacionCoherencia) {
		this.evaluacionCoherencia = evaluacionCoherencia;
	}
	public CalificacionEvaluacion getEvaluacionGrupo() {
		return evaluacionGrupo;
	}
	public void setEvaluacionGrupo(CalificacionEvaluacion evaluacionGrupo) {
		this.evaluacionGrupo = evaluacionGrupo;
	}
	public CalificacionEvaluacion getEvaluacionImpacto() {
		return evaluacionImpacto;
	}
	public void setEvaluacionImpacto(CalificacionEvaluacion evaluacionImpacto) {
		this.evaluacionImpacto = evaluacionImpacto;
	}
	public CalificacionEvaluacion getEvaluacionMetodologia() {
		return evaluacionMetodologia;
	}
	public void setEvaluacionMetodologia(
			CalificacionEvaluacion evaluacionMetodologia) {
		this.evaluacionMetodologia = evaluacionMetodologia;
	}
	public CalificacionEvaluacion getEvaluacionObjetivos() {
		return evaluacionObjetivos;
	}
	public void setEvaluacionObjetivos(CalificacionEvaluacion evaluacionObjetivos) {
		this.evaluacionObjetivos = evaluacionObjetivos;
	}
	public CalificacionEvaluacion getEvaluacionPertinencia() {
		return evaluacionPertinencia;
	}
	public void setEvaluacionPertinencia(
			CalificacionEvaluacion evaluacionPertinencia) {
		this.evaluacionPertinencia = evaluacionPertinencia;
	}
	public CalificacionEvaluacion getEvaluacionPlanteamiento() {
		return evaluacionPlanteamiento;
	}
	public void setEvaluacionPlanteamiento(
			CalificacionEvaluacion evaluacionPlanteamiento) {
		this.evaluacionPlanteamiento = evaluacionPlanteamiento;
	}
	public CalificacionEvaluacion getEvaluacionPresupuesto() {
		return evaluacionPresupuesto;
	}
	public void setEvaluacionPresupuesto(
			CalificacionEvaluacion evaluacionPresupuesto) {
		this.evaluacionPresupuesto = evaluacionPresupuesto;
	}
	public ProyectoEvaluador getEvaluacionProyecto() {
		return evaluacionProyecto;
	}
	public void setEvaluacionProyecto(ProyectoEvaluador evaluacionProyecto) {
		this.evaluacionProyecto = evaluacionProyecto;
	}
	public CalificacionEvaluacion getEvaluacionResultados() {
		return evaluacionResultados;
	}
	public void setEvaluacionResultados(CalificacionEvaluacion evaluacionResultados) {
		this.evaluacionResultados = evaluacionResultados;
	}	
	public SelectItem[] getRecomendaciones() {
		return recomendaciones;
	}
	public void setRecomendaciones(SelectItem[] recomendaciones) {
		this.recomendaciones = recomendaciones;
	}	
	public float getPromedioCalificacion() {
		return promedioCalificacion;
	}
	public void setPromedioCalificacion(float promedioCalificacion) {
		this.promedioCalificacion = promedioCalificacion;
	}	
	public HtmlPanelGrid getPanelGridEvaluadorBasico() {
	    return panelGridEvaluadorBasico;
	}
	public void setPanelGridEvaluadorBasico(HtmlPanelGrid panelGridEvaluadorBasico) {
	    this.panelGridEvaluadorBasico = panelGridEvaluadorBasico;
	}
	
	public String getNombreEvaluador() {
	    return nombreEvaluador;
	}	
	public void setNombreEvaluador(String nombreEvaluador) {
	    this.nombreEvaluador = nombreEvaluador;
	}
	
	public List getListaCC() {
	    return listaCC;
	}
	public void setListaCC(List listaCC) {
	    this.listaCC = listaCC;
	}
	
	public String regresar(){
		return "informacionProyecto";
	}
}
