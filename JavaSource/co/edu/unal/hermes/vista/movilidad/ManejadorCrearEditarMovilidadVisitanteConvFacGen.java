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
import javax.faces.component.UIData;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.ActividadMovilidadVE;
import co.edu.unal.hermes.modelo.ArchivoMovilidadVE;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Institucion;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorGrupo;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.MovilidadArchivo;
import co.edu.unal.hermes.modelo.MovilidadVisitanteExterior;
import co.edu.unal.hermes.modelo.Pais;
import co.edu.unal.hermes.modelo.PalabraClave;
import co.edu.unal.hermes.modelo.Parametro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PlanEstudios;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.TipoActividadMovilidadVE;
import co.edu.unal.hermes.modelo.TipoArchivo;
import co.edu.unal.hermes.modelo.TipoArchivoMovilidad;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoMovilidad;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.utils.Navegacion;

public class ManejadorCrearEditarMovilidadVisitanteConvFacGen extends ManejadorBaseMovilidad {

    private static final long serialVersionUID = -2710786670355401060L;
    private Long costoTiquetes;
    private Long costoInscripcion;
    private Long aporteFacultad;
    private Long destinado;
    private String idcreador;
    private String solDocente;
    private boolean mostrarDatosBasicos;
    private boolean mostrarDatosEvento;
    private boolean mostrarDatosEventoGrupo;
    private boolean panelNoExiste;
    private UIData tablaActividades;
    private Date fechaMinimaInicio;
    byte[] datos;
    private boolean identificacion;
    private SelectItem[] depsItem;
    private List listaDependencias;
    private String sedeSeleccionada = "";
    private String nombreSedeSeleccionada;
    private String documentoDocente;
    private String nombreArchivo;
    private String facultadSeleccionada = "";
    private String nombreFacultadSeleccionada;
    private String departamentoSeleccionado = "";
    private String nombreDepartamentoSeleccionado;
    private Persona persona;
    private String idCiudad;
    private String nombreEvento;
    private SelectItem[] institucionItem;
    private List ciudades;
    private List listaInstitucion;
    private List listaGrupos;
    private List listaIntegrantesGrupo;
    private Investigador responsableGrupo;
    private Grupo grupo;
    private String actividadSel;
    private PalabraClave palabraClave;
    private List<PalabraClave> listaPalabrasClave;

    private SelectItem[] sedeItem;
    private String sede;
    private UploadedFile archivoCargar;
    private SelectItem[] tipoDocumentoItem;
    private SelectItem[] actividadSelItem;
    private TipoDocumento tipoDocumento;
    private String idgrupo;
    private String idPrograma;
    private String nombreDocente;
    private String documentoVisitante;
    private String emailVisitante;
    private String nacionalidad;
    private String paisProcedencia;
    private String ciudadProcedencia;
    private String institucion;
    private String ruta;
    private String cronograma;
    private String eventoDifusion;
    private String montoDiario;
    private String numeroDias;
    private Date fechaInicioEvento;
    private Date fechaFinEvento;
    private Date fechaActividad;
    private String duracionActividad;
    private String nombreLider;
    private String pMontoDiario;
    private boolean panelMasDatos;
    private boolean fechaIncorrecta;
    private boolean panelMasDatos2;
    private boolean panelErrorGrupo;
    private boolean panelEmailInvestigador;
    private boolean panelMasDatos3;
    private boolean fechaIncorrecta2;
    private boolean errorArchivo;
    private boolean archivoCargado;
    private boolean errorMontoDiario;
    private boolean bImprimirReporte;
    private ActividadMovilidadVE temp;
    private InvestigadorInterno investigadorInterno;
    Long anoAnt;
    Long anoAct;
    Long mesAct;
    private ActividadMovilidadVE actividadMovilidadVESeleccionada;
    private ArchivoMovilidadVE archivoMovilidadVESeleccionado;
    private boolean puedeSubirArchivos = false;

    private MovilidadVisitanteExterior mve;

    private String tipoDocDocente;
    private String tipoDocCreador;
    // private InvestigadorInterno investigadorInterno;

    private SelectItem[] aprobacion = { new SelectItem("NO", "NO"), new SelectItem("SI", "SI") };

    private List programas;

    private List paises;

    private SelectItem[] tipoDocumentoSelItem;
    private String tipoDocumentoSel;

    private String solInscripcion;
    private String resolucionViaje;
    private String aceptacionDIB;
    private String aceptacionFacultad;
    private String aceptacionPonencia;
    private String documento;
    private String tituloPonencia;
    private String resumenPonencia;
    private String idPais;
    private List listaActividades;
    private String mensajeErrorActividad = "";

    private UploadedFile archivoObligatorio;
    private TipoArchivo tipoArchivo;

    private List listaTipoArchivo;
    public SelectItem[] listaActividadesItem;

    private SelectItem[] tipoArchivoItem;

    private List listaArchivos;
    private List listaArchivosObligatorios;
    private List<ArchivoMovilidadVE> listaArchivosObligatoriosSel;

    private HtmlPanelGroup panelArchivos;
    private HtmlDataTable tablaArchivos;
    private HtmlDataTable tablaArchivosObligatorios;
    CorreoPlantilla correoActual = new CorreoPlantilla();
    String cuerpoCorreo = "";

    private String errores[];
    private boolean bErrorGrupo;
    private boolean bErrorPrograma;
    private boolean bErrorDocente;
    private boolean bErrorEmail;
    private boolean bErrorDocumento;
    private boolean bErrorNacionalidad;
    private boolean bErrorPais;
    private boolean bErrorCiudad;
    private boolean bErrorInstitucion;
    private boolean bErrorRuta;
    private boolean bErrorActividades;
    private boolean bErrorEvento;
    private boolean bErrorTiquete;
    private boolean bErrorMonto;
    private boolean bErrorDias;
    private boolean bErrorDocumentos;
    private boolean bErrorHoja;
    private boolean mostrarError = false;
	private String msgError = "Su solicitud NO ha sido enviada, por favor verifique la información y vuelva a GUARDAR. Debe seleccionar SI desea confirmar el envio para enviar la movilidad o NO en caso de guardar parcialmente.";
    private boolean esConvocatoriaFacultad = false;

    private String noExiste;
    private String nombreObligatorio;
    private ArchivoMovilidadVE documentoSeleccionado;
    private String restriccionArchivosConv;
    private boolean restriccionLiderGrupo;

