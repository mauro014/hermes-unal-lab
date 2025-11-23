package co.edu.unal.hermes.vista.propiedadintelectual;

import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.ArchivoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.ClasificacionEstadoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.CotitularPropiedadIntelectual;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.EstadoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.FichaGestorPropiedadIndustrial;
import co.edu.unal.hermes.modelo.HistoricoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.NegociacionPropiedadIntelectual;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PropiedadIntelectual;
import co.edu.unal.hermes.modelo.Rol;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.SubEstadoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.SubTipoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.TipoArchivo;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.correo.Correo;

public class ManejadorRevisionPropiedadIntelectual extends ManejadorBasePropiedadIntelectual {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String observaciones;
	private SelectItem[] clasificacionEstadosPropiedadIntelectualItem;
	private SelectItem[] estadosPropiedadIntelectualItem;
	private SelectItem[] subEstadoPropiedadIntelectualItem;
	private SelectItem[] sedesApoyoItem;
	private SelectItem[] listaTipoArchivoPropiedadIntItem;
	private String clasificacionEstado;
	private String estado;
	private String subEstado;
	private InvestigadorInterno revisor;
	private Long estadoInicial;
	private Date fechaFinalizacionDerechoInicial;
	private Boolean mostrarDatos;
	private Boolean datosExisten;
	private UploadedFile archivo;
	private Short tipoArchivo;
	private List<TipoArchivo> listaTiposArchivo;
	private String entidadNegociacion;
	private float tiempo;
	private String formaPago;
	private NegociacionPropiedadIntelectual negociacionSeleccionada;
	private List<HistoricoPropiedadIntelectual> historicoPI;
//	private SelectItem[] tipoSolicitudPropiedadIndustrialItem;

	public ManejadorRevisionPropiedadIntelectual() {
		datosExisten = true;
		mostrarDatos = false;
		revisor = servicioPersona.obtenerInvestigadorInterno(cargarPersonaActual().getId());

		if (sesion.getAttribute(ID_PROPIEDAD_INTELECTUAL) != null) {
			Long id = (Long) sesion.getAttribute(ID_PROPIEDAD_INTELECTUAL);
			propiedad = servicioPropiedadIntelectual.obtenerRegistroPropiedadIntelectual(id);
			estadoInicial = propiedad.getSubEstado().getId();
			clasificacionEstado = propiedad.getSubEstado().getEstado().getClasificacion().getId().toString();
			estadosPropiedadIntelectual();
			estado = propiedad.getSubEstado().getEstado().getId().toString();
			subEstadosPropiedadIntelectual();
			subEstado = propiedad.getSubEstado().getId().toString();
			if (propiedad.getPagoAnualidades() == null)
				propiedad.setPagoAnualidades("N");
			if (propiedad.getNegociacion() == null)
				propiedad.setNegociacion("N");
			historicoPI = servicioPropiedadIntelectual.obtenerHistoricoPropiedadIntelectual(propiedad.getId());
			java.util.Collections.sort(historicoPI);
			observaciones = historicoPI.get(historicoPI.size() - 1).getObservacion();

			fechaFinalizacionDerechoInicial = propiedad.getFechaFinalizacionDerecho();
		} else {
			propiedad = new PropiedadIntelectual();
		}
		
		//Se comenta condicion, se generaba error al ser "derechos de autor"
//		if (propiedad.isEsPropiedadIndustrial()) {
		crearFichaGestor();
//		} else {
//			propiedad.setFichaGestor(new FichaGestorPropiedadIndustrial());
//		}

		obtenerClasificacionEstadoPropiedadIntelectual();
		subTiposPropiedadIntelectual();
		cargarSedesApoyo();
		
		//Se comenta condicion, se generaba error al ser "derechos de autor"
//		if (propiedad.isEsPropiedadIndustrial()) {
		cargarTiposArchivo();
//		}

	}

//	public void setTipoSolicitudPropiedadIndustrialItem(SelectItem[] tipoSolicitudPropiedadIndustrialItem) {
//		this.tipoSolicitudPropiedadIndustrialItem = tipoSolicitudPropiedadIndustrialItem;
//	}

