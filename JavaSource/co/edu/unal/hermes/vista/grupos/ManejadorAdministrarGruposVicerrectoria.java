/**
 * Creado por Mauricio
 * 29/10/2023: se amplia la funcionalidad a vicedecanaturas y direcciones de investigación.
 */

package co.edu.unal.hermes.vista.grupos;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.CategoriaGrupo;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.EstadoGrupo;
import co.edu.unal.hermes.modelo.EstadoGrupoColciencias;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.HistoricoCambioLiderGrupo;
import co.edu.unal.hermes.modelo.HistoricoEstadoGrupo;
import co.edu.unal.hermes.modelo.HistoricoFormularioGrupo;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorGrupo;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoInvestigador;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * The Class ManejadorAdministrarGruposVicerrectoria.
 */
public class ManejadorAdministrarGruposVicerrectoria extends ManejadorBase {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2970445251386453397L;

	/** The grupos. */
	private List<Grupo> grupos;

	/** The grupos vista. */
	private List<String[]> gruposVista;

	/** The estados unal. */
	private List<EstadoGrupo> estadosUnal;

	/** The categoria. */
	private List<CategoriaGrupo> categorias;

	/** The EstadoGrupo. */
	private List<EstadoGrupoColciencias> estadosGruposCocliencias;

	/** The EstadoGrupo. */
	private List<SelectItem> dependenciaItems;

	/** The historicos estado grupo. */
	private List<HistoricoEstadoGrupo> historicosEstadoGrupo;
	
	/** The historicos estado grupo. */
	private List<HistoricoFormularioGrupo> historicosFormularioGrupo;

	/** The codigo filtro. */
	private String codigoFiltro;

	/** The nombre filtro. */
	private String nombreFiltro;

	/** The estado unal filtro. */
	private String[] estadosUnalFiltro;

	/** The estado s cienti filtro. */
	private String[] estadosSCientiFiltro;

	/** The codigo col filtro. */
	private String codigoColFiltro;

	/** The categoria col filtro. */
	private String[] categoriasColFiltro;

	/** The categoria col filtro. */
	private String dependenciaFiltro;

	/** The grupo seleccionado. */
	private String[] grupoSeleccionado;
	private Grupo grupoEdicion;
	private Investigador nuevoInvestigador;
	private SelectItem[] tipoDocumentoItem;
	/** The tipo documento inv. */
	private String tipoDocumentoInv;
	private InvestigadorGrupo nuevoLiderGrupo;
	/** The documento. */
	private String documento;
	private boolean mostrarBotonCambioLider;
	
	private boolean error = false;
	private String idError="";
	protected UploadedFile archivoCargar;
	private boolean requiereArchivo;
	private String archivoAval;
	private HistoricoEstadoGrupo historicoSeleccionado;
	
	private List<CategoriaGrupo> categoriasActualizacion;
	private boolean vieneVicedecanatura;
	private boolean vieneDireccion;

	/**
	 * Instantiates a new manejador administrar grupos vicerrectoria.
	 */
	public ManejadorAdministrarGruposVicerrectoria() {
		nuevoInvestigador = new Investigador();
		vieneVicedecanatura = false;
		vieneDireccion = false;
		mostrarBotonCambioLider = true;
		requiereArchivo = false;
		historicoSeleccionado = new HistoricoEstadoGrupo();
		
		String referer = request.getHeader("Referer");
		
        if (referer != null) {
        	System.out.println("El encabezado Referer de la solicitud anterior es: " + referer);
        	if(referer.indexOf("avalarDireccion.xhtml") != -1){
        		vieneDireccion = true;
        	}else if(referer.indexOf("avalarFacultad.xhtml") != -1) {
        		vieneVicedecanatura = true;
        	}            
        } else {
        	System.out.println("sin encabezado anterior");
        }
		
		// Se cargan todos los grpos para la vista
		gruposVista = cargarDatosGrupos();

		// Cargar estados para la vista
		estadosUnal = cargarPosiblesEstadosGrupo();

		// Cargar las categorias
		categorias = cargarPosiblesCategoriasGrupo();
		categoriasActualizacion=new ArrayList<CategoriaGrupo>();
		for (CategoriaGrupo cg : categorias) {
			if(!cg.getId().equals("D")) {
				categoriasActualizacion.add(cg);
			}
		}

		// Cargar estados de colciencias para la vista
		estadosGruposCocliencias = cargarPosiblesEstadosColciencias();

		cambiosGruposHistorico = "";



	}

