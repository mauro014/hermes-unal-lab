package co.edu.unal.hermes.vista.laboratorios;

import java.util.List;

import javax.faces.model.SelectItem;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleEquipos;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorCriticidadEquipos extends ManejadorLaboratorios {

	public LaboratorioDetalleEquipos equipo;
	
	protected SelectItem[] listaImpactoOperaLab;
	protected SelectItem[] listaImpactoSegUsuarios;
	protected SelectItem[] listaImpactoDaniosInfra;
	protected SelectItem[] listaImpactoDaniosAmbient;
	protected SelectItem[] listaImpactoImagenUN;
	protected SelectItem[] listaImpactoQuejas;
	protected SelectItem[] listaImpactoEconomicos;

	protected SelectItem[] listaProbRepFalla;
	protected SelectItem[] listaProbTiempoTrabajo;
	protected SelectItem[] listaProbCondAmbient;
	protected SelectItem[] listaProbMetrologia;
	
	protected String colorValorRiesgo;

	public ManejadorCriticidadEquipos() {
		limpiarSesion();
		List<LaboratorioDetalleEquipos> listaEquipos = (List<LaboratorioDetalleEquipos>) sesion.getAttribute("listaEquipos");
		equipo = (LaboratorioDetalleEquipos) sesion.getAttribute("equipoSeleccionado");
		servicioGeneral.calcularValoresCriticidadXLab(equipo.getLaboratorio().getId(),listaEquipos);
		obtenerEstiloValorRiesgo();
		incializarListas();		
	}
	
	public void incializarListas() {
		listaImpactoOperaLab = servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccioneSinValorVacio(Tipos.TIPOS_CRITIC_EQUIPOS_IMPACTO_OPER_LAB);
		listaImpactoSegUsuarios = servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccioneSinValorVacio(Tipos.TIPOS_CRITIC_EQUIPOS_IMPACTO_SEG_USUARIOS);
		listaImpactoDaniosInfra = servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccioneSinValorVacio(Tipos.TIPOS_CRITIC_EQUIPOS_IMPACTO_DANIOS_INFRA);
		listaImpactoDaniosAmbient = servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccioneSinValorVacio(Tipos.TIPOS_CRITIC_EQUIPOS_IMPACTO_DANIOS_AMBIENT);
		listaImpactoImagenUN = servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccioneSinValorVacio(Tipos.TIPOS_CRITIC_EQUIPOS_IMPACTO_IMAGEN_UN);
		listaImpactoQuejas = servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccioneSinValorVacio(Tipos.TIPOS_CRITIC_EQUIPOS_IMPACTO_QUEJAS);
		listaImpactoEconomicos = servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccioneSinValorVacio(Tipos.TIPOS_CRITIC_EQUIPOS_IMPACTO_IMPACTOS_ECON);
		
		listaProbRepFalla = servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccioneSinValorVacio(Tipos.TIPOS_CRITIC_EQUIPOS_PROBAB_REP_FALLA);
		listaProbTiempoTrabajo = servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccioneSinValorVacio(Tipos.TIPOS_CRITIC_EQUIPOS_PROBAB_TIEMPO_TRAB);
		listaProbCondAmbient = servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccioneSinValorVacio(Tipos.TIPOS_CRITIC_EQUIPOS_PROBAB_COND_AMB);
		listaProbMetrologia = servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccioneSinValorVacio(Tipos.TIPOS_CRITIC_EQUIPOS_PROBAB_METROLOGIA);
	}

	public void limpiarSesion() {
		sesion.removeAttribute("manejadorCriticidadEquipos");
		sesion.removeAttribute("ManejadorLaboratoriosEquipos");
	}

	public void guardarEquipo() {
		if (validar()) {
			try {
				servicioGeneral.calcularValoresCriticidadXEquipo(equipo);
				obtenerEstiloValorRiesgo();
				servicioGeneral.guardarObjeto(equipo);
				guardarLogCriticidadEquipos(equipo);
				mensajeInfo("Información guardada con éxito");
			} catch(Exception e) {
				mensajeError("Hubo un error al guardar los datos. Por favor regrese al formulario anterior e intente de nuevo.");
				e.printStackTrace();
			}
		} else {
			mensajeError("Debe seleccionar alguna opcion para cada uno de los items");
		}
	}

	public Boolean validar() {
		Boolean validar = true;
		if(esNulo(equipo.getCritEquiposImpactoOperaLab())
			|| esNulo(equipo.getCritEquiposImpactoSegUsuarios())
			|| esNulo(equipo.getCritEquiposImpactoDaniosInfra())
			|| esNulo(equipo.getCritEquiposImpactoDaniosAmbient())
			|| esNulo(equipo.getCritEquiposImpactoImagenUN())
			|| esNulo(equipo.getCritEquiposImpactoQuejas())
			|| esNulo(equipo.getCritEquiposImpactoEconomicos())
			|| esNulo(equipo.getCritEquiposProbRepFalla())
			|| esNulo(equipo.getCritEquiposProbTiempoTrabajo())
			|| esNulo(equipo.getCritEquiposProbCondAmbient())
			|| esNulo(equipo.getCritEquiposProbMetrologia())) {
			validar = false;
		}

		return validar;
	}
	
	public String volverLaboratorio() {
		limpiarSesion();
		return "laboratorioEquipos";
	}

	public String obtenerEstiloValorRiesgo() {
        Float valorRiesgo = equipo.getCritEquiposValorRiesgo();
        if (valorRiesgo == null) {
        	colorValorRiesgo = "";
        } else if (valorRiesgo < 5) {
        	colorValorRiesgo = "background-color: #84e8aa;";
        } else if (valorRiesgo < 10) {
        	colorValorRiesgo = "background-color: #f0e948;";
        } else if (valorRiesgo < 30) {
        	colorValorRiesgo = "background-color: #f0a24e;";
        } else {
        	colorValorRiesgo = "background-color: #fc5d5d;";
        }
        
        return colorValorRiesgo;
    }
	
	public LaboratorioDetalleEquipos getEquipo() {
		return equipo;
	}

	public void setEquipo(LaboratorioDetalleEquipos equipo) {
		this.equipo = equipo;
	}

	public SelectItem[] getListaImpactoOperaLab() {
		return listaImpactoOperaLab;
	}

	public void setListaImpactoOperaLab(SelectItem[] listaImpactoOperaLab) {
		this.listaImpactoOperaLab = listaImpactoOperaLab;
	}

	public SelectItem[] getListaImpactoSegUsuarios() {
		return listaImpactoSegUsuarios;
	}

	public void setListaImpactoSegUsuarios(SelectItem[] listaImpactoSegUsuarios) {
		this.listaImpactoSegUsuarios = listaImpactoSegUsuarios;
	}

	public SelectItem[] getListaImpactoDaniosInfra() {
		return listaImpactoDaniosInfra;
	}

	public void setListaImpactoDaniosInfra(SelectItem[] listaImpactoDaniosInfra) {
		this.listaImpactoDaniosInfra = listaImpactoDaniosInfra;
	}

	public SelectItem[] getListaImpactoDaniosAmbient() {
		return listaImpactoDaniosAmbient;
	}

	public void setListaImpactoDaniosAmbient(SelectItem[] listaImpactoDaniosAmbient) {
		this.listaImpactoDaniosAmbient = listaImpactoDaniosAmbient;
	}

	public SelectItem[] getListaImpactoImagenUN() {
		return listaImpactoImagenUN;
	}

	public void setListaImpactoImagenUN(SelectItem[] listaImpactoImagenUN) {
		this.listaImpactoImagenUN = listaImpactoImagenUN;
	}

	public SelectItem[] getListaImpactoQuejas() {
		return listaImpactoQuejas;
	}

	public void setListaImpactoQuejas(SelectItem[] listaImpactoQuejas) {
		this.listaImpactoQuejas = listaImpactoQuejas;
	}

	public SelectItem[] getListaImpactoEconomicos() {
		return listaImpactoEconomicos;
	}

	public void setListaImpactoEconomicos(SelectItem[] listaImpactoEconomicos) {
		this.listaImpactoEconomicos = listaImpactoEconomicos;
	}

	public SelectItem[] getListaProbRepFalla() {
		return listaProbRepFalla;
	}

	public void setListaProbRepFalla(SelectItem[] listaProbRepFalla) {
		this.listaProbRepFalla = listaProbRepFalla;
	}

	public SelectItem[] getListaProbTiempoTrabajo() {
		return listaProbTiempoTrabajo;
	}

	public void setListaProbTiempoTrabajo(SelectItem[] listaProbTiempoTrabajo) {
		this.listaProbTiempoTrabajo = listaProbTiempoTrabajo;
	}

	public SelectItem[] getListaProbCondAmbient() {
		return listaProbCondAmbient;
	}

	public void setListaProbCondAmbient(SelectItem[] listaProbCondAmbient) {
		this.listaProbCondAmbient = listaProbCondAmbient;
	}

	public SelectItem[] getListaProbMetrologia() {
		return listaProbMetrologia;
	}

	public void setListaProbMetrologia(SelectItem[] listaProbMetrologia) {
		this.listaProbMetrologia = listaProbMetrologia;
	}

	public String getColorValorRiesgo() {
		return colorValorRiesgo;
	}

	public void setColorValorRiesgo(String colorValorRiesgo) {
		this.colorValorRiesgo = colorValorRiesgo;
	}

	@Override
	public String salirGuardar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String siguiente() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
