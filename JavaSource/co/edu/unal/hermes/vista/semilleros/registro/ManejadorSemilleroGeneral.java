package co.edu.unal.hermes.vista.semilleros.registro;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.HistoricoCambioIntegrantes;
import co.edu.unal.hermes.modelo.SemilleroIntegrante;
import co.edu.unal.hermes.modelo.SemilleroIntegranteTipo;
import co.edu.unal.hermes.modelo.SemilleroLaboratorio;
import co.edu.unal.hermes.modelo.SemilleroObjetivo;
import co.edu.unal.hermes.modelo.SemilleroSede;
import co.edu.unal.hermes.modelo.SemilleroSolicitud;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.Semillero;
import co.edu.unal.hermes.modelo.SemilleroEstado;
import co.edu.unal.hermes.modelo.SemilleroFacultad;
import co.edu.unal.hermes.modelo.SemilleroGrupo;
import co.edu.unal.hermes.modelo.SemilleroHistoricoCambios;
import co.edu.unal.hermes.modelo.SemilleroHistoricoEstado;
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;

public class ManejadorSemilleroGeneral extends ManejadorSemilleroRegistro {

	private static final long serialVersionUID = 1L;
	private Semillero semilleroActual;
	private SelectItem[] sedeItem;
	private String sedeSeleccionada;
	private Sede sedeEliminar;
	private SelectItem[] facultadItem;
	private String facultadSeleccionada;
	private Dependencia facultadEliminar;
	private String sedeFiltroGrupo;
	private String grupoSeleccionado;
	private SelectItem[] gruposItem;
	private Grupo grupoEliminar;
	private String objetivoEspecifico;
	private String objetivoEliminar;
	private String sedeFiltroLabs;
	private String labSeleccionado;
	private SelectItem[] labsItem;
	private Laboratorio labEliminar;
	private ArrayList<Sede> sedes;
	private ArrayList<Dependencia> facultades;
	private ArrayList<String> objetivosEspecificos;
	private ArrayList<Grupo> grupos;
	private ArrayList<Laboratorio> laboratorios;
	private String idModal;
	private UploadedFile imagenSeleccionada;
	public StreamedContent imagen;
	private boolean solicitudAprobadaNombre;
	private boolean solicitudAprobadaPresentacion;
	private boolean solicitudAprobadaObjGeneral;
	private boolean solicitudAprobadaObjEspecifico;
	private boolean solicitudAprobadaJustificacion;
	private Integer idSolicitudContenido;

	public ManejadorSemilleroGeneral() {
		super();
		init();
	}