	/**
	 * Se comparan dos grupos apra saber si hay diferencias.
	 *
	 * @param grupoVista
	 *            the grupo vista
	 */
	private void buscarGrupoyGuardar(String[] grupoVista) {

		Iterator<Grupo> j = grupos.iterator();
		error=false;
		idError="";
		// Luego de obtener el objeto vista se itera por el listado de
		// grupos cargados
		// para verificar si hay cambios y solo guardar si los hay.
		Grupo grupo = null;

		while (j.hasNext()) {

			grupo = j.next();

			Long id = Long.parseLong(grupoVista[0]);
			
			if (grupoVista[4].equals("CT")
					&& (grupoVista[6].equals("S") || grupoVista[6].equals("R") || grupoVista[6].equals("G"))) {
				error = true;
				if (!idError.contains(grupoVista[0])) {
					idError += grupoVista[0] + ",";
				}
			} else {
				// Se comparan los grupos por ID
				if (grupo.getId().equals(id)) {
					compararGrupoyGuardar(grupo, grupoVista);
					break;
				} else {
					grupo = null;
				}
			}
		}

		// Si se encontro el grupo se elimina de la lista para la
		// siguiente iteración sea mas corta
		if (grupo != null) {
			grupos.remove(grupo);
		}
	}

	/**
	 * Cargar datos grupos.
	 *
	 * @return the list
	 */
	public List<String[]> cargarDatosGrupos() {

		Map<String, String> dependencias = new TreeMap<String, String>();
		// Se carga select basico
		String consulta = "select #id g.id ,#nombre g.nombre, #dependencia g.dependencia, "
				+ "#estadoGrupo g.estadoGrupo, #estadoGrupoColciencias g.estadoGrupoColciencias,"
				+ "#idColciencias g.idColciencias, #categoria g.categoria " + "from Grupo g where g.id <> 0 ";

		Investigador investigadorActual = servicioPersona
				.obtenerInvestigador(((Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL)).getId());
		
		if(vieneDireccion) {
			consulta += "and g.sede.id = '"+investigadorActual.getDependencia().getSede().getId()+"' ";
		}else if(vieneVicedecanatura) {
			consulta += "and g.dependencia.facultad.id = '"+investigadorActual.getDependencia().getFacultad().getId()+"' ";
		}

		// Se validan filtros adicionales
		if (StringUtils.isNotEmpty(codigoFiltro)) {
			consulta += "and g.id = '" + codigoFiltro + "' ";
		}

		if (StringUtils.isNotEmpty(nombreFiltro)) {
			consulta += "and upper(g.nombre) like '%" + nombreFiltro.toUpperCase() + "%' ";
		}

		if (!esArrayVacio(estadosUnalFiltro)) {
			consulta += "and g.estadoGrupo.id in (";
			consulta += crearListaConsulta(estadosUnalFiltro) + ") ";
		}

		if (!esArrayVacio(estadosSCientiFiltro)) {
			consulta += "and g.estadoGrupoColciencias.id in (";
			consulta += crearListaConsulta(estadosSCientiFiltro) + ") ";
		}

		if (StringUtils.isNotEmpty(codigoColFiltro)) {
			consulta += "and g.idColciencias like '%" + codigoColFiltro + "%' ";
		}

		if (!esArrayVacio(categoriasColFiltro)) {
			consulta += "and g.categoria in (";
			consulta += crearListaConsulta(categoriasColFiltro) + ") ";
		}

		if (StringUtils.isNotEmpty(dependenciaFiltro)) {
			consulta += "and (" + "g.dependencia.id = '" + dependenciaFiltro + "' or " + "g.dependencia.facultad.id = '"
					+ dependenciaFiltro + "') ";
		}

		// Se carga order final
		consulta += "order by to_number(g.id) asc";

		grupos = servicioGeneral.obtenerObjetosLimitado(Grupo.class, consulta);
		List<String[]> gruposVistaTemporal = new ArrayList<String[]>();
		if (!esListaVacia(grupos)) {
			Iterator<Grupo> i = grupos.iterator();
			while (i.hasNext()) {
				Grupo grupoCargado = i.next();
				String[] grupo = new String[10];
				grupo[0] = grupoCargado.getId().toString();
				grupo[1] = grupoCargado.getNombre();
				grupo[2] = grupoCargado.getDependencia().getNombre();
				grupo[3] = grupoCargado.getEstadoGrupo().getId();
				grupo[4] = grupoCargado.getEstadoGrupoColciencias().getId();
				if (grupoCargado.getIdColciencias() == null) {
					grupoCargado.setIdColciencias("");
				}
				grupo[5] = grupoCargado.getIdColciencias();
				grupo[6] = grupoCargado.getCategoria().getId();
				grupo[7] = "SI";
				grupo[8] = "";
				grupo[9] = "";
				gruposVistaTemporal.add(grupo);

				// Se agrega dependecia a mapa
				String key;
				String value;
				if (grupoCargado.getDependencia().getFacultad() != null) {
					value = grupoCargado.getDependencia().getFacultad().getId().toString();
					key = grupoCargado.getDependencia().getFacultad().getNombre();
				} else {
					value = grupoCargado.getDependencia().getId().toString();
					key = grupoCargado.getDependencia().getNombre();
				}
				dependencias.put(key, value);
			}
		}
		crearListadoDependencias(dependencias);
		return gruposVistaTemporal;
	}