	public void cargarTiposArchivo() {
		setListaTiposArchivo(servicioPropiedadIntelectual.obtenerTiposArchivosPropiedadIntelectual());

		if (!esListaVacia(listaTiposArchivo)) {
			listaTipoArchivoPropiedadIntItem = new SelectItem[listaTiposArchivo.size()];
			for (int i = 0; i < listaTiposArchivo.size(); i++) {
				TipoArchivo tipo = listaTiposArchivo.get(i);
				listaTipoArchivoPropiedadIntItem[i] = new SelectItem(tipo.getId(), tipo.getNombre());
			}
		}
	}

	public void obtenerClasificacionEstadoPropiedadIntelectual() {
		List<ClasificacionEstadoPropiedadIntelectual> lista = servicioPropiedadIntelectual
				.obtenerClasificacionEstadosPropiedadIntelectual(
						propiedad.getSubEstado().getEstado().getClasificacion().getId());
		if (!esListaVacia(lista)) {
			clasificacionEstadosPropiedadIntelectualItem = new SelectItem[lista.size()];
			for (int i = 0; i < lista.size(); i++) {
				ClasificacionEstadoPropiedadIntelectual tipo = lista.get(i);
				clasificacionEstadosPropiedadIntelectualItem[i] = new SelectItem(tipo.getId(), tipo.getNombre());
			}
		}
	}

	private TipoArchivo obtenerTipoArchivo(Short tipoArchivo) {
		if (!esListaVacia(listaTiposArchivo)) {
			for (int i = 0; i < listaTiposArchivo.size(); i++) {
				TipoArchivo tipo = listaTiposArchivo.get(i);
				if (tipoArchivo.equals(tipo.getId())) {
					return tipo;
				}
			}
		}
		return new TipoArchivo();
	}

	public void estadosPropiedadIntelectual() {
		Integer tamLista = 0;
		estadosPropiedadIntelectualItem = new SelectItem[0];
		if (!esCadenaVacia(clasificacionEstado)) {
			List<EstadoPropiedadIntelectual> lista = servicioPropiedadIntelectual.obtenerEstadosPropiedadIntelectual(clasificacionEstado, propiedad.getSubTipo().getTipo().getId());
			
			if (!esListaVacia(lista)) {
				
				if(!(propiedad.getSubTipo().getId().equals(SubTipoPropiedadIntelectual.PATENTE_INVENCION_PROPIEDAD_INDUSTRIAL) || propiedad.getSubTipo().getId().equals(SubTipoPropiedadIntelectual.PATENTE_MODELO_UTILIDAD_PROPIEDAD_INDUSTRIAL)))	
				{	
					tamLista = lista.size();
//					estadosPropiedadIntelectualItem = new SelectItem[tamLista];
					EstadoPropiedadIntelectual epi2 = null;
					
					for (EstadoPropiedadIntelectual estadoPropiedadIntelectual : lista) {
						if(estadoPropiedadIntelectual.getId().equals(EstadoPropiedadIntelectual.MANTENIMIENTO_PROPIEDAD_INDUSTRIAL))
							epi2 = estadoPropiedadIntelectual;
					}
					
					if(epi2 != null){
						tamLista = lista.size()-1;
						lista.remove(epi2);
					}
				}	
				else
				{
					tamLista = lista.size();
//					estadosPropiedadIntelectualItem = new SelectItem[tamLista];
				}
				
				estadosPropiedadIntelectualItem = new SelectItem[tamLista];
				
				for (int i = 0; i < tamLista; i++) {
					EstadoPropiedadIntelectual estadosClasificacion = lista.get(i);
					estadosPropiedadIntelectualItem[i] = new SelectItem(estadosClasificacion.getId(),estadosClasificacion.getNombre());
				}
			}
		} else {
			mensajeError(
					"Debe seleccionar un estado para el trámite, de acuerdo a esta selección se le cargarán los estados posibles para el registro de la propiedad intelectual");
		}
	}

	public void subEstadosPropiedadIntelectual() {
		subEstadoPropiedadIntelectualItem = new SelectItem[0];
		if (!esCadenaVacia(estado)) {
			List<SubEstadoPropiedadIntelectual> lista = servicioPropiedadIntelectual
					.obtenerSubEstadosPropiedadIntelectual(estado);
			if (!esListaVacia(lista)) {
				subEstadoPropiedadIntelectualItem = new SelectItem[lista.size()];
				for (int i = 0; i < lista.size(); i++) {
					SubEstadoPropiedadIntelectual subEstadoClasificacion = lista.get(i);
					subEstadoPropiedadIntelectualItem[i] = new SelectItem(subEstadoClasificacion.getId(),
							subEstadoClasificacion.getNombre());
				}
			}
		} else {
			mensajeError(
					"Debe seleccionar un estado para el proceso, de acuerdo a esta selección se le cargarán los subestados posibles para el registro de la propiedad intelectual");
		}
	}