	private void init() {
		setSedes(new ArrayList<Sede>());
		setFacultades(new ArrayList<Dependencia>());
		setGrupos(new ArrayList<Grupo>());
		setObjetivosEspecificos(new ArrayList<String>());
		setLaboratorios(new ArrayList<Laboratorio>());
		if (sesion.getAttribute("semillero") != null) {
			setSemilleroActual(servicioGeneral
					.obtenerObjetos(Semillero.class,
							"from Semillero s where s.id=" + ((Integer) sesion.getAttribute("semillero")).toString())
					.get(0));
		}
		if (getSemilleroActual() == null) {
			setSemilleroActual(new Semillero());
			SemilleroIntegrante is = new SemilleroIntegrante();
			is.setSemillero(getSemilleroActual());
			is.setIntegrante(servicioPersona.obtenerInvestigador(personaActual.getId()));
			is.setTipo(new SemilleroIntegranteTipo());
			is.getTipo().setId("DD");
			is.setActividades("Líder de Semillero.");
			getSemilleroActual().getIntegrantes().add(is);
		} else {
			for (SemilleroSede s : getSemilleroActual().getSedes()) {
				if (s.getFechaBorrado() == null) {
					getSedes().add(s.getSede());
				}
			}
			for (SemilleroFacultad f : getSemilleroActual().getFacultades()) {
				if (f.getFechaBorrado() == null) {
					getFacultades().add(f.getFacultad());
				}
			}
			for (SemilleroObjetivo oo : getSemilleroActual().getObjetivosEspecificos()) {
				getObjetivosEspecificos().add(oo.getObjetivo());
			}
			for (SemilleroGrupo g : getSemilleroActual().getGrupos()) {
				if (g.getFechaBorrado() == null) {
					getGrupos().add(g.getGrupo());
				}
			}
			for (SemilleroLaboratorio l : getSemilleroActual().getLaboratorios()) {
				if (l.getFechaBorrado() == null) {
					getLaboratorios().add(l.getLaboratorio());
				}
			}
		}
		cargarSedes();
		cargarFacultades();
		actualizarGrupos();
		actualizarLabs();
		cargarImagenSemillero();
		solicitudAprobadaNombre = false;
		solicitudAprobadaPresentacion = false;
		solicitudAprobadaObjGeneral = false;
		solicitudAprobadaObjEspecifico = false;
		solicitudAprobadaJustificacion = false;
		for (SemilleroSolicitud solicitud : semilleroActual.getSolicitudes()) {
			if (solicitud.getRespuestaCoordinador() != null && solicitud.getRespuestaCoordinador().getId().equals(7)
					&& solicitud.getDetalle() != null && solicitud.getTipo().getId().equals(3)) {
				idSolicitudContenido = solicitud.getId();
				if (solicitud.getDetalle().toLowerCase().contains("nombre")) {
					solicitudAprobadaNombre = true;
				}
				if (solicitud.getDetalle().toLowerCase().contains("presentación")) {
					solicitudAprobadaPresentacion = true;
				}
				if (solicitud.getDetalle().toLowerCase().contains("objetivo general")) {
					solicitudAprobadaObjGeneral = true;
				}
				if (solicitud.getDetalle().toLowerCase().contains("objetivos específicos")) {
					solicitudAprobadaObjEspecifico = true;
				}
				if (solicitud.getDetalle().toLowerCase().contains("justificación de creación")) {
					solicitudAprobadaJustificacion = true;
				}
			}
		}
	}

	private void cargarImagenSemillero() {
		File actual = new File(
				RUTA_ARCHIVOS + File.separator + "HER_SEMILLERO" + File.separator + semilleroActual.getId());
		try {
			imagen = new DefaultStreamedContent(
					new ByteArrayInputStream(org.apache.commons.io.FileUtils.readFileToByteArray(actual)), "image/jpg");

		} catch (Exception i) {
			i.printStackTrace();
		}
	}

	private void cargarSedes() {
		List<Dependencia> listaFacultad = servicioGeneral.obtenerObjetos(Dependencia.class,
				"from Dependencia d where d.sede.id not in ('" + Sede.NIVEL_NACIONAL
						+ "') and d.estado='A' and d.esFacultad='Y' order by d.id");
		facultadItem = new SelectItem[listaFacultad.size()];
		for (int i = 0; i < listaFacultad.size(); i++) {
			Dependencia facultad = listaFacultad.get(i);
			facultadItem[i] = new SelectItem(facultad.getId(), facultad.getNombre());
		}
	}

	private void cargarFacultades() {
		List<Sede> listaSede = servicioGeneral.obtenerObjetos(Sede.class,
				"from Sede s where s.id not in ('" + Sede.NIVEL_NACIONAL + "') order by s.id");
		sedeItem = new SelectItem[listaSede.size()];
		for (int i = 0; i < listaSede.size(); i++) {
			Sede sede = listaSede.get(i);
			sedeItem[i] = new SelectItem(sede.getId(), sede.getNombre());
		}
	}

	public void actualizarGrupos() {
		List<Grupo> listaGrupos = servicioGeneral.obtenerObjetos(Grupo.class,
				"from Grupo s where s.sede.id='" + getSedeFiltroGrupo() + "' and s.estadoGrupo.id='A' order by s.id");
		gruposItem = new SelectItem[listaGrupos.size()];
		for (int i = 0; i < listaGrupos.size(); i++) {
			Grupo grupo = listaGrupos.get(i);
			gruposItem[i] = new SelectItem(grupo.getId(), grupo.getNombre());
		}
	}

