package co.edu.unal.hermes.vista.convocatorias.terminosReferencia;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.CamposAdicionalesConvocatoriaTR;
import co.edu.unal.hermes.modelo.CompromisoConvocatoriaTR;
import co.edu.unal.hermes.modelo.ConvocatoriaTerminosReferencia;
import co.edu.unal.hermes.modelo.CriteriosEvaluacionConvocatoriaTR;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DependenciaConvocatoriaTR;
import co.edu.unal.hermes.modelo.DocumentoConvocatoriaTR;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.IncompatibilidadConvocatoriaTR;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.ModalidadConvocatoriaTR;
import co.edu.unal.hermes.modelo.ObjetivosEspecificosConvocatoriaTR;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.ProductoTipo;
import co.edu.unal.hermes.modelo.ProductosConvocatoriaTR;
import co.edu.unal.hermes.modelo.RequisitoConvocatoriaTR;
import co.edu.unal.hermes.modelo.RubrosFinanciablesConvocatoriaTR;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.TipoRubro;
import co.edu.unal.hermes.vista.ManejadorAutenticacion;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorTerminosReferenciaConvocatoria extends ManejadorBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ConvocatoriaTerminosReferencia convocatoria;
	private SelectItem[] tipoConvocatoriaItem;
	private final String DOM_TIPO_CONVOCATORIA = "TIPO_CONVOCATORIAS";
	private Long idConvTerRef;
	private Dependencia dependenciaActual;
	private Persona personaActual;
	private String sedeSel;
	private SelectItem[] sedeItem;
	private boolean mostrarFacultades = false;
	private List<Dependencia> dependenciasUN;
	private List<SelectItem> dependenciaItem;
	private String dependenciaConvocatoria;
	private List<SelectItem> facultadItem;
	private String facultadSel;
	private String nombreCampoAdicional;
	private String descripcionCampoAdicional;
	private DependenciaConvocatoriaTR dependenciaTRSeleccionada;
	// modalidades
	private ModalidadConvocatoriaTR modalidad;
	private ModalidadConvocatoriaTR modalidadTRSeleccionada;
	// Requisitos
	private RequisitoConvocatoriaTR requisito;
	private RequisitoConvocatoriaTR requisitoTRSeleccionada;

	private DocumentoConvocatoriaTR documento;
	private DocumentoConvocatoriaTR documentoTRSeleccionada;

	private CompromisoConvocatoriaTR compromiso;
	private CompromisoConvocatoriaTR compromisoTRSeleccionada;

	private IncompatibilidadConvocatoriaTR incompatibilidad;
	private IncompatibilidadConvocatoriaTR incompatibilidadTRSeleccionada;

	private RubrosFinanciablesConvocatoriaTR rubro;
	private RubrosFinanciablesConvocatoriaTR rubroTRSeleccionado;
	private List<SelectItem> tipoRubroItems;

	private CriteriosEvaluacionConvocatoriaTR criterioEvaluacion;
	private CriteriosEvaluacionConvocatoriaTR criterioEvaluacionSeleccionado;

	private CriteriosEvaluacionConvocatoriaTR criterioSeleccion;
	private CriteriosEvaluacionConvocatoriaTR criterioSeleccionSeleccionado;

	private ProductosConvocatoriaTR producto;
	private ProductosConvocatoriaTR productoTRSeleccionado;
	private List<SelectItem> tipoProductoItems;

	private CamposAdicionalesConvocatoriaTR campo;
	private CamposAdicionalesConvocatoriaTR campoTRSeleccionado;

	private ObjetivosEspecificosConvocatoriaTR objetivo;
	private ObjetivosEspecificosConvocatoriaTR objetivoTRSeleccionado;

	public ManejadorTerminosReferenciaConvocatoria() {
		super();
		Long idConvTRSel = (Long) sesion.getAttribute("convocatoriaTRSel");
		cargarListas();
		personaActual = (Persona) sesion.getAttribute("persona");
		if (personaActual != null) {
			InvestigadorInterno ii = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());
			if (ii != null) {
				dependenciaActual = ii.getDependencia();
			}
		}

		// obtener convocatoria si existe
		if (idConvTRSel != null) {
			convocatoria = servicioModalidad.obtenerConvocatoriaTerminosReferenciaPorId(idConvTRSel);
		} else {
			convocatoria = new ConvocatoriaTerminosReferencia();
		}
		campo = new CamposAdicionalesConvocatoriaTR();
		objetivo = new ObjetivosEspecificosConvocatoriaTR();
	}

	// objetivos especificos
	public void adicionarObjetivo() {
		if (objetivo.getDescripcion() != null) {
			this.convocatoria.adicionaObjetivoEspecifico(this.objetivo);
			this.objetivo = new ObjetivosEspecificosConvocatoriaTR();
			campo = new CamposAdicionalesConvocatoriaTR();
		} else {
			mensajeError("Por favor ingrese la información completa del campo.");
		}
	}

	public void eliminarObjetivos() {
		this.convocatoria.borrarObjetivoEspecifico(objetivoTRSeleccionado);
	}

	// campos adicionales
	public void adicionarCampoAdicional() {
		if (campo.getNombre() != null && campo.getDescripcion() != null) {
			campo.setNombre(campo.getNombre().toUpperCase());
			this.convocatoria.adicionaCampo(this.campo);
			this.campo = new CamposAdicionalesConvocatoriaTR();
		} else {
			mensajeError("Por favor ingrese la información completa del campo.");
		}
	}

	public void eliminarCampoAdicional() {
		this.convocatoria.borrarCampo(campoTRSeleccionado);
	}

	// productos
	public void adicionarProducto() {

		boolean existeMod = false;
		if (producto.getProducto().getId() != null) {
			// Se verifica que no exista.
			if (this.convocatoria.getListaProductosConvocatoriaTR().size() > 0) {
				if (this.convocatoria.getListaProductosConvocatoriaTR().contains(this.getProducto())) {
					existeMod = true;
				}
			}
			if (!existeMod) {
				String hqlProducto = "select #id e.id, #nombre e.nombre from ProductoTipo e where e.id in ('" + producto.getProducto().getId() + "')";
				List<ProductoTipo> listaTipoProducto = servicioGeneral.obtenerObjetosLimitado(ProductoTipo.class, hqlProducto);
				ProductoTipo tr = listaTipoProducto.get(0);
				this.producto.setProducto(tr);
				this.convocatoria.adicionarProducto(this.producto);
				this.producto = new ProductosConvocatoriaTR();
			} else {
				mensajeError("El producto seleccionado ya se encuentra registrado.");
			}
		} else {
			mensajeError("Por favor ingrese la información completa del producto.");
		}
	}

	public void eliminarProducto() {
		this.convocatoria.borrarProducto(productoTRSeleccionado);
	}

	// criterios evaluación
	public void adicionarCriterioEvaluacion() {

		boolean existeMod = false;
		if (!esCadenaVacia(criterioEvaluacion.getDescripcion())) {
			// Se verifica que no exista.
			this.criterioEvaluacion.setTipoPuntaje("E");
			if (this.convocatoria.getListaCriteriosEvaluacionConvocatoriaTR().size() > 0) {
				if (this.convocatoria.getListaCriteriosEvaluacionConvocatoriaTR().contains(this.getCriterioEvaluacion())) {
					existeMod = true;
				}
			}
			if (!existeMod) {
				this.convocatoria.adicionarCriterioEvaluacion(this.criterioEvaluacion);
				this.criterioEvaluacion = new CriteriosEvaluacionConvocatoriaTR();
			} else {
				mensajeError("El criterio de evaluación ya se encuentra registrado.");
			}
		} else {
			mensajeError("Por favor ingrese el criterio de evaluación");
		}
	}

	public void eliminarCriterioEvaluacion() {
		this.convocatoria.borrarCriterio(criterioEvaluacionSeleccionado);
	}

	// criterios selección
	public void adicionarCriterioSeleccion() {

		boolean existeMod = false;
		if (!esCadenaVacia(criterioSeleccion.getDescripcion())) {
			// Se verifica que no exista.
			if (this.convocatoria.getListaCriteriosSeleccionConvocatoriaTR().size() > 0) {
				this.criterioSeleccion.setTipoPuntaje("S");
				if (this.convocatoria.getListaCriteriosSeleccionConvocatoriaTR().contains(this.getCriterioSeleccion())) {
					existeMod = true;
				}
			}
			if (!existeMod) {
				this.convocatoria.adicionarCriterioSeleccion(this.criterioSeleccion);
				this.criterioSeleccion = new CriteriosEvaluacionConvocatoriaTR();
			} else {
				mensajeError("El criterio de selección ya se encuentra registrado.");
			}
		} else {
			mensajeError("Por favor ingrese el criterio de selección");
		}
	}

	public void eliminarCriterioSeleccion() {
		this.convocatoria.borrarCriterio(criterioSeleccionSeleccionado);
	}

	// rubros
	public void adicionarRubro() {

		boolean existeMod = false;
		if ((rubro.getTipoRubro().getId() != null) || !esCadenaVacia(rubro.getJustificacion())) {
			// Se verifica que no exista.
			if (this.convocatoria.getListaRubrosConvocatoriaTR().size() > 0) {
				if (this.convocatoria.getListaRubrosConvocatoriaTR().contains(this.getRubro())) {
					existeMod = true;
				}
			}
			if (!existeMod) {
				String hqlRubros = "select #id e.id, #nombre e.nombre from TipoRubro e where e.id in (" + rubro.getTipoRubro().getId() + ")";
				List<TipoRubro> listaTipoRubro = servicioGeneral.obtenerObjetosLimitado(TipoRubro.class, hqlRubros);
				TipoRubro tr = listaTipoRubro.get(0);
				this.rubro.setTipoRubro(tr);
				this.convocatoria.adicionarRubro(this.rubro);
				this.rubro = new RubrosFinanciablesConvocatoriaTR();
			} else {
				mensajeError("El rubro seleccionado ya se encuentra registrado.");
			}
		} else {
			mensajeError("Por favor ingrese la información completa del rubro.");
		}
	}

	public void eliminarRubro() {
		this.convocatoria.borrarRubro(rubroTRSeleccionado);
	}

	// modalidad
	public void adicionarModalidad() {

		boolean existeMod = false;
		if (!esCadenaVacia(modalidad.getModalidad())) {
			// Se verifica que no exista.
			if (this.convocatoria.getListaModalidadConvocatoriaTR().size() > 0) {
				if (this.convocatoria.getListaModalidadConvocatoriaTR().contains(this.getModalidad())) {
					existeMod = true;
				}
			}
			if (!existeMod) {
				this.convocatoria.adicionarModalidad(this.modalidad);
				this.modalidad = new ModalidadConvocatoriaTR();
			} else {
				mensajeError("La modalidade ya se encuentra registrada.");
			}
		} else {
			mensajeError("Por favor ingrese el nombre de la modalidad.");
		}
	}

	public void eliminarModalidad() {
		this.convocatoria.borrarModalidad(modalidadTRSeleccionada);
	}

	public void cargarListaRubros() {
		String hqlRubros = "select #id e.id, #nombre e.nombre from TipoRubro e where e.id in (3, 14, 131, 190, 107, 125, 9, 70, 10, 12, 13, 111, 109, 156, 58, 193, 194, 195, 196, 155)";
		List<TipoRubro> listaTipoRubro = servicioGeneral.obtenerObjetosLimitado(TipoRubro.class, hqlRubros);
		if (!listaTipoRubro.isEmpty()) {
			tipoRubroItems = new ArrayList<SelectItem>();

			Iterator<TipoRubro> i = listaTipoRubro.iterator();
			while (i.hasNext()) {
				TipoRubro tipoRubro = i.next();
				tipoRubroItems.add(new SelectItem(tipoRubro.getId(), tipoRubro.getNombre()));
			}
		}
	}

	public void cargarListaProductos() {
		String hqlProductos = "select #id e.id, #nombre e.nombre from ProductoTipo e where e.id in ('77','32','58','97','98','99','100','101','102','103','104','105','106','107','108','109','110','111','112','113','114','115','116','118','119','120','121','122','123','124','125','126','127','128','130','131','133','134','135','136','138','139','140','141','142','143','144','145','146','147','148','149','150','151','153','154','155','157','158','160','162','163','192','74','218','221')";
		List<ProductoTipo> listaTipoProducto = servicioGeneral.obtenerObjetosLimitado(ProductoTipo.class, hqlProductos);
		if (!listaTipoProducto.isEmpty()) {
			tipoProductoItems = new ArrayList<SelectItem>();

			Iterator<ProductoTipo> i = listaTipoProducto.iterator();
			while (i.hasNext()) {
				ProductoTipo tipoPro = i.next();
				tipoProductoItems.add(new SelectItem(tipoPro.getId(), tipoPro.getNombre()));
			}
		}
	}

	// requisitos
	public void adicionarRequisito() {

		boolean existeMod = false;
		if (!esCadenaVacia(requisito.getRequisito())) {
			// Se verifica que no exista.
			if (this.convocatoria.getListaRequisitoConvocatoriaTR().size() > 0) {
				if (this.convocatoria.getListaRequisitoConvocatoriaTR().contains(this.getRequisito())) {
					existeMod = true;
				}
			}
			if (!existeMod) {
				this.convocatoria.adicionarRequisito(this.requisito);
				requisito = new RequisitoConvocatoriaTR();
			} else {
				mensajeError("El requisito ya se encuentra registrado.");
			}
		} else {
			mensajeError("Por favor ingrese el requisito de la convocatoria.");
		}
	}

	public void eliminarRequisito() {
		this.convocatoria.borrarRequisito(requisitoTRSeleccionada);
	}

	// documentación
	public void adicionarDocumento() {

		boolean existeMod = false;
		if (!esCadenaVacia(documento.getDocumento())) {
			// Se verifica que no exista.
			if (this.convocatoria.getListaDocumentoConvocatoriaTR().size() > 0) {
				if (this.convocatoria.getListaDocumentoConvocatoriaTR().contains(this.getDocumento())) {
					existeMod = true;
				}
			}
			if (!existeMod) {
				this.convocatoria.adicionarDocumento(this.documento);
				this.documento = new DocumentoConvocatoriaTR();
			} else {
				mensajeError("El documento ya se encuentra registrado.");
			}
		} else {
			mensajeError("Por favor ingrese el documento asociado a la convocatoria.");
		}
	}

	public void eliminarDocumento() {
		this.convocatoria.borrarDocumento(documentoTRSeleccionada);
	}

	// compromisos
	public void adicionarCompromiso() {

		boolean existeMod = false;
		if (!esCadenaVacia(compromiso.getCompromiso())) {
			// Se verifica que no exista.
			if (this.convocatoria.getListaCompromisoConvocatoriaTR().size() > 0) {
				if (this.convocatoria.getListaCompromisoConvocatoriaTR().contains(this.getCompromiso())) {
					existeMod = true;
				}
			}
			if (!existeMod) {
				this.convocatoria.adicionarCompromiso(this.compromiso);
				this.compromiso = new CompromisoConvocatoriaTR();
			} else {
				mensajeError("El compromiso ya se encuentra registrado.");
			}
		} else {
			mensajeError("Por favor ingrese los compromisos de la convocatoria.");
		}
	}

	public void eliminarCompromiso() {
		this.convocatoria.borrarCompromiso(compromisoTRSeleccionada);
	}

	// incompatibilidades
	public void adicionarIncompatibilidad() {

		boolean existeMod = false;
		if (!esCadenaVacia(incompatibilidad.getIncompatibilidad())) {
			// Se verifica que no exista.
			if (this.convocatoria.getListaIncompatibilidadConvocatoriaTR().size() > 0) {
				if (this.convocatoria.getListaIncompatibilidadConvocatoriaTR().contains(this.getIncompatibilidad())) {
					existeMod = true;
				}
			}
			if (!existeMod) {
				this.convocatoria.adicionarIncompatibilidad(this.incompatibilidad);
				this.incompatibilidad = new IncompatibilidadConvocatoriaTR();
			} else {
				mensajeError("La incompatibilidad ya se encuentra registrada.");
			}
		} else {
			mensajeError("Por favor ingrese las incompatibilidad de la convocatoria.");
		}
	}

	public void eliminarIncompatibilidad() {
		this.convocatoria.borrarIncompatibilidad(incompatibilidadTRSeleccionada);
	}

	public void cambiarSede() {

		if (!esCadenaVacia(sedeSel)) {
			// Si es sede de presencia nacional
			if ((new Sede(sedeSel)).isEsSedePresenciaNacional()) {
				mostrarFacultades = false;
				dependenciasUN = servicioDependencia.obtenerDependenciaXSede(sedeSel);
				dependenciaItem = servicioDependencia.crearSelectItem(dependenciasUN);
				dependenciaConvocatoria = "";
			} else {
				// Si es una sede con facultad.
				mostrarFacultades = true;
				List<Dependencia> facultadesUN = servicioDependencia.obtenerFacultadesXSede(sedeSel);
				facultadItem = servicioDependencia.crearSelectItem(facultadesUN);
				facultadSel = ((Dependencia) facultadesUN.get(0)).getId().toString();
				cambiarFacultad();
			}
		} else {
			dependenciaItem = new ArrayList<SelectItem>();
			dependenciaConvocatoria = "";
			mostrarFacultades = false;
		}
	}

	public void cambiarFacultad() {
		dependenciasUN = servicioDependencia.obtenerDependenciasXFacultad(facultadSel);

		// Si tiene mas dependencias la facultad.
		if (!esListaVacia(dependenciasUN)) {
			dependenciaItem = servicioDependencia.crearSelectItem(dependenciasUN);
		} else {
			// Si no tiene mas dependencias se carga la misma.
			dependenciasUN = new ArrayList<Dependencia>();
			dependenciasUN.add(servicioDependencia.obtenerDependencia(facultadSel));
			dependenciaItem = servicioDependencia.crearSelectItem(dependenciasUN);
		}

		dependenciaConvocatoria = "";
	}

	public void adicionarDependencia() {

		boolean existeDep = false;
		if (!esCadenaVacia(dependenciaConvocatoria) && !esCadenaVacia(sedeSel)) {
			// Se verifica que no exista.
			if (this.convocatoria.getListaDependenciasConvocatoriraTR().size() > 0) {
				DependenciaConvocatoriaTR dep = buscarDependenciaConvocatoriaTR(dependenciaConvocatoria, convocatoria.getListaDependenciasConvocatoriraTR());
				if (dep != null) {
					existeDep = true;
				}
			}
			if (!existeDep) {
				// Se busca dependencia en listado de dependencias.
				Dependencia dep = buscarDependencia(dependenciaConvocatoria, dependenciasUN);

				if (dep != null) {
					DependenciaConvocatoriaTR dependenciaAreaResponsabilidad = new DependenciaConvocatoriaTR();
					dependenciaAreaResponsabilidad.setDependencia(dep);
					this.convocatoria.adicionarDependencia(dependenciaAreaResponsabilidad);
				} else {
					mensajeError("La dependencia seleccionada no se encuentra disponible.");
				}
			} else {
				mensajeError("La dependencia seleccionada ya se encuentra registrada.");
			}
		} else {
			mensajeError("Por favor seleccione la dependencia a registrar.");
		}
	}

	public void eliminarDependencia() {
		this.convocatoria.borrarDependencia(dependenciaTRSeleccionada);
	}

	protected DependenciaConvocatoriaTR buscarDependenciaConvocatoriaTR(String id, List<DependenciaConvocatoriaTR> dependencias) {

		int i = 0;
		while (i < dependencias.size()) {
			DependenciaConvocatoriaTR dependencia = (DependenciaConvocatoriaTR) dependencias.get(i);
			if (id.equals(dependencia.getDependencia().getId())) {
				return dependencia;
			}
			i = i + 1;
		}
		return null;

	}

	public void cargarListas() {
		List<DominioDetalle> listaTipoConvocatoria = servicioGeneral.obtenerDominioDetalle(DOM_TIPO_CONVOCATORIA);
		tipoConvocatoriaItem = crearListaItems(listaTipoConvocatoria);

		// Se carga listado de sedes
		List<Sede> sedesUN = servicioGeneral.obtenerObjetos(Sede.class, "select e from Sede e where  e.id<>0 ");
		sedeItem = new SelectItem[sedesUN.size()];
		for (int i = 0; i < sedesUN.size(); i++) {
			Sede sede = (Sede) sedesUN.get(i);
			sedeItem[i] = new SelectItem(sede.getId(), sede.getNombre());
		}
		sedeSel = "";
		cambiarSede();

		modalidad = new ModalidadConvocatoriaTR();
		requisito = new RequisitoConvocatoriaTR();
		compromiso = new CompromisoConvocatoriaTR();
		documento = new DocumentoConvocatoriaTR();
		incompatibilidad = new IncompatibilidadConvocatoriaTR();
		cargarListaRubros();
		rubro = new RubrosFinanciablesConvocatoriaTR();
		criterioEvaluacion = new CriteriosEvaluacionConvocatoriaTR();
		criterioSeleccion = new CriteriosEvaluacionConvocatoriaTR();
		producto = new ProductosConvocatoriaTR();
		cargarListaProductos();
	}

	public boolean validarInformacionGeneral() {
		boolean val = true;

		if (this.convocatoria.getTipoConvocatoria() == null) {
			val = false;
			mensajeError("Por favor seleccione el tipo de convocatoria");
		} else {
			if (this.convocatoria.getTipoConvocatoria().equals("")) {
				mensajeError("Por favor seleccione el tipo de convocatoria");
			}
		}

		if (this.convocatoria.getTitulo() == null) {
			val = false;
			mensajeError("Por favor ingrese el título de la convocatoria");
		} else {
			if (this.convocatoria.getTitulo().equals("")) {
				mensajeError("Por favor ingrese el título de la convocatoria");
			}
		}

		if (this.convocatoria.getObjetivo() == null) {
			val = false;
			mensajeError("Por favor ingrese el objetivo de la convocatoria");
		} else {
			if (this.convocatoria.getObjetivo().equals("")) {
				mensajeError("Por favor ingrese el objetivo de la convocatoria");
			}
		}

		if (this.convocatoria.getDirigidoA() == null) {
			val = false;
			mensajeError("Por ingrese el campo \"Dirigido a\" de la convocatoria");
		} else {
			if (this.convocatoria.getDirigidoA().equals("")) {
				mensajeError("Por ingrese el campo \"Dirigido a\" de la convocatoria");
			}
		}

		if (this.convocatoria.getDuracion() == null) {
			val = false;
			mensajeError("Por ingrese la duración de la ejecución de los proyectos de la convocatoria");
		} else {
			if (this.convocatoria.getDuracion().equals("") || this.convocatoria.getDuracion().equals(0L)) {
				mensajeError("Por ingrese la duración de la ejecución de los proyectos de la convocatoria");
			} else {
				try {
					@SuppressWarnings("unused")
					Long num = this.convocatoria.getDuracion();
				} catch (Exception e) {
					e.getMessage();
					mensajeError("Por ingrese la duración de la ejecución de los proyectos de la convocatoria, solo números enteros sin puntos ni comas");
				}
			}
		}

		return val;
	}

	public String guardarInformacionGeneral() {
		if (validarInformacionGeneral()) {
			this.convocatoria.setEstado("I");
			this.convocatoria.setDependenciaPrincipal(dependenciaActual);
			this.convocatoria.setPersonaMod(personaActual);
			if (this.convocatoria.getFechaCreacion() == null) {
				this.convocatoria.setFechaCreacion(new Date());
				this.convocatoria.setFechaModificacion(new Date());
			} else {
				this.convocatoria.setFechaModificacion(new Date());
			}
			servicioGeneral.guardarObjeto(this.convocatoria);
			return "modalidadesConvocatoriaTR";
		} else {
			return "";
		}

	}
	
	public String guardarInformacionGeneralSalir() {
		if (validarInformacionGeneral()) {
			this.convocatoria.setEstado("I");
			this.convocatoria.setDependenciaPrincipal(dependenciaActual);
			this.convocatoria.setPersonaMod(personaActual);
			if (this.convocatoria.getFechaCreacion() == null) {
				this.convocatoria.setFechaCreacion(new Date());
				this.convocatoria.setFechaModificacion(new Date());
			} else {
				this.convocatoria.setFechaModificacion(new Date());
			}
			servicioGeneral.guardarObjeto(this.convocatoria);
			sesion.removeAttribute("manejadorTerminosReferenciaConvocatoria");
			return "proyectosInvestigador";
		} else {
			return "";
		}

	}

	public String guardarModalidades() {
		if (validarModalidades()) {
			servicioGeneral.guardarObjeto(this.convocatoria);
			return "informacionFinancieraConvocatoriaTR";
		} else {
			return "";
		}

	}
	
	public String guardarModalidadesSalir() {
		if (validarModalidades()) {
			servicioGeneral.guardarObjeto(this.convocatoria);
			sesion.removeAttribute("manejadorTerminosReferenciaConvocatoria");
			return "proyectosInvestigador";
		} else {
			return "";
		}

	}

	private boolean validarModalidades() {
		if (!this.convocatoria.getListaModalidadConvocatoriaTR().isEmpty()) {
			return true;
		} else {
			mensajeError("Por registre las modalidades de la convocatoria");
			return false;
		}
	}

	public String guardarInformacionFinanciera() {
		if (validarInformacionFinanciera()) {
			servicioGeneral.guardarObjeto(this.convocatoria);
			return "requisitosConvocatoriaTR";
		} else {
			return "";
		}

	}
	
	public String guardarInformacionFinancieraSalir() {
		if (validarInformacionFinanciera()) {
			servicioGeneral.guardarObjeto(this.convocatoria);
			sesion.removeAttribute("manejadorTerminosReferenciaConvocatoria");
			return "proyectosInvestigador";
		} else {
			return "";
		}

	}

	private boolean validarInformacionFinanciera() {
		boolean val = true;
		if (esCadenaVacia(this.convocatoria.getRecursosDisponibles())) {
			mensajeError("Por registre los recursos disponibles para la convocatoria y la fuente de dichos recursos");
			val = false;
		}

		if (esCadenaVacia(this.convocatoria.getUnidadEjecutra())) {
			mensajeError("Por registre la unidad ejecutora de la convocatoria.");
			val = false;
		}

		if (this.convocatoria.getListaRubrosConvocatoriaTR().isEmpty()) {
			mensajeError("Por registre los rubros financiables de la convocatoria");
			val = false;
		}

		if (esCadenaVacia(this.convocatoria.getLiquidacionProyectos())) {
			mensajeError("Por registre la información relacionada con liquidación de proyectos y devolución de saldos.");
			val = false;
		}

		return val;
	}

	public String guardarRequisitos() {
		if (validarRequisitos()) {
			servicioGeneral.guardarObjeto(this.convocatoria);
			return "documentacionConvocatoriaTR";
		} else {
			return "";
		}

	}
	
	public String guardarRequisitosSalir() {
		if (validarRequisitos()) {
			servicioGeneral.guardarObjeto(this.convocatoria);
			sesion.removeAttribute("manejadorTerminosReferenciaConvocatoria");
			return "proyectosInvestigador";
		} else {
			return "";
		}

	}

	private boolean validarRequisitos() {
		if (!this.convocatoria.getListaRequisitoConvocatoriaTR().isEmpty()) {
			return true;
		} else {
			mensajeError("Por registre los requisitos de la convocatoria");
			return false;
		}
	}

	public String guardarDocumentacion() {
		if (validarDocumentacion()) {
			servicioGeneral.guardarObjeto(this.convocatoria);
			return "seleccionGanadoresConvocatoriaTR";
		} else {
			return "";
		}

	}
	
	public String guardarDocumentacionSalir() {
		if (validarDocumentacion()) {
			servicioGeneral.guardarObjeto(this.convocatoria);
			sesion.removeAttribute("manejadorTerminosReferenciaConvocatoria");
			return "proyectosInvestigador";
		} else {
			return "";
		}

	}

	private boolean validarDocumentacion() {
		if (!this.convocatoria.getListaDocumentoConvocatoriaTR().isEmpty()) {
			return true;
		} else {
			mensajeError("Por registre la documentación de la convocatoria");
			return false;
		}
	}

	public String guardarSeleccionGanadores() {
		if (validarSeleccionGanadores()) {
			servicioGeneral.guardarObjeto(this.convocatoria);
			return "compromisosConvocatoriaTR";
		} else {
			return "";
		}

	}
	
	public String guardarSeleccionGanadoresSalir() {
		if (validarSeleccionGanadores()) {
			servicioGeneral.guardarObjeto(this.convocatoria);
			sesion.removeAttribute("manejadorTerminosReferenciaConvocatoria");
			return "proyectosInvestigador";
		} else {
			return "";
		}

	}

	private boolean validarSeleccionGanadores() {
		boolean val = true;
		if(this.convocatoria.getTipoEvaluacion().equals("E")){
			if (this.convocatoria.getDependeciaVerificacionRequisito().isEmpty()) {
				val = false;
				mensajeError("Por registre el responsable del proceso de verificación de requisitos mínimos.");
			}

			if (this.convocatoria.getResponsableProcesoEvaluacion().isEmpty()) {
				val = false;
				mensajeError("Por registre el responsable del proceso de evaluación.");
			}

			if (this.convocatoria.getPuntajeMinimoEvaluacion().toString().isEmpty()) {
				val = false;
				mensajeError("Por registre el puntaje mínimo.");
			} else {
				if (this.convocatoria.getPuntajeMinimoEvaluacion() < 10 || this.convocatoria.getPuntajeMinimoEvaluacion() > 100) {
					val = false;
					mensajeError("Puntaje mínimo incorrecto.");
				}
			}

			if (this.convocatoria.getDescripcionProcesoEvaluacion().isEmpty()) {
				val = false;
				mensajeError("Por registre la descripción del proceso de evaluación.");
			}
		}else{
			if(this.convocatoria.getTipoEvaluacion().equals("S")){
				if (this.convocatoria.getResponsableProcesoSeleccion().isEmpty()) {
					val = false;
					mensajeError("Por registre el responsable del proceso de selección.");
				}

				if (this.convocatoria.getProcesoSelecicon().isEmpty()) {
					val = false;
					mensajeError("Por registre la descripción del proceso de selección.");
				}
			}else{
				if (this.convocatoria.getDependeciaVerificacionRequisito().isEmpty()) {
					val = false;
					mensajeError("Por registre el responsable del proceso de verificación de requisitos mínimos.");
				}

				if (this.convocatoria.getResponsableProcesoEvaluacion().isEmpty()) {
					val = false;
					mensajeError("Por registre el responsable del proceso de evaluación.");
				}

				if (this.convocatoria.getPuntajeMinimoEvaluacion().toString().isEmpty()) {
					val = false;
					mensajeError("Por favor registre el puntaje mínimo.");
				} else {
					if (this.convocatoria.getPuntajeMinimoEvaluacion() < 10 || this.convocatoria.getPuntajeMinimoEvaluacion() > 100) {
						val = false;
						mensajeError("Puntaje mínimo incorrecto.");
					}
				}

				if (this.convocatoria.getDescripcionProcesoEvaluacion().isEmpty()) {
					val = false;
					mensajeError("Por favor registre la descripción del proceso de evaluación.");
				}
				
				if (this.convocatoria.getResponsableProcesoSeleccion().isEmpty()) {
					val = false;
					mensajeError("Por favor registre el responsable del proceso de selección.");
				}

				if (this.convocatoria.getProcesoSelecicon().isEmpty()) {
					val = false;
					mensajeError("Por favor registre la descripción del proceso de selección.");
				}
			}
		}
		
		if (this.convocatoria.getCriteriosDesempate().isEmpty()) {
			val = false;
			mensajeError("Por favor registre la información relacionada con el proceso de desempate.");
		}
		
		if (this.convocatoria.getTipoReclamacion().isEmpty()) {
			val = false;
			mensajeError("Por favor registre el proceso de reclamaciones o aclaración.");
		}else{
			if(this.convocatoria.getTipoReclamacion().equals("R")){
				if (this.convocatoria.getDependenciaResponsableResponderReclamacionReq().isEmpty()) {
					val = false;
					mensajeError("Por favor registre la información relacionada con la dependencia responsable del proceso de reclamación de requisitos.");
				}
				
				if (this.convocatoria.getDiasParaSolicitarReclamacionesReq().toString().isEmpty()) {
					val = false;
					mensajeError("Por registre los días para solicitar reclamaciones de requisitos.");
				} else {
					if (this.convocatoria.getDiasParaSolicitarReclamacionesReq() < 1 || this.convocatoria.getDiasParaSolicitarReclamacionesReq() > 30) {
						val = false;
						mensajeError("Días para solicitar reclamaciones de requisitos incorrecto.");
					}
				}
				
				if (this.convocatoria.getDiasParaResponderReclamacionesReq().toString().isEmpty()) {
					val = false;
					mensajeError("Por registre número de días para responder reclamaciones de requisitos.");
				} else {
					if (this.convocatoria.getDiasParaResponderReclamacionesReq() < 1 || this.convocatoria.getDiasParaResponderReclamacionesReq() > 30) {
						val = false;
						mensajeError("Días para responder reclamaciones de requisitos incorrecto.");
					}
				}
			}else{
				
				
				if(this.convocatoria.getTipoReclamacion().equals("E")){
					
					if (this.convocatoria.getDependenciaResponsableResponderReclamacionEva().isEmpty()) {
						val = false;
						mensajeError("Por favor registre la información relacionada con la dependencia responsable del proceso de reclamación de evaluación.");
					}
					
					if (this.convocatoria.getDiasParaSolicitarReclamacionesEva().toString().isEmpty()) {
						val = false;
						mensajeError("Por registre el número de días para solicitar reclamaciones del proceso de evaluación.");
					} else {
						if (this.convocatoria.getDiasParaSolicitarReclamacionesEva() < 1 || this.convocatoria.getDiasParaSolicitarReclamacionesEva() > 30) {
							val = false;
							mensajeError("Días para solicitar reclamaciones de evaluación o selección incorrecto.");
						}
					}
					
					if (this.convocatoria.getDiasParaResponderReclamacionesEva().toString().isEmpty()) {
						val = false;
						mensajeError("Por registre el número de días para responder reclamaciones del proceso de evaluación.");
					} else {
						if (this.convocatoria.getDiasParaResponderReclamacionesEva() < 1 || this.convocatoria.getDiasParaResponderReclamacionesEva() > 30) {
							val = false;
							mensajeError("Días para responder reclamaciones de evaluación o selección incorrecto.");
						}
					}
					
				}else{
					if (this.convocatoria.getDependenciaResponsableResponderReclamacionReq().isEmpty()) {
						val = false;
						mensajeError("Por favor registre la información relacionada con la dependencia responsable del proceso de reclamación de requisitos.");
					}
					
					if (this.convocatoria.getDependenciaResponsableResponderReclamacionEva().isEmpty()) {
						val = false;
						mensajeError("Por favor registre la información relacionada con la dependencia responsable del proceso de reclamación de evaluación.");
					}
					
					if (this.convocatoria.getDiasParaSolicitarReclamacionesReq().toString().isEmpty()) {
						val = false;
						mensajeError("Por registre los días para solicitar reclamaciones de requisitos.");
					} else {
						if (this.convocatoria.getDiasParaSolicitarReclamacionesReq() < 1 || this.convocatoria.getDiasParaSolicitarReclamacionesReq() > 30) {
							val = false;
							mensajeError("Días para solicitar reclamaciones de requisitos incorrecto.");
						}
					}
					
					if (this.convocatoria.getDiasParaResponderReclamacionesReq().toString().isEmpty()) {
						val = false;
						mensajeError("Por registre número de días para responder reclamaciones de requisitos.");
					} else {
						if (this.convocatoria.getDiasParaResponderReclamacionesReq() < 1 || this.convocatoria.getDiasParaResponderReclamacionesReq() > 30) {
							val = false;
							mensajeError("Días para responder reclamaciones de requisitos incorrecto.");
						}
					}
					
					if (this.convocatoria.getDiasParaSolicitarReclamacionesEva().toString().isEmpty()) {
						val = false;
						mensajeError("Por registre el número de días para solicitar reclamaciones del proceso de evaluación.");
					} else {
						if (this.convocatoria.getDiasParaSolicitarReclamacionesEva() < 1 || this.convocatoria.getDiasParaSolicitarReclamacionesEva() > 30) {
							val = false;
							mensajeError("Días para solicitar reclamaciones de evaluación o selección incorrecto.");
						}
					}
					
					if (this.convocatoria.getDiasParaResponderReclamacionesEva().toString().isEmpty()) {
						val = false;
						mensajeError("Por registre el número de días para responder reclamaciones del proceso de evaluación.");
					} else {
						if (this.convocatoria.getDiasParaResponderReclamacionesEva() < 1 || this.convocatoria.getDiasParaResponderReclamacionesEva() > 30) {
							val = false;
							mensajeError("Días para responder reclamaciones de evaluación o selección incorrecto.");
						}
					}
					
				}
			}
		}
		
		if (this.convocatoria.getTextoPublicacionGanadores().isEmpty()) {
			val = false;
			mensajeError("Por favor registre el texto relacionado con publicación de ganadores.");
		}
		
		return val;
	}

	public String guardarCompromisos() {
		if (validarCompromisos()) {
			servicioGeneral.guardarObjeto(this.convocatoria);
			return "productosEsperadosConvocatoriaTR";
		} else {
			return "";
		}

	}
	
	public String guardarCompromisosSalir() {
		if (validarCompromisos()) {
			servicioGeneral.guardarObjeto(this.convocatoria);
			sesion.removeAttribute("manejadorTerminosReferenciaConvocatoria");
			return "proyectosInvestigador";
		} else {
			return "";
		}

	}

	private boolean validarCompromisos() {
		if (!this.convocatoria.getListaCompromisoConvocatoriaTR().isEmpty()) {
			return true;
		} else {
			mensajeError("Por registre los compromisos de la convocatoria");
			return false;
		}
	}

	public String guardarProductos() {
		if (validarProductos()) {
			servicioGeneral.guardarObjeto(this.convocatoria);
			return "incompatibilidadesConvocatoriaTR";
		} else {
			return "";
		}

	}
	
	public String guardarProductosSalir() {
		if (validarProductos()) {
			servicioGeneral.guardarObjeto(this.convocatoria);
			sesion.removeAttribute("manejadorTerminosReferenciaConvocatoria");
			return "proyectosInvestigador";
		} else {
			return "";
		}

	}

	private boolean validarProductos() {
		boolean val = true;
		if(convocatoria.getTextoProductosAcademicos().isEmpty()){
			val = false;
			mensajeError("Por registre el texto de productos académicos (texto a mostrar en los términos de referencia)");
		}
		return val;
	}

	public String guardarIncompatibilidades() {
		if (validarIncompatibilidades()) {
			servicioGeneral.guardarObjeto(this.convocatoria);
			return "calendarioConvocatoriaTR";
		} else {
			return "";
		}

	}
	
	public String guardarIncompatibilidadesSalir() {
		if (validarIncompatibilidades()) {
			servicioGeneral.guardarObjeto(this.convocatoria);
			sesion.removeAttribute("manejadorTerminosReferenciaConvocatoria");
			return "proyectosInvestigador";
		} else {
			return "";
		}

	}

	private boolean validarIncompatibilidades() {
		if (!this.convocatoria.getListaIncompatibilidadConvocatoriaTR().isEmpty()) {
			return true;
		} else {
			mensajeError("Por registre las incompatibilidades de la convocatoria");
			return false;
		}
	}

	public String guardarCalendario() {
		if (validarCalendario()) {
			servicioGeneral.guardarObjeto(this.convocatoria);
			return "consideracionesConvocatoriaTR";
		} else {
			return "";
		}

	}
	
	public String guardarCalendarioSalir() {
		if (validarCalendario()) {
			servicioGeneral.guardarObjeto(this.convocatoria);
			sesion.removeAttribute("manejadorTerminosReferenciaConvocatoria");
			return "proyectosInvestigador";
		} else {
			return "";
		}

	}

	private boolean validarCalendario() {
		// TODO Auto-generated method stub
		return true;
	}

	public String guardarConsideraciones() {
		if (validarConsideraciones()) {
			servicioGeneral.guardarObjeto(this.convocatoria);
			mensajeInfo("Información guardada con éxito.");
			return "";
		} else {
			return "";
		}

	}
	
	public String guardarConsideracionesSalir() {
		if (validarConsideraciones()) {
			servicioGeneral.guardarObjeto(this.convocatoria);
			sesion.removeAttribute("manejadorTerminosReferenciaConvocatoria");
			return "proyectosInvestigador";
		} else {
			return "";
		}

	}


	private boolean validarConsideraciones() {
		boolean val = true;
		if (this.convocatoria.getPropiedadIntelectual().isEmpty()) {
			val = false;
			mensajeError("Por favor registre la información relacionada con la propiedad intelectual.");
		}
		
		if (this.convocatoria.getConsideracionesEticas().isEmpty()) {
			val = false;
			mensajeError("Por favor registre la información relacionada con las consideraciones éticas.");
		}
		
		
		if (this.convocatoria.getDependenciaResponsableSeguimientoTecnico().isEmpty()) {
			val = false;
			mensajeError("Por favor registre la información relacionada con la dependencia responsable del seguimiento técnico.");
		}
		
		if (this.convocatoria.getTextoConsideraciones().isEmpty()) {
			val = false;
			mensajeError("Por favor registre la información relacionada con las consideraciones.");
		}
		return val;
	}

	public SelectItem[] getTipoConvocatoriaItem() {
		return tipoConvocatoriaItem;
	}

	public void setTipoConvocatoriaItem(SelectItem[] tipoConvocatoriaItem) {
		this.tipoConvocatoriaItem = tipoConvocatoriaItem;
	}

	public ConvocatoriaTerminosReferencia getConvocatoria() {
		return convocatoria;
	}

	public void setConvocatoria(ConvocatoriaTerminosReferencia convocatoria) {
		this.convocatoria = convocatoria;
	}

	public Long getIdConvTerRef() {
		return idConvTerRef;
	}

	public void setIdConvTerRef(Long idConvTerRef) {
		this.idConvTerRef = idConvTerRef;
	}

	public Dependencia getDependenciaActual() {
		return dependenciaActual;
	}

	public void setDependenciaActual(Dependencia dependenciaActual) {
		this.dependenciaActual = dependenciaActual;
	}

	public Persona getPersonaActual() {
		return personaActual;
	}

	public void setPersonaActual(Persona personaActual) {
		this.personaActual = personaActual;
	}

	public String getSedeSel() {
		return sedeSel;
	}

	public void setSedeSel(String sedeSel) {
		this.sedeSel = sedeSel;
	}

	public SelectItem[] getSedeItem() {
		return sedeItem;
	}

	public void setSedeItem(SelectItem[] sedeItem) {
		this.sedeItem = sedeItem;
	}

	public boolean isMostrarFacultades() {
		return mostrarFacultades;
	}

	public void setMostrarFacultades(boolean mostrarFacultades) {
		this.mostrarFacultades = mostrarFacultades;
	}

	public List<Dependencia> getDependenciasUN() {
		return dependenciasUN;
	}

	public void setDependenciasUN(List<Dependencia> dependenciasUN) {
		this.dependenciasUN = dependenciasUN;
	}

	public List<SelectItem> getDependenciaItem() {
		return dependenciaItem;
	}

	public void setDependenciaItem(List<SelectItem> dependenciaItem) {
		this.dependenciaItem = dependenciaItem;
	}

	public String getDependenciaConvocatoria() {
		return dependenciaConvocatoria;
	}

	public void setDependenciaConvocatoria(String dependenciaConvocatoria) {
		this.dependenciaConvocatoria = dependenciaConvocatoria;
	}

	public List<SelectItem> getFacultadItem() {
		return facultadItem;
	}

	public void setFacultadItem(List<SelectItem> facultadItem) {
		this.facultadItem = facultadItem;
	}

	public String getFacultadSel() {
		return facultadSel;
	}

	public void setFacultadSel(String facultadSel) {
		this.facultadSel = facultadSel;
	}

	public DependenciaConvocatoriaTR getDependenciaTRSeleccionada() {
		return dependenciaTRSeleccionada;
	}

	public void setDependenciaTRSeleccionada(DependenciaConvocatoriaTR dependenciaTRSeleccionada) {
		this.dependenciaTRSeleccionada = dependenciaTRSeleccionada;
	}

	public ModalidadConvocatoriaTR getModalidad() {
		return modalidad;
	}

	public void setModalidad(ModalidadConvocatoriaTR modalidad) {
		this.modalidad = modalidad;
	}

	public ModalidadConvocatoriaTR getModalidadTRSeleccionada() {
		return modalidadTRSeleccionada;
	}

	public void setModalidadTRSeleccionada(ModalidadConvocatoriaTR modalidadTRSeleccionada) {
		this.modalidadTRSeleccionada = modalidadTRSeleccionada;
	}

	public RequisitoConvocatoriaTR getRequisito() {
		return requisito;
	}

	public void setRequisito(RequisitoConvocatoriaTR requisito) {
		this.requisito = requisito;
	}

	public RequisitoConvocatoriaTR getRequisitoTRSeleccionada() {
		return requisitoTRSeleccionada;
	}

	public void setRequisitoTRSeleccionada(RequisitoConvocatoriaTR requisitoTRSeleccionada) {
		this.requisitoTRSeleccionada = requisitoTRSeleccionada;
	}

	public DocumentoConvocatoriaTR getDocumento() {
		return documento;
	}

	public void setDocumento(DocumentoConvocatoriaTR documento) {
		this.documento = documento;
	}

	public DocumentoConvocatoriaTR getDocumentoTRSeleccionada() {
		return documentoTRSeleccionada;
	}

	public void setDocumentoTRSeleccionada(DocumentoConvocatoriaTR documentoTRSeleccionada) {
		this.documentoTRSeleccionada = documentoTRSeleccionada;
	}

	public CompromisoConvocatoriaTR getCompromiso() {
		return compromiso;
	}

	public void setCompromiso(CompromisoConvocatoriaTR compromiso) {
		this.compromiso = compromiso;
	}

	public CompromisoConvocatoriaTR getCompromisoTRSeleccionada() {
		return compromisoTRSeleccionada;
	}

	public void setCompromisoTRSeleccionada(CompromisoConvocatoriaTR compromisoTRSeleccionada) {
		this.compromisoTRSeleccionada = compromisoTRSeleccionada;
	}

	public IncompatibilidadConvocatoriaTR getIncompatibilidad() {
		return incompatibilidad;
	}

	public void setIncompatibilidad(IncompatibilidadConvocatoriaTR incompatibilidad) {
		this.incompatibilidad = incompatibilidad;
	}

	public IncompatibilidadConvocatoriaTR getIncompatibilidadTRSeleccionada() {
		return incompatibilidadTRSeleccionada;
	}

	public void setIncompatibilidadTRSeleccionada(IncompatibilidadConvocatoriaTR incompatibilidadTRSeleccionada) {
		this.incompatibilidadTRSeleccionada = incompatibilidadTRSeleccionada;
	}

	public RubrosFinanciablesConvocatoriaTR getRubro() {
		return rubro;
	}

	public void setRubro(RubrosFinanciablesConvocatoriaTR rubro) {
		this.rubro = rubro;
	}

	public RubrosFinanciablesConvocatoriaTR getRubroTRSeleccionado() {
		return rubroTRSeleccionado;
	}

	public void setRubroTRSeleccionado(RubrosFinanciablesConvocatoriaTR rubroTRSeleccionado) {
		this.rubroTRSeleccionado = rubroTRSeleccionado;
	}

	public List<SelectItem> getTipoRubroItems() {
		return tipoRubroItems;
	}

	public void setTipoRubroItems(List<SelectItem> tipoRubroItems) {
		this.tipoRubroItems = tipoRubroItems;
	}

	public CriteriosEvaluacionConvocatoriaTR getCriterioEvaluacion() {
		return criterioEvaluacion;
	}

	public void setCriterioEvaluacion(CriteriosEvaluacionConvocatoriaTR criterioEvaluacion) {
		this.criterioEvaluacion = criterioEvaluacion;
	}

	public CriteriosEvaluacionConvocatoriaTR getCriterioEvaluacionSeleccionado() {
		return criterioEvaluacionSeleccionado;
	}

	public void setCriterioEvaluacionSeleccionado(CriteriosEvaluacionConvocatoriaTR criterioEvaluacionSeleccionado) {
		this.criterioEvaluacionSeleccionado = criterioEvaluacionSeleccionado;
	}

	public CriteriosEvaluacionConvocatoriaTR getCriterioSeleccion() {
		return criterioSeleccion;
	}

	public void setCriterioSeleccion(CriteriosEvaluacionConvocatoriaTR criterioSeleccion) {
		this.criterioSeleccion = criterioSeleccion;
	}

	public CriteriosEvaluacionConvocatoriaTR getCriterioSeleccionSeleccionado() {
		return criterioSeleccionSeleccionado;
	}

	public void setCriterioSeleccionSeleccionado(CriteriosEvaluacionConvocatoriaTR criterioSeleccionSeleccionado) {
		this.criterioSeleccionSeleccionado = criterioSeleccionSeleccionado;
	}

	public ProductosConvocatoriaTR getProducto() {
		return producto;
	}

	public void setProducto(ProductosConvocatoriaTR producto) {
		this.producto = producto;
	}

	public ProductosConvocatoriaTR getProductoTRSeleccionado() {
		return productoTRSeleccionado;
	}

	public void setProductoTRSeleccionado(ProductosConvocatoriaTR productoTRSeleccionado) {
		this.productoTRSeleccionado = productoTRSeleccionado;
	}

	public List<SelectItem> getTipoProductoItems() {
		return tipoProductoItems;
	}

	public void setTipoProductoItems(List<SelectItem> tipoProductoItems) {
		this.tipoProductoItems = tipoProductoItems;
	}

	public String getNombreCampoAdicional() {
		return nombreCampoAdicional;
	}

	public void setNombreCampoAdicional(String nombreCampoAdicional) {
		this.nombreCampoAdicional = nombreCampoAdicional;
	}

	public String getDescripcionCampoAdicional() {
		return descripcionCampoAdicional;
	}

	public void setDescripcionCampoAdicional(String descripcionCampoAdicional) {
		this.descripcionCampoAdicional = descripcionCampoAdicional;
	}

	public CamposAdicionalesConvocatoriaTR getCampo() {
		return campo;
	}

	public void setCampo(CamposAdicionalesConvocatoriaTR campo) {
		this.campo = campo;
	}

	public CamposAdicionalesConvocatoriaTR getCampoTRSeleccionado() {
		return campoTRSeleccionado;
	}

	public void setCampoTRSeleccionado(CamposAdicionalesConvocatoriaTR campoTRSeleccionado) {
		this.campoTRSeleccionado = campoTRSeleccionado;
	}

	public ObjetivosEspecificosConvocatoriaTR getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(ObjetivosEspecificosConvocatoriaTR objetivo) {
		this.objetivo = objetivo;
	}

	public ObjetivosEspecificosConvocatoriaTR getObjetivoTRSeleccionado() {
		return objetivoTRSeleccionado;
	}

	public void setObjetivoTRSeleccionado(ObjetivosEspecificosConvocatoriaTR objetivoTRSeleccionado) {
		this.objetivoTRSeleccionado = objetivoTRSeleccionado;
	}

}
