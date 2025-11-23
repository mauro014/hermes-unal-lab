package co.edu.unal.hermes.vista.proyectos.fichaquipu;

import java.util.ArrayList;
import java.util.List;

import co.edu.unal.hermes.modelo.Financiacion;
import co.edu.unal.hermes.modelo.Gasto;

public class FinanciacionVista{
	
	private boolean       colapsado;
	private Financiacion  financiacion;
	private List          listaGastosVista;
	
    public FinanciacionVista(Financiacion f){
    	colapsado=false;
    	financiacion=f;
    	listaGastosVista=new ArrayList();    	    	    	
    }    
	public boolean isColapsado() {
		return colapsado;
	}
	public void setColapsado(boolean colapsado) {
		this.colapsado = colapsado;
	}	
	public Financiacion getFinanciacion() {
		return financiacion;
	}
	public void setFinanciacion(Financiacion financiacion) {
		this.financiacion = financiacion;
	}	
	public List getListaGastosVista() {
		List listaGastos=new ArrayList();
		listaGastosVista.clear();
		listaGastos.addAll(financiacion.getGastos());    	   
    	for(int i=0;i<listaGastos.size();i++){
    		listaGastosVista.add(new GastoVista((Gasto)listaGastos.get(i)));
    	}
		return listaGastosVista;
	}
	public void setListaGastosVista(List listaGastosVista) {
		this.listaGastosVista = listaGastosVista;
	}
}
