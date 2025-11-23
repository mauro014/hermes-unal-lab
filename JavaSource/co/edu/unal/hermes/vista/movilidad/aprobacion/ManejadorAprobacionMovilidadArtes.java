package co.edu.unal.hermes.vista.movilidad.aprobacion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import co.edu.unal.hermes.bd.imp.MovilidadDAOHibernate;
import co.edu.unal.hermes.modelo.ArchivoMovilidad;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.MovilidadVisitantesArtes;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.utils.Navegacion;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorAprobacionMovilidadArtes extends ManejadorBase {

    private List listaMovilidadesVisitanteArtes;

    private MovilidadVisitantesArtes movilidadVisitantesArtesSel;
    CorreoPlantilla correoActual = new CorreoPlantilla();
    String cuerpoCorreo = "";

    public ManejadorAprobacionMovilidadArtes() {

        Persona persona = new Persona();
        persona = (Persona) sesion.getAttribute("persona");

        Dependencia dependencia;

        dependencia = new Dependencia();

        try {
            persona = servicioPersona.obtenerInvestigadorInternoCompleto(persona.getId());
            InvestigadorInterno investigadorInterno = (InvestigadorInterno) persona;
            dependencia = servicioDependencia.obtenerDependencia2(investigadorInterno.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setListaMovilidadesVisitanteArtes(servicioMovilidad.obtenerMovilidadesRevision(
                MovilidadVisitantesArtes.ARTISTASVISITANTES, persona, dependencia, "", MovilidadDAOHibernate.FACULTAD));

    }

    public void descargarDocumentoEvento() {

        FacesContext context = FacesContext.getCurrentInstance();
        Map map = context.getExternalContext().getRequestParameterMap();
        Object o = (Object) map.get("archivoResumen");
        Long idArchivo = Long.valueOf((String) o);
        o = (Object) map.get("movilidadVisitante");
        Long idME = Long.valueOf((String) o);
        ArchivoMovilidad archMDESel = null;
        MovilidadVisitantesArtes mdeSel = null;
        for (Object o2 : listaMovilidadesVisitanteArtes) {
            MovilidadVisitantesArtes me = (MovilidadVisitantesArtes) o2;
            Long idMe2 = me.getId();
            if (idMe2.equals(idME)) {
                mdeSel = me;
                break;
            }
        }
        if (mdeSel != null) {
            Iterator it = mdeSel.getArchivos().iterator();
            while (it.hasNext()) {
                ArchivoMovilidad amde = (ArchivoMovilidad) it.next();
                if (amde.getId().equals(idArchivo)) {
                    archMDESel = amde;
                    break;
                }
            }
        }
        if (archMDESel != null) {

            ArchivoMovilidad archivo = archMDESel;
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

    public void setListaMovilidadesVisitanteArtes(List listaMovilidadesVisitanteArtes) {
        this.listaMovilidadesVisitanteArtes = listaMovilidadesVisitanteArtes;
    }

    public List getListaMovilidadesVisitanteArtes() {
        return listaMovilidadesVisitanteArtes;
    }

    public void imprimirMovilidadVisitanteArtes() {
        FacesContext context = FacesContext.getCurrentInstance();
        Long id = movilidadVisitantesArtesSel.getId();
        // movilidadActual = servicioMovilidad.obtenerMovilidad(id);
        // List act = servicioMovilidad.obtenerActividades(movilidadActual);
        // Set tmp = new HashSet();
        // tmp.addAll(act);
        // movilidadActual.setActividades(tmp);
        // movilidadVista = completarMovilidad(movilidadActual,
        // Movilidad.VISITANTE);
        // if (movilidadVista.getNombreArchivo() == null) {
        // mostrarArchivo = false;
        // }

        // long movId = movilidadVista.getId().longValue();
        ReporteBirt r = new ReporteBirt();
        r.adicionarParametro("id", Long.toString(id));
        r.setNombreReporte("/movilidad/ReporteMovilidadVisArt");
        r.setFormato(ReporteBirt.FORMATO_PDF);
        sesion.setAttribute("reporte", r);
        System.out.println("mirar");
        System.out.println(Navegacion.REPORTE);
        try {
            context.getExternalContext().dispatch("/ReporteEngineServlet");
        } catch (Exception e) {
        } finally {
            context.responseComplete();
        }
    }

    public void setMovilidadVisitantesArtesSel(MovilidadVisitantesArtes movilidadVisitantesArtesSel) {
        this.movilidadVisitantesArtesSel = movilidadVisitantesArtesSel;
    }

    public MovilidadVisitantesArtes getMovilidadVisitantesArtesSel() {
        return movilidadVisitantesArtesSel;
    }

    public String aprobarMovilidad() {

        MovilidadVisitantesArtes mov = new MovilidadVisitantesArtes();
        MovilidadVisitantesArtes movAux = new MovilidadVisitantesArtes();

        Long id = ((MovilidadVisitantesArtes) (movilidadVisitantesArtesSel)).getId();

        movAux = (MovilidadVisitantesArtes) (movilidadVisitantesArtesSel);

        List listaMovilidad;
        listaMovilidad = new ArrayList();

        listaMovilidad = servicioGeneral.obtenerListaObjetos("MovilidadDocentesExterior where id ='" + id + "'");

        if (listaMovilidad == null) {
            listaMovilidad = new ArrayList();
        }

        mov = (MovilidadVisitantesArtes) movilidadVisitantesArtesSel;
        mov.setAceptacionFacultad("SI");
        mov.setComentariosFac(movAux.getComentariosFac());
        servicioGeneral.guardarObjeto(mov);

        personaActual = (Persona) sesion.getAttribute("persona");
        String dirCorreoConfirmacion = personaActual.getEmail();

        correoActual = cargarPlantilla(66);// 42a
        editarCorreo(mov.getPersonaInv(), mov.getTipoMovilidad().getNombre(), mov.getId(), mov.getComentariosFac());
        Correo correo = new Correo();
        correo.setOrigen(Correo.CORREO_HERMES);
        correo.adicionarDireccion(dirCorreoConfirmacion);
        correo.setAsunto(correoActual.getAsunto());
        correo.setCuerpo(cuerpoCorreo);
        servicioCorreo.enviarCorreo(correo);

        sesion.removeAttribute("ManejadorAprobacionMovilidad");
        sesion.removeAttribute("movilidad");
        return "listadoAprobacion2";
    }

    public String noAprobarMovilidad() {

        MovilidadVisitantesArtes mov = new MovilidadVisitantesArtes();
        MovilidadVisitantesArtes movAux = new MovilidadVisitantesArtes();

        Long id = ((MovilidadVisitantesArtes) (movilidadVisitantesArtesSel)).getId();

        movAux = (MovilidadVisitantesArtes) (movilidadVisitantesArtesSel);

        List listaMovilidad;
        listaMovilidad = new ArrayList();

        listaMovilidad = servicioGeneral.obtenerListaObjetos("MovilidadDocentesExterior where id ='" + id + "'");

        if (listaMovilidad == null) {
            listaMovilidad = new ArrayList();
        }

        mov = (MovilidadVisitantesArtes) movilidadVisitantesArtesSel;
        mov.setAceptacionFacultad("NO");
        mov.setComentariosFac(movAux.getComentariosFac());
        servicioGeneral.guardarObjeto(mov);

        personaActual = (Persona) sesion.getAttribute("persona");
        String dirCorreoConfirmacion = personaActual.getEmail();

        correoActual = cargarPlantilla(66);// 42a
        editarCorreo(mov.getPersonaInv(), mov.getTipoMovilidad().getNombre(), mov.getId(), mov.getComentariosFac());
        Correo correo = new Correo();
        correo.setOrigen(Correo.CORREO_HERMES);
        correo.adicionarDireccion(dirCorreoConfirmacion);
        correo.setAsunto(correoActual.getAsunto());
        correo.setCuerpo(cuerpoCorreo);
        servicioCorreo.enviarCorreo(correo);

        sesion.removeAttribute("ManejadorAprobacionMovilidad");
        sesion.removeAttribute("movilidad");
        return "listadoAprobacion2";
    }

    public CorreoPlantilla cargarPlantilla(int cod_id) {

        CorreoPlantilla correoActualAux = new CorreoPlantilla();
        // CorreoPlantilla correoActualAux=(CorreoPlantilla)
        CorreoPlantilla a = new CorreoPlantilla();
        // CorreoPlantilla correoActualAux3 = (CorreoPlantilla) servicioGeneral
        // .obtenerObjeto(a, Long.valueOf(String.valueOf(cod_id)));
        List lista = servicioGeneral.obtenerObjetos("select c from CorreoPlantilla c where c.id='" + cod_id + "'");
        if (lista != null && lista.size() > 0) {
            correoActualAux = (CorreoPlantilla) lista.get(0);
        }
        // String
        // correo=correoActual.getCuerpo().replaceAll("<<fecha>>",Fecha.fechaActual());
        return correoActualAux;
    }

    public String editarCorreo(Persona personaAux, String tipo, Long id, String comFac) {

        try {
            String coinvNombre = "";

            String correo = correoActual.getCuerpo();

            String investigador = "";

            investigador = personaAux.getNombre1() + " " + personaAux.getApellido1() + " " + personaAux.getApellido2();
            correo = correo.replaceAll("<<INVESTIGADOR>>", investigador);

            correo = correo.replaceAll("<<IDMOVILIDAD>>", id.toString());
            correo = correo.replaceAll("<<TIPO>>", tipo);

            correo = correo.replaceAll("<<OBSERVACION>>", comFac);

            cuerpoCorreo = correo;

            // cuerpoCorreo2 = investigador + " " + coinvNombre;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return "";
    }

}
