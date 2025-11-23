package co.edu.unal.hermes.vista;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;

import co.edu.unal.hermes.modelo.ActividadRecursoGenetico;
import co.edu.unal.hermes.modelo.ArchivoConceptoContrato;
import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.modelo.ConceptoContratoBiodiversidad;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.PermisoBiodiversidad;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PersonaTramiteBiodiversidad;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.TipoTramiteBiodiversidad;
import co.edu.unal.hermes.modelo.correo.Correo;

public class ManejadorConvenioMarco extends ManejadorBasePermisosMarco {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Grupo grupoEscogido;
    private Aval aval;
    private String cuerpoCorreo = "";
    private CorreoPlantilla correoActual = new CorreoPlantilla();
    private List<SelectItem> gruposItem;
    private SelectItem[] tiposPermiso;
    private SelectItem[] tiposContrato;
    private List<Grupo> listaGrupos;
    private List<Aval> listaSolicitudesRecoleccionEspecimenes;
    private List<ConceptoContratoBiodiversidad> listaSolicitudesAccesoRecurso;
    private List<ConceptoContratoBiodiversidad> listaSolicitudesConceptoPendientes;
    private String idTipoPermiso;
    public static final String TIPO_RECOLECCION_ESPECIMENES = "RE";
    public static final String TIPO_ACCESO_RECURSO = "AR";
    private boolean aceptaResolucion;
    private String actividadesRecolecta;
    private boolean solicitudPendiente;
    private String tipoSolicitud;
    private ConceptoContratoBiodiversidad proyectoConcepto;
    private List<SelectItem> listaProyectosItem;
    List<ActividadRecursoGenetico> listaActividades;
    private List<SelectItem> listaActividadesItem;
    private String[] selectedActividades;
    private Long idSolicitudConcepto;

    private List<SelectItem> listaEntidadesExternas;
    private String entidadExterna;
    private FuenteFinanciacion entidadSeleccionada;

    private TipoTramiteBiodiversidad tramite;
    private PermisoBiodiversidad permisoVigente;
    private boolean grupoIncluidoPermiso;
    private boolean sinPermisoVigente;

    public ManejadorConvenioMarco() {
    	grupoIncluidoPermiso = false;
        listaProyectosItem = new ArrayList<SelectItem>();
        listaActividadesItem = new ArrayList<SelectItem>();
        listaActividades = new ArrayList<ActividadRecursoGenetico>();
        listaSolicitudesConceptoPendientes = new ArrayList<ConceptoContratoBiodiversidad>();
        aceptaResolucion = false;
        solicitudPendiente = false;
        proyectoConcepto = new ConceptoContratoBiodiversidad();
        Proyecto proyecto = new Proyecto();
        Convocatoria modalidad = new Convocatoria();
        proyectoConcepto.setProyecto(proyecto);
        proyectoConcepto.setModalidad(modalidad);
        grupoEscogido = new Grupo();
        cargarListaTiposPermiso();
        listaSolicitudesRecoleccionEspecimenes = cargarPermisosSolicitados(Aval.TIPO_PERMISO_RECOLECCION);
        listaSolicitudesAccesoRecurso = cargarPermisosSolicitados(Aval.TIPO_ACCESO_RECURSO);

    }

    public void cargarOpciones() {
        if (idTipoPermiso.equals(TIPO_RECOLECCION_ESPECIMENES)) {
            cargarListaGrupos();
            cargarUltimaSolicitud();
            cargarPermisoMarcoVigente();
        } else {
            cargarListaConceptosPendientes();
            cargarListaTiposContrato();
            cargarActividadesBiodiversidad();
            cargarEntidades();
        }
    }

    public void cargarUltimaSolicitud() {
        if (!esListaVacia(listaSolicitudesRecoleccionEspecimenes)) {
            Aval solicitud = listaSolicitudesRecoleccionEspecimenes.get(0);
            if (solicitud != null && ("I".equals(solicitud.getAviEstado()) || "P".equals(solicitud.getAviEstado()))) {
                solicitudPendiente = true;
                aceptaResolucion = true;
            }
            if (solicitud != null && solicitud.getGrupoCandidato() != null) {
                grupoEscogido.setId(Long.parseLong(solicitud.getGrupoCandidato()));
                actividadesRecolecta = solicitud.getResumen();
                if ("I".equals(solicitud.getModalidadDoc())) {
                    tipoSolicitud = "INCLUSIÓN";
                } else {
                    tipoSolicitud = "EXCLUSIÓN";
                }
            }
        }
    }

