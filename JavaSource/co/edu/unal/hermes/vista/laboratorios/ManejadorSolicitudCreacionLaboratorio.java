package co.edu.unal.hermes.vista.laboratorios;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;

import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Icono;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Rol;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.laboratorios.ArchivoLaboratorio;
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleEquipos;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioSolicitud;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioSolicitudCreacionLaboratorio;

/**
 * @author dgbenitezc
 * 
 */
public class ManejadorSolicitudCreacionLaboratorio extends ManejadorLaboratorios {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LaboratorioDetalleEquipos equipoSeleccionado;
	public Boolean solicitudCoordinador;
	public Boolean revisionDLS;
	public LaboratorioSolicitud solicitud;
	private Boolean soloLectura;
	
	private SelectItem[] listaLaboratorios;
	private Long idLabDestino;
	private String procesoRevision;
	Laboratorio labOriginal;
	
	//Creacion Laboratorios
	private LaboratorioSolicitudCreacionLaboratorio solicitudCL;
	
	public Boolean esCreacionCL;
	public Boolean esEdicionCL;
//	boolean esInvestigador;
	
	public Boolean mostrarGuardarParcialmente;
	public Boolean mostrarEnviar;
	
	private ArrayList<filaMuebleAlmacenamiento> listaMueblesAlmacenamiento;
	
	private Long reactivosQuimicosArmarioSeguridad;
	private Long reactivosQuimicosVitrin;
	private Long reactivosQuimicosCajonera;
	private Long reactivosQuimicosEstanteria;
	private Long reactivosQuimicosOtro;
	private Long instrumentalArmarioSeguridad;
	private Long instrumentalVitrina;
	private Long instrumentalCajonera;
	private Long instrumentalEstanteria;
	private Long instrumentalOtro;
	private Long herramientasArmarioSeguridad;
	private Long herramientasVitrina;
	private Long herramientasCajonera;
	private Long herramientasEstanteria;
	private Long herramientasOtro;
	private Long insumosArmarioSeguridad;
	private Long insumosVitrina;
	private Long insumosCajonera;
	private Long insumosEstanteria;
	private Long insumosOtro;
	private Long otroArmarioSeguridad;
	private Long otroVitrina;
	private Long otroCajonera;
	private Long otroEstanteria;
	private Long otroOtro;
	
//	private SelectItem[] siTiposDocumentos;
	private Tipos tipoArchivoSeleccionado;
	private List<ArchivoLaboratorio> listaArchivosSolicitud;
	private List<ArchivoLaboratorio> listaArchivosEliminadosSolicitud;
	private ArchivoLaboratorio archivoLaboratorioSolicitudSeleccionado;
	
	public ManejadorSolicitudCreacionLaboratorio() {
		
//		esEdicionCL = (Boolean) sesion.getAttribute("esEdicionCL") ? ;
		
		esEdicionCL = esNulo(sesion.getAttribute("esEdicionCL")) ? false : (Boolean) sesion.getAttribute("esEdicionCL");
		
		if(esEdicionCL){
			solicitudCL = (LaboratorioSolicitudCreacionLaboratorio) sesion.getAttribute("solicitudSeleccionada");
			listaArchivosSolicitud = servicioGeneral.obtenerArchivosLaboratorioXidSol(solicitudCL.getId());
		}	
		else
			inicializarSolicitud();
		
		inicializarTablaMuebles();
		
		tipoArchivoSeleccionado = obtenerTipoXid(Tipos.TIPOS_ARCHIVOS_SOLICITUD_LABORATORIOS_Soporte);
//		listaArchivosSolicitud = new ArrayList<ArchivoLaboratorio>();
		listaArchivosEliminadosSolicitud = new ArrayList<ArchivoLaboratorio>();
		
		mostrarGuardarParcialmente = false;
		mostrarEnviar = false;
		
		if(solicitudCL.getEstado().getId().equals(Tipos.TIPOS_ESTADO_SOLICITUD_LABORATORIO_Ingresando)){
			mostrarEnviar = true;
			mostrarGuardarParcialmente = true;
		}
		
//		esCreacionCL = (Boolean) sesion.getAttribute("esCreacionCL");
		
//		limpiarSesion();
//		ensayoServicio = (LaboratorioDetalleEnsayosServicios) sesion.getAttribute("ensayoServicioSeleccionado");
//		equipoSeleccionado = (LaboratorioDetalleEquipos) sesion.getAttribute("equipoSeleccionado");
//		solicitudCoordinador = (Boolean) sesion.getAttribute("solicitudCoordinador");
//		revisionDLS = (Boolean) sesion.getAttribute("revisionDLS");
//		System.out.println("equipoSeleccionado: " + equipoSeleccionado.getPlaca());
//		equiposSeleccionadosItem = null;
//		unidadTiempoTotalEstimado = Tipos.UNIDAD_TIEMPO_MINUTOS.toString();
//		cargarListas();
		
//		labOriginal = new Laboratorio();
	}
	
