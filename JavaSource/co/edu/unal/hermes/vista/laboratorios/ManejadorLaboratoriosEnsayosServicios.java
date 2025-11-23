/**
 * @author dgbenitezc
 */

package co.edu.unal.hermes.vista.laboratorios;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;

import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.laboratorios.ArchivoLaboratorio;
import co.edu.unal.hermes.modelo.laboratorios.InsumoLaboratorio;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioActividadEquipo;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleEnsayosServicios;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleEquipos;

public class ManejadorLaboratoriosEnsayosServicios extends ManejadorLaboratorios {

	protected List<LaboratorioDetalleEnsayosServicios> listaEnsayos;
	protected List<LaboratorioDetalleEnsayosServicios> listaEnsayosEliminados;
	protected List<LaboratorioDetalleEnsayosServicios> listaEnsayosFiltrados;
	protected List<LaboratorioDetalleEnsayosServicios> listaensayoAAgregar;

	protected Boolean cambiosEnEnsayos;
	protected SelectItem[] unidadesTiempoItem;
	protected List<SelectItem> equiposUsadosItem;
	private String[] equiposSeleccionadosItem;
	private LaboratorioDetalleEnsayosServicios nuevoES;
	private LaboratorioDetalleEnsayosServicios editaES;
	private LaboratorioDetalleEnsayosServicios consultaES;
	
	protected Boolean nuevoEnsayoServicio;
	
	
	private String nombreEnsayo;
	private Boolean docencia;
	private Boolean investigacion;
	private Boolean extension;
	private Integer valorServicio;
	private Integer ensayosMes;
	private Integer tiempoTotalEstimado;
	
	private Integer personas;
	private Boolean protocoloMuestra;
	private Boolean protocoloEnsayo;
	private Boolean acreditado;
	
	private Tipos unidadTiempoTotalEstimado;
	private Tipos tipoNormaSeleccionado;
	private Tipos tipo;
	
	protected UIComponent agregarEnsayoServicio;
	protected UIComponent editarEnsayoServicio;
	
	protected SelectItem[] tiposNormaLista;
	private String numeroNormaTecnica;
	
	private List<ArchivoLaboratorio> listaArchivosEnsayosServicios;
	private List<ArchivoLaboratorio> listaArchivosEliminadosEnsayosServicios;
	private ArchivoLaboratorio archivoLaboratorioTarifasSeleccionado;
	
	private List<ArchivoLaboratorio> listaArchivosEnsayosServiciosBrochure;
	private List<ArchivoLaboratorio> listaArchivosEliminadosEnsayosServiciosBrochure;
	private ArchivoLaboratorio archivoLaboratorioBrochureSeleccionado;
	
	private List<ArchivoLaboratorio> listaArchivosEnsayosServiciosCondiciones;
	private List<ArchivoLaboratorio> listaArchivosEliminadosEnsayosServiciosCondiciones;
	private ArchivoLaboratorio archivoLaboratorioCondicionesSeleccionado;
	
	private SelectItem[] tipoEnsayoItem;
//	private SelectItem[] acreditadoCertificadoItem;
	
	private String  tipoEnsayoOtros;
	private List<SelectItem> annioItems;
	
	protected SelectItem[] acreditadoOrgItem;
	protected SelectItem[] magnitudAreaItem;
	private SelectItem[] selectItemUnidIntPrefijo;
	private SelectItem[] selectItemUnidIntUnidad;
	private SelectItem[] selectItemIncertMedIncert;
	private SelectItem[] selectItemIncertMedPrefijo;
	private SelectItem[] selectItemIncertMedUnidad;
	
	// Editar
	private String tipoOperación;

	public ManejadorLaboratoriosEnsayosServicios() {
		idManejador = ENSAYOS_SERVICIOS;

		cambiosEnEnsayos = false;
		listaEnsayosEliminados = new ArrayList<LaboratorioDetalleEnsayosServicios>();

		cargarListas();
		
		nuevoEnsayoServicio = false;
		
		cargarDatosIncialesServicioEnsayo();
		
		tipoOperación = "C";
		
//		nuevoES = new LaboratorioDetalleEnsayosServicios();

	}
	
	public void botonConsultarEnsayo()
	{
		System.out.println("botonConsultarEnsayo: ");
	}
	
