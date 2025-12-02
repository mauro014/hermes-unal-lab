package co.edu.unal.hermes.vista.movilidad;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.model.DefaultUploadedFile;

import co.edu.unal.hermes.modelo.ActividadMovilidadSeguimientoVE;
import co.edu.unal.hermes.modelo.ActividadMovilidadVE;
import co.edu.unal.hermes.modelo.ArchivoMovilidad;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.MovilidadArchivo;
import co.edu.unal.hermes.modelo.MovilidadSeguimientoActEvento;
import co.edu.unal.hermes.modelo.MovilidadSeguimientoCooproducto;
import co.edu.unal.hermes.modelo.MovilidadSeguimientoEvento;
import co.edu.unal.hermes.modelo.MovilidadSeguimientoEventoAsistente;
import co.edu.unal.hermes.modelo.MovilidadVisitanteExterior;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PlanEstudios;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.utils.Navegacion;

public class ManejadorCrearEditarSeguimientoMovilidadVisitante extends ManejadorBaseMovilidad {

    private static final long serialVersionUID = -7347116937508878406L;
    private MovilidadVisitanteExterior mve;
    private boolean consulta;
    private boolean completado;

    private final String ES_CONSULTA_FACULTAD = "esConsultaFacultad";
    private final String ES_REVISION_FACULTAD = "esRevisionFacultad";
    private final String ES_REVISION_SEDE = "esRevisionSede";
    private final String CONSULTA_MOVILIDAD_VISITANTE = "consultaMovilidadVisitante";

    private SelectItem[] tipoCalificacion1;
    private SelectItem[] tipoCalificacion2;
    private SelectItem[] tipoCalificacion3;
    private String calificacion1Sel;
    private String calificacion2Sel;
    private String calificacion3Sel;
    private String descripcionPro;

    private String apoyoTotal;
    private String costoTiquetes;
    private String valorTotalViativos;
    private Long apoyoTotalAdicional;

    private SelectItem[] tipoEvento;
    private SelectItem[] tipoAsistente;
    private SelectItem[] tipoAsistente1;

    private List<SelectItem> programas;
    private String descripcionNuevaActividad;

    private List<ActividadMovilidadVE> listaActividadJuradoTesis;
    private List<ActividadMovilidadVE> listaActividadModuloCurso;
    private List<ActividadMovilidadVE> listaActividadEventoCientifico;

    private SelectItem[] tipoCooproducto;
    private String cooproductoSel;
    private List<Object[]> listaCooproductos;
    private List<Object[]> listaCooproductosEliminados;
    private List<Object[]> listaEventos;
    private List<Object[]> listaEventosEliminados;
    private SelectItem[] tipoDocumentoSelItem;
    private DefaultUploadedFile archivoObligatorio;
    private List<?> listaAsistentes;
    private List<?> listaAsistentes1;

    private String[] errores;
    private String personaMovilidad = "";
    private String tipoPrograma;
    private String eventoAsist;

    private String tipoEventoSel;
    private SelectItem[] tipoCoproducto;
    private String tipoCoproductoSel;
    private String tipoCalificacionSel;
    private SelectItem[] tipoCalificacion;
    private String tipoAspectosSel;

    private String descripcionMovilidad;
    private List<MovilidadArchivo> listaArchivosObligatorios;
    private List<ArchivoMovilidad> listaArchivosObligatoriosEliminados;
    private String tipoDocumentoSel;
    private List<ArchivoMovilidad> listaArchivosObligatoriosSel;

    private ArchivoMovilidad archivoTabla;
    private ActividadMovilidadVE act;
    private Object[] eventoTable;
    private Object[] coproductoTable;
    private List<ActividadMovilidadSeguimientoVE> actividadesAdicionales;
    private List<ActividadMovilidadSeguimientoVE> actividadesAdicionalesEliminadas;
    private ActividadMovilidadSeguimientoVE actividadSelTabla;
    private String experiencia;
    private List<DominioDetalle> listaTipoAsistentes;
    List<DominioDetalle> listaTipoEvento;
    private boolean esConsultaFacultad;
    private boolean esRevisionFacultad;
    private boolean esRevisionSede;

    private String comentariosInforme;
    private String mensajeRevisionFacultad;
    private boolean revisadoFacultad;
    private boolean revisadoSede;
    private String estadoRevisionSeguimiento;

    private Persona responsableRevision;
    private Date fechaRevision;

    private String realizacionMovilidad;
    private String razonesNoRealizacion;

    public ManejadorCrearEditarSeguimientoMovilidadVisitante() {

        cargarValoresIniciales();
        consulta = habilitarEdicion();
        mve = (MovilidadVisitanteExterior) sesion.getAttribute("movilidadVisExt");

        List<MovilidadVisitanteExterior> movs = servicioGeneral.obtenerObjetos(MovilidadVisitanteExterior.class,
                "from MovilidadVisitanteExterior mov where mov.id = '" + mve.getId() + "'");
        if (movs != null && !movs.isEmpty()) {
            mve = movs.get(0);
        }

        realizacionMovilidad = "SI";

        cargarTipoCooproductos();
        cargarTipoAsistentes();
        cargarTipoEventos();
        cargarProgramasPosgrado();
        cargarTipoCalificacion();
        cargarTiposDocumentos();

        cargarInformacionMovilidadVisitante();
        cargarArchivosMovilidad();
        cargarDatosFacultad();
        revisadoFacultad = false;
    }

    public void imprimirInforme() {
        FacesContext context = FacesContext.getCurrentInstance();
        Long id = mve.getId();
        ReporteBirt r = new ReporteBirt();
        r.adicionarParametro("id", Long.toString(id));
        r.setNombreReporte("/movilidad/InformeSeguimientoVisitante");
        r.setFormato(ReporteBirt.FORMATO_PDF);
        sesion.setAttribute("reporte", r);
        System.out.println(Navegacion.REPORTE);
        try {
            context.getExternalContext().dispatch("/ReporteEngineServlet");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            context.responseComplete();
        }
    }

    private void cargarDatosFacultad() {
        if (sesion.getAttribute(ES_CONSULTA_FACULTAD) != null) {
            esConsultaFacultad = (Boolean) sesion.getAttribute(ES_CONSULTA_FACULTAD);
            sesion.removeAttribute(ES_CONSULTA_FACULTAD);
        }
        if (sesion.getAttribute(ES_REVISION_FACULTAD) != null) {
            esRevisionFacultad = (Boolean) sesion.getAttribute(ES_REVISION_FACULTAD);
            sesion.removeAttribute(ES_REVISION_FACULTAD);
        }
        if (sesion.getAttribute(ES_REVISION_SEDE) != null) {
            esRevisionSede = (Boolean) sesion.getAttribute(ES_REVISION_SEDE);
            sesion.removeAttribute(ES_REVISION_SEDE);
        }
        if(esRevisionFacultad || esRevisionSede) {
        	completado = false;            	
        }
    }

    private boolean habilitarEdicion() {
        try {
            String consultaHabilitarEdicion = (String) sesion.getAttribute(CONSULTA_MOVILIDAD_VISITANTE);
            sesion.removeAttribute(CONSULTA_MOVILIDAD_VISITANTE);
            if ("SI".equals(consultaHabilitarEdicion)) {
                return true;
            }
        } catch (NullPointerException npe) {
            return false;
        }
        return false;
    }

    private void cargarValoresIniciales() {

        personaActual = (Persona) sesion.getAttribute("persona");
        listaCooproductos = new ArrayList<Object[]>();
        listaEventos = new ArrayList<Object[]>();
        listaArchivosObligatoriosSel = new ArrayList<ArchivoMovilidad>();
        actividadesAdicionalesEliminadas = new ArrayList<ActividadMovilidadSeguimientoVE>();
        errores = new String[60];

    }

