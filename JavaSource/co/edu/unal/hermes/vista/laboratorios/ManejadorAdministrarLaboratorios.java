package co.edu.unal.hermes.vista.laboratorios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleEquipos;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioEquipoMetrologia;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorAdministrarLaboratorios extends ManejadorBase implements Serializable {

	private static final long serialVersionUID = 533509269493931716L;
	private List<Laboratorio> listaLaboratorios;
	private UIData tablaLaboratorios;

	// private Long idLaboratorio = -1L;
	private Laboratorio laboratorioSeleccionado;
	private List<Laboratorio> laboratoriosFiltrados;
	private SelectItem[] sedeSelectItem;
	protected Boolean esLaboratoriosSede = false;
	protected Boolean esLaboratoriosFacultad = false;
	protected Boolean esLaboratoriosDepto = false;
	protected Boolean esLaboratoriosNacional = false;
	protected Boolean esConsultaLaboratorios = false;
	protected Boolean esCoordinadorLaboratorio = false;
	protected String rolSeleccionadoLabs = "";
	protected Sede sedePersona;
	protected String tituloTabla = "Consulta de Laboratorios";
	private List<Laboratorio> laboratoriosCoordinador;
	private Boolean buscarAcreditados;
	private String hqlAnd;
	private String hqlAndAcreditados;

	public ManejadorAdministrarLaboratorios() {
		
		try {

			String hql = "from Sede WHERE id NOT IN (1) ORDER BY id";
			List<Sede> listaSedes = servicioGeneral.obtenerObjetos(Sede.class, hql);
	
			sedeSelectItem = new SelectItem[listaSedes.size() + 1];
			sedeSelectItem[0] = new SelectItem("", "Todas");
			for (int i = 0; i < listaSedes.size(); i++) {
				Sede sede = (Sede) listaSedes.get(i);
				sedeSelectItem[i + 1] = new SelectItem(sede.getId(),sede.getNombre());
			}
	
			esLaboratoriosSede = (Boolean) sesion.getAttribute("esLaboratoriosSede");
			esLaboratoriosNacional = (Boolean) sesion.getAttribute("esLaboratorios");
			esConsultaLaboratorios = (Boolean) sesion.getAttribute("esConsultaLaboratorios");
			esCoordinadorLaboratorio = (Boolean) sesion.getAttribute("esCoordinadorLaboratorio");
			esLaboratoriosFacultad = (Boolean) sesion.getAttribute("esLaboratoriosFacultad");
			esLaboratoriosDepto = (Boolean) sesion.getAttribute("esLaboratoriosDepto");
			
			rolSeleccionadoLabs = (String) sesion.getAttribute("rolSeleccionadoLabs");
	
			hqlAnd = "";
			personaActual = (Persona) sesion.getAttribute("persona");
			if (rolSeleccionadoLabs.equals("LS") && personaActual instanceof InvestigadorInterno) {
				sedePersona = ((InvestigadorInterno) personaActual).getDependencia().getSede();
				tituloTabla = "Dirección de Laboratorios Sede " + sedePersona.getNombre();
				hqlAnd = " AND L.sede.id = '" + sedePersona.getId() + "' AND L.id <> '49'";
				sesion.setAttribute("sedeLabsSede", sedePersona);
			}
	
			String idLabs = "";
			if (esCoordinadorLaboratorio) {
				String documento = personaActual.getId().getDocumento();
				String tipoDocumento = personaActual.getId().getTipoDocumento();
	
				String hHql = "SELECT pL.laboratorio FROM PersonaLaboratorio pL "
						+ "WHERE pL.persona.id.documento = '"+ documento
						+ "' AND pL.persona.id.tipoDocumento = '"+ tipoDocumento + "' "
	//					+ "AND pL.laboratorio.id <> '49' "
						+ "AND pL.laboratorio.activo = 1 "
						+ "ORDER BY pL.laboratorio.id ASC";
				laboratoriosCoordinador = servicioGeneral.obtenerObjetos(Laboratorio.class, hHql);
			}
	
			if (
				(rolSeleccionadoLabs.equals("CO") || rolSeleccionadoLabs.equals("TL") || rolSeleccionadoLabs.equals("CT")) 
				&& laboratoriosCoordinador.size() > 0
			) {
				tituloTabla = "Coordinador de Laboratorio";
				// Se arma una cadena con los ids de los laboratorios que los que la
				// persona actual es coordinador:
				for (Laboratorio lc : laboratoriosCoordinador) {
					Long idLab = lc.getId();
					idLabs += idLab + " ";
				}
	
				idLabs = idLabs.trim().replace(" ", ",");
				sesion.setAttribute("idLabs", idLabs);
				hqlAnd = " AND L.id in (" + idLabs + ") ";
			}
	
			if (rolSeleccionadoLabs.equals("DL")) {
				tituloTabla = "Sistema Nacional de Laboratorios";
				hqlAnd = "";
			}
	
			// Se consulta la facultad, de acuerdo a
			// InvestigadorInterno.dependencia2 ó dependencia
			if (rolSeleccionadoLabs.equals("LF") && personaActual instanceof InvestigadorInterno) {
				Dependencia dependencia = ((InvestigadorInterno) personaActual).getDependencia2();
				if (dependencia == null) {
					dependencia = ((InvestigadorInterno) personaActual).getDependencia();
				}
				if (dependencia.getFacultad() != null) {
					dependencia = dependencia.getFacultad();
				}
				tituloTabla = "Laboratorios " + dependencia.getNombre();
				hqlAnd = " AND L.facultad.id = '" + dependencia.getId() + "' AND L.id <> '49'";
				sesion.setAttribute("depLabsFacultad", dependencia);
			}
	
			// Laboratorios Departamento
			if (rolSeleccionadoLabs.equals("LD") && personaActual instanceof InvestigadorInterno) {
				Dependencia dependencia = ((InvestigadorInterno) personaActual)
						.getDependencia2();
				if (dependencia == null) {
					dependencia = ((InvestigadorInterno) personaActual).getDependencia();
				}
				if (dependencia.getDepartamento() != null) {
					dependencia = servicioDependencia.obtenerDependencia(dependencia
							.getDepartamento());
				}
				tituloTabla = "Laboratorios " + dependencia.getNombre();
				hqlAnd = " AND L.departamento.id = '" + dependencia.getId() + "' AND L.id <> '49'";
				sesion.setAttribute("depLabsFacultad", dependencia);
			}
			buscarAcreditados = false;
			buscarLaboratorios();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void buscarLaboratorios() {
		if (buscarAcreditados) {
			hqlAndAcreditados = " AND L.gestionAcreditacion = '" + Tipos.TIPO_GESTION_LABORATORIO_SI + "' ";
		} else {
			hqlAndAcreditados = "";
		}

		String hqlLabs = "FROM Laboratorio L WHERE L.activo = "
				+ Laboratorio.VERDADERO + hqlAnd + hqlAndAcreditados
				+ " order by L.nombre ";
		listaLaboratorios = servicioGeneral.obtenerObjetos(Laboratorio.class,hqlLabs);
		
		// Actualizacion porcentaje laboratorios : Mantener comentado
//		for (Laboratorio l : listaLaboratorios) {
//			servicioGeneral.calcularPorcentajeLab(l);
//		}
		
		//Actualizar de manera masiva los equipos robustos por cambios en SMMLV (Mantener comentado)
//		actualizarEquiposRobustosTodosLabs();
		
		// Se marcan los laboratorios de los cuales la persona actual es
		// coordinador:
		if (laboratoriosCoordinador != null) {
			for (Laboratorio lc : laboratoriosCoordinador) {
				Long idLab = lc.getId();
				for (Laboratorio l : listaLaboratorios) {
					if (l.getId().equals(idLab)) {
						l.setPersonaActualEsCoordinador(true);
					}
				}
			}
		}
	}
	
	public void actualizarEquiposRobustosTodosLabs(){
		for (Laboratorio l : listaLaboratorios) {
			List<LaboratorioDetalleEquipos> listaEquipos;
			listaEquipos = new ArrayList<LaboratorioDetalleEquipos>();
			String hql = "from LaboratorioDetalleEquipos WHERE laboratorio = '"
					+ l.getId() + "' AND robusto IS NOT NULL";
			listaEquipos = servicioGeneral.obtenerObjetos(LaboratorioDetalleEquipos.class, hql);
			if (listaEquipos.size() < 1) {
				listaEquipos = null;
			}
			if(!esNulo(listaEquipos)) {
				for(LaboratorioDetalleEquipos equipo: listaEquipos) {
					actualizarEquipoRobusto(equipo);
				}
			}
		}
	}
	
	public void actualizarEquipoRobusto(LaboratorioDetalleEquipos equipo) {
		
		long SMMLV = 1423500L;
		long VALOR_REFERENCIA_EQUIPO_ROBUSTO = 240L;
		
		equipo.setRobustoMayorValorReferencia(false);
//		equipo.setRobustoAltaPrecision(false);
//		equipo.setRobustoAltaExactitud(false);
		
		//esRobustoMayorValorReferencia
		if(!esNulo(equipo.getValor()) && equipo.getValor() > 0) {
			if(equipo.getValor()/SMMLV >= VALOR_REFERENCIA_EQUIPO_ROBUSTO || (equipo.getPlaca().equals("2354805") || equipo.getPlaca().equals("2239674")))
				equipo.setRobustoMayorValorReferencia(true);
		}
		
		// Lógica solo para cuando carga la HV
//		if(!listaMagnitudes.isEmpty()) {
//			for (LaboratorioEquipoMetrologia lem : listaMagnitudes) {
//				if(!esNulo(lem.getEquipoAltaPrecision()) && lem.getEquipoAltaPrecision())
//					equipo.setRobustoAltaPrecision(true);
//				if(!esNulo(lem.getEquipoAltaExactitud()) && lem.getEquipoAltaExactitud())
//					equipo.setRobustoAltaExactitud(true);
//			}
//		}
		
		Boolean esRobusto = esNulo(equipo.getRobustoEspecialidadCalidadAnalitica()) ? false : equipo.getRobustoEspecialidadCalidadAnalitica()
				&& equipo.getRobustoMayorValorReferencia()
				&& equipo.getRobustoAltaPrecision()
				&& equipo.getRobustoAltaExactitud();
		
		equipo.setRobusto(esRobusto);
		
//		System.out.println(
//				"Laboratorio= " + equipo.getLaboratorio().getId() + 
//				", Placa= " + equipo.getPlaca() +
//			    ", Analitica=" + equipo.getRobustoEspecialidadCalidadAnalitica() +
//			    ", MayorValorRef=" + equipo.getRobustoMayorValorReferencia() +
//			    ", AltaPrecision=" + equipo.getRobustoAltaPrecision() +
//			    ", AltaExactitud=" + equipo.getRobustoAltaExactitud() +
//			    " => esRobusto=" + esRobusto
//			);
		
//		servicioGeneral.guardarObjeto(equipo);
	}
	
	public void inactivarLaboratorioSeleccionado() {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean validar = true;
		
		if(esNulo(laboratorioSeleccionado.getJustificacionInactivo()) || laboratorioSeleccionado.getJustificacionInactivo().equals("")){
			mensajeError("formAdminLab:justificacionInactivarTxt","La justificación es un campo obligatorio");
			validar = false;
			return;
		}
		
		if (validar) {
			laboratorioSeleccionado.setActivo(false);
			laboratorioSeleccionado.setFechaInactivo(new Date());
			servicioGeneral.guardarObjeto(laboratorioSeleccionado);
			
			listaLaboratorios.remove(laboratorioSeleccionado);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se inactivo el laboratorio con ID:" + laboratorioSeleccionado.getId(), "");
			FacesContext.getCurrentInstance().addMessage("btnInactivarLab", msg);
		}
		context.execute("dialogInactivarLaboratorios.hide();");
	}

	public String consultarLaboratorios() {

		/*
		 * idLaboratorio = ((Laboratorio) (tablaLaboratorios.getRowData()))
		 * .getId(); List labSel =
		 * servicioGeneral.obtenerObjetoXID("Laboratorio",
		 * idLaboratorio.toString()); Laboratorio labActual = (Laboratorio)
		 * labSel.get(0);
		 */

		limpiarSesion();
		// sesion.setAttribute("Laboratorio", labActual);
		sesion.setAttribute("Laboratorio", laboratorioSeleccionado);
		sesion.setAttribute("soloLectura", true);
		return "CrearLaboratorio";
	}

	public void reporte() {
		System.out.println("ManejadorAdministrarLaboratorios.reporte");
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("id", laboratorioSeleccionado.getId().toString());
		System.out.println("r.getParametros():" + r.getParametros());
		r.setNombreReporte("/laboratorios/ReporteLab");
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

	public void limpiarSesion() {
		sesion.removeAttribute("ManejadorLaboratoriosInformacionGeneral");
		sesion.removeAttribute("ManejadorLaboratoriosRecursoHumano");
		sesion.removeAttribute("ManejadorLaboratoriosRiesgos");
		sesion.removeAttribute("ManejadorLaboratoriosGestion");
		sesion.removeAttribute("ManejadorLaboratoriosEquipos");
		sesion.removeAttribute("ManejadorLaboratoriosInvestigacion");
		sesion.removeAttribute("ManejadorLaboratoriosProyectos");
		sesion.removeAttribute("ManejadorLaboratoriosDocencia");
		sesion.removeAttribute("ManejadorLaboratoriosEnsayosServicios");
		sesion.removeAttribute("ManejadorMenuFormularioLaboratorios");
		sesion.removeAttribute("ManejadorAdministrarLaboratorios");
		sesion.removeAttribute("manejadorHojaDeVidaEquipos");
		sesion.removeAttribute("Laboratorio");
		sesion.removeAttribute("soloLectura");
		sesion.removeAttribute("solicitudLaboratorio");
		sesion.removeAttribute("manejadorLaboratorioPresupuesto");
	}

	public String editarLaboratorios() {
		limpiarSesion();
		sesion.setAttribute("Laboratorio", laboratorioSeleccionado);
		sesion.setAttribute("soloLectura", false);
		return "CrearLaboratorio";
	}

	public String salir() {
		limpiarSesion();
		return "misProyectos";
	}
	
	public String consultarHistoricoEstadoLaboratorio() {
        sesion.setAttribute("lab", laboratorioSeleccionado);
        sesion.setAttribute("lab_id", laboratorioSeleccionado.getId());
        sesion.setAttribute("buscarHistoricoEstado", true);
        sesion.removeAttribute("manejadorConsultaHistoricosLaboratorios");
        return "consultarHistoricoLaboratorio";
    }

	/**
	 * @return the listaLaboratorios
	 */
	public List<Laboratorio> getListaLaboratorios() {
		return listaLaboratorios;
	}

	/**
	 * @param listaLaboratorios
	 *            the listaLaboratorios to set
	 */
	public void setListaLaboratorios(List<Laboratorio> listaLaboratorios) {
		this.listaLaboratorios = listaLaboratorios;
	}

	/**
	 * @return the tablaLaboratorios
	 */
	public UIData getTablaLaboratorios() {
		return tablaLaboratorios;
	}

	/**
	 * @param tablaLaboratorios
	 *            the tablaLaboratorios to set
	 */
	public void setTablaLaboratorios(UIData tablaLaboratorios) {
		this.tablaLaboratorios = tablaLaboratorios;
	}

	/**
	 * @return the laboratorioSeleccionado
	 */
	public Laboratorio getLaboratorioSeleccionado() {
		return laboratorioSeleccionado;
	}

	/**
	 * @param laboratorioSeleccionado
	 *            the laboratorioSeleccionado to set
	 */
	public void setLaboratorioSeleccionado(Laboratorio laboratorioSeleccionado) {
		this.laboratorioSeleccionado = laboratorioSeleccionado;
	}

	/**
	 * @return the laboratoriosFiltrados
	 */
	public List<Laboratorio> getLaboratoriosFiltrados() {
		return laboratoriosFiltrados;
	}

	/**
	 * @param laboratoriosFiltrados
	 *            the laboratoriosFiltrados to set
	 */
	public void setLaboratoriosFiltrados(List<Laboratorio> laboratoriosFiltrados) {
		this.laboratoriosFiltrados = laboratoriosFiltrados;
	}

	/**
	 * @return the sedeSelectItem
	 */
	public SelectItem[] getSedeSelectItem() {
		return sedeSelectItem;
	}

	/**
	 * @return the esLaboratoriosSede
	 */
	public Boolean getEsLaboratoriosSede() {
		return esLaboratoriosSede;
	}

	/**
	 * @return the esLaboratoriosNacional
	 */
	public Boolean getEsLaboratoriosNacional() {
		return esLaboratoriosNacional;
	}

	/**
	 * @return the sedePersona
	 */
	public Sede getSedePersona() {
		return sedePersona;
	}

	/**
	 * @return the tituloTabla
	 */
	public String getTituloTabla() {
		return tituloTabla;
	}

	/**
	 * @return the esConsultaLaboratorios
	 */
	public Boolean getEsConsultaLaboratorios() {
		return esConsultaLaboratorios;
	}

	/**
	 * @return the esCoordinadorLaboratorio
	 */
	public Boolean getEsCoordinadorLaboratorio() {
		return esCoordinadorLaboratorio;
	}

	/**
	 * @return the laboratoriosCoordinador
	 */
	public List<Laboratorio> getLaboratoriosCoordinador() {
		return laboratoriosCoordinador;
	}

	/**
	 * @return the esLaboratoriosFacultad
	 */
	public Boolean getEsLaboratoriosFacultad() {
		return esLaboratoriosFacultad;
	}

	/**
	 * @return the buscarAcreditados
	 */
	public Boolean getBuscarAcreditados() {
		return buscarAcreditados;
	}

	/**
	 * @param buscarAcreditados
	 *            the buscarAcreditados to set
	 */
	public void setBuscarAcreditados(Boolean buscarAcreditados) {
		this.buscarAcreditados = buscarAcreditados;
	}

	/**
	 * @return the esLaboratoriosDepto
	 */
	public Boolean getEsLaboratoriosDepto() {
		return esLaboratoriosDepto;
	}

	public String getRolSeleccionadoLabs() {
		return rolSeleccionadoLabs;
	}

	public void setRolSeleccionadoLabs(String rolSeleccionadoLabs) {
		this.rolSeleccionadoLabs = rolSeleccionadoLabs;
	}

}
