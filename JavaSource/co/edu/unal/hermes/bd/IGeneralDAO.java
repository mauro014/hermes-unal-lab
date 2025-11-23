package co.edu.unal.hermes.bd;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;

import co.edu.unal.hermes.modelo.ArchivoInforme;
import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.modelo.Bien;
import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.Coleccion;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.Departamento;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DocumentoVice;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.Empresa;
import co.edu.unal.hermes.modelo.EstadoCivil;
import co.edu.unal.hermes.modelo.GrupoLaboratorioVista;
import co.edu.unal.hermes.modelo.HistoricoEstadoSolicitud;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Institucion;
import co.edu.unal.hermes.modelo.Instructivo;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorAreaInteres;
import co.edu.unal.hermes.modelo.InvestigadorAreaInvestigacion;
import co.edu.unal.hermes.modelo.InvestigadorAsignatura;
import co.edu.unal.hermes.modelo.InvestigadorEnlace;
import co.edu.unal.hermes.modelo.InvestigadorEvento;
import co.edu.unal.hermes.modelo.InvestigadorLineaInvestigacion;
import co.edu.unal.hermes.modelo.InvestigadorObraExposicion;
import co.edu.unal.hermes.modelo.InvestigadorPublicacion;
import co.edu.unal.hermes.modelo.LineaInvestigacion;
import co.edu.unal.hermes.modelo.LogAlertasAutomaticas;
import co.edu.unal.hermes.modelo.MovilidadAlertaAutomatica;
import co.edu.unal.hermes.modelo.Pais;
import co.edu.unal.hermes.modelo.PalabraClave;
import co.edu.unal.hermes.modelo.ParametroMaestro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PersonaRol;
import co.edu.unal.hermes.modelo.Pregunta;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.ProyectoInforme;
import co.edu.unal.hermes.modelo.ProyectoLaboratorioVista;
import co.edu.unal.hermes.modelo.Reporte;
import co.edu.unal.hermes.modelo.Requerimiento;
import co.edu.unal.hermes.modelo.Rol;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.SemilleroActividad;
import co.edu.unal.hermes.modelo.SemilleroInforme;
import co.edu.unal.hermes.modelo.SemilleroLaboratorioVista;
import co.edu.unal.hermes.modelo.Servicio;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoFormacion;
import co.edu.unal.hermes.modelo.laboratorios.ArchivoLaboratorio;
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioActividadEquipoInterfazAlerta;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioAreasSecundariasOCDE;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioCostosServicio;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleEnsayosServicios;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioLogActividades;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioLogEquipos;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioLogLaboratorios;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioSolicitud;
import co.edu.unal.hermes.modelo.seguimiento.Solicitud;

/**
 * Interface para obtener atributos generales de la aplicacion.
 *
 * @trows lanza la excepcion cuando no se puede acceder a los datos
 */

public interface IGeneralDAO {

    /**
     * Obtener avales vice.
     *
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List<Aval> obtenerAvalesVice() throws DataAccessException;
    
    public List<Aval> obtenerAvalesDRE() throws DataAccessException;
    
    public List<Aval> obtenerAvalesCEPI(String dependenciaRev) throws DataAccessException;
    
    public List<Aval> obtenerAvalesCESI(String dependenciaRev) throws DataAccessException;
    
    public List<Aval> obtenerAvalesCESIQueja(String dependenciaRev) throws DataAccessException;

    public List<Aval> obtenerAvalesRectoria() throws DataAccessException;

    /**
     * Consulta equipos.
     *
     * @param placa
     *            the placa
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List<Bien> consultaEquipos(String placa) throws DataAccessException;
    
    public List<ProyectoLaboratorioVista> consultaProyectosAsociadosLaboratorio(Long idLab) throws DataAccessException;
    
    public List<GrupoLaboratorioVista> consultaGruposAsociadosLaboratorio(Long idLab) throws DataAccessException;
    
    public List<SemilleroLaboratorioVista> consultaSemillerosAsociadosLaboratorio(Long idLab) throws DataAccessException;
    
    public List<String[]> consultaValorEjecutado(String idProyecto, String director) throws DataAccessException;
    
    public List<Bien> consultaEquiposPorPlacaYNombre(String placa) throws DataAccessException;

    /**
     * Consulta recursos convocatoria.
     *
     * @param padre
     *            the padre
     * @param modalidad
     *            the modalidad
     * @return the long
     * @throws SQLException
     *             the SQL exception
     */
    public long consultaRecursosConvocatoria(Long padre, Long modalidad) throws SQLException;

    /**
     * Obtener lista archivos movilidad.
     *
     * @param idMovilidad
     *            the id movilidad
     * @param idArchivo
     *            the id archivo
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List obtenerListaArchivosMovilidad(String idMovilidad, Long idArchivo) throws DataAccessException;

    /**
     * Obtener lista archivos movilidad.
     *
     * @param idMovilidad
     *            the id movilidad
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List obtenerListaArchivosMovilidad(String idMovilidad) throws DataAccessException;

    /**
     * Existe movilidad.
     *
     * @param sSql
     *            the s sql
     * @return the int
     * @throws SQLException
     *             the SQL exception
     */
    public int existeMovilidad(String sSql) throws SQLException;

