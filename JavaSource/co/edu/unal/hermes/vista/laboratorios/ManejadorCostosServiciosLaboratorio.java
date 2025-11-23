package co.edu.unal.hermes.vista.laboratorios;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.primefaces.event.RowEditEvent;

import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.laboratorios.CostoInsumoServicio;
import co.edu.unal.hermes.modelo.laboratorios.InsumoLaboratorio;
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioCostosGestion;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioCostosMantenimientoCalibracion;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioCostosPersonal;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioCostosSedeAnnio;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioCostosServicio;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleEnsayosServicios;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleEquipos;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioEnsayoEquipo;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * @author dgbenitezc
 * 
 */
public class ManejadorCostosServiciosLaboratorio extends ManejadorBase {

	private Boolean esLaboratoriosSede = false;
	private Boolean esLaboratoriosNacional = false;
	private Boolean esConsultaLaboratorios = false;
	private Boolean esCoordinadorLaboratorio = false;
	private Boolean soloLectura = false;

	private List<LaboratorioCostosServicio> listaCostos;
	private LaboratorioCostosServicio costoSeleccionado;
	private LaboratorioCostosSedeAnnio costosSedeAnnio;
	private List<LaboratorioEnsayoEquipo> listaEquiposEnsayo;
	private List<CostoInsumoServicio> listaInsumosEnsayo;
	private List<CostoInsumoServicio> listaInsumosEnsayoEliminados;
	private CostoInsumoServicio insumoEnsayoSeleccionado;
	private LaboratorioCostosPersonal costoPersonalSeleccionado;

	private List<LaboratorioCostosPersonal> listaCostosPersonal;
	private List<LaboratorioCostosMantenimientoCalibracion> listaCostosManttoCalib;
	private List<LaboratorioCostosGestion> listaCostosGestion;
	private List<LaboratorioDetalleEnsayosServicios> listaServiciosCandidatos;
	private List<LaboratorioDetalleEnsayosServicios> listaServiciosCandidatosFiltrados;
	private LaboratorioDetalleEnsayosServicios servicioSeleccionado;
	private Integer annioActual;

	private List<Boolean> listaFalsa;

	private Float totalCostoEnergia;
	private Float totalDepreciacion;
	private Float totalCostoInsumos;
	private Float totalDepreciacionPlanta;
	private Integer totalTiempoUsoEquipos;
	private Float totalCostoPersonal;
	private Float totalCostoServicio;
	private Float totalCostoDirecto;
	private Float totalCostoIndirecto;
	private Float totalCostoIluminacion;
	private Float totalAcueductoAlcantarillado;
	private Float totalCostoManttoCalib;
	private Float totalCostoGestion;

	private Boolean cambiosEnEquipos;
	private Boolean cambiosEnIluminacion;
	private Boolean cambiosEnInsumos;
	private Boolean cambiosEnAcuedAlcant;
	private Boolean cambiosEnPersonal;
	private Boolean cambiosEnMantto;
	private Boolean cambiosEnGestion;

	private CostoInsumoServicio nuevoInsumo;
	private SelectItem[] selectItemInsumos;
	private List<InsumoLaboratorio> listaInsumos;
	private LaboratorioCostosPersonal nuevoPersonal;
	private LaboratorioCostosMantenimientoCalibracion nuevoMantto;
	private SelectItem[] selectItemEquipos;
	private SelectItem[] selectItemActividades;
	private LaboratorioCostosGestion nuevoGestion;
	
	Boolean mostrarListaEnsayos;
	Long idSedeCoordinador;

