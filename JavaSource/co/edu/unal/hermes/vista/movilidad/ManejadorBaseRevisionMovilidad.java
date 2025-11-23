package co.edu.unal.hermes.vista.movilidad;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;

import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.MovilidadDocentesArtes;
import co.edu.unal.hermes.modelo.MovilidadDocentesExterior;
import co.edu.unal.hermes.modelo.MovilidadEstudiantesArtes;
import co.edu.unal.hermes.modelo.MovilidadEstudiantesPosgrado;
import co.edu.unal.hermes.modelo.MovilidadVisitanteExterior;
import co.edu.unal.hermes.modelo.MovilidadVisitantesArtes;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.vista.movilidad.aprobacion.ConvocatoriaMovilidadVista;

/**
 * The Class ManejadorBaseMovilidad.
 */
public abstract class ManejadorBaseRevisionMovilidad extends ManejadorBaseMovilidad {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -7176318323244909461L;

    /** The movilidad visitante modificacion. */
    protected MovilidadVisitanteExterior movilidadVisitanteModificacion = new MovilidadVisitanteExterior();

    /** The movilidad eventos modificacion. */
    protected MovilidadDocentesExterior movilidadEventosModificacion = new MovilidadDocentesExterior();

    /** The movilidad eventos modificacion. */
    protected MovilidadDocentesArtes movilidadDocentesArtesModificacion = new MovilidadDocentesArtes();

    /** The movilidad eventos modificacion. */
    protected MovilidadEstudiantesArtes movilidadEstudiantesArtesModificacion = new MovilidadEstudiantesArtes();

    /** The movilidad estudiantes modificacion. */
    protected MovilidadEstudiantesPosgrado movilidadEstudiantesModificacion = new MovilidadEstudiantesPosgrado();
    
    /** The movilidad visitantes artes. */
    protected MovilidadVisitantesArtes movilidadVisitantesArtesModificacion = new MovilidadVisitantesArtes();

    /** The movilidad vista seleccionada. */
    protected ConvocatoriaMovilidadVista movilidadVistaSeleccionada;

    /** The lista movilidades general. */
    protected List<ConvocatoriaMovilidadVista> listaMovilidadesGeneral;

    /** The lista movilidades evento. */
    protected List<MovilidadDocentesExterior> listaMovilidadesEvento;

    /** The lista movilidades visitante. */
    protected List<MovilidadVisitanteExterior> listaMovilidadesVisitante;

    /** The lista movilidades estudiante posgrado. */
    protected List<MovilidadEstudiantesPosgrado> listaMovilidadesEstudiantePosgrado;

    /** The lista movilidades docentes artes. */
    protected List<MovilidadDocentesArtes> listaMovilidadesDocentesArtes;

    /** The lista movilidades estudiantes artes. */
    protected List<MovilidadEstudiantesArtes> listaMovilidadesEstudiantesArtes;

    /** The lista movilidades visitantes artes. */
    protected List<MovilidadVisitantesArtes> listaMovilidadesVisitantesArtes;

    /** The movilidad eventos seleccionada. */
    protected MovilidadDocentesExterior movilidadEventosSeleccionada;

    /** The movilidad visitante seleccionada. */
    protected MovilidadVisitanteExterior movilidadVisitanteSeleccionada;

    /** The movilidad docente artes seleccionada. */
    protected MovilidadDocentesArtes movilidadDocenteArtesSeleccionada;

    /** The movilidad estudiantes seleccionada. */
    protected MovilidadEstudiantesPosgrado movilidadEstudiantesSeleccionada;

    /** The movilidad estudiantes artes seleccionada. */
    protected MovilidadEstudiantesArtes movilidadEstudiantesArtesSeleccionada;

    /** The movilidad residencias artes seleccionada. */
    protected MovilidadVisitantesArtes movilidadResidenciasArtesSeleccionada;

    /** The listado convocatorias. */
    protected List<ConvocatoriaPadre> listadoConvocatoriasPadre;

