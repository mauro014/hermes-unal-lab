package co.edu.unal.hermes.modelo.servicioGeneral;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.HibernateQueryException;
import org.springframework.web.jsf.FacesContextUtils;

import co.edu.unal.hermes.bd.IGeneralDAO;
import co.edu.unal.hermes.modelo.ArchivoInforme;
import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.modelo.Bien;
import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.Coleccion;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
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
import co.edu.unal.hermes.modelo.TipoRubro;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.correo.Correo;
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
import co.edu.unal.hermes.modelo.servicioPersona.ServicioPersona;
import co.edu.unal.hermes.modelo.servicios.IServicioCorreo;
import co.edu.unal.hermes.vista.utils.ExcelUtils;

public class ServicioGeneral implements IServicioGeneral {	
	
	public List<Bien> consultaEquipos(String placa) throws DataAccessException {
		return generalDAO.consultaEquipos(placa);
	}
	
	public List<ProyectoLaboratorioVista> consultaProyectosAsociadosLaboratorio(Long idLab) throws DataAccessException {
		return generalDAO.consultaProyectosAsociadosLaboratorio(idLab);
	}
	
	public List<GrupoLaboratorioVista> consultaGruposAsociadosLaboratorio(Long idLab) throws DataAccessException {
		return generalDAO.consultaGruposAsociadosLaboratorio(idLab);
	}
	
	public List<SemilleroLaboratorioVista> consultaSemillerosAsociadosLaboratorio(Long idLab) throws DataAccessException {
		return generalDAO.consultaSemillerosAsociadosLaboratorio(idLab);
	}
	
	public List<String[]> consultaValorEjecutado(String idProyecto, String director) throws DataAccessException {
		return generalDAO.consultaValorEjecutado(idProyecto, director);
	}
	
	public List<Bien> consultaEquiposPorPlacaYNombre(String placa) throws DataAccessException {
		return generalDAO.consultaEquiposPorPlacaYNombre(placa);
	}
	
	public long consultaRecursosConvocatoria(Long padre, Long modalidad) throws SQLException {
		return this.generalDAO.consultaRecursosConvocatoria(padre, modalidad);
	}

	public List obtenerListaArchivosMovilidad(String idMovilidad, Long idArchivo) throws DataAccessException {
		return this.generalDAO.obtenerListaArchivosMovilidad(idMovilidad, idArchivo);
	}

	public List<MovilidadArchivo> obtenerListaArchivosMovilidad(String idMovilidad) throws DataAccessException {
		return this.generalDAO.obtenerListaArchivosMovilidad(idMovilidad);
	}

	public int existeMovilidad(String sSql) throws SQLException {
		return this.generalDAO.existeMovilidad(sSql);
	}
	
	public List<Aval> consultaAvalesFacultadSede(Long sede) throws DataAccessException {
		return this.generalDAO.consultaAvalesFacultadSede(sede);
	}

	public List<Aval> consultaAvalesVice() throws DataAccessException {
		return this.generalDAO.consultaAvalesVice();
	}
	
	public List<Aval> consultaAvalesDRE() throws DataAccessException {
		return this.generalDAO.consultaAvalesDRE();
	}

	public List<ProyectoInforme> obtenerAvalInformeFacultad(String dependencia) throws DataAccessException {
		return generalDAO.obtenerAvalInformeFacultad(dependencia);
	}
	
	public List<ProyectoInforme> obtenerAvalInformeUab(String dependencia) throws DataAccessException {
		return generalDAO.obtenerAvalInformeUab(dependencia);
	}
	
	public long consultaTotalLaboratoriosActivos() throws SQLException {
		return generalDAO.consultaTotalLaboratoriosActivos();
	}

	public long consultaUltimoSequenciaSolicitud(Long idTipoSolicitud, Long idProyecto, Long ano, Long mes, Long dia) throws SQLException {
		return generalDAO.consultaUltimoSequenciaSolicitud(idTipoSolicitud, idProyecto, ano, mes, dia);
	}

	public List<ArchivoInforme> obtenerListaArchivosInformes(Long idInforme) throws DataAccessException {
		return generalDAO.obtenerListaArchivosInformes(idInforme);
	}
	
	public List obtenerListaSolicitudesRenovacion(Long idProyecto, String idPersona, String tipoDocumentoPersona) throws DataAccessException{
		return generalDAO.obtenerListaSolicitudesRenovacion(idProyecto, idPersona, tipoDocumentoPersona);
	}
	
	public List<InvestigadorEvento> obtenerEventosInvestigador(String tipo, String doc) throws DataAccessException{
		return generalDAO.obtenerEventosInvestigador(tipo, doc);
	}
	
	public List<InvestigadorEnlace> obtenerEnlacesInvestigador(String tipo, String doc) throws DataAccessException{
		return generalDAO.obtenerEnlacesInvestigador(tipo, doc);
	}
	
	public List<InvestigadorPublicacion> obtenerPublicacionesInvestigador(String tipo, String doc) throws DataAccessException{
		return generalDAO.obtenerPublicacionesInvestigador(tipo, doc);
	}
	
	public List<InvestigadorAreaInvestigacion> obtenerAreasInvestigacionInvestigador(String tipo, String doc) throws DataAccessException{
		return generalDAO.obtenerAreasInvestigacionInvestigador(tipo, doc);
	}
	
	public List<InvestigadorAreaInteres> obtenerAreasInteresInvestigador(String tipo, String doc) throws DataAccessException{
		return generalDAO.obtenerAreasInteresInvestigador(tipo, doc);
	}
	
	public List<InvestigadorAsignatura> obtenerAsignaturasInvestigador(String tipo, String doc) throws DataAccessException{
		return generalDAO.obtenerAsignaturasInvestigador(tipo, doc);
	}
	
	public List<InvestigadorObraExposicion> obtenerObraExposicionInvestigador(String tipo, String doc) throws DataAccessException{
		return generalDAO.obtenerObraExposicionInvestigador(tipo, doc);
	}
	
	public List<InvestigadorLineaInvestigacion> obtenerLineasInvestigacionInvestigador(String tipo, String doc) throws DataAccessException{
		return generalDAO.obtenerLineasInvestigacionInvestigador(tipo, doc);
	}
	
	public List<Laboratorio> obtenerNombreLaboratoriosInvestigador(String tipo, String doc) throws DataAccessException{
		return generalDAO.obtenerNombreLaboratoriosInvestigador(tipo, doc);
	}
	
	public List<LaboratorioLogLaboratorios> obtenerHistoricoEstadosLaboratorio(Long labId) throws DataAccessException{
		return generalDAO.obtenerHistoricoEstadosLaboratorio(labId);
	}
	
	public List<LaboratorioLogEquipos> obtenerHistoricoEstadosEquipo(Long equipoId) throws DataAccessException{
		return generalDAO.obtenerHistoricoEstadosEquipo(equipoId);
	}
	
	public List<LaboratorioLogActividades> obtenerHistoricoEstadosActividad(Long actividadId) throws DataAccessException{
		return generalDAO.obtenerHistoricoEstadosActividad(actividadId);
	}
	
	public List<ProyectoInforme> obtenerListaInformes(Long idProyecto) throws DataAccessException {
		return generalDAO.obtenerListaInformes(idProyecto);
	}

	public List obtenerCorreoBoletin(int tipo, String sede) throws DataAccessException {
		return generalDAO.obtenerCorreoBoletin(tipo, sede);
	}

	public String envioCorreoBoletin() throws DataAccessException, SQLException {
		return generalDAO.envioCorreoBoletin();
	}

