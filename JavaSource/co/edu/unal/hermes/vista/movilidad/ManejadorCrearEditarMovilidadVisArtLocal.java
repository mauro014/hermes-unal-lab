package co.edu.unal.hermes.vista.movilidad;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.myfaces.custom.fileupload.UploadedFile;

import co.edu.unal.hermes.modelo.ActividadMovilidad;
import co.edu.unal.hermes.modelo.ArchivoMovilidad;
import co.edu.unal.hermes.modelo.ArchivoMovilidadDA;
import co.edu.unal.hermes.modelo.CategoriaInvestigador;
import co.edu.unal.hermes.modelo.Continente;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Institucion;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorExterno;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.MovilidadArchivo;
import co.edu.unal.hermes.modelo.MovilidadVisitantesArtes;
import co.edu.unal.hermes.modelo.Pais;
import co.edu.unal.hermes.modelo.Parametro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Rol;
import co.edu.unal.hermes.modelo.TipoArchivoMovilidad;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoFormacion;
import co.edu.unal.hermes.modelo.TipoMovilidad;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.utils.Fecha;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorCrearEditarMovilidadVisArtLocal extends ManejadorBase {

	private MovilidadVisitantesArtes movArt;

	private String errores[];
	private boolean panelRender[];
	private boolean panelRenderError[];
	private boolean banderaConvenio = false;

	private String documento;
	private TipoDocumento tipoDocumento;
	private SelectItem[] tipoDocumentoItem;
	private SelectItem[] tipoDocumentoSelItem;

	private List paises;
	private List nacionalidad;
	private List continentes;
	private List tipoFormacion;
	private List listaArchivos;
	private List areaArte;

	private List tipoInstitucion;
	private List convenio;
	private List comoEntero;
	private List listaActividades;
	private List listaArchivosObligatoriosSel;
	private List socializacion;

	// VARIABLES TEMPORALES
	private String sede;
	private String facultadDocente;
	private String departamentoDocente;
	private String nombreDocente;
	private String documentoDocente;
	private String descripcionActividad;
	private Date fechaActividad;
	private String duracionActividad;
	private String tipoDocumentoSel;
	private UploadedFile archivoObligatorio;

	private HtmlDataTable tablaArchivos;
	private HtmlDataTable tablaActividadesSel;
	private HtmlDataTable tablaArchivosObligatoriosSel;
	
	String nombreUno;
	String nombreDos;
	String apellidoUno;
	String apellidoDos;
	String email;
	String genero;

	public ManejadorCrearEditarMovilidadVisArtLocal() {
		
		movArt = new MovilidadVisitantesArtes();

		cargarValoresIniciales();
		reiniciarVariables();

		Long idModalidad_ = (Long) super.sesion.getAttribute("idMovilidad");
		if (idModalidad_ != null) {
			infoModalidad(idModalidad_);
		}

	}

	private void infoModalidad(Long idModalidad_) {
		List listaMovilidadConsulta;
		listaMovilidadConsulta = new ArrayList();

		listaMovilidadConsulta = servicioGeneral
				.obtenerListaObjetos("MovilidadDocentesArtes where id = '"
						+ idModalidad_ + "'");
		if (listaMovilidadConsulta != null && listaMovilidadConsulta.size() > 0) {
			movArt = (MovilidadVisitantesArtes) listaMovilidadConsulta.get(0);
		}
	}

	private void cargarTiposDocumentos() {
		List listaArchivosObligatorios = new ArrayList();
		listaArchivosObligatorios = servicioGeneral
				.obtenerListaArchivosMovilidad("C3");
		if (listaArchivosObligatorios != null
				&& listaArchivosObligatorios.size() > 0) {
			tipoDocumentoSelItem = new SelectItem[listaArchivosObligatorios
					.size()];
			for (int i = 0; i < listaArchivosObligatorios.size(); i++) {
				MovilidadArchivo mva = (MovilidadArchivo) listaArchivosObligatorios
						.get(i);
				tipoDocumentoSelItem[i] = new SelectItem(mva.getTipoArchivo()
						.getId().toString(), mva.getTipoArchivo().getNombre());
			}
		}
	}

	private void cargarContinentes() {
		List listaContinentes = servicioGeneral
				.obtenerListaObjetosOrdenadosAsc(new Continente(), "nombre");
		continentes = new Vector();
		for (Iterator it = listaContinentes.iterator(); it.hasNext();) {
			Continente p = (Continente) it.next();
			SelectItem s = new SelectItem(p.getId(), p.getNombre());
			continentes.add(s);
		}
	}
	
	private void cargarTipoFormacion() {
		List listaFormacion = servicioGeneral
				.obtenerListaObjetosOrdenadosAsc(new TipoFormacion(), "nombre");
		tipoFormacion = new Vector();
		for (Iterator it = listaFormacion.iterator(); it.hasNext();) {
			TipoFormacion p = (TipoFormacion) it.next();
			SelectItem s = new SelectItem(p.getId(), p.getNombre());
			tipoFormacion.add(s);
		}
	}

	private void reiniciarVariables() {
		movArt = new MovilidadVisitantesArtes();
		errores = new String[60];
		panelRender = new boolean[10];
		panelRenderError = new boolean[60];
	    banderaConvenio = false;
		Pais nacionalidad = new Pais();
		nacionalidad.setId("CO");
	
		movArt.setNacionalidad(nacionalidad);
		Continente continente = new Continente();
		continente.setId(new Long("1"));
		movArt.setContinente(continente);
		TipoFormacion formacion = new TipoFormacion();
		formacion.setId("DO");
		movArt.setFormacion(formacion);
		this.nombreUno = "";
		this.nombreDos = "";
		this.apellidoUno = "";
		this.apellidoDos = "";
		this.email = "";
		genero = "M";
	}

	public void limpiar() {
		movArt = new MovilidadVisitantesArtes();
		documento = new String("");
		banderaConvenio = false;
		cargarValoresIniciales();
		ocultarPaneles(0);
	}

	void processChild(List childList) {
		for (int i = 0; i < childList.size(); i++) {
			UIComponent component = (UIComponent) childList.get(i);
			try {
				UIInput input = (UIInput) component;
				input.setSubmittedValue(null);
			} catch (Exception ex) {

			}
			List childList2 = component.getChildren();
			processChild(childList2);
		}
	}

	public void cancelAction(ActionEvent event) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot viewRoot = facesContext.getViewRoot();
		List childList = viewRoot.getChildren();
		processChild(childList);
	}
	
	 public void cambiarConvenio(ValueChangeEvent event) {
	    	
	    	String valor = (String)event.getNewValue();
	    	
	    	if(valor.equals("SI")){
	    		banderaConvenio  = true;
	    	}
	    	
	    	if(valor.equals("NO")){
	    		banderaConvenio  = false;
	    	}
	    	
	 }   	 
	    	
	

	public boolean validarPresupuesto() {
		boolean bandera = true;
		int totalUN = 0;
		int totalPropio = 0;

		if (this.movArt.getTiquetesUN() != null) {
			try {
				int cantidad = Integer.parseInt(movArt.getTiquetesUN());
				totalUN = totalUN + cantidad;
			} catch (Exception e) {
				this.errores[29] = "Valor NO valido";
				this.panelRenderError[29] = true;
				bandera = false;
			}
		}

		if (this.movArt.getTiquetesP() != null) {
			try {
				int cantidad = Integer.parseInt(movArt.getTiquetesP());
				totalPropio = totalPropio + cantidad;
			} catch (Exception e) {
				this.errores[30] = "Valor NO valido";
				this.panelRenderError[30] = true;
				bandera = false;
			}
		}

		if (this.movArt.getTallerUN() != null) {
			try {
				int cantidad = Integer.parseInt(movArt.getTallerUN());
				totalUN = totalUN + cantidad;
			} catch (Exception e) {
				this.errores[31] = "Valor NO valido";
				this.panelRenderError[31] = true;
				bandera = false;
			}
		}

		if (this.movArt.getTallerP() != null) {
			try {
				int cantidad = Integer.parseInt(movArt.getTallerP());
				totalPropio = totalPropio + cantidad;
			} catch (Exception e) {
				this.errores[32] = "Valor NO valido";
				this.panelRenderError[32] = true;
				bandera = false;
			}
		}

		if (this.movArt.getAlojamientoUN() != null) {
			try {
				int cantidad = Integer.parseInt(movArt.getAlojamientoUN());
				totalUN = totalUN + cantidad;
			} catch (Exception e) {
				this.errores[33] = "Valor NO valido";
				this.panelRenderError[33] = true;
				bandera = false;
			}
		}

		if (this.movArt.getAlojamientoP() != null) {
			try {
				int cantidad = Integer.parseInt(movArt.getAlojamientoP());
				totalPropio = totalPropio + cantidad;
			} catch (Exception e) {
				this.errores[34] = "Valor NO valido";
				this.panelRenderError[34] = true;
				bandera = false;
			}
		}

		if (this.movArt.getAlimentacionUN() != null) {
			try {
				int cantidad = Integer.parseInt(movArt.getAlimentacionUN());
				totalUN = totalUN + cantidad;
			} catch (Exception e) {
				this.errores[35] = "Valor NO valido";
				this.panelRenderError[35] = true;
				bandera = false;
			}
		}

		if (this.movArt.getAlimentacionP() != null) {
			try {
				int cantidad = Integer.parseInt(movArt.getAlimentacionP());
				totalPropio = totalPropio + cantidad;
			} catch (Exception e) {
				this.errores[36] = "Valor NO valido";
				this.panelRenderError[36] = true;
				bandera = false;
			}
		}

		if (this.movArt.getMaterialesUN() != null) {
			try {
				int cantidad = Integer.parseInt(movArt.getMaterialesUN());
				totalUN = totalUN + cantidad;
			} catch (Exception e) {
				this.errores[37] = "Valor NO valido";
				this.panelRenderError[37] = true;
				bandera = false;
			}
		}

		if (this.movArt.getMaterialesP() != null) {
			try {
				int cantidad = Integer.parseInt(movArt.getMaterialesP());
				totalPropio = totalPropio + cantidad;
			} catch (Exception e) {
				this.errores[38] = "Valor NO valido";
				this.panelRenderError[38] = true;
				bandera = false;
			}
		}

		if (this.movArt.getTransporteUN() != null) {
			try {
				int cantidad = Integer.parseInt(movArt.getTransporteUN());
				totalUN = totalUN + cantidad;
			} catch (Exception e) {
				this.errores[39] = "Valor NO valido";
				this.panelRenderError[39] = true;
				bandera = false;
			}
		}

		if (this.movArt.getTransporteP() != null) {
			try {
				int cantidad = Integer.parseInt(movArt.getTransporteP());
				totalPropio = totalPropio + cantidad;
			} catch (Exception e) {
				this.errores[40] = "Valor NO valido";
				this.panelRenderError[40] = true;
				bandera = false;
			}
		}

		if (this.movArt.getSocializacionUN() != null) {
			try {
				int cantidad = Integer.parseInt(movArt.getSocializacionUN());
				totalUN = totalUN + cantidad;
			} catch (Exception e) {
				this.errores[41] = "Valor NO valido";
				this.panelRenderError[41] = true;
				bandera = false;
			}
		}

		if (this.movArt.getSocializacionP() != null) {
			try {
				int cantidad = Integer.parseInt(movArt.getSocializacionP());
				totalPropio = totalPropio + cantidad;
			} catch (Exception e) {
				this.errores[42] = "Valor NO valido";
				this.panelRenderError[42] = true;
				bandera = false;
			}
		}

		int africaDocente = 0;
		int americaDocente = 0;
		int asiaDocente = 0;
		int europaDocente = 0;
		int salario = 1;

		List listaParametroUno = this.servicioGeneral
				.obtenerObjetos("FROM Parametro WHERE nombre = 'AFRICA_EXTRANJERO'");
		List listaParametroDos = this.servicioGeneral
				.obtenerObjetos("FROM Parametro WHERE nombre = 'AMERICA_EXTRANJERO'");
		List listaParametroTres = this.servicioGeneral
				.obtenerObjetos("FROM Parametro WHERE nombre = 'ASIA_EXTRANJERO'");
		List listaParametroCuatro = this.servicioGeneral
				.obtenerObjetos("FROM Parametro WHERE nombre = 'EUROPA_EXTRANJERO'");
		List listaParametroSalario = this.servicioGeneral
				.obtenerObjetos("FROM Parametro WHERE id = 43");

		if (listaParametroUno != null && listaParametroUno.size() > 0) {
			Parametro par = (Parametro) listaParametroUno.get(0);
			africaDocente = Integer.valueOf((par.getValor())).intValue();
		}

		if (listaParametroDos != null && listaParametroDos.size() > 0) {
			Parametro par = (Parametro) listaParametroDos.get(0);
			americaDocente = Integer.valueOf((par.getValor())).intValue();
		}

		if (listaParametroTres != null && listaParametroTres.size() > 0) {
			Parametro par = (Parametro) listaParametroTres.get(0);
			asiaDocente = Integer.valueOf((par.getValor())).intValue();
		}

		if (listaParametroCuatro != null && listaParametroCuatro.size() > 0) {
			Parametro par = (Parametro) listaParametroCuatro.get(0);
			europaDocente = Integer.valueOf((par.getValor())).intValue();
		}

		if (listaParametroSalario != null && listaParametroSalario.size() > 0) {
			Parametro par = (Parametro) listaParametroSalario.get(0);
			salario = Integer.valueOf((par.getValor())).intValue();
		}

		if (this.movArt.getContinente().getId() == 1
				&& totalUN > (africaDocente * salario)) {
			this.errores[2] = "Valor excede el monto para el continente seleccionado";
			this.panelRenderError[2] = true;
			bandera = false;
		}

		if (this.movArt.getContinente().getId() == 2
				&& totalUN > (americaDocente * salario)) {
			this.errores[2] = "Valor excede el monto para el continente seleccionado";
			this.panelRenderError[2] = true;
			bandera = false;

		}

		if ((this.movArt.getContinente().getId() == 3 || this.movArt
				.getContinente().getId() == 5)
				&& (totalUN > (asiaDocente * salario))) {
			this.errores[2] = "Valor excede el monto para el continente seleccionado";
			this.panelRenderError[2] = true;
			bandera = false;

		}

		if (this.movArt.getContinente().getId() == 4
				&& totalUN > (europaDocente * salario)) {
			this.errores[2] = "Valor excede el monto para el continente seleccionado";
			this.panelRenderError[2] = true;
			bandera = false;
		}

		this.movArt.setTotalUN(String.valueOf(totalUN));
		this.movArt.setTotalPropio(String.valueOf(totalPropio));

		return bandera;
	}

	public void calcularTotales() {
		limpiarErores();
		if (validarPresupuesto()) {
			ocultarPaneles(11);
		}

	}

	public void guardarArchivoObligatorio() {
		try {
			if (archivoObligatorio.getBytes() != null) {
				List listaTipoArchivo = new ArrayList();
				List listaTipoMovilidad = new ArrayList();
				int i = archivoObligatorio.getName().lastIndexOf("\\");
				ArchivoMovilidad archivoMovilidad = new ArchivoMovilidad();

				listaTipoMovilidad = servicioGeneral
						.obtenerListaObjetos("TipoMovilidad where id ='C3'");
				TipoMovilidad tipoMovilidad = (TipoMovilidad) listaTipoMovilidad
						.get(0);
				archivoMovilidad.setBytes(archivoObligatorio.getBytes());
				archivoMovilidad.setNombre(archivoObligatorio.getName()
						.substring(i + 1));
				archivoMovilidad.setFecha(new Date());
				archivoMovilidad.setTipoMovilidad(tipoMovilidad);
				listaTipoArchivo = servicioGeneral
						.obtenerListaObjetos("TipoArchivoMovilidad where id ='"
								+ tipoDocumentoSel + "'");
				TipoArchivoMovilidad tipoArchivo = (TipoArchivoMovilidad) listaTipoArchivo
						.get(0);
				archivoMovilidad.setTipoArchivo(tipoArchivo);

				listaArchivosObligatoriosSel.add(archivoMovilidad);

			}

		} catch (Exception x) {
			System.out.println(x.toString());

		}
	}

	public void eliminarArchivoObligatorio() {
		ArchivoMovilidad amv = (ArchivoMovilidad) tablaArchivosObligatoriosSel
				.getRowData();
		listaArchivosObligatoriosSel.remove(tablaArchivosObligatoriosSel
				.getRowIndex());
	}

	public void buscarPersona() throws SQLException {
		reiniciarVariables();
		IdPersona id = new IdPersona();
		id.setDocumento(documento);
		id.setTipoDocumento(tipoDocumento.getId());
		movArt.setPersonaInv(servicioPersona.obtenerPersonaRoles(id));

		//String sSql = "";
		//int nExiste = 0;
		//Date fechaActual = new Date();

		//SimpleDateFormat spd = new SimpleDateFormat("dd");
		//SimpleDateFormat spm = new SimpleDateFormat("MM");
		//SimpleDateFormat spy = new SimpleDateFormat("yyyy");

		//sSql = " SELECT COUNT(1) " + " FROM " + " HER_MOVILIDAD_DOCENTES_ART "
		//		+ " WHERE " + " MOV_ID_PER ='" + documento + "'"
		//		+ " AND MOV_APROB = 'SI' "
		//		+ " AND EXTRACT(YEAR FROM MOV_FEC_SOL) ='"
		//		+ Long.parseLong(spy.format(fechaActual)) + "'";

		//nExiste = servicioGeneral.existeMovilidad(sSql);

		//if (nExiste > 0) {
		//	errores[0] = "El investigador ya tiene aprobada una movilidad para este año.";
		//	ocultarPaneles(0);
		//} else {

			if (movArt.getPersonaInv() != null) {
				// idCiudad = persona.getCiudadDomicilio() == null ? null :
				// persona
				// .getCiudadDomicilio().getId();
				if (movArt.getPersonaInv() instanceof Investigador) {
					if (movArt.getPersonaInv() instanceof InvestigadorInterno) {
						movArt.setPersonaInv(servicioPersona
								.obtenerInvestigadorInternoCompleto(movArt
										.getPersonaInv().getId()));
						InvestigadorInterno investigadorInterno = (InvestigadorInterno) movArt
								.getPersonaInv();
						Dependencia dependencia;
						dependencia = servicioDependencia
								.obtenerDependencia(investigadorInterno.getId());
						String nombre1, nombre2, apellido1, apellido2;
						// Ing. Wilver Alexander Martínez Martínez (wam²)
						// Cambio para aceptar docentes de medio tiempo
						if (investigadorInterno.getTipoDedicacion() != null
								&& (investigadorInterno.getTipoDedicacion()
										.getId().equals(Investigador.EXCLUSIVA)
										|| investigadorInterno
												.getTipoDedicacion()
												.getId()
												.equals(
														Investigador.TIEMPOCOMPLETO) || investigadorInterno
										.getTipoDedicacion().getId().equals(
												Investigador.MEDIOTIEMPO))) {
							if (investigadorInterno.getNombre1() != null) {
								nombre1 = investigadorInterno.getNombre1();
							} else {
								nombre1 = "";
							}
							if (investigadorInterno.getNombre2() != null) {
								nombre2 = investigadorInterno.getNombre2();
							} else {
								nombre2 = "";
							}
							if (investigadorInterno.getApellido1() != null) {
								apellido1 = investigadorInterno.getApellido1();
							} else {
								apellido1 = "";
							}
							if (investigadorInterno.getApellido2() != null) {
								apellido2 = investigadorInterno.getApellido2();
							} else {
								apellido2 = "";
							}
							this.nombreDocente = nombre1 + " " + nombre2 + " "
									+ apellido1 + " " + apellido2;

							this.documentoDocente = investigadorInterno.getId()
									.getDocumento();

							if (dependencia!=null && dependencia.getFacultad() != null) {
								this.sede = dependencia.getSede().getNombre();
								this.facultadDocente = dependencia
										.getFacultad().getNombre();
								this.departamentoDocente = dependencia
										.getNombre();

								ocultarPaneles(3);

							} else {
								errores[0] = "La dependencia del investigador no tiene una facultad asociada";
								panelRenderError[0] = true;
								ocultarPaneles(0);
							}
						} else {
							errores[0] = "El investigador debe ser de dedicación exclusiva o tiempo completo de la Universidad Nacional de Colombia";
							panelRenderError[0] = true;
							ocultarPaneles(0);
						}
					} else {
						errores[0] = "El documento ingresado no corresponde a un investigador";
						panelRenderError[0] = true;
						ocultarPaneles(0);
					}
				} else {
					movArt.setPersonaInv(new Persona());
					IdPersona idP = new IdPersona();
					idP.setDocumento(documento);
					idP.setTipoDocumento(tipoDocumento.getId());
					movArt.getPersonaInv().setId(idP);
					errores[0] = "El documento ingresado no corresponde a un investigador";
					panelRenderError[0] = true;
					ocultarPaneles(0);
				}
			} else {
				movArt.setPersonaInv(new Persona());
				IdPersona idP = new IdPersona();
				idP.setDocumento(documento);
				idP.setTipoDocumento(tipoDocumento.getId());
				movArt.getPersonaInv().setId(idP);
				errores[0] = "El documento ingresado no existe";
				panelRenderError[0] = true;
				ocultarPaneles(0);
			}
		}