    /** The lista convocatorias padre items. */
    protected List<SelectItem> listaConvocatoriasPadreItems;

    /** The lista convocatorias padre items. */
    protected List<SelectItem> listaConvocatoriasItems;

    /** The convocatoria padre filtro. */
    protected String convocatoriaPadreFiltro;

    /** The convocatoria filtro. */
    protected String convocatoriaFiltro;
    
    

    /**
     * Instantiates a new manejador base revision movilidad.
     */
    public ManejadorBaseRevisionMovilidad() {
        personaActual = (Persona) sesion.getAttribute("persona");
        cargarListasMovilidades();        
    }

    /**
     * Gets the lista movilidades general.
     *
     * @return the listaMovilidadesGeneral
     */
    public List<ConvocatoriaMovilidadVista> getListaMovilidadesGeneral() {
        return listaMovilidadesGeneral;
    }

    /**
     * Gets the lista convocatorias padre items.
     *
     * @return the listaConvocatoriasItems
     */
    public List<SelectItem> getListaConvocatoriasPadreItems() {
        return listaConvocatoriasPadreItems;
    }

    /**
     * Gets the convocatoria padre filtro.
     *
     * @return the convocatoriaPadreFiltro
     */
    public String getConvocatoriaPadreFiltro() {
        return convocatoriaPadreFiltro;
    }

    /**
     * Sets the convocatoria padre filtro.
     *
     * @param convocatoriaPadreFiltro
     *            the convocatoriaPadreFiltro to set
     */
    public void setConvocatoriaPadreFiltro(String convocatoriaPadreFiltro) {
        this.convocatoriaPadreFiltro = convocatoriaPadreFiltro;
    }

    /**
     * Gets the convocatoria filtro.
     *
     * @return the convocatoriaFiltro
     */
    public String getConvocatoriaFiltro() {
        return convocatoriaFiltro;
    }

    /**
     * Sets the convocatoria filtro.
     *
     * @param convocatoriaFiltro
     *            the convocatoriaFiltro to set
     */
    public void setConvocatoriaFiltro(String convocatoriaFiltro) {
        this.convocatoriaFiltro = convocatoriaFiltro;
    }

    /**
     * Gets the lista convocatorias items.
     *
     * @return the listaConvocatoriasItems
     */
    public List<SelectItem> getListaConvocatoriasItems() {
        return listaConvocatoriasItems;
    }

    /**
     * Sets the movilidad eventos seleccionada.
     *
     * @param movilidadEventosSeleccionada
     *            the new movilidad eventos seleccionada
     */
    public void setMovilidadEventosSeleccionada(MovilidadDocentesExterior movilidadEventosSeleccionada) {
        this.movilidadEventosSeleccionada = movilidadEventosSeleccionada;
    }

    /**
     * Gets the movilidad eventos seleccionada.
     *
     * @return the movilidad eventos seleccionada
     */
    public MovilidadDocentesExterior getMovilidadEventosSeleccionada() {
        return movilidadEventosSeleccionada;
    }

    /**
     * Sets the movilidad visitante seleccionada.
     *
     * @param movilidadVisitanteSeleccionada
     *            the new movilidad visitante seleccionada
     */
    public void setMovilidadVisitanteSeleccionada(MovilidadVisitanteExterior movilidadVisitanteSeleccionada) {
        this.movilidadVisitanteSeleccionada = movilidadVisitanteSeleccionada;
    }

    /**
     * Gets the movilidad visitante seleccionada.
     *
     * @return the movilidad visitante seleccionada
     */
    public MovilidadVisitanteExterior getMovilidadVisitanteSeleccionada() {
        return movilidadVisitanteSeleccionada;
    }

    /**
     * Sets the movilidad estudiantes seleccionada.
     *
     * @param movilidadEstudiantesSeleccionada
     *            the new movilidad estudiantes seleccionada
     */
    public void setMovilidadEstudiantesSeleccionada(MovilidadEstudiantesPosgrado movilidadEstudiantesSeleccionada) {
        this.movilidadEstudiantesSeleccionada = movilidadEstudiantesSeleccionada;
    }

