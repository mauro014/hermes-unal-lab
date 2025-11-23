package co.edu.unal.hermes.vista.laboratorios;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFCreationHelper;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.event.FileUploadEvent;

import co.edu.unal.hermes.modelo.Bien;
import co.edu.unal.hermes.modelo.Rol;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.laboratorios.ArchivoLaboratorio;
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioActividadEquipo;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleEquipos;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioEnsayoEquipo;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioProyectoEquipo;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioSolicitud;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.utils.Util;

/**
 * @author dgbenitezc
 * 
 */
public class ManejadorLaboratoriosEquipos extends ManejadorLaboratorios {

	protected String placaEquipo;
	protected Bien bien;
	protected List<LaboratorioDetalleEquipos> listaEquipos;
	protected List<LaboratorioDetalleEquipos> listaEquiposDadosDeBaja;
	protected List<LaboratorioActividadEquipo> listaActividades;
	protected List<LaboratorioActividadEquipo> listaActividadesFiltradas;
	protected List<LaboratorioSolicitud> listaSolicitudes;
	protected List<LaboratorioSolicitud> listaSolicitudesFiltradas;
	protected LaboratorioDetalleEquipos detalle;
	protected LaboratorioDetalleEquipos detalleSeleccionado;
	protected LaboratorioSolicitud solicitudSeleccionada;
	protected SelectItem[] estadoFisicoItem;
	protected SelectItem[] estadoFisicoCensoItem;
	protected SelectItem[] mantenimientoItem;
	protected SelectItem[] calibracionItem;
	protected Boolean mostrarEquipos;
	protected String equiposObservaciones;
	protected SelectItem[] estadoFisicoItemTabla;
	protected SelectItem[] estadoFisicoCensoItemTabla;
	protected SelectItem[] mantenimientoItemTabla;
	protected SelectItem[] calibracionItemTabla;
	protected List<LaboratorioDetalleEquipos> listaEquiposEliminar;
	protected List<LaboratorioDetalleEquipos> listaEquiposFiltrados;
	protected SelectItem[] razonNoEstaQuipuItem;
	protected Boolean mostrarRazonNoEstaQuipu;
	
	private SelectItem[] siTiposDocumentos;
	private List<ArchivoLaboratorio> listaArchivos;
	private List<ArchivoLaboratorio> listaArchivosEliminados;
	private ArchivoLaboratorio archivoLaboratorioSeleccionado;
	private Tipos tipoArchivoSeleccionado;
	
	protected Boolean esCoordinadorLabActual;
	
	protected Boolean puedeVerHVEquiposDadosBaja = false;

	public ManejadorLaboratoriosEquipos() {
		idManejador = EQUIPOS;

		limpiarEquipo();

		listaEquipos = new ArrayList<LaboratorioDetalleEquipos>();
		String hql = "from LaboratorioDetalleEquipos WHERE laboratorio = '"
				+ laboratorioActual.getId() + "' AND dadoDeBaja = '0' " 
				+ "ORDER BY tieneHojaDeVida DESC";
		listaEquipos = servicioGeneral.obtenerObjetos(LaboratorioDetalleEquipos.class, hql);
		if (listaEquipos.size() < 1) {
			listaEquipos = null;
		}
		
		cargarEquiposDadosDeBaja();
		cargarListaActividades();
		cargarListaSolicitudes();
		cargarListaArchivos();
		
		estadoFisicoItem = servicioGeneral.retornaSelectItemArregloDeHijosDeTipos(Tipos.TIPOS_ESTADOS);
		estadoFisicoCensoItem = servicioGeneral.retornaSelectItemArregloDeHijosDeTipos(Tipos.TIPOS_ESTADOS);
		equiposObservaciones = laboratorioActual.getEquiposObservaciones();
		mantenimientoItem = servicioGeneral.retornaSelectItemArregloDeHijosDeTipos(Tipos.FRECUENCIA_MANTENIMIENTO);
		calibracionItem = servicioGeneral.retornaSelectItemArregloDeHijosDeTipos(Tipos.FRECUENCIA_MANTENIMIENTO);

		estadoFisicoItemTabla = servicioGeneral.retornaSelectItemArregloDeHijosDeTipos(Tipos.TIPOS_ESTADOS);
		estadoFisicoCensoItemTabla = servicioGeneral.retornaSelectItemArregloDeHijosDeTipos(Tipos.TIPOS_ESTADOS);
		mantenimientoItemTabla = servicioGeneral.retornaSelectItemArregloDeHijosDeTipos(Tipos.FRECUENCIA_MANTENIMIENTO);
		calibracionItemTabla = servicioGeneral.retornaSelectItemArregloDeHijosDeTipos(Tipos.FRECUENCIA_MANTENIMIENTO);
		razonNoEstaQuipuItem = servicioGeneral.retornaSelectItemArregloDeHijosDeTipos(Tipos.TIPOS_EQUIPO_RAZON_NO_QUIPU);
		
		mostrarRazonNoEstaQuipu = false;
		listaEquiposEliminar = new ArrayList<LaboratorioDetalleEquipos>();
		
		rolSeleccionadoLabs = (String) sesion.getAttribute("rolSeleccionadoLabs");
		puedeVerHVEquiposDadosBaja = rolSeleccionadoLabs.equals("LS") || rolSeleccionadoLabs.equals("DL");
		
		validarCoordinadorLaboratorioActual();
		
		cargarValoresPorDefectoCriticidad();
	}
	
