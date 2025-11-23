package co.edu.unal.hermes.vista.laboratorios;

import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioSolicitud;
import co.edu.unal.hermes.modelo.laboratorios.SolicitudLaboratorios;
import co.edu.unal.hermes.modelo.servicioGeneral.ServicioGeneral;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * @author dgbenitezc
 * 
 */
public class ManejadorListaSolicitudCreacionLab extends ManejadorBase {

	protected Boolean esLaboratoriosSede;
	protected Boolean esLaboratoriosFacultad;
	protected Boolean esLaboratoriosNacional;
	protected Boolean esConsultaLaboratorios;
	protected Boolean esCoordinadorLaboratorio;

	private Boolean soloLectura;
	private List<LaboratorioSolicitud> listaSolicitudes;
	private LaboratorioSolicitud nuevaSolicitud;
	private SelectItem[] selectItemTipoSolicitud;
	private SelectItem[] selectItemLaboratorios;
	private List<Laboratorio> listaLabs;
	private Boolean creacionLab;
	private LaboratorioSolicitud solicitudSeleccionada;
	
	

	public ManejadorListaSolicitudCreacionLab() {

		System.out.println("ManejadorListaSolicitudCreacionLab");

		limpiarSesion();
		sesion.removeAttribute("ManejadorListaSolicitudCreacionLab");
		sesion.removeAttribute("ManejadorSolicitudCreacionLaboratorio");

		personaActual = (Persona) sesion.getAttribute("persona");
		esLaboratoriosSede = (Boolean) sesion.getAttribute("esLaboratoriosSede");
		esLaboratoriosNacional = (Boolean) sesion.getAttribute("esLaboratorios");
		esConsultaLaboratorios = (Boolean) sesion.getAttribute("esConsultaLaboratorios");
		esCoordinadorLaboratorio = (Boolean) sesion.getAttribute("esCoordinadorLaboratorio");
		esLaboratoriosFacultad = (Boolean) sesion.getAttribute("esLaboratoriosFacultad");

		soloLectura = false;
//		System.out.println("soloLectura: " + soloLectura);
//		System.out.println("esLaboratoriosSede: " + esLaboratoriosSede);
//		System.out.println("esLaboratoriosNacional: " + esLaboratoriosNacional);
//		System.out.println("esConsultaLaboratorios: " + esConsultaLaboratorios);
//		System.out.println("esCoordinadorLaboratorio: "+ esCoordinadorLaboratorio);
//		System.out.println("esLaboratoriosFacultad: " + esLaboratoriosFacultad);
//		System.out.println("ManejadorSolicitudLaboratorio personaActual:"+ personaActual);

		cargarListaSolicitudes();

//		selectItemTipoSolicitud = servicioGeneral.selectItemHijosDeTiposValorObjeto(Tipos.TIPOS_SOLICITUDES_LABORATORIO);
//
//		// Lista de laboratorios:
//		String hqlBuscarLabs = "SELECT #id l.id, #nombre l.nombre, #sede l.sede FROM Laboratorio l WHERE l.activo = '1' ";
//		if (esCoordinadorLaboratorio) {
//			hqlBuscarLabs += " AND l.id in (" + sesion.getAttribute("idLabs")
//					+ ") ";
//		}
//		if (esLaboratoriosSede) {
//			Sede sede = (Sede) sesion.getAttribute("sedeLabsSede");
//			hqlBuscarLabs += " AND l.sede.id in (" + sede.getId() + ") ";
//		}
//		
//		hqlBuscarLabs += " ORDER BY l.nombre";
//		System.out.println("hqlBuscarLabs:" + hqlBuscarLabs);
//		listaLabs = servicioGeneral.obtenerObjetosLimitado(Laboratorio.class,hqlBuscarLabs);
//
//		selectItemLaboratorios = new SelectItem[listaLabs.size() + 1];
//		selectItemLaboratorios[0] = new SelectItem(null, "Seleccione...");
//		int i = 1;
//		for (Laboratorio l : listaLabs) {
//			selectItemLaboratorios[i] = new SelectItem(l.getId(), l.getSede().getNombre() + " (" + l.getId() + ") " + l.getNombre());
//			i++;
//		}
		// TODO: una página para cada tipo de solitud???
	}

