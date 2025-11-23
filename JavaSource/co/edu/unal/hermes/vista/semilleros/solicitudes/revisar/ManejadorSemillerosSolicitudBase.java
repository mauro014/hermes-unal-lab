package co.edu.unal.hermes.vista.semilleros.solicitudes.revisar;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Semillero;
import co.edu.unal.hermes.modelo.SemilleroArchivo;
import co.edu.unal.hermes.modelo.SemilleroSolicitud;
import co.edu.unal.hermes.modelo.SemilleroSolicitudArchivo;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorSemillerosSolicitudBase extends ManejadorBase {

	private static final long serialVersionUID = 1L;
	protected List<SemilleroSolicitud> listaSolicitudes;
	protected SemilleroSolicitud solicitudActual;
	protected SemilleroArchivo archivoActual;
	protected SemilleroSolicitudArchivo archivoSolActual;
	protected UploadedFile archivoSeleccionado;
	protected ArrayList<SelectItem> respuestasSolicitud;
	protected List<Semillero> semilleros;
	protected InvestigadorInterno investigadorInterno;

	public ManejadorSemillerosSolicitudBase() {
		respuestasSolicitud = new ArrayList<SelectItem>();
		solicitudActual = new SemilleroSolicitud();
		Investigador investigadorActual = servicioPersona
				.obtenerInvestigador(((Persona) sesion.getAttribute("persona")).getId());
		investigadorInterno = servicioPersona.obtenerInvestigadorInterno(investigadorActual.getId());
		listaSolicitudes = new ArrayList<SemilleroSolicitud>();
		semilleros = new ArrayList<Semillero>();
	}

	public int getTotalSolicitudes() {
		return listaSolicitudes.size();
	}

	public String reporteSemillero() {
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("idSemillero", solicitudActual.getSemillero().getId().toString());
		r.setNombreReporte("/semilleros/reporteSemillero");
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
		return "";
	}

	public Correo editarCorreo(CorreoPlantilla plantilla) {
		String cuerpo = plantilla.getCuerpo();
		cuerpo = cuerpo.replaceAll("<<NOMBRE>>", solicitudActual.getSemillero().getNombre());
		cuerpo = cuerpo.replaceAll("<<ID>>", solicitudActual.getSemillero().getId().toString());
		cuerpo = cuerpo.replaceAll("<<LIDER>>", solicitudActual.getSemillero().getLider().getNombreCompleto());
		Correo correo = new Correo();
		correo.setOrigen(Correo.CORREO_HERMES);
		correo.setAsunto(plantilla.getAsunto());
		//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
		correo.setCuerpo(cuerpo);
		return correo;
	}

	protected void generarMsg(int tipo, String mensaje) {
		switch (tipo) {
		case 1:
			FacesContext.getCurrentInstance().addMessage("msgs",
					new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, null));
			break;
		case 2:
			FacesContext.getCurrentInstance().addMessage("msgs",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, null));
			break;
		}
	}

	protected void generarMsgModal(int tipo, String mensaje) {
		switch (tipo) {
		case 1:
			FacesContext.getCurrentInstance().addMessage("msgsModal",
					new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, null));
			break;
		case 2:
			FacesContext.getCurrentInstance().addMessage("msgsModal",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, null));
			break;
		}
	}

	public void descargarArchivo() {
		try {
			descargarArchivoGenerico("HER_SEMILLERO_ARCHIVO", archivoActual.getId().toString(),
					archivoActual.getNombre());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void descargarArchivoSol() {
		try {
			descargarArchivoGenerico("HER_SEMILLERO_SOL_ARCHIVO", archivoSolActual.getId().toString(),
					archivoSolActual.getNombre());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void agregarArchivo(String tipo) {
		if (archivoSeleccionado != null) {
			if (archivoSeleccionado.getSize() <= 3145728) {
				SemilleroSolicitudArchivo sa = new SemilleroSolicitudArchivo();
				sa.setSolicitud(solicitudActual);
				sa.setNombre(archivoSeleccionado.getFileName());
				sa.setTipo(tipo);
				servicioGeneral.guardarObjeto(sa);
				cargarArchivoDisco(archivoSeleccionado, "HER_SEMILLERO_SOL_ARCHIVO", sa.getId().toString());
				solicitudActual.getArchivos().add(sa);
			} else {
				generarMsg(2, "El tamaño del archivo excede el máximo permitido (3 Mb)");
			}
		} else {
			generarMsg(2, "Por favor seleccione un archivo");
		}
	}

	public void eliminarArchivoSol() {
		Integer id = archivoSolActual.getId();
		Long subdirectorio = id / NUMERO_ARCHIVOS_CARPETA;
		String ruta = "HER_SEMILLERO_SOL_ARCHIVO" + "//" + subdirectorio.toString() + "//" + id;
		if (eliminarArchivoGenerico(ruta)) {
			solicitudActual.getArchivos().remove(archivoSolActual);
			archivoSolActual.setSolicitud(null);
			servicioGeneral.eliminarObjeto(archivoSolActual);
		}
	}
}
