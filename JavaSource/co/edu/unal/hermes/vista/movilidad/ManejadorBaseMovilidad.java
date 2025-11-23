package co.edu.unal.hermes.vista.movilidad;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.ArchivoMovilidad;
import co.edu.unal.hermes.modelo.ArchivoMovilidadDE;
import co.edu.unal.hermes.modelo.ArchivoMovilidadEP;
import co.edu.unal.hermes.modelo.ArchivoMovilidadVE;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaPadreParametrizacion;
import co.edu.unal.hermes.modelo.ConvocatoriaParametrizacion;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.Estudiante;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Movilidad;
import co.edu.unal.hermes.modelo.MovilidadArchivo;
import co.edu.unal.hermes.modelo.MovilidadDocentesArtes;
import co.edu.unal.hermes.modelo.MovilidadDocentesExterior;
import co.edu.unal.hermes.modelo.MovilidadEstudiantesArtes;
import co.edu.unal.hermes.modelo.MovilidadEstudiantesPosgrado;
import co.edu.unal.hermes.modelo.MovilidadVisitanteExterior;
import co.edu.unal.hermes.modelo.MovilidadVisitantesArtes;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.TipoArchivoMovilidad;
import co.edu.unal.hermes.modelo.TipoMovilidad;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * The Class ManejadorBaseMovilidad.
 */
public class ManejadorBaseMovilidad extends ManejadorBase {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -4370017013371509388L;

    /** The Constant MOV3_IN. */
    public static final String MOV3_IN = "MOV3_IN";

    /** The Constant CF_MOV6. */
    public static final String CF_MOV6 = "CF_MOV6";

    /** The Constant CF_MOV1. */
    public static final String CF_MOV1 = "CF_MOV1";

    /** The Constant D1. */
    public static final String D1 = "D1";

    /** The Constant B1. */
    public static final String B1 = "B1";

    /** The Constant CF_MOV5. */
    public static final String CF_MOV5 = "CF_MOV5";

    /** The Constant CF_MOV2. */
    public static final String CF_MOV2 = "CF_MOV2";

    /** The Constant CF_MOV3. */
    public static final String CF_MOV3 = "CF_MOV3";

    /** The Constant CF_MOV4. */
    public static final String CF_MOV4 = "CF_MOV4";

    /** The Constant HER_ARCHIVO_MOVILIDAD. */
    public static final String HER_ARCHIVO_MOVILIDAD = "HER_ARCHIVO_MOVILIDAD";

    /** The Constant ES_CONSULTA_FACULTAD. */
    public static final String ES_CONSULTA_FACULTAD = "esConsultaFacultad";

    /** The plant envio seg facultad. */
    protected final int PLANT_ENVIO_SEG_FACULTAD = 229;

    /** The plant envio seg sede presencia nacional. */
    protected final int PLANT_ENVIO_SEG_SEDE_PRESENCIA_NACIONAL = 36;

    /** The plant envio seg docente. */
    protected final int PLANT_ENVIO_SEG_DOCENTE = 230;

    /** The plant envio revi facultad. */
    protected final int PLANT_ENVIO_REVI_FACULTAD = 231;

    /** The plant envio aprobacion sede. */
    protected final int PLANT_ENVIO_APROBACION_SEDE = 275;

    /** The plant envio devo facultad. */
    protected final int PLANT_ENVIO_DEVO_FACULTAD = 232;
    
    protected List<MovilidadDocentesExterior> listaMovilidadesEventoFiltered;
    protected List<MovilidadVisitanteExterior> listaMovilidadesVisitanteFiltered;
    protected List<MovilidadEstudiantesPosgrado> listaMovilidadesEstudiantePosgradoFiltered;
    protected List<MovilidadEstudiantesPosgrado> listaMovilidadesEstudiantePosgradoEventosFiltered;
    protected List<MovilidadDocentesArtes> listaMovilidadesDocentesArtesFiltered;
    protected List<MovilidadEstudiantesArtes> listaMovilidadesEstudiantesArtesFiltered;
    protected List<MovilidadVisitantesArtes> listaMovilidadesVisitantesArtesFiltered;
    
    protected List<ConvocatoriaPadreParametrizacion> listaParametrosConvPadre;
    protected List<ConvocatoriaParametrizacion> listaParametrosConv;
    Convocatoria convocatoriaActual;
    
    public Integer obtenerNumMovilidadesXModadlidadXDocente(Persona persona, Convocatoria modalidad, Sede sede, String anioFechaInicio) {
    	Integer cantidad = 0;
    	
    	List<MovilidadVisitanteExterior> listaMovilidadesVisitante = cargarMovilidadesXDocenteXModXSedeXAnio(MovilidadVisitanteExterior.class, persona, modalidad, sede, anioFechaInicio);
    	List<MovilidadDocentesExterior> listaMovilidadesEvento = cargarMovilidadesXDocenteXModXSedeXAnio(MovilidadDocentesExterior.class, persona, modalidad, sede, anioFechaInicio);
    	List<MovilidadEstudiantesPosgrado> listaMovilidadesEstudiantePosgrado = cargarMovilidadesXDocenteXModXSedeXAnio(MovilidadEstudiantesPosgrado.class, persona, modalidad, sede, anioFechaInicio);
    	
    	cantidad = listaMovilidadesVisitante.size() + listaMovilidadesEvento.size() + listaMovilidadesEstudiantePosgrado.size();
//    	System.out.println("Cantidad: " + cantidad);
    	return cantidad;
    }
    
