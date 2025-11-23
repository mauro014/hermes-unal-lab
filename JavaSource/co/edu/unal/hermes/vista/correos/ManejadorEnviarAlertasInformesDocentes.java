package co.edu.unal.hermes.vista.correos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.EstadoInforme;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.ProyectoCompromiso;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * The Class ManejadorEnviarAlertasInformesDocentes.
 *
 * @author Mauricio Amaya Ríos
 * @date 24/09/2015 Manejador para envio de alertas relacionados con informes
 *       que aún no han sido registrados por el investigador pero de manera
 *       consolidad.
 */
public class ManejadorEnviarAlertasInformesDocentes extends ManejadorBase {

    private static final long serialVersionUID = -455984891742465095L;

    /** Listado de investigadores con compromisos pendientes. */
    private List<String[]> listaInvestigadores;
    private final String CONSULTA_BASE = " ip.proyecto.id = pc.proyecto.id"
            + " and ip.proyecto.id = pry.id and pry.estadoProyecto.id = '" + EstadoProyecto.ACTIVO + "'"
            + " and pc.cumplido = '" + ProyectoCompromiso.NO_CUMPLIDO + "' and " + " pc.fechaVencimiento < SYSDATE and "
            + " (pc.fechaVencProrroga IS NULL or pc.fechaVencProrroga < SYSDATE)"
            // Se suma uno al numero de notificaciones ya enviadas, y se
            // multiplica por 2 que son el numero de meses entre
            // notificaciones.
            // Si esta fecha es mas antigua que el dia de hoy entonces se
            // debe mandar una notificación.
            + " and ADD_MONTHS(decode(pc.fechaVencProrroga,null,pc.fechaVencimiento,pc.fechaVencProrroga), "
            + "((pc.numeroNotificacionesAcumuladas + 1) * 2)) < SYSDATE "
            + " and (select count(*) from ProyectoInforme pi where pi.proyecto.id = pry.id and pi.tipoInforme.id = pc.tipoInforme.id "
            + "and pi.estadoInforme not in (" + EstadoInforme.BORRADO + "," + EstadoInforme.INGRESANDO + ","
            + EstadoInforme.DEVUELTO + ")) = 0 ";

    /** Plantilla utilizada para el envio de alertas de coordinador */
    private final Long PLANTILLA_INVESTIGADOR = 252L;

    public ManejadorEnviarAlertasInformesDocentes() {
        super();
        // Se realiza consulta en la base de datos.

        listaInvestigadores = generarListadoInvestigadores();
    }

    /**
     * Se cargan los investigadores con la información necesaria para las
     * alertas.
     * 
     * @author Mauricio Amaya Ríos
     * @since 25-09-2015
     * @return listado de coordinadores ordenados en un listado de cadenas
     */
    public List<String[]> generarListadoInvestigadores() {
        // Se cargan los investigadores con compromisos pendeintes pendientes./*

        // Se crea lista para organizar los resultados en un array de String.
        List<String[]> listaCoordinadores = new ArrayList<String[]>();

        List<Persona> listaInvestigadores = servicioGeneral.obtenerObjetosLimitado(Persona.class,
                "select " + " #id.documento p.id.documento, " + " #id.tipoDocumento p.id.tipoDocumento,"
                        + " #nombre1 p.nombre1, #nombre2 p.nombre2, " + " #apellido1 p.apellido1, "
                        + " #apellido2 p.apellido2, " + " #email p.email, " + " #genero count(*) from Persona p, "
                        + " ProyectoCompromiso pc, InvestigadorProyecto ip," + " Proyecto pry "
                        + " where ip.tipo = 'P' and " + " ip.investigador.id.documento = p.id.documento and "
                        + " ip.investigador.id.tipoDocumento = p.id.tipoDocumento and" + CONSULTA_BASE
                        + " group by p.id.documento, p.id.tipoDocumento, p.nombre1, "
                        + " p.nombre2, p.apellido1, p.apellido2, p.email");
        
        if (listaInvestigadores != null) {

            // Se prepara iterator para recorrer toda la lista del resultado.
            Iterator<Persona> i = listaInvestigadores.iterator();

            while (i.hasNext()) {

                Persona p = i.next();
                String[] investigador = new String[6];
                // Se recorre la lista y se crea array de String según los
                // resultados

                investigador[0] = p.getNombreCompletoMinusculas();
                investigador[1] = p.getGenero();
                investigador[2] = p.getId().getDocumento();
                investigador[3] = p.getId().getTipoDocumento();
                investigador[4] = "true";
                investigador[5] = p.getEmail();

                listaCoordinadores.add(investigador);

            }

        }

        return listaCoordinadores;
    }