	public void actualizarLabs() {
		List<Laboratorio> listaLabs = servicioGeneral.obtenerObjetos(Laboratorio.class,
				"from Laboratorio s where s.sede.id='" + getSedeFiltroLabs() + "' and s.activo = '1' order by s.id");
		labsItem = new SelectItem[listaLabs.size()];
		for (int i = 0; i < listaLabs.size(); i++) {
			Laboratorio lab = listaLabs.get(i);
			labsItem[i] = new SelectItem(lab.getId(), lab.getNombre());
		}
	}

	public void adicionarSede() {
		if (StringUtils.isNotEmpty(getSedeSeleccionada())) {
			boolean existe = false;
			for (Sede s : getSedes()) {
				if (s.getId().toString().equals(getSedeSeleccionada())) {
					existe = true;
					generarMsgModal(2, "La sede ya se encuentra vinculado.");
					break;
				}
			}
			if (!existe) {
				Sede s = servicioGeneral.obtenerObjetos(Sede.class, "from Sede s where s.id=" + getSedeSeleccionada())
						.get(0);
				SemilleroSede ss = new SemilleroSede();
				ss.setSede(s);
				ss.setSemillero(getSemilleroActual());
				ss.setFechaRegistro(new Date());
				getSemilleroActual().getSedes().add(ss);
				getSedes().add(s);
			}
		} else {
			generarMsgModal(2, "Por favor, indicar una sede.");
		}
	}

	public void adicionarFacultad() {
		if (StringUtils.isNotEmpty(getFacultadSeleccionada())) {
			boolean existe = false;
			for (Dependencia d : getFacultades()) {
				if (d.getId().toString().equals(getFacultadSeleccionada())) {
					existe = true;
					generarMsgModal(2, "La facultad ya se encuentra vinculada.");
					break;
				}
			}
			if (!existe) {
				Dependencia f = servicioGeneral.obtenerObjetos(Dependencia.class,
						"from Dependencia s where s.id='" + getFacultadSeleccionada() + "'").get(0);
				SemilleroFacultad sf = new SemilleroFacultad();
				sf.setFacultad(f);
				sf.setSemillero(getSemilleroActual());
				sf.setFechaRegistro(new Date());
				getSemilleroActual().getFacultades().add(sf);
				getFacultades().add(f);
			}
		} else {
			generarMsgModal(2, "Por favor, indicar una facultad.");
		}
	}

	public void adicionarGrupo() {
		if (StringUtils.isNotEmpty(getGrupoSeleccionado())) {
			boolean existe = false;
			for (Grupo s : getGrupos()) {
				if (s.getId().toString().equals(getGrupoSeleccionado())) {
					existe = true;
					generarMsgModal(2, "El grupo ya se encuentra vinculado.");
					break;
				}
			}
			if (!existe) {
				Grupo g = servicioGeneral
						.obtenerObjetos(Grupo.class, "from Grupo s where s.id=" + getGrupoSeleccionado()).get(0);
				SemilleroGrupo sg = new SemilleroGrupo();
				sg.setGrupo(g);
				sg.setSemillero(getSemilleroActual());
				sg.setFechaRegistro(new Date());
				sg.setAgregoSemillero("S");
				getSemilleroActual().getGrupos().add(sg);
				getGrupos().add(g);
			}
		} else {
			generarMsgModal(2, "Por favor, indicar un grupo.");
		}
	}