    /**
     * Consulta avales facultad sede.
     *
     * @param sede
     *            the sede
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List<Aval> consultaAvalesFacultadSede(Long sede) throws DataAccessException;

    /**
     * Consulta avales vice.
     *
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List<Aval> consultaAvalesVice() throws DataAccessException;
    
    public List<Aval> consultaAvalesDRE() throws DataAccessException;

    /**
     * Obtener aval informe facultad.
     *
     * @param dependencia
     *            the dependencia
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List<ProyectoInforme> obtenerAvalInformeFacultad(String dependencia) throws DataAccessException;

    /**
     * Obtener aval informe uab.
     *
     * @param dependencia
     *            the dependencia
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List<ProyectoInforme> obtenerAvalInformeUab(String dependencia) throws DataAccessException;

    /**
     * Consulta total laboratorios activos.
     *
     * @return the long
     * @throws SQLException
     *             the SQL exception
     */
    public long consultaTotalLaboratoriosActivos() throws SQLException;

    /**
     * Consulta ultimo sequencia solicitud.
     *
     * @param idTipoSolicitud
     *            the id tipo solicitud
     * @param idProyecto
     *            the id proyecto
     * @param ano
     *            the ano
     * @param mes
     *            the mes
     * @param dia
     *            the dia
     * @return the long
     * @throws SQLException
     *             the SQL exception
     */
    public long consultaUltimoSequenciaSolicitud(Long idTipoSolicitud, Long idProyecto, Long ano, Long mes, Long dia)
            throws SQLException;

    /**
     * Obtener lista archivos informes.
     *
     * @param idInforme
     *            the id informe
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List<ArchivoInforme> obtenerListaArchivosInformes(Long idInforme) throws DataAccessException;

    /**
     * Obtener lista solicitudes renovacion.
     *
     * @param idProyecto
     *            the id proyecto
     * @param idPersona
     *            the id persona
     * @param tipoDocumentoPersona
     *            the tipo documento persona
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List<ProyectoInforme> obtenerListaSolicitudesRenovacion(Long idProyecto, String idPersona,
            String tipoDocumentoPersona) throws DataAccessException;

    /**
     * Obtener eventos investigador.
     *
     * @param tipo
     *            the tipo
     * @param doc
     *            the doc
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List<InvestigadorEvento> obtenerEventosInvestigador(String tipo, String doc) throws DataAccessException;

    /**
     * Obtener enlaces investigador.
     *
     * @param tipo
     *            the tipo
     * @param doc
     *            the doc
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List<InvestigadorEnlace> obtenerEnlacesInvestigador(String tipo, String doc) throws DataAccessException;

    /**
     * Obtener publicaciones investigador.
     *
     * @param tipo
     *            the tipo
     * @param doc
     *            the doc
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List<InvestigadorPublicacion> obtenerPublicacionesInvestigador(String tipo, String doc)
            throws DataAccessException;

    /**
     * Obtener areas investigacion investigador.
     *
     * @param tipo
     *            the tipo
     * @param doc
     *            the doc
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List<InvestigadorAreaInvestigacion> obtenerAreasInvestigacionInvestigador(String tipo, String doc)
            throws DataAccessException;

    /**
     * Obtener areas interes investigador.
     *
     * @param tipo
     *            the tipo
     * @param doc
     *            the doc
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List<InvestigadorAreaInteres> obtenerAreasInteresInvestigador(String tipo, String doc)
            throws DataAccessException;

    /**
     * Obtener obra exposicion investigador.
     *
     * @param tipo
     *            the tipo
     * @param doc
     *            the doc
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List<InvestigadorObraExposicion> obtenerObraExposicionInvestigador(String tipo, String doc)
            throws DataAccessException;

    /**
     * Obtener nombre laboratorios investigador.
     *
     * @param tipo
     *            the tipo
     * @param doc
     *            the doc
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List<Laboratorio> obtenerNombreLaboratoriosInvestigador(String tipo, String doc) throws DataAccessException;

    /**
     * Obtener asignaturas investigador.
     *
     * @param tipo
     *            the tipo
     * @param doc
     *            the doc
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List<InvestigadorAsignatura> obtenerAsignaturasInvestigador(String tipo, String doc)
            throws DataAccessException;

    /**
     * Obtener lista informes.
     *
     * @param idProyecto
     *            the id proyecto
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List<ProyectoInforme> obtenerListaInformes(Long idProyecto) throws DataAccessException;

    /**
     * Obtener correo boletin.
     *
     * @param tipo
     *            the tipo
     * @param sede
     *            the sede
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List obtenerCorreoBoletin(int tipo, String sede) throws DataAccessException;

    /**
     * Envio correo boletin.
     *
     * @return the string
     * @throws DataAccessException
     *             the data access exception
     * @throws SQLException
     *             the SQL exception
     */
    public String envioCorreoBoletin() throws DataAccessException, SQLException;