    private void cargarTipoCooproductos() {
        listaCooproductosEliminados = new ArrayList<Object[]>();
        List<DominioDetalle> listaTipoCooproducto = servicioGeneral.obtenerObjetos(DominioDetalle.class,
                "from DominioDetalle where identificador.id = '3'");
        tipoCooproducto = new SelectItem[listaTipoCooproducto.size()];
        for (int i = 0; i < listaTipoCooproducto.size(); i++) {
            DominioDetalle td = (DominioDetalle) listaTipoCooproducto.get(i);
            tipoCooproducto[i] = new SelectItem(td.getIdentificador().getTipo(), td.getDescripcion());
        }
        cooproductoSel = ((DominioDetalle) listaTipoCooproducto.get(0)).getIdentificador().getTipo();

        if (mve.getId() != null) {
            List<MovilidadSeguimientoCooproducto> listaMovSegCo = servicioGeneral.obtenerObjetos(
                    MovilidadSeguimientoCooproducto.class,
                    "from MovilidadSeguimientoCooproducto " + "where idMovilidad = " + mve.getId());

            for (int i = 0; i < listaMovSegCo.size(); i++) {
                MovilidadSeguimientoCooproducto coproObj;
                coproObj = (MovilidadSeguimientoCooproducto) listaMovSegCo.get(i);
                String[] producto = new String[5];
                producto[0] = coproObj.getTipo();
                Iterator<DominioDetalle> it = listaTipoCooproducto.iterator();
                while (it.hasNext()) {
                    DominioDetalle dominioDetalle = it.next();
                    if (dominioDetalle.getIdentificador().getTipo().equals(producto[0])) {
                        producto[1] = dominioDetalle.getDescripcion();
                    }
                }
                producto[3] = coproObj.getId().toString();
                producto[2] = coproObj.getDetalle();
                listaCooproductos.add(producto);
            }
        }
    }

    private void cargarProgramasPosgrado() {
        // String consulta = "select p from Programa p where p.idDependencia
        // like '" + investigadorInterno.getDependencia().getSede().getId() +
        // "%' order by p.nombre";
        // String consulta = "select p from PlanEstudios p where p.tipo in
        // (4,5,6,7) and p.id in (select prog.id from Programa prog) order by
        // p.id ";
        String consulta = "select p from PlanEstudios p where p.tipo in (4,5,6,7) order by p.id ";
        List listaProgramas = servicioGeneral.obtenerObjetos(consulta);

        programas = new Vector();
        SelectItem stemp = new SelectItem("0", "Seleccione un programa");
        programas.add(stemp);
        for (Iterator it = listaProgramas.iterator(); it.hasNext();) {
            PlanEstudios p = (PlanEstudios) it.next();
            String nombrePlan = "";
            if (p.getNombre() != null && p.getNombre().length() > 0) {
                boolean bandera = false;
                String ini = p.getId().substring(0, 1);
                if (ini.equals("1")) {
                    nombrePlan = "Sede Medellín - ";
                    bandera = true;
                }
                if (ini.equals("2")) {
                    nombrePlan = "Sede Bogotá - ";
                    bandera = true;
                }
                if (ini.equals("3")) {
                    nombrePlan = "Sede Medellín - ";
                    bandera = true;
                }
                if (ini.equals("4")) {
                    nombrePlan = "Sede Manizales - ";
                    bandera = true;
                }
                if (ini.equals("5")) {
                    nombrePlan = "Sede Palmira - ";
                    bandera = true;
                }
                if (ini.equals("6")) {
                    nombrePlan = "Sede Amazonia - ";
                    bandera = true;
                }
                if (ini.equals("7")) {

                }
                if (ini.equals("8")) {
                    nombrePlan = "Sede Caribe - ";
                    bandera = true;
                }
                if (bandera) {
                    nombrePlan = nombrePlan + p.getNombre();
                    SelectItem s = new SelectItem(p.getId(), nombrePlan);
                    programas.add(s);
                }
            }
        }
    }

    public void cargarArchivosMovilidad() {
        listaArchivosObligatoriosEliminados = new ArrayList<ArchivoMovilidad>();
        listaArchivosObligatoriosSel = servicioGeneral.obtenerObjetos(ArchivoMovilidad.class,
                "from ArchivoMovilidad am " + "where am.movilidad = '" + mve.getId() + "'");
    }

    private void cargarTipoEventos() {
        listaEventosEliminados = new ArrayList<Object[]>();
        listaTipoEvento = servicioGeneral.obtenerObjetos(DominioDetalle.class,
                "from DominioDetalle where identificador.id = '2'");
        tipoEvento = new SelectItem[listaTipoEvento.size()];
        for (int i = 0; i < listaTipoEvento.size(); i++) {
            DominioDetalle td = (DominioDetalle) listaTipoEvento.get(i);
            tipoEvento[i] = new SelectItem(td.getIdentificador().getTipo(), td.getDescripcion());
        }
        tipoEventoSel = ((DominioDetalle) listaTipoEvento.get(0)).getIdentificador().getTipo();

        if (mve.getId() != null) {
            List<MovilidadSeguimientoEvento> listaEventosDisco = servicioGeneral.obtenerObjetos(
                    MovilidadSeguimientoEvento.class,
                    "from MovilidadSeguimientoEvento where movilidadId = " + this.mve.getId());

            Iterator<MovilidadSeguimientoEvento> i = listaEventosDisco.iterator();
            // Llenando lista
            while (i.hasNext()) {
                Object evento[] = new Object[8];
                boolean bandera = true;
                MovilidadSeguimientoEvento mse = i.next();
                evento[1] = mse.getAsistente();
                evento[5] = mse.getDetalleTipo();
                evento[6] = mse.getId();
                Iterator<DominioDetalle> ite = listaTipoEvento.iterator();
                while (ite.hasNext()) {
                    DominioDetalle dd = ite.next();
                    if (dd.getIdentificador().getTipo().equals(evento[5])) {
                        evento[2] = dd.getDescripcion();
                    }
                }
                evento[4] = mse.getListaAsistentes();
                Iterator<MovilidadSeguimientoEventoAsistente> ia = mse.getAsistentes().iterator();
                String asistentes = "";
                while (ia.hasNext()) {
                    if (asistentes.length() > 0) {
                        asistentes += ", ";
                    }
                    MovilidadSeguimientoEventoAsistente msea = ia.next();
                    Iterator<DominioDetalle> idd = listaTipoAsistentes.iterator();
                    while (idd.hasNext()) {
                        DominioDetalle dd = idd.next();
                        if (dd.getIdentificador().getTipo().equals(msea.getDetalleTipo())) {
                            asistentes += dd.getDescripcion();
                        }
                    }
                }
                evento[3] = asistentes;
                if (bandera) {
                    listaEventos.add(evento);
                    eventoAsist = "";
                }
            }
        }
    }

    private void cargarTipoAsistentes() {
        listaTipoAsistentes = servicioGeneral.obtenerObjetos(DominioDetalle.class,
                "from DominioDetalle where identificador.id = '1'");
        tipoAsistente = new SelectItem[listaTipoAsistentes.size()];
        tipoAsistente1 = new SelectItem[listaTipoAsistentes.size()];
        for (int i = 0; i < listaTipoAsistentes.size(); i++) {
            DominioDetalle td = (DominioDetalle) listaTipoAsistentes.get(i);
            tipoAsistente[i] = new SelectItem(td.getIdentificador().getTipo(), td.getDescripcion());
            tipoAsistente1[i] = new SelectItem(td.getIdentificador().getTipo(), td.getDescripcion());
        }
    }

