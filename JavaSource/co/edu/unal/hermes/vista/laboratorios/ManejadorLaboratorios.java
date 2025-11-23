package co.edu.unal.hermes.vista.laboratorios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Rol;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.laboratorios.ArchivoLaboratorio;
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioLogLaboratorios;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioSolicitudCreacionLaboratorio;
import co.edu.unal.hermes.modelo.laboratorios.PersonaLaboratorio;
import co.edu.unal.hermes.modelo.laboratorios.SolicitudLaboratorios;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * @author dgbenitezc
 */
public abstract class ManejadorLaboratorios extends ManejadorBase {

	public static int INFORMACION_GENERAL = 0;
	public static int RECURSO_HUMANO = 1;
	public static int RIESGOS = 2;
	public static int GESTION = 3;
	public static int EQUIPOS = 4;
	public static int INVESTIGACION = 5;
	public static int PROYECTOS = 6;
	public static int DOCENCIA = 7;
	public static int ENSAYOS_SERVICIOS = 8;
	public static int PRESUPUESTO = 9;
	public static int METRORED = 11;

	// IDENTIFICADOR DE LA FASE DEL MANEJADOR
	protected int idManejador;
	
	protected Laboratorio laboratorioActual;
	protected Boolean soloLectura;
	protected List<String> listaMensajesValidacion;
	protected Boolean esSolicitudLab;
	protected Boolean esLaboratorioNuevo;
	protected Boolean esLaboratorios;
	protected Boolean esLaboratoriosSede;
	protected Boolean esLaboratoriosFacultad;
	protected Boolean esLaboratoriosDepto;
	protected Boolean esCoordinadorLaboratorio;
	protected Boolean esPersonalLaboratorio;
	protected String rolSeleccionadoLabs = "";
	
	boolean esInvestigador;
	
//	private Boolean puedeModificarNombreLab = false;
//	private Boolean puedeModificarUbicacionLab = false;
//	private Boolean puedeModificarCoordinadorLab = false;
	private Boolean puedeModificarCamposProtegidos = false;
	private Boolean puedeModPermisosPersonal = false;
	
	protected String nombreOriginal;
	protected String edificioOriginal;
	protected String salonOriginal;
	protected String pisoOriginal;
	protected Persona coordinadorOriginal;
	protected Long tipoOriginal;
	
	protected List<PersonaLaboratorio> listaPersonasLaboratorio;
	
	private List<String> listaInfoFaltante = new ArrayList<String>();
	
	LaboratorioSolicitudCreacionLaboratorio solicitudLab;
	
	private Boolean personaActualEsCoordinador = false;

