package co.edu.unal.hermes.vista.laboratorios;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;

import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PersonaRol;
import co.edu.unal.hermes.modelo.Rol;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.laboratorios.ArchivoLaboratorio;
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioActividadEquipoInterfazAlerta;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioAreasSecundariasOCDE;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleEnsayosServicios;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleEquipos;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioSolicitud;
import co.edu.unal.hermes.modelo.laboratorios.SolicitudLaboratorios;
import co.edu.unal.hermes.modelo.servicios.imp.ServicioCorreo;
import co.edu.unal.hermes.seguridad.autenticacion.Usuario;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * @author dgbenitezc
 * 
 */
public class ManejadorEliminarEquipoHojaDeVida extends ManejadorLaboratoriosEquipos {

	public LaboratorioDetalleEquipos equipoSeleccionado;
	public Boolean solicitudCoordinador;
	public Boolean revisionDLS;
	public Boolean consultarSolicitud;
	public LaboratorioSolicitud solicitud;
	private Boolean soloLectura;	
	private SelectItem[] listaLaboratorios;
	private Long idLabDestino;
	private String procesoRevision;
	Laboratorio labOriginal;
	private SelectItem[] listaMotivos;
	
	private List<ArchivoLaboratorio> listaArchivos = new ArrayList<ArchivoLaboratorio>();
	private List<ArchivoLaboratorio> listaArchivosEliminados = new ArrayList<ArchivoLaboratorio>();
	private ArchivoLaboratorio archivoLaboratorioSeleccionado;

	public ManejadorEliminarEquipoHojaDeVida() {
		solicitudSeleccionada = (LaboratorioSolicitud) sesion.getAttribute("solicitudSeleccionada");
		equipoSeleccionado = (LaboratorioDetalleEquipos) sesion.getAttribute("equipoSeleccionado");
		solicitudCoordinador = (Boolean) sesion.getAttribute("solicitudCoordinador");
		revisionDLS = (Boolean) sesion.getAttribute("revisionDLS");
		consultarSolicitud = (Boolean) sesion.getAttribute("consultarSolicitud");
		cargarListas();
		inicializarSolicitud();
		labOriginal = new Laboratorio();
	}
	
	public void inicializarSolicitud(){
		
		if(solicitudCoordinador) {
			solicitud = new LaboratorioSolicitud();
			solicitud.setPersona(personaActual);
			solicitud.setEquipo(equipoSeleccionado);
			solicitud.setLaboratorio(laboratorioActual);
			solicitud.setFechaRegistro(getToday());
			solicitud.setFechaUltimoCambioEstado(getToday());
		}
		
		if(revisionDLS) {
			solicitud = equipoSeleccionado.getSolicitudEliminarActiva();
			//ARCHIVOS DADO DE BAJA
			String hql1 = "FROM ArchivoLaboratorio AL WHERE AL.solicitud = '"+ solicitud.getId()
					+ "' AND AL.tipoArchivo.id in ("
					+ Tipos.TIPOS_ARCHIVOS_SOLICITUD_LABORATORIOS_Soporte_desvincular_equipo + "," + Tipos.TIPOS_ARCHIVOS_SOLICITUD_LABORATORIOS_Soporte_desvincular_equipo_sede
					+ ") ORDER BY AL.id DESC";
			listaArchivos = servicioGeneral.obtenerObjetos(ArchivoLaboratorio.class, hql1);
		}
		
		if(consultarSolicitud) {
			solicitud = solicitudSeleccionada;
			//ARCHIVOS DADO DE BAJA
			String hql1 = "FROM ArchivoLaboratorio AL WHERE AL.solicitud = '"+ solicitud.getId()
					+ "' AND AL.tipoArchivo.id in ("
					+ Tipos.TIPOS_ARCHIVOS_SOLICITUD_LABORATORIOS_Soporte_desvincular_equipo + "," + Tipos.TIPOS_ARCHIVOS_SOLICITUD_LABORATORIOS_Soporte_desvincular_equipo_sede
					+ ") ORDER BY AL.id DESC";
			listaArchivos = servicioGeneral.obtenerObjetos(ArchivoLaboratorio.class, hql1);
		}
	}
	
	public void cargarListas() {
		listaLaboratorios = servicioGeneral.selectItemListaLaboratoriosActivos();
		listaMotivos = servicioGeneral.selectItemHijosDeTiposValorObjeto(Tipos.TIPOS_SOL_LAB_MOTIVO);
	}
	
