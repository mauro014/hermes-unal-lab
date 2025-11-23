package co.edu.unal.hermes.vista.movilidad;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.model.DefaultUploadedFile;

import co.edu.unal.hermes.modelo.ArchivoMovilidad;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.MovilidadArchivo;
import co.edu.unal.hermes.modelo.MovilidadDocentesExterior;
import co.edu.unal.hermes.modelo.MovilidadDocentesProducto;
import co.edu.unal.hermes.modelo.MovilidadSeguimientoCooproducto;
import co.edu.unal.hermes.modelo.MovilidadVisitanteExterior;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.ProductoTipo;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.utils.Navegacion;

public class ManejadorCrearEditarSeguimientoMovilidadDocente extends ManejadorBaseMovilidad {

    private static final long serialVersionUID = 2902435233928323623L;
    private MovilidadDocentesExterior mde;
    private boolean consulta;
    
    private List<Object[]> listaCooproductos;
    private List<Object[]> listaCooproductosEliminados;
    private List<ProductoTipo> listaSubproducto;

    private List<MovilidadDocentesProducto> listaProductos;
    private List<MovilidadDocentesProducto> listaProductosEliminados;

    private String personaMovilidad = "";
    private String tipoProducto;
    private String tipoSubproducto;
    private String comentariosInformeSede;

    private String calificacion1Sel;
    private String calificacion2Sel;
    private String calificacion3Sel;
    private SelectItem[] tipoCalificacion1;
    private SelectItem[] tipoCalificacion2;
    private SelectItem[] tipoCalificacion3;

    private String cooproductoSel;
    private SelectItem[] tipoCooproducto;
    private SelectItem[] tipoProductoSelect;
    private SelectItem[] tipoSubproductoSelect;
    private String descripcionPro;
    private String descripcionProducto;
    private String tituloTrabajoPresentado;
    private String experienciasTrabajo;

    private String apoyoTotal;
    private String costoTiquetes;
    private String valorTotalViativos;
    private String valorEvento;
    private Long apoyoTotalAdicional;

    private String descripcionMovilidad;
    private DefaultUploadedFile archivoObligatorio;
    private SelectItem[] tipoDocumentoSelItem;
    private String tipoDocumentoSel;
    private List<ArchivoMovilidad> listaArchivosObligatoriosSel;
    private List<ArchivoMovilidad> listaArchivosObligatoriosEliminados;
    private Object[] coproductoTable;
    private MovilidadDocentesProducto productoTable;

    private ArchivoMovilidad archivoTabla;
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

    public ManejadorCrearEditarSeguimientoMovilidadDocente() {

        cargarValoresIniciales();

        consulta = habilitarEdicion();
        mde = (MovilidadDocentesExterior) sesion.getAttribute("movilidadDocente");
        List<MovilidadDocentesExterior> movs = servicioGeneral.obtenerObjetos(MovilidadDocentesExterior.class,
                "from MovilidadDocentesExterior mov where mov.id = '" + mde.getId() + "'");
        if (movs != null && movs.size() > 0) {
            mde = movs.get(0);
        }

        realizacionMovilidad = "SI";

        cargarTipoCooproductos();
        cargarTipoCalificacion();
        cargarTiposDocumentos();

        asignarInformacionMovilidadDocente();
        cargarArchivosMovilidad();
        cargarProductos();
        cargarDatosFacultad();
    }

    public void imprimirInforme() {
        FacesContext context = FacesContext.getCurrentInstance();
        Long id = mde.getId();
        ReporteBirt r = new ReporteBirt();
        r.adicionarParametro("id", Long.toString(id));
        r.setNombreReporte("/movilidad/InformeSeguimientoDocentesEventos");
        r.setFormato(ReporteBirt.FORMATO_PDF);
        sesion.setAttribute("reporte", r);
        System.out.println("mirar");
        System.out.println(Navegacion.REPORTE);
        try {
            context.getExternalContext().dispatch("/ReporteEngineServlet");
        } catch (Exception e) {
            // System.out.println(e);
        } finally {
            context.responseComplete();
        }
    }