	public void cargarListas()
	{
		listaEnsayos = new ArrayList<LaboratorioDetalleEnsayosServicios>();
		String hql = "from LaboratorioDetalleEnsayosServicios WHERE laboratorio = '" + laboratorioActual.getId() + "' ORDER BY id";
		listaEnsayos = servicioGeneral.obtenerObjetos(LaboratorioDetalleEnsayosServicios.class, hql);
		if (listaEnsayos.size() < 1) {
			listaEnsayos = new ArrayList<LaboratorioDetalleEnsayosServicios>();
		}
		
		// Se buscan los equipos en uso por ese laboratorio:
		String hql2 = "FROM LaboratorioDetalleEquipos WHERE enUso = 1 AND dadoDeBaja = 0 AND laboratorio = "
				+ laboratorioActual.getId() + " ORDER BY id";
		System.out.println("Búsqueda equipos: " + hql2);
		List<LaboratorioDetalleEquipos> listaEquiposLaboratorio = new ArrayList<LaboratorioDetalleEquipos>();
		listaEquiposLaboratorio = servicioGeneral.obtenerObjetos(LaboratorioDetalleEquipos.class, hql2);
		
		equiposUsadosItem = new Vector<SelectItem>();
		for (LaboratorioDetalleEquipos g : listaEquiposLaboratorio) {
			equiposUsadosItem.add(new SelectItem(g.getId().toString(), g.getPlaca() + " " + g.getEquipo()));
		}

		unidadesTiempoItem = servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccione2(Tipos.UNIDADES_TIEMPO);
		tiposNormaLista = servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccione2(Tipos.TIPOS_LAB_NORMA_TECNICA_ENSAYO_SERVICIO);
		tipoEnsayoItem = servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccione2(Tipos.TIPOS_LAB_TIPO_ENSAYO);
		
		acreditadoOrgItem = servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccione2(Tipos.TIPOS_LAB_ENSAYOS_ORGANISMO_ACREDITA);
		magnitudAreaItem = servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccione2(Tipos.TIPOS_LAB_ENSAYOS_MAGNITUD_AREA);
		
		selectItemUnidIntPrefijo = servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccione2(Tipos.TIPO_LAB_METRO_PREFIJO);
		selectItemUnidIntUnidad = servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccione2(Tipos.TIPO_LAB_METRO_UNIDAD);
		selectItemIncertMedIncert = servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccione2(Tipos.TIPO_LAB_METRO_INCERT_MED_INCERTIDUMBRE);
		selectItemIncertMedPrefijo = servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccione2(Tipos.TIPO_LAB_METRO_PREFIJO);
		selectItemIncertMedUnidad = servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccione2(Tipos.TIPO_LAB_METRO_UNIDAD);
		
		//Lista Archivos
		
		// Carga listaArchivosEnsayosServicios TARIFAS
		String hql3 = "FROM ArchivoLaboratorio AL WHERE AL.idLab = '"
				+ laboratorioActual.getId()
				+ "' AND AL.tipoArchivo in (SELECT t.id FROM Tipos t WHERE t.id = '"
				+ Tipos.TIPOS_DOCUMENTOS_ENS_SERV_LABORATORIOS_ACTO_TARIFAS
				+ "') ORDER BY AL.id DESC";
		if (laboratorioActual.getId() == null) {
			listaArchivosEnsayosServicios = new ArrayList<ArchivoLaboratorio>();
		} else {
			listaArchivosEnsayosServicios = servicioGeneral.obtenerObjetos(ArchivoLaboratorio.class, hql3);
		}
		listaArchivosEliminadosEnsayosServicios = new ArrayList<ArchivoLaboratorio>();
		
		// Carga listaArchivosEnsayosServicios BROCHURE
		String hql4 = "FROM ArchivoLaboratorio AL WHERE AL.idLab = '"
				+ laboratorioActual.getId()
				+ "' AND AL.tipoArchivo in (SELECT t.id FROM Tipos t WHERE t.id = '"
				+ Tipos.TIPOS_DOCUMENTOS_ENS_SERV_LABORATORIOS_BROCHURE
				+ "') ORDER BY AL.id DESC";
		if (laboratorioActual.getId() == null) {
			listaArchivosEnsayosServiciosBrochure = new ArrayList<ArchivoLaboratorio>();
		} else {
			listaArchivosEnsayosServiciosBrochure = servicioGeneral.obtenerObjetos(ArchivoLaboratorio.class, hql4);
		}
		listaArchivosEliminadosEnsayosServiciosBrochure = new ArrayList<ArchivoLaboratorio>();
		
		// Carga listaArchivosEnsayosServicios CONDICIONES
		String hql5 = "FROM ArchivoLaboratorio AL WHERE AL.idLab = '"
				+ laboratorioActual.getId()
				+ "' AND AL.tipoArchivo in (SELECT t.id FROM Tipos t WHERE t.id = '"
				+ Tipos.TIPOS_DOCUMENTOS_ENS_SERV_LABORATORIOS_CONDICIONES
				+ "') ORDER BY AL.id DESC";
		listaArchivosEnsayosServiciosCondiciones = 
			esNulo(laboratorioActual.getId())
			? new ArrayList<ArchivoLaboratorio>()
			: servicioGeneral.obtenerObjetos(ArchivoLaboratorio.class, hql5);
		listaArchivosEliminadosEnsayosServiciosCondiciones = new ArrayList<ArchivoLaboratorio>();
		
		cargarListaAños();
	}
	
	public void cargarListaAños() {
		annioItems = new ArrayList<SelectItem>();
		Calendar fecha = Calendar.getInstance();
        int añoActual = fecha.get(Calendar.YEAR);
        while(añoActual >= 1950){
        	annioItems.add(new SelectItem(añoActual+"",añoActual+""));
        	añoActual--;
        }
	}
	
	public void cargarDatosIncialesServicioEnsayoOld() {
		nombreEnsayo = "";
		docencia = false;
		investigacion = false;
		extension = false;
		valorServicio = 0;
		ensayosMes = 0;
		tiempoTotalEstimado = 0;
		unidadTiempoTotalEstimado = null;
		equiposSeleccionadosItem = null;
		personas = 0;
		protocoloMuestra = false;
		protocoloEnsayo = false;
		acreditado = false;
		tipoNormaSeleccionado = null;
		numeroNormaTecnica = "";
		tipoEnsayoOtros = null;
		tipo = null;
	}
	
	public void cargarDatosIncialesServicioEnsayo() {
		nuevoES = new LaboratorioDetalleEnsayosServicios();
		equiposSeleccionadosItem = null;
	}
	
	public void cancelarModoEdicion() {
		tipoOperación = "C";
		cargarDatosIncialesServicioEnsayo();
		FacesMessage msg = new FacesMessage(
				FacesMessage.SEVERITY_INFO, "Ha cancelado el modo edicion. Si desea editar un ensayo/servicio puede dar clic en el boton editar ubicado en la tabla de la parte inferior", "");
		FacesContext.getCurrentInstance().addMessage("botonAgregarEnsayoServicio", msg);
	}
	
	public void actualizarValoresCamposCondicionales() {
		
		// Tipo ENSAYO Y CALIBRACION
		if(!nuevoES.getTipoEnsayo().getId().equals(Tipos.TIPOS_LAB_TIPO_ENSAYO_ensayo)
				&& !nuevoES.getTipoEnsayo().getId().equals(Tipos.TIPOS_LAB_TIPO_ENSAYO_calib)) {
			nuevoES.setIntervaloPuntoMedMinimo(null);
			nuevoES.setIntervaloPuntoMedMaximo(null);
			nuevoES.setUnidadesIntervaloPrefijo(null);
			nuevoES.setUnidadesIntervaloUnidad(null);
			nuevoES.setUnidadesIntervaloSimbolo(null);
			nuevoES.setIncertidumbreMedicionIncertidumbre(null);
			nuevoES.setIncertidumbreMedicionValor(null);
			nuevoES.setIncertidumbreMedicionPrefijo(null);
			nuevoES.setIncertidumbreMedicionUnidad(null);
			nuevoES.setIncertidumbreMedicionSimbolo(null);
		}
		
		// Tipo CALIBRACION
		if(!nuevoES.getTipoEnsayo().getId().equals(Tipos.TIPOS_LAB_TIPO_ENSAYO_calib)) {
			nuevoES.setCalibMagnitudArea(null);
			nuevoES.setCalibEquipo(null);
		}
		
		//ACREDITADO
		if(!nuevoES.getAcreditado()){
			nuevoES.setAcreditadoOrganismo(null);
		}
		
		//Tipo OTROS
		if(!nuevoES.getTipoEnsayo().getId().equals(Tipos.TIPOS_LAB_TIPO_ENSAYO_otros))
			nuevoES.setTipoEnsayoOtros(null);
	}
	