	public void cargarSedesApoyo() {
		sedesApoyoItem = new SelectItem[0];

		List<Dependencia> lista = servicioGeneral.obtenerObjetos(Dependencia.class,
				"select d from Dependencia d where d.id in (" + Sede.SEDE_NIVEL_NACIONAL + "," + Sede.SEDES_ANDINAS +")");

		if (!esListaVacia(lista)) {
			sedesApoyoItem = new SelectItem[lista.size()];
			for (int i = 0; i < lista.size(); i++) {
				Dependencia subtipo = lista.get(i);
				sedesApoyoItem[i] = new SelectItem(subtipo.getId(), subtipo.getNombre());
			}
		}
	}

	public void adjuntarArchivo() {
		if (tipoArchivo == null || !StringUtils.isNotBlank(tipoArchivo.toString()) || tipoArchivo.toString().equals("0")) {
			tipoArchivo = ArchivoPropiedadIntelectual.ARCHIVO_TRAMITE;
		}

		if (archivo != null) {
			if (archivo.getSize() <= 3072000) {

				if (propiedad.isTieneDocumentoCambioFechaDerecho()
						&& ArchivoPropiedadIntelectual.ARCHIVO_FECHA_DERECHO.equals(tipoArchivo)) {
					mensajeError(
							"Ya existe un archivo de cambio de fecha de finalización del derecho, no es posible adjuntar otro archivo de esta tipología.");
					return;
				}

				ArchivoPropiedadIntelectual ap = insertarArchivoPropiedadIntelectual(archivo);
				if (ap != null && (ap.getId() != null && ap.getId() != 0L)) {
					ap.setPropiedad(propiedad);
					ap.setEstado("V");
					ap.setTipo(obtenerTipoArchivo(tipoArchivo));
					ap.setFechaCreacion(new Date());
					ap.setVisibleDocente(false);
					ap.setPersonaCarga(cargarPersonaActual());
					propiedad.adicionarArchivo(ap);
				}
			}
		} else {
			mensajeError("No se encontró archivo para adjuntar");
		}
	}

	public void agregarNegociacion() {

		if (!validarNegociacion()) {
			return;
		}
		NegociacionPropiedadIntelectual negociacion = new NegociacionPropiedadIntelectual();
		negociacion.setEntidad(obtenerEntidadExterna(entidadNegociacion));
		negociacion.setTiempoAnos(tiempo);
		negociacion.setPropiedad(propiedad);
		negociacion.setFormaPago(controlTamanoCadena(formaPago, 3000));
		propiedad.adicionarNegociacion(negociacion);
	}

	/**
	 * Eliminar negociacion
	 */
	public void eliminarNegociacion() {
		if (negociacionSeleccionada != null) {
			propiedad.borrarNegociacion(negociacionSeleccionada);
		}
	}

	private boolean validarNegociacion() {
		boolean valida = true;
		if (esCadenaVacia(entidadNegociacion)) {
			mensajeError("Debe seleccionar una entidad para agregarla.");
			valida = false;
		}
		for (int i = 0; i < propiedad.getListaNegociaciones().size(); i++) {
			NegociacionPropiedadIntelectual entidadAgregada = (NegociacionPropiedadIntelectual) propiedad
					.getListaNegociaciones().get(i);
			if (entidadAgregada.getEntidad().getId().equals(entidadNegociacion)) {
				mensajeError("El entidad ya ha sido agregada.");
				valida = false;
				break;
			}
		}
		if (esCadenaVacia(formaPago)) {
			mensajeError("Debe indicar la forma de pago.");
			valida = false;
		}
		if (tiempo <= 0) {
			mensajeError("Debe el tiempo de la negociación (en años).");
			valida = false;
		}
		return valida;
	}
	
	public void actualizarEstadosSubestados() {
		estadosPropiedadIntelectual();
	}