    private void cargarDatosFacultad() {
        if (sesion.getAttribute("esConsultaFacultad") != null) {
            esConsultaFacultad = (Boolean) sesion.getAttribute("esConsultaFacultad");
            sesion.removeAttribute("esConsultaFacultad");
        }
        if (sesion.getAttribute("esRevisionFacultad") != null) {
            esRevisionFacultad = (Boolean) sesion.getAttribute("esRevisionFacultad");
            sesion.removeAttribute("esRevisionFacultad");
        }
        if (sesion.getAttribute("esRevisionSede") != null) {
            esRevisionSede = (Boolean) sesion.getAttribute("esRevisionSede");
            sesion.removeAttribute("esRevisionSede");
        }
    }

    private boolean habilitarEdicion() {
        try {
            String consulta = (String) sesion.getAttribute("consultaMovilidadDocente");
            sesion.removeAttribute("consultaMovilidadDocente");
            if (consulta.equals("SI")) {
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
        listaArchivosObligatoriosSel = new ArrayList<ArchivoMovilidad>();

    }

    public void asignarInformacionMovilidadDocente() {
        if (mde != null) {
            personaMovilidad = mde.getPersonaInv().getNombreCompletoMinusculas();
            if (mde.getCalificacionA() != null) {
                calificacion1Sel = mde.getCalificacionA();
            }
            if (mde.getCalificacionB() != null) {
                calificacion2Sel = mde.getCalificacionB();
            }
            if (mde.getCalificacionC() != null) {
                calificacion3Sel = mde.getCalificacionC();
            }
            descripcionMovilidad = mde.getSegMovDescripcion();
            tituloTrabajoPresentado = mde.getTituloTrabajoPresentado();
            experienciasTrabajo = mde.getExperienciasTrabajoPresentado();
            responsableRevision = mde.getPersonaSeguimiento();
            fechaRevision = mde.getFechaRevisionSeguimiento();
            if (mde.getRealizacionMovilidad() != null) {
                realizacionMovilidad = mde.getRealizacionMovilidad();
            }
            razonesNoRealizacion = mde.getRazonesNoRealizacion();
            if (mde.getDescripcionRevision() != null) {
                comentariosInforme = mde.getDescripcionRevision();
            }
            if (mde.getEstadoRevisionSeguimiento() != null) {
                estadoRevisionSeguimiento = mde.getEstadoRevisionSeguimiento();
            }
            if (mde.getValorTotalApoyo() != null) {
                apoyoTotal = mde.getValorTotalApoyo().toString();
            }
            if (mde.getValorTotalTiquetes() != null) {
                costoTiquetes = mde.getValorTotalTiquetes().toString();
            }
            if (mde.getValorTotalViaticos() != null) {
                valorTotalViativos = mde.getValorTotalViaticos().toString();
            }
            if (mde.getValorTotalEvento() != null) {
                valorEvento = mde.getValorTotalEvento().toString();
            }
            if(mde.getMontoAdicionalEjecutado() != null){
            	apoyoTotalAdicional = mde.getMontoAdicionalEjecutado();
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

    public void cargarArchivosMovilidad() {
        listaArchivosObligatoriosEliminados = new ArrayList<ArchivoMovilidad>();
        listaArchivosObligatoriosSel = servicioGeneral.obtenerObjetos(ArchivoMovilidad.class,
                "from ArchivoMovilidad am " + "where am.movilidad = '" + mde.getId() + "'");
    }

    private void cargarProductos() {
        listaProductos = new ArrayList<MovilidadDocentesProducto>();
        listaProductosEliminados = new ArrayList<MovilidadDocentesProducto>();
        List<ProductoTipo> listaProductoTipo = servicioGeneral.obtenerObjetos(ProductoTipo.class,
                "from ProductoTipo pt where pt.descripcion = 'FICHA_MINIMA'" + "and pt.padre.id = '"
                        + CODIGO_PRODUCTOS_FICHA + "'");
        tipoProductoSelect = new SelectItem[listaProductoTipo.size()];
        for (int i = 0; i < listaProductoTipo.size(); i++) {
            ProductoTipo pt = (ProductoTipo) listaProductoTipo.get(i);
            tipoProductoSelect[i] = new SelectItem(pt.getId(), pt.getNombre());
        }
        tipoProducto = ((ProductoTipo) listaProductoTipo.get(0)).getId();
        if (mde.getId() != null) {
            List<MovilidadDocentesProducto> listaProductosBase = servicioGeneral.obtenerObjetos(
                    MovilidadDocentesProducto.class,
                    "from MovilidadDocentesProducto m where m.movilidad.id = " + mde.getId());
            listaProductos.addAll(listaProductosBase);
        }
        cargarSubproductos();
    }

    public void cargarSubproductos() {
        listaSubproducto = servicioGeneral.obtenerObjetos(ProductoTipo.class,
                "from ProductoTipo pt where pt.descripcion = 'FICHA_MINIMA'" + "and pt.padre.id = '" + tipoProducto
                        + "'");
        tipoSubproductoSelect = new SelectItem[listaSubproducto.size()];
        for (int i = 0; i < listaSubproducto.size(); i++) {
            ProductoTipo pt = (ProductoTipo) listaSubproducto.get(i);
            tipoSubproductoSelect[i] = new SelectItem(pt.getId(), pt.getNombre());
        }
        tipoSubproducto = ((ProductoTipo) listaSubproducto.get(0)).getId();
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
        if (mde.getId() != null) {
            List<MovilidadSeguimientoCooproducto> listaMovSegCo = servicioGeneral.obtenerObjetos(
                    MovilidadSeguimientoCooproducto.class,
                    "from MovilidadSeguimientoCooproducto where idMovilidad = " + mde.getId());
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

    public void adicionarCooproducto() {
        boolean bandera = true;
        Object producto[] = new Object[5];
        List<DominioDetalle> listaDominio = servicioGeneral.obtenerObjetos(DominioDetalle.class,
                "from DominioDetalle where " + "identificador.id = '3' and identificador.tipo = '" + cooproductoSel
                        + "'");
        String nombreCo = "";
        if (listaDominio != null && listaDominio.size() > 0) {
            nombreCo = ((DominioDetalle) listaDominio.get(0)).getDescripcion();
        }
        producto[0] = this.cooproductoSel;
        producto[1] = nombreCo;
        producto[2] = this.descripcionPro;

        if (this.descripcionPro == null || this.descripcionPro.length() <= 0) {
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

    private String obtenerNombreTipoProducto(String tipoProducto) {
        Iterator<ProductoTipo> i = listaSubproducto.iterator();
        while (i.hasNext()) {
            ProductoTipo productoTipo = i.next();
            if (productoTipo.getId().equals(tipoProducto)) {
                return productoTipo.getNombre();
            }
        }
        return "";
    }

    public void adicionarProducto() {
        boolean bandera = true;
        MovilidadDocentesProducto docentesProducto = new MovilidadDocentesProducto();
        ProductoTipo productoTipo = new ProductoTipo();
        productoTipo.setId(tipoSubproducto);
        String nombre = obtenerNombreTipoProducto(tipoSubproducto);
        productoTipo.setNombre(nombre);
        docentesProducto.setProducto(productoTipo);
        docentesProducto.setDescripcion(descripcionProducto);

        if (descripcionProducto == null || descripcionProducto.length() <= 0) {
            bandera = false;

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "La descripción del producto es obligatoria", "La descripción del producto es obligatoria");
            mostrarMensaje(message, null);

        }

        if (bandera) {
            listaProductos.add(docentesProducto);
            descripcionProducto = "";
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

    public void eliminarArchivoObligatorio() {
        listaArchivosObligatoriosSel.remove(archivoTabla);
        if (archivoTabla.getId() != null) {
            listaArchivosObligatoriosEliminados.add(archivoTabla);
        }
        archivoTabla = new ArchivoMovilidad();
    }

    public void guardarArchivoObligatorio() {
        ArchivoMovilidad archivoMovilidad = insertarArchivoMovilidadGenerico(mde.getId(), archivoObligatorio,
                tipoDocumentoSel, "SMD");
        if (archivoMovilidad != null) {
            listaArchivosObligatoriosSel.add(archivoMovilidad);
        }
    }

    public void eliminarCoproducto() {
        listaCooproductos.remove(coproductoTable);
        if (coproductoTable[3] != null) {
            coproductoTable[4] = "true";
            listaCooproductosEliminados.add(coproductoTable);
        }
    }

    public void eliminarProducto() {
        listaProductos.remove(productoTable);
        if (productoTable.getId() != null) {
            listaProductosEliminados.add(productoTable);
        }
    }

    public boolean validacion(boolean parcial) {
        if (!parcial) {
            boolean bandera = true;
            if (listaCooproductos == null || listaCooproductos.size() <= 0) {
                bandera = false;

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se han adicionado coproductos",
                        "No se han adicionado coproductos");
                mostrarMensaje(message, null);
            }
            if (listaProductos == null || listaProductos.size() <= 0) {
                bandera = false;

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se han adicionado productos",
                        "No se han adicionado productos");
                mostrarMensaje(message, null);
            }
            if (listaArchivosObligatoriosSel == null || listaArchivosObligatoriosSel.size() <= 0) {
                bandera = false;

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No se encuentran archivos registrados", "No se encuentran archivos registrados");
                mostrarMensaje(message, null);
            }
            if (tituloTrabajoPresentado == null || tituloTrabajoPresentado.length() <= 0) {
                bandera = false;

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No se encuentra registrado el titulo del trabajo presentado",
                        "No se encuentra registrado el titulo del trabajo presentado");
                mostrarMensaje(message, null);
            }
            if (experienciasTrabajo == null || experienciasTrabajo.length() <= 0) {
                bandera = false;

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No se encuentra registrado la experiencia del trabajo presentado",
                        "No se encuentra registrado la experiencia del trabajo presentado");
                mostrarMensaje(message, null);
            }
            if (apoyoTotal == null || (apoyoTotal != null && apoyoTotal.length() == 0)) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Debe ingresar el valor total del apoyo", "Debe ingresar el valor total del apoyo");
                mostrarMensaje(message, null);
                bandera = false;
            } else {
                try {
                    int valor = Integer.parseInt(apoyoTotal.trim());
                    /*if (valor == 0) {
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Debe ingresar el valor total del apoyo válido",
                                "Debe ingresar el valor total del apoyo válido");
                        mostrarMensaje(message, null);
                        bandera = false;
                    }*/
                } catch (NumberFormatException e) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Debe ingresar el valor total del apoyo válido",
                            "Debe ingresar el valor total del apoyo válido");
                    mostrarMensaje(message, null);
                    bandera = false;
                }

            }
            if (costoTiquetes == null || (costoTiquetes != null && costoTiquetes.length() == 0)) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Debe ingresar el valor de los tiquetes", "Debe ingresar el valor de los tiquetes");
                mostrarMensaje(message, null);
                bandera = false;
            } else {
                try {
                    Integer.parseInt(costoTiquetes.trim());
                } catch (NumberFormatException e) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Debe ingresar el valor de tiquetes válido", "Debe ingresar el valor de tiquetes válido");
                    mostrarMensaje(message, null);
                    bandera = false;
                }
            }
            if (valorTotalViativos == null || (valorTotalViativos != null && valorTotalViativos.length() == 0)) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Debe ingresar el valor total de viaticos", "Debe ingresar el valor total de viaticos");
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
            if (valorEvento == null || (valorEvento != null && valorEvento.length() == 0)) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Debe ingresar el valor total de inscripión", "Debe ingresar el valor total de inscripión");
                mostrarMensaje(message, null);
                bandera = false;
            } else {
                try {
                    Integer.parseInt(valorEvento.trim());
                } catch (NumberFormatException e) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Debe ingresar el valor total de inscripión", "Debe ingresar el valor total de inscripión");
                    mostrarMensaje(message, null);
                    bandera = false;
                }
            }
            return bandera;
        } else {
            return true;
        }
    }

    private void cargarTiposDocumentos() {
        List<MovilidadArchivo> listaArchivosObligatorios;
        listaArchivosObligatorios = servicioGeneral.obtenerListaArchivosMovilidad("SMD");
        if (listaArchivosObligatorios != null && listaArchivosObligatorios.size() > 0) {
            tipoDocumentoSelItem = new SelectItem[listaArchivosObligatorios.size()];
            for (int i = 0; i < listaArchivosObligatorios.size(); i++) {
                MovilidadArchivo mea = (MovilidadArchivo) listaArchivosObligatorios.get(i);
                tipoDocumentoSelItem[i] = new SelectItem(mea.getTipoArchivo().getId().toString(),
                        mea.getTipoArchivo().getId().toString() + "-" + mea.getTipoArchivo().getNombre());
            }
        }
    }

    public boolean guardar(String estadoSeguimiento, boolean parcial) {
        if (validacion(parcial) || parcial) {
            for (int c = 0; c < listaCooproductos.size(); c++) {
                Object producto1[] = (Object[]) this.listaCooproductos.get(c);
                if (producto1[3] == null) {
                    MovilidadSeguimientoCooproducto producto = new MovilidadSeguimientoCooproducto();
                    producto.setIdMovilidad(this.mde.getId());
                    producto.setTipo(String.valueOf(producto1[0]));
                    producto.setDetalle(String.valueOf(producto1[2]));
                    servicioGeneral.guardarObjeto(producto);
                }
            }
            if (listaProductos != null && listaProductos.size() > 0) {
                Iterator<MovilidadDocentesProducto> i = listaProductos.iterator();
                while (i.hasNext()) {
                    MovilidadDocentesProducto movilidadDocentesProducto = i.next();
                    if (movilidadDocentesProducto.getId() == null) {
                        movilidadDocentesProducto.setMovilidad(mde);
                        servicioGeneral.guardarObjeto(movilidadDocentesProducto);
                    }
                }
            }
            if (listaProductosEliminados != null && listaProductosEliminados.size() > 0) {
                Iterator<MovilidadDocentesProducto> i = listaProductosEliminados.iterator();
                while (i.hasNext()) {
                    MovilidadDocentesProducto movilidadDocentesProducto = i.next();
                    if (movilidadDocentesProducto.getId() != null) {
                        servicioGeneral.eliminarObjeto(movilidadDocentesProducto);
                    }
                }
            }
            listaProductosEliminados = new ArrayList<MovilidadDocentesProducto>();

            if (listaArchivosObligatoriosEliminados != null && listaArchivosObligatoriosEliminados.size() > 0) {
                Iterator<ArchivoMovilidad> i = listaArchivosObligatoriosEliminados.iterator();
                while (i.hasNext()) {
                    ArchivoMovilidad archivoMovilidad = i.next();
                    if (archivoMovilidad.getId() != null) {
                        servicioGeneral.eliminarObjeto(archivoMovilidad);
                    }
                }
            }
            listaArchivosObligatoriosEliminados = new ArrayList<ArchivoMovilidad>();
            mde.setCalificacionA(this.calificacion1Sel);
            mde.setCalificacionB(this.calificacion2Sel);
            mde.setCalificacionC(this.calificacion3Sel);

            Calendar actual = Calendar.getInstance();
            Date date = actual.getTime();
            mde.setSegMovFecha(date);
            mde.setSegMovDescripcion(this.descripcionMovilidad);
            mde.setEstadoSeguimiento(estadoSeguimiento);
            mde.setEstadoRevisionSeguimiento("");
            Long apoyoTotal;
            try {
                apoyoTotal = Long.parseLong(this.apoyoTotal.trim());
            } catch (NullPointerException e) {
                apoyoTotal = 0L;
            } catch (NumberFormatException e) {
                apoyoTotal = 0L;
            }
            mde.setValorTotalApoyo(apoyoTotal);
            mde.setMontoAdicionalEjecutado(apoyoTotalAdicional);
            Long costoTiquetes;
            try {
                costoTiquetes = Long.parseLong(this.costoTiquetes.trim());
            } catch (NullPointerException e) {
                costoTiquetes = 0L;
            } catch (NumberFormatException e) {
                costoTiquetes = 0L;
            }
            mde.setValorTotalTiquetes(costoTiquetes);
            Long valorTotalViativos;
            try {
                valorTotalViativos = Long.parseLong(this.valorTotalViativos.trim());
            } catch (NullPointerException e) {
                valorTotalViativos = 0L;
            } catch (NumberFormatException e) {
                valorTotalViativos = 0L;
            }
            mde.setValorTotalViaticos(valorTotalViativos);
            Long valorEvento;
            try {
                valorEvento = Long.parseLong(this.valorEvento.trim());
            } catch (NullPointerException e) {
                valorEvento = 0L;
            } catch (NumberFormatException e) {
                valorEvento = 0L;
            }
            mde.setValorTotalEvento(valorEvento);
            
            mde.setRealizacionMovilidad(realizacionMovilidad);

            if (listaCooproductosEliminados != null && listaCooproductosEliminados.size() > 0) {
                for (int c = 0; c < listaCooproductosEliminados.size(); c++) {
                    Object coproducto1[] = (Object[]) listaCooproductosEliminados.get(c);
                    if (coproducto1[3] != null && coproducto1[4] != null && coproducto1[4].equals("true")) {
                        Long id = Long.parseLong(((String) coproducto1[3]).trim());
                        MovilidadSeguimientoCooproducto producto = new MovilidadSeguimientoCooproducto();
                        producto.setId(id);
                        servicioGeneral.eliminarObjeto(producto);
                    }
                }
            }

            listaCooproductosEliminados = new ArrayList<Object[]>();
            mde.setTituloTrabajoPresentado(tituloTrabajoPresentado);
            mde.setExperienciasTrabajoPresentado(experienciasTrabajo);
            for (int i = 0; i < listaArchivosObligatoriosSel.size(); i++) {
                ArchivoMovilidad archivo = new ArchivoMovilidad();
                archivo = (ArchivoMovilidad) listaArchivosObligatoriosSel.get(i);
                archivo.setMovilidad(String.valueOf(mde.getId()));
                if (archivo.getId() == null) {
                    servicioGeneral.guardarObjeto(archivo);
                }
            }
            servicioGeneral.guardarObjeto(mde);
            String mensaje;
            if (!parcial) {
                mensaje = "Seguimiento de movilidad guardado satisfactoriamente.";
                consulta = true;
            } else {
                mensaje = "Seguimiento de movilidad guardado parcialmente.";
            }
            FacesContext.getCurrentInstance().addMessage("msgs",
                    new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, ""));
            sesion.removeAttribute("manejadorConsultaMovilidadesInvestigador");
            sesion.removeAttribute("manejadorAprobacionMovilidadSeguimiento");
        } else {
            return false;
        }
        return true;
    }

    public String atras() {
        sesion.removeAttribute("manejadorCrearEditarSeguimientoMovilidadDocente");
        return "successProyectosMovilidad";
    }
    
    public String atrasAprobacion() {
        sesion.removeAttribute("manejadorCrearEditarSeguimientoMovilidadDocente");
        sesion.removeAttribute("manejadorAprobacionMovilidadSeguimientoSede");
        return "listadoRevisionSeguimientoSede";
    }

    public String atrasRevision() {
        sesion.removeAttribute("manejadorCrearEditarSeguimientoMovilidadDocente");
        sesion.removeAttribute("manejadorAprobacionMovilidadSeguimiento");
        return "listadoRevisionSeguimiento";
    }
    

    
    public MovilidadDocentesExterior asignarMontos(MovilidadDocentesExterior movilidadDocentesExterior) {
        Long apoyoTotal;
        try {
            apoyoTotal = Long.parseLong(this.apoyoTotal.trim());
        } catch (NullPointerException e) {
            apoyoTotal = 0L;
        } catch (NumberFormatException e) {
            apoyoTotal = 0L;
        }
        movilidadDocentesExterior.setValorTotalApoyo(apoyoTotal);
        Long costoTiquetes;
        try {
            costoTiquetes = Long.parseLong(this.costoTiquetes.trim());
        } catch (NullPointerException e) {
            costoTiquetes = 0L;
        } catch (NumberFormatException e) {
            costoTiquetes = 0L;
        }
        movilidadDocentesExterior.setValorTotalTiquetes(costoTiquetes);
        Long valorTotalViativos;
        try {
            valorTotalViativos = Long.parseLong(this.valorTotalViativos.trim());
        } catch (NullPointerException e) {
            valorTotalViativos = 0L;
        } catch (NumberFormatException e) {
            valorTotalViativos = 0L;
        }
        movilidadDocentesExterior.setValorTotalViaticos(valorTotalViativos);
        Long valorEvento;
        try {
            valorEvento = Long.parseLong(this.valorEvento.trim());
        } catch (NullPointerException e) {
            valorEvento = 0L;
        } catch (NumberFormatException e) {
            valorEvento = 0L;
        }
        movilidadDocentesExterior.setValorTotalEvento(valorEvento);
        movilidadDocentesExterior.setMontoAdicionalEjecutado(this.apoyoTotalAdicional);
        return movilidadDocentesExterior;
    }

    private void actualizarRevisionSeguimiento(String estado, String sqlAdcional, String nombreObservaciones) {
        
        mde = asignarMontos(mde);        
        servicioGeneral.guardarObjeto(mde);
        
        revisadoFacultad = true;
        revisadoSede = true;
        estadoRevisionSeguimiento = estado;
        String sql = "update HER_MOVILIDAD_DOCENTES_EVENTOS " + "set "+nombreObservaciones+" = '" + comentariosInforme
                + "'," + "MOV_PER_ID_REV_SEG = '" + personaActual.getId().getDocumento() + "', "
                + "MOV_TDO_ID_REV_SEG = '" + personaActual.getId().getTipoDocumento() + "',"
                + "MOV_FECHA_REV_SEG = SYSDATE, " + "MOV_ESTADO_REVISION_SEG = '" + estado + "' " + sqlAdcional
                + " where MOV_ID = '" + mde.getId() + "'";
        sesion.removeAttribute("manejadorAprobacionMovilidadSeguimiento");
        responsableRevision = personaActual;
        fechaRevision = new Date();
        servicioGeneral.ejecutarSentencia(sql);
    }

    public void guardarLectura() {
        actualizarRevisionSeguimiento(MovilidadDocentesExterior.LECTURA_FACULTAD, "","MOV_OBSERVACION_REV_SEG");
        enviarCorreoSeguimientoMovilidad(PLANT_ENVIO_REVI_FACULTAD, mde.getPersonaInv().getEmail());
        mensajeRevisionFacultad = "Se ha dado lectura al informe.";
    }

    public void guardarAprobacion() {
        actualizarRevisionSeguimiento(MovilidadVisitanteExterior.APROBACION_SEDE, "","MOV_OBSERVACIONES_APR_SEG");
        enviarCorreoSeguimientoMovilidad(PLANT_ENVIO_APROBACION_SEDE, mde.getPersonaInv().getEmail());
        mensajeRevisionFacultad = "Se ha dado aprobación al informe.";
    }

    public void enviarCorreoSeguimientoMovilidad(int idPlantilla, String email) {
        CorreoPlantilla correoActual = cargarPlantilla(idPlantilla);
        correoActual = editarCorreoSeguimientoMovilidad(mde, correoActual);
        Correo correo = new Correo();
        correo.setOrigen(Correo.CORREO_HERMES);
        correo.adicionarDireccion(email);
        //correo.adicionarDireccion(Correo.CORREO_HERMES);
        if (idPlantilla == PLANT_ENVIO_REVI_FACULTAD || idPlantilla == PLANT_ENVIO_DEVO_FACULTAD) {
            correo.adicionarDireccion(personaActual.getEmail());
        }
        correo.setAsunto(correoActual.getAsunto());
        correo.setCuerpo(correoActual.getCuerpo());
        servicioCorreo.enviarCorreo(correo);
    }

    public CorreoPlantilla editarCorreoSeguimientoMovilidad(MovilidadDocentesExterior mov,
            CorreoPlantilla correoPlantilla) {
        try {
            String correo = correoPlantilla.getCuerpo();
            String investigador = mov.getPersonaInv().getNombreCompleto();
            correo = correo.replaceAll("<<INVESTIGADOR>>", investigador);
            correo = correo.replaceAll("<<IDMOVILIDAD>>", mov.getId().toString());
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
            correo = correo.replaceAll("<<TIPO>>", mov.getTipoMovilidad().getNombre());
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

    public void guardarDevolucion() {
        actualizarRevisionSeguimiento(MovilidadDocentesExterior.DEVUELTO, ", MOV_ESTADO_SEG  = 'P'","MOV_OBSERVACION_REV_SEG");
        enviarCorreoSeguimientoMovilidad(PLANT_ENVIO_DEVO_FACULTAD, mde.getPersonaInv().getEmail());
        mensajeRevisionFacultad = "Se ha devuelto el informe para correcciones.";
    }

    public void guardarDevolucionSede() {
        actualizarRevisionSeguimiento(MovilidadVisitanteExterior.DEVUELTO, ", MOV_ESTADO_SEG  = 'P'","MOV_OBSERVACIONES_APR_SEG");
        enviarCorreoSeguimientoMovilidad(PLANT_ENVIO_DEVO_FACULTAD, mde.getPersonaInv().getEmail());
        mensajeRevisionFacultad = "Se ha devuelto el informe para correcciones.";
    }

    public void enviarNotificacionEnvioInforme() {
        InvestigadorInterno ii = (InvestigadorInterno) mde.getPersonaInv();
        List<Persona> responsables = obtenerCorreoResponsable(ii.getDependencia());
        if (responsables != null && responsables.size() > 0) {
            Iterator<Persona> i = responsables.iterator();
            while (i.hasNext()) {
                Persona persona = i.next();
                if(ii.getDependencia().getSede().isEsSedePresenciaNacional()) {
                    enviarCorreoSeguimientoMovilidad(PLANT_ENVIO_SEG_SEDE_PRESENCIA_NACIONAL, persona.getEmail());
                }
                else {
                    enviarCorreoSeguimientoMovilidad(PLANT_ENVIO_SEG_FACULTAD, persona.getEmail());
                }
            }
        }
        enviarCorreoSeguimientoMovilidad(PLANT_ENVIO_SEG_DOCENTE, mde.getPersonaInv().getEmail());
    }

    public void guardarNoRealizacionParcial() {
        guardarNoRealizacion("P", true);
    }

    public void guardarNoRealizacionEnviar() {
        boolean guardo = guardarNoRealizacion("F", false);
        if (guardo) {
            enviarNotificacionEnvioInforme();
        }
    }

    public boolean guardarNoRealizacion(String estadoSeguimiento, boolean parcial) {
        if (validacionNoRealizado(parcial) || parcial) {
            mde.setRealizacionMovilidad(realizacionMovilidad);
            mde.setEstadoSeguimiento(estadoSeguimiento);
            mde.setSegMovFecha(new Date());
            mde.setRazonesNoRealizacion(razonesNoRealizacion);
            servicioGeneral.guardarObjeto(mde);
            for (int i = 0; i < listaArchivosObligatoriosSel.size(); i++) {
                ArchivoMovilidad archivo = new ArchivoMovilidad();
                archivo = (ArchivoMovilidad) listaArchivosObligatoriosSel.get(i);
                archivo.setMovilidad(String.valueOf(mde.getId()));
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
            sesion.removeAttribute("manejadorConsultaMovilidadesInvestigador");
            sesion.removeAttribute("manejadorAprobacionMovilidadSeguimiento");
        } else {
            return false;
        }
        return true;
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

    public MovilidadDocentesExterior getMde() {
        return mde;
    }

    public String getPersonaMovilidad() {
        return personaMovilidad;
    }

    public String getCooproductoSel() {
        return cooproductoSel;
    }

    public SelectItem[] getTipoCooproducto() {
        return tipoCooproducto;
    }

    public String getDescripcionPro() {
        return descripcionPro;
    }

    public void setDescripcionPro(String descripcionPro) {
        this.descripcionPro = descripcionPro;
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

    public String getTituloTrabajoPresentado() {
        return tituloTrabajoPresentado;
    }

    public void setTituloTrabajoPresentado(String tituloTrabajoPresentado) {
        this.tituloTrabajoPresentado = tituloTrabajoPresentado;
    }

    public String getExperienciasTrabajo() {
        return experienciasTrabajo;
    }

    public void setExperienciasTrabajo(String experienciasTrabajo) {
        this.experienciasTrabajo = experienciasTrabajo;
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

    public String getValorEvento() {
        return valorEvento;
    }

    public void setValorEvento(String valorEvento) {
        this.valorEvento = valorEvento;
    }

    public boolean isConsulta() {
        return consulta;
    }

    public void setCooproductoSel(String cooproductoSel) {
        this.cooproductoSel = cooproductoSel;
    }

    public void descargarArchivoObligatorio() {
        descargarArchivoMovilidadGenerico(archivoTabla.getId());
    }

    public List<MovilidadDocentesProducto> getListaProductos() {
        return listaProductos;
    }

    public MovilidadDocentesProducto getProductoTable() {
        return productoTable;
    }

    public void setProductoTable(MovilidadDocentesProducto productoTable) {
        this.productoTable = productoTable;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public SelectItem[] getTipoProductoSelect() {
        return tipoProductoSelect;
    }

    public String getTipoSubproducto() {
        return tipoSubproducto;
    }

    public void setTipoSubproducto(String tipoSubproducto) {
        this.tipoSubproducto = tipoSubproducto;
    }

    public SelectItem[] getTipoSubproductoSelect() {
        return tipoSubproductoSelect;
    }

    public boolean isEsConsultaFacultad() {
        return esConsultaFacultad;
    }

    public String getComentariosInforme() {
        return comentariosInforme;
    }

    public void setComentariosInforme(String comentariosInforme) {
        this.comentariosInforme = comentariosInforme;
    }

    public boolean isEsRevisionFacultad() {
        return esRevisionFacultad;
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
     * @param esRevisionSede the esRevisionSede to set
     */
    public void setEsRevisionSede(boolean esRevisionSede) {
        this.esRevisionSede = esRevisionSede;
    }

    /**
     * @return the comentariosInformeSede
     */
    public String getComentariosInformeSede() {
        return comentariosInformeSede;
    }

    /**
     * @param comentariosInformeSede the comentariosInformeSede to set
     */
    public void setComentariosInformeSede(String comentariosInformeSede) {
        this.comentariosInformeSede = comentariosInformeSede;
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
}