    /**
     * Obtener codigo solicitud.
     *
     * @param idProyecto
     *            the id proyecto
     * @param idTipoSol
     *            the id tipo sol
     * @param solicitudesExistentes
     *            the solicitudes existentes
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List obtenerCodigoSolicitud(Long idProyecto, String idTipoSol, String solicitudesExistentes)
            throws DataAccessException;

    /**
     * Eliminar.
     *
     * @param sSql
     *            the s sql
     * @throws SQLException
     *             the SQL exception
     */
    public void eliminar(String sSql) throws SQLException;

    /**
     * Consulta id carta.
     *
     * @param idProyecto
     *            the id proyecto
     * @param idTipoCarta
     *            the id tipo carta
     * @param sede
     *            the sede
     * @param año
     *            the año
     * @return the long
     * @throws SQLException
     *             the SQL exception
     */
    public Long consultaIdCarta(Long idProyecto, Long idTipoCarta, String sede, String año) throws SQLException;

    /**
     * Consulta ultimo id carta.
     *
     * @param sede
     *            the sede
     * @param fecha
     *            the fecha
     * @return the long
     * @throws SQLException
     *             the SQL exception
     */
    public Long consultaUltimoIdCarta(String sede, Date fecha) throws SQLException;

    /**
     * Consulta descripcion solicitud.
     *
     * @param idProyecto
     *            the id proyecto
     * @return the string
     * @throws SQLException
     *             the SQL exception
     */
    public String consultaDescripcionSolicitud(Long idProyecto) throws SQLException;

    /**
     * Consulta id solicitud.
     *
     * @param idProyecto
     *            the id proyecto
     * @return the long
     * @throws SQLException
     *             the SQL exception
     */
    public Long consultaIdSolicitud(Long idProyecto) throws SQLException;

    /**
     * Obtener objetos.
     *
     * @param hql
     *            the hql
     * @return the list
     */
    public List obtenerObjetos(String hql);

    /**
     * Dependencia padre.
     *
     * @param idDependencia
     *            the id dependencia
     * @return the string
     * @throws SQLException
     *             the SQL exception
     */
    public String dependenciaPadre(String idDependencia) throws SQLException;

    /**
     * Obtener avales direccion.
     *
     * @param dependencia
     *            the dependencia
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List<Aval> obtenerAvalesDireccion(String dependencia) throws DataAccessException;

    /**
     * Obtener avales investigador.
     *
     * @param doc
     *            the doc
     * @param tipodoc
     *            the tipodoc
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List<Aval> obtenerAvalesInvestigador(String doc, String tipodoc) throws DataAccessException;

    /**
     * Obtener avales proyecto.
     *
     * @param idProyecto
     *            the id proyecto
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List<Aval> obtenerAvalesProyecto(String idProyecto) throws DataAccessException;
    
    public List<Aval> obtenerAvalesProyectoXEstatoAvalXTipoAval(String idProyecto, String tiposAval, String estadosAval, Boolean incluirnNoAprobados) throws DataAccessException;

    /**
     * Obtener avales.
     *
     * @param dependencia
     *            the dependencia
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List<Aval> obtenerAvales(String dependencia) throws DataAccessException;

    /**
     * Obtener aval.
     *
     * @param id
     *            the id
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List<Aval> obtenerAval(String id) throws DataAccessException;

    /**
     * Actualizar evaluacion.
     *
     * @param id
     *            the id
     * @param valor
     *            the valor
     * @throws DataAccessException
     *             the data access exception
     */
    public void actualizarEvaluacion(int id, String valor) throws DataAccessException;

    /**
     * Obtener lineas empezando con.
     *
     * @param nombre
     *            the nombre
     * @return the list
     */
    public List obtenerLineasEmpezandoCon(String nombre);
    
    /**
     * Obtener Instituciones que contengan una cadena.
     *
     * @param nombre
     *            the nombre
     * @return the list
     */
    public List obtenerInstitucionesQueContienen(String nombreBusqueda) throws DataAccessException;

    /**
     * Obtener areas conocimiento empezando con.
     *
     * @param nombre
     *            the nombre
     * @return the list
     */
    public List obtenerAreasConocimientoEmpezandoCon(String nombre);

    /**
     * Obtener lista objetos where.
     *
     * @param clase
     *            the clase
     * @param where
     *            the where
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List obtenerListaObjetosWhere(String clase, String where) throws DataAccessException;

    /**
     * Obtener lista objetos where.
     *
     * @param <T>
     *            the generic type
     * @param clazz
     *            the clazz
     * @param where
     *            the where
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    <T> List<T> obtenerListaObjetosWhere(Class<T> clazz, String where) throws DataAccessException;

    /**
     * Obtener palabra clave ingles.
     *
     * @param nombre
     *            the nombre
     * @return the palabra clave
     */
    public PalabraClave obtenerPalabraClaveIngles(String nombre);