	public List obtenerCodigoSolicitud(Long idProyecto, String idTipoSol, String solicitudesExistentes) throws DataAccessException {
		return generalDAO.obtenerCodigoSolicitud(idProyecto, idTipoSol,solicitudesExistentes);
	}
	public List<Requerimiento> obternerRequerimientosXIngeniero(String ingeniero) throws DataAccessException{
	    return generalDAO.obtenerRequerimientosXIngeniero(ingeniero);
	}
	
	public List<Requerimiento> obternerRequerimientosPorIngeniero(String ccIng) throws DataAccessException {
	    return generalDAO.obtenerRequerimientosPorIngeniero(ccIng);
	}

	public void eliminar(String sSql) throws SQLException {
		generalDAO.eliminar(sSql);
	}

	public List obtenerObjetos(String hql) {
		return generalDAO.obtenerObjetos(hql);
	}
	
	// XXX revisar cómo manejar el hibernateTemplate para parametrizar el tipo
	// de retorno
	@SuppressWarnings("unchecked")
	public <T> List<T> obtenerObjetos(Class<T> t, String hql) {
		return generalDAO.obtenerObjetos(hql);
	}
	
	public <T> List<T> obtenerObjetosLimitado(Class<T> t, String hql) {
		String hqlTmp = hql.replace("select", "").trim();

		List<String> atributos_1 = new ArrayList<String>();
		List listadoObjetos;
		List<T> listaResultado = new ArrayList<T>();
		String nomAtributoMayus = null;

		while (hqlTmp.indexOf('#') >= 0) {
			hqlTmp = hqlTmp.substring(hqlTmp.indexOf('#')).trim();
			String atributo = hqlTmp.substring(hqlTmp.indexOf('#') + 1,
					hqlTmp.indexOf(' '));
			atributos_1.add(atributo);
			hql = hql.replace("#" + atributo + " ", "");
			hqlTmp = hqlTmp.replace("#" + atributo + " ", "");
		}

		listadoObjetos = generalDAO.obtenerObjetos(hql);
		
		Method method;
		Iterator it = listadoObjetos.iterator();	
		
		for (int k=0; k<listadoObjetos.size();k++){		
			
			Object[] invPro;
			try {
				 invPro = (Object[]) listadoObjetos.get(k);	

			} catch (Exception e) {
				invPro=new Object[1];
				invPro[0]=(Object) listadoObjetos.get(k);	
			}			
			
			List<String> atributos = new ArrayList<String>();			
			atributos.addAll(atributos_1);
			try {
				Class cls = Class.forName(t.getName());	
					
				Object ObjectClass = cls.newInstance();
				Class noparams[] = {};
				
				for (int i = 0; i < invPro.length; i++) {			
										
					if(atributos.get(i).toString().indexOf('.')>0){		
						String nombreRelacion="";
						Boolean nombreNewRelacion=new Boolean(false);
						Integer indiceResultado=null;
						if(i==0){
							nombreNewRelacion=true;
						}
						else{
							nombreNewRelacion=(atributos.get(i).toString().substring(0, atributos.get(i).toString().indexOf('.'))!=nombreRelacion);
						}
						if(nombreNewRelacion){
							nombreRelacion=atributos.get(i).toString().substring(0, atributos.get(i).toString().indexOf('.'));
							
							nomAtributoMayus="get"+ atributos.get(i).substring(0, 1)
									.toUpperCase()+atributos.get(i).toString().substring(1, atributos.get(i).toString().indexOf('.'));					
							
							Method mthd=cls.getMethod(nomAtributoMayus,noparams);
							Object output=(Object)mthd.invoke(ObjectClass,null);												
							Class cls1 = Class.forName(output.getClass().getName());							
							Object ObjectClass1 = cls1.newInstance();							
						
							for (int j = i; j < invPro.length; j++) {
								if(atributos.get(j).toString().indexOf('.')>0){		
									if(atributos.get(j).toString().substring(0, atributos.get(j).toString().indexOf('.')).equals(nombreRelacion)){
										nomAtributoMayus="set"+atributos.get(j).substring(atributos.get(j).indexOf('.')+1,atributos.get(j).indexOf('.')+2).toUpperCase()+
												atributos.get(j).substring(atributos.get(j).indexOf('.')+2);
										
										Method mthd_1=cls1.getDeclaredMethod(nomAtributoMayus, String.class);
										mthd_1.invoke(ObjectClass1, new String(
												(String) invPro[j]));
										invPro[j]=null;
										atributos.set(j,atributos.get(j).substring(0,atributos.get(j).toString().indexOf('.')));
									}
								}			
								
						}	
							invPro[i]=ObjectClass1;
					}				
						
										
					}
				}
				
				
				
				for (int i = 0; i < invPro.length; i++) {
					nomAtributoMayus="";
					if (invPro[i] != null) {							

						try {							
							 Class[] cArg = new Class[1];
						     cArg[0] = invPro[i].getClass();
							
							nomAtributoMayus+= "set"
									+ atributos.get(i).substring(0, 1)
											.toUpperCase()
									+ atributos.get(i).substring(1);
//							method = cls.getDeclaredMethod(nomAtributoMayus,
//									Long.class);
//							method.invoke(ObjectClass, new Long(
//									(Long) invPro[i]));
							method = cls.getMethod(nomAtributoMayus,
									cArg[0]);
							method.invoke(ObjectClass, 
									invPro[i]);	
						} catch (Exception e) {
							
							try {
								method = cls.getMethod(
										nomAtributoMayus, String.class);
								method.invoke(ObjectClass, new String(
										(String) invPro[i].toString()));

							} catch (Exception e2) {

								try {
									method = cls.getMethod(
											nomAtributoMayus, Date.class);
									method.invoke(ObjectClass,
											((Date) invPro[i]));
									
								} catch (Exception e3) {
									
									try {
									
									method = cls.getMethod(
											nomAtributoMayus, Boolean.class);
									method.invoke(ObjectClass, new Boolean(
											(Boolean) invPro[i]));																		
										
									} catch (Exception e4) {
										
										try {
											
											method = cls.getMethod(
													nomAtributoMayus, Blob.class);
											method.invoke(ObjectClass, 
													(Blob) invPro[i]);																		
												
											} catch (Exception e5) {
										
										Class cls1 = cls.getSuperclass();	
											
										while (cls1 != null) {
											try {
												method = cls1.getMethod(nomAtributoMayus,
									        			invPro[i].getClass());
												method.invoke(ObjectClass, 
														invPro[i]);													
												cls1=null;												
												} catch (Exception e6) {
													 cls1 = cls1.getSuperclass();
												}									   
										}
											}
									}
									
								}
							}
						}
					}
				}
				listaResultado.add((T) ObjectClass);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		return listaResultado;
	}
	
	public boolean enviarCorreo(String correoDestino, Integer idPlantilla, String variable[], String nombreVariables[]){
		String correosDestino[]={correoDestino};		
		return enviarCorreo(correosDestino, idPlantilla, variable, nombreVariables);
	}
	public boolean enviarCorreo(String correoDestino, String cuerpoCorreo){
		String correosDestino[]={correoDestino};
		String entrada[]={new String()};
		return enviarCorreo(correosDestino, cuerpoCorreo);
	}
	
	public boolean enviarCorreo(String correosDestino[], String cuerpoCorreo )	{
		String entrada[]={new String()};
		return enviarCorreo(correosDestino,0,entrada,entrada,cuerpoCorreo);
	}	
	
	public boolean enviarCorreo(String correosDestino[], Integer idPlantilla, String variable[], String nombreVariables[] )	{
		return enviarCorreo(correosDestino,idPlantilla,variable,nombreVariables,"");
	}
		
	public boolean enviarCorreo(String correosDestino[], Integer idPlantilla, String variable[], String nombreVariables[],  String cuerpoCorreo)	{
		IServicioCorreo servicioCorreo = null;
		FacesContext facesContext;
		ApplicationContext appCtx;		
		facesContext = javax.faces.context.FacesContext.getCurrentInstance();
		appCtx = FacesContextUtils.getWebApplicationContext(facesContext);		
		servicioCorreo=(IServicioCorreo) appCtx.getBean("servicioCorreo");
		CorreoPlantilla correoActual = new CorreoPlantilla();
		
		String elCuerpoCorreo=cuerpoCorreo;
		Boolean envio = false;
		if(idPlantilla>0){
			correoActual = cargarPlantilla(idPlantilla);		
			elCuerpoCorreo=editarCorreo(correoActual.getCuerpo(), variable, nombreVariables);
		}		
		Correo correo = new Correo();
		correo.setOrigen(Correo.CORREO_HERMES);
		for (int i=0; i<correosDestino.length;i++){
			String dirCorreo = correosDestino[i];
			correo.adicionarDireccion(dirCorreo);
		}		
		//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
		correo.setAsunto(correoActual.getAsunto());
		correo.setCuerpo(elCuerpoCorreo);
		try{
			if(servicioCorreo.enviarCorreo(correo)){
				 envio = true;
			}
			else envio = false;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
		return envio;
	}	

	public String editarCorreo(String cuerpoCorreo, String variable[],
			String nombreVariables[]) {
		String correo="";
		try {
			correo = cuerpoCorreo;
			
			for(int i=0; i<variable.length; i++){
				correo = correo.replaceAll("<<"+nombreVariables[i]+">>", variable[i]);
			}		
			
		} catch (Exception e) {
			
		}
		return correo;
	}
	
	public CorreoPlantilla cargarPlantilla(int cod_id) {

		CorreoPlantilla correoActualAux = new CorreoPlantilla();
		CorreoPlantilla a = new CorreoPlantilla();
		List lista = obtenerObjetos("select c from CorreoPlantilla c where c.id='"
						+ cod_id + "'");
		if (lista != null && lista.size() > 0) {
			correoActualAux = (CorreoPlantilla) lista.get(0);
		}

		return correoActualAux;
	}
	
	public String consultaDescripcionSolicitud(Long idProyecto) throws SQLException {
		return this.generalDAO.consultaDescripcionSolicitud(idProyecto);
	}

	public Long consultaIdSolicitud(Long idProyecto) throws SQLException {
		return this.generalDAO.consultaIdSolicitud(idProyecto);
	}

	public Long consultaIdCarta(Long idProyecto, Long idTipoCarta, String sede, String año) throws SQLException {
		return this.generalDAO.consultaIdCarta(idProyecto, idTipoCarta, sede, año);
	}

	public Long consultaUltimoIdCarta(String sede, Date fecha) throws SQLException {
		return this.generalDAO.consultaUltimoIdCarta(sede, fecha);
	}
	
	public Long consultaUltimoIdCarta(String sede, Date fecha, String tipoCarta) throws SQLException {
		return this.generalDAO.consultaUltimoIdCarta(sede, fecha);
	}

	public String dependenciaPadre(String idDependencia) throws SQLException {
		return this.generalDAO.dependenciaPadre(idDependencia);
	}

	public List<Aval> obtenerAvalesDireccion(String dependencia) throws DataAccessException {
		return this.generalDAO.obtenerAvalesDireccion(dependencia);
	}
	
	public List<Aval> obtenerAvalesVice() throws DataAccessException {
		return this.generalDAO.obtenerAvalesVice();
	}
	
	public List<Aval> obtenerAvalesDRE() throws DataAccessException {
		return this.generalDAO.obtenerAvalesDRE();
	}
	
	public List<Aval> obtenerAvalesCEPI(String dependenciaRev) throws DataAccessException {
		return this.generalDAO.obtenerAvalesCEPI(dependenciaRev);
	}
	
	public List<Aval> obtenerAvalesCESI(String dependenciaRev) throws DataAccessException {
		return this.generalDAO.obtenerAvalesCESI(dependenciaRev);
	}
	
	public List<Aval> obtenerAvalesCESIQueja(String dependenciaRev) throws DataAccessException {
		return this.generalDAO.obtenerAvalesCESIQueja(dependenciaRev);
	}

	public List<Aval> obtenerAvalesRectoria() throws DataAccessException {
		return this.generalDAO.obtenerAvalesRectoria();
	}
	
	public List<Aval> obtenerAvalesInvestigador(String doc, String tipodoc) throws DataAccessException {
		return this.generalDAO.obtenerAvalesInvestigador(doc, tipodoc);
	}

	public List<Aval> obtenerAvalesProyecto(String idProyecto) throws DataAccessException {
		return this.generalDAO.obtenerAvalesProyecto(idProyecto);
	}
	
	public List<Aval> obtenerAvalesProyectoXEstatoAvalXTipoAval(String idProyecto, String tiposAval, String estadosAval, Boolean incluirnNoAprobados) throws DataAccessException {
		return this.generalDAO.obtenerAvalesProyectoXEstatoAvalXTipoAval(idProyecto,tiposAval,estadosAval,incluirnNoAprobados);
	}
	
//	public List<Aval> obtenerAvalesProyectoXEstadosAval(String idProyecto, List<String> listaEstados) {
//		List<Aval> listaAvalesProyecto = obtenerAvalesProyecto(idProyecto);
//		List<Aval> listaAvalesEstados = new ArrayList<Aval>();
//		
//		for (Aval aval : listaAvalesProyecto) {
//			for (String estado : listaEstados) {
//				if(aval.getTipo().equals(estado)) {
//					listaAvalesEstados.add(aval);
//					break;
//				}
//			}
//		}
//		
//		return listaAvalesEstados;
//	}

	public List<Aval> obtenerAvales(String dependencia) throws DataAccessException {
		return this.generalDAO.obtenerAvales(dependencia);
	}

	public List<Aval> obtenerAval(String id) throws DataAccessException {
		return this.generalDAO.obtenerAval(id);
	}

	public void actualizarEvaluacion(int id, String valor) throws DataAccessException {

		this.generalDAO.actualizarEvaluacion(id, valor);
	}
	
	private IGeneralDAO generalDAO;

	public void setGeneralDAO(IGeneralDAO generalDAO) {
		this.generalDAO = generalDAO;
	}
	

	public List obtenerDependencias(Sede sede) {
		return generalDAO.obtenerDependencias(sede);
	}

	public List obtenerFacultades(Dependencia pSede) {
		return generalDAO.obtenerFacultades(pSede);
	}

	public List obtenerListaObjetos(String objeto) {
		List listaObjetos = null;
		try {
			listaObjetos = generalDAO.obtenerListaObjetos(objeto);
		} catch (HibernateQueryException e) {
			// No existe el objeto
		}
		return listaObjetos;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> obtenerListaObjetos(Class<T> t) {
		List<T> listaObjetos = null;
		try {
			listaObjetos = generalDAO.obtenerListaObjetos(t.getSimpleName());
		} catch (HibernateQueryException e) {
			// No existe el objeto
		}
		return listaObjetos;
	}

	public List obtenerListaObjetosWhere(String clase, String where) {
		return generalDAO.obtenerListaObjetosWhere(clase, where);
	}

	// Cualquier cosa echarle la culpa a Miguel Cubides
	public <T> List<T> obtenerListaObjetosWhere(Class<T> t, String where) {
		return generalDAO.obtenerListaObjetosWhere(t, where);
	}

	// hasta acá llegan las manitas creativas de Miguel Cubides

	/**
	 * obtiene una lista de ubicaciones que puede ser pais, region,
	 * departamento, ciudad y depende de los parametros de ubicacion y su
	 * respectivo padre. ejemplo:
	 */
	public List obtenerUbicacion(String ubicacion, String valorUbicacion, String s_padre, Object o_padre, boolean incluirTodos) {
		return generalDAO.obtenerUbicacion(ubicacion, valorUbicacion, s_padre, o_padre, incluirTodos);
	}

	public void guardarObjeto(Object objeto) {
		try {
			generalDAO.guardarObjeto(objeto);
		} catch (DataIntegrityViolationException ex) {
			ex.printStackTrace();
			System.out.println("Data Integrity");
			if (ex.getMessage().indexOf("PK_HER_PERSONA_ROL") > 0) {
				
				//System.out.println(ex.getStackTrace());
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	public void guardarObjetoLaboratorio(Object objeto) {
		try {
			generalDAO.guardarObjetoLaboratorio(objeto);
		} catch (DataIntegrityViolationException ex) {
			ex.printStackTrace();
			if (ex.getMessage().indexOf("PK_HER_PERSONA_ROL") > 0) {
				
				//System.out.println(ex.getStackTrace());
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	 public boolean ingresarColeccion(Coleccion col) {
	        
	        boolean guardo = guardarColeccion(col);
	        return guardo;
	 }
	 
	 private boolean guardarColeccion(Coleccion col) {
		 boolean guardo = generalDAO.guardarColeccion(col);
		 return guardo;
	 }

	public void insertarObjeto(Object objeto) {
		generalDAO.insertarObjeto(objeto);
	}

	public PalabraClave obtenerPalabraClave(String nombre) {
		return generalDAO.obtenerPalabraClave(nombre);
	}

	public PalabraClave obtenerPalabraClaveIngles(String nombre) {
		return generalDAO.obtenerPalabraClaveIngles(nombre);
	}

	public Departamento obtenerDepartamento(Ciudad ciudad) {
		return generalDAO.obtenerDepartamento(ciudad);
	}

	public void eliminarObjeto(Object objeto) {
		generalDAO.eliminarObjeto(objeto);
	}

	public List<Institucion> obtenerListaInstituciones() {
		return generalDAO.obtenerListaInstituciones();
	}

	public Object obtenerObjeto(Object clase, Object id) throws DataAccessException{
		return generalDAO.obtenerObjeto(clase, id);
	}

	public Object obtenerObjetoYPadre(Object objeto, Object id) {
		return generalDAO.obtenerObjetoYPadre(objeto, id);
	}

	public List obtenerHijos(Object padre) {
		return generalDAO.obtenerHijos(padre);
	}

	public List obtenerFacultades() {
		return generalDAO.obtenerFacultades();
	}

	public Dependencia obtenerDependenciaPorPaginaWeb(String paginaWeb) {
		return generalDAO.obtenerDependenciaPorPaginaWeb(paginaWeb);
	}

	/**
	 * Obtiene la lista de todos los objetos de la clase "object" ordenados en
	 * orden ascendiente del campo "campoOrden"
	 * 
	 * @param object
	 * @param campoOrden
	 * @return
	 */
	public List obtenerListaObjetosOrdenadosAsc(Object object, String campoOrden) {
		return generalDAO.obtenerListaObjetosOrdenadosAsc(object, campoOrden);
	}

	public <T> List<T> obtenerListaObjetosOrdenadosAscG(Class<T> t, String campoOrden) {
		return generalDAO.obtenerListaObjetosOrdenadosAscG(t, campoOrden);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.unal.hermes.modelo.servicioGeneral.IServicioGeneral#
	 * getReporteEstudianteProyecto(java.lang.Long)
	 */
	public List getReporteEstudianteProyecto(Long modalidad) {
		
		return generalDAO.getReporteEstudianteProyecto(modalidad);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.unal.hermes.modelo.servicioGeneral.IServicioGeneral#getSesion()
	 */
	public Session getSesion() {
		
		return generalDAO.getSesion();
	}

	public List obtenerSedes() {
		return generalDAO.obtenerSedes();
	}

	public Object adjuntarObjetoPropiedad(String nombreObjecto, Object id, String propiedadesAdjuntas) {
		return generalDAO.adjuntarObjetoPropiedad(nombreObjecto, id, propiedadesAdjuntas);
	}

	public Empresa buscarEmpresaXNIT(String nit) {
		return generalDAO.buscarEmpresaXNIT(nit);
	}

	public List obtenerListaObjetosXListaId(Object[] listaId, String objeto) {
		return generalDAO.obtenerListaObjetosXListaId(listaId, objeto);
	}

	public List obtenerListaTipoRubroXPadre(TipoRubro ti) {
		return generalDAO.obtenerListaTipoRubroXPadre(ti.getId());
	}

	public List obtenerListaTipoRubro1Nivel(boolean incluirPadres) {
		return generalDAO.obtenerListaTipoRubro1Nivel(incluirPadres);
	}
	
	public List obtenerListaTipoRubro2022porNivel(int nivel) {
		return generalDAO.obtenerListaTipoRubro2022porNivel(nivel);
	}

	public List obtenerHijosTipoXPadre(Long idTipoPadre) {
		return generalDAO.obtenerHijosTipoXPadre(idTipoPadre);
	}
	
	public SelectItem[] retornaSelectItemArregloDeHijosDeTipos(Long idPadre) {
		List hijos = obtenerHijosTipoXPadre(idPadre);
		int i = 0;

		SelectItem[] itemHijos = new SelectItem[hijos.size()];
		for (Iterator itH = hijos.iterator(); itH.hasNext();) {
			Tipos tipo = (Tipos) itH.next();
			itemHijos[i] = new SelectItem(tipo.getId() + "", tipo.getNombre());
			i++;
		}
		return itemHijos;
	}
	
	public List obtenerHijosTipoXPadreOrdenABC(Long idTipoPadre) {
		return generalDAO.obtenerHijosTipoXPadreOrdenABC(idTipoPadre);
	}
	
	public SelectItem[] retornaSelectItemArregloDeHijosDeTiposOrdenABC(Long idPadre) {
		List hijos = obtenerHijosTipoXPadreOrdenABC(idPadre);
		int i = 0;

		SelectItem[] itemHijos = new SelectItem[hijos.size()];
		for (Iterator itH = hijos.iterator(); itH.hasNext();) {
			Tipos tipo = (Tipos) itH.next();
			itemHijos[i] = new SelectItem(tipo.getId() + "", tipo.getNombre());
			i++;
		}
		return itemHijos;
	}

	public SelectItem[] retornaSelectItemArregloDeHijosDeTiposConClaveLong(Long idPadre) {
		List hijos = obtenerHijosTipoXPadre(idPadre);
		int i = 0;

		SelectItem[] itemHijos = new SelectItem[hijos.size()];
		for (Iterator itH = hijos.iterator(); itH.hasNext();) {
			Tipos tipo = (Tipos) itH.next();
			itemHijos[i] = new SelectItem(tipo.getId(), tipo.getNombre());
			i++;
		}
		return itemHijos;
	}
	
	public SelectItem[] selectItemHijosDeTiposValorObjeto(Long idPadre) {
		List<Tipos> hijos = obtenerHijosTipoXPadre(idPadre);
		int i = 0;
		SelectItem[] itemHijos = new SelectItem[hijos.size()];
		for (Tipos tipo : hijos) {
			itemHijos[i] = new SelectItem(tipo, tipo.getNombre());
			i++;
		}
		return itemHijos;
	}
	
	public SelectItem[] selectItemListaLaboratoriosActivos() {
		List<Laboratorio> laboratorios = obtenerListaLaboratoriosActivos();
		int i = 0;
		SelectItem[] itemLabs = new SelectItem[laboratorios.size()];
		for (Laboratorio lab : laboratorios) {
			itemLabs[i] = new SelectItem(lab.getId(), lab.getId() + " - " + lab.getNombre());
			i++;
		}
		return itemLabs;
	}
	
	public SelectItem[] selectItemHijosDeTiposValorObjetoSeleccione(Long idPadre) {
		List<Tipos> hijos = obtenerHijosTipoXPadre(idPadre);
		int i = 1;
		SelectItem[] itemHijos = new SelectItem[hijos.size() + 1];
		itemHijos[0] = new SelectItem(null, "");
		for (Tipos tipo : hijos) {
			itemHijos[i] = new SelectItem(tipo, tipo.getNombre());
			i++;
		}
		return itemHijos;
	}
	
	public SelectItem[] selectItemHijosDeTiposValorObjetoSeleccioneSinValorVacio(Long idPadre) {
		List<Tipos> hijos = obtenerHijosTipoXPadre(idPadre);
		int i = 0;
		SelectItem[] itemHijos = new SelectItem[hijos.size()];
		for (Tipos tipo : hijos) {
			itemHijos[i] = new SelectItem(tipo, tipo.getNombre());
			i++;
		}
		return itemHijos;
	}
	
	public SelectItem[] selectItemHijosDeTiposValorObjetoSeleccione2(Long idPadre) {
		List<Tipos> hijos = obtenerHijosTipoXPadre(idPadre);
		int i = 1;
		SelectItem[] itemHijos = new SelectItem[hijos.size() + 1];
		 
		Tipos tipoNinguno = obtenerTipoXid(0L);
		itemHijos[0] = new SelectItem(tipoNinguno, tipoNinguno.getNombre());
		for (Tipos tipo : hijos) {
			itemHijos[i] = new SelectItem(tipo, tipo.getNombre());
			i++;
		}
		return itemHijos;
	}
	
	public Float calcularValoresCriticidadXEquipo(LaboratorioDetalleEquipos equipo) {
		Float valorImpacto = 0F;
		Float valorProbabilidad = 0F;
		Float valorRiesgo = 0F;
		
		valorImpacto = (
				(equipo.getCritEquiposImpactoOperaLab().getOrden() * equipo.getLaboratorio().getCritEquiposImpactoOperaLab()/100)
				+ (equipo.getCritEquiposImpactoSegUsuarios().getOrden() * equipo.getLaboratorio().getCritEquiposImpactoSegUsuarios()/100)
				+ (equipo.getCritEquiposImpactoDaniosInfra().getOrden() * equipo.getLaboratorio().getCritEquiposImpactoDaniosInfra()/100)
				+ (equipo.getCritEquiposImpactoDaniosAmbient().getOrden() * equipo.getLaboratorio().getCritEquiposImpactoDaniosAmbient()/100)
				+ (equipo.getCritEquiposImpactoImagenUN().getOrden() * equipo.getLaboratorio().getCritEquiposImpactoImagenUN()/100)
				+ (equipo.getCritEquiposImpactoQuejas().getOrden() * equipo.getLaboratorio().getCritEquiposImpactoQuejas()/100)
				+ (equipo.getCritEquiposImpactoEconomicos().getOrden() * equipo.getLaboratorio().getCritEquiposImpactoEconomicos()/100)
		);
		
		valorProbabilidad = (
				(equipo.getCritEquiposProbRepFalla().getOrden() * equipo.getLaboratorio().getCritEquiposProbRepFalla()/100)
				+ (equipo.getCritEquiposProbTiempoTrabajo().getOrden() * equipo.getLaboratorio().getCritEquiposProbTiempoTrabajo()/100)
				+ (equipo.getCritEquiposProbCondAmbient().getOrden() * equipo.getLaboratorio().getCritEquiposProbCondAmbient()/100)
				+ (equipo.getCritEquiposProbMetrologia().getOrden() * equipo.getLaboratorio().getCritEquiposProbMetrologia()/100)
		);
		
		valorRiesgo = valorImpacto * valorProbabilidad;
		
		equipo.setCritEquiposValorImpacto(valorImpacto);
		equipo.setCritEquiposValorProbabilidad(valorProbabilidad);
		equipo.setCritEquiposValorRiesgo(valorRiesgo);
		
		return valorRiesgo;
	}
	
	public void calcularValoresCriticidadXLab(Long IdLab, List<LaboratorioDetalleEquipos> listaEquipos) {	
		for (LaboratorioDetalleEquipos equipo : listaEquipos) {
			if(equipo.getCritEquiposValorRiesgo() != null){	
				calcularValoresCriticidadXEquipo(equipo);
			}
		}
	}
	
	public Tipos obtenerTipoXid(Long id) {
		return (Tipos) obtenerObjeto(new Tipos(), id);
	}

	public Dependencia cargaDependencia(String nit) {
		return generalDAO.cargaDependencia(nit);
	}

	public List obtenerPalabrasClaveConteniendoCadena(String nombre) {
		return generalDAO.obtenerPalabrasClaveConteniendoCadena(nombre);
	}

	public List obtenerPalabrasClaveEmpezandoCon(String nombre) {
		return generalDAO.obtenerPalabrasClaveEmpezandoCon(nombre);
	}

	public List obtenerLineasEmpezandoCon(String nombre) {
		return generalDAO.obtenerLineasEmpezandoCon(nombre);
	}
	
	public List obtenerInstitucionesQueContienen(String nombreBusqueda) throws DataAccessException {
	    return generalDAO.obtenerInstitucionesQueContienen(nombreBusqueda);
	}

	public List obtenerAreasConocimientoEmpezandoCon(String nombre) {
		return generalDAO.obtenerAreasConocimientoEmpezandoCon(nombre);
	}

	public List obtenerKeyWordEmpezandoCon(String nombre) {
		return generalDAO.obtenerKeyWordEmpezandoCon(nombre);
	}

	public List listaDeObjetosYDiferenteElIdStringA(String clase, String id) {
		return generalDAO.listaDeObjetosYDiferenteElIdStringA(clase, id);
	}

	public List listaDeObjetosYDiferenteElIdStringAMod(String clase, String id, String mod) {
		return generalDAO.listaDeObjetosYDiferenteElIdStringAMod(clase, id, mod);
	}

	public List<Institucion> buscarListaDeInstitucionesPorNombre(String nombre) {
		return generalDAO.buscarListaDeInstitucionesPorNombre(nombre);
	}

	public List buscarDepartamentosFacultad(String idFacultad) {
		return generalDAO.buscarDepartamentosFacultad(idFacultad);
	}

	public List obtenerServicioDe1NivelXRol(Rol r) {
		return generalDAO.obtenerServicioDe1NivelXRol(r);
	}

	public List obtenerServiciosXPadre(Servicio s) {
		return generalDAO.obtenerServiciosXPadre(s);
	}

	public List obtenerDepartamentos() {
		return generalDAO.obtenerDepartamentos();
	}

	public List buscarDepartamentosFacultadPersona(String idPersona) {
		return generalDAO.buscarDepartamentosFacultadPersona(idPersona);
	}

	public List obtenerListaProductoHijo(String idPadre) {
		return generalDAO.obtenerListaProductoHijo(idPadre);
	}

	public List obtenerListaCompromisoHijo(Long idPadre) {
		return generalDAO.obtenerListaCompromisoHijo(idPadre);
	}

	public List obtenerListaRequisitoHijo(Long idPadre) {
		return generalDAO.obtenerListaRequisitoHijo(idPadre);
	}

	public List verificarEvaluadorConvocatoria(String mod) {

		return generalDAO.verificarEvaluadorConvocatoria(mod);
	}

	public Institucion obtenerinstitucionPorNombre(String nombre) {
		return generalDAO.obtenerinstitucionPorNombre(nombre);
	}

	public List buscarListaDeNombresInstitucionesPorNombre(String nombre) {
		return generalDAO.buscarListaDeNombresInstitucionesPorNombre(nombre);
	}

	public List verificarEvaluadorInternoConvocatoria(String mod) {
		return generalDAO.verificarEvaluadorInternoConvocatoria(mod);
	}

	public Persona obtenerEstadoInvestigador(String id) {
		return generalDAO.obtenerEstadoInvestigador(id);
	}

	public void actualizarEstadoEvaluador(Persona person) {
		generalDAO.actualizarEstadoEvaluador(person);
	}

	public String obtenerIdRubro() {
		return generalDAO.obtenerIdRubro();
	}

	public String insertarTipoRubro(String consecutivo, String nombre) {

		String mensajeRubro = "";
		try {
			if (nombre == null || nombre.equals("")) {
				mensajeRubro = "Debe ingresar el tipo de rubro";
				throw new Exception(mensajeRubro);
			}
			if (nombre.length() > 255) {
				mensajeRubro = "El tipo de rubro no puede contener mas de 255 caracteres";
				throw new Exception(mensajeRubro);
			}
			generalDAO.insertarTipoRubro(consecutivo, nombre);
		} catch (Exception ex) {

		}
		return mensajeRubro;
	}
	
	public List obtenerListaObjetosAdministrador(String clase, String id) {
		return generalDAO.obtenerListaObjetosAdministrador(clase, id);
	}

	public List<DocumentoVice> obtenerListaDocumentosVice(String opcion) {
		return generalDAO.obtenerListaDocumentosVice(opcion);
	}

	public void insertarRecursos(Long padre, Long mod, String sede, Long valor, Long apoyo) {
		generalDAO.insertarRecursos(padre, mod, sede, valor, apoyo);
	}
	
	public List seleccionarValores(Long padre, Long mod) {
		return generalDAO.seleccionarValores(padre, mod);
	}

	public void actualizarRecursos(Long padre, Long mod, String sede, Long valor, Long apoyo) {
		generalDAO.actualizarRecursos(padre, mod, sede, valor, apoyo);
	}

	// Aurelio
	public List obtenerObjetoXID(String clase, String id) {
		return generalDAO.obtenerObjetoXID(clase, id);
	}

	public <T> List<T> obtenerObjetoXID(Class<T> clazz, String id) {
		return generalDAO.obtenerObjetoXID(clazz, id);
	}

	// //

	public Date obtenerFechaDB() {
		return generalDAO.obtenerFechaDB();
	}

	public boolean ejecutarSentencia(String sql) {
		return generalDAO.ejecutarSentencia(sql);
	}
	
	public List<ConvocatoriaPadre> obtenerConvocatoriasBuscador(String where, String fromSql, boolean limite) {
		return generalDAO.obtenerConvocatoriasBuscador(where, fromSql, limite);
	}
	
	public List<ConvocatoriaPadre> obtenerConvocatoriasBuscadorPrincipal(String where, String fromSql, boolean limite) {
		return generalDAO.obtenerConvocatoriasBuscadorPrincipal(where, fromSql, limite);
	}
	
	public List<Proyecto> obtenerECPCatalogoBuscador(String where, String fromSql) {
		return generalDAO.obtenerECPCatalogoBuscador( where,fromSql);
	}
	
	public List<LaboratorioDetalleEnsayosServicios> obtenerEnsayosLaboratorioBuscador(String where, String where2, String fromSql, List<Dependencia> sedesSeleccionadas ,List<Dependencia> facultadesSeleccionadas ){
		return generalDAO.obtenerEnsayosLaboratorioBuscador( where,where2,fromSql,sedesSeleccionadas,facultadesSeleccionadas);
	}
	
	public void actualizarNotificacionLaboratoriosProyecto(Long idProyecto, Long idLab, Long valor, Long tipo) throws DataAccessException {
		generalDAO.actualizarNotificacionLaboratoriosProyecto(idProyecto,idLab,valor,tipo);
	}
	
	public Boolean consultaNotificacionEnviadaLabsProyecto(Long idProyecto, Long idLab, Long tipo) {
		return generalDAO.consultaNotificacionEnviadaLabsProyecto(idProyecto,idLab,tipo);
	}
	
	public SelectItem[] selectItemSedes() {
		String hql = "from Sede WHERE id between 2 AND 9 ORDER BY id";
		System.out.println("hql:" + hql);
		List<Sede> listaSedes = obtenerObjetos(Sede.class, hql);
		SelectItem[] sedeSelectItem = new SelectItem[listaSedes.size()];
		// /sedeSelectItem[0] = new SelectItem("", "Seleccione una...");
		for (int i = 0; i < listaSedes.size(); i++) {
			Sede sede = (Sede) listaSedes.get(i);
			sedeSelectItem[i] = new SelectItem(sede.getId(), sede.getNombre());
		}
		return sedeSelectItem;
	}

	public SelectItem[] selectItemFacultades(Long idSede) {
		String hql = "from Dependencia WHERE sede = '" + idSede
				+ "' AND esFacultad = 'Y' ORDER BY nombre";
		System.out.println("hql:" + hql);
		List<Dependencia> listaFacultades = obtenerObjetos(Dependencia.class,
				hql);
		SelectItem[] facultadSelectItem = new SelectItem[0];
		if (!listaFacultades.isEmpty()) {
			facultadSelectItem = new SelectItem[listaFacultades.size()];
			// facultadSelectItem[0] = new SelectItem("", "Seleccione una...");
			for (int i = 0; i < listaFacultades.size(); i++) {
				Dependencia ci = (Dependencia) listaFacultades.get(i);
				facultadSelectItem[i] = new SelectItem(ci.getId(),
						ci.getNombre());
				ci = null;
			}
		}

		return facultadSelectItem;
	}

	public SelectItem[] selectItemDepartamentos(String idFacultad) {
		String hql = "from Dependencia WHERE facultad = '"
				+ idFacultad
				+ "' AND (esDepartamento = 'Y' OR UPPER(nombre) LIKE '%ESCUELA%') ORDER BY nombre";
		System.out.println("hql:" + hql);
		List<Dependencia> listaDepartamentos = obtenerObjetos(
				Dependencia.class, hql);
		SelectItem[] departamentoSelectItem = new SelectItem[0];
		if (!listaDepartamentos.isEmpty()) {
			departamentoSelectItem = new SelectItem[listaDepartamentos.size()];
			// departamentoSelectItem[0] = new SelectItem("",
			// "Seleccione uno...");
			for (int i = 0; i < listaDepartamentos.size(); i++) {
				Dependencia ci = (Dependencia) listaDepartamentos.get(i);
				departamentoSelectItem[i] = new SelectItem(ci.getId(),
						ci.getNombre());
				ci = null;
			}
		}
		return departamentoSelectItem;
	}
	
	public SelectItem[] selectItemDepartamentosBusquedaEquipos(String idFacultad) {
		String hql = "from Dependencia WHERE facultad = '"
				+ idFacultad
				+ "' AND (esDepartamento = 'Y' OR UPPER(nombre) LIKE '%ESCUELA%') ORDER BY nombre";
		System.out.println("hql:" + hql);
		List<Dependencia> listaDepartamentos = obtenerObjetos(
				Dependencia.class, hql);
		SelectItem[] departamentoSelectItem = new SelectItem[0];
		if (!listaDepartamentos.isEmpty()) {
			departamentoSelectItem = new SelectItem[listaDepartamentos.size()+1];
			departamentoSelectItem[0] = new SelectItem("", "Todas");
			// departamentoSelectItem[0] = new SelectItem("",
			// "Seleccione uno...");
			for (int i = 0; i < listaDepartamentos.size(); i++) {
				Dependencia ci = (Dependencia) listaDepartamentos.get(i);
				departamentoSelectItem[i + 1] = new SelectItem(ci.getId(),
						ci.getNombre());
				ci = null;
			}
		}
		return departamentoSelectItem;
	}

	public List<String> listaNombresEmpresasContiene(String nombreEmpresa) {
		return generalDAO.listaNombresEmpresasContiene(nombreEmpresa);
	}
	
	public Boolean esAmbienteProduccion() throws HibernateException, SQLException {
		return generalDAO.esAmbienteProduccion();
	}

	public Empresa buscarEmpresaXNombre(String nombre) {
		return generalDAO.buscarEmpresaXNombre(nombre);
	}
	
	public void descargarDocumentoDisco(String path, Boolean origen){
		generalDAO.descargarDocumentoDisco(path,origen);
	}
	
	public boolean esEstudiante(String tipoInvestigador){
		return generalDAO.esEstudiante(tipoInvestigador);
	}

	public Coleccion obtenerColeccion(Long id) {
		return generalDAO.obtenerColeccion(id);
	}
	
	public Investigador obtenerInvestigadorPorEmail(String correo){
		return generalDAO.obtenerInvestigadorPorEmail(correo);
	}
	
	public List<Coleccion> obtenerColeccionBuscador(String where ) {
		return generalDAO.obtenerColeccionBuscador(where);
	}
	
    public List<Instructivo> obtenerInstructivos(String sqlInstructivos) throws DataAccessException{
    	return generalDAO.obtenerInstructivos(sqlInstructivos);
    }
    
    public List<Pregunta> obtenerPreguntas(String sqlPreguntas) throws DataAccessException{
    	return generalDAO.obtenerPreguntas(sqlPreguntas);
    }
	
	//duplicar pry
	public Long duplicarProyecto(Long idProyecto) throws DataAccessException {
		return generalDAO.duplicarProyecto(idProyecto);
	}
	
	public List obtenerSolicitudesAprobadasUnidadAdministrativa(String dependencias){
		return generalDAO.obtenerSolicitudesAprobadasUnidadAdministrativa(dependencias);
	}
	
	public List obtenerConvocatoriaMovilidades(String convocatoria)	throws DataAccessException{
		return generalDAO.obtenerConvocatoriaMovilidades(convocatoria);
	}
	
	public Long obtenerTotalListaOtrosBoletin()	throws DataAccessException{
		return generalDAO.obtenerTotalListaOtrosBoletin();
	}

	public List<Map> obtenerMapa(String sql) {
		return generalDAO.obtenerMapa(sql);
	}
	
	public List<Map> obtenerMapa(String sql, String from) {
		return generalDAO.obtenerMapa(sql,from);
	}

	public void insertarObjetoConIdLong(Object objeto, Long id) {
		generalDAO.insertarObjetoConIdLong(objeto, id);
	}
	
	public Long consecutivoSecuencia(String secuencia) {
		return generalDAO.consecutivoSecuencia(secuencia);
	}
	
	public List<HistoricoEstadoSolicitud> obtenerHistoricoSolicitud(Solicitud solicitud){
		return generalDAO.obtenerHistoricoSolicitud(solicitud);
	}
	
	public List<Dependencia> obtenerDependenciasSede(String nivelSolicitante, String estado){
	    return generalDAO.obtenerDependenciasSede(nivelSolicitante, estado);
	}
    
    public List<DominioDetalle> obtenerDominioDetalle(String tipoDominio, boolean estado){
        return generalDAO.obtenerDominioDetalle(tipoDominio, estado);
    }
    
    public List<DominioDetalle> obtenerDominioDetalle(String tipoDominio){
        return generalDAO.obtenerDominioDetalle(tipoDominio, true);
    }
    
    public List<DominioDetalle> obtenerDominioDetalleListaUnico(String tipoDominio, String domDetTipo){
        return generalDAO.obtenerDominioDetalleListaUnico(tipoDominio, domDetTipo);
    }
    
    public List<SelectItem> obtenerDominioDetalleSelectItem(String tipoDominio, boolean estado){
    	List<DominioDetalle> listaDom = generalDAO.obtenerDominioDetalle(tipoDominio, estado);
    	List<SelectItem> listaSelectItem = new ArrayList<SelectItem>();
    	
    	for(DominioDetalle dom : listaDom)
    		listaSelectItem.add(new SelectItem(dom.getIdentificador().getTipo(), dom.getDescripcion()));

        return listaSelectItem;
    }
    
    public DominioDetalle obtenerDominioDetalleUnico(String idDominio, String domDetTipo){
        return generalDAO.obtenerDominioDetalleUnico(idDominio, domDetTipo);
    }

	public List<PersonaRol> obtenerPersonaRolXIdPersona(String tipoDoc, String numDoc, String rol) {
		return generalDAO.obtenerPersonaRolXIdPersona(tipoDoc, numDoc, rol);
	}
	
	public List<PersonaRol> obtenerPersonaRolXIdPersonaLaboratorios(String tipoDoc, String numDoc) {
		return generalDAO.obtenerPersonaRolXIdPersonaLaboratorios(tipoDoc, numDoc);
	}
	
//	public List<PersonaRol> obtenerListaPersonaRolXidRol(String idRol, Long idSede) {
//		
//		List<PersonaRol> listaPersonaRolXidRol = generalDAO.obtenerListaPersonaRolXidRol(idRol);
//		
//		for (PersonaRol personaRol : listaPersonaRolXidRol) {
//			servicioPersona.
//		}
//		
//		return generalDAO.obtenerPersonaRolXIdPersonaLaboratorios(tipoDoc, numDoc);
//	}
	
	public List<Reporte> obtenerListaIndicadores(String nivel, Long categoria) {
        return generalDAO.obtenerListaIndicadores(nivel, categoria);
    }
	
	public Reporte obtenerReporte(Long id) {
        return generalDAO.obtenerReporte(id);
    }
	
	public List<Requerimiento> obtenerRequerimientosXIngeniero(String ingeniero) {
        // TODO Auto-generated method stub
        return generalDAO.obtenerRequerimientosXIngeniero(ingeniero);
    }

    public List<Requerimiento> obtenerRequerimientosPorIngeniero(String ccIng) {
        // TODO Auto-generated method stub
        return generalDAO.obtenerRequerimientosPorIngeniero(ccIng);
    }
	
	public void descargarReporteExcelDesdeSql(String sql, String nombreArchivo){
		
		try {
		    Object[] objects = generalDAO.ejecutarQuerySql(sql);
			
			if(objects != null){

                String[] headers = (String[]) objects[0];
                
                List<Object[]> data = (List<Object[]>) objects[1];
                
                if(headers != null && data != null){
    			    
    				XSSFWorkbook wb = ExcelUtils.getExcelFromResulSet(headers, data);
    				//populate
    
    				FacesContext ctx = FacesContext.getCurrentInstance();
    
    				HttpServletResponse response = (HttpServletResponse) ctx
    						.getExternalContext().getResponse();
    				
    				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    				
    				response.setHeader("Content-Disposition",
                            "attachment;filename=\"" + nombreArchivo + ".xlsx\"");
    
    				ServletOutputStream out = response.getOutputStream();
    				try {
    				   wb.write(out);
    				   out.flush();
    				}       
    				catch (IOException ioe) { 
    					ioe.printStackTrace();
    				}
    				out.close();
                }
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
		
	public Persona obtenerCoordinadorLaboratorio(Long idLab) throws DataAccessException {
		return generalDAO.obtenerCoordinadorLaboratorio(idLab);
	}	
	
	public List<Laboratorio> obtenerListaLaboratoriosActivos() throws DataAccessException {
		return generalDAO.obtenerListaLaboratoriosActivos();
	}
		
	public void calcularPorcentajeLab(Laboratorio laboratorioActual){
		generalDAO.calcularPorcentajeLab(laboratorioActual);
	}
	
	public Laboratorio obtenerLaboratorioXID(Long idLab) throws DataAccessException {
		return generalDAO.obtenerLaboratorioXID(idLab);
	}
	
	public Rol obtenerRolPersonaLaboratorioxIDLab(Long idLab, String tipoDocumento, String documento) throws DataAccessException {
		return generalDAO.obtenerRolPersonaLaboratorioxIDLab(idLab, tipoDocumento, documento);
	}
	
	public List<TipoDocumento> obtenerTiposDeDocumento() {
        // TODO Auto-generated method stub
        return generalDAO.obtenerTiposDeDocumento();
    }
	
	public List<TipoFormacion> obtenerTiposDeFormacion(){
		return generalDAO.obtenerTiposDeFormacion();
	}
	
	public List<EstadoCivil> obtenerTiposDeEstadoCivil(){
		return generalDAO.obtenerTiposDeEstadoCivil();
	}
	
	public List<LaboratorioCostosServicio> obtenerAnalisisCostosEnsayosServicios(Long idServicio) {
        // TODO Auto-generated method stub
        return generalDAO.obtenerAnalisisCostosEnsayosServicios(idServicio);
    }
	
	public List<LaboratorioSolicitud> obtenerListaSolicitudesLaboratorioXTIpoSol(Long idLab, Long tipoSol)
			throws DataAccessException {
		return generalDAO.obtenerListaSolicitudesLaboratorioXTIpoSol(idLab,tipoSol);
	}
	
	public List<LaboratorioSolicitud> obtenerListaSolicitudesLaboratorioXPersonaXTIpoSol(IdPersona idPersona, Long tipoSol) throws DataAccessException{
		return generalDAO.obtenerListaSolicitudesLaboratorioXPersonaXTIpoSol(idPersona,tipoSol);
	}
	
	public List obtenerListasMetrologia(String entidad, Long idLab) {
        // TODO Auto-generated method stub
        return generalDAO.obtenerListasMetrologia(entidad, idLab);
    }
	
	public List<LaboratorioAreasSecundariasOCDE> obtenerAreasOCDESecundariasLab(Long idLab) {
		return generalDAO.obtenerAreasOCDESecundariasLab(idLab);
	}
	public List<ArchivoLaboratorio> obtenerArchivosLaboratorioXidSol(Long idSol) throws DataAccessException {
		return generalDAO.obtenerArchivosLaboratorioXidSol(idSol);
	}
	
	public List<ArchivoLaboratorio> obtenerArchivosLaboratorioXidActividad(Long idActividad) throws DataAccessException {
		return generalDAO.obtenerArchivosLaboratorioXidActividad(idActividad);
	}
	
	public List<ArchivoLaboratorio> obtenerArchivosLaboratorioXidReporteDanio(Long idReporte) throws DataAccessException {
		return generalDAO.obtenerArchivosLaboratorioXidReporteDanio(idReporte);
	}
	
	public List<ArchivoLaboratorio> obtenerArchivosInsumoXidInsumoXidTipoArchivo(Long idInsumo, Long tipoArchivo) throws DataAccessException {
		return generalDAO.obtenerArchivosInsumoXidInsumoXidTipoArchivo(idInsumo, tipoArchivo);
	}
	
	public List<Ciudad> obtenerListaCiudades() {
		return generalDAO.obtenerListaCiudades();
	}
	
	public List<Pais> obtenerListaPaisesISO() {
		return generalDAO.obtenerListaPaisesISO();
	}
	
	protected boolean esListaVacia(List lista) {
		return lista == null || (lista != null && lista.isEmpty());
	}
	
	public SelectItem[] obtenerListaPaisesISOSelectItem() {
		List<Pais> lista = obtenerListaPaisesISO();
		SelectItem[] itemPais = new SelectItem[lista.size()];
		if (!esListaVacia(lista)) {
			itemPais = new SelectItem[lista.size()];
			for (int i = 0; i < lista.size(); i++) {
				Pais pais = lista.get(i);
				itemPais[i] = new SelectItem(pais.getId(), pais.getNombre());
			}
		}
		return itemPais;
	}
	
	public SelectItem[] obtenerListaCiudadesSelectItem() {
		List ciudades = obtenerListaCiudades();
		int i = 0;

		SelectItem[] itemCiudades = new SelectItem[ciudades.size()];
		for (Iterator itC = ciudades.iterator(); itC.hasNext();) {
			Tipos tipo = (Tipos) itC.next();
			itemCiudades[i] = new SelectItem(tipo.getId() + "", tipo.getNombre());
			i++;
		}
		return itemCiudades; 
	}
    
	public List<TipoDocumento> obtenerTiposDeDocumentoMetrologia() {
        // TODO Auto-generated method stub
        return generalDAO.obtenerTiposDeDocumentoMetrologia();
    }
	
	public List<Aval> consultaAvalesPendientesRectoria() throws DataAccessException {
		return this.generalDAO.consultaAvalesPendientesRectoria();
	}
	
	public List<MovilidadAlertaAutomatica> consultaMovilidadesPendienteInforme() throws DataAccessException {
		return this.generalDAO.consultaMovilidadesPendienteInforme();
	}

	public ParametroMaestro obtenerParametroPorNombre(String parametro) {
		return this.generalDAO.obtenerParametroPorNombre(parametro);
	}

	public Long consultaUltimoIdPorDependenciaTipoCarta(String sede, Date fecha, String tipoCarta) throws SQLException {
		return this.generalDAO.consultaUltimoIdPorDependenciaTipoCarta(sede, fecha, tipoCarta);
	}
	
	public List<Integer> obtenerIdsActividadesRegistradasPorSemilleroYPorcentaje(String semillero, String porcentaje) {
		return this.generalDAO.obtenerIdsActividadesRegistradasPorSemilleroYPorcentaje(semillero, porcentaje);
	}

}