	public void guardarRevision() {
		
//		System.out.println("propiedad.getDependenciaSolicitante().getSede().isEsSedePresenciaNacional() " + propiedad.getDependenciaSolicitante().getSede().isEsSedePresenciaNacional());
//		System.out.println("revisor.getDependencia2().getSede().isEsSedePresenciaNacional() " + revisor.getDependencia2().getSede().isEsSedePresenciaNacional());
		
//		if (propiedad.getDependenciaSolicitante().getSede().isEsSedePresenciaNacional() && revisor.getDependencia2().getSede().isEsSedePresenciaNacional()) {
//			if (esCadenaVacia(propiedad.getDependenciaApoyo()) || propiedad.getDependenciaApoyo().equals(propiedad.getDependenciaSolicitante().getId())) {
//				mensajeError("Debe seleccionar una sede de apoyo para guardar la revisión");
//			} else {
//				if(!esNulo(propiedad.getSectorTecnologico()) && propiedad.getSectorTecnologico().getId() != 649) {
//					propiedad.setOtroSector(null);					
//				}
//				servicioGeneral.guardarObjeto(propiedad);
//				crearHistoricoEstadoPropiedadIntelectual(propiedad, cargarPersonaActual(),
//						"Selección de sede de apoyo por parte de sede de presencia nacional - " + controlTamanoCadena(observaciones, 3500));
				
//				Dependencia dep = servicioDependencia.obtenerDependencia(propiedad.getDependenciaApoyo().toString());
				
				// Envio de correo a sede de apoyo
//				Correo correoSedeApoyo = new Correo();
//				CorreoPlantilla cpConfirmacion = cargarPlantilla(279);
//				correoSedeApoyo.setOrigen(Correo.CORREO_HERMES_SOLICITUDES);
//				correoSedeApoyo.adicionarCopiaOculta(Correo.CORREO_HERMES_SOLICITUDES);
//
//				for (InvestigadorInterno inv : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class,
//						" JOIN i.roles r WHERE r.id = '" + Rol.PROPIEDAD_INTELECTUAL_SEDE
//								+ "' and i.dependencia2.sede.id = '" + propiedad.getDependenciaApoyo() + "'")) {
//					correoSedeApoyo.adicionarDireccion(inv.getEmail());
//				}
//
//				correoSedeApoyo.setAsunto(cpConfirmacion.getAsunto());
//				correoSedeApoyo.setCuerpo(cpConfirmacion.getCuerpo()
//						.replaceAll("<<PERSONA>>", propiedad.getResponsableRegistro().getNombreCompletoMinusculas())
//						.replaceAll("<<ID>>", propiedad.getId().toString())
//						.replaceAll("<<TIPO>>", propiedad.getSubTipo().getTipo().getNombre())
//						.replaceAll("<<SEDE>>", propiedad.getDependenciaSolicitante().getSede().getNombre())
//						.replaceAll("<<SEDE_APOYO>>", dep.getSede().getNombre())
//						.replaceAll("<<OBSERVACIONES>>", observaciones).replaceAll("<<EMAIL>>", revisor.getEmail())
//						.replaceAll("<<REVISOR>>", revisor.getNombreCompletoMinusculas()));
//				if (correoSedeApoyo.getDirecciones() != null) {
//					servicioCorreo.enviarCorreo(correoSedeApoyo);
//				}
//			}
//		}
		
		if (!esCadenaVacia(this.subEstado)) {
			
			//Sede de apoyo - Sedes presencia nacional
//			if (propiedad.getDependenciaSolicitante().getSede().isEsSedePresenciaNacional() && revisor.getDependencia2().getSede().isEsSedePresenciaNacional()) {
//				if (esCadenaVacia(propiedad.getDependenciaApoyo()) || propiedad.getDependenciaApoyo().equals(propiedad.getDependenciaSolicitante().getId())) {
//					mensajeError("Debe seleccionar una sede de apoyo para guardar la revisión");
//					return;
//				} else {
//					if(!esNulo(propiedad.getSectorTecnologico()) && propiedad.getSectorTecnologico().getId() != 649) {
//						propiedad.setOtroSector(null);					
//					}
//					servicioGeneral.guardarObjeto(propiedad);
//					crearHistoricoEstadoPropiedadIntelectual(propiedad, cargarPersonaActual(),
//							"Selección de sede de apoyo por parte de sede de presencia nacional - " + controlTamanoCadena(observaciones, 3500));
//				}
//			}

			if (!validarCotitularidad()) {
				return;
			}

			if (clasificacionEstado.equals(ClasificacionEstadoPropiedadIntelectual.EN_TRAMITE_EXTERNO.toString())
					&& esCadenaVacia(propiedad.getRadicado())) {
				mensajeError("Debe ingresar el número de radicado de la entidad externa");
				return;
			}

			if (clasificacionEstado.equals(ClasificacionEstadoPropiedadIntelectual.EN_TRAMITE_EXTERNO.toString())
					&& propiedad.isEsPatente() && esCadenaVacia(propiedad.getContratoAccesoRecursoGenetico())) {
				mensajeError(
						"Debe indicar si se requiere o no contrato de acceso a recurso genético para cambiar a trámite externo la solicitud de protección");
				return;
			}

			if (clasificacionEstado.equals(ClasificacionEstadoPropiedadIntelectual.EN_TRAMITE_EXTERNO.toString())
					&& propiedad.isEsPropiedadIndustrial() && (propiedad.getApoderado() == null
							|| (propiedad.getApoderado() != null && propiedad.getApoderado().getId() == null))) {
				mensajeError("Debe indicar el apoderado de la solicitud de protección");
				return;
			}

			propiedad.setFechaRevision(new Date());

			EstadoPropiedadIntelectual nuevoEstado = new EstadoPropiedadIntelectual();
			nuevoEstado.setTipoPropiedad(propiedad.getSubTipo().getTipo());
			SubEstadoPropiedadIntelectual nuevoSubEstado = new SubEstadoPropiedadIntelectual();
			nuevoSubEstado.setEstado(nuevoEstado);
			nuevoSubEstado.setId(Long.parseLong(this.subEstado));
			propiedad.setSubEstado(nuevoSubEstado);
			crearHistoricoEstadoPropiedadIntelectual(propiedad, cargarPersonaActual(),
					controlTamanoCadena(observaciones, 3500));

			// Requerimiento # 2369 - Angela Devia
			// Sólo se envía correo electrónico al integrante responsable si hay un cambio de estado
			if (!(estadoInicial.toString().equals(this.subEstado))) {
				// Envio de correo solicitante
				Correo correoActualizacionTramite = new Correo();
				CorreoPlantilla cpConfirmacion = cargarPlantilla(278);
				correoActualizacionTramite.setOrigen(Correo.CORREO_HERMES_SOLICITUDES);
				//correoActualizacionTramite.adicionarCopiaOculta(Correo.CORREO_HERMES_SOLICITUDES);
				correoActualizacionTramite.adicionarDireccion(personaActual.getEmail());
				correoActualizacionTramite.adicionarDireccion(propiedad.getResponsableRegistro().getEmail());
				correoActualizacionTramite.setAsunto(cpConfirmacion.getAsunto());
				correoActualizacionTramite.setCuerpo(cpConfirmacion.getCuerpo()
						.replaceAll("<<PERSONA>>", propiedad.getResponsableRegistro().getNombreCompletoMinusculas())
						.replaceAll("<<ID>>", propiedad.getId().toString())
						.replaceAll("<<TIPO>>", propiedad.getSubTipo().getTipo().getNombre())
						.replaceAll("<<ESTADO>>", servicioPropiedadIntelectual
								.obtenerSubEstadoPropiedadIntelectual(propiedad.getSubEstado().getId()).getNombre())
						.replaceAll("<<REVISOR>>", revisor.getNombreCompletoMinusculas())
						.replaceAll("<<EMAIL>>", revisor.getEmail())
						.replaceAll("<<OBSERVACIONES>>", controlTamanoCadena(observaciones, 3500)));
				if (correoActualizacionTramite.getDirecciones() != null) {
					servicioCorreo.enviarCorreo(correoActualizacionTramite);
				}
			}

			if (propiedad.getFechaFinalizacionDerecho() != null && fechaFinalizacionDerechoInicial != null) {
				if (!propiedad.getFechaFinalizacionDerecho().equals(fechaFinalizacionDerechoInicial)) {
					if (!propiedad.isTieneDocumentoCambioFechaDerecho()) {
						mensajeError(
								"Para cambiar la fecha de finalización del derecho debe anexar primero un archivo con la tipología 'Cambio fecha de finalización del derecho'");
					}
				}
			}

			servicioGeneral.guardarObjeto(propiedad);
			mensajeInfo("Se han guardado la revisión.");
		} else {
			mensajeError(
					"Debe seleccionar un estado para el proceso, de acuerdo a esta selección se le cargarán los subestados posibles para el registro de la propiedad intelectual");
		}
	}