    /**
     * Lista de objetos Y diferente el id string A mod.
     *
     * @param clase
     *            the clase
     * @param id
     *            the id
     * @param modalidad
     *            the modalidad
     * @return the list
     */
    public List listaDeObjetosYDiferenteElIdStringAMod(String clase, String id, String modalidad);

    /**
     * Obtener key word empezando con.
     *
     * @param nombre
     *            the nombre
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List obtenerKeyWordEmpezandoCon(String nombre) throws DataAccessException;

    /**
     * Obtener dependencias.
     *
     * @param sede
     *            the sede
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List obtenerDependencias(Sede sede) throws DataAccessException;

    /**
     * Obtener facultades.
     *
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List obtenerFacultades() throws DataAccessException;

    /**
     * Obtener facultades.
     *
     * @param pSede
     *            the sede
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List obtenerFacultades(Dependencia pSede) throws DataAccessException;

    /**
     * Obtener lista objetos.
     *
     * @param clase
     *            the clase
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List obtenerListaObjetos(String clase) throws DataAccessException;

    /**
     * Obtener ubicacion.
     *
     * @param ubicacion
     *            the ubicacion
     * @param valorUbicacion
     *            the valor ubicacion
     * @param s_padre
     *            the s padre
     * @param o_padre
     *            the o padre
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List obtenerUbicacion(String ubicacion, String valorUbicacion, String s_padre, Object o_padre, boolean incluirTodos)
            throws DataAccessException;

    /**
     * Guardar objeto.
     *
     * @param objeto
     *            the objeto
     * @throws DataAccessException
     *             the data access exception
     */
    public void guardarObjeto(Object objeto) throws DataAccessException;

    /**
     * Guardar objeto laboratorio.
     *
     * @param objeto
     *            the objeto
     * @throws DataAccessException
     *             the data access exception
     */
    public void guardarObjetoLaboratorio(Object objeto) throws DataAccessException;

    /**
     * Insertar objeto.
     *
     * @param objeto
     *            the objeto
     * @throws DataAccessException
     *             the data access exception
     */
    public void insertarObjeto(Object objeto) throws DataAccessException;

    /**
     * Obtener objeto.
     *
     * @param clase
     *            the clase
     * @param id
     *            the id
     * @return the object
     * @throws DataAccessException
     *             the data access exception
     */
    public Object obtenerObjeto(Object clase, Object id) throws DataAccessException;

    /**
     * Obtener palabra clave.
     *
     * @param nombre
     *            the nombre
     * @return the palabra clave
     * @throws DataAccessException
     *             the data access exception
     */
    public PalabraClave obtenerPalabraClave(String nombre) throws DataAccessException;

    /**
     * Obtener departamento.
     *
     * @param ciudad
     *            the ciudad
     * @return the departamento
     * @throws DataAccessException
     *             the data access exception
     */
    public Departamento obtenerDepartamento(Ciudad ciudad) throws DataAccessException;

    /**
     * Eliminar objeto.
     *
     * @param objeto
     *            the objeto
     * @throws DataAccessException
     *             the data access exception
     */
    public void eliminarObjeto(Object objeto) throws DataAccessException;

    /**
     * Obtener lista instituciones.
     *
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List<Institucion> obtenerListaInstituciones() throws DataAccessException;

    /**
     * Obtener objeto Y padre.
     *
     * @param objeto
     *            the objeto
     * @param id
     *            the id
     * @return the object
     * @throws DataAccessException
     *             the data access exception
     */
    public Object obtenerObjetoYPadre(Object objeto, Object id) throws DataAccessException;

    /**
     * Obtener hijos.
     *
     * @param padre
     *            the padre
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List obtenerHijos(Object padre) throws DataAccessException;

    /**
     * Obtener dependencia por pagina web.
     *
     * @param paginaWeb
     *            the pagina web
     * @return the dependencia
     * @throws DataAccessException
     *             the data access exception
     */
    public Dependencia obtenerDependenciaPorPaginaWeb(String paginaWeb) throws DataAccessException;

    /**
     * Obtener lista objetos ordenados asc.
     *
     * @param object
     *            the object
     * @param campoOrden
     *            the campo orden
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List obtenerListaObjetosOrdenadosAsc(Object object, String campoOrden) throws DataAccessException;

    /**
     * Obtener lista objetos ordenados asc G.
     *
     * @param <T>
     *            the generic type
     * @param t
     *            the t
     * @param campoOrden
     *            the campo orden
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    <T> List<T> obtenerListaObjetosOrdenadosAscG(Class<T> t, String campoOrden) throws DataAccessException;

    /**
     * Gets the reporte estudiante proyecto.
     *
     * @param m
     *            the m
     * @return the reporte estudiante proyecto
     * @throws DataAccessException
     *             the data access exception
     */
    public List getReporteEstudianteProyecto(Long m) throws DataAccessException;