    private void cargarTipoCalificacion() {
        List<DominioDetalle> listaTipoCalificacion = servicioGeneral.obtenerObjetos(DominioDetalle.class,
                "from DominioDetalle where identificador.id = '4'");
        tipoCalificacion1 = new SelectItem[listaTipoCalificacion.size()];
        tipoCalificacion2 = new SelectItem[listaTipoCalificacion.size()];
        tipoCalificacion3 = new SelectItem[listaTipoCalificacion.size()];
        for (int i = 0; i < listaTipoCalificacion.size(); i++) {
            DominioDetalle td1 = (DominioDetalle) listaTipoCalificacion.get(i);
            DominioDetalle td2 = (DominioDetalle) listaTipoCalificacion.get(i);
            DominioDetalle td3 = (DominioDetalle) listaTipoCalificacion.get(i);
            tipoCalificacion1[i] = new SelectItem(td1.getIdentificador().getTipo(), td1.getDescripcion());
            tipoCalificacion2[i] = new SelectItem(td2.getIdentificador().getTipo(), td2.getDescripcion());
            tipoCalificacion3[i] = new SelectItem(td3.getIdentificador().getTipo(), td3.getDescripcion());
        }
        calificacion1Sel = ((DominioDetalle) listaTipoCalificacion.get(listaTipoCalificacion.size() - 1))
                .getIdentificador().getTipo();
        calificacion2Sel = ((DominioDetalle) listaTipoCalificacion.get(listaTipoCalificacion.size() - 1))
                .getIdentificador().getTipo();
        calificacion3Sel = ((DominioDetalle) listaTipoCalificacion.get(listaTipoCalificacion.size() - 1))
                .getIdentificador().getTipo();

    }

    public void editarActividad1() {
        limpiarErores(4, 4);
        boolean bandera = true;
        if (act.getNomTesis() == null || act.getNomTesis().length() <= 0 || act.getNomTesis().length() > 300) {
            this.errores[4] = "Nombre Tesis NO valido";
            bandera = false;
        }

        if (act.getCodEstudiante() != null) {
            try {
                Integer.parseInt(act.getCodEstudiante().trim());
            } catch (Exception e) {
                this.errores[4] = this.errores[4] + " Código Estudiante NO valido";
                bandera = false;
            }
        }

        if (bandera) {
            servicioGeneral.guardarObjeto(act);
        }
    }