	public void cargarValoresPorDefectoCriticidad(){
		laboratorioActual.setCritEquiposImpactoOperaLab(esNulo(laboratorioActual.getCritEquiposImpactoOperaLab()) ? 25F : laboratorioActual.getCritEquiposImpactoOperaLab());
		laboratorioActual.setCritEquiposImpactoSegUsuarios(esNulo(laboratorioActual.getCritEquiposImpactoSegUsuarios()) ? 20F : laboratorioActual.getCritEquiposImpactoSegUsuarios());
		laboratorioActual.setCritEquiposImpactoDaniosInfra(esNulo(laboratorioActual.getCritEquiposImpactoDaniosInfra()) ? 20F : laboratorioActual.getCritEquiposImpactoDaniosInfra());
		laboratorioActual.setCritEquiposImpactoDaniosAmbient(esNulo(laboratorioActual.getCritEquiposImpactoDaniosAmbient()) ? 20F : laboratorioActual.getCritEquiposImpactoDaniosAmbient());
		laboratorioActual.setCritEquiposImpactoImagenUN(esNulo(laboratorioActual.getCritEquiposImpactoImagenUN()) ? 5F : laboratorioActual.getCritEquiposImpactoImagenUN());
		laboratorioActual.setCritEquiposImpactoQuejas(esNulo(laboratorioActual.getCritEquiposImpactoQuejas()) ? 5F : laboratorioActual.getCritEquiposImpactoQuejas());
		laboratorioActual.setCritEquiposImpactoEconomicos(esNulo(laboratorioActual.getCritEquiposImpactoEconomicos()) ? 5F : laboratorioActual.getCritEquiposImpactoEconomicos());

		laboratorioActual.setCritEquiposProbRepFalla(esNulo(laboratorioActual.getCritEquiposProbRepFalla()) ? 30F : laboratorioActual.getCritEquiposProbRepFalla());
		laboratorioActual.setCritEquiposProbTiempoTrabajo(esNulo(laboratorioActual.getCritEquiposProbTiempoTrabajo()) ? 25F : laboratorioActual.getCritEquiposProbTiempoTrabajo());
		laboratorioActual.setCritEquiposProbCondAmbient(esNulo(laboratorioActual.getCritEquiposProbCondAmbient()) ? 20F : laboratorioActual.getCritEquiposProbCondAmbient());
		laboratorioActual.setCritEquiposProbMetrologia(esNulo(laboratorioActual.getCritEquiposProbMetrologia()) ? 25F : laboratorioActual.getCritEquiposProbMetrologia());
		
		servicioGeneral.guardarObjeto(laboratorioActual);
	}
	
	public void validarCoordinadorLaboratorioActual(){
		Rol rol = servicioGeneral.obtenerRolPersonaLaboratorioxIDLab(laboratorioActual.getId(),personaActual.getId().getTipoDocumento(), personaActual.getId().getDocumento());
		if(esNulo(rol))
			esCoordinadorLabActual = false;
		else
			esCoordinadorLabActual = true;
	}
	
	public void cargarListaArchivos()
	{
		// carga listaArchivos
		String hql2 = "FROM ArchivoLaboratorio AL WHERE AL.idLab = '"
				+ laboratorioActual.getId()
				+ "' AND AL.tipoArchivo in (SELECT t.id FROM Tipos t WHERE t.padre = '"
				+ Tipos.TIPOS_DOCUMENTOS_EQUIPOS_LABORATORIO
				+ "') ORDER BY AL.id DESC";
		if (laboratorioActual.getId() == null) {
			listaArchivos = new ArrayList<ArchivoLaboratorio>();
		} else {
			listaArchivos = servicioGeneral.obtenerObjetos(
					ArchivoLaboratorio.class, hql2);
		}

		listaArchivosEliminados = new ArrayList<ArchivoLaboratorio>();

		siTiposDocumentos = servicioGeneral.selectItemHijosDeTiposValorObjeto(Tipos.TIPOS_DOCUMENTOS_EQUIPOS_LABORATORIO);
		tipoArchivoSeleccionado = (Tipos) siTiposDocumentos[0].getValue();
	}
	
	public void descargarArchivo() {
		descargarArchivoLaboratorios(archivoLaboratorioSeleccionado);
	}

	public void eliminarArchivo() {
		listaArchivos.remove(archivoLaboratorioSeleccionado);
		listaArchivosEliminados.add(archivoLaboratorioSeleccionado);
	}
	
	public void cargarEquiposDadosDeBaja()
	{
		listaEquiposDadosDeBaja = new ArrayList<LaboratorioDetalleEquipos>();
		String hql = "from LaboratorioDetalleEquipos WHERE laboratorio = '"
				+ laboratorioActual.getId() + "' AND dadoDeBaja = '1' " 
				+ "ORDER BY tieneHojaDeVida DESC";
		listaEquiposDadosDeBaja = servicioGeneral.obtenerObjetos(LaboratorioDetalleEquipos.class, hql);
		if (listaEquiposDadosDeBaja.size() < 1) {
			listaEquiposDadosDeBaja = null;
		}
	}
	
	public void cambiarRazonNoQuipu()
	{
		if(detalle.getRazonNoQuipu() != null)
		{
			if(detalle.getRazonNoQuipu().equals(7214L))
				mostrarRazonNoEstaQuipu = true;
			else
				mostrarRazonNoEstaQuipu = false;
		}
		else
			mostrarRazonNoEstaQuipu = false;
	}
	
	public void cargarListaActividades()
	{
		if(listaEquipos != null)
		{
			List<LaboratorioActividadEquipo> listaActividadesAux;
			listaActividades = new ArrayList<LaboratorioActividadEquipo>();
			
			for (LaboratorioDetalleEquipos equipo : listaEquipos) {
				String hql = "from LaboratorioActividadEquipo"
						+ " WHERE equipo = '" + equipo.getId() + "'"
						+ " AND activa = 1" 
						+ " ORDER BY fechaEjecucion DESC";
				listaActividadesAux = servicioGeneral.obtenerObjetos(LaboratorioActividadEquipo.class, hql);
				listaActividades.addAll(listaActividadesAux);
			}
		}	

	}
	
