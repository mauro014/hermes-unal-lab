package co.edu.unal.hermes.vista.proyectos;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.Actividad;
import co.edu.unal.hermes.modelo.ActividadInforme;
import co.edu.unal.hermes.modelo.ArchivoInforme;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EstadoInforme;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.ProyectoInforme;
import co.edu.unal.hermes.modelo.ResultadoInforme;
import co.edu.unal.hermes.modelo.ResultadoProyecto;
import co.edu.unal.hermes.modelo.TipoInforme;
import co.edu.unal.hermes.vista.proyectos.base.BaseManejadorRegistroInforme;

public class ManejadorRegistroInformeBancoProyectos extends BaseManejadorRegistroInforme {

    private static final long serialVersionUID = 1635661852469046341L;
    private static final String TIPO_ESTADO_POSTULACION = "116";
    private static final String OPCION_FECHA_PREVISTA_RESULTADOS = "3";
    private static final int OPCION_CARGAR = 1;
    private static final int OPCION_ACTUALIZAR = 2;
    private static final String ID_PROYECTO_SESION = "idProyecto";

    private Proyecto proyectoActual;
    private ProyectoInforme proyectoInforme;
    private ArchivoInforme archivoSeleccionado;
    private Date fechaPrevista;

    private String avanceColaborativo;
    private String entidadExterna;
    private String convocatoriaExterna;
    private String estadoPostulacion;
    private Long montoSolicitado;
    private String contribucionFormacion;

    private List<ArchivoInforme> listaArchivos;
    private List<ArchivoInforme> listaArchivosEliminados;
    private List<ActividadInforme> listaActividades;
    private List<ResultadoInforme> listaResultados;
    private List<DominioDetalle> estadosPostulacion;
    private List<FuenteFinanciacion> entidadesExternas;

    public ManejadorRegistroInformeBancoProyectos() {
        super();
        fechaEntrega = new Date();
        listaArchivos = new ArrayList<ArchivoInforme>();
        listaArchivosEliminados = new ArrayList<ArchivoInforme>();

        proyectoActual = cargarInformacionProyecto();
        proyectoInforme = cargarInforme();
        proyectoInformeActual = proyectoInforme;
        listaActividades = cargarActividadesInforme();
        listaResultados = cargarResultadosInforme();
        cargarInformacionInforme(OPCION_CARGAR);

        estadosPostulacion = cargarEstadosPostulacion();
        entidadesExternas = cargarEntidadesExternas(CONSULTA_ENTIDADES_EXTERNAS_SIN_UNAL);

        tipoAccion = (Long) sesion.getAttribute("tipoAccion");
        if (tipoAccion == null) {
            tipoAccion = (Long) sesion.getAttribute("tipoAccionBancoProyectos");
        }
        editable = tipoAccion == 2;
    }

    private ProyectoInforme cargarInforme() {
        ProyectoInforme proyectoInformeCargado = null;
        idInforme = (Long) sesion.getAttribute("idInforme");
        if (idInforme != null && idInforme > 0) {

            List<ProyectoInforme> listaProyectosInforme = servicioGeneral
                    .obtenerListaObjetosWhere(ProyectoInforme.class, " where p.id ='" + idInforme + "'");
            if (listaProyectosInforme != null && listaProyectosInforme.size() > 0) {
                proyectoInformeCargado = listaProyectosInforme.get(0);
                listaArchivos = servicioGeneral.obtenerObjetos(ArchivoInforme.class,
                        "select arch from ArchivoInforme arch " + "where (arch.estado is null or "
                                + " arch.estado <> 'B') and  arch.informe.id="
                                + proyectoInformeCargado.getId().toString());
            }
            sesion.removeAttribute("idInforme");
            sesion.removeAttribute("tipoAccion");
            sesion.removeAttribute("tipoInformeParam");
        } else {
            if (idInforme == null)
                idInforme = new Long("0");
        }
        if (proyectoInformeCargado == null) {
            proyectoInformeCargado = new ProyectoInforme();
        }
        return proyectoInformeCargado;
    }

