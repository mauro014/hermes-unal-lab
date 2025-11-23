package co.edu.unal.hermes.vista.colecciones;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import co.edu.unal.hermes.modelo.Coleccion;
import co.edu.unal.hermes.modelo.ColeccionPersona;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorColeccionBusqueda extends ManejadorBase {

    /**
     * 
     */
    private static final long serialVersionUID = -8659926199440535949L;
    private String paginaActual;
    private Coleccion coleccionActual = null;
    private boolean error;
    public StreamedContent imagen;
    private List<DominioDetalle> listaTiposColecciones;
    private DominioDetalle tipoColeccion = null;
    private List<ColeccionPersona> listaPersonal;
    private String idColeccion;

    File actual;
    String path = RUTA_ARCHIVOS + File.separator + "HER_COLECCION" + File.separator;
    boolean bandera = true;

    public ManejadorColeccionBusqueda() throws SQLException {

        if (idColeccion != null) {
            reporteColeccion();
            idColeccion = null;
        } else {
            error = false;
            cargarColeccion();

            if (!error) {
                cargarPaginaActual();
            }
        }
    }

    private void cargarColeccion() {

        this.error = false;
        if (this.request.getParameter("idColeccion") != null && !this.request.getParameter("idColeccion").equals("")) {
            try {
                coleccionActual = servicioGeneral.obtenerColeccion(new Long(this.request.getParameter("idColeccion")));
                if (coleccionActual != null) {
                    actual = new File(path + coleccionActual.getImagen() + ".jpg");
                    try {
                        tipoColeccion();
                        imagen = new DefaultStreamedContent(
                                new ByteArrayInputStream(org.apache.commons.io.FileUtils.readFileToByteArray(actual)),
                                "image/jpg");

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception i) {
                        i.printStackTrace();
                    }

                    if (imagen != null) {
                        sesion.setAttribute("imagenColeccion", imagen);
                    }

                    if (actual.exists()) {
                        bandera = true;
                    } else {
                        bandera = false;
                    }
                }else{
                    this.error = true;
                }
            } catch (NumberFormatException nfe) {
                this.error = true;
            }
        } else {
            coleccionActual = (Coleccion) sesion.getAttribute("coleccionBusqueda");
            sesion.removeAttribute("coleccionBusqueda");
            if (coleccionActual == null) {
                this.error = true;
            }
        }
    }

    private void cargarPaginaActual() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();

        String viewId = "/pages/Consultas/Coleccion.xhtml";

        viewId = extContext.getRequestContextPath() + viewId + '?' + "idColeccion" + "=" + coleccionActual.getId();

        this.paginaActual = context.getExternalContext().encodeActionURL(viewId);
    }

    public void reporteColeccion() throws SQLException {

        if (idColeccion == null) {
            idColeccion = coleccionActual.getId().toString();
        }
        ReporteBirt r = new ReporteBirt();
        String foto = "1";
        if (!bandera) {
            foto = "0";
        }
        r.adicionarParametro("id", idColeccion);
        r.adicionarParametro("foto", foto);
        r.setNombreReporte("/colecciones/reporte-coleccion");
        r.setFormato(ReporteBirt.FORMATO_PDF);
        sesion.setAttribute("reporte", r);
        FacesContext context = FacesContext.getCurrentInstance();
        r.run(context);
    }

    public String getPaginaActual() {
        return paginaActual;
    }

    public void setPaginaActual(String paginaActual) {
        this.paginaActual = paginaActual;
    }

    public Coleccion getColeccionActual() {
        return coleccionActual;
    }

    public void setColeccionActual(Coleccion coleccionActual) {
        this.coleccionActual = coleccionActual;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public boolean getVisibleDescripcion() {
        return coleccionActual.getDescripcion() != null && coleccionActual.getDescripcion().trim().length() > 0;
    }

    public boolean getVisibleDireccion() {
        return coleccionActual.getDireccion() != null && coleccionActual.getDireccion().trim().length() > 0;
    }

    public boolean getVisibleEmail() {
        return coleccionActual.getEmail() != null && coleccionActual.getEmail().trim().length() > 0;
    }

    public boolean getVisibleTelefono() {
        return coleccionActual.getTelefono() != null && coleccionActual.getTelefono().trim().length() > 0;
    }

    public boolean getVisibleAcronimo() {
        return coleccionActual.getAcronimo() != null && coleccionActual.getAcronimo().trim().length() > 0;
    }

    public boolean getVisibleTipo() {
        return tipoColeccion != null && tipoColeccion.getDescripcion() != null
                && tipoColeccion.getDescripcion().trim().length() > 0;
    }

    public StreamedContent getImagen() {
        StreamedContent imagen2 = (StreamedContent) sesion.getAttribute("imagenColeccion");
        if (imagen2 != null) {
            return imagen2;
        } else {
            try {
                imagen = new DefaultStreamedContent(
                        new ByteArrayInputStream(org.apache.commons.io.FileUtils.readFileToByteArray(actual)),
                        "image/jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (imagen != null) {
                sesion.setAttribute("imagenColeccion", imagen);
                return imagen;
            }
        }
        return null;
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

    public boolean isBandera() {
        return bandera;
    }

    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }

    public String getInformacionBasica() {
        return this.paginaActual + "&opcion=1";
    }

    public void setListaTiposColecciones(List<DominioDetalle> listaTiposColecciones) {
        this.listaTiposColecciones = listaTiposColecciones;
    }

    public List<DominioDetalle> getListaTiposColecciones() {
        return listaTiposColecciones;
    }

    public void setTipoColeccion(DominioDetalle tipoColeccion) {
        this.tipoColeccion = tipoColeccion;
    }

    public DominioDetalle tipoColeccion() {
        listaTiposColecciones = servicioGeneral
                .obtenerListaObjetos("DominioDetalle where identificador.id = '99' and identificador.tipo = '"
                        + coleccionActual.getTipo() + "'");

        if (!esListaVacia(listaTiposColecciones)) {
            tipoColeccion = (DominioDetalle) listaTiposColecciones.get(0);
        }
        return tipoColeccion;
    }

    public DominioDetalle getTipoColeccion() {
        return tipoColeccion;
    }

    public Dependencia getUnidadAcademicaBasica() {

        try {
            String dpnsql = "select e from Dependencia e where e.id = '" + coleccionActual.getUab() + "'";
            List<Dependencia> dependenciasUN2 = servicioGeneral.obtenerObjetos(Dependencia.class, dpnsql);
            Dependencia dd = new Dependencia();
            if (!esListaVacia(dependenciasUN2)) {
                dd = (Dependencia) dependenciasUN2.get(0);
                return dd;
            } else {
                return dd;
            }

        } catch (Exception e) {
            return null;
        }
    }

    public List<ColeccionPersona> getListaPersonal() {
        listaPersonal = servicioGeneral.obtenerObjetos("from ColeccionPersona cp where cp.coleccion.id = '"
                + coleccionActual.getId() + "' and cp.tipoPersona not in ('" + ColeccionPersona.CURADOR_GENERAL + "')");
        if (!esListaVacia(listaPersonal)) {
            listaPersonal.get(0).setPrimeraPersona(true);
        }
        return listaPersonal;
    }
}