    public void adicionarCooproducto() {
        boolean bandera = true;
        Object[] producto = new Object[5];
        List<DominioDetalle> listaDominio = servicioGeneral.obtenerObjetos(DominioDetalle.class,
                "from DominioDetalle where identificador.id = '3' " + "and identificador.tipo = '" + cooproductoSel
                        + "'");
        String nombreCo = "";
        if (listaDominio != null && !listaDominio.isEmpty()) {
            nombreCo = ((DominioDetalle) listaDominio.get(0)).getDescripcion();
        }
        producto[0] = this.cooproductoSel;
        producto[1] = nombreCo;
        producto[2] = this.descripcionPro;

        if (this.descripcionPro == null || this.descripcionPro.length() <= 0) {
            this.errores[7] = "Debe registrar una descripción";
            bandera = false;

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "La descripción del coproducto es obligatoria", "La descripción del coproducto es obligatoria");
            mostrarMensaje(message, null);
        }
        if (bandera) {
            listaCooproductos.add(producto);
            descripcionPro = "";

        }
    }

    protected void mostrarMensaje(FacesMessage msg, UIComponent component) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (component == null) {
            context.addMessage(null, msg);
        } else {
            context.addMessage(component.getClientId(context), msg);
        }
    }

    public void adicionarEvento() {
        try {
            Object[] evento = new Object[8];
            boolean bandera = true;
            limpiarErores(1, 3);
            String nombreEvento = "";
            List<DominioDetalle> listaDominio = servicioGeneral.obtenerObjetos(DominioDetalle.class,
                    "from DominioDetalle where identificador.id = '2' " + "and identificador.tipo = '" + tipoEventoSel
                            + "'");
            if (listaDominio != null && !listaDominio.isEmpty()) {
                nombreEvento = ((DominioDetalle) listaDominio.get(0)).getDescripcion();
            }

            // Llenando lista
            evento[0] = tipoPrograma;
            evento[1] = eventoAsist;
            evento[2] = nombreEvento;
            evento[5] = tipoEventoSel;
            String tipoAsis = "";
            try {
                if (listaAsistentes != null) {
                    for (int i = 0; i < listaAsistentes.size(); i++) {
                        String Qsql = "from DominioDetalle where identificador.tipo = '"
                                + listaAsistentes.get(i).toString() + "'";
                        List<DominioDetalle> lst = servicioGeneral.obtenerObjetos(DominioDetalle.class, Qsql);
                        DominioDetalle dd = (DominioDetalle) lst.get(0);
                        tipoAsis += dd.getDescripcion() + ", ";
                    }
                }
                evento[3] = tipoAsis;
                evento[4] = listaAsistentes;

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (tipoAsis != null && tipoAsis.length() == 0) {
                this.errores[1] = "Debe seleccionar un tipo de asistentes";
                bandera = false;
            }

            if (eventoAsist != null) {
                try {
                    Integer.parseInt(eventoAsist.trim());
                } catch (Exception e) {
                    this.errores[2] = "Valor NO valido";
                    bandera = false;
                }
            }

            if (bandera) {
                listaEventos.add(evento);
                eventoAsist = "";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarEvento() {
        listaEventos.remove(eventoTable);
        eventoTable[7] = "true";
        if (eventoTable[6] != null) {
            listaEventosEliminados.add(eventoTable);
        }
    }

    public void editarActividad2() {
        limpiarErores(5, 5);
        boolean bandera = true;
        if (act.getNomCursoEvento() == null || act.getNomCursoEvento().length() <= 0
                || act.getNomCursoEvento().length() > 300) {
            this.errores[5] = "Nombre Curso NO valido";
            bandera = false;
        }
        if (act.getAsistentes() != null) {
            try {
                Integer.parseInt(act.getAsistentes().trim());
            } catch (Exception e) {
                this.errores[5] = this.errores[5] + " Asistentes NO valido";
                bandera = false;
                e.printStackTrace();
            }
        }
        if (act.getTipoAsistentes() == null || act.getTipoAsistentes().size() <= 0) {
            this.errores[5] = this.errores[5] + " Tipo Asistentes NO valido";
            bandera = false;
        }
        if (bandera) {
            servicioGeneral.guardarObjeto(act);
            try {
                servicioGeneral
                        .eliminar("DELETE HER_MOVSEG_ACTV_ASISTENTE WHERE ACT_MOV_ID = " + String.valueOf(act.getId()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < act.getTipoAsistentes().size(); i++) {
                MovilidadSeguimientoActEvento asis = new MovilidadSeguimientoActEvento();
                asis.setIdActividad(act.getId());
                asis.setDetalle(String.valueOf(act.getTipoAsistentes().get(i).toString()));
                servicioGeneral.guardarObjeto(asis);
            }
        }
    }

    public String atras() {
        sesion.removeAttribute("manejadorCrearEditarSeguimientoMovilidadVisitante");
        return "successProyectosMovilidad";
    }

    public String atrasRevision() {
        sesion.removeAttribute("manejadorCrearEditarSeguimientoMovilidadVisitante");
        sesion.removeAttribute("manejadorAprobacionMovilidadSeguimiento");
        return "listadoRevisionSeguimiento";
    }

    public String atrasAprobacion() {
        sesion.removeAttribute("manejadorCrearEditarSeguimientoMovilidadVisitante");
        sesion.removeAttribute("manejadorAprobacionMovilidadSeguimientoSede");
        return "listadoRevisionSeguimientoSede";
    }

    public void editarActividad3() {
        limpiarErores(6, 6);
        boolean bandera = true;
        if (act.getNomCursoEvento() == null || act.getNomCursoEvento().length() <= 0
                || act.getNomCursoEvento().length() > 300) {
            this.errores[6] = "Nombre Evento NO valido";
            bandera = false;
        }
        if (act.getNomArea() == null || act.getNomArea().length() <= 0 || act.getNomArea().length() > 300) {
            this.errores[6] = this.errores[6] + " Área NO valida";
            bandera = false;
        }
        if (act.getAsistentes() != null) {
            try {
                Integer.parseInt(act.getAsistentes().trim());
            } catch (Exception e) {
                this.errores[6] = this.errores[6] + " Asistentes NO valido";
                bandera = false;
            }
        }
        if (bandera) {
            servicioGeneral.guardarObjeto(act);
        }
    }

    public void eliminarCoproducto() {
        listaCooproductos.remove(coproductoTable);
        if (coproductoTable[3] != null) {
            coproductoTable[4] = "true";
            listaCooproductosEliminados.add(coproductoTable);
        }
    }

    public void limpiarErores(int min, int max) {
        for (int i = min; i <= max; i++) {
            this.errores[i] = "";
        }
    }

    public boolean validacion(boolean parcial) {
        if (!parcial) {
            boolean bandera = true;

            limpiarErores(1, 9);
            if (listaEventos == null || listaEventos.size() <= 0) {
                this.errores[3] = "No se encuentran eventos registrados";
                bandera = false;

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No se encuentran eventos registrados", "No se encuentran eventos registrados");
                mostrarMensaje(message, null);
            }
            boolean bandera1 = false;
            boolean bandera2 = false;
            boolean bandera3 = false;

            for (int i = 0; i < listaActividadJuradoTesis.size(); i++) {
                ActividadMovilidadVE actividad = (ActividadMovilidadVE) listaActividadJuradoTesis.get(i);
                if ((actividad.getNomTesis() == null) || (actividad.getNomTesis().length() == 0)) {
                    bandera1 = true;
                }
            }

            for (int i = 0; i < listaActividadModuloCurso.size(); i++) {
                ActividadMovilidadVE actividad = (ActividadMovilidadVE) listaActividadModuloCurso.get(i);
                if ((actividad.getNomCursoEvento() == null) || (actividad.getNomCursoEvento().length() == 0)) {
                    bandera2 = true;
                }
            }

            for (int i = 0; i < listaActividadEventoCientifico.size(); i++) {
                ActividadMovilidadVE actividad = (ActividadMovilidadVE) listaActividadEventoCientifico.get(i);
                if ((actividad.getNomCursoEvento() == null) || (actividad.getNomCursoEvento().length() == 0)) {
                    bandera3 = true;
                }
            }

            if (bandera1) {
                this.errores[4] = "No se encuentran actualizadas las actividades";
                bandera = false;
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, this.errores[4], this.errores[4]);
                mostrarMensaje(message, null);
            }
            if (bandera2) {
                this.errores[5] = "No se encuentran actualizadas las actividades";
                bandera = false;
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, this.errores[5], this.errores[5]);
                mostrarMensaje(message, null);
            }
            if (bandera3) {
                this.errores[6] = "No se encuentran actualizadas las actividades";
                bandera = false;
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, this.errores[6], this.errores[6]);
                mostrarMensaje(message, null);
            }
            if (listaCooproductos == null || listaCooproductos.size() <= 0) {
                this.errores[7] = "No se encuentran coproductos registrados";
                bandera = false;
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se han adicionado coproductos",
                        "No se han adicionado coproductos");
                mostrarMensaje(message, null);
            }
            if (listaArchivosObligatoriosSel == null || listaArchivosObligatoriosSel.size() <= 0) {
                this.errores[9] = "No se encuentran archivos registrados";
                bandera = false;

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No se encuentran archivos registrados", "No se encuentran archivos registrados");
                mostrarMensaje(message, null);
            }

            
            if (experiencia == null || (experiencia != null && experiencia.length() == 0)) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Debe ingresar la experiencia de la visita del profesor",
                        "Debe ingresar la experiencia de la visita del profesor");
                mostrarMensaje(message, null);
                bandera = false;
            }
            return bandera;
        } else {
            return true;
        }
    }
    
    public boolean validarAprobacionValores() {

        boolean bandera = true;
        
        if(esRevisionFacultad || esRevisionSede) {
            if (apoyoTotal == null || (apoyoTotal != null && apoyoTotal.length() == 0)) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Debe ingresar del valor total del apoyo", "Debe ingresar del valor total del apoyo");
                mostrarMensaje(message, null);
                bandera = false;
            } else {
                try {
                    int valor = Integer.parseInt(apoyoTotal.trim());
                    if (valor == 0) {
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Debe ingresar del valor total del apoyo válido",
                                "Debe ingresar del valor total del apoyo válido");
                        mostrarMensaje(message, null);
                        bandera = false;
                    }
                } catch (NumberFormatException e) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Debe ingresar del valor total del apoyo válido",
                            "Debe ingresar del valor total del apoyo válido");
                    mostrarMensaje(message, null);
                    bandera = false;
                }

            }
            if (costoTiquetes == null || (costoTiquetes != null && costoTiquetes.length() == 0)) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Debe ingresar del valor de los tiquetes", "Debe ingresar del valor de los tiquetes");
                mostrarMensaje(message, null);
                bandera = false;
            } else {
                try {
                    Integer.parseInt(costoTiquetes.trim());
                } catch (NumberFormatException e) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Debe ingresar del valor de tiquetes válido", "Debe ingresar del valor de tiquetes válido");
                    mostrarMensaje(message, null);
                    bandera = false;
                }
            }
            if (valorTotalViativos == null || (valorTotalViativos != null && valorTotalViativos.length() == 0)) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Debe ingresar del valor total de viaticos", "Debe ingresar del valor total de viaticos");
                mostrarMensaje(message, null);
                bandera = false;
            } else {
                try {
                    Integer.parseInt(valorTotalViativos.trim());
                } catch (NumberFormatException e) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Debe ingresar el valor total de viaticos válido",
                            "Debe ingresar el valor total de viaticos válido");
                    mostrarMensaje(message, null);
                    bandera = false;
                }
            }
        }
        
        return bandera;
        
    }

    public void cargarInformacionMovilidadVisitante() {
        if (mve != null) {
            actividadesAdicionales = new ArrayList<ActividadMovilidadSeguimientoVE>();
            personaMovilidad = mve.getPersonaInv().getNombreCompletoMinusculas();
            if(mve.getIdprograma() != null){
                tipoPrograma = mve.getIdprograma().getNombre();
            }
            if (mve.getExperienciasVisitante() != null) {
                experiencia = mve.getExperienciasVisitante();
            }
            listaActividadJuradoTesis = servicioGeneral.obtenerObjetos(ActividadMovilidadVE.class,
                    "from ActividadMovilidadVE act where act.movilidad.id = " + mve.getId()
                            + " and ((act.tipoActividad.id = 1) " + "or (act.tipoActividad.id = 2) "
                            + "or (act.tipoActividad.id = 3)) ");
            listaActividadModuloCurso = servicioGeneral.obtenerObjetos(ActividadMovilidadVE.class,
                    "from ActividadMovilidadVE act where act.movilidad.id = " + mve.getId()
                            + " and ((act.tipoActividad.id = 4)" + " or (act.tipoActividad.id = 5))");
            listaActividadEventoCientifico = servicioGeneral.obtenerObjetos(ActividadMovilidadVE.class,
                    "from ActividadMovilidadVE act where act.movilidad.id = " + mve.getId()
                            + " and (act.tipoActividad.id = 6)");

            if (listaActividadModuloCurso != null) {
                List<ActividadMovilidadVE> lista = new ArrayList<ActividadMovilidadVE>();
                for (int i = 0; i < listaActividadModuloCurso.size(); i++) {
                    ActividadMovilidadVE ac = (ActividadMovilidadVE) listaActividadModuloCurso.get(i);
                    List<MovilidadSeguimientoActEvento> asis = servicioGeneral.obtenerObjetos(
                            MovilidadSeguimientoActEvento.class,
                            "from MovilidadSeguimientoActEvento where idActividad = " + ac.getId());
                    if (asis != null) {
                        List<String> acheck = new ArrayList<String>();
                        for (int j = 0; j < asis.size(); j++) {
                            MovilidadSeguimientoActEvento a = (MovilidadSeguimientoActEvento) asis.get(j);
                            acheck.add(a.getDetalle());
                        }
                        ac.setTipoAsistentes(acheck);
                    }
                    lista.add(ac);
                }
                listaActividadModuloCurso = lista;
            }
            responsableRevision = mve.getPersonaSeguimiento();
            fechaRevision = mve.getFechaRevisionSeguimiento();
            cargarActividadesAdicionalesRealizadas();
            if (mve.getCalificacionA() != null) {
                calificacion1Sel = mve.getCalificacionA();
            }
            if (mve.getCalificacionB() != null) {
                calificacion2Sel = mve.getCalificacionB();
            }
            if (mve.getCalificacionC() != null) {
                calificacion3Sel = mve.getCalificacionC();
            }
            if (mve.getRealizacionMovilidad() != null) {
                realizacionMovilidad = mve.getRealizacionMovilidad();
            }
            razonesNoRealizacion = mve.getRazonesNoRealizacion();
            if (mve.getDescripcionRevision() != null) {
                comentariosInforme = mve.getDescripcionRevision();
            }
            descripcionMovilidad = mve.getSegMovDescripcion();
            if (mve.getValorTotalApoyo() != null) {
                apoyoTotal = mve.getValorTotalApoyo().toString();
            }
            if (mve.getEstadoRevisionSeguimiento() != null) {
                estadoRevisionSeguimiento = mve.getEstadoRevisionSeguimiento();
            }
            if (mve.getValorTotalTiquetes() != null) {
                costoTiquetes = mve.getValorTotalTiquetes().toString();
            }
            if (mve.getValorTotalViaticos() != null) {
                valorTotalViativos = mve.getValorTotalViaticos().toString();
            }
            if(mve.getMontoAdicionalEjecutado() != null){
            	apoyoTotalAdicional = mve.getMontoAdicionalEjecutado();
            }
        }
    }

    public boolean validacionNoRealizado(boolean parcial) {
        if (!parcial) {
            boolean bandera = true;
            if (razonesNoRealizacion == null || (razonesNoRealizacion != null && razonesNoRealizacion.length() == 0)) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Debe ingresar las razones de no realización", "Debe ingresar las razones de no realización");
                mostrarMensaje(message, null);
                bandera = false;
            }
            return bandera;
        } else {
            return true;
        }
    }

    private void cargarActividadesAdicionalesRealizadas() {
        List<ActividadMovilidadSeguimientoVE> actividades = servicioGeneral.obtenerObjetos(
                ActividadMovilidadSeguimientoVE.class,
                "from ActividadMovilidadSeguimientoVE a " + "where a.movilidad.id = '" + mve.getId() + "'");
        if (actividades != null && actividades.size() > 0) {
            actividadesAdicionales.addAll(actividades);
        }
    }

    public void eliminarArchivoObligatorio() {
        listaArchivosObligatoriosSel.remove(archivoTabla);
        if (archivoTabla.getId() != null) {
            listaArchivosObligatoriosEliminados.add(archivoTabla);
        }
        archivoTabla = new ArchivoMovilidad();
    }

    public void descargarArchivoObligatorio() {
        descargarArchivoMovilidadGenerico(archivoTabla.getId());
    }

    public void guardarArchivoObligatorio() {
        ArchivoMovilidad archivoMovilidad = insertarArchivoMovilidadGenerico(mve.getId(), archivoObligatorio,
                tipoDocumentoSel, "SMVE");
        if (archivoMovilidad != null) {
            listaArchivosObligatoriosSel.add(archivoMovilidad);
        }
    }

    private void cargarTiposDocumentos() {
        listaArchivosObligatorios = servicioGeneral.obtenerListaArchivosMovilidad("SMVE");
        if (listaArchivosObligatorios != null && listaArchivosObligatorios.size() > 0) {
            tipoDocumentoSelItem = new SelectItem[listaArchivosObligatorios.size()];
            for (int i = 0; i < listaArchivosObligatorios.size(); i++) {
                MovilidadArchivo mea = (MovilidadArchivo) listaArchivosObligatorios.get(i);
                tipoDocumentoSelItem[i] = new SelectItem(mea.getTipoArchivo().getId().toString(),
                        mea.getTipoArchivo().getId().toString() + "-" + mea.getTipoArchivo().getNombre());
            }
        }
    }

    public void guardarParcialmente() {
        guardar("P", true);
    }

    public void guardarEnviar() {
        boolean guardo = guardar("F", false);
        if (guardo) {
            enviarNotificacionEnvioInforme();
        }
    }

    public void enviarNotificacionEnvioInforme() {
        InvestigadorInterno ii = (InvestigadorInterno) mve.getPersonaInv();
        List<Persona> responsables = obtenerCorreoResponsable(ii.getDependencia());
        if (responsables != null && responsables.size() > 0) {
            Iterator<Persona> i = responsables.iterator();
            while (i.hasNext()) {
                Persona persona = i.next();
                if (ii.getDependencia().getSede().isEsSedePresenciaNacional()) {
                    enviarCorreoSeguimientoMovilidad(PLANT_ENVIO_SEG_SEDE_PRESENCIA_NACIONAL, persona.getEmail());
                } else {
                    enviarCorreoSeguimientoMovilidad(PLANT_ENVIO_SEG_FACULTAD, persona.getEmail());
                }
            }
        }
        enviarCorreoSeguimientoMovilidad(PLANT_ENVIO_SEG_DOCENTE, mve.getPersonaInv().getEmail());
    }

    public boolean guardar(String estadoSeguimiento, boolean parcial) {
        if (validacion(parcial) || parcial) {
            for (int i = 0; i < listaEventos.size(); i++) {
                Object evento1[] = (Object[]) listaEventos.get(i);
                if (evento1[6] == null) {
                    MovilidadSeguimientoEvento evento = new MovilidadSeguimientoEvento();

                    evento.setDetalleTipo(String.valueOf(evento1[5]));
                    evento.setAsistente(String.valueOf(evento1[1]));
                    evento.setMovilidadId(String.valueOf(this.mve.getId()));
                    servicioGeneral.guardarObjeto(evento);

                    List<?> tipoAsis = (List<?>) evento1[4];
                    for (int j = 0; j < tipoAsis.size(); j++) {
                        MovilidadSeguimientoEventoAsistente as = new MovilidadSeguimientoEventoAsistente();
                        as.setIdEvento(evento.getId());
                        as.setDetalleTipo(String.valueOf(tipoAsis.get(j)));
                        servicioGeneral.guardarObjeto(as);
                    }
                }
            }
            for (int i = 0; i < listaEventosEliminados.size(); i++) {
                Object evento1[] = (Object[]) listaEventosEliminados.get(i);
                if (evento1[6] != null && evento1[7] != null && evento1[7].equals("true")) {
                    Long id = (Long) evento1[6];
                    MovilidadSeguimientoEvento movilidadSeguimientoEvento = new MovilidadSeguimientoEvento();
                    movilidadSeguimientoEvento.setId(id);
                    servicioGeneral.eliminarObjeto(movilidadSeguimientoEvento);
                }
            }
            listaEventosEliminados = new ArrayList<Object[]>();
            for (int c = 0; c < listaCooproductos.size(); c++) {
                Object producto1[] = (Object[]) this.listaCooproductos.get(c);
                if (producto1[3] == null) {
                    MovilidadSeguimientoCooproducto producto = new MovilidadSeguimientoCooproducto();
                    producto.setIdMovilidad(this.mve.getId());
                    producto.setTipo(String.valueOf(producto1[0]));
                    producto.setDetalle(String.valueOf(producto1[2]));
                    servicioGeneral.guardarObjeto(producto);
                }
            }
            if (listaCooproductosEliminados != null && listaCooproductosEliminados.size() > 0) {
                for (int c = 0; c < listaCooproductosEliminados.size(); c++) {
                    Object coproducto1[] = (Object[]) listaCooproductosEliminados.get(c);
                    if (coproducto1[3] != null && coproducto1[4] != null && coproducto1[4].equals("true")) {
                        Long id = Long.parseLong((String) coproducto1[3]);
                        MovilidadSeguimientoCooproducto producto = new MovilidadSeguimientoCooproducto();
                        producto.setId(id);
                        servicioGeneral.eliminarObjeto(producto);
                    }
                }
            }
            listaCooproductosEliminados = new ArrayList<Object[]>();
            mve.setCalificacionA(this.calificacion1Sel);
            mve.setCalificacionB(this.calificacion2Sel);
            mve.setCalificacionC(this.calificacion3Sel);
            mve.setRealizacionMovilidad(realizacionMovilidad);
            mve.setEstadoRevisionSeguimiento("");
            Long apoyoTotal;
            try {
                apoyoTotal = Long.parseLong(this.apoyoTotal);
            } catch (NullPointerException e) {
                apoyoTotal = 0L;
            } catch (NumberFormatException e) {
                apoyoTotal = 0L;
            }
            mve.setValorTotalApoyo(apoyoTotal);
            if(esRevisionFacultad || esRevisionSede) {
            	mve.setMontoAdicionalEjecutado(apoyoTotalAdicional);
            }
            Long costoTiquetes;
            try {
                costoTiquetes = Long.parseLong(this.costoTiquetes);
            } catch (NullPointerException e) {
                costoTiquetes = 0L;
            } catch (NumberFormatException e) {
                costoTiquetes = 0L;
            }
            mve.setValorTotalTiquetes(costoTiquetes);
            Long valorTotalViativos;
            try {
                valorTotalViativos = Long.parseLong(this.valorTotalViativos);
            } catch (NullPointerException e) {
                valorTotalViativos = 0L;
            } catch (NumberFormatException e) {
                valorTotalViativos = 0L;
            }
            mve.setValorTotalViaticos(valorTotalViativos);
            mve.setExperienciasVisitante(experiencia);
            Calendar actual = Calendar.getInstance();
            Date date = actual.getTime();
            mve.setSegMovFecha(date);

            mve.setSegMovDescripcion(this.descripcionMovilidad);
            mve.setEstadoSeguimiento(estadoSeguimiento);
            servicioGeneral.guardarObjeto(mve);
            for (int i = 0; i < listaArchivosObligatoriosSel.size(); i++) {
                ArchivoMovilidad archivo = new ArchivoMovilidad();
                archivo = (ArchivoMovilidad) listaArchivosObligatoriosSel.get(i);
                archivo.setMovilidad(String.valueOf(mve.getId()));
                servicioGeneral.guardarObjeto(archivo);
            }
            if (listaArchivosObligatoriosEliminados != null && listaArchivosObligatoriosEliminados.size() > 0) {
                for (int i = 0; i < listaArchivosObligatoriosEliminados.size(); i++) {
                    ArchivoMovilidad archivoMovilidad = listaArchivosObligatoriosEliminados.get(i);
                    servicioGeneral.eliminarObjeto(archivoMovilidad);
                }
            }
            listaArchivosObligatoriosEliminados = new ArrayList<ArchivoMovilidad>();
            for (int i = 0; i < actividadesAdicionales.size(); i++) {
                ActividadMovilidadSeguimientoVE actividad = actividadesAdicionales.get(i);
                servicioGeneral.guardarObjeto(actividad);
            }
            if (actividadesAdicionalesEliminadas != null && actividadesAdicionalesEliminadas.size() > 0) {
                for (int i = 0; i < actividadesAdicionalesEliminadas.size(); i++) {
                    ActividadMovilidadSeguimientoVE actividad = actividadesAdicionalesEliminadas.get(i);
                    servicioGeneral.eliminarObjeto(actividad);
                }
            }
            actividadesAdicionalesEliminadas = new ArrayList<ActividadMovilidadSeguimientoVE>();
            String mensaje;
            if (!parcial) {
                mensaje = "Seguimiento de movilidad guardado satisfactoriamente.";
                consulta = true;
                setCompletado(true);
            } else {
                mensaje = "Seguimiento de movilidad guardado parcialmente.";
            }
            FacesContext.getCurrentInstance().addMessage("msgs",
                    new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, ""));
            this.errores[0] = mensaje;
            sesion.removeAttribute("manejadorConsultaMovilidadesInvestigador");
            sesion.removeAttribute("manejadorAprobacionMovilidadSeguimiento");
        } else {
            return false;
        }
        return true;
    }

    public void guardarNoRealizacionParcial() {
        guardarNoRealizacion("P", true);
    }

    public void guardarNoRealizacionEnviar() {
        boolean guardado = guardarNoRealizacion("F", false);
        if (guardado) {
            enviarNotificacionEnvioInforme();
        }
    }

    public boolean guardarNoRealizacion(String estadoSeguimiento, boolean parcial) {
        if (validacionNoRealizado(parcial) || parcial) {
            mve.setRealizacionMovilidad(realizacionMovilidad);
            mve.setEstadoSeguimiento(estadoSeguimiento);
            mve.setSegMovFecha(new Date());
            mve.setRazonesNoRealizacion(razonesNoRealizacion);
            servicioGeneral.guardarObjeto(mve);
            for (int i = 0; i < listaArchivosObligatoriosSel.size(); i++) {
                ArchivoMovilidad archivo = new ArchivoMovilidad();
                archivo = (ArchivoMovilidad) listaArchivosObligatoriosSel.get(i);
                archivo.setMovilidad(String.valueOf(mve.getId()));
                servicioGeneral.guardarObjeto(archivo);
            }
            if (listaArchivosObligatoriosEliminados != null && listaArchivosObligatoriosEliminados.size() > 0) {
                for (int i = 0; i < listaArchivosObligatoriosEliminados.size(); i++) {
                    ArchivoMovilidad archivoMovilidad = listaArchivosObligatoriosEliminados.get(i);
                    servicioGeneral.eliminarObjeto(archivoMovilidad);
                }
            }
            listaArchivosObligatoriosEliminados = new ArrayList<ArchivoMovilidad>();
            String mensaje;
            if (!parcial) {
                mensaje = "Seguimiento de movilidad guardado satisfactoriamente.";
                consulta = true;
            } else {
                mensaje = "Seguimiento de movilidad guardado parcialmente.";
            }
            FacesContext.getCurrentInstance().addMessage("msgs",
                    new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, ""));
            this.errores[0] = mensaje;
            sesion.removeAttribute("manejadorConsultaMovilidadesInvestigador");
            sesion.removeAttribute("manejadorAprobacionMovilidadSeguimiento");
        } else {
            return false;
        }
        return true;
    }

    public void agregarActividadaAdicional() {
        if (actividadesAdicionales == null) {
            actividadesAdicionales = new ArrayList<ActividadMovilidadSeguimientoVE>();
        }
        descripcionNuevaActividad = descripcionNuevaActividad.trim();
        if (descripcionNuevaActividad.length() > 0) {
            ActividadMovilidadSeguimientoVE actividadMovilidadSeguimientoVE = new ActividadMovilidadSeguimientoVE();
            actividadMovilidadSeguimientoVE.setDescripcion(descripcionNuevaActividad);
            actividadMovilidadSeguimientoVE.setMovilidad(mve);
            actividadesAdicionales.add(actividadMovilidadSeguimientoVE);
            descripcionNuevaActividad = "";
        }
    }
    
    public MovilidadVisitanteExterior asignarMontos(MovilidadVisitanteExterior movilidadVisitanteExterior) {
        Long apoyoTotal;
        try {
            apoyoTotal = Long.parseLong(this.apoyoTotal.trim());
        } catch (NullPointerException e) {
            apoyoTotal = 0L;
        } catch (NumberFormatException e) {
            apoyoTotal = 0L;
        }
        movilidadVisitanteExterior.setValorTotalApoyo(apoyoTotal);
        Long costoTiquetes;
        try {
            costoTiquetes = Long.parseLong(this.costoTiquetes.trim());
        } catch (NullPointerException e) {
            costoTiquetes = 0L;
        } catch (NumberFormatException e) {
            costoTiquetes = 0L;
        }
        movilidadVisitanteExterior.setValorTotalTiquetes(costoTiquetes);
        Long valorTotalViativos;
        try {
            valorTotalViativos = Long.parseLong(this.valorTotalViativos.trim());
        } catch (NullPointerException e) {
            valorTotalViativos = 0L;
        } catch (NumberFormatException e) {
            valorTotalViativos = 0L;
        }
        movilidadVisitanteExterior.setValorTotalViaticos(valorTotalViativos);
        if(esRevisionFacultad || esRevisionSede) {
        	movilidadVisitanteExterior.setMontoAdicionalEjecutado(this.apoyoTotalAdicional);
        }
        return movilidadVisitanteExterior;
    }

    private void actualizarRevisionSeguimiento(String estado, String sqlAdcional, String nombreObservaciones) {
        
        mve = asignarMontos(mve);        
        servicioGeneral.guardarObjeto(mve);

        revisadoFacultad = true;
        revisadoSede = true;
        estadoRevisionSeguimiento = estado;
        String sql = "update HER_MOVILIDAD_VISITANTES_EXT " + "set " + nombreObservaciones + " = '" + comentariosInforme
                + "'," + "MOV_PER_ID_REV_SEG = '" + personaActual.getId().getDocumento() + "', "
                + "MOV_TDO_ID_REV_SEG = '" + personaActual.getId().getTipoDocumento() + "',"
                + "MOV_FECHA_REV_SEG = SYSDATE, " + "MOV_ESTADO_REVISION_SEG = '" + estado + "' " + sqlAdcional
                + " where MOV_ID = '" + mve.getId() + "'";
        servicioGeneral.ejecutarSentencia(sql);
        responsableRevision = personaActual;
        fechaRevision = new Date();
        sesion.removeAttribute("manejadorAprobacionMovilidadSeguimiento");
    }

    public void guardarLectura() {
        actualizarRevisionSeguimiento(MovilidadVisitanteExterior.LECTURA_FACULTAD, "", "MOV_OBSERVACION_REV_SEG");
        setCompletado(true);
        enviarCorreoSeguimientoMovilidad(PLANT_ENVIO_REVI_FACULTAD, mve.getPersonaInv().getEmail());
        mensajeRevisionFacultad = "Se ha dado lectura al informe.";
    }

    public void guardarAprobacion() {
    	if(validarAprobacionValores()) {
	        actualizarRevisionSeguimiento(MovilidadVisitanteExterior.APROBACION_SEDE, "", "MOV_OBSERVACIONES_APR_SEG");
	        enviarCorreoSeguimientoMovilidad(PLANT_ENVIO_APROBACION_SEDE, mve.getPersonaInv().getEmail());
	        setCompletado(true);
	        mensajeRevisionFacultad = "Se ha dado aprobación al informe.";
    	}
    }

    public void guardarDevolucion() {
        actualizarRevisionSeguimiento(MovilidadVisitanteExterior.DEVUELTO, ", MOV_ESTADO_SEG  = 'P'",
                "MOV_OBSERVACION_REV_SEG");
        enviarCorreoSeguimientoMovilidad(PLANT_ENVIO_DEVO_FACULTAD, mve.getPersonaInv().getEmail());
        mensajeRevisionFacultad = "Se ha devuelto el informe para correcciones.";
    }

    public void guardarDevolucionSede() {
        actualizarRevisionSeguimiento(MovilidadVisitanteExterior.DEVUELTO, ", MOV_ESTADO_SEG  = 'P'",
                "MOV_OBSERVACIONES_APR_SEG");
        enviarCorreoSeguimientoMovilidad(PLANT_ENVIO_DEVO_FACULTAD, mve.getPersonaInv().getEmail());
        mensajeRevisionFacultad = "Se ha devuelto el informe para correcciones.";
    }

    public void enviarCorreoSeguimientoMovilidad(int idPlantilla, String email) {
        CorreoPlantilla correoActual = cargarPlantilla(idPlantilla);
        correoActual = editarCorreoSeguimientoMovilidad(mve, correoActual);
        Correo correo = new Correo();
        if (idPlantilla == PLANT_ENVIO_REVI_FACULTAD || idPlantilla == PLANT_ENVIO_DEVO_FACULTAD) {
            correo.adicionarDireccion(personaActual.getEmail());
        }
        correo.setOrigen(Correo.CORREO_HERMES);
        correo.adicionarDireccion(email);
        //correo.adicionarDireccion(Correo.CORREO_HERMES);
        correo.setAsunto(correoActual.getAsunto());
        correo.setCuerpo(correoActual.getCuerpo());
        servicioCorreo.enviarCorreo(correo);
    }

    public void eliminarActividadAdicional() {
        actividadesAdicionales.remove(actividadSelTabla);
        if (actividadSelTabla.getId() != null) {
            actividadesAdicionalesEliminadas.add(actividadSelTabla);
        }
    }

    public CorreoPlantilla editarCorreoSeguimientoMovilidad(MovilidadVisitanteExterior mov,
            CorreoPlantilla correoPlantilla) {
        try {
            String correo = correoPlantilla.getCuerpo();
            String investigador = mov.getPersonaInv().getNombreCompleto();
            correo = correo.replaceAll("<<INVESTIGADOR>>", investigador);
            correo = correo.replaceAll("<<IDMOVILIDAD>>", mov.getId().toString());
            correo = correo.replaceAll("<<TIPO>>", mov.getTipoMovilidad().getNombre());
            if (correoPlantilla.getId() == ((long) PLANT_ENVIO_REVI_FACULTAD)
                    || correoPlantilla.getId() == ((long) PLANT_ENVIO_DEVO_FACULTAD)) {
                String[] parts = correo.split("<<COMENTARIOS>>");
                String part1 = parts[0];
                String part2 = parts[1];
                String comentarios = "No se ingresaron comentarios.";
                if (comentariosInforme != null && !comentariosInforme.trim().equals("")) {
                    comentarios = comentariosInforme;
                }
                correo = part1 + comentarios + part2;
            }
            correoPlantilla.setCuerpo(correo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return correoPlantilla;
    }

    public CorreoPlantilla cargarPlantilla(int cod_id) {
        CorreoPlantilla correoPlantilla = new CorreoPlantilla();
        List<CorreoPlantilla> lista = servicioGeneral.obtenerObjetos(CorreoPlantilla.class,
                "from CorreoPlantilla c where c.id='" + cod_id + "'");
        if (lista != null && lista.size() > 0) {
            correoPlantilla = (CorreoPlantilla) lista.get(0);
        }
        return correoPlantilla;
    }

    public String[] getErrores() {
        return errores;
    }

    public MovilidadVisitanteExterior getMve() {
        return mve;
    }

    public List<SelectItem> getProgramas() {
        return programas;
    }

    public List<ActividadMovilidadVE> getListaActividadJuradoTesis() {
        return listaActividadJuradoTesis;
    }

    public List<ActividadMovilidadVE> getListaActividadModuloCurso() {
        return listaActividadModuloCurso;
    }

    public List<ActividadMovilidadVE> getListaActividadEventoCientifico() {
        return listaActividadEventoCientifico;
    }

    public String getPersonaMovilidad() {
        return personaMovilidad;
    }

    public String getTipoEventoSel() {
        return tipoEventoSel;
    }

    public void setTipoEventoSel(String tipoEventoSel) {
        this.tipoEventoSel = tipoEventoSel;
    }

    public SelectItem[] getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(SelectItem[] tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public SelectItem[] getTipoCoproducto() {
        return tipoCoproducto;
    }

    public void setTipoCoproducto(SelectItem[] tipoCoproducto) {
        this.tipoCoproducto = tipoCoproducto;
    }

    public String getTipoCoproductoSel() {
        return tipoCoproductoSel;
    }

    public void setTipoCoproductoSel(String tipoCoproductoSel) {
        this.tipoCoproductoSel = tipoCoproductoSel;
    }

    public String getTipoCalificacionSel() {
        return tipoCalificacionSel;
    }

    public void setTipoCalificacionSel(String tipoCalificacionSel) {
        this.tipoCalificacionSel = tipoCalificacionSel;
    }

    public SelectItem[] getTipoCalificacion() {
        return tipoCalificacion;
    }

    public void setTipoCalificacion(SelectItem[] tipoCalificacion) {
        this.tipoCalificacion = tipoCalificacion;
    }

    public String getTipoAspectosSel() {
        return tipoAspectosSel;
    }

    public void setTipoAspectosSel(String tipoAspectosSel) {
        this.tipoAspectosSel = tipoAspectosSel;
    }

    public List<?> getListaAsistentes() {
        return listaAsistentes;
    }

    public void setListaAsistentes(List<?> listaAsistentes) {
        this.listaAsistentes = listaAsistentes;
    }

    public List<Object[]> getListaEventos() {
        return listaEventos;
    }

    public String getEventoAsist() {
        return eventoAsist;
    }

    public void setEventoAsist(String eventoAsist) {
        this.eventoAsist = eventoAsist;
    }

    public List<?> getListaAsistentes1() {
        return listaAsistentes1;
    }

    public void setListaAsistentes1(List<?> listaAsistentes1) {
        this.listaAsistentes1 = listaAsistentes1;
    }

    public SelectItem[] getTipoAsistente1() {
        return tipoAsistente1;
    }

    public String getCooproductoSel() {
        return cooproductoSel;
    }

    public void setCooproductoSel(String cooproductoSel) {
        this.cooproductoSel = cooproductoSel;
    }

    public SelectItem[] getTipoCooproducto() {
        return tipoCooproducto;
    }

    public List<Object[]> getListaCooproductos() {
        return listaCooproductos;
    }

    public String getCalificacion1Sel() {
        return calificacion1Sel;
    }

    public void setCalificacion1Sel(String calificacion1Sel) {
        this.calificacion1Sel = calificacion1Sel;
    }

    public String getCalificacion2Sel() {
        return calificacion2Sel;
    }

    public void setCalificacion2Sel(String calificacion2Sel) {
        this.calificacion2Sel = calificacion2Sel;
    }

    public String getCalificacion3Sel() {
        return calificacion3Sel;
    }

    public void setCalificacion3Sel(String calificacion3Sel) {
        this.calificacion3Sel = calificacion3Sel;
    }

    public SelectItem[] getTipoCalificacion1() {
        return tipoCalificacion1;
    }

    public SelectItem[] getTipoCalificacion2() {
        return tipoCalificacion2;
    }

    public SelectItem[] getTipoCalificacion3() {
        return tipoCalificacion3;
    }

    public String getDescripcionMovilidad() {
        return descripcionMovilidad;
    }

    public void setDescripcionMovilidad(String descripcionMovilidad) {
        this.descripcionMovilidad = descripcionMovilidad;
    }

    public List<MovilidadArchivo> getListaArchivosObligatorios() {
        return listaArchivosObligatorios;
    }

    public SelectItem[] getTipoDocumentoSelItem() {
        return tipoDocumentoSelItem;
    }

    public String getTipoDocumentoSel() {
        return tipoDocumentoSel;
    }

    public void setTipoDocumentoSel(String tipoDocumentoSel) {
        this.tipoDocumentoSel = tipoDocumentoSel;
    }

    public DefaultUploadedFile getArchivoObligatorio() {
        return archivoObligatorio;
    }

    public void setArchivoObligatorio(DefaultUploadedFile archivoObligatorio) {
        this.archivoObligatorio = archivoObligatorio;
    }

    public List<ArchivoMovilidad> getListaArchivosObligatoriosSel() {
        return listaArchivosObligatoriosSel;
    }

    public void setListaArchivosObligatoriosSel(List<ArchivoMovilidad> listaArchivosObligatoriosSel) {
        this.listaArchivosObligatoriosSel = listaArchivosObligatoriosSel;
    }

    public Object[] getEventoTable() {
        return eventoTable;
    }

    public void setEventoTable(Object[] eventoTable) {
        this.eventoTable = eventoTable;
    }

    public ActividadMovilidadVE getAct() {
        return act;
    }

    public void setAct(ActividadMovilidadVE act) {
        this.act = act;
    }

    public Object[] getCoproductoTable() {
        return coproductoTable;
    }

    public void setCoproductoTable(Object[] coproductoTable) {
        this.coproductoTable = coproductoTable;
    }

    public ArchivoMovilidad getArchivoTabla() {
        return archivoTabla;
    }

    public void setArchivoTabla(ArchivoMovilidad archivoTabla) {
        this.archivoTabla = archivoTabla;
    }

    public SelectItem[] getTipoAsistente() {
        return tipoAsistente;
    }

    public String getApoyoTotal() {
        return apoyoTotal;
    }

    public void setApoyoTotal(String apoyoTotal) {
        this.apoyoTotal = apoyoTotal;
    }

    public String getCostoTiquetes() {
        return costoTiquetes;
    }

    public void setCostoTiquetes(String costoTiquetes) {
        this.costoTiquetes = costoTiquetes;
    }

    public String getValorTotalViativos() {
        return valorTotalViativos;
    }

    public void setValorTotalViativos(String valorTotalViativos) {
        this.valorTotalViativos = valorTotalViativos;
    }

    public List<ActividadMovilidadSeguimientoVE> getActividadesAdicionales() {
        return actividadesAdicionales;
    }

    public String getDescripcionNuevaActividad() {
        return descripcionNuevaActividad;
    }

    public void setDescripcionNuevaActividad(String descripcionNuevaActividad) {
        this.descripcionNuevaActividad = descripcionNuevaActividad;
    }

    public ActividadMovilidadSeguimientoVE getActividadSelTabla() {
        return actividadSelTabla;
    }

    public void setActividadSelTabla(ActividadMovilidadSeguimientoVE actividadSelTabla) {
        this.actividadSelTabla = actividadSelTabla;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    public String getDescripcionPro() {
        return descripcionPro;
    }

    public void setDescripcionPro(String descripcionPro) {
        this.descripcionPro = descripcionPro;
    }

    public boolean isConsulta() {
        return consulta;
    }

    public boolean isEsConsultaFacultad() {
        return esConsultaFacultad;
    }

    public boolean isEsRevisionFacultad() {
        return esRevisionFacultad;
    }

    public String getComentariosInforme() {
        return comentariosInforme;
    }

    public void setComentariosInforme(String comentariosInforme) {
        this.comentariosInforme = comentariosInforme;
    }

    public String getMensajeRevisionFacultad() {
        return mensajeRevisionFacultad;
    }

    public boolean isRevisadoFacultad() {
        return revisadoFacultad;
    }

    public String getEstadoRevisionSeguimiento() {
        return estadoRevisionSeguimiento;
    }

    public Persona getResponsableRevision() {
        return responsableRevision;
    }

    public Date getFechaRevision() {
        return fechaRevision;
    }

    public String getRealizacionMovilidad() {
        return realizacionMovilidad;
    }

    public void setRealizacionMovilidad(String realizacionMovilidad) {
        this.realizacionMovilidad = realizacionMovilidad;
    }

    public String getRazonesNoRealizacion() {
        return razonesNoRealizacion;
    }

    public void setRazonesNoRealizacion(String razonesNoRealizacion) {
        this.razonesNoRealizacion = razonesNoRealizacion;
    }

    /**
     * @return the esRevisionSede
     */
    public boolean isEsRevisionSede() {
        return esRevisionSede;
    }

    /**
     * @return the revisadoSede
     */
    public boolean isRevisadoSede() {
        return revisadoSede;
    }

	public Long getApoyoTotalAdicional() {
		return apoyoTotalAdicional;
	}

	public void setApoyoTotalAdicional(Long apoyoTotalAdicional) {
		this.apoyoTotalAdicional = apoyoTotalAdicional;
	}

	public boolean isCompletado() {
		return completado;
	}

	public void setCompletado(boolean completado) {
		this.completado = completado;
	}

}
