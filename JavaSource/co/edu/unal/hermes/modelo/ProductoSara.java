package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;

public class ProductoSara implements Serializable, Comparable<ProductoSara> {

	private static final long serialVersionUID = -4280947934566691610L;
	private Long id;
	
	
	private String llave;
	private Integer idInvestigador;
	private Integer nroAutores;
	private String nombreProducto;
	private String titulo1;
	private String titulo2;
	private String institucion;
	private String pais;
	private String departamento;
	private String municipio;
	private String areaConocimiento;
	private String idioma;
	private String nombreSeccion;
	
	private String origen;
	private String codSeccion;
	private String codCategoriaHV;
	private String nombreCategoriaHV;
	private Integer codigoEmpleado;
	private String nombresYApellidos;
	private Date fechaFinal;
	private String codRevista;
	private String nomRevista;
	private Integer paginaInicial;
	private Integer paginaFinal;
	private String isbn;
	private Integer nroOficioDoc;
	private Integer codInstitucion;
	private String codPais;
	private String codDepto;
	private String codMunicipio;
	private Integer horasSemana;
	private Integer horasSemestre;
	private Integer horasTotales;
	private Integer horasCertificadas;
	private String codAreaConocimiento;
	private String codIdioma;
	private String codNuevoIdioma;
	private String nuevoIdioma;
	private String tipoDocumento;
	private String tipoPublicacion;
	private String tipoProduccion;
	private String tipoMaterial;
	private String medioDifusion;
	private String medioEdicion;
	private String modalidadMemoria;
	private String caracter;
	private String distincion;
	private String participacion;
	private String tipoPatente;
	private String facultadSara;
	private String motivoReconocimiento;
	private String tipoLista;
	private String entidad;
	private String entidadFinancia;
	private String lugar;
	private String tiraje;
	private String volumen;
	private String duracion;
	private String objetivo;
    private String productoResultadoEsperado;
    private String productoCompletado;
    private String motivoNoEntrega;
    private String linkPublico;

    private Date fechaEntrega;
    private String objetivoDesarrolloSost;
    private String nombreOds; // No mapeada, pero se usa para la vista
    private String areaOCDEppal;
    private String nombreOcde; // No mapeada, pero se usa para la vista
    private String lugarDeposito;
    private String susceptibleProteccion;
	private ProyectoInforme informe;

	public ProductoSara() {
		super();
		this.id = null;
	}

	public ProductoSara(Long id) {
		super();
		this.id = id;
	}

	public String getLlave() {
		return llave;
	}

	public void setLlave(String llave) {
		this.llave = llave;
	}

	public Integer getIdInvestigador() {
		return idInvestigador;
	}

	public void setIdInvestigador(Integer idInvestigador) {
		this.idInvestigador = idInvestigador;
	}

	public Integer getNroAutores() {
		return nroAutores;
	}

