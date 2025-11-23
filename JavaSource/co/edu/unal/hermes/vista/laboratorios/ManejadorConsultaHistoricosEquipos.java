package co.edu.unal.hermes.vista.laboratorios;

import java.util.List;

import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleEquipos;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioLogEquipos;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorConsultaHistoricosEquipos extends ManejadorBase {

	LaboratorioDetalleEquipos EquipoActual = new LaboratorioDetalleEquipos();
	List<LaboratorioLogEquipos> listaHistoricoEstadoEquipo;
	
	public ManejadorConsultaHistoricosEquipos() {
		super();
	
		try{
		EquipoActual = (LaboratorioDetalleEquipos) sesion.getAttribute("equipo");
		buscarHistoricoEquipo();
		
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public void buscarHistoricoEquipo() {
		listaHistoricoEstadoEquipo = servicioGeneral.obtenerHistoricoEstadosEquipo(EquipoActual.getId());
	}
	
	public String volverAdministrarEquipos()
	{
		sesion.removeAttribute("manejadorConsultaHistoricosEquipos");
		return "laboratorioEquipos";
	}

	public LaboratorioDetalleEquipos getEquipoActual() {
		return EquipoActual;
	}

	public void setEquipoActual(LaboratorioDetalleEquipos equipoActual) {
		EquipoActual = equipoActual;
	}

	public List<LaboratorioLogEquipos> getListaHistoricoEstadoEquipo() {
		return listaHistoricoEstadoEquipo;
	}

	public void setListaHistoricoEstadoEquipo(
			List<LaboratorioLogEquipos> listaHistoricoEstadoEquipo) {
		this.listaHistoricoEstadoEquipo = listaHistoricoEstadoEquipo;
	}
}

	