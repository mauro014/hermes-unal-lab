package co.edu.unal.hermes.vista;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.ArchivoConceptoContrato;
import co.edu.unal.hermes.modelo.ConceptoContratoBiodiversidad;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.TipoTramiteBiodiversidad;

public class ManejadorBasePermisosMarco extends ManejadorBase {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public final String ARCHIVO_DESDE_INVESTIGADOR = "I";
    public final String ARCHIVO_DESDE_COORDINADOR = "C";

    protected SelectItem[] tienePermisoItem = { new SelectItem("", "Todos"), new SelectItem("S", "Sí"),
            new SelectItem("N", "No") };
    protected SelectItem[] tipoSolicitudItem = { new SelectItem("", "Todos"), new SelectItem("I", "Inclusión"),
            new SelectItem("E", "Exclusión") };
    protected SelectItem[] tipoContratoItem = { new SelectItem("", "Todos"), new SelectItem("21", "Individual"),
            new SelectItem("22", "Marco") };
    protected SelectItem[] estadoConceptoItems = { new SelectItem("C", "Devuelto para correcciones"),
            new SelectItem("T", "En trámite"), new SelectItem("N", "No requiere suscripción"),
            new SelectItem("R", "Rechazado"), new SelectItem("A", "Requiere suscripción") };

    protected List<ArchivoConceptoContrato> listaArchivos;
    protected List<ArchivoConceptoContrato> listaArchivosCoordinador;
    protected ArchivoConceptoContrato archivoSeleccionado;
    protected Long idConcepto;
    protected ConceptoContratoBiodiversidad concepto;
    protected String decisionConcepto;
    protected InvestigadorInterno investigadorActual;

    private List<TipoTramiteBiodiversidad> listaTramitesBiodiversidad;
    protected boolean esEncargadoInclusionPM;
    protected boolean esEncargadoSolicitudConceptoAR;
    protected String dependenciaTramitaPermiso;
    protected String dependenciaTramitaContrato;

