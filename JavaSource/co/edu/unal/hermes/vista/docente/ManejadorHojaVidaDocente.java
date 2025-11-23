package co.edu.unal.hermes.vista.docente;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.IdDominioDetalle;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorAreaInteres;
import co.edu.unal.hermes.modelo.InvestigadorAreaInvestigacion;
import co.edu.unal.hermes.modelo.InvestigadorAsignatura;
import co.edu.unal.hermes.modelo.InvestigadorEnlace;
import co.edu.unal.hermes.modelo.InvestigadorEvento;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.InvestigadorLineaInvestigacion;
import co.edu.unal.hermes.modelo.InvestigadorObraExposicion;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.InvestigadorPublicacion;
import co.edu.unal.hermes.modelo.LineaInvestigacion;
import co.edu.unal.hermes.modelo.Pais;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.VAsignaturasSIA;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.ManejadorBase;
import sun.awt.image.ImageFormatException;

public class ManejadorHojaVidaDocente extends ManejadorBase {

    private static final long serialVersionUID = 649252525197696424L;
    private CroppedImage croppedImage;
    String path = RUTA_ARCHIVOS + File.separator + "HER_INVESTIGADOR_INTERNO" + File.separator;
    ServletContext servletContext;
    private String newImageName;
    private String cedula;
    private String perfil;
    private String extension;
    private boolean otroTelefono = false;
    InvestigadorInterno investigadorInterno;
    private String newImageNameActual;
    boolean bandera = true;
    boolean banderaUno = false;
    boolean banderaDos = false;
    boolean verCVitae = false;
    private StreamedContent imagen;
    File actual;
    String newFileName = "";
    private Investigador investigadorActual = null;
    private Map<String, String> proyectoArray;
    private List<String> proyectoSeleccionadosArray;

    // Eventos del docente
    private List<InvestigadorEvento> listaEventos;
    private List<InvestigadorEvento> listaEventosBorrados;
    private String nombreEvento;
    private String tituloTrabajo;
    private String paisSeleccionado;
    private String ciudadEvento;
    private Date fechaEvento;
    private List<Pais> listaPaises;
    private SelectItem[] paisItem;
    private InvestigadorEvento eventoSeleccionado;

    // Enlaces del docente
    private List<InvestigadorEnlace> listaEnlaces;
    private List<InvestigadorEnlace> listaEnlacesBorrados;
    private String nombreSitio;
    private String linkSitio;
    private InvestigadorEnlace enlaceSeleccionado;

    // Publicaciones del docente
    private List<InvestigadorPublicacion> listaPublicaciones;
    private List<InvestigadorPublicacion> listaPublicacionesBorradas;
    private String tipo;
    private String autores;
    private String publicacion;
    private InvestigadorPublicacion publicacionSeleccionada;
    private SelectItem[] tipoPublicacionesItem;

    // Areas de investigacion docente
    private SelectItem[] areasInvestigacionItem;
    private List<DominioDetalle> listaAreasInvestigacion;
    private String areaInvestigacion;
    private List<InvestigadorAreaInvestigacion> listaAreasInvestigacionInvestigador;
    private List<InvestigadorAreaInvestigacion> listaAreasInvestigacionBorradas;
    private InvestigadorAreaInvestigacion areaInvestigacionSeleccionada;

    // Areas de interes docente
    private String areaInteres;
    private List<InvestigadorAreaInteres> listaAreasInteresInvestigador;
    private List<InvestigadorAreaInteres> listaAreasInteresBorradas;
    private InvestigadorAreaInteres areaInteresSeleccionada;

    // Asignaturas docente
    private String sedeUniversidad;
    private SelectItem[] listaSedesItem;
    private List<Dependencia> listaSedes;
    private String dependenciaUniversidad;
    private SelectItem[] listaDependenciasItem;
    private List<Dependencia> listaDependencias;
    private String deptoAsignatura;
    private SelectItem[] listaDepartamentosItem;
    private List<Dependencia> listaDepartamentos;
    private String asignatura;
    private SelectItem[] listaAsignaturasDepartamento;
    private List<VAsignaturasSIA> listaAsignaturas;
    private List<InvestigadorAsignatura> listaAsignaturasInvestigador;
    private List<InvestigadorAsignatura> listaAsignaturasInvestigadorBorradas;
    private InvestigadorAsignatura asignaturaSeleccionada;

    // ObraExposicion del docente
    private List<InvestigadorObraExposicion> listaObraExposicion;
    private List<InvestigadorObraExposicion> listaObraExposicionBorrados;
    private String nombreObra;
    private String nombreExposicion;
    private String institucionOrganizadora;
    private String paisObraExpo;
    private String ciudadObraExpo;
    private Date fechaExposicion;
    private Date fechaObra;
    private InvestigadorObraExposicion obraExposicionSeleccionada;
    boolean exposicionObra = false;
    
    //lineas de investigacion
    private String lineaInvestigacion;
	private InvestigadorLineaInvestigacion lineaInvestigacionSeleccionada;
	private List<InvestigadorLineaInvestigacion> listaLineasInvestigacion;
	private SelectItem[] listaLineasInvestigacionItems;
	private List<InvestigadorLineaInvestigacion> listaLineasBorradas;

    public ManejadorHojaVidaDocente() {
        servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        personaActual = (Persona) sesion.getAttribute("persona");
        investigadorInterno = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());
        investigadorActual = servicioPersona.obtenerProyectosGruposInvestigador(personaActual.getId());

        cedula = personaActual.getId().getDocumento();

        Iterator it = investigadorActual.getProyectosInvestigador().iterator();
        proyectoArray = new LinkedHashMap<String, String>();

        proyectoSeleccionadosArray = new ArrayList<String>();
        while (it.hasNext()) {
            InvestigadorProyecto iproyecto = (InvestigadorProyecto) it.next();

            proyectoArray.put(iproyecto.getProyecto().getId() + " - " + iproyecto.getProyecto().getNombre(),
                    String.valueOf(iproyecto.getProyecto().getId()));

            if (iproyecto != null && iproyecto.getVisible() != null && "S".equals(iproyecto.getVisible())) {
                proyectoSeleccionadosArray.add(String.valueOf(iproyecto.getProyecto().getId()));

            }
        }

        proyectoArray = sortByValues(proyectoArray);

        if (proyectoArray.size() == 0) {
            proyectoArray = null;

        }

        actual = new File(path + cedula + ".jpg");

        if (actual.exists()) {
            bandera = true;
        } else {
            bandera = false;
        }

