package co.edu.unal.hermes.vista.personas;

import co.edu.unal.hermes.modelo.Estudiante;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorBecario extends ManejadorBase
{
	Persona persona;
	Estudiante estudiante;
	public ManejadorBecario()
	{
	    persona= (Persona)sesion.getAttribute("persona");
	    
	} 
	
}