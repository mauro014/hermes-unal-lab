package co.edu.unal.hermes.vista.laboratorios;

import java.util.ArrayList;

import co.edu.unal.hermes.modelo.GrupoLaboratorioVista;
import co.edu.unal.hermes.modelo.SemilleroLaboratorioVista;

public class ManejadorLaboratoriosInvestigacion extends ManejadorLaboratorios {

	private static final long serialVersionUID = 1L;
	private GrupoLaboratorioVista grupoSel;
	private ArrayList<GrupoLaboratorioVista> listaGrupos;
	private ArrayList<GrupoLaboratorioVista> listaGruposFiltrados;
	
	private SemilleroLaboratorioVista semilleroSel;
	private ArrayList<SemilleroLaboratorioVista> listaSemilleros;
	private ArrayList<SemilleroLaboratorioVista> listaSemillerosFiltrados;
	
	public ManejadorLaboratoriosInvestigacion() {
		idManejador = INVESTIGACION;		
		listaGrupos = (ArrayList<GrupoLaboratorioVista>) servicioGeneral.consultaGruposAsociadosLaboratorio(laboratorioActual.getId());
		listaSemilleros = (ArrayList<SemilleroLaboratorioVista>) servicioGeneral.consultaSemillerosAsociadosLaboratorio(laboratorioActual.getId());
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
			return "laboratorioDocencia";
		} else {
			return null;
		}
	}

	public Boolean validar() {
		return true;
	}

	public GrupoLaboratorioVista getGrupoSel() {
		return grupoSel;
	}

	public void setGrupoSel(GrupoLaboratorioVista grupoSel) {
		this.grupoSel = grupoSel;
	}

	public ArrayList<GrupoLaboratorioVista> getListaGrupos() {
		return listaGrupos;
	}

	public void setListaGrupos(ArrayList<GrupoLaboratorioVista> listaGrupos) {
		this.listaGrupos = listaGrupos;
	}

	public ArrayList<GrupoLaboratorioVista> getListaGruposFiltrados() {
		return listaGruposFiltrados;
	}

	public void setListaGruposFiltrados(ArrayList<GrupoLaboratorioVista> listaGruposFiltrados) {
		this.listaGruposFiltrados = listaGruposFiltrados;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public SemilleroLaboratorioVista getSemilleroSel() {
		return semilleroSel;
	}

	public void setSemilleroSel(SemilleroLaboratorioVista semilleroSel) {
		this.semilleroSel = semilleroSel;
	}

	public ArrayList<SemilleroLaboratorioVista> getListaSemilleros() {
		return listaSemilleros;
	}

	public void setListaSemilleros(ArrayList<SemilleroLaboratorioVista> listaSemilleros) {
		this.listaSemilleros = listaSemilleros;
	}

	public ArrayList<SemilleroLaboratorioVista> getListaSemillerosFiltrados() {
		return listaSemillerosFiltrados;
	}

	public void setListaSemillerosFiltrados(ArrayList<SemilleroLaboratorioVista> listaSemillerosFiltrados) {
		this.listaSemillerosFiltrados = listaSemillerosFiltrados;
	}
	
}
