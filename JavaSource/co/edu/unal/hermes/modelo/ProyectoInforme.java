package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.Hibernate;


/**
 * The Class ProyectoInforme.
 */
public class ProyectoInforme implements Serializable {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 159278820869965233L;

	/** The Constant TIPO_INFORME_AVANCE. */
	public static final Long TIPO_INFORME_AVANCE = 1L;

	/** The Constant TIPO_INFORME_FINAL. */
	public static final Long TIPO_INFORME_FINAL = 2L;

    /** The Constant ESTADO_BORRADO. */
    public static final Long ESTADO_BORRADO = 0L;
    
    /** The Constant ESTADO_BORRADO. */
    public static final Long TIPO_ACCION_CONSULTA = 1L;
    
    /** The Constant TIPO_ACCION_EDICION. */
    public static final Long TIPO_ACCION_EDICION = 2L;
    
    /** The Constant TIPO_ACCION_EDICION. */
    public static final String PRESENTACION_INFORMES_SI = "SI";

	/** The id. */
	private Long id;

	/** The proyecto. */
	private Proyecto proyecto;

	/** The tipo informe. */
	private TipoInforme tipoInforme;

	/** The estado informe. */
	private EstadoInforme estadoInforme;

	/** The avance resumen. */
	private String avanceResumen;

	/** The avance resultados. */
	private String avanceResultados;

	/** The dificultades. */
	private String dificultades;

	/** The cuadro resultados. */
	private Blob cuadroResultados;

	/** The informe financiero. */
	private Blob informeFinanciero;

	/** The documento facultad. */
	private Blob documentoFacultad;

	/** The sipnosis. */
	private String sipnosis;

	/** The sipnosis d. */
	private String sipnosisD;

	/** The impacto d. */
	private String impactoD;

	/** The impacto. */
	private String impacto;

	/** The conclusiones. */
	private String conclusiones;

	/** The resumen tecnico. */
	private String resumenTecnico;

	/** The compra equipos. */
	private String compraEquipos = "NO";

	/** The nombre documento fac. */
	private String nombreDocumentoFac;

	/** The cuadro nombre. */
	private String cuadroNombre;

	/** The financiero nombre. */
	private String financieroNombre;

	/** The no aprobacion. */
	private String noAprobacion = "";

	/** The no lectura. */
	private String noLectura;

	/** The bienes. */
	private Set<Bien> bienes;

	/** The productos. */
	private Set<ProductoSara> productos;
	
	private Set<ProyectoInformeObjetivoEspecifico> objetivos = new HashSet<ProyectoInformeObjetivoEspecifico>();
	private Set<ProyectoInformeActividad> actividades = new HashSet<ProyectoInformeActividad>();
	private Set<ProyectoInformeResultado> resultados = new HashSet<ProyectoInformeResultado>();

	

	/** The fecha generacion. */
	private Date fechaGeneracion;

	/** The index. */
	private int index;

	/** The dependencia informe. */
	private String dependenciaInforme = "";

	/** The fecha aprobacion. */
	private Date fechaAprobacion;

	/** The fecha lectura. */
	private Date fechaLectura;

	/** The fecha desde. */
	private Date fechaDesde;

	/** The fecha hasta. */
	private Date fechaHasta;

	/** The proyecto compromiso. */
	private ProyectoCompromiso proyectoCompromiso;

	/** The formacion estudiantes. */
	private String formacionEstudiantes;

	/** The compromisos. */
	private String compromisos;

	/** The productos en sara. */
	private String productosEnSara = "SI";

	/** The verificacion productos compromisos. */
	private String verificacionProductosCompromisos;

	/** The dependencia aval facultad. */
	private String dependenciaAvalFacultad;

	/** The acto administrativo facultad. */
	private String actoAdministrativoFacultad;

	/** The fecha acto administrativo facultad. */
	private Date fechaActoAdministrativoFacultad;

	/** The numero acto administrativo facultad. */
	private String numeroActoAdministrativoFacultad;

	/** The pry tiene saldo ejecucion. */
	private String pryTieneSaldoEjecucion;

	/** The pry saldo ejecucion. */
	private Integer prySaldoEjecucion;

	/** The dependencia saldo ejecucion. */
	private String dependenciaSaldoEjecucion;

	/** The observaciones. */
	private String observaciones;

	/** The porcentaje ejecucion. */
	private String porcentajeEjecucion;

	/** The porcentaje ejecucion presupuestal. */
	private String porcentajeEjecucionPresupuestal;

	/** The id estudiante. */
	private String idEstudiante;

	/** The tipo doc estudiante. */
	private String tipoDocEstudiante;

	/** The calificacion. */
	private String calificacion;

	/** The nombre estudiante. */
	private String nombreEstudiante;

	/** The id responsable. */
	private String idResponsable;

	/** The tipo doc responsable. */
	private String tipoDocResponsable;

	/** The monto solicitado. */
	private Long montoSolicitado;

	/** The revisor facultad. */
	private Persona revisorFacultad;

	/** The revisor coordinador. */
	private Persona revisorCoordinador;

