package co.edu.unal.hermes.vista.articulo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.myfaces.custom.fileupload.UploadedFile;

import co.edu.unal.hermes.modelo.ActividadPosdoctorado;
import co.edu.unal.hermes.modelo.ArchivoConvocatoria;
import co.edu.unal.hermes.modelo.CandidatoPosdoctorado;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaArticulo;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.Parametro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoInvestigador;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.utils.Navegacion;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorConsultarEvaluarSolicitudArticulo extends ManejadorBase {

	private ConvocatoriaArticulo conArt;
	private String mensajeTransaccion = "";
	private String errorReq1 = "";
	private String errorReq2 = "";
	private String errorReq3 = "";
	private String errorReq4 = "";
	private String errores[];
	private boolean panelRender[];
	private boolean panelRenderError[];
	private String codigo;
	private String documento;
	private String tipoDocumento;
	private int apoyos;
	// private String programa;
	private SelectItem[] tipoDocumentoItem;
	private SelectItem[] tipoDocumentoSelItem;

	private List listaArchivos;
	private List listaCaracter;
	private List listaTipo;

	private List listaArchivosObligatoriosSel;
	private List listaArchivosObligatorios;

	private String valueGuardar;
	private SelectItem[] articuloItem;

	private int estado = 0;

	private List listaGrupos;
	private InvestigadorProyecto investigadorProyectoNuevo;

	// VARIABLES TEMPORALES
	private String sede;
	private String facultadDocente;
	private String departamentoDocente;
	private String nombreDocente;
	private String documentoDocente;
	private String descripcionActividad;
	private String nombreActividad;
	private Date fechaActividad;
	private String duracionActividad;
	private String tipoDocumentoSel;
	private UploadedFile archivoObligatorio;
	private String paisProcedencia;
	private String paisEstudio;

	CandidatoPosdoctorado candidato;
	ActividadPosdoctorado actividad;

	private Convocatoria convocatoria;

	CorreoPlantilla correoActual = new CorreoPlantilla();
	String cuerpoCorreo = "";

	CorreoPlantilla correoActual2 = new CorreoPlantilla();
	String cuerpoCorreo2 = "";

	private String documentoLiderGrupo;
	private String tipoDocumentoLiderGrupo;

	private HtmlDataTable tablaArchivos;
	private HtmlDataTable tablaActividadesSel;
	private HtmlDataTable tablaArchivosObligatoriosSel;

	private HtmlDataTable tablaGruposSel;
	private HtmlDataTable tablaCandidatosSel;
	private HtmlDataTable tablaGrupos;
	private SelectItem[] tipoDocumentoGrupoItem;

	public HtmlDataTable getTablaGrupos() {
		return tablaGrupos;
	}

	public void setTablaGrupos(HtmlDataTable tablaGrupos) {
		this.tablaGrupos = tablaGrupos;
	}

	private boolean banderaModalidad = true;
	private boolean banderaAlianza = false;
	private boolean banderaCandidatos = false;
	private boolean banderaMensajeTransaccion;
	private boolean banderaReq1;
	private boolean banderaReq2;
	private boolean banderaReq3;
	private boolean banderaReq4;
	private boolean banderaMontoAprobado;
	private boolean banderaComentarioEv;

	public ManejadorConsultarEvaluarSolicitudArticulo() {

		try {

			personaActual = (Persona) sesion.getAttribute("persona");
			reiniciarVariables();

			documento = this.getPersonaActual().getId().getDocumento();
			tipoDocumento = this.getPersonaActual().getId().getTipoDocumento();

			investigadorProyectoNuevo = new InvestigadorProyecto();
			TipoInvestigador tc = (TipoInvestigador) servicioGeneral
					.obtenerObjeto(new TipoInvestigador(),
							TipoInvestigador.coinvestigador);
			investigadorProyectoNuevo.setInvestigador(new Investigador());
			TipoDocumento tDocumento = new TipoDocumento();
			tDocumento = (TipoDocumento) servicioGeneral.obtenerObjeto(
					new TipoDocumento(), TipoDocumento.CEDULA);

			investigadorProyectoNuevo.getInvestigador().setId(new IdPersona());

			investigadorProyectoNuevo.setTipo(tc);

			cargarValoresIniciales();
			listarArticulos();
			// panelRender[3]= false;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public int apoyosAnteriores() {
		int apoyos = 0;

		List listaApoyosAnteriores = servicioGeneral
				.obtenerObjetos("select ca from ConvocatoriaArticulo ca where ca.personaInv.id.documento = '"
						+ documentoDocente
						+ "' and to_char(ca.fechaRegistro,'YYYY') = to_char(sysdate,'YYYY') and ca.estado = 'AP'");

		apoyos = listaApoyosAnteriores.size();

		return apoyos;
	}

	public void listarArticulos() {

		Dependencia dependencia;
		dependencia = servicioDependencia.obtenerDependencia(personaActual.getId());

		List listaSolcitudes = servicioGeneral
				.obtenerObjetos("Select e from ConvocatoriaArticulo e, InvestigadorInterno i  where e.personaInv.id.documento = i.id.documento"
						+ " and i.dependencia.sede.id = '"
						+ dependencia.getSede().getId()
						+ "' and e.estado = 'P' ORDER BY e.id");

		if (listaSolcitudes != null && listaSolcitudes.size() > 0) {

			articuloItem = new SelectItem[listaSolcitudes.size()];
			for (int i = 0; i < listaSolcitudes.size(); i++) {
				ConvocatoriaArticulo articulo = (ConvocatoriaArticulo) listaSolcitudes
						.get(i);
				articuloItem[i] = new SelectItem(String.valueOf(articulo
						.getId()), "Id=" + articulo.getId()
						+ " - Fecha Registro(" + articulo.getFechaRegistro()
						+ ")");
			}
			codigo = String.valueOf(((ConvocatoriaArticulo) listaSolcitudes
					.get(0)).getId());
			ocultarPaneles(0);

		} else {
			articuloItem = new SelectItem[0];

		}

	}

	private void cargarTipoArchivos() {
		listaArchivosObligatorios = new ArrayList();
		listaArchivosObligatorios = servicioGeneral
				.obtenerListaObjetos("DominioDetalle where identificador.id = '8' ORDER BY identificador.tipo");

		if (listaArchivosObligatorios != null
				&& listaArchivosObligatorios.size() > 0) {
			tipoDocumentoSelItem = new SelectItem[listaArchivosObligatorios
					.size()];
			for (int i = 0; i < listaArchivosObligatorios.size(); i++) {
				DominioDetalle tae = (DominioDetalle) listaArchivosObligatorios
						.get(i);
				tipoDocumentoSelItem[i] = new SelectItem(tae.getIdentificador()
						.getTipo(), tae.getIdentificador().getTipo() + "-"
						+ tae.getDescripcion());
			}
		}
	}

	private void cargarTiposDocumentoGrupo() {
		List listaTipoDocumentoGrupo = new ArrayList();
		listaTipoDocumentoGrupo = servicioGeneral
				.obtenerListaObjetos("TipoDocumento");
		tipoDocumentoGrupoItem = new SelectItem[listaTipoDocumentoGrupo.size()];
		for (int i = 0; i < listaTipoDocumentoGrupo.size(); i++) {
			TipoDocumento td = (TipoDocumento) listaTipoDocumentoGrupo.get(i);
			tipoDocumentoGrupoItem[i] = new SelectItem(td.getId(), td
					.getNombre());
		}
	}

	private void reiniciarVariables() {
		conArt = new ConvocatoriaArticulo();
		errores = new String[60];
		panelRender = new boolean[10];
		panelRenderError = new boolean[60];
	}

	public void limpiar() {
		conArt = new ConvocatoriaArticulo();
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

	public void guardarArchivoObligatorio() {
		try {
			if (archivoObligatorio.getBytes() != null) {
				List listaTipoArchivo = new ArrayList();

				int i = archivoObligatorio.getName().lastIndexOf("\\");

				ArchivoConvocatoria archivo = new ArchivoConvocatoria();
				archivo.setBytes(archivoObligatorio.getBytes());
				archivo
						.setNombre(archivoObligatorio.getName()
								.substring(i + 1));
				archivo.setFecha(new Date());
				archivo.setTipoArchivo(tipoDocumentoSel);
				listaArchivosObligatoriosSel.add(archivo);

			}

		} catch (Exception x) {
			System.out.println(x.toString());

		}
	}

	public void eliminarArchivoObligatorio() {
		ArchivoConvocatoria amv = (ArchivoConvocatoria) tablaArchivosObligatoriosSel
				.getRowData();
		listaArchivosObligatoriosSel.remove(tablaArchivosObligatoriosSel
				.getRowIndex());
	}

	public void buscarPersona() throws SQLException {
		this.panelRenderError[0] = false;
		List objeto = null;
		try {
			objeto = this.servicioGeneral
					.obtenerListaObjetos("ConvocatoriaArticulo e "
							+ " where e.id = '" + codigo + "'");
		} catch (Exception e) {
			
		}

		if (objeto != null && objeto.size() > 0) {

			ConvocatoriaArticulo convo = (ConvocatoriaArticulo) objeto.get(0);
			this.conArt = convo;

			List lista = this.servicioGeneral
					.obtenerListaObjetos("ArchivoConvocatoria a where a.convocatoria = '"
							+ this.conArt.getId()
							+ "' and (a.tipoArchivo = 'CACA' or a.tipoArchivo = 'CAAR' or a.tipoArchivo = 'CACR')");
			this.setListaArchivosObligatoriosSel(lista);

			if (conArt.getPersonaInv() != null) {
				if (conArt.getPersonaInv() instanceof Investigador) {
					if (conArt.getPersonaInv() instanceof InvestigadorInterno) {

						InvestigadorInterno investigadorInterno = (InvestigadorInterno) servicioPersona
								.obtenerInvestigadorInternoCompleto(conArt
										.getPersonaInv().getId());
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
												Investigador.MEDIOTIEMPO) 
												|| investigadorInterno.getTipoDedicacion().getId().equals(Investigador.CATEDRA_0_4))) {
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

								ocultarPaneles(2);
								estado = 1;

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
					conArt.setPersonaInv(new Persona());
					IdPersona idP = new IdPersona();
					idP.setDocumento(documento);
					idP.setTipoDocumento(tipoDocumento);
					conArt.getPersonaInv().setId(idP);
					errores[0] = "El documento ingresado no corresponde a un investigador";
					panelRenderError[0] = true;
					ocultarPaneles(0);
				}
			} else {
				conArt.setPersonaInv(new Persona());
				IdPersona idP = new IdPersona();
				idP.setDocumento(documento);
				idP.setTipoDocumento(tipoDocumento);
				conArt.getPersonaInv().setId(idP);
				errores[0] = "El documento ingresado no existe";
				panelRenderError[0] = true;
				ocultarPaneles(0);
			}
			if (this.conArt.getModalidad().equals("1")) {
				System.out.println("true");
				panelRender[3] = true;

			} else {
				System.out.println("false");
				panelRender[3] = false;
			}
		}

		// System.out.println("this.conArt.getModalidad() =" +
		// this.conArt.getModalidad());

		// System.out.println("panelRender[3]" + panelRender[3]);

	}

	public void verArchivo() {

		ArchivoConvocatoria ain = (ArchivoConvocatoria) tablaArchivosObligatoriosSel
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

	public void limpiarErores(int min, int max) {
		for (int i = min; i <= max; i++) {
			this.errores[i] = "";
			this.panelRenderError[i] = false;
		}
	}

	public boolean validacion(int estado) {
		boolean bandera = true;

		if (estado >= 1) {
			limpiarErores(0, 11);

			if (listaArchivosObligatoriosSel == null
					|| listaArchivosObligatoriosSel.size() <= 0) {
				this.errores[11] = "No tiene archivos adjuntos";
				this.panelRenderError[11] = true;
				bandera = false;
			}

		}

		return bandera;

	}

	public String editarCorreo(Persona personaAux, String id) {
		try {
			String coinvNombre = "";

			String correo = correoActual.getCuerpo();

			String investigador = "";

			investigador = personaAux.getNombre1() + " "
					+ personaAux.getApellido1() + " "
					+ personaAux.getApellido2();
			correo = correo.replaceAll("<<INVESTIGADOR>>", investigador);

			correo = correo.replaceAll("<<IDAVAL>>", id);

			cuerpoCorreo = correo;

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return "";
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

			}

		}
	}

	/* CREADA POR: ING. LILIANA MARCELA OLARTE MESA */
	public void articuloAprobar() {

		errorReq1 = "";
		this.setBanderaReq1(false);
		errorReq2 = "";
		this.setBanderaReq2(false);
		errorReq3 = "";
		this.setBanderaReq3(false);

		if (this.conArt.getRequerimiento1() == null || this.conArt.getRequerimiento1().equals("")) {
			errorReq1 = "Por favor evalue el requisito 1";
			this.setBanderaReq1(true);
			this.panelRenderError[12] = true;
		}

		if (this.conArt.getRequerimiento2() == null || this.conArt.getRequerimiento2().equals("")) {
			errorReq2 = "Por favor evalue el requisito 2";
			this.setBanderaReq2(true);
		}
		if (this.conArt.getRequerimiento1().equals("NO")) {
			errorReq1 = "Es necesario cumplir con el requisito 1 para ser aprobado";
			this.setBanderaReq1(true);
			this.panelRenderError[12] = true;
		}

		if (this.conArt.getRequerimiento2().equals("NO")) {
			errorReq2 = "Es necesario cumplir con el requisito 2 para ser aprobado";
			this.setBanderaReq2(true);
		}
			
		
		if(errorReq1.equals("")&errorReq2.equals("")&errorReq3.equals(""))
		{
			conArt.setEstado(EstadoProyecto.APROBADO);
			servicioGeneral.guardarObjeto(conArt);
			sesion.removeAttribute("ManejadorConsultarEvaluarSolicitudArticulo");
			sesion.removeAttribute("ConsultarEvaluarSolicitudArticulo");
			
			
			
			String mailV = "";
			List mail = this.servicioGeneral
					.obtenerObjetos("FROM Parametro WHERE id = 129");
			if (mail != null && mail.size() > 0) {
				Parametro parametro = (Parametro) mail.get(0);
				mailV = parametro.getValor();
			}

			String mailDestino = conArt.getPersonaInv().getEmail();
			CorreoPlantilla correoActual2 = new CorreoPlantilla();
			correoActual2 = cargarPlantilla(100);
			Correo correo = new Correo();
			correo.setOrigen(Correo.CORREO_HERMES);
			String dirCorreo = Correo.CORREO_HERMES;
			correo.adicionarDireccion(mailV);
			// correo.adicionarCopiaOculta(mailDestino);
			correo.setAsunto(correoActual2.getAsunto());
			cuerpoCorreo = correoActual2.getCuerpo();
			cuerpoCorreo = cuerpoCorreo.replaceAll("<<ID_ARTICULO>>", String
					.valueOf(conArt.getId()));
			cuerpoCorreo = cuerpoCorreo.replaceAll("<<NOMBRE>>", conArt
					.getTitulo());
			String evaluacion = conArt.getEstado();
			if (evaluacion.equals(EstadoProyecto.APROBADO))
				evaluacion = " Aprobada";
			if (evaluacion.equals(EstadoProyecto.NO_APROBADO))
				evaluacion = " No Aprobada";
			cuerpoCorreo = cuerpoCorreo
					.replaceAll("<<EVALUACION>>", evaluacion);
			correo.setCuerpo(cuerpoCorreo);
			servicioCorreo.enviarCorreo(correo);
			
		}
	}

	/* CREADA POR: ING. LILIANA MARCELA OLARTE MESA */
	public void articuloNegar() {

		errorReq1 = "";
		this.setBanderaReq1(false);
		errorReq2 = "";
		this.setBanderaReq2(false);
		errorReq3 = "";
		this.setBanderaReq3(false);

		if (conArt.getRequerimiento1() == null
				|| conArt.getRequerimiento1().equals("")) {
			errorReq1 = "Por favor evalue el requerimiento 1";
			this.setBanderaReq1(true);
		}
		if (conArt.getRequerimiento2() == null
				|| conArt.getRequerimiento2().equals("")) {
			errorReq2 = "Por favor evalue el requerimiento 2";
			this.setBanderaReq2(true);
		}
				
		if (this.conArt.getRequerimiento1().equals("SI")
					&& (this.conArt.getRequerimiento2().equals("SI") || this.conArt.getRequerimiento2().equals("No Aplica"))) {
				setErrorReq3("No debe cumplir con algún requisito para que no sea aprobada la solicitud");
				banderaReq3 = true;
		}
		

		if (errorReq1.equals("") & errorReq2.equals("") & errorReq3.equals("")) {
			conArt.setEstado(EstadoProyecto.NO_APROBADO);
			servicioGeneral.guardarObjeto(conArt);
			sesion
					.removeAttribute("ManejadorConsultarEvaluarSolicitudArticulo");
			sesion.removeAttribute("ConsultarEvaluarSolicitudArticulo");

			String mailV = "";
			List mail = this.servicioGeneral
					.obtenerObjetos("FROM Parametro WHERE id = 129");
			if (mail != null && mail.size() > 0) {
				Parametro parametro = (Parametro) mail.get(0);
				mailV = parametro.getValor();
			}

			String mailDestino = conArt.getPersonaInv().getEmail();
			CorreoPlantilla correoActual2 = new CorreoPlantilla();
			correoActual2 = cargarPlantilla(100);
			Correo correo = new Correo();
			correo.setOrigen(Correo.CORREO_HERMES);
			String dirCorreo = Correo.CORREO_HERMES;
			correo.adicionarDireccion(mailV);
			// correo.adicionarCopiaOculta(mailDestino);
			correo.setAsunto(correoActual2.getAsunto());
			cuerpoCorreo = correoActual2.getCuerpo();
			cuerpoCorreo = cuerpoCorreo.replaceAll("<<ID_ARTICULO>>", String
					.valueOf(conArt.getId()));
			cuerpoCorreo = cuerpoCorreo.replaceAll("<<NOMBRE>>", conArt
					.getTitulo());
			String evaluacion = conArt.getEstado();
			if (evaluacion.equals(EstadoProyecto.APROBADO))
				evaluacion = " Aprobada";
			if (evaluacion.equals(EstadoProyecto.NO_APROBADO))
				evaluacion = " No Aprobada";
			cuerpoCorreo = cuerpoCorreo
					.replaceAll("<<EVALUACION>>", evaluacion);
			correo.setCuerpo(cuerpoCorreo);
			servicioCorreo.enviarCorreo(correo);
		}

	}

	private void cargarValoresIniciales() {
		errores = new String[60];
		panelRender = new boolean[10];
		panelRenderError = new boolean[60];
		personaActual = (Persona) sesion.getAttribute("persona");
		// programa = "";

		listaArchivos = new ArrayList();
		listaArchivosObligatoriosSel = new ArrayList();
		listaArchivosObligatorios = new ArrayList();

		valueGuardar = "Guardar";

		ocultarPaneles(0);
		cargarTipoArchivos();
	}

	public String imprimirReporte() {

		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("id", codigo);
		r.setNombreReporte("/convocatoriaArticulos/ConvocatoriaArticulos");
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		return Navegacion.REPORTE;
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

	public boolean[] getPanelRenderError() {
		return panelRenderError;
	}

	public void setPanelRenderError(boolean[] panelRenderError) {
		this.panelRenderError = panelRenderError;
	}

	/*
	 * public List getModalidad() { return modalidad; }
	 * 
	 * public void setModalidad(List modalidad) { this.modalidad = modalidad; }
	 */

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

	public String getNombreActividad() {
		return nombreActividad;
	}

	public void setNombreActividad(String nombreActividad) {
		this.nombreActividad = nombreActividad;
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

	public ConvocatoriaArticulo getconArt() {
		return conArt;
	}

	public void setconArt(ConvocatoriaArticulo conArt) {
		this.conArt = conArt;
	}

	public List getListaCaracter() {
		return listaCaracter;
	}

	public void setListaCaracter(List listaCaracter) {
		this.listaCaracter = listaCaracter;
	}

	public List getListaTipo() {
		return listaTipo;
	}

	public void setListaTipo(List listaTipo) {
		this.listaTipo = listaTipo;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public boolean isBanderaModalidad() {
		return banderaModalidad;
	}

	public void setBanderaModalidad(boolean banderaModalidad) {
		this.banderaModalidad = banderaModalidad;
	}

	public boolean isBanderaAlianza() {
		return banderaAlianza;
	}

	public void setBanderaAlianza(boolean banderaAlianza) {
		this.banderaAlianza = banderaAlianza;
	}

	public List getListaGrupos() {
		return listaGrupos;
	}

	public void setListaGrupos(List listaGrupos) {
		this.listaGrupos = listaGrupos;
	}

	public HtmlDataTable getTablaGruposSel() {
		return tablaGruposSel;
	}

	public void setTablaGruposSel(HtmlDataTable tablaGruposSel) {
		this.tablaGruposSel = tablaGruposSel;
	}

	public InvestigadorProyecto getInvestigadorProyectoNuevo() {
		return investigadorProyectoNuevo;
	}

	public void setInvestigadorProyectoNuevo(
			InvestigadorProyecto investigadorProyectoNuevo) {
		this.investigadorProyectoNuevo = investigadorProyectoNuevo;
	}

	public SelectItem[] getTipoDocumentoGrupoItem() {
		return tipoDocumentoGrupoItem;
	}

	public void setTipoDocumentoGrupoItem(SelectItem[] tipoDocumentoGrupoItem) {
		this.tipoDocumentoGrupoItem = tipoDocumentoGrupoItem;
	}

	public String getDocumentoLiderGrupo() {
		return documentoLiderGrupo;
	}

	public void setDocumentoLiderGrupo(String documentoLiderGrupo) {
		this.documentoLiderGrupo = documentoLiderGrupo;
	}

	public String getTipoDocumentoLiderGrupo() {
		return tipoDocumentoLiderGrupo;
	}

	public void setTipoDocumentoLiderGrupo(String tipoDocumentoLiderGrupo) {
		this.tipoDocumentoLiderGrupo = tipoDocumentoLiderGrupo;
	}

	public boolean isBanderaCandidatos() {
		return banderaCandidatos;
	}

	public void setBanderaCandidatos(boolean banderaCandidatos) {
		this.banderaCandidatos = banderaCandidatos;
	}

	public String getPaisProcedencia() {
		return paisProcedencia;
	}

	public void setPaisProcedencia(String paisProcedencia) {
		this.paisProcedencia = paisProcedencia;
	}

	public String getPaisEstudio() {
		return paisEstudio;
	}

	public void setPaisEstudio(String paisEstudio) {
		this.paisEstudio = paisEstudio;
	}

	public HtmlDataTable getTablaCandidatosSel() {
		return tablaCandidatosSel;
	}

	public void setTablaCandidatosSel(HtmlDataTable tablaCandidatosSel) {
		this.tablaCandidatosSel = tablaCandidatosSel;
	}

	public CandidatoPosdoctorado getCandidato() {
		return candidato;
	}

	public void setCandidato(CandidatoPosdoctorado candidato) {
		this.candidato = candidato;
	}

	public ActividadPosdoctorado getActividad() {
		return actividad;
	}

	public void setActividad(ActividadPosdoctorado actividad) {
		this.actividad = actividad;
	}

	public String getValueGuardar() {
		return valueGuardar;
	}

	public void setValueGuardar(String valueGuardar) {
		this.valueGuardar = valueGuardar;
	}

	public CorreoPlantilla getCorreoActual() {
		return correoActual;
	}

	public void setCorreoActual(CorreoPlantilla correoActual) {
		this.correoActual = correoActual;
	}

	public CorreoPlantilla getCorreoActual2() {
		return correoActual2;
	}

	public void setCorreoActual2(CorreoPlantilla correoActual2) {
		this.correoActual2 = correoActual2;
	}

	public String getCuerpoCorreo() {
		return cuerpoCorreo;
	}

	public void setCuerpoCorreo(String cuerpoCorreo) {
		this.cuerpoCorreo = cuerpoCorreo;
	}

	public List getListaArchivosObligatorios() {
		return listaArchivosObligatorios;
	}

	public void setListaArchivosObligatorios(List listaArchivosObligatorios) {
		this.listaArchivosObligatorios = listaArchivosObligatorios;
	}

	public SelectItem[] getarticuloItem() {
		return articuloItem;
	}

	public void setarticuloItem(SelectItem[] articuloItem) {
		this.articuloItem = articuloItem;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setErrorReq1(String b) {
		this.errorReq1 = b;
	}

	public String getErrorReq1() {
		return errorReq1;
	}

	public void setErrorReq2(String errorReq2) {
		this.errorReq2 = errorReq2;
	}

	public String getErrorReq2() {
		return errorReq2;
	}

	public void setErrorReq3(String errorReq3) {
		this.errorReq3 = errorReq3;
	}

	public String getErrorReq3() {
		return errorReq3;
	}

	public void setBanderaMensajeTransaccion(boolean banderaMensajeTransaccion) {
		this.banderaMensajeTransaccion = banderaMensajeTransaccion;
	}

	public boolean isBanderaMensajeTransaccion() {
		return banderaMensajeTransaccion;
	}

	public String getMensajeTransaccion() {
		return mensajeTransaccion;
	}

	public void setMensajeTransaccion(String mensajeTransaccion) {
		this.mensajeTransaccion = mensajeTransaccion;
	}

	public void setBanderaReq1(boolean banderaReq1) {
		this.banderaReq1 = banderaReq1;
	}

	public boolean isBanderaReq1() {
		return banderaReq1;
	}

	public void setBanderaReq2(boolean banderaReq2) {
		this.banderaReq2 = banderaReq2;
	}

	public boolean isBanderaReq2() {
		return banderaReq2;
	}

	public void setBanderaReq3(boolean banderaReq3) {
		this.banderaReq3 = banderaReq3;
	}

	public boolean isBanderaReq3() {
		return banderaReq3;
	}

	public void setBanderaReq4(boolean banderaReq4) {
		this.banderaReq4 = banderaReq4;
	}

	public boolean isBanderaReq4() {
		return banderaReq4;
	}

	public void setBanderaMontoAprobado(boolean banderaMontoAprobado) {
		this.banderaMontoAprobado = banderaMontoAprobado;
	}

	public boolean isBanderaMontoAprobado() {
		return banderaMontoAprobado;
	}

	public void setBanderaComentarioEv(boolean banderaComentarioEv) {
		this.banderaComentarioEv = banderaComentarioEv;
	}

	public boolean isBanderaComentarioEv() {
		return banderaComentarioEv;
	}

	public void setApoyos(int apoyos) {
		this.apoyos = apoyos;
	}

	public int getApoyos() {
		return apoyos;
	}

	public void setErrorReq4(String errorReq4) {
		this.errorReq4 = errorReq4;
	}

	public String getErrorReq4() {
		return errorReq4;
	}

}
