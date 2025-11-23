package co.edu.unal.hermes.vista.proyectos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorConsultaProyectosCoordinador extends ManejadorBase {

	private static final long serialVersionUID = -6634015075603869844L;
	private List<ConvocatoriaPadre> listadoConvocatoriaPadre;
	private List listaConvocatorias;
	private String convocatoriaSel;
	private SelectItem[] convocatoriaItem;
	private String modalidadSel;
	private SelectItem[] modalidadItem;
	private SelectItem[] sedeItem;
	private String sedeSel;
	private boolean mostrarFacultades = false;
	private boolean mostrarModalidades = false;
	private List<Dependencia> facultadesUN;
	private SelectItem[] facultadItem;
	private String facultadSel;
	private SelectItem[] tipoCoordinadorItem = { new SelectItem(new Integer(0), "Seguimiento"),
			new SelectItem(new Integer(1), "Revisión"), new SelectItem(new Integer(2), "Evaluación") };
	private Integer coorSel;
	List<Proyecto> listaProyectos = new ArrayList<Proyecto>();
	List<Proyecto> listaProyectosMorosos = new ArrayList<Proyecto>();
	private Integer totalProyectos = 0;
	private Integer totalProyectosMorosos = 0;
	private Long proyectoSeleccionado;
	private List filteredProyectos;

	public ManejadorConsultaProyectosCoordinador() {
		super();
		setListadoConvocatoriaPadre(new ArrayList<ConvocatoriaPadre>());
		ArrayList<Sede> sedesUN = new ArrayList<Sede>();
		ArrayList<ConvocatoriaPadre> convocatoriasPadre = new ArrayList<ConvocatoriaPadre>();
		String sql = "select convp from ConvocatoriaPadre as convp where convp.estadoConvocatoria.id<>'O' order by convp.titulo asc";
		String sql1 = "select e from Sede e where  e.id<>0 and e.id<>1";
		convocatoriasPadre = (ArrayList<ConvocatoriaPadre>) servicioGeneral.obtenerObjetos(sql);
		sedesUN = new ArrayList<Sede>();
		sedesUN = (ArrayList<Sede>) servicioGeneral.obtenerObjetos(sql1);

		sedeItem = new SelectItem[sedesUN.size()];
		convocatoriaItem = new SelectItem[convocatoriasPadre.size()];
		for (int i = 0; i < convocatoriasPadre.size(); i++) {
			ConvocatoriaPadre dd = (ConvocatoriaPadre) convocatoriasPadre.get(i);
			convocatoriaItem[i] = new SelectItem(dd.getId(), dd.getTitulo());
			dd = null;
		}

		for (int i = 0; i < sedesUN.size(); i++) {
			Sede dd = (Sede) sedesUN.get(i);
			sedeItem[i] = new SelectItem(dd.getId(), "Sede " + dd.getNombre());
			dd = null;
		}
		convocatoriaSel = "15"; // Identificador para todas las convocatorias no
								// ocultas
	}

	public void cambiarSede() {
		Sede sede = new Sede();
		sede.setId(Long.parseLong(sedeSel));
		facultadesUN = new ArrayList<Dependencia>();
		if (sede.isEsSedePresenciaNacional() || "0".equals(sedeSel)) {
			setMostrarFacultades(false);
		} else {
			setMostrarFacultades(true);
			// Facultades
			facultadesUN = servicioGeneral.obtenerObjetos("select e from Dependencia e where e.sede.id = '" + sedeSel
					+ "' and e.esFacultad = 'Y' and e.estado = 'A' order by e.nombre");
			facultadItem = new SelectItem[facultadesUN.size()];
			for (int i = 0; i < facultadesUN.size(); i++) {
				Dependencia dd = (Dependencia) facultadesUN.get(i);
				facultadItem[i] = new SelectItem(dd.getId(), dd.getNombre());
			}
		}
	}

	public void cargarModalidad() {
		ConvocatoriaPadre cp = new ConvocatoriaPadre();
		cp.setId(Long.parseLong(convocatoriaSel));
		if (cp.getId() != null) {
			listaConvocatorias = servicioModalidad.obtenerConvocatoriasxPadre(cp);

			if (!esListaVacia(listaConvocatorias)) {
				modalidadItem = new SelectItem[listaConvocatorias.size()];
				mostrarModalidades = true;
				for (int i = 0; i < listaConvocatorias.size(); i++) {
					Convocatoria con = (Convocatoria) listaConvocatorias.get(i);
					modalidadItem[i] = new SelectItem(con.getId().toString(), con.getTitulo());
				}
			} else {
				modalidadItem = new SelectItem[0];
				mostrarModalidades = false;
			}
		}
	}

	public void consultarProyectos() {
		Persona persona = (Persona) sesion.getAttribute("persona");
		String parteInicialConsulta = "select p from Proyecto p, InvestigadorProyecto ip";
		try {
			// Consultas para coordinador de evaluacion
			if (coorSel == 2) {

				consultaCoordinadorEvaluacion(persona, parteInicialConsulta);

				// Consultas para coordinador de revisión
			} else if (coorSel == 1) {

				consultaCoordinadorRequisitos(persona, parteInicialConsulta);

				// Consultas para coordinador de seguimiento
			} else if (coorSel == 0) {

				consultaCoordinadorSeguimiento(persona, parteInicialConsulta);

			}
		} catch (org.hibernate.ObjectNotFoundException e) {
			System.out.print(persona.getId().getDocumento() + "-" + persona.getId().getTipoDocumento());
			e.printStackTrace();
		}

		if (!esListaVacia(listaProyectos)) {
			totalProyectos = listaProyectos.size();
			for (Proyecto pry : listaProyectos) {
				pry.setNombreConvocatoriaPadre(((Convocatoria) pry.getModalidad()).getTitulo());
				for (InvestigadorProyecto inv : pry.getInvestigadoresProyecto()) {
					if (inv.getTipo().getId().equals("P")) {
						pry.setInvestigadorPrincipalVista(inv);
					}
				}
			}
		}

	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	private void consultaCoordinadorEvaluacion(Persona persona, String parteInicialConsulta) {
		if ("15".equals(convocatoriaSel) && "0".equals(sedeSel)) {
			listaProyectos = servicioGeneral.obtenerObjetos(parteInicialConsulta
					+ " where ip.proyecto = p and ip.tipo.id = 'P' and p.estadoProyecto.id <> 'B' and p.id in (select c.idProyecto from ProyectoCoordinador c where c.perEvaluacion ='"
					+ persona.getId().getDocumento() + "' and c.tdoId3 = '" + persona.getId().getTipoDocumento()
					+ "') order by p.id");
		} else if ("15".equals(convocatoriaSel) && !"0".equals(sedeSel) && "0".equals(facultadSel)) {
			listaProyectos = servicioGeneral.obtenerObjetos(parteInicialConsulta
					+ ", InvestigadorInterno ii where ip.proyecto = p and ip.tipo.id = 'P' and ii.id.tipoDocumento = ip.investigador.id.tipoDocumento and ii.id.documento = ip.investigador.id.documento and ii.dependencia.sede.id = '"
					+ sedeSel
					+ "' and p.estadoProyecto.id <> 'B' and p.id in (select c.idProyecto from ProyectoCoordinador c where c.perEvaluacion ='"
					+ persona.getId().getDocumento() + "' and c.tdoId3 = '" + persona.getId().getTipoDocumento()
					+ "') order by p.id");
		} else if ("15".equals(convocatoriaSel) && !"0".equals(sedeSel) && !"0".equals(facultadSel)) {
			listaProyectos = servicioGeneral.obtenerObjetos(parteInicialConsulta
					+ ", InvestigadorInterno ii where ip.proyecto = p and ip.tipo.id = 'P' and ii.id.tipoDocumento = ip.investigador.id.tipoDocumento and ii.id.documento = ip.investigador.id.documento and ii.dependencia.sede.id = '"
					+ sedeSel + "' and ii.dependencia.facultad.id = '" + facultadSel
					+ "' and p.estadoProyecto.id <> 'B' and p.id in (select c.idProyecto from ProyectoCoordinador c where c.perEvaluacion ='"
					+ persona.getId().getDocumento() + "' and c.tdoId3 = '" + persona.getId().getTipoDocumento()
					+ "') order by p.id");
		} else if (mostrarModalidades && "0".equals(sedeSel)) {
			listaProyectos = servicioGeneral.obtenerObjetos(parteInicialConsulta
					+ " where ip.proyecto = p and ip.tipo.id = 'P' and p.modalidad.id = '" + modalidadSel
					+ "' and p.estadoProyecto.id <> 'B' and p.id in (select c.idProyecto from ProyectoCoordinador c where c.perEvaluacion ='"
					+ persona.getId().getDocumento() + "' and c.tdoId3 = '" + persona.getId().getTipoDocumento()
					+ "') order by p.id");
		} else if (mostrarModalidades && !"0".equals(sedeSel) && "0".equals(facultadSel)) {
			listaProyectos = servicioGeneral.obtenerObjetos(parteInicialConsulta
					+ ", InvestigadorInterno ii where ip.proyecto = p and ip.tipo.id = 'P' and ii.id.tipoDocumento = ip.investigador.id.tipoDocumento and ii.id.documento = ip.investigador.id.documento and ii.dependencia.sede.id = '"
					+ sedeSel + "' and p.modalidad.id = '" + modalidadSel
					+ "' and p.estadoProyecto.id <> 'B' and p.id in (select c.idProyecto from ProyectoCoordinador c where c.perEvaluacion ='"
					+ persona.getId().getDocumento() + "' and c.tdoId3 = '" + persona.getId().getTipoDocumento()
					+ "') order by p.id");
		} else if (mostrarModalidades && !"0".equals(sedeSel) && !"0".equals(facultadSel)) {
			listaProyectos = servicioGeneral.obtenerObjetos(parteInicialConsulta
					+ ", InvestigadorInterno ii where ip.proyecto = p and ip.tipo.id = 'P' and ii.id.tipoDocumento = ip.investigador.id.tipoDocumento and ii.id.documento = ip.investigador.id.documento and ii.dependencia.sede.id = '"
					+ sedeSel + "' and ii.dependencia.facultad.id = '" + facultadSel + "' and p.modalidad.id = '"
					+ modalidadSel
					+ "' and p.estadoProyecto.id <> 'B' and p.id in (select c.idProyecto from ProyectoCoordinador c where c.perEvaluacion ='"
					+ persona.getId().getDocumento() + "' and c.tdoId3 = '" + persona.getId().getTipoDocumento()
					+ "') order by p.id");
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	private void consultaCoordinadorRequisitos(Persona persona, String parteInicialConsulta) {
		if ("15".equals(convocatoriaSel) && "0".equals(sedeSel)) {
			listaProyectos = servicioGeneral.obtenerObjetos(parteInicialConsulta
					+ " where ip.proyecto = p and ip.tipo.id = 'P' and p.estadoProyecto.id <> 'B' and p.id in (select c.idProyecto from ProyectoCoordinador c where c.perRevision ='"
					+ persona.getId().getDocumento() + "' and c.tdoId2 = '" + persona.getId().getTipoDocumento()
					+ "')");
		} else if ("15".equals(convocatoriaSel) && !"0".equals(sedeSel) && "0".equals(facultadSel)) {
			listaProyectos = servicioGeneral.obtenerObjetos(parteInicialConsulta
					+ ", InvestigadorInterno ii where ip.proyecto = p and ip.tipo.id = 'P' and ii.id.tipoDocumento = ip.investigador.id.tipoDocumento and ii.id.documento = ip.investigador.id.documento and ii.dependencia.sede.id = '"
					+ sedeSel
					+ "' and p.estadoProyecto.id <> 'B' and p.id in (select c.idProyecto from ProyectoCoordinador c where c.perRevision ='"
					+ persona.getId().getDocumento() + "' and c.tdoId2 = '" + persona.getId().getTipoDocumento()
					+ "')");
		} else if ("15".equals(convocatoriaSel) && !"0".equals(sedeSel) && !"0".equals(facultadSel)) {
			listaProyectos = servicioGeneral.obtenerObjetos(parteInicialConsulta
					+ ", InvestigadorInterno ii where ip.proyecto = p and ip.tipo.id = 'P' and ii.id.tipoDocumento = ip.investigador.id.tipoDocumento and ii.id.documento = ip.investigador.id.documento and ii.dependencia.sede.id = '"
					+ sedeSel + "' and ii.dependencia.facultad.id = '" + facultadSel
					+ "' and p.estadoProyecto.id <> 'B' and p.id in (select c.idProyecto from ProyectoCoordinador c where c.perRevision ='"
					+ persona.getId().getDocumento() + "' and c.tdoId2 = '" + persona.getId().getTipoDocumento()
					+ "')");
		} else if (mostrarModalidades && "0".equals(sedeSel)) {
			listaProyectos = servicioGeneral.obtenerObjetos(parteInicialConsulta
					+ " where ip.proyecto = p and ip.tipo.id = 'P' and p.modalidad.id = '" + modalidadSel
					+ "' and p.estadoProyecto.id <> 'B' and p.id in (select c.idProyecto from ProyectoCoordinador c where c.perRevision ='"
					+ persona.getId().getDocumento() + "' and c.tdoId2 = '" + persona.getId().getTipoDocumento()
					+ "')");
		} else if (mostrarModalidades && !"0".equals(sedeSel) && "0".equals(facultadSel)) {
			listaProyectos = servicioGeneral.obtenerObjetos(parteInicialConsulta
					+ ", InvestigadorInterno ii where ip.proyecto = p and ip.tipo.id = 'P' and ii.id.tipoDocumento = ip.investigador.id.tipoDocumento and ii.id.documento = ip.investigador.id.documento and ii.dependencia.sede.id = '"
					+ sedeSel + "' and p.modalidad.id = '" + modalidadSel
					+ "' and p.estadoProyecto.id <> 'B' and p.id in (select c.idProyecto from ProyectoCoordinador c where c.perRevision ='"
					+ persona.getId().getDocumento() + "' and c.tdoId2 = '" + persona.getId().getTipoDocumento()
					+ "')");
		} else if (mostrarModalidades && !"0".equals(sedeSel) && !"0".equals(facultadSel)) {
			listaProyectos = servicioGeneral.obtenerObjetos(parteInicialConsulta
					+ ", InvestigadorInterno ii where ip.proyecto = p and ip.tipo.id = 'P' and ii.id.tipoDocumento = ip.investigador.id.tipoDocumento and ii.id.documento = ip.investigador.id.documento and ii.dependencia.sede.id = '"
					+ sedeSel + "' and ii.dependencia.facultad.id = '" + facultadSel + "' and p.modalidad.id = '"
					+ modalidadSel
					+ "' and p.estadoProyecto.id <> 'B' and p.id in (select c.idProyecto from ProyectoCoordinador c where c.perRevision ='"
					+ persona.getId().getDocumento() + "' and c.tdoId2 = '" + persona.getId().getTipoDocumento()
					+ "')");
		}
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	private void consultaCoordinadorSeguimiento(Persona persona, String parteInicialConsulta) {

		if ("15".equals(convocatoriaSel) && "0".equals(sedeSel)) {
			listaProyectos = servicioGeneral.obtenerObjetos(parteInicialConsulta
					+ " where ip.proyecto = p and ip.tipo.id = 'P' and p.estadoProyecto.id <> 'B'"
					+ " and p.id in (select c.idProyecto from ProyectoCoordinador c where c.perId ='"
					+ persona.getId().getDocumento() + "' and c.tdoId = '" + persona.getId().getTipoDocumento() + "')");
		} else if ("15".equals(convocatoriaSel) && !"0".equals(sedeSel) && "0".equals(facultadSel)) {
			listaProyectos = servicioGeneral.obtenerObjetos(parteInicialConsulta
					+ ", InvestigadorInterno ii where ip.proyecto = p and ip.tipo.id = 'P' and ii.id.tipoDocumento = ip.investigador.id.tipoDocumento and ii.id.documento = ip.investigador.id.documento and ii.dependencia.sede.id = '"
					+ sedeSel
					+ "' and p.estadoProyecto.id <> 'B' and p.id in (select c.idProyecto from ProyectoCoordinador c where c.perId ='"
					+ persona.getId().getDocumento() + "' and c.tdoId = '" + persona.getId().getTipoDocumento() + "')");
		} else if ("15".equals(convocatoriaSel) && !"0".equals(sedeSel) && !"0".equals(facultadSel)) {
			listaProyectos = servicioGeneral.obtenerObjetos(parteInicialConsulta
					+ ", InvestigadorInterno ii where ip.proyecto = p and ip.tipo.id = 'P' and ii.id.tipoDocumento = ip.investigador.id.tipoDocumento and ii.id.documento = ip.investigador.id.documento and ii.dependencia.sede.id = '"
					+ sedeSel + "' and ii.dependencia.facultad.id = '" + facultadSel
					+ "' and p.estadoProyecto.id <> 'B' and p.id in (select c.idProyecto from ProyectoCoordinador c where c.perId ='"
					+ persona.getId().getDocumento() + "' and c.tdoId = '" + persona.getId().getTipoDocumento() + "')");
		} else if (mostrarModalidades && "0".equals(sedeSel)) {
			listaProyectos = servicioGeneral.obtenerObjetos(parteInicialConsulta
					+ " where ip.proyecto = p and ip.tipo.id = 'P' and p.modalidad.id = '" + modalidadSel
					+ "' and p.estadoProyecto.id <> 'B' and p.id in (select c.idProyecto from ProyectoCoordinador c where c.perId ='"
					+ persona.getId().getDocumento() + "' and c.tdoId = '" + persona.getId().getTipoDocumento() + "')");
		} else if (mostrarModalidades && !"0".equals(sedeSel) && "0".equals(facultadSel)) {
			listaProyectos = servicioGeneral.obtenerObjetos(parteInicialConsulta
					+ ", InvestigadorInterno ii where ip.proyecto = p and ip.tipo.id = 'P' and ii.id.tipoDocumento = ip.investigador.id.tipoDocumento and ii.id.documento = ip.investigador.id.documento and ii.dependencia.sede.id = '"
					+ sedeSel + "' and p.modalidad.id = '" + modalidadSel
					+ "' and p.estadoProyecto.id <> 'B' and p.id in (select c.idProyecto from ProyectoCoordinador c where c.perId ='"
					+ persona.getId().getDocumento() + "' and c.tdoId = '" + persona.getId().getTipoDocumento() + "')");
		} else if (mostrarModalidades && !"0".equals(sedeSel) && !"0".equals(facultadSel)) {
			listaProyectos = servicioGeneral.obtenerObjetos(parteInicialConsulta
					+ ", InvestigadorInterno ii where ip.proyecto = p and ip.tipo.id = 'P' and ii.id.tipoDocumento = ip.investigador.id.tipoDocumento and ii.id.documento = ip.investigador.id.documento and ii.dependencia.sede.id = '"
					+ sedeSel + "' and ii.dependencia.facultad.id = '" + facultadSel + "' and p.modalidad.id = '"
					+ modalidadSel
					+ "' and p.estadoProyecto.id <> 'B' and p.id in (select c.idProyecto from ProyectoCoordinador c where c.perId ='"
					+ persona.getId().getDocumento() + "' and c.tdoId = '" + persona.getId().getTipoDocumento() + "')");
		}
	}

	public void consultarProyectosMorosos() {
		Persona persona = (Persona) sesion.getAttribute("persona");
		listaProyectosMorosos = new ArrayList<Proyecto>();

		if ("15".equals(convocatoriaSel)) {
			listaProyectosMorosos = servicioProyecto.obtenerProyectosCompromisosPendientes(persona, "");
		} else if (mostrarModalidades) {
			listaProyectosMorosos = servicioProyecto.obtenerProyectosCompromisosPendientes(persona, modalidadSel);
		}
		if (!esListaVacia(listaProyectosMorosos)) {
			setTotalProyectosMorosos(listaProyectosMorosos.size());
		}
	}

	public String cargarProyecto() {
		if (proyectoSeleccionado != null) {
			sesion.setAttribute("idProyectoSeguimientoCoordinador", proyectoSeleccionado);
			return "informacionProyecto";
		}
		return "";
	}

	public String consultarEvaluacionProyectos() {
		personaActual = (Persona) sesion.getAttribute("persona");

		if (!esListaVacia(listaProyectos)) {
			String ids = "";

			Iterator<Proyecto> itr = listaProyectos.iterator();
			while (itr.hasNext()) {
				Proyecto p = (Proyecto) itr.next();
				ids = ids + " " + p.getId().toString();
			}

			ReporteBirt r = new ReporteBirt();

			r.setNombreReporte("/reportes-proyectos/reporteEvaluacionProyectos");
			r.adicionarParametro("ids", ids);
			r.setFormato(ReporteBirt.FORMATO_XLS);
			sesion.setAttribute("reporte", r);
			FacesContext context = FacesContext.getCurrentInstance();
			r.run(context);
		}
		return "";
	}

	public SelectItem[] getConvocatoriaItem() {
		return convocatoriaItem;
	}

	public Long getProyectoSeleccionado() {
		return proyectoSeleccionado;
	}

	public void setProyectoSeleccionado(Long proyectoSeleccionado) {
		this.proyectoSeleccionado = proyectoSeleccionado;
	}

	public void setConvocatoriaItem(SelectItem[] convocatoriaItem) {
		this.convocatoriaItem = convocatoriaItem;
	}

	public String getConvocatoriaSel() {
		return convocatoriaSel;
	}

	public void setConvocatoriaSel(String convocatoriaSel) {
		this.convocatoriaSel = convocatoriaSel;
	}

	public List<ConvocatoriaPadre> getListadoConvocatoriaPadre() {
		return listadoConvocatoriaPadre;
	}

	public void setListadoConvocatoriaPadre(List<ConvocatoriaPadre> listadoConvocatoriaPadre) {
		this.listadoConvocatoriaPadre = listadoConvocatoriaPadre;
	}

	public String getSedeSel() {
		return sedeSel;
	}

	public void setSedeSel(String sedeSel) {
		this.sedeSel = sedeSel;
	}

	public String getFacultadSel() {
		return facultadSel;
	}

	public void setFacultadSel(String facultadSel) {
		this.facultadSel = facultadSel;
	}

	public boolean isMostrarFacultades() {
		return mostrarFacultades;
	}

	public void setMostrarFacultades(boolean mostrarFacultades) {
		this.mostrarFacultades = mostrarFacultades;
	}

	public Integer getCoorSel() {
		return coorSel;
	}

	public void setCoorSel(Integer coorSel) {
		this.coorSel = coorSel;
	}

	public SelectItem[] getTipoCoordinadorItem() {
		return tipoCoordinadorItem;
	}

	public void setTipoCoordinadorItem(SelectItem[] tipoCoordinadorItem) {
		this.tipoCoordinadorItem = tipoCoordinadorItem;
	}

	public List<Proyecto> getListaProyectos() {
		return listaProyectos;
	}

	public void setListaProyectos(List<Proyecto> listaProyectos) {
		this.listaProyectos = listaProyectos;
	}

	public Integer getTotalProyectos() {
		return totalProyectos;
	}

	public void setTotalProyectos(Integer totalProyectos) {
		this.totalProyectos = totalProyectos;
	}

	public String getModalidadSel() {
		return modalidadSel;
	}

	public void setModalidadSel(String modalidadSel) {
		this.modalidadSel = modalidadSel;
	}

	public SelectItem[] getModalidadItem() {
		return modalidadItem;
	}

	public void setModalidadItem(SelectItem[] modalidadItem) {
		this.modalidadItem = modalidadItem;
	}

	public SelectItem[] getSedeItem() {
		return sedeItem;
	}

	public void setSedeItem(SelectItem[] sedeItem) {
		this.sedeItem = sedeItem;
	}

	public List<Dependencia> getFacultadesUN() {
		return facultadesUN;
	}

	public void setFacultadesUN(List<Dependencia> facultadesUN) {
		this.facultadesUN = facultadesUN;
	}

	public SelectItem[] getFacultadItem() {
		return facultadItem;
	}

	public void setFacultadItem(SelectItem[] facultadItem) {
		this.facultadItem = facultadItem;
	}

	public List getListaConvocatorias() {
		return listaConvocatorias;
	}

	public void setListaConvocatorias(List listaConvocatorias) {
		this.listaConvocatorias = listaConvocatorias;
	}

	public boolean isMostrarModalidades() {
		return mostrarModalidades;
	}

	public void setMostrarModalidades(boolean mostrarModalidades) {
		this.mostrarModalidades = mostrarModalidades;
	}

	public List getFilteredProyectos() {
		return filteredProyectos;
	}

	public void setFilteredProyectos(List filteredProyectos) {
		this.filteredProyectos = filteredProyectos;
	}

	public List<Proyecto> getListaProyectosMorosos() {
		return listaProyectosMorosos;
	}

	public void setListaProyectosMorosos(List<Proyecto> listaProyectosMorosos) {
		this.listaProyectosMorosos = listaProyectosMorosos;
	}

	public Integer getTotalProyectosMorosos() {
		return totalProyectosMorosos;
	}

	public void setTotalProyectosMorosos(Integer totalProyectosMorosos) {
		this.totalProyectosMorosos = totalProyectosMorosos;
	}
}
