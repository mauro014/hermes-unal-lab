package co.edu.unal.hermes.vista.movilidad;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.ActividadMovilidad;
import co.edu.unal.hermes.modelo.ArchivoMovilidad;
import co.edu.unal.hermes.modelo.Continente;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.MovilidadArchivo;
import co.edu.unal.hermes.modelo.MovilidadDocentesArtes;
import co.edu.unal.hermes.modelo.Pais;
import co.edu.unal.hermes.modelo.Parametro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoMovilidad;
import co.edu.unal.hermes.modelo.correo.Correo;

public class ManejadorEditarMovilidadDocArt extends ManejadorBaseMovilidad {

    private static final long serialVersionUID = 8376979453614871026L;

    private MovilidadDocentesArtes movArt;

    private String errores[];
    private boolean panelRender[];
    private boolean panelRenderError[];

    private String documento;
    private TipoDocumento tipoDocumento;
    private SelectItem[] tipoDocumentoItem;
    private SelectItem[] tipoDocumentoSelItem;
    private SelectItem[] movilidadItem;

    private List paises;
    private List nacionalidad;
    private List continentes;
    private List listaArchivos;
    private List areaArte;
    private List seleccionReceptora;
    private List tipoInstitucion;
    private List movilidadAnterior;
    private List confirmarResidencia;
    private List comoEntero;
    private List listaActividades;
    private List listaArchivosObligatoriosSel;

    private List avalFacultad;
    private List avalSede;

    // VARIABLES TEMPORALES
    private String sede;
    private String facultadDocente;
    private String departamentoDocente;
    private String nombreDocente;
    private String documentoDocente;
    private String descripcionActividad;
    private Date fechaActividad;
    private String duracionActividad;
    private String tipoDocumentoSel;
    private UploadedFile archivoObligatorio;
    private Long codigoMovilidad = Long.parseLong("-1");

    private final String TIPO_MOVILIDAD = "C1";

    private HtmlDataTable tablaArchivos;

    private int estado = 0;

    private Date fechaMinimaFinalizacion;

    private Date fechaMaximaFinalizacion;

    private Date fechaMinimaInicio;

    private ActividadMovilidad actividadMovilidadSeleccionada;

    private boolean puedeSubirArchivos;

    private ArchivoMovilidad archivoMovilidadSeleccionado;

    private ArchivoMovilidad documentoSeleccionado;

    public ManejadorEditarMovilidadDocArt() throws SQLException {

        movArt = new MovilidadDocentesArtes();

        cargarValoresIniciales();
        cargarLista();
        calcularFechaMinimaInicio();
        buscarPersona();

    }

    public void cargarLista() {
        List listaMovilidades = servicioGeneral
                .obtenerListaObjetos("MovilidadDocentesArtes m where m.personaInv.id.documento = '"
                        + personaActual.getId().getDocumento() + "' and m.personaInv.id.tipoDocumento = '"
                        + personaActual.getId().getTipoDocumento() + "' and m.estado = 'I' ORDER BY m.id");

        if (listaMovilidades != null && listaMovilidades.size() > 0) {
            movilidadItem = new SelectItem[listaMovilidades.size()];
            for (int i = 0; i < listaMovilidades.size(); i++) {
                MovilidadDocentesArtes movilidad = (MovilidadDocentesArtes) listaMovilidades.get(i);
                movilidadItem[i] = new SelectItem(movilidad.getId(),
                        "Id=" + movilidad.getId() + " - Etapa Registro(" + movilidad.getEtapa() + "/9)");
            }
            codigoMovilidad = ((MovilidadDocentesArtes) listaMovilidades.get(0)).getId();
        } else {
            movilidadItem = new SelectItem[0];

        }
    }

    private void infoModalidad(Long idModalidad_) {
        List listaMovilidadConsulta;
        listaMovilidadConsulta = new ArrayList();

        listaMovilidadConsulta = servicioGeneral
                .obtenerListaObjetos("MovilidadDocentesArtes where id = '" + idModalidad_ + "'");
        if (listaMovilidadConsulta != null && listaMovilidadConsulta.size() > 0) {
            movArt = (MovilidadDocentesArtes) listaMovilidadConsulta.get(0);
        }
    }

    private void cargarTiposDocumentos() {
        List listaArchivosObligatorios = new ArrayList();
        listaArchivosObligatorios = servicioGeneral.obtenerListaArchivosMovilidad("C1");
        if (listaArchivosObligatorios != null && listaArchivosObligatorios.size() > 0) {
            tipoDocumentoSelItem = new SelectItem[listaArchivosObligatorios.size() + 1];
            tipoDocumentoSelItem[0] = new SelectItem("", "Seleccione un tipo de documento");
            for (int i = 0; i < listaArchivosObligatorios.size(); i++) {
                MovilidadArchivo mva = (MovilidadArchivo) listaArchivosObligatorios.get(i);
                tipoDocumentoSelItem[i + 1] = new SelectItem(mva.getTipoArchivo().getId().toString(),
                        mva.getTipoArchivo().getNombre());
            }
        }
    }

    private void cargarContinentes() {
        List listaContinentes = servicioGeneral.obtenerListaObjetosOrdenadosAsc(new Continente(), "nombre");
        continentes = new Vector();
        for (Iterator it = listaContinentes.iterator(); it.hasNext();) {
            Continente p = (Continente) it.next();
            SelectItem s = new SelectItem(p.getId(), p.getNombre());
            continentes.add(s);
        }
    }

    private void reiniciarVariables() {
        movArt = new MovilidadDocentesArtes();
        errores = new String[60];
        panelRender = new boolean[10];
        panelRenderError = new boolean[60];
        Pais pais = new Pais();
        pais.setId("CO");
        Pais nacionalidad = new Pais();
        nacionalidad.setId("CO");
        movArt.setPais(pais);
        movArt.setNacionalidad(nacionalidad);
        Continente continente = new Continente();
        continente.setId(new Long("1"));
        movArt.setContinente(continente);
    }

    public void limpiar() {
        movArt = new MovilidadDocentesArtes();
        documento = new String("");
        cargarValoresIniciales();
        ocultarPaneles(0);
    }

    void processChild(List childList) {
        for (int i = 0; i < childList.size(); i++) {
            UIComponent component = (UIComponent) childList.get(i);
            try {
                UIInput input = (UIInput) component;
                input.setSubmittedValue(null);
            } catch (Exception ex) {

            }
            List childList2 = component.getChildren();
            processChild(childList2);
        }
    }

    public void cancelAction(ActionEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot viewRoot = facesContext.getViewRoot();
        List childList = viewRoot.getChildren();
        processChild(childList);
    }

