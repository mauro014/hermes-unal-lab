package co.edu.unal.hermes.vista.laboratorios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.Pais;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.laboratorios.*;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;

/**
 * @author dgbenitezc
 */
public class ManejadorLaboratoriosMetrologia extends ManejadorLaboratorios {

	// METROLOGIA

	// DATOS BASICOS
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

	private List<LaboratorioSubred> listaSubredes;
	private List<LaboratorioSubred> listaSubredesEliminar;
	private LaboratorioSubred subredEliminar;

	private List<LaboratorioActividad> listaActividades;
	private List<LaboratorioActividad> listaActividadesEliminar;
	private LaboratorioActividad actividadEliminar;

	private List<LaboratorioCapacitacion> listaCapacitaciones;
	private List<LaboratorioCapacitacion> listaCapacitacionesEliminar;
	private LaboratorioCapacitacion capacitacionEliminar;

	private List<LaboratorioMuestreo> listaMuestreos;
	private List<LaboratorioMuestreo> listaMuestreosEliminar;
	private LaboratorioMuestreo muestreoEliminar;

	private List<LaboratorioSistemaGestion> listaSistemasGestion;
	private List<LaboratorioSistemaGestion> listaSistemasGestionEliminar;
	private LaboratorioSistemaGestion sistemaGestionEliminar;

	// Medidas Químicas
	private ArrayList<LaboratorioMetroMedidaQuimica> listaMedidasQuimicas;
	private ArrayList<LaboratorioMetroMedidaQuimica> listaMedidasQuimicasEliminar;
	private LaboratorioMetroMedidaQuimica medidaQuimicaSeleccionadaTabla;

	private String sectorSel;
	private String matrizSel;
	private String mensurandoSel;
	private String tecnicaSel;
	private String concentracionSel;
	private String metodoValidadoSelDescrip;
	private String[] metodoValidadoSelCheckMenu;

	private String normaMetodoReferencia;
	private Boolean materialesReferencia;

	private SelectItem[] selectItemSector;
	private SelectItem[] selectItemMatriz;
	private SelectItem[] selectItemMensurando;
	private SelectItem[] selectItemTecnica;
	private SelectItem[] selectItemConcentracion;
	private SelectItem[] selectItemMetodoValidado;

	// Medidas Microbiológicas
	private ArrayList<LaboratorioMetroMedidaMicro> listaMedidasMicrobilogicas;
	private ArrayList<LaboratorioMetroMedidaMicro> listaMedidasMicrobilogicasEliminar;
	private LaboratorioMetroMedidaMicro medidaMicrobiologicaSeleccionadaTabla;

	private String materialReferenciaCepaUtilizadaSel;
	private String sectorMicroSel;
	private String matrizMicroSel;
	private String tipoEnsayoSel;
	private String nombreEnsayoMicroSel;
	private String tecnicaMicroSel;
	private String normaTecnicaSel;
	private String materialReferenciaCepaCertificadaSel;
	private String[] validacionSelCheckMenu;
	private String validacionDescSel;
	private String[] controCalidadMediosCultivoSelCheckMenu;
	private String controCalidadMediosCultivoDescSel;
	private String[] condicionesAmbientalesSelCheckMenu;
	private String condicionesAmbientalesDescSel;

	private SelectItem[] selectItemTipoEnsayo;
	private SelectItem[] selectItemNombreEnsayo;
	private SelectItem[] selectItemTecnicaMicro;
	private SelectItem[] selectItemNormaTecnica;
	private SelectItem[] selectItemMateCepaCertificada;
	private SelectItem[] selectItemValidacion;
	private SelectItem[] selectItemControlCalMedioCultivo;
	private SelectItem[] selectItemCondicionesAmbientales;

	// Medidas Físicas
	private ArrayList<LaboratorioMetroMedidaFisica> listaMedidasFisicas;
	private ArrayList<LaboratorioMetroMedidaFisica> listaMedidasFisicasEliminar;
	private LaboratorioMetroMedidaFisica medidaFisicaSeleccionadaTabla;

	private String descripcionMensurandoSel;
	private String magnitudFisicaSel;
	private String campoAplicacionSel;
	private String intervaloPuntoMedMinimoSel;
	private String intervaloPuntoMedMaximoSel;
	private String unidadesIntervaloPrefijoSel;
	private String unidadesIntervaloUnidadSel;
	private String unidadesIntervaloSimboloSel;
	private String incertidumbreMedicionIncertidumbreSel;
	private String incertidumbreMedicionValorSel;
	private String incertidumbreMedicionPrefijoSel;
	private String incertidumbreMedicionUnidadSel;
	private String incertidumbreMedicionSimboloSel;
	private String factorCoberturaKSel;
	private String normaDocumentosMetodosReferenciaSel;
	private Boolean acreditadoSel;
	private String entidadAcreditadoraTipoDocSel;
	private String entidadAcreditadoraNumDocSel;
	private String entidadAcreditadoraNombreSel;

	private SelectItem[] selectItemMagnitudFisica;
	private SelectItem[] selectItemCampoAplicacion;
	private SelectItem[] selectItemUnidIntPrefijo;
	private SelectItem[] selectItemUnidIntUnidad;
	private SelectItem[] selectItemIncertMedIncert;
	private SelectItem[] selectItemIncertMedPrefijo;
	private SelectItem[] selectItemIncertMedUnidad;
	private SelectItem[] tipoDocumentoItem;

	// Ensayos Físicos
	private ArrayList<LaboratorioMetroEnsayoFisico> listaEnsayosFisicos;
	private ArrayList<LaboratorioMetroEnsayoFisico> listaEnsayosFisicosEliminar;
	private LaboratorioMetroEnsayoFisico ensayoFisicoSeleccionadoTabla;

	private String nombreEnsayoSel;
	private String productosMaterialAEnsayarSel;
	private String propiedadesMediblesSel;
	private String minimoSel;
	private String maximoSel;
	private String unidadesSel;
	private String descripcionSel;
	private String normaTecnicaProcedimientoSel;

	// Pruebas Interlaboratorios
	private ArrayList<LaboratorioMetroPruebaInterlab> listaPruebasInterlaboratorios;
	private ArrayList<LaboratorioMetroPruebaInterlab> listaPruebasInterlaboratoriosEliminar;
	private LaboratorioMetroPruebaInterlab pruebaInterlaboratorioSeleccionadaTabla;

	private Boolean participaSel;
	private String cualSel;
	private String desempenoObtenidoSel;
	private String proveedorSel;
	private String nombreProveedorPISel;
	private String tipoDocumentoProveedorPISel;
	private String identificacionProveedorPISel;

	private SelectItem[] selectItemDesempeno;
	private SelectItem[] selectItemProveedor;

	// Grupos de trabajo
	private ArrayList<LaboratorioMetroGrupoTrabajo> listaGruposTrabajo;
	private ArrayList<LaboratorioMetroGrupoTrabajo> listaGruposTrabajoEliminar;
	private LaboratorioMetroGrupoTrabajo grupoTrabajoSeleccionadoTabla;

	private String tipoGrupoSel;
	private String nombreGrupoSel;
	private String periodoParticipacionSel;

	private SelectItem[] selectItemTipoGrupo;
	private SelectItem[] selectItemPeriodoParticipacion;

	// Materiales de Referencia
	private ArrayList<LaboratorioMetroMaterialReferencia> listaMaterialesReferencia;
	private ArrayList<LaboratorioMetroMaterialReferencia> listaMaterialesReferenciaEliminar;
	private LaboratorioMetroMaterialReferencia materialReferenciaSeleccionadaTabla;

	private String nombreMaterialReferenciaSel;
	private String productorSel;
	private String proveedorMRSel;
	private String usaMaterialReferenciaSel;
	private String tipoMRSel;
	private Boolean materialDificilAdquisicionSel;
	private Boolean usoAseguraTrazabilildadMedicionSel;

	private SelectItem[] selectItemNecesitaUsaMaterialReferncia;
	private SelectItem[] selectItemTipoMaterialReferencia;

	// Productos
	private ArrayList<LaboratorioMetroProducto> listaProductos;
	private ArrayList<LaboratorioMetroProducto> listaProductosEliminar;
	private LaboratorioMetroProducto productoSeleccionadoTabla;

	private String productoSel;
	private String subredSel;
	private Boolean exportacionSel;
	private String paisSel;
	private String ciudadSel;

	private SelectItem[] selectItemPaises;

	// UI
	protected UIComponent agregarMedidaQuimica;
	protected UIComponent agregarMedidaMicro;
	protected UIComponent agregarMedidaFisica;
	protected UIComponent agregarEnsayoFisico;
	protected UIComponent agregarPruebaInterlab;
	protected UIComponent agregarGrupoTrabajo;
	protected UIComponent agregarMaterialesReferencia;
	protected UIComponent agregarProducto;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ManejadorLaboratoriosMetrologia() {
		super();
		idManejador = METRORED;

		// DATOS BASICOS
		listaSubredesEliminar = new ArrayList<LaboratorioSubred>();
		listaActividadesEliminar = new ArrayList<LaboratorioActividad>();
		listaCapacitacionesEliminar = new ArrayList<LaboratorioCapacitacion>();
		listaMuestreosEliminar = new ArrayList<LaboratorioMuestreo>();
		listaSistemasGestionEliminar = new ArrayList<LaboratorioSistemaGestion>();

		listaMedidasQuimicasEliminar = new ArrayList<LaboratorioMetroMedidaQuimica>();
		listaMedidasMicrobilogicasEliminar = new ArrayList<LaboratorioMetroMedidaMicro>();
		listaMedidasFisicasEliminar = new ArrayList<LaboratorioMetroMedidaFisica>();
		listaEnsayosFisicosEliminar = new ArrayList<LaboratorioMetroEnsayoFisico>();
		listaPruebasInterlaboratoriosEliminar = new ArrayList<LaboratorioMetroPruebaInterlab>();
		listaGruposTrabajoEliminar = new ArrayList<LaboratorioMetroGrupoTrabajo>();
		listaMaterialesReferenciaEliminar = new ArrayList<LaboratorioMetroMaterialReferencia>();
		listaProductosEliminar = new ArrayList<LaboratorioMetroProducto>();

		cargarListas();
		cargarListasParaTablas();

		materialesReferencia = false;
		acreditadoSel = false;
		participaSel = false;

		materialDificilAdquisicionSel = false;
		usoAseguraTrazabilildadMedicionSel = false;

		exportacionSel = false;

		paisSel = "00";

	}

	public void estaAcreditado() {
		System.out.println("acreditadoSel: " + acreditadoSel);
		System.out.println("participaSel: " + participaSel);
	}

	private void cargarPaises() {
		List<Pais> listaPaises = servicioGeneral.obtenerListaObjetosOrdenadosAscG(Pais.class, "nombre");
		selectItemPaises = new SelectItem[listaPaises.size()];
		for (int i = 0; i < listaPaises.size(); i++) {
			Pais p = listaPaises.get(i);
			selectItemPaises[i] = new SelectItem(p.getId(), p.getNombre());
		}
	}

	private void obtenerListaSubredes() {
		System.out.println("obtenerListaSubredes in:");
		listaSubredes = new ArrayList<LaboratorioSubred>();
		if (laboratorioActual.getId() != null) {
			String hql = "FROM LaboratorioSubred ls WHERE ls.laboratorio.id = '" + laboratorioActual.getId()
					+ "' ORDER BY ls.subred.nombre";
			System.out.println("hql:" + hql);
			List<LaboratorioSubred> lista = servicioGeneral.obtenerObjetos(LaboratorioSubred.class, hql);
			for (LaboratorioSubred lao : lista) {
				listaSubredes.add(lao);
			}
		}
		System.out.println("obtenerListaSubredes out: listaSubredes.size: " + listaSubredes.size());
	}

	private void obtenerListaActividades() {
		System.out.println("obtenerListaActividades in:");
		listaActividades = new ArrayList<LaboratorioActividad>();
		if (laboratorioActual.getId() != null) {
			String hql = "FROM LaboratorioActividad ls WHERE ls.laboratorio.id = '" + laboratorioActual.getId()
					+ "' ORDER BY ls.actividad.nombre";
			System.out.println("hql:" + hql);
			List<LaboratorioActividad> lista = servicioGeneral.obtenerObjetos(LaboratorioActividad.class, hql);
			for (LaboratorioActividad lao : lista) {
				listaActividades.add(lao);
			}
		}
		System.out.println("obtenerListaActividades out: listaActividades.size: " + listaActividades.size());
	}

	private void obtenerListaCapacitaciones() {
		System.out.println("obtenerListaCapacitaciones in:");
		listaCapacitaciones = new ArrayList<LaboratorioCapacitacion>();
		if (laboratorioActual.getId() != null) {
			String hql = "FROM LaboratorioCapacitacion ls WHERE ls.laboratorio.id = '" + laboratorioActual.getId()
					+ "' ORDER BY ls.capacitacion.nombre";
			System.out.println("hql:" + hql);
			List<LaboratorioCapacitacion> lista = servicioGeneral.obtenerObjetos(LaboratorioCapacitacion.class, hql);
			for (LaboratorioCapacitacion lao : lista) {
				listaCapacitaciones.add(lao);
			}
		}
		System.out.println("obtenerListaCapacitaciones out: listaCapacitaciones.size: " + listaCapacitaciones.size());
	}

