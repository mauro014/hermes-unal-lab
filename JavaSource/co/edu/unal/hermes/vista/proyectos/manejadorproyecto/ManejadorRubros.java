/**
 * @author  Ing Hernán Darío Bernal Parra
 */

//TODO: CARGAR RUBROS FINANCIABLES

package co.edu.unal.hermes.vista.proyectos.manejadorproyecto;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.menuitem.MenuItem;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Financiacion;
import co.edu.unal.hermes.modelo.Formulario;
import co.edu.unal.hermes.modelo.Gasto;
import co.edu.unal.hermes.modelo.HistoricoFormularioProyecto;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.Modalidad;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Registro;
import co.edu.unal.hermes.modelo.RestriccionConvocatoria;
import co.edu.unal.hermes.modelo.RubroFinanciable;
import co.edu.unal.hermes.modelo.TipoModalidad;
import co.edu.unal.hermes.modelo.TipoRubro;
import co.edu.unal.hermes.vista.proyectos.ManejadorMenuFormularios;
import co.edu.unal.hermes.vista.proyectos.fichaquipu.FinanciacionVista;
import co.edu.unal.hermes.vista.proyectos.fichaquipu.GastoVista;

public class ManejadorRubros extends ManejadorProyecto {

	// TABLA PARA MOSTRAR LOS RUBROS ASOCIADOS AL PROYECTO
	private DataTable tablaGastos; // TABLA DE RUBROS
	private Gasto gastoActual;

	private TipoRubro tipoRubroActual; // TIPO DE RUBRO ACTUAL
	private SelectItem[] tiposRubroItem; // LISTA DE TIPOS DE RUBRO A MOSTRAR
	private List listaTiposRubros; // LISTA GENERAL DE TIPOS DE RUBROS
	private List listaRubrosFinanciables; // LISTA GENERAL DE TIPOS DE RUBROS
	private List listaGastosProyecto; // LISTA DE RUBROS ASOCIADOS AL PROYECTO

	private DataTable tablaFinanciacion; // TABLA PARA MOSTRAR LAS FUENTES DE
	// FINANCIACION ASOCIADAS AL
	// PROYECTO
	private List listaFinanciaciones; // LISTA DE FINANCIACIONES ASOCIDAS AL
	// PROYECTO
	private List listaFinanciacionVista; // LISTA DE FINANCIACIONES ASOCIDAS AL
	// PROYECTO
	private SelectItem[] fuentesFinancierasItem; // LISTA DE FUENTES FINANCIERAS
	// A MOSTRAR

	private String[] vigencias = { "Primer año", "Segundo año" };
	private SelectItem[] vigenciaItem; // LISTA DE FUENTES FINANCIERAS A MOSTRAR

	private long valorTotalInvestigacion; // VALOR TOTAL DE LA SUMA DE LAS
	// VIGENCIAS
	private String mensajeRubros = ""; // MENSAJE DE VALIDACION ASOCIADO A LA
	// CLASE

	private Long totalFinanciacion;

	private boolean esProgramaNacional = false;

	private String titulo1;
	private String titulo2;

	private GastoVista gastoSeleccionado;

	private List listaInvestigadoresVista;
	private Long valorJornadaDocente = 0l;
	private boolean esExtensionSolidaria = false;

	public ManejadorRubros() {
		super();
		idManejador = RUBROS;

		titulo1 = "Proyecto:";
		titulo2 = "Búsqueda de Integrantes del Proyecto";

		listaGastosProyecto = new ArrayList();
		listaFinanciaciones = new ArrayList();
		listaFinanciacionVista = new ArrayList();
		gastoActual = new Gasto();
		gastoActual.setValor(new Long(0));
		// Asignar por defecto 1 a la vigencia y la cantidad
		gastoActual.setCantidad(1);
		gastoActual.setVigencia(1);
		gastoActual.setDescripcion("");
		proyectoActual = servicioProyecto.obtenerProyecto(proyectoActual
				.getId(), ProyectoDAOHibernate.RUBROS);
		vigenciaItem = new SelectItem[vigencias.length];
		for (int i = 0; i < vigencias.length; i++) {
			vigenciaItem[i] = new SelectItem(new Integer(i + 1), vigencias[i]);
		}
		cargarTiposRubro();
		cargarFuentesProyecto();

		if (proyectoActual.getModalidad().getTipo().getId().compareTo("PN") == 0) {
			this.esProgramaNacional = true;

			titulo1 = "Programa:";
			titulo2 = "Integrantes del programa";
		} else {
			titulo1 = "Proyecto:";
			titulo2 = "Integrantes del proyecto de investigación";
		}

		if (proyectoActual.getModalidad() instanceof Convocatoria) {
			System.out.println("Extensión Solidaria");
			RestriccionConvocatoria r = ((Convocatoria) proyectoActual
					.getModalidad()).getRestriccion();
			if (r != null) {
				System.out.println(r.getId());
				if (r.getId().equals(RestriccionConvocatoria.CONV_EXT_SOL)) {

					esExtensionSolidaria = true;

				}
			}
		} else {
			System.out.println("no es Extensión Solidaria");
		}

		if (esExtensionSolidaria) {
			Financiacion fi = new Financiacion();

			for (int i = 0; i < listaFinanciaciones.size(); i++) {
				Financiacion fin = (Financiacion) listaFinanciaciones.get(i);
				if (fin.getFuente().getId().equals("453")) {
					fi = (Financiacion) listaFinanciaciones.get(i);
				}
			}

			Financiacion f = buscarFinanciacion(fi.getId());
			if (f.getGastos().isEmpty()) {
				agregarGastoEspecie_ES();
			}

		}
	}

	// DEFINICION DE FUNCIONES BASICAS
	protected void cargarValoresIniciales() {
	}