    public boolean validarPresupuesto() {
        boolean bandera = true;
        int totalUN = 0;
        int totalPropio = 0;

        if (this.movArt.getTiquetesUN() != null) {
            try {
                int cantidad = Integer.parseInt(movArt.getTiquetesUN());
                totalUN = totalUN + cantidad;
            } catch (Exception e) {
                this.errores[29] = "Valor NO valido";
                this.panelRenderError[29] = true;
                bandera = false;
            }
        }

        if (this.movArt.getTiquetesP() != null) {
            try {
                int cantidad = Integer.parseInt(movArt.getTiquetesP());
                totalPropio = totalPropio + cantidad;
            } catch (Exception e) {
                this.errores[30] = "Valor NO valido";
                this.panelRenderError[30] = true;
                bandera = false;
            }
        }

        if (this.movArt.getTallerUN() != null) {
            try {
                int cantidad = Integer.parseInt(movArt.getTallerUN());
                totalUN = totalUN + cantidad;
            } catch (Exception e) {
                this.errores[31] = "Valor NO valido";
                this.panelRenderError[31] = true;
                bandera = false;
            }
        }

        if (this.movArt.getTallerP() != null) {
            try {
                int cantidad = Integer.parseInt(movArt.getTallerP());
                totalPropio = totalPropio + cantidad;
            } catch (Exception e) {
                this.errores[32] = "Valor NO valido";
                this.panelRenderError[32] = true;
                bandera = false;
            }
        }

        if (this.movArt.getAlojamientoUN() != null) {
            try {
                int cantidad = Integer.parseInt(movArt.getAlojamientoUN());
                totalUN = totalUN + cantidad;
            } catch (Exception e) {
                this.errores[33] = "Valor NO valido";
                this.panelRenderError[33] = true;
                bandera = false;
            }
        }

        if (this.movArt.getAlojamientoP() != null) {
            try {
                int cantidad = Integer.parseInt(movArt.getAlojamientoP());
                totalPropio = totalPropio + cantidad;
            } catch (Exception e) {
                this.errores[34] = "Valor NO valido";
                this.panelRenderError[34] = true;
                bandera = false;
            }
        }

        if (this.movArt.getAlimentacionUN() != null) {
            try {
                int cantidad = Integer.parseInt(movArt.getAlimentacionUN());
                totalUN = totalUN + cantidad;
            } catch (Exception e) {
                this.errores[35] = "Valor NO valido";
                this.panelRenderError[35] = true;
                bandera = false;
            }
        }

        if (this.movArt.getAlimentacionP() != null) {
            try {
                int cantidad = Integer.parseInt(movArt.getAlimentacionP());
                totalPropio = totalPropio + cantidad;
            } catch (Exception e) {
                this.errores[36] = "Valor NO valido";
                this.panelRenderError[36] = true;
                bandera = false;
            }
        }

        if (this.movArt.getMaterialesUN() != null) {
            try {
                int cantidad = Integer.parseInt(movArt.getMaterialesUN());
                totalUN = totalUN + cantidad;
            } catch (Exception e) {
                this.errores[37] = "Valor NO valido";
                this.panelRenderError[37] = true;
                bandera = false;
            }
        }

        if (this.movArt.getMaterialesP() != null) {
            try {
                int cantidad = Integer.parseInt(movArt.getMaterialesP());
                totalPropio = totalPropio + cantidad;
            } catch (Exception e) {
                this.errores[38] = "Valor NO valido";
                this.panelRenderError[38] = true;
                bandera = false;
            }
        }

        if (this.movArt.getTransporteUN() != null) {
            try {
                int cantidad = Integer.parseInt(movArt.getTransporteUN());
                totalUN = totalUN + cantidad;
            } catch (Exception e) {
                this.errores[39] = "Valor NO valido";
                this.panelRenderError[39] = true;
                bandera = false;
            }
        }

        if (this.movArt.getTransporteP() != null) {
            try {
                int cantidad = Integer.parseInt(movArt.getTransporteP());
                totalPropio = totalPropio + cantidad;
            } catch (Exception e) {
                this.errores[40] = "Valor NO valido";
                this.panelRenderError[40] = true;
                bandera = false;
            }
        }

        if (this.movArt.getSocializacionUN() != null) {
            try {
                int cantidad = Integer.parseInt(movArt.getSocializacionUN());
                totalUN = totalUN + cantidad;
            } catch (Exception e) {
                this.errores[41] = "Valor NO valido";
                this.panelRenderError[41] = true;
                bandera = false;
            }
        }

        if (this.movArt.getSocializacionP() != null) {
            try {
                int cantidad = Integer.parseInt(movArt.getSocializacionP());
                totalPropio = totalPropio + cantidad;
            } catch (Exception e) {
                this.errores[42] = "Valor NO valido";
                this.panelRenderError[42] = true;
                bandera = false;
            }
        }

        int africaDocente = 0;
        int americaDocente = 0;
        int asiaDocente = 0;
        int europaDocente = 0;
        int oceaniaDocente = 0;
        int salario = 1;

        List listaParametroUno = this.servicioGeneral.obtenerObjetos("FROM Parametro WHERE nombre = 'AFRICA_DOCENTE'");
        List listaParametroDos = this.servicioGeneral.obtenerObjetos("FROM Parametro WHERE nombre = 'AMERICA_DOCENTE'");
        List listaParametroTres = this.servicioGeneral.obtenerObjetos("FROM Parametro WHERE nombre = 'ASIA_DOCENTE'");
        List listaParametroCuatro = this.servicioGeneral
                .obtenerObjetos("FROM Parametro WHERE nombre = 'EUROPA_DOCENTE'");
        List listaParametroCinco = this.servicioGeneral
                .obtenerObjetos("FROM Parametro WHERE nombre = 'OCEANIA_DOCENTE'");
        List listaParametroSalario = this.servicioGeneral.obtenerObjetos("FROM Parametro WHERE id = 43");

        if (listaParametroUno != null && listaParametroUno.size() > 0) {
            Parametro par = (Parametro) listaParametroUno.get(0);
            africaDocente = Integer.valueOf((par.getValor())).intValue();
        }

        if (listaParametroDos != null && listaParametroDos.size() > 0) {
            Parametro par = (Parametro) listaParametroDos.get(0);
            americaDocente = Integer.valueOf((par.getValor())).intValue();
        }

        if (listaParametroTres != null && listaParametroTres.size() > 0) {
            Parametro par = (Parametro) listaParametroTres.get(0);
            asiaDocente = Integer.valueOf((par.getValor())).intValue();
        }

        if (listaParametroCuatro != null && listaParametroCuatro.size() > 0) {
            Parametro par = (Parametro) listaParametroCuatro.get(0);
            europaDocente = Integer.valueOf((par.getValor())).intValue();
        }

        if (listaParametroCinco != null && listaParametroCinco.size() > 0) {
            Parametro par = (Parametro) listaParametroCinco.get(0);
            oceaniaDocente = Integer.valueOf((par.getValor())).intValue();
        }

        if (listaParametroSalario != null && listaParametroSalario.size() > 0) {
            Parametro par = (Parametro) listaParametroSalario.get(0);
            salario = Integer.valueOf((par.getValor())).intValue();
        }

        if (this.movArt.getContinente().getId() == 1 && totalUN > (africaDocente * salario)) {
            this.errores[2] = "Valor excede el monto para el continente seleccionado";
            this.panelRenderError[2] = true;
            bandera = false;
        }

        if (this.movArt.getContinente().getId() == 2 && totalUN > (americaDocente * salario)) {
            this.errores[2] = "Valor excede el monto para el continente seleccionado";
            this.panelRenderError[2] = true;
            bandera = false;

        }

        if ((this.movArt.getContinente().getId() == 3 || this.movArt.getContinente().getId() == 5)
                && (totalUN > (asiaDocente * salario))) {
            this.errores[2] = "Valor excede el monto para el continente seleccionado";
            this.panelRenderError[2] = true;
            bandera = false;

        }

        if (this.movArt.getContinente().getId() == 4 && totalUN > (europaDocente * salario)) {
            this.errores[2] = "Valor excede el monto para el continente seleccionado";
            this.panelRenderError[2] = true;
            bandera = false;
        }

        this.movArt.setTotalUN(String.valueOf(totalUN));
        this.movArt.setTotalPropio(String.valueOf(totalPropio));

        return bandera;
    }

    public void calcularTotales() {
        limpiarErores();
        if (validarPresupuesto()) {
            // ocultarPaneles(11);
        }

    }

    /// ****guardar Archivo

