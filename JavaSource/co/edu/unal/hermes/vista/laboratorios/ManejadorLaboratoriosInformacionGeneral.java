package co.edu.unal.hermes.vista.laboratorios;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.Campus;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.Edificio;
import co.edu.unal.hermes.modelo.IdDominioDetalle;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.LaboratorioODSSec;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.laboratorios.ArchivoLaboratorio;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioAreaOCDE;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioAreasSecundariasOCDE;
import sun.awt.image.ImageFormatException;

/**
 * @author dgbenitezc
 */
public class ManejadorLaboratoriosInformacionGeneral extends ManejadorLaboratorios {

	private static final long serialVersionUID = -4743455104036996546L;

	private Sede sedeActual;
	private SelectItem[] sedeItem;
	private List<Sede> listaSedes;
	private Dependencia facultadActual;
	private SelectItem[] facultadItem;
	private List<Dependencia> listaFacultades;
	private Dependencia departamentoActual;
	private SelectItem[] departamentoItem;
	private List<Dependencia> listaDepartamentos;
	// private List<Tipos> listaTipos;
	private SelectItem[] tipoLaboratorioItem;
	private SelectItem[] deptoCiudadItem;
	private SelectItem[] campusItem;
	private SelectItem[] portafolioServiciosItem;
	private SelectItem[] edificiosItem;
	private List<LaboratorioAreaOCDE> listaAreasOCDE;
	private SelectItem[] selectItemAreasOCDE;
	private String tipoAreaOCDESeleccionada;
	private LaboratorioAreaOCDE areaOCDEEliminar;
	private List<LaboratorioAreaOCDE> listaAreasOCDEEliminar;

	private SelectItem[] seletItemTipoSolicitud;

//	private SolicitudLaboratorios solicitudLab;

	private Tipos tipoArchivoSelActoCreacion;
	private Tipos tipoArchivoSelReglamento;
	private List<ArchivoLaboratorio> listaArchivosInfGral;
	private List<ArchivoLaboratorio> listaArchivosEliminadosInfGral;
	private ArchivoLaboratorio archivoLaboratorioSeleccionado;
	
	//METROLOGIA
	private String subredSeleccionada;
	private String capacitacionSeleccionada;
	private String actividadSeleccionada;
	private String muestreoSeleccionada;
	private String sistemaGestionSeleccionado;
	
	private SelectItem[] selectItemSubredes;
	private SelectItem[] selectItemCapacitaciones;
	private SelectItem[] selectItemActividades;
	private SelectItem[] selectItemMuestreos;
	private SelectItem[] selectItemSisGestion;
	
	private SelectItem[] listaSelectItemAreasPrincipales;
	private SelectItem[] listaSelectItemSubAreasPrincipales;
	private SelectItem[] listaSelectItemAreasSecundarias;
	private SelectItem[] listaSelectItemSubAreasSecundarias;
	private SelectItem[] listaSelectItemObjetivosSocieconomicos;
	
	private String areaOCDEPrincipalSel;
	private String subAreaOCDEPrincipalSel;
	private String areaOCDESecundariaSel;
	private String subAreaOCDESecundariaSel;
	private String objetivoSocioeconomicoSel;
	private String deptoCiudadSel;
	
	private ArrayList<LaboratorioAreasSecundariasOCDE> listaAreasSecundarias;
	private ArrayList<LaboratorioAreasSecundariasOCDE> listaAreasSecundariasEliminar;
	private LaboratorioAreasSecundariasOCDE areaSecundariaSeleccionadaTabla;
	
	private SelectItem[] listaSIReglamento;
	private List<ArchivoLaboratorio> listaArchivosReglamento;
	private List<ArchivoLaboratorio> listaArchivosEliminadosReglamento;
	private ArchivoLaboratorio archivoLaboratorioSeleccionadoReglamento;
	
	//ODS
	private SelectItem[] listaODS;
	private Tipos ODSSecundarioSel;
	private LaboratorioODSSec ODSSecundarioSelTabla;
	
	//Telefono 2
	private Persona coordinador;
	private String telefonoMask;
	private String telefonoMask2;
	
	//Foto
	boolean bandera = true; //Si ya existe una imagen guardada
    boolean banderaUno = false; // Saber si ya se subio la imagen original y mostrar panel para cortarla
    boolean banderaDos = false; // Saber si se cortó la imagen exitosamente
    private String newImageNameActual;
    private StreamedContent imagen;
    private CroppedImage croppedImage;
    File actual;
    String path = RUTA_ARCHIVOS + File.separator + "HER_LABORATORIO" + File.separator;

	public ManejadorLaboratoriosInformacionGeneral() {
		super();
		idManejador = INFORMACION_GENERAL;
		obtenerListaSedes();
		if (laboratorioActual.getId() != null) {
			sedeActual = laboratorioActual.getSede();
		}

		if (laboratorioActual.getActivo() == null) {
			laboratorioActual.setActivo(true);
		}

		obtenerListaEdificios();
		obtenerListaCampus();
		obtenerListaFacultades();
		if (laboratorioActual.getId() != null
				&& laboratorioActual.getFacultad() != null) {
			facultadActual = laboratorioActual.getFacultad();
		}

		obtenerListaDepartamentos();
		if (laboratorioActual.getId() != null
				&& laboratorioActual.getDepartamento() != null) {
			departamentoActual = laboratorioActual.getDepartamento();
		}

		obtenerListaTipos();
		obtenerListaAreasOCDE();

		// selectItemAreasOCDE:
		String hql7 = "FROM DominioDetalle dd WHERE dd.identificador.id = '"
				+ DominioDetalle.ID_IDENTIFICADOR_AREAS_OCDE
				+ "' ORDER BY dd.identificador.tipo";
		List<DominioDetalle> listaLAO = servicioGeneral.obtenerObjetos(
				DominioDetalle.class, hql7);
		int i = 1;
		selectItemAreasOCDE = new SelectItem[listaLAO.size() + 1];
		selectItemAreasOCDE[0] = new SelectItem("", "Seleccione...");
		for (DominioDetalle lao : listaLAO) {
			selectItemAreasOCDE[i] = new SelectItem(lao.getIdentificador()
					.getTipo(), lao.getDescripcion());
			i++;
		}

		listaAreasOCDEEliminar = new ArrayList<LaboratorioAreaOCDE>();

		listaAreasSecundarias = (ArrayList<LaboratorioAreasSecundariasOCDE>) servicioGeneral
				.obtenerAreasOCDESecundariasLab(laboratorioActual.getId());
		listaAreasSecundariasEliminar = new ArrayList<LaboratorioAreasSecundariasOCDE>();

		seletItemTipoSolicitud = servicioGeneral.selectItemHijosDeTiposValorObjeto(Tipos.TIPOS_SOLICITUDES_LABORATORIO);
		
		inicializarDatosArchivos();
		
		inicializarDatosLab();
		
		if(esNulo(laboratorioActual.getTipoTelefono()) || laboratorioActual.getTipoTelefono().equals("")) {
			telefonoMask = "(99 9) 9999999? Ext.99999 99999 99999";
			laboratorioActual.setTipoTelefono("FIJO");
		}
		if(esNulo(laboratorioActual.getTipoTelefono2()) || laboratorioActual.getTipoTelefono2().equals("")) {
			telefonoMask2 = "(99 9) 9999999? Ext.99999 99999 99999";
			laboratorioActual.setTipoTelefono2("FIJO");
		}
		cambiarTipoTelefono();
		cambiarTipoTelefono2();
		
		actual = new File(path + laboratorioActual.getId() + ".jpg");
		bandera = actual.exists();
	}
	