        if (StringUtils.isNotEmpty(investigadorInterno.getTelefono())) {
            otroTelefono = true;
        }
        perfil = investigadorInterno.getPerfil();
        extension = investigadorInterno.getTelExtension();

        cargarPaises();
        listaEventos = new ArrayList<InvestigadorEvento>();
        listaEventosBorrados = new ArrayList<InvestigadorEvento>();
        fechaEvento = new Date();
        obtenerEventosInvestigador(investigadorInterno.getId().getTipoDocumento(),
                investigadorInterno.getId().getDocumento());
        listaEnlaces = new ArrayList<InvestigadorEnlace>();
        listaEnlacesBorrados = new ArrayList<InvestigadorEnlace>();
        obtenerEnlacesInvestigador(investigadorInterno.getId().getTipoDocumento(),
                investigadorInterno.getId().getDocumento());
        cargarTiposPublicaciones();
        listaPublicaciones = new ArrayList<InvestigadorPublicacion>();
        listaPublicacionesBorradas = new ArrayList<InvestigadorPublicacion>();
        obtenerPublicacionesInvestigador(investigadorInterno.getId().getTipoDocumento(),
                investigadorInterno.getId().getDocumento());
        cargarAreasInteres();
        listaAreasInvestigacionInvestigador = new ArrayList<InvestigadorAreaInvestigacion>();
        listaAreasInvestigacionBorradas = new ArrayList<InvestigadorAreaInvestigacion>();
        obtenerAreasInvestigacionInvestigador(investigadorInterno.getId().getTipoDocumento(),
                investigadorInterno.getId().getDocumento());
        listaAreasInteresInvestigador = new ArrayList<InvestigadorAreaInteres>();
        listaAreasInteresBorradas = new ArrayList<InvestigadorAreaInteres>();
        obtenerAreasInteresInvestigador(investigadorInterno.getId().getTipoDocumento(),
                investigadorInterno.getId().getDocumento());
        listaSedes();
        listaAsignaturasInvestigador = new ArrayList<InvestigadorAsignatura>();
        listaAsignaturasInvestigadorBorradas = new ArrayList<InvestigadorAsignatura>();
        obtenerAsignaturasInvestigador(investigadorInterno.getId().getTipoDocumento(),
                investigadorInterno.getId().getDocumento());
        listaObraExposicion = new ArrayList<InvestigadorObraExposicion>();
        listaObraExposicionBorrados = new ArrayList<InvestigadorObraExposicion>();
        obtenerObraExposicionInvestigador(investigadorInterno.getId().getTipoDocumento(),
                investigadorInterno.getId().getDocumento());
        cargarLineasInvestigacion();
        listaLineasInvestigacion = new ArrayList<InvestigadorLineaInvestigacion>();
        obtenerLineasInvestigacionInvestigador(investigadorInterno.getId().getTipoDocumento(),
                investigadorInterno.getId().getDocumento());
        listaLineasBorradas = new ArrayList<InvestigadorLineaInvestigacion>();
        
    }

    public CroppedImage getCroppedImage() {
        return croppedImage;
    }

    public void setCroppedImage(CroppedImage croppedImage) {
        this.croppedImage = croppedImage;
    }

    public void cerrar() {
        banderaUno = false;
    }

    public void guardar() {
        if (banderaDos) {

            // Original file
            File dataInputFile = new File(servletContext.getRealPath("") + File.separator + CARPETA_TEMPORAL_IMAGENES
                    + File.separator + cedula + ".jpg");
            // New path
            File fileSendPath = new File(path + cedula + ".jpg");
            // Moving the file.
            if (fileSendPath.exists()) {
                fileSendPath.delete();
            }
            dataInputFile.renameTo(fileSendPath);
            banderaDos = false;
        }

        this.investigadorInterno.setPerfil(controlTamanoCadena(perfil, 3950));
        this.investigadorInterno.setTelExtension(controlTamanoCadena(extension, 5));
        servicioGeneral.guardarObjeto(investigadorInterno);

        actualizarListados();

        Iterator it = investigadorActual.getProyectosInvestigador().iterator();
        while (it.hasNext()) {
            InvestigadorProyecto iproyecto = (InvestigadorProyecto) it.next();

            if (proyectoSeleccionadosArray.contains(String.valueOf(iproyecto.getProyecto().getId()))) {
                iproyecto.setVisible("S");

            } else {
                iproyecto.setVisible("N");
            }
            servicioGeneral.guardarObjeto(iproyecto);
        }

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Registro Satisfactorio", "Hoja de vida actualizada"));
        verCVitae = true;

    }

    private void actualizarListados() {

        if (!esListaVacia(listaEventos)) {
            for (int i = 0; i < listaEventos.size(); i++) {
                servicioGeneral.guardarObjeto(listaEventos.get(i));
            }
        }

        if (!esListaVacia(listaEnlaces)) {
            for (int i = 0; i < listaEnlaces.size(); i++) {
                servicioGeneral.guardarObjeto(listaEnlaces.get(i));
            }
        }

        if (!esListaVacia(listaPublicaciones)) {
            for (int i = 0; i < listaPublicaciones.size(); i++) {
                servicioGeneral.guardarObjeto(listaPublicaciones.get(i));
            }
        }

        if (!esListaVacia(listaAreasInvestigacionInvestigador)) {
            for (int i = 0; i < listaAreasInvestigacionInvestigador.size(); i++) {
                servicioGeneral.guardarObjeto(listaAreasInvestigacionInvestigador.get(i));
            }
        }

        if (!esListaVacia(listaAreasInteresInvestigador)) {
            for (int i = 0; i < listaAreasInteresInvestigador.size(); i++) {
                servicioGeneral.guardarObjeto(listaAreasInteresInvestigador.get(i));
            }
        }

        if (!esListaVacia(listaAsignaturasInvestigador)) {
            for (int i = 0; i < listaAsignaturasInvestigador.size(); i++) {
                servicioGeneral.guardarObjeto(listaAsignaturasInvestigador.get(i));
            }
        }

        if (!esListaVacia(listaObraExposicion)) {
            for (int i = 0; i < listaObraExposicion.size(); i++) {
                servicioGeneral.guardarObjeto(listaObraExposicion.get(i));
            }
        }
        
        if (!esListaVacia(listaLineasInvestigacion)) {
            for (int i = 0; i < listaLineasInvestigacion.size(); i++) {
                servicioGeneral.guardarObjeto(listaLineasInvestigacion.get(i));
            }
        }

        eliminarElementosDescartados();
    }

    private void eliminarElementosDescartados() {

        if (!esListaVacia(listaEventosBorrados)) {
            for (int i = 0; i < listaEventosBorrados.size(); i++) {
                if (listaEventosBorrados.get(i).getId() != null) {
                    servicioGeneral.eliminarObjeto(listaEventosBorrados.get(i));
                }
            }
        }

        if (!esListaVacia(listaEnlacesBorrados)) {
            for (int i = 0; i < listaEnlacesBorrados.size(); i++) {
                if (listaEnlacesBorrados.get(i).getId() != null) {
                    servicioGeneral.eliminarObjeto(listaEnlacesBorrados.get(i));
                }
            }
        }

        if (!esListaVacia(listaPublicacionesBorradas)) {
            for (int i = 0; i < listaPublicacionesBorradas.size(); i++) {
                if (listaPublicacionesBorradas.get(i).getId() != null) {
                    servicioGeneral.eliminarObjeto(listaPublicacionesBorradas.get(i));
                }
            }
        }

        if (!esListaVacia(listaAreasInvestigacionBorradas)) {
            for (int i = 0; i < listaAreasInvestigacionBorradas.size(); i++) {
                if (listaAreasInvestigacionBorradas.get(i).getId() != null) {
                    servicioGeneral.eliminarObjeto(listaAreasInvestigacionBorradas.get(i));
                }
            }
        }

        if (!esListaVacia(listaAreasInteresBorradas)) {
            for (int i = 0; i < listaAreasInteresBorradas.size(); i++) {
                if (listaAreasInteresBorradas.get(i).getId() != null) {
                    servicioGeneral.eliminarObjeto(listaAreasInteresBorradas.get(i));
                }
            }
        }

        if (!esListaVacia(listaAsignaturasInvestigadorBorradas)) {
            for (int i = 0; i < listaAsignaturasInvestigadorBorradas.size(); i++) {
                if (listaAsignaturasInvestigadorBorradas.get(i).getId() != null) {
                    servicioGeneral.eliminarObjeto(listaAsignaturasInvestigadorBorradas.get(i));
                }
            }
        }

        if (!esListaVacia(listaObraExposicionBorrados)) {
            for (int i = 0; i < listaObraExposicionBorrados.size(); i++) {
                if (listaObraExposicionBorrados.get(i).getId() != null) {
                    servicioGeneral.eliminarObjeto(listaObraExposicionBorrados.get(i));
                }
            }
        }
        
        if (!esListaVacia(listaLineasBorradas)) {
            for (int i = 0; i < listaLineasBorradas.size(); i++) {
                if (listaLineasBorradas.get(i).getId() != null) {
                    servicioGeneral.eliminarObjeto(listaLineasBorradas.get(i));
                }
            }
        }
    }

    public String verCV() {

        guardar();

        String emailPersona;
        int arroba = personaActual.getEmail().indexOf("@");
        if (arroba != -1) {

            emailPersona = personaActual.getEmail().substring(0, arroba);
        } else {
            emailPersona = personaActual.getEmail();
        }

        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        String viewId = "/pages/Docentes/Docente.xhtml";
        try {
            viewId = extContext.getRequestContextPath() + viewId + '?' + "u" + "=" + emailPersona;
            String urlLink = context.getExternalContext().encodeActionURL(viewId);
            extContext.redirect(urlLink);
        } catch (IOException e) {
            extContext.log(getClass().getName() + ".invokeRedirect", e);
        }
        return null;

    }

    public void crop() throws ImageFormatException {

        String newFilePathDestino = servletContext.getRealPath("") + File.separator + CARPETA_TEMPORAL_IMAGENES
                + File.separator + cedula + ".jpg";

        banderaDos = recortarImagen(croppedImage, newFilePathDestino, servletContext);

        if (!banderaDos) {
            mensajeError("Ha ocurrido un problema en la edición de la imagen cargada.");
        }
    }

    public void subirArchivo(FileUploadEvent event) {

        UploadedFile archivoSubir = event.getFile();

        newImageNameActual = getRandomImageName() + "." + obtenerExtensionArchivo(archivoSubir.getFileName());

        String rutaArchivoTemporal = servletContext.getRealPath("") + File.separator + CARPETA_TEMPORAL_IMAGENES
                + File.separator + newImageNameActual;

        int tamanioMinimo = 210;
        int tamanioEscala = 500;
        int tamanioMaximoLado = 1000;

        banderaUno = cargarImagenDisco(archivoSubir, servletContext, rutaArchivoTemporal, tamanioMinimo, tamanioEscala,
                tamanioMaximoLado);
    }

    public static <K extends Comparable, V extends Comparable> Map<K, V> sortByValues(Map<K, V> map) {
        List<Map.Entry<K, V>> entries = new LinkedList<Map.Entry<K, V>>(map.entrySet());

        Collections.sort(entries, new Comparator<Map.Entry<K, V>>() {

            public int compare(Entry<K, V> o1, Entry<K, V> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        Map<K, V> sortedMap = new LinkedHashMap<K, V>();

        for (Map.Entry<K, V> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }


    private void cargarPaises() {
        listaPaises = servicioGeneral.obtenerListaObjetosOrdenadosAscG(Pais.class, "nombre");
        if (!esListaVacia(listaPaises)) {
            paisItem = new SelectItem[listaPaises.size()];
            for (int i = 0; i < listaPaises.size(); i++) {
                Pais p = listaPaises.get(i);
                paisItem[i] = new SelectItem(p.getId(), p.getNombre());
            }
        }
    }

    public void agregarEvento() {

        if (esCadenaVacia(nombreEvento)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe ingresar el nombre del evento.", ""));
            return;
        }

        if (paisSeleccionado != null && "00".equals(paisSeleccionado.trim())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Debe indicar el país del donde se realizó el evento.", ""));
            return;
        }

        if (esCadenaVacia(ciudadEvento)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Debe indicar la ciudad donde se llevó a cabo el evento.", ""));
            return;
        }

        if (esCadenaVacia(tituloTrabajo)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Debe indicar el título del trabajo con el cual participó en el evento.", ""));
            return;
        }

        Date hoy = new Date();
        if (fechaEvento != null && fechaEvento.after(hoy)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "El evento no se ha realizado, el ingreso de la información debe ser posterior a su realización.",
                            ""));
            return;
        }

        InvestigadorEvento eventoInvestigador = new InvestigadorEvento();
        eventoInvestigador.setNombre(controlTamanoCadena(nombreEvento, 980));
        eventoInvestigador.setTituloTrabajo(controlTamanoCadena(tituloTrabajo, 1800));
        eventoInvestigador.setFecha(fechaEvento);
        eventoInvestigador.setInvestigador(investigadorInterno);
        eventoInvestigador.setCiudadPais(controlTamanoCadena(ciudadEvento, 490));
        for (int i = 0; i < listaPaises.size(); i++) {
            if (listaPaises.get(i).getId().equals(paisSeleccionado)) {
                Pais paisEvento = new Pais();
                paisEvento.setId(paisSeleccionado);
                paisEvento.setNombre(listaPaises.get(i).getNombre());
                paisEvento.setSigla(listaPaises.get(i).getSigla());
                eventoInvestigador.setPais(paisEvento);
                break;
            }
        }
        listaEventos.add(eventoInvestigador);
    }

    public void eliminarEvento() {
        listaEventosBorrados.add(eventoSeleccionado);
        listaEventos.remove(eventoSeleccionado);
    }

    public void obtenerEventosInvestigador(String tipo, String doc) {
        listaEventos = servicioGeneral.obtenerEventosInvestigador(tipo, doc);
    }

    public void agregarEnlace() {

        if (esCadenaVacia(nombreSitio)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Debe ingresar el nombre del sitio que va a referenciar.", ""));
            return;
        }

        if (esCadenaVacia(linkSitio)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe ingresar la url del sitio.", ""));
            return;
        }

        InvestigadorEnlace enlaceInvestigador = new InvestigadorEnlace();
        enlaceInvestigador.setNombre(controlTamanoCadena(nombreSitio, 980));
        enlaceInvestigador.setLink(controlTamanoCadena(linkSitio, 1980));
        enlaceInvestigador.setInvestigador(investigadorInterno);
        listaEnlaces.add(enlaceInvestigador);
    }

    public void eliminarEnlace() {
        listaEnlacesBorrados.add(enlaceSeleccionado);
        listaEnlaces.remove(enlaceSeleccionado);
    }

    public void obtenerEnlacesInvestigador(String tipo, String doc) {
        listaEnlaces = servicioGeneral.obtenerEnlacesInvestigador(tipo, doc);
    }

    public void obtenerPublicacionesInvestigador(String tipo, String doc) {
        listaPublicaciones = servicioGeneral.obtenerPublicacionesInvestigador(tipo, doc);
    }

    private void cargarTiposPublicaciones() {
        List<DominioDetalle> lista = servicioGeneral.obtenerObjetos(DominioDetalle.class,
                "from DominioDetalle dd where dd.identificador.id = '110' order by dd.descripcion");

        if (!esListaVacia(lista)) {
            tipoPublicacionesItem = new SelectItem[lista.size()];
            for (int i = 0; i < lista.size(); i++) {
                DominioDetalle dominio = (DominioDetalle) lista.get(i);
                tipoPublicacionesItem[i] = new SelectItem(dominio.getIdentificador().getTipo(),
                        dominio.getDescripcion());
            }
        } else {
            tipoPublicacionesItem = new SelectItem[1];
            tipoPublicacionesItem[0] = new SelectItem("0", " - ");
        }
    }

    public void agregarPublicacion() {

        if (esCadenaVacia(autores)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Debe ingresar el(los) autor(es) de la publicación.", ""));
            return;
        }

        if (esCadenaVacia(publicacion)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Debe ingresar la publicación en el formato recomendado, dado que así se verá en su página personalizada.",
                            ""));
            return;
        }

        InvestigadorPublicacion publicacionInvestigador = new InvestigadorPublicacion();
        publicacionInvestigador.setTipo(tipo);
        publicacionInvestigador.setAutores(controlTamanoCadena(autores, 980));
        publicacionInvestigador.setPublicacion(controlTamanoCadena(publicacion, 1950));
        publicacionInvestigador.setInvestigador(investigadorInterno);
        listaPublicaciones.add(publicacionInvestigador);
    }

    public void eliminarPublicacion() {
        listaPublicacionesBorradas.add(publicacionSeleccionada);
        listaPublicaciones.remove(publicacionSeleccionada);
    }

    private void cargarAreasInteres() {
        listaAreasInvestigacion = servicioGeneral.obtenerObjetos(DominioDetalle.class,
                "from DominioDetalle dd where dd.identificador.id = '68' order by dd.descripcion");

        if (!esListaVacia(listaAreasInvestigacion)) {
            areasInvestigacionItem = new SelectItem[listaAreasInvestigacion.size()];
            for (int i = 0; i < listaAreasInvestigacion.size(); i++) {
                DominioDetalle dominio = (DominioDetalle) listaAreasInvestigacion.get(i);
                areasInvestigacionItem[i] = new SelectItem(dominio.getIdentificador().getTipo(),
                        dominio.getDescripcion());
            }
        } else {
            areasInvestigacionItem = new SelectItem[1];
            areasInvestigacionItem[0] = new SelectItem("0", " - ");
        }
    }

    public void agregarAreaInvestigacion() {

        if (esCadenaVacia(areaInvestigacion)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar un área de investigación.", ""));
            return;
        }

        if (!esListaVacia(listaAreasInvestigacionInvestigador)) {
            for (int a = 0; a < listaAreasInvestigacionInvestigador.size(); a++) {
                if (listaAreasInvestigacionInvestigador.get(a).getArea().getIdentificador().getTipo()
                        .equals(areaInvestigacion)) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "El área de investigación seleccionada ya existe en la lista.", ""));
                    return;
                }
            }
        }

        InvestigadorAreaInvestigacion investigadorAreaInteres = new InvestigadorAreaInvestigacion();
        DominioDetalle areaInv = new DominioDetalle();
        IdDominioDetalle areaAgregada = new IdDominioDetalle();
        areaAgregada.setId("68");
        areaAgregada.setTipo(areaInvestigacion);
        areaInv.setIdentificador(areaAgregada);
        investigadorAreaInteres.setArea(areaInv);
        investigadorAreaInteres.setInvestigador(investigadorInterno);
        for (int i = 0; i < listaAreasInvestigacion.size(); i++) {
            DominioDetalle area = (DominioDetalle) listaAreasInvestigacion.get(i);
            if (area.getIdentificador().getTipo().equals(areaInvestigacion)) {
                investigadorAreaInteres.setNombreArea(area.getDescripcion());
                break;
            }
        }
        listaAreasInvestigacionInvestigador.add(investigadorAreaInteres);
    }

    public void eliminarAreaInvestigacion() {
        listaAreasInvestigacionBorradas.add(areaInvestigacionSeleccionada);
        listaAreasInvestigacionInvestigador.remove(areaInvestigacionSeleccionada);
    }

    public void obtenerAreasInvestigacionInvestigador(String tipo, String doc) {
        listaAreasInvestigacionInvestigador = servicioGeneral.obtenerAreasInvestigacionInvestigador(tipo, doc);
    }

    public void agregarAreaInteres() {

        if (esCadenaVacia(areaInteres)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe ingresar su área de interés.", ""));
            return;
        }

        InvestigadorAreaInteres areaInvestigador = new InvestigadorAreaInteres();
        areaInvestigador.setNombreAreaInteres(controlTamanoCadena(areaInteres, 1200));
        areaInvestigador.setInvestigador(investigadorInterno);
        listaAreasInteresInvestigador.add(areaInvestigador);
    }

    public void eliminarAreaInteres() {
        listaAreasInteresBorradas.add(areaInteresSeleccionada);
        listaAreasInteresInvestigador.remove(areaInteresSeleccionada);
    }

    public void obtenerAreasInteresInvestigador(String tipo, String doc) {
        listaAreasInteresInvestigador = servicioGeneral.obtenerAreasInteresInvestigador(tipo, doc);
    }

    private void listaSedes() {
        listaSedes = (List<Dependencia>) servicioGeneral.obtenerSedes();
        if (!esListaVacia(listaSedes)) {
            listaSedesItem = new SelectItem[listaSedes.size() + 1];
            listaSedesItem[0] = new SelectItem("", "");

            for (int i = 1; i < listaSedes.size() + 1; i++) {
                Dependencia sede = (Dependencia) listaSedes.get(i - 1);
                listaSedesItem[i] = new SelectItem(sede.getId(), sede.getNombre().trim().toUpperCase());
            }
        }
    }

    public SelectItem[] getListaDependenciasItem() {
        return listaDependenciasItem;
    }

    public void setListaDependenciasItem(SelectItem[] listaDependenciasItem) {
        this.listaDependenciasItem = listaDependenciasItem;
    }

    public void cargarDependencias() {

        if (esCadenaVacia(sedeUniversidad)) {
            listaDependenciasItem = null;
            listaDepartamentosItem = null;
            listaAsignaturasDepartamento = null;
            deptoAsignatura = null;
            dependenciaUniversidad = null;
            asignatura = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Debe seleccionar una sede para que sean consultadas las asignaturas.", ""));
            return;
        }

        listaDependencias = servicioGeneral.obtenerObjetos(Dependencia.class,
                "from Dependencia d where d.sede.id = " + sedeUniversidad + " AND d.esFacultad = 'Y' AND d.estado = 'A' ORDER BY d.id");
        listaDependenciasItem = null;
        if (!esListaVacia(listaDependencias)) {
            listaDependenciasItem = new SelectItem[listaDependencias.size()];
            for (int i = 0; i < listaDependencias.size(); i++) {
                Dependencia dep = (Dependencia) listaDependencias.get(i);
                listaDependenciasItem[i] = new SelectItem(dep.getId(), dep.getNombre());
            }
            dependenciaUniversidad = listaDependencias.get(0).getId();
            cargarDepartamentos();
        }

    }

    public void cargarDepartamentos() {
        listaDepartamentos = servicioGeneral.obtenerObjetos(Dependencia.class,
                "from Dependencia d where d.facultad.id = '" + dependenciaUniversidad
                        + "' AND d.esDepartamento = 'Y' AND d.estado = 'A' ORDER BY d.id");
        listaDepartamentosItem = null;
        if (!esListaVacia(listaDepartamentos)) {
            listaDepartamentosItem = new SelectItem[listaDepartamentos.size()];
            for (int i = 0; i < listaDepartamentos.size(); i++) {
                Dependencia dep = (Dependencia) listaDepartamentos.get(i);
                listaDepartamentosItem[i] = new SelectItem(dep.getId(), dep.getNombre());
            }
            deptoAsignatura = listaDepartamentos.get(0).getId();
            cargarAsignaturas();
        }
    }

    public void cargarAsignaturas() {
        listaAsignaturas = servicioGeneral.obtenerObjetos(VAsignaturasSIA.class,
                "from VAsignaturasSIA a where a.sede.id = '" + sedeUniversidad + "' AND a.facultad.id = "
                        + dependenciaUniversidad + " AND a.uab.id = '" + deptoAsignatura + "' ORDER BY a.id");

        listaAsignaturasDepartamento = null;
        if (!esListaVacia(listaAsignaturas)) {
            listaAsignaturasDepartamento = new SelectItem[listaAsignaturas.size()];
            for (int i = 0; i < listaAsignaturas.size(); i++) {
                VAsignaturasSIA asi = (VAsignaturasSIA) listaAsignaturas.get(i);
                listaAsignaturasDepartamento[i] = new SelectItem(asi.getId(), asi.getCodAsignatura() + "-"
                        + asi.getGrupo() + "(" + asi.getAnnio() + "): " + asi.getNombreAsignatura());
            }
        }
    }

    public void agregarAsignatura() {

        if (esCadenaVacia(asignatura)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar la asignatura.", ""));
            return;
        }

        if (!esListaVacia(listaAsignaturasInvestigador)) {
            for (int a = 0; a < listaAsignaturasInvestigador.size(); a++) {
                if (listaAsignaturasInvestigador.get(a).getMateria().toString().equals(asignatura)) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "La asignatura seleccionada ya existe en la lista.", ""));
                    return;
                }
            }
        }

        InvestigadorAsignatura asignaturaInvestigador = new InvestigadorAsignatura();
        for (int i = 0; i < listaAsignaturas.size(); i++) {
            VAsignaturasSIA asig = (VAsignaturasSIA) listaAsignaturas.get(i);
            if (asig.getId().toString().equals(asignatura)) {
                asignaturaInvestigador.setAsignatura(asig);
                asignaturaInvestigador.setMateria(asig.getId());
                break;
            }
        }

        asignaturaInvestigador.setInvestigador(investigadorInterno);
        listaAsignaturasInvestigador.add(asignaturaInvestigador);
    }

    public void eliminarAsignatura() {
        listaAsignaturasInvestigadorBorradas.add(asignaturaSeleccionada);
        listaAsignaturasInvestigador.remove(asignaturaSeleccionada);
    }

    public void obtenerAsignaturasInvestigador(String tipo, String doc) {

        listaAsignaturasInvestigador = servicioGeneral.obtenerAsignaturasInvestigador(tipo, doc);
    }

    public void agregarObraExposicion() {

        if (esCadenaVacia(nombreObra)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Debe ingresar el nombre de la obra o exposición realizada.", ""));
            return;
        }

        if (exposicionObra) {
            if (paisObraExpo != null && "00".equals(paisObraExpo.trim())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Debe indicar el país donde se realizó la exposición o se presentó la obra.", ""));
                return;
            }
            if (esCadenaVacia(ciudadObraExpo)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Debe indicar la ciudad donde se realizó la exposición o se presentó la obra.", ""));
                return;
            }

            if (esCadenaVacia(nombreExposicion)) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe indicar el nombre de la exposición", ""));
                return;
            }

            if (esCadenaVacia(institucionOrganizadora)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Debe indicar el nombre de la institución organizadora de la exposición.", ""));
                return;
            }
        }

        Date hoy = new Date();
        if (fechaObra != null && fechaObra.after(hoy)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "La obra o exposición no se ha realizado, el ingreso de la información debe ser posterior a su realización.",
                            ""));
            return;
        }

        InvestigadorObraExposicion obraInvestigador = new InvestigadorObraExposicion();
        obraInvestigador.setNombreObra(controlTamanoCadena(nombreObra, 1200));
        obraInvestigador.setNombreExposicion(controlTamanoCadena(nombreExposicion, 1480));
        obraInvestigador.setOrganizador(controlTamanoCadena(institucionOrganizadora, 980));
        obraInvestigador.setFechaObra(fechaObra);
        obraInvestigador.setFechaExposicion(fechaExposicion);
        obraInvestigador.setInvestigador(investigadorInterno);
        obraInvestigador.setCiudadPais(controlTamanoCadena(ciudadObraExpo, 480));
        obraInvestigador.setObra(exposicionObra);

        if (paisObraExpo != null && !"".equals(paisObraExpo)) {

            for (int i = 0; i < listaPaises.size(); i++) {
                if (listaPaises.get(i).getId().equals(paisObraExpo)) {
                    Pais paisObra = new Pais();
                    paisObra.setId(paisObraExpo);
                    paisObra.setNombre(listaPaises.get(i).getNombre());
                    paisObra.setSigla(listaPaises.get(i).getSigla());
                    obraInvestigador.setPais(paisObra);
                    break;
                }
            }
        } else {
            Pais paisObra = new Pais();
            paisObra.setId("00");
            obraInvestigador.setPais(paisObra);
        }

        listaObraExposicion.add(obraInvestigador);
    }

    public void eliminarObraExposicion() {
        listaObraExposicionBorrados.add(obraExposicionSeleccionada);
        listaObraExposicion.remove(obraExposicionSeleccionada);
    }

    public void obtenerObraExposicionInvestigador(String tipo, String doc) {

        listaObraExposicion = servicioGeneral.obtenerObraExposicionInvestigador(tipo, doc);
    }

    public void reporteHojaVida() throws SQLException {

        String id = investigadorInterno.getId().getDocumento().toString();

        ReporteBirt r = new ReporteBirt();
        r.adicionarParametro("inv", id);
        r.setNombreReporte("/portafolio/hoja-vida-docente");
        String foto = "1";
        if (!bandera) {
            foto = "0";
        }
        r.adicionarParametro("foto", foto);
        r.setFormato(ReporteBirt.FORMATO_PDF);
        sesion.setAttribute("reporte", r);
        FacesContext context = FacesContext.getCurrentInstance();
        r.run(context);
    }

    public String getNewImageName() {
        return newImageName;
    }

    public void setNewImageName(String newImageName) {
        this.newImageName = newImageName;
    }

    public String getNewFileName() {
        return newFileName;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }

    public String getNewImageNameActual() {
        return newImageNameActual;
    }

    public void setNewImageNameActual(String newImageNameActual) {
        this.newImageNameActual = newImageNameActual;
    }

    public boolean isBandera() {
        return bandera;
    }

    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }

    public boolean isBanderaUno() {
        return banderaUno;
    }

    public void setBanderaUno(boolean banderaUno) {
        this.banderaUno = banderaUno;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public boolean isBanderaDos() {
        return banderaDos;
    }

    public void setBanderaDos(boolean banderaDos) {
        this.banderaDos = banderaDos;
    }

    public StreamedContent getImagen() {
        try {
            imagen = new DefaultStreamedContent(
                    new ByteArrayInputStream(org.apache.commons.io.FileUtils.readFileToByteArray(actual)), "image/png");
        } catch (IOException e) {

            e.printStackTrace();
        }
        return imagen;
    }

    public void setImagen(StreamedContent imagen) {
        this.imagen = imagen;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public InvestigadorInterno getInvestigadorInterno() {
        return investigadorInterno;
    }

    public void setInvestigadorInterno(InvestigadorInterno investigadorInterno) {
        this.investigadorInterno = investigadorInterno;
    }

    public Investigador getInvestigadorActual() {
        return investigadorActual;
    }

    public void setInvestigadorActual(Investigador investigadorActual) {
        this.investigadorActual = investigadorActual;
    }

    public Map<String, String> getProyectoArray() {
        return proyectoArray;
    }

    public void setProyectoArray(Map<String, String> proyectoArray) {
        this.proyectoArray = proyectoArray;
    }

    public List<String> getProyectoSeleccionadosArray() {
        return proyectoSeleccionadosArray;
    }

    public void setProyectoSeleccionadosArray(List<String> proyectoSeleccionadosArray) {
        this.proyectoSeleccionadosArray = proyectoSeleccionadosArray;
    }

    public List<InvestigadorEvento> getListaEventos() {
        return listaEventos;
    }

    public void setListaEventos(List<InvestigadorEvento> listaEventos) {
        this.listaEventos = listaEventos;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getTituloTrabajo() {
        return tituloTrabajo;
    }

    public void setTituloTrabajo(String tituloTrabajo) {
        this.tituloTrabajo = tituloTrabajo;
    }

    public String getPaisSeleccionado() {
        return paisSeleccionado;
    }

    public void setPaisSeleccionado(String paisSeleccionado) {
        this.paisSeleccionado = paisSeleccionado;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public List<Pais> getListaPaises() {
        return listaPaises;
    }

    public void setListaPaises(List<Pais> listaPaises) {
        this.listaPaises = listaPaises;
    }

    public SelectItem[] getPaisItem() {
        return paisItem;
    }

    public void setPaisItem(SelectItem[] paisItem) {
        this.paisItem = paisItem;
    }

    public InvestigadorEvento getEventoSeleccionado() {
        return eventoSeleccionado;
    }

    public void setEventoSeleccionado(InvestigadorEvento eventoSeleccionado) {
        this.eventoSeleccionado = eventoSeleccionado;
    }

    public String getCiudadEvento() {
        return ciudadEvento;
    }

    public void setCiudadEvento(String ciudadEvento) {
        this.ciudadEvento = ciudadEvento;
    }

    public List<InvestigadorEnlace> getListaEnlaces() {
        return listaEnlaces;
    }

    public void setListaEnlaces(List<InvestigadorEnlace> listaEnlaces) {
        this.listaEnlaces = listaEnlaces;
    }

    public String getNombreSitio() {
        return nombreSitio;
    }

    public void setNombreSitio(String nombreSitio) {
        this.nombreSitio = nombreSitio;
    }

    public String getLinkSitio() {
        return linkSitio;
    }

    public void setLinkSitio(String linkSitio) {
        this.linkSitio = linkSitio;
    }

    public InvestigadorEnlace getEnlaceSeleccionado() {
        return enlaceSeleccionado;
    }

    public void setEnlaceSeleccionado(InvestigadorEnlace enlaceSeleccionado) {
        this.enlaceSeleccionado = enlaceSeleccionado;
    }

    public List<InvestigadorEnlace> getListaEnlacesBorrados() {
        return listaEnlacesBorrados;
    }

    public void setListaEnlacesBorrados(List<InvestigadorEnlace> listaEnlacesBorrados) {
        this.listaEnlacesBorrados = listaEnlacesBorrados;
    }

    public List<InvestigadorPublicacion> getListaPublicaciones() {
        return listaPublicaciones;
    }

    public void setListaPublicaciones(List<InvestigadorPublicacion> listaPublicaciones) {
        this.listaPublicaciones = listaPublicaciones;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(String publicacion) {
        this.publicacion = publicacion;
    }

    public InvestigadorPublicacion getPublicacionSeleccionada() {
        return publicacionSeleccionada;
    }

    public void setPublicacionSeleccionada(InvestigadorPublicacion publicacionSeleccionada) {
        this.publicacionSeleccionada = publicacionSeleccionada;
    }

    public SelectItem[] getTipoPublicacionesItem() {
        return tipoPublicacionesItem;
    }

    public void setTipoPublicacionesItem(SelectItem[] tipoPublicacionesItem) {
        this.tipoPublicacionesItem = tipoPublicacionesItem;
    }

    public String getAutores() {
        return autores;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    public String getAreaInteres() {
        return areaInteres;
    }

    public void setAreaInteres(String areaInteres) {
        this.areaInteres = areaInteres;
    }

    public SelectItem[] getAreasInvestigacionItem() {
        return areasInvestigacionItem;
    }

    public void setAreasInvestigacionItem(SelectItem[] areasInvestigacionItem) {
        this.areasInvestigacionItem = areasInvestigacionItem;
    }

    public String getAreaInvestigacion() {
        return areaInvestigacion;
    }

    public void setAreaInvestigacion(String areaInvestigacion) {
        this.areaInvestigacion = areaInvestigacion;
    }

    public List<InvestigadorAreaInvestigacion> getListaAreasInvestigacionInvestigador() {
        return listaAreasInvestigacionInvestigador;
    }

    public void setListaAreasInvestigacionInvestigador(
            List<InvestigadorAreaInvestigacion> listaAreasInvestigacionInvestigador) {
        this.listaAreasInvestigacionInvestigador = listaAreasInvestigacionInvestigador;
    }

    public InvestigadorAreaInvestigacion getAreaInvestigacionSeleccionada() {
        return areaInvestigacionSeleccionada;
    }

    public void setAreaInvestigacionSeleccionada(InvestigadorAreaInvestigacion areaInvestigacionSeleccionada) {
        this.areaInvestigacionSeleccionada = areaInvestigacionSeleccionada;
    }

    public List<InvestigadorAreaInteres> getListaAreasInteresInvestigador() {
        return listaAreasInteresInvestigador;
    }

    public void setListaAreasInteresInvestigador(List<InvestigadorAreaInteres> listaAreasInteresInvestigador) {
        this.listaAreasInteresInvestigador = listaAreasInteresInvestigador;
    }

    public InvestigadorAreaInteres getAreaInteresSeleccionada() {
        return areaInteresSeleccionada;
    }

    public void setAreaInteresSeleccionada(InvestigadorAreaInteres areaInteresSeleccionada) {
        this.areaInteresSeleccionada = areaInteresSeleccionada;
    }

    public String getSedeUniversidad() {
        return sedeUniversidad;
    }

    public void setSedeUniversidad(String sedeUniversidad) {
        this.sedeUniversidad = sedeUniversidad;
    }

    public SelectItem[] getListaSedesItem() {
        return listaSedesItem;
    }

    public void setListaSedesItem(SelectItem[] listaSedesItem) {
        this.listaSedesItem = listaSedesItem;
    }

    public List<Dependencia> getListaSedes() {
        return listaSedes;
    }

    public void setListaSedes(List<Dependencia> listaSedes) {
        this.listaSedes = listaSedes;
    }

    public String getDependenciaUniversidad() {
        return dependenciaUniversidad;
    }

    public void setDependenciaUniversidad(String dependenciaUniversidad) {
        this.dependenciaUniversidad = dependenciaUniversidad;
    }

    public SelectItem[] getListaDepartamentosItem() {
        return listaDepartamentosItem;
    }

    public void setListaDepartamentosItem(SelectItem[] listaDepartamentosItem) {
        this.listaDepartamentosItem = listaDepartamentosItem;
    }

    public List<Dependencia> getListaDepartamentos() {
        return listaDepartamentos;
    }

    public void setListaDepartamentos(List<Dependencia> listaDepartamentos) {
        this.listaDepartamentos = listaDepartamentos;
    }

    public String getDeptoAsignatura() {
        return deptoAsignatura;
    }

    public void setDeptoAsignatura(String deptoAsignatura) {
        this.deptoAsignatura = deptoAsignatura;
    }

    public SelectItem[] getListaAsignaturasDepartamento() {
        return listaAsignaturasDepartamento;
    }

    public void setListaAsignaturasDepartamento(SelectItem[] listaAsignaturasDepartamento) {
        this.listaAsignaturasDepartamento = listaAsignaturasDepartamento;
    }

    public List<VAsignaturasSIA> getListaAsignaturas() {
        return listaAsignaturas;
    }

    public void setListaAsignaturas(List<VAsignaturasSIA> listaAsignaturas) {
        this.listaAsignaturas = listaAsignaturas;
    }

    public List<InvestigadorAsignatura> getListaAsignaturasInvestigador() {
        return listaAsignaturasInvestigador;
    }

    public void setListaAsignaturasInvestigador(List<InvestigadorAsignatura> listaAsignaturasInvestigador) {
        this.listaAsignaturasInvestigador = listaAsignaturasInvestigador;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public InvestigadorAsignatura getAsignaturaSeleccionada() {
        return asignaturaSeleccionada;
    }

    public void setAsignaturaSeleccionada(InvestigadorAsignatura asignaturaSeleccionada) {
        this.asignaturaSeleccionada = asignaturaSeleccionada;
    }

    public List<InvestigadorObraExposicion> getListaObraExposicion() {
        return listaObraExposicion;
    }

    public void setListaObraExposicion(List<InvestigadorObraExposicion> listaObraExposicion) {
        this.listaObraExposicion = listaObraExposicion;
    }

    public List<InvestigadorObraExposicion> getListaObraExposicionBorrados() {
        return listaObraExposicionBorrados;
    }

    public void setListaObraExposicionBorrados(List<InvestigadorObraExposicion> listaObraExposicionBorrados) {
        this.listaObraExposicionBorrados = listaObraExposicionBorrados;
    }

    public String getInstitucionOrganizadora() {
        return institucionOrganizadora;
    }

    public void setInstitucionOrganizadora(String institucionOrganizadora) {
        this.institucionOrganizadora = institucionOrganizadora;
    }

    public String getPaisObraExpo() {
        return paisObraExpo;
    }

    public void setPaisObraExpo(String paisObraExpo) {
        this.paisObraExpo = paisObraExpo;
    }

    public String getCiudadObraExpo() {
        return ciudadObraExpo;
    }

    public void setCiudadObraExpo(String ciudadObraExpo) {
        this.ciudadObraExpo = ciudadObraExpo;
    }

    public InvestigadorObraExposicion getObraExposicionSeleccionada() {
        return obraExposicionSeleccionada;
    }

    public void setObraExposicionSeleccionada(InvestigadorObraExposicion obraExposicionSeleccionada) {
        this.obraExposicionSeleccionada = obraExposicionSeleccionada;
    }

    public String getNombreExposicion() {
        return nombreExposicion;
    }

    public void setNombreExposicion(String nombreExposicion) {
        this.nombreExposicion = nombreExposicion;
    }

    public String getNombreObra() {
        return nombreObra;
    }

    public void setNombreObra(String nombreObra) {
        this.nombreObra = nombreObra;
    }

    public Date getFechaExposicion() {
        return fechaExposicion;
    }

    public void setFechaExposicion(Date fechaExposicion) {
        this.fechaExposicion = fechaExposicion;
    }

    public Date getFechaObra() {
        return fechaObra;
    }

    public void setFechaObra(Date fechaObra) {
        this.fechaObra = fechaObra;
    }

    public boolean isExposicionObra() {
        return exposicionObra;
    }

    public void setExposicionObra(boolean exposicionObra) {
        this.exposicionObra = exposicionObra;
    }

    public boolean isOtroTelefono() {
        return otroTelefono;
    }

    public void setOtroTelefono(boolean otroTelefono) {
        this.otroTelefono = otroTelefono;
    }
    
    public void cargarLineasInvestigacion() {
		List listaLineas = servicioGeneral
				.obtenerObjetos("select l from LineaInvestigacion l ");
		if (!esListaVacia(listaLineas)) {
			listaLineasInvestigacionItems = new SelectItem[listaLineas.size()];
			for (int i = 0; i < listaLineas.size(); i++) {
				LineaInvestigacion li = (LineaInvestigacion) listaLineas.get(i);
				listaLineasInvestigacionItems[i] = new SelectItem(li.getId(), li.getNombre());
			}
		}
		lineaInvestigacion="";
	}
	
	public void agregarLineaInvestigacion() {
		if (esCadenaVacia(lineaInvestigacion)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar un línea de investigación.", ""));
            return;
        }

        if (!esListaVacia(listaLineasInvestigacion)) {
            for (int a = 0; a < listaLineasInvestigacion.size(); a++) {
                if (listaLineasInvestigacion.get(a).getLinea().getId().toString().equals(lineaInvestigacion)) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "La línea de investigación seleccionada ya existe en la lista.", ""));
                    return;
                }
            }
        }

        LineaInvestigacion l = servicioGeneral.obtenerObjetos(
        		LineaInvestigacion.class,"from LineaInvestigacion l where l.id=" + lineaInvestigacion).get(0);	
        InvestigadorLineaInvestigacion linea= new InvestigadorLineaInvestigacion();
        linea.setLinea(l);
        linea.setInvestigador(investigadorActual);
        listaLineasInvestigacion.add(linea);
	}
	
    public void eliminarLinea() {
        listaLineasBorradas.add(lineaInvestigacionSeleccionada);
        listaLineasInvestigacion.remove(lineaInvestigacionSeleccionada);
    }

	public String getLineaInvestigacion() {
		return lineaInvestigacion;
	}

	public void setLineaInvestigacion(String lineaInvestigacion) {
		this.lineaInvestigacion = lineaInvestigacion;
	}

	public InvestigadorLineaInvestigacion getLineaInvestigacionSeleccionada() {
		return lineaInvestigacionSeleccionada;
	}

	public void setLineaInvestigacionSeleccionada(InvestigadorLineaInvestigacion lineaInvestigacionSeleccionada) {
		this.lineaInvestigacionSeleccionada = lineaInvestigacionSeleccionada;
	}

	public List<InvestigadorLineaInvestigacion> getListaLineasInvestigacion() {
		return listaLineasInvestigacion;
	}

	public void setListaLineasInvestigacion(List<InvestigadorLineaInvestigacion> listaLineasInvestigacion) {
		this.listaLineasInvestigacion = listaLineasInvestigacion;
	}

	public SelectItem[] getListaLineasInvestigacionItems() {
		return listaLineasInvestigacionItems;
	}

	public void setListaLineasInvestigacionItems(SelectItem[] listaLineasInvestigacionItems) {
		this.listaLineasInvestigacionItems = listaLineasInvestigacionItems;
	}
	
    public void obtenerLineasInvestigacionInvestigador(String tipo, String doc) {
        listaLineasInvestigacion = servicioGeneral.obtenerLineasInvestigacionInvestigador(tipo, doc);
    }

	public List<InvestigadorLineaInvestigacion> getListaLineasBorradas() {
		return listaLineasBorradas;
	}

	public void setListaLineasBorradas(List<InvestigadorLineaInvestigacion> listaLineasBorradas) {
		this.listaLineasBorradas = listaLineasBorradas;
	}

}