	private boolean validarCotitularidad() {
		boolean valida = true;
		if (!esListaVacia(propiedad.getListaCotitulares())) {
			float totalPorcentaje = 0;
			for (int i = 0; i < propiedad.getListaCotitulares().size(); i++) {
				CotitularPropiedadIntelectual entidadAgregada = (CotitularPropiedadIntelectual) propiedad
						.getListaCotitulares().get(i);
				totalPorcentaje = totalPorcentaje + entidadAgregada.getPorcentaje();
				if (entidadAgregada.getPorcentaje() <= 0 && clasificacionEstado
						.equals(ClasificacionEstadoPropiedadIntelectual.EN_TRAMITE_EXTERNO.toString())) {
					mensajeError("Se debe indicar el porcentaje de participación de cada uno de los cotitulares.");
					valida = false;
				}
				if (entidadAgregada.getPorcentaje() > 100) {
					mensajeError(
							"Verifique el porcentaje de cotitularidad de las entidades una entidad no puede tener más del 100% de participación.");
					valida = false;
				}
			}

			if (totalPorcentaje > 100) {
				mensajeError("El total del porcentaje de participación de cotitulares no puede superar el 100%.");
				valida = false;
			}
		}
		return valida;
	}

	public boolean isEsRevisorPrincipal() {
		if (propiedad.getDependenciaSolicitante().getSede().getId()
				.equals(revisor.getDependencia2().getSede().getId())) {
			return true;
		}
		return false;
	}