    /**
     * Gets the movilidad estudiantes seleccionada.
     *
     * @return the movilidad estudiantes seleccionada
     */
    public MovilidadEstudiantesPosgrado getMovilidadEstudiantesSeleccionada() {
        return movilidadEstudiantesSeleccionada;
    }

    /**
     * Gets the movilidad docente artes seleccionada.
     *
     * @return the movilidad docente artes seleccionada
     */
    public MovilidadDocentesArtes getMovilidadDocenteArtesSeleccionada() {
        return movilidadDocenteArtesSeleccionada;
    }

    /**
     * Sets the movilidad docente artes seleccionada.
     *
     * @param movilidadDocenteArtesSeleccionada
     *            the new movilidad docente artes seleccionada
     */
    public void setMovilidadDocenteArtesSeleccionada(MovilidadDocentesArtes movilidadDocenteArtesSeleccionada) {
        this.movilidadDocenteArtesSeleccionada = movilidadDocenteArtesSeleccionada;
    }

    /**
     * Gets the movilidad estudiantes artes seleccionada.
     *
     * @return the movilidad estudiantes artes seleccionada
     */
    public MovilidadEstudiantesArtes getMovilidadEstudiantesArtesSeleccionada() {
        return movilidadEstudiantesArtesSeleccionada;
    }

    /**
     * Sets the movilidad estudiantes artes seleccionada.
     *
     * @param movilidadEstudiantesArtesSeleccionada
     *            the new movilidad estudiantes artes seleccionada
     */
    public void setMovilidadEstudiantesArtesSeleccionada(
            MovilidadEstudiantesArtes movilidadEstudiantesArtesSeleccionada) {
        this.movilidadEstudiantesArtesSeleccionada = movilidadEstudiantesArtesSeleccionada;
    }

    /**
     * Gets the lista movilidades evento.
     *
     * @return the lista movilidades evento
     */
    public List<MovilidadDocentesExterior> getListaMovilidadesEvento() {
        return listaMovilidadesEvento;
    }

    /**
     * Gets the lista movilidades estudiante posgrado.
     *
     * @return the lista movilidades estudiante posgrado
     */
    public List<MovilidadEstudiantesPosgrado> getListaMovilidadesEstudiantePosgrado() {
        return listaMovilidadesEstudiantePosgrado;
    }

    /**
     * Gets the lista movilidades visitante.
     *
     * @return the lista movilidades visitante
     */
    public List<MovilidadVisitanteExterior> getListaMovilidadesVisitante() {
        return listaMovilidadesVisitante;
    }

    /**
     * Gets the lista movilidades docentes artes.
     *
     * @return the lista movilidades docentes artes
     */
    public List<MovilidadDocentesArtes> getListaMovilidadesDocentesArtes() {
        return listaMovilidadesDocentesArtes;
    }

    /**
     * Gets the lista movilidades estudiantes artes.
     *
     * @return the lista movilidades estudiantes artes
     */
    public List<MovilidadEstudiantesArtes> getListaMovilidadesEstudiantesArtes() {
        return listaMovilidadesEstudiantesArtes;
    }

    /**
     * Gets the lista movilidades visitantes artes.
     *
     * @return the lista movilidades visitantes artes
     */
    public List<MovilidadVisitantesArtes> getListaMovilidadesVisitantesArtes() {
        return listaMovilidadesVisitantesArtes;
    }

