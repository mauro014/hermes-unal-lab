package co.edu.unal.hermes.vista.laboratorios;

import java.io.Serializable;
import java.util.List;

import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.modelo.laboratorios.PersonaLaboratorio;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorAdministrarLaboratoriosRolInterno extends ManejadorBase implements Serializable {

	private static final long serialVersionUID = 533509269493931716L;
	private List<PersonaLaboratorio> listaLaboratorios;
	private UIData tablaLaboratorios;

	// private Long idLaboratorio = -1L;
	private PersonaLaboratorio laboratorioSeleccionado;
	private List<PersonaLaboratorio> laboratoriosFiltrados;
	private SelectItem[] sedeSelectItem;
	protected Boolean esLaboratoriosSede = false;
	protected Boolean esLaboratoriosFacultad = false;
	protected Boolean esLaboratoriosDepto = false;
	protected Boolean esLaboratoriosNacional = false;
	protected Boolean esConsultaLaboratorios = false;
	protected Boolean esCoordinadorLaboratorio = false;
	protected Boolean esPersonalLaboratorio = false;
	protected Boolean esRolInternoLab = false;
	protected String rolSeleccionadoLabs = "";
	protected Sede sedePersona;
	protected String tituloTabla = "Consulta de Laboratorios";
	private List<Laboratorio> laboratoriosCoordinador;
	private Boolean buscarAcreditados;
	private String hqlAnd;
	private String hqlAndAcreditados;

	public ManejadorAdministrarLaboratoriosRolInterno() {
		
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
			esPersonalLaboratorio = (Boolean) sesion.getAttribute("esPersonalLaboratorio");
			esLaboratoriosFacultad = (Boolean) sesion.getAttribute("esLaboratoriosFacultad");
			esLaboratoriosDepto = (Boolean) sesion.getAttribute("esLaboratoriosDepto");
			
			rolSeleccionadoLabs = (String) sesion.getAttribute("rolSeleccionadoLabs");
			
			esRolInternoLab = esCoordinadorLaboratorio || esPersonalLaboratorio;
	
			hqlAnd = "";
			personaActual = (Persona) sesion.getAttribute("persona");
			if (rolSeleccionadoLabs.equals("LS") && personaActual instanceof InvestigadorInterno) {
				sedePersona = ((InvestigadorInterno) personaActual)
						.getDependencia().getSede();
				tituloTabla = "Dirección de Laboratorios Sede " + sedePersona.getNombre();
				hqlAnd = " AND L.laboratorio.sede.id = '" + sedePersona.getId() + "' ";
				sesion.setAttribute("sedeLabsSede", sedePersona);
			}
	
			String idLabs = "";
			if (esCoordinadorLaboratorio || esPersonalLaboratorio) {
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
	
			if ((rolSeleccionadoLabs.equals("CO") || rolSeleccionadoLabs.equals("PL")) 
				&& laboratoriosCoordinador.size() > 0
			) {
				tituloTabla = rolSeleccionadoLabs.equals("CO") ? "Coordinador de Laboratorio" : "Personal de Laboratorio";
				// Se arma una cadena con los ids de los laboratorios que los que la
				// persona actual es coordinador:
				for (Laboratorio lc : laboratoriosCoordinador) {
					Long idLab = lc.getId();
					idLabs += idLab + " ";
				}
	
				idLabs = idLabs.trim().replace(" ", ",");
				sesion.setAttribute("idLabs", idLabs);
				hqlAnd = " AND L.laboratorio.id in (" + idLabs + ") ";
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
				hqlAnd = " AND L.laboratorio.facultad.id = '" + dependencia.getId() + "' ";
				sesion.setAttribute("depLabsFacultad", dependencia);
			}
	
			// Laboratorios Departamento
			if (rolSeleccionadoLabs.equals("LD") && personaActual instanceof InvestigadorInterno) {
				Dependencia dependencia = ((InvestigadorInterno) personaActual).getDependencia2();
				if (dependencia == null) {
					dependencia = ((InvestigadorInterno) personaActual).getDependencia();
				}
				if (dependencia.getDepartamento() != null) {
					dependencia = servicioDependencia.obtenerDependencia(dependencia.getDepartamento());
				}
				tituloTabla = "Laboratorios " + dependencia.getNombre();
				hqlAnd = " AND L.laboratorio.departamento.id = '" + dependencia.getId() + "' ";
				sesion.setAttribute("depLabsFacultad", dependencia);
			}
			buscarAcreditados = false;
			buscarLaboratorios();
		} catch (Exception e) {
			System.out.println("Error personaActual: " + personaActual.getId().getDocumento() + " - " + personaActual.getNombreCompleto());
			System.out.println("Error esLaboratoriosSede: " + esLaboratoriosSede);
			System.out.println("Error esLaboratoriosNacional: " + esLaboratoriosNacional);
			System.out.println("Error esConsultaLaboratorios: " + esConsultaLaboratorios);
			System.out.println("Error esCoordinadorLaboratorio: " + esCoordinadorLaboratorio);
			System.out.println("Error esLaboratoriosFacultad: " + esLaboratoriosFacultad);
			System.out.println("Error esLaboratoriosDepto: " + esLaboratoriosDepto);
			System.out.println("Error rolSeleccionadoLabs: " + rolSeleccionadoLabs);
			System.out.println("Error ManejadorAdministrarLaboratorios: " + e);
			e.printStackTrace();
		}
	}

	public void buscarLaboratorios() {
		
		String documento = personaActual.getId().getDocumento();
		String tipoDocumento = personaActual.getId().getTipoDocumento();
		
		if (buscarAcreditados) {
			hqlAndAcreditados = " AND L.laboratorio.gestionAcreditacion = '" + Tipos.TIPO_GESTION_LABORATORIO_SI + "' ";
		} else {
			hqlAndAcreditados = "";
		}
		
		String hqlTipoRol = "";
		if(esPersonalLaboratorio || esCoordinadorLaboratorio) {
			hqlTipoRol = " AND (L.persona.id.tipoDocumento ='" + tipoDocumento + "' AND L.persona.id.documento ='"+ documento +"')";
		} else
			hqlTipoRol = " AND L.rol.id = 'CO'";

		String hqlLabs = "FROM PersonaLaboratorio L "
				+ "WHERE L.laboratorio.activo = " + Laboratorio.VERDADERO
//				+ " AND (L.persona.id.tipoDocumento ='" + tipoDocumento + "' AND L.persona.id.documento ='"+ documento +"')"
				+ hqlTipoRol
				+ hqlAnd 
				+ hqlAndAcreditados
				+ " ORDER BY L.laboratorio.nombre ";
		listaLaboratorios = servicioGeneral.obtenerObjetos(PersonaLaboratorio.class,hqlLabs);

		// Se marcan los laboratorios de los cuales la persona actual es
		// coordinador:
//		if (laboratoriosCoordinador != null) {
//			for (Laboratorio lc : laboratoriosCoordinador) {
//				Long idLab = lc.getId();
//				for (Laboratorio l : listaLaboratorios) {
//					if (l.getId().equals(idLab)) {
//						l.setPersonaActualEsCoordinador(true);
//					}
//				}
//			}
//		}

	}

	public String consultarLaboratorios() {
		limpiarSesion();
		sesion.setAttribute("Laboratorio", laboratorioSeleccionado.getLaboratorio());
		sesion.setAttribute("soloLectura", true);
		return "CrearLaboratorio";
	}

	public void reporte() {
		System.out.println("ManejadorAdministrarLaboratorios.reporte");
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("id", laboratorioSeleccionado.getLaboratorio().getId().toString());
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
		sesion.setAttribute("Laboratorio", laboratorioSeleccionado.getLaboratorio());
		sesion.setAttribute("soloLectura", false);
		return "CrearLaboratorio";
	}

	public String salir() {
		limpiarSesion();
		return "misProyectos";
	}
	
	public String consultarHistoricoEstadoLaboratorio() {
        sesion.setAttribute("lab", laboratorioSeleccionado.getLaboratorio());
        sesion.setAttribute("lab_id", laboratorioSeleccionado.getLaboratorio().getId());
        sesion.setAttribute("buscarHistoricoEstado", true);
        sesion.removeAttribute("manejadorConsultaHistoricosLaboratorios");
        return "consultarHistoricoLaboratorio";
    }

	/**
	 * @return the listaLaboratorios
	 */
	public List<PersonaLaboratorio> getListaLaboratorios() {
		return listaLaboratorios;
	}

	/**
	 * @param listaLaboratorios
	 *            the listaLaboratorios to set
	 */
	public void setListaLaboratorios(List<PersonaLaboratorio> listaLaboratorios) {
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
	public PersonaLaboratorio getLaboratorioSeleccionado() {
		return laboratorioSeleccionado;
	}

	/**
	 * @param laboratorioSeleccionado
	 *            the laboratorioSeleccionado to set
	 */
	public void setLaboratorioSeleccionado(PersonaLaboratorio laboratorioSeleccionado) {
		this.laboratorioSeleccionado = laboratorioSeleccionado;
	}

	/**
	 * @return the laboratoriosFiltrados
	 */
	public List<PersonaLaboratorio> getLaboratoriosFiltrados() {
		return laboratoriosFiltrados;
	}

	/**
	 * @param laboratoriosFiltrados
	 *            the laboratoriosFiltrados to set
	 */
	public void setLaboratoriosFiltrados(List<PersonaLaboratorio> laboratoriosFiltrados) {
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

	public Boolean getEsPersonalLaboratorio() {
		return esPersonalLaboratorio;
	}

	public void setEsPersonalLaboratorio(Boolean esPersonalLaboratorio) {
		this.esPersonalLaboratorio = esPersonalLaboratorio;
	}

	public Boolean getEsRolInternoLab() {
		return esRolInternoLab;
	}

	public void setEsRolInternoLab(Boolean esRolInternoLab) {
		this.esRolInternoLab = esRolInternoLab;
	}
}