	public void abrirFormularioApoderado() {
		datosExisten = true;
		mostrarDatos = false;
	}

	public void consultarPersona() {
		mostrarDatos = true;
		datosExisten = false;
		if (personaExterna != null) {
			if (esCadenaVacia(personaExterna.getId().getTipoDocumento())
					|| esCadenaVacia(personaExterna.getId().getDocumento())) {
				mensajeError("Debe indicar el tipo de documento y número de documento del apoderado.");
				mostrarDatos = false;
				datosExisten = true;
				return;
			} else {
				Persona persona = servicioPersona.obtenerPersona(personaExterna.getId());
				if (persona != null) {
					personaExterna = persona;
					datosExisten = true;
				} else {
					personaExterna.setNombre1("");
					personaExterna.setNombre2("");
					personaExterna.setApellido1("");
					personaExterna.setApellido2("");
					personaExterna.setEmail("");
					personaExterna.setDireccion("");
				}
			}
		}
	}

	public void asignarApoderado() {
		if (!datosExisten) {
			if (StringUtils.isNotBlank(personaExterna.getNombre1())
					&& StringUtils.isNotBlank(personaExterna.getApellido1())) {
				servicioPersona.insertarNuevaPersonaDatosBasicosPI(personaExterna);
			} else {
				mensajeError("Como mínimo debe indicar el nombre y apellido del apoderado.");
			}
		}
		propiedad.setApoderado(personaExterna);
	}

	public void crearFichaGestor() {
		if (propiedad.getFichaGestor() == null) {
			propiedad.setFichaGestor(new FichaGestorPropiedadIndustrial());
			propiedad.getFichaGestor().setDescripcion(propiedad.getDescripcion());
			propiedad.getFichaGestor().setDifusion(propiedad.getDifusion());
			propiedad.getFichaGestor().setImpactoSolucion(propiedad.getImpactoSolucion());
			propiedad.getFichaGestor().setMercadoPoblacion(propiedad.getMercadoPoblacion());
			propiedad.getFichaGestor().setMonitoreo(propiedad.getMonitoreo());
			propiedad.getFichaGestor().setObjetoProteccion(propiedad.getObjetoProteccion());
			propiedad.getFichaGestor().setPropiedad(propiedad);
			propiedad.getFichaGestor().setVentajas(propiedad.getVentajas());
		}
	}

