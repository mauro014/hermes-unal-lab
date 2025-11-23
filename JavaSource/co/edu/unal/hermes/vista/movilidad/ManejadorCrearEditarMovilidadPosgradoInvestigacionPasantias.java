package co.edu.unal.hermes.vista.movilidad;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.dao.DataIntegrityViolationException;

import co.edu.unal.hermes.modelo.ArchivoMovilidad;
import co.edu.unal.hermes.modelo.ArchivoMovilidadEP;
import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.Continente;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.Estudiante;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Institucion;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorGrupo;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.MovilidadArchivo;
import co.edu.unal.hermes.modelo.MovilidadEstudiantesPosgrado;
import co.edu.unal.hermes.modelo.Pais;
import co.edu.unal.hermes.modelo.Parametro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.TipoArchivoMovilidad;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoEntidadInvestigacion;
import co.edu.unal.hermes.modelo.TipoInforme;
import co.edu.unal.hermes.modelo.TipoMovilidad;
import co.edu.unal.hermes.modelo.TipoPonencia;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.utils.Navegacion;

public class ManejadorCrearEditarMovilidadPosgradoInvestigacionPasantias extends ManejadorBaseMovilidad {

    private static final long serialVersionUID = 423128876862081345L;
    private String aceptacionDIB;
    private String aceptacionFacultad;
    private String aceptacionPonencia;
    private Long aporteFacultad;
    private final String ID_RESTRICCION_ESTUDIATE_PONENCIA = "MOV3";
    private final String ID_RESTRICCION_ESTUDIATE_PASANTIA = "MOV4";
    private SelectItem[] aprobacion = { new SelectItem("NO", "NO"), new SelectItem("SI", "SI") };

    private boolean bFinanciacion;
    private boolean bOtroTipoPonencia;
    private String categoriaInvestigador;
    private List ciudades;
    private String ciudadEvento;
    private Long costoInscripcion; // También aplicado a: alojamiento
    private Long costoTiquetes;
    private String departamentoDocente;
    private String carreraDocente;
    private String correoDocente;
    private String departamentoSeleccionado = "";
    private Dependencia dependencia;
    private String dependenciaId;
    private SelectItem[] depsItem;
    private Long destinado;
    private String documento;
    private String documentoEstudiante;
    private String documentoDocente;
    private String tipoDocDocente;
    private String facultadDocente;
    private String facultadSeleccionada = "";
    private Date fechaFinEvento;
    private boolean fechaIncorrecta;
    private boolean fechaIncorrecta2;
    private Date fechaInicioEvento;
    private String financiacionActual;
    private Grupo grupo;
    private Grupo grupoActual;
    private String idCiudad;
    private String idcreador;
    private String tipoDocCreador;
    private boolean identificacion;
    private String idgrupo;
    private String idInstitucion;
    private String idInvestigador;
    private String idPais;
    private String idProyecto;
    private String idTipoPonencia;
    private SelectItem[] institucionItem;
    private Investigador investigadorActual;
    private SelectItem[] investigadorItem;
    private List listaArchivos;
    private List listaCategoriaInvestigador;
    private List listaDependencias;
    private List listaGrupos;
    private List listaGruposInv;
    private List listaInstitucion;
    private List listaIntegrantesGrupo;
    private List listaTipoArchivo;
    private boolean mostrarDatosBasicos;
    private boolean mostrarDatosEvento;
    private boolean mostrarEstudiante;
    private String nombreDepartamentoSeleccionado;
    private String nombreDocente;
    private Estudiante estudiante;

    private String nombreEvento;
    private String nombreFacultadSeleccionada;
    private String nombreLiderGrupo;
    private String nombreSedeSeleccionada;
    private boolean esPonencia;

    private String otroTipoPonencia;

    private List paises;

    private HtmlPanelGroup panelArchivos;
    private boolean panelMasDatos;
    private boolean panelMasDatos2;
    private boolean panelNoExiste;
    private boolean mostrarDatosEstudiante = false;
    private Persona persona;
    private List proyectosInvestigador;
    private String resolucionViaje;
    private Investigador responsableGrupo;
    private String resumenPonencia;

    private String sedeSeleccionada = "";

    private String solDocente;
    private String solInscripcion;

    private TipoDocumento tipoDocumento;
    private TipoDocumento tipoDocumentoEstudiante;
    private UploadedFile archivoObligatorio;
    private HtmlDataTable tablaArchivosObligatoriosSel;
    private List listaArchivosObligatorios;
    private List listaArchivosObligatoriosSel;
    private String nombreObligatorio;

    private SelectItem[] tipoDocumentoItem;
    private SelectItem[] tipoDocumentoEstudianteItem;

    private String tipoInvestigador = new String();
    private SelectItem[] tipoPonencia = { new SelectItem("1", "Conferencia Magistral"), new SelectItem("2", "Póster"),
            new SelectItem("3", "Otro tipo de ponencia") };
    private String tipoPonenciaActual;
    private String tituloPonencia;
    private String errores[];
    private boolean bErrorProyecto;
    private boolean bErrorEvento;
    private boolean bErrorTitulo;
    private boolean bErrorPais;
    private boolean bErrorPapa;
    private boolean bErrorDocumentos;
    private boolean bErrorCiudad;
    private boolean bErrorPonencia;

    private boolean bErrorResumen;
    private boolean bErrorResolucion;
    private boolean bErrorTiquete;
    private boolean bErrorInscripcion;
    private boolean mostrarError = false;
	private String msgError = "Su solicitud NO ha sido enviada, por favor verifique la información y vuelva a GUARDAR. Debe seleccionar SI desea confirmar el envio para enviar la movilidad o NO en caso de guardar parcialmente.";
    private String noExiste;
    private String noExiste1;

    private SelectItem[] tipoDocumentoSelItem;
    private String tipoDocumentoSel;
    private boolean bImprimirReporte;
    private String universidad;
    private Float papaEstudiante;

    private String valorAporteOtrasDep;
    private String depValorAporteOtrasDep;
    
    private final static String RUTA_ADJUNTO = "/pages/Movilidad";
    private MovilidadEstudiantesPosgrado mep;
    

    private UploadedFile archivoCargar;
    private HtmlDataTable tablaArchivos;
    private HtmlDataTable tablaEntidadInvestigacion;
    List listaEntidadInvestigacion;

    private String aprobacionInvestigacionUN;
    private SelectItem[] aprobacionInvestigacionUNItem;

    CorreoPlantilla correoActual = new CorreoPlantilla();
    String cuerpoCorreo = "";
    private Date fechaMinimaInicio;
    private boolean puedeSubirArchivos = false;
    private ArchivoMovilidadEP archivoMovilidadEPSeleccionado;

    // lmom
    private Long MOD_MOVILIDAD_PASANTIA = 423L;
    private Long idContinente;
    private List continentes;
    int africaDocente = 0;
    int americaDocente = 0;
    int asiaDocente = 0;
    int europaDocente = 0;
    int oceaniaDocente = 0;
    int salario = 1;
    private ArchivoMovilidadEP documentoSeleccionado;
    private String restriccionArchivosConv;
    private boolean esConvocatoriaFacultad = false;
    private boolean mostrarValorViaticos = false;
    private String medioTransporte;
    private SelectItem[] medioTransporteItem = { new SelectItem("1", "Terrestre"), new SelectItem("2", "Aereo") };
    private boolean mostrarAportesOtrasDependencias = false;
    private boolean esParaPregrado = false;

    public void seleccionarTipoDocumento() {
        System.out.println("puedeSubirArchivos: " + puedeSubirArchivos);
        puedeSubirArchivos = false;
        if (!tipoDocumentoSel.equals("")) {
            puedeSubirArchivos = true;
        }
        System.out.println("tipoDocumentoSel: " + tipoDocumentoSel);
        System.out.println("puedeSubirArchivos: " + puedeSubirArchivos);
    }

    public void calcularFechaMinimaInicio() {
        Calendar fechaActual = Calendar.getInstance();
        fechaActual.add(Calendar.DATE, 20);
        fechaMinimaInicio = fechaActual.getTime();
        System.out.print("fechaMinimaInicio: " + fechaMinimaInicio);
    }

