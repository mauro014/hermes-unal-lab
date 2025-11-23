package co.edu.unal.hermes.vista.movilidad.aprobacion;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import co.edu.unal.hermes.modelo.ArchivoMovilidad;
import co.edu.unal.hermes.modelo.ArchivoMovilidadDE;
import co.edu.unal.hermes.modelo.ArchivoMovilidadEP;
import co.edu.unal.hermes.modelo.ArchivoMovilidadVE;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.MovilidadDocentesArtes;
import co.edu.unal.hermes.modelo.MovilidadDocentesExterior;
import co.edu.unal.hermes.modelo.MovilidadEstudiantesArtes;
import co.edu.unal.hermes.modelo.MovilidadEstudiantesPosgrado;
import co.edu.unal.hermes.modelo.MovilidadVisitanteExterior;
import co.edu.unal.hermes.modelo.MovilidadVisitantesArtes;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.vista.movilidad.ManejadorBaseMovilidad;

public class ManejadorAprobacionMovilidadCons extends ManejadorBaseMovilidad {

    private static final long serialVersionUID = -252112812479735842L;

    private List<MovilidadDocentesExterior> listaMovilidadesEvento;
    private List<MovilidadVisitanteExterior> listaMovilidadesVisitante;
    private List<MovilidadEstudiantesPosgrado> listaMovilidadesEstudiantePosgrado;
    private List<MovilidadEstudiantesPosgrado> listaMovilidadesEstudiantePosgradoEventos;
    private List<MovilidadDocentesArtes> listaMovilidadesDocentesArtes;
    private List<MovilidadEstudiantesArtes> listaMovilidadesEstudiantesArtes;
    private List<MovilidadVisitantesArtes> listaMovilidadesVisitantesArtes;

    private MovilidadDocentesExterior movilidadEventosSeleccionada;
    private MovilidadVisitanteExterior movilidadVisitanteSeleccionada;
    private MovilidadDocentesArtes movilidadDocenteArtesSeleccionada;
    private MovilidadEstudiantesArtes movilidadEstudiantesArtesSeleccionada;
    private MovilidadEstudiantesPosgrado movilidadEstudiantesSeleccionada;

    private ArchivoMovilidadDE archivoMovilidadDESeleccionada;
    private ArchivoMovilidadVE archivoMovilidadVSeleccionada;
    private ArchivoMovilidadEP ArchivoMovilidadEPSeleccionada;
    private MovilidadEstudiantesPosgrado movilidadEstudiantesPosgradoSel;

    public ManejadorAprobacionMovilidadCons() {
        cargarListasMovilidades();
    }

    public void imprimirMovilidadVisitante() {
        imprimirMovilidadVisitanteGenerico(movilidadVisitanteSeleccionada);
    }

    public void imprimirMovilidadEvento() {
        imprimirMovilidadEventoGenerico(movilidadEventosSeleccionada);
    }

    public void imprimirMovilidadPosgrado() {
        imprimirMovilidadEstudiantesPosgradoGenerico(movilidadEstudiantesSeleccionada);
    }

    public void imprimirMovilidadDocenteArtes() {
        imprimirMovilidadDocenteArtesGenerico(movilidadDocenteArtesSeleccionada);
    }

    public void imprimirMovilidadEstudianteArtes() {
        imprimirMovilidadEstudiantesArtesGenerico(movilidadEstudiantesArtesSeleccionada);
    }

