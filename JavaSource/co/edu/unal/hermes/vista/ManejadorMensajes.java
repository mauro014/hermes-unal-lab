
package co.edu.unal.hermes.vista;

import java.util.List;
import java.util.Vector;


public class ManejadorMensajes extends ManejadorBase{
    
    public String mensaje;
    
    
    public String getMensaje() {
        String m=null;
        if(sesion.getAttribute("mensaje")!=null)
        {
            m = new String((String)sesion.getAttribute("mensaje"));
        }
        sesion.setAttribute("mensaje",null);
        return "* " + m;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
        sesion.setAttribute("mensaje",mensaje);
    }
    /**
     * @author Rodrigo Gallo
     * Método q no se que hace cuando llegue ya estaba, pienso que esta pensado para ver la forma
     * de reportar todos los errores o mensajes al tiempo y no por cada uno de los campos, como hace
     * por defeto JSF
     * @param a
     * @return
     */
    public List p(String a)
    {
        List l=new Vector();
        l.add("fdsfdas");
        return l; 
    }
    public boolean getVacio()
    {
    	return sesion.getAttribute("mensaje")==null ;
    }
}
