package co.edu.unal.hermes.vista.laboratorios;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.event.SelectEvent;

import co.edu.unal.hermes.modelo.Convenio;
import co.edu.unal.hermes.modelo.Empresa;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.laboratorios.ArchivoLaboratorio;
import co.edu.unal.hermes.modelo.laboratorios.InsumoLaboratorio;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleEnsayosServicios;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleEquipos;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleInsumos;

public class ManejadorLaboratoriosRiesgos extends ManejadorLaboratorios {

	private SelectItem[] estadoInstalacionesFisicasItem;
	private SelectItem[] estadoClimatizacionItem;
	private SelectItem[] estadoVentilacionItem;
	private SelectItem[] estadoIluminacionItem;
	private SelectItem[] estadoRuidoItem;
	
	protected List<LaboratorioDetalleInsumos> listaSustanciasControladas;
	protected List<LaboratorioDetalleInsumos> listaSustanciasControladasFiltradas;
	protected Boolean cambiosEnSustancias;
	protected LaboratorioDetalleInsumos laboratorioDetInsumoSustControladaSeleccionada;
	protected String nombreSustancia;
	protected List<InsumoLaboratorio> insumosEncontrados;
	
	private Long idSustanciaSeleccionada;
	private List<SelectItem> sustanciasItems;
	private String mes;
	private Double consumoSustancia;
	private String almacenamiento;
	private List<SelectItem> mesesItems;
	private String annio;
	private List<SelectItem> annioItems;
	private Long actividadManejoSeleccionada;
	
	protected List<LaboratorioDetalleInsumos> listaSustanciasEliminar;
	
	List<InsumoLaboratorio> listaInsumosConsulta;
	private SelectItem[] actividadManejoItem;
	private String nombreProveedor;
	private Empresa proveedor;
	private String descActividadConsumo;
	private String descAlmacenamiento;
	private SelectItem[] unidadMedidaSustanciaItem;
	private SelectItem[] unidadconcentracionSustanciaItem;
	private Double valorConcentracionSustancia;
	private Long unidadMedidaSustanciaSeleccionada;
	private Long unidadConcentracionSeleccionada;
	
	// Editar
	private String tipoOperación;
	protected LaboratorioDetalleInsumos sustanciaSelEditar;

	public ManejadorLaboratoriosRiesgos() {
		idManejador = RIESGOS;
		
		nombreSustancia = "";
		cambiosEnSustancias = false;
		
		listaSustanciasEliminar = new ArrayList<LaboratorioDetalleInsumos>();
		listaInsumosConsulta = new ArrayList<InsumoLaboratorio>();

		estadoInstalacionesFisicasItem = servicioGeneral
				.retornaSelectItemArregloDeHijosDeTipos(Tipos.TIPOS_ESTADOS);
		estadoClimatizacionItem = servicioGeneral
				.retornaSelectItemArregloDeHijosDeTipos(Tipos.TIPOS_ESTADOS);
		estadoVentilacionItem = servicioGeneral
				.retornaSelectItemArregloDeHijosDeTipos(Tipos.TIPOS_ESTADOS);
		estadoIluminacionItem = servicioGeneral
				.retornaSelectItemArregloDeHijosDeTipos(Tipos.TIPOS_ESTADOS);
		estadoRuidoItem = servicioGeneral
				.retornaSelectItemArregloDeHijosDeTipos(Tipos.TIPOS_ESTADOS);
		
		actividadManejoItem = servicioGeneral
				.retornaSelectItemArregloDeHijosDeTipos(Tipos.TIPO_LAB_SUSTANCIAS_ACTIVIDAD_MANEJO);
		unidadMedidaSustanciaItem = servicioGeneral
				.retornaSelectItemArregloDeHijosDeTipos(Tipos.UNIDADES_MEDIDA_INSUMOS_LABORATORIO);
		unidadconcentracionSustanciaItem = servicioGeneral
				.retornaSelectItemArregloDeHijosDeTipos(Tipos.TIPOS_LAB_SUSTANCIAS_UNIDAD_CONCENTRACION);

		if (laboratorioActual.getEstadoClimatizacion() == null) {
			laboratorioActual.setEstadoClimatizacion(Tipos.TIPOS_ESTADOS);

			laboratorioActual.setEstadoIluminacion(Tipos.TIPOS_ESTADOS);
			laboratorioActual.setEstadoInstalacionesFisicas(Tipos.TIPOS_ESTADOS);
			laboratorioActual.setEstadoRuido(Tipos.TIPOS_ESTADOS);
			laboratorioActual.setEstadoVentilacion(Tipos.TIPOS_ESTADOS);
		}
		
		//Inicializa tipo operacion - Creacion de sustancia
		tipoOperación = "C";
		
		cargarListaSustancias();
		cargarlistaInsumos();
		cargarListaMeses();
		cargarListaAños();

	}
	