	public String atras() {
		sesion.removeAttribute("manejadorRubros");

		// ///////MODIFICADO GIOVANNI
		ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion
				.getAttribute("manejadorMenuFormularios");
		boolean bandera = false;
		if (man.getItemProyecto() != null) {
			// NavigationMenuItem lis[] =
			// man.getItemProyecto()[0].getNavigationMenuItems();
			MenuItem lis[] = man.getMenuItemArray();
			if (lis != null) {
				for (int i = lis.length - 1; i >= 0; i--) {
					if (bandera) {
						if (lis[i].isRendered()) {
							return lis[i].getOutcome();
						}
					}

					if (lis[i].getOutcome().equals("irRubros")) {
						bandera = true;
					}

				}
			}
		}
		// ////////////////
		return "irFuentes";
	}

	public String salir() {
		sesion.removeAttribute("proyecto");
		borrarManejadoresInsercionProyecto();
		return "misProyectos";
	}

	public String salirGuardar() {
		if (validarGastos()) {

			// ///////MODIFICADO GIOVANNI
			String link = "";
			ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion
					.getAttribute("manejadorMenuFormularios");
			boolean bandera = false;
			int pos = 0;
			if (man.getItemProyecto() != null) {
				// NavigationMenuItem lis[] =
				// man.getItemProyecto()[0].getNavigationMenuItems();
				MenuItem lis[] = man.getMenuItemArray();
				if (lis != null) {
					for (int i = 0; i < lis.length; i++) {
						if (bandera) {
							if (lis[i].isRendered()) {
								sesion
										.removeAttribute("manejadorMenuFormularios");
								link = lis[i].getOutcome();
								break;
							}
						}

						if (lis[i].getOutcome().equals("irRubros")) {
							bandera = true;
						}
						if (lis[i].isRendered()) {
							pos++;
						}

					}
				}
			}

			if ((proyectoActual.getEstadoProyecto().getId()).equals("I")
					&& (pos - 1) >= proyectoActual.getFase().intValue()) {
				proyectoActual.setFase(new Integer((proyectoActual.getFase())
						.intValue() + 1));
			}

			if (proyectoActual.getId() != null) {
				// Ing. Wilver Alexander Martínez Martínez -wam²
				// Cambio - Registro de cambios
				Persona personaAux = new Persona();
				personaAux = (Persona) sesion.getAttribute("persona");

				Formulario formulario = new Formulario();
				List listaFormulario = new ArrayList();

				listaFormulario = servicioGeneral
						.obtenerListaObjetos("Formulario where id ='300'");
				formulario = (Formulario) listaFormulario.get(0);

				HistoricoFormularioProyecto historicoFormualrioProyecto = new HistoricoFormularioProyecto();
                historicoFormualrioProyecto.setDocPersona(personaAux.getId().getDocumento());
                historicoFormualrioProyecto.setTipoDocumentoPersona(personaAux.getId().getTipoDocumento());
                historicoFormualrioProyecto.setFormulario(formulario);
                historicoFormualrioProyecto.setProyecto(proyectoActual);
                historicoFormualrioProyecto.setFechaCambio(new Date());
                servicioGeneral.guardarObjeto(historicoFormualrioProyecto);
			}

			servicioProyecto.ingresarProyecto(proyectoActual);
			sesion.removeAttribute("proyecto");
			sesion.removeAttribute("manejadorMenuFormularios");
			borrarManejadoresInsercionProyecto();

			return "misProyectos";
		}
		return "";
	}