	/** The fecha eliminacion. */
	private Date fechaEliminacion;

	/** The responsable eliminacion. */
	private Persona responsableEliminacion;

	/** The numero notificaciones. */
	private Long numeroNotificaciones;
	
	/** The total valor ejecutado. */
	private Long  totalValorEjecutado;

	/** The numero participantes evento. */
	private Long numeroParticipantesEvento; 

	/** The numero estudiantes evento. */
	private Long numeroEstudiantesEvento; 

	/** The numero externos evento. */
	private Long numeroExternosEvento; 
	
	/** The recolecta especimenes. */
	private String recolectaEspecimenes;
	
	/** The movilizo especimenes. */
	private boolean movilizoEspecimenes;
	
	private boolean mostrarCompromisos;
	
	private Long montoTotalEjecutadoPeriodo;
	
	private Long montoTotalEjecutadoPeriodoExt;
	private String porcentajeEjecucionPresupuestalExterno;
	private String mostartMontoEjecutadoExt;
	

	/**
	 * Instantiates a new proyecto informe.
	 */
	//
	public ProyectoInforme() {
		bienes = new TreeSet<Bien>();
		productos = new TreeSet<ProductoSara>();
	}

	/**
	 * Gets the proyecto.
	 *
	 * @return the proyecto
	 */
	public Proyecto getProyecto() {
		return proyecto;
	}

	/**
	 * Sets the proyecto.
	 *
	 * @param proyecto
	 *            the new proyecto
	 */
	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the fecha generacion.
	 *
	 * @return the fecha generacion
	 */
	public Date getFechaGeneracion() {
		return fechaGeneracion;
	}

	/**
	 * Sets the fecha generacion.
	 *
	 * @param fechaGeneracion
	 *            the new fecha generacion
	 */
	public void setFechaGeneracion(Date fechaGeneracion) {
		this.fechaGeneracion = fechaGeneracion;
	}

	/**
	 * Gets the index.
	 *
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Sets the index.
	 *
	 * @param index
	 *            the new index
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * Gets the tipo informe.
	 *
	 * @return the tipo informe
	 */
	public TipoInforme getTipoInforme() {
		return tipoInforme;
	}

	/**
	 * Sets the tipo informe.
	 *
	 * @param tipoInforme
	 *            the new tipo informe
	 */
	public void setTipoInforme(TipoInforme tipoInforme) {
		this.tipoInforme = tipoInforme;
	}

	/**
	 * Gets the estado informe.
	 *
	 * @return the estado informe
	 */
	public EstadoInforme getEstadoInforme() {
		return estadoInforme;
	}

	/**
	 * Sets the estado informe.
	 *
	 * @param estadoInforme
	 *            the new estado informe
	 */
	public void setEstadoInforme(EstadoInforme estadoInforme) {
		this.estadoInforme = estadoInforme;
	}

	/**
	 * Gets the avance resumen.
	 *
	 * @return the avance resumen
	 */
	public String getAvanceResumen() {
		return avanceResumen;
	}

	/**
	 * Sets the avance resumen.
	 *
	 * @param avanceResumen
	 *            the new avance resumen
	 */
	public void setAvanceResumen(String avanceResumen) {
		this.avanceResumen = avanceResumen;
	}

	/**
	 * Gets the avance resultados.
	 *
	 * @return the avance resultados
	 */
	public String getAvanceResultados() {
		return avanceResultados;
	}

	/**
	 * Sets the avance resultados.
	 *
	 * @param avanceResultados
	 *            the new avance resultados
	 */
	public void setAvanceResultados(String avanceResultados) {
		this.avanceResultados = avanceResultados;
	}

	/**
	 * Gets the dificultades.
	 *
	 * @return the dificultades
	 */
	public String getDificultades() {
		return dificultades;
	}

	/**
	 * Sets the dificultades.
	 *
	 * @param dificultades
	 *            the new dificultades
	 */
	public void setDificultades(String dificultades) {
		this.dificultades = dificultades;
	}

	/**
	 * Gets the cuadro resultados.
	 *
	 * @return the cuadro resultados
	 */
	public Blob getCuadroResultados() {
		return cuadroResultados;
	}

	/**
	 * Sets the cuadro resultados.
	 *
	 * @param cuadroResultados
	 *            the new cuadro resultados
	 */
	public void setCuadroResultados(Blob cuadroResultados) {
		this.cuadroResultados = cuadroResultados;
	}

	/**
	 * Gets the documento facultad.
	 *
	 * @return the documento facultad
	 */
	public Blob getDocumentoFacultad() {
		return documentoFacultad;
	}

	/**
	 * Sets the documento facultad.
	 *
	 * @param documentoFacultad
	 *            the new documento facultad
	 */
	public void setDocumentoFacultad(Blob documentoFacultad) {
		this.documentoFacultad = documentoFacultad;
	}

	/**
	 * Gets the informe financiero.
	 *
	 * @return the informe financiero
	 */
	public Blob getInformeFinanciero() {
		return informeFinanciero;
	}

