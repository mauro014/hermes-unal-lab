package co.edu.unal.hermes.vista.laboratorios;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;

import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PersonaRol;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.laboratorios.ArchivoLaboratorio;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleEquipos;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * @author dgbenitezc
 * 
 */
public class ManejadorInformacionBasicaEquipo extends ManejadorBase {

	public LaboratorioDetalleEquipos equipo;
	public Boolean soloLectura;
	protected SelectItem[] estadoFisicoSI;
	protected SelectItem[] mantenimientoSI;
	protected SelectItem[] calibracionSI;
	private SelectItem[] instrumentoMedicionSelectItem;
	private SelectItem[] requiereMantenimientoSelectItem;
	
	public Boolean danadoInicio;
	public Boolean perdidoInicio;
	
	public String rolSeleccionadoLabs = "";
	public Boolean permitirDarBaja = false;
	private List<ArchivoLaboratorio> listaArchivos = new ArrayList<ArchivoLaboratorio>();
	private List<ArchivoLaboratorio> listaArchivosEliminados = new ArrayList<ArchivoLaboratorio>();
	private ArchivoLaboratorio archivoLaboratorioSeleccionado;

	public ManejadorInformacionBasicaEquipo() {
		limpiarSesion();
		equipo = (LaboratorioDetalleEquipos) sesion.getAttribute("equipoSeleccionado");
		Boolean attEdicion = (Boolean) sesion.getAttribute("editarEquipo");
		if (attEdicion == null) {
			attEdicion = false;
		}
		soloLectura = !attEdicion;
		estadoFisicoSI = servicioGeneral.retornaSelectItemArregloDeHijosDeTipos(Tipos.TIPOS_ESTADOS);
		mantenimientoSI = servicioGeneral.retornaSelectItemArregloDeHijosDeTipos(Tipos.FRECUENCIA_MANTENIMIENTO);
		calibracionSI = servicioGeneral.retornaSelectItemArregloDeHijosDeTipos(Tipos.FRECUENCIA_MANTENIMIENTO);
		
		instrumentoMedicionSelectItem = new SelectItem[2];
		instrumentoMedicionSelectItem[0] = new SelectItem(false, "NO");
		instrumentoMedicionSelectItem[1] = new SelectItem(true, "SI");
		
		requiereMantenimientoSelectItem = new SelectItem[2];
		requiereMantenimientoSelectItem[0] = new SelectItem(false, "NO");
		requiereMantenimientoSelectItem[1] = new SelectItem(true, "SI");
		
		danadoInicio = esNulo(equipo.getDanado()) || equipo.getDanado() == false ? false : true;
		perdidoInicio = esNulo(equipo.getPerdido()) || equipo.getPerdido() == false ? false : true;
		
		rolSeleccionadoLabs = (String) sesion.getAttribute("rolSeleccionadoLabs");
		List<String> rolesPermisoDadoBaja = Arrays.asList("LS");
		permitirDarBaja = rolesPermisoDadoBaja.contains(rolSeleccionadoLabs);
		
		//ARCHIVOS DADO DE BAJA
		if(!esNulo(equipo.getId())) {
			String hql1 = "FROM ArchivoLaboratorio AL WHERE AL.idDetalle = '"+ equipo.getId()
			+ "' AND AL.tipoArchivo.id in ("+Tipos.TIPO_ARCHIVO_LAB_EQUIPOS_DADO_BAJA+") ORDER BY AL.id DESC";
			listaArchivos = servicioGeneral.obtenerObjetos(ArchivoLaboratorio.class, hql1);
		}
	}
	
