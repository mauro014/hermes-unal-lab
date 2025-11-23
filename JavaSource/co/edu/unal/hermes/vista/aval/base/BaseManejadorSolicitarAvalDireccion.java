package co.edu.unal.hermes.vista.aval.base;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.ArchivoAval;
import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.modelo.ConvocatoriaExterna;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Parametro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.Reporte;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.utils.ReemplazaAcentos;
import co.edu.unal.hermes.vista.ManejadorBase;

public class BaseManejadorSolicitarAvalDireccion extends ManejadorBase {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    protected Aval aval;
    protected ArchivoAval arCoor;
    protected boolean edicionProyecto = false;

    protected ArchivoAval documentoSeleccionado;

    protected CorreoPlantilla correoActual;
    protected CorreoPlantilla correoFacultad;
    protected List<Reporte> listaplantilla;
    protected SelectItem[] plantillas;
    protected String avalId;

    protected List<Aval> listaAval;
    protected List<Aval> listaAvalQueja;
    protected Investigador investigadorActual;
    protected String nombreInvestigador = "";
    protected String nombreFacultad;

    protected UploadedFile archivoCargar;
    protected UploadedFile archivoCoorCargar;
    protected List<ArchivoAval> listaArchivos;

    protected SelectItem[] categoriaItems = { new SelectItem(new Integer(1), "Pendiente de Aprobación"),
            new SelectItem(new Integer(2), "Aprobado"), new SelectItem(new Integer(3), "No Aprobado"),
            new SelectItem(new Integer(4), "Devolver para correcciones") };
    protected SelectItem[] categoriaItemsRevision = { new SelectItem(new Integer(1), "Pendiente de Aprobación"),
            new SelectItem(new Integer(2), "Aprobado"), new SelectItem(new Integer(3), "No Aprobado"),
            new SelectItem(new Integer(4), "Devolver para correcciones") };
    protected SelectItem[] categoriaItemsCE;
    protected int selItem;
    protected List<Aval> filteredAvales;
    protected SelectItem[] opcionAvalVRIItems = { new SelectItem("", "--"), new SelectItem("N", "No"), new SelectItem("S", "Sí")};
    
    protected UploadedFile archivoDRECargar;
    private boolean formatoIngles;
	private String consecutivo;
	private String nombreInstitucion;
	private String nombreConvocatoria;
	private String nombreProyecto;
	private String nombreRol;

	private String rector;
	protected String cargo = "Director (E)"; // Valor por defecto
	protected boolean encargado;
	private ConvocatoriaExterna convocatoriaExterna;
    
    public BaseManejadorSolicitarAvalDireccion() {

        listaArchivos = new ArrayList<ArchivoAval>();
        aval = new Aval();
        investigadorActual = servicioPersona.obtenerInvestigador(((Persona) sesion.getAttribute("persona")).getId());
        listaplantilla = new ArrayList<Reporte>();
        correoActual = new CorreoPlantilla();
        listaAval = new ArrayList<Aval>();
    }

    public Aval getAval() {
        return aval;
    }

    public void setAval(Aval aval) {
        this.aval = aval;
    }
    
    public void organizarListaRevisionAval(List<Aval> listaAvalAux){
        String valoresParam = "";
        List<Parametro> listaParametro = this.servicioGeneral.obtenerListaObjetosWhere(Parametro.class,
                "WHERE p.nombre = 'T_AVAL' AND p.valor= '" + personaActual.getId().getDocumento() + "'");
        if (!esListaVacia(listaAvalAux)) {
            for (int j = 0; j < listaAvalAux.size(); j++) {
                Aval avalAux = listaAvalAux.get(j);

                if (!esListaVacia(listaParametro)) {
                    Parametro paActual = listaParametro.get(0);
                    valoresParam = paActual.getDescripcion();

                    if (!esCadenaVacia(valoresParam)) {
                        StringTokenizer tokens = new StringTokenizer(valoresParam);
                        while (tokens.hasMoreTokens()) {
                            if (avalAux.getTipo().equals(tokens.nextToken().trim())) {
                                Persona persona2 = servicioPersona.obtenerPersona(
                                        new IdPersona(avalAux.getDocumento(), avalAux.getTipoDocumento()));
                                avalAux.setNombreInvestigador(persona2.getNombreCompletoMinusculas());
                                listaAval.add(avalAux);
                            }
                        }
                    }
                } else {
                    Persona persona2 = servicioPersona
                            .obtenerPersona(new IdPersona(avalAux.getDocumento(), avalAux.getTipoDocumento()));
                    avalAux.setNombreInvestigador(persona2.getNombreCompletoMinusculas());
                    listaAval.add(avalAux);
                }
            }
        }
    }
    
