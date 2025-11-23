
package co.edu.unal.hermes.vista.proyectos.manejadorproyecto;

import javax.faces.component.UIData;

import co.edu.unal.hermes.modelo.Actividad;


public class VistaActividad {

    Actividad actividad;
    UIData tablaActividad;
    
    public VistaActividad(Actividad actividad)
    {
        this.actividad=actividad;
        tablaActividad=new UIData();
    }
    public UIData getTablaActividad() {
        return tablaActividad;
    }
    public void setTablaActividad(UIData tablaActividad) {
        this.tablaActividad = tablaActividad;
    }
    public Actividad getActividad() {
        return actividad;
    }
    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }
}