	public Boolean validar() {
		Boolean validar = true;
		if(esCadenaVacia(solicitud.getDescripcion())) {
			mensajeError("La descripción de la solicitud es un campo obligatorio");
			validar=false;
		}
		return validar;
	}
	
	public String guardarSolicitud() {
		if (validar()) {
			Laboratorio labDestino = null;
			
			if(!esNulo(idLabDestino)) {
				if(!idLabDestino.equals(0L)) {
					List<Laboratorio> listaLaboratorioSel = servicioGeneral.obtenerObjetoXID(Laboratorio.class, idLabDestino.toString());
					if(listaLaboratorioSel != null)
						labDestino = listaLaboratorioSel.get(0);
				}
			}
			
			Tipos tipoTipoSol = obtenerTipoXid(Tipos.TIPOS_TIPO_SOLICITUD_LABORATORIO_eliminar_equipo);
			Tipos tipoEstadoSol = obtenerTipoXid(Tipos.TIPOS_ESTADO_SOLICITUD_LABORATORIO_Enviada);
			
			solicitud.setTipo(tipoTipoSol);
			solicitud.setEstado(tipoEstadoSol);
			solicitud.setLaboratorioDestinoEquipo(labDestino);
			
			try {
				servicioGeneral.guardarObjeto(solicitud);
				equipoSeleccionado.setSolicitudEliminarActiva(solicitud);
				servicioGeneral.guardarObjeto(equipoSeleccionado);
				
				for (ArchivoLaboratorio al : listaArchivos) {
					Long id = al.getId();
					al.setSolicitud(solicitud);
					try {
						servicioGeneral.insertarObjetoConIdLong(al, id);
					} catch (Exception e) {
					}
				}

				for (ArchivoLaboratorio ale : listaArchivosEliminados)
					eliminarArchivoLaboratorios(ale);
				
				mensajeInfo("Su solicitud ha sido enviada correctamente con el código "+solicitud.getId()+". "
						+ "Una notificación ha sido enviada a la Dirección de Laboratorios de la sede a la cual está asociado el laboratorio así que cualquier inquietud adicional "
						+ "puede solucionarlo directamente en dicha dependencia.");
				enviarNotificacionRegistroSolicitud();
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		return "";
		
		} else {
			return "";
		}
	}
	
	public Boolean validarAprobar() {
		Boolean validar = true;
		
		if(esNulo(procesoRevision)) {
			mensajeError("Debe seleccionar si desea desvincular o trasladar el equipo");
			validar = false;
		}
		
		if(procesoRevision.equals("D") && esCadenaVacia(solicitud.getObservaciones())) {
			mensajeError("Debe indicar el motivo por que no se translada el equipo a otro laboratorio");
			validar = false;
		}
		
		// Validar
		if (procesoRevision.equals("T") && (idLabDestino.equals(0L) || idLabDestino == null)) {
			mensajeError("Debe seleccionar el laboratorio al cual desea trasladar el equipo");
			validar = false;
		}
		
		return validar;
	}
	
	public void aprobarSolicitud(){
		if (validarAprobar()) 
		{
			labOriginal = equipoSeleccionado.getLaboratorio();
			Laboratorio labDestino = servicioGeneral.obtenerLaboratorioXID(idLabDestino);
			
			Tipos tipoEstadoSol = obtenerTipoXid(Tipos.TIPOS_ESTADO_SOLICITUD_LABORATORIO_Aprobada);
			
			//Se actualizan datos de la solicitud
			solicitud.setEstado(tipoEstadoSol);
			solicitud.setFechaUltimoCambioEstado(getToday());
			solicitud.setFechaRespuesta(getToday());
			solicitud.setPersonaRevision(personaActual);
			
			//Se actualizan datos del equipo
			equipoSeleccionado.setSolicitudEliminarActiva(null);
			if(procesoRevision.equals("D"))
				equipoSeleccionado.setLaboratorio(null);
			else if(procesoRevision.equals("T"))
				equipoSeleccionado.setLaboratorio(labDestino);
			
			try
			{
				servicioGeneral.guardarObjeto(solicitud);
				servicioGeneral.guardarObjeto(equipoSeleccionado);
				for (ArchivoLaboratorio al : listaArchivos) {
					Long id = al.getId();
					al.setSolicitud(solicitud);
					try {
						servicioGeneral.insertarObjetoConIdLong(al, id);
					} catch (Exception e) {
					}
				}

				for (ArchivoLaboratorio ale : listaArchivosEliminados)
					eliminarArchivoLaboratorios(ale);
				
				mensajeInfo("La solicitud  "+solicitud.getId()+" fue aprobada. Una notificación ha sido enviada al Coordinador del laboratorio del cual se envió la solicitud. "
						+ "Además, se envió notificación a el Coordinador del laboratorio al cual se translada el equipo (Si aplica)");
				if(procesoRevision.equals("D"))
					enviarNotificacionDesvincularEquipo();
				else if(procesoRevision.equals("T"))
					enviarNotificacionTransladarEquipo();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
	}
	
	public void rechazarSolicitud(){
		
		Tipos tipoEstadoSol = obtenerTipoXid(Tipos.TIPOS_ESTADO_SOLICITUD_LABORATORIO_Rechazada);
		
		//Se actualizan datos de la solicitud
		solicitud.setEstado(tipoEstadoSol);
		solicitud.setFechaUltimoCambioEstado(getToday());
		solicitud.setFechaRespuesta(getToday());
		solicitud.setPersonaRevision(personaActual);
		
		equipoSeleccionado.setSolicitudEliminarActiva(null);
		
		try {
			servicioGeneral.guardarObjeto(solicitud);
			servicioGeneral.guardarObjeto(equipoSeleccionado);
			
			for (ArchivoLaboratorio al : listaArchivos) {
				Long id = al.getId();
				al.setSolicitud(solicitud);
				try {
					servicioGeneral.insertarObjetoConIdLong(al, id);
				} catch (Exception e) {
				}
			}

			for (ArchivoLaboratorio ale : listaArchivosEliminados)
				eliminarArchivoLaboratorios(ale);
			
			mensajeInfo("La solicitud  "+solicitud.getId()+" ha sido rechazada. Una notificación ha sido enviada al Coordinador del laboratorio del cual se envió la solicitud. ");
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void subirArchivo(FileUploadEvent event) {			
		Tipos tipoArchivoSelReglamento = obtenerTipoXid(Tipos.TIPOS_ARCHIVOS_SOLICITUD_LABORATORIOS_Soporte_desvincular_equipo);
		if(revisionDLS) tipoArchivoSelReglamento = obtenerTipoXid(Tipos.TIPOS_ARCHIVOS_SOLICITUD_LABORATORIOS_Soporte_desvincular_equipo_sede);
		ArchivoLaboratorio archivoNuevo = subirArchivoLaboratorios(event,tipoArchivoSelReglamento);
		if (archivoNuevo != null)
			listaArchivos.add(archivoNuevo);
		else
			mensajeError("Error al subir archivo.");
	}
	
	public void descargarArchivo() {
		descargarArchivoLaboratorios(archivoLaboratorioSeleccionado);
	}
	
	public void eliminarArchivo() {
		listaArchivos.remove(archivoLaboratorioSeleccionado);
		listaArchivosEliminados.add(archivoLaboratorioSeleccionado);
	}
	
	public void enviarNotificacionRegistroSolicitud()
	{
		CorreoPlantilla correoPlantilla = servicioCorreo.obtenerPlantillaCorreoCompleta(CorreoPlantilla.CORREO_NOTIFICACION_SOLICITUD_DESVINCULAR_EQUIPO_DLS);

		Correo correo = new Correo();
		String cuerpo = correoPlantilla.getCuerpo();
		String asunto = correoPlantilla.getAsunto();

		Persona coorinador = servicioGeneral.obtenerCoordinadorLaboratorio(equipoSeleccionado.getLaboratorio().getId());
		
		List<Persona> listaPersonasDLS = servicioPersona.obtenerPersonasxRolIdxSedeId("LS",equipoSeleccionado.getLaboratorio().getSede().getId());
		
		Rol rolPersonaActual = servicioGeneral.obtenerRolPersonaLaboratorioxIDLab(laboratorioActual.getId(), solicitud.getPersona().getId().getTipoDocumento(), solicitud.getPersona().getId().getDocumento());

		correo.setOrigen(Correo.CORREO_HERMES);
		for (Persona persona : listaPersonasDLS) {
			if(!esNulo(persona.getEmail())){
				correo.adicionarDireccion(persona.getEmail());
			}
		}

		asunto = asunto.replaceAll("<<PLACA_EQUIPO>>", equipoSeleccionado.getPlaca());
		asunto = asunto.replaceAll("<<ID_LABORATORIO>>", equipoSeleccionado.getLaboratorio().getId().toString());
		asunto = asunto.replaceAll("<<NOMBRE_LABORATORIO>>", equipoSeleccionado.getLaboratorio().getNombre());
		
		cuerpo = cuerpo.replaceAll("<<PLACA_EQUIPO>>", equipoSeleccionado.getPlaca());
		cuerpo = cuerpo.replaceAll("<<ID_LABORATORIO>>", equipoSeleccionado.getLaboratorio().getId().toString());
		cuerpo = cuerpo.replaceAll("<<NOMBRE_LABORATORIO>>", equipoSeleccionado.getLaboratorio().getNombre());
		
		cuerpo = cuerpo.replaceAll("<<NOMBRE_PERSONA>>", solicitud.getPersona().getNombreCompleto());
		cuerpo = cuerpo.replaceAll("<<NOMBRE_ROL>>", rolPersonaActual.getNombre());

		correo.setAsunto(asunto);
		correo.setCuerpo(cuerpo);
		servicioCorreo.enviarCorreo(correo);
	}
	
	public String volverLaboratorio() {
		limpiarSesion();
		return "laboratorioEquipos";
	}
	
	public void limpiarSesion() {
		sesion.removeAttribute("ManejadorEliminarEquipoHojaDeVida");
		sesion.removeAttribute("ManejadorLaboratoriosEquipos");
	}
	
	public void enviarNotificacionDesvincularEquipo()
	{
		CorreoPlantilla correoPlantilla = servicioCorreo.obtenerPlantillaCorreoCompleta(CorreoPlantilla.CORREO_NOTIFICACION_EQUIPO_DESVINCULADO);

		Correo correo = new Correo();
		String cuerpo = correoPlantilla.getCuerpo();
		String asunto = correoPlantilla.getAsunto();

		Persona coorinador = servicioGeneral.obtenerCoordinadorLaboratorio(labOriginal.getId());

		correo.setOrigen(Correo.CORREO_HERMES);
		correo.adicionarDireccion(coorinador.getEmail());

		asunto = asunto.replaceAll("<<PLACA_EQUIPO>>", equipoSeleccionado.getPlaca());
		asunto = asunto.replaceAll("<<ID_LABORATORIO>>", labOriginal.getId().toString());
		asunto = asunto.replaceAll("<<NOMBRE_LABORATORIO>>", labOriginal.getNombre());

		cuerpo = cuerpo.replaceAll("<<NOMBRE_SEDE>>", labOriginal.getSede().getNombre());
		cuerpo = cuerpo.replaceAll("<<NOMBRE_EQUIPO>>", equipoSeleccionado.getEquipo());
		cuerpo = cuerpo.replaceAll("<<PLACA_EQUIPO>>", equipoSeleccionado.getPlaca());
		cuerpo = cuerpo.replaceAll("<<ID_LABORATORIO>>", labOriginal.getId().toString());
		cuerpo = cuerpo.replaceAll("<<NOMBRE_LABORATORIO>>", labOriginal.getNombre());

		correo.setAsunto(asunto);
		correo.setCuerpo(cuerpo);
		servicioCorreo.enviarCorreo(correo);
	}
	
	public void enviarNotificacionTransladarEquipo()
	{
		CorreoPlantilla correoPlantilla = servicioCorreo.obtenerPlantillaCorreoCompleta(CorreoPlantilla.CORREO_NOTIFICACION_EQUIPO_TRANSLADADO);

		Correo correo = new Correo();
		String cuerpo = correoPlantilla.getCuerpo();
		String asunto = correoPlantilla.getAsunto();

		Persona coorinador = servicioGeneral.obtenerCoordinadorLaboratorio(equipoSeleccionado.getLaboratorio().getId());

		correo.setOrigen(Correo.CORREO_HERMES);
		correo.adicionarDireccion(coorinador.getEmail());

		asunto = asunto.replaceAll("<<PLACA_EQUIPO>>", equipoSeleccionado.getPlaca());
		asunto = asunto.replaceAll("<<ID_LABORATORIO_DESTINO>>", equipoSeleccionado.getLaboratorio().getId().toString());
		asunto = asunto.replaceAll("<<NOMBRE_LABORATORIO_DESTINO>>", equipoSeleccionado.getLaboratorio().getNombre());

		cuerpo = cuerpo.replaceAll("<<NOMBRE_SEDE>>", equipoSeleccionado.getLaboratorio().getSede().getNombre());
		cuerpo = cuerpo.replaceAll("<<NOMBRE_EQUIPO>>", equipoSeleccionado.getEquipo());
		cuerpo = cuerpo.replaceAll("<<PLACA_EQUIPO>>", equipoSeleccionado.getPlaca());
		cuerpo = cuerpo.replaceAll("<<ID_LABORATORIO_DESTINO>>", equipoSeleccionado.getLaboratorio().getId().toString());
		cuerpo = cuerpo.replaceAll("<<NOMBRE_LABORATORIO_DESTINO>>", equipoSeleccionado.getLaboratorio().getNombre());

		correo.setAsunto(asunto);
		correo.setCuerpo(cuerpo);
		servicioCorreo.enviarCorreo(correo);
	}

	/**
	 * @return the soloLectura
	 */
	public Boolean getSoloLectura() {
		return soloLectura;
	}

	public LaboratorioDetalleEquipos getEquipoSeleccionado() {
		return equipoSeleccionado;
	}

	public void setEquipoSeleccionado(LaboratorioDetalleEquipos equipoSeleccionado) {
		this.equipoSeleccionado = equipoSeleccionado;
	}

	public void setSoloLectura(Boolean soloLectura) {
		this.soloLectura = soloLectura;
	}

	public LaboratorioSolicitud getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(LaboratorioSolicitud solicitud) {
		this.solicitud = solicitud;
	}

	public SelectItem[] getListaLaboratorios() {
		return listaLaboratorios;
	}

	public void setListaLaboratorios(SelectItem[] listaLaboratorios) {
		this.listaLaboratorios = listaLaboratorios;
	}

	public Long getIdLabDestino() {
		return idLabDestino;
	}

	public void setIdLabDestino(Long idLabDestino) {
		this.idLabDestino = idLabDestino;
	}

	public Boolean getSolicitudCoordinador() {
		return solicitudCoordinador;
	}

	public void setSolicitudCoordinador(Boolean solicitudCoordinador) {
		this.solicitudCoordinador = solicitudCoordinador;
	}

	public Boolean getRevisionDLS() {
		return revisionDLS;
	}

	public void setRevisionDLS(Boolean revisionDLS) {
		this.revisionDLS = revisionDLS;
	}

	public String getProcesoRevision() {
		return procesoRevision;
	}

	public void setProcesoRevision(String procesoRevision) {
		this.procesoRevision = procesoRevision;
	}

	public Laboratorio getLabOriginal() {
		return labOriginal;
	}

	public void setLabOriginal(Laboratorio labOriginal) {
		this.labOriginal = labOriginal;
	}

	public SelectItem[] getListaMotivos() {
		return listaMotivos;
	}

	public void setListaMotivos(SelectItem[] listaMotivos) {
		this.listaMotivos = listaMotivos;
	}

	public List<ArchivoLaboratorio> getListaArchivos() {
		return listaArchivos;
	}

	public void setListaArchivos(List<ArchivoLaboratorio> listaArchivos) {
		this.listaArchivos = listaArchivos;
	}

	public List<ArchivoLaboratorio> getListaArchivosEliminados() {
		return listaArchivosEliminados;
	}

	public void setListaArchivosEliminados(List<ArchivoLaboratorio> listaArchivosEliminados) {
		this.listaArchivosEliminados = listaArchivosEliminados;
	}

	public ArchivoLaboratorio getArchivoLaboratorioSeleccionado() {
		return archivoLaboratorioSeleccionado;
	}

	public void setArchivoLaboratorioSeleccionado(ArchivoLaboratorio archivoLaboratorioSeleccionado) {
		this.archivoLaboratorioSeleccionado = archivoLaboratorioSeleccionado;
	}

	public Boolean getConsultarSolicitud() {
		return consultarSolicitud;
	}

	public void setConsultarSolicitud(Boolean consultarSolicitud) {
		this.consultarSolicitud = consultarSolicitud;
	}
	
}