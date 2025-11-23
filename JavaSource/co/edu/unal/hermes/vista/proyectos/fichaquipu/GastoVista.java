package co.edu.unal.hermes.vista.proyectos.fichaquipu;

import co.edu.unal.hermes.modelo.Gasto;

public class GastoVista{
	
	private boolean       colapsado;
	private Gasto  gasto;
	
    public GastoVista(Gasto g){
    	gasto=g;
    }    
	public boolean isColapsado() {
		return colapsado;
	}
	public void setColapsado(boolean colapsado) {
		this.colapsado = colapsado;
	}			
	public Gasto getGasto() {
		return gasto;
	}
	public void setGasto(Gasto gasto) {
		this.gasto = gasto;
	}
}
