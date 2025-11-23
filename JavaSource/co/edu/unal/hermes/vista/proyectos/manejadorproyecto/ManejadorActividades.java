package co.edu.unal.hermes.vista.proyectos.manejadorproyecto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.menuitem.MenuItem;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.Actividad;
import co.edu.unal.hermes.modelo.ActividadPersona;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.Formulario;
import co.edu.unal.hermes.modelo.HistoricoFormularioProyecto;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.MetaProyecto;
import co.edu.unal.hermes.modelo.ObjetivoEspecifico;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.RestriccionConvocatoria;
import co.edu.unal.hermes.modelo.ResultadoProyecto;
import co.edu.unal.hermes.modelo.TipoModalidad;
import co.edu.unal.hermes.vista.proyectos.ManejadorMenuFormularios;

public class ManejadorActividades extends ManejadorProyecto {

	private List listaActividades;
	private DataTable tablaActividades;
	private String mensajeErrorActividad = "";

	public List listaInvestigadoresDelProyecto;
	public SelectItem[] listainvestigadoresitem;
	public SelectItem[] listaActividadesItem;

	public List listaActividadesParaPersona;

	public DataTable tablaActividadesParaPersona;
	private String personaNueva;
	public String actividadPersonaNueva;
	public Integer semanaInicial;
	public Integer duracionSemanas;
	public Map serializables = new HashMap();
	public String inversion;
	private boolean esConvIni = false;
	private boolean esConvSem = false;
	private boolean esConvRepotenciacionLab2018 = false;

	private String titulo1;
	private String titulo2;

	private List listaObjetivosProyecto;
	public SelectItem[] listaObjetivosItem;
	private DataTable tablaActividades2;
	private DataTable tablaActividades3;
	private Boolean esProyectoLaboratorios;
	private boolean mostrarActividadesObjetivosMetas = false;
	private Convocatoria convocatoriaActual;

	private String actividad;
	private UIComponent actividadProyecto;
	private UIComponent mesInicialUI;
	private UIComponent duracionUI;
	protected String metaObjetivoSelectValue;
	private List<SelectItem> metasItem;
	protected String resultadoSelectValue;
	private List<SelectItem> resultadosItem;
	private String mesInicial;
	private String duracion;

	private Actividad actividadTabla;

	private String responsable;