	public ManejadorLaboratorios() {
		laboratorioActual = (Laboratorio) sesion.getAttribute("Laboratorio");
		Boolean attSoloLectura = (Boolean) sesion.getAttribute("soloLectura");
		soloLectura = true;
		
		esLaboratorios = (Boolean) sesion.getAttribute("esLaboratorios");
		esLaboratoriosSede = (Boolean) sesion.getAttribute("esLaboratoriosSede");
		esLaboratoriosFacultad = (Boolean) sesion.getAttribute("esLaboratoriosFacultad");
		esLaboratoriosDepto = (Boolean) sesion.getAttribute("esLaboratoriosDepto");
		esCoordinadorLaboratorio = (Boolean) sesion.getAttribute("esCoordinadorLaboratorio");
		esPersonalLaboratorio = (Boolean) sesion.getAttribute("esPersonalLaboratorio");
		esInvestigador = (Boolean) sesion.getAttribute("esInvestigador");
		rolSeleccionadoLabs = (String) sesion.getAttribute("rolSeleccionadoLabs");
		
		if (attSoloLectura != null) {
			soloLectura = attSoloLectura;
		}

		if (laboratorioActual == null) {
			laboratorioActual = new Laboratorio();
			laboratorioActual.setEtapaRegistro(INFORMACION_GENERAL);
			sesion.setAttribute("Laboratorio", laboratorioActual);
			soloLectura = false;
		} else {
			nombreOriginal = laboratorioActual.getNombre();
			edificioOriginal = laboratorioActual.getEdificio().getId();
			salonOriginal = laboratorioActual.getSalon() == null ? "" : laboratorioActual.getSalon();
			pisoOriginal = laboratorioActual.getPiso() == null ? "" : laboratorioActual.getPiso();
			coordinadorOriginal = servicioGeneral.obtenerCoordinadorLaboratorio(laboratorioActual.getId());
			tipoOriginal = esNulo(laboratorioActual.getTipo()) ? 0L : laboratorioActual.getTipo().getId();
		}

		sesion.setAttribute("soloLectura", soloLectura);
		personaActual = (Persona) sesion.getAttribute("persona");

		// solicitud:
		solicitudLab = (LaboratorioSolicitudCreacionLaboratorio) sesion.getAttribute("solicitudLaboratorio");
		esSolicitudLab = (solicitudLab != null);
		
//		Modificar nombre y ubicacion del Laboratorio:
//		if (soloLectura) {	
//			puedeModificarNombreLab = false;
//			puedeModificarUbicacionLab = false;
//			puedeModificarCoordinadorLab = false;
//		} else {	
//			if(rolSeleccionadoLabs.equals("DL"))//Solo rol de DNIL puede modificar nombre, ubicacion y coordinador
//			{	
//				puedeModificarNombreLab = true;
//				puedeModificarUbicacionLab = true;
//				puedeModificarCoordinadorLab = true;
//			}
//		}
		
		convertirListaInformacionFaltante();
		
		puedeModificarCamposProtegidos = rolSeleccionadoLabs.equals("DL");
		
		if(!esNulo(coordinadorOriginal) && coordinadorOriginal.equals(personaActual))
			personaActualEsCoordinador = true;
		
		if(personaActualEsCoordinador || rolSeleccionadoLabs.equals("DL") || rolSeleccionadoLabs.equals("LS")) {
			puedeModPermisosPersonal = true;
		}
	}

	public void guardarLaboratorioActual(Integer formulario) {
		laboratorioActual.setFechaCreacionRegistro(new Date());
		laboratorioActual.setRegistroTipoDocumentoPersona(personaActual.getId().getTipoDocumento());
		laboratorioActual.setRegistroDocumentoPersona(personaActual.getId().getDocumento());
		
		if(laboratorioActual.getId() == null) 
			esLaboratorioNuevo = true;
		else
			esLaboratorioNuevo = false;
		try {
			servicioGeneral.guardarObjeto(laboratorioActual);
			guardarLogLaboratorios(formulario);
	    } catch (Exception x) {
	        x.printStackTrace();
	    }
	}
	