	private void obtenerListaMuestreos() {
		System.out.println("obtenerListaMuestreos in:");
		listaMuestreos = new ArrayList<LaboratorioMuestreo>();
		if (laboratorioActual.getId() != null) {
			String hql = "FROM LaboratorioMuestreo ls WHERE ls.laboratorio.id = '" + laboratorioActual.getId()
					+ "' ORDER BY ls.muestreo.nombre";
			System.out.println("hql:" + hql);
			List<LaboratorioMuestreo> lista = servicioGeneral.obtenerObjetos(LaboratorioMuestreo.class, hql);
			for (LaboratorioMuestreo lao : lista) {
				listaMuestreos.add(lao);
			}
		}
		System.out.println("obtenerListaMuestreos out: listaMuestreos.size: " + listaMuestreos.size());
	}

	private void obtenerListaSistemasGestion() {
		System.out.println("obtenerListaSistemasGestion in:");
		listaSistemasGestion = new ArrayList<LaboratorioSistemaGestion>();
		if (laboratorioActual.getId() != null) {
			String hql = "FROM LaboratorioSistemaGestion ls WHERE ls.laboratorio.id = '" + laboratorioActual.getId()
					+ "' ORDER BY ls.sistemaGestion.nombre";
			System.out.println("hql:" + hql);
			List<LaboratorioSistemaGestion> lista = servicioGeneral.obtenerObjetos(LaboratorioSistemaGestion.class,
					hql);
			for (LaboratorioSistemaGestion lao : lista) {
				listaSistemasGestion.add(lao);
			}
		}
		System.out
				.println("obtenerListaSistemasGestion out: listaSistemasGestion.size: " + listaSistemasGestion.size());
	}

	@SuppressWarnings("unchecked")
	public void cargarListasParaTablas() {
		// DATOS BASICOS
		obtenerListaSubredes();
		obtenerListaActividades();
		obtenerListaCapacitaciones();
		obtenerListaMuestreos();
		obtenerListaSistemasGestion();

		listaMedidasQuimicas = (ArrayList<LaboratorioMetroMedidaQuimica>) servicioGeneral
				.obtenerListasMetrologia("LaboratorioMetroMedidaQuimica", laboratorioActual.getId());
		listaMedidasMicrobilogicas = (ArrayList<LaboratorioMetroMedidaMicro>) servicioGeneral
				.obtenerListasMetrologia("LaboratorioMetroMedidaMicro", laboratorioActual.getId());
		listaMedidasFisicas = (ArrayList<LaboratorioMetroMedidaFisica>) servicioGeneral
				.obtenerListasMetrologia("LaboratorioMetroMedidaFisica", laboratorioActual.getId());
		listaEnsayosFisicos = (ArrayList<LaboratorioMetroEnsayoFisico>) servicioGeneral
				.obtenerListasMetrologia("LaboratorioMetroEnsayoFisico", laboratorioActual.getId());
		listaPruebasInterlaboratorios = (ArrayList<LaboratorioMetroPruebaInterlab>) servicioGeneral
				.obtenerListasMetrologia("LaboratorioMetroPruebaInterlab", laboratorioActual.getId());
		listaGruposTrabajo = (ArrayList<LaboratorioMetroGrupoTrabajo>) servicioGeneral
				.obtenerListasMetrologia("LaboratorioMetroGrupoTrabajo", laboratorioActual.getId());
		listaMaterialesReferencia = (ArrayList<LaboratorioMetroMaterialReferencia>) servicioGeneral
				.obtenerListasMetrologia("LaboratorioMetroMaterialReferencia", laboratorioActual.getId());
		listaProductos = (ArrayList<LaboratorioMetroProducto>) servicioGeneral
				.obtenerListasMetrologia("LaboratorioMetroProducto", laboratorioActual.getId());
	}

	// Subred

	public void agregarSubred() {

		System.out.println("agregarSubRed SIN EVENTO subredSeleccionada: " + subredSeleccionada);
		// se comprueba que no exista ya:
		for (LaboratorioSubred labS : listaSubredes) {
			if (labS.getSubred().getId().toString().equals(subredSeleccionada)) {
				System.out.println("agregarSubred SIN EVENTO ya existe");
				mensajeError("InformacionLaboratorioMetrologia:tabViewMetrologiaDatosBasicos:siSubred",
						"La subred '" + labS.getSubred().getNombre() + "' ya se encuentra asociada");
				subredSeleccionada = "";
				return;
			}
		}

		if (subredSeleccionada != "") {
			LaboratorioSubred labS = new LaboratorioSubred();

			List<Tipos> lista = servicioGeneral.obtenerObjetoXID(Tipos.class, subredSeleccionada);
			Tipos subredTipo = (Tipos) lista.get(0);

			labS.setLaboratorio(laboratorioActual);
			;
			labS.setSubred(subredTipo);
			listaSubredes.add(labS);
			subredSeleccionada = "";
		}
	}

	public void eliminarSubred() {
		System.out.println("eliminarSubred IN: " + subredEliminar);
		listaSubredesEliminar.add(subredEliminar);
		listaSubredes.remove(subredEliminar);
	}

	// Capacitacion
	public void agregarCapacitacion() {

		System.out.println("agregarCapacitacion SIN EVENTO capacitacionSeleccionada: " + capacitacionSeleccionada);
		// se comprueba que no exista ya:
		for (LaboratorioCapacitacion labS : listaCapacitaciones) {
			if (labS.getCapacitacion().getId().toString().equals(capacitacionSeleccionada)) {
				System.out.println("agregarCapacitacion SIN EVENTO ya existe");
				mensajeError("InformacionLaboratorioMetrologia:tabViewMetrologiaDatosBasicos:siCapacitacion",
						"La opción de capacitación '" + labS.getCapacitacion().getNombre()
								+ "' ya se encuentra asociada");
				capacitacionSeleccionada = "";
				return;
			}
		}

		if (capacitacionSeleccionada != "") {
			LaboratorioCapacitacion labS = new LaboratorioCapacitacion();

			List<Tipos> lista = servicioGeneral.obtenerObjetoXID(Tipos.class, capacitacionSeleccionada);
			Tipos capacitacionTipo = (Tipos) lista.get(0);

			labS.setLaboratorio(laboratorioActual);
			;
			labS.setCapacitacion(capacitacionTipo);
			listaCapacitaciones.add(labS);
			capacitacionSeleccionada = "";
		}
	}

	public void eliminarCapacitacion() {
		System.out.println("eliminarCapacitacion IN: " + capacitacionEliminar);
		listaCapacitacionesEliminar.add(capacitacionEliminar);
		listaCapacitaciones.remove(capacitacionEliminar);
	}

	// Actividad

	public void agregarActividad() {

		System.out.println("agregarActividad SIN EVENTO actividadSeleccionada: " + actividadSeleccionada);
		// se comprueba que no exista ya:
		for (LaboratorioActividad labS : listaActividades) {
			if (labS.getActividad().getId().toString().equals(actividadSeleccionada)) {
				System.out.println("agregarActividad SIN EVENTO ya existe");
				mensajeError("InformacionLaboratorioMetrologia:tabViewMetrologiaDatosBasicos:siActividad",
						"La actividad '" + labS.getActividad().getNombre() + "' ya se encuentra asociada");
				actividadSeleccionada = "";
				return;
			}
		}

		if (actividadSeleccionada != "") {
			LaboratorioActividad labS = new LaboratorioActividad();

			List<Tipos> lista = servicioGeneral.obtenerObjetoXID(Tipos.class, actividadSeleccionada);
			Tipos actividadTipo = (Tipos) lista.get(0);

			labS.setLaboratorio(laboratorioActual);
			;
			labS.setActividad(actividadTipo);
			listaActividades.add(labS);
			actividadSeleccionada = "";
		}
	}

	public void eliminarActividad() {
		System.out.println("eliminarActividad IN: " + actividadEliminar);
		listaActividadesEliminar.add(actividadEliminar);
		listaActividades.remove(actividadEliminar);
	}

	// Muestreo

	public void agregarMuestreo() {

		System.out.println("agregarMuestreo SIN EVENTO muestreoSeleccionada: " + muestreoSeleccionada);
		// se comprueba que no exista ya:
		for (LaboratorioMuestreo labS : listaMuestreos) {
			if (labS.getMuestreo().getId().toString().equals(muestreoSeleccionada)) {
				System.out.println("agregarMuestreo SIN EVENTO ya existe");
				mensajeError("InformacionLaboratorioMetrologia:tabViewMetrologiaDatosBasicos:siMuestreo",
						"El problema para el muestreo '" + labS.getMuestreo().getNombre()
								+ "' ya se encuentra asociado");
				muestreoSeleccionada = "";
				return;
			}
		}

		if (muestreoSeleccionada != "") {
			LaboratorioMuestreo labS = new LaboratorioMuestreo();

			List<Tipos> lista = servicioGeneral.obtenerObjetoXID(Tipos.class, muestreoSeleccionada);
			Tipos muestreoTipo = (Tipos) lista.get(0);

			labS.setLaboratorio(laboratorioActual);
			;
			labS.setMuestreo(muestreoTipo);
			listaMuestreos.add(labS);
			muestreoSeleccionada = "";
		}
	}

	public void eliminarMuestreo() {
		System.out.println("eliminarMuestreo IN: " + muestreoEliminar);
		listaMuestreosEliminar.add(muestreoEliminar);
		listaMuestreos.remove(muestreoEliminar);
	}

	// Sistemas de gestión

	public void agregarSistemaGestion() {

		System.out
				.println("agregarSistemaGestion SIN EVENTO sistemaGestionSeleccionado: " + sistemaGestionSeleccionado);
		// se comprueba que no exista ya:
		for (LaboratorioSistemaGestion labS : listaSistemasGestion) {
			if (labS.getSistemaGestion().getId().toString().equals(sistemaGestionSeleccionado)) {
				System.out.println("agregarSistemaGestion SIN EVENTO ya existe");
				mensajeError("InformacionLaboratorioMetrologia:tabViewMetrologiaDatosBasicos:siSisGestion",
						"El sistema de gestión '" + labS.getSistemaGestion().getNombre()
								+ "' ya se encuentra asociado");
				sistemaGestionSeleccionado = "";
				return;
			}
		}

		if (sistemaGestionSeleccionado != "") {
			LaboratorioSistemaGestion labS = new LaboratorioSistemaGestion();

			List<Tipos> lista = servicioGeneral.obtenerObjetoXID(Tipos.class, sistemaGestionSeleccionado);
			Tipos sisGestionTipo = (Tipos) lista.get(0);

			labS.setLaboratorio(laboratorioActual);
			;
			labS.setSistemaGestion(sisGestionTipo);
			listaSistemasGestion.add(labS);
			sistemaGestionSeleccionado = "";
		}
	}

	public void eliminarSistemaGestion() {
		System.out.println("eliminarSistemaGestion IN: " + sistemaGestionEliminar);
		listaSistemasGestionEliminar.add(sistemaGestionEliminar);
		listaSistemasGestion.remove(sistemaGestionEliminar);
	}

	public void agregarMedidaQuimica() {
		boolean error = false;
		String metodoValidadoSel = null;
		String metodoValidadoSelDescrip = null;
		LaboratorioMetroMedidaQuimica medidaQuimicaSeleccionada = new LaboratorioMetroMedidaQuimica();

		// Validar

		// if (esCadenaVacia(normaMetodoReferencia.toString())) {
		// error = true;
		// mensajeError(agregarMedidaQuimica,"El campo 'Norma/Metodo de
		// referencia' no puede estar vacio");
		// }

		if (sectorSel.equals("0")) {
			error = true;
			mensajeError(agregarMedidaQuimica, "Debe seleccionar una opción de la lista SECTOR");
		}

		if (matrizSel.equals("0")) {
			error = true;
			mensajeError(agregarMedidaQuimica, "Debe seleccionar una opción de la lista MATRIZ");
		}

		// Adicionar a la lista
		if (!error) {

			Tipos tipoSector = obtenerTipoXid(Long.parseLong(sectorSel));
			Tipos tipoMatriz = obtenerTipoXid(Long.parseLong(matrizSel));
			Tipos tipoMensurando = obtenerTipoXid(Long.parseLong(mensurandoSel));
			Tipos tipoTecnica = obtenerTipoXid(Long.parseLong(tecnicaSel));
			Tipos tipoConcentracion = obtenerTipoXid(Long.parseLong(concentracionSel));

			for (String metodo : metodoValidadoSelCheckMenu) {
				Tipos tipo = obtenerTipoXid(Long.parseLong(metodo));
				metodoValidadoSel += metodo + ";";
				metodoValidadoSelDescrip += tipo.getNombre() + ";";
			}

			if (metodoValidadoSel != null)
				metodoValidadoSel = metodoValidadoSel.substring(4);

			if (metodoValidadoSelDescrip != null)
				metodoValidadoSelDescrip = metodoValidadoSelDescrip.substring(4);

			medidaQuimicaSeleccionada.setLaboratorio(laboratorioActual);

			medidaQuimicaSeleccionada.setSector(tipoSector);
			medidaQuimicaSeleccionada.setMatriz(tipoMatriz);
			medidaQuimicaSeleccionada.setMensurando(tipoMensurando);
			medidaQuimicaSeleccionada.setTecnica(tipoTecnica);
			medidaQuimicaSeleccionada.setConcentracionintervaloMedicion(tipoConcentracion);

			medidaQuimicaSeleccionada.setMetodoValidado(metodoValidadoSel);
			medidaQuimicaSeleccionada.setMetodoValidadoDesc(metodoValidadoSelDescrip);
			medidaQuimicaSeleccionada.setNormaMetodoReferencia(normaMetodoReferencia);
			medidaQuimicaSeleccionada.setMaterialesReferencia(materialesReferencia);

			listaMedidasQuimicas.add(medidaQuimicaSeleccionada);

			limpiarCamposMedidaQuimica();

		} else {
			System.out.println("ERROR al adicionar la MEDIDA QUÍMICA a la lista");
			return;
		}
	}

