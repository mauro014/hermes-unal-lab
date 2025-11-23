package co.edu.unal.hermes.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class PropiedadIntelectual implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final Long PROPIEDAD_INDUSTRIAL = 1L;
	public static final Long DERECHOS_AUTOR = 2L;
	public static final Long DERECHOS_OBTENTOR = 3L;

	public static final Long PATENTE_INVENCION = 1L;
	public static final Long PATENTE_MOD_UTILIDAD = 2L;
	public static final Long DISENO_INDUSTRIAL = 3L;
	public static final Long ESQUEMA_TRAZADO = 12L;

	public static final Long OBRA_LITERARIA = 4L;
	public static final Long OBRA_ARTISTICA = 5L;
	public static final Long OBRA_MUSICAL = 6L;
	public static final Long OBRA_AUDIOVISUAL = 7L;
	public static final Long FONOGRAMA = 8L;
	public static final Long SOFTWARE = 9L;
	public static final Long MARCA = 11L;

	public static final String OBRA_INEDITA = "IN";
	public static final String OBRA_EDITADA = "ED";

	// Naturaleza de proyectos
	public static final Long TIPO_PROY_INV_LAB = (long) 1;
	public static final Long TIPO_PROY_EXT = (long) 2;

	private Long id;
	private Date fechaRegistro;
	private Date fechaRevision;
	private SubTipoPropiedadIntelectual subTipo;
	private SubEstadoPropiedadIntelectual subEstado;
	private String titulo;
	private String tituloEspanol;
	private String descripcion;//Ahora: Problema técnico que resuelve
	private Long anoTerminacion;
	private Pais pais;
	private Dependencia dependenciaSolicitante;
	private InvestigadorInterno responsableRegistro;
	private String monitoreo; // Ahora: Monitoreo tecnológico
	private String difusion;
	private String mercadoPoblacion; //Ahora: Posibilidades de transferencia
	private String estadoObra; // Inedita o Editada
	private String editor;
	private String impresor;
	private Date fechaPublicacion;
	private String numeroEdicion;
	private String tiraje;
	private Long numeroPaginas;
	private String isbn;
	private String ventajas;
	private String impactoSolucion;
	private String dependenciaApoyo;
	private String informacionEmail;
	private String ritmo;
	private Tipos genero;
	private Float duracion;
	private Long solicitudIsbn;
	private String otrosDatos;
	private String objetoProteccion;
	private Tipos clasificacion;
	private Tipos estadoDesarrollo;
	private Tipos sectorTecnologico;
	private String otroSector;
	private Date fechaFinalizacionDerecho;
	private Persona apoderado;
	private String contratoAccesoRecursoGenetico;
	private FichaGestorPropiedadIndustrial fichaGestor;
	private String pagoAnualidades;
	private String negociacion;
	
	private Tipos tipoSolicitud;
	private String clasificacionRevision;

	// Variables tramite externo
	private String radicado;
	private Date fechaRadicado;

	// Variable temporal para filtrar por fecha - Requerimiento #2323
	protected String fechaRegistroConFormato;

	private Set<CotitularPropiedadIntelectual> cotitulares = new HashSet<CotitularPropiedadIntelectual>();
	private Set<CaracterPropiedadIntelectual> caracter = new HashSet<CaracterPropiedadIntelectual>();
	private Set<AmbitoPropiedadIntelectual> ambito = new HashSet<AmbitoPropiedadIntelectual>();
	private Set<TipoEdicionPropiedadIntelectual> tiposEdicion = new HashSet<TipoEdicionPropiedadIntelectual>();
	private Set<PersonaPropiedadIntelectual> personal = new HashSet<PersonaPropiedadIntelectual>();
	private Set<ArchivoPropiedadIntelectual> archivos = new HashSet<ArchivoPropiedadIntelectual>();
	private Set<ClasePropiedadIntelectual> clase = new HashSet<ClasePropiedadIntelectual>();
	private Set<FormatoPropiedadIntelectual> formato = new HashSet<FormatoPropiedadIntelectual>();
	private Set<ObraFonogramaPropiedadIntelectual> obrasFonograma = new HashSet<ObraFonogramaPropiedadIntelectual>();
	private Set<ResultadoPropiedadIntelectual> resultados = new HashSet<ResultadoPropiedadIntelectual>();
	private Set<ProyectoPropiedadIntelectual> proyectos = new HashSet<ProyectoPropiedadIntelectual>();
	private Set<Grupo> gruposInvestigacion = new HashSet<Grupo>();
	private Set<NegociacionPropiedadIntelectual> negociaciones = new HashSet<NegociacionPropiedadIntelectual>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaRevision() {
		return fechaRevision;
	}

	public void setFechaRevision(Date fechaRevision) {
		this.fechaRevision = fechaRevision;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public InvestigadorInterno getResponsableRegistro() {
		return responsableRegistro;
	}

	public void setResponsableRegistro(InvestigadorInterno responsableRegistro) {
		this.responsableRegistro = responsableRegistro;
	}

	public Set<CotitularPropiedadIntelectual> getCotitulares() {
		return cotitulares;
	}

	public void setCotitulares(Set<CotitularPropiedadIntelectual> cotitulares) {
		this.cotitulares = cotitulares;
	}

	public void adicionarCotitularidad(CotitularPropiedadIntelectual cotitular) {
		cotitulares.add(cotitular);
	}

	public void borrarCotitularidad(CotitularPropiedadIntelectual cotitular) {
		cotitulares.remove(cotitular);
	}

	public List<CotitularPropiedadIntelectual> getListaCotitulares() {
		ArrayList<CotitularPropiedadIntelectual> listaCotitulares = new ArrayList<CotitularPropiedadIntelectual>();
		listaCotitulares.addAll(cotitulares);
		return listaCotitulares;
	}

	public SubTipoPropiedadIntelectual getSubTipo() {
		return subTipo;
	}

	public void setSubTipo(SubTipoPropiedadIntelectual subTipo) {
		this.subTipo = subTipo;
	}

	public String getMonitoreo() {
		return monitoreo;
	}

	public void setMonitoreo(String monitoreo) {
		this.monitoreo = monitoreo;
	}

	public String getDifusion() {
		return difusion;
	}

	public void setDifusion(String difusion) {
		this.difusion = difusion;
	}

	public Set<ArchivoPropiedadIntelectual> getArchivos() {
		return archivos;
	}

	public void setArchivos(Set<ArchivoPropiedadIntelectual> archivos) {
		this.archivos = archivos;
	}

	public void adicionarArchivo(ArchivoPropiedadIntelectual arc) {
		archivos.add(arc);
	}

	public void borrarArchivo(ArchivoPropiedadIntelectual arc) {
		archivos.remove(arc);
	}

	public List<ArchivoPropiedadIntelectual> getListaArchivos() {
		ArrayList<ArchivoPropiedadIntelectual> listaArchivos = new ArrayList<ArchivoPropiedadIntelectual>();
		listaArchivos.addAll(archivos);
//		Collections.sort(listaArchivos, tipoArchivoPI);

		return listaArchivos;
	}

	public static Comparator<ArchivoPropiedadIntelectual> tipoArchivoPI = new Comparator<ArchivoPropiedadIntelectual>() {

		public int compare(ArchivoPropiedadIntelectual s1, ArchivoPropiedadIntelectual s2) {
			Short tipo1 = s1.getTipo().getId();
			Short tipo2 = s2.getTipo().getId();

			// ascending order
			return tipo1.compareTo(tipo2);
		}
	};

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Long getAnoTerminacion() {
		return anoTerminacion;
	}

	public void setAnoTerminacion(Long anoTerminacion) {
		this.anoTerminacion = anoTerminacion;
	}

	public SubEstadoPropiedadIntelectual getSubEstado() {
		return subEstado;
	}

	public void setSubEstado(SubEstadoPropiedadIntelectual subEstado) {
		this.subEstado = subEstado;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public String getEstadoObra() {
		return estadoObra;
	}

	public void setEstadoObra(String estadoObra) {
		this.estadoObra = estadoObra;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getImpresor() {
		return impresor;
	}

	public void setImpresor(String impresor) {
		this.impresor = impresor;
	}

	public Date getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(Date fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public String getNumeroEdicion() {
		return numeroEdicion;
	}

	public void setNumeroEdicion(String numeroEdicion) {
		this.numeroEdicion = numeroEdicion;
	}

	public String getTiraje() {
		return tiraje;
	}

	public void setTiraje(String tiraje) {
		this.tiraje = tiraje;
	}

	public Long getNumeroPaginas() {
		return numeroPaginas;
	}

	public void setNumeroPaginas(Long numeroPaginas) {
		this.numeroPaginas = numeroPaginas;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getVentajas() {
		return ventajas;
	}

	public void setVentajas(String ventajas) {
		this.ventajas = ventajas;
	}

	public String getImpactoSolucion() {
		return impactoSolucion;
	}

	public void setImpactoSolucion(String impactoSolucion) {
		this.impactoSolucion = impactoSolucion;
	}

	public boolean isEsInedita() {
		if (this.estadoObra != null && this.estadoObra.equals(OBRA_INEDITA)) {
			return true;
		}
		return false;
	}

	public boolean isEsEditada() {
		if (this.estadoObra != null && this.estadoObra.equals(OBRA_EDITADA)) {
			return true;
		}
		return false;
	}

	public boolean isEsPropiedadIndustrial() {
		if (this.subTipo != null && this.subTipo.getTipo() != null && this.subTipo.getTipo().getId() != null
				&& this.subTipo.getTipo().getId().equals(PROPIEDAD_INDUSTRIAL)) {
			return true;
		}
		return false;
	}

	public boolean isEsDerechosAutor() {
		if (this.subTipo != null && this.subTipo.getTipo() != null && this.subTipo.getTipo().getId() != null
				&& this.subTipo.getTipo().getId().equals(DERECHOS_AUTOR)) {
			return true;
		}
		return false;
	}

	public boolean isEsDerechosObtentor() {
		if (this.subTipo != null && this.subTipo.getTipo() != null && this.subTipo.getTipo().getId() != null
				&& this.subTipo.getTipo().getId().equals(DERECHOS_OBTENTOR)) {
			return true;
		}
		return false;
	}

	public boolean isEsObraLiteraria() {
		if (this.isEsDerechosAutor() && this.subTipo.getId().equals(OBRA_LITERARIA)) {
			return true;
		}
		return false;
	}

	public boolean isEsSoftware() {
		if (this.isEsDerechosAutor() && this.subTipo.getId().equals(SOFTWARE)) {
			return true;
		}
		return false;
	}

	public boolean isEsFonograma() {
		if (this.isEsDerechosAutor() && this.subTipo.getId().equals(FONOGRAMA)) {
			return true;
		}
		return false;
	}

	public boolean isEsObraArtistica() {
		if (this.isEsDerechosAutor() && this.subTipo.getId().equals(OBRA_ARTISTICA)) {
			return true;
		}
		return false;
	}

	public boolean isEsObraMusical() {
		if (this.isEsDerechosAutor() && this.subTipo.getId().equals(OBRA_MUSICAL)) {
			return true;
		}
		return false;
	}

	public boolean isEsObraAudioVisual() {
		if (this.isEsDerechosAutor() && this.subTipo.getId().equals(OBRA_AUDIOVISUAL)) {
			return true;
		}
		return false;
	}

	public boolean isEsMarca() {
		if (this.isEsPropiedadIndustrial() && this.subTipo.getId().equals(MARCA)) {
			return true;
		}
		return false;
	}
	
	public boolean isEsDisenoIndustrial() {
		if (this.isEsPropiedadIndustrial() && this.subTipo.getId().equals(DISENO_INDUSTRIAL)) {
			return true;
		}
		return false;
	}
	
	public boolean isEsEsquemaDeTrazado() {
		if (this.isEsPropiedadIndustrial() && this.subTipo.getId().equals(ESQUEMA_TRAZADO)) {
			return true;
		}
		return false;
	}
	
	public boolean isEsPatenteInvencion() {
		if (this.isEsPropiedadIndustrial() && this.subTipo.getId().equals(PATENTE_INVENCION)) {
			return true;
		}
		return false;
	}
	
	public boolean isEsPatenteModeloUtilidad() {
		if (this.isEsPropiedadIndustrial() && this.subTipo.getId().equals(PATENTE_MOD_UTILIDAD)) {
			return true;
		}
		return false;
	}

	public boolean isEsPatente() {
		if (this.isEsPropiedadIndustrial() && (this.subTipo.getId().equals(PATENTE_INVENCION)
				|| this.subTipo.getId().equals(PATENTE_MOD_UTILIDAD))) {
			return true;
		}
		return false;
	}

	public boolean isEsEditable() {
		if (this.subEstado != null && this.subEstado.isEsEditable()) {
			return true;
		}
		return false;
	}

	public boolean isHayPrincipal() {
		if (!getListaPersonas().isEmpty()) {
			for (int i = 0; i < getListaPersonas().size(); i++) {
				PersonaPropiedadIntelectual persona = getListaPersonas().get(i);
				if (persona.getTipoPersona().getId().equals(TipoPersonaPropiedadIntelectual.AUTOR_SOFTWARE)) {
					return true;
				} else if (persona.getTipoPersona().getId()
						.equals(TipoPersonaPropiedadIntelectual.AUTOR_OBRA_LITERARIA)) {
					return true;
				} else if (persona.getTipoPersona().getId()
						.equals(TipoPersonaPropiedadIntelectual.AUTOR_OBRA_ARTISTICA)) {
					return true;
				} else if (persona.getTipoPersona().getId().equals(TipoPersonaPropiedadIntelectual.AUTOR_FONOGRAMA)) {
					return true;
				} else if (persona.getTipoPersona().getId()
						.equals(TipoPersonaPropiedadIntelectual.AUTOR_OBRA_MUSICAL)) {
					return true;
				} else if (persona.getTipoPersona().getId()
						.equals(TipoPersonaPropiedadIntelectual.PRODUCTOR_OBRA_AUDIOVISUAL)) {
					return true;
				} else if (persona.getTipoPersona().getId()
						.equals(TipoPersonaPropiedadIntelectual.PRINCIPAL_PROPIEDAD_INDUSTRIAL)) {
					return true;
				}
			}
		}
		return false;
	}

	public Set<CaracterPropiedadIntelectual> getCaracter() {
		return caracter;
	}

	public void setCaracter(Set<CaracterPropiedadIntelectual> caracter) {
		this.caracter = caracter;
	}

	public void adicionarCaracter(CaracterPropiedadIntelectual car) {
		this.caracter.add(car);
	}

	public void borrarCaracter(CaracterPropiedadIntelectual car) {
		this.caracter.remove(car);
	}

	public List<CaracterPropiedadIntelectual> getListaCaracter() {
		ArrayList<CaracterPropiedadIntelectual> listaCaracter = new ArrayList<CaracterPropiedadIntelectual>();
		listaCaracter.addAll(this.caracter);
		return listaCaracter;
	}

	public void setListaCaracter(List<CaracterPropiedadIntelectual> listaCaracter) {
		this.caracter.addAll(listaCaracter);
	}

	public void borrarListaCaracter(List<CaracterPropiedadIntelectual> listaCaracter) {
		for (int i = 0; i < listaCaracter.size(); i++) {
			borrarCaracter(listaCaracter.get(i));
		}
	}

	public void borrarListaPersonas(List<PersonaPropiedadIntelectual> listaPersonas) {
		for (int i = 0; i < listaPersonas.size(); i++) {
			borrarPersona(listaPersonas.get(i));
		}
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getMercadoPoblacion() {
		return mercadoPoblacion;
	}

	public void setMercadoPoblacion(String mercadoPoblacion) {
		this.mercadoPoblacion = mercadoPoblacion;
	}

	public Dependencia getDependenciaSolicitante() {
		return dependenciaSolicitante;
	}

	public void setDependenciaSolicitante(Dependencia dependenciaSolicitante) {
		this.dependenciaSolicitante = dependenciaSolicitante;
	}

	public Set<AmbitoPropiedadIntelectual> getAmbito() {
		return ambito;
	}

	public void setAmbito(Set<AmbitoPropiedadIntelectual> ambito) {
		this.ambito = ambito;
	}

	public void adicionarAmbito(AmbitoPropiedadIntelectual ambito) {
		this.ambito.add(ambito);
	}

	public void borrarAmbito(AmbitoPropiedadIntelectual ambito) {
		this.ambito.remove(ambito);
	}

	public List<AmbitoPropiedadIntelectual> getListaAmbito() {
		ArrayList<AmbitoPropiedadIntelectual> listaAmbito = new ArrayList<AmbitoPropiedadIntelectual>();
		listaAmbito.addAll(this.ambito);
		return listaAmbito;
	}

	public void setListaAmbito(List<AmbitoPropiedadIntelectual> listaAmbito) {
		this.ambito.addAll(listaAmbito);
	}

	public void borrarListaAmbito(List<AmbitoPropiedadIntelectual> listaAmbito) {
		for (int i = 0; i < listaAmbito.size(); i++) {
			borrarAmbito(listaAmbito.get(i));
		}
	}

	public Set<TipoEdicionPropiedadIntelectual> getTiposEdicion() {
		return tiposEdicion;
	}

	public void setTiposEdicion(Set<TipoEdicionPropiedadIntelectual> tiposEdicion) {
		this.tiposEdicion = tiposEdicion;
	}

	public List<TipoEdicionPropiedadIntelectual> getListaTiposEdicion() {
		ArrayList<TipoEdicionPropiedadIntelectual> listaTipoEdicion = new ArrayList<TipoEdicionPropiedadIntelectual>();
		listaTipoEdicion.addAll(this.tiposEdicion);
		return listaTipoEdicion;
	}

	public void adicionarTipoEdicion(TipoEdicionPropiedadIntelectual tipo) {
		this.tiposEdicion.add(tipo);
	}

	public void borrarTipoEdicion(TipoEdicionPropiedadIntelectual tipo) {
		this.tiposEdicion.remove(tipo);
	}

	public void setListaTiposEdicion(List<TipoEdicionPropiedadIntelectual> listaTipoEdicion) {
		this.tiposEdicion.addAll(listaTipoEdicion);
	}

	public void borrarListaTiposEdicion(List<TipoEdicionPropiedadIntelectual> listaTipoEdicion) {
		for (int i = 0; i < listaTipoEdicion.size(); i++) {
			borrarTipoEdicion(listaTipoEdicion.get(i));
		}
	}

	public Set<PersonaPropiedadIntelectual> getPersonal() {
		return personal;
	}

	public void setPersonal(Set<PersonaPropiedadIntelectual> personal) {
		this.personal = personal;
	}

	public List<PersonaPropiedadIntelectual> getListaPersonas() {
		ArrayList<PersonaPropiedadIntelectual> listaPersonal = new ArrayList<PersonaPropiedadIntelectual>();
		listaPersonal.addAll(this.personal);
		return listaPersonal;
	}

	public void adicionarPersona(PersonaPropiedadIntelectual per) {
		this.personal.add(per);
	}

	public void borrarPersona(PersonaPropiedadIntelectual per) {
		this.personal.remove(per);
	}

	public boolean existeTipoPersona(Long tipo) {
		Iterator<PersonaPropiedadIntelectual> it = getPersonal().iterator();
		while (it.hasNext()) {
			PersonaPropiedadIntelectual persona = (PersonaPropiedadIntelectual) it.next();
			if (persona.getTipoPersona().getId().equals(tipo)) {
				return true;
			}
		}
		return false;
	}

	public List<PersonaPropiedadIntelectual> getListaDocentes() {
		ArrayList<PersonaPropiedadIntelectual> listaProfesores = new ArrayList<PersonaPropiedadIntelectual>();
		if (getPersonal() != null) {
			Iterator<PersonaPropiedadIntelectual> it = getPersonal().iterator();
			while (it.hasNext()) {
				PersonaPropiedadIntelectual persona = (PersonaPropiedadIntelectual) it.next();
//				if (persona.getTipoPersona().getId().equals(PersonaPropiedadIntelectual.DOCENTE)) {
				if (persona.getCalidad().equals(PersonaPropiedadIntelectual.DOCENTE)) {
					listaProfesores.add(persona);
				}
			}
		}

		return listaProfesores;
	}

	public boolean isEsSolicitudIntersedes() {
		if (getListaDocentes().size() > 0) {
			PersonaPropiedadIntelectual persona = (PersonaPropiedadIntelectual) getListaDocentes().get(0);

			Long idSedeIntegrante = persona.getDependencia().getSede().getId();

			Iterator<PersonaPropiedadIntelectual> it = getListaDocentes().iterator();
			while (it.hasNext()) {
				PersonaPropiedadIntelectual profesor = (PersonaPropiedadIntelectual) it.next();
				if (!idSedeIntegrante.equals(profesor.getDependencia().getSede().getId())) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isTieneDocumentoCambioFechaDerecho() {
		if (getListaArchivos().size() > 0) {
			Iterator<ArchivoPropiedadIntelectual> it = getListaArchivos().iterator();
			while (it.hasNext()) {
				ArchivoPropiedadIntelectual archivo = (ArchivoPropiedadIntelectual) it.next();
				if (archivo.getTipo().getId().equals(ArchivoPropiedadIntelectual.ARCHIVO_FECHA_DERECHO)) {
					return true;
				}
			}
		}
		return false;
	}

	public String getDependenciaApoyo() {
		return dependenciaApoyo;
	}

	public void setDependenciaApoyo(String dependenciaApoyo) {
		this.dependenciaApoyo = dependenciaApoyo;
	}

	public String getRadicado() {
		return radicado;
	}

	public void setRadicado(String radicado) {
		this.radicado = radicado;
	}

	public Date getFechaRadicado() {
		return fechaRadicado;
	}

	public void setFechaRadicado(Date fechaRadicado) {
		this.fechaRadicado = fechaRadicado;
	}

	public String getInformacionEmail() {
		return informacionEmail;
	}

	public void setInformacionEmail(String informacionEmail) {
		this.informacionEmail = informacionEmail;
	}

	public Set<ClasePropiedadIntelectual> getClase() {
		return clase;
	}

	public void setClase(Set<ClasePropiedadIntelectual> clase) {
		this.clase = clase;
	}

	public void adicionarClase(ClasePropiedadIntelectual clase) {
		this.clase.add(clase);
	}

	public void borrarClase(ClasePropiedadIntelectual clase) {
		this.clase.remove(clase);
	}

	public List<ClasePropiedadIntelectual> getListaClase() {
		ArrayList<ClasePropiedadIntelectual> listaClase = new ArrayList<ClasePropiedadIntelectual>();
		listaClase.addAll(this.clase);
		return listaClase;
	}

	public void setListaClase(List<ClasePropiedadIntelectual> listaClase) {
		this.clase.addAll(listaClase);
	}

	public void borrarListaClase(List<ClasePropiedadIntelectual> listaClase) {
		for (int i = 0; i < listaClase.size(); i++) {
			borrarClase(listaClase.get(i));
		}
	}

	public Set<FormatoPropiedadIntelectual> getFormato() {
		return formato;
	}

	public void setFormato(Set<FormatoPropiedadIntelectual> formato) {
		this.formato = formato;
	}

	public void adicionarFormato(FormatoPropiedadIntelectual formato) {
		this.formato.add(formato);
	}

	public void borrarFormato(FormatoPropiedadIntelectual formato) {
		this.formato.remove(formato);
	}

	public List<FormatoPropiedadIntelectual> getListaFormato() {
		ArrayList<FormatoPropiedadIntelectual> listaPublicacion = new ArrayList<FormatoPropiedadIntelectual>();
		listaPublicacion.addAll(this.formato);
		return listaPublicacion;
	}

	public void setListaFormato(List<FormatoPropiedadIntelectual> listaMediosPublicacion) {
		this.formato.addAll(listaMediosPublicacion);
	}

	public void borrarListaFormato(List<FormatoPropiedadIntelectual> listaMediosPublicacion) {
		for (int i = 0; i < listaMediosPublicacion.size(); i++) {
			borrarFormato(listaMediosPublicacion.get(i));
		}
	}

	public String getRitmo() {
		return ritmo;
	}

	public void setRitmo(String ritmo) {
		this.ritmo = ritmo;
	}

	public Set<ObraFonogramaPropiedadIntelectual> getObrasFonograma() {
		return obrasFonograma;
	}

	public void setObrasFonograma(Set<ObraFonogramaPropiedadIntelectual> obrasFonograma) {
		this.obrasFonograma = obrasFonograma;
	}

	public List<ObraFonogramaPropiedadIntelectual> getListaObrasFonograma() {
		ArrayList<ObraFonogramaPropiedadIntelectual> listaObrasFonograma = new ArrayList<ObraFonogramaPropiedadIntelectual>();
		listaObrasFonograma.addAll(this.obrasFonograma);
		return listaObrasFonograma;
	}

	public void adicionarObraFonograma(ObraFonogramaPropiedadIntelectual obra) {
		this.obrasFonograma.add(obra);
	}

	public void borrarObraFonograma(ObraFonogramaPropiedadIntelectual obra) {
		this.obrasFonograma.remove(obra);
	}

	public Tipos getGenero() {
		return genero;
	}

	public void setGenero(Tipos genero) {
		this.genero = genero;
	}

	public String getTituloEspanol() {
		return tituloEspanol;
	}

	public void setTituloEspanol(String tituloEspanol) {
		this.tituloEspanol = tituloEspanol;
	}

	public Float getDuracion() {
		return duracion;
	}

	public void setDuracion(Float duracion) {
		this.duracion = duracion;
	}

	public Long getSolicitudIsbn() {
		return solicitudIsbn;
	}

	public void setSolicitudIsbn(Long solicitudIsbn) {
		this.solicitudIsbn = solicitudIsbn;
	}

	public String getOtrosDatos() {
		return otrosDatos;
	}

	public void setOtrosDatos(String otrosDatos) {
		this.otrosDatos = otrosDatos;
	}

	public String getObjetoProteccion() {
		return objetoProteccion;
	}

	public void setObjetoProteccion(String objetoProteccion) {
		this.objetoProteccion = objetoProteccion;
	}

	public Set<ResultadoPropiedadIntelectual> getResultados() {
		return resultados;
	}

	public void setResultados(Set<ResultadoPropiedadIntelectual> resultados) {
		this.resultados = resultados;
	}

	public List<ResultadoPropiedadIntelectual> getListaResultados() {
		ArrayList<ResultadoPropiedadIntelectual> listaResultados = new ArrayList<ResultadoPropiedadIntelectual>();
		listaResultados.addAll(this.resultados);
		return listaResultados;
	}

	public void adicionarResultado(ResultadoPropiedadIntelectual res) {
		this.resultados.add(res);
	}

	public void borrarResultado(ResultadoPropiedadIntelectual res) {
		this.resultados.remove(res);
	}

	public Set<Grupo> getGruposInvestigacion() {
		return gruposInvestigacion;
	}

	public void setGruposInvestigacion(Set<Grupo> gruposInvestigacion) {
		this.gruposInvestigacion = gruposInvestigacion;
	}

	public List<Grupo> getListaGrupos() {
		ArrayList<Grupo> listaGrupos = new ArrayList<Grupo>();
		listaGrupos.addAll(this.gruposInvestigacion);
		return listaGrupos;
	}

	public void adicionarGrupo(Grupo gru) {
		this.gruposInvestigacion.add(gru);
	}

	public void borrarGrupo(Grupo gru) {
		this.gruposInvestigacion.remove(gru);
	}

	public Set<ProyectoPropiedadIntelectual> getProyectos() {
		return proyectos;
	}

	public void setProyectos(Set<ProyectoPropiedadIntelectual> proyectos) {
		this.proyectos = proyectos;
	}

	public List<ProyectoPropiedadIntelectual> getListaProyectos() {
		ArrayList<ProyectoPropiedadIntelectual> listaProyectos = new ArrayList<ProyectoPropiedadIntelectual>();
		listaProyectos.addAll(this.proyectos);
		return listaProyectos;
	}

	public void adicionarProyecto(ProyectoPropiedadIntelectual pry) {
		this.proyectos.add(pry);
	}

	public void borrarProyecto(ProyectoPropiedadIntelectual pry) {
		this.proyectos.remove(pry);
	}

	public Tipos getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(Tipos clasificacion) {
		this.clasificacion = clasificacion;
	}

	public boolean isEsTramiteTransferido() {
		if (this.dependenciaSolicitante != null && this.dependenciaApoyo != null
				&& !(this.dependenciaSolicitante.getSede().getId().toString().equals(this.dependenciaApoyo))) {
			return true;
		}
		return false;
	}

	public Tipos getEstadoDesarrollo() {
		return estadoDesarrollo;
	}

	public void setEstadoDesarrollo(Tipos estadoDesarrollo) {
		this.estadoDesarrollo = estadoDesarrollo;
	}

	public Tipos getSectorTecnologico() {
		return sectorTecnologico;
	}

	public void setSectorTecnologico(Tipos sectorTecnologico) {
		this.sectorTecnologico = sectorTecnologico;
	}

	public Date getFechaFinalizacionDerecho() {
		return fechaFinalizacionDerecho;
	}

	public void setFechaFinalizacionDerecho(Date fechaFinalizacionDerecho) {
		this.fechaFinalizacionDerecho = fechaFinalizacionDerecho;
	}

	public Persona getApoderado() {
		return apoderado;
	}

	public void setApoderado(Persona apoderado) {
		this.apoderado = apoderado;
	}

	public String getContratoAccesoRecursoGenetico() {
		return contratoAccesoRecursoGenetico;
	}

	public void setContratoAccesoRecursoGenetico(String contratoAccesoRecursoGenetico) {
		this.contratoAccesoRecursoGenetico = contratoAccesoRecursoGenetico;
	}

	public FichaGestorPropiedadIndustrial getFichaGestor() {
		return fichaGestor;
	}

	public void setFichaGestor(FichaGestorPropiedadIndustrial fichaGestor) {
		this.fichaGestor = fichaGestor;
	}

	public String getOtroSector() {
		return otroSector;
	}

	public void setOtroSector(String otroSector) {
		this.otroSector = otroSector;
	}

	public String getPagoAnualidades() {
		return pagoAnualidades;
	}

	public void setPagoAnualidades(String pagoAnualidades) {
		this.pagoAnualidades = pagoAnualidades;
	}

	public String getNegociacion() {
		return negociacion;
	}

	public void setNegociacion(String negociacion) {
		this.negociacion = negociacion;
	}

	public Set<NegociacionPropiedadIntelectual> getNegociaciones() {
		return negociaciones;
	}

	public void setNegociaciones(Set<NegociacionPropiedadIntelectual> negociaciones) {
		this.negociaciones = negociaciones;
	}

	public void adicionarNegociacion(NegociacionPropiedadIntelectual negociacion) {
		negociaciones.add(negociacion);
	}

	public void borrarNegociacion(NegociacionPropiedadIntelectual negociacion) {
		negociaciones.remove(negociacion);
	}

	public List<NegociacionPropiedadIntelectual> getListaNegociaciones() {
		ArrayList<NegociacionPropiedadIntelectual> listaNegociaciones = new ArrayList<NegociacionPropiedadIntelectual>();
		listaNegociaciones.addAll(negociaciones);
		return listaNegociaciones;
	}

	// Inicio Requerimiento #2353
	public String getFechaRegistroConFormato() {
		return fechaRegistroConFormato;
	}

	public void setFechaRegistroConFormato(String fecha) {
		this.fechaRegistroConFormato = fecha;
	}
	// Fin Requerimiento #2353

	public Tipos getTipoSolicitud() {
		return tipoSolicitud;
	}

	public void setTipoSolicitud(Tipos tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}

	public String getClasificacionRevision() {
		return clasificacionRevision;
	}

	public void setClasificacionRevision(String clasificacionRevision) {
		this.clasificacionRevision = clasificacionRevision;
	}
	
	
}