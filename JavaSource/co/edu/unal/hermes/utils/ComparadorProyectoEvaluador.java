
package co.edu.unal.hermes.utils;

import java.util.Comparator;

import co.edu.unal.hermes.modelo.ProyectoEvaluador;


public class ComparadorProyectoEvaluador implements Comparator{

 
    public int compare(Object arg0, Object arg1) {
        ProyectoEvaluador p1=(ProyectoEvaluador)arg0;
        ProyectoEvaluador p2=(ProyectoEvaluador)arg1;
        if(p1==null)
        {
            return -1;
        }
        if(p2==null )
        {
            return 1;
        }
        
        return p1.getProyecto().getId().compareTo(p2.getProyecto().getId());
    }
}
