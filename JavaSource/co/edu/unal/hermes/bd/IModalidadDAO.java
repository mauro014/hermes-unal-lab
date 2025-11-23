package co.edu.unal.hermes.bd;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;

import co.edu.unal.hermes.modelo.CalificacionEvaluacion;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaExterna;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.ConvocatoriaPadreParametrizacion;
import co.edu.unal.hermes.modelo.ConvocatoriaParametrizacion;
import co.edu.unal.hermes.modelo.ConvocatoriaTerminosReferencia;
import co.edu.unal.hermes.modelo.CorteConvocatoria;
import co.edu.unal.hermes.modelo.CriterioEvaluacion;
import co.edu.unal.hermes.modelo.EstadoConvocatoria;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Modalidad;
import co.edu.unal.hermes.modelo.ModalidadCriterioTipoPregunta;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.ProductoTipo;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.RubroFinanciable;
import co.edu.unal.hermes.modelo.RubroFinanciableArbol;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.TipoModalidad;
import co.edu.unal.hermes.modelo.TipoRubro;

/**
 * Interface para obtener y guardar convocatorias.
 *
 * @trows lanza la excepcion cuando no se puede acceder a los datos de los
 *        grupos
 */

public interface IModalidadDAO {
    
    /**
     * Resumen estado proyectos.
     *
     * @param idConvocatoria the id convocatoria
     * @return the list
     * @throws SQLException the SQL exception
     */
    public List resumenEstadoProyectos(String idConvocatoria) throws SQLException;

    /**
     * Resumen estado proyectos movilidad.
     *
     * @param idConvocatoria the id convocatoria
     * @param tipoConvocatoria the tipo convocatoria
     * @return the list
     * @throws SQLException the SQL exception
     */
    public List resumenEstadoProyectosMovilidad(String idConvocatoria, String tipoConvocatoria) throws SQLException;

    /**
     * Obtener rubros.
     *
     * @param idFiltro the id filtro
     * @param opcion the opcion
     * @return the list
     * @throws DataAccessException the data access exception
     */
    public List<TipoRubro> obtenerRubros(Long idFiltro, int opcion) throws DataAccessException;

    /**
     * Obtener convocatorias.
     *
     * @param where the where
     * @param isParametros the is parametros
     * @param parametros the parametros
     * @return the list
     * @throws DataAccessException the data access exception
     */
    public List obtenerConvocatorias(String where, boolean isParametros, Object[] parametros)
            throws DataAccessException;

    /**
     * Guardar convocatoria.
     *
     * @param convocatoria the convocatoria
     * @throws DataAccessException the data access exception
     */
    public void guardarConvocatoria(Convocatoria convocatoria) throws DataAccessException;

    /**
     * Obtener convocatoria.
     *
     * @param id the id
     * @return the convocatoria
     * @throws DataAccessException the data access exception
     */
    public Convocatoria obtenerConvocatoria(Long id) throws DataAccessException;

    /**
     * Obtener convocatorias.
     *
     * @param estado the estado
     * @return the list
     * @throws DataAccessException the data access exception
     */
    public List obtenerConvocatorias(EstadoConvocatoria estado) throws DataAccessException;

    /**
     * Obtener modalidad.
     *
     * @param id the id
     * @return the modalidad
     * @throws DataAccessException the data access exception
     */
    public Modalidad obtenerModalidad(Long id) throws DataAccessException;

    /**
     * Obtener convocatoria edicion.
     *
     * @param id the id
     * @return the convocatoria
     * @throws DataAccessException the data access exception
     */
    public Convocatoria obtenerConvocatoriaEdicion(Long id) throws DataAccessException;

    /**
     * Obtener convocatoria requisitos.
     *
     * @param id the id
     * @return the convocatoria
     * @throws DataAccessException the data access exception
     */
    public Convocatoria obtenerConvocatoriaRequisitos(Long id) throws DataAccessException;

    /**
     * Obtener convocatoriasx padre.
     *
     * @param padre the padre
     * @return the list
     * @throws DataAccessException the data access exception
     */
    public List<Convocatoria> obtenerConvocatoriasxPadre(ConvocatoriaPadre padre) throws DataAccessException;

    /**
     * Obtener convocatorias X padre Y coordinador.
     *
     * @param idPadre the id padre
     * @param idCoordinador the id coordinador
     * @return the list
     * @throws DataAccessException the data access exception
     */
    public List obtenerConvocatoriasXPadreYCoordinador(Long idPadre, IdPersona idCoordinador)
            throws DataAccessException;

    /**
     * Obtener rubros financiables.
     *
     * @param conv the conv
     * @return the list
     * @throws DataAccessException the data access exception
     */
    public List obtenerRubrosFinanciables(Modalidad conv) throws DataAccessException;

    /**
     * Obtener productos convocatoria.
     *
     * @param conv the conv
     * @return the list
     * @throws DataAccessException the data access exception
     */
    public List obtenerProductosConvocatoria(Convocatoria conv) throws DataAccessException;

    /**
     * Obtener productos formulario informe.
     *
     * @return the list
     */
    public List<ProductoTipo> obtenerProductosFormularioInforme();

