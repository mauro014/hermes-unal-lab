/**
 * @author  Ing Juan Pablo Duque García
 */

package co.edu.unal.hermes.vista.proyectos.manejadorproyecto;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.Archivo;
import co.edu.unal.hermes.modelo.ArchivoResumen;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.CorteConvocatoria;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PersonaTramiteBiodiversidad;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.RestriccionConvocatoria;
import co.edu.unal.hermes.modelo.TipoArchivo;
import co.edu.unal.hermes.modelo.TipoModalidad;
import co.edu.unal.hermes.modelo.TipoTramiteBiodiversidad;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleProyectos;
import co.edu.unal.hermes.vista.evaluadores.ProyectoCoordinador;

public class ManejadorArchivos extends ManejadorProyecto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UploadedFile archivo;
	private TipoArchivo tipoArchivo;

	private List listaTipoArchivo;

	private SelectItem[] tipoArchivoItem;

	private List<Archivo> listaArchivos;
	private HtmlPanelGroup panelArchivos;
	private DataTable tablaArchivos;
	private boolean renderFechaFinalConvocatoria;

	// lmom
	private boolean mostrarConfirmacion = false;
	private boolean esDirector = false;

	private String titulo1;
	private String titulo2;

	private boolean esConvFichaMinima = false;
	private boolean botonEnviarProyecto = false;
	private boolean esPermisoMarco = false;
	private boolean esPermisoMarcoAsignatura = false;
	private boolean esConvocatoriaFichaMinimaHome = false;
	private boolean esSolicitudISBN = false;
	private boolean esEditableProyecto = false;
	private Convocatoria convocatoriaActual;
	private boolean convocatoriaActiva = false;
	private boolean convocatoriaDifusionConocimientoArtes = false;
	private List<Archivo> listaArchivosISBN;
	private Boolean esProyectoLaboratorios = false;
	private String mensajeArchivosAdjuntos;
	private boolean esJovenesInvestigadoresColciencias = false;
	private boolean esCorredorTecnologico = false;
	private boolean esExtensionSolidaria = false;
	private boolean esConvCP2019 = false;
	protected boolean esConvCP2019_Nuevos = false;
	private boolean oblArchivos = false;
	private boolean esConvocatoriaFortalecimientoLabs2024_M1 = false;
	private boolean esConvocatoriaFortalecimientoLabs2024_M2 = false;

	public ManejadorArchivos() {
		super();

		proyectoActual = servicioProyecto.obtenerProyecto(proyectoActual.getId(), ProyectoDAOHibernate.RUBROS);
		personaActual = (Persona) sesion.getAttribute("persona");

		cargarConvocatoriaActual();

		if ((proyectoActual.getModalidad().getTipo().getId().compareTo("CFM") == 0)
				|| (proyectoActual.getModalidad().getTipo().getId().compareTo("CEQ") == 0)
				|| (proyectoActual.getModalidad().getTipo().getId().compareTo("CMP") == 0)
				|| (proyectoActual.getModalidad().getTipo().getId().compareTo("CPU") == 0)
				|| (proyectoActual.getModalidad().getTipo().getId().compareTo("CEI") == 0)
				|| (proyectoActual.getModalidad().getTipo().getId().compareTo("CED") == 0)
				|| (proyectoActual.getModalidad().getTipo().getId().compareTo("ECO") == 0)
				|| (proyectoActual.getModalidad().getTipo().getId().compareTo("CTP") == 0)
				|| (proyectoActual.getModalidad().getTipo().getId().compareTo("CL") == 0)
				|| (proyectoActual.getModalidad().getTipo().getId().compareTo("CLN") == 0)
				|| (proyectoActual.getModalidad().getTipo().getId().compareTo("CBP") == 0)
				|| (proyectoActual.getModalidad().getTipo().getId().compareTo("CTV") == 0)
				|| (proyectoActual.getModalidad().getTipo().getId().compareTo("CPU") == 0)
				|| (proyectoActual.getModalidad().getTipo().getId().compareTo("CMP") == 0)
				|| (proyectoActual.getModalidad().getTipo().getId().compareTo("ESI") == 0)
				|| (proyectoActual.getModalidad().getTipo().getId().compareTo("ES7") == 0)
				|| (proyectoActual.getModalidad().getTipo().getId().compareTo("CSF") == 0)) {
			esConvFichaMinima = true;

			Investigador invesPry = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId());

			if (personaActual.getId().equals(invesPry.getId())) {
				esDirector = true;
			}

			if (convocatoriaActual.getId().equals(614L) || convocatoriaActual.getId().equals(615L)) {
				convocatoriaDifusionConocimientoArtes = true;
			}

		}
		
		if (convocatoriaActual.getRestriccion()!=null && convocatoriaActual.getRestriccion().getId()!=null && convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_LABS_2024_M1)) {
			esConvocatoriaFortalecimientoLabs2024_M1 = true;
		}
		
		if (convocatoriaActual.getRestriccion()!=null && convocatoriaActual.getRestriccion().getId()!=null && convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_LABS_2024_M2)) {
			esConvocatoriaFortalecimientoLabs2024_M2 = true;
		}

		if ((proyectoActual.getModalidad().getTipo().getId().compareTo("FMH") == 0
				|| proyectoActual.getModalidad().getId().equals(10L))) {
			esConvocatoriaFichaMinimaHome = true;

			Investigador invesPry = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId());

			if (personaActual.getId().equals(invesPry.getId())) {
				esDirector = true;
			}
		}

		if ((proyectoActual.getModalidad().getTipo().getId().compareTo("PM") == 0)) {
			esPermisoMarco = true;

			Investigador invesPry = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId());

			if (personaActual.getId().equals(invesPry.getId())) {
				esDirector = true;
			}
		}

		if ((proyectoActual.getModalidad().getTipo().getId().compareTo("PMA") == 0)) {
			esPermisoMarcoAsignatura = true;

			Investigador invesPry = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId());

			if (personaActual.getId().equals(invesPry.getId())) {
				esDirector = true;
			}
		}

		if (proyectoActual.getModalidad().getTipo().getId().compareTo("CJI") == 0) {
			esJovenesInvestigadoresColciencias = true;

			Investigador invesPry = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId());

			if (personaActual.getId().equals(invesPry.getId())) {
				esDirector = true;
			}
		}

		if (proyectoActual.getModalidad().getTipo().getId().compareTo("CCT") == 0) {
			esCorredorTecnologico = true;

			Investigador invesPry = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId());

			if (personaActual.getId().equals(invesPry.getId())) {
				esDirector = true;
			}
		}

		if ((proyectoActual.getModalidad().getTipo().getId().compareTo("SIS") == 0)) {
			esSolicitudISBN = true;

			Investigador invesPry = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId());

			if (invesPry != null && personaActual.getId().equals(invesPry.getId())) {
				esDirector = true;
			} else if (proyectoActual.getCreadorDocumento().equals(personaActual.getId().getTipoDocumento())
					&& proyectoActual.getCreadorId().equals(personaActual.getId().getDocumento())) {
				esDirector = true;
			}
		}

		Investigador ip = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId());
		if (ip != null && ip.getId().getTipoDocumento().equals(personaActual.getId().getTipoDocumento())
				&& ip.getId().getDocumento().equals(personaActual.getId().getDocumento())) {
			botonEnviarProyecto = true;
		}
		if (esSolicitudISBN && proyectoActual.getCreadorDocumento() != null
				&& proyectoActual.getCreadorDocumento().equals(personaActual.getId().getTipoDocumento())
				&& proyectoActual.getCreadorId().equals(personaActual.getId().getDocumento())) {
			botonEnviarProyecto = true;
		}

		if (proyectoActual.getModalidad() instanceof Convocatoria) {
			System.out.println("Extensión Solidaria");

			RestriccionConvocatoria r = ((Convocatoria) proyectoActual.getModalidad()).getRestriccion();
			if (r != null) {
				System.out.println(r.getId());
				if (r.getId().equals(RestriccionConvocatoria.CONV_EXT_SOL)
						|| r.getId().equals(RestriccionConvocatoria.EXT_SOL_2018)) {
					esExtensionSolidaria = true;
				}
				if (r.getId().equals(RestriccionConvocatoria.CONV_CP_2019)) {
					esConvCP2019 = true;
				}
				if (r.getId().equals(RestriccionConvocatoria.CONV_CP_NUEVOS_2019)) {
					esConvCP2019_Nuevos = true;
				}
			} else if (convocatoriaActual.getTipo().getId().equals("ES7")) {
				esExtensionSolidaria = true;
			}
		} else {
			System.out.println("no es Extensión Solidaria");
		}

		// dgbenitezc proyectos Laboratorios
		if (proyectoActual.getModalidad().getTipo().getId().equals(TipoModalidad.REGISTRO_PROYECTOS_LABORATORIOS)) {
			esProyectoLaboratorios = true;
			Investigador invesPry = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId());
			if (personaActual.getId().equals(invesPry.getId())) {
				esDirector = true;
			}
		}

		panelArchivos = new HtmlPanelGroup();
		tablaArchivos = new DataTable();
		listaTipoArchivo = new ArrayList();

		titulo1 = "Proyecto:";
		titulo2 = "Búsqueda de Integrantes del Proyecto";
		try {
			listaArchivos = servicioProyecto.obtenerNombresArchivos(proyectoActual);

			if (esSolicitudISBN)
				listaArchivos = servicioProyecto.obtenerNombresArchivosConTipos(proyectoActual);

			// Cuando no tiene archivos no se muestra el panel
			if (listaArchivos.size() == 0) {
				panelArchivos.setRendered(false);
			}
			// Se cargan los tipos de archivos
			// SE CARGAN LOS TIPOS DE ARCHIVO

			if (esExtensionSolidaria) {

				listaTipoArchivo = servicioGeneral
						.obtenerListaObjetos("TipoArchivo e where e.id in (4, 5, 13, 14, 15)");
				tipoArchivoItem = new SelectItem[listaTipoArchivo.size()];
				for (int i = 0; i < listaTipoArchivo.size(); i++) {
					TipoArchivo ta = (TipoArchivo) listaTipoArchivo.get(i);
					tipoArchivoItem[i] = new SelectItem(ta.getId(), ta.getNombre());
				}
				tipoArchivo = (TipoArchivo) listaTipoArchivo.get(0);

			} else if (esSolicitudISBN) {

				listaTipoArchivo = servicioGeneral.obtenerListaObjetos(
						// "TipoArchivo e where e.id in (16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28)");
						// "TipoArchivo e where e.id in (88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99) order by e.id");
						"TipoArchivo e where e.id in (104, 105, 106, 107, 108, 109, 110, 112, 113, 114, 115, 117) order by e.id");
				tipoArchivoItem = new SelectItem[listaTipoArchivo.size()];
				for (int i = 0; i < listaTipoArchivo.size(); i++) {
					TipoArchivo ta = (TipoArchivo) listaTipoArchivo.get(i);
					tipoArchivoItem[i] = new SelectItem(ta.getId(), ta.getNombre());
				}
				tipoArchivo = (TipoArchivo) listaTipoArchivo.get(0);

			} else if (esJovenesInvestigadoresColciencias) {

				listaTipoArchivo = servicioGeneral
						.obtenerListaObjetos("TipoArchivo e where e.id in (30, 31, 32, 33, 34)");
				tipoArchivoItem = new SelectItem[listaTipoArchivo.size()];
				for (int i = 0; i < listaTipoArchivo.size(); i++) {
					TipoArchivo ta = (TipoArchivo) listaTipoArchivo.get(i);
					tipoArchivoItem[i] = new SelectItem(ta.getId(), ta.getNombre());
				}
				tipoArchivo = (TipoArchivo) listaTipoArchivo.get(0);

			} else if (esCorredorTecnologico) {

				listaTipoArchivo = servicioGeneral
						.obtenerListaObjetos("TipoArchivo e where e.id in (53, 54, 55, 56, 58, 59, 60, 61, 5, 66)");
				tipoArchivoItem = new SelectItem[listaTipoArchivo.size()];
				for (int i = 0; i < listaTipoArchivo.size(); i++) {
					TipoArchivo ta = (TipoArchivo) listaTipoArchivo.get(i);
					tipoArchivoItem[i] = new SelectItem(ta.getId(), ta.getNombre());
				}
				tipoArchivo = (TipoArchivo) listaTipoArchivo.get(0);

			} else {
				listaTipoArchivo = servicioGeneral.obtenerListaObjetos("TipoArchivo");
				if (listaTipoArchivo != null && listaTipoArchivo.size() > 0) {
					tipoArchivoItem = new SelectItem[listaTipoArchivo.size()];
					for (int i = 0; i < listaTipoArchivo.size(); i++) {
						TipoArchivo ta = (TipoArchivo) listaTipoArchivo.get(i);
						tipoArchivoItem[i] = new SelectItem(ta.getId(), ta.getNombre());
					}
					tipoArchivo = (TipoArchivo) listaTipoArchivo.get(0);
				}

			}

			if (proyectoActual.getModalidad().getTipo().getId().compareTo("PN") == 0) {
				titulo1 = "Programa:";
				titulo2 = "Integrantes del programa";
			} else {
				titulo1 = "Proyecto:";
				titulo2 = "Integrantes del proyecto de investigación";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!proyectoActual.getEstadoProyecto().getId().equals("I") && proyectoActual.getPermitirModificacion() != null
				&& proyectoActual.getPermitirModificacion().equals("S")) {
			esEditableProyecto = true;
		}

	}

	public void cargarConvocatoriaActual() {

		if (proyectoActual.getId() != null) {

			List listConvocatorias = servicioGeneral.obtenerObjetos(
					"select e from Convocatoria e where e.id = " + proyectoActual.getModalidad().getId());
			if (listConvocatorias.size() > 0 && listConvocatorias != null) {
				convocatoriaActual = (Convocatoria) listConvocatorias.get(0);
			}

			boolean esConvExt = false;
			boolean esConvAcuicolaPesquero = false;

			if (convocatoriaActual != null) {
				if (convocatoriaActual.getId().compareTo(10L) == 0 || convocatoriaActual.getId().compareTo(2L) == 0) {
					esConvExt = true;
				}
				
				if (convocatoriaActual.getObligatorioSubirArchivos() != null
						&& convocatoriaActual.getObligatorioSubirArchivos() == 1) {
					setOblArchivos(true);
				}

				if (convocatoriaActual.getId().compareTo(685L) == 0
						|| convocatoriaActual.getId().compareTo(686L) == 0) {
					esConvAcuicolaPesquero = true;
				}

				ConvocatoriaPadre pad = servicioModalidad
						.obtenerConvocatoriaPadre(convocatoriaActual.getPadre().getId());
				if (ConvocatoriaPadre.PERMANENTE.equals(pad.getEsPermanente()) && !esConvExt
						&& !esConvAcuicolaPesquero) {
					InvestigadorInterno invI = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());
					if (invI != null && validadCorteConvocatoriaPermanente(convocatoriaActual,
							invI.getDependencia().getSede().getId().toString())) {
						convocatoriaActiva = true;
					} else {
						convocatoriaActiva = false;
					}
				} else {
					String estadoConvocatoria = convocatoriaActual.getEstadoConvocatoria().getId();
					Date fechaCierreConvocatoria = convocatoriaActual.getFechaFinal();
					Date fechaActual = new Date();
					boolean esFechaValida = false;

					if (fechaActual.after(fechaCierreConvocatoria)) {
						esFechaValida = false;
					} else {
						esFechaValida = true;
					}

					if (esFechaValida && (estadoConvocatoria.equals("A") || esConvExt || esConvAcuicolaPesquero)) {
						convocatoriaActiva = true;
					} else {
						convocatoriaActiva = false;
					}
				}

				if (convocatoriaActual.getMensajeArchivosAdjuntos() != null) {
					mensajeArchivosAdjuntos = convocatoriaActual.getMensajeArchivosAdjuntos();
				} else {
					mensajeArchivosAdjuntos = "";
				}

			}

		}

	}

	protected boolean validarFechaConvocatoriaActiva() {

		Date fechaCierreConvocatoria = convocatoriaActual.getFechaFinal();
		Date fechaActual = new Date();

		if (fechaActual.after(fechaCierreConvocatoria)) {
			return false;
		} else {
			return true;
		}
	}

	protected boolean validadCorteConvocatoriaPermanente(Convocatoria convocatoria, String dependenciaId) {
		if (convocatoria.getPadre() != null && convocatoria.getPadre().getEsPermanente() != null
				&& convocatoria.getPadre().getEsPermanente().equals(ConvocatoriaPadre.PERMANENTE)) {
			String sql = "from CorteConvocatoria cc " + "where cc.convocatoriaPadre.id = '"
					+ convocatoria.getPadre().getId() + "'" + " and to_date(SYSDATE,'dd/mm/yyyy') between "
					+ "to_date(cc.fechaInicial,'dd/mm/yyyy') and " + "to_date(cc.fechaFinal,'dd/mm/yyyy')"
					+ " and cc.sede.id = '" + dependenciaId + "'" + " and cc.estado <> '"
					+ CorteConvocatoria.ESTADO_BORRADO + "'";
			List<CorteConvocatoria> cortes = servicioGeneral.obtenerObjetos(CorteConvocatoria.class, sql);
			return cortes != null && cortes.size() > 0;
		} else {
			return true;
		}
	}

	public String atras() {
		return null;
	}

	public String salir() {
		return null;
	}

	public String salirGuardar() {
		return null;
	}

	private void enviarCorreoCoordinador() {
		Persona persona;
		Correo correo = new Correo();
		CorreoPlantilla cp = cargarPlantilla(171);
		correo.setAsunto(cp.getAsunto());
		//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
		String cuerpo;
		try {
			cuerpo = cp.getCuerpo()
					.replace("<<NOMBRE_CONVOCATORIA>>",
							((Convocatoria) proyectoActual.getModalidad()).getPadre().getTitulo())
					.replace("<<CODIGO_PROYECTO>>", proyectoActual.getId().toString());
		} catch (Exception e) {
			System.out.println("Error cargando plantilla.");
			cuerpo = cp.getCuerpo().replace("<<NOMBRE_CONVOCATORIA>>", "Sin identificar").replace("<<CODIGO_PROYECTO>>",
					proyectoActual.getId().toString());
		}
		correo.setCuerpo(cuerpo);
		if (sesion.getAttribute("persona") != null) {
			persona = (Persona) sesion.getAttribute("persona");
			InvestigadorInterno invInt = servicioPersona.obtenerInvestigadorInterno(persona.getId());
			if (invInt != null) {
				String facultad = invInt.getDependencia().getFacultad().getId();

				String sql = "select per from PersonaRol pr, InvestigadorInterno ii, Persona per where pr.nombre IN ('A','C') "
						+ "and ii.id.documento = pr.documento and ii.dependencia.facultad.id = '" + facultad + "' and "
						+ " ii.id.tipoDocumento = pr.tipoDocumento and pr.documento = per.id.documento and "
						+ " per.id.tipoDocumento = pr.tipoDocumento ";
				try {
					List lista = servicioGeneral.obtenerObjetos(sql);
					if (lista != null && lista.size() > 0) {
						Iterator it = lista.iterator();
						while (it.hasNext()) {
							Persona per = (Persona) it.next();
							correo.adicionarDireccion(per.getEmail());
						}
						servicioCorreo.enviarCorreo(correo);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void enviarCorreoCoordinadorConvDifusionArtes() {
		Correo correo = new Correo();
		CorreoPlantilla cp = cargarPlantilla(171);
		correo.setAsunto(cp.getAsunto());
		//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
		String cuerpo;
		try {
			cuerpo = cp.getCuerpo()
					.replace("<<NOMBRE_CONVOCATORIA>>",
							((Convocatoria) proyectoActual.getModalidad()).getPadre().getTitulo())
					.replace("<<CODIGO_PROYECTO>>", proyectoActual.getId().toString());
		} catch (Exception e) {
			System.out.println("Error cargando plantilla.");
			cuerpo = cp.getCuerpo().replace("<<NOMBRE_CONVOCATORIA>>", "Sin identificar").replace("<<CODIGO_PROYECTO>>",
					proyectoActual.getId().toString());
		}
		correo.setCuerpo(cuerpo);
		correo.adicionarDireccion(convocatoriaActual.getCriteriosCalificacionConv());
		servicioCorreo.enviarCorreo(correo);
	}

	public boolean validarCorredor() {
		boolean ret = true;

		if (proyectoActual.getMarcoTeorico() == null || proyectoActual.getMarcoTeorico().equals("")) {
			ret = false;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"por favor registre la información del planteamiento del problema.",
							"por favor registre la información del planteamiento del problema."));
		}

		if (proyectoActual.getConsideracionesEticas() == null || proyectoActual.getConsideracionesEticas().equals("")) {
			ret = false;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"por favor registre la información del marco conceptual.",
							"por favor registre la información del marco conceptual."));
		}

		if (proyectoActual.getMetodologia() == null || proyectoActual.getMetodologia().equals("")) {
			ret = false;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"por favor registre la información de la propuesta técnica y metodológica",
							"por favor registre la información de la propuesta técnica y metodológica"));

		}

		if (proyectoActual.getJustificacion() == null || proyectoActual.getJustificacion().equals("")) {
			ret = false;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"por favor registre la información de la Justificación.",
							"por favor registre la información de la Justificación."));
		}

		return ret;

	}

	public void enviarProyecto() {
		try {
			if (isOblArchivos() && listaArchivos.isEmpty()) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage("Error", new FacesMessage(FacesMessage.SEVERITY_ERROR,"El proyecto no ha sido enviado, debe adjuntar al menos 1 archivo.",""));
				return;
			}
			if ((esExtensionSolidaria || convocatoriaActual.getId().equals(915L)
					|| convocatoriaActual.getId().equals(917L) || convocatoriaActual.getPadre().getId().equals(528L)
					|| esConvCP2019 || esConvCP2019_Nuevos || isOblArchivos()) && listaArchivos.isEmpty()) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage("Error", new FacesMessage(FacesMessage.SEVERITY_ERROR,"El proyecto no ha sido enviado, debe adjuntar al menos 1 archivo.",""));
				return;
			}
			if (esConvCP2019) {
				EstadoProyecto[] ep = { new EstadoProyecto("P") };
				List<Proyecto> pry = servicioProyecto.obtenerProyectosConvocatoria(ep, proyectoActual.getModalidad());
				for (Proyecto proyecto : pry) {
					if (proyecto.getEjeTematico().equals(proyectoActual.getEjeTematico())) {
						String mensajeError = "No pueden haber más de una propuesta asociada al mismo centro de pensamiento. Por favor, cambie el centro de pensamiento en el formulario de 'Información Específica'.";
						FacesContext.getCurrentInstance().addMessage("Error",
								new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeError, ""));
						return;
					}
				}
			}
			if (convocatoriaActual.getPadre().getId().equals(524L)) {
				EstadoProyecto[] ep = { new EstadoProyecto("P") };
				int counter = 0;
				List<Proyecto> pry = servicioProyecto.obtenerProyectosConvocatoria(ep, proyectoActual.getModalidad());
				for (Proyecto proyecto : pry) {
					String query = "select #id e.id from Grupo e, Proyecto p where p.grupos.id = e.id and p.id = "
							+ proyecto.getId();
					List<Grupo> gru = servicioGeneral.obtenerObjetosLimitado(Grupo.class, query);
					for (Grupo g : gru) {
						query = "select #id e.id from Grupo e, Proyecto p where p.grupos.id = e.id and p.id = "
								+ proyectoActual.getId();
						List<Grupo> gru2 = servicioGeneral.obtenerObjetosLimitado(Grupo.class, query);
						for (Object g2 : gru2) {
							if (g.equals(g2)) {
								counter++;
							}
						}
						if(counter>=3) {
							String mensajeError = "No pueden haber más de una propuesta asociada al mismo grupo de investigación.";
							FacesContext.getCurrentInstance().addMessage("Error",
									new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeError, ""));
							return;
						}
					}
				}
			}if (convocatoriaActual.getRegistrosPorGrupo()!=null && convocatoriaActual.getRegistrosPorGrupo()!=0) {
				EstadoProyecto[] ep = { new EstadoProyecto("P") };
				int counter = 0;
				List<Proyecto> pry = servicioProyecto.obtenerProyectosConvocatoria(ep, proyectoActual.getModalidad());
				for (Proyecto proyecto : pry) {
					String query = "select #id e.id from Grupo e, Proyecto p where p.grupos.id = e.id and p.id = "
							+ proyecto.getId();
					List<Grupo> gru = servicioGeneral.obtenerObjetosLimitado(Grupo.class, query);
					for (Grupo g : gru) {
						query = "select #id e.id from Grupo e, Proyecto p where p.grupos.id = e.id and p.id = "
								+ proyectoActual.getId();
						List<Grupo> gru2 = servicioGeneral.obtenerObjetosLimitado(Grupo.class, query);
						for (Object g2 : gru2) {
							if (g.equals(g2)) {
								counter++;
							}
						}
						if(counter>=convocatoriaActual.getRegistrosPorGrupo()) {
							String mensajeError = "No puede(n) haber más de "+convocatoriaActual.getRegistrosPorGrupo()+" propuesta(s) asociada(s) al mismo grupo de investigación.";
							FacesContext.getCurrentInstance().addMessage("Error",
									new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeError, ""));
							return;
						}
					}
				}
			}
			if (proyectoActual.getModalidad().getId().equals(931L)) {
				EstadoProyecto[] ep = { new EstadoProyecto("P") };
				List<Proyecto> pry = servicioProyecto.obtenerProyectosConvocatoria(ep, proyectoActual.getModalidad());
				List<LaboratorioDetalleProyectos> listaLaboratoriosProyecto = new ArrayList<LaboratorioDetalleProyectos>();
				String hqlLab = "select #id e.id, #laboratorio e.laboratorio from LaboratorioDetalleProyectos e where e.proyecto = "
						+ proyectoActual.getId();
				listaLaboratoriosProyecto = servicioGeneral.obtenerObjetosLimitado(LaboratorioDetalleProyectos.class,
						hqlLab);
				List<LaboratorioDetalleProyectos> llab = new ArrayList<LaboratorioDetalleProyectos>();
				for (Proyecto proyecto : pry) {
					hqlLab = "select #id e.id, #laboratorio e.laboratorio from LaboratorioDetalleProyectos e where e.proyecto = "
							+ proyecto.getId();
					llab = servicioGeneral.obtenerObjetosLimitado(LaboratorioDetalleProyectos.class, hqlLab);
					for (LaboratorioDetalleProyectos l : llab) {
						for (LaboratorioDetalleProyectos l2 : listaLaboratoriosProyecto) {
							if (l2.getLaboratorio().getId().equals(l.getLaboratorio().getId())) {
								String mensajeError = "No pueden haber más de una propuesta asociada al mismo laboratorio. Por favor, cambie el laboratorio en el formulario de 'Información Específica'.";
								FacesContext.getCurrentInstance().addMessage("msgForm",
										new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeError, ""));
								return;
							}
						}
					}
				}
			}
			if (esEditableProyecto) {
				servicioProyecto.ingresarProyecto(proyectoActual);
				mostrarConfirmacion = true;
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage("confirmacion", new FacesMessage(
						"Su proyecto se ingresó satisfactoriamente con el número: " + this.proyectoActual.getId()));
				// enviarCorreoCoordinador();

			} else {

				if (esCorredorTecnologico) {
					if (validarCorredor()) {
						proyectoActual.cambiarEstadoPersona(EstadoProyecto.PROPUESTO, cargarPersonaActual());
						servicioProyecto.ingresarProyecto(proyectoActual);

						enviarCorreoCoordinadorLabObtenerLaboratorios();

						mostrarConfirmacion = true;
						FacesContext context = FacesContext.getCurrentInstance();
						context.addMessage("confirmacion",
								new FacesMessage("Su proyecto se ingresó satisfactoriamente con el número: "
										+ this.proyectoActual.getId()));
					} else {
						FacesContext context = FacesContext.getCurrentInstance();
						context.addMessage("Información", new FacesMessage(
								"Por favor verifique que la información del proyecto se encuentre registrada completamente "));
					}

				} else {
					if (validarFechaConvocatoriaActiva()) {
						proyectoActual.cambiarEstadoPersona(EstadoProyecto.PROPUESTO, cargarPersonaActual());
						servicioProyecto.ingresarProyecto(proyectoActual);
						generarHistoricoProyecto();
						mostrarConfirmacion = true;
						FacesContext context = FacesContext.getCurrentInstance();
						context.addMessage("confirmacion",
								new FacesMessage("Su proyecto se ingresó satisfactoriamente con el número: "
										+ this.proyectoActual.getId()));

						enviarCorreoCoordinadorLabObtenerLaboratorios();

						// enviarCorreoCoordinador();
						if (convocatoriaDifusionConocimientoArtes) {
							enviarCorreoCoordinadorConvDifusionArtes();
						}

						if (esConvocatoriaFichaMinimaHome) {
							enviarCorreoCoordinadorLeg();
						}

						botonEnviarProyecto = false;
					} else {
						// mensajeError("Su proyecto no ha sido enviado para
						// revisión, la fecha y hora actual son posteriores a la
						// fecha y hora de cierre de la convocatoria.");
						FacesContext context = FacesContext.getCurrentInstance();
						context.addMessage("confirmacion", new FacesMessage(
								"Su proyecto no ha sido enviado para revisión, la fecha y hora actual son posteriores a la fecha y hora de cierre de la convocatoria."));
					}

				}
			}

		} catch (Exception e) {
		}
	}

	public void enviarCorreoCoordinadorLabObtenerLaboratorios() {

		List<Laboratorio> lista = servicioProyecto.obtenerLaboratoriosPreAsociadosProyecto(proyectoActual.getId());
		for (Laboratorio laboratorio : lista) {
			enviarCorreoCoordinadorLab(laboratorio);
		}
	}

	public void enviarCorreoCoordinadorLab(Laboratorio laboratorio) {

		CorreoPlantilla correoPlantilla = servicioCorreo
				.obtenerPlantillaCorreoCompleta(CorreoPlantilla.CORREO_NOTIFICACION_PREASOCIAR_LABORATORIO_A_PROYECTO);

		Correo correo = new Correo();
		String cuerpo = correoPlantilla.getCuerpo();
		String asunto = correoPlantilla.getAsunto();

		Persona coordinador = servicioGeneral.obtenerCoordinadorLaboratorio(laboratorio.getId());
		Investigador investigador = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId());

		// Correo a Hermes
		correo.setOrigen(Correo.CORREO_HERMES);
		correo.adicionarDireccion(coordinador.getEmail());
		//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);

		asunto = asunto.replaceAll("<<ID_PROYECTO>>", proyectoActual.getId().toString());
		asunto = asunto.replaceAll("<<ID_LABORATORIO>>", laboratorio.getId().toString());
		asunto = asunto.replaceAll("<<NOMBRE_LABORATORIO>>", laboratorio.getNombre());

		cuerpo = cuerpo.replaceAll("<<ID_PROYECTO>>", proyectoActual.getId().toString());
		cuerpo = cuerpo.replaceAll("<<NOMBRE_PROYECTO>>", proyectoActual.getNombre());
		cuerpo = cuerpo.replaceAll("<<NOMBRE_DIRECTOR_PROYECTO>>", investigador.getNombreCompleto());
		cuerpo = cuerpo.replaceAll("<<EMAIL_DIRECTOR_PROYECTO>>", investigador.getEmail());

		cuerpo = cuerpo.replaceAll("<<ID_LABORATORIO>>", laboratorio.getId().toString());
		cuerpo = cuerpo.replaceAll("<<NOMBRE_LABORATORIO>>", laboratorio.getNombre());

		correo.setAsunto(asunto);
		correo.setCuerpo(cuerpo);
		servicioCorreo.enviarCorreo(correo);
	}

	public void enviarCorreoCoordinadorLeg() {

		// Correo a Hermes
		Correo correo = new Correo();
		int nPlantilla = 284;
		CorreoPlantilla cp = cargarPlantilla(nPlantilla);
		correo.setOrigen(Correo.CORREO_HERMES);
		correo.setAsunto(cp.getAsunto());
		correo.setCuerpo(cp.getCuerpo().replaceAll("<<ID>>", proyectoActual.getId().toString()));
		correo.setCuerpo(correo.getCuerpo().replaceAll("<<NOMBRE>>", proyectoActual.getNombre().toString()));

		// docente
		Investigador investigador = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId());
		if (investigador != null) {
			correo.setCuerpo(correo.getCuerpo().replaceAll("<<USUARIO>>",
					investigador.getNombre1() + " " + investigador.getApellido1()));

			// Coordinador
			Persona coordinad = servicioProyecto.obtenerCoordinadorRequisitosProyecto(proyectoActual.getId());
			if (coordinad.getEmail() != null) {
				correo.adicionarDireccion(coordinad.getEmail());
			}
			correo.setCuerpo(correo.getCuerpo().replaceAll("<<COORDINADOR>>",
					coordinad.getNombre1() + " " + coordinad.getApellido1()));

			// ver correo
			System.out
					.println("Correo al docente ******** " + investigador.getEmail() + " ****** " + correo.getCuerpo());

			// enviar correo
			correo.adicionarDireccion(Correo.CORREO_HERMES);
			servicioCorreo.enviarCorreo(correo);

		} else {

		}

	}

	public boolean validarTiposArchivos(Short idTipo) {
		boolean idTipoExiste = false;

		// String hql = "select pp from Archivo pp where pp.proyecto.id =
		// "+proyectoActual.getId();
		// List lista = servicioGeneral.obtenerObjetos(hql);

		if (listaArchivosISBN.size() > 0) {
			for (int i = 0; i < listaArchivosISBN.size(); i++) {
				Archivo arc = (Archivo) listaArchivosISBN.get(i);
				if (arc.getTipoArchivo().getId().intValue() == idTipo.intValue())
					idTipoExiste = true;
			}
		} else
			System.out.println("Lista de archivos vacia");

		return idTipoExiste;
	}

	public void enviarProyectoISBN() {
		try {

			String hql = "select pp from Archivo pp where pp.proyecto.id = " + proyectoActual.getId();
			ArrayList<Archivo> lista = (ArrayList<Archivo>) servicioGeneral.obtenerObjetos(hql);

			if (lista.size() > 0) {
				listaArchivosISBN = new ArrayList<Archivo>();
				for (int i = 0; i < lista.size(); i++) {
					Archivo arc = (Archivo) lista.get(i);
					listaArchivosISBN.add(arc);
				}
			}

			if (listaArchivosISBN != null && listaArchivosISBN.size() > 0) {
				String tieneSello = proyectoActual.getTieneSelloDigitalISBN();
				proyectoActual.setTieneSelloDigitalISBN(esNulo(tieneSello) ? "NO" : tieneSello);
				Boolean validaArchivoSello = true;
				if(!validarTiposArchivos(Short.parseShort(117+"")) && proyectoActual.getTieneSelloDigitalISBN().equals("SI")) {
					validaArchivoSello = false;
				}
				
				if (validarTiposArchivos(Short.parseShort(104+"")) &&
				  validarTiposArchivos(Short.parseShort(105+"")) &&
				  validarTiposArchivos(Short.parseShort(107+"")) &&
				  validarTiposArchivos(Short.parseShort(108+"")) &&
				  validarTiposArchivos(Short.parseShort(109+"")) &&
				  validarTiposArchivos(Short.parseShort(110+"")) &&
				  validarTiposArchivos(Short.parseShort(112+"")) &&
				  validarTiposArchivos(Short.parseShort(114+"")) &&
				  validarTiposArchivos(Short.parseShort(115+"")) &&
				  validaArchivoSello
				) {
				proyectoActual.cambiarEstadoPersona(EstadoProyecto.PROPUESTO, cargarPersonaActual());
				servicioProyecto.ingresarProyecto(proyectoActual);

				mostrarConfirmacion = true;
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage("confirmacion",
						new FacesMessage("Su solicitud se ingresó satisfactoriamente con el número: " + this.proyectoActual.getId()));
				} else {
					FacesContext.getCurrentInstance().addMessage(
						"error", new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Es necesario subir todos los archivos requeridos: "
							+ "Evaluación académica 1 | "
							+ "Evaluación académica 2 | "
							+ "Concepto editorial | "
							+ "Acta de aprobación del comité editorial debidamente firmado o de quien haga sus veces en las unidades editoras | "
							+ "Aval del Consejo de Facultad debidamente firmado o de quien haga sus veces en las unidades editoras| "
							+ "Soportes financieros del pago del ISBN (ATI o consignación) | "
							+ "Contrato de cesión de derechos patrimoniales o Declaración de titularidad de derechos de autor | "
							+ "Taco (cuerpo del texto, versión final para revisión en PDF) | "
							+ "Cubierta | "
							+ "Formato solicitud de sello editorial (solo SI requiere sello editorial)"
						, ""));
				}
			} else {
				FacesContext.getCurrentInstance().addMessage("error",
						new FacesMessage(FacesMessage.SEVERITY_FATAL, "Lista de archivos vacia", ""));
			}

			// sesion.removeAttribute("ManejadorSolicitudISBN");

		} catch (Exception e) {
			System.out.println("EXCEPTION : AL GUARDAR ISBN - " + e.getMessage());
		}
	}

	public void enviarProyectoPermisoMarco() {
		boolean valida = true;
		/* Se omite validación de archivos en el envio de proyecto para asociación a permiso marco 2024-2034
		if (listaArchivos == null || listaArchivos.size() < 1) {
			ArrayList<Boolean> opciones = proyectoActual.getOpcionesMarcoBoolean(6);
			if (opciones.size() == 6) {
				if ((Boolean) opciones.get(2)) {
					mensajeError(
							"Debe adjuntar el Formato de Solicitud de Autorización de Recolección de Especies Amenazadas, Vedadas o Endémicas.");
					valida = false;
				}
				if ((Boolean) opciones.get(3)) {
					mensajeError("Debe adjuntar el documento de consulta previa.");
					valida = false;
				}
				if ((Boolean) opciones.get(4)) {
					mensajeError(
							"Debe adjuntar el Formato de autorización de Parques Nacionales Naturales de Colombia.");
					valida = false;
				}
			}
		}
		*/

		if (valida) {
			try {
				Object[] informacionRevisionRequisitos = servicioBiodiversidad.obtenerDatosRevisionTramiteBiodiversidad(
						TipoTramiteBiodiversidad.REVISION_REQUISITOS_PM, cargarPersonaActual());
				List<PersonaTramiteBiodiversidad> personaRevision = (List<PersonaTramiteBiodiversidad>) informacionRevisionRequisitos[1];
				PersonaTramiteBiodiversidad personaRequisitos = personaRevision.get(0);

				proyectoActual.cambiarEstadoPersona(EstadoProyecto.PROPUESTO, cargarPersonaActual());
				servicioProyecto.ingresarProyecto(proyectoActual);
				mostrarConfirmacion = true;
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage("confirmacion", new FacesMessage(
						"Su proyecto se envió para revisión satisfactoriamente con el número: " + this.proyectoActual.getId()));

				String consulta = " from ProyectoCoordinador pc where pc.idProyecto = '"
						+ proyectoActual.getId().toString() + "'";
				List<ProyectoCoordinador> lista = servicioGeneral.obtenerObjetos(ProyectoCoordinador.class, consulta);
				if (!esListaVacia(lista)) {
					servicioProyecto.modificarCoordinadorEnProyecto(ProyectoDAOHibernate.UPDATE, "R",
							proyectoActual.getId().toString(), personaRequisitos.getPersonaEncargada().getId(), null,
							"R", true);
				} else {
					servicioProyecto.modificarCoordinadorEnProyecto(ProyectoDAOHibernate.INSERT, "R",
							proyectoActual.getId().toString(), personaRequisitos.getPersonaEncargada().getId(), null,
							"R", true);
				}

				CorreoPlantilla cp = cargarPlantilla(160);
				Correo correo = new Correo();
				correo.setOrigen(Correo.CORREO_HERMES);
				//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
				correo.adicionarDireccion(personaRequisitos.getPersonaEncargada().getEmail());
				String cuerpoCorreo = cp.getCuerpo();
				cuerpoCorreo = cuerpoCorreo.replaceAll("<<ID_PROYECTO>>", proyectoActual.getId().toString());
				cuerpoCorreo = cuerpoCorreo.replaceAll("<<INVESTIGADOR>>", personaActual.getNombreCompletoMinusculas());
				cuerpoCorreo = cuerpoCorreo.replaceAll("<<NOMBRE_PROYECTO>>", proyectoActual.getNombre());
				if(personaActual.getId().getDocumento().equals(proyectoActual.getResponsable().getId().getDocumento())){
					cuerpoCorreo = cuerpoCorreo.replaceAll("<<ROL>>", "investigador principal");
				}else {
					cuerpoCorreo = cuerpoCorreo.replaceAll("<<ROL>>", "participante");
				}
				correo.setAsunto(cp.getAsunto());
				correo.setCuerpo(cuerpoCorreo);
				servicioCorreo.enviarCorreo(correo);

			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public String solicitarPermisoAsignatura() {
		boolean valida = true;
		/* Se vuelve opcional el cargue de archivos permiso 2024-2034
		if (listaArchivos == null || listaArchivos.size() < 1) {
			ArrayList<Boolean> opciones = proyectoActual.getOpcionesMarcoBoolean(5);
			if (opciones.size() == 5) {
				if ((Boolean) opciones.get(2)) {
					mensajeError(
							"Debe adjuntar el Formato de Solicitud de Autorización de Recolección de Especies Amenazadas, Vedadas o Endémicas.");
					valida = false;
				}
				if ((Boolean) opciones.get(3)) {
					mensajeError("Debe adjuntar el documento de consulta previa.");
					valida = false;
				}
				if ((Boolean) opciones.get(4)) {
					mensajeError(
							"Debe adjuntar el Formato de autorización de Parques Nacionales Naturales de Colombia.");
					valida = false;
				}
			}
		}*/

		if (valida) {
			try {

				Object[] informacionSeguimientoAsignatura = servicioBiodiversidad
						.obtenerDatosRevisionTramiteBiodiversidad(TipoTramiteBiodiversidad.ASIGNATURAS_PM,
								cargarPersonaActual());
				List<PersonaTramiteBiodiversidad> personaSeguimientoAS = (List<PersonaTramiteBiodiversidad>) informacionSeguimientoAsignatura[1];
				PersonaTramiteBiodiversidad personaSeguimientoAsignatura = personaSeguimientoAS.get(0);

				proyectoActual.cambiarEstadoPersona(EstadoProyecto.PROPUESTO, cargarPersonaActual());
				servicioProyecto.ingresarProyecto(proyectoActual);

				ProyectoCoordinador proyCoordinador = new ProyectoCoordinador();
				proyCoordinador.setProyecto(proyectoActual);
				proyCoordinador.setIdProyecto(proyectoActual.getId());
				proyCoordinador.setPerId(personaSeguimientoAsignatura.getPersonaEncargada().getId().getDocumento());
				proyCoordinador.setTdoId(personaSeguimientoAsignatura.getPersonaEncargada().getId().getTipoDocumento());
				proyCoordinador.setTdoId3(servicioBiodiversidad.obtenerPersonaEncargadaBiodiversidadVicerrectoria()
						.getId().getTipoDocumento());
				proyCoordinador.setPerEvaluacion(servicioBiodiversidad
						.obtenerPersonaEncargadaBiodiversidadVicerrectoria().getId().getDocumento());
				servicioGeneral.guardarObjeto(proyCoordinador);

				super.sesion.setAttribute("idProyecto", proyectoActual.getId());
				sesion.removeAttribute("manejadorSolicitudes");
				return "solicitudesAdmProyecto";

			} catch (Exception e) {
				mensajeError(
						"Ocurrió un error en el guardado de la información. ");
				return "";
				// TODO: handle exception
			}
		}
		return "";
	}

	public String siguiente() {
		sesion.removeAttribute("proyecto");
		sesion.removeAttribute("manejadorMenuFormularios");
		borrarManejadoresInsercionProyecto();
		return "proyectoExito";
	}

	public void guardarEnlace() {
		if (this.proyectoActual.getSoportesUrl() != null && !this.proyectoActual.getSoportesUrl().equals("")) {
			servicioProyecto.ingresarProyecto(proyectoActual);
			sesion.setAttribute("proyecto", proyectoActual);
			sesion.removeAttribute("manejadorSostenibilidadProyecto");
			sesion.removeAttribute("manejadorMenuFormularios");
			borrarManejadoresInsercionProyecto();
		}
	}

	public void reporte() {

		servicioProyecto.imprimirReporteProyecto(proyectoActual, sesion, false);

	}

	protected void cargarValoresIniciales() {
	}

	public String insertarArchivoPrueba() {
		return insertarArchivoProyectoGenerico(archivo, proyectoActual, listaArchivos);
	}

	public String insertarArchivo() {
		if (archivo != null) {
			long size = archivo.getSize();               // bytes
		    long max  = convocatoriaActual.getTamanoArchivoMb() * 1048576; // megas a bytes
		    if(max == 0) {
		    	max = 3 * 1048576;
		    }
		    
			if (size <= max) {
				if (esSolicitudISBN || esJovenesInvestigadoresColciencias) {
					TipoArchivo tipoAr = new TipoArchivo();
					List listaAr = servicioGeneral.obtenerObjetoXID("TipoArchivo", tipoArchivo.getId() + "");
					tipoAr = (TipoArchivo) listaAr.get(0);

					return insertarArchivoProyectoGenericoConTipos(archivo, proyectoActual, listaArchivos, tipoAr);
				} else
					return insertarArchivoProyectoGenerico(archivo, proyectoActual, listaArchivos);
			} else {
				mensajeError("El tamaño del archivo excede el máximo permitido para la convocatoria ("+convocatoriaActual.getTamanoArchivoMb()+" Mb )");
				return "";
			}
		} else {
			mensajeError("Por favor seleccione un archivo");
			return "";
		}

	}

	public String eliminarArchivo() {
		Long id = ((ArchivoResumen) (tablaArchivos.getRowData())).getId();
		Archivo archivo = servicioProyecto.obtenerArchivo(id);
		boolean entra = false;
		boolean elimina = false;
		if (archivo != null && (archivo.getDatos() == null || archivo.getBytes().length <= 1)) {
			entra = true;
			elimina = eliminarArchivoProyectoGenerico(archivo.getId(), proyectoActual, archivo.getNombre());
		}
		if (!entra) {
			servicioGeneral.eliminarObjeto(archivo);
		} else if (elimina) {
			servicioGeneral.eliminarObjeto(archivo);
		}
		return "archivos";
	}
	
	public String eliminarArchivoISBN() {
		Long id = ((Archivo) (tablaArchivos.getRowData())).getId();
		Archivo archivo = servicioProyecto.obtenerArchivo(id);
		boolean entra = false;
		boolean elimina = false;
		if (archivo != null && (archivo.getDatos() == null || archivo.getBytes().length <= 1)) {
			entra = true;
			elimina = eliminarArchivoProyectoGenerico(archivo.getId(), proyectoActual, archivo.getNombre());
		}
		if (!entra) {
			servicioGeneral.eliminarObjeto(archivo);
		} else if (elimina) {
			servicioGeneral.eliminarObjeto(archivo);
		}
		return "archivos";
	}

	public void descargarArchivo() {
		try {
			Long id = ((ArchivoResumen) (tablaArchivos.getRowData())).getId();
			descargarArchivoProyectoGenerico(id, proyectoActual.getId());
		}catch(Exception e) {
			Long id = ((Archivo) (tablaArchivos.getRowData())).getId();
			descargarArchivoProyectoGenerico(id, proyectoActual.getId());
		}
	}
	
	public void descargarArchivoISBN() {
        Long id = ((Archivo) (tablaArchivos.getRowData())).getId();
        descargarArchivoProyectoGenerico(id, proyectoActual.getId());
    }

	public void guardarProyecto() {

		servicioGeneral.guardarObjeto(proyectoActual);

	}

	public UploadedFile getArchivo() {
		return archivo;
	}

	public void setArchivo(UploadedFile archivo) {
		this.archivo = archivo;
	}

	public HtmlPanelGroup getPanelArchivos() {
		return panelArchivos;
	}

	public void setPanelArchivos(HtmlPanelGroup panelArchivos) {
		this.panelArchivos = panelArchivos;
	}

	public List getListaArchivos() {
		return listaArchivos;
	}

	public void setListaArchivos(List listaArchivos) {
		this.listaArchivos = listaArchivos;
	}

	public DataTable getTablaArchivos() {
		return tablaArchivos;
	}

	public void setTablaArchivos(DataTable tablaArchivos) {
		this.tablaArchivos = tablaArchivos;
	}

	public List getListaTipoArchivo() {
		return listaTipoArchivo;
	}

	public void setListaTipoArchivo(List listaTipoArchivo) {
		this.listaTipoArchivo = listaTipoArchivo;
	}

	public TipoArchivo getTipoArchivo() {
		return tipoArchivo;
	}

	public void setTipoArchivo(TipoArchivo tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}

	public SelectItem[] getTipoArchivoItem() {
		return tipoArchivoItem;
	}

	public void setTipoArchivoItem(SelectItem[] tipoArchivoItem) {
		this.tipoArchivoItem = tipoArchivoItem;
	}

	public boolean isRenderFechaFinalConvocatoria() {
		if (this.fechaFinalConvocatoria != null)
			renderFechaFinalConvocatoria = true;
		else
			renderFechaFinalConvocatoria = false;
		return renderFechaFinalConvocatoria;
	}

	public void setRenderFechaFinalConvocatoria(boolean renderFechaFinalConvocatoria) {
		this.renderFechaFinalConvocatoria = renderFechaFinalConvocatoria;
	}

	public String getTitulo1() {
		return titulo1;
	}

	public void setTitulo1(String titulo1) {
		this.titulo1 = titulo1;
	}

	public String getTitulo2() {
		return titulo2;
	}

	public void setTitulo2(String titulo2) {
		this.titulo2 = titulo2;
	}

	public boolean isEsExtensionSolidaria() {
		return esExtensionSolidaria;
	}

	public void setEsExtensionSolidaria(boolean esExtensionSolidaria) {
		this.esExtensionSolidaria = esExtensionSolidaria;
	}

	public boolean isEsConvFichaMinima() {
		return esConvFichaMinima;
	}

	public void setEsConvFichaMinima(boolean esConvFichaMinima) {
		this.esConvFichaMinima = esConvFichaMinima;
	}

	public boolean isBotonEnviarProyecto() {
		return botonEnviarProyecto;
	}

	public void setBotonEnviarProyecto(boolean botonEnviarProyecto) {
		this.botonEnviarProyecto = botonEnviarProyecto;
	}

	public boolean isMostrarConfirmacion() {
		return mostrarConfirmacion;
	}

	public void setMostrarConfirmacion(boolean mostrarConfirmacion) {
		this.mostrarConfirmacion = mostrarConfirmacion;
	}

	public boolean isEsDirector() {
		return esDirector;
	}

	public void setEsDirector(boolean esDirector) {
		this.esDirector = esDirector;
	}

	public void imprimirProyectoAsociado() {
		Proyecto proyectoActual2 = servicioProyecto.obtenerProyecto(Long.parseLong(proyectoActual.getCodigoDib()),
				ProyectoDAOHibernate.INFORMACION_GENERAL);

		servicioProyecto.imprimirReporteProyecto(proyectoActual2, sesion, false);

	}

	public boolean isCheckProyectoAsociado() {
		try {
			Proyecto proyectoActual2 = servicioProyecto.obtenerProyecto(Long.parseLong(proyectoActual.getCodigoDib()),
					ProyectoDAOHibernate.INFORMACION_GENERAL);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void setEsPermisoMarco(boolean esPermisoMarco) {
		this.esPermisoMarco = esPermisoMarco;
	}

	public boolean isEsPermisoMarco() {
		return esPermisoMarco;
	}

	public boolean isEsSolicitudISBN() {
		//System.out.println("isEsSolicitudISBN "+esSolicitudISBN);
		return esSolicitudISBN;
	}

	public void setEsSolicitudISBN(boolean esSolicitudISBN) {
		this.esSolicitudISBN = esSolicitudISBN;
	}

	public List<Archivo> getListaArchivosISBN() {
		return listaArchivosISBN;
	}

	public void setListaArchivosISBN(List<Archivo> listaArchivosISBN) {
		this.listaArchivosISBN = listaArchivosISBN;
	}

	public boolean isEsConvocatoriaFichaMinimaHome() {
		return esConvocatoriaFichaMinimaHome;
	}

	public void setEsConvocatoriaFichaMinimaHome(boolean esConvocatoriaFichaMinimaHome) {
		this.esConvocatoriaFichaMinimaHome = esConvocatoriaFichaMinimaHome;
	}

	public boolean isEsJovenesInvestigadoresColciencias() {
		return esJovenesInvestigadoresColciencias;
	}

	public void setEsJovenesInvestigadoresColciencias(boolean esJovenesInvestigadoresColciencias) {
		this.esJovenesInvestigadoresColciencias = esJovenesInvestigadoresColciencias;
	}

	public boolean isEsEditableProyecto() {
		return esEditableProyecto;
	}

	public void setEsEditableProyecto(boolean esEditableProyecto) {
		this.esEditableProyecto = esEditableProyecto;
	}

	public boolean isEsCorredorTecnologico() {
		return esCorredorTecnologico;
	}

	public void setEsCorredorTecnologico(boolean esCorredorTecnologico) {
		this.esCorredorTecnologico = esCorredorTecnologico;
	}

	public Convocatoria getConvocatoriaActual() {
		return convocatoriaActual;
	}

	public void setConvocatoriaActual(Convocatoria convocatoriaActual) {
		this.convocatoriaActual = convocatoriaActual;
	}

	public boolean isConvocatoriaActiva() {
		return convocatoriaActiva;
	}

	public void setConvocatoriaActiva(boolean convocatoriaActiva) {
		this.convocatoriaActiva = convocatoriaActiva;
	}

	public boolean isConvocatoriaDifusionConocimientoArtes() {
		return convocatoriaDifusionConocimientoArtes;
	}

	public void setConvocatoriaDifusionConocimientoArtes(boolean convocatoriaDifusionConocimientoArtes) {
		this.convocatoriaDifusionConocimientoArtes = convocatoriaDifusionConocimientoArtes;
	}

	public boolean isEsPermisoMarcoAsignatura() {
		return esPermisoMarcoAsignatura;
	}

	public void setEsPermisoMarcoAsignatura(boolean esPermisoMarcoAsignatura) {
		this.esPermisoMarcoAsignatura = esPermisoMarcoAsignatura;
	}

	/**
	 * @return the esProyectoLaboratorios
	 */
	public Boolean getEsProyectoLaboratorios() {
		return esProyectoLaboratorios;
	}

	public boolean isEsConvCP2019() {
		return esConvCP2019;
	}

	public void setEsConvCP2019(boolean esConvCP2019) {
		this.esConvCP2019 = esConvCP2019;
	}

	public boolean isEsConvCP2019_Nuevos() {
		return esConvCP2019_Nuevos;
	}

	public void setEsConvCP2019_Nuevos(boolean esConvCP2019_Nuevos) {
		this.esConvCP2019_Nuevos = esConvCP2019_Nuevos;
	}

	public String getMensajeArchivosAdjuntos() {
		return mensajeArchivosAdjuntos;
	}

	public void setMensajeArchivosAdjuntos(String mensajeArchivosAdjuntos) {
		this.mensajeArchivosAdjuntos = mensajeArchivosAdjuntos;
	}

	public boolean isOblArchivos() {
		return oblArchivos;
	}

	public void setOblArchivos(boolean oblArchivos) {
		this.oblArchivos = oblArchivos;
	}

	public boolean isEsConvocatoriaFortalecimientoLabs2024_M1() {
		return esConvocatoriaFortalecimientoLabs2024_M1;
	}

	public void setEsConvocatoriaFortalecimientoLabs2024_M1(boolean esConvocatoriaFortalecimientoLabs2024_M1) {
		this.esConvocatoriaFortalecimientoLabs2024_M1 = esConvocatoriaFortalecimientoLabs2024_M1;
	}

	public boolean isEsConvocatoriaFortalecimientoLabs2024_M2() {
		return esConvocatoriaFortalecimientoLabs2024_M2;
	}

	public void setEsConvocatoriaFortalecimientoLabs2024_M2(boolean esConvocatoriaFortalecimientoLabs2024_M2) {
		this.esConvocatoriaFortalecimientoLabs2024_M2 = esConvocatoriaFortalecimientoLabs2024_M2;
	}
}