    private void cargarListasMovilidades() {
        Persona persona = (Persona) sesion.getAttribute("persona");
        Dependencia dependencia = null;
        try {
            persona = servicioPersona.obtenerInvestigadorInternoCompleto(persona.getId());
            InvestigadorInterno investigadorInterno = (InvestigadorInterno) persona;
            dependencia = servicioDependencia.obtenerDependencia(investigadorInterno.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (dependencia != null) {
            listaMovilidadesVisitante = cargarMovilidades(MovilidadVisitanteExterior.class, dependencia, "aceptacion",
                    "aprobacion", true, false, "and mov.tipoMovilidad.id IN ('A1','CF_MOV3','CF_MOV7')", false);
            listaMovilidadesEvento = cargarMovilidades(MovilidadDocentesExterior.class, dependencia, "aceptacion",
                    "aprobacion", true, false, "and mov.tipoMovilidad.id IN ('B1','CF_MOV2','CF_MOV4','CF_MOV8')",
                    false);
            listaMovilidadesEstudiantePosgradoEventos = cargarMovilidades(MovilidadEstudiantesPosgrado.class,
                    dependencia, "aceptacion", "aprobacion", true, true,
                    "and mov.tipoMovilidad.id in ('MOV3_IN','CF_MOV1','CF_MOV6')", true);
            listaMovilidadesEstudiantePosgrado = cargarMovilidades(MovilidadEstudiantesPosgrado.class, dependencia,
                    "aceptacion", "aprobacion", true, true, "and mov.tipoMovilidad.id IN ('D1','CF_MOV5')", true);
            listaMovilidadesEstudiantesArtes = cargarMovilidades(MovilidadEstudiantesArtes.class, dependencia,
                    "aceptacionFacultad", "aprobacionSede", false, true, "", false);
            listaMovilidadesDocentesArtes = cargarMovilidades(MovilidadDocentesArtes.class, dependencia,
                    "aceptacionFacultad", "aprobacionSede", false, false, "", false);
        }

    }

    private <T> List<T> cargarMovilidades(Class<T> clase, Dependencia dependencia, String nombreAceptacion,
            String nombreAprobacion, boolean seg, boolean estudiante, String hqlWhereAdicional, boolean tipoM) {
        List<T> lista;
        String hql = "select #id mov.id,#tipoMovilidadCadena mov.tipoMovilidad.id, #fechainicial mov.fechainicial," + "#fechasolicitud mov.fechasolicitud," + "#"
                + nombreAceptacion + " mov." + nombreAceptacion + "," + "#" + nombreAprobacion + " mov."
                + nombreAprobacion + "";
        if (seg) {
            hql += ",#estadoSeguimiento mov.estadoSeguimiento ";
        }
        if (estudiante) {
            hql += ",#estudianteInv mov.estudianteInv ";
        }
        if (tipoM) {
            hql += ",#tipoMovilidad mov.tipoMovilidad ";
        }
        hql += " from " + clase.getSimpleName() + " mov " + ", InvestigadorInterno ii ";
        if (estudiante) {
            hql += ", Estudiante e ";
        }
        hql += " where " + " mov.personaInv.id.documento = ii.id.documento and "
                + " mov.personaInv.id.tipoDocumento = ii.id.tipoDocumento ";
        if (estudiante) {
            hql += " and e.id.documento = mov.estudianteInv.id.documento "
                    + "and e.id.tipoDocumento = mov.estudianteInv.id.tipoDocumento "
                    + " and e.dependencia.facultad.id ='";
        } else {
            hql += " and ii.dependencia.facultad.id ='";
        }
        hql += dependencia.getFacultad().getId() + "' "
                + " and mov.convocatoria.id IS NOT NULL and ii.id.documento not in (" + DOCUMENTOS_PRUEBAS_DESARROLLO
                + ") ";
        hql += hqlWhereAdicional;
        lista = servicioGeneral.obtenerObjetosLimitado(clase, hql);
        return lista;
    }

    public String consultarSeguimientoVisExt() {
        boolean consultaFacultad = false;
        boolean esConsulta = true;
        return consultarSeguimientoVisExt(movilidadVisitanteSeleccionada.getId(), consultaFacultad, esConsulta);
    }

    public String consultarSeguimientoDoc() {
        boolean consultaFacultad = false;
        boolean esConsulta = true;
        return ingresarSeguimientoDoc(movilidadEventosSeleccionada.getId(), consultaFacultad, esConsulta);
    }

    public void descargarDocumentoEvento() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        Object o = (Object) map.get("archivoResumen");
        Long idArchivo = Long.valueOf((String) o);
        descargarArchivoMovilidadDEGenerico(idArchivo);
    }

    public void descargarDocumentoEstPosgrado() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        Object o = (Object) map.get("archivoResumen4");
        Long idArchivo = Long.valueOf((String) o);
        descargarArchivoMovilidadEPGenerico(idArchivo);
    }

