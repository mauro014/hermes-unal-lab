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
import co.edu.unal.hermes.modelo.MovilidadArchivo;
import co.edu.unal.hermes.modelo.MovilidadEstudiantesPosgrado;
import co.edu.unal.hermes.modelo.MovilidadEstudiantesProducto;
import co.edu.unal.hermes.modelo.MovilidadSeguimientoCooproducto;
import co.edu.unal.hermes.modelo.MovilidadVisitanteExterior;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.ProductoTipo;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.utils.Navegacion;

public class ManejadorCrearEditarSeguimientoMovilidadEstudiante extends ManejadorBaseMovilidad {

    private static final long serialVersionUID = -3870123805429202672L;
    private MovilidadEstudiantesPosgrado mep;
    private List<Object[]> listaCooproductos;
    private boolean consulta;

    private String personaMovilidad = "";
    private String tipoProducto;
    private String estudianteMovilidad = "";
    private String tipoSubproducto;
    private String tituloTrabajoPresentado;
    private String experienciasTrabajo;

    private String calificacion1Sel;
    private String calificacion2Sel;
    private String calificacion3Sel;
    private SelectItem[] tipoCalificacion1;
    private SelectItem[] tipoCalificacion2;
    private SelectItem[] tipoCalificacion3;
    private List<Object[]> listaCooproductosEliminados;

    private String cooproductoSel;
    private SelectItem[] tipoCooproducto;
    private SelectItem[] tipoProductoSelect;
    private SelectItem[] tipoSubproductoSelect;

    private String apoyoTotal;
    private String costoTiquetes;
    private String valorTotalViativos;
    private String valorEvento;
    private Long apoyoTotalAdicional;

    private String descripcionPro;
    private String descripcionProducto;

    private String descripcionMovilidad;
    private DefaultUploadedFile archivoObligatorio;
    private SelectItem[] tipoDocumentoSelItem;
    private String tipoDocumentoSel;
    private List<ArchivoMovilidad> listaArchivosObligatoriosSel;
    private List<ArchivoMovilidad> listaArchivosObligatoriosEliminados;

    private Object[] coproductoTable;
    private ArchivoMovilidad archivoTabla;
    private List<ProductoTipo> listaSubproducto;

    private List<MovilidadEstudiantesProducto> listaProductos;
    private List<MovilidadEstudiantesProducto> listaProductosEliminados;
    private MovilidadEstudiantesProducto productoTable;
    private boolean esConsultaFacultad;

    private boolean esResidencia;
    private String comentariosInforme;
    private boolean esRevisionFacultad;
    private boolean esRevisionSede;

    private String mensajeRevisionFacultad;
    private boolean revisadoFacultad;
    private boolean revisadoSede;
    private String estadoRevisionSeguimiento;

    private Persona responsableRevision;
    private Date fechaRevision;

    private String realizacionMovilidad;
    private String razonesNoRealizacion;

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