    /**
     * Obtener las sedes como objetos dependecias de la tabla "HER_DEPENDENCIA".
     *
     * @return the list
     */
    public List obtenerSedes();

    /**
     * Adjuntar objeto propiedad.
     *
     * @param nombreObjecto
     *            the nombre objecto
     * @param id
     *            the id
     * @param propiedadesAdjuntas
     *            the propiedades adjuntas
     * @return the object
     */
    public Object adjuntarObjetoPropiedad(String nombreObjecto, Object id, String propiedadesAdjuntas);

    /**
     * Buscar empresa XNIT.
     *
     * @param nit
     *            the nit
     * @return the empresa
     */
    public Empresa buscarEmpresaXNIT(String nit);

    /**
     * Obtener lista objetos X lista id.
     *
     * @param listaId
     *            the lista id
     * @param objeto
     *            the objeto
     * @return the list
     */
    public List obtenerListaObjetosXListaId(Object[] listaId, String objeto);

    /**
     * Obtener lista tipo rubro X padre.
     *
     * @param id
     *            the id
     * @return the list
     */
    public List obtenerListaTipoRubroXPadre(Long id);

    /**
     * Gets the sesion.
     *
     * @return the sesion
     */
    public Session getSesion();

    /**
     * Obtener lista tipo rubro 1 nivel.
     *
     * @param incluirPadres
     *            the incluir padres
     * @return the list
     */
    public List obtenerListaTipoRubro1Nivel(boolean incluirPadres);

    /**
     * Obtener hijos tipo X padre.
     *
     * @param idTipoPadre
     *            the id tipo padre
     * @return the list
     */
    public List obtenerHijosTipoXPadre(Long idTipoPadre);
    
    public List obtenerHijosTipoXPadreOrdenABC(Long idTipoPadre);

    /**
     * Carga dependencia.
     *
     * @param nit
     *            the nit
     * @return the dependencia
     */
    public Dependencia cargaDependencia(String nit);

    /**
     * Obtener palabras clave conteniendo cadena.
     *
     * @param nombre
     *            the nombre
     * @return the list
     */
    public List obtenerPalabrasClaveConteniendoCadena(String nombre);

    /**
     * Obtener palabras clave empezando con.
     *
     * @param nombre
     *            the nombre
     * @return the list
     */
    public List obtenerPalabrasClaveEmpezandoCon(String nombre);

    /**
     * Lista de objetos Y diferente el id string A.
     *
     * @param clase
     *            the clase
     * @param id
     *            the id
     * @return the list
     */
    public List listaDeObjetosYDiferenteElIdStringA(String clase, String id);

    /**
     * Buscar lista de instituciones por nombre.
     *
     * @param nombre
     *            the nombre
     * @return the list
     */
    public List buscarListaDeInstitucionesPorNombre(String nombre);

    /**
     * Buscar departamentos facultad.
     *
     * @param idFacultad
     *            the id facultad
     * @return the list
     */
    public List buscarDepartamentosFacultad(String idFacultad);

    /**
     * Obtener servicios X padre.
     *
     * @param s
     *            the s
     * @return the list
     */
    public List obtenerServiciosXPadre(Servicio s);

    /**
     * Obtener servicio de 1 nivel X rol.
     *
     * @param r
     *            the r
     * @return the list
     */
    public List obtenerServicioDe1NivelXRol(Rol r);

    /**
     * Obtener departamentos.
     *
     * @return the list
     */
    public List obtenerDepartamentos();

    /**
     * Buscar departamentos facultad persona.
     *
     * @param idPersona
     *            the id persona
     * @return the list
     */
    public List buscarDepartamentosFacultadPersona(String idPersona);

    /**
     * Obtener lista producto hijo.
     *
     * @param idPadre
     *            the id padre
     * @return the list
     */
    public List obtenerListaProductoHijo(String idPadre);

    /**
     * Obtener lista compromiso hijo.
     *
     * @param idPadre
     *            the id padre
     * @return the list
     */
    public List obtenerListaCompromisoHijo(Long idPadre);

    /**
     * Obtener lista requisito hijo.
     *
     * @param idPadre
     *            the id padre
     * @return the list
     */
    public List obtenerListaRequisitoHijo(Long idPadre);

    /**
     * Verificar evaluador convocatoria.
     *
     * @param mod
     *            the mod
     * @return the list
     */
    public List verificarEvaluadorConvocatoria(String mod);

    /**
     * Verificar evaluador interno convocatoria.
     *
     * @param mod
     *            the mod
     * @return the list
     */
    public List verificarEvaluadorInternoConvocatoria(String mod);

    /**
     * Obtenerinstitucion por nombre.
     *
     * @param nombre
     *            the nombre
     * @return the institucion
     */
    public Institucion obtenerinstitucionPorNombre(String nombre);

    /**
     * Buscar lista de nombres instituciones por nombre.
     *
     * @param nombre
     *            the nombre
     * @return the list
     */
    public List buscarListaDeNombresInstitucionesPorNombre(String nombre);

