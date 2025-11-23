/*
 * Created on 11-jun-2013
 */
package co.edu.unal.hermes.vista.proyectos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.component.menuitem.MenuItem;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.AreaTematica;
import co.edu.unal.hermes.modelo.AreaTematicaVista;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Financiacion;
import co.edu.unal.hermes.modelo.Formulario;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.Gasto;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.HistoricoFormularioProyecto;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoInvestigador;
import co.edu.unal.hermes.vista.Error;

public class ManejadorFichaMinimaHomeJovenes extends ManejadorFichaMinimaBase {

    private static final long serialVersionUID = 1273752486261082571L;

    public String documentoCoinv2;

    private List listaFinanciacionesConv;
    // PROYECTO
    InvestigadorProyecto participante = new InvestigadorProyecto();
    public String selItems = "1";
    private String objetivoEspecifico;
    private UIComponent objetoEspecifico;
    private List<DominioDetalle> listaCategorias;
    private SelectItem[] categoriaItems;
    public TipoDocumento tipoDocumentoCoInv2;
    private String DOMINIO_SUB_AREA_CIENCIA = "SUB_AREA_CIENCIA_FM";
    private boolean noAplicaProductos;
    private Proyecto proyectoAsociar;

    private boolean mostrarSiGuardarFinalizar = false;

    private boolean verTutor;
    private boolean verSemillero;

    private boolean inscPryJI_Aval = false;
    private Long idAvalJI;
    private Long idProyectoJI;
    private Grupo gSel;
    private ArrayList<Gasto> listaGastos;