	public void adicionarLab() {
		if (StringUtils.isNotEmpty(getLabSeleccionado())) {
			boolean existe = false;
			for (Laboratorio s : getLaboratorios()) {
				if (s.getId().toString().equals(getLabSeleccionado())) {
					existe = true;
					generarMsgModal(2, "El Laboratorio ya se encuentra vinculado.");
					break;
				}
			}
			if (!existe) {
				Laboratorio l = servicioGeneral
						.obtenerObjetos(Laboratorio.class, "from Laboratorio s where s.id=" + getLabSeleccionado())
						.get(0);
				SemilleroLaboratorio sl = new SemilleroLaboratorio();
				sl.setLaboratorio(l);
				sl.setSemillero(getSemilleroActual());
				sl.setFechaRegistro(new Date());
				getSemilleroActual().getLaboratorios().add(sl);
				getLaboratorios().add(l);
			}
		} else {
			generarMsgModal(2, "Por favor, indicar un laboratorio.");
		}
	}

	public void adicionarObjetivo() {
		if (StringUtils.isNotEmpty(getObjetivoEspecifico())) {
			boolean existe = false;
			for (String d : getObjetivosEspecificos()) {
				if (d.equals(getObjetivoEspecifico())) {
					existe = true;
					generarMsgModal(2, "El objetivo ya se encuentra vinculado.");
					break;
				}
			}
			if (!existe) {
				SemilleroObjetivo so = new SemilleroObjetivo();
				so.setSemillero(getSemilleroActual());
				so.setObjetivo(getObjetivoEspecifico());
				getSemilleroActual().getObjetivosEspecificos().add(so);
				getObjetivosEspecificos().add(getObjetivoEspecifico());
				setObjetivoEspecifico(null);
			}
		} else {
			generarMsgModal(2, "Por favor, indique el objetivo a añadir.");
		}
	}

	public void eliminarSede() {
		getSedes().remove(sedeEliminar);
		for (SemilleroSede ss : getSemilleroActual().getSedes()) {
			if (ss.getSede().getId().equals(sedeEliminar.getId())) {
				ss.setFechaBorrado(new Date());
				break;
			}
		}
	}

	public void eliminarFacultad() {
		getFacultades().remove(facultadEliminar);
		for (SemilleroFacultad sf : getSemilleroActual().getFacultades()) {
			if (sf.getFacultad().getId().equals(facultadEliminar.getId())) {
				sf.setFechaBorrado(new Date());
				break;
			}
		}
	}

	public void eliminarGrupo() {
		getGrupos().remove(grupoEliminar);
		for (SemilleroGrupo sg : getSemilleroActual().getGrupos()) {
			if (sg.getGrupo().getId().equals(grupoEliminar.getId())) {
				sg.setFechaBorrado(new Date());
				sg.setResponsable(personaActual);
				break;
			}
		}
	}

	public void eliminarObjetivo() {
		getObjetivosEspecificos().remove(objetivoEliminar);
		for (SemilleroObjetivo obj : getSemilleroActual().getObjetivosEspecificos()) {
			if (obj.getObjetivo().equals(objetivoEliminar) && getSemilleroActual().getId() != null) {
				servicioGeneral.eliminarObjeto(obj);
			}
			getSemilleroActual().getObjetivosEspecificos().remove(obj);
			break;
		}
	}

	public void eliminarLab() {
		getLaboratorios().remove(labEliminar);
		for (SemilleroLaboratorio sl : getSemilleroActual().getLaboratorios()) {
			if (sl.getLaboratorio().getId().equals(labEliminar.getId())) {
				sl.setFechaBorrado(new Date());
				break;
			}
		}
	}

