package co.edu.unal.hermes.vista.proyectos.manejadorproyecto;

import co.edu.unal.hermes.modelo.InvestigadorProyecto;

public class InvestigadorProyectoVista {
	InvestigadorProyecto ip;
	
	boolean edicion;
	public InvestigadorProyecto getIp() {
		return ip;
	}
	public void setIp(InvestigadorProyecto ip) {
		this.ip = ip;
	}
	public boolean isEdicion() {
		return edicion;
	}
	public void setEdicion(boolean edicion) {
		this.edicion = edicion;
	}
	public InvestigadorProyecto getInvestigadorProyecto() {
		return ip;
	}
	public void setInvestigadorProyecto(InvestigadorProyecto ip) {
		this.ip = ip;
	}
}