    public ManejadorCrearEditarMovilidadPosgradoInvestigacionPasantias() {
    	
    	Long idMovilidadEd = (Long) sesion.getAttribute("movilidadEstPasSel");
    	
        ocultarPaneles();
        cargarValoresIniciales();

        personaActual = (Persona) sesion.getAttribute("persona");
        if (personaActual != null) {
            documento = personaActual.getId().getDocumento();
            tipoDocumento = new TipoDocumento();
            tipoDocumento.setId(personaActual.getId().getTipoDocumento());
        }
        
        mostrarDatosEstudiante = false;

        listaArchivosObligatoriosSel = new ArrayList();
        listaArchivosObligatorios = new ArrayList();

        String restriccionConvocatoria = "";

        mep = new MovilidadEstudiantesPosgrado();
        Proyecto p = (Proyecto) sesion.getAttribute("proyectoMovilidad");
        if (p != null) {
            mep.setProyectoFicha(p.getId());
        }
        
        if(idMovilidadEd != null){
        	mep = servicioMovilidad.obtenerMovilidadesEstudiantes(idMovilidadEd);
        	sesion.setAttribute("idConvocatoriaActual", mep.getConvocatoria().getId());
        	listaArchivosObligatoriosSel.addAll(mep.getArchivos());
        	try
			{
				buscarPersona();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
        	
        	nombreEvento = mep.getEvento();
        	tituloPonencia =  mep.getTitulo();
        	ciudadEvento =  mep.getCiudad();
        	costoTiquetes = mep.getCostotiquete();
        	resumenPonencia = mep.getResumen();
        	solInscripcion = mep.getInscripcionevento();
        	otroTipoPonencia = mep.getOtroTipoPonencia();
        	costoInscripcion = mep.getValorViaticos();
        	fechaInicioEvento = mep.getFechainicial();
        	fechaFinEvento = mep.getFechafinal();
            
        	aceptacionFacultad = mep.getAceptacion();
        	universidad =  mep.getUniversidad();
        	
        	documentoEstudiante = mep.getEstudianteInv().getId().getDocumento();
            tipoDocumentoEstudiante = new TipoDocumento();
            tipoDocumentoEstudiante.setId(mep.getEstudianteInv().getId().getTipoDocumento());
            papaEstudiante = mep.getPapaEstudiante();
            
            idPais =  mep.getPais().getId();
            List listaPais = new ArrayList();
            listaPais = servicioGeneral.obtenerListaObjetos("Pais where id ='" + idPais + "'");
            Pais pais = new Pais();
            pais = (Pais) listaPais.get(0);
            
            String sqlContinente = "select e from Continente e where e.id = " + pais.getContinente();
            List listaContinente = new ArrayList();
            listaContinente = servicioGeneral.obtenerObjetos(sqlContinente);
            Continente cont = new Continente();
            cont = (Continente) listaContinente.get(0);
            idContinente = cont.getId();
            
            try
			{
				buscarEstudiante();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
            mostrarDatosEvento = true;
            mostrarDatosEstudiante = true;
            panelMasDatos2 = true;
            panelMasDatos = true;        	
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

                    String sqlBuscaConvocatoria = "select #id e.id, #gruposCategoriaA e.gruposCategoriaA, #gruposReconocidos e.gruposReconocidos, #areaDirigida e.areaDirigida from Convocatoria e where e.id = "
                            + idConvocatoria + "";

                    List<Convocatoria> listConvs = servicioGeneral.obtenerObjetosLimitado(Convocatoria.class,
                            sqlBuscaConvocatoria);
                    Convocatoria convActual = listConvs.get(0);
                    if (convActual.getGruposCategoriaA() != null) {
                        if (convActual.getGruposCategoriaA()) {
                            mostrarValorViaticos = true;
                        } else {
                            mostrarValorViaticos = false;
                        }
                    }
                    
                    if (convActual.getGruposReconocidos() != null) {
                        if (convActual.getGruposReconocidos()) {
                        	mostrarAportesOtrasDependencias = true;
                        } else {
                        	mostrarAportesOtrasDependencias = false;
                        }
                    }
                    
                    if(convActual.getAreaDirigida() != null){
                    	if(convActual.getAreaDirigida().equals("PREGRADO")){
                    		esParaPregrado = true;
                    	}else{
                    		esParaPregrado = false;
                    	}
                    }
                    
                    if(idMovilidadEd != null){
                    	restriccionConvocatoria = convActual.getRestriccion().getId();
                    }else{
                    	restriccionConvocatoria = (String) sesion.getAttribute("convocatoriaMovilidadResticcion");
                    }

                }

            }
        }

        
        cargarTiposDocumentos();
       
        List listaTipoPonencia;
        listaTipoPonencia = new ArrayList();
       
       
        
        listaTipoPonencia = servicioGeneral.obtenerListaObjetos("TipoPonencia where mostrar = 'P' ");
        tipoPonencia = new SelectItem[listaTipoPonencia.size()];
        int j = 0;
        for (int i = 0; i < listaTipoPonencia.size(); i++) {
            TipoPonencia tpo = (TipoPonencia) listaTipoPonencia.get(i);
            if (restriccionConvocatoria.equals(ID_RESTRICCION_ESTUDIATE_PONENCIA)
                    && (tpo.getId().equals((long) 12) || tpo.getId().equals((long) 13)))
                tipoPonencia[j++] = new SelectItem(tpo.getId().toString(), tpo.getDescripcion());
            if (restriccionConvocatoria.equals(ID_RESTRICCION_ESTUDIATE_PASANTIA)
                    && (tpo.getId().equals((long) 10) || tpo.getId().equals((long) 11)))
                tipoPonencia[j++] = new SelectItem(tpo.getId().toString(), tpo.getDescripcion());
        }
        SelectItem[] listaAuxiliar = new SelectItem[j];
        for (int i = 0; i < j; i++) {
            listaAuxiliar[i] = tipoPonencia[i];
        }
        tipoPonencia = listaAuxiliar;
        
        
        listaArchivos = new ArrayList();

        Long idModalidad_ = (Long) super.sesion.getAttribute("idMovilidad");
        if (idModalidad_ != null) {
            infoModalidad(idModalidad_);
            // super.sesion.removeAttribute("idProyecto");
        }
        calcularFechaMinimaInicio();

        // lmom

        List listaParametroUno = this.servicioGeneral
                .obtenerObjetos("FROM Parametro WHERE nombre = 'AFRICA_ESTUDIANTE'");
        List listaParametroDos = this.servicioGeneral
                .obtenerObjetos("FROM Parametro WHERE nombre = 'AMERICA_ESTUDIANTE'");
        List listaParametroTres = this.servicioGeneral
                .obtenerObjetos("FROM Parametro WHERE nombre = 'ASIA_ESTUDIANTE'");
        List listaParametroCuatro = this.servicioGeneral
                .obtenerObjetos("FROM Parametro WHERE nombre = 'EUROPA_ESTUDIANTE'");
        List listaParametroCinco = this.servicioGeneral
                .obtenerObjetos("FROM Parametro WHERE nombre = 'OCEANIA_ESTUDIANTE'");

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

    }

    // lmom
    private void cargarContinentes() {
        List listaContinentes = servicioGeneral.obtenerListaObjetosOrdenadosAsc(new Continente(), "nombre");
        continentes = new Vector();
        for (Iterator it = listaContinentes.iterator(); it.hasNext();) {
            Continente p = (Continente) it.next();
            SelectItem s = new SelectItem(p.getId(), p.getNombre());
            continentes.add(s);
        }
        idContinente = 1L;
    }

    private void infoModalidad(Long idModalidad_) {
        List listaMovilidadConsulta;
        listaMovilidadConsulta = new ArrayList();

        listaMovilidadConsulta = servicioGeneral
                .obtenerListaObjetos("MovilidadDocentesExterior where id = '" + idModalidad_ + "'");
        if (listaMovilidadConsulta != null && listaMovilidadConsulta.size() > 0) {
            mep = (MovilidadEstudiantesPosgrado) listaMovilidadConsulta.get(0);
        }

        tipoDocumento.setId(mep.getTipoDocumentoPersona().toString());
        // documento=mde.getIdinvestigador();

    }

    public void activarFinanciacion(ValueChangeEvent event) {
        financiacionActual = event.getNewValue().toString();

        if (financiacionActual.equals("SI")) {
            bFinanciacion = true;
        } else {
            bFinanciacion = false;
        }
    }

    public void activarOtrotipoPonencia() { // ValueChangeEvent event) {
        // tipoPonenciaActual = event.getNewValue().toString();
        tipoPonenciaActual = idTipoPonencia;

        if (tipoPonenciaActual.equals("3")) {
            bOtroTipoPonencia = true;
        } else {
            bOtroTipoPonencia = false;
        }

        if (tipoPonenciaActual.equals("10") || tipoPonenciaActual.equals("11")) {
            esPonencia = false;
        } else {
            esPonencia = true;
        }
    }

    public void verArchivo() {

        ArchivoMovilidad ain = (ArchivoMovilidad) tablaArchivos.getRowData();
        FacesContext ctx = FacesContext.getCurrentInstance();

        // listaInformes = new ArrayList();
        // listaInformes =
        // servicioGeneral.obtenerListaObjetos("ProyectoInforme where id
        // ='"+pin.getId()+"'");
        // ProyectoInforme pinAux = new ProyectoInforme();
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
        // ArchivoInforme ain = ((ArchivoInforme) (tablaArchivos.getRowData()));
        // ain.eliminarArchivo(ain);

    }

