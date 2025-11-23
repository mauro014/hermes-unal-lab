package co.edu.unal.hermes.vista.aval.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.ArchivoAval;
import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.utils.ReemplazaAcentos;
import co.edu.unal.hermes.vista.ManejadorBase;

public class BaseManejadorSolicitarAvalFacultad extends ManejadorBase {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    protected Aval aval;
    protected List<Aval> listaAval;

    protected ArchivoAval documentoSeleccionado;
    protected List<CorreoPlantilla> listaplantilla;
    protected SelectItem[] plantillas;
    protected String avalId;
    protected UploadedFile archivoCoorCargar;

    protected boolean verRevision = true;
    protected boolean verEvaluacion;

    protected ArchivoAval arCoor;

    protected Investigador investigadorActual;
    protected UploadedFile archivoCargar;
    protected List<ArchivoAval> listaArchivos;
    protected String nombreInvestigador = "";
    protected CorreoPlantilla correoActual = new CorreoPlantilla();
    protected String cuerpoCorreo = "";
    protected SelectItem[] categoriaItems = { new SelectItem(new Integer(1), "Pendiente de Aprobación"),
            new SelectItem(new Integer(2), "Aprobado"), new SelectItem(new Integer(3), "No Aprobado"),
            new SelectItem(new Integer(4), "Devolver para correcciones") };
    
    protected List<Aval> filteredAvales;
    protected int selItem;
    protected boolean edicionProyecto = false;

    public BaseManejadorSolicitarAvalFacultad(){
        aval = new Aval();
        investigadorActual = servicioPersona.obtenerInvestigador(((Persona) sesion.getAttribute("persona")).getId());
    }

    public void imprimirProyecto() {
        Long id = aval.getIdProyecto();
        Proyecto proyectoActual = servicioProyecto.obtenerProyecto(id, ProyectoDAOHibernate.INFORMACION_GENERAL);
        if (proyectoActual != null) {
            servicioProyecto.imprimirReporteProyecto(proyectoActual, sesion, false);
        }
    }
    
    public void descargarArchivo() {
        if (documentoSeleccionado != null && documentoSeleccionado.getId() != null) {
            descargarArchivoAvalGenerico(documentoSeleccionado.getId());
        } else {
            descargarArchivoAvalGenerico(arCoor.getId());
        }
    }
    
    public void guardarArchivo(FileUploadEvent event) {
        archivoCargar = event.getFile();
        cargarArchivoDisco(archivoCargar, "HER_AVAL", aval.getAviId() + "");
        int i = archivoCargar.getFileName().lastIndexOf("\\");
        this.aval.setArchivoAval(archivoCargar.getFileName().substring(i + 1));
    }

    public void guardarArchivoCoor(FileUploadEvent event) {
        archivoCoorCargar = event.getFile();
        int i = archivoCoorCargar.getFileName().lastIndexOf("\\");
        ArchivoAval aa = insertarArchivoAvalGenerico(aval.getAviId(), archivoCoorCargar, true, "175");
        aa.setNombre(archivoCoorCargar.getFileName().substring(i + 1));
        aval.getArchivosCoor().add(aa);
    }
    
    public void eliminarArchivoCoor() {
        aval.getArchivosCoor().remove(arCoor);
        arCoor.setAvalCoor(0L);
        arCoor.setFechaBorrado(new Date());

        if (arCoor.getDescripcion() != null) {
            arCoor.setDescripcion(arCoor.getDescripcion() + " - Eliminado por "
                    + investigadorActual.getId().getDocumento() + "-" + investigadorActual.getId().getTipoDocumento());
            if (aval.getAviId() != null) {
                arCoor.setDescripcion(arCoor.getDescripcion() + "- Aval previo = " + aval.getAviId());
            }
        } else {
            arCoor.setDescripcion("Eliminado por " + investigadorActual.getId().getDocumento() + "-"
                    + investigadorActual.getId().getTipoDocumento());
            if (aval.getAviId() != null) {
                arCoor.setDescripcion(arCoor.getDescripcion() + "- Aval previo = " + aval.getAviId());
            }
        }
        servicioGeneral.guardarObjeto(arCoor);
    }
    