	public void cargarListaSolicitudes() {
		
		listaSolicitudes = servicioGeneral.obtenerListaSolicitudesLaboratorioXPersonaXTIpoSol(personaActual.getId(), Tipos.TIPOS_TIPO_SOLICITUD_LABORATORIO_Crear_Laboratorio);
		
//		String hqlBuscarSols;
//		String orderBy;
//
//		/*
//		 * hqlBuscarSols =
//		 * "SELECT #id l.id FROM Laboratorio l WHERE l.tipoSolicitud IS NOT NULL"
//		 * ; System.out.println("hqlBuscarSols:" + hqlBuscarSols);
//		 * listaSolicitudes = servicioGeneral.obtenerObjetosLimitado(
//		 * Laboratorio.class, hqlBuscarSols); List<Laboratorio>
//		 * listaSolicitudes2 = new ArrayList<Laboratorio>(); for (Laboratorio l
//		 * : listaSolicitudes) {
//		 * listaSolicitudes2.add(servicioGeneral.obtenerObjetoXID(
//		 * Laboratorio.class, l.getId().toString()).get(0)); }
//		 */
//
//		hqlBuscarSols = "FROM SolicitudLaboratorios sL WHERE sL.laboratorio IS NOT NULL ";
//		orderBy = " ORDER BY sL.id DESC ";
//		System.out.println("hqlBuscarSols:" + hqlBuscarSols);
//
//		// esLaboratoriosNacional: ve todas
//		// esCoordinadorLaboratorio: solo ve las propias
//		if (esCoordinadorLaboratorio) {
//			hqlBuscarSols += " AND sL.persona.id.tipoDocumento = '"
//					+ personaActual.getId().getTipoDocumento()
//					+ "' AND sL.persona.id.documento = '"
//					+ personaActual.getId().getDocumento() + "'";
//		}
//
//		// TODO: esLaboratoriosSede
//
//		hqlBuscarSols += orderBy;
//		System.out.println("hqlBuscarSols:" + hqlBuscarSols);
//		listaSolicitudes = servicioGeneral.obtenerObjetos(SolicitudLaboratorios.class, hqlBuscarSols);
//
//		System.out.println("listaSolicitudes.size(): "+ listaSolicitudes.size());
	}

	public void limpiarSesion() {
		sesion.removeAttribute("ManejadorLaboratoriosInformacionGeneral");
		sesion.removeAttribute("ManejadorLaboratoriosRecursoHumano");
		sesion.removeAttribute("ManejadorLaboratoriosRiesgos");
		sesion.removeAttribute("ManejadorLaboratoriosGestion");
		sesion.removeAttribute("ManejadorLaboratoriosEquipos");
		sesion.removeAttribute("ManejadorLaboratoriosInvestigacion");
		sesion.removeAttribute("ManejadorLaboratoriosProyectos");
		sesion.removeAttribute("ManejadorLaboratoriosDocencia");
		sesion.removeAttribute("ManejadorLaboratoriosEnsayosServicios");
		sesion.removeAttribute("ManejadorMenuFormularioLaboratorios");
		sesion.removeAttribute("ManejadorAdministrarLaboratorios");
		sesion.removeAttribute("manejadorHojaDeVidaEquipos");
		sesion.removeAttribute("Laboratorio");
		sesion.removeAttribute("soloLectura");
		sesion.removeAttribute("solicitudLaboratorio");
		sesion.removeAttribute("manejadorLaboratorioPresupuesto");
		sesion.removeAttribute("ManejadorSolicitudCreacionLaboratorio");
	}

	public String salir() {
		limpiarSesion();
		sesion.removeAttribute("ManejadorListaSolicitudCreacionLab");
		return "misProyectos";
	}

