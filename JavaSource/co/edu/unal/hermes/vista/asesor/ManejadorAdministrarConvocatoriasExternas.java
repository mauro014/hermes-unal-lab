/**
 * @author Martha Liliana Correa O.
 * @date 24/08/2015
 */

package co.edu.unal.hermes.vista.asesor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.ConvocatoriaExterna;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.TerminosConvocatoriaExterna;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorAdministrarConvocatoriasExternas extends ManejadorBase {

    /**
     * 
     */
    private static final long serialVersionUID = 4028447806420511746L;
    private ConvocatoriaExterna convocatoriaSeleccionada;
    private List<ConvocatoriaExterna> listaConvocatoriasExternas;
    private List<FuenteFinanciacion> listaEntidadesExternas;
    private List<ConvocatoriaExterna> filteredConvocatoriasExternas;
    private Long tamanoLista;
    private SelectItem[] estadoConvocatoriasItems = { new SelectItem("A", "Activa"), new SelectItem("I", "Inactiva"),
            new SelectItem("S", "En solicitud") };
    private ArrayList<SelectItem> listaEntidadesItem;
    private String estadoConvocatoria;
    private String entidadSeleccionada;
    private TerminosConvocatoriaExterna terminosSeleccionados;
    private List<TerminosConvocatoriaExterna> listaTerminosReferencia;
    private TerminosConvocatoriaExterna terminosReferencia;
    private boolean esConsultaTerminos;

    public ManejadorAdministrarConvocatoriasExternas() {
        // Inicialización de valores
        personaActual = (Persona) sesion.getAttribute("persona");
        listaConvocatoriasExternas = new ArrayList<ConvocatoriaExterna>();
        cargarEntidades();
    }

    /**
     * Método para listar las entidades externas, las cuales pueden ofertar
     * convocatorias para proyectos de investigación.
     */
    public void cargarEntidades() {

        listaEntidadesItem = new ArrayList<SelectItem>();

        String hqlentidades = "select e from FuenteFinanciacion e "
                + "where e.internaExterna = 'E' order by e.descripcion asc";
        listaEntidadesExternas = servicioGeneral.obtenerObjetos(FuenteFinanciacion.class, hqlentidades);

        if (!esListaVacia(listaEntidadesExternas)) {
            for (int i = 0; i < listaEntidadesExternas.size(); i++) {

                FuenteFinanciacion entidadExterna = (FuenteFinanciacion) listaEntidadesExternas.get(i);
                listaEntidadesItem.add(new SelectItem(entidadExterna.getId(), entidadExterna.getDescripcion()));
            }
        }
    }

    /**
     * Método para consultar las convocatorias asociadas a la entidad externa de
     * acuerdo a estado seleccionado, se listan en forma descendente de acuerdo
     * con la fecha de cierre
     */
    public void consultarConvocatorias() {
        if (entidadSeleccionada == null || "".equals(entidadSeleccionada.trim())) {
            mensajeError("Debe seleccionar primero una entidad convocante");

        } else if (estadoConvocatoria == null || "".equals(estadoConvocatoria.trim())) {
            mensajeError("Debe seleccionar un estado para las convocatorias");
        }
        listaConvocatoriasExternas = servicioGeneral.obtenerObjetosLimitado(ConvocatoriaExterna.class,
                "select #id ce.id, #nombre ce.nombre, #numero " + "ce.numero, #fechaCierre ce.fechaCierre, "
                        + "#fechaApertura ce.fechaApertura, " + "#estado ce.estado " + "from ConvocatoriaExterna ce "
                        + "where ce.estado in ('" + estadoConvocatoria + "') and ce.entidad.id = '"
                        + entidadSeleccionada + "' " + "order by ce.fechaCierre desc");
        tamanoLista = (long) listaConvocatoriasExternas.size();
    }

    /**
     * Método que remite al formulario para crear una nueva convocatoria externa
     * 
     * @return
     */
    public String crearConvocatoriaExterna() {
        sesion.setAttribute("idConvocatoriaExterna", null);
        sesion.setAttribute("esEdicion", false);
        sesion.setAttribute("esConsulta", false);
        sesion.removeAttribute("manejadorCrearEditarConvocatoriasExternas");
        return "crearEditarConvocatoriaExterna";
    }

    /**
     * Método para editar la convocatoria externa seleccionada
     * 
     * @return
     */
    public String editarConvocatoriaExterna() {
        sesion.setAttribute("idConvocatoriaExterna", null);
        sesion.setAttribute("idConvocatoriaExterna", convocatoriaSeleccionada.getId());
        sesion.setAttribute("esEdicion", true);
        sesion.setAttribute("esConsulta", false);
        sesion.removeAttribute("manejadorCrearEditarConvocatoriasExternas");
        return "crearEditarConvocatoriaExterna";
    }

    /**
     * Método para consultar la convocatoria externa seleccionada
     * 
     * @return
     */
    public String consultarConvocatoriaExterna() {
        sesion.setAttribute("idConvocatoriaExterna", null);
        sesion.setAttribute("idConvocatoriaExterna", convocatoriaSeleccionada.getId());
        sesion.setAttribute("esConsulta", true);
        sesion.setAttribute("esEdicion", false);
        sesion.removeAttribute("manejadorCrearEditarConvocatoriasExternas");
        return "crearEditarConvocatoriaExterna";
    }

    /**
     * Método que remite al formulario para listar las convocatorias externas
     * por entidad y estado.
     * 
     * @return
     */
    public String administrarConvocatoriasExternas() {
        sesion.setAttribute("idConvocatoriaExterna", null);
        sesion.setAttribute("esConsulta", false);
        sesion.setAttribute("esEdicion", false);
        sesion.removeAttribute("manejadorCrearEditarConvocatoriasExternas");
        sesion.removeAttribute("manejadorAdministrarConvocatoriasExternas");
        return "administrarConvocatoriaExterna";
    }

    /**
     * Método que remite a la lista de términos de referencia que se han creado
     * ya sea para consultarlos o adicionar unos nuevos.
     * 
     * @return
     */
    public String administrarTerminosReferencia() {
        sesion.setAttribute("idConvocatoriaExterna", null);
        sesion.setAttribute("esConsulta", false);
        sesion.setAttribute("esEdicion", false);
        sesion.removeAttribute("manejadorCrearEditarConvocatoriasExternas");
        return "administrarTerminosReferencia";
    }

    /**
     * Consulta de histórico de lista de términos de referencia
     * 
     * @return
     */
    public List<TerminosConvocatoriaExterna> getListaTerminosReferencia() {
        listaTerminosReferencia = new ArrayList<TerminosConvocatoriaExterna>();
        String consulta = "select tr from TerminosConvocatoriaExterna tr order " + "by tr.vigenciaTermina desc";
        listaTerminosReferencia = servicioGeneral.obtenerObjetos(TerminosConvocatoriaExterna.class, consulta);
        return listaTerminosReferencia;
    }

    /**
     * Consultar términos de referencia seleccionados
     * 
     * @return
     */
    public String consultarTerminosReferencia() {
        esConsultaTerminos = true;
        return "crearConsultarTerminosReferencia";
    }

    /**
     * Agregar nuevos términos de referencia
     * 
     * @return
     */
    public String agregarNuevosTerminos() {
        esConsultaTerminos = false;
        terminosReferencia = new TerminosConvocatoriaExterna();
        return "crearConsultarTerminosReferencia";
    }

    /**
     * Guardar nuevos términos de referencia que han sido creados
     */
    public void guardarTerminosReferencia() {
        // Validación que exista el texto de los nuevos términos de referencia
        if (esCadenaVacia(terminosReferencia.getDescripcion())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Debe ingresar el texto de los " + "términos de referencia.", ""));
            return;
        } else {
            // Se consultan los términos que estaban vigentes, para
            // desactivarlos y guardar fecha y persona.
            String consulta = "select tr from TerminosConvocatoriaExterna tr "
                    + "where tr.estado = 'A' order by tr.vigenciaTermina desc";
            List<TerminosConvocatoriaExterna> listaTerminosVigentes = servicioGeneral
                    .obtenerObjetos(TerminosConvocatoriaExterna.class, consulta);
            if (!esListaVacia(listaTerminosVigentes)) {
                TerminosConvocatoriaExterna oldTerms = (TerminosConvocatoriaExterna) listaTerminosVigentes.get(0);
                oldTerms.setEstado("I");
                oldTerms.setVigenciaTermina(new Date());
                oldTerms.setPersonaCierra(personaActual);
                servicioGeneral.guardarObjeto(oldTerms);
            }
            // Se guardan los nuevos términos de referencia y se asigna fecha de
            // vigencia de inicio y persona, se dejan activos
            terminosReferencia.setVigenciaInicio(new Date());
            terminosReferencia.setPersonaAbre(personaActual);
            terminosReferencia.setEstado("A");
            servicioGeneral.guardarObjeto(terminosReferencia);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Los términos de referencia se han ingresado "
                            + "y han quedado vigentes a partir de la " + "fecha actual.", ""));

        }
    }

    public ConvocatoriaExterna getConvocatoriaSeleccionada() {
        return convocatoriaSeleccionada;
    }

    public void setConvocatoriaSeleccionada(ConvocatoriaExterna convocatoriaSeleccionada) {
        this.convocatoriaSeleccionada = convocatoriaSeleccionada;
    }

    public List<ConvocatoriaExterna> getListaConvocatoriasExternas() {
        return listaConvocatoriasExternas;
    }

    public void setListaConvocatoriasExternas(List<ConvocatoriaExterna> listaConvocatoriasExternas) {
        this.listaConvocatoriasExternas = listaConvocatoriasExternas;
    }

    public List<ConvocatoriaExterna> getFilteredConvocatoriasExternas() {
        return filteredConvocatoriasExternas;
    }

    public void setFilteredConvocatoriasExternas(List<ConvocatoriaExterna> filteredConvocatoriasExternas) {
        this.filteredConvocatoriasExternas = filteredConvocatoriasExternas;
    }

    public Long getTamanoLista() {
        return tamanoLista;
    }

    public void setTamanoLista(Long tamanoLista) {
        this.tamanoLista = tamanoLista;
    }

    public SelectItem[] getEstadoConvocatoriasItems() {
        return estadoConvocatoriasItems;
    }

    public void setEstadoConvocatoriasItems(SelectItem[] estadoConvocatoriasItems) {
        this.estadoConvocatoriasItems = estadoConvocatoriasItems;
    }

    public List<FuenteFinanciacion> getListaEntidadesExternas() {
        return listaEntidadesExternas;
    }

    public void setListaEntidadesExternas(List<FuenteFinanciacion> listaEntidadesExternas) {
        this.listaEntidadesExternas = listaEntidadesExternas;
    }

    public ArrayList<SelectItem> getListaEntidadesItem() {
        return listaEntidadesItem;
    }

    public void setListaEntidadesItem(ArrayList<SelectItem> listaEntidadesItem) {
        this.listaEntidadesItem = listaEntidadesItem;
    }

    public String getEstadoConvocatoria() {
        return estadoConvocatoria;
    }

    public void setEstadoConvocatoria(String estadoConvocatoria) {
        this.estadoConvocatoria = estadoConvocatoria;
    }

    public String getEntidadSeleccionada() {
        return entidadSeleccionada;
    }

    public void setEntidadSeleccionada(String entidadSeleccionada) {
        this.entidadSeleccionada = entidadSeleccionada;
    }

    public TerminosConvocatoriaExterna getTerminosSeleccionados() {
        return terminosSeleccionados;
    }

    public void setTerminosSeleccionados(TerminosConvocatoriaExterna terminosSeleccionados) {
        this.terminosSeleccionados = terminosSeleccionados;
    }

    public void setListaTerminosReferencia(List<TerminosConvocatoriaExterna> listaTerminosReferencia) {
        this.listaTerminosReferencia = listaTerminosReferencia;
    }

    public TerminosConvocatoriaExterna getTerminosReferencia() {
        return terminosReferencia;
    }

    public void setTerminosReferencia(TerminosConvocatoriaExterna terminosReferencia) {
        this.terminosReferencia = terminosReferencia;
    }

    public boolean isEsConsultaTerminos() {
        return esConsultaTerminos;
    }

    public void setEsConsultaTerminos(boolean esConsultaTerminos) {
        this.esConsultaTerminos = esConsultaTerminos;
    }

}
