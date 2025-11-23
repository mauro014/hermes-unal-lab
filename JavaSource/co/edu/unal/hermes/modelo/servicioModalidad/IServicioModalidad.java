/*
 * Created on 09-ago-2005
 */
package co.edu.unal.hermes.modelo.servicioModalidad;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

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
import co.edu.unal.hermes.modelo.ModalidadFuenteFinanciacion;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.ProductoTipo;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.RubroFinanciable;
import co.edu.unal.hermes.modelo.RubroFinanciableArbol;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.TipoModalidad;
import co.edu.unal.hermes.modelo.TipoRubro;

/**
 * The Interface IServicioModalidad.
 */
public interface IServicioModalidad {

    /**
     * Resumen estado proyectos.
     *
     * @param idConvocatoria
     *            the id convocatoria
     * @return the list
     * @throws SQLException
     *             the SQL exception
     */
    public List resumenEstadoProyectos(String idConvocatoria) throws SQLException;

    /**
     * Resumen estado proyectos movilidad.
     *
     * @param idConvocatoria
     *            the id convocatoria
     * @param tipoConvocatoria
     *            the tipo convocatoria
     * @return the list
     * @throws SQLException
     *             the SQL exception
     */
    public List resumenEstadoProyectosMovilidad(String idConvocatoria, String tipoConvocatoria) throws SQLException;

    /**
     * Obtener rubros.
     *
     * @param idFiltro
     *            the id filtro
     * @param opcion
     *            the opcion
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List<TipoRubro> obtenerRubros(Long idFiltro, int opcion) throws DataAccessException;

    /**
     * Obtener convocatorias.
     *
     * @param where
     *            the where
     * @param isParametros
     *            the is parametros
     * @param parametros
     *            the parametros
     * @return the list
     */
    public List obtenerConvocatorias(String where, boolean isParametros, Object[] parametros);

    /**
     * Guardar convocatoria.
     *
     * @param convocatoria
     *            the convocatoria
     */
    public void guardarConvocatoria(Convocatoria convocatoria);

    /**
     * Obtener convocatoria.
     *
     * @param id
     *            the id
     * @return the convocatoria
     */
    public Convocatoria obtenerConvocatoria(Long id);

    /**
     * Obtener modalidad.
     *
     * @param id
     *            the id
     * @return the modalidad
     */
    public Modalidad obtenerModalidad(Long id);

    /**
     * Obtener convocatorias.
     *
     * @param estado
     *            the estado
     * @return the list
     */
    public List obtenerConvocatorias(EstadoConvocatoria estado);

    /**
     * Obtener convocatoria edicion.
     *
     * @param id
     *            the id
     * @return the convocatoria
     */
    public Convocatoria obtenerConvocatoriaEdicion(Long id);

    /**
     * Obtener convocatoria requisitos.
     *
     * @param id
     *            the id
     * @return the convocatoria
     */
    public Convocatoria obtenerConvocatoriaRequisitos(Long id);

    /**
     * Obtener convocatoriasx padre.
     *
     * @param padre
     *            the padre
     * @return the list
     */
    public List<Convocatoria> obtenerConvocatoriasxPadre(ConvocatoriaPadre padre);
    
    public List<ConvocatoriaPadreParametrizacion> obtenerParametrosConvocatoriasPadre(ConvocatoriaPadre convPadre, Long tipoParam);
    
    public List<ConvocatoriaParametrizacion> obtenerParametrosConvocatorias(Convocatoria convocatoria, Long tipoParam);

    /**
     * Obtener convocatorias activas.
     *
     * @return the list
     */
    public List obtenerConvocatoriasActivas();

    /**
     * Obtener rubros financiables.
     *
     * @param conv
     *            the conv
     * @return the list
     */
    public List<RubroFinanciable> obtenerRubrosFinanciables(Modalidad conv);

    /**
     * Obtener productos convocatoria.
     *
     * @param conv
     *            the conv
     * @return the list
     */
    public List<ProductoTipo> obtenerProductosConvocatoria(Convocatoria conv);

    /**
     * Obtener productos formulario informe.
     *
     * @return the list
     */
    public List<ProductoTipo> obtenerProductosFormularioInforme();

    /**
     * Lista criterios.
     *
     * @param m
     *            the m
     * @return the list
     */
    public List listaCriterios(Modalidad m);

    /**
     * Obtenr calificacion evaluacion x criterio y proyecto y evaluador.
     *
     * @param c
     *            the c
     * @param pr
     *            the pr
     * @param p
     *            the p
     * @return the calificacion evaluacion
     */
    public CalificacionEvaluacion obtenrCalificacionEvaluacionXCriterioYProyectoYEvaluador(CriterioEvaluacion c,
            Proyecto pr, Persona p);

    /**
     * Actualizar porcentajes rubros financiables.
     *
     * @param c
     *            the c
     * @return the sets the
     */
    public Set actualizarPorcentajesRubrosFinanciables(Convocatoria c);

    /**
     * Lista mod fuente fin x modalidad.
     *
     * @param idModalidad
     *            the id modalidad
     * @return the list
     */
    public List<ModalidadFuenteFinanciacion> listaModFuenteFinXModalidad(Long idModalidad);

