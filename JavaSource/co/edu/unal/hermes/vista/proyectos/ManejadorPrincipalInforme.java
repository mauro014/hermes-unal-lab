package co.edu.unal.hermes.vista.proyectos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EstadoInforme;
import co.edu.unal.hermes.modelo.Estudiante;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.ProyectoCompromiso;
import co.edu.unal.hermes.modelo.ProyectoInforme;
import co.edu.unal.hermes.modelo.TipoInforme;
import co.edu.unal.hermes.modelo.TipoModalidad;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.proyectos.base.BaseManejadorRegistroInforme;

public class ManejadorPrincipalInforme extends BaseManejadorRegistroInforme {
    private static final long serialVersionUID = -3663649652782106931L;

    private static final String ID_PERSONA_RENOVACION = "idPersonaRenovacion";
    private String accionBoton = "Editar";
    private String anoConvocatoria = "";
    private String anoConvocatoriaTmp = "";
    private String comentariosInvestigador = "";
    private String comentariosInvestigadorTmp = "";
    private boolean consultaFacultad = false;
    private String descripcionEstInv = "";
    private boolean esConsultaJovenesInv = false;

    /**
     * Variable para el manejo de edición de información en solicitudes de
     * renovación.
     */
    boolean esCoordinador;
    boolean esInvestigadorPrincipal = false;
    private boolean esJornadaDocente = false;
    private boolean esPermisoMarco = false;
    private boolean esPryContrapartida = false;
    boolean esTutor = false;
    private String estadoInvestigador = "";
    private String estadoInvestigadorTmp = "";
    private ProyectoInforme informeSeleccionado;
    private InvestigadorProyecto investigadorProyecto;
    private Persona jovenInvestigador;
    private List<DominioDetalle> listaEstadoRenovacion;
    private List<TipoInforme> listaFormatosInforme;
    private List<ProyectoInforme> listaInformes;
    private List<ProyectoInforme> listaSolicitudesRenovacion;
    private double mesesAprobados = 0;
    private double mesesAprobadosTmp = 0;
    private String nombreEstudiante;
    private String nombrePrograma;
    private boolean permitirInformeTutor = true;
    private Proyecto proyectoActual;

	private Persona coordinadorSeguimientoProyecto;

    /**
     * Contructor
     */
    public ManejadorPrincipalInforme() {

        esCoordinador = false;

        // Variables para controlar vista
        listaFormatosInforme = new ArrayList<TipoInforme>();

        esPryContrapartida = cargarVariableSessionBoolean("esPryContrapartida");
        System.out.println("esPryContrapartida: " + esPryContrapartida);

        esJornadaDocente = cargarVariableSessionBoolean("esJornadaDocente");

        esPermisoMarco = cargarVariableSessionBoolean("esPermisoMarco");

        esInvestigadorPrincipal = cargarVariableSessionBoolean("esPrincipalProyectoInforme");
        System.out.println("esInvestigadorPrincipal: " + esInvestigadorPrincipal);

        consultaFacultad = cargarVariableSessionBoolean("consultaFacultad");

        esTutor = cargarVariableSessionBoolean("esTutorProyectoInforme");
        System.out.println("esTutor: " + esTutor);

        esConsultaJovenesInv = cargarVariableSessionBoolean("esConsultaJovenesInv");
        System.out.println("esConsultaJovenesInv: " + esConsultaJovenesInv);

        Long idProyecto = 0L;

        if (super.sesion.getAttribute("idProyecto") != null) {
            if (super.sesion.getAttribute("idProyecto") instanceof Long) {
                idProyecto = (Long) super.sesion.getAttribute("idProyecto");
            } else {
                idProyecto = Long.parseLong((String) super.sesion.getAttribute("idProyecto"));
            }
            this.proyectoActual = super.servicioProyecto.obtenerProyecto(idProyecto,
                    ProyectoDAOHibernate.INFORMACION_GENERAL);
            if (proyectoActual != null) {
                Convocatoria conv = null;
                conv = (Convocatoria) this.proyectoActual.getModalidad();
                if (conv != null) {
                    permitirInformeTutor = conv.getPermitirInformeTutor() == null
                            || conv.getPermitirInformeTutor().equals("Y");
                    System.out.println("permitirInformeTutor: " + permitirInformeTutor);
                }
            }
        }

        cargarFormatosInformes();

        if (esConsultaJovenesInv) {
            cargarInformesJovenInv();
        } else {
            cargarInformes();
        }
    }

