
package co.edu.unal.hermes.utils;

import java.util.Comparator;

import co.edu.unal.hermes.modelo.CalificacionEvaluacion;


public class ComparadorCriterioEvaluacionPorId implements Comparator{

 
    public int compare(Object arg0, Object arg1) {
        
        CalificacionEvaluacion c1=(CalificacionEvaluacion)arg0;
        CalificacionEvaluacion c2=(CalificacionEvaluacion)arg1;
        if(c1==null)
        {
            return -1;
        }
        if(c2==null )
        {
            return 1;
        }
        
        return c1.getCriterio().getId().compareTo(c2.getCriterio().getId());
    }
    
    
}