	public void limpiarCamposMedidaQuimica() {
		sectorSel = null;
		matrizSel = null;
		mensurandoSel = null;
		tecnicaSel = null;
		concentracionSel = null;
		metodoValidadoSelCheckMenu = null;
		normaMetodoReferencia = null;
		materialesReferencia = false;
	}

	public void agregarMedidaMicro() {
		boolean error = false;

		String validacionDescSel = null;
		String validacionDescSelDescrip = null;
		String controCalidadMediosCultivoDescSel = null;
		String controCalidadMediosCultivoDescSelDescrip = null;
		String condicionesAmbientalesDescSel = null;
		String condicionesAmbientalesDescSelDescrip = null;

		LaboratorioMetroMedidaMicro medidaMicroSeleccionada = new LaboratorioMetroMedidaMicro();

		// Validar
		// if (esCadenaVacia(materialReferenciaCepaUtilizadaSel.toString())) {
		// error = true;
		// mensajeError(agregarMedidaMicro,"El campo 'Material de Referencia
		// Cepa Usada' no puede estar vacio");
		// }

		if (sectorMicroSel.equals("0")) {
			error = true;
			mensajeError(agregarMedidaMicro, "Debe seleccionar una opción de la lista SECTOR");
		}

		if (matrizMicroSel.equals("0")) {
			error = true;
			mensajeError(agregarMedidaMicro, "Debe seleccionar una opción de la lista MATRIZ");
		}

		// Adicionar a la lista
		if (!error) {

			Tipos tipoSector = obtenerTipoXid(Long.parseLong(sectorMicroSel));
			Tipos tipoMatriz = obtenerTipoXid(Long.parseLong(matrizMicroSel));
			Tipos tipoTipoEnsayo = obtenerTipoXid(Long.parseLong(tipoEnsayoSel));
			Tipos tipoNombreEnsayo = obtenerTipoXid(Long.parseLong(nombreEnsayoMicroSel));
			Tipos tipoTecnica = obtenerTipoXid(Long.parseLong(tecnicaMicroSel));
			Tipos tipoNormaTecnica = obtenerTipoXid(Long.parseLong(normaTecnicaSel));
			Tipos tipoMaterialReferenciaCepaCertificada = obtenerTipoXid(
					Long.parseLong(materialReferenciaCepaCertificadaSel));

			// validacion
			for (String validacion : validacionSelCheckMenu) {
				Tipos tipo = obtenerTipoXid(Long.parseLong(validacion));
				validacionDescSel += validacion + ";";
				validacionDescSelDescrip += tipo.getNombre() + ";";
			}
			if (validacionDescSel != null)
				validacionDescSel = validacionDescSel.substring(4);
			if (validacionDescSelDescrip != null)
				validacionDescSelDescrip = validacionDescSelDescrip.substring(4);

			// Control Calidad
			for (String controlCal : controCalidadMediosCultivoSelCheckMenu) {
				Tipos tipo = obtenerTipoXid(Long.parseLong(controlCal));
				controCalidadMediosCultivoDescSel += controlCal + ";";
				controCalidadMediosCultivoDescSelDescrip += tipo.getNombre() + ";";
			}
			if (controCalidadMediosCultivoDescSel != null)
				controCalidadMediosCultivoDescSel = controCalidadMediosCultivoDescSel.substring(4);
			if (controCalidadMediosCultivoDescSelDescrip != null)
				controCalidadMediosCultivoDescSelDescrip = controCalidadMediosCultivoDescSelDescrip.substring(4);

			// Condiciones Ambientales
			for (String condicionesAmbientales : condicionesAmbientalesSelCheckMenu) {
				Tipos tipo = obtenerTipoXid(Long.parseLong(condicionesAmbientales));
				condicionesAmbientalesDescSel += condicionesAmbientales + ";";
				condicionesAmbientalesDescSelDescrip += tipo.getNombre() + ";";
			}
			if (condicionesAmbientalesDescSel != null)
				condicionesAmbientalesDescSel = condicionesAmbientalesDescSel.substring(4);
			if (condicionesAmbientalesDescSelDescrip != null)
				condicionesAmbientalesDescSelDescrip = condicionesAmbientalesDescSelDescrip.substring(4);

			medidaMicroSeleccionada.setLaboratorio(laboratorioActual);

			medidaMicroSeleccionada.setMaterialReferenciaCepaUtilizada(materialReferenciaCepaUtilizadaSel);

			medidaMicroSeleccionada.setSector(tipoSector);
			medidaMicroSeleccionada.setMatriz(tipoMatriz);
			medidaMicroSeleccionada.setTipoEnsayo(tipoTipoEnsayo);
			medidaMicroSeleccionada.setNombreEnsayo(tipoNombreEnsayo);
			medidaMicroSeleccionada.setTecnica(tipoTecnica);
			medidaMicroSeleccionada.setNormaTecnica(tipoNormaTecnica);
			medidaMicroSeleccionada.setMaterialReferenciaCepaCertificada(tipoMaterialReferenciaCepaCertificada);

			medidaMicroSeleccionada.setValidacion(validacionDescSel);
			medidaMicroSeleccionada.setValidacionDesc(validacionDescSelDescrip);
			medidaMicroSeleccionada.setControCalidadMediosCultivo(controCalidadMediosCultivoDescSel);
			medidaMicroSeleccionada.setControCalidadMediosCultivoDesc(controCalidadMediosCultivoDescSelDescrip);
			medidaMicroSeleccionada.setCondicionesAmbientales(condicionesAmbientalesDescSel);
			medidaMicroSeleccionada.setCondicionesAmbientalesDesc(condicionesAmbientalesDescSelDescrip);

			listaMedidasMicrobilogicas.add(medidaMicroSeleccionada);
			limpiarCamposMedidaMicro();

		} else {
			System.out.println("ERROR al adicionar la MEDIDA MICROBIOLOGICA a la lista");
			return;
		}

	}

	public void limpiarCamposMedidaMicro() {
		sectorMicroSel = null;
		matrizMicroSel = null;
		tipoEnsayoSel = null;
		nombreEnsayoMicroSel = null;
		tecnicaMicroSel = null;
		normaTecnicaSel = null;
		materialReferenciaCepaCertificadaSel = null;
		materialReferenciaCepaUtilizadaSel = null;
		validacionSelCheckMenu = null;
		controCalidadMediosCultivoSelCheckMenu = null;
		condicionesAmbientalesSelCheckMenu = null;
	}

	public void agregarMedidaFisica() {
		boolean error = false;

		LaboratorioMetroMedidaFisica medidaFisicaSeleccionada = new LaboratorioMetroMedidaFisica();

		// Validar
		if (esCadenaVacia(descripcionMensurandoSel.toString())) {
			error = true;
			mensajeError(agregarMedidaFisica, "El campo 'Descripción del Mensurando' no puede estar vacio");
		}

		if (magnitudFisicaSel.equals("0")) {
			error = true;
			mensajeError(agregarMedidaFisica, "Debe seleccionar una opción de la lista MAGNITUD FISICA");
		}

		if (campoAplicacionSel.equals("0")) {
			error = true;
			mensajeError(agregarMedidaFisica, "Debe seleccionar una opción de la lista CAMPO APLICACIÓN");
		}

		// Adicionar a la lista
		if (!error) {

			Tipos tipoMagnitudFisica = obtenerTipoXid(Long.parseLong(magnitudFisicaSel));
			Tipos tipoCampoAplicacion = obtenerTipoXid(Long.parseLong(campoAplicacionSel));
			Tipos tipoUniIntPrefijo = obtenerTipoXid(Long.parseLong(unidadesIntervaloPrefijoSel));
			Tipos tipoUniIntUnidad = obtenerTipoXid(Long.parseLong(unidadesIntervaloUnidadSel));
			Tipos tipoIncertMedIncertidumbre = obtenerTipoXid(Long.parseLong(incertidumbreMedicionIncertidumbreSel));
			Tipos tipoIncertMedPrefijo = obtenerTipoXid(Long.parseLong(incertidumbreMedicionPrefijoSel));
			Tipos tipoIncertMedUnidad = obtenerTipoXid(Long.parseLong(incertidumbreMedicionUnidadSel));

			medidaFisicaSeleccionada.setLaboratorio(laboratorioActual);

			medidaFisicaSeleccionada.setDescripcionMensurando(descripcionMensurandoSel);
			medidaFisicaSeleccionada.setMagnitudFisica(tipoMagnitudFisica);
			medidaFisicaSeleccionada.setCampoAplicacion(tipoCampoAplicacion);
			medidaFisicaSeleccionada.setFactorCoberturaK(factorCoberturaKSel);
			medidaFisicaSeleccionada.setNormaDocumentosMetodosReferencia(normaDocumentosMetodosReferenciaSel);

			medidaFisicaSeleccionada.setIntervaloPuntoMedMinimo(intervaloPuntoMedMinimoSel);
			medidaFisicaSeleccionada.setIntervaloPuntoMedMaximo(intervaloPuntoMedMaximoSel);

			medidaFisicaSeleccionada.setUnidadesIntervaloPrefijo(tipoUniIntPrefijo);
			medidaFisicaSeleccionada.setUnidadesIntervaloUnidad(tipoUniIntUnidad);
			medidaFisicaSeleccionada.setUnidadesIntervaloSimbolo(unidadesIntervaloSimboloSel);

			medidaFisicaSeleccionada.setIncertidumbreMedicionIncertidumbre(tipoIncertMedIncertidumbre);
			medidaFisicaSeleccionada.setIncertidumbreMedicionValor(incertidumbreMedicionValorSel);
			medidaFisicaSeleccionada.setIncertidumbreMedicionPrefijo(tipoIncertMedPrefijo);
			medidaFisicaSeleccionada.setIncertidumbreMedicionUnidad(tipoIncertMedUnidad);
			medidaFisicaSeleccionada.setIncertidumbreMedicionSimbolo(incertidumbreMedicionSimboloSel);

			medidaFisicaSeleccionada.setAcreditado(acreditadoSel);
			medidaFisicaSeleccionada.setEntidadAcreditadoraTipoDoc(entidadAcreditadoraTipoDocSel);
			medidaFisicaSeleccionada.setEntidadAcreditadoraNumDoc(entidadAcreditadoraNumDocSel);
			medidaFisicaSeleccionada.setEntidadAcreditadoraNombre(entidadAcreditadoraNombreSel);

			listaMedidasFisicas.add(medidaFisicaSeleccionada);
			limpiarCamposMedidaFisica();

		} else {
			System.out.println("ERROR al adicionar la MEDIDA FÍSICA a la lista");
			return;
		}

	}

	public void limpiarCamposMedidaFisica() {

		magnitudFisicaSel = null;
		campoAplicacionSel = null;
		descripcionMensurandoSel = null;
		factorCoberturaKSel = null;
		normaDocumentosMetodosReferenciaSel = null;

		intervaloPuntoMedMinimoSel = null;
		intervaloPuntoMedMaximoSel = null;

		unidadesIntervaloPrefijoSel = null;
		unidadesIntervaloUnidadSel = null;
		unidadesIntervaloSimboloSel = null;

		incertidumbreMedicionIncertidumbreSel = null;
		incertidumbreMedicionPrefijoSel = null;
		incertidumbreMedicionUnidadSel = null;
		incertidumbreMedicionValorSel = null;
		incertidumbreMedicionSimboloSel = null;

		acreditadoSel = false;
		entidadAcreditadoraTipoDocSel = null;
		entidadAcreditadoraNumDocSel = null;
		entidadAcreditadoraNombreSel = null;

	}

	public void agregarEnsayoFisico() {
		boolean error = false;

		LaboratorioMetroEnsayoFisico ensayoFisicoSeleccionada = new LaboratorioMetroEnsayoFisico();

		// Validar

		if (esCadenaVacia(nombreEnsayoSel.toString())) {
			error = true;
			mensajeError(agregarEnsayoFisico, "El campo 'Nombre del Ensayo' no puede estar vacio");
		}

		// Adicionar a la lista
		if (!error) {

			ensayoFisicoSeleccionada.setLaboratorio(laboratorioActual);

			ensayoFisicoSeleccionada.setNombreEnsayo(nombreEnsayoSel);
			;
			ensayoFisicoSeleccionada.setProductosMaterialAEnsayar(productosMaterialAEnsayarSel);
			ensayoFisicoSeleccionada.setPropiedadesMedibles(propiedadesMediblesSel);
			ensayoFisicoSeleccionada.setMinimo(minimoSel);
			ensayoFisicoSeleccionada.setMaximo(maximoSel);
			ensayoFisicoSeleccionada.setUnidades(unidadesSel);
			ensayoFisicoSeleccionada.setDescripcion(descripcionSel);
			ensayoFisicoSeleccionada.setNormaTecnicaProcedimiento(normaTecnicaProcedimientoSel);

			listaEnsayosFisicos.add(ensayoFisicoSeleccionada);
			limpiarCamposEnsayosFisicos();

		} else {
			System.out.println("ERROR al adicionar el ENSAYO FÍSICO a la lista");
			return;
		}

	}