    public String editarCorreo(Persona personaAux, Aval nAval) {
        String correo = correoActual.getCuerpo();
        correo = correo.replaceAll("<<INVESTIGADOR>>", personaAux.getNombreCompletoMinusculas());

        correo = correo.replaceAll("<<ID_AVAL>>", nAval.getAviId().toString());

        if (nAval.getDescripcionfacultad() != null) {
            String mensaje = nAval.getDescripcionfacultad();

            if (!mensaje.contains("$")) {
                correo = correo.replaceAll("<<RAZON_RECHAZO>>", nAval.getDescripcionfacultad());
                correo = correo.replaceAll("<<OBSERVACIONES>>", nAval.getDescripcionfacultad());
            } else {
                String mensajeSinCaracter = eliminarCaracterSinReplace("$", nAval.getDescripcionfacultad());
                correo = correo.replaceAll("<<RAZON_RECHAZO>>", mensajeSinCaracter);
                correo = correo.replaceAll("<<OBSERVACIONES>>", mensajeSinCaracter);
            }

            if (edicionProyecto) {
                correo = correo.replaceAll("<<PROYECTO>>",
                        "El proyecto asociado al aval ha sido habilitado para edición con el fin de que aplique los cambios indicados, recuerde que debe enviarlo de nuevo para su revisión");
            } else {
                correo = correo.replaceAll("<<PROYECTO>>", " ");
            }
        }
        cuerpoCorreo = correo;
        return "";
    }
    
    /**
     * enviar correo generico a la persona destinatario con copia o no la que persona que genera el correo
     * @param correoActual
     * @param personaDestinataria
     * @param copiaPersonaActual
     */
    public void enviarCorreoGenerico(CorreoPlantilla correoActual, Persona personaDestinataria, boolean copiaPersonaActual){
        editarCorreo(personaDestinataria, aval);
        Correo correo = new Correo();
        correo.setOrigen(Correo.CORREO_HERMES);
        correo.adicionarDireccion(personaDestinataria.getEmail());
        //correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
        correo.setAsunto(correoActual.getAsunto());
        correo.setCuerpo(cuerpoCorreo);
        if(copiaPersonaActual){
            correo.adicionarDireccion(personaActual.getEmail());
        }
        servicioCorreo.enviarCorreo(correo);
    }
    
    public void consultarArchivosAdjuntos(){
        List<ArchivoAval> archi = this.servicioGeneral.obtenerObjetos(ArchivoAval.class,
                "select a from ArchivoAval a where a.aval='" + aval.getAviId() + "'");
        if (!esListaVacia(archi)) {
            for (int i = 0; i < archi.size(); i++) {
                aval.setArchivo(archi.get(i));
            }
        }

        List<ArchivoAval> archi2 = servicioGeneral.obtenerListaObjetosWhere(ArchivoAval.class,
                " where a.avalCoor='" + aval.getAviId() + "'");
        if (!esListaVacia(archi2)) {
            for (int i = 0; i < archi2.size(); i++) {
                aval.getArchivosCoor().add(archi2.get(i));
            }
        }
    }
    
    public void guardarArchivosSoporte(){
        for (ArchivoAval aa : aval.getArchivosCoorList()) {
            aa.setAvalCoor(aval.getAviId());
            servicioGeneral.guardarObjeto(aa);
        }
    }
    
    public void aprobarProyectoJornadaDocente(){
        
        Proyecto proyectoAval = servicioProyecto.obtenerProyecto(
        		aval.getIdProyecto(),
                ProyectoDAOHibernate.DATOS_BASICOS);
        if (proyectoAval.getModalidad().getId().equals(MODALIDAD_FICHA_MINIMA_ID) && proyectoAval.getEstadoProyecto().getId().equals(EstadoProyecto.PROPUESTO)) {
            proyectoAval.cambiarEstadoPersona(
            		EstadoProyecto.APROBADO, 
            		cargarPersonaActual(),
                    "Aprobación de proyecto para jornada docente");
            proyectoAval.setEsJornadaDocente("Y");
            servicioGeneral.guardarObjeto(proyectoAval);
            
            //Notificacion labs asociados
    		if(!proyectoAval.getLaboratorios().isEmpty()) {
    			for (Laboratorio lab : (ArrayList<Laboratorio>) proyectoAval.getListaLaboratorios()) {
    				enviarCorreoLaboratoriosProyecto(lab, proyectoAval, CorreoPlantilla.CORREO_NOTIFICACION_ASOCIACION_LAB_PROYECTO_APROBADO);
    			}
    		}
        }
    }
    
