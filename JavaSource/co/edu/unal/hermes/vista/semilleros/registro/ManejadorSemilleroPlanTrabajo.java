package co.edu.unal.hermes.vista.semilleros.registro;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.faces.model.SelectItem;

import org.primefaces.event.RowEditEvent;

import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Semillero;
import co.edu.unal.hermes.modelo.SemilleroActividad;
import co.edu.unal.hermes.modelo.SemilleroEstado;
import co.edu.unal.hermes.modelo.SemilleroHistoricoCambios;
import co.edu.unal.hermes.modelo.SemilleroIntegrante;
import co.edu.unal.hermes.modelo.SemilleroResultado;
import co.edu.unal.hermes.modelo.SemilleroSolicitud;
import co.edu.unal.hermes.modelo.correo.Correo;

public class ManejadorSemilleroPlanTrabajo extends ManejadorSemilleroRegistro {

	private static final long serialVersionUID = 1L;
	private Semillero semilleroActual;
	private ArrayList<SelectItem> integrantesItems;
	private SemilleroActividad actividadActual;
	private ArrayList<SemilleroActividad> actividades;
	private Date fechaInicio;
	private String duracion;
	private SemilleroActividad actividadEliminar;
	private SemilleroResultado resultadoActual;
	private SemilleroResultado resultadoEliminar;
	private boolean solicitudAprobadaPlanTrabajo;
	private Integer idSolicitudPlanTrabajo;
	private boolean solicitudAprobadaMetodologia;
	private boolean solicitudAprobadaResultados;
	private Integer idSolicitudContenido;
	private Date fechaCreacionActividad;
	private SemilleroActividad actividadSeleccionada;

	public ManejadorSemilleroPlanTrabajo() {
		init();
	}

	private void init() {
		setSemilleroActual(servicioGeneral.obtenerObjetos(Semillero.class,
				"from Semillero s where s.id=" + (Integer) sesion.getAttribute("semillero")).get(0));
		setActividadActual(new SemilleroActividad());
		setResultadoActual(new SemilleroResultado());
		cargarActividades();
		solicitudAprobadaPlanTrabajo = false;
		solicitudAprobadaMetodologia = false;
		solicitudAprobadaResultados = false;
		actividadSeleccionada = new SemilleroActividad();
		for (SemilleroSolicitud solicitud : semilleroActual.getSolicitudes()) {
			if (solicitud.getRespuestaCoordinador() != null && solicitud.getRespuestaCoordinador().getId().equals(7)) {
				if (solicitud.getTipo().getId().equals(4) && solicitud.getDetalle() == null) {
					solicitudAprobadaPlanTrabajo = true;
					idSolicitudPlanTrabajo = solicitud.getId();
				} else if (solicitud.getDetalle() != null && solicitud.getTipo().getId().equals(3)) {
					idSolicitudContenido = solicitud.getId();
					if (solicitud.getDetalle().toLowerCase().contains("metodología")) {
						solicitudAprobadaMetodologia = true;
					}
					if (solicitud.getDetalle().toLowerCase().contains("resultados esperados")) {
						solicitudAprobadaResultados = true;
					}
				}
			}
		}
	}

	private void cargarActividades() {
		actividades = new ArrayList<SemilleroActividad>();
		integrantesItems = new ArrayList<SelectItem>();
		for (SemilleroIntegrante item : getSemilleroActual().getListaIntegrantes()) {
			if(!"R".equals(item.getEstado())) {
				integrantesItems.add(new SelectItem(item.getId(), item.getIntegrante().getNombreCompleto()));
			}
				actividades.addAll(item.getListaPlanTrabajo());
		}
		
		Collections.sort(actividades, new Comparator<SemilleroActividad>() {
		    public int compare(SemilleroActividad a1, SemilleroActividad a2) {
		        if (a1.getFechaInicio() == null && a2.getFechaInicio() == null) {
		            return 0;
		        }
		        if (a1.getFechaInicio() == null) {
		            return 1; // nulls al final
		        }
		        if (a2.getFechaInicio() == null) {
		            return -1; // nulls al final
		        }
		        return a1.getFechaInicio().compareTo(a2.getFechaInicio());
		    }
		});

	}
	public String guardarPlanTrabajo() {
		if(semilleroActual.getEstadoActual().getId().equals(SemilleroEstado.ESTADO_ACTIVO) && !solicitudAprobadaMetodologia && !solicitudAprobadaResultados) {
			guardarHistoricoModPlanTrabajo();
		}
		return siguiente();
	}
	