    private boolean cargarVariableSessionBoolean(String nombre) {
        if (sesion.getAttribute(nombre) != null) {
            return (Boolean) sesion.getAttribute(nombre);
        } else {
            return false;
        }
    }

    public void cancelar() {
        accionBoton = "Editar";
        estadoInvestigador = estadoInvestigadorTmp;
        comentariosInvestigador = comentariosInvestigadorTmp;
        anoConvocatoria = anoConvocatoriaTmp;
        mesesAprobados = mesesAprobadosTmp;
    }

    public void cargarFormatosInformes() {
        listaFormatosInforme = servicioGeneral.obtenerObjetos(TipoInforme.class, "from TipoInforme");
    }

    public String consultarHistoricoEstadoInforme() {
        return consultarHistoricoEstadoInforme(informeSeleccionado);
    }

    private void cargarIndexInformes() {
        if (!esListaVacia(listaInformes)) {
            int contadorSinEstudiante = 0;
            Iterator<ProyectoInforme> i = listaInformes.iterator();
            List<List<Object>> listaEstudiante = new ArrayList<List<Object>>();
            while (i.hasNext()) {
                ProyectoInforme informe = i.next();
                if (StringUtils.isNotEmpty(informe.getIdEstudiante())) {
                    boolean yaAgregado = false;
                    if (listaEstudiante == null) {
                        listaEstudiante = new ArrayList<List<Object>>();
                    } else {
                        Iterator<List<Object>> j = listaEstudiante.iterator();
                        while (j.hasNext()) {
                            List<Object> estudiante = j.next();
                            String idEtudiante = (String) estudiante.get(0);
                            if (idEtudiante.equals(informe.getIdEstudiante())) {
                                int cantidad = (Integer) estudiante.get(1);
                                cantidad++;
                                estudiante.set(1, cantidad);
                                informe.setIndex(cantidad);
                                yaAgregado = true;
                                break;
                            }
                        }
                    }
                    if (!yaAgregado) {
                        List<Object> estudiante = new ArrayList<Object>();
                        estudiante.add(informe.getIdEstudiante());
                        estudiante.add(1);
                        informe.setIndex(1);
                        listaEstudiante.add(estudiante);
                    }
                } else {
                    informe.setIndex(++contadorSinEstudiante);
                }
            }
        }
    }

