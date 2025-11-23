
package co.edu.unal.hermes.vista.evaluadores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.myfaces.component.html.ext.HtmlDataTable;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.primefaces.event.FileUploadEvent;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.ProyectoEvaluador;
import co.edu.unal.hermes.modelo.TipoArchivo;
import co.edu.unal.hermes.modelo.TipoInvestigacion;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.utils.ComparadorProyectoEvaluador;
import co.edu.unal.hermes.utils.Navegacion;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * @author Juan Pablo Duque
 * @modified Rodrigo Gallo
 * 
 */

public class ManejadorProyectosEvaluador extends ManejadorBase {

    private static final long serialVersionUID = -4597482426142286684L;
    private Investigador evaluador;
    private HtmlDataTable tablaProyectos;
    private SelectItem[] proyectosEvaluadorItem;
    private String idProyecto;

    private UploadedFile archivoSubir;
    private List listaProyectos;
    private List listaProyectosEvaluador;
    private Proyecto proyectoActual;
    private List listaArchivos;
    private HtmlPanelGroup panelArchivos;
    private HtmlDataTable tablaArchivos;
    private UploadedFile archivo;
    private TipoArchivo tipoArchivo;
    private boolean noArchivos;
    private boolean mostrarEstoySeguro;
    private boolean mostrarBotonesAp;
    private ProyectoEvaluador proyetoSeleccionado;

    private List listaTipoArchivo;

    private SelectItem[] tipoArchivoItem;
    private boolean bArtistica;
    private boolean bFormatos;
    private Date FechaBD;

    private boolean esExtensionSolidaria;

    private ProyectoEvaluador ProEval;

    public Date getFechaBD() {
        return FechaBD;
    }

    public void setFechaBD(Date fechaBD) {
        FechaBD = fechaBD;
    }

    public ManejadorProyectosEvaluador() {
        tablaProyectos = new HtmlDataTable();
        mostrarEstoySeguro = true;
        mostrarBotonesAp = false;
        personaActual = (Persona) sesion.getAttribute("persona");
        evaluador = servicioPersona.obtenerInvestigadorProyectosPropuestosAEvaluar(personaActual.getId());
        cargarProyectos();
        getEsCreacionArtistica();
        panelArchivos = new HtmlPanelGroup();
        tablaArchivos = new HtmlDataTable();
        listaTipoArchivo = new ArrayList();
        noArchivos = false;
        panelArchivos.setRendered(true);
        FechaBD = servicioGeneral.obtenerFechaDB();
        ProEval.setFechaBD(FechaBD);
    }