	public ManejadorCostosServiciosLaboratorio() {
		System.out.println("ManejadorCostosServiciosLaboratorio");

		cambiosAFalse();
		nuevoInsumo();
		nuevoPersonal();
		nuevoMantto();
		nuevoGestion();

		esLaboratoriosSede = (Boolean) sesion
				.getAttribute("esLaboratoriosSede");
		esLaboratoriosNacional = (Boolean) sesion
				.getAttribute("esLaboratorios");
		esConsultaLaboratorios = (Boolean) sesion
				.getAttribute("esConsultaLaboratorios");
		esCoordinadorLaboratorio = (Boolean) sesion
				.getAttribute("esCoordinadorLaboratorio");

		System.out.println("esLaboratoriosSede: " + esLaboratoriosSede);
		System.out.println("esLaboratoriosNacional: " + esLaboratoriosNacional);
		System.out.println("esConsultaLaboratorios: " + esConsultaLaboratorios);
		System.out.println("esCoordinadorLaboratorio: "
				+ esCoordinadorLaboratorio);
		System.out.println("personaActual: " + personaActual);
		personaActual = (Persona) sesion.getAttribute("persona");
		System.out.println("personaActual: " + personaActual);
		
		mostrarListaEnsayos = false;
		if(esLaboratoriosSede || esLaboratoriosNacional || esCoordinadorLaboratorio)
			mostrarListaEnsayos = true;
		
		idSedeCoordinador = servicioPersona.obtenerInvestigadorInterno(personaActual.getId()).getDependencia().getSede().getId();
		
		String where = "";
		if (esLaboratoriosSede) {
			where = " WHERE LCS.servicio.laboratorio.sede.id = '" + idSedeCoordinador + "'";
		}

		String idLabs = null;
		if (esCoordinadorLaboratorio) {
			idLabs = (String) sesion.getAttribute("idLabs");
			System.out.println("*** idLabs: " + idLabs);
			if (idLabs != null) {
				idLabs = idLabs.trim().replace(" ", ", ");
				System.out.println("*** idLabs: " + idLabs);
				where = " WHERE LCS.servicio.laboratorio.id in (" + idLabs
						+ ")";
			}
		}

		// lista de todos los servicios a los cuales se les ha realizado estudio
		// de costos:
		String hql = "FROM LaboratorioCostosServicio LCS"
				+ where
				+ " ORDER BY LCS.annio DESC, LCS.servicio.laboratorio.sede.nombre, LCS.servicio.laboratorio.nombre, LCS.servicio.nombre";
		System.out.println("hql: " + hql);
		listaCostos = servicioGeneral.obtenerObjetos(
				LaboratorioCostosServicio.class, hql);
		System.out.println("listaCostos.size(): " + listaCostos.size());

		annioActual = Calendar.getInstance().get(Calendar.YEAR);
		System.out.println("annioActual: " + annioActual);
		String idsServicioCosto = "";
		if (listaCostos.size() > 0) {
			for (LaboratorioCostosServicio lcs : listaCostos) {
				if (lcs.getAnnio().equals(annioActual)) {
					idsServicioCosto += "'" + lcs.getServicio().getId() + "' ";
					System.out.println("idsServicioCosto: " + idsServicioCosto);
				}
			}
			idsServicioCosto = idsServicioCosto.trim().replace(" ", ",");
			System.out.println("idsServicioCosto: " + idsServicioCosto);
		}

		listaFalsa = new ArrayList<Boolean>();
		listaFalsa.add(false);

		// selectitem insumos
		
		// Sedes en las cuales es posible realizar estudio de costos:
		// (existe el registro en LaboratorioCostosSedeAnnio para el ano
		// actual)
		String hqlBuscarSedes = "SELECT #sede lcsa.sede FROM LaboratorioCostosSedeAnnio lcsa WHERE lcsa.annio = '"
				+ annioActual + "'";
		System.out.println("hqlBuscarSedes:" + hqlBuscarSedes);
		List<LaboratorioCostosSedeAnnio> listaLCSA = servicioGeneral
				.obtenerObjetosLimitado(LaboratorioCostosSedeAnnio.class,
						hqlBuscarSedes);
		System.out.println("listaLCSA.size(): " + listaLCSA.size());

		String mensajeListaEnsayos = "";

		String idSedes = "";
		if (listaLCSA.size() > 0) {
			for (LaboratorioCostosSedeAnnio s : listaLCSA) {
				System.out.println("s(): " + s);
				System.out.println("s..getSede(): " + s.getSede());
				idSedes += "'" + s.getSede().getId() + "' ";
				System.out.println("idsedes(): " + idSedes);
			}
			idSedes = idSedes.trim().replace(" ", ",");
			System.out.println("idsedes(): " + idSedes);
		}	

		// crear lista de servicios a los que se les puede calcular costos:
//		if (esCoordinadorLaboratorio) {
			
				// lista de laboratorios del coordinador, de esa(s) sede(s)
		String hqlBuscar = "";
		if (esCoordinadorLaboratorio) {
				hqlBuscar = "select #id l.id from Laboratorio l where l.id in ("+ idLabs + ") and l.sede.id in (" + idSedes + ")";
				System.out.println("hqlBuscar:" + hqlBuscar);
		}
		
		if (esLaboratoriosSede) {
//				Long idSedeCoordinador = servicioPersona.obtenerInvestigadorInterno(personaActual.getId()).getDependencia().getSede().getId();
				hqlBuscar = "select #id l.id from Laboratorio l where l.sede.id in ("+ idSedeCoordinador + ") and l.sede.id in (" + idSedes + ")";
				System.out.println("hqlBuscar:" + hqlBuscar);
		}
		
		if (esLaboratoriosNacional) {
			hqlBuscar = "select #id l.id from Laboratorio l where l.sede.id in (" + idSedes + ")";
			System.out.println("hqlBuscar:" + hqlBuscar);
		}
		
		List<Laboratorio> labs = servicioGeneral.obtenerObjetosLimitado(Laboratorio.class, hqlBuscar);
		System.out.println("labs.size(): " + labs.size());

				if (labs.size() > 0) {
					String idsLabs = "";
					for (Laboratorio l : labs) {
						idsLabs += "'" + l.getId() + "' ";
						System.out.println("l(): " + l);
						System.out.println("l.getID(): " + l.getId());
						System.out.println("l.getSede(): " + l.getSede());
						System.out.println("idsLabs(): " + idsLabs);
					}
					System.out.println("idsLabs(): " + idsLabs);
					idsLabs = idsLabs.trim().replace(" ", ",");
					System.out.println("idsLabs(): " + idsLabs);

					// lista de los servicios de esos laboratorios, se excluyen
					// los que
					String hqlBuscarServ = "";
//					if (esCoordinadorLaboratorio) 
//						hqlBuscarServ = "select #id s.id, #nombre s.nombre, #laboratorio s.laboratorio from LaboratorioDetalleEnsayosServicios s where s.laboratorio.id in ("+ idsLabs + ")";
//					
//					if (idsServicioCosto != "") {
//						hqlBuscarServ += " and s.id not in ("
//								+ idsServicioCosto + ")";
//					}
//					
//					if (esLaboratoriosNacional) 
//						hqlBuscarServ = "select #id s.id, #nombre s.nombre, #laboratorio s.laboratorio from LaboratorioDetalleEnsayosServicios s where s.laboratorio.id in ("+ idsLabs + ")";
//					
//					if (esLaboratoriosSede)
//					{	
//						hqlBuscarServ = "select #id s.id, #nombre s.nombre, #laboratorio s.laboratorio from LaboratorioDetalleEnsayosServicios s where s.laboratorio.sede "
//								+ "in("+servicioPersona.obtenerInvestigadorInterno(personaActual.getId()).getDependencia().getSede().getId() + ")";
//					}	
					
					hqlBuscarServ = "select #id s.id, #nombre s.nombre, #laboratorio s.laboratorio from LaboratorioDetalleEnsayosServicios s where s.laboratorio.id in ("+ idsLabs + ")";
					
					if (idsServicioCosto != "") {
						hqlBuscarServ += " and s.id not in ("
								+ idsServicioCosto + ")";
					}
					
					// ordenar por nombre del servicio:
					hqlBuscarServ += " ORDER BY s.nombre";

					System.out.println("hqlBuscarServ:" + hqlBuscarServ);
					listaServiciosCandidatos = servicioGeneral
							.obtenerObjetosLimitado(
									LaboratorioDetalleEnsayosServicios.class,
									hqlBuscarServ);
					System.out.println("listaServiciosCandidatos.size(): "
							+ listaServiciosCandidatos.size());

					if (listaServiciosCandidatos.size() > 0) {

						for (LaboratorioDetalleEnsayosServicios s : listaServiciosCandidatos) {
							System.out.println("s(): " + s);
							System.out.println("s.getID(): " + s.getId());
							System.out.println("s.getNombre: " + s.getNombre());
						}

					} else {
						mensajeListaEnsayos = "No existen Servicios asociados a su(s) Laboratorio(s).  Debe ingresar por Administrar Laboratorios, editar el deseado, y crear un nuevo servicio.";
					}

				} else {
					mensajeListaEnsayos = "No se puede realizar estudio de Costos para su(s) Laboratorio(s), favor comunicarse con el soporte Hermes.";
				}

//			} else {
//				mensajeListaEnsayos = "No se puede realizar estudio de Costos para ninguna Sede, favor comunicarse con el soporte Hermes.";
//			}

			if (mensajeListaEnsayos != "") {
				mensajeError(mensajeListaEnsayos);
			}

	}