	public void inicializarSolicitud(){
		solicitudCL = new LaboratorioSolicitudCreacionLaboratorio();
		solicitudCL.setPersona(personaActual);
		solicitudCL.setFechaRegistro(getToday());
		solicitudCL.setFechaUltimoCambioEstado(getToday());			
		solicitudCL.setTipo(obtenerTipoXid(Tipos.TIPOS_TIPO_SOLICITUD_LABORATORIO_Crear_Laboratorio));
		solicitudCL.setEstado(obtenerTipoXid(Tipos.TIPOS_ESTADO_SOLICITUD_LABORATORIO_Ingresando));
		
//		tipoArchivoSeleccionado = obtenerTipoXid(Tipos.TIPOS_ARCHIVOS_SOLICITUD_LABORATORIOS_Soporte);
		listaArchivosSolicitud = new ArrayList<ArchivoLaboratorio>();
//		listaArchivosEliminadosSolicitud = new ArrayList<ArchivoLaboratorio>();
	}
	
	public void actualizarInfoTablaMuebles(){
		solicitudCL.setReactivosQuimicosArmarioSeguridad(listaMueblesAlmacenamiento.get(0).getArmarioSeguridad());
		solicitudCL.setReactivosQuimicosVitrina(listaMueblesAlmacenamiento.get(0).getVitrina());
		solicitudCL.setReactivosQuimicosCajonera(listaMueblesAlmacenamiento.get(0).getCajonera());
		solicitudCL.setReactivosQuimicosEstanteria(listaMueblesAlmacenamiento.get(0).getEstanteria());
		solicitudCL.setReactivosQuimicosOtro(listaMueblesAlmacenamiento.get(0).getOtro());
		
		solicitudCL.setInstrumentalArmarioSeguridad(listaMueblesAlmacenamiento.get(1).getArmarioSeguridad());
		solicitudCL.setInstrumentalVitrina(listaMueblesAlmacenamiento.get(1).getVitrina());
		solicitudCL.setInstrumentalCajonera(listaMueblesAlmacenamiento.get(1).getCajonera());
		solicitudCL.setInstrumentalEstanteria(listaMueblesAlmacenamiento.get(1).getEstanteria());
		solicitudCL.setInstrumentalOtro(listaMueblesAlmacenamiento.get(1).getOtro());
		
		solicitudCL.setHerramientasArmarioSeguridad(listaMueblesAlmacenamiento.get(2).getArmarioSeguridad());
		solicitudCL.setHerramientasVitrina(listaMueblesAlmacenamiento.get(2).getVitrina());
		solicitudCL.setHerramientasCajonera(listaMueblesAlmacenamiento.get(2).getCajonera());
		solicitudCL.setHerramientasEstanteria(listaMueblesAlmacenamiento.get(2).getEstanteria());
		solicitudCL.setHerramientasOtro(listaMueblesAlmacenamiento.get(2).getOtro());
		
		solicitudCL.setInsumosArmarioSeguridad(listaMueblesAlmacenamiento.get(3).getArmarioSeguridad());
		solicitudCL.setInsumosVitrina(listaMueblesAlmacenamiento.get(3).getVitrina());
		solicitudCL.setInsumosCajonera(listaMueblesAlmacenamiento.get(3).getCajonera());
		solicitudCL.setInsumosEstanteria(listaMueblesAlmacenamiento.get(3).getEstanteria());
		solicitudCL.setInsumosOtro(listaMueblesAlmacenamiento.get(3).getOtro());
		
		solicitudCL.setOtroArmarioSeguridad(listaMueblesAlmacenamiento.get(4).getArmarioSeguridad());
		solicitudCL.setOtroVitrina(listaMueblesAlmacenamiento.get(4).getVitrina());
		solicitudCL.setOtroCajonera(listaMueblesAlmacenamiento.get(4).getCajonera());
		solicitudCL.setOtroEstanteria(listaMueblesAlmacenamiento.get(4).getEstanteria());
		solicitudCL.setOtroOtro(listaMueblesAlmacenamiento.get(4).getOtro());
	}
	
