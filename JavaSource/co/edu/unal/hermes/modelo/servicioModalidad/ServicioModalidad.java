/*
 * Created on 09-ago-2005
 */
package co.edu.unal.hermes.modelo.servicioModalidad;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.springframework.dao.DataAccessException;

import co.edu.unal.hermes.bd.IModalidadDAO;
import co.edu.unal.hermes.bd.IProyectoDAO;
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
import co.edu.unal.hermes.modelo.Financiacion;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.Gasto;
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
 * The Class ServicioModalidad.
 */
public class ServicioModalidad implements IServicioModalidad {

    /** The modalidad dao. */
    private IModalidadDAO modalidadDAO;

    /** The proyecto dao. */
    private IProyectoDAO proyectoDAO;

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#
     * resumenEstadoProyectos(java.lang.String)
     */
    public List resumenEstadoProyectos(String idConvocatoria) throws SQLException {
        return modalidadDAO.resumenEstadoProyectos(idConvocatoria);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#
     * resumenEstadoProyectosMovilidad(java.lang.String, java.lang.String)
     */
    public List resumenEstadoProyectosMovilidad(String idConvocatoria, String tipoConvocatoria) throws SQLException {
        return modalidadDAO.resumenEstadoProyectosMovilidad(idConvocatoria, tipoConvocatoria);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#
     * obtenerRubros(java.lang.Long, int)
     */
    public List<TipoRubro> obtenerRubros(Long idFiltro, int opcion) throws DataAccessException {
        return modalidadDAO.obtenerRubros(idFiltro, opcion);
    }

    /**
     * Sets the modalidad dao.
     *
     * @param dao
     *            the new modalidad dao
     */
    public void setModalidadDAO(IModalidadDAO dao) {
        modalidadDAO = dao;
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#
     * guardarConvocatoria(co.edu.unal.hermes.modelo.Convocatoria)
     */
    public void guardarConvocatoria(Convocatoria convocatoria) {
        modalidadDAO.guardarConvocatoria(convocatoria);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#
     * obtenerConvocatoria(java.lang.Long)
     */
    public Convocatoria obtenerConvocatoria(Long id) {
        return modalidadDAO.obtenerConvocatoria(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#
     * obtenerConvocatorias(co.edu.unal.hermes.modelo.EstadoConvocatoria)
     */
    public List obtenerConvocatorias(EstadoConvocatoria estado) {
        return modalidadDAO.obtenerConvocatorias(estado);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#
     * obtenerModalidad(java.lang.Long)
     */
    public Modalidad obtenerModalidad(Long id) {
        return modalidadDAO.obtenerModalidad(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#
     * obtenerConvocatoriaEdicion(java.lang.Long)
     */
    public Convocatoria obtenerConvocatoriaEdicion(Long id) {
        return modalidadDAO.obtenerConvocatoriaEdicion(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#
     * obtenerConvocatoriaRequisitos(java.lang.Long)
     */
    public Convocatoria obtenerConvocatoriaRequisitos(Long id) {
        return modalidadDAO.obtenerConvocatoriaRequisitos(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#
     * obtenerConvocatoriasxPadre(co.edu.unal.hermes.modelo.ConvocatoriaPadre)
     */
    public List<Convocatoria> obtenerConvocatoriasxPadre(ConvocatoriaPadre padre) {
        return modalidadDAO.obtenerConvocatoriasxPadre(padre);
    }
    
    public List<ConvocatoriaPadreParametrizacion> obtenerParametrosConvocatoriasPadre(ConvocatoriaPadre convPadre, Long tipoParam) {
        return modalidadDAO.obtenerParametrosConvocatoriasPadre(convPadre, tipoParam);
    }
    
    public List<ConvocatoriaParametrizacion> obtenerParametrosConvocatorias(Convocatoria convocatoria, Long tipoParam) {
        return modalidadDAO.obtenerParametrosConvocatorias(convocatoria, tipoParam);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#
     * obtenerConvocatoriasXPadreYCoordinador(java.lang.Long,
     * co.edu.unal.hermes.modelo.IdPersona)
     */
    public List obtenerConvocatoriasXPadreYCoordinador(Long idPadre, IdPersona idCoordinador) {
        return modalidadDAO.obtenerConvocatoriasXPadreYCoordinador(idPadre, idCoordinador);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#
     * obtenerConvocatoriasActivas()
     */
    public List obtenerConvocatoriasActivas() {
        EstadoConvocatoria estado = new EstadoConvocatoria();
        estado.setId(EstadoConvocatoria.ACTIVA);
        return obtenerConvocatorias(estado);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#
     * obtenerRubrosFinanciables(co.edu.unal.hermes.modelo.Modalidad)
     */
    public List<RubroFinanciable> obtenerRubrosFinanciables(Modalidad conv) {
        return modalidadDAO.obtenerRubrosFinanciables(conv);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#
     * obtenerProductosConvocatoria(co.edu.unal.hermes.modelo.Convocatoria)
     */
    public List obtenerProductosConvocatoria(Convocatoria conv) {
        return modalidadDAO.obtenerProductosConvocatoria(conv);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#
     * obtenerProductosFormularioInforme()
     */
    public List<ProductoTipo> obtenerProductosFormularioInforme() {
        return modalidadDAO.obtenerProductosFormularioInforme();
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#
     * listaCriterios(co.edu.unal.hermes.modelo.Modalidad)
     */
    public List listaCriterios(Modalidad conv) {
        return modalidadDAO.listaCriterios(conv);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#
     * obtenrCalificacionEvaluacionXCriterioYProyectoYEvaluador(co.edu.unal.
     * hermes.modelo.CriterioEvaluacion, co.edu.unal.hermes.modelo.Proyecto,
     * co.edu.unal.hermes.modelo.Persona)
     */
    public CalificacionEvaluacion obtenrCalificacionEvaluacionXCriterioYProyectoYEvaluador(CriterioEvaluacion c,
            Proyecto pr, Persona p) {
        return modalidadDAO.obtenrCalificacionEvaluacionXCriterioYProyectoYEvaluador(c, pr, p);
    }

    /**
     * Sets the proyecto dao.
     *
     * @param proyectoDAO
     *            the new proyecto dao
     */
    public void setProyectoDAO(IProyectoDAO proyectoDAO) {
        this.proyectoDAO = proyectoDAO;
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#
     * actualizarPorcentajesRubrosFinanciables(co.edu.unal.hermes.modelo.
     * Convocatoria)
     */
    public Set actualizarPorcentajesRubrosFinanciables(Convocatoria c) {
        List listaRF = c.getListaRubrosFinanciables();
        Double max = new Double(c.getMontoApoyoGanadores());
        List lista = new Vector();
        for (Iterator it = listaRF.iterator(); it.hasNext();) {
            RubroFinanciable rf = (RubroFinanciable) it.next();
            if (rf.getParametro() != null && rf.getParametro().getValor() != null
                    && rf.getCantidadParametro() != null) {

                double cantidadParametro = rf.getCantidadParametro().doubleValue();
                double valorParametro = new Double(rf.getParametro().getValor()).doubleValue();

                double porcentaje = (new Double(((cantidadParametro * valorParametro) / max.doubleValue()) * 100)
                        .doubleValue());
                rf.setPorcentajeMaximo(porcentaje);
                System.out.println("modifica" + cantidadParametro + "*" + valorParametro + "/" + max.doubleValue() + "="
                        + rf.getPorcentajeMaximo());
            }
            if (rf.getPorcentajeMaximo() > 100) {
                return null;
            }
            lista.add(rf);

        }
        return new HashSet(lista);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#
     * listaModFuenteFinXModalidad(java.lang.Long)
     */
    public List<ModalidadFuenteFinanciacion> listaModFuenteFinXModalidad(Long idModalidad) {
        return modalidadDAO.listaModFuenteFinXModalidad(idModalidad);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#
     * obtenerTotalDeListaFinanciacionXFuenteFinanciacion(java.util.List,
     * java.lang.String)
     */
    public double obtenerTotalDeListaFinanciacionXFuenteFinanciacion(List listaFinanciaciones,
            String idFuenteFinanciacion) {
        double total = 0;
        for (Iterator it = listaFinanciaciones.iterator(); it.hasNext();) {
            Financiacion f = (Financiacion) it.next();
            if (f.getFuente().getId().equals(idFuenteFinanciacion)) {
                total += f.getValor().doubleValue();

            }
        }
        return total;
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#
     * validarFinanciaciones(java.util.List, java.util.List, java.util.List,
     * double)
     */
    public String validarFinanciaciones(List listaFinanciaciones, List listaRubrosFinanciables,
            List listaFinanciacionesConvocatoria, double totalConvocatoria) {
        for (Iterator itFinanciaciones = listaFinanciaciones.iterator(); itFinanciaciones.hasNext();) {
            Financiacion f = (Financiacion) itFinanciaciones.next();
            if (!gastosPasanFinanciacion(f)) {
                return "los gastos de " + f.getFuente().getDescripcion() + " pasan el valor de ella "
                        + f.getValor().longValue();
            }
        }
        List listaGastos = new Vector();
        List listaFuentes = new Vector();
        List listaFinanciacionesConvocatoriaAux = new Vector();

        for (Iterator itFinanciaciones = listaFinanciacionesConvocatoria.iterator(); itFinanciaciones.hasNext();) {
            ModalidadFuenteFinanciacion mff = (ModalidadFuenteFinanciacion) itFinanciaciones.next();
            listaFuentes.add(mff.getFuenteFinanciacion());
            // Financiacion f=mff.get(Financiacion) itFinanciaciones.next();
            // listaGastos.addAll(f.getGastos());
        }

        for (Iterator i = listaFinanciaciones.iterator(); i.hasNext();) {
            Financiacion f = (Financiacion) i.next();
            FuenteFinanciacion ff = f.getFuente();
            if (listaFuentes.contains(ff)) {
                System.out.println("fuente" + ff.getDescripcion() + "esta");
                listaFinanciacionesConvocatoriaAux.add(f);
            }
        }

        for (Iterator itFinanciaciones = listaFinanciacionesConvocatoriaAux.iterator(); itFinanciaciones.hasNext();) {
            Financiacion f = (Financiacion) itFinanciaciones.next();
            listaGastos.addAll(f.getGastos());
        }
        for (Iterator itRubrosFianciables = listaRubrosFinanciables.iterator(); itRubrosFianciables.hasNext();) {
            RubroFinanciable rf = (RubroFinanciable) itRubrosFianciables.next();
            if (!validaGastosXRubroFinanciable(listaGastos, rf, totalConvocatoria)) {
                return "el porcentaje para el rubro " + rf.getTipoRubro().getDescripcion() + " pasa el valor maximo "
                        + (rf.getPorcentajeMaximo() * totalConvocatoria);
            }
        }
        return null;
    }

    /**
     * Gastos pasan financiacion.
     *
     * @param f
     *            the f
     * @return true, if successful
     */
    public boolean gastosPasanFinanciacion(Financiacion f) {
        double totalF = 0;
        for (Iterator i = f.getGastos().iterator(); i.hasNext();) {
            Gasto g = (Gasto) i.next();
            totalF += g.getValor().doubleValue();
        }
        if (totalF > f.getValor().doubleValue()) {
            return false;
        }
        return true;
    }

    /**
     * Valida gastos x rubro financiable.
     *
     * @param listaGastos
     *            the lista gastos
     * @param rf
     *            the rf
     * @param totalConvocatoria
     *            the total convocatoria
     * @return true, if successful
     */
    public boolean validaGastosXRubroFinanciable(List listaGastos, RubroFinanciable rf, double totalConvocatoria) {
        double totalMaxiomRubro = rf.getPorcentajeMaximo() * totalConvocatoria;
        double totalGastos = 0;
        for (Iterator i = listaGastos.iterator(); i.hasNext();) {
            Gasto g = (Gasto) i.next();
            if (g.getTipoRubro().getId().equals(rf.getTipoRubro().getId())) {
                totalGastos += g.getValor().doubleValue();
            }
        }
        if (totalMaxiomRubro < totalGastos) {
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#
     * obtenerListaFuentesFinanciacionXConvocatoria(java.lang.Long)
     */
    public List obtenerListaFuentesFinanciacionXConvocatoria(Long id) {
        return modalidadDAO.obtenerListaFuentesFinanciacionXConvocatoria(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#
     * listaModalidadCriterioTipoPregunta(co.edu.unal.hermes.modelo.Modalidad)
     */
    public List listaModalidadCriterioTipoPregunta(Modalidad m) {
        return modalidadDAO.listaModalidadCriterioTipoPregunta(m);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#
     * modalidadContieneCriterio(co.edu.unal.hermes.modelo.Modalidad,
     * java.lang.Long)
     */
    public boolean modalidadContieneCriterio(Modalidad m, Long idCriterio) {
        return modalidadDAO.modalidadContieneCriterio(m, idCriterio);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#
     * obtenerConvocatoriasPadreEnEstados(java.util.List)
     */
    public List<ConvocatoriaPadre> obtenerConvocatoriasPadreEnEstados(List listaIdEstados) {
        return modalidadDAO.obtenerConvocatoriasPadreEnEstados(listaIdEstados);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#
     * obtenerConvocatoriasXPadreYListaEstado(java.lang.Long, java.util.List)
     */
    public List<Convocatoria> obtenerConvocatoriasXPadreYListaEstado(Long idConvocatoriaPadre, List estados) {
        return modalidadDAO.obtenerConvocatoriasXPadreYListaEstado(idConvocatoriaPadre, estados);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#
     * validarModalidadFuenteFinanciacion(java.lang.Long, java.lang.String)
     */
    public boolean validarModalidadFuenteFinanciacion(Long idModalidad, String idFuenteFinanciacion) {
        return this.modalidadDAO.validarModalidadFuenteFinanciacion(idModalidad, idFuenteFinanciacion);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#
     * obtenerRubrosFinanciablesModalidad(java.lang.Long)
     */
    public List<RubroFinanciable> obtenerRubrosFinanciablesModalidad(Long idModalidad) {
        return this.modalidadDAO.obtenerRubrosFinanciablesModalidad(idModalidad);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#
     * obtenerModalidadCriterioTipoPreguntaXProyectoEvaluadorYCriterio(java.lang
     * .Long, java.lang.Long)
     */
    public ModalidadCriterioTipoPregunta obtenerModalidadCriterioTipoPreguntaXProyectoEvaluadorYCriterio(
            Long idModalidad, Long idCriterio) {
        return modalidadDAO.obtenerModalidadCriterioTipoPreguntaXProyectoEvaluadorYCriterio(idModalidad, idCriterio);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#
     * listaCriteriosResumen(co.edu.unal.hermes.modelo.Modalidad,
     * java.lang.String)
     */
    public List listaCriteriosResumen(Modalidad m, String pryId) {
        return modalidadDAO.listaCriteriosResumen(m, pryId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#
     * obtenerConvocatorias(java.lang.String, boolean, java.lang.Object[])
     */
    public List obtenerConvocatorias(String where, boolean isParametros, Object[] parametros) {
        return modalidadDAO.obtenerConvocatorias(where, isParametros, parametros);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#
     * obtenerConvocatoriaPadre(java.lang.Long)
     */
    public ConvocatoriaPadre obtenerConvocatoriaPadre(Long id) throws DataAccessException {
        return modalidadDAO.obtenerConvocatoriaPadre(id);
    }
    
    public CorteConvocatoria obtenerCorteConvocatoriaPadre(Long idConvPadre, Long numeroCorte, Sede sede, String estadoCorte) throws DataAccessException {
        return modalidadDAO.obtenerCorteConvocatoriaPadre(idConvPadre, numeroCorte, sede, estadoCorte);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#
     * obtenerConvocatoriaExterna(java.lang.Long)
     */
    public ConvocatoriaExterna obtenerConvocatoriaExterna(Long id) throws DataAccessException {
        return modalidadDAO.obtenerConvocatoriaExterna(id);
    }
    
    /* (non-Javadoc)
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#obtenerTipoModalidadConvocatoria(java.lang.String)
     */
    public TipoModalidad obtenerTipoModalidadConvocatoria(String id){
    	return modalidadDAO.obtenerTipoModalidadConvocatoria(id);
    }
    
    /* (non-Javadoc)
     * @see co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad#getRubroFinanciableArbol(java.lang.Long, java.lang.Long)
     */
    public List<RubroFinanciableArbol> getRubroFinanciableArbol(Long idModalidadFuenteFinanciacion){
        try{
            return modalidadDAO.getRubroFinanciableArbol(idModalidadFuenteFinanciacion);
        }
        catch (DataAccessException e) {
            return new ArrayList<RubroFinanciableArbol>();
        }
    }
    
    public ConvocatoriaTerminosReferencia obtenerConvocatoriaTerminosReferenciaPorId(Long id) throws DataAccessException {
    	 return modalidadDAO.obtenerConvocatoriaTerminosReferenciaPorId(id);
    }
    
}