    public ManejadorCrearEditarMovilidadVisitanteConvFacGen() {
    	
    	Long idMovilidadEd = (Long) sesion.getAttribute("movilidadVisExtSel");

        palabraClave = new PalabraClave();
        ocultarPaneles();
        cargarValoresIniciales();
        personaActual = (Persona) sesion.getAttribute("persona");
        if (personaActual != null) {
            documento = personaActual.getId().getDocumento();
            tipoDocumento = new TipoDocumento();
            tipoDocumento.setId(personaActual.getId().getTipoDocumento());
        }

        mve = new MovilidadVisitanteExterior();
        listaArchivos = new ArrayList();
        listaArchivosObligatorios = new ArrayList();
        listaArchivosObligatoriosSel = new ArrayList();
        
        if(idMovilidadEd != null){
        	mve = servicioMovilidad.obtenerMovilidadesVisExt(idMovilidadEd);
        	sesion.setAttribute("idConvocatoriaActual", mve.getConvocatoria().getId());
        	listaArchivosObligatoriosSel.addAll(mve.getArchivos());
        	listaActividades.addAll(mve.getActividades());
        	try
			{
				buscarPersona();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
        	
        	eventoDifusion = mve.getEvento();
        	tituloPonencia = mve.getTitulo();
        	costoTiquetes = mve.getCostotiquete();
        	resumenPonencia = mve.getResumen();
        	solInscripcion = mve.getInscripcionevento();
        	costoInscripcion = mve.getCostoevento();
        	aporteFacultad = mve.getAportefacultad();
        	aceptacionPonencia = mve.getResolucionviaje();
        	fechaInicioEvento = mve.getFechainicial();
        	fechaFinEvento = mve.getFechafinal();
        	aceptacionFacultad = mve.getAceptacion();
        	institucion = mve.getUniversidad();
        	ruta = mve.getPlan();
        	montoDiario = mve.getMonto().toString();
        	numeroDias = mve.getNumerodias().toString();
        	documentoVisitante = mve.getDocumentoVisitante();
        	nombreDocente = mve.getNombreVisitante();
        	emailVisitante = mve.getEmailVisitante();
        	nacionalidad = mve.getNacionalidad();
        	paisProcedencia = mve.getPais().getId();
        	ciudadProcedencia = mve.getCiudad();
        	idPrograma = mve.getIdprograma().getId();
        	idgrupo = mve.getGrupo().getId().toString();
        	
        	mostrarDatosEventoGrupo = true;
        	mostrarDatosEvento = true;
        	panelMasDatos2 = true;
        	panelMasDatos3 = true;
        }

        // para saber si es de facultad
        if (sesion.getAttribute("idConvocatoriaActual") != null) {
            Long idConvocatoria = (Long) sesion.getAttribute("idConvocatoriaActual");
            List listConvocatorias = servicioGeneral.obtenerConvocatoriaMovilidades(idConvocatoria.toString());
            if (listConvocatorias.size() > 0 && listConvocatorias != null) {
                Convocatoria conv = (Convocatoria) listConvocatorias.get(0);

                if (conv.getPadre().getDependencia().getId().equals("1")
                        || conv.getPadre().getDependencia().getId().equals("2")
                        || conv.getPadre().getDependencia().getId().equals("3")
                        || conv.getPadre().getDependencia().getId().equals("4")
                        || conv.getPadre().getDependencia().getId().equals("5")
                        || conv.getPadre().getDependencia().getId().equals("6")
                        || conv.getPadre().getDependencia().getId().equals("7")
                        || conv.getPadre().getDependencia().getId().equals("8")) {
                    esConvocatoriaFacultad = false;
                } else {
                    esConvocatoriaFacultad = true;
                }

            }
        }

        Proyecto p = (Proyecto) sesion.getAttribute("proyectoMovilidad");
        if (p != null) {
            mve.setProyectoFicha(p.getId());
        }

        temp = new ActividadMovilidadVE();

        cargarTiposDocumentos();
        calcularFechaMinimaInicio();
        cargarTiposDocumento();

    }

    private void cargarTiposDocumentos() {

        if (sesion.getAttribute("idConvocatoriaActual") != null) {
            Long idConvocatoria = (Long) sesion.getAttribute("idConvocatoriaActual");

            List listConvocatorias = servicioGeneral
                    .obtenerObjetos("select e from Convocatoria e where e.id = " + idConvocatoria);
            if (listConvocatorias.size() > 0 && listConvocatorias != null) {
                Convocatoria convocatoriaActual = (Convocatoria) listConvocatorias.get(0);
                restriccionArchivosConv = convocatoriaActual.getRequisitosConvTexto() != null
                        ? convocatoriaActual.getRequisitosConvTexto() : "";
                restriccionLiderGrupo = convocatoriaActual.getGruposRegistrados();
            }
        }

        if (listaArchivosObligatorios == null
                || (listaArchivosObligatorios != null && listaArchivosObligatorios.size() == 0)) {
            listaArchivosObligatorios = new ArrayList();
            listaArchivosObligatorios = servicioGeneral.obtenerListaArchivosMovilidad(restriccionArchivosConv);
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
    }

    private void cargarValoresIniciales() {
        errores = new String[16];
        bErrorGrupo = false;
        bErrorPrograma = false;
        bErrorDocente = false;
        bErrorEmail = false;
        bErrorDocumento = false;
        bErrorNacionalidad = false;
        bErrorPais = false;
        bErrorCiudad = false;
        bErrorInstitucion = false;
        bErrorRuta = false;
        bErrorActividades = false;
        bErrorEvento = false;
        bErrorTiquete = false;
        bErrorMonto = false;
        bErrorDias = false;
        bErrorDocumentos = false;
        bErrorHoja = false;
        mostrarDatosBasicos = true;
        mostrarDatosEvento = false;
        fechaIncorrecta = false;
        fechaIncorrecta2 = false;
        errorArchivo = false;
        panelNoExiste = false;
        panelMasDatos = false;
        panelMasDatos2 = false;
        panelMasDatos3 = false;
        archivoCargado = false;
        errorMontoDiario = false;
        montoDiario = "0";
        numeroDias = "0";
        costoTiquetes = 0L;
        identificacion = true;
        panelArchivos = new HtmlPanelGroup();
        personaActual = (Persona) sesion.getAttribute("persona");
        persona = new Persona();
        idcreador = personaActual.getId().getDocumento();
        tipoDocCreador = personaActual.getId().getTipoDocumento();
        documento = new String();
        listaActividades = new ArrayList();
        mostrarDatosEventoGrupo = false;

        cargarSede();
    }

    private void cargarSede() {
        List listaSede = servicioGeneral.obtenerObjetos("from Sede order by nombre asc");
        if (listaSede != null && listaSede.size() > 0) {
            sedeItem = new SelectItem[listaSede.size()];
            for (int i = 0; i < listaSede.size(); i++) {
                Sede sed = (Sede) listaSede.get(i);
                sedeItem[i] = new SelectItem(sed.getId().toString(), sed.getNombre());
            }
        }
        sede = "2";
        // cargarFacultad();
    }

    private void reiniciarVariables() {
        errores = new String[16];
        bErrorGrupo = false;
        bErrorPrograma = false;
        bErrorDocente = false;
        bErrorEmail = false;
        bErrorDocumento = false;
        bErrorNacionalidad = false;
        bErrorPais = false;
        bErrorCiudad = false;
        bErrorInstitucion = false;
        bErrorRuta = false;
        bErrorActividades = false;
        bErrorEvento = false;
        bErrorTiquete = false;
        bErrorMonto = false;
        bErrorDias = false;
        bErrorDocumentos = false;
        bErrorHoja = false;
        mostrarDatosBasicos = true;
        mostrarDatosEvento = false;
        fechaIncorrecta = false;
        fechaIncorrecta2 = false;
        errorArchivo = false;
        panelNoExiste = false;
        panelMasDatos = false;
        panelMasDatos2 = false;
        panelMasDatos3 = false;
        archivoCargado = false;
        errorMontoDiario = false;
        montoDiario = "0";
        numeroDias = "0";
        identificacion = true;
        mostrarError = false;
    }

    public void ocultarPaneles() {
        mostrarDatosBasicos = false;
        mostrarDatosEvento = false;
        panelMasDatos = false;
        panelMasDatos2 = false;
        panelMasDatos3 = false;
        panelNoExiste = false;
        archivoCargado = false;
        errorMontoDiario = false;
        cargarTiposActividades();

    }

    public void buscarPersona() throws SQLException {
        reiniciarVariables();
        IdPersona id = new IdPersona();
        id.setDocumento(documento);
        id.setTipoDocumento(tipoDocumento.getId());
        persona = servicioPersona.obtenerPersona(id);
        String sSql = "";

        int nExiste = 0;
        Date fechaActual = new Date();

        SimpleDateFormat spd = new SimpleDateFormat("dd");
        SimpleDateFormat spm = new SimpleDateFormat("MM");
        SimpleDateFormat spy = new SimpleDateFormat("yyyy");

        anoAct = Long.parseLong(spy.format(fechaActual));
        mesAct = Long.parseLong(spm.format(fechaActual));

        if (mesAct == 12) {
            anoAct = anoAct + 1;
        }

        anoAnt = anoAct - 1;

        System.out.println("año ant : " + anoAnt);
        System.out.println("año act : " + anoAct);
        System.out.println("mes act : " + mesAct);
        if (persona != null) {
            idCiudad = persona.getCiudadDomicilio() == null ? null : persona.getCiudadDomicilio().getId();
            if (persona instanceof Investigador) {
                if (persona instanceof InvestigadorInterno) {
                    investigadorInterno = (InvestigadorInterno) persona;
                    /*
                     * investigadorInterno = servicioPersona
                     * .obtenerInvestigadorInternoCompleto(persona .getId());
                     */
                    String nombre1, nombre2, apellido1, apellido2;
                    listaGrupos = new Vector();
                    cargarGrupos(investigadorInterno, documento);
                    if (listaGrupos.size() > 1) {
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
                        nombreLider = nombre1 + " " + nombre2 + " " + apellido1 + " " + apellido2;
                        documentoDocente = investigadorInterno.getId().getDocumento();
                        tipoDocDocente = investigadorInterno.getId().getTipoDocumento();
                        mostrarDatosPersona();
                    } else {
                        noExiste = "El investigador no es líder de un grupo de investigación.";
                        mostrarPanelNoExiste();
                    }
                } else {
                    noExiste = "El documento ingresado no corresponde a un investigador";
                    mostrarPanelNoExiste();
                }
            } else {
                persona = new Persona();
                IdPersona idP = new IdPersona();
                idP.setDocumento(documento);
                idP.setTipoDocumento(tipoDocumento.getId());
                persona.setId(idP);
                noExiste = "El documento ingresado no corresponde a un investigador";
                mostrarPanelNoExiste();
            }
        } else {
            persona = new Persona();
            IdPersona idP = new IdPersona();
            idP.setDocumento(documento);
            idP.setTipoDocumento(tipoDocumento.getId());
            persona.setId(idP);
            noExiste = "El documento ingresado no existe";
            mostrarPanelNoExiste();
        }
    }

    private void mostrarPanelNoExiste() {
        panelNoExiste = true;
    }

    private void mostrarMasDatos() {
        panelMasDatos = true;
        panelMasDatos2 = false;
        panelMasDatos3 = false;
        fechaIncorrecta = false;
        fechaIncorrecta2 = false;
        archivoCargado = false;
        errorMontoDiario = false;
    }

    private void mostrarMasDatos2() {
        panelMasDatos = true;
        panelMasDatos2 = true;

        if (listaActividades == null || listaActividades.size() == 0) {
            panelMasDatos3 = false;
        } else {
            panelMasDatos3 = true;
        }

        fechaIncorrecta = false;
        fechaIncorrecta2 = false;
        archivoCargado = false;
        errorMontoDiario = false;
    }

    private void ocultarMasDatos() {
        fechaIncorrecta = true;
        panelMasDatos = false;
        panelMasDatos3 = false;
        archivoCargado = false;
        errorMontoDiario = false;
    }

    private void ocultarMasDatos2() {
        fechaIncorrecta2 = true;
        panelMasDatos2 = false;

        panelMasDatos3 = false;
        errorArchivo = false;
        archivoCargado = false;
        errorMontoDiario = false;
    }

    private void errorArchivo() {
        errorArchivo = true;
        archivoCargado = false;
    }

    private void errorMontoDiario() {
        errorMontoDiario = true;
    }

    private void archivoCargo() {
        archivoCargado = true;
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

    public void seleccionarTipoDocumento() {
        puedeSubirArchivos = true;
        if (tipoDocumentoSel.equals("")) {
            puedeSubirArchivos = false;
        }
    }

    public void eliminarArchivo() {
        listaArchivos.remove(tablaArchivos.getRowIndex());
    }

    public void eliminarArchivoObligatorio() {

        ArchivoMovilidadVE amv = archivoMovilidadVESeleccionado;
        listaArchivosObligatoriosSel.remove(archivoMovilidadVESeleccionado);

        List listaTipoArchivo;
        listaTipoArchivo = new ArrayList();
        listaTipoArchivo = servicioGeneral
                .obtenerListaObjetos("TipoArchivoMovilidad where id ='" + amv.getTipoArchivo().getId() + "'");
        TipoArchivoMovilidad tipoArchivo = (TipoArchivoMovilidad) listaTipoArchivo.get(0);

        List listaMovilidadArchivo;
        listaMovilidadArchivo = new ArrayList();
        listaMovilidadArchivo = servicioGeneral.obtenerListaArchivosMovilidad(restriccionArchivosConv,
                amv.getTipoArchivo().getId());

        MovilidadArchivo mva1 = (MovilidadArchivo) listaMovilidadArchivo.get(0);

    }

    public void validarGrupo() throws SQLException {
        if (!idgrupo.equals("0")) {
            String grupoId = idgrupo;

            String sql = " SELECT COUNT(1) " + " FROM " + " HER_MOVILIDAD_VISITANTES_EXT " + " WHERE "
                    + " MOV_ID_GRUPO ='" + grupoId + "'" + " AND MOV_APROB = 'SI' "
                    + " AND MOV_FEC_SOL BETWEEN TO_DATE('12/01/" + anoAnt + "', 'MM/DD/YYYY') AND TO_DATE('11/30/"
                    + anoAct + "', 'MM/DD/YYYY')";

            int grupoExiste = servicioGeneral.existeMovilidad(sql);

            if (grupoExiste < 0) {
                errores[1] = "El grupo ya tiene aprobada una movilidad para este año.";
                panelErrorGrupo = true;
                mostrarDatosEventoGrupo = false;
            } else {
                errores[1] = "";
                panelErrorGrupo = false;
                mostrarDatosEventoGrupo = true;
            }
        } else {
            panelErrorGrupo = true;
            mostrarDatosEventoGrupo = false;
        }
    }

    public void guardarArchivoMovVE(FileUploadEvent event) {
        archivoObligatorio = event.getFile();
        if (tipoDocumentoSel == null || tipoDocumentoSel.equals("")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar un tipo de archivo", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        ArchivoMovilidadVE archivoMovilidad = new ArchivoMovilidadVE();
        archivoMovilidad = insertarArchivoMovilidadVEGenerico(1, archivoObligatorio, tipoDocumentoSel);
        listaArchivosObligatoriosSel.add(archivoMovilidad);
    }

    public void validarFecha(DateSelectEvent event) throws SQLException {
        Date fechaEvento = event.getDate();
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(fechaEvento);
        int diff = 0;
        while (cal1.before(cal2) || cal1.equals(cal2)) {
            SimpleDateFormat dia = new SimpleDateFormat("EEEE");
            SimpleDateFormat spd = new SimpleDateFormat("dd");
            SimpleDateFormat spm = new SimpleDateFormat("MM");
            SimpleDateFormat spy = new SimpleDateFormat("yyyy");
            cal1.add(Calendar.DATE, 1);
            diff++;
        }
        if (diff >= 30) {
            mostrarMasDatos();
        } else {
            ocultarMasDatos();
        }
    }

    public void validarFechaLlegada(DateSelectEvent event) {
        Date fechaEvento = event.getDate();

        Calendar c = Calendar.getInstance();
        c.setTime(fechaInicioEvento);

        c.add(Calendar.DATE, 9);

        if (!fechaEvento.before(this.fechaInicioEvento) && !fechaEvento.after(c.getTime())) {
            mostrarMasDatos2();

        } else {
            ocultarMasDatos2();
        }

    }

    private void mostrarDatosPersona() {
        mostrarDatosBasicos = true;
        mostrarDatosEvento = true;
        cargarPaises();
        cargarInstituciones();
        cargarProgramasPosgrado();
    }

    private void cargarPaises() {
        List listaPaises = servicioGeneral.obtenerListaObjetosOrdenadosAsc(new Pais(), "nombre");
        paises = new Vector();
        for (Iterator it = listaPaises.iterator(); it.hasNext();) {
            Pais p = (Pais) it.next();
            SelectItem s = new SelectItem(p.getId(), p.getNombre());
            paises.add(s);
        }
    }

    private void cargarTiposActividades() {
        List<TipoActividadMovilidadVE> listaTipoActividad = servicioGeneral
                .obtenerObjetos(TipoActividadMovilidadVE.class, "from TipoActividadMovilidadVE");
        actividadSelItem = new SelectItem[listaTipoActividad.size() - 5];
        int j = 0;
        for (int i = 0; i < listaTipoActividad.size(); i++) {
            TipoActividadMovilidadVE tipoActividad = listaTipoActividad.get(i);
            if (!tipoActividad.getId().equals(new Long(6L)) && !tipoActividad.getId().equals(new Long(8L))
                    && !tipoActividad.getId().equals(new Long(9L)) && !tipoActividad.getId().equals(new Long(3L))
                    && !tipoActividad.getId().equals(new Long(10L))) {
                actividadSelItem[j++] = new SelectItem(tipoActividad.getId().toString(), tipoActividad.getNombre());
            }
        }
        TipoActividadMovilidadVE tipoActividad1 = (TipoActividadMovilidadVE) listaTipoActividad.get(0);
        actividadSel = tipoActividad1.getId().toString();
    }

    private void cargarTiposDocumento() {
//        List<TipoDocumento> listaTipoDocumento = servicioGeneral.obtenerObjetos(TipoDocumento.class,
//                "from TipoDocumento");
    	
    	List<TipoDocumento> listaTipoDocumento = servicioGeneral.obtenerTiposDeDocumento();
    	
        tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
        for (int i = 0; i < listaTipoDocumento.size(); i++) {
            TipoDocumento td = (TipoDocumento) listaTipoDocumento.get(i);
            tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
        }
        tipoDocumento = (TipoDocumento) listaTipoDocumento.get(0);
    }

    private void cargarGrupos(InvestigadorInterno investigadorInterno, String doc) {
        SelectItem stemp = new SelectItem("0", "Seleccione un grupo");
        listaGrupos.add(stemp);
        try {

            List listGruposInvestigador = servicioPersona.obtenerGruposInvestigadorMovilidades(investigadorInterno);

            if (!listGruposInvestigador.isEmpty()) {
                Iterator itgrupos = listGruposInvestigador.iterator();
                while (itgrupos.hasNext()) {
                    InvestigadorGrupo invG = (InvestigadorGrupo) itgrupos.next();
                    Grupo g = (Grupo) invG.getGrupo();

                    if (restriccionLiderGrupo) {
                        if (invG.getTipo().equals("L")) {
                            SelectItem s = new SelectItem(String.valueOf(g.getId()), g.getNombre());
                            listaGrupos.add(s);
                        }
                    } else {
                        SelectItem s = new SelectItem(String.valueOf(g.getId()), g.getNombre());
                        listaGrupos.add(s);
                    }

                }
            } else {
                System.out.println("ManejadorGruposInvestigador:ManejadorGruposInvestigador:Lista de Grupos Vacia");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void adicionarActividad() {
        bErrorActividades = false;
        try {

            temp.setBErrorDescripcion(false);
            temp.setBErrorDuracion(false);
            temp.setBErrorFecha(false);
            if (temp.getDescripcion().equals("")) {
                temp.setBErrorDescripcion(true);
                temp.setErrorDescripcion("La descripción es obligatoria");
                bErrorActividades = true;
            } else {
                temp.setDescripcion(cortarCadena(temp.getDescripcion(), 200));
            }
            if (temp.getDuracion() == null || temp.getDuracion().intValue() == 0) {
                temp.setBErrorDuracion(true);
                temp.setErrorDuracion("La duración es obligatoria");
                bErrorActividades = true;
            }
            if (temp.getFecha() == null) {
                temp.setBErrorFecha(true);
                temp.setErrorFecha("La fecha es obligatoria");
                bErrorActividades = true;
            } else {
                if (temp.getFecha().before(this.fechaInicioEvento)) {
                    bErrorActividades = true;
                    temp.setBErrorFecha(true);

                    temp.setErrorFecha("La fecha debe ser posterior a la fecha de inicio del viaje");
                } else if (temp.getFecha().after(this.fechaFinEvento)) {
                    bErrorActividades = true;
                    temp.setBErrorFecha(true);
                    temp.setErrorFecha("La fecha debe ser anterior a la fecha de fin del viaje");
                }
            }

            if (!bErrorActividades) {
                List<TipoActividadMovilidadVE> listaTipoActividad = servicioGeneral.obtenerObjetos(
                        TipoActividadMovilidadVE.class,
                        "from TipoActividadMovilidadVE where id ='" + actividadSel + "'");
                TipoActividadMovilidadVE sd = new TipoActividadMovilidadVE();
                sd = (TipoActividadMovilidadVE) listaTipoActividad.get(0);
                temp.setTipoActividad(sd);
                listaActividades.add(temp);
                temp = new ActividadMovilidadVE();
                panelMasDatos3 = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void eliminarActividad() {
        listaActividades.remove(actividadMovilidadVESeleccionada);
        if (listaActividades.size() == 0) {
            panelMasDatos3 = false;
        }
    }

    private void cargarInstituciones() {
        listaInstitucion = servicioGeneral.obtenerListaInstituciones();
        institucionItem = new SelectItem[listaInstitucion.size()];
        for (int i = 0; i < listaInstitucion.size(); i++) {
            Institucion in = (Institucion) listaInstitucion.get(i);
            institucionItem[i] = new SelectItem(in.getId(), in.getNombre());
        }
    }

    public void cargarDatosInvestigadores() {
        listaIntegrantesGrupo = new ArrayList();
        List integrantesGrupo = this.servicioGrupo.obtenerIntegrantesGrupo(grupo.getId());
        for (int i = 0; i < integrantesGrupo.size(); i++) {
            InvestigadorGrupo investigadorGrupo = (InvestigadorGrupo) integrantesGrupo.get(i);
            if (investigadorGrupo.getTipo().equals(InvestigadorGrupo.LIDER))
                responsableGrupo = investigadorGrupo.getInvestigador();
            else
                listaIntegrantesGrupo.add(investigadorGrupo.getInvestigador());
        }

    }

    public void limpiar() {
        persona = new Persona();
        documento = new String("");
        // sesion.removeAttribute("ManejadorCrearEditarMovilidadVisitante");
        cargarValoresIniciales();
        ocultarPaneles();
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

    public boolean montoDiarioDolares() {
        boolean result = true;/*
                               * List listaParametros =
                               * servicioGeneral.obtenerListaObjetos("Parametro"
                               * ); for (Iterator itParametro =
                               * listaParametros.iterator(); itParametro
                               * .hasNext();) { Parametro p = (Parametro)
                               * itParametro.next(); Long id = new Long("4"); if
                               * (p.getId().longValue() == id.longValue()) {
                               * pMontoDiario = p.getValor(); } } Long temp =
                               * new Long(montoDiario); Long temp1 = new
                               * Long(pMontoDiario);
                               * 
                               * if (temp.longValue() <= temp1.longValue()) {
                               * result = true; } else { result = false; }
                               */

        return result;
    }

    public void guardarConvocatoriaFacultad() {

        errorMontoDiario = false;
        if (montoDiarioDolares()) {
            Calendar actual = Calendar.getInstance();
            Date date = actual.getTime();

            mve.setFechasolicitud(date);
            mve.setEvento(cortarCadena(eventoDifusion, 100));
            mve.setTitulo(tituloPonencia);
            mve.setCostotiquete(costoTiquetes);
            mve.setResumen(resumenPonencia);
            mve.setInscripcionevento(solInscripcion);
            mve.setCostoevento(costoInscripcion);
            mve.setAportefacultad(aporteFacultad);
            mve.setResolucionviaje(aceptacionPonencia);
            mve.setFechainicial(fechaInicioEvento);
            mve.setFechafinal(fechaFinEvento);
            mve.setAceptacion(aceptacionFacultad);
            mve.setCiudad(ciudadProcedencia);
            mve.setUniversidad(cortarCadena(institucion, 255));
            mve.setPlan(cortarCadena(ruta, 2000));
            mve.setMovilidadConvocatoriaFacultad("S");

            try {
                mve.setMonto(Long.valueOf(montoDiario.trim()));
            } catch (NumberFormatException nfe) {
                mve.setMonto(0L);
                errorMontoDiario = true;
            }

            try {
                mve.setNumerodias(Double.valueOf(numeroDias.trim()));
            } catch (NumberFormatException nfe) {
                mve.setNumerodias(0d);
                errorMontoDiario = true;
            }
            mve.setDocumentoVisitante(cortarCadena(documentoVisitante, 100));

            mve.setNombreVisitante(cortarCadena(nombreDocente, 255));

            mve.setEmailVisitante(cortarCadena(emailVisitante, 60));

            mve.setNacionalidad(cortarCadena(nacionalidad, 255));

            Set act = new HashSet();
            for (Iterator it = listaActividades.iterator(); it.hasNext();) {
                ActividadMovilidadVE a = (ActividadMovilidadVE) it.next();

                act.add(a);
            }
            mve.setActividades(act);

            List listaTipoMovilidad = new ArrayList();
            listaTipoMovilidad = servicioGeneral.obtenerListaObjetos("TipoMovilidad where id ='CF_MOV7'");
            TipoMovilidad tm = new TipoMovilidad();
            tm = (TipoMovilidad) listaTipoMovilidad.get(0);
            mve.setTipoMovilidad(tm);

            List listaSede = new ArrayList();
            listaSede = servicioGeneral.obtenerListaObjetos("Sede where id ='" + sede + "'");
            Sede sd = new Sede();
            sd = (Sede) listaSede.get(0);

            mve.setSede(sd);

            if (validar(mve) && !errorMontoDiario) {
                Persona personaAux = new Persona();
                IdPersona idp = new IdPersona();

                idp.setDocumento(documentoDocente);
                idp.setTipoDocumento(tipoDocDocente);
                personaAux = servicioPersona.obtenerPersona(idp);

                mve.setPersonaInv(personaAux);

                List listaPais = new ArrayList();
                listaPais = servicioGeneral.obtenerListaObjetos("Pais where id ='" + paisProcedencia + "'");
                Pais pais = new Pais();
                pais = (Pais) listaPais.get(0);

                mve.setPais(pais);

                List listaPrograma = new ArrayList();
                listaPrograma = servicioGeneral.obtenerListaObjetos("PlanEstudios where id ='" + idPrograma + "'");
                PlanEstudios programa = new PlanEstudios();
                programa = (PlanEstudios) listaPrograma.get(0);

                mve.setIdprograma(programa);

                List listaGrupo = new ArrayList();
                listaGrupo = servicioGeneral.obtenerListaObjetos("Grupo where id ='" + idgrupo + "'");
                Grupo grupo = new Grupo();
                grupo = (Grupo) listaGrupo.get(0);

                mve.setGrupo(grupo);
                if (sesion.getAttribute("idConvocatoriaActual") != null) {
                    Long idConvocatoria = (Long) sesion.getAttribute("idConvocatoriaActual");
                    mve.setConvocatoriaId(idConvocatoria.toString());
                }

                Dependencia dependencia;
                dependencia = new Dependencia();

                if (personaAux instanceof Investigador) {
                    if (personaAux instanceof InvestigadorInterno) {
                        personaAux = servicioPersona.obtenerInvestigadorInternoCompleto(personaAux.getId());
                        InvestigadorInterno investigadorInterno = (InvestigadorInterno) personaAux;
                        dependencia = servicioDependencia.obtenerDependencia(investigadorInterno.getId());
                    }
                }

                mve.setDependencia(dependencia);

                List listaParametro;
                listaParametro = new ArrayList();

                listaParametro = this.servicioGeneral
                        .obtenerObjetos("FROM Parametro WHERE nombre = '" + dependencia.getSede().getId() + "'");

                if (listaParametro == null || listaParametro.size() == 0) {
                    mve.setAceptacion("SI");
                }

                servicioGeneral.guardarObjeto(mve);

                if (listaArchivosObligatoriosSel != null && listaArchivosObligatoriosSel.size() > 0) {
                    for (int i = 0; i < listaArchivosObligatoriosSel.size(); i++) {
                        ArchivoMovilidadVE amovAux1 = (ArchivoMovilidadVE) listaArchivosObligatoriosSel.get(i);
                        amovAux1.setMovilidad(mve);
                        servicioGeneral.guardarObjeto(amovAux1);
                    }
                }

                personaActual = (Persona) sesion.getAttribute("persona");
                String dirCorreoConfirmacion = personaActual.getEmail();

                correoActual = cargarPlantilla(65);
                editarCorreo(personaAux, mve);
                Correo correo = new Correo();
                correo.setOrigen(Correo.CORREO_HERMES);
                String dirCorreo = personaAux.getEmail();
                correo.adicionarDireccion(dirCorreo);
                correo.adicionarCopiaOculta(new String(personaAux.getEmail()));

                correo.adicionarCopiaOculta(dirCorreoConfirmacion);
                correo.setAsunto(correoActual.getAsunto());
                correo.setCuerpo(cuerpoCorreo);
                boolean correo2 = false;
                boolean correo1 = servicioCorreo.enviarCorreo(correo);

                List listaCorreoEncargado = new ArrayList();
                List listaParametroAux;
                listaParametroAux = new ArrayList();

                Dependencia dependenciaAux;
                dependenciaAux = new Dependencia();
                Persona personaEnvio = new Persona();

                personaEnvio = servicioPersona.obtenerInvestigadorInternoCompleto(mve.getPersonaInv().getId());

                InvestigadorInterno investigadorInterno = (InvestigadorInterno) personaEnvio;
                dependenciaAux = servicioDependencia.obtenerDependencia(investigadorInterno.getId());

                if (listaParametro == null || listaParametro.size() == 0) {
                    listaParametroAux = this.servicioGeneral
                            .obtenerObjetos("FROM Parametro WHERE nombre = '" + dependencia.getSede().getId() + "'");
                    correoActual = cargarPlantilla(86);

                    listaCorreoEncargado = this.servicioGeneral
                            .obtenerObjetos("FROM Parametro WHERE nombre = 'R_MOVILIDAD'    AND DESCRIPCION= '"
                                    + dependenciaAux.getSede().getId() + "'");
                    InvestigadorInterno ii;
                    if (listaCorreoEncargado != null && listaCorreoEncargado.size() > 0) {
                        Parametro para = (Parametro) listaCorreoEncargado.get(0);
                        ii = servicioPersona
                                .obtenerInvestigadorInterno(new IdPersona(para.getValor(), para.getProfesion()));
                    } else {
                        ii = servicioPersona.obtenerInvestigadorInterno(new IdPersona("19380666", "C"));
                    }

                    listaCorreoEncargado = new ArrayList();
                    listaCorreoEncargado.add(ii);

                } else {

                    listaParametroAux = this.servicioGeneral
                            .obtenerObjetos("FROM Parametro WHERE nombre = '" + dependencia.getSede().getId() + "'");
                    correoActual = cargarPlantilla(85);

                    try {
                        String consult = "select i from InvestigadorInterno i, " + " PersonaRol pr "
                                + " where i.id.documento= pr.documento " + " and i.id.tipoDocumento= pr.tipoDocumento "
                                + " and pr.nombre = 'MF' and i.dependencia.facultad.id = '"
                                + dependencia.getFacultad().getId() + "' "
                                + " and i.dependencia.facultad.esFacultad = 'Y' and (pr.fechaFinRol IS NULL OR pr.fechaFinRol >= CURRENT_DATE) ";
                        listaCorreoEncargado = servicioGeneral.obtenerObjetos(consult);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                String correoEnvio = "sisii_nal@unal.edu.co";
                Persona personaActualAux2 = new Persona();

                if (listaCorreoEncargado != null && listaCorreoEncargado.size() > 0) {
                    // Parametro paActual = (Parametro)
                    // listaCorreoEncargado.get(0);
                    int numCoord = listaCorreoEncargado.size();

                    for (int i = 0; i < numCoord; i++) {

                        InvestigadorInterno paActual = (InvestigadorInterno) listaCorreoEncargado.get(i);
                        String numeroDocumento = "0";
                        // numeroDocumento = paActual.getValor();
                        numeroDocumento = paActual.getId().getDocumento();
                        IdPersona id = new IdPersona();
                        id.setDocumento(numeroDocumento);
                        // id.setTipoDocumento(paActual.getProfesion());
                        id.setTipoDocumento(paActual.getId().getTipoDocumento());
                        personaActualAux2 = servicioPersona.obtenerPersona(id);

                        // System.out.println("personaActualAux2 "+
                        // personaActualAux2.getEmail());
                        if (personaActualAux2.getEmail() != null && !personaActualAux2.getEmail().equals("")) {

                            correoEnvio = personaActualAux2.getEmail();
                        } else {
                            correoActual = cargarPlantilla(87);
                        }

                        editarCorreo(personaActualAux2, mve);
                        correo = new Correo();
                        correo.setOrigen(Correo.CORREO_HERMES);
                        dirCorreo = correoEnvio;
                        correo.adicionarDireccion(dirCorreo);
                        correo.adicionarCopiaOculta(dirCorreo);
                        correo.setAsunto(correoActual.getAsunto());
                        correo.setCuerpo(cuerpoCorreo);
                        correo2 = servicioCorreo.enviarCorreo(correo);
                    }

                } else {
                    correoActual = cargarPlantilla(87);
                    editarCorreo(personaActualAux2, mve);
                    correo = new Correo();
                    correo.setOrigen(Correo.CORREO_HERMES);
                    dirCorreo = correoEnvio;
                    correo.adicionarDireccion(dirCorreo);
                    correo.adicionarCopiaOculta(dirCorreo);
                    correo.setAsunto(correoActual.getAsunto());
                    correo.setCuerpo(cuerpoCorreo);
                    correo2 = servicioCorreo.enviarCorreo(correo);
                    servicioCorreo.enviarCorreo(correo);
                }

                limpiar();
//                if (correo1 && correo2) {
//                	noExiste = "Solicitud de movilidad guardada correctamente con el código " + mve.getId() + ". Un correo electrónico confirmando su registro se ha enviado a su cuenta de correo electrónico.";
//                } else {
//                    noExiste = "Solicitud de movilidad guardada correctamente con el código " + mve.getId() + ". Ha ocurrido un error en el momento de envio del correo electrónico.";
//                }
                
                noExiste = "Solicitud de movilidad guardada correctamente, con el número " + mve.getId()
				+ ".  Se ha enviado a su correo electrónico un mensaje confirmando el registro. Si lo considera necesario comuníquese con la "
				+ "Coordinación de Investigación de la facultad o sede a la que pertenece para la revisión de la movilidad. ";
                
                /*noExiste = "La movilidad con código" + mve.getId() + "se ha guardado y enviado correctamente al usuario a través del "
    					+ "sistema Hermes; sin embargo, no se ha enviado correo, les recomendamos comunicarse con "
    					+ "la Coordinación de Investigación de la facultad o sede a la que pertenece para realizar "
    					+ "la revisión de la movilidad. No hay necesidad de enviar nuevamente la movilidad";*/

                mostrarPanelNoExiste();
                bImprimirReporte = true;
            } else {
                mostrarError = true;
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msgError, msgError);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else {
            errorMontoDiario();
        }
    }

    public void guardar() {
        errorMontoDiario = false;
        if (montoDiarioDolares()) {
            Calendar actual = Calendar.getInstance();
            Date date = actual.getTime();

            mve.setFechasolicitud(date);
            mve.setEvento(cortarCadena(eventoDifusion, 100));
            mve.setTitulo(tituloPonencia);
            mve.setCostotiquete(costoTiquetes);
            mve.setResumen(resumenPonencia);
            mve.setInscripcionevento(solInscripcion);
            mve.setCostoevento(costoInscripcion);
            mve.setAportefacultad(aporteFacultad);
            mve.setResolucionviaje(aceptacionPonencia);
            mve.setFechainicial(fechaInicioEvento);
            mve.setFechafinal(fechaFinEvento);
            mve.setAceptacion(aceptacionFacultad);
            mve.setCiudad(ciudadProcedencia);
            mve.setUniversidad(cortarCadena(institucion, 255));
            mve.setPlan(cortarCadena(ruta, 2000));
            mve.setMovilidadConvocatoriaFacultad("S");
            try {
                mve.setMonto(Long.valueOf(montoDiario.trim()));
            } catch (NumberFormatException nfe) {
                mve.setMonto(0L);
                errorMontoDiario = true;
            }

            try {
                mve.setNumerodias(Double.valueOf(numeroDias.trim()));
            } catch (NumberFormatException nfe) {
                mve.setNumerodias(0d);
                errorMontoDiario = true;
            }
            mve.setDocumentoVisitante(cortarCadena(documentoVisitante, 100));

            mve.setNombreVisitante(cortarCadena(nombreDocente, 255));

            mve.setEmailVisitante(cortarCadena(emailVisitante, 60));

            mve.setNacionalidad(cortarCadena(nacionalidad, 255));

            Set act = new HashSet();
            for (Iterator it = listaActividades.iterator(); it.hasNext();) {
                ActividadMovilidadVE a = (ActividadMovilidadVE) it.next();

                act.add(a);
            }
            mve.setActividades(act);

            List listaTipoMovilidad = new ArrayList();
            listaTipoMovilidad = servicioGeneral.obtenerListaObjetos("TipoMovilidad where id ='CF_MOV7'");
            TipoMovilidad tm = new TipoMovilidad();
            tm = (TipoMovilidad) listaTipoMovilidad.get(0);
            mve.setTipoMovilidad(tm);

            List listaSede = new ArrayList();
            listaSede = servicioGeneral.obtenerListaObjetos("Sede where id ='" + sede + "'");
            Sede sd = new Sede();
            sd = (Sede) listaSede.get(0);

            mve.setSede(sd);

            if (validar(mve) && !errorMontoDiario) {
                Persona personaAux = new Persona();
                IdPersona idp = new IdPersona();

                idp.setDocumento(documentoDocente);
                idp.setTipoDocumento(tipoDocDocente);
                personaAux = servicioPersona.obtenerPersona(idp);

                mve.setPersonaInv(personaAux);

                List listaPais = new ArrayList();
                listaPais = servicioGeneral.obtenerListaObjetos("Pais where id ='" + paisProcedencia + "'");
                Pais pais = new Pais();
                pais = (Pais) listaPais.get(0);

                mve.setPais(pais);

                List listaPrograma = new ArrayList();
                listaPrograma = servicioGeneral.obtenerListaObjetos("PlanEstudios where id ='" + idPrograma + "'");
                PlanEstudios programa = new PlanEstudios();
                programa = (PlanEstudios) listaPrograma.get(0);

                mve.setIdprograma(programa);

                List listaGrupo = new ArrayList();
                listaGrupo = servicioGeneral.obtenerListaObjetos("Grupo where id ='" + idgrupo + "'");
                Grupo grupo = new Grupo();
                grupo = (Grupo) listaGrupo.get(0);

                mve.setGrupo(grupo);
                if (sesion.getAttribute("idConvocatoriaActual") != null) {
                    Long idConvocatoria = (Long) sesion.getAttribute("idConvocatoriaActual");
                    mve.setConvocatoriaId(idConvocatoria.toString());
                }

                Dependencia dependencia;
                dependencia = new Dependencia();

                if (personaAux instanceof Investigador) {
                    if (personaAux instanceof InvestigadorInterno) {
                        personaAux = servicioPersona.obtenerInvestigadorInternoCompleto(personaAux.getId());
                        InvestigadorInterno investigadorInterno = (InvestigadorInterno) personaAux;
                        dependencia = servicioDependencia.obtenerDependencia(investigadorInterno.getId());
                    }
                }

                mve.setDependencia(dependencia);

                List listaParametro;
                listaParametro = new ArrayList();

                listaParametro = this.servicioGeneral
                        .obtenerObjetos("FROM Parametro WHERE nombre = '" + dependencia.getSede().getId() + "'");

                if (listaParametro == null || listaParametro.size() == 0) {
                    mve.setAceptacion("SI");
                }

                servicioGeneral.guardarObjeto(mve);

                if (listaArchivosObligatoriosSel != null && listaArchivosObligatoriosSel.size() > 0) {
                    for (int i = 0; i < listaArchivosObligatoriosSel.size(); i++) {
                        ArchivoMovilidadVE amovAux1 = (ArchivoMovilidadVE) listaArchivosObligatoriosSel.get(i);
                        amovAux1.setMovilidad(mve);
                        servicioGeneral.guardarObjeto(amovAux1);
                    }
                }

                personaActual = (Persona) sesion.getAttribute("persona");
                String dirCorreoConfirmacion = personaActual.getEmail();

                correoActual = cargarPlantilla(65);
                editarCorreo(personaAux, mve);
                Correo correo = new Correo();
                correo.setOrigen(Correo.CORREO_HERMES);
                String dirCorreo = personaAux.getEmail();
                correo.adicionarDireccion(dirCorreo);
                correo.adicionarCopiaOculta(new String(personaAux.getEmail()));

                correo.adicionarCopiaOculta(dirCorreoConfirmacion);
                correo.setAsunto(correoActual.getAsunto());
                correo.setCuerpo(cuerpoCorreo);
                boolean correo2 = false;
                boolean correo1 = servicioCorreo.enviarCorreo(correo);

                List listaCorreoEncargado = new ArrayList();
                List listaParametroAux;
                listaParametroAux = new ArrayList();

                Dependencia dependenciaAux;
                dependenciaAux = new Dependencia();
                Persona personaEnvio = new Persona();

                personaEnvio = servicioPersona.obtenerInvestigadorInternoCompleto(mve.getPersonaInv().getId());

                InvestigadorInterno investigadorInterno = (InvestigadorInterno) personaEnvio;
                dependenciaAux = servicioDependencia.obtenerDependencia(investigadorInterno.getId());

                if (listaParametro == null || listaParametro.size() == 0) {
                    listaParametroAux = this.servicioGeneral
                            .obtenerObjetos("FROM Parametro WHERE nombre = '" + dependencia.getSede().getId() + "'");
                    correoActual = cargarPlantilla(86);

                    listaCorreoEncargado = this.servicioGeneral
                            .obtenerObjetos("FROM Parametro WHERE nombre = 'R_MOVILIDAD'    AND DESCRIPCION= '"
                                    + dependenciaAux.getSede().getId() + "'");
                    InvestigadorInterno ii;
                    if (listaCorreoEncargado != null && listaCorreoEncargado.size() > 0) {
                        Parametro para = (Parametro) listaCorreoEncargado.get(0);
                        ii = servicioPersona
                                .obtenerInvestigadorInterno(new IdPersona(para.getValor(), para.getProfesion()));
                    } else {
                        ii = servicioPersona.obtenerInvestigadorInterno(new IdPersona("19380666", "C"));
                    }

                    listaCorreoEncargado = new ArrayList();
                    listaCorreoEncargado.add(ii);

                } else {

                    listaParametroAux = this.servicioGeneral
                            .obtenerObjetos("FROM Parametro WHERE nombre = '" + dependencia.getSede().getId() + "'");
                    correoActual = cargarPlantilla(85);

                    try {
                        String consult = "select i from InvestigadorInterno i, " + " PersonaRol pr "
                                + " where i.id.documento= pr.documento " + " and i.id.tipoDocumento= pr.tipoDocumento "
                                + " and pr.nombre = 'MF' and i.dependencia.facultad.id = '"
                                + dependencia.getFacultad().getId() + "' "
                                + " and i.dependencia.facultad.esFacultad = 'Y' and (pr.fechaFinRol IS NULL OR pr.fechaFinRol >= CURRENT_DATE) ";
                        listaCorreoEncargado = servicioGeneral.obtenerObjetos(consult);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                String correoEnvio = "sisii_nal@unal.edu.co";
                Persona personaActualAux2 = new Persona();

                if (listaCorreoEncargado != null && listaCorreoEncargado.size() > 0) {
                    // Parametro paActual = (Parametro)
                    // listaCorreoEncargado.get(0);
                    int numCoord = listaCorreoEncargado.size();

                    for (int i = 0; i < numCoord; i++) {

                        InvestigadorInterno paActual = (InvestigadorInterno) listaCorreoEncargado.get(i);
                        String numeroDocumento = "0";
                        // numeroDocumento = paActual.getValor();
                        numeroDocumento = paActual.getId().getDocumento();
                        IdPersona id = new IdPersona();
                        id.setDocumento(numeroDocumento);
                        // id.setTipoDocumento(paActual.getProfesion());
                        id.setTipoDocumento(paActual.getId().getTipoDocumento());
                        personaActualAux2 = servicioPersona.obtenerPersona(id);

                        // System.out.println("personaActualAux2 "+
                        // personaActualAux2.getEmail());
                        if (personaActualAux2.getEmail() != null && !personaActualAux2.getEmail().equals("")) {

                            correoEnvio = personaActualAux2.getEmail();
                        } else {
                            correoActual = cargarPlantilla(87);
                        }

                        editarCorreo(personaActualAux2, mve);
                        correo = new Correo();
                        correo.setOrigen(Correo.CORREO_HERMES);
                        dirCorreo = correoEnvio;
                        correo.adicionarDireccion(dirCorreo);
                        correo.adicionarCopiaOculta(dirCorreo);
                        correo.setAsunto(correoActual.getAsunto());
                        correo.setCuerpo(cuerpoCorreo);
                        correo2 = servicioCorreo.enviarCorreo(correo);
                    }

                } else {
                    correoActual = cargarPlantilla(87);
                    editarCorreo(personaActualAux2, mve);
                    correo = new Correo();
                    correo.setOrigen(Correo.CORREO_HERMES);
                    dirCorreo = correoEnvio;
                    correo.adicionarDireccion(dirCorreo);
                    correo.adicionarCopiaOculta(dirCorreo);
                    correo.setAsunto(correoActual.getAsunto());
                    correo.setCuerpo(cuerpoCorreo);
                    correo2 = servicioCorreo.enviarCorreo(correo);
                    servicioCorreo.enviarCorreo(correo);
                }

                limpiar();
//                if (correo1 && correo2) {
//                    noExiste = "Solicitud de movilidad guardada correctamente con el código " + mve.getId() + ". Un correo electrónico confirmando su registro se ha enviado a su cuenta de correo electrónico.";
//                } else {
//                    noExiste = "Solicitud de movilidad guardada correctamente con el código " + mve.getId() + ". Ha ocurrido un error en el momento de envio del correo electrónico.";
//                }
                
                noExiste = "La movilidad con código" + mve.getId() + "se ha guardado y enviado correctamente al usuario a través del "
    					+ "sistema Hermes; sin embargo, no se ha enviado correo, les recomendamos comunicarse con "
    					+ "la Coordinación de Investigación de la facultad o sede a la que pertenece para realizar "
    					+ "la revisión de la movilidad. No hay necesidad de enviar nuevamente la movilidad";

                mostrarPanelNoExiste();
                bImprimirReporte = true;
            } else {
                mostrarError = true;
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msgError, msgError);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else {
            errorMontoDiario();
        }

    }

    public String editarCorreo(Persona personaAux, MovilidadVisitanteExterior mov) {

        try {
            String coinvNombre = "";
            // CorreoPlantilla cp = CorreoPlantilla(57);
            // String correo = correoActual.getCuerpo().replaceAll("<<FECHA>>",
            // Fecha.fechaActual());
            String correo = correoActual.getCuerpo();

            String investigador = "";
            // if (inv != null && inv.size() > 0) {
            // Persona e = (Persona) inv.get(0);
            investigador = personaAux.getNombre1() + " " + personaAux.getApellido1() + " " + personaAux.getApellido2();
            correo = correo.replaceAll("<<INVESTIGADOR>>", investigador);
            // }

            correo = correo.replaceAll("<<IDMOVILIDAD>>", mov.getId().toString());
            correo = correo.replaceAll("<<TIPO>>", mov.getTipoMovilidad().getNombre());

            cuerpoCorreo = correo;

            // cuerpoCorreo2 = investigador + " " + coinvNombre;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return "";
    }

    public CorreoPlantilla cargarPlantilla(int cod_id) {

        CorreoPlantilla correoActualAux = new CorreoPlantilla();
        // CorreoPlantilla correoActualAux=(CorreoPlantilla)
        CorreoPlantilla a = new CorreoPlantilla();
        // CorreoPlantilla correoActualAux3 = (CorreoPlantilla) servicioGeneral
        // .obtenerObjeto(a, Long.valueOf(String.valueOf(cod_id)));
        List lista = servicioGeneral.obtenerObjetos("select c from CorreoPlantilla c where c.id='" + cod_id + "'");
        if (lista != null && lista.size() > 0) {
            correoActualAux = (CorreoPlantilla) lista.get(0);
        }
        // String
        // correo=correoActual.getCuerpo().replaceAll("<<fecha>>",Fecha.fechaActual());
        return correoActualAux;
    }

    private boolean validar(MovilidadVisitanteExterior mov) {
        mostrarError = false;
        boolean result = true;
        bErrorGrupo = false;
        bErrorPrograma = false;
        bErrorDocente = false;
        bErrorEmail = false;
        bErrorDocumento = false;
        bErrorNacionalidad = false;
        bErrorPais = false;
        bErrorCiudad = false;
        bErrorInstitucion = false;
        bErrorRuta = false;
        bErrorActividades = false;
        bErrorEvento = false;
        bErrorTiquete = false;
        bErrorMonto = false;
        bErrorDias = false;
        bErrorDocumentos = false;
        bErrorHoja = false;
        bImprimirReporte = false;

        if (idgrupo.equals("0")) {
            String error = "El grupo es obligatorio \n";
            bErrorGrupo = true;
            errores[0] = error;
            result = false;
        }
        if (idPrograma.equals("0")) {
            String error = "El nombre del programa es obligatorio \n";
            errores[1] = error;
            bErrorPrograma = true;
            result = false;
        }
        if (mov.getNombreVisitante().equals("")) {
            String error = "El nombre del docente visitante es obligatorio \n";
            errores[2] = error;
            bErrorDocente = true;
            result = false;
        }
        if (mov.getEmailVisitante().equals("")) {
            String error = "El email del docente visitante es obligatorio \n";
            errores[14] = error;
            bErrorEmail = true;
            result = false;
        }
        if (mov.getDocumentoVisitante().equals("")) {
            String error = "El documento es obligatorio \n";
            errores[3] = error;
            bErrorDocumento = true;
            result = false;
        }
        if (mov.getNacionalidad().equals("")) {
            String error = "La nacionalidad es obligatoria \n";
            errores[4] = error;
            bErrorNacionalidad = true;
            result = false;
        }
        if (paisProcedencia.equals("00")) {
            String error = "El pais es obligatorio";
            errores[5] = error;
            bErrorPais = true;
            result = false;
        }
        if (mov.getCiudad().equals("")) {
            String error = "La ciudad es obligatoria";
            errores[15] = error;
            bErrorCiudad = true;
            result = false;
        }
        if (mov.getUniversidad().equals("")) {
            String error = "La institución es obligatoria";
            errores[6] = error;
            bErrorInstitucion = true;
            result = false;
        }
        if (mov.getPlan().equals("")) {
            String error = "El plan es obligatorio";
            errores[7] = error;
            bErrorRuta = true;
            result = false;
        }
        if (mov.getActividades().size() == 1) {
            List tempAct = new ArrayList();
            Iterator ite = listaActividades.iterator();
            while (ite.hasNext()) {
                ActividadMovilidadVE temp = (ActividadMovilidadVE) ite.next();
                temp.setBErrorDescripcion(false);
                temp.setBErrorDuracion(false);
                temp.setBErrorFecha(false);
                if (temp.getDescripcion().equals("")) {
                    temp.setBErrorDescripcion(true);
                    temp.setErrorDescripcion("La descripción es obligatoria");
                    bErrorActividades = true;
                    result = false;
                }

                if (temp.getDuracion().intValue() == 0) {
                    temp.setBErrorDuracion(true);
                    temp.setErrorDuracion("La duración es obligatoria");
                    bErrorActividades = true;
                    result = false;
                }

                if (temp.getFecha() == null) {
                    temp.setBErrorFecha(true);
                    temp.setErrorFecha("La fecha es obligatoria");
                    bErrorActividades = true;
                    result = false;
                } else {
                    if (temp.getFecha().before(this.fechaInicioEvento)) {
                        temp.setBErrorFecha(true);
                        temp.setErrorFecha("La fecha debe ser posterior a la fecha de inicio del viaje");
                        bErrorActividades = true;
                        result = false;
                    } else if (temp.getFecha().after(this.fechaFinEvento)) {
                        temp.setBErrorFecha(true);
                        temp.setErrorFecha("La fecha debe ser anterior a la fecha de fin del viaje");
                        bErrorActividades = true;
                        result = false;
                    }
                }
                tempAct.add(temp);
            }
            listaActividades = tempAct;
            // String error = "Debe ingresar por lo menos una actividad";
            // errores[8] = error;
            // bErrorActividades = true;
        }
        if (mov.getEvento().equals("")) {
            String error = "El evento es obligatorio";
            errores[9] = error;
            bErrorEvento = true;
            result = false;
        }
        if (mov.getCostotiquete() == null) {
            String error = "El costo de tiquete es obligatorio \n";
            errores[10] = error;
            bErrorTiquete = true;
            result = false;
        } else if (mov.getCostotiquete().longValue() > 100000000) {
            String error = "El costo del tiquete no puede ser superior a 100.000.000 \n";
            errores[10] = error;
            bErrorTiquete = true;
            result = false;
        }

        if (mov.getMonto() == null) {
            String error = "El monto diario es obligatorio";
            errores[11] = error;
            bErrorMonto = true;
            result = false;
        }

        if (mov.getNumerodias() == null) {
            String error = "El número de días es obligatorio \n";
            errores[12] = error;
            bErrorDias = true;
            result = false;
        } else if (mov.getNumerodias().intValue() > 10) {
            String error = "El número de días no puede ser superior a 10 \n";
            errores[12] = error;
            bErrorDias = true;
            result = false;
        }
        
        ArrayList<TipoArchivoMovilidad> listaTipoArchObligatorios = new ArrayList<TipoArchivoMovilidad>();
        ArrayList<TipoArchivoMovilidad> listaTipoArchObligatoriosReg = new ArrayList<TipoArchivoMovilidad>();
        ArrayList<TipoArchivoMovilidad> listaTipoArchObligatoriosFaltantes = new ArrayList<TipoArchivoMovilidad>();
        
        for(int i = 0; i < listaArchivosObligatorios.size(); i++){
        	MovilidadArchivo mam = (MovilidadArchivo) listaArchivosObligatorios.get(i);
        	if(mam.getEsObligatorio().equals(1L)){
        		listaTipoArchObligatorios.add(mam.getTipoArchivo());
        	}
        }
        
        for (int j = 0; j < listaArchivosObligatoriosSel.size(); j++) {
        	ArchivoMovilidadVE mva = (ArchivoMovilidadVE) listaArchivosObligatoriosSel.get(j);
        	TipoArchivoMovilidad tam = mva.getTipoArchivo();
        	listaTipoArchObligatoriosReg.add(tam);
        }
        
        for (int j = 0; j < listaTipoArchObligatorios.size(); j++) {
        	TipoArchivoMovilidad tam = listaTipoArchObligatorios.get(j);
        	if(!listaTipoArchObligatoriosReg.contains(tam)){
        		listaTipoArchObligatoriosFaltantes.add(tam);
        	}
        }
        
        if(listaTipoArchObligatoriosFaltantes.size() > 0){        	
        	String error = "Debe adjuntar los documentos necesarios para esta modalidad: \n";
        	for (int j = 0; j < listaTipoArchObligatoriosFaltantes.size(); j++) {
        		TipoArchivoMovilidad tam = listaTipoArchObligatoriosFaltantes.get(j);
        		error += tam.getNombre() + " \n";
        	}        	 
        	errores[13] = error;
            bErrorDocumentos = true;
            result = false;
        }


        return result;
    }


    public void enviarCorreo() {
        Correo correo = new Correo();
        correo.adicionarDireccion("iabohorquezc@unal.edu.co");
        correo.setOrigen(personaActual.getEmail());
        correo.adicionarCopiaOculta(new String(personaActual.getEmail()));
        String asunto = "Nueva movilidad de visitante internacional";

        correo.setAsunto(asunto);// getCorreoActual().getAsunto());//"Quiere ser
                                 // posible evaluador");
        String cuerpo = "Se ha adicionado una nueva movilidad para visitante internacional.";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm.ss");

        Calendar actual = Calendar.getInstance();
        Date date = actual.getTime();
        String strDate = formatter.format(date);
        cuerpo = cuerpo + strDate;
        correo.setCuerpo(cuerpo);// "por favoooooooor, le pagamos y todo");
        String mensajeCorreo;
        /*
         * if(servicioCorreo.enviarCorreo(correo)){ mensajeCorreo =
         * "Su correo ha sido enviado con exito al posible evaluador con copia a "
         * +persona.getEmail(); }else{ mensajeCorreo =
         * "No se pudo enviar el correo, por favor verifique la dirección electronica"
         * ; }
         */
        // System.out.println(mensajeCorreo);
    }

    public Long getDestinado() {
        return destinado;
    }

    public void setDestinado(Long destinado) {
        this.destinado = destinado;
    }

    public boolean isMostrarDatosBasicos() {
        return mostrarDatosBasicos;
    }

    public void setMostrarDatosBasicos(boolean mostrarDatosBasicos) {
        this.mostrarDatosBasicos = mostrarDatosBasicos;
    }

    public boolean isIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(boolean identificacion) {
        this.identificacion = identificacion;
    }

    public SelectItem[] getDepsItem() {
        return depsItem;
    }

    public void setDepsItem(SelectItem[] depsItem) {
        this.depsItem = depsItem;
    }

    public List getListaDependencias() {
        return listaDependencias;
    }

    public void setListaDependencias(List listaDependencias) {
        this.listaDependencias = listaDependencias;
    }

    public String getSedeSeleccionada() {
        return sedeSeleccionada;
    }

    public void setSedeSeleccionada(String sedeSeleccionada) {
        this.sedeSeleccionada = sedeSeleccionada;
    }

    public String getNombreSedeSeleccionada() {
        return nombreSedeSeleccionada;
    }

    public void setNombreSedeSeleccionada(String nombreSedeSeleccionada) {
        this.nombreSedeSeleccionada = nombreSedeSeleccionada;
    }

    public String getFacultadSeleccionada() {
        return facultadSeleccionada;
    }

    public void setFacultadSeleccionada(String facultadSeleccionada) {
        this.facultadSeleccionada = facultadSeleccionada;
    }

    public String getNombreFacultadSeleccionada() {
        return nombreFacultadSeleccionada;
    }

    public void setNombreFacultadSeleccionada(String nombreFacultadSeleccionada) {
        this.nombreFacultadSeleccionada = nombreFacultadSeleccionada;
    }

    public String getDepartamentoSeleccionado() {
        return departamentoSeleccionado;
    }

    public void setDepartamentoSeleccionado(String departamentoSeleccionado) {
        this.departamentoSeleccionado = departamentoSeleccionado;
    }

    public String getNombreDepartamentoSeleccionado() {
        return nombreDepartamentoSeleccionado;
    }

    public void setNombreDepartamentoSeleccionado(String nombreDepartamentoSeleccionado) {
        this.nombreDepartamentoSeleccionado = nombreDepartamentoSeleccionado;
    }

    public SelectItem[] getTipoDocumentoItem() {
        return tipoDocumentoItem;
    }

    public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
        this.tipoDocumentoItem = tipoDocumentoItem;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(String idCiudad) {
        this.idCiudad = idCiudad;
    }

    public SelectItem[] getInstitucionItem() {
        return institucionItem;
    }

    public void setInstitucionItem(SelectItem[] institucionItem) {
        this.institucionItem = institucionItem;
    }

    public List getCiudades() {
        return ciudades;
    }

    public void setCiudades(List ciudades) {
        this.ciudades = ciudades;
    }

    public List getListaInstitucion() {
        return listaInstitucion;
    }

    public void setListaInstitucion(List listaInstitucion) {
        this.listaInstitucion = listaInstitucion;
    }

    public List getListaGrupos() {
        return listaGrupos;
    }

    public void setListaGrupos(List listaGrupos) {
        this.listaGrupos = listaGrupos;
    }

    public String getIdgrupo() {
        return idgrupo;
    }

    public void setIdgrupo(String idgrupo) {
        this.idgrupo = idgrupo;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public List getListaIntegrantesGrupo() {
        return listaIntegrantesGrupo;
    }

    public void setListaIntegrantesGrupo(List listaIntegrantesGrupo) {
        this.listaIntegrantesGrupo = listaIntegrantesGrupo;
    }

    public Investigador getResponsableGrupo() {
        return responsableGrupo;
    }

    public void setResponsableGrupo(Investigador responsableGrupo) {
        this.responsableGrupo = responsableGrupo;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public SelectItem[] getAprobacion() {
        return aprobacion;
    }

    public void setAprobacion(SelectItem[] aprobacion) {
        this.aprobacion = aprobacion;
    }

    public Long getCostoTiquetes() {
        return costoTiquetes;
    }

    public void setCostoTiquetes(Long costoTiquetes) {
        this.costoTiquetes = costoTiquetes;
    }

    public Long getCostoInscripcion() {
        return costoInscripcion;
    }

    public void setCostoInscripcion(Long costoInscripcion) {
        this.costoInscripcion = costoInscripcion;
    }

    public String getSolinscripcion() {
        return solInscripcion;
    }

    public void setSolinscripcion(String solInscripcion) {
        this.solInscripcion = solInscripcion;
    }

    public Long getAporteFacultad() {
        return aporteFacultad;
    }

    public void setAporteFacultad(Long aporteFacultad) {
        this.aporteFacultad = aporteFacultad;
    }

    public String getSolDocente() {
        return solDocente;
    }

    public void setSolDocente(String solDocente) {
        this.solDocente = solDocente;
    }

    public String getSolInscripcion() {
        return solInscripcion;
    }

    public void setSolInscripcion(String solInscripcion) {
        this.solInscripcion = solInscripcion;
    }

    public String getTituloPonencia() {
        return tituloPonencia;
    }

    public void setTituloPonencia(String tituloPonencia) {
        this.tituloPonencia = tituloPonencia;
    }

    public String getResumenPonencia() {
        return resumenPonencia;
    }

    public void setResumenPonencia(String resumenPonencia) {
        this.resumenPonencia = resumenPonencia;
    }

    public String getIdPais() {
        return idPais;
    }

    public void setIdPais(String idPais) {
        this.idPais = idPais;
    }

    public List getPaises() {
        return paises;
    }

    public void setPaises(List paises) {
        this.paises = paises;
    }

    public String getResolucionViaje() {
        return resolucionViaje;
    }

    public void setResolucionViaje(String resolucionViaje) {
        this.resolucionViaje = resolucionViaje;
    }

    public String getAceptacionDIB() {
        return aceptacionDIB;
    }

    public void setAceptacionDIB(String aceptacionDIB) {
        this.aceptacionDIB = aceptacionDIB;
    }

    public String getAceptacionPonencia() {
        return aceptacionPonencia;
    }

    public void setAceptacionPonencia(String aceptacionPonencia) {
        this.aceptacionPonencia = aceptacionPonencia;
    }

    /*
     * public UploadedFile getArchivo() { return archivo; }
     * 
     * public void setArchivo(UploadedFile archivo) { this.archivo = archivo; }
     */

    public TipoArchivo getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(TipoArchivo tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    public List getListaTipoArchivo() {
        return listaTipoArchivo;
    }

    public void setListaTipoArchivo(List listaTipoArchivo) {
        this.listaTipoArchivo = listaTipoArchivo;
    }

    public SelectItem[] getTipoArchivoItem() {
        return tipoArchivoItem;
    }

    public void setTipoArchivoItem(SelectItem[] tipoArchivoItem) {
        this.tipoArchivoItem = tipoArchivoItem;
    }

    public List getListaArchivos() {
        return listaArchivos;
    }

    public void setListaArchivos(List listaArchivos) {
        this.listaArchivos = listaArchivos;
    }

    public HtmlPanelGroup getPanelArchivos() {
        return panelArchivos;
    }

    public void setPanelArchivos(HtmlPanelGroup panelArchivos) {
        this.panelArchivos = panelArchivos;
    }

    public HtmlDataTable getTablaArchivos() {
        return tablaArchivos;
    }

    public void setTablaArchivos(HtmlDataTable tablaArchivos) {
        this.tablaArchivos = tablaArchivos;
    }

    public List getProgramas() {
        return programas;
    }

    public void setProgramas(List programas) {
        this.programas = programas;
    }

    public String getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(String idPrograma) {
        this.idPrograma = idPrograma;
    }

    public String getNombreDocente() {
        return nombreDocente;
    }

    public void setNombreDocente(String nombreDocente) {
        this.nombreDocente = nombreDocente;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getPaisProcedencia() {
        return paisProcedencia;
    }

    public void setPaisProcedencia(String paisProcedencia) {
        this.paisProcedencia = paisProcedencia;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getCronograma() {
        return cronograma;
    }

    public void setCronograma(String cronograma) {
        this.cronograma = cronograma;
    }

    public String getEventoDifusion() {
        return eventoDifusion;
    }

    public void setEventoDifusion(String eventoDifusion) {
        this.eventoDifusion = eventoDifusion;
    }

    public String getMontoDiario() {
        return montoDiario;
    }

    public void setMontoDiario(String montoDiario) {
        this.montoDiario = montoDiario;
    }

    public String getNumeroDias() {
        return numeroDias;
    }

    public void setNumeroDias(String numeroDias) {
    	
        this.numeroDias = numeroDias;
    }

    public String getAceptacionFacultad() {
        return aceptacionFacultad;
    }

    public void setAceptacionFacultad(String aceptacionFacultad) {
        this.aceptacionFacultad = aceptacionFacultad;
    }

    public String getDocumentoVisitante() {
        return documentoVisitante;
    }

    public void setDocumentoVisitante(String documentoVisitante) {
        this.documentoVisitante = documentoVisitante;
    }

    public Date getFechaInicioEvento() {
        return fechaInicioEvento;
    }

    public void setFechaInicioEvento(Date fechaInicioEvento) {
        this.fechaInicioEvento = fechaInicioEvento;
    }

    public Date getFechaFinEvento() {
        return fechaFinEvento;
    }

    public void setFechaFinEvento(Date fechaFinEvento) {
        this.fechaFinEvento = fechaFinEvento;
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

    public boolean isMostrarDatosEvento() {
        return mostrarDatosEvento;
    }

    public void setMostrarDatosEvento(boolean mostrarDatosEvento) {
        this.mostrarDatosEvento = mostrarDatosEvento;
    }

    public boolean isPanelNoExiste() {
        return panelNoExiste;
    }

    public void setPanelNoExiste(boolean panelNoExiste) {
        this.panelNoExiste = panelNoExiste;
    }

    public String getDocumentoDocente() {
        return documentoDocente;
    }

    public void setDocumentoDocente(String documentoDocente) {
        this.documentoDocente = documentoDocente;
    }

    public String getNombreLider() {
        return nombreLider;
    }

    public void setNombreLider(String nombreLider) {
        this.nombreLider = nombreLider;
    }

    public boolean isPanelMasDatos() {
        return panelMasDatos;
    }

    public void setPanelMasDatos(boolean panelMasDatos) {
        this.panelMasDatos = panelMasDatos;
    }

    public boolean isFechaIncorrecta() {
        return fechaIncorrecta;
    }

    public void setFechaIncorrecta(boolean fechaIncorrecta) {
        this.fechaIncorrecta = fechaIncorrecta;
    }

    public List getListaActividades() {
        return listaActividades;
    }

    public void setListaActividades(List listaActividades) {
        this.listaActividades = listaActividades;
    }

    public String getMensajeErrorActividad() {
        return mensajeErrorActividad;
    }

    public void setMensajeErrorActividad(String mensajeErrorActividad) {
        this.mensajeErrorActividad = mensajeErrorActividad;
    }

    public UIData getTablaActividades() {
        return tablaActividades;
    }

    public void setTablaActividades(UIData tablaActividades) {
        this.tablaActividades = tablaActividades;
    }
    
    public SelectItem[] getListaActividadesItem() {
        return listaActividadesItem;
    }

    public void setListaActividadesItem(SelectItem[] listaActividadesItem) {
        this.listaActividadesItem = listaActividadesItem;
    }

    public boolean isPanelMasDatos2() {
        return panelMasDatos2;
    }

    public void setPanelMasDatos2(boolean panelMasDatos2) {
        this.panelMasDatos2 = panelMasDatos2;
    }

    public boolean isFechaIncorrecta2() {
        return fechaIncorrecta2;
    }

    public void setFechaIncorrecta2(boolean fechaIncorrecta2) {
        this.fechaIncorrecta2 = fechaIncorrecta2;
    }

    public byte[] getDatos() {
        return datos;
    }

    public void setDatos(byte[] datos) {
        this.datos = datos;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public boolean isErrorArchivo() {
        return errorArchivo;
    }

    public void setErrorArchivo(boolean errorArchivo) {
        this.errorArchivo = errorArchivo;
    }

    public boolean isArchivoCargado() {
        return archivoCargado;
    }

    public void setArchivoCargado(boolean archivoCargado) {
        this.archivoCargado = archivoCargado;
    }

    public String getPMontoDiario() {
        return pMontoDiario;
    }

    public void setPMontoDiario(String montoDiario) {
        pMontoDiario = montoDiario;
    }

    public boolean isErrorMontoDiario() {
        return errorMontoDiario;
    }

    public void setErrorMontoDiario(boolean errorMontoDiario) {
        this.errorMontoDiario = errorMontoDiario;
    }

    public String getIdcreador() {
        return idcreador;
    }

    public void setIdcreador(String idcreador) {
        this.idcreador = idcreador;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public String getTipoDocDocente() {
        return tipoDocDocente;
    }

    public void setTipoDocDocente(String tipoDocDocente) {
        this.tipoDocDocente = tipoDocDocente;
    }

    public String getTipoDocCreador() {
        return tipoDocCreador;
    }

    public void setTipoDocCreador(String tipoDocCreador) {
        this.tipoDocCreador = tipoDocCreador;
    }

    public String[] getErrores() {
        return errores;
    }

    public void setErrores(String[] errores) {
        this.errores = errores;
    }

    public boolean isBErrorGrupo() {
        return bErrorGrupo;
    }

    public void setBErrorGrupo(boolean errorGrupo) {
        bErrorGrupo = errorGrupo;
    }

    public boolean isBErrorPrograma() {
        return bErrorPrograma;
    }

    public void setBErrorPrograma(boolean errorPrograma) {
        bErrorPrograma = errorPrograma;
    }

    public boolean isBErrorDocente() {
        return bErrorDocente;
    }

    public void setBErrorDocente(boolean errorDocente) {
        bErrorDocente = errorDocente;
    }

    public boolean isBErrorDocumento() {
        return bErrorDocumento;
    }

    public void setBErrorDocumento(boolean errorDocumento) {
        bErrorDocumento = errorDocumento;
    }

    public boolean isBErrorNacionalidad() {
        return bErrorNacionalidad;
    }

    public void setBErrorNacionalidad(boolean errorNacionalidad) {
        bErrorNacionalidad = errorNacionalidad;
    }

    public boolean isBErrorPais() {
        return bErrorPais;
    }

    public void setBErrorPais(boolean errorPais) {
        bErrorPais = errorPais;
    }

    public boolean isBErrorInstitucion() {
        return bErrorInstitucion;
    }

    public void setBErrorInstitucion(boolean errorInstitucion) {
        bErrorInstitucion = errorInstitucion;
    }

    public boolean isBErrorRuta() {
        return bErrorRuta;
    }

    public void setBErrorRuta(boolean errorRuta) {
        bErrorRuta = errorRuta;
    }

    public boolean isBErrorActividades() {
        return bErrorActividades;
    }

    public void setBErrorActividades(boolean errorActividades) {
        bErrorActividades = errorActividades;
    }

    public boolean isBErrorEvento() {
        return bErrorEvento;
    }

    public void setBErrorEvento(boolean errorEvento) {
        bErrorEvento = errorEvento;
    }

    public boolean isBErrorTiquete() {
        return bErrorTiquete;
    }

    public void setBErrorTiquete(boolean errorTiquete) {
        bErrorTiquete = errorTiquete;
    }

    public boolean isBErrorMonto() {
        return bErrorMonto;
    }

    public void setBErrorMonto(boolean errorMonto) {
        bErrorMonto = errorMonto;
    }

    public boolean isBErrorDias() {
        return bErrorDias;
    }

    public void setBErrorDias(boolean errorDias) {
        bErrorDias = errorDias;
    }

    public boolean isBErrorDocumentos() {
        return bErrorDocumentos;
    }

    public void setBErrorDocumentos(boolean errorDocumentos) {
        bErrorDocumentos = errorDocumentos;
    }

    public boolean isBErrorHoja() {
        return bErrorHoja;
    }

    public void setBErrorHoja(boolean errorHoja) {
        bErrorHoja = errorHoja;
    }

    public String getNoExiste() {
        return noExiste;
    }

    public void setNoExiste(String noExiste) {
        this.noExiste = noExiste;
    }

    public boolean isBImprimirReporte() {
        return bImprimirReporte;
    }

    public void setBImprimirReporte(boolean imprimirReporte) {
        bImprimirReporte = imprimirReporte;
    }

    public MovilidadVisitanteExterior getMve() {
        return mve;
    }

    public void setMve(MovilidadVisitanteExterior mve) {
        this.mve = mve;
    }

    public SelectItem[] getSedeItem() {
        return sedeItem;
    }

    public void setSedeItem(SelectItem[] sedeItem) {
        this.sedeItem = sedeItem;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public UploadedFile getArchivoCargar() {
        return archivoCargar;
    }

    public void setArchivoCargar(UploadedFile archivoCargar) {
        this.archivoCargar = archivoCargar;
    }

    public List getListaArchivosObligatorios() {
        return listaArchivosObligatorios;
    }

    public void setListaArchivosObligatorios(List listaArchivosObligatorios) {
        this.listaArchivosObligatorios = listaArchivosObligatorios;
    }

    public HtmlDataTable getTablaArchivosObligatorios() {
        return tablaArchivosObligatorios;
    }

    public void setTablaArchivosObligatorios(HtmlDataTable tablaArchivosObligatorios) {
        this.tablaArchivosObligatorios = tablaArchivosObligatorios;
    }

    public UploadedFile getArchivoObligatorio() {
        return archivoObligatorio;
    }

    public void setArchivoObligatorio(UploadedFile archivoObligatorio) {
        this.archivoObligatorio = archivoObligatorio;
    }

    public String getNombreObligatorio() {
        return nombreObligatorio;
    }

    public void setNombreObligatorio(String nombreObligatorio) {
        this.nombreObligatorio = nombreObligatorio;
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

    public List getListaArchivosObligatoriosSel() {
        return listaArchivosObligatoriosSel;
    }

    public void setListaArchivosObligatoriosSel(List listaArchivosObligatoriosSel) {
        this.listaArchivosObligatoriosSel = listaArchivosObligatoriosSel;
    }

    public String getActividadSel() {
        return actividadSel;
    }

    public void setActividadSel(String actividadSel) {
        this.actividadSel = actividadSel;
    }

    public SelectItem[] getActividadSelItem() {
        return actividadSelItem;
    }

    public void setActividadSelItem(SelectItem[] actividadSelItem) {
        this.actividadSelItem = actividadSelItem;
    }

    public ActividadMovilidadVE getTemp() {
        return temp;
    }

    public void setTemp(ActividadMovilidadVE temp) {
        this.temp = temp;
    }

    public CorreoPlantilla getCorreoActual() {
        return correoActual;
    }

    public void setCorreoActual(CorreoPlantilla correoActual) {
        this.correoActual = correoActual;
    }

    public String getCuerpoCorreo() {
        return cuerpoCorreo;
    }

    public void setCuerpoCorreo(String cuerpoCorreo) {
        this.cuerpoCorreo = cuerpoCorreo;
    }

    public boolean isPanelMasDatos3() {
        return panelMasDatos3;
    }

    public void setPanelMasDatos3(boolean panelMasDatos3) {
        this.panelMasDatos3 = panelMasDatos3;
    }

    public boolean isMostrarError() {
        return mostrarError;
    }

    public void setMostrarError(boolean mostrarError) {
        this.mostrarError = mostrarError;
    }

    public String getMsgError() {
        return msgError;
    }

    public void setMsgError(String msgError) {
        this.msgError = msgError;
    }

    public boolean isMostrarDatosEventoGrupo() {
        return mostrarDatosEventoGrupo;
    }

    public void setMostrarDatosEventoGrupo(boolean mostrarDatosEventoGrupo) {
        this.mostrarDatosEventoGrupo = mostrarDatosEventoGrupo;
    }

    public boolean isPanelErrorGrupo() {
        return panelErrorGrupo;
    }

    public void setPanelErrorGrupo(boolean panelErrorGrupo) {
        this.panelErrorGrupo = panelErrorGrupo;
    }

    /**
     * @return the actividadMovilidadVESeleccionada
     */
    public ActividadMovilidadVE getActividadMovilidadVESeleccionada() {
        return actividadMovilidadVESeleccionada;
    }

    /**
     * @param actividadMovilidadVESeleccionada
     *            the actividadMovilidadVESeleccionada to set
     */
    public void setActividadMovilidadVESeleccionada(ActividadMovilidadVE actividadMovilidadVESeleccionada) {
        this.actividadMovilidadVESeleccionada = actividadMovilidadVESeleccionada;
    }

    /**
     * @return the archivoMovilidadVESeleccionado
     */
    public ArchivoMovilidadVE getArchivoMovilidadVESeleccionado() {
        return archivoMovilidadVESeleccionado;
    }

    /**
     * @param archivoMovilidadVESeleccionado
     *            the archivoMovilidadVESeleccionado to set
     */
    public void setArchivoMovilidadVESeleccionado(ArchivoMovilidadVE archivoMovilidadVESeleccionado) {
        this.archivoMovilidadVESeleccionado = archivoMovilidadVESeleccionado;
    }

    /**
     * @return the puedeSubirArchivos
     */
    public boolean isPuedeSubirArchivos() {
        return puedeSubirArchivos;
    }

    /**
     * @param puedeSubirArchivos
     *            the puedeSubirArchivos to set
     */
    public void setPuedeSubirArchivos(boolean puedeSubirArchivos) {
        this.puedeSubirArchivos = puedeSubirArchivos;
    }

    public void setPalabraClave(PalabraClave palabraClave) {
        this.palabraClave = palabraClave;
    }

    public PalabraClave getPalabraClave() {
        return palabraClave;
    }

    public List obtenerPalabraClavesSugeridas(String nombre) {
        return servicioGeneral.obtenerPalabrasClaveEmpezandoCon(nombre);
    }

    public void insertarPalabraClave() {

        // System.out.println("inserta " + palabraClave.getPalabra());
        if ((!palabraClave.getPalabra().equals(""))) {
            PalabraClave pc = new PalabraClave();
            pc.setPalabra(palabraClave.getPalabra().toUpperCase());
            pc.setPalabraOriginal(palabraClave.getPalabra());
            try {
                PalabraClave pc1 = servicioGeneral.obtenerPalabraClave(pc.getPalabra());
                if (pc1 == null) {
                    pc.setIdioma("ES");
                    servicioGeneral.guardarObjeto(pc);
                } else {
                    pc = (PalabraClave) pc1.clone();
                }
                // listaPalabrasClave.add(pc);
                mve.adicionarPalabraClave(pc);
                palabraClave.setPalabra("");
                pc1 = null;
                pc = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        palabraClave = new PalabraClave();
    }

    public void setListaPalabrasClave(List<PalabraClave> listaPalabrasClave) {
        this.listaPalabrasClave = listaPalabrasClave;
    }

    public List<PalabraClave> getListaPalabrasClave() {
        return listaPalabrasClave;
    }

    public void eliminarPalabraClave() {
        mve.borrarPalabraClave(this.palabraClave);
        // listaPalabrasClave.remove(this.palabraClave);
    }

    public String getEmailVisitante() {
        return emailVisitante;
    }

    public void setEmailVisitante(String emailVisitante) {
        this.emailVisitante = emailVisitante;
    }

    public boolean isPanelEmailInvestigador() {
        return panelEmailInvestigador;
    }

    public void setPanelEmailInvestigador(boolean panelEmailInvestigador) {
        this.panelEmailInvestigador = panelEmailInvestigador;
    }

    public boolean isbErrorEmail() {
        return bErrorEmail;
    }

    public void setbErrorEmail(boolean bErrorEmail) {
        this.bErrorEmail = bErrorEmail;
    }

    public boolean isEsConvocatoriaFacultad() {
        return esConvocatoriaFacultad;
    }

    public void setEsConvocatoriaFacultad(boolean esConvocatoriaFacultad) {
        this.esConvocatoriaFacultad = esConvocatoriaFacultad;
    }

    public void calcularFechaMinimaInicio() {
        Calendar fechaActual = Calendar.getInstance();
        fechaActual.add(Calendar.DATE, 30);
        fechaMinimaInicio = fechaActual.getTime();
    }

    public Date getFechaMinimaInicio() {
        return fechaMinimaInicio;
    }

    public void setFechaMinimaInicio(Date fechaMinimaInicio) {
        this.fechaMinimaInicio = fechaMinimaInicio;
    }

    public void descargarArchivoMovVE() {
        ArchivoMovilidadVE archivo = documentoSeleccionado;
        descargarArchivoMovilidadVEGenerico(archivo.getId());
    }

    public String getRestriccionArchivosConv() {
        return restriccionArchivosConv;
    }

    public void setRestriccionArchivosConv(String restriccionArchivosConv) {
        this.restriccionArchivosConv = restriccionArchivosConv;
    }

    public boolean isRestriccionLiderGrupo() {
        return restriccionLiderGrupo;
    }

    public void setRestriccionLiderGrupo(boolean restriccionLiderGrupo) {
        this.restriccionLiderGrupo = restriccionLiderGrupo;
    }

	/**
	 * @return the ciudadProcedencia
	 */
	public String getCiudadProcedencia()
	{
		return ciudadProcedencia;
	}

	/**
	 * @param ciudadProcedencia the ciudadProcedencia to set
	 */
	public void setCiudadProcedencia(String ciudadProcedencia)
	{
		this.ciudadProcedencia = ciudadProcedencia;
	}

	/**
	 * @return the bErrorCiudad
	 */
	public boolean isbErrorCiudad()
	{
		return bErrorCiudad;
	}

	/**
	 * @param bErrorCiudad the bErrorCiudad to set
	 */
	public void setbErrorCiudad(boolean bErrorCiudad)
	{
		this.bErrorCiudad = bErrorCiudad;
	}

}
