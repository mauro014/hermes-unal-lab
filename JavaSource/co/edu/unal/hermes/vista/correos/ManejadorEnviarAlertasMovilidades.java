/**
 * @author Mauricio Amaya Ríos
 */

package co.edu.unal.hermes.vista.correos;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.MovilidadDocentesExterior;
import co.edu.unal.hermes.modelo.MovilidadEstudiantesPosgrado;
import co.edu.unal.hermes.modelo.MovilidadVisitanteExterior;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * The Class ManejadorEnviarAlertasMovilidades.
 *
 * @author Mauricio Amaya Ríos
 * @date 16/04/2015 Manejador para envio de alertas a movilidades de
 *       internacionalización que aún no han presentado el informe de
 *       seguimietno.
 */
public class ManejadorEnviarAlertasMovilidades extends ManejadorBase {

    private static final long serialVersionUID = 2432117360062121440L;

    /** Elementos de tipo movilidad. */
    private List<String[]> tiposMovilidad;

    /** The tipos movilidad item. */
    private List<SelectItem> tiposMovilidadItem;

    /** The valor tipo movilidad. */
    private String valorTipoMovilidad;

    /** The tipo movilidad seleccionada. */
    private String[] tipoMovilidadSeleccionada;

    /** Listado movilidades. */
    private List<String[]> movilidadesEncontradas;

    /** The movilidades enviadas. */
    private List<String[]> movilidadesEnviadas;

    /** Constantes para tipos de movilidades. */
    private static final String MOVILIDAD_VISITANTE = "1";

    /** The movilidad docentes. */
    private static final String MOVILIDAD_DOCENTES = "2";

    /** The movilidad estudiantes. */
    private static final String MOVILIDAD_ESTUDIANTES = "3";

    /** The movilidad docentes. */
    private static final String MOVILIDAD_ESTUDIANTES_PASANTIAS = "4";

    /** plantilla por modalidad. */
    private static final Long PLANTILLA_MOVILIDAD = 221L;

    /** Variable para validar si es la primera vez que se consulta. */
    private boolean primeraVez;

    /** Variable para mostrar envío. */
    private boolean envioRealizado;

    /** Objetos necesarias para le edición de la plantilla de correo. */
    private CorreoPlantilla correoPlantilla;

    /** Objeto para la gestión de fechas *. */
    SimpleDateFormat format;

    /**
     * Instancia un nuevo manejadorEnviarAlertasMovilidades.
     */
    public ManejadorEnviarAlertasMovilidades() {
        super();

        // Se cargan tipos de movilidades a las que se les puede enviar las
        // alertas
        tiposMovilidad = crearListaTipo();
        tiposMovilidadItem = crearListaTipoItem(tiposMovilidad);

        // Cargar que es primera vez
        primeraVez = true;

        // Formateador de las fechas
        format = new SimpleDateFormat("dd/MM/yyyy");

    }

    /**
     * Crear lista tipo. Se cargan los item por tipos de movilidades de acuerdo
     * a un listado de tipos movilidades enviados
     *
     * @author Mauricio Amaya Ríos
     * @param tiposMovilidad
     *            tipos movilidad de tipo vector de string
     * @return lista con tipos item
     * @date 16/04/2015
     */
    private List<SelectItem> crearListaTipoItem(List<String[]> tiposMovilidad) {
        List<SelectItem> tiposMovilidadItem = new ArrayList<SelectItem>();
        Iterator<String[]> i = tiposMovilidad.iterator();
        while (i.hasNext()) {
            String[] tipoMovilidad = i.next();
            tiposMovilidadItem.add(new SelectItem(tipoMovilidad[0], tipoMovilidad[1]));
        }
        return tiposMovilidadItem;
    }