    public ManejadorBasePermisosMarco() {
        listaArchivos = new ArrayList<ArchivoConceptoContrato>();
        listaArchivosCoordinador = new ArrayList<ArchivoConceptoContrato>();
        archivoSeleccionado = new ArchivoConceptoContrato();
        concepto = new ConceptoContratoBiodiversidad();
        personaActual = (Persona) sesion.getAttribute("persona");
        investigadorActual = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());
    }

    public void evaluarEncargados() {
        dependenciaTramitaPermiso = "";
        dependenciaTramitaContrato = "";
        esEncargadoInclusionPM = false;
        esEncargadoSolicitudConceptoAR = false;
        listaTramitesBiodiversidad = servicioBiodiversidad.obtenerListaTramitesBiodiversidadPersona(personaActual);

        if (esListaVacia(listaTramitesBiodiversidad)) {
            return;
        }
        for (int i = 0; i < listaTramitesBiodiversidad.size(); i++) {
            TipoTramiteBiodiversidad tram = (TipoTramiteBiodiversidad) listaTramitesBiodiversidad.get(i);
            if (tram.getId().equals(TipoTramiteBiodiversidad.INCLUSION_INVESTIGADOR_PM)) {
                esEncargadoInclusionPM = true;
                dependenciaTramitaPermiso = tram.getNivelTramite();
            } else if (tram.getId().equals(TipoTramiteBiodiversidad.CONCEPTO_NECESIDAD_CARG)) {
                esEncargadoSolicitudConceptoAR = true;
                dependenciaTramitaContrato = tram.getNivelTramite();
            }
        }
    }

    public void consultarInformacionSolicitudConcepto() {
        concepto = new ConceptoContratoBiodiversidad();

        List<ConceptoContratoBiodiversidad> lista = servicioGeneral.obtenerObjetos(ConceptoContratoBiodiversidad.class,
                "select c from ConceptoContratoBiodiversidad c where c.id = '" + getIdConcepto() + "'");
        if (!esListaVacia(lista)) {
            concepto = lista.get(0);
            consultarArchivosConcepto(concepto.getId().toString());
            decisionConcepto = concepto.getEstado();
        }
    }

    public void consultarArchivosConcepto(String idConcepto) {
        listaArchivos = new ArrayList<ArchivoConceptoContrato>();
        String consultaArchivos = "select a from ArchivoConceptoContrato a where a.concepto.id = '" + idConcepto
                + "' and a.estado = 'V'";
        List<ArchivoConceptoContrato> listaA = (List<ArchivoConceptoContrato>) servicioGeneral
                .obtenerObjetos(ArchivoConceptoContrato.class, consultaArchivos);
        if (!esListaVacia(listaA)) {
            listaArchivos.addAll(listaA);
        }

        listaArchivosCoordinador = new ArrayList<ArchivoConceptoContrato>();
        String consultaArchivosCoordinador = "select a from ArchivoConceptoContrato a where a.concepto.id = '"
                + idConcepto + "' and a.estado = 'C'";
        List<ArchivoConceptoContrato> listaAC = (List<ArchivoConceptoContrato>) servicioGeneral
                .obtenerObjetos(ArchivoConceptoContrato.class, consultaArchivosCoordinador);
        if (!esListaVacia(listaAC)) {
            listaArchivosCoordinador.addAll(listaAC);
        }
    }

    public void descargarArchivo() {
        if (archivoSeleccionado != null) {
            descargarArchivoConceptoContrato(archivoSeleccionado);
        }
    }

    public void eliminarArchivoConcepto(String origen) {

        if (origen.equals(ARCHIVO_DESDE_INVESTIGADOR)) {
            listaArchivos.remove(archivoSeleccionado);
        } else {
            listaArchivosCoordinador.remove(archivoSeleccionado);
        }
        archivoSeleccionado.setFechaElimina(new Date());
        archivoSeleccionado.setEstado("B");
        Persona personaElimina = (Persona) sesion.getAttribute("persona");
        archivoSeleccionado.setPersonaElimina(personaElimina);
        servicioGeneral.guardarObjeto(archivoSeleccionado);
    }

    public void adjuntarArchivoConcepto(FileUploadEvent event, String origen) {
        UploadedFile archivoCargado = event.getFile();
        ArchivoConceptoContrato a = insertarArchivoConceptoContrato(0, archivoCargado);
        if (a != null) {
            if (origen.equals(ARCHIVO_DESDE_INVESTIGADOR)) {
                listaArchivos.add(a);
            } else {
                listaArchivosCoordinador.add(a);
            }
        }
    }

    public SelectItem[] getTienePermisoItem() {
        return tienePermisoItem;
    }

    public void setTienePermisoItem(SelectItem[] tienePermisoItem) {
        this.tienePermisoItem = tienePermisoItem;
    }

    public SelectItem[] getTipoSolicitudItem() {
        return tipoSolicitudItem;
    }

    public void setTipoSolicitudItem(SelectItem[] tipoSolicitudItem) {
        this.tipoSolicitudItem = tipoSolicitudItem;
    }

    public List<ArchivoConceptoContrato> getListaArchivos() {
        return listaArchivos;
    }

    public void setListaArchivos(List<ArchivoConceptoContrato> listaArchivos) {
        this.listaArchivos = listaArchivos;
    }

    public ArchivoConceptoContrato getArchivoSeleccionado() {
        return archivoSeleccionado;
    }

    public void setArchivoSeleccionado(ArchivoConceptoContrato archivoSeleccionado) {
        this.archivoSeleccionado = archivoSeleccionado;
    }

    public SelectItem[] getTipoContratoItem() {
        return tipoContratoItem;
    }

    public void setTipoContratoItem(SelectItem[] tipoContratoItem) {
        this.tipoContratoItem = tipoContratoItem;
    }

    public List<ArchivoConceptoContrato> getListaArchivosCoordinador() {
        return listaArchivosCoordinador;
    }

    public void setListaArchivosCoordinador(List<ArchivoConceptoContrato> listaArchivosCoordinador) {
        this.listaArchivosCoordinador = listaArchivosCoordinador;
    }

    public Long getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(Long idConcepto) {
        this.idConcepto = idConcepto;
    }

    public ConceptoContratoBiodiversidad getConcepto() {
        return concepto;
    }

    public void setConcepto(ConceptoContratoBiodiversidad concepto) {
        this.concepto = concepto;
    }

    public SelectItem[] getEstadoConceptoItems() {
        return estadoConceptoItems;
    }

    public void setEstadoConceptoItems(SelectItem[] estadoConceptoItems) {
        this.estadoConceptoItems = estadoConceptoItems;
    }

    public String getDecisionConcepto() {
        return decisionConcepto;
    }

    public void setDecisionConcepto(String decisionConcepto) {
        this.decisionConcepto = decisionConcepto;
    }

    public List<TipoTramiteBiodiversidad> getListaTramitesBiodiversidad() {
        return listaTramitesBiodiversidad;
    }

    public void setListaTramitesBiodiversidad(List<TipoTramiteBiodiversidad> listaTramitesBiodiversidad) {
        this.listaTramitesBiodiversidad = listaTramitesBiodiversidad;
    }

    public InvestigadorInterno getInvestigadorActual() {
        return investigadorActual;
    }

    public void setInvestigadorActual(InvestigadorInterno investigadorActual) {
        this.investigadorActual = investigadorActual;
    }

    public boolean isEsEncargadoInclusionPM() {
        return esEncargadoInclusionPM;
    }

    public void setEsEncargadoInclusionPM(boolean esEncargadoInclusionPM) {
        this.esEncargadoInclusionPM = esEncargadoInclusionPM;
    }

    public boolean isEsEncargadoSolicitudConceptoAR() {
        return esEncargadoSolicitudConceptoAR;
    }

    public void setEsEncargadoSolicitudConceptoAR(boolean esEncargadoSolicitudConceptoAR) {
        this.esEncargadoSolicitudConceptoAR = esEncargadoSolicitudConceptoAR;
    }

    public String getDependenciaTramitaPermiso() {
        return dependenciaTramitaPermiso;
    }

    public void setDependenciaTramitaPermiso(String dependenciaTramitaPermiso) {
        this.dependenciaTramitaPermiso = dependenciaTramitaPermiso;
    }

    public String getDependenciaTramitaContrato() {
        return dependenciaTramitaContrato;
    }

    public void setDependenciaTramitaContrato(String dependenciaTramitaContrato) {
        this.dependenciaTramitaContrato = dependenciaTramitaContrato;
    }

}