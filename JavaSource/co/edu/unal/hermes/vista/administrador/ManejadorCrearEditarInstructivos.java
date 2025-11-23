/**
 * @author Martha Liliana Correa O.
 * @date 10/11/2015
 */

package co.edu.unal.hermes.vista.administrador;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.ArchivoInstructivo;
import co.edu.unal.hermes.modelo.Instructivo;
import co.edu.unal.hermes.modelo.InstructivoClasificacion;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Rol;

public class ManejadorCrearEditarInstructivos extends ManejadorAdministrarInstructivos {

    /**
     * 
     */
    private static final long serialVersionUID = 7143085113009418072L;
    private boolean esEdicion;
    private boolean esNuevo;
    private Instructivo instructivo;
    private SelectItem[] estadoInstructivo = { new SelectItem("A", "Activo"), new SelectItem("I", "Inactivo") };
	private SelectItem[] componenteInstructivo = { new SelectItem("DP", "Documentos de lineamientos y protocolos"),
			new SelectItem("I", "Investigación"), new SelectItem("E", "Extensión"), new SelectItem("L", "Laboratorios"),
			new SelectItem("PI", "Propiedad Intelectual"),
			new SelectItem("CB", "Biodiversidad y Colecciones Biológicas"),
			new SelectItem("ED", "Sistema de Gestión Editorial"), new SelectItem("O", "Otros") };
    private List<SelectItem> listaRolesItem;
    private List<ArchivoInstructivo> listaArchivos;
    private ArchivoInstructivo archivoSeleccionado;

    public ManejadorCrearEditarInstructivos() {
        Long id = null;
        personaActual = (Persona) sesion.getAttribute("persona");
        try {
            id = (Long) sesion.getAttribute("idInstructivo");
        } catch (Exception e) {
            esEdicion = false;
        }

        if (id != null) {
            String consulta = "select icl from Instructivo icl where icl.id = '" + id + "'";
            List<Instructivo> instructivos = servicioGeneral.obtenerObjetos(Instructivo.class, consulta);

            if (!esListaVacia(instructivos)) {
                instructivo = (Instructivo) instructivos.get(0);
                consultarArchivosInstructivo();
            }
        } else {
            esNuevo = true;
            instructivo = new Instructivo();
            InstructivoClasificacion clas = new InstructivoClasificacion();
            Rol rol = new Rol();
            instructivo.setClasificacion(clas);
            instructivo.setRol(rol);
            listaArchivos = new ArrayList<ArchivoInstructivo>();
        }
        consultarClasificacionesActivasInstructivos();
        consultarRolesSistema();
    }

    public void consultarRolesSistema() {
        List<Rol> listaRoles = servicioGeneral.obtenerObjetosLimitado(Rol.class,
                "select #id r.id, #nombre r.nombre  " + "from Rol r " + "order by r.nombre asc");

        if (!esListaVacia(listaRoles)) {
            listaRolesItem = new ArrayList<SelectItem>();
            for (int i = 0; i < listaRoles.size(); i++) {
                Rol rol = (Rol) listaRoles.get(i);
                listaRolesItem.add(new SelectItem(rol.getId(), rol.getNombre()));
            }
        }
    }

    public void consultarArchivosInstructivo() {
        listaArchivos = new ArrayList<ArchivoInstructivo>();
        String consultaArchivos = "select a from ArchivoInstructivo a where a.instructivo.id = '" + instructivo.getId()
                + "' and a.estado = 'V'";
        List<ArchivoInstructivo> listaA = (List<ArchivoInstructivo>) servicioGeneral
                .obtenerObjetos(ArchivoInstructivo.class, consultaArchivos);
        if (!esListaVacia(listaA)) {
            listaArchivos.addAll(listaA);
        }
    }

    public void adjuntarArchivo(FileUploadEvent event) {
        UploadedFile archivoCargado = event.getFile();
        ArchivoInstructivo a = insertarArchivoInstructivo(0, archivoCargado);
        if (a != null) {
            listaArchivos.add(a);
        }
    }

    public void descargarArchivo() {
        if (archivoSeleccionado != null) {
            descargarArchivoInstructivo(archivoSeleccionado);
        }
    }

