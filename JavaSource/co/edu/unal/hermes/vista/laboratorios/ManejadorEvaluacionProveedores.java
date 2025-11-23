/**
 * @author dgbenitezc
 */

package co.edu.unal.hermes.vista.laboratorios;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.event.SelectEvent;

import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.Empresa;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.laboratorios.EvaluacionProveedores;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.ManejadorBase;
import co.edu.unal.hermes.vista.utils.Util;

public class ManejadorEvaluacionProveedores extends ManejadorBase {

	private EvaluacionProveedores evaluacionProveedor;
	private SelectItem[] mecanismoAdquisicionSelectItem;
	private SelectItem[] sedeSelectItem;
	private SelectItem[] facultadSelectItem;
	private SelectItem[] departamentoSelectItem;
	private SelectItem[] tipoAdquisicionSelectItem;
	private SelectItem[] tipoCalidadSelectItem;
	private SelectItem[] tipoOportunidadSelectItem;
	private SelectItem[] tipoDocumentosSelectItem;
	private SelectItem[] tipoComportamientoSelectItem;
	private SelectItem[] tipoAsesoriaSelectItem;
	private SelectItem[] tipoRecomendacionSelectItem;
	private Integer annioActual;
	private Boolean evaluacionGuardada = true;
	private List<EvaluacionProveedores> listaEvaluaciones;
	private EvaluacionProveedores evaluacionSeleccionada;
	private Boolean esDNL;
	private Boolean esDLS;

	public ManejadorEvaluacionProveedores() {
		constructor();
	}

	public void constructor() {

		System.out.println("ManejadorEvaluacionProveedores.constructor");

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		String today = formatter.format(new java.util.Date());
		annioActual = Integer.valueOf(today);

		evaluacionProveedor = new EvaluacionProveedores();
		evaluacionProveedor.setAnnioOrden(annioActual);
		evaluacionProveedor.setNumeroOrden(0);
		sedeSelectItem = servicioGeneral.selectItemSedes();
		personaActual = (Persona) sesion.getAttribute("persona");

		esDNL = (Boolean) sesion.getAttribute("esLaboratorios");
		esDLS = (Boolean) sesion.getAttribute("esLaboratoriosSede");
		System.out.println("esDNL:" + esDNL);
		System.out.println("esDLS:" + esDLS);

		if (personaActual instanceof InvestigadorInterno) {
			System.out.println("Es InvestigadorInterno");

			Sede sede = ((InvestigadorInterno) personaActual).getDependencia()
					.getSede();
			// Nivel nacional no tiene facultades:
			if (sede.getId().equals(Sede.NIVEL_NACIONAL)) {
				sede = new Sede(Sede.BOGOTA);
			}
			evaluacionProveedor.setSede(sede);
			cambiarSede();
			if (((InvestigadorInterno) personaActual).getDependencia()
					.getFacultad() != null) {
				evaluacionProveedor
						.setFacultad(((InvestigadorInterno) personaActual)
								.getDependencia().getFacultad());
				cambiarFacultad();
			}
			if (((InvestigadorInterno) personaActual).getDependencia()
					.getDepartamento() != null) {
				String departamentoSeleccionadoString = ((InvestigadorInterno) personaActual)
						.getDependencia().getDepartamento();
				evaluacionProveedor.setDepartamento(servicioDependencia
						.obtenerDependencia(departamentoSeleccionadoString));
			}
			if (((InvestigadorInterno) personaActual).getTipoCargo() != null) {
				evaluacionProveedor
						.setCargo(((InvestigadorInterno) personaActual)
								.getTipoCargo().getNombre());
			}

		}

		if (evaluacionProveedor.getSede() == null) {
			evaluacionProveedor.setSede(new Sede((Long) sedeSelectItem[0]
					.getValue()));
			cambiarSede();
		}

		if (evaluacionProveedor.getFacultad() == null) {
			evaluacionProveedor.setFacultad(new Dependencia(
					(String) facultadSelectItem[0].getValue()));
			cambiarFacultad();
		}

		if (evaluacionProveedor.getDepartamento() == null) {
			if (departamentoSelectItem.length > 0) {
				evaluacionProveedor.setDepartamento(new Dependencia(
						(String) departamentoSelectItem[0].getValue()));
			} else {
				evaluacionProveedor.setDepartamento(new Dependencia());
			}
		}

		mecanismoAdquisicionSelectItem = servicioGeneral
				.selectItemHijosDeTiposValorObjeto(Tipos.TIPOS_MECANISMOS_ADQUISICION_EQUIPOS);

		tipoAdquisicionSelectItem = servicioGeneral
				.selectItemHijosDeTiposValorObjeto(Tipos.TIPOS_ADQUISICIONES_LABORATORIOS);

		if (evaluacionProveedor.getEmpresa() == null) {
			System.out.println("Empresa nula.");
			evaluacionProveedor.setEmpresa(new Empresa());
		}

		/*
		 * tipoCalidadSelectItem = servicioGeneral
		 * .retornaSelectItemArregloDeHijosDeTipos
		 * (Tipos.TIPOS_FRECUENCIAS_EVALUACION); tipoOportunidadSelectItem =
		 * servicioGeneral .retornaSelectItemArregloDeHijosDeTipos(Tipos.
		 * TIPOS_FRECUENCIAS_EVALUACION); tipoDocumentosSelectItem =
		 * servicioGeneral .retornaSelectItemArregloDeHijosDeTipos(Tipos.
		 * TIPOS_FRECUENCIAS_EVALUACION); tipoComportamientoSelectItem =
		 * servicioGeneral .retornaSelectItemArregloDeHijosDeTipos(Tipos.
		 * TIPOS_FRECUENCIAS_EVALUACION); tipoAsesoriaSelectItem =
		 * servicioGeneral .retornaSelectItemArregloDeHijosDeTipos(Tipos.
		 * TIPOS_FRECUENCIAS_EVALUACION);
		 */

		tipoCalidadSelectItem = selectItemUnoCinco();
		tipoOportunidadSelectItem = selectItemUnoCinco();
		tipoDocumentosSelectItem = selectItemUnoCinco();
		tipoComportamientoSelectItem = selectItemUnoCinco();
		tipoAsesoriaSelectItem = selectItemUnoCinco();
		tipoRecomendacionSelectItem = servicioGeneral
				.retornaSelectItemArregloDeHijosDeTipos(Tipos.TIPOS_SI_NO);

		cargarListaEvaluaciones();
	}

