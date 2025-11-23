package co.edu.unal.hermes.vista.laboratorios;

import java.util.List;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.HistoricoAsignacionProyecto;
import co.edu.unal.hermes.modelo.HistoricoEstadoInforme;
import co.edu.unal.hermes.modelo.HistoricoEstadoProyecto;
import co.edu.unal.hermes.modelo.HistoricoHabilitacionEdicionProyecto;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * @author Mauricio Amaya Ríos Fecha ultima modificación: 12-02-2015 Bean:
 *         manejadorConsultaHistoricos
 */

public class ManejadorConsultaHistoricosActividades extends ManejadorBase {

	private String codigo;
	private boolean primerBusquedaHistoricoEstado;
	private boolean primerBusquedaHistoricoAsginacion;
	private boolean primerBusquedaHistoricoHabilitacionEdicionProyecto;
	private boolean primerBusquedaHistoricoInforme;
	List<HistoricoEstadoProyecto> listaHistoricoEstadoProyecto;
	List<HistoricoAsignacionProyecto> listaHistoricoAsignacionProyecto;
	List<HistoricoHabilitacionEdicionProyecto> listaHistoricoHabilitacionEdicionProyecto;
	List<HistoricoEstadoInforme> listaHistoricoEstadoInforme;
	private boolean codigoSesion;
	String error;
	Proyecto proyectoActual = new Proyecto();

	public ManejadorConsultaHistoricosActividades() {
		super();
		codigoSesion = false;
		primerBusquedaHistoricoEstado = true;
		primerBusquedaHistoricoAsginacion = true;
		primerBusquedaHistoricoHabilitacionEdicionProyecto = true;
		cargarDatosSesion();
		try{
		proyectoActual = servicioProyecto.obtenerProyecto(Long.valueOf(codigo),
				 ProyectoDAOHibernate.TODO_POR_ID);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public void cargarDatosSesion() {
		// Se carga código de proyecto desde la sesión.
		if (sesion.getAttribute("pry_id") != null 
				|| sesion.getAttribute("pin_id") != null) {
			try {
				Long idCodigo = null;
				if(sesion.getAttribute("pry_id") != null){
					idCodigo = (Long) sesion.getAttribute("pry_id");
					sesion.removeAttribute("pry_id");
				}
				if(sesion.getAttribute("pin_id") != null){
					idCodigo = (Long) sesion.getAttribute("pin_id");
					sesion.removeAttribute("pin_id");
				}
				if(idCodigo != null){
					codigo = idCodigo.toString();
					codigoSesion = true;
				}
			} catch (NumberFormatException nfe) {
				codigoSesion = false;
				codigo = "";
			}
		}
		// Se valida si se desea buscar historico estado proyecto
		if (sesion.getAttribute("buscarHistoricoEstado") != null) {
			sesion.removeAttribute("buscarHistoricoEstado");
			buscarEstadoProyecto();
		}
		// Se valida si se desea buscar historico asignación
		if (sesion.getAttribute("buscarHistoricoAsignacion") != null) {
			sesion.removeAttribute("buscarHistoricoAsignacion");
			buscarAsignacionProyectos();
		}
		// Se valida si se desea buscar historico estado proyecto
		if (sesion.getAttribute("buscarHistoricoEdicion") != null) {
			sesion.removeAttribute("buscarHistoricoEdicion");
			buscarHabilitacionEdicionProyectos();
		}
		// Se valida si se desea buscar historico estado informe
		if (sesion.getAttribute("buscarHistoricoInforme") != null) {
			sesion.removeAttribute("buscarHistoricoInforme");
			buscarEstadoInforme();
		}
	}

	public void buscarAsignacionProyectos() {
		primerBusquedaHistoricoAsginacion = false;
		Long id = validarCodigo();
		if (error.equals("")) {
			Proyecto proyecto = new Proyecto();
			proyecto.setId(id);
			listaHistoricoAsignacionProyecto = servicioProyecto
					.obtenerHistoricosAsignacionProyecto(id);
		}
	}

	public void buscarEstadoProyecto() {
		primerBusquedaHistoricoEstado = false;
		Long id = validarCodigo();
		if (error.equals("")) {
			Proyecto proyecto = new Proyecto();
			proyecto.setId(id);
			listaHistoricoEstadoProyecto = servicioProyecto
					.obtenerHistoricosEstadoProyecto(proyecto);
		}
	}

	public void buscarEstadoInforme() {
		primerBusquedaHistoricoInforme = false;
		Long id = validarCodigo();
		if (error.equals("")) {
			Proyecto proyecto = new Proyecto();
			proyecto.setId(id);
			listaHistoricoEstadoInforme = servicioProyecto
					.obtenerHistoricosEstadoInforme(id);
		}
	}

	public void buscarHabilitacionEdicionProyectos() {
		primerBusquedaHistoricoHabilitacionEdicionProyecto = false;
		Long id = validarCodigo();
		if (error.equals("")) {
			Proyecto proyecto = new Proyecto();
			proyecto.setId(id);
			listaHistoricoHabilitacionEdicionProyecto = servicioProyecto
					.obtenerHistoricosHabilitacionEdicionProyecto(id);
		}
	}

	private Long validarCodigo() {
		error = "";
		Long id = 0L;
		if (codigo != null && codigo.length() > 0) {
			codigo = codigo.trim();
			try {
				id = Long.parseLong(codigo);
			} catch (NumberFormatException nfe) {
				error = "Ha ingresado un valor incorrecto.";
			}
			if (id.equals(0L)) {
				error = "Ha ingresado un valor incorrecto.";
			}
		}
		return id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigoProyecto) {
		this.codigo = codigoProyecto;
	}

	public List<HistoricoEstadoProyecto> getListaHistoricoEstadoProyecto() {
		return listaHistoricoEstadoProyecto;
	}

	public boolean isPrimerBusquedaHistoricoEstado() {
		return primerBusquedaHistoricoEstado;
	}

	public List<HistoricoAsignacionProyecto> getListaHistoricoAsignacionProyecto() {
		return listaHistoricoAsignacionProyecto;
	}

	public boolean isPrimerBusquedaHistoricoAsginacion() {
		return primerBusquedaHistoricoAsginacion;
	}

	public List<HistoricoHabilitacionEdicionProyecto> getListaHistoricoHabilitacionEdicionProyecto() {
		return listaHistoricoHabilitacionEdicionProyecto;
	}

	public boolean isPrimerBusquedaHistoricoHabilitacionEdicionProyecto() {
		return primerBusquedaHistoricoHabilitacionEdicionProyecto;
	}

	public List<HistoricoEstadoInforme> getListaHistoricoEstadoInforme() {
		return listaHistoricoEstadoInforme;
	}

	public boolean isCodigoSesion() {
		return codigoSesion;
	}

	public boolean isPrimerBusquedaHistoricoInforme() {
		return primerBusquedaHistoricoInforme;
	}

	public Proyecto getProyectoActual() {
		return proyectoActual;
	}

	public void setProyectoActual(Proyecto proyectoActual) {
		this.proyectoActual = proyectoActual;
	}

}
