/*
 * Created on 14-dic-2005
 */
package co.edu.unal.hermes.vista.proyectos;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import co.edu.unal.hermes.modelo.Contrapartida;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.EstadoConvocatoria;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.JornadaDocente;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.RestriccionConvocatoria;

/**
 * The Class ProyectoVista.
 *
 * @author jpduqueg
 * @modified Rodrigo Gallo
 */
public class ProyectoVista implements Comparable<ProyectoVista> {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3179384613794010779L;
    
    /** The id. */
    private Long id;
    
    /** The nombre modalidad. */
    private String nombreModalidad;
    
    /** The codigo quipu. */
    private String codigoQuipu;
    
    /** The codigo dib. */
    private String codigoDib;
    
    /** The tipo investigador actual. */
    private String tipoInvestigadorActual;
    
    /** The tipo convocatoria. */
    private String tipoConvocatoria;
    
    /** The mostrar boton migrar. */
    private boolean mostrarBotonMigrar = false;
    
    /** The estado convocatoria. */
    private boolean estadoConvocatoria = false;
    
    /** The permitir modificacion convocatoria. */
    private String permitirModificacionConvocatoria;
    
    /** The restriccion convocatoria. */
    private RestriccionConvocatoria restriccionConvocatoria;
    
    /** The nombre. */
    private String nombre;
    
    /** The fase. */
    private Integer fase;
    
    /** The tiene codigo QUIPU. */
    private boolean tieneCodigoQUIPU;
    
    /** The mensaje estado. */
    private String mensajeEstado = "";
    
    /** The completo. */
    private boolean completo = false;
    
    /** The es principal. */
    private boolean esPrincipal = false;
    
    /** The es estudiante lider. */
    private boolean esEstudianteLider = false;
    
    /** The es carta inicio. */
    private boolean esCartaInicio = false;
    
    /** The es carta fin. */
    private boolean esCartaFin = false;
    
    /** The es carta prorroga. */
    private boolean esCartaProrroga = false;
    
    /** The es modalidad alianzas. */
    private boolean esModalidadAlianzas = false;
    
    /** The permitir modificacion. */
    private String permitirModificacion;
    
    /** The convocatoria padre. */
    private String convocatoriaPadre;
    
    /** The estado proyecto. */
    private String estadoProyecto;
    
    /** The estado proyecto id. */
    private String estadoProyectoId;
    
    /** The entidad financiadora. */
    private String entidadFinanciadora;
    
    /** The monto recursos. */
    private String montoRecursos;
    
    /** The nombre convocatoria padre. */
    private String nombreConvocatoriaPadre;
    
    /** The responsable. */
    private Investigador responsable;
    
    /** The es coordinador. */
    private boolean esCoordinador = false;
    
    /** The es unidad. */
    private boolean esUnidad = false;
    
    /** The nombre estado proyecto. */
    private String nombreEstadoProyecto;
    
    /** The es articulo. */
    private Boolean esArticulo = false;
    
    /** The es ficha minima. */
    private Boolean esFichaMinima = false;
    
    /** The es convocatoria externa. */
    private Boolean esConvocatoriaExterna = false;
    
    /** The es convocatoria permanente. */
    private Boolean esConvocatoriaPermanente = false;
    
    /** The habilitar informes estudiante. */
    private String habilitarInformesEstudiante;
    
    /** The proyecto padre. */
    private Long proyectoPadre;
    
    /** The habilitar informes tutor. */
    private String habilitarInformesTutor;
    
    /** The es tutor. */
    private boolean esTutor = false;
    
    /** The avales proyecto. */
    private List avalesProyecto;
    
    /** The es jornada docente. */
    private boolean esJornadaDocente;
    
    /** The es pry contrapartida. */
    private boolean esPryContrapartida;
    
    /** The tipo actividad. */
    private String tipoActividad;
    
    /** The lista archivos. */
    private List listaArchivos;
    
    /** The mostrar archivos. */
    private boolean mostrarArchivos;
    
    /** The es permiso marco. */
    private boolean esPermisoMarco;
    
    /** The es permiso marco asignatura. */
    private boolean esPermisoMarcoAsignatura;
    
    /** The fecha tentativa inicio. */
    private Date fechaTentativaInicio;
    
    private Date fechaFinalizacionVista;
    
    /** The duracion. */
    private int duracion;
    
    /** The duracion dias acumulada. */
    private Integer duracionDiasAcumulada;
    
    /** The duracion acumulada. */
    private Integer duracionAcumulada;
    
    /** The compromisos pendientes. */
    private String compromisosPendientes;
    
    /** The es contrato acceso biodiversidad. */
    private boolean esContratoAccesoBiodiversidad;
    
    /** The estado reclamacion. */
    private String estadoReclamacion;
    
    /** The estado reclamacion eva. */
    private String estadoReclamacionEva;
    
    /** The es estudiante lider. */
    private boolean esAsistenteLider = false;
    
    private boolean tieneEstudiantesSinDetalle = false;
    
    private String documentoCreador;
    
    private boolean editaSoloCreador = false;
    private boolean editaPrincipal=true;
    
    private boolean convocatoriaEditaCreador = false;
    

