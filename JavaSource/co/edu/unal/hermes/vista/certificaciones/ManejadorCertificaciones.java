package co.edu.unal.hermes.vista.certificaciones;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.EstadoGrupo;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.MovilidadDocentesExterior;
import co.edu.unal.hermes.modelo.MovilidadEstudiantesPosgrado;
import co.edu.unal.hermes.modelo.MovilidadVisitanteExterior;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.ProyectoCompromiso;
import co.edu.unal.hermes.modelo.Semillero;
import co.edu.unal.hermes.modelo.SemilleroEstado;
import co.edu.unal.hermes.modelo.SemilleroInforme;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorCertificaciones extends ManejadorBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3046995852623378048L;

	private InvestigadorInterno ii;
	private String nombreCompletoPersona = "";
	private List<Proyecto> listaProyectosFinalizados;
	private List<Semillero> listaSemillerosActivos;
	private List<Grupo> listaGruposActivos;
	private List<Grupo> listaGruposActivosNoVinculados;
	private List<Proyecto> listaProyectosFinalizadosNoVinculadoActualmente;
	private List<Proyecto> listaProyectosFinalizadosCoinvestigador;
	private List<Proyecto> listaProyectosActivos;
	private List<ProyectoCompromiso> listaCompromisosProyectos;
	private List<SemilleroInforme> listaCompromisosSemilleros;
	private List<String[]> listaCompromisosMovilidades;
	private String opcionesCertificado;
	private boolean mostrarCertParticipacion = false;
	private boolean mostrarCertSemilleros = false;
	private boolean mostrarCertGrupos = false;
	private boolean mostrarCertPazysalvo = false;
	private Proyecto proyectoSeleccionado;
	private Semillero semilleroSeleccionado;
	private Grupo grupoSeleccionado;
	private boolean mostrarNoPazysalvo = false;
	private boolean mostrarSiPazysalvo = false;
	private boolean esVif = false;
	private boolean esCertificadoCoinvestigador = false;

	private TipoDocumento tipoDocumento;
	private SelectItem[] tipoDocumentoItem;
	private String documento;
	private boolean mostrarCertParticipacionCoinvestigador = false;
	private boolean mostrarCertParticipacionCoinvestigadorNiVin = false;
	private boolean mostrarCertParticipacionCoinvestigadorSenm = false;
	private boolean mostrarCertParticipacionCoinvestigadorGru = false;

	public ManejadorCertificaciones() {
		super();
		esVif = false;
		Persona personaCertVIF = (Persona) sesion.getAttribute("personaCertVIF");
		if (personaCertVIF == null) {
			personaActual = (Persona) sesion.getAttribute("persona");
		} else {
			personaActual = (Persona) sesion.getAttribute("personaCertVIF");
			esVif = true;
		}

		ii = servicioPersona.obtenerInvestigadorInternoCompleto(personaActual.getId());

		if (ii != null) {
			nombreCompletoPersona = ii.getNombre1() + " " + ii.getNombre2() + " " + ii.getApellido1() + " "
					+ ii.getApellido2();
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error en el tipo de vinculación del profesor", "Error en el tipo de vinculación del profesor"));
		}

		cargarTiposDocumento();

	}

	public void cargarProyectos() {
		String consultaProyectos = "select #id e.id, #nombre e.nombre from Proyecto e, InvestigadorProyecto i where e.estadoProyecto.id = 'F' and i.proyecto.id = e.id and i.investigador.id.documento = '"
				+ personaActual.getId().getDocumento() + "' and i.investigador.id.tipoDocumento = '"
				+ personaActual.getId().getTipoDocumento() + "'";
		listaProyectosFinalizados = servicioGeneral.obtenerObjetosLimitado(Proyecto.class, consultaProyectos);

		String consultaProyectosSolInvestigador = "select distinct #id e.id, #nombre e.nombre from Proyecto e, SolicitudInvestigador si, Solicitud sol where e.estadoProyecto.id = 'F' and sol.proyecto.id = e.id and si.solicitud.id = sol.id and si.investigador.id.documento = '"
				+ personaActual.getId().getDocumento() + "' and si.investigador.id.tipoDocumento = '"
				+ personaActual.getId().getTipoDocumento() + "'";
		List<Proyecto> listaProyectosFinalizadosNoVinAux = servicioGeneral.obtenerObjetosLimitado(Proyecto.class,
				consultaProyectosSolInvestigador);
		listaProyectosFinalizadosNoVinculadoActualmente = new ArrayList<Proyecto>();
		for (int i = 0; i < listaProyectosFinalizadosNoVinAux.size(); i++) {
			if (!listaProyectosFinalizados.contains(listaProyectosFinalizadosNoVinAux.get(i))) {
				listaProyectosFinalizadosNoVinculadoActualmente.add(listaProyectosFinalizadosNoVinAux.get(i));
			}
		}
	}

	public void cargarGruposActual() {
		cargarGrupos(personaActual.getId().getDocumento(), personaActual.getId().getTipoDocumento(), "0");
	}

	public void cargarGruposInvestigador() {
		cargarGrupos(this.documento, this.tipoDocumento.getId(), "1");
	}

	public void cargarSemillerosActual() {
		cargarSemilleros(personaActual.getId().getDocumento(), personaActual.getId().getTipoDocumento(), "0");
	}

	public void cargarSemillerosInvestigador() {
		cargarSemilleros(this.documento, this.tipoDocumento.getId(), "1");
	}

	public void cargarSemilleros(String documento, String tipo, String coinv) {
		String consultaSemilleros = "select e from Semillero e, SemilleroIntegrante i where e.id = i.semillero.id and i.integrante.id.documento = '"
				+ documento + "' and i.integrante.id.tipoDocumento = '" + tipo + "' AND i.estado = 'A'";
		List<Semillero> listaSemilleros = servicioGeneral.obtenerObjetos(Semillero.class, consultaSemilleros);
		listaSemillerosActivos = new ArrayList<Semillero>();
		for (int i = 0; i < listaSemilleros.size(); i++) {
			if (listaSemilleros.get(i).getEstadoActual().getId().equals(SemilleroEstado.ESTADO_ACTIVO)) {
				if (coinv.equals("1") && listaSemilleros.get(i).getLider().getId().getDocumento()
						.equals(personaActual.getId().getDocumento()) && !esVif) {
					listaSemillerosActivos.add(listaSemilleros.get(i));
				} else {
					listaSemillerosActivos.add(listaSemilleros.get(i));
				}
			}
		}
	}

	public void cargarGrupos(String documento, String tipo, String coinv) {
		String consultaGrupos = "select e from Grupo e, InvestigadorGrupo i where e.id = i.grupo.id and i.investigador.id.documento = '"
				+ documento + "' and i.investigador.id.tipoDocumento = '" + tipo + "'";
		List<Grupo> listaGrupos = servicioGeneral.obtenerObjetos(Grupo.class, consultaGrupos);
		listaGruposActivos = new ArrayList<Grupo>();
		for (int i = 0; i < listaGrupos.size(); i++) {
			if (listaGrupos.get(i).getEstadoGrupo().getId().equals(EstadoGrupo.ACTIVO)) {

				if (coinv.equals("1") && listaGrupos.get(i).getResponsable().getId().getDocumento()
						.equals(personaActual.getId().getDocumento()) && !esVif) {
					listaGruposActivos.add(listaGrupos.get(i));
				} else {
					listaGruposActivos.add(listaGrupos.get(i));
				}
			}
		}
		
		String consultaNovinculados = "select e from Grupo e, HistoricoCambioIntegrantes i where e.id = i.grupo.id and i.integrante.id.documento = '"
				+ documento + "' and i.integrante.id.tipoDocumento = '" + tipo + "' and i.fechaRetiro is not null";
		List<Grupo> listaGruposSinVinc = servicioGeneral.obtenerObjetos(Grupo.class, consultaNovinculados);
		listaGruposActivosNoVinculados = new ArrayList<Grupo>();
		for (int i = 0; i < listaGruposSinVinc.size(); i++) {
			if (listaGruposSinVinc.get(i).getEstadoGrupo().getId().equals(EstadoGrupo.ACTIVO)) {

				if (coinv.equals("1") && listaGruposSinVinc.get(i).getResponsable().getId().getDocumento()
						.equals(personaActual.getId().getDocumento()) && !esVif) {
					listaGruposActivosNoVinculados.add(listaGruposSinVinc.get(i));
				} else {
					listaGruposActivosNoVinculados.add(listaGruposSinVinc.get(i));
				}
			}
		}
		
	}

	public void generarCertificadoParticipacion() {
		boolean esInvPalProyecto = false;
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("Id", proyectoSeleccionado.getId().toString());
		r.adicionarParametro("Dep", ii.getDependencia().getSede().getId().toString());
		r.adicionarParametro("InvId", personaActual.getId().getDocumento());
		r.adicionarParametro("InvTipoId", personaActual.getId().getTipoDocumento());
		r.setFormato(ReporteBirt.FORMATO_PDF);

		Investigador inPal = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyectoSeleccionado.getId());
		if (inPal.getId().getDocumento().equals(personaActual.getId().getDocumento())
				&& inPal.getId().getTipoDocumento().equals(personaActual.getId().getTipoDocumento())) {
			esInvPalProyecto = true;
		} else {
			esInvPalProyecto = false;
		}

		if (esInvPalProyecto) {
			r.setNombreReporte("/cartas/ActaParticipacionProyectosInvPrincipal");
		} else {
			r.setNombreReporte("/cartas/ActaParticipacionProyectos");
		}

		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().dispatch("/ReporteEngineServlet");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.responseComplete();
		}
	}

	public void generarCertificadoParticipacionNoVinculado() {
		boolean esInvPalProyecto = false;
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("Id", proyectoSeleccionado.getId().toString());
		r.adicionarParametro("Dep", ii.getDependencia().getSede().getId().toString());
		r.adicionarParametro("InvId", personaActual.getId().getDocumento());
		r.adicionarParametro("InvTipoId", personaActual.getId().getTipoDocumento());
		r.setFormato(ReporteBirt.FORMATO_PDF);

		Investigador inPal = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyectoSeleccionado.getId());
		if (inPal.getId().getDocumento().equals(personaActual.getId().getDocumento())
				&& inPal.getId().getTipoDocumento().equals(personaActual.getId().getTipoDocumento())) {
			esInvPalProyecto = true;
		} else {
			esInvPalProyecto = false;
		}

		r.setNombreReporte("/cartas/ActaParticipacionProyectosHistorico");

		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().dispatch("/ReporteEngineServlet");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.responseComplete();
		}
	}

	public void generarCertificadoParticipacionCoinvestigadores() {
		boolean esInvPalProyecto = false;
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("Id", proyectoSeleccionado.getId().toString());
		r.adicionarParametro("Dep", ii.getDependencia().getSede().getId().toString());
		r.adicionarParametro("InvId", this.documento);
		r.adicionarParametro("InvTipoId", this.tipoDocumento.getId());
		r.setFormato(ReporteBirt.FORMATO_PDF);
		r.setNombreReporte("/cartas/ActaParticipacionProyectos");

		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().dispatch("/ReporteEngineServlet");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.responseComplete();
		}
	}

	public void generarCertificadoParticipacionCoinvestigadoresNoVin() {
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("Id", proyectoSeleccionado.getId().toString());
		r.adicionarParametro("Dep", ii.getDependencia().getSede().getId().toString());
		r.adicionarParametro("InvId", this.documento);
		r.adicionarParametro("InvTipoId", this.tipoDocumento.getId());
		r.setFormato(ReporteBirt.FORMATO_PDF);
		r.setNombreReporte("/cartas/ActaParticipacionProyectosHistorico");

		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().dispatch("/ReporteEngineServlet");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.responseComplete();
		}
	}

	public void generarCertificadoPazYSalvo() {

		String consultaProyectosCompromiso = "select e " + "from ProyectoCompromiso e, InvestigadorProyecto i "
				+ "where e.proyecto.estadoProyecto.id = 'A' " + "and e.cumplido = 'N' " + "and i.tipo.id = 'P' "
				+ "and i.proyecto.id = e.proyecto.id " + "and i.investigador.id.documento = '"
				+ personaActual.getId().getDocumento() + "' and i.investigador.id.tipoDocumento = '"
				+ personaActual.getId().getTipoDocumento()
				+ "' and nvl(e.fechaVencProrroga,e.fechaVencimiento) < trunc(sysdate)";
		listaCompromisosProyectos = servicioGeneral.obtenerObjetos(ProyectoCompromiso.class,
				consultaProyectosCompromiso);

		if (listaCompromisosProyectos != null) {
			if (listaCompromisosProyectos.size() > 0) {
				mostrarNoPazysalvo = true;
				mostrarSiPazysalvo = false;
			}

		}

		String consultaSemillerosCompromiso = "select e " + "from SemilleroInforme e, SemilleroIntegrante i "
				+ "where e.cumple = '0' " + "and i.tipo.id = 'DD' " + "and i.semillero.id = e.semillero.id "
				+ "and i.integrante.id.documento = '" + personaActual.getId().getDocumento()
				+ "' and i.integrante.id.tipoDocumento = '" + personaActual.getId().getTipoDocumento()
				+ "' and e.fechaCompromiso < trunc(sysdate) and i.estado = 'A'";

		listaCompromisosSemilleros = servicioGeneral.obtenerObjetos(SemilleroInforme.class,
				consultaSemillerosCompromiso);

		if (listaCompromisosSemilleros != null) {
			if (listaCompromisosSemilleros.size() > 0) {
				for (int i = 0; i < listaCompromisosSemilleros.size(); i++) {
					Semillero sem = listaCompromisosSemilleros.get(i).getSemillero();
					if (sem.getEstadoActual().getId().equals(SemilleroEstado.ESTADO_ACTIVO)) {
						mostrarNoPazysalvo = true;
						mostrarSiPazysalvo = false;
						break;
					}
				}

			}

		}
		
		listaCompromisosMovilidades = new ArrayList<String[]>();

		String consultaMovilidadesMovilidadVisitanteExterior = "select  #id mv.id, " + "#aceptacion "
				+ "mv.aceptacion, #aprobacion mv.aprobacion, " + "#fechafinal add_months(mv.fechafinal,3) , "
				+ "#numeroNotificaciones mv.numeroNotificaciones " + "from MovilidadVisitanteExterior mv " + "where "
				+ "(mv.estadoSeguimiento <> 'F' or " + "mv.estadoSeguimiento is null) " + "and mv.aprobacion = 'SI' "
				+ "and mv.aceptacion = 'SI' " + "and ADD_MONTHS(mv.fechafinal,3) < SYSDATE and mv.personaInv.id.documento = '" + personaActual.getId().getDocumento() +"' "
				+ "and mv.personaInv.id.tipoDocumento = '" + personaActual.getId().getTipoDocumento()+"' "
				+ " order by mv.id";

		List<MovilidadVisitanteExterior> movilidadesVisitantes = servicioGeneral.obtenerObjetosLimitado(
				MovilidadVisitanteExterior.class, consultaMovilidadesMovilidadVisitanteExterior);
		Iterator<MovilidadVisitanteExterior> i = movilidadesVisitantes.iterator();
		if (movilidadesVisitantes != null && movilidadesVisitantes.size() > 0) {
			mostrarNoPazysalvo = true;
			mostrarSiPazysalvo = false;
			while (i.hasNext()) {
				MovilidadVisitanteExterior mov = i.next();
				String estadoMov = "";
				if (!esCadenaVacia(mov.getAprobacion())) {
					estadoMov = "Aprobada";
				}
				listaCompromisosMovilidades.add(new String[] { mov.getId().toString(), "Visitantes", estadoMov, mov.getEstadoSeguimiento(),
						mov.getFechafinal().toString(), mov.getNumeroNotificaciones()==null ? "0" : mov.getNumeroNotificaciones().toString()});

			}
		}
		
		String consultaMovilidadesMovilidadDocentesExterior = "select  #id mv.id, " + "#aceptacion "
				+ "mv.aceptacion, #aprobacion mv.aprobacion, " + "#fechafinal add_months(mv.fechafinal,3) , "
				+ "#numeroNotificaciones mv.numeroNotificaciones " + "from MovilidadDocentesExterior mv " + "where "
				+ "(mv.estadoSeguimiento <> 'F' or " + "mv.estadoSeguimiento is null) " + "and mv.aprobacion = 'SI' "
				+ "and mv.aceptacion = 'SI' " + "and ADD_MONTHS(mv.fechafinal,3) < SYSDATE and mv.personaInv.id.documento = '" + personaActual.getId().getDocumento() +"' "
				+ "and mv.personaInv.id.tipoDocumento = '" + personaActual.getId().getTipoDocumento()+"' "
				+ " order by mv.id";

		List<MovilidadDocentesExterior> movilidadesDocentesExt = servicioGeneral.obtenerObjetosLimitado(
				MovilidadDocentesExterior.class, consultaMovilidadesMovilidadDocentesExterior);
		Iterator<MovilidadDocentesExterior> iD = movilidadesDocentesExt.iterator();
		if (movilidadesDocentesExt != null && movilidadesDocentesExt.size() > 0) {
			mostrarNoPazysalvo = true;
			mostrarSiPazysalvo = false;
			while (iD.hasNext()) {
				MovilidadDocentesExterior movD = iD.next();
				String estadoMov = "";
				if (!esCadenaVacia(movD.getAprobacion())) {
					estadoMov = "Aprobada";
				}
				listaCompromisosMovilidades.add(new String[] { movD.getId().toString(), "Docentes Exterior", estadoMov, movD.getEstadoSeguimiento(),
						movD.getFechafinal().toString(),  movD.getNumeroNotificaciones()==null ? "0" : movD.getNumeroNotificaciones().toString() });

			}
		}
		
		String consultaMovilidadesMovilidadEstudiantesExterior = "select  #id mv.id, " + "#aceptacion "
				+ "mv.aceptacion, #aprobacion mv.aprobacion, " + "#fechafinal add_months(mv.fechafinal,3) , #estadoSeguimiento mv.estadoSeguimiento, "
				+ "#numeroNotificaciones mv.numeroNotificaciones " + "from MovilidadEstudiantesPosgrado mv " + "where "
				+ "(mv.estadoSeguimiento <> 'F' or " + "mv.estadoSeguimiento is null) " + "and mv.aprobacion = 'SI' "
				+ "and mv.aceptacion = 'SI' " + "and ADD_MONTHS(mv.fechafinal,3) < SYSDATE and mv.personaInv.id.documento = '" + personaActual.getId().getDocumento() +"' "
				+ "and mv.personaInv.id.tipoDocumento = '" + personaActual.getId().getTipoDocumento()+"' "
				+ " order by mv.id";

		List<MovilidadEstudiantesPosgrado> movilidadesEstudiantesExt = servicioGeneral.obtenerObjetosLimitado(
				MovilidadEstudiantesPosgrado.class, consultaMovilidadesMovilidadEstudiantesExterior);
		Iterator<MovilidadEstudiantesPosgrado> iE = movilidadesEstudiantesExt.iterator();
		if (movilidadesEstudiantesExt != null && movilidadesEstudiantesExt.size() > 0) {
			mostrarNoPazysalvo = true;
			mostrarSiPazysalvo = false;
			while (iE.hasNext()) {
				MovilidadEstudiantesPosgrado movE = iE.next();
				String estadoMov = "";
				if (!esCadenaVacia(movE.getAprobacion())) {
					estadoMov = "Aprobada";
				}
				listaCompromisosMovilidades.add(new String[] { movE.getId().toString(), "Estudiantes Exterior", estadoMov, movE.getEstadoSeguimiento(),
						movE.getFechafinal().toString(),  movE.getNumeroNotificaciones()==null ? "0" : movE.getNumeroNotificaciones().toString() });

			}
		}

		if (!mostrarNoPazysalvo) {
			mostrarNoPazysalvo = false;
			mostrarSiPazysalvo = true;
		}

	}

	public void descargarCertificadoPazySalvo() {
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("Dep", ii.getDependencia().getSede().getId().toString());
		r.adicionarParametro("InvId", personaActual.getId().getDocumento());
		r.adicionarParametro("InvTipoId", personaActual.getId().getTipoDocumento());
		r.setFormato(ReporteBirt.FORMATO_PDF);
		r.setNombreReporte("/cartas/ActaPazYSalvoProyectos");

		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().dispatch("/ReporteEngineServlet");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.responseComplete();
		}
	}

	public void cambiarTipoBusqueda() {
		esCertificadoCoinvestigador = false;
		if (opcionesCertificado.equals("participacion")) {
			mostrarCertParticipacion = true;
			mostrarCertPazysalvo = false;
			mostrarCertSemilleros = false;
			mostrarCertGrupos = false;
			cargarProyectos();
		} else if (opcionesCertificado.equals("semilleros")) {
			mostrarCertParticipacion = false;
			mostrarCertPazysalvo = false;
			mostrarCertSemilleros = true;
			mostrarCertGrupos = false;
			cargarSemillerosActual();

		} else if (opcionesCertificado.equals("grupos")) {
			mostrarCertParticipacion = false;
			mostrarCertPazysalvo = false;
			mostrarCertSemilleros = false;
			mostrarCertGrupos = true;
			cargarGruposActual();

		} else {
			mostrarCertParticipacion = false;
			mostrarCertSemilleros = false;
			mostrarCertGrupos = false;
			mostrarCertPazysalvo = true;

		}

	}

	private void cargarTiposDocumento() {
		if (tipoDocumentoItem == null || (tipoDocumentoItem != null && tipoDocumentoItem.length == 0)) {
			List listaTipoDocumento = servicioGeneral.obtenerListaObjetos("TipoDocumento");
			tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
			for (int i = 0; i < listaTipoDocumento.size(); i++) {
				TipoDocumento td = (TipoDocumento) listaTipoDocumento.get(i);
				tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
			}
			tipoDocumento = (TipoDocumento) listaTipoDocumento.get(0);
		}
	}

	public void buscarPersona() {
		String consultaProyectos = "select #id e.id, #nombre e.nombre from Proyecto e, InvestigadorProyecto i, InvestigadorProyecto c where e.estadoProyecto.id = 'F' and i.proyecto.id = e.id and i.investigador.id.documento = '"
				+ personaActual.getId().getDocumento() + "' and i.investigador.id.tipoDocumento = '"
				+ personaActual.getId().getTipoDocumento()
				+ "' and i.tipo.id = 'P' and c.proyecto.id = e.id and c.investigador.id.documento = '" + this.documento
				+ "' and c.investigador.id.tipoDocumento = '" + this.tipoDocumento.getId() + "'";
		listaProyectosFinalizadosCoinvestigador = servicioGeneral.obtenerObjetosLimitado(Proyecto.class,
				consultaProyectos);
		mostrarCertParticipacionCoinvestigador = true;
		cargarSemillerosInvestigador();
		cargarGruposInvestigador();
		mostrarCertParticipacionCoinvestigadorSenm = true;
		mostrarCertParticipacionCoinvestigadorGru = true;
		esCertificadoCoinvestigador = true;

		String consultaProyectosSolInvestigador = "select distinct #id e.id, #nombre e.nombre from Proyecto e, SolicitudInvestigador si, Solicitud sol, InvestigadorProyecto i where e.estadoProyecto.id = 'F' and i.proyecto.id = e.id and i.investigador.id.documento = '"
				+ personaActual.getId().getDocumento() + "' and i.investigador.id.tipoDocumento = '"
				+ personaActual.getId().getTipoDocumento()
				+ "' and i.tipo.id = 'P' and sol.proyecto.id = e.id and si.solicitud.id = sol.id and si.investigador.id.documento = '"
				+ this.documento + "' and si.investigador.id.tipoDocumento = '" + this.tipoDocumento.getId() + "'";
		List<Proyecto> listaProyectosFinalizadosNoVinAux = servicioGeneral.obtenerObjetosLimitado(Proyecto.class,
				consultaProyectosSolInvestigador);
		listaProyectosFinalizadosNoVinculadoActualmente = new ArrayList<Proyecto>();
		for (int i = 0; i < listaProyectosFinalizadosNoVinAux.size(); i++) {
			if (!listaProyectosFinalizadosCoinvestigador.contains(listaProyectosFinalizadosNoVinAux.get(i))) {
				listaProyectosFinalizadosNoVinculadoActualmente.add(listaProyectosFinalizadosNoVinAux.get(i));
			}
		}

		mostrarCertParticipacionCoinvestigadorNiVin = true;
	}

	public String irInicio() {
		sesion.removeAttribute("ManejadorCertificaciones");
		return "misProyectos";
	}

	public InvestigadorInterno getIi() {
		return ii;
	}

	public void setIi(InvestigadorInterno ii) {
		this.ii = ii;
	}

	public String getNombreCompletoPersona() {
		return nombreCompletoPersona;
	}

	public void setNombreCompletoPersona(String nombreCompletoPersona) {
		this.nombreCompletoPersona = nombreCompletoPersona;
	}

	public List<Proyecto> getListaProyectosFinalizados() {
		return listaProyectosFinalizados;
	}

	public void setListaProyectosFinalizados(List<Proyecto> listaProyectosFinalizados) {
		this.listaProyectosFinalizados = listaProyectosFinalizados;
	}

	public String getOpcionesCertificado() {
		return opcionesCertificado;
	}

	public void setOpcionesCertificado(String opcionesCertificado) {
		this.opcionesCertificado = opcionesCertificado;
	}

	public boolean isMostrarCertParticipacion() {
		return mostrarCertParticipacion;
	}

	public void setMostrarCertParticipacion(boolean mostrarCertParticipacion) {
		this.mostrarCertParticipacion = mostrarCertParticipacion;
	}

	public boolean isMostrarCertPazysalvo() {
		return mostrarCertPazysalvo;
	}

	public void setMostrarCertPazysalvo(boolean mostrarCertPazysalvo) {
		this.mostrarCertPazysalvo = mostrarCertPazysalvo;
	}

	public Proyecto getProyectoSeleccionado() {
		return proyectoSeleccionado;
	}

	public void setProyectoSeleccionado(Proyecto proyectoSeleccionado) {
		this.proyectoSeleccionado = proyectoSeleccionado;
	}

	public List<Proyecto> getListaProyectosActivos() {
		return listaProyectosActivos;
	}

	public void setListaProyectosActivos(List<Proyecto> listaProyectosActivos) {
		this.listaProyectosActivos = listaProyectosActivos;
	}

	public List<ProyectoCompromiso> getListaCompromisosProyectos() {
		return listaCompromisosProyectos;
	}

	public void setListaCompromisosProyectos(List<ProyectoCompromiso> listaCompromisosProyectos) {
		this.listaCompromisosProyectos = listaCompromisosProyectos;
	}

	public boolean isMostrarNoPazysalvo() {
		return mostrarNoPazysalvo;
	}

	public void setMostrarNoPazysalvo(boolean mostrarNoPazysalvo) {
		this.mostrarNoPazysalvo = mostrarNoPazysalvo;
	}

	public boolean isMostrarSiPazysalvo() {
		return mostrarSiPazysalvo;
	}

	public void setMostrarSiPazysalvo(boolean mostrarSiPazysalvo) {
		this.mostrarSiPazysalvo = mostrarSiPazysalvo;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public SelectItem[] getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}

	public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
		this.tipoDocumentoItem = tipoDocumentoItem;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public boolean isMostrarCertParticipacionCoinvestigador() {
		return mostrarCertParticipacionCoinvestigador;
	}

	public void setMostrarCertParticipacionCoinvestigador(boolean mostrarCertParticipacionCoinvestigador) {
		this.mostrarCertParticipacionCoinvestigador = mostrarCertParticipacionCoinvestigador;
	}

	public List<Proyecto> getListaProyectosFinalizadosCoinvestigador() {
		return listaProyectosFinalizadosCoinvestigador;
	}

	public void setListaProyectosFinalizadosCoinvestigador(List<Proyecto> listaProyectosFinalizadosCoinvestigador) {
		this.listaProyectosFinalizadosCoinvestigador = listaProyectosFinalizadosCoinvestigador;
	}

	public List<Proyecto> getListaProyectosFinalizadosNoVinculadoActualmente() {
		return listaProyectosFinalizadosNoVinculadoActualmente;
	}

	public void setListaProyectosFinalizadosNoVinculadoActualmente(
			List<Proyecto> listaProyectosFinalizadosNoVinculadoActualmente) {
		this.listaProyectosFinalizadosNoVinculadoActualmente = listaProyectosFinalizadosNoVinculadoActualmente;
	}

	public boolean isMostrarCertParticipacionCoinvestigadorNiVin() {
		return mostrarCertParticipacionCoinvestigadorNiVin;
	}

	public void setMostrarCertParticipacionCoinvestigadorNiVin(boolean mostrarCertParticipacionCoinvestigadorNiVin) {
		this.mostrarCertParticipacionCoinvestigadorNiVin = mostrarCertParticipacionCoinvestigadorNiVin;
	}

	public boolean isMostrarCertSemilleros() {
		return mostrarCertSemilleros;
	}

	public void setMostrarCertSemilleros(boolean mostrarCertSemilleros) {
		this.mostrarCertSemilleros = mostrarCertSemilleros;
	}

	public Semillero getSemilleroSeleccionado() {
		return semilleroSeleccionado;
	}

	public void setSemilleroSeleccionado(Semillero semilleroSeleccionado) {
		this.semilleroSeleccionado = semilleroSeleccionado;
	}

	public List<Semillero> getListaSemillerosActivos() {
		return listaSemillerosActivos;
	}

	public void setListaSemillerosActivos(List<Semillero> listaSemillerosActivos) {
		this.listaSemillerosActivos = listaSemillerosActivos;
	}

	public void generarCertificadoParticipacionSemillero() {
		boolean esInvPalSemillero = false;
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("Id", semilleroSeleccionado.getId().toString());
		if (ii.getDependencia().getFacultad().getSede().isEsSedeAndina()) {
			r.adicionarParametro("Dep", ii.getDependencia().getFacultad().getId().toString());
		} else {
			r.adicionarParametro("Dep", ii.getDependencia().getSede().getId().toString());
		}
		if (esCertificadoCoinvestigador) {
			r.adicionarParametro("InvId", documento);
			r.adicionarParametro("InvTipoId", tipoDocumento.getId());
		} else {
			r.adicionarParametro("InvId", personaActual.getId().getDocumento());
			r.adicionarParametro("InvTipoId", personaActual.getId().getTipoDocumento());
		}

		r.setFormato(ReporteBirt.FORMATO_PDF);

		Investigador inPal = semilleroSeleccionado.getLider();
		if (inPal.getId().getDocumento().equals(personaActual.getId().getDocumento())
				&& inPal.getId().getTipoDocumento().equals(personaActual.getId().getTipoDocumento())
				&& !esCertificadoCoinvestigador) {
			esInvPalSemillero = true;
		} else {
			esInvPalSemillero = false;
		}

		if (esInvPalSemillero) {
			r.setNombreReporte("/cartas/ActaParticipacionSemillerosDirector");
		} else {
			r.setNombreReporte("/cartas/ActaParticipacionSemilleros");
		}

		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().dispatch("/ReporteEngineServlet");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.responseComplete();
		}
	}

	public void generarCertificadoParticipacionGrupo() {
		boolean esInvPalGrupo = false;
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("Id", grupoSeleccionado.getId().toString());
		if (ii.getDependencia().getFacultad().getSede().isEsSedeAndina()) {
			r.adicionarParametro("Dep", ii.getDependencia().getFacultad().getId().toString());
		} else {
			r.adicionarParametro("Dep", ii.getDependencia().getSede().getId().toString());
		}

		if (esCertificadoCoinvestigador) {
			r.adicionarParametro("InvId", documento);
			r.adicionarParametro("InvTipoId", tipoDocumento.getId());
		} else {
			r.adicionarParametro("InvId", personaActual.getId().getDocumento());
			r.adicionarParametro("InvTipoId", personaActual.getId().getTipoDocumento());
		}
		r.setFormato(ReporteBirt.FORMATO_PDF);

		Investigador inPal = grupoSeleccionado.getResponsable();
		if (inPal.getId().getDocumento().equals(personaActual.getId().getDocumento())
				&& inPal.getId().getTipoDocumento().equals(personaActual.getId().getTipoDocumento())
				&& !esCertificadoCoinvestigador) {
			esInvPalGrupo = true;
		} else {
			esInvPalGrupo = false;
		}

		if (esInvPalGrupo) {
			r.setNombreReporte("/cartas/ActaParticipacionGrupoDirector");
		} else {
			r.setNombreReporte("/cartas/ActaParticipacionGrupo");
		}

		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().dispatch("/ReporteEngineServlet");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.responseComplete();
		}
	}
	
	public void generarCertificadoParticipacionGrupoNoVinc() {
		boolean esInvPalGrupo = false;
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("Id", grupoSeleccionado.getId().toString());
		if (ii.getDependencia().getFacultad().getSede().isEsSedeAndina()) {
			r.adicionarParametro("Dep", ii.getDependencia().getFacultad().getId().toString());
		} else {
			r.adicionarParametro("Dep", ii.getDependencia().getSede().getId().toString());
		}

		if (esCertificadoCoinvestigador) {
			r.adicionarParametro("InvId", documento);
			r.adicionarParametro("InvTipoId", tipoDocumento.getId());
		} else {
			r.adicionarParametro("InvId", personaActual.getId().getDocumento());
			r.adicionarParametro("InvTipoId", personaActual.getId().getTipoDocumento());
		}
		r.setFormato(ReporteBirt.FORMATO_PDF);
		r.setNombreReporte("/cartas/ActaParticipacionGrupoDesvinculado");
		

		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().dispatch("/ReporteEngineServlet");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.responseComplete();
		}
	}

	public boolean isMostrarCertParticipacionCoinvestigadorSenm() {
		return mostrarCertParticipacionCoinvestigadorSenm;
	}

	public void setMostrarCertParticipacionCoinvestigadorSenm(boolean mostrarCertParticipacionCoinvestigadorSenm) {
		this.mostrarCertParticipacionCoinvestigadorSenm = mostrarCertParticipacionCoinvestigadorSenm;
	}

	public boolean isEsVif() {
		return esVif;
	}

	public void setEsVif(boolean esVif) {
		this.esVif = esVif;
	}

	public boolean isMostrarCertGrupos() {
		return mostrarCertGrupos;
	}

	public void setMostrarCertGrupos(boolean mostrarCertGrupos) {
		this.mostrarCertGrupos = mostrarCertGrupos;
	}

	public Grupo getGrupoSeleccionado() {
		return grupoSeleccionado;
	}

	public void setGrupoSeleccionado(Grupo grupoSeleccionado) {
		this.grupoSeleccionado = grupoSeleccionado;
	}

	public List<Grupo> getListaGruposActivos() {
		return listaGruposActivos;
	}

	public void setListaGruposActivos(List<Grupo> listaGruposActivos) {
		this.listaGruposActivos = listaGruposActivos;
	}

	public boolean isMostrarCertParticipacionCoinvestigadorGru() {
		return mostrarCertParticipacionCoinvestigadorGru;
	}

	public void setMostrarCertParticipacionCoinvestigadorGru(boolean mostrarCertParticipacionCoinvestigadorGru) {
		this.mostrarCertParticipacionCoinvestigadorGru = mostrarCertParticipacionCoinvestigadorGru;
	}

	public boolean isEsCertificadoCoinvestigador() {
		return esCertificadoCoinvestigador;
	}

	public void setEsCertificadoCoinvestigador(boolean esCertificadoCoinvestigador) {
		this.esCertificadoCoinvestigador = esCertificadoCoinvestigador;
	}

	public List<SemilleroInforme> getListaCompromisosSemilleros() {
		return listaCompromisosSemilleros;
	}

	public void setListaCompromisosSemilleros(List<SemilleroInforme> listaCompromisosSemilleros) {
		this.listaCompromisosSemilleros = listaCompromisosSemilleros;
	}

	public List<String[]> getListaCompromisosMovilidades() {
		return listaCompromisosMovilidades;
	}

	public void setListaCompromisosMovilidades(List<String[]> listaCompromisosMovilidades) {
		this.listaCompromisosMovilidades = listaCompromisosMovilidades;
	}

	public List<Grupo> getListaGruposActivosNoVinculados() {
		return listaGruposActivosNoVinculados;
	}

	public void setListaGruposActivosNoVinculados(List<Grupo> listaGruposActivosNoVinculados) {
		this.listaGruposActivosNoVinculados = listaGruposActivosNoVinculados;
	}

}
