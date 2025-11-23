package co.edu.unal.hermes.vista.proyectos;

import java.io.IOException;
import java.util.List;

import javax.faces.context.FacesContext;

import co.edu.unal.hermes.modelo.ProyectoInforme;
import co.edu.unal.hermes.modelo.TipoInforme;
import co.edu.unal.hermes.modelo.TipoModalidad;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * The Class ManejadorAvalInformeBase.
 */
public class ManejadorAvalInformeBase extends ManejadorBase {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5718110152710401217L;
    
    /** The lista informe. */
    protected List<ProyectoInforme> listaInforme;
    
    /** The filteredinformes. */
    private List<ProyectoInforme> filteredinformes;
    
    /** The informe seleccionado. */
    private ProyectoInforme informeSeleccionado;
    
    /**
     * Revisar informe.
     *
     * @return the string
     */
    protected String revisarInforme() {

        List<ProyectoInforme> listaInformes = servicioGeneral.obtenerObjetos(ProyectoInforme.class,
                "from ProyectoInforme  where id ='" + informeSeleccionado.getId() + "'");

        if (!esListaVacia(listaInformes)) {
            ProyectoInforme pin = (ProyectoInforme) listaInformes.get(0);
            
            sesion.setAttribute("tipoInformeParam", pin.getTipoInforme().getId());
            sesion.setAttribute("idProyecto", pin.getProyecto().getId());
            sesion.setAttribute("idInforme", pin.getId());
            sesion.setAttribute("tipoAccion", ProyectoInforme.TIPO_ACCION_CONSULTA);
            sesion.setAttribute("tipoAccionBancoProyectos", ProyectoInforme.TIPO_ACCION_CONSULTA);
            sesion.setAttribute("esVIF", new Long("0"));
            sesion.setAttribute("solicitudRenovacion", false);
            sesion.removeAttribute("proyectoRenovacion");
            sesion.removeAttribute("manejadorAvalInformeFacultad");
            sesion.removeAttribute("manejadorAvalInformeUab");
            agregarReglaNavegacion("detalleInforme", "ManejadorPrincipalInforme");

            super.sesion.setAttribute("anteriorManejador", "ManejadorAvalInformeFacultad");

            if (pin.getProyecto() != null && pin.getProyecto().getModalidad() != null
                    && pin.getProyecto().getModalidad().getTipo() != null
                    && pin.getProyecto().getModalidad().getTipo().getId().equals(TipoModalidad.BANCO_PROYECTOS)
                    && pin.getTipoInforme().getId().equals(TipoInforme.INFORME_FINAL)) {
                sesion.removeAttribute("manejadorRegistroInformeBancoProyectos");
                return "detalleInformeBancoProyectosVIF";
            } else {
                sesion.removeAttribute("manejadorRegistroInforme");
                return "detalleInformeVIF";
            }
        }
        return "";
    }

    /**
     * Imprimir informe.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void imprimirInforme() throws IOException {

        ReporteBirt r = new ReporteBirt();
        r.adicionarParametro("id", informeSeleccionado.getId().toString());

        List<ProyectoInforme> listaInformes = servicioGeneral.obtenerObjetos(ProyectoInforme.class,
                "from ProyectoInforme  where id ='" + informeSeleccionado.getId() + "'");

        if (!esListaVacia(listaInformes)) {

            ProyectoInforme proyectoInformeSeleccionado = (ProyectoInforme) listaInformes.get(0);

            if (proyectoInformeSeleccionado.getProyecto().getModalidad() != null
                    && proyectoInformeSeleccionado.getProyecto().getModalidad().getTipo() != null
                    && proyectoInformeSeleccionado.getProyecto().getModalidad().getTipo().getId()
                            .equals(TipoModalidad.BANCO_PROYECTOS)) {
                r.setNombreReporte("/informes/informeFinalBancoProyectos");
            } else {
                r.setNombreReporte("/informes/informeFinal");
            }
        }
        r.setFormato(ReporteBirt.FORMATO_PDF);
        sesion.setAttribute("reporte", r);

        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().dispatch("/ReporteEngineServlet");
        context.responseComplete();
    }

    /**
     * Gets the tamano lista.
     *
     * @return the tamano lista
     */
    public int getTamanoLista() {
        if (listaInforme != null) {
            return listaInforme.size();
        } else {
            return 0;
        }
    }

    /**
     * Gets the filteredinformes.
     *
     * @return the filteredinformes
     */
    public List<ProyectoInforme> getFilteredinformes() {
        return filteredinformes;
    }

    /**
     * Sets the filteredinformes.
     *
     * @param filteredinformes the new filteredinformes
     */
    public void setFilteredinformes(List<ProyectoInforme> filteredinformes) {
        this.filteredinformes = filteredinformes;
    }

    /**
     * Gets the lista informe.
     *
     * @return the lista informe
     */
    public List<ProyectoInforme> getListaInforme() {
        return listaInforme;
    }

    /**
     * Gets the informe seleccionado.
     *
     * @return the informeSeleccionado
     */
    public ProyectoInforme getInformeSeleccionado() {
        return informeSeleccionado;
    }

    /**
     * Sets the informe seleccionado.
     *
     * @param informeSeleccionado the informeSeleccionado to set
     */
    public void setInformeSeleccionado(ProyectoInforme informeSeleccionado) {
        this.informeSeleccionado = informeSeleccionado;
    }

}