    public <T> List<T> cargarMovilidadesXDocenteXModXSedeXAnio(Class<T> clase, Persona persona, Convocatoria modalidad, Sede sede, String anioFechaInicio) {
    	List<T> lista;
    	String hqlWhereSede = !esNulo(sede) ? "AND mov.dependencia.sede.id = "  + sede.getId() : "";
    	String hqlWhereAño = !esNulo(anioFechaInicio) ? "AND EXTRACT(YEAR FROM MOV_FEC_INI_EV) ='" + anioFechaInicio + "' " : "";
		String hql = "SELECT #id mov.id, #estado mov.estado, #fechainicial mov.fechainicial, #convocatoria mov.convocatoria, #fechasolicitud mov.fechasolicitud ";
		hql += " FROM " + clase.getSimpleName() + " mov "
				+ "WHERE " 
				+ "mov.personaInv.id.documento = '" + persona.getId().getDocumento() + "' "
				+ "AND mov.personaInv.id.tipoDocumento = '" + persona.getId().getTipoDocumento() + "' "
				+ "AND mov.convocatoria.id = " + modalidad.getId() + " "
				+ "AND (mov.estado = 'P' AND mov.aprobacion = 'SI') "
				+ "AND (mov.estadoSeguimiento IS NULL OR (mov.estadoSeguimiento IS NOT NULL AND mov.realizacionMovilidad = 'SI' AND (mov.estadoSeguimiento = 'F' OR mov.estadoRevisionSeguimiento = 'L'))) "
				;
		hql += hqlWhereSede;
		hql += hqlWhereAño;
//		System.out.println("hql: " + hql);
		lista = servicioGeneral.obtenerObjetosLimitado(clase, hql);
		return lista;
	}
    
    public <T> List<T> cargarMovilidadesXDocenteXTipoPonencia(Class<T> clase, Persona persona, Convocatoria modalidad, String idTipoPonencia) {
    	List<T> lista;
    	String hql = "SELECT mov ";
		hql += "FROM " + clase.getSimpleName() + " mov, MovilidadPonencia mp "
				+ "WHERE " 
				+ "mov.personaInv.id.documento = '" + persona.getId().getDocumento() + "' "
				+ "AND mov.personaInv.id.tipoDocumento = '" + persona.getId().getTipoDocumento() + "' "
				+ "AND mov.convocatoria.id = " + modalidad.getId() + " "
				+ "AND (mov.estado = 'P' AND mov.aprobacion = 'SI') "
				+ "AND (mov.estadoSeguimiento IS NULL OR (mov.estadoSeguimiento IS NOT NULL AND mov.realizacionMovilidad = 'SI' AND (mov.estadoSeguimiento = 'F' OR mov.estadoRevisionSeguimiento = 'L'))) "
				+ "AND mov.id = mp.movilidad.id "
				+ "AND mp.ponencia.id = " + Long.parseLong(idTipoPonencia)
				;
//		System.out.println("hql: " + hql);
		lista = servicioGeneral.obtenerObjetos(clase, hql);
		return lista;
	}
    
    public List<MovilidadEstudiantesPosgrado> cargarMovilidadesXEstudiante(Estudiante estudiante, Convocatoria modalidad, String anioFechaInicio) {
    	List<MovilidadEstudiantesPosgrado> lista;
    	
    	String hqlWhereModalidad = !esNulo(modalidad) ? "AND mov.convocatoria.id = " + modalidad.getId() + " " : "";
    	String hqlWhereAño = !esNulo(anioFechaInicio) ? "AND EXTRACT(YEAR FROM MOV_FEC_INI_EV) ='" + anioFechaInicio + "' " : "";
		
    	String hql = "SELECT mov.id AS id, mov.estado AS estado, mov.fechainicial AS fechainicial, mov.convocatoria AS convocatoria, mov.fechasolicitud AS fechasolicitud ";
		hql += "FROM MovilidadEstudiantesPosgrado mov "
				+ "WHERE " 
				+ "mov.estudianteInv.id.documento = '" + estudiante.getId().getDocumento() + "' "
				+ "AND mov.estudianteInv.id.tipoDocumento = '" + estudiante.getId().getTipoDocumento() + "' "
				+ "AND (mov.estado = 'P' AND mov.aprobacion = 'SI') "
				+ "AND (mov.estadoSeguimiento IS NULL OR (mov.estadoSeguimiento IS NOT NULL AND mov.realizacionMovilidad = 'SI' AND (mov.estadoSeguimiento = 'F' OR mov.estadoRevisionSeguimiento = 'L'))) "
				;
		hql += hqlWhereModalidad;
		hql += hqlWhereAño;
//		System.out.println("hql: " + hql);
		lista = servicioGeneral.obtenerObjetos(MovilidadEstudiantesPosgrado.class, hql);
		return lista;
	}
    
    public String obtenerAño(Date fecha) {
    	SimpleDateFormat spy = new SimpleDateFormat("yyyy");
    	return spy.format(fecha);
    }