	public void agregarEditarEnsayoServicio()
	{
		boolean error = false;
				
		// VALIDAR
		if (esCadenaVacia(nuevoES.getNombre().toString()) || nuevoES.getNombre().equals("")) {
			error = true;
			mensajeError("NOMBRE es un campo obligatorio");
		}
		
		if(esNulo(nuevoES.getTipoEnsayo()) || nuevoES.getTipoEnsayo().getId().equals(0L)){
			error = true;
			mensajeError("TIPO es un campo obligatorio");
		}
		
		// Tipo CALIBRACION
		if(nuevoES.getTipoEnsayo().getId().equals(Tipos.TIPOS_LAB_TIPO_ENSAYO_calib)) {
			if(esNulo(nuevoES.getCalibMagnitudArea()) || nuevoES.getCalibMagnitudArea().getId().equals(0L)) {
				error = true;
				mensajeError("MAGNITUD O ÁREA es un campo obligatorio");
			}
			
			if(esCadenaVacia(nuevoES.getCalibEquipo().toString()) || nuevoES.getCalibEquipo().equals("")) {
				error = true;
				mensajeError("EQUIPO O INSTRUMENTO es un campo obligatorio");
			}
		}
		
		// Tipo OTROS
				if(nuevoES.getTipoEnsayo().getId().equals(Tipos.TIPOS_LAB_TIPO_ENSAYO_otros) 
						&& esCadenaVacia(nuevoES.getTipoEnsayoOtros())){
					error = true;
					mensajeError("Debe registrar el detalle del tipo OTROS SERVICIOS");
				}
		
		//ACREDITADO
		if(nuevoES.getAcreditado() && nuevoES.getAcreditadoOrganismo().getId().equals(0L)){
			error = true;
			mensajeError("Debe seleccionar el ORGANISMO que otorga la acreditación");
		}
		
		if (!error) {
			actualizarValoresCamposCondicionales();
			nuevoES.setLaboratorio(laboratorioActual);
			
			// equipos asociados al proyecto:
			Set<LaboratorioDetalleEquipos> equipos = new HashSet<LaboratorioDetalleEquipos>();
			for (String e : equiposSeleccionadosItem) {
				LaboratorioDetalleEquipos detalleEquipo = servicioGeneral.obtenerObjetos(
						LaboratorioDetalleEquipos.class,"FROM LaboratorioDetalleEquipos WHERE id = "+ e).get(0);
				equipos.add(detalleEquipo);
			}
			nuevoES.setEquipos(equipos);
			
			//Si es creacion
			if(tipoOperación.equals("C")) {
				nuevoES.setFechaRegistro(new Date());
				listaEnsayos.add(nuevoES);
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_INFO, "Ensayo/Servicio agregado satisfactoriamente. Para guardar la informacion de todos los servicios asociados al laboratorio es necesario dan clic en los botones inferiores de guardado", "");
				FacesContext.getCurrentInstance().addMessage("botonAgregarEnsayoServicio", msg);
			} else if(tipoOperación.equals("E")){
				tipoOperación = "C";
				int index = listaEnsayos.indexOf(editaES);
				if(index != -1) {
					listaEnsayos.set(index, nuevoES);
				}
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_INFO, "Ensayo/Servicio editado satisfactoriamente. Para guardar la informacion de todos los servicios asociados al laboratorio es necesario dan clic en los botones inferiores de guardado", "");
				FacesContext.getCurrentInstance().addMessage("botonEditarEnsayoServicio", msg);
			}

