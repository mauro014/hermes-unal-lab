package co.edu.unal.hermes.vista;



public class ManejadorCierreSesion extends ManejadorBase{

	public ManejadorCierreSesion() {
		super();
	}
	
	public String cerrarSesion(){
		sesion.invalidate();
		return "paginaInicial";
	}
	
	
}
