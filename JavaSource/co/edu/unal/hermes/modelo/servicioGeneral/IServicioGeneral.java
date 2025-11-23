package co.edu.unal.hermes.modelo.servicioGeneral;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

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
import co.edu.unal.hermes.modelo.MovilidadAlertaAutomatica;
import co.edu.unal.hermes.modelo.MovilidadArchivo;
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
import co.edu.unal.hermes.modelo.SemilleroLaboratorioVista;
import co.edu.unal.hermes.modelo.Servicio;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoFormacion;
import co.edu.unal.hermes.modelo.TipoRequisito;
import co.edu.unal.hermes.modelo.TipoRubro;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.laboratorios.ArchivoLaboratorio;
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioAreasSecundariasOCDE;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioCostosServicio;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleEnsayosServicios;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleEquipos;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioLogActividades;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioLogEquipos;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioLogLaboratorios;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioSolicitud;
import co.edu.unal.hermes.modelo.seguimiento.Solicitud;

public interface IServicioGeneral {

	public List<Aval> obtenerAvalesVice() throws DataAccessException;
	
	List<String[]> consultaValorEjecutado(String idProyecto, String director) throws DataAccessException;
	
	public List<Aval> obtenerAvalesDRE() throws DataAccessException;
	
	public List<Aval> obtenerAvalesCEPI(String dependenciaRev) throws DataAccessException;
	
	public List<Aval> obtenerAvalesCESI(String dependenciaRev) throws DataAccessException;
	
	public List<Aval> obtenerAvalesCESIQueja(String dependenciaRev) throws DataAccessException;

	public List<Bien> consultaEquipos(String placa) throws DataAccessException;
	
	public List<ProyectoLaboratorioVista> consultaProyectosAsociadosLaboratorio(Long idLab) throws DataAccessException;
	
	public List<GrupoLaboratorioVista> consultaGruposAsociadosLaboratorio(Long idLab) throws DataAccessException;
	
	public List<SemilleroLaboratorioVista> consultaSemillerosAsociadosLaboratorio(Long idLab) throws DataAccessException;
	
	public List<Bien> consultaEquiposPorPlacaYNombre(String placa) throws DataAccessException;

	public long consultaRecursosConvocatoria(Long padre, Long modalidad)
			throws SQLException;

	public List obtenerListaArchivosMovilidad(String idMovilidad, Long idArchivo)
			throws DataAccessException;

	public List<MovilidadArchivo> obtenerListaArchivosMovilidad(
			String idMovilidad) throws DataAccessException;

	public int existeMovilidad(String sSql) throws SQLException;

	public List<Aval> consultaAvalesFacultadSede(Long sede)
			throws DataAccessException;

	public List<Aval> consultaAvalesVice() throws DataAccessException;
	
	public List<Aval> consultaAvalesDRE() throws DataAccessException;
	
	public List<ProyectoInforme> obtenerAvalInformeFacultad(String dependencia)
            throws DataAccessException;

	public List<ProyectoInforme> obtenerAvalInformeUab(String dependencia)
			throws DataAccessException;

	public long consultaTotalLaboratoriosActivos()
			throws SQLException;

	public long consultaUltimoSequenciaSolicitud(Long idTipoSolicitud,
			Long idProyecto, Long ano, Long mes, Long dia) throws SQLException;

	public List<ArchivoInforme> obtenerListaArchivosInformes(Long idInforme)
			throws DataAccessException;

	public List obtenerListaSolicitudesRenovacion(Long idProyecto,
			String idPersona, String tipoDocumentoPersona)
			throws DataAccessException;

	public List<InvestigadorEvento> obtenerEventosInvestigador(String tipo,
			String doc) throws DataAccessException;

	public List<InvestigadorEnlace> obtenerEnlacesInvestigador(String tipo,
			String doc) throws DataAccessException;

	public List<InvestigadorPublicacion> obtenerPublicacionesInvestigador(
			String tipo, String doc) throws DataAccessException;

	public List<InvestigadorAreaInvestigacion> obtenerAreasInvestigacionInvestigador(
			String tipo, String doc) throws DataAccessException;

	public List<InvestigadorAreaInteres> obtenerAreasInteresInvestigador(
			String tipo, String doc) throws DataAccessException;

	public List<InvestigadorObraExposicion> obtenerObraExposicionInvestigador(
			String tipo, String doc) throws DataAccessException;