			cargarDatosIncialesServicioEnsayo();
		}
	}
	
	public void agregarEnsayoServicioOld()
	{
		boolean error = false;
		LaboratorioDetalleEnsayosServicios nuevoEnsayoServicio = new LaboratorioDetalleEnsayosServicios();
		
//		nombreEnsayo = "";
//		valorServicio = 0;
//		ensayosMes = 0;
//		tiempoTotalEstimado = 0;
//		personas = 0;
		
		// Validar
		if (esCadenaVacia(nombreEnsayo.toString()) || nombreEnsayo.equals("")) {
			error = true;
			mensajeError("El NOMBRE es un campo obligatorio");
		}
		
		if(esNulo(tipo) || tipo.getId().equals(0L)){
			error = true;
			mensajeError("El TIPO es un campo obligatorio");
		}
		
//		if (esCadenaVacia(valorServicio.toString()) || valorServicio.equals("")) {
//			error = true;
//			mensajeError(agregarEnsayoServicio, "El VALOR es un campo obligatorio");
//		}
//		
//		if (esCadenaVacia(ensayosMes.toString()) || ensayosMes.equals("")) {
//			error = true;
//			mensajeError(agregarEnsayoServicio, "La cantidad de ENSAYOS X MES es un campo obligatorio");
//		}
//		
//		if (esCadenaVacia(tiempoTotalEstimado.toString()) || tiempoTotalEstimado.equals("")) {
//			error = true;
//			mensajeError(agregarEnsayoServicio, "El TIEMPO ESTIMADO es un campo obligatorio");
//		}
//		
//		if (esCadenaVacia(personas.toString()) || personas.equals("")) {
//			error = true;
//			mensajeError(agregarEnsayoServicio, "La cantidad de PERSONAS es un campo obligatorio");
//		}
			
		
		if (!error) {

//			Tipos tipoUnidadTiempoTotalEstimado = obtenerTipoXid(Long.parseLong(unidadTiempoTotalEstimado));
//			Tipos tipoTipoNormaTec = obtenerTipoXid(Long.parseLong(tipoNormaSeleccionado));
//			Tipos tipoEnsayoServicio = obtenerTipoXid(Long.parseLong(tipo));

			nuevoEnsayoServicio.setLaboratorio(laboratorioActual);
			nuevoEnsayoServicio.setNombre(nombreEnsayo);
			nuevoEnsayoServicio.setDocencia(docencia);
			nuevoEnsayoServicio.setInvestigacion(investigacion);
			nuevoEnsayoServicio.setExtension(extension);
			nuevoEnsayoServicio.setValorServicio(valorServicio);
			nuevoEnsayoServicio.setEnsayosMes(ensayosMes);
			nuevoEnsayoServicio.setTipoNormaTecnica(tipoNormaSeleccionado);
			nuevoEnsayoServicio.setNumeroNormaTecnica(numeroNormaTecnica);
			nuevoEnsayoServicio.setTiempoTotalEstimado(tiempoTotalEstimado);
			nuevoEnsayoServicio.setUnidadTiempoTotalEstimado(unidadTiempoTotalEstimado);
			nuevoEnsayoServicio.setPersonas(personas);
//			nuevoEnsayoServicio.setProtocoloMuestra(protocoloMuestra);
//			nuevoEnsayoServicio.setProtocoloEnsayo(protocoloEnsayo);
			nuevoEnsayoServicio.setAcreditado(acreditado);
			nuevoEnsayoServicio.setTipoEnsayo(tipo);
			nuevoEnsayoServicio.setTipoEnsayoOtros(tipoEnsayoOtros);
			nuevoEnsayoServicio.setFechaRegistro(new Date());
			
			if(!nuevoEnsayoServicio.getTipoEnsayo().getId().equals(Tipos.TIPOS_LAB_TIPO_ENSAYO_otros))
				nuevoEnsayoServicio.setTipoEnsayoOtros(null);
			
			// equipos asociados al proyecto:
			Set<LaboratorioDetalleEquipos> equipos = new HashSet<LaboratorioDetalleEquipos>();
			for (String e : equiposSeleccionadosItem) {
				LaboratorioDetalleEquipos detalleEquipo = servicioGeneral.obtenerObjetos(
						LaboratorioDetalleEquipos.class,"FROM LaboratorioDetalleEquipos WHERE id = "+ e).get(0);
				equipos.add(detalleEquipo);
			}
			nuevoEnsayoServicio.setEquipos(equipos);
			if(listaEnsayos.add(nuevoEnsayoServicio))
				cargarDatosIncialesServicioEnsayoOld();
		}
	}
	
	public void  editarServicioGuardar()
	{
		Set<LaboratorioDetalleEquipos> equipos = new HashSet<LaboratorioDetalleEquipos>();
		for (String e : equiposSeleccionadosItem) {
			LaboratorioDetalleEquipos detalleEquipo = servicioGeneral
					.obtenerObjetos(LaboratorioDetalleEquipos.class,"FROM LaboratorioDetalleEquipos WHERE id = "+ e).get(0);
			equipos.add(detalleEquipo);
		}
	}
	
	public void editarEnsayoServicio() throws CloneNotSupportedException {
		tipoOperación = "E";
		
		//Equipos
		Set<LaboratorioDetalleEquipos> equipos = editaES.getEquipos();
		List<String> listaIds = new ArrayList<String>();		
		for (LaboratorioDetalleEquipos e : equipos)
			listaIds.add(e.getId().toString());
		equiposSeleccionadosItem = new String[listaIds.size()];
		listaIds.toArray(equiposSeleccionadosItem);
		
		nuevoES = new LaboratorioDetalleEnsayosServicios();
		nuevoES = (LaboratorioDetalleEnsayosServicios) editaES.clone();
		
		FacesMessage msg = new FacesMessage(
				FacesMessage.SEVERITY_INFO, "Ensayo/Servicio seleccionado para ser editado. Esto lo puede realizar en el panel superior llamado EDITAR SERVICIO.", "");
		FacesContext.getCurrentInstance().addMessage("botonEditarEnsayoServicio", msg);
	}
	
	public void eliminarEnsayoServicio() {
		
		if(servicioGeneral.obtenerAnalisisCostosEnsayosServicios(consultaES.getId()).isEmpty()) {
			listaEnsayos.remove(consultaES);
			listaEnsayosEliminados.add(consultaES);
			listaEnsayosFiltrados = null;
		} else
				mensajeError("No se puede eliminar el Ensayo/Servicio dado que tiene analisis de costos asociados. "
						+ "Primero debe eliminar los analisis de costos asociados, menú lateral derecho, opcion 'Costos de Servicio'");
		cargarDatosIncialesServicioEnsayo();
	}

	public void subirArchivoTarifas(FileUploadEvent event) {
		ArchivoLaboratorio archivoNuevo = subirArchivoLaboratorios(event,obtenerTipoXid(Tipos.TIPOS_DOCUMENTOS_ENS_SERV_LABORATORIOS_ACTO_TARIFAS));
		if (archivoNuevo != null)
			listaArchivosEnsayosServicios.add(archivoNuevo);
		 else 
			mensajeError("Error al subir archivo.");
	}
	
	public void subirArchivoBrochure(FileUploadEvent event) {
		ArchivoLaboratorio archivoNuevo = subirArchivoLaboratorios(event,obtenerTipoXid(Tipos.TIPOS_DOCUMENTOS_ENS_SERV_LABORATORIOS_BROCHURE));
		if (archivoNuevo != null)
			listaArchivosEnsayosServiciosBrochure.add(archivoNuevo);
		else
			mensajeError("Error al subir archivo.");
	}
	
	public void subirArchivoCondiciones(FileUploadEvent event) {
		ArchivoLaboratorio archivoNuevo = subirArchivoLaboratorios(event,obtenerTipoXid(Tipos.TIPOS_DOCUMENTOS_ENS_SERV_LABORATORIOS_CONDICIONES));
		if (archivoNuevo != null)
			listaArchivosEnsayosServiciosCondiciones.add(archivoNuevo);
		else
			mensajeError("Error al subir archivo.");
	}
	
	public void descargarArchivoTarifas() {
		descargarArchivoLaboratorios(archivoLaboratorioTarifasSeleccionado);
	}
	
	public void descargarArchivoBrochure() {
		descargarArchivoLaboratorios(archivoLaboratorioBrochureSeleccionado);
	}
	
	public void descargarArchivoCondiciones() {
		descargarArchivoLaboratorios(archivoLaboratorioCondicionesSeleccionado);
	}
	
	public void eliminarArchivoTarifas() {
		listaArchivosEnsayosServicios.remove(archivoLaboratorioTarifasSeleccionado);
		listaArchivosEliminadosEnsayosServicios.add(archivoLaboratorioTarifasSeleccionado);
	}
	
	public void eliminarArchivoBrochure() {
		listaArchivosEnsayosServiciosBrochure.remove(archivoLaboratorioBrochureSeleccionado);
		listaArchivosEliminadosEnsayosServiciosBrochure.add(archivoLaboratorioBrochureSeleccionado);
	}
	
	public void eliminarArchivoCondiciones() {
		listaArchivosEnsayosServiciosCondiciones.remove(archivoLaboratorioCondicionesSeleccionado);
		listaArchivosEliminadosEnsayosServiciosCondiciones.add(archivoLaboratorioCondicionesSeleccionado);
	}
	
	public void imprimeListaEnsayos() {
		System.out.println("listaEnsayos");
		if (listaEnsayos != null) {
			for (LaboratorioDetalleEnsayosServicios d : listaEnsayos) {
				System.out.println("getId: " + d.getId());
				System.out.println("getTiempoTotalEstimado: "
						+ d.getTiempoTotalEstimado());
			}
		}
	}

	public void cambiosEnsayos(ValueChangeEvent event) {
		cambiosEnEnsayos = true;
	}

	public void eliminarEnsayo() {
		listaEnsayosEliminados.add(nuevoES);
		listaEnsayos.remove(nuevoES);
		if (listaEnsayos.size() < 1)
			listaEnsayos = null;
		cambiosEnEnsayos = true;
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
		guardarLaboratorioActual(idManejador);
		for (ArchivoLaboratorio al : listaArchivosEnsayosServicios) {
			Long id = al.getId();
			try {
				servicioGeneral.insertarObjetoConIdLong(al, id);
			} catch (Exception e) {
				System.out.println("ya estaba guardado archivo: " + id);
			}
		}
		
		// Archivos listaArchivosEnsayosServiciosBrochure:
		for (ArchivoLaboratorio al : listaArchivosEnsayosServiciosBrochure) {
			Long id = al.getId();
			try {
				servicioGeneral.insertarObjetoConIdLong(al, id);
			} catch (Exception e) {
				System.out.println("ya estaba guardado archivo: " + id);
			}
		}
		
		// Archivos listaArchivosEnsayosServiciosBrochure:
		for (ArchivoLaboratorio al : listaArchivosEnsayosServiciosCondiciones) {
			try {
				servicioGeneral.insertarObjetoConIdLong(al, al.getId());
			} catch (Exception e) {
				System.out.println("ya estaba guardado archivo: " + al.getId());
			}
		}

		for (ArchivoLaboratorio ale : listaArchivosEliminadosEnsayosServicios) {
			eliminarArchivoLaboratorios(ale);
		}
		
		for (ArchivoLaboratorio ale : listaArchivosEliminadosEnsayosServiciosBrochure) {
			eliminarArchivoLaboratorios(ale);
		}
		
		for (ArchivoLaboratorio ale : listaArchivosEliminadosEnsayosServiciosCondiciones) {
			eliminarArchivoLaboratorios(ale);
		}
		
		// Ensayos
		for (LaboratorioDetalleEnsayosServicios lds : listaEnsayos) {
			if (lds.getId() == null)
				lds.setFechaRegistro(new Date());
			servicioGeneral.guardarObjeto(lds);
		}

		for (LaboratorioDetalleEnsayosServicios lds : listaEnsayosEliminados) {
			if (lds.getId() != null) 
				servicioGeneral.eliminarObjeto(lds);
		}
		
		// Guardar solicitud de creación
		if (esSolicitudLab) {
			solicitudLab.setLaboratorio(laboratorioActual);
			servicioGeneral.guardarObjeto(solicitudLab);
		}

//		imprimeListaEnsayos();
//		if (cambiosEnEnsayos) {
//
//			// se eliminan registros que existen en BD:
//			for (LaboratorioDetalleEnsayosServicios ldes : listaEnsayosEliminados) {
//				if (ldes.getId() != null) {
//					System.out
//							.println("eliminando LaboratorioDetalleEnsayosServicios:");
//
//					// borra el set de equipos???
//					servicioGeneral.eliminarObjeto(ldes);
//				}
//			}
//
//			// guardar detalles
//			if (listaEnsayos != null) {
//				for (LaboratorioDetalleEnsayosServicios d : listaEnsayos) {
//					// d.setId(null);
//					d.setLaboratorio(laboratorioActual);
//					servicioGeneral.guardarObjeto(d);
//				}
//			}
//		}
		
		calcularCompletitud();
	}