	/**
	 * Sets the informe financiero.
	 *
	 * @param informeFinanciero
	 *            the new informe financiero
	 */
	public void setInformeFinanciero(Blob informeFinanciero) {
		this.informeFinanciero = informeFinanciero;
	}

	/**
	 * Gets the cuadro nombre.
	 *
	 * @return the cuadro nombre
	 */
	public String getCuadroNombre() {
		return cuadroNombre;
	}

	/**
	 * Sets the cuadro nombre.
	 *
	 * @param cuadroNombre
	 *            the new cuadro nombre
	 */
	public void setCuadroNombre(String cuadroNombre) {
		this.cuadroNombre = cuadroNombre;
	}

	/**
	 * Gets the financiero nombre.
	 *
	 * @return the financiero nombre
	 */
	public String getFinancieroNombre() {
		return financieroNombre;
	}

	/**
	 * Sets the financiero nombre.
	 *
	 * @param financieroNombre
	 *            the new financiero nombre
	 */
	public void setFinancieroNombre(String financieroNombre) {
		this.financieroNombre = financieroNombre;
	}

	/**
	 * Sets the bytes documento facultad.
	 *
	 * @param bytes
	 *            the new bytes documento facultad
	 */
	public void setBytesDocumentoFacultad(byte[] bytes) {
		documentoFacultad = Hibernate.createBlob(bytes);
	}