    /**
     * Obtener total de lista financiacion x fuente financiacion.
     *
     * @param listaFinanciaciones
     *            the lista financiaciones
     * @param idFuenteFinanciacion
     *            the id fuente financiacion
     * @return the double
     */
    public double obtenerTotalDeListaFinanciacionXFuenteFinanciacion(List listaFinanciaciones,
            String idFuenteFinanciacion);

    /**
     * Validar financiaciones.
     *
     * @param listaFinanciaciones
     *            the lista financiaciones
     * @param listaRubrosFinanciables
     *            the lista rubros financiables
     * @param listaFinanciacionesConvocatoria
     *            the lista financiaciones convocatoria
     * @param totalConvocatoria
     *            the total convocatoria
     * @return the string
     */
    public String validarFinanciaciones(List listaFinanciaciones, List listaRubrosFinanciables,
            List listaFinanciacionesConvocatoria, double totalConvocatoria);

    /**
     * Obtener lista fuentes financiacion x convocatoria.
     *
     * @param id
     *            the id
     * @return the list
     */
    public List obtenerListaFuentesFinanciacionXConvocatoria(Long id);

    /**
     * Lista modalidad criterio tipo pregunta.
     *
     * @param m
     *            the m
     * @return the list
     */
    public List listaModalidadCriterioTipoPregunta(Modalidad m);

    /**
     * Modalidad contiene criterio.
     *
     * @param m
     *            the m
     * @param idCriterio
     *            the id criterio
     * @return true, if successful
     */
    public boolean modalidadContieneCriterio(Modalidad m, Long idCriterio);

    /**
     * Obtener convocatorias padre en estados.
     *
     * @param listaIdEstados
     *            the lista id estados
     * @return the list
     */
    public List<ConvocatoriaPadre> obtenerConvocatoriasPadreEnEstados(List listaIdEstados);

    /**
     * Obtener convocatorias x padre y lista estado.
     *
     * @param idConvocatoriaPadre
     *            the id convocatoria padre
     * @param estados
     *            the estados
     * @return the list
     */
    public List<Convocatoria> obtenerConvocatoriasXPadreYListaEstado(Long idConvocatoriaPadre, List estados);

    /**
     * Validar modalidad fuente financiacion.
     *
     * @param idModalidad
     *            the id modalidad
     * @param idFuenteFinanciacion
     *            the id fuente financiacion
     * @return true, if successful
     */
    public boolean validarModalidadFuenteFinanciacion(Long idModalidad, String idFuenteFinanciacion);

    /**
     * Obtener rubros financiables modalidad.
     *
     * @param idModalidad
     *            the id modalidad
     * @return the list
     */
    public List<RubroFinanciable> obtenerRubrosFinanciablesModalidad(Long idModalidad);

    /**
     * Obtener modalidad criterio tipo pregunta x proyecto evaluador y criterio.
     *
     * @param idModalidad
     *            the id modalidad
     * @param idCriterio
     *            the id criterio
     * @return the modalidad criterio tipo pregunta
     */
    public ModalidadCriterioTipoPregunta obtenerModalidadCriterioTipoPreguntaXProyectoEvaluadorYCriterio(
            Long idModalidad, Long idCriterio);

    /**
     * Lista criterios resumen.
     *
     * @param m
     *            the m
     * @param pryId
     *            the pry id
     * @return the list
     */
    public List listaCriteriosResumen(Modalidad m, String pryId);

    /**
     * Obtener convocatorias x padre y coordinador.
     *
     * @param idPadre
     *            the id padre
     * @param idCoordinador
     *            the id coordinador
     * @return the list
     */
    public List obtenerConvocatoriasXPadreYCoordinador(Long idPadre, IdPersona idCoordinador);

    /**
     * Obtener convocatoria padre.
     *
     * @param id
     *            the id
     * @return the convocatoria padre
     * @throws DataAccessException
     *             the data access exception
     */
    public ConvocatoriaPadre obtenerConvocatoriaPadre(Long id) throws DataAccessException;
    
    public CorteConvocatoria obtenerCorteConvocatoriaPadre(Long idConvPadre, Long numeroCorte, Sede sede, String estadoCorte) throws DataAccessException;

    /**
     * Obtener convocatoria externa.
     *
     * @param id
     *            the id
     * @return the convocatoria externa
     * @throws DataAccessException
     *             the data access exception
     */
    public ConvocatoriaExterna obtenerConvocatoriaExterna(Long id) throws DataAccessException;

    /**
     * Obtener tipo modalidad convocatoria.
     *
     * @param id
     *            the id
     * @return the tipo modalidad
     * @throws DataAccessException
     *             the data access exception
     */
    public TipoModalidad obtenerTipoModalidadConvocatoria(String id) throws DataAccessException;

    /**
     * Gets the rubro financiable arbol.
     *
     * @param idModalidadFuenteFinanciacion
     *            the id modalidad fuente financiacion
     * @param padre
     *            the padre
     * @return the rubro financiable arbol
     */
    public List<RubroFinanciableArbol> getRubroFinanciableArbol(Long idModalidadFuenteFinanciacion);
    
    public ConvocatoriaTerminosReferencia obtenerConvocatoriaTerminosReferenciaPorId(Long id);

}