	@Override
	String guardar(boolean parcial) {
		boolean nuevo = false;
		if (esCadenaVacia(getSemilleroActual().getEmail())) {
			generarMsg(2, "Debe indicar el e-mail del semillero.");
			return "";
		}
		if (!esCadenaVacia(getSemilleroActual().getEmail()) && !validarEmail(getSemilleroActual().getEmail())) {
			generarMsg(2, "El e-mail del semillero no es válido.");
			return "";
		}
		if (getSemilleroActual().getId() != null) {
			List<Semillero> listSemOriginal = servicioGeneral
					.obtenerObjetoXID(Semillero.class, getSemilleroActual().getId().toString());
			
			if(listSemOriginal!=null && listSemOriginal.size()>0) {
				
				Semillero semOriginal = listSemOriginal.get(0);
			
				if (semOriginal.getEmail() != null && !semOriginal.getEmail().equals(getSemilleroActual().getEmail())) {
					SemilleroHistoricoCambios shc = new SemilleroHistoricoCambios();
					shc.setSemillero(getSemilleroActual());
					shc.setFecha(new Date());
					shc.setDescripcion("Correo Actual: " + getSemilleroActual().getEmail());
					servicioGeneral.guardarObjeto(shc);
					getSemilleroActual().getHistoricoCambios().add(shc);
				}
			}
		}
		if (idSolicitudContenido != null) {
			for (SemilleroSolicitud s : semilleroActual.getSolicitudes()) {
				if (s.getId().equals(idSolicitudContenido)) {
					if (solicitudAprobadaNombre) {
						s.setDetalle(s.getDetalle().replace("Nombre", ""));
					}
					if (solicitudAprobadaPresentacion) {
						s.setDetalle(s.getDetalle().replace("Presentación", ""));
					}
					if (solicitudAprobadaObjGeneral) {
						s.setDetalle(s.getDetalle().replace("Objetivo General", ""));
					}
					if (solicitudAprobadaObjEspecifico) {
						s.setDetalle(s.getDetalle().replace("Objetivos Específicos", ""));
					}
					if (solicitudAprobadaJustificacion) {
						s.setDetalle(s.getDetalle().replace("Justificación de Creación", ""));
					}
					s.setDetalle(s.getDetalle().replace("--", "-"));
					if (s.getDetalle().endsWith("-")) {
						s.setDetalle(s.getDetalle().substring(0, s.getDetalle().length() - 1));
					}
					solicitudAprobadaNombre = false;
					solicitudAprobadaPresentacion = false;
					solicitudAprobadaObjGeneral = false;
					solicitudAprobadaObjEspecifico = false;
					solicitudAprobadaJustificacion = false;
				}
			}
		}
		if (!parcial) {
			if (validarForm()) {
				if (getSemilleroActual().getFase().equals(0)) {
					getSemilleroActual().setFase(1);
				}
				servicioGeneral.guardarObjeto(getSemilleroActual());
				if (getSemilleroActual().getHistoricoEstados().isEmpty()) {
					SemilleroHistoricoEstado she = new SemilleroHistoricoEstado();
					she.setSemillero(getSemilleroActual());
					she.setEstado((SemilleroEstado) servicioGeneral.obtenerObjeto(new SemilleroEstado(), 1));
					she.setMotivo("Registro Inicial de Semillero.");
					she.setFecha(Calendar.getInstance().getTime());
					she.setResponsable(getSemilleroActual().getLider());
					getSemilleroActual().getHistoricoEstados().add(she);
					servicioGeneral.guardarObjeto(she);
				}
				for (SemilleroIntegrante si : getSemilleroActual().getIntegrantes()) {
					nuevo = false;
					if (si.getId() == null) {
						nuevo = true;
						si.setSemillero(getSemilleroActual());
						servicioGeneral.guardarObjeto(si);
					}
					if (nuevo) {
						hci = new HistoricoCambioIntegrantes();
						hci.setSemillero(getSemilleroActual());
						hci.setFechaIngreso(new Date());
						hci.setIntegrante(getSemilleroActual().getLider());
						hci.setTipoInvestigadorSemillero((SemilleroIntegranteTipo) servicioGeneral
								.obtenerObjeto(new SemilleroIntegranteTipo(), "DD"));
						hci.setTipoVinculacion(getSemilleroActual().getLider().getTipoVinculacion());
						hci.setTipoDedicacion(getSemilleroActual().getLider().getTipoDedicacion());
						hci.setTipoFormacion(getSemilleroActual().getLider().getTipoFormacion());
						hci.setDependencia(getSemilleroActual().getLider().getDependencia().getFacultad());
						hci.setSede(getSemilleroActual().getLider().getDependencia().getFacultad().getSede());
						servicioGeneral.guardarObjeto(hci);
					}
				}
				return irIntegrantes(getSemilleroActual().getId());
			} else {
				return "";
			}
		} else {
			if (esCadenaVacia(getSemilleroActual().getNombre())) {
				generarMsg(2, "Debe indicar el nombre del semillero.");
				return "";
			} else {
				getSemilleroActual().setFase(0);
				servicioGeneral.guardarObjeto(getSemilleroActual());
				if (getSemilleroActual().getHistoricoEstados().isEmpty()) {
					SemilleroHistoricoEstado she = new SemilleroHistoricoEstado();
					she.setSemillero(getSemilleroActual());
					she.setEstado((SemilleroEstado) servicioGeneral.obtenerObjeto(new SemilleroEstado(), 1));
					she.setMotivo("Registro Inicial de Semillero.");
					she.setFecha(Calendar.getInstance().getTime());
					she.setResponsable(getSemilleroActual().getLider());
					getSemilleroActual().getHistoricoEstados().add(she);
					servicioGeneral.guardarObjeto(she);
				}
				for (SemilleroIntegrante si : getSemilleroActual().getIntegrantes()) {
					nuevo = false;
					if (si.getId() == null) {
						nuevo = true;
						si.setSemillero(getSemilleroActual());
						servicioGeneral.guardarObjeto(si);
					}
					if (nuevo) {
						hci = new HistoricoCambioIntegrantes();
						hci.setSemillero(getSemilleroActual());
						hci.setFechaIngreso(new Date());
						hci.setIntegrante(getSemilleroActual().getLider());
						hci.setTipoInvestigadorSemillero((SemilleroIntegranteTipo) servicioGeneral
								.obtenerObjeto(new SemilleroIntegranteTipo(), "DD"));
						hci.setTipoVinculacion(getSemilleroActual().getLider().getTipoVinculacion());
						hci.setTipoDedicacion(getSemilleroActual().getLider().getTipoDedicacion());
						hci.setTipoFormacion(getSemilleroActual().getLider().getTipoFormacion());
						hci.setDependencia(getSemilleroActual().getLider().getDependencia().getFacultad());
						hci.setSede(getSemilleroActual().getLider().getDependencia().getFacultad().getSede());
						servicioGeneral.guardarObjeto(hci);
					}
				}
				generarMsg(1, "Semillero registrado correctamente con el ID " + getSemilleroActual().getId());
				return "";
			}
		}
	}

