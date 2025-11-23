package co.edu.unal.hermes.vista.grupos;

import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * The Class ManejadorMenuFormularioGrupos.
 */
public class ManejadorMenuFormularioGrupos extends ManejadorBase {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1840958796125200532L;

    /** The grupo actual. */
    private Grupo grupoActual;

    /** The es consulta. */
    private boolean esConsulta;

    /** The es coordinador. */
    private boolean esCoordinador;

    /**
     * Instantiates a new manejador menu formulario grupos.
     */
    public ManejadorMenuFormularioGrupos() {
        super();
        grupoActual = (Grupo) sesion.getAttribute("grupo");
        if (sesion.getAttribute("esConsulta") != null) {
            esConsulta = (Boolean) sesion.getAttribute("esConsulta");
        }
        if (sesion.getAttribute("esCoordinador") != null) {
            esCoordinador = (Boolean) sesion.getAttribute("esCoordinador");
        }
    }

    /**
     * Gets the opcion especifica.
     *
     * @return the opcion especifica
     */
    public boolean getOpcionEspecifica() {
        return grupoActual != null && grupoActual.getEstadoMenu() >= 4;
    }

    /**
     * Gets the opcion integrantes.
     *
     * @return the opcion integrantes
     */
    public boolean getOpcionIntegrantes() {
        return grupoActual != null && grupoActual.getEstadoMenu() >= 2;
    }

    /**
     * Gets the opcion lineas.
     *
     * @return the opcion lineas
     */
    public boolean getOpcionLineas() {
        return grupoActual != null && grupoActual.getEstadoMenu() >= 3;
    }

    /**
     * Checks if is es consulta.
     *
     * @return true, if is es consulta
     */
    public boolean isEsConsulta() {
        return esConsulta;
    }

    /**
     * Checks if is es coordinador.
     *
     * @return true, if is es coordinador
     */
    public boolean isEsCoordinador() {
        return esCoordinador;
    }

}
