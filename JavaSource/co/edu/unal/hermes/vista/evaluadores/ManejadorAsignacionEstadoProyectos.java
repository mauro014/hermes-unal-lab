package co.edu.unal.hermes.vista.evaluadores;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.EstadoInforme;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.HistoricoEstadoProyecto;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.ProyectoInforme;
import co.edu.unal.hermes.modelo.TipoCarta;
import co.edu.unal.hermes.modelo.TipoInforme;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * The Class ManejadorAsignacionEstadoProyectos.
 */
public class ManejadorAsignacionEstadoProyectos extends ManejadorBase {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1766645428748923330L;
    
    /** The id proyecto. */
    private String idProyecto;
    
    /** The lista proyectos coordinador. */
    private List<ProyectoCoordinador> listaProyectosCoordinador;
    
    /** The estados item. */
    private SelectItem[] estadosItem;
    
    /** The mostrar tabla. */
    private boolean mostrarTabla;
    
    /** The mensaje asignacion id. */
    private String mensajeAsignacionId;
    
    /** The mensaje guardar. */
    private String mensajeGuardar;
    
    /** The mostrar confirmacion. */
    private boolean mostrarConfirmacion;
    
    /** The habilitar guardar. */
    private boolean habilitarGuardar;

    /**
     * Instantiates a new manejador asignacion estado proyectos.
     */
    public ManejadorAsignacionEstadoProyectos() {
        personaActual = (Persona) sesion.getAttribute("persona");
        listaProyectosCoordinador = new ArrayList<ProyectoCoordinador>();
        mostrarConfirmacion = false;
        mostrarTabla = false;
        cargarEstados();
    }

    /**
     * Buscar proyectosx id.
     */
    public void buscarProyectosxId() {
        mensajeGuardar = "";
        mostrarTabla = false;
        mostrarConfirmacion = false;
        mensajeAsignacionId = "";
        listaProyectosCoordinador.clear();
        habilitarGuardar = false;

        // Se valida que el valor ingresado sea un Long
        Long idProyectoLong;
        try {
            idProyectoLong = Long.parseLong(idProyecto);
        } catch (NumberFormatException nfe) {
            idProyectoLong = 0L;
        }

        List<ProyectoCoordinador> proyectosCoordinadorEncontrados = servicioProyecto
                .obtenerProyectosCoordinadorxIdProyecto(idProyectoLong.toString());
        // Se recorren todos el listado de proyectos para validar si es
        // coordinador de seguimiento.
        if (proyectosCoordinadorEncontrados != null && proyectosCoordinadorEncontrados.size() > 0) {
            for (int i = 0; i < proyectosCoordinadorEncontrados.size(); i++) {
                ProyectoCoordinador p = proyectosCoordinadorEncontrados.get(i);
                String perId = p.getPerId();
                // Se valida si es seguimiento
                if (personaActual.getId().getDocumento().equals(perId)) {
                    p.setEsCoordinadorSeguimiento(true);
                    if (p.getEstadoProyecto().equals(EstadoProyecto.CANCELADO)
                            || p.getEstadoProyecto().equals(EstadoProyecto.FINALIZADO)
                            || p.getEstadoProyecto().equals(EstadoProyecto.POR_FINALIZAR)
                            || p.getEstadoProyecto().equals(EstadoProyecto.SUSPENDIDO)
                            || p.getEstadoProyecto().equals(EstadoProyecto.ACTIVO)
                            || p.getEstadoProyecto().equals(EstadoProyecto.BANCO_FINANCIABLE)) {
                        habilitarGuardar = true;
                    }
                    listaProyectosCoordinador.add(p);
                }
            }
        }
        if (listaProyectosCoordinador != null && listaProyectosCoordinador.size() > 0) {
            ProyectoCoordinador proyectoCoordinador = listaProyectosCoordinador.get(0);
            cargarEstados(proyectoCoordinador);
            mostrarTabla = true;
        } else {
            mostrarTabla = false;
            mensajeAsignacionId = "No se encontraron proyecto asociados";
        }
    }

    /**
     * Gets the mensaje asignacion id.
     *
     * @return the mensaje asignacion id
     */
    public String getMensajeAsignacionId() {
        return mensajeAsignacionId;
    }

    /**
     * Checks if is mostrar tabla.
     *
     * @return true, if is mostrar tabla
     */
    public boolean isMostrarTabla() {
        return mostrarTabla;
    }

    /**
     * Cargar estados.
     */
    private void cargarEstados() {
        cargarEstados(null);
    }

    /**
     * Cargar estados.
     *
     * @param modalidad the modalidad
     */
    // Solo se cargan los estados habilitados para cambio.
    private void cargarEstados(ProyectoCoordinador pry) {
        String estadosAdicionales = "";
       
        if (pry != null) {
        	 Long modalidad = pry.getProyecto().getModalidad().getId();
            if (modalidad.equals(10L)) {
                estadosAdicionales = ",'" + EstadoProyecto.POR_FINALIZAR + "'";
            }
        }
        List<EstadoProyecto> listaEstados = servicioGeneral.obtenerObjetos(EstadoProyecto.class,
                "from EstadoProyecto e " + "where e.id in ('" + EstadoProyecto.CANCELADO + "','"
                        + EstadoProyecto.FINALIZADO + "','" + EstadoProyecto.SUSPENDIDO + "','" + EstadoProyecto.ACTIVO
                        + "','" + EstadoProyecto.APROBADO + "'" + estadosAdicionales + ")");
        
        
        if(pry!=null && pry.getEstadoProyecto().equals(EstadoProyecto.BANCO_FINANCIABLE)) {
        	listaEstados.clear();
        	listaEstados = servicioGeneral.obtenerObjetos(EstadoProyecto.class,
                    "from EstadoProyecto e " + "where e.id in ('" + EstadoProyecto.NO_APROBADO
                            + "','" + EstadoProyecto.APROBADO + "')");
        }
        
        if (listaEstados != null) {
            estadosItem = new SelectItem[listaEstados.size()];
            for (int i = 0; i < listaEstados.size(); i++) {
                EstadoProyecto estado = (EstadoProyecto) listaEstados.get(i);
                estadosItem[i] = new SelectItem(estado.getId(), estado.getNombre());
            }
        }
    }

