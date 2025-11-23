package co.edu.unal.hermes.vista;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.bd.conexion.ConexionBDECP;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Preinscripcion_ECP;
import co.edu.unal.hermes.modelo.Proyecto;

public class ManejadorCursosAsistenteECP extends ManejadorBase {

	private List listaCursosAsistente;
	private List listaPreinscripcionesPersona;
	private List<Proyecto> cursosFiltrados;
	private List<Preinscripcion_ECP> listaPreinscripcionesfiltradas;
	private InvestigadorInterno investigadorInterno;
	private Proyecto cursoSeleccionado;
	private Investigador investigadorActual;
	private List<SelectItem> listaEstadosProyectoItem;

	private List<SelectItem> listaEstadosPreinscripcionItem;
	private Preinscripcion_ECP preinscripcionSeleccionada;

	public ManejadorCursosAsistenteECP() {
		listaCursosAsistente = new ArrayList<Proyecto>();
		listaEstadosProyectoItem = new ArrayList<SelectItem>();
		listaPreinscripcionesPersona = new ArrayList<Preinscripcion_ECP>();
		// listaPreinscripcionesfiltradas = new ArrayList<Preinscripcion_ECP>();
		listaEstadosPreinscripcionItem = new ArrayList<SelectItem>();

		Investigador investigadorActual = new Investigador();
		investigadorActual.setId(((Persona) sesion.getAttribute("persona"))
				.getId());
		this.investigadorInterno = servicioPersona
				.obtenerInvestigadorInterno(investigadorActual.getId());

		this.investigadorActual = servicioPersona
				.obtenerInvestigadorProyectos(((Persona) sesion
						.getAttribute("persona")).getId());

		cargarCursosECP();
		cargarEstados();
		cargarEstadosPreinscripcion();
	}

	public class CursoPreinscripcion {

		private Preinscripcion_ECP preinscripcion;
		private Proyecto curso;

		public Preinscripcion_ECP getPreinscripcion() {
			return preinscripcion;
		}

		public void setPreinscripcion(Preinscripcion_ECP preinscripcion) {
			this.preinscripcion = preinscripcion;
		}

		public Proyecto getCurso() {
			return curso;
		}

		public void setCurso(Proyecto curso) {
			this.curso = curso;
		}

	}

	private void cargarEstados() {
		String hql = "select pp from EstadoProyecto pp where pp.id in ('A','AP','PB','CN','F')";
		List lista = servicioGeneral.obtenerObjetos(hql);

		if (lista.size() != 0 && listaEstadosProyectoItem != null) {
			listaEstadosProyectoItem.add(new SelectItem("", "Todos"));
			for (int i = 0; i < lista.size(); i++) {
				EstadoProyecto estado = (EstadoProyecto) lista.get(i);
				listaEstadosProyectoItem.add(new SelectItem(estado.getNombre(),
						estado.getNombre()));
			}
		}
	}

	private void cargarEstadosPreinscripcion() {
		listaEstadosPreinscripcionItem.add(new SelectItem("", "Todos"));
		listaEstadosPreinscripcionItem.add(new SelectItem("P", "Preinscrito"));
		listaEstadosPreinscripcionItem.add(new SelectItem("FE",
				"Formalizado Estudiante"));
		listaEstadosPreinscripcionItem.add(new SelectItem("FF",
				"Formalizado Facultad"));

	}

