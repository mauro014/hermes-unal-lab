/**
 * @author  Ing Hernán Darío Bernal Parra
 */

package co.edu.unal.hermes.vista.proyectos.manejadorproyecto;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.menuitem.MenuItem;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.ClasificacionConocimiento;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.Formulario;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.HistoricoFormularioProyecto;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorExterno;
import co.edu.unal.hermes.modelo.InvestigadorGrupo;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PosibleEvaluador;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoModalidad;
import co.edu.unal.hermes.vista.proyectos.ManejadorMenuFormularios;

public class ManejadorEvaluadores extends ManejadorProyecto {

    private static final long serialVersionUID = 3410161352216746165L;
    private List<EvaluadorVista> listaEvaluadoresVista;
    private List listaEvaluadoresExternos;
    private List listaEvaluadoresInternos;
    private List listaEleccionInternos;
    private DataTable tablaEvaluadores;
    private PosibleEvaluador posibleEvaluador;
    private InvestigadorInterno posibleEvaluadorInterno;
    private List listaEvaluadorInvExterno;
    private List listaEvaluadorInvestigadorExterno;
    private InvestigadorInterno posibleEvaluadorInvestigadorExt;
    private String idInvestigador = "";
    private String titulo1;
    private String titulo2;
    private InvestigadorInternoVista peiSeleccionado;
    private EvaluadorVista posibleEvaluadorSeleccionado;

    private String emailDetalle;
    private String telefonoDetalle;
    private String experticiaDetalle;
    private String tipoEvaluadorDetalle;
    private String nombreDetalle;

    private UIData tablaConsultas; // OBJETO GRÁFICO PARA EL MANEJO DE LAS
    // LISTAS

    private List listaInvestigadores; // MANTIENE LA LISTA DE PERSONAS
    // CONSULTADAS

    private String nombreBusqueda; // CADENA CLAVE PARA LA BÚSQUEDA DE LOS
    // NOMBRES DE LOS INVESTIGADORES
    private String apellidoBusqueda; // CADENA CLAVE PARA LA BÚSQUEDA DE LOS
    // APELLIDOS DE LOS INVESTIGADORES

    private String mensajeErrorEvaluador = "";
    private String mensajeErrorEvaluadores = "";
    private String tipoEvaluador;
    private String documento;
    private String nombres = "";
    private String apellidos = "";

    private List listaTipoDocumento;
    private TipoDocumento tipoDocumentoInv;
    private SelectItem[] tipoDocumentoItem;
    private SelectItem[] itemsTipoEvaluador;

    private SelectItem[] areasTematicasItem;
    private String areaExperticia;
    private List listaAreasTematicas;
    private UIData tablaEleccionInternos;
    private UIData tablaEvaluadorInvExterno;

    public ManejadorEvaluadores() {
        super();
        idManejador = EVALUADORES;

        titulo1 = "Proyecto:";
        titulo2 = "Búsqueda de Integrantes del Proyecto";

        posibleEvaluador = new PosibleEvaluador();
        listaEvaluadoresVista = new ArrayList<EvaluadorVista>();
        listaEleccionInternos = new ArrayList();
        listaEvaluadorInvestigadorExterno = new ArrayList();
        listaEvaluadorInvExterno = new ArrayList();
        listaEvaluadoresExternos = new ArrayList();
        listaEvaluadoresInternos = new ArrayList();
        listaAreasTematicas = new ArrayList();
        tipoEvaluador = "I";
        listaTipoDocumento = servicioGeneral.obtenerListaObjetos("TipoDocumento");
        tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
        for (int i = 0; i < listaTipoDocumento.size(); i++) {
            TipoDocumento td = (TipoDocumento) listaTipoDocumento.get(i);
            tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
        }
        tipoDocumentoInv = (TipoDocumento) listaTipoDocumento.get(0);
        proyectoActual = servicioProyecto.obtenerProyecto(proyectoActual.getId(),
                ProyectoDAOHibernate.POSIBLES_EVALUADORES);
        // DESPUES DE OBTENER EL PROYECTO SE CARGA LA LISTA DE POSIBLES
        // EVALUADORES INTERNOS
        List listaAuxiliar = new ArrayList();
        listaEvaluadoresInternos
                .addAll(volverInvestigadorInternoVista(proyectoActual.getPosiblesEvaluadoresInternos()));
        listaEvaluadorInvestigadorExterno
                .addAll(volverInvestigadorExternoVista(proyectoActual.getPosiblesEvaluadoresInvestigadoresExternos()));
        listaAuxiliar.addAll(proyectoActual.getPosiblesEvaluadoresInternos());
        for (int i = 0; i < listaAuxiliar.size(); i++) {
            InvestigadorInterno invI = servicioPersona
                    .obtenerInvestigadorClasificacionConocimiento(((InvestigadorInterno) listaAuxiliar.get(i)).getId());
            Iterator it = invI.getClasificacionesConocimiento().iterator();
            if (it.hasNext()) {
                listaEvaluadoresVista.add(new EvaluadorVista(invI, ((ClasificacionConocimiento) it.next()).getId()));
            } else {
                listaEvaluadoresVista.add(new EvaluadorVista(invI, ""));
            }
        }
        // investigadores externos evaluadores
        listaAuxiliar.clear();
        listaAuxiliar.addAll(proyectoActual.getPosiblesEvaluadoresInvestigadoresExternos());
        System.out.println("posibles evaludores externos inv " + listaAuxiliar.size());
        for (int i = 0; i < listaAuxiliar.size(); i++) {
            InvestigadorExterno invI = servicioPersona.obtenerInvestigadorExternoClasificacionConocimiento(
                    ((InvestigadorExterno) listaAuxiliar.get(i)).getId());
            Iterator it = invI.getClasificacionesConocimiento().iterator();
            if (it.hasNext()) {
                listaEvaluadoresVista.add(new EvaluadorVista(invI, ((ClasificacionConocimiento) it.next()).getId()));
            } else {
                listaEvaluadoresVista.add(new EvaluadorVista(invI, ""));
            }
        }

        // se llena los tipos de evaluadores
        itemsTipoEvaluador = new SelectItem[2];
        itemsTipoEvaluador[0] = new SelectItem("I", "Interno");
        itemsTipoEvaluador[1] = new SelectItem("E", "Externo");

        // DESPUES DE OBTENER EL PROYECTO SE CARGA LA LISTA DE POSIBLES
        // EVALUADORES EXTERNOS
        listaAuxiliar.clear();
        listaEvaluadoresExternos.addAll(proyectoActual.getPosiblesEvaluadoresExternos());
        listaAuxiliar.addAll(proyectoActual.getPosiblesEvaluadoresExternos());
        for (int i = 0; i < listaAuxiliar.size(); i++) {
            listaEvaluadoresVista.add(new EvaluadorVista((PosibleEvaluador) listaAuxiliar.get(i)));
        }
        listaAreasTematicas.addAll(proyectoActual.getClasificacionConocimiento());
        obtenerListasAreas();

        if (proyectoActual.getModalidad().getTipo().getId().compareTo("PN") == 0) {
            titulo1 = "Programa:";
            titulo2 = "Integrantes del programa";
        } else {
            titulo1 = "Proyecto:";
            titulo2 = "Integrantes del proyecto de investigación";
        }

    }