	public String salir() {
		limpiarSesion();
		return "buscarCostosServiciosLaboratorio";
	}

	public void limpiarSesion() {
		sesion.removeAttribute("manejadorCostosServiciosLaboratorio");
	}

	public String crearCostos() {
		sesion.setAttribute("soloLectura", false);
		soloLectura = false;
		System.out.println("crearCostos servicioSeleccionado: "+ servicioSeleccionado);

		// nuevo estudio de costos:
		costoSeleccionado = new LaboratorioCostosServicio();
		costoSeleccionado.setAnnio(annioActual);
		costoSeleccionado.setServicio(servicioSeleccionado);
		costoSeleccionado.setIluminacionPotenciaW(0F);
		costoSeleccionado.setIluminacionTiempoMinutos(0);
		costoSeleccionado.setConsumoAcueductoM3(0F);
		costoSeleccionado.setConsumoAlcantarilladoM3(0F);

		// costos sede año
		String hql = "FROM LaboratorioCostosSedeAnnio lcsa WHERE lcsa.annio = '"
				+ annioActual
				+ "' AND lcsa.sede.id = '"
				+ servicioSeleccionado.getLaboratorio().getSede().getId() + "'";
		System.out.println("hql:" + hql);
		// seguro hay uno y solo uno?
		costosSedeAnnio = servicioGeneral.obtenerObjetos(LaboratorioCostosSedeAnnio.class, hql).get(0);
		costoSeleccionado.setValorM2(costosSedeAnnio.getValorM2());

		cargarDatosCostoSeleccionado();

		return "costosServiciosLaboratorio";
	}

	public String editarCostos() {
		// limpiarSesion();
		sesion.setAttribute("soloLectura", false);
		soloLectura = false;
		System.out.println("editarCostos costoSeleccionado: "
				+ costoSeleccionado);
		if (cargarDatosCostoSeleccionado()) {
			return "costosServiciosLaboratorio";
		} else {
			return null;
		}
	}

	public String consultarCostos() {
		sesion.setAttribute("soloLectura", true);
		soloLectura = true;
		System.out.println("consultarCostos costoSeleccionado: "
				+ costoSeleccionado);
		if (cargarDatosCostoSeleccionado()) {
			return "costosServiciosLaboratorio";
		} else {
			return null;
		}
	}

	public Boolean validar() {
		// TODO: qué se debe validar????
		return true;
	}

	public void nuevoInsumo() {
		nuevoInsumo = new CostoInsumoServicio();
		nuevoInsumo.setCantidadUnidadesInsumo(0F);
		nuevoInsumo.setCostoUnidad(0F);
		nuevoInsumo.setInsumo(new InsumoLaboratorio());
	}

	public void nuevoPersonal() {
		nuevoPersonal = new LaboratorioCostosPersonal();
		nuevoPersonal.setSalarioMensual(0L);
		nuevoPersonal.setTiempoMinutos(0);
	}

	public void nuevoMantto() {
		nuevoMantto = new LaboratorioCostosMantenimientoCalibracion();
		nuevoMantto.setCostoAnual(0F);
		nuevoMantto.setEquipo(new LaboratorioDetalleEquipos());
	}

	public void nuevoGestion() {
		nuevoGestion = new LaboratorioCostosGestion();
		nuevoGestion.setCostoAnual(0F);
		nuevoGestion.setTiempoUsoMinutos(0);
	}

	public void cambiosAFalse() {
		cambiosEnEquipos = false;
		cambiosEnIluminacion = false;
		cambiosEnInsumos = false;
		cambiosEnAcuedAlcant = false;
		cambiosEnPersonal = false;
		cambiosEnMantto = false;
		cambiosEnGestion = false;
	}