    /**
     * Se realiza el envio de las alertas de acuerdo a las personas
     * seleccionadas y se actualiza el numero de notificación en la base de
     * datos-
     * 
     * @author Mauricio Amaya Ríos
     * @since 25-08-2015
     */
    public void enviarNotificaciones() {
        Iterator<String[]> i = listaInvestigadores.iterator();
        while (i.hasNext()) {
            String[] investigador = i.next();
            // Se valida que se halla seleccionado
            if (investigador[4].equals("true")) {
                // Se obtienen las alertas de cada persona seleccionada.
                List<ProyectoCompromiso> compromisoProyecto = servicioGeneral.obtenerObjetosLimitado(
                        ProyectoCompromiso.class,
                        "select #id pc.id," + "#nombreTipoInforme pc.proyecto.id, " + "#tipoInforme pc.tipoInforme, "
                                + "#cumplido pc.proyecto.nombre " + "from ProyectoCompromiso pc, "
                                + "InvestigadorProyecto ip," + " Proyecto pry where " + CONSULTA_BASE
                                + " and ip.tipo = 'P' and ip.investigador.id.documento = '" + investigador[2] + "'"
                                + " and ip.investigador.id.tipoDocumento = '" + investigador[3] + "'");

                // Se verifica que la consulta haya tenido resultados.
                if (compromisoProyecto != null && compromisoProyecto.size() > 0) {

                    String cadenaCompromisos = "<table border='1' cellspacing='0' cellpadding='3' >"
                            + "<tr><th style='text-align: center;'>Código proyecto</th>"
                            + "<th style='text-align: center;'>Tipo informe</th>"
                            + "<th style='text-align: center;'>Nombre proyecto</th>"
                            + "<th style='text-align: center;'>Coordinador de seguimiento</th></tr>";
                    Iterator<ProyectoCompromiso> j = compromisoProyecto.iterator();
                    // Se itera sobre el listado de alertas devueltas.
                    while (j.hasNext()) {
                        ProyectoCompromiso proyectoCompromiso = j.next();

                        String consultaCoordinador = "select p from InvestigadorInterno p, ProyectoCoordinador pc "
                                + " where pc.idProyecto = '" + proyectoCompromiso.getNombreTipoInforme()
                                + "' and p.id.documento = pc.perId and p.id.tipoDocumento = pc.tdoId ";
                        List<InvestigadorInterno> investigadorInternos = servicioGeneral
                                .obtenerObjetos(InvestigadorInterno.class, consultaCoordinador);
                        // Se agrega información de la alerta según lo devuelto
                        // en la consulta.
                        String nombreCoordinador = "";
                        if (!esListaVacia(investigadorInternos)) {
                            InvestigadorInterno investigadorInterno = investigadorInternos.get(0);
                            nombreCoordinador = investigadorInterno.getNombreCompletoMinusculas();
                            if (investigadorInterno.getTelExtension() != null) {
                                nombreCoordinador += " Ext: " + investigadorInterno.getTelExtension();
                            }
                        }

                        cadenaCompromisos += "<tr><td style='text-align: center;'>"
                                + proyectoCompromiso.getNombreTipoInforme() + "</td><td>"
                                + proyectoCompromiso.getTipoInforme().getNombre() + "</td><td>"
                                + proyectoCompromiso.getCumplido() + "</td><td>" + nombreCoordinador + "</td></tr>";

                    }
                    cadenaCompromisos += "</table>";
                    boolean error = false;
                    if (cadenaCompromisos.length() > 0) {
                        // Cargar plantilla para el envío de alertas.
                        CorreoPlantilla correoPlantilla = cargarPlantillaCorreo(PLANTILLA_INVESTIGADOR);
                        Correo correo = new Correo();
                        String asunto = correoPlantilla.getAsunto();
                        asunto = asunto.replaceAll("<<INVESTIGADOR>>", investigador[0]);
                        correo.setAsunto(asunto);
                        
                        correo.setOrigen(Correo.CORREO_HERMES);
                        correo.adicionarDireccion(investigador[5]);
                        correo.adicionarDireccion(Correo.CORREO_HERMES);
                        String cuerpoCorreo = correoPlantilla.getCuerpo().replaceAll("<<INVESTIGADOR>>",
                                investigador[0]);
                        cuerpoCorreo = cuerpoCorreo.replaceAll("<<COMPROMISOS>>", cadenaCompromisos);
                        correo.setCuerpo(cuerpoCorreo);
                        // Si se envia correo se realiza la actualización en la
                        // base de datos del numero de notificaciones enviada.
                        if (servicioCorreo.enviarCorreo(correo, true)) {
                            Iterator<ProyectoCompromiso> k = compromisoProyecto.iterator();
                            while (k.hasNext() && !error) {
                                ProyectoCompromiso proyectoCompromiso = k.next();
                                String sql = "UPDATE HER_PROYECTO_COMPROMISO " + " SET "
                                        + "COM_NUMERO_NOTI_ACUMULADA = COM_NUMERO_NOTI_ACUMULADA + 1,"
                                        + "COM_FECHA_NOTI_ACUMULADA = SYSDATE " + "WHERE COM_ID = '"
                                        + proyectoCompromiso.getId() + "'";
                                servicioGeneral.ejecutarSentencia(sql);
                            }
                        } else {
                            error = true;
                        }
                    }
                    if (error) {
                        mostrarMensaje("Ha ocurrido un error al enviar los mensajes", null,
                                FacesMessage.SEVERITY_ERROR);

                    } else {
                        mostrarMensaje("Las alertas han sido enviadas a los coordinadores.", null,
                                FacesMessage.SEVERITY_INFO);
                    }
                }
            }
        }
        // Se cargan los coordinadores con alertas pendientes.
        listaInvestigadores = generarListadoInvestigadores();
    }