    public List cargarPermisosSolicitados(String tipo) {
        if (tipo.equals(Aval.TIPO_PERMISO_RECOLECCION)) {
            return servicioGeneral.obtenerObjetosLimitado(Aval.class,
                    "select #aviId a.aviId, #fecha a.fecha, #grupoCandidato a.grupoCandidato, #resumen a.resumen, "
                            + "#nombreGrupoPINV g.nombre, #modalidadDoc a.modalidadDoc, #aviEstado a.aviEstado "
                            + "from Aval a, Grupo g where a.tipo = '" + tipo + "'" + " and a.documento = '"
                            + personaActual.getId().getDocumento() + "' " + "and a.tipoDocumento = '"
                            + personaActual.getId().getTipoDocumento() + "'"
                            + " and g.id = a.grupoCandidato order by a.aviId desc");
        } else {
            String dpnsql = "select c from ConceptoContratoBiodiversidad c where c.investigador.id.tipoDocumento='"
                    + personaActual.getId().getTipoDocumento() + "' " + "and  c.investigador.id.documento='"
                    + personaActual.getId().getDocumento() + "' and c.estado not in ('B','I') order by c.id";
            return servicioGeneral.obtenerObjetos(ConceptoContratoBiodiversidad.class, dpnsql);
        }
    }
    
    private void cargarPermisoMarcoVigente() {
        permisoVigente = new PermisoBiodiversidad();
        sinPermisoVigente = false;
        List<PermisoBiodiversidad> listaPermisoMarco = servicioGeneral.obtenerObjetos(PermisoBiodiversidad.class,
                "from PermisoBiodiversidad c where c.tipoPermiso = '"+PermisoBiodiversidad.TIPO_PERMISO_MARCO+"' and c.estado='" + PermisoBiodiversidad.ACTIVO + "'");
        if (!esListaVacia(listaPermisoMarco)) {
        	permisoVigente = (PermisoBiodiversidad) listaPermisoMarco.get(0);
        }else {
        	sinPermisoVigente = true;
        }
    }
    
    public void buscarGrupoIncluido() {
    	grupoIncluidoPermiso = false;
    	List<Aval>listaGrupos = servicioGeneral.obtenerObjetosLimitado(Aval.class,
                "select #id a.id from Aval a where "
                        + " a.grupoCandidato = '"
                         + grupoEscogido.getId()
                        + "' and a.tipo = 'BI' and a.aviEstado = 'F' and a.aviAvalfacultad = 'S' "
                        + "and a.aviConvocatoria = '"+permisoVigente.getId().toString()+"' and a.modalidadDoc = 'I'");
    	
    	 if (!esListaVacia(listaGrupos)) {
    		 grupoIncluidoPermiso = true;
         }
    }

    private void cargarListaGrupos() {
        gruposItem = new ArrayList<SelectItem>();
        gruposItem.clear();
        listaGrupos = servicioGeneral.obtenerObjetosLimitado(Grupo.class,
                "select #id g.id, #nombre g.nombre from Grupo g, " + "InvestigadorGrupo ig where "
                        + " ig.investigador.id.tipoDocumento = '" + investigadorActual.getId().getTipoDocumento()
                        + "' and ig.investigador.id.documento = '" + investigadorActual.getId().getDocumento()
                        + "' and (g.estadoGrupoColciencias.id in ('CT','RC')) " + "and g.id = ig.grupo.id");

        if (!esListaVacia(listaGrupos)) {
            for (int i = 0; i < listaGrupos.size(); i++) {
                Grupo grupo = (Grupo) listaGrupos.get(i);
                gruposItem.add(new SelectItem(grupo.getId(), grupo.getNombre()));
            }
        }
    }

