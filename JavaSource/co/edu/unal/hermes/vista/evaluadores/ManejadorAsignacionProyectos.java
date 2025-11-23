package co.edu.unal.hermes.vista.evaluadores;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Modalidad;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.Rol;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.seguimiento.AlertaProyecto;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorAsignacionProyectos extends ManejadorBase {

	private static final long serialVersionUID = 4356358879950612228L;

	private String convocatoria;

	// Listas necesarias para el funcionamiento de la vista
	private SelectItem[] coordinadoresItem;
	private SelectItem[] coordinadoresItemRev;
	private SelectItem[] coordinadoresItemEv;
	private SelectItem[] convocatoriaItem;
	private SelectItem[] convocatoriaPadreItem;
	private SelectItem[] facultadItem;
	private SelectItem[] estadoItem;
	private SelectItem[] sedeItem;
	private SelectItem[] coordinadorNuevoItem;

	// Objetos necesarios para ejecución en local.
	private List<Persona> listaCoordinadores;
	private List listaConvocatorias;
	private List listaConvocatoriasPadre;
	private List<ProyectoCoordinador> listaProyectosCoordinador;
	private List<ProyectoCoordinador> listaProyectosCoordinadorRev;
	private List<ProyectoCoordinador> listaProyectosCoordinadorEv;
	private List listaProyectosCoordinadorAux;
	private List listaProyectosCoordinadorAux2;
	private List listaProyectosCoordinadorAux3;
	
	//Variables para ingreso de datos
	private String nombreModalidad;
	private String convocatoriaPadre;
	private String proyectoId;
	private String facultad;
	private String estado;
	private String sede;
	private String coordinadorNuevo;
	
	//Varianles para manejo de renderes en vista
	private boolean mostrarTabla;
	private String mensajeAsignacion;
	private int total;
	private int totalAsignados;
	private int totalSinAsignar;
	private String nombreConvocatoria;
	List listaHijo;

	private String errores[];
	private boolean panelRender[];
	private boolean panelRenderError[];
	private String tipoBusqueda = "Convocatoria";
	private String codigo;
	private String codigoProyecto;
	private String buscarFac;
	private boolean habBusFac;
	private String perIdSeguim;
	private String perIdRevision;
	private String perIdEvaluacion;
	private String perNombreSeguim;
	private Persona personaSeguimiento;
	private String perNombreRev;
	private Persona personaRevision;
	private String perNombreEval;
	private Persona personaEvaluac;
	private String tipoDoc;
	private String tipoDoc2;
	private String tipoDoc3;
	private String coordinadoresActuales;
	private String estadoPadre = "";
	private boolean esModLegalizacion;

	public ManejadorAsignacionProyectos() {
		listaConvocatorias = new ArrayList();
		listaConvocatoriasPadre = new ArrayList();
		listaProyectosCoordinadorAux = new ArrayList();
		listaProyectosCoordinadorAux2 = new ArrayList();
		listaProyectosCoordinadorAux3 = new ArrayList();
		listaProyectosCoordinador = new ArrayList();
		listaCoordinadores = new ArrayList<Persona>();
		personaActual = (Persona) sesion.getAttribute("persona");
		total = 0;
		listaHijo = new ArrayList();

		errores = new String[60];
		panelRender = new boolean[10];
		panelRenderError = new boolean[60];

		buscarFac = "No";
		panelRender[3] = false;
		panelRender[4] = true;
		panelRender[1] = true;

		estadoPadre = "A";

		cargarCoordinadores();
		convocatoriaPadreItem = new SelectItem[0];
		convocatoriaItem = new SelectItem[0];
		cargarSede();
		cargarEstadoProyectos();

	}

	public void consultarConvocatorias() {
		mensajeAsignacion = "";
		if (nombreConvocatoria != null && nombreConvocatoria.length() > 0) {
			listaConvocatoriasPadre = servicioGeneral.obtenerListaObjetos(
					"ConvocatoriaPadre where upper(titulo) like '%" + nombreConvocatoria.toUpperCase() + "%' order by id");
			if (listaConvocatoriasPadre != null && listaConvocatoriasPadre.size() > 0) {
				convocatoriaPadreItem = new SelectItem[listaConvocatoriasPadre.size()];

				for (int i = 0; i < listaConvocatoriasPadre.size(); i++) {
					ConvocatoriaPadre con = (ConvocatoriaPadre) listaConvocatoriasPadre.get(i);
					String nombre = con.getTitulo();
					if (con.getTitulo().length() > 300) {
						nombre = con.getTitulo().substring(0, 300) + "...";
					}
					convocatoriaPadreItem[i] = new SelectItem(con.getId().toString(), nombre);
				}

				panelRender[5] = true;
				if (listaConvocatoriasPadre != null && listaConvocatoriasPadre.size() > 0) {
					convocatoriaPadre = ((ConvocatoriaPadre) listaConvocatoriasPadre.get(0)).getTitulo();
				}

				if (listaConvocatoriasPadre != null && listaConvocatoriasPadre.size() > 0) {

					listaConvocatorias = servicioModalidad
							.obtenerConvocatoriasxPadre((ConvocatoriaPadre) listaConvocatoriasPadre.get(0));

					convocatoriaItem = new SelectItem[listaConvocatorias.size()];
					for (int i = 0; i < listaConvocatorias.size(); i++) {
						Convocatoria con = (Convocatoria) listaConvocatorias.get(i);
						String nombre = con.getTitulo();
						if (con.getTitulo().length() > 240) {
							nombre = con.getTitulo().substring(0, 240) + "...";
						}
						// if(con.getEstadoConvocatoria().getId().equals("I")){
						convocatoriaItem[i] = new SelectItem(con.getId().toString(), nombre);
						// }
					}
					if (listaConvocatorias != null && listaConvocatorias.size() > 0) {
						nombreModalidad = ((Convocatoria) listaConvocatorias.get(0)).getTitulo();
					}
				} else {
					listaConvocatorias = new ArrayList();
					convocatoriaItem = new SelectItem[listaConvocatorias.size()];
				}
			} else {
				listaConvocatoriasPadre = new ArrayList();
				listaConvocatorias = new ArrayList();
				convocatoriaPadreItem = new SelectItem[listaConvocatoriasPadre.size()];
				convocatoriaItem = new SelectItem[listaConvocatorias.size()];
				FacesContext.getCurrentInstance().addMessage("messagesCons",
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"No hay ninguna convocatoria que concuerde con la palabra buscada", ""));
				panelRender[5] = false;
			}
		} else {
			listaConvocatoriasPadre = new ArrayList();
			listaConvocatorias = new ArrayList();
			convocatoriaPadreItem = new SelectItem[listaConvocatoriasPadre.size()];
			convocatoriaItem = new SelectItem[listaConvocatorias.size()];
			FacesContext.getCurrentInstance().addMessage("messagesCons",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Por favor ingrese una palabra clave", ""));
			panelRender[5] = false;
		}
	}

	private void cargarSede() {
		List listaSede = servicioGeneral.obtenerObjetos("from Sede order by nombre asc");
		if (listaSede != null && listaSede.size() > 0) {
			sedeItem = new SelectItem[listaSede.size()];
			for (int i = 0; i < listaSede.size(); i++) {
				Sede sed = (Sede) listaSede.get(i);
				sedeItem[i] = new SelectItem(sed.getId().toString(), sed.getNombre());
			}
		}
		sede = "2";
		cargarFacultad();
	}

	public void cargarPadresEstado() {
		List listaEstados = new ArrayList();
		listaEstados.add(estadoPadre);
		listaConvocatoriasPadre = servicioModalidad.obtenerConvocatoriasPadreEnEstados(listaEstados);

		convocatoriaPadreItem = new SelectItem[listaConvocatoriasPadre.size()];
		for (int i = 0; i < listaConvocatoriasPadre.size(); i++) {
			ConvocatoriaPadre con = (ConvocatoriaPadre) listaConvocatoriasPadre.get(i);
			String nombre = con.getTitulo();
			if (con.getTitulo().length() > 80) {
				nombre = con.getTitulo().substring(0, 80) + "...";
			}
			convocatoriaPadreItem[i] = new SelectItem(con.getId().toString(), nombre);
		}
		if (listaConvocatoriasPadre != null && listaConvocatoriasPadre.size() > 0) {
			convocatoriaPadre = ((ConvocatoriaPadre) listaConvocatoriasPadre.get(0)).getTitulo();
		}

		List listaEstado = new ArrayList();

		listaEstado.add("I");

		listaConvocatorias = servicioModalidad.obtenerConvocatoriasXPadreYListaEstado(
				((ConvocatoriaPadre) listaConvocatoriasPadre.get(0)).getId(), listaEstado);

		convocatoriaItem = new SelectItem[listaConvocatorias.size()];
		for (int i = 0; i < listaConvocatorias.size(); i++) {
			Convocatoria con = (Convocatoria) listaConvocatorias.get(i);
			String nombre = con.getTitulo();
			if (con.getTitulo().length() > 120) {
				nombre = con.getTitulo().substring(0, 120) + "...";
			}
			convocatoriaItem[i] = new SelectItem(con.getId().toString(), nombre);
		}
		if (listaConvocatorias != null && listaConvocatorias.size() > 0) {
			nombreModalidad = ((Convocatoria) listaConvocatorias.get(0)).getTitulo();
		}
	}

	private void cargarEstadoProyectos() {

		List<EstadoProyecto> listaEstadosProyectos = servicioGeneral.obtenerObjetos(EstadoProyecto.class,
				"from EstadoProyecto");
		if (listaEstadosProyectos != null && listaEstadosProyectos.size() > 0) {
			estadoItem = new SelectItem[listaEstadosProyectos.size() + 1];
			estadoItem[0] = new SelectItem("P','R','N','AP','S','A','CN','F','I','E','H','B','BP", "Todos");
			for (int i = 0; i < listaEstadosProyectos.size(); i++) {
				EstadoProyecto estProy = (EstadoProyecto) listaEstadosProyectos.get(i);
				estadoItem[i + 1] = new SelectItem(estProy.getId(), estProy.getNombre());
			}
		}

	}

	public void cargarFacultad() {
		/* and sede.id= '"+sede+"' */
		mensajeAsignacion = "";
		String sedeSeleccionada = "";
		if (sede == null) {
			sedeSeleccionada = "2";
		} else {
			sedeSeleccionada = sede;
		}
		List listaFacultad = servicioGeneral
				.obtenerObjetos("from Dependencia d where d.esFacultad = 'Y' and d.sede.id= '" + sedeSeleccionada
						+ "' order by d.nombre asc");
		if (listaFacultad != null && listaFacultad.size() > 0) {
			facultadItem = new SelectItem[listaFacultad.size()];
			for (int i = 0; i < listaFacultad.size(); i++) {
				Dependencia dep = (Dependencia) listaFacultad.get(i);
				facultadItem[i] = new SelectItem(dep.getId().toString(), dep.getNombre());
			}
		} else {
			facultadItem = new SelectItem[0];
		}
	}

	private void cargarCoordinadores() {
		coordinadoresActuales = "";
		Rol r = new Rol();
		r.setId("C");
		Persona persona = (Persona) sesion.getAttribute("persona");
		listaCoordinadores = servicioPersona.obtenerListaCoordinadores(persona.getId().getDocumento());
		coordinadoresItem = new SelectItem[listaCoordinadores.size() + 1];
		setCoordinadoresItemRev(new SelectItem[listaCoordinadores.size() + 1]);
		setCoordinadoresItemEv(new SelectItem[listaCoordinadores.size() + 1]);
		coordinadorNuevoItem = new SelectItem[listaCoordinadores.size() + 1];
		coordinadoresItem[0] = new SelectItem("0", "Seleccione coordinador");
		coordinadoresItemRev[0] = new SelectItem("0", "Seleccione coordinador");
		coordinadoresItemEv[0] = new SelectItem("0", "Seleccione coordinador");
		coordinadorNuevoItem[0] = new SelectItem("0", "Seleccione coordinador");
		for (int i = 0; i < listaCoordinadores.size(); i++) {
			Persona p = (Persona) listaCoordinadores.get(i);
			coordinadoresActuales += p.getId().getDocumento() + p.getId().getTipoDocumento() + "==";
			coordinadoresItem[i + 1] = new SelectItem(p.getId().getDocumento(),
					p.getNombre1() + " " + p.getApellido1() + " " + p.getApellido2());
			coordinadoresItemRev[i + 1] = new SelectItem(p.getId().getDocumento(),
					p.getNombre1() + " " + p.getApellido1() + " " + p.getApellido2());
			coordinadoresItemEv[i + 1] = new SelectItem(p.getId().getDocumento(),
					p.getNombre1() + " " + p.getApellido1() + " " + p.getApellido2());
			coordinadorNuevoItem[i + 1] = new SelectItem(p.getId().getDocumento(),
					p.getNombre1() + " " + p.getApellido1() + " " + p.getApellido2());
		}
	}

	// por fac
	public void buscarProyectosxModalidad() {
		// ACA SE DEBEN TRAER LOS PROYECTOS CON SUS RESPECTIVOS ASESORES, ADEMAS
		// DEBEN TENER EL INVESTIGADOR PRINCIPAL
		// PARA PODER OBTENER LA FACULTAD
		mensajeAsignacion = "";
		listaProyectosCoordinador.clear();
		listaProyectosCoordinadorAux.clear();
		listaProyectosCoordinadorAux2.clear();
		listaProyectosCoordinadorAux3.clear();
		try {
			listaProyectosCoordinador.addAll(servicioProyecto.obtenerProyectosCoordinadorAsesorxModalidadxFacultad(
					buscarModalidadxId(convocatoria), facultad, estado));
		} catch (Exception e) {
			
		}

		mostrarTabla = false;
		if (listaProyectosCoordinador != null) {

			for (int i = 0; i < listaProyectosCoordinador.size(); i++) {
				ProyectoCoordinador p = (ProyectoCoordinador) listaProyectosCoordinador.get(i);
				p.setCoordinadoresAsesor(coordinadoresActuales);
				String perId = ((ProyectoCoordinador) (listaProyectosCoordinador.get(i))).getPerId();
				String perRevision = ((ProyectoCoordinador) (listaProyectosCoordinador.get(i))).getPerRevision();
				String perEvaluacion = ((ProyectoCoordinador) (listaProyectosCoordinador.get(i))).getPerEvaluacion();

				if (perId == null) {
					listaProyectosCoordinadorAux.add("0");
				} else {
					listaProyectosCoordinadorAux.add(perId);
				}
				if (perRevision == null) {
					listaProyectosCoordinadorAux2.add("0");
				} else {
					listaProyectosCoordinadorAux2.add(perRevision);
				}
				if (perEvaluacion == null) {
					listaProyectosCoordinadorAux3.add("0");
				} else {
					listaProyectosCoordinadorAux3.add(perEvaluacion);
				}

			}
		}
		if (listaProyectosCoordinador != null && listaProyectosCoordinador.size() > 0) {
			mostrarTabla = true;
		}
	}

	public void buscarProyectoxCodigo() {

		mostrarTabla = false;
		mensajeAsignacion = "";
		if(codigoProyecto != null && codigoProyecto.trim().length() > 0){
			ProyectoCoordinador proyecto = new ProyectoCoordinador();
			proyecto = servicioProyecto.obtenerProyectoCoordinadorxCodigo(codigoProyecto);
			listaProyectosCoordinador.clear();
			listaProyectosCoordinadorAux.clear();
			listaProyectosCoordinadorAux2.clear();
			listaProyectosCoordinadorAux3.clear();
	
			if (proyecto != null) {
				proyecto.setCoordinadoresAsesor(coordinadoresActuales);
				setPerNombreSeguim("0");
				setPerNombreRev("0");
				setPerNombreEval("0");
	
				if (proyecto.getPerId() != null) {
					setPerIdSeguim(proyecto.getPerId());
					setTipoDoc(proyecto.getTdoId());
					IdPersona pSeg = new IdPersona(perIdSeguim, tipoDoc);
					personaSeguimiento = servicioPersona.obtenerPersona(pSeg);
					setPerNombreSeguim(personaSeguimiento.getNombre1() + " " + personaSeguimiento.getApellido1());
				}
	
				if (proyecto.getPerRevision() != null) {
					setPerIdRevision(proyecto.getPerRevision());
					setTipoDoc2(proyecto.getTdoId2());
					IdPersona pRev = new IdPersona(perIdRevision, tipoDoc2);
					personaRevision = servicioPersona.obtenerPersona(pRev);
					setPerNombreRev(personaRevision.getNombre1() + " " + personaRevision.getApellido1());
				}
	
				if (proyecto.getPerEvaluacion() != null) {
					setPerIdEvaluacion(proyecto.getPerEvaluacion());
					setTipoDoc3(proyecto.getTdoId3());
					IdPersona pEval = new IdPersona(perIdEvaluacion, tipoDoc3);
					personaEvaluac = servicioPersona.obtenerPersona(pEval);
					setPerNombreEval(personaEvaluac.getNombre1() + " " + personaEvaluac.getApellido1());
				}
	
				if (perNombreSeguim.equals(" ")) {
					listaProyectosCoordinadorAux.add("0");
				} else {
					listaProyectosCoordinadorAux.add(perNombreSeguim);
				}
				if (perNombreRev == null) {
					listaProyectosCoordinadorAux2.add("0");
				} else {
					listaProyectosCoordinadorAux2.add(perNombreRev);
				}
				if (perNombreEval == null) {
					listaProyectosCoordinadorAux3.add("0");
				} else {
					listaProyectosCoordinadorAux3.add(perNombreEval);
				}
	
				listaProyectosCoordinador.add(proyecto);
				System.out.print("listaproyCood: " + listaProyectosCoordinador.size() + "\n");
				System.out.print("Proyecto: " + listaProyectosCoordinador.get(0).getNombreProyecto() + "\n");
				mostrarTabla = true;
				
				if(mostrarTabla){
					List listaPry = servicioGeneral.obtenerObjetoXID("Proyecto", codigoProyecto);
					if(listaPry.size()>0){
						Proyecto pry = (Proyecto) listaPry.get(0);
						if(pry.getModalidad().getId().equals(2L) || pry.getModalidad().getId().equals(10L)){
							esModLegalizacion = true;
						}else{
							esModLegalizacion = false;
						}
					}
				}
			}
		}
	}

	public void buscarProyectosxModalidadxSede() {

		mensajeAsignacion = "";
		listaProyectosCoordinador.clear();
		listaProyectosCoordinadorAux.clear();
		listaProyectosCoordinadorAux2.clear();
		listaProyectosCoordinadorAux3.clear();

		try {
			listaProyectosCoordinador.addAll(servicioProyecto
					.obtenerProyectosCoordinadorxModalidadxSede(buscarModalidadxId(convocatoria), sede, estado));
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		mostrarTabla = false;
		if (listaProyectosCoordinador != null) {

			for (int i = 0; i < listaProyectosCoordinador.size(); i++) {
				ProyectoCoordinador p = (ProyectoCoordinador) listaProyectosCoordinador.get(i);
				p.setCoordinadoresAsesor(coordinadoresActuales);
				String perId = ((ProyectoCoordinador) (listaProyectosCoordinador.get(i))).getPerId();
				String perRevision = ((ProyectoCoordinador) (listaProyectosCoordinador.get(i))).getPerRevision();
				String perEvaluacion = ((ProyectoCoordinador) (listaProyectosCoordinador.get(i))).getPerEvaluacion();
				if (perId == null) {
					listaProyectosCoordinadorAux.add("0");
				} else {
					listaProyectosCoordinadorAux.add(perId);
				}
				if (perRevision == null) {
					listaProyectosCoordinadorAux2.add("0");
				} else {
					listaProyectosCoordinadorAux2.add(perRevision);
				}
				if (perEvaluacion == null) {
					listaProyectosCoordinadorAux3.add("0");
				} else {
					listaProyectosCoordinadorAux3.add(perEvaluacion);
				}
			}
		}
		if (listaProyectosCoordinador != null && listaProyectosCoordinador.size() > 0) {
			mostrarTabla = true;
		}
	}

	public void buscarProyectoId() {

		mensajeAsignacion = "";
		List listaProyectoCoordinador = new ArrayList();

		listaProyectoCoordinador.addAll(servicioProyecto.obtenerProyectoXId(proyectoId));
		mostrarTabla = false;
		for (int j = 0; j < listaProyectoCoordinador.size(); j++) {
			ProyectoCoordinador p = (ProyectoCoordinador) listaProyectoCoordinador.get(j);
			String perId = ((ProyectoCoordinador) (listaProyectoCoordinador.get(j))).getPerId();
			String perRevision = ((ProyectoCoordinador) (listaProyectosCoordinador.get(j))).getPerRevision();
			String perEvaluacion = ((ProyectoCoordinador) (listaProyectosCoordinador.get(j))).getPerEvaluacion();
			if (perId == null) {
				listaProyectosCoordinadorAux.add("0");
			} else {
				listaProyectosCoordinadorAux.add(perId);
			}
			if (perRevision == null) {
				listaProyectosCoordinadorAux2.add("0");
			} else {
				listaProyectosCoordinadorAux2.add(perRevision);
			}
			if (perEvaluacion == null) {
				listaProyectosCoordinadorAux3.add("0");
			} else {
				listaProyectosCoordinadorAux3.add(perEvaluacion);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void guardarAsociacionProyectosCoordinador() {
		int j = 0;
		int k = 0;
		int l = 0;
		mensajeAsignacion = "";
		Persona asesor = (Persona) sesion.getAttribute("persona");
		boolean errorAsignacion = false;
		try {
			for (int i = 0; i < listaProyectosCoordinador.size(); i++) {
				String pco = (String) listaProyectosCoordinadorAux.get(i);
				String pco2 = (String) listaProyectosCoordinadorAux2.get(i);
				String pco3 = (String) listaProyectosCoordinadorAux3.get(i);
				ProyectoCoordinador pc = (ProyectoCoordinador) listaProyectosCoordinador.get(i);

				@SuppressWarnings("deprecation")
				List dato = servicioGeneral.obtenerObjetos(
						"select pc from ProyectoCoordinador pc where pc.idProyecto = '" + pc.idProyecto + "'");
				if (!dato.isEmpty()) {
					ProyectoCoordinador pcOriginal = (ProyectoCoordinador) dato.get(0);
					if (!pcOriginal.getPerId().equals("0") && pc.getPerId().equals("0")) {
						mensajeAsignacion += "Al proyecto con ID " + pc.getIdProyecto()
								+ " se le está quitando el coordinador de seguimiento. Por favor, asigne uno. ";
						errorAsignacion = true;
					} else if (pcOriginal.getPerRevision() != null && !pcOriginal.getPerRevision().equals("0")
							&& pc.getPerRevision().equals("0")) {
						mensajeAsignacion += "Al proyecto con ID " + pc.getIdProyecto()
								+ " se le está quitando el coordinador de revisión de requisitos o legalización. Por favor, asigne uno. ";
						errorAsignacion = true;
					} else if (pcOriginal.getPerEvaluacion() != null && pc.getPerEvaluacion() != null
							&& !pcOriginal.getPerEvaluacion().equals("0") && pc.getPerEvaluacion().equals("0")) {
						mensajeAsignacion += "Al proyecto con ID " + pc.getIdProyecto()
								+ " se le está quitando el coordinador de evaluacion. Por favor, asigne uno. ";
						errorAsignacion = true;
					}
				}
				if (pc.getPerId() != null) {
					if (pco.equals("0") && !pc.getPerId().equals("0")) {
						// EN EL CASO QUE EL COORDINADOR NO ESTE ASIGNADO SE
						// INSERTA
						j++;

						listaProyectosCoordinadorAux.set(i, pc.getPerId());
						Persona pb = buscarAsesorxDocumento(pc.getPerId());
						if (pb != null) {
							String consulta = " from ProyectoCoordinador pc where pc.idProyecto = '"
									+ pc.getIdProyecto().toString() + "'";
							List<ProyectoCoordinador> lista = servicioGeneral.obtenerObjetos(ProyectoCoordinador.class,
									consulta);
							if (lista.size() > 0) {
								servicioProyecto.modificarCoordinadorEnProyecto(ProyectoDAOHibernate.UPDATE, "S",
										pc.getIdProyecto().toString(), pb.getId(), asesor, "S", false);

							} else {
								servicioProyecto.modificarCoordinadorEnProyecto(ProyectoDAOHibernate.INSERT, "S",
										pc.getIdProyecto().toString(), pb.getId(), asesor, "S", false);
							}
						}
					}
					if (!pc.getPerId().equals(pco)) {
						if (pc.getPerId().equals("0")) {
							// Se consulta si no tiene solicitudes pendientes
							// por revisar
							String consulta = "from AlertaProyecto ap where ap.proyecto.id = '"
									+ pc.getIdProyecto().toString() + "' and ap.estado <> 'C'";
							List<AlertaProyecto> lista = servicioGeneral.obtenerObjetos(AlertaProyecto.class, consulta);
							if (lista != null && lista.size() > 0) {
								mensajeAsignacion += "El proyecto con código " + pc.getIdProyecto()
										+ " no puede estar sin "
										+ "coordinador asignado debido a que tiene alertas pendientes por revisión. Por favor tramite las "
										+ "alertas o asígnelo a otro coordinador. ";
								errorAsignacion = true;
							} else {
								// EN EL CASO QUE LA NUEVA ASIGNACION SEA VACIA
								// SE
								// BORRA EL COORDINADOR DEL PROYECTO
								listaProyectosCoordinadorAux.set(i, "0");
								IdPersona idPersona = new IdPersona(ProyectoCoordinador.COORDINADOR_PRUEBAS, "C");
								mensajeAsignacion += servicioProyecto.modificarCoordinadorEnProyecto(
										ProyectoDAOHibernate.DELETE, "S", pc.getIdProyecto().toString(), idPersona,
										asesor, "S", true);
							}
							l++;
						} else {
							// EN EL CASO QUE LA NUEVA ASIGNACION SEA DIFERENTE
							// SE ACTUALIZA EL COORDINADOR DEL PROYECTO
							k++;
							listaProyectosCoordinadorAux.set(i, pc.getPerId());
							Persona pb = buscarAsesorxDocumento(pc.getPerId());
							if (pb != null) {
								servicioProyecto.modificarCoordinadorEnProyecto(ProyectoDAOHibernate.UPDATE, "S",
										pc.getIdProyecto().toString(), pb.getId(), asesor, "S", true);

								// Se cargan las alertas al nuevo coordinador
								String sql = "UPDATE HER_ALERTA_PROYECTO SET PER_ID = '" + pb.getId().getDocumento()
										+ "', TDO_ID = '" + pb.getId().getTipoDocumento() + "' " + "WHERE PRY_ID = '"
										+ pc.getIdProyecto() + "' AND ALPR_ESTADO <> 'C'";
								servicioGeneral.ejecutarSentencia(sql);
							}
						}
					}
				} // fin IF perId !=null

				if (pc.getPerRevision() != null) {
					if (pco2.equals("0") && !pc.getPerRevision().equals("0")) {
						// EN EL CASO QUE EL COORDINADOR NO ESTE ASIGNADO SE
						// INSERTA
						j++;
						listaProyectosCoordinadorAux2.set(i, pc.getPerRevision());
						Persona pb = buscarAsesorxDocumento(pc.getPerRevision());
						if (pb != null) {
							if (pc.getPerId() != null && !pc.getPerId().equals("0")) {
								servicioProyecto.modificarCoordinadorEnProyecto(ProyectoDAOHibernate.UPDATE, "R",
										pc.getIdProyecto().toString(), pb.getId(), asesor, "R", false);
							} else {

								String consulta = "select pc from ProyectoCoordinador pc where pc.idProyecto = '"
										+ pc.getIdProyecto().toString() + "'";
								List lista = servicioGeneral.obtenerObjetos(consulta);

								if (lista.size() > 0) {
									servicioProyecto.modificarCoordinadorEnProyecto(ProyectoDAOHibernate.UPDATE, "R",
											pc.getIdProyecto().toString(), pb.getId(), asesor, "R", false);
								} else {
									servicioProyecto.modificarCoordinadorEnProyecto(ProyectoDAOHibernate.INSERT, "R",
											pc.getIdProyecto().toString(), pb.getId(), asesor, "R", false);
								}
							}
						}
					}
					if (!pc.getPerRevision().equals(pco2)) {
						if (pc.getPerRevision().equals("0")) {
							// EN EL CASO QUE LA NUEVA ASIGNACION SEA VACIA SE
							// BORRA EL COORDINADOR DEL PROYECTO
							l++;
							listaProyectosCoordinadorAux2.set(i, "0");
							IdPersona idPersona = new IdPersona("", "");
							mensajeAsignacion += servicioProyecto.modificarCoordinadorEnProyecto(
									ProyectoDAOHibernate.DELETE, "R", pc.getIdProyecto().toString(), idPersona, asesor,
									"R", true);
						} else {
							// EN EL CASO QUE LA NUEVA ASIGNACION SEA DIFERENTE
							// SE ACTUALIZA EL COORDINADOR DEL
							// PROYECTO---------------------------------
							k++;
							listaProyectosCoordinadorAux2.set(i, pc.getPerRevision());
							Persona pb = buscarAsesorxDocumento(pc.getPerRevision());
							if (pb != null) {
								servicioProyecto.modificarCoordinadorEnProyecto(ProyectoDAOHibernate.UPDATE, "R",
										pc.getIdProyecto().toString(), pb.getId(), asesor, "R", true);
							}
						}
					}

				} // fin If rev

				if (pc.getPerEvaluacion() != null) {

					if (pco3.equals("0") && !pc.getPerEvaluacion().equals("0")) {
						// EN EL CASO QUE EL COORDINADOR NO ESTE ASIGNADO SE
						// INSERTA
						j++;
						listaProyectosCoordinadorAux3.set(i, pc.getPerEvaluacion());
						Persona pb = buscarAsesorxDocumento(pc.getPerEvaluacion());
						if (pb != null) {
							if (pc.getPerId() != null && !pc.getPerId().equals("0")) {
								servicioProyecto.modificarCoordinadorEnProyecto(ProyectoDAOHibernate.UPDATE, "E",
										pc.getIdProyecto().toString(), pb.getId(), asesor, "E", false);
							} else {

								String consulta = "select pc from ProyectoCoordinador pc where pc.idProyecto = '"
										+ pc.getIdProyecto().toString() + "'";
								List lista = servicioGeneral.obtenerObjetos(consulta);

								if (lista.size() > 0) {
									servicioProyecto.modificarCoordinadorEnProyecto(ProyectoDAOHibernate.UPDATE, "E",
											pc.getIdProyecto().toString(), pb.getId(), asesor, "E", false);
								} else {
									servicioProyecto.modificarCoordinadorEnProyecto(ProyectoDAOHibernate.INSERT, "E",
											pc.getIdProyecto().toString(), pb.getId(), asesor, "E", false);
								}
							}
						}
					}
					if (!pc.getPerEvaluacion().equals(pco3)) {
						if (pc.getPerEvaluacion().equals("0")) {
							// EN EL CASO QUE LA NUEVA ASIGNACION SEA VACIA SE
							// BORRA EL COORDINADOR DEL PROYECTO
							l++;
							listaProyectosCoordinadorAux3.set(i, "0");
							IdPersona idPersona = new IdPersona("", "");
							mensajeAsignacion += servicioProyecto.modificarCoordinadorEnProyecto(
									ProyectoDAOHibernate.DELETE, "E", pc.getIdProyecto().toString(), idPersona, asesor,
									"E", true);
						} else {
							// EN EL CASO QUE LA NUEVA ASIGNACION SEA DIFERENTE
							// SE ACTUALIZA EL COORDINADOR DEL PROYECTO
							k++;
							listaProyectosCoordinadorAux3.set(i, pc.getPerEvaluacion());
							Persona pb = buscarAsesorxDocumento(pc.getPerEvaluacion());
							if (pb != null) {
								servicioProyecto.modificarCoordinadorEnProyecto(ProyectoDAOHibernate.UPDATE, "E",
										pc.getIdProyecto().toString(), pb.getId(), asesor, "E", true);
								Persona per = new Persona();
								per.getApellido1();
							}
						}
					}

				} // fin If evaluac
			}
		} catch (NullPointerException ex) {
			ex.printStackTrace();
			errorAsignacion = true;
		}
		if (errorAsignacion) {
			mensajeAsignacion += "Hubo un error en la asignación de los proyectos. Por favor verifique la información. ";
		}

		if ((mensajeAsignacion != null || mensajeAsignacion != "") && !errorAsignacion) {
			if (j != 0) {
				if (j > 1) {
					mensajeAsignacion += "Los coordinadores fueron insertados con exito.";
				} else {
					mensajeAsignacion += "El coordinador fue insertado con exito.";
				}
			}
			if (l != 0) {
				if (l > 1) {
					mensajeAsignacion += "Los coordinadores fueron modificados con exito.";
				} else {
					mensajeAsignacion += "El coordinador fue modificado con exito.";
				}
			}
			if (k != 0) {
				if (k > 1) {
					mensajeAsignacion += "Los coordinadores fueron actualizados con exito.";
				} else {
					mensajeAsignacion += "El coordinador fue actualizado con exito.";
				}
			}
		}
	}

	private Modalidad buscarModalidadxId(String idModalidad) {
		Iterator it = this.listaConvocatorias.iterator();
		Modalidad m = null;
		boolean modalidadEncontrada = false;
		while (it.hasNext() && !modalidadEncontrada) {
			m = (Modalidad) it.next();
			if (m.getId().longValue() == Long.parseLong(idModalidad)) {
				modalidadEncontrada = true;
			}
		}
		return m;
	}

	private ConvocatoriaPadre buscarConvocatoriaPadrexId(String idModalidad) {
		Iterator it = this.listaConvocatoriasPadre.iterator();
		ConvocatoriaPadre m = null;
		boolean modalidadEncontrada = false;
		while (it.hasNext() && !modalidadEncontrada) {
			m = (ConvocatoriaPadre) it.next();
			try {
				if (m.getId().longValue() == Long.parseLong(idModalidad)) {
					modalidadEncontrada = true;
				}
			} catch (NumberFormatException e) {

			}

		}
		return m;
	}

	private Persona buscarAsesorxDocumento(String documento) {
		Iterator<Persona> it = listaCoordinadores.iterator();
		Persona m = null;
		boolean personaEncontrada = false;
		while (it.hasNext() && !personaEncontrada) {
			m = (Persona) it.next();
			if (m.getId().getDocumento().equals(documento)) {
				personaEncontrada = true;
			}
		}
		if (personaEncontrada) {
			return m;
		} else {
			return null;
		}
	}

	public void cambiarSede(ValueChangeEvent event) {

		sede = (String) event.getNewValue();
		cargarFacultad();

	}

	public void cambiarConvocatoriaPadre() {

		mensajeAsignacion = "";
		ConvocatoriaPadre cp = buscarConvocatoriaPadrexId((String) convocatoriaPadre);
		convocatoriaPadre = cp.getTitulo();
		listaConvocatorias.clear();

		listaConvocatorias = servicioModalidad.obtenerConvocatoriasxPadre(cp);
		String documento = this.personaActual.getId().getDocumento();
		String tipoDocumento = this.personaActual.getId().getTipoDocumento();

		convocatoriaItem = new SelectItem[listaConvocatorias.size()];

		try {
			for (int i = 0; i < listaConvocatorias.size(); i++) {
				Convocatoria con = (Convocatoria) listaConvocatorias.get(i);
				String nombre = con.getTitulo();
				if (con.getTitulo().length() > 290) {
					nombre = con.getTitulo().substring(0, 290) + "...";
				}
				convocatoriaItem[i] = new SelectItem(con.getId().toString(), nombre);
			}
			nombreModalidad = "";
			nombreModalidad = ((Convocatoria) listaConvocatorias.get(0)).getTitulo();
		} catch (IndexOutOfBoundsException ex) {
			FacesContext.getCurrentInstance().addMessage("messagesMod", new FacesMessage(FacesMessage.SEVERITY_WARN,
					"La convocatoria " + convocatoriaPadre + " no posee modalidades asociadas", ""));
		}
	}

	public void cambiarModalidad() {
		try {
			nombreModalidad = ((Convocatoria) (buscarModalidadxId((String) convocatoria))).getTitulo();
		} catch (NullPointerException ex) {
			FacesContext.getCurrentInstance().addMessage("messagesMod", new FacesMessage(FacesMessage.SEVERITY_WARN,
					"La convocatoria " + convocatoriaPadre + " no posee modalidades asociadas", ""));
		} catch (NumberFormatException ex) {

		}
	}

	public void asignarCoordinadorRevisionProyectoTodos() {
		for (int i = 0; i < listaProyectosCoordinador.size(); i++) {
			ProyectoCoordinador pc = (ProyectoCoordinador) listaProyectosCoordinador.get(i);
			if (pc.isCoordinadorRequisitosAsociadoAsesor()) {
				pc.setPerRevision(coordinadorNuevo);
			}
			listaProyectosCoordinador.set(i, pc);
		}
	}

	public void asignarCoordinadorEvaluacionProyectoTodos() {
		for (int i = 0; i < listaProyectosCoordinador.size(); i++) {
			ProyectoCoordinador pc = (ProyectoCoordinador) listaProyectosCoordinador.get(i);
			if (pc.isCoordinadorEvaluacionAsociadoAsesor()) {
				pc.setPerEvaluacion(coordinadorNuevo);
			}
			listaProyectosCoordinador.set(i, pc);
		}
	}

	public void asignarCoordinadorProyectoTodos() {

		for (int i = 0; i < listaProyectosCoordinador.size(); i++) {
			ProyectoCoordinador pc = (ProyectoCoordinador) listaProyectosCoordinador.get(i);
			if (pc.isCoordinadorSeguimientoAsociadoAsesor()) {
				pc.setPerId(coordinadorNuevo);
			}
			listaProyectosCoordinador.set(i, pc);
		}
	}

	public String consultaAsignacion() {
		return "consultaAsignacionProyectos";
	}

	public void cambiarForm() {
		String tipoB = (String) tipoBusqueda;
		if (tipoB.equals("Convocatoria")) {
			panelRender[1] = true;
			panelRender[2] = false;
			mostrarTabla = false;
			mensajeAsignacion = "";
		}
		if (tipoB.equals("Individual")) {
			panelRender[2] = true;
			panelRender[1] = false;
			mostrarTabla = false;
			mensajeAsignacion = "";
		}
	}

	public void habilitarBuscarFacultad() {
		mensajeAsignacion =  "SELECT SEDE AS SEDE, COUNT(*) AS CANTIDAD FROM "
				+ "(SELECT S.SED_NOMBRE AS SEDE FROM HER_MOVILIDAD_DOCENTES_EVENTOS M, HER_SEDE S, HER_INVESTIGADOR_INTERNO II, "
				+ "HER_DEPENDENCIA D, HER_CONVOCATORIA_PADRE cp, HER_CONVOCATORIA con WHERE m.CON_ID = cON.CON_ID and cON.CNP_ID = cp.CNP_ID and cp.CNP_ID = 227 and M.MOV_ID_PER = ii.INV_ID AND II.TDO_ID = M.MOV_TDO_ID_PER AND m.MOV_ACEPT = 'SI' "
				+ "AND M.MOV_ID_PER NOT IN ("+DOCUMENTOS_PRUEBAS_DESARROLLO+") AND II.DPN_ID = D.DPN_ID AND D.SED_ID = S.SED_ID UNION ALL "
				+ "SELECT S.SED_NOMBRE AS SEDE FROM HER_MOVILIDAD_DOCENTES_ART M, HER_SEDE S, HER_INVESTIGADOR_INTERNO II, "
				+ "HER_DEPENDENCIA D, HER_CONVOCATORIA_PADRE cp, HER_CONVOCATORIA con WHERE m.CON_ID = cON.CON_ID and cON.CNP_ID = cp.CNP_ID and cp.CNP_ID = 227 and M.MOV_ID_PER = ii.INV_ID AND II.TDO_ID = M.MOV_TDO_ID_PER AND m.MOV_AVAL_SEDE = 'SI' "
				+ "AND M.MOV_ID_PER NOT IN ("+DOCUMENTOS_PRUEBAS_DESARROLLO+") AND II.DPN_ID = D.DPN_ID AND D.SED_ID = S.SED_ID) GROUP BY SEDE ORDER by CANTIDAD DESC";
		String tipoB = (String) buscarFac;
		if (tipoB.equals("Si")) {
			panelRender[3] = true;
			cargarFacultad();
			panelRender[4] = false;

		}
		if (tipoB.equals("No")) {
			panelRender[3] = false;
			panelRender[4] = true;
		}
	}

	public String getConvocatoria() {
		return convocatoria;
	}

	public void setConvocatoria(String convocatoria) {
		this.convocatoria = convocatoria;
	}

	public SelectItem[] getConvocatoriaItem() {
		return convocatoriaItem;
	}

	public void setConvocatoriaItem(SelectItem[] convocatoriaItem) {
		this.convocatoriaItem = convocatoriaItem;
	}

	public SelectItem[] getCoordinadoresItem() {
		return coordinadoresItem;
	}

	public void setCoordinadoresItem(SelectItem[] coordinadoresItem) {
		this.coordinadoresItem = coordinadoresItem;
	}

	public String getNombreModalidad() {
		return nombreModalidad;
	}

	public void setNombreModalidad(String nombreModalidad) {
		this.nombreModalidad = nombreModalidad;
	}

	public Persona getPersonaSeguimiento() {
		return personaSeguimiento;
	}

	public void setPersonaSeguimiento(Persona personaSeguimiento) {
		this.personaSeguimiento = personaSeguimiento;
	}

	public List getListaProyectosCoordinador() {
		return listaProyectosCoordinador;
	}

	public void setListaProyectosCoordinador(List listaProyectosCoordinador) {
		this.listaProyectosCoordinador = listaProyectosCoordinador;
	}

	public String getConvocatoriaPadre() {
		return convocatoriaPadre;
	}

	public void setConvocatoriaPadre(String convocatoriaPadre) {
		this.convocatoriaPadre = convocatoriaPadre;
	}

	public SelectItem[] getConvocatoriaPadreItem() {
		return convocatoriaPadreItem;
	}

	public void setConvocatoriaPadreItem(SelectItem[] convocatoriaPadreItem) {
		this.convocatoriaPadreItem = convocatoriaPadreItem;
	}

	public List getListaConvocatoriasPadre() {
		return listaConvocatoriasPadre;
	}

	public void setListaConvocatoriasPadre(List listaConvocatoriasPadre) {
		this.listaConvocatoriasPadre = listaConvocatoriasPadre;
	}

	public SelectItem[] getFacultadItem() {
		return facultadItem;
	}

	public void setFacultadItem(SelectItem[] facultadItem) {
		this.facultadItem = facultadItem;
	}

	public List getListaProyectosCoordinadorAux() {
		return listaProyectosCoordinadorAux;
	}

	public void setListaProyectosCoordinadorAux(List listaProyectosCoordinadorAux) {
		this.listaProyectosCoordinadorAux = listaProyectosCoordinadorAux;
	}

	public String getFacultad() {
		return facultad;
	}

	public void setFacultad(String facultad) {
		this.facultad = facultad;
	}

	public SelectItem[] getCoordinadorNuevoItem() {
		return coordinadorNuevoItem;
	}

	public void setCoordinadorNuevoItem(SelectItem[] coordinadorNuevoItem) {
		this.coordinadorNuevoItem = coordinadorNuevoItem;
	}

	public String getCoordinadorNuevo() {
		return coordinadorNuevo;
	}

	public void setCoordinadorNuevo(String coordinadorNuevo) {
		this.coordinadorNuevo = coordinadorNuevo;
	}
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTotalAsignados() {
		return totalAsignados;
	}

	public void setTotalAsignados(int totalAsignados) {
		this.totalAsignados = totalAsignados;
	}

	public int getTotalSinAsignar() {
		return totalSinAsignar;
	}

	public void setTotalSinAsignar(int totalSinAsignar) {
		this.totalSinAsignar = totalSinAsignar;
	}

	public SelectItem[] getSedeItem() {
		return sedeItem;
	}

	public void setSedeItem(SelectItem[] sedeItem) {
		this.sedeItem = sedeItem;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	public String getEstadoPadre() {
		return estadoPadre;
	}

	public void setEstadoPadre(String estadoPadre) {
		this.estadoPadre = estadoPadre;
	}

	public String getNombreConvocatoria() {
		return nombreConvocatoria;
	}

	public void setNombreConvocatoria(String nombreConvocatoria) {
		this.nombreConvocatoria = nombreConvocatoria;
	}

	public void setTipoBusqueda(String tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}

	public String getTipoBusqueda() {
		return tipoBusqueda;
	}

	public void setCodigoProyecto(String codigoProyecto) {
		this.codigoProyecto = codigoProyecto;
	}

	public String getCodigoProyecto() {
		return codigoProyecto;
	}

	public void setBuscarFac(String buscarFac) {
		this.buscarFac = buscarFac;
	}

	public String getBuscarFac() {
		return buscarFac;
	}

	public void setHabBusFac(boolean habBusFac) {
		this.habBusFac = habBusFac;
	}

	public boolean isHabBusFac() {
		return habBusFac;
	}

	public void setPerIdSeguim(String perIdSeguim) {
		this.perIdSeguim = perIdSeguim;
	}

	public String getPerIdSeguim() {
		return perIdSeguim;
	}

	public void setPerNombreSeguim(String perNombreSeguim) {
		this.perNombreSeguim = perNombreSeguim;
	}

	public String getPerNombreSeguim() {
		return perNombreSeguim;
	}

	public void setCoordinadoresItemRev(SelectItem[] coordinadoresItemRev) {
		this.coordinadoresItemRev = coordinadoresItemRev;
	}

	public SelectItem[] getCoordinadoresItemRev() {
		return coordinadoresItemRev;
	}

	public void setCoordinadoresItemEv(SelectItem[] coordinadoresItemEv) {
		this.coordinadoresItemEv = coordinadoresItemEv;
	}

	public SelectItem[] getCoordinadoresItemEv() {
		return coordinadoresItemEv;
	}

	public void setListaProyectosCoordinadorRev(List<ProyectoCoordinador> listaProyectosCoordinadorRev) {
		this.listaProyectosCoordinadorRev = listaProyectosCoordinadorRev;
	}

	public List<ProyectoCoordinador> getListaProyectosCoordinadorRev() {
		return listaProyectosCoordinadorRev;
	}

	public void setListaProyectosCoordinadorEv(List<ProyectoCoordinador> listaProyectosCoordinadorEv) {
		this.listaProyectosCoordinadorEv = listaProyectosCoordinadorEv;
	}

	public List<ProyectoCoordinador> getListaProyectosCoordinadorEv() {
		return listaProyectosCoordinadorEv;
	}

	public void setListaProyectosCoordinadorAux2(List listaProyectosCoordinadorAux2) {
		this.listaProyectosCoordinadorAux2 = listaProyectosCoordinadorAux2;
	}

	public List getListaProyectosCoordinadorAux2() {
		return listaProyectosCoordinadorAux2;
	}

	public void setPerIdRevision(String perIdRevision) {
		this.perIdRevision = perIdRevision;
	}

	public String getPerIdRevision() {
		return perIdRevision;
	}

	public void setPerIdEvaluacion(String perIdEvaluacion) {
		this.perIdEvaluacion = perIdEvaluacion;
	}

	public String getPerIdEvaluacion() {
		return perIdEvaluacion;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public String getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc2(String tipoDoc2) {
		this.tipoDoc2 = tipoDoc2;
	}

	public String getTipoDoc2() {
		return tipoDoc2;
	}

	public void setTipoDoc3(String tipoDoc3) {
		this.tipoDoc3 = tipoDoc3;
	}

	public String getTipoDoc3() {
		return tipoDoc3;
	}

	public SelectItem[] getEstadoItem() {
		return estadoItem;
	}

	public void setEstadoItem(SelectItem[] estadoItem) {
		this.estadoItem = estadoItem;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getProyectoId() {
		return proyectoId;
	}

	public void setProyectoId(String proyectoId) {
		this.proyectoId = proyectoId;
	}

	public String getMensajeAsignacion() {
		return mensajeAsignacion;
	}

	public void setMensajeAsignacion(String mensajeAsignacion) {
		this.mensajeAsignacion = mensajeAsignacion;
	}

	public boolean isMostrarTabla() {
		return mostrarTabla;
	}

	public void setMostrarTabla(boolean mostrarTabla) {
		this.mostrarTabla = mostrarTabla;
	}


	public List getListaProyectosCoordinadorAux3() {
		return listaProyectosCoordinadorAux3;
	}

	public void setListaProyectosCoordinadorAux3(List listaProyectosCoordinadorAux3) {
		this.listaProyectosCoordinadorAux3 = listaProyectosCoordinadorAux3;
	}

	public String getPerNombreRev() {
		return perNombreRev;
	}

	public void setPerNombreRev(String perNombreRev) {
		this.perNombreRev = perNombreRev;
	}

	public Persona getPersonaRevision() {
		return personaRevision;
	}

	public void setPersonaRevision(Persona personaRevision) {
		this.personaRevision = personaRevision;
	}

	public String getPerNombreEval() {
		return perNombreEval;
	}

	public void setPerNombreEval(String perNombreEval) {
		this.perNombreEval = perNombreEval;
	}

	public Persona getPersonaEvaluac() {
		return personaEvaluac;
	}

	public void setPersonaEvaluac(Persona personaEvaluac) {
		this.personaEvaluac = personaEvaluac;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String[] getErrores() {
		return errores;
	}

	public void setErrores(String[] errores) {
		this.errores = errores;
	}

	public boolean[] getPanelRender() {
		return panelRender;
	}

	public void setPanelRender(boolean[] panelRender) {
		this.panelRender = panelRender;
	}

	public boolean[] getPanelRenderError() {
		return panelRenderError;
	}

	public void setPanelRenderError(boolean[] panelRenderError) {
		this.panelRenderError = panelRenderError;
	}

	public boolean isEsModLegalizacion() {
		return esModLegalizacion;
	}

	public void setEsModLegalizacion(boolean esModLegalizacion) {
		this.esModLegalizacion = esModLegalizacion;
	}

}