    /**
     * Obtener estado investigador.
     *
     * @param id
     *            the id
     * @return the persona
     */
    public Persona obtenerEstadoInvestigador(String id);

    /**
     * Actualizar estado evaluador.
     *
     * @param person
     *            the person
     */
    public void actualizarEstadoEvaluador(Persona person);

    /**
     * Obtener id rubro.
     *
     * @return the string
     */
    public String obtenerIdRubro();

    /**
     * Insertar tipo rubro.
     *
     * @param consecutivo
     *            the consecutivo
     * @param nombre
     *            the nombre
     */
    public void insertarTipoRubro(String consecutivo, String nombre);

    /**
     * Obtener lista objetos administrador.
     *
     * @param clase
     *            the clase
     * @param id
     *            the id
     * @return the list
     */
    public List obtenerListaObjetosAdministrador(String clase, String id);

    /**
     * Obtener lista documentos vice.
     *
     * @param opcion
     *            the opcion
     * @return the list
     */
    public List<DocumentoVice> obtenerListaDocumentosVice(String opcion);

    /**
     * Insertar recursos.
     *
     * @param padre
     *            the padre
     * @param mod
     *            the mod
     * @param sede
     *            the sede
     * @param valor
     *            the valor
     * @param apoyo
     *            the apoyo
     */
    public void insertarRecursos(Long padre, Long mod, String sede, Long valor, Long apoyo);

    /**
     * Seleccionar valores.
     *
     * @param padre
     *            the padre
     * @param mod
     *            the mod
     * @return the list
     */
    public List seleccionarValores(Long padre, Long mod);

    /**
     * Actualizar recursos.
     *
     * @param padre
     *            the padre
     * @param mod
     *            the mod
     * @param sede
     *            the sede
     * @param valor
     *            the valor
     * @param apoyo
     *            the apoyo
     */
    public void actualizarRecursos(Long padre, Long mod, String sede, Long valor, Long apoyo);

    /**
     * Obtener objeto XID.
     *
     * @param clase
     *            the clase
     * @param id
     *            the id
     * @return the list
     */
    // Aurelio
    public List obtenerObjetoXID(String clase, String id);

    /**
     * Obtener objeto XID.
     *
     * @param <T>
     *            the generic type
     * @param clazz
     *            the clazz
     * @param id
     *            the id
     * @return the list
     */
    <T> List<T> obtenerObjetoXID(Class<T> clazz, String id);

    /**
     * Obtener fecha DB.
     *
     * @return the date
     */
    public Date obtenerFechaDB();

    /**
     * Ejecutar sentencia.
     *
     * @param sql
     *            the sql
     * @return true, if successful
     */
    boolean ejecutarSentencia(String sql);

    /**
     * Obtener convocatorias buscador.
     *
     * @param where
     *            the where
     * @param fromSql
     *            the from sql
     * @param limite
     *            the limite
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List<ConvocatoriaPadre> obtenerConvocatoriasBuscador(String where, String fromSql, boolean limite)
            throws DataAccessException;

    /**
     * Obtener convocatorias buscador principal.
     *
     * @param where
     *            the where
     * @param fromSql
     *            the from sql
     * @param limite
     *            the limite
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List<ConvocatoriaPadre> obtenerConvocatoriasBuscadorPrincipal(String where, String fromSql, boolean limite)
            throws DataAccessException;

    /**
     * Obtener ECP catalogo buscador.
     *
     * @param where
     *            the where
     * @param fromSql
     *            the from sql
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List<Proyecto> obtenerECPCatalogoBuscador(String where, String fromSql) throws DataAccessException;

    /**
     * Obtener ensayos laboratorio buscador.
     *
     * @param where
     *            the where
     * @param where2
     *            the where 2
     * @param fromSql
     *            the from sql
     * @param sedesSeleccionadas
     *            the sedes seleccionadas
     * @param facultadesSeleccionadas
     *            the facultades seleccionadas
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List<LaboratorioDetalleEnsayosServicios> obtenerEnsayosLaboratorioBuscador(String where, String where2,
            String fromSql, List<Dependencia> sedesSeleccionadas, List<Dependencia> facultadesSeleccionadas)
            throws DataAccessException;

    /**
     * Lista nombres empresas contiene.
     *
     * @param nombreEmpresa
     *            the nombre empresa
     * @return the list
     */
    public List<String> listaNombresEmpresasContiene(String nombreEmpresa);
    
    public Boolean esAmbienteProduccion() throws HibernateException, SQLException;

    /**
     * Buscar empresa X nombre.
     *
     * @param nombre
     *            the nombre
     * @return the empresa
     */
    public Empresa buscarEmpresaXNombre(String nombre);

    /**
     * Descargar documento disco.
     *
     * @param path
     *            the path
     * @param origen
     *            the origen
     */
    public void descargarDocumentoDisco(String path, Boolean origen);

