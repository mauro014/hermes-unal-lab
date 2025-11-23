
package co.edu.unal.hermes.vista;


public class ManejadorMensajes2 extends ManejadorBase{
    
    public String mensaje;
    
    
    public String getMensaje() {
        String m=null;
        if(sesion.getAttribute("mensaje2")!=null)
        {
            m = new String((String)sesion.getAttribute("mensaje2"));
        }
        sesion.setAttribute("mensaje2",null);
        
        return m;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
        sesion.setAttribute("mensaje2",mensaje);
    }
    
    public boolean getVacio(){
        return sesion.getAttribute("mensaje2")==null;
    }
}