	public void guardar() {

		Boolean guardar = (cambiosEnEquipos || cambiosEnIluminacion
				|| cambiosEnInsumos || cambiosEnAcuedAlcant
				|| cambiosEnPersonal || cambiosEnMantto || cambiosEnGestion);
		Boolean validar = validar();

		if (guardar && validar) {

			System.out.println("guardar:");
			System.out.println("guardar costoSeleccionado.getId():"
					+ costoSeleccionado.getId());

			if (cambiosEnEquipos) {
				System.out.println("Guardando equipos:");
				for (LaboratorioEnsayoEquipo lee : listaEquiposEnsayo) {
					// valor, vida util, potencia:
					servicioGeneral.guardarObjeto(lee.getEquipo());
					// tiempo de uso:
					servicioGeneral.guardarObjeto(lee);
				}
			}

			if (cambiosEnIluminacion) {
				System.out.println("cambiosEnIluminacion:");
			}

			if (cambiosEnAcuedAlcant) {
				System.out.println("cambiosEnAcuedAlcant:");
			}

			costoSeleccionado.setFechaRegistro(new Date());

			// TODO: cambiar boolean guardarObjeto
			Boolean guardado = true;

			servicioGeneral.guardarObjeto(costoSeleccionado);

			if (cambiosEnPersonal) {
				System.out.println("guardar personal:");
				// guardar personal
				for (LaboratorioCostosPersonal lcp : listaCostosPersonal) {
					lcp.setCostosServicio(costoSeleccionado);
					servicioGeneral.guardarObjeto(lcp);
				}
			}

			if (cambiosEnInsumos) {
				System.out.println("guardar insumo-servicio:" + guardado);
				// guardar insumo-servicio
				for (CostoInsumoServicio cis : listaInsumosEnsayo) {
					cis.setCostosServicio(costoSeleccionado);
					servicioGeneral.guardarObjeto(cis);
				}

				for (CostoInsumoServicio cise : listaInsumosEnsayoEliminados) {
					System.out.println("eliminando insumo-servicio:" + cise);
					if (cise.getCostosServicio() != null) {
						servicioGeneral.eliminarObjeto(cise);
					}
				}
			}

			if (cambiosEnMantto) {
				System.out.println("cambiosEnMantto:");
				// guardar actividades
				for (LaboratorioCostosMantenimientoCalibracion lcmc : listaCostosManttoCalib) {
					lcmc.setCostosServicio(costoSeleccionado);
					servicioGeneral.guardarObjeto(lcmc);
				}
			}

			if (cambiosEnGestion) {
				System.out.println("cambiosEnGestion:");
				for (LaboratorioCostosGestion lcg : listaCostosGestion) {
					lcg.setCostosServicio(costoSeleccionado);
					servicioGeneral.guardarObjeto(lcg);
				}
			}

			if (guardado) {
				mensajeInfo("Estudio guardado.");
				cambiosAFalse();
			}

			System.out.println("guardado:" + guardado);
			System.out.println("guardar costoSeleccionado.getId():"
					+ costoSeleccionado.getId());

		} else if (!guardar) {
			mensajeInfo("No se han realizado cambios.");

		} else if (!validar) {
			mensajeInfo("No se han superado las validaciones para guardar.");
		}
	}

	public void onRowEditEquipos(RowEditEvent event) {
		cambiosEnEquipos = true;
		calcularTotales();
	}
	
	public void onRowEditPersonal(RowEditEvent event) {
		cambiosEnPersonal = true;
		calcularTotales();
	}

	public void onRowEditIluminacion(RowEditEvent event) {
		cambiosEnIluminacion = true;
		calcularTotales();
	}