	/**
	 * Gets the bytes documento facultad.
	 *
	 * @return the bytes documento facultad
	 */
	public byte[] getBytesDocumentoFacultad() {
		byte[] resultado = null;
		try {
			resultado = documentoFacultad.getBytes(1, (int) documentoFacultad.length());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}

	/**
	 * Sets the bytes cuadro resultados.
	 *
	 * @param bytes
	 *            the new bytes cuadro resultados
	 */
	public void setBytesCuadroResultados(byte[] bytes) {
		cuadroResultados = Hibernate.createBlob(bytes);
	}

	/**
	 * Gets the bytes cuadro resultados.
	 *
	 * @return the bytes cuadro resultados
	 */
	public byte[] getBytesCuadroResultados() {
		byte[] resultado = null;
		try {
			resultado = cuadroResultados.getBytes(1, (int) cuadroResultados.length());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}

	/**
	 * Sets the bytes informe financiero.
	 *
	 * @param bytes
	 *            the new bytes informe financiero
	 */
	public void setBytesInformeFinanciero(byte[] bytes) {
		informeFinanciero = Hibernate.createBlob(bytes);
	}

	/**
	 * Gets the bytes informe financiero.
	 *
	 * @return the bytes informe financiero
	 */
	public byte[] getBytesInformeFinanciero() {
		byte[] resultado = null;
		try {
			resultado = informeFinanciero.getBytes(1, (int) informeFinanciero.length());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}

	/**
	 * Checks if is mostrar cuadro.
	 *
	 * @return true, if is mostrar cuadro
	 */
	public boolean isMostrarCuadro() {

		if (this.cuadroNombre != null) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if is mostrar financiero.
	 *
	 * @return true, if is mostrar financiero
	 */
	public boolean isMostrarFinanciero() {

		if (this.financieroNombre != null) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the conclusiones.
	 *
	 * @return the conclusiones
	 */
	public String getConclusiones() {
		return conclusiones;
	}

	/**
	 * Sets the conclusiones.
	 *
	 * @param conclusiones
	 *            the new conclusiones
	 */
	public void setConclusiones(String conclusiones) {
		this.conclusiones = conclusiones;
	}

	/**
	 * Gets the sipnosis.
	 *
	 * @return the sipnosis
	 */
	public String getSipnosis() {
		return sipnosis;
	}

	/**
	 * Sets the sipnosis.
	 *
	 * @param sipnosis
	 *            the new sipnosis
	 */
	public void setSipnosis(String sipnosis) {
		this.sipnosis = sipnosis;
	}

	/**
	 * Gets the impacto.
	 *
	 * @return the impacto
	 */
	public String getImpacto() {
		return impacto;
	}

	/**
	 * Sets the impacto.
	 *
	 * @param impacto
	 *            the new impacto
	 */
	public void setImpacto(String impacto) {
		this.impacto = impacto;
	}

	/**
	 * Gets the resumen tecnico.
	 *
	 * @return the resumen tecnico
	 */
	public String getResumenTecnico() {
		return resumenTecnico;
	}

	/**
	 * Sets the resumen tecnico.
	 *
	 * @param resumenTecnico
	 *            the new resumen tecnico
	 */
	public void setResumenTecnico(String resumenTecnico) {
		this.resumenTecnico = resumenTecnico;
	}

	/**
	 * Gets the dependencia informe.
	 *
	 * @return the dependencia informe
	 */
	public String getDependenciaInforme() {
		return dependenciaInforme;
	}

	/**
	 * Sets the dependencia informe.
	 *
	 * @param dependenciaInforme
	 *            the new dependencia informe
	 */
	public void setDependenciaInforme(String dependenciaInforme) {
		this.dependenciaInforme = dependenciaInforme;
	}

	/**
	 * Gets the nombre documento fac.
	 *
	 * @return the nombre documento fac
	 */
	public String getNombreDocumentoFac() {
		return nombreDocumentoFac;
	}

	/**
	 * Sets the nombre documento fac.
	 *
	 * @param nombreDocumentoFac
	 *            the new nombre documento fac
	 */
	public void setNombreDocumentoFac(String nombreDocumentoFac) {
		this.nombreDocumentoFac = nombreDocumentoFac;
	}

	/**
	 * Gets the fecha aprobacion.
	 *
	 * @return the fecha aprobacion
	 */
	public Date getFechaAprobacion() {
		return fechaAprobacion;
	}

	/**
	 * Sets the fecha aprobacion.
	 *
	 * @param fechaAprobacion
	 *            the new fecha aprobacion
	 */
	public void setFechaAprobacion(Date fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}

	/**
	 * Gets the fecha lectura.
	 *
	 * @return the fecha lectura
	 */
	public Date getFechaLectura() {
		return fechaLectura;
	}

	/**
	 * Sets the fecha lectura.
	 *
	 * @param fechaLectura
	 *            the new fecha lectura
	 */
	public void setFechaLectura(Date fechaLectura) {
		this.fechaLectura = fechaLectura;
	}

	/**
	 * Gets the no aprobacion.
	 *
	 * @return the no aprobacion
	 */
	public String getNoAprobacion() {
		return noAprobacion;
	}

	/**
	 * Sets the no aprobacion.
	 *
	 * @param noAprobacion
	 *            the new no aprobacion
	 */
	public void setNoAprobacion(String noAprobacion) {
		this.noAprobacion = noAprobacion;
	}

	/**
	 * Gets the bienes.
	 *
	 * @return the bienes
	 */
	public Set<Bien> getBienes() {
		return bienes;
	}

	/**
	 * Sets the bienes.
	 *
	 * @param bienes
	 *            the new bienes
	 */
	public void setBienes(Set<Bien> bienes) {
		this.bienes = bienes;
	}

	/**
	 * Gets the bienes list.
	 *
	 * @return the bienes list
	 */
	public List<Bien> getBienesList() {
		return new ArrayList<Bien>(bienes);
	}

	/**
	 * Sets the bienes list.
	 *
	 * @param bienesList
	 *            the new bienes list
	 */
	public void setBienesList(List<Bien> bienesList) {
		this.bienes = new TreeSet<Bien>(bienesList);
	}

	/**
	 * Gets the proyecto compromiso.
	 *
	 * @return el proyectoCompromiso
	 */
	public ProyectoCompromiso getProyectoCompromiso() {
		return proyectoCompromiso;
	}

	/**
	 * Sets the proyecto compromiso.
	 *
	 * @param proyectoCompromiso
	 *            el proyectoCompromiso a fijar
	 */
	public void setProyectoCompromiso(ProyectoCompromiso proyectoCompromiso) {
		this.proyectoCompromiso = proyectoCompromiso;
	}

	/**
	 * Gets the productos.
	 *
	 * @return the productos
	 */
	public Set<ProductoSara> getProductos() {
		return productos;
	}

	/**
	 * Sets the productos.
	 *
	 * @param productos
	 *            the new productos
	 */
	public void setProductos(Set<ProductoSara> productos) {
		this.productos = productos;
	}

	/**
	 * Gets the productos list.
	 *
	 * @return the productos list
	 */
	public List<ProductoSara> getProductosList() {
		return new ArrayList<ProductoSara>(productos);
	}

	/**
	 * Sets the productos.
	 *
	 * @param productos
	 *            the new productos
	 */
	public void setProductos(List<ProductoSara> productos) {
		this.productos = new TreeSet<ProductoSara>(productos);
	}

	/**
	 * Gets the compra equipos.
	 *
	 * @return the compra equipos
	 */
	public String getCompraEquipos() {
		return compraEquipos;
	}

	/**
	 * Sets the compra equipos.
	 *
	 * @param compraEquipos
	 *            the new compra equipos
	 */
	public void setCompraEquipos(String compraEquipos) {
		this.compraEquipos = compraEquipos;
	}

	/**
	 * Gets the formacion estudiantes.
	 *
	 * @return the formacion estudiantes
	 */
	public String getFormacionEstudiantes() {
		return formacionEstudiantes;
	}

	/**
	 * Sets the formacion estudiantes.
	 *
	 * @param formacionEstudiantes
	 *            the new formacion estudiantes
	 */
	public void setFormacionEstudiantes(String formacionEstudiantes) {
		this.formacionEstudiantes = formacionEstudiantes;
	}

	/**
	 * Gets the compromisos.
	 *
	 * @return the compromisos
	 */
	public String getCompromisos() {
		return compromisos;
	}

	/**
	 * Sets the compromisos.
	 *
	 * @param compromisos
	 *            the new compromisos
	 */
	public void setCompromisos(String compromisos) {
		this.compromisos = compromisos;
	}

	/**
	 * Gets the productos en sara.
	 *
	 * @return the productos en sara
	 */
	public String getProductosEnSara() {
		return productosEnSara;
	}

	/**
	 * Sets the productos en sara.
	 *
	 * @param productosEnSara
	 *            the new productos en sara
	 */
	public void setProductosEnSara(String productosEnSara) {
		this.productosEnSara = productosEnSara;
	}

	/**
	 * Gets the verificacion productos compromisos.
	 *
	 * @return the verificacion productos compromisos
	 */
	public String getVerificacionProductosCompromisos() {
		return verificacionProductosCompromisos;
	}

	/**
	 * Sets the verificacion productos compromisos.
	 *
	 * @param verificacionProductosCompromisos
	 *            the new verificacion productos compromisos
	 */
	public void setVerificacionProductosCompromisos(String verificacionProductosCompromisos) {
		this.verificacionProductosCompromisos = verificacionProductosCompromisos;
	}

	/**
	 * Gets the dependencia aval facultad.
	 *
	 * @return the dependencia aval facultad
	 */
	public String getDependenciaAvalFacultad() {
		return dependenciaAvalFacultad;
	}

	/**
	 * Sets the dependencia aval facultad.
	 *
	 * @param dependenciaAvalFacultad
	 *            the new dependencia aval facultad
	 */
	public void setDependenciaAvalFacultad(String dependenciaAvalFacultad) {
		this.dependenciaAvalFacultad = dependenciaAvalFacultad;
	}

	/**
	 * Gets the acto administrativo facultad.
	 *
	 * @return the acto administrativo facultad
	 */
	public String getActoAdministrativoFacultad() {
		return actoAdministrativoFacultad;
	}

	/**
	 * Sets the acto administrativo facultad.
	 *
	 * @param actoAdministrativoFacultad
	 *            the new acto administrativo facultad
	 */
	public void setActoAdministrativoFacultad(String actoAdministrativoFacultad) {
		this.actoAdministrativoFacultad = actoAdministrativoFacultad;
	}

	/**
	 * Gets the numero acto administrativo facultad.
	 *
	 * @return the numero acto administrativo facultad
	 */
	public String getNumeroActoAdministrativoFacultad() {
		return numeroActoAdministrativoFacultad;
	}

	/**
	 * Sets the numero acto administrativo facultad.
	 *
	 * @param numeroActoAdministrativoFacultad
	 *            the new numero acto administrativo facultad
	 */
	public void setNumeroActoAdministrativoFacultad(String numeroActoAdministrativoFacultad) {
		this.numeroActoAdministrativoFacultad = numeroActoAdministrativoFacultad;
	}

	/**
	 * Gets the pry tiene saldo ejecucion.
	 *
	 * @return the pry tiene saldo ejecucion
	 */
	public String getPryTieneSaldoEjecucion() {
		return pryTieneSaldoEjecucion;
	}

	/**
	 * Sets the pry tiene saldo ejecucion.
	 *
	 * @param pryTieneSaldoEjecucion
	 *            the new pry tiene saldo ejecucion
	 */
	public void setPryTieneSaldoEjecucion(String pryTieneSaldoEjecucion) {
		this.pryTieneSaldoEjecucion = pryTieneSaldoEjecucion;
	}

	/**
	 * Gets the pry saldo ejecucion.
	 *
	 * @return the pry saldo ejecucion
	 */
	public Integer getPrySaldoEjecucion() {
		return prySaldoEjecucion;
	}

	/**
	 * Sets the pry saldo ejecucion.
	 *
	 * @param prySaldoEjecucion
	 *            the new pry saldo ejecucion
	 */
	public void setPrySaldoEjecucion(Integer prySaldoEjecucion) {
		this.prySaldoEjecucion = prySaldoEjecucion;
	}

	/**
	 * Gets the dependencia saldo ejecucion.
	 *
	 * @return the dependencia saldo ejecucion
	 */
	public String getDependenciaSaldoEjecucion() {
		return dependenciaSaldoEjecucion;
	}

	/**
	 * Sets the dependencia saldo ejecucion.
	 *
	 * @param dependenciaSaldoEjecucion
	 *            the new dependencia saldo ejecucion
	 */
	public void setDependenciaSaldoEjecucion(String dependenciaSaldoEjecucion) {
		this.dependenciaSaldoEjecucion = dependenciaSaldoEjecucion;
	}

	/**
	 * Gets the fecha acto administrativo facultad.
	 *
	 * @return the fecha acto administrativo facultad
	 */
	public Date getFechaActoAdministrativoFacultad() {
		return fechaActoAdministrativoFacultad;
	}

	/**
	 * Sets the fecha acto administrativo facultad.
	 *
	 * @param fechaActoAdministrativoFacultad
	 *            the new fecha acto administrativo facultad
	 */
	public void setFechaActoAdministrativoFacultad(Date fechaActoAdministrativoFacultad) {
		this.fechaActoAdministrativoFacultad = fechaActoAdministrativoFacultad;
	}

	/**
	 * Gets the observaciones.
	 *
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * Sets the observaciones.
	 *
	 * @param observaciones
	 *            the new observaciones
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	/**
	 * Gets the sipnosis d.
	 *
	 * @return the sipnosis d
	 */
	public String getSipnosisD() {
		return sipnosisD;
	}

	/**
	 * Sets the sipnosis d.
	 *
	 * @param sipnosisD
	 *            the new sipnosis d
	 */
	public void setSipnosisD(String sipnosisD) {
		this.sipnosisD = sipnosisD;
	}

	/**
	 * Gets the impacto d.
	 *
	 * @return the impacto d
	 */
	public String getImpactoD() {
		return impactoD;
	}

	/**
	 * Sets the impacto d.
	 *
	 * @param impactoD
	 *            the new impacto d
	 */
	public void setImpactoD(String impactoD) {
		this.impactoD = impactoD;
	}

	/**
	 * Gets the fecha desde.
	 *
	 * @return the fecha desde
	 */
	public Date getFechaDesde() {
		return fechaDesde;
	}

	/**
	 * Sets the fecha desde.
	 *
	 * @param fechaDesde
	 *            the new fecha desde
	 */
	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	/**
	 * Gets the fecha hasta.
	 *
	 * @return the fecha hasta
	 */
	public Date getFechaHasta() {
		return fechaHasta;
	}

	/**
	 * Sets the fecha hasta.
	 *
	 * @param fechaHasta
	 *            the new fecha hasta
	 */
	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	/**
	 * Gets the porcentaje ejecucion.
	 *
	 * @return the porcentaje ejecucion
	 */
	public String getPorcentajeEjecucion() {
		return porcentajeEjecucion;
	}

	/**
	 * Sets the porcentaje ejecucion.
	 *
	 * @param porcentajeEjecucion
	 *            the new porcentaje ejecucion
	 */
	public void setPorcentajeEjecucion(String porcentajeEjecucion) {
		this.porcentajeEjecucion = porcentajeEjecucion;
	}

	/**
	 * Gets the id estudiante.
	 *
	 * @return the id estudiante
	 */
	public String getIdEstudiante() {
		return idEstudiante;
	}

	/**
	 * Sets the id estudiante.
	 *
	 * @param idEstudiante
	 *            the new id estudiante
	 */
	public void setIdEstudiante(String idEstudiante) {
		this.idEstudiante = idEstudiante;
	}

	/**
	 * Gets the tipo doc estudiante.
	 *
	 * @return the tipo doc estudiante
	 */
	public String getTipoDocEstudiante() {
		return tipoDocEstudiante;
	}

	/**
	 * Sets the tipo doc estudiante.
	 *
	 * @param tipoDocEstudiante
	 *            the new tipo doc estudiante
	 */
	public void setTipoDocEstudiante(String tipoDocEstudiante) {
		this.tipoDocEstudiante = tipoDocEstudiante;
	}

	/**
	 * Gets the calificacion.
	 *
	 * @return the calificacion
	 */
	public String getCalificacion() {
		return calificacion;
	}

	/**
	 * Sets the calificacion.
	 *
	 * @param calificacion
	 *            the new calificacion
	 */
	public void setCalificacion(String calificacion) {
		this.calificacion = calificacion;
	}

	/**
	 * Gets the nombre estudiante.
	 *
	 * @return the nombre estudiante
	 */
	public String getNombreEstudiante() {
		return nombreEstudiante;
	}

	/**
	 * Sets the nombre estudiante.
	 *
	 * @param nombreEstudiante
	 *            the new nombre estudiante
	 */
	public void setNombreEstudiante(String nombreEstudiante) {
		this.nombreEstudiante = nombreEstudiante;
	}

	/**
	 * Gets the id responsable.
	 *
	 * @return the id responsable
	 */
	public String getIdResponsable() {
		return idResponsable;
	}

	/**
	 * Sets the id responsable.
	 *
	 * @param idResponsable
	 *            the new id responsable
	 */
	public void setIdResponsable(String idResponsable) {
		this.idResponsable = idResponsable;
	}

	/**
	 * Gets the tipo doc responsable.
	 *
	 * @return the tipo doc responsable
	 */
	public String getTipoDocResponsable() {
		return tipoDocResponsable;
	}

	/**
	 * Sets the tipo doc responsable.
	 *
	 * @param tipoDocResponsable
	 *            the new tipo doc responsable
	 */
	public void setTipoDocResponsable(String tipoDocResponsable) {
		this.tipoDocResponsable = tipoDocResponsable;
	}

	/**
	 * Gets the monto solicitado.
	 *
	 * @return the monto solicitado
	 */
	public Long getMontoSolicitado() {
		return montoSolicitado;
	}

	/**
	 * Sets the monto solicitado.
	 *
	 * @param montoSolicitado
	 *            the new monto solicitado
	 */
	public void setMontoSolicitado(Long montoSolicitado) {
		this.montoSolicitado = montoSolicitado;
	}

	/**
	 * Gets the no lectura.
	 *
	 * @return the no lectura
	 */
	public String getNoLectura() {
		return noLectura;
	}

	/**
	 * Sets the no lectura.
	 *
	 * @param noLectura
	 *            the new no lectura
	 */
	public void setNoLectura(String noLectura) {
		this.noLectura = noLectura;
	}

	/**
	 * Gets the revisor facultad.
	 *
	 * @return the revisor facultad
	 */
	public Persona getRevisorFacultad() {
		return revisorFacultad;
	}

	/**
	 * Sets the revisor facultad.
	 *
	 * @param revisorFacultad
	 *            the new revisor facultad
	 */
	public void setRevisorFacultad(Persona revisorFacultad) {
		this.revisorFacultad = revisorFacultad;
	}

	/**
	 * Gets the revisor coordinador.
	 *
	 * @return the revisor coordinador
	 */
	public Persona getRevisorCoordinador() {
		return revisorCoordinador;
	}

	/**
	 * Sets the revisor coordinador.
	 *
	 * @param revisorCoordinador
	 *            the new revisor coordinador
	 */
	public void setRevisorCoordinador(Persona revisorCoordinador) {
		this.revisorCoordinador = revisorCoordinador;
	}

	/**
	 * Gets the fecha eliminacion.
	 *
	 * @return the fecha eliminacion
	 */
	public Date getFechaEliminacion() {
		return fechaEliminacion;
	}

	/**
	 * Sets the fecha eliminacion.
	 *
	 * @param fechaEliminacion
	 *            the new fecha eliminacion
	 */
	public void setFechaEliminacion(Date fechaEliminacion) {
		this.fechaEliminacion = fechaEliminacion;
	}

	/**
	 * Gets the responsable eliminacion.
	 *
	 * @return the responsable eliminacion
	 */
	public Persona getResponsableEliminacion() {
		return responsableEliminacion;
	}

	/**
	 * Sets the responsable eliminacion.
	 *
	 * @param responsableEliminacion
	 *            the new responsable eliminacion
	 */
	public void setResponsableEliminacion(Persona responsableEliminacion) {
		this.responsableEliminacion = responsableEliminacion;
	}

	/**
	 * Gets the porcentaje ejecucion presupuestal.
	 *
	 * @return the porcentaje ejecucion presupuestal
	 */
	public String getPorcentajeEjecucionPresupuestal() {
		return porcentajeEjecucionPresupuestal;
	}

	/**
	 * Sets the porcentaje ejecucion presupuestal.
	 *
	 * @param porcentajeEjecucionPresupuestal
	 *            the new porcentaje ejecucion presupuestal
	 */
	public void setPorcentajeEjecucionPresupuestal(String porcentajeEjecucionPresupuestal) {
		this.porcentajeEjecucionPresupuestal = porcentajeEjecucionPresupuestal;
	}

	/**
	 * Gets the numero notificaciones.
	 *
	 * @return the numero notificaciones
	 */
	public Long getNumeroNotificaciones() {
		return numeroNotificaciones;
	}

	/**
	 * Sets the numero notificaciones.
	 *
	 * @param numeroNotificaciones
	 *            the new numero notificaciones
	 */
	public void setNumeroNotificaciones(Long numeroNotificaciones) {
		this.numeroNotificaciones = numeroNotificaciones;
	}

	/**
	 * Gets the total valor ejecutado.
	 *
	 * @return the totalValorEjecutado
	 */
	public Long getTotalValorEjecutado() {
		return totalValorEjecutado;
	}

	/**
	 * Sets the total valor ejecutado.
	 *
	 * @param totalValorEjecutado the totalValorEjecutado to set
	 */
	public void setTotalValorEjecutado(Long totalValorEjecutado) {
		this.totalValorEjecutado = totalValorEjecutado;
	}

	/**
	 * Gets the numero participantes evento.
	 *
	 * @return the numeroParticipantesEvento
	 */
	public Long getNumeroParticipantesEvento() {
		return numeroParticipantesEvento;
	}

	/**
	 * Sets the numero participantes evento.
	 *
	 * @param numeroParticipantesEvento the numeroParticipantesEvento to set
	 */
	public void setNumeroParticipantesEvento(Long numeroParticipantesEvento) {
		this.numeroParticipantesEvento = numeroParticipantesEvento;
	}

	/**
	 * Gets the numero estudiantes evento.
	 *
	 * @return the numeroEstudiantesEvento
	 */
	public Long getNumeroEstudiantesEvento() {
		return numeroEstudiantesEvento;
	}

	/**
	 * Sets the numero estudiantes evento.
	 *
	 * @param numeroEstudiantesEvento the numeroEstudiantesEvento to set
	 */
	public void setNumeroEstudiantesEvento(Long numeroEstudiantesEvento) {
		this.numeroEstudiantesEvento = numeroEstudiantesEvento;
	}

	/**
	 * Gets the numero externos evento.
	 *
	 * @return the numeroExternosEvento
	 */
	public Long getNumeroExternosEvento() {
		return numeroExternosEvento;
	}

	/**
	 * Sets the numero externos evento.
	 *
	 * @param numeroExternosEvento the numeroExternosEvento to set
	 */
	public void setNumeroExternosEvento(Long numeroExternosEvento) {
		this.numeroExternosEvento = numeroExternosEvento;
	}

	/**
	 * Gets the recolecta especimenes.
	 *
	 * @return the recolecta especimenes
	 */
	public String getRecolectaEspecimenes() {
		return recolectaEspecimenes;
	}

	/**
	 * Sets the recolecta especimenes.
	 *
	 * @param recolectaEspecimenes the new recolecta especimenes
	 */
	public void setRecolectaEspecimenes(String recolectaEspecimenes) {
		this.recolectaEspecimenes = recolectaEspecimenes;
	}

	/**
	 * Checks if is movilizo especimenes.
	 *
	 * @return true, if is movilizo especimenes
	 */
	public boolean isMovilizoEspecimenes() {
		movilizoEspecimenes = false;
		if(recolectaEspecimenes!=null && recolectaEspecimenes.equals("S")){
			return true;
		}
		return movilizoEspecimenes;
	}

	/**
	 * Sets the movilizo especimenes.
	 *
	 * @param movilizoEspecimenes the new movilizo especimenes
	 */
	public void setMovilizoEspecimenes(boolean movilizoEspecimenes) {
		this.movilizoEspecimenes = movilizoEspecimenes;
	}

	public boolean isMostrarCompromisos() {
		return mostrarCompromisos;
	}

	public void setMostrarCompromisos(boolean mostrarCompromisos) {
		this.mostrarCompromisos = mostrarCompromisos;
	}

	public Long getMontoTotalEjecutadoPeriodo() {
		return montoTotalEjecutadoPeriodo;
	}

	public void setMontoTotalEjecutadoPeriodo(Long montoTotalEjecutadoPeriodo) {
		this.montoTotalEjecutadoPeriodo = montoTotalEjecutadoPeriodo;
	}

	public Long getMontoTotalEjecutadoPeriodoExt() {
		return montoTotalEjecutadoPeriodoExt;
	}

	public void setMontoTotalEjecutadoPeriodoExt(Long montoTotalEjecutadoPeriodoExt) {
		this.montoTotalEjecutadoPeriodoExt = montoTotalEjecutadoPeriodoExt;
	}

	public String getPorcentajeEjecucionPresupuestalExterno() {
		return porcentajeEjecucionPresupuestalExterno;
	}

	public void setPorcentajeEjecucionPresupuestalExterno(String porcentajeEjecucionPresupuestalExterno) {
		this.porcentajeEjecucionPresupuestalExterno = porcentajeEjecucionPresupuestalExterno;
	}

	public String getMostartMontoEjecutadoExt() {
		return mostartMontoEjecutadoExt;
	}

	public void setMostartMontoEjecutadoExt(String mostartMontoEjecutadoExt) {
		this.mostartMontoEjecutadoExt = mostartMontoEjecutadoExt;
	}

	public Set<ProyectoInformeObjetivoEspecifico> getObjetivos() {
		return objetivos;
	}

	public void setObjetivos(Set<ProyectoInformeObjetivoEspecifico> objetivos) {
		this.objetivos = objetivos;
	}
	
    public List<ProyectoInformeObjetivoEspecifico> getListaObjetivos() {
        List listaObjetivos = new ArrayList();
        listaObjetivos.addAll(objetivos);
        return listaObjetivos;
    }
    
    public void adicionarObjetivo(ObjetivoEspecifico objetivo) {
        if (objetivos == null) {
        	objetivos = new HashSet<ProyectoInformeObjetivoEspecifico>();
        }
        ProyectoInformeObjetivoEspecifico objetivoInforme = new ProyectoInformeObjetivoEspecifico();
        objetivoInforme.setInforme(this);
        objetivoInforme.setObjetivo(objetivo);
        objetivos.add(objetivoInforme);
    }

	public Set<ProyectoInformeActividad> getActividades() {
		return actividades;
	}

	public void setActividades(Set<ProyectoInformeActividad> actividades) {
		this.actividades = actividades;
	}
	
    public List<ProyectoInformeActividad> getListaActividades() {
        List listaActvidades = new ArrayList();
        listaActvidades.addAll(actividades);
        return listaActvidades;
    }
    
    public void adicionarActividad(Actividad actividad) {
        if (actividades == null) {
        	actividades = new HashSet<ProyectoInformeActividad>();
        }
        ProyectoInformeActividad actividadInforme = new ProyectoInformeActividad();
        actividadInforme.setInforme(this);
        actividadInforme.setActividad(actividad);
        actividades.add(actividadInforme);
    }

	public Set<ProyectoInformeResultado> getResultados() {
		return resultados;
	}

	public void setResultados(Set<ProyectoInformeResultado> resultados) {
		this.resultados = resultados;
	}
	
    public List<ProyectoInformeResultado> getListaResultados() {
        List listaResultados = new ArrayList();
        listaResultados.addAll(resultados);
        return listaResultados;
    }
    
    public void adicionarResultado(ResultadoProyecto resultado) {
        if (resultados == null) {
        	resultados = new HashSet<ProyectoInformeResultado>();
        }
        ProyectoInformeResultado resultadoInforme = new ProyectoInformeResultado();
        resultadoInforme.setInforme(this);
        resultadoInforme.setResultado(resultado);
        resultados.add(resultadoInforme);
    }

}