	/**
	 * Función para cargar historico de cambio de estado de grupos.
	 */
	public void cargarHistorico() {
		historicosEstadoGrupo = servicioGeneral.obtenerObjetos(HistoricoEstadoGrupo.class,
				"from HistoricoEstadoGrupo h where h.grupo.id = '" + grupoSeleccionado[0] + "' order by h.fecha asc");

	}
	
	/**
	 * Función para cargar historico de edicion de formularios de grupos.
	 */
	public void cargarHistoricoFormulario() {
		historicosFormularioGrupo = servicioGeneral.obtenerObjetos(HistoricoFormularioGrupo.class,
				"from HistoricoFormularioGrupo h where h.grupo.id = '" + grupoSeleccionado[0] + "' order by h.fecha desc");

	}

	/**
	 * Cargar posibles categorias grupo.
	 *
	 * @return the list
	 */
	private List<CategoriaGrupo> cargarPosiblesCategoriasGrupo() {
		return servicioGeneral.obtenerObjetos(CategoriaGrupo.class, "from CategoriaGrupo eg where eg.id not in ('D')");
	}

	/**
	 * Cargar posibles categorias grupo.
	 *
	 * @return the list
	 */
	private List<EstadoGrupoColciencias> cargarPosiblesEstadosColciencias() {
		return servicioGeneral.obtenerObjetos(EstadoGrupoColciencias.class, "from EstadoGrupoColciencias eg ");
	}

	/**
	 * Cargar posibles estados grupo.
	 *
	 * @return the list
	 */
	private List<EstadoGrupo> cargarPosiblesEstadosGrupo() {
		return servicioGeneral.obtenerObjetos(EstadoGrupo.class,
				"from EstadoGrupo eg where eg.id in ('" + EstadoGrupo.ACTIVO + "','" + EstadoGrupo.INACTIVO + "','"
						 + EstadoGrupo.DISUELTO + "')");
	}