    public ManejadorFichaMinimaHomeJovenes() {
        super();

        proyectoAsociar = (Proyecto) sesion.getAttribute("proyectoAsociar");
        sesion.removeAttribute("proyectoAsociar");
        tipoDocumentoCoInv2 = new TipoDocumento();

        listaFinanciacionesConv = new ArrayList<Financiacion>();
        listaGastos = new ArrayList<Gasto>();

        if (sesion.getAttribute("Aval_JI") != null || sesion.getAttribute("idProyecto_JI") != null) {
            idAvalJI = (Long) sesion.getAttribute("Aval_JI");
            idProyectoJI = (Long) sesion.getAttribute("idProyecto_JI");
            gSel = (Grupo) sesion.getAttribute("grupoJI");
            inscPryJI_Aval = false;
        } else {
            gSel = (Grupo) sesion.getAttribute("grupoJI");
            inscPryJI_Aval = true;
        }

        if (sesion.getAttribute("convocatoriaUsoFichaMinima") != null) {
            Long idLong = (Long) sesion.getAttribute("convocatoriaUsoFichaMinima");
            convocatoriaActual = (Convocatoria) servicioGeneral.obtenerObjeto(new Convocatoria(), Long.valueOf(idLong));
        } else {
            convocatoriaActual = (Convocatoria) servicioGeneral.obtenerObjeto(new Convocatoria(),
                    Long.valueOf(MODALIDAD_FICHA_MINIMA_ID));
            mostrarMenuFormulario = false;
        }

        noAplicaProductos = false;

        cargarConvocatoriaActual();
        cargarListas();
        crearListaTipoVinculacion();
        cambiarArea();
        cambiarAreaSec();

        cargarCiudad();

        if (mostrarMenuFormulario) {
            noAplicaProductos = false;
            cargarRubrosModalidad();
        }

        selItems = "PCD";

        boolean fichaConsulta = false;
        if (sesion.getAttribute("consultaFichaMinina") != null) {
            fichaConsulta = (Boolean) sesion.getAttribute("consultaFichaMinina");
        }
        if (fichaConsulta) {
            esConsulta = true;
        } else {
            esConsulta = false;
        }
        ProyectoVista proyecto = (ProyectoVista) sesion.getAttribute("proyectoFichaMinina");
        if (proyecto == null) {
            proyectoActual = new Proyecto();
            proyectoActual.setDuracion(12);
            proyectoActual.adicionarGrupo(gSel);
            proyectoActual.setModalidad(convocatoriaActual);
            proyectoActual.cambiarEstadoPersona(EstadoProyecto.INGRESANDO, cargarPersonaActual());
            proyectoActual.setFase(0);
            if (proyectoAsociar != null) {
                proyectoActual.setCodigoDib(proyectoAsociar.getId().toString());
                proyectoActual.setNombre(proyectoAsociar.getNombre());
            }

            this.proyectoActual.setTipoActividad("FM_ECP");

            this.proyectoActual.setTipoActividad("FM_PINV");

            if (mostrarMenuFormulario) {
                ingresarFuenteEspecieUN_Interna();
            }

        } else {

            if (proyectoActual.getAvalAsociado() != null) {
                idAvalJI = proyectoActual.getAvalAsociado();
            }

            this.proyectoActual.setTipoActividad("FM_PINV");

            if (proyectoActual.getAvalAsociado() != null) {
                inscPryJI_Aval = false;
            } else {
                inscPryJI_Aval = true;
            }
            boolean incluirGastos = true;
            proyectoActual = servicioProyecto.obtenerProyecto(proyecto.getId(), ProyectoDAOHibernate.TODO_POR_ID,
                    incluirGastos);

            try {
                sedeSel = proyectoActual.getSedeEjecucion().getId().toString();
            } catch (Exception e) {
                sedeSel = "";
            }

            List listaAreasSec = servicioGeneral.obtenerObjetos(
                    "select e from AreaTematica e where e.proyecto.id = " + proyectoActual.getId() + "and e.tipo = 2");

            if (listaAreasSec.size() > 0) {

                for (int i = 0; i < listaAreasSec.size(); i++) {
                    AreaTematica atDos = (AreaTematica) listaAreasSec.get(i);

                    DominioDetalle arDos = new DominioDetalle();
                    List listaDomDetarDos = new ArrayList<DominioDetalle>();
                    listaDomDetarDos = servicioGeneral.obtenerObjetos(
                            "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
                                    + DOMINIO_SUB_AREA_CIENCIA + "' and dd.identificador.tipo = '"
                                    + atDos.getProyectoAreaTematica().getIdentificador().getTipo() + "'");
                    if (listaDomDetarDos.size() > 0) {
                        arDos = (DominioDetalle) listaDomDetarDos.get(0);

                        System.out.println("consulta uno ***************************======= "
                                + "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and dd.identificador.tipo ='"
                                + arDos.getIdentificador().getTipo() + "'");
                        List listaAreasSecTemp = new ArrayList<DominioDetalle>();
                        listaAreasSecTemp = servicioGeneral.obtenerObjetos(
                                "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and dd.identificador.tipo ='"
                                        + arDos.getIdentificador().getTipo() + "'");
                        DominioDetalle dd = (DominioDetalle) listaAreasSecTemp.get(0);

                        DominioDetalle arUno = new DominioDetalle();
                        List listaDomDetarUno = new ArrayList<DominioDetalle>();
                        System.out.println("consulta:  ====== "
                                + "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
                                + DOMINIO_AREA_CIENCIA + "' and dd.identificador.tipo = '" + dd.getEstado() + "'");
                        listaDomDetarUno = servicioGeneral.obtenerObjetos(
                                "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
                                        + DOMINIO_AREA_CIENCIA + "' and dd.identificador.tipo = '" + dd.getEstado()
                                        + "'");
                        arUno = (DominioDetalle) listaDomDetarUno.get(0);

                        AreaTematicaVista arTem = new AreaTematicaVista();
                        arTem.setProyecto(proyectoActual);
                        arTem.setNombreArea(arUno.getDescripcion());
                        arTem.setAreaTematica(arUno);
                        arTem.setNombreSubArea(arDos.getDescripcion());
                        arTem.setSubAreaTematica(arDos);
                        arTem.setTipo(2l);
                        dd = null;
                        listaAreasTematicas.add(arTem);
                    }

                }

            }

            if (mostrarMenuFormulario) {
                // cargar funentes financieras

                if (proyectoActual.getFinanciaciones().size() > 0) {
                    listaFinanciacionesConv.addAll(proyectoActual.getFinanciaciones());

                    Financiacion f = new Financiacion();
                    f = (Financiacion) listaFinanciacionesConv.get(0);

                    List gastosList = servicioGeneral
                            .obtenerObjetos("select e from Gasto e where e.financiacion.id = " + f.getId());
                    ArrayList gast = new ArrayList<Gasto>();
                    gast.addAll(gastosList);
                    f.setGastosListados(gast);
                    if (gastosList.size() > 0) {
                        listaGastos.addAll(gastosList);
                    }

                } else {
                    ingresarFuenteEspecieUN_Interna();
                }

            }

        }

        if (!validarVinculacionPersona()) {
            Error error = new Error();
            error.setMensaje("El tipo de vinculación del investigador no es válido.");
        }

        cargarListaProductos();

        sesion.setAttribute("proyecto", proyectoActual);
        sesion.removeAttribute("manejadorProyectosInvestigador");
        sesion.removeAttribute("manejadorActividades");
        sesion.removeAttribute("manejadorArchivos");
        sesion.removeAttribute("manejadorBibliografia");
        sesion.removeAttribute("manejadorDatosBasicos");
        sesion.removeAttribute("manejadorDetallesFinancieros");
        sesion.removeAttribute("manejadorEvaluadores");
        sesion.removeAttribute("manejadorFuentesFinancieras");
        sesion.removeAttribute("manejadorInformacionEspecifica");
        sesion.removeAttribute("manejadorInvestigadores");
        sesion.removeAttribute("manejadorLineas");
        sesion.removeAttribute("manejadorObjetivosResultados");
        sesion.removeAttribute("manejadorRubros");
        sesion.removeAttribute("manejadorVigencias");
        sesion.removeAttribute("manejadorMenuFormularios");

    }