    // DEFINICION DE FUNCIONES BASICAS
    protected void cargarValoresIniciales() {
    }

    public String atras() {
        // ///////MODIFICADO GIOVANNI
        ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
        boolean bandera = false;
        if (man.getItemProyecto() != null) {
            MenuItem lis[] = man.getMenuItemArray();
            if (lis != null) {
                for (int i = lis.length - 1; i >= 0; i--) {
                    if (bandera) {
                        if (lis[i].isRendered()) {

                            return lis[i].getOutcome();
                        }
                    }

                    if (lis[i].getOutcome().equals("irEvaluadores")) {
                        bandera = true;
                    }

                }
            }
        }
        // ////////////////
        return "irProductos";
    }

    public String salir() {
        sesion.removeAttribute("proyecto");
        borrarManejadoresInsercionProyecto();
        return "misProyectos";
    }

    public String salirGuardar() {
        if (validarEvaluadores()) {

            // ///MODIFICADO GIOVANNI
            String link = "";
            ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
            boolean bandera = false;
            int pos = 0;
            if (man.getItemProyecto() != null) {
                MenuItem lis[] = man.getMenuItemArray();
                if (lis != null) {
                    for (int i = 0; i < lis.length; i++) {
                        if (bandera) {
                            if (lis[i].isRendered()) {
                                sesion.removeAttribute("manejadorMenuFormularios");
                                link = lis[i].getOutcome();
                                break;
                            }
                        }

                        if (lis[i].getOutcome().equals("irEvaluadores")) {
                            bandera = true;
                        }
                        if (lis[i].isRendered()) {
                            pos++;
                        }
                    }
                }
            }

            if ((proyectoActual.getEstadoProyecto().getId()).equals("I")
                    && (pos - 1) >= proyectoActual.getFase().intValue()) {
                proyectoActual.setFase(new Integer(proyectoActual.getFase().intValue() + 1));
            }
            // SE GUARDAN LOS INVESTIGADORES EXTERNOS Y SU ASOCIACION A LAS
            // LINEAS DE INVESTIGACION
            for (int i = 0; i < proyectoActual.getListaEvaluadoresInternos().size(); i++) {
                servicioGeneral.guardarObjeto(proyectoActual.getListaEvaluadoresInternos().get(i));
            }

            if (proyectoActual.getId() != null) {
                // Ing. Wilver Alexander Martínez Martínez -wam²
                // Cambio - Registro de cambios
                Persona personaAux = new Persona();
                personaAux = (Persona) sesion.getAttribute("persona");

                Formulario formulario = new Formulario();
                List listaFormulario = new ArrayList();

                listaFormulario = servicioGeneral.obtenerListaObjetos("Formulario where id ='260'");
                formulario = (Formulario) listaFormulario.get(0);

                HistoricoFormularioProyecto historicoFormualrioProyecto = new HistoricoFormularioProyecto();
                historicoFormualrioProyecto.setDocPersona(personaAux.getId().getDocumento());
                historicoFormualrioProyecto.setTipoDocumentoPersona(personaAux.getId().getTipoDocumento());
                historicoFormualrioProyecto.setFormulario(formulario);
                historicoFormualrioProyecto.setProyecto(proyectoActual);
                historicoFormualrioProyecto.setFechaCambio(new Date());
                servicioGeneral.guardarObjeto(historicoFormualrioProyecto);
            }

            servicioProyecto.ingresarProyecto(proyectoActual);
            sesion.removeAttribute("proyecto");
            sesion.removeAttribute("manejadorMenuFormularios");
            borrarManejadoresInsercionProyecto();
            return "misProyectos";
        }
        return "";
    }