	public void limpiarCamposEnsayosFisicos() {

		nombreEnsayoSel = null;
		productosMaterialAEnsayarSel = null;
		propiedadesMediblesSel = null;
		minimoSel = null;
		maximoSel = null;
		unidadesSel = null;
		descripcionSel = null;
		normaTecnicaProcedimientoSel = null;
	}

	public void agregarPruebaInterlaboratorio() {
		boolean error = false;
		// proveedorSel = null;
		// desempenoObtenidoSel = null;

		LaboratorioMetroPruebaInterlab pruebaInterLabSeleccionada = new LaboratorioMetroPruebaInterlab();

		// Validar
		if (participaSel) {

			if (esCadenaVacia(cualSel.toString())) {
				error = true;
				mensajeError(agregarPruebaInterlab, "Debe ingresar Cual Prueba Interlaboratorio");
			}

			if (esNulo(proveedorSel)) {
				error = true;
				mensajeError(agregarPruebaInterlab,
						"Debe seleccionar la naturaleza del proveedor NACIONAL O INTERNACIONAL");
			}

			if (esCadenaVacia(nombreProveedorPISel.toString())) {
				error = true;
				mensajeError(agregarPruebaInterlab, "Debe ingresar el nombre del proveedor");
			}
		}

		// Adicionar a la lista
		if (!error) {

			if (esNulo(desempenoObtenidoSel))
				desempenoObtenidoSel = "0";

			if (esNulo(proveedorSel))
				proveedorSel = "0";

			Tipos tipoDesempeño = obtenerTipoXid(Long.parseLong(desempenoObtenidoSel));
			Tipos tipoProveedor = obtenerTipoXid(Long.parseLong(proveedorSel));

			pruebaInterLabSeleccionada.setLaboratorio(laboratorioActual);

			pruebaInterLabSeleccionada.setParticipa(participaSel);
			pruebaInterLabSeleccionada.setCual(cualSel);
			pruebaInterLabSeleccionada.setDesempenoObtenido(tipoDesempeño);
			pruebaInterLabSeleccionada.setProveedor(tipoProveedor);
			pruebaInterLabSeleccionada.setNombreProveedor(nombreProveedorPISel);
			pruebaInterLabSeleccionada.setTipoDocumento(tipoDocumentoProveedorPISel);
			pruebaInterLabSeleccionada.setIdentificacion(identificacionProveedorPISel);

			listaPruebasInterlaboratorios.add(pruebaInterLabSeleccionada);
			limpiarCamposPruebaInterlaboratorio();

		} else {
			System.out.println("ERROR al adicionar la PRUEBA INTERLABORATORIO a la lista");
			return;
		}

	}
	
	public void limpiarCamposPruebaInterlaboratorio() {

		participaSel = false;
		cualSel = null;
		desempenoObtenidoSel = null;
		proveedorSel = null;
		nombreProveedorPISel = null;
		tipoDocumentoProveedorPISel = null;
		identificacionProveedorPISel = null;
	}

	public void agregarGrupoDeTrabajo() {
		boolean error = false;
		LaboratorioMetroGrupoTrabajo grupoTrabajoSeleccionado = new LaboratorioMetroGrupoTrabajo();

		// Validar

		if (esCadenaVacia(nombreGrupoSel.toString())) {
			error = true;
			mensajeError(agregarGrupoTrabajo, "El campo 'Nombre' no puede estar vacio");
		}

		// Adicionar a la lista
		if (!error) {

			Tipos tipoTipoGrupo = obtenerTipoXid(Long.parseLong(tipoGrupoSel));
			Tipos tipoPeriodoParticipacion = obtenerTipoXid(Long.parseLong(periodoParticipacionSel));

			grupoTrabajoSeleccionado.setLaboratorio(laboratorioActual);

			grupoTrabajoSeleccionado.setNombre(nombreGrupoSel);
			grupoTrabajoSeleccionado.setTipoGrupo(tipoTipoGrupo);
			grupoTrabajoSeleccionado.setPeriodoParticipacion(tipoPeriodoParticipacion);

			listaGruposTrabajo.add(grupoTrabajoSeleccionado);
			limpiarCamposGrupoDeTrabajo();

		} else {
			System.out.println("ERROR al adicionar el GRUPO DE TRABAJO a la lista");
			return;
		}

	}
	
	public void limpiarCamposGrupoDeTrabajo() {

		tipoGrupoSel = null;
		nombreGrupoSel = null;
		periodoParticipacionSel = null;
	}

	public void agregarMaterialReferencia() {
		boolean error = false;
		LaboratorioMetroMaterialReferencia materialReferenciaSeleccionada = new LaboratorioMetroMaterialReferencia();

		// Validar

		if (esCadenaVacia(nombreMaterialReferenciaSel.toString())) {
			error = true;
			mensajeError(agregarMaterialesReferencia,
					"El campo 'Nombre del Material de Referencia' no puede estar vacio");
		}

		if (esNulo(materialDificilAdquisicionSel)) {
			error = true;
			mensajeError(agregarMaterialesReferencia,
					"Debe seleccionar si Considera que el material de referencia es de dificil adquisicion");
		}

		if (esNulo(usoAseguraTrazabilildadMedicionSel)) {
			error = true;
			mensajeError(agregarMaterialesReferencia,
					"Debe seleccionar si Considera que el uso de MR/MRC asegura la trazabilidad de su medición");
		}

		// Adicionar a la lista
		if (!error) {

			Tipos tipoUsaMaterialReferenciaSel = obtenerTipoXid(Long.parseLong(usaMaterialReferenciaSel));
			Tipos tipoTipoMRSel = obtenerTipoXid(Long.parseLong(tipoMRSel));

			materialReferenciaSeleccionada.setLaboratorio(laboratorioActual);

			materialReferenciaSeleccionada.setNombreMaterialReferencia(nombreMaterialReferenciaSel);
			materialReferenciaSeleccionada.setProductor(productorSel);
			materialReferenciaSeleccionada.setProveedor(proveedorMRSel);
			materialReferenciaSeleccionada.setUsaMaterialReferencia(tipoUsaMaterialReferenciaSel);
			materialReferenciaSeleccionada.setTipo(tipoTipoMRSel);
			materialReferenciaSeleccionada.setMaterialDificilAdquisicion(materialDificilAdquisicionSel);
			materialReferenciaSeleccionada.setUsoAseguraTrazabilildadMedicion(usoAseguraTrazabilildadMedicionSel);

			listaMaterialesReferencia.add(materialReferenciaSeleccionada);
			limpiarCamposMaterialReferencia();

		} else {
			System.out.println("ERROR al adicionar el MATERIAL DE REFERENCIA a la lista");
			return;
		}

	}
	
	public void limpiarCamposMaterialReferencia() {

		nombreMaterialReferenciaSel = null;
		productorSel = null;
		proveedorMRSel = null;
		usaMaterialReferenciaSel = null;
		tipoMRSel = null;
		materialDificilAdquisicionSel = false;
		usoAseguraTrazabilildadMedicionSel = false;
	}

	public void agregarProducto() {
		boolean error = false;
		LaboratorioMetroProducto productoSeleccionado = new LaboratorioMetroProducto();

		// Validar

		if (esCadenaVacia(productoSel.toString())) {
			error = true;
			mensajeError(agregarProducto, "El campo 'Producto' no puede estar vacio");
		}

		if (esNulo(exportacionSel)) {
			error = true;
			mensajeError(agregarProducto, "Debe seleccionar si el Producto es de Exportación");
		}

		if (exportacionSel && paisSel.equals("00")) {
			error = true;
			mensajeError(agregarProducto, "Debe seleccionar un pais de Exportación");
		}

		// Adicionar a la lista
		if (!error) {

			Tipos tipoSubRedProducto = obtenerTipoXid(Long.parseLong(subredSel));

			productoSeleccionado.setLaboratorio(laboratorioActual);

			productoSeleccionado.setProducto(productoSel);
			productoSeleccionado.setSubred(tipoSubRedProducto);
			productoSeleccionado.setExportacion(exportacionSel);
			if (exportacionSel) {
				Pais paisObjSel = (Pais) servicioGeneral.obtenerObjetoXID("Pais", paisSel).get(0);
				productoSeleccionado.setPais(paisObjSel);
				productoSeleccionado.setCiudad(ciudadSel);
			}

			listaProductos.add(productoSeleccionado);
			limpiarCamposProducto();

		} else {
			System.out.println("ERROR al adicionar el PRODUCTO a la lista");
			return;
		}

	}
	
	public void limpiarCamposProducto() {

		productoSel = null;
		subredSel = null;
		exportacionSel = false;
		paisSel = null;
		ciudadSel = null;
	}
	
	public void imprimirMaterialReferencia() {
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("id", materialReferenciaSeleccionadaTabla.getId().toString());
		r.setNombreReporte("/laboratorios/Metrologia/MaterialesReferencia");
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
	}
	
	public void imprimirMedidaQuimica() {
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("id", medidaQuimicaSeleccionadaTabla.getId().toString());
		r.setNombreReporte("/laboratorios/Metrologia/MedidasQuimicas");
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
	}
	
	public void imprimirMedidaFisica() {
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("id", medidaFisicaSeleccionadaTabla.getId().toString());
		r.setNombreReporte("/laboratorios/Metrologia/MedidasFisicas");
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
	}
	
	public void imprimirMedidaMicrobiologica() {
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("id", medidaMicrobiologicaSeleccionadaTabla.getId().toString());
		r.setNombreReporte("/laboratorios/Metrologia/MedidasMicrobiologicas");
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
	}
	
	public void imprimirEnsayoFisico() {
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("id", ensayoFisicoSeleccionadoTabla.getId().toString());
		r.setNombreReporte("/laboratorios/Metrologia/EnsayosFisicos");
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
	}