	public void guardarHistoricoModPlanTrabajo() {
		try {
			SemilleroHistoricoCambios hcambios = new SemilleroHistoricoCambios();
			hcambios.setFecha(new Date());
			hcambios.setSemillero(semilleroActual);
			hcambios.setDescripcion("Modificación del plan de trabajo");
			hcambios.setTipoDocResponsable(personaActual.getId().getTipoDocumento());
			hcambios.setDocumentoResponsable(personaActual.getId().getDocumento());
			servicioGeneral.guardarObjeto(hcambios);
			CorreoPlantilla cp = cargarPlantilla(392);
			cp.setCuerpo(cp.getCuerpo().replaceAll("<<ID>>", semilleroActual.getId().toString()));
			cp.setCuerpo(cp.getCuerpo().replaceAll("<<DIRECTOR>>", semilleroActual.getLider().getNombreCompleto()));
			
			IdPersona idCoordinador = new IdPersona();
			idCoordinador.setDocumento(semilleroActual.getDocumentoCoordinador());
			idCoordinador.setTipoDocumento(semilleroActual.getTipoDocumentoCoordinador());
			semilleroActual.setCoordinador(servicioPersona.obtenerInvestigador(idCoordinador));
			
			Correo correo = new Correo();
			correo.setOrigen(Correo.CORREO_HERMES);
			//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
			correo.adicionarDireccion(semilleroActual.getCoordinador().getEmail());
			correo.setAsunto(cp.getAsunto());
			correo.setCuerpo(cp.getCuerpo());
			servicioCorreo.enviarCorreo(correo);
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	

	@Override
	String guardar(boolean parcial) {
		if (getSemilleroActual().getFase().equals(3)) {
			getSemilleroActual().setFase(4);
		}
		servicioGeneral.guardarObjeto(getSemilleroActual());
		if (!parcial) {
			if (validarForm()) {
				if (idSolicitudPlanTrabajo != null) {
					SemilleroSolicitud s = (SemilleroSolicitud) servicioGeneral.obtenerObjetos(SemilleroSolicitud.class,
							"from SemilleroSolicitud s where s.id=" + idSolicitudPlanTrabajo).get(0);
					s.setDetalle("Aplicado: " + getToday().toString());
					servicioGeneral.guardarObjeto(s);
				}
				if (idSolicitudContenido != null) {
					for (SemilleroSolicitud s : semilleroActual.getSolicitudes()) {
						if (s.getId().equals(idSolicitudContenido)) {
							if (solicitudAprobadaMetodologia) {
								s.setDetalle(s.getDetalle().replace("Metodología", ""));
							}
							if (solicitudAprobadaResultados) {
								s.setDetalle(s.getDetalle().replace("Resultados Esperados", ""));
							}
							s.setDetalle(s.getDetalle().replace("--", "-"));
							if (s.getDetalle().endsWith("-")) {
								s.setDetalle(s.getDetalle().substring(0, s.getDetalle().length() - 1));
							}
							solicitudAprobadaPlanTrabajo = false;
							solicitudAprobadaMetodologia = false;
							solicitudAprobadaResultados = false;
						}
					}
				}
				servicioGeneral.guardarObjeto(getSemilleroActual());
				return irArchivosEnviar(getSemilleroActual().getId());
			} else {
				return "";
			}
		} else {
			generarMsg(1, "Semillero registrado correctamente con el ID " + getSemilleroActual().getId());
			return "";
		}
	}

	@Override
	boolean validarForm() {
		isOK = true;
		if (getActividades().isEmpty()) {
			isOK = false;
			generarMsg(2, "Por favor, ingrese al menos una actividad.");
		}
		if (esCadenaVacia(getSemilleroActual().getMetodologia())) {
			isOK = false;
			generarMsg(2, "Por favor, indique la metodología del semillero.");
		}
		if (getSemilleroActual().getResultados().isEmpty()) {
			isOK = false;
			generarMsg(2, "Por favor, ingrese al menos un resultado esperado.");
		}
		return isOK;
	}

	public void agregarResultado() {
		if (esCadenaVacia(resultadoActual.getResultado())) {
			generarMsg(2, "Por favor, indique el resultado a añadir.");
		} else {
			for (SemilleroResultado sr : semilleroActual.getResultados()) {
				if (sr.getResultado().equals(resultadoActual.getResultado())) {
					generarMsg(2, "El resultado ya se encuentra vinculado al semillero.");
					return;
				}
			}
			resultadoActual.setSemillero(getSemilleroActual());
			semilleroActual.getResultados().add(resultadoActual);
			servicioGeneral.insertarObjeto(resultadoActual);
		}
		resultadoActual = new SemilleroResultado();
	}

	public void eliminarResultado() {
		try {
			servicioGeneral.eliminarObjeto(resultadoEliminar);
			semilleroActual.getResultados().remove(resultadoEliminar);
		}catch (Exception e) {
			e.printStackTrace();
			generarMsg(2,"No es posible eliminar el resultado si ya se ha vinculado a informes.");
			return;
		}
	}

	public void agregarActividad() {
		if (validarActividad()) {
			actividadActual.setFechaInicio(fechaInicio);
			actividadActual.setDuracion(Integer.parseInt(duracion));
			actividadActual.setFechaRegistro(getToday());
			for (SemilleroIntegrante item : getSemilleroActual().getListaIntegrantes()) {
				if (item.getId().equals(actividadActual.getResponsable().getId())) {
					actividadActual.setResponsable(item);
					servicioGeneral.guardarObjeto(actividadActual);
					item.getPlanTrabajo().add(actividadActual);
					break;
				}
			}
			cargarActividades();
			actividadActual = new SemilleroActividad();
			fechaInicio = null;
			duracion = "";
		}
	}
	
	/**
	 * Guarda el responsable seleccionado para una actividad específica dentro del semillero actual.
	 * 
	 * <p>Este método realiza los siguientes pasos:</p>
	 * <ol>
	 *   <li>Valida que la actividad seleccionada y el ID del nuevo responsable no sean nulos.</li>
	 *   <li>Busca el objeto completo del responsable dentro de la lista de integrantes actuales.</li>
	 *   <li>Asigna el nuevo responsable a la actividad seleccionada.</li>
	 *   <li>Guarda la actividad actualizada en la base de datos.</li>
	 *   <li>Recarga el semillero completo desde la base de datos para obtener datos actualizados.</li>
	 *   <li>Reconstruye la lista de actividades e integrantes para reflejar los cambios.</li>
	 *   <li>Limpia la selección de actividad para dejarla lista para un nuevo registro.</li>
	 * </ol>
	 * 
	 * <p>Este método asume que el ID del semillero actual está almacenado en sesión bajo la clave "semillero".</p>
	 */
	public void guardarResponsable() {
	    if (actividadSeleccionada != null && actividadSeleccionada.getResponsable() != null) {
	        Integer idResponsable = actividadSeleccionada.getResponsable().getId();

	        // Buscar el objeto completo del nuevo responsable en la lista de integrantes
	        for (SemilleroIntegrante item : getSemilleroActual().getListaIntegrantes()) {
	            if (item.getId().equals(idResponsable)) {
	                actividadSeleccionada.setResponsable(item);
	                break;
	            }
	        }

	        // Guardar la actividad con el nuevo responsable en la base de datos
	        servicioGeneral.guardarObjeto(actividadSeleccionada);

	        // Recargar el semillero completo desde la base de datos
	        Semillero semilleroActualizado = (Semillero) servicioGeneral.obtenerObjetos(
	            Semillero.class,
	            "from Semillero s where s.id = " + (Integer) sesion.getAttribute("semillero")
	        ).get(0);
	        setSemilleroActual(semilleroActualizado);

	        // Reconstruir listas de actividades e integrantes actualizadas
	        cargarActividades();

	        // Limpiar la selección actual para futuras ediciones
	        actividadSeleccionada = new SemilleroActividad();
	    }
	}

	
	public void modificarResponsable() {
		
	}
	

	public void eliminarActividad() {
		actividadEliminar.getResponsable().getPlanTrabajo().remove(actividadEliminar);
		servicioGeneral.eliminarObjeto(actividadEliminar);
		cargarActividades();
	}

	private boolean validarActividad() {
		boolean valido = true;
		if (esCadenaVacia(getActividadActual().getDescripcion())) {
			valido = false;
			generarMsg(2, "Por favor, indique la descrpción de la actividad.");
		}
		if (getFechaInicio() == null) {
			valido = false;
			generarMsg(2, "Por favor, indique la fecha de inicio de la actividad.");
		}
		if (esCadenaVacia(getDuracion())) {
			valido = false;
			generarMsg(2, "Por favor, indique la duración de la actividad.");
		} else if (!cadenaEsValorNumerico(getDuracion())) {
			valido = false;
			generarMsg(2, "La duración de la actividad no tiene un valor válido.");
		} else {
			Integer duracion = Integer.parseInt(getDuracion());
			if (duracion <= 0) {
				valido = false;
				generarMsg(2, "La duración de la actividad debe ser mayor o igual a 1 mes.");
			} else if (duracion > 100) {
				valido = false;
				generarMsg(2, "La duración de la actividad debe ser menor o igual a 100 meses.");
			}
		}
		if (getActividadActual().getResponsable().getId().equals(0)) {
			valido = false;
			generarMsg(2, "Por favor, indique el responsable de la actividad.");
		}
		return valido;
	}

	public Semillero getSemilleroActual() {
		return semilleroActual;
	}

	public void setSemilleroActual(Semillero semilleroActual) {
		this.semilleroActual = semilleroActual;
	}

	public ArrayList<SelectItem> getIntegrantesItems() {
		return integrantesItems;
	}

	public void setIntegrantesItems(ArrayList<SelectItem> integrantesItems) {
		this.integrantesItems = integrantesItems;
	}

	public SemilleroActividad getActividadActual() {
		return actividadActual;
	}

	public void setActividadActual(SemilleroActividad actividadActual) {
		this.actividadActual = actividadActual;
	}

	public ArrayList<SemilleroActividad> getActividades() {
		return actividades;
	}

	public void setActividades(ArrayList<SemilleroActividad> actividades) {
		this.actividades = actividades;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getDuracion() {
		return duracion;
	}

	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}

	public SemilleroActividad getActividadEliminar() {
		return actividadEliminar;
	}

	public void setActividadEliminar(SemilleroActividad actividadEliminar) {
		this.actividadEliminar = actividadEliminar;
	}

	public SemilleroResultado getResultadoActual() {
		return resultadoActual;
	}

	public void setResultadoActual(SemilleroResultado resultadoActual) {
		this.resultadoActual = resultadoActual;
	}

	public SemilleroResultado getResultadoEliminar() {
		return resultadoEliminar;
	}

	public void setResultadoEliminar(SemilleroResultado resultadoEliminar) {
		this.resultadoEliminar = resultadoEliminar;
	}

	public boolean isSolicitudAprobadaPlanTrabajo() {
		return solicitudAprobadaPlanTrabajo;
	}

	public void setSolicitudAprobadaPlanTrabajo(boolean solicitudAprobadaPlanTrabajo) {
		this.solicitudAprobadaPlanTrabajo = solicitudAprobadaPlanTrabajo;
	}

	public boolean isSolicitudAprobadaMetodologia() {
		return solicitudAprobadaMetodologia;
	}

	public void setSolicitudAprobadaMetodologia(boolean solicitudAprobadaMetodologia) {
		this.solicitudAprobadaMetodologia = solicitudAprobadaMetodologia;
	}

	public boolean isSolicitudAprobadaResultados() {
		return solicitudAprobadaResultados;
	}

	public void setSolicitudAprobadaResultados(boolean solicitudAprobadaResultados) {
		this.solicitudAprobadaResultados = solicitudAprobadaResultados;
	}

	public Date getFechaCreacionActividad() {
		return fechaCreacionActividad;
	}

	public void setFechaCreacionActividad(Date fechaCreacionActividad) {
		this.fechaCreacionActividad = fechaCreacionActividad;
	}

	public SemilleroActividad getActividadSeleccionada() {
		return actividadSeleccionada;
	}

	public void setActividadSeleccionada(SemilleroActividad actividadSeleccionada) {
		this.actividadSeleccionada = actividadSeleccionada;
	}

}