    /**
     * Imprimir movilidad.
     *
     * @param id
     *            the id
     * @param tipoMovilidad
     *            the tipo movilidad
     */
    // Metodos impresión de movilidades
    private void imprimirMovilidad(Long id, String tipoMovilidad) {
        ReporteBirt r = new ReporteBirt();
        r.adicionarParametro("id", Long.toString(id));
        r.setNombreReporte("/movilidad/" + tipoMovilidad);
        r.setFormato(ReporteBirt.FORMATO_PDF);
        sesion.setAttribute("reporte", r);
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            context.getExternalContext().dispatch("/ReporteEngineServlet");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            context.responseComplete();
        }
    }
    
    private void imprimirMovilidad(Long id, String tipoMovilidad, String tipo) {
        ReporteBirt r = new ReporteBirt();
        r.adicionarParametro("id", Long.toString(id));
        
        if(tipo==null){
        	tipo="A";
        }
        r.adicionarParametro("tipo", tipo.toString());
        r.setNombreReporte("/movilidad/" + tipoMovilidad);
        r.setFormato(ReporteBirt.FORMATO_PDF);
        sesion.setAttribute("reporte", r);
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            context.getExternalContext().dispatch("/ReporteEngineServlet");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            context.responseComplete();
        }
    }

    /**
     * Imprimir movilidad posgrado.
     *
     * @param movilidadEstudiantesPosgrado
     *            the movilidad estudiantes posgrado
     */
    public void imprimirMovilidadEstudiantesPosgradoGenerico(
            MovilidadEstudiantesPosgrado movilidadEstudiantesPosgrado) {
        String tipoModalidad;
        Long id = movilidadEstudiantesPosgrado.getId();
        String tipoMovilidad = "";
        if (movilidadEstudiantesPosgrado.getTipoMovilidad() != null) {
            tipoMovilidad = movilidadEstudiantesPosgrado.getTipoMovilidad().getId();
        } else {
            tipoMovilidad = movilidadEstudiantesPosgrado.getTipoMovilidadCadena();
        }
       
        tipoModalidad = "DApoyoEstudiantesPosgrado";
        

        imprimirMovilidad(id, tipoModalidad, tipoMovilidad);
    }
    
   

    /**
     * Imprimir movilidad visitante.
     *
     * @param movilidadVisitante
     *            the movilidad visitante
     */
    public void imprimirMovilidadVisitanteGenerico(MovilidadVisitanteExterior movilidadVisitante) {
        String nombreReporte = null;
        String tipoMovilidad = "";
        if (movilidadVisitante.getTipoMovilidad() != null) {
            tipoMovilidad = movilidadVisitante.getTipoMovilidad().getId();
        } else {
            tipoMovilidad = movilidadVisitante.getTipoMovilidadCadena();
        }
       
            nombreReporte = "AMovilidadVisitantesExt";
        
        Long id = movilidadVisitante.getId();
        imprimirMovilidad(id, nombreReporte, tipoMovilidad);
    }

    /**
     * Imprimir movilidad evento.
     *
     * @param movilidadDocentesExterior
     *            the movilidad docentes exterior
     */
    public void imprimirMovilidadEventoGenerico(MovilidadDocentesExterior movilidadDocentesExterior) {
        String nombreReporte;
        String tipoMovilidad;
        if (movilidadDocentesExterior.getTipoMovilidad() != null) {
            tipoMovilidad = movilidadDocentesExterior.getTipoMovilidad().getId();
        } else {
            tipoMovilidad = movilidadDocentesExterior.getTipoMovilidadCadena();
        }
        
            nombreReporte = "BMovilidadDocentesEvento";
        
        Long id = movilidadDocentesExterior.getId();
        imprimirMovilidad(id, nombreReporte, tipoMovilidad);
    }

    /**
     * Imprimir movilidad docente artes.
     *
     * @param movilidadDocentesArtes
     *            the movilidad docentes artes
     */
    public void imprimirMovilidadDocenteArtesGenerico(MovilidadDocentesArtes movilidadDocentesArtes) {
        Long id = movilidadDocentesArtes.getId();
        imprimirMovilidad(id, "ReporteMovilidadDocArt");
    }

    /**
     * Imprimir movilidad estudiantes artes.
     *
     * @param movilidadEstudiantesArtes
     *            the movilidad estudiantes artes
     */
    public void imprimirMovilidadEstudiantesArtesGenerico(MovilidadEstudiantesArtes movilidadEstudiantesArtes) {
        Long id = movilidadEstudiantesArtes.getId();
        imprimirMovilidad(id, "ReporteMovilidadEstArt");
    }

    /**
     * Imprimir movilidad visitantes artes.
     *
     * @param movilidadVisitantesArtes
     *            the movilidad visitantes artes
     */
    public void imprimirMovilidadVisitantesArtesGenerico(MovilidadVisitantesArtes movilidadVisitantesArtes) {
        Long id = movilidadVisitantesArtes.getId();
        imprimirMovilidad(id, "ReporteMovilidadVisArt");
    }

    /**
     * Descargar archivo movilidad ve generico.
     *
     * @param idArchivo
     *            the id archivo
     */
    // Descargar archivos movilidad
    public void descargarArchivoMovilidadVEGenerico(Long idArchivo) {
        List<ArchivoMovilidadVE> archivos = servicioGeneral.obtenerObjetoXID(ArchivoMovilidadVE.class,idArchivo.toString());
        ArchivoMovilidadVE archivo = archivos.get(0);
        descargarArchivoMovilidadVEGenerico(archivo);
    }

    /**
     * Descargar archivo movilidad ve generico.
     *
     * @param archivoMovilidadVE
     *            the archivo movilidad ve
     */
    public void descargarArchivoMovilidadVEGenerico(ArchivoMovilidadVE archivoMovilidadVE) {
        descargarArchivoBytes(
        		archivoMovilidadVE.getId().toString(), 
        		archivoMovilidadVE.getBytes(),
                archivoMovilidadVE.getNombre(), 
                HER_ARCHIVO_MOVILIDAD + "_VE");
    }

    /**
     * Descargar archivo movilidad de generico.
     *
     * @param idArchivo
     *            the id archivo
     */
    public void descargarArchivoMovilidadDEGenerico(Long idArchivo) {
        List<ArchivoMovilidadDE> archivos = servicioGeneral.obtenerObjetoXID(ArchivoMovilidadDE.class,
                idArchivo.toString());
        ArchivoMovilidadDE archivo = archivos.get(0);
        descargarArchivoMovilidadDEGenerico(archivo);
    }

    /**
     * Descargar archivo movilidad de generico.
     *
     * @param archivoMovilidadDE
     *            the archivo movilidad de
     */
    public void descargarArchivoMovilidadDEGenerico(ArchivoMovilidadDE archivoMovilidadDE) {
        descargarArchivoBytes(archivoMovilidadDE.getId().toString(), archivoMovilidadDE.getBytes(),
                archivoMovilidadDE.getNombre(), HER_ARCHIVO_MOVILIDAD + "_DE");
    }

    /**
     * Descargar archivo movilidad ep generico.
     *
     * @param idArchivo
     *            the id archivo
     */
    public void descargarArchivoMovilidadEPGenerico(Long idArchivo) {
        List<ArchivoMovilidadEP> archivos = servicioGeneral.obtenerObjetoXID(ArchivoMovilidadEP.class,
                idArchivo.toString());
        ArchivoMovilidadEP archivo = archivos.get(0);
        descargarArchivoMovilidadEPGenerico(archivo);
    }

    /**
     * Descargar archivo movilidad ep generico.
     *
     * @param archivoMovilidadEP
     *            the archivo movilidad ep
     */
    public void descargarArchivoMovilidadEPGenerico(ArchivoMovilidadEP archivoMovilidadEP) {
        descargarArchivoBytes(archivoMovilidadEP.getId().toString(), archivoMovilidadEP.getBytes(),
                archivoMovilidadEP.getNombre(), HER_ARCHIVO_MOVILIDAD + "_EP");
    }

    /**
     * Descargar archivo movilidad generico.
     *
     * @param idArchivo
     *            the id archivo
     */
    public void descargarArchivoMovilidadGenerico(Long idArchivo) {
        List<ArchivoMovilidad> archivos = servicioGeneral.obtenerObjetoXID(ArchivoMovilidad.class,
                idArchivo.toString());
        ArchivoMovilidad archivo = archivos.get(0);
        descargarArchivoMovilidadGenerico(archivo);
    }

    /**
     * Descargar archivo movilidad generico.
     *
     * @param archivoMovilidad
     *            the archivo movilidad
     */
    public void descargarArchivoMovilidadGenerico(ArchivoMovilidad archivoMovilidad) {

        descargarArchivoBytes(archivoMovilidad.getId().toString(), archivoMovilidad.getBytes(),
                archivoMovilidad.getNombre(), HER_ARCHIVO_MOVILIDAD);

    }

    /**
     * Insertar archivo movilidad seg.
     *
     * @param movilidadId
     *            the movilidad id
     * @param archivo
     *            the archivo
     * @param tipoDocumentoSel
     *            the tipo documento sel
     * @param tipoSegMov
     *            the tipo seg mov
     * @return the archivo movilidad
     */
    public ArchivoMovilidad insertarArchivoMovilidadSeg(long movilidadId, UploadedFile archivo, String tipoDocumentoSel,
            String tipoSegMov) {

        try {
            if (archivo.getContents() != null) {

                int i = archivo.getFileName().lastIndexOf("\\");
                ArchivoMovilidad archivoMovilidadSeg = new ArchivoMovilidad();
                archivoMovilidadSeg.setNombre(archivo.getFileName().substring(i + 1));
                archivoMovilidadSeg.setFecha(new Date());

                TipoMovilidad tipoMovilidad = new TipoMovilidad();
                tipoMovilidad.setId(tipoSegMov);
                archivoMovilidadSeg.setTipoMovilidad(tipoMovilidad);

                List<TipoArchivoMovilidad> listaTipoArchivo = servicioGeneral.obtenerObjetos(TipoArchivoMovilidad.class,
                        "from TipoArchivoMovilidad where id ='" + tipoDocumentoSel + "'");
                TipoArchivoMovilidad tipoArchivo = (TipoArchivoMovilidad) listaTipoArchivo.get(0);
                archivoMovilidadSeg.setTipoArchivo(tipoArchivo);

                archivoMovilidadSeg.setMovilidad(Long.toString(movilidadId));

                servicioGeneral.guardarObjeto(archivoMovilidadSeg);
                if (archivoMovilidadSeg.getId() != null) {
                    cargarArchivoDisco(archivo, HER_ARCHIVO_MOVILIDAD, archivoMovilidadSeg.getId().toString());
                }
                return archivoMovilidadSeg;
            }
        } catch (Exception x) {
            x.printStackTrace();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, x.getClass().getName(),
                    x.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        return null;
    }

    /**
     * Descargar archivo movilidad seg generico.
     *
     * @param idArchivo
     *            the id archivo
     */
    public void descargarArchivoMovilidadSegGenerico(Long idArchivo) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        List<ArchivoMovilidad> archivos = servicioGeneral.obtenerObjetoXID(ArchivoMovilidad.class,
                idArchivo.toString());

        ArchivoMovilidad archivo = archivos.get(0);

        try {

            if (archivo != null && archivo.getArchivo() != null && archivo.getBytes().length > 1) {
                if (!ctx.getResponseComplete()) {
                    HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
                    response.setContentType("text/plain");
                    response.setHeader("Content-Disposition", "attachment;filename=\"" + archivo.getNombre() + "\"");
                    ServletOutputStream out = response.getOutputStream();
                    out.write(archivo.getBytes());
                    out.flush();
                    ctx.responseComplete();
                }
            } else {
                if (idArchivo > 44631) {
                    String ext = obtenerExtensionArchivo(archivo.getNombre());
                    descargarArchivoGenerico(HER_ARCHIVO_MOVILIDAD, archivo.getId().toString(), archivo.getId() + ext);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Insertar archivo movilidad ve generico.
     *
     * @param movilidadId
     *            the movilidad id
     * @param archivo
     *            the archivo
     * @param tipoDocumentoSel
     *            the tipo documento sel
     * @return the archivo movilidad ve
     */
    // Insertar archivos movilidades
    public ArchivoMovilidadVE insertarArchivoMovilidadVEGenerico(long movilidadId, UploadedFile archivo,
            String tipoDocumentoSel) {
        try {
            if (archivo.getContents() != null) {
                int i = archivo.getFileName().lastIndexOf("\\");
                ArchivoMovilidadVE archivoMovilidadVE = new ArchivoMovilidadVE();
                archivoMovilidadVE.setNombre(archivo.getFileName().substring(i + 1));
                archivoMovilidadVE.setFecha(new Date());
                archivoMovilidadVE.setPersona(personaActual);
                
                TipoMovilidad tipoMovilidad = new TipoMovilidad();
                tipoMovilidad.setId("A1");
                archivoMovilidadVE.setTipoMovilidad(tipoMovilidad);
                List<TipoArchivoMovilidad> listaTipoArchivo = servicioGeneral.obtenerObjetos(TipoArchivoMovilidad.class,
                        "from TipoArchivoMovilidad where id ='" + tipoDocumentoSel + "'");
                TipoArchivoMovilidad tipoArchivo = (TipoArchivoMovilidad) listaTipoArchivo.get(0);
                archivoMovilidadVE.setTipoArchivo(tipoArchivo);
                MovilidadVisitanteExterior mve = new MovilidadVisitanteExterior();
                mve.setId(movilidadId);
                archivoMovilidadVE.setMovilidad(mve);
                servicioGeneral.guardarObjeto(archivoMovilidadVE);
                if (archivoMovilidadVE.getId() != null) {
                    cargarArchivoDisco(archivo, HER_ARCHIVO_MOVILIDAD + "_VE", archivoMovilidadVE.getId().toString());
                }
                return archivoMovilidadVE;
            }
        } catch (Exception x) {
            x.printStackTrace();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, x.getClass().getName(),
                    x.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        return null;
    }

    /**
     * Insertar archivo movilidad de generico.
     *
     * @param movilidadId
     *            the movilidad id
     * @param archivo
     *            the archivo
     * @param tipoDocumentoSel
     *            the tipo documento sel
     * @return the archivo movilidad de
     */
    public ArchivoMovilidadDE insertarArchivoMovilidadDEGenerico(long movilidadId, UploadedFile archivo,
            String tipoDocumentoSel) {

        try {
            if (archivo.getContents() != null) {

                int i = archivo.getFileName().lastIndexOf("\\");
                ArchivoMovilidadDE archivoMovilidadDE = new ArchivoMovilidadDE();
                archivoMovilidadDE.setNombre(archivo.getFileName().substring(i + 1));
                archivoMovilidadDE.setFecha(new Date());
                archivoMovilidadDE.setPersona(personaActual);

                TipoMovilidad tipoMovilidad = new TipoMovilidad();
                tipoMovilidad.setId("B1");
                archivoMovilidadDE.setTipoMovilidad(tipoMovilidad);
                List<TipoArchivoMovilidad> listaTipoArchivo = servicioGeneral.obtenerObjetos(TipoArchivoMovilidad.class,
                        "from TipoArchivoMovilidad where id ='" + tipoDocumentoSel + "'");
                TipoArchivoMovilidad tipoArchivo = (TipoArchivoMovilidad) listaTipoArchivo.get(0);
                archivoMovilidadDE.setTipoArchivo(tipoArchivo);
                MovilidadDocentesExterior mde = new MovilidadDocentesExterior();
                mde.setId(movilidadId);
                archivoMovilidadDE.setMovilidad(mde);

                servicioGeneral.guardarObjeto(archivoMovilidadDE);
                if (archivoMovilidadDE.getId() != null) {
                    cargarArchivoDisco(archivo, HER_ARCHIVO_MOVILIDAD + "_DE", archivoMovilidadDE.getId().toString());
                }
                return archivoMovilidadDE;
            }
        } catch (Exception x) {
            x.printStackTrace();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, x.getClass().getName(),
                    x.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        return null;
    }

    /**
     * Insertar archivo movilidad generico.
     *
     * @param movilidadId
     *            the movilidad id
     * @param archivo
     *            the archivo
     * @param tipoDocumentoSel
     *            the tipo documento sel
     * @param tipoMovilidadId
     *            the tipo movilidad id
     * @return the archivo movilidad
     */
    public ArchivoMovilidad insertarArchivoMovilidadGenerico(long movilidadId, UploadedFile archivo,
            String tipoDocumentoSel, String tipoMovilidadId) {

        try {
            if (archivo.getContents() != null) {

                int i = archivo.getFileName().lastIndexOf("\\");
                ArchivoMovilidad archivoMovilidadDA = new ArchivoMovilidad();
                archivoMovilidadDA.setNombre(archivo.getFileName().substring(i + 1));
                archivoMovilidadDA.setFecha(new Date());
                archivoMovilidadDA.setPersona(personaActual);

                TipoMovilidad tipoMovilidad = new TipoMovilidad();
                tipoMovilidad.setId(tipoMovilidadId);
                archivoMovilidadDA.setTipoMovilidad(tipoMovilidad);
                List<TipoArchivoMovilidad> listaTipoArchivo = servicioGeneral.obtenerObjetos(TipoArchivoMovilidad.class,
                        "from TipoArchivoMovilidad where id ='" + tipoDocumentoSel + "'");

                TipoArchivoMovilidad tipoArchivo = (TipoArchivoMovilidad) listaTipoArchivo.get(0);
                archivoMovilidadDA.setTipoArchivo(tipoArchivo);
                MovilidadDocentesArtes mda = new MovilidadDocentesArtes();
                mda.setId(movilidadId);
                archivoMovilidadDA.setMovilidad(mda.getId().toString());

                servicioGeneral.guardarObjeto(archivoMovilidadDA);
                if (archivoMovilidadDA.getId() != null) {
                    cargarArchivoDisco(archivo, HER_ARCHIVO_MOVILIDAD, archivoMovilidadDA.getId().toString());
                }
                return archivoMovilidadDA;
            }
        } catch (Exception x) {
            x.printStackTrace();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, x.getClass().getName(),
                    x.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        return null;
    }

    /**
     * Insertar archivo movilidad da generico.
     *
     * @param movilidadId
     *            the movilidad id
     * @param archivo
     *            the archivo
     * @param tipoDocumentoSel
     *            the tipo documento sel
     * @return the archivo movilidad
     */
    public ArchivoMovilidad insertarArchivoMovilidadDAGenerico(long movilidadId, UploadedFile archivo,
            String tipoDocumentoSel) {

        try {
            if (archivo.getContents() != null) {

                int i = archivo.getFileName().lastIndexOf("\\");
                ArchivoMovilidad archivoMovilidadDA = new ArchivoMovilidad();
                archivoMovilidadDA.setNombre(archivo.getFileName().substring(i + 1));
                archivoMovilidadDA.setFecha(new Date());
                archivoMovilidadDA.setPersona(personaActual);

                TipoMovilidad tipoMovilidad = new TipoMovilidad();
                tipoMovilidad.setId("C1");
                archivoMovilidadDA.setTipoMovilidad(tipoMovilidad);
                List<TipoArchivoMovilidad> listaTipoArchivo = servicioGeneral.obtenerObjetos(TipoArchivoMovilidad.class,
                        "from TipoArchivoMovilidad where id ='" + tipoDocumentoSel + "'");

                TipoArchivoMovilidad tipoArchivo = (TipoArchivoMovilidad) listaTipoArchivo.get(0);
                archivoMovilidadDA.setTipoArchivo(tipoArchivo);
                MovilidadDocentesArtes mda = new MovilidadDocentesArtes();
                mda.setId(movilidadId);
                archivoMovilidadDA.setMovilidad(mda.getId().toString());

                servicioGeneral.guardarObjeto(archivoMovilidadDA);
                if (archivoMovilidadDA.getId() != null) {
                    cargarArchivoDisco(archivo, HER_ARCHIVO_MOVILIDAD, archivoMovilidadDA.getId().toString());
                }
                return archivoMovilidadDA;
            }
        } catch (Exception x) {
            x.printStackTrace();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, x.getClass().getName(),
                    x.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        return null;
    }

    /**
     * Insertar archivo movilidad ea generico.
     *
     * @param movilidadId
     *            the movilidad id
     * @param archivo
     *            the archivo
     * @param tipoDocumentoSel
     *            the tipo documento sel
     * @return the archivo movilidad
     */
    public ArchivoMovilidad insertarArchivoMovilidadEAGenerico(long movilidadId, UploadedFile archivo,
            String tipoDocumentoSel) {

        try {
            if (archivo.getContents() != null) {

                int i = archivo.getFileName().lastIndexOf("\\");
                ArchivoMovilidad archivoMovilidadEA = new ArchivoMovilidad();
                archivoMovilidadEA.setNombre(archivo.getFileName().substring(i + 1));
                archivoMovilidadEA.setFecha(new Date());
                archivoMovilidadEA.setPersona(personaActual);

                TipoMovilidad tipoMovilidad = new TipoMovilidad();
                tipoMovilidad.setId("C2");
                archivoMovilidadEA.setTipoMovilidad(tipoMovilidad);
                List<TipoArchivoMovilidad> listaTipoArchivo = servicioGeneral.obtenerObjetos(TipoArchivoMovilidad.class,
                        "from TipoArchivoMovilidad where id ='" + tipoDocumentoSel + "'");

                TipoArchivoMovilidad tipoArchivo = (TipoArchivoMovilidad) listaTipoArchivo.get(0);
                archivoMovilidadEA.setTipoArchivo(tipoArchivo);
                MovilidadEstudiantesArtes mea = new MovilidadEstudiantesArtes();
                mea.setId(movilidadId);
                archivoMovilidadEA.setMovilidad(mea.getId().toString());

                servicioGeneral.guardarObjeto(archivoMovilidadEA);
                if (archivoMovilidadEA.getId() != null) {
                    cargarArchivoDisco(archivo, HER_ARCHIVO_MOVILIDAD, archivoMovilidadEA.getId().toString());
                }
                return archivoMovilidadEA;
            }
        } catch (Exception x) {
            x.printStackTrace();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, x.getClass().getName(),
                    x.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        return null;
    }

    /**
     * Insertar archivo movilidad ep generico.
     *
     * @param movilidadId
     *            the movilidad id
     * @param archivo
     *            the archivo
     * @param tipoDocumentoSel
     *            the tipo documento sel
     * @return the archivo movilidad ep
     */
    public ArchivoMovilidadEP insertarArchivoMovilidadEPGenerico(long movilidadId, UploadedFile archivo, String tipoDocumentoSel) {

        try {
            if (archivo.getContents() != null) {
                int i = archivo.getFileName().lastIndexOf("\\");
                ArchivoMovilidadEP archivoMovilidadEP = new ArchivoMovilidadEP();
                archivoMovilidadEP.setNombre(archivo.getFileName().substring(i + 1));
                archivoMovilidadEP.setFecha(new Date());
                archivoMovilidadEP.setPersona(personaActual);
                
                TipoMovilidad tipoMovilidad = new TipoMovilidad();
                tipoMovilidad.setId(D1);
                archivoMovilidadEP.setTipoMovilidad(tipoMovilidad);
                List<TipoArchivoMovilidad> listaTipoArchivo = servicioGeneral.obtenerObjetos(TipoArchivoMovilidad.class,
                        "from TipoArchivoMovilidad where id ='" + tipoDocumentoSel + "'");
                TipoArchivoMovilidad tipoArchivo = (TipoArchivoMovilidad) listaTipoArchivo.get(0);
                archivoMovilidadEP.setTipoArchivo(tipoArchivo);
                MovilidadEstudiantesPosgrado mep = new MovilidadEstudiantesPosgrado();
                mep.setId(movilidadId);
                archivoMovilidadEP.setMovilidad(mep);
                servicioGeneral.guardarObjeto(archivoMovilidadEP);
                if (archivoMovilidadEP.getId() != null) {
                    cargarArchivoDisco(archivo, HER_ARCHIVO_MOVILIDAD + "_EP", archivoMovilidadEP.getId().toString());
                }
                return archivoMovilidadEP;
            }
        } catch (Exception x) {
            x.printStackTrace();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, x.getClass().getName(),
                    x.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        return null;

    }

    /**
     * Eliminar archivo movilidad generico.
     *
     * @param id
     *            the id
     * @return true, if successful
     */
    public boolean eliminarArchivoMovilidadGenerico(Long id) {
        String ruta = HER_ARCHIVO_MOVILIDAD + obtenerSubCarpetaArchivo(id) + "//" + id;
        return eliminarArchivoGenerico(ruta);
    }

    /**
     * Consultar seguimiento vis ext.
     *
     * @param id
     *            the id
     * @param consultaFacultad
     *            the consulta facultad
     * @param esConsulta
     *            the es consulta
     * @return the string
     */
    public String consultarSeguimientoVisExt(Long id, boolean consultaFacultad, boolean esConsulta) {
        MovilidadVisitanteExterior movilidadVisitanteSeleccionada = new MovilidadVisitanteExterior();
        movilidadVisitanteSeleccionada.setId(id);
        sesion.setAttribute("movilidadVisExt", movilidadVisitanteSeleccionada);
        if (esConsulta) {
            sesion.setAttribute("consultaMovilidadVisitante", "SI");
        }
        if (consultaFacultad) {
            sesion.setAttribute(ES_CONSULTA_FACULTAD, consultaFacultad);
        }
        sesion.removeAttribute("manejadorCrearEditarSeguimientoMovilidadVisitante");
        return "SeguimientoMovilidadVisitante";
    }

    /**
     * Consultar seguimiento doc.
     *
     * @param id
     *            the id
     * @param consultaFacultad
     *            the consulta facultad
     * @param esConsulta
     *            the es consulta
     * @return the string
     */
    public String ingresarSeguimientoDoc(Long id, boolean consultaFacultad, boolean esConsulta) {
        MovilidadDocentesExterior movilidadEventosSeleccionada = new MovilidadDocentesExterior();
        movilidadEventosSeleccionada.setId(id);
        sesion.setAttribute("movilidadDocente", movilidadEventosSeleccionada);
        sesion.removeAttribute("manejadorCrearEditarSeguimientoMovilidadDocente");
        if (esConsulta) {
            sesion.setAttribute("consultaMovilidadDocente", "SI");
        }
        if (consultaFacultad) {
            sesion.setAttribute(ES_CONSULTA_FACULTAD, true);
        }
        return "SeguimientoMovilidadDocente";
    }

    /**
     * Consultar seguimiento est res.
     *
     * @param id
     *            the id
     * @param consultaFacultad
     *            the consulta facultad
     * @param esConsulta
     *            the es consulta
     * @return the string
     */
    public String ingresarSeguimientoEstRes(Long id, boolean consultaFacultad, boolean esConsulta) {
        MovilidadEstudiantesPosgrado movilidadEstudiantesSeleccionada = new MovilidadEstudiantesPosgrado();
        movilidadEstudiantesSeleccionada.setId(id);
        sesion.setAttribute("movilidadEstudiantePos", movilidadEstudiantesSeleccionada);
        sesion.removeAttribute("manejadorCrearEditarSeguimientoMovilidadEstudiante");
        sesion.setAttribute("esMovilidadResidencia", true);
        if (esConsulta) {
            sesion.setAttribute("consultaMovilidadEstudiantesEventos", "SI");
        }
        if (consultaFacultad) {
            sesion.setAttribute(ES_CONSULTA_FACULTAD, true);
        }
        return "SeguimientoMovilidadEstudiante";
    }

    /**
     * Consultar seguimiento est pos.
     *
     * @param id
     *            the id
     * @param consultaFacultad
     *            the consulta facultad
     * @param esConsulta
     *            the es consulta
     * @return the string
     */
    public String consultarSeguimientoEstPos(Long id, boolean consultaFacultad, boolean esConsulta) {
        MovilidadEstudiantesPosgrado movilidadEstudiantesSeleccionada = new MovilidadEstudiantesPosgrado();
        movilidadEstudiantesSeleccionada.setId(id);
        sesion.setAttribute("movilidadEstudiantePos", movilidadEstudiantesSeleccionada);
        sesion.removeAttribute("manejadorCrearEditarSeguimientoMovilidadEstudiante");
        if (esConsulta) {
            sesion.setAttribute("consultaMovilidadEstudiantesEventos", "SI");
        }
        if (consultaFacultad) {
            sesion.setAttribute(ES_CONSULTA_FACULTAD, true);
        }
        return "SeguimientoMovilidadEstudiante";
    }

    /**
     * Consultar archivos modalidad.
     *
     * @param <T>
     *            the generic type
     * @param type
     *            the type
     * @param codigoMovilidad
     *            the codigo movilidad
     * @return the list
     */
    protected <T> List<T> consultarArchivosModalidad(Class<T> type, String codigoMovilidad) {
        return servicioGeneral.obtenerListaObjetosWhere(type, "where a.movilidad = '" + codigoMovilidad + "'");
    }

    /**
     * Cargar tipos documentos est art.
     *
     * @param tipo
     *            the tipo
     * @return the object[]
     */
    protected Object[] cargarTiposDocumentosGenericoItem(String tipo) {

        Object[] objetos = new Object[2];

        List<SelectItem> listaItems = new ArrayList<SelectItem>();
        String valorInicial = "";

        List<MovilidadArchivo> listaTipoArchivoMovilidadEA = servicioGeneral.obtenerListaArchivosMovilidad(tipo);
        if (!esListaVacia(listaTipoArchivoMovilidadEA)) {
            for (int i = 0; i < listaTipoArchivoMovilidadEA.size(); i++) {
                MovilidadArchivo mva = (MovilidadArchivo) listaTipoArchivoMovilidadEA.get(i);
                listaItems
                        .add(new SelectItem(mva.getTipoArchivo().getId().toString(), mva.getTipoArchivo().getNombre()));

                valorInicial = String
                        .valueOf(((MovilidadArchivo) listaTipoArchivoMovilidadEA.get(0)).getTipoArchivo().getId());
            }

            listaItems.add(new SelectItem("48", "Documento de aprobación de movilidad"));
        }
        objetos[0] = listaItems;
        objetos[1] = valorInicial;
        return objetos;
    }

    /**
     * Obtener correo responsable.
     *
     * @param dependencia
     *            the dependencia
     * @return the list
     */
    public List<Persona> obtenerCorreoResponsable(Dependencia dependencia) {
        List<Persona> encargados = null;
        try {
            String consult = "select #email p.email from " + "Persona p," + "InvestigadorInterno i, " + "PersonaRol pr "
                    + " where i.id.documento= pr.documento " + " and i.id.tipoDocumento= pr.tipoDocumento "
                    + " and p.id.tipoDocumento = p.id.tipoDocumento " + " and p.id.documento = i.id.documento and (pr.fechaFinRol IS NULL OR pr.fechaFinRol >= CURRENT_DATE) ";
            if (dependencia.getSede().isEsSedePresenciaNacional()) {
                consult += " and pr.nombre = 'MD' and i.dependencia.sede.id = '" + dependencia.getSede().getId() + "' "
                        + " ";
            } else {
                consult += " and pr.nombre = 'MF' and i.dependencia.facultad.id = '"
                        + dependencia.getFacultad().getId() + "' " + " and i.dependencia.facultad.esFacultad = 'Y'  ";
            }
            encargados = servicioGeneral.obtenerObjetosLimitado(Persona.class, consult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encargados;
    }

	public List<MovilidadDocentesExterior> getListaMovilidadesEventoFiltered() {
		return listaMovilidadesEventoFiltered;
	}

	public void setListaMovilidadesEventoFiltered(List<MovilidadDocentesExterior> listaMovilidadesEventoFiltered) {
		this.listaMovilidadesEventoFiltered = listaMovilidadesEventoFiltered;
	}

	public List<MovilidadVisitanteExterior> getListaMovilidadesVisitanteFiltered() {
		return listaMovilidadesVisitanteFiltered;
	}

	public void setListaMovilidadesVisitanteFiltered(List<MovilidadVisitanteExterior> listaMovilidadesVisitanteFiltered) {
		this.listaMovilidadesVisitanteFiltered = listaMovilidadesVisitanteFiltered;
	}

	public List<MovilidadEstudiantesPosgrado> getListaMovilidadesEstudiantePosgradoFiltered() {
		return listaMovilidadesEstudiantePosgradoFiltered;
	}

	public void setListaMovilidadesEstudiantePosgradoFiltered(List<MovilidadEstudiantesPosgrado> listaMovilidadesEstudiantePosgradoFiltered) {
		this.listaMovilidadesEstudiantePosgradoFiltered = listaMovilidadesEstudiantePosgradoFiltered;
	}

	public List<MovilidadEstudiantesPosgrado> getListaMovilidadesEstudiantePosgradoEventosFiltered() {
		return listaMovilidadesEstudiantePosgradoEventosFiltered;
	}

	public void setListaMovilidadesEstudiantePosgradoEventosFiltered(
			List<MovilidadEstudiantesPosgrado> listaMovilidadesEstudiantePosgradoEventosFiltered) {
		this.listaMovilidadesEstudiantePosgradoEventosFiltered = listaMovilidadesEstudiantePosgradoEventosFiltered;
	}

	public List<MovilidadDocentesArtes> getListaMovilidadesDocentesArtesFiltered() {
		return listaMovilidadesDocentesArtesFiltered;
	}

	public void setListaMovilidadesDocentesArtesFiltered(List<MovilidadDocentesArtes> listaMovilidadesDocentesArtesFiltered) {
		this.listaMovilidadesDocentesArtesFiltered = listaMovilidadesDocentesArtesFiltered;
	}

	public List<MovilidadEstudiantesArtes> getListaMovilidadesEstudiantesArtesFiltered() {
		return listaMovilidadesEstudiantesArtesFiltered;
	}

	public void setListaMovilidadesEstudiantesArtesFiltered(List<MovilidadEstudiantesArtes> listaMovilidadesEstudiantesArtesFiltered) {
		this.listaMovilidadesEstudiantesArtesFiltered = listaMovilidadesEstudiantesArtesFiltered;
	}

	public List<MovilidadVisitantesArtes> getListaMovilidadesVisitantesArtesFiltered() {
		return listaMovilidadesVisitantesArtesFiltered;
	}

	public void setListaMovilidadesVisitantesArtesFiltered(List<MovilidadVisitantesArtes> listaMovilidadesVisitantesArtesFiltered) {
		this.listaMovilidadesVisitantesArtesFiltered = listaMovilidadesVisitantesArtesFiltered;
	}

	public List<ConvocatoriaPadreParametrizacion> getListaParametrosConvPadre() {
		return listaParametrosConvPadre;
	}

	public void setListaParametrosConvPadre(List<ConvocatoriaPadreParametrizacion> listaParametrosConvPadre) {
		this.listaParametrosConvPadre = listaParametrosConvPadre;
	}

	public Convocatoria getConvActual() {
		return convocatoriaActual;
	}

	public void setConvActual(Convocatoria convocatoriaActual) {
		this.convocatoriaActual = convocatoriaActual;
	}

	public List<ConvocatoriaParametrizacion> getListaParametrosConv() {
		return listaParametrosConv;
	}

	public void setListaParametrosConv(List<ConvocatoriaParametrizacion> listaParametrosConv) {
		this.listaParametrosConv = listaParametrosConv;
	}

	public Convocatoria getConvocatoriaActual() {
		return convocatoriaActual;
	}

	public void setConvocatoriaActual(Convocatoria convocatoriaActual) {
		this.convocatoriaActual = convocatoriaActual;
	}

}