    public void descargarDocumentoDocenArtes() {

        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        Object o = (Object) map.get("archivoResumen6");
        Long idArchivo = Long.valueOf((String) o);
        o = (Object) map.get("movilidadDocentesArtes");
        Long idMEP = Long.valueOf((String) o);
        ArchivoMovilidad archMEPSel = null;
        MovilidadDocentesArtes mdepSel = null;
        for (Object o2 : listaMovilidadesDocentesArtes) {
            MovilidadDocentesArtes me = (MovilidadDocentesArtes) o2;
            Long idMep2 = me.getId();
            if (idMep2.equals(idMEP)) {
                mdepSel = me;
                break;
            }
        }
        if (mdepSel != null) {
            Iterator<ArchivoMovilidad> it = mdepSel.getArchivos().iterator();
            while (it.hasNext()) {
                ArchivoMovilidad amde = (ArchivoMovilidad) it.next();
                if (amde.getId().equals(idArchivo)) {
                    archMEPSel = amde;
                    break;
                }
            }
        }
        if (archMEPSel != null) {
            ArchivoMovilidad archivo = (ArchivoMovilidad) archMEPSel;

            FacesContext ctx = FacesContext.getCurrentInstance();
            try {
                if (!ctx.getResponseComplete()) {
                    HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
                    response.setContentType("text/plain");
                    response.setHeader("Content-Disposition", "attachment;filename=\"" + archivo.getNombre() + "\"");
                    ServletOutputStream out = response.getOutputStream();
                    out.write(archivo.getBytes());
                    out.flush();
                    ctx.responseComplete();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String consultarSeguimientoEstRes() {
        boolean consultaFacultad = false;
        boolean esConsulta = true;
        return ingresarSeguimientoEstRes(movilidadEstudiantesSeleccionada.getId(), consultaFacultad, esConsulta);
    }

    public String consultarSeguimientoEstPos() {
        boolean consultaFacultad = false;
        boolean esConsulta = true;
        return consultarSeguimientoEstPos(movilidadEstudiantesSeleccionada.getId(), consultaFacultad, esConsulta);
    }

    public void descargarDocumentoVisitante() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        Object o = (Object) map.get("archivoResumen1");
        Long idArchivo = Long.valueOf((String) o);
        descargarArchivoMovilidadVEGenerico(idArchivo);
    }

    public List<MovilidadDocentesExterior> getListaMovilidadesEvento() {
        return listaMovilidadesEvento;
    }

    public List<MovilidadEstudiantesPosgrado> getListaMovilidadesEstudiantePosgrado() {
        return listaMovilidadesEstudiantePosgrado;
    }

    public List<MovilidadVisitanteExterior> getListaMovilidadesVisitante() {
        return listaMovilidadesVisitante;
    }

    public List<MovilidadEstudiantesPosgrado> getListaMovilidadesEstudiantePosgradoEventos() {
        return listaMovilidadesEstudiantePosgradoEventos;
    }

    public List<MovilidadDocentesArtes> getListaMovilidadesDocentesArtes() {
        return listaMovilidadesDocentesArtes;
    }

    public List<MovilidadEstudiantesArtes> getListaMovilidadesEstudiantesArtes() {
        return listaMovilidadesEstudiantesArtes;
    }

    public List<MovilidadVisitantesArtes> getListaMovilidadesVisitantesArtes() {
        return listaMovilidadesVisitantesArtes;
    }

    public void setMovilidadEventosSeleccionada(MovilidadDocentesExterior movilidadEventosSeleccionada) {
        this.movilidadEventosSeleccionada = movilidadEventosSeleccionada;
    }

    public MovilidadDocentesExterior getMovilidadEventosSeleccionada() {
        return movilidadEventosSeleccionada;
    }

    public void setMovilidadVisitanteSeleccionada(MovilidadVisitanteExterior movilidadVisitanteSeleccionada) {
        this.movilidadVisitanteSeleccionada = movilidadVisitanteSeleccionada;
    }

    public MovilidadVisitanteExterior getMovilidadVisitanteSeleccionada() {
        return movilidadVisitanteSeleccionada;
    }

    public void setMovilidadEstudiantesSeleccionada(MovilidadEstudiantesPosgrado movilidadEstudiantesSeleccionada) {
        this.movilidadEstudiantesSeleccionada = movilidadEstudiantesSeleccionada;
    }

    public MovilidadEstudiantesPosgrado getMovilidadEstudiantesSeleccionada() {
        return movilidadEstudiantesSeleccionada;
    }

    public void setArchivoMovilidadDESeleccionada(ArchivoMovilidadDE archivoMovilidadDESeleccionada) {
        this.archivoMovilidadDESeleccionada = archivoMovilidadDESeleccionada;
    }

    public ArchivoMovilidadDE ManejadorAprobacionMovilidadSede() {
        return archivoMovilidadDESeleccionada;
    }

    public void setArchivoMovilidadVSeleccionada(ArchivoMovilidadVE archivoMovilidadVSeleccionada) {
        this.archivoMovilidadVSeleccionada = archivoMovilidadVSeleccionada;
    }

    public ArchivoMovilidadVE getArchivoMovilidadVSeleccionada() {
        return archivoMovilidadVSeleccionada;
    }

    public void setArchivoMovilidadEPSeleccionada(ArchivoMovilidadEP archivoMovilidadEPSeleccionada) {
        ArchivoMovilidadEPSeleccionada = archivoMovilidadEPSeleccionada;
    }

    public ArchivoMovilidadEP getArchivoMovilidadEPSeleccionada() {
        return ArchivoMovilidadEPSeleccionada;
    }

    public MovilidadDocentesArtes getMovilidadDocenteArtesSeleccionada() {
        return movilidadDocenteArtesSeleccionada;
    }

    public void setMovilidadDocenteArtesSeleccionada(MovilidadDocentesArtes movilidadDocenteArtesSeleccionada) {
        this.movilidadDocenteArtesSeleccionada = movilidadDocenteArtesSeleccionada;
    }

    public ArchivoMovilidadDE getArchivoMovilidadDESeleccionada() {
        return archivoMovilidadDESeleccionada;
    }

    public MovilidadEstudiantesPosgrado getMovilidadEstudiantesPosgradoSel() {
        return movilidadEstudiantesPosgradoSel;
    }

    public void setMovilidadEstudiantesPosgradoSel(MovilidadEstudiantesPosgrado movilidadEstudiantesPosgradoSel) {
        this.movilidadEstudiantesPosgradoSel = movilidadEstudiantesPosgradoSel;
    }

}