    public void consultarAvalTramitar(){
        FacesContext context = FacesContext.getCurrentInstance();
        borrarManejadoresInsercionProyecto();
        @SuppressWarnings("unchecked")
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        Object o = map.get("idAval");
        Long id = Long.valueOf((String) o);

        List<Aval> lista = servicioGeneral.obtenerAval(id.toString());

        if (!esListaVacia(lista)) {
            aval = lista.get(0);
            
            if(!esCadenaVacia(aval.getAviConvocatoria())) {
            	setConvocatoriaExterna(obtenerConvocatoriaExterna(Long.parseLong(aval.getAviConvocatoria())));
            }else {
            	setConvocatoriaExterna(new ConvocatoriaExterna());
            }
            
            listaArchivos = servicioGeneral.obtenerListaObjetosWhere(ArchivoAval.class,
                    "where a.aval='" + aval.getAviId() + "'");

            List<ArchivoAval> archiCoor = this.servicioGeneral.obtenerListaObjetosWhere(ArchivoAval.class,
                    "where a.avalCoor='" + aval.getAviId() + "'");
            if (!esListaVacia(archiCoor)) {
                for (int i = 0; i < archiCoor.size(); i++) {
                    aval.getArchivosCoor().add(archiCoor.get(i));
                }
            }

            nombreInvestigador = "";
            IdPersona idAux = new IdPersona();
            idAux.setDocumento(aval.getDocumento());
            idAux.setTipoDocumento(aval.getTipoDocumento());
            InvestigadorInterno personaAux = servicioPersona.obtenerInvestigadorInterno(idAux);

            if (personaAux != null) {
                nombreInvestigador = personaAux.getNombreCompletoMinusculas();
                nombreFacultad = personaAux.getDependencia().getFacultad().getNombre();
            }
            
            if(aval.isTieneRecurso()) {
            	if(esNulo(aval.getAvalCEPIRecurso())) {
            		if(aval.getTipoRecursoAvalEtico().getId().equals(Tipos.TIPOS_TIPO_RECURSO_AVAL_ETICO_Reposicion) 
                			|| aval.getTipoRecursoAvalEtico().getId().equals(Tipos.TIPOS_TIPO_RECURSO_AVAL_ETICO_Reposición_Apelación)) {
                		categoriaItemsCE = new SelectItem[] {
                                new SelectItem(new Integer(2), "Aprobado"), 
                                new SelectItem(new Integer(3), "No Aprobado") };
                    	if(aval.getAviEstado().equals(Aval.ETICO_NO_APROBADO_CEPI))
                    		selItem = 3;
                    	else if(aval.getAviEstado().equals(Aval.ETICO_APROBADO_CEPI))
                    		selItem = 2;
                	} else if(aval.getTipoRecursoAvalEtico().getId().equals(Tipos.TIPOS_TIPO_RECURSO_AVAL_ETICO_Apelacion)) {
                		categoriaItemsCE = new SelectItem[] {
                                new SelectItem(new Integer(2), "Procedente"), 
                                new SelectItem(new Integer(3), "No Procedente") };
                	}
            	} else {
            		categoriaItemsCE = new SelectItem[] {
                            new SelectItem(new Integer(2), "Aprobado"), 
                            new SelectItem(new Integer(3), "No Aprobado") };
            	}
            } else {
            	categoriaItemsCE = new SelectItem[] {
                    new SelectItem(new Integer(2), "Aprobado"), 
                    new SelectItem(new Integer(3), "No Aprobado"),
                    new SelectItem(new Integer(4), "Devolver para correcciones") };
            }
        }
    }
    
    public String convertirDuracionMeses(String tipoDuracion, int duracion) {
        String duracionMeses = "0";
        if(esCadenaVacia(tipoDuracion)){
            tipoDuracion = Proyecto.DURACION_MESES;
        }
        
        if (tipoDuracion.equals(Proyecto.DURACION_MESES)) {
            duracionMeses = String.valueOf(duracion);
        } else if (tipoDuracion.equals(Proyecto.DURACION_ANOS)) {
            duracionMeses = String.valueOf(duracion * 12);
        } else if (tipoDuracion.equals(Proyecto.DURACION_SEMANAS)) {
            duracionMeses = String.valueOf(duracion / 4);
        } else if (tipoDuracion.equals(Proyecto.DURACION_DIAS)) {
            duracionMeses = String.valueOf(duracion / 30);
        } else if (tipoDuracion.equals(Proyecto.DURACION_HORAS)) {
            duracionMeses = String.valueOf(duracion / 720);
        }
        return duracionMeses;
    }
    