	public String nuevoLab() {
		limpiarSesion();
		if (validarSolicitud()) {
			nuevaSolicitud.setPersona(personaActual);
			sesion.setAttribute("solicitudLaboratorio", nuevaSolicitud);
			return "CrearLaboratorio";
		} else {
			return "";
		}
	}

	public String editarLaboratorioCreacion() {
		limpiarSesion();
		sesion.removeAttribute("ManejadorSolicitudCreacionLaboratorio");
		sesion.removeAttribute("ManejadorListaSolicitudCreacionLab");
		
		System.out.println("editarLaboratorioCreacion:"+ solicitudSeleccionada.getLaboratorio().getId());
		sesion.setAttribute("Laboratorio", nuevaSolicitud.getLaboratorio());
		sesion.setAttribute("solicitudLaboratorio", nuevaSolicitud);
		sesion.setAttribute("soloLectura", false);
		return "CrearLaboratorio";
	}

	public String consultarLaboratorioCreacion() {
		limpiarSesion();
		System.out.println("consultarLaboratorioCreacion:"
				+ nuevaSolicitud.getLaboratorio().getId());
		sesion.setAttribute("Laboratorio", nuevaSolicitud.getLaboratorio());
		sesion.setAttribute("solicitudLaboratorio", nuevaSolicitud);
		sesion.setAttribute("soloLectura", true);
		return "CrearLaboratorio";
	}

	public String consultarSolicitud() {
//		System.out.println("consultarSolicitud:");
//		limpiarSesion();
//		nuevaSolicitud = solicitudSeleccionada;
//		soloLectura = true;
		return "consultarEditarSolicitudLaboratorio";
	}

	public String editarSolicitud() {
		System.out.println("editarSolicitud:");
		limpiarSesion();
		sesion.removeAttribute("ManejadorListaSolicitudCreacionLab");
//		nuevaSolicitud = solicitudSeleccionada;
		sesion.setAttribute("solicitudSeleccionada", solicitudSeleccionada);
		sesion.setAttribute("esEdicionCL", true);
		soloLectura = false;
		return "solicitudCreacionLaboratorio";
	}

	public String regresarListado() {
		System.out.println("regresarListado:");
		limpiarSesion();
		sesion.removeAttribute("manejadorSolicitudLaboratorio");
		// nuevaSolicitud = solicitudSeleccionada;
		// soloLectura = true;
		return "solicitudLaboratorio";
	}

	public String crearSolicitud() {
		System.out.println("crearSolicitud:");
		limpiarSesion();
		// valores iniciales
//		nuevaSolicitud = new SolicitudLaboratorios();
//		nuevaSolicitud.getTipoSolicitud().setId(Tipos.TIPO_SOLICITUD_LABORATORIO_CREACION);
		soloLectura = false;
		return "solicitudCreacionLaboratorio";
	}

//	public String guardarSolicitud() {
//		System.out.println("guardarSolicitud:");
//
//		System.out.println("guardarSolicitud:"
//				+ nuevaSolicitud.getLaboratorio());
//		System.out.println("guardarSolicitud:"
//				+ nuevaSolicitud.getLaboratorio().getId());
//
//		if (validarSolicitud()) {
//			nuevaSolicitud.setPersona(personaActual);
//			nuevaSolicitud.setFecha(new Date());
//
//			Long idLabSol = nuevaSolicitud.getLaboratorio().getId();
//			for (Laboratorio l : listaLabs) {
//				if (idLabSol.equals(l.getId())) {
//					nuevaSolicitud.setLaboratorio(l);
//				}
//			}
//			listaSolicitudes.add(nuevaSolicitud);
//			EstadoProyecto estadoSolicitud = new EstadoProyecto();
//			estadoSolicitud.setId(EstadoProyecto.PROPUESTO);
//			nuevaSolicitud.setEstadoSolicitud(estadoSolicitud);
//			nuevaSolicitud.setLaboratorioSolicitante(null);
//			servicioGeneral.guardarObjeto(nuevaSolicitud);
//
//			mensajeInfo("Su solicitud ha sido guardada, con Id "
//					+ nuevaSolicitud.getId());
//			// nuevaSolicitud = new SolicitudLaboratorios();
//			cargarListaSolicitudes();
//			solicitudSeleccionada = nuevaSolicitud;
//			FacesContext context = FacesContext.getCurrentInstance();
//			context.getExternalContext().getFlash().setKeepMessages(true);
//			// return consultarSolicitud();
//			return regresarListado();
//			// return "solicitudLaboratorio";
//		} else {
//			return "";
//		}
//	}