	public void subirArchivo(FileUploadEvent event) {

        UploadedFile archivoSubir = event.getFile();
        newImageNameActual = getRandomImageName() + "." + obtenerExtensionArchivo(archivoSubir.getFileName());
        String rutaArchivoTemporal = servletContext.getRealPath("") + File.separator + CARPETA_TEMPORAL_IMAGENES + File.separator + newImageNameActual;
        System.out.println("1- subirArchivo - rutaArchivoTemporal: " + rutaArchivoTemporal);

        int tamanioMinimo = 210;
        int tamanioEscala = 500;
        int tamanioMaximoLado = 1000;

        banderaUno = cargarImagenDisco(archivoSubir, servletContext, rutaArchivoTemporal, tamanioMinimo, tamanioEscala, tamanioMaximoLado);
    }
	
	public void crop() throws ImageFormatException {

        String newFilePathDestino = servletContext.getRealPath("") + File.separator + CARPETA_TEMPORAL_IMAGENES + File.separator + laboratorioActual.getId() + ".jpg";
        System.out.println("2- crop - newFilePathDestino: " + newFilePathDestino);

        banderaDos = recortarImagen(croppedImage, newFilePathDestino, servletContext);

        if (!banderaDos)
        	mensajeError("Ha ocurrido un problema en la edición de la imagen cargada.");
    }
	
	public void cerrar() {
        banderaUno = false;
    }
	
	public void agregarODSSecundario() {
		if(esNulo(ODSSecundarioSel)){
			mensajeError("InformacionLaboratorio:selectODSSec","Debe seleccionar un objetivo de desarrollo sostenibñe secundario para agregarlo a la lista");
			return;
		}
		
		if(!ODSSecYaExiste(ODSSecundarioSel)) {
			LaboratorioODSSec odsSec = new LaboratorioODSSec();
			odsSec.setODS(ODSSecundarioSel);
			laboratorioActual.adicionarODSSec(odsSec);
		} else {
			mensajeError("InformacionLaboratorio:selectODSSec","El objetivo secundario seleccionado ya se encuentra asociado");
		}
	}
	
	public Boolean ODSSecYaExiste(Tipos ODSSecundarioSel) {
		for (LaboratorioODSSec odsSec : (List<LaboratorioODSSec>) laboratorioActual.getListaODSSec()) {
			if(ODSSecundarioSel.equals(odsSec.getODS()))
				return true;
		}
		return false;
	}
	
	public void eliminarODSSecundario() {
		laboratorioActual.borrarODSSec(ODSSecundarioSelTabla);
	}
	
	public void inicializarDatosArchivos() {
		// ACTO DE CREACION
		String hql2 = "FROM ArchivoLaboratorio AL WHERE AL.idLab = '"+ laboratorioActual.getId()
			+ "' AND AL.tipoArchivo.id in ("+Tipos.TIPO_DOCUMENTO_INF_GRAL_ACTO_CREACION+") ORDER BY AL.id DESC";
		if (laboratorioActual.getId() == null)
			listaArchivosInfGral = new ArrayList<ArchivoLaboratorio>();
		else 
			listaArchivosInfGral = servicioGeneral.obtenerObjetos(ArchivoLaboratorio.class, hql2);

		listaArchivosEliminadosInfGral = new ArrayList<ArchivoLaboratorio>();
		tipoArchivoSelActoCreacion = obtenerTipoXid(Tipos.TIPO_DOCUMENTO_INF_GRAL_ACTO_CREACION);
		
		//REGLAMENTO
		String hql1 = "FROM ArchivoLaboratorio AL WHERE AL.idLab = '"+ laboratorioActual.getId()
				+ "' AND AL.tipoArchivo.id in ("+Tipos.TIPO_DOCUMENTO_INF_GRAL_REGLAMENTO+") ORDER BY AL.id DESC";
		if (laboratorioActual.getId() == null)
			listaArchivosReglamento = new ArrayList<ArchivoLaboratorio>();
		else 
			listaArchivosReglamento = servicioGeneral.obtenerObjetos(ArchivoLaboratorio.class, hql1);

		listaArchivosEliminadosReglamento = new ArrayList<ArchivoLaboratorio>();
		tipoArchivoSelReglamento = obtenerTipoXid(Tipos.TIPO_DOCUMENTO_INF_GRAL_REGLAMENTO);
	}
	
	public void inicializarDatosLab()
	{
		if(!esNulo(laboratorioActual.getAreaPrincipal()))
			areaOCDEPrincipalSel = laboratorioActual.getAreaPrincipal().getIdString();
		else
			areaOCDEPrincipalSel = "";
		
		if(!esNulo(areaOCDEPrincipalSel))
		{	
			cargarSubAreasOCDE();
			if(!esNulo(laboratorioActual.getSubAreaPrincipal()))
				subAreaOCDEPrincipalSel = laboratorioActual.getSubAreaPrincipal().getIdString();
			else
				subAreaOCDEPrincipalSel = "";
		}
		
		if(!esNulo(laboratorioActual.getObjetivoSocioEconomico()))
			objetivoSocioeconomicoSel = laboratorioActual.getObjetivoSocioEconomico().getIdString();
		else
			objetivoSocioeconomicoSel = "";
		
		if(!esNulo(laboratorioActual.getDeptoCiudad()))
			deptoCiudadSel = laboratorioActual.getDeptoCiudad().getIdString();
		else
			deptoCiudadSel = "";
		
		coordinador = servicioGeneral.obtenerCoordinadorLaboratorio(laboratorioActual.getId());
	}

	public void seleccionarTipos() {
		System.out.println("ManejadorLaboratoriosInformacionGeneral seleccionarTipos:");
	}
	
	public void cambiarTipoTelefono() {
		if(laboratorioActual.getTipoTelefono().equals("FIJO"))
			telefonoMask = "(99 9) 9999999? Ext.99999 99999 99999";
		else if(laboratorioActual.getTipoTelefono().equals("CEL"))
			telefonoMask = "(99) 9999999999";
		else
			telefonoMask = "(99 9) 9999999? Ext.99999 99999 99999";
	}
	
	public void cambiarTipoTelefono2() {
		if(laboratorioActual.getTipoTelefono2().equals("FIJO"))
			telefonoMask2 = "(99 9) 9999999? Ext.99999 99999 99999";
		else if(laboratorioActual.getTipoTelefono2().equals("CEL"))
			telefonoMask2 = "(99) 9999999999";
		else
			telefonoMask2 = "(99 9) 9999999? Ext.99999 99999 99999";
	}
	