    public void guardarArchivo(FileUploadEvent event) {
        archivoCargar = event.getFile();
        cargarArchivoDisco(archivoCargar, "HER_AVAL", aval.getAviId() + "");
        int i = archivoCargar.getFileName().lastIndexOf("\\");
        this.aval.setArchivoAval(archivoCargar.getFileName().substring(i + 1));
        this.aval.setArchivoAval(ReemplazaAcentos.quitarTildes(this.aval.getArchivoAval()));
    }

    public void guardarArchivoCoor(FileUploadEvent event) {
        archivoCoorCargar = event.getFile();
        int i = archivoCoorCargar.getFileName().lastIndexOf("\\");
        ArchivoAval aa = insertarArchivoAvalGenerico(aval.getAviId(), archivoCoorCargar, true, "175");
        aval.getArchivosCoor().add(aa);
    }
    
    public void guardarArchivoDRE(FileUploadEvent event) {
        archivoDRECargar = event.getFile();
        int i = archivoDRECargar.getFileName().lastIndexOf("\\");
        ArchivoAval aa = insertarArchivoAvalGenerico(aval.getAviId(), archivoCoorCargar, true, "174");
        aval.getArchivosCoor().add(aa);
    }
	
	public void guardarArchivoCoorRectoria(FileUploadEvent event) {
        archivoCoorCargar = event.getFile();
        int i = archivoCoorCargar.getFileName().lastIndexOf("\\");
        ArchivoAval aa = insertarArchivoAvalGenerico(aval.getAviId(), archivoCoorCargar, true, "175");
        aval.getArchivosCoor().add(aa);
    }

    public void eliminarArchivoCoor() {
        aval.getArchivosCoor().remove(arCoor);
        arCoor.setAvalCoor(0L);
        arCoor.setFechaBorrado(new Date());

        Persona personaElimina = (Persona) sesion.getAttribute("persona");
        if (arCoor.getDescripcion() != null) {
            arCoor.setDescripcion(arCoor.getDescripcion() + " - Eliminado por " + personaElimina.getId().getDocumento()
                    + "-" + personaElimina.getId().getTipoDocumento());
            if (aval.getAviId() != null) {
                arCoor.setDescripcion(arCoor.getDescripcion() + "- Aval previo = " + aval.getAviId());
            }
        } else {
            arCoor.setDescripcion("Eliminado por " + personaElimina.getId().getDocumento() + "-"
                    + personaElimina.getId().getTipoDocumento());
            if (aval.getAviId() != null) {
                arCoor.setDescripcion(arCoor.getDescripcion() + "- Aval previo = " + aval.getAviId());
            }
        }
        servicioGeneral.guardarObjeto(arCoor);
    }

    public void imprimirGrupo() {
        String id = aval.getNombreGrupoPINV();
        imprimirReporteFormatoGrupo(id);
    }

    public void imprimirAval() {
        if (aval != null) {
            servicioAval.imprimirReporteAval(aval.getAviId(), sesion);
        }
    }