	public ManejadorActividades() {
		super();
		idManejador = ACTIVIDADES;

		titulo1 = "Proyecto:";
		titulo2 = "Búsqueda de Integrantes del Proyecto";

		listaActividades = new ArrayList();

		proyectoActual = servicioProyecto.obtenerProyecto(proyectoActual.getId(),
				ProyectoDAOHibernate.INVESTIGADORESYACTIVIDADES);
		cargarValoresIniciales();
		Set investigadores = new HashSet();

		for (Iterator itInvestigadorProyecto = proyectoActual.getInvestigadoresProyecto()
				.iterator(); itInvestigadorProyecto.hasNext();) {
			InvestigadorProyecto ip = (InvestigadorProyecto) itInvestigadorProyecto.next();
			investigadores.add(ip.getInvestigador());
		}
		listaInvestigadoresDelProyecto = new Vector(investigadores);
		listainvestigadoresitem = new SelectItem[investigadores.size()];
		int i = 0;
		for (Iterator itInvestigadores = investigadores.iterator(); itInvestigadores.hasNext();) {
			Investigador investigado = (Investigador) itInvestigadores.next();
			listainvestigadoresitem[i] = new SelectItem(
					investigado.getId().getDocumento() + "&" + investigado.getId().getTipoDocumento(),
					investigado.getNombreCompletoMinusculas());
			i++;
		}

		listaActividadesParaPersona = new Vector();
		for (Iterator itActividades = listaActividades.iterator(); itActividades.hasNext();) {
			listaActividadesParaPersona.add(new VistaActividad((Actividad) itActividades.next()));
		}
		listaActividadesItem = new SelectItem[listaActividades.size()];
		i = 0;
		for (Iterator itActividades = listaActividades.iterator(); itActividades.hasNext();) {
			Actividad a = (Actividad) itActividades.next();

			listaActividadesItem[i] = new SelectItem(a.getId().toString(), a.getDescripcion());
			i++;
		}

		listaObjetivosItem = new SelectItem[listaObjetivosProyecto.size()];
		i = 0;
		for (Iterator itObje = listaObjetivosProyecto.iterator(); itObje.hasNext();) {
			ObjetivoEspecifico a = (ObjetivoEspecifico) itObje.next();

			listaObjetivosItem[i] = new SelectItem(a.getId().toString(), a.getNombre());
			i++;
		}

		if ((proyectoActual.getModalidad().getTipo().getId().compareTo("CFM") == 0)) {
			if (proyectoActual.getModalidad() instanceof Convocatoria) {
				RestriccionConvocatoria r = ((Convocatoria) proyectoActual.getModalidad()).getRestriccion();
				if (r != null) {
					if (r.getId().equals("CONV_INI")) {
						esConvIni = true;

					} else {
						Convocatoria con = (Convocatoria) proyectoActual.getModalidad();
						if (con != null && con.getPadre() != null && con.getPadre().getId() == 226) {
							esConvSem = true;
						}
					}
				}
			} else {
			}
		}

		if (proyectoActual.getModalidad().getTipo().getId().compareTo("PN") == 0) {
			titulo1 = "Programa:";
			titulo2 = "Integrantes del programa";
		} else {
			titulo1 = "Proyecto:";
			titulo2 = "Integrantes del proyecto de investigación";
		}

		esProyectoLaboratorios = proyectoActual.getModalidad().getTipo().getId()
				.equals(TipoModalidad.REGISTRO_PROYECTOS_LABORATORIOS);

		List listConvocatorias = servicioGeneral
				.obtenerObjetos("select e from Convocatoria e where e.id = " + proyectoActual.getModalidad().getId());
		if (listConvocatorias.size() > 0 && listConvocatorias != null) {
			convocatoriaActual = (Convocatoria) listConvocatorias.get(0);
			if (convocatoriaActual.getActividadesObjetivosMetas() != null) {
				if (convocatoriaActual.getActividadesObjetivosMetas().equals(1L)) {
					mostrarActividadesObjetivosMetas = true;
				} else {
					mostrarActividadesObjetivosMetas = false;
				}
			} else {
				mostrarActividadesObjetivosMetas = false;
			}

			if (convocatoriaActual.getRestriccion() != null) {
				if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_REP_EQU_LAB)) {
					esConvRepotenciacionLab2018 = true;
				}
			}

		}

		actualizarListaMetas();
		actualizarListaResultados();

	}

	public void actualizarListaMetas() {
		metasItem = servicioProyecto.crearSelectItemMetas(proyectoActual.getListaMetas());
	}

	public void actualizarListaResultados() {
		resultadosItem = servicioProyecto.crearSelectItemResultados(proyectoActual.getListaResultados());
	}

	public void insertarActividadMetaResultado() {
		System.out.println("         -------------------------         insertarActividad() --------------------");

		if (esCadenaVacia(actividad) || metaObjetivoSelectValue.equals("") || resultadoSelectValue.equals("")) {
			mensajeError(actividadProyecto,
					"Por favor ingrese la actividad (Máx. 500 caracteres) y seleccione una meta y un resultado.");
		} else if (esCadenaVacia(mesInicial)) {
			mensajeError(mesInicialUI, "Por favor ingrese el mes inicial de la actividad.");
		} else if (esCadenaVacia(duracion)) {
			mensajeError(duracionUI, "Por favor ingrese la duración de la actividad.");
		} else {
			actividad = controlTamanoCadena(actividad, 500);

			Actividad a = new Actividad();
			a.setDescripcion(actividad);
			a.setMesInicial(Integer.parseInt(mesInicial));
			a.setDuracionMeses(Integer.parseInt(duracion));
			MetaProyecto metaObjeto = buscarMeta(metaObjetivoSelectValue, proyectoActual.getListaMetas());
			ResultadoProyecto resultadoObjeto = buscarResultado(resultadoSelectValue,
					proyectoActual.getListaResultados());
			a.setMetaObjeto(metaObjeto);
			a.setResultado(resultadoObjeto);
			a.setNumeroOrden(obtenerMaxOrderActividad(proyectoActual.getListaActividadesAsociadasAMeta(metaObjeto)));
			listaActividades.add(a);
			proyectoActual.adicionarActividad(a);
			String tdo = responsable.split("&")[1];
			String documento = responsable.split("&")[0];
			IdPersona idp = new IdPersona(documento, tdo);
			ActividadPersona ap = new ActividadPersona();
			ap.setActividad(a);
			ap.setDuracionSemanas(0);
			ap.setSemanaInicial(0);
			ap.setInversion("0");
			if (a.getActividadesPersona() == null) {
				a.setActividadesPersona(new HashSet());
				ap.setInvestigador(servicioPersona.obtenerInvestigador(idp));
				a.getActividadesPersona().add(ap);
			} else {
				((ActividadPersona) a.getActividadesPersona().toArray()[0])
						.setInvestigador(servicioPersona.obtenerInvestigador(idp));
			}
			a = null;
			mensajeErrorActividad = "";

			this.actividad = "";
		}
	}

	public Long obtenerMaxOrderActividad(List<Actividad> actividadesProyecto) {
		Long max = 0L;
		if (actividadesProyecto != null) {
			Iterator<Actividad> i = actividadesProyecto.iterator();
			while (i.hasNext()) {
				Actividad actividad = i.next();
				if (actividad.getNumeroOrden() != null && actividad.getNumeroOrden() > max) {
					max = actividad.getNumeroOrden();
				}
			}
		}
		return max + 1;
	}

	public void eliminarActividadMetaResultado() {
		// ((Actividad)
		// listaActividades.get(tablaActividades3.getRowIndex())).setBorrable(true);
		// listaActividades.remove(actividadTabla);
		listaActividades = new ArrayList();
		proyectoActual.recalcularNumeroOrden(actividadTabla.getMetaObjeto(), actividadTabla);
		proyectoActual.borrarActividad(actividadTabla);
		cargarValoresIniciales();
	}

	// public void recalcularNumeroOrden(MetaProyecto meta)
	// {
	//
	// }

	// DEFINICION DE FUNCIONES BASICAS
	protected void cargarValoresIniciales() {
		Set actividades = proyectoActual.getActividades();
		Iterator it = actividades.iterator();
		while (it.hasNext()) {
			Actividad a = (Actividad) it.next();
			a.pasarSemanasMeses();
			IdPersona idp;
			if (a.getActividadesPersona() == null || a.getActividadesPersona().isEmpty()) {
				idp = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId()).getId();
			} else {
				idp = ((ActividadPersona) a.getActividadesPersona().toArray()[0]).getInvestigador().getId();
			}
			a.setIdInv(idp.getDocumento() + "&" + idp.getTipoDocumento());
			listaActividades.add(a);
		}

		listaObjetivosProyecto = (List<ObjetivoEspecifico>) servicioProyecto
				.obtenerObjetivosEspeficicos(proyectoActual);

	}

	public String atras() {
		///////// MODIFICADO GIOVANNI
		ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
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

					if (lis[i].getOutcome().equals("irActividades")) {
						bandera = true;
					}

				}
			}
		}
		//////////////////
		return "irActividades";
	}

	public String salir() {
		sesion.removeAttribute("proyecto");
		borrarManejadoresInsercionProyecto();
		return "misProyectos";
	}

	public String salirGuardar() {
		if (validarActividades()) {

			///// MODIFICADO GIOVANNI
			String link = "";
			ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
			boolean bandera = false;
			int pos = 0;
			if (man != null && man.getItemProyecto() != null) {
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

						if (lis[i].getOutcome().equals("irActividades")) {
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
				proyectoActual.setFase(new Integer(proyectoActual.getFase().intValue() + 1));
			}

			if (proyectoActual.getId() != null) {
				// Ing. Wilver Alexander Martínez Martínez -wam²
				// Cambio - Registro de cambios
				Persona personaAux = new Persona();
				personaAux = (Persona) sesion.getAttribute("persona");

				Formulario formulario = new Formulario();
				List listaFormulario = new ArrayList();

				listaFormulario = servicioGeneral.obtenerListaObjetos("Formulario where id ='120'");
				formulario = (Formulario) listaFormulario.get(0);

				HistoricoFormularioProyecto historicoFormularioProyecto = new HistoricoFormularioProyecto();
				historicoFormularioProyecto.setDocPersona(personaAux.getId().getDocumento());
				historicoFormularioProyecto.setTipoDocumentoPersona(personaAux.getId().getTipoDocumento());
				historicoFormularioProyecto.setFormulario(formulario);
				historicoFormularioProyecto.setProyecto(proyectoActual);
				historicoFormularioProyecto.setFechaCambio(new Date());
				servicioGeneral.guardarObjeto(historicoFormularioProyecto);
			}

			servicioProyecto.ingresarProyecto(proyectoActual);

			if (validarSiEsEstudianteLiderProyecto(proyectoActual.getId()) && proyectoActual.getId() != null) {
				//Se comenta el envio de correo por que en BD no esta creada la plantilla de correo 326 y se desconoce el motivo, 
				//lo cual genera excepcion al guardar con rol Estudiante Lider
//				enviarCorreoEdicionInfoPryEstLider(proyectoActual.getId());
			}

			sesion.removeAttribute("proyecto");
			sesion.removeAttribute("manejadorMenuFormularios");
			borrarManejadoresInsercionProyecto();
			return "misProyectos";
		}
		return "";
	}

	public String siguiente() {
		if (validarActividades()) {

			///// MODIFICADO GIOVANNI
			String link = "";
			ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
			boolean bandera = false;
			int pos = 0;

			if (man != null && man.getItemProyecto() != null) {
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

						if (lis[i].getOutcome().equals("irActividades")) {
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
				proyectoActual.setFase(new Integer(proyectoActual.getFase().intValue() + 1));
			}

			if (proyectoActual.getId() != null) {
				// Ing. Wilver Alexander Martínez Martínez -wam²
				// Cambio - Registro de cambios
				Persona personaAux = new Persona();
				personaAux = (Persona) sesion.getAttribute("persona");

				Formulario formulario = new Formulario();
				List listaFormulario = new ArrayList();

				listaFormulario = servicioGeneral.obtenerListaObjetos("Formulario where id ='120'");
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

			if (validarSiEsEstudianteLiderProyecto(proyectoActual.getId()) && proyectoActual.getId() != null) {
				//Se comenta el envio de correo por que en BD no esta creada la plantilla de correo 326 y se desconoce el motivo, 
				//lo cual genera excepcion al guardar con rol Estudiante Lider
//				enviarCorreoEdicionInfoPryEstLider(proyectoActual.getId());
			}

			sesion.setAttribute("proyecto", proyectoActual);

			sesion.removeAttribute("manejadorBibliografia");
			sesion.removeAttribute("manejadorFichaMinimaHome");
			sesion.removeAttribute("manejadorActividades");
			sesion.removeAttribute("manejadorBibliografia");
			sesion.removeAttribute("manejadorArchivos");

			///////// MODIFICADO GIOVANNI
			man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
			bandera = false;
			if (man != null) {
				if (man.getItemProyecto() != null) {
					// NavigationMenuItem lis[] =
					// man.getItemProyecto()[0].getNavigationMenuItems();
					MenuItem lis[] = man.getMenuItemArray();
					if (lis != null) {
						for (int i = 0; i < lis.length; i++) {
							if (bandera) {
								if (lis[i].isRendered()) {
									sesion.removeAttribute("manejadorMenuFormularios");
									// borrarManejadoresInsercionProyecto();
									return lis[i].getOutcome();
								}
							}

							if (lis[i].getOutcome().equals("irActividades")) {
								bandera = true;
							}

						}
					}
				}
			}

			//////////////////
			sesion.removeAttribute("manejadorMenuFormularios");

			if (proyectoActual.getModalidad().getTipo().getId().equals(TipoModalidad.SENA)) {
				return "irActividadesPersona";
			}
			return "irBibliografia";
		}
		return "";
	}

	public String siguienteInvestigador() {
		if (validarActividadesInvestigador()) {
			// if(idManejador>=(proyectoActual.getFase()).intValue() &&
			// (proyectoActual.getEstadoProyecto().getId()).equals("I"))
			// {proyectoActual.setFase(new Integer(idManejador + 1));}
			servicioProyecto.ingresarProyecto(proyectoActual);

			sesion.setAttribute("proyecto", proyectoActual);
			sesion.removeAttribute("manejadorMenuFormularios");
			sesion.removeAttribute("manejadorActividades");
			if ((proyectoActual.getEstadoProyecto().getId()).equals("I")) {
				return "irBibliografia";
			}
		}
		return "";
	}

	public boolean validarActividadesInvestigador() {
		for (Iterator i = proyectoActual.getActividades().iterator(); i.hasNext();) {
			Actividad a = (Actividad) i.next();
			for (Iterator ia = a.getActividadesPersona().iterator(); ia.hasNext();) {
				ActividadPersona ap = (ActividadPersona) ia.next();
				if (a.getSemanaInicial().intValue() > ap.getSemanaInicial().intValue()
						|| (a.getDuracionSemanas().intValue()) < (ap.getDuracionSemanas().intValue())) {
					sesion.setAttribute("mensaje",
							"las semanas de una actividad de persona no pueden estar por fuera de las semanas de la actividad");
					return false;
				}
			}
		}

		// for(Iterator
		// i=proyectoActual.getActividades().iterator();i.hasNext();)
		// {
		// Actividad a = (Actividad) i.next();
		// for(Iterator ia=a.getActividadesPersona().iterator();ia.hasNext();)
		// {
		// ActividadPersona ap=(ActividadPersona) ia.next();
		// int contador=1;
		//
		//
		// if(UtilidadesVarias.seTranslapanIntervalos(a.getSemanaInicial().intValue(),a.getSemanaInicial().intValue()+a.getDuracionSemanas().intValue(),ap.getSemanaInicial().intValue(),ap.getDuracionSemanas().intValue()+ap.getSemanaInicial().intValue()))
		// {
		// sesion.setAttribute("mensaje","Se estan traslapando los intervalos de
		// las actividades para una misma persona");
		// return false;
		// }
		// }
		// }
		return true;
	}

	// FUNCIONES ESPECIFICAS DE LA CLASE
	public void insertarActividad() {
		Actividad a = new Actividad();
		a.setDescripcion("");
		a.setMesInicial(new Integer(1));
		a.setDuracionMeses(new Integer(1));
		IdPersona idp = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId()).getId();
		a.setIdInv(idp.getDocumento() + "&" + idp.getTipoDocumento());
		listaActividades.add(a);
		a = null;
		mensajeErrorActividad = "";
	}

	public void insertarActividadConMetaObjetivo() {
		Actividad a = new Actividad();
		a.setDescripcion("");
		a.setMesInicial(new Integer(1));
		a.setDuracionMeses(new Integer(1));
		a.setObjetivoEspecifico(new ObjetivoEspecifico());
		a.setMeta("");
		listaActividades.add(a);
		a = null;
		mensajeErrorActividad = "";
	}

	public void eliminarActividad() {
		((Actividad) listaActividades.get(tablaActividades.getRowIndex())).setBorrable(true);
	}

	public void eliminarActividadConMetaObjetivo() {
		((Actividad) listaActividades.get(tablaActividades2.getRowIndex())).setBorrable(true);
	}

	// VALIDADORES
	private boolean validarActividades() {

		if (esProyectoLaboratorios) {
			if (proyectoActual.getListaActividades().isEmpty()) {
				mensajeErrorActividad = "No se encuentran actividades asociados al proyecto";
				FacesContext.getCurrentInstance().addMessage("msgForm", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"No se encuentran actividades asociados al proyecto", ""));
				return false;
			} else {
				int duracionProyecto = proyectoActual.getDuracion().intValue();
				int i_actividad = 1;
				proyectoActual.getListaActividades().iterator();
				Iterator it = proyectoActual.getListaActividades().iterator();
				while (it.hasNext()) {
					Actividad actividad = (Actividad) it.next();
					int mesInicial = actividad.getMesInicial().intValue();
					int duracionMeses = actividad.getDuracionMeses().intValue();
					if (duracionProyecto < (mesInicial + duracionMeses) - 1) {
						String numeroActividad = actividad.getMetaObjeto().getObjetivo().getNumeroOrden() + "."
								+ actividad.getMetaObjeto().getNumeroOrden() + "." + actividad.getNumeroOrden();
						mensajeErrorActividad = "La duración de la actividad " + numeroActividad
								+ " supera a la del proyecto";
						FacesContext.getCurrentInstance().addMessage("msgForm",
								new FacesMessage(FacesMessage.SEVERITY_ERROR,
										"La duración de la actividad " + numeroActividad + " supera a la del proyecto",
										""));
						return false;
					} else {
						i_actividad++;
					}
				}
			}

			return true;
		} else {
			// SE MIRA SI EXISTE ALGUNA ACTIVIDAD MARCADA PARA BORRAR
			List listaAuxiliar = new ArrayList();
			for (int i = 0; i < listaActividades.size(); i++) {
				Actividad a = (Actividad) listaActividades.get(i);
				if (a.isBorrable()) {
					listaAuxiliar.add(a);
					proyectoActual.borrarActividad(a);
				} else {
					proyectoActual.adicionarActividad(a);
					String tdo = a.getIdInv().split("&")[1];
					String documento = a.getIdInv().split("&")[0];
					IdPersona idp = new IdPersona(documento, tdo);
					ActividadPersona ap = new ActividadPersona();
					ap.setActividad(a);
					ap.setDuracionSemanas(0);
					ap.setSemanaInicial(0);
					ap.setInversion("0");
					if (a.getActividadesPersona() == null) {
						a.setActividadesPersona(new HashSet());
						ap.setInvestigador(servicioPersona.obtenerInvestigador(idp));
						a.getActividadesPersona().add(ap);
					} else if (a.getActividadesPersona().isEmpty()) {
						ap.setInvestigador(servicioPersona.obtenerInvestigador(idp));
						a.getActividadesPersona().add(ap);
					} else {
						((ActividadPersona) a.getActividadesPersona().toArray()[0])
								.setInvestigador(servicioPersona.obtenerInvestigador(idp));
					}

				}
			}
			listaActividades.removeAll(listaAuxiliar);
			mensajeErrorActividad = "";
			// SE VALIDA QUE LA LISTA CONTENGA OBJETOS
			if (!listaActividades.isEmpty()) {
				int duracionProyecto = proyectoActual.getDuracion().intValue();
				int i_actividad = 1;
				listaActividades.iterator();
				Iterator it = listaActividades.iterator();
				while (it.hasNext()) {
					Actividad actividad = (Actividad) it.next();
					int mesInicial = actividad.getMesInicial().intValue();
					int duracionMeses = actividad.getDuracionMeses().intValue();
					if (!actividad.isBorrable() && (duracionProyecto < (mesInicial + duracionMeses) - 1)) {
						mensajeErrorActividad = "La duración de la actividad " + i_actividad
								+ " supera a la del proyecto";
						FacesContext.getCurrentInstance().addMessage("msgForm",
								new FacesMessage(FacesMessage.SEVERITY_ERROR,
										"La duración de la actividad " + i_actividad + " supera a la del proyecto",
										""));
						return false;
					} else {
						i_actividad++;
					}
				}
			} else {
				mensajeErrorActividad = "No se encuentran actividades asociados al proyecto";
				FacesContext.getCurrentInstance().addMessage("msgForm", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"No se encuentran actividades asociados al proyecto", ""));
				return false;
			}
			return true;
		}

	}

	public void cambiaPersona(ValueChangeEvent event) {

		personaNueva = (String) event.getNewValue();
		// String documento = personaNueva.split("&")[0];
		// String tipoDocumento = personaNueva.split("&")[1];
		// IdPersona id= new IdPersona();
		// id.setDocumento(documento);
		// id.setTipoDocumento(tipoDocumento);
		// Investigador i = new Investigador();
		// i.setId(id);
		// for(Iterator itInv=
		// listaInvestigadoresDelProyecto.iterator();itInv.hasNext();)
		// {
		// Investigador
		// }

	}

	public void cambiaActividad(ValueChangeEvent event) {
		actividadPersonaNueva = (String) event.getNewValue();

	}

	public void insertarActividadPersona() {
		Actividad actividad = (Actividad) servicioGeneral.obtenerObjeto(new Actividad(),
				Long.valueOf(actividadPersonaNueva));
		String tdo = personaNueva.split("&")[1];
		String documento = personaNueva.split("&")[0];
		IdPersona id = new IdPersona();
		id.setDocumento(documento);
		id.setTipoDocumento(tdo);

		ActividadPersona ap = new ActividadPersona();
		ap.setActividad(actividad);
		ap.setInvestigador(servicioPersona.obtenerInvestigador(id));
		ap.setDuracionSemanas(duracionSemanas);
		ap.setSemanaInicial(semanaInicial);
		ap.setInversion(inversion);

		for (Iterator itAct = listaActividadesParaPersona.iterator(); itAct.hasNext();) {
			VistaActividad a = (VistaActividad) itAct.next();
			if (a.getActividad().getId().equals(Long.valueOf(actividadPersonaNueva))) {
				if (validarActividadPersona(ap, a.getActividad())) {
					a.getActividad().getActividadesPersona().add(ap);
					break;
				}
			}
		}

		// servicioGeneral.guardarObjeto(ap);
	}

	public boolean validarActividadPersona(ActividadPersona ap, Actividad a) {
		if (ap.getSemanaInicial().intValue() < a.getSemanaInicial().intValue()) {
			sesion.setAttribute("mensaje", "la semana inicial es menor a el de la semana inicial de la actividad");
			return false;
		}
		if (ap.getSemanaInicial().intValue() + ap.getDuracionSemanas().intValue() > a.getSemanaInicial().intValue()
				+ a.getDuracionSemanas().intValue()) {
			sesion.setAttribute("mensaje",
					"la semana final es mayor a la semana final de la actividad, recuerde que la semana final no se ingresa, sino la duración");
			return false;
		}

		List listaAp = obtenerActividadesPersonaXInvestigador(
				a.getActividadesPersona() == null ? new Vector() : new Vector(a.getActividadesPersona()),
				ap.getInvestigador().getId());// servicioProyecto.obtenerListaActividadesPersonaXActividadYPersona(a,ap.getInvestigador().getId());
		if (servicioProyecto.seTranslapanActividadPersona(ap, listaAp)) {
			sesion.setAttribute("mensaje", "esta Persona ya tiene asociada actividad en este intervalo ");
			return false;
		}
		return true;
	}

	public List obtenerActividadesPersonaXInvestigador(List listaActividadesPersona, IdPersona id) {
		List resultado = new Vector();
		for (Iterator itActividadesPersona = listaActividadesPersona.iterator(); itActividadesPersona.hasNext();) {
			ActividadPersona ap = (ActividadPersona) itActividadesPersona.next();
			if (id.equals(ap.getInvestigador().getId())) {
				resultado.add(ap);
			}
		}
		return resultado;
	}

	public void eliminarActividadPersona(ActionEvent event) {
		System.out.println("----------------------");
		System.out.println("----------------------");
		System.out.println("----------------------");
		System.out.println("----------------------");

		// String duracionSemana = (String)
		// event.getComponent().getAttributes().get("duracionSemanas");
		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		String o = (String) map.get("duracionSemanas");
		o = o.replaceAll("&amp;", "&");
		System.out.println(o.getClass().getName());
		System.out.println(o);
		// idActividadPersona=Long.valueOf( o);
		String semanaInicial = o.split("&")[3];
		String duracionSemana = o.split("&")[4];
		String actividad = o.split("&")[0];
		String tdo = o.split("&")[1];
		String documento = o.split("&")[2];

		// String tdo=personaNueva.split("&")[1];
		// String documento=personaNueva.split("&")[0];
		// IdPersona id= new IdPersona();
		// id.setDocumento(documento);
		// id.setTipoDocumento(tdo);
		// Investigador inv=new Investigador();
		// inv.setId(id);
		boolean borrar = false;
		VistaActividad a = null;
		ActividadPersona ap = null;
		//
		// VistaActividad ac= (VistaActividad)
		// tablaActividadesParaPersona.getRowData();
		// ActividadPersona actividadPersona =(ActividadPersona)
		// ac.getTablaActividad().getRowData();
		// ac.getActividad().getActividadesPersona().remove(actividadPersona);
		for (Iterator itAct = listaActividadesParaPersona.iterator(); itAct.hasNext();) {
			a = (VistaActividad) itAct.next();
			if (actividad.equals(a.getActividad().getId().toString())) {
				for (Iterator itAcp = a.getActividad().getActividadesPersona().iterator(); itAcp.hasNext();) {
					ap = (ActividadPersona) itAcp.next();
					if (ap.getSemanaInicial().toString().equals(semanaInicial)
							&& ap.getDuracionSemanas().toString().equals(duracionSemana)
							&& ap.getInvestigador().getId().getTipoDocumento().equals(tdo)
							&& ap.getInvestigador().getId().getDocumento().equals(documento)) {
						borrar = true;
						// a.getActividadesPersona().remove(ap);
						break;
					}
				}
				if (borrar) {
					break;
				}
			}
		}
		if (borrar) {
			a.getActividad().getActividadesPersona().remove(ap);
		}
		// ActividadPersona ap =(ActividadPersona
		// )servicioGeneral.obtenerObjeto(new
		// ActividadPersona(),idActividadPersona);
		System.out.println("-------------------------");
		System.out.println("-------------------------");
		System.out.println("-------------------------");

		// System.out.println(idActividadPersona);
		// servicioGeneral.eliminarObjeto(ap);
	}
	// METODOS SET Y GET

	public List getActividadPersonas() {
		if (tablaActividadesParaPersona != null) {

			VistaActividad actividad = (VistaActividad) tablaActividadesParaPersona.getRowData();
			return new Vector(actividad.getActividad().getActividadesPersona());// servicioProyecto.obtenerActividadesPersonaPorActividad(actividad.getId());
		}
		return new Vector();
	}

	public UIData getTabla() {
		VistaActividad actividad = (VistaActividad) tablaActividadesParaPersona.getRowData();
		actividad.setTablaActividad(new UIData());
		return actividad.getTablaActividad();
	}

	public boolean getEsSena() {
		return proyectoActual.getModalidad().getTipo().getId().equals(TipoModalidad.SENA);
	}

	public List getListaActividades() {
		return listaActividades;
	}

	public void setListaActividades(List listaActividades) {
		this.listaActividades = listaActividades;
	}

	public String getMensajeErrorActividad() {
		return mensajeErrorActividad;
	}

	public void setMensajeErrorActividad(String mensajeErrorActividad) {
		this.mensajeErrorActividad = mensajeErrorActividad;
	}
	// public UIData getTablaActividades() {
	// return tablaActividades;
	// }
	// public void setTablaActividades(UIData tablaActividades) {
	// this.tablaActividades = tablaActividades;
	// }

	public List getListaInvestigadoresDelProyecto() {
		return listaInvestigadoresDelProyecto;
	}

	public DataTable getTablaActividades() {
		return tablaActividades;
	}

	public void setTablaActividades(DataTable tablaActividades) {
		this.tablaActividades = tablaActividades;
	}

	public void setTablaActividadesParaPersona(DataTable tablaActividadesParaPersona) {
		this.tablaActividadesParaPersona = tablaActividadesParaPersona;
	}

	public void setListaInvestigadoresDelProyecto(List listaInvestigadoresDelProyecto) {
		this.listaInvestigadoresDelProyecto = listaInvestigadoresDelProyecto;
	}

	public List getListaActividadesParaPersona() {
		return listaActividadesParaPersona;
	}

	public void setListaActividadesParaPersona(List listaActividadesParaPersona) {
		this.listaActividadesParaPersona = listaActividadesParaPersona;
	}

	public SelectItem[] getListaActividadesItem() {
		return listaActividadesItem;
	}

	public void setListaActividadesItem(SelectItem[] listaActividadesItem) {
		this.listaActividadesItem = listaActividadesItem;
	}

	// public UIData getTablaActividadesParaPersona() {
	// return tablaActividadesParaPersona;
	// }
	// public void setTablaActividadesParaPersona(
	// UIData tablaActividadesParaPersona) {
	// this.tablaActividadesParaPersona = tablaActividadesParaPersona;
	// }
	public String getActividadPersonaNueva() {
		return actividadPersonaNueva;
	}

	public void setActividadPersonaNueva(String actividadPersonaNueva) {
		this.actividadPersonaNueva = actividadPersonaNueva;
	}

	public SelectItem[] getListainvestigadoresitem() {
		return listainvestigadoresitem;
	}

	public void setListainvestigadoresitem(SelectItem[] listainvestigadoresitem) {
		this.listainvestigadoresitem = listainvestigadoresitem;
	}

	public String getPersonaNueva() {
		return personaNueva;
	}

	public void setPersonaNueva(String personaNueva) {
		this.personaNueva = personaNueva;
	}

	public Integer getDuracionSemanas() {
		return duracionSemanas;
	}

	public void setDuracionSemanas(Integer duracionSemanas) {
		this.duracionSemanas = duracionSemanas;
	}

	public Integer getSemanaInicial() {
		return semanaInicial;
	}

	public void setSemanaInicial(Integer semanaInicial) {
		this.semanaInicial = semanaInicial;
	}

	public String getInversion() {
		return inversion;
	}

	public void setInversion(String inversion) {
		this.inversion = inversion;
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

	public void setEsConvIni(boolean esConvIni) {
		this.esConvIni = esConvIni;
	}

	public boolean isEsConvIni() {
		return esConvIni;
	}

	public String getTitulo() {
		if (esConvIni || esConvSem) {
			return "Actividades a desarrollar por parte del estudiante";
		} else if (esConvRepotenciacionLab2018) {
			return "Plan de actividades a desarrollar, para la repotenciación del equipo";
		} else {
			return "Actividades";
		}

	}

	public List getListaObjetivosProyecto() {
		return listaObjetivosProyecto;
	}

	public void setListaObjetivosProyecto(List listaObjetivosProyecto) {
		this.listaObjetivosProyecto = listaObjetivosProyecto;
	}

	public SelectItem[] getListaObjetivosItem() {
		return listaObjetivosItem;
	}

	public void setListaObjetivosItem(SelectItem[] listaObjetivosItem) {
		this.listaObjetivosItem = listaObjetivosItem;
	}

	public DataTable getTablaActividades2() {
		return tablaActividades2;
	}

	public void setTablaActividades2(DataTable tablaActividades2) {
		this.tablaActividades2 = tablaActividades2;
	}

	public Boolean getEsProyectoLaboratorios() {
		return esProyectoLaboratorios;
	}

	public void setEsProyectoLaboratorios(Boolean esProyectoLaboratorios) {
		this.esProyectoLaboratorios = esProyectoLaboratorios;
	}

	/**
	 * @return the mostrarActividadesObjetivosMetas
	 */
	public boolean isMostrarActividadesObjetivosMetas() {
		return mostrarActividadesObjetivosMetas;
	}

	/**
	 * @param mostrarActividadesObjetivosMetas
	 *            the mostrarActividadesObjetivosMetas to set
	 */
	public void setMostrarActividadesObjetivosMetas(boolean mostrarActividadesObjetivosMetas) {
		this.mostrarActividadesObjetivosMetas = mostrarActividadesObjetivosMetas;
	}

	/**
	 * @return the convocatoriaActual
	 */
	public Convocatoria getConvocatoriaActual() {
		return convocatoriaActual;
	}

	/**
	 * @param convocatoriaActual
	 *            the convocatoriaActual to set
	 */
	public void setConvocatoriaActual(Convocatoria convocatoriaActual) {
		this.convocatoriaActual = convocatoriaActual;
	}

	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public UIComponent getActividadProyecto() {
		return actividadProyecto;
	}

	public void setActividadProyecto(UIComponent actividadProyecto) {
		this.actividadProyecto = actividadProyecto;
	}

	public String getMetaObjetivoSelectValue() {
		return metaObjetivoSelectValue;
	}

	public void setMetaObjetivoSelectValue(String metaObjetivoSelectValue) {
		this.metaObjetivoSelectValue = metaObjetivoSelectValue;
	}

	public List<SelectItem> getMetasItem() {
		return metasItem;
	}

	public void setMetasItem(List<SelectItem> metasItem) {
		this.metasItem = metasItem;
	}

	public String getResultadoSelectValue() {
		return resultadoSelectValue;
	}

	public void setResultadoSelectValue(String resultadoSelectValue) {
		this.resultadoSelectValue = resultadoSelectValue;
	}

	public List<SelectItem> getResultadosItem() {
		return resultadosItem;
	}

	public void setResultadosItem(List<SelectItem> resultadosItem) {
		this.resultadosItem = resultadosItem;
	}

	public String getMesInicial() {
		return mesInicial;
	}

	public void setMesInicial(String mesInicial) {
		this.mesInicial = mesInicial;
	}

	public String getDuracion() {
		return duracion;
	}

	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}

	public DataTable getTablaActividades3() {
		return tablaActividades3;
	}

	public void setTablaActividades3(DataTable tablaActividades3) {
		this.tablaActividades3 = tablaActividades3;
	}

	public Actividad getActividadTabla() {
		return actividadTabla;
	}

	public void setActividadTabla(Actividad actividadTabla) {
		this.actividadTabla = actividadTabla;
	}

	public UIComponent getMesInicialUI() {
		return mesInicialUI;
	}

	public void setMesInicialUI(UIComponent mesInicialUI) {
		this.mesInicialUI = mesInicialUI;
	}

	public UIComponent getDuracionUI() {
		return duracionUI;
	}

	public void setDuracionUI(UIComponent duracionUI) {
		this.duracionUI = duracionUI;
	}

	public boolean isEsConvRepotenciacionLab2018() {
		return esConvRepotenciacionLab2018;
	}

	public void setEsConvRepotenciacionLab2018(boolean esConvRepotenciacionLab2018) {
		this.esConvRepotenciacionLab2018 = esConvRepotenciacionLab2018;
	}

	public boolean isValidarBotonGuardar() {
		return validarBotonGuardarConvocatoria(proyectoActual.getId());
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

}