    /**
     * Lista criterios.s
     *
     * @param m the m
     * @return the list
     * @throws DataAccessException the data access exception
     */
    public List listaCriterios(Modalidad m) throws DataAccessException;

    /**
     * Obtenr calificacion evaluacion X criterio Y proyecto Y evaluador.
     *
     * @param c the c
     * @param pr the pr
     * @param p the p
     * @return the calificacion evaluacion
     * @throws DataAccessException the data access exception
     */
    public CalificacionEvaluacion obtenrCalificacionEvaluacionXCriterioYProyectoYEvaluador(CriterioEvaluacion c,
            Proyecto pr, Persona p) throws DataAccessException;

    /**
     * Eliminar objeto.
     *
     * @param objeto the objeto
     */
    public void eliminarObjeto(Object objeto);
    
    public List<ConvocatoriaPadreParametrizacion> obtenerParametrosConvocatoriasPadre(ConvocatoriaPadre convPadre, Long tipoParam);
    
    public List<ConvocatoriaParametrizacion> obtenerParametrosConvocatorias(Convocatoria convocatoria, Long tipoParam);

    /**
     * Lista mod fuente fin X modalidad.
     *
     * @param idModalidad the id modalidad
     * @return the list
     */
    public List listaModFuenteFinXModalidad(Long idModalidad);

    /**
     * Obtener lista fuentes financiacion X convocatoria.
     *
     * @param id the id
     * @return the list
     */
    public List obtenerListaFuentesFinanciacionXConvocatoria(Long id);

    /**
     * Lista modalidad criterio tipo pregunta.
     *
     * @param m the m
     * @return the list
     */
    public List listaModalidadCriterioTipoPregunta(Modalidad m);

    /**
     * Modalidad contiene criterio.
     *
     * @param m the m
     * @param idCriterio the id criterio
     * @return true, if successful
     */
    public boolean modalidadContieneCriterio(Modalidad m, Long idCriterio);

    /**
     * Obtener convocatorias padre en estados.
     *
     * @param listaIdEstados the lista id estados
     * @return the list
     */
    public List<ConvocatoriaPadre> obtenerConvocatoriasPadreEnEstados(List listaIdEstados);

    /**
     * Obtener convocatorias X padre Y lista estado.
     *
     * @param idConvocatoriaPadre the id convocatoria padre
     * @param estados the estados
     * @return the list
     */
    public List obtenerConvocatoriasXPadreYListaEstado(Long idConvocatoriaPadre, List estados);

    /**
     * Validar modalidad fuente financiacion.
     *
     * @param idModalidad the id modalidad
     * @param idFuenteFinanciacion the id fuente financiacion
     * @return true, if successful
     */
    public boolean validarModalidadFuenteFinanciacion(Long idModalidad, String idFuenteFinanciacion);

    /**
     * Obtener rubros financiables modalidad.
     *
     * @param idModalidad the id modalidad
     * @return the list
     */
    public List<RubroFinanciable> obtenerRubrosFinanciablesModalidad(Long idModalidad);

    /**
     * Obtener modalidad criterio tipo pregunta X proyecto evaluador Y criterio.
     *
     * @param idModalidad the id modalidad
     * @param idCriterio the id criterio
     * @return the modalidad criterio tipo pregunta
     */
    public ModalidadCriterioTipoPregunta obtenerModalidadCriterioTipoPreguntaXProyectoEvaluadorYCriterio(
            Long idModalidad, Long idCriterio);

    /**
     * Lista criterios resumen.
     *
     * @param m the m
     * @param pryId the pry id
     * @return the list
     */
    public List listaCriteriosResumen(Modalidad m, String pryId);

    /**
     * Obtener convocatoria padre.
     *
     * @param id the id
     * @return the convocatoria padre
     * @throws DataAccessException the data access exception
     */
    public ConvocatoriaPadre obtenerConvocatoriaPadre(Long id) throws DataAccessException;

    /**
     * Obtener convocatoria externa.
     *
     * @param id the id
     * @return the convocatoria externa
     * @throws DataAccessException the data access exception
     */
    public ConvocatoriaExterna obtenerConvocatoriaExterna(Long id) throws DataAccessException;

    /**
     * Obtener tipo modalidad convocatoria.
     *
     * @param id the id
     * @return the tipo modalidad
     * @throws DataAccessException the data access exception
     */
    public TipoModalidad obtenerTipoModalidadConvocatoria(String id) throws DataAccessException;

    /**
     * Gets the rubro financiable arbol.
     *
     * @param idModalidadFuenteFinanciacion the id modalidad fuente financiacion
     * @return the rubro financiable arbol
     * @throws DataAccessException the data access exception
     */
    public List<RubroFinanciableArbol> getRubroFinanciableArbol(Long idModalidadFuenteFinanciacion)
            throws DataAccessException;

	public CorteConvocatoria obtenerCorteConvocatoriaPadre(Long idConvPadre, Long numeroCorte, Sede sede, String estadoCorte) throws DataAccessException;
	
	public ConvocatoriaTerminosReferencia obtenerConvocatoriaTerminosReferenciaPorId(Long id) throws DataAccessException ;

}