    /**
     * Asignar valores.
     *
     * @param proyecto the proyecto
     */
    public void asignarValores(Proyecto proyecto) {
        if (proyecto != null) {
            id = proyecto.getId();
            nombre = proyecto.getNombre();
            fase = proyecto.getFase();
            responsable = proyecto.getResponsable();
            tipoActividad = proyecto.getTipoActividad();
            fechaTentativaInicio = proyecto.getFechaTentativaInicio();
            documentoCreador = proyecto.getCreadorId();
            if (proyecto.getDuracion() != null) {
                duracion = proyecto.getDuracion();
            } else {
                duracion = 0;
            }
            duracionDiasAcumulada = proyecto.getDuracionDiasAcumulada();
            duracionAcumulada = proyecto.getDuracionAcumulada();
    		if (fechaTentativaInicio != null) {
    			fechaFinalizacionVista = new Date(fechaTentativaInicio.getTime());
    			if(duracionAcumulada != null && duracionAcumulada > 0){
    				fechaFinalizacionVista.setMonth(fechaFinalizacionVista.getMonth() + duracionAcumulada);
                    // Se agregan los días
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(fechaFinalizacionVista);
                    calendar.add(Calendar.DAY_OF_YEAR, duracionDiasAcumulada);
                    fechaFinalizacionVista = calendar.getTime();
    			}else{
    				fechaFinalizacionVista.setMonth(fechaFinalizacionVista.getMonth() + duracion);
    			}
            }
            nombreEstadoProyecto = proyecto.getEstadoProyecto().getNombre();

            if (proyecto.getModalidad().getId().equals(Convocatoria.ID_CONVOCATORIA_ALIANZAS)) {
                esModalidadAlianzas = true;
            }
            this.permitirModificacionConvocatoria = proyecto.getPermitirModificacion();

            if (proyecto.getModalidad() instanceof Convocatoria) {
                Convocatoria convocatoria = (Convocatoria) proyecto.getModalidad();
                if (convocatoria != null && convocatoria.getEstadoConvocatoria() != null
                        && convocatoria.getEstadoConvocatoria().getId() != null
                        && convocatoria.getEstadoConvocatoria().getId().equals(EstadoConvocatoria.ACTIVA)) {
                    this.setEstadoConvocatoria(true);
                }
                try {
                    nombreModalidad = convocatoria.getTitulo();
                    nombreConvocatoriaPadre = convocatoria.getPadre().getTitulo();
                } catch (NullPointerException npe) {
                    nombreModalidad = "Sin modalidad";
                    nombreConvocatoriaPadre = "Sin convocatoria";
                }
                tipoConvocatoria = convocatoria.getTipo().getId();
                this.restriccionConvocatoria = convocatoria.getRestriccion();

                habilitarInformesEstudiante = convocatoria.getHabilitarModEstudiantes();

                setHabilitarInformesTutor(convocatoria.getHabilitarModTutor());

            }
            if (proyecto.getModalidad() instanceof Contrapartida) {
                Contrapartida convocatoria = (Contrapartida) proyecto.getModalidad();
                if (convocatoria.getEstadoContrapartida().getId().equals(EstadoConvocatoria.ACTIVA)) {
                    this.setEstadoConvocatoria(true);
                }
                this.setTipoConvocatoria("P");
            }
            if (proyecto.getModalidad() instanceof JornadaDocente) {
                JornadaDocente convocatoria = (JornadaDocente) proyecto.getModalidad();
                if (convocatoria.getEstadoJornadaDocente().getId().equals(EstadoConvocatoria.ACTIVA)) {
                    this.setEstadoConvocatoria(true);
                }
                this.setTipoConvocatoria("J");
            }
            if (proyecto.getModalidad().getTipo().getId().equals("PM")) {
                esPermisoMarco = true;
            }
            if (proyecto.getModalidad().getTipo().getId().equals("PMA")) {
                esPermisoMarcoAsignatura = true;
            }
            if (proyecto.getModalidad().getTipo().getId().equals("CAR")) {
                esContratoAccesoBiodiversidad = true;
            }
            if (proyecto.getModalidad().getId().toString().equals("2")
                    || proyecto.getModalidad().getId().toString().equals("10")) {
                esFichaMinima = true;
            }
            if (proyecto.getModalidad().getId().toString().equals("10")) {
                esConvocatoriaExterna = true;
            }
            if (proyecto.getCodigoQuipu() != null && proyecto.getCodigoQuipu().length() > 8) {
                tieneCodigoQUIPU = true;
                codigoQuipu = proyecto.getCodigoQuipu();
            } else
                tieneCodigoQUIPU = false;

            esJornadaDocente = false;
            if (proyecto.getEsJornadaDocente() != null && proyecto.getEsJornadaDocente().equals("Y")) {
                esJornadaDocente = true;
            }
            esPryContrapartida = false;
            if (proyecto.getEsPryContrapartida() != null && proyecto.getEsPryContrapartida().equals("Y")) {
                esPryContrapartida = true;
            }
            codigoDib = proyecto.getCodigoDib();

            if (fase == null) {
                mensajeEstado = "Sin estado";
            } else {

                if (proyecto.getEstadoProyecto().getId().equals(EstadoProyecto.PROPUESTO)
                        || proyecto.getEstadoProyecto().getId().equals(EstadoProyecto.APROBADO)) {
                    completo = true;
                }
                if (fase.intValue() < 3) {
                    mensajeEstado += "lineas,";
                }
                if (fase.intValue() < 4) {
                    mensajeEstado += "objetivos,";
                }
                if (fase.intValue() < 5) {
                    mensajeEstado += "informacion especifica,";
                }
                if (fase.intValue() < 6) {
                    mensajeEstado += "actividades,";
                }
                if (fase.intValue() < 7) {
                    mensajeEstado += "bibliografías,";
                }
                if (fase.intValue() < 9) {
                    mensajeEstado += "información financiera,";
                } else {
                    completo = true;
                }

            }
            if (proyecto.getModalidad().getTipo().getId().equals("CFM")) {
                esConvocatoriaPermanente = true;
            }

            if (proyecto.getProyectoPadre() != null) {
                this.proyectoPadre = proyecto.getProyectoPadre();
            }

            if (proyecto.getEstadoReclamacion() != null) {
                estadoReclamacion = proyecto.getEstadoReclamacion();
            } else {
                estadoReclamacion = "";
            }

            if (proyecto.getEstadoReclamacionEvaluacion() != null) {
                estadoReclamacionEva = proyecto.getEstadoReclamacionEvaluacion();
            } else {
                estadoReclamacionEva = "";
            }
            
        }

    }