	public Boolean cargarDatosCostoSeleccionado() {

		// costos por sede:
		String hql4 = "FROM LaboratorioCostosSedeAnnio LCSA WHERE LCSA.sede.id = '"
				+ costoSeleccionado.getServicio().getLaboratorio().getSede()
						.getId()
				+ "' AND LCSA.annio = '"
				+ costoSeleccionado.getAnnio() + "'";
		System.out.println("hql4: " + hql4);
		try {
			costosSedeAnnio = servicioGeneral.obtenerObjetos(
					LaboratorioCostosSedeAnnio.class, hql4).get(0);
		} catch (Exception e) {
			System.out.println("Error obteniendo LaboratorioCostosSedeAnnio");
			mensajeError("No se han creado en el sistema los valores para la sede "
					+ costoSeleccionado.getServicio().getLaboratorio()
							.getSede().getNombre()
					+ " y el año "
					+ costoSeleccionado.getAnnio());
			return false;
		}

		String hql = "FROM LaboratorioEnsayoEquipo LEE WHERE LEE.servicio.id = '"
				+ costoSeleccionado.getServicio().getId()
				+ "' ORDER BY LEE.equipo.equipo";
		System.out.println("hql: " + hql);
		listaEquiposEnsayo = servicioGeneral.obtenerObjetos(
				LaboratorioEnsayoEquipo.class, hql);
		System.out.println("listaEquiposEnsayo.size(): "
				+ listaEquiposEnsayo.size());

		/*
		 * totalCostoEnergia = 0F; totalDepreciacion = 0F; totalCostoInsumos =
		 * 0F; totalTiempoUsoEquipos = 0; totalCostoPersonal = 0F;
		 * totalCostoServicio = 0F; totalCostoIluminacion = 0F;
		 * 
		 * // fijar valor kWh: // calcular total energía // calcular total
		 * depreciación // calcular total tiempo uso equipos for
		 * (LaboratorioEnsayoEquipo lee : listaEquiposEnsayo) {
		 * lee.setValorkWh(costosSedeAnnio.getValorkWh()); totalCostoEnergia +=
		 * lee.getCostoEnergia(); totalDepreciacion += lee.getDepreciacion();
		 * totalTiempoUsoEquipos += lee.getTiempoUsoMinutos(); }
		 */

		Boolean nuevoEstudioCostos = (costoSeleccionado.getId() == null);
		System.out.println("nuevoEstudioCostos: " + nuevoEstudioCostos);

		// cargar insumos:
		if (nuevoEstudioCostos) {
			listaInsumosEnsayo = new ArrayList<CostoInsumoServicio>();
		} else {
			String hql2 = "FROM CostoInsumoServicio CIS WHERE CIS.costosServicio.id = '"
					+ costoSeleccionado.getId()
					+ "' ORDER BY CIS.insumo.nombre";
			System.out.println("hql2: " + hql2);
			listaInsumosEnsayo = servicioGeneral.obtenerObjetos(
					CostoInsumoServicio.class, hql2);
		}
		System.out.println("listaInsumosEnsayo.size(): "
				+ listaInsumosEnsayo.size());
		listaInsumosEnsayoEliminados = new ArrayList<CostoInsumoServicio>();

		/*
		 * // calcular total insumos for (CostoInsumoServicio cis :
		 * listaInsumosEnsayo) { totalCostoInsumos += cis.getCostoInsumo(); }
		 * 
		 * // depreciación planta: totalDepreciacionPlanta =
		 * (costoSeleccionado.getServicio() .getLaboratorio().getAreaM2()
		 * costoSeleccionado.getValorM2() / 10512000) totalTiempoUsoEquipos;
		 */

		// cargar costos personal
		if (nuevoEstudioCostos) {
			listaCostosPersonal = new ArrayList<LaboratorioCostosPersonal>();
		} else {
			String hql3 = "FROM LaboratorioCostosPersonal LCP WHERE LCP.costosServicio.id = '"
					+ costoSeleccionado.getId() + "' ORDER BY LCP.id";
			System.out.println("hql3: " + hql3);
			listaCostosPersonal = servicioGeneral.obtenerObjetos(
					LaboratorioCostosPersonal.class, hql3);
		}
		System.out.println("listaCostosPersonal.size(): "
				+ listaCostosPersonal.size());

		// cargar costos mantto:
		if (nuevoEstudioCostos) {
			listaCostosManttoCalib = new ArrayList<LaboratorioCostosMantenimientoCalibracion>();
		} else {
			String hql5 = "FROM LaboratorioCostosMantenimientoCalibracion LCMC WHERE LCMC.costosServicio.id = '"
					+ costoSeleccionado.getId()
					+ "' ORDER BY LCMC.equipo.equipo";
			System.out.println("hql5: " + hql5);
			listaCostosManttoCalib = servicioGeneral.obtenerObjetos(
					LaboratorioCostosMantenimientoCalibracion.class, hql5);
		}
		System.out.println("listaCostosManttoCalib.size(): "
				+ listaCostosManttoCalib.size());

		// fijar tiempoUsoMinutos
		for (LaboratorioCostosMantenimientoCalibracion lcmc : listaCostosManttoCalib) {
			System.out.println("listaCostosManttoCalib:" + lcmc.getEquipo());
			for (LaboratorioEnsayoEquipo leq : listaEquiposEnsayo) {
				System.out.println("listaEquiposEnsayo:" + leq.getEquipo());
				if (leq.getEquipo().equals(lcmc.getEquipo())) {
					System.out.println("equals: "
							+ lcmc.getEquipo().getEquipo());
					lcmc.setTiempoUsoMinutos(leq.getTiempoUsoMinutos());
				}
			}
		}

		// cargar costos gestión:
		if (nuevoEstudioCostos) {
			listaCostosGestion = new ArrayList<LaboratorioCostosGestion>();
		} else {
			String hql6 = "FROM LaboratorioCostosGestion LCG WHERE LCG.costosServicio.id = '"
					+ costoSeleccionado.getId()
					+ "' ORDER BY LCG.nombreGestion";
			System.out.println("hql6: " + hql6);
			listaCostosGestion = servicioGeneral.obtenerObjetos(
					LaboratorioCostosGestion.class, hql6);
		}

		System.out.println("listaCostosGestion.size(): "
				+ listaCostosGestion.size());

		calcularTotales();

		// selectitem insumos:
		String hql7 = "FROM InsumoLaboratorio WHERE activo = 1 ORDER BY nombre";
		System.out.println("hql7: " + hql7);

		listaInsumos = servicioGeneral.obtenerObjetos(InsumoLaboratorio.class,
				hql7);
		selectItemInsumos = new SelectItem[listaInsumos.size()];
		int i = 0;
		for (InsumoLaboratorio tD : listaInsumos) {
			selectItemInsumos[i] = new SelectItem(tD.getId(), tD.getNombre()
					+ " / " + tD.getUnidadDeMedida().getNombre());
			i++;
		}

		// selectItem de equipos
		if (!soloLectura) {
			selectItemEquipos = new SelectItem[listaEquiposEnsayo.size()];
			int j = 0;
			for (LaboratorioEnsayoEquipo les : listaEquiposEnsayo) {
				selectItemEquipos[j] = new SelectItem(les.getEquipo().getId(),
						les.getEquipo().getNombreCorto() + " ("
								+ les.getEquipo().getPlaca() + ")");
				j++;
			}

			selectItemActividades = servicioGeneral
					.selectItemHijosDeTiposValorObjeto(Tipos.TIPOS_ACTIVIDADES_EQUIPOS_LABORATORIO);

		}

		return true;
	}