    public void imprimirReporteRelacionado() throws SQLException {
        List<Aval> listaAvalesRelacionados = servicioGeneral.obtenerObjetosLimitado(Aval.class,
                "select #aviId a.aviId, #tipo a.tipo from Aval a where " + "a.aviId = '" + aval.getAvalRelacionado()
                        + "' order by a.aviId asc");
        if (listaAvalesRelacionados != null) {
            servicioAval.imprimirReporteAval(listaAvalesRelacionados.get(0).getAviId(), sesion);
        }
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
    
    public String obtenerNombreCarta(String avalId) {
        for(int i = 0; i < listaplantilla.size() ; i++){
            if(listaplantilla.get(i).getId().toString().equals(avalId)){
                return listaplantilla.get(i).getRuta();
            }
        }
        return "";
    }
    
    private boolean validarFormatoStd() {
		boolean datosValidos = true;
		if (esCadenaVacia(consecutivo)) {
			mensajeErrorFormato(formatoIngles ? "Por favor, indique el consecutivo de rectoria asociado al formato."
					: "Por favor, indique el consecutivo del formato.");
			datosValidos = false;
		}
		if (formatoIngles) {
			if (esCadenaVacia(nombreInstitucion)) {
				mensajeErrorFormato("Por favor, indique el nombre de la institución a colocar en el formato.");
				datosValidos = false;
			}
			if (esCadenaVacia(nombreConvocatoria)) {
				mensajeErrorFormato("Por favor, indique el nombre de la convocatoria a colocar en el formato.");
				datosValidos = false;
			}
			if (esCadenaVacia(nombreProyecto)) {
				mensajeErrorFormato("Por favor, indique el nombre del proyecto a colocar en el formato.");
				datosValidos = false;
			}
			if (esCadenaVacia(nombreRol)) {
				mensajeErrorFormato("Por favor, indique el rol de la unviersidad a colocar en el formato.");
				datosValidos = false;
			}
		}
		if(encargado) {
			if(esCadenaVacia(rector)) {
				mensajeErrorFormato("Por favor, indique el nombre del Rector(a) encargado(a)");
				datosValidos = false;
			}
		}
		return datosValidos;
	}
    
	public void generarFormatoStd() {
		if (validarFormatoStd()) {
			ReporteBirt r = new ReporteBirt();
			r.setNombreReporte("/aval/formatoEstandarAvalInternacional");
			r.setFormato(ReporteBirt.FORMATO_PDF);
			r.adicionarParametro("codigo", aval.getAviId().toString());
			r.adicionarParametro("consecutivo", consecutivo);
			r.adicionarParametro("elaboro", personaActual.getId().getDocumento());
			r.adicionarParametro("formatoIngles", String.valueOf(formatoIngles));
			if (formatoIngles) {
				r.adicionarParametro("institucion", nombreInstitucion);
				r.adicionarParametro("convocatoria", nombreConvocatoria);
				r.adicionarParametro("proyecto", nombreProyecto);
				r.adicionarParametro("rol", nombreRol);
			}
			if(encargado) {
				List <Parametro> encargado = servicioGeneral.obtenerObjetos(Parametro.class, "select p from Parametro p where "
						+ "p.nombre = 'TEMPORAL_CARTAS_AVAL'");				
				encargado.get(0).setValor(rector);
				servicioGeneral.guardarObjeto(encargado.get(0));
				
				r.adicionarParametro("cargo", cargo);
				r.adicionarParametro("rector", rector);
			}else {
				r.adicionarParametro("cargo", "N");
				r.adicionarParametro("rector", "N");
			}
			sesion.setAttribute("reporte", r);
			FacesContext context = FacesContext.getCurrentInstance();
			r.run(context);
		} else {
			return;
		}
	}
	
	public void mensajeErrorFormato(String mensaje) {
		FacesContext.getCurrentInstance().addMessage("msgFormato",
				new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, null));
	}

    public CorreoPlantilla getCorreoActual() {
        return correoActual;
    }

    public void setCorreoActual(CorreoPlantilla correoActual) {
        this.correoActual = correoActual;
    }

    public List<Reporte> getListaplantilla() {
        return listaplantilla;
    }

