package co.edu.unal.hermes.vista.colecciones;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.ArchivoColeccion;
import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.Coleccion;
import co.edu.unal.hermes.modelo.ColeccionCatalogacion;
import co.edu.unal.hermes.modelo.ColeccionClasificacion;
import co.edu.unal.hermes.modelo.ColeccionGestion;
import co.edu.unal.hermes.modelo.ColeccionNomenclatura;
import co.edu.unal.hermes.modelo.ColeccionPersona;
import co.edu.unal.hermes.modelo.ColeccionTipoObjeto;
import co.edu.unal.hermes.modelo.ColeccionTipoPreservacion;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Departamento;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.Dominio;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.Estudiante;
import co.edu.unal.hermes.modelo.HistoricoColeccion;
import co.edu.unal.hermes.modelo.HistoricoFormularioGrupo;
import co.edu.unal.hermes.modelo.IdDominioDetalle;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.Parametro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Rol;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoFormacion;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.vista.ManejadorBase;
import co.edu.unal.hermes.vista.utils.Util;
import sun.awt.image.ImageFormatException;

public class ManejadorHojaVidaColeccion extends ManejadorBase {

	private static final long serialVersionUID = -9151417907152676579L;
	private Coleccion coleccion;
	private SelectItem[] listaSedesItem;
	private List<Dependencia> listaSedes;
	private Persona curadorColeccion;
	private SelectItem[] listaTiposItem;
	private List<DominioDetalle> listaTipos;
	private SelectItem[] listaDependenciasItem;
	private List<Dependencia> listaDependencias;
	private String departamento;
	private SelectItem[] listaDepartamentosItem;
	private SelectItem[] listaCiudadesItem;
	private SelectItem[] listaTiposDocItem;
	private List<TipoDocumento> listaTiposDoc;
	private ColeccionPersona personalCol;
	private SelectItem[] listaTipoPerItem;
	private List<ColeccionPersona> listaPersonalEliminado;
	private List<DominioDetalle> listaTipoPreservacion;
	private SelectItem[] listaPreservacionItem;
	private ColeccionTipoPreservacion tipoPreservacionColeccion;
	private List<ColeccionTipoPreservacion> listaTiposPreservacionColeccion;
	private List<DominioDetalle> listaTiposNomenclaturales;
	private List<ColeccionNomenclatura> listaTiposEliminados;
	private SelectItem[] listaNomenclaturasItem;
	private ColeccionNomenclatura nomenclaturaColeccion;
	private String nombreImagen;
	private ColeccionNomenclatura nomenc;
	private ColeccionTipoPreservacion regc;
	private ColeccionPersona personaSeleccionada;
	private DominioDetalle regionSeleccionada;
	private DominioDetalle tipoSeleccionada;
	boolean validaciones = false;
	SelectItem[] dependenciaItemCompleta;
	boolean tipos = false;
	private List<DominioDetalle> listaGruposBiologicos;
	private SelectItem[] listaGruposBiologicosItem;
	private ColeccionCatalogacion catalogacionColeccion;
	private List<DominioDetalle> listaSubGruposBiologicos;
	private SelectItem[] listaSubGruposBiologicosItem;
	private String grupoBio;
	private String subGrupoBio;
	private long numeroEjemplares = 0L;
	private long numeroEjemplaresNoBiologico = 0L;
	private float ejemplaresCatalogados;
	private float ejemplaresCatalogadosNoBiologico;
	private float ejemplaresSistematizados;
	private float ejemplaresSistematizadosNoBiologico;
	private float ejemplaresIdentificadosOrden;
	private float ejemplaresIdentificadosFamilia;
	private float ejemplaresIdentificadosGenero;
	private float ejemplaresIdentificadosEspecie;
	private float ejemplaresIdentificadosFilum;
	private ColeccionCatalogacion catalogc;
	private String grupoBioTipo;
	private boolean nuevaPersona = false;
	private String nombresExterno;
	private String apellidosExterno;
	private String nombresExterno2;
	private String apellidosExterno2;
	private String correoElectronico;
	private ColeccionPersona curador;
	private DominioDetalle grupoBiologicoCurador;

	// archivos
	private ArchivoColeccion archivoSeleccionado;
	private List<ArchivoColeccion> listaArchivos;
	private UploadedFile archivoCargar;

	private StreamedContent imagen;
	boolean bandera = true;
	boolean banderaDos = false;
	private CroppedImage croppedImage;
	private String newImageNameActual;
	private String newImageName;
	boolean banderaTabla = false;
	String path = RUTA_ARCHIVOS + "HER_COLECCION" + File.separator;
	ServletContext servletContext;

	private String extension;
	boolean banderaUno = false;
	private boolean terminaEdicion;
	private boolean guardarParcialmente;
	private boolean soloConsulta;
	private boolean aceptaTerminos;
	private String institucionLabora; // Persona externa

	File actual;
	String newFileName = "";

	private Persona repLegal;

	// REQUERIMIENTO COLECCIONES 2024
	private SelectItem[] listaClasificacionItem;
	private ColeccionClasificacion coleccionClasificacion;
	private ColeccionClasificacion clasificacionSeleccionada;
	private SelectItem[] listaTipoObjetoColeccionItem;
	private String tipoObjetoColeccion;
	private SelectItem[] listaSubTipoObjetoColeccionItem;
	private String subTipoObjetoColeccion;
	private ColeccionTipoObjeto coleccionTipoObjeto;
	private ColeccionTipoObjeto coleccionTipoObjetoSeleccionado;
	private boolean esColeccionBiologica;
	private boolean noColeccionBiologica;
	
	private String grupoNoBiologico;
	private String subGrupoNoBiologico;
	private String unidadMedidaNoBiologico;
	private SelectItem[] tipoFormacionItem;
	private String idTipoFormacion;

	public ManejadorHojaVidaColeccion() {

		curador = new ColeccionPersona();
		personalCol = new ColeccionPersona();
		Persona personaC = new Persona();
		IdPersona idPersonalCol = new IdPersona();
		idPersonalCol.setTipoDocumento(TipoDocumento.CEDULA);
		personaC.setId(idPersonalCol);
		personalCol.setPersona(personaC);
		personalCol.setColeccion(coleccion);

		tipoPreservacionColeccion = new ColeccionTipoPreservacion();
		DominioDetalle tipoPreCol = new DominioDetalle();
		IdDominioDetalle idDominioDetalle = new IdDominioDetalle();
		tipoPreCol.setIdentificador(idDominioDetalle);
		tipoPreservacionColeccion.setTipoPreservacion(tipoPreCol);

		coleccionClasificacion = new ColeccionClasificacion();
		DominioDetalle clasif = new DominioDetalle();
		IdDominioDetalle idDominioDetalleClasif = new IdDominioDetalle();
		clasif.setIdentificador(idDominioDetalleClasif);
		coleccionClasificacion.setClasificacion(clasif);

		nomenclaturaColeccion = new ColeccionNomenclatura();
		DominioDetalle nomenclaturaCol = new DominioDetalle();
		IdDominioDetalle idDominioDetalle2 = new IdDominioDetalle();
		nomenclaturaCol.setIdentificador(idDominioDetalle2);
		nomenclaturaColeccion.setNomenclatura(nomenclaturaCol);
		catalogacionColeccion = new ColeccionCatalogacion();

		listaTiposPreservacionColeccion = new ArrayList<ColeccionTipoPreservacion>();
		listaArchivos = new ArrayList<ArchivoColeccion>();
		esColeccionBiologica = false;
		noColeccionBiologica = false;

		coleccion = (Coleccion) sesion.getAttribute("colEdit");
		personaActual = (Persona) sesion.getAttribute("persona");
		if (coleccion == null) {
			coleccion = new Coleccion();
			Persona curador1 = new Persona();
			IdPersona idCurador = new IdPersona();
			idCurador.setTipoDocumento(TipoDocumento.CEDULA);
			curador1.setId(idCurador);
			coleccion.setCuradorGeneral(curador1);
			terminaEdicion = false;
			coleccion.setCuradorGeneral(personaActual);
			curadorColeccion = personaActual;
		} else {
			curadorColeccion = coleccion.getCuradorGeneral();
			if (coleccion.getSede() != null && coleccion.getSede().getId() != null) {
				cargarDependencias();
			}
			if (coleccion.getCiudad() != null && coleccion.getCiudad().getId() != null) {
				departamento = coleccion.getCiudad().getDepartamento().getId();
				cargarCiudadesDepto();
			}

			if (coleccion.getListaTiposNomenclaturales() != null
					&& !coleccion.getListaTiposNomenclaturales().isEmpty()) {
				tipos = true;
			}
			recuperarArchivos();
		}

		if (coleccion.getSede() == null) {
			Sede sede2 = new Sede();
			coleccion.setSede(sede2);
		}

		if (coleccion.getDependencia() == null) {
			Dependencia dependenciaCol = new Dependencia();
			coleccion.setDependencia(dependenciaCol);
		}

		if (coleccion.getCiudad() == null) {
			Ciudad ciudadCol = new Ciudad();
			coleccion.setCiudad(ciudadCol);
		}

		if (esListaVacia(listaPersonalEliminado)) {
			listaPersonalEliminado = new ArrayList<ColeccionPersona>();
		}
		listaSedes();
		tiposColecciones();
		departamentos();
		tipoDoc();
		listaTipoPersonal();
		tiposPreservacionColecciones();
		nomenclaturaColecciones();
		gruposBiologicos();
		cargarListaClasificacion();
		cargarListaTipoObjetos();
		esColeccionBiologica = revisarColeccionBiologica();
		noColeccionBiologica = revisarColeccionNoBiologica();

		servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

		if (coleccion.getId() != null) {
			nombreImagen = coleccion.getId().toString();
		} else {
			nombreImagen = "imagenTemporal";
		}

		actual = new File(path + nombreImagen + ".jpg");

		if (actual.exists()) {
			bandera = true;
		} else {
			bandera = false;
		}

		Parametro p = (Parametro) servicioGeneral
				.obtenerObjetos(Parametro.class, "select p from Parametro p where p.id = '358'").get(0);
		IdPersona ip = new IdPersona(p.getValor(), "C");
		setRepLegal(servicioPersona.obtenerPersona(ip));
	}
	
	

	public boolean revisarColeccionBiologica() {

			if (!coleccion.getListaTiposObjetos().isEmpty()) {
				for (ColeccionTipoObjeto a : coleccion.getListaTiposObjetos()) {
					if (a.getSubTipoObjeto().getEstado().equals("BIO")) {
						return true;
					}
				}
			}

		return false;
	}
	