	public String siguiente() {
		if (proyectoActual.getModalidad().getTipo().getId().equals(
				TipoModalidad.REGISTRO)) {

			if (proyectoActual.getId() != null) {
				// Ing. Wilver Alexander Martínez Martínez -wam²
				// Cambio - Registro de cambios
				Persona personaAux = new Persona();
				personaAux = (Persona) sesion.getAttribute("persona");

				Formulario formulario = new Formulario();
				List listaFormulario = new ArrayList();

				listaFormulario = servicioGeneral
						.obtenerListaObjetos("Formulario where id ='300'");
				formulario = (Formulario) listaFormulario.get(0);

				HistoricoFormularioProyecto historicoFormualrioProyecto = new HistoricoFormularioProyecto();
                historicoFormualrioProyecto.setDocPersona(personaAux.getId().getDocumento());
                historicoFormualrioProyecto.setTipoDocumentoPersona(personaAux.getId().getTipoDocumento());
                historicoFormualrioProyecto.setFormulario(formulario);
                historicoFormualrioProyecto.setProyecto(proyectoActual);
                historicoFormualrioProyecto.setFechaCambio(new Date());
                servicioGeneral.guardarObjeto(historicoFormualrioProyecto);
			}

			// ///////MODIFICADO GIOVANNI
			String link = "";
			ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion
					.getAttribute("manejadorMenuFormularios");
			boolean bandera = false;
			int pos = 0;
			if (man.getItemProyecto() != null) {
				// NavigationMenuItem lis[] =
				// man.getItemProyecto()[0].getNavigationMenuItems();
				MenuItem lis[] = man.getMenuItemArray();
				if (lis != null) {
					for (int i = 0; i < lis.length; i++) {
						if (bandera) {
							if (lis[i].isRendered()) {
								// sesion.removeAttribute("manejadorMenuFormularios");
								link = lis[i].getOutcome();
								break;
							}
						}

						if (lis[i].getOutcome().equals("irRubros")) {
							bandera = true;
						}
						if (lis[i].isRendered()) {
							pos++;
						}

					}
				}
			}

			if ((proyectoActual.getEstadoProyecto().getId()).equals("I")
					&& (pos - 1) == proyectoActual.getFase().intValue()) {
				proyectoActual.setFase(new Integer((proyectoActual.getFase())
						.intValue() + 1));
			}
			if (proyectoActual.getEstadoProyecto().getId().equals(
					EstadoProyecto.INGRESANDO)) {
				// wam²
				// 20090122
				List listaModalidad = new ArrayList();
				// Cambio para el estado de los proyectos de jornada docente - A
				listaModalidad = servicioGeneral
						.obtenerListaObjetos("Modalidad where id ='"
								+ proyectoActual.getModalidad().getId() + "'");
				Modalidad mod = (Modalidad) listaModalidad.get(0);

				if (mod.getTipo().getId().equals("J")) {
					proyectoActual.cambiarEstadoPersona(EstadoProyecto.ACTIVO,cargarPersonaActual());
					// wamm²
					// 20110218 - Permitir edición siempre de jornadas docentes
					proyectoActual.setPermitirModificacion("S");
				} else {
					proyectoActual.cambiarEstadoPersona(EstadoProyecto.PROPUESTO,cargarPersonaActual());
				}
			}
			// proyectoActual.setFase(new Integer(0));
			for (int i = 0; i < listaFinanciaciones.size(); i++) {
				servicioGeneral.guardarObjeto(listaFinanciaciones.get(i));
			}

			servicioProyecto.ingresarProyecto(proyectoActual);
			sesion.setAttribute("proyecto", proyectoActual);

			sesion.removeAttribute("manejadorRubros");
			// ///////MODIFICADO GIOVANNI
			man = (ManejadorMenuFormularios) sesion
					.getAttribute("manejadorMenuFormularios");
			bandera = false;
			if (man.getItemProyecto() != null) {
				// NavigationMenuItem lis[] =
				// man.getItemProyecto()[0].getNavigationMenuItems();
				MenuItem lis[] = man.getMenuItemArray();
				if (lis != null) {
					for (int i = 0; i < lis.length; i++) {
						if (bandera) {
							if (lis[i].isRendered()) {
								borrarManejadoresInsercionProyecto();
								sesion
										.removeAttribute("manejadorMenuFormularios");
								return lis[i].getOutcome();
							}
						}

						if (lis[i].getOutcome().equals("irRubros")) {
							bandera = true;
						}

					}
				}
			}
			// ////////////////
			sesion.removeAttribute("manejadorMenuFormularios");

			// if(proyectoActual.getModalidad().getTipo().getId().compareTo("PN")
			// == 0){
			// return "irAgendaConocimiento";
			// }else{
			return "irSubirArchivo";
			// }

		} else {

			if (validarGastos()) {
				if ((proyectoActual.getEstadoProyecto().getId()).equals("I")) {
					proyectoActual.setFase(new Integer((proyectoActual
							.getFase()).intValue() + 1));
				}
				if (proyectoActual.getEstadoProyecto().getId().equals(
						EstadoProyecto.INGRESANDO)) {

					// wam²
					// 20090122
					List listaModalidad = new ArrayList();
					// Cambio para el estado de los proyectos de jornada docente
					// - A
					listaModalidad = servicioGeneral
							.obtenerListaObjetos("Modalidad where id ='"
									+ proyectoActual.getModalidad().getId()
									+ "'");
					Modalidad mod = null;
					if (listaModalidad != null && listaModalidad.size() > 1) {
						Modalidad mod2;
						for (int i = 0; i < listaModalidad.size(); i++) {
							mod2 = (Modalidad) listaModalidad.get(i);
							if (mod2 != null) {
								if (mod2.getTipo() != null) {
									mod = mod2;
									break;
								}
							}
						}
					} else
						mod = (Modalidad) listaModalidad.get(0);

					if (mod.getTipo().getId().equals("J")) {
						proyectoActual.cambiarEstadoPersona(EstadoProyecto.ACTIVO,cargarPersonaActual());
						proyectoActual.setPermitirModificacion("S");

					} else {

						proyectoActual.cambiarEstadoPersona(EstadoProyecto.PROPUESTO,cargarPersonaActual());
					}

				}
				// proyectoActual.setFase(new Integer(0));
				for (int i = 0; i < listaFinanciaciones.size(); i++) {
					servicioGeneral.guardarObjeto(listaFinanciaciones.get(i));
				}

				if (proyectoActual.getId() != null) {
					// Ing. Wilver Alexander Martínez Martínez -wam²
					// Cambio - Registro de cambios
					Persona personaAux = new Persona();
					personaAux = (Persona) sesion.getAttribute("persona");

					Formulario formulario = new Formulario();
					List listaFormulario = new ArrayList();

					listaFormulario = servicioGeneral
							.obtenerListaObjetos("Formulario where id ='300'");
					formulario = (Formulario) listaFormulario.get(0);
					HistoricoFormularioProyecto historicoFormualrioProyecto = new HistoricoFormularioProyecto();
	                historicoFormualrioProyecto.setDocPersona(personaAux.getId().getDocumento());
	                historicoFormualrioProyecto.setTipoDocumentoPersona(personaAux.getId().getTipoDocumento());
	                historicoFormualrioProyecto.setFormulario(formulario);
	                historicoFormualrioProyecto.setProyecto(proyectoActual);
	                historicoFormualrioProyecto.setFechaCambio(new Date());
	                servicioGeneral.guardarObjeto(historicoFormualrioProyecto);
				}

				servicioProyecto.ingresarProyecto(proyectoActual);
				sesion.setAttribute("proyecto", proyectoActual);
				sesion.removeAttribute("manejadorMenuFormularios");
				sesion.removeAttribute("manejadorRubros");

				return "irSubirArchivo";

			}
		}
		return "";
	}

