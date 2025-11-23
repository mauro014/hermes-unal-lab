package co.edu.unal.hermes.vista.aval;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.ArchivoAval;
import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.modelo.ConvocatoriaExterna;
import co.edu.unal.hermes.modelo.Departamento;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.HistoricoEstadoAval;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorConsultarActualizarAvales extends ManejadorBase {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long codigoAval = Long.parseLong("-1");
    private String sCodigoAval = "";
    private Aval aval;
    private String entidad = "";
    private boolean[] panelRenderError;
    private List<ArchivoAval> listaArchivosAval;
    private List<Aval> listaAval;
    private boolean imprimirReporteAval = false;
    private UploadedFile archivoCargar;
    private ArchivoAval archivoSeleccionado;
    private String nombreProfesor = "";
    private boolean ver = false;
    ArchivoAval documentoSeleccionado;
    boolean avalRelacionado = false;
    private List<InvestigadorInterno> listaUab;
    private List<InvestigadorInterno> listaFacultad;
    private List<InvestigadorInterno> listaDireccion;
    private List<InvestigadorInterno> listaVicerrectoria;
    private List<InvestigadorInterno> listaDRE;
    List<HistoricoEstadoAval> listaHistoricoEstadoAval;
    private Dependencia dependencia;
    private ConvocatoriaExterna convocatoria;
    private HistoricoEstadoAval historicoAval;

    public ManejadorConsultarActualizarAvales() {
        reiniciarVariables();
        sesion.removeAttribute("manejadorSemillerosSolicitudVIF");
        sesion.removeAttribute("manejadorSemillerosSolicitudDI");
        sesion.removeAttribute("manejadorSemillerosConsultaVIF");
		sesion.removeAttribute("manejadorSemillerosConsultaDI");
    }

    public void buscarReporteSolicitudAval() {
        try {
            Long sCodigoAval2 = Long.parseLong(sCodigoAval.trim());
            List<Aval> listaAvales = servicioGeneral.obtenerListaObjetosWhere(Aval.class,
                    " WHERE a.id = '" + sCodigoAval2 + "'");

            if (!esListaVacia(listaAvales)) {
                codigoAval = (listaAvales.get(0)).getAviId();
                buscarSolicitudAval();
            } else {
                panelRenderError[10] = true;
            }
        } catch (NumberFormatException e) {
            panelRenderError[10] = true;
        }
    }

    public void buscarSolicitudAval() {
        if (codigoAval != -1 && codigoAval != 0) {

            imprimirReporteAval = true;
            panelRenderError[0] = false;
            panelRenderError[10] = false;

            listaArchivosAval = new ArrayList<ArchivoAval>();

            String consultaArchivos = "select a from ArchivoAval a where a.fechaBorrado is null and a.aval =" + codigoAval;
            List<ArchivoAval> listaA = (List<ArchivoAval>) servicioGeneral.obtenerObjetos(ArchivoAval.class,
                    consultaArchivos);

            if (!esListaVacia(listaA)) {
                listaArchivosAval.addAll(listaA);
            }

            listaAval = servicioGeneral.obtenerListaObjetosWhere(Aval.class, " where a.id = '" + codigoAval + "'");
            aval = listaAval.get(0);

            List<ArchivoAval> archi = servicioGeneral.obtenerListaObjetosWhere(ArchivoAval.class,
                    " where a.avalCoor='" + codigoAval + "'");
            if (archi != null) {
                for (int i = 0; i < archi.size(); i++) {
                    aval.getArchivosCoor().add(archi.get(i));
                }
            }

            if (aval.getAviId() <= 4855) {
                ver = false;
            } else {
                ver = true;
            }

            obtenerPersonasRevision(aval.getAviId().toString(), aval.getDependencia().getId());
            buscarHistoricoEstadoAval();

            if (!esCadenaVacia(aval.getAviEntidad())) {
                identificarEntidad();
            }
            
            consultarConvocatoria();

            IdPersona idPer = new IdPersona();

            if (aval.getDocumento() != null && aval.getTipoDocumento() != null) {
                idPer.setDocumento(aval.getDocumento());
                idPer.setTipoDocumento(aval.getTipoDocumento());
                Persona per = servicioPersona.obtenerPersona(idPer);
                nombreProfesor = per != null ? per.getNombreCompletoMinusculas() : "";
            }

        } else {
            imprimirReporteAval = false;
            panelRenderError[0] = true;
        }
    }

    public void consultarConvocatoria() {
        convocatoria = new ConvocatoriaExterna();
        if (!esCadenaVacia(aval.getAviConvocatoria())) {
            List<ConvocatoriaExterna> convocaExt = servicioGeneral.obtenerObjetoXID(ConvocatoriaExterna.class,
                    aval.getAviConvocatoria());
            if (!esListaVacia(convocaExt)) {
                setConvocatoria(convocaExt.get(0));
            } else {
                convocatoria.setNombre(aval.getAviConvocatoria());
            }
        }
    }

    /**
     * Se obtiene el listado de personas que puede revisar en aval en cada
     * dependencia a partir del id del aval y el identificador de la dependencia
     * 
     * @param id
     * @param dep
     */
    public void obtenerPersonasRevision(String id, String dep) {
        
        obtenerDependenciaTramite(dep);
        
        listaUab = new ArrayList<InvestigadorInterno>();
        listaFacultad = new ArrayList<InvestigadorInterno>();
        listaDireccion = new ArrayList<InvestigadorInterno>();
        listaVicerrectoria = new ArrayList<InvestigadorInterno>();
        listaDRE = new ArrayList<InvestigadorInterno>();

        if (aval.getTipo().equals(Aval.TIPO_JORNADA_DOCENTE)) {
            listaUab = obtenerRevisoresUab(id);
        }else{
            listaUab.add(new InvestigadorInterno());
        }

        if (!(aval.getTipo().equals(Aval.TIPO_INVESTIGACION_CONT) && "D".equals(aval.getDependenciaContrapartida()))) {
            listaFacultad = obtenerRevisoresFacultad(id);
        } else {
            listaFacultad.add(new InvestigadorInterno());
        }

        if (!(aval.getTipo().equals(Aval.TIPO_INVESTIGACION_CONT)
                && "F".equals(aval.getDependenciaContrapartida()))) {
            listaDireccion = obtenerRevisoresDireccion(id);
        } else {
            listaDireccion.add(new InvestigadorInterno());
        }

		if ((aval.isEsRegaliasVicerrectoria() || aval.isEsPaedRegaliasVicerrectoria() || aval.isEsGrupoInvestigacion()
				|| aval.isEsInvestigadorIndependiente())
				|| (aval.getEsAvalParaRevisionVice() != null && aval.getEsAvalParaRevisionVice().equals("S"))) {
			listaVicerrectoria = obtenerRevisoresVicerrectoria();
		} else {
			listaVicerrectoria.add(new InvestigadorInterno());
		}
        
		if (aval.getEsAvalParaRevisionDRE() != null && aval.getEsAvalParaRevisionDRE().equals("S")) {
        	 listaDRE = obtenerRevisoresDRE();
        } else {
        	listaDRE.add(new InvestigadorInterno());
        }
    }
    
    private void obtenerDependenciaTramite(String dep){
        List<Dependencia> dependencias = servicioGeneral.obtenerObjetos(Dependencia.class,
                "select d from Dependencia d where d.id = '" + dep + "'");
        dependencia = (Dependencia) dependencias.get(0);

        if (aval.getTipo().equals(Aval.TIPO_INVESTIGACION_CONT) && aval.getDependenciaContrapartida() == null) {
            aval.setDependenciaContrapartida("");
        }
    }

    private List<InvestigadorInterno> obtenerRevisoresUab(String id) {

        return servicioGeneral.obtenerObjetos(InvestigadorInterno.class,
                "select ii from Persona p, PersonaRol pr, Aval a, InvestigadorInterno ii "
                        + " where pr.nombre = 'DD' and a.dependencia.id = ii.dependencia2.id and p.id.documento = ii.id.documento and "
                        + "ii.id.tipoDocumento = p.id.tipoDocumento and p.id.documento = pr.documento and p.id.tipoDocumento = pr.tipoDocumento and a.aviId = '"
                        + id + "' " + "and p.id.documento not in (" + DOCUMENTOS_PRUEBAS_DESARROLLO + ") and (pr.fechaFinRol IS NULL OR pr.fechaFinRol >= CURRENT_DATE)");
    }
    
    private List<InvestigadorInterno> obtenerRevisoresFacultad(String id) {

        return servicioGeneral.obtenerObjetos(InvestigadorInterno.class,
                "select ii from Persona p, PersonaRol pr, Aval a, InvestigadorInterno ii, Dependencia d, Dependencia d2 "
                        + " where pr.nombre = 'AF' and a.dependencia.id = d.id and ii.dependencia2 = d2.id and d.facultad.id = d2.facultad.id and p.id.documento = ii.id.documento and "
                        + "ii.id.tipoDocumento = p.id.tipoDocumento and p.id.documento = pr.documento and p.id.tipoDocumento = pr.tipoDocumento and a.aviId = '"
                        + id + "' " + "and p.id.documento not in (" + DOCUMENTOS_PRUEBAS_DESARROLLO + ") and (pr.fechaFinRol IS NULL OR pr.fechaFinRol >= CURRENT_DATE)");
    }
    
    private List<InvestigadorInterno> obtenerRevisoresDireccion(String id) {

        return servicioGeneral.obtenerObjetos(InvestigadorInterno.class,
                "select ii from Persona p, PersonaRol pr, Aval a, InvestigadorInterno ii, Dependencia d, Dependencia d2 "
                        + " where pr.nombre = 'AD' and a.dependencia.id = d.id and ii.dependencia2 = d2.id and d.sede.id = d2.sede.id and p.id.documento = ii.id.documento and "
                        + "ii.id.tipoDocumento = p.id.tipoDocumento and p.id.documento = pr.documento and p.id.tipoDocumento = pr.tipoDocumento and a.aviId = '"
                        + id + "' " + "and p.id.documento not in (" + DOCUMENTOS_PRUEBAS_DESARROLLO + ") and (pr.fechaFinRol IS NULL OR pr.fechaFinRol >= CURRENT_DATE)");
    }
    
    private List<InvestigadorInterno> obtenerRevisoresVicerrectoria() {

        return servicioGeneral.obtenerObjetos(InvestigadorInterno.class,
                "select ii from Persona p, PersonaRol pr, InvestigadorInterno ii "
                        + " where pr.nombre = 'AV' and p.id.documento = pr.documento and p.id.tipoDocumento = pr.tipoDocumento and "
                        + "p.id.documento not in (" + DOCUMENTOS_PRUEBAS_DESARROLLO
                        + ") and p.id.documento = ii.id.documento and ii.id.tipoDocumento = p.id.tipoDocumento and (pr.fechaFinRol IS NULL OR pr.fechaFinRol >= CURRENT_DATE)");
    }
    
    private List<InvestigadorInterno> obtenerRevisoresDRE() {

        return servicioGeneral.obtenerObjetos(InvestigadorInterno.class,
                "select ii from Persona p, PersonaRol pr, InvestigadorInterno ii "
                        + " where pr.nombre = 'AI' and p.id.documento = pr.documento and p.id.tipoDocumento = pr.tipoDocumento and "
                        + "p.id.documento not in (" + DOCUMENTOS_PRUEBAS_DESARROLLO
                        + ") and p.id.documento = ii.id.documento and ii.id.tipoDocumento = p.id.tipoDocumento and (pr.fechaFinRol IS NULL OR pr.fechaFinRol >= CURRENT_DATE)");
    }

    public void buscarHistoricoEstadoAval() {
        listaHistoricoEstadoAval = servicioProyecto.obtenerHistoricosEstadoAval(aval);
    }

    public void imprimirProyecto() {

        Long id = aval.getIdProyecto();
        Proyecto proyectoActual = servicioProyecto.obtenerProyecto(id, ProyectoDAOHibernate.INFORMACION_GENERAL);
        if (proyectoActual != null) {
            servicioProyecto.imprimirReporteProyecto(proyectoActual, sesion, false);
        }
    }

    public void imprimirAval() {
        if (aval != null) {
            servicioAval.imprimirReporteAval(aval.getAviId(), sesion);
        }
    }

    public void imprimirGrupo() {
        String id = aval.getNombreGrupoPINV();
        imprimirReporteGrupo(id);
    }

    public void identificarEntidad() {
        List<FuenteFinanciacion> entidadConvocante = servicioGeneral.obtenerObjetoXID(FuenteFinanciacion.class,
                aval.getAviEntidad());
        if (esListaVacia(entidadConvocante)) {
            List<Departamento> entidadTerritorial = servicioGeneral.obtenerObjetoXID(Departamento.class,
                    aval.getAviEntidad());
            entidad = "Departamento de " + entidadTerritorial.get(0).getNombre();
        } else {
            entidad = entidadConvocante.get(0).getDescripcion();
        }
    }

    public void descargarArchivoAval() {
        descargarArchivoAvalGenerico(documentoSeleccionado.getId());
    }

    public void guardarArchivoAval(FileUploadEvent event) {
        archivoCargar = event.getFile();
        insertarArchivoAvalGenerico(codigoAval.longValue(), archivoCargar, false, "0");
        listaArchivosAval = servicioGeneral.obtenerListaObjetosWhere(ArchivoAval.class,
                " where a.aval = '" + codigoAval + "'");
    }

    public String eliminarArchivoAval() {

        Persona personaElimina = (Persona) sesion.getAttribute("persona");

        ArchivoAval id = archivoSeleccionado;
        listaArchivosAval.remove(id);
        List<ArchivoAval> archivosAvales = servicioGeneral.obtenerListaObjetosWhere(ArchivoAval.class,
                " where a.id = '" + id.getId() + "'");
        ArchivoAval archivo = archivosAvales.get(0);
        archivo.setAval(0L);
        archivo.setFechaBorrado(new Date());

        if (archivo.getDescripcion() != null) {
            archivo.setDescripcion(archivo.getDescripcion() + " - Eliminado por "
                    + personaElimina.getId().getDocumento() + "-" + personaElimina.getId().getTipoDocumento());
        } else {
            archivo.setDescripcion("Eliminado por " + personaElimina.getId().getDocumento() + "-"
                    + personaElimina.getId().getTipoDocumento());
        }
        if (aval.getAviId() != null) {
            archivo.setDescripcion(archivo.getDescripcion() + "- Aval previo = " + aval.getAviId());
        }
        servicioGeneral.guardarObjeto(archivo);
        return "";
    }

    public String imprimirReporteAval() {

        if (codigoAval != -1 && codigoAval != null) {
            imprimirReporteAval = true;
        } else {
            noExisteSolicitudAval();
        }
        return "";
    }

    public void noExisteSolicitudAval() {
        panelRenderError[0] = true;
    }

    public void imprimirReporteRelacionado() throws SQLException {
        List<Aval> listaAvalesRelacionados = servicioGeneral.obtenerObjetosLimitado(Aval.class,
                "select #aviId a.aviId, #tipo a.tipo from Aval a where " + "a.aviId = '" + aval.getAvalRelacionado()
                        + "' order by a.aviId asc");
        if (!esListaVacia(listaAvalesRelacionados)) {
            servicioAval.imprimirReporteAval(listaAvalesRelacionados.get(0).getAviId(), sesion);
        }
    }

    public void limpiar() {
        sesion.removeAttribute("ManejadorConsultarActualizarAvales");
    }

    private void reiniciarVariables() {
        codigoAval = Long.parseLong("-1");
        panelRenderError = new boolean[11];
        imprimirReporteAval = false;
    }

    void processChild(List<UIComponent> childList) {
        for (int i = 0; i < childList.size(); i++) {
            UIComponent component = childList.get(i);
            try {
                UIInput input = (UIInput) component;
                input.setSubmittedValue(null);
            } catch (Exception ex) {
                // TODO
            }
            List<UIComponent> childList2 = component.getChildren();
            processChild(childList2);
        }
    }

    public void cancelAction(ActionEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot viewRoot = facesContext.getViewRoot();
        List<UIComponent> childList = viewRoot.getChildren();
        processChild(childList);
    }

    public Long getCodigoAval() {
        return codigoAval;
    }

    public void setCodigoAval(Long codigoAval) {
        this.codigoAval = codigoAval;
    }

    public String getsCodigoAval() {
        return sCodigoAval;
    }

    public void setsCodigoAval(String sCodigoAval) {
        this.sCodigoAval = sCodigoAval;
    }

    public boolean[] getPanelRenderError() {
        return panelRenderError;
    }

    public void setPanelRenderError(boolean[] panelRenderError) {
        this.panelRenderError = panelRenderError;
    }

    public List<ArchivoAval> getListaArchivosAval() {
        return listaArchivosAval;
    }

    public void setListaArchivosAval(List<ArchivoAval> listaArchivosAval) {
        this.listaArchivosAval = listaArchivosAval;
    }

    public boolean isImprimirReporteAval() {
        return imprimirReporteAval;
    }

    public void setImprimirReporteAval(boolean imprimirReporteAval) {
        this.imprimirReporteAval = imprimirReporteAval;
    }

    public UploadedFile getArchivoCargar() {
        return archivoCargar;
    }

    public void setArchivoCargar(UploadedFile archivoCargar) {
        this.archivoCargar = archivoCargar;
    }

    public Aval getAval() {
        return aval;
    }

    public void setAval(Aval aval) {
        this.aval = aval;
    }

    public List<Aval> getListaAval() {
        return listaAval;
    }

    public void setListaAval(List<Aval> listaAval) {
        this.listaAval = listaAval;
    }

    public String getNombreProfesor() {
        return nombreProfesor;
    }

    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = nombreProfesor;
    }

    public void setArchivoSeleccionado(ArchivoAval archivoSeleccionado) {
        this.archivoSeleccionado = archivoSeleccionado;
    }

    public ArchivoAval getArchivoSeleccionado() {
        return archivoSeleccionado;
    }

    public void descargarAval() {
        descargarArchivoDocumentoAvalGenerico(aval);
    }

    public void descargarArchivoCoor() {
        ArchivoAval archivo = documentoSeleccionado;
        descargarArchivoAvalGenerico(archivo.getId());
    }

    public boolean isVer() {
        return ver;
    }

    public void setVer(boolean ver) {
        this.ver = ver;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public ArchivoAval getDocumentoSeleccionado() {
        return documentoSeleccionado;
    }

    public void setDocumentoSeleccionado(ArchivoAval documentoSeleccionado) {
        this.documentoSeleccionado = documentoSeleccionado;
    }

    public boolean isAvalRelacionado() {
        return avalRelacionado;
    }

    public void setAvalRelacionado(boolean avalRelacionado) {
        this.avalRelacionado = avalRelacionado;
    }

    public List<InvestigadorInterno> getListaUab() {
        return listaUab;
    }

    public void setListaUab(List<InvestigadorInterno> listaUab) {
        this.listaUab = listaUab;
    }

    public List<InvestigadorInterno> getListaFacultad() {
        return listaFacultad;
    }

    public void setListaFacultad(List<InvestigadorInterno> listaFacultad) {
        this.listaFacultad = listaFacultad;
    }

    public List<InvestigadorInterno> getListaDireccion() {
        return listaDireccion;
    }

    public void setListaDireccion(List<InvestigadorInterno> listaDireccion) {
        this.listaDireccion = listaDireccion;
    }

    public List<InvestigadorInterno> getListaVicerrectoria() {
        return listaVicerrectoria;
    }

    public void setListaVicerrectoria(List<InvestigadorInterno> listaVicerrectoria) {
        this.listaVicerrectoria = listaVicerrectoria;
    }

    public Dependencia getDependencia() {
        return dependencia;
    }

    public void setDependencia(Dependencia dependencia) {
        this.dependencia = dependencia;
    }

    public List<HistoricoEstadoAval> getListaHistoricoEstadoAval() {
        return listaHistoricoEstadoAval;
    }

    public void setListaHistoricoEstadoAval(List<HistoricoEstadoAval> listaHistoricoEstadoAval) {
        this.listaHistoricoEstadoAval = listaHistoricoEstadoAval;
    }

    public ConvocatoriaExterna getConvocatoria() {
        return convocatoria;
    }

    public void setConvocatoria(ConvocatoriaExterna convocatoria) {
        this.convocatoria = convocatoria;
    }

    public HistoricoEstadoAval getHistoricoAval() {
        return historicoAval;
    }

    public void setHistoricoAval(HistoricoEstadoAval historicoAval) {
        this.historicoAval = historicoAval;
    }

	public List<InvestigadorInterno> getListaDRE() {
		return listaDRE;
	}

	public void setListaDRE(List<InvestigadorInterno> listaDRE) {
		this.listaDRE = listaDRE;
	}

}
