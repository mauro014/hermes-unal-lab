package co.edu.unal.hermes.vista.proyectos;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.Archivo;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.CorteConvocatoria;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.TipoArchivo;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * The Class ManejadorProyectosReclamacionBase.
 *
 * @author jgcarvajalp
 */

public abstract class ManejadorProyectosReclamacionBase extends ManejadorBase {

    /** The Constant FECHA_NO_HABILITADA. */
    private static final int FECHA_NO_HABILITADA = 1;

    /** The Constant FECHA_YA_PASO. */
    private static final int FECHA_YA_PASO = 2;

    /** The Constant FECHA_VALIDA. */
    private static final int FECHA_VALIDA = 0;

    /** The Constant ERROR_NO_CONFIGURADO. */
    private static final int ERROR_NO_CONFIGURADO = -1;

    /** The Constant TIPO_RECLAMACION_REQUISITOS. */
    protected static final int TIPO_RECLAMACION_REQUISITOS = 3;

    /** The Constant TIPO_RECLAMACION_EVALUACION. */
    protected static final int TIPO_RECLAMACION_EVALUACION = 2;

    /** The proyecto actual. */
    protected Proyecto proyectoActual;

    /** The mostrar respuesta reclamacion. */
    private boolean mostrarRespuestaReclamacion = false;

    /** The mostrar panel reclamaciones. */
    private boolean mostrarPanelReclamaciones;

    /** The es consulta reclamacion. */
    private boolean esConsultaReclamacion;

    /** The estado reclamacion. */
    private String estadoReclamacion;

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4567027847231517458L;
    
    private UploadedFile archivoReclamacion;
        
    private List<Archivo> listaArchivos;
    
    private Archivo archivoSeleccionado;

    /**
     * Instantiates a new manejador proyectos reclamacion base.
     *
     * @param idSesion
     *            the id sesion
     * @param tipo
     *            the tipo
     */
    public ManejadorProyectosReclamacionBase(String idSesion) {

        Long idProyecto = (Long) sesion.getAttribute(idSesion);
        proyectoActual = servicioProyecto.obtenerProyecto(idProyecto, ProyectoDAOHibernate.DATOS_BASICOS);
        listaArchivos = servicioProyecto.obtenerNombresArchivosReclamacion(proyectoActual);
    }

    /**
     * Validar fechas reclamacion.
     *
     * @param tipo
     *            the tipo
     * @return the int
     */
    private int validarFechasReclamacion(int tipo) {

        Date fechaIniRec = new Date();
        Date fechaFinRec = new Date();

        Convocatoria convocatoria = (Convocatoria) proyectoActual.getModalidad();

        if (convocatoria.getPadre() == null) {
            return ERROR_NO_CONFIGURADO;
        }

        if (ConvocatoriaPadre.PERMANENTE.equals(convocatoria.getPadre().getEsPermanente())
                && proyectoActual.getCorteConvocatoria() != null) {

            List<CorteConvocatoria> infoCorte = servicioGeneral.obtenerObjetos(CorteConvocatoria.class,
                    "from CorteConvocatoria e where e.id = '" + proyectoActual.getCorteConvocatoria().getId() + "'");

            if (infoCorte != null && !infoCorte.isEmpty()) {
                CorteConvocatoria corConv = infoCorte.get(0);
                if (tipo == TIPO_RECLAMACION_REQUISITOS) {
                    fechaIniRec = corConv.getFechaInicioReclamacion();
                    fechaFinRec = corConv.getFechaFinalReclamacion();
                } else if (tipo == TIPO_RECLAMACION_EVALUACION) {
                    fechaIniRec = corConv.getFechaInicioReclamacionEvaluacion();
                    fechaFinRec = corConv.getFechaFinalReclamacionEvaluacion();
                }
            }
        } else {
            if (tipo == TIPO_RECLAMACION_REQUISITOS) {
                fechaIniRec = convocatoria.getFechaInicioReclamacion();
                fechaFinRec = convocatoria.getFechaFinalReclamacion();
            } else if (tipo == TIPO_RECLAMACION_EVALUACION) {
                fechaIniRec = convocatoria.getFechaInicioReclamacionEvaluacion();
                fechaFinRec = convocatoria.getFechaFinalReclamacionEvaluacion();
            }
        }

        if (fechaIniRec == null || fechaFinRec == null) {
            return ERROR_NO_CONFIGURADO;
        }

        Date fechaActual = new Date();

        if (fechaActual.before(fechaIniRec)) {
            return FECHA_NO_HABILITADA;
        }
        if (fechaActual.after(fechaFinRec)) {
            return FECHA_YA_PASO;
        }

        return FECHA_VALIDA;
    }
    
