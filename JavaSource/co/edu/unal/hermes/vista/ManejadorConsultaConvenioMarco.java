package co.edu.unal.hermes.vista;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;

import co.edu.unal.hermes.modelo.ArchivoConceptoContrato;
import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.modelo.CoinvestigadorAval;
import co.edu.unal.hermes.modelo.ConceptoContratoBiodiversidad;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.HistoricoEstadoGrupo;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.PersonaTramiteBiodiversidad;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.TipoInvestigador;
import co.edu.unal.hermes.modelo.TipoTramiteBiodiversidad;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.evaluadores.ProyectoCoordinador;

public class ManejadorConsultaConvenioMarco extends ManejadorBasePermisosMarco {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<Aval> listaSolicitudesRecoleccionEspecimenes;
    private List<ConceptoContratoBiodiversidad> listaSolicitudesAccesoRecurso;
    private List<Aval> listaSolicitudesRecoleccionEspecimenesFiltered;
    private List<ConceptoContratoBiodiversidad> listaSolicitudesAccesoRecursoFiltered;

    private Long idAval;
    private Aval aval;
    private List<CoinvestigadorAval> listaIntegrantesSolicitud;
    private List<HistoricoEstadoGrupo> historicosEstadoGrupo;
    private InvestigadorInterno personaSolicitante = new InvestigadorInterno();
    private String observaciones;
    private String observacionPermiso;

    private List<InvestigadorInterno> listaInvestigadoresPermiso;
    private List<InvestigadorInterno> listaInvestigadoresIncluidosFiltered;
    private InvestigadorInterno investigadorSeleccionado;
    private int totalInvestigadores;

    private List<ConceptoContratoBiodiversidad> listaConceptosTramitados;
    private List<ConceptoContratoBiodiversidad> listaConceptosTramitadosFiltered;

    private int reporte;
    private final static String[] reportes;

    static {
        reportes = new String[8];
        reportes[0] = "Investigadores incluidos en permiso marco de recolección con grupo";
        reportes[1] = "Grupos incluidos en permiso marco de recolección";
        reportes[2] = "Certificados de movilización solicitados";
        reportes[3] = "Proyectos incluidos en permiso marco de recolección";
        reportes[4] = "Censo de docentes interesados en tramitar contratos de acceso a recurso genético";
        reportes[5] = "Solicitudes de concepto de necesidad de suscripción de contrato u otrosí";
        reportes[6] = "Proyectos con contratos individuales/otrosí suscritos";
        reportes[7] = "Solicitudes de biodiversidad (ej. trámite de Exp/Imp, PNN, etc.)";
    }

    private List<Object[]> reportesHabilitados;
    
    public ManejadorConsultaConvenioMarco() {
        evaluarEncargados();
        
        if(esEncargadoInclusionPM){
            cargarListasPermisoMarco();
        }
        
        if(esEncargadoSolicitudConceptoAR){
            cargarListasAccesoRecursoGenetico();
        }
        
        totalInvestigadores = 0;
        cargarPosiblesReportes(8);
        
    }
    
    private void cargarListasPermisoMarco(){
        listaSolicitudesRecoleccionEspecimenes = cargarPermisosSolicitados(Aval.TIPO_PERMISO_RECOLECCION);
        obtenerListaInvestigadoresIncluidos();
    }
    
    private void cargarListasAccesoRecursoGenetico(){
        listaSolicitudesAccesoRecurso = cargarConceptosSolicitados();
        obtenerListaConceptosTramitados();
    }

    public void cargarPosiblesReportes(int number) {
        reportesHabilitados = new ArrayList<Object[]>();
        for (int i = 0; i < number; i++) {
            Object[] r1 = new Object[2];
            r1[0] = i;
            r1[1] = reportes[i];
            reportesHabilitados.add(r1);
        }
    }

