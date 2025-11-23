package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Coleccion implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // Estados de la coleccion

    public static final Long DOMINIO_ESTADO_COLECCION = 133L;

    public static final String EN_PROCESO_ACT = "E";
    public static final String DEVUELTA_CORRECCIONES = "C";
    public static final String RECONOCIDA_UN = "A";
    public static final String REGISTRADA = "R";
    public static final String REGISTRADA_ACTUALIZADA = "AR";
    public static final String PROCESO_AMNISTIA = "AM";
    public static final String BORRADA = "B";
    public static final String ENVIADA_VRI = "EV";
    public static final String INGRESANDO = "I";
    public static final String INACTIVA = "IN";
    public static final String PROPUESTA = "P";

    // Atributos de la clase Coleccion, correspondientes a la tabla
    // HER_COLECCION

    private Long id;

    private String nombre;
    private String acronimo;
    private String tipo;
    private String nombreOtroTipo;
    private Sede sede;
    private Dependencia dependencia;
    private Ciudad ciudad;
    private Date fechaFundacion;
    private String sitioWeb;
    private String descripcion;
    private Persona curadorGeneral;
    private String direccion;
    private String telefono;
    private String email;
    private String imagen;
    private String infraestructura;
    private String baseDatos;
    private String coberturaTaxonomica;
    private String coberturaGeografica;
    private String coberturaTemporal;
    private String uab;
    private Date fechaActualizacion;
    private long cantidadEjemplaresTipo;
    private String porcentajeEspeGeo;
    private String sistematizacionPublicacion;
    private String porcentajeEspeGeoNoBio;
    private String sistematizacionPublicacionNoBio;
    private String estado;
    private String estadoEntidadExterna;
    private String historicoCuradores = "";
    private String registroHumboldt; // S: Si N: No
    private Date fechaUltimoRegistroHumboldt;
    private Date fechaProximoRegistro;
    private Long controlAlertas;
    
    private String tipoActoCreacion;
    private String numeroActo;
    private Date fechaActoCreacion;
    private boolean soloConsulta;

    private Set<ColeccionNomenclatura> tiposNomenclaturales = new HashSet<ColeccionNomenclatura>();
    private Set<ColeccionAutoridadCompetente> autoridades = new HashSet<ColeccionAutoridadCompetente>();
    private Set<ColeccionClasificacion> clasificaciones = new HashSet<ColeccionClasificacion>();
    private Set<ColeccionTipoObjeto> tipoObjetos = new HashSet<ColeccionTipoObjeto>();
    private Set<ColeccionGestion> gestiones = new HashSet<ColeccionGestion>();
    
    private String observaciones;
    private String linkPortalColeccion;
    private Date fechaCompromisoActualizacion;


    public void adicionarTipoNomenclatural(ColeccionNomenclatura nom) {
        nom.setColeccion(this);
        tiposNomenclaturales.add(nom);
    }

    public void borrarTipoNomenclatural(ColeccionNomenclatura nom) {
        tiposNomenclaturales.remove(nom);
    }

    public ArrayList<ColeccionNomenclatura> getListaTiposNomenclaturales() {
        ArrayList<ColeccionNomenclatura> listaTiposNomenclaturales = new ArrayList<ColeccionNomenclatura>();
        listaTiposNomenclaturales.addAll(tiposNomenclaturales);
        return listaTiposNomenclaturales;
    }

    private Set<ColeccionTipoPreservacion> tiposPreservacion = new HashSet<ColeccionTipoPreservacion>();

    public void adicionarRegion(ColeccionTipoPreservacion tp) {
        tp.setColeccion(this);
        tiposPreservacion.add(tp);
    }
    
    public void adicionarClasificacion(ColeccionClasificacion c) {
        c.setColeccion(this);
        clasificaciones.add(c);
    }
    
    public void borrarClasificacion(ColeccionClasificacion ca) {
    	clasificaciones.remove(ca);
    }
    
    public void adicionarTipoObjetos(ColeccionTipoObjeto c) {
        c.setColeccion(this);
        c.setFechaAgregado(new Date());
        tipoObjetos.add(c);
    }
    
    public void borrarTipoObjetos(ColeccionTipoObjeto ca) {
    	tipoObjetos.remove(ca);
    }
    
    public void adicionarAutoridad(ColeccionAutoridadCompetente ca) {
        ca.setIdColeccion(this.id);
        autoridades.add(ca);
    }
    
    public void borrarAutoridad(ColeccionAutoridadCompetente ca) {
    	autoridades.remove(ca);
    }
    
    public ArrayList<ColeccionTipoObjeto> getListaTiposObjetos() {
        ArrayList<ColeccionTipoObjeto> listaTipoObjetos = new ArrayList<ColeccionTipoObjeto>();
        listaTipoObjetos.addAll(tipoObjetos);
        return listaTipoObjetos;
    }

    public ArrayList<ColeccionAutoridadCompetente> getListaAutoridades() {
        ArrayList<ColeccionAutoridadCompetente> listaAutoridades = new ArrayList<ColeccionAutoridadCompetente>();
        listaAutoridades.addAll(autoridades);
        return listaAutoridades;
    }
    
    public void adicionarGestion(ColeccionGestion ca) {
        ca.setIdColeccion(this.id);
        gestiones.add(ca);
    }
    
    public void borrarGestion(ColeccionGestion ca) {
    	gestiones.remove(ca);
    }
    
    public ArrayList<ColeccionGestion> getListaGestiones() {
        ArrayList<ColeccionGestion> lista = new ArrayList<ColeccionGestion>();
        lista.addAll(gestiones);
        return lista;
    }

    public Set<ColeccionTipoPreservacion> getTiposPreservacion() {
        return tiposPreservacion;
    }

    public void setTiposPreservacion(Set<ColeccionTipoPreservacion> tiposPreservacion) {
        this.tiposPreservacion = tiposPreservacion;
    }

    public void borrarRegion(ColeccionTipoPreservacion tp) {
        tiposPreservacion.remove(tp);
    }

    public ArrayList<ColeccionTipoPreservacion> getListaTiposPreservacion() {
        ArrayList<ColeccionTipoPreservacion> listaTiposPreservacion = new ArrayList<ColeccionTipoPreservacion>();
        listaTiposPreservacion.addAll(tiposPreservacion);
        return listaTiposPreservacion;
    }
    
    public ArrayList<ColeccionClasificacion> getListaClasificacion() {
        ArrayList<ColeccionClasificacion> listaClasificacion = new ArrayList<ColeccionClasificacion>();
        listaClasificacion.addAll(clasificaciones);
        return listaClasificacion;
    }

    private Set<ColeccionCatalogacion> catalogacionColeccion = new HashSet<ColeccionCatalogacion>();

    public void adicionarCatalogacion(ColeccionCatalogacion cat) {
        cat.setColeccion(this);
        catalogacionColeccion.add(cat);
    }

    public void borrarCatalogacion(ColeccionCatalogacion cat) {
        catalogacionColeccion.remove(cat);
    }

    public ArrayList<ColeccionCatalogacion> getListaCatalogacionColeccionCompleta() {
        ArrayList<ColeccionCatalogacion> listaCatalotacionColeccion = new ArrayList<ColeccionCatalogacion>();
        listaCatalotacionColeccion.addAll(catalogacionColeccion);
        return listaCatalotacionColeccion;
    }
    
    public ArrayList<ColeccionCatalogacion> getListaCatalogacionColeccionNoBio() {
        ArrayList<ColeccionCatalogacion> listaCatalotacionColeccionNoBio = new ArrayList<ColeccionCatalogacion>();
        if (catalogacionColeccion != null) {
            Iterator<ColeccionCatalogacion> i = catalogacionColeccion.iterator();
            while (i.hasNext()) {
            	ColeccionCatalogacion cc = i.next();
                if (cc.getGrupoNoBiologico() != null && cc.getGrupoNoBiologico()  != "") {
                	listaCatalotacionColeccionNoBio.add(cc);
                }
            }
        }
        return listaCatalotacionColeccionNoBio;
    }
    
    public ArrayList<ColeccionCatalogacion> getListaCatalogacionColeccionBio() {
        ArrayList<ColeccionCatalogacion> listaCatalotacionColeccionBio = new ArrayList<ColeccionCatalogacion>();
        if (catalogacionColeccion != null) {
            Iterator<ColeccionCatalogacion> i = catalogacionColeccion.iterator();
            while (i.hasNext()) {
            	ColeccionCatalogacion cc = i.next();
                if (cc.getGrupoBiologico() != null && cc.getGrupoBiologico().getIdentificador() != null) {
                	listaCatalotacionColeccionBio.add(cc);
                }
            }
        }
        return listaCatalotacionColeccionBio;
    }

    private Set<ColeccionPersona> personalColeccion = new HashSet<ColeccionPersona>();

    public void adicionarPersona(ColeccionPersona per) {
        per.setColeccion(this);
        personalColeccion.add(per);
    }

    public void borrarPersona(ColeccionPersona per) {
        personalColeccion.remove(per);
    }

    public ArrayList<ColeccionPersona> getListaPersonal() {
        ArrayList<ColeccionPersona> listaPersonal = new ArrayList<ColeccionPersona>();
        listaPersonal.addAll(personalColeccion);
        return listaPersonal;
    }

    public String getNombreEstado() {
        String nombre = "";
        if (this.estado != null) {
            if (this.estado.equals(EN_PROCESO_ACT)) {
                nombre = "En proceso de actualización en el IAvH";
            } else if (this.estado.equals(DEVUELTA_CORRECCIONES)) {
                nombre = "Devuelta para correcciones";
            } else if (this.estado.equals(RECONOCIDA_UN)) {
                nombre = "Activa";
            } else if (this.estado.equals(REGISTRADA)) {
                nombre = "Registrada ante el IAvH";
            } else if (this.estado.equals(REGISTRADA_ACTUALIZADA)) {
                nombre = "Registrada y actualizada ante el IAvH";
            } else if (this.estado.equals(PROCESO_AMNISTIA)) {
                nombre = "En proceso de Amnistía ante el IAvH";
            } else if (this.estado.equals(BORRADA)) {
                nombre = "Borrada";
            } else if (this.estado.equals(INGRESANDO)) {
                nombre = "Ingresando";
            } else if (this.estado.equals(PROPUESTA)) {
                nombre = "Propuesta";
            } else if (this.estado.equals(ENVIADA_VRI)) {
                nombre = "Enviada para revisión de la VRI";
            } else if (this.estado.equals(INACTIVA)) {
                nombre = "Inactiva";
            }
        }
        return nombre;
    }
    
    public String getNombreEstadoEntidadExterna() {
        String nombre = "";
        if (this.estado != null) {
            if (this.estado.equals(EN_PROCESO_ACT)) {
                nombre = "En proceso de actualización en el IAvH";
            } else if (this.estado.equals(DEVUELTA_CORRECCIONES)) {
                nombre = "Devuelta para correcciones";
            } else if (this.estado.equals(RECONOCIDA_UN)) {
                nombre = "Reconocida Universidad";
            } else if (this.estado.equals(REGISTRADA)) {
                nombre = "Registrada ante el IAvH";
            } else if (this.estado.equals(REGISTRADA_ACTUALIZADA)) {
                nombre = "Registrada y actualizada ante el IAvH";
            } else if (this.estado.equals(PROCESO_AMNISTIA)) {
                nombre = "En proceso de Amnistía ante el IAvH";
            } else if (this.estado.equals(BORRADA)) {
                nombre = "Borrada";
            }
        }
        return nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAcronimo() {
        return acronimo;
    }

    public void setAcronimo(String acronimo) {
        this.acronimo = acronimo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public Dependencia getDependencia() {
        return dependencia;
    }

    public void setDependencia(Dependencia dependencia) {
        this.dependencia = dependencia;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public Date getFechaFundacion() {
        return fechaFundacion;
    }

    public void setFechaFundacion(Date fechaFundacion) {
        this.fechaFundacion = fechaFundacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Persona getCuradorGeneral() {
        Iterator it = getPersonalColeccion().iterator();
        while (it.hasNext()) {
            ColeccionPersona curador = (ColeccionPersona) it.next();
            if (curador.getTipoPersona().equals(ColeccionPersona.CURADOR_GENERAL)) {
                this.curadorGeneral = curador.getPersona();
                return curadorGeneral;
            }
        }
        return null;
    }

    public void setCuradorGeneral(Persona curadorGeneral) {
        this.curadorGeneral = curadorGeneral;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getImagen() {
        return imagen;
    }

    public void setRegiones(Set<ColeccionTipoPreservacion> regiones) {
        this.tiposPreservacion = regiones;
    }

    public Set<ColeccionTipoPreservacion> getRegiones() {
        return tiposPreservacion;
    }

    public void setInfraestructura(String infraestructura) {
        this.infraestructura = infraestructura;
    }

    public String getInfraestructura() {
        return infraestructura;
    }

    public void setTiposNomenclaturales(Set<ColeccionNomenclatura> tiposNomenclaturales) {
        this.tiposNomenclaturales = tiposNomenclaturales;
    }

    public Set<ColeccionNomenclatura> getTiposNomenclaturales() {
        return tiposNomenclaturales;
    }

    public void setBaseDatos(String baseDatos) {
        this.baseDatos = baseDatos;
    }

    public String getBaseDatos() {
        return baseDatos;
    }

    public void setCoberturaTaxonomica(String coberturaTaxonomica) {
        this.coberturaTaxonomica = coberturaTaxonomica;
    }

    public String getCoberturaTaxonomica() {
        return coberturaTaxonomica;
    }

    public String getUab() {
        return uab;
    }

    public void setUab(String uab) {
        this.uab = uab;
    }

    public String getCoberturaGeografica() {
        return coberturaGeografica;
    }

    public void setCoberturaGeografica(String coberturaGeografica) {
        this.coberturaGeografica = coberturaGeografica;
    }

    public String getCoberturaTemporal() {
        return coberturaTemporal;
    }

    public void setCoberturaTemporal(String coberturaTemporal) {
        this.coberturaTemporal = coberturaTemporal;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }


    public String getSistematizacionPublicacion() {
        return sistematizacionPublicacion;
    }

    public void setSistematizacionPublicacion(String sistematizacionPublicacion) {
        this.sistematizacionPublicacion = sistematizacionPublicacion;
    }

    public String getPorcentajeEspeGeo() {
        return porcentajeEspeGeo;
    }

    public void setPorcentajeEspeGeo(String porcentajeEspeGeo) {
        this.porcentajeEspeGeo = porcentajeEspeGeo;
    }

    public Set<ColeccionCatalogacion> getCatalogacionColeccion() {
        return catalogacionColeccion;
    }

    public void setCatalogacionColeccion(Set<ColeccionCatalogacion> catalogacionColeccion) {
        this.catalogacionColeccion = catalogacionColeccion;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getHistoricoCuradores() {
        return historicoCuradores;
    }

    public void setHistoricoCuradores(String historicoCuradores) {
        this.historicoCuradores = historicoCuradores;
    }

    public String getRegistroHumboldt() {
        return registroHumboldt;
    }

    public void setRegistroHumboldt(String registroHumboldt) {
        this.registroHumboldt = registroHumboldt;
    }

    public Date getFechaUltimoRegistroHumboldt() {
        return fechaUltimoRegistroHumboldt;
    }

    public void setFechaUltimoRegistroHumboldt(Date fechaUltimoRegistroHumboldt) {
        this.fechaUltimoRegistroHumboldt = fechaUltimoRegistroHumboldt;
    }

    public Date getFechaProximoRegistro() {
        if (fechaUltimoRegistroHumboldt != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(fechaUltimoRegistroHumboldt);
            c.add(Calendar.YEAR, 2);
            fechaProximoRegistro = c.getTime();
        }
        return fechaProximoRegistro;
    }
    
    public int getDiasRestantesActualizacion(){
        int dias = 0;
        if (this.getFechaProximoRegistro() != null){
            dias = (int) ((this.getFechaProximoRegistro().getTime() - new Date().getTime()) / (24 * 60 * 60 * 1000));
        }
        return dias;
    }

    public void setFechaProximoRegistro(Date fechaProximoRegistro) {
        this.fechaProximoRegistro = fechaProximoRegistro;
    }

    public Set<ColeccionPersona> getPersonalColeccion() {
        return personalColeccion;
    }

    public void setPersonalColeccion(Set<ColeccionPersona> personalColeccion) {
        this.personalColeccion = personalColeccion;
    }

    public Long getControlAlertas() {
        if (controlAlertas == null) {
            return 0L;
        } else {
            return controlAlertas;
        }
    }

    public void setControlAlertas(Long controlAlertas) {
        this.controlAlertas = controlAlertas;
    }

	public String getEstadoEntidadExterna() {
		return estadoEntidadExterna;
	}

	public void setEstadoEntidadExterna(String estadoEntidadExterna) {
		this.estadoEntidadExterna = estadoEntidadExterna;
	}

	public Set<ColeccionAutoridadCompetente> getAutoridades() {
		return autoridades;
	}

	public void setAutoridades(Set<ColeccionAutoridadCompetente> autoridades) {
		this.autoridades = autoridades;
	}

	public String getNombreOtroTipo() {
		return nombreOtroTipo;
	}

	public void setNombreOtroTipo(String nombreOtroTipo) {
		this.nombreOtroTipo = nombreOtroTipo;
	}

	public Set<ColeccionClasificacion> getClasificaciones() {
		return clasificaciones;
	}

	public void setClasificaciones(Set<ColeccionClasificacion> clasificaciones) {
		this.clasificaciones = clasificaciones;
	}

	public Set<ColeccionTipoObjeto> getTipoObjetos() {
		return tipoObjetos;
	}

	public void setTipoObjetos(Set<ColeccionTipoObjeto> tipoObjetos) {
		this.tipoObjetos = tipoObjetos;
	}

	public String getTipoActoCreacion() {
		return tipoActoCreacion;
	}

	public void setTipoActoCreacion(String tipoActoCreacion) {
		this.tipoActoCreacion = tipoActoCreacion;
	}

	public String getNumeroActo() {
		return numeroActo;
	}

	public void setNumeroActo(String numeroActo) {
		this.numeroActo = numeroActo;
	}

	public Date getFechaActoCreacion() {
		return fechaActoCreacion;
	}

	public void setFechaActoCreacion(Date fechaActoCreacion) {
		this.fechaActoCreacion = fechaActoCreacion;
	}

	public boolean isSoloConsulta() {
		soloConsulta = false;
		if(estado!=null) {
			if(estado.equals(ENVIADA_VRI)) {
				soloConsulta=true;
			}
		}
		return soloConsulta;
	}

	public void setSoloConsulta(boolean soloConsulta) {
		this.soloConsulta = soloConsulta;
	}

	public String getPorcentajeEspeGeoNoBio() {
		return porcentajeEspeGeoNoBio;
	}

	public void setPorcentajeEspeGeoNoBio(String porcentajeEspeGeoNoBio) {
		this.porcentajeEspeGeoNoBio = porcentajeEspeGeoNoBio;
	}

	public String getSistematizacionPublicacionNoBio() {
		return sistematizacionPublicacionNoBio;
	}

	public void setSistematizacionPublicacionNoBio(String sistematizacionPublicacionNoBio) {
		this.sistematizacionPublicacionNoBio = sistematizacionPublicacionNoBio;
	}

	public long getCantidadEjemplaresTipo() {
		return cantidadEjemplaresTipo;
	}

	public void setCantidadEjemplaresTipo(long cantidadEjemplaresTipo) {
		this.cantidadEjemplaresTipo = cantidadEjemplaresTipo;
	}

	public Set<ColeccionGestion> getGestiones() {
		return gestiones;
	}

	public void setGestiones(Set<ColeccionGestion> gestiones) {
		this.gestiones = gestiones;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getLinkPortalColeccion() {
		return linkPortalColeccion;
	}

	public void setLinkPortalColeccion(String linkPortalColeccion) {
		this.linkPortalColeccion = linkPortalColeccion;
	}

	public Date getFechaCompromisoActualizacion() {
		return fechaCompromisoActualizacion;
	}

	public void setFechaCompromisoActualizacion(Date fechaCompromisoActualizacion) {
		this.fechaCompromisoActualizacion = fechaCompromisoActualizacion;
	}

}
