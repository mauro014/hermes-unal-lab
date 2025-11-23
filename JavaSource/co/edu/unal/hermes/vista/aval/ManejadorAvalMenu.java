package co.edu.unal.hermes.vista.aval;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.ArchivoAval;
import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorAvalMenu extends ManejadorBase {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public static final String ID_AVAL_SESION = "idAval"; //nuevo, edicion, consulta
    private List<Aval> listaAvales;
    private InvestigadorInterno investigadorInterno;
    private Aval avalSeleccionado;
    private String idAval;
    private UploadedFile archivoCargado;
    protected List<ArchivoAval> listaArchivosSoporteConv = new ArrayList<ArchivoAval>();
    protected ArchivoAval archivoAvalSoporteConv;
    private Long avalIdSoporteConv;


    public ManejadorAvalMenu() {
        avalSeleccionado = new Aval();
        this.investigadorInterno = servicioPersona
                .obtenerInvestigadorInterno(((Persona) sesion.getAttribute("persona")).getId());
    }

    public List<Aval> getListaAvalesInvestigador() {
        listaAvales = servicioGeneral.obtenerAvalesInvestigador(this.investigadorInterno.getId().getDocumento(),
                this.investigadorInterno.getId().getTipoDocumento());
        return listaAvales;
    }

    private boolean validarVinculacionPersona() {
        if (investigadorInterno == null) {
            return false;
        }else{
            if (investigadorInterno.getTipoVinculacion().getId()!=null && ("16".equals(investigadorInterno.getTipoVinculacion().getId()) || "30".equals(investigadorInterno.getTipoVinculacion().getId())
                    || "29".equals(investigadorInterno.getTipoVinculacion().getId()) ||investigadorInterno.getTipoCargo().getId().equals("D-ESPEMP"))) {
                return true;
            }
        }
        return false;
    }

    // Aval Investigador
    public String solicitarAval() {
        eliminarManejadoresAval(ID_AVAL_SESION);
        sesion.removeAttribute(ID_AVAL_SESION);
        return solicitudAval("IN");
    }

    public String solicitudAval(String tipo) {
        // Tipo -> IN : Investigador
        if (validarVinculacionPersona()) {
            eliminarManejadoresAval(ID_AVAL_SESION);
            if ("IN".equals(tipo)) {
                return "solicitudAvalHome";
            }
        }
        return "";
    }

    public String verAvalesRegistrados() {
        sesion.removeAttribute("ManejadorAvalMenu");
        sesion.removeAttribute(ID_AVAL_SESION);
        return "successProyectosAval";
    }

    public String registroProyectos() {
        eliminarManejadoresAval(ID_AVAL_SESION);
        if (validarVinculacionPersona()) {
            return "irRegistroProyectos";
        } else {
            return "";
        }
    }

    public String editarMovilidadAval() {
        eliminarManejadoresAval(ID_AVAL_SESION);
        FacesContext context = FacesContext.getCurrentInstance();
        borrarManejadoresInsercionProyecto();
        Map map = context.getExternalContext().getRequestParameterMap();
        Object o = map.get("idAvalConsulta");
        Long id = Long.valueOf((String) o);
        sesion.setAttribute(ID_AVAL_SESION, id);
        sesion.setAttribute("esConsulta", true);

        if ((Long) sesion.getAttribute(ID_AVAL_SESION) <= 4855) {
            return "avalarConsulta";
        } else {
            return "avalarConsultaHome";
        }
    }
    
    public String interponerRecursoAval() {
        eliminarManejadoresAval(ID_AVAL_SESION);
        FacesContext context = FacesContext.getCurrentInstance();
        borrarManejadoresInsercionProyecto();
        Map map = context.getExternalContext().getRequestParameterMap();
        Object o = map.get("idAvalConsulta");
        Long id = Long.valueOf((String) o);
        sesion.setAttribute(ID_AVAL_SESION, id);
        return "interponerRecursoAval";
    }
    
    public String interponerQuejaAval() {
        eliminarManejadoresAval(ID_AVAL_SESION);
        FacesContext context = FacesContext.getCurrentInstance();
        borrarManejadoresInsercionProyecto();
        Map map = context.getExternalContext().getRequestParameterMap();
        Object o = map.get("idAvalConsulta");
        Long id = Long.valueOf((String) o);
        sesion.setAttribute(ID_AVAL_SESION, id);
        return "interponerQuejaAval";
    }

    public String eliminarSolicitudAval() {
        List<Aval> lista = servicioGeneral.obtenerAval(idAval);
        if (!esListaVacia(lista)) {
            Aval aval = lista.get(0);
            aval.setAviEstado(Aval.BORRADO);
            servicioGeneral.guardarObjeto(aval);
            crearHistoricoEstadoAval(aval, cargarPersonaActual(), "D");
        }
        return "";
    }

    public String editarAval() {
        eliminarManejadoresAval(ID_AVAL_SESION);
        FacesContext context = FacesContext.getCurrentInstance();
        borrarManejadoresInsercionProyecto();
        Map map = context.getExternalContext().getRequestParameterMap();
        Object o = map.get("idAvalConsulta");
        Long id = Long.valueOf((String) o);
        sesion.setAttribute(ID_AVAL_SESION, id);
        sesion.setAttribute("esEdicion", true);
        return "avalarEditarHome";
    }
    
    /**
     * Adjunta archivos de la solicitud de aval
     */
    public void adjuntarArchivo(FileUploadEvent event) {
        archivoCargado = event.getFile();
        ArchivoAval aa = insertarArchivoAvalGenerico(Long.parseLong(idAval), archivoCargado, false, "SPC");
        if (aa != null) {
        	listaArchivosSoporteConv.add(aa);
        }
    }
    
    /**
     * Consulta archivos asociados al aval, en caso de que se encuentre en
     * edición o para consulta
     */
    public void cerrarVentanaSoporteParticipacionConvocatoria() {
    	if(!getListaArchivosSoporteConv().isEmpty()){
    		List<Aval> listaAvalesSoporteConv= servicioGeneral.obtenerAval(idAval);
    	    Aval avalSopParConv = listaAvalesSoporteConv.get(0);
    	    avalSopParConv.setTieneArchivosSoporteParticipacionConvocatoria("S");
    	    servicioGeneral.guardarObjeto(avalSopParConv);
    	}
    }

    /**
     * Descargar archivos de la solicitud de aval, ya sea de solicitud o
     * revisión
     */

    public void descargarArchivo() {
        if (archivoAvalSoporteConv != null) {
            descargarArchivoAvalGenerico(archivoAvalSoporteConv.getId());
        }
    }

    /**
     * Elimina archivos de la solicitud de aval
     */
    public void eliminarArchivo() {
    	List<Aval> listaAvalesSoporteConv= servicioGeneral.obtenerAval(idAval);
    	Aval aval = listaAvalesSoporteConv.get(0);
    	listaArchivosSoporteConv.remove(archivoAvalSoporteConv);
        aval.eliminarArchivo(archivoAvalSoporteConv);
        archivoAvalSoporteConv.setAval(0L);
        archivoAvalSoporteConv.setFechaBorrado(new Date());

        if (archivoAvalSoporteConv.getDescripcion() != null) {
        	archivoAvalSoporteConv.setDescripcion(archivoAvalSoporteConv.getDescripcion() + " - Eliminado por "
                    + investigadorInterno.getId().getDocumento() + "-" + investigadorInterno.getId().getTipoDocumento());
        } else {
        	archivoAvalSoporteConv.setDescripcion("Eliminado por " + investigadorInterno.getId().getDocumento() + "-"
                    + investigadorInterno.getId().getTipoDocumento());
        }
        if (aval.getAviId() != null) {
        	archivoAvalSoporteConv.setDescripcion(archivoAvalSoporteConv.getDescripcion() + "- Aval previo = " + aval.getAviId());
        }
        servicioGeneral.guardarObjeto(archivoAvalSoporteConv);
    }


    public List getListaAvales() {
        return listaAvales;
    }

    public void setListaAvales(List listaAvales) {
        this.listaAvales = listaAvales;
    }

    public Aval getAvalSeleccionado() {
        return avalSeleccionado;
    }

    public void setAvalSeleccionado(Aval avalSeleccionado) {
        this.avalSeleccionado = avalSeleccionado;
    }

    public String getIdAval() {
        return idAval;
    }

    public void setIdAval(String idAval) {
        this.idAval = idAval;
    }

	public UploadedFile getArchivoCargado() {
		return archivoCargado;
	}

	public void setArchivoCargado(UploadedFile archivoCargado) {
		this.archivoCargado = archivoCargado;
	}

	public List<ArchivoAval> getListaArchivosSoporteConv() {
		List<ArchivoAval> listaA;
		if(idAval!=null){
			String consultaArchivos = "select a from ArchivoAval a where a.tipo = 'SPC' and a.aval = " + idAval;
			listaA = (List<ArchivoAval>) servicioGeneral.obtenerObjetos(ArchivoAval.class,
	                consultaArchivos);
		}else{
			listaA = new ArrayList<ArchivoAval>();
		}

		return listaA;
	}

	public ArchivoAval getArchivoAvalSoporteConv() {
		return archivoAvalSoporteConv;
	}

	public void setArchivoAvalSoporteConv(ArchivoAval archivoAvalSoporteConv) {
		this.archivoAvalSoporteConv = archivoAvalSoporteConv;
	}

	public Long getAvalIdSoporteConv() {
		return avalIdSoporteConv;
	}

	public void setAvalIdSoporteConv(Long avalIdSoporteConv) {
		this.avalIdSoporteConv = avalIdSoporteConv;
	}

}