	public String siguienteUno() {
		if (proyectoActual.getModalidad().getTipo().getId().equals(
				TipoModalidad.REGISTRO)) {

			// ///////MODIFICADO GIOVANNI
			String link = "";
			ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion
					.getAttribute("manejadorMenuFormularios");
			boolean bandera = false;
			int pos = 0;
			if (man.getItemProyecto() != null) {
				// NavigationMenuItem lis[] =
				// man.getItemProyecto()[0].getNavigationMenuItems();
				MenuItem lis[] = man.getMenuItemArray();
				if (lis != null) {
					for (int i = 0; i < lis.length; i++) {
						if (bandera) {
							if (lis[i].isRendered()) {
								// sesion.removeAttribute("manejadorMenuFormularios");
								link = lis[i].getOutcome();
								break;
							}
						}

						if (lis[i].getOutcome().equals("irRubros")) {
							bandera = true;
						}
						if (lis[i].isRendered()) {
							pos++;
						}

					}
				}
			}

			if ((proyectoActual.getEstadoProyecto().getId()).equals("I")
					&& (pos - 1) == proyectoActual.getFase().intValue()) {
				proyectoActual.setFase(new Integer((proyectoActual.getFase())
						.intValue() + 1));
			}
			if (proyectoActual.getEstadoProyecto().getId().equals(
					EstadoProyecto.INGRESANDO)) {
				// wam²
				// 20090122
				List listaModalidad = new ArrayList();
				// Cambio para el estado de los proyectos de jornada docente - A
				listaModalidad = servicioGeneral
						.obtenerListaObjetos("Modalidad where id ='"
								+ proyectoActual.getModalidad().getId() + "'");
				Modalidad mod = (Modalidad) listaModalidad.get(0);

				if (mod.getTipo().getId().equals("J")) {
					proyectoActual.cambiarEstadoPersona(EstadoProyecto.ACTIVO,cargarPersonaActual());

				} else {

					proyectoActual.cambiarEstadoPersona(EstadoProyecto.PROPUESTO,cargarPersonaActual());
				}
			}
			// proyectoActual.setFase(new Integer(0));
			for (int i = 0; i < listaFinanciaciones.size(); i++) {
				servicioGeneral.guardarObjeto(listaFinanciaciones.get(i));
			}
			servicioProyecto.ingresarProyecto(proyectoActual);
			sesion.setAttribute("proyecto", proyectoActual);

			sesion.removeAttribute("manejadorRubros");
			// ///////MODIFICADO GIOVANNI
			man = (ManejadorMenuFormularios) sesion
					.getAttribute("manejadorMenuFormularios");
			bandera = false;
			if (man.getItemProyecto() != null) {
				// NavigationMenuItem lis[] =
				// man.getItemProyecto()[0].getNavigationMenuItems();
				MenuItem lis[] = man.getMenuItemArray();
				if (lis != null) {
					for (int i = 0; i < lis.length; i++) {
						if (bandera) {
							if (lis[i].isRendered()) {
								borrarManejadoresInsercionProyecto();
								sesion
										.removeAttribute("manejadorMenuFormularios");
								return lis[i].getOutcome();
							}
						}

						if (lis[i].getOutcome().equals("irRubros")) {
							bandera = true;
						}

					}
				}
			}
			// ////////////////
			sesion.removeAttribute("manejadorMenuFormularios");
			return "irSubirArchivo";
		} else {

			if (validarGastosUno()) {
				if ((proyectoActual.getEstadoProyecto().getId()).equals("I")) {
					proyectoActual.setFase(new Integer((proyectoActual
							.getFase()).intValue() + 1));
				}
				if (proyectoActual.getEstadoProyecto().getId().equals(
						EstadoProyecto.INGRESANDO)) {
					// wam²
					// 20090122
					List listaModalidad = new ArrayList();
					// Cambio para el estado de los proyectos de jornada docente
					// - A
					listaModalidad = servicioGeneral
							.obtenerListaObjetos("Modalidad where id ='"
									+ proyectoActual.getModalidad().getId()
									+ "'");
					Modalidad mod = (Modalidad) listaModalidad.get(0);

					if (mod.getTipo() != null
							&& mod.getTipo().getId().equals("J")) {
						proyectoActual.cambiarEstadoPersona(EstadoProyecto.ACTIVO,cargarPersonaActual());

					} else {
						proyectoActual.cambiarEstadoPersona(EstadoProyecto.PROPUESTO,cargarPersonaActual());
					}
				}
				// proyectoActual.setFase(new Integer(0));
				for (int i = 0; i < listaFinanciaciones.size(); i++) {
					servicioGeneral.guardarObjeto(listaFinanciaciones.get(i));
				}
				servicioProyecto.ingresarProyecto(proyectoActual);
				sesion.setAttribute("proyecto", proyectoActual);
				sesion.removeAttribute("manejadorMenuFormularios");
				sesion.removeAttribute("manejadorRubros");
				return "irSubirArchivo";
			}
		}
		return "";
	}

	// FUNCIONES ESPECIFICAS DE LA CLASE
	private void cargarFuentesProyecto() {
		List listaAux = new ArrayList();
		listaAux.addAll(proyectoActual.getFinanciaciones());
		fuentesFinancierasItem = new SelectItem[listaAux.size()];
		for (int i = 0; i < listaAux.size(); i++) {
			Financiacion f = (Financiacion) listaAux.get(i);
			listaFinanciaciones.add(servicioProyecto
					.obtenerFinanciacionGastos(f));
			listaFinanciacionVista.add(new FinanciacionVista(
					(Financiacion) listaFinanciaciones.get(i)));
			fuentesFinancierasItem[i] = new SelectItem(f.getId(), f.getFuente()
					.getDescripcion());
			f = null;
		}
		gastoActual.setFinanciacion(new Financiacion());
		if (listaAux != null && listaAux.size() > 0) {
			gastoActual.getFinanciacion().setId(
					((Financiacion) listaAux.get(0)).getId());
			totalFinanciacion = ((Financiacion) listaAux.get(0)).getValor();
		}
		listaAux = null;
	}