    /**
     * Crear movilidad vista visitantes ext.
     */
    protected void crearMovilidadVistaVisitantesExt() {
        if (!esListaVacia(listaMovilidadesVisitante)) {
            Iterator<MovilidadVisitanteExterior> i = listaMovilidadesVisitante.iterator();
            while (i.hasNext()) {
                MovilidadVisitanteExterior movilidadVisitanteExterior = i.next();
                ConvocatoriaMovilidadVista convocatoriaMovilidadVista = new ConvocatoriaMovilidadVista();
                convocatoriaMovilidadVista.setIdSolicitud(movilidadVisitanteExterior.getId().toString());
                convocatoriaMovilidadVista.setFechaSolicitud(movilidadVisitanteExterior.getFechasolicitud());
                convocatoriaMovilidadVista
                        .setNombreSolicitante(movilidadVisitanteExterior.getPersonaInv().getNombreCompleto());
                convocatoriaMovilidadVista
                        .setNombreModalidad(movilidadVisitanteExterior.getTipoMovilidad().getNombreVista());
                convocatoriaMovilidadVista.setTipoMovilidad(movilidadVisitanteExterior.getTipoMovilidad());
                convocatoriaMovilidadVista.setConvocatoria(movilidadVisitanteExterior.getConvocatoria());
                InvestigadorInterno ii = servicioPersona.obtenerInvestigadorInterno(new IdPersona(movilidadVisitanteExterior.getIdPersonaAprobacion(), movilidadVisitanteExterior.getTipoIdPersonaAprobacion()));
                if(ii != null){
                	convocatoriaMovilidadVista.setFacultadRevisionFac(ii.getDependencia().getFacultad().getNombre());
                }else{
                	convocatoriaMovilidadVista.setFacultadRevisionFac("");
                }                
                agregarConvocatoriaLista(movilidadVisitanteExterior.getConvocatoria());
                listaMovilidadesGeneral.add(convocatoriaMovilidadVista);
            }
        }
    }

    /**
     * Crear movilidad vista docentes eventos.
     */
    protected void crearMovilidadVistaDocentesEventos() {
        if (!esListaVacia(listaMovilidadesEvento)) {
            for (int j = 0; j < listaMovilidadesEvento.size(); j++) {
                ConvocatoriaMovilidadVista convocatoriaMovilidadVista = new ConvocatoriaMovilidadVista();
                convocatoriaMovilidadVista.setIdSolicitud(listaMovilidadesEvento.get(j).getId().toString());
                convocatoriaMovilidadVista.setFechaSolicitud(listaMovilidadesEvento.get(j).getFechasolicitud());
                convocatoriaMovilidadVista
                        .setNombreSolicitante(listaMovilidadesEvento.get(j).getPersonaInv().getNombreCompleto());
                convocatoriaMovilidadVista
                        .setNombreModalidad(listaMovilidadesEvento.get(j).getTipoMovilidad().getNombreVista());
                convocatoriaMovilidadVista.setTipoMovilidad(listaMovilidadesEvento.get(j).getTipoMovilidad());
                convocatoriaMovilidadVista.setConvocatoria(listaMovilidadesEvento.get(j).getConvocatoria());
                InvestigadorInterno ii = servicioPersona.obtenerInvestigadorInterno(new IdPersona(listaMovilidadesEvento.get(j).getIdPersonaAprobacion(), listaMovilidadesEvento.get(j).getTipoIdPersonaAprobacion()));
                if(ii != null){
                	convocatoriaMovilidadVista.setFacultadRevisionFac(ii.getDependencia().getFacultad().getNombre());
                }else{
                	convocatoriaMovilidadVista.setFacultadRevisionFac("");
                }
                agregarConvocatoriaLista(listaMovilidadesEvento.get(j).getConvocatoria());
                listaMovilidadesGeneral.add(convocatoriaMovilidadVista);
            }
        }
    }