	@Override
	boolean validarForm() {
		isOK = true;
		if (esCadenaVacia(getSemilleroActual().getNombre())) {
			isOK = false;
			generarMsg(2, "Debe indicar el nombre del semillero.");
		}
		if (esCadenaVacia(getSemilleroActual().getPresentacion())) {
			isOK = false;
			generarMsg(2, "Debe indicar la presentación del semillero.");
		}
		if (getSemilleroActual().getFechaCreacion() == null) {
			isOK = false;
			generarMsg(2, "Debe indicar la fecha de creación del semillero.");
		}
		if (esCadenaVacia(getSemilleroActual().getEmail())) {
			isOK = false;
			generarMsg(2, "Debe indicar el e-mail del semillero.");
		}
		if (!esCadenaVacia(getSemilleroActual().getEmail()) && !validarEmail(getSemilleroActual().getEmail())) {
			isOK = false;
			generarMsg(2, "El e-mail del semillero no es válido.");
		}
		if (getSemilleroActual().getInterfacultades() && getFacultades().isEmpty()) {
			isOK = false;
			generarMsg(2, "Si el semillero es interfacultades, debe indicar las facultades vinculadas al mismo.");
		}
		if (getSemilleroActual().getIntersedes() && getSedes().isEmpty()) {
			isOK = false;
			generarMsg(2, "Si el semillero es intersedes, debe indicar las sedes vinculadas al mismo.");
		}
		if (!getGrupos().isEmpty() && esCadenaVacia(getSemilleroActual().getPertinencia())) {
			isOK = false;
			generarMsg(2, "Si el semillero tiene grupos vinculados, debe indicar la pertinencia del mismo.");
		}
		if (esCadenaVacia(getSemilleroActual().getObjetivoGeneral())) {
			isOK = false;
			generarMsg(2, "Debe indicar el objetivo general del semillero.");
		}
		if (getObjetivosEspecificos().isEmpty()) {
			isOK = false;
			generarMsg(2, "Debe indicar al menos un objetivo específico del semillero.");
		}
		if (esCadenaVacia(getSemilleroActual().getJustificacion())) {
			isOK = false;
			generarMsg(2, "Debe indicar la justificación del semillero.");
		}
		if (esCadenaVacia(getSemilleroActual().getEnfoque())) {
			isOK = false;
			generarMsg(2, "Debe indicar el enfoque del semillero.");
		}
		return isOK;
	}