	public List<Laboratorio> obtenerNombreLaboratoriosInvestigador(String tipo,
			String doc) throws DataAccessException;

	public List<InvestigadorAsignatura> obtenerAsignaturasInvestigador(
			String tipo, String doc) throws DataAccessException;

	public List<ProyectoInforme> obtenerListaInformes(Long idProyecto)
			throws DataAccessException;

	public List obtenerCorreoBoletin(int tipo, String sede)
			throws DataAccessException;

	public String envioCorreoBoletin() throws DataAccessException, SQLException;

	public List obtenerCodigoSolicitud(Long idProyecto, String idTipoSol, String solicitudesExistentes)
			throws DataAccessException;

	public void eliminar(String sSql) throws SQLException;

	public Long consultaIdCarta(Long idProyecto, Long idTipoCarta, String sede,
			String año) throws SQLException;

	public Long consultaUltimoIdCarta(String sede, Date fecha)
			throws SQLException;
	
	public Long consultaUltimoIdPorDependenciaTipoCarta(String sede, Date fecha, String tipoCarta)
			throws SQLException;

	public String consultaDescripcionSolicitud(Long idProyecto)
			throws SQLException;

	public Long consultaIdSolicitud(Long idProyecto) throws SQLException;

	/**
	 * @param hql
	 * @deprecated usar obtenerObjetos(Class<T> t, String hql)
	 * @return
	 */
	@Deprecated
	public List obtenerObjetos(String hql);

	// Cualquier cosa echarle la culpa a Miguel Cubides
	<T> List<T> obtenerObjetos(Class<T> t, String hql);

	public <T> List<T> obtenerObjetosLimitado(Class<T> t, String hql);

	public boolean enviarCorreo(String correoDestino, Integer idPlantilla,
			String variable[], String nombreVariables[]);

	public boolean enviarCorreo(String correoDestino, String cuerpoCorreo);

	public boolean enviarCorreo(String correosDestino[], Integer idPlantilla,
			String variable[], String nombreVariables[]);

	public boolean enviarCorreo(String correosDestino[], String cuerpoCorreo);

	public String dependenciaPadre(String idDependencia) throws SQLException;

	public List<Aval> obtenerAvalesDireccion(String dependencia)
			throws DataAccessException;

	public List<Aval> obtenerAvales(String dependencia)
			throws DataAccessException;

	public List<Aval> obtenerAvalesInvestigador(String doc, String tipodoc)
			throws DataAccessException;

	public List<Aval> obtenerAvalesProyecto(String idProyecto) throws DataAccessException;
	
	public List<Aval> obtenerAvalesProyectoXEstatoAvalXTipoAval(String idProyecto, String tiposAval, String estadosAval, Boolean incluirnNoAprobados) throws DataAccessException;
	
	public List<Aval> obtenerAval(String id) throws DataAccessException;

	public void actualizarEvaluacion(int id, String valor)
			throws DataAccessException;

	public PalabraClave obtenerPalabraClaveIngles(String nombre);

	public List<String> obtenerLineasEmpezandoCon(String nombre);
	
	public List obtenerInstitucionesQueContienen(String nombreBusqueda) throws DataAccessException;

	public List obtenerAreasConocimientoEmpezandoCon(String nombre);

	public List listaDeObjetosYDiferenteElIdStringAMod(String clase, String id,
			String mod);

	public List obtenerKeyWordEmpezandoCon(String nombre);

	public List obtenerDependencias(Sede sede);

	public List obtenerFacultades();

	public List<Dependencia> obtenerFacultades(Dependencia pSede);

	/**
	 * 
	 * @param objeto
	 * @deprecated usar obtenerListaObjetos(Class<T> t)
	 * @return
	 */
	@Deprecated
	public List obtenerListaObjetos(String objeto);

	<T> List<T> obtenerListaObjetos(Class<T> t);

	/**
	 * 
	 * @param clase
	 * @param where
	 * @deprecated usar obtenerListaObjetosWhere(Class<T> t, String where) en
	 *             cambio
	 * @return
	 */
	@Deprecated
	public List obtenerListaObjetosWhere(String clase, String where);

	// Cualquier cosa echarle la culpa a Miguel Cubides
	<T> List<T> obtenerListaObjetosWhere(Class<T> t, String where);

	// hasta acá llegan las manitas creativas de Miguel Cubides