    /**
     * Validar finalizado.
     *
     * @param estadoProyecto the estado proyecto
     * @param ipProyecto the ip proyecto
     * @return true, if successful
     */
    public boolean validarFinalizado(String estadoProyecto, Long ipProyecto) {
        if (estadoProyecto != null && (estadoProyecto.equals(EstadoProyecto.FINALIZADO)
                || estadoProyecto.equals(EstadoProyecto.POR_FINALIZAR))) {
            List<ProyectoInforme> informes = servicioGeneral.obtenerObjetosLimitado(ProyectoInforme.class,
                    "select #id pi.id from" + " ProyectoInforme pi " + "where pi.proyecto.id = '" + ipProyecto + "'"
                            + "and pi.estadoInforme.id in ('" + EstadoInforme.ACEPTADO_DIRECCION + "'," + "'"
                            + EstadoInforme.ACEPTADO_FACULTAD + "')" + "and pi.tipoInforme.id='"
                            + TipoInforme.INFORME_FINAL + "'");
            if (informes != null && informes.size() > 0 && proyectoTieneCarta(ipProyecto, TipoCarta.FINALIZACION)) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * Guardar estados proyecto.
     */
    public void guardarEstadosProyecto() {

        mensajeGuardar = "";
        try {
            for (int i = 0; i < listaProyectosCoordinador.size(); i++) {
                ProyectoCoordinador pc = (ProyectoCoordinador) listaProyectosCoordinador.get(i);
                if (pc.getIdProyecto() != null) {
                    if (pc.getEstadoProyectoAnterior().equals(EstadoProyecto.ACTIVO)
                            && pc.getEstadoProyecto().equals(EstadoProyecto.APROBADO)
                            && proyectoTieneCarta(pc.getIdProyecto(), TipoCarta.INICIO)) {
                        mensajeGuardar = "Para realizar el cambio de estado a aprobado, debe eliminar la carta de inicio del mismo.";
                        mostrarConfirmacion = false;
                    } else if (!pc.getJustificacion().trim().equals("")) {
                        if (validarFinalizado(pc.getEstadoProyecto(), pc.getIdProyecto())) {
                            if (pc.getEstadoProyecto() != null) {
                            	guardarEstadoBD(pc);
                                buscarProyectosxId();
                            }
                            mostrarConfirmacion = true;
                        } else if (pc.getEstadoProyecto().equals(EstadoProyecto.POR_FINALIZAR)){
                        	guardarEstadoBD(pc);
                        	buscarProyectosxId();
                        	mostrarConfirmacion = true;
                        } else {
                            mensajeGuardar = "Para realizar el cambio de estado a finalizado debe existir un informe final aceptado asociado al proyecto. " +
                            		"Asimismo el proyecto debe tener una acta o resolución de finalización asociada.";
                            mostrarConfirmacion = false;
                        }
                    } else if (pc.getJustificacion().trim().equals("")) {
                        mensajeGuardar = "Debe ingresar la justificación.";
                        mostrarConfirmacion = false;
                    }
                }
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }
    
    public void guardarEstadoBD(ProyectoCoordinador pc){
    	String sql = "update HER_PROYECTO set EPR_ID = '" + pc.getEstadoProyecto()
                + "' where pry_id = '" + String.valueOf(pc.getIdProyecto() + "'");
        HistoricoEstadoProyecto hepry = new HistoricoEstadoProyecto();
        Date fechaHoy = new Date();
        EstadoProyecto ep = new EstadoProyecto();
        ep.setId(pc.getEstadoProyecto());
        hepry.setEstadoProyecto(ep);
        Proyecto proyecto = new Proyecto();
        proyecto.setId(pc.idProyecto);
        hepry.setProyecto(proyecto);
        hepry.setFecha(fechaHoy);
        hepry.setResponsable(cargarPersonaActual());
        hepry.setJustificacion(pc.getJustificacion());
        try {
            servicioGeneral.eliminar(sql);
            servicioGeneral.guardarObjeto(hepry);
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the estados item.
     *
     * @return the estados item
     */
    public SelectItem[] getEstadosItem() {
        return estadosItem;
    }

    /**
     * Gets the lista proyectos coordinador.
     *
     * @return the lista proyectos coordinador
     */
    public List<ProyectoCoordinador> getListaProyectosCoordinador() {
        return listaProyectosCoordinador;
    }

    /**
     * Sets the id proyecto.
     *
     * @param idProyecto the new id proyecto
     */
    public void setIdProyecto(String idProyecto) {
        this.idProyecto = idProyecto;
    }

    /**
     * Gets the id proyecto.
     *
     * @return the id proyecto
     */
    public String getIdProyecto() {
        return idProyecto;
    }

    /**
     * Checks if is mostrar confirmacion.
     *
     * @return true, if is mostrar confirmacion
     */
    public boolean isMostrarConfirmacion() {
        return mostrarConfirmacion;
    }

    /**
     * Gets the mensaje guardar.
     *
     * @return the mensaje guardar
     */
    public String getMensajeGuardar() {
        return mensajeGuardar;
    }

    /**
     * Checks if is habilitar guardar.
     *
     * @return true, if is habilitar guardar
     */
    public boolean isHabilitarGuardar() {
        return habilitarGuardar;
    }

}