    /**
     * Crear movilidad vista estudiantes posgrados.
     */
    protected void crearMovilidadVistaEstudiantesPosgrados() {
        if (!esListaVacia(listaMovilidadesEstudiantePosgrado)) {
            for (int j = 0; j < listaMovilidadesEstudiantePosgrado.size(); j++) {
                ConvocatoriaMovilidadVista convocatoriaMovilidadVista = new ConvocatoriaMovilidadVista();
                convocatoriaMovilidadVista.setIdSolicitud(listaMovilidadesEstudiantePosgrado.get(j).getId().toString());
                convocatoriaMovilidadVista
                        .setFechaSolicitud(listaMovilidadesEstudiantePosgrado.get(j).getFechasolicitud());
                convocatoriaMovilidadVista.setNombreSolicitante(
                        listaMovilidadesEstudiantePosgrado.get(j).getPersonaInv().getNombreCompleto());
                convocatoriaMovilidadVista.setNombreModalidad(
                        listaMovilidadesEstudiantePosgrado.get(j).getTipoMovilidad().getNombreVista());
                convocatoriaMovilidadVista
                        .setTipoMovilidad(listaMovilidadesEstudiantePosgrado.get(j).getTipoMovilidad());
                convocatoriaMovilidadVista.setConvocatoria(listaMovilidadesEstudiantePosgrado.get(j).getConvocatoria());
                InvestigadorInterno ii = servicioPersona.obtenerInvestigadorInterno(new IdPersona(listaMovilidadesEstudiantePosgrado.get(j).getIdPersonaAprobacion(), listaMovilidadesEstudiantePosgrado.get(j).getTipoIdPersonaAprobacion()));
                if(ii != null){
                	convocatoriaMovilidadVista.setFacultadRevisionFac(ii.getDependencia().getFacultad().getNombre());
                }else{
                	convocatoriaMovilidadVista.setFacultadRevisionFac("");
                }
                agregarConvocatoriaLista(listaMovilidadesEstudiantePosgrado.get(j).getConvocatoria());
                listaMovilidadesGeneral.add(convocatoriaMovilidadVista);
            }
        }
    }

    /**
     * Crear movilidad vista docentes artes.
     */
    protected void crearMovilidadVistaDocentesArtes() {
        if (!esListaVacia(listaMovilidadesDocentesArtes)) {
            for (int j = 0; j < listaMovilidadesDocentesArtes.size(); j++) {
                ConvocatoriaMovilidadVista convocatoriaMovilidadVista = new ConvocatoriaMovilidadVista();
                convocatoriaMovilidadVista.setIdSolicitud(listaMovilidadesDocentesArtes.get(j).getId().toString());
                convocatoriaMovilidadVista.setFechaSolicitud(listaMovilidadesDocentesArtes.get(j).getFechasolicitud());
                convocatoriaMovilidadVista
                        .setNombreSolicitante(listaMovilidadesDocentesArtes.get(j).getPersonaInv().getNombreCompleto());
                convocatoriaMovilidadVista
                        .setNombreModalidad(listaMovilidadesDocentesArtes.get(j).getTipoMovilidad().getNombreVista());
                convocatoriaMovilidadVista.setTipoMovilidad(listaMovilidadesDocentesArtes.get(j).getTipoMovilidad());
                convocatoriaMovilidadVista.setConvocatoria(listaMovilidadesDocentesArtes.get(j).getConvocatoria());
                InvestigadorInterno ii = servicioPersona.obtenerInvestigadorInterno(new IdPersona(listaMovilidadesDocentesArtes.get(j).getIdPersonaAprobacion(), listaMovilidadesDocentesArtes.get(j).getTipoIdPersonaAprobacion()));
                if(ii != null){
                	convocatoriaMovilidadVista.setFacultadRevisionFac(ii.getDependencia().getFacultad().getNombre());
                }else{
                	convocatoriaMovilidadVista.setFacultadRevisionFac("");
                }
                agregarConvocatoriaLista(listaMovilidadesDocentesArtes.get(j).getConvocatoria());
                listaMovilidadesGeneral.add(convocatoriaMovilidadVista);
            }
        }
    }

