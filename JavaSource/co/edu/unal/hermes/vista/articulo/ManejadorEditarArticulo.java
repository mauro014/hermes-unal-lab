package co.edu.unal.hermes.vista.articulo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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

import co.edu.unal.hermes.modelo.ActividadPosdoctorado;
import co.edu.unal.hermes.modelo.ArchivoConvocatoria;
import co.edu.unal.hermes.modelo.CandidatoPosdoctorado;
import co.edu.unal.hermes.modelo.Contrapartida;
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
import co.edu.unal.hermes.modelo.JornadaDocente;
import co.edu.unal.hermes.modelo.Modalidad;
import co.edu.unal.hermes.modelo.Pais;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Programa;
import co.edu.unal.hermes.modelo.Registro;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoInvestigador;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorEditarArticulo extends ManejadorBase {

	private ConvocatoriaArticulo conArt;
    private String codigo;
	private SelectItem[] articuloItem;
	public SelectItem[] getArticuloItem() {
		return articuloItem;
	}

	public void setArticuloItem(SelectItem[] articuloItem) {
		this.articuloItem = articuloItem;
	}

	private String errores[];
	private boolean panelRender[];
	private boolean panelRenderError[];

	private String documento;
	private String tipoDocumento;
	// private String programa;
	private SelectItem[] tipoDocumentoItem;
	private SelectItem[] tipoDocumentoSelItem;

	private List gruposInvestigador;
	private List listaPrograma;
	private List listaMeses;

	private List listaArchivos;
	private List numeroApoyos;

	private String nombreModalidad;

	// private List modalidad;
	private List listaArchivosObligatoriosSel;
	private List listaArchivosObligatorios;

	private List paises;
	private List paisesEstudio;

	private String valueGuardar;

	private List listaGruposSel;
	private List listaCandidatosSel;
	private List listaActividadesSel;

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

	public ManejadorEditarArticulo() {

		try {
			
			personaActual = (Persona) sesion.getAttribute("persona");
			convocatoria = (Convocatoria) sesion.getAttribute("convocatoria");
			Modalidad mod = new Modalidad();
			mod = (Convocatoria) convocatoria;

			if (mod instanceof Convocatoria) {
				nombreModalidad = ((Convocatoria) mod).getTitulo();				
			} else if (mod instanceof JornadaDocente) {
				nombreModalidad = ((JornadaDocente) mod).getDescripcion();
			} else if (mod instanceof Contrapartida) {
				nombreModalidad = ((Contrapartida) mod).getNombre();
			} else if (mod instanceof Registro) {
				nombreModalidad = ((Registro) mod).getNombre();
			} else {
				nombreModalidad = "sin nombre";
			}

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
			listarEstancias();

		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * Long idModalidad_ = (Long) super.sesion.getAttribute("idEstancia");
		 * if (idModalidad_ != null) { infoModalidad(idModalidad_); }
		 */

	}

	/*
	 * private void infoModalidad(Long idModalidad_) { List
	 * listaEstanciaConsulta; listaEstanciaConsulta = new ArrayList();
	 * 
	 * listaEstanciaConsulta = servicioGeneral
	 * .obtenerListaObjetos("EstanciaPosdoctorado where id = '" + idModalidad_ +
	 * "'"); if (listaEstanciaConsulta != null && listaEstanciaConsulta.size() >
	 * 0) { estPos = (EstanciaPosdoctorado) listaEstanciaConsulta.get(0); } }
	 */

	private void cargarPaises() {
		List listaPaises = servicioGeneral.obtenerListaObjetosOrdenadosAsc(
				new Pais(), "nombre");
		paises = new Vector();
		paisesEstudio = new Vector();
		for (Iterator it = listaPaises.iterator(); it.hasNext();) {
			Pais p = (Pais) it.next();
			if (!p.getId().endsWith("CO")) {
				SelectItem s = new SelectItem(p.getId(), p.getNombre());
				SelectItem sAux = new SelectItem(p.getId(), p.getNombre());
				paises.add(s);
				paisesEstudio.add(sAux);
			}
		}
	}

	public void adicionarInvestigador() {
		Investigador nuevoInvestigador = new Investigador();
		listaGrupos = new ArrayList();

		listaGrupos = servicioGrupo
				.obtenerGruposXInvestigador(investigadorProyectoNuevo
						.getInvestigador().getId().getDocumento());

	}

	public void adicionarCandidato() {
		boolean bandera = true;
		limpiarErores(3, 10);

		this.candidato
				.setCandidato(this.candidato.getCandidato().toUpperCase());
		this.candidato.setNacionalidad(this.candidato.getNacionalidad()
				.toUpperCase());
		this.candidato.setCiudad(this.candidato.getCiudad().toUpperCase());
		this.candidato.setTitulo(this.candidato.getTitulo().toUpperCase());
		this.candidato.setUniversidad(this.candidato.getUniversidad()
				.toUpperCase());
		this.candidato.setInstitucion(this.candidato.getInstitucion()
				.toUpperCase());

		if (this.candidato.getCandidato() == null
				|| this.candidato.getCandidato().length() <= 0
				|| this.candidato.getCandidato().length() > 200) {
			this.errores[3] = "Nombre candidato NO válido";
			this.panelRenderError[3] = true;
			bandera = false;
		}

		if (this.candidato.getNacionalidad() == null
				|| this.candidato.getNacionalidad().length() <= 0
				|| this.candidato.getNacionalidad().length() > 200) {
			this.errores[4] = "Nacionalidad NO válida";
			this.panelRenderError[4] = true;
			bandera = false;
		}

		if (this.candidato.getCiudad() == null
				|| this.candidato.getCiudad().length() <= 0
				|| this.candidato.getCiudad().length() > 50) {
			this.errores[5] = "Ciudad NO válida";
			this.panelRenderError[5] = true;
			bandera = false;
		}

		if (this.candidato.getTitulo() == null
				|| this.candidato.getTitulo().length() <= 0
				|| this.candidato.getTitulo().length() > 500) {
			this.errores[6] = "Titulo NO válido";
			this.panelRenderError[6] = true;
			bandera = false;
		}

		if (this.candidato.getGrado() != null) {
			try {
				int ano = Integer.parseInt(this.candidato.getGrado());
				if (ano < 1900 || ano > 2100) {
					this.errores[7] = "Valor NO válido";
					this.panelRenderError[7] = true;
					bandera = false;
				}
			} catch (Exception e) {
				this.errores[7] = "Valor NO válido";
				this.panelRenderError[7] = true;
				bandera = false;
			}
		}

		if (this.candidato.getUniversidad() == null
				|| this.candidato.getUniversidad().length() <= 0
				|| this.candidato.getUniversidad().length() > 200) {
			this.errores[8] = "Universidad NO válida";
			this.panelRenderError[8] = true;
			bandera = false;
		}

		if (this.candidato.getInstitucion() == null
				|| this.candidato.getInstitucion().length() <= 0
				|| this.candidato.getInstitucion().length() > 200) {
			this.errores[9] = "Institución NO válida";
			this.panelRenderError[9] = true;
			bandera = false;
		}

		if (this.candidato.getPerfil() == null
				|| this.candidato.getPerfil().length() <= 0
				|| this.candidato.getPerfil().length() > 4000) {
			this.errores[10] = "Perfil NO válido";
			this.panelRenderError[10] = true;
			bandera = false;
		}

		if (bandera) {
			listaCandidatosSel.add(candidato);
			candidato = new CandidatoPosdoctorado();
		}

	}
	
	public void listarEstancias() {

		List listaSolcitudes = servicioGeneral
				.obtenerListaObjetos("ConvocatoriaArticulo e where e.personaInv.id.documento = '"
						+ personaActual.getId().getDocumento()					
						+ "' and e.personaInv.id.tipoDocumento = '"
						+ personaActual.getId().getTipoDocumento()
						+ "' ORDER BY e.id");

		if (listaSolcitudes != null && listaSolcitudes.size() > 0) {
			
				articuloItem = new SelectItem[listaSolcitudes.size()];
				for (int i = 0; i < listaSolcitudes.size(); i++) {
					ConvocatoriaArticulo estancia = (ConvocatoriaArticulo) listaSolcitudes
							.get(i);
					articuloItem[i] = new SelectItem(String.valueOf(estancia
							.getId()), "Id=" + estancia.getId()
							+ " - Fecha Registro("
							+ estancia.getFechaRegistro() + ")");
				}
				codigo = String
						.valueOf(((ConvocatoriaArticulo) listaSolcitudes
								.get(0)).getId());
				ocultarPaneles(1);
			

		} else {
			articuloItem = new SelectItem[0];

		}

	}


	
	private void cargarTipoArchivos() {
		listaArchivosObligatorios = new ArrayList();
		if (banderaModalidad) {
			listaArchivosObligatorios = servicioGeneral
					.obtenerListaObjetos("DominioDetalle where identificador.id = '7' ORDER BY identificador.tipo");
		} else {
			listaArchivosObligatorios = servicioGeneral
					.obtenerListaObjetos("DominioDetalle where identificador.id = '6' ORDER BY identificador.tipo");
		}
		if (listaArchivosObligatorios != null
				&& listaArchivosObligatorios.size() > 0) {
			tipoDocumentoSelItem = new SelectItem[listaArchivosObligatorios
					.size()];
			for (int i = 0; i < listaArchivosObligatorios.size(); i++) {
				DominioDetalle tae = (DominioDetalle) listaArchivosObligatorios
						.get(i);
				tipoDocumentoSelItem[i] = new SelectItem(
						tae.getIdentificador().getTipo(),tae.getIdentificador().getTipo()+ "-" + tae.getDescripcion());
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

	private void cargarProgramas() {
		listaPrograma = new ArrayList();

		List listaProgramasAux = new ArrayList();
		listaProgramasAux = servicioGeneral
				.obtenerListaObjetos("Programa where (idTipoNivelPrograma = 1) or (idTipoNivelPrograma = 4) ");

		for (Iterator it = listaProgramasAux.iterator(); it.hasNext();) {
			Programa p = (Programa) it.next();
			SelectItem s = new SelectItem(p.getId(), p.getNombre());
			listaPrograma.add(s);
		}
	}

	private void reiniciarVariables() {
		conArt = new ConvocatoriaArticulo();

		if (convocatoria != null && convocatoria.getTitulo() != null
				&& convocatoria.getId().intValue() == 264) {
			this.conArt.setModalidad("1");
			this.conArt.setConvocatoria(String.valueOf(convocatoria.getId()));
			banderaModalidad = false;

		}

		if (convocatoria != null && convocatoria.getTitulo() != null
				&& convocatoria.getId().intValue() == 265) {
			this.conArt.setModalidad("2");
			this.conArt.setConvocatoria(String.valueOf(convocatoria.getId()));
			banderaModalidad = true;
		}

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
				archivo.setNombre(archivoObligatorio.getName().substring(i + 1));
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
		// reiniciarVariables();
		this.panelRenderError[0] = false;
		List objeto = this.servicioGeneral
				.obtenerListaObjetos("ConvocatoriaArticulo e where e.id = '"
						+ codigo + "'");

		
		if (objeto != null && objeto.size() > 0) {
		
			ConvocatoriaArticulo convo = (ConvocatoriaArticulo) objeto
			.get(0);
			this.conArt = convo;
			
			Convocatoria convocatoria = servicioModalidad
			.obtenerConvocatoria(new Long(this.conArt.getConvocatoria()));

			Modalidad mod = new Modalidad();
			mod = (Convocatoria) convocatoria;

			if (mod instanceof Convocatoria) {
				nombreModalidad = ((Convocatoria) mod).getTitulo();
				Convocatoria con = (Convocatoria) mod;
			} 
		
			List lista = this.servicioGeneral
			.obtenerListaObjetos("ArchivoArticulo a where a.articulo = '"
					+ this.conArt.getId() + "'");
			this.setListaArchivosObligatoriosSel(lista);
			
			if (conArt.getModalidad().equals("2")) {
				banderaAlianza = true;
				banderaModalidad = true;
			}

			if (conArt.getModalidad().equals("1")) {
				banderaAlianza = false;
				banderaModalidad = false;
			}

		if (conArt.getPersonaInv() != null) {
			if (conArt.getPersonaInv() instanceof Investigador) {
				if (conArt.getPersonaInv() instanceof InvestigadorInterno) {
					//conArt.setPersonaInv(servicioPersona
						//	.obtenerInvestigadorInternoCompleto(conArt
							//		.getPersonaInv().getId()));
					InvestigadorInterno investigadorInterno = (InvestigadorInterno)servicioPersona.obtenerInvestigadorInternoCompleto(conArt
							.getPersonaInv().getId());
					Dependencia dependencia;
					dependencia = servicioDependencia
							.obtenerDependencia(investigadorInterno.getId());
					String nombre1, nombre2, apellido1, apellido2;
					if (investigadorInterno.getTipoDedicacion() != null
							&& (investigadorInterno.getTipoDedicacion().getId()
									.equals(Investigador.EXCLUSIVA)
									|| investigadorInterno
											.getTipoDedicacion()
											.getId()
											.equals(Investigador.TIEMPOCOMPLETO) || investigadorInterno
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
							this.facultadDocente = dependencia.getFacultad()
									.getNombre();
							this.departamentoDocente = dependencia.getNombre();

						
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
		}
	}

	/*
	 * public void cambiarModalidad(ValueChangeEvent event) {
	 * 
	 * String valor = (String)event.getNewValue();
	 * 
	 * 
	 * if(valor.equals("1")){ banderaModalidad = true;
	 * 
	 * }
	 * 
	 * if(valor.equals("2")){ banderaModalidad = false; }
	 * 
	 * cargarTipoArchivos();
	 * 
	 * }
	 */

	/*
	 * public void cambiarACandidatos(ValueChangeEvent event) {
	 * 
	 * String valor = (String)event.getNewValue();
	 * 
	 * if(valor.equals("SI")){ banderaCandidatos = true; }
	 * 
	 * if(valor.equals("NO")){ banderaCandidatos = false; }
	 * 
	 * }
	 */

	public void cambiarAlianza(ValueChangeEvent event) {

		String valor = (String) event.getNewValue();

		if (valor.equals("0") ) {
			banderaAlianza = true;
			panelRenderError[2] = true;
			errores[2] = "";
			this.ocultarPaneles(2);
			
		}
		
		if (valor.equals("1") || valor.equals("2")) {
			banderaAlianza = true;
			panelRenderError[2] = true;
			errores[2] = "Debe anexar certificado de la radicación de(l) artículo(s) en los archivos adjuntos";
			this.ocultarPaneles(2);
		}

		if (valor.equals("3")) {
			banderaAlianza = false;
			panelRenderError[2] = true;
			errores[2] = "No se permite diligenciar la solicitud por superar el máximo de apoyos por año";
		}

	}

	public void adicionarActividad() {
		boolean bandera = true;

		if (this.actividad.getNombre() == null
				|| this.actividad.getNombre().length() <= 0
				|| this.actividad.getNombre().length() > 300) {
			this.errores[26] = "Nombre de la actividad NO válida";
			this.panelRenderError[26] = true;
			bandera = false;
		}

		if (this.actividad.getDescripcion() == null
				|| this.actividad.getDescripcion().length() <= 0
				|| this.actividad.getDescripcion().length() > 4000) {
			this.errores[14] = "Descripción NO válida";
			this.panelRenderError[14] = true;
			bandera = false;
		}

		if (this.actividad.getPertinencia() == null
				|| this.actividad.getPertinencia().length() <= 0
				|| this.actividad.getPertinencia().length() > 4000) {
			this.errores[15] = "Pertinencia NO válida";
			this.panelRenderError[15] = true;
			bandera = false;
		}

		if (this.actividad.getImpacto() == null
				|| this.actividad.getImpacto().length() <= 0
				|| this.actividad.getImpacto().length() > 4000) {
			this.errores[16] = "Impacto NO válido";
			this.panelRenderError[16] = true;
			bandera = false;
		}

		if (bandera) {
			listaActividadesSel.add(actividad);
			actividad = new ActividadPosdoctorado();
		}

	}

	public void eliminarActividad() {
		listaActividadesSel.remove(tablaActividadesSel.getRowIndex());
	}

	public void editarActividad() {

		ActividadPosdoctorado actPos = (ActividadPosdoctorado) tablaActividadesSel
				.getRowData();
		this.actividad = actPos;
		listaActividadesSel.remove(tablaActividadesSel.getRowIndex());

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
			limpiarErores(0, 7);
			if (this.conArt.getModalidad().equals("2")) {
				if (this.conArt.getDependencia() == null
						|| this.conArt.getDependencia().length() <= 0
						|| this.conArt.getDependencia().length() > 500) {
					this.errores[1] = "Constrapartida NO válida";
					this.panelRenderError[1] = true;
					bandera = false;
				}
			}
			if (this.conArt.getTitulo() == null
					|| this.conArt.getTitulo().length() <= 0
					|| this.conArt.getTitulo().length() > 1000) {
				this.errores[3] = "Título NO válido";
				this.panelRenderError[3] = true;
				bandera = false;
			}
			
			try {
					int palabras = Integer.parseInt(this.conArt.getPalabras());
					
			} catch (Exception e) {
				this.errores[4] = "Número de palabras NO válido";
				this.panelRenderError[4] = true;
				bandera = false;
			}
			
			if (this.conArt.getCoautores() == null
					|| this.conArt.getCoautores().length() <= 0
					|| this.conArt.getCoautores().length() > 1000) {
				this.errores[5] = "Coautores NO válido";
				this.panelRenderError[5] = true;
				bandera = false;
			}
			
			if (this.conArt.getIssn() == null
					|| this.conArt.getIssn().length() <= 0
					|| this.conArt.getIssn().length() > 500) {
				this.errores[6] = "ISSN NO válido";
				this.panelRenderError[6] = true;
				bandera = false;
			}
			
			if (this.conArt.getResumen() == null
					|| this.conArt.getResumen().length() <= 0
					|| this.conArt.getResumen().length() > 4000) {
				this.errores[7] = "Resumen NO válido";
				this.panelRenderError[7] = true;
				bandera = false;
			}
			
		}
		
		return bandera;

	}

	public void guardar() {

		if (validacion(estado)) {

			if (estado >= 1) {
				if (this.conArt.getId() == null) {
					
					Set archivoSet = new HashSet();
					for (int i = 0; i < listaArchivosObligatoriosSel.size(); i++) {
						ArchivoConvocatoria archivo = new ArchivoConvocatoria();
						archivo = (ArchivoConvocatoria) listaArchivosObligatoriosSel
								.get(i);
						archivo.setConvocatoria(String.valueOf(this.conArt.getId()));
						archivoSet.add(archivo);
						
					}
					this.conArt.setArchivos(archivoSet);
					Calendar actual = Calendar.getInstance();
					Date date = actual.getTime();
					this.conArt.setFechaRegistro(date);
					this.conArt.setEstado(EstadoProyecto.PROPUESTO);
					servicioGeneral.guardarObjeto(this.conArt);									
						
					this.limpiar();
					personaActual = (Persona) sesion.getAttribute("persona");
					this.errores[0] = "Solicitud creada satisfactoriamente.";
					this.panelRenderError[0] = true;
					
				}
			}

		}

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
			// correo = correo.replaceAll("<<TIPO>>", mov.getTipoMovilidad()
			// .getNombre());

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

	/*
	 * private void cargarPaises() { List listaPaises =
	 * servicioGeneral.obtenerListaObjetosOrdenadosAsc( new Pais(), "nombre");
	 * paises = new Vector(); nacionalidad = new Vector();
	 * 
	 * for (Iterator it = listaPaises.iterator(); it.hasNext();) { Pais p =
	 * (Pais) it.next(); SelectItem s = new SelectItem(p.getId(),
	 * p.getNombre()); paises.add(s);
	 * 
	 * }
	 * 
	 * for (Iterator it = listaPaises.iterator(); it.hasNext();) { Pais p =
	 * (Pais) it.next(); SelectItem s1 = new SelectItem(p.getId(),
	 * p.getNombre()); nacionalidad.add(s1); } }
	 */

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
		



		numeroApoyos = new Vector();
		numeroApoyos.add(new SelectItem("-1", "Seleccione"));
		numeroApoyos.add(new SelectItem("0", "0"));
		numeroApoyos.add(new SelectItem("1", "1"));
		numeroApoyos.add(new SelectItem("2", "2"));
		numeroApoyos.add(new SelectItem("3", "3 o más"));

	
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

	public List getGruposInvestigador() {
		return gruposInvestigador;
	}

	public void setGruposInvestigador(List gruposInvestigador) {
		this.gruposInvestigador = gruposInvestigador;
	}

	public ConvocatoriaArticulo getConArt() {
		return conArt;
	}

	public void setConArt(ConvocatoriaArticulo conArt) {
		this.conArt = conArt;
	}

	public List getnumeroApoyos() {
		return numeroApoyos;
	}

	public void setnumeroApoyos(List numeroApoyos) {
		this.numeroApoyos = numeroApoyos;
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

	public List getListaPrograma() {
		return listaPrograma;
	}

	public void setListaPrograma(List listaPrograma) {
		this.listaPrograma = listaPrograma;
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

	public List getListaGruposSel() {
		return listaGruposSel;
	}

	public void setListaGruposSel(List listaGruposSel) {
		this.listaGruposSel = listaGruposSel;
	}

	public boolean isBanderaCandidatos() {
		return banderaCandidatos;
	}

	public void setBanderaCandidatos(boolean banderaCandidatos) {
		this.banderaCandidatos = banderaCandidatos;
	}

	public List getPaises() {
		return paises;
	}

	public void setPaises(List paises) {
		this.paises = paises;
	}

	public String getPaisProcedencia() {
		return paisProcedencia;
	}

	public void setPaisProcedencia(String paisProcedencia) {
		this.paisProcedencia = paisProcedencia;
	}

	public List getPaisesEstudio() {
		return paisesEstudio;
	}

	public void setPaisesEstudio(List paisesEstudio) {
		this.paisesEstudio = paisesEstudio;
	}

	public String getPaisEstudio() {
		return paisEstudio;
	}

	public void setPaisEstudio(String paisEstudio) {
		this.paisEstudio = paisEstudio;
	}

	public List getListaCandidatosSel() {
		return listaCandidatosSel;
	}

	public void setListaCandidatosSel(List listaCandidatosSel) {
		this.listaCandidatosSel = listaCandidatosSel;
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

	public List getListaActividadesSel() {
		return listaActividadesSel;
	}

	public void setListaActividadesSel(List listaActividadesSel) {
		this.listaActividadesSel = listaActividadesSel;
	}

	public List getListaMeses() {
		return listaMeses;
	}

	public void setListaMeses(List listaMeses) {
		this.listaMeses = listaMeses;
	}

	public String getValueGuardar() {
		return valueGuardar;
	}

	public void setValueGuardar(String valueGuardar) {
		this.valueGuardar = valueGuardar;
	}

	public String getNombreModalidad() {
		return nombreModalidad;
	}

	public void setNombreModalidad(String nombreModalidad) {
		this.nombreModalidad = nombreModalidad;
	}

	public CorreoPlantilla getCorreoActual() {
		return correoActual;
	}

	public void setCorreoActual(CorreoPlantilla correoActual) {
		this.correoActual = correoActual;
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}	
	
}
