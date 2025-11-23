package co.edu.unal.hermes.vista.colecciones;

import java.io.File;
import java.io.IOException;
import java.sql.BatchUpdateException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.hibernate.HibernateException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.ArchivoColeccion;
import co.edu.unal.hermes.modelo.ArchivoGestionColeccion;
import co.edu.unal.hermes.modelo.Coleccion;
import co.edu.unal.hermes.modelo.ColeccionAutoridadCompetente;
import co.edu.unal.hermes.modelo.ColeccionGestion;
import co.edu.unal.hermes.modelo.ColeccionPersona;
import co.edu.unal.hermes.modelo.ColeccionRequisito;
import co.edu.unal.hermes.modelo.ColeccionTipoObjeto;
import co.edu.unal.hermes.modelo.ColeccionTipoPreservacion;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.HistoricoColeccion;
import co.edu.unal.hermes.modelo.HistoricoEstadoGestionColeccion;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Parametro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.ProyectoRequisito;
import co.edu.unal.hermes.modelo.Rol;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorColeccionesBiologicas extends ManejadorBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Persona persona;
	private List<Coleccion> listaColecciones;
	private Coleccion coleccionEditar;
	private Coleccion coleccionVer;
	private boolean verReporte = false;
	private List<Coleccion> listaColeccionesSistema = new ArrayList<Coleccion>();
	private List<SelectItem> listaColeccionesItems = new ArrayList<SelectItem>();
	private SelectItem[] listaEstados;
	private String idColeccion;
	private Coleccion coleccion;
	private UploadedFile archivoCargar;
	private List<ArchivoColeccion> listaArchivos;
	private ArchivoColeccion archivoSeleccionado;
	private boolean estaRegistrada;
	private List<SelectItem> listaAutoridades;
	List<FuenteFinanciacion> listaEntidadesExt;
	private String autoridadCompetente;
	private String numeroRegistro;
	private Date fechaUltActualizacionAutoridad;
	private SelectItem[] listaEstadosAutoridad;
	private String estadoAutoridad;
	private ColeccionAutoridadCompetente autoridadSeleccionada;
	private ColeccionGestion gestionSeleccionada;
	private String comentariosGenerales;
	private String estadoInicial;
	private List<HistoricoColeccion> historico;
	private List<ArchivoGestionColeccion> listaArchivosGestion;
	private List<ColeccionRequisito> listaRequisito;

	org.primefaces.model.UploadedFile archivoGestionCargado;
	private ArchivoGestionColeccion archivoGestion;
	private String nuevoEstadoGestion;
	private String nuevoEstadoAutoridad;
	private String estadoGestionActual;
	private String observacionCambioEstado;
	private String comentariosAutoridad;
	
	private boolean esColeccionBiologica;

	/** The lista solicitudes proyecto. */
	private List<ColeccionGestion> listaGestionesColeccion;

	public ManejadorColeccionesBiologicas() {

		persona = (Persona) sesion.getAttribute("persona");
		if (persona != null) {
			listaColecciones = servicioGeneral.obtenerObjetos(Coleccion.class,
					"select c from Coleccion c, ColeccionPersona cp "
							+ "where c.estado not in ('B') and c.id = cp.coleccion.id and cp.tipoPersona = 'CU1' and "
							+ "cp.persona.id.tipoDocumento = '" + persona.getId().getTipoDocumento() + "' "
							+ "AND cp.persona.id.documento = '" + persona.getId().getDocumento() + "'");

			List<Parametro> listaParametro = new ArrayList<Parametro>();

			listaParametro = this.servicioGeneral.obtenerListaObjetosWhere(Parametro.class,
					"WHERE p.nombre = 'COORD_BIODIVERSIDAD' AND p.valor= '" + persona.getId().getDocumento() + "' and (p.fechaFinal > current_date OR p.fechaFinal IS NULL)");

			if (!esListaVacia(listaParametro)) {
				listaColecciones();
				cargarEstados();
				cargarEstadosEntidad();
				cargarEntidadesBiodiversidad();
				verReporte = true;
			}
		}
	}
	
	public boolean revisarColeccionBiologica() {

		if (!coleccion.getListaTiposObjetos().isEmpty()) {
			for (ColeccionTipoObjeto a : coleccion.getListaTiposObjetos()) {
				if (a.getSubTipoObjeto().getEstado().equals("BIO")) {
					return true;
				}
			}
		}

	return false;
}
	
	
	public boolean revisarColeccionBiologicaRegistro() {

		if (!coleccion.getListaAutoridades().isEmpty()) {
			for (ColeccionAutoridadCompetente a : coleccion.getListaAutoridades()) {
				if (a.getAutoridad().getId().equals(FuenteFinanciacion.ID_RNC) && a.getEstado().getIdentificador().getTipo().equals("REG")) {
					coleccion.setFechaUltimoRegistroHumboldt(a.getFechaActualizacion());
					return true;
				}
			}
		}

	return false;
}

	/**
	 * Lista entidades externas a partir de método del manejador base
	 */
	public void cargarEntidadesBiodiversidad() {

		listaAutoridades = new ArrayList<SelectItem>();
		listaEntidadesExt = cargarEntidadesExternas(
				"select e from FuenteFinanciacion e where e.autoridadCompetenteBiodiversidad = '1'");

		if (!esListaVacia(listaEntidadesExt)) {
			for (int i = 0; i < listaEntidadesExt.size(); i++) {
				FuenteFinanciacion entidad = (FuenteFinanciacion) listaEntidadesExt.get(i);
				listaAutoridades.add(new SelectItem(entidad.getId(), entidad.getDescripcion()));
			}
		}
	}
	
	public void cargarRequisitos() {

		List<DominioDetalle> lista;
		String consulta = "select dd from Dominio d, DominioDetalle dd where "
				+ "d.id = dd.identificador.id and d.tipo like 'REQUISITOS_REG_COLECCION_UN' "
				+ " and dd.estado in ('A') order by dd.descripcion";

		lista = servicioGeneral.obtenerObjetos(DominioDetalle.class, consulta);
		
		if(esListaVacia(gestionSeleccionada.getListaRequisitos())) {
			if (!esListaVacia(lista)) {
				for (int i = 0; i < lista.size(); i++) {
					DominioDetalle dominio = (DominioDetalle) lista.get(i);
					ColeccionRequisito cr = new ColeccionRequisito();
					cr.setRequisito(dominio);
					cr.setGestion(gestionSeleccionada);
					cr.setCumplido(false);
					gestionSeleccionada.adicionarRequisito(cr);
					
				}
			}
		}

	}

	public void guardarParcialmenteGestion() {

		if (esCadenaVacia(nuevoEstadoGestion)) {
			mensajeError("Debe seleccionar el nuevo estado.");
			return;
		}
		
		if(gestionSeleccionada.getTipo().getIdentificador().getTipo().equals("RCUN") && nuevoEstadoGestion.equals(ColeccionGestion.ESTADO_APROBADA) && !esListaVacia(gestionSeleccionada.getListaRequisitos())){
			for(int i= 0; i<gestionSeleccionada.getListaRequisitos().size(); i++ ) {
				ColeccionRequisito cre = gestionSeleccionada.getListaRequisitos().get(i);
				if(!cre.isCumplido()) {
					mensajeError("Debe cumplir con todos los requisitos para aprobar la gestión, en caso contrario considere devolverla para correcciones.");
					return;
				}
			}
		}

		if (esCadenaVacia(observacionCambioEstado)) {
			mensajeError("Debe ingresar una observación al cambio de estado.");
			return;
		}

		if (gestionSeleccionada.getTipo().getIdentificador().getTipo().equals("AC")
				|| gestionSeleccionada.getTipo().getIdentificador().getTipo().equals("RC")) {
			if (!nuevoEstadoGestion.equals(ColeccionGestion.ESTADO_RECHAZADA) && !nuevoEstadoGestion.equals(ColeccionGestion.ESTADO_DEVUELTA)) {
				if (esCadenaVacia(nuevoEstadoAutoridad)) {
					mensajeError("Debe ingresar el estado ante la autoridad.");
					return;
				}
				if (esCadenaVacia(comentariosAutoridad)) {
					mensajeError("Debe ingresar los comentarios ante la autoridad.");
					return;
				}
			}
			gestionSeleccionada.setComentariosAutoridad(comentariosAutoridad);
			if (gestionSeleccionada.getTipo().getIdentificador().getTipo().equals("AC")) {
				DominioDetalle estadoActualizacionColeccion = cargarEstadoGestionAutoridad(nuevoEstadoAutoridad,
						"ESTADO_ACT_COL_AUTORIDAD");
				gestionSeleccionada.setEstadoAutoridad(estadoActualizacionColeccion);
			} else if (gestionSeleccionada.getTipo().getIdentificador().getTipo().equals("RC")) {
				DominioDetalle estadoRegistroColeccion = cargarEstadoGestionAutoridad(nuevoEstadoAutoridad,
						"ESTADO_REG_COL_AUTORIDAD");
				gestionSeleccionada.setEstadoAutoridad(estadoRegistroColeccion);
			}
		}

		DominioDetalle estado = cargarEstadoGestion(nuevoEstadoGestion);
		gestionSeleccionada.setEstado(estado);
		gestionSeleccionada.setComentariosRevision(observacionCambioEstado);
		gestionSeleccionada.setPersonaRevisa(getPersonaActual());
		gestionSeleccionada.setFechaRevision(getToday());


		HistoricoEstadoGestionColeccion hecg = HistoricoEstadoGestionColeccion
				.generarHistoricoEstado(gestionSeleccionada);
		hecg.setFecha(getToday());
		hecg.setResposable(getPersonaActual());

		servicioGeneral.guardarObjeto(hecg);
		
		if(nuevoEstadoGestion.equals(ColeccionGestion.ESTADO_APROBADA)) {
			if (gestionSeleccionada.getTipo().getIdentificador().getTipo().equals("IC")) {
				coleccion.setEstado(Coleccion.INACTIVA);
				boolean guardo = servicioGeneral.ingresarColeccion(coleccion);
	
				if (!guardo) {
					mensajeError("Problema guardando la información del nuevo estado de la Colección");
				}
				else {
					guardarHistorico("-2",gestionSeleccionada.getComentariosRevision());
				}
			}
			
			if (gestionSeleccionada.getTipo().getIdentificador().getTipo().equals("RCUN")) {
				
				if(!esListaVacia(gestionSeleccionada.getListaRequisitos())){
					for(int i= 0; i<gestionSeleccionada.getListaRequisitos().size(); i++ ) {
						ColeccionRequisito cre = gestionSeleccionada.getListaRequisitos().get(i);
						servicioGeneral.guardarObjeto(cre);
					}
				}
					
				coleccion.setEstado(Coleccion.RECONOCIDA_UN);
				boolean guardo = servicioGeneral.ingresarColeccion(coleccion);
	
				if (!guardo) {
					mensajeError("Problema guardando la información del nuevo estado de la Colección");
				}
				else {
					guardarHistorico("-2",gestionSeleccionada.getComentariosRevision());
				}
			}

			if (gestionSeleccionada.getTipo().getIdentificador().getTipo().equals("CD")) {
				guardarNuevoDirector(gestionSeleccionada);
			}
		}
		
		if(nuevoEstadoGestion.equals(ColeccionGestion.ESTADO_DEVUELTA) &&  gestionSeleccionada.getTipo().getIdentificador().getTipo().equals("RCUN")) {
			coleccion.setEstado(Coleccion.INGRESANDO);
			boolean guardo = servicioGeneral.ingresarColeccion(coleccion);

			if (!guardo) {
				mensajeError("Problema guardando la información del nuevo estado de la Colección");
			}
			else {
				guardarHistorico("-2",gestionSeleccionada.getComentariosRevision());
			}
		}
		
		servicioGeneral.guardarObjeto(gestionSeleccionada);

		estadoGestionActual = estado.getIdentificador().getTipo();
		
		enviarCorreoRevisionGestionColeccion(gestionSeleccionada);

		mensajeInfo("Se ha guardado la información.");
	}
	
	private void guardarNuevoDirector(ColeccionGestion coleccionGestion) {
		Persona directorNuevo = coleccionGestion.getDirectorNuevo();
		Persona directorAnterior = coleccionGestion.getDirectorAnterior();
		ColeccionPersona coleccionPersonaYaExistente = null;
		ColeccionPersona coleccionPersonaAnterior = null;
		if(coleccion.getPersonalColeccion() != null) {
			Iterator<ColeccionPersona> it = coleccion.getPersonalColeccion().iterator();
			while (it.hasNext()) {
				ColeccionPersona coleccionPersona = it.next();
				if(coleccionPersona.getPersona().getId().getTipoDocumento().equals(directorNuevo.getId().getTipoDocumento())
						&& coleccionPersona.getPersona().getId().getDocumento().equals(directorNuevo.getId().getDocumento())) {
					coleccionPersonaYaExistente = coleccionPersona;
				}
				if(coleccionPersona.getPersona().getId().getTipoDocumento().equals(directorAnterior.getId().getTipoDocumento())
						&& coleccionPersona.getPersona().getId().getDocumento().equals(directorAnterior.getId().getDocumento())) {
					coleccionPersonaAnterior = coleccionPersona;
				}
			}
		}
		
		//Se guarda nuevo director
		if(coleccionPersonaYaExistente != null) {
			coleccionPersonaYaExistente.setTipoPersona(ColeccionPersona.CURADOR_GENERAL);
			servicioGeneral.guardarObjeto(coleccionPersonaYaExistente);
		}
		else {
			ColeccionPersona personalCol = new ColeccionPersona();

			personalCol.setPersona(directorNuevo);
			personalCol.setColeccion(coleccion);
			personalCol.setTipoPersona(ColeccionPersona.CURADOR_GENERAL);
			coleccion.getListaPersonal().add(personalCol);
			
			servicioGeneral.guardarObjeto(personalCol);			
		}
		

		boolean tieneRol = false;
		List roles = servicioPersona.obtenerRols(directorNuevo.getId());
		if (!esListaVacia(roles)) {
			for (int i = 0; i < roles.size(); i++) {
				Rol rol = (Rol) roles.get(i);
				String idRol = rol.getId();
				if ("CU".equals(idRol)) {
					tieneRol = true;
					break;
				}
			}
		}
		
		if (!tieneRol) {
			servicioGeneral.ejecutarSentencia("INSERT INTO HER_PERSONA_ROL (TDO_ID, PER_ID ,ROL_ID) VALUES ('"
					+ directorNuevo.getId().getTipoDocumento() + "','"
					+ directorNuevo.getId().getDocumento() + "','CU')");
		}

		List listaColecciones = servicioGeneral.obtenerObjetos(
				"from Coleccion c, ColeccionPersona cp where c.estado != 'B' and cp.persona.id.tipoDocumento = '"
						+ directorAnterior.getId().getTipoDocumento() + "' AND cp.persona.id.documento = '"
						+ directorAnterior.getId().getDocumento() + "' and cp.tipoPersona = 'CU1'");
		if (listaColecciones == null || (listaColecciones != null && listaColecciones.size() == 1)) {
			servicioGeneral.ejecutarSentencia("DELETE FROM HER_PERSONA_ROL WHERE ROL_ID = 'CU' AND TDO_ID = '"
					+ personaActual.getId().getTipoDocumento() + "' AND PER_ID = '"
					+ personaActual.getId().getDocumento() + "'");

		}
		coleccion.getListaPersonal().remove(coleccionPersonaAnterior);
		servicioGeneral.guardarObjeto(coleccion);	
		
		servicioGeneral.eliminarObjeto(coleccionPersonaAnterior);
		if (coleccion.getHistoricoCuradores() == null) {
			coleccion.setHistoricoCuradores("");
		}
		coleccion.setHistoricoCuradores(coleccion.getHistoricoCuradores()
				+ directorAnterior.getId().getTipoDocumento() + "-" + directorAnterior.getId().getDocumento() + "->"
				+ directorNuevo.getId().getTipoDocumento() + "-"
				+ directorNuevo.getId().getDocumento() + "--");

		servicioGeneral.guardarObjeto(coleccion);	
		
	}

	public void cargarEstados() {
		String consulta = "select dd from Dominio d, DominioDetalle dd where "
				+ "d.id = dd.identificador.id and d.tipo like 'ESTADO_COLECCION' "
				+ " and dd.identificador.id in ('133') and dd.estado = 'A' order by dd.descripcion";
		List<DominioDetalle> lista = servicioGeneral.obtenerObjetos(DominioDetalle.class, consulta);

		if (!esListaVacia(lista)) {
			listaEstados = new SelectItem[lista.size()];
			for (int i = 0; i < lista.size(); i++) {
				DominioDetalle dominio = (DominioDetalle) lista.get(i);
				listaEstados[i] = (new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion()));
			}
		}
	}

	public void cargarEstadosEntidad() {

		String consulta = "select dd from Dominio d, DominioDetalle dd where "
				+ "d.id = dd.identificador.id and d.tipo like 'ESTADO_COL_AUTORIDAD' order by dd.descripcion";
		List<DominioDetalle> lista = servicioGeneral.obtenerObjetos(DominioDetalle.class, consulta);

		if (!esListaVacia(lista)) {
			listaEstadosAutoridad = new SelectItem[lista.size()];
			for (int i = 0; i < lista.size(); i++) {
				DominioDetalle dominio = (DominioDetalle) lista.get(i);
				listaEstadosAutoridad[i] = (new SelectItem(dominio.getIdentificador().getTipo(),
						dominio.getDescripcion()));
			}
		}
	}

	private void eliminarManejadoresColecciones() {
		sesion.removeAttribute("colEdit");
		sesion.removeAttribute("manejadorHojaVidaColeccion");
		sesion.removeAttribute("manejadorGestionColecciones");
	}

	public String agregar() {
		eliminarManejadoresColecciones();
		return "editarColeccion";
	}

	public String editarColeccion() {
		eliminarManejadoresColecciones();
		sesion.setAttribute("colEdit", coleccionEditar);
		return "editarColeccion";
	}

	public String gestionarSolicitudesColeccion() {
		eliminarManejadoresColecciones();
		sesion.setAttribute("colEdit", coleccionEditar);
		return "gestionarColeccion";
	}

	public String verColeccion() {
		eliminarManejadoresColecciones();

		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext extContext = context.getExternalContext();
		String viewId = "/pages/Consultas/Coleccion.jsf";
		try {
			viewId = extContext.getRequestContextPath() + viewId + '?' + "idColeccion" + "=" + coleccionVer.getId();
			String urlLink = context.getExternalContext().encodeActionURL(viewId);
			extContext.redirect(urlLink);
		} catch (IOException e) {
			extContext.log(getClass().getName() + ".invokeRedirect", e);
		}
		return null;
	}

	public void reporteColecciones() throws SQLException {
		ReporteBirt r = new ReporteBirt();

		r.setNombreReporte("/colecciones/reporteColecciones");

		r.setFormato(ReporteBirt.FORMATO_XLS);
		sesion.setAttribute("reporte", r);

		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
	}

	public void reporteColeccion() throws SQLException {
		ReporteBirt r = new ReporteBirt();
		String path = RUTA_ARCHIVOS + "HER_COLECCION" + File.separator;
		String foto = "0";

		File actual = new File(path + idColeccion + ".jpg");
		if (actual.exists()) {
			foto = "1";

		} else {
			foto = "0";
		}

		r.setNombreReporte("/colecciones/reporte-coleccion");
		r.adicionarParametro("id", idColeccion);
		r.adicionarParametro("foto", foto);
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().dispatch("/ReporteEngineServlet");
			r.run(context);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.responseComplete();
		}

	}

	public String adminColecciones() {
		return "gestionColecciones";
	}

	public String verColecciones() {
		sesion.removeAttribute("manejadorColeccionesBiologicas");
		sesion.removeAttribute("manejadorHojaVidaColeccion");
		return "colecciones";
	}

	public void listaColecciones() {

		listaColeccionesSistema = servicioGeneral.obtenerObjetosLimitado(Coleccion.class,
				"select #id c.id, #nombre c.nombre from Coleccion c where "
						+ "c.estado not in ('B') order by c.id asc");

		if (!esListaVacia(listaColeccionesSistema)) {
			for (int i = 0; i < listaColeccionesSistema.size(); i++) {

				Coleccion col = (Coleccion) listaColeccionesSistema.get(i);
				listaColeccionesItems.add(new SelectItem(col.getId(), col.getNombre()));
			}
			Coleccion col = (Coleccion) listaColeccionesSistema.get(0);
			idColeccion = col.getId().toString();
			cargarColeccion();

		}
	}

	public void cargarColeccion() {

		String consulta = "from Coleccion c where c.id = " + idColeccion;
		List<Coleccion> lista = servicioGeneral.obtenerObjetos(Coleccion.class, consulta);
		comentariosGenerales = "";
		if (!esListaVacia(lista)) {
			coleccion = (Coleccion) lista.get(0);
			if (!esCadenaVacia(coleccion.getEstado())) {
				estadoInicial = coleccion.getEstado();
			}
			if (coleccion.getRegistroHumboldt() != null && "S".equals(coleccion.getRegistroHumboldt())) {
				estaRegistrada = true;
			} else {
				estaRegistrada = false;
			}
			recuperarArchivos();
			recuperarHistorico();
			cagarGestionesNoEliminadas(coleccion);
			esColeccionBiologica = revisarColeccionBiologica() && revisarColeccionBiologicaRegistro(); // contrla el formulario de las coleciones biologicas con registro ante RNC
		}
	}

	private void cagarGestionesNoEliminadas(Coleccion coleccion) {
		listaGestionesColeccion = new ArrayList<ColeccionGestion>();
		Iterator<ColeccionGestion> setIterator = coleccion.getGestiones().iterator();
		while (setIterator.hasNext()) {
			ColeccionGestion gestion = setIterator.next();
			if (!gestion.getEstado().getIdentificador().getTipo().equals(ColeccionGestion.ESTADO_ELIMINADA)
					&& !gestion.getEstado().getIdentificador().getTipo().equals(ColeccionGestion.ESTADO_INGRESANDO)) {
				listaGestionesColeccion.add(gestion);
			}
		}
		Collections.sort( listaGestionesColeccion, new ColeccionGestionComparator());
	}

	public void recuperarArchivos() {
		String archsql = "from ArchivoColeccion a where a.coleccion.id='" + idColeccion + "' order by a.fecha desc";
		listaArchivos = servicioGeneral.obtenerObjetos(ArchivoColeccion.class, archsql);
	}

	public void recuperarHistorico() {
		String archsql = "from HistoricoColeccion a where a.coleccion.id='" + idColeccion + "' order by a.fecha desc";
		historico = servicioGeneral.obtenerObjetos(HistoricoColeccion.class, archsql);
	}

	public void verArchivo() {
		descargarArchivoColeccionGenerico(archivoSeleccionado);
	}

	public void guardarArchivo(FileUploadEvent event) {
		archivoCargar = event.getFile();
		ArchivoColeccion ai = insertarArchivoColeccionGenerico(0, archivoCargar, "HU");
		listaArchivos.add(ai);
		ai.setColeccion(coleccion);
		servicioGeneral.guardarObjeto(ai);
	}

	public String agregarInfoAutoridad() {
		boolean valida = true;
		if (esCadenaVacia(autoridadCompetente)) {
			valida = false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Debe seleccionar la autoridad Competente", ""));
		}

		if (esCadenaVacia(numeroRegistro)) {
			valida = false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage("Debe indicar el número de registro en la autoridad Competente", ""));
		}

		if (fechaUltActualizacionAutoridad == null) {
			valida = false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage("Debe indicar la fecha de actualización en la autoridad Competente", ""));
		}

		if (esCadenaVacia(estadoAutoridad)) {
			valida = false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage("Debe indicar el estado de la colección en la autoridad Competente", ""));
		}

		if (valida) {
			ColeccionAutoridadCompetente autoridad = new ColeccionAutoridadCompetente();
			autoridad.setIdColeccion(coleccion.getId());
			autoridad.setFechaActualizacion(fechaUltActualizacionAutoridad);
			autoridad.setNumeroRegistro(numeroRegistro);
			autoridad.setAutoridad(obtenerEntidad(autoridadCompetente));
			autoridad.setEstado(servicioGeneral.obtenerDominioDetalleUnico("312", estadoAutoridad));
			if (coleccion.getListaAutoridades().isEmpty()) {
				coleccion.adicionarAutoridad(autoridad);
			} else {
				Boolean repetido = false;
				for (ColeccionAutoridadCompetente a : coleccion.getListaAutoridades()) {
					if (a.getAutoridad().getId().equals(autoridadCompetente)) {
						repetido = true;
						break;
					}
				}
				if (repetido) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Autoridad:", "La autoridad fue agregada a la tabla"));
				} else {
					coleccion.adicionarAutoridad(autoridad);
				}
			}
		}
		return null;

	}

	public void eliminarAutoridad() {
		coleccion.borrarAutoridad(autoridadSeleccionada);
		if (autoridadSeleccionada != null && autoridadSeleccionada.getId() != null) {
			servicioGeneral.eliminarObjeto(autoridadSeleccionada);
		}

	}

	public FuenteFinanciacion obtenerEntidad(String id) {
		FuenteFinanciacion entidad = new FuenteFinanciacion();
		if (!esListaVacia(listaEntidadesExt)) {
			for (int i = 0; i < listaEntidadesExt.size(); i++) {
				if (listaEntidadesExt.get(i).getId().equals(id)) {
					entidad = listaEntidadesExt.get(i);
					break;
				}
			}
		}
		return entidad;

	}

	public void actualizar() {
		if (coleccion.getEstado().equals(Coleccion.RECONOCIDA_UN)
				&& coleccion.getFechaUltimoRegistroHumboldt() == null) {
			coleccion.setRegistroHumboldt("N");
		} else {
			coleccion.setRegistroHumboldt("S");
		}
		boolean guardo = true;
		guardo = servicioGeneral.ingresarColeccion(coleccion);

		try {
			for (ColeccionAutoridadCompetente aut : coleccion.getListaAutoridades()) {
				servicioGeneral.guardarObjeto(aut);
			}
		} catch (HibernateException e) {
			Throwable cause = e.getCause();
			while (cause != null) {
				if (cause instanceof BatchUpdateException) {
					BatchUpdateException batchException = (BatchUpdateException) cause;
					System.out.println("SQL State: " + batchException.getSQLState());
					System.out.println("Error Code: " + batchException.getErrorCode());
					System.out.println("Message: " + batchException.getMessage());
					break;
				}
				cause = cause.getCause();
			}
			e.printStackTrace();
		}

		if (!guardo)

		{
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage("Problema guardando la información de registro de la Colección", ""));
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Se ha actualizado información de registro de la Colección", ""));
		}
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setListaColecciones(List<Coleccion> listaColecciones) {
		this.listaColecciones = listaColecciones;
	}

	public List<Coleccion> getListaColecciones() {
		return listaColecciones;
	}

	public void setColeccionEditar(Coleccion coleccionEditar) {
		this.coleccionEditar = coleccionEditar;
	}

	public Coleccion getColeccionEditar() {
		return coleccionEditar;
	}

	public Coleccion getColeccionVer() {
		return coleccionVer;
	}

	public void setColeccionVer(Coleccion coleccionVer) {
		this.coleccionVer = coleccionVer;
	}

	public boolean isVerReporte() {
		return verReporte;
	}

	public void setVerReporte(boolean verReporte) {
		this.verReporte = verReporte;
	}

	public List<SelectItem> getListaColeccionesItems() {
		return listaColeccionesItems;
	}

	public void setListaColeccionesItems(List<SelectItem> listaColeccionesItems) {
		this.listaColeccionesItems = listaColeccionesItems;
	}

	public String getIdColeccion() {
		return idColeccion;
	}

	public void setIdColeccion(String idColeccion) {
		this.idColeccion = idColeccion;
	}

	public UploadedFile getArchivoCargar() {
		return archivoCargar;
	}

	public void setArchivoCargar(UploadedFile archivoCargar) {
		this.archivoCargar = archivoCargar;
	}

	public List<ArchivoColeccion> getListaArchivos() {
		return listaArchivos;
	}

	public void setListaArchivos(List<ArchivoColeccion> listaArchivos) {
		this.listaArchivos = listaArchivos;
	}

	public Coleccion getColeccion() {
		return coleccion;
	}

	public void setColeccion(Coleccion coleccion) {
		this.coleccion = coleccion;
	}

	public ArchivoColeccion getArchivoSeleccionado() {
		return archivoSeleccionado;
	}

	public void setArchivoSeleccionado(ArchivoColeccion archivoSeleccionado) {
		this.archivoSeleccionado = archivoSeleccionado;
	}

	public boolean isEstaRegistrada() {
		return estaRegistrada;
	}

	public void setEstaRegistrada(boolean estaRegistrada) {
		this.estaRegistrada = estaRegistrada;
	}

	public SelectItem[] getListaEstados() {
		return listaEstados;
	}

	public void setListaEstados(SelectItem[] listaEstados) {
		this.listaEstados = listaEstados;
	}

	public List<SelectItem> getListaAutoridades() {
		return listaAutoridades;
	}

	public void setListaAutoridades(List<SelectItem> listaAutoridades) {
		this.listaAutoridades = listaAutoridades;
	}

	public String getAutoridadCompetente() {
		return autoridadCompetente;
	}

	public void setAutoridadCompetente(String autoridadCompetente) {
		this.autoridadCompetente = autoridadCompetente;
	}

	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	public Date getFechaUltActualizacionAutoridad() {
		return fechaUltActualizacionAutoridad;
	}

	public void setFechaUltActualizacionAutoridad(Date fechaUltActualizacionAutoridad) {
		this.fechaUltActualizacionAutoridad = fechaUltActualizacionAutoridad;
	}

	public String getEstadoAutoridad() {
		return estadoAutoridad;
	}

	public void setEstadoAutoridad(String estadoAutoridad) {
		this.estadoAutoridad = estadoAutoridad;
	}

	public SelectItem[] getListaEstadosAutoridad() {
		return listaEstadosAutoridad;
	}

	public void setListaEstadosAutoridad(SelectItem[] listaEstadosAutoridad) {
		this.listaEstadosAutoridad = listaEstadosAutoridad;
	}

	public List<FuenteFinanciacion> getListaEntidadesExt() {
		return listaEntidadesExt;
	}

	public void setListaEntidadesExt(List<FuenteFinanciacion> listaEntidadesExt) {
		this.listaEntidadesExt = listaEntidadesExt;
	}

	public ColeccionAutoridadCompetente getAutoridadSeleccionada() {
		return autoridadSeleccionada;
	}

	public void setAutoridadSeleccionada(ColeccionAutoridadCompetente autoridadSeleccionada) {
		this.autoridadSeleccionada = autoridadSeleccionada;
	}

	public ColeccionGestion getGestionSeleccionada() {
		return gestionSeleccionada;
	}

	public void setGestionSeleccionada(ColeccionGestion gestionSeleccionada) {
		this.gestionSeleccionada = gestionSeleccionada;
	}

	public void revisarGestion() {
		listaArchivosGestion = null;
		observacionCambioEstado = "";
		estadoGestionActual = "";
		nuevoEstadoGestion = "";
		nuevoEstadoAutoridad = "";
		comentariosAutoridad = "";
		if (gestionSeleccionada != null) {
			cargarArchivosGestion(gestionSeleccionada.getId());
			estadoGestionActual = gestionSeleccionada.getEstado().getIdentificador().getTipo();
			if (gestionSeleccionada.getComentariosRevision() != null) {
				observacionCambioEstado = gestionSeleccionada.getComentariosRevision();
			}
			if (gestionSeleccionada.getEstado() != null && gestionSeleccionada.getEstado().getIdentificador().getTipo()
					.equals(ColeccionGestion.ESTADO_EN_PROCESO)) {
				nuevoEstadoGestion = ColeccionGestion.ESTADO_EN_PROCESO;
			}
			if (gestionSeleccionada.getComentariosAutoridad() != null) {
				comentariosAutoridad = gestionSeleccionada.getComentariosAutoridad();
			}
			if (gestionSeleccionada.getEstadoAutoridad() != null) {
				nuevoEstadoAutoridad = gestionSeleccionada.getEstadoAutoridad().getIdentificador().getTipo();
			}
			if(gestionSeleccionada.getTipo().getIdentificador().getTipo().equals("RCUN")) {
				cargarRequisitos();
			}
		}
	}

	public String getComentariosGenerales() {
		return comentariosGenerales;
	}

	public void setComentariosGenerales(String comentariosGenerales) {
		this.comentariosGenerales = comentariosGenerales;
	}

	public void enviarCorreoComentarios() {
		actualizar();
		guardarHistorico("-1",comentariosGenerales);
	}

	public void guardarHistorico(String seccionFormulario, String comentarios) {
		HistoricoColeccion historicoFormulario = new HistoricoColeccion();
		historicoFormulario.setResponsable(personaActual);
		historicoFormulario.setColeccion(coleccion);
		historicoFormulario.setSeccionFormulario(seccionFormulario);
		historicoFormulario.setFecha(new Date());
		historicoFormulario.setEstado(coleccion.getEstado());
		historicoFormulario.setJustificacion(comentarios);
		servicioGeneral.guardarObjeto(historicoFormulario);
		enviarCorreoModificacionColeccion();
	}

	public void enviarCorreoModificacionColeccion() {
		String nombreEstadoColeccion = coleccion.getNombreEstado();
		Correo correo = new Correo();
		CorreoPlantilla cp = cargarPlantilla(411);
		correo.setOrigen(Correo.CORREO_HERMES_SOLICITUDES);
		correo.setAsunto(cp.getAsunto().replaceAll("<<ID>>", coleccion.getNombre()));
		correo.setCuerpo(cp.getCuerpo().replaceAll("<<COLECCION>>", coleccion.getNombre())
				.replaceAll("<<INVESTIGADOR>>", coleccion.getCuradorGeneral().getNombreCompletoMinusculas())
				.replaceAll("<<OBSERVACIONES>>", comentariosGenerales)
				.replaceAll("<<NUEVO_ESTADO>>", nombreEstadoColeccion));
		correo.adicionarDireccion(coleccion.getCuradorGeneral().getEmail());
		servicioCorreo.enviarCorreo(correo);
	}
	
	public void enviarCorreoRevisionGestionColeccion(ColeccionGestion coleccionGestion) {
		Correo correo = new Correo();
		CorreoPlantilla cp = cargarPlantilla(413);
		correo.setOrigen(Correo.CORREO_HERMES_SOLICITUDES);
		correo.setAsunto(cp.getAsunto().replaceAll("<<ID>>", coleccion.getNombre()));
		correo.setCuerpo(cp.getCuerpo().replaceAll("<<COLECCION>>", coleccion.getNombre())
				.replaceAll("<<INVESTIGADOR>>", coleccion.getCuradorGeneral().getNombreCompletoMinusculas())
				.replaceAll("<<CODIGO_GESTION>>", coleccionGestion.getId().toString())
				.replaceAll("<<TIPO_SOLICITUD>>", coleccionGestion.getTipo().getDescripcion())
				.replaceAll("<<OBSERVACIONES>>", coleccionGestion.getComentariosRevision())
				.replaceAll("<<ESTADO_GESTION>>", coleccionGestion.getEstado().getDescripcion()));
		correo.adicionarDireccion(coleccion.getCuradorGeneral().getEmail());
		servicioCorreo.enviarCorreo(correo);
	}

	public String getEstadoInicial() {
		return estadoInicial;
	}

	public void setEstadoInicial(String estadoInicial) {
		this.estadoInicial = estadoInicial;
	}

	public List<HistoricoColeccion> getHistorico() {
		return historico;
	}

	public void setHistorico(List<HistoricoColeccion> historico) {
		this.historico = historico;
	}

	public List<ColeccionGestion> getListaGestionesColeccion() {
		return listaGestionesColeccion;
	}

	public void setListaGestionesColeccion(List<ColeccionGestion> listaGestionesColeccion) {
		this.listaGestionesColeccion = listaGestionesColeccion;
	}

	public List<ArchivoGestionColeccion> getListaArchivosGestion() {
		return listaArchivosGestion;
	}

	public void setListaArchivosGestion(List<ArchivoGestionColeccion> listaArchivosGestion) {
		this.listaArchivosGestion = listaArchivosGestion;
	}

	public ArchivoGestionColeccion getArchivoGestion() {
		return archivoGestion;
	}

	public void setArchivoGestion(ArchivoGestionColeccion archivoGestion) {
		this.archivoGestion = archivoGestion;
	}

	public void descargarArchivoGestion() {
		if (archivoGestion != null) {
			descargarArchivoGestionColeccion(archivoGestion);
		}
	}

	/**
	 * Elimina archivos de la solicitud
	 */
	public void eliminarArchivoGestion() {

		listaArchivosGestion.remove(archivoGestion);
		// archivoGestion.setGestion(0L);
		archivoGestion.setFechaBorrado(new Date());
		servicioGeneral.guardarObjeto(archivoGestion);
	}

	private void cargarArchivosGestion(Long idGestion) {
		if (idGestion != null) {
			listaArchivosGestion = servicioGeneral.obtenerObjetos(ArchivoGestionColeccion.class,
					"select a from ArchivoGestionColeccion a where a.gestion.id = '" + idGestion + "'"
							+ "and a.fechaBorrado is null " + " order by a.fecha asc");
		}
	}

	public void adjuntarArchivoGestion(FileUploadEvent event) {
		archivoGestionCargado = event.getFile();
		ArchivoGestionColeccion aa = insertarArchivoGestionColeccion(gestionSeleccionada.getId(), archivoGestionCargado,
				"CO");
		if (aa != null) {
			if (listaArchivosGestion == null) {
				listaArchivosGestion = new ArrayList<ArchivoGestionColeccion>();
			}
			listaArchivosGestion.add(aa);
		}
		cargarArchivosGestion(gestionSeleccionada.getId());
	}

	public String getNuevoEstadoGestion() {
		return nuevoEstadoGestion;
	}

	public void setNuevoEstadoGestion(String nuevoEstadoGestion) {
		this.nuevoEstadoGestion = nuevoEstadoGestion;
	}

	public String getEstadoGestionActual() {
		return estadoGestionActual;
	}

	public void setEstadoGestionActual(String estadoGestionActual) {
		this.estadoGestionActual = estadoGestionActual;
	}

	public String getObservacionCambioEstado() {
		return observacionCambioEstado;
	}

	public void setObservacionCambioEstado(String observacionCambioEstado) {
		this.observacionCambioEstado = observacionCambioEstado;
	}

	public String getComentariosAutoridad() {
		return comentariosAutoridad;
	}

	public void setComentariosAutoridad(String comentariosAutoridad) {
		this.comentariosAutoridad = comentariosAutoridad;
	}

	public String getNuevoEstadoAutoridad() {
		return nuevoEstadoAutoridad;
	}

	public void setNuevoEstadoAutoridad(String nuevoEstadoAutoridad) {
		this.nuevoEstadoAutoridad = nuevoEstadoAutoridad;
	}
	
	public List<ColeccionRequisito> getListaRequisito() {
		return listaRequisito;
	}

	public void setListaRequisito(List<ColeccionRequisito> listaRequisito) {
		this.listaRequisito = listaRequisito;
	}

	public boolean isEsColeccionBiologica() {
		return esColeccionBiologica;
	}

	public void setEsColeccionBiologica(boolean esColeccionBiologica) {
		this.esColeccionBiologica = esColeccionBiologica;
	}

	class ColeccionGestionComparator implements Comparator<ColeccionGestion>{

		@Override
		public int compare(ColeccionGestion arg0, ColeccionGestion arg1) {
			return arg1.getId().compareTo(arg0.getId());
		}

	}

}