	public void cargarListaSolicitudes() {
		listaSolicitudes = servicioGeneral.obtenerListaSolicitudesLaboratorioXTIpoSol(laboratorioActual.getId(), Tipos.TIPOS_TIPO_SOLICITUD_LABORATORIO_eliminar_equipo);
	}
	
	public void subirArchivo(FileUploadEvent event) {
		ArchivoLaboratorio archivoNuevo = subirArchivoLaboratorios(event,tipoArchivoSeleccionado);
		if (archivoNuevo != null) {
			listaArchivos.add(archivoNuevo);
		} else {
			mensajeError("Error al subir archivo.");
		}
	}
	
	public String consultarHistoricoEstadoEquipo() {
        sesion.setAttribute("equipo", detalleSeleccionado);
        sesion.removeAttribute("manejadorConsultaHistoricosEquipos");
        return "consultarHistoricoEquipo";
    }

	public String editarEquipo() {
		sesion.setAttribute("equipoSeleccionado", detalleSeleccionado);
		sesion.setAttribute("fromEquipos", true);
		sesion.setAttribute("editarEquipo", true);
		sesion.removeAttribute("manejadorHojaDeVidaEquipos");
		return "hojaDeVidaEquipos";
	}

	public String consultarInformacionBasica() {
		sesion.setAttribute("equipoSeleccionado", detalleSeleccionado);
		sesion.setAttribute("fromEquipos", true);
		sesion.setAttribute("editarEquipo", false);
        sesion.removeAttribute("manejadorInformacionBasicaEquipo");
		return "informacionBasicaEquipo";
	}

	public String editarInformacionBasica() {
		sesion.setAttribute("equipoSeleccionado", detalleSeleccionado);
		sesion.setAttribute("fromEquipos", true);
		sesion.setAttribute("editarEquipo", true);
		sesion.setAttribute("rolSeleccionadoLabs", rolSeleccionadoLabs);
		sesion.removeAttribute("manejadorInformacionBasicaEquipo");
		return "informacionBasicaEquipo";
	}

	public String consultarEquipo() {
		sesion.setAttribute("equipoSeleccionado", detalleSeleccionado);
		sesion.setAttribute("fromEquipos", true);
		sesion.setAttribute("editarEquipo", false);
		sesion.removeAttribute("manejadorHojaDeVidaEquipos");
		return "hojaDeVidaEquipos";
	}
	
	public String criticidadEquipos() {
		servicioGeneral.guardarObjeto(laboratorioActual);
		servicioGeneral.guardarObjeto(detalleSeleccionado);
		detalleSeleccionado.setLaboratorio(laboratorioActual);
		sesion.setAttribute("equipoSeleccionado", detalleSeleccionado);
		sesion.setAttribute("listaEquipos", listaEquipos);
		sesion.removeAttribute("manejadorCriticidadEquipos");
		return "criticidadEquipos";
	}
	