    public String siguiente() {
        if (validarEvaluadores())/*
                                  * VALIDAR QUE LA LISTA DE INVESTIGADORES TENGA
                                  * ALGO
                                  */
        {

            // ///MODIFICADO GIOVANNI
            String link = "";
            ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
            boolean bandera = false;
            int pos = 0;
            if (man.getItemProyecto() != null) {
                MenuItem lis[] = man.getMenuItemArray();
                if (lis != null) {
                    for (int i = 0; i < lis.length; i++) {
                        if (bandera) {
                            if (lis[i].isRendered()) {
                                link = lis[i].getOutcome();
                                break;
                            }
                        }

                        if (lis[i].getOutcome().equals("irEvaluadores")) {
                            bandera = true;
                        }
                        if (lis[i].isRendered()) {
                            pos++;
                        }
                    }
                }
            }

            if ((proyectoActual.getEstadoProyecto().getId()).equals("I")
                    && (pos - 1) == proyectoActual.getFase().intValue()) {
                proyectoActual.setFase(new Integer((proyectoActual.getFase()).intValue() + 1));
            }
            // SE GUARDAN LOS INVESTIGADORES EXTERNOS Y SU ASOCIACION A LAS
            // LINEAS DE INVESTIGACION
            for (int i = 0; i < proyectoActual.getListaEvaluadoresInternos().size(); i++) {
                servicioGeneral.guardarObjeto(proyectoActual.getListaEvaluadoresInternos().get(i));
            }
            System.out.println("eval " + proyectoActual.getPosiblesEvaluadoresInvestigadoresExternos().size());

            if (proyectoActual.getId() != null) {
                // Ing. Wilver Alexander Martínez Martínez -wam²
                // Cambio - Registro de cambios
                Persona personaAux = new Persona();
                personaAux = (Persona) sesion.getAttribute("persona");

                Formulario formulario = new Formulario();
                List listaFormulario = new ArrayList();

                listaFormulario = servicioGeneral.obtenerListaObjetos("Formulario where id ='260'");
                formulario = (Formulario) listaFormulario.get(0);

                HistoricoFormularioProyecto historicoFormualrioProyecto = new HistoricoFormularioProyecto();
                historicoFormualrioProyecto.setDocPersona(personaAux.getId().getDocumento());
                historicoFormualrioProyecto.setTipoDocumentoPersona(personaAux.getId().getTipoDocumento());
                historicoFormualrioProyecto.setFormulario(formulario);
                historicoFormualrioProyecto.setProyecto(proyectoActual);
                historicoFormualrioProyecto.setFechaCambio(new Date());
                servicioGeneral.guardarObjeto(historicoFormualrioProyecto);
            }

            servicioProyecto.ingresarProyecto(proyectoActual);
            sesion.setAttribute("proyecto", proyectoActual);

            sesion.removeAttribute("manejadorEvaluadores");

            // ///////MODIFICADO GIOVANNI
            man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
            bandera = false;
            if (man.getItemProyecto() != null) {
                MenuItem lis[] = man.getMenuItemArray();
                if (lis != null) {
                    for (int i = 0; i < lis.length; i++) {
                        if (bandera) {
                            if (lis[i].isRendered()) {
                                sesion.removeAttribute("manejadorMenuFormularios");
                                borrarManejadoresInsercionProyecto();
                                return lis[i].getOutcome();
                            }
                        }

                        if (lis[i].getOutcome().equals("irEvaluadores")) {
                            bandera = true;
                        }

                    }
                }
            }
            // ////////////////
            sesion.removeAttribute("manejadorMenuFormularios");
            if (proyectoActual.getModalidad().getTipo().getId().equals(TipoModalidad.JORNADA_DOCENTE))
                return "irSubirArchivo";
            return "irFuentes";
        }
        return "";
    }

