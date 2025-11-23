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
import co.edu.unal.hermes.modelo.ArchivoMovilidadDE;
import co.edu.unal.hermes.modelo.Continente;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.MovilidadArchivo;
import co.edu.unal.hermes.modelo.MovilidadDocentesArtes;
import co.edu.unal.hermes.modelo.Pais;
import co.edu.unal.hermes.modelo.Parametro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.TipoArchivoMovilidad;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoMovilidad;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorEditarSedeMovilidadDocArt extends ManejadorBase {

	private MovilidadDocentesArtes movArt;

	private String errores[];
	private boolean panelRender[];
	private boolean panelRenderError[];
	
	private boolean banderaAval = false;
	private String avalDescripcion;
	private boolean banderaDescipcion = false;;
	private String errorDescripcion;

	private String documento;
	private TipoDocumento tipoDocumento;
	private SelectItem[] tipoDocumentoItem;
	private SelectItem[] tipoDocumentoSelItem;
	private SelectItem[] movilidadItem;

	private List paises;
	private List nacionalidad;
	private List continentes;
	private List listaArchivos;
	private List areaArte;
	private List seleccionReceptora;
	private List tipoInstitucion;
	private List movilidadAnterior;
	private List comoEntero;
	private List listaActividades;
	private List listaArchivosObligatoriosSel;

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
	private Long codigoMovilidad = Long.parseLong("-1");

	private HtmlDataTable tablaArchivos;
	private HtmlDataTable tablaActividadesSel;
	private HtmlDataTable tablaArchivosObligatoriosSel;

	public ManejadorEditarSedeMovilidadDocArt() {

		movArt = new MovilidadDocentesArtes();

		cargarValoresIniciales();

		listarMovilidades(); 
		
		
	}

	public void listarMovilidades() {
		Dependencia dependencia;
		dependencia = servicioDependencia.obtenerDependencia(personaActual.getId());
		
		List listaMovilidades = servicioGeneral
				.obtenerObjetos("Select m from MovilidadDocentesArtes m, InvestigadorInterno i where m.personaInv.id.documento = i.id.documento"
							+ " and i.dependencia.sede.id = "+ dependencia.getSede().getId() +" and m.aceptacionFacultad = 'SI' and m.aprobacionSede is null ORDER BY m.id");

		if (listaMovilidades != null && listaMovilidades.size() > 0) {
			movilidadItem = new SelectItem[listaMovilidades.size()];
			for (int i = 0; i < listaMovilidades.size(); i++) {
				MovilidadDocentesArtes movilidad = (MovilidadDocentesArtes) listaMovilidades
						.get(i);
				movilidadItem[i] = new SelectItem(movilidad.getId(), "Id="
						+ movilidad.getId() + " - Fecha Registro("
						+ movilidad.getFechasolicitud() + ")");
			}
			codigoMovilidad = ((MovilidadDocentesArtes) listaMovilidades.get(0))
					.getId();
		} else {
			movilidadItem = new SelectItem[0];

		}
	}
	
	private void infoModalidad(Long idModalidad_) {
		List listaMovilidadConsulta;
		listaMovilidadConsulta = new ArrayList();

		listaMovilidadConsulta = servicioGeneral
				.obtenerListaObjetos("MovilidadDocentesArtes where id = '"
						+ idModalidad_ + "'");
		if (listaMovilidadConsulta != null && listaMovilidadConsulta.size() > 0) {
			movArt = (MovilidadDocentesArtes) listaMovilidadConsulta.get(0);
		}
	}

	private void cargarTiposDocumentos() {
		List listaArchivosObligatorios = new ArrayList();
		listaArchivosObligatorios = servicioGeneral
				.obtenerListaArchivosMovilidad("C1");
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

	private void reiniciarVariables() {
		movArt = new MovilidadDocentesArtes();
		errores = new String[60];
		panelRender = new boolean[10];
		panelRenderError = new boolean[60];
		Pais pais = new Pais();
		pais.setId("CO");
		Pais nacionalidad = new Pais();
		nacionalidad.setId("CO");
		movArt.setPais(pais);
		movArt.setNacionalidad(nacionalidad);
		Continente continente = new Continente();
		continente.setId(new Long("1"));
		movArt.setContinente(continente);
	}

	public void limpiar() {
		movArt = new MovilidadDocentesArtes();
		documento = new String("");
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
				.obtenerObjetos("FROM Parametro WHERE nombre = 'AFRICA_DOCENTE'");
		List listaParametroDos = this.servicioGeneral
				.obtenerObjetos("FROM Parametro WHERE nombre = 'AMERICA_DOCENTE'");
		List listaParametroTres = this.servicioGeneral
				.obtenerObjetos("FROM Parametro WHERE nombre = 'ASIA_DOCENTE'");
		List listaParametroCuatro = this.servicioGeneral
				.obtenerObjetos("FROM Parametro WHERE nombre = 'EUROPA_DOCENTE'");
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
						.obtenerListaObjetos("TipoMovilidad where id ='C1'");
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
		ArchivoMovilidadDE amv = (ArchivoMovilidadDE) tablaArchivosObligatoriosSel
				.getRowData();
		listaArchivosObligatoriosSel.remove(tablaArchivosObligatoriosSel
				.getRowIndex());
	}

	public void avalAprobar(){
		this.errorDescripcion = "";
    	this.banderaDescipcion = false;
    	
		if(this.avalDescripcion == null || this.avalDescripcion.length() <= 0){
			this.errorDescripcion = "Debe registrar una descripción para la aprobación del aval.";
			this.banderaDescipcion = true;
		}else{
			List objeto = this.servicioGeneral
			.obtenerListaObjetos("MovilidadDocentesArtes m where m.id = '"+ this.movArt.getId()+ "'");
			if (objeto != null && objeto.size() > 0) {
				MovilidadDocentesArtes movilidad = (MovilidadDocentesArtes) objeto
				.get(0);
				movilidad.setAprobacionSede("SI");
				movilidad.setDescripcionSede(this.avalDescripcion);
				
				try {
					String sql = "update HER_MOVILIDAD_DOCENTES_ART set MOV_AVAL_SEDE = 'SI', MOV_DESCRIPCION_SEDE = '"+this.avalDescripcion+"'  where MOV_ID = '"+ movilidad.getId()+"'";
					servicioGeneral.eliminar(sql);
				} catch (SQLException e) {
					System.out.println(e.toString());
				} 
				
			//	servicioGeneral.guardarObjeto(movilidad);
			
				
				CorreoPlantilla correoActual = new CorreoPlantilla();
				String cuerpoCorreo = "";
				correoActual = cargarPlantilla(82);
				cuerpoCorreo = editarCorreo(movilidad.getPersonaInv(), String.valueOf(this.movArt.getId()), this.movArt.getTipoMovilidad().getNombre(), correoActual.getCuerpo());
				Correo correo = new Correo();
				correo.setOrigen(Correo.CORREO_HERMES);
				String dirCorreo = movilidad.getPersonaInv().getEmail();
				correo.adicionarDireccion(dirCorreo);			
				correo.adicionarCopiaOculta(new String(movilidad.getPersonaInv().getEmail()));				
				correo.setAsunto(correoActual.getAsunto());
				correo.setCuerpo(cuerpoCorreo);
				servicioCorreo.enviarCorreo(correo);
				
				this.limpiar();
				listarMovilidades();
				this.errores[0] = "Aval sede guardado satisfactoriamente.";
				this.panelRenderError[0] = true;
				
			}
		}
	}
	
    public void avalNegar(){
    	this.errorDescripcion = "";
    	this.banderaDescipcion = false;
    	
    	if(this.avalDescripcion == null || this.avalDescripcion.length() <= 0){
			this.errorDescripcion = "Debe registrar una descripción para la no aprobación del aval.";
			this.banderaDescipcion = true;
		}else{
			List objeto = this.servicioGeneral
			.obtenerListaObjetos("MovilidadDocentesArtes m where m.id = '"+ this.movArt.getId()+ "'");
			if (objeto != null && objeto.size() > 0) {
				MovilidadDocentesArtes movilidad = (MovilidadDocentesArtes) objeto
				.get(0);
				movilidad.setAprobacionSede("NO");
				movilidad.setDescripcionSede(this.avalDescripcion);
				try {
					String sql = "update HER_MOVILIDAD_DOCENTES_ART set MOV_AVAL_SEDE = 'NO', MOV_DESCRIPCION_SEDE = '"+this.avalDescripcion+"'  where MOV_ID = '"+ movilidad.getId()+"'";
					servicioGeneral.eliminar(sql);
				} catch (SQLException e) {
					System.out.println(e.toString());
				} 
				//servicioGeneral.guardarObjeto(movilidad);
				
				
				CorreoPlantilla correoActual = new CorreoPlantilla();
				String cuerpoCorreo = "";
				correoActual = cargarPlantilla(84);
				cuerpoCorreo = editarCorreo(this.movArt.getPersonaInv(), String.valueOf(this.movArt.getId()), this.movArt.getTipoMovilidad().getNombre(), correoActual.getCuerpo());
				cuerpoCorreo = cuerpoCorreo.replaceAll("<<COMENTARIO>>", this.avalDescripcion);
				Correo correo = new Correo();
				correo.setOrigen(Correo.CORREO_HERMES);
				String dirCorreo = this.movArt.getPersonaInv().getEmail();
				correo.adicionarDireccion(dirCorreo);			
				correo.adicionarCopiaOculta(new String(this.movArt.getPersonaInv().getEmail()));				
				correo.setAsunto(correoActual.getAsunto());
				correo.setCuerpo(cuerpoCorreo);
				servicioCorreo.enviarCorreo(correo);
				
				this.limpiar();
				listarMovilidades();
				this.errores[0] = "Aval sede guardado satisfactoriamente.";
				this.panelRenderError[0] = true;
				
			}
			
		}
	}

	
	public void buscarPersona() throws SQLException {
		reiniciarVariables();

		List objeto = this.servicioGeneral
				.obtenerListaObjetos("MovilidadDocentesArtes m where m.id = '"
						+ codigoMovilidad + "'");

		if (objeto != null && objeto.size() > 0) {
			MovilidadDocentesArtes movilidad = (MovilidadDocentesArtes) objeto
					.get(0);
			this.movArt = movilidad;
			
			List lista = this.servicioGeneral
			.obtenerListaObjetos("ArchivoMovilidad a where a.movilidad = '"
					+ this.movArt.getId() + "'");
			this.setListaArchivosObligatoriosSel(lista);

			List lista1 = this.servicioGeneral
					.obtenerListaObjetos("ActividadMovilidad a where a.movilidad = '"
							+ this.movArt.getId() + "'");
			this.setListaActividades(lista1);
		
			if (movArt.getPersonaInv() != null) {

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

							if (dependencia != null
									&& dependencia.getFacultad() != null) {
								this.sede = dependencia.getSede().getNombre();
								this.facultadDocente = dependencia
										.getFacultad().getNombre();
								this.departamentoDocente = dependencia
										.getNombre();

								banderaAval = true;			
								ocultarPaneles(9);

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
			
					errores[0] = "El documento ingresado no corresponde a un investigador";
					panelRenderError[0] = true;
					ocultarPaneles(0);
				}
			} else {
	
				errores[0] = "El documento ingresado no existe";
				panelRenderError[0] = true;
				ocultarPaneles(0);
			}

		}

	}

	// }

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

		ArchivoMovilidad ain = (ArchivoMovilidad) tablaArchivosObligatoriosSel
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

		if (this.movArt.getNombreArtistico() != null
				&& this.movArt.getNombreArtistico().length() > 50) {
			this.errores[1] = "Nombre artistico NO valido";
			this.panelRenderError[1] = true;
			bandera = false;
		}

		if (this.movArt.getPasaporte() == null
				|| this.movArt.getPasaporte().length() <= 0
				|| this.movArt.getPasaporte().length() > 20) {
			this.errores[3] = "Pasaporte es NO valido";
			this.panelRenderError[3] = true;
			bandera = false;
		}

		if (this.movArt.getPaginaWeb() != null
				&& this.movArt.getTelefonoFijo().length() > 200) {
			this.errores[4] = "Página web NO valida";
			this.panelRenderError[4] = true;
			bandera = false;
		}

		if (this.movArt.getTelefonoFijo() == null
				|| this.movArt.getTelefonoFijo().length() <= 0
				|| this.movArt.getTelefonoFijo().length() > 20) {
			this.errores[5] = "Telefono fijo NO valido";
			this.panelRenderError[5] = true;
			bandera = false;
		}

		if (this.movArt.getTelefonoMovil() == null
				|| this.movArt.getTelefonoMovil().length() <= 0
				|| this.movArt.getTelefonoMovil().length() > 20) {
			this.errores[6] = "Telefono movil NO valido";
			this.panelRenderError[6] = true;
			bandera = false;
		}

		if (this.movArt.getDireccion() == null
				|| this.movArt.getDireccion().length() <= 0
				|| this.movArt.getDireccion().length() > 200) {
			this.errores[7] = "Dirección NO valida";
			this.panelRenderError[7] = true;
			bandera = false;
		}

		if (this.movArt.getNombreProyecto() == null
				|| this.movArt.getNombreProyecto().length() <= 0
				|| this.movArt.getNombreProyecto().length() > 200) {
			this.errores[8] = "Nombre del Proyecto NO valido";
			this.panelRenderError[8] = true;
			bandera = false;
		}

		if (this.movArt.getStatementArtista() == null
				|| this.movArt.getStatementArtista().length() <= 0
				|| this.movArt.getStatementArtista().length() > 500) {
			this.errores[9] = "Statement del Artista NO valido";
			this.panelRenderError[9] = true;
			bandera = false;
		}

		if (this.movArt.getDescripcionProyecto() == null
				|| this.movArt.getDescripcionProyecto().length() <= 0
				|| this.movArt.getDescripcionProyecto().length() > 2000) {
			this.errores[10] = "Descripción del Proyecto NO valida";
			this.panelRenderError[10] = true;
			bandera = false;
		}

		if (this.movArt.getObjetivoGeneral() == null
				|| this.movArt.getObjetivoGeneral().length() <= 0
				|| this.movArt.getObjetivoGeneral().length() > 500) {
			this.errores[11] = "Objetivo General NO valido";
			this.panelRenderError[11] = true;
			bandera = false;
		}

		if (this.movArt.getObjetivoEspecifico() == null
				|| this.movArt.getObjetivoEspecifico().length() <= 0
				|| this.movArt.getObjetivoEspecifico().length() > 2000) {
			this.errores[12] = "Objetivos Específicos NO validos";
			this.panelRenderError[12] = true;
			bandera = false;
		}

		if (this.movArt.getProyectoRelacionLocal() == null
				|| this.movArt.getProyectoRelacionLocal().length() <= 0
				|| this.movArt.getProyectoRelacionLocal().length() > 500) {
			this.errores[13] = "Valor NO valido";
			this.panelRenderError[13] = true;
			bandera = false;
		}

		if (this.movArt.getFechainicial() == null) {
			this.errores[14] = "Fecha inicial NO valida";
			this.panelRenderError[14] = true;
			bandera = false;
		}

		if (this.movArt.getFechafinal() == null) {
			this.errores[15] = "Fecha Final NO valida";
			this.panelRenderError[15] = true;
			bandera = false;
		}

		if (this.movArt.getCiudad() == null
				|| this.movArt.getCiudad().length() <= 0
				|| this.movArt.getCiudad().length() > 20) {
			this.errores[18] = "Ciudad NO valida";
			this.panelRenderError[18] = true;
			bandera = false;
		}

		if (this.movArt.getInstitucion() == null
				|| this.movArt.getInstitucion().length() <= 0
				|| this.movArt.getInstitucion().length() > 300) {
			this.errores[21] = "Institución NO valida";
			this.panelRenderError[21] = true;
			bandera = false;
		}

		if (this.movArt.getDescripcionPrograma() == null
				|| this.movArt.getDescripcionPrograma().length() <= 0
				|| this.movArt.getDescripcionPrograma().length() > 500) {
			this.errores[23] = "Valor NO valido";
			this.panelRenderError[23] = true;
			bandera = false;
		}

		if (this.listaActividades == null || this.listaActividades.size() <= 0) {
			this.errores[28] = "No se encuentran actividades registradas";
			this.panelRenderError[28] = true;
			bandera = false;
		}

		if (this.movArt.getPlanSocializacionResidencia() == null
				|| this.movArt.getPlanSocializacionResidencia().length() <= 0
				|| this.movArt.getPlanSocializacionResidencia().length() > 1000) {
			this.errores[43] = "Plan de Socialización NO validos";
			this.panelRenderError[43] = true;
			bandera = false;
		}
		
		if (this.movArt.getPlanSocializacionUN() == null
				|| this.movArt.getPlanSocializacionUN().length() <= 0
				|| this.movArt.getPlanSocializacionUN().length() > 1000) {
			this.errores[43] = "Plan de Socialización NO validos";
			this.panelRenderError[49] = true;
			bandera = false;
		}

		if (this.movArt.getAporteRedArtistas() == null
				|| this.movArt.getAporteRedArtistas().length() <= 0
				|| this.movArt.getAporteRedArtistas().length() > 500) {
			this.errores[45] = "Valor NO valido";
			this.panelRenderError[45] = true;
			bandera = false;
		}

		if (this.movArt.getContribucion() == null
				|| this.movArt.getContribucion().length() <= 0
				|| this.movArt.getContribucion().length() > 500) {
			this.errores[46] = "Valor NO valido";
			this.panelRenderError[46] = true;
			bandera = false;
		}

		if (this.movArt.getEspecialistasArea() == null
				|| this.movArt.getEspecialistasArea().length() <= 0
				|| this.movArt.getEspecialistasArea().length() > 500) {
			this.errores[47] = "Valor NO valido";
			this.panelRenderError[47] = true;
			bandera = false;
		}

		if (this.listaArchivosObligatoriosSel == null
				|| this.listaArchivosObligatoriosSel.size() <= 0) {
			this.errores[48] = "No se encuentran documentos registrados";
			this.panelRenderError[48] = true;
			bandera = false;
		}

		return bandera;

	}

	public void guardar() {

		if (validacion() && validarPresupuesto()) {
			this.movArt.setNombreProyecto(this.movArt.getNombreProyecto()
					.toUpperCase());
			this.movArt.setCiudad(this.movArt.getCiudad().toUpperCase());
			this.movArt.setInstitucion(this.movArt.getInstitucion()
					.toUpperCase());
			Calendar actual = Calendar.getInstance();
			Date date = actual.getTime();
			this.movArt.setFechasolicitud(date);
			List listaTipoMovilidad = servicioGeneral
					.obtenerListaObjetos("TipoMovilidad where id ='C1'");
			TipoMovilidad tipoMovilidad = (TipoMovilidad) listaTipoMovilidad
					.get(0);
			this.movArt.setTipoMovilidad(tipoMovilidad);
			servicioGeneral.guardarObjeto(this.movArt);

			for (int i = 0; i < listaArchivosObligatoriosSel.size(); i++) {
				ArchivoMovilidad archivo = new ArchivoMovilidad();
				archivo = (ArchivoMovilidad) listaArchivosObligatoriosSel
						.get(i);
				archivo.setMovilidad(String.valueOf(this.movArt.getId()));
				servicioGeneral.guardarObjeto(archivo);
			}

			for (int i = 0; i < listaActividades.size(); i++) {
				ActividadMovilidad actividad = new ActividadMovilidad();
				actividad = (ActividadMovilidad) listaActividades.get(i);
				actividad.setMovilidad(String.valueOf(this.movArt.getId()));
				servicioGeneral.guardarObjeto(actividad);
			}
			CorreoPlantilla correoActual = new CorreoPlantilla();
			String cuerpoCorreo = "";
			correoActual = cargarPlantilla(65);
			cuerpoCorreo = editarCorreo(this.movArt.getPersonaInv(), String
					.valueOf(this.movArt.getId()), this.movArt
					.getTipoMovilidad().getNombre(), correoActual.getCuerpo());
			Correo correo = new Correo();
			correo.setOrigen(Correo.CORREO_HERMES);
			String dirCorreo = this.movArt.getPersonaInv().getEmail();
			correo.adicionarDireccion(dirCorreo);
			correo.adicionarCopiaOculta(new String(this.movArt.getPersonaInv()
					.getEmail()));
			correo.setAsunto(correoActual.getAsunto());
			correo.setCuerpo(cuerpoCorreo);
			servicioCorreo.enviarCorreo(correo);

			this.limpiar();
			this.errores[0] = "Movilidad creada satisfactoriamente.";
			this.panelRenderError[0] = true;
		}

	}

	public String editarCorreo(Persona personaAux, String id, String tipo,
			String cuerpo) {
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
		
		this.banderaAval = false;
	    avalDescripcion = "";
	    this.banderaDescipcion = false;
	    this.errorDescripcion = "";

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

		seleccionReceptora = new Vector();
		seleccionReceptora.add(new SelectItem("Convocatoria Abierta",
				"Convocatoria Abierta"));
		seleccionReceptora.add(new SelectItem("Convocatoria con Cortes",
				"Convocatoria con Cortes"));
		seleccionReceptora.add(new SelectItem(
				"Presentación Directa del Proyecto",
				"Presentación Directa del Proyecto"));
		seleccionReceptora.add(new SelectItem("Contacto Académico",
				"Contacto Académico"));

		tipoInstitucion = new Vector();
		tipoInstitucion.add(new SelectItem("Programa de Residencia",
				"Programa de Residencia"));
		tipoInstitucion.add(new SelectItem("Universidad", "Universidad"));
		tipoInstitucion.add(new SelectItem("Institución Académica",
				"Institución Académica"));
		tipoInstitucion.add(new SelectItem("Fundación Cultural",
				"Fundación Cultural"));

		movilidadAnterior = new Vector();
		movilidadAnterior.add(new SelectItem("NO", "NO"));
		movilidadAnterior.add(new SelectItem("SI", "SI"));

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

	public MovilidadDocentesArtes getMovArt() {
		return movArt;
	}

	public void setMovArt(MovilidadDocentesArtes movArt) {
		this.movArt = movArt;
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

	public List getSeleccionReceptora() {
		return seleccionReceptora;
	}

	public void setSeleccionReceptora(List seleccionReceptora) {
		this.seleccionReceptora = seleccionReceptora;
	}

	public List getTipoInstitucion() {
		return tipoInstitucion;
	}

	public void setTipoInstitucion(List tipoInstitucion) {
		this.tipoInstitucion = tipoInstitucion;
	}

	public List getMovilidadAnterior() {
		return movilidadAnterior;
	}

	public void setMovilidadAnterior(List movilidadAnterior) {
		this.movilidadAnterior = movilidadAnterior;
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

	public SelectItem[] getMovilidadItem() {
		return movilidadItem;
	}

	public void setMovilidadItem(SelectItem[] movilidadItem) {
		this.movilidadItem = movilidadItem;
	}

	public Long getCodigoMovilidad() {
		return codigoMovilidad;
	}

	public void setCodigoMovilidad(Long codigoMovilidad) {
		this.codigoMovilidad = codigoMovilidad;
	}

	public boolean isBanderaAval() {
		return banderaAval;
	}

	public void setBanderaAval(boolean banderaAval) {
		this.banderaAval = banderaAval;
	}

	public String getAvalDescripcion() {
		return avalDescripcion;
	}

	public void setAvalDescripcion(String avalDescripcion) {
		this.avalDescripcion = avalDescripcion;
	}

	public boolean isBanderaDescipcion() {
		return banderaDescipcion;
	}

	public void setBanderaDescipcion(boolean banderaDescipcion) {
		this.banderaDescipcion = banderaDescipcion;
	}

	public String getErrorDescripcion() {
		return errorDescripcion;
	}

	public void setErrorDescripcion(String errorDescripcion) {
		this.errorDescripcion = errorDescripcion;
	}

	
}