//	METODO PARA ES_SOLICITUD_LABORATORIOS
//	@Override
//	public String siguiente() {
//		if (validar()) {
//			if (idManejador == laboratorioActual.getEtapaRegistro().intValue()) {
//				laboratorioActual.setEtapaRegistro(new Integer(
//						(laboratorioActual.getEtapaRegistro()).intValue() + 1));
//			}
//			guardar();
//			sesion.setAttribute("Laboratorio", laboratorioActual);
//			sesion.removeAttribute("ManejadorMenuFormularioLaboratorios");
//			limpiarSesion();
//			return "laboratorioPresupuesto";
//		} else {
//			return null;
//		}
//	}
	
	public void agregarEnsayo() {
		if (listaEnsayos != null && listaEnsayos.contains(listaensayoAAgregar.get(0))) {
			mensajeError("Ya existe un ensayo/servicio con el nombre "
					+ listaensayoAAgregar.get(0).getNombre());
		} else {

			// equipos asociados al proyecto:
			Set<LaboratorioDetalleEquipos> equipos = new HashSet<LaboratorioDetalleEquipos>();
			for (String e : equiposSeleccionadosItem) {
				System.out.println("e:" + e);
				LaboratorioDetalleEquipos detalleEquipo = servicioGeneral
						.obtenerObjetos(
								LaboratorioDetalleEquipos.class,
								"FROM LaboratorioDetalleEquipos WHERE id = "
										+ e).get(0);
				equipos.add(detalleEquipo);
			}
			listaensayoAAgregar.get(0).setEquipos(equipos);

			// cuando se agrega un nuevo tipo, solo cambia el id y no el nombre
			Tipos unidadTTEActual = listaensayoAAgregar.get(0)
					.getUnidadTiempoTotalEstimado();
			Tipos unidadTTENueva = (Tipos) servicioGeneral.obtenerObjeto(
					new Tipos(), unidadTTEActual.getId());
			listaensayoAAgregar.get(0).setUnidadTiempoTotalEstimado(
					unidadTTENueva);

			if (listaEnsayos == null) {
				listaEnsayos = new ArrayList<LaboratorioDetalleEnsayosServicios>();
			}
			listaEnsayos.add(listaensayoAAgregar.get(0));
			listaensayoAAgregar = null;
			cambiosEnEnsayos = true;
			equiposSeleccionadosItem = null;
		}
	}

	public void crearNuevoEnsayo() {
		listaensayoAAgregar = new ArrayList<LaboratorioDetalleEnsayosServicios>();

		LaboratorioDetalleEnsayosServicios nuevoEnsayo = new LaboratorioDetalleEnsayosServicios();
		// Valores por omisión
		Tipos unidadTiempoTotalEstimado = servicioGeneral.obtenerObjetoXID(Tipos.class, Tipos.UNIDAD_TIEMPO_SEGUNDO.toString()).get(0);
		nuevoEnsayo.setNombre("Nombre Ensayo");
		nuevoEnsayo.setUnidadTiempoTotalEstimado(unidadTiempoTotalEstimado);
		nuevoEnsayo.setFechaRegistro(new Date());
		nuevoEnsayo.setPersonas(0);
		nuevoEnsayo.setTiempoTotalEstimado(0);
		listaensayoAAgregar.add(nuevoEnsayo);
		imprimeListaEnsayos();
		mensajeError("Seleccione los equipos relacionados, complete los demás datos, y haga click en el botón Asociar.");
	}
	
	public String siguiente() {
		if (validar()) {
			if (idManejador == laboratorioActual.getEtapaRegistro().intValue()) {
				laboratorioActual.setEtapaRegistro(new Integer(
						(laboratorioActual.getEtapaRegistro()).intValue() + 1));
			}
			guardar();
			sesion.setAttribute("Laboratorio", laboratorioActual);
			sesion.removeAttribute("ManejadorMenuFormularioLaboratorios");
			limpiarSesion();
			return "laboratorioMetrologia";
		} else {
			return null;
		}
	}
	
	public String siguienteVolverASolicitud() {
		if (validar()) {
			if (idManejador == laboratorioActual.getEtapaRegistro().intValue()) {
				laboratorioActual.setEtapaRegistro(new Integer(
						(laboratorioActual.getEtapaRegistro()).intValue() + 1));
			}
			guardar();
			limpiarSesion();
//			sesion.setAttribute("Laboratorio", laboratorioActual);
			sesion.removeAttribute("ManejadorMenuFormularioLaboratorios");
			return "solicitudCreacionLaboratorio";
		} else {
			return null;
		}
	}

	public Boolean validar() {
		boolean validar = true;
//		laboratorioActual.trim();
		return validar;
	}
	
	/**
	 * @return the listaEnsayos
	 */
	public List<LaboratorioDetalleEnsayosServicios> getListaEnsayos() {
		return listaEnsayos;
	}

	/**
	 * @param listaEnsayos
	 *            the listaEnsayos to set
	 */
	public void setListaEnsayos(
			List<LaboratorioDetalleEnsayosServicios> listaEnsayos) {
		this.listaEnsayos = listaEnsayos;
	}

	/**
	 * @return the unidadesTiempoItem
	 */
	public SelectItem[] getUnidadesTiempoItem() {
		return unidadesTiempoItem;
	}

	/**
	 * @param unidadesTiempoItem
	 *            the unidadesTiempoItem to set
	 */
	public void setUnidadesTiempoItem(SelectItem[] unidadesTiempoItem) {
		this.unidadesTiempoItem = unidadesTiempoItem;
	}

	/**
	 * @return the equiposUsadosItem
	 */
	public List<SelectItem> getEquiposUsadosItem() {
		return equiposUsadosItem;
	}

	/**
	 * @param equiposUsadosItem
	 *            the equiposUsadosItem to set
	 */
	public void setEquiposUsadosItem(List<SelectItem> equiposUsadosItem) {
		this.equiposUsadosItem = equiposUsadosItem;
	}

	/**
	 * @return the equiposSeleccionadosItem
	 */
	public String[] getEquiposSeleccionadosItem() {
		return equiposSeleccionadosItem;
	}

	/**
	 * @param equiposSeleccionadosItem
	 *            the equiposSeleccionadosItem to set
	 */
	public void setEquiposSeleccionadosItem(String[] equiposSeleccionadosItem) {
		this.equiposSeleccionadosItem = equiposSeleccionadosItem;
	}

	/**
	 * @return the listaensayoAAgregar
	 */
	public List<LaboratorioDetalleEnsayosServicios> getListaensayoAAgregar() {
		return listaensayoAAgregar;
	}

	/**
	 * @param listaensayoAAgregar
	 *            the listaensayoAAgregar to set
	 */
	public void setListaensayoAAgregar(
			List<LaboratorioDetalleEnsayosServicios> listaensayoAAgregar) {
		this.listaensayoAAgregar = listaensayoAAgregar;
	}

	/**
	 * @return the listaEnsayosFiltrados
	 */
	public List<LaboratorioDetalleEnsayosServicios> getListaEnsayosFiltrados() {
		return listaEnsayosFiltrados;
	}

	/**
	 * @param listaEnsayosFiltrados
	 *            the listaEnsayosFiltrados to set
	 */
	public void setListaEnsayosFiltrados(
			List<LaboratorioDetalleEnsayosServicios> listaEnsayosFiltrados) {
		this.listaEnsayosFiltrados = listaEnsayosFiltrados;
	}

	public String getNombreEnsayo() {
		return nombreEnsayo;
	}

	public void setNombreEnsayo(String nombreEnsayo) {
		this.nombreEnsayo = nombreEnsayo;
	}

	public Boolean getDocencia() {
		return docencia;
	}

	public void setDocencia(Boolean docencia) {
		this.docencia = docencia;
	}

	public Boolean getInvestigacion() {
		return investigacion;
	}

	public void setInvestigacion(Boolean investigacion) {
		this.investigacion = investigacion;
	}

	public Boolean getExtension() {
		return extension;
	}

	public void setExtension(Boolean extension) {
		this.extension = extension;
	}

	public List<LaboratorioDetalleEnsayosServicios> getListaEnsayosEliminados() {
		return listaEnsayosEliminados;
	}

	public void setListaEnsayosEliminados(List<LaboratorioDetalleEnsayosServicios> listaEnsayosEliminados) {
		this.listaEnsayosEliminados = listaEnsayosEliminados;
	}

	public Boolean getCambiosEnEnsayos() {
		return cambiosEnEnsayos;
	}

	public void setCambiosEnEnsayos(Boolean cambiosEnEnsayos) {
		this.cambiosEnEnsayos = cambiosEnEnsayos;
	}

	public Integer getValorServicio() {
		return valorServicio;
	}

	public void setValorServicio(Integer valorServicio) {
		this.valorServicio = valorServicio;
	}

	public Integer getEnsayosMes() {
		return ensayosMes;
	}

	public void setEnsayosMes(Integer ensayosMes) {
		this.ensayosMes = ensayosMes;
	}

	public Integer getTiempoTotalEstimado() {
		return tiempoTotalEstimado;
	}

	public void setTiempoTotalEstimado(Integer tiempoTotalEstimado) {
		this.tiempoTotalEstimado = tiempoTotalEstimado;
	}