    /**
     * Crear lista tipo. Se cargan los tipos de movilidades que estan soportados
     * para el envío de alertas.
     * 
     * @author Mauricio Amaya Ríos
     * @return lista con tipos
     * @date 16/04/2015
     */
    private List<String[]> crearListaTipo() {
        List<String[]> tiposMovilidadTemporal = new ArrayList<String[]>();
        tiposMovilidadTemporal
                .add(new String[] { MOVILIDAD_VISITANTE, "Apoyo para la visita a la UN investigadores y artistas con "
                        + "residencia permanente en el extranjero" });
        tiposMovilidadTemporal
                .add(new String[] { MOVILIDAD_DOCENTES, "Apoyo a docentes investigadores o creadores de la UN para "
                        + "presentación en colombia o en el extranjero." });
        tiposMovilidadTemporal.add(new String[] { MOVILIDAD_ESTUDIANTES,
                "Apoyo a estudiantes de pregrado y posgrado para presentación en " + "colombia y en el extranjero." });
        tiposMovilidadTemporal.add(new String[] { MOVILIDAD_ESTUDIANTES_PASANTIAS,
                "Apoyo a estudiantes de pregrado y posgrado para pasantias en el extranjero." });
        return tiposMovilidadTemporal;
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
     * Obtiene tipo movilidad actualmente seleccionada en la lista de tipos.
     *
     * @author Mauricio Amaya Ríos
     * @param tiposMovilidad
     *            the tipos movilidad
     * @param valorSeleccionado
     *            the valor seleccionado
     * @return tipoSeleccionado [0]id Tipo [1] valor tipo
     * @date 16/04/2015
     */
    private static String[] obtenerTipoMovilidadSeleccionada(List<String[]> tiposMovilidad, String valorSeleccionado) {
        Iterator<String[]> i = tiposMovilidad.iterator();
        while (i.hasNext()) {
            String[] tipoMovilidad = i.next();
            if (tipoMovilidad[0].equals(valorSeleccionado)) {
                return tipoMovilidad;
            }
        }
        return null;
    }

    /**
     * Obtiene movilidades que no han registrado informe de seguimiento
     * dependiendo del tipo de movilidad seleccionado.
     *
     * @author Mauricio Amaya Ríos
     * @param tipoMoviliad
     *            the tipo moviliad
     * @return movilidades sin registro de informe de seguimiento [0] Id [1]
     *         Fecha solicitud [2] Fecha inicial [3] Nombre docente solicitante
     *         [4] Campo boolean para uso en vista [5] Titulo convocatoria [6]
     *         Fecha final [7] Tipo de notificacion 1: primeros 10 días, 2:
     *         segundos 10 días, 3 si > 30 [8] Email docente solicitante [9]
     *         TituloConvocatoriaPadre [10] Numero de notificaciones [11] Fecha
     *         de notificacion
     * @date 16/04/2015
     */
    private List<String[]> buscarMovilidades(String[] tipoMoviliad) {
        envioRealizado = false;
        // Se carga nombre de la tabla dependiendo del tipoMovilidad
        // seleccionado
        Class<?> clase = obtenerClase(tipoMoviliad[0]);

        List<String[]> movilidadesEncontradas = new ArrayList<String[]>();
        if (clase != null) {
            // Se genera la consulta sql de acuerdo al nombre de tabla
            // seleccionada
            String sql = obtenerConstulaSql(clase.getSimpleName(), tipoMoviliad[0]);
            // Se obtiene la información de las movilidades
            List<?> movilidades = servicioGeneral.obtenerObjetosLimitado(clase, sql);
            Iterator<?> i = movilidades.iterator();
            while (i.hasNext()) {
                Object movilidad = (Object) i.next();
                String fechaNotificacion = "";
                try {
                    Method method = clase.getMethod("getFechaNotificacion");
                    Date fechaNotificacionDate = (Date) method.invoke(movilidad);
                    if (fechaNotificacionDate != null) {
                        fechaNotificacion = format.format((Date) method.invoke(movilidad));
                    }
                    movilidadesEncontradas
                            .add(new String[] {
                                    // Se carga el id de la solicitud
                                    ((Long) clase.getMethod("getId").invoke(movilidad)).toString(),
                                    // Se carga la fecha de la solicitud
                                    format.format(
                                            (Date) clase.getMethod("getFechasolicitud").invoke(movilidad)),
                                    // Se carga la fecha inicial
                                    format.format((Date) clase.getMethod("getFechainicial").invoke(movilidad)),
                                    // Se carga el nombre del docente
                                    (String) clase.getMethod("getAceptacion").invoke(movilidad),
                                    // Se define campo para determinar si se
                                    // envia o no
                                    "true",
                                    // Se carga el titlo de la convocatoria
                                    (String) clase.getMethod("getEvento").invoke(movilidad),
                                    // Se carga la fecha final
                                    format.format((Date) clase.getMethod("getFechafinal").invoke(movilidad)),
                                    // Se carga el tipo de notificacion
                                    ((Integer) clase.getMethod("getTipoNotificacion").invoke(movilidad)) == null
                                            ? "0"
                                            : ((Integer) clase.getMethod("getTipoNotificacion")
                                                    .invoke(movilidad)).toString(),
                                    // Se carga el email de docente
                                    (String) clase.getMethod("getResumen").invoke(movilidad),
                                    // Se carga el titulo de convocatoria padre
                                    (String) clase.getMethod("getTitulo").invoke(movilidad),
                                    // Se carga el numero de notificaciones
                                    ((Integer) clase.getMethod("getNumeroNotificaciones")
                                            .invoke(movilidad)) == null ? "0"
                                                    : ((Integer) clase.getMethod("getNumeroNotificaciones")
                                                            .invoke(movilidad)).toString(),
                                    fechaNotificacion });
                } catch (NoSuchMethodException e) {
                    System.out.println("No se encontro el metodo en la movililidad.");
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return movilidadesEncontradas;
    }

    /**
     * Obtener constula sql.
     *
     * @param nombreTabla
     *            nombre tabla para generar consulta
     * @return the string
     */
    private String obtenerConstulaSql(String nombreTabla, String opcionSeleccionado) {
        String sqlEstudiantes = "";
        if (nombreTabla.equals("MovilidadEstudiantesPosgrado")) {
            if (opcionSeleccionado.equals(MOVILIDAD_ESTUDIANTES_PASANTIAS)) {
                sqlEstudiantes = " and mv.tipoMovilidad.id = 'D1' ";
            } else {
                sqlEstudiantes = " and mv.tipoMovilidad.id = 'MOV3_IN' ";
            }
        }
        String sql = "select  " + "#id mv.id, #fechasolicitud mv.fechasolicitud," + "#fechainicial mv.fechainicial, "
                + "#aceptacion " + "mv.personaInv.nombre1 || ' ' || mv.personaInv.nombre2 || ' ' ||"
                + "mv.personaInv.apellido1 || ' ' || mv.personaInv.apellido2, " + "#evento c.titulo, "
                + "#fechafinal mv.fechafinal , " + "#tipoNotificacion mv.tipoNotificacion, "
                + "#resumen mv.personaInv.email," + "#titulo c.padre.titulo,"
                + "#numeroNotificaciones mv.numeroNotificaciones, " + "#fechaNotificacion mv.fechaNotificacion "
                + "from " + nombreTabla + " mv," + "Persona per, Convocatoria c " + "where "
                + "(mv.estadoSeguimiento <> 'F' or " + "mv.estadoSeguimiento is null) " + "and mv.aprobacion = 'SI' "
                + "and mv.aceptacion = 'SI' " + "and mv.fechasolicitud > to_date('01/01/2013','DD/MM/yyyy') "
                + "and mv.fechafinal < SYSDATE " + "and mv.personaInv.id.documento = per.id.documento "
                + "and mv.personaInv.id.tipoDocumento = per.id.tipoDocumento " + "and mv.convocatoria.id = c.id "
                + "and (mv.tipoNotificacion < 3 or mv.tipoNotificacion is null)" + sqlEstudiantes + "order by mv.id";
        return sql;
    }

    /**
     * Obtener nombre tabla para realizar las consultas pertinentenentes.
     *
     * @param tipoMovilidadSeleccionada
     *            the tipo movilidad seleccionada
     */
    private Class<?> obtenerClase(String tipoMovilidadSeleccionada) {
        if (tipoMovilidadSeleccionada.equals(MOVILIDAD_VISITANTE)) {
            return MovilidadVisitanteExterior.class;
        } else if (tipoMovilidadSeleccionada.equals(MOVILIDAD_DOCENTES)) {
            return MovilidadDocentesExterior.class;
        } else if (tipoMovilidadSeleccionada.equals(MOVILIDAD_ESTUDIANTES)
                || tipoMovilidadSeleccionada.equals(MOVILIDAD_ESTUDIANTES_PASANTIAS)) {
            return MovilidadEstudiantesPosgrado.class;
        } else
            return null;
    }

    /**
     * Cargar movilidades.
     *
     * @author Mauricio Amaya Ríos
     * @throws Exception
     *             the exception
     * @date 16/04/2015
     */
    public void cargarMovilidades() throws Exception {

        // Se cambia que ya no es primera vez para la vista
        primeraVez = false;

        // Se obtiene tipo de movilidad seleccionada
        tipoMovilidadSeleccionada = obtenerTipoMovilidadSeleccionada(tiposMovilidad, valorTipoMovilidad);

        if (tipoMovilidadSeleccionada != null) {
            // Se buscan las movilidades según el tipo seleccionado
            movilidadesEncontradas = buscarMovilidades(tipoMovilidadSeleccionada);

            // Cargar plantilla de acuerdo al tipo movilidad seleccionada
            correoPlantilla = cargarPlantillaCorreo(PLANTILLA_MOVILIDAD);

        }
    }

    /**
     * Enviar notificaciones, accion llamada directamente desde la pagina y que
     * dependiendo del tipo de modalidad cargada realiza el envio y la
     * actualización en la tabla que sea necesario.
     * 
     * @author Mauricio Amaya Ríos
     * @date 20/04/2015
     */
    public void enviarNotificaciones() {
        if (tipoMovilidadSeleccionada[0].equals(MOVILIDAD_VISITANTE)) {
            enviarNotificaciones("HER_MOVILIDAD_VISITANTES_EXT");
        } else if (tipoMovilidadSeleccionada[0].equals(MOVILIDAD_DOCENTES)) {
            enviarNotificaciones("HER_MOVILIDAD_DOCENTES_EVENTOS");
        } else if (tipoMovilidadSeleccionada[0].equals(MOVILIDAD_ESTUDIANTES)
                || tipoMovilidadSeleccionada[0].equals(MOVILIDAD_ESTUDIANTES_PASANTIAS)) {
            enviarNotificaciones("HER_MOVILIDAD_ESTUDIANTE_POS");
        }
    }

    /**
     * Envio de notificaciones a docentes si esta seleccionado en la vista segun
     * parametro.
     *
     * @author Mauricio Amaya Ríos
     * @param nombreTabla
     *            the nombre tabla
     * @date 20/04/2015
     */
    public void enviarNotificaciones(String nombreTabla) {
        Iterator<String[]> i = movilidadesEncontradas.iterator();
        movilidadesEnviadas = new ArrayList<String[]>();
        while (i.hasNext()) {
            String[] movilidad = i.next();
            // Se valida que tenga datos validos y de acorde a lo que se espera
            if (movilidad != null && movilidad.length == 12) {
                // Se valida que se halla seleccionado
                if (movilidad[4].equals("true")) {
                    Date fechaFinal = null;
                    try {
                        fechaFinal = format.parse(movilidad[6]);
                    } catch (ParseException e) {
                        fechaFinal = null;
                    }
                    // Se valida si se cargo la fecha correctamente
                    if (fechaFinal != null) {
                        // Boolean para determinar si se debe enviar
                        // notificación
                        // o no, dado que ya se pudieron haber enviado las 3
                        boolean enviarNotificacion = false;
                        int tipoNotificacion = 0;
                        // Se optienen numero de notificaciones
                        int notificaciones = Integer.parseInt(movilidad[7]);

                        // Se calculan cuantos dias han pasado luego de la
                        // movilidad
                        Date fechaActual = new Date();
                        long diasRestantes = (fechaActual.getTime() - fechaFinal.getTime()) / 86400000; // 1000
                                                                                                        // *
                                                                                                        // 60
                                                                                                        // *
                                                                                                        // 60
                                                                                                        // *
                                                                                                        // 24
                        // Alerta entrega Informe de seguimiento, un día después
                        if (notificaciones == 0 && diasRestantes <= 10) {
                            enviarNotificacion = true;
                            tipoNotificacion = 1;
                        }
                        // Alerta entrega Informe Final despues de 10 días
                        else if (notificaciones <= 1 && diasRestantes > 10 && diasRestantes <= 30) {
                            enviarNotificacion = true;
                            tipoNotificacion = 2;
                        }
                        // Alerta entrega Informe Final despues de 30 días
                        else if (notificaciones <= 2 && diasRestantes > 30) {
                            enviarNotificacion = true;
                            tipoNotificacion = 3;
                        }
                        // Se carga la informción de la movilidad en el proyecto
                        // y se envía
                        if (enviarNotificacion) {
                            Correo correo = new Correo();
                            String asunto = correoPlantilla.getAsunto().replaceAll("<<ID>>", movilidad[0]);
                            correo.setAsunto(asunto);
                            correo.setOrigen(Correo.CORREO_HERMES);
                            correo.adicionarDireccion(movilidad[8]);
                            correo.adicionarDireccion(Correo.CORREO_HERMES);
                            String cuerpoCorreo = correoPlantilla.getCuerpo().replaceAll("<<ID>>", movilidad[0]);
                            cuerpoCorreo = cuerpoCorreo.replaceAll("<<MODALIDAD>>", movilidad[5]);
                            cuerpoCorreo = cuerpoCorreo.replaceAll("<<CONVOCATORIA>>", movilidad[9]);
                            correo.setCuerpo(cuerpoCorreo);
                            // Si se envia correo se actualizan los datos en
                            // base de datos
                            // y se prepara la vista para mostrar
                            if (servicioCorreo.enviarCorreo(correo)) {
                                String sql = "update " + nombreTabla + " set MOV_FECHA_NOTIFICACION = SYSDATE, "
                                        + "MOV_TIPO_NOTIFICACION = '" + tipoNotificacion + "',  "
                                        + "MOV_NUMERO_NOTIFICACIONES = " + (Long.parseLong(movilidad[10]) + 1L)
                                        + "where MOV_ID = '" + movilidad[0] + "'";
                                servicioGeneral.ejecutarSentencia(sql);
                                movilidadesEnviadas.add(movilidad);
                            }
                        }

                    }
                }
            }
        }
        envioRealizado = true;
    }

    /**
     * Gets tipos movilidad item.
     *
     * @return tipos movilidad item
     */
    public List<SelectItem> getTiposMovilidadItem() {
        return tiposMovilidadItem;
    }

    /**
     * Gets valor tipo movilidad.
     *
     * @return valor Tipo Movilidad
     */
    public String getValorTipoMovilidad() {
        return valorTipoMovilidad;
    }

    /**
     * Sets valor tipo movilidad.
     *
     * @param valorTipoMovilidad
     *            nuevo valor tipo movilidad
     */
    public void setValorTipoMovilidad(String valorTipoMovilidad) {
        this.valorTipoMovilidad = valorTipoMovilidad;
    }

    /**
     * Gets primera vez.
     *
     * @return tipo primera vez.
     */
    public boolean isPrimeraVez() {
        return primeraVez;
    }

    /**
     * Gets tipo solicitud seleccionada.
     *
     * @return tipo solicitud seleccionada
     */
    public String[] getTipoMovilidadSeleccionada() {
        return tipoMovilidadSeleccionada;
    }

    /**
     * Gets movilidades encontradas.
     *
     * @return movilidades encontradas
     */
    public List<String[]> getMovilidadesEncontradas() {
        return movilidadesEncontradas;
    }

    /**
     * Gets correo plantilla.
     *
     * @return correo plantilla
     */
    public CorreoPlantilla getCorreoPlantilla() {
        return correoPlantilla;
    }

    /**
     * Sets the correo plantilla.
     *
     * @param correoPlantilla
     *            nuevo correo plantilla
     */
    public void setCorreoPlantilla(CorreoPlantilla correoPlantilla) {
        this.correoPlantilla = correoPlantilla;
    }

    /**
     * Gets movilidades enviadas.
     *
     * @return movilidades enviadas
     */
    public List<String[]> getMovilidadesEnviadas() {
        return movilidadesEnviadas;
    }

    /**
     * Checks if is envio realizado.
     *
     * @return true, if is envio realizado
     */
    public boolean isEnvioRealizado() {
        return envioRealizado;
    }

    /**
     * Obtener tamaño de solicitudes encontradas
     */
    public int getNumeroMovilidadEncontradas() {
        if (movilidadesEncontradas != null) {
            return movilidadesEncontradas.size();
        } else
            return 0;
    }
}