    public ManejadorCrearEditarSeguimientoMovilidadEstudiante() {

        cargarValoresIniciales();

        consulta = habilitarEdicion();
        mep = (MovilidadEstudiantesPosgrado) sesion.getAttribute("movilidadEstudiantePos");

        List<MovilidadEstudiantesPosgrado> movs = servicioGeneral.obtenerObjetos(MovilidadEstudiantesPosgrado.class,
                "from MovilidadEstudiantesPosgrado mov where mov.id = '" + mep.getId() + "'");
        if (movs != null && movs.size() > 0) {
            mep = movs.get(0);
        }

        realizacionMovilidad = "SI";

        cargarTipoCooproductos();
        cargarTipoCalificacion();
        cargarTiposDocumentos();
        asignarInformacionMovilidadEstudiantes();
        cargarProductos();
        cargarArchivosMovilidad();
        cargarTipoMovilidad();
        cargarDatosFacultad();
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

    public void cargarArchivosMovilidad() {
        listaArchivosObligatoriosEliminados = new ArrayList<ArchivoMovilidad>();
        listaArchivosObligatoriosSel = servicioGeneral.obtenerObjetos(ArchivoMovilidad.class,
                "from ArchivoMovilidad am " + "where am.movilidad = '" + mep.getId() + "'");
    }

    private void cargarTipoMovilidad() {
        if (sesion.getAttribute("esMovilidadResidencia") != null) {
            esResidencia = (Boolean) sesion.getAttribute("esMovilidadResidencia");
        }
        sesion.removeAttribute("esMovilidadResidencia");
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

    private boolean habilitarEdicion() {
        try {
            String consulta = (String) sesion.getAttribute("consultaMovilidadEstudiantesEventos");
            sesion.removeAttribute("consultaMovilidadEstudiantesEventos");
            if (consulta.equals("SI")) {
                return true;
            }
        } catch (NullPointerException npe) {
            return false;
        }
        return false;
    }

    public void adicionarProducto() {
        boolean bandera = true;
        MovilidadEstudiantesProducto estudiantesProducto = new MovilidadEstudiantesProducto();
        ProductoTipo productoTipo = new ProductoTipo();
        productoTipo.setId(tipoSubproducto);
        String nombre = obtenerNombreTipoProducto(tipoSubproducto);
        productoTipo.setNombre(nombre);
        estudiantesProducto.setProducto(productoTipo);
        estudiantesProducto.setDescripcion(descripcionProducto);

        if (descripcionProducto == null || descripcionProducto.length() <= 0) {
            bandera = false;

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "La descripción del producto es obligatoria", "La descripción del producto es obligatoria");
            mostrarMensaje(message, null);

        }

        if (bandera) {
            listaProductos.add(estudiantesProducto);
            descripcionProducto = "";
        }

    }

    private void asignarInformacionMovilidadEstudiantes() {
        if (mep != null) {
            personaMovilidad = mep.getPersonaInv().getNombreCompletoMinusculas();
            estudianteMovilidad = mep.getEstudianteInv().getNombreCompleto();
            if (mep.getCalificacionA() != null) {
                calificacion1Sel = mep.getCalificacionA();
            }
            if (mep.getCalificacionB() != null) {
                calificacion2Sel = mep.getCalificacionB();
            }
            if (mep.getCalificacionC() != null) {
                calificacion3Sel = mep.getCalificacionC();
            }
            descripcionMovilidad = mep.getSegMovDescripcion();
            tituloTrabajoPresentado = mep.getTituloTrabajoPresentado();
            experienciasTrabajo = mep.getExperienciasTrabajoPresentado();
            responsableRevision = mep.getPersonaSeguimiento();
            fechaRevision = mep.getFechaRevisionSeguimiento();
            if (mep.getRealizacionMovilidad() != null) {
                realizacionMovilidad = mep.getRealizacionMovilidad();
            }
            razonesNoRealizacion = mep.getRazonesNoRealizacion();
            if (mep.getDescripcionRevision() != null) {
                comentariosInforme = mep.getDescripcionRevision();
            }
            if (mep.getEstadoRevisionSeguimiento() != null) {
                estadoRevisionSeguimiento = mep.getEstadoRevisionSeguimiento();
            }
            if (mep.getValorTotalApoyo() != null) {
                apoyoTotal = mep.getValorTotalApoyo().toString();
            }
            if (mep.getValorTotalTiquetes() != null) {
                costoTiquetes = mep.getValorTotalTiquetes().toString();
            }
            if (mep.getValorTotalViaticos() != null) {
                valorTotalViativos = mep.getValorTotalViaticos().toString();
            }
            if (mep.getValorTotalEvento() != null) {
                valorEvento = mep.getValorTotalEvento().toString();
            }
            if (mep.getMontoAdicionalEjecutado() != null) {
                apoyoTotalAdicional = mep.getMontoAdicionalEjecutado();
            }
        }
    }

    public void eliminarProducto() {
        listaProductos.remove(productoTable);
        if (productoTable.getId() != null) {
            listaProductosEliminados.add(productoTable);
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
        List<Persona> responsables = obtenerCorreoResponsable(mep.getEstudianteInv().getDependencia());
        if (responsables != null && responsables.size() > 0) {
            Iterator<Persona> i = responsables.iterator();
            while (i.hasNext()) {
                Persona persona = i.next();
                if(mep.getEstudianteInv().getDependencia().getSede().isEsSedePresenciaNacional()) {
                    enviarCorreoSeguimientoMovilidad(PLANT_ENVIO_SEG_SEDE_PRESENCIA_NACIONAL, persona.getEmail());
                }
                else {
                    enviarCorreoSeguimientoMovilidad(PLANT_ENVIO_SEG_FACULTAD, persona.getEmail());
                }
            }
        }
        enviarCorreoSeguimientoMovilidad(PLANT_ENVIO_SEG_DOCENTE, mep.getPersonaInv().getEmail());
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

    public void enviarCorreoSeguimientoMovilidad(int idPlantilla, String email) {
        CorreoPlantilla correoActual = cargarPlantilla(idPlantilla);
        correoActual = editarCorreoSeguimientoMovilidad(mep, correoActual);
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

    public CorreoPlantilla editarCorreoSeguimientoMovilidad(MovilidadEstudiantesPosgrado mov,
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

    private void cargarValoresIniciales() {
        personaActual = (Persona) sesion.getAttribute("persona");
        listaCooproductos = new ArrayList<Object[]>();
        listaArchivosObligatoriosSel = new ArrayList<ArchivoMovilidad>();

    }

    private void cargarProductos() {
        listaProductos = new ArrayList<MovilidadEstudiantesProducto>();
        listaProductosEliminados = new ArrayList<MovilidadEstudiantesProducto>();
        List<ProductoTipo> listaProductoTipo = servicioGeneral.obtenerObjetos(ProductoTipo.class,
                "from ProductoTipo pt where pt.descripcion = 'FICHA_MINIMA'" + "and pt.padre.id = '"
                        + CODIGO_PRODUCTOS_FICHA + "'");
        tipoProductoSelect = new SelectItem[listaProductoTipo.size()];
        for (int i = 0; i < listaProductoTipo.size(); i++) {
            ProductoTipo pt = (ProductoTipo) listaProductoTipo.get(i);
            tipoProductoSelect[i] = new SelectItem(pt.getId(), pt.getNombre());
        }
        tipoProducto = ((ProductoTipo) listaProductoTipo.get(0)).getId();
        if (mep.getId() != null) {
            List<MovilidadEstudiantesProducto> listaProductosBase = servicioGeneral.obtenerObjetos(
                    MovilidadEstudiantesProducto.class,
                    "from MovilidadEstudiantesProducto m where m.movilidad.id = " + mep.getId());
            listaProductos.addAll(listaProductosBase);
        }
        cargarSubproductos();
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
        if (mep.getId() != null) {
            List<MovilidadSeguimientoCooproducto> listaMovSegCo = servicioGeneral.obtenerObjetos(
                    MovilidadSeguimientoCooproducto.class,
                    "from MovilidadSeguimientoCooproducto where idMovilidad = " + mep.getId());
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

    public void descargarArchivoObligatorio() {
        descargarArchivoMovilidadGenerico(archivoTabla.getId());
    }

    public void adicionarCooproducto() {
        boolean bandera = true;
        Object producto[] = new Object[5];
        List<DominioDetalle> listaDominio = servicioGeneral.obtenerObjetos(DominioDetalle.class, "from "
                + "DominioDetalle where identificador.id = '3' " + "and identificador.tipo = '" + cooproductoSel + "'");
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
        ArchivoMovilidad archivoMovilidad = insertarArchivoMovilidadGenerico(mep.getId(), archivoObligatorio,
                tipoDocumentoSel, "SME");
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
    
    public boolean validarMontos() {
        boolean bandera = true;
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
        return bandera;
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
            if ((tituloTrabajoPresentado == null || tituloTrabajoPresentado.length() <= 0) && !esResidencia) {
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
            if(!validarMontos()) {
                bandera = false;
            }
            if (!esResidencia) {
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
                                "Debe ingresar el valor total de inscripión",
                                "Debe ingresar el valor total de inscripión");
                        mostrarMensaje(message, null);
                        bandera = false;
                    }
                }
            }
            return bandera;
        } else {
            return true;
        }
    }

    private void cargarTiposDocumentos() {
        List<MovilidadArchivo> listaArchivosObligatorios;
        if (!esResidencia) {
            listaArchivosObligatorios = servicioGeneral.obtenerListaArchivosMovilidad("SME1");
        } else {
            listaArchivosObligatorios = servicioGeneral.obtenerListaArchivosMovilidad("SME2");
        }
        if (listaArchivosObligatorios != null && listaArchivosObligatorios.size() > 0) {
            tipoDocumentoSelItem = new SelectItem[listaArchivosObligatorios.size()];
            for (int i = 0; i < listaArchivosObligatorios.size(); i++) {
                MovilidadArchivo mea = (MovilidadArchivo) listaArchivosObligatorios.get(i);
                tipoDocumentoSelItem[i] = new SelectItem(mea.getTipoArchivo().getId().toString(),
                        mea.getTipoArchivo().getNombre());
            }
        }
    }
    
    public MovilidadEstudiantesPosgrado asignarMontos(MovilidadEstudiantesPosgrado movilidadEstudiantesPosgrado) {
        Long apoyoTotal;
        try {
            apoyoTotal = Long.parseLong(this.apoyoTotal.trim());
        } catch (NullPointerException e) {
            apoyoTotal = 0L;
        } catch (NumberFormatException e) {
            apoyoTotal = 0L;
        }
        movilidadEstudiantesPosgrado.setValorTotalApoyo(apoyoTotal);
        Long costoTiquetes;
        try {
            costoTiquetes = Long.parseLong(this.costoTiquetes.trim());
        } catch (NullPointerException e) {
            costoTiquetes = 0L;
        } catch (NumberFormatException e) {
            costoTiquetes = 0L;
        }
        movilidadEstudiantesPosgrado.setValorTotalTiquetes(costoTiquetes);
        Long valorTotalViativos;
        try {
            valorTotalViativos = Long.parseLong(this.valorTotalViativos.trim());
        } catch (NullPointerException e) {
            valorTotalViativos = 0L;
        } catch (NumberFormatException e) {
            valorTotalViativos = 0L;
        }
        movilidadEstudiantesPosgrado.setValorTotalViaticos(valorTotalViativos);
        Long valorEvento;
        try {
            valorEvento = Long.parseLong(this.valorEvento.trim());
        } catch (NullPointerException e) {
            valorEvento = 0L;
        } catch (NumberFormatException e) {
            valorEvento = 0L;
        }
        movilidadEstudiantesPosgrado.setValorTotalEvento(valorEvento);
        movilidadEstudiantesPosgrado.setMontoAdicionalEjecutado(this.apoyoTotalAdicional);
        return movilidadEstudiantesPosgrado;
    }

    public boolean guardar(String estadoSeguimiento, boolean parcial) {
        if (validacion(parcial) || parcial) {
            mep.setRealizacionMovilidad(realizacionMovilidad);
            for (int c = 0; c < listaCooproductos.size(); c++) {
                MovilidadSeguimientoCooproducto producto = new MovilidadSeguimientoCooproducto();
                Object producto1[] = (Object[]) this.listaCooproductos.get(c);
                if (producto1[3] == null) {
                    producto.setIdMovilidad(this.mep.getId());
                    producto.setTipo(String.valueOf(producto1[0]));
                    producto.setDetalle(String.valueOf(producto1[2]));
                    servicioGeneral.guardarObjeto(producto);
                }
            }
            if (listaProductos != null && listaProductos.size() > 0) {
                Iterator<MovilidadEstudiantesProducto> i = listaProductos.iterator();
                while (i.hasNext()) {
                    MovilidadEstudiantesProducto movilidadEstudiantesProducto = i.next();
                    if (movilidadEstudiantesProducto.getId() == null) {
                        movilidadEstudiantesProducto.setMovilidad(mep);
                        servicioGeneral.guardarObjeto(movilidadEstudiantesProducto);
                    }
                }
            }
            if (listaProductosEliminados != null && listaProductosEliminados.size() > 0) {
                Iterator<MovilidadEstudiantesProducto> i = listaProductosEliminados.iterator();
                while (i.hasNext()) {
                    MovilidadEstudiantesProducto movilidadEstudiantesProducto = i.next();
                    if (movilidadEstudiantesProducto.getId() != null) {
                        servicioGeneral.eliminarObjeto(movilidadEstudiantesProducto);
                    }
                }
            }
            listaProductosEliminados = new ArrayList<MovilidadEstudiantesProducto>();

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
            for (int i = 0; i < listaArchivosObligatoriosSel.size(); i++) {
                ArchivoMovilidad archivo = new ArchivoMovilidad();
                archivo = (ArchivoMovilidad) listaArchivosObligatoriosSel.get(i);
                archivo.setMovilidad(String.valueOf(mep.getId()));
                servicioGeneral.guardarObjeto(archivo);
            }
            listaArchivosObligatoriosEliminados = new ArrayList<ArchivoMovilidad>();

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

            mep.setTituloTrabajoPresentado(tituloTrabajoPresentado);
            mep.setExperienciasTrabajoPresentado(experienciasTrabajo);

            mep.setCalificacionA(this.calificacion1Sel);
            mep.setCalificacionB(this.calificacion2Sel);
            mep.setCalificacionC(this.calificacion3Sel);

            Calendar actual = Calendar.getInstance();
            Date date = actual.getTime();
            mep.setSegMovFecha(date);
            mep.setEstadoRevisionSeguimiento("");

            mep.setSegMovDescripcion(this.descripcionMovilidad);
            mep.setEstadoSeguimiento(estadoSeguimiento);
            
            mep = asignarMontos(mep);
            
            servicioGeneral.guardarObjeto(mep);
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
            mep.setRealizacionMovilidad(realizacionMovilidad);
            mep.setEstadoSeguimiento(estadoSeguimiento);
            mep.setSegMovFecha(new Date());
            mep.setRazonesNoRealizacion(razonesNoRealizacion);
            servicioGeneral.guardarObjeto(mep);
            for (int i = 0; i < listaArchivosObligatoriosSel.size(); i++) {
                ArchivoMovilidad archivo = new ArchivoMovilidad();
                archivo = (ArchivoMovilidad) listaArchivosObligatoriosSel.get(i);
                archivo.setMovilidad(String.valueOf(mep.getId()));
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

    public String atras() {
        sesion.removeAttribute("manejadorCrearEditarSeguimientoMovilidadEstudiante");
        return "successProyectosMovilidad";
    }

    public String atrasRevision() {
        sesion.removeAttribute("manejadorCrearEditarSeguimientoMovilidadEstudiante");
        sesion.removeAttribute("manejadorAprobacionMovilidadSeguimiento");
        return "listadoRevisionSeguimiento";
    }
    
    public String atrasAprobacion() {
        sesion.removeAttribute("manejadorCrearEditarSeguimientoMovilidadEstudiantes");
        sesion.removeAttribute("manejadorAprobacionMovilidadSeguimientoSede");
        return "listadoRevisionSeguimientoSede";
    }

    private void actualizarRevisionSeguimiento(String estado, String sqlAdcional, String nombreObservaciones) {

        mep = asignarMontos(mep);        
        servicioGeneral.guardarObjeto(mep);
        
        revisadoFacultad = true;
        revisadoSede = true;
        estadoRevisionSeguimiento = estado;
        String sql = "update HER_MOVILIDAD_ESTUDIANTE_POS " + "set "+nombreObservaciones+" = '" + comentariosInforme
                + "'," + "MOV_PER_ID_REV_SEG = '" + personaActual.getId().getDocumento() + "', "
                + "MOV_TDO_ID_REV_SEG = '" + personaActual.getId().getTipoDocumento() + "',"
                + "MOV_FECHA_REV_SEG = SYSDATE, " + "MOV_ESTADO_REVISION_SEG = '" + estado + "' " + sqlAdcional
                + " where MOV_ID = '" + mep.getId() + "'";
        responsableRevision = personaActual;
        fechaRevision = new Date();
        sesion.removeAttribute("manejadorAprobacionMovilidadSeguimiento");
        servicioGeneral.ejecutarSentencia(sql);
    }

    public void guardarLectura() {
        actualizarRevisionSeguimiento(MovilidadEstudiantesPosgrado.LECTURA_FACULTAD, "","MOV_OBSERVACION_REV_SEG");
        enviarCorreoSeguimientoMovilidad(PLANT_ENVIO_REVI_FACULTAD, mep.getPersonaInv().getEmail());
        mensajeRevisionFacultad = "Se ha dado lectura al informe.";
    }

    public void guardarAprobacion() {
        actualizarRevisionSeguimiento(MovilidadVisitanteExterior.APROBACION_SEDE, "","MOV_OBSERVACIONES_APR_SEG");
        enviarCorreoSeguimientoMovilidad(PLANT_ENVIO_APROBACION_SEDE, mep.getPersonaInv().getEmail());
        mensajeRevisionFacultad = "Se ha dado aprobación al informe.";
    }

    public void guardarDevolucion() {
        actualizarRevisionSeguimiento(MovilidadEstudiantesPosgrado.DEVUELTO, ", MOV_ESTADO_SEG  = 'P'","MOV_OBSERVACION_REV_SEG");
        enviarCorreoSeguimientoMovilidad(PLANT_ENVIO_DEVO_FACULTAD, mep.getPersonaInv().getEmail());
        mensajeRevisionFacultad = "Se ha devuelto el informe para correcciones.";
    }

    public void guardarDevolucionSede() {
        actualizarRevisionSeguimiento(MovilidadVisitanteExterior.DEVUELTO, ", MOV_ESTADO_SEG  = 'P'","MOV_OBSERVACIONES_APR_SEG");
        enviarCorreoSeguimientoMovilidad(PLANT_ENVIO_DEVO_FACULTAD, mep.getPersonaInv().getEmail());
        mensajeRevisionFacultad = "Se ha devuelto el informe para correcciones.";
    }

    public void imprimirInforme() {
        FacesContext context = FacesContext.getCurrentInstance();
        Long id = mep.getId();
        ReporteBirt r = new ReporteBirt();
        r.adicionarParametro("id", Long.toString(id));
        if (esResidencia) {
            r.adicionarParametro("esResidencia", "SI");
        } else {
            r.adicionarParametro("esResidencia", "NO");
        }
        r.setNombreReporte("/movilidad/InformeSeguimientoEstudiantesPosgrado");
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

    public MovilidadEstudiantesPosgrado getMep() {
        return mep;
    }

    public String getPersonaMovilidad() {
        return personaMovilidad;
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

    public String getDescripcionPro() {
        return descripcionPro;
    }

    public void setDescripcionPro(String descripcionPro) {
        this.descripcionPro = descripcionPro;
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

    public List<Object[]> getListaCooproductos() {
        return listaCooproductos;
    }

    public String getEstudianteMovilidad() {
        return estudianteMovilidad;
    }

    public boolean isConsulta() {
        return consulta;
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

    public SelectItem[] getTipoSubproductoSelect() {
        return tipoSubproductoSelect;
    }

    public String getTipoSubproducto() {
        return tipoSubproducto;
    }

    public void setTipoSubproducto(String tipoSubproducto) {
        this.tipoSubproducto = tipoSubproducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public List<MovilidadEstudiantesProducto> getListaProductos() {
        return listaProductos;
    }

    public MovilidadEstudiantesProducto getProductoTable() {
        return productoTable;
    }

    public void setProductoTable(MovilidadEstudiantesProducto productoTable) {
        this.productoTable = productoTable;
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

    public boolean isEsResidencia() {
        return esResidencia;
    }

    public String getTituloPonencia() {
        if (esResidencia) {
            return "Residencia";
        } else
            return "Ponencia";
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