    private void cargarInformes() {
        listaSolicitudesRenovacion = new ArrayList<ProyectoInforme>();
        listaEstadoRenovacion = new ArrayList<DominioDetalle>();
        Persona persona = (Persona) sesion.getAttribute("persona");
        if (proyectoActual != null) {
            listaInformes = servicioGeneral.obtenerListaInformes(proyectoActual.getId());
            coordinadorSeguimientoProyecto = this.servicioProyecto.obtenerCoordinadorProyecto(proyectoActual.getId());
        }
        if (sesion.getAttribute(ID_PERSONA_RENOVACION) != null) {
            IdPersona idPersona = (IdPersona) sesion.getAttribute(ID_PERSONA_RENOVACION);
            List<Estudiante> estudiantes;
            List<InvestigadorProyecto> investigadoresProyecto;

            Estudiante estudiante;
            String sql = "select #nombre1 est.nombre1, #nombre2 est.nombre2, #apellido1 est.apellido1, #apellido2 est.apellido2, #plan est.plan from Estudiante est where est.id.documento = '"
                    + idPersona.getDocumento() + "' and est.id.tipoDocumento = '" + idPersona.getTipoDocumento() + "'";
            String sql2 = "select inv" + " from InvestigadorProyecto inv" + " where inv.investigador.id.documento = '"
                    + idPersona.getDocumento() + "' " + "and inv.investigador.id.tipoDocumento = '"
                    + idPersona.getTipoDocumento() + "'" + "and inv.proyecto.id = '" + PROYECTO_SOLICITUD_RENOVACION
                    + "'";
            estudiantes = servicioGeneral.obtenerObjetosLimitado(Estudiante.class, sql);
            investigadoresProyecto = servicioGeneral.obtenerObjetos(InvestigadorProyecto.class, sql2);
            listaEstadoRenovacion = servicioGeneral.obtenerObjetos(DominioDetalle.class,
                    "from DominioDetalle estado where estado.identificador.id = 30");
            if (!esListaVacia(investigadoresProyecto)) {
                investigadorProyecto = investigadoresProyecto.get(0);
            }
            if (!esListaVacia(estudiantes)) {
                estudiante = estudiantes.get(0);
                nombreEstudiante = estudiante.getNombreCompleto();
                if (estudiante.getPlan() == null) {
                    nombrePrograma = "No tiene plan de Estudio Asociado";
                } else {
                    nombrePrograma = estudiante.getPlan().getNombre();
                }
                comentariosInvestigador = investigadorProyecto.getFuncion();
                estadoInvestigador = investigadorProyecto.getVisible();
                if (investigadorProyecto.getHorasJornadaDocente() != null) {
                    anoConvocatoria = investigadorProyecto.getTotalHorasVinculacion().toString();
                }
                if (investigadorProyecto.getTotalHorasVinculacion() != null) {
                    mesesAprobados = (double) (investigadorProyecto.getHorasJornadaDocente() / 10);
                }

                for (int i = 0; i < listaEstadoRenovacion.size(); i++) {
                    if (listaEstadoRenovacion.get(i).getIdentificador().getTipo().equals(estadoInvestigador)) {
                        descripcionEstInv = listaEstadoRenovacion.get(i).getDescripcion();
                    }
                }
            }
            listaSolicitudesRenovacion = servicioGeneral.obtenerListaSolicitudesRenovacion(20898L,
                    idPersona.getDocumento(), idPersona.getTipoDocumento());
            sesion.removeAttribute(ID_PERSONA_RENOVACION);
            if (idPersona != null) {
                esCoordinador = true;
            }
        } else {
            listaSolicitudesRenovacion = servicioGeneral.obtenerListaSolicitudesRenovacion(20898L,
                    persona.getId().getDocumento(), persona.getId().getTipoDocumento());
        }
        cargarIndexInformes();
    }

    private void cargarInformesJovenInv() {
        jovenInvestigador = (Persona) super.sesion.getAttribute("JovenInvestigador");
        String sqlLimitadoInformes = "select #id pryInf.id, #estadoInforme pryInf.estadoInforme, #tipoInforme pryInf.tipoInforme, "
                + "#cuadroNombre pryInf.cuadroNombre, #fechaGeneracion pryInf.fechaGeneracion, #noAprobacion pryInf.noAprobacion, #noLectura pryInf.noLectura "
                + "from ProyectoInforme pryInf " + "where pryInf.tipoDocEstudiante='"
                + jovenInvestigador.getId().getTipoDocumento() + "' " + "and pryInf.idEstudiante='"
                + jovenInvestigador.getId().getDocumento() + "' " + "and pryInf.proyecto.id = " + proyectoActual.getId()
                + " and pryInf.estadoInforme <> '" + ProyectoInforme.ESTADO_BORRADO + "' "
                + " order by pryInf.fechaGeneracion";
        listaInformes = servicioGeneral.obtenerObjetosLimitado(ProyectoInforme.class, sqlLimitadoInformes);

        for (int i = 0; i < listaInformes.size(); i++) {
            ProyectoInforme unProyectoInforme = listaInformes.get(i);
            unProyectoInforme.setIndex(i + 1);
        }
    }

    public String cerrarInforme() throws SQLException {
        boolean mostrarMensajes = true;
        return cerrarInforme(informeSeleccionado, proyectoActual, mostrarMensajes);
    }

    // Metodo para consultar si existen compromisos pendientes no cumplidos de
    // informe de avance
    public boolean tieneCompromisosPendientesAvance() {
        String hql = "select #id pc.id" + " from ProyectoCompromiso pc WHERE " + "pc.proyecto.id = '"
                + proyectoActual.getId() + "' and pc.cumplido = '" + ProyectoCompromiso.NO_CUMPLIDO + "'"
                + "and pc.tipoInforme.id = '" + TipoInforme.INFORME_AVANCE + "'";
        List<ProyectoCompromiso> listaCompromisos = servicioGeneral.obtenerObjetosLimitado(ProyectoCompromiso.class,
                hql);
        return !esListaVacia(listaCompromisos);
    }