	public List obtenerUbicacion(String ubicacion, String valorUbicacion,
			String s_padre, Object o_padre, boolean incluirTodos);

	public void guardarObjeto(Object objeto);
	
	public void guardarObjetoLaboratorio(Object objeto);

	public void insertarObjeto(Object objeto);

	public Object obtenerObjeto(Object clase, Object id);

	public void eliminarObjeto(Object objeto);

	public PalabraClave obtenerPalabraClave(String nombre);

	public Departamento obtenerDepartamento(Ciudad ciudad);

	public List<Institucion> obtenerListaInstituciones();

	public Object obtenerObjetoYPadre(Object objeto, Object id);

	public List obtenerHijos(Object padre);

	public Dependencia obtenerDependenciaPorPaginaWeb(String paginaWeb);

	/**
	 * Obtiene la lista de todos los objetos de la clase "object" ordenados en
	 * orden ascendiente del campo "campoOrden"
	 * 
	 * @param object
	 * @param campoOrden
	 * @deprecated usar obtenerListaObjetosOrdenadosAscG(Class<T> t, String
	 *             campoOrden)
	 * @return
	 */
	@Deprecated
	public List obtenerListaObjetosOrdenadosAsc(Object object, String campoOrden);

	// Cualquier cosa echarle la culpa a Miguel Cubides
	<T> List<T> obtenerListaObjetosOrdenadosAscG(Class<T> t, String campoOrden);

	// hasta acá llegan las manitas creativas de Miguel Cubides

	public List getReporteEstudianteProyecto(Long modalidad);

	public Session getSesion();

	public List<Dependencia> obtenerSedes();

	public Object adjuntarObjetoPropiedad(String nombreObjecto, Object id,
			String propiedadesAdjuntas);

	public Empresa buscarEmpresaXNIT(String nit);

	public List obtenerListaObjetosXListaId(Object[] listaId, String objeto);

	public List obtenerListaTipoRubroXPadre(TipoRubro id);

	public List obtenerListaTipoRubro1Nivel(boolean incluirPadres);

	public List obtenerHijosTipoXPadre(Long idTipoPadre);
	
	public SelectItem[] retornaSelectItemArregloDeHijosDeTipos(Long idPadre);
	
	public List obtenerHijosTipoXPadreOrdenABC(Long idTipoPadre);

	public SelectItem[] retornaSelectItemArregloDeHijosDeTiposOrdenABC(Long idPadre);

	public SelectItem[] selectItemHijosDeTiposValorObjeto(Long idPadre);

	public SelectItem[] selectItemHijosDeTiposValorObjetoSeleccione(Long idPadre);
	
	public SelectItem[] selectItemHijosDeTiposValorObjetoSeleccioneSinValorVacio(Long idPadre);
	
	public SelectItem[] selectItemHijosDeTiposValorObjetoSeleccione2(Long idPadre);
	
	public Float calcularValoresCriticidadXEquipo(LaboratorioDetalleEquipos equipo);
	
	public void calcularValoresCriticidadXLab(Long IdLab, List<LaboratorioDetalleEquipos> listaEquipos);
	
	public Tipos obtenerTipoXid(Long id);
	
	public SelectItem[] selectItemListaLaboratoriosActivos();

	public Dependencia cargaDependencia(String nit);

	public List obtenerPalabrasClaveConteniendoCadena(String nombre);

	public List<String> obtenerPalabrasClaveEmpezandoCon(String nombre);

	public List listaDeObjetosYDiferenteElIdStringA(String clase, String id);

	public List<Institucion> buscarListaDeInstitucionesPorNombre(String nombre);

	public SelectItem[] retornaSelectItemArregloDeHijosDeTiposConClaveLong(
			Long idPadre);

	public List buscarDepartamentosFacultad(String idFacultad);

	public List obtenerServicioDe1NivelXRol(Rol r);

	public List obtenerServiciosXPadre(Servicio s);

	public List obtenerDepartamentos();

	public List buscarDepartamentosFacultadPersona(String idPersona);

	public List obtenerListaProductoHijo(String idPadre);

	public List verificarEvaluadorConvocatoria(String mod);

	public Institucion obtenerinstitucionPorNombre(String nombre);

	public List buscarListaDeNombresInstitucionesPorNombre(String nombre);

	public List verificarEvaluadorInternoConvocatoria(String mod);

	public Persona obtenerEstadoInvestigador(String id);

	public void actualizarEstadoEvaluador(Persona persona);

