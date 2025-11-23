package co.edu.unal.hermes.vista.colecciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.Coleccion;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.HistoricoBusqueda;
import co.edu.unal.hermes.utils.ReemplazaAcentos;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorBuscadorColecciones extends ManejadorBase {

    /**
     * 
     */
    private static final long serialVersionUID = 4106826633968401346L;

    // Determina si la busqueda ya fue realizada
    boolean banderaResultadosBusqueda = false;

    // Busqueda segun opciones
    int tipo;
    private String sedeAgregar;
    private String facultadAgregar;
    private String tipoColeccionAgregar;
    private String campoBusqueda;

    // Opciones tipo busqueda
    private SelectItem[] tipoItem = { new SelectItem(new Integer(0), "Seleccione"),
            new SelectItem(new Integer(1), "Colecciones"), new SelectItem(new Integer(2), "Especímenes"), };

    private SelectItem[] listaSedesItem;
    private SelectItem[] listaFacultadesItem;
    private SelectItem[] listaTiposColeccionesItem;

    // Listado de filtros aplicados
    private List<Dependencia> listaFacultadesFiltro;
    private List<Dependencia> listaSedesFiltro;
    private List<DominioDetalle> listaTiposColeccionesFiltro;

    // Listados encontrados
    private List<Coleccion> listaColecciones;

    // Objetos de busqueda
    private Coleccion coleccionSeleccionada;
    private List<Dependencia> listaSedes;
    private List<Dependencia> listaFacultades;
    private List<DominioDetalle> listaTiposColecciones;
    private Dependencia sedeSeleccionada;
    private Dependencia facultadSeleccionada;
    private DominioDetalle tipoColeccionSeleccionado;

    // Mostrar opciones
    private Boolean mostrarAgregarSede;
    private Boolean mostrarAgregarFacultad;
    private Boolean mostrarAgregarTipoColeccion;

    private HistoricoBusqueda historicoBusqueda;
    private final int TIPOCOL = 7;

    public ManejadorBuscadorColecciones() {
        // Inicialización valores básicos
        banderaResultadosBusqueda = false;
        mostrarAgregarSede = false;
        mostrarAgregarFacultad = false;
        mostrarAgregarTipoColeccion = false;
    }

    boolean banderaBusqueda = false;

    public boolean isBanderaBusqueda() {
        return banderaBusqueda;
    }

    public void setBanderaBusqueda(boolean banderaBusqueda) {
        this.banderaBusqueda = banderaBusqueda;
    }

    public void buscar() {

        historicoBusqueda = new HistoricoBusqueda();
        historicoBusqueda.setFechaBusqueda(new Date());
        historicoBusqueda.setCategoria(String.valueOf(TIPOCOL));

        banderaBusqueda = true;

        if (mostrarAgregarSede) {
            if (!"".equals(sedeAgregar.trim())) {
                boolean esta = false;
                if (listaSedesFiltro == null) {
                    listaSedesFiltro = new ArrayList<Dependencia>();
                } else {
                    for (Dependencia sedeBucle : listaSedesFiltro) {
                        if (sedeBucle.getId().trim().equals(sedeAgregar.trim())) {
                            esta = true;
                            break;
                        }
                    }
                }
                if (!esta) {
                    Dependencia sede = encontrarSede(sedeAgregar);
                    if (sede != null) {
                        listaSedesFiltro.add(sede);
                    }
                }
            }
            mostrarAgregarSede = false;
        }
        if (mostrarAgregarFacultad) {
            if (!esCadenaVacia(facultadAgregar)) {
                boolean esta = false;
                if (listaFacultadesFiltro == null) {
                    listaFacultadesFiltro = new ArrayList<Dependencia>();
                } else {
                    for (Dependencia facultadBucle : listaFacultadesFiltro) {
                        if (facultadBucle.getId().trim().equals(facultadAgregar.trim())) {
                            esta = true;
                            break;
                        }
                    }
                }
                if (!esta) {
                    Dependencia facultad = encontrarFacultad(facultadAgregar);
                    if (facultad != null) {
                        listaFacultadesFiltro.add(facultad);
                    }
                }
            }
            mostrarAgregarFacultad = false;
        }
        if (mostrarAgregarTipoColeccion) {
            if (!esCadenaVacia(tipoColeccionAgregar)) {
                boolean esta = false;
                if (listaTiposColeccionesFiltro == null) {
                    listaTiposColeccionesFiltro = new ArrayList<DominioDetalle>();
                } else {
                    for (DominioDetalle tipoBucle : listaTiposColeccionesFiltro) {
                        if (tipoBucle.getIdentificador().getId().equals(tipoColeccionAgregar.trim())) {
                            esta = true;
                            break;
                        }
                    }
                }
                if (!esta) {

                    DominioDetalle nuevoTipoColeccion = null;

                    for (DominioDetalle tipoCol : listaTiposColecciones) {
                        if (tipoColeccionAgregar.trim().equals(tipoCol.getIdentificador().getTipo().trim())) {
                            nuevoTipoColeccion = tipoCol;
                            break;
                        }
                    }
                    if (nuevoTipoColeccion != null) {
                        listaTiposColeccionesFiltro.add(nuevoTipoColeccion);
                    }
                }
                mostrarAgregarTipoColeccion = false;
                tipoColeccionAgregar = "";
            }
        }

        if (!esCadenaVacia(campoBusqueda)) {
            historicoBusqueda.setPalabra(campoBusqueda.trim());
        }

        servicioGeneral.guardarObjeto(historicoBusqueda);

        busquedaColecciones();
        banderaResultadosBusqueda = true;
    }

    public Dependencia encontrarSede(String id) {
        if (!esListaVacia(listaSedes)) {
            for (Dependencia sede : listaSedes) {
                if (sede.getId().equals(id)) {
                    return sede;
                }
            }
        }
        return null;
    }

    public Dependencia encontrarFacultad(String id) {
        if (!esListaVacia(listaFacultades)) {
            for (Dependencia facultad : listaFacultades) {
                if (facultad.getId().equals(id)) {
                    return facultad;
                }
            }
        }
        return null;
    }

    public void busquedaColecciones() {
        String sql = "";

        // Busqueda por sede
        if (!esListaVacia(listaSedesFiltro)) {
            if (sql.length() > 0) {
                sql = sql + " and ";
            }
            sql += " ( ";
            boolean primero = true;
            for (Dependencia sede : listaSedesFiltro) {
                if (!primero) {
                    sql += " or ";
                }
                primero = false;
                sql += " HER_DEPENDENCIA.SED_ID = " + sede.getId();
            }
            sql += " ) ";
        }

        // Busqueda por facultad
        if (!esListaVacia(listaFacultadesFiltro)) {
            if (sql.length() > 0) {
                sql = sql + " and ";
            }
            sql += " ( ";
            boolean primero = true;
            for (Dependencia facultad : listaFacultadesFiltro) {
                if (!primero) {
                    sql += " or ";
                }
                primero = false;
                sql += " HER_COLECCION.DPN_ID= " + facultad.getId() + " or HER_DEPENDENCIA.DPN_ID_2 = "
                        + facultad.getId();
            }
            sql += " ) ";
        }

        // Busqueda por nombre y descripcion
        if (!esCadenaVacia(campoBusqueda)) {
            if (sql.length() > 0)
                sql = sql + " and ";
            List<String> cadena = ReemplazaAcentos.listaPalabrasConTildes(this.campoBusqueda.toUpperCase());
            String palabra2 = ReemplazaAcentos.quitarTildes(this.campoBusqueda.toUpperCase());
            cadena.add(palabra2);
            if (cadena.size() > 0) {
                sql += "(";
                boolean primera = true;
                for (String palabra : cadena) {
                    if (!primera) {
                        sql += " or ";
                    } else
                        primera = false;
                    sql += " (upper(HER_COLECCION.COL_NOMBRE) like '%" + palabra + "%'"
                            + " or upper(HER_COLECCION.COL_DESCRIPCION) like '%" + palabra + "%') ";
                }
                sql += ")";
            }

        }
        // busqueda por tipo de coleccion
        String fromSql = "";

        if (!esListaVacia(listaTiposColeccionesFiltro)) {
            if (sql.length() > 0) {
                sql = sql + " and ";
            }
            sql += " ( ";
            boolean primero = true;
            for (DominioDetalle tipoCol : listaTiposColeccionesFiltro) {
                if (!primero) {
                    sql += " or ";
                }
                primero = false;
                sql += "HER_COLECCION.COL_TIPO = '" + tipoCol.getIdentificador().getTipo() + "'";
            }
            sql += " ) ";
        }

        if (sql.length() > 0)
            sql = fromSql + " where " + sql;

        listaColecciones = this.servicioGeneral.obtenerColeccionBuscador(sql);
    }

    public List<DominioDetalle> getListaTiposColeccionesFiltro() {
        return listaTiposColeccionesFiltro;
    }

    public void setListaTiposColeccionesFiltro(List<DominioDetalle> listaTiposColeccionesFiltro) {
        this.listaTiposColeccionesFiltro = listaTiposColeccionesFiltro;
    }

    public void desactivarAgregarGeneral() {
        mostrarAgregarFacultad = false;
        mostrarAgregarSede = false;
        mostrarAgregarTipoColeccion = false;
    }

    private void cargarOpcionesSedes() {
        if (listaSedesItem != null && listaSedesItem.length > 0) {
            return;
        } else {
            listaSedes = (List<Dependencia>) servicioGeneral.obtenerSedes();
            listaSedesItem = new SelectItem[listaSedes.size() + 1];
            listaSedesItem[0] = new SelectItem("", "Seleccione sede");
            for (int i = 1; i < listaSedes.size() + 1; i++) {
                Dependencia sede = (Dependencia) listaSedes.get(i - 1);
                String nombreSede = sede.getNombre().substring(0, 1).toUpperCase()
                        + sede.getNombre().substring(1, sede.getNombre().length());
                listaSedesItem[i] = new SelectItem(sede.getId(), nombreSede);
            }
        }
    }

    private void cargarOpcionesTiposColecciones() {
        if (listaTiposColeccionesItem != null && listaTiposColeccionesItem.length > 0) {
            return;
        } else {
            listaTiposColecciones = servicioGeneral
                    .obtenerListaObjetos("DominioDetalle where identificador.id = '99' ORDER BY identificador.tipo");
            if (!esListaVacia(listaTiposColecciones)) {
                listaTiposColeccionesItem = new SelectItem[listaTiposColecciones.size() + 1];
                listaTiposColeccionesItem[0] = new SelectItem("", "Seleccione tipo");
                for (int i = 1; i <= listaTiposColecciones.size(); i++) {
                    DominioDetalle tipoCol = (DominioDetalle) listaTiposColecciones.get(i - 1);
                    listaTiposColeccionesItem[i] = new SelectItem(tipoCol.getIdentificador().getTipo(),
                            tipoCol.getDescripcion());
                }
            }

        }
    }

    public List<DominioDetalle> getListaTiposColecciones() {
        return listaTiposColecciones;
    }

    public void setListaTiposColecciones(List<DominioDetalle> listaTiposColecciones) {
        this.listaTiposColecciones = listaTiposColecciones;
    }

    public void cargarNuevaSede() {
        desactivarAgregarGeneral();
        mostrarAgregarSede = true;
        sedeAgregar = "";
        cargarOpcionesSedes();
    }

    public void cargarNuevaFacultad() {
        desactivarAgregarGeneral();
        mostrarAgregarFacultad = true;
        facultadAgregar = "";
        cargarOpcionesSedes();
    }

    public void cargarNuevoTipoColeccion() {
        desactivarAgregarGeneral();
        mostrarAgregarTipoColeccion = true;
        tipoColeccionAgregar = "";
        cargarOpcionesTiposColecciones();
    }

    public void reiniciarOpcionesBusqueda() {
        sedeAgregar = "";
        facultadAgregar = "";
        tipoColeccionAgregar = "";
        banderaBusqueda = false;
    }

    public void eliminarSede() {
        reiniciarOpcionesBusqueda();
        Dependencia sedeActual = sedeSeleccionada;
        listaSedesFiltro.remove(sedeActual);
        buscar();
    }

    public void eliminarFacultad() {
        reiniciarOpcionesBusqueda();
        Dependencia facultadActual = facultadSeleccionada;
        listaFacultadesFiltro.remove(facultadActual);
        buscar();
    }

    public void eliminarTipoColeccion() {
        reiniciarOpcionesBusqueda();
        listaTiposColeccionesFiltro.remove(tipoColeccionSeleccionado);
        buscar();
    }

    public List<String> obtenerPalabraClaves(String nombre) {
        return (List<String>) servicioGeneral.obtenerLineasEmpezandoCon(nombre);
    }

    public int getNumeroResultados() {

        if (!esListaVacia(listaColecciones))
            return listaColecciones.size();
        else
            return 0;
    }

    public String consultarPaginaColeccion() {

        Coleccion coleccion = (Coleccion) coleccionSeleccionada;

        Long idColeccion = coleccion.getId();

        Coleccion coleccionActual;
        coleccionActual = servicioGeneral.obtenerColeccion(idColeccion);

        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        String viewId = "/pages/Consultas/Coleccion.xhtml";
        try {
            viewId = extContext.getRequestContextPath() + viewId + '?' + "idColeccion" + "=" + coleccionActual.getId();
            String urlLink = context.getExternalContext().encodeActionURL(viewId);
            extContext.redirect(urlLink);
        } catch (IOException e) {
            extContext.log(getClass().getName() + ".invokeRedirect", e);
        }
        return null;

    }

    public void cargarFacultadesSede() {
        facultadAgregar = "";
        if (!esCadenaVacia(sedeAgregar)) {
            listaFacultades = this.servicioGeneral.obtenerFacultades(new Dependencia(sedeAgregar));
            listaFacultadesItem = new SelectItem[listaFacultades.size() + 1];
            listaFacultadesItem[0] = new SelectItem("", "Seleccione facultad");
            for (int i = 1; i < listaFacultades.size() + 1; i++) {
                Dependencia facultad = (Dependencia) listaFacultades.get(i - 1);
                listaFacultadesItem[i] = new SelectItem(facultad.getId(), facultad.getNombre());
            }
        } else
            listaFacultadesItem = null;
    }

    public void reiniciarBusqueda() {
        tipo = 0;
        reiniciarOpcionesBusqueda();
        listaSedesFiltro = new ArrayList<Dependencia>();
        listaFacultadesFiltro = new ArrayList<Dependencia>();
        listaTiposColeccionesFiltro = new ArrayList<DominioDetalle>();
        listaColecciones = new ArrayList<Coleccion>();
        banderaResultadosBusqueda = false;
        banderaBusqueda = false;
        campoBusqueda = "";
    }

    public boolean isBanderaResultadosBusqueda() {
        return banderaResultadosBusqueda;
    }

    public void setBanderaResultadosBusqueda(boolean banderaResultadosBusqueda) {
        this.banderaResultadosBusqueda = banderaResultadosBusqueda;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getSedeAgregar() {
        return sedeAgregar;
    }

    public void setSedeAgregar(String sedeAgregar) {
        this.sedeAgregar = sedeAgregar;
    }

    public String getFacultadAgregar() {
        return facultadAgregar;
    }

    public void setFacultadAgregar(String facultadAgregar) {
        this.facultadAgregar = facultadAgregar;
    }

    public String getTipoColeccionAgregar() {
        return tipoColeccionAgregar;
    }

    public void setTipoColeccionAgregar(String tipoColeccionAgregar) {
        this.tipoColeccionAgregar = tipoColeccionAgregar;
    }

    public String getCampoBusqueda() {
        return campoBusqueda;
    }

    public void setCampoBusqueda(String campoBusqueda) {
        this.campoBusqueda = campoBusqueda;
    }

    public SelectItem[] getTipoItem() {
        return tipoItem;
    }

    public void setTipoItem(SelectItem[] tipoItem) {
        this.tipoItem = tipoItem;
    }

    public SelectItem[] getListaSedesItem() {
        return listaSedesItem;
    }

    public void setListaSedesItem(SelectItem[] listaSedesItem) {
        this.listaSedesItem = listaSedesItem;
    }

    public SelectItem[] getListaFacultadesItem() {
        return listaFacultadesItem;
    }

    public void setListaFacultadesItem(SelectItem[] listaFacultadesItem) {
        this.listaFacultadesItem = listaFacultadesItem;
    }

    public SelectItem[] getListaTiposColeccionesItem() {
        return listaTiposColeccionesItem;
    }

    public void setListaTiposColeccionesItem(SelectItem[] listaTiposColeccionesItem) {
        this.listaTiposColeccionesItem = listaTiposColeccionesItem;
    }

    public List<Dependencia> getListaFacultadesFiltro() {
        return listaFacultadesFiltro;
    }

    public void setListaFacultadesFiltro(List<Dependencia> listaFacultadesFiltro) {
        this.listaFacultadesFiltro = listaFacultadesFiltro;
    }

    public List<Dependencia> getListaSedesFiltro() {
        return listaSedesFiltro;
    }

    public void setListaSedesFiltro(List<Dependencia> listaSedesFiltro) {
        this.listaSedesFiltro = listaSedesFiltro;
    }

    public List<Coleccion> getListaColecciones() {
        return listaColecciones;
    }

    public void setListaColecciones(List<Coleccion> listaColecciones) {
        this.listaColecciones = listaColecciones;
    }

    public Coleccion getColeccionSeleccionada() {
        return coleccionSeleccionada;
    }

    public void setColeccionSeleccionada(Coleccion coleccionSeleccionada) {
        this.coleccionSeleccionada = coleccionSeleccionada;
    }

    public List<Dependencia> getListaSedes() {
        return listaSedes;
    }

    public void setListaSedes(List<Dependencia> listaSedes) {
        this.listaSedes = listaSedes;
    }

    public List<Dependencia> getListaFacultades() {
        return listaFacultades;
    }

    public void setListaFacultades(List<Dependencia> listaFacultades) {
        this.listaFacultades = listaFacultades;
    }

    public Dependencia getSedeSeleccionada() {
        return sedeSeleccionada;
    }

    public void setSedeSeleccionada(Dependencia sedeSeleccionada) {
        this.sedeSeleccionada = sedeSeleccionada;
    }

    public Dependencia getFacultadSeleccionada() {
        return facultadSeleccionada;
    }

    public void setFacultadSeleccionada(Dependencia facultadSeleccionada) {
        this.facultadSeleccionada = facultadSeleccionada;
    }

    public DominioDetalle getTipoColeccionSeleccionado() {
        return tipoColeccionSeleccionado;
    }

    public void setTipoColeccionSeleccionado(DominioDetalle tipoColeccionSeleccionado) {
        this.tipoColeccionSeleccionado = tipoColeccionSeleccionado;
    }

    public Boolean getMostrarAgregarSede() {
        return mostrarAgregarSede;
    }

    public void setMostrarAgregarSede(Boolean mostrarAgregarSede) {
        this.mostrarAgregarSede = mostrarAgregarSede;
    }

    public Boolean getMostrarAgregarFacultad() {
        return mostrarAgregarFacultad;
    }

    public void setMostrarAgregarFacultad(Boolean mostrarAgregarFacultad) {
        this.mostrarAgregarFacultad = mostrarAgregarFacultad;
    }

    public Boolean getMostrarAgregarTipoColeccion() {
        return mostrarAgregarTipoColeccion;
    }

    public void setMostrarAgregarTipoColeccion(Boolean mostrarAgregarTipoColeccion) {
        this.mostrarAgregarTipoColeccion = mostrarAgregarTipoColeccion;
    }

    public HistoricoBusqueda getHistoricoBusqueda() {
        return historicoBusqueda;
    }

    public void setHistoricoBusqueda(HistoricoBusqueda historicoBusqueda) {
        this.historicoBusqueda = historicoBusqueda;
    }

}
