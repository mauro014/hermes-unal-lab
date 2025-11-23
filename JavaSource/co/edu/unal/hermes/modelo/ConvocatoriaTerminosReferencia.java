package co.edu.unal.hermes.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ConvocatoriaTerminosReferencia {

	private Long id;
	private String tipoConvocatoria;
	private String otroTipoConvocatoria;
	private String titulo;
	private String introduccion;
	private String presentacion;
	private String antecedentes;
	private String objetivo;
	private String dirigidoA;
	private String recursosDisponibles;
	private String unidadEjecutra;
	private String apoyoGanadores;
	private String liquidacionProyectos;
	private Long duracion;
	private String dependeciaVerificacionRequisito;
	private String tipoEvaluacion;
	private String tipoPuntajeEvaluacion;
	private String descripcionProcesoEvaluacion;
	private String responsableProcesoEvaluacion;
	private String tipoPuntajeSeleccion;
	private String responsableProcesoSeleccion;
	private String subtipoEvaluacion;
	private Long puntajeMinimoEvaluacion;
	private String propuestasFinanciables;
	private String procesoSelecicon;
	private String criteriosDesempate;
	private String tipoReclamacion;
	private Long diasParaSolicitarReclamacionesReq;
	private String dependenciaResponsableResponderReclamacionReq;
	private Long diasParaResponderReclamacionesReq;
	private String tipoReclamacionEvaluacion;
	private Long diasParaSolicitarReclamacionesEva;
	private String dependenciaResponsableResponderReclamacionEva;
	private Long diasParaResponderReclamacionesEva;
	private String textoPublicacionGanadores;
	private String textoProductosAcademicos;
	private String textoResultados;
	private String tipoCalendario;
	private String cronogramaCortes;
	private Date fechaLanzamientoConvocatoria;
	private Date fechaPublicacionResultados;
	private Date fechaInicioInscripciones;
	private Date fechaFinInscripciones;
	private Date fechaInicioReclamacionesRequisitos;
	private Date fechaFinReclamacionesRequisitos;
	private Date fechaRespuestaReclamacionRequisitos;
	private Date fechaInicioEvaluacion;
	private Date fechaFinEvaluacion;
	private Date fechaPublicacionPropuestasSeleccionadas;
	private Date fechaInicioReclamacionesEvaluacion;
	private Date fechaFinReclamacionesEvaluacion;
	private Date fechaRespuestaReclamacionesEvaluacion;
	private Date fechaPublicacionGanadores;
	private String dependenciaResponsableSeguimientoTecnico;
	private String propuestasModificablesEjecucion;
	private String textoConsideraciones;
	private String textoMayorInformacion;
	private String consideracionesEticas;
	private String propiedadIntelectual;
	private Dependencia dependenciaPrincipal;
	private String estado;
	private Date fechaCreacion;
	private Date fechaModificacion;
	private Persona personaMod;

	private Set<DependenciaConvocatoriaTR> dependenciasConvoctoriaTR = new HashSet<DependenciaConvocatoriaTR>();
	private Set<ModalidadConvocatoriaTR> modalidadesConvoctoriaTR = new HashSet<ModalidadConvocatoriaTR>();
	private Set<RequisitoConvocatoriaTR> requisitosConvoctoriaTR = new HashSet<RequisitoConvocatoriaTR>();
	private Set<DocumentoConvocatoriaTR> documentacionConvoctoriaTR = new HashSet<DocumentoConvocatoriaTR>();
	private Set<CompromisoConvocatoriaTR> compromisosConvoctoriaTR = new HashSet<CompromisoConvocatoriaTR>();
	private Set<IncompatibilidadConvocatoriaTR> incompatibilidadesConvoctoriaTR = new HashSet<IncompatibilidadConvocatoriaTR>();
	private Set<RubrosFinanciablesConvocatoriaTR> rubrosConvoctoriaTR = new HashSet<RubrosFinanciablesConvocatoriaTR>();
	private Set<CriteriosEvaluacionConvocatoriaTR> criteriosEvaluacionSeleccionConvoctoriaTR = new HashSet<CriteriosEvaluacionConvocatoriaTR>();
	private Set<ProductosConvocatoriaTR> productosConvoctoriaTR = new HashSet<ProductosConvocatoriaTR>();
	private Set<CamposAdicionalesConvocatoriaTR> camposAdicionalesConvoctoriaTR = new HashSet<CamposAdicionalesConvocatoriaTR>();
	private Set<ObjetivosEspecificosConvocatoriaTR> objetivosEspecificosConvoctoriaTR = new HashSet<ObjetivosEspecificosConvocatoriaTR>();

	public ConvocatoriaTerminosReferencia() {
		super();
	}

	// campos adicionales
	public void adicionaObjetivoEspecifico(ObjetivosEspecificosConvocatoriaTR obj) {
		if (objetivosEspecificosConvoctoriaTR == null) {
			objetivosEspecificosConvoctoriaTR = new HashSet<ObjetivosEspecificosConvocatoriaTR>();
		}
		obj.setOrden(Long.parseLong(String.valueOf(getListaObjetivosEspecificosConvocatoriaTR().size() + 1)));
		obj.setConvocatoriaTR(this);
		objetivosEspecificosConvoctoriaTR.add(obj);
	}

	public void borrarObjetivoEspecifico(ObjetivosEspecificosConvocatoriaTR obj) {
		objetivosEspecificosConvoctoriaTR.remove(obj);
	}

	public List<ObjetivosEspecificosConvocatoriaTR> getListaObjetivosEspecificosConvocatoriaTR() {
		List<ObjetivosEspecificosConvocatoriaTR> listaObjetivos = new ArrayList<ObjetivosEspecificosConvocatoriaTR>();
		if (objetivosEspecificosConvoctoriaTR != null) {
			Iterator<ObjetivosEspecificosConvocatoriaTR> i = objetivosEspecificosConvoctoriaTR.iterator();
			while (i.hasNext()) {
				ObjetivosEspecificosConvocatoriaTR objTR = i.next();
				listaObjetivos.add(objTR);
			}
		}
		return listaObjetivos;
	}

	// campos adicionales
	public void adicionaCampo(CamposAdicionalesConvocatoriaTR campo) {
		if (camposAdicionalesConvoctoriaTR == null) {
			camposAdicionalesConvoctoriaTR = new HashSet<CamposAdicionalesConvocatoriaTR>();
		}
		campo.setOrden(Long.parseLong(String.valueOf(getListaCamposAdicionalesConvocatoriaTR().size() + 1)));
		campo.setConvocatoriaTR(this);
		camposAdicionalesConvoctoriaTR.add(campo);
	}

	public void borrarCampo(CamposAdicionalesConvocatoriaTR campo) {
		camposAdicionalesConvoctoriaTR.remove(campo);
	}

	public List<CamposAdicionalesConvocatoriaTR> getListaCamposAdicionalesConvocatoriaTR() {
		List<CamposAdicionalesConvocatoriaTR> listaCampos = new ArrayList<CamposAdicionalesConvocatoriaTR>();
		if (camposAdicionalesConvoctoriaTR != null) {
			Iterator<CamposAdicionalesConvocatoriaTR> i = camposAdicionalesConvoctoriaTR.iterator();
			while (i.hasNext()) {
				CamposAdicionalesConvocatoriaTR campoTR = i.next();
				listaCampos.add(campoTR);
			}
		}
		return listaCampos;
	}

	// productos
	public void adicionarProducto(ProductosConvocatoriaTR producto) {
		if (productosConvoctoriaTR == null) {
			productosConvoctoriaTR = new HashSet<ProductosConvocatoriaTR>();
		}
		producto.setOrden(Long.parseLong(String.valueOf(getListaProductosConvocatoriaTR().size() + 1)));
		producto.setConvocatoriaTR(this);
		productosConvoctoriaTR.add(producto);
	}

	public void borrarProducto(ProductosConvocatoriaTR producto) {
		productosConvoctoriaTR.remove(producto);
	}

	public List<ProductosConvocatoriaTR> getListaProductosConvocatoriaTR() {
		List<ProductosConvocatoriaTR> listaProductos = new ArrayList<ProductosConvocatoriaTR>();
		if (productosConvoctoriaTR != null) {
			Iterator<ProductosConvocatoriaTR> i = productosConvoctoriaTR.iterator();
			while (i.hasNext()) {
				ProductosConvocatoriaTR productoTR = i.next();
				listaProductos.add(productoTR);
			}
		}
		return listaProductos;
	}

	// criterios de evaluación y selección
	public void adicionarCriterioEvaluacion(CriteriosEvaluacionConvocatoriaTR criterio) {
		if (criteriosEvaluacionSeleccionConvoctoriaTR == null) {
			criteriosEvaluacionSeleccionConvoctoriaTR = new HashSet<CriteriosEvaluacionConvocatoriaTR>();
		}
		criterio.setOrden(Long.parseLong(String.valueOf(getListaCriteriosEvaluacionConvocatoriaTR().size() + 1)));
		criterio.setConvocatoriaTR(this);
		criteriosEvaluacionSeleccionConvoctoriaTR.add(criterio);
	}

	public void adicionarCriterioSeleccion(CriteriosEvaluacionConvocatoriaTR criterio) {
		if (criteriosEvaluacionSeleccionConvoctoriaTR == null) {
			criteriosEvaluacionSeleccionConvoctoriaTR = new HashSet<CriteriosEvaluacionConvocatoriaTR>();
		}
		criterio.setOrden(Long.parseLong(String.valueOf(getListaCriteriosSeleccionConvocatoriaTR().size() + 1)));
		criterio.setConvocatoriaTR(this);
		criteriosEvaluacionSeleccionConvoctoriaTR.add(criterio);
	}

	public void borrarCriterio(CriteriosEvaluacionConvocatoriaTR criterio) {
		criteriosEvaluacionSeleccionConvoctoriaTR.remove(criterio);
	}

	public List<CriteriosEvaluacionConvocatoriaTR> getListaCriteriosEvaluacionConvocatoriaTR() {
		List<CriteriosEvaluacionConvocatoriaTR> listaCriterios = new ArrayList<CriteriosEvaluacionConvocatoriaTR>();
		if (criteriosEvaluacionSeleccionConvoctoriaTR != null) {
			Iterator<CriteriosEvaluacionConvocatoriaTR> i = criteriosEvaluacionSeleccionConvoctoriaTR.iterator();
			while (i.hasNext()) {
				CriteriosEvaluacionConvocatoriaTR criterioTR = i.next();
				if (criterioTR.getTipoPuntaje() != null) {
					if (criterioTR.getTipoPuntaje().equals("E")) {
						listaCriterios.add(criterioTR);
					}
				}
			}
		}
		return listaCriterios;
	}

	public List<CriteriosEvaluacionConvocatoriaTR> getListaCriteriosSeleccionConvocatoriaTR() {
		List<CriteriosEvaluacionConvocatoriaTR> listaCriterios = new ArrayList<CriteriosEvaluacionConvocatoriaTR>();
		if (criteriosEvaluacionSeleccionConvoctoriaTR != null) {
			Iterator<CriteriosEvaluacionConvocatoriaTR> i = criteriosEvaluacionSeleccionConvoctoriaTR.iterator();
			while (i.hasNext()) {
				CriteriosEvaluacionConvocatoriaTR criterioTR = i.next();
				if (criterioTR.getTipoPuntaje() != null) {
					if (criterioTR.getTipoPuntaje().equals("S")) {
						listaCriterios.add(criterioTR);
					}
				}
			}
		}
		return listaCriterios;
	}

	// rubros
	public void adicionarRubro(RubrosFinanciablesConvocatoriaTR rubro) {
		if (rubrosConvoctoriaTR == null) {
			rubrosConvoctoriaTR = new HashSet<RubrosFinanciablesConvocatoriaTR>();
		}
		rubro.setOrden(Long.parseLong(String.valueOf(getListaRubrosConvocatoriaTR().size() + 1)));
		rubro.setConvocatoriaTR(this);
		rubrosConvoctoriaTR.add(rubro);
	}

	public void borrarRubro(RubrosFinanciablesConvocatoriaTR rubro) {
		rubrosConvoctoriaTR.remove(rubro);
	}

	public List<RubrosFinanciablesConvocatoriaTR> getListaRubrosConvocatoriaTR() {
		List<RubrosFinanciablesConvocatoriaTR> listaRubros = new ArrayList<RubrosFinanciablesConvocatoriaTR>();
		if (rubrosConvoctoriaTR != null) {
			Iterator<RubrosFinanciablesConvocatoriaTR> i = rubrosConvoctoriaTR.iterator();
			while (i.hasNext()) {
				RubrosFinanciablesConvocatoriaTR rubroTR = i.next();
				listaRubros.add(rubroTR);
			}
		}
		return listaRubros;
	}

	// modalidades
	public void adicionarModalidad(ModalidadConvocatoriaTR modalidad) {
		if (modalidadesConvoctoriaTR == null) {
			modalidadesConvoctoriaTR = new HashSet<ModalidadConvocatoriaTR>();
		}
		modalidad.setOrden(Long.parseLong(String.valueOf(getListaModalidadConvocatoriaTR().size() + 1)));
		modalidad.setConvocatoriaTR(this);
		modalidadesConvoctoriaTR.add(modalidad);
	}

	public void borrarModalidad(ModalidadConvocatoriaTR modalidad) {
		modalidadesConvoctoriaTR.remove(modalidad);
	}

	public List<ModalidadConvocatoriaTR> getListaModalidadConvocatoriaTR() {
		List<ModalidadConvocatoriaTR> listaModalidades = new ArrayList<ModalidadConvocatoriaTR>();
		if (modalidadesConvoctoriaTR != null) {
			Iterator<ModalidadConvocatoriaTR> i = modalidadesConvoctoriaTR.iterator();
			while (i.hasNext()) {
				ModalidadConvocatoriaTR modalidadTR = i.next();
				listaModalidades.add(modalidadTR);
			}
		}
		return listaModalidades;
	}

	// requisitos
	public void adicionarRequisito(RequisitoConvocatoriaTR requisito) {
		if (requisitosConvoctoriaTR == null) {
			requisitosConvoctoriaTR = new HashSet<RequisitoConvocatoriaTR>();
		}
		requisito.setOrden(Long.parseLong(String.valueOf(getListaRequisitoConvocatoriaTR().size() + 1)));
		requisito.setConvocatoriaTR(this);
		requisitosConvoctoriaTR.add(requisito);
	}

	public void borrarRequisito(RequisitoConvocatoriaTR requisito) {
		requisitosConvoctoriaTR.remove(requisito);
	}

	public List<RequisitoConvocatoriaTR> getListaRequisitoConvocatoriaTR() {
		List<RequisitoConvocatoriaTR> listaRequisitos = new ArrayList<RequisitoConvocatoriaTR>();
		if (requisitosConvoctoriaTR != null) {
			Iterator<RequisitoConvocatoriaTR> i = requisitosConvoctoriaTR.iterator();
			while (i.hasNext()) {
				RequisitoConvocatoriaTR requisitoTR = i.next();
				listaRequisitos.add(requisitoTR);
			}
		}
		return listaRequisitos;
	}

	// documentos
	public void adicionarDocumento(DocumentoConvocatoriaTR documento) {
		if (documentacionConvoctoriaTR == null) {
			documentacionConvoctoriaTR = new HashSet<DocumentoConvocatoriaTR>();
		}
		documento.setOrden(Long.parseLong(String.valueOf(getListaDocumentoConvocatoriaTR().size() + 1)));
		documento.setConvocatoriaTR(this);
		documentacionConvoctoriaTR.add(documento);
	}

	public void borrarDocumento(DocumentoConvocatoriaTR documento) {
		documentacionConvoctoriaTR.remove(documento);
	}

	public List<DocumentoConvocatoriaTR> getListaDocumentoConvocatoriaTR() {
		List<DocumentoConvocatoriaTR> listaDocumentos = new ArrayList<DocumentoConvocatoriaTR>();
		if (documentacionConvoctoriaTR != null) {
			Iterator<DocumentoConvocatoriaTR> i = documentacionConvoctoriaTR.iterator();
			while (i.hasNext()) {
				DocumentoConvocatoriaTR docTR = i.next();
				listaDocumentos.add(docTR);
			}
		}
		return listaDocumentos;
	}

	// compromisos
	public void adicionarCompromiso(CompromisoConvocatoriaTR compromiso) {
		if (compromisosConvoctoriaTR == null) {
			compromisosConvoctoriaTR = new HashSet<CompromisoConvocatoriaTR>();
		}
		compromiso.setOrden(Long.parseLong(String.valueOf(getListaCompromisoConvocatoriaTR().size() + 1)));
		compromiso.setConvocatoriaTR(this);
		compromisosConvoctoriaTR.add(compromiso);
	}

	public void borrarCompromiso(CompromisoConvocatoriaTR compromiso) {
		compromisosConvoctoriaTR.remove(compromiso);
	}

	public List<CompromisoConvocatoriaTR> getListaCompromisoConvocatoriaTR() {
		List<CompromisoConvocatoriaTR> listaCompromisos = new ArrayList<CompromisoConvocatoriaTR>();
		if (compromisosConvoctoriaTR != null) {
			Iterator<CompromisoConvocatoriaTR> i = compromisosConvoctoriaTR.iterator();
			while (i.hasNext()) {
				CompromisoConvocatoriaTR compromisoTR = i.next();
				listaCompromisos.add(compromisoTR);
			}
		}
		return listaCompromisos;
	}

	// incompatibilidades
	public void adicionarIncompatibilidad(IncompatibilidadConvocatoriaTR incompatibilidad) {
		if (incompatibilidadesConvoctoriaTR == null) {
			incompatibilidadesConvoctoriaTR = new HashSet<IncompatibilidadConvocatoriaTR>();
		}
		incompatibilidad.setOrden(Long.parseLong(String.valueOf(getListaIncompatibilidadConvocatoriaTR().size() + 1)));
		incompatibilidad.setConvocatoriaTR(this);
		incompatibilidadesConvoctoriaTR.add(incompatibilidad);
	}

	public void borrarIncompatibilidad(IncompatibilidadConvocatoriaTR incompatibilidad) {
		incompatibilidadesConvoctoriaTR.remove(incompatibilidad);
	}

	public List<IncompatibilidadConvocatoriaTR> getListaIncompatibilidadConvocatoriaTR() {
		List<IncompatibilidadConvocatoriaTR> listaIncompatibilidades = new ArrayList<IncompatibilidadConvocatoriaTR>();
		if (incompatibilidadesConvoctoriaTR != null) {
			Iterator<IncompatibilidadConvocatoriaTR> i = incompatibilidadesConvoctoriaTR.iterator();
			while (i.hasNext()) {
				IncompatibilidadConvocatoriaTR incompatibilidadTR = i.next();
				listaIncompatibilidades.add(incompatibilidadTR);
			}
		}
		return listaIncompatibilidades;
	}

	public void adicionarDependencia(DependenciaConvocatoriaTR dependencia) {
		if (dependenciasConvoctoriaTR == null) {
			dependenciasConvoctoriaTR = new HashSet<DependenciaConvocatoriaTR>();
		}
		dependencia.setConvocatoriaTR(this);
		dependenciasConvoctoriaTR.add(dependencia);
	}

	public void borrarDependencia(DependenciaConvocatoriaTR dependencia) {
		dependenciasConvoctoriaTR.remove(dependencia);
	}

	public List<DependenciaConvocatoriaTR> getListaDependenciasConvocatoriraTR() {
		List<DependenciaConvocatoriaTR> listaDependencias = new ArrayList<DependenciaConvocatoriaTR>();
		if (dependenciasConvoctoriaTR != null) {
			Iterator<DependenciaConvocatoriaTR> i = dependenciasConvoctoriaTR.iterator();
			while (i.hasNext()) {
				DependenciaConvocatoriaTR dependenciaTR = i.next();
				listaDependencias.add(dependenciaTR);
			}
		}
		return listaDependencias;
	}

	public String guardarInformacionGeneral() {
		return "";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoConvocatoria() {
		return tipoConvocatoria;
	}

	public void setTipoConvocatoria(String tipoConvocatoria) {
		this.tipoConvocatoria = tipoConvocatoria;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public String getDirigidoA() {
		return dirigidoA;
	}

	public void setDirigidoA(String dirigidoA) {
		this.dirigidoA = dirigidoA;
	}

	public String getRecursosDisponibles() {
		return recursosDisponibles;
	}

	public void setRecursosDisponibles(String recursosDisponibles) {
		this.recursosDisponibles = recursosDisponibles;
	}

	public String getUnidadEjecutra() {
		return unidadEjecutra;
	}

	public void setUnidadEjecutra(String unidadEjecutra) {
		this.unidadEjecutra = unidadEjecutra;
	}

	public String getApoyoGanadores() {
		return apoyoGanadores;
	}

	public void setApoyoGanadores(String apoyoGanadores) {
		this.apoyoGanadores = apoyoGanadores;
	}

	public String getLiquidacionProyectos() {
		return liquidacionProyectos;
	}

	public void setLiquidacionProyectos(String liquidacionProyectos) {
		this.liquidacionProyectos = liquidacionProyectos;
	}

	public Long getDuracion() {
		return duracion;
	}

	public void setDuracion(Long duracion) {
		this.duracion = duracion;
	}

	public String getDependeciaVerificacionRequisito() {
		return dependeciaVerificacionRequisito;
	}

	public void setDependeciaVerificacionRequisito(String dependeciaVerificacionRequisito) {
		this.dependeciaVerificacionRequisito = dependeciaVerificacionRequisito;
	}

	public String getTipoEvaluacion() {
		return tipoEvaluacion;
	}

	public void setTipoEvaluacion(String tipoEvaluacion) {
		this.tipoEvaluacion = tipoEvaluacion;
	}

	public String getTipoPuntajeEvaluacion() {
		return tipoPuntajeEvaluacion;
	}

	public void setTipoPuntajeEvaluacion(String tipoPuntajeEvaluacion) {
		this.tipoPuntajeEvaluacion = tipoPuntajeEvaluacion;
	}

	public String getResponsableProcesoEvaluacion() {
		return responsableProcesoEvaluacion;
	}

	public void setResponsableProcesoEvaluacion(String responsableProcesoEvaluacion) {
		this.responsableProcesoEvaluacion = responsableProcesoEvaluacion;
	}

	public String getSubtipoEvaluacion() {
		return subtipoEvaluacion;
	}

	public void setSubtipoEvaluacion(String subtipoEvaluacion) {
		this.subtipoEvaluacion = subtipoEvaluacion;
	}

	public String getProcesoSelecicon() {
		return procesoSelecicon;
	}

	public void setProcesoSelecicon(String procesoSelecicon) {
		this.procesoSelecicon = procesoSelecicon;
	}

	public String getCriteriosDesempate() {
		return criteriosDesempate;
	}

	public void setCriteriosDesempate(String criteriosDesempate) {
		this.criteriosDesempate = criteriosDesempate;
	}

	public String getTipoReclamacion() {
		return tipoReclamacion;
	}

	public void setTipoReclamacion(String tipoReclamacion) {
		this.tipoReclamacion = tipoReclamacion;
	}

	public Long getDiasParaSolicitarReclamacionesReq() {
		return diasParaSolicitarReclamacionesReq;
	}

	public void setDiasParaSolicitarReclamacionesReq(Long diasParaSolicitarReclamacionesReq) {
		this.diasParaSolicitarReclamacionesReq = diasParaSolicitarReclamacionesReq;
	}

	public String getDependenciaResponsableResponderReclamacionReq() {
		return dependenciaResponsableResponderReclamacionReq;
	}

	public void setDependenciaResponsableResponderReclamacionReq(String dependenciaResponsableResponderReclamacionReq) {
		this.dependenciaResponsableResponderReclamacionReq = dependenciaResponsableResponderReclamacionReq;
	}

	public Long getDiasParaResponderReclamacionesReq() {
		return diasParaResponderReclamacionesReq;
	}

	public void setDiasParaResponderReclamacionesReq(Long diasParaResponderReclamacionesReq) {
		this.diasParaResponderReclamacionesReq = diasParaResponderReclamacionesReq;
	}

	public String getTipoReclamacionEvaluacion() {
		return tipoReclamacionEvaluacion;
	}

	public void setTipoReclamacionEvaluacion(String tipoReclamacionEvaluacion) {
		this.tipoReclamacionEvaluacion = tipoReclamacionEvaluacion;
	}

	public Long getDiasParaSolicitarReclamacionesEva() {
		return diasParaSolicitarReclamacionesEva;
	}

	public void setDiasParaSolicitarReclamacionesEva(Long diasParaSolicitarReclamacionesEva) {
		this.diasParaSolicitarReclamacionesEva = diasParaSolicitarReclamacionesEva;
	}

	public String getDependenciaResponsableResponderReclamacionEva() {
		return dependenciaResponsableResponderReclamacionEva;
	}

	public void setDependenciaResponsableResponderReclamacionEva(String dependenciaResponsableResponderReclamacionEva) {
		this.dependenciaResponsableResponderReclamacionEva = dependenciaResponsableResponderReclamacionEva;
	}

	public Long getDiasParaResponderReclamacionesEva() {
		return diasParaResponderReclamacionesEva;
	}

	public void setDiasParaResponderReclamacionesEva(Long diasParaResponderReclamacionesEva) {
		this.diasParaResponderReclamacionesEva = diasParaResponderReclamacionesEva;
	}

	public String getTextoPublicacionGanadores() {
		return textoPublicacionGanadores;
	}

	public void setTextoPublicacionGanadores(String textoPublicacionGanadores) {
		this.textoPublicacionGanadores = textoPublicacionGanadores;
	}

	public String getTextoProductosAcademicos() {
		return textoProductosAcademicos;
	}

	public void setTextoProductosAcademicos(String textoProductosAcademicos) {
		this.textoProductosAcademicos = textoProductosAcademicos;
	}

	public String getTipoCalendario() {
		return tipoCalendario;
	}

	public void setTipoCalendario(String tipoCalendario) {
		this.tipoCalendario = tipoCalendario;
	}

	public String getCronogramaCortes() {
		return cronogramaCortes;
	}

	public void setCronogramaCortes(String cronogramaCortes) {
		this.cronogramaCortes = cronogramaCortes;
	}

	public Date getFechaLanzamientoConvocatoria() {
		return fechaLanzamientoConvocatoria;
	}

	public void setFechaLanzamientoConvocatoria(Date fechaLanzamientoConvocatoria) {
		this.fechaLanzamientoConvocatoria = fechaLanzamientoConvocatoria;
	}

	public Date getFechaPublicacionResultados() {
		return fechaPublicacionResultados;
	}

	public void setFechaPublicacionResultados(Date fechaPublicacionResultados) {
		this.fechaPublicacionResultados = fechaPublicacionResultados;
	}

	public Date getFechaInicioInscripciones() {
		return fechaInicioInscripciones;
	}

	public void setFechaInicioInscripciones(Date fechaInicioInscripciones) {
		this.fechaInicioInscripciones = fechaInicioInscripciones;
	}

	public Date getFechaFinInscripciones() {
		return fechaFinInscripciones;
	}

	public void setFechaFinInscripciones(Date fechaFinInscripciones) {
		this.fechaFinInscripciones = fechaFinInscripciones;
	}

	public Date getFechaInicioReclamacionesRequisitos() {
		return fechaInicioReclamacionesRequisitos;
	}

	public void setFechaInicioReclamacionesRequisitos(Date fechaInicioReclamacionesRequisitos) {
		this.fechaInicioReclamacionesRequisitos = fechaInicioReclamacionesRequisitos;
	}

	public Date getFechaFinReclamacionesRequisitos() {
		return fechaFinReclamacionesRequisitos;
	}

	public void setFechaFinReclamacionesRequisitos(Date fechaFinReclamacionesRequisitos) {
		this.fechaFinReclamacionesRequisitos = fechaFinReclamacionesRequisitos;
	}

	public Date getFechaRespuestaReclamacionRequisitos() {
		return fechaRespuestaReclamacionRequisitos;
	}

	public void setFechaRespuestaReclamacionRequisitos(Date fechaRespuestaReclamacionRequisitos) {
		this.fechaRespuestaReclamacionRequisitos = fechaRespuestaReclamacionRequisitos;
	}

	public Date getFechaInicioEvaluacion() {
		return fechaInicioEvaluacion;
	}

	public void setFechaInicioEvaluacion(Date fechaInicioEvaluacion) {
		this.fechaInicioEvaluacion = fechaInicioEvaluacion;
	}

	public Date getFechaFinEvaluacion() {
		return fechaFinEvaluacion;
	}

	public void setFechaFinEvaluacion(Date fechaFinEvaluacion) {
		this.fechaFinEvaluacion = fechaFinEvaluacion;
	}

	public Date getFechaPublicacionPropuestasSeleccionadas() {
		return fechaPublicacionPropuestasSeleccionadas;
	}

	public void setFechaPublicacionPropuestasSeleccionadas(Date fechaPublicacionPropuestasSeleccionadas) {
		this.fechaPublicacionPropuestasSeleccionadas = fechaPublicacionPropuestasSeleccionadas;
	}

	public Date getFechaInicioReclamacionesEvaluacion() {
		return fechaInicioReclamacionesEvaluacion;
	}

	public void setFechaInicioReclamacionesEvaluacion(Date fechaInicioReclamacionesEvaluacion) {
		this.fechaInicioReclamacionesEvaluacion = fechaInicioReclamacionesEvaluacion;
	}

	public Date getFechaFinReclamacionesEvaluacion() {
		return fechaFinReclamacionesEvaluacion;
	}

	public void setFechaFinReclamacionesEvaluacion(Date fechaFinReclamacionesEvaluacion) {
		this.fechaFinReclamacionesEvaluacion = fechaFinReclamacionesEvaluacion;
	}

	public Date getFechaRespuestaReclamacionesEvaluacion() {
		return fechaRespuestaReclamacionesEvaluacion;
	}

	public void setFechaRespuestaReclamacionesEvaluacion(Date fechaRespuestaReclamacionesEvaluacion) {
		this.fechaRespuestaReclamacionesEvaluacion = fechaRespuestaReclamacionesEvaluacion;
	}

	public Date getFechaPublicacionGanadores() {
		return fechaPublicacionGanadores;
	}

	public void setFechaPublicacionGanadores(Date fechaPublicacionGanadores) {
		this.fechaPublicacionGanadores = fechaPublicacionGanadores;
	}

	public String getDependenciaResponsableSeguimientoTecnico() {
		return dependenciaResponsableSeguimientoTecnico;
	}

	public void setDependenciaResponsableSeguimientoTecnico(String dependenciaResponsableSeguimientoTecnico) {
		this.dependenciaResponsableSeguimientoTecnico = dependenciaResponsableSeguimientoTecnico;
	}

	public String getPropuestasModificablesEjecucion() {
		return propuestasModificablesEjecucion;
	}

	public void setPropuestasModificablesEjecucion(String propuestasModificablesEjecucion) {
		this.propuestasModificablesEjecucion = propuestasModificablesEjecucion;
	}

	public String getTextoConsideraciones() {
		return textoConsideraciones;
	}

	public void setTextoConsideraciones(String textoConsideraciones) {
		this.textoConsideraciones = textoConsideraciones;
	}

	public String getTextoMayorInformacion() {
		return textoMayorInformacion;
	}

	public void setTextoMayorInformacion(String textoMayorInformacion) {
		this.textoMayorInformacion = textoMayorInformacion;
	}

	public Dependencia getDependenciaPrincipal() {
		return dependenciaPrincipal;
	}

	public void setDependenciaPrincipal(Dependencia dependenciaPrincipal) {
		this.dependenciaPrincipal = dependenciaPrincipal;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Persona getPersonaMod() {
		return personaMod;
	}

	public void setPersonaMod(Persona personaMod) {
		this.personaMod = personaMod;
	}

	public String getIntroduccion() {
		return introduccion;
	}

	public void setIntroduccion(String introduccion) {
		this.introduccion = introduccion;
	}

	public Set<DependenciaConvocatoriaTR> getDependenciasConvoctoriaTR() {
		return dependenciasConvoctoriaTR;
	}

	public void setDependenciasConvoctoriaTR(Set<DependenciaConvocatoriaTR> dependenciasConvoctoriaTR) {
		this.dependenciasConvoctoriaTR = dependenciasConvoctoriaTR;
	}

	public Set<ModalidadConvocatoriaTR> getModalidadesConvoctoriaTR() {
		return modalidadesConvoctoriaTR;
	}

	public void setModalidadesConvoctoriaTR(Set<ModalidadConvocatoriaTR> modalidadesConvoctoriaTR) {
		this.modalidadesConvoctoriaTR = modalidadesConvoctoriaTR;
	}

	public Set<RequisitoConvocatoriaTR> getRequisitosConvoctoriaTR() {
		return requisitosConvoctoriaTR;
	}

	public void setRequisitosConvoctoriaTR(Set<RequisitoConvocatoriaTR> requisitosConvoctoriaTR) {
		this.requisitosConvoctoriaTR = requisitosConvoctoriaTR;
	}

	public Set<DocumentoConvocatoriaTR> getDocumentacionConvoctoriaTR() {
		return documentacionConvoctoriaTR;
	}

	public void setDocumentacionConvoctoriaTR(Set<DocumentoConvocatoriaTR> documentacionConvoctoriaTR) {
		this.documentacionConvoctoriaTR = documentacionConvoctoriaTR;
	}

	public Set<CompromisoConvocatoriaTR> getCompromisosConvoctoriaTR() {
		return compromisosConvoctoriaTR;
	}

	public void setCompromisosConvoctoriaTR(Set<CompromisoConvocatoriaTR> compromisosConvoctoriaTR) {
		this.compromisosConvoctoriaTR = compromisosConvoctoriaTR;
	}

	public Set<IncompatibilidadConvocatoriaTR> getIncompatibilidadesConvoctoriaTR() {
		return incompatibilidadesConvoctoriaTR;
	}

	public void setIncompatibilidadesConvoctoriaTR(Set<IncompatibilidadConvocatoriaTR> incompatibilidadesConvoctoriaTR) {
		this.incompatibilidadesConvoctoriaTR = incompatibilidadesConvoctoriaTR;
	}

	public Set<RubrosFinanciablesConvocatoriaTR> getRubrosConvoctoriaTR() {
		return rubrosConvoctoriaTR;
	}

	public void setRubrosConvoctoriaTR(Set<RubrosFinanciablesConvocatoriaTR> rubrosConvoctoriaTR) {
		this.rubrosConvoctoriaTR = rubrosConvoctoriaTR;
	}

	public Long getPuntajeMinimoEvaluacion() {
		return puntajeMinimoEvaluacion;
	}

	public void setPuntajeMinimoEvaluacion(Long puntajeMinimoEvaluacion) {
		this.puntajeMinimoEvaluacion = puntajeMinimoEvaluacion;
	}

	public String getPropuestasFinanciables() {
		return propuestasFinanciables;
	}

	public void setPropuestasFinanciables(String propuestasFinanciables) {
		this.propuestasFinanciables = propuestasFinanciables;
	}

	public Set<CriteriosEvaluacionConvocatoriaTR> getCriteriosEvaluacionSeleccionConvoctoriaTR() {
		return criteriosEvaluacionSeleccionConvoctoriaTR;
	}

	public void setCriteriosEvaluacionSeleccionConvoctoriaTR(Set<CriteriosEvaluacionConvocatoriaTR> criteriosEvaluacionSeleccionConvoctoriaTR) {
		this.criteriosEvaluacionSeleccionConvoctoriaTR = criteriosEvaluacionSeleccionConvoctoriaTR;
	}

	public String getTipoPuntajeSeleccion() {
		return tipoPuntajeSeleccion;
	}

	public void setTipoPuntajeSeleccion(String tipoPuntajeSeleccion) {
		this.tipoPuntajeSeleccion = tipoPuntajeSeleccion;
	}

	public String getResponsableProcesoSeleccion() {
		return responsableProcesoSeleccion;
	}

	public void setResponsableProcesoSeleccion(String responsableProcesoSeleccion) {
		this.responsableProcesoSeleccion = responsableProcesoSeleccion;
	}

	public Set<ProductosConvocatoriaTR> getProductosConvoctoriaTR() {
		return productosConvoctoriaTR;
	}

	public void setProductosConvoctoriaTR(Set<ProductosConvocatoriaTR> productosConvoctoriaTR) {
		this.productosConvoctoriaTR = productosConvoctoriaTR;
	}

	public String getOtroTipoConvocatoria() {
		return otroTipoConvocatoria;
	}

	public void setOtroTipoConvocatoria(String otroTipoConvocatoria) {
		this.otroTipoConvocatoria = otroTipoConvocatoria;
	}

	public String getPresentacion() {
		return presentacion;
	}

	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}

	public String getAntecedentes() {
		return antecedentes;
	}

	public void setAntecedentes(String antecedentes) {
		this.antecedentes = antecedentes;
	}

	public Set<CamposAdicionalesConvocatoriaTR> getCamposAdicionalesConvoctoriaTR() {
		return camposAdicionalesConvoctoriaTR;
	}

	public void setCamposAdicionalesConvoctoriaTR(Set<CamposAdicionalesConvocatoriaTR> camposAdicionalesConvoctoriaTR) {
		this.camposAdicionalesConvoctoriaTR = camposAdicionalesConvoctoriaTR;
	}

	public Set<ObjetivosEspecificosConvocatoriaTR> getObjetivosEspecificosConvoctoriaTR() {
		return objetivosEspecificosConvoctoriaTR;
	}

	public void setObjetivosEspecificosConvoctoriaTR(Set<ObjetivosEspecificosConvocatoriaTR> objetivosEspecificosConvoctoriaTR) {
		this.objetivosEspecificosConvoctoriaTR = objetivosEspecificosConvoctoriaTR;
	}

	public String getDescripcionProcesoEvaluacion() {
		return descripcionProcesoEvaluacion;
	}

	public void setDescripcionProcesoEvaluacion(String descripcionProcesoEvaluacion) {
		this.descripcionProcesoEvaluacion = descripcionProcesoEvaluacion;
	}

	public String getTextoResultados() {
		return textoResultados;
	}

	public void setTextoResultados(String textoResultados) {
		this.textoResultados = textoResultados;
	}

	public String getConsideracionesEticas() {
		return consideracionesEticas;
	}

	public void setConsideracionesEticas(String consideracionesEticas) {
		this.consideracionesEticas = consideracionesEticas;
	}

	public String getPropiedadIntelectual() {
		return propiedadIntelectual;
	}

	public void setPropiedadIntelectual(String propiedadIntelectual) {
		this.propiedadIntelectual = propiedadIntelectual;
	}

}