    public void obtenerListaInvestigadoresIncluidos() {
        String sqlDependencia = "";
        
        if(dependenciaTramitaPermiso.equals(TipoTramiteBiodiversidad.FACULTAD)){
            sqlDependencia = "and a.dependencia.facultad.id = '"+investigadorActual.getDependencia().getFacultad().getId()+"'";
        }else if (dependenciaTramitaPermiso.equals(TipoTramiteBiodiversidad.SEDE)){
            sqlDependencia = "and a.dependencia.sede.id = '"+investigadorActual.getDependencia().getSede().getId()+"'";
        }
        
        listaInvestigadoresPermiso = servicioGeneral.obtenerObjetosLimitado(InvestigadorInterno.class,
                "select distinct #tienePermisoMarco max(ii.id.tipoDocumento), #telExtension max(ii.id.documento), "
                        + "#urlWebDocente max(g.nombre)," + "#perfil max(ii.nombre1) || ' ' || max(ii.nombre2)"
                        + "|| ' ' || max(ii.apellido1) || ' ' || max(ii.apellido2), #temporal max(a.aviId) "
                        + "from Aval a, Grupo g, InvestigadorInterno ii" + " where ii.tienePermisoMarco = 'S' "
                        + " and g.id = a.grupoCandidato and a.modalidadDoc = 'I' "
                        + "and a.documento = ii.id.documento "
                        + "and a.tipoDocumento = ii.id.tipoDocumento and a.aviEstado = 'F' and a.aviAvalfacultad = 'S' and a.tipo = '"
                        + Aval.TIPO_PERMISO_RECOLECCION + "' "+sqlDependencia+" group by ii.id.documento");
        if (!esListaVacia(listaInvestigadoresPermiso)) {
            totalInvestigadores = listaInvestigadoresPermiso.size();
        }
    }

    public void obtenerListaConceptosTramitados() {
        String sqlDependencia = "";
        
        if(dependenciaTramitaContrato.equals(TipoTramiteBiodiversidad.FACULTAD)){
            sqlDependencia = "and c.dependencia.facultad.id = '"+investigadorActual.getDependencia().getFacultad().getId()+"'";
        }else if (dependenciaTramitaContrato.equals(TipoTramiteBiodiversidad.SEDE)){
            sqlDependencia = "and c.dependencia.sede.id = '"+investigadorActual.getDependencia().getSede().getId()+"'";
        }
        
        String dpnsql = "select c from ConceptoContratoBiodiversidad c where c.estado not in ('B','F','E','T')  "+sqlDependencia+"  order by c.id desc";
        listaConceptosTramitados = servicioGeneral.obtenerObjetos(ConceptoContratoBiodiversidad.class, dpnsql);
    }

    public List<Aval> cargarPermisosSolicitados(String tipo) {
        String sqlDependencia = "";
        
        if(dependenciaTramitaPermiso.equals(TipoTramiteBiodiversidad.FACULTAD)){
            sqlDependencia = "and a.dependencia.facultad.id = '"+investigadorActual.getDependencia().getFacultad().getId()+"'";
        }else if (dependenciaTramitaPermiso.equals(TipoTramiteBiodiversidad.SEDE)){
            sqlDependencia = "and a.dependencia.sede.id = '"+investigadorActual.getDependencia().getSede().getId()+"'";
        }
        return servicioGeneral.obtenerObjetosLimitado(Aval.class,
                "select #aviId a.aviId, #fecha a.fecha, #aviEstado a.aviEstado, "
                        + "#nombreGrupoPINV g.nombre, #aviTitulo ii.tienePermisoMarco, "
                        + "#palabraClave ii.nombre1 || ' ' || ii.nombre2 "
                        + "|| ' ' || ii.apellido1 || ' ' || ii.apellido2, #nombreProyecto g.idColciencias, #modalidadDoc a.modalidadDoc "
                        + "from Aval a, Grupo g, InvestigadorInterno ii where a.tipo = '" + tipo + "' "
                        + " and g.id = a.grupoCandidato " + "and a.documento = ii.id.documento "
                        + "and a.tipoDocumento = ii.id.tipoDocumento and a.aviEstado in ('I','P') "+sqlDependencia
                        + " order by a.aviId desc");
    }