    /**
     * Cargar correo plantilla de correo segun parametro enviado.
     *
     * @author Mauricio Amaya Ríos
     * @param tipo
     *            the tipo
     * @return PlantillaCorreo con información
     * @date 17/04/2015
     */
    private CorreoPlantilla cargarPlantillaCorreo(Long nPlantilla) {
        if (nPlantilla != 0L) {
            return servicioCorreo.obtenerPlantillaCorreoCompleta(nPlantilla);
        } else
            return null;
    }

    /**
     * Gets the lista investigadores.
     *
     * @return the lista investigadores
     */
    public List<String[]> getListaInvestigadores() {
        return listaInvestigadores;
    }

    public int getNumeroInvestigadores() {
        if (listaInvestigadores != null) {
            return listaInvestigadores.size();
        } else {
            return 0;
        }
    }

    /**
     * @author Mauricio Amaya Ríos
     * @since 31-08-2015
     * 
     * @param msg
     *            con mensaje que sera enviado.
     * @param component
     *            el componente al que esta asociado el mensaje para que lo
     *            bloquee.
     * @param el
     *            tipo de rror para que este se muestre si es error, informativo
     *            o alerta.
     */
    protected void mostrarMensaje(String msg, UIComponent component, Severity s) {
        FacesMessage message = new FacesMessage(s, msg, msg);
        FacesContext context = FacesContext.getCurrentInstance();
        if (component == null) {
            context.addMessage(null, message);
        } else {
            context.addMessage(component.getClientId(context), message);
        }
    }

}