    /**
     * Crear movilidad vista estudiantes artes.
     */
    protected void crearMovilidadVistaEstudiantesArtes() {
        if (!esListaVacia(listaMovilidadesEstudiantesArtes)) {
            for (int j = 0; j < listaMovilidadesEstudiantesArtes.size(); j++) {
                ConvocatoriaMovilidadVista convocatoriaMovilidadVista = new ConvocatoriaMovilidadVista();
                convocatoriaMovilidadVista.setIdSolicitud(listaMovilidadesEstudiantesArtes.get(j).getId().toString());
                convocatoriaMovilidadVista
                        .setFechaSolicitud(listaMovilidadesEstudiantesArtes.get(j).getFechasolicitud());
                convocatoriaMovilidadVista.setNombreSolicitante(
                        listaMovilidadesEstudiantesArtes.get(j).getPersonaInv().getNombreCompleto());
                convocatoriaMovilidadVista.setNombreModalidad(
                        listaMovilidadesEstudiantesArtes.get(j).getTipoMovilidad().getNombreVista());
                convocatoriaMovilidadVista.setTipoMovilidad(listaMovilidadesEstudiantesArtes.get(j).getTipoMovilidad());
                convocatoriaMovilidadVista.setConvocatoria(listaMovilidadesEstudiantesArtes.get(j).getConvocatoria());
                InvestigadorInterno ii = servicioPersona.obtenerInvestigadorInterno(new IdPersona(listaMovilidadesEstudiantesArtes.get(j).getIdPersonaAprobacion(), listaMovilidadesEstudiantesArtes.get(j).getTipoIdPersonaAprobacion()));
                if(ii != null){
                	convocatoriaMovilidadVista.setFacultadRevisionFac(ii.getDependencia().getFacultad().getNombre());
                }else{
                	convocatoriaMovilidadVista.setFacultadRevisionFac("");
                }
                agregarConvocatoriaLista(listaMovilidadesEstudiantesArtes.get(j).getConvocatoria());
                listaMovilidadesGeneral.add(convocatoriaMovilidadVista);
            }
        }
    }

    /**
     * Crear movilidad vista estudiantes artes.
     */
    protected void crearMovilidadVistaVisitantesArtes() {
        if (!esListaVacia(listaMovilidadesVisitantesArtes)) {
            for (int j = 0; j < listaMovilidadesVisitantesArtes.size(); j++) {
                ConvocatoriaMovilidadVista convocatoriaMovilidadVista = new ConvocatoriaMovilidadVista();
                convocatoriaMovilidadVista.setIdSolicitud(listaMovilidadesVisitantesArtes.get(j).getId().toString());
                convocatoriaMovilidadVista
                        .setFechaSolicitud(listaMovilidadesVisitantesArtes.get(j).getFechasolicitud());
                convocatoriaMovilidadVista.setNombreSolicitante(
                        listaMovilidadesVisitantesArtes.get(j).getPersonaInv().getNombreCompleto());
                convocatoriaMovilidadVista.setNombreModalidad(
                        listaMovilidadesVisitantesArtes.get(j).getTipoMovilidad().getNombreVista());
                convocatoriaMovilidadVista.setTipoMovilidad(listaMovilidadesVisitantesArtes.get(j).getTipoMovilidad());
                convocatoriaMovilidadVista.setConvocatoria(listaMovilidadesVisitantesArtes.get(j).getConvocatoria());
                InvestigadorInterno ii = servicioPersona.obtenerInvestigadorInterno(new IdPersona(listaMovilidadesVisitantesArtes.get(j).getIdPersonaAprobacion(), listaMovilidadesVisitantesArtes.get(j).getTipoIdPersonaAprobacion()));
                if(ii != null){
                	convocatoriaMovilidadVista.setFacultadRevisionFac(ii.getDependencia().getFacultad().getNombre());
                }else{
                	convocatoriaMovilidadVista.setFacultadRevisionFac("");
                }
                agregarConvocatoriaLista(listaMovilidadesVisitantesArtes.get(j).getConvocatoria());
                listaMovilidadesGeneral.add(convocatoriaMovilidadVista);
            }
        }
    }