    public void consultarInformacionSolicitud() {
        listaIntegrantesSolicitud = new ArrayList<CoinvestigadorAval>();
        List<Aval> lista = servicioGeneral.obtenerAval(idAval.toString());
        if (!esListaVacia(lista)) {
            aval = lista.get(0);
            if (aval != null && aval.getCoinvestigador().size() > 0) {
                for (Iterator<CoinvestigadorAval> iterador = aval.getCoinvestigador().iterator(); iterador.hasNext();) {
                    CoinvestigadorAval participante = (CoinvestigadorAval) iterador.next();
                    listaIntegrantesSolicitud.add(participante);
                }
            }
            if (aval.getDocumento() != null && aval.getTipoDocumento() != null) {
                IdPersona idPer = new IdPersona();

                idPer.setDocumento(aval.getDocumento());
                idPer.setTipoDocumento(aval.getTipoDocumento());
                personaSolicitante = servicioPersona.obtenerInvestigadorInterno(idPer);
                aval.setNombreInvestigador(
                        personaSolicitante != null ? personaSolicitante.getNombreCompletoMinusculas() : "");
                aval.setAviTitulo(personaSolicitante.getDependencia().getFacultad().getNombre());
            }
            List<Grupo> grupos = servicioGeneral.obtenerObjetosLimitado(Grupo.class,
                    "select #nombre g.nombre, #estadoGrupoColciencias g.estadoGrupoColciencias " + "from Grupo g"
                            + " where g.id = '" + aval.getGrupoCandidato() + "' " + "order by g.nombre desc");

            if (!esListaVacia(grupos)) {
                Grupo grupo = (Grupo) grupos.get(0);
                aval.setNombreGrupoPINV(grupo.getNombre());
                aval.setPalabraClave(grupo.getEstadoGrupoColciencias().getNombre());
            }

            setHistoricosEstadoGrupo(servicioGeneral.obtenerObjetos(HistoricoEstadoGrupo.class,
                    "from HistoricoEstadoGrupo h where h.grupo.id = '" + aval.getGrupoCandidato()
                            + "' order by h.fecha asc"));

        }
    }

    public List<ConceptoContratoBiodiversidad> cargarConceptosSolicitados() {
        String sqlDependencia = "";
        
        if(dependenciaTramitaContrato.equals(TipoTramiteBiodiversidad.FACULTAD)){
            sqlDependencia = "and cc.dependencia.facultad.id = '"+investigadorActual.getDependencia().getFacultad().getId()+"'";
        }else if (dependenciaTramitaContrato.equals(TipoTramiteBiodiversidad.SEDE)){
            sqlDependencia = "and cc.dependencia.sede.id = '"+investigadorActual.getDependencia().getSede().getId()+"'";
        }
        return servicioGeneral.obtenerObjetosLimitado(ConceptoContratoBiodiversidad.class,
                "select #id cc.id, #fechaSolicitud cc.fechaSolicitud, " + "#investigador ii, #modalidad m, #proyecto p, #estado cc.estado "
                        + "from ConceptoContratoBiodiversidad cc, InvestigadorInterno ii, Convocatoria m, Proyecto p"
                        + " where cc.proyecto.id = p.id "
                        + " and cc.investigador.id.tipoDocumento = ii.id.tipoDocumento "
                        + "and cc.investigador.id.documento = ii.id.documento "
                        + "and cc.estado in ('E','T') and cc.modalidad.id = m.id  "+sqlDependencia+" order by cc.id asc");
    }

    public void adjuntarArchivo(FileUploadEvent event) {
        adjuntarArchivoConcepto(event, ARCHIVO_DESDE_COORDINADOR);
    }

    public void eliminarArchivo() {
        eliminarArchivoConcepto(ARCHIVO_DESDE_COORDINADOR);
    }

    public void aceptarSolicitud() {
        actualizarPermiso("S");
    }

    public void rechazarSolicitud() {
        actualizarPermiso("N");
    }

    public void revisarSolicitud() {
        actualizarPermiso("P");
    }

