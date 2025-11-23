/*
 * Created on 30-sep-2005
 */
package co.edu.unal.hermes.vista.personas;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.myfaces.custom.datalist.HtmlDataList;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorAreaInteres;
import co.edu.unal.hermes.modelo.InvestigadorAreaInvestigacion;
import co.edu.unal.hermes.modelo.InvestigadorAsignatura;
import co.edu.unal.hermes.modelo.InvestigadorEnlace;
import co.edu.unal.hermes.modelo.InvestigadorEvento;
import co.edu.unal.hermes.modelo.InvestigadorGrupo;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.InvestigadorObraExposicion;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.InvestigadorPublicacion;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorPersonaBusqueda extends ManejadorBase {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String paginaActual;
    private Investigador investigadorActual = null; // Investigador actualmente
                                                    // cargado
    private boolean error;

    private HtmlDataList dataListProyectosInvestigador;
    private HtmlDataList dataListGruposInvestigador;
    private HtmlDataList dataListAreasConocimiento;

    private InvestigadorInterno investigadorInterno;
    private Set proyectosInvestigador;

    boolean bandera = true;
    private StreamedContent imagen;
    File actual;

    String path = RUTA_ARCHIVOS + File.separator + "HER_INVESTIGADOR_INTERNO" + File.separator;

    private String cedula;
    private String idPersona;

    private List<InvestigadorEvento> listaEventos;
    private List<InvestigadorEnlace> listaEnlaces;
    private List<InvestigadorPublicacion> listaPublicaciones;
    private List<InvestigadorPublicacion> listaPublicacionesInternacionales;
    private List<InvestigadorPublicacion> listaPublicacionesNacionales;
    private List<InvestigadorPublicacion> listaPublicacionesLibros;
    private List<InvestigadorPublicacion> listaPublicacionesTesis;
    private List<InvestigadorPublicacion> listaPublicacionesTesisPregrado;
    private List<InvestigadorAreaInvestigacion> listaAreasInvestigacionInvestigador;
    private List<InvestigadorAreaInteres> listaAreasInteresInvestigador;
    private List<Laboratorio> listaLaboratoriosAsociados;
    private List<InvestigadorAsignatura> listaAsignaturasInvestigador;
    private List<InvestigadorObraExposicion> listaObraExposicion;
    private List<InvestigadorObraExposicion> listaObrasInvestigador;
    private List<InvestigadorObraExposicion> listaExposicionInvestigador;

    public ManejadorPersonaBusqueda() throws SQLException {
    	sesion = request.getSession();
        if (this.request.getParameter("u") != null && !this.request.getParameter("u").equals("")) {
        	error = false;
            cargarInvestigador();

            if (!error) {
                cargarPaginaActual();
            }
        	
        } 
        
        /*else {
        	reporteHojaVida();
            idPersona = null;   
        }*/
    }

    private void cargarInvestigador() {
        sesion = request.getSession();
        this.error = false;

        if (this.request.getParameter("u") != null && !this.request.getParameter("u").equals("")) {

            try {
                investigadorActual = servicioGeneral
                        .obtenerInvestigadorPorEmail(new String(this.request.getParameter("u") + "@unal.edu.co"));

                if (investigadorActual != null) {
                    InvestigadorInterno ii = servicioPersona.obtenerInvestigadorInterno(investigadorActual.getId());
                    if (ii == null || ii.getInterno() == null || ii.getInterno().equals("N")) {
                        this.error = true;
                    } else {
                        obtenerEventosInvestigador(investigadorActual.getId().getTipoDocumento(),
                                investigadorActual.getId().getDocumento());
                        obtenerEnlacesInvestigador(investigadorActual.getId().getTipoDocumento(),
                                investigadorActual.getId().getDocumento());
                        obtenerPublicacionesInvestigador(investigadorActual.getId().getTipoDocumento(),
                                investigadorActual.getId().getDocumento());
                        obtenerAreasInvestigacionInvestigador(investigadorActual.getId().getTipoDocumento(),
                                investigadorActual.getId().getDocumento());
                        obtenerAreasInteresInvestigador(investigadorActual.getId().getTipoDocumento(),
                                investigadorActual.getId().getDocumento());
                        obtenerLaboratoriosInvestigador(investigadorActual.getId().getTipoDocumento(),
                                investigadorActual.getId().getDocumento());
                        obtenerAsignaturasInvestigador(investigadorActual.getId().getTipoDocumento(),
                                investigadorActual.getId().getDocumento());
                        obtenerObraExposicionInvestigador(investigadorActual.getId().getTipoDocumento(),
                                investigadorActual.getId().getDocumento());
                        investigadorActual = servicioPersona
                                .obtenerProyectosGruposInvestigador(investigadorActual.getId());
                        cedula = investigadorActual.getId().getDocumento();
                        idPersona = cedula;
                        actual = new File(path + cedula + ".jpg");
                    }
                    try {
                    	if(actual!= null && actual.exists()) {

	                        imagen = new DefaultStreamedContent(
	                                new ByteArrayInputStream(org.apache.commons.io.FileUtils.readFileToByteArray(actual)),
	                                "image/jpg");
	
	                        if (imagen != null) {
	                            sesion.setAttribute("imagenDocente", imagen);
	                        }
                    	}
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    this.error = true;
                }
            } catch (Exception e) {

                e.printStackTrace();
                this.error = true;
            }

        } else {
            try {
                investigadorActual = (Investigador) sesion.getAttribute("investigadorBusqueda");
                if (investigadorActual == null) {
                    this.error = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                this.error = true;
            }
        }

        // por el momento se asigna la referencia de la url de colciencias y el
        // uid con el correo
        if (investigadorActual != null && !error) {
            dataListProyectosInvestigador = new HtmlDataList();
            dataListGruposInvestigador = new HtmlDataList();
            dataListAreasConocimiento = new HtmlDataList();

            // investigadorActual.crearUrlColciencias();
            investigadorActual.crearUidTemporal();

            ////////////
            cedula = investigadorActual.getId().getDocumento();
            actual = new File(path + cedula + ".jpg");
            try {
                investigadorInterno = servicioPersona.obtenerInvestigadorInterno(investigadorActual.getId());

                if (actual.exists()) {
                    bandera = true;

                } else {
                    bandera = false;

                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
            //////////

            Iterator it = investigadorActual.getProyectosInvestigador().iterator();
            proyectosInvestigador = new HashSet();

            while (it.hasNext()) {
                InvestigadorProyecto iproyecto = (InvestigadorProyecto) it.next();

                if (iproyecto != null && iproyecto.getVisible() != null && iproyecto.getVisible().equals("S")
                        && (iproyecto.getProyecto().getEstadoProyecto().getId().equals("A")
                                || iproyecto.getProyecto().getEstadoProyecto().getId().equals("AP")
                                || iproyecto.getProyecto().getEstadoProyecto().getId().equals("F"))) {
                    proyectosInvestigador.add(iproyecto);

                }

            }
        } else {
            error = true;
        }
    }

    private void cargarPaginaActual() {

        String emailPersona;
        int arroba = investigadorActual.getEmail().indexOf("@");
        if (arroba != -1) {

            emailPersona = investigadorActual.getEmail().substring(0, arroba);
        } else {
            emailPersona = investigadorActual.getEmail();
        }

        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();

        String viewId = "/pages/Docentes/Docente.xhtml";

        viewId = extContext.getRequestContextPath() + viewId + '?' + "u" + "=" + emailPersona;

        this.setPaginaActual(context.getExternalContext().encodeActionURL(viewId));
    }

    private void cargarPaginaControl() {

        String emailPersona;
        int arroba = investigadorActual.getEmail().indexOf("@");
        if (arroba != -1) {

            emailPersona = investigadorActual.getEmail().substring(0, arroba);
        } else {
            emailPersona = investigadorActual.getEmail();
        }

        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();

        String viewId = "/pages/Docentes/Docente.xhtml";

        viewId = extContext.getRequestContextPath() + viewId + '?' + "u" + "=" + emailPersona;

        this.setPaginaActual(context.getExternalContext().encodeActionURL(viewId));
    }

    public String buscarProyecto() {
        InvestigadorProyecto invProyecto = (InvestigadorProyecto) dataListProyectosInvestigador.getRowData();
        Proyecto proyecto = invProyecto.getProyecto();
        proyecto = servicioProyecto.obtenerResumenProyecto(proyecto.getId());
        IdPersona idInvestigador = proyecto.getResponsable().getId();
        InvestigadorInterno investigadorInterno = servicioPersona.obtenerInvestigadorInterno(idInvestigador);
        proyecto.setDependenciaPrincipal(investigadorInterno.getDependencia());
        sesion.setAttribute("proyectoBusqueda", proyecto);
        return "successProyecto";
    }

    public String buscarGrupo() {
        InvestigadorGrupo invGrupo = (InvestigadorGrupo) dataListGruposInvestigador.getRowData();
        Grupo grupo = invGrupo.getGrupo();
        grupo = servicioGrupo.obtenerResumenGrupo(grupo.getId());
        sesion.setAttribute("grupoBusqueda", grupo);
        return "successGrupo";
    }

    public String verPaginaDocente() {
        IdPersona id = investigadorActual.getId();
        return "successPaginaDocente";
    }

    public void obtenerEventosInvestigador(String tipo, String doc) {
        setListaEventos(new ArrayList<InvestigadorEvento>());
        try {
            setListaEventos(servicioGeneral.obtenerEventosInvestigador(tipo, doc));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void obtenerEnlacesInvestigador(String tipo, String doc) {
        setListaEnlaces(new ArrayList<InvestigadorEnlace>());
        try {
            setListaEnlaces(servicioGeneral.obtenerEnlacesInvestigador(tipo, doc));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void obtenerPublicacionesInvestigador(String tipo, String doc) {
        setListaPublicaciones(new ArrayList<InvestigadorPublicacion>());
        setListaPublicacionesInternacionales(new ArrayList<InvestigadorPublicacion>());
        setListaPublicacionesNacionales(new ArrayList<InvestigadorPublicacion>());
        setListaPublicacionesLibros(new ArrayList<InvestigadorPublicacion>());
        setListaPublicacionesTesis(new ArrayList<InvestigadorPublicacion>());
        setListaPublicacionesTesisPregrado(new ArrayList<InvestigadorPublicacion>());
        try {
            setListaPublicaciones(servicioGeneral.obtenerPublicacionesInvestigador(tipo, doc));
            if (listaPublicaciones != null && listaPublicaciones.size() > 0) {
                for (int i = 0; i < listaPublicaciones.size(); i++) {
                    if (listaPublicaciones.get(i).getTipo().equals("PUB_INT")) {
                        listaPublicacionesInternacionales.add(listaPublicaciones.get(i));
                    } else if (listaPublicaciones.get(i).getTipo().equals("PUB_NAC")) {
                        listaPublicacionesNacionales.add(listaPublicaciones.get(i));
                    } else if (listaPublicaciones.get(i).getTipo().equals("PUB_LIB")) {
                        listaPublicacionesLibros.add(listaPublicaciones.get(i));
                    } else if (listaPublicaciones.get(i).getTipo().equals("PUB_TES")) {
                        listaPublicacionesTesis.add(listaPublicaciones.get(i));
                    } else if (listaPublicaciones.get(i).getTipo().equals("PUB_TES_PR")) {
                        listaPublicacionesTesisPregrado.add(listaPublicaciones.get(i));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void obtenerAreasInvestigacionInvestigador(String tipo, String doc) {
        setListaAreasInvestigacionInvestigador(new ArrayList<InvestigadorAreaInvestigacion>());
        try {
            setListaAreasInvestigacionInvestigador(servicioGeneral.obtenerAreasInvestigacionInvestigador(tipo, doc));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void obtenerAreasInteresInvestigador(String tipo, String doc) {
        setListaAreasInteresInvestigador(new ArrayList<InvestigadorAreaInteres>());
        try {
            setListaAreasInteresInvestigador(servicioGeneral.obtenerAreasInteresInvestigador(tipo, doc));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void obtenerLaboratoriosInvestigador(String tipo, String doc) {
        setListaLaboratoriosAsociados(new ArrayList<Laboratorio>());
        try {
            setListaLaboratoriosAsociados(servicioGeneral.obtenerNombreLaboratoriosInvestigador(tipo, doc));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void obtenerAsignaturasInvestigador(String tipo, String doc) {

        try {
            setListaAsignaturasInvestigador(servicioGeneral.obtenerAsignaturasInvestigador(tipo, doc));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void obtenerObraExposicionInvestigador(String tipo, String doc) {
        setListaObraExposicion(new ArrayList<InvestigadorObraExposicion>());
        setListaObrasInvestigador(new ArrayList<InvestigadorObraExposicion>());
        setListaExposicionInvestigador(new ArrayList<InvestigadorObraExposicion>());
        try {
            setListaObraExposicion(servicioGeneral.obtenerObraExposicionInvestigador(tipo, doc));
            if (listaObraExposicion != null && listaObraExposicion.size() > 0) {
                for (int i = 0; i < listaObraExposicion.size(); i++) {
                    if (listaObraExposicion.get(i).isObra()) {
                        listaObrasInvestigador.add(listaObraExposicion.get(i));
                    } else {
                        listaExposicionInvestigador.add(listaObraExposicion.get(i));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String consultarPaginaPersona() {
        String emailPersona;
        int arroba = investigadorActual.getEmail().indexOf("@");
        if (arroba != -1) {

            emailPersona = investigadorActual.getEmail().substring(0, arroba);
        } else {
            emailPersona = investigadorActual.getEmail();
        }

        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        String viewId = "/pages/Docentes/Docente.jsf";
        try {
            viewId = extContext.getRequestContextPath() + viewId + '?' + "u" + "=" + emailPersona;
            String urlLink = context.getExternalContext().encodeActionURL(viewId);
            extContext.redirect(urlLink);
        } catch (IOException e) {
            extContext.log(getClass().getName() + ".invokeRedirect", e);
        }
        return null;
    }

    public void reporteInvestigadorBusqueda() throws SQLException {
        String id = investigadorInterno.getId().getDocumento().toString();

        ReporteBirt r = new ReporteBirt();
        r.adicionarParametro("inv", id);
        r.setNombreReporte("/portafolio/Investigador");
        r.setFormato(ReporteBirt.FORMATO_PDF);
        sesion.setAttribute("reporte", r);
        FacesContext context = FacesContext.getCurrentInstance();
        r.run(context);
    }

    public void reporteHojaVida() throws SQLException {

        if (idPersona == null) {
            idPersona = cedula;
        }

        ReporteBirt r = new ReporteBirt();
        r.adicionarParametro("inv", idPersona);
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

    public Investigador getInvestigadorActual() {
        return investigadorActual;
    }

    public void setInvestigadorActual(Investigador investigadorActual) {
        this.investigadorActual = investigadorActual;
    }

    public HtmlDataList getDataListProyectosInvestigador() {
        return dataListProyectosInvestigador;
    }

    public void setDataListProyectosInvestigador(HtmlDataList dataListProyectosInvestigador) {
        this.dataListProyectosInvestigador = dataListProyectosInvestigador;
    }

    public HtmlDataList getDataListGruposInvestigador() {
        return dataListGruposInvestigador;
    }

    public void setDataListGruposInvestigador(HtmlDataList dataListGruposInvestigador) {
        this.dataListGruposInvestigador = dataListGruposInvestigador;
    }

    public HtmlDataList getDataListAreasConocimiento() {
        return dataListAreasConocimiento;
    }

    public void setDataListAreasConocimiento(HtmlDataList dataListAreasConocimiento) {
        this.dataListAreasConocimiento = dataListAreasConocimiento;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public boolean isError() {
        return error;
    }

    public String getInformacionBasica() {
        return "";
    }

    public String getTituloActual() {
        return "titulo_info_general.png";
    }

    public boolean isBandera() {
        return bandera;
    }

    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }

    public StreamedContent getImagen() {
        StreamedContent imagen2 = null;
        try {
            imagen2 = (StreamedContent) sesion.getAttribute("imagenDocente");
        } catch (Exception e) {
            // e.printStackTrace();
        }
        if (imagen2 != null) {
            return imagen2;
        } else {
            try {
                imagen = new DefaultStreamedContent(
                        new ByteArrayInputStream(org.apache.commons.io.FileUtils.readFileToByteArray(actual)),
                        "image/jpg");
            } catch (IOException e) {
            	imagen = null;
                // e.printStackTrace();
            }
            return imagen;
        }
    }

    public void setImagen(StreamedContent imagen) {
        this.imagen = imagen;
    }

    public File getActual() {
        return actual;
    }

    public void setActual(File actual) {
        this.actual = actual;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public InvestigadorInterno getInvestigadorInterno() {
        return investigadorInterno;
    }

    public void setInvestigadorInterno(InvestigadorInterno investigadorInterno) {
        this.investigadorInterno = investigadorInterno;
    }

    public Set getProyectosInvestigador() {
        return proyectosInvestigador;
    }

    public void setProyectosInvestigador(Set proyectosInvestigador) {
        this.proyectosInvestigador = proyectosInvestigador;
    }

    public List<InvestigadorEvento> getListaEventos() {
        return listaEventos;
    }

    public void setListaEventos(List<InvestigadorEvento> listaEventos) {
        this.listaEventos = listaEventos;
    }

    public List<InvestigadorEnlace> getListaEnlaces() {
        return listaEnlaces;
    }

    public void setListaEnlaces(List<InvestigadorEnlace> listaEnlaces) {
        this.listaEnlaces = listaEnlaces;
    }

    public List<InvestigadorPublicacion> getListaPublicacionesInternacionales() {
        return listaPublicacionesInternacionales;
    }

    public void setListaPublicacionesInternacionales(List<InvestigadorPublicacion> listaPublicacionesInternacionales) {
        this.listaPublicacionesInternacionales = listaPublicacionesInternacionales;
    }

    public List<InvestigadorPublicacion> getListaPublicacionesNacionales() {
        return listaPublicacionesNacionales;
    }

    public void setListaPublicacionesNacionales(List<InvestigadorPublicacion> listaPublicacionesNacionales) {
        this.listaPublicacionesNacionales = listaPublicacionesNacionales;
    }

    public List<InvestigadorPublicacion> getListaPublicacionesLibros() {
        return listaPublicacionesLibros;
    }

    public void setListaPublicacionesLibros(List<InvestigadorPublicacion> listaPublicacionesLibros) {
        this.listaPublicacionesLibros = listaPublicacionesLibros;
    }

    public List<InvestigadorPublicacion> getListaPublicacionesTesis() {
        return listaPublicacionesTesis;
    }

    public void setListaPublicacionesTesis(List<InvestigadorPublicacion> listaPublicacionesTesis) {
        this.listaPublicacionesTesis = listaPublicacionesTesis;
    }

    public List<InvestigadorPublicacion> getListaPublicaciones() {
        return listaPublicaciones;
    }

    public void setListaPublicaciones(List<InvestigadorPublicacion> listaPublicaciones) {
        this.listaPublicaciones = listaPublicaciones;
    }

    public List<InvestigadorAreaInvestigacion> getListaAreasInvestigacionInvestigador() {
        return listaAreasInvestigacionInvestigador;
    }

    public void setListaAreasInvestigacionInvestigador(
            List<InvestigadorAreaInvestigacion> listaAreasInvestigacionInvestigador) {
        this.listaAreasInvestigacionInvestigador = listaAreasInvestigacionInvestigador;
    }

    public List<InvestigadorAreaInteres> getListaAreasInteresInvestigador() {
        return listaAreasInteresInvestigador;
    }

    public void setListaAreasInteresInvestigador(List<InvestigadorAreaInteres> listaAreasInteresInvestigador) {
        this.listaAreasInteresInvestigador = listaAreasInteresInvestigador;
    }

    public List<Laboratorio> getListaLaboratoriosAsociados() {
        return listaLaboratoriosAsociados;
    }

    public void setListaLaboratoriosAsociados(List<Laboratorio> listaLaboratoriosAsociados) {
        this.listaLaboratoriosAsociados = listaLaboratoriosAsociados;
    }

    public List<InvestigadorAsignatura> getListaAsignaturasInvestigador() {
        return listaAsignaturasInvestigador;
    }

    public void setListaAsignaturasInvestigador(List<InvestigadorAsignatura> listaAsignaturasInvestigador) {
        this.listaAsignaturasInvestigador = listaAsignaturasInvestigador;
    }

    public List<InvestigadorObraExposicion> getListaObraExposicion() {
        return listaObraExposicion;
    }

    public void setListaObraExposicion(List<InvestigadorObraExposicion> listaObraExposicion) {
        this.listaObraExposicion = listaObraExposicion;
    }

    public List<InvestigadorObraExposicion> getListaObrasInvestigador() {
        return listaObrasInvestigador;
    }

    public void setListaObrasInvestigador(List<InvestigadorObraExposicion> listaObrasInvestigador) {
        this.listaObrasInvestigador = listaObrasInvestigador;
    }

    public List<InvestigadorObraExposicion> getListaExposicionInvestigador() {
        return listaExposicionInvestigador;
    }

    public void setListaExposicionInvestigador(List<InvestigadorObraExposicion> listaExposicionInvestigador) {
        this.listaExposicionInvestigador = listaExposicionInvestigador;
    }

    public String getPaginaActual() {
        return paginaActual;
    }

    public void setPaginaActual(String paginaActual) {
        this.paginaActual = paginaActual;
    }

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public List<InvestigadorPublicacion> getListaPublicacionesTesisPregrado() {
        return listaPublicacionesTesisPregrado;
    }

    public void setListaPublicacionesTesisPregrado(List<InvestigadorPublicacion> listaPublicacionesTesisPregrado) {
        this.listaPublicacionesTesisPregrado = listaPublicacionesTesisPregrado;
    }

}