//	public String getUnidadTiempoTotalEstimado() {
//		return unidadTiempoTotalEstimado;
//	}
//
//	public void setUnidadTiempoTotalEstimado(String unidadTiempoTotalEstimado) {
//		this.unidadTiempoTotalEstimado = unidadTiempoTotalEstimado;
//	}

	public Integer getPersonas() {
		return personas;
	}

	public void setPersonas(Integer personas) {
		this.personas = personas;
	}

	public Boolean getProtocoloMuestra() {
		return protocoloMuestra;
	}

	public void setProtocoloMuestra(Boolean protocoloMuestra) {
		this.protocoloMuestra = protocoloMuestra;
	}

	public Boolean getProtocoloEnsayo() {
		return protocoloEnsayo;
	}

	public void setProtocoloEnsayo(Boolean protocoloEnsayo) {
		this.protocoloEnsayo = protocoloEnsayo;
	}

	public Boolean getAcreditado() {
		return acreditado;
	}

	public void setAcreditado(Boolean acreditado) {
		this.acreditado = acreditado;
	}

	public UIComponent getAgregarEnsayoServicio() {
		return agregarEnsayoServicio;
	}

	public void setAgregarEnsayoServicio(UIComponent agregarEnsayoServicio) {
		this.agregarEnsayoServicio = agregarEnsayoServicio;
	}

	public Boolean getNuevoEnsayoServicio() {
		return nuevoEnsayoServicio;
	}

	public void setNuevoEnsayoServicio(Boolean nuevoEnsayoServicio) {
		this.nuevoEnsayoServicio = nuevoEnsayoServicio;
	}

	public UIComponent getEditarEnsayoServicio() {
		return editarEnsayoServicio;
	}

	public void setEditarEnsayoServicio(UIComponent editarEnsayoServicio) {
		this.editarEnsayoServicio = editarEnsayoServicio;
	}