	public void reporteEquipo() {
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("id_equipo", detalleSeleccionado.getId().toString());
		r.setFormato(ReporteBirt.FORMATO_PDF);
		r.setNombreReporte("laboratorios/ficha-tecnica-equipo");
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().dispatch("/ReporteEngineServlet");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.responseComplete();
			sesion.removeAttribute("reporte");
		}
	}
	
	public void buscarEquipo() {
		placaEquipo = placaEquipo.trim();
		if (!Util.validarNoVacio(placaEquipo)) {
			mensajeError("LaboratoriosEquipos:udPlacaBien",
					"Debe ingresar un número de placa para buscar.");
			return;
		}

		// Se verifica que el equipo no está asociado ya
		LaboratorioDetalleEquipos lde2 = new LaboratorioDetalleEquipos();
		lde2.setPlaca(placaEquipo);
		if (listaEquipos != null && listaEquipos.contains(lde2)) {
			mensajeError("LaboratoriosEquipos:udPlacaBien",
					"El equipo con placa " + placaEquipo
							+ " ya está asociado al laboratorio.");
			return;
		}

		// Se buscan equipos con la misma placa en otro laboratorio
		String hql = "from LaboratorioDetalleEquipos lde WHERE lde.placa = '"
				+ placaEquipo + "' AND lde.laboratorio.id <> "
				+ laboratorioActual.getId();
		List<LaboratorioDetalleEquipos> listaEquiposOtrosLabs = servicioGeneral
				.obtenerObjetos(LaboratorioDetalleEquipos.class, hql);
		if (listaEquiposOtrosLabs.size() > 0) {
			String mensaje = "El equipo con placa " + placaEquipo
					+ " ya está asociado al laboratorio "
					+ listaEquiposOtrosLabs.get(0).getLaboratorio().getNombre()
					+ " ("
					+ listaEquiposOtrosLabs.get(0).getLaboratorio().getId()
					+ "). Para obtener mayor información del equipo por favor consulte en la opción “Administrar Equipos";
			mensajeError("LaboratoriosEquipos:udPlacaBien", mensaje);
			return;
		}

		List<Bien> bienes = servicioGeneral.consultaEquipos(placaEquipo);
		limpiarEquipo();

		detalle.setPlaca(placaEquipo);

		// valores editables por omisión:
		detalle.setEspecializado(false);
		detalle.setMayorDiezAnnios(false);
		detalle.setEnUso(true);
		detalle.setEstadoFisicoCenso(Tipos.TIPO_ESTADO_BUENO);
		mostrarEquipos = true;
		detalle.setDadoDeBaja(false);
		detalle.setFechaBusquedaEnBienes(new Date());

		if (bienes != null && !bienes.isEmpty()) {
			try {
				BeanUtils.copyProperties(bien, bienes.get(0));
				detalle.setSerial(bien.getSerial());
				detalle.setEquipo(buscarEnDescripcion("DESCRIPCION :"));
				detalle.setMarca(buscarEnDescripcion("MARCA :"));
				detalle.setModelo(buscarEnDescripcion("MODELO :"));
				detalle.setExisteEnBienes(true);
				detalle.setValor(bien.getValor());
				detalle.setFechaAdquisicion(bien.getFechaAdquisicion());
				detalle.setFechaServicio(bien.getFechaServicio());
				detalle.setResponsable(bien.getResponsable());
				detalle.setIdResponsable(bien.getIdResponsable());

				Long idTipo = Tipos.TIPOS_ESTADOS + bien.getIdEstadoFisico();
				detalle.setEstadoFisico(idTipo);

				placaEquipo = "";

			} catch (Exception e) {
				e.printStackTrace();
				mensajeError("LaboratoriosEquipos:udPlacaBien",
						"Error al buscar equipo con placa '" + placaEquipo
								+ "'.");
			}
		} else {
			mensajeError("LaboratoriosEquipos:udPlacaBien",
					"No se encuentra ningún equipo en inventario con placa '"
							+ placaEquipo + "'.");
			mensajeError(
					"LaboratoriosEquipos:udPlacaBien",
					"Puede que no aparezca en la búsqueda si su valor es inferior a medio salario mínimo. Ver:");
			mensajeError("LaboratoriosEquipos:udPlacaBien",
					"http://www.legal.unal.edu.co/sisjurun/normas/Norma1.jsp?i=51350");
			mensajeError(
					"LaboratoriosEquipos:udPlacaBien",
					"Si está seguro, y bajo su responsabilidad, puede agregarlo, luego de diligenciar la información necesaria: Equipo(descripción), Marca, Modelo, Serial, etc.");

			detalle.setPlaca("NP-"+detalle.getPlaca());
			detalle.setEstadoFisico(Tipos.TIPO_ESTADO_BUENO);
			detalle.setExisteEnBienes(false);

		}
	}

	public void limpiarEquipo() {
		bien = new Bien();
		detalle = new LaboratorioDetalleEquipos();
		detalle.setEstadoFisico(Tipos.TIPOS_ESTADOS);
		detalle.setEstadoFisicoCenso(Tipos.TIPOS_ESTADOS);
		mostrarEquipos = false;
	}
	
	public String eliminarEquipoAvalHojaDeVida() {
		sesion.setAttribute("equipoSeleccionado", detalleSeleccionado);
		sesion.setAttribute("solicitudCoordinador", true);
		sesion.setAttribute("revisionDLS", false);
		sesion.setAttribute("consultarSolicitud", false);
		sesion.removeAttribute("ManejadorEliminarEquipoHojaDeVida");
		return "enviarRevisarSolicitudEliminarEquipo";
	}
	
	public String revisarSolicitud() {
		sesion.setAttribute("equipoSeleccionado", detalleSeleccionado);
		sesion.setAttribute("solicitudCoordinador", false);
		sesion.setAttribute("revisionDLS", true);
		sesion.setAttribute("consultarSolicitud", false);
		sesion.removeAttribute("ManejadorEliminarEquipoHojaDeVida");
		return "enviarRevisarSolicitudEliminarEquipo";
	}
	
	public String consultarSolicitud() {
		sesion.setAttribute("solicitudSeleccionada", solicitudSeleccionada);
		sesion.setAttribute("equipoSeleccionado", solicitudSeleccionada.getEquipo());
		sesion.setAttribute("solicitudCoordinador", false);
		sesion.setAttribute("revisionDLS", false);
		sesion.setAttribute("consultarSolicitud", true);
		sesion.removeAttribute("ManejadorEliminarEquipoHojaDeVida");
		return "enviarRevisarSolicitudEliminarEquipo";
	}

	public void eliminarEquipo() {		
		List<LaboratorioProyectoEquipo> listaProyectos = null;
		List<ArchivoLaboratorio> listaArchivos = null;
		List<LaboratorioEnsayoEquipo> listaServicios = null;
		Boolean tieneHV = null;
		
	 if(detalleSeleccionado.getId() != null)
	 {
		// se verifican proyectos
		String hql = "FROM LaboratorioProyectoEquipo lpe WHERE lpe.equipo.id = '"
				+ detalleSeleccionado.getId()
				+ "' order by lpe.proyecto.id ASC";
		listaProyectos = servicioGeneral.obtenerObjetos(LaboratorioProyectoEquipo.class, hql);

		// se verifican archivos
		String hql2 = "FROM ArchivoLaboratorio WHERE idDetalle = "+ detalleSeleccionado.getId();
		listaArchivos = servicioGeneral.obtenerObjetos(ArchivoLaboratorio.class, hql2);

		// se verifican ensayos/servicios:
		String hql3 = "FROM LaboratorioEnsayoEquipo LES WHERE LES.equipo.id = '"
				+ detalleSeleccionado.getId()
				+ "' order by LES.servicio.nombre ASC";
		listaServicios = servicioGeneral.obtenerObjetos(LaboratorioEnsayoEquipo.class, hql3);
		}
	 
	 	// se verifica hoja de vida:
			tieneHV = detalleSeleccionado.getTieneHojaDeVida();
		
		if ((listaServicios == null || listaServicios.isEmpty()) && (listaProyectos == null || listaProyectos.isEmpty())
				&& (listaArchivos == null || listaArchivos.isEmpty()) && !tieneHV) {
			listaEquipos.remove(detalleSeleccionado);
			listaEquiposEliminar.add(detalleSeleccionado);
			if (listaEquipos.size() < 1) {
				listaEquipos = null;
			}
		} else {
			for (LaboratorioProyectoEquipo lpe : listaProyectos) {
				String mensaje = "No se puede eliminar el equipo "
						+ detalleSeleccionado.toString()
						+ ", porque está asociado al proyecto de investigación "
						+ lpe.getProyecto().getId()
						+ ". Ver formulario 'Investigación'.";
				mensajeError(mensaje);
			}

			if (tieneHV) {
				mensajeError("No se puede eliminar el equipo "
						+ detalleSeleccionado.toString()
						+ ", porque su Hoja de Vida ha sido diligenciada.  Ver 'Hoja de Vida Equipos'");
			}

			if (!listaArchivos.isEmpty()) {
				mensajeError("No se puede eliminar el equipo "
						+ detalleSeleccionado.toString()
						+ ", porque tiene archivos asociados.  Ver 'Hoja de Vida Equipos'");
			}

			if (!listaServicios.isEmpty()) {
				mensajeError("No se puede eliminar el equipo "
						+ detalleSeleccionado.toString()
						+ ", porque está asociado a algún Servicio registrado.  Ver 'Ensayos y Servicios'");
			}
		}
	}

	public String buscarEnDescripcion(String queBusca) {
		String descripcionBuscar;
		descripcionBuscar = bien.getDescripcion();
		int inicioBusca = descripcionBuscar.indexOf(queBusca);
		if (inicioBusca >= 0) {
			int finalBusca = descripcionBuscar.indexOf(";", inicioBusca);
			descripcionBuscar = descripcionBuscar.substring(inicioBusca + queBusca.length(), finalBusca);
			descripcionBuscar = descripcionBuscar.trim();
		} else {
			descripcionBuscar = "";
		}
		return descripcionBuscar;
	}
	
	public Boolean validarPorcentajesCritEquiposImpactos() {
		boolean validar = true;
		UIComponent comp;

		Float critEquiposImpactoOperaLab = laboratorioActual.getCritEquiposImpactoOperaLab();
		Float critEquiposImpactoSegUsuarios = laboratorioActual.getCritEquiposImpactoSegUsuarios();
		Float critEquiposImpactoDaniosInfra = laboratorioActual.getCritEquiposImpactoDaniosInfra();
		Float critEquiposImpactoDaniosAmbient = laboratorioActual.getCritEquiposImpactoDaniosAmbient();
		Float critEquiposImpactoImagenUN = laboratorioActual.getCritEquiposImpactoImagenUN();
		Float critEquiposImpactoQuejas = laboratorioActual.getCritEquiposImpactoQuejas();
		Float critEquiposImpactoEconomicos = laboratorioActual.getCritEquiposImpactoEconomicos();	

		Float totalImpacto = critEquiposImpactoOperaLab + critEquiposImpactoSegUsuarios + critEquiposImpactoDaniosInfra 
				+ critEquiposImpactoDaniosAmbient + critEquiposImpactoImagenUN + critEquiposImpactoQuejas + critEquiposImpactoEconomicos;
		
		if (totalImpacto == 100) {
			validar = true;
		} else {
			FacesContext.getCurrentInstance().addMessage("LaboratoriosEquipos:messagesImpacto",
				new FacesMessage(FacesMessage.SEVERITY_ERROR,"La suma de los porcentajes de impacto debe ser igual a 100.",null));
			validar = false;
		}
		
		comp = FacesContext.getCurrentInstance().getViewRoot().findComponent("LaboratoriosEquipos:critEquiposImpactoOperaLab");
		if ((comp != null) && (comp instanceof UIInput)) ((UIInput) comp).setValid(validar);
		
		comp = FacesContext.getCurrentInstance().getViewRoot().findComponent("LaboratoriosEquipos:critEquiposImpactoSegUsuarios");
		if ((comp != null) && (comp instanceof UIInput)) ((UIInput) comp).setValid(validar);
		
		comp = FacesContext.getCurrentInstance().getViewRoot().findComponent("LaboratoriosEquipos:critEquiposImpactoDaniosInfra");
		if ((comp != null) && (comp instanceof UIInput)) ((UIInput) comp).setValid(validar);
		
		comp = FacesContext.getCurrentInstance().getViewRoot().findComponent("LaboratoriosEquipos:critEquiposImpactoDaniosAmbient");
		if ((comp != null) && (comp instanceof UIInput)) ((UIInput) comp).setValid(validar);
		
		comp = FacesContext.getCurrentInstance().getViewRoot().findComponent("LaboratoriosEquipos:critEquiposImpactoImagenUN");
		if ((comp != null) && (comp instanceof UIInput)) ((UIInput) comp).setValid(validar);
		
		comp = FacesContext.getCurrentInstance().getViewRoot().findComponent("LaboratoriosEquipos:critEquiposImpactoQuejas");
		if ((comp != null) && (comp instanceof UIInput)) ((UIInput) comp).setValid(validar);
		
		comp = FacesContext.getCurrentInstance().getViewRoot().findComponent("LaboratoriosEquipos:critEquiposImpactoEconomicos");
		if ((comp != null) && (comp instanceof UIInput)) ((UIInput) comp).setValid(validar);
		
		return validar;
	}
	
	public Boolean validarPorcentajesCritEquiposProbabilidades() {
		boolean validar = true;
		UIComponent comp;
		
		Float critEquiposProbRepFalla = laboratorioActual.getCritEquiposProbRepFalla();
		Float critEquiposProbTiempoTrabajo = laboratorioActual.getCritEquiposProbTiempoTrabajo();
		Float critEquiposProbCondAmbient = laboratorioActual.getCritEquiposProbCondAmbient();
		Float critEquiposProbMetrologia = laboratorioActual.getCritEquiposProbMetrologia();
		
		Float totalProbabilidad = critEquiposProbRepFalla + critEquiposProbTiempoTrabajo + critEquiposProbCondAmbient + critEquiposProbMetrologia;

		if (totalProbabilidad == 100) {
			validar = true;
		} else {
			FacesContext.getCurrentInstance().addMessage("LaboratoriosEquipos:messagesProbabilidad",
				new FacesMessage(FacesMessage.SEVERITY_ERROR,"La suma de los porcentajes de probabilidades debe ser igual a 100.",null));
			validar = false;
		}
		
		comp = FacesContext.getCurrentInstance().getViewRoot().findComponent("LaboratoriosEquipos:critEquiposProbRepFalla");
		if ((comp != null) && (comp instanceof UIInput)) ((UIInput) comp).setValid(validar);
		
		comp = FacesContext.getCurrentInstance().getViewRoot().findComponent("LaboratoriosEquipos:critEquiposProbTiempoTrabajo");
		if ((comp != null) && (comp instanceof UIInput)) ((UIInput) comp).setValid(validar);
		
		comp = FacesContext.getCurrentInstance().getViewRoot().findComponent("LaboratoriosEquipos:critEquiposProbCondAmbient");
		if ((comp != null) && (comp instanceof UIInput)) ((UIInput) comp).setValid(validar);
		
		comp = FacesContext.getCurrentInstance().getViewRoot().findComponent("LaboratoriosEquipos:critEquiposProbMetrologia");
		if ((comp != null) && (comp instanceof UIInput)) ((UIInput) comp).setValid(validar);
		
		return validar;
	}


	public void agregarEquipo() {
		listaMensajesValidacion = new ArrayList<String>();
		if (!Util.validarNoVacio(detalle)
			|| !Util.validarNoVacio(detalle.getPlaca())
			|| !Util.validarNoVacio(detalle.getEquipo())
			|| !Util.validarNoVacio(detalle.getMarca())
			|| !Util.validarNoVacio(detalle.getModelo())
			|| !Util.validarNoVacio(detalle.getSerial())
			|| (!Util.validarNoVacio(detalle.getRazonNoQuipu()) && !detalle.getExisteEnBienes())
			) {
			listaMensajesValidacion.add("No puede agregar un bien vacío.");
			mensajeError("Campos obligatorios: Placa, Descripción(Equipo), Marca, Modelo y Serial. Si la placa no está registrada en el sistema de inventarios, es obligatorio registrar el motivo.");
			return;
		}
		
		if (Util.validarNoVacio(detalle.getRazonNoQuipu()) && !detalle.getExisteEnBienes())
		{
			if ( (!Util.validarNoVacio(detalle.getRazonNoQuipuOtros()) && detalle.getRazonNoQuipu().equals(Tipos.TIPOS_EQUIPO_RAZON_NO_QUIPU_OTROS) ) ) {
					listaMensajesValidacion.add("No puede agregar un bien vacío.");
					mensajeError("Campos obligatorios: Otro motivo por el cual la placa no está registrada en el sistema de inventarios");
					return;
				}
		}	

		if (listaEquipos != null && listaEquipos.contains(detalle)) {
			listaMensajesValidacion.add("El equipo con placa " + bien.getPlaca() + " ya ha sido agregado.");
			mensajeError("El equipo con placa " + detalle.getPlaca() + " ya ha sido agregado.");
			return;
		}
		
		detalle.setFechaRegistro(new Date());
		detalle.setTipoDocumentoPersonaRegistro(personaActual.getId().getTipoDocumento());
		detalle.setDocumentoPersonaRegistro(personaActual.getId().getDocumento());
		detalle.setLaboratorio(laboratorioActual);
		if (listaEquipos == null) {
			listaEquipos = new ArrayList<LaboratorioDetalleEquipos>();
		}

		listaEquipos.add(detalle);
		mensajeInfo("El equipo ha sido asociado al Laboratorio, debe registrar la información "
				+ "básica para que quede guardado. Luego debe registrar la hoja de vida, recuerde que "
				+ "DEBE hacer clic en uno de los botones Guardar en la parte inferior de la página y "
				+ "verificar que haya registrado todos los campos requeridos");
		limpiarEquipo();
	}

	public String salirGuardar() {
		if (validar()) {
			guardar();
			return (salir());
		} else {
			return null;
		}
	}
	
	public void postProcessXLS(Object document) {
		HSSFWorkbook wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow header = sheet.getRow(0);
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		wb.setSheetName(0, "ACTIVIDADES REGISTRADAS");
		int numeroColumnas = header.getPhysicalNumberOfCells();
		// fija el estilo al encabezado
		for (int i = 0; i < numeroColumnas; i++) {
			header.getCell(i).setCellStyle(cellStyle);
		}

		// Inmovilizar fila superior:
		sheet.createFreezePane(0, 1);

	}

	public void guardar() {
		laboratorioActual.setEquiposObservaciones(equiposObservaciones);
		guardarLaboratorioActual(idManejador);
		servicioGeneral.calcularValoresCriticidadXLab(laboratorioActual.getId(),listaEquipos);
		try {
			// Se eliminan los equipos:
			for (LaboratorioDetalleEquipos lde : listaEquiposEliminar) {
				if(lde.getId() != null)
				{
					String sql = "DELETE HER_LABORATORIO_DETALLE_EQUIPO WHERE LDE_ID = "+ lde.getId();
					guardarLogEquiposBorrar(lde,lde.getTieneHojaDeVida());
					servicioGeneral.eliminar(sql);
				}	
			}

			// guardar detalles
			if (listaEquipos != null) {
				for (LaboratorioDetalleEquipos d : listaEquipos) {
					d.setLaboratorio(laboratorioActual);
					Boolean esEquipoNuevo = d.getId() == null ? true : false;
					servicioGeneral.guardarObjeto(d);
					if(esEquipoNuevo)
						guardarLogEquipos(esEquipoNuevo,true,d);
				}
			}
			
//			 Archivos:
			for (ArchivoLaboratorio al : listaArchivos) {
				Long id = al.getId();
				al.setIdLab(laboratorioActual.getId());
				try {
					servicioGeneral.insertarObjetoConIdLong(al, id);
				} catch (Exception e) {
				}
			}

			for (ArchivoLaboratorio ale : listaArchivosEliminados) {
				eliminarArchivoLaboratorios(ale);
			}

		} catch (SQLException e) {
			
			e.printStackTrace();
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
			return "laboratorioInvestigacion";
		} else {
			return null;
		}
	}

	public Boolean validar() {
		Boolean validar = false;
		listaMensajesValidacion = new ArrayList<String>();
		equiposObservaciones = equiposObservaciones.trim();
		validar = listaMensajesValidacion.size() == 0 && validarPorcentajesCritEquiposImpactos() && validarPorcentajesCritEquiposProbabilidades();
		return validar;
	}

	/**
	 * @return the placaEquipo
	 */
	public String getPlacaEquipo() {
		return placaEquipo;
	}

	/**
	 * @param placaEquipo
	 *            the placaEquipo to set
	 */
	public void setPlacaEquipo(String placaEquipo) {
		this.placaEquipo = placaEquipo;
	}

	/**
	 * @return the bien
	 */
	public Bien getBien() {
		return bien;
	}

	/**
	 * @return the listaEquipos
	 */
	public List<LaboratorioDetalleEquipos> getListaEquipos() {
		return listaEquipos;
	}

	/**
	 * @param listaEquipos
	 *            the listaEquipos to set
	 */
	public void setListaEquipos(List<LaboratorioDetalleEquipos> listaEquipos) {
		this.listaEquipos = listaEquipos;
	}

	/**
	 * @return the detalle
	 */
	public LaboratorioDetalleEquipos getDetalle() {
		return detalle;
	}

	/**
	 * @param detalle
	 *            the detalle to set
	 */
	public void setDetalle(LaboratorioDetalleEquipos detalle) {
		this.detalle = detalle;
	}

	/**
	 * @return the estadoFisicoItem
	 */
	public SelectItem[] getEstadoFisicoItem() {
		return estadoFisicoItem;
	}

	/**
	 * @return the mostrarEquipos
	 */
	public Boolean getMostrarEquipos() {
		return mostrarEquipos;
	}

	/**
	 * @return the equiposObservaciones
	 */
	public String getEquiposObservaciones() {
		return equiposObservaciones;
	}

	/**
	 * @param equiposObservaciones
	 *            the equiposObservaciones to set
	 */
	public void setEquiposObservaciones(String equiposObservaciones) {
		this.equiposObservaciones = equiposObservaciones;
	}

	/**
	 * @return the estadoFisicoCensoItem
	 */
	public SelectItem[] getEstadoFisicoCensoItem() {
		return estadoFisicoCensoItem;
	}

	/**
	 * @return the mantenimientoItem
	 */
	public SelectItem[] getMantenimientoItem() {
		return mantenimientoItem;
	}

	/**
	 * @return the calibracionItem
	 */
	public SelectItem[] getCalibracionItem() {
		return calibracionItem;
	}

	/**
	 * @return the detalleSeleccionado
	 */
	public LaboratorioDetalleEquipos getDetalleSeleccionado() {
		return detalleSeleccionado;
	}

	/**
	 * @param detalleSeleccionado
	 *            the detalleSeleccionado to set
	 */
	public void setDetalleSeleccionado(
			LaboratorioDetalleEquipos detalleSeleccionado) {
		this.detalleSeleccionado = detalleSeleccionado;
	}

	/**
	 * @return the estadoFisicoItemTabla
	 */
	public SelectItem[] getEstadoFisicoItemTabla() {
		return estadoFisicoItemTabla;
	}

	/**
	 * @return the estadoFisicoCensoItemTabla
	 */
	public SelectItem[] getEstadoFisicoCensoItemTabla() {
		return estadoFisicoCensoItemTabla;
	}

	/**
	 * @return the mantenimientoItemTabla
	 */
	public SelectItem[] getMantenimientoItemTabla() {
		return mantenimientoItemTabla;
	}

	/**
	 * @return the calibracionItemTabla
	 */
	public SelectItem[] getCalibracionItemTabla() {
		return calibracionItemTabla;
	}

	/**
	 * @return the listaEquiposFiltrados
	 */
	public List<LaboratorioDetalleEquipos> getListaEquiposFiltrados() {
		return listaEquiposFiltrados;
	}

	/**
	 * @param listaEquiposFiltrados
	 *            the listaEquiposFiltrados to set
	 */
	public void setListaEquiposFiltrados(
			List<LaboratorioDetalleEquipos> listaEquiposFiltrados) {
		this.listaEquiposFiltrados = listaEquiposFiltrados;
	}

	public List<LaboratorioDetalleEquipos> getListaEquiposDadosDeBaja() {
		return listaEquiposDadosDeBaja;
	}

	public void setListaEquiposDadosDeBaja(
			List<LaboratorioDetalleEquipos> listaEquiposDadosDeBaja) {
		this.listaEquiposDadosDeBaja = listaEquiposDadosDeBaja;
	}

	public List<LaboratorioActividadEquipo> getListaActividadesMantenimiento() {
		return listaActividades;
	}

	public void setListaActividadesMantenimiento(
			List<LaboratorioActividadEquipo> listaActividadesMantenimiento) {
		this.listaActividades = listaActividadesMantenimiento;
	}

	public List<LaboratorioActividadEquipo> getListaActividadesFiltradas() {
		return listaActividadesFiltradas;
	}

	public void setListaActividadesFiltradas(
			List<LaboratorioActividadEquipo> listaActividadesFiltradas) {
		this.listaActividadesFiltradas = listaActividadesFiltradas;
	}

	public List<LaboratorioActividadEquipo> getListaActividades() {
		return listaActividades;
	}

	public void setListaActividades(
			List<LaboratorioActividadEquipo> listaActividades) {
		this.listaActividades = listaActividades;
	}

	public SelectItem[] getRazonNoEstaQuipuItem() {
		return razonNoEstaQuipuItem;
	}

	public void setRazonNoEstaQuipuItem(SelectItem[] razonNoEstaQuipuItem) {
		this.razonNoEstaQuipuItem = razonNoEstaQuipuItem;
	}

	public Boolean getMostrarRazonNoEstaQuipu() {
		return mostrarRazonNoEstaQuipu;
	}

	public void setMostrarRazonNoEstaQuipu(Boolean mostrarRazonNoEstaQuipu) {
		this.mostrarRazonNoEstaQuipu = mostrarRazonNoEstaQuipu;
	}

	public Tipos getTipoArchivoSeleccionado() {
		return tipoArchivoSeleccionado;
	}

	public void setTipoArchivoSeleccionado(Tipos tipoArchivoSeleccionado) {
		this.tipoArchivoSeleccionado = tipoArchivoSeleccionado;
	}

	public List<ArchivoLaboratorio> getListaArchivos() {
		return listaArchivos;
	}

	public void setListaArchivos(List<ArchivoLaboratorio> listaArchivos) {
		this.listaArchivos = listaArchivos;
	}

	public SelectItem[] getSiTiposDocumentos() {
		return siTiposDocumentos;
	}

	public void setSiTiposDocumentos(SelectItem[] siTiposDocumentos) {
		this.siTiposDocumentos = siTiposDocumentos;
	}

	public List<ArchivoLaboratorio> getListaArchivosEliminados() {
		return listaArchivosEliminados;
	}

	public void setListaArchivosEliminados(List<ArchivoLaboratorio> listaArchivosEliminados) {
		this.listaArchivosEliminados = listaArchivosEliminados;
	}

	public ArchivoLaboratorio getArchivoLaboratorioSeleccionado() {
		return archivoLaboratorioSeleccionado;
	}

	public void setArchivoLaboratorioSeleccionado(ArchivoLaboratorio archivoLaboratorioSeleccionado) {
		this.archivoLaboratorioSeleccionado = archivoLaboratorioSeleccionado;
	}

	public Boolean getEsCoordinadorLabActual() {
		return esCoordinadorLabActual;
	}

	public void setEsCoordinadorLabActual(Boolean esCoordinadorLabActual) {
		this.esCoordinadorLabActual = esCoordinadorLabActual;
	}

	public List<LaboratorioSolicitud> getListaSolicitudes() {
		return listaSolicitudes;
	}

	public void setListaSolicitudes(List<LaboratorioSolicitud> listaSolicitudes) {
		this.listaSolicitudes = listaSolicitudes;
	}

	public List<LaboratorioSolicitud> getListaSolicitudesFiltradas() {
		return listaSolicitudesFiltradas;
	}

	public void setListaSolicitudesFiltradas(List<LaboratorioSolicitud> listaSolicitudesFiltradas) {
		this.listaSolicitudesFiltradas = listaSolicitudesFiltradas;
	}

	public List<LaboratorioDetalleEquipos> getListaEquiposEliminar() {
		return listaEquiposEliminar;
	}

	public void setListaEquiposEliminar(List<LaboratorioDetalleEquipos> listaEquiposEliminar) {
		this.listaEquiposEliminar = listaEquiposEliminar;
	}

	public Boolean getPuedeVerHVEquiposDadosBaja() {
		return puedeVerHVEquiposDadosBaja;
	}

	public void setPuedeVerHVEquiposDadosBaja(Boolean puedeVerHVEquiposDadosBaja) {
		this.puedeVerHVEquiposDadosBaja = puedeVerHVEquiposDadosBaja;
	}

	public void setBien(Bien bien) {
		this.bien = bien;
	}

	public void setEstadoFisicoItem(SelectItem[] estadoFisicoItem) {
		this.estadoFisicoItem = estadoFisicoItem;
	}

	public void setEstadoFisicoCensoItem(SelectItem[] estadoFisicoCensoItem) {
		this.estadoFisicoCensoItem = estadoFisicoCensoItem;
	}

	public void setMantenimientoItem(SelectItem[] mantenimientoItem) {
		this.mantenimientoItem = mantenimientoItem;
	}

	public void setCalibracionItem(SelectItem[] calibracionItem) {
		this.calibracionItem = calibracionItem;
	}

	public void setMostrarEquipos(Boolean mostrarEquipos) {
		this.mostrarEquipos = mostrarEquipos;
	}

	public void setEstadoFisicoItemTabla(SelectItem[] estadoFisicoItemTabla) {
		this.estadoFisicoItemTabla = estadoFisicoItemTabla;
	}

	public void setEstadoFisicoCensoItemTabla(SelectItem[] estadoFisicoCensoItemTabla) {
		this.estadoFisicoCensoItemTabla = estadoFisicoCensoItemTabla;
	}

	public void setMantenimientoItemTabla(SelectItem[] mantenimientoItemTabla) {
		this.mantenimientoItemTabla = mantenimientoItemTabla;
	}

	public void setCalibracionItemTabla(SelectItem[] calibracionItemTabla) {
		this.calibracionItemTabla = calibracionItemTabla;
	}

	public LaboratorioSolicitud getSolicitudSeleccionada() {
		return solicitudSeleccionada;
	}

	public void setSolicitudSeleccionada(LaboratorioSolicitud solicitudSeleccionada) {
		this.solicitudSeleccionada = solicitudSeleccionada;
	}
	
}