	/**
	 * Comparar grupoy guardar.
	 *
	 * @param grupo
	 *            the grupo
	 * @param grupoVista
	 *            the grupo vista
	 */
	private void compararGrupoyGuardar(Grupo grupo, String[] grupoVista) {
		cambiosGruposHistorico = "";
		boolean igual = true;
		// Se busca si hay cambios
		if (!grupo.getEstadoGrupo().getId().equals(grupoVista[3])) {
			igual = false;
			grupo.getEstadoGrupo().setId(grupoVista[3]);
			cambiosGruposHistorico += "eg,";
			if((grupo.getEstadoGrupo().getId().equals(EstadoGrupo.ACTIVO)||grupo.getEstadoGrupo().getId().equals(EstadoGrupo.INACTIVO)) && grupoVista[3].equals(EstadoGrupo.DISUELTO)) {
				requiereArchivo = true;
				if(esCadenaVacia(grupoVista[9])) {
					mensajeError("Realizó el cambio de estado del grupo '"+grupoVista[0]+"' a Disuelto debe adjuntar el archivo que soporta el cambio.");
					return;
				}
			}
		}
		
		
		if (!grupo.getEstadoGrupoColciencias().getId().equals(grupoVista[4])) {
			igual = false;
			grupo.getEstadoGrupoColciencias().setId(grupoVista[4]);
			cambiosGruposHistorico += "ec,";
		}
		if (!grupo.getIdColciencias().equals(grupoVista[5].trim())) {
			igual = false;
			grupo.setIdColciencias(grupoVista[5]);
		}
		if (!grupo.getCategoria().getId().equals(grupoVista[6])) {
			igual = false;
			grupo.getCategoria().setId(grupoVista[6]);
		}

		// Se obtiene el grupo completo desde disco, dado que el
		// grupo que se
		// trae en la consulta es un grupo sin informacion.
		if (!igual) {
			if(esCadenaVacia(grupoVista[8])) {
				mensajeError("Realizó el cambios en el grupo '"+grupoVista[0]+"' debe ingresar una justificación.");
				return;
			}
			justificacionGrupoHistorico = grupoVista[8];
			Grupo grupocompleto = servicioGrupo.obtenerGrupo(grupo.getId());
			grupocompleto.getEstadoGrupo().setId(grupo.getEstadoGrupo().getId());
			grupocompleto.getEstadoGrupoColciencias().setId(grupo.getEstadoGrupoColciencias().getId());
			grupocompleto.setIdColciencias(grupo.getIdColciencias());
			grupocompleto.getCategoria().setId(grupo.getCategoria().getId());
			servicioGeneral.guardarObjeto(grupocompleto);
			if (cambiosGruposHistorico != "") {
				guardarHistoricoEstadoGrupo(grupo, cargarPersonaActual(), grupoVista[9]);
			}
		}
	}

	/**
	 * Crear lista consulta.
	 *
	 * @param array
	 *            the array
	 * @return the string
	 */
	public String crearListaConsulta(String[] array) {
		String values = "";
		int tamanio = array.length;
		for (int i = 0; i < tamanio; i++) {
			if (values.length() > 0) {
				values += ",";
			}
			values += "'" + array[i] + "'";
		}
		return values;
	}

	/**
	 * Crear listado dependencias.
	 *
	 * @param dependencias
	 *            the dependencias
	 */
	private void crearListadoDependencias(Map<String, String> dependencias) {
		dependenciaItems = new ArrayList<SelectItem>();
		for (Map.Entry<String, String> entry : dependencias.entrySet()) {
			SelectItem selectItem = new SelectItem(entry.getValue(), entry.getKey());
			dependenciaItems.add(selectItem);
		}

	}

	/**
	 * Filtrar grupos.
	 */
	public void filtrarGrupos() {

		// Se limpian los campos
		codigoFiltro = codigoFiltro.trim();
		nombreFiltro = nombreFiltro.trim();
		codigoColFiltro = codigoColFiltro.trim();

		// Se ejecuta busqueda
		gruposVista = cargarDatosGrupos();
	}

	/**
	 * Gets the categorias.
	 *
	 * @return the categorias
	 */
	public List<CategoriaGrupo> getCategorias() {
		return categorias;
	}

	/**
	 * Gets the categorias col filtro.
	 *
	 * @return the categoriasColFiltro
	 */
	public String[] getCategoriasColFiltro() {
		return categoriasColFiltro;
	}

	/**
	 * Gets the codigo col filtro.
	 *
	 * @return the codigoColFiltro
	 */
	public String getCodigoColFiltro() {
		return codigoColFiltro;
	}

	/**
	 * Gets the codigo filtro.
	 *
	 * @return the codigoFiltro
	 */
	public String getCodigoFiltro() {
		return codigoFiltro;
	}

	/**
	 * Gets the dependencia filtro.
	 *
	 * @return the dependenciaFiltro
	 */
	public String getDependenciaFiltro() {
		return dependenciaFiltro;
	}

	/**
	 * Gets the dependencia items.
	 *
	 * @return the dependencia items
	 */
	public List<SelectItem> getDependenciaItems() {
		return dependenciaItems;
	}

	/**
	 * Gets the estado grupos cocliencias.
	 *
	 * @return the estado grupos cocliencias
	 */
	public List<EstadoGrupoColciencias> getEstadosGruposCocliencias() {
		return estadosGruposCocliencias;
	}