	public boolean revisarColeccionNoBiologica() {

		if (!coleccion.getListaTiposObjetos().isEmpty()) {
			for (ColeccionTipoObjeto a : coleccion.getListaTiposObjetos()) {
				if (!a.getSubTipoObjeto().getEstado().equals("BIO")) {
					return true;
				}
			}
		}

	return false;
}

	public void agregarPersonaExterna() {
		if(aceptaTerminos) {
		Persona personaNueva = new Persona();
		IdPersona id = new IdPersona();
		personaNueva.setEmail(correoElectronico);
		id.setTipoDocumento(personalCol.getPersona().getId().getTipoDocumento());
		id.setDocumento(personalCol.getPersona().getId().getDocumento());
		personaNueva.setId(id);
		personaNueva.setNombre1(nombresExterno);
		personaNueva.setNombre2(nombresExterno2);
		personaNueva.setApellido1(apellidosExterno);
		personaNueva.setApellido2(apellidosExterno2);
		personaNueva.setGenero("S");
		personaNueva.setDireccion(null);
		personaNueva.setTelefono(null);
		nuevaPersona = false;
		this.servicioPersona.insertarNuevaPersonaDatosBasicos(personaNueva);
		personalCol.setPersona(personaNueva);
		obtenerPersonalCol();
		}else {
			mensajeError("Debe aceptar los términos para guardar los datos de la persona");
		}

	}

	public void cancelarPersonaExterna() {
		nuevaPersona = false;
	}

	private void listaSedes() {
		listaSedes = (List<Dependencia>) servicioGeneral.obtenerSedes();
		listaSedesItem = new SelectItem[listaSedes.size() + 1];
		listaSedesItem[0] = new SelectItem("", "Seleccione sede");

		for (int i = 1; i < listaSedes.size() + 1; i++) {
			Dependencia sede = (Dependencia) listaSedes.get(i - 1);
			listaSedesItem[i] = new SelectItem(sede.getId(), sede.getNombre().trim().toUpperCase());
		}
	}

	private void listaTipoPersonal() {
		listaTipoPerItem = new SelectItem[7];
		listaTipoPerItem[0] = new SelectItem("", "Seleccione tipo de personal");
		listaTipoPerItem[1] = new SelectItem("A", "Administrativo(a)");
		listaTipoPerItem[2] = new SelectItem("CU", "Curador(a)");
		listaTipoPerItem[3] = new SelectItem("CU1", "Director(a) de la colección");
		listaTipoPerItem[4] = new SelectItem("EST", "Estudiante");
		listaTipoPerItem[5] = new SelectItem("EXT", "Externo");
		listaTipoPerItem[6] = new SelectItem("I", "Investigador(a)");
	}

	private void tiposColecciones() {
		listaTipos = servicioGeneral
				.obtenerListaObjetos("DominioDetalle where identificador.id = '99' ORDER BY descripcion");

		if (listaTipos != null && !listaTipos.isEmpty()) {
			listaTiposItem = new SelectItem[listaTipos.size() + 1];
			listaTiposItem[0] = new SelectItem("", "Seleccione tipo");
			for (int i = 1; i <= listaTipos.size(); i++) {
				DominioDetalle tipoCol = (DominioDetalle) listaTipos.get(i - 1);
				listaTiposItem[i] = new SelectItem(tipoCol.getIdentificador().getTipo(), tipoCol.getDescripcion());
			}
		}
	}

	private void tiposPreservacionColecciones() {
		listaTipoPreservacion = servicioGeneral
				.obtenerListaObjetos("DominioDetalle where identificador.id = '100' ORDER BY descripcion");

		if (!esListaVacia(listaTipoPreservacion)) {
			listaPreservacionItem = new SelectItem[listaTipoPreservacion.size() + 1];
			listaPreservacionItem[0] = new SelectItem("", "Seleccione tipo preservación");
			for (int i = 1; i <= listaTipoPreservacion.size(); i++) {
				DominioDetalle regionCol = (DominioDetalle) listaTipoPreservacion.get(i - 1);
				listaPreservacionItem[i] = new SelectItem(regionCol.getIdentificador().getTipo(),
						regionCol.getDescripcion());
			}
		}
	}