	public void handleSelectProveedor(SelectEvent event) {
		String nombre = (String) event.getObject();
		proveedor = servicioGeneral.buscarEmpresaXNombre(nombre);
		mensajeInfo("Proveedor seleccionado: " + proveedor.getNombre());
	}
	
	public void cargarListaSustancias()
	{
		listaSustanciasControladas = new ArrayList<LaboratorioDetalleInsumos>();
		String hql = "from LaboratorioDetalleInsumos WHERE laboratorio = '"+ laboratorioActual.getId() + "' ORDER BY insumo,mes,annio";
		listaSustanciasControladas = servicioGeneral.obtenerObjetos(LaboratorioDetalleInsumos.class, hql);
	}
	
	public void cargarlistaInsumos() {
		sustanciasItems = new ArrayList<SelectItem>();
		String hql1 = "FROM InsumoLaboratorio IL WHERE IL.activo = 1 ORDER BY IL.nombre";
		System.out.println("hql1:" + hql1);
		listaInsumosConsulta = servicioGeneral.obtenerObjetos(InsumoLaboratorio.class,hql1);
		
		for (int i = 0; i < listaInsumosConsulta.size(); i++) {
			InsumoLaboratorio insumo = (InsumoLaboratorio) listaInsumosConsulta.get(i);
			sustanciasItems.add(new SelectItem(insumo.getId(), insumo.getCas() +  " | " + insumo.getNombre() + " | " + insumo.getUnidadDeMedida().getNombre() + (insumo.getControlado() ? "  | (Controlada)" : "")));
		}

	}
	
	public void cargarListaMeses() {
		mesesItems = new ArrayList<SelectItem>();
		mesesItems.add(new SelectItem("Enero","Enero"));
		mesesItems.add(new SelectItem("Febrero","Febrero"));
		mesesItems.add(new SelectItem("Marzo","Marzo"));
		mesesItems.add(new SelectItem("Abril","Abril"));
		mesesItems.add(new SelectItem("Mayo","Mayo"));
		mesesItems.add(new SelectItem("Junio","Junio"));
		mesesItems.add(new SelectItem("Julio","Julio"));
		mesesItems.add(new SelectItem("Agosto","Agosto"));
		mesesItems.add(new SelectItem("Septiembre","Septiembre"));
		mesesItems.add(new SelectItem("Octubre","Octubre"));
		mesesItems.add(new SelectItem("Noviembre","Noviembre"));
		mesesItems.add(new SelectItem("Diciembre","Diciembre"));
	}
	
	public void cargarListaAños()
	{
		annioItems = new ArrayList<SelectItem>();
		Calendar fecha = Calendar.getInstance();
        int añoActual = fecha.get(Calendar.YEAR);
        
        while(añoActual >= 2000){
        	annioItems.add(new SelectItem(añoActual+"",añoActual+""));
        	añoActual--;
        }
	}
	
	public boolean sustanciaExiste()
	{
		boolean existe = false;
		if(listaSustanciasControladas != null && listaSustanciasControladas.size() > 0)
		{	
			for(int i=0;i<listaSustanciasControladas.size();i++)
			{
				LaboratorioDetalleInsumos labDettale = (LaboratorioDetalleInsumos) listaSustanciasControladas.get(i);
				if(labDettale.getInsumo().getId().equals(idSustanciaSeleccionada) && labDettale.getMes().equals(mes) && labDettale.getAnnio().equals(annio) && labDettale.getActividadManejo().getId().equals(actividadManejoSeleccionada))
					existe = true;
			}
		}
		return existe;
	}
	