    private void cargarProyectos() {

        listaProyectosEvaluador = evaluador.getListaProyectosEvaluador();
        listaProyectos = new ArrayList();
        proyectosEvaluadorItem = new SelectItem[listaProyectosEvaluador.size()];
        for (int i = 0; i < listaProyectosEvaluador.size(); i++) {
            ProyectoEvaluador proyectoEvaluador = (ProyectoEvaluador) listaProyectosEvaluador.get(i);
            List listaArchivosConvocatoriaPadreProceso;
            Proyecto p = ((ProyectoEvaluador) listaProyectosEvaluador.get(i)).getProyecto();
            ConvocatoriaPadre padre = ((Convocatoria) p.getModalidad()).getPadre();
            listaArchivosConvocatoriaPadreProceso = servicioGeneral
                    .obtenerListaObjetos("ArchivoConvocatoriaPadre a where (a.terminos = 'Y' or a.terminos = 'A') "
                            + "and a.tipoArchivo <> 'B' and a.convocatoriaPadre = '" + padre.getId() + "' ");
            proyectoEvaluador.setListaArchivosConvocatoriaPadre(listaArchivosConvocatoriaPadreProceso);
            listaProyectos.add(p);
            List listaConvocatoria = servicioGeneral
                    .obtenerListaObjetos("Convocatoria c where c.id = '" + p.getModalidad().getId() + "'");
            if (listaConvocatoria != null && listaConvocatoria.size() > 0) {
                Convocatoria conv = (Convocatoria) listaConvocatoria.get(0);
                if(!esNulo(conv.getMostrarArchivosEvaluador())) {
                	if(conv.getMostrarArchivosEvaluador().equals("Y"))
                		proyectoEvaluador.setMostrarArchivosEvaluador(true);
                	else
                		proyectoEvaluador.setMostrarArchivosEvaluador(false);
                } else
                	proyectoEvaluador.setMostrarArchivosEvaluador(false);
            } else
                proyectoEvaluador.setMostrarArchivosEvaluador(false);
            String nombre = p.getNombre();
            if (nombre.length() > 65) {
                nombre = nombre.substring(0, 65) + "...";
            }
            proyectosEvaluadorItem[i] = new SelectItem(p.getId().toString(), nombre);
        }
        if (listaProyectos != null && listaProyectos.size() > 0) {
            proyectoActual = (Proyecto) listaProyectos.get(0);
            idProyecto = proyectoActual.getId().toString();
        }
        Iterator ite = listaProyectosEvaluador.iterator();
        List temp = new ArrayList();
        while (ite.hasNext()) {
            ProyectoEvaluador tmp = (ProyectoEvaluador) ite.next();

            ProyectoEvaluador pryEval = new ProyectoEvaluador();

            pryEval = servicioProyecto.obtenerProyectoEvaluador(tmp);

            tmp.setEstado(pryEval.getEstado());
            tmp.setDocumentoEvaluador(pryEval.getDocumentoEvaluador());

            try {
                listaArchivos = servicioProyecto.obtenerNombresArchivos(tmp.getProyecto());
                // Cuando no tiene archivos no se muestra el panel
                tmp.setListaArchivos(listaArchivos);
                if (listaArchivos.size() == 0) {
                    tmp.setMostrarArchivos(false);
                } else {
                    tmp.setMostrarArchivos(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            temp.add(tmp);
        }
        listaProyectosEvaluador = new ArrayList(temp);
        Collections.sort(listaProyectosEvaluador, new ComparadorProyectoEvaluador());
    }

    public void cambiarProyecto(ValueChangeEvent event) {
        Long l = new Long(event.getNewValue().toString());
        proyectoActual = buscarProyectoxId(l);
        idProyecto = proyectoActual.getId().toString();
    }

    public String verProyecto() {
        ProyectoEvaluador pe = (ProyectoEvaluador) tablaProyectos.getRowData();
        return "";
    }

    public String evaluarProyectoLista() {
        ProyectoEvaluador pe = new ProyectoEvaluador();

        mostrarBotonesAp = false;
        mostrarEstoySeguro = true;

        // tablaProyectos = new HtmlDataTable();
        pe = proyetoSeleccionado;

        // ProyectoEvaluador pe= buscarProyectoEvaluadorxId(p.getId());
        sesion.removeAttribute("manejadorProyectosEvaluador");
        sesion.removeAttribute("manejadorEvaluacion");
        sesion.removeAttribute("proyectoEvaluador");
        sesion.setAttribute("proyectoEvaluador", pe);

        return "evaluarProyecto";
    }

    public void imprimirReporteLista() {
        ProyectoEvaluador p = proyetoSeleccionado;

        proyectoActual = servicioProyecto.obtenerProyecto(p.getProyecto().getId(),
                ProyectoDAOHibernate.INFORMACION_GENERAL);

        servicioProyecto.imprimirReporteProyecto(proyectoActual, sesion, true);

    }

    public void descargarDocumentoEvaluacion() {
        ProyectoEvaluador p = proyetoSeleccionado;
        descargarArchivoGenerico("HER_PROYECTO_EVALUADOR", p.getId().toString(), p.getDocumentoEvaluador());
    }

    public void imprimirProyectoAsociado() {
        ProyectoEvaluador p = proyetoSeleccionado;

        proyectoActual = servicioProyecto.obtenerProyecto(Long.parseLong(p.getProyecto().getCodigoDib()),
                ProyectoDAOHibernate.INFORMACION_GENERAL);

        servicioProyecto.imprimirReporteProyecto(proyectoActual, sesion, true);

    }

    public void mostrarBotones() {

        mostrarBotonesAp = true;
        mostrarEstoySeguro = false;

    }

    public String evaluarProyecto() {

        sesion.setAttribute("proyectoEvaluador", buscarProyectoEvaluadorxId(proyectoActual.getId()));
        sesion.removeAttribute("manejadorEvaluacion");
        return "evaluarProyecto";
    }

    public String consultarProyecto() {
        try {
            Boolean b;
            b = new Boolean(true);
            borrarManejadoresInsercionProyecto();
            sesion.removeAttribute("manejadorProyectosEvaluador");
            sesion.setAttribute("consultaEvaluacion", b);
            sesion.setAttribute("proyecto", proyectoActual);
            return "consultaProyecto";
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    private Proyecto buscarProyectoxId(Long idProyecto) {
        Iterator it = listaProyectos.iterator();
        while (it.hasNext()) {
            Proyecto p = (Proyecto) it.next();
            if (p.getId().longValue() == idProyecto.longValue()) {
                return p;
            }
        }
        return null;
    }

    private ProyectoEvaluador buscarProyectoEvaluadorxId(Long idProyecto) {
        Iterator it = listaProyectosEvaluador.iterator();
        while (it.hasNext()) {
            ProyectoEvaluador p = (ProyectoEvaluador) it.next();
            if (p.getProyecto().getId().longValue() == idProyecto.longValue()) {
                return p;
            }
        }
        return null;
    }

    public void getEsCreacionArtistica() {
        bArtistica = false;
        Iterator ite = listaProyectosEvaluador.iterator();
        while (ite.hasNext()) {
            ProyectoEvaluador p = (ProyectoEvaluador) ite.next();
            if (p != null && p.getProyecto().getTipoInvestigacion() != null
                    && p.getProyecto().getTipoInvestigacion().getId().equals(TipoInvestigacion.CREACION_ARTISTICA)) {
                bArtistica = true;
            }
        }
    }

    public Investigador getEvaluador() {
        return evaluador;
    }

    public void setEvaluador(Investigador evaluador) {
        this.evaluador = evaluador;
    }

    public HtmlDataTable getTablaProyectos() {
        return tablaProyectos;
    }

    public void setTablaProyectos(HtmlDataTable tablaProyectos) {
        this.tablaProyectos = tablaProyectos;
    }

    public void descargarArchivo() {

        FacesContext context = FacesContext.getCurrentInstance();
        Map map = context.getExternalContext().getRequestParameterMap();
        Object o = (Object) map.get("archivoResumen");
        Long id = Long.valueOf((String) o);

        Object o1 = (Object) map.get("proyectoEval");
        Long idP = Long.valueOf((String) o1);

        descargarArchivoProyectoGenerico(id, idP);
    }

    public void descargarArchivoConvocatoriaPadre() {

        FacesContext context = FacesContext.getCurrentInstance();
        Map map = context.getExternalContext().getRequestParameterMap();
        Object o = (Object) map.get("archivoConvocatoria");
        Long id = Long.valueOf((String) o);

        Object o2 = (Object) map.get("archivoNombreConvocatoria");
        String nombre = (String) o2;

        /*
         * Object o3 = (Object) map.get("archivoIdConvocatoria"); String
         * idConvocatoria = (String) o3;
         */

        String ext = obtenerExtensionArchivo(nombre);
        descargarArchivoGenerico("HER_ARCHIVO_CONVOCATORIA_PADRE", id.toString(), id + ext);

        // String path = "HER_ARCHIVO_CONVOCATORIA_PADRE//"+idConvocatoria+"//"
        // + id + "//" + nombre;
        // servicioGeneral.descargarDocumentoDisco(path, true);
    }

    /**
     * @return Returns the idProyecto.
     */
    public String getIdProyecto() {
        return idProyecto;
    }

    /**
     * @param idProyecto
     *            The idProyecto to set.
     */
    public void setIdProyecto(String idProyecto) {
        this.idProyecto = idProyecto;
    }

    /**
     * @return Returns the listaProyectos.
     */
    public List getListaProyectos() {
        return listaProyectos;
    }

    /**
     * @param listaProyectos
     *            The listaProyectos to set.
     */
    public void setListaProyectos(List listaProyectos) {
        this.listaProyectos = listaProyectos;
    }

    /**
     * @return Returns the proyectoActual.
     */
    public Proyecto getProyectoActual() {
        return proyectoActual;
    }

    /**
     * @param proyectoActual
     *            The proyectoActual to set.
     */
    public void setProyectoActual(Proyecto proyectoActual) {
        this.proyectoActual = proyectoActual;
    }

    /**
     * @return Returns the proyectosEvaluadorItem.
     */
    public SelectItem[] getProyectosEvaluadorItem() {
        return proyectosEvaluadorItem;
    }

    /**
     * @param proyectosEvaluadorItem
     *            The proyectosEvaluadorItem to set.
     */
    public void setProyectosEvaluadorItem(SelectItem[] proyectosEvaluadorItem) {
        this.proyectosEvaluadorItem = proyectosEvaluadorItem;
    }

    public List getListaProyectosEvaluador() {
        return listaProyectosEvaluador;
    }

    public void setListaProyectosEvaluador(List listaProyectosEvaluador) {
        this.listaProyectosEvaluador = listaProyectosEvaluador;
    }

    public List getListaArchivos() {
        return listaArchivos;
    }

    public void setListaArchivos(List listaArchivos) {
        this.listaArchivos = listaArchivos;
    }

    public HtmlPanelGroup getPanelArchivos() {
        return panelArchivos;
    }

    public void setPanelArchivos(HtmlPanelGroup panelArchivos) {
        this.panelArchivos = panelArchivos;
    }

    public HtmlDataTable getTablaArchivos() {
        return tablaArchivos;
    }

    public void setTablaArchivos(HtmlDataTable tablaArchivos) {
        this.tablaArchivos = tablaArchivos;
    }

    public UploadedFile getArchivo() {
        return archivo;
    }

    public void setArchivo(UploadedFile archivo) {
        this.archivo = archivo;
    }

    public TipoArchivo getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(TipoArchivo tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    public List getListaTipoArchivo() {
        return listaTipoArchivo;
    }

    public void setListaTipoArchivo(List listaTipoArchivo) {
        this.listaTipoArchivo = listaTipoArchivo;
    }

    public SelectItem[] getTipoArchivoItem() {
        return tipoArchivoItem;
    }

    public void setTipoArchivoItem(SelectItem[] tipoArchivoItem) {
        this.tipoArchivoItem = tipoArchivoItem;
    }

    public boolean isNoArchivos() {
        return noArchivos;
    }

    public void setNoArchivos(boolean noArchivos) {
        this.noArchivos = noArchivos;
    }

    public boolean isBArtistica() {
        return bArtistica;
    }

    public boolean isBFormatos() {
        bFormatos = false;
        if (evaluador != null) {
            if (evaluador.getListaProyectosEvaluador() != null && evaluador.getListaProyectosEvaluador().size() > 0) {
                bFormatos = true;
            }
        } else {
            bFormatos = false;
        }
        return bFormatos;
    }

    public void setbFormatos(boolean bFormatos) {
        this.bFormatos = bFormatos;
    }

    public void setBArtistica(boolean artistica) {
        bArtistica = artistica;
    }

    public boolean isMostrarEstoySeguro() {
        return mostrarEstoySeguro;
    }

    public void setMostrarEstoySeguro(boolean mostrarEstoySeguro) {
        this.mostrarEstoySeguro = mostrarEstoySeguro;
    }

    public boolean isMostrarBotonesAp() {
        return mostrarBotonesAp;
    }

    public void setMostrarBotonesAp(boolean mostrarBotonesAp) {
        this.mostrarBotonesAp = mostrarBotonesAp;
    }

    public ProyectoEvaluador getProyetoSeleccionado() {
        return proyetoSeleccionado;
    }

    public void setProyetoSeleccionado(ProyectoEvaluador proyetoSeleccionado) {
        this.proyetoSeleccionado = proyetoSeleccionado;
    }

    public boolean isEsExtensionSolidaria() {
        return esExtensionSolidaria;
    }

    public void setEsExtensionSolidaria(boolean esExtensionSolidaria) {
        this.esExtensionSolidaria = esExtensionSolidaria;
    }

    public void guardarArchivo(FileUploadEvent event) {

        String proyectoEvaluador2 = "";
        Map map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Object o = (Object) map.get("proyectoEvaluadorId");
        proyectoEvaluador2 = (String) o;

        System.out.println(proyectoEvaluador2);
    }

}