    // FUNCIONES ESPECIFICAS DE LA CLASE
    public void adicionarPosibleEvaluador() {
        // SE OBTIENE EL ID FINAL DEL AREA DE EXPERTICIA

        // SE DETERMINA SI EL POSIBLE EVALUADOR ES INTERNO O EXTERNO

        if (tipoEvaluador.equals("I")) {
            Iterator it = listaEleccionInternos.iterator();
            boolean elegibleEncontrado = false;
            while (it.hasNext() && !elegibleEncontrado) {
                InvestigadorInternoVista inviv = (InvestigadorInternoVista) it.next();
                if (inviv.isElegible() && !investigadorPerteneceGrupo(inviv.getInvestigadorInterno())) {
                    // SE ASOCIA LA LINEA CORRESPONDIENTE AL INVESTIGADOR
                    // INTERNO
                    ClasificacionConocimiento cc = buscarAreaxId(areaExperticia);
                    Investigador inv = inviv.getInvestigadorInterno();
                    InvestigadorInterno invI = servicioPersona
                            .obtenerInvestigadorClasificacionConocimiento(inv.getId());
                    if (invI != null) {
                        invI.adicionarClasificacionConocimiento(cc);
                        inviv.setInvestigadorInterno(invI);
                    }

                    Investigador iiP = servicioProyecto.estaInvestigadorEnProyecto(invI, proyectoActual.getId());
                    if (iiP != null) {
                        FacesContext.getCurrentInstance().addMessage("msgs",
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "No puede ser evaluador ya que es investigador de el proyecto", ""));
                        sesion.setAttribute("mensaje", "No puede ser evaluador ya que es investigador de el proyecto");
                        return;
                    }
                    // SE GUARDA EN LA LISTA DE EVALUADORES VISTA
                    listaEvaluadoresInternos.add(inviv);
                    listaEvaluadoresVista.add(new EvaluadorVista(inviv.getInvestigadorInterno(), areaExperticia));
                    // SE ADICIONA EN EL PROYECTO EL INVESTIGADOR INTERNO
                    proyectoActual.adicionarPosibleEvaluadorInterno(inviv.getInvestigadorInterno());
                    elegibleEncontrado = true;
                }
            }
            listaEleccionInternos.clear();

            Iterator itE = listaEvaluadorInvExterno.iterator();
            elegibleEncontrado = false;
            while (itE.hasNext() && !elegibleEncontrado) {
                InvestigadorExternoVista inviv = (InvestigadorExternoVista) itE.next();
                if (inviv.isElegible() && !investigadorPerteneceGrupo(inviv.getInvestigadorExterno())) {
                    // SE ASOCIA LA LINEA CORRESPONDIENTE AL INVESTIGADOR
                    // INTERNO
                    ClasificacionConocimiento cc = buscarAreaxId(areaExperticia);
                    Investigador inv = inviv.getInvestigadorExterno();

                    InvestigadorExterno invI = (InvestigadorExterno) servicioPersona
                            .obtenerInvestigadorExternoClasificacionConocimiento(inv.getId());
                    if (invI != null) {
                        invI.adicionarClasificacionConocimiento(cc);
                        inviv.setInvestigadorExterno(invI);
                    }

                    Investigador iiP = servicioProyecto.estaInvestigadorEnProyecto(invI, proyectoActual.getId());
                    if (iiP != null) {
                        FacesContext.getCurrentInstance().addMessage("msgs",
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "No puede ser evaluador ya que es investigador del proyecto "
                                                + iiP.getId().getDocumento(),
                                        ""));
                        sesion.setAttribute("mensaje", "No puede ser evaluador ya que es investigador de el proyecto");
                        return;
                    }
                    // SE GUARDA EN LA LISTA DE EVALUADORES VISTA
                    listaEvaluadorInvestigadorExterno.add(inviv);
                    listaEvaluadoresVista.add(new EvaluadorVista(inviv.getInvestigadorExterno(), areaExperticia));
                    // SE ADICIONA EN EL PROYECTO EL INVESTIGADOR INTERNO
                    proyectoActual.adicionarPosibleEvaluadorInvestigadorExterno(inviv.getInvestigadorExterno());
                    elegibleEncontrado = true;
                }
            }
            listaEvaluadorInvExterno.clear();
        } else {
            if (validarEvaluador()) {

                ClasificacionConocimiento clas = new ClasificacionConocimiento();
                clas.setId(areaExperticia);
                posibleEvaluador.setClasificacionConocimiento(clas);

                // SE GUARDA EN LA LISTA DE EVALUADORES VISTA
                listaEvaluadoresExternos.add(posibleEvaluador);
                listaEvaluadoresVista.add(new EvaluadorVista(posibleEvaluador));
                // SE ADICIONA EN EL PROYECTO EL INVESTIGADOR EXTERNO
                proyectoActual.adicionarPosibleEvaluador(posibleEvaluador);
                posibleEvaluador = new PosibleEvaluador();
                mensajeErrorEvaluadores = "";
                tipoEvaluador = "I";
            }
            listaEleccionInternos = new ArrayList();
            obtenerListasAreas();
        }
    }

    public void adicionarPosibleEvaluadorInterno() {
        if (!investigadorPerteneceGrupo(peiSeleccionado.getInvestigadorInterno())) {
            // SE ASOCIA LA LINEA CORRESPONDIENTE AL INVESTIGADOR INTERNO
            ClasificacionConocimiento cc = buscarAreaxId(areaExperticia);
            Investigador inv = peiSeleccionado.getInvestigadorInterno();
            InvestigadorInterno invI = servicioPersona.obtenerInvestigadorClasificacionConocimiento(inv.getId());
            if (invI != null) {
                invI.adicionarClasificacionConocimiento(cc);
                peiSeleccionado.setInvestigadorInterno(invI);
            }

            Investigador iiP = servicioProyecto.estaInvestigadorEnProyecto(invI, proyectoActual.getId());
            if (iiP != null) {
                FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No puede ser evaluador ya que es investigador de el proyecto", ""));
                sesion.setAttribute("mensaje", "No puede ser evaluador ya que es investigador de el proyecto");
                return;
            }
            // SE GUARDA EN LA LISTA DE EVALUADORES VISTA
            if (!listaEvaluadoresInternos.contains(peiSeleccionado)) {
                listaEvaluadoresInternos.add(peiSeleccionado);
                listaEvaluadoresVista.add(new EvaluadorVista(peiSeleccionado.getInvestigadorInterno(), areaExperticia));
                proyectoActual.adicionarPosibleEvaluadorInterno(peiSeleccionado.getInvestigadorInterno());

            }
            // SE ADICIONA EN EL PROYECTO EL INVESTIGADOR INTERNO

        }
        listaEleccionInternos.clear();
        listaEvaluadorInvExterno.clear();
    }

    public void adicionarPosibleEvaluadorInvExterno() {
        InvestigadorExternoVista inviv = (InvestigadorExternoVista) tablaEvaluadorInvExterno.getRowData();
        if (!investigadorPerteneceGrupo(inviv.getInvestigadorExterno())) {
            // SE ASOCIA LA LINEA CORRESPONDIENTE AL INVESTIGADOR INTERNO
            ClasificacionConocimiento cc = buscarAreaxId(areaExperticia);
            Investigador inv = inviv.getInvestigadorExterno();

            InvestigadorExterno invI = (InvestigadorExterno) servicioPersona
                    .obtenerInvestigadorExternoClasificacionConocimiento(inv.getId());
            if (invI != null) {
                invI.adicionarClasificacionConocimiento(cc);
                inviv.setInvestigadorExterno(invI);
            }

            Investigador iiP = servicioProyecto.estaInvestigadorEnProyecto(invI, proyectoActual.getId());
            if (iiP != null) {
                FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No puede ser evaluador ya que es investigador del proyecto " + iiP.getId().getDocumento(),
                        ""));
                sesion.setAttribute("mensaje", "No puede ser evaluador ya que es investigador de el proyecto");
                return;
            }
            // SE GUARDA EN LA LISTA DE EVALUADORES VISTA
            if (!listaEvaluadorInvestigadorExterno.contains(inviv)) {
                listaEvaluadorInvestigadorExterno.add(inviv);
                listaEvaluadoresVista.add(new EvaluadorVista(inviv.getInvestigadorExterno(), areaExperticia));
                // SE ADICIONA EN EL PROYECTO EL INVESTIGADOR INTERNO
                proyectoActual.adicionarPosibleEvaluadorInvestigadorExterno(inviv.getInvestigadorExterno());
            }
        }
        listaEleccionInternos.clear();
        listaEvaluadorInvExterno.clear();
    }

    private boolean investigadorPerteneceGrupo(Investigador ii) {
        Object o = proyectoActual.getModalidad();
        if (o != null) {
            System.out.println(o.getClass().getName());
        }
        if (o instanceof Convocatoria) {

            if (((Convocatoria) proyectoActual.getModalidad()).getEsParaGrupos() != null
                    && ((Convocatoria) proyectoActual.getModalidad()).getEsParaGrupos().booleanValue()) {
                Iterator it = proyectoActual.getGrupos().iterator();
                Grupo g = null;
                if (it.hasNext()) {
                    g = (Grupo) it.next();
                    Iterator it2 = servicioGrupo.obtenerInvestigadoresGrupo(g.getId()).iterator();
                    while (it2.hasNext()) {
                        Investigador i = ((InvestigadorGrupo) it2.next()).getInvestigador();
                        if (i.getId().getTipoDocumento().equals(ii.getId().getTipoDocumento())
                                && i.getId().getDocumento().equals(ii.getId().getDocumento())) {
                            FacesContext.getCurrentInstance().addMessage("msgs",
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                            "El investigador no puede ser adicionado, por que pertenece al grupo que inscribe el proyecto",
                                            ""));
                            return true;
                        }
                    }
                }
            }
        }
        mensajeErrorEvaluador = "";
        return false;
    }

    public void buscarPosibleEvaluadorInterno() {

        listaEleccionInternos.clear();
        listaEvaluadorInvExterno.clear();

        if (nombres.length() > 0 || apellidos.length() > 0) {
            List aux = servicioPersona.obtenerInvestigadoresPorNombresYApellidos(nombres, apellidos,false);
            for (int i = 0; i < aux.size(); i++) {
                Object obj = aux.get(i);
                Investigador inv = (Investigador) obj;

                if (servicioPersona.esInvestigadorInterno(inv.getId())) {
                    InvestigadorInterno ii = servicioPersona.obtenerInvestigadorInterno(inv.getId());
                    listaEleccionInternos.add(new InvestigadorInternoVista((InvestigadorInterno) ii));
                }
                if (servicioPersona.esInvestigadorExterno(inv.getId())) {
                    InvestigadorExterno ie = servicioPersona.obtenerInvestigadorExterno(inv.getId());
                    System.out.println(ie.getNombre1());
                    listaEvaluadorInvExterno.add(new InvestigadorExternoVista((InvestigadorExterno) ie));
                }

            }
        }
        if (listaEleccionInternos.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "No se ha encontrado el investigador interno de nombre " + nombres + " " + apellidos, ""));
        }
    }

    public void verDetalle() {
        nombreDetalle = posibleEvaluadorSeleccionado.getNombre();
        emailDetalle = posibleEvaluadorSeleccionado.getEmail();
        telefonoDetalle = posibleEvaluadorSeleccionado.getTelefono();
        experticiaDetalle = getNombreExperticia();
        if (posibleEvaluadorSeleccionado.getTipoEvaluador().equals("I"))
            tipoEvaluadorDetalle = "Interno";
        else
            tipoEvaluadorDetalle = "Externo";

    }

    public void eliminarEvaluadorVista() {

        if (posibleEvaluadorSeleccionado.getTipoEvaluador().equals("I")) {

            Object o = buscarEvaluadorxNombre(posibleEvaluadorSeleccionado.getNombre(), "I");

            if (o != null && o instanceof InvestigadorInterno) {
                InvestigadorInterno invI = (InvestigadorInterno) o;

                ArrayList listaborrar = new ArrayList();
                for (Iterator iterator = listaEvaluadoresInternos.iterator(); iterator.hasNext();) {
                    InvestigadorInternoVista invInt = (InvestigadorInternoVista) iterator.next();
                    if (invInt.getInvestigadorInterno().getId().equals(invI.getId())) {
                        listaborrar.add(invInt);
                    }

                }
                for (Iterator iterator = listaborrar.iterator(); iterator.hasNext();) {
                    listaEvaluadoresInternos.remove(iterator.next());

                }
                proyectoActual.borrarPosibleEvaluadorInterno(invI);
            }
            if (o != null && o instanceof InvestigadorExterno) {
                InvestigadorExterno invE = (InvestigadorExterno) buscarEvaluadorxNombre(
                        posibleEvaluadorSeleccionado.getNombre(), "I");

                ArrayList listaborrar = new ArrayList();
                for (Iterator iterator = listaEvaluadorInvestigadorExterno.iterator(); iterator.hasNext();) {
                    InvestigadorExternoVista invInt = (InvestigadorExternoVista) iterator.next();
                    if (invInt.getInvestigadorExterno().getId().equals(invE.getId())) {
                        listaborrar.add(invInt);
                    }

                }
                for (Iterator iterator = listaborrar.iterator(); iterator.hasNext();) {
                    listaEvaluadorInvestigadorExterno.remove(iterator.next());

                }
                proyectoActual.borrarPosibleEvaluadorInvestigadorExterno(invE);
            }
        } else {
            PosibleEvaluador pev = (PosibleEvaluador) buscarEvaluadorxNombre(posibleEvaluadorSeleccionado.getNombre(),
                    "E");
            listaEvaluadoresExternos.remove(pev);
            proyectoActual.borrarPosibleEvaluador(pev);
        }
        this.listaEvaluadoresVista.remove(posibleEvaluadorSeleccionado);

    }

    private void obtenerListasAreas() {
        areasTematicasItem = new SelectItem[listaAreasTematicas.size()];
        for (int i = 0; i < listaAreasTematicas.size(); i++) {
            ClasificacionConocimiento cc = (ClasificacionConocimiento) listaAreasTematicas.get(i);
            areasTematicasItem[i] = new SelectItem(cc.getId(), cc.getNombre());
        }
        areaExperticia = ((ClasificacionConocimiento) listaAreasTematicas.get(0)).getId();
    }

    // VALIDADORES
    private boolean validarEvaluador() {
        mensajeErrorEvaluador = "";
        if (posibleEvaluador.getNombre() == null || (posibleEvaluador.getNombre()).length() == 0) {
            FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Debe ingresar el nombre del posible evaluador ", ""));
            return false;
        }
        if (posibleEvaluador.getApellido1() == null || (posibleEvaluador.getApellido1()).length() == 0) {
            FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Debe ingresar el primer apellido del posible evaluador", ""));
            return false;
        }

        if (posibleEvaluador.getEmail() == null || (posibleEvaluador.getEmail()).length() == 0) {
            FacesContext.getCurrentInstance().addMessage("msgs",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe ingresar el email del posible evaluador ", ""));
            return false;
        }
        return true;
    }

    private boolean validarEvaluadores() {

        if (proyectoActual.getModalidad().getTipo().getId().equals(TipoModalidad.CONVOCATORIA)
                || proyectoActual.getModalidad().getTipo().getId().equals(TipoModalidad.SENA)) {
            if (listaEvaluadoresInternos.isEmpty() && listaEvaluadoresExternos.isEmpty()
                    && listaEvaluadorInvestigadorExterno.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No se encuentran posibles evaluadores asociados al proyecto ", ""));
                return false;
            } else {
                if ((listaEvaluadoresInternos.size() + listaEvaluadoresExternos.size()
                        + listaEvaluadorInvestigadorExterno.size()) < 3) {
                    FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Debe haber mínimo tres(3) posibles evaluadores asociados al proyecto ", ""));
                    return false;
                }
            }
            mensajeErrorEvaluadores = " ";
            return true;
        }

        mensajeErrorEvaluadores = " ";
        return true;
    }

    private ClasificacionConocimiento buscarAreaxId(String idArea) {
        List listaAuxiliar = new ArrayList();
        listaAuxiliar.addAll(listaAreasTematicas);
        Iterator it = listaAuxiliar.iterator();
        ClasificacionConocimiento c = null;
        boolean clasificacionEncontrada = false;
        while (it.hasNext() && !clasificacionEncontrada) {
            c = (ClasificacionConocimiento) it.next();
            if (c.getId().equals(idArea)) {
                clasificacionEncontrada = true;
            }
        }
        listaAuxiliar = null;
        return c;
    }

    private Object buscarEvaluadorxNombre(String nombre, String tipoInvestigador) {
        if (tipoInvestigador.equals("I")) {
            for (int i = 0; i < listaEvaluadoresInternos.size(); i++) {
                Object o = listaEvaluadoresInternos.get(i);
                System.out.println(o.getClass().getName());
                InvestigadorInternoVista invI = (InvestigadorInternoVista) o;
                String nombreAux = invI.getInvestigadorInterno().getNombre1() + " "
                        + invI.getInvestigadorInterno().getNombre2() + " "
                        + invI.getInvestigadorInterno().getApellido1() + " "
                        + invI.getInvestigadorInterno().getApellido2();
                if (nombreAux.equals(nombre)) {
                    return invI.getInvestigadorInterno();
                }
            }
            for (int i = 0; i < listaEvaluadorInvestigadorExterno.size(); i++) {
                Object o = listaEvaluadorInvestigadorExterno.get(i);
                System.out.println(o.getClass().getName());
                InvestigadorExternoVista invI = (InvestigadorExternoVista) o;
                String nombreAux = invI.getInvestigadorExterno().getNombre1() + " "
                        + invI.getInvestigadorExterno().getNombre2() + " "
                        + invI.getInvestigadorExterno().getApellido1() + " "
                        + invI.getInvestigadorExterno().getApellido2();
                if (nombreAux.equals(nombre)) {
                    return invI.getInvestigadorExterno();
                }
            }
        } else {
            for (int i = 0; i < listaEvaluadoresExternos.size(); i++) {
                PosibleEvaluador pe = (PosibleEvaluador) listaEvaluadoresExternos.get(i);
                String nombreAux = pe.getNombre() + " " + pe.getApellido1() + " " + pe.getApellido2();
                if (nombreAux.equals(nombre)) {
                    return pe;
                }
            }
        }
        return null;
    }

    public void setListaObjetos() {

        try {
            this.listaInvestigadores = new ArrayList();
            String sql = "";
            String join = "";

            if (this.idInvestigador.length() > 0) {

                if (sql.length() > 0) {
                    sql = sql + " and ";
                }
                sql = sql + " (inv.id.documento = '" + this.idInvestigador + "')";

            }

            // nombres y apellidos

            if (this.apellidoBusqueda.length() > 0 || this.nombreBusqueda.length() > 0) {

                boolean bandera = false;

                if (this.nombreBusqueda.length() > 0) {
                    this.nombreBusqueda = this.nombreBusqueda.trim();
                    if (this.nombreBusqueda.indexOf(" ") > 0) {
                        String cadenaNombres[] = this.nombreBusqueda.split(" ");
                        String tempNombre1 = "";
                        String tempNombre2 = "";
                        if (cadenaNombres != null && cadenaNombres.length > 0) {
                            if (sql.length() > 0) {
                                sql = sql + " and ";
                            }
                            if (cadenaNombres.length == 1) {

                                tempNombre1 = cadenaNombres[0];
                                tempNombre1 = tempNombre1.trim();
                                sql = sql + " (upper(inv.nombre1) like '%" + tempNombre1.toUpperCase() + "%'"
                                        + " or upper(inv.nombre2) like '%" + tempNombre1.toUpperCase() + "%') ";
                                bandera = true;
                            }
                            if (cadenaNombres.length == 2) {
                                tempNombre1 = cadenaNombres[0];
                                tempNombre2 = cadenaNombres[1];
                                tempNombre1 = tempNombre1.trim();
                                tempNombre2 = tempNombre2.trim();
                                sql = sql + " (upper(inv.nombre1) like '%" + tempNombre1.toUpperCase() + "%'"
                                        + " or upper(inv.nombre2) like '%" + tempNombre2.toUpperCase() + "%' )";
                                bandera = true;
                            }
                        }
                    } else {
                        if (sql.length() > 0) {
                            sql = sql + " and ";
                        }
                        sql = sql + " (upper(inv.nombre1) like '%" + nombreBusqueda.toUpperCase() + "%'"
                                + " or upper(inv.nombre2) like '%" + nombreBusqueda.toUpperCase() + "%') ";
                        bandera = true;
                    }
                }

                if (this.apellidoBusqueda.length() > 0) {
                    this.apellidoBusqueda = this.apellidoBusqueda.trim();
                    if (this.apellidoBusqueda.indexOf(" ") > 0) {
                        String cadenaApellidos[] = this.apellidoBusqueda.split(" ");
                        String tempApellido1 = "";
                        String tempApellido2 = "";

                        if (cadenaApellidos != null && cadenaApellidos.length > 0) {
                            if (sql.length() > 0) {
                                sql = sql + " and ";
                            }
                            if (cadenaApellidos.length == 1) {
                                tempApellido1 = cadenaApellidos[0];
                                tempApellido1 = tempApellido1.trim();
                                sql = sql + "  (upper(inv.apellido1) like '%" + tempApellido1.toUpperCase() + "%'"
                                        + " or upper(inv.apellido2) like '%" + tempApellido1.toUpperCase() + "%') ";
                                bandera = true;
                            }
                            if (cadenaApellidos.length == 2) {
                                tempApellido1 = cadenaApellidos[0];
                                tempApellido2 = cadenaApellidos[1];
                                tempApellido1 = tempApellido1.trim();
                                tempApellido2 = tempApellido2.trim();
                                sql = sql + " (upper(inv.apellido1) like '%" + tempApellido1.toUpperCase() + "%'"
                                        + " or upper(inv.apellido2) like '%" + tempApellido2.toUpperCase() + "%') ";
                                bandera = true;
                            }

                        }
                    } else {
                        if (sql.length() > 0) {
                            sql = sql + " and ";
                        }
                        sql = sql + "  (upper(inv.apellido1) like '%" + apellidoBusqueda.toUpperCase() + "%'"
                                + " or upper(inv.apellido2) like '%" + apellidoBusqueda.toUpperCase() + "%') ";
                        bandera = true;
                    }
                }
            }

            // termina nombres

            if (sql.length() > 0) {
                sql = sql + " and ";
            }
            sql = sql + " (inv.interno = 'S')";

            if (sql.length() > 0) {
                sql = " where " + sql;
            }

            if (join.length() > 0) {
                sql = join + sql;
            }

            List listaInvestigadoresAux = this.servicioPersona.obtenerInvestigadores(sql, false, null);

            for (int i = 0; i < listaInvestigadoresAux.size(); i++) {

                InvestigadorInterno ii = (InvestigadorInterno) listaInvestigadoresAux.get(i);
                this.listaInvestigadores.add(new InvestigadorInternoVista((InvestigadorInterno) ii));
            }

            if (this.listaInvestigadores.size() <= 0) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List volverInvestigadorInternoVista(Set listaInvestigadoresInternos2) {
        Iterator itII = null;
        List listaInvestigadoresIVista = new Vector();
        if (listaInvestigadoresInternos2 != null) {
            itII = listaInvestigadoresInternos2.iterator();
            while (itII.hasNext()) {
                InvestigadorInterno ii = (InvestigadorInterno) itII.next();
                listaInvestigadoresIVista.add(new InvestigadorInternoVista(ii));
            }
        }
        return listaInvestigadoresIVista;

    }

    public List volverInvestigadorExternoVista(Set listaInvestigadoresInternos2) {
        Iterator itII = null;
        List listaInvestigadoresIVista = new Vector();
        if (listaInvestigadoresInternos2 != null) {
            itII = listaInvestigadoresInternos2.iterator();
            while (itII.hasNext()) {
                InvestigadorExterno ii = (InvestigadorExterno) itII.next();
                listaInvestigadoresIVista.add(new InvestigadorExternoVista(ii));
            }
        }
        return listaInvestigadoresIVista;

    }

    // METODOS SET Y GET
    public String getMensajeErrorEvaluador() {
        return mensajeErrorEvaluador;
    }

    public void setMensajeErrorEvaluador(String mensajeErrorEvaluador) {
        this.mensajeErrorEvaluador = mensajeErrorEvaluador;
    }

    public String getMensajeErrorEvaluadores() {
        return mensajeErrorEvaluadores;
    }

    public void setMensajeErrorEvaluadores(String mensajeErrorEvaluadores) {
        this.mensajeErrorEvaluadores = mensajeErrorEvaluadores;
    }

    public PosibleEvaluador getPosibleEvaluador() {
        return posibleEvaluador;
    }

    public void setPosibleEvaluador(PosibleEvaluador posibleEvaluador) {
        this.posibleEvaluador = posibleEvaluador;
    }

    public DataTable getTablaEvaluadores() {
        return tablaEvaluadores;
    }

    public void setTablaEvaluadores(DataTable tablaEvaluadores) {
        this.tablaEvaluadores = tablaEvaluadores;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public List getListaTipoDocumento() {
        return listaTipoDocumento;
    }

    public void setListaTipoDocumento(List listaTipoDocumento) {
        this.listaTipoDocumento = listaTipoDocumento;
    }

    public TipoDocumento getTipoDocumentoInv() {
        return tipoDocumentoInv;
    }

    public void setTipoDocumentoInv(TipoDocumento tipoDocumentoInv) {
        this.tipoDocumentoInv = tipoDocumentoInv;
    }

    public SelectItem[] getTipoDocumentoItem() {
        return tipoDocumentoItem;
    }

    public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
        this.tipoDocumentoItem = tipoDocumentoItem;
    }

    public String getTipoEvaluador() {
        return tipoEvaluador;
    }

    public void setTipoEvaluador(String tipoEvaluador) {
        this.tipoEvaluador = tipoEvaluador;
    }

    public List<EvaluadorVista> getListaEvaluadoresVista() {
        return listaEvaluadoresVista;
    }

    public void setListaEvaluadoresVista(List<EvaluadorVista> listaEvaluadoresVista) {
        this.listaEvaluadoresVista = listaEvaluadoresVista;
    }

    public String getNombreExperticia() {
        // EvaluadorVista ev = (EvaluadorVista) tablaEvaluadores.getRowData();

        /**/
        if (posibleEvaluadorSeleccionado.getIdExperticia().length() != 0) {
            ClasificacionConocimiento cc = (ClasificacionConocimiento) servicioGeneral
                    .obtenerObjeto(new ClasificacionConocimiento(), posibleEvaluadorSeleccionado.getIdExperticia());
            if (cc != null) {
                return cc.getNombre();
            } else {
                posibleEvaluadorSeleccionado.getIdExperticia();
            }
        }
        return "";
    }

    public List getListaEleccionInternos() {
        return listaEleccionInternos;
    }

    public void setListaEleccionInternos(List listaEleccionInternos) {
        this.listaEleccionInternos = listaEleccionInternos;
    }

    public InvestigadorInterno getPosibleEvaluadorInterno() {
        return posibleEvaluadorInterno;
    }

    public void setPosibleEvaluadorInterno(InvestigadorInterno posibleEvaluadorInterno) {
        this.posibleEvaluadorInterno = posibleEvaluadorInterno;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getAreaExperticia() {
        return areaExperticia;
    }

    public void setAreaExperticia(String areaExperticia) {
        this.areaExperticia = areaExperticia;
    }

    public SelectItem[] getAreasTematicasItem() {
        return areasTematicasItem;
    }

    public void setAreasTematicasItem(SelectItem[] areasTematicasItem) {
        this.areasTematicasItem = areasTematicasItem;
    }

    public InvestigadorInterno getPosibleEvaluadorInvestigadorExt() {
        return posibleEvaluadorInvestigadorExt;
    }

    public void setPosibleEvaluadorInvestigadorExt(InvestigadorInterno posibleEvaluadorInvestigadorExt) {
        this.posibleEvaluadorInvestigadorExt = posibleEvaluadorInvestigadorExt;
    }

    public List getListaEvaluadorInvExterno() {
        return listaEvaluadorInvExterno;
    }

    public void setListaEvaluadorInvExterno(List listaEvaluadorInvExterno) {
        this.listaEvaluadorInvExterno = listaEvaluadorInvExterno;
    }

    public SelectItem[] getItemsTipoEvaluador() {
        return itemsTipoEvaluador;
    }

    public void setItemsTipoEvaluador(SelectItem[] itemsTipoEvaluador) {
        this.itemsTipoEvaluador = itemsTipoEvaluador;
    }

    public UIData getTablaEleccionInternos() {
        return tablaEleccionInternos;
    }

    public void setTablaEleccionInternos(UIData tablaEleccionInternos) {
        this.tablaEleccionInternos = tablaEleccionInternos;
    }

    public UIData getTablaEvaluadorInvExterno() {
        return tablaEvaluadorInvExterno;
    }

    public void setTablaEvaluadorInvExterno(UIData tablaEvaluadorInvExterno) {
        this.tablaEvaluadorInvExterno = tablaEvaluadorInvExterno;
    }

    public String getNombreBusqueda() {
        return nombreBusqueda;
    }

    public void setNombreBusqueda(String nombreBusqueda) {
        this.nombreBusqueda = nombreBusqueda;
    }

    public String getApellidoBusqueda() {
        return apellidoBusqueda;
    }

    public void setApellidoBusqueda(String apellidoBusqueda) {
        this.apellidoBusqueda = apellidoBusqueda;
    }

    public List getListaInvestigadores() {
        return listaInvestigadores;
    }

    public void setListaInvestigadores(List listaInvestigadores) {
        this.listaInvestigadores = listaInvestigadores;
    }

    public String getIdInvestigador() {
        return idInvestigador;
    }

    public void setIdInvestigador(String idInvestigador) {
        this.idInvestigador = idInvestigador;
    }

    public UIData getTablaConsultas() {
        return tablaConsultas;
    }

    public void setTablaConsultas(UIData tablaConsultas) {
        this.tablaConsultas = tablaConsultas;
    }

    public String getTitulo1() {
        return titulo1;
    }

    public void setTitulo1(String titulo1) {
        this.titulo1 = titulo1;
    }

    public String getTitulo2() {
        return titulo2;
    }

    public void setTitulo2(String titulo2) {
        this.titulo2 = titulo2;
    }

    public InvestigadorInternoVista getPeiSeleccionado() {
        return peiSeleccionado;
    }

    public void setPeiSeleccionado(InvestigadorInternoVista peiSeleccionado) {
        this.peiSeleccionado = peiSeleccionado;
    }

    public EvaluadorVista getPosibleEvaluadorSeleccionado() {
        return posibleEvaluadorSeleccionado;
    }

    public void setPosibleEvaluadorSeleccionado(EvaluadorVista posibleEvaluadorSeleccionado) {
        this.posibleEvaluadorSeleccionado = posibleEvaluadorSeleccionado;
    }

    public String getEmailDetalle() {
        return emailDetalle;
    }

    public void setEmailDetalle(String emailDetalle) {
        this.emailDetalle = emailDetalle;
    }

    public String getTelefonoDetalle() {
        return telefonoDetalle;
    }

    public void setTelefonoDetalle(String telefonoDetalle) {
        this.telefonoDetalle = telefonoDetalle;
    }

    public String getExperticiaDetalle() {
        return experticiaDetalle;
    }

    public void setExperticiaDetalle(String experticiaDetalle) {
        this.experticiaDetalle = experticiaDetalle;
    }

    public String getTipoEvaluadorDetalle() {
        return tipoEvaluadorDetalle;
    }

    public void setTipoEvaluadorDetalle(String tipoEvaluadorDetalle) {
        this.tipoEvaluadorDetalle = tipoEvaluadorDetalle;
    }

    public String getNombreDetalle() {
        return nombreDetalle;
    }

    public void setNombreDetalle(String nombreDetalle) {
        this.nombreDetalle = nombreDetalle;
    }

}
