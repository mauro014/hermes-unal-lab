package co.edu.unal.hermes.vista;

import java.util.List;
import java.util.Vector;

import org.apache.myfaces.custom.navmenu.NavigationMenuItem;

import co.edu.unal.hermes.modelo.Servicio;

public class ServicioRolVista {

	private Servicio servicio;
	private List listaHijosServicios=new Vector();
	private NavigationMenuItem menu;
	public Servicio getServicio() {
		return servicio;
	}
	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}
	public List getListaHijosServicios() {
		return listaHijosServicios;
	}
	public void setListaHijosServicios(List listaHijosServicios) {
		this.listaHijosServicios = listaHijosServicios;
	}
	public NavigationMenuItem getMenu() {
		return menu;
	}
	public void setMenu(NavigationMenuItem menu) {
		this.menu = menu;
	}
}
