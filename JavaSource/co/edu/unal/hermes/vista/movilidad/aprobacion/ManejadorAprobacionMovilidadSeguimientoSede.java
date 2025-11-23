/**
 * Modified by Mauricio Amaya Ríos<br/>
 * Date:  12/12/2013<br/>
 */

package co.edu.unal.hermes.vista.movilidad.aprobacion;

/**
 * The Class ManejadorAprobacionMovilidadSeguimiento.
 */
public class ManejadorAprobacionMovilidadSeguimientoSede extends ManejadorAprobacionMovilidadSeguimientoBase {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -7222219828887959842L;

    /**
     * Instantiates a new manejador aprobacion movilidad seguimiento.
     */
    public ManejadorAprobacionMovilidadSeguimientoSede() {
        boolean sede = true;
        cargarListasSeguimientoMovilidades(sede);
    }
    
    /**
     * Consultar seguimiento vis ext.
     *
     * @return the string
     */
    public String consultarSeguimientoVisExt() {
        sesion.setAttribute("esRevisionFacultad", false);
        sesion.setAttribute("esRevisionSede", true);
        boolean consultaFacultad = true;
        boolean esConsulta = true;
        return consultarSeguimientoVisExt(movilidadVisitanteSeleccionada.getId(), consultaFacultad, esConsulta);
    }

    /**
     * Consultar seguimiento doc.
     *
     * @return the string
     */
    public String consultarSeguimientoDoc() {
        sesion.setAttribute("esRevisionFacultad", false);
        sesion.setAttribute("esRevisionSede", true);
        boolean consultaFacultad = true;
        boolean esConsulta = true;
        return ingresarSeguimientoDoc(movilidadDocentesSeleccionada.getId(),consultaFacultad,esConsulta);
    }

    /**
     * Consultar seguimiento est eventos.
     *
     * @return the string
     */
    public String consultarSeguimientoEstEventos() {
        sesion.setAttribute("esRevisionFacultad", false);
        sesion.setAttribute("esRevisionSede", true);
        boolean consultaFacultad = true;
        boolean esConsulta = true;
        return consultarSeguimientoEstPos(movilidadEstudiantesSeleccionada.getId(),consultaFacultad,esConsulta);
    }

    /**
     * Consultar seguimiento est pasantias.
     *
     * @return the string
     */
    public String consultarSeguimientoEstPasantias() {
        sesion.setAttribute("esRevisionFacultad", false);
        sesion.setAttribute("esRevisionSede", true);
        boolean consultaFacultad = true;
        boolean esConsulta = true;
        return ingresarSeguimientoEstRes(movilidadEstudiantesSeleccionada.getId(),consultaFacultad,esConsulta);
    }

}