    protected void enviarCorreoReclamacion(Persona coordinador, int numeroPlantilla, String reclamacion){
        if (coordinador != null) {

            Investigador ipal = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId());
            
            if (StringUtils.isNotBlank(coordinador.getEmail())) {
                
                CorreoPlantilla correoActual = cargarPlantilla(numeroPlantilla);
                
                Correo correo = new Correo();
                correo.setOrigen(Correo.CORREO_HERMES);
                correo.adicionarDireccion(coordinador.getEmail());
                //correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
                correo.setAsunto(correoActual.getAsunto().replace("<<ID_PROYECTO>>", proyectoActual.getId().toString()));
                String cuerpoCorreo = correoActual.getCuerpo();
                cuerpoCorreo = cuerpoCorreo.replace("<<INV_PRINCIPAL>>", ipal.getNombreCompleto());
                cuerpoCorreo = cuerpoCorreo.replace("<<NOMBRE_PROYECTO>>", proyectoActual.getNombre());
                cuerpoCorreo = cuerpoCorreo.replace("<<ID_PROYECTO>>", proyectoActual.getId().toString());
                cuerpoCorreo = cuerpoCorreo.replace("<<RECLAMACION>>", reclamacion);

                correo.setCuerpo(cuerpoCorreo);
                
                servicioCorreo.enviarCorreo(correo);

                mostrarPanelReclamaciones = false;
                
                mensajeInfo("La reclamación se ha registrado correctamente en el sistema.");
            } else {
                mensajeInfo("La reclamación se ha registrado correctamente en el sistema.");
                mensajeInfo("El coordinador del proyecto no tiene el correo electrónico registrado en el sistema,"
                        + " por favor comunicarse con el coordinador del proyecto directamente.");
            }
        } else {
            mensajeError("El proyecto no tiene coordinador asignado para revisar esta reclamación o aclaración.");
        }
    }

    /**
     * Cargar datos vista.
     *
     * @param tipo
     *            the tipo
     */
    protected void cargarDatosVista(int tipo) {

        mostrarPanelReclamaciones = true;
        esConsultaReclamacion = true;
        
        String estado = "";
        
        if(tipo == TIPO_RECLAMACION_REQUISITOS) {
        	estado = proyectoActual.getEstadoReclamacion();
        }
        else if(tipo == TIPO_RECLAMACION_EVALUACION) {
        	estado = proyectoActual.getEstadoReclamacionEvaluacion();
        }

        if (estado != null) {
            if (estado.equals(Proyecto.RECLAMACION_ENVIADO)) {
                estadoReclamacion = "Enviada por el profesor";
            } else if (estado.equals(Proyecto.RECLAMACION_APROBADO)) {
                estadoReclamacion = "Aprobada";
                mostrarRespuestaReclamacion = true;
            } else if (estado.equals(Proyecto.RECLAMACION_RECHAZADO)) {
                estadoReclamacion = "Rechazada";
                mostrarRespuestaReclamacion = true;
            } else {
                esConsultaReclamacion = false;
                mostrarPanelReclamaciones = false;
                estadoReclamacion = "";
            }
        } else {
            mostrarPanelReclamaciones = false;
            esConsultaReclamacion = false;
            estadoReclamacion = "";
        }

        int codigoValidacion = validarFechasReclamacion(tipo);

        switch (codigoValidacion) {
        case ERROR_NO_CONFIGURADO:
            mensajeError("No se encuentran configuradas las fechas para las reclamaciones de la convocatoria.");
            break;
        case FECHA_NO_HABILITADA:
            mensajeError("En estos momentos no se encuentran abiertas las reclamaciones");
            break;
        case FECHA_YA_PASO:
            mensajeError("La fecha para presentar la reclamación ya se ha vencido.");
            break;
        case FECHA_VALIDA:
            if (estado == null) {
                mostrarPanelReclamaciones = true;
            }
            break;
        }
    }
    
    public String guardarArchivoReclamacion(FileUploadEvent event) {
    	archivoReclamacion = event.getFile();
    	if(archivoReclamacion != null){
    		if(archivoReclamacion.getSize() <= 3145728){
    			TipoArchivo tipoAr = new TipoArchivo();
                List listaAr = servicioGeneral.obtenerObjetoXID("TipoArchivo", ID_TIPO_ARCHIVO_RECLAMACION_REQUISITOS);
                tipoAr = (TipoArchivo) listaAr.get(0);
               return insertarArchivoProyectoGenericoConTipos(archivoReclamacion, proyectoActual, listaArchivos, tipoAr);
    		}else{
    			mensajeError("El tamaño del archivo excede el máximo permitido (3 Mb)");
    			return "";
    		}
    	}else{
    		mensajeError("Por favor seleccione un archivo");
    		return "";
    	}
    }
    
    public void eliminarArchivo() {
       Archivo archivo = servicioProyecto.obtenerArchivo(archivoSeleccionado.getId());
        boolean elimina = false;
        
        if(archivo != null){
        	elimina = eliminarArchivoProyectoGenerico(archivo.getId(), proyectoActual, archivo.getNombre());
        	
            if (elimina) {
            	listaArchivos.remove(archivo);
                servicioGeneral.eliminarObjeto(archivo);
            } 
        }
    }

    public void descargarArchivo() {
        Long id = archivoSeleccionado.getId();
        descargarArchivoProyectoGenerico(id, proyectoActual.getId());
    }

    /**
     * Enviar reclamacion.
     */
    public abstract void enviarReclamacion();

    /**
     * Atras.
     *
     * @return the string
     */
    public abstract String atras();

    /**
     * Gets the proyecto actual.
     *
     * @return the proyectoActual
     */
    public Proyecto getProyectoActual() {
        return proyectoActual;
    }

    /**
     * Checks if is mostrar respuesta reclamacion.
     *
     * @return true, if is mostrar respuesta reclamacion
     */
    public boolean isMostrarRespuestaReclamacion() {
        return mostrarRespuestaReclamacion;
    }

    /**
     * Checks if is mostrar panel reclamaciones.
     *
     * @return true, if is mostrar panel reclamaciones
     */
    public boolean isMostrarPanelReclamaciones() {
        return mostrarPanelReclamaciones;
    }

    /**
     * Checks if is es consulta reclamacion.
     *
     * @return true, if is es consulta reclamacion
     */
    public boolean isEsConsultaReclamacion() {
        return esConsultaReclamacion;
    }

    /**
     * Gets the estado reclamacion.
     *
     * @return the estado reclamacion
     */
    public String getEstadoReclamacion() {
        return estadoReclamacion;
    }

	public UploadedFile getArchivoReclamacion() {
		return archivoReclamacion;
	}

	public void setArchivoReclamacion(UploadedFile archivoReclamacion) {
		this.archivoReclamacion = archivoReclamacion;
	}

	public List<Archivo> getListaArchivos() {
		return listaArchivos;
	}

	public void setListaArchivos(List<Archivo> listaArchivos) {
		this.listaArchivos = listaArchivos;
	}

	public Archivo getArchivoSeleccionado() {
		return archivoSeleccionado;
	}

	public void setArchivoSeleccionado(Archivo archivoSeleccionado) {
		this.archivoSeleccionado = archivoSeleccionado;
	}

}