    public void eliminarArchivo() {
        listaArchivos.remove(archivoSeleccionado);
        archivoSeleccionado.setFechaElimina(new Date());
        archivoSeleccionado.setEstado("B");
        Persona personaElimina = (Persona) sesion.getAttribute("persona");
        archivoSeleccionado.setPersonaElimina(personaElimina);
        servicioGeneral.guardarObjeto(archivoSeleccionado);
    }

    public void guardar() {

        boolean validada = true;

        if (instructivo.getComponente() == null || "".equals(instructivo.getComponente())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Debe indicar el componente al que pertenece el instructivo.", ""));
            validada = false;
        } else if (instructivo.getClasificacion().getId() == null
                || "".equals(instructivo.getClasificacion().getId().toString())
                || instructivo.getClasificacion().getId() == 0L) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Debe seleccionar una clasificación para el instructivo.", ""));
            validada = false;
        } else if (instructivo.getNombre() == null || "".equals(instructivo.getNombre().trim())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe indicar el nombre del instructivo.", ""));
            validada = false;
        } else if (instructivo.getDescripcion() == null || "".equals(instructivo.getDescripcion().trim())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Debe ingresar una breve descripción para el instructivo.", ""));
            validada = false;
        } else if (instructivo.getRol().getId() == null || "".equals(instructivo.getRol().getId().toString())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Debe indicar el rol al que se asocia el instructivo.", ""));
            validada = false;
        } else if (instructivo.getEstado() == null || "".equals(instructivo.getEstado().trim())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe asignar un estado al instructivo.", ""));
            validada = false;
        }

        if (validada) {
            // Guardar persona que abre o cierra
            if ("I".equals(instructivo.getEstado())) {
                instructivo.setPersonaElimina(personaActual);
            } else if ("A".equals(instructivo.getEstado())) {
                instructivo.setPersonaCarga(personaActual);
            }
            instructivo.setFecha(new Date());

            // GuardarConvocatoria
            servicioGeneral.guardarObjeto(instructivo);

            // GuardarArchivos
            Iterator<ArchivoInstructivo> itSet = listaArchivos.iterator();
            while (itSet.hasNext()) {
                ArchivoInstructivo archivo = itSet.next();
                archivo.setInstructivo(instructivo);
                archivo.setEstado("V");
                this.servicioGeneral.guardarObjeto(archivo);
            }

            sesion.removeAttribute("manejadorAdministrarInstructivos");
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "El instructivo se ha guardado correctamente.", ""));
        } else {
            sesion.removeAttribute("manejadorBusquedaAyuda");
            return;

        }
        sesion.removeAttribute("manejadorBusquedaAyuda");
    }

    public boolean isEsEdicion() {
        return esEdicion;
    }

    public void setEsEdicion(boolean esEdicion) {
        this.esEdicion = esEdicion;
    }

    public boolean isEsNuevo() {
        return esNuevo;
    }

    public void setEsNuevo(boolean esNuevo) {
        this.esNuevo = esNuevo;
    }

    public Instructivo getInstructivo() {
        return instructivo;
    }

    public void setInstructivo(Instructivo instructivo) {
        this.instructivo = instructivo;
    }

    public SelectItem[] getEstadoInstructivo() {
        return estadoInstructivo;
    }

    public void setEstadoInstructivo(SelectItem[] estadoInstructivo) {
        this.estadoInstructivo = estadoInstructivo;
    }

    public List<SelectItem> getListaRolesItem() {
        return listaRolesItem;
    }

    public void setListaRolesItem(List<SelectItem> listaRolesItem) {
        this.listaRolesItem = listaRolesItem;
    }

    public ArchivoInstructivo getArchivoSeleccionado() {
        return archivoSeleccionado;
    }

    public void setArchivoSeleccionado(ArchivoInstructivo archivoSeleccionado) {
        this.archivoSeleccionado = archivoSeleccionado;
    }

    public List<ArchivoInstructivo> getListaArchivos() {
        return listaArchivos;
    }

    public void setListaArchivos(List<ArchivoInstructivo> listaArchivos) {
        this.listaArchivos = listaArchivos;
    }

    public SelectItem[] getComponenteInstructivo() {
        return componenteInstructivo;
    }

    public void setComponenteInstructivo(SelectItem[] componenteInstructivo) {
        this.componenteInstructivo = componenteInstructivo;
    }

}