    public void ingresarFuenteEspecieUN_Interna() {

        Financiacion f = new Financiacion();
        try {

            List lf = servicioGeneral.obtenerObjetos("select e from FuenteFinanciacion e where e.id = 583");

            FuenteFinanciacion ff = (FuenteFinanciacion) lf.get(0);
            f.setFuente(ff);
            f.setValor(22176000L);
            f.setRol("ROL_FT_FIN");
            if (listaFinanciacionesConv == null) {
                listaFinanciacionesConv = new ArrayList();
                listaFinanciacionesConv.add(f);
            } else {
                listaFinanciacionesConv.add(f);
            }
        } catch (Exception e) {
            System.out.println(
                    "ManejadorFichaMinima:asociarFuente:Error hallando la fuente de financiacion especificada");
            e.printStackTrace();
        }

    }

    public void crearListaTipoVinculacion() {

        for (int i = 0; i < proyectoActual.getListaInvestigadoresProyectoConDatos().size(); i++) {
            if (proyectoActual.getListaInvestigadoresProyectoConDatos().size() > 0) {
                if (proyectoActual.getListaInvestigadoresProyectoConDatos().get(i).getTipo().getId().equals("JIEU")
                        || proyectoActual.getListaInvestigadoresProyectoConDatos().get(i).getTipo().getId().equals("JIPO")) {

                    String consulta2 = "select ti from TipoInvestigador ti where ti.id like 'JITU'";

                    listaTipoVinculacion = servicioGeneral.obtenerObjetos(consulta2);

                    if (listaTipoVinculacion.size() > 0) {
                        tipoVinculacionItems = new SelectItem[listaTipoVinculacion.size()];
                        for (int ii = 0; ii < listaTipoVinculacion.size(); ii++) {
                            TipoInvestigador dominio = (TipoInvestigador) listaTipoVinculacion.get(ii);
                            tipoVinculacionItems[ii] = new SelectItem(dominio.getId(), dominio.getNombre());
                        }
                    }
                } else if (proyectoActual.getListaInvestigadoresProyectoConDatos().get(i).getTipo().getId().equals("JITU")) {
                    String consulta2 = "select ti from TipoInvestigador ti where ti.id in( 'JIEU', 'JIPO')";

                    listaTipoVinculacion = servicioGeneral.obtenerObjetos(consulta2);

                    if (listaTipoVinculacion.size() > 0) {
                        tipoVinculacionItems = new SelectItem[listaTipoVinculacion.size()];
                        for (int ii = 0; ii < listaTipoVinculacion.size(); ii++) {
                            TipoInvestigador dominio = (TipoInvestigador) listaTipoVinculacion.get(ii);
                            tipoVinculacionItems[ii] = new SelectItem(dominio.getId(), dominio.getNombre());
                        }
                    }
                }

            }

        }

    }
    