    public void actualizarPermiso(String decision) {
        String sol;
        String estado = "";
        if ("S".equals(decision)) {
            if ("I".equals(aval.getModalidadDoc())) {
                personaSolicitante.setTienePermisoMarco("S");
                estado = " a partir de este momento usted se encuentra incluído y puede proceder a la vinculación del proyecto en cual debe realizar la recolección. \r\n"
                		+ "Por favor tener en cuenta el instructivo http://www.hermes.unal.edu.co/pages/html/descargas/instructivo.xhtml?id=326 \r\n"
                		+ "";
            } else {
                personaSolicitante.setTienePermisoMarco("N");
                estado = " a partir de este momento usted se encuentra excluído del Permiso.";
            }
            servicioGeneral.guardarObjeto(personaSolicitante);
        }

        if ("I".equals(aval.getModalidadDoc())) {
            sol = "INCLUSIÓN";
        } else {
            sol = "EXCLUSIÓN";
        }

        if ("P".equals(decision)) {
            estado = ". Usted recibirá una nueva notificación si la inclusión ha sido aprobado o no aprobada";
        }

        List<Aval> lista = servicioGeneral.obtenerAval(aval.getAviId().toString());
        Aval solicitudTramitada = lista.get(0);
        if (!"P".equals(decision)) {
            solicitudTramitada.setAviEstado("F");
            solicitudTramitada.setAviAvalfacultad(decision);
        } else {
            solicitudTramitada.setAviEstado("P");
        }
        solicitudTramitada.setDescripcionfacultad(observacionPermiso.trim());
        solicitudTramitada.setAviFechaAvalFac(new Date());
        servicioGeneral.guardarObjeto(solicitudTramitada);
        crearHistoricoEstadoAval(solicitudTramitada, cargarPersonaActual(), "BI");

        CorreoPlantilla correoActual = cargarPlantilla(261);
        String textoCorreo = correoActual.getCuerpo();
        textoCorreo = textoCorreo.replaceAll("<<INVESTIGADOR>>", personaSolicitante.getNombreCompletoMinusculas());
        textoCorreo = textoCorreo.replaceAll("<<TIPO>>", sol);
        textoCorreo = textoCorreo.replaceAll("<<ID>>", solicitudTramitada.getAviId().toString());
        textoCorreo = textoCorreo.replaceAll("<<ESTADO>>", estado);
        textoCorreo = textoCorreo.replaceAll("<<NO>>", "");
        if ("S".equals(decision)) {
            textoCorreo = textoCorreo.replaceAll("<<DECISION>>", "APROBADA");
        } else if ("P".equals(decision)) {
            textoCorreo = textoCorreo.replaceAll("<<DECISION>>", "REVISADA");
        } else {
        	textoCorreo = textoCorreo.replaceAll("<<NO>>", " NO");
            textoCorreo = textoCorreo.replaceAll("<<DECISION>>", "APROBADA");
        }

        if (!esCadenaVacia(observacionPermiso)) {
            textoCorreo = textoCorreo.replaceAll("<<OBSERVACIONES>>", observacionPermiso);
        } else {
            textoCorreo = textoCorreo.replaceAll("<<OBSERVACIONES>>", "");
        }

        Correo correo = new Correo();
        correo.setOrigen(Correo.CORREO_HERMES);
        correo.adicionarDireccion(personaSolicitante.getEmail());
        correo.adicionarDireccion(Correo.CORREO_HERMES);
        correo.setAsunto(correoActual.getAsunto().replaceAll("<<TIPO>>", sol));
        correo.setCuerpo(textoCorreo);
        servicioCorreo.enviarCorreo(correo);
        cargarListasPermisoMarco();
    }
    
    public void guardar(){
        if(!esCadenaVacia(decisionConcepto)){
            darConcepto(decisionConcepto);
        }
    }