    /**
     * Agregar convocatoria lista.
     *
     * @param convocatoria
     *            the convocatoria
     */
    private void agregarConvocatoriaLista(Convocatoria convocatoria) {
        if (listadoConvocatoriasPadre == null) {
            listadoConvocatoriasPadre = new ArrayList<ConvocatoriaPadre>();
        }
        Iterator<ConvocatoriaPadre> i = listadoConvocatoriasPadre.iterator();
        while (i.hasNext()) {
            ConvocatoriaPadre convocatoriaLista = i.next();
            if (convocatoria != null && convocatoria.getPadre() != null
                    && convocatoriaLista.getId().equals(convocatoria.getPadre().getId())) {
                return;
            }
        }
        if (convocatoria != null && convocatoria.getPadre() != null) {
            listadoConvocatoriasPadre.add(convocatoria.getPadre());
        }
    }
    


    /**
     * Crear lista convocatorias item.
     */
    protected void crearListaConvocatoriasItem() {
        if (listadoConvocatoriasPadre != null) {
            Iterator<ConvocatoriaPadre> i = listadoConvocatoriasPadre.iterator();
            listaConvocatoriasPadreItems = new ArrayList<SelectItem>();
            while (i.hasNext()) {
                ConvocatoriaPadre convocatoriaPadreLista = i.next();
                listaConvocatoriasPadreItems.add(
                        new SelectItem(convocatoriaPadreLista.getId().toString(), convocatoriaPadreLista.getTitulo()));
            }
        }
    }

    /**
     * Cambiar convocatoria padre.
     */
    public void cambiarConvocatoriaPadre() {

        ConvocatoriaPadre cp = buscarConvocatoriaPadrexId(convocatoriaPadreFiltro);
        List<Convocatoria> listadoModalidades = servicioModalidad.obtenerConvocatoriasxPadre(cp);
        listaConvocatoriasItems = new ArrayList<SelectItem>();

        if (!esListaVacia(listadoModalidades)) {
            Iterator<Convocatoria> i = listadoModalidades.iterator();
            listaConvocatoriasItems.add(new SelectItem("", "Seleccione una modalidad"));
            while (i.hasNext()) {
                Convocatoria convocatoriaLista = i.next();
                listaConvocatoriasItems
                        .add(new SelectItem(convocatoriaLista.getId().toString(), convocatoriaLista.getTitulo()));
            }
        }
    }

    /**
     * Buscar convocatoria padrex id.
     *
     * @param idModalidad
     *            the id modalidad
     * @return the convocatoria padre
     */
    private ConvocatoriaPadre buscarConvocatoriaPadrexId(String idModalidad) {
        Iterator<ConvocatoriaPadre> it = this.listadoConvocatoriasPadre.iterator();
        ConvocatoriaPadre convocatoriaPadreEncontrada = null;
        if (StringUtils.isNoneEmpty(idModalidad)) {
            Long idFiltrado = Long.parseLong(idModalidad);
            while (it.hasNext()) {
                ConvocatoriaPadre convocatoriaPadre = (ConvocatoriaPadre) it.next();
                if (convocatoriaPadre.getId().equals(idFiltrado)) {
                    convocatoriaPadreEncontrada = convocatoriaPadre;
                }
            }
        }
        return convocatoriaPadreEncontrada;
    }    
    
    /**
     * Cargar listas movilidades.
     */
    public abstract void cargarListasMovilidades();
    
    /**
     * Reiniciar filtro.
     */
    public void reiniciarFiltro() {
        convocatoriaPadreFiltro = "";
        convocatoriaFiltro = "";
        cargarListasMovilidades();
        cambiarConvocatoriaPadre();
    }
    
    /**
     * Gets the movilidad vista seleccionada.
     *
     * @return the movilidad vista seleccionada
     */
    public ConvocatoriaMovilidadVista getMovilidadVistaSeleccionada() {
        return movilidadVistaSeleccionada;
    }

    /**
     * Sets the movilidad vista seleccionada.
     *
     * @param movilidadVistaSeleccionada
     *            the new movilidad vista seleccionada
     */
    public void setMovilidadVistaSeleccionada(ConvocatoriaMovilidadVista movilidadVistaSeleccionada) {
        this.movilidadVistaSeleccionada = movilidadVistaSeleccionada;
    }
    