	public String obtenerIdRubro();

	public String insertarTipoRubro(String consecutivo, String nombre);
	
	public List obtenerListaObjetosAdministrador(String clase, String id);

	public List<DocumentoVice> obtenerListaDocumentosVice(String opcion);

	public void insertarRecursos(Long padre, Long mod, String sede, Long valor,
			Long apoyo);

	public List seleccionarValores(Long padre, Long mod);

	public void actualizarRecursos(Long padre, Long mod, String sede,
			Long valor, Long apoyo);

	public List<TipoRequisito> obtenerListaRequisitoHijo(Long idPadre);

	public List obtenerListaCompromisoHijo(Long idPadre);

	// Aurelio
	public List obtenerObjetoXID(String clase, String id);

	<T> List<T> obtenerObjetoXID(Class<T> clazz, String id);

	public Date obtenerFechaDB();
	
	// ejecuta la sentencia del parámetro
	boolean ejecutarSentencia(String sql);

	public List<ConvocatoriaPadre> obtenerConvocatoriasBuscador(String where,
			String fromSql, boolean limite) throws DataAccessException;

	public List<ConvocatoriaPadre> obtenerConvocatoriasBuscadorPrincipal(
			String where, String fromSql, boolean limite)
			throws DataAccessException;

	public List<Proyecto> obtenerECPCatalogoBuscador(String where,
			String fromSql) throws DataAccessException;

	public List<LaboratorioDetalleEnsayosServicios> obtenerEnsayosLaboratorioBuscador(
			String where,
			String where2,
			String fromSql,
			List<Dependencia> sedesSeleccionadas,
			List<Dependencia> facultadesSeleccionadas)
			throws DataAccessException;
	
	public void actualizarNotificacionLaboratoriosProyecto(Long idProyecto, Long idLab, Long valor, Long tipo) throws DataAccessException;
	
	public Boolean consultaNotificacionEnviadaLabsProyecto(Long idProyecto, Long idLab, Long tipo);

	public SelectItem[] selectItemSedes();

	public SelectItem[] selectItemFacultades(Long idSede);

	public SelectItem[] selectItemDepartamentos(String idFacultad);
	
	public SelectItem[] selectItemDepartamentosBusquedaEquipos(String idFacultad);

	public List<String> listaNombresEmpresasContiene(String nombreEmpresa);
	
	public Boolean esAmbienteProduccion() throws HibernateException, SQLException;

	public Empresa buscarEmpresaXNombre(String nombre);

	public void descargarDocumentoDisco(String path, Boolean origen);

	public boolean esEstudiante(String tipoInvestigador);

	public Coleccion obtenerColeccion(Long id);

	public Investigador obtenerInvestigadorPorEmail(String correo);

	public List<Coleccion> obtenerColeccionBuscador(String sql);

	public List<Instructivo> obtenerInstructivos(String sql);
	
	public List<Pregunta> obtenerPreguntas(String sql);

	public boolean ingresarColeccion(Coleccion coleccion)
			throws DataAccessException;

	public Long duplicarProyecto(Long idProyecto);

	public List obtenerSolicitudesAprobadasUnidadAdministrativa(
			String dependencias);

	public List obtenerConvocatoriaMovilidades(String convocatoria)
			throws DataAccessException;

	public Long obtenerTotalListaOtrosBoletin() throws DataAccessException;

	/**
	 * @param sql
	 *            la sentencia en SQL a ejecutar, de la forma: SELECT XXX AS
	 *            XXX, YYY AS YYY, ... FROM ... WHERE ...
	 * @return Lista de Mapas
	 */
	public List<Map> obtenerMapa(String sql);
	
	public List<Map> obtenerMapa(String sql, String from);

	public void insertarObjetoConIdLong(Object objeto, Long id);

	public Long consecutivoSecuencia(String secuencia);
	
	public List<HistoricoEstadoSolicitud> obtenerHistoricoSolicitud(Solicitud solicitud);

    public List<Dependencia> obtenerDependenciasSede(String nivelSolicitante, String estado);
    
    public List<DominioDetalle> obtenerDominioDetalle(String tipoDominio, boolean estado);
    
    public List<SelectItem> obtenerDominioDetalleSelectItem(String tipoDominio, boolean estado);
    
    public List<DominioDetalle> obtenerDominioDetalle(String tipoDominio);
    