    public void darConcepto(String decision) {
        CorreoPlantilla correoActual = new CorreoPlantilla();
        String textoCorreo = "";
        boolean envioCorreo = true;
        concepto.setEstado(decision);
        if (decision.equals(ConceptoContratoBiodiversidad.REQUIERE_SUSCRIPCION)) {
            List<Proyecto> lista = servicioGeneral.obtenerObjetosLimitado(Proyecto.class, "select #id p.id from Proyecto p where p.codigoDib = '"+concepto.getProyecto().getId().toString()+"' and "
                    + "p.modalidad.id = '"+concepto.getModalidad().getId().toString()+"' and p.estadoProyecto.id !='B'");
            if(esListaVacia(lista)){
            
                Proyecto proyectoContrato = new Proyecto();
                EstadoProyecto estado = new EstadoProyecto();
                estado.setId(EstadoProyecto.INGRESANDO);
                proyectoContrato.setEstadoProyecto(estado);
                proyectoContrato.cambiarEstadoPersona(EstadoProyecto.ELEGIBLE, cargarPersonaActual(),
                        "Concepto de necesidad de sucripción de contrato de acceso a recurso genético.");
                proyectoContrato.setNombre(concepto.getModalidad().getTitulo() + ": " + concepto.getProyecto().getNombre());
                proyectoContrato.setCodigoDib(concepto.getProyecto().getId().toString());
                proyectoContrato.setModalidad(concepto.getModalidad());
                proyectoContrato.setResumen(concepto.getProyecto().getResumen());
                proyectoContrato.setDuracion(concepto.getProyecto().getDuracion());
                servicioGeneral.guardarObjeto(proyectoContrato);
    
                InvestigadorProyecto investigadorPrincipal = new InvestigadorProyecto();
                TipoInvestigador tipoInvestigador = new TipoInvestigador();
                tipoInvestigador.setId(InvestigadorProyecto.PRINCIPAL);
                investigadorPrincipal.setInvestigador(concepto.getInvestigador());
                investigadorPrincipal.setTipo(tipoInvestigador);
                investigadorPrincipal.setProyecto(proyectoContrato);
                investigadorPrincipal.setDedicacionHorasSemana((short) 1);
                investigadorPrincipal.setFuncion("Director");
                servicioGeneral.guardarObjeto(investigadorPrincipal);
    
                /*
                 * Asignación de personas para atenciond de solicitudes y
                 * seguimiento
                 */
                Object[] informacionSeguimientoPM = null;
                
                if(concepto.getModalidad().equals(TIPO_CONTRATO_INDIVIDUAL)){
                    informacionSeguimientoPM = servicioBiodiversidad.obtenerDatosRevisionTramiteBiodiversidad(
                            TipoTramiteBiodiversidad.SEGUIMIENTO_CONTRATO_INDIVIDUAL, concepto.getInvestigador());
                }else{
                    informacionSeguimientoPM = servicioBiodiversidad.obtenerDatosRevisionTramiteBiodiversidad(
                            TipoTramiteBiodiversidad.SEGUIMIENTO_CONTRATO_MARCO, concepto.getInvestigador());
                }
                
                List<PersonaTramiteBiodiversidad> personasSeguimiento = (List<PersonaTramiteBiodiversidad>) informacionSeguimientoPM[1];
                PersonaTramiteBiodiversidad personaSeguimiento = personasSeguimiento.get(0);
                
                ProyectoCoordinador coordinadorContrato = new ProyectoCoordinador();
                coordinadorContrato.setPerId(personaSeguimiento.getPersonaEncargada().getId().getDocumento());
                coordinadorContrato.setTdoId(personaSeguimiento.getPersonaEncargada().getId().getTipoDocumento());
                coordinadorContrato.setTdoId3(servicioBiodiversidad.obtenerPersonaEncargadaSolicitudesEspecificasVicerrectoria()
                        .getId().getTipoDocumento());
                coordinadorContrato.setPerEvaluacion(servicioBiodiversidad.obtenerPersonaEncargadaSolicitudesEspecificasVicerrectoria()
                        .getId().getDocumento());
                coordinadorContrato.setIdProyecto(proyectoContrato.getId());
                coordinadorContrato.setProyecto(proyectoContrato);
                servicioGeneral.guardarObjeto(coordinadorContrato);
                concepto.setProyectoContrato(proyectoContrato);
    
                if (concepto.getModalidad().getId().equals(TIPO_CONTRATO_INDIVIDUAL)) {
                    correoActual = cargarPlantilla(269);
                } else {
                    correoActual = cargarPlantilla(270);
                }
    
                textoCorreo = correoActual.getCuerpo();
                textoCorreo = textoCorreo.replaceAll("<<COD_CONTRATO>>", proyectoContrato.getId().toString());
             
            }else{
                envioCorreo = false;
            }
        } else if (decision.equals(ConceptoContratoBiodiversidad.NO_REQUIERE_SUSCRIPCION)) {
            correoActual = cargarPlantilla(271);
            textoCorreo = correoActual.getCuerpo();
        } else if (decision.equals(ConceptoContratoBiodiversidad.RECHAZADO)){
            correoActual = cargarPlantilla(272);
            textoCorreo = correoActual.getCuerpo();
        }else {
            correoActual = cargarPlantilla(274);
            textoCorreo = correoActual.getCuerpo();
            textoCorreo = textoCorreo.replaceAll("<<ESTADO>>",
                    concepto.getNombreEstado());            
        }
        textoCorreo = textoCorreo.replaceAll("<<INVESTIGADOR>>",
                concepto.getInvestigador().getNombreCompletoMinusculas());
        textoCorreo = textoCorreo.replaceAll("<<ID>>",
                concepto.getId().toString());
        
        concepto.setPersonaRevisa(cargarPersonaActual());
        concepto.setFechaTramite(new Date());
        concepto.setObservaciones(observaciones);
        servicioGeneral.guardarObjeto(concepto);
        
        crearHistoricoEstadoConcepto(concepto, cargarPersonaActual());

        if (listaArchivosCoordinador != null) {
            Iterator<ArchivoConceptoContrato> itSet = listaArchivosCoordinador.iterator();
            while (itSet.hasNext()) {
                ArchivoConceptoContrato archivo = itSet.next();
                archivo.setConcepto(concepto);
                archivo.setEstado("C");
                this.servicioGeneral.guardarObjeto(archivo);
            }
        }

        if (observaciones != null && !"".equals(observaciones.trim())) {
            textoCorreo = textoCorreo.replaceAll("<<OBSERVACIONES>>", observaciones);
        } else {
            textoCorreo = textoCorreo.replaceAll("<<OBSERVACIONES>>", "Sin observaciones.");
        }

        /*
         * Envio de correo con la decisión que corresponda
         */
        if(envioCorreo){
            Correo correo = new Correo();
            correo.setOrigen(Correo.CORREO_HERMES);
            correo.adicionarDireccion(concepto.getInvestigador().getEmail());
            correo.adicionarDireccion(Correo.CORREO_HERMES);
            correo.adicionarDireccion(cargarPersonaActual().getEmail());
            correo.setAsunto(correoActual.getAsunto());
            correo.setCuerpo(textoCorreo);
            servicioCorreo.enviarCorreo(correo);
        }
        cargarListasAccesoRecursoGenetico();
        observaciones = "";
    }