    /**
     * Checks if is es presentaron reclamacion.
     *
     * @return true, if is es presentaron reclamacion
     */
    public boolean isEsPresentaronReclamacion() {
        if (estadoReclamacion.equals("E") || estadoReclamacion.equals("A") || estadoReclamacion.equals("R")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if is es presentaron reclamacion eva.
     *
     * @return true, if is es presentaron reclamacion eva
     */
    public boolean isEsPresentaronReclamacionEva() {
        if (estadoReclamacionEva.equals("E") || estadoReclamacionEva.equals("A") || estadoReclamacionEva.equals("R")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets the mostrar boton consultar Y ver evaluacion negado.
     *
     * @return the mostrar boton consultar Y ver evaluacion negado
     */
    public boolean getMostrarBotonConsultarYVerEvaluacionNegado() {

        if (tipoConvocatoria != null && tipoConvocatoria.equals("C") && estadoConvocatoria == false) {
            return true;
        }

        if (tipoConvocatoria != null && tipoConvocatoria.equals("J") && estadoConvocatoria == false) {
            return true;
        }

        if (tipoConvocatoria != null && !tipoConvocatoria.equals("C") && !tipoConvocatoria.equals("J")) {
            return true;
        }
        return false;
    }

    /**
     * Checks if is mostrar boton editar proyecto activo.
     *
     * @return true, if is mostrar boton editar proyecto activo
     */
    public boolean isMostrarBotonEditarProyectoActivo() {

        if (!esPrincipal && !esEstudianteLider && !esAsistenteLider) {
            return false;
        }

        if (tipoConvocatoria != null && tipoConvocatoria.equals("C") && estadoConvocatoria == false) {
            return true;
        }

        if (tipoConvocatoria != null && tipoConvocatoria.equals("J") && estadoConvocatoria == false) {
            return true;
        }
        return false;
    }

    /**
     * Gets the mostrar boton consultar Y quipu Y evaluacion con proyecto activo.
     *
     * @return the mostrar boton consultar Y quipu Y evaluacion con proyecto activo
     */
    public boolean getMostrarBotonConsultarYQuipuYEvaluacionConProyectoActivo() {
        if (tipoConvocatoria != null && tipoConvocatoria.equals("C") && estadoConvocatoria == false) {
            return true;
        }

        if (tipoConvocatoria != null && tipoConvocatoria.equals("J") && estadoConvocatoria == false) {
            return true;
        }

        if (tipoConvocatoria != null && !tipoConvocatoria.equals("C") && !tipoConvocatoria.equals("J")) {
            return true;
        }
        return false;
    }

    /**
     * Gets the mostrar boton ficha quipu O consultar proyecto ingresando.
     *
     * @return the mostrar boton ficha quipu O consultar proyecto ingresando
     */
    public boolean getMostrarBotonFichaQuipuOConsultarProyectoIngresando() {

        if (tipoConvocatoria != null && tipoConvocatoria.equals("C") && estadoConvocatoria == false) {
            return true;
        }

        if (tipoConvocatoria != null && tipoConvocatoria.equals("J") && estadoConvocatoria == false) {
            return true;
        }

        if (tipoConvocatoria != null && !tipoConvocatoria.equals("C") && !tipoConvocatoria.equals("J")) {
            return true;
        }
        return false;
    }

    /**
     * Gets the mostrar boton editar O eliminar proyecto ingresando.
     *
     * @return the mostrar boton editar O eliminar proyecto ingresando
     */
    public boolean getMostrarBotonEditarOEliminarProyectoIngresando() {

        if (!this.isEsPrincipal() && !this.isEsEstudianteLider() && !this.esAsistenteLider) {
            return false;
        }

        if (tipoConvocatoria != null && tipoConvocatoria.equals("C") && estadoConvocatoria == false) {
            return true;
        }

        if (tipoConvocatoria != null && tipoConvocatoria.equals("J") && estadoConvocatoria == false) {
            return true;
        }

        if (tipoConvocatoria != null && !tipoConvocatoria.equals("C") && !tipoConvocatoria.equals("J")) {
            return true;
        }
        return false;
    }

    /**
     * Gets the mostrar boton editar proyecto aprobado.
     *
     * @return the mostrar boton editar proyecto aprobado
     */
    public boolean getMostrarBotonEditarProyectoAprobado() {

        if (!this.isEsPrincipal() && !this.isEsEstudianteLider()) {
            return false;
        }

        if (tipoConvocatoria != null && tipoConvocatoria.equals("C") && estadoConvocatoria == false) {
            return true;
        }

        if (tipoConvocatoria != null && tipoConvocatoria.equals("J") && estadoConvocatoria == false) {
            return true;
        }

        if (tipoConvocatoria != null && !tipoConvocatoria.equals("C") && !tipoConvocatoria.equals("J")) {
            return true;
        }
        return false;
    }

    /**
     * Gets the mostrar boton consultar ficha quipu evaluacion aprovado.
     *
     * @return the mostrar boton consultar ficha quipu evaluacion aprovado
     */
    public boolean getMostrarBotonConsultarFichaQuipuEvaluacionAprovado() {

        if (tipoConvocatoria != null && tipoConvocatoria.equals("C") && estadoConvocatoria == false) {
            return true;
        }

        if (tipoConvocatoria != null && tipoConvocatoria.equals("J") && estadoConvocatoria == false) {
            return true;
        }

        if (tipoConvocatoria != null && !tipoConvocatoria.equals("C") && !tipoConvocatoria.equals("J")) {
            return true;
        }
        return false;
    }

    /**
     * Gets the mostrar boton consultar rechazado.
     *
     * @return the mostrar boton consultar rechazado
     */
    public boolean getMostrarBotonConsultarRechazado() {

        if (tipoConvocatoria != null && tipoConvocatoria.equals("C") && estadoConvocatoria == false) {
            return true;
        }

        if (tipoConvocatoria != null && tipoConvocatoria.equals("J") && estadoConvocatoria == false) {
            return true;
        }

        if (tipoConvocatoria != null && !tipoConvocatoria.equals("C") && !tipoConvocatoria.equals("J")) {
            return true;
        }
        return false;
    }

    /**
     * Gets the mostrar boton consultar proyecto cancelado.
     *
     * @return the mostrar boton consultar proyecto cancelado
     */
    public boolean getMostrarBotonConsultarProyectoCancelado() {
        if (tipoConvocatoria != null && tipoConvocatoria.equals("C") && estadoConvocatoria == false) {
            return true;
        }

        if (tipoConvocatoria != null && tipoConvocatoria.equals("J") && estadoConvocatoria == false) {
            return true;
        }

        if (tipoConvocatoria != null && !tipoConvocatoria.equals("C") && !tipoConvocatoria.equals("J")) {
            return true;
        }
        return false;
    }

    /**
     * Gets the mostrar boton consultar Y quipu Y evaluacion proyecto finalizado.
     *
     * @return the mostrar boton consultar Y quipu Y evaluacion proyecto finalizado
     */
    public boolean getMostrarBotonConsultarYQuipuYEvaluacionProyectoFinalizado() {

        if (tipoConvocatoria != null && tipoConvocatoria.equals("C") && estadoConvocatoria == false) {
            return true;
        }

        if (tipoConvocatoria != null && tipoConvocatoria.equals("J") && estadoConvocatoria == false) {
            return true;
        }

        if (tipoConvocatoria != null && !tipoConvocatoria.equals("C") && !tipoConvocatoria.equals("J")) {
            return true;
        }
        return false;
    }

    /**
     * Gets the mostrar boton editar proyecto activo solicitud.
     *
     * @return the mostrar boton editar proyecto activo solicitud
     */
    public boolean getMostrarBotonEditarProyectoActivoSolicitud() {

        if (id.equals(25695L)) {
            System.out.println();
        }

        if (!this.isEsPrincipal()) {
            return false;
        }

        if (this != null && this.getRestriccionConvocatoria() != null
                && this.getRestriccionConvocatoria().getId() != null
                && this.getRestriccionConvocatoria().getId().equals("EXT")) {
            return true;
        }

        if (tipoConvocatoria != null && tipoConvocatoria.equals("C")) {
            return true;
        }

        if (tipoConvocatoria != null && tipoConvocatoria.equals("J")) {
            return true;
        }

        if (tipoConvocatoria != null && !tipoConvocatoria.equals("C") && !tipoConvocatoria.equals("J")) {
            return true;
        }
        return false;
    }

    /**
     * Gets the mostrar boton informe.
     *
     * @return the mostrar boton informe
     */
    public boolean getMostrarBotonInforme() {
        if (isEsPrincipal()) {

            if (getRestriccionConvocatoria() != null && getRestriccionConvocatoria().getId() != null
                    && getRestriccionConvocatoria().getId().equals("EXT")) {
                return true;
            }

            if (tipoConvocatoria != null && tipoConvocatoria.equals("C")) {
                return true;
            }

            if (tipoConvocatoria != null && tipoConvocatoria.equals("J")) {
                return true;
            }

            if (tipoConvocatoria != null && !tipoConvocatoria.equals("C") && !tipoConvocatoria.equals("J")) {
                return true;
            }

            if (esJornadaDocente) {
                return true;
            }
            return false;
        } else if (this.getHabilitarInformesEstudiante() != null) {
            if (this.habilitarInformesEstudiante.indexOf("-" + tipoInvestigadorActual + "-") != -1) {
                return true;
            }
        }
        if (habilitarInformesTutor != null) {
            if (habilitarInformesTutor.indexOf("-" + tipoInvestigadorActual + "-") != -1) {
                esTutor = true;
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the mostrar boton editar eliminar proyecto propuesto.
     *
     * @return the mostrar boton editar eliminar proyecto propuesto
     */
    public boolean getMostrarBotonEditarEliminarProyectoPropuesto() {
        if (!isEsPrincipal() && !isEsEstudianteLider()) {
            return false;
        }

        if (tipoConvocatoria != null && tipoConvocatoria.equals("C") && estadoConvocatoria == false) {
            return true;
        }

        if (tipoConvocatoria != null && !tipoConvocatoria.equals("C") && !tipoConvocatoria.equals("J")) {
            return false;
        }
        return false;
    }

    /**
     * Gets the entidad financiadora.
     *
     * @return the entidad financiadora
     */
    public String getEntidadFinanciadora() {
        return entidadFinanciadora;
    }

    /**
     * Sets the entidad financiadora.
     *
     * @param entidadFinanciadora the new entidad financiadora
     */
    public void setEntidadFinanciadora(String entidadFinanciadora) {
        this.entidadFinanciadora = entidadFinanciadora;
    }

    /**
     * Gets the monto recursos.
     *
     * @return the monto recursos
     */
    public String getMontoRecursos() {
        return montoRecursos;
    }

    /**
     * Sets the monto recursos.
     *
     * @param montoRecursos the new monto recursos
     */
    public void setMontoRecursos(String montoRecursos) {
        this.montoRecursos = montoRecursos;
    }

    /**
     * Gets the estado proyecto.
     *
     * @return the estado proyecto
     */
    public String getEstadoProyecto() {
        return estadoProyecto;
    }

    /**
     * Sets the estado proyecto.
     *
     * @param estadoProyecto the new estado proyecto
     */
    public void setEstadoProyecto(String estadoProyecto) {
        this.estadoProyecto = estadoProyecto;
    }

    /**
     * Gets the convocatoria padre.
     *
     * @return the convocatoria padre
     */
    public String getConvocatoriaPadre() {
        return convocatoriaPadre;
    }

    /**
     * Sets the convocatoria padre.
     *
     * @param convocatoriaPadre the new convocatoria padre
     */
    public void setConvocatoriaPadre(String convocatoriaPadre) {
        this.convocatoriaPadre = convocatoriaPadre;
    }

    /**
     * Checks if is es principal.
     *
     * @return true, if is es principal
     */
    public boolean isEsPrincipal() {
        return esPrincipal;
    }

    /**
     * Sets the es principal.
     *
     * @param esPrincipal the new es principal
     */
    public void setEsPrincipal(boolean esPrincipal) {
        this.esPrincipal = esPrincipal;
    }

    /**
     * Checks if is es estudiante lider.
     *
     * @return true, if is es estudiante lider
     */
    public boolean isEsEstudianteLider() {
        return esEstudianteLider;
    }

    /**
     * Sets the es estudiante lider.
     *
     * @param esEstudianteLider the new es estudiante lider
     */
    public void setEsEstudianteLider(boolean esEstudianteLider) {
        this.esEstudianteLider = esEstudianteLider;
    }

    /**
     * Gets the fase.
     *
     * @return the fase
     */
    public Integer getFase() {
        return fase;
    }

    /**
     * Sets the fase.
     *
     * @param fase the new fase
     */
    public void setFase(Integer fase) {
        this.fase = fase;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the nombre.
     *
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the nombre.
     *
     * @param nombre the new nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Gets the nombre modalidad.
     *
     * @return the nombre modalidad
     */
    public String getNombreModalidad() {
        return nombreModalidad;
    }

    /**
     * Sets the nombre modalidad.
     *
     * @param nombreModalidad the new nombre modalidad
     */
    public void setNombreModalidad(String nombreModalidad) {
        this.nombreModalidad = nombreModalidad;
    }

    /**
     * Gets the mensaje estado.
     *
     * @return the mensaje estado
     */
    public String getMensajeEstado() {
        return mensajeEstado;
    }

    /**
     * Sets the mensaje estado.
     *
     * @param mensajeEstado the new mensaje estado
     */
    public void setMensajeEstado(String mensajeEstado) {
        this.mensajeEstado = mensajeEstado;
    }

    /**
     * Checks if is completo.
     *
     * @return true, if is completo
     */
    public boolean isCompleto() {
        return completo;
    }

    /**
     * Sets the completo.
     *
     * @param completo the new completo
     */
    public void setCompleto(boolean completo) {
        this.completo = completo;
    }

    /**
     * Gets the es editable.
     *
     * @return the es editable
     */
    public boolean getEsEditable() {
        if (permitirModificacion != null && permitirModificacion.equals("S")) {
            return true;
        }
        return false;
    }

    /**
     * Sets the es editable.
     *
     * @param esEditable the new es editable
     */
    public void setEsEditable(boolean esEditable) {
        if (esEditable) {
            permitirModificacion = "S";
        } else {
            permitirModificacion = "N";
        }
    }

    /**
     * Sets the permitir modificacion.
     *
     * @param permitirModificacion the new permitir modificacion
     */
    public void setPermitirModificacion(String permitirModificacion) {
        this.permitirModificacion = permitirModificacion;
    }

    /**
     * Gets the permitir modificacion.
     *
     * @return the permitir modificacion
     */
    public String getPermitirModificacion() {
        return permitirModificacion;
    }

    /**
     * Gets the estado proyecto id.
     *
     * @return the estado proyecto id
     */
    public String getEstadoProyectoId() {
        return estadoProyectoId;
    }

    /**
     * Sets the estado proyecto id.
     *
     * @param estadoProyectoId the new estado proyecto id
     */
    public void setEstadoProyectoId(String estadoProyectoId) {
        this.estadoProyectoId = estadoProyectoId;
    }

    /**
     * Gets the lista archivos.
     *
     * @return the lista archivos
     */
    public List getListaArchivos() {
        return listaArchivos;
    }

    /**
     * Sets the lista archivos.
     *
     * @param listaArchivos the new lista archivos
     */
    public void setListaArchivos(List listaArchivos) {
        this.listaArchivos = listaArchivos;
    }

    /**
     * Checks if is mostrar archivos.
     *
     * @return true, if is mostrar archivos
     */
    public boolean isMostrarArchivos() {
        return mostrarArchivos;
    }

    /**
     * Sets the mostrar archivos.
     *
     * @param mostrarArchivos the new mostrar archivos
     */
    public void setMostrarArchivos(boolean mostrarArchivos) {
        this.mostrarArchivos = mostrarArchivos;
    }

    /**
     * Gets the tipo convocatoria.
     *
     * @return the tipo convocatoria
     */
    public String getTipoConvocatoria() {
        return tipoConvocatoria;
    }

    /**
     * Sets the tipo convocatoria.
     *
     * @param tipoConvocatoria the new tipo convocatoria
     */
    public void setTipoConvocatoria(String tipoConvocatoria) {
        this.tipoConvocatoria = tipoConvocatoria;
    }

    /**
     * Checks if is estado convocatoria.
     *
     * @return true, if is estado convocatoria
     */
    public boolean isEstadoConvocatoria() {
        return estadoConvocatoria;
    }

    /**
     * Sets the estado convocatoria.
     *
     * @param estadoConvocatoria the new estado convocatoria
     */
    public void setEstadoConvocatoria(boolean estadoConvocatoria) {
        this.estadoConvocatoria = estadoConvocatoria;
    }

    /**
     * Gets the permitir modificacion convocatoria.
     *
     * @return the permitir modificacion convocatoria
     */
    public String getPermitirModificacionConvocatoria() {
        return permitirModificacionConvocatoria;
    }

    /**
     * Sets the permitir modificacion convocatoria.
     *
     * @param permitirModificacionConvocatoria the new permitir modificacion convocatoria
     */
    public void setPermitirModificacionConvocatoria(String permitirModificacionConvocatoria) {
        this.permitirModificacionConvocatoria = permitirModificacionConvocatoria;
    }

    /**
     * Gets the restriccion convocatoria.
     *
     * @return the restriccion convocatoria
     */
    public RestriccionConvocatoria getRestriccionConvocatoria() {
        return restriccionConvocatoria;
    }

    /**
     * Sets the restriccion convocatoria.
     *
     * @param restriccionConvocatoria the new restriccion convocatoria
     */
    public void setRestriccionConvocatoria(RestriccionConvocatoria restriccionConvocatoria) {
        this.restriccionConvocatoria = restriccionConvocatoria;
    }

    /**
     * Checks if is es carta inicio.
     *
     * @return true, if is es carta inicio
     */
    public boolean isEsCartaInicio() {
        return esCartaInicio;
    }

    /**
     * Sets the es carta inicio.
     *
     * @param esCartaInicio the new es carta inicio
     */
    public void setEsCartaInicio(boolean esCartaInicio) {
        this.esCartaInicio = esCartaInicio;
    }

    /**
     * Checks if is es carta fin.
     *
     * @return true, if is es carta fin
     */
    public boolean isEsCartaFin() {
        return esCartaFin;
    }

    /**
     * Sets the es carta fin.
     *
     * @param esCartaFin the new es carta fin
     */
    public void setEsCartaFin(boolean esCartaFin) {
        this.esCartaFin = esCartaFin;
    }

    /**
     * Checks if is es carta prorroga.
     *
     * @return true, if is es carta prorroga
     */
    public boolean isEsCartaProrroga() {
        return esCartaProrroga;
    }

    /**
     * Sets the es carta prorroga.
     *
     * @param esCartaProrroga the new es carta prorroga
     */
    public void setEsCartaProrroga(boolean esCartaProrroga) {
        this.esCartaProrroga = esCartaProrroga;
    }

    /**
     * Gets the nombre convocatoria padre.
     *
     * @return the nombre convocatoria padre
     */
    public String getNombreConvocatoriaPadre() {
        return nombreConvocatoriaPadre;
    }

    /**
     * Sets the nombre convocatoria padre.
     *
     * @param nombreConvocatoriaPadre the new nombre convocatoria padre
     */
    public void setNombreConvocatoriaPadre(String nombreConvocatoriaPadre) {
        this.nombreConvocatoriaPadre = nombreConvocatoriaPadre;
    }

    /**
     * Checks if is es coordinador.
     *
     * @return true, if is es coordinador
     */
    public boolean isEsCoordinador() {
        return esCoordinador;
    }

    /**
     * Sets the es coordinador.
     *
     * @param esCoordinador the new es coordinador
     */
    public void setEsCoordinador(boolean esCoordinador) {
        this.esCoordinador = esCoordinador;
    }

    /**
     * Gets the nombre estado proyecto.
     *
     * @return the nombre estado proyecto
     */
    public String getNombreEstadoProyecto() {
        return nombreEstadoProyecto;
    }

    /**
     * Sets the nombre estado proyecto.
     *
     * @param nombreEstadoProyecto the new nombre estado proyecto
     */
    public void setNombreEstadoProyecto(String nombreEstadoProyecto) {
        this.nombreEstadoProyecto = nombreEstadoProyecto;
    }

    /**
     * Checks if is es unidad.
     *
     * @return true, if is es unidad
     */
    public boolean isEsUnidad() {
        return esUnidad;
    }

    /**
     * Sets the es unidad.
     *
     * @param esUnidad the new es unidad
     */
    public void setEsUnidad(boolean esUnidad) {
        this.esUnidad = esUnidad;
    }

    /**
     * Gets the codigo quipu.
     *
     * @return the codigo quipu
     */
    public String getCodigoQuipu() {
        return codigoQuipu;
    }

    /**
     * Sets the codigo quipu.
     *
     * @param codigoQuipu the new codigo quipu
     */
    public void setCodigoQuipu(String codigoQuipu) {
        this.codigoQuipu = codigoQuipu;
    }

    /**
     * Checks if is es activo.
     *
     * @return true, if is es activo
     */
    public boolean isEsActivo() {
        if (estadoProyecto.equals("Activo")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if is es aprobado.
     *
     * @return true, if is es aprobado
     */
    public boolean isEsAprobado() {
        if (estadoProyecto.equals("Aprobado")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if is es ingresando.
     *
     * @return true, if is es ingresando
     */
    public boolean isEsIngresando() {
        if (estadoProyecto.equals("Ingresando Proyecto")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if is es propuesto.
     *
     * @return true, if is es propuesto
     */
    public boolean isEsPropuesto() {
        if (estadoProyecto.equals("Propuesto")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if is es elegible.
     *
     * @return true, if is es elegible
     */
    public boolean isEsElegible() {
        if (estadoProyecto.equals("Elegible")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if is es rechazado.
     *
     * @return true, if is es rechazado
     */
    public boolean isEsRechazado() {
        if (esPrincipal && estadoProyecto.equals("No cumplió requisitos")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if is es cancelado.
     *
     * @return true, if is es cancelado
     */
    public boolean isEsCancelado() {
        if (estadoProyecto.equals("Cancelado")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if is es finalizado.
     *
     * @return true, if is es finalizado
     */
    public boolean isEsFinalizado() {
        if (estadoProyecto.equals("Finalizado") || estadoProyecto.equals("Por finalizar entidad externa")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if is es no aprobado.
     *
     * @return true, if is es no aprobado
     */
    public boolean isEsNoAprobado() {
        if (esPrincipal && estadoProyecto.equals("No aprobado")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if is es suspendido.
     *
     * @return true, if is es suspendido
     */
    public boolean isEsSuspendido() {
        if (nombreEstadoProyecto.equals("Suspendido")) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean isEsEnLegalizacion() {
        if (nombreEstadoProyecto.equals("En Legalización")) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean isEsPublicable() {
        if (nombreEstadoProyecto.equals("Publicable")) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean isEsNoPublicable() {
        if (nombreEstadoProyecto.equals("No publicable")) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean isEsBancoFinanciable() {
        if (nombreEstadoProyecto.equals("Banco Financiable")) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean isFechaFinalizacionVigente(){
    	if(nombreEstadoProyecto != null && nombreEstadoProyecto.equals("Activo") && fechaTentativaInicio != null){
    		Calendar c = Calendar.getInstance();
        	c.setTime(fechaTentativaInicio);
        	        	        	
        	if(duracionAcumulada == null)
        		duracionAcumulada = 0;
        	
        	if(duracionDiasAcumulada == null)
        		duracionDiasAcumulada = 0;
        	
        	if(duracion != duracionAcumulada){
        		c.add(Calendar.MONTH, duracionAcumulada);
            	c.add(Calendar.DATE, duracionDiasAcumulada);	
        	}else{
        		c.add(Calendar.MONTH, duracion);
        		if(duracionDiasAcumulada > 0){
            		c.add(Calendar.DATE, duracionDiasAcumulada);
        		}
        	}
        	        	        	
        	Date fechaFin = c.getTime();
        	Date fechaActual = new Date();
        	
        	Calendar calFechFin = Calendar.getInstance();
        	Calendar calFecActual = Calendar.getInstance();
        	calFechFin.setTime(fechaFin);
        	calFecActual.setTime(fechaActual);
        	boolean mismaFecha = calFechFin.get(Calendar.YEAR) == calFecActual.get(Calendar.YEAR)
        			&& calFechFin.get(Calendar.MONTH) == calFecActual.get(Calendar.MONTH)
        			&& calFechFin.get(Calendar.DAY_OF_MONTH) == calFecActual.get(Calendar.DAY_OF_MONTH);
        	
        	if(mismaFecha){
        		return true;	
        	}else{
        		if(!(fechaFin.compareTo(fechaActual) < 0)){
            		return true;
            	}else{    		
            		return false;
            	}
        	}
    	}else if(nombreEstadoProyecto != null && nombreEstadoProyecto.equals("Suspendido") && fechaTentativaInicio != null){
    		return true;
    	}else {
    		return false;
    	}
    }

    /**
     * Sets the mostrar boton migrar.
     *
     * @param mostrarBotonMigrar the new mostrar boton migrar
     */
    public void setMostrarBotonMigrar(boolean mostrarBotonMigrar) {
        this.mostrarBotonMigrar = mostrarBotonMigrar;
    }

    /**
     * Checks if is mostrar boton migrar.
     *
     * @return true, if is mostrar boton migrar
     */
    public boolean isMostrarBotonMigrar() {
        return mostrarBotonMigrar;
    }

    /**
     * Sets the tiene codigo QUIPU.
     *
     * @param tieneCodigoQUIPU the new tiene codigo QUIPU
     */
    public void setTieneCodigoQUIPU(boolean tieneCodigoQUIPU) {
        this.tieneCodigoQUIPU = tieneCodigoQUIPU;
    }

    /**
     * Checks if is tiene codigo QUIPU.
     *
     * @return true, if is tiene codigo QUIPU
     */
    public boolean isTieneCodigoQUIPU() {
        return tieneCodigoQUIPU;
    }

    /**
     * Sets the responsable.
     *
     * @param responsable the new responsable
     */
    public void setResponsable(Investigador responsable) {
        this.responsable = responsable;
    }

    /**
     * Gets the responsable.
     *
     * @return the responsable
     */
    public Investigador getResponsable() {
        return responsable;
    }

    /**
     * Gets the es articulo.
     *
     * @return the es articulo
     */
    public Boolean getEsArticulo() {
        return esArticulo;
    }

    /**
     * Sets the es articulo.
     *
     * @param esArticulo the new es articulo
     */
    public void setEsArticulo(Boolean esArticulo) {
        this.esArticulo = esArticulo;
    }

    /**
     * Gets the es ficha minima.
     *
     * @return the es ficha minima
     */
    public Boolean getEsFichaMinima() {
        return esFichaMinima;
    }

    /**
     * Sets the es ficha minima.
     *
     * @param esFichaMinima the new es ficha minima
     */
    public void setEsFichaMinima(Boolean esFichaMinima) {
        this.esFichaMinima = esFichaMinima;
    }

    /**
     * Sets the es convocatoria permanente.
     *
     * @param esConvocatoriaPermanente the new es convocatoria permanente
     */
    public void setEsConvocatoriaPermanente(Boolean esConvocatoriaPermanente) {
        this.esConvocatoriaPermanente = esConvocatoriaPermanente;
    }

    /**
     * Gets the es convocatoria permanente.
     *
     * @return the es convocatoria permanente
     */
    public Boolean getEsConvocatoriaPermanente() {
        return esConvocatoriaPermanente;
    }

    /**
     * Sets the codigo dib.
     *
     * @param codigoDib the new codigo dib
     */
    public void setCodigoDib(String codigoDib) {
        this.codigoDib = codigoDib;
    }

    /**
     * Gets the codigo dib.
     *
     * @return the codigo dib
     */
    public String getCodigoDib() {
        return codigoDib;
    }

    /**
     * Sets the es modalidad alianzas.
     *
     * @param esModalidadAlianzas the new es modalidad alianzas
     */
    public void setEsModalidadAlianzas(boolean esModalidadAlianzas) {
        this.esModalidadAlianzas = esModalidadAlianzas;
    }

    /**
     * Checks if is es modalidad alianzas.
     *
     * @return true, if is es modalidad alianzas
     */
    public boolean isEsModalidadAlianzas() {
        return esModalidadAlianzas;
    }

    /**
     * Gets the habilitar informes estudiante.
     *
     * @return the habilitar informes estudiante
     */
    public String getHabilitarInformesEstudiante() {
        return habilitarInformesEstudiante;
    }

    /**
     * Sets the habilitar informes estudiante.
     *
     * @param habilitarInformesEstudiante the new habilitar informes estudiante
     */
    public void setHabilitarInformesEstudiante(String habilitarInformesEstudiante) {
        this.habilitarInformesEstudiante = habilitarInformesEstudiante;
    }

    /**
     * Gets the tipo investigador actual.
     *
     * @return the tipo investigador actual
     */
    public String getTipoInvestigadorActual() {
        return tipoInvestigadorActual;
    }

    /**
     * Sets the tipo investigador actual.
     *
     * @param tipoInvestigadorActual the new tipo investigador actual
     */
    public void setTipoInvestigadorActual(String tipoInvestigadorActual) {
        this.tipoInvestigadorActual = tipoInvestigadorActual;
    }

    /**
     * Gets the proyecto padre.
     *
     * @return the proyecto padre
     */
    public Long getProyectoPadre() {
        return proyectoPadre;
    }

    /**
     * Sets the proyecto padre.
     *
     * @param proyectoPadre the new proyecto padre
     */
    public void setProyectoPadre(Long proyectoPadre) {
        this.proyectoPadre = proyectoPadre;
    }

    /**
     * Checks if is es tutor.
     *
     * @return true, if is es tutor
     */
    public boolean isEsTutor() {
        return esTutor;
    }

    /**
     * Sets the es tutor.
     *
     * @param esTutor the new es tutor
     */
    public void setEsTutor(boolean esTutor) {
        this.esTutor = esTutor;
    }

    /**
     * Gets the habilitar informes tutor.
     *
     * @return the habilitar informes tutor
     */
    public String getHabilitarInformesTutor() {
        return habilitarInformesTutor;
    }

    /**
     * Sets the habilitar informes tutor.
     *
     * @param habilitarInformesTutor the new habilitar informes tutor
     */
    public void setHabilitarInformesTutor(String habilitarInformesTutor) {
        this.habilitarInformesTutor = habilitarInformesTutor;
    }

    /**
     * Gets the avales proyecto.
     *
     * @return the avales proyecto
     */
    public List getAvalesProyecto() {
        return avalesProyecto;
    }

    /**
     * Sets the avales proyecto.
     *
     * @param avalesProyecto the new avales proyecto
     */
    public void setAvalesProyecto(List avalesProyecto) {
        this.avalesProyecto = avalesProyecto;
    }

    /**
     * Checks if is es jornada docente.
     *
     * @return true, if is es jornada docente
     */
    public boolean isEsJornadaDocente() {
        return esJornadaDocente;
    }

    /**
     * Gets the tipo actividad.
     *
     * @return the tipo actividad
     */
    public String getTipoActividad() {
        return tipoActividad;
    }

    /**
     * Sets the tipo actividad.
     *
     * @param tipoActividad the new tipo actividad
     */
    public void setTipoActividad(String tipoActividad) {
        this.tipoActividad = tipoActividad;
    }

    /**
     * Checks if is es permiso marco.
     *
     * @return true, if is es permiso marco
     */
    public boolean isEsPermisoMarco() {
        return esPermisoMarco;
    }

    /**
     * Sets the es permiso marco.
     *
     * @param esPermisoMarco the new es permiso marco
     */
    public void setEsPermisoMarco(boolean esPermisoMarco) {
        this.esPermisoMarco = esPermisoMarco;
    }

    /**
     * Gets the fecha tentativa inicio.
     *
     * @return the fecha tentativa inicio
     */
    public Date getFechaTentativaInicio() {
        return fechaTentativaInicio;
    }

    /**
     * Sets the fecha tentativa inicio.
     *
     * @param fechaTentativaInicio the new fecha tentativa inicio
     */
    public void setFechaTentativaInicio(Date fechaTentativaInicio) {
        this.fechaTentativaInicio = fechaTentativaInicio;
    }

    /**
     * Gets the duracion.
     *
     * @return the duracion
     */
    public int getDuracion() {
        return duracion;
    }

    /**
     * Sets the duracion.
     *
     * @param duracion the new duracion
     */
    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    /**
     * Gets the duracion dias acumulada.
     *
     * @return the duracion dias acumulada
     */
    public Integer getDuracionDiasAcumulada() {
        return duracionDiasAcumulada;
    }

    /**
     * Sets the duracion dias acumulada.
     *
     * @param duracionDiasAcumulada the new duracion dias acumulada
     */
    public void setDuracionDiasAcumulada(Integer duracionDiasAcumulada) {
        this.duracionDiasAcumulada = duracionDiasAcumulada;
    }

    /**
     * Gets the duracion acumulada.
     *
     * @return the duracion acumulada
     */
    public Integer getDuracionAcumulada() {
        return duracionAcumulada;
    }

    /**
     * Sets the duracion acumulada.
     *
     * @param duracionAcumulada the new duracion acumulada
     */
    public void setDuracionAcumulada(Integer duracionAcumulada) {
        this.duracionAcumulada = duracionAcumulada;
    }

    /**
     * Checks if is es pry contrapartida.
     *
     * @return true, if is es pry contrapartida
     */
    public boolean isEsPryContrapartida() {
        return esPryContrapartida;
    }

    /**
     * Sets the es pry contrapartida.
     *
     * @param esPryContrapartida the new es pry contrapartida
     */
    public void setEsPryContrapartida(boolean esPryContrapartida) {
        this.esPryContrapartida = esPryContrapartida;
    }

    /**
     * Gets the compromisos pendientes.
     *
     * @return the compromisos pendientes
     */
    public String getCompromisosPendientes() {
        return compromisosPendientes;
    }

    /**
     * Sets the compromisos pendientes.
     *
     * @param compromisosPendientes the new compromisos pendientes
     */
    public void setCompromisosPendientes(String compromisosPendientes) {
        this.compromisosPendientes = compromisosPendientes;
    }

    /**
     * Gets the es convocatoria externa.
     *
     * @return the esConvocatoriaExterna
     */
    public Boolean getEsConvocatoriaExterna() {
        return esConvocatoriaExterna;
    }

    /**
     * Sets the es convocatoria externa.
     *
     * @param esConvocatoriaExterna            the esConvocatoriaExterna to set
     */
    public void setEsConvocatoriaExterna(Boolean esConvocatoriaExterna) {
        this.esConvocatoriaExterna = esConvocatoriaExterna;
    }

    /**
     * Checks if is es contrato acceso biodiversidad.
     *
     * @return true, if is es contrato acceso biodiversidad
     */
    public boolean isEsContratoAccesoBiodiversidad() {
        return esContratoAccesoBiodiversidad;
    }

    /**
     * Sets the es contrato acceso biodiversidad.
     *
     * @param esContratoAccesoBiodiversidad the new es contrato acceso biodiversidad
     */
    public void setEsContratoAccesoBiodiversidad(boolean esContratoAccesoBiodiversidad) {
        this.esContratoAccesoBiodiversidad = esContratoAccesoBiodiversidad;
    }

    /**
     * Checks if is es permiso marco asignatura.
     *
     * @return true, if is es permiso marco asignatura
     */
    public boolean isEsPermisoMarcoAsignatura() {
        return esPermisoMarcoAsignatura;
    }

    /**
     * Sets the es permiso marco asignatura.
     *
     * @param esPermisoMarcoAsignatura the new es permiso marco asignatura
     */
    public void setEsPermisoMarcoAsignatura(boolean esPermisoMarcoAsignatura) {
        this.esPermisoMarcoAsignatura = esPermisoMarcoAsignatura;
    }

    /**
     * Gets the estado reclamacion.
     *
     * @return the estado reclamacion
     */
    public String getEstadoReclamacion() {
        return estadoReclamacion;
    }

    /**
     * Sets the estado reclamacion.
     *
     * @param estadoReclamacion the new estado reclamacion
     */
    public void setEstadoReclamacion(String estadoReclamacion) {
        this.estadoReclamacion = estadoReclamacion;
    }

    /**
     * Gets the estado reclamacion eva.
     *
     * @return the estado reclamacion eva
     */
    public String getEstadoReclamacionEva() {
        return estadoReclamacionEva;
    }

    /**
     * Sets the estado reclamacion eva.
     *
     * @param estadoReclamacionEva the new estado reclamacion eva
     */
    public void setEstadoReclamacionEva(String estadoReclamacionEva) {
        this.estadoReclamacionEva = estadoReclamacionEva;
    }
    
	public Date getFechaFinalizacionVista() {
		return fechaFinalizacionVista;
	}

	public void setFechaFinalizacionVista(Date fechaFinalizacionVista) {
		this.fechaFinalizacionVista = fechaFinalizacionVista;
	}

    public boolean isTieneEstudiantesSinDetalle() {
		return tieneEstudiantesSinDetalle;
	}

	public void setTieneEstudiantesSinDetalle(boolean tieneEstudiantesSinDetalle) {
		this.tieneEstudiantesSinDetalle = tieneEstudiantesSinDetalle;
	}

	/* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(ProyectoVista arg0) {
        if (arg0.getId() == null) {
            return -1;
        }
        if (this.id == null) {
            return 1;
        }
        if (this.id < arg0.getId()) {
            return 1;
        }
        if (this.id > arg0.getId()) {
            return -1;
        }
        return 0;
    }

	public boolean isEsAsistenteLider() {
		return esAsistenteLider;
	}

	public void setEsAsistenteLider(boolean esAsistenteLider) {
		this.esAsistenteLider = esAsistenteLider;
	}

	public String getDocumentoCreador() {
		return documentoCreador;
	}

	public void setDocumentoCreador(String documentoCreador) {
		this.documentoCreador = documentoCreador;
	}

	public boolean isEditaSoloCreador() {
		return editaSoloCreador;
	}

	public void setEditaSoloCreador(boolean editaSoloCreador) {
		this.editaSoloCreador = editaSoloCreador;
	}

	public boolean isConvocatoriaEditaCreador() {
		return convocatoriaEditaCreador;
	}

	public void setConvocatoriaEditaCreador(boolean convocatoriaEditaCreador) {
		this.convocatoriaEditaCreador = convocatoriaEditaCreador;
	}

	public boolean isEditaPrincipal() {
		return editaPrincipal;
	}

	public void setEditaPrincipal(boolean editaPrincipal) {
		this.editaPrincipal = editaPrincipal;
	}

}