	private void cargarListaClasificacion() {
		try {
			listaClasificacionItem = crearListaItemDominioDetalle(Dominio.DOMINIO_CLASIF_COL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void cargarListaTipoObjetos() {
		try {
			listaTipoObjetoColeccionItem = crearListaItemDominioDetalle(Dominio.DOMINIO_TIPO_OBJETOS);
			tipoObjetoColeccion = listaTipoObjetoColeccionItem[0].getValue().toString();
			cargarListaSubtipos();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cargarListaSubtipos() {
		try {
			listaSubTipoObjetoColeccionItem = crearListaItemDominioDetalle(Dominio.DOMINIO_SUBTIPO_OBJETOS,
					tipoObjetoColeccion);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void nomenclaturaColecciones() {
		listaTiposNomenclaturales = servicioGeneral
				.obtenerListaObjetos("DominioDetalle where identificador.id = '101' ORDER BY descripcion");

		if (listaTiposNomenclaturales != null && !listaTiposNomenclaturales.isEmpty()) {
			listaNomenclaturasItem = new SelectItem[listaTiposNomenclaturales.size() + 1];
			listaNomenclaturasItem[0] = new SelectItem("", "Seleccione tipo nomenclatural");
			for (int i = 1; i <= listaTiposNomenclaturales.size(); i++) {
				DominioDetalle nomenclaturaCol = (DominioDetalle) listaTiposNomenclaturales.get(i - 1);
				listaNomenclaturasItem[i] = new SelectItem(nomenclaturaCol.getIdentificador().getTipo(),
						nomenclaturaCol.getDescripcion());
			}
		}
	}

	private void gruposBiologicos() {
		setListaGruposBiologicos(servicioGeneral.obtenerListaObjetos(
				"DominioDetalle where identificador.id = '111' and identificador.tipo like '%GRUB%' ORDER BY descripcion"));

		if (!esListaVacia(listaGruposBiologicos)) {
			listaGruposBiologicosItem = new SelectItem[listaGruposBiologicos.size()];
			for (int i = 0; i < listaGruposBiologicos.size(); i++) {
				DominioDetalle grupoCol = (DominioDetalle) listaGruposBiologicos.get(i);
				listaGruposBiologicosItem[i] = new SelectItem(grupoCol.getIdentificador().getTipo(),
						grupoCol.getDescripcion());
			}
			grupoBio = listaGruposBiologicos.get(0).getIdentificador().getTipo();
			subGruposBiologicos();
		}
	}

	public void subGruposBiologicos() {
		setListaSubGruposBiologicos(servicioGeneral.obtenerListaObjetos(
				"DominioDetalle where identificador.id = '111' and estado = '" + grupoBio + "' ORDER BY descripcion"));

		if (!esListaVacia(listaSubGruposBiologicos)) {
			listaSubGruposBiologicosItem = new SelectItem[listaSubGruposBiologicos.size() + 1];
			listaSubGruposBiologicosItem[0] = new SelectItem("", "Seleccione subgrupo biológico");
			for (int i = 1; i <= listaSubGruposBiologicos.size(); i++) {
				DominioDetalle grupoCol = (DominioDetalle) listaSubGruposBiologicos.get(i - 1);
				listaSubGruposBiologicosItem[i] = new SelectItem(grupoCol.getIdentificador().getTipo(),
						grupoCol.getDescripcion());
			}
		}
	}

	public void cargarDependencias() {

		if (coleccion.getSede().getId() == null && coleccion.getSede().getId() == 0L) {
			listaDependenciasItem = new SelectItem[0];
			return;
		}

		listaDependencias = servicioGeneral.obtenerListaObjetos("Dependencia d where d.sede.id = "
				+ coleccion.getSede().getId() + " AND d.esFacultad = 'Y' AND d.estado = 'A' ORDER BY d.id");

		if (!esListaVacia(listaDependencias)) {
			listaDependenciasItem = new SelectItem[listaDependencias.size()];
			for (int i = 0; i < listaDependencias.size(); i++) {
				Dependencia depCol = (Dependencia) listaDependencias.get(i);
				listaDependenciasItem[i] = new SelectItem(depCol.getId(), depCol.getNombre());
			}
		}
		if (coleccion.getDependencia() == null) {
			coleccion.setDependencia(new Dependencia());
			coleccion.getDependencia().setId(listaDependencias.get(0).getId());
			cargarUnidadesAcademicas();
		} else {
			cargarUnidadesAcademicas();
		}
	}

	private void departamentos() {
		String consulta = "select d from Departamento d where d.id like 'CO%' " + "order by d.nombre";
		List<Departamento> listaDepartament = servicioGeneral.obtenerObjetos(Departamento.class, consulta);

		if (!esListaVacia(listaDepartament)) {
			listaDepartamentosItem = new SelectItem[listaDepartament.size() + 1];
			listaDepartamentosItem[0] = new SelectItem("", "Seleccione departamento");
			for (int i = 1; i <= listaDepartament.size(); i++) {
				Departamento deptoCol = (Departamento) listaDepartament.get(i - 1);
				listaDepartamentosItem[i] = new SelectItem(deptoCol.getId(), deptoCol.getNombre());
			}
		}
	}

	public void cargarCiudadesDepto() {

		listaCiudadesItem = new SelectItem[0];
		if (esCadenaVacia(departamento)) {
			mensajeError("Debe seleccionar un departamento para que sean listados las ciudades/municipios.");
			return;
		} else {
			List<Ciudad> listaCiudades = servicioGeneral.obtenerObjetos(Ciudad.class,
					"select c from Ciudad c where c.departamento.id = '" + departamento + "'");
			listaCiudadesItem = new SelectItem[listaCiudades.size()];
			for (int i = 0; i < listaCiudades.size(); i++) {
				Ciudad ci = (Ciudad) listaCiudades.get(i);
				listaCiudadesItem[i] = new SelectItem(ci.getId(), ci.getNombre());
			}
		}

	}

	private void tipoDoc() {
		listaTiposDoc = servicioGeneral.obtenerListaObjetos("TipoDocumento td ORDER BY td.nombre");

		if (listaTiposDoc != null && !listaTiposDoc.isEmpty()) {
			listaTiposDocItem = new SelectItem[listaTiposDoc.size() + 1];
			listaTiposDocItem[0] = new SelectItem("", "Seleccione tipo de documento");
			for (int i = 1; i <= listaTiposDoc.size(); i++) {
				TipoDocumento tipoDocCur = (TipoDocumento) listaTiposDoc.get(i - 1);
				listaTiposDocItem[i] = new SelectItem(tipoDocCur.getId(), tipoDocCur.getNombre());
			}
		}
	}

	public void consultarCurador() {
		List<ColeccionPersona> curadores = servicioGeneral.obtenerObjetos(ColeccionPersona.class,
				"select cp from ColeccionPersona cp where " + "cp.persona.id.tipoDocumento = '"
						+ curador.getPersona().getId().getTipoDocumento() + "' and cp.persona.id.documento = '"
						+ curador.getPersona().getId().getDocumento() + "' ");
		if (!esListaVacia(curadores)) {
			curador = curadores.get(0);
			if (curador.getSubGrupoBiologicoCurador() != null
					&& StringUtils.isNotEmpty(curador.getSubGrupoBiologicoCurador().getEstado())) {
				String sql = "select dd from DominioDetalle dd where dd.identificador.id = 111 and dd.identificador.tipo = '"
						+ curador.getSubGrupoBiologicoCurador().getEstado() + "' ";
				List<DominioDetalle> gBiologico = servicioGeneral.obtenerObjetos(DominioDetalle.class, sql);
				if (!esListaVacia(gBiologico)) {
					grupoBiologicoCurador = new DominioDetalle();
					grupoBiologicoCurador = gBiologico.get(0);
				}
			}
		}
	}

	public void cargarUnidadesAcademicas() {

		List<Dependencia> dependenciasUN2;
		String dpnsql = "select e from Dependencia e where e.estado='A' and e.esDepartamento='Y' and e.nombre not like 'Comite%' and e.nombre not like 'Fondo%' and e.facultad.id = '"
				+ coleccion.getDependencia().getId() + "' order by e.nombre";
		dependenciasUN2 = servicioGeneral.obtenerObjetos(Dependencia.class, dpnsql);
		dependenciaItemCompleta = new SelectItem[dependenciasUN2.size()];
		for (int i = 0; i < dependenciasUN2.size(); i++) {
			Dependencia dd = (Dependencia) dependenciasUN2.get(i);
			dependenciaItemCompleta[i] = new SelectItem(dd.getId(), dd.getNombre());
		}
	}

	public void recuperarArchivos() {

		listaArchivos = new ArrayList<ArchivoColeccion>();
		String archsql = "select a from ArchivoColeccion a where a.coleccion.id='" + coleccion.getId()
				+ "' order by a.tipo asc";
		listaArchivos = servicioGeneral.obtenerObjetos(ArchivoColeccion.class, archsql);
	}

	private boolean verificarInvestigadorInterno(IdPersona id) {
		Investigador investigador = servicioPersona.obtenerInvestigador(id);
		if (investigador != null && investigador.getInterno() != null && "S".equals(investigador.getInterno())) {
			return true;
		}
		return false;
	}

	private boolean verificarAdministrativo(IdPersona id) {
		Investigador investigador = servicioPersona.obtenerInvestigador(id);
		if (investigador != null && investigador.getEsFuncionario() != null
				&& "S".equals(investigador.getEsFuncionario())) {
			return true;
		}
		return false;
	}

	private boolean personaRepetida(IdPersona id) {
		boolean existe = false;
		if (esListaVacia(coleccion.getListaPersonal())) {
			return existe;
		} else {
			for (int i = 0; i < coleccion.getListaPersonal().size(); i++) {
				IdPersona idPersona = coleccion.getListaPersonal().get(i).getPersona().getId();
				if ((idPersona.getTipoDocumento().equals(id.getTipoDocumento()))
						&& (idPersona.getDocumento().equals(id.getDocumento()))) {
					existe = true;
					break;
				}
			}
		}
		return existe;
	}

	public void obtenerPersonalCol() {
		if (!"".equals(personalCol.getTipoPersona())) {
			boolean ingresar = true;
			nuevaPersona = false;
			Persona personaAgregar = servicioPersona.obtenerPersona(personalCol.getPersona().getId());
			if ("CU1".equals(personalCol.getTipoPersona()) && coleccion.getCuradorGeneral() != null) {
				mensajeError(
						"Ya hay un curador principal asociado a la colección, para cambiarlo debe eliminarse de la lista. "
								+ "Recuerde que al cambiarlo, la colección pasará a la cuenta del curador principal y desaparecerá de la suya.");
				return;
			} else if (personaAgregar != null) {
				if (personaRepetida(personaAgregar.getId())) {
					mensajeError("La persona ya existe en la lista.");
					return;
				} else if ("I".equals(personalCol.getTipoPersona())
						&& !verificarInvestigadorInterno(personalCol.getPersona().getId())) {
					mensajeError("El investigador no fue encontrado, debe ser un docente de la Universidad Nacional. ");
					return;
				} else if ("A".equals(personalCol.getTipoPersona())
						&& !verificarAdministrativo(personalCol.getPersona().getId())) {
					mensajeError(
							"El administrativo no fue encontrado, debe ser un funcionario activo de la Universidad Nacional. ");
					return;
				}

				if ("EST".equals(personalCol.getTipoPersona())) {
					Estudiante estudiante = servicioPersona.obtenerEstudiante(personalCol.getPersona().getId());
					if (estudiante != null && "S".equals(estudiante.getInterno())) {
						personaAgregar = estudiante.convertirAPersona();
						ingresar = true;
					} else {
						mensajeError("El estudiante no fue encontrado o no se encuentra activo. ");
						return;
					}
				}
			} else if ("EXT".equals(personalCol.getTipoPersona()) || "CU".equals(personalCol.getTipoPersona())) {
				List<TipoFormacion> listaTipoFormacion = servicioGeneral.obtenerTiposDeFormacion();
				tipoFormacionItem = new SelectItem[listaTipoFormacion.size()];

				for (int i = 0; i < listaTipoFormacion.size(); i++) {
					TipoFormacion td = listaTipoFormacion.get(i);
					tipoFormacionItem[i] = new SelectItem(td.getId(), td.getNombre());
				}
				nuevaPersona = true;
				ingresar = false;
			} else {
				mensajeError("La persona no fue encontrada.");
				return;
			}

			if (ingresar) {
				ColeccionPersona personaColeccion = new ColeccionPersona();
				personaColeccion.setPersona(personaAgregar);
				personaColeccion.setTipoPersona(personalCol.getTipoPersona());
				personaColeccion.setColeccion(coleccion);
				if (ColeccionPersona.CURADOR_GENERAL.equals(personaColeccion.getTipoPersona())
						|| ColeccionPersona.CURADOR.equals(personaColeccion.getTipoPersona())) {
					if (!StringUtils.isNotEmpty(personalCol.getEspecialidad())) {
						mensajeError("Debe ingresar la especialidad del curador.");
						return;
					}
					if (!StringUtils.isNotEmpty(subGrupoBio)) {
						mensajeError("Debe ingresar el subgrupo biológico de experticia del curador.");
						return;
					}
					personaColeccion.setEspecialidad(personalCol.getEspecialidad());
					personaColeccion.setPerfilEnLinea(personalCol.getPerfilEnLinea());
					DominioDetalle subgrupo = new DominioDetalle();
					IdDominioDetalle id = new IdDominioDetalle();
					subgrupo.setIdentificador(id);
					subgrupo.getIdentificador().setTipo(subGrupoBio);
					subgrupo.getIdentificador().setId("111");
					personaColeccion.setSubGrupoBiologicoCurador(subgrupo);
				}
				coleccion.adicionarPersona(personaColeccion);
				IdPersona idPersonalCol = new IdPersona();
				Persona personaC = new Persona();
				personaC.setId(idPersonalCol);
				personalCol = new ColeccionPersona();
				personalCol.setPersona(personaC);
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Requerido: Debe seleccionar un tipo de personal", ""));
		}
	}

	private DominioDetalle obtenerTipoPreservacion() {
		for (DominioDetalle region : listaTipoPreservacion) {
			if (region.getIdentificador().getTipo()
					.equals(tipoPreservacionColeccion.getTipoPreservacion().getIdentificador().getTipo())) {
				return region;
			}
		}
		return null;
	}

	private DominioDetalle obtenerClasificacion() {
		List<DominioDetalle> listaClasificacion = servicioGeneral
				.obtenerListaObjetos("DominioDetalle where identificador.id = '313' ORDER BY descripcion");

		if (!esListaVacia(listaClasificacion)) {
			for (DominioDetalle clasif : listaClasificacion) {
				if (clasif.getIdentificador().getTipo()
						.equals(coleccionClasificacion.getClasificacion().getIdentificador().getTipo())) {
					return clasif;
				}
			}
			return null;
		}
		return null;
	}

	public void agregarTipoPreservacion() {
		DominioDetalle reg = obtenerTipoPreservacion();

		if (reg != null) {
			if (tipoPreservacionColeccion.getCobertura() != null
					&& tipoPreservacionColeccion.getCobertura().length() > 2399) {
				tipoPreservacionColeccion.setCobertura(tipoPreservacionColeccion.getCobertura().substring(0, 2399));
			}
			tipoPreservacionColeccion.setCobertura(modificarCaracteres(tipoPreservacionColeccion.getCobertura()));

			if (coleccion.getListaTiposPreservacion().isEmpty()) {
				tipoPreservacionColeccion.setTipoPreservacion(reg);
				coleccion.adicionarRegion(tipoPreservacionColeccion);
			} else {
				Boolean repetido = false;
				for (ColeccionTipoPreservacion a : coleccion.getListaTiposPreservacion()) {
					if (a.getTipoPreservacion().getIdentificador().getTipo()
							.equals(tipoPreservacionColeccion.getTipoPreservacion().getIdentificador().getTipo())) {
						repetido = true;
						break;
					}
				}
				if (repetido) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Preservación:", "Ya fue agregado el tipo de preservación seleccionada"));
				} else {
					tipoPreservacionColeccion.setTipoPreservacion(reg);
					coleccion.adicionarRegion(tipoPreservacionColeccion);
				}
			}
			tipoPreservacionColeccion = new ColeccionTipoPreservacion();
			DominioDetalle regionCol = new DominioDetalle();
			IdDominioDetalle idDominioDetalle = new IdDominioDetalle();
			regionCol.setIdentificador(idDominioDetalle);
			tipoPreservacionColeccion.setTipoPreservacion(regionCol);
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Preservación: ", "Debe seleccionar un tipo de preservación!"));
		}
	}

	public void agregarClasificacion() {
		DominioDetalle cla = obtenerClasificacion();

		if (cla != null) {
			if (coleccion.getListaClasificacion().isEmpty()) {
				coleccionClasificacion.setClasificacion(cla);
				coleccion.adicionarClasificacion(coleccionClasificacion);
			} else {
				Boolean repetido = false;
				for (ColeccionClasificacion a : coleccion.getListaClasificacion()) {
					if (a.getClasificacion().getIdentificador().getTipo()
							.equals(coleccionClasificacion.getClasificacion().getIdentificador().getTipo())) {
						repetido = true;
						break;
					}
				}
				if (repetido) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Clasificación:", "Ya fue agregado el tipo de clasificación seleccionada"));
				} else {
					coleccionClasificacion.setClasificacion(cla);
					coleccion.adicionarClasificacion(coleccionClasificacion);
				}
			}
			coleccionClasificacion = new ColeccionClasificacion();
			DominioDetalle clasif = new DominioDetalle();
			IdDominioDetalle idDominioDetalle = new IdDominioDetalle();
			clasif.setIdentificador(idDominioDetalle);
			coleccionClasificacion.setClasificacion(clasif);
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Clasificación: ", "Debe seleccionar un tipo de clasificación!"));
		}
	}

	public void agregarTipoObjetos() {
		DominioDetalle tipo = servicioGeneral.obtenerDominioDetalleUnico(Dominio.ID_DOMINIO_TIPO_OBJETOS,
				tipoObjetoColeccion);
		DominioDetalle subt = servicioGeneral.obtenerDominioDetalleUnico(Dominio.ID_DOMINIO_SUBTIPO_OBJETOS,
				subTipoObjetoColeccion);

		if (subt != null) {
			coleccionTipoObjeto = new ColeccionTipoObjeto();
			if (coleccion.getListaTiposObjetos().isEmpty()) {
				coleccionTipoObjeto.setTipoObjeto(tipo);
				coleccionTipoObjeto.setSubTipoObjeto(subt);
				coleccion.adicionarTipoObjetos(coleccionTipoObjeto);
			} else {
				Boolean repetido = false;
				for (ColeccionTipoObjeto a : coleccion.getListaTiposObjetos()) {
					if (a.getSubTipoObjeto().getIdentificador().getTipo().equals(subTipoObjetoColeccion)) {
						repetido = true;
						break;
					}
				}
				if (repetido) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Tipo de objetos:", "Ya fue agregado el tipo de objetos a la colección"));
				} else {   
					coleccionTipoObjeto.setTipoObjeto(tipo);
					coleccionTipoObjeto.setSubTipoObjeto(subt);
					coleccion.adicionarTipoObjetos(coleccionTipoObjeto);
				}
			}
			esColeccionBiologica = revisarColeccionBiologica();
			noColeccionBiologica = revisarColeccionNoBiologica();
			coleccionTipoObjeto = new ColeccionTipoObjeto();
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Tipo de objetos: ", "Debe seleccionar un tipo de objetos contenido en la colección!"));
		}
	}

	public void eliminarTipoObjeto() {
		coleccion.borrarTipoObjetos(coleccionTipoObjetoSeleccionado);
		esColeccionBiologica = revisarColeccionBiologica();
		noColeccionBiologica = revisarColeccionNoBiologica();
		if (coleccionTipoObjetoSeleccionado.getId() != null) {
			servicioGeneral.eliminarObjeto(coleccionTipoObjetoSeleccionado);
		}
	}

	private DominioDetalle obtenerNomenclatura() {
		for (DominioDetalle nomenclatura : listaTiposNomenclaturales) {
			if (nomenclatura.getIdentificador().getTipo()
					.equals(nomenclaturaColeccion.getNomenclatura().getIdentificador().getTipo())) {
				return nomenclatura;
			}
		}
		return null;
	}

	public void eliminarTipoNomenclatura() {
		coleccion.borrarTipoNomenclatural(nomenc);
		if (nomenc.getId() != null) {
			servicioGeneral.eliminarObjeto(nomenc);
		}
		coleccion.setCantidadEjemplaresTipo(coleccion.getCantidadEjemplaresTipo()-nomenc.getCantidad());
	}

	public void eliminarRegion() {
		coleccion.borrarRegion(regc);
		if (regc.getId() != null) {
			servicioGeneral.eliminarObjeto(regc);
		}
	}

	public void eliminarClasificacion() {
		coleccion.borrarClasificacion(clasificacionSeleccionada);
		if (clasificacionSeleccionada.getId() != null) {
			servicioGeneral.eliminarObjeto(clasificacionSeleccionada);
		}
	}

	public void eliminarPersonaColeccion() {
		coleccion.borrarPersona(personaSeleccionada);
		if (personaSeleccionada.getId() != null) {
			listaPersonalEliminado.add(personaSeleccionada);
		}
	}

	public void agregarTipoNomenclatural() {
		DominioDetalle nom = obtenerNomenclatura();
		DominioDetalle gru = null;
		for (DominioDetalle grupo : listaGruposBiologicos) {
			if (grupo.getIdentificador().getTipo().equals(grupoBioTipo)) {
				gru = grupo;
			}
		}

		if (nom != null && gru != null) {
			if (nomenclaturaColeccion.getCantidad() == 0L) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Tipos en coleccion:", "Debe indicar la cantidad de ejemplares"));
				return;
			}

			if (coleccion.getListaTiposNomenclaturales().isEmpty()) {
				nomenclaturaColeccion.setNomenclatura(nom);
				nomenclaturaColeccion.setGrupoBiologico(gru);
				coleccion.adicionarTipoNomenclatural(nomenclaturaColeccion);
			} else {
				Boolean repetido = false;
				for (ColeccionNomenclatura a : coleccion.getListaTiposNomenclaturales()) {
					if (a.getNomenclatura().getIdentificador().getTipo()
							.equals(nomenclaturaColeccion.getNomenclatura().getIdentificador().getTipo())
							&& a.getGrupoBiologico().getIdentificador().getTipo().equals(grupoBioTipo)) {
						repetido = true;
						break;
					}
				}
				if (repetido) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Tipo Nomenclatural",
							"Ya existe una cantidad de ejemplares del tipo nomenclatural y grupo biológico seleccionado"));
					return;
				} else {
					nomenclaturaColeccion.setNomenclatura(nom);
					nomenclaturaColeccion.setGrupoBiologico(gru);
					coleccion.adicionarTipoNomenclatural(nomenclaturaColeccion);
				}
			}

			coleccion.setCantidadEjemplaresTipo(coleccion.getCantidadEjemplaresTipo()+nomenclaturaColeccion.getCantidad());
			nomenclaturaColeccion = new ColeccionNomenclatura();
			DominioDetalle nomenclaturaCol = new DominioDetalle();
			IdDominioDetalle idDominioDetalle = new IdDominioDetalle();
			nomenclaturaCol.setIdentificador(idDominioDetalle);
			nomenclaturaColeccion.setNomenclatura(nomenclaturaCol);
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Tipo Nomenclatural", "Debe seleccionar un tipo nomenclatural!"));
		}
	}

	private DominioDetalle obtenerGrupoBiologico() {
		for (DominioDetalle grupo : listaGruposBiologicos) {
			if (grupo.getIdentificador().getTipo().equals(grupoBio)) {
				return grupo;
			}
		}
		return null;
	}

	private DominioDetalle obtenerSubGrupoBiologico() {
		for (DominioDetalle subgrupo : listaSubGruposBiologicos) {
			if (subgrupo.getIdentificador().getTipo().equals(subGrupoBio)) {
				return subgrupo;
			}
		}
		return null;
	}

	public void agregarCatalogacion() {
		
		DominioDetalle gru = obtenerGrupoBiologico();
		DominioDetalle subGru = obtenerSubGrupoBiologico();
		catalogacionColeccion = new ColeccionCatalogacion();
		if (gru != null) {
			if (subGru != null) {
				if (numeroEjemplares == 0L) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Debe indicar la cantidad de ejemplares.", ""));
					return;
				}

				Float totalPorcentaje = ejemplaresIdentificadosFilum + ejemplaresIdentificadosFamilia
						+ ejemplaresIdentificadosGenero + ejemplaresIdentificadosOrden + ejemplaresIdentificadosEspecie;
				if (totalPorcentaje > 100) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"La suma del porcentaje de ejemplares identificados solamente a nivel de Filum, Familia, Género, Orden y Especie no debe superar el 100%.",
							""));
					return;
				}