	public void cambioAcuedAlcant() {
		System.out.println("cambioAcuedAlcant:");
		cambiosEnAcuedAlcant = true;
		calcularTotales();
	}

	public void agregarGestion() {
		System.out.println("agregarGestion:");
		Boolean validarGestion = true;

		if (nuevoGestion.getNombreGestion() == "") {
			mensajeError("form:itNombreGestion",
					"Debe ingresar un nombre para la actividad.");
			validarGestion = false;
		}
		if (nuevoGestion.getCostoAnual() <= 0) {
			mensajeError("form:itCostoAnualGestion",
					"El costo anual debe ser mayor a cero.");
			validarGestion = false;
		}
		if (nuevoGestion.getTiempoUsoMinutos() <= 0) {
			mensajeError("form:itTiempoMinutosGestion",
					"El tiempo de uso debe ser mayor a cero.");
			validarGestion = false;
		}

		if (validarGestion) {
			listaCostosGestion.add(nuevoGestion);
			nuevoGestion();
			calcularTotales();
			cambiosEnGestion = true;
		}
	}

	public void agregarCostoMantto() {
		System.out.println("agregarCostoMantto:");
		Boolean validarMantto = true;

		if (nuevoMantto.getCostoAnual() <= 0) {
			mensajeError("form:itCostoAnual",
					"El costo anual debe ser mayor a cero.");
			validarMantto = false;
		}

		// TODO: validar pk equipo/actividad.

		if (validarMantto) {
			// fijar equipo:
			bucleEquipos: for (LaboratorioEnsayoEquipo les : listaEquiposEnsayo) {
				LaboratorioDetalleEquipos lde = les.getEquipo();
				if (nuevoMantto.getEquipo().getId().equals(lde.getId())) {
					nuevoMantto.setEquipo(lde);
					nuevoMantto.setTiempoUsoMinutos(les.getTiempoUsoMinutos());
					break bucleEquipos;
				}
			}

			listaCostosManttoCalib.add(nuevoMantto);
			nuevoMantto();
			calcularTotales();
			cambiosEnMantto = true;
		}
	}

	public void agregarInsumo() {
		Boolean validarInsumo = true;
		if (nuevoInsumo.getCostoInsumo() <= 0) {
			mensajeError("form:itCostoInsumo",
					"El costo debe ser mayor a cero.");
			validarInsumo = false;
		}
		if (nuevoInsumo.getCantidadUnidadesInsumo() <= 0) {
			mensajeError("form:itCantidadInsumo",
					"La cantidad debe ser mayor a cero.");
			validarInsumo = false;
		}
		for (CostoInsumoServicio cis : listaInsumosEnsayo) {
			if (cis.getInsumo().getId().equals(nuevoInsumo.getInsumo().getId())) {
				mensajeError("form:siInsumos", "El insumo ya fue asociado.");
				validarInsumo = false;
				break;
			}
		}

		if (validarInsumo) {
			for (InsumoLaboratorio il : listaInsumos) {
				System.out.println("busca Insummo:");
				if (il.getId().equals(nuevoInsumo.getInsumo().getId())) {
					nuevoInsumo.setInsumo(il);
					break;
				}
			}
			listaInsumosEnsayo.add(nuevoInsumo);
			nuevoInsumo();
			calcularTotales();
			cambiosEnInsumos = true;
		}
	}

	public void eliminarInsumo() {
		System.out.println("eliminarInsumo in:");
		System.out.println("eliminarInsumo listaInsumosEnsayoEliminados: "
				+ listaInsumosEnsayoEliminados.size());
		listaInsumosEnsayoEliminados.add(insumoEnsayoSeleccionado);
		System.out.println("eliminarInsumo listaInsumosEnsayoEliminados: "
				+ listaInsumosEnsayoEliminados.size());
		System.out.println("eliminarInsumo listaInsumosEnsayo: "
				+ listaInsumosEnsayo.size());
		listaInsumosEnsayo.remove(insumoEnsayoSeleccionado);
		System.out.println("eliminarInsumo listaInsumosEnsayo: "
				+ listaInsumosEnsayo.size());
		cambiosEnInsumos = true;
		calcularTotales();
		System.out.println("eliminarInsumo out.");
	}

	public void agregarPersonal() {
		System.out.println("agregarPersonal:");
		Boolean validarPersonal = true;

		if (nuevoPersonal.getNombrePersonal() == "") {
			validarPersonal = false;
			mensajeError("form:itNombrePersonal",
					"Debe ingresar la descripción del personal.");
		}
		if (nuevoPersonal.getSalarioMensual() <= 0) {
			validarPersonal = false;
			mensajeError("form:itSalarioMensual",
					"El salario mensual debe ser mayor que cero.");
		}
		if (nuevoPersonal.getTiempoMinutos() <= 0) {
			validarPersonal = false;
			mensajeError("form:itTiempoMinutos",
					"El tiempo invertido en el servicio debe ser mayor que cero.");
		}

		if (validarPersonal) {
			listaCostosPersonal.add(nuevoPersonal);
			nuevoPersonal();
			cambiosEnPersonal = true;
			calcularTotales();
		}

	}
	
	public void eliminarPersonal() {
		System.out.println("eliminarPersonal:");
		listaCostosPersonal.remove(costoPersonalSeleccionado);
		cambiosEnPersonal = true;
		calcularTotales();
	}