    private void cargarListaConceptosPendientes() {
        listaSolicitudesConceptoPendientes.clear();
        listaSolicitudesConceptoPendientes = servicioGeneral.obtenerObjetosLimitado(ConceptoContratoBiodiversidad.class,
                "select #id c.id, #proyecto p, #estado c.estado from ConceptoContratoBiodiversidad c, Proyecto p where "
                        + " c.investigador.id.tipoDocumento = '" + investigadorActual.getId().getTipoDocumento()
                        + "' and c.investigador.id.documento = '" + investigadorActual.getId().getDocumento()
                        + "' and c.estado in ('I','F','C') and c.proyecto.id = p.id order by c.id desc");
    }

    public void cargarEntidades() {
        listaEntidadesExternas = new ArrayList<SelectItem>();
        List<FuenteFinanciacion> listaEntidades = cargarEntidadesExternas(CONSULTA_ENTIDADES_EXTERNAS_SIN_UNAL);

        if (!esListaVacia(listaEntidades)) {
            for (int i = 0; i < listaEntidades.size(); i++) {
                FuenteFinanciacion entidad = (FuenteFinanciacion) listaEntidades.get(i);
                listaEntidadesExternas.add(new SelectItem(entidad.getId(), entidad.getDescripcion()));
            }
        }
    }

    /**
     * Agregar entidad
     */
    public void agregarEntidad() {

        if (entidadExterna == null || "".equals(entidadExterna)) {
            mensajeError("Debe seleccionar una entidad para agregarla.");
            return;
        }

        boolean entidadYaesta = false;

        for (int i = 0; i < proyectoConcepto.getListaEntidades().size(); i++) {
            FuenteFinanciacion entidadAgregada = (FuenteFinanciacion) proyectoConcepto.getListaEntidades().get(i);
            if (entidadAgregada.getId().equals(entidadExterna)) {
                entidadYaesta = true;
                break;
            }
        }

        if (entidadYaesta) {
            mensajeError("El entidad ya ha sido agregada.");
            return;
        } else {
            List<FuenteFinanciacion> list = servicioGeneral.obtenerObjetoXID(FuenteFinanciacion.class, entidadExterna);
            proyectoConcepto.adicionarEntidad(list.get(0));
        }
    }

    /**
     * Eliminar entidad
     */
    public void eliminarEntidad() {
        proyectoConcepto.borrarEntidad(entidadSeleccionada);
    }

    private void cargarListaTiposPermiso() {
        tiposPermiso = new SelectItem[3];
        tiposPermiso[0] = new SelectItem("", "Seleccione");
        tiposPermiso[1] = new SelectItem(TIPO_ACCESO_RECURSO, "Contrato de acceso a recurso genético");
        tiposPermiso[2] = new SelectItem(TIPO_RECOLECCION_ESPECIMENES, "Permiso marco de recolección de especímenes");
    }

    private void cargarListaTiposContrato() {
        tiposContrato = new SelectItem[2]; 
        tiposContrato[0] = new SelectItem(TIPO_CONTRATO_INDIVIDUAL, "Contrato individual de acceso a recurso genético");
        tiposContrato[1] = new SelectItem(TIPO_CONTRATO_MARCO, "Contrato marco de acceso a recurso genético");
    }

    public void cargarProyectosPosibles() {
        listaProyectosItem.clear();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); // Configuramos la fecha
        calendar.add(Calendar.MONTH, -3); // numero de meses a añadir o restar
        Date fechaLimiteActivacion = calendar.getTime();

        SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy");
        dt1.format(fechaLimiteActivacion);

        String restriccionActivos = "";

        List<Proyecto> listaP = servicioGeneral.obtenerObjetosLimitado(Proyecto.class,
                "select distinct #id p.id, #nombre p.nombre from Proyecto p, InvestigadorProyecto ip where "
                        + "p.id not in (select c.proyecto.id from ConceptoContratoBiodiversidad c where c.estado <> 'R') and ip.investigador.id.tipoDocumento like '%"
                        + investigadorActual.getId().getTipoDocumento() + "%' and ip.investigador.id.documento like '%"
                        + investigadorActual.getId().getDocumento()
                        + "%' and ip.proyecto.id=p.id and ("
                        + "(p.estadoProyecto.id = 'P' and p.modalidad.id in ('2','10') and p.id in (select #idProyecto av.idProyecto from Aval av where av.aviEstado in ('"+Aval.REVISADO_DIRECCION+"') and av.aviAvaldireccion = '"+Aval.APROBADO+"')) "
                        + "or p.estadoProyecto.id in ('"+EstadoProyecto.APROBADO+"','"+EstadoProyecto.APROBADO_OCAD+"') or (p.estadoProyecto.id = '"+EstadoProyecto.ACTIVO+"' " + restriccionActivos
                        + ")) and p.modalidad.id not in ("+ MODALIDADES_PERMISO_CONTRATO + ") order by p.id desc");