    private void cargarInformacionInforme(int opcion) {
        if (opcion == OPCION_CARGAR) {
            if (proyectoInforme != null && proyectoInforme.getId() != null) {
                avanceColaborativo = proyectoInforme.getAvanceResultados();
                entidadExterna = proyectoInforme.getCompromisos();
                convocatoriaExterna = proyectoInforme.getConclusiones();
                contribucionFormacion = proyectoInforme.getDificultades();
                estadoPostulacion = proyectoInforme.getCalificacion();
                fechaPrevista = proyectoInforme.getFechaDesde();
                montoSolicitado = proyectoInforme.getMontoSolicitado();
            }
        } else if (opcion == OPCION_ACTUALIZAR) {
            proyectoInforme.setAvanceResultados(avanceColaborativo);
            if (proyectoInforme.getAvanceResultados() != null
                    && proyectoInforme.getAvanceResultados().length() > 3900) {
                proyectoInforme.setAvanceResultados(proyectoInforme.getAvanceResultados().substring(0, 3900));
            }
            proyectoInforme.setCompromisos(entidadExterna);
            if (proyectoInforme.getCompromisos() != null && proyectoInforme.getCompromisos().length() > 3900) {
                proyectoInforme.setCompromisos(proyectoInforme.getCompromisos().substring(0, 3900));
            }
            proyectoInforme.setConclusiones(convocatoriaExterna);
            if (proyectoInforme.getConclusiones() != null && proyectoInforme.getConclusiones().length() > 3900) {
                proyectoInforme.setConclusiones(proyectoInforme.getConclusiones().substring(0, 3900));
            }
            proyectoInforme.setDificultades(contribucionFormacion);
            if (proyectoInforme.getDificultades() != null && proyectoInforme.getDificultades().length() > 3900) {
                proyectoInforme.setDificultades(proyectoInforme.getDificultades().substring(0, 3900));
            }
            proyectoInforme.setCalificacion(estadoPostulacion);
            if (proyectoInforme.getCalificacion().equals(OPCION_FECHA_PREVISTA_RESULTADOS)) {
                proyectoInforme.setFechaDesde(fechaPrevista);
            } else {
                proyectoInforme.setFechaDesde(null);
            }
            proyectoInforme.setMontoSolicitado(montoSolicitado);
            if (proyectoInforme.getTipoInforme() == null) {
                proyectoInforme.setTipoInforme(new TipoInforme(2L));
            }
            if (proyectoInforme.getEstadoInforme() == null) {
                proyectoInforme.setEstadoInforme(new EstadoInforme(1L));
            }
            if (proyectoInforme.getProyecto() == null) {
                proyectoInforme.setProyecto(proyectoActual);
            }
            if (proyectoInforme.getFechaGeneracion() == null) {
                proyectoInforme.setFechaGeneracion(new Date());
            }

            Investigador investigadorPrincipal = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId());
            if (investigadorPrincipal.getDependencia().getId() != null) {
                proyectoInforme.setDependenciaInforme(investigadorPrincipal.getDependencia().getId());
            }
        }
    }

    private Proyecto cargarInformacionProyecto() {
        Proyecto proyecto = null;
        Long idProyecto;
        if (super.sesion.getAttribute(ID_PROYECTO_SESION) instanceof Long) {
            idProyecto = (Long) super.sesion.getAttribute(ID_PROYECTO_SESION);
        } else {
            idProyecto = Long.parseLong((String) super.sesion.getAttribute(ID_PROYECTO_SESION));
        }
        if (idProyecto != null) {
            proyecto = super.servicioProyecto.obtenerProyecto(idProyecto, ProyectoDAOHibernate.INFORMACION_GENERAL);
        }
        return proyecto;
    }

    public void guardarArchivo(FileUploadEvent event) {
        UploadedFile archivoCargar = event.getFile();
        Persona persona = (Persona) sesion.getAttribute("persona");
        ArchivoInforme ai;
        ai = insertarArchivoInformeGenerico(3821, archivoCargar, proyectoActual.getId(), persona);
        listaArchivos.add(ai);
    }

    private List<ActividadInforme> cargarActividadesInforme() {
        List<ActividadInforme> listaActividadesCargadas = null;
        if (proyectoInforme.getId() != null) {
            listaActividadesCargadas = servicioGeneral.obtenerObjetos(ActividadInforme.class,
                    "select ai from ActividadInforme ai " + "where ai.proyectoInforme.id = '"
                            + proyectoInforme.getId().toString() + "'");
        }
        if (esListaVacia(listaActividadesCargadas)) {
            return cargarActividadesDesdeProyecto();
        } else {
            return listaActividadesCargadas;
        }
    }

    private List<ResultadoInforme> cargarResultadosInforme() {
        List<ResultadoInforme> listaResultadosCargados = null;
        if (proyectoInforme.getId() != null) {
            listaResultadosCargados = servicioGeneral.obtenerObjetos(ResultadoInforme.class,
                    "select ri from ResultadoInforme ri " + "where ri.proyectoInforme.id = '"
                            + proyectoInforme.getId().toString() + "'");
        }
        if (esListaVacia(listaResultadosCargados)) {
            return cargarResultadosDesdeProyecto();
        } else {
            return listaResultadosCargados;
        }
    }

    private List<ActividadInforme> cargarActividadesDesdeProyecto() {
        List<ActividadInforme> listaActividadesCargados = new ArrayList<ActividadInforme>();
        List<Actividad> listaActividadesProyecto = servicioGeneral.obtenerObjetos(Actividad.class,
                "select a from Actividad a where " + "a.proyecto.id = '" + proyectoActual.getId().toString() + "'");
        if (!esListaVacia(listaActividadesProyecto)) {
            Iterator<Actividad> i = listaActividadesProyecto.iterator();
            while (i.hasNext()) {
                Actividad actividad = i.next();
                ActividadInforme actividadInforme = new ActividadInforme();
                actividadInforme.setActividad(actividad);
                actividadInforme.setProyectoInforme(proyectoInforme);
                listaActividadesCargados.add(actividadInforme);
            }
        }
        return listaActividadesCargados;
    }

    private List<ResultadoInforme> cargarResultadosDesdeProyecto() {
        List<ResultadoInforme> listaResultadosCargados = new ArrayList<ResultadoInforme>();
        List<ResultadoProyecto> listaResultadosProyecto = servicioGeneral.obtenerObjetos(ResultadoProyecto.class,
                "select rp from ResultadoProyecto rp where " + "rp.proyecto.id = '" + proyectoActual.getId().toString()
                        + "'");
        if (!esListaVacia(listaResultadosProyecto)) {
            Iterator<ResultadoProyecto> i = listaResultadosProyecto.iterator();
            while (i.hasNext()) {
                ResultadoProyecto resultadoProyecto = i.next();
                ResultadoInforme resultadoInforme = new ResultadoInforme();
                resultadoInforme.setResultadoProyecto(resultadoProyecto);
                resultadoInforme.setProyectoInforme(proyectoInforme);
                listaResultadosCargados.add(resultadoInforme);
            }
        }
        return listaResultadosCargados;
    }

    public List<DominioDetalle> cargarEstadosPostulacion() {
        List<DominioDetalle> listaResultadosProyecto = servicioGeneral.obtenerObjetos(DominioDetalle.class,
                "select dd from DominioDetalle dd where " + "dd.identificador.id = '" + TIPO_ESTADO_POSTULACION + "'");
        return listaResultadosProyecto;
    }

    public String guardar() {
        Boolean resultado1 = true;
        Boolean resultado2 = true;
        Boolean resultado3 = true;
        cargarInformacionInforme(OPCION_ACTUALIZAR);
        servicioGeneral.guardarObjeto(proyectoInforme);
        if (proyectoInforme.getId() != null) {
            if (!actualizarArchivosDisco()) {
                resultado1 = false;
            }
            Iterator<ActividadInforme> i = listaActividades.iterator();
            while (i.hasNext()) {
                ActividadInforme actividadInforme = i.next();
                servicioGeneral.guardarObjeto(actividadInforme);
                if (actividadInforme.getId() == null) {
                    resultado3 = false;
                }
            }
            Iterator<ResultadoInforme> j = listaResultados.iterator();
            while (j.hasNext()) {
                ResultadoInforme resultadoInforme = j.next();
                servicioGeneral.guardarObjeto(resultadoInforme);
                if (resultadoInforme.getId() == null) {
                    resultado3 = false;
                }
            }
        } else {
            resultado2 = false;
        }
        if (resultado1 && resultado2 && resultado3) {
            sesion.removeAttribute("manejadorPrincipalInforme");
            return "volverPrincipalInforme";
        } else {
            String mensaje = "";
            if (resultado1 || resultado2) {
                mensaje = "Error al guardar el informe.";
            }
            if (resultado2) {
                mensaje = "Error al guardar los archivos.";
            }
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, "");
            context.addMessage("errorGuardar", msg);
            return "";
        }
    }

    public Boolean actualizarArchivosDisco() {
        Boolean resultado = true;
        if (!esListaVacia(listaArchivosEliminados)) {
            Iterator<ArchivoInforme> i = listaArchivosEliminados.iterator();
            while (i.hasNext()) {
                ArchivoInforme archivoInforme = i.next();
                if (!eliminarArchivoDisco(archivoInforme)) {
                    resultado = false;
                }
            }
        }
        if (!esListaVacia(listaArchivos)) {
            Iterator<ArchivoInforme> i = listaArchivos.iterator();
            while (i.hasNext()) {
                ArchivoInforme archivoInforme = i.next();
                archivoInforme.setInforme(proyectoInforme);
                if (!guardarArchivo(archivoInforme)) {
                    resultado = false;
                }
            }
        }
        return resultado;
    }

    public boolean eliminarArchivoDisco(ArchivoInforme archivoInforme) {
        eliminarArchivoGenerico(ArchivoInforme.RUTA_ARCHIVO_DISCO + archivoInforme.getId());
        servicioGeneral.eliminarObjeto(archivoInforme);
        return true;

    }

    public Boolean guardarArchivo(ArchivoInforme archivoInforme) {
        try {
            servicioGeneral.guardarObjeto(archivoInforme);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void verArchivo() {
        descargarArchivoInformeGenerico(archivoSeleccionado);
    }

    public Proyecto getProyectoActual() {
        return proyectoActual;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public boolean isEditable() {
        return editable;
    }

    public List<ArchivoInforme> getListaArchivos() {
        return listaArchivos;
    }

    public void setListaArchivos(List<ArchivoInforme> listaArchivos) {
        this.listaArchivos = listaArchivos;
    }

    public ArchivoInforme getArchivoSeleccionado() {
        return archivoSeleccionado;
    }

    public void setArchivoSeleccionado(ArchivoInforme archivoSeleccionado) {
        this.archivoSeleccionado = archivoSeleccionado;
    }

    public void eliminarArchivo() {
        listaArchivosEliminados.add(archivoSeleccionado);
        listaArchivos.remove(archivoSeleccionado);
    }

    public List<ActividadInforme> getListaActividades() {
        return listaActividades;
    }

    public List<ResultadoInforme> getListaResultados() {
        return listaResultados;
    }

    public String getAvanceColaborativo() {
        return avanceColaborativo;
    }

    public void setAvanceColaborativo(String avanceColaborativo) {
        this.avanceColaborativo = avanceColaborativo;
    }

    public String getEntidadExterna() {
        return entidadExterna;
    }

    public void setEntidadExterna(String entidadExterna) {
        this.entidadExterna = entidadExterna;
    }

    public String getConvocatoriaExterna() {
        return convocatoriaExterna;
    }

    public void setConvocatoriaExterna(String convocatoriaExterna) {
        this.convocatoriaExterna = convocatoriaExterna;
    }

    public String getEstadoPostulacion() {
        return estadoPostulacion;
    }

    public void setEstadoPostulacion(String estadoPostulacion) {
        this.estadoPostulacion = estadoPostulacion;
    }

    public Date getFechaPrevista() {
        return fechaPrevista;
    }

    public void setFechaPrevista(Date fechaPrevista) {
        this.fechaPrevista = fechaPrevista;
    }

    public String getOPCION_FECHA_PREVISTA_RESULTADOS() {
        return OPCION_FECHA_PREVISTA_RESULTADOS;
    }

    public Long getMontoSolicitado() {
        return montoSolicitado;
    }

    public void setMontoSolicitado(Long montoSolicitado) {
        this.montoSolicitado = montoSolicitado;
    }

    public String getContribucionFormacion() {
        return contribucionFormacion;
    }

    public void setContribucionFormacion(String contribucionFormacion) {
        this.contribucionFormacion = contribucionFormacion;
    }

    public List<DominioDetalle> getEstadosPostulacion() {
        return estadosPostulacion;
    }

    public List<FuenteFinanciacion> getEntidadesExternas() {
        return entidadesExternas;
    }

}
