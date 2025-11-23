/**
 * @author Mauricion Amaya Ríos
 * @date 06/05/2015
 */

package co.edu.unal.hermes.vista.asesor;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.ArchivoConvocatoriaPadre;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.CorteConvocatoria;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorAdministrarConvocatorias extends ManejadorBase {

    private static final long serialVersionUID = 2106924496528032508L;
    private String nombreConvocatoriaBusqueda;
    private String idConvocaPadre;
    private List<ConvocatoriaPadre> listaConvocatoriasPadre;
    private SelectItem[] convocatoriaPadreItem;
    private ConvocatoriaPadre convocatoriaPadre;
    private List<ArchivoConvocatoriaPadre> listaArchivos;
    private List<CorteConvocatoria> listaCortes;
    private ArchivoConvocatoriaPadre archivoProcesoSeleccionado;

    // Campos nuevo corte
    private Date fechaInicial;
    private Date fechaFinal;
    private Date fechaInicialReclamacionRequisitos;
    private Date fechaFinalReclamacionRequisitos;
    private Date fechaInicialReclamacionEvaluacion;
    private Date fechaFinalReclamacionEvaluacion;
    private Date fechaPublicacionResultados;
    
    private CorteConvocatoria corteEdicion;
    private boolean mostrarEditarCorte;
    
    public Date getFechaInicialReclamacionRequisitos() {
		return fechaInicialReclamacionRequisitos;
	}

	public void setFechaInicialReclamacionRequisitos(
			Date fechaInicialReclamacionRequisitos) {
		this.fechaInicialReclamacionRequisitos = fechaInicialReclamacionRequisitos;
	}

	public Date getFechaFinalReclamacionRequisitos() {
		return fechaFinalReclamacionRequisitos;
	}

	public void setFechaFinalReclamacionRequisitos(
			Date fechaFinalReclamacionRequisitos) {
		this.fechaFinalReclamacionRequisitos = fechaFinalReclamacionRequisitos;
	}

	public Date getFechaInicialReclamacionEvaluacion() {
		return fechaInicialReclamacionEvaluacion;
	}

	public void setFechaInicialReclamacionEvaluacion(
			Date fechaInicialReclamacionEvaluacion) {
		this.fechaInicialReclamacionEvaluacion = fechaInicialReclamacionEvaluacion;
	}

	public Date getFechaFinalReclamacionEvaluacion() {
		return fechaFinalReclamacionEvaluacion;
	}

	public void setFechaFinalReclamacionEvaluacion(
			Date fechaFinalReclamacionEvaluacion) {
		this.fechaFinalReclamacionEvaluacion = fechaFinalReclamacionEvaluacion;
	}

	public Date getFechaPublicacionResultados() {
		return fechaPublicacionResultados;
	}

	public void setFechaPublicacionResultados(Date fechaPublicacionResultados) {
		this.fechaPublicacionResultados = fechaPublicacionResultados;
	}

	private List<Sede> sedes;
    private String sedeId;
    private Long numeroCorte;
    private Long minimoCortePermitido;
    private boolean mostrarAgregarArchivo;
    private boolean mostrarAgregarCorte;
    private CorteConvocatoria corteSeleccionado;

    public ManejadorAdministrarConvocatorias() {
        cargarSedes();
        personaActual = (Persona) sesion.getAttribute("persona");
        setMostrarEditarCorte(false);
    }

    public void consultarConvocatorias() {
        consultarConvocatorias(false);
    }

    public void  consultarConvocatorias(boolean soloPermanentes){		
		boolean encontrado = true;
		setMostrarEditarCorte(false);
		if(nombreConvocatoriaBusqueda.length()>0){
			String sql = "from ConvocatoriaPadre where upper(titulo)"
					+ " like '%"+nombreConvocatoriaBusqueda.toUpperCase()+"%' ";
			if(soloPermanentes){
				sql += " and esPermanente = '"+ConvocatoriaPadre.PERMANENTE+"' ";
			}
			sql += "order by id desc";
			listaConvocatoriasPadre = 
					servicioGeneral.obtenerObjetos(ConvocatoriaPadre.class,sql);
			if(!esListaVacia(listaConvocatoriasPadre)){
				convocatoriaPadreItem = 
						new SelectItem[listaConvocatoriasPadre.size()];
				for (int i = 0; i < listaConvocatoriasPadre.size(); i++) {
					ConvocatoriaPadre con = 
						(ConvocatoriaPadre)listaConvocatoriasPadre.get(i);
					if(i == 0) idConvocaPadre = con.getId().toString();
					convocatoriaPadreItem[i] = 
						new SelectItem(con.getId().toString(),con.getTitulo());	       
				}
			}else{
				encontrado = false;
			}
		}else{
			encontrado = false;
		}	   
		if(!encontrado){
			listaConvocatoriasPadre = new ArrayList<ConvocatoriaPadre>();
			convocatoriaPadreItem = new SelectItem[0];
		}
	}

    public void consultarConvocatoriasPermenanentes() {
        consultarConvocatorias(true);
    }

    public void cargarArchivosConvocatoriaPadre() {
        inicializarListas();
        if (listaConvocatoriasPadre != null) {
            obtenerConvocatoriaPadreSeleccionada();
            if (convocatoriaPadre != null) {
                listaArchivos = servicioGeneral.obtenerObjetos(ArchivoConvocatoriaPadre.class,
                        "from ArchivoConvocatoriaPadre a where " + " a.convocatoriaPadre = '"
                                + convocatoriaPadre.getId() + "' " + "and a.tipoArchivo <> 'B' order by a.id asc ");
            }
        }
        mostrarAgregarArchivo = true;
    }

    public void cargarCortesConvocatoriaPadre() {
        cargarCortesConvocatoriaPadre(true);
    }

    private void cargarCortesConvocatoriaPadre(boolean inicializarCampos) {
        inicializarListas();
        if (listaConvocatoriasPadre != null) {
            obtenerConvocatoriaPadreSeleccionada();
            if (convocatoriaPadre != null) {
                listaCortes = servicioGeneral.obtenerObjetos(CorteConvocatoria.class,
                        "from CorteConvocatoria c where " + " c.convocatoriaPadre.id = '" + convocatoriaPadre.getId()
                                + "' " + "and c.estado <> '" + CorteConvocatoria.ESTADO_BORRADO
                                + "' order by c.sede.id, c.id");
            }
        }
        if (inicializarCampos) {
            inicializarCamposCorte();
        }
        mostrarAgregarCorte = true;
        setMostrarEditarCorte(false);
    }

    public void insertarArchivo(FileUploadEvent event) {
        UploadedFile archivo = event.getFile();

        insertarArchivoConvocatoriaPadre(convocatoriaPadre.getId().toString(), archivo);

        cargarArchivosConvocatoriaPadre();
    }

    public void cambiarSede() {
        minimoCortePermitido = calcularMinimoCorte();
        numeroCorte = minimoCortePermitido;
    }

    private void inicializarCamposCorte() {
        fechaInicial = null;
        fechaFinal = null;
        sedeId = "";
        minimoCortePermitido = calcularMinimoCorte();
        numeroCorte = minimoCortePermitido;
    }

    private long calcularMinimoCorte() {
        long maximoUsado = 0;
        if (listaCortes != null && StringUtils.isNotEmpty(sedeId)) {
            Iterator<CorteConvocatoria> i = listaCortes.iterator();
            while (i.hasNext()) {
                CorteConvocatoria corteConvocatoria = i.next();
                Long sedeIdLong = Long.parseLong(this.sedeId);
                if (corteConvocatoria.getSede().getId().equals(sedeIdLong) && corteConvocatoria.getNumero() > maximoUsado) {
                    maximoUsado = corteConvocatoria.getNumero();
                }
            }
        }
        return maximoUsado + 1;
    }

    public void agregarCorte() {
        if (validarCorte()) {
            CorteConvocatoria corteConvocatoria = new CorteConvocatoria();
            corteConvocatoria.setConvocatoriaPadre(convocatoriaPadre);
            corteConvocatoria.setFechaInicial(fechaInicial);
            corteConvocatoria.setFechaFinal(fechaFinal);            
            corteConvocatoria.setFechaInicioReclamacion(fechaInicialReclamacionRequisitos);
            corteConvocatoria.setFechaFinalReclamacion(fechaFinalReclamacionRequisitos);
            corteConvocatoria.setFechaInicioReclamacionEvaluacion(fechaInicialReclamacionEvaluacion);
            corteConvocatoria.setFechaFinalReclamacionEvaluacion(fechaFinalReclamacionEvaluacion);
            corteConvocatoria.setFechaPublicacionResultados(fechaPublicacionResultados);            
            corteConvocatoria.setNumero(numeroCorte);
            corteConvocatoria.setSede(obtenerSede(sedeId));
            corteConvocatoria.setDocumento(personaActual.getId().getDocumento());
            corteConvocatoria.setTipoDocumento(personaActual.getId().getTipoDocumento());
            corteConvocatoria.setFechaCreacion(new Date());
            corteConvocatoria.setEstado(CorteConvocatoria.ESTADO_POR_DEFECTO);
            servicioGeneral.guardarObjeto(corteConvocatoria);
            if (corteConvocatoria.getId() != null) {
                inicializarCamposCorte();
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Ha ocurrido un error al guardar el nuevo corte de " + "convocatoria", "");
                context.addMessage("", mensaje);
            }
        }
        cargarCortesConvocatoriaPadre(false);
    }

    public void eliminarCorte() {
        corteSeleccionado.setEstado(CorteConvocatoria.ESTADO_BORRADO);
        corteSeleccionado.setFechaEliminacion(new Date());
        servicioGeneral.guardarObjeto(corteSeleccionado);
        cargarCortesConvocatoriaPadre(false);
    }
    
	public void modificarCorte() {
		try {
			setCorteEdicion((CorteConvocatoria) servicioGeneral
					.obtenerObjetoXID("CorteConvocatoria", corteSeleccionado.getId().toString()).get(0));
			if (getCorteEdicion() == null) {
				FacesContext context = FacesContext.getCurrentInstance();
				FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"No se encuentra el corte de la convocatoria.", "");
				context.addMessage("", mensaje);
			} else {
				setMostrarEditarCorte(true);
				mostrarAgregarCorte = false;
			}
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Ha ocurrido un error al cargar el corte de la convocatoria.", "");
			context.addMessage("", mensaje);
		}
	}
	
	public void actualizarCorte() {
		try {
			servicioGeneral.guardarObjeto(corteEdicion);
			setCorteEdicion(null);
			cargarCortesConvocatoriaPadre(false);
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Se ha actualizado el corte de la convocatoria.", "");
			context.addMessage("", mensaje);
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Ha ocurrido un error al actualizar el corte de la convocatoria.", "");
			context.addMessage("", mensaje);
		}
	}

    private Sede obtenerSede(String sedeId) {
        if (sedes != null) {
            Iterator<Sede> i = sedes.iterator();
            while (i.hasNext()) {
                Sede sede = i.next();
                Long id = Long.parseLong(sedeId);
                if (sede.getId().equals(id)) {
                    return sede;
                }
            }
        }
        return null;
    }

    public boolean validarCorte() {
        boolean valiacionCorrecta = true;
        FacesContext context = FacesContext.getCurrentInstance();
        if (fechaInicial == null) {
            valiacionCorrecta = false;
            FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar la fecha inicial.",
                    "");
            context.addMessage("", mensaje);
        }
        if (fechaFinal == null) {
            valiacionCorrecta = false;
            FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar la fecha final.",
                    "");
            context.addMessage("", mensaje);
        }
        if (valiacionCorrecta && fechaInicial.compareTo(fechaFinal) == 0) {
            valiacionCorrecta = false;
            FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "La fecha final debe ser diferente a la inicial.", "");
            context.addMessage("", mensaje);
        }
        if (StringUtils.isEmpty(sedeId)) {
            valiacionCorrecta = false;
            FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar una sede.", "");
            context.addMessage("", mensaje);
        }
        if (!valiacionCorrecta) {
            FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "El nuevo corte de convocatoria no ha sido guardado.", "");
            context.addMessage("", mensaje);
        }
        return valiacionCorrecta;
    }

    public void consultarDocumentoProceso() {
        ArchivoConvocatoriaPadre archivo = archivoProcesoSeleccionado;
        String path = "HER_ARCHIVO_CONVOCATORIA_PADRE";
        descargarArchivoGenerico(path, archivo.getId().toString(), archivo.getNombre());
    }

    public void guardarDocumentoProceso() {
        ArchivoConvocatoriaPadre archivo = archivoProcesoSeleccionado;
        servicioGeneral.guardarObjeto(archivo);
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "El documento ha sido actualizado", "");
        context.addMessage("datosGuardados", mensaje);
    }

    public void borrarDocumentoProceso() {
        ArchivoConvocatoriaPadre archivo = archivoProcesoSeleccionado;
        archivo.setTipoArchivo("B");
        servicioGeneral.guardarObjeto(archivo);
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "El documento ha sido borrado", "");
        context.addMessage("datosGuardados", mensaje);
        cargarArchivosConvocatoriaPadre();
    }

    private void inicializarListas() {
        listaArchivos = null;
        listaCortes = null;
        mostrarAgregarArchivo = false;
        mostrarAgregarCorte = false;
    }

    private void obtenerConvocatoriaPadreSeleccionada() {
        for (ConvocatoriaPadre cp : listaConvocatoriasPadre) {
            if (cp.getId().toString().equals(idConvocaPadre)) {
                convocatoriaPadre = cp;
            }
        }
    }

    public void cambioFechaInicial() {
        if (fechaFinal != null && fechaFinal.compareTo(fechaInicial) < 0) {
            fechaFinal = fechaInicial;
        }
    }

    private void cargarSedes() {
        sedes = servicioGeneral.obtenerObjetos(Sede.class,
                "from Sede s where s.id not in (" + "'" + Sede.NIVEL_NACIONAL + "')");
    }

    public SelectItem[] getConvocatoriaPadreItem() {
        return convocatoriaPadreItem;
    }

    public String getNombreConvocatoriaBusqueda() {
        return nombreConvocatoriaBusqueda;
    }

    public String getIdConvocaPadre() {
        return idConvocaPadre;
    }

    public void setIdConvocaPadre(String idConvocaPadre) {
        this.idConvocaPadre = idConvocaPadre;
    }

    public ConvocatoriaPadre getConvocatoriaPadre() {
        return convocatoriaPadre;
    }

    public List<ArchivoConvocatoriaPadre> getListaArchivos() {
        return listaArchivos;
    }

    public ArchivoConvocatoriaPadre getArchivoProcesoSeleccionado() {
        return archivoProcesoSeleccionado;
    }

    public void setArchivoProcesoSeleccionado(ArchivoConvocatoriaPadre archivoProcesoSeleccionado) {
        this.archivoProcesoSeleccionado = archivoProcesoSeleccionado;
    }

    public List<CorteConvocatoria> getListaCortes() {
        return listaCortes;
    }

    public void setNombreConvocatoriaBusqueda(String nombreConvocatoriaBusqueda) {
        this.nombreConvocatoriaBusqueda = nombreConvocatoriaBusqueda;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public List<Sede> getSedes() {
        return sedes;
    }

    public String getSedeId() {
        return sedeId;
    }

    public void setSedeId(String sedeId) {
        this.sedeId = sedeId;
    }

    public Long getNumeroCorte() {
        return numeroCorte;
    }

    public void setNumeroCorte(Long numeroCorte) {
        this.numeroCorte = numeroCorte;
    }

    public Long getMinimoCortePermitido() {
        return minimoCortePermitido;
    }

    public boolean isMostrarAgregarArchivo() {
        return mostrarAgregarArchivo;
    }

    public boolean isMostrarAgregarCorte() {
        return mostrarAgregarCorte;
    }

    public CorteConvocatoria getCorteSeleccionado() {
        return corteSeleccionado;
    }

    public void setCorteSeleccionado(CorteConvocatoria corteSeleccionado) {
        this.corteSeleccionado = corteSeleccionado;
    }

	public CorteConvocatoria getCorteEdicion() {
		return corteEdicion;
	}

	public void setCorteEdicion(CorteConvocatoria corteEdicion) {
		this.corteEdicion = corteEdicion;
	}

	public boolean isMostrarEditarCorte() {
		return mostrarEditarCorte;
	}

	public void setMostrarEditarCorte(boolean mostrarEditarCorte) {
		this.mostrarEditarCorte = mostrarEditarCorte;
	}

}