    /**
     * Es estudiante.
     *
     * @param tipoInvestigador
     *            the tipo investigador
     * @return true, if successful
     */
    public boolean esEstudiante(String tipoInvestigador);

    /**
     * Obtener coleccion.
     *
     * @param id
     *            the id
     * @return the coleccion
     */
    public Coleccion obtenerColeccion(Long id);

    /**
     * Obtener investigador por email.
     *
     * @param correo
     *            the correo
     * @return the investigador
     */
    public Investigador obtenerInvestigadorPorEmail(String correo);

    /**
     * Obtener coleccion buscador.
     *
     * @param where
     *            the where
     * @return the list
     */
    public List<Coleccion> obtenerColeccionBuscador(String where);

    /**
     * Obtener instructivos.
     *
     * @param sql
     *            the sql
     * @return the list
     */
    public List<Instructivo> obtenerInstructivos(String sql);

    /**
     * Obtener preguntas.
     *
     * @param sql
     *            the sql
     * @return the list
     */
    public List<Pregunta> obtenerPreguntas(String sql);

    /**
     * Duplicar proyecto.
     *
     * @param proyecto
     *            the proyecto
     * @return the long
     */
    public Long duplicarProyecto(Long proyecto);

    /**
     * Obtener solicitudes aprobadas unidad administrativa.
     *
     * @param dependencias
     *            the dependencias
     * @return the list
     */
    public List obtenerSolicitudesAprobadasUnidadAdministrativa(String dependencias);

    /**
     * Obtener convocatoria movilidades.
     *
     * @param convocatoria
     *            the convocatoria
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List obtenerConvocatoriaMovilidades(String convocatoria) throws DataAccessException;

    /**
     * Guardar coleccion.
     *
     * @param col
     *            the col
     * @return true, if successful
     */
    public boolean guardarColeccion(Coleccion col);

    /**
     * Obtener total lista otros boletin.
     *
     * @return the long
     */
    public Long obtenerTotalListaOtrosBoletin();

    /**
     * Obtener mapa.
     *
     * @param sql
     *            the sql
     * @return the list
     */
    public List<Map> obtenerMapa(String sql);
    
    public List<Map> obtenerMapa(String sql, String from);

    /**
     * Insertar objeto con id long.
     *
     * @param objeto
     *            the objeto
     * @param id
     *            the id
     * @throws DataAccessException
     *             the data access exception
     */
    public void insertarObjetoConIdLong(Object objeto, Long id) throws DataAccessException;

    /**
     * Consecutivo secuencia.
     *
     * @param secuencia
     *            the secuencia
     * @return the long
     */
    public Long consecutivoSecuencia(String secuencia);

    /**
     * Obtener historico solicitud.
     *
     * @param solicitud
     *            the solicitud
     * @return the list
     */
    public List<HistoricoEstadoSolicitud> obtenerHistoricoSolicitud(Solicitud solicitud);

    /**
     * Obtener dependencias sede.
     *
     * @param nivelSolicitante
     *            the nivel solicitante
     * @param estado
     *            the estado
     * @return the list
     */
    public List<Dependencia> obtenerDependenciasSede(String nivelSolicitante, String estado);

    /**
     * Obtener dominio detalle.
     *
     * @param tipoDominio
     *            the tipo dominio
     * @param estado
     *            the estado
     * @return the list
     */
    public List<DominioDetalle> obtenerDominioDetalle(String tipoDominio, boolean estado);

    /**
     * Obtener dominio detalle unico.
     *
     * @param idDominio
     *            the id dominio
     * @param domDetTipo
     *            the dom det tipo
     * @return the dominio detalle
     */
    public DominioDetalle obtenerDominioDetalleUnico(String idDominio, String domDetTipo);

    /**
     * Obtener historico estados laboratorio.
     *
     * @param labId
     *            the lab id
     * @return the list
     */
    public List<LaboratorioLogLaboratorios> obtenerHistoricoEstadosLaboratorio(Long labId);

    /**
     * Obtener historico estados equipo.
     *
     * @param equipoId
     *            the equipo id
     * @return the list
     */
    public List<LaboratorioLogEquipos> obtenerHistoricoEstadosEquipo(Long equipoId);

    /**
     * Obtener historico estados actividad.
     *
     * @param actividadId
     *            the actividad id
     * @return the list
     */
    public List<LaboratorioLogActividades> obtenerHistoricoEstadosActividad(Long actividadId);

    /**
     * Obtener persona rol X id persona.
     *
     * @param tipoDoc
     *            the tipo doc
     * @param numDoc
     *            the num doc
     * @param rol
     *            the rol
     * @return the list
     */
    public List<PersonaRol> obtenerPersonaRolXIdPersona(String tipoDoc, String numDoc, String rol);
    
    public List<PersonaRol> obtenerPersonaRolXIdPersonaLaboratorios(String tipoDoc, String numDoc);
    
//    public List<PersonaRol> obtenerListaPersonaRolXidRol(String idRol) throws DataAccessException;
    