    public void guardarArchivoMovDA(FileUploadEvent event) {

        archivoObligatorio = event.getFile();
        System.out.println("tipoDocumentoSel: " + tipoDocumentoSel);
        if (tipoDocumentoSel == null || tipoDocumentoSel.equals("")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar un tipo de archivo", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        ArchivoMovilidad archivoMovilidad = new ArchivoMovilidad();
        archivoMovilidad = insertarArchivoMovilidadDAGenerico(movArt.getId(), archivoObligatorio, tipoDocumentoSel);
        listaArchivosObligatoriosSel.add(archivoMovilidad);

    }

    public void descargarArchivoMovDocArt() {
        ArchivoMovilidad archivo = documentoSeleccionado;
        descargarArchivoMovilidadGenerico(archivo.getId());
    }

    public void guardarArchivoObligatorio(FileUploadEvent event) {
        archivoObligatorio = event.getFile();
        if (tipoDocumentoSel == null || tipoDocumentoSel.equals("")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar un tipo de archivo", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        ArchivoMovilidad archivoMovilidad = new ArchivoMovilidad();
        archivoMovilidad = insertarArchivoMovilidadGenerico(1, archivoObligatorio, tipoDocumentoSel, TIPO_MOVILIDAD);
        listaArchivosObligatoriosSel.add(archivoMovilidad);

    }

    public void eliminarArchivoObligatorio() {
        listaArchivosObligatoriosSel.remove(archivoMovilidadSeleccionado);
        servicioGeneral.eliminarObjeto(archivoMovilidadSeleccionado);
        eliminarArchivoMovilidadGenerico(archivoMovilidadSeleccionado.getId());
    }

    public void buscarPersona() throws SQLException {
        reiniciarVariables();

        Long movId = (Long) sesion.getAttribute("movilidadDocArt");

        codigoMovilidad = movId;

        List objeto = this.servicioGeneral
                .obtenerListaObjetos("MovilidadDocentesArtes m where m.id = '" + codigoMovilidad + "'");

        if (objeto != null && objeto.size() > 0) {
            MovilidadDocentesArtes movilidad = (MovilidadDocentesArtes) objeto.get(0);
            this.movArt = movilidad;

            List lista = this.servicioGeneral
                    .obtenerListaObjetos("ArchivoMovilidad a where a.movilidad = '" + this.movArt.getId() + "'");
            this.setListaArchivosObligatoriosSel(lista);

            List lista1 = this.servicioGeneral
                    .obtenerListaObjetos("ActividadMovilidad a where a.movilidad = '" + this.movArt.getId() + "'");
            this.setListaActividades(lista1);

            if (movArt.getPersonaInv() != null) {

                if (movArt.getPersonaInv() instanceof Investigador) {
                    if (movArt.getPersonaInv() instanceof InvestigadorInterno) {
                        movArt.setPersonaInv(
                                servicioPersona.obtenerInvestigadorInternoCompleto(movArt.getPersonaInv().getId()));
                        InvestigadorInterno investigadorInterno = (InvestigadorInterno) movArt.getPersonaInv();
                        Dependencia dependencia;
                        dependencia = servicioDependencia.obtenerDependencia(investigadorInterno.getId());
                        String nombre1, nombre2, apellido1, apellido2;

                        if (investigadorInterno.getTipoDedicacion() != null
                                && (investigadorInterno.getTipoDedicacion().getId().equals(Investigador.EXCLUSIVA)
                                        || investigadorInterno.getTipoDedicacion().getId()
                                                .equals(Investigador.TIEMPOCOMPLETO)
                                || investigadorInterno.getTipoDedicacion().getId().equals(Investigador.MEDIOTIEMPO))) {
                            if (investigadorInterno.getNombre1() != null) {
                                nombre1 = investigadorInterno.getNombre1();
                            } else {
                                nombre1 = "";
                            }
                            if (investigadorInterno.getNombre2() != null) {
                                nombre2 = investigadorInterno.getNombre2();
                            } else {
                                nombre2 = "";
                            }
                            if (investigadorInterno.getApellido1() != null) {
                                apellido1 = investigadorInterno.getApellido1();
                            } else {
                                apellido1 = "";
                            }
                            if (investigadorInterno.getApellido2() != null) {
                                apellido2 = investigadorInterno.getApellido2();
                            } else {
                                apellido2 = "";
                            }
                            this.nombreDocente = nombre1 + " " + nombre2 + " " + apellido1 + " " + apellido2;

                            this.documentoDocente = investigadorInterno.getId().getDocumento();

                            if (dependencia != null && dependencia.getFacultad() != null) {
                                this.sede = dependencia.getSede().getNombre();
                                this.facultadDocente = dependencia.getFacultad().getNombre();
                                this.departamentoDocente = dependencia.getNombre();

                                // ocultarPaneles(9);
                                if (movArt.getEtapa().equals("") || movArt.getEtapa().equals(null)) {
                                    ocultarPaneles(1);
                                    estado = 1;
                                } else {
                                    estado = Integer.parseInt(movArt.getEtapa()) + 1;
                                    ocultarPaneles(estado);
                                }

                            } else {
                                errores[0] = "La dependencia del investigador no tiene una facultad asociada";
                                panelRenderError[0] = true;
                                ocultarPaneles(0);
                            }
                        } else {
                            errores[0] = "El investigador debe ser de dedicación exclusiva o tiempo completo de la Universidad Nacional de Colombia";
                            panelRenderError[0] = true;
                            ocultarPaneles(0);
                        }
                    } else {
                        errores[0] = "El documento ingresado no corresponde a un investigador";
                        panelRenderError[0] = true;
                        ocultarPaneles(0);
                    }
                } else {

                    errores[0] = "El documento ingresado no corresponde a un investigador";
                    panelRenderError[0] = true;
                    ocultarPaneles(0);
                }
            } else {

                errores[0] = "El documento ingresado no existe";
                panelRenderError[0] = true;
                ocultarPaneles(0);
            }

        }

    }

    // }

    public void adicionarActividad() {
        ActividadMovilidad actividad = new ActividadMovilidad();

        try {

            this.errores[25] = this.errores[26] = this.errores[27] = "";
            this.panelRenderError[25] = this.panelRenderError[26] = this.panelRenderError[27] = false;

            if (this.descripcionActividad == null || this.descripcionActividad.equals("")
                    || this.descripcionActividad.length() > 2000) {
                this.errores[25] = "La descripción es NO valida";
                this.panelRenderError[25] = true;
            } else {
                actividad.setDescripcion(descripcionActividad);
            }

            if (this.fechaActividad == null) {
                this.errores[26] = "La fecha es NO valida";
                this.panelRenderError[26] = true;
            } else {
                if (this.fechaActividad.before(this.movArt.getFechainicial())) {
                    this.errores[26] = "La fecha debe ser posterior a la fecha de inicio del viaje";
                    this.panelRenderError[26] = true;

                } else if (this.fechaActividad.after(this.movArt.getFechafinal())) {
                    this.errores[26] = "La fecha debe ser anterior a la fecha de fin del viaje";
                    this.panelRenderError[26] = true;
                } else {
                    actividad.setFecha(fechaActividad);
                }
            }

            if (this.duracionActividad == null || this.duracionActividad.length() == 0) {
                this.errores[27] = "La duración es NO valida";
                this.panelRenderError[27] = true;
            } else {
                try {
                    int cantidad = Integer.parseInt(this.duracionActividad);
                    if (cantidad <= 0 || cantidad > 30) {
                        this.errores[27] = "Duración fuera de rango (1 - 100)";
                        this.panelRenderError[27] = true;
                    } else {
                        actividad.setDuracion(cantidad);
                    }

                } catch (Exception e) {
                    this.errores[27] = "La duración es NO valida";
                    this.panelRenderError[27] = true;
                }
            }

            if (this.panelRenderError[25] == false && this.panelRenderError[26] == false
                    && this.panelRenderError[27] == false) {
                this.descripcionActividad = "";
                this.fechaActividad = null;
                this.duracionActividad = null;
                this.listaActividades.add(actividad);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void eliminarActividad() {
        listaActividades.remove(actividadMovilidadSeleccionada);
    }

    public void seleccionarTipoDocumento() {
        puedeSubirArchivos = false;
        if (!tipoDocumentoSel.equals("")) {
            puedeSubirArchivos = true;
        }
        System.out.println("tipoDocumentoSel: " + tipoDocumentoSel);
        System.out.println("puedeSubirArchivos: " + puedeSubirArchivos);
    }

    public void verArchivo() {
        ArchivoMovilidad ain = archivoMovilidadSeleccionado;

        FacesContext ctx = FacesContext.getCurrentInstance();

        if (ain != null && ain.getArchivo() != null) {

            try {
                if (!ctx.getResponseComplete()) {
                    HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
                    response.setContentType("text/plain");
                    response.setHeader("Content-Disposition", "attachment;filename=\"" + ain.getNombre() + "\"");
                    ServletOutputStream out = response.getOutputStream();
                    out.write(ain.getBytes());
                    out.flush();
                    ctx.responseComplete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void eliminarArchivo() {
        listaArchivos.remove(tablaArchivos.getRowIndex());
    }

    public void limpiarErores() {
        for (int i = 0; i < 60; i++) {
            this.errores[i] = "";
            this.panelRenderError[i] = false;
        }
    }

    public void limpiarErores(int min, int max) {
        for (int i = min; i <= max; i++) {
            this.errores[i] = "";
            this.panelRenderError[i] = false;
        }
    }

    public boolean validacion(int estado) {
        boolean bandera = true;

        if (estado >= 1) {
            limpiarErores(0, 7);
            if (this.movArt.getNombreArtistico() != null && this.movArt.getNombreArtistico().length() > 100) {
                this.errores[1] = "Nombre artistico NO valido";
                this.panelRenderError[1] = true;
                bandera = false;
            }

            if (this.movArt.getPasaporte() == null || this.movArt.getPasaporte().length() <= 0
                    || this.movArt.getPasaporte().length() > 20) {
                this.errores[3] = "Pasaporte es NO valido";
                this.panelRenderError[3] = true;
                bandera = false;
            }

            if (this.movArt.getPaginaWeb() != null && this.movArt.getPaginaWeb().length() > 200) {
                this.errores[4] = "Página web NO valida";
                this.panelRenderError[4] = true;
                bandera = false;
            }

            // Tel fijo
            this.errores[5] = "";
            this.panelRenderError[5] = false;

            if (this.movArt.getTelefonoFijo() == null || this.movArt.getTelefonoFijo().length() <= 0
                    || this.movArt.getTelefonoFijo().length() > 20) {
                this.errores[5] = "Telefono fijo NO valido";
                this.panelRenderError[5] = true;
                bandera = false;
            } else {

                try {
                    Long valor = Long.parseLong(this.movArt.getTelefonoFijo());
                    if (valor <= -1) {
                        this.errores[5] = "Telefono fijo NO valido";
                        this.panelRenderError[5] = true;
                        bandera = false;
                    }

                } catch (Exception e) {
                    this.errores[5] = "Teléfono fijo NO valido";
                    this.panelRenderError[5] = true;
                    bandera = false;
                }
            }

            // tel móvil
            this.errores[6] = "";
            this.panelRenderError[6] = false;

            if (this.movArt.getTelefonoMovil() == null || this.movArt.getTelefonoMovil().length() <= 0
                    || this.movArt.getTelefonoMovil().length() > 20) {
                this.errores[6] = "Teléfono móvil NO valido";
                this.panelRenderError[6] = true;
                bandera = false;
            } else {

                try {
                    Long valor = Long.parseLong(this.movArt.getTelefonoMovil());
                    if (valor <= -1) {
                        this.errores[6] = "Teléfono móvil NO valido";
                        this.panelRenderError[6] = true;
                        bandera = false;
                    }

                } catch (Exception e) {
                    this.errores[6] = "Teléfono Móvil NO valido";
                    this.panelRenderError[6] = true;
                    bandera = false;
                }
            }

            // Direccion
            if (this.movArt.getDireccion() == null || this.movArt.getDireccion().length() <= 0
                    || this.movArt.getDireccion().length() > 200) {
                this.errores[7] = "Dirección NO valida";
                this.panelRenderError[7] = true;
                bandera = false;
            }

        }

        if (estado >= 2) {

            limpiarErores(8, 13);

            if (this.movArt.getNombreProyecto() == null || this.movArt.getNombreProyecto().length() <= 0) {
                this.errores[8] = "Nombre del Proyecto NO valido";
                this.panelRenderError[8] = true;
                bandera = false;
            }

            if (this.movArt.getStatementArtista() == null || this.movArt.getStatementArtista().length() <= 0) {
                this.errores[9] = "Statement del Artista NO valido";
                this.panelRenderError[9] = true;
                bandera = false;
            }

            if (this.movArt.getDescripcionProyecto() == null || this.movArt.getDescripcionProyecto().length() <= 0) {
                this.errores[10] = "Descripción del Proyecto NO valida";
                this.panelRenderError[10] = true;
                bandera = false;
            }

            if (this.movArt.getObjetivoGeneral() == null || this.movArt.getObjetivoGeneral().length() <= 0) {
                this.errores[11] = "Objetivo General NO valido";
                this.panelRenderError[11] = true;
                bandera = false;
            }

            if (this.movArt.getObjetivoEspecifico() == null || this.movArt.getObjetivoEspecifico().length() <= 0) {
                this.errores[12] = "Objetivos Específicos NO validos";
                this.panelRenderError[12] = true;
                bandera = false;
            }

            /*
             * if (this.movArt.getProyectoRelacionLocal() == null ||
             * this.movArt.getProyectoRelacionLocal().length() <= 0) {
             * this.errores[13] = "Valor NO valido"; this.panelRenderError[13] =
             * true; bandera = false; }
             */
        }

        if (estado >= 3) {
            limpiarErores(14, 23);
            if (this.movArt.getFechainicial() == null) {
                this.errores[14] = "Fecha inicial NO valida";
                this.panelRenderError[14] = true;
                bandera = false;
            }

            if (this.movArt.getFechafinal() == null) {
                this.errores[15] = "Fecha Final NO valida";
                this.panelRenderError[15] = true;
                bandera = false;
            }

            if (this.movArt.getCiudad() == null || this.movArt.getCiudad().length() <= 0
                    || this.movArt.getCiudad().length() > 50) {
                this.errores[18] = "Ciudad NO valida";
                this.panelRenderError[18] = true;
                bandera = false;
            }

            if (this.movArt.getInstitucion() == null || this.movArt.getInstitucion().length() <= 0
                    || this.movArt.getInstitucion().length() > 300) {
                this.errores[21] = "Institución NO valida";
                this.panelRenderError[21] = true;
                bandera = false;
            }

            if (this.movArt.getDescripcionPrograma() == null || this.movArt.getDescripcionPrograma().length() <= 0) {
                this.errores[23] = "Valor NO valido";
                this.panelRenderError[23] = true;
                bandera = false;
            }

        }

        if (estado >= 4) {
            limpiarErores(28, 28);
            if (this.listaActividades == null || this.listaActividades.size() <= 0) {
                this.errores[28] = "No se encuentran actividades registradas";
                this.panelRenderError[28] = true;
                bandera = false;
            }
        }

        if (estado >= 5) {

            // limpiarErores(8, 13);

        }

        if (estado >= 6) {

            limpiarErores(43, 49);
            if (this.movArt.getPlanSocializacionResidencia() == null
                    || this.movArt.getPlanSocializacionResidencia().length() <= 0) {
                this.errores[43] = "Plan de Socialización NO validos";
                this.panelRenderError[43] = true;
                bandera = false;
            }

            if (this.movArt.getPlanSocializacionUN() == null || this.movArt.getPlanSocializacionUN().length() <= 0) {
                this.errores[49] = "Plan de Socialización NO validos";
                this.panelRenderError[49] = true;
                bandera = false;
            }

        }

        if (estado >= 7) {

            limpiarErores(45, 50);
            /*
             * if (this.movArt.getAporteRedArtistas() == null ||
             * this.movArt.getAporteRedArtistas().length() <= 0) {
             * this.errores[45] = "Valor NO valido"; this.panelRenderError[45] =
             * true; bandera = false; }
             * 
             * if (this.movArt.getContribucion() == null ||
             * this.movArt.getContribucion().length() <= 0) { this.errores[46] =
             * "Valor NO valido"; this.panelRenderError[46] = true; bandera =
             * false; }
             */

            if (this.movArt.getEspecialistasArea() == null || this.movArt.getEspecialistasArea().length() <= 0) {
                this.errores[47] = "Valor NO valido";
                this.panelRenderError[47] = true;
                bandera = false;
            }

            if (this.movArt.getTrabajos() == null || this.movArt.getTrabajos().length() <= 0) {
                this.errores[50] = "Valor NO valido";
                this.panelRenderError[50] = true;
                bandera = false;
            }

        }

        if (estado >= 8) {

            limpiarErores(48, 48);
            if (this.listaArchivosObligatoriosSel == null || this.listaArchivosObligatoriosSel.size() <= 0) {
                this.errores[48] = "No se encuentran documentos registrados";
                this.panelRenderError[48] = true;
                bandera = false;
            }

        }

        return bandera;

    }

    public void guardar() {
        if (validacion(estado) && validarPresupuesto()) {
            if (estado >= 9) {

                // if (estado == 9) {
                this.movArt.setNombreProyecto(this.movArt.getNombreProyecto().toUpperCase());
                this.movArt.setCiudad(this.movArt.getCiudad().toUpperCase());
                this.movArt.setInstitucion(this.movArt.getInstitucion().toUpperCase());

                if (movArt.getConfirmarResidencia() != null && movArt.getConfirmarResidencia().equals("SI")) {
                    estado = 10;
                    this.movArt.setEstado("P");
                    this.movArt.setEtapa("9");
                    Calendar actual = Calendar.getInstance();
                    Date date = actual.getTime();
                    this.movArt.setFechasolicitud(date);
                    servicioGeneral.guardarObjeto(this.movArt);

                    Dependencia dependencia;
                    dependencia = servicioDependencia.obtenerDependencia(this.movArt.getPersonaInv().getId());
                    dependencia.getSede().getId();

                    List listaParametroSede = new ArrayList();

                    /*
                     * if (dependencia != null && dependencia.getSede() != null
                     * && (dependencia.getSede().getId() .compareTo(new
                     * Long("2")) == 0)) { listaParametroSede =
                     * this.servicioGeneral .obtenerObjetos(
                     * "FROM Parametro WHERE id = 62"); } if (dependencia !=
                     * null && dependencia.getSede() != null &&
                     * (dependencia.getSede().getId() .compareTo(new Long("3"))
                     * == 0)) { listaParametroSede = this.servicioGeneral
                     * .obtenerObjetos("FROM Parametro WHERE id = 63"); } if
                     * (dependencia != null && dependencia.getSede() != null &&
                     * (dependencia.getSede().getId() .compareTo(new Long("4"))
                     * == 0)) { listaParametroSede = this.servicioGeneral
                     * .obtenerObjetos("FROM Parametro WHERE id = 64"); } if
                     * (dependencia != null && dependencia.getSede() != null &&
                     * (dependencia.getSede().getId() .compareTo(new Long("5"))
                     * == 0)) { listaParametroSede = this.servicioGeneral
                     * .obtenerObjetos("FROM Parametro WHERE id = 65"); } if
                     * (dependencia != null && dependencia.getSede() != null &&
                     * (dependencia.getSede().getId() .compareTo(new Long("6"))
                     * == 0)) { listaParametroSede = this.servicioGeneral
                     * .obtenerObjetos("FROM Parametro WHERE id = 66"); } if
                     * (dependencia != null && dependencia.getSede() != null &&
                     * (dependencia.getSede().getId() .compareTo(new Long("7"))
                     * == 0)) { listaParametroSede = this.servicioGeneral
                     * .obtenerObjetos("FROM Parametro WHERE id = 67"); } if
                     * (dependencia != null && dependencia.getSede() != null &&
                     * (dependencia.getSede().getId() .compareTo(new Long("8"))
                     * == 0)) { listaParametroSede = this.servicioGeneral
                     * .obtenerObjetos("FROM Parametro WHERE id = 68"); }
                     */

                    List listaCorreoEncargado = new ArrayList();
                    List listaParametroAux;
                    listaParametroAux = new ArrayList();

                    listaParametroSede = this.servicioGeneral
                            .obtenerObjetos("FROM Parametro WHERE nombre = '" + dependencia.getSede().getId() + "'");

                    if (listaParametroSede == null || listaParametroSede.size() == 0) {
                        movArt.setAceptacionFacultad("SI");
                    }

                    /*
                     * String correoFacultad = "";
                     * 
                     * if (listaParametroSede != null &&
                     * listaParametroSede.size() > 0) { Parametro par =
                     * (Parametro) listaParametroSede.get(0); correoFacultad =
                     * par.getValor(); }
                     */

                    // Correo al investigador
                    CorreoPlantilla correoActual = new CorreoPlantilla();
                    String cuerpoCorreo = "";
                    correoActual = cargarPlantilla(80);
                    cuerpoCorreo = editarCorreo(this.movArt.getPersonaInv(), String.valueOf(this.movArt.getId()),
                            this.movArt.getTipoMovilidad().getNombre(), correoActual.getCuerpo());
                    Correo correo = new Correo();
                    correo.setOrigen(Correo.CORREO_HERMES);
                    String dirCorreo = this.movArt.getPersonaInv().getEmail();
                    // correo.adicionarDireccion(dirCorreo);
                    // correo.adicionarDireccion(correoFacultad);
                    correo.adicionarCopiaOculta(new String(this.movArt.getPersonaInv().getEmail()));
                    // correo.adicionarCopiaOculta(correoFacultad);
                    correo.setAsunto(correoActual.getAsunto());
                    correo.setCuerpo(cuerpoCorreo);
                    servicioCorreo.enviarCorreo(correo);

                    // this.limpiar();
                    ocultarPaneles(0);
                    // this.errores[0] = "Movilidad creada satisfactoriamente.";
                    // this.panelRenderError[0] = true;

                    // correo para revisión en la respectiva dependencia
                    if (listaParametroSede == null || listaParametroSede.size() == 0) {
                        listaParametroAux = this.servicioGeneral.obtenerObjetos(
                                "FROM Parametro WHERE nombre = '" + dependencia.getSede().getId() + "'");
                        correoActual = cargarPlantilla(86);

                        listaCorreoEncargado = this.servicioGeneral
                                .obtenerObjetos("FROM Parametro WHERE nombre = 'R_MOVILIDAD'    AND DESCRIPCION= '"
                                        + dependencia.getSede().getId() + "'");
                        InvestigadorInterno ii;
                        if (listaCorreoEncargado != null && listaCorreoEncargado.size() > 0) {
                            Parametro para = (Parametro) listaCorreoEncargado.get(0);
                            ii = servicioPersona
                                    .obtenerInvestigadorInterno(new IdPersona(para.getValor(), para.getDescripcion()));
                        } else {
                            ii = servicioPersona.obtenerInvestigadorInterno(new IdPersona("19380666", "C"));
                        }

                        listaCorreoEncargado = new ArrayList();
                        listaCorreoEncargado.add(ii);

                    } else {

                        listaParametroAux = this.servicioGeneral.obtenerObjetos(
                                "FROM Parametro WHERE nombre = '" + dependencia.getSede().getId() + "'");
                        correoActual = cargarPlantilla(85);

                        /*
                         * listaCorreoEncargado = this.servicioGeneral
                         * .obtenerObjetos(
                         * "FROM Parametro WHERE nombre = 'R_MOVILIDAD'    AND DESCRIPCION= '"
                         * + dependenciaAux.getFacultad().getId() + "'");
                         */

                        String consult = "select i from InvestigadorInterno i, " + " PersonaRol pr "
                                + " where i.id.documento= pr.documento " + " and i.id.tipoDocumento= pr.tipoDocumento "
                                + " and pr.nombre = 'MF' and i.dependencia.id = '" + dependencia.getId() + "' "
                                + " and i.dependencia.esFacultad = 'Y' and (pr.fechaFinRol IS NULL OR pr.fechaFinRol >= CURRENT_DATE) ";
                        listaCorreoEncargado = servicioGeneral.obtenerObjetos(consult);
                    }

                    String correoEnvio = Correo.CORREO_HERMES;
                    Persona personaActualAux2 = new Persona();

                    if (listaCorreoEncargado != null && listaCorreoEncargado.size() > 0) {
                        int numCoord = listaCorreoEncargado.size();

                        for (int i = 0; i < numCoord; i++) {

                            InvestigadorInterno paActual = (InvestigadorInterno) listaCorreoEncargado.get(i);
                            String numeroDocumento = "0";
                            numeroDocumento = paActual.getId().getDocumento();
                            IdPersona id = new IdPersona();
                            id.setDocumento(numeroDocumento);
                            id.setTipoDocumento(paActual.getId().getTipoDocumento());
                            personaActualAux2 = servicioPersona.obtenerPersona(id);

                            if (personaActualAux2.getEmail() != null && !personaActualAux2.getEmail().equals("")) {

                                correoEnvio = personaActualAux2.getEmail();
                            } else {
                                correoActual = cargarPlantilla(87);
                            }

                            String investigador = "";
                            investigador = personaActualAux2.getNombre1() + " " + personaActualAux2.getApellido1() + " "
                                    + personaActualAux2.getApellido2();
                            cuerpoCorreo = correoActual.getCuerpo().replaceAll("<<INVESTIGADOR>>", investigador);
                            cuerpoCorreo = correoActual.getCuerpo().replaceAll("<<IDMOVILIDAD>>",
                                    movArt.getId().toString());
                            cuerpoCorreo = correoActual.getCuerpo().replaceAll("<<TIPO>>",
                                    movArt.getTipoMovilidad().getNombre());

                            correo = new Correo();
                            correo.setOrigen(Correo.CORREO_HERMES);
                            dirCorreo = correoEnvio;
                            correo.adicionarDireccion(dirCorreo);
                            correo.adicionarCopiaOculta(dirCorreo);
                            correo.setAsunto(correoActual.getAsunto());
                            correo.setCuerpo(cuerpoCorreo);
                            servicioCorreo.enviarCorreo(correo);
                        }

                    } else {
                        correoActual = cargarPlantilla(87);
                        // String investigador = "";
                        // investigador = personaActualAux2.getNombre1() + " " +
                        // personaActualAux2.getApellido1() + " "
                        // + personaActualAux2.getApellido2();
                        // cuerpoCorreo =
                        // correoActual.getCuerpo().replaceAll("<<INVESTIGADOR>>",
                        // investigador);
                        cuerpoCorreo = correoActual.getCuerpo().replaceAll("<<IDMOVILIDAD>>",
                                movArt.getId().toString());
                        cuerpoCorreo = correoActual.getCuerpo().replaceAll("<<TIPO>>",
                                movArt.getTipoMovilidad().getNombre());

                        correo = new Correo();
                        correo.setOrigen(Correo.CORREO_HERMES);
                        dirCorreo = correoEnvio;
                        correo.adicionarDireccion(dirCorreo);
                        correo.adicionarCopiaOculta(dirCorreo);
                        correo.setAsunto(correoActual.getAsunto());
                        correo.setCuerpo(cuerpoCorreo);
                        servicioCorreo.enviarCorreo(correo);
                    }

                    sesion.removeAttribute("ManejadorEditarFacultadMovilidadDocArt");
                    sesion.removeAttribute("ManejadorEditarMovilidadDocArt");

                } else {
                    if (movArt.getConfirmarResidencia() != null && movArt.getConfirmarResidencia().equals("NO")) {
                        this.movArt.setEstado("I");
                        this.movArt.setEtapa("8");
                        servicioGeneral.guardarObjeto(this.movArt);
                        // this.limpiar();
                        ocultarPaneles(0);
                        // this.errores[0] =
                        // "Para confirmar la solicitud de la residencia, por
                        // favor ingrese por el menú\n Investigador,
                        // Movilidades\n, Actualizar Residencia de Artes.";
                        // this.panelRenderError[0] = true;
                    }
                }

            }

            if (estado >= 8) {
                if (estado == 8) {

                    ocultarPaneles(9);
                    estado = 9;
                    this.movArt.setEtapa("8");
                }
                Set archivoSet = new HashSet();
                for (int i = 0; i < listaArchivosObligatoriosSel.size(); i++) {
                    ArchivoMovilidad archivo = new ArchivoMovilidad();
                    archivo = (ArchivoMovilidad) listaArchivosObligatoriosSel.get(i);
                    archivo.setMovilidad(String.valueOf(this.movArt.getId()));

                    archivoSet.add(archivo);
                }
                this.movArt.setArchivos(archivoSet);
                servicioGeneral.guardarObjeto(this.movArt);

                try {
                    servicioGeneral.eliminar("DELETE HER_ARCHIVO_MOVILIDAD WHERE MOV_ID IS NULL");
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

            if (estado >= 7) {
                if (estado == 7) {
                    ocultarPaneles(8);
                    estado = 8;
                    this.movArt.setEtapa("7");
                }
                servicioGeneral.guardarObjeto(this.movArt);
            }

            if (estado >= 6) {
                if (estado == 6) {
                    ocultarPaneles(7);
                    estado = 7;
                    this.movArt.setEtapa("6");
                }
                servicioGeneral.guardarObjeto(this.movArt);
            }

            if (estado >= 5) {
                if (estado == 5) {
                    ocultarPaneles(6);
                    estado = 6;
                    this.movArt.setEtapa("5");
                }
                servicioGeneral.guardarObjeto(this.movArt);
            }

            if (estado >= 4) {
                if (estado == 4) {
                    ocultarPaneles(5);
                    estado = 5;
                    this.movArt.setEtapa("4");
                }
                Set actividadesSet = new HashSet();

                for (int i = 0; i < listaActividades.size(); i++) {
                    ActividadMovilidad actividad = new ActividadMovilidad();
                    actividad = (ActividadMovilidad) listaActividades.get(i);
                    actividad.setMovilidad(String.valueOf(this.movArt.getId()));
                    actividadesSet.add(actividad);

                }

                this.movArt.setActividades(actividadesSet);
                servicioGeneral.guardarObjeto(this.movArt);

                try {
                    servicioGeneral.eliminar("DELETE HER_ACTIVIDAD_MOVILIDAD WHERE MOV_ID IS NULL");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (estado >= 3) {
                if (estado == 3) {
                    ocultarPaneles(4);
                    estado = 4;
                    this.movArt.setEtapa("3");
                }
                servicioGeneral.guardarObjeto(this.movArt);
            }

            if (estado >= 2) {

                if (estado == 2) {
                    ocultarPaneles(3);
                    estado = 3;
                    this.movArt.setEtapa("2");
                }
                servicioGeneral.guardarObjeto(this.movArt);
            }
            if (estado >= 1) {

                if (estado == 1) {
                    ocultarPaneles(2);
                    estado = 2;
                    this.movArt.setEtapa("1");
                    this.movArt.setEstado("I");

                }
                List listaTipoMovilidad = servicioGeneral.obtenerListaObjetos("TipoMovilidad where id ='C1'");
                TipoMovilidad tipoMovilidad = (TipoMovilidad) listaTipoMovilidad.get(0);
                this.movArt.setTipoMovilidad(tipoMovilidad);

                servicioGeneral.guardarObjeto(this.movArt);
                if (this.movArt.getConfirmarResidencia() != null) {
                    if (this.movArt.getConfirmarResidencia().equals("NO")) {
                        this.limpiar();

                        this.errores[0] = "Para continuar ingresando o actualizar la información, por favor ingrese por el menú\n Investigador, Movilidades\n, Actualizar Residencia de Artes.";
                        this.panelRenderError[0] = true;
                        sesion.removeAttribute("ManejadorEditarFacultadMovilidadDocArt");
                        // sesion.removeAttribute("ManejadorEditarMovilidadDocArt");
                        sesion.removeAttribute("ManejadorConsultarMovilidadDocArt");

                    } else {
                        if (this.movArt.getConfirmarResidencia().equals("SI")) {
                            mensajeInfo(
                                    "Movilidad creada satisfactoriamente, con el número " + this.movArt.getId() + ".");
                            this.limpiar();
                            this.panelRenderError[0] = true;
                            cargarLista();
                            sesion.removeAttribute("ManejadorEditarFacultadMovilidadDocArt");
                            // sesion.removeAttribute("ManejadorEditarMovilidadDocArt");
                            sesion.removeAttribute("ManejadorConsultarMovilidadDocArt");
                        }
                    }
                }
            }

        }
    }

    public void guardar2() {
        if (validacion(estado) && validarPresupuesto()) {
            if (estado >= 9) {

                // if (estado == 9) {
                this.movArt.setNombreProyecto(this.movArt.getNombreProyecto().toUpperCase());
                this.movArt.setCiudad(this.movArt.getCiudad().toUpperCase());
                this.movArt.setInstitucion(this.movArt.getInstitucion().toUpperCase());

                if (movArt.getConfirmarResidencia().equals("SI")) {
                    estado = 10;
                    this.movArt.setEstado("P");
                    this.movArt.setEtapa("9");
                    Calendar actual = Calendar.getInstance();
                    Date date = actual.getTime();
                    this.movArt.setFechasolicitud(date);
                    servicioGeneral.guardarObjeto(this.movArt);

                    Dependencia dependencia;
                    dependencia = servicioDependencia.obtenerDependencia(this.movArt.getPersonaInv().getId());
                    dependencia.getSede().getId();

                    List listaParametroSede = new ArrayList();

                    if (dependencia != null && dependencia.getSede() != null
                            && (dependencia.getSede().getId().compareTo(new Long("2")) == 0)) {
                        listaParametroSede = this.servicioGeneral.obtenerObjetos("FROM Parametro WHERE id = 62");
                    }
                    if (dependencia != null && dependencia.getSede() != null
                            && (dependencia.getSede().getId().compareTo(new Long("3")) == 0)) {
                        listaParametroSede = this.servicioGeneral.obtenerObjetos("FROM Parametro WHERE id = 63");
                    }
                    if (dependencia != null && dependencia.getSede() != null
                            && (dependencia.getSede().getId().compareTo(new Long("4")) == 0)) {
                        listaParametroSede = this.servicioGeneral.obtenerObjetos("FROM Parametro WHERE id = 64");
                    }
                    if (dependencia != null && dependencia.getSede() != null
                            && (dependencia.getSede().getId().compareTo(new Long("5")) == 0)) {
                        listaParametroSede = this.servicioGeneral.obtenerObjetos("FROM Parametro WHERE id = 65");
                    }
                    if (dependencia != null && dependencia.getSede() != null
                            && (dependencia.getSede().getId().compareTo(new Long("6")) == 0)) {
                        listaParametroSede = this.servicioGeneral.obtenerObjetos("FROM Parametro WHERE id = 66");
                    }
                    if (dependencia != null && dependencia.getSede() != null
                            && (dependencia.getSede().getId().compareTo(new Long("7")) == 0)) {
                        listaParametroSede = this.servicioGeneral.obtenerObjetos("FROM Parametro WHERE id = 67");
                    }
                    if (dependencia != null && dependencia.getSede() != null
                            && (dependencia.getSede().getId().compareTo(new Long("8")) == 0)) {
                        listaParametroSede = this.servicioGeneral.obtenerObjetos("FROM Parametro WHERE id = 68");
                    }

                    String correoFacultad = "";

                    if (listaParametroSede != null && listaParametroSede.size() > 0) {
                        Parametro par = (Parametro) listaParametroSede.get(0);
                        correoFacultad = par.getValor();
                    }

                    CorreoPlantilla correoActual = new CorreoPlantilla();
                    String cuerpoCorreo = "";
                    correoActual = cargarPlantilla(80);
                    cuerpoCorreo = editarCorreo(this.movArt.getPersonaInv(), String.valueOf(this.movArt.getId()),
                            this.movArt.getTipoMovilidad().getNombre(), correoActual.getCuerpo());
                    Correo correo = new Correo();
                    correo.setOrigen(Correo.CORREO_HERMES);
                    String dirCorreo = this.movArt.getPersonaInv().getEmail();
                    correo.adicionarDireccion(dirCorreo);
                    correo.adicionarDireccion(correoFacultad);
                    correo.adicionarCopiaOculta(new String(this.movArt.getPersonaInv().getEmail()));
                    correo.adicionarCopiaOculta(correoFacultad);
                    correo.setAsunto(correoActual.getAsunto());
                    correo.setCuerpo(cuerpoCorreo);
                    servicioCorreo.enviarCorreo(correo);

                    // this.limpiar();
                    ocultarPaneles(0);
                    // this.errores[0] = "Movilidad creada satisfactoriamente.";
                    // this.panelRenderError[0] = true;

                    // sesion.removeAttribute("ManejadorEditarFacultadMovilidadDocArt");
                    // sesion.removeAttribute("ManejadorEditarMovilidadDocArt");
                } else {
                    if (movArt.getConfirmarResidencia().equals("NO")) {
                        this.movArt.setEstado("I");
                        this.movArt.setEtapa("8");
                        servicioGeneral.guardarObjeto(this.movArt);
                        // this.limpiar();
                        ocultarPaneles(0);
                        // this.errores[0] =
                        // "Para confirmar la solicitud de la residencia, por
                        // favor ingrese por el menú\n Investigador,
                        // Movilidades\n, Actualizar Residencia de Artes.";
                        // this.panelRenderError[0] = true;

                    }
                }

            }

            if (estado >= 8) {
                if (estado == 8) {

                    ocultarPaneles(0);
                    estado = 9;
                    this.movArt.setEtapa("8");
                }
                Set archivoSet = new HashSet();
                for (int i = 0; i < listaArchivosObligatoriosSel.size(); i++) {
                    ArchivoMovilidad archivo = new ArchivoMovilidad();
                    archivo = (ArchivoMovilidad) listaArchivosObligatoriosSel.get(i);
                    archivo.setMovilidad(String.valueOf(this.movArt.getId()));
                    archivoSet.add(archivo);
                }
                this.movArt.setArchivos(archivoSet);
                servicioGeneral.guardarObjeto(this.movArt);

                try {
                    servicioGeneral.eliminar("DELETE HER_ARCHIVO_MOVILIDAD WHERE MOV_ID IS NULL");
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

            if (estado >= 7) {
                if (estado == 7) {
                    ocultarPaneles(0);
                    estado = 8;
                    this.movArt.setEtapa("7");
                }
                servicioGeneral.guardarObjeto(this.movArt);
            }

            if (estado >= 6) {
                if (estado == 6) {
                    ocultarPaneles(0);
                    estado = 7;
                    this.movArt.setEtapa("6");
                }
                servicioGeneral.guardarObjeto(this.movArt);
            }

            if (estado >= 5) {
                if (estado == 5) {
                    ocultarPaneles(0);
                    estado = 6;
                    this.movArt.setEtapa("5");
                }
                servicioGeneral.guardarObjeto(this.movArt);
            }

            if (estado >= 4) {
                if (estado == 4) {
                    ocultarPaneles(0);
                    estado = 5;
                    this.movArt.setEtapa("4");
                }
                Set actividadesSet = new HashSet();

                for (int i = 0; i < listaActividades.size(); i++) {
                    ActividadMovilidad actividad = new ActividadMovilidad();
                    actividad = (ActividadMovilidad) listaActividades.get(i);
                    actividad.setMovilidad(String.valueOf(this.movArt.getId()));
                    actividadesSet.add(actividad);

                }

                this.movArt.setActividades(actividadesSet);
                servicioGeneral.guardarObjeto(this.movArt);

                try {
                    servicioGeneral.eliminar("DELETE HER_ACTIVIDAD_MOVILIDAD WHERE MOV_ID IS NULL");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (estado >= 3) {
                if (estado == 3) {
                    ocultarPaneles(0);
                    estado = 4;
                    this.movArt.setEtapa("3");
                }
                servicioGeneral.guardarObjeto(this.movArt);
            }

            if (estado >= 2) {

                if (estado == 2) {
                    ocultarPaneles(0);
                    estado = 3;
                    this.movArt.setEtapa("2");
                }
                servicioGeneral.guardarObjeto(this.movArt);
            }
            if (estado >= 1) {

                if (estado == 1) {
                    ocultarPaneles(0);
                    estado = 2;
                    this.movArt.setEtapa("1");
                    this.movArt.setEstado("I");
                }
                List listaTipoMovilidad = servicioGeneral.obtenerListaObjetos("TipoMovilidad where id ='C1'");
                TipoMovilidad tipoMovilidad = (TipoMovilidad) listaTipoMovilidad.get(0);
                this.movArt.setTipoMovilidad(tipoMovilidad);

                servicioGeneral.guardarObjeto(this.movArt);
                if (this.movArt.getConfirmarResidencia() != null) {
                    if (this.movArt.getConfirmarResidencia().equals("NO")) {
                        this.limpiar();
                        this.errores[0] = "Para continuar ingresando o actualizar la información, por favor ingrese por el menú\n Investigador, Movilidades\n, Actualizar Residencia de Artes.";
                        this.panelRenderError[0] = true;
                        sesion.removeAttribute("ManejadorEditarFacultadMovilidadDocArt");
                        // sesion.removeAttribute("ManejadorEditarMovilidadDocArt");
                        sesion.removeAttribute("ManejadorConsultarMovilidadDocArt");
                    } else {
                        if (this.movArt.getConfirmarResidencia().equals("SI")) {
                            mensajeInfo(
                                    "Movilidad creada satisfactoriamente, con el número " + this.movArt.getId() + ".");
                            this.limpiar();
                            this.panelRenderError[0] = true;
                            cargarLista();
                            sesion.removeAttribute("ManejadorEditarFacultadMovilidadDocArt");
                            // sesion.removeAttribute("ManejadorEditarMovilidadDocArt");
                            sesion.removeAttribute("ManejadorConsultarMovilidadDocArt");
                        }
                    }
                } else {
                    this.limpiar();
                    this.errores[0] = "Para continuar ingresando o actualizar la información, por favor ingrese por el menú\n Investigador, Movilidades\n, Actualizar Residencia de Artes.";
                    this.panelRenderError[0] = true;
                    sesion.removeAttribute("ManejadorEditarFacultadMovilidadDocArt");
                    // sesion.removeAttribute("ManejadorEditarMovilidadDocArt");
                    sesion.removeAttribute("ManejadorConsultarMovilidadDocArt");
                }
            }

        }
    }

    public String editarCorreo(Persona personaAux, String id, String tipo, String cuerpo) {
        String correo = cuerpo;
        try {
            String investigador = "";
            investigador = personaAux.getNombre1() + " " + personaAux.getApellido1() + " " + personaAux.getApellido2();
            correo = correo.replaceAll("<<INVESTIGADOR>>", investigador);
            correo = correo.replaceAll("<<IDMOVILIDAD>>", id);
            correo = correo.replaceAll("<<TIPO>>", tipo);

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return correo;
    }

    public CorreoPlantilla cargarPlantilla(int cod_id) {

        CorreoPlantilla correoActualAux = new CorreoPlantilla();
        CorreoPlantilla a = new CorreoPlantilla();

        List lista = servicioGeneral.obtenerObjetos("select c from CorreoPlantilla c where c.id='" + cod_id + "'");
        if (lista != null && lista.size() > 0) {
            correoActualAux = (CorreoPlantilla) lista.get(0);
        }

        return correoActualAux;
    }

    // CREADO Y PATENTADO POR ING. CANTOR
    public void ocultarPaneles(int nivel) {
        for (int i = 0; i < 10; i++) {
            if (i < nivel) {
                panelRender[i] = true;
                ;
            } else {
                panelRender[i] = false;
                ;
            }

        }
    }

    public void validarFechaInicial(ValueChangeEvent event) {

        Date fechaInicial = (Date) event.getNewValue();
        Calendar cal1 = Calendar.getInstance();
        Date fechaSinHora = new Date();
        System.out.print(" fechaSinHora: " + fechaSinHora);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            fechaSinHora = sdf.parse(sdf.format(new Date()));
            System.out.print(" fechaSinHora: " + fechaSinHora);
        } catch (Exception e) {

        }
        cal1.setTime(fechaSinHora);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(fechaInicial);
        cal2.add(Calendar.DATE, -30);
        System.out.print(" cal1: " + cal1.getTime());
        System.out.print(" cal2: " + cal2.getTime());

        if (cal2.before(cal1)) {
            ocultarPaneles(3);
            this.errores[14] = "La fecha del viaje debe ser superior que la fecha actual en 30 días calendario.";
            this.panelRenderError[14] = true;
        } else {
            this.errores[14] = "";
            this.panelRenderError[14] = false;

            fechaMinimaFinalizacion = (Date) event.getNewValue();
            Calendar fechaInicial2 = Calendar.getInstance();
            fechaInicial2.setTime(fechaMinimaFinalizacion);
            fechaInicial2.add(Calendar.DATE, 30);
            fechaMaximaFinalizacion = fechaInicial2.getTime();
            System.out.print(" fechaMinimaFinalizacion: " + fechaMinimaFinalizacion);
            System.out.print(" fechaMaximaFinalizacion: " + fechaMaximaFinalizacion);
        }

        if (this.movArt.getFechafinal() != null) {
            Calendar cal3 = Calendar.getInstance();
            Calendar cal4 = Calendar.getInstance();
            cal3.setTime(fechaInicial);
            cal4.setTime(this.movArt.getFechafinal());
            cal4.add(Calendar.DATE, -30);
            if (this.movArt.getFechafinal().before(fechaInicial) || !cal3.after(cal4)) {

                this.errores[15] = "La fecha de finalización del viaje no es válida. La fecha debe ser superior a la fecha de incio en máximo 30 días.";
                this.panelRenderError[15] = true;

            } else {
                this.errores[15] = "";
                this.panelRenderError[15] = false;

                // ocultarPaneles(6);

            }
        }
    }

    public void calcularFechaMinimaInicio() {
        Calendar fechaActual = Calendar.getInstance();
        fechaActual.add(Calendar.DATE, 30);
        fechaMinimaInicio = fechaActual.getTime();
        System.out.print("fechaMinimaInicio: " + fechaMinimaInicio);
    }

    public void validarFechaLlegada(ValueChangeEvent event) {
        Date fechaLlegada = (Date) event.getNewValue();

        if (this.movArt.getFechainicial() != null && this.panelRenderError[14] == false) {
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(this.movArt.getFechainicial());
            cal2.setTime(fechaLlegada);
            cal2.add(Calendar.DATE, -30);
            if (fechaLlegada.before(this.movArt.getFechainicial()) || !cal1.after(cal2)) {
                this.errores[15] = "La fecha de finalización del viaje no es válida. La fecha debe ser superior a la fecha de incio en máximo 30 días.";
                this.panelRenderError[15] = true;
                ;
            } else {
                this.errores[15] = "";
                this.panelRenderError[15] = false;

                // ocultarPaneles(6);

            }
        } else {
            this.errores[15] = "Debe diligenciar la fecha de inicio del viaje válida.";
            this.panelRenderError[15] = true;
            ;
        }

    }

    private void cargarPaises() {
        List listaPaises = servicioGeneral.obtenerListaObjetosOrdenadosAsc(new Pais(), "nombre");
        paises = new Vector();
        nacionalidad = new Vector();

        for (Iterator it = listaPaises.iterator(); it.hasNext();) {
            Pais p = (Pais) it.next();
            SelectItem s = new SelectItem(p.getId(), p.getNombre());
            paises.add(s);

        }

        for (Iterator it = listaPaises.iterator(); it.hasNext();) {
            Pais p = (Pais) it.next();
            SelectItem s1 = new SelectItem(p.getId(), p.getNombre());
            nacionalidad.add(s1);
        }
    }

    private void cargarTiposDocumento() {
        List listaTipoDocumento = servicioGeneral.obtenerListaObjetos("TipoDocumento");
        tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
        for (int i = 0; i < listaTipoDocumento.size(); i++) {
            TipoDocumento td = (TipoDocumento) listaTipoDocumento.get(i);
            tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
        }
        tipoDocumento = (TipoDocumento) listaTipoDocumento.get(0);
    }

    private void cargarValoresIniciales() {
        errores = new String[60];
        panelRender = new boolean[10];
        panelRenderError = new boolean[60];
        personaActual = (Persona) sesion.getAttribute("persona");
        cargarTiposDocumento();
        cargarPaises();
        cargarContinentes();
        ocultarPaneles(0);
        cargarTiposDocumentos();

        listaActividades = new ArrayList();
        listaArchivos = new ArrayList();
        listaArchivosObligatoriosSel = new ArrayList();

        areaArte = new Vector();
        /*
         * areaArte.add(new SelectItem("Animación (Bogotá)",
         * "Animación (Bogotá)")); areaArte.add(new SelectItem(
         * "Diseño de multimedia (Bogotá)", "Diseño de multimedia (Bogotá)"));
         * areaArte.add(new SelectItem("Fotografía (Bogotá)",
         * "Fotografía (Bogotá)")); areaArte.add(newSelectItem(
         * "Plástica Contemporánea (Bogotá)", "Plástica Contemporánea (Bogotá)"
         * )); areaArte.add(new SelectItem("Performance","Performance"));
         * areaArte.add(new SelectItem( "Teatro y Artes Vivas (Bogotá)",
         * "Teatro y Artes Vivas (Bogotá)")); areaArte.add(newSelectItem(
         * "Museología y Patrimonio (Bogotá)",
         * "Museología y Patrimonio (Bogotá)")); areaArte.add(new SelectItem(
         * "Sonido (Bogotá)", "Sonido (Bogotá)")); areaArte.add(new SelectItem(
         * "Música (Bogotá)","Música (Bogotá)")); areaArte.add(new SelectItem(
         * "Historia/Teoría del arte/Curaduría (Bogotá)",
         * "Historia/Teoría del arte/Curaduría (Bogotá)")); areaArte.add(new
         * SelectItem("Sostenibilidad/Urbanismo/Arquitectura (Bogotá)",
         * "Sostenibilidad/Urbanismo/Arquitectura (Bogotá)")); areaArte.add(new
         * SelectItem("Gestión cultural y comunicativa (Manizales)",
         * "Gestión cultural y comunicativa (Manizales)")); areaArte.add(new
         * SelectItem("Estudios ambientales (Manizales)",
         * "Estudios ambientales (Manizales)")); areaArte.add(new SelectItem(
         * "Diseño multimedia (Medellín)", "Diseño multimedia (Medellín)"));
         * areaArte.add(new SelectItem("Estética (Medellín)",
         * "Estética (Medellín)")); areaArte.add(new SelectItem(
         * "Plástica contemporánea (Medellín)",
         * "Plástica contemporánea (Medellín)")); areaArte.add(new SelectItem(
         * "Arquitectura (Medellín)", "Arquitectura (Medellín)"));
         */
        /*
         * areaArte.add(new SelectItem("Animación", "Animación"));
         * areaArte.add(new SelectItem("Multimedia", "Multimedia"));
         * areaArte.add(new SelectItem("Fotografía", "Fotografía"));
         * areaArte.add(new SelectItem("Plástica Contemporánea",
         * "Plástica Contemporánea")); areaArte.add(new SelectItem(
         * "Performance/ Teatro /Artes Vivas",
         * "Performance/ Teatro /Artes Vivas")); areaArte.add(new
         * SelectItem("Sonido", "Sonido")); areaArte.add(new SelectItem(
         * "Historia/Teoría del arte/Curaduría",
         * "Historia/Teoría del arte/Curaduría")); areaArte.add(new
         * SelectItem("Arquitectura", "Arquitectura")); areaArte.add(new
         * SelectItem("Diseño", "Diseño")); areaArte.add(new SelectItem(
         * "Nuevos Medios", "Nuevos Medios")); areaArte.add(new
         * SelectItem("Otro", "Otro"));
         */

        areaArte.add(new SelectItem("Fotografía", "Fotografía"));
        areaArte.add(new SelectItem("Animación", "Animación"));
        areaArte.add(new SelectItem("Video", "Video"));
        areaArte.add(new SelectItem("Multimedia", "Multimedia"));
        areaArte.add(new SelectItem("Museología", "Museología"));
        areaArte.add(new SelectItem("Plástica Contemporánea", "Plástica Contemporánea"));
        areaArte.add(new SelectItem("Artes vivas/performance", "Artes vivas/performance"));
        areaArte.add(new SelectItem("Escritura", "Escritura"));
        areaArte.add(new SelectItem("Música/Sonido", "Música/Sonido"));
        areaArte.add(new SelectItem("Arquitectura", "Arquitectura"));
        areaArte.add(new SelectItem("Sostenibilidad", "Sostenibilidad"));
        areaArte.add(new SelectItem("Urbanismo", "Urbanismo"));
        areaArte.add(new SelectItem("Diseño y curaduría", "Diseño y curaduría"));

        seleccionReceptora = new Vector();
        seleccionReceptora.add(new SelectItem("Convocatoria Abierta", "Convocatoria Abierta"));
        seleccionReceptora.add(new SelectItem("Convocatoria con Cortes", "Convocatoria con Cortes"));
        seleccionReceptora
                .add(new SelectItem("Presentación Directa del Proyecto", "Presentación Directa del Proyecto"));
        seleccionReceptora.add(new SelectItem("Contacto Académico", "Contacto Académico"));

        tipoInstitucion = new Vector();
        tipoInstitucion.add(new SelectItem("Programa de Residencia", "Programa de Residencia"));
        tipoInstitucion.add(new SelectItem("Universidad", "Universidad"));
        tipoInstitucion.add(new SelectItem("Institución Académica", "Institución Académica"));
        tipoInstitucion.add(new SelectItem("Fundación Cultural", "Fundación Cultural"));

        movilidadAnterior = new Vector();
        movilidadAnterior.add(new SelectItem("NO", "NO"));
        movilidadAnterior.add(new SelectItem("SI", "SI"));

        confirmarResidencia = new Vector();
        confirmarResidencia.add(new SelectItem("NO", "NO"));
        confirmarResidencia.add(new SelectItem("SI", "SI"));

        avalFacultad = new Vector();
        avalFacultad.add(new SelectItem("", "NO REGISTRADO"));
        avalFacultad.add(new SelectItem("NO", "NO"));
        avalFacultad.add(new SelectItem("SI", "SI"));

        avalSede = new Vector();
        avalSede.add(new SelectItem("", "NO REGISTRADO"));
        avalSede.add(new SelectItem("NO", "NO"));
        avalSede.add(new SelectItem("SI", "SI"));

        comoEntero = new Vector();
        comoEntero.add(new SelectItem("Página Web", "Página Web"));
        comoEntero.add(new SelectItem("Red de Residencias", "Red de Residencias"));
        comoEntero.add(new SelectItem("Institución Académica", "Institución Académica"));
        comoEntero.add(new SelectItem("Revista", "Revista"));
        comoEntero.add(new SelectItem("Museo", "Museo"));
        comoEntero.add(new SelectItem("Galería", "Galería"));
        comoEntero.add(new SelectItem("Referencia Personal", "Referencia Personal"));

    }

    public List getListaActividades() {
        return listaActividades;
    }

    public MovilidadDocentesArtes getMovArt() {
        return movArt;
    }

    public void setMovArt(MovilidadDocentesArtes movArt) {
        this.movArt = movArt;
    }

    public String[] getErrores() {
        return errores;
    }

    public void setErrores(String[] errores) {
        this.errores = errores;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public SelectItem[] getTipoDocumentoItem() {
        return tipoDocumentoItem;
    }

    public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
        this.tipoDocumentoItem = tipoDocumentoItem;
    }

    public HtmlDataTable getTablaArchivos() {
        return tablaArchivos;
    }

    public void setTablaArchivos(HtmlDataTable tablaArchivos) {
        this.tablaArchivos = tablaArchivos;
    }

    public List getPaises() {
        return paises;
    }

    public void setPaises(List paises) {
        this.paises = paises;
    }

    public List getContinentes() {
        return continentes;
    }

    public void setContinentes(List continentes) {
        this.continentes = continentes;
    }

    public List getListaArchivos() {
        return listaArchivos;
    }

    public void setListaArchivos(List listaArchivos) {
        this.listaArchivos = listaArchivos;
    }

    public boolean[] getPanelRender() {
        return panelRender;
    }

    public void setPanelRender(boolean[] panelRender) {
        this.panelRender = panelRender;
    }

    public List getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(List nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public boolean[] getPanelRenderError() {
        return panelRenderError;
    }

    public void setPanelRenderError(boolean[] panelRenderError) {
        this.panelRenderError = panelRenderError;
    }

    public List getAreaArte() {
        return areaArte;
    }

    public void setAreaArte(List areaArte) {
        this.areaArte = areaArte;
    }

    public List getSeleccionReceptora() {
        return seleccionReceptora;
    }

    public void setSeleccionReceptora(List seleccionReceptora) {
        this.seleccionReceptora = seleccionReceptora;
    }

    public List getTipoInstitucion() {
        return tipoInstitucion;
    }

    public void setTipoInstitucion(List tipoInstitucion) {
        this.tipoInstitucion = tipoInstitucion;
    }

    public List getMovilidadAnterior() {
        return movilidadAnterior;
    }

    public void setMovilidadAnterior(List movilidadAnterior) {
        this.movilidadAnterior = movilidadAnterior;
    }

    public List getConfirmarResidencia() {
        return confirmarResidencia;
    }

    public void setConfirmarResidencia(List confirmarResidencia) {
        this.confirmarResidencia = confirmarResidencia;
    }

    public List getComoEntero() {
        return comoEntero;
    }

    public void setComoEntero(List comoEntero) {
        this.comoEntero = comoEntero;
    }

    public void setListaActividades(List listaActividades) {
        this.listaActividades = listaActividades;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getFacultadDocente() {
        return facultadDocente;
    }

    public void setFacultadDocente(String facultadDocente) {
        this.facultadDocente = facultadDocente;
    }

    public String getDepartamentoDocente() {
        return departamentoDocente;
    }

    public void setDepartamentoDocente(String departamentoDocente) {
        this.departamentoDocente = departamentoDocente;
    }

    public String getNombreDocente() {
        return nombreDocente;
    }

    public void setNombreDocente(String nombreDocente) {
        this.nombreDocente = nombreDocente;
    }

    public String getDocumentoDocente() {
        return documentoDocente;
    }

    public void setDocumentoDocente(String documentoDocente) {
        this.documentoDocente = documentoDocente;
    }

    public String getDescripcionActividad() {
        return descripcionActividad;
    }

    public void setDescripcionActividad(String descripcionActividad) {
        this.descripcionActividad = descripcionActividad;
    }

    public Date getFechaActividad() {
        return fechaActividad;
    }

    public void setFechaActividad(Date fechaActividad) {
        this.fechaActividad = fechaActividad;
    }

    public String getDuracionActividad() {
        return duracionActividad;
    }

    public void setDuracionActividad(String duracionActividad) {
        this.duracionActividad = duracionActividad;
    }

    public SelectItem[] getTipoDocumentoSelItem() {
        return tipoDocumentoSelItem;
    }

    public void setTipoDocumentoSelItem(SelectItem[] tipoDocumentoSelItem) {
        this.tipoDocumentoSelItem = tipoDocumentoSelItem;
    }

    public String getTipoDocumentoSel() {
        return tipoDocumentoSel;
    }

    public void setTipoDocumentoSel(String tipoDocumentoSel) {
        this.tipoDocumentoSel = tipoDocumentoSel;
    }

    public UploadedFile getArchivoObligatorio() {
        return archivoObligatorio;
    }

    public void setArchivoObligatorio(UploadedFile archivoObligatorio) {
        this.archivoObligatorio = archivoObligatorio;
    }

    public List getListaArchivosObligatoriosSel() {
        return listaArchivosObligatoriosSel;
    }

    public void setListaArchivosObligatoriosSel(List listaArchivosObligatoriosSel) {
        this.listaArchivosObligatoriosSel = listaArchivosObligatoriosSel;
    }

    public SelectItem[] getMovilidadItem() {
        return movilidadItem;
    }

    public void setMovilidadItem(SelectItem[] movilidadItem) {
        this.movilidadItem = movilidadItem;
    }

    public Long getCodigoMovilidad() {
        return codigoMovilidad;
    }

    public void setCodigoMovilidad(Long codigoMovilidad) {
        this.codigoMovilidad = codigoMovilidad;
    }

    public List getAvalFacultad() {
        return avalFacultad;
    }

    public void setAvalFacultad(List avalFacultad) {
        this.avalFacultad = avalFacultad;
    }

    public List getAvalSede() {
        return avalSede;
    }

    public void setAvalSede(List avalSede) {
        this.avalSede = avalSede;
    }

    /**
     * @return the fechaMinimaFinalizacion
     */
    public Date getFechaMinimaFinalizacion() {
        return fechaMinimaFinalizacion;
    }

    /**
     * @return the fechaMaximaFinalizacion
     */
    public Date getFechaMaximaFinalizacion() {
        return fechaMaximaFinalizacion;
    }

    /**
     * @return the fechaMinimaInicio
     */
    public Date getFechaMinimaInicio() {
        return fechaMinimaInicio;
    }

    /**
     * @param actividadMovilidadSeleccionada
     *            the actividadMovilidadSeleccionada to set
     */
    public void setActividadMovilidadSeleccionada(ActividadMovilidad actividadMovilidadSeleccionada) {
        this.actividadMovilidadSeleccionada = actividadMovilidadSeleccionada;
    }

    /**
     * @return the puedeSubirArchivos
     */
    public boolean isPuedeSubirArchivos() {
        return puedeSubirArchivos;
    }

    /**
     * @param archivoMovilidadSeleccionado
     *            the archivoMovilidadSeleccionado to set
     */
    public void setArchivoMovilidadSeleccionado(ArchivoMovilidad archivoMovilidadSeleccionado) {
        this.archivoMovilidadSeleccionado = archivoMovilidadSeleccionado;
    }

    public ArchivoMovilidad getDocumentoSeleccionado() {
        return documentoSeleccionado;
    }

    public void setDocumentoSeleccionado(ArchivoMovilidad documentoSeleccionado) {
        this.documentoSeleccionado = documentoSeleccionado;
    }

}
