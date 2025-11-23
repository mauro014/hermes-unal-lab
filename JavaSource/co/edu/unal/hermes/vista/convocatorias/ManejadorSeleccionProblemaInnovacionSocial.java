package co.edu.unal.hermes.vista.convocatorias;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.model.SelectItem;

import org.primefaces.component.menuitem.MenuItem;

import co.edu.unal.hermes.modelo.AreaTematica;
import co.edu.unal.hermes.modelo.AreaTematicaVista;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.Preinscripcion_ECP;
import co.edu.unal.hermes.modelo.ValoresListasProyecto;
import co.edu.unal.hermes.vista.ManejadorBase;
import co.edu.unal.hermes.vista.proyectos.ManejadorMenuFormularios;

public class ManejadorSeleccionProblemaInnovacionSocial extends ManejadorBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = -521845384791019431L;
	private SelectItem[] problemasItem;
	private List<Preinscripcion_ECP> listaProblemas;
	private String problemaSeleccionado;

	public ManejadorSeleccionProblemaInnovacionSocial() {

		// listaProblemas = servicioGeneral
		// .obtenerObjetosLimitado(
		// Preinscripcion_ECP.class,
		// "select #id_pre e.id_pre, #nombreProblema e.nombreProblema from Preinscripcion_ECP e where e.curso = 39232");
		listaProblemas = servicioGeneral.obtenerObjetosLimitado(Preinscripcion_ECP.class,"select #id_pre e.id_pre, #nombreProblema e.nombreProblema from Preinscripcion_ECP e where e.curso = 42585");

		if (listaProblemas != null && listaProblemas.size() > 0) {
			problemasItem = new SelectItem[listaProblemas.size()];
			for (int i = 0; i < listaProblemas.size(); i++) {
				Preinscripcion_ECP pro = (Preinscripcion_ECP) listaProblemas
						.get(i);
				problemasItem[i] = new SelectItem(pro.getId_pre(),
						pro.getId_pre() + " - " + pro.getNombreProblema());
				pro = null;
			}
		}
	}

	public String siguiente() {
		sesion.removeAttribute("ManejadorTrabajoPrevioProyectoES_Inno");
		sesion.removeAttribute("manejadorMenuFormularios");

		borrarManejadoresInsercionProyecto();
		if (problemaSeleccionado.equals("")) {
			mensajeError("Por favor seleccione el problema de innvocación social asociado al proyecto");
			return "";
		} else {
			sesion.setAttribute("idProblemaInnovacionSocial2018",
					problemaSeleccionado);
			return "fichaMinimaHome";
		}

	}

	public String volver() {
		sesion.removeAttribute("ManejadorPreinscripcionECP");
		sesion.removeAttribute("ManejadorPreinscripcionBancoProblemas");
		sesion.removeAttribute("manejadorSeleccionProblemaInnovacionSocial");
		return "misProyectos";
	}

	public SelectItem[] getProblemasItem() {
		return problemasItem;
	}

	public void setProblemasItem(SelectItem[] problemasItem) {
		this.problemasItem = problemasItem;
	}

	public String getProblemaSeleccionado() {
		return problemaSeleccionado;
	}

	public void setProblemaSeleccionado(String problemaSeleccionado) {
		this.problemaSeleccionado = problemaSeleccionado;
	}

}
