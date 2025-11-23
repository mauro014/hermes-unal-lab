package co.edu.unal.hermes.vista.propiedadintelectual;

import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.ArchivoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.CotitularPropiedadIntelectual;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.HistoricoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.Pais;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PersonaPropiedadIntelectual;
import co.edu.unal.hermes.modelo.PropiedadIntelectual;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.SubTipoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorBasePropiedadIntelectual extends ManejadorBase {

	/**
	 * 
	 */
	public static final String TIPO_NUEVO = "N";
	protected static final String TIPO_EDICION = "E";
	protected static final String TIPO_CONSULTA = "C";

	// Dominios propiedad intelectual
	protected static final String TIPO_DOMINIO_CARACTER = "CARACTER_OBRA_PROP_INT";
	protected static final String TIPO_AMBITO_OBRA = "AMBITO_OBRA_LITERARIA_PROP_INT";
	protected static final String TIPO_EDICION_OBRA = "TIPO_EDICION_OBRA_PROP_INT";
	protected static final String TIPO_CLASE_OBRA = "TIPO_CLASE_OBRA_PROP_INT";
	protected static final String TIPO_FORMATO_OBRA = "TIPO_FORMATO_OBRA_PROP_INT";
	protected static final String TIPO_RESULTADO_PI = "TIPO_RESULTADO_PROP_INT";

	// Variables de sesion en propiedad intelectual
	protected static final String TIPO_ACCESO = "tipoAcceso"; // nuevo, edicion,
																// consulta
	protected static final String ID_PROPIEDAD_INTELECTUAL = "idPropiedad"; // propiedad
																			// en
																			// sesion
	protected static final String ROL_INGRESO_PI = "rolIngresoPropiedadIntelectual"; // nivel
																						// sede
																						// o
																						// nacional

	// Variable temporal agregar una nueva persona
	protected Persona personaExterna;

	// Registros isbn
	protected static final Long MODALIDAD_ISBN = 452L;

	private static final long serialVersionUID = 1L;
	protected List<PropiedadIntelectual> listaPropiedades;
	protected List<PropiedadIntelectual> filteredPropiedades;
	protected PropiedadIntelectual propiedad;
	protected Long idPropiedad;
	protected ArchivoPropiedadIntelectual archivoSeleccionado;
	protected SelectItem[] categoriasPropiedadIntelectualItem;
	protected SelectItem[] tiposNaturalezaProyecto;
	protected SelectItem[] paisesItem;
	protected SelectItem[] ciudadItem;
	protected SelectItem[] generosMusicalesItem;
	protected SelectItem[] generosCineItem;
	protected Long idProyecto; // temporal para identificar proyecto
								// seleccionado
	protected String entidadExterna; // temporal para identificar entidad
										// seleccionada
	protected CotitularPropiedadIntelectual entidadSeleccionada;
	protected float porcentaje; // temporal
	protected String tipoDocumento; // temporal
	protected String documento; // temporal
	private boolean esGestorPropiedadIntelectual = false;

	public ManejadorBasePropiedadIntelectual() {
		personaExterna = new Persona();
		esGestorPropiedadIntelectual = (Boolean) sesion.getAttribute("esPropiedadIntelectual");
	}

	public boolean isEsEdicion() {
		if (sesion.getAttribute(TIPO_ACCESO) != null && sesion.getAttribute(TIPO_ACCESO) == TIPO_EDICION) {
			return true;
		}
		return false;
	}

	public boolean isEsNueva() {
		if (sesion.getAttribute(TIPO_ACCESO) != null && sesion.getAttribute(TIPO_ACCESO) == TIPO_NUEVO) {
			return true;
		}
		return false;
	}

	public boolean isEsConsulta() {
		if (sesion.getAttribute(TIPO_ACCESO) != null && sesion.getAttribute(TIPO_ACCESO) == TIPO_CONSULTA) {
			return true;
		}
		return false;
	}

	public void eliminarSesionPI() {
		sesion.removeAttribute("manejadorPropiedadIntelectual");
		sesion.removeAttribute("manejadorPropiedadIntelectualListas");
		sesion.removeAttribute("manejadorRevisionPropiedadIntelectual");
		sesion.removeAttribute("manejadorConsultarPropiedadIntelectual");
		sesion.removeAttribute(TIPO_ACCESO);
		sesion.removeAttribute(ID_PROPIEDAD_INTELECTUAL);
	}

	/*
	 * Editar por parte de la persona que registra la propiedad intelectual.
	 */
	public String editarPropiedad() {
		eliminarSesionPI();
		sesion.setAttribute(TIPO_ACCESO, ManejadorBasePropiedadIntelectual.TIPO_EDICION);
		sesion.setAttribute(ID_PROPIEDAD_INTELECTUAL, idPropiedad);
		return "registroPropiedadIntelectual";
	}

	/*
	 * Consultar por parte de la persona que registra la propiedad intelectual.
	 */
	public String consultarPropiedad() {
		eliminarSesionPI();
		sesion.setAttribute(TIPO_ACCESO, ManejadorBasePropiedadIntelectual.TIPO_CONSULTA);
		sesion.setAttribute(ID_PROPIEDAD_INTELECTUAL, idPropiedad);
		return "registroPropiedadIntelectual";
	}

	public void descargarArchivo() {
		if (archivoSeleccionado != null) {
			descargarArchivoPropiedadIntelectual(archivoSeleccionado);
		}
	}

	public void eliminarArchivo() {
		if (archivoSeleccionado != null) {
			propiedad.borrarArchivo(archivoSeleccionado);
			archivoSeleccionado.setFechaBorrado(new Date());
			archivoSeleccionado.setPersonaElimina(cargarPersonaActual());
			archivoSeleccionado.setEstado("B");
			if (propiedad.getId() != null) {
				archivoSeleccionado.setPropiedadBorrado(propiedad.getId());
			}
			servicioGeneral.guardarObjeto(archivoSeleccionado);
		}
	}

	/**
	 * Lista los subtipos de propiedad intelectual por tipo
	 */
	public void subTiposPropiedadIntelectual() {
		categoriasPropiedadIntelectualItem = new SelectItem[0];
		if (propiedad.getSubTipo() != null && propiedad.getSubTipo().getTipo().getId() != null) {
			List<SubTipoPropiedadIntelectual> lista = servicioPropiedadIntelectual
					.obtenerSubTiposPropiedadIntelectual(propiedad.getSubTipo().getTipo().getId(), "A", esGestorPropiedadIntelectual);
			if (!esListaVacia(lista)) {
				categoriasPropiedadIntelectualItem = new SelectItem[lista.size()];
				for (int i = 0; i < lista.size(); i++) {
					SubTipoPropiedadIntelectual subtipo = lista.get(i);
					categoriasPropiedadIntelectualItem[i] = new SelectItem(subtipo.getId(), subtipo.getNombre());
				}
			}
		}
	}

	/**
	 * Lista los subtipos de propiedad intelectual
	 */
	public SelectItem[] getSubTiposPropiedadIntelectualTodos() {
		categoriasPropiedadIntelectualItem = new SelectItem[0];
		List<SubTipoPropiedadIntelectual> lista = servicioPropiedadIntelectual.obtenerSubTiposPropiedadIntelectual();
		if (!esListaVacia(lista)) {
			categoriasPropiedadIntelectualItem = new SelectItem[lista.size()];
			for (int i = 0; i < lista.size(); i++) {
				SubTipoPropiedadIntelectual subtipo = lista.get(i);
				categoriasPropiedadIntelectualItem[i] = new SelectItem(subtipo.getId(), subtipo.getNombre());
			}
		}
		return categoriasPropiedadIntelectualItem;
	}

	public void imprimirPropiedad() {
		generarPDFPropiedad(propiedad.getId().toString());
	}

	public void generarPDFPropiedad(String id) {
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("id", id);
		r.setNombreReporte("/propiedad-intelectual/propiedadIntelectual");
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
	}

	public void imprimirFichaTecnica() {
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("id", propiedad.getId().toString());
		r.setNombreReporte("/propiedad-intelectual/ficha-prop-industrial-gestor");
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
	}

	public void crearHistoricoEstadoPropiedadIntelectual(PropiedadIntelectual propiedad, Persona persona,
			String observacion) {
		HistoricoPropiedadIntelectual historicoPropiedad = new HistoricoPropiedadIntelectual();
		Investigador investigador = servicioPersona.obtenerInvestigador(persona.getId());
		Tipos sectorTecnologico = propiedad.getSectorTecnologico();
		historicoPropiedad.setFecha(new Date());
		historicoPropiedad.setPropiedad(propiedad);
		historicoPropiedad.setEstado(propiedad.getSubEstado());
		historicoPropiedad.setResponsable(investigador);
		historicoPropiedad.setObservacion(observacion);
		historicoPropiedad.setSectorTecnologico(sectorTecnologico);
		if (sesion.getAttribute(ROL_INGRESO_PI) != null) {
			historicoPropiedad.setDependencia(investigador.getDependencia());
		} else {
			historicoPropiedad.setDependencia(propiedad.getDependenciaSolicitante());
		}
		this.servicioGeneral.guardarObjeto(historicoPropiedad);
	}

	public void cargarListaNaturalezaProyecto() {
		tiposNaturalezaProyecto = new SelectItem[2];
		tiposNaturalezaProyecto[0] = new SelectItem(PropiedadIntelectual.TIPO_PROY_INV_LAB,
				"Proyecto de investigación o laboratorios");
		tiposNaturalezaProyecto[1] = new SelectItem(PropiedadIntelectual.TIPO_PROY_EXT, "Proyecto de extensión");
	}

	public SelectItem[] getTiposVinculacionItem() {
		SelectItem[] tiposVinculacionItem = new SelectItem[6];
		tiposVinculacionItem[0] = new SelectItem("", "");
		tiposVinculacionItem[1] = new SelectItem(PersonaPropiedadIntelectual.ADMINISTRATIVO, "Administrativo(a)");
		tiposVinculacionItem[2] = new SelectItem(PersonaPropiedadIntelectual.DOCENTE, "Docente");
		tiposVinculacionItem[3] = new SelectItem(PersonaPropiedadIntelectual.ESTUDIANTE, "Estudiante");
		tiposVinculacionItem[4] = new SelectItem(PersonaPropiedadIntelectual.EXTERNO, "Externo");
		tiposVinculacionItem[5] = new SelectItem(PersonaPropiedadIntelectual.EGRESADO, "Egresado");
		return tiposVinculacionItem;
	}

	public void cargarPaises() {
//		List<Pais> lista = servicioGeneral.obtenerListaPaisesISO();
//		if (!esListaVacia(lista)) {
//			paisesItem = new SelectItem[lista.size()];
//			for (int i = 0; i < lista.size(); i++) {
//				Pais pais = lista.get(i);
//				paisesItem[i] = new SelectItem(pais.getId(), pais.getNombre());
//			}
//		}		
		paisesItem = servicioGeneral.obtenerListaPaisesISOSelectItem();
	}
	
	public void cargarCiudades() {
		List<Ciudad> lista = servicioGeneral.obtenerListaObjetos(Ciudad.class);
		if (!esListaVacia(lista)) {
			ciudadItem = new SelectItem[lista.size()];
			for (int i = 0; i < lista.size(); i++) {
				Ciudad ciudad = lista.get(i);
				ciudadItem[i] = new SelectItem(ciudad.getId(), ciudad.getNombre());
			}
		}
	}

	public SelectItem[] getEntidadesItem() {
		SelectItem[] entidadesItem = new SelectItem[0];
		List<FuenteFinanciacion> listaEntidades = cargarEntidadesExternas(CONSULTA_ENTIDADES_EXTERNAS_CON_UNAL);

		if (!esListaVacia(listaEntidades)) {
			entidadesItem = new SelectItem[listaEntidades.size()];
			for (int i = 0; i < listaEntidades.size(); i++) {
				FuenteFinanciacion entidad = (FuenteFinanciacion) listaEntidades.get(i);
				entidadesItem[i] = new SelectItem(entidad.getId(), entidad.getDescripcion());
			}
		}
		return entidadesItem;
	}

	/**
	 * Lista generos musicales
	 */
	public void cargarGenerosMusicales() {
		generosMusicalesItem = servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccione(Tipos.GENEROS_MUSICALES);
	}

	/**
	 * Lista generos cinematograficos
	 */
	public void cargarGenerosCinematograficos() {
		generosCineItem = servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccione(Tipos.GENEROS_CINEMATOGRAFICOS);
	}

	/**
	 * Lista de clasificación de obraAudivisuales
	 */
	public SelectItem[] getClasificacionObraAudiovisualItem() {
		return servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccione(Tipos.CLASIFICACION_OBRAS_AUDIOVISUALES);
	}

	/**
	 * Lista de estados de desarrollo
	 */
	public SelectItem[] getListaEstadoDesarrolloItem() {
		return servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccione(Tipos.ESTADO_DLLO_PROP_INDUSTRIAL_NUEVO);
	}

	/**
	 * Lista de sectores
	 */
	public SelectItem[] getListaSectorTecnologicoItem() {
		return servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccione(Tipos.SECTOR_TECNOLOGICO_INVENCION);
	}
	
	public SelectItem[] getTipoSolicitudPropiedadIndustrialItem() {
		return servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccione(Tipos.TIPO_SOLICITUD_PROP_INT);
	}

	public void imprimirIsbn() {
		imprimir(propiedad.getSolicitudIsbn());
	}

	public void imprimir(Long id) {
		Proyecto proyectoActual = servicioProyecto.obtenerProyecto(id, ProyectoDAOHibernate.INFORMACION_GENERAL);
		if (proyectoActual != null) {
			servicioProyecto.imprimirReporteProyecto(proyectoActual, sesion, false);
		}
	}

	public void imprimirProyecto() {
		imprimir(idProyecto);
	}

	/**
	 * Lista de tipos de documento se exceptúan
	 * 
	 * @return
	 */
	public SelectItem[] getListaTiposDocItem() {
		SelectItem[] listaTiposDocItem = new SelectItem[0];
//		List<TipoDocumento> listaTiposDoc = servicioGeneral.obtenerObjetos(TipoDocumento.class,
//				"select td from TipoDocumento td where td.id not in ('D') ORDER BY td.nombre");
		
		List<TipoDocumento> listaTiposDoc = servicioGeneral.obtenerTiposDeDocumento();

		if (!esListaVacia(listaTiposDoc)) {
			listaTiposDocItem = new SelectItem[listaTiposDoc.size()];
			for (int i = 0; i < listaTiposDoc.size(); i++) {
				TipoDocumento tipoDoc = (TipoDocumento) listaTiposDoc.get(i);
				listaTiposDocItem[i] = new SelectItem(tipoDoc.getId(), tipoDoc.getNombre());
			}
		}
		return listaTiposDocItem;
	}

	public void agregarCotitular() {

		if (!validarEntidad()) {
			return;
		}
		CotitularPropiedadIntelectual cotitular = new CotitularPropiedadIntelectual();
		cotitular.setEntidad(obtenerEntidadExterna(entidadExterna));
		cotitular.setPorcentaje(getPorcentaje());
		cotitular.setPropiedad(propiedad);
		propiedad.adicionarCotitularidad(cotitular);
	}

	private boolean validarEntidad() {
		boolean valida = true;
		if (esCadenaVacia(entidadExterna)) {
			mensajeError("Debe seleccionar una entidad para agregarla.");
			valida = false;
		}
		float totalPorcentaje = 0;
		for (int i = 0; i < propiedad.getListaCotitulares().size(); i++) {
			CotitularPropiedadIntelectual entidadAgregada = (CotitularPropiedadIntelectual) propiedad
					.getListaCotitulares().get(i);
			totalPorcentaje = totalPorcentaje + entidadAgregada.getPorcentaje();
			if (entidadAgregada.getEntidad().getId().equals(entidadExterna)) {
				mensajeError("El entidad ya ha sido agregada.");
				valida = false;
				break;
			}
		}

		if (totalPorcentaje + getPorcentaje() > 100) {
			mensajeError("El total del porcentaje de participación de cotitulares no puede superar el 100%.");
			valida = false;
		}
		return valida;
	}

	public FuenteFinanciacion obtenerEntidadExterna(String id) {
		List<FuenteFinanciacion> list = servicioGeneral.obtenerObjetoXID(FuenteFinanciacion.class, id);
		if (!esListaVacia(list)) {
			return list.get(0);
		} else {
			return new FuenteFinanciacion();
		}
	}

	/**
	 * Eliminar entidad
	 */
	public void eliminarEntidad() {
		if (entidadSeleccionada != null) {
			propiedad.borrarCotitularidad(entidadSeleccionada);
		}
	}

	public List<PropiedadIntelectual> getListaPropiedades() {
		return listaPropiedades;
	}

	public void setListaPropiedades(List<PropiedadIntelectual> listaPropiedades) {
		this.listaPropiedades = listaPropiedades;
	}

	public PropiedadIntelectual getPropiedad() {
		return propiedad;
	}

	public void setPropiedad(PropiedadIntelectual propiedad) {
		this.propiedad = propiedad;
	}

	public List<PropiedadIntelectual> getFilteredPropiedades() {
		return filteredPropiedades;
	}

	public void setFilteredPropiedades(List<PropiedadIntelectual> filteredPropiedades) {
		this.filteredPropiedades = filteredPropiedades;
	}

	public Long getIdPropiedad() {
		return idPropiedad;
	}

	public void setIdPropiedad(Long idPropiedad) {
		this.idPropiedad = idPropiedad;
	}

	public ArchivoPropiedadIntelectual getArchivoSeleccionado() {
		return archivoSeleccionado;
	}

	public void setArchivoSeleccionado(ArchivoPropiedadIntelectual archivoSeleccionado) {
		this.archivoSeleccionado = archivoSeleccionado;
	}

	public SelectItem[] getCategoriasPropiedadIntelectualItem() {
		return categoriasPropiedadIntelectualItem;
	}

	public void setCategoriasPropiedadIntelectualItem(SelectItem[] categoriasPropiedadIntelectualItem) {
		this.categoriasPropiedadIntelectualItem = categoriasPropiedadIntelectualItem;
	}

	public SelectItem[] getTiposNaturalezaProyecto() {
		return tiposNaturalezaProyecto;
	}

	public void setTiposNaturalezaProyecto(SelectItem[] tiposNaturalezaProyecto) {
		this.tiposNaturalezaProyecto = tiposNaturalezaProyecto;
	}

	public SelectItem[] getPaisesItem() {
		return paisesItem;
	}

	public void setPaisesItem(SelectItem[] paisesItem) {
		this.paisesItem = paisesItem;
	}

	public SelectItem[] getGenerosMusicalesItem() {
		return generosMusicalesItem;
	}

	public void setGenerosMusicalesItem(SelectItem[] generosMusicalesItem) {
		this.generosMusicalesItem = generosMusicalesItem;
	}

	public SelectItem[] getGenerosCineItem() {
		return generosCineItem;
	}

	public void setGenerosCineItem(SelectItem[] generosCineItem) {
		this.generosCineItem = generosCineItem;
	}

	public Long getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}

	public String getEntidadExterna() {
		return entidadExterna;
	}

	public void setEntidadExterna(String entidadExterna) {
		this.entidadExterna = entidadExterna;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public float getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(float porcentaje) {
		this.porcentaje = porcentaje;
	}

	public CotitularPropiedadIntelectual getEntidadSeleccionada() {
		return entidadSeleccionada;
	}

	public void setEntidadSeleccionada(CotitularPropiedadIntelectual entidadSeleccionada) {
		this.entidadSeleccionada = entidadSeleccionada;
	}

	public Persona getPersonaExterna() {
		return personaExterna;
	}

	public void setPersonaExterna(Persona personaExterna) {
		this.personaExterna = personaExterna;
	}

	public SelectItem[] getCiudadItem() {
		return ciudadItem;
	}

	public void setCiudadItem(SelectItem[] ciudadItem) {
		this.ciudadItem = ciudadItem;
	}

}