	public void guardarLogLaboratorios(Integer formulario)
	{		
		Tipos tipoLogNivel1 = null;
		Tipos tipoLogCambioNombre = null;
		Tipos tipoLogCambioUbicacion = null;
		
		if(esLaboratorioNuevo)
			tipoLogNivel1 = obtenerTipoXid(Tipos.TIPOS_OPERACION_LOG_LAB_CREACION);
		else
		{			
			switch(formulario) {
				case 0: 
					tipoLogNivel1 = obtenerTipoXid(Tipos.TIPOS_OPERACION_LOG_LAB_ACTUALIZACION_inf_general);
					break;
				case 1: 
					tipoLogNivel1 = obtenerTipoXid(Tipos.TIPOS_OPERACION_LOG_LAB_ACTUALIZACION_rec_humano);
					break;
				case 2: 
					tipoLogNivel1 = obtenerTipoXid(Tipos.TIPOS_OPERACION_LOG_LAB_ACTUALIZACION_riesgos);
					break;
				case 3: 
					tipoLogNivel1 = obtenerTipoXid(Tipos.TIPOS_OPERACION_LOG_LAB_ACTUALIZACION_gestion);
					break;
				case 4: 
					tipoLogNivel1 = obtenerTipoXid(Tipos.TIPOS_OPERACION_LOG_LAB_ACTUALIZACION_equipos);
					break;
				case 5: 
					tipoLogNivel1 = obtenerTipoXid(Tipos.TIPOS_OPERACION_LOG_LAB_ACTUALIZACION_investigacion);
					break;
				case 6: 
					tipoLogNivel1 = obtenerTipoXid(Tipos.TIPOS_OPERACION_LOG_LAB_ACTUALIZACION_proyectos);
					break;
				case 7: 
					tipoLogNivel1 = obtenerTipoXid(Tipos.TIPOS_OPERACION_LOG_LAB_ACTUALIZACION_docencia);
					break;
				case 8: 
					tipoLogNivel1 = obtenerTipoXid(Tipos.TIPOS_OPERACION_LOG_LAB_ACTUALIZACION_servicios);
					break;
				case 9: 
					tipoLogNivel1 = obtenerTipoXid(Tipos.TIPOS_OPERACION_LOG_LAB_ACTUALIZACION_presupuesto);
					break;
				case 11: 
					tipoLogNivel1 = obtenerTipoXid(Tipos.TIPOS_OPERACION_LOG_LAB_ACTUALIZACION_metrologia);
					break;
				default:
					tipoLogNivel1 = obtenerTipoXid(Tipos.TIPOS_OPERACION_LOG_LAB_ACTUALIZACION);
					break;
			}
		
			LaboratorioLogLaboratorios logLaboratorio = new LaboratorioLogLaboratorios();
			logLaboratorio.setLaboratorio(laboratorioActual);
			logLaboratorio.setTipoOperacion(tipoLogNivel1);
			logLaboratorio.setFechaRegistro(new Date());
			logLaboratorio.setResponsable(personaActual);
			servicioGeneral.guardarObjeto(logLaboratorio);
			
			if(!nombreOriginal.equals(laboratorioActual.getNombre()))
			{
				tipoLogCambioNombre = obtenerTipoXid(Tipos.TIPOS_OPERACION_LOG_LAB_CAMBIO_NOMBRE);
				
				LaboratorioLogLaboratorios logLaboratorioNombre = new LaboratorioLogLaboratorios();
				logLaboratorioNombre.setLaboratorio(laboratorioActual);
				logLaboratorioNombre.setTipoOperacion(tipoLogCambioNombre);
				logLaboratorioNombre.setFechaRegistro(new Date());
				logLaboratorioNombre.setResponsable(personaActual);
				servicioGeneral.guardarObjeto(logLaboratorioNombre);
			}
			
			if(!edificioOriginal.equals(laboratorioActual.getEdificio().getId()) || !salonOriginal.equals(laboratorioActual.getSalon()) || !pisoOriginal.equals(laboratorioActual.getPiso()))
			{
				tipoLogCambioUbicacion = obtenerTipoXid(Tipos.TIPOS_OPERACION_LOG_LAB_CAMBIO_UBICACION);
				
				LaboratorioLogLaboratorios logLaboratorioUbicacion = new LaboratorioLogLaboratorios();
				logLaboratorioUbicacion.setLaboratorio(laboratorioActual);
				logLaboratorioUbicacion.setTipoOperacion(tipoLogCambioUbicacion);
				logLaboratorioUbicacion.setFechaRegistro(new Date());
				logLaboratorioUbicacion.setResponsable(personaActual);
				servicioGeneral.guardarObjeto(logLaboratorioUbicacion);
			}
			
			if(!tipoOriginal.equals(laboratorioActual.getTipo().getId()))
			{
				tipoLogCambioNombre = obtenerTipoXid(Tipos.TIPOS_OPERACION_LOG_LAB_CAMBIO_TIPO);
				
				LaboratorioLogLaboratorios logLaboratorioNombre = new LaboratorioLogLaboratorios();
				logLaboratorioNombre.setLaboratorio(laboratorioActual);
				logLaboratorioNombre.setTipoOperacion(tipoLogCambioNombre);
				logLaboratorioNombre.setFechaRegistro(new Date());
				logLaboratorioNombre.setResponsable(personaActual);
				servicioGeneral.guardarObjeto(logLaboratorioNombre);
			}
			
//			Persona coordinador;
			
			if(!esNulo(listaPersonasLaboratorio))
			{	
				for(PersonaLaboratorio perLab : listaPersonasLaboratorio)
				{
					if(perLab.getRol().getId().equals(Rol.COORDINADOR_LABORATORIO))
					{
						Persona coordinador = perLab.getPersona();
						if(coordinador != null)
						{
							if(!coordinadorOriginal.equals(coordinador))
							{
								Tipos tipoLogCambioCoordinador = obtenerTipoXid(Tipos.TIPOS_OPERACION_LOG_LAB_CAMBIO_COORDINADOR);
								
								LaboratorioLogLaboratorios logLaboratorioCoord = new LaboratorioLogLaboratorios();
								logLaboratorioCoord.setLaboratorio(laboratorioActual);
								logLaboratorioCoord.setTipoOperacion(tipoLogCambioCoordinador);
								logLaboratorioCoord.setFechaRegistro(new Date());
								logLaboratorioCoord.setResponsable(personaActual);
								servicioGeneral.guardarObjeto(logLaboratorioCoord);
							}
						}
						return;
					}	
				}
			}
		}	
		
	}
	