        if (!esListaVacia(listaP)) {
            for (int i = 0; i < listaP.size(); i++) {
                Proyecto fichaProyecto = (Proyecto) listaP.get(i);
                listaProyectosItem.add(new SelectItem(fichaProyecto.getId(),
                        fichaProyecto.getId() + " - " + fichaProyecto.getNombre()));
            }
        }
    }

    public void cargarActividadesBiodiversidad() {
        listaActividadesItem.clear();
        listaActividades = servicioGeneral.obtenerObjetosLimitado(ActividadRecursoGenetico.class,
                "select distinct #id ar.id, #descripcion ar.descripcion from ActividadRecursoGenetico ar "
                        + "where ar.estado = 'A' order by ar.descripcion asc");

        if (!esListaVacia(listaActividades)) {
            for (int i = 0; i < listaActividades.size(); i++) {
                ActividadRecursoGenetico actividad = (ActividadRecursoGenetico) listaActividades.get(i);
                listaActividadesItem.add(new SelectItem(actividad.getId().toString(), actividad.getDescripcion()));
            }
            selectedActividades = new String[listaActividades.size()];
        }
    }

    public void guardarSolicitud(String id, String tipo) {
        if ("I".equals(id) && (grupoEscogido.getId() == null || grupoEscogido.getId() == 0
                || "".equals(grupoEscogido.getId().toString().trim()))) {
            mensajeError(
                    "No se ha seleccionado el grupo de investigación, debe seleccionarlo de la lista, tenga en cuenta que sólo aparecen los grupos categorizados por Colciencias. ");
            return;
        }

        if ("I".equals(id) && (actividadesRecolecta == null || "".equals(actividadesRecolecta.trim())) && !grupoIncluidoPermiso) {
            mensajeError("Debe ingresar la descripción de las actividades de recolección que realiza el grupo. ");
            return;
        }

        aval = new Aval();
        aval.setFechaInicio(new Date());
        aval.setFechaFin(new Date());
        aval.setModalidadDoc(id); // Se guarda si es exclusión o inclusion
        aval.setDocumento(investigadorActual.getId().getDocumento());
        aval.setTipoDocumento(investigadorActual.getId().getTipoDocumento());
        aval.setGrupoCandidato(grupoEscogido.getId().toString());
        aval.setAviEstado(Aval.ENVIADO);
        aval.setAviConvocatoria(permisoVigente.getId().toString());

        if ("I".equals(id)) {
            aval.setResumen(actividadesRecolecta.trim());
        }
        aval.setFecha(new Date());
        aval.setTipo(tipo);
        aval.setDependencia(investigadorActual.getDependencia().getFacultad());
        
        this.servicioGeneral.guardarObjeto(this.aval);
        crearHistoricoEstadoAval(aval, personaActual, "D");

        Grupo grupoGuardar = null;
        for (int i = 0; i < listaGrupos.size(); i++) {
            Grupo grupo = listaGrupos.get(i);
            if (grupo.getId().equals(grupoEscogido.getId())) {
                grupo.setCategoria(grupoEscogido.getCategoria());
                grupoGuardar = grupo;
                break;
            }
        }

        if (aval.getAviId() != null) {
            /* Informacion de tramite */
            Object[] informacionRevision = servicioBiodiversidad.obtenerDatosRevisionTramiteBiodiversidad(
                    TipoTramiteBiodiversidad.INCLUSION_INVESTIGADOR_PM, investigadorActual);
            
            if (enviarCorreo(aval.getAviId().toString(), grupoGuardar, informacionRevision[1])) {
                mensajeInfo("La solicitud ha sido guardada con el codigo " + aval.getAviId() + "."
                        + " Un correo ha sido enviado con la confirmación del registro");
            } else {
                mensajeError("La solicitud ha sido guardada con el codigo " + aval.getAviId() + ". "
                        + "Ha ocurrido un error durante el envío del correo de confirmación.");
            }
        } else {
            mensajeError("Ha ocurrido un error al guardar la solicitud, intente nuevamente.");
        }
    }

    private boolean enviarCorreo(String id, Grupo grupo, Object tramitantes) {
        Boolean envio;
        correoActual = cargarPlantilla(34);
        editarCorreoInvestigador(personaActual, id, grupo);
        Correo correo = new Correo();
        correo.setOrigen(Correo.CORREO_HERMES);
        String dirCorreo = personaActual.getEmail();
        List<PersonaTramiteBiodiversidad> encargados = (List<PersonaTramiteBiodiversidad>) tramitantes;
        if (!esListaVacia(encargados)) {
            Iterator<PersonaTramiteBiodiversidad> i = encargados.iterator();
            while (i.hasNext()) {
                correo.adicionarDireccion(i.next().getPersonaEncargada().getEmail());
            }
        }
        correo.adicionarDireccion(dirCorreo);
        //correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
        correo.setAsunto(correoActual.getAsunto());
        correo.setCuerpo(cuerpoCorreo);
        envio = servicioCorreo.enviarCorreo(correo);
        return envio;
    }

    public CorreoPlantilla cargarPlantilla(int cod) {

        CorreoPlantilla correoActualAux = new CorreoPlantilla();
        List<CorreoPlantilla> lista = servicioGeneral.obtenerObjetos(CorreoPlantilla.class,
                "from CorreoPlantilla c where c.id='" + cod + "'");
        if (!esListaVacia(lista)) {
            correoActualAux = (CorreoPlantilla) lista.get(0);
        }
        return correoActualAux;
    }

    public void editarCorreoInvestigador(Persona personaAux, String id, Grupo grupo) {
    	// Ajuste solicitado mediante el correo "sisii_nal - 4 de noviembre de 2017, 13:21" - Angela Devia
        try {
        	String correo = correoActual.getCuerpo();
            correo = correo.replaceAll("<<INVESTIGADOR>>", personaAux.getNombreCompleto());
            correo = correo.replaceAll("<<IDSOLICITUD>>", id);
            correo = correo.replaceAll("<<ID_INVESTIGADOR>>",
                    personaActual.getId().getTipoDocumento() + " - " + personaActual.getId().getDocumento());
            correo = correo.replaceAll("<<NOMBRE_GRUPO>>", grupo.getNombre());
            cuerpoCorreo = correo;        	
        } catch (Exception e) {
			System.out.println(e.toString());
		}
     // Fin Ajuste solicitado mediante el correo "sisii_nal - 4 de noviembre de 2017, 13:21" - Angela Devia
    }

    public void guardarSolicitudInclusionRecoleccion() {
        guardarSolicitud("I", "BI");
    }

    public void guardarSolicitudExclusionRecoleccion() {
        guardarSolicitud("E", "BI");
    }

    public String nuevaSolicitudAcceso() {
        proyectoConcepto = new ConceptoContratoBiodiversidad();
        Proyecto proyecto = new Proyecto();
        Convocatoria modalidad = new Convocatoria();
        proyectoConcepto.setProyecto(proyecto);
        proyectoConcepto.setModalidad(modalidad);
        selectedActividades = new String[listaActividades.size()];
        listaArchivos.clear();
        listaProyectosItem.clear();
        return "";
    }

    public String editarSolicitudConcepto() {
        listaProyectosItem.clear();
        String dpnsql = "select c from ConceptoContratoBiodiversidad c where c.id='" + idSolicitudConcepto + "'";
        List<ConceptoContratoBiodiversidad> concepto = servicioGeneral
                .obtenerObjetos(ConceptoContratoBiodiversidad.class, dpnsql);
        if (!esListaVacia(concepto)) {
            proyectoConcepto = concepto.get(0);
            listaProyectosItem.add(
                    new SelectItem(proyectoConcepto.getProyecto().getId(), proyectoConcepto.getProyecto().getNombre()));
            if (!esListaVacia(proyectoConcepto.getListaActividades())) {
                selectedActividades = new String[listaActividades.size()];
                for (int i = 0; i < proyectoConcepto.getListaActividades().size(); i++) {
                    ActividadRecursoGenetico act = (ActividadRecursoGenetico) proyectoConcepto.getListaActividades()
                            .get(i);
                    selectedActividades[i] = act.getId().toString();
                }
            }
            consultarArchivosConcepto(proyectoConcepto.getId().toString());
        }
        return "";
    }

    public void guardarSolicitudConceptoAccesoParcialmente() {
        guardarSolicitudAcceso(false);
    }

    public void guardarSolicitudConceptoAcceso() {
        guardarSolicitudAcceso(true);
    }

    public void guardarSolicitudAcceso(boolean enviar) {
        boolean valida = true;
        Object[] informacionRevision = null;
        if (proyectoConcepto.getModalidad().getId() == null || proyectoConcepto.getModalidad().getId() == 0
                || "".equals(proyectoConcepto.getModalidad().getId().toString())) {
            mensajeError("Debe indicar la modalidad de suscripción de contrato.");
            valida = false;
        }
        if (proyectoConcepto.getProyecto().getId() == null || proyectoConcepto.getProyecto().getId() == 0
                || "".equals(proyectoConcepto.getProyecto().getId().toString())) {
            mensajeError("Debe seleccionar el proyecto con el cual se espera suscribir el contrato.");
            valida = false;
        }

        if (!valida) {
            return;
        } else {
            if (enviar) {
                if (selectedActividades == null || selectedActividades.length == 0) {
                    mensajeError(
                            "Debe seleccionar al menos una actividad de las que configuran acceso a recurso genético.");
                    valida = false;
                }
                if (proyectoConcepto.getDescripcionActividad() == null
                        || "".equals(proyectoConcepto.getDescripcionActividad().trim())) {
                    mensajeError("Debe describir en forma breve la(s) actividad(es) que va a realizar.");
                    valida = false;
                }
                if (!valida) {
                    return;
                } else {
                    proyectoConcepto.setEstado("E");
                }
                

            } else {
                proyectoConcepto.setEstado("F");
            }

            if (proyectoConcepto.getListaActividades() != null && proyectoConcepto.getListaActividades().size() > 0) {
                proyectoConcepto.getActividades().clear();
            }

            if (selectedActividades != null && selectedActividades.length > 0) {
                for (String valor : selectedActividades) {
                    ActividadRecursoGenetico actividad = new ActividadRecursoGenetico();
                    actividad.setId(Long.parseLong(valor));
                    proyectoConcepto.adicionarActividad(actividad);
                }
            }
            proyectoConcepto
                    .setDescripcionActividad(controlTamanoCadena(proyectoConcepto.getDescripcionActividad(), 2000));
            proyectoConcepto.setInvestigador(investigadorActual);
            proyectoConcepto.setFechaSolicitud(new Date());
            proyectoConcepto.setTipoTramite(new TipoTramiteBiodiversidad(TipoTramiteBiodiversidad.CONCEPTO_NECESIDAD_CARG));
            proyectoConcepto.setDependencia(investigadorActual.getDependencia().getFacultad());
            servicioGeneral.guardarObjeto(proyectoConcepto);

            // GuardarArchivos
            if (!esListaVacia(listaArchivos)) {
                Iterator<ArchivoConceptoContrato> itSet = listaArchivos.iterator();
                while (itSet.hasNext()) {
                    ArchivoConceptoContrato archivo = itSet.next();
                    archivo.setConcepto(proyectoConcepto);
                    archivo.setEstado("V");
                    this.servicioGeneral.guardarObjeto(archivo);
                }
            }

            if (proyectoConcepto.getId() != null) {
                if (enviar) {
                    /* Informacion de tramite */
                    informacionRevision = servicioBiodiversidad.obtenerDatosRevisionTramiteBiodiversidad(
                            TipoTramiteBiodiversidad.CONCEPTO_NECESIDAD_CARG, investigadorActual);
                    correoActual = cargarPlantilla(268);
                    String cuerpoCorreo = correoActual.getCuerpo();
                    if (proyectoConcepto.getModalidad().getId() == TIPO_CONTRATO_INDIVIDUAL) {
                        cuerpoCorreo = cuerpoCorreo.replaceAll("<<TIPO>>", "INDIVIDUAL");
                    } else {
                        cuerpoCorreo = cuerpoCorreo.replaceAll("<<TIPO>>", "MARCO");
                    }
                    cuerpoCorreo = cuerpoCorreo.replaceAll("<<INVESTIGADOR>>", personaActual.getNombreCompleto());
                    cuerpoCorreo = cuerpoCorreo.replaceAll("<<ID>>", proyectoConcepto.getId().toString());
                    cuerpoCorreo = cuerpoCorreo.replaceAll("<<ID_PRY>>",
                            proyectoConcepto.getProyecto().getId().toString());
                    cuerpoCorreo = cuerpoCorreo.replaceAll("<<PROYECTO>>", proyectoConcepto.getProyecto().getNombre());

                    Correo correo = new Correo();
                    correo.setOrigen(Correo.CORREO_HERMES);
                    String dirCorreo = personaActual.getEmail();
                    // se obtiene persona encargada de revisión
                    List<PersonaTramiteBiodiversidad> encargados = (List<PersonaTramiteBiodiversidad>) informacionRevision[1];
                    if (!esListaVacia(encargados)) {
                        Iterator<PersonaTramiteBiodiversidad> i = encargados.iterator();
                        while (i.hasNext()) {
                            correo.adicionarDireccion(i.next().getPersonaEncargada().getEmail());
                        }
                    }
                    correo.adicionarDireccion(dirCorreo);
                    //correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
                    correo.setAsunto(correoActual.getAsunto());
                    correo.setCuerpo(cuerpoCorreo);
                    servicioCorreo.enviarCorreo(correo);
                    mensajeInfo(
                            "La solicitud de concepto ha sido enviada con el código " + proyectoConcepto.getId() + ".");
                } else {
                    mensajeInfo("La solicitud de concepto ha sido guardada con el código " + proyectoConcepto.getId()
                            + ".");
                }
            }

            cargarListaConceptosPendientes();
            crearHistoricoEstadoConcepto(proyectoConcepto, cargarPersonaActual());
        }
    }

    public void eliminarArchivo() {
        eliminarArchivoConcepto(ARCHIVO_DESDE_INVESTIGADOR);
    }

    public void adjuntarArchivo(FileUploadEvent event) {
        adjuntarArchivoConcepto(event, ARCHIVO_DESDE_INVESTIGADOR);
    }

    public List<SelectItem> getGruposItem() {
        return gruposItem;
    }

    public void setGrupoEscogido(Grupo grupoEscogido) {
        this.grupoEscogido = grupoEscogido;
    }

    public Grupo getGrupoEscogido() {
        return grupoEscogido;
    }

    public SelectItem[] getTiposPermiso() {
        return tiposPermiso;
    }

    public String getIdTipoPermiso() {
        return idTipoPermiso;
    }

    public void setIdTipoPermiso(String idTipoPermiso) {
        this.idTipoPermiso = idTipoPermiso;
    }

    public void setTiposPermiso(SelectItem[] tiposPermiso) {
        this.tiposPermiso = tiposPermiso;
    }

    public String getTipoRecoleccionEspecimenes() {
        return TIPO_RECOLECCION_ESPECIMENES;
    }

    public String getTipoAccesoRecurso() {
        return TIPO_ACCESO_RECURSO;
    }

    public Long getTipoContratoIndividual() {
        return TIPO_CONTRATO_INDIVIDUAL;
    }

    public Long getTipoContratoMarco() {
        return TIPO_CONTRATO_MARCO;
    }

    public List<Aval> getListaSolicitudesRecoleccionEspecimenes() {
        return listaSolicitudesRecoleccionEspecimenes;
    }

    public List<ConceptoContratoBiodiversidad> getListaSolicitudesAccesoRecurso() {
        return listaSolicitudesAccesoRecurso;
    }

    public boolean isAceptaResolucion() {
        if (investigadorActual.getTienePermisoMarco() != null
                && "S".equals(investigadorActual.getTienePermisoMarco())) {
            aceptaResolucion = true;
        }
        return aceptaResolucion;
    }

    public void setAceptaResolucion(boolean aceptaResolucion) {
        this.aceptaResolucion = aceptaResolucion;
    }

    public String getActividadesRecolecta() {
        return actividadesRecolecta;
    }

    public void setActividadesRecolecta(String actividadesRecolecta) {
        this.actividadesRecolecta = actividadesRecolecta;
    }

    public boolean isSolicitudPendiente() {
        return solicitudPendiente;
    }

    public void setSolicitudPendiente(boolean solicitudPendiente) {
        this.solicitudPendiente = solicitudPendiente;
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public SelectItem[] getTiposContrato() {
        return tiposContrato;
    }

    public void setTiposContrato(SelectItem[] tiposContrato) {
        this.tiposContrato = tiposContrato;
    }

    public ConceptoContratoBiodiversidad getProyectoConcepto() {
        return proyectoConcepto;
    }

    public void setProyectoConcepto(ConceptoContratoBiodiversidad proyectoConcepto) {
        this.proyectoConcepto = proyectoConcepto;
    }

    public List<SelectItem> getListaProyectosItem() {
        return listaProyectosItem;
    }

    public void setListaProyectosItem(List<SelectItem> listaProyectosItem) {
        this.listaProyectosItem = listaProyectosItem;
    }

    public String[] getSelectedActividades() {
        return selectedActividades;
    }

    public void setSelectedActividades(String[] selectedActividades) {
        this.selectedActividades = selectedActividades;
    }

    public List<SelectItem> getListaActividadesItem() {
        return listaActividadesItem;
    }

    public void setListaActividadesItem(List<SelectItem> listaActividadesItem) {
        this.listaActividadesItem = listaActividadesItem;
    }

    public List<ConceptoContratoBiodiversidad> getListaSolicitudesConceptoPendientes() {
        return listaSolicitudesConceptoPendientes;
    }

    public void setListaSolicitudesConceptoPendientes(
            List<ConceptoContratoBiodiversidad> listaSolicitudesConceptoPendientes) {
        this.listaSolicitudesConceptoPendientes = listaSolicitudesConceptoPendientes;
    }

    public Long getIdSolicitudConcepto() {
        return idSolicitudConcepto;
    }

    public void setIdSolicitudConcepto(Long idSolicitudConcepto) {
        this.idSolicitudConcepto = idSolicitudConcepto;
    }

    public List<SelectItem> getListaEntidadesExternas() {
        return listaEntidadesExternas;
    }

    public void setListaEntidadesExternas(List<SelectItem> listaEntidadesExternas) {
        this.listaEntidadesExternas = listaEntidadesExternas;
    }

    public String getEntidadExterna() {
        return entidadExterna;
    }

    public void setEntidadExterna(String entidadExterna) {
        this.entidadExterna = entidadExterna;
    }

    public FuenteFinanciacion getEntidadSeleccionada() {
        return entidadSeleccionada;
    }

    public void setEntidadSeleccionada(FuenteFinanciacion entidadSeleccionada) {
        this.entidadSeleccionada = entidadSeleccionada;
    }

    public TipoTramiteBiodiversidad getTramite() {
        return tramite;
    }

    public void setTramite(TipoTramiteBiodiversidad tramite) {
        this.tramite = tramite;
    }

	public PermisoBiodiversidad getPermisoVigente() {
		return permisoVigente;
	}

	public void setPermisoVigente(PermisoBiodiversidad permisoVigente) {
		this.permisoVigente = permisoVigente;
	}

	public boolean isGrupoIncluidoPermiso() {
		return grupoIncluidoPermiso;
	}

	public void setGrupoIncluidoPermiso(boolean grupoIncluidoPermiso) {
		this.grupoIncluidoPermiso = grupoIncluidoPermiso;
	}

	public boolean isSinPermisoVigente() {
		return sinPermisoVigente;
	}

	public void setSinPermisoVigente(boolean sinPermisoVigente) {
		this.sinPermisoVigente = sinPermisoVigente;
	}

}