	/**
	 * Gets the estados s cienti filtro.
	 *
	 * @return the estados s cienti filtro
	 */
	public String[] getEstadosSCientiFiltro() {
		return estadosSCientiFiltro;
	}

	/**
	 * Gets the estados unal.
	 *
	 * @return the estados unal
	 */
	public List<EstadoGrupo> getEstadosUnal() {
		return estadosUnal;
	}

	/**
	 * Gets the estados unal filtro.
	 *
	 * @return the estadosUnalFiltro
	 */
	public String[] getEstadosUnalFiltro() {
		return estadosUnalFiltro;
	}

	/**
	 * Gets the grupos.
	 *
	 * @return the grupos
	 */
	public List<Grupo> getGrupos() {
		return grupos;
	}

	/**
	 * Gets the grupo seleccionado.
	 *
	 * @return the grupo seleccionado
	 */
	public String[] getGrupoSeleccionado() {
		return grupoSeleccionado;
	}

	/**
	 * Gets the grupos vista.
	 *
	 * @return the grupos
	 */
	public List<String[]> getGruposVista() {
		return gruposVista;
	}

	/**
	 * Gets the historicos estado grupo.
	 *
	 * @return the historicosEstadoGrupo
	 */
	public List<HistoricoEstadoGrupo> getHistoricosEstadoGrupo() {
		return historicosEstadoGrupo;
	}

	/**
	 * Gets the nombre filtro.
	 *
	 * @return the nombreFiltro
	 */
	public String getNombreFiltro() {
		return nombreFiltro;
	}

	/**
	 * Gets the total grupos investigacion.
	 *
	 * @return the total grupos investigacion
	 */
	public int getTotalGruposInvestigacion() {
		if (gruposVista != null) {
			return gruposVista.size();
		} else {
			return 0;
		}
	}