	private void cargarCursosECP() {
		try {

			String hql = "Select pp from Preinscripcion_ECP pp "
					+ "where pp.investigador.id.tipoDocumento = '"
					+ investigadorActual.getId().getTipoDocumento() + "' "
					+ "and pp.investigador.id.documento = '"
					+ investigadorActual.getId().getDocumento() + "'";

			List lista = servicioGeneral.obtenerObjetos(hql);

			for (int i = 0; i < lista.size(); i++) {
				Preinscripcion_ECP pre = (Preinscripcion_ECP) lista.get(i);
				listaPreinscripcionesPersona.add(pre);
				listaCursosAsistente.add(pre.getCurso());
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	public String formalizarCurso() {
		sesion.removeAttribute("ManejadorEditarCursoECP");
		sesion.removeAttribute("ManejadorCursosAsistenteECP");
		sesion.removeAttribute("ManejadorFormalizarCursosAsistenteECP");

		// sesion.setAttribute("cursoECP", cursoSeleccionado);
		sesion.setAttribute("cursoECP", preinscripcionSeleccionada.getCurso());

		for (int i = 0; i < listaPreinscripcionesPersona.size(); i++) {
			Preinscripcion_ECP pre = (Preinscripcion_ECP) listaPreinscripcionesPersona
					.get(i);

			if (pre.getCurso().getId() == preinscripcionSeleccionada.getCurso()
					.getId()) {
				if (pre.getEstado().equals("P")) {
					sesion.setAttribute("preinscripcionECP", pre);
					System.out
							.println("Se envió objeto PREINSCRIPCION_ECP por la SESION - "
									+ pre.getId_pre());
				} else {
					FacesMessage msg = new FacesMessage(
							FacesMessage.SEVERITY_ERROR, "",
							"El proceso de formalizacion ya fue hecho previamente");
					FacesContext.getCurrentInstance().addMessage("growl", msg);
					System.out
							.println("NOOOO Se envió objeto PREINSCRIPCION_ECP por la SESION - YA SE HIZO FORMALIZACIÓN");
					return null;
				}
			} else
				System.out
						.println("NOOOO Se envió objeto PREINSCRIPCION_ECP por la SESION");

		}

		return "formalizarCurso";
	}

	public void descargarCertificado() {
		// Descargar Certificado
		String url = "";

		personaActual = (Persona) sesion.getAttribute("persona");

		String sql = "SELECT 'http://www.extension.hermes.unal.edu.co/apex/f?p=202:301:::::P301_USUARIO_HERMES,P301_PAGE,P301_ID_ALUMNO:'||uec_schema.uecf_hash_hermes('EVALUACION','USUARIOECP')||',23,'||id_alumno as ENLACE from uec_schema.uect_alumnos where numero_documento = '"
				+ personaActual.getId().getDocumento() + "'";

		url = ConexionBDECP.execQueryLink(sql);

		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			fc.getExternalContext().redirect(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void realizarEvaluacion() {
		// Realizar evaluacion
		String url = "";

		personaActual = (Persona) sesion.getAttribute("persona");

		String sql = "SELECT 'http://www.extension.hermes.unal.edu.co/apex/f?p=202:301:::::P301_USUARIO_HERMES,P301_ID_ALUMNO:'||uec_schema.uecf_hash_hermes('EVALUACION','USUARIOECP')||','||id_alumno as ENLACE from uec_schema.uect_alumnos where numero_documento = '"
				+ personaActual.getId().getDocumento() + "'";

		url = ConexionBDECP.execQueryLink(sql);

		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			fc.getExternalContext().redirect(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String listaCurso() {
		sesion.removeAttribute("ManejadorEditarCursoECP");
		sesion.removeAttribute("ManejadorListaPreinscritosECP");

		sesion.setAttribute("cursoECP", preinscripcionSeleccionada.getCurso());

		return "verListaInscritos";
	}

	public InvestigadorInterno getInvestigadorInterno() {
		return investigadorInterno;
	}

	public List getListaCursosAsistente() {
		return listaCursosAsistente;
	}

	public void setListaCursosAsistente(List listaCursosAsistente) {
		this.listaCursosAsistente = listaCursosAsistente;
	}

	public void setInvestigadorInterno(InvestigadorInterno investigadorInterno) {
		this.investigadorInterno = investigadorInterno;
	}

	public Proyecto getCursoSeleccionado() {
		return cursoSeleccionado;
	}

	public void setCursoSeleccionado(Proyecto cursoSeleccionado) {
		this.cursoSeleccionado = cursoSeleccionado;
	}

	public List<Proyecto> getCursosFiltrados() {
		return cursosFiltrados;
	}

	public void setCursosFiltrados(List<Proyecto> cursosFiltrados) {
		this.cursosFiltrados = cursosFiltrados;
	}

	public Investigador getInvestigadorActual() {
		return investigadorActual;
	}

	public void setInvestigadorActual(Investigador investigadorActual) {
		this.investigadorActual = investigadorActual;
	}

	public List<SelectItem> getListaEstadosProyectoItem() {
		return listaEstadosProyectoItem;
	}

	public void setListaEstadosProyectoItem(
			List<SelectItem> listaEstadosProyectoItem) {
		this.listaEstadosProyectoItem = listaEstadosProyectoItem;
	}

	public List getListaPreinscripcionesPersona() {
		return listaPreinscripcionesPersona;
	}

	public void setListaPreinscripcionesPersona(
			List listaPreinscripcionesPersona) {
		this.listaPreinscripcionesPersona = listaPreinscripcionesPersona;
	}

	public List<Preinscripcion_ECP> getListaPreinscripcionesfiltradas() {
		return listaPreinscripcionesfiltradas;
	}

	public void setListaPreinscripcionesfiltradas(
			List<Preinscripcion_ECP> listaPreinscripcionesfiltradas) {
		this.listaPreinscripcionesfiltradas = listaPreinscripcionesfiltradas;
	}

	public List<SelectItem> getListaEstadosPreinscripcionItem() {
		return listaEstadosPreinscripcionItem;
	}

	public void setListaEstadosPreinscripcionItem(
			List<SelectItem> listaEstadosPreinscripcionItem) {
		this.listaEstadosPreinscripcionItem = listaEstadosPreinscripcionItem;
	}

	public Preinscripcion_ECP getPreinscripcionSeleccionada() {
		return preinscripcionSeleccionada;
	}

	public void setPreinscripcionSeleccionada(
			Preinscripcion_ECP preinscripcionSeleccionada) {
		this.preinscripcionSeleccionada = preinscripcionSeleccionada;
	}

}