	public void cargarImagen(FileUploadEvent event) {
		UploadedFile imagenSeleccionada = event.getFile();
		if (imagenSeleccionada != null && semilleroActual!=null && semilleroActual.getId()!=null) {
			cargarArchivoDisco(imagenSeleccionada, "HER_SEMILLERO", semilleroActual.getId().toString());
			cargarImagenSemillero();
		} else {
			generarMsg(2, "Por favor seleccione un archivo");
		}
	}

	public SelectItem[] getSedeItem() {
		return sedeItem;
	}

	public void setSedeItem(SelectItem[] sedeItem) {
		this.sedeItem = sedeItem;
	}

	public String getSedeSeleccionada() {
		return sedeSeleccionada;
	}

	public void setSedeSeleccionada(String sedeSeleccionada) {
		this.sedeSeleccionada = sedeSeleccionada;
	}

	public Sede getSedeEliminar() {
		return sedeEliminar;
	}

	public void setSedeEliminar(Sede sedeEliminar) {
		this.sedeEliminar = sedeEliminar;
	}

	public SelectItem[] getFacultadItem() {
		return facultadItem;
	}

	public void setFacultadItem(SelectItem[] facultadItem) {
		this.facultadItem = facultadItem;
	}

	public String getFacultadSeleccionada() {
		return facultadSeleccionada;
	}

	public void setFacultadSeleccionada(String facultadSeleccionada) {
		this.facultadSeleccionada = facultadSeleccionada;
	}

	public Dependencia getFacultadEliminar() {
		return facultadEliminar;
	}

	public void setFacultadEliminar(Dependencia facultadEliminar) {
		this.facultadEliminar = facultadEliminar;
	}

	public String getSedeFiltroGrupo() {
		return sedeFiltroGrupo;
	}

	public void setSedeFiltroGrupo(String sedeFiltroGrupo) {
		this.sedeFiltroGrupo = sedeFiltroGrupo;
	}

	public String getGrupoSeleccionado() {
		return grupoSeleccionado;
	}

	public void setGrupoSeleccionado(String grupoSeleccionado) {
		this.grupoSeleccionado = grupoSeleccionado;
	}

	public SelectItem[] getGruposItem() {
		return gruposItem;
	}

	public void setGruposItem(SelectItem[] gruposItem) {
		this.gruposItem = gruposItem;
	}

	public Grupo getGrupoEliminar() {
		return grupoEliminar;
	}

	public void setGrupoEliminar(Grupo grupoEliminar) {
		this.grupoEliminar = grupoEliminar;
	}

	public String getObjetivoEspecifico() {
		return objetivoEspecifico;
	}

	public void setObjetivoEspecifico(String objetivoEspecifico) {
		this.objetivoEspecifico = objetivoEspecifico;
	}

	public String getObjetivoEliminar() {
		return objetivoEliminar;
	}

	public void setObjetivoEliminar(String objetivoEliminar) {
		this.objetivoEliminar = objetivoEliminar;
	}