	public Boolean validarSolicitud() {
		Boolean validar = true;

//		if (!nuevaSolicitud.getTipoSolicitud().getId()
//				.equals(Tipos.TIPO_SOLICITUD_LABORATORIO_CREACION)) {
//			if (nuevaSolicitud.getLaboratorio().getId().equals(0L)) {
//				mensajeError("form:siLab", "Debe seleccionar un Laboratorio.");
//				validar = false;
//			}
//		}
//
//		nuevaSolicitud.setJustificacion(nuevaSolicitud.getJustificacion()
//				.trim());
//		if (nuevaSolicitud.getJustificacion().isEmpty()) {
//			mensajeError("form:itJust", "Debe justificar su Solicitud.");
//			validar = false;
//		}

		return validar;
	}

	/**
	 * @return the listaSolicitudes
	 */
	public List<LaboratorioSolicitud> getListaSolicitudes() {
		return listaSolicitudes;
	}

	/**
	 * @return the seletcItemTipoSolicitud
	 */
	public SelectItem[] getSelectItemTipoSolicitud() {
		return selectItemTipoSolicitud;
	}

	/**
	 * @return the selectItemLaboratorios
	 */
	public SelectItem[] getSelectItemLaboratorios() {
		return selectItemLaboratorios;
	}

	/**
	 * @return the soloLectura
	 */
	public Boolean getSoloLectura() {
		return soloLectura;
	}

	/**
	 * @return the creacionLab
	 */
	public Boolean getCreacionLab() {
//		if (nuevaSolicitud.getTipoSolicitud().getId()
//				.equals(Tipos.TIPO_SOLICITUD_LABORATORIO_CREACION)) {
//			creacionLab = true;
//		} else {
//			creacionLab = false;
//		}
//		System.out.println("creacionLab:" + creacionLab);
		return creacionLab;
	}

	public Boolean getEsCreacionYaGuardada() {
		return (getCreacionLab() && nuevaSolicitud.getLaboratorio().getId() != null);
	}

	public Boolean getPuedeGuardarCreacion() {
		System.out.println("getPuedeGuardarCreacion:");
		Boolean puedeGuardarCreacion;
		if (getEsCreacionYaGuardada()) {
			String hqlBuscarEtapa = "SELECT #id l.id FROM Laboratorio l WHERE l.id = "
					+ nuevaSolicitud.getLaboratorio().getId()
					+ " AND l.etapaRegistro > "
					+ ManejadorLaboratorios.PRESUPUESTO;
			System.out.println("hqlBuscarEtapa:" + hqlBuscarEtapa);
			List<Laboratorio> listaLabEtapa = servicioGeneral
					.obtenerObjetosLimitado(Laboratorio.class, hqlBuscarEtapa);
			puedeGuardarCreacion = (listaLabEtapa.size() > 0);
		} else {
			puedeGuardarCreacion = false;
		}
		System.out.println("getPuedeGuardarCreacion:" + puedeGuardarCreacion);
		return puedeGuardarCreacion;
	}

	public LaboratorioSolicitud getNuevaSolicitud() {
		return nuevaSolicitud;
	}

	public void setNuevaSolicitud(LaboratorioSolicitud nuevaSolicitud) {
		this.nuevaSolicitud = nuevaSolicitud;
	}

	public LaboratorioSolicitud getSolicitudSeleccionada() {
		return solicitudSeleccionada;
	}

	public void setSolicitudSeleccionada(LaboratorioSolicitud solicitudSeleccionada) {
		this.solicitudSeleccionada = solicitudSeleccionada;
	}
	
	

}