	public void calcularTotales() {

		System.out.println("calcularTotales in:");

		totalCostoEnergia = 0F;
		totalDepreciacion = 0F;
		totalCostoInsumos = 0F;
		totalTiempoUsoEquipos = 0;
		totalCostoPersonal = 0F;
		totalCostoDirecto = 0F;
		totalCostoIndirecto = 0F;
		totalCostoServicio = 0F;
		totalCostoIluminacion = 0F;

		// fijar valor kWh:
		// calcular total energía
		// calcular total depreciación
		// calcular total tiempo uso equipos
		for (LaboratorioEnsayoEquipo lee : listaEquiposEnsayo) {
			lee.setValorkWh(costosSedeAnnio.getValorkWh());
			totalCostoEnergia += lee.getCostoEnergia();
			totalDepreciacion += lee.getDepreciacion();
			totalTiempoUsoEquipos += lee.getTiempoUsoMinutos();
		}

		// calcular total insumos
		for (CostoInsumoServicio cis : listaInsumosEnsayo) {
			totalCostoInsumos += cis.getCostoInsumo();
		}

		// depreciación planta:
		totalDepreciacionPlanta = (costoSeleccionado.getServicio()
				.getLaboratorio().getAreaM2()
				* costoSeleccionado.getValorM2() / 10512000)
				* totalTiempoUsoEquipos;

		for (LaboratorioCostosPersonal lcp : listaCostosPersonal) {
			totalCostoPersonal += lcp.getCosto();
		}

		totalCostoIluminacion = (costoSeleccionado.getIluminacionPotenciaW()
				* costosSedeAnnio.getValorkWh() * costoSeleccionado
				.getIluminacionTiempoMinutos()) / 60000;

		totalAcueductoAlcantarillado = (costosSedeAnnio.getValorM3Acueducto() * costoSeleccionado
				.getConsumoAcueductoM3())
				+ (costosSedeAnnio.getValorM3Alcantarillado() * costoSeleccionado
						.getConsumoAlcantarilladoM3());

		totalCostoManttoCalib = 0F;
		
		for (LaboratorioCostosMantenimientoCalibracion lcmc : listaCostosManttoCalib) {
		    int tiempoUsoMinutos = 0;
	        if(lcmc.getTiempoUsoMinutos() != null){
	            tiempoUsoMinutos = lcmc.getTiempoUsoMinutos();
	        }
			totalCostoManttoCalib += lcmc.getCostoMinuto()
					* tiempoUsoMinutos;
		}

		totalCostoGestion = 0F;
		for (LaboratorioCostosGestion lcg : listaCostosGestion) {
			totalCostoGestion += lcg.getCostoMinuto()
					* lcg.getTiempoUsoMinutos();
		}

		// calcular totales:
		totalCostoDirecto = totalCostoEnergia + totalDepreciacion
				+ totalDepreciacionPlanta + totalCostoInsumos
				+ totalCostoPersonal + totalCostoIluminacion
				+ totalAcueductoAlcantarillado + totalCostoManttoCalib
				+ totalCostoGestion;

		totalCostoIndirecto = totalCostoDirecto * 0.05F;

		totalCostoServicio = totalCostoDirecto + totalCostoIndirecto;

		System.out.println("calcularTotales out: " + totalCostoServicio);

	}

	/**
	 * @return the listaCostos
	 */
	public List<LaboratorioCostosServicio> getListaCostos() {
		return listaCostos;
	}

	/**
	 * @param listaCostos
	 *            the listaCostos to set
	 */
	public void setListaCostos(List<LaboratorioCostosServicio> listaCostos) {
		this.listaCostos = listaCostos;
	}

	/**
	 * @param costoSeleccionado
	 *            the costoSeleccionado to set
	 */
	public void setCostoSeleccionado(LaboratorioCostosServicio costoSeleccionado) {
		this.costoSeleccionado = costoSeleccionado;
	}

	/**
	 * @return the costoSeleccionado
	 */
	public LaboratorioCostosServicio getCostoSeleccionado() {
		return costoSeleccionado;
	}

	/**
	 * @return the listaEquiposEnsayo
	 */
	public List<LaboratorioEnsayoEquipo> getListaEquiposEnsayo() {
		return listaEquiposEnsayo;
	}

	/**
	 * @return the totalCostoEnergia
	 */
	public Float getTotalCostoEnergia() {
		return totalCostoEnergia;
	}

	/**
	 * @return the totalDepreciacion
	 */
	public Float getTotalDepreciacion() {
		return totalDepreciacion;
	}

	/**
	 * @return the listaInsumosEnsayo
	 */
	public List<CostoInsumoServicio> getListaInsumosEnsayo() {
		return listaInsumosEnsayo;
	}

	/**
	 * @return the totalCostoInsumos
	 */
	public Float getTotalCostoInsumos() {
		return totalCostoInsumos;
	}

	/**
	 * @return the totalDepreciacionPlanta
	 */
	public Float getTotalDepreciacionPlanta() {
		return totalDepreciacionPlanta;
	}

	/**
	 * @return the listaFalsa
	 */
	public List<Boolean> getListaFalsa() {
		return listaFalsa;
	}

	/**
	 * @return the totalTiempoUsoEquipos
	 */
	public Integer getTotalTiempoUsoEquipos() {
		return totalTiempoUsoEquipos;
	}

	/**
	 * @return the listaCostosPersonal
	 */
	public List<LaboratorioCostosPersonal> getListaCostosPersonal() {
		return listaCostosPersonal;
	}

	/**
	 * @return the totalCostoPersonal
	 */
	public Float getTotalCostoPersonal() {
		return totalCostoPersonal;
	}

	/**
	 * @return the totalCostoServicio
	 */
	public Float getTotalCostoServicio() {
		return totalCostoServicio;
	}