	public String getSedeFiltroLabs() {
		return sedeFiltroLabs;
	}

	public void setSedeFiltroLabs(String sedeFiltroLabs) {
		this.sedeFiltroLabs = sedeFiltroLabs;
	}

	public String getLabSeleccionado() {
		return labSeleccionado;
	}

	public void setLabSeleccionado(String labSeleccionado) {
		this.labSeleccionado = labSeleccionado;
	}

	public SelectItem[] getLabsItem() {
		return labsItem;
	}

	public void setLabsItem(SelectItem[] labsItem) {
		this.labsItem = labsItem;
	}

	public Laboratorio getLabEliminar() {
		return labEliminar;
	}

	public void setLabEliminar(Laboratorio labEliminar) {
		this.labEliminar = labEliminar;
	}

	public ArrayList<Sede> getSedes() {
		return sedes;
	}

	public void setSedes(ArrayList<Sede> sedes) {
		this.sedes = sedes;
	}

	public ArrayList<Dependencia> getFacultades() {
		return facultades;
	}

	public void setFacultades(ArrayList<Dependencia> facultades) {
		this.facultades = facultades;
	}

	public ArrayList<String> getObjetivosEspecificos() {
		return objetivosEspecificos;
	}

	public void setObjetivosEspecificos(ArrayList<String> objetivosEspecificos) {
		this.objetivosEspecificos = objetivosEspecificos;
	}

	public ArrayList<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(ArrayList<Grupo> grupos) {
		this.grupos = grupos;
	}

	public ArrayList<Laboratorio> getLaboratorios() {
		return laboratorios;
	}

	public void setLaboratorios(ArrayList<Laboratorio> laboratorios) {
		this.laboratorios = laboratorios;
	}

	public String getIdModal() {
		return idModal;
	}

	public void setIdModal(String idModal) {
		this.idModal = idModal;
	}

	public Semillero getSemilleroActual() {
		return semilleroActual;
	}

	public void setSemilleroActual(Semillero semilleroActual) {
		this.semilleroActual = semilleroActual;
	}

	public UploadedFile getImagenSeleccionada() {
		return imagenSeleccionada;
	}

	public void setImagenSeleccionada(UploadedFile imagenSeleccionada) {
		this.imagenSeleccionada = imagenSeleccionada;
	}

	public StreamedContent getImagen() {
		return imagen;
	}

	public void setImagen(StreamedContent imagen) {
		this.imagen = imagen;
	}

	public boolean isSolicitudAprobadaNombre() {
		return solicitudAprobadaNombre;
	}

	public void setSolicitudAprobadaNombre(boolean solicitudAprobadaNombre) {
		this.solicitudAprobadaNombre = solicitudAprobadaNombre;
	}

	public boolean isSolicitudAprobadaPresentacion() {
		return solicitudAprobadaPresentacion;
	}

	public void setSolicitudAprobadaPresentacion(boolean solicitudAprobadaPresentacion) {
		this.solicitudAprobadaPresentacion = solicitudAprobadaPresentacion;
	}

	public boolean isSolicitudAprobadaObjGeneral() {
		return solicitudAprobadaObjGeneral;
	}

	public void setSolicitudAprobadaObjGeneral(boolean solicitudAprobadaObjGeneral) {
		this.solicitudAprobadaObjGeneral = solicitudAprobadaObjGeneral;
	}

	public boolean isSolicitudAprobadaObjEspecifico() {
		return solicitudAprobadaObjEspecifico;
	}

	public void setSolicitudAprobadaObjEspecifico(boolean solicitudAprobadaObjEspecifico) {
		this.solicitudAprobadaObjEspecifico = solicitudAprobadaObjEspecifico;
	}

	public boolean isSolicitudAprobadaJustificacion() {
		return solicitudAprobadaJustificacion;
	}

	public void setSolicitudAprobadaJustificacion(boolean solicitudAprobadaJustificacion) {
		this.solicitudAprobadaJustificacion = solicitudAprobadaJustificacion;
	}
}