    public String generarReporte() {
        ReporteBirt r = new ReporteBirt();

        switch (reporte) {
        case 0:
            r.setNombreReporte("/biodiversidad/investigadoresPermisoMarco");
            break;
        case 1:
            r.setNombreReporte("/biodiversidad/gruposPermisoMarco");
            break;
        case 2:
            r.setNombreReporte("/biodiversidad/certificadosPermisoMarco");
            break;
        case 3:
            r.setNombreReporte("/biodiversidad/proyectosPermisoMarco");
            break;
        case 4:
            r.setNombreReporte("/biodiversidad/censoContratoAcceso");
            break;
        case 5:
            r.setNombreReporte("/biodiversidad/conceptosContratoAcceso");
            break;
        case 6:
            r.setNombreReporte("/biodiversidad/proyectosContratoAcceso");
            break;
        case 7:
            r.setNombreReporte("/biodiversidad/solicitudesBiodiversidad");
            break;
        default:
            break;
        }

        r.setFormato(ReporteBirt.FORMATO_XLS);
        sesion.setAttribute("reporte", r);
        FacesContext context = FacesContext.getCurrentInstance();
        r.run(context);
        return "";
    }

    // Reporte proyecto
    public void reporteProyecto() {
        servicioProyecto.imprimirReporteProyecto(concepto.getProyecto(), sesion, false);
    }

    public List<Aval> getListaSolicitudesRecoleccionEspecimenes() {
        return listaSolicitudesRecoleccionEspecimenes;
    }

    public List<ConceptoContratoBiodiversidad> getListaSolicitudesAccesoRecurso() {
        return listaSolicitudesAccesoRecurso;
    }

    public List<Aval> getListaSolicitudesRecoleccionEspecimenesFiltered() {
        return listaSolicitudesRecoleccionEspecimenesFiltered;
    }

    public void setListaSolicitudesRecoleccionEspecimenesFiltered(
            List<Aval> listaSolicitudesRecoleccionEspecimenesFiltered) {
        this.listaSolicitudesRecoleccionEspecimenesFiltered = listaSolicitudesRecoleccionEspecimenesFiltered;
    }

    public List<ConceptoContratoBiodiversidad> getListaSolicitudesAccesoRecursoFiltered() {
        return listaSolicitudesAccesoRecursoFiltered;
    }

    public void setListaSolicitudesAccesoRecursoFiltered(
            List<ConceptoContratoBiodiversidad> listaSolicitudesAccesoRecursoFiltered) {
        this.listaSolicitudesAccesoRecursoFiltered = listaSolicitudesAccesoRecursoFiltered;
    }

    public SelectItem[] getTienePermisoItem() {
        return tienePermisoItem;
    }