	public void inicializarTablaMuebles(){
		
		reactivosQuimicosArmarioSeguridad = esEdicionCL ? solicitudCL.getReactivosQuimicosArmarioSeguridad() : 0L;
		reactivosQuimicosVitrin = esEdicionCL ? solicitudCL.getReactivosQuimicosVitrina() : 0L;
		reactivosQuimicosCajonera = esEdicionCL ? solicitudCL.getReactivosQuimicosCajonera() : 0L;
		reactivosQuimicosEstanteria = esEdicionCL ? solicitudCL.getReactivosQuimicosEstanteria() : 0L;
		reactivosQuimicosOtro = esEdicionCL ? solicitudCL.getReactivosQuimicosOtro() : 0L;
		
		instrumentalArmarioSeguridad = esEdicionCL ? solicitudCL.getInstrumentalArmarioSeguridad() : 0L;
		instrumentalVitrina = esEdicionCL ? solicitudCL.getInstrumentalVitrina() : 0L;
		instrumentalCajonera = esEdicionCL ? solicitudCL.getInstrumentalCajonera() : 0L;
		instrumentalEstanteria = esEdicionCL ? solicitudCL.getInstrumentalEstanteria() : 0L;
		instrumentalOtro = esEdicionCL ? solicitudCL.getInstrumentalOtro() : 0L;
		
		herramientasArmarioSeguridad = esEdicionCL ? solicitudCL.getHerramientasArmarioSeguridad() : 0L;
		herramientasVitrina = esEdicionCL ? solicitudCL.getHerramientasVitrina() : 0L;
		herramientasCajonera = esEdicionCL ? solicitudCL.getHerramientasCajonera() : 0L;
		herramientasEstanteria = esEdicionCL ? solicitudCL.getHerramientasEstanteria() : 0L;
		herramientasOtro = esEdicionCL ? solicitudCL.getHerramientasOtro() : 0L;
		
		insumosArmarioSeguridad = esEdicionCL ? solicitudCL.getInsumosArmarioSeguridad() : 0L;
		insumosVitrina = esEdicionCL ? solicitudCL.getInsumosVitrina() : 0L;
		insumosCajonera = esEdicionCL ? solicitudCL.getInsumosCajonera() : 0L;
		insumosEstanteria = esEdicionCL ? solicitudCL.getInsumosEstanteria() : 0L;
		insumosOtro = esEdicionCL ? solicitudCL.getInsumosOtro() : 0L;
		
		otroArmarioSeguridad = esEdicionCL ? solicitudCL.getOtroArmarioSeguridad() : 0L;
		otroVitrina = esEdicionCL ? solicitudCL.getOtroVitrina() : 0L;
		otroCajonera = esEdicionCL ? solicitudCL.getOtroCajonera() : 0L;
		otroEstanteria = esEdicionCL ? solicitudCL.getOtroEstanteria() : 0L;
		otroOtro = esEdicionCL ? solicitudCL.getOtroOtro() : 0L;

		listaMueblesAlmacenamiento = new ArrayList<ManejadorSolicitudCreacionLaboratorio.filaMuebleAlmacenamiento>();
		listaMueblesAlmacenamiento.add(new filaMuebleAlmacenamiento("Reactivos químicos", 
																	reactivosQuimicosArmarioSeguridad, 
																	reactivosQuimicosVitrin, 
																	reactivosQuimicosCajonera, 
																	reactivosQuimicosEstanteria, 
																	reactivosQuimicosOtro));
		listaMueblesAlmacenamiento.add(new filaMuebleAlmacenamiento("Instrumental", 
																	instrumentalArmarioSeguridad, 
																	instrumentalVitrina, 
																	instrumentalCajonera, 
																	instrumentalEstanteria, 
																	instrumentalOtro));
		listaMueblesAlmacenamiento.add(new filaMuebleAlmacenamiento("Herramientas", 
																	herramientasArmarioSeguridad, 
																	herramientasVitrina, 
																	herramientasCajonera, 
																	herramientasEstanteria, 
																	herramientasOtro));
		listaMueblesAlmacenamiento.add(new filaMuebleAlmacenamiento("Insumos", 
																	insumosArmarioSeguridad, 
																	insumosVitrina, 
																	insumosCajonera, 
																	insumosEstanteria, 
																	insumosOtro));
		listaMueblesAlmacenamiento.add(new filaMuebleAlmacenamiento("Otro", 
																	otroArmarioSeguridad, 
																	otroVitrina, 
																	otroCajonera, 
																	otroEstanteria, 
																	otroOtro));
	}
	
	public class filaMuebleAlmacenamiento
	{
	    //Atributos de la clase
	    private String nombre;
	    private Long armarioSeguridad; 
	    private Long vitrina;
	    private Long cajonera;
	    private Long estanteria;
	    private Long otro;

	    //Constructor con el mismo nombre de la clase
	    public filaMuebleAlmacenamiento(){
	 
	    }
	    
	    public filaMuebleAlmacenamiento(String nombre, Long armarioSeguridad, Long vitrina, Long cajonera, Long estanteria, Long otro){
	    	this.nombre = nombre;
	    	this.armarioSeguridad = armarioSeguridad;
	    	this.vitrina = vitrina;
	    	this.cajonera = cajonera;
	    	this.estanteria = estanteria;
	    	this.otro = otro;
	    }

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public Long getArmarioSeguridad() {
			return armarioSeguridad;
		}

		public void setArmarioSeguridad(Long armarioSeguridad) {
			this.armarioSeguridad = armarioSeguridad;
		}

		public Long getVitrina() {
			return vitrina;
		}

		public void setVitrina(Long vitrina) {
			this.vitrina = vitrina;
		}

		public Long getCajonera() {
			return cajonera;
		}

		public void setCajonera(Long cajonera) {
			this.cajonera = cajonera;
		}

		public Long getEstanteria() {
			return estanteria;
		}

		public void setEstanteria(Long estanteria) {
			this.estanteria = estanteria;
		}

		public Long getOtro() {
			return otro;
		}