    public void buscarEstudiante() throws SQLException {
        reiniciarVariables();
        IdPersona id = new IdPersona();

        mostrarDatosEstudiante = false;

        List est = servicioGeneral.obtenerListaObjetosWhere("Estudiante e", "where e.id.documento='"
                + this.documentoEstudiante + "' and e.id.tipoDocumento='" + this.tipoDocumentoEstudiante.getId() + "'");
        if (est != null && est.size() > 0) {
            estudiante = (Estudiante) est.get(0);
        } else {
            noExiste = "El documento buscado no ha sido encontrado en el sistema";
            mostrarPanelNoExiste();
            return;
        }

        id.setDocumento(documentoEstudiante);
        id.setTipoDocumento(tipoDocumentoEstudiante.getId());

        String sSql = "";
        int nExiste = 0;
        Date fechaActual = new Date();

        SimpleDateFormat spy = new SimpleDateFormat("yyyy");

        boolean bandera = false;
        if (sesion.getAttribute("idConvocatoriaActual") != null) {
            Long idConvocatoria = (Long) sesion.getAttribute("idConvocatoriaActual");
            if (idConvocatoria != null) {
                sSql = " SELECT COUNT(1) " + " FROM " + " HER_MOVILIDAD_ESTUDIANTE_POS " + " WHERE " + " MOV_ID_EST ='"
                        + estudiante.getId().getDocumento() + "'" + " AND ("
                        + "(MOV_APROB = 'SI' AND MOV_REALIZACION_MOVILIDAD IS NULL)" + " OR "
                        + "(MOV_APROB = 'SI' AND MOV_REALIZACION_MOVILIDAD = 'SI')" + ") "
                        + " AND EXTRACT(YEAR FROM MOV_FEC_SOL) ='" + Long.parseLong(spy.format(fechaActual))
                        + "' and CON_ID = '" + idConvocatoria + "'";
                bandera = true;
            }
        }
        if (!bandera) {
            sSql = " SELECT COUNT(1) " + " FROM " + " HER_MOVILIDAD_ESTUDIANTE_POS " + " WHERE " + " MOV_ID_EST ='"
                    + estudiante.getId().getDocumento() + "'" + " AND ("
                    + "(MOV_APROB = 'SI' AND MOV_REALIZACION_MOVILIDAD IS NULL)" + " OR "
                    + "(MOV_APROB = 'SI' AND MOV_REALIZACION_MOVILIDAD = 'SI')" + ") "
                    + " AND EXTRACT(YEAR FROM MOV_FEC_SOL) ='" + Long.parseLong(spy.format(fechaActual)) + "'";
        }
        nExiste = servicioGeneral.existeMovilidad(sSql);

        if (nExiste < 0) {
            noExiste = "El estudiante ya tiene aprobada una movilidad para este año.";
            mostrarPanelNoExiste();
        } else {
            if (estudiante != null) {
                dependencia = estudiante.getDependencia();// servicioPersona
                carreraDocente = estudiante.getPlan().getNombre();
                correoDocente = estudiante.getEmail();
                String nombre1, nombre2, apellido1, apellido2;
                if (estudiante.getNombre1() != null) {
                    nombre1 = estudiante.getNombre1();
                } else {
                    nombre1 = "";
                }
                if (estudiante.getNombre2() != null) {
                    nombre2 = estudiante.getNombre2();
                } else {
                    nombre2 = "";
                }
                if (estudiante.getApellido1() != null) {
                    apellido1 = estudiante.getApellido1();
                } else {
                    apellido1 = "";
                }
                if (estudiante.getApellido2() != null) {
                    apellido2 = estudiante.getApellido2();
                } else {
                    apellido2 = "";
                }
                nombreDocente = nombre1 + " " + nombre2 + " " + apellido1 + " " + apellido2;
                documentoDocente = estudiante.getId().getDocumento();
                tipoDocDocente = estudiante.getId().getTipoDocumento();

                if (estudiante.getPapa() == null) {
                    papaEstudiante = Float.parseFloat("0");
                } else {
                    papaEstudiante = estudiante.getPapa();
                }

                if (papaEstudiante < 0.0) {
                    String error = "El Promedio aritmético del estudiante " + nombreDocente + " es " + papaEstudiante
                            + ", y debe ser igual o mayor a 4.0  \n";
                    noExiste = error;
                    mostrarPanelNoExiste();
                    return;
                } else {
                    bErrorPapa = false;
                }

                if (dependencia != null) {
                    if (dependencia.getFacultad() != null) {
                        facultadDocente = dependencia.getFacultad().getNombre();
                        departamentoDocente = dependencia.getNombre();
                    }
                } else {
                    facultadDocente = "";
                    departamentoDocente = "";
                }

                if(esParaPregrado){
                	if (estudiante.getPlan().getTipo().equals(5L) || estudiante.getPlan().getTipo().equals(6L) ||estudiante.getPlan().getTipo().equals(7L)) {
                    	noExiste = "El documento ingresado no existe";
                        mostrarPanelNoExiste();
                    }else{
                    	mostrarDatosPersona();
                        mostrarDatosEstudiante = true;
                    }
                }else{
                	mostrarDatosPersona();
                    mostrarDatosEstudiante = true;
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
    }

    public void buscarPersona() throws SQLException {
        noExiste1 = "";
        reiniciarVariables();
        IdPersona id = new IdPersona();
        id.setDocumento(documento);
        id.setTipoDocumento(tipoDocumento.getId());
        persona = servicioPersona.obtenerPersonaRoles(id);

        // existeMovilidad

        String sSql = "";
        int nExiste = 0;
        Date fechaActual = new Date();

        SimpleDateFormat spd = new SimpleDateFormat("dd");
        SimpleDateFormat spm = new SimpleDateFormat("MM");
        SimpleDateFormat spy = new SimpleDateFormat("yyyy");

        mostrarDatosEstudiante = false;

        if (persona != null) {
            idCiudad = persona.getCiudadDomicilio() == null ? null : persona.getCiudadDomicilio().getId();
            if (persona instanceof Investigador) {
                if (persona instanceof InvestigadorInterno) {
                    persona = servicioPersona.obtenerInvestigadorInternoCompleto(persona.getId());
                    InvestigadorInterno investigadorInterno = (InvestigadorInterno) persona;
                    dependencia = servicioDependencia.obtenerDependencia(investigadorInterno.getId());
                    String nombre1, nombre2, apellido1, apellido2;
                    listaGrupos = new Vector();
                    cargarGrupos(investigadorInterno, documento);
                    // Ing. Wilver Alexander Martínez Martínez (wam²)
                    // Cambio para aceptar docentes de medio tiempo
                    if (investigadorInterno.getTipoDedicacion() != null
                            && (investigadorInterno.getTipoDedicacion().getId().equals(Investigador.EXCLUSIVA)
                                    || investigadorInterno.getTipoDedicacion().getId()
                                            .equals(Investigador.TIEMPOCOMPLETO)
                            || investigadorInterno.getTipoDedicacion().getId().equals(Investigador.MEDIOTIEMPO)
                            || investigadorInterno.getTipoDedicacion().getId()
                                    .equals(Investigador.TIEMPO_COMPLETO_ADMINISTRATIVO))) {
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
                        nombreDocente = nombre1 + " " + nombre2 + " " + apellido1 + " " + apellido2;
                        documentoDocente = investigadorInterno.getId().getDocumento();
                        tipoDocDocente = investigadorInterno.getId().getTipoDocumento();
                        if (dependencia.getFacultad() != null) {
                            facultadDocente = dependencia.getFacultad().getNombre();
                            departamentoDocente = dependencia.getNombre();
                            mostrarDatosPersona();
                            // }
                        } else {
                            noExiste = "La dependencia del investigador no tiene una facultad asociada";
                            mostrarPanelNoExiste();
                        }
                    } else {
                        noExiste = "El investigador debe ser de dedicación exclusiva o tiempo completo de la Universidad Nacional de Colombia";
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
        // }
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

                    SelectItem s = new SelectItem(String.valueOf(g.getId()), g.getNombre());
                    listaGrupos.add(s);

                }
            } else {
                System.out.println("ManejadorGruposInvestigador:ManejadorGruposInvestigador:Lista de Grupos Vacia");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cambiarInvestigador(ValueChangeEvent event) {
        // VINCULACION DE LOS DEPARTAMENTOS CONTENIDOS EN LA BASE DE DATOS CON
        // EL COMPONENTE
        // WEB QUE PERMITE LA ESCOGENCIA DE UN DEPARTAMENTO Y LA RECARGA DE LAS
        // CIUDADES
        String nombrel1, nombrel2, apellidol1, apellidol2;
        if (investigadorActual.getNombre1() != null) {
            nombrel1 = investigadorActual.getNombre1();
        } else {
            nombrel1 = "";
        }
        if (investigadorActual.getNombre2() != null) {
            nombrel2 = investigadorActual.getNombre2();
        } else {
            nombrel2 = "";
        }
        if (investigadorActual.getApellido1() != null) {
            apellidol1 = investigadorActual.getApellido1();
        } else {
            apellidol1 = "";
        }
        if (investigadorActual.getApellido2() != null) {
            apellidol2 = investigadorActual.getApellido2();
        } else {
            apellidol2 = "";
        }
        nombreDocente = nombrel1 + " " + nombrel2 + " " + apellidol1 + " " + apellidol2;
        documentoDocente = investigadorActual.getId().getDocumento();

        facultadDocente = "";
        departamentoDocente = "";
        mostrarDatosEvento = true;
    }

    public void cargarDatosInvestigadores() {
        listaIntegrantesGrupo = new ArrayList();
        List integrantesGrupo = this.servicioGrupo.obtenerIntegrantesGrupo(grupoActual.getId());
        for (int i = 0; i < integrantesGrupo.size(); i++) {
            InvestigadorGrupo investigadorGrupo = (InvestigadorGrupo) integrantesGrupo.get(i);
            if (investigadorGrupo.getTipo().equals(InvestigadorGrupo.LIDER))
                responsableGrupo = investigadorGrupo.getInvestigador();
            else
                listaIntegrantesGrupo.add(investigadorGrupo.getInvestigador());
        }
    }

    /*
     * DML Editor DEPENDENCIA
     */
    public void cargarDependencias() {
        Dependencia sede = new Dependencia();
        sedeSeleccionada = "";
        nombreSedeSeleccionada = "";
        facultadSeleccionada = "";
        nombreFacultadSeleccionada = "";
        departamentoSeleccionado = "";
        nombreDepartamentoSeleccionado = "";
        // listaDependencias = this.servicioMovilidad.obtenerDependencias(id);
        depsItem = new SelectItem[listaDependencias.size() + 1];
        depsItem[0] = new SelectItem("", "");
        for (int i = 1; i < listaDependencias.size() + 1; i++) {
            sede = null;
            sede = (Dependencia) listaDependencias.get(i - 1);
            String nombre = sede.getNombre().length() > 55 ? sede.getNombre().substring(0, 55) + "..."
                    : sede.getNombre();
            depsItem[i] = new SelectItem(sede.getId(), nombre);
        }
    }

    public void guardar() {

        boolean banderaPrincipal = true;

        Calendar actual = Calendar.getInstance();
        Date date = actual.getTime();

        mep.setFechasolicitud(date);
        mep.setEvento(nombreEvento);
        mep.setTitulo(tituloPonencia);
        mep.setCiudad(cortarCadena(ciudadEvento, 255));
        mep.setCostotiquete(costoTiquetes);
        mep.setResumen(resumenPonencia);
        mep.setInscripcionevento(solInscripcion);
        mep.setOtroTipoPonencia(otroTipoPonencia);
        mep.setValorViaticos(costoInscripcion);
        mep.setFechainicial(fechaInicioEvento);
        mep.setFechafinal(fechaFinEvento);
        TipoPonencia tm2 = new TipoPonencia();
        tm2.setId(Long.parseLong("11"));
        mep.setPonencia(tm2);
        mep.setAceptacion(aceptacionFacultad);
        mep.setUniversidad(cortarCadena(universidad, 255));
        mep.setMovilidadConvocatoriaFacultad("S");
        mep.setMedioTransporte(medioTransporte != null ? medioTransporte : "");
        mep.setValorOtrosAportes(valorAporteOtrasDep);
        mep.setDependenciaOtrosAportes(depValorAporteOtrasDep);

        if (sesion.getAttribute("idConvocatoriaActual") != null) {
            Long idConvocatoria = (Long) sesion.getAttribute("idConvocatoriaActual");
            mep.setConvocatoriaId(idConvocatoria.toString());
        }

        if(mep.getCostotiquete() == null){
        	mep.setCostotiquete(0L);
        }
        
        if(mep.getValorViaticos() == null){
        	 mep.setValorViaticos(0L);
        }
        
        Long costoTotal = mep.getCostotiquete() + mep.getValorViaticos();
        Long costoMaximo = 0L;

        if (idContinente.equals(1L)) {
            costoMaximo = Long.parseLong(String.valueOf(africaDocente * salario));
        }
        if (idContinente.equals(2L)) {
            costoMaximo = Long.parseLong(String.valueOf(americaDocente * salario));
        }
        if (idContinente.equals(3L)) {
            costoMaximo = Long.parseLong(String.valueOf(asiaDocente * salario));
        }
        if (idContinente.equals(4L)) {
            costoMaximo = Long.parseLong(String.valueOf(europaDocente * salario));
        }
        if (idContinente.equals(5L)) {
            costoMaximo = Long.parseLong(String.valueOf(oceaniaDocente * salario));
        }

        String detalleError = "";

        if (costoTotal > costoMaximo) {
            banderaPrincipal = false;
            detalleError += "El costo total supera el monto máximo ($" + costoMaximo + "). ";
        }

        if (banderaPrincipal) {

            List listaTipoMovilidad = new ArrayList();
            listaTipoMovilidad = servicioGeneral.obtenerListaObjetos("TipoMovilidad where id ='CF_MOV5'");
            TipoMovilidad tm = new TipoMovilidad();
            tm = (TipoMovilidad) listaTipoMovilidad.get(0);
            if (tm != null) {
                mep.setTipoMovilidad(tm);
            }
            Estudiante e = null;
            List est = servicioGeneral.obtenerListaObjetosWhere("Estudiante e",
                    "where e.id.documento='" + this.documentoEstudiante + "' and e.id.tipoDocumento='"
                            + this.tipoDocumentoEstudiante.getId() + "'");
            if (est != null && est.size() > 0) {
                e = (Estudiante) est.get(0);

            }
            mep.setEstudianteInv(estudiante);
            mep.setPapaEstudiante(papaEstudiante);

            if (validar(mep)) {

                Persona personaAux = new Persona();
                IdPersona idp = new IdPersona();

                idp.setDocumento(documento);
                idp.setTipoDocumento(tipoDocumento.getId());
                personaAux = servicioPersona.obtenerPersona(idp);

                mep.setPersonaInv(personaAux);

                List listaPais = new ArrayList();
                listaPais = servicioGeneral.obtenerListaObjetos("Pais where id ='" + idPais + "'");
                Pais pais = new Pais();
                pais = (Pais) listaPais.get(0);

                mep.setPais(pais);                
                
                List listaGrupo = new ArrayList();
    			listaGrupo = servicioGeneral.obtenerListaObjetos("Grupo where id ='" + idgrupo + "'");
    			Grupo grupo = new Grupo();
    			grupo = (Grupo) listaGrupo.get(0);

    			mep.setGrupo(grupo);

                Dependencia dependencia;
                dependencia = new Dependencia();

                if (personaAux instanceof Investigador) {
                    if (personaAux instanceof InvestigadorInterno) {
                        personaAux = servicioPersona.obtenerInvestigadorInternoCompleto(personaAux.getId());
                        InvestigadorInterno investigadorInterno = (InvestigadorInterno) personaAux;
                        if (e != null && e.getDependencia() != null) {
                            dependencia = e.getDependencia();
                        } else {
                            dependencia = servicioDependencia.obtenerDependencia(investigadorInterno.getId());
                        }
                    }
                }

                List listaParametro;
                listaParametro = new ArrayList();

                listaParametro = this.servicioGeneral
                        .obtenerObjetos("FROM Parametro WHERE nombre = '" + dependencia.getSede().getId() + "'");

                if (listaParametro == null || listaParametro.size() == 0) {
                    mep.setAceptacion("SI");
                }

                servicioGeneral.guardarObjeto(mep);

                if (listaArchivosObligatoriosSel != null && listaArchivosObligatoriosSel.size() > 0) {
                    for (int i = 0; i < listaArchivosObligatoriosSel.size(); i++) {
                        ArchivoMovilidadEP amovAux1 = (ArchivoMovilidadEP) listaArchivosObligatoriosSel.get(i);
                        amovAux1.setMovilidad(mep);
                        servicioGeneral.guardarObjeto(amovAux1);
                    }
                }

                correoActual = cargarPlantilla(65);// 42
                editarCorreo(personaAux, mep);
                Correo correo = new Correo();
                correo.setOrigen(Correo.CORREO_HERMES);
                String dirCorreo = personaAux.getEmail();
                correo.adicionarDireccion(dirCorreo);
                correo.adicionarCopiaOculta(new String(personaAux.getEmail()));
                // correo.adicionarCopiaOculta(dirCorreoConfirmacion);
                correo.setAsunto(correoActual.getAsunto());
                correo.setCuerpo(cuerpoCorreo);
                servicioCorreo.enviarCorreo(correo);

                List listaCorreoEncargado = new ArrayList();
                List listaParametroAux;
                listaParametroAux = new ArrayList();

                Dependencia dependenciaAux;
                dependenciaAux = new Dependencia();
                Persona personaEnvio = new Persona();

                personaEnvio = servicioPersona.obtenerInvestigadorInternoCompleto(mep.getPersonaInv().getId());

                InvestigadorInterno investigadorInterno = (InvestigadorInterno) personaEnvio;
                dependenciaAux = servicioDependencia.obtenerDependencia(investigadorInterno.getId());

                if (listaParametro == null || listaParametro.size() == 0) {
                    listaParametroAux = this.servicioGeneral
                            .obtenerObjetos("FROM Parametro WHERE nombre = '" + dependencia.getSede().getId() + "'");
                    correoActual = cargarPlantilla(86);

                    listaCorreoEncargado = this.servicioGeneral
                            .obtenerObjetos("FROM Parametro WHERE nombre = 'R_MOVILIDAD'    AND DESCRIPCION= '"
                                    + dependenciaAux.getSede().getId() + "'");

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
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                String correoEnvio = "sisii_nal@unal.edu.co";
                Persona personaActualAux2 = new Persona();

                if (listaCorreoEncargado != null && listaCorreoEncargado.size() > 0) {

                    boolean bandera = true;
                    Parametro paraActual = new Parametro();
                    try {
                        paraActual = (Parametro) listaCorreoEncargado.get(0);
                    } catch (Exception ex) {
                        bandera = false;
                    }

                    int numCoord = listaCorreoEncargado.size();

                    for (int i = 0; i < numCoord; i++) {
                        InvestigadorInterno paActual;
                        if (bandera) {
                            IdPersona id = new IdPersona();
                            id.setDocumento(paraActual.getValor());
                            id.setTipoDocumento(paraActual.getProfesion());
                            paActual = servicioPersona.obtenerInvestigadorInterno(id);
                        } else {
                            paActual = (InvestigadorInterno) listaCorreoEncargado.get(i);
                        }

                        String numeroDocumento = "0";
                        // numeroDocumento = paActual.getValor();
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

                        editarCorreo(personaActualAux2, mep);
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
                    editarCorreo(personaActualAux2, mep);
                    correo = new Correo();
                    correo.setOrigen(Correo.CORREO_HERMES);
                    dirCorreo = correoEnvio;
                    correo.adicionarDireccion(dirCorreo);
                    correo.adicionarCopiaOculta(dirCorreo);
                    correo.setAsunto(correoActual.getAsunto());
                    correo.setCuerpo(cuerpoCorreo);
                    servicioCorreo.enviarCorreo(correo);
                }

                noExiste1 = "Solicitud de movilidad guardada correctamente, con el número " + mep.getId()
				+ ".  Se ha enviado a su correo electrónico un mensaje confirmando el registro. Si lo considera necesario comuníquese con la "
				+ "Coordinación de Investigación de la facultad o sede a la que pertenece para la revisión de la movilidad. ";
                
                /*noExiste1 = "La movilidad con código" + mep.getId() + "se ha guardado y enviado correctamente al usuario a través del "
    					+ "sistema Hermes; sin embargo, no se ha enviado correo, les recomendamos comunicarse con "
    					+ "la Coordinación de Investigación de la facultad o sede a la que pertenece para realizar "
    					+ "la revisión de la movilidad. No hay necesidad de enviar nuevamente la movilidad";*/
                limpiar();
                mostrarPanelNoExiste();
                // enviarCorreo();
                bImprimirReporte = true;
            } else {
                mostrarError = true;
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Ha ocurrido un error, por favor revise los archivos adjuntos y el pais que ha seleccionado.",
                        msgError);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } else {
            mostrarError = true;
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msgError + detalleError,
                    msgError + detalleError);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public String editarCorreo(Persona personaAux, MovilidadEstudiantesPosgrado mov) {

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

    public String imprimirReporte() {
        long movId = mep.getId(); // movilidadGlobal.getId().longValue();
        ReporteBirt r = new ReporteBirt();
        r.adicionarParametro("id", Long.toString(movId));
        r.setNombreReporte("/movilidad/MovilidadEventos");
        r.setFormato(ReporteBirt.FORMATO_PDF);
        sesion.setAttribute("reporte", r);
        System.out.println("mirar");
        System.out.println(Navegacion.REPORTE);
        return Navegacion.REPORTE;
    }

    private boolean validar(MovilidadEstudiantesPosgrado mov) {
        boolean result = true;
        mostrarError = false;
        bErrorProyecto = false;
        bErrorEvento = false;
        bErrorTitulo = false;
        bErrorPais = false;
        bErrorCiudad = false;
        bErrorPonencia = false;
        bErrorResumen = false;
        bErrorResolucion = false;
        bErrorTiquete = false;
        bErrorInscripcion = false;
        bImprimirReporte = false;
        bErrorPais = false;

        
        if (idPais.equals("00")) {
            String error = "El país es obligatorio \n";
            errores[3] = error;
            bErrorPais = true;
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
        	ArchivoMovilidadEP mva = (ArchivoMovilidadEP) listaArchivosObligatoriosSel.get(j);
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
            errores[11] = error;
            bErrorDocumentos = true;
            result = false;
        }

        return result;
    }

    public void limpiar() {
        persona = new Persona();
        documento = new String("");
        // sesion
        // .removeAttribute("ManejadorCrearEditarMovilidadPosgradoInvestigacion");
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

    public void ocultarPaneles() {
        mostrarDatosBasicos = false;
        mostrarDatosEvento = false;
        panelMasDatos = false;
        panelNoExiste = false;
        identificacion = false;
        fechaIncorrecta2 = false;
        panelMasDatos2 = false;

        cargarTiposDocumento();
    }

    public void validarFecha(ValueChangeEvent event) {
        Date fechaEvento = (Date) event.getNewValue();

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(fechaEvento);
        int diff = 0;
        /*
         * java.util.Date fechaActual = new Date(); if
         * (fechaActual.before(fechaEvento)) { mostrarMasDatos(); } else {
         * ocultarMasDatos(); }
         */
        while (cal1.before(cal2) || cal1.equals(cal2)) // (c) on its own = 3
        // days diff
        {
            SimpleDateFormat dia = new SimpleDateFormat("EEEE");
            int diaEntero = cal1.get(Calendar.DAY_OF_WEEK);

            // System.out.println("DIA DE LA SEMANA ----------------------: " +
            // dia.format(cal1.getTime()) + "entero del dia::" + diaEntero);
            boolean esFestivo = false;
            SimpleDateFormat spd = new SimpleDateFormat("dd");
            SimpleDateFormat spm = new SimpleDateFormat("MM");
            SimpleDateFormat spy = new SimpleDateFormat("yyyy");

            /*
             * List listaFestivos = new ArrayList(); listaFestivos =
             * servicioGeneral .obtenerListaObjetos("Festivo where ano ='" +
             * Integer.parseInt(spy.format(cal1.getTime())) + "' and mes ='" +
             * Integer.parseInt(spm.format(cal1.getTime())) + "'  and dia ='" +
             * Integer.parseInt(spd.format(cal1.getTime())) + "' ");
             * 
             * if (listaFestivos != null && listaFestivos.size() > 0) {
             * esFestivo = true; // System.out.println(
             * "ES FESTIVO ----------------------: " + //
             * dia.format(cal1.getTime()) + " ** entero del dia::" + //
             * diaEntero + " fECHA COMPLETA " + cal1.getTime() ); }
             */

            cal1.add(Calendar.DATE, 1);
            // if ((diaEntero != 7 && diaEntero != 1) && !esFestivo) {
            diff++;
            // }

        }
        // System.out.print("Esta es la diferencia de días****************** " +
        // diff + "***");
        if (diff >= 20) {
            mostrarMasDatos();
        } else {
            ocultarMasDatos();
        }

    }

    public void validarFechaLlegada(ValueChangeEvent event) {
        Date fechaEvento = (Date) event.getNewValue();

        Calendar c = Calendar.getInstance();
        c.setTime(fechaInicioEvento);

        c.add(Calendar.DATE, 9);

        boolean bandera = true;

        if (fechaEvento.before(this.fechaInicioEvento)) {
            bandera = false;
        }

        Long diferencia = (fechaEvento.getTime() - fechaInicioEvento.getTime());
        double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
        if (dias < 30) {
            bandera = false;
        }

        if (bandera) {
            mostrarMasDatos2();
        } else {
            ocultarMasDatos2();
        }

    }

    private void cargarCiudades() {
        List listaCiudades = servicioGeneral.obtenerListaObjetosOrdenadosAsc(new Ciudad(), "nombre");
        ciudades = new Vector();
        for (Iterator it = listaCiudades.iterator(); it.hasNext();) {
            Ciudad c = (Ciudad) it.next();
            SelectItem s = new SelectItem(c.getId(), c.getNombre());
            ciudades.add(s);
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

    public void cargarPaises() {
       // List listaPaises = servicioGeneral.obtenerListaObjetosOrdenadosAsc(new Pais(), "nombre");
        List listaPaises = servicioGeneral.obtenerObjetos("select e from Pais e where e.continente = " + idContinente);
        paises = new Vector();
        for (Iterator it = listaPaises.iterator(); it.hasNext();) {
            Pais p = (Pais) it.next();
            SelectItem s = new SelectItem(p.getId(), p.getNombre());
            paises.add(s);
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

        List listaTipoDocumentoEstudiante = servicioGeneral.obtenerListaObjetos("TipoDocumento");

        tipoDocumentoEstudianteItem = new SelectItem[listaTipoDocumentoEstudiante.size()];
        for (int i = 0; i < listaTipoDocumentoEstudiante.size(); i++) {
            TipoDocumento td1 = (TipoDocumento) listaTipoDocumentoEstudiante.get(i);
            tipoDocumentoEstudianteItem[i] = new SelectItem(td1.getId(), td1.getNombre());
        }
        tipoDocumentoEstudiante = (TipoDocumento) listaTipoDocumentoEstudiante.get(0);

    }

    private void cargarValoresIniciales() {
        errores = new String[12];
        bErrorProyecto = false;
        bErrorEvento = false;
        bErrorTitulo = false;
        bErrorPais = false;
        bErrorPapa = false;
        bErrorDocumentos = false;
        bErrorCiudad = false;
        bErrorPonencia = false;
        bErrorResumen = false;
        bErrorResolucion = false;
        bErrorTiquete = false;
        bErrorInscripcion = false;
        mostrarDatosBasicos = false;
        mostrarDatosEvento = false;
        fechaIncorrecta = false;
        panelNoExiste = false;
        panelMasDatos = false;
        identificacion = true;
        panelArchivos = new HtmlPanelGroup();
        bOtroTipoPonencia = false;
        fechaIncorrecta2 = false;
        panelMasDatos2 = false;
        esPonencia = false;
        noExiste = "";
        aporteFacultad = new Long(0);
        costoTiquetes = new Long(0);
        costoInscripcion = new Long(0);
        personaActual = (Persona) sesion.getAttribute("persona");
        idcreador = personaActual.getId().getDocumento();
        tipoDocCreador = personaActual.getId().getTipoDocumento();
        persona = new Persona();
        documento = new String();
        tituloPonencia = "";
        resumenPonencia = "";
        universidad = "";
        cargarTiposDocumento();
        cargarCiudades();
        cargarContinentes();
        cargarPaises();
        cargarInstituciones();

        listaEntidadInvestigacion = new ArrayList();

        listaEntidadInvestigacion = servicioGeneral.obtenerListaObjetos("TipoEntidadInvestigacion");

        if (listaEntidadInvestigacion != null) {
            aprobacionInvestigacionUNItem = new SelectItem[listaEntidadInvestigacion.size()];

            for (int i = 0; i < listaEntidadInvestigacion.size(); i++) {
                TipoEntidadInvestigacion tei = (TipoEntidadInvestigacion) listaEntidadInvestigacion.get(i);
                aprobacionInvestigacionUNItem[i] = new SelectItem(tei.getId().toString(), tei.getNombre());

            }
        }

    }

    private void reiniciarVariables() {
        errores = new String[12];
        bErrorProyecto = false;
        bErrorEvento = false;
        bErrorTitulo = false;
        bErrorPais = false;
        bErrorPapa = false;
        bErrorDocumentos = false;
        bErrorCiudad = false;
        bErrorPonencia = false;
        bErrorResumen = false;
        bErrorResolucion = false;
        bErrorTiquete = false;
        bErrorInscripcion = false;
        mostrarDatosBasicos = false;
        mostrarDatosEvento = false;
        fechaIncorrecta = false;
        panelNoExiste = false;
        panelMasDatos = false;
        identificacion = true;
        panelArchivos = new HtmlPanelGroup();
        bOtroTipoPonencia = false;
        fechaIncorrecta2 = false;
        panelMasDatos2 = false;
        esPonencia = false;
        noExiste = "";
        aporteFacultad = new Long(0);
        tituloPonencia = "";
        resumenPonencia = "";
        universidad = "";
        costoTiquetes = new Long(0);
        costoInscripcion = new Long(0);
        mostrarError = false;

    }

    private void mostrarDatosPersona() {
        mostrarDatosBasicos = true;
        mostrarDatosEvento = true;
    }

    private void mostrarMasDatos() {
        panelMasDatos = true;
        fechaIncorrecta = false;
        fechaIncorrecta2 = false;
        panelMasDatos2 = false;

    }

    private void mostrarMasDatos2() {
        panelMasDatos = true;
        panelMasDatos2 = true;
        fechaIncorrecta = false;
        fechaIncorrecta2 = false;
    }

    private void mostrarPanelNoExiste() {
        panelNoExiste = true;
        panelMasDatos = false;
        panelMasDatos2 = false;
        mostrarDatosEstudiante = false;
    }

    private void ocultarMasDatos() {
        fechaIncorrecta = true;
        panelMasDatos = false;
        fechaIncorrecta2 = false;
        panelMasDatos2 = false;

    }

    private void ocultarMasDatos2() {
        fechaIncorrecta2 = true;
        panelMasDatos2 = false;
    }

    public void apruebaEntidad() {
        TipoInforme tinAux = (TipoInforme) tablaEntidadInvestigacion.getRowData();
        FacesContext ctx = FacesContext.getCurrentInstance();

        // listaInformes = new ArrayList();
        // listaInformes =
        // servicioGeneral.obtenerListaObjetos("ProyectoInforme where id
        // ='"+idInforme+"'");
        // ProyectoInforme pinAux = new ProyectoInforme();
        if (tinAux != null && tinAux.getArchivoInforme() != null) {
            // pinAux = (ProyectoInforme)listaInformes.get(0);
            try {
                if (!ctx.getResponseComplete()) {
                    HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
                    response.setContentType("text/plain");
                    response.setHeader("Content-Disposition",
                            "attachment;filename=\"" + tinAux.getNombreArchivo() + "\"");
                    ServletOutputStream out = response.getOutputStream();
                    out.write(tinAux.getBytesArchivoInforme());
                    out.flush();
                    ctx.responseComplete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void guardarArchivo() {

        try {

            if (archivoCargar.getContents() != null) {
                int i = archivoCargar.getFileName().lastIndexOf("\\");

                List listaTipoMovilidad = new ArrayList();
                listaTipoMovilidad = servicioGeneral.obtenerListaObjetos("TipoMovilidad where id ='CF_MOV5'");
                TipoMovilidad tipoMovilidad = (TipoMovilidad) listaTipoMovilidad.get(0);

                ArchivoMovilidad archivoMovilidad = new ArchivoMovilidad();
                archivoMovilidad.setBytes(archivoCargar.getContents());
                archivoMovilidad.setNombre(archivoCargar.getFileName().substring(i + 1));
                archivoMovilidad.setFecha(new Date());
                archivoMovilidad.setTipoMovilidad(tipoMovilidad);
                // listaArchivos = new ArrayList();aa

                listaArchivos.add(archivoMovilidad);
            }

        } catch (DataIntegrityViolationException ex) {
            System.out.println(ex.toString());
            // if (ex.getMessage().indexOf("AD_COMBINACION01_UK") > 0)
            // resultadoIngresoInforme = "Ya existe un archivo con este nombre";
            // else
            // resultadoIngresoInforme =
            // "Ocurrio un error inesperado al publicar el archivo";
        } catch (Exception ex) {
            System.out.println(ex.toString());
            // resultadoIngresoInforme =
            // "Ocurrio un error inesperado al publicar el archivo";
        }
    }

    public void enviarCorreo() {
        Correo correo = new Correo();
        correo.adicionarDireccion("iabohorquezc@unal.edu.co");
        correo.setOrigen(personaActual.getEmail());
        correo.adicionarCopiaOculta(new String(personaActual.getEmail()));
        String asunto = "Nueva movilidad para evento internacional";

        correo.setAsunto(asunto);// getCorreoActual().getAsunto());//"Quiere
        // ser posible evaluador");
        String cuerpo = "Se ha adicionado una nueva movilidad para evento internacional.";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm.ss");

        Calendar actual = Calendar.getInstance();
        Date date = actual.getTime();
        String strDate = formatter.format(date);
        cuerpo = cuerpo + strDate;
        correo.setCuerpo(cuerpo);// "por favoooooooor, le pagamos y todo");
        String mensajeCorreo = "";
        /*
         * if (servicioCorreo.enviarCorreo(correo)) { mensajeCorreo =
         * "Su correo ha sido enviado con exito al posible evaluador con copia a "
         * + persona.getEmail(); } else { mensajeCorreo =
         * "No se pudo enviar el correo, por favor verifique la dirección electronica"
         * ; }
         */
        System.out.println(mensajeCorreo);
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
            }
        }

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

    public void guardarArchivoMovEP(FileUploadEvent event) {

        archivoObligatorio = event.getFile();
        System.out.println("tipoDocumentoSel: " + tipoDocumentoSel);
        if (tipoDocumentoSel == null || tipoDocumentoSel.equals("")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar un tipo de archivo", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        ArchivoMovilidadEP archivoMovilidad = new ArchivoMovilidadEP();
        archivoMovilidad = insertarArchivoMovilidadEPGenerico(1, archivoObligatorio, tipoDocumentoSel);
        listaArchivosObligatoriosSel.add(archivoMovilidad);

    }

    public void descargarArchivoMovEP() {
        ArchivoMovilidadEP archivo = documentoSeleccionado;
        descargarArchivoMovilidadEPGenerico(archivo.getId());
    }

    public void eliminarArchivoObligatorio() {
        ArchivoMovilidadEP amv = archivoMovilidadEPSeleccionado;

        listaArchivosObligatoriosSel.remove(archivoMovilidadEPSeleccionado);
    }

    public String getAceptacionDIB() {
        return aceptacionDIB;
    }

    public String getAceptacionFacultad() {
        return aceptacionFacultad;
    }

    public String getAceptacionPonencia() {
        return aceptacionPonencia;
    }

    public Long getAporteFacultad() {
        return aporteFacultad;
    }

    public SelectItem[] getAprobacion() {
        return aprobacion;
    }

    public String getCategoriaInvestigador() {
        return categoriaInvestigador;
    }

    public List getCiudades() {
        return ciudades;
    }

    public String getCiudadEvento() {
        return ciudadEvento;
    }

    public Long getCostoInscripcion() {
        return costoInscripcion;
    }

    public Long getCostoTiquetes() {
        return costoTiquetes;
    }

    public String getDepartamentoDocente() {
        return departamentoDocente;
    }

    public String getDepartamentoSeleccionado() {
        return departamentoSeleccionado;
    }

    public Dependencia getDependencia() {
        return dependencia;
    }

    public String getDependenciaId() {
        return dependenciaId;
    }

    public SelectItem[] getDepsItem() {
        return depsItem;
    }

    public Long getDestinado() {
        return destinado;
    }

    public String getDocumento() {
        return documento;
    }

    public String getDocumentoDocente() {
        return documentoDocente;
    }

    public String getFacultadDocente() {
        return facultadDocente;
    }

    public String getFacultadSeleccionada() {
        return facultadSeleccionada;
    }

    public Date getFechaFinEvento() {
        return fechaFinEvento;
    }

    public Date getFechaInicioEvento() {
        return fechaInicioEvento;
    }

    public String getFinanciacionActual() {
        return financiacionActual;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public Grupo getGrupoActual() {
        return grupoActual;
    }

    public String getIdCiudad() {
        return idCiudad;
    }

    public String getIdcreador() {
        return idcreador;
    }

    public String getIdgrupo() {
        return idgrupo;
    }

    public String getIdInstitucion() {
        return idInstitucion;
    }

    public String getIdInvestigador() {
        return idInvestigador;
    }

    public String getIdPais() {
        return idPais;
    }

    public String getIdProyecto() {
        return idProyecto;
    }

    public String getIdTipoPonencia() {
        return idTipoPonencia;
    }

    public SelectItem[] getInstitucionItem() {
        return institucionItem;
    }

    public Investigador getInvestigadorActual() {
        return investigadorActual;
    }

    public SelectItem[] getInvestigadorItem() {
        return investigadorItem;
    }

    public List getListaArchivos() {
        return listaArchivos;
    }

    public List getListaCategoriaInvestigador() {
        return listaCategoriaInvestigador;
    }

    public List getListaDependencias() {
        return listaDependencias;
    }

    public List getListaGrupos() {
        return listaGrupos;
    }

    public List getListaGruposInv() {
        return listaGruposInv;
    }

    public List getListaInstitucion() {
        return listaInstitucion;
    }

    public List getListaIntegrantesGrupo() {
        return listaIntegrantesGrupo;
    }

    public List getListaTipoArchivo() {
        return listaTipoArchivo;
    }

    public String getNombreDepartamentoSeleccionado() {
        return nombreDepartamentoSeleccionado;
    }

    public String getNombreDocente() {
        return nombreDocente;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public String getNombreFacultadSeleccionada() {
        return nombreFacultadSeleccionada;
    }

    public String getNombreLiderGrupo() {
        return nombreLiderGrupo;
    }

    public String getNombreSedeSeleccionada() {
        return nombreSedeSeleccionada;
    }

    public String getOtroTipoPonencia() {
        return otroTipoPonencia;
    }

    public List getPaises() {
        return paises;
    }

    public HtmlPanelGroup getPanelArchivos() {
        return panelArchivos;
    }

    public Persona getPersona() {
        return persona;
    }

    public List getProyectosInvestigador() {
        return proyectosInvestigador;
    }

    public String getResolucionViaje() {
        return resolucionViaje;
    }

    public Investigador getResponsableGrupo() {
        return responsableGrupo;
    }

    public String getResumenPonencia() {
        return resumenPonencia;
    }

    public String getSedeSeleccionada() {
        return sedeSeleccionada;
    }

    public String getSolDocente() {
        return solDocente;
    }

    public String getSolinscripcion() {
        return solInscripcion;
    }

    public String getSolInscripcion() {
        return solInscripcion;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public SelectItem[] getTipoDocumentoItem() {
        return tipoDocumentoItem;
    }

    public String getTipoInvestigador() {
        return tipoInvestigador;
    }

    public SelectItem[] getTipoPonencia() {
        return tipoPonencia;
    }

    public String getTipoPonenciaActual() {
        return tipoPonenciaActual;
    }

    public String getTituloPonencia() {
        return tituloPonencia;
    }

    public boolean isBFinanciacion() {
        return bFinanciacion;
    }

    public boolean isBOtroTipoPonencia() {
        return bOtroTipoPonencia;
    }

    public boolean isFechaIncorrecta() {
        return fechaIncorrecta;
    }

    public boolean isFechaIncorrecta2() {
        return fechaIncorrecta2;
    }

    public boolean isIdentificacion() {
        return identificacion;
    }

    public boolean isMostrarDatosBasicos() {
        return mostrarDatosBasicos;
    }

    public boolean isMostrarDatosEvento() {
        return mostrarDatosEvento;
    }

    public boolean isPanelMasDatos() {
        return panelMasDatos;
    }

    public boolean isPanelMasDatos2() {
        return panelMasDatos2;
    }

    public boolean isPanelNoExiste() {
        return panelNoExiste;
    }

    public void setAceptacionDIB(String aceptacionDIB) {
        this.aceptacionDIB = aceptacionDIB;
    }

    public void setAceptacionFacultad(String aceptacionFacultad) {
        this.aceptacionFacultad = aceptacionFacultad;
    }

    public void setAceptacionPonencia(String aceptacionPonencia) {
        this.aceptacionPonencia = aceptacionPonencia;
    }

    public void setAporteFacultad(Long aporteFacultad) {
        this.aporteFacultad = aporteFacultad;
    }

    public void setAprobacion(SelectItem[] aprobacion) {
        this.aprobacion = aprobacion;
    }

    public void setBFinanciacion(boolean financiacion) {
        bFinanciacion = financiacion;
    }

    public void setBOtroTipoPonencia(boolean otroTipoPonencia) {
        bOtroTipoPonencia = otroTipoPonencia;
    }

    public void setCategoriaInvestigador(String categoriaInvestigador) {
        this.categoriaInvestigador = categoriaInvestigador;
    }

    public void setCiudades(List ciudades) {
        this.ciudades = ciudades;
    }

    public void setCiudadEvento(String ciudadEvento) {
        this.ciudadEvento = ciudadEvento;
    }

    public void setCostoInscripcion(Long costoInscripcion) {
        this.costoInscripcion = costoInscripcion;
    }

    public void setCostoTiquetes(Long costoTiquetes) {
        this.costoTiquetes = costoTiquetes;
    }

    public void setDepartamentoDocente(String departamentoDocente) {
        this.departamentoDocente = departamentoDocente;
    }

    public void setDepartamentoSeleccionado(String departamentoSeleccionado) {
        this.departamentoSeleccionado = departamentoSeleccionado;
    }

    public void setDependencia(Dependencia dependencia) {
        this.dependencia = dependencia;
    }

    public void setDependenciaId(String dependenciaId) {
        this.dependenciaId = dependenciaId;
    }

    public void setDepsItem(SelectItem[] depsItem) {
        this.depsItem = depsItem;
    }

    public void setDestinado(Long destinado) {
        this.destinado = destinado;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public void setDocumentoDocente(String documentoDocente) {
        this.documentoDocente = documentoDocente;
    }

    public void setFacultadDocente(String facultadDocente) {
        this.facultadDocente = facultadDocente;
    }

    public void setFacultadSeleccionada(String facultadSeleccionada) {
        this.facultadSeleccionada = facultadSeleccionada;
    }

    public void setFechaFinEvento(Date fechaFinEvento) {
        this.fechaFinEvento = fechaFinEvento;
    }

    public void setFechaIncorrecta(boolean fechaIncorrecta) {
        this.fechaIncorrecta = fechaIncorrecta;
    }

    public void setFechaIncorrecta2(boolean fechaIncorrecta2) {
        this.fechaIncorrecta2 = fechaIncorrecta2;
    }

    public void setFechaInicioEvento(Date fechaInicioEvento) {
        this.fechaInicioEvento = fechaInicioEvento;
    }

    public void setFinanciacionActual(String financiacionActual) {
        this.financiacionActual = financiacionActual;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public void setGrupoActual(Grupo grupoActual) {
        this.grupoActual = grupoActual;
    }

    public void setIdCiudad(String idCiudad) {
        this.idCiudad = idCiudad;
    }

    public void setIdcreador(String idcreador) {
        this.idcreador = idcreador;
    }

    public void setIdentificacion(boolean identificacion) {
        this.identificacion = identificacion;
    }

    public void setIdgrupo(String idgrupo) {
        this.idgrupo = idgrupo;
    }

    public void setIdInstitucion(String idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    public void setIdInvestigador(String idInvestigador) {
        this.idInvestigador = idInvestigador;
    }

    public void setIdPais(String idPais) {
        this.idPais = idPais;
    }

    public void setIdProyecto(String idProyecto) {
        this.idProyecto = idProyecto;
    }

    public void setIdTipoPonencia(String idTipoPonencia) {
        this.idTipoPonencia = idTipoPonencia;
    }

    public void setInstitucionItem(SelectItem[] institucionItem) {
        this.institucionItem = institucionItem;
    }

    public void setInvestigadorActual(Investigador investigadorActual) {
        this.investigadorActual = investigadorActual;
    }

    public void setInvestigadorItem(SelectItem[] investigadorItem) {
        this.investigadorItem = investigadorItem;
    }

    public void setListaArchivos(List listaArchivos) {
        this.listaArchivos = listaArchivos;
    }

    public void setListaCategoriaInvestigador(List listaCategoriaInvestigador) {
        this.listaCategoriaInvestigador = listaCategoriaInvestigador;
    }

    public void setListaDependencias(List listaDependencias) {
        this.listaDependencias = listaDependencias;
    }

    public void setListaGrupos(List listaGrupos) {
        this.listaGrupos = listaGrupos;
    }

    public void setListaGruposInv(List listaGruposInv) {
        this.listaGruposInv = listaGruposInv;
    }

    public void setListaInstitucion(List listaInstitucion) {
        this.listaInstitucion = listaInstitucion;
    }

    public void setListaIntegrantesGrupo(List listaIntegrantesGrupo) {
        this.listaIntegrantesGrupo = listaIntegrantesGrupo;
    }

    public void setListaTipoArchivo(List listaTipoArchivo) {
        this.listaTipoArchivo = listaTipoArchivo;
    }

    public void setMostrarDatosBasicos(boolean mostrarDatosBasicos) {
        this.mostrarDatosBasicos = mostrarDatosBasicos;
    }

    public void setMostrarDatosEvento(boolean mostrarDatosEvento) {
        this.mostrarDatosEvento = mostrarDatosEvento;
    }

    public void setNombreDepartamentoSeleccionado(String nombreDepartamentoSeleccionado) {
        this.nombreDepartamentoSeleccionado = nombreDepartamentoSeleccionado;
    }

    public void setNombreDocente(String nombreDocente) {
        this.nombreDocente = nombreDocente;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public void setNombreFacultadSeleccionada(String nombreFacultadSeleccionada) {
        this.nombreFacultadSeleccionada = nombreFacultadSeleccionada;
    }

    public void setNombreLiderGrupo(String nombreLiderGrupo) {
        this.nombreLiderGrupo = nombreLiderGrupo;
    }

    public void setNombreSedeSeleccionada(String nombreSedeSeleccionada) {
        this.nombreSedeSeleccionada = nombreSedeSeleccionada;
    }

    public void setOtroTipoPonencia(String otroTipoPonencia) {
        this.otroTipoPonencia = otroTipoPonencia;
    }

    public void setPaises(List paises) {
        this.paises = paises;
    }

    public void setPanelArchivos(HtmlPanelGroup panelArchivos) {
        this.panelArchivos = panelArchivos;
    }

    public void setPanelMasDatos(boolean panelMasDatos) {
        this.panelMasDatos = panelMasDatos;
    }

    public void setPanelMasDatos2(boolean panelMasDatos2) {
        this.panelMasDatos2 = panelMasDatos2;
    }

    public void setPanelNoExiste(boolean panelNoExiste) {
        this.panelNoExiste = panelNoExiste;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public void setProyectosInvestigador(List proyectosInvestigador) {
        this.proyectosInvestigador = proyectosInvestigador;
    }

    public void setResolucionViaje(String resolucionViaje) {
        this.resolucionViaje = resolucionViaje;
    }

    public void setResponsableGrupo(Investigador responsableGrupo) {
        this.responsableGrupo = responsableGrupo;
    }

    public void setResumenPonencia(String resumenPonencia) {
        this.resumenPonencia = resumenPonencia;
    }

    public void setSedeSeleccionada(String sedeSeleccionada) {
        this.sedeSeleccionada = sedeSeleccionada;
    }

    public void setSolDocente(String solDocente) {
        this.solDocente = solDocente;
    }

    public void setSolinscripcion(String solInscripcion) {
        this.solInscripcion = solInscripcion;
    }

    public void setSolInscripcion(String solInscripcion) {
        this.solInscripcion = solInscripcion;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
        this.tipoDocumentoItem = tipoDocumentoItem;
    }

    public void setTipoInvestigador(String tipoInvestigador) {
        this.tipoInvestigador = tipoInvestigador;
    }

    public void setTipoPonencia(SelectItem[] tipoPonencia) {
        this.tipoPonencia = tipoPonencia;
    }

    public void setTipoPonenciaActual(String tipoPonenciaActual) {
        this.tipoPonenciaActual = tipoPonenciaActual;
    }

    public void setTituloPonencia(String tituloPonencia) {
        this.tituloPonencia = tituloPonencia;
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

    public boolean isBErrorProyecto() {
        return bErrorProyecto;
    }

    public void setBErrorProyecto(boolean errorProyecto) {
        bErrorProyecto = errorProyecto;
    }

    public boolean isBErrorEvento() {
        return bErrorEvento;
    }

    public void setBErrorEvento(boolean errorEvento) {
        bErrorEvento = errorEvento;
    }

    public boolean isBErrorTitulo() {
        return bErrorTitulo;
    }

    public void setBErrorTitulo(boolean errorTitulo) {
        bErrorTitulo = errorTitulo;
    }

    public boolean isBErrorPais() {
        return bErrorPais;
    }

    public void setBErrorPais(boolean errorPais) {
        bErrorPais = errorPais;
    }

    public boolean isBErrorDocumentos() {
        return bErrorDocumentos;
    }

    public void setBErrorDocumentos(boolean errorDocumentos) {
        bErrorDocumentos = errorDocumentos;
    }

    public boolean isBErrorPapa() {
        return bErrorPapa;
    }

    public void setBErrorPapa(boolean errorPapa) {
        bErrorPapa = errorPapa;
    }

    public boolean isBErrorCiudad() {
        return bErrorCiudad;
    }

    public void setBErrorCiudad(boolean errorCiudad) {
        bErrorCiudad = errorCiudad;
    }

    public boolean isBErrorPonencia() {
        return bErrorPonencia;
    }

    public void setBErrorPonencia(boolean errorPonencia) {
        bErrorPonencia = errorPonencia;
    }

    public boolean isBErrorResumen() {
        return bErrorResumen;
    }

    public void setBErrorResumen(boolean errorResumen) {
        bErrorResumen = errorResumen;
    }

    public boolean isBErrorResolucion() {
        return bErrorResolucion;
    }

    public void setBErrorResolucion(boolean errorResolucion) {
        bErrorResolucion = errorResolucion;
    }

    public boolean isBErrorTiquete() {
        return bErrorTiquete;
    }

    public void setBErrorTiquete(boolean errorTiquete) {
        bErrorTiquete = errorTiquete;
    }

    public boolean isBErrorInscripcion() {
        return bErrorInscripcion;
    }

    public void setBErrorInscripcion(boolean errorInscripcion) {
        bErrorInscripcion = errorInscripcion;
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

    public String getAprobacionInvestigacionUN() {
        return aprobacionInvestigacionUN;
    }

    public void setAprobacionInvestigacionUN(String aprobacionInvestigacionUN) {
        this.aprobacionInvestigacionUN = aprobacionInvestigacionUN;
    }

    public SelectItem[] getAprobacionInvestigacionUNItem() {
        return aprobacionInvestigacionUNItem;
    }

    public void setAprobacionInvestigacionUNItem(SelectItem[] aprobacionInvestigacionUNItem) {
        this.aprobacionInvestigacionUNItem = aprobacionInvestigacionUNItem;
    }

    public HtmlDataTable getTablaEntidadInvestigacion() {
        return tablaEntidadInvestigacion;
    }

    public void setTablaEntidadInvestigacion(HtmlDataTable tablaEntidadInvestigacion) {
        this.tablaEntidadInvestigacion = tablaEntidadInvestigacion;
    }

    public List getListaEntidadInvestigacion() {
        return listaEntidadInvestigacion;
    }

    public void setListaEntidadInvestigacion(List listaEntidadInvestigacion) {
        this.listaEntidadInvestigacion = listaEntidadInvestigacion;
    }

    public UploadedFile getArchivoCargar() {
        return archivoCargar;
    }

    public void setArchivoCargar(UploadedFile archivoCargar) {
        this.archivoCargar = archivoCargar;
    }

    public HtmlDataTable getTablaArchivos() {
        return tablaArchivos;
    }

    public void setTablaArchivos(HtmlDataTable tablaArchivos) {
        this.tablaArchivos = tablaArchivos;
    }

    public String getDocumentoEstudiante() {
        return documentoEstudiante;
    }

    public void setDocumentoEstudiante(String documentoEstudiante) {
        this.documentoEstudiante = documentoEstudiante;
    }

    public TipoDocumento getTipoDocumentoEstudiante() {
        return tipoDocumentoEstudiante;
    }

    public void setTipoDocumentoEstudiante(TipoDocumento tipoDocumentoEstudiante) {
        this.tipoDocumentoEstudiante = tipoDocumentoEstudiante;
    }

    public SelectItem[] getTipoDocumentoEstudianteItem() {
        return tipoDocumentoEstudianteItem;
    }

    public void setTipoDocumentoEstudianteItem(SelectItem[] tipoDocumentoEstudianteItem) {
        this.tipoDocumentoEstudianteItem = tipoDocumentoEstudianteItem;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public boolean isMostrarEstudiante() {
        return mostrarEstudiante;
    }

    public void setMostrarEstudiante(boolean mostrarEstudiante) {
        this.mostrarEstudiante = mostrarEstudiante;
    }

    public boolean isMostrarDatosEstudiante() {
        return mostrarDatosEstudiante;
    }

    public void setMostrarDatosEstudiante(boolean mostrarDatosEstudiante) {
        this.mostrarDatosEstudiante = mostrarDatosEstudiante;
    }

    public Float getPapaEstudiante() {
        return papaEstudiante;
    }

    public void setPapaEstudiante(Float papaEstudiante) {
        this.papaEstudiante = papaEstudiante;
    }

    public String getNoExiste1() {
        return noExiste1;
    }

    public void setNoExiste1(String noExiste1) {
        this.noExiste1 = noExiste1;
    }

    public boolean isEsPonencia() {
        return esPonencia;
    }

    public void setEsPonencia(boolean esPonencia) {
        this.esPonencia = esPonencia;
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

    public HtmlDataTable getTablaArchivosObligatoriosSel() {
        return tablaArchivosObligatoriosSel;
    }

    public void setTablaArchivosObligatoriosSel(HtmlDataTable tablaArchivosObligatoriosSel) {
        this.tablaArchivosObligatoriosSel = tablaArchivosObligatoriosSel;
    }

    public List getListaArchivosObligatorios() {
        return listaArchivosObligatorios;
    }

    public void setListaArchivosObligatorios(List listaArchivosObligatorios) {
        this.listaArchivosObligatorios = listaArchivosObligatorios;
    }

    public List getListaArchivosObligatoriosSel() {
        return listaArchivosObligatoriosSel;
    }

    public void setListaArchivosObligatoriosSel(List listaArchivosObligatoriosSel) {
        this.listaArchivosObligatoriosSel = listaArchivosObligatoriosSel;
    }

    public String getNombreObligatorio() {
        return nombreObligatorio;
    }

    public void setNombreObligatorio(String nombreObligatorio) {
        this.nombreObligatorio = nombreObligatorio;
    }

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
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

    /**
     * @return the fechaMinimaInicio
     */
    public Date getFechaMinimaInicio() {
        return fechaMinimaInicio;
    }

    /**
     * @param fechaMinimaInicio
     *            the fechaMinimaInicio to set
     */
    public void setFechaMinimaInicio(Date fechaMinimaInicio) {
        this.fechaMinimaInicio = fechaMinimaInicio;
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

    /**
     * @return the archivoMovilidadEPSeleccionado
     */
    public ArchivoMovilidadEP getArchivoMovilidadEPSeleccionado() {
        return archivoMovilidadEPSeleccionado;
    }

    /**
     * @param archivoMovilidadEPSeleccionado
     *            the archivoMovilidadEPSeleccionado to set
     */
    public void setArchivoMovilidadEPSeleccionado(ArchivoMovilidadEP archivoMovilidadEPSeleccionado) {
        this.archivoMovilidadEPSeleccionado = archivoMovilidadEPSeleccionado;
    }

    public Long getIdContinente() {
        return idContinente;
    }

    public void setIdContinente(Long idContinente) {
        this.idContinente = idContinente;
    }

    public List getContinentes() {
        return continentes;
    }

    public void setContinentes(List continentes) {
        this.continentes = continentes;
    }

    public String getCarreraDocente() {
        return carreraDocente;
    }

    public void setCarreraDocente(String carreraDocente) {
        this.carreraDocente = carreraDocente;
    }

    public String getCorreoDocente() {
        return correoDocente;
    }

    public void setCorreoDocente(String correoDocente) {
        this.correoDocente = correoDocente;
    }

    public ArchivoMovilidadEP getDocumentoSeleccionado() {
        return documentoSeleccionado;
    }

    public void setDocumentoSeleccionado(ArchivoMovilidadEP documentoSeleccionado) {
        this.documentoSeleccionado = documentoSeleccionado;
    }

    public String getRestriccionArchivosConv() {
        return restriccionArchivosConv;
    }

    public void setRestriccionArchivosConv(String restriccionArchivosConv) {
        this.restriccionArchivosConv = restriccionArchivosConv;
    }

    public boolean isEsConvocatoriaFacultad() {
        return esConvocatoriaFacultad;
    }

    public void setEsConvocatoriaFacultad(boolean esConvocatoriaFacultad) {
        this.esConvocatoriaFacultad = esConvocatoriaFacultad;
    }

    public boolean isMostrarValorViaticos() {
        return mostrarValorViaticos;
    }

    public void setMostrarValorViaticos(boolean mostrarValorViaticos) {
        this.mostrarValorViaticos = mostrarValorViaticos;
    }

	/**
	 * @return the valorAporteOtrasDep
	 */
	public String getValorAporteOtrasDep()
	{
		return valorAporteOtrasDep;
	}

	/**
	 * @param valorAporteOtrasDep the valorAporteOtrasDep to set
	 */
	public void setValorAporteOtrasDep(String valorAporteOtrasDep)
	{
		this.valorAporteOtrasDep = valorAporteOtrasDep;
	}

	/**
	 * @return the depValorAporteOtrasDep
	 */
	public String getDepValorAporteOtrasDep()
	{
		return depValorAporteOtrasDep;
	}

	/**
	 * @param depValorAporteOtrasDep the depValorAporteOtrasDep to set
	 */
	public void setDepValorAporteOtrasDep(String depValorAporteOtrasDep)
	{
		this.depValorAporteOtrasDep = depValorAporteOtrasDep;
	}

	/**
	 * @return the medioTransporte
	 */
	public String getMedioTransporte()
	{
		return medioTransporte;
	}

	/**
	 * @param medioTransporte the medioTransporte to set
	 */
	public void setMedioTransporte(String medioTransporte)
	{
		this.medioTransporte = medioTransporte;
	}

	/**
	 * @return the medioTransporteItem
	 */
	public SelectItem[] getMedioTransporteItem()
	{
		return medioTransporteItem;
	}

	/**
	 * @param medioTransporteItem the medioTransporteItem to set
	 */
	public void setMedioTransporteItem(SelectItem[] medioTransporteItem)
	{
		this.medioTransporteItem = medioTransporteItem;
	}

	/**
	 * @return the mostrarAportesOtrasDependencias
	 */
	public boolean isMostrarAportesOtrasDependencias()
	{
		return mostrarAportesOtrasDependencias;
	}

	/**
	 * @param mostrarAportesOtrasDependencias the mostrarAportesOtrasDependencias to set
	 */
	public void setMostrarAportesOtrasDependencias(boolean mostrarAportesOtrasDependencias)
	{
		this.mostrarAportesOtrasDependencias = mostrarAportesOtrasDependencias;
	}

	/**
	 * @return the esParaPregrado
	 */
	public boolean isEsParaPregrado()
	{
		return esParaPregrado;
	}

	/**
	 * @param esParaPregrado the esParaPregrado to set
	 */
	public void setEsParaPregrado(boolean esParaPregrado)
	{
		this.esParaPregrado = esParaPregrado;
	}

}