    /**
     * Gets the movilidad visitante modificacion.
     *
     * @return the movilidadVisitanteModificacion
     */
    public MovilidadVisitanteExterior getMovilidadVisitanteModificacion() {
        return movilidadVisitanteModificacion;
    }

    /**
     * Sets the movilidad visitante modificacion.
     *
     * @param movilidadVisitanteModificacion
     *            the movilidadVisitanteModificacion to set
     */
    public void setMovilidadVisitanteModificacion(MovilidadVisitanteExterior movilidadVisitanteModificacion) {
        this.movilidadVisitanteModificacion = movilidadVisitanteModificacion;
    }

    /**
     * Gets the movilidad eventos modificacion.
     *
     * @return the movilidadEventosModificacion
     */
    public MovilidadDocentesExterior getMovilidadEventosModificacion() {
        return movilidadEventosModificacion;
    }

    /**
     * Sets the movilidad eventos modificacion.
     *
     * @param movilidadEventosModificacion
     *            the movilidadEventosModificacion to set
     */
    public void setMovilidadEventosModificacion(MovilidadDocentesExterior movilidadEventosModificacion) {
        this.movilidadEventosModificacion = movilidadEventosModificacion;
    }

    /**
     * Gets the movilidad estudiantes modificacion.
     *
     * @return the movilidadEstudiantesModificacion
     */
    public MovilidadEstudiantesPosgrado getMovilidadEstudiantesModificacion() {
        return movilidadEstudiantesModificacion;
    }

    /**
     * Sets the movilidad estudiantes modificacion.
     *
     * @param movilidadEstudiantesModificacion
     *            the movilidadEstudiantesModificacion to set
     */
    public void setMovilidadEstudiantesModificacion(MovilidadEstudiantesPosgrado movilidadEstudiantesModificacion) {
        this.movilidadEstudiantesModificacion = movilidadEstudiantesModificacion;
    }

    /**
     * Gets the movilidad docentes artes modificacion.
     *
     * @return the movilidadDocentesArtesModificacion
     */
    public MovilidadDocentesArtes getMovilidadDocentesArtesModificacion() {
        return movilidadDocentesArtesModificacion;
    }

    /**
     * Sets the movilidad docentes artes modificacion.
     *
     * @param movilidadDocentesArtesModificacion
     *            the movilidadDocentesArtesModificacion to set
     */
    public void setMovilidadDocentesArtesModificacion(MovilidadDocentesArtes movilidadDocentesArtesModificacion) {
        this.movilidadDocentesArtesModificacion = movilidadDocentesArtesModificacion;
    }

    /**
     * Gets the movilidad estudiantes artes modificacion.
     *
     * @return the movilidadEstudiantesArtesModificacion
     */
    public MovilidadEstudiantesArtes getMovilidadEstudiantesArtesModificacion() {
        return movilidadEstudiantesArtesModificacion;
    }

    /**
     * Sets the movilidad estudiantes artes modificacion.
     *
     * @param movilidadEstudiantesArtesModificacion
     *            the movilidadEstudiantesArtesModificacion to set
     */
    public void setMovilidadEstudiantesArtesModificacion(
            MovilidadEstudiantesArtes movilidadEstudiantesArtesModificacion) {
        this.movilidadEstudiantesArtesModificacion = movilidadEstudiantesArtesModificacion;
    }

    /**
     * Gets the movilidad visitantes artes modificacion.
     *
     * @return the movilidad visitantes artes modificacion
     */
    public MovilidadVisitantesArtes getMovilidadVisitantesArtesModificacion() {
        return movilidadVisitantesArtesModificacion;
    }

    /**
     * Sets the movilidad visitantes artes modificacion.
     *
     * @param movilidadVisitantesArtesModificacion the new movilidad visitantes artes modificacion
     */
    public void setMovilidadVisitantesArtesModificacion(MovilidadVisitantesArtes movilidadVisitantesArtesModificacion) {
        this.movilidadVisitantesArtesModificacion = movilidadVisitantesArtesModificacion;
    }

}
