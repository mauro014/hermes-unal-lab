package co.edu.unal.hermes.modelo.servicioPersona;

import co.edu.unal.hermes.modelo.IdPersona;

public class ConsultaInvestigadores {

    private IdPersona id;
    
    private String nombre1;
    private String nombre2;
    private String apellido1;
    private String apellido2;
       
    public IdPersona getId() {
        return id;
    }
   
    private void setId(IdPersona id) {
        this.id = id;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getNombre1() {
        return nombre1;
    }
    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }
    public String getNombre2() {
        return nombre2;
    }
    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }
}