    public Aval getAval() {
        return aval;
    }

    public void setAval(Aval aval) {
        this.aval = aval;
    }

    public Investigador getInvestigadorActual() {
        return investigadorActual;
    }

    public void setInvestigadorActual(Investigador investigadorActual) {
        this.investigadorActual = investigadorActual;
    }

    public UploadedFile getArchivoCargar() {
        return archivoCargar;
    }

    public void setArchivoCargar(UploadedFile archivoCargar) {
        this.archivoCargar = archivoCargar;
    }

    public int getTamañoLista() {
        if (!esListaVacia(listaAval)) {
            return listaAval.size();
        } else {
            return 0;
        }
    }
    
    public List<ArchivoAval> getListaArchivos() {

        listaArchivos = new ArrayList<ArchivoAval>();
        if (aval != null && aval.getArchivos().size() > 0) {
            for (Iterator<ArchivoAval> iterador = aval.getArchivos().iterator(); iterador.hasNext();) {
                listaArchivos.add(iterador.next());
            }
        }
        return listaArchivos;
    }

    public List<Aval> getListaAval() {
        return listaAval;
    }

    public void setListaAval(List<Aval> listaAval) {
        this.listaAval = listaAval;
    }

    public void setListaArchivos(List<ArchivoAval> listaArchivos) {
        this.listaArchivos = listaArchivos;
    }

    public String getNombreInvestigador() {
        return nombreInvestigador;
    }

    public void setNombreInvestigador(String nombreInvestigador) {
        this.nombreInvestigador = nombreInvestigador;
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

    public ArchivoAval getDocumentoSeleccionado() {
        return documentoSeleccionado;
    }

    public void setDocumentoSeleccionado(ArchivoAval documentoSeleccionado) {
        this.documentoSeleccionado = documentoSeleccionado;
    }

    public boolean isVerRevision() {
        return verRevision;
    }

    public void setVerRevision(boolean verRevision) {
        this.verRevision = verRevision;
    }

    public boolean isVerEvaluacion() {
        return verEvaluacion;
    }

    public void setVerEvaluacion(boolean verEvaluacion) {
        this.verEvaluacion = verEvaluacion;
    }

    public List<CorreoPlantilla> getListaplantilla() {
        return listaplantilla;
    }

    public void setListaplantilla(List<CorreoPlantilla> listaplantilla) {
        this.listaplantilla = listaplantilla;
    }

    public SelectItem[] getPlantillas() {
        return plantillas;
    }

    public void setPlantillas(SelectItem[] plantillas) {
        this.plantillas = plantillas;
    }

    public String getAvalId() {
        return avalId;
    }

    public void setAvalId(String avalId) {
        this.avalId = avalId;
    }

    public UploadedFile getArchivoCoorCargar() {
        return archivoCoorCargar;
    }

    public void setArchivoCoorCargar(UploadedFile archivoCoorCargar) {
        this.archivoCoorCargar = archivoCoorCargar;
    }

    public ArchivoAval getArCoor() {
        return arCoor;
    }

    public void setArCoor(ArchivoAval arCoor) {
        this.arCoor = arCoor;
    }

    public SelectItem[] getCategoriaItems() {
        return categoriaItems;
    }

    public void setCategoriaItems(SelectItem[] categoriaItems) {
        this.categoriaItems = categoriaItems;
    }

    public List<Aval> getFilteredAvales() {
        return filteredAvales;
    }

    public void setFilteredAvales(List<Aval> filteredAvales) {
        this.filteredAvales = filteredAvales;
    }

    public int getSelItem() {
        return selItem;
    }

    public void setSelItem(int selItem) {
        this.selItem = selItem;
    }

    public boolean isEdicionProyecto() {
        return edicionProyecto;
    }

    public void setEdicionProyecto(boolean edicionProyecto) {
        this.edicionProyecto = edicionProyecto;
    }

}
