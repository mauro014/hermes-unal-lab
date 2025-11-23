package co.edu.unal.hermes.vista.laboratorios;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioSolicitudCreacionLaboratorio;
import co.edu.unal.hermes.modelo.laboratorios.SolicitudLaboratorios;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorMenuFormularioLaboratorios extends ManejadorBase {

	protected MenuModel modeloMenu;

	public ManejadorMenuFormularioLaboratorios() {

		int idManejador = -1;

		if (sesion.getAttribute("Laboratorio") != null) {
			Laboratorio laboratorioActual = ((Laboratorio) sesion.getAttribute("Laboratorio"));
			idManejador = laboratorioActual.getEtapaRegistro();
		}

		modeloMenu = new DefaultMenuModel();
		int i = 0;

		MenuItem menuItem = new MenuItem();
		menuItem.setValue("Información General");
		// menuItem.setIcon("ui-icon-note");
		menuItem.setOutcome("CrearLaboratorio");
		modeloMenu.addMenuItem(menuItem);
		i++;

		menuItem = new MenuItem();
		menuItem.setValue("Recurso Humano");
		menuItem.setIcon("ui-icon-person");
		menuItem.setIcon("ui-icon-carat-1-e");
		menuItem.setOutcome("laboratorioRecursoHumano");
		menuItem.setDisabled(idManejador < i);
		modeloMenu.addMenuItem(menuItem);
		i++;

		menuItem = new MenuItem();
		menuItem.setValue("Infraestructura y Riesgos");
		menuItem.setIcon("ui-icon-alert");
		menuItem.setIcon("ui-icon-carat-1-e");
		menuItem.setOutcome("laboratorioRiesgos");
		menuItem.setDisabled(idManejador < i);
		modeloMenu.addMenuItem(menuItem);
		i++;

		menuItem = new MenuItem();
		menuItem.setValue("Gestión");
		menuItem.setIcon("ui-icon-suitcase");
		menuItem.setIcon("ui-icon-carat-1-e");
		menuItem.setOutcome("laboratorioGestion");
		menuItem.setDisabled(idManejador < i);
		modeloMenu.addMenuItem(menuItem);
		i++;

		menuItem = new MenuItem();
		menuItem.setValue("Equipos");
		menuItem.setIcon("ui-icon-wrench");
		menuItem.setIcon("ui-icon-carat-1-e");
		menuItem.setOutcome("laboratorioEquipos");
		menuItem.setDisabled(idManejador < i);
		modeloMenu.addMenuItem(menuItem);
		i++;
		
		menuItem = new MenuItem();
		menuItem.setValue("Proyectos");
		menuItem.setIcon("ui-icon-lightbulb");
		menuItem.setIcon("ui-icon-carat-1-e");
		menuItem.setOutcome("laboratorioProyectos");
		menuItem.setDisabled(idManejador < i);
		modeloMenu.addMenuItem(menuItem);
		i++;

		menuItem = new MenuItem();
		menuItem.setValue("Investigación");
		menuItem.setIcon("ui-icon-lightbulb");
		menuItem.setIcon("ui-icon-carat-1-e");
		menuItem.setOutcome("laboratorioInvestigacion");
		menuItem.setDisabled(idManejador < i);
		modeloMenu.addMenuItem(menuItem);
		i++;

		menuItem = new MenuItem();
		menuItem.setValue("Docencia");
		menuItem.setIcon("ui-icon-pencil");
		menuItem.setIcon("ui-icon-carat-1-e");
		menuItem.setOutcome("laboratorioDocencia");
		menuItem.setDisabled(idManejador < i);
		modeloMenu.addMenuItem(menuItem);
		i++;

		menuItem = new MenuItem();
		menuItem.setValue("Servicios");
		menuItem.setIcon("ui-icon-cart");
		menuItem.setIcon("ui-icon-carat-1-e");
		menuItem.setOutcome("laboratorioEnsayosServicios");
		menuItem.setDisabled(idManejador < i);
		modeloMenu.addMenuItem(menuItem);
		i++;
		
		//Sección oculta mientras se define que va a pasar con METRORED/SEP 2018
		//Sección oculta mientras se define que va a pasar con METRORED/DIC 2019
		menuItem = new MenuItem();
		menuItem.setValue("Metrología");
		menuItem.setIcon("ui-icon-cart");
		menuItem.setIcon("ui-icon-carat-1-e");
		menuItem.setOutcome("laboratorioMetrologia");
		menuItem.setDisabled(idManejador < i);
		modeloMenu.addMenuItem(menuItem);
		i++;

		// Presupuesto aparece solo si se trata de una
		// solicitud:
//		LaboratorioSolicitudCreacionLaboratorio solicitudLab = (LaboratorioSolicitudCreacionLaboratorio) sesion.getAttribute("solicitudLaboratorio");
//		if (solicitudLab != null) {
//			System.out.println("menuLab solicitudLab");
//			menuItem = new MenuItem();
//			menuItem.setValue("Presupuesto");
//			// menuItem.setIcon("ui-icon-cart");
//			menuItem.setIcon("ui-icon-carat-1-e");
//			menuItem.setOutcome("laboratorioPresupuesto");
//			menuItem.setDisabled(idManejador < i);
//			modeloMenu.addMenuItem(menuItem);
//			i++;
//		}

	}

	/**
	 * @return the modeloMenu
	 */
	public MenuModel getModeloMenu() {
		return modeloMenu;
	}

}