	/**
	 * @return the totalCostoIluminacion
	 */
	public Float getTotalCostoIluminacion() {
		return totalCostoIluminacion;
	}

	/**
	 * @return the totalAcueductoAlcantarillado
	 */
	public Float getTotalAcueductoAlcantarillado() {
		return totalAcueductoAlcantarillado;
	}

	/**
	 * @return the costosSedeAnnio
	 */
	public LaboratorioCostosSedeAnnio getCostosSedeAnnio() {
		return costosSedeAnnio;
	}

	/**
	 * @return the listaCostosManttoCalib
	 */
	public List<LaboratorioCostosMantenimientoCalibracion> getListaCostosManttoCalib() {
		return listaCostosManttoCalib;
	}

	/**
	 * @return the totalCostoManttoCalib
	 */
	public Float getTotalCostoManttoCalib() {
		return totalCostoManttoCalib;
	}

	/**
	 * @return the totalCostoGestion
	 */
	public Float getTotalCostoGestion() {
		return totalCostoGestion;
	}

	/**
	 * @return the listaCostosGestion
	 */
	public List<LaboratorioCostosGestion> getListaCostosGestion() {
		return listaCostosGestion;
	}

	/**
	 * @return the listaServiciosCandidatos
	 */
	public List<LaboratorioDetalleEnsayosServicios> getListaServiciosCandidatos() {
		return listaServiciosCandidatos;
	}

	/**
	 * @return the annioActual
	 */
	public Integer getAnnioActual() {
		return annioActual;
	}

	/**
	 * @return the servicioSeleccionado
	 */
	public LaboratorioDetalleEnsayosServicios getServicioSeleccionado() {
		return servicioSeleccionado;
	}

	/**
	 * @param servicioSeleccionado
	 *            the servicioSeleccionado to set
	 */
	public void setServicioSeleccionado(
			LaboratorioDetalleEnsayosServicios servicioSeleccionado) {
		this.servicioSeleccionado = servicioSeleccionado;
	}

	/**
	 * @return the soloLectura
	 */
	public Boolean getSoloLectura() {
		return soloLectura;
	}

	/**
	 * @return the nuevoInsumo
	 */
	public CostoInsumoServicio getNuevoInsumo() {
		return nuevoInsumo;
	}

	/**
	 * @return the selectItemInsumos
	 */
	public SelectItem[] getSelectItemInsumos() {
		return selectItemInsumos;
	}

	/**
	 * @return the nuevoPersonal
	 */
	public LaboratorioCostosPersonal getNuevoPersonal() {
		return nuevoPersonal;
	}

	/**
	 * @return the nuevoMantto
	 */
	public LaboratorioCostosMantenimientoCalibracion getNuevoMantto() {
		return nuevoMantto;
	}

	/**
	 * @return the esCoordinadorLaboratorio
	 */
	public Boolean getEsCoordinadorLaboratorio() {
		return esCoordinadorLaboratorio;
	}

	/**
	 * @return the selectItemEquipos
	 */
	public SelectItem[] getSelectItemEquipos() {
		return selectItemEquipos;
	}

	/**
	 * @return the selectItemActividades
	 */
	public SelectItem[] getSelectItemActividades() {
		return selectItemActividades;
	}

	/**
	 * @return the nuevoCostoGestion
	 */
	public LaboratorioCostosGestion getNuevoGestion() {
		return nuevoGestion;
	}

	/**
	 * @return the totalCostoDirecto
	 */
	public Float getTotalCostoDirecto() {
		return totalCostoDirecto;
	}

	/**
	 * @return the totalCostoIndirecto
	 */
	public Float getTotalCostoIndirecto() {
		return totalCostoIndirecto;
	}

	/**
	 * @return the insumoEnsayoSeleccionado
	 */
	public CostoInsumoServicio getInsumoEnsayoSeleccionado() {
		return insumoEnsayoSeleccionado;
	}

	/**
	 * @param insumoEnsayoSeleccionado
	 *            the insumoEnsayoSeleccionado to set
	 */
	public void setInsumoEnsayoSeleccionado(
			CostoInsumoServicio insumoEnsayoSeleccionado) {
		this.insumoEnsayoSeleccionado = insumoEnsayoSeleccionado;
	}

	/**
	 * @return the listaServiciosCandidatosFiltrados
	 */
	public List<LaboratorioDetalleEnsayosServicios> getListaServiciosCandidatosFiltrados() {
		return listaServiciosCandidatosFiltrados;
	}

	/**
	 * @param listaServiciosCandidatosFiltrados
	 *            the listaServiciosCandidatosFiltrados to set
	 */
	public void setListaServiciosCandidatosFiltrados(
			List<LaboratorioDetalleEnsayosServicios> listaServiciosCandidatosFiltrados) {
		this.listaServiciosCandidatosFiltrados = listaServiciosCandidatosFiltrados;
	}

	public Boolean getMostrarListaEnsayos() {
		return mostrarListaEnsayos;
	}

	public void setMostrarListaEnsayos(Boolean mostrarListaEnsayos) {
		this.mostrarListaEnsayos = mostrarListaEnsayos;
	}

	public Long getIdSedeCoordinador() {
		return idSedeCoordinador;
	}

	public void setIdSedeCoordinador(Long idSedeCoordinador) {
		this.idSedeCoordinador = idSedeCoordinador;
	}

	public LaboratorioCostosPersonal getCostoPersonalSeleccionado() {
		return costoPersonalSeleccionado;
	}

	public void setCostoPersonalSeleccionado(LaboratorioCostosPersonal costoPersonalSeleccionado) {
		this.costoPersonalSeleccionado = costoPersonalSeleccionado;
	}
	
	

}