	public void cargarSubAreasOCDE() {
		if (!areaOCDEPrincipalSel.equals(""))
			listaSelectItemSubAreasPrincipales = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Long.parseLong(areaOCDEPrincipalSel));
	}
	
	public void cargarSubAreasSecOCDE() {
		if (!areaOCDESecundariaSel.equals(""))
			listaSelectItemSubAreasSecundarias = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Long.parseLong(areaOCDESecundariaSel));
	}
	
	public void eliminarAreaSecundaria() {
		listaAreasSecundarias.remove(areaSecundariaSeleccionadaTabla);
		listaAreasSecundariasEliminar.add(areaSecundariaSeleccionadaTabla);
	}
	
	public Boolean areaSecundariaRepetida() {
		boolean existe = false;
		
		for (LaboratorioAreasSecundariasOCDE las : listaAreasSecundarias) {
			if(!areaOCDESecundariaSel.equals("") && !subAreaOCDESecundariaSel.equals(""))
			{	
				if (las.getArea().getId().equals(Long.parseLong(areaOCDESecundariaSel)))
				{
					if(las.getSubarea().getId().equals(Long.parseLong(subAreaOCDESecundariaSel)))
						existe = true;
				}
			}	
		}
		return existe;
	}
	
	public void agregarAreaSecundaria() {
		boolean error = false;
		LaboratorioAreasSecundariasOCDE areaSecundariaSeleccionada = new LaboratorioAreasSecundariasOCDE();

		if (areaOCDESecundariaSel.equals("")) {
			error = true;
			mensajeError("Debe seleccionar un área secundaria para ser adicionada a la lista");
		}

		if (subAreaOCDESecundariaSel.equals("")) {
			error = true;
			mensajeError("Debe seleccionar una sub área secundaria para ser adicionada a la lista");
		}
		
		if(areaSecundariaRepetida())
		{	
			error = true;
			mensajeError("El área y sub-área secundaria seleccionada ya se encuentra asociada al laboratorio");
		}	

		// Adicionar a la lista
		if (!error) {

			Tipos tipoArea = obtenerTipoXid(Long.parseLong(areaOCDESecundariaSel));
			Tipos tipoSubArea = obtenerTipoXid(Long.parseLong(subAreaOCDESecundariaSel));

			areaSecundariaSeleccionada.setLaboratorio(laboratorioActual);
			areaSecundariaSeleccionada.setArea(tipoArea);
			areaSecundariaSeleccionada.setSubarea(tipoSubArea);

			listaAreasSecundarias.add(areaSecundariaSeleccionada);

			limpiarCamposAreaSecundaria();

		} else {
			System.out.println("ERROR al adicionar el Área y Sub-Área Secundaria a la lista");
			return;
		}
	}
	
	public void limpiarCamposAreaSecundaria() {
		areaOCDESecundariaSel = "";
		subAreaOCDESecundariaSel = "";
	}

	public void subirArchivoActoCreacion(FileUploadEvent event) {
		ArchivoLaboratorio archivoNuevo = subirArchivoLaboratorios(event,tipoArchivoSelActoCreacion);
		if (archivoNuevo != null)
			listaArchivosInfGral.add(archivoNuevo);
		else
			mensajeError("Error al subir archivo.");
	}
	
	public void subirArchivoReglamento(FileUploadEvent event) {
		ArchivoLaboratorio archivoNuevo = subirArchivoLaboratorios(event,tipoArchivoSelReglamento);
		if (archivoNuevo != null)
			listaArchivosReglamento.add(archivoNuevo);
		else
			mensajeError("Error al subir archivo.");
	}

	public void descargarArchivo() {
		descargarArchivoLaboratorios(archivoLaboratorioSeleccionado);
	}
	
	public void descargarArchivoReglamento() {
		descargarArchivoLaboratorios(archivoLaboratorioSeleccionadoReglamento);
	}

	public void eliminarArchivo() {
		listaArchivosInfGral.remove(archivoLaboratorioSeleccionado);
		listaArchivosEliminadosInfGral.add(archivoLaboratorioSeleccionado);
	}
	
	public void eliminarArchivoReglamento() {
		listaArchivosReglamento.remove(archivoLaboratorioSeleccionadoReglamento);
		listaArchivosEliminadosReglamento.add(archivoLaboratorioSeleccionadoReglamento);
	}

	private void obtenerListaAreasOCDE() {
		listaAreasOCDE = new ArrayList<LaboratorioAreaOCDE>();
		if (laboratorioActual.getId() != null) {
			String hql = "FROM LaboratorioAreaOCDE WHERE idLaboratorio = '"
					+ laboratorioActual.getId() + "' ORDER BY fecha";
			List<LaboratorioAreaOCDE> listaLAO = servicioGeneral
					.obtenerObjetos(LaboratorioAreaOCDE.class, hql);
			for (LaboratorioAreaOCDE lao : listaLAO) {
				listaAreasOCDE.add(lao);
			}
		}
	}
	
	private void obtenerListaCampus() {
		String hql = "from Campus WHERE sede = '" + sedeActual.getId()
				+ "' ORDER BY id";
		List<Campus> listaCampus = servicioGeneral.obtenerObjetos(Campus.class,hql);
		campusItem = new SelectItem[listaCampus.size()];
		for (int i = 0; i < listaCampus.size(); i++) {
			Campus c = (Campus) listaCampus.get(i);
			campusItem[i] = new SelectItem(c.getId(), c.getNombre() + " | " + c.getDireccion() + " | " + c.getTelefono());
			c = null;
		}

		if (laboratorioActual.getCampus() == null) {
			laboratorioActual.setCampus(listaCampus.get(0));
		}
	}

	private void obtenerListaEdificios() {
		String hql = "from Edificio WHERE sede = '" + sedeActual.getId()
				+ "' ORDER BY codigo";
		List<Edificio> listaEdificios = servicioGeneral.obtenerObjetos(
				Edificio.class, hql);
		edificiosItem = new SelectItem[listaEdificios.size()];
		for (int i = 0; i < listaEdificios.size(); i++) {
			Edificio e = (Edificio) listaEdificios.get(i);
			String label;
			if (e.getCodigo().equals("-")) {
				label = e.getNombre();
			} else {
				label = e.getCodigo() + " - " + e.getNombre();
			}
			edificiosItem[i] = new SelectItem(e.getId(), label);
			e = null;
		}

		if (laboratorioActual.getEdificio() == null) {
			laboratorioActual.setEdificio(listaEdificios.get(0));
		}
	}

	private void obtenerListaTipos() {
		tipoLaboratorioItem = servicioGeneral.retornaSelectItemArregloDeHijosDeTipos(Tipos.TIPOS_LABORATORIO);
		deptoCiudadItem = servicioGeneral.retornaSelectItemArregloDeHijosDeTipos(Tipos.TIPOS_LAB_DEPTO_CIUDAD);

		portafolioServiciosItem = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPOS_PORTAFOLIO);

		if (laboratorioActual.getTipo() == null) {
			Tipos tipo = new Tipos();
			tipo.setId(Tipos.TIPO_LABORATORIO_PLANTA);
			laboratorioActual.setTipo(tipo);
		}
		
		listaSelectItemAreasPrincipales = servicioGeneral
				.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPOS_LAB_AREAS_CIENTIFICAS_TECNOLOGICAS_OCDE);
		
		listaSelectItemAreasSecundarias = servicioGeneral
				.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPOS_LAB_AREAS_CIENTIFICAS_TECNOLOGICAS_OCDE);
		
		listaSelectItemObjetivosSocieconomicos = servicioGeneral
				.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPOS_LAB_OBJETIVOS_SOCIOECONOMICOS);
		
		listaSIReglamento = servicioGeneral.selectItemHijosDeTiposValorObjeto(Tipos.TIPOS_TIPO_REGLAMENTO_LAB);
		listaODS = servicioGeneral.selectItemHijosDeTiposValorObjeto(Tipos.TIPOS_OBJETIVOS_DESARROLLO_SOSTENIBLE);

	}
	
	public SelectItem[] selectItemTiposXids(String idsTipos) {
		String hqlQuery = "FROM Tipos WHERE id IN (" + idsTipos
				+ ") ORDER BY id";

		// TODO: ordenar por id, sacar actividades mantto de la otra lista,
		// cambiar otro Selectitem

		List<Tipos> listaTipos = servicioGeneral.obtenerObjetos(Tipos.class,
				hqlQuery);
		int i = 0;
		SelectItem[] itemHijos = new SelectItem[listaTipos.size()];
		for (Tipos tipo : listaTipos) {
			itemHijos[i++] = new SelectItem(tipo, tipo.getNombre());
		}
		return itemHijos;
	}

	private void obtenerListaSedes() {

		Boolean esLaboratoriosSede = (Boolean) sesion
				.getAttribute("esLaboratoriosSede");
		personaActual = (Persona) sesion.getAttribute("persona");

		String hql = "from Sede WHERE id NOT IN (1) ORDER BY id";

		if (esLaboratoriosSede && personaActual instanceof InvestigadorInterno) {
			Long idSedePersona = ((InvestigadorInterno) personaActual)
					.getDependencia().getSede().getId();
			hql = "from Sede WHERE id = " + idSedePersona;

		}

		listaSedes = servicioGeneral.obtenerObjetos(Sede.class, hql);

		sedeItem = new SelectItem[listaSedes.size()];
		for (int i = 0; i < listaSedes.size(); i++) {
			Sede sede = (Sede) listaSedes.get(i);
			sedeItem[i] = new SelectItem(sede.getId(), sede.getNombre());
			sede = null;
		}
		sedeActual = (Sede) listaSedes.get(0);
	}

	private void obtenerListaFacultades() {
		String hql = "from Dependencia WHERE sede = '"
				+ sedeActual.getId()
				+ "' AND (esFacultad = 'Y' OR UPPER(nombre) like '%LABORATORIOS SEDE%') AND estado = 'A' "
				+ "ORDER BY nombre";
		listaFacultades = servicioGeneral
				.obtenerObjetos(Dependencia.class, hql);

		facultadItem = new SelectItem[listaFacultades.size()];
		for (int i = 0; i < listaFacultades.size(); i++) {
			Dependencia ci = (Dependencia) listaFacultades.get(i);
			facultadItem[i] = new SelectItem(ci.getId(), ci.getNombre());
			ci = null;
		}
		facultadActual = (Dependencia) listaFacultades.get(0);
	}

	private void obtenerListaDepartamentos() {
		
		/*String hql = "from Dependencia WHERE sede = '"
				+ sedeActual.getId()
				+ "' AND facultad = '"
				+ facultadActual.getId()
				+ "' AND (esDepartamento = 'Y' OR UPPER(nombre) LIKE '%ESCUELA%') AND estado = 'A' "
				+ "ORDER BY nombre";*/
		String hql = "from Dependencia WHERE sede = '"
				+ sedeActual.getId()
				+ "' AND facultad = '"
				+ facultadActual.getId()
				+ "' AND (esDepartamento = 'Y' OR UPPER(nombre) LIKE '%ESCUELA%' OR UPPER(nombre) = '" + facultadActual.getNombre() + "') AND estado = 'A' "
				+ "ORDER BY nombre";
		
		listaDepartamentos = servicioGeneral.obtenerObjetos(Dependencia.class,
				hql);
		
		departamentoItem = new SelectItem[listaDepartamentos.size()];
		for (int i = 0; i < listaDepartamentos.size(); i++) { 
			Dependencia ci = (Dependencia) listaDepartamentos.get(i);

			departamentoItem[i] = new SelectItem(ci.getId(), ci.getNombre());
			ci = null; 
			
		}

		if (listaDepartamentos.size() > 0) {
			departamentoActual = (Dependencia) listaDepartamentos.get(0);
		} else {
			departamentoActual = new Dependencia();
		}
	}

	public void eliminarOCDE() {
		listaAreasOCDEEliminar.add(areaOCDEEliminar);
		listaAreasOCDE.remove(areaOCDEEliminar);
	}

	public void agregarOCDE() {

		// se comprueba que no exista ya:
		for (LaboratorioAreaOCDE llao : listaAreasOCDE) {
			if (llao.getAreaOCDE().getIdentificador().getTipo()
					.equals(tipoAreaOCDESeleccionada)) {
				mensajeError("InformacionLaboratorio:siOCDE",
						"Ya está asociado el área "
								+ llao.getAreaOCDE().getDescripcion());
				return;
			}
		}

		if (tipoAreaOCDESeleccionada != "") {
			LaboratorioAreaOCDE lao = new LaboratorioAreaOCDE();
			DominioDetalle areaOCDE = new DominioDetalle();
			IdDominioDetalle identificador = new IdDominioDetalle();
			identificador.setId(DominioDetalle.ID_IDENTIFICADOR_AREAS_OCDE);
			identificador.setTipo(tipoAreaOCDESeleccionada);
			areaOCDE.setIdentificador(identificador);

			busquedaArea: for (int i = 1; i < selectItemAreasOCDE.length; i++) {
				String idAreaOCDE = selectItemAreasOCDE[i].getValue().toString();
				if (idAreaOCDE.equals(tipoAreaOCDESeleccionada)) {
					areaOCDE.setDescripcion(selectItemAreasOCDE[i].getLabel());
					break busquedaArea;
				}
			}

			lao.setAreaOCDE(areaOCDE);
			lao.setFecha(new Date());
			listaAreasOCDE.add(lao);
			tipoAreaOCDESeleccionada = "";
		}
	}	
	
	public void cambiarSede(ValueChangeEvent event) {
		sedeActual = buscarSede((event.getNewValue()).toString());
		obtenerListaFacultades();
		obtenerListaDepartamentos();
		obtenerListaCampus();
		obtenerListaEdificios();
	}

	public void cambiarSede() {
		sedeActual = buscarSede(sedeActual.getId().toString());
		obtenerListaFacultades();
		obtenerListaDepartamentos();
		obtenerListaCampus();
		obtenerListaEdificios();
	}

	private Sede buscarSede(String id) {
		Sede d = new Sede();
		int i = 0;
		while (i < listaSedes.size()) {
			d = (Sede) listaSedes.get(i);
			if (id.equals(d.getId().toString())) {
				break;
			}
			i = i + 1;
		}
		return d;
	}

	public void cambiarFacultad(ValueChangeEvent event) {
		facultadActual = buscarFacultad((event.getNewValue()).toString());
		obtenerListaDepartamentos();
	}

	public void cambiarFacultad() {
		facultadActual = buscarFacultad(facultadActual.getId());
		obtenerListaDepartamentos();
	}

	private Dependencia buscarFacultad(String id) {
		Dependencia d = new Dependencia();
		int i = 0;
		while (i < listaFacultades.size()) {
			d = (Dependencia) listaFacultades.get(i);
			if (id.equals(d.getId().toString())) {
				break;
			}
			i = i + 1;
		}
		return d;
	}

	@Override
	public String salirGuardar() {
		if (validar()) {
			guardar();
			return (salir());
		} else {
			return null;
		}
	}

	public void guardar() {
		laboratorioActual.setSede(sedeActual);
		laboratorioActual.setFacultad(facultadActual);
		if (departamentoActual.getId() != null) {
			
			String a = departamentoActual.getId();
			if(a.equals(" ")) {
				departamentoActual.setNombre(" ");
				departamentoActual.setId(" ");
			}
			
			laboratorioActual.setDepartamento(departamentoActual);
					
		} else {
			laboratorioActual.setDepartamento(null);
		}

		// Solicitud de creación
		if (esSolicitudLab) {
			laboratorioActual.setActivo(false);
		}
		
		//Tipos Lab
		if(!laboratorioActual.getTipo().getId().equals(Tipos.TIPO_LABORATORIO_LABORATORIO)){
			laboratorioActual.setTipoLabCalibracion(null);
			laboratorioActual.setTipoLabEnsayo(null);
			laboratorioActual.setTipoLabMuestreo(null);
			laboratorioActual.setTipoLabOtro(null);
			laboratorioActual.setTipoLabOtroCual(null);
		}
		
		//Areas OCDE
		Tipos tipoAreaOCDEPrincipalSel = obtenerTipoXid(Long.parseLong(areaOCDEPrincipalSel));
		Tipos tipoSubAreaOCDEPrincipalSel = obtenerTipoXid(Long.parseLong(subAreaOCDEPrincipalSel));
		Tipos tipoObjSocSel = obtenerTipoXid(Long.parseLong(objetivoSocioeconomicoSel));
		
		Tipos deptoCSel = obtenerTipoXid(Long.parseLong(deptoCiudadSel));
		
		laboratorioActual.setAreaPrincipal(tipoAreaOCDEPrincipalSel);
		laboratorioActual.setSubAreaPrincipal(tipoSubAreaOCDEPrincipalSel);
		laboratorioActual.setObjetivoSocioEconomico(tipoObjSocSel);
		laboratorioActual.setDeptoCiudad(deptoCSel);
		
		if(!esNulo(laboratorioActual.getValidaInformacionLabXCoordinador())){
			if(laboratorioActual.getValidaInformacionLabXCoordinador().equals(true))
				laboratorioActual.setFechaValidaInformacionLabXCoordinador(new Date());
		}
		
		guardarLaboratorioActual(idManejador);
		
		// Areas Secundarias
		for (LaboratorioAreasSecundariasOCDE las : listaAreasSecundarias) {
			if (las.getId() == null)
				las.setFechaRegistro(new Date());
			servicioGeneral.guardarObjeto(las);
		}

		for (LaboratorioAreasSecundariasOCDE las : listaAreasSecundariasEliminar) {
			if (las.getId() != null) {
				servicioGeneral.eliminarObjeto(las);
			}
		}
		
		// Guardar solicitud de creación
		if (esSolicitudLab) {
			solicitudLab.setLaboratorio(laboratorioActual);
//			solicitudLab.setFecha(new Date());
			servicioGeneral.guardarObjeto(solicitudLab);
//			sesion.removeAttribute("solicitudLaboratorio");
//			sesion.removeAttribute("manejadorSolicitudLaboratorio");
		}

		// Archivos ACTO CREACION:
		for (ArchivoLaboratorio al : listaArchivosInfGral) {
			Long id = al.getId();
			al.setIdLab(laboratorioActual.getId());
			try {
				servicioGeneral.insertarObjetoConIdLong(al, id);
			} catch (Exception e) {
			}
		}

		for (ArchivoLaboratorio ale : listaArchivosEliminadosInfGral) {
			eliminarArchivoLaboratorios(ale);
		}
		
		// Archivos REGLAMENTO:
		for (ArchivoLaboratorio al : listaArchivosReglamento) {
			Long id = al.getId();
			al.setIdLab(laboratorioActual.getId());
			try {
				servicioGeneral.insertarObjetoConIdLong(al, id);
			} catch (Exception e) {
			}
		}

		for (ArchivoLaboratorio ale : listaArchivosEliminadosReglamento) {
			eliminarArchivoLaboratorios(ale);
		}
		
		//Guardar imagen del lab
		if (banderaDos) {
            // Original file
            File dataInputFile = new File(
            		servletContext.getRealPath("") 
            		+ File.separator + CARPETA_TEMPORAL_IMAGENES 
            		+ File.separator + laboratorioActual.getId() 
            		+ ".jpg"
            		);
            // New path
            File fileSendPath = new File(path + laboratorioActual.getId() + ".jpg");
            
            // Moving the file.
            if (fileSendPath.exists())
                fileSendPath.delete();
            
            dataInputFile.renameTo(fileSendPath);
            banderaDos = false;
        }
		
		calcularCompletitud();
	}

	@Override
	public String siguiente() {
		if (validar()) {
			if (idManejador == laboratorioActual.getEtapaRegistro().intValue()) {
				laboratorioActual.setEtapaRegistro(new Integer(
						(laboratorioActual.getEtapaRegistro()).intValue() + 1));
			}
			guardar();
			limpiarSesion();
			sesion.setAttribute("Laboratorio", laboratorioActual);
			sesion.setAttribute("solicitudLaboratorio", solicitudLab);
			sesion.removeAttribute("ManejadorMenuFormularioLaboratorios");
			return "laboratorioRecursoHumano";
		} else {
			return null;
		}
	}

	public Boolean validar() {
		boolean validar = true;

		Float dedicacionDocencia = laboratorioActual.getDedicacionDocencia();
		Float dedicacionExtension = laboratorioActual.getDedicacionExtension();
		Float dedicacionInvestigacion = laboratorioActual.getDedicacionInvestigacion();

		Float totalDedicacion = dedicacionDocencia + dedicacionExtension + dedicacionInvestigacion;

		if (totalDedicacion > 100 || totalDedicacion < 99) {
			FacesContext.getCurrentInstance().addMessage("InformacionLaboratorio",
				new FacesMessage(FacesMessage.SEVERITY_ERROR,"La suma de los porcentajes de dedicación debe ser igual a 100.",null));

			UIComponent comp = FacesContext.getCurrentInstance().getViewRoot().findComponent("InformacionLaboratorio:dedicacionDocencia");
			if ((comp != null) && (comp instanceof UIInput)) {
				((UIInput) comp).setValid(false);
			}

			comp = FacesContext
					.getCurrentInstance()
					.getViewRoot()
					.findComponent("InformacionLaboratorio:dedicacionInvestigacion");
			if ((comp != null) && (comp instanceof UIInput)) {
				((UIInput) comp).setValid(false);
			}

			comp = FacesContext
					.getCurrentInstance()
					.getViewRoot()
					.findComponent("InformacionLaboratorio:dedicacionExtension");
			if ((comp != null) && (comp instanceof UIInput)) {
				((UIInput) comp).setValid(false);
			}

			validar = false;
		}

		if(areaOCDEPrincipalSel == null || areaOCDEPrincipalSel.equals(""))
		{
			mensajeError("InformacionLaboratorio:selectAreaPrincipal","Debe seleccionar el Área principal (Clasificación OCDE)");
			validar = false;
		}
		
		if(subAreaOCDEPrincipalSel == null || subAreaOCDEPrincipalSel.equals(""))
		{
			mensajeError("InformacionLaboratorio:selectSubAreaPrincipal","Debe seleccionar la Sub Área principal (Clasificación OCDE)");
			validar = false;
		}
		
		if(objetivoSocioeconomicoSel == null || objetivoSocioeconomicoSel.equals(""))
		{
			mensajeError("InformacionLaboratorio:selectObjSocioeconomico","Debe seleccionar el objetivo socio-económico");
			validar = false;
		}
		
		if(deptoCiudadSel == null || deptoCiudadSel.equals(""))
		{
			mensajeError("InformacionLaboratorio:deptoCiudadLista","Debe seleccionar el departamento y la ciudad");
			validar = false;
		}
		
		if(laboratorioActual.getTipo().getId().equals(Tipos.TIPO_LABORATORIO_LABORATORIO))
		{
			if(!laboratorioActual.getTipoLabEnsayo() 
					&& !laboratorioActual.getTipoLabMuestreo() 
					&& !laboratorioActual.getTipoLabCalibracion()
					&& !laboratorioActual.getTipoLabOtro())
			{
				mensajeError("InformacionLaboratorio:cambia5","Debe seleccionar un subtipo de laboratorio (Ensayo, muestreo, calibración u otro)");
				validar = false;
			}
		}
		
		if(esNulo(laboratorioActual.getODSPrincipal())) {
			mensajeError("InformacionLaboratorio:selectODSPrin","Debe seleccionar el objetivo de desarrollo sostenible principal");
			validar = false;
		}

		// Acto de Creación obligatorio para laboratorios nuevos:
		if (!esSolicitudLab) {
			Boolean validadAdC = true;

			try {
				if (laboratorioActual.getTipoActoCreacion().trim().equals("")) {
					validadAdC = false;
				}
				if (laboratorioActual.getNumeroActoCreacion().trim().equals("")) {
					validadAdC = false;
				}
				if (laboratorioActual.getEmanadaPorActoCreacion().trim().equals("")) {
					validadAdC = false;
				}
				if (laboratorioActual.getFechaActoCreacion() == null) {
					validadAdC = false;
				}
				if (listaArchivosInfGral.isEmpty())
					validadAdC = false;
				
			} catch (Exception e) {
				validadAdC = false;
			}

			if (!validadAdC) {
				mensajeError("InformacionLaboratorio:tipoActoCreacion","Debe ingresar TODA la información relativa al Acto de Creación.");
				validar = false;
			}
		}

		return validar;
	}

	/**
	 * @return the sedeActual
	 */
	public Sede getSedeActual() {
		return sedeActual;
	}

	/**
	 * @param sedeActual
	 *            the sedeActual to set
	 */
	public void setSedeActual(Sede sedeActual) {
		this.sedeActual = sedeActual;
	}

	/**
	 * @return the sedeItem
	 */
	public SelectItem[] getSedeItem() {
		return sedeItem;
	}

	/**
	 * @param sedeItem
	 *            the sedeItem to set
	 */
	public void setSedeItem(SelectItem[] sedeItem) {
		this.sedeItem = sedeItem;
	}

	/**
	 * @return the facultadActual
	 */
	public Dependencia getFacultadActual() {
		return facultadActual;
	}

	/**
	 * @param facultadActual
	 *            the facultadActual to set
	 */
	public void setFacultadActual(Dependencia facultadActual) {
		this.facultadActual = facultadActual;
	}

	/**
	 * @return the facultadItem
	 */
	public SelectItem[] getFacultadItem() {
		return facultadItem;
	}

	/**
	 * @param facultadItem
	 *            the facultadItem to set
	 */
	public void setFacultadItem(SelectItem[] facultadItem) {
		this.facultadItem = facultadItem;
	}

	/**
	 * @return the departamentoActual
	 */
	public Dependencia getDepartamentoActual() {
		return departamentoActual;
	}

	/**
	 * @param departamentoActual
	 *            the departamentoActual to set
	 */
	public void setDepartamentoActual(Dependencia departamentoActual) {
		this.departamentoActual = departamentoActual;
	}

	/**
	 * @return the departamentoItem
	 */
	public SelectItem[] getDepartamentoItem() {
		return departamentoItem;
	}

	/**
	 * @param departamentoItem
	 *            the departamentoItem to set
	 */
	public void setDepartamentoItem(SelectItem[] departamentoItem) {
		this.departamentoItem = departamentoItem;
	}

	/**
	 * @return the tipoLaboratorioItem
	 */
	public SelectItem[] getTipoLaboratorioItem() {
		return tipoLaboratorioItem;
	}

	/**
	 * @param tipoLaboratorioItem
	 *            the tipoLaboratorioItem to set
	 */
	public void setTipoLaboratorioItem(SelectItem[] tipoLaboratorioItem) {
		this.tipoLaboratorioItem = tipoLaboratorioItem;
	}

	public SelectItem[] getDeptoCiudadItem() {
		return deptoCiudadItem;
	}

	public void setDeptoCiudadItem(SelectItem[] deptoCiudadItem) {
		this.deptoCiudadItem = deptoCiudadItem;
	}

	/**
	 * @return the campusItem
	 */
	public SelectItem[] getCampusItem() {
		return campusItem;
	}

	/**
	 * @param campusItem
	 *            the campusItem to set
	 */
	public void setCampusItem(SelectItem[] campusItem) {
		this.campusItem = campusItem;
	}

	/**
	 * @return the portafolioServiciosItem
	 */
	public SelectItem[] getPortafolioServiciosItem() {
		return portafolioServiciosItem;
	}

	/**
	 * @return the edificiosItem
	 */
	public SelectItem[] getEdificiosItem() {
		return edificiosItem;
	}

	/**
	 * @param edificiosItem
	 *            the edificiosItem to set
	 */
	public void setEdificiosItem(SelectItem[] edificiosItem) {
		this.edificiosItem = edificiosItem;
	}

	/**
	 * @return the listaAreasOCDE
	 */
	public List<LaboratorioAreaOCDE> getListaAreasOCDE() {
		return listaAreasOCDE;
	}

	/**
	 * @return the seletItemTipoSolicitud
	 */
	public SelectItem[] getSeletItemTipoSolicitud() {
		return seletItemTipoSolicitud;
	}

	/**
	 * @return the selectItemAreasOCDE
	 */
	public SelectItem[] getSelectItemAreasOCDE() {
		return selectItemAreasOCDE;
	}

	/**
	 * @return the tipoAreaOCDESeleccionada
	 */
	public String getTipoAreaOCDESeleccionada() {
		return tipoAreaOCDESeleccionada;
	}

	/**
	 * @param tipoAreaOCDESeleccionada
	 *            the tipoAreaOCDESeleccionada to set
	 */
	public void setTipoAreaOCDESeleccionada(String tipoAreaOCDESeleccionada) {
		this.tipoAreaOCDESeleccionada = tipoAreaOCDESeleccionada;
	}

	/**
	 * @return the areaOCDEEliminar
	 */
	public LaboratorioAreaOCDE getAreaOCDEEliminar() {
		return areaOCDEEliminar;
	}

	/**
	 * @param areaOCDEEliminar
	 *            the areaOCDEEliminar to set
	 */
	public void setAreaOCDEEliminar(LaboratorioAreaOCDE areaOCDEEliminar) {
		this.areaOCDEEliminar = areaOCDEEliminar;
	}

	/**
	 * @return the listaArchivosInfGral
	 */
	public List<ArchivoLaboratorio> getListaArchivosInfGral() {
		return listaArchivosInfGral;
	}

	public Tipos getTipoArchivoSelActoCreacion() {
		return tipoArchivoSelActoCreacion;
	}

	public void setTipoArchivoSelActoCreacion(Tipos tipoArchivoSelActoCreacion) {
		this.tipoArchivoSelActoCreacion = tipoArchivoSelActoCreacion;
	}

	public Tipos getTipoArchivoSelReglamento() {
		return tipoArchivoSelReglamento;
	}

	public void setTipoArchivoSelReglamento(Tipos tipoArchivoSelReglamento) {
		this.tipoArchivoSelReglamento = tipoArchivoSelReglamento;
	}

	/**
	 * @return the archivoLaboratorioSeleccionado
	 */
	public ArchivoLaboratorio getArchivoLaboratorioSeleccionado() {
		return archivoLaboratorioSeleccionado;
	}

	/**
	 * @param archivoLaboratorioSeleccionado
	 *            the archivoLaboratorioSeleccionado to set
	 */
	public void setArchivoLaboratorioSeleccionado(
			ArchivoLaboratorio archivoLaboratorioSeleccionado) {
		this.archivoLaboratorioSeleccionado = archivoLaboratorioSeleccionado;
	}

	public String getSubredSeleccionada() {
		return subredSeleccionada;
	}

	public void setSubredSeleccionada(String subredSeleccionada) {
		this.subredSeleccionada = subredSeleccionada;
	}

	public String getCapacitacionSeleccionada() {
		return capacitacionSeleccionada;
	}

	public void setCapacitacionSeleccionada(String capacitacionSeleccionada) {
		this.capacitacionSeleccionada = capacitacionSeleccionada;
	}

	public String getActividadSeleccionada() {
		return actividadSeleccionada;
	}

	public void setActividadSeleccionada(String actividadSeleccionada) {
		this.actividadSeleccionada = actividadSeleccionada;
	}

	public String getMuestreoSeleccionada() {
		return muestreoSeleccionada;
	}

	public void setMuestreoSeleccionada(String muestreoSeleccionada) {
		this.muestreoSeleccionada = muestreoSeleccionada;
	}

	public String getSistemaGestionSeleccionado() {
		return sistemaGestionSeleccionado;
	}

	public void setSistemaGestionSeleccionado(String sistemaGestionSeleccionado) {
		this.sistemaGestionSeleccionado = sistemaGestionSeleccionado;
	}

	public SelectItem[] getSelectItemSubredes() {
		return selectItemSubredes;
	}

	public void setSelectItemSubredes(SelectItem[] selectItemSubredes) {
		this.selectItemSubredes = selectItemSubredes;
	}

	public SelectItem[] getSelectItemCapacitaciones() {
		return selectItemCapacitaciones;
	}

	public void setSelectItemCapacitaciones(SelectItem[] selectItemCapacitaciones) {
		this.selectItemCapacitaciones = selectItemCapacitaciones;
	}

	public SelectItem[] getSelectItemActividades() {
		return selectItemActividades;
	}

	public void setSelectItemActividades(SelectItem[] selectItemActividades) {
		this.selectItemActividades = selectItemActividades;
	}

	public SelectItem[] getSelectItemMuestreos() {
		return selectItemMuestreos;
	}

	public void setSelectItemMuestreos(SelectItem[] selectItemMuestreos) {
		this.selectItemMuestreos = selectItemMuestreos;
	}

	public SelectItem[] getSelectItemSisGestion() {
		return selectItemSisGestion;
	}

	public void setSelectItemSisGestion(SelectItem[] selectItemSisGestion) {
		this.selectItemSisGestion = selectItemSisGestion;
	}

	public void setSelectItemAreasOCDE(SelectItem[] selectItemAreasOCDE) {
		this.selectItemAreasOCDE = selectItemAreasOCDE;
	}

	public SelectItem[] getListaSelectItemAreasPrincipales() {
		return listaSelectItemAreasPrincipales;
	}

	public void setListaSelectItemAreasPrincipales(SelectItem[] listaSelectItemAreasPrincipales) {
		this.listaSelectItemAreasPrincipales = listaSelectItemAreasPrincipales;
	}

	public SelectItem[] getListaSelectItemSubAreasPrincipales() {
		return listaSelectItemSubAreasPrincipales;
	}

	public void setListaSelectItemSubAreasPrincipales(SelectItem[] listaSelectItemSubAreasPrincipales) {
		this.listaSelectItemSubAreasPrincipales = listaSelectItemSubAreasPrincipales;
	}

	public String getAreaOCDEPrincipalSel() {
		return areaOCDEPrincipalSel;
	}

	public void setAreaOCDEPrincipalSel(String areaOCDEPrincipalSel) {
		this.areaOCDEPrincipalSel = areaOCDEPrincipalSel;
	}

	public String getSubAreaOCDEPrincipalSel() {
		return subAreaOCDEPrincipalSel;
	}

	public void setSubAreaOCDEPrincipalSel(String subAreaOCDEPrincipalSel) {
		this.subAreaOCDEPrincipalSel = subAreaOCDEPrincipalSel;
	}

	public String getAreaOCDESecundariaSel() {
		return areaOCDESecundariaSel;
	}

	public void setAreaOCDESecundariaSel(String areaOCDESecundariaSel) {
		this.areaOCDESecundariaSel = areaOCDESecundariaSel;
	}

	public String getSubAreaOCDESecundariaSel() {
		return subAreaOCDESecundariaSel;
	}

	public void setSubAreaOCDESecundariaSel(String subAreaOCDESecundariaSel) {
		this.subAreaOCDESecundariaSel = subAreaOCDESecundariaSel;
	}

	public SelectItem[] getListaSelectItemAreasSecundarias() {
		return listaSelectItemAreasSecundarias;
	}

	public void setListaSelectItemAreasSecundarias(SelectItem[] listaSelectItemAreasSecundarias) {
		this.listaSelectItemAreasSecundarias = listaSelectItemAreasSecundarias;
	}

	public SelectItem[] getListaSelectItemSubAreasSecundarias() {
		return listaSelectItemSubAreasSecundarias;
	}

	public void setListaSelectItemSubAreasSecundarias(SelectItem[] listaSelectItemSubAreasSecundarias) {
		this.listaSelectItemSubAreasSecundarias = listaSelectItemSubAreasSecundarias;
	}

	public ArrayList<LaboratorioAreasSecundariasOCDE> getListaAreasSecundarias() {
		return listaAreasSecundarias;
	}

	public void setListaAreasSecundarias(ArrayList<LaboratorioAreasSecundariasOCDE> listaAreasSecundarias) {
		this.listaAreasSecundarias = listaAreasSecundarias;
	}

	public ArrayList<LaboratorioAreasSecundariasOCDE> getListaAreasSecundariasEliminar() {
		return listaAreasSecundariasEliminar;
	}

	public void setListaAreasSecundariasEliminar(ArrayList<LaboratorioAreasSecundariasOCDE> listaAreasSecundariasEliminar) {
		this.listaAreasSecundariasEliminar = listaAreasSecundariasEliminar;
	}

	public LaboratorioAreasSecundariasOCDE getAreaSecundariaSeleccionadaTabla() {
		return areaSecundariaSeleccionadaTabla;
	}

	public void setAreaSecundariaSeleccionadaTabla(LaboratorioAreasSecundariasOCDE areaSecundariaSeleccionadaTabla) {
		this.areaSecundariaSeleccionadaTabla = areaSecundariaSeleccionadaTabla;
	}

	public SelectItem[] getListaSelectItemObjetivosSocieconomicos() {
		return listaSelectItemObjetivosSocieconomicos;
	}

	public void setListaSelectItemObjetivosSocieconomicos(SelectItem[] listaSelectItemObjetivosSocieconomicos) {
		this.listaSelectItemObjetivosSocieconomicos = listaSelectItemObjetivosSocieconomicos;
	}
	
	

	public String getObjetivoSocioeconomicoSel() {
		return objetivoSocioeconomicoSel;
	}

	public void setObjetivoSocioeconomicoSel(String objetivoSocioeconomicoSel) {
		this.objetivoSocioeconomicoSel = objetivoSocioeconomicoSel;
	}

	public List<Sede> getListaSedes() {
		return listaSedes;
	}

	public void setListaSedes(List<Sede> listaSedes) {
		this.listaSedes = listaSedes;
	}

	public List<Dependencia> getListaFacultades() {
		return listaFacultades;
	}

	public void setListaFacultades(List<Dependencia> listaFacultades) {
		this.listaFacultades = listaFacultades;
	}

	public List<Dependencia> getListaDepartamentos() {
		return listaDepartamentos;
	}

	public void setListaDepartamentos(List<Dependencia> listaDepartamentos) {
		this.listaDepartamentos = listaDepartamentos;
	}

	public List<LaboratorioAreaOCDE> getListaAreasOCDEEliminar() {
		return listaAreasOCDEEliminar;
	}

	public void setListaAreasOCDEEliminar(List<LaboratorioAreaOCDE> listaAreasOCDEEliminar) {
		this.listaAreasOCDEEliminar = listaAreasOCDEEliminar;
	}

	public List<ArchivoLaboratorio> getListaArchivosEliminadosInfGral() {
		return listaArchivosEliminadosInfGral;
	}

	public void setListaArchivosEliminadosInfGral(List<ArchivoLaboratorio> listaArchivosEliminadosInfGral) {
		this.listaArchivosEliminadosInfGral = listaArchivosEliminadosInfGral;
	}

	public Persona getCoordinador() {
		return coordinador;
	}

	public void setCoordinador(Persona coordinador) {
		this.coordinador = coordinador;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setPortafolioServiciosItem(SelectItem[] portafolioServiciosItem) {
		this.portafolioServiciosItem = portafolioServiciosItem;
	}

	public void setListaAreasOCDE(List<LaboratorioAreaOCDE> listaAreasOCDE) {
		this.listaAreasOCDE = listaAreasOCDE;
	}

	public SelectItem[] getListaSIReglamento() {
		return listaSIReglamento;
	}

	public void setListaSIReglamento(SelectItem[] listaSIReglamento) {
		this.listaSIReglamento = listaSIReglamento;
	}

	public void setSeletItemTipoSolicitud(SelectItem[] seletItemTipoSolicitud) {
		this.seletItemTipoSolicitud = seletItemTipoSolicitud;
	}

	public void setListaArchivosInfGral(List<ArchivoLaboratorio> listaArchivosInfGral) {
		this.listaArchivosInfGral = listaArchivosInfGral;
	}

	public String getDeptoCiudadSel() {
		return deptoCiudadSel;
	}

	public void setDeptoCiudadSel(String deptoCiudadSel) {
		this.deptoCiudadSel = deptoCiudadSel;
	}

	public String getTelefonoMask() {
		return telefonoMask;
	}

	public void setTelefonoMask(String telefonoMask) {
		this.telefonoMask = telefonoMask;
	}

	public String getTelefonoMask2() {
		return telefonoMask2;
	}

	public void setTelefonoMask2(String telefonoMask2) {
		this.telefonoMask2 = telefonoMask2;
	}

	public SelectItem[] getListaODS() {
		return listaODS;
	}

	public void setListaODS(SelectItem[] listaODS) {
		this.listaODS = listaODS;
	}

	public Tipos getODSSecundarioSel() {
		return ODSSecundarioSel;
	}

	public void setODSSecundarioSel(Tipos oDSSecundarioSel) {
		ODSSecundarioSel = oDSSecundarioSel;
	}

	public LaboratorioODSSec getODSSecundarioSelTabla() {
		return ODSSecundarioSelTabla;
	}

	public void setODSSecundarioSelTabla(LaboratorioODSSec oDSSecundarioSelTabla) {
		ODSSecundarioSelTabla = oDSSecundarioSelTabla;
	}

	public List<ArchivoLaboratorio> getListaArchivosReglamento() {
		return listaArchivosReglamento;
	}

	public void setListaArchivosReglamento(List<ArchivoLaboratorio> listaArchivosReglamento) {
		this.listaArchivosReglamento = listaArchivosReglamento;
	}

	public List<ArchivoLaboratorio> getListaArchivosEliminadosReglamento() {
		return listaArchivosEliminadosReglamento;
	}

	public void setListaArchivosEliminadosReglamento(List<ArchivoLaboratorio> listaArchivosEliminadosReglamento) {
		this.listaArchivosEliminadosReglamento = listaArchivosEliminadosReglamento;
	}

	public ArchivoLaboratorio getArchivoLaboratorioSeleccionadoReglamento() {
		return archivoLaboratorioSeleccionadoReglamento;
	}

	public void setArchivoLaboratorioSeleccionadoReglamento(ArchivoLaboratorio archivoLaboratorioSeleccionadoReglamento) {
		this.archivoLaboratorioSeleccionadoReglamento = archivoLaboratorioSeleccionadoReglamento;
	}
	
	public StreamedContent getImagen() {
        try {
            imagen = new DefaultStreamedContent(
                    new ByteArrayInputStream(org.apache.commons.io.FileUtils.readFileToByteArray(actual)), "image/png");
        } catch (IOException e) {

            e.printStackTrace();
        }
        return imagen;
    }

    public void setImagen(StreamedContent imagen) {
        this.imagen = imagen;
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

	public boolean isBanderaDos() {
		return banderaDos;
	}

	public void setBanderaDos(boolean banderaDos) {
		this.banderaDos = banderaDos;
	}

	public String getNewImageNameActual() {
		return newImageNameActual;
	}

	public void setNewImageNameActual(String newImageNameActual) {
		this.newImageNameActual = newImageNameActual;
	}

	public CroppedImage getCroppedImage() {
		return croppedImage;
	}

	public void setCroppedImage(CroppedImage croppedImage) {
		this.croppedImage = croppedImage;
	}

	public File getActual() {
		return actual;
	}

	public void setActual(File actual) {
		this.actual = actual;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