	public void fijarDatos() {
		propiedad.setFichaGestor(propiedad.getFichaGestor());
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public SelectItem[] getEstadosPropiedadIntelectualItem() {
		return estadosPropiedadIntelectualItem;
	}

	public void setEstadosPropiedadIntelectualItem(SelectItem[] estadosPropiedadIntelectualItem) {
		this.estadosPropiedadIntelectualItem = estadosPropiedadIntelectualItem;
	}

	public SelectItem[] getClasificacionEstadosPropiedadIntelectualItem() {
		return clasificacionEstadosPropiedadIntelectualItem;
	}

	public void setClasificacionEstadosPropiedadIntelectualItem(
			SelectItem[] clasificacionEstadosPropiedadIntelectualItem) {
		this.clasificacionEstadosPropiedadIntelectualItem = clasificacionEstadosPropiedadIntelectualItem;
	}

	public SelectItem[] getSubEstadoPropiedadIntelectualItem() {
		return subEstadoPropiedadIntelectualItem;
	}

	public void setSubEstadoPropiedadIntelectualItem(SelectItem[] subEstadoPropiedadIntelectualItem) {
		this.subEstadoPropiedadIntelectualItem = subEstadoPropiedadIntelectualItem;
	}

	public String getClasificacionEstado() {
		return clasificacionEstado;
	}

	public void setClasificacionEstado(String clasificacionEstado) {
		this.clasificacionEstado = clasificacionEstado;
	}

	public String getSubEstado() {
		return subEstado;
	}

	public void setSubEstado(String subEstado) {
		this.subEstado = subEstado;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public SelectItem[] getSedesApoyoItem() {
		return sedesApoyoItem;
	}

	public void setSedesApoyoItem(SelectItem[] sedesApoyoItem) {
		this.sedesApoyoItem = sedesApoyoItem;
	}

	public InvestigadorInterno getRevisor() {
		return revisor;
	}

	public void setRevisor(InvestigadorInterno revisor) {
		this.revisor = revisor;
	}

	public Long getEstadoInicial() {
		return estadoInicial;
	}

	public void setEstadoInicial(Long estadoInicial) {
		this.estadoInicial = estadoInicial;
	}

	public Boolean getMostrarDatos() {
		return mostrarDatos;
	}

	public void setMostrarDatos(Boolean mostrarDatos) {
		this.mostrarDatos = mostrarDatos;
	}

	public Boolean getDatosExisten() {
		return datosExisten;
	}

	public void setDatosExisten(Boolean datosExisten) {
		this.datosExisten = datosExisten;
	}

	public Short getTipoArchivo() {
		return tipoArchivo;
	}

	public void setTipoArchivo(Short tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}

	public SelectItem[] getListaTipoArchivoPropiedadIntItem() {
		return listaTipoArchivoPropiedadIntItem;
	}

	public void setListaTipoArchivoPropiedadIntItem(SelectItem[] listaTipoArchivoPropiedadIntItem) {
		this.listaTipoArchivoPropiedadIntItem = listaTipoArchivoPropiedadIntItem;
	}

	public List<TipoArchivo> getListaTiposArchivo() {
		return listaTiposArchivo;
	}

	public void setListaTiposArchivo(List<TipoArchivo> listaTiposArchivo) {
		this.listaTiposArchivo = listaTiposArchivo;
	}

	public UploadedFile getArchivo() {
		return archivo;
	}

	public void setArchivo(UploadedFile archivo) {
		this.archivo = archivo;
	}

	public Date getFechaFinalizacionDerechoInicial() {
		return fechaFinalizacionDerechoInicial;
	}

	public void setFechaFinalizacionDerechoInicial(Date fechaFinalizacionDerechoInicial) {
		this.fechaFinalizacionDerechoInicial = fechaFinalizacionDerechoInicial;
	}

	public String getEntidadNegociacion() {
		return entidadNegociacion;
	}

	public void setEntidadNegociacion(String entidadNegociacion) {
		this.entidadNegociacion = entidadNegociacion;
	}

	public float getTiempo() {
		return tiempo;
	}

	public void setTiempo(float tiempo) {
		this.tiempo = tiempo;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public NegociacionPropiedadIntelectual getNegociacionSeleccionada() {
		return negociacionSeleccionada;
	}

	public void setNegociacionSeleccionada(NegociacionPropiedadIntelectual negociacionSeleccionada) {
		this.negociacionSeleccionada = negociacionSeleccionada;
	}
	
}