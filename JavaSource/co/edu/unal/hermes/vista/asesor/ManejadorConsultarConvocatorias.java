package co.edu.unal.hermes.vista.asesor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;

import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.TipoMovilidad;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorConsultarConvocatorias extends ManejadorBase {

    String nombreConvocatoria = "";
    List listaConvocatorias;
    List listaHijo;
    private DataTable tablaConvocatorias;
    private DataTable tablaConvocatoriasEstado;
    String idPadre = "";

    public ManejadorConsultarConvocatorias() {

        listaConvocatorias = new ArrayList();
        listaHijo = new ArrayList();

    }

    public void consultarConvocatorias() {
        if (nombreConvocatoria != null && nombreConvocatoria.length() > 0) {
            listaConvocatorias = servicioGeneral.obtenerListaObjetos("ConvocatoriaPadre where upper(titulo) like '%"
                    + nombreConvocatoria.toUpperCase() + "%' order by id");
        }
        listaHijo = new ArrayList();
    }

    public void consultarHijos() {
        listaHijo = new ArrayList();

        ConvocatoriaPadre padre = (ConvocatoriaPadre) listaConvocatorias.get(tablaConvocatorias.getRowIndex());

        if (padre.getId() != null) {
            idPadre = String.valueOf(padre.getId());
            listaHijo = servicioModalidad.obtenerConvocatoriasxPadre(padre);
            List listaHijoEstados = new ArrayList();
            for (int i = 0; i < listaHijo.size(); i++) {
                Convocatoria con = (Convocatoria) listaHijo.get(i);
                List estados = new ArrayList();
                try {
                    boolean esConvocatoriaMovilidad = false;
                    if (con.getRestriccion() != null) {
                        List<TipoMovilidad> tiposMovilidad = servicioGeneral.obtenerObjetos(TipoMovilidad.class,
                                "from TipoMovilidad tm where tm.id = '" + con.getRestriccion().getId() + "'");
                        if (!esListaVacia(tiposMovilidad)) {
                            esConvocatoriaMovilidad = true;
                            TipoMovilidad tipoMovilidad = tiposMovilidad.get(0);
                            if (!esCadenaVacia(tipoMovilidad.getTabla())) {
                                if (tipoMovilidad.getTabla().equals(TipoMovilidad.HER_MOVILIDAD_VISITANTES_EXT)) {
                                    estados = servicioModalidad
                                            .resumenEstadoProyectosMovilidad(String.valueOf(con.getId()), "11");
                                } else if (tipoMovilidad.getTabla()
                                        .equals(TipoMovilidad.HER_MOVILIDAD_DOCENTES_EVENTOS)) {
                                    estados = servicioModalidad
                                            .resumenEstadoProyectosMovilidad(String.valueOf(con.getId()), "21");
                                } else if (tipoMovilidad.getTabla()
                                        .equals(TipoMovilidad.HER_MOVILIDAD_ESTUDIANTE_POS)) {
                                    estados = servicioModalidad
                                            .resumenEstadoProyectosMovilidad(String.valueOf(con.getId()), "3");
                                }
                            }
                        }
                    }
                    if (!esConvocatoriaMovilidad) {
                        estados = servicioModalidad.resumenEstadoProyectos(String.valueOf(con.getId()));
                    }

                    String sal[] = new String[4];
                    int total = 0;
                    int valor = 0;
                    for (Object cadena : estados) {
                        String[] cad = (String[]) cadena;
                        try {
                            valor = Integer.parseInt(cad[0]);
                        } catch (NumberFormatException nfe) {
                            valor = 0;
                        }
                        total += valor;
                    }
                    sal[0] = String.valueOf(total);
                    sal[1] = "Todos los estados";
                    sal[2] = String.valueOf(con.getId());
                    sal[3] = "TODO";
                    estados.add(sal);
                    con.setEstados(estados);
                    listaHijoEstados.add(con);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            listaHijo = listaHijoEstados;
        } else {
            idPadre = "";
        }
    }

    public void consultarReporte() {
        String modalidadId = "";
        Long modalidadIdLong = null;
        String estadoId = "";
        String convocatoriaId = "";
        FacesContext context = FacesContext.getCurrentInstance();
        Map map = context.getExternalContext().getRequestParameterMap();
        Object o = (Object) map.get("modalidadId");
        modalidadId = (String) o;
        modalidadIdLong = Long.parseLong(modalidadId);
        o = (Object) map.get("estadoId");
        estadoId = (String) o;
        o = (Object) map.get("convocatoriaId");
        convocatoriaId = (String) o;
        Convocatoria con = servicioModalidad.obtenerConvocatoria(modalidadIdLong);

        boolean esConvocatoriaMovilidad = false;
        TipoMovilidad tipoMovilidad = null;
        if (con.getRestriccion() != null) {
            List<TipoMovilidad> tiposMovilidad = servicioGeneral.obtenerObjetos(TipoMovilidad.class,
                    "from TipoMovilidad tm where tm.id = '" + con.getRestriccion().getId() + "'");
            if (!esListaVacia(tiposMovilidad)) {
                esConvocatoriaMovilidad = true;
                tipoMovilidad = tiposMovilidad.get(0);
            }
        }

        ReporteBirt r = new ReporteBirt();
        r.adicionarParametro("Convocatoria", convocatoriaId);
        r.adicionarParametro("ModId", modalidadId);
        r.adicionarParametro("Estado", estadoId);
        r.setFormato(ReporteBirt.FORMATO_XLS);

        if (estadoId.equals("TODO")) {
            r.setNombreReporte("/convocatoria/proyectosParticipantesTodo");
            if (esConvocatoriaMovilidad) {
                if (!esCadenaVacia(tipoMovilidad.getTabla())) {
                    if (tipoMovilidad.getTabla().equals(TipoMovilidad.HER_MOVILIDAD_VISITANTES_EXT)) {
                        r.setNombreReporte("/convocatoria/proyectosParticipantesEstadoMovilidadVE_Todos");
                    } else if (tipoMovilidad.getTabla().equals(TipoMovilidad.HER_MOVILIDAD_DOCENTES_EVENTOS)) {
                        r.setNombreReporte("/convocatoria/proyectosParticipantesEstadoMovilidadDE_Todos");
                    } else if (tipoMovilidad.getTabla().equals(TipoMovilidad.HER_MOVILIDAD_ESTUDIANTE_POS)) {
                        r.setNombreReporte("/convocatoria/proyectosParticipantesEstadoMovilidadE1_Todos");
                    }
                }
            } else if (con != null && con.getRestriccion() != null
                    && con.getRestriccion().getId().equals("CONV_JI_COL_2014")) {
                r.setNombreReporte("/convocatoria/proyectosParticipantesJI_Todos");
            } else if (con != null && con.getRestriccion() != null && con.getRestriccion().getId().equals("FUNCYTCA")) {
                r.setNombreReporte("/convocatoria/proyectosParticipantesTodosEstadosFunCyTCA");
            }
        } else if (esConvocatoriaMovilidad) {
            if (tipoMovilidad.getTabla().equals(TipoMovilidad.HER_MOVILIDAD_VISITANTES_EXT)) {
                r.setNombreReporte("/convocatoria/proyectosParticipantesEstadoMovilidadVE");
            } else if (tipoMovilidad.getTabla().equals(TipoMovilidad.HER_MOVILIDAD_DOCENTES_EVENTOS)) {
                r.setNombreReporte("/convocatoria/proyectosParticipantesEstadoMovilidadDE");
            } else if (tipoMovilidad.getTabla().equals(TipoMovilidad.HER_MOVILIDAD_ESTUDIANTE_POS)) {
                r.setNombreReporte("/convocatoria/proyectosParticipantesEstadoMovilidadE1");
            }
        } else if (con != null && con.getRestriccion() != null && con.getRestriccion().getId() != null
                && (con.getRestriccion().getId().equals("CON_INNO_ES"))) {
            r.setNombreReporte("/convocatoria/proyectosParticipantesEstadoConvInnoES");

        } else if (con != null && con.getRestriccion() != null && con.getRestriccion().getId() != null
                && (con.getRestriccion().getId().equals("CONV_JI_COL_2014"))) {
            r.setNombreReporte("/convocatoria/proyectosParticipantesJI");
        } else {
            r.setNombreReporte("/convocatoria/proyectosParticipantesEstado");
        }
        try {
            sesion.setAttribute("reporte", r);
            context.getExternalContext().dispatch("/ReporteEngineServlet");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            context.responseComplete();
        }
    }

    public String getNombreConvocatoria() {
        return nombreConvocatoria;
    }

    public void setNombreConvocatoria(String nombreConvocatoria) {
        this.nombreConvocatoria = nombreConvocatoria;
    }

    public List getListaConvocatorias() {
        return listaConvocatorias;
    }

    public void setListaConvocatorias(List listaConvocatorias) {
        this.listaConvocatorias = listaConvocatorias;
    }

    public List getListaHijo() {
        return listaHijo;
    }

    public void setListaHijo(List listaHijo) {
        this.listaHijo = listaHijo;
    }

    public DataTable getTablaConvocatorias() {
        return tablaConvocatorias;
    }

    public void setTablaConvocatorias(DataTable tablaConvocatorias) {
        this.tablaConvocatorias = tablaConvocatorias;
    }

    public boolean isBandera() {
        if (listaHijo != null && listaHijo.size() > 0) {
            return true;
        }
        return false;
    }

    public DataTable getTablaConvocatoriasEstado() {
        return tablaConvocatoriasEstado;
    }

    public void setTablaConvocatoriasEstado(DataTable tablaConvocatoriasEstado) {
        this.tablaConvocatoriasEstado = tablaConvocatoriasEstado;
    }

}
