package co.edu.unal.hermes.vista.busqueda;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.HistoricoBusqueda;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorHistoricoBusqueda extends ManejadorBase {

    /**
     * 
     */
    private static final long serialVersionUID = -329349831021769001L;
    private Date fechaInicio;
    private Date fechaFin;
    private String selItem;
    List listaCategorias = new ArrayList<SelectItem>();

    private String[] selectedCategorias;
    private String categorias;
    List<HistoricoBusqueda> listaBusquedas = new ArrayList<HistoricoBusqueda>();

    private List filteredCategorias;
    private Long totalBusquedas = 0L;
    private int totalUsoBuscador = 0;
    private int totalCatColecciones = 0;
    private int totalCatConvocatoria = 0;
    private int totalCatEcp = 0;
    private int totalCatGrupos = 0;
    private int totalCatInvestigadores = 0;
    private int totalCatLaboratorios = 0;
    private int totalCatProyectos = 0;
    private int totalCatTodas = 0;

    public ManejadorHistoricoBusqueda() {
        super();
        obtenerCategoriasBuscador();
        totalBusquedas();
    }

    public void totalBusquedas() {
        List cantidadFilas = servicioGeneral.obtenerObjetos("select count(*) from HistoricoBusqueda");
        if (!esListaVacia(cantidadFilas)) {
            setTotalUsoBuscador((Integer) cantidadFilas.get(0));
        }

        List cantidadCol = servicioGeneral
                .obtenerObjetos("select count(*) from HistoricoBusqueda hb where hb.categoria = '7'");
        if (!esListaVacia(cantidadCol)) {
            totalCatColecciones = (Integer) cantidadCol.get(0);
        }

        List cantidadConvoca = servicioGeneral
                .obtenerObjetos("select count(*) from HistoricoBusqueda hb where hb.categoria = '6'");
        if (!esListaVacia(cantidadConvoca)) {
            totalCatConvocatoria = (Integer) cantidadConvoca.get(0);
        }

        List cantidadEcp = servicioGeneral
                .obtenerObjetos("select count(*) from HistoricoBusqueda hb where hb.categoria = '5'");
        if (!esListaVacia(cantidadEcp)) {
            totalCatEcp = (Integer) cantidadEcp.get(0);
        }

        List cantidadGru = servicioGeneral
                .obtenerObjetos("select count(*) from HistoricoBusqueda hb where hb.categoria = '3'");
        if (!esListaVacia(cantidadGru)) {
            totalCatGrupos = (Integer) cantidadGru.get(0);
        }

        List cantidadInv = servicioGeneral
                .obtenerObjetos("select count(*) from HistoricoBusqueda hb where hb.categoria = '1'");
        if (!esListaVacia(cantidadInv)) {
            totalCatInvestigadores = (Integer) cantidadInv.get(0);
        }

        List cantidadLab = servicioGeneral
                .obtenerObjetos("select count(*) from HistoricoBusqueda hb where hb.categoria = '4'");
        if (!esListaVacia(cantidadLab)) {
            totalCatLaboratorios = (Integer) cantidadLab.get(0);
        }

        List cantidadPro = servicioGeneral
                .obtenerObjetos("select count(*) from HistoricoBusqueda hb where hb.categoria = '2'");
        if (!esListaVacia(cantidadPro)) {
            totalCatProyectos = (Integer) cantidadPro.get(0);
        }

        List cantidadTod = servicioGeneral
                .obtenerObjetos("select count(*) from HistoricoBusqueda hb where hb.categoria = '0'");
        if (!esListaVacia(cantidadTod)) {
            totalCatTodas = (Integer) cantidadTod.get(0);
        }

    }

    public void obtenerCategoriasBuscador() {
        String consulta = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.id = '123' "
                + " order by dd.descripcion";
        List lista = servicioGeneral.obtenerObjetos(DominioDetalle.class,consulta);

        if (!esListaVacia(lista)) {
            for (int i = 0; i < lista.size(); i++) {
                DominioDetalle dd = (DominioDetalle) lista.get(i);
                listaCategorias.add(new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion()));
            }
        }
    }

    public void categoriasSeleccionadas() {

        categorias = "'";
        for (String valor : selectedCategorias) {
            categorias = categorias + valor + "','";
        }
        categorias = categorias + "'";

    }

    public void consultarRegistros() {

        if (this.fechaInicio != null && this.fechaFin != null) {
            if (!this.fechaInicio.before(this.fechaFin)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "La fecha Final debe ser posterior a la de Inicio", ""));
                return;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe indicar el rango de fechas del reporte", ""));
            return;
        }

        categoriasSeleccionadas();

        if (esCadenaVacia(categorias) || "''".equals(categorias)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar al menos una categoría", ""));
            return;
        }

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yy");
        String fechainicial = sdf.format(fechaInicio);
        String fechafinal = sdf.format(sumarRestarDiasFecha(fechaFin, 1));

        String consultaBusquedas = "select #id hb.id, #categoria hb.categoria, "
                + "#palabra hb.palabra, #fechaBusqueda hb.fechaBusqueda, #nombreCategoria dd.descripcion "
                + "from HistoricoBusqueda hb, DominioDetalle dd "
                + "where dd.identificador.id = '123' and dd.identificador.tipo = hb.categoria and hb.categoria in ("
                + categorias + ")" + " and to_date(hb.fechaBusqueda,'DD/MM/YY') >= to_date('" + fechainicial
                + "','DD/MM/YY') and to_date(hb.fechaBusqueda,'DD/MM/YY') < to_date('" + fechafinal
                + "','DD/MM/YY') order by hb.fechaBusqueda desc";

        listaBusquedas = servicioGeneral.obtenerObjetosLimitado(HistoricoBusqueda.class, consultaBusquedas);

        if (!esListaVacia(listaBusquedas)) {
            totalBusquedas = (long) listaBusquedas.size();
        }

    }

    public Date sumarRestarDiasFecha(Date fecha, int dias) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, dias);

        return calendar.getTime();

    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getSelItem() {
        return selItem;
    }

    public void setSelItem(String selItem) {
        this.selItem = selItem;
    }

    public String getCategorias() {
        return categorias;
    }

    public void setCategorias(String categorias) {
        this.categorias = categorias;
    }

    public String[] getSelectedCategorias() {
        return selectedCategorias;
    }

    public void setSelectedCategorias(String[] selectedCategorias) {
        this.selectedCategorias = selectedCategorias;
    }

    public List getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    public List getFilteredCategorias() {
        return filteredCategorias;
    }

    public void setFilteredCategorias(List filteredCategorias) {
        this.filteredCategorias = filteredCategorias;
    }

    public Long getTotalBusquedas() {
        return totalBusquedas;
    }

    public void setTotalBusquedas(Long totalBusquedas) {
        this.totalBusquedas = totalBusquedas;
    }

    public List<HistoricoBusqueda> getListaBusquedas() {
        return listaBusquedas;
    }

    public void setListaBusquedas(List<HistoricoBusqueda> listaBusquedas) {
        this.listaBusquedas = listaBusquedas;
    }

    public int getTotalUsoBuscador() {
        return totalUsoBuscador;
    }

    public void setTotalUsoBuscador(int totalUsoBuscador) {
        this.totalUsoBuscador = totalUsoBuscador;
    }

    public int getTotalCatColecciones() {
        return totalCatColecciones;
    }

    public void setTotalCatColecciones(int totalCatColecciones) {
        this.totalCatColecciones = totalCatColecciones;
    }

    public int getTotalCatConvocatoria() {
        return totalCatConvocatoria;
    }

    public void setTotalCatConvocatoria(int totalCatConvocatoria) {
        this.totalCatConvocatoria = totalCatConvocatoria;
    }

    public int getTotalCatEcp() {
        return totalCatEcp;
    }

    public void setTotalCatEcp(int totalCatEcp) {
        this.totalCatEcp = totalCatEcp;
    }

    public int getTotalCatGrupos() {
        return totalCatGrupos;
    }

    public void setTotalCatGrupos(int totalCatGrupos) {
        this.totalCatGrupos = totalCatGrupos;
    }

    public int getTotalCatInvestigadores() {
        return totalCatInvestigadores;
    }

    public void setTotalCatInvestigadores(int totalCatInvestigadores) {
        this.totalCatInvestigadores = totalCatInvestigadores;
    }

    public int getTotalCatLaboratorios() {
        return totalCatLaboratorios;
    }

    public void setTotalCatLaboratorios(int totalCatLaboratorios) {
        this.totalCatLaboratorios = totalCatLaboratorios;
    }

    public int getTotalCatProyectos() {
        return totalCatProyectos;
    }

    public void setTotalCatProyectos(int totalCatProyectos) {
        this.totalCatProyectos = totalCatProyectos;
    }

    public int getTotalCatTodas() {
        return totalCatTodas;
    }

    public void setTotalCatTodas(int totalCatTodas) {
        this.totalCatTodas = totalCatTodas;
    }

}