	public void agregarSustancia() {

        boolean error = false;
        LaboratorioDetalleInsumos sustanciaSeleccionada;
//
        if (idSustanciaSeleccionada == 0) {
            error = true;
            mensajeError("Debe seleccionar una sustancia de la lista");
        }
        
        if (actividadManejoSeleccionada == 0) {
            error = true;
            mensajeError("Debe seleccionar una actividad de manejo de la lista");
        } else {
    		if (actividadManejoSeleccionada.equals(Tipos.TIPO_LAB_SUSTANCIAS_ACTIVIDAD_MANEJO_Ingreso) && esCadenaVacia(nombreProveedor)) {
    			error = true;
                mensajeError("Debe ingresar el nombre del proveedor");
    		}
    		
    		if (actividadManejoSeleccionada.equals(Tipos.TIPO_LAB_SUSTANCIAS_ACTIVIDAD_MANEJO_Consumo) && esCadenaVacia(descActividadConsumo)) {
    			error = true;
                mensajeError("Debe ingresar la descripción de la actividad de consumo");
    		}
    		
    		if (actividadManejoSeleccionada.equals(Tipos.TIPO_LAB_SUSTANCIAS_ACTIVIDAD_MANEJO_Almacenamiento) && esCadenaVacia(descAlmacenamiento)) {
    			error = true;
                mensajeError("Debe ingresar el tipo de almacenamiento");
    		}
    	}
        
        if (unidadConcentracionSeleccionada == 0) {
            error = true;
            mensajeError("Debe seleccionar una unidad de concentración de la lista");
        }
        
        if (esCadenaVacia(consumoSustancia.toString()) || consumoSustancia <= 0D) {
          error = true;
          mensajeError("El valor del consumo no puede estar vacio y debe ser mayor a 0.0");
        }
        
        if (esCadenaVacia(valorConcentracionSustancia.toString()) || valorConcentracionSustancia <= 0D) {
            error = true;
            mensajeError("El valor de la concentración no puede estar vacio y debe ser mayor a 0.0");
          }
        
        if (mes.equals("0")) {
        	error = true;
            mensajeError("Debe seleccionar un mes de la lista");
        }
        
        if (annio.equals("0")) {
        	error = true;
            mensajeError("Debe seleccionar un año de la lista");
        }
        
        if(sustanciaExiste() && tipoOperación.equals("C")){
        	error = true;
            mensajeError("La sustancia seleccionada ya fue agregada para el periodo MES/AÑO y actividad de manejo seleccionados");
        }
        
        if (!error) {
            try {
            	
            	Calendar fecha = Calendar.getInstance();
            	
            	String hql1 = "FROM InsumoLaboratorio IL WHERE IL.id ="+idSustanciaSeleccionada+" ORDER BY IL.nombre";
        		System.out.println("hql1:" + hql1);
        		List<InsumoLaboratorio> listaInsumos = servicioGeneral.obtenerObjetos(InsumoLaboratorio.class,hql1);
        		InsumoLaboratorio insumo;
        		
        		List<Tipos> actividadManejoTipos = servicioGeneral.obtenerObjetoXID(Tipos.class,actividadManejoSeleccionada.toString());
        		Tipos actividadManejoSel =  new Tipos();
                if (!esListaVacia(actividadManejoTipos)) {
                    actividadManejoSel = actividadManejoTipos.get(0);
                }
                
                Tipos unidadConcentracion = obtenerTipoXid(unidadConcentracionSeleccionada);
        		
				if(listaInsumos.size() > 0)
				{
        			insumo = listaInsumos.get(0);
        			
        			if(tipoOperación.equals("C"))
        			{	
		                sustanciaSeleccionada = new LaboratorioDetalleInsumos();
		                sustanciaSeleccionada.setInsumo(insumo);
		                sustanciaSeleccionada.setLaboratorio(laboratorioActual);
		                sustanciaSeleccionada.setConsumo(consumoSustancia);
		                sustanciaSeleccionada.setMes(mes);
		                sustanciaSeleccionada.setAnnio(annio);
		                sustanciaSeleccionada.setAlmacenamiento(almacenamiento);
		                sustanciaSeleccionada.setUnidadConcentracion(unidadConcentracion);
		                sustanciaSeleccionada.setValorConcentracion(valorConcentracionSustancia);
		                sustanciaSeleccionada.setActividadManejo(actividadManejoSel);
		                
		                if (actividadManejoSeleccionada.equals(Tipos.TIPO_LAB_SUSTANCIAS_ACTIVIDAD_MANEJO_Ingreso))
		                	sustanciaSeleccionada.setProveedor(proveedor);
		                
		                if (actividadManejoSeleccionada.equals(Tipos.TIPO_LAB_SUSTANCIAS_ACTIVIDAD_MANEJO_Consumo))
		                	sustanciaSeleccionada.setDescActividadConsumo(descActividadConsumo);
		                
		                if (actividadManejoSeleccionada.equals(Tipos.TIPO_LAB_SUSTANCIAS_ACTIVIDAD_MANEJO_Almacenamiento))
		                	sustanciaSeleccionada.setAlmacenamientoDetalle(descAlmacenamiento);	                
		                
		                sustanciaSeleccionada.setFechaRegistro(fecha.getTime());
		                listaSustanciasControladas.add(sustanciaSeleccionada);
		                
		                FacesMessage msg = new FacesMessage(
		        				FacesMessage.SEVERITY_INFO, "Sustancia ["+ insumo.getNombre() +"] agregada con éxito. No olvide guardar la información por medio de alguno de los botones en la parte inferior.", "");
		        		FacesContext.getCurrentInstance().addMessage("botonAgregarSustancia", msg);
        			} else {
        				sustanciaSelEditar.setInsumo(insumo);
        				sustanciaSelEditar.setLaboratorio(laboratorioActual);
        				sustanciaSelEditar.setConsumo(consumoSustancia);
        				sustanciaSelEditar.setMes(mes);
        				sustanciaSelEditar.setAnnio(annio);
        				sustanciaSelEditar.setAlmacenamiento(almacenamiento);
        				sustanciaSelEditar.setUnidadConcentracion(unidadConcentracion);
        				sustanciaSelEditar.setValorConcentracion(valorConcentracionSustancia);
        				sustanciaSelEditar.setActividadManejo(actividadManejoSel);
		                
		                if (actividadManejoSeleccionada.equals(Tipos.TIPO_LAB_SUSTANCIAS_ACTIVIDAD_MANEJO_Ingreso)) {
		                	sustanciaSelEditar.setProveedor(proveedor);
		                	sustanciaSelEditar.setDescActividadConsumo(null);
		                	sustanciaSelEditar.setAlmacenamientoDetalle(null);
		                }
		                
		                if (actividadManejoSeleccionada.equals(Tipos.TIPO_LAB_SUSTANCIAS_ACTIVIDAD_MANEJO_Consumo)) {
			                sustanciaSelEditar.setProveedor(null);
		                	sustanciaSelEditar.setDescActividadConsumo(descActividadConsumo);
		                	sustanciaSelEditar.setAlmacenamientoDetalle(null);
		                }	
		                
		                if (actividadManejoSeleccionada.equals(Tipos.TIPO_LAB_SUSTANCIAS_ACTIVIDAD_MANEJO_Almacenamiento)) {
		                	sustanciaSelEditar.setProveedor(null);
	                		sustanciaSelEditar.setDescActividadConsumo(null);
		                	sustanciaSelEditar.setAlmacenamientoDetalle(descAlmacenamiento);
		                }
		                
		                sustanciaSelEditar.setFechaActualizacion(fecha.getTime());
		                
		                tipoOperación = "C";
		                
		                FacesMessage msg = new FacesMessage(
		                		FacesMessage.SEVERITY_INFO, "Sustancia ["+ insumo.getNombre() +"] editada con éxito. No olvide guardar la información por medio de alguno de los botones en la parte inferior.", "");
		        		FacesContext.getCurrentInstance().addMessage("botonEditarSustancia", msg);
        			}
        			
	                inicializarValoresSustanciasControladas();
				}
				else
				{
					System.out.println("No se encontró el Insumo en tabla HER_INSUMO_LABORATORIO");
					return;
				}

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
	
	public void editarSustanciaControlada() {
		tipoOperación = "E";
		
		if(!esNulo(sustanciaSelEditar.getInsumo()) && !esNulo(sustanciaSelEditar.getActividadManejo())) {
			idSustanciaSeleccionada = sustanciaSelEditar.getInsumo().getId();
			actividadManejoSeleccionada = sustanciaSelEditar.getActividadManejo().getId();
			nombreProveedor = !esNulo(sustanciaSelEditar.getProveedor()) ? sustanciaSelEditar.getProveedor().getNombre() : null;
			proveedor = sustanciaSelEditar.getProveedor();
			descActividadConsumo = sustanciaSelEditar.getDescActividadConsumo();
			descAlmacenamiento = sustanciaSelEditar.getAlmacenamientoDetalle();
			consumoSustancia = sustanciaSelEditar.getConsumo();
			unidadConcentracionSeleccionada = !esNulo(sustanciaSelEditar.getUnidadConcentracion()) ? sustanciaSelEditar.getUnidadConcentracion().getId() : null;
			valorConcentracionSustancia = sustanciaSelEditar.getValorConcentracion();
			mes = sustanciaSelEditar.getMes();
			annio = sustanciaSelEditar.getAnnio();
		}
		
		FacesMessage msg = new FacesMessage(
				FacesMessage.SEVERITY_INFO, "Sustancia seleccionada para ser editada. Esto lo puede realizar en el panel superior.", "");
		FacesContext.getCurrentInstance().addMessage("botonEditarSustancia", msg);
	}
	
	public void cancelarModoEdicion() {
		tipoOperación = "C";
		inicializarValoresSustanciasControladas();
		FacesMessage msg = new FacesMessage(
				FacesMessage.SEVERITY_INFO, "Ha cancelado el modo edicion. Si desea editar una sustancia puede dar clic en el boton editar ubicado en la tabla de la parte inferior.", "");
		FacesContext.getCurrentInstance().addMessage("botonAgregarSustancia", msg);
	}
	
	public void descargarArchivoEtiqueta()
	{		
		ArrayList<ArchivoLaboratorio> 
			listaArchivos= (ArrayList<ArchivoLaboratorio>) 
				servicioGeneral.obtenerArchivosInsumoXidInsumoXidTipoArchivo(
						laboratorioDetInsumoSustControladaSeleccionada.getInsumo().getId(),
						Tipos.TIPOS_ARCHIVOS_INSUMO_LABORATORIO_Etiqueta
				);
		ArchivoLaboratorio archivo = listaArchivos.size() > 0 ? (ArchivoLaboratorio) listaArchivos.get(0) : null;
		
		if(!esNulo(archivo))
			descargarArchivoLaboratorios(archivo);
		else
			mensajeError("No existe archivo de la etiqueta cargado en el sistema para la sustancia seleccionada [ " + laboratorioDetInsumoSustControladaSeleccionada.getInsumo().getNombre() + " ]");
	}
	
	public void descargarArchivoFichaSeguridad()
	{		
		ArrayList<ArchivoLaboratorio> 
			listaArchivos= (ArrayList<ArchivoLaboratorio>) 
				servicioGeneral.obtenerArchivosInsumoXidInsumoXidTipoArchivo(
						laboratorioDetInsumoSustControladaSeleccionada.getInsumo().getId(),
						Tipos.TIPOS_ARCHIVOS_INSUMO_LABORATORIO_Ficha_Datos_Seguridad
				);
		ArchivoLaboratorio archivo = listaArchivos.size() > 0 ? (ArchivoLaboratorio) listaArchivos.get(0) : null;
		
		if(!esNulo(archivo))
			descargarArchivoLaboratorios(archivo);
		else
			mensajeError("No existe archivo de ficha de seguridad cargado para la sustancia seleccionada [ " + laboratorioDetInsumoSustControladaSeleccionada.getInsumo().getNombre() + " ]");
	}
	
	public void descargarArchivoLaboratorios(ArchivoLaboratorio archivoLaboratorioDescargar) {
		String idArchivo = archivoLaboratorioDescargar.getId().toString();
		String nombreArchivo = archivoLaboratorioDescargar.getNombreArchivo();
		descargarArchivoGenerico(ArchivoLaboratorio.TABLA_ARCHIVOS_LABORATORIOS, idArchivo, nombreArchivo);
	}
	
	public void cambiosEnSustancias(ValueChangeEvent event) {
		System.out.println("Se realizó un cambio de: " + event.getOldValue()+ " a: " + event.getNewValue());
		cambiosEnSustancias = true;
	}
	
	public void eliminarSustanciaControlada() {
		listaSustanciasControladas.remove(laboratorioDetInsumoSustControladaSeleccionada);
		listaSustanciasEliminar.add(laboratorioDetInsumoSustControladaSeleccionada);
		listaSustanciasControladasFiltradas = null;
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
		
		try {
			if(laboratorioActual.getUsaSustanciasControladas()) {
				// Se eliminan las sustancias:
				for (LaboratorioDetalleInsumos ldi : listaSustanciasEliminar) {
					String sql = "DELETE HER_LABORATORIO_DET_INSUMO WHERE LDI_ID = "+ ldi.getId();
					servicioGeneral.eliminar(sql);
				}

				// guardar detalles
				if (listaSustanciasControladas != null) {
					for (LaboratorioDetalleInsumos d : listaSustanciasControladas) {
						d.setLaboratorio(laboratorioActual);
						servicioGeneral.guardarObjeto(d);
					}
				}
			} else {
				//Borrar sustancias agregadas
				for (LaboratorioDetalleInsumos ldi : listaSustanciasControladas) {
					String sql = "DELETE HER_LABORATORIO_DET_INSUMO WHERE LDI_ID = "+ ldi.getId();
					servicioGeneral.eliminar(sql);
				}
				
				//Borrar sustancias en la lista de eliminadas
				for (LaboratorioDetalleInsumos ldi : listaSustanciasEliminar) {
					String sql = "DELETE HER_LABORATORIO_DET_INSUMO WHERE LDI_ID = "+ ldi.getId();
					servicioGeneral.eliminar(sql);
				}
			}
		}
		
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		calcularCompletitud();
	}
	
	public void postProcessXLS(Object document) {
		HSSFWorkbook wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow header = sheet.getRow(0);
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		wb.setSheetName(0, "SUSTANCIAS CONTROLADAS");
		int numeroColumnas = header.getPhysicalNumberOfCells();
		int numeroFilas = sheet.getPhysicalNumberOfRows();
		boolean filaTotalesCreada = false;

		// fija el estilo al encabezado
		for (int i = 0; i < numeroColumnas; i++) {
			header.getCell(i).setCellStyle(cellStyle);
			//sheet.autoSizeColumn(i);
		}

		// Inmovilizar fila superior:
		sheet.createFreezePane(0, 1);

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
			return "laboratorioGestion";
		} else {
			return null;
		}
	}
	
	public void inicializarValoresSustanciasControladas(){
		idSustanciaSeleccionada = 0L;
		actividadManejoSeleccionada = 0L;
		nombreProveedor = null;
		descActividadConsumo = null;
		descAlmacenamiento = null;
		consumoSustancia = null;
		mes = "0";
		annio = "0";
		almacenamiento = null;
//		unidadMedidaSustanciaSeleccionada = 0L;
		unidadConcentracionSeleccionada = 0L;
		valorConcentracionSustancia = null;
	}

	public Boolean validar() {
		boolean validar = true;
		listaMensajesValidacion = new ArrayList<String>();	
		return validar;
	}

	/**
	 * @return the estadoInstalacionesFisicasItem
	 */
	public SelectItem[] getEstadoInstalacionesFisicasItem() {
		return estadoInstalacionesFisicasItem;
	}

	/**
	 * @return the estadoClimatizacion
	 */
	public SelectItem[] getEstadoClimatizacionItem() {
		return estadoClimatizacionItem;
	}

	/**
	 * @return the estadoVentilacionItem
	 */
	public SelectItem[] getEstadoVentilacionItem() {
		return estadoVentilacionItem;
	}

	/**
	 * @return the estadoIluminacionItem
	 */
	public SelectItem[] getEstadoIluminacionItem() {
		return estadoIluminacionItem;
	}

	/**
	 * @return the estadoRuidoItem
	 */
	public SelectItem[] getEstadoRuidoItem() {
		return estadoRuidoItem;
	}

	public List<LaboratorioDetalleInsumos> getListaSustanciasControladas() {
		return listaSustanciasControladas;
	}

	public void setListaSustanciasControladas(
			List<LaboratorioDetalleInsumos> listaSustanciasControladas) {
		this.listaSustanciasControladas = listaSustanciasControladas;
	}

	public Boolean getCambiosEnSustancias() {
		return cambiosEnSustancias;
	}

	public void setCambiosEnSustancias(Boolean cambiosEnSustancias) {
		this.cambiosEnSustancias = cambiosEnSustancias;
	}

	public LaboratorioDetalleInsumos getLaboratorioDetInsumoSustControladaSeleccionada() {
		return laboratorioDetInsumoSustControladaSeleccionada;
	}

	public void setLaboratorioDetInsumoSustControladaSeleccionada(
			LaboratorioDetalleInsumos laboratorioDetInsumoSustControladaSeleccionada) {
		this.laboratorioDetInsumoSustControladaSeleccionada = laboratorioDetInsumoSustControladaSeleccionada;
	}

	public String getNombreSustancia() {
		return nombreSustancia;
	}

	public void setNombreSustancia(String nombreSustancia) {
		this.nombreSustancia = nombreSustancia;
	}

	public List<InsumoLaboratorio> getInsumosEncontrados() {
		return insumosEncontrados;
	}

	public void setInsumosEncontrados(List<InsumoLaboratorio> insumosEncontrados) {
		this.insumosEncontrados = insumosEncontrados;
	}



	public Long getIdSustanciaSeleccionada() {
		return idSustanciaSeleccionada;
	}



	public void setIdSustanciaSeleccionada(Long idSustanciaSeleccionada) {
		this.idSustanciaSeleccionada = idSustanciaSeleccionada;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public List<SelectItem> getSustanciasItems() {
		return sustanciasItems;
	}

	public void setSustanciasItems(List<SelectItem> sustanciasItems) {
		this.sustanciasItems = sustanciasItems;
	}

	public List<SelectItem> getMesesItems() {
		return mesesItems;
	}

	public void setMesesItems(List<SelectItem> mesesItems) {
		this.mesesItems = mesesItems;
	}

	public String getAnnio() {
		return annio;
	}

	public void setAnnio(String annio) {
		this.annio = annio;
	}

	public List<SelectItem> getAnnioItems() {
		return annioItems;
	}

	public void setAnnioItems(List<SelectItem> annioItems) {
		this.annioItems = annioItems;
	}

	public String getAlmacenamiento() {
		return almacenamiento;
	}

	public void setAlmacenamiento(String almacenamiento) {
		this.almacenamiento = almacenamiento;
	}

	public Double getConsumoSustancia() {
		return consumoSustancia;
	}

	public void setConsumoSustancia(Double consumoSustancia) {
		this.consumoSustancia = consumoSustancia;
	}

	public List<LaboratorioDetalleInsumos> getListaSustanciasEliminar() {
		return listaSustanciasEliminar;
	}

	public void setListaSustanciasEliminar(
			List<LaboratorioDetalleInsumos> listaSustanciasEliminar) {
		this.listaSustanciasEliminar = listaSustanciasEliminar;
	}

	public List<InsumoLaboratorio> getListaInsumosConsulta() {
		return listaInsumosConsulta;
	}

	public void setListaInsumosConsulta(List<InsumoLaboratorio> listaInsumosConsulta) {
		this.listaInsumosConsulta = listaInsumosConsulta;
	}

	public SelectItem[] getActividadManejoItem() {
		return actividadManejoItem;
	}

	public void setActividadManejoItem(SelectItem[] actividadManejoItem) {
		this.actividadManejoItem = actividadManejoItem;
	}

	public Long getActividadManejoSeleccionada() {
		return actividadManejoSeleccionada;
	}

	public void setActividadManejoSeleccionada(Long actividadManejoSeleccionada) {
		this.actividadManejoSeleccionada = actividadManejoSeleccionada;
	}

	public String getNombreProveedor() {
		return nombreProveedor;
	}

	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}

	public Empresa getProveedor() {
		return proveedor;
	}

	public void setProveedor(Empresa proveedor) {
		this.proveedor = proveedor;
	}

	public String getDescActividadConsumo() {
		return descActividadConsumo;
	}

	public void setDescActividadConsumo(String descActividadConsumo) {
		this.descActividadConsumo = descActividadConsumo;
	}

	public String getDescAlmacenamiento() {
		return descAlmacenamiento;
	}

	public void setDescAlmacenamiento(String descAlmacenamiento) {
		this.descAlmacenamiento = descAlmacenamiento;
	}

	public List<LaboratorioDetalleInsumos> getListaSustanciasControladasFiltradas() {
		return listaSustanciasControladasFiltradas;
	}

	public void setListaSustanciasControladasFiltradas(
			List<LaboratorioDetalleInsumos> listaSustanciasControladasFiltradas) {
		this.listaSustanciasControladasFiltradas = listaSustanciasControladasFiltradas;
	}

	public SelectItem[] getUnidadMedidaSustanciaItem() {
		return unidadMedidaSustanciaItem;
	}

	public void setUnidadMedidaSustanciaItem(SelectItem[] unidadMedidaSustanciaItem) {
		this.unidadMedidaSustanciaItem = unidadMedidaSustanciaItem;
	}

	public SelectItem[] getUnidadconcentracionSustanciaItem() {
		return unidadconcentracionSustanciaItem;
	}

	public void setUnidadconcentracionSustanciaItem(SelectItem[] unidadconcentracionSustanciaItem) {
		this.unidadconcentracionSustanciaItem = unidadconcentracionSustanciaItem;
	}

	public Double getValorConcentracionSustancia() {
		return valorConcentracionSustancia;
	}

	public void setValorConcentracionSustancia(Double valorConcentracionSustancia) {
		this.valorConcentracionSustancia = valorConcentracionSustancia;
	}

	public Long getUnidadMedidaSustanciaSeleccionada() {
		return unidadMedidaSustanciaSeleccionada;
	}

	public void setUnidadMedidaSustanciaSeleccionada(Long unidadMedidaSustanciaSeleccionada) {
		this.unidadMedidaSustanciaSeleccionada = unidadMedidaSustanciaSeleccionada;
	}

	public Long getUnidadConcentracionSeleccionada() {
		return unidadConcentracionSeleccionada;
	}

	public void setUnidadConcentracionSeleccionada(Long unidadConcentracionSeleccionada) {
		this.unidadConcentracionSeleccionada = unidadConcentracionSeleccionada;
	}

	public void setEstadoInstalacionesFisicasItem(SelectItem[] estadoInstalacionesFisicasItem) {
		this.estadoInstalacionesFisicasItem = estadoInstalacionesFisicasItem;
	}

	public void setEstadoClimatizacionItem(SelectItem[] estadoClimatizacionItem) {
		this.estadoClimatizacionItem = estadoClimatizacionItem;
	}

	public void setEstadoVentilacionItem(SelectItem[] estadoVentilacionItem) {
		this.estadoVentilacionItem = estadoVentilacionItem;
	}

	public void setEstadoIluminacionItem(SelectItem[] estadoIluminacionItem) {
		this.estadoIluminacionItem = estadoIluminacionItem;
	}

	public void setEstadoRuidoItem(SelectItem[] estadoRuidoItem) {
		this.estadoRuidoItem = estadoRuidoItem;
	}

	public String getTipoOperación() {
		return tipoOperación;
	}

	public void setTipoOperación(String tipoOperación) {
		this.tipoOperación = tipoOperación;
	}

	public LaboratorioDetalleInsumos getSustanciaSelEditar() {
		return sustanciaSelEditar;
	}

	public void setSustanciaSelEditar(LaboratorioDetalleInsumos sustanciaSelEditar) {
		this.sustanciaSelEditar = sustanciaSelEditar;
	}
}
