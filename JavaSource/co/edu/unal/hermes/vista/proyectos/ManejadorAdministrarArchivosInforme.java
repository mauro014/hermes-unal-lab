/*
 * @author Mauricio Amaya Ríos
 * Fecha: Marzo 17 del 2015
 */
package co.edu.unal.hermes.vista.proyectos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.ArchivoInforme;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.ProyectoInforme;
import co.edu.unal.hermes.modelo.TipoArchivo;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * The Class ManejadorAdministrarArchivosInforme.
 */
public class ManejadorAdministrarArchivosInforme extends ManejadorBase {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 6346065861070438441L;

    /** The proyecto informe. */
    private ProyectoInforme proyectoInforme;

    /** The archivos informe. */
    private List<ArchivoInforme> archivosInforme;

    /** The archivo informe seleccionado. */
    private ArchivoInforme archivoInformeSeleccionado;

    /** The archivo cargar. */
    private UploadedFile archivoCargar;

    /** The proyecto. */
    private Proyecto proyecto;
    
    private TipoArchivo tipoArchivo;
    private List listaTipoArchivo;
	private SelectItem[] tipoArchivoItem;
	private Short idTipoArchivo;

    /**
     * Instantiates a new manejador administrar archivos informe.
     */
    public ManejadorAdministrarArchivosInforme() {
        // Se obtiene informe desde sesión
        proyectoInforme = (ProyectoInforme) sesion.getAttribute("informe");
        proyecto = (Proyecto) sesion.getAttribute("proyecto");
        sesion.removeAttribute("informe");
        sesion.removeAttribute("proyecto");

        consultarArchivosInforme();
        cargarListaTiposArchivo();
    }

    
    public void cargarListaTiposArchivo() {
    	listaTipoArchivo = new ArrayList();
    	try {
		listaTipoArchivo = servicioGeneral
				.obtenerListaObjetos("TipoArchivo e where e.parametro = 'INFORMES_PRY' order by e.id");
		tipoArchivoItem = new SelectItem[listaTipoArchivo.size()];
		for (int i = 0; i < listaTipoArchivo.size(); i++) {
			TipoArchivo ta = (TipoArchivo) listaTipoArchivo.get(i);
			tipoArchivoItem[i] = new SelectItem(ta.getId(), ta.getNombre());
		}
		tipoArchivo = (TipoArchivo) listaTipoArchivo.get(0);
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    /**
     * Consultar archivos informe.
     */
    // Se consultan los archivos informe desde base de datos
    public void consultarArchivosInforme() {
        archivosInforme = servicioGeneral.obtenerObjetos(ArchivoInforme.class,
                "select arch from ArchivoInforme arch " + "where (arch.estado is null or " + " arch.estado <> 'B') and "
                        + "arch.informe.id=" + proyectoInforme.getId());
    }

    /**
     * Gets the proyecto informe.
     *
     * @return the proyecto informe
     */
    public ProyectoInforme getProyectoInforme() {
        return proyectoInforme;
    }

    /**
     * Gets the archivos informe.
     *
     * @return the archivos informe
     */
    public List<ArchivoInforme> getArchivosInforme() {
        return archivosInforme;
    }

    /**
     * Descargar archivo.
     */
    public void descargarArchivo() {
        descargarArchivoInformeGenerico(archivoInformeSeleccionado);
    }

    /**
     * Gets the archivo informe seleccionado.
     *
     * @return the archivo informe seleccionado
     */
    public ArchivoInforme getArchivoInformeSeleccionado() {
        return archivoInformeSeleccionado;
    }

    /**
     * Sets the archivo informe seleccionado.
     *
     * @param archivoInformeSeleccionado
     *            the new archivo informe seleccionado
     */
    public void setArchivoInformeSeleccionado(ArchivoInforme archivoInformeSeleccionado) {
        this.archivoInformeSeleccionado = archivoInformeSeleccionado;
    }

    /**
     * Guardar archivo.
     *
     * @param event
     *            the event
     */
    public void guardarArchivo(FileUploadEvent event) {
        archivoCargar = event.getFile();
        ArchivoInforme ai;
        Persona persona = (Persona) sesion.getAttribute("persona");
		try {
			if (archivoCargar != null) {
				if (archivoCargar.getSize() <= ManejadorBase.MAXIMO_TAMANO_ARCHIVOS) {
						TipoArchivo tipoAr = new TipoArchivo();
						List listaAr = servicioGeneral.obtenerObjetoXID("TipoArchivo", idTipoArchivo + "");
						tipoAr = (TipoArchivo) listaAr.get(0);
						 ai = insertarArchivoInformeGenericoConTipos(proyectoInforme.getId(), archivoCargar, proyecto.getId(), persona, tipoAr);
						 idTipoArchivo = 0;
						 if (ai == null) {
					            FacesContext context = FacesContext.getCurrentInstance();
					            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					                    "Ha ocurrido un erro al cargar el archivo.", "");
					            context.addMessage("datosGuardados", msg);
					        }
				} else {
					mensajeError("El tamaño del archivo excede el máximo permitido");
				}
			} else {
				mensajeError("Por favor seleccione un archivo");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        
        
        consultarArchivosInforme();
    }

    /**
     * Gets the proyecto.
     *
     * @return the proyecto
     */
    public Proyecto getProyecto() {
        return proyecto;
    }

    /**
     * Eliminar archivo informe.
     */
    public void eliminarArchivoInforme() {
        if (archivoInformeSeleccionado != null) {
            Persona responsableEliminacion = (Persona) sesion.getAttribute("persona");
            List<ArchivoInforme> archivosInformeTemporal = servicioGeneral.obtenerObjetos(ArchivoInforme.class,
                    "from ArchivoInforme where id = '" + archivoInformeSeleccionado.getId() + "'");
            if (!esListaVacia(archivosInformeTemporal)) {
                ArchivoInforme archivoInforme = archivosInformeTemporal.get(0);
                archivoInforme.setEstado(ArchivoInforme.BORRADO);
                archivoInforme.setFechaEliminacion(new Date());
                archivoInforme.setResponsableEliminacion(responsableEliminacion);
                servicioGeneral.guardarObjeto(archivoInforme);
                consultarArchivosInforme();
            }
        }
    }

	public TipoArchivo getTipoArchivo() {
		return tipoArchivo;
	}

	public void setTipoArchivo(TipoArchivo tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}

	public List getListaTipoArchivo() {
		return listaTipoArchivo;
	}

	public void setListaTipoArchivo(List listaTipoArchivo) {
		this.listaTipoArchivo = listaTipoArchivo;
	}

	public SelectItem[] getTipoArchivoItem() {
		return tipoArchivoItem;
	}

	public void setTipoArchivoItem(SelectItem[] tipoArchivoItem) {
		this.tipoArchivoItem = tipoArchivoItem;
	}


	public Short getIdTipoArchivo() {
		return idTipoArchivo;
	}


	public void setIdTipoArchivo(Short idTipoArchivo) {
		this.idTipoArchivo = idTipoArchivo;
	}

}