    public boolean validarNombre_InvPpal() {
        boolean val = true;
        boolean valestud = false;

        if (proyectoActual.getListaInvestigadorPrincipal().size() <= 0 || proyectoActual.getListaInvestigadorPrincipal() == null) {
            val = false;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "Por favor registre la información del director del proyecto",
                            "Por favor registre la información del director del proyecto"));
        }

        if (this.proyectoActual.getNombre() == null || this.proyectoActual.getNombre().equals("--")
                || this.proyectoActual.getNombre().equals("")) {
            val = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Por favor registre el título del proyecto", "Por favor registre el título del proyecto"));
        }

        // Palabras Clave
        if (this.proyectoActual.getListaPalabrasES().size() < 3 && inscPryJI_Aval) {
            val = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Por favor agregue mínimo 3 palabras clave", "Por favor agregue mínimo 3 palabras clave"));
        }

        String sHorasSem = String.valueOf(proyectoActual.getHorasSemanaFormulacion());
        if (sHorasSem.length() > 2 || proyectoActual.getHorasSemanaFormulacion() < 0) {
            val = false;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "Las horas semanales dedicadas a la formulación debe ser de máximo 2 dígitos y mayor a 0",
                            "Las horas semanales dedicadas a la formulación debe ser de máximo 2 dígitos y mayor a 0"));
        }

        String sSemFor = String.valueOf(proyectoActual.getSemanasFormulacion());
        if (sSemFor.length() > 3 || proyectoActual.getSemanasFormulacion() < 0) {
            val = false;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "Las semanas dedicadas a la formulación debe ser de máximo 3 dígitos y mayor a 0",
                            "Las semanas dedicadas a la formulación debe ser de máximo 3 dígitos y mayor a 0"));
        }

        return val;
    }

    public boolean validarNombre_InvPpal_preview() {
        boolean val = true;
        boolean valestud = false;

        if (proyectoActual.getListaInvestigadorPrincipal().size() <= 0
                || proyectoActual.getListaInvestigadorPrincipal() == null) {
            val = false;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "Por favor registre la información del director del proyecto",
                            "Por favor registre la información del director del proyecto"));
        }

        if (this.proyectoActual.getNombre() == null || this.proyectoActual.getNombre().equals("--")
                || this.proyectoActual.getNombre().equals("")) {
            val = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Por favor registre el título del proyecto", "Por favor registre el título del proyecto"));
        }

        if (proyectoActual.getDuracion() < 13) {
            proyectoActual.setDuracionTipo("M");

        } else {
            val = false;
            mensajeError(buscarPer2, "La duración máxima del proyecto es de 12 meses.");
        }

        return val;
    }

    public boolean validarConvocatoriaSemilleros() {
        boolean ret = true;
        String docTutor = "";
        String docTutorJI = "";
        if (convocatoriaActual.getRestriccion() != null) {

            if (convocatoriaActual.getRestriccion().getId().equals("CONV_SEM_COL")) {
                if (proyectoActual.getListaInvestigadoresProyectoConDatos().size() < 3) {
                    ret = false;
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                    "Para semilleros de deben registar mínimo tres estudiantes.",
                                    "Para semilleros de deben registar mínimo tres estudiantes."));
                } else {
                    int contEst = 0;
                    for (int i = 0; i < proyectoActual.getListaInvestigadoresProyectoConDatos().size(); i++) {
                        InvestigadorProyecto ip = proyectoActual.getListaInvestigadoresProyectoConDatos().get(i);
                        if (ip.getTipo().getId().equals("SEMC")) {
                            contEst++;
                        }

                        if (ip.getTipo().getId().equals("TSEC")) {
                            InvestigadorProyecto ipd = proyectoActual.getListaInvestigadoresProyectoConDatos().get(i);
                            docTutor = ipd.getInvestigador().getId().getDocumento();
                        }
                    }

                    if (contEst < 3) {
                        ret = false;
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                        "Para semilleros de deben registar mínimo tres estudiantes.",
                                        "Para semilleros de deben registar mínimo tres estudiantes."));

                    }
                }
            }

            if (proyectoActual.getListaInvestigadorPrincipal().size() > 0) {
                InvestigadorProyecto prin = proyectoActual.getListaInvestigadorPrincipal().get(0);
                if (prin.getInvestigador().getId().getDocumento().equals(docTutor)) {
                    ret = false;
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                    "El tutor ya se encuentra registrado como investigador responsable del proyecto.",
                                    "El tutor ya se encuentra registrado como investigador responsable del proyecto."));

                }
            }
        }

        if (convocatoriaActual.getRestriccion() != null) {
            if (convocatoriaActual.getRestriccion().getId().equals("CONV_JI_COL")) {
                for (int i = 0; i < proyectoActual.getListaInvestigadoresProyectoConDatos().size(); i++) {
                    InvestigadorProyecto ip = proyectoActual.getListaInvestigadoresProyectoConDatos().get(i);
                    if (ip.getTipo().getId().equals("JITU")) {
                        InvestigadorProyecto ipd = proyectoActual.getListaInvestigadoresProyectoConDatos().get(i);
                        docTutorJI = ipd.getInvestigador().getId().getDocumento();
                    }
                }

                InvestigadorProyecto prin = proyectoActual.getListaInvestigadorPrincipal().get(0);
                if (prin.getInvestigador().getId().getDocumento().equals(docTutorJI)) {
                    ret = false;
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                    "El tutor ya se encuentra registrado como investigador responsable del proyecto.",
                                    "El tutor ya se encuentra registrado como investigador responsable del proyecto."));

                }

            }
        }

        return ret;
    }

    public boolean validarJovenInvestigador() {
        boolean ret = true;
        String docTutor = "";
        String docTutorJI = "";
        if (convocatoriaActual.getRestriccion() != null) {

            if (convocatoriaActual.getRestriccion().getId().equals("CONV_JI_COL_2014")) {
                if (proyectoActual.getListaInvestigadoresProyectoConDatos().size() < 1) {
                    ret = false;
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                    "Se debe registar un estudiante Joven Investigador.",
                                    "Se debe registar un estudiante Joven Investigador."));
                } else {
                    int contEst = 0;
                    for (int i = 0; i < proyectoActual.getListaInvestigadoresProyectoConDatos().size(); i++) {
                        InvestigadorProyecto ip = proyectoActual.getListaInvestigadoresProyectoConDatos().get(i);
                        if (ip.getTipo().getId().equals("JIPO") || ip.getTipo().getId().equals("JIEU")) {
                            contEst++;
                        }

                        if (ip.getTipo().getId().equals("JITU")) {
                            InvestigadorProyecto ipd = proyectoActual.getListaInvestigadoresProyectoConDatos().get(i);
                            docTutor = ipd.getInvestigador().getId().getDocumento();
                        }
                    }

                    if (contEst < 1) {
                        ret = false;
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                        "Se debe registar un estudiante Joven Investigador.",
                                        "Se debe registar un estudiante Joven Investigador."));

                    }
                }
            }

            if (proyectoActual.getListaInvestigadorPrincipal().size() > 0) {
                InvestigadorProyecto prin = proyectoActual.getListaInvestigadorPrincipal().get(0);
                if (prin.getInvestigador().getId().getDocumento().equals(docTutor)) {
                    ret = false;
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                    "El tutor ya se encuentra registrado como investigador responsable del proyecto.",
                                    "El tutor ya se encuentra registrado como investigador responsable del proyecto."));

                }
            }

        }

        return ret;
    }

    public String siguiente() {

        if (validarNombre_InvPpal_preview() && validarConvocatoriaSemilleros() && validarJovenInvestigador()) {

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

                            if (lis[i].getOutcome().equals("irMod_JI_SEM")) {
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

                if (proyectoActual.getId() != null) {
                    Persona personaAux = new Persona();
                    personaAux = (Persona) sesion.getAttribute("persona");

                    Formulario formulario = new Formulario();
                    List listaFormulario = new ArrayList();

                    listaFormulario = servicioGeneral.obtenerListaObjetos("Formulario where id ='8'");
                    formulario = (Formulario) listaFormulario.get(0);

                    HistoricoFormularioProyecto historicoFormualrioProyecto = new HistoricoFormularioProyecto();
                    historicoFormualrioProyecto.setDocPersona(personaAux.getId().getDocumento());
                    historicoFormualrioProyecto.setTipoDocumentoPersona(personaAux.getId().getTipoDocumento());
                    historicoFormualrioProyecto.setFormulario(formulario);
                    historicoFormualrioProyecto.setProyecto(proyectoActual);
                    historicoFormualrioProyecto.setFechaCambio(new Date());
                    servicioGeneral.guardarObjeto(historicoFormualrioProyecto);
                }

                proyectoActual.setAvalAsociado(idAvalJI);
                guardarFichaMinimaProyecto(false, false);
                
                if(validarSiEsEstudianteLiderProyecto(proyectoActual.getId()) && proyectoActual.getId() != null){
                	//Se comenta el envio de correo por que en BD no esta creada la plantilla de correo 326 y se desconoce el motivo, 
					//lo cual genera excepcion al guardar con rol Estudiante Lider
//              	  enviarCorreoEdicionInfoPryEstLider(proyectoActual.getId());
                }

                if (convocatoriaActual.getTipo().getId().equals("PM")) {
                    sesion.removeAttribute("manejadorMenuFormularios");
                    return "irInformacionEspecificaMarco";
                } else {
                    if (convocatoriaActual.getTipo().getId().equals("CJI")) {
                        return "irInformacionEspecifica";
                    }
                }
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Su proyecto NO ha sido guardado", "Su proyecto NO ha sido guardado"));
        }
        return "";

    }

    public String siguienteFM() {

        if (validarNombre_InvPpal_preview()) {

            guardarFichaMinimaProyecto(false, false);

            if (convocatoriaActual.getTipo().getId().equals("PM")) {
                sesion.removeAttribute("manejadorMenuFormularios");
                return "irInformacionEspecificaMarco";
            } else {
                if (convocatoriaActual.getTipo().getId().equals("CJI")) {
                    return "irInformacionEspecifica";
                }
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Su proyecto NO ha sido guardado", "Su proyecto NO ha sido guardado"));
        }
        return "";
    }

    public String atras() {
        sesion.removeAttribute("manejadorFichaMinimaHomeJovenes");
        return "successProyectosProyecto";
    }

    public String guardarFinalizar() {
        proyectoActual.cambiarEstadoPersona(EstadoProyecto.PROPUESTO, cargarPersonaActual());

        if (validarNombre_InvPpal()) {
            mostrarSiGuardarFinalizar = true;

            guardarFichaMinimaProyecto(false, false);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Su proyecto NO ha sido guardado", "Su proyecto NO ha sido guardado"));
        }

        return "";
    }

    public void setProyectoActual(Proyecto proyectoActual) {
        this.proyectoActual = proyectoActual;
    }

    public Proyecto getProyectoActual() {
        return proyectoActual;
    }

    public void setObjetivoEspecifico(String objetivoEspecifico) {
        this.objetivoEspecifico = objetivoEspecifico;
    }

    public String getObjetivoEspecifico() {
        return objetivoEspecifico;
    }

    public void setObjetoEspecifico(UIComponent objetoEspecifico) {
        this.objetoEspecifico = objetoEspecifico;
    }

    public UIComponent getObjetoEspecifico() {
        return objetoEspecifico;
    }

    public void setAreaCiencia(String areaCiencia) {
        this.areaCiencia = areaCiencia;
    }

    public String getAreaCiencia() {
        return areaCiencia;
    }

    public void setAreaCienciaSec(String areaCienciaSec) {
        this.areaCienciaSec = areaCienciaSec;
    }

    public String getAreaCienciaSec() {
        return areaCienciaSec;
    }

    public TipoDocumento getTipoDocumentoCoInv2() {
        return tipoDocumentoCoInv2;
    }

    public void setTipoDocumentoCoInv2(TipoDocumento tipoDocumentoCoInv2) {
        this.tipoDocumentoCoInv2 = tipoDocumentoCoInv2;
    }

    public String getDocumentoCoinv2() {
        return documentoCoinv2;
    }

    public void setDocumentoCoinv2(String documentoCoinv2) {
        this.documentoCoinv2 = documentoCoinv2;
    }

    public String getSelItems() {
        return selItems;
    }

    public void setSelItems(String selItems) {
        this.selItems = selItems;
    }

    public void setTiempoTotalParticipante(Integer tiempoTotalParticipante) {
        this.tiempoTotalParticipante = tiempoTotalParticipante;
    }

    public Integer getTiempoTotalParticipante() {
        return tiempoTotalParticipante;
    }

    public InvestigadorProyecto getParticipante() {
        return participante;
    }

    public void setParticipante(InvestigadorProyecto participante) {
        this.participante = participante;
    }

    public List<DominioDetalle> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<DominioDetalle> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    public List<Dependencia> getDependenciasUN() {
        return dependenciasUN;
    }

    public void setDependenciasUN(List<Dependencia> dependenciasUN) {
        this.dependenciasUN = dependenciasUN;
    }

    public SelectItem[] getCategoriaItems() {
        return categoriaItems;
    }

    public void setCategoriaItems(SelectItem[] categoriaItems) {
        this.categoriaItems = categoriaItems;
    }
    
    public String terminarDespues() {
        boolean band = true;

        if (validarNombre_InvPpal_preview() && validarConvocatoriaSemilleros() && validarJovenInvestigador()) {
            guardarFichaMinimaProyecto(true, false);
            
            if(validarSiEsEstudianteLiderProyecto(proyectoActual.getId()) && proyectoActual.getId() != null){
            	//Se comenta el envio de correo por que en BD no esta creada la plantilla de correo 326 y se desconoce el motivo, 
				//lo cual genera excepcion al guardar con rol Estudiante Lider
//          	  enviarCorreoEdicionInfoPryEstLider(proyectoActual.getId());
            }

            if (!band) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "Su proyecto NO ha sido guardado", "Su proyecto NO ha sido guardado"));
            }
        }

        return "";
    }

    public void setProyectoAsociar(Proyecto proyectoAsociar) {
        this.proyectoAsociar = proyectoAsociar;
    }

    public Proyecto getProyectoAsociar() {
        return proyectoAsociar;
    }

    public void setNoAplicaProductos(boolean noAplicaProductos) {
        this.noAplicaProductos = noAplicaProductos;
    }

    public boolean isNoAplicaProductos() {
        return noAplicaProductos;
    }

    public List getListaFinanciacionesConv() {
        return listaFinanciacionesConv;
    }

    public void setListaFinanciacionesConv(List listaFinanciacionesConv) {
        this.listaFinanciacionesConv = listaFinanciacionesConv;
    }

    public boolean isMostrarSiGuardarFinalizar() {
        return mostrarSiGuardarFinalizar;
    }

    public void setMostrarSiGuardarFinalizar(boolean mostrarSiGuardarFinalizar) {
        this.mostrarSiGuardarFinalizar = mostrarSiGuardarFinalizar;
    }
    
    public boolean isVerTutor() {
        return verTutor;
    }

    public void setVerTutor(boolean verTutor) {
        this.verTutor = verTutor;
    }

    public boolean isVerSemillero() {
        return verSemillero;
    }

    public void setVerSemillero(boolean verSemillero) {
        this.verSemillero = verSemillero;
    }

    public boolean isInscPryJI_Aval() {
        return inscPryJI_Aval;
    }

    public void setInscPryJI_Aval(boolean inscPryJI_Aval) {
        this.inscPryJI_Aval = inscPryJI_Aval;
    }

    public Long getIdAvalJI() {
        return idAvalJI;
    }

    public void setIdAvalJI(Long idAvalJI) {
        this.idAvalJI = idAvalJI;
    }

    public Long getIdProyectoJI() {
        return idProyectoJI;
    }

    public void setIdProyectoJI(Long idProyectoJI) {
        this.idProyectoJI = idProyectoJI;
    }

    public Grupo getgSel() {
        return gSel;
    }

    public void setgSel(Grupo gSel) {
        this.gSel = gSel;
    }

}