    public void setTienePermisoItem(SelectItem[] tienePermisoItem) {
        this.tienePermisoItem = tienePermisoItem;
    }

    public SelectItem[] getTipoSolicitudItem() {
        return tipoSolicitudItem;
    }

    public void setTipoSolicitudItem(SelectItem[] tipoSolicitudItem) {
        this.tipoSolicitudItem = tipoSolicitudItem;
    }

    public Long getIdAval() {
        return idAval;
    }

    public void setIdAval(Long idAval) {
        this.idAval = idAval;
    }

    public Aval getAval() {
        return aval;
    }

    public void setAval(Aval aval) {
        this.aval = aval;
    }

    public List<CoinvestigadorAval> getListaIntegrantesSolicitud() {
        return listaIntegrantesSolicitud;
    }

    public void setListaIntegrantesSolicitud(List<CoinvestigadorAval> listaIntegrantesSolicitud) {
        this.listaIntegrantesSolicitud = listaIntegrantesSolicitud;
    }

    public List<HistoricoEstadoGrupo> getHistoricosEstadoGrupo() {
        return historicosEstadoGrupo;
    }

    public void setHistoricosEstadoGrupo(List<HistoricoEstadoGrupo> historicosEstadoGrupo) {
        this.historicosEstadoGrupo = historicosEstadoGrupo;
    }

    public InvestigadorInterno getPersonaSolicitante() {
        return personaSolicitante;
    }

    public void setPersonaSolicitante(InvestigadorInterno personaSolicitante) {
        this.personaSolicitante = personaSolicitante;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<InvestigadorInterno> getListaInvestigadoresPermiso() {
        return listaInvestigadoresPermiso;
    }

    public void setListaInvestigadoresPermiso(List<InvestigadorInterno> listaInvestigadoresPermiso) {
        this.listaInvestigadoresPermiso = listaInvestigadoresPermiso;
    }

    public InvestigadorInterno getInvestigadorSeleccionado() {
        return investigadorSeleccionado;
    }

    public void setInvestigadorSeleccionado(InvestigadorInterno investigadorSeleccionado) {
        this.investigadorSeleccionado = investigadorSeleccionado;
    }

    public List<InvestigadorInterno> getListaInvestigadoresIncluidosFiltered() {
        return listaInvestigadoresIncluidosFiltered;
    }

    public void setListaInvestigadoresIncluidosFiltered(
            List<InvestigadorInterno> listaInvestigadoresIncluidosFiltered) {
        this.listaInvestigadoresIncluidosFiltered = listaInvestigadoresIncluidosFiltered;
    }

    public int getTotalInvestigadores() {
        return totalInvestigadores;
    }

    public void setTotalInvestigadores(int totalInvestigadores) {
        this.totalInvestigadores = totalInvestigadores;
    }

    public SelectItem[] getTipoContratoItem() {
        return tipoContratoItem;
    }

    public void setTipoContratoItem(SelectItem[] tipoContratoItem) {
        this.tipoContratoItem = tipoContratoItem;
    }

    public List<ConceptoContratoBiodiversidad> getListaConceptosTramitados() {
        return listaConceptosTramitados;
    }

    public void setListaConceptosTramitados(List<ConceptoContratoBiodiversidad> listaConceptosTramitados) {
        this.listaConceptosTramitados = listaConceptosTramitados;
    }

    public List<ConceptoContratoBiodiversidad> getListaConceptosTramitadosFiltered() {
        return listaConceptosTramitadosFiltered;
    }

    public void setListaConceptosTramitadosFiltered(
            List<ConceptoContratoBiodiversidad> listaConceptosTramitadosFiltered) {
        this.listaConceptosTramitadosFiltered = listaConceptosTramitadosFiltered;
    }

    public String getObservacionPermiso() {
        return observacionPermiso;
    }

    public void setObservacionPermiso(String observacionPermiso) {
        this.observacionPermiso = observacionPermiso;
    }

    public List<Object[]> getReportesHabilitados() {
        return reportesHabilitados;
    }

    public void setReportesHabilitados(List<Object[]> reportesHabilitados) {
        this.reportesHabilitados = reportesHabilitados;
    }

    public int getReporte() {
        return reporte;
    }

    public void setReporte(int reporte) {
        this.reporte = reporte;
    }
}