    public void setListaplantilla(List<Reporte> listaplantilla) {
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

    public List<Aval> getListaAval() {
        return listaAval;
    }

    public void setListaAval(List<Aval> listaAval) {
        this.listaAval = listaAval;
    }

    public Investigador getInvestigadorActual() {
        return investigadorActual;
    }

    public void setInvestigadorActual(Investigador investigadorActual) {
        this.investigadorActual = investigadorActual;
    }

    public String getNombreInvestigador() {
        return nombreInvestigador;
    }

    public void setNombreInvestigador(String nombreInvestigador) {
        this.nombreInvestigador = nombreInvestigador;
    }

    public UploadedFile getArchivoCargar() {
        return archivoCargar;
    }

    public void setArchivoCargar(UploadedFile archivoCargar) {
        this.archivoCargar = archivoCargar;
    }

    public List<ArchivoAval> getListaArchivos() {
        return listaArchivos;
    }

    public void setListaArchivos(List<ArchivoAval> listaArchivos) {
        this.listaArchivos = listaArchivos;
    }
    
    public SelectItem[] getCategoriaItems() {
        return categoriaItems;
    }

    public void setCategoriaItems(SelectItem[] categoriaItems) {
        this.categoriaItems = categoriaItems;
    }

    public int getSelItem() {
        return selItem;
    }

    public void setSelItem(int selItem) {
        this.selItem = selItem;
    }
    
    public boolean isRechazado() {
        return selItem == 3;
    }

    public boolean isDevuelto() {
        return selItem == 4;
    }

    public UploadedFile getArchivoCoorCargar() {
        return archivoCoorCargar;
    }

    public void setArchivoCoorCargar(UploadedFile archivoCoorCargar) {
        this.archivoCoorCargar = archivoCoorCargar;
    }

    public ArchivoAval getDocumentoSeleccionado() {
        return documentoSeleccionado;
    }

    public void setDocumentoSeleccionado(ArchivoAval documentoSeleccionado) {
        this.documentoSeleccionado = documentoSeleccionado;
    }

    public SelectItem[] getCategoriaItemsRevision() {
        return categoriaItemsRevision;
    }

    public void setCategoriaItemsRevision(SelectItem[] categoriaItemsRevision) {
        this.categoriaItemsRevision = categoriaItemsRevision;
    }

    public SelectItem[] getcategoriaItemsCE() {
		return categoriaItemsCE;
	}

	public void setcategoriaItemsCE(SelectItem[] categoriaItemsCE) {
		this.categoriaItemsCE = categoriaItemsCE;
	}

	public CorreoPlantilla getCorreoFacultad() {
        return correoFacultad;
    }

    public void setCorreoFacultad(CorreoPlantilla correoFacultad) {
        this.correoFacultad = correoFacultad;
    }

    public ArchivoAval getArCoor() {
        return arCoor;
    }

    public void setArCoor(ArchivoAval arCoor) {
        this.arCoor = arCoor;
    }

    public boolean isEdicionProyecto() {
        return edicionProyecto;
    }

    public void setEdicionProyecto(boolean edicionProyecto) {
        this.edicionProyecto = edicionProyecto;
    }

    public List<Aval> getFilteredAvales() {
        return filteredAvales;
    }

    public void setFilteredAvales(List<Aval> filteredAvales) {
        this.filteredAvales = filteredAvales;
    }

    public String getNombreFacultad() {
        return nombreFacultad;
    }

    public void setNombreFacultad(String nombreFacultad) {
        this.nombreFacultad = nombreFacultad;
    }

	public SelectItem[] getOpcionAvalVRIItems() {
		return opcionAvalVRIItems;
	}

	public void setOpcionAvalVRIItems(SelectItem[] opcionAvalVRIItems) {
		this.opcionAvalVRIItems = opcionAvalVRIItems;
	}
	
	public Boolean getEsAvalInternacional() {
		List<ConvocatoriaExterna> convocaExt = servicioGeneral.obtenerObjetoXID(ConvocatoriaExterna.class,
				aval.getAviConvocatoria());
		if (!esListaVacia(convocaExt)) {
			return convocaExt.get(0).getNaturaleza() != null && convocaExt.get(0).getNaturaleza().getId().equals(376L);
		} else {
			return false;
		}
	}

	public List<Aval> getListaAvalQueja() {
		return listaAvalQueja;
	}

	public void setListaAvalQueja(List<Aval> listaAvalQueja) {
		this.listaAvalQueja = listaAvalQueja;
	}
	
	public UploadedFile getArchivoDRECargar() {
		return archivoDRECargar;
	}

	public void setArchivoDRECargar(UploadedFile archivoDRECargar) {
		this.archivoDRECargar = archivoDRECargar;
	}

	public boolean isFormatoIngles() {
		return formatoIngles;
	}

	public void setFormatoIngles(boolean formatoIngles) {
		this.formatoIngles = formatoIngles;
	}

	public String getConsecutivo() {
		return consecutivo;
	}

	public void setConsecutivo(String consecutivo) {
		this.consecutivo = consecutivo;
	}

	public String getNombreInstitucion() {
		return nombreInstitucion;
	}

	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}

	public String getNombreConvocatoria() {
		return nombreConvocatoria;
	}

	public void setNombreConvocatoria(String nombreConvocatoria) {
		this.nombreConvocatoria = nombreConvocatoria;
	}

	public String getNombreProyecto() {
		return nombreProyecto;
	}

	public void setNombreProyecto(String nombreProyecto) {
		this.nombreProyecto = nombreProyecto;
	}

	public String getNombreRol() {
		return nombreRol;
	}

	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}

	public String getRector() {
		return rector;
	}

	public void setRector(String rector) {
		this.rector = rector;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public boolean isEncargado() {
		return encargado;
	}

	public void setEncargado(boolean encargado) {
		this.encargado = encargado;
	}

	public ConvocatoriaExterna getConvocatoriaExterna() {
		return convocatoriaExterna;
	}

	public void setConvocatoriaExterna(ConvocatoriaExterna convocatoriaExterna) {
		this.convocatoriaExterna = convocatoriaExterna;
	}
}