	public void convertirListaInformacionFaltante()
	{
		
		if(laboratorioActual != null && laboratorioActual.getInformacionFaltante() != null && laboratorioActual.getInformacionFaltante() != "") {
			String[] lista = laboratorioActual.getInformacionFaltante().split("- ");
			for (int i = 1; i < lista.length; i++) {
				listaInfoFaltante.add(lista[i]);
			}
		}
		else
			System.out.println("Equipo NULO");
	}

	public Boolean esNulo(Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof String) {
			String str = (String) obj;
			return str.trim().length() == 0;
		}
		if (obj instanceof Number) {
			Number n = (Number) obj;
			return n.doubleValue() == 0.0;
		}
		return false;
	}
	
	public Boolean esNuloVacio(Object obj) {
		if (obj == null) {
			return true;
		}
		
		if (obj.equals("")) {
			return true;
		}
		
		return false;
	}

	@SuppressWarnings("rawtypes")
	public void calcularCompletitud() {
		servicioGeneral.calcularPorcentajeLab(laboratorioActual);
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
		sesion.removeAttribute("manejadorSolicitudLaboratorio");
		sesion.removeAttribute("solicitudLaboratorio");
		sesion.removeAttribute("manejadorLaboratorioPresupuesto");
//		sesion.removeAttribute("ManejadorSolicitudCreacionLaboratorio");
	}

	public String salir() {
		limpiarSesion();
		
		sesion.removeAttribute("Laboratorio");
		sesion.removeAttribute("soloLectura");
		
		return "misProyectos";
	}
	
	public String volverSolicitud() {
		limpiarSesion();
		sesion.removeAttribute("Laboratorio");
		sesion.removeAttribute("soloLectura");
		return "solicitudCreacionLaboratorio";
	}

	public ArchivoLaboratorio subirArchivoLaboratorios(FileUploadEvent event,Tipos tipoArchivoLaboratorios) {

		UploadedFile archivoSubir = event.getFile();
		Long id = null;
		ArchivoLaboratorio archivoNuevo = null;
		if (archivoSubir != null) {

			archivoNuevo = new ArchivoLaboratorio();
			archivoNuevo.setNombreArchivo(archivoSubir.getFileName());
			archivoNuevo.setFechaArchivo(new Date());
			archivoNuevo.setIdLab(laboratorioActual.getId());
			archivoNuevo.setTipoArchivo(tipoArchivoLaboratorios);

			// obtener secuencia:
			Long seq = servicioGeneral.consecutivoSecuencia("SEQ_ARCHIVO");
			archivoNuevo.setId(seq);
			id = archivoNuevo.getId();

			if (id != null) {
				Boolean exitoSubir = cargarArchivoDisco(archivoSubir,ArchivoLaboratorio.TABLA_ARCHIVOS_LABORATORIOS,id.toString());
				if (exitoSubir) {
					return archivoNuevo;
				} else {
					mensajeError("Error al subir archivo.");
					return null;
				}
			}
		}
		
		mensajeError("Error al subir archivo.");
		return null;
	}

	public void descargarArchivoLaboratorios(ArchivoLaboratorio archivoLaboratorioDescargar) {
		String idArchivo = archivoLaboratorioDescargar.getId().toString();
		String nombreArchivo = archivoLaboratorioDescargar.getNombreArchivo();
		descargarArchivoGenerico(ArchivoLaboratorio.TABLA_ARCHIVOS_LABORATORIOS, idArchivo,nombreArchivo);
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
	
	public String convertirArregloACadena(String[] arreglo, String separador)
	{
		String cadena = null;
		
		for (String metodo : arreglo) {
    		Tipos tipo = obtenerTipoXid(Long.parseLong(metodo));
    		cadena += tipo.getNombre() + separador;
		}
    	
    	if(cadena != null)
    		cadena = cadena.substring(4);
		
		return cadena;
	}

	// Métodos abstractos, se implementan en cada manejador
	abstract public String salirGuardar();

	abstract public String siguiente();

	/**
	 * @return the idManejador
	 */
	public int getIdManejador() {
		return idManejador;
	}

	/**
	 * @param idManejador
	 *            the idManejador to set
	 */
	public void setIdManejador(int idManejador) {
		this.idManejador = idManejador;
	}

	/**
	 * @return the laboratorioActual
	 */
	public Laboratorio getLaboratorioActual() {
		return laboratorioActual;
	}

	/**
	 * @param laboratorioActual
	 *            the laboratorioActual to set
	 */
	public void setLaboratorioActual(Laboratorio laboratorioActual) {
		this.laboratorioActual = laboratorioActual;
	}

	/**
	 * @return the listaMensajesValidacion
	 */
	public List<String> getListaMensajesValidacion() {
		return listaMensajesValidacion;
	}

	/**
	 * @return the soloLectura
	 */
	public Boolean getSoloLectura() {
		return soloLectura;
	}

	/**
	 * @return the esSolicitudLab
	 */
	public Boolean getEsSolicitudLab() {
		return esSolicitudLab;
	}

	public Boolean getEsLaboratorioNuevo() {
		return esLaboratorioNuevo;
	}

	public void setEsLaboratorioNuevo(Boolean esLaboratorioNuevo) {
		this.esLaboratorioNuevo = esLaboratorioNuevo;
	}

	public List<String> getListaInfoFaltante() {
		return listaInfoFaltante;
	}

	public void setListaInfoFaltante(List<String> listaInfoFaltante) {
		this.listaInfoFaltante = listaInfoFaltante;
	}

	public Boolean getEsLaboratorios() {
		return esLaboratorios;
	}

	public void setEsLaboratorios(Boolean esLaboratorios) {
		this.esLaboratorios = esLaboratorios;
	}

	public Boolean getEsLaboratoriosSede() {
		return esLaboratoriosSede;
	}

	public void setEsLaboratoriosSede(Boolean esLaboratoriosSede) {
		this.esLaboratoriosSede = esLaboratoriosSede;
	}

	public Boolean getEsLaboratoriosFacultad() {
		return esLaboratoriosFacultad;
	}

	public void setEsLaboratoriosFacultad(Boolean esLaboratoriosFacultad) {
		this.esLaboratoriosFacultad = esLaboratoriosFacultad;
	}

	public Boolean getEsLaboratoriosDepto() {
		return esLaboratoriosDepto;
	}

	public void setEsLaboratoriosDepto(Boolean esLaboratoriosDepto) {
		this.esLaboratoriosDepto = esLaboratoriosDepto;
	}

	public Boolean getEsCoordinadorLaboratorio() {
		return esCoordinadorLaboratorio;
	}

	public void setEsCoordinadorLaboratorio(Boolean esCoordinadorLaboratorio) {
		this.esCoordinadorLaboratorio = esCoordinadorLaboratorio;
	}

	public Boolean getPuedeModificarCamposProtegidos() {
		return puedeModificarCamposProtegidos;
	}

	public void setPuedeModificarCamposProtegidos(Boolean puedeModificarCamposProtegidos) {
		this.puedeModificarCamposProtegidos = puedeModificarCamposProtegidos;
	}

	public List<PersonaLaboratorio> getListaPersonasLaboratorio() {
		return listaPersonasLaboratorio;
	}

	public void setListaPersonasLaboratorio(List<PersonaLaboratorio> listaPersonasLaboratorio) {
		this.listaPersonasLaboratorio = listaPersonasLaboratorio;
	}

	public boolean isEsInvestigador() {
		return esInvestigador;
	}

	public void setEsInvestigador(boolean esInvestigador) {
		this.esInvestigador = esInvestigador;
	}

	public String getNombreOriginal() {
		return nombreOriginal;
	}

	public void setNombreOriginal(String nombreOriginal) {
		this.nombreOriginal = nombreOriginal;
	}

	public String getEdificioOriginal() {
		return edificioOriginal;
	}

	public void setEdificioOriginal(String edificioOriginal) {
		this.edificioOriginal = edificioOriginal;
	}

	public String getSalonOriginal() {
		return salonOriginal;
	}

	public void setSalonOriginal(String salonOriginal) {
		this.salonOriginal = salonOriginal;
	}

	public String getPisoOriginal() {
		return pisoOriginal;
	}

	public void setPisoOriginal(String pisoOriginal) {
		this.pisoOriginal = pisoOriginal;
	}

	public Persona getCoordinadorOriginal() {
		return coordinadorOriginal;
	}

	public void setCoordinadorOriginal(Persona coordinadorOriginal) {
		this.coordinadorOriginal = coordinadorOriginal;
	}

	public void setSoloLectura(Boolean soloLectura) {
		this.soloLectura = soloLectura;
	}

	public void setListaMensajesValidacion(List<String> listaMensajesValidacion) {
		this.listaMensajesValidacion = listaMensajesValidacion;
	}

	public void setEsSolicitudLab(Boolean esSolicitudLab) {
		this.esSolicitudLab = esSolicitudLab;
	}

	public LaboratorioSolicitudCreacionLaboratorio getSolicitudLab() {
		return solicitudLab;
	}

	public void setSolicitudLab(LaboratorioSolicitudCreacionLaboratorio solicitudLab) {
		this.solicitudLab = solicitudLab;
	}

	public Boolean getEsPersonalLaboratorio() {
		return esPersonalLaboratorio;
	}

	public void setEsPersonalLaboratorio(Boolean esPersonalLaboratorio) {
		this.esPersonalLaboratorio = esPersonalLaboratorio;
	}
	
	public Boolean getPersonaActualEsCoordinador() {
		return personaActualEsCoordinador;
	}

	public void setPersonaActualEsCoordinador(Boolean personaActualEsCoordinador) {
		this.personaActualEsCoordinador = personaActualEsCoordinador;
	}

	public String getRolSeleccionadoLabs() {
		return rolSeleccionadoLabs;
	}

	public void setRolSeleccionadoLabs(String rolSeleccionadoLabs) {
		this.rolSeleccionadoLabs = rolSeleccionadoLabs;
	}

	public Boolean getPuedeModPermisosPersonal() {
		return puedeModPermisosPersonal;
	}

	public void setPuedeModPermisosPersonal(Boolean puedeModPermisosPersonal) {
		this.puedeModPermisosPersonal = puedeModPermisosPersonal;
	}
}