	private void cargarTiposRubro() {
		// TODO ESTE CARGUE HAY QUE REVISARLO YA QUE NO TIENE EN CEUNTA LOS
		// RUBROS DE CUANDO
		// LA MODALIDAD NO ES UNA CONVOCATORIA
		try {
			Object o = proyectoActual.getModalidad();
			System.out.println(o.getClass().getName());
			Modalidad mod = proyectoActual.getModalidad();

			// if(mod instanceof Convocatoria){
			// Convocatoria conv = (Convocatoria)mod;
			listaRubrosFinanciables = servicioModalidad
					.obtenerRubrosFinanciables(mod);
			// }
			// else
			// {
			// List aux=servicioGeneral.obtenerListaObjetos("TipoRubro");
			// listaTiposRubros = new ArrayList();
			// tiposRubroItem=new SelectItem[aux.size()];
			// for (int i = 0; i < aux.size(); i++)
			// {
			// TipoRubro tr = (TipoRubro) aux.get(i);
			// listaTiposRubros.add(tr);
			// tiposRubroItem[i] = new SelectItem(tr.getId(), tr.getNombre());
			// tr=null;
			// }
			// gastoActual.setTipoRubro(new TipoRubro());
			// gastoActual.getTipoRubro().setId(((TipoRubro)listaTiposRubros.get(0)).getId());
			// }

			// if(!(mod instanceof Registro))
			// {
			//
			listaTiposRubros = new ArrayList();
			if (listaRubrosFinanciables != null) {
				tiposRubroItem = new SelectItem[listaRubrosFinanciables.size()];
			} else {
				tiposRubroItem = new SelectItem[0];
			}
			for (int i = 0; listaRubrosFinanciables != null
					&& i < listaRubrosFinanciables.size(); i++) {
				RubroFinanciable rf = (RubroFinanciable) listaRubrosFinanciables
						.get(i);
				TipoRubro tr = rf.getTipoRubro();
				listaTiposRubros.add(tr);
				String nombre = tr.getNombre();
				if (nombre != null && nombre.length() > 120) {
					nombre = nombre.substring(0, 120) + "...";
				}
				tiposRubroItem[i] = new SelectItem(tr.getId(), nombre);
				tr = null;
			}
			gastoActual.setTipoRubro(new TipoRubro());
			if (listaTiposRubros != null && listaTiposRubros.size() > 0) { // If
				// creado
				// por
				// giovanni
				gastoActual.getTipoRubro().setId(
						((TipoRubro) listaTiposRubros.get(0)).getId());
			}
			// }
		} catch (Exception e) {
			System.out
					.println("ManejadorInfoFinanciera:cargarTiposRubro:Error Cargando Los Tipos de Rubro");
			e.printStackTrace();
		}
	}

	private TipoRubro buscarTipoRubro(Long id) {
		// BUSCA EL TIPO DE RUBRO POR EL ID
		TipoRubro tr = new TipoRubro();
		int i = 0;
		while (i < listaTiposRubros.size()) {
			tr = (TipoRubro) listaTiposRubros.get(i);
			if (id.longValue() == (tr.getId()).longValue())
				break;
			i = i + 1;
		}
		return tr;
	}

	private Financiacion buscarFinanciacion(Long id) {
		// BUSCA UNA FINANCIACION POR EL ID
		Financiacion f = null;
		int i = 0;
		while (i < listaFinanciaciones.size()) {
			f = (Financiacion) listaFinanciaciones.get(i);
			if (id.longValue() == (f.getId()).longValue())
				break;
			i = i + 1;
		}
		return f;
	}

	public void agregarGastoEspecie_ES() {

		Financiacion fi = new Financiacion();

		for (int i = 0; i < listaFinanciaciones.size(); i++) {
			Financiacion fin = (Financiacion) listaFinanciaciones.get(i);
			if (fin.getFuente().getId().equals("453")) {
				fi = (Financiacion) listaFinanciaciones.get(i);
			}
		}

		Financiacion f = buscarFinanciacion(fi.getId());

		listaInvestigadoresVista = proyectoActual
				.getObtenerListaInvestigadoresVista();

		for (int j = 0; j < listaInvestigadoresVista.size(); j++) {
			InvestigadorProyectoVista ipv = (InvestigadorProyectoVista) listaInvestigadoresVista
					.get(j);
			InvestigadorProyecto ip = ipv.getIp();
			if (ip.getTipo().getId().equals("P")
					|| ip.getTipo().getId().equals("CES")
					|| ip.getTipo().getId().equals("PRES")) {
				InvestigadorInterno ii = servicioPersona
						.obtenerInvestigadorInternoCompleto(ip
								.getInvestigador().getId());
				double horasDedicacion = ip.getDedicacionHorasSemana();
				Long valorHora = ii.getValorHora();
				// Long horasTotal = hora * horasDedicacion;
				int duracionProyecto = proyectoActual.getDuracion();
				Long valorTotal = (long) (horasDedicacion * valorHora * 4
						* duracionProyecto);
				System.out.println("Tiempo del profesor: " + ii.getNombre1()
						+ " " + ii.getNombre2() + " " + ii.getApellido1() + " "
						+ ii.getApellido2());
				Gasto gastoInv = new Gasto();
				gastoInv.setValor(valorTotal);
				gastoInv.setCantidad(1);
				gastoInv.setVigencia(1);
				gastoInv.setDescripcion("Tiempo del profesor: "
						+ ii.getNombre1() + " " + ii.getNombre2() + " "
						+ ii.getApellido1() + " " + ii.getApellido2());
				gastoInv.setFinanciacion(new Financiacion());
				gastoInv.getFinanciacion().setId(fi.getId());
				// gastoInv.setTipoRubro(new TipoRubro());
				// gastoInv.getTipoRubro().setId(49L);
				gastoInv.setTipoRubro(buscarTipoRubro(49L));
				f.adicionarGasto(gastoInv);
			}

		}
	}