	public void cargarListas() {
		// DATOS BASICOS
		selectItemSubredes = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPO_LAB_METRO_SUBRED);
		selectItemCapacitaciones = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPO_LAB_METRO_CAPACITACION);
		selectItemActividades = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPO_LAB_METRO_ACTIVIDADES);
		selectItemMuestreos = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPO_LAB_METRO_MUESTRA);
		selectItemSisGestion = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPO_LAB_METRO_SISTEMAS_GESTION);

		// PRODUCTOS
		cargarPaises();

		// Med Químicas
		selectItemSector = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPO_LAB_METRO_SECTOR);
		// selectItemMatriz =
		// servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPO_LAB_METRO_MATRIZ);
		selectItemMensurando = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPO_LAB_METRO_MENSURANDO);
		selectItemTecnica = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPO_LAB_METRO_TECNICA);
		selectItemConcentracion = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPO_LAB_METRO_CONCENTRACION);
		selectItemMetodoValidado = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPO_LAB_METRO_METODO_VALIDADO);

		// Med Microbiologicas
		selectItemTipoEnsayo = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPO_LAB_METRO_TIPO_ENSAYO);
		selectItemNombreEnsayo = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPO_LAB_METRO_NOMBRE_ENSAYO);
		selectItemTecnicaMicro = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPO_LAB_METRO_TECNICA_MED_MICRO);
		selectItemNormaTecnica = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPO_LAB_METRO_NORMA_TECNICA);
		selectItemMateCepaCertificada = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPO_LAB_METRO_MATERIAL_REF_CEPA_CERTIFICADA);
		selectItemValidacion = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPO_LAB_METRO_VALIDACION);
		selectItemControlCalMedioCultivo = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPO_LAB_METRO_CALIDAD_MEDIOS_CULTIVO);
		selectItemCondicionesAmbientales = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPO_LAB_METRO_CONDICIONES_AMBIENTALES);

		// Med Físicas
		selectItemMagnitudFisica = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPO_LAB_METRO_MAGNITUD_FISICA);
		// selectItemCampoAplicacion =
		// servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPO_LAB_METRO_MAGNITUD_CAMPO_APLICACION);
		selectItemUnidIntPrefijo = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPO_LAB_METRO_PREFIJO);
		selectItemUnidIntUnidad = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPO_LAB_METRO_UNIDAD);
		selectItemIncertMedIncert = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPO_LAB_METRO_INCERT_MED_INCERTIDUMBRE);
		selectItemIncertMedPrefijo = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPO_LAB_METRO_PREFIJO);
		selectItemIncertMedUnidad = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPO_LAB_METRO_UNIDAD);

		List<TipoDocumento> listaTipoDocumento = servicioGeneral.obtenerTiposDeDocumentoMetrologia();
		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];

		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			TipoDocumento td = listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
		}

		// Ensayos Físicos

		// Pruebas Inter Lab
		selectItemDesempeno = servicioGeneral.retornaSelectItemArregloDeHijosDeTipos(Tipos.TIPO_LAB_METRO_DESEMPEÑO_LAB_PRUEBA);
		selectItemProveedor = servicioGeneral.retornaSelectItemArregloDeHijosDeTipos(Tipos.TIPO_LAB_METRO_PROVEEDOR);

		// Grupo Trabajo
		selectItemTipoGrupo = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPO_LAB_METRO_TIPO_GRUPO);
		selectItemPeriodoParticipacion = servicioGeneral.retornaSelectItemArregloDeHijosDeTipos(Tipos.TIPO_LAB_METRO_PERIODO_PARTICIPACION_GRUPO);

		// Mat Referencia
		selectItemNecesitaUsaMaterialReferncia = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPO_LAB_METRO_NECESITA_USA_MATERIAL_REF);
		selectItemTipoMaterialReferencia = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPO_LAB_METRO_TIPO_MATERIAL_REF);

	}

	public void cambiarSector() {
		if (!sectorSel.equals("0"))
			selectItemMatriz = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Long.parseLong(sectorSel));
	}

	public void cambiarSectorMM() {
		if (!sectorMicroSel.equals("0"))
			selectItemMatriz = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Long.parseLong(sectorMicroSel));
	}

	public void cambiarMagnitudFisica() {
		if (!magnitudFisicaSel.equals("0"))
			selectItemCampoAplicacion = servicioGeneral
					.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Long.parseLong(magnitudFisicaSel));
	}

	public void guardar() {

		guardarLaboratorioActual(idManejador);

		// INICIO METOLOGIA

		// Subredes
		System.out.println("guardando listaSubredes");
		for (LaboratorioSubred lao : listaSubredes) {
			servicioGeneral.guardarObjeto(lao);
		}
		System.out.println("eliminado listaSubredesEliminar");
		for (LaboratorioSubred laoe : listaSubredesEliminar) {
			if (laoe.getId() != null) {
				servicioGeneral.eliminarObjeto(laoe);
			}
		}

		// Actividades
		System.out.println("guardando listaActividades");
		for (LaboratorioActividad lao : listaActividades) {
			servicioGeneral.guardarObjeto(lao);
		}
		System.out.println("eliminado listaActividadesEliminar");
		for (LaboratorioActividad laoe : listaActividadesEliminar) {
			if (laoe.getId() != null) {
				servicioGeneral.eliminarObjeto(laoe);
			}
		}

		// Capacitación
		System.out.println("guardando listaCapacitaciones");
		for (LaboratorioCapacitacion lao : listaCapacitaciones) {
			servicioGeneral.guardarObjeto(lao);
		}
		System.out.println("eliminado listaCapacitacionesEliminar");
		for (LaboratorioCapacitacion laoe : listaCapacitacionesEliminar) {
			if (laoe.getId() != null) {
				servicioGeneral.eliminarObjeto(laoe);
			}
		}

		// Muestreo
		System.out.println("guardando listaMuestreos");
		for (LaboratorioMuestreo lao : listaMuestreos) {
			servicioGeneral.guardarObjeto(lao);
		}
		System.out.println("eliminado listaMuestreosEliminar");
		for (LaboratorioMuestreo laoe : listaMuestreosEliminar) {
			if (laoe.getId() != null) {
				servicioGeneral.eliminarObjeto(laoe);
			}
		}

		// Sistemas de Gestión
		System.out.println("guardando listaSistemasGestion");
		for (LaboratorioSistemaGestion lao : listaSistemasGestion) {
			servicioGeneral.guardarObjeto(lao);
		}
		System.out.println("eliminado listaSistemasGestionEliminar");
		for (LaboratorioSistemaGestion laoe : listaSistemasGestionEliminar) {
			if (laoe.getId() != null) {
				servicioGeneral.eliminarObjeto(laoe);
			}
		}

		// Medidas Quimícas
		System.out.println("guardando listaMedidasQuimicas");
		for (LaboratorioMetroMedidaQuimica lmq : listaMedidasQuimicas) {
			if (lmq.getId() == null)
				lmq.setFechaRegistro(new Date());
			servicioGeneral.guardarObjeto(lmq);
		}

		System.out.println("eliminado listaMedidasQuimicasEliminar");
		for (LaboratorioMetroMedidaQuimica lmqe : listaMedidasQuimicasEliminar) {
			if (lmqe.getId() != null) {
				servicioGeneral.eliminarObjeto(lmqe);
			}
		}

		// Medidas Micro
		System.out.println("guardando listaMedidasMicrobilogicas");
		for (LaboratorioMetroMedidaMicro lmm : listaMedidasMicrobilogicas) {
			if (lmm.getId() == null)
				lmm.setFechaRegistro(new Date());
			servicioGeneral.guardarObjeto(lmm);
		}

		System.out.println("eliminado listaMedidasMicrobilogicasEliminar");
		for (LaboratorioMetroMedidaMicro lmme : listaMedidasMicrobilogicasEliminar) {
			if (lmme.getId() != null) {
				servicioGeneral.eliminarObjeto(lmme);
			}
		}

		// Medidas Fisicas
		System.out.println("guardando listaMedidasFisicas");
		for (LaboratorioMetroMedidaFisica lmf : listaMedidasFisicas) {
			if (lmf.getId() == null)
				lmf.setFechaRegistro(new Date());
			servicioGeneral.guardarObjeto(lmf);
		}

		System.out.println("eliminado listaMedidasFisicasEliminar");
		for (LaboratorioMetroMedidaFisica lmfe : listaMedidasFisicasEliminar) {
			if (lmfe.getId() != null) {
				servicioGeneral.eliminarObjeto(lmfe);
			}
		}

		// Ensayos Fisicos
		System.out.println("guardando listaEnsayosFisicos");
		for (LaboratorioMetroEnsayoFisico lef : listaEnsayosFisicos) {
			if (lef.getId() == null)
				lef.setFechaRegistro(new Date());
			servicioGeneral.guardarObjeto(lef);
		}

		System.out.println("eliminado listaEnsayosFisicosEliminar");
		for (LaboratorioMetroEnsayoFisico lefe : listaEnsayosFisicosEliminar) {
			if (lefe.getId() != null) {
				servicioGeneral.eliminarObjeto(lefe);
			}
		}

		// Pruebas InterLabs
		System.out.println("guardando listaPruebasInterlaboratorios");
		for (LaboratorioMetroPruebaInterlab lpi : listaPruebasInterlaboratorios) {
			if (lpi.getId() == null)
				lpi.setFechaRegistro(new Date());
			servicioGeneral.guardarObjeto(lpi);
		}

		System.out.println("eliminado listaPruebasInterlaboratoriosEliminar");
		for (LaboratorioMetroPruebaInterlab lpie : listaPruebasInterlaboratoriosEliminar) {
			if (lpie.getId() != null) {
				servicioGeneral.eliminarObjeto(lpie);
			}
		}

		// Grupos de trabajo
		System.out.println("guardando listaGruposTrabajo");
		for (LaboratorioMetroGrupoTrabajo lgt : listaGruposTrabajo) {
			if (lgt.getId() == null)
				lgt.setFechaRegistro(new Date());
			servicioGeneral.guardarObjeto(lgt);
		}

		System.out.println("eliminado listaGruposTrabajoEliminar");
		for (LaboratorioMetroGrupoTrabajo lgte : listaGruposTrabajoEliminar) {
			if (lgte.getId() != null) {
				servicioGeneral.eliminarObjeto(lgte);
			}
		}

		// Materiales referencia
		System.out.println("guardando listaMaterialesReferencia");
		for (LaboratorioMetroMaterialReferencia lmr : listaMaterialesReferencia) {
			if (lmr.getId() == null)
				lmr.setFechaRegistro(new Date());
			servicioGeneral.guardarObjeto(lmr);
		}

		System.out.println("eliminado listaMaterialesReferenciaEliminar");
		for (LaboratorioMetroMaterialReferencia lmre : listaMaterialesReferenciaEliminar) {
			if (lmre.getId() != null) {
				servicioGeneral.eliminarObjeto(lmre);
			}
		}

		// Productos
		System.out.println("guardando listaProductos");
		for (LaboratorioMetroProducto lpr : listaProductos) {
			if (lpr.getId() == null)
				lpr.setFechaRegistro(new Date());
			servicioGeneral.guardarObjeto(lpr);
		}

		System.out.println("eliminado listaProductosEliminar");
		for (LaboratorioMetroProducto lpre : listaProductosEliminar) {
			if (lpre.getId() != null) {
				servicioGeneral.eliminarObjeto(lpre);
			}
		}
	}

	public Boolean validar() {
		boolean validar = true;

		// Float dedicacionDocencia = laboratorioActual.getDedicacionDocencia();
		// Float dedicacionExtension =
		// laboratorioActual.getDedicacionExtension();
		// Float dedicacionInvestigacion = laboratorioActual
		// .getDedicacionInvestigacion();
		//
		// Float totalDedicacion = dedicacionDocencia + dedicacionExtension
		// + dedicacionInvestigacion;
		//
		// if (totalDedicacion > 100 || totalDedicacion < 99) {
		// FacesContext
		// .getCurrentInstance()
		// .addMessage(
		// "InformacionLaboratorio",
		// new FacesMessage(
		// FacesMessage.SEVERITY_ERROR,
		// "La suma de los porcentajes de dedicación debe ser igual a 100.",
		// null));
		//
		// UIComponent comp = FacesContext.getCurrentInstance().getViewRoot()
		// .findComponent("InformacionLaboratorio:dedicacionDocencia");
		// if ((comp != null) && (comp instanceof UIInput)) {
		// ((UIInput) comp).setValid(false);
		// }
		//
		// comp = FacesContext
		// .getCurrentInstance()
		// .getViewRoot()
		// .findComponent(
		// "InformacionLaboratorio:dedicacionInvestigacion");
		// if ((comp != null) && (comp instanceof UIInput)) {
		// ((UIInput) comp).setValid(false);
		// }
		//
		// comp = FacesContext
		// .getCurrentInstance()
		// .getViewRoot()
		// .findComponent("InformacionLaboratorio:dedicacionExtension");
		// if ((comp != null) && (comp instanceof UIInput)) {
		// ((UIInput) comp).setValid(false);
		// }
		//
		// validar = false;
		// }
		//
		// // Obligatorio area OCDE:
		// if (listaAreasOCDE == null || listaAreasOCDE.size() == 0) {
		// System.out.println("aarea OCDE obligatoria:");
		// mensajeError("InformacionLaboratorio:siOCDE",
		// "Debe seleccionar al menos un área OCDE.");
		// validar = false;
		// }
		//
		// // Acto de Creación obligatorio para laboratorios nuevos:
		// if (!esSolicitudLab && laboratorioActual.getId() == null) {
		// System.out.println("id null, laboratorio nuevo:");
		// Boolean validadAdC = true;
		//
		// try {
		// if (laboratorioActual.getTipoActoCreacion().trim().equals("")) {
		// validadAdC = false;
		// }
		// if (laboratorioActual.getNumeroActoCreacion().trim().equals("")) {
		// validadAdC = false;
		// }
		// if (laboratorioActual.getEmanadaPorActoCreacion().trim()
		// .equals("")) {
		// validadAdC = false;
		// }
		// if (laboratorioActual.getFechaActoCreacion() == null) {
		// validadAdC = false;
		// }
		// } catch (Exception e) {
		// validadAdC = false;
		// System.out.println("error validando acto de creación:");
		// }
		//
		// if (!validadAdC) {
		// mensajeError("InformacionLaboratorio:tipoActoCreacion",
		// "Debe ingresar TODA la información relativa al Acto de Creación.");
		// validar = false;
		// }
		// System.out.println("acto de creación válido:" + validadAdC);
		// }
		//
		// System.out.println("validar: " + validar);
		return validar;
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

	public void eliminarMedidaQuimica() {
		listaMedidasQuimicas.remove(medidaQuimicaSeleccionadaTabla);
		listaMedidasQuimicasEliminar.add(medidaQuimicaSeleccionadaTabla);
	}

	public void eliminarMedidaMicro() {
		listaMedidasMicrobilogicas.remove(medidaMicrobiologicaSeleccionadaTabla);
		listaMedidasMicrobilogicasEliminar.add(medidaMicrobiologicaSeleccionadaTabla);
	}

	public void eliminarMedidaFisica() {
		listaMedidasFisicas.remove(medidaFisicaSeleccionadaTabla);
		listaMedidasFisicasEliminar.add(medidaFisicaSeleccionadaTabla);
	}

	public void eliminarEnsayoFisico() {
		listaEnsayosFisicos.remove(ensayoFisicoSeleccionadoTabla);
		listaEnsayosFisicosEliminar.add(ensayoFisicoSeleccionadoTabla);
	}

	public void eliminarPruebaInterLab() {
		listaPruebasInterlaboratorios.remove(pruebaInterlaboratorioSeleccionadaTabla);
		listaPruebasInterlaboratoriosEliminar.add(pruebaInterlaboratorioSeleccionadaTabla);
	}

	public void eliminarGrupoTrabajo() {
		listaGruposTrabajo.remove(grupoTrabajoSeleccionadoTabla);
		listaGruposTrabajoEliminar.add(grupoTrabajoSeleccionadoTabla);
	}

	public void eliminarMaterialReferencia() {
		listaMaterialesReferencia.remove(materialReferenciaSeleccionadaTabla);
		listaMaterialesReferenciaEliminar.add(materialReferenciaSeleccionadaTabla);
	}

	public void eliminarProducto() {
		listaProductos.remove(productoSeleccionadoTabla);
		listaProductosEliminar.add(productoSeleccionadoTabla);
	}

	public void cargarInfoMedidaQuimicaSel() {
		metodoValidadoSelDescrip = convertirArregloACadena(metodoValidadoSelCheckMenu, ";");
	}

	@Override
	public String siguiente() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<LaboratorioMetroMedidaQuimica> getListaMedidasQuimicas() {
		return listaMedidasQuimicas;
	}

	public void setListaMedidasQuimicas(ArrayList<LaboratorioMetroMedidaQuimica> listaMedidasQuimicas) {
		this.listaMedidasQuimicas = listaMedidasQuimicas;
	}

	public ArrayList<LaboratorioMetroMedidaQuimica> getListaMedidasQuimicasEliminar() {
		return listaMedidasQuimicasEliminar;
	}

	public void setListaMedidasQuimicasEliminar(ArrayList<LaboratorioMetroMedidaQuimica> listaMedidasQuimicasEliminar) {
		this.listaMedidasQuimicasEliminar = listaMedidasQuimicasEliminar;
	}

	public LaboratorioMetroMedidaQuimica getMedidaQuimicaSeleccionadaTabla() {
		return medidaQuimicaSeleccionadaTabla;
	}

	public void setMedidaQuimicaSeleccionadaTabla(LaboratorioMetroMedidaQuimica medidaQuimicaSeleccionadaTabla) {
		this.medidaQuimicaSeleccionadaTabla = medidaQuimicaSeleccionadaTabla;
	}

	public String getSectorSel() {
		return sectorSel;
	}

	public void setSectorSel(String sectorSel) {
		this.sectorSel = sectorSel;
	}

	public String getMatrizSel() {
		return matrizSel;
	}

	public void setMatrizSel(String matrizSel) {
		this.matrizSel = matrizSel;
	}

	public String getMensurandoSel() {
		return mensurandoSel;
	}

	public void setMensurandoSel(String mensurandoSel) {
		this.mensurandoSel = mensurandoSel;
	}

	public String getTecnicaSel() {
		return tecnicaSel;
	}

	public void setTecnicaSel(String tecnicaSel) {
		this.tecnicaSel = tecnicaSel;
	}

	public String getConcentracionSel() {
		return concentracionSel;
	}

	public void setConcentracionSel(String concentracionSel) {
		this.concentracionSel = concentracionSel;
	}

	public String getMetodoValidadoSelDescrip() {
		return metodoValidadoSelDescrip;
	}

	public void setMetodoValidadoSelDescrip(String metodoValidadoSelDescrip) {
		this.metodoValidadoSelDescrip = metodoValidadoSelDescrip;
	}

	public String[] getMetodoValidadoSelCheckMenu() {
		return metodoValidadoSelCheckMenu;
	}

	public void setMetodoValidadoSelCheckMenu(String[] metodoValidadoSelCheckMenu) {
		this.metodoValidadoSelCheckMenu = metodoValidadoSelCheckMenu;
	}

	public String getNormaMetodoReferencia() {
		return normaMetodoReferencia;
	}

	public void setNormaMetodoReferencia(String normaMetodoReferencia) {
		this.normaMetodoReferencia = normaMetodoReferencia;
	}

	public Boolean getMaterialesReferencia() {
		return materialesReferencia;
	}

	public void setMaterialesReferencia(Boolean materialesReferencia) {
		this.materialesReferencia = materialesReferencia;
	}

	public SelectItem[] getSelectItemSector() {
		return selectItemSector;
	}

	public void setSelectItemSector(SelectItem[] selectItemSector) {
		this.selectItemSector = selectItemSector;
	}

	public SelectItem[] getSelectItemMatriz() {
		return selectItemMatriz;
	}

	public void setSelectItemMatriz(SelectItem[] selectItemMatriz) {
		this.selectItemMatriz = selectItemMatriz;
	}

	public SelectItem[] getSelectItemMensurando() {
		return selectItemMensurando;
	}

	public void setSelectItemMensurando(SelectItem[] selectItemMensurando) {
		this.selectItemMensurando = selectItemMensurando;
	}

	public SelectItem[] getSelectItemTecnica() {
		return selectItemTecnica;
	}

	public void setSelectItemTecnica(SelectItem[] selectItemTecnica) {
		this.selectItemTecnica = selectItemTecnica;
	}

	public SelectItem[] getSelectItemConcentracion() {
		return selectItemConcentracion;
	}

	public void setSelectItemConcentracion(SelectItem[] selectItemConcentracion) {
		this.selectItemConcentracion = selectItemConcentracion;
	}

	public SelectItem[] getSelectItemMetodoValidado() {
		return selectItemMetodoValidado;
	}

	public void setSelectItemMetodoValidado(SelectItem[] selectItemMetodoValidado) {
		this.selectItemMetodoValidado = selectItemMetodoValidado;
	}

	public ArrayList<LaboratorioMetroMedidaMicro> getListaMedidasMicrobilogicas() {
		return listaMedidasMicrobilogicas;
	}

	public void setListaMedidasMicrobilogicas(ArrayList<LaboratorioMetroMedidaMicro> listaMedidasMicrobilogicas) {
		this.listaMedidasMicrobilogicas = listaMedidasMicrobilogicas;
	}

	public ArrayList<LaboratorioMetroMedidaMicro> getListaMedidasMicrobilogicasEliminar() {
		return listaMedidasMicrobilogicasEliminar;
	}

	public void setListaMedidasMicrobilogicasEliminar(
			ArrayList<LaboratorioMetroMedidaMicro> listaMedidasMicrobilogicasEliminar) {
		this.listaMedidasMicrobilogicasEliminar = listaMedidasMicrobilogicasEliminar;
	}

	public LaboratorioMetroMedidaMicro getMedidaMicrobiologicaSeleccionadaTabla() {
		return medidaMicrobiologicaSeleccionadaTabla;
	}

	public void setMedidaMicrobiologicaSeleccionadaTabla(
			LaboratorioMetroMedidaMicro medidaMicrobiologicaSeleccionadaTabla) {
		this.medidaMicrobiologicaSeleccionadaTabla = medidaMicrobiologicaSeleccionadaTabla;
	}

	public String getMaterialReferenciaCepaUtilizadaSel() {
		return materialReferenciaCepaUtilizadaSel;
	}

	public void setMaterialReferenciaCepaUtilizadaSel(String materialReferenciaCepaUtilizadaSel) {
		this.materialReferenciaCepaUtilizadaSel = materialReferenciaCepaUtilizadaSel;
	}

	public String getSectorMicroSel() {
		return sectorMicroSel;
	}

	public void setSectorMicroSel(String sectorMicroSel) {
		this.sectorMicroSel = sectorMicroSel;
	}

	public String getMatrizMicroSel() {
		return matrizMicroSel;
	}

	public void setMatrizMicroSel(String matrizMicroSel) {
		this.matrizMicroSel = matrizMicroSel;
	}

	public String getTipoEnsayoSel() {
		return tipoEnsayoSel;
	}

	public void setTipoEnsayoSel(String tipoEnsayoSel) {
		this.tipoEnsayoSel = tipoEnsayoSel;
	}

	public String getNombreEnsayoMicroSel() {
		return nombreEnsayoMicroSel;
	}

	public void setNombreEnsayoMicroSel(String nombreEnsayoMicroSel) {
		this.nombreEnsayoMicroSel = nombreEnsayoMicroSel;
	}

	public String getTecnicaMicroSel() {
		return tecnicaMicroSel;
	}

	public void setTecnicaMicroSel(String tecnicaMicroSel) {
		this.tecnicaMicroSel = tecnicaMicroSel;
	}

	public String getNormaTecnicaSel() {
		return normaTecnicaSel;
	}

	public void setNormaTecnicaSel(String normaTecnicaSel) {
		this.normaTecnicaSel = normaTecnicaSel;
	}

	public String getMaterialReferenciaCepaCertificadaSel() {
		return materialReferenciaCepaCertificadaSel;
	}

	public void setMaterialReferenciaCepaCertificadaSel(String materialReferenciaCepaCertificadaSel) {
		this.materialReferenciaCepaCertificadaSel = materialReferenciaCepaCertificadaSel;
	}

	public String[] getValidacionSelCheckMenu() {
		return validacionSelCheckMenu;
	}

	public void setValidacionSelCheckMenu(String[] validacionSelCheckMenu) {
		this.validacionSelCheckMenu = validacionSelCheckMenu;
	}

	public String getValidacionDescSel() {
		return validacionDescSel;
	}

	public void setValidacionDescSel(String validacionDescSel) {
		this.validacionDescSel = validacionDescSel;
	}

	public String[] getControCalidadMediosCultivoSelCheckMenu() {
		return controCalidadMediosCultivoSelCheckMenu;
	}

	public void setControCalidadMediosCultivoSelCheckMenu(String[] controCalidadMediosCultivoSelCheckMenu) {
		this.controCalidadMediosCultivoSelCheckMenu = controCalidadMediosCultivoSelCheckMenu;
	}

	public String getControCalidadMediosCultivoDescSel() {
		return controCalidadMediosCultivoDescSel;
	}

	public void setControCalidadMediosCultivoDescSel(String controCalidadMediosCultivoDescSel) {
		this.controCalidadMediosCultivoDescSel = controCalidadMediosCultivoDescSel;
	}

	public String[] getCondicionesAmbientalesSelCheckMenu() {
		return condicionesAmbientalesSelCheckMenu;
	}

	public void setCondicionesAmbientalesSelCheckMenu(String[] condicionesAmbientalesSelCheckMenu) {
		this.condicionesAmbientalesSelCheckMenu = condicionesAmbientalesSelCheckMenu;
	}

	public String getCondicionesAmbientalesDescSel() {
		return condicionesAmbientalesDescSel;
	}

	public void setCondicionesAmbientalesDescSel(String condicionesAmbientalesDescSel) {
		this.condicionesAmbientalesDescSel = condicionesAmbientalesDescSel;
	}

	public SelectItem[] getSelectItemTipoEnsayo() {
		return selectItemTipoEnsayo;
	}

	public void setSelectItemTipoEnsayo(SelectItem[] selectItemTipoEnsayo) {
		this.selectItemTipoEnsayo = selectItemTipoEnsayo;
	}

	public SelectItem[] getSelectItemNombreEnsayo() {
		return selectItemNombreEnsayo;
	}

	public void setSelectItemNombreEnsayo(SelectItem[] selectItemNombreEnsayo) {
		this.selectItemNombreEnsayo = selectItemNombreEnsayo;
	}

	public SelectItem[] getSelectItemTecnicaMicro() {
		return selectItemTecnicaMicro;
	}

	public void setSelectItemTecnicaMicro(SelectItem[] selectItemTecnicaMicro) {
		this.selectItemTecnicaMicro = selectItemTecnicaMicro;
	}

	public SelectItem[] getSelectItemNormaTecnica() {
		return selectItemNormaTecnica;
	}

	public void setSelectItemNormaTecnica(SelectItem[] selectItemNormaTecnica) {
		this.selectItemNormaTecnica = selectItemNormaTecnica;
	}

	public SelectItem[] getSelectItemMateCepaCertificada() {
		return selectItemMateCepaCertificada;
	}

	public void setSelectItemMateCepaCertificada(SelectItem[] selectItemMateCepaCertificada) {
		this.selectItemMateCepaCertificada = selectItemMateCepaCertificada;
	}

	public SelectItem[] getSelectItemValidacion() {
		return selectItemValidacion;
	}

	public void setSelectItemValidacion(SelectItem[] selectItemValidacion) {
		this.selectItemValidacion = selectItemValidacion;
	}

	public SelectItem[] getSelectItemControlCalMedioCultivo() {
		return selectItemControlCalMedioCultivo;
	}

	public void setSelectItemControlCalMedioCultivo(SelectItem[] selectItemControlCalMedioCultivo) {
		this.selectItemControlCalMedioCultivo = selectItemControlCalMedioCultivo;
	}

	public SelectItem[] getSelectItemCondicionesAmbientales() {
		return selectItemCondicionesAmbientales;
	}

	public void setSelectItemCondicionesAmbientales(SelectItem[] selectItemCondicionesAmbientales) {
		this.selectItemCondicionesAmbientales = selectItemCondicionesAmbientales;
	}

	public ArrayList<LaboratorioMetroMedidaFisica> getListaMedidasFisicas() {
		return listaMedidasFisicas;
	}

	public void setListaMedidasFisicas(ArrayList<LaboratorioMetroMedidaFisica> listaMedidasFisicas) {
		this.listaMedidasFisicas = listaMedidasFisicas;
	}

	public ArrayList<LaboratorioMetroMedidaFisica> getListaMedidasFisicasEliminar() {
		return listaMedidasFisicasEliminar;
	}

	public void setListaMedidasFisicasEliminar(ArrayList<LaboratorioMetroMedidaFisica> listaMedidasFisicasEliminar) {
		this.listaMedidasFisicasEliminar = listaMedidasFisicasEliminar;
	}

	public LaboratorioMetroMedidaFisica getMedidaFisicaSeleccionadaTabla() {
		return medidaFisicaSeleccionadaTabla;
	}

	public void setMedidaFisicaSeleccionadaTabla(LaboratorioMetroMedidaFisica medidaFisicaSeleccionadaTabla) {
		this.medidaFisicaSeleccionadaTabla = medidaFisicaSeleccionadaTabla;
	}

	public String getDescripcionMensurandoSel() {
		return descripcionMensurandoSel;
	}

	public void setDescripcionMensurandoSel(String descripcionMensurandoSel) {
		this.descripcionMensurandoSel = descripcionMensurandoSel;
	}

	public String getMagnitudFisicaSel() {
		return magnitudFisicaSel;
	}

	public void setMagnitudFisicaSel(String magnitudFisicaSel) {
		this.magnitudFisicaSel = magnitudFisicaSel;
	}

	public String getCampoAplicacionSel() {
		return campoAplicacionSel;
	}

	public void setCampoAplicacionSel(String campoAplicacionSel) {
		this.campoAplicacionSel = campoAplicacionSel;
	}

	public String getIntervaloPuntoMedMinimoSel() {
		return intervaloPuntoMedMinimoSel;
	}

	public void setIntervaloPuntoMedMinimoSel(String intervaloPuntoMedMinimoSel) {
		this.intervaloPuntoMedMinimoSel = intervaloPuntoMedMinimoSel;
	}

	public String getIntervaloPuntoMedMaximoSel() {
		return intervaloPuntoMedMaximoSel;
	}

	public void setIntervaloPuntoMedMaximoSel(String intervaloPuntoMedMaximoSel) {
		this.intervaloPuntoMedMaximoSel = intervaloPuntoMedMaximoSel;
	}

	public String getUnidadesIntervaloPrefijoSel() {
		return unidadesIntervaloPrefijoSel;
	}

	public void setUnidadesIntervaloPrefijoSel(String unidadesIntervaloPrefijoSel) {
		this.unidadesIntervaloPrefijoSel = unidadesIntervaloPrefijoSel;
	}

	public String getUnidadesIntervaloUnidadSel() {
		return unidadesIntervaloUnidadSel;
	}

	public void setUnidadesIntervaloUnidadSel(String unidadesIntervaloUnidadSel) {
		this.unidadesIntervaloUnidadSel = unidadesIntervaloUnidadSel;
	}

	public String getUnidadesIntervaloSimboloSel() {
		return unidadesIntervaloSimboloSel;
	}

	public void setUnidadesIntervaloSimboloSel(String unidadesIntervaloSimboloSel) {
		this.unidadesIntervaloSimboloSel = unidadesIntervaloSimboloSel;
	}

	public String getIncertidumbreMedicionIncertidumbreSel() {
		return incertidumbreMedicionIncertidumbreSel;
	}

	public void setIncertidumbreMedicionIncertidumbreSel(String incertidumbreMedicionIncertidumbreSel) {
		this.incertidumbreMedicionIncertidumbreSel = incertidumbreMedicionIncertidumbreSel;
	}

	public String getIncertidumbreMedicionValorSel() {
		return incertidumbreMedicionValorSel;
	}

	public void setIncertidumbreMedicionValorSel(String incertidumbreMedicionValorSel) {
		this.incertidumbreMedicionValorSel = incertidumbreMedicionValorSel;
	}

	public String getIncertidumbreMedicionPrefijoSel() {
		return incertidumbreMedicionPrefijoSel;
	}

	public void setIncertidumbreMedicionPrefijoSel(String incertidumbreMedicionPrefijoSel) {
		this.incertidumbreMedicionPrefijoSel = incertidumbreMedicionPrefijoSel;
	}

	public String getIncertidumbreMedicionUnidadSel() {
		return incertidumbreMedicionUnidadSel;
	}

	public void setIncertidumbreMedicionUnidadSel(String incertidumbreMedicionUnidadSel) {
		this.incertidumbreMedicionUnidadSel = incertidumbreMedicionUnidadSel;
	}

	public String getIncertidumbreMedicionSimboloSel() {
		return incertidumbreMedicionSimboloSel;
	}

	public void setIncertidumbreMedicionSimboloSel(String incertidumbreMedicionSimboloSel) {
		this.incertidumbreMedicionSimboloSel = incertidumbreMedicionSimboloSel;
	}

	public String getFactorCoberturaKSel() {
		return factorCoberturaKSel;
	}

	public void setFactorCoberturaKSel(String factorCoberturaKSel) {
		this.factorCoberturaKSel = factorCoberturaKSel;
	}

	public String getNormaDocumentosMetodosReferenciaSel() {
		return normaDocumentosMetodosReferenciaSel;
	}

	public void setNormaDocumentosMetodosReferenciaSel(String normaDocumentosMetodosReferenciaSel) {
		this.normaDocumentosMetodosReferenciaSel = normaDocumentosMetodosReferenciaSel;
	}

	public Boolean getAcreditadoSel() {
		return acreditadoSel;
	}

	public void setAcreditadoSel(Boolean acreditadoSel) {
		this.acreditadoSel = acreditadoSel;
	}

	public String getEntidadAcreditadoraTipoDocSel() {
		return entidadAcreditadoraTipoDocSel;
	}

	public void setEntidadAcreditadoraTipoDocSel(String entidadAcreditadoraTipoDocSel) {
		this.entidadAcreditadoraTipoDocSel = entidadAcreditadoraTipoDocSel;
	}

	public String getEntidadAcreditadoraNumDocSel() {
		return entidadAcreditadoraNumDocSel;
	}

	public void setEntidadAcreditadoraNumDocSel(String entidadAcreditadoraNumDocSel) {
		this.entidadAcreditadoraNumDocSel = entidadAcreditadoraNumDocSel;
	}

	public String getEntidadAcreditadoraNombreSel() {
		return entidadAcreditadoraNombreSel;
	}

	public void setEntidadAcreditadoraNombreSel(String entidadAcreditadoraNombreSel) {
		this.entidadAcreditadoraNombreSel = entidadAcreditadoraNombreSel;
	}

	public SelectItem[] getSelectItemMagnitudFisica() {
		return selectItemMagnitudFisica;
	}

	public void setSelectItemMagnitudFisica(SelectItem[] selectItemMagnitudFisica) {
		this.selectItemMagnitudFisica = selectItemMagnitudFisica;
	}

	public SelectItem[] getSelectItemCampoAplicacion() {
		return selectItemCampoAplicacion;
	}

	public void setSelectItemCampoAplicacion(SelectItem[] selectItemCampoAplicacion) {
		this.selectItemCampoAplicacion = selectItemCampoAplicacion;
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

	public ArrayList<LaboratorioMetroEnsayoFisico> getListaEnsayosFisicos() {
		return listaEnsayosFisicos;
	}

	public void setListaEnsayosFisicos(ArrayList<LaboratorioMetroEnsayoFisico> listaEnsayosFisicos) {
		this.listaEnsayosFisicos = listaEnsayosFisicos;
	}

	public ArrayList<LaboratorioMetroEnsayoFisico> getListaEnsayosFisicosEliminar() {
		return listaEnsayosFisicosEliminar;
	}

	public void setListaEnsayosFisicosEliminar(ArrayList<LaboratorioMetroEnsayoFisico> listaEnsayosFisicosEliminar) {
		this.listaEnsayosFisicosEliminar = listaEnsayosFisicosEliminar;
	}

	public LaboratorioMetroEnsayoFisico getEnsayoFisicoSeleccionadoTabla() {
		return ensayoFisicoSeleccionadoTabla;
	}

	public void setEnsayoFisicoSeleccionadoTabla(LaboratorioMetroEnsayoFisico ensayoFisicoSeleccionadoTabla) {
		this.ensayoFisicoSeleccionadoTabla = ensayoFisicoSeleccionadoTabla;
	}

	public String getNombreEnsayoSel() {
		return nombreEnsayoSel;
	}

	public void setNombreEnsayoSel(String nombreEnsayoSel) {
		this.nombreEnsayoSel = nombreEnsayoSel;
	}

	public String getProductosMaterialAEnsayarSel() {
		return productosMaterialAEnsayarSel;
	}

	public void setProductosMaterialAEnsayarSel(String productosMaterialAEnsayarSel) {
		this.productosMaterialAEnsayarSel = productosMaterialAEnsayarSel;
	}

	public String getPropiedadesMediblesSel() {
		return propiedadesMediblesSel;
	}

	public void setPropiedadesMediblesSel(String propiedadesMediblesSel) {
		this.propiedadesMediblesSel = propiedadesMediblesSel;
	}

	public String getMinimoSel() {
		return minimoSel;
	}

	public void setMinimoSel(String minimoSel) {
		this.minimoSel = minimoSel;
	}

	public String getMaximoSel() {
		return maximoSel;
	}

	public void setMaximoSel(String maximoSel) {
		this.maximoSel = maximoSel;
	}

	public String getUnidadesSel() {
		return unidadesSel;
	}

	public void setUnidadesSel(String unidadesSel) {
		this.unidadesSel = unidadesSel;
	}

	public String getDescripcionSel() {
		return descripcionSel;
	}

	public void setDescripcionSel(String descripcionSel) {
		this.descripcionSel = descripcionSel;
	}

	public String getNormaTecnicaProcedimientoSel() {
		return normaTecnicaProcedimientoSel;
	}

	public void setNormaTecnicaProcedimientoSel(String normaTecnicaProcedimientoSel) {
		this.normaTecnicaProcedimientoSel = normaTecnicaProcedimientoSel;
	}

	public ArrayList<LaboratorioMetroPruebaInterlab> getListaPruebasInterlaboratorios() {
		return listaPruebasInterlaboratorios;
	}

	public void setListaPruebasInterlaboratorios(
			ArrayList<LaboratorioMetroPruebaInterlab> listaPruebasInterlaboratorios) {
		this.listaPruebasInterlaboratorios = listaPruebasInterlaboratorios;
	}

	public ArrayList<LaboratorioMetroPruebaInterlab> getListaPruebasInterlaboratoriosEliminar() {
		return listaPruebasInterlaboratoriosEliminar;
	}

	public void setListaPruebasInterlaboratoriosEliminar(
			ArrayList<LaboratorioMetroPruebaInterlab> listaPruebasInterlaboratoriosEliminar) {
		this.listaPruebasInterlaboratoriosEliminar = listaPruebasInterlaboratoriosEliminar;
	}

	public LaboratorioMetroPruebaInterlab getPruebaInterlaboratorioSeleccionadaTabla() {
		return pruebaInterlaboratorioSeleccionadaTabla;
	}

	public void setPruebaInterlaboratorioSeleccionadaTabla(
			LaboratorioMetroPruebaInterlab pruebaInterlaboratorioSeleccionadaTabla) {
		this.pruebaInterlaboratorioSeleccionadaTabla = pruebaInterlaboratorioSeleccionadaTabla;
	}

	public Boolean getParticipaSel() {
		return participaSel;
	}

	public void setParticipaSel(Boolean participaSel) {
		this.participaSel = participaSel;
	}

	public String getCualSel() {
		return cualSel;
	}

	public void setCualSel(String cualSel) {
		this.cualSel = cualSel;
	}

	public String getDesempenoObtenidoSel() {
		return desempenoObtenidoSel;
	}

	public void setDesempenoObtenidoSel(String desempenoObtenidoSel) {
		this.desempenoObtenidoSel = desempenoObtenidoSel;
	}

	public String getProveedorSel() {
		return proveedorSel;
	}

	public void setProveedorSel(String proveedorSel) {
		this.proveedorSel = proveedorSel;
	}

	public String getNombreProveedorPISel() {
		return nombreProveedorPISel;
	}

	public void setNombreProveedorPISel(String nombreProveedorPISel) {
		this.nombreProveedorPISel = nombreProveedorPISel;
	}

	public String getTipoDocumentoProveedorPISel() {
		return tipoDocumentoProveedorPISel;
	}

	public void setTipoDocumentoProveedorPISel(String tipoDocumentoProveedorPISel) {
		this.tipoDocumentoProveedorPISel = tipoDocumentoProveedorPISel;
	}

	public String getIdentificacionProveedorPISel() {
		return identificacionProveedorPISel;
	}

	public void setIdentificacionProveedorPISel(String identificacionProveedorPISel) {
		this.identificacionProveedorPISel = identificacionProveedorPISel;
	}

	public SelectItem[] getSelectItemDesempeno() {
		return selectItemDesempeno;
	}

	public void setSelectItemDesempeno(SelectItem[] selectItemDesempeno) {
		this.selectItemDesempeno = selectItemDesempeno;
	}

	public SelectItem[] getSelectItemProveedor() {
		return selectItemProveedor;
	}

	public void setSelectItemProveedor(SelectItem[] selectItemProveedor) {
		this.selectItemProveedor = selectItemProveedor;
	}

	public ArrayList<LaboratorioMetroGrupoTrabajo> getListaGruposTrabajo() {
		return listaGruposTrabajo;
	}

	public void setListaGruposTrabajo(ArrayList<LaboratorioMetroGrupoTrabajo> listaGruposTrabajo) {
		this.listaGruposTrabajo = listaGruposTrabajo;
	}

	public ArrayList<LaboratorioMetroGrupoTrabajo> getListaGruposTrabajoEliminar() {
		return listaGruposTrabajoEliminar;
	}

	public void setListaGruposTrabajoEliminar(ArrayList<LaboratorioMetroGrupoTrabajo> listaGruposTrabajoEliminar) {
		this.listaGruposTrabajoEliminar = listaGruposTrabajoEliminar;
	}

	public LaboratorioMetroGrupoTrabajo getGrupoTrabajoSeleccionadoTabla() {
		return grupoTrabajoSeleccionadoTabla;
	}

	public void setGrupoTrabajoSeleccionadoTabla(LaboratorioMetroGrupoTrabajo grupoTrabajoSeleccionadoTabla) {
		this.grupoTrabajoSeleccionadoTabla = grupoTrabajoSeleccionadoTabla;
	}

	public String getTipoGrupoSel() {
		return tipoGrupoSel;
	}

	public void setTipoGrupoSel(String tipoGrupoSel) {
		this.tipoGrupoSel = tipoGrupoSel;
	}

	public String getNombreGrupoSel() {
		return nombreGrupoSel;
	}

	public void setNombreGrupoSel(String nombreGrupoSel) {
		this.nombreGrupoSel = nombreGrupoSel;
	}

	public String getPeriodoParticipacionSel() {
		return periodoParticipacionSel;
	}

	public void setPeriodoParticipacionSel(String periodoParticipacionSel) {
		this.periodoParticipacionSel = periodoParticipacionSel;
	}

	public SelectItem[] getSelectItemTipoGrupo() {
		return selectItemTipoGrupo;
	}

	public void setSelectItemTipoGrupo(SelectItem[] selectItemTipoGrupo) {
		this.selectItemTipoGrupo = selectItemTipoGrupo;
	}

	public SelectItem[] getSelectItemPeriodoParticipacion() {
		return selectItemPeriodoParticipacion;
	}

	public void setSelectItemPeriodoParticipacion(SelectItem[] selectItemPeriodoParticipacion) {
		this.selectItemPeriodoParticipacion = selectItemPeriodoParticipacion;
	}

	public ArrayList<LaboratorioMetroMaterialReferencia> getListaMaterialesReferencia() {
		return listaMaterialesReferencia;
	}

	public void setListaMaterialesReferencia(ArrayList<LaboratorioMetroMaterialReferencia> listaMaterialesReferencia) {
		this.listaMaterialesReferencia = listaMaterialesReferencia;
	}

	public ArrayList<LaboratorioMetroMaterialReferencia> getListaMaterialesReferenciaEliminar() {
		return listaMaterialesReferenciaEliminar;
	}

	public void setListaMaterialesReferenciaEliminar(
			ArrayList<LaboratorioMetroMaterialReferencia> listaMaterialesReferenciaEliminar) {
		this.listaMaterialesReferenciaEliminar = listaMaterialesReferenciaEliminar;
	}

	public LaboratorioMetroMaterialReferencia getMaterialReferenciaSeleccionadaTabla() {
		return materialReferenciaSeleccionadaTabla;
	}

	public void setMaterialReferenciaSeleccionadaTabla(
			LaboratorioMetroMaterialReferencia materialReferenciaSeleccionadaTabla) {
		this.materialReferenciaSeleccionadaTabla = materialReferenciaSeleccionadaTabla;
	}

	public String getNombreMaterialReferenciaSel() {
		return nombreMaterialReferenciaSel;
	}

	public void setNombreMaterialReferenciaSel(String nombreMaterialReferenciaSel) {
		this.nombreMaterialReferenciaSel = nombreMaterialReferenciaSel;
	}

	public String getProductorSel() {
		return productorSel;
	}

	public void setProductorSel(String productorSel) {
		this.productorSel = productorSel;
	}

	public String getProveedorMRSel() {
		return proveedorMRSel;
	}

	public void setProveedorMRSel(String proveedorMRSel) {
		this.proveedorMRSel = proveedorMRSel;
	}

	public String getUsaMaterialReferenciaSel() {
		return usaMaterialReferenciaSel;
	}

	public void setUsaMaterialReferenciaSel(String usaMaterialReferenciaSel) {
		this.usaMaterialReferenciaSel = usaMaterialReferenciaSel;
	}

	public String getTipoMRSel() {
		return tipoMRSel;
	}

	public void setTipoMRSel(String tipoMRSel) {
		this.tipoMRSel = tipoMRSel;
	}

	public Boolean getMaterialDificilAdquisicionSel() {
		return materialDificilAdquisicionSel;
	}

	public void setMaterialDificilAdquisicionSel(Boolean materialDificilAdquisicionSel) {
		this.materialDificilAdquisicionSel = materialDificilAdquisicionSel;
	}

	public Boolean getUsoAseguraTrazabilildadMedicionSel() {
		return usoAseguraTrazabilildadMedicionSel;
	}

	public void setUsoAseguraTrazabilildadMedicionSel(Boolean usoAseguraTrazabilildadMedicionSel) {
		this.usoAseguraTrazabilildadMedicionSel = usoAseguraTrazabilildadMedicionSel;
	}

	public SelectItem[] getSelectItemNecesitaUsaMaterialReferncia() {
		return selectItemNecesitaUsaMaterialReferncia;
	}

	public void setSelectItemNecesitaUsaMaterialReferncia(SelectItem[] selectItemNecesitaUsaMaterialReferncia) {
		this.selectItemNecesitaUsaMaterialReferncia = selectItemNecesitaUsaMaterialReferncia;
	}

	public SelectItem[] getSelectItemTipoMaterialReferencia() {
		return selectItemTipoMaterialReferencia;
	}

	public void setSelectItemTipoMaterialReferencia(SelectItem[] selectItemTipoMaterialReferencia) {
		this.selectItemTipoMaterialReferencia = selectItemTipoMaterialReferencia;
	}

	public ArrayList<LaboratorioMetroProducto> getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(ArrayList<LaboratorioMetroProducto> listaProductos) {
		this.listaProductos = listaProductos;
	}

	public ArrayList<LaboratorioMetroProducto> getListaProductosEliminar() {
		return listaProductosEliminar;
	}

	public void setListaProductosEliminar(ArrayList<LaboratorioMetroProducto> listaProductosEliminar) {
		this.listaProductosEliminar = listaProductosEliminar;
	}

	public LaboratorioMetroProducto getProductoSeleccionadoTabla() {
		return productoSeleccionadoTabla;
	}

	public void setProductoSeleccionadoTabla(LaboratorioMetroProducto productoSeleccionadoTabla) {
		this.productoSeleccionadoTabla = productoSeleccionadoTabla;
	}

	public String getProductoSel() {
		return productoSel;
	}

	public void setProductoSel(String productoSel) {
		this.productoSel = productoSel;
	}

	public String getSubredSel() {
		return subredSel;
	}

	public void setSubredSel(String subredSel) {
		this.subredSel = subredSel;
	}

	public Boolean getExportacionSel() {
		return exportacionSel;
	}

	public void setExportacionSel(Boolean exportacionSel) {
		this.exportacionSel = exportacionSel;
	}

	public String getPaisSel() {
		return paisSel;
	}

	public void setPaisSel(String paisSel) {
		this.paisSel = paisSel;
	}

	public String getCiudadSel() {
		return ciudadSel;
	}

	public void setCiudadSel(String ciudadSel) {
		this.ciudadSel = ciudadSel;
	}

	public UIComponent getAgregarMedidaQuimica() {
		return agregarMedidaQuimica;
	}

	public void setAgregarMedidaQuimica(UIComponent agregarMedidaQuimica) {
		this.agregarMedidaQuimica = agregarMedidaQuimica;
	}

	public UIComponent getAgregarMedidaMicro() {
		return agregarMedidaMicro;
	}

	public void setAgregarMedidaMicro(UIComponent agregarMedidaMicro) {
		this.agregarMedidaMicro = agregarMedidaMicro;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public SelectItem[] getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}

	public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
		this.tipoDocumentoItem = tipoDocumentoItem;
	}

	public UIComponent getAgregarMedidaFisica() {
		return agregarMedidaFisica;
	}

	public void setAgregarMedidaFisica(UIComponent agregarMedidaFisica) {
		this.agregarMedidaFisica = agregarMedidaFisica;
	}

	public UIComponent getAgregarEnsayoFisico() {
		return agregarEnsayoFisico;
	}

	public void setAgregarEnsayoFisico(UIComponent agregarEnsayoFisico) {
		this.agregarEnsayoFisico = agregarEnsayoFisico;
	}

	public UIComponent getAgregarPruebaInterlab() {
		return agregarPruebaInterlab;
	}

	public void setAgregarPruebaInterlab(UIComponent agregarPruebaInterlab) {
		this.agregarPruebaInterlab = agregarPruebaInterlab;
	}

	public UIComponent getAgregarGrupoTrabajo() {
		return agregarGrupoTrabajo;
	}

	public void setAgregarGrupoTrabajo(UIComponent agregarGrupoTrabajo) {
		this.agregarGrupoTrabajo = agregarGrupoTrabajo;
	}

	public UIComponent getAgregarMaterialesReferencia() {
		return agregarMaterialesReferencia;
	}

	public void setAgregarMaterialesReferencia(UIComponent agregarMaterialesReferencia) {
		this.agregarMaterialesReferencia = agregarMaterialesReferencia;
	}

	public UIComponent getAgregarProducto() {
		return agregarProducto;
	}

	public void setAgregarProducto(UIComponent agregarProducto) {
		this.agregarProducto = agregarProducto;
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

	public List<LaboratorioSubred> getListaSubredes() {
		return listaSubredes;
	}

	public void setListaSubredes(List<LaboratorioSubred> listaSubredes) {
		this.listaSubredes = listaSubredes;
	}

	public List<LaboratorioSubred> getListaSubredesEliminar() {
		return listaSubredesEliminar;
	}

	public void setListaSubredesEliminar(List<LaboratorioSubred> listaSubredesEliminar) {
		this.listaSubredesEliminar = listaSubredesEliminar;
	}

	public LaboratorioSubred getSubredEliminar() {
		return subredEliminar;
	}

	public void setSubredEliminar(LaboratorioSubred subredEliminar) {
		this.subredEliminar = subredEliminar;
	}

	public List<LaboratorioActividad> getListaActividades() {
		return listaActividades;
	}

	public void setListaActividades(List<LaboratorioActividad> listaActividades) {
		this.listaActividades = listaActividades;
	}

	public List<LaboratorioActividad> getListaActividadesEliminar() {
		return listaActividadesEliminar;
	}

	public void setListaActividadesEliminar(List<LaboratorioActividad> listaActividadesEliminar) {
		this.listaActividadesEliminar = listaActividadesEliminar;
	}

	public LaboratorioActividad getActividadEliminar() {
		return actividadEliminar;
	}

	public void setActividadEliminar(LaboratorioActividad actividadEliminar) {
		this.actividadEliminar = actividadEliminar;
	}

	public List<LaboratorioCapacitacion> getListaCapacitaciones() {
		return listaCapacitaciones;
	}

	public void setListaCapacitaciones(List<LaboratorioCapacitacion> listaCapacitaciones) {
		this.listaCapacitaciones = listaCapacitaciones;
	}

	public List<LaboratorioCapacitacion> getListaCapacitacionesEliminar() {
		return listaCapacitacionesEliminar;
	}

	public void setListaCapacitacionesEliminar(List<LaboratorioCapacitacion> listaCapacitacionesEliminar) {
		this.listaCapacitacionesEliminar = listaCapacitacionesEliminar;
	}

	public LaboratorioCapacitacion getCapacitacionEliminar() {
		return capacitacionEliminar;
	}

	public void setCapacitacionEliminar(LaboratorioCapacitacion capacitacionEliminar) {
		this.capacitacionEliminar = capacitacionEliminar;
	}

	public List<LaboratorioMuestreo> getListaMuestreos() {
		return listaMuestreos;
	}

	public void setListaMuestreos(List<LaboratorioMuestreo> listaMuestreos) {
		this.listaMuestreos = listaMuestreos;
	}

	public List<LaboratorioMuestreo> getListaMuestreosEliminar() {
		return listaMuestreosEliminar;
	}

	public void setListaMuestreosEliminar(List<LaboratorioMuestreo> listaMuestreosEliminar) {
		this.listaMuestreosEliminar = listaMuestreosEliminar;
	}

	public LaboratorioMuestreo getMuestreoEliminar() {
		return muestreoEliminar;
	}

	public void setMuestreoEliminar(LaboratorioMuestreo muestreoEliminar) {
		this.muestreoEliminar = muestreoEliminar;
	}

	public List<LaboratorioSistemaGestion> getListaSistemasGestion() {
		return listaSistemasGestion;
	}

	public void setListaSistemasGestion(List<LaboratorioSistemaGestion> listaSistemasGestion) {
		this.listaSistemasGestion = listaSistemasGestion;
	}

	public List<LaboratorioSistemaGestion> getListaSistemasGestionEliminar() {
		return listaSistemasGestionEliminar;
	}

	public void setListaSistemasGestionEliminar(List<LaboratorioSistemaGestion> listaSistemasGestionEliminar) {
		this.listaSistemasGestionEliminar = listaSistemasGestionEliminar;
	}

	public LaboratorioSistemaGestion getSistemaGestionEliminar() {
		return sistemaGestionEliminar;
	}

	public void setSistemaGestionEliminar(LaboratorioSistemaGestion sistemaGestionEliminar) {
		this.sistemaGestionEliminar = sistemaGestionEliminar;
	}

	public SelectItem[] getSelectItemPaises() {
		return selectItemPaises;
	}

	public void setSelectItemPaises(SelectItem[] selectItemPaises) {
		this.selectItemPaises = selectItemPaises;
	}

}