    public List<Reporte> obtenerListaIndicadores(String nivel, Long categoria);
    
    /**
     * Obtener requerimientos por ingeniero.
     *
     * @param ingeniero
     *            documento ingeniero asignado
     * @return the list
     */
    public List<Requerimiento> obtenerRequerimientosXIngeniero(String ingeniero) throws DataAccessException;
    
    /**
     * Obtener requerimientos por ingeniero.
     *
     * @param ccIng
     *            documento ingeniero asignado
     * @return the list
     */
    public List<Requerimiento> obtenerRequerimientosPorIngeniero(String ccIng) throws DataAccessException;
    
    public void actualizarInfoAlertaActividadesLab(Long idActividad, Long alertaEnviada, String textoAlerta);
    
    public void insertarInfoAlertaMovilidades(LogAlertasAutomaticas alerta);
    
    public void actualizarNotificacionLaboratoriosProyecto(Long idProyecto, Long idLab, Long valor, Long tipo);
    
    public Boolean consultaNotificacionEnviadaLabsProyecto(Long idProyecto, Long idLab, Long tipo);

    public Reporte obtenerReporte(Long id);
    
    public Object[] ejecutarQuerySql(String sql) throws SQLException;
    
    public Laboratorio obtenerLaboratorioXID(Long idLab) throws DataAccessException;
    
    public Rol obtenerRolPersonaLaboratorioxIDLab(Long idLab, String tipoDocumento, String documento) throws DataAccessException;
    
    public List<TipoDocumento> obtenerTiposDeDocumentoMetrologia() throws DataAccessException;
    
    public List<TipoFormacion> obtenerTiposDeFormacion() throws DataAccessException;
    
    public List<EstadoCivil> obtenerTiposDeEstadoCivil() throws DataAccessException;

    public void calcularPorcentajeLab(Laboratorio laboratorioActual);
    
    public List<TipoDocumento> obtenerTiposDeDocumento() throws DataAccessException;
    
    public Persona obtenerCoordinadorLaboratorio(Long idLab) throws DataAccessException;
    
    public List<Laboratorio> obtenerListaLaboratoriosActivos() throws DataAccessException;
    
    public List<LaboratorioCostosServicio> obtenerAnalisisCostosEnsayosServicios(Long idServicio) throws DataAccessException;
    
    public List<LaboratorioSolicitud> obtenerListaSolicitudesLaboratorioXTIpoSol(Long idLab, Long tipoSol) throws DataAccessException;
    
    public List<LaboratorioSolicitud> obtenerListaSolicitudesLaboratorioXPersonaXTIpoSol(IdPersona idPersona, Long tipoSol) throws DataAccessException;
    
    public List obtenerListasMetrologia(String entidad, Long idLab) throws DataAccessException;
    
    public List<LaboratorioAreasSecundariasOCDE> obtenerAreasOCDESecundariasLab(Long idLab) throws DataAccessException;
    
    public List<ArchivoLaboratorio> obtenerArchivosLaboratorioXidSol(Long idSol) throws DataAccessException;
    
    public List<ArchivoLaboratorio> obtenerArchivosLaboratorioXidActividad(Long idActividad) throws DataAccessException;
    
    public List<ArchivoLaboratorio> obtenerArchivosInsumoXidInsumoXidTipoArchivo(Long idInsumo, Long tipoArchivo) throws DataAccessException;
    
    public List<ArchivoLaboratorio> obtenerArchivosLaboratorioXidReporteDanio(Long idReporte) throws DataAccessException;
    
    public String buscarEnDescripcion(String descripcionBien, String queBusca);
    
    public List<Ciudad> obtenerListaCiudades() throws DataAccessException;
    
    public List<Pais> obtenerListaPaisesISO() throws DataAccessException;
    
    public List<LaboratorioActividadEquipoInterfazAlerta> obtenerActividadesCandidatasAlertas(Integer diasAntesAlerta);
    
    public List<SemilleroInforme> obtenerInformesSemillerosAlertas();
    
    public void actualizarInfoSemilleroInforme(Integer idInforme, Integer numNotificaciones);

	public List obtenerListaTipoRubro2022porNivel(int nivel);
	    
    public List<Aval> consultaAvalesPendientesRectoria() throws DataAccessException;

	public List<InvestigadorLineaInvestigacion> obtenerLineasInvestigacionInvestigador(String tipo, String doc);
	
	public List<MovilidadAlertaAutomatica> consultaMovilidadesPendienteInforme() throws DataAccessException;

	public ParametroMaestro obtenerParametroPorNombre(String parametro);

	public List<DominioDetalle> obtenerDominioDetalleListaUnico(String tipoDominio, String domDetTipo);

	public Long consultaUltimoIdPorDependenciaTipoCarta(String sede, Date fecha, String tipoCarta) throws SQLException;
	
	public List<Integer> obtenerIdsActividadesRegistradasPorSemilleroYPorcentaje(String semillero, String porcentaje);
}
