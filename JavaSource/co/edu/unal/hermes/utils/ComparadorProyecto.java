
package co.edu.unal.hermes.utils;

import java.util.Comparator;

import co.edu.unal.hermes.modelo.Proyecto;


public class ComparadorProyecto implements Comparator{

 
    public int compare(Object arg0, Object arg1) {
        Proyecto p1=(Proyecto)arg0;
        Proyecto p2=(Proyecto)arg1;
        if(p1==null)
        {
            return -1;
        }
        if(p2==null )
        {
            return 1;
        }
        
        return p1.getId().compareTo(p2.getId());
    }
}