				if (coleccion.getListaCatalogacionColeccionCompleta().isEmpty()) {
					catalogacionColeccion.setGrupoBiologico(gru);
					catalogacionColeccion.setSubGrupoBiologico(subGru);
					catalogacionColeccion.setNumeroEjemplares(numeroEjemplares);
					catalogacionColeccion.setEjemplaresCatalogados(ejemplaresCatalogados);
					catalogacionColeccion.setEjemplaresSistematizados(ejemplaresSistematizados);
					catalogacionColeccion.setEjemplaresIdentificadosFilum(ejemplaresIdentificadosFilum);
					catalogacionColeccion.setEjemplaresIdentificadosFamilia(ejemplaresIdentificadosFamilia);
					catalogacionColeccion.setEjemplaresIdentificadosGenero(ejemplaresIdentificadosGenero);
					catalogacionColeccion.setEjemplaresIdentificadosOrden(ejemplaresIdentificadosOrden);
					catalogacionColeccion.setEjemplaresIdentificadosEspecie(ejemplaresIdentificadosEspecie);
					coleccion.adicionarCatalogacion(catalogacionColeccion);
				} else {
					Boolean repetido = false;
					for (ColeccionCatalogacion a : coleccion.getListaCatalogacionColeccionCompleta()) {
						if (a.getGrupoBiologico().getIdentificador().getTipo().equals(grupoBio)
								&& a.getSubGrupoBiologico().getIdentificador().getTipo().equals(subGrupoBio)) {
							repetido = true;
							break;
						}
					}
					if (repetido) {
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Ya ha ingresado información para el tipo y subtipo biológico seleccionado.", ""));
					} else {
						catalogacionColeccion.setGrupoBiologico(gru);
						catalogacionColeccion.setSubGrupoBiologico(subGru);
						catalogacionColeccion.setNumeroEjemplares(numeroEjemplares);
						catalogacionColeccion.setEjemplaresCatalogados(ejemplaresCatalogados);
						catalogacionColeccion.setEjemplaresSistematizados(ejemplaresSistematizados);
						catalogacionColeccion.setEjemplaresIdentificadosFilum(ejemplaresIdentificadosFilum);
						catalogacionColeccion.setEjemplaresIdentificadosFamilia(ejemplaresIdentificadosFamilia);
						catalogacionColeccion.setEjemplaresIdentificadosGenero(ejemplaresIdentificadosGenero);
						catalogacionColeccion.setEjemplaresIdentificadosOrden(ejemplaresIdentificadosOrden);
						catalogacionColeccion.setEjemplaresIdentificadosEspecie(ejemplaresIdentificadosEspecie);
						coleccion.adicionarCatalogacion(catalogacionColeccion);
					}
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Subgrupo biológico", "Debe seleccionar un subgrupo biológico!"));
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Grupo biológico", "Debe seleccionar un grupo biológico!"));
		}
		
	}
	
	public void agregarCatalogacionNoBiologica() {
		
			if(esCadenaVacia(grupoNoBiologico)) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Grupo", "Debe indicar un grupo para los ejemplares u objetos de la colección!"));
				return;
			}else if(esCadenaVacia(subGrupoNoBiologico)) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Subgrupo", "Debe indicar un subgrupo para los ejemplares u objetos de la colección!"));
				return;
			}else if(esCadenaVacia(unidadMedidaNoBiologico)) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Unidad de Medida", "Debe indicar una unidad de medida para los ejemplares u objetos de la colección!"));
				return;
			}else if (numeroEjemplaresNoBiologico == 0L) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Debe indicar la cantidad de ejemplares u objetos.", ""));
				return;
			}else {
				catalogacionColeccion.setGrupoNoBiologico(grupoNoBiologico);
				catalogacionColeccion.setSubGrupoNoBiologico(subGrupoNoBiologico);
				catalogacionColeccion.setNumeroEjemplares(numeroEjemplaresNoBiologico);
				catalogacionColeccion.setUnidadMedidaNoBiologico(unidadMedidaNoBiologico);
				catalogacionColeccion.setEjemplaresCatalogados(ejemplaresCatalogadosNoBiologico);
				catalogacionColeccion.setEjemplaresSistematizados(ejemplaresSistematizadosNoBiologico);
				coleccion.adicionarCatalogacion(catalogacionColeccion);
			}
	}


	public void eliminarCatalogacion() {
		coleccion.borrarCatalogacion(catalogc);
		if (catalogc.getId() != null) {
			servicioGeneral.eliminarObjeto(catalogc);
		}
	}

	public void guardarArchivo(FileUploadEvent event) {
		archivoCargar = event.getFile();
		ArchivoColeccion ai = insertarArchivoColeccionGenerico(0, archivoCargar, "CU");
		listaArchivos.add(ai);
	}

	public void eliminarArchivo() {
		listaArchivos.remove(archivoSeleccionado);
		servicioGeneral.eliminarObjeto(archivoSeleccionado);
		eliminarArchivoColeccionGenerico(archivoSeleccionado.getId());
	}

	public void verArchivo() {
		descargarArchivoColeccionGenerico(archivoSeleccionado);
	}

	public CroppedImage getCroppedImage() {
		return croppedImage;
	}

	public void setCroppedImage(CroppedImage croppedImage) {
		this.croppedImage = croppedImage;
	}

	public void cerrar() {
		banderaUno = false;
	}

	public String guardar() {

		validaciones = false;
		if ("".equals(coleccion.getNombre())) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Nombre: Debe ingresar un nombre para la Colección en la sección 'Información Básica'", ""));
			validaciones = true;
		}
		if ("".equals(coleccion.getTipo())) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Tipo de Colección: Debe seleccionar un tipo de Colección en la sección 'Información Básica'", ""));
			validaciones = true;
		}
		if (coleccion.getSede().getId() == null
				|| (coleccion.getSede().getId() != null && coleccion.getSede().getId() == 0)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Sede: Debe seleccionar la sede a la que corresponde la Colección en la sección 'Información Básica'",
					""));
			validaciones = true;
		}

		if (coleccion.getDependencia().getId() == null
				|| (coleccion.getDependencia().getId() != null && "".equals(coleccion.getDependencia().getId()))) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Dependencia: Debe seleccionar la dependencia a la que corresponde la Colección en la sección 'Información Básica'",
					""));
			validaciones = true;
		}
		if (coleccion.getCiudad().getId() == null
				|| (coleccion.getCiudad().getId() != null && "".equals(coleccion.getCiudad().getId()))) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Ciudad: Debe seleccionar la ciudad a la que corresponde la Colección en la sección 'Información Básica'",
					""));
			validaciones = true;
		}
		if (coleccion.getCuradorGeneral() == null) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage("Curador general: Debe indicar el curador general de la colección.", ""));
			validaciones = true;
		}
		
		if(coleccion.getTipo().equals("0")) {
			if(esCadenaVacia(coleccion.getNombreOtroTipo())){
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null,
						new FacesMessage("Tipo de Colección: Debe indicar el nombre para otro tipo de colección.", ""));
				validaciones = true;
			}
		}

		if (!validaciones) {
			guardarParcialmente = false;
			return revisarDatosGuardarColeccion();

		}
		return null;
	}
	
	public String revisarDatosGuardarColeccion() {
		
		if (coleccion.getDescripcion() != null && coleccion.getDescripcion().length() > 2899) {
			this.coleccion.setDescripcion(cortarCadena(coleccion.getDescripcion(), 2899));
		}

		if (coleccion.getInfraestructura() != null && coleccion.getInfraestructura().length() > 2899) {
			this.coleccion.setInfraestructura(cortarCadena(coleccion.getInfraestructura(), 2899));
		}

		if (coleccion.getCoberturaGeografica() != null && coleccion.getCoberturaGeografica().length() > 2899) {
			this.coleccion.setCoberturaGeografica(cortarCadena(coleccion.getCoberturaGeografica(), 2899));
		}

		if (coleccion.getCoberturaTemporal() != null && coleccion.getCoberturaTemporal().length() > 1699) {
			this.coleccion.setCoberturaTemporal(cortarCadena(coleccion.getCoberturaTemporal(), 1699));
		}

		if (coleccion.getCoberturaTaxonomica() != null && coleccion.getCoberturaTaxonomica().length() > 2899) {
			this.coleccion.setCoberturaTaxonomica(cortarCadena(coleccion.getCoberturaTaxonomica(), 2899));
		}

		if (coleccion.getSistematizacionPublicacion() != null
				&& coleccion.getSistematizacionPublicacion().length() > 699) {
			this.coleccion
					.setSistematizacionPublicacion(cortarCadena(coleccion.getSistematizacionPublicacion(), 699));
		}

		if (coleccion.getDireccion() != null && coleccion.getDireccion().length() > 240) {
			this.coleccion.setDireccion(cortarCadena(coleccion.getDireccion(), 240));
		}

		if (coleccion.getTelefono() != null && coleccion.getTelefono().length() > 95) {
			this.coleccion.setTelefono(cortarCadena(coleccion.getTelefono(), 95));
		}

		if (coleccion.getSitioWeb() != null && coleccion.getSitioWeb().length() > 495) {
			this.coleccion.setSitioWeb(cortarCadena(coleccion.getSitioWeb(), 495));
		}

		if (coleccion.getAcronimo() != null && coleccion.getAcronimo().length() > 98) {
			this.coleccion.setAcronimo(cortarCadena(coleccion.getAcronimo(), 98));
		}

		if (coleccion.getBaseDatos() != null && coleccion.getBaseDatos().length() > 198) {
			this.coleccion.setBaseDatos(cortarCadena(coleccion.getBaseDatos(), 198));
		}

		if (coleccion.getEmail() != null && coleccion.getEmail().length() > 148) {
			this.coleccion.setEmail(cortarCadena(coleccion.getEmail(), 148));
		}

		if (!coleccion.getCuradorGeneral().getId().getDocumento().equals(personaActual.getId().getDocumento())) {

			boolean tieneRol = false;
			List roles = servicioPersona.obtenerRols(coleccion.getCuradorGeneral().getId());
			if (!esListaVacia(roles)) {
				for (int i = 0; i < roles.size(); i++) {
					Rol rol = (Rol) roles.get(i);
					String idRol = rol.getId();
					if ("CU".equals(idRol)) {
						tieneRol = true;
						break;
					}
				}
			}

			if (!tieneRol) {
				servicioGeneral.ejecutarSentencia("INSERT INTO HER_PERSONA_ROL (TDO_ID, PER_ID ,ROL_ID) VALUES ('"
						+ coleccion.getCuradorGeneral().getId().getTipoDocumento() + "','"
						+ coleccion.getCuradorGeneral().getId().getDocumento() + "','CU')");
			}

			List listaColecciones = servicioGeneral.obtenerObjetos(
					"from Coleccion c, ColeccionPersona cp where c.estado != 'B' and cp.persona.id.tipoDocumento = '"
							+ personaActual.getId().getTipoDocumento() + "' AND cp.persona.id.documento = '"
							+ personaActual.getId().getDocumento() + "' and cp.tipoPersona = 'CU1'");
			if (listaColecciones != null && listaColecciones.size() == 1) {
				servicioGeneral.ejecutarSentencia("DELETE FROM HER_PERSONA_ROL WHERE ROL_ID = 'CU' AND TDO_ID = '"
						+ personaActual.getId().getTipoDocumento() + "' AND PER_ID = '"
						+ personaActual.getId().getDocumento() + "'");
			}
			if (coleccion.getHistoricoCuradores() == null) {
				coleccion.setHistoricoCuradores("");
			}
			coleccion.setHistoricoCuradores(coleccion.getHistoricoCuradores()
					+ personaActual.getId().getTipoDocumento() + "-" + personaActual.getId().getDocumento() + "->"
					+ coleccion.getCuradorGeneral().getId().getTipoDocumento() + "-"
					+ coleccion.getCuradorGeneral().getId().getDocumento() + "--");
		}

		coleccion.setDescripcion(modificarCaracteres(coleccion.getDescripcion()));
		coleccion.setDireccion(modificarCaracteres(coleccion.getDireccion()));
		coleccion.setTelefono(modificarCaracteres(coleccion.getTelefono()));
		coleccion.setAcronimo(modificarCaracteres(coleccion.getAcronimo()));
		coleccion.setCoberturaGeografica(modificarCaracteres(coleccion.getCoberturaGeografica()));
		coleccion.setCoberturaTaxonomica(modificarCaracteres(coleccion.getCoberturaTaxonomica()));
		coleccion.setCoberturaTemporal(modificarCaracteres(coleccion.getCoberturaTemporal()));
		coleccion.setInfraestructura(modificarCaracteres(coleccion.getInfraestructura()));
		coleccion.setSistematizacionPublicacion(modificarCaracteres(coleccion.getSistematizacionPublicacion()));
		coleccion.setBaseDatos(modificarCaracteres(coleccion.getBaseDatos()));
		coleccion.setEmail(modificarCaracteres(coleccion.getEmail()));

		coleccion.setFechaActualizacion(new Date());
		
		if(guardarParcialmente) {
			if(esCadenaVacia(coleccion.getEstado())) {
				coleccion.setEstado(Coleccion.INGRESANDO);
			}
		}else {
			coleccion.setEstado(Coleccion.PROPUESTA);
			guardarHistorico("0");
		}

		if (terminaEdicion && !guardarParcialmente) {
			coleccion.setEstado(Coleccion.ENVIADA_VRI);
			guardarHistorico("0");
			
			try {
				
				ColeccionGestion gestion = new ColeccionGestion();
				DominioDetalle estado = new DominioDetalle();
				IdDominioDetalle iddde = new IdDominioDetalle();
				iddde.setId("323");// Dominio de estados
				iddde.setTipo(ColeccionGestion.ESTADO_ENVIADA); 
				estado.setIdentificador(iddde);
				
				if(esListaVacia(coleccion.getListaGestiones())){
					
					gestion.setPersonaRegistra(getPersonaActual());
					DominioDetalle tipo = new DominioDetalle();
					IdDominioDetalle iddd = new IdDominioDetalle();
					iddd.setId(Dominio.ID_DOMINIO_TIPO_GESTION_COL);// Solicitude Registro UNAL
					iddd.setTipo("RCUN"); 
					tipo.setIdentificador(iddd);
					gestion.setTipo(tipo);
					gestion.setEstado(estado);
					gestion.setFechaRegistro(getToday());
					gestion.setIdColeccion(coleccion.getId());
					gestion.setJustificacion("Finalización de registro de información de la colección por parte del director");
					servicioGeneral.guardarObjeto(gestion);

					
				}else {
					for(int i=0; i<coleccion.getListaGestiones().size(); i++) {
						if(coleccion.getListaGestiones().get(i).getTipo().getIdentificador().getTipo().equals("RCUN") && 
								coleccion.getListaGestiones().get(i).getEstado().getIdentificador().getTipo().equals(ColeccionGestion.ESTADO_DEVUELTA)) {
							gestion = coleccion.getListaGestiones().get(i);
							gestion.setEstado(estado);
							gestion.setJustificacion("Nuevo envio solicitud de registro de información de la colección por parte del director");
							servicioGeneral.guardarObjeto(gestion);
						}
					}
				}
				
				
				
				String consulta = "select d from Departamento d where d.id like 'CO%' " + "order by d.nombre";
				List<Departamento> listaDepartament = servicioGeneral.obtenerObjetos(Departamento.class, consulta);

				if (!esListaVacia(listaDepartament)) {
					listaDepartamentosItem = new SelectItem[listaDepartament.size() + 1];
					listaDepartamentosItem[0] = new SelectItem("", "Seleccione departamento");
					for (int i = 1; i <= listaDepartament.size(); i++) {
						Departamento deptoCol = (Departamento) listaDepartament.get(i - 1);
						listaDepartamentosItem[i] = new SelectItem(deptoCol.getId(), deptoCol.getNombre());
					}
				}
				
				
				
			}catch (Exception e) {
				
			}

			
		}

		boolean guardo;
		guardo = servicioGeneral.ingresarColeccion(coleccion);
		coleccion.setImagen(coleccion.getId().toString());
		guardo = servicioGeneral.ingresarColeccion(coleccion);

		for (ColeccionPersona personaEliminar : listaPersonalEliminado) {
			servicioGeneral.eliminarObjeto(personaEliminar);
		}

		for (ColeccionTipoPreservacion region : coleccion.getListaTiposPreservacion()) {
			servicioGeneral.guardarObjeto(region);
		}

		for (ColeccionNomenclatura nomenclatura : coleccion.getTiposNomenclaturales()) {
			servicioGeneral.guardarObjeto(nomenclatura);
		}

		for (ColeccionCatalogacion catalog : coleccion.getListaCatalogacionColeccionCompleta()) {
			servicioGeneral.guardarObjeto(catalog);
		}

		for (ColeccionPersona persona : coleccion.getListaPersonal()) {
			servicioGeneral.guardarObjeto(persona);
		}
		
		for (ColeccionClasificacion clasif : coleccion.getListaClasificacion()) {
			servicioGeneral.guardarObjeto(clasif);
		}
		
		for (ColeccionTipoObjeto tipoObj : coleccion.getListaTiposObjetos()) {
			servicioGeneral.guardarObjeto(tipoObj);
		}

		for (ArchivoColeccion arch : listaArchivos) {
			arch.setColeccion(coleccion);
			servicioGeneral.guardarObjeto(arch);
		}

		if (banderaDos && coleccion.getId() != null) {

			// Original file
			File dataInputFile = new File(
					servletContext.getRealPath("") + File.separator + CARPETA_TEMPORAL_IMAGENES + File.separator
							+ "colecciones" + File.separator + nombreImagen + ".jpg");
			// New path
			File fileSendPath = new File(path + coleccion.getId() + ".jpg");

			// Moving the file.
			if (fileSendPath.exists()) {
				fileSendPath.delete();
			}
			dataInputFile.renameTo(fileSendPath);
		}
		if (coleccion.getId() != null && guardo) {

			if (terminaEdicion && !guardarParcialmente) {
				Correo correo = new Correo();
				CorreoPlantilla cp = cargarPlantilla(255);
				correo.setOrigen(Correo.CORREO_HERMES_SOLICITUDES);
				correo.setAsunto(cp.getAsunto());
				correo.setCuerpo(cp.getCuerpo().replaceAll("<<NOMBRECOL>>", coleccion.getNombre())
						.replaceAll("<<CURADOR>>", coleccion.getCuradorGeneral().getNombreCompletoMinusculas()));
				// correo.adicionarCopiaOculta(Correo.CORREO_HERMES_SOLICITUDES);

				List<Parametro> listaParametro = this.servicioGeneral.obtenerListaObjetosWhere(Parametro.class,
						"WHERE p.nombre = 'COORD_BIODIVERSIDAD' and (p.fechaFinal > current_date OR p.fechaFinal IS NULL)");
				for (int i = 0; i < listaParametro.size(); i++) {
					Parametro par = listaParametro.get(i);
					IdPersona id = new IdPersona();
					id.setDocumento(par.getValor());
					if (par.getProfesion() != null) {
						id.setTipoDocumento(par.getProfesion());
					} else {
						id.setTipoDocumento("C");
					}
					Persona persona = servicioPersona.obtenerPersona(id);
					correo.adicionarDireccion(persona.getEmail());
				}
				servicioCorreo.enviarCorreo(correo);
			}

			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Registro Satisfactorio",
					"Se ha guardado satisfactoriamente la información de la Colección"));

		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Registro no Satisfactorio",
					"Ocurrió un problema mientras se guardaba la Hoja de Vida de la Colección"));
		}
		return "";
	}
	
	public String guardarBasico() {
		guardarHistorico("1");
		return guardarParcialmente();
	}
	
	public String guardarClasificacion() {
		guardarHistorico("2");
		return guardarParcialmente();
	}
	
	public String guardarCobertura() {
		guardarHistorico("3");
		return guardarParcialmente();
	}
	
	public String guardarPreservacion() {
		guardarHistorico("4");
		return guardarParcialmente();
	}
	
	public String guardarTipo() {
		guardarHistorico("5");
		return guardarParcialmente();
	}
	
	public String guardarCatalogacion() {
		guardarHistorico("6");
		return guardarParcialmente();
	}
	
	public String guardarPersonal() {
		guardarHistorico("7");
		return guardarParcialmente();
	}
	
	public String guardarDocumentos() {
		guardarHistorico("8");
		return guardarParcialmente();
	}
	
	
	public void guardarHistorico(String seccionFormulario) {
		HistoricoColeccion historicoFormulario = new HistoricoColeccion();
		historicoFormulario.setResponsable(personaActual);
		historicoFormulario.setColeccion(coleccion);
		historicoFormulario.setSeccionFormulario(seccionFormulario);
		historicoFormulario.setFecha(new Date());
		historicoFormulario.setEstado(coleccion.getEstado());
		servicioGeneral.guardarObjeto(historicoFormulario);
		enviarCorreoModificacionColeccion();
	}
	
	public void enviarCorreoModificacionColeccion() {
		Correo correo = new Correo();
		CorreoPlantilla cp = cargarPlantilla(410);
		correo.setOrigen(Correo.CORREO_HERMES_SOLICITUDES);
		correo.setAsunto(cp.getAsunto());
		correo.setCuerpo(cp.getCuerpo().replaceAll("<<COLECCION>>", coleccion.getNombre())
				.replaceAll("<<INVESTIGADOR>>", coleccion.getCuradorGeneral().getNombreCompletoMinusculas()));
		// correo.adicionarCopiaOculta(Correo.CORREO_HERMES_SOLICITUDES);

		List<Parametro> listaParametro = this.servicioGeneral.obtenerListaObjetosWhere(Parametro.class,
				"WHERE p.nombre = 'COORD_BIODIVERSIDAD' and (p.fechaFinal > current_date OR p.fechaFinal IS NULL)");
		for (int i = 0; i < listaParametro.size(); i++) {
			Parametro par = listaParametro.get(i);
			IdPersona id = new IdPersona();
			id.setDocumento(par.getValor());
			if (par.getProfesion() != null) {
				id.setTipoDocumento(par.getProfesion());
			} else {
				id.setTipoDocumento("C");
			}
			Persona persona = servicioPersona.obtenerPersona(id);
			correo.adicionarDireccion(persona.getEmail());
		}
		servicioCorreo.enviarCorreo(correo);
	}
	
	public String guardarParcialmente() {
		if (esCadenaVacia(coleccion.getNombre())) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Nombre: Debe ingresar un nombre para la Colección en la sección 'Información Básica'", ""));
			return null;
		}else {
			guardarParcialmente = true;
			return revisarDatosGuardarColeccion();
		}
	}

	public String modificarCaracteres(String cadena) {

		String sinCaracteresEspeciales = "";
		if(!esCadenaVacia(cadena)) {
			sinCaracteresEspeciales = cadena.replaceAll("–", "-");
			sinCaracteresEspeciales = sinCaracteresEspeciales.replaceAll("“", "\"");
			sinCaracteresEspeciales = sinCaracteresEspeciales.replaceAll("”", "\"");
		}
		return sinCaracteresEspeciales;

	}

	public String volver() {
		sesion.removeAttribute("manejadorColeccionesBiologicas");
		return "colecciones";
	}

	public void crop() throws ImageFormatException {

		String newFilePathDestino = servletContext.getRealPath("") + File.separator + "images" + File.separator
				+ "colecciones" + File.separator + nombreImagen + ".jpg";

		banderaDos = recortarImagen(croppedImage, newFilePathDestino, servletContext);

		if (!banderaDos) {
			mensajeError("Ha ocurrido un problema en la edición de la imagen cargada.");
		}

	}

	public void subirArchivo(FileUploadEvent event) {

		UploadedFile archivoSubir = event.getFile();
		String extension = obtenerExtensionArchivo(archivoSubir.getFileName());
		newImageNameActual = Util.getRandomImageName() + extension;
		String rutaArchivoTemporal = servletContext.getRealPath("") + File.separator + "images" + File.separator
				+ "colecciones" + File.separator + newImageNameActual;

		int tamanioMinimo = 210;
		int tamanioEscala = 500;
		int tamanioMaximoLado = 1000;

		banderaUno = cargarImagenDisco(archivoSubir, servletContext, rutaArchivoTemporal, tamanioMinimo, tamanioEscala,
				tamanioMaximoLado);

	}

	public String getNewImageName() {
		return newImageName;
	}

	public void setNewImageName(String newImageName) {
		this.newImageName = newImageName;
	}

	public String getNewFileName() {
		return newFileName;
	}

	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}

	public String getNewImageNameActual() {
		return newImageNameActual;
	}

	public void setNewImageNameActual(String newImageNameActual) {
		this.newImageNameActual = newImageNameActual;
	}

	public boolean isBandera() {
		return bandera;
	}

	public void setBandera(boolean bandera) {
		this.bandera = bandera;
	}

	public boolean isBanderaUno() {
		return banderaUno;
	}

	public void setBanderaUno(boolean banderaUno) {
		this.banderaUno = banderaUno;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isBanderaDos() {
		return banderaDos;
	}

	public void setBanderaDos(boolean banderaDos) {
		this.banderaDos = banderaDos;
	}

	public StreamedContent getImagen() {
		try {
			imagen = new DefaultStreamedContent(
					new ByteArrayInputStream(org.apache.commons.io.FileUtils.readFileToByteArray(actual)), "image/png");
		} catch (IOException e) {
			// TODO
		}
		return imagen;
	}

	public void setImagen(StreamedContent imagen) {
		this.imagen = imagen;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public void setColeccion(Coleccion coleccion) {
		this.coleccion = coleccion;
	}

	public Coleccion getColeccion() {
		return coleccion;
	}

	public void setListaSedesItem(SelectItem[] listaSedesItem) {
		this.listaSedesItem = listaSedesItem;
	}

	public SelectItem[] getListaSedesItem() {
		return listaSedesItem;
	}

	public void setListaTiposItem(SelectItem[] listaTiposItem) {
		this.listaTiposItem = listaTiposItem;
	}

	public SelectItem[] getListaTiposItem() {
		return listaTiposItem;
	}

	public void setListaDependenciasItem(SelectItem[] listaDependenciasItem) {
		this.listaDependenciasItem = listaDependenciasItem;
	}

	public SelectItem[] getListaDependenciasItem() {
		return listaDependenciasItem;
	}

	public void setListaDepartamentosItem(SelectItem[] listaDepartamentosItem) {
		this.listaDepartamentosItem = listaDepartamentosItem;
	}

	public SelectItem[] getListaDepartamentosItem() {
		return listaDepartamentosItem;
	}

	public void setListaCiudadesItem(SelectItem[] listaCiudadesItem) {
		this.listaCiudadesItem = listaCiudadesItem;
	}

	public SelectItem[] getListaCiudadesItem() {
		return listaCiudadesItem;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setListaTiposDocItem(SelectItem[] listaTiposDocItem) {
		this.listaTiposDocItem = listaTiposDocItem;
	}

	public SelectItem[] getListaTiposDocItem() {
		return listaTiposDocItem;
	}

	public void setCuradorColeccion(Persona curadorColeccion) {
		this.curadorColeccion = curadorColeccion;
	}

	public Persona getCuradorColeccion() {
		return curadorColeccion;
	}

	public void setListaTipoPerItem(SelectItem[] listaTipoPerItem) {
		this.listaTipoPerItem = listaTipoPerItem;
	}

	public SelectItem[] getListaTipoPerItem() {
		return listaTipoPerItem;
	}

	public ColeccionPersona getPersonalCol() {
		return personalCol;
	}

	public void setPersonalCol(ColeccionPersona personalCol) {
		this.personalCol = personalCol;
	}

	public void setNomenclaturaColeccion(ColeccionNomenclatura nomenclaturaColeccion) {
		this.nomenclaturaColeccion = nomenclaturaColeccion;
	}

	public ColeccionNomenclatura getNomenclaturaColeccion() {
		return nomenclaturaColeccion;
	}

	public void setListaTiposNomenclaturales(List<DominioDetalle> listaTiposNomenclaturales) {
		this.listaTiposNomenclaturales = listaTiposNomenclaturales;
	}

	public List<DominioDetalle> getListaTiposNomenclaturales() {
		return listaTiposNomenclaturales;
	}

	public void setNombreImagen(String nombreImagen) {
		this.nombreImagen = nombreImagen;
	}

	public String getNombreImagen() {
		return nombreImagen;
	}

	public SelectItem[] getListaNomenclaturasItem() {
		return listaNomenclaturasItem;
	}

	public void setListaNomenclaturasItem(SelectItem[] listaNomenclaturasItem) {
		this.listaNomenclaturasItem = listaNomenclaturasItem;
	}

	public void setNomenc(ColeccionNomenclatura nomenc) {
		this.nomenc = nomenc;
	}

	public ColeccionNomenclatura getNomenc() {
		return nomenc;
	}

	public void setPersonaSeleccionada(ColeccionPersona personaSeleccionada) {
		this.personaSeleccionada = personaSeleccionada;
	}

	public ColeccionPersona getPersonaSeleccionada() {
		return personaSeleccionada;
	}

	public List<ColeccionPersona> getListaPersonalEliminado() {
		return listaPersonalEliminado;
	}

	public void setListaPersonalEliminado(List<ColeccionPersona> listaPersonalEliminado) {
		this.listaPersonalEliminado = listaPersonalEliminado;
	}

	public List<ColeccionNomenclatura> getListaTiposEliminados() {
		return listaTiposEliminados;
	}

	public void setListaTiposEliminados(List<ColeccionNomenclatura> listaTiposEliminados) {
		this.listaTiposEliminados = listaTiposEliminados;
	}

	public void setTipoSeleccionada(DominioDetalle tipoSeleccionada) {
		this.tipoSeleccionada = tipoSeleccionada;
	}

	public DominioDetalle getTipoSeleccionada() {
		return tipoSeleccionada;
	}

	public void setRegionSeleccionada(DominioDetalle regionSeleccionada) {
		this.regionSeleccionada = regionSeleccionada;
	}

	public DominioDetalle getRegionSeleccionada() {
		return regionSeleccionada;
	}

	public SelectItem[] getDependenciaItemCompleta() {
		return dependenciaItemCompleta;
	}

	public void setDependenciaItemCompleta(SelectItem[] dependenciaItemCompleta) {
		this.dependenciaItemCompleta = dependenciaItemCompleta;
	}

	public List<ColeccionTipoPreservacion> getListaTiposPreservacionColeccion() {
		return listaTiposPreservacionColeccion;
	}

	public void setListaTiposPreservacionColeccion(List<ColeccionTipoPreservacion> listaTiposPreservacionColeccion) {
		this.listaTiposPreservacionColeccion = listaTiposPreservacionColeccion;
	}

	public ColeccionTipoPreservacion getTipoPreservacionColeccion() {
		return tipoPreservacionColeccion;
	}

	public void setTipoPreservacionColeccion(ColeccionTipoPreservacion tipoPreservacionColeccion) {
		this.tipoPreservacionColeccion = tipoPreservacionColeccion;
	}

	public SelectItem[] getListaPreservacionItem() {
		return listaPreservacionItem;
	}

	public void setListaPreservacionItem(SelectItem[] listaPreservacionItem) {
		this.listaPreservacionItem = listaPreservacionItem;
	}

	public ColeccionTipoPreservacion getRegc() {
		return regc;
	}

	public void setRegc(ColeccionTipoPreservacion regc) {
		this.regc = regc;
	}

	public boolean isTipos() {
		return tipos;
	}

	public void setTipos(boolean tipos) {
		this.tipos = tipos;
	}

	public List<DominioDetalle> getListaGruposBiologicos() {
		return listaGruposBiologicos;
	}

	public void setListaGruposBiologicos(List<DominioDetalle> listaGruposBiologicos) {
		this.listaGruposBiologicos = listaGruposBiologicos;
	}

	public SelectItem[] getListaGruposBiologicosItem() {
		return listaGruposBiologicosItem;
	}

	public void setListaGruposBiologicosItem(SelectItem[] listaGruposBiologicosItem) {
		this.listaGruposBiologicosItem = listaGruposBiologicosItem;
	}

	public ColeccionCatalogacion getCatalogacionColeccion() {
		return catalogacionColeccion;
	}

	public void setCatalogacionColeccion(ColeccionCatalogacion catalogacionColeccion) {
		this.catalogacionColeccion = catalogacionColeccion;
	}

	public List<DominioDetalle> getListaSubGruposBiologicos() {
		return listaSubGruposBiologicos;
	}

	public void setListaSubGruposBiologicos(List<DominioDetalle> listaSubGruposBiologicos) {
		this.listaSubGruposBiologicos = listaSubGruposBiologicos;
	}

	public SelectItem[] getListaSubGruposBiologicosItem() {
		return listaSubGruposBiologicosItem;
	}

	public void setListaSubGruposBiologicosItem(SelectItem[] listaSubGruposBiologicosItem) {
		this.listaSubGruposBiologicosItem = listaSubGruposBiologicosItem;
	}

	public String getGrupoBio() {
		return grupoBio;
	}

	public void setGrupoBio(String grupoBio) {
		this.grupoBio = grupoBio;
	}

	public String getSubGrupoBio() {
		return subGrupoBio;
	}

	public void setSubGrupoBio(String subGrupoBio) {
		this.subGrupoBio = subGrupoBio;
	}

	public long getNumeroEjemplares() {
		return numeroEjemplares;
	}

	public void setNumeroEjemplares(long numeroEjemplares) {
		this.numeroEjemplares = numeroEjemplares;
	}

	public float getEjemplaresCatalogados() {
		return ejemplaresCatalogados;
	}

	public void setEjemplaresCatalogados(float ejemplaresCatalogados) {
		this.ejemplaresCatalogados = ejemplaresCatalogados;
	}

	public float getEjemplaresSistematizados() {
		return ejemplaresSistematizados;
	}

	public void setEjemplaresSistematizados(float ejemplaresSistematizados) {
		this.ejemplaresSistematizados = ejemplaresSistematizados;
	}

	public float getEjemplaresIdentificadosOrden() {
		return ejemplaresIdentificadosOrden;
	}

	public void setEjemplaresIdentificadosOrden(float ejemplaresIdentificadosOrden) {
		this.ejemplaresIdentificadosOrden = ejemplaresIdentificadosOrden;
	}

	public float getEjemplaresIdentificadosFamilia() {
		return ejemplaresIdentificadosFamilia;
	}

	public void setEjemplaresIdentificadosFamilia(float ejemplaresIdentificadosFamilia) {
		this.ejemplaresIdentificadosFamilia = ejemplaresIdentificadosFamilia;
	}

	public float getEjemplaresIdentificadosGenero() {
		return ejemplaresIdentificadosGenero;
	}

	public void setEjemplaresIdentificadosGenero(float ejemplaresIdentificadosGenero) {
		this.ejemplaresIdentificadosGenero = ejemplaresIdentificadosGenero;
	}

	public float getEjemplaresIdentificadosEspecie() {
		return ejemplaresIdentificadosEspecie;
	}

	public void setEjemplaresIdentificadosEspecie(float ejemplaresIdentificadosEspecie) {
		this.ejemplaresIdentificadosEspecie = ejemplaresIdentificadosEspecie;
	}

	public ColeccionCatalogacion getCatalogc() {
		return catalogc;
	}

	public void setCatalogc(ColeccionCatalogacion catalogc) {
		this.catalogc = catalogc;
	}

	public ArchivoColeccion getArchivoSeleccionado() {
		return archivoSeleccionado;
	}

	public void setArchivoSeleccionado(ArchivoColeccion archivoSeleccionado) {
		this.archivoSeleccionado = archivoSeleccionado;
	}

	public List<ArchivoColeccion> getListaArchivos() {
		return listaArchivos;
	}

	public void setListaArchivos(List<ArchivoColeccion> listaArchivos) {
		this.listaArchivos = listaArchivos;
	}

	public UploadedFile getArchivoCargar() {
		return archivoCargar;
	}

	public void setArchivoCargar(UploadedFile archivoCargar) {
		this.archivoCargar = archivoCargar;
	}

	public String getGrupoBioTipo() {
		return grupoBioTipo;
	}

	public void setGrupoBioTipo(String grupoBioTipo) {
		this.grupoBioTipo = grupoBioTipo;
	}

	public boolean isNuevaPersona() {
		return nuevaPersona;
	}

	public void setNuevaPersona(boolean nuevaPersona) {
		this.nuevaPersona = nuevaPersona;
	}

	public String getNombresExterno() {
		return nombresExterno;
	}

	public void setNombresExterno(String nombresExterno) {
		this.nombresExterno = nombresExterno;
	}

	public String getApellidosExterno() {
		return apellidosExterno;
	}

	public void setApellidosExterno(String apellidosExterno) {
		this.apellidosExterno = apellidosExterno;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getNombresExterno2() {
		return nombresExterno2;
	}

	public void setNombresExterno2(String nombresExterno2) {
		this.nombresExterno2 = nombresExterno2;
	}

	public String getApellidosExterno2() {
		return apellidosExterno2;
	}

	public void setApellidosExterno2(String apellidosExterno2) {
		this.apellidosExterno2 = apellidosExterno2;
	}

	public float getEjemplaresIdentificadosFilum() {
		return ejemplaresIdentificadosFilum;
	}

	public void setEjemplaresIdentificadosFilum(float ejemplaresIdentificadosFilum) {
		this.ejemplaresIdentificadosFilum = ejemplaresIdentificadosFilum;
	}

	public boolean isTerminaEdicion() {
		return terminaEdicion;
	}

	public void setTerminaEdicion(boolean terminaEdicion) {
		this.terminaEdicion = terminaEdicion;
	}

	public ColeccionPersona getCurador() {
		return curador;
	}

	public void setCurador(ColeccionPersona curador) {
		this.curador = curador;
	}

	public DominioDetalle getGrupoBiologicoCurador() {
		return grupoBiologicoCurador;
	}

	public void setGrupoBiologicoCurador(DominioDetalle grupoBiologicoCurador) {
		this.grupoBiologicoCurador = grupoBiologicoCurador;
	}

	public Persona getRepLegal() {
		return repLegal;
	}

	public void setRepLegal(Persona repLegal) {
		this.repLegal = repLegal;
	}

	public SelectItem[] getListaClasificacionItem() {
		return listaClasificacionItem;
	}

	public void setListaClasificacionItem(SelectItem[] listaClasificacionItem) {
		this.listaClasificacionItem = listaClasificacionItem;
	}

	public ColeccionClasificacion getColeccionClasificacion() {
		return coleccionClasificacion;
	}

	public void setColeccionClasificacion(ColeccionClasificacion coleccionClasificacion) {
		this.coleccionClasificacion = coleccionClasificacion;
	}

	public ColeccionClasificacion getClasificacionSeleccionada() {
		return clasificacionSeleccionada;
	}

	public void setClasificacionSeleccionada(ColeccionClasificacion clasificacionSeleccionada) {
		this.clasificacionSeleccionada = clasificacionSeleccionada;
	}

	public SelectItem[] getListaTipoObjetoColeccionItem() {
		return listaTipoObjetoColeccionItem;
	}

	public void setListaTipoObjetoColeccionItem(SelectItem[] listaTipoObjetoColeccionItem) {
		this.listaTipoObjetoColeccionItem = listaTipoObjetoColeccionItem;
	}

	public SelectItem[] getListaSubTipoObjetoColeccionItem() {
		return listaSubTipoObjetoColeccionItem;
	}

	public void setListaSubTipoObjetoColeccionItem(SelectItem[] listaSubTipoObjetoColeccionItem) {
		this.listaSubTipoObjetoColeccionItem = listaSubTipoObjetoColeccionItem;
	}

	public String getTipoObjetoColeccion() {
		return tipoObjetoColeccion;
	}

	public void setTipoObjetoColeccion(String tipoObjetoColeccion) {
		this.tipoObjetoColeccion = tipoObjetoColeccion;
	}

	public String getSubTipoObjetoColeccion() {
		return subTipoObjetoColeccion;
	}

	public void setSubTipoObjetoColeccion(String subTipoObjetoColeccion) {
		this.subTipoObjetoColeccion = subTipoObjetoColeccion;
	}

	public ColeccionTipoObjeto getColeccionTipoObjeto() {
		return coleccionTipoObjeto;
	}

	public void setColeccionTipoObjeto(ColeccionTipoObjeto coleccionTipoObjeto) {
		this.coleccionTipoObjeto = coleccionTipoObjeto;
	}

	public ColeccionTipoObjeto getColeccionTipoObjetoSeleccionado() {
		return coleccionTipoObjetoSeleccionado;
	}

	public void setColeccionTipoObjetoSeleccionado(ColeccionTipoObjeto coleccionTipoObjetoSeleccionado) {
		this.coleccionTipoObjetoSeleccionado = coleccionTipoObjetoSeleccionado;
	}

	public boolean isEsColeccionBiologica() {
		return esColeccionBiologica;
	}

	public void setEsColeccionBiologica(boolean esColeccionBiologica) {
		this.esColeccionBiologica = esColeccionBiologica;
	}

	public String getGrupoNoBiologico() {
		return grupoNoBiologico;
	}

	public void setGrupoNoBiologico(String grupoNoBiologico) {
		this.grupoNoBiologico = grupoNoBiologico;
	}

	public String getSubGrupoNoBiologico() {
		return subGrupoNoBiologico;
	}

	public void setSubGrupoNoBiologico(String subGrupoNoBiologico) {
		this.subGrupoNoBiologico = subGrupoNoBiologico;
	}

	public String getUnidadMedidaNoBiologico() {
		return unidadMedidaNoBiologico;
	}

	public void setUnidadMedidaNoBiologico(String unidadMedidaNoBiologico) {
		this.unidadMedidaNoBiologico = unidadMedidaNoBiologico;
	}

	public boolean isGuardarParcialmente() {
		return guardarParcialmente;
	}

	public void setGuardarParcialmente(boolean guardarParcialmente) {
		this.guardarParcialmente = guardarParcialmente;
	}

	public boolean isSoloConsulta() {
		return soloConsulta;
	}

	public void setSoloConsulta(boolean soloConsulta) {
		this.soloConsulta = soloConsulta;
	}

	public boolean isAceptaTerminos() {
		return aceptaTerminos;
	}

	public void setAceptaTerminos(boolean aceptaTerminos) {
		this.aceptaTerminos = aceptaTerminos;
	}

	public String getInstitucionLabora() {
		return institucionLabora;
	}

	public void setInstitucionLabora(String institucionLabora) {
		this.institucionLabora = institucionLabora;
	}



	public SelectItem[] getTipoFormacionItem() {
		return tipoFormacionItem;
	}



	public void setTipoFormacionItem(SelectItem[] tipoFormacionItem) {
		this.tipoFormacionItem = tipoFormacionItem;
	}



	public String getIdTipoFormacion() {
		return idTipoFormacion;
	}



	public void setIdTipoFormacion(String idTipoFormacion) {
		this.idTipoFormacion = idTipoFormacion;
	}



	public boolean isNoColeccionBiologica() {
		return noColeccionBiologica;
	}



	public void setNoColeccionBiologica(boolean noColeccionBiologica) {
		this.noColeccionBiologica = noColeccionBiologica;
	}



	public long getNumeroEjemplaresNoBiologico() {
		return numeroEjemplaresNoBiologico;
	}



	public void setNumeroEjemplaresNoBiologico(long numeroEjemplaresNoBiologico) {
		this.numeroEjemplaresNoBiologico = numeroEjemplaresNoBiologico;
	}



	public float getEjemplaresCatalogadosNoBiologico() {
		return ejemplaresCatalogadosNoBiologico;
	}



	public void setEjemplaresCatalogadosNoBiologico(float ejemplaresCatalogadosNoBiologico) {
		this.ejemplaresCatalogadosNoBiologico = ejemplaresCatalogadosNoBiologico;
	}



	public float getEjemplaresSistematizadosNoBiologico() {
		return ejemplaresSistematizadosNoBiologico;
	}



	public void setEjemplaresSistematizadosNoBiologico(float ejemplaresSistematizadosNoBiologico) {
		this.ejemplaresSistematizadosNoBiologico = ejemplaresSistematizadosNoBiologico;
	}

}