	/**
	 * Guardar cambios realizados.
	 */
	public void guardarCambiosRealizados() {
		if (!esListaVacia(gruposVista)) {
			Iterator<String[]> i = gruposVista.iterator();

			// Se iteran todos los grupos de la vista para validar si hay
			// cambios y guardarlos
			while (i.hasNext()) {
				String[] grupoVista = i.next();
				buscarGrupoyGuardar(grupoVista);
			}
			
			if (error) {
				FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_WARN,
						"La información de los grupos ha sigo guardada exitosamente. Sin embargo, los grupos "
								+ idError.substring(0, idError.length() - 1)
								+ " no se han actualizado, porque se indica que fue categorizado, pero no se define una categoría ScienTI válida.",
						null));
			} else {
				FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_INFO,
						"La información de los grupos ha sigo guardada exitosamente.", null));
			}

			// Se ejecuta busqueda nuevamente
			gruposVista = cargarDatosGrupos();
		}
	}

	/**
	 * Reiniciar filtros.
	 */
	public void reiniciarFiltros() {
		codigoFiltro = "";
		nombreFiltro = "";
		codigoColFiltro = "";
		estadosUnalFiltro = null;
		estadosSCientiFiltro = null;
		categoriasColFiltro = null;
		dependenciaFiltro = "";

		// Se ejecuta busqueda
		gruposVista = cargarDatosGrupos();

	}

	/**
	 * Se imprime reporte de grupo.
	 */
	public void reporte() {
		String[] grupoVistaActual = grupoSeleccionado;
		imprimirReporteGrupo(grupoVistaActual[0]);
	}

	/**
	 * Sets the categorias col filtro.
	 *
	 * @param categoriasColFiltro
	 *            the categoriasColFiltro to set
	 */
	public void setCategoriasColFiltro(String[] categoriasColFiltro) {
		this.categoriasColFiltro = categoriasColFiltro;
	}

	/**
	 * Sets the codigo col filtro.
	 *
	 * @param codigoColFiltro
	 *            the codigoColFiltro to set
	 */
	public void setCodigoColFiltro(String codigoColFiltro) {
		this.codigoColFiltro = codigoColFiltro;
	}

	/**
	 * Sets the codigo filtro.
	 *
	 * @param codigoFiltro
	 *            the new codigo filtro
	 */
	public void setCodigoFiltro(String codigoFiltro) {
		this.codigoFiltro = codigoFiltro;
	}

	/**
	 * Sets the dependencia filtro.
	 *
	 * @param dependenciaFiltro
	 *            the dependenciaFiltro to set
	 */
	public void setDependenciaFiltro(String dependenciaFiltro) {
		this.dependenciaFiltro = dependenciaFiltro;
	}

	/**
	 * Sets the estados s cienti filtro.
	 *
	 * @param estadosSCientiFiltro
	 *            the new estados s cienti filtro
	 */
	public void setEstadosSCientiFiltro(String[] estadosSCientiFiltro) {
		this.estadosSCientiFiltro = estadosSCientiFiltro;
	}

	/**
	 * Sets the estados unal filtro.
	 *
	 * @param estadosUnalFiltro
	 *            the estadosUnalFiltro to set
	 */
	public void setEstadosUnalFiltro(String[] estadosUnalFiltro) {
		this.estadosUnalFiltro = estadosUnalFiltro;
	}

	/**
	 * Sets the grupo seleccionado.
	 *
	 * @param grupoSeleccionado
	 *            the new grupo seleccionado
	 */
	public void setGrupoSeleccionado(String[] grupoSeleccionado) {
		this.grupoSeleccionado = grupoSeleccionado;
	}

	/**
	 * Sets the nombre filtro.
	 *
	 * @param nombreFiltro
	 *            the nombreFiltro to set
	 */
	public void setNombreFiltro(String nombreFiltro) {
		this.nombreFiltro = nombreFiltro;
	}

	public void verificarCambios() {
		Iterator<String[]> gv = gruposVista.iterator();
		while (gv.hasNext()) {
			String[] grupoVista = gv.next();
			grupoVista[7] = "NO";
			Iterator<Grupo> g = grupos.iterator();
			while (g.hasNext()) {
				Grupo grupoCargado = g.next();
				if (grupoVista[4].equals("RG") || grupoVista[4].equals("NR")
						|| grupoVista[4].equals("SA")) {
					grupoVista[6] = "S";
				} else if (grupoVista[4].equals("RC")) {
					grupoVista[6] = "R";
				}
				if (grupoVista[0].equals(grupoCargado.getId().toString())
						&& (!grupoVista[3].equals(grupoCargado.getEstadoGrupo().getId())
								|| !grupoVista[4].equals(grupoCargado.getEstadoGrupoColciencias().getId()))) {
					grupoVista[7] = "SI";
					break;
				}
			}
		}
	}

	public List<CategoriaGrupo> getCategoriasActualizacion() {
		return categoriasActualizacion;
	}

	public void setCategoriasActualizacion(List<CategoriaGrupo> categoriasActualizacion) {
		this.categoriasActualizacion = categoriasActualizacion;
	}

	public boolean isVieneVicedecanatura() {
		return vieneVicedecanatura;
	}

	public void setVieneVicedecanatura(boolean vieneVicedecanatura) {
		this.vieneVicedecanatura = vieneVicedecanatura;
	}

	public boolean isVieneDireccion() {
		return vieneDireccion;
	}

	public void setVieneDireccion(boolean vieneDireccion) {
		this.vieneDireccion = vieneDireccion;
	}
	
	public void prepararCambioLider() {
		cargarTipoDocumento();
		grupoEdicion = new Grupo();
		nuevoLiderGrupo = new InvestigadorGrupo();
		nuevoInvestigador = new Investigador();
		documento = "";
		tipoDocumentoInv = "";
		grupoEdicion = servicioGrupo.obtenerGrupo(Long.parseLong(grupoSeleccionado[0]));
		mostrarBotonCambioLider = true;
		
	}
	
	public void buscarNuevoLider() {
		nuevoInvestigador = new Investigador();
		nuevoLiderGrupo = new InvestigadorGrupo();
		if (esCadenaVacia(tipoDocumentoInv) || esCadenaVacia(documento)) {
			mensajeError("Debe seleccionar un tipo de documento e ingresar un documento");
			return;
		}

			Iterator<InvestigadorGrupo> i = grupoEdicion.getInvestigadoresGrupo().iterator();
			while (i.hasNext()) {
				InvestigadorGrupo investigadorGrupo = i.next();
				// Se verifican documentos de identidad
				if (investigadorGrupo != null
						&& documento.equals(investigadorGrupo.getInvestigador().getId().getDocumento())
						&& tipoDocumentoInv.equals(investigadorGrupo.getInvestigador().getId().getTipoDocumento())) {
					nuevoInvestigador = investigadorGrupo.getInvestigador();
					nuevoLiderGrupo = investigadorGrupo;
					break;
				}
			}
			if(nuevoInvestigador.getId()==null || esCadenaVacia(nuevoInvestigador.getId().getDocumento())) {
				IdPersona id = new IdPersona();
				id.setTipoDocumento(tipoDocumentoInv);
				id.setDocumento(documento);
				nuevoInvestigador = servicioPersona.obtenerInvestigador(id);
				if((nuevoInvestigador==null || nuevoInvestigador.getId()==null 
						|| esCadenaVacia(nuevoInvestigador.getId().getDocumento()))
						|| (!esCadenaVacia(nuevoInvestigador.getInterno()) && !Investigador.INTERNO.equals(nuevoInvestigador.getInterno()))) {
					mensajeError("Inválido: No se encontró un investigador con el tipo y número de documento indicado o se encontró un investigador que no es docente activo de la Universidad.");
					return;
				}
			}
			nuevoLiderGrupo.setGrupo(grupoEdicion);
			nuevoLiderGrupo.setInvestigador(nuevoInvestigador);
			nuevoLiderGrupo.setFuncion("Líder");
			nuevoLiderGrupo.setTipo(InvestigadorGrupo.LIDER);
			mensajeInfo("Nuevo líder válido. Para realizar le cambio haga clic en el botón 'Cambiar líder'");

	}
	
	public void guardarCambioLider() {
		if(nuevoInvestigador==null || nuevoInvestigador.getId()==null || esCadenaVacia(nuevoInvestigador.getId().getDocumento())) {
			mensajeError("Debe primero validar el nuevo líder. Ingrese tipo y número de documento y valide el nuevo lider para el grupo");
			return;
		}else if(nuevoLiderGrupo == null || nuevoLiderGrupo.getInvestigador()==null) {
			mensajeError("Debe indicar un nuevo líder válido");
			return;
		}
		
		
		HistoricoCambioLiderGrupo historicoCambioLiderGrupo = new HistoricoCambioLiderGrupo();
		historicoCambioLiderGrupo.setFecha(new Date());
		historicoCambioLiderGrupo.setGrupo(grupoEdicion);
		try {
		historicoCambioLiderGrupo.setLiderAnterior(grupoEdicion.getLider().getInvestigador());
		servicioGeneral.guardarObjeto(historicoCambioLiderGrupo);
		InvestigadorGrupo liderAntiguo = grupoEdicion.getLider();
		liderAntiguo.setTipo(InvestigadorGrupo.DOCENTE);
		servicioGeneral.guardarObjeto(liderAntiguo);
		servicioGeneral.guardarObjeto(nuevoLiderGrupo);
		enviarCorreoCambioLider(liderAntiguo.getInvestigador().getNombreCompletoMinusculas(),liderAntiguo.getInvestigador().getEmail());
		}catch (Exception e) {
			e.printStackTrace();
			mensajeError("Hubo un problema con el cambio de líder o con el envío del correo de notificación");
			return;
		}
		
		
		cargarDatosGrupos();
		mensajeInfo("Líder Cambiado satisfactoriamente");
		mostrarBotonCambioLider = false;
		
	}
	
	private void enviarCorreoCambioLider(String nombreAntiguoLider, String correo) {
		CorreoPlantilla correoActual = new CorreoPlantilla();
		correoActual = cargarPlantilla(401);
		String cuerpoCorreo = correoActual.getCuerpo().replaceAll("<<EST>>",
				((Investigador) sesion.getAttribute("persona")).getNombreCompleto());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<ID>>", grupoEdicion.getId().toString());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<GRUPO>>", grupoEdicion.getNombre());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<LIDER_ANT>>", nombreAntiguoLider);
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<LIDER_NUEVO>>",grupoEdicion.getLider().getInvestigador().getNombreCompletoMinusculas());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<SEDE>>", grupoEdicion.getSede().getNombre().toString());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<FACULTAD>>", grupoEdicion.getDependencia().getNombre());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<DEPENDENCIA>>", grupoEdicion.getDependencia().getNombre());
		Correo mensaje = new Correo();
		mensaje.setOrigen(Correo.CORREO_HERMES);
		mensaje.setAsunto(correoActual.getAsunto().replaceAll("<<ID>>", grupoEdicion.getId().toString()));
		mensaje.setCuerpo(cuerpoCorreo);
		mensaje.adicionarDireccion(grupoEdicion.getLider().getInvestigador().getEmail());
		mensaje.adicionarDireccion(correo);;
		servicioCorreo.enviarCorreo(mensaje);
	}

	public Grupo getGrupoEdicion() {
		return grupoEdicion;
	}

	public void setGrupoEdicion(Grupo grupoEdicion) {
		this.grupoEdicion = grupoEdicion;
	}

	public Investigador getNuevoInvestigador() {
		return nuevoInvestigador;
	}

	public void setNuevoInvestigador(Investigador nuevoInvestigador) {
		this.nuevoInvestigador = nuevoInvestigador;
	}
	
	public void cargarTipoDocumento() {
	
		List<TipoDocumento> listaTipoDocumento = servicioGeneral.obtenerTiposDeDocumento();
		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			TipoDocumento td = listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
		}
		tipoDocumentoInv = listaTipoDocumento.get(0).getId();
	}

	public SelectItem[] getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}

	public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
		this.tipoDocumentoItem = tipoDocumentoItem;
	}

	public String getTipoDocumentoInv() {
		return tipoDocumentoInv;
	}

	public void setTipoDocumentoInv(String tipoDocumentoInv) {
		this.tipoDocumentoInv = tipoDocumentoInv;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public InvestigadorGrupo getNuevoLiderGrupo() {
		return nuevoLiderGrupo;
	}

	public void setNuevoLiderGrupo(InvestigadorGrupo nuevoLiderGrupo) {
		this.nuevoLiderGrupo = nuevoLiderGrupo;
	}

	public boolean isMostrarBotonCambioLider() {
		return mostrarBotonCambioLider;
	}

	public void setMostrarBotonCambioLider(boolean mostrarBotonCambioLider) {
		this.mostrarBotonCambioLider = mostrarBotonCambioLider;
	}
	
	public void abrirVentanaCargarArchivo() {
		
	}
	
	public void descargarArchivo() {
		if(!esCadenaVacia(historicoSeleccionado.getArchivoCambio())) {
		String ext = obtenerExtensionArchivo(historicoSeleccionado.getArchivoCambio());
		descargarArchivoGenerico("HER_HISTORICO_ESTADO_GRUPO", historicoSeleccionado.getId().toString(), historicoSeleccionado.getId().toString() + ext);
		}
	}
	
	public void guardarArchivo(FileUploadEvent event) {
		archivoCargar = event.getFile();
		cargarArchivoDisco(archivoCargar, "HER_HISTORICO_ESTADO_GRUPO", grupoSeleccionado[0].toString() + "");
		int i = archivoCargar.getFileName().lastIndexOf("\\");
		grupoSeleccionado[9] = archivoCargar.getFileName().substring(i + 1);
	}

	public UploadedFile getArchivoCargar() {
		return archivoCargar;
	}
	
	public void imprimirReporteFormatoGrupo() {
		imprimirReporteFormatoGrupo(grupoSeleccionado[0].toString());
	}

	public void setArchivoCargar(UploadedFile archivoCargar) {
		this.archivoCargar = archivoCargar;
	}

	public boolean isRequiereArchivo() {
		return requiereArchivo;
	}

	public void setRequiereArchivo(boolean requiereArchivo) {
		this.requiereArchivo = requiereArchivo;
	}

	public String getArchivoAval() {
		return archivoAval;
	}

	public void setArchivoAval(String archivoAval) {
		this.archivoAval = archivoAval;
	}

	public HistoricoEstadoGrupo getHistoricoSeleccionado() {
		return historicoSeleccionado;
	}

	public void setHistoricoSeleccionado(HistoricoEstadoGrupo historicoSeleccionado) {
		this.historicoSeleccionado = historicoSeleccionado;
	}

	public List<HistoricoFormularioGrupo> getHistoricosFormularioGrupo() {
		return historicosFormularioGrupo;
	}

	public void setHistoricosFormularioGrupo(List<HistoricoFormularioGrupo> historicosFormularioGrupo) {
		this.historicosFormularioGrupo = historicosFormularioGrupo;
	}
}