	public void subirArchivo(FileUploadEvent event) {
		Tipos tipoArchivoSelReglamento = obtenerTipoXid(Tipos.TIPO_ARCHIVO_LAB_EQUIPOS_DADO_BAJA);
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

	public void limpiarSesion() {
		sesion.removeAttribute("manejadorInformacionBasicaEquipo");
		sesion.removeAttribute("ManejadorLaboratoriosEquipos");
	}

	public String volverLaboratorio() {
		limpiarSesion();
		return "laboratorioEquipos";
	}

	public String guardarEquipo() {
		if (validar()) {
			personaActual = (Persona) sesion.getAttribute("persona");
			equipo.setDocumentoPersonaRegistro(personaActual.getId().getDocumento());
			equipo.setTipoDocumentoPersonaRegistro(personaActual.getId().getTipoDocumento());
			equipo.setFechaRegistro(new Date());	
			Boolean esEquipoNuevo = equipo.getId() == null ? true : false;
			
			try {
				servicioGeneral.guardarObjeto(equipo);
				guardarLogEquipos(esEquipoNuevo,true,equipo);
				
				for (ArchivoLaboratorio al : listaArchivos) {
					Long id = al.getId();
					al.setIdDetalle(equipo.getId());
					try {
						servicioGeneral.insertarObjetoConIdLong(al, id);
					} catch (Exception e) {
					}
				}

				for (ArchivoLaboratorio ale : listaArchivosEliminados)
					eliminarArchivoLaboratorios(ale);
				
				if(esEquipoNuevo)
					enviarNotificacionCreacionEquipo();
				
				if(!esNulo(danadoInicio) && danadoInicio.equals(false) && equipo.getDanado().equals(true))
					enviarAlertaDanoPerdida("DAÑADO");
				
				if(!esNulo(perdidoInicio) && perdidoInicio.equals(false) && equipo.getPerdido().equals(true))
					enviarAlertaDanoPerdida("PERDIDO");
					
				sesion.removeAttribute("ManejadorLaboratoriosEquipos");
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			return volverLaboratorio();
		} else {
			return "";
		}
	}
	
	public void eliminarArchivoLaboratorios(
			ArchivoLaboratorio archivoLaboratorioEliminar) {
		try {
			Long id = archivoLaboratorioEliminar.getId();
			eliminarArchivoLaboratorio(id);
			servicioGeneral.eliminarObjeto(archivoLaboratorioEliminar);
		} catch (Exception e) {
			
		}
	}
	
	public void enviarAlertaDanoPerdida(String tipo)
	{
		if(!esNulo(equipo.getResponsableEmail()) && !equipo.getResponsableEmail().equals(""))
		{
			CorreoPlantilla correoPlantilla = servicioCorreo.obtenerPlantillaCorreoCompleta(CorreoPlantilla.CORREO_NOTIFICACION_EQUIPO_DANADO_PERDIDO);
	
			Correo correo = new Correo();
			String cuerpo = correoPlantilla.getCuerpo();
			String asunto = correoPlantilla.getAsunto();
	
			Persona coorinador = servicioGeneral.obtenerCoordinadorLaboratorio(equipo.getLaboratorio().getId());
	
			correo.setOrigen(Correo.CORREO_HERMES);
			correo.adicionarDireccion(equipo.getResponsableEmail());
	
			asunto = asunto.replaceAll("<<TIPO>>", tipo);
			asunto = asunto.replaceAll("<<ID_LABORATORIO>>", equipo.getLaboratorio().getId().toString());
			asunto = asunto.replaceAll("<<NOMBRE_LABORATORIO>>", equipo.getLaboratorio().getNombre());
	
			cuerpo = cuerpo.replaceAll("<<COORDINADOR_LABORATORIO>>", coorinador.getNombreCompleto());
			cuerpo = cuerpo.replaceAll("<<EMAIL_COORDINADOR_LABORATORIO>>", coorinador.getEmail());
			cuerpo = cuerpo.replaceAll("<<ID_LABORATORIO>>", equipo.getLaboratorio().getId().toString());
			cuerpo = cuerpo.replaceAll("<<NOMBRE_LABORATORIO>>", equipo.getLaboratorio().getNombre());
			cuerpo = cuerpo.replaceAll("<<NOMBRE_EQUIPO>>", equipo.getEquipo());
			cuerpo = cuerpo.replaceAll("<<PLACA_EQUIPO>>", equipo.getPlaca());
			cuerpo = cuerpo.replaceAll("<<TIPO>>", tipo);
	
			correo.setAsunto(asunto);
			correo.setCuerpo(cuerpo);
			servicioCorreo.enviarCorreo(correo);
		}
	}
	
	public void enviarNotificacionCreacionEquipo()
	{
		CorreoPlantilla correoPlantilla = servicioCorreo.obtenerPlantillaCorreoCompleta(CorreoPlantilla.CORREO_NOTIFICACION_EQUIPO_ASOCIADO);

		Correo correo = new Correo();
		String cuerpo = correoPlantilla.getCuerpo();
		String asunto = correoPlantilla.getAsunto();

		Persona coorinador = servicioGeneral.obtenerCoordinadorLaboratorio(equipo.getLaboratorio().getId());
		String rol = null;
		InvestigadorInterno ii = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());
		
		List<PersonaRol> listaPr = servicioGeneral.obtenerPersonaRolXIdPersonaLaboratorios(ii.getId().getTipoDocumento(),ii.getId().getDocumento());
		if(listaPr != null)
			rol = servicioPersona.obtenerRol(listaPr.get(0).getNombre()).getNombre();

		correo.setOrigen(Correo.CORREO_HERMES);
		correo.adicionarDireccion(coorinador.getEmail());

		asunto = asunto.replaceAll("<<PLACA_EQUIPO>>", equipo.getPlaca());
		asunto = asunto.replaceAll("<<ID_LABORATORIO>>", equipo.getLaboratorio().getId().toString());
		asunto = asunto.replaceAll("<<NOMBRE_LABORATORIO>>", equipo.getLaboratorio().getNombre());

		cuerpo = cuerpo.replaceAll("<<NOMBRE_COORDINADOR>>", coorinador.getNombreCompleto());
		cuerpo = cuerpo.replaceAll("<<NOMBRE_EQUIPO>>", equipo.getEquipo());
		cuerpo = cuerpo.replaceAll("<<PLACA_EQUIPO>>", equipo.getPlaca());
		cuerpo = cuerpo.replaceAll("<<NOMBRE_LABORATORIO>>", equipo.getLaboratorio().getNombre());
		
		cuerpo = cuerpo.replaceAll("<<NOMBRE_USUARIO_SESION>>", personaActual.getNombreCompleto());
		cuerpo = cuerpo.replaceAll("<<NOMBRE_ROL>>", rol);
		cuerpo = cuerpo.replaceAll("<<NOMBRE_FACULTAD>>", ii.getDependencia().getFacultad().getNombre());
		cuerpo = cuerpo.replaceAll("<<NOMBRE_SEDE>>", ii.getDependencia().getFacultad().getSede().getNombre());

		correo.setAsunto(asunto);
		correo.setCuerpo(cuerpo);
		servicioCorreo.enviarCorreo(correo);

	}


	public Boolean validar() {
		Boolean validar = true;
		if (equipo.getDadoDeBaja()) {
			if(esCadenaVacia(equipo.getMotivoDadoBaja())) {
				mensajeError("Debe ingresar el motivo por el cual el equipo fue dado de baja");
				validar=false;
			}
		}
		return validar;
	}

	/**
	 * @return the equipo
	 */
	public LaboratorioDetalleEquipos getEquipo() {
		return equipo;
	}

	/**
	 * @param equipo
	 *            the equipo to set
	 */
	public void setEquipo(LaboratorioDetalleEquipos equipo) {
		this.equipo = equipo;
	}

	/**
	 * @return the soloLectura
	 */
	public Boolean getSoloLectura() {
		return soloLectura;
	}

	/**
	 * @return the estadoFisicoSI
	 */
	public SelectItem[] getEstadoFisicoSI() {
		return estadoFisicoSI;
	}

	/**
	 * @return the mantenimientoSI
	 */
	public SelectItem[] getMantenimientoSI() {
		return mantenimientoSI;
	}

	/**
	 * @return the calibracionSI
	 */
	public SelectItem[] getCalibracionSI() {
		return calibracionSI;
	}

	public SelectItem[] getInstrumentoMedicionSelectItem() {
		return instrumentoMedicionSelectItem;
	}

	public void setInstrumentoMedicionSelectItem(
			SelectItem[] instrumentoMedicionSelectItem) {
		this.instrumentoMedicionSelectItem = instrumentoMedicionSelectItem;
	}

	public SelectItem[] getRequiereMantenimientoSelectItem() {
		return requiereMantenimientoSelectItem;
	}

	public void setRequiereMantenimientoSelectItem(
			SelectItem[] requiereMantenimientoSelectItem) {
		this.requiereMantenimientoSelectItem = requiereMantenimientoSelectItem;
	}

	public Boolean getDanadoInicio() {
		return danadoInicio;
	}

	public void setDanadoInicio(Boolean danadoInicio) {
		this.danadoInicio = danadoInicio;
	}

	public Boolean getPerdidoInicio() {
		return perdidoInicio;
	}

	public void setPerdidoInicio(Boolean perdidoInicio) {
		this.perdidoInicio = perdidoInicio;
	}

	public void setSoloLectura(Boolean soloLectura) {
		this.soloLectura = soloLectura;
	}

	public void setEstadoFisicoSI(SelectItem[] estadoFisicoSI) {
		this.estadoFisicoSI = estadoFisicoSI;
	}

	public void setMantenimientoSI(SelectItem[] mantenimientoSI) {
		this.mantenimientoSI = mantenimientoSI;
	}

	public void setCalibracionSI(SelectItem[] calibracionSI) {
		this.calibracionSI = calibracionSI;
	}

	public String getRolSeleccionadoLabs() {
		return rolSeleccionadoLabs;
	}

	public void setRolSeleccionadoLabs(String rolSeleccionadoLabs) {
		this.rolSeleccionadoLabs = rolSeleccionadoLabs;
	}

	public Boolean getPermitirDarBaja() {
		return permitirDarBaja;
	}

	public void setPermitirDarBaja(Boolean permitirDarBaja) {
		this.permitirDarBaja = permitirDarBaja;
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

}