	public void setNroAutores(Integer nroAutores) {
		this.nroAutores = nroAutores;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public String getTitulo1() {
		return titulo1;
	}

	public void setTitulo1(String titulo1) {
		this.titulo1 = titulo1;
	}

	public String getTitulo2() {
		return titulo2;
	}

	public void setTitulo2(String titulo2) {
		this.titulo2 = titulo2;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getAreaConocimiento() {
		return areaConocimiento;
	}

	public void setAreaConocimiento(String areaConocimiento) {
		this.areaConocimiento = areaConocimiento;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public ProyectoInforme getInforme() {
		return informe;
	}

	public void setInforme(ProyectoInforme informe) {
		this.informe = informe;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreSeccion() {
		return nombreSeccion;
	}

	public void setNombreSeccion(String nombreSeccion) {
		this.nombreSeccion = nombreSeccion;
	}

	public int compareTo(ProductoSara o) {
	    if(o.getNombreProducto() == null){
	        o.setNombreProducto("");
	    }
		return nombreProducto == null ? hashCode() - o.hashCode() : nombreProducto.compareTo(o.getNombreProducto());
	}

	public String getOrigen() {
	    return origen;
	}

	public void setOrigen(String origen) {
	    this.origen = origen;
	}

	public String getCodSeccion() {
	    return codSeccion;
	}

	public void setCodSeccion(String codSeccion) {
	    this.codSeccion = codSeccion;
	}

	public String getCodCategoriaHV() {
	    return codCategoriaHV;
	}

	public void setCodCategoriaHV(String codCategoriaHV) {
	    this.codCategoriaHV = codCategoriaHV;
	}

	public String getNombreCategoriaHV() {
	    return nombreCategoriaHV;
	}

	public void setNombreCategoriaHV(String nombreCategoriaHV) {
	    this.nombreCategoriaHV = nombreCategoriaHV;
	}

	public Integer getCodigoEmpleado() {
	    return codigoEmpleado;
	}

	public void setCodigoEmpleado(Integer codigoEmpleado) {
	    this.codigoEmpleado = codigoEmpleado;
	}

	public String getNombresYApellidos() {
	    return nombresYApellidos;
	}

	public void setNombresYApellidos(String nombresYApellidos) {
	    this.nombresYApellidos = nombresYApellidos;
	}

	public Date getFechaFinal() {
	    return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
	    this.fechaFinal = fechaFinal;
	}

	public String getCodRevista() {
	    return codRevista;
	}

	public void setCodRevista(String codRevista) {
	    this.codRevista = codRevista;
	}

	public String getNomRevista() {
	    return nomRevista;
	}

	public void setNomRevista(String nomRevista) {
	    this.nomRevista = nomRevista;
	}

	public Integer getPaginaInicial() {
	    return paginaInicial;
	}

	public void setPaginaInicial(Integer paginaInicial) {
	    this.paginaInicial = paginaInicial;
	}

	public Integer getPaginaFinal() {
	    return paginaFinal;
	}

	public void setPaginaFinal(Integer paginaFinal) {
	    this.paginaFinal = paginaFinal;
	}

	public String getIsbn() {
	    return isbn;
	}

	public void setIsbn(String isbn) {
	    this.isbn = isbn;
	}

	public Integer getNroOficioDoc() {
	    return nroOficioDoc;
	}

	public void setNroOficioDoc(Integer nroOficioDoc) {
	    this.nroOficioDoc = nroOficioDoc;
	}

	public Integer getCodInstitucion() {
	    return codInstitucion;
	}

	public void setCodInstitucion(Integer codInstitucion) {
	    this.codInstitucion = codInstitucion;
	}

	public String getCodPais() {
	    return codPais;
	}

	public void setCodPais(String codPais) {
	    this.codPais = codPais;
	}

	public String getCodDepto() {
	    return codDepto;
	}

	public void setCodDepto(String codDepto) {
	    this.codDepto = codDepto;
	}

	public String getCodMunicipio() {
	    return codMunicipio;
	}

	public void setCodMunicipio(String codMunicipio) {
	    this.codMunicipio = codMunicipio;
	}

	public Integer getHorasSemana() {
	    return horasSemana;
	}

	public void setHorasSemana(Integer horasSemana) {
	    this.horasSemana = horasSemana;
	}

	public Integer getHorasSemestre() {
	    return horasSemestre;
	}

	public void setHorasSemestre(Integer horasSemestre) {
	    this.horasSemestre = horasSemestre;
	}

	public Integer getHorasTotales() {
	    return horasTotales;
	}

	public void setHorasTotales(Integer horasTotales) {
	    this.horasTotales = horasTotales;
	}

	public Integer getHorasCertificadas() {
	    return horasCertificadas;
	}

	public void setHorasCertificadas(Integer horasCertificadas) {
	    this.horasCertificadas = horasCertificadas;
	}

	public String getCodAreaConocimiento() {
	    return codAreaConocimiento;
	}

	public void setCodAreaConocimiento(String codAreaConocimiento) {
	    this.codAreaConocimiento = codAreaConocimiento;
	}

	public String getCodIdioma() {
	    return codIdioma;
	}

	public void setCodIdioma(String codIdioma) {
	    this.codIdioma = codIdioma;
	}

	public String getCodNuevoIdioma() {
	    return codNuevoIdioma;
	}

	public void setCodNuevoIdioma(String codNuevoIdioma) {
	    this.codNuevoIdioma = codNuevoIdioma;
	}

	public String getNuevoIdioma() {
	    return nuevoIdioma;
	}

	public void setNuevoIdioma(String nuevoIdioma) {
	    this.nuevoIdioma = nuevoIdioma;
	}

	public String getTipoDocumento() {
	    return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
	    this.tipoDocumento = tipoDocumento;
	}

	public String getTipoPublicacion() {
	    return tipoPublicacion;
	}

	public void setTipoPublicacion(String tipoPublicacion) {
	    this.tipoPublicacion = tipoPublicacion;
	}

	public String getTipoProduccion() {
	    return tipoProduccion;
	}

	public void setTipoProduccion(String tipoProduccion) {
	    this.tipoProduccion = tipoProduccion;
	}

	public String getTipoMaterial() {
	    return tipoMaterial;
	}

	public void setTipoMaterial(String tipoMaterial) {
	    this.tipoMaterial = tipoMaterial;
	}

	public String getMedioDifusion() {
	    return medioDifusion;
	}

	public void setMedioDifusion(String medioDifusion) {
	    this.medioDifusion = medioDifusion;
	}

	public String getMedioEdicion() {
	    return medioEdicion;
	}

	public void setMedioEdicion(String medioEdicion) {
	    this.medioEdicion = medioEdicion;
	}

	public String getModalidadMemoria() {
	    return modalidadMemoria;
	}

	public void setModalidadMemoria(String modalidadMemoria) {
	    this.modalidadMemoria = modalidadMemoria;
	}

	
	public String getDistincion() {
	    return distincion;
	}

	public void setDistincion(String distincion) {
	    this.distincion = distincion;
	}

	public String getParticipacion() {
	    return participacion;
	}

	public void setParticipacion(String participacion) {
	    this.participacion = participacion;
	}

	public String getTipoPatente() {
	    return tipoPatente;
	}

	public void setTipoPatente(String tipoPatente) {
	    this.tipoPatente = tipoPatente;
	}

	public String getFacultadSara() {
	    return facultadSara;
	}

	public void setFacultadSara(String facultadSara) {
	    this.facultadSara = facultadSara;
	}

	public String getMotivoReconocimiento() {
	    return motivoReconocimiento;
	}

	public void setMotivoReconocimiento(String motivoReconocimiento) {
	    this.motivoReconocimiento = motivoReconocimiento;
	}

	public String getTipoLista() {
	    return tipoLista;
	}

	public void setTipoLista(String tipoLista) {
	    this.tipoLista = tipoLista;
	}

	public String getEntidad() {
	    return entidad;
	}

	public void setEntidad(String entidad) {
	    this.entidad = entidad;
	}

	public String getEntidadFinancia() {
	    return entidadFinancia;
	}

	public void setEntidadFinancia(String entidadFinancia) {
	    this.entidadFinancia = entidadFinancia;
	}

	public String getLugar() {
	    return lugar;
	}

	public void setLugar(String lugar) {
	    this.lugar = lugar;
	}

	public String getTiraje() {
	    return tiraje;
	}

	public void setTiraje(String tiraje) {
	    this.tiraje = tiraje;
	}

	public String getVolumen() {
	    return volumen;
	}

	public void setVolumen(String volumen) {
	    this.volumen = volumen;
	}

	public String getDuracion() {
	    return duracion;
	}

	public void setDuracion(String duracion) {
	    this.duracion = duracion;
	}

	public String getObjetivo() {
	    return objetivo;
	}

	public void setObjetivo(String objetivo) {
	    this.objetivo = objetivo;
	}

	public String getProductoResultadoEsperado() {
	    return productoResultadoEsperado;
	}

	public void setProductoResultadoEsperado(String productoResultadoEsperado) {
	    this.productoResultadoEsperado = productoResultadoEsperado;
	}

	public String getCaracter() {
	    return caracter;
	}

	public void setCaracter(String caracter) {
	    this.caracter = caracter;
	}

    /**
     * @return the productoCompletado
     */
    public String getProductoCompletado() {
        return productoCompletado;
    }

    /**
     * @param productoCompletado the productoCompletado to set
     */
    public void setProductoCompletado(String productoCompletado) {
        this.productoCompletado = productoCompletado;
    }

    /**
     * @return the motivoNoEntrega
     */
    public String getMotivoNoEntrega() {
        return motivoNoEntrega;
    }

    /**
     * @param motivoNoEntrega the motivoNoEntrega to set
     */
    public void setMotivoNoEntrega(String motivoNoEntrega) {
        this.motivoNoEntrega = motivoNoEntrega;
    }

    /**
     * @return the linkPublico
     */
    public String getLinkPublico() {
        return linkPublico;
    }

    /**
     * @param linkPublico the linkPublico to set
     */
    public void setLinkPublico(String linkPublico) {
        this.linkPublico = linkPublico;
    }

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public String getLugarDeposito() {
		return lugarDeposito;
	}

	public String getObjetivoDesarrolloSost() {
		return objetivoDesarrolloSost;
	}

	public void setObjetivoDesarrolloSost(String objetivoDesarrolloSost) {
		this.objetivoDesarrolloSost = objetivoDesarrolloSost;
	}

	public String getAreaOCDEppal() {
		return areaOCDEppal;
	}

	public void setAreaOCDEppal(String areaOCDEppal) {
		this.areaOCDEppal = areaOCDEppal;
	}

	public void setLugarDeposito(String lugarDeposito) {
		this.lugarDeposito = lugarDeposito;
	}

	public String getSusceptibleProteccion() {
		return susceptibleProteccion;
	}

	public void setSusceptibleProteccion(String susceptibleProteccion) {
		this.susceptibleProteccion = susceptibleProteccion;
	}

	public String getNombreOds() {
		return nombreOds;
	}

	public void setNombreOds(String nombreOds) {
		this.nombreOds = nombreOds;
	}

	public String getNombreOcde() {
		return nombreOcde;
	}

	public void setNombreOcde(String nombreOcde) {
		this.nombreOcde = nombreOcde;
	}	

}