//	}

	public void adicionarActividad() {
		ActividadMovilidad actividad = new ActividadMovilidad();

		try {

			this.errores[25] = this.errores[26] = this.errores[27] = "";
			this.panelRenderError[25] = this.panelRenderError[26] = this.panelRenderError[27] = false;

			if (this.descripcionActividad == null
					|| this.descripcionActividad.equals("")
					|| this.descripcionActividad.length() > 2000) {
				this.errores[25] = "La descripción es NO valida";
				this.panelRenderError[25] = true;
			} else {
				actividad.setDescripcion(descripcionActividad);
			}

			if (this.fechaActividad == null) {
				this.errores[26] = "La fecha es NO valida";
				this.panelRenderError[26] = true;
			} else {
				if (this.fechaActividad.before(this.movArt.getFechainicial())) {
					this.errores[26] = "La fecha debe ser posterior a la fecha de inicio del viaje";
					this.panelRenderError[26] = true;

				} else if (this.fechaActividad.after(this.movArt
						.getFechafinal())) {
					this.errores[26] = "La fecha debe ser anterior a la fecha de fin del viaje";
					this.panelRenderError[26] = true;
				} else {
					actividad.setFecha(fechaActividad);
				}
			}

			if (this.duracionActividad == null
					|| this.duracionActividad.length() == 0) {
				this.errores[27] = "La duración es NO valida";
				this.panelRenderError[27] = true;
			} else {
				try {
					int cantidad = Integer.parseInt(this.duracionActividad);
					if (cantidad <= 0 || cantidad > 30) {
						this.errores[27] = "Duración fuera de rango (1 - 100)";
						this.panelRenderError[27] = true;
					} else {
						actividad.setDuracion(cantidad);
					}

				} catch (Exception e) {
					this.errores[27] = "La duración es NO valida";
					this.panelRenderError[27] = true;
				}
			}

			if (this.panelRenderError[25] == false
					&& this.panelRenderError[26] == false
					&& this.panelRenderError[27] == false) {
				this.descripcionActividad = "";
				this.fechaActividad = null;
				this.duracionActividad = null;
				this.listaActividades.add(actividad);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void eliminarActividad() {
		listaActividades.remove(tablaActividadesSel.getRowIndex());
	}

	public void verArchivo() {

		ArchivoMovilidadDA ain = (ArchivoMovilidadDA) tablaArchivos
				.getRowData();
		FacesContext ctx = FacesContext.getCurrentInstance();

		if (ain != null && ain.getArchivo() != null) {

			try {
				if (!ctx.getResponseComplete()) {
					HttpServletResponse response = (HttpServletResponse) ctx
							.getExternalContext().getResponse();
					response.setContentType("text/plain");
					response.setHeader("Content-Disposition",
							"attachment;filename=\"" + ain.getNombre() + "\"");
					ServletOutputStream out = response.getOutputStream();
					out.write(ain.getBytes());
					out.flush();
					ctx.responseComplete();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void eliminarArchivo() {
		listaArchivos.remove(tablaArchivos.getRowIndex());
	}

	public void limpiarErores() {
		for (int i = 0; i < 60; i++) {			
			this.errores[i] = "";
			this.panelRenderError[i] = false;
			
		}
	}

	public boolean validacion() {
		boolean bandera = true;
		limpiarErores();
		
		if (this.nombreUno == null
				|| this.nombreUno.length() <= 0
				|| this.nombreUno.length() > 20) {
			this.errores[1] = "Nombre Uno NO valido";
			this.panelRenderError[1] = true;
			bandera = false;
		}
		
		if (this.nombreDos != null && this.nombreDos.length() >0 && this.nombreDos.length() > 20) {
			this.errores[2] = "Nombre Dos NO valido";
			this.panelRenderError[2] = true;
			bandera = false;
		}
		
		if (this.apellidoUno == null
				|| this.apellidoUno.length() <= 0
				|| this.apellidoUno.length() > 20) {
			this.errores[3] = "Apellido Uno NO valido";
			this.panelRenderError[3] = true;
			bandera = false;
		}
		
		if (this.apellidoDos != null && this.apellidoDos.length() >0 && this.apellidoDos.length() > 20) {
			this.errores[4] = "Apellido Dos NO valido";
			this.panelRenderError[4] = true;
			bandera = false;
		}
		

		if (this.email == null || this.email.length() <=0 || this.email.length() > 60 || this.email.indexOf("@") == -1) {
			this.errores[5] = "Email NO valido";
			this.panelRenderError[5] = true;
			bandera = false;
		}
		

		if (this.movArt.getPasaporte() == null
				|| this.movArt.getPasaporte().length() <= 0
				|| this.movArt.getPasaporte().length() > 20) {
			this.errores[6] = "Pasaporte NO valido";
			this.panelRenderError[6] = true;
			bandera = false;
		}
		
		return bandera;

	}

	public void guardar() {

		if (validacion()) {
					

			List listaTipoMovilidad = servicioGeneral
					.obtenerListaObjetos("TipoMovilidad where id ='C3'");
			TipoMovilidad tipoMovilidad = (TipoMovilidad) listaTipoMovilidad
					.get(0);
			this.movArt.setTipoMovilidad(tipoMovilidad);
			
		
			
	
			Persona person = new Persona();
			IdPersona id =  new IdPersona(this.movArt.getPasaporte(), "P");
	    	person.setId(id);
	    	person.setApellido1(this.apellidoUno.trim().toUpperCase());
	    	person.setApellido2(this.apellidoDos.trim().toUpperCase());
	    	person.setNombre1(this.nombreUno.trim().toUpperCase());
	    	person.setNombre2(this.nombreDos.trim().toUpperCase());
	    	person.setEmail(this.email.trim());   
	    	person.setGenero(genero);
	    	
			try {
				
		
	        List roles = this.servicioGeneral.obtenerListaObjetos("Rol where id = 'VA'");
	    	
	    	if(roles!=null && roles.size() > 0){
	    		Rol rol = (Rol) roles.get(0);
	    		//person.adicionarRol(rol);
	    		servicioPersona.insertarNuevaPersona(person);
	    		servicioGeneral.guardarObjeto(person);	    		
		    	servicioPersona.agregarRolPersona(person.getId(), rol);
	    	}	   	
    	
	    	Investigador inv = new Investigador();
	    	inv.setId(id);
	    	inv.setInterno("N");
	    	inv.setEvaluador("N");
	    	
	    	List categoria = this.servicioGeneral.obtenerListaObjetos("CategoriaInvestigador where id = 3");
	    	
	    	if(categoria!=null && categoria.size() > 0){
	    		CategoriaInvestigador cat = (CategoriaInvestigador) categoria.get(0);
	    		inv.setCategoriaInvestigador(cat);
	    	}	    	
	    
	    	String mensajeInv = servicioPersona.insertarNuevoInvestigador(inv);
	    	
	       	InvestigadorExterno ext = new InvestigadorExterno();
	    	ext.setId(id);
	    	List institucion = this.servicioGeneral.obtenerListaObjetos("Institucion where id = '1040'");
	    	
	    	if(institucion!=null && institucion.size() > 0){
	    		Institucion ins = (Institucion) institucion.get(0);
	    		ext.setInstitucion(ins);
	    	}
	  
	    	servicioPersona.insertarNuevoInvestigadorExterno(ext);
	    	  
			} catch (Exception e) {
				System.out.println("problema creando investigador externo");
			}
	        InvestigadorExterno ie = servicioPersona.obtenerInvestigadorExterno(id);
             
	         if(ie!=null)
	         {		            
	 	        if(ie.getContrasena()==null)
	 	        {
	 	            ie.setContrasena(new Long(servicioPersona.generarClaveExterno()));
	 	        }
	 	        System.out.println(servicioPersona.login(ie));
	 	        System.out.println(ie.getContrasena().toString());
	 	        servicioGeneral.guardarObjeto(ie);
	            CorreoPlantilla a= new CorreoPlantilla();
	            CorreoPlantilla correoActual=(CorreoPlantilla) servicioGeneral.obtenerObjeto(a,Long.valueOf(88));                
                String correo1=correoActual.getCuerpo().replaceAll("<<fecha>>",Fecha.fechaActual());
                correo1=correo1.replaceAll("<<nombreVisitante>>",person.getNombre1() + " " + person.getNombre2() + " " + person.getApellido1() + " " + person.getApellido2());    
                correo1=correo1.replaceAll("<<usuario>>", servicioPersona.login(ie));                
                correo1=correo1.replaceAll("<<clave>>", ie.getContrasena().toString());    
                
                Correo correo = new Correo();
    			correo.setOrigen(Correo.CORREO_HERMES);
    			String dirCorreo = person.getEmail();
    			correo.adicionarDireccion(dirCorreo);
       			correo.adicionarCopiaOculta(new String(person.getEmail()));
       			correo.setAsunto(correoActual.getAsunto());
    			correo.setCuerpo(correo1);
    			
    			List listaParametroSede = new ArrayList();
    			listaParametroSede = this.servicioGeneral.obtenerObjetos("FROM Parametro WHERE id = 62");				
    			
    			if (listaParametroSede != null && listaParametroSede.size() > 0) {
    				Parametro par = (Parametro) listaParametroSede.get(0);    				
    				correo.adicionarCopiaOculta(new String(par.getValor()));
    			}
    			
    			servicioCorreo.enviarCorreo(correo);
    			this.movArt.setPersonaInv(person);
	         }
			
	     	Dependencia dependencia;
			dependencia = servicioDependencia.obtenerDependencia(personaActual.getId());
			
			if(dependencia!=null && dependencia.getSede()!=null && dependencia.getSede().getId()!=null){
				this.movArt.setDependencia(String.valueOf(dependencia.getSede().getId()));
			}
			
	     	servicioGeneral.guardarObjeto(this.movArt);	


			this.limpiar();
		
			
			reiniciarVariables();
			this.panelRenderError[0] = true;
			this.errores[0] = "Visitante creado satisfactoriamente.";
		}

	}

	public String editarCorreo(Persona personaAux, String id, String tipo, String cuerpo) {
		String correo = cuerpo;
		try {
			String investigador = "";
			investigador = personaAux.getNombre1() + " "
					+ personaAux.getApellido1() + " "
					+ personaAux.getApellido2();
			correo = correo.replaceAll("<<INVESTIGADOR>>", investigador);
			correo = correo.replaceAll("<<IDMOVILIDAD>>", id);
			correo = correo.replaceAll("<<TIPO>>", tipo);

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return correo;
	}

	public CorreoPlantilla cargarPlantilla(int cod_id) {

		CorreoPlantilla correoActualAux = new CorreoPlantilla();
		CorreoPlantilla a = new CorreoPlantilla();

		List lista = servicioGeneral
				.obtenerObjetos("select c from CorreoPlantilla c where c.id='"
						+ cod_id + "'");
		if (lista != null && lista.size() > 0) {
			correoActualAux = (CorreoPlantilla) lista.get(0);
		}

		return correoActualAux;
	}

	// CREADO Y PATENTADO POR ING. CANTOR
	public void ocultarPaneles(int nivel) {
		for (int i = 0; i < 10; i++) {
			if (i < nivel) {
				panelRender[i] = true;
				;
			} else {
				panelRender[i] = false;
				;
			}

		}
	}

	public void validarFechaInicial(ValueChangeEvent event) {

		Date fechaInicial = (Date) event.getNewValue();
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(fechaInicial);
		cal2.add(Calendar.DATE, -30);

		if (cal2.before(cal1)) {
			ocultarPaneles(3);
			this.errores[14] = "La fecha del viaje debe ser superior que la fecha actual en 30 días calendario.";
			this.panelRenderError[14] = true;
		} else {
			this.errores[14] = "";
			this.panelRenderError[14] = false;
		}

		if (this.movArt.getFechafinal() != null) {
			Calendar cal3 = Calendar.getInstance();
			Calendar cal4 = Calendar.getInstance();
			cal3.setTime(fechaInicial);
			cal4.setTime(this.movArt.getFechafinal());
			cal4.add(Calendar.DATE, -30);
			if (this.movArt.getFechafinal().before(fechaInicial)
					|| !cal3.after(cal4)) {

				this.errores[15] = "La fecha de finalización del viaje no es válida. La fecha debe ser superior a la fecha de incio en máximo 30 días.";
				this.panelRenderError[15] = true;

			} else {
				this.errores[15] = "";
				this.panelRenderError[15] = false;

				ocultarPaneles(6);

			}
		}
	}

	public void validarFechaLlegada(ValueChangeEvent event) {
		Date fechaLlegada = (Date) event.getNewValue();

		if (this.movArt.getFechainicial() != null
				&& this.panelRenderError[14] == false) {
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.setTime(this.movArt.getFechainicial());
			cal2.setTime(fechaLlegada);
			cal2.add(Calendar.DATE, -30);
			if (fechaLlegada.before(this.movArt.getFechainicial())
					|| !cal1.after(cal2)) {
				this.errores[15] = "La fecha de finalización del viaje no es válida. La fecha debe ser superior a la fecha de incio en máximo 30 días.";
				this.panelRenderError[15] = true;
				;
			} else {
				this.errores[15] = "";
				this.panelRenderError[15] = false;

				ocultarPaneles(6);

			}
		} else {
			this.errores[15] = "Debe diligenciar la fecha de inicio del viaje válida.";
			this.panelRenderError[15] = true;
			;
		}

	}

	private void cargarPaises() {
		List listaPaises = servicioGeneral.obtenerListaObjetosOrdenadosAsc(
				new Pais(), "nombre");
		paises = new Vector();
		nacionalidad = new Vector();

		for (Iterator it = listaPaises.iterator(); it.hasNext();) {
			Pais p = (Pais) it.next();
			SelectItem s = new SelectItem(p.getId(), p.getNombre());
			paises.add(s);

		}

		for (Iterator it = listaPaises.iterator(); it.hasNext();) {
			Pais p = (Pais) it.next();
			SelectItem s1 = new SelectItem(p.getId(), p.getNombre());
			nacionalidad.add(s1);
		}
	}

	private void cargarTiposDocumento() {
		List listaTipoDocumento = servicioGeneral
				.obtenerListaObjetos("TipoDocumento");
		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			TipoDocumento td = (TipoDocumento) listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
		}
		tipoDocumento = (TipoDocumento) listaTipoDocumento.get(0);
	}

	private void cargarValoresIniciales() {
		errores = new String[60];
		panelRender = new boolean[10];
		panelRenderError = new boolean[60];
		personaActual = (Persona) sesion.getAttribute("persona");
		cargarTiposDocumento();
		cargarPaises();
		cargarContinentes();
		ocultarPaneles(0);
		cargarTiposDocumentos();
		cargarTipoFormacion();

		listaActividades = new ArrayList();
		listaArchivos = new ArrayList();
		listaArchivosObligatoriosSel = new ArrayList();

		areaArte = new Vector();
		areaArte.add(new SelectItem("Animación (Bogotá)", "Animación (Bogotá)"));
		areaArte.add(new SelectItem("Diseño de multimedia (Bogotá)", "Diseño de multimedia (Bogotá)"));
		areaArte.add(new SelectItem("Fotografía (Bogotá)", "Fotografía (Bogotá)"));
		areaArte.add(new SelectItem("Plástica Contemporánea (Bogotá)","Plástica Contemporánea (Bogotá)"));
		areaArte.add(new SelectItem("Performance","Performance"));
		areaArte.add(new SelectItem("Teatro y Artes Vivas (Bogotá)","Teatro y Artes Vivas (Bogotá)"));
		areaArte.add(new SelectItem("Museología y Patrimonio (Bogotá)","Museología y Patrimonio (Bogotá)"));
		areaArte.add(new SelectItem("Sonido (Bogotá)", "Sonido (Bogotá)"));
		areaArte.add(new SelectItem("Música (Bogotá)","Música (Bogotá)"));
		areaArte.add(new SelectItem("Historia/Teoría del arte/Curaduría (Bogotá)", "Historia/Teoría del arte/Curaduría (Bogotá)"));
		areaArte.add(new SelectItem("Sostenibilidad/Urbanismo/Arquitectura (Bogotá)", "Sostenibilidad/Urbanismo/Arquitectura (Bogotá)"));
		areaArte.add(new SelectItem("Gestión cultural y comunicativa (Manizales)", "Gestión cultural y comunicativa (Manizales)"));
		areaArte.add(new SelectItem("Estudios ambientales (Manizales)", "Estudios ambientales (Manizales)"));
		areaArte.add(new SelectItem("Diseño multimedia (Medellín)", "Diseño multimedia (Medellín)"));
		areaArte.add(new SelectItem("Estética (Medellín)", "Estética (Medellín)"));
		areaArte.add(new SelectItem("Plástica contemporánea (Medellín)", "Plástica contemporánea (Medellín)"));
		areaArte.add(new SelectItem("Arquitectura (Medellín)", "Arquitectura (Medellín)"));

		socializacion = new Vector();
		socializacion.add(new SelectItem("Conferencia","Conferencia"));
		socializacion.add(new SelectItem("Exposición","Exposición"));
		socializacion.add(new SelectItem("Publicación","Publicación"));
		socializacion.add(new SelectItem("Concierto","Concierto"));
		socializacion.add(new SelectItem("Otros","Otros"));
		
		tipoInstitucion = new Vector();
		tipoInstitucion.add(new SelectItem("Programa de Residencia",
				"Programa de Residencia"));
		tipoInstitucion.add(new SelectItem("Universidad", "Universidad"));
		tipoInstitucion.add(new SelectItem("Institución Académica",
				"Institución Académica"));
		tipoInstitucion.add(new SelectItem("Fundación Cultural",
				"Fundación Cultural"));

		convenio = new Vector();
		convenio.add(new SelectItem("M", "Masculino"));
		convenio.add(new SelectItem("F", "Femenino"));

		comoEntero = new Vector();
		comoEntero.add(new SelectItem("Página Web", "Página Web"));
		comoEntero.add(new SelectItem("Red de Residencias",
				"Red de Residencias"));
		comoEntero.add(new SelectItem("Institución Académica",
				"Institución Académica"));
		comoEntero.add(new SelectItem("Revista", "Revista"));
		comoEntero.add(new SelectItem("Museo", "Museo"));
		comoEntero.add(new SelectItem("Galería", "Galería"));
		comoEntero.add(new SelectItem("Referencia Personal",
				"Referencia Personal"));

	}

	public List getListaActividades() {
		return listaActividades;
	}

	public String[] getErrores() {
		return errores;
	}

	public void setErrores(String[] errores) {
		this.errores = errores;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public SelectItem[] getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}

	public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
		this.tipoDocumentoItem = tipoDocumentoItem;
	}

	public HtmlDataTable getTablaArchivos() {
		return tablaArchivos;
	}

	public void setTablaArchivos(HtmlDataTable tablaArchivos) {
		this.tablaArchivos = tablaArchivos;
	}

	public List getPaises() {
		return paises;
	}

	public void setPaises(List paises) {
		this.paises = paises;
	}

	public List getContinentes() {
		return continentes;
	}

	public void setContinentes(List continentes) {
		this.continentes = continentes;
	}

	public List getListaArchivos() {
		return listaArchivos;
	}

	public void setListaArchivos(List listaArchivos) {
		this.listaArchivos = listaArchivos;
	}

	public boolean[] getPanelRender() {
		return panelRender;
	}

	public void setPanelRender(boolean[] panelRender) {
		this.panelRender = panelRender;
	}

	public List getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(List nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public boolean[] getPanelRenderError() {
		return panelRenderError;
	}

	public void setPanelRenderError(boolean[] panelRenderError) {
		this.panelRenderError = panelRenderError;
	}

	public List getAreaArte() {
		return areaArte;
	}

	public void setAreaArte(List areaArte) {
		this.areaArte = areaArte;
	}
	
	public List getTipoInstitucion() {
		return tipoInstitucion;
	}

	public void setTipoInstitucion(List tipoInstitucion) {
		this.tipoInstitucion = tipoInstitucion;
	}

	public List getConvenio() {
		return convenio;
	}

	public void setConvenio(List convenio) {
		this.convenio = convenio;
	}

	
	public List getComoEntero() {
		return comoEntero;
	}

	public void setComoEntero(List comoEntero) {
		this.comoEntero = comoEntero;
	}

	public void setListaActividades(List listaActividades) {
		this.listaActividades = listaActividades;
	}

	public HtmlDataTable getTablaActividadesSel() {
		return tablaActividadesSel;
	}

	public void setTablaActividadesSel(HtmlDataTable tablaActividadesSel) {
		this.tablaActividadesSel = tablaActividadesSel;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	public String getFacultadDocente() {
		return facultadDocente;
	}

	public void setFacultadDocente(String facultadDocente) {
		this.facultadDocente = facultadDocente;
	}

	public String getDepartamentoDocente() {
		return departamentoDocente;
	}

	public void setDepartamentoDocente(String departamentoDocente) {
		this.departamentoDocente = departamentoDocente;
	}

	public String getNombreDocente() {
		return nombreDocente;
	}

	public void setNombreDocente(String nombreDocente) {
		this.nombreDocente = nombreDocente;
	}

	public String getDocumentoDocente() {
		return documentoDocente;
	}

	public void setDocumentoDocente(String documentoDocente) {
		this.documentoDocente = documentoDocente;
	}

	public String getDescripcionActividad() {
		return descripcionActividad;
	}

	public void setDescripcionActividad(String descripcionActividad) {
		this.descripcionActividad = descripcionActividad;
	}

	public Date getFechaActividad() {
		return fechaActividad;
	}

	public void setFechaActividad(Date fechaActividad) {
		this.fechaActividad = fechaActividad;
	}

	public String getDuracionActividad() {
		return duracionActividad;
	}

	public void setDuracionActividad(String duracionActividad) {
		this.duracionActividad = duracionActividad;
	}

	public SelectItem[] getTipoDocumentoSelItem() {
		return tipoDocumentoSelItem;
	}

	public void setTipoDocumentoSelItem(SelectItem[] tipoDocumentoSelItem) {
		this.tipoDocumentoSelItem = tipoDocumentoSelItem;
	}

	public String getTipoDocumentoSel() {
		return tipoDocumentoSel;
	}

	public void setTipoDocumentoSel(String tipoDocumentoSel) {
		this.tipoDocumentoSel = tipoDocumentoSel;
	}

	public UploadedFile getArchivoObligatorio() {
		return archivoObligatorio;
	}

	public void setArchivoObligatorio(UploadedFile archivoObligatorio) {
		this.archivoObligatorio = archivoObligatorio;
	}

	public List getListaArchivosObligatoriosSel() {
		return listaArchivosObligatoriosSel;
	}

	public void setListaArchivosObligatoriosSel(
			List listaArchivosObligatoriosSel) {
		this.listaArchivosObligatoriosSel = listaArchivosObligatoriosSel;
	}

	public HtmlDataTable getTablaArchivosObligatoriosSel() {
		return tablaArchivosObligatoriosSel;
	}

	public void setTablaArchivosObligatoriosSel(
			HtmlDataTable tablaArchivosObligatoriosSel) {
		this.tablaArchivosObligatoriosSel = tablaArchivosObligatoriosSel;
	}

	public MovilidadVisitantesArtes getMovArt() {
		return movArt;
	}

	public void setMovArt(MovilidadVisitantesArtes movArt) {
		this.movArt = movArt;
	}

	public List getTipoFormacion() {
		return tipoFormacion;
	}

	public void setTipoFormacion(List tipoFormacion) {
		this.tipoFormacion = tipoFormacion;
	}

	public boolean isBanderaConvenio() {
		return banderaConvenio;
	}

	public void setBanderaConvenio(boolean banderaConvenio) {
		this.banderaConvenio = banderaConvenio;
	}

	public List getSocializacion() {
		return socializacion;
	}

	public void setSocializacion(List socializacion) {
		this.socializacion = socializacion;
	}

	public String getNombreUno() {
		return nombreUno;
	}

	public void setNombreUno(String nombreUno) {
		this.nombreUno = nombreUno;
	}

	public String getNombreDos() {
		return nombreDos;
	}

	public void setNombreDos(String nombreDos) {
		this.nombreDos = nombreDos;
	}

	public String getApellidoUno() {
		return apellidoUno;
	}

	public void setApellidoUno(String apellidoUno) {
		this.apellidoUno = apellidoUno;
	}

	public String getApellidoDos() {
		return apellidoDos;
	}

	public void setApellidoDos(String apellidoDos) {
		this.apellidoDos = apellidoDos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}	
	
	

}