		public void setOtro(Long otro) {
			this.otro = otro;
		}
	    
	};
	
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
	}
	
	public void subirArchivo(FileUploadEvent event) {
		ArchivoLaboratorio archivoNuevo = subirArchivoLaboratorios(event,tipoArchivoSeleccionado);
		if (archivoNuevo != null) {
			listaArchivosSolicitud.add(archivoNuevo);
		} else {
			mensajeError("Error al subir archivo.");
			System.out.println("Error");
		}
	}
	
	public void descargarArchivo() {
		descargarArchivoLaboratorios(archivoLaboratorioSolicitudSeleccionado);
	}
	
	public void eliminarArchivo() {
		listaArchivosSolicitud.remove(archivoLaboratorioSolicitudSeleccionado);
		listaArchivosEliminadosSolicitud.add(archivoLaboratorioSolicitudSeleccionado);
	}
	
	public String nuevoLab() {
		limpiarSesion();

//		if (validarSolicitud()) {
//			nuevaSolicitud.setPersona(personaActual);
			sesion.setAttribute("solicitudLaboratorio", solicitudCL);
			return "CrearLaboratorio";
//		} else {
//			return "";
//		}
	}
	
	public String editarLaboratorioCreacion() {
		limpiarSesion();
//		System.out.println("editarLaboratorioCreacion:"+ solicitudSeleccionada.getLaboratorio().getId());
		sesion.setAttribute("Laboratorio", solicitudCL.getLaboratorio());
		sesion.setAttribute("solicitudLaboratorio", solicitudCL);
		sesion.setAttribute("soloLectura", false);
		return "CrearLaboratorio";
	}
	
	public void registrarInformaciónLaboratorio() {
		
	}
	
	public void guardarArchivos(){
		// Archivos:
		for (ArchivoLaboratorio al : listaArchivosSolicitud) {
			// lao.setIdLaboratorio(laboratorioActual.getId());
			Long id = al.getId();
			al.setIdLab(laboratorioActual.getId());
			al.setSolicitud(solicitudCL);
			try {
				servicioGeneral.insertarObjetoConIdLong(al, id);
				System.out.println("guardado nuevo archivo: " + id);
			} catch (Exception e) {
				System.out.println("ya estaba guardado archivo: " + id);
			}
		}

		System.out.println("eliminando archivos:");
		for (ArchivoLaboratorio ale : listaArchivosEliminadosSolicitud) {
			eliminarArchivoLaboratorios(ale);
		}
	}
	
	public String enviarSolicitud() {
		if (validar()) 
		{
//			Laboratorio labDestino = servicioGeneral.obtenerLaboratorioXID(idLabDestino);
			
			//Se actualizan datos de la solicitud
			solicitudCL.setEstado(obtenerTipoXid(Tipos.TIPOS_ESTADO_SOLICITUD_LABORATORIO_Enviada));
			solicitudCL.setFechaUltimoCambioEstado(getToday());
			solicitudCL.setFechaRegistro(getToday());
			
			actualizarInfoTablaMuebles();
			
			try
			{
				servicioGeneral.guardarObjeto(solicitudCL);
				mensajeInfo("Su solicitud ha sido guardada y enviada correctamente con el código "+solicitudCL.getId()+". "
							+ "Una notificación ha sido enviada a la Unidad Académica Básica UAB para su respectiva revisión.");
				guardarArchivos();
//				enviarNotificacionTransladarEquipo();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		return "";
		
		} else {
			return "";
		}
	}
	
	public Boolean validar() {
		Boolean validar = true;
				
		// Validar
		//		if (procesoRevision.equals("T") && (idLabDestino.equals(0L) || idLabDestino == null)) {
		//			mensajeError("Debe seleccionar el laboratorio al cual desea trasladar el equipo");
		//			validar = false;
		//		}
		
		if(esNulo(solicitudCL.getJustificacion()) || solicitudCL.getJustificacion().equals(""))
		{
			mensajeError("form:textoJustificacion","La Justificación es un campo obligatorio");
			validar = false;
		}
		
		if(esNulo(solicitudCL.getPertinencia()) || solicitudCL.getPertinencia().equals(""))
		{
			mensajeError("form:textoPertinencia","La Pertinencia es un campo obligatorio");
			validar = false;
		}
		
		if(esNulo(solicitudCL.getInfraestructura()) || solicitudCL.getInfraestructura().equals(""))
		{
			mensajeError("form:textoInfraestructura","La Infraestructura es un campo obligatorio");
			validar = false;
		}
		
		if(esNulo(solicitudCL.getSostenibilidad()) || solicitudCL.getSostenibilidad().equals(""))
		{
			mensajeError("form:textoSostenibilidad","La Sostenibilidad es un campo obligatorio");
			validar = false;
		}
		
		if(esNulo(solicitudCL.getInvestigacionDescActividades()) || solicitudCL.getInvestigacionDescActividades().equals(""))
		{
			mensajeError("form:textoInvestigacion","La Descripción de actividades de Investigacion es un campo obligatorio");
			validar = false;
		}
		
		if(esNulo(solicitudCL.getExtensionDescActividades()) || solicitudCL.getExtensionDescActividades().equals(""))
		{
			mensajeError("form:textoExtension","La Descripción de actividades de Extension es un campo obligatorio");
			validar = false;
		}
		
		if(esNulo(solicitudCL.getDocenciaDescActividades()) || solicitudCL.getDocenciaDescActividades().equals(""))
		{
			mensajeError("form:textoDocencia","La Descripción de actividades de Docencia es un campo obligatorio");
			validar = false;
		}
		
		if(esNulo(solicitudCL.getLaboratorio()))
		{
			mensajeError("Debe ingresar la información específica del laboratorio haciendo clic en el botón 'Registrar Laboratorio'");
			validar = false;
		}
		else
		{
			if(esNulo(solicitudCL.getLaboratorio().getDescripcion())){
				mensajeError("Debe ingresar la descripción de las actividades laboratorio haciendo clic en el botón 'Editar laboratorio'");
				validar = false;
			}
		}
		
		
		
		return validar;
	}
	
	public String guardarSolicitudParcialmente() {
		if (validarGuardadoParcial()) 
		{
			
			//Se actualizan datos de la solicitud
			solicitudCL.setEstado(obtenerTipoXid(Tipos.TIPOS_ESTADO_SOLICITUD_LABORATORIO_Ingresando));
			solicitudCL.setFechaUltimoCambioEstado(getToday());
//			solicitudCL.setFechaRespuesta(getToday());
			
			actualizarInfoTablaMuebles();
			
			try
			{
				servicioGeneral.guardarObjeto(solicitudCL);
				mensajeInfo("Su solicitud ha sido guardada parcialmente con el código "+solicitudCL.getId()+". "
							+ "Puede ingresar en otro momento para complementar la información y enviar para revisión.");
				guardarArchivos();
//				enviarNotificacionTransladarEquipo();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		return "";
		
		} else {
			return "";
		}
	}
	
	public Boolean validarGuardadoParcial() {
		
		Boolean validar = true;
		
		if(esNulo(solicitudCL.getJustificacion()) || solicitudCL.getJustificacion().equals(""))
		{
			mensajeError("form:textoJustificacion","La Justificación es un campo obligatorio");
			validar = false;
		}
		
		if(esNulo(solicitudCL.getPertinencia()) || solicitudCL.getPertinencia().equals(""))
		{
			mensajeError("form:textoPertinencia","La Pertinencia es un campo obligatorio");
			validar = false;
		}
		
		return validar;		
	}
	
	public String registrarInfoLaboratorio()
	{
		return "";
	}
	
//	public String editarLaboratorios() {
//		limpiarSesion();
//		sesion.setAttribute("Laboratorio", laboratorioSeleccionado);
//		sesion.setAttribute("soloLectura", false);
//		return "CrearLaboratorio";
//	}
	
	public Boolean validarAprobar() {
		Boolean validar = true;
		
		// Validar
//		if (procesoRevision.equals("T") && (idLabDestino.equals(0L) || idLabDestino == null)) {
//			mensajeError("Debe seleccionar el laboratorio al cual desea transladar el equipo");
//			validar = false;
//		}
		
		if(esNulo(solicitudCL.getJustificacion()) || solicitudCL.getJustificacion().equals(""))
		{
			mensajeError("form:textoJustificacion","La Justificación de la solicitud es un campo obligatorio");
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
		
		try
		{
			servicioGeneral.guardarObjeto(solicitud);
			servicioGeneral.guardarObjeto(equipoSeleccionado);
			mensajeInfo("La solicitud  "+solicitud.getId()+" ha sido rechazada. Una notificación ha sido enviada al Coordinador del laboratorio del cual se envió la solicitud. ");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
	public void cargarListas() {
			
			listaLaboratorios = servicioGeneral.selectItemListaLaboratoriosActivos();
			
			// Se buscan los equipos en uso por ese laboratorio:
	//		String hql2 = "FROM LaboratorioDetalleEquipos WHERE enUso = 1 AND dadoDeBaja = 0 AND laboratorio = "
	//				+ ensayoServicio.getLaboratorio().getId() + " ORDER BY id";
	//		System.out.println("Búsqueda equipos: " + hql2);
	//		List<LaboratorioDetalleEquipos> listaEquiposLaboratorio = new ArrayList<LaboratorioDetalleEquipos>();
	//		listaEquiposLaboratorio = servicioGeneral.obtenerObjetos(LaboratorioDetalleEquipos.class, hql2);
	//		
	//		equiposUsadosItem = new Vector<SelectItem>();
	//		for (LaboratorioDetalleEquipos g : listaEquiposLaboratorio) {
	//			System.out.println("equiposUsadosItem: " + g.getId());
	//			equiposUsadosItem.add(new SelectItem(g.getId().toString(), g.getPlaca() + " " + g.getEquipo()));
	//		}
	//		
	//		unidadesTiempoItem = servicioGeneral.retornaSelectItemArregloDeHijosDeTipos(Tipos.UNIDADES_TIEMPO);
	//		
	//		tiposNormaLista = servicioGeneral.retornaSelectItemArregloDeHijosDeTipos(Tipos.TIPOS_LAB_NORMA_TECNICA_ENSAYO_SERVICIO);
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
		
//		String rol = null;
//		InvestigadorInterno ii = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());
//		
//		List<PersonaRol> listaPr = servicioGeneral.obtenerPersonaRolXIdPersonaLaboratorios(ii.getId().getTipoDocumento(),ii.getId().getDocumento());
//		if(listaPr != null)
//			rol = servicioPersona.obtenerRol(listaPr.get(0).getNombre()).getNombre();

		correo.setOrigen(Correo.CORREO_HERMES);
//		correo.adicionarDireccion(coorinador.getEmail());
		for (Persona persona : listaPersonasDLS) {
			if(!esNulo(persona.getEmail())){
				correo.adicionarDireccion(persona.getEmail());
			}
		}
		//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);

		asunto = asunto.replaceAll("<<PLACA_EQUIPO>>", equipoSeleccionado.getPlaca());
		asunto = asunto.replaceAll("<<ID_LABORATORIO>>", equipoSeleccionado.getLaboratorio().getId().toString());
		asunto = asunto.replaceAll("<<NOMBRE_LABORATORIO>>", equipoSeleccionado.getLaboratorio().getNombre());
		
		cuerpo = cuerpo.replaceAll("<<PLACA_EQUIPO>>", equipoSeleccionado.getPlaca());
		cuerpo = cuerpo.replaceAll("<<ID_LABORATORIO>>", equipoSeleccionado.getLaboratorio().getId().toString());
		cuerpo = cuerpo.replaceAll("<<NOMBRE_LABORATORIO>>", equipoSeleccionado.getLaboratorio().getNombre());
		
		cuerpo = cuerpo.replaceAll("<<NOMBRE_PERSONA>>", solicitud.getPersona().getNombreCompleto());
		cuerpo = cuerpo.replaceAll("<<NOMBRE_ROL>>", rolPersonaActual.getNombre());
		
		
//		cuerpo = cuerpo.replaceAll("<<NOMBRE_USUARIO_SESION>>", personaActual.getNombreCompleto());
//		cuerpo = cuerpo.replaceAll("<<NOMBRE_ROL>>", rol);
//		cuerpo = cuerpo.replaceAll("<<NOMBRE_FACULTAD>>", ii.getDependencia().getFacultad().getNombre());
//		cuerpo = cuerpo.replaceAll("<<NOMBRE_SEDE>>", ii.getDependencia().getFacultad().getSede().getNombre());

		correo.setAsunto(asunto);
		correo.setCuerpo(cuerpo);
		servicioCorreo.enviarCorreo(correo);
	}
	
	public String volverInicio() {
		limpiarSesion();
		sesion.removeAttribute("ManejadorSolicitudCreacionLaboratorio");
		sesion.removeAttribute("ManejadorListaSolicitudCreacionLab");
		sesion.removeAttribute("Laboratorio");
		sesion.removeAttribute("soloLectura");
		return "misProyectos";
	}
//	
//	public void limpiarSesionSolCrea() {
//		sesion.removeAttribute("ManejadorSolicitudCreacionLaboratorio");
//	}
	
	public void enviarNotificacionDesvincularEquipo()
	{
		CorreoPlantilla correoPlantilla = servicioCorreo.obtenerPlantillaCorreoCompleta(CorreoPlantilla.CORREO_NOTIFICACION_EQUIPO_DESVINCULADO);

		Correo correo = new Correo();
		String cuerpo = correoPlantilla.getCuerpo();
		String asunto = correoPlantilla.getAsunto();

		Persona coorinador = servicioGeneral.obtenerCoordinadorLaboratorio(labOriginal.getId());
//		String rol = null;
//		InvestigadorInterno ii = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());
//		
//		List<PersonaRol> listaPr = servicioGeneral.obtenerPersonaRolXIdPersonaLaboratorios(ii.getId().getTipoDocumento(),ii.getId().getDocumento());
//		if(listaPr != null)
//			rol = servicioPersona.obtenerRol(listaPr.get(0).getNombre()).getNombre();

		correo.setOrigen(Correo.CORREO_HERMES);
		correo.adicionarDireccion(coorinador.getEmail());
		//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);

		asunto = asunto.replaceAll("<<PLACA_EQUIPO>>", equipoSeleccionado.getPlaca());
		asunto = asunto.replaceAll("<<ID_LABORATORIO>>", labOriginal.getId().toString());
		asunto = asunto.replaceAll("<<NOMBRE_LABORATORIO>>", labOriginal.getNombre());

		cuerpo = cuerpo.replaceAll("<<NOMBRE_SEDE>>", labOriginal.getSede().getNombre());
		cuerpo = cuerpo.replaceAll("<<NOMBRE_EQUIPO>>", equipoSeleccionado.getEquipo());
		cuerpo = cuerpo.replaceAll("<<PLACA_EQUIPO>>", equipoSeleccionado.getPlaca());
		cuerpo = cuerpo.replaceAll("<<ID_LABORATORIO>>", labOriginal.getId().toString());
		cuerpo = cuerpo.replaceAll("<<NOMBRE_LABORATORIO>>", labOriginal.getNombre());
		
//		cuerpo = cuerpo.replaceAll("<<NOMBRE_USUARIO_SESION>>", personaActual.getNombreCompleto());
//		cuerpo = cuerpo.replaceAll("<<NOMBRE_ROL>>", rol);
//		cuerpo = cuerpo.replaceAll("<<NOMBRE_FACULTAD>>", ii.getDependencia().getFacultad().getNombre());
//		cuerpo = cuerpo.replaceAll("<<NOMBRE_SEDE>>", ii.getDependencia().getFacultad().getSede().getNombre());

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
//		String rol = null;
//		InvestigadorInterno ii = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());
//		
//		List<PersonaRol> listaPr = servicioGeneral.obtenerPersonaRolXIdPersonaLaboratorios(ii.getId().getTipoDocumento(),ii.getId().getDocumento());
//		if(listaPr != null)
//			rol = servicioPersona.obtenerRol(listaPr.get(0).getNombre()).getNombre();

		correo.setOrigen(Correo.CORREO_HERMES);
		correo.adicionarDireccion(coorinador.getEmail());
		//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);

		asunto = asunto.replaceAll("<<PLACA_EQUIPO>>", equipoSeleccionado.getPlaca());
		asunto = asunto.replaceAll("<<ID_LABORATORIO_DESTINO>>", equipoSeleccionado.getLaboratorio().getId().toString());
		asunto = asunto.replaceAll("<<NOMBRE_LABORATORIO_DESTINO>>", equipoSeleccionado.getLaboratorio().getNombre());

		cuerpo = cuerpo.replaceAll("<<NOMBRE_SEDE>>", equipoSeleccionado.getLaboratorio().getSede().getNombre());
		cuerpo = cuerpo.replaceAll("<<NOMBRE_EQUIPO>>", equipoSeleccionado.getEquipo());
		cuerpo = cuerpo.replaceAll("<<PLACA_EQUIPO>>", equipoSeleccionado.getPlaca());
		cuerpo = cuerpo.replaceAll("<<ID_LABORATORIO_DESTINO>>", equipoSeleccionado.getLaboratorio().getId().toString());
		cuerpo = cuerpo.replaceAll("<<NOMBRE_LABORATORIO_DESTINO>>", equipoSeleccionado.getLaboratorio().getNombre());
		
//		cuerpo = cuerpo.replaceAll("<<NOMBRE_USUARIO_SESION>>", personaActual.getNombreCompleto());
//		cuerpo = cuerpo.replaceAll("<<NOMBRE_ROL>>", rol);
//		cuerpo = cuerpo.replaceAll("<<NOMBRE_FACULTAD>>", ii.getDependencia().getFacultad().getNombre());
//		cuerpo = cuerpo.replaceAll("<<NOMBRE_SEDE>>", ii.getDependencia().getFacultad().getSede().getNombre());

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

	@Override
	public String salirGuardar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String siguiente() {
		// TODO Auto-generated method stub
		return null;
	}

	public LaboratorioSolicitudCreacionLaboratorio getSolicitudCL() {
		return solicitudCL;
	}

	public void setSolicitudCL(LaboratorioSolicitudCreacionLaboratorio solicitudCL) {
		this.solicitudCL = solicitudCL;
	}

	public Boolean getEsCreacionCL() {
		return esCreacionCL;
	}

	public void setEsCreacionCL(Boolean esCreacionCL) {
		this.esCreacionCL = esCreacionCL;
	}

//	public boolean isEsInvestigador() {
//		return esInvestigador;
//	}
//
//	public void setEsInvestigador(boolean esInvestigador) {
//		this.esInvestigador = esInvestigador;
//	}

	public ArrayList<filaMuebleAlmacenamiento> getListaMueblesAlmacenamiento() {
		return listaMueblesAlmacenamiento;
	}

	public void setListaMueblesAlmacenamiento(ArrayList<filaMuebleAlmacenamiento> listaMueblesAlmacenamiento) {
		this.listaMueblesAlmacenamiento = listaMueblesAlmacenamiento;
	}

	public Boolean getEsEdicionCL() {
		return esEdicionCL;
	}

	public void setEsEdicionCL(Boolean esEdicionCL) {
		this.esEdicionCL = esEdicionCL;
	}

	public Boolean getMostrarGuardarParcialmente() {
		return mostrarGuardarParcialmente;
	}

	public void setMostrarGuardarParcialmente(Boolean mostrarGuardarParcialmente) {
		this.mostrarGuardarParcialmente = mostrarGuardarParcialmente;
	}

	public Boolean getMostrarEnviar() {
		return mostrarEnviar;
	}

	public void setMostrarEnviar(Boolean mostrarEnviar) {
		this.mostrarEnviar = mostrarEnviar;
	}

	public Long getReactivosQuimicosArmarioSeguridad() {
		return reactivosQuimicosArmarioSeguridad;
	}

	public void setReactivosQuimicosArmarioSeguridad(Long reactivosQuimicosArmarioSeguridad) {
		this.reactivosQuimicosArmarioSeguridad = reactivosQuimicosArmarioSeguridad;
	}

	public Long getReactivosQuimicosVitrin() {
		return reactivosQuimicosVitrin;
	}

	public void setReactivosQuimicosVitrin(Long reactivosQuimicosVitrin) {
		this.reactivosQuimicosVitrin = reactivosQuimicosVitrin;
	}

	public Long getReactivosQuimicosCajonera() {
		return reactivosQuimicosCajonera;
	}

	public void setReactivosQuimicosCajonera(Long reactivosQuimicosCajonera) {
		this.reactivosQuimicosCajonera = reactivosQuimicosCajonera;
	}

	public Long getReactivosQuimicosEstanteria() {
		return reactivosQuimicosEstanteria;
	}

	public void setReactivosQuimicosEstanteria(Long reactivosQuimicosEstanteria) {
		this.reactivosQuimicosEstanteria = reactivosQuimicosEstanteria;
	}

	public Long getReactivosQuimicosOtro() {
		return reactivosQuimicosOtro;
	}

	public void setReactivosQuimicosOtro(Long reactivosQuimicosOtro) {
		this.reactivosQuimicosOtro = reactivosQuimicosOtro;
	}

	public Long getInstrumentalArmarioSeguridad() {
		return instrumentalArmarioSeguridad;
	}

	public void setInstrumentalArmarioSeguridad(Long instrumentalArmarioSeguridad) {
		this.instrumentalArmarioSeguridad = instrumentalArmarioSeguridad;
	}

	public Long getInstrumentalVitrina() {
		return instrumentalVitrina;
	}

	public void setInstrumentalVitrina(Long instrumentalVitrina) {
		this.instrumentalVitrina = instrumentalVitrina;
	}

	public Long getInstrumentalCajonera() {
		return instrumentalCajonera;
	}

	public void setInstrumentalCajonera(Long instrumentalCajonera) {
		this.instrumentalCajonera = instrumentalCajonera;
	}

	public Long getInstrumentalEstanteria() {
		return instrumentalEstanteria;
	}

	public void setInstrumentalEstanteria(Long instrumentalEstanteria) {
		this.instrumentalEstanteria = instrumentalEstanteria;
	}

	public Long getInstrumentalOtro() {
		return instrumentalOtro;
	}

	public void setInstrumentalOtro(Long instrumentalOtro) {
		this.instrumentalOtro = instrumentalOtro;
	}

	public Long getHerramientasArmarioSeguridad() {
		return herramientasArmarioSeguridad;
	}

	public void setHerramientasArmarioSeguridad(Long herramientasArmarioSeguridad) {
		this.herramientasArmarioSeguridad = herramientasArmarioSeguridad;
	}

	public Long getHerramientasVitrina() {
		return herramientasVitrina;
	}

	public void setHerramientasVitrina(Long herramientasVitrina) {
		this.herramientasVitrina = herramientasVitrina;
	}

	public Long getHerramientasCajonera() {
		return herramientasCajonera;
	}

	public void setHerramientasCajonera(Long herramientasCajonera) {
		this.herramientasCajonera = herramientasCajonera;
	}

	public Long getHerramientasEstanteria() {
		return herramientasEstanteria;
	}

	public void setHerramientasEstanteria(Long herramientasEstanteria) {
		this.herramientasEstanteria = herramientasEstanteria;
	}

	public Long getHerramientasOtro() {
		return herramientasOtro;
	}

	public void setHerramientasOtro(Long herramientasOtro) {
		this.herramientasOtro = herramientasOtro;
	}

	public Long getInsumosArmarioSeguridad() {
		return insumosArmarioSeguridad;
	}

	public void setInsumosArmarioSeguridad(Long insumosArmarioSeguridad) {
		this.insumosArmarioSeguridad = insumosArmarioSeguridad;
	}

	public Long getInsumosVitrina() {
		return insumosVitrina;
	}

	public void setInsumosVitrina(Long insumosVitrina) {
		this.insumosVitrina = insumosVitrina;
	}

	public Long getInsumosCajonera() {
		return insumosCajonera;
	}

	public void setInsumosCajonera(Long insumosCajonera) {
		this.insumosCajonera = insumosCajonera;
	}

	public Long getInsumosEstanteria() {
		return insumosEstanteria;
	}

	public void setInsumosEstanteria(Long insumosEstanteria) {
		this.insumosEstanteria = insumosEstanteria;
	}

	public Long getInsumosOtro() {
		return insumosOtro;
	}

	public void setInsumosOtro(Long insumosOtro) {
		this.insumosOtro = insumosOtro;
	}

	public Long getOtroArmarioSeguridad() {
		return otroArmarioSeguridad;
	}

	public void setOtroArmarioSeguridad(Long otroArmarioSeguridad) {
		this.otroArmarioSeguridad = otroArmarioSeguridad;
	}

	public Long getOtroVitrina() {
		return otroVitrina;
	}

	public void setOtroVitrina(Long otroVitrina) {
		this.otroVitrina = otroVitrina;
	}

	public Long getOtroCajonera() {
		return otroCajonera;
	}

	public void setOtroCajonera(Long otroCajonera) {
		this.otroCajonera = otroCajonera;
	}

	public Long getOtroEstanteria() {
		return otroEstanteria;
	}

	public void setOtroEstanteria(Long otroEstanteria) {
		this.otroEstanteria = otroEstanteria;
	}

	public Long getOtroOtro() {
		return otroOtro;
	}

	public void setOtroOtro(Long otroOtro) {
		this.otroOtro = otroOtro;
	}

	public Tipos getTipoArchivoSeleccionado() {
		return tipoArchivoSeleccionado;
	}

	public void setTipoArchivoSeleccionado(Tipos tipoArchivoSeleccionado) {
		this.tipoArchivoSeleccionado = tipoArchivoSeleccionado;
	}

	public List<ArchivoLaboratorio> getListaArchivosSolicitud() {
		return listaArchivosSolicitud;
	}

	public void setListaArchivosSolicitud(List<ArchivoLaboratorio> listaArchivosSolicitud) {
		this.listaArchivosSolicitud = listaArchivosSolicitud;
	}

	public List<ArchivoLaboratorio> getListaArchivosEliminadosSolicitud() {
		return listaArchivosEliminadosSolicitud;
	}

	public void setListaArchivosEliminadosSolicitud(List<ArchivoLaboratorio> listaArchivosEliminadosSolicitud) {
		this.listaArchivosEliminadosSolicitud = listaArchivosEliminadosSolicitud;
	}

	public ArchivoLaboratorio getArchivoLaboratorioSolicitudSeleccionado() {
		return archivoLaboratorioSolicitudSeleccionado;
	}

	public void setArchivoLaboratorioSolicitudSeleccionado(ArchivoLaboratorio archivoLaboratorioSolicitudSeleccionado) {
		this.archivoLaboratorioSolicitudSeleccionado = archivoLaboratorioSolicitudSeleccionado;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}