//	public String getTipoNormaSeleccionado() {
//		return tipoNormaSeleccionado;
//	}
//
//	public void setTipoNormaSeleccionado(String tipoNormaSeleccionado) {
//		this.tipoNormaSeleccionado = tipoNormaSeleccionado;
//	}

	public SelectItem[] getTiposNormaLista() {
		return tiposNormaLista;
	}

	public void setTiposNormaLista(SelectItem[] tiposNormaLista) {
		this.tiposNormaLista = tiposNormaLista;
	}

	public String getNumeroNormaTecnica() {
		return numeroNormaTecnica;
	}

	public void setNumeroNormaTecnica(String numeroNormaTecnica) {
		this.numeroNormaTecnica = numeroNormaTecnica;
	}

	public List<ArchivoLaboratorio> getListaArchivosEnsayosServicios() {
		return listaArchivosEnsayosServicios;
	}

	public void setListaArchivosEnsayosServicios(List<ArchivoLaboratorio> listaArchivosEnsayosServicios) {
		this.listaArchivosEnsayosServicios = listaArchivosEnsayosServicios;
	}

	public List<ArchivoLaboratorio> getListaArchivosEliminadosEnsayosServicios() {
		return listaArchivosEliminadosEnsayosServicios;
	}

	public void setListaArchivosEliminadosEnsayosServicios(
			List<ArchivoLaboratorio> listaArchivosEliminadosEnsayosServicios) {
		this.listaArchivosEliminadosEnsayosServicios = listaArchivosEliminadosEnsayosServicios;
	}

	public List<ArchivoLaboratorio> getListaArchivosEnsayosServiciosBrochure() {
		return listaArchivosEnsayosServiciosBrochure;
	}

	public void setListaArchivosEnsayosServiciosBrochure(List<ArchivoLaboratorio> listaArchivosEnsayosServiciosBrochure) {
		this.listaArchivosEnsayosServiciosBrochure = listaArchivosEnsayosServiciosBrochure;
	}

	public List<ArchivoLaboratorio> getListaArchivosEliminadosEnsayosServiciosBrochure() {
		return listaArchivosEliminadosEnsayosServiciosBrochure;
	}

	public void setListaArchivosEliminadosEnsayosServiciosBrochure(
			List<ArchivoLaboratorio> listaArchivosEliminadosEnsayosServiciosBrochure) {
		this.listaArchivosEliminadosEnsayosServiciosBrochure = listaArchivosEliminadosEnsayosServiciosBrochure;
	}

	public ArchivoLaboratorio getArchivoLaboratorioTarifasSeleccionado() {
		return archivoLaboratorioTarifasSeleccionado;
	}

	public void setArchivoLaboratorioTarifasSeleccionado(ArchivoLaboratorio archivoLaboratorioTarifasSeleccionado) {
		this.archivoLaboratorioTarifasSeleccionado = archivoLaboratorioTarifasSeleccionado;
	}

	public ArchivoLaboratorio getArchivoLaboratorioBrochureSeleccionado() {
		return archivoLaboratorioBrochureSeleccionado;
	}

	public void setArchivoLaboratorioBrochureSeleccionado(ArchivoLaboratorio archivoLaboratorioBrochureSeleccionado) {
		this.archivoLaboratorioBrochureSeleccionado = archivoLaboratorioBrochureSeleccionado;
	}

	

	public LaboratorioDetalleEnsayosServicios getNuevoES() {
		return nuevoES;
	}

	public void setNuevoES(LaboratorioDetalleEnsayosServicios nuevoES) {
		this.nuevoES = nuevoES;
	}

	public SelectItem[] getTipoEnsayoItem() {
		return tipoEnsayoItem;
	}

	public void setTipoEnsayoItem(SelectItem[] tipoEnsayoItem) {
		this.tipoEnsayoItem = tipoEnsayoItem;
	}

	public LaboratorioDetalleEnsayosServicios getEditaES() {
		return editaES;
	}

	public void setEditaES(LaboratorioDetalleEnsayosServicios editaES) {
		this.editaES = editaES;
	}

	public LaboratorioDetalleEnsayosServicios getConsultaES() {
		return consultaES;
	}

	public void setConsultaES(LaboratorioDetalleEnsayosServicios consultaES) {
		this.consultaES = consultaES;
	}

	public String getTipoEnsayoOtros() {
		return tipoEnsayoOtros;
	}

	public void setTipoEnsayoOtros(String tipoEnsayoOtros) {
		this.tipoEnsayoOtros = tipoEnsayoOtros;
	}

	public Tipos getUnidadTiempoTotalEstimado() {
		return unidadTiempoTotalEstimado;
	}

	public void setUnidadTiempoTotalEstimado(Tipos unidadTiempoTotalEstimado) {
		this.unidadTiempoTotalEstimado = unidadTiempoTotalEstimado;
	}

	public Tipos getTipoNormaSeleccionado() {
		return tipoNormaSeleccionado;
	}

	public void setTipoNormaSeleccionado(Tipos tipoNormaSeleccionado) {
		this.tipoNormaSeleccionado = tipoNormaSeleccionado;
	}

	public Tipos getTipo() {
		return tipo;
	}

	public void setTipo(Tipos tipo) {
		this.tipo = tipo;
	}

	public String getTipoOperación() {
		return tipoOperación;
	}

	public void setTipoOperación(String tipoOperación) {
		this.tipoOperación = tipoOperación;
	}

	public List<SelectItem> getAnnioItems() {
		return annioItems;
	}

	public void setAnnioItems(List<SelectItem> annioItems) {
		this.annioItems = annioItems;
	}

	public SelectItem[] getAcreditadoOrgItem() {
		return acreditadoOrgItem;
	}

	public void setAcreditadoOrgItem(SelectItem[] acreditadoOrgItem) {
		this.acreditadoOrgItem = acreditadoOrgItem;
	}

	public SelectItem[] getMagnitudAreaItem() {
		return magnitudAreaItem;
	}

	public void setMagnitudAreaItem(SelectItem[] magnitudAreaItem) {
		this.magnitudAreaItem = magnitudAreaItem;
	}

	public SelectItem[] getSelectItemUnidIntPrefijo() {
		return selectItemUnidIntPrefijo;
	}

	public void setSelectItemUnidIntPrefijo(SelectItem[] selectItemUnidIntPrefijo) {
		this.selectItemUnidIntPrefijo = selectItemUnidIntPrefijo;
	}

	public SelectItem[] getSelectItemUnidIntUnidad() {
		return selectItemUnidIntUnidad;
	}

	public void setSelectItemUnidIntUnidad(SelectItem[] selectItemUnidIntUnidad) {
		this.selectItemUnidIntUnidad = selectItemUnidIntUnidad;
	}

	public SelectItem[] getSelectItemIncertMedIncert() {
		return selectItemIncertMedIncert;
	}

	public void setSelectItemIncertMedIncert(SelectItem[] selectItemIncertMedIncert) {
		this.selectItemIncertMedIncert = selectItemIncertMedIncert;
	}

	public SelectItem[] getSelectItemIncertMedPrefijo() {
		return selectItemIncertMedPrefijo;
	}

	public void setSelectItemIncertMedPrefijo(SelectItem[] selectItemIncertMedPrefijo) {
		this.selectItemIncertMedPrefijo = selectItemIncertMedPrefijo;
	}

	public SelectItem[] getSelectItemIncertMedUnidad() {
		return selectItemIncertMedUnidad;
	}

	public void setSelectItemIncertMedUnidad(SelectItem[] selectItemIncertMedUnidad) {
		this.selectItemIncertMedUnidad = selectItemIncertMedUnidad;
	}

	public List<ArchivoLaboratorio> getListaArchivosEnsayosServiciosCondiciones() {
		return listaArchivosEnsayosServiciosCondiciones;
	}

	public void setListaArchivosEnsayosServiciosCondiciones(
			List<ArchivoLaboratorio> listaArchivosEnsayosServiciosCondiciones) {
		this.listaArchivosEnsayosServiciosCondiciones = listaArchivosEnsayosServiciosCondiciones;
	}

	public List<ArchivoLaboratorio> getListaArchivosEliminadosEnsayosServiciosCondiciones() {
		return listaArchivosEliminadosEnsayosServiciosCondiciones;
	}

	public void setListaArchivosEliminadosEnsayosServiciosCondiciones(
			List<ArchivoLaboratorio> listaArchivosEliminadosEnsayosServiciosCondiciones) {
		this.listaArchivosEliminadosEnsayosServiciosCondiciones = listaArchivosEliminadosEnsayosServiciosCondiciones;
	}

	public ArchivoLaboratorio getArchivoLaboratorioCondicionesSeleccionado() {
		return archivoLaboratorioCondicionesSeleccionado;
	}

	public void setArchivoLaboratorioCondicionesSeleccionado(ArchivoLaboratorio archivoLaboratorioCondicionesSeleccionado) {
		this.archivoLaboratorioCondicionesSeleccionado = archivoLaboratorioCondicionesSeleccionado;
	}
}