	public void agregarGastoEspecie_ES_2() {

		listaInvestigadoresVista = proyectoActual
				.getObtenerListaInvestigadoresVista();

		Gasto gastoInv = new Gasto();
		Financiacion fi = new Financiacion();

		for (int i = 0; i < listaFinanciaciones.size(); i++) {
			Financiacion fin = (Financiacion) listaFinanciaciones.get(i);
			if (fin.getFuente().getId().equals("7")) {
				fi = (Financiacion) listaFinanciaciones.get(i);
			}
		}

		gastoInv.setTipoRubro(buscarTipoRubro(49L));
		Financiacion f = buscarFinanciacion(fi.getId());

		for (int j = 0; j < listaInvestigadoresVista.size(); j++) {
			InvestigadorProyectoVista ipv = (InvestigadorProyectoVista) listaInvestigadoresVista
					.get(j);
			InvestigadorProyecto ip = ipv.getIp();

			if (ip.getTipo().getId().equals("P")
					|| ip.getTipo().getId().equals("CES")
					|| ip.getTipo().getId().equals("PRES")) {
				double horasDedicacion = ip.getDedicacionHorasSemana();
				InvestigadorInterno ii = servicioPersona
						.obtenerInvestigadorInternoCompleto(ip
								.getInvestigador().getId());
				System.out.println("Tiempo del profesor: " + ii.getNombre1()
						+ " " + ii.getNombre2() + " " + ii.getApellido1() + " "
						+ ii.getApellido2());
				Long hora = ii.getValorHora();
				Long horasTotal = (long) (hora * horasDedicacion);
				int duracionProyecto = proyectoActual.getDuracion();
				Long valorTotal = horasTotal * duracionProyecto;
				try {
					f.adicionarGasto(gastoInv);
					gastoInv = new Gasto();
					gastoInv.setValor(valorTotal);
					gastoInv.setCantidad(1);
					gastoInv.setVigencia(1);
					gastoInv.setDescripcion("Tiempo del profesor: "
							+ ii.getNombre1() + " " + ii.getNombre2() + " "
							+ ii.getApellido1() + " " + ii.getApellido2());
					gastoInv.setFinanciacion(new Financiacion());
					gastoInv.getFinanciacion().setId(fi.getId());
					gastoInv.setTipoRubro(new TipoRubro());
					gastoInv.getTipoRubro().setId(49L);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	// FUNCIONES DE INSERCION Y BORRADO EN LAS LISTAS ASOCIADAS
	public void agregarGasto() {
		// ADICIONAR A LA FINANCIACION EL GASTO ACTUAL
		this.mensajeRubros = "";
		boolean bandera = true;

		if (gastoActual.getValor() == 0 || gastoActual.getValor() < 0) {
			// this.mensajeRubros="El atributo 'Descripción' se encuentra vacio";
			bandera = false;
			FacesContext
					.getCurrentInstance()
					.addMessage(
							"msgs",
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"El atributo 'Cantidad' debe ser mayor a cero",
									""));
		}

		if (gastoActual.getDescripcion() == null
				|| gastoActual.getDescripcion().length() <= 0) {
			// this.mensajeRubros="El atributo 'Descripción' se encuentra vacio";
			bandera = false;
			FacesContext
					.getCurrentInstance()
					.addMessage(
							"msgs",
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"El atributo 'Descripción' se encuentra vacio",
									""));
		}

		if (bandera) {

			try {
				gastoActual.setTipoRubro(buscarTipoRubro(gastoActual
						.getTipoRubro().getId()));
				Financiacion f = buscarFinanciacion(gastoActual
						.getFinanciacion().getId());
				if (!(proyectoActual.getModalidad() instanceof Registro)) {
					if (validarPorcentajeGasto(gastoActual)) {
						f.adicionarGasto(gastoActual);
						gastoActual = new Gasto();
						gastoActual.setValor(new Long(0));
						// Se asigna por defecto 1 a la cantidad y la vigencia
						gastoActual.setCantidad(1);
						gastoActual.setVigencia(1);
						gastoActual.setDescripcion("");
						gastoActual.setFinanciacion(new Financiacion());
						gastoActual.getFinanciacion().setId(
								((Financiacion) listaFinanciaciones.get(0))
										.getId());
						gastoActual.setTipoRubro(new TipoRubro());
						gastoActual.getTipoRubro().setId(
								((TipoRubro) listaTiposRubros.get(0)).getId());
					}
				} else {
					f.adicionarGasto(gastoActual);
					gastoActual = new Gasto();
					gastoActual.setValor(new Long(0));
					// Se asigna por defecto 1 a la cantidad y la vigencia
					gastoActual.setCantidad(1);
					gastoActual.setVigencia(1);
					gastoActual.setDescripcion("");
					gastoActual.setFinanciacion(new Financiacion());
					gastoActual.getFinanciacion()
							.setId(
									((Financiacion) listaFinanciaciones.get(0))
											.getId());
					gastoActual.setTipoRubro(new TipoRubro());
					gastoActual.getTipoRubro().setId(
							((TipoRubro) listaTiposRubros.get(0)).getId());
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void eliminarGasto() {

		// GastoVista g=(GastoVista)(tablaGastos.getRowData());
		GastoVista g = gastoSeleccionado;
		Financiacion f = g.getGasto().getFinanciacion();
		// REMOVER GASTO DE LA VISTA Y DEL MODELO
		(f.getGastos()).remove(g.getGasto());
	}

	private FinanciacionVista buscarFinanciacionVista(Financiacion f) {
		int i = 0;
		FinanciacionVista fv = null;
		while (i < listaFinanciacionVista.size()) {
			Financiacion faux = ((FinanciacionVista) listaFinanciacionVista
					.get(i)).getFinanciacion();
			if (f.getId().longValue() == faux.getId().longValue()) {
				fv = (FinanciacionVista) listaFinanciacionVista.get(i);
				break;
			}
			i++;
		}
		return fv;
	}

	private RubroFinanciable buscarRubroFinanciable(TipoRubro tr) {
		for (int i = 0; i < listaRubrosFinanciables.size(); i++) {
			RubroFinanciable rf = (RubroFinanciable) listaRubrosFinanciables
					.get(i);
			TipoRubro traux = rf.getTipoRubro();
			if (tr.getId().longValue() == traux.getId().longValue()) {
				return rf;
			}
		}
		return null;
	}

	private boolean validarGastosUno() {
		return true;
	}

	private boolean validarGastos() {
		// SE VALIDA INICIALMENTE SI CADA UNA DE LAS FUENTES TIENE GASTOS
		// ASOCIADOS
		List listaFuenteFinanciacionXModalidad = servicioModalidad
				.listaModFuenteFinXModalidad(proyectoActual.getModalidad()
						.getId());
		if (proyectoActual.getModalidad() instanceof Convocatoria) {
			Convocatoria co = (Convocatoria) proyectoActual.getModalidad();
			double maximoC = new Double(co.getMontoApoyoGanadores())
					.doubleValue();

			String valida = servicioModalidad.validarFinanciaciones(
					listaFinanciaciones, listaRubrosFinanciables,
					listaFuenteFinanciacionXModalidad, maximoC);
			if (valida != null) {
				mensajeRubros = valida;
				return false;
			}
		}

		int i = 0;
		while (i < listaFinanciaciones.size()) {
			Financiacion f = (Financiacion) listaFinanciaciones.get(i);

			if (f.getGastos().size() == 0) {
				// mensajeRubros="No se encuentran gastos asociados a "+(f.getFuente()).getDescripcion();
				FacesContext.getCurrentInstance()
						.addMessage(
								"msgs",
								new FacesMessage(FacesMessage.SEVERITY_ERROR,
										"No se encuentran gastos asociados a "
												+ (f.getFuente())
														.getDescripcion(), ""));
				return false;
			} else {
				// SI HAY GASTOS ASOCIADOS QUE NO EXCEDAN LOS PORCENTAJES
				// VALIDOS DE LA CONVOCATORIA
				// SE VALIDA QUE EL TOTAL DE LA FINANCIACION SEA IGUAL AL TOTAL
				// DE
				// LA SUMA DE LOS GASTOS ASOCIADOS
				long sumaGastos = 0;
				long suma_STR_EEA = 0;
				Iterator it = (f.getGastos()).iterator();
				while (it.hasNext()) {
					Gasto g = (Gasto) it.next();
					sumaGastos += ((g.getValor()).longValue() * g.getCantidad());
					if (g.getTipoRubro().getId().longValue() == 3
							|| g.getTipoRubro().getId().longValue() == 14) {
						suma_STR_EEA += ((g.getValor()).longValue() * g
								.getCantidad());
					}
				}
				// LO PRIMERO QUE SE VALIDA ANTES DE CONTINUAR CON LA SUMA
				// TOTAL, ES QUE EL VALOR DE SERVICIOS TECNICOS
				// Y ESTUDIANTES AUXILIARES NO SOBREPASE EL 70% DE LA
				// FINANCIACION
				System.out.println("se valida "
						+ proyectoActual.getModalidad().getId());
				// if(servicioModalidad.validarModalidadFuenteFinanciacion(proyectoActual.getModalidad().getId(),f.getFuente().getId()))
				// {
				// System.out.println("si");
				// if(suma_STR_EEA>f.getValor().longValue()*0.7){
				// mensajeRubros="La suma de 'servicios técnicos' y 'estímulos a estudiantes' sobrepasa el 70% del valor de la financiación";
				// return false;
				// }
				// }
				if (sumaGastos < f.getValor().longValue()
						|| sumaGastos > f.getValor().longValue()) {
					// mensajeRubros="El total de los gastos asociados a "+(f.getFuente()).getDescripcion()+" difiere del valor aportado por dicha fuente";
					// mensajeRubros+=" La suma de sus gastos es: "+sumaGastos+" y el monto de la convocatoria es: "+f.getValor();
					FacesContext
							.getCurrentInstance()
							.addMessage(
									"msgs",
									new FacesMessage(
											FacesMessage.SEVERITY_ERROR,
											"El total de los gastos asociados a "
													+ (f.getFuente())
															.getDescripcion()
													+ " difiere del valor aportado por dicha fuente"
													+ "\n"
													+ " La suma de sus gastos es: "
													+ sumaGastos
													+ " y el monto de la fuente es: "
													+ f.getValor(), ""));
					return false;
				}
			}
			i++;
		}
		mensajeRubros = "";
		return true;
	}

	private boolean validarPorcentajeGasto(Gasto gastoActual) {
		System.out.println("valida" + gastoActual.getValor()
				+ " para la fuente " + gastoActual.getTipoRubro().getNombre()
				+ " con %");
		Financiacion f = buscarFinanciacion(gastoActual.getFinanciacion()
				.getId());
		long total_financiacion = f.getValor().longValue();
		RubroFinanciable rf = buscarRubroFinanciable(gastoActual.getTipoRubro());
		System.out.println(rf.getPorcentajeMaximo());
		double maximoFinanciable = (rf.getPorcentajeMaximo() / 100)
				* total_financiacion;

		System.out.println("maximo financiable " + maximoFinanciable);
		if (servicioModalidad.validarModalidadFuenteFinanciacion(proyectoActual
				.getModalidad().getId(), f.getFuente().getId())) {
			// wam²
			long sumaGatos = 0;
			sumaGatos = gastoActual.getValor().longValue();
			for (int i = 0; i < f.getListaGastos().size(); i++) {
				Gasto g = (Gasto) f.getListaGastos().get(i);
				if (g.getTipoRubro().getId().compareTo(
						gastoActual.getTipoRubro().getId()) == 0) {
					sumaGatos = sumaGatos + g.getValor().longValue();
				}
			}
			if (sumaGatos > maximoFinanciable) {
				// mensajeRubros="El valor del rubro excede el porcentaje máximo permitido por la convocatoria"+"("+rf.getPorcentajeMaximo()+")";
				FacesContext
						.getCurrentInstance()
						.addMessage(
								"msgs",
								new FacesMessage(
										FacesMessage.SEVERITY_ERROR,
										"El valor del rubro excede el porcentaje máximo permitido por la convocatoria"
												+ "("
												+ rf.getPorcentajeMaximo()
												+ ") ó excede el total de la fuente de financiación seleccionada",
										""));
				return false;
			}
		}
		mensajeRubros = "";
		return true;
	}

	public void cambiaTipoRubro(ValueChangeEvent event) {
		Long idTipoRubro = (Long) event.getNewValue();
		TipoRubro tr = (TipoRubro) servicioGeneral.obtenerObjeto(
				new TipoRubro(), idTipoRubro);
		getGastoActual().setTipoRubro(tr);
	}

	public void cambiaFinanciacion(ValueChangeEvent event) {
		Long idFinanciacion = (Long) event.getNewValue();
		List listaAux = new ArrayList();
		listaAux.addAll(proyectoActual.getFinanciaciones());
		if (listaAux != null && listaAux.size() > 0) {
			Iterator ite = listaAux.iterator();
			while (ite.hasNext()) {
				Financiacion financiacion = (Financiacion) ite.next();
				if (idFinanciacion.longValue() == financiacion.getId()
						.longValue()) {
					this.totalFinanciacion = financiacion.getValor();
				}
			}
		}
		listaAux = null;
	}

	// METODOS SET Y GET
	public List getListaTiposRubros() {
		return listaTiposRubros;
	}

	public void setListaTiposRubros(List listaTiposRubros) {
		this.listaTiposRubros = listaTiposRubros;
	}

	public String getMensajeRubros() {
		return mensajeRubros;
	}

	public void setMensajeRubros(String mensajeRubros) {
		this.mensajeRubros = mensajeRubros;
	}

	public TipoRubro getTipoRubroActual() {
		return tipoRubroActual;
	}

	public void setTipoRubroActual(TipoRubro tipoRubroActual) {
		this.tipoRubroActual = tipoRubroActual;
	}

	public SelectItem[] getTiposRubroItem() {
		return tiposRubroItem;
	}

	public void setTiposRubroItem(SelectItem[] tiposRubroItem) {
		this.tiposRubroItem = tiposRubroItem;
	}

	public long getValorTotalInvestigacion() {
		return valorTotalInvestigacion;
	}

	public void setValorTotalInvestigacion(long valorTotalInvestigacion) {
		this.valorTotalInvestigacion = valorTotalInvestigacion;
	}

	public List getListaGastosProyecto() {
		return listaGastosProyecto;
	}

	public void setListaGastosProyecto(List listaGastosProyecto) {
		this.listaGastosProyecto = listaGastosProyecto;
	}

	public Gasto getGastoActual() {
		return gastoActual;
	}

	public void setGastoActual(Gasto gastoActual) {
		this.gastoActual = gastoActual;
	}

	public SelectItem[] getFuentesFinancierasItem() {
		return fuentesFinancierasItem;
	}

	public void setFuentesFinancierasItem(SelectItem[] fuentesFinancierasItem) {
		this.fuentesFinancierasItem = fuentesFinancierasItem;
	}

	public List getListaFinanciaciones() {
		return listaFinanciaciones;
	}

	public void setListaFinanciaciones(List listaFinanciaciones) {
		this.listaFinanciaciones = listaFinanciaciones;
	}

	public DataTable getTablaFinanciacion() {
		return tablaFinanciacion;
	}

	public void setTablaFinanciacion(DataTable tablaFinanciacion) {
		this.tablaFinanciacion = tablaFinanciacion;
	}

	public DataTable getTablaGastos() {
		return tablaGastos;
	}

	public void setTablaGastos(DataTable tablaGastos) {
		this.tablaGastos = tablaGastos;
	}

	public SelectItem[] getVigenciaItem() {
		return vigenciaItem;
	}

	public void setVigenciaItem(SelectItem[] vigenciaItem) {
		this.vigenciaItem = vigenciaItem;
	}

	public List getListaFinanciacionVista() {
		return listaFinanciacionVista;
	}

	public void setListaFinanciacionVista(List listaFinanciacionVista) {
		this.listaFinanciacionVista = listaFinanciacionVista;
	}

	public String[] getVigencias() {
		return vigencias;
	}

	public void setVigencias(String[] vigencias) {
		this.vigencias = vigencias;
	}

	public Long getTotalFinanciacion() {
		return totalFinanciacion;
	}

	public void setTotalFinanciacion(Long totalFinanciacion) {
		this.totalFinanciacion = totalFinanciacion;
	}

	public boolean isEsProgramaNacional() {
		return esProgramaNacional;
	}

	public void setEsProgramaNacional(boolean esProgramaNacional) {
		this.esProgramaNacional = esProgramaNacional;
	}

	public String getTitulo1() {
		return titulo1;
	}

	public void setTitulo1(String titulo1) {
		this.titulo1 = titulo1;
	}

	public String getTitulo2() {
		return titulo2;
	}

	public void setTitulo2(String titulo2) {
		this.titulo2 = titulo2;
	}

	public GastoVista getGastoSeleccionado() {
		return gastoSeleccionado;
	}

	public void setGastoSeleccionado(GastoVista gastoSeleccionado) {
		this.gastoSeleccionado = gastoSeleccionado;
	}

	public List getListaInvestigadoresVista() {
		return listaInvestigadoresVista;
	}

	public void setListaInvestigadoresVista(List listaInvestigadoresVista) {
		this.listaInvestigadoresVista = listaInvestigadoresVista;
	}

	public Long getValorJornadaDocente() {
		return valorJornadaDocente;
	}

	public void setValorJornadaDocente(Long valorJornadaDocente) {
		this.valorJornadaDocente = valorJornadaDocente;
	}

	public boolean isEsExtensionSolidaria() {
		return esExtensionSolidaria;
	}

	public void setEsExtensionSolidaria(boolean esExtensionSolidaria) {
		this.esExtensionSolidaria = esExtensionSolidaria;
	}

}