	public SelectItem[] selectItemUnoCinco() {
		SelectItem[] si = new SelectItem[5];
		for (Integer i = 1; i <= 5; i++) {
			System.out.println("i: " + i);
			System.out.println("i.tostring: " + i.toString());
			si[i - 1] = new SelectItem(i, i.toString());
		}
		return si;
	}

	public void nuevaEncuesta() {
		System.out.println("ManejadorEvaluacionProveedores.nuevaEncuesta in");
		constructor();
		evaluacionGuardada = false;
		System.out.println("ManejadorEvaluacionProveedores.nuevaEncuesta out");
	}

	public void cancelar() {
		System.out.println("ManejadorEvaluacionProveedores.cancelar in");
		evaluacionGuardada = true;
		constructor();
		System.out.println("ManejadorEvaluacionProveedores.cancelar out");
	}

	public void reporte() {
		System.out.println("ManejadorEvaluacionProveedores.reporte");
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("idReporte", evaluacionSeleccionada.getId()
				.toString());
		System.out.println("r.getParametros():" + r.getParametros());
		r.setNombreReporte("/laboratorios/ReporteEvaluacionProveedores");
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().dispatch("/ReporteEngineServlet");
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			context.responseComplete();
		}
	}

	public void cargarListaEvaluaciones() {
		String from = "FROM EvaluacionProveedores ";
		String where;

		// DNL ve todas, los demás solamente las propias;
		if (esDNL) {
			where = "";
		} else {
			where = " WHERE documento = '"
					+ personaActual.getId().getDocumento()
					+ "' AND tipoDocumento = '"
					+ personaActual.getId().getTipoDocumento() + "' ";
		}

		String orderBy = " ORDER BY id DESC ";
		String hql = from + where + orderBy;
		System.out.println("hql:" + hql);
		listaEvaluaciones = servicioGeneral.obtenerObjetos(
				EvaluacionProveedores.class, hql);

		// se fija el nombre del evaluador, para poder mostrarlo en la lista:
		for (EvaluacionProveedores ep : listaEvaluaciones) {
			List<Persona> personas;
			String sqlBuscar = "select #nombre1 p.nombre1, #nombre2 p.nombre2, #apellido1 p.apellido1, #apellido2 p.apellido2 from Persona p where p.id.documento = '"
					+ ep.getDocumento()
					+ "' and p.id.tipoDocumento = '"
					+ ep.getTipoDocumento() + "'";
			System.out.println("sqlBuscar:" + sqlBuscar);

			personas = servicioGeneral.obtenerObjetosLimitado(Persona.class,
					sqlBuscar);
			Persona perso1 = personas.get(0);
			ep.setNombreEvaluador(perso1.getNombreCompletoMinusculas());
		}

	}

	public void handleSelectEmpresa(SelectEvent event) {
		String nombre = (String) event.getObject();
		Empresa empresaDistribuidora = servicioGeneral
				.buscarEmpresaXNombre(nombre);
		evaluacionProveedor.setEmpresa(empresaDistribuidora);
	}

	public void cambiarSede() {
		Long idSede = evaluacionProveedor.getSede().getId();
		System.out.println("cambiarSede: " + idSede);
		facultadSelectItem = servicioGeneral.selectItemFacultades(idSede);
		String id = (String) facultadSelectItem[0].getValue();
		System.out.println("cambiarSede: " + id);
		evaluacionProveedor.setFacultad(new Dependencia(id));
		cambiarFacultad();
	}

	public void cambiarFacultad() {
		if (evaluacionProveedor.getFacultad() != null
				&& evaluacionProveedor.getFacultad().getId() != null) {
			String idFacultad = evaluacionProveedor.getFacultad().getId();
			System.out.println("cambiarFacultad: " + idFacultad);
			departamentoSelectItem = servicioGeneral
					.selectItemDepartamentos(idFacultad);
			if (departamentoSelectItem.length > 0) {
				evaluacionProveedor.setDepartamento(new Dependencia(
						(String) departamentoSelectItem[0].getValue()));
			} else {
				evaluacionProveedor.setDepartamento(new Dependencia());
			}
		}
	}

	public void cambiarDepartamento() {
		System.out.println("cambiarDepartamento:");
	}

	public Boolean validar() {
		Boolean validar = true;

		if (evaluacionProveedor.getEmpresa().getId() == null) {
			System.out.println("cambiarSede evaluacionProveedor.getEmpresa(): "
					+ evaluacionProveedor.getEmpresa());
			System.out
					.println("cambiarSede evaluacionProveedor.getEmpresa().id: "
							+ evaluacionProveedor.getEmpresa().getId());
			System.out.println("No se ha seleccionado empresa.");
			validar = false;
		}
		return validar;
	}

	public void guardar() {
		if (validar()) {
			evaluacionProveedor.setDocumento(personaActual.getId()
					.getDocumento());
			evaluacionProveedor.setTipoDocumento(personaActual.getId()
					.getTipoDocumento());
			evaluacionProveedor.setOtrosProductos(Util
					.removerEnter(evaluacionProveedor.getOtrosProductos()));
			evaluacionProveedor.setFechaRegistro(new Date());

			// Fijar DLS:
			if (esDLS && personaActual instanceof InvestigadorInterno) {
				System.out.println("Fijando DLS:");
				Sede sede = ((InvestigadorInterno) personaActual)
						.getDependencia().getSede();
				String hql = "FROM Dependencia d WHERE UPPER(d.nombre) like '%LABORATORIOS SEDE%' AND d.sede.id = '"
						+ sede.getId() + "'";
				System.out.println(hql);
				List<Dependencia> listaDLSs = servicioGeneral.obtenerObjetos(
						Dependencia.class, hql);
				if (listaDLSs.size() > 0) {
					Dependencia DLS = listaDLSs.get(0);
					evaluacionProveedor.setFacultad(DLS);
					System.out.println("Fijando DLS:" + DLS.getNombre());
				}
			}

			servicioGeneral.guardarObjeto(evaluacionProveedor);
			mensajeInfo("La evaluación ha sido guardada con id "
					+ evaluacionProveedor.getId());
			evaluacionGuardada = true;
			cargarListaEvaluaciones();
		}
	}

	public String salir() {
		borrarSesion();
		return "misProyectos";
	}

	public void borrarSesion() {
		sesion.removeAttribute("manejadorEvaluacionProveedores");
	}

	/**
	 * @return the mecanismoAdquisicionSelectItem
	 */
	public SelectItem[] getMecanismoAdquisicionSelectItem() {
		return mecanismoAdquisicionSelectItem;
	}

	/**
	 * @return the evaluacionProveedor
	 */
	public EvaluacionProveedores getEvaluacionProveedor() {
		return evaluacionProveedor;
	}

	/**
	 * @param evaluacionProveedor
	 *            the evaluacionProveedor to set
	 */
	public void setEvaluacionProveedor(EvaluacionProveedores evaluacionProveedor) {
		this.evaluacionProveedor = evaluacionProveedor;
	}

	/**
	 * @return the sedeSelectItem
	 */
	public SelectItem[] getSedeSelectItem() {
		return sedeSelectItem;
	}

	/**
	 * @return the facultadSelectItem
	 */
	public SelectItem[] getFacultadSelectItem() {
		return facultadSelectItem;
	}

	/**
	 * @return the departamentoSelectItem
	 */
	public SelectItem[] getDepartamentoSelectItem() {
		return departamentoSelectItem;
	}

	/**
	 * @return the tipoAdquisicionSelectItem
	 */
	public SelectItem[] getTipoAdquisicionSelectItem() {
		return tipoAdquisicionSelectItem;
	}

	/**
	 * @return the tipoCalidadSelectItem
	 */
	public SelectItem[] getTipoCalidadSelectItem() {
		return tipoCalidadSelectItem;
	}

	/**
	 * @return the tipoOportunidadSelectItem
	 */
	public SelectItem[] getTipoOportunidadSelectItem() {
		return tipoOportunidadSelectItem;
	}

	/**
	 * @return the tipoDocumentosSelectItem
	 */
	public SelectItem[] getTipoDocumentosSelectItem() {
		return tipoDocumentosSelectItem;
	}

	/**
	 * @return the tipoComportamientoSelectItem
	 */
	public SelectItem[] getTipoComportamientoSelectItem() {
		return tipoComportamientoSelectItem;
	}

	/**
	 * @return the tipoAsesoriaSelectItem
	 */
	public SelectItem[] getTipoAsesoriaSelectItem() {
		return tipoAsesoriaSelectItem;
	}

	/**
	 * @return the tipoRecomendacionSelectItem
	 */
	public SelectItem[] getTipoRecomendacionSelectItem() {
		return tipoRecomendacionSelectItem;
	}

	/**
	 * @return the annioActual
	 */
	public Integer getAnnioActual() {
		return annioActual;
	}

	/**
	 * @return the evaluacionGuardada
	 */
	public Boolean getEvaluacionGuardada() {
		return evaluacionGuardada;
	}

	/**
	 * @return the listaEvaluaciones
	 */
	public List<EvaluacionProveedores> getListaEvaluaciones() {
		return listaEvaluaciones;
	}

	/**
	 * @return the evaluacionSeleccionada
	 */
	public EvaluacionProveedores getEvaluacionSeleccionada() {
		return evaluacionSeleccionada;
	}

	/**
	 * @param evaluacionSeleccionada
	 *            the evaluacionSeleccionada to set
	 */
	public void setEvaluacionSeleccionada(
			EvaluacionProveedores evaluacionSeleccionada) {
		this.evaluacionSeleccionada = evaluacionSeleccionada;
	}

}