    public DominioDetalle obtenerDominioDetalleUnico(String idDominio, String domDetTipo);

	public List<LaboratorioLogLaboratorios> obtenerHistoricoEstadosLaboratorio(Long id);
	
	public List<LaboratorioLogEquipos> obtenerHistoricoEstadosEquipo(Long equipoId);
	
	public List<LaboratorioLogActividades> obtenerHistoricoEstadosActividad(Long actividadId);

	public List<PersonaRol> obtenerPersonaRolXIdPersona(String tipoDoc, String numDoc, String rol);
	
	public List<PersonaRol> obtenerPersonaRolXIdPersonaLaboratorios(String tipoDoc, String numDoc);
	
	public List<Reporte> obtenerListaIndicadores(String nivel, Long categoria);
	
	public List<Requerimiento> obtenerRequerimientosXIngeniero(String ingeniero);
	
	public List<Requerimiento> obtenerRequerimientosPorIngeniero(String ccIng); 
	
	public Reporte obtenerReporte(Long id);
	
	public void descargarReporteExcelDesdeSql(String sql, String nombreArchivo);
	
	public Persona obtenerCoordinadorLaboratorio(Long idLab) throws DataAccessException;
	
//	public List<Persona> obtenerPersonasxRolIdxSedeId(String rol, Long sedeId) throws DataAccessException;
	
	public List<Laboratorio> obtenerListaLaboratoriosActivos() throws DataAccessException;

	public void calcularPorcentajeLab(Laboratorio laboratorioActual);
	
	public Laboratorio obtenerLaboratorioXID(Long idLab) throws DataAccessException;
	
	public Rol obtenerRolPersonaLaboratorioxIDLab(Long idLab, String tipoDocumento, String documento) throws DataAccessException;
	
	public List<TipoDocumento> obtenerTiposDeDocumento();
	
	public List<TipoFormacion> obtenerTiposDeFormacion();
	
	public List<EstadoCivil> obtenerTiposDeEstadoCivil();
	
	public List<LaboratorioCostosServicio> obtenerAnalisisCostosEnsayosServicios(Long idServicio) throws DataAccessException;
	
	public List<LaboratorioSolicitud> obtenerListaSolicitudesLaboratorioXTIpoSol(Long idLab, Long tipoSol) throws DataAccessException;
	
	public List<LaboratorioSolicitud> obtenerListaSolicitudesLaboratorioXPersonaXTIpoSol(IdPersona idPersona, Long tipoSol) throws DataAccessException;
	
	public List obtenerListasMetrologia(String entidad, Long idLab) throws DataAccessException;
	
	public List<LaboratorioAreasSecundariasOCDE> obtenerAreasOCDESecundariasLab(Long idLab) throws DataAccessException;
	
	public List<ArchivoLaboratorio> obtenerArchivosLaboratorioXidSol(Long idSol) throws DataAccessException;
	
	public List<ArchivoLaboratorio> obtenerArchivosLaboratorioXidActividad(Long idActividad) throws DataAccessException;
	
	public List<ArchivoLaboratorio> obtenerArchivosLaboratorioXidReporteDanio(Long idReporte) throws DataAccessException;
	
	public List<ArchivoLaboratorio> obtenerArchivosInsumoXidInsumoXidTipoArchivo(Long idInsumo, Long tipoArchivo) throws DataAccessException;
	
	public List<Ciudad> obtenerListaCiudades() throws DataAccessException;
	
	public List<Pais> obtenerListaPaisesISO() throws DataAccessException;
	
	public SelectItem[] obtenerListaPaisesISOSelectItem() throws DataAccessException;
	
	public List<TipoDocumento> obtenerTiposDeDocumentoMetrologia();

	public List<TipoRubro> obtenerListaTipoRubro2022porNivel(int i);
	
	public List<Aval> obtenerAvalesRectoria() throws DataAccessException;

	public List<InvestigadorLineaInvestigacion> obtenerLineasInvestigacionInvestigador(String tipo, String doc);
	
	public List<MovilidadAlertaAutomatica> consultaMovilidadesPendienteInforme() throws DataAccessException;

	public ParametroMaestro obtenerParametroPorNombre(String parametro);

	public List<DominioDetalle> obtenerDominioDetalleListaUnico(String valorDominio, String domDetTipo);
	
	public List<Integer> obtenerIdsActividadesRegistradasPorSemilleroYPorcentaje(String semillero, String porcentaje);

}