    public void descargarCuadroResultados() {
        ProyectoInforme pin = informeSeleccionado;
        FacesContext ctx = FacesContext.getCurrentInstance();

        if (pin != null && pin.getCuadroResultados() != null) {

            try {
                if (!ctx.getResponseComplete()) {
                    HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
                    response.setContentType("text/plain");
                    response.setHeader("Content-Disposition", "attachment;filename=\"" + pin.getCuadroNombre() + "\"");
                    ServletOutputStream out = response.getOutputStream();
                    out.write(pin.getBytesCuadroResultados());
                    out.flush();
                    ctx.responseComplete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void ejecutarAccion() {
        if (accionBoton.equals("Editar")) {
            accionBoton = "Guardar";
            estadoInvestigadorTmp = estadoInvestigador;
            comentariosInvestigadorTmp = comentariosInvestigador;
            anoConvocatoriaTmp = anoConvocatoria;
            mesesAprobadosTmp = mesesAprobados;
        } else {
            // Se ejecuta la accion de Guardar
            accionBoton = "Editar";
            investigadorProyecto.setVisible(estadoInvestigador);
            investigadorProyecto.setFuncion(comentariosInvestigador);
            investigadorProyecto.setTotalHorasVinculacion(Double.valueOf(anoConvocatoria));
            investigadorProyecto.setHorasJornadaDocente((int) (mesesAprobados * 10));
            ;
            servicioGeneral.guardarObjeto(investigadorProyecto);

            for (int i = 0; i < listaEstadoRenovacion.size(); i++) {
                if (listaEstadoRenovacion.get(i).getIdentificador().getTipo().equals(estadoInvestigador)) {
                    descripcionEstInv = listaEstadoRenovacion.get(i).getDescripcion();
                }
            }

        }
    }

    public void eliminarInforme() {
        if (informeSeleccionado != null) {
            Persona responsableEliminacion = (Persona) sesion.getAttribute("persona");
            List<ProyectoInforme> informesProyecto = servicioGeneral.obtenerObjetos(ProyectoInforme.class,
                    "from ProyectoInforme where id = '" + informeSeleccionado.getId() + "'");
            if (informesProyecto != null && informesProyecto.size() > 0) {
                ProyectoInforme proyectoInforme = informesProyecto.get(0);
                EstadoInforme estadoInforme = new EstadoInforme(ProyectoInforme.ESTADO_BORRADO);
                proyectoInforme.setEstadoInforme(estadoInforme);
                proyectoInforme.setFechaEliminacion(new Date());
                proyectoInforme.setResponsableEliminacion(responsableEliminacion);

                // Se guarda historico del estado del informe
                try {
                    servicioGeneral.guardarObjeto(
                            crearHistoricoEstadoInforme(proyectoInforme, "Eliminado por investigador principal"));
                } catch (NullPointerException npe) {
                    npe.printStackTrace();
                    System.out.println("Error al guardar historico");
                }
                try {
                    servicioGeneral.guardarObjeto(proyectoInforme);
                    sesion.removeAttribute("manejadorPrincipalInforme");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getAccionBoton() {
        return accionBoton;
    }

    public String getAnoConvocatoria() {
        return anoConvocatoria;
    }

    public String getComentariosInvestigador() {
        return comentariosInvestigador;
    }

    public CorreoPlantilla getCorreoActual() {
        return correoActual;
    }

    public String getDescripcionEstInv() {
        return descripcionEstInv;
    }

    public String getEstadoInvestigador() {
        return estadoInvestigador;
    }

    public ProyectoInforme getInformeSeleccionado() {
        return informeSeleccionado;
    }

    public Persona getJovenInvestigador() {
        return jovenInvestigador;
    }

    public List<DominioDetalle> getListaEstadoRenovacion() {
        return listaEstadoRenovacion;
    }

    public List<TipoInforme> getListaFormatosInforme() {
        return listaFormatosInforme;
    }

    public List<ProyectoInforme> getListaInformes() {
        return listaInformes;
    }

    public List<ProyectoInforme> getListaSolicitudesRenovacion() {
        return listaSolicitudesRenovacion;
    }

    public double getMesesAprobados() {
        return mesesAprobados;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public String getNombrePrograma() {
        return nombrePrograma;
    }

    public Proyecto getProyectoActual() {
        return proyectoActual;
    }

    public void imprimirDetalleConsultaRenovacion() {
        Long idInforme = informeSeleccionado.getId();
        ReporteBirt r = new ReporteBirt();
        r.adicionarParametro("idInf", idInforme.toString());
        r.setNombreReporte("/cartas/solicitudRenovacion");
        r.setFormato(ReporteBirt.FORMATO_PDF);
        sesion.setAttribute("reporte", r);
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            context.getExternalContext().dispatch("/ReporteEngineServlet");
        } catch (Exception e) {

        } finally {
            context.responseComplete();
        }
    }

    public void imprimirInforme() {
        imprimirInforme(informeSeleccionado);
    }

    public String ingresardetalleConsultaInforme() {

        ProyectoInforme pin = informeSeleccionado;

        super.sesion.setAttribute("tipoInformeParam", pin.getTipoInforme().getId());
        super.sesion.setAttribute("idInforme", pin.getId());
        super.sesion.setAttribute("tipoAccion", ProyectoInforme.TIPO_ACCION_CONSULTA);
        super.sesion.setAttribute("tipoAccionBancoProyectos", ProyectoInforme.TIPO_ACCION_CONSULTA);
        super.sesion.setAttribute("esCoordinador", new Long("0"));
        super.sesion.setAttribute("consultaInformeCoordinador", esCoordinador && !consultaFacultad);
        sesion.removeAttribute("consultaInformeCoordinador");
        if (esConsultaJovenesInv) {
            super.sesion.setAttribute("anteriorFrm", "principalInforme");
            super.sesion.setAttribute("anteriorManejador", "ManejadorPrincipalInforme");
        }
        super.sesion.setAttribute("solicitudRenovacion", false);
        super.sesion.removeAttribute("proyectoRenovacion");
        agregarReglaNavegacion("detalleInforme", "ManejadorPrincipalInforme");
        if (pin.getProyecto() != null && pin.getProyecto().getModalidad() != null
                && pin.getProyecto().getModalidad().getTipo() != null
                && proyectoActual.getModalidad().getTipo().getId().equals(TipoModalidad.BANCO_PROYECTOS)
                && pin.getTipoInforme().getId().equals(TipoInforme.INFORME_FINAL)) {
            sesion.removeAttribute("manejadorRegistroInformeBancoProyectos");
            return "detalleInformeBancoProyectos";
        } else {
            sesion.removeAttribute("manejadorRegistroInforme");
            return "detalleInforme";
        }
    }

    public String ingresardetalleConsultaRenovacion() {

        ProyectoInforme pin = informeSeleccionado;
        super.sesion.setAttribute("idInforme", pin.getId());
        super.sesion.setAttribute("proyectoRenovacion", pin.getProyecto().getId());
        super.sesion.setAttribute("tipoAccion", new Long(ProyectoInforme.TIPO_ACCION_CONSULTA));
        super.sesion.setAttribute("esCoordinador", new Long("0"));
        super.sesion.setAttribute("consultaInformeCoordinador", esCoordinador && !consultaFacultad);
        sesion.removeAttribute("manejadorRegistroInforme");
        return "detalleInforme";
    }

    public String ingresardetalleEditaInforme() {

        ProyectoInforme pin = informeSeleccionado;
        super.sesion.setAttribute("idInforme", pin.getId());
        super.sesion.setAttribute("tipoInformeParam", pin.getTipoInforme().getId());
        super.sesion.setAttribute("tipoAccion", new Long(ProyectoInforme.TIPO_ACCION_EDICION));
        super.sesion.setAttribute("tipoAccionBancoProyectos", new Long(ProyectoInforme.TIPO_ACCION_EDICION));
        sesion.removeAttribute("manejadorRegistroInforme");
        sesion.removeAttribute("manejadorRegistroInformeBancoProyectos");
        sesion.removeAttribute("consultaInformeCoordinador");
        super.sesion.setAttribute("solicitudRenovacion", false);
        super.sesion.removeAttribute("proyectoRenovacion");
        if (pin.getProyecto().getModalidad() != null && pin.getProyecto().getModalidad().getTipo() != null
                && proyectoActual.getModalidad().getTipo().getId().equals(TipoModalidad.BANCO_PROYECTOS)
                && pin.getTipoInforme().getId().equals(TipoInforme.INFORME_FINAL)) {
            sesion.removeAttribute("manejadorRegistroInformeBancoProyectos");
            return "detalleInformeBancoProyectos";
        } else {
            sesion.removeAttribute("manejadorRegistroInforme");
            return "detalleInforme";
        }

    }

    public String ingresardetalleInformeAvanceNuevo() {

        super.sesion.setAttribute("solicitudRenovacion", false);
        super.sesion.removeAttribute("proyectoRenovacion");

        boolean habilitarRegistroValido = true;

        if (!esListaVacia(listaInformes)) {

            // Se otiene parametro para saber si se permiten varios informes
            Convocatoria convocatoria = (Convocatoria) this.proyectoActual.getModalidad();
            boolean permiteVariosInformesParciales = convocatoria.getVariosInformesAvance() != null
                    && "S".equals(convocatoria.getVariosInformesAvance());

            // Se validan los estados de los informes que ya existen
            for (int i = 0; i < listaInformes.size(); i++) {
                ProyectoInforme proyectoInformeItem = listaInformes.get(i);
                if (proyectoInformeItem.getTipoInforme().getId() == ProyectoInforme.TIPO_INFORME_AVANCE
                        && proyectoInformeItem.getEstadoInforme().getId() == EstadoInforme.ENVIADO
                        && !permiteVariosInformesParciales) {
                    mensajeError(
                            "No se puede adicionar un nuevo informe de avance, ya existe un informe en estado ENVIADO con código "
                                    + proyectoInformeItem.getId() + ".");
                    habilitarRegistroValido = false;
                }
                if (proyectoInformeItem.getTipoInforme().getId() == ProyectoInforme.TIPO_INFORME_AVANCE
                        && proyectoInformeItem.getEstadoInforme().getId() == EstadoInforme.INGRESANDO
                        && !permiteVariosInformesParciales) {
                    mensajeError(
                            "No se puede adicionar un nuevo informe de avance, ya existe uno en estado INGRESANDO con código "
                                    + proyectoInformeItem.getId() + ".");
                    habilitarRegistroValido = false;
                }
                if (proyectoInformeItem.getTipoInforme().getId().equals(ProyectoInforme.TIPO_INFORME_FINAL)
                        && (!proyectoInformeItem.getEstadoInforme().getId().equals(EstadoInforme.BORRADO))) { // && !permiteVariosInformesParciales (se quita esto en la condición)
                    mensajeError(
                            "No se puede adicionar un nuevo informe de avance, ya existe un informe final REGISTRADO con código "
                                    + proyectoInformeItem.getId() + ".");
                    habilitarRegistroValido = false;
                }
            }
        }

        if (habilitarRegistroValido) {
            super.sesion.setAttribute("tipoInformeParam", new Long("1"));
            super.sesion.setAttribute("tipoAccion", ProyectoInforme.TIPO_ACCION_EDICION);
            sesion.removeAttribute("manejadorRegistroInforme");
            return "detalleInforme";
        } else {
            return "";
        }

    }

    /**
     * Registro de informe final nuevo
     * 
     * @return
     */
    public String ingresardetalleInformeFinalNuevo() {
        super.sesion.setAttribute("solicitudRenovacion", false);
        super.sesion.removeAttribute("proyectoRenovacion");

        // Se valida si ya existe informe final y si puede registrar varios
        if (!esListaVacia(listaInformes)) {

            // Se obtienen parametros para saber si se pueden varios informes
            Convocatoria convocatoria = (Convocatoria) this.proyectoActual.getModalidad();
            boolean permiteVariosInformesFinales = convocatoria.getVariosInformesFinales() != null
                    && "S".equals(convocatoria.getVariosInformesFinales());

            if (!permiteVariosInformesFinales) {

                for (int i = 0; i < listaInformes.size(); i++) {
                    ProyectoInforme proyectoInformeItem = listaInformes.get(i);
                    if (proyectoInformeItem.getTipoInforme().getId().equals(ProyectoInforme.TIPO_INFORME_FINAL)
                            && !proyectoInformeItem.getEstadoInforme().getId().equals(ProyectoInforme.ESTADO_BORRADO)) {
                        mensajeError("No se puede adicionar un informe final, ya existe uno en el sistema. Si el informe final fue devuelto para correcciones es necesario editarlo y enviarlo nuevamente.");
                        return "";
                    }
                }
            }
        }

        // Se valida si tiene compromisos pendeintes de informe de avance
        if (!tieneCompromisosPendientesAvance()) {
            super.sesion.setAttribute("tipoInformeParam", new Long("2"));
            super.sesion.setAttribute("tipoAccion", ProyectoInforme.TIPO_ACCION_EDICION);

            // Si es banco de proyectos
            if (proyectoActual.getModalidad() != null && proyectoActual.getModalidad().getTipo() != null
                    && proyectoActual.getModalidad().getTipo().getId().equals(TipoModalidad.BANCO_PROYECTOS)) {
                sesion.removeAttribute("manejadorRegistroInformeBancoProyectos");
                return "detalleInformeBancoProyectos";
            } else {
                // Si no es de banco de proyectos
                sesion.removeAttribute("manejadorRegistroInforme");
                return "detalleInforme";
            }
        } else {
            mensajeError("Aún tiene pendiente informes de avance por registrar o por revisión por parte del coordinador.");
            return "";
        }

    }

    public String ingresardetallesolicitudrenovacion() {
        super.sesion.setAttribute("tipoInformeParam", new Long("2"));
        super.sesion.setAttribute("tipoAccion", ProyectoInforme.TIPO_ACCION_EDICION);
        super.sesion.setAttribute("solicitudRenovacion", true);
        super.sesion.setAttribute("proyectoRenovacion", 20898L);

        sesion.removeAttribute("manejadorRegistroInforme");
        sesion.removeAttribute("idInforme");
        return "detalleInforme";
    }

    public boolean isConsultaFacultad() {
        return consultaFacultad;
    }

    public boolean isEsConsultaJovenesInv() {
        return esConsultaJovenesInv;
    }

    public boolean isEsCoordinador() {
        return esCoordinador;
    }

    public boolean isEsInvestigadorPrincipal() {
        return esInvestigadorPrincipal;
    }

    public boolean isEsJornadaDocente() {
        return esJornadaDocente;
    }

    public boolean isEsPermisoMarco() {
        return esPermisoMarco;
    }

    public boolean isEsPryContrapartida() {
        return esPryContrapartida;
    }

    public boolean isEsTutor() {
        return esTutor;
    }

    public boolean isPermitirInformeTutor() {
        return permitirInformeTutor;
    }

    public void setAnoConvocatoria(String anoConvocatoria) {
        this.anoConvocatoria = anoConvocatoria;
    }

    public void setComentariosInvestigador(String comentariosInvestigador) {
        this.comentariosInvestigador = comentariosInvestigador;
    }

    public void setEstadoInvestigador(String estadoInvestigador) {
        this.estadoInvestigador = estadoInvestigador;
    }

    public void setInformeSeleccionado(ProyectoInforme informeSeleccionado) {
        this.informeSeleccionado = informeSeleccionado;
    }

    public void setListaFormatosInforme(List<TipoInforme> listaFormatosInforme) {
        this.listaFormatosInforme = listaFormatosInforme;
    }

    public void setListaInformes(List<ProyectoInforme> listaInformes) {
        this.listaInformes = listaInformes;
    }

    public void setMesesAprobados(double mesesAprobados) {
        this.mesesAprobados = mesesAprobados;
    }

	public Persona getCoordinadorSeguimientoProyecto() {
		return coordinadorSeguimientoProyecto;
	}

	public void setCoordinadorSeguimientoProyecto(Persona coordinadorSeguimientoProyecto) {
		this.coordinadorSeguimientoProyecto = coordinadorSeguimientoProyecto;
	}

}
