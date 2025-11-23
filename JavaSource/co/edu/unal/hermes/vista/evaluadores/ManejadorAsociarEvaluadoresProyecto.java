package co.edu.unal.hermes.vista.evaluadores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import co.edu.unal.hermes.modelo.CategoriaInvestigador;
import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.ClasificacionConocimiento;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.Dominio;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.Evaluador;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.HistoricoInfoEvaluador;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Institucion;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorExterno;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.InvestigadorLineaInvestigacion;
import co.edu.unal.hermes.modelo.LineaInvestigacion;
import co.edu.unal.hermes.modelo.Modalidad;
import co.edu.unal.hermes.modelo.Parametro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PosibleEvaluador;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.ProyectoEvaluador;
import co.edu.unal.hermes.modelo.Rol;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoFormacion;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.utils.Fecha;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorAsociarEvaluadoresProyecto extends ManejadorBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Persona personaActual;

	private String tipoBusqueda;
	private String idProyecto;
	private boolean coordinadorTieneProyectos;
	private SelectItem[] proyectoItem;
	private String tipoBusquedaEval;
	String campoBusquedaNombreInvestigador;
	String campoBusquedaApellidoInvestigador;
	private SelectItem[] tipoDocumentoItem;

	// Campos nuevo investigador
	private String genero;

	// Renders
	private boolean mostrarBusquedaProyecto;
	private boolean mostrarBusquedaConvocatoria;

	private List listaProyectos;
	private String nombreConvocatoria;
	private List listaProyectosclone;
	private String proyectoId;
	private Proyecto proyectoActual;
	private Convocatoria convocatoriaActual;
	private String mensajeBusqueProyecto;
	private String mensajeBusqueProyecto2;
	private String tipoDocumentoNuevo;
	private Institucion institucionNueva;
	private String mensajeBusEval = "";
	private String mensajeCrear = "";
	private Long posibleEvaluadorSel10;
	private Long posibleEvaluadorSel2;
	private String posibleEvaluadorSel3;
	private String posibleEvaluadorSel4;
	private String institucionEvaluador;
	private String correoActualCuerpo;
	private String correoActualCuerpo2;
	private String correoActualCuerpo3;
	private String correoActualCuerpo4;
	PosibleEvaluador posevalu2;
	private Investigador posevalu3;
	private Investigador posibleEval3;
	private Persona posevaluador3;
	private Persona posevaluador4;
	private ProyectoEvaluador evaluadorSeleccionado;

	Persona posevalu4;
	private String idCorreo;
	private String idCorreo4;

	String mensajeCorreo4 = "";
	List correosL;

	private String tipoDocumentoB;
	private String documentoEvaluadorB;

	private boolean permiteGuardar;
	private boolean editandoEvaluador;

	private long idPosibleEvaluador;
	private List listaConvocatorias;
	private List listaConvocatoriasPadre;
	private SelectItem[] convocatoriaItem;
	private SelectItem[] posiblesEvaluadoresItem10;
	private SelectItem[] posiblesEvaluadoresItem2;
	private SelectItem[] posiblesEvaluadoresItem3;
	private SelectItem[] posiblesEvaluadoresItem4;

	private SelectItem[] convocatoriaPadreItem;

	private String nombreModalidad;
	private String nombrePadre;
	private String convocatoriaPadre;
	private String convocatoria;
	private String idClasificacionConocimiento;
	private Date fechaFinalEvaluacion;

	private List listaPosiblesEvaluadores2;
	private List listaPosiblesEvaluadores3;
	private List<PosibleEvaluador> listaPosiblesEvaluadores10;
	private String mensajeCorreo;
	private String mensajeCorreo2;
	private String mensajeCorreo3;
	private String mensajeError2 = "";
	private String mensajeError3 = "";
	private String mensajeError4 = "";
	private TipoDocumento tipoDocumento;
	private String documento;
	private String docEvaluador;
	private List estadoEvaluador;
	private boolean mostrarEvaluador;
	private SelectItem[] correos;
	private String correoId2;
	private String correoId3;
	private String correoId4;
	private CorreoPlantilla correoActual2;
	private CorreoPlantilla correoActual3;
	private CorreoPlantilla correoActual4;
	private String cuerpoCorreo2;
	private String cuerpoCorreo3;
	private String cuerpoCorreo4;
	private String correoDestino;
	private boolean detalle;
	private boolean detalle2;
	private boolean detalle3;
	private boolean detalle4;
	private String nombreDestino;
	private Institucion institucionInicial;
	private boolean panelRender[];
	private String nombreProy;

	private boolean verEvaluadores;
	private boolean verPosiblesEvaluadores;

	public String documentoParaCorreo;
	public String tipoDocumentoParaCorreo;
	public SelectItem[] tiposDocumentoItem;
	public List listaTipoDocumento;
	private String mensajeBusqueda;
	private String mensajeConvocatoria;
	private boolean correcto;
	private String[] contactado;
	private List listaEvaluadorCorreo;
	private boolean mostrado;
	private String mensajeRespuesta;
	private String[] evaluadoresSeleccionadosArray;
	private SelectItem[] evaluadoresItemArray;
	private String mensajeEvaluador;
	private boolean mostrarNuevoEvaluador;
	private String codigoEvaluador;
	private String telefono;
	private String email;
	private List areasExperticia;
	private SelectItem[] areasExperticiaItem;
	private String areaPorExperticia;
	private InvestigadorExterno personaNueva = new InvestigadorExterno();
	private String nombre;
	private String nombre2;
	private String primerApellido;
	private String segundoApellido;
	private Investigador investigadorEvaluador = new Investigador();
	private List<Institucion> listaInstituciones;
	private ArrayList<SelectItem> listaInstitucionesItem;
	private boolean esInterno;
	private boolean esInternoAdministrativo = false;
	private String experticia;
	private String cvlac;
	private boolean esNuevo;
	private boolean esNuevoEvaluador; //implica que es nuevo omo evaluador externo aunque exista como persona o incluso como investigador
	private String subAreaCiencia;
	private String areaCiencia;
	private String formacion;
	private SelectItem[] areaCienciaItems;
	private SelectItem[] subAreaCienciaItems;
	private SelectItem[] tipoFormacionItem;
	private SelectItem[] listaLineasInvestigacionItems;
	private String facultad;
	private String sede;
	private String perfil;
	private String tesis;
	private String tipo;
	private Evaluador posibleEvaluadorExterno;
	private InvestigadorInterno posibleEvaluadorInterno;
	private String institucionEstudio;
	private String institucionLabora;
	private String minciencias;
	private boolean aceptaTerminos;
	private String lineaInvestigacion;
	private LineaInvestigacion lineaInvestigacionSeleccionada;
	private List<LineaInvestigacion> listaLineasInvestigacionEvaluador;
	private SelectItem[] siNoItem = { new SelectItem("S", "SI"), new SelectItem("N", "NO") };
	private SelectItem[] tipoItem = { new SelectItem("NAC", "Nacional"), new SelectItem("INT", "Internacional") };
	/** The requiere otra fuente. */
	private boolean requiereOtraFuente;
	/** The tipos naturaleza fuente item. */
	private SelectItem[] tiposNaturalezaFuenteItem;
	/** The tipos caracterFuenteFinanciacionItem. */
	public SelectItem[] caracterFuenteFinanciacionItem;
	/** The tipos tiposFuenteFinanciacionItem. */
	public SelectItem[] tiposFuenteFinanciacionItem;

	public ManejadorAsociarEvaluadoresProyecto() {

		permiteGuardar = false;
		editandoEvaluador = false;
		esNuevo = false;
		esNuevoEvaluador = false;
		esInterno = false;
		idPosibleEvaluador = 0;
		idClasificacionConocimiento = "";
		mostrarEvaluador = false;
		mostrarNuevoEvaluador = false;
		borrarMensajesError();
		personaActual = (Persona) sesion.getAttribute("persona");

		convocatoriaPadreItem = new SelectItem[0];
		convocatoriaItem = new SelectItem[0];
		listaProyectos = new ArrayList();
		listaProyectosclone = new ArrayList();
		listaLineasInvestigacionEvaluador = new ArrayList();

		correosL = servicioGeneral.obtenerListaObjetos("CorreoPlantilla where evaluacion = 'Y'");
		correos = new SelectItem[correosL.size()];
		int i = 0;
		for (Iterator ic = correosL.iterator(); ic.hasNext(); i++) {
			CorreoPlantilla p = (CorreoPlantilla) ic.next();
			correos[i] = new SelectItem(p.getId().toString(), p.getNombre());
		}

		// reemplazarCuerpo();
//        listaTipoDocumento = servicioGeneral.obtenerObjetos(TipoDocumento.class,
//                "from TipoDocumento td where td.id not in ('D','T','I')");

		listaTipoDocumento = servicioGeneral.obtenerTiposDeDocumento();

		tiposDocumentoItem = new SelectItem[listaTipoDocumento.size()];
		int contadorTipoDocumento = 0;
		for (Iterator itTipoDocumento = listaTipoDocumento.iterator(); itTipoDocumento
				.hasNext(); contadorTipoDocumento++) {
			TipoDocumento td = (TipoDocumento) itTipoDocumento.next();
			tiposDocumentoItem[contadorTipoDocumento] = new SelectItem(td.getId(), td.getNombre());
		}

		listaPosiblesEvaluadores2 = new ArrayList();
		listaPosiblesEvaluadores3 = new ArrayList();

		listaPosiblesEvaluadores10 = new ArrayList();

		tipoBusquedaEval = "documento";
		panelRender = new boolean[20];

		personaActual = (Persona) sesion.getAttribute("persona");
		panelRender[3] = false;
		panelRender[5] = false;
		panelRender[6] = true;
		panelRender[7] = false;
		panelRender[10] = false;
		panelRender[11] = false;
		panelRender[12] = false;
		panelRender[14] = false;
		panelRender[15] = false;
		panelRender[16] = false;
		posevalu2 = new PosibleEvaluador();
		posevalu3 = new Investigador();

		// consultarListaInstituciones();
		cargarAreasCiencia();
		cargarFuentesFinanciacionExternas();
		cargarTiposFormacion();
		cargarLineasInvestigacion();

	}

	public void cargarTiposFormacion() {
		List<TipoFormacion> listaTipoFormacion = servicioGeneral.obtenerTiposDeFormacion();
		setTipoFormacionItem(new SelectItem[listaTipoFormacion.size()]);
		for (int i = 0; i < listaTipoFormacion.size(); i++) {
			TipoFormacion td = listaTipoFormacion.get(i);
			tipoFormacionItem[i] = new SelectItem(td.getId(), td.getNombre());
		}
	}

	/*
	 * public void consultarListaInstituciones() { try { listaInstituciones =
	 * servicioGeneral.obtenerObjetosLimitado(Institucion.class,
	 * "select #id ins.id, #nombre ins.nombre  " + "from Institucion ins " +
	 * "order by ins.nombre asc");
	 * 
	 * listaInstitucionesItem = new ArrayList<SelectItem>(); for (int i = 0; i <
	 * listaInstituciones.size(); i++) { Institucion institucion = (Institucion)
	 * listaInstituciones.get(i); listaInstitucionesItem.add(new
	 * SelectItem(institucion.getId(), institucion.getNombre())); }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 */

	public void eliminarEvaluador() {
		borrarMensajesError();
		ProyectoEvaluador pe = evaluadorSeleccionado;
		proyectoActual.borrarEvaluadorProyecto(pe);
		servicioGeneral.eliminarObjeto(pe);
	}

	private void cargarTiposDocumento() {
		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			TipoDocumento td = (TipoDocumento) listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
		}
		tipoDocumento = (TipoDocumento) listaTipoDocumento.get(0);
	}

	public void buscarEvaluador() {

		if ((documentoEvaluadorB != null && documentoEvaluadorB.length() > 0)
				|| (campoBusquedaApellidoInvestigador != null && campoBusquedaApellidoInvestigador.length() > 0)) {
			IdPersona investigador = new IdPersona(documentoEvaluadorB, tipoDocumentoB);

			codigoEvaluador = documentoEvaluadorB;
			tipoDocumentoNuevo = tipoDocumentoB;

			Persona persona = new Persona();
			ArrayList<Investigador> investigadores = new ArrayList<Investigador>();

			try {
				persona = servicioPersona.obtenerPersona(investigador);
				/*investigadores = (ArrayList<Investigador>) servicioGeneral
						.obtenerObjetos("select pe from Investigador pe" + " where pe.id.documento  ='"
								+ codigoEvaluador + "'  and pe.id.tipoDocumento  ='" + tipoDocumentoNuevo + "' ");*/
			} catch (Exception e) {
				e.printStackTrace();
			}

			Investigador invesNuevo = new Investigador();

			//if (investigadores != null && investigadores.size() > 0) {
			if(persona!=null && persona.getId()!=null) {
				
				try {
					investigadores = (ArrayList<Investigador>) servicioGeneral
							.obtenerObjetos("select pe from Investigador pe" + " where pe.id.documento  ='"
									+ codigoEvaluador + "'  and pe.id.tipoDocumento  ='" + tipoDocumentoNuevo + "' ");
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(investigadores!=null && investigadores.size()>0) {
				invesNuevo = (Investigador) investigadores.get(0);
				investigadorEvaluador = (Investigador) investigadores.get(0);
				esNuevo = false; 
				}else {
					invesNuevo.setId(persona.getId());
					invesNuevo.setNombre1(persona.getNombre1());
					invesNuevo.setNombre2(persona.getNombre2());
					invesNuevo.setApellido1(persona.getApellido1());
					invesNuevo.setApellido2(persona.getApellido2());
					invesNuevo.setEmail(persona.getEmail());
					investigadorEvaluador = (Investigador) invesNuevo;
					esNuevo = false; 
				}
				investigadorRes = "Posible evaluador encontrado: " + persona.getNombre1() + " "
						+ persona.getNombre2() + " " + persona.getApellido1() + " " + persona.getApellido2();
				mensajeError2 = "";
			} else {
				esNuevo = true;
				investigadorRes = "No se encontró ninguna persona con los datos proporcionados, pero puede crear el evaluador";
				mensajeEvaluador = "";

			}
			panelRender[14] = false;
			panelRender[16] = true;

		}

	}

	public void guardarEvaluador() {

		if (validacion()) {

			/*
			 * Porción de código existente antes de requerimiento evaluadores, se requiere
			 * // evaluar si se deja... PosibleEvaluador pos = new PosibleEvaluador(); Set
			 * setClasificaciones = new HashSet(); pos.setProyecto(proyectoActual);
			 * pos.setTipoDocumento(tipoDocumentoNuevo); pos.setDocumento(codigoEvaluador);
			 * pos.setNombre(nombre);
			 * 
			 * pos.setExperticia(areaPorExperticia); pos.setApellido1(primerApellido);
			 * pos.setApellido2(segundoApellido);
			 * 
			 * // List instituciones = servicioGeneral.obtenerObjetos(Institucion.class, //
			 * "select i from Institucion i where i.id = '" + institucionEvaluador + "'");
			 * // institucionNueva = (Institucion) (instituciones.get(0)); //
			 * pos.setInstitucion(institucionNueva.getNombre());
			 * 
			 * pos.setTelefono(telefono); pos.setEmail(email); pos.setTipo("C"); String
			 * mensaje; if (editandoEvaluador) { pos.setId(new Long(idPosibleEvaluador));
			 * mensaje = servicioPersona.actualizarEvaluadorExterno(pos); } else { String
			 * consecutivo = servicioPersona.buscarUltimoConsecutivo(); mensaje =
			 * servicioPersona.insertarNuevoEvaluadorExterno(pos, consecutivo); }
			 * 
			 */

			IdPersona per = new IdPersona();
			per.setDocumento(codigoEvaluador);
			per.setTipoDocumento(tipoDocumentoNuevo);
			Persona person = servicioPersona.obtenerPersona(per);
			if (!esInterno && !esInternoAdministrativo) {
				if (person == null) {
					person = new Persona();
					person.setId(per);
				}
				person.setApellido1(primerApellido);
				person.setApellido2(segundoApellido);
				person.setNombre1(nombre);
				person.setNombre2(nombre2);
				person.setGenero(genero);
				person.setTelefono(telefono);
				person.setEmail(email);
				if(person.getCiudadDomicilio()==null) {
					Ciudad ciudad = new Ciudad();
					ciudad.setId("00");
					person.setCiudadExpedicion(ciudad);
					person.setCiudadDomicilio(ciudad);
					person.setDireccion("Sin dirección");
				}
				if (!esNuevo) {
					servicioPersona.actualizarPersonaDatosBasicos(person);
				} else {
					servicioPersona.insertarNuevaPersona(person);
				}
			} else if (esInterno) {
				try {
					InvestigadorInterno evaluadorInterno = servicioPersona.obtenerInvestigadorInternoCompleto(per);
					evaluadorInterno.setEvaluadorMinciencias(minciencias);
					evaluadorInterno.setAreaExperticia(experticia);
					servicioGeneral.guardarObjeto(evaluadorInterno);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (!esInterno) {
				verificarCambiosInfo();

				Evaluador evaluador = new Evaluador();
				evaluador.setId(per);
				evaluador.setTesis(tesis);
				evaluador.setAreaExperticia(experticia);
				FuenteFinanciacion institucionEstudioEvaluador = new FuenteFinanciacion();
				institucionEstudioEvaluador.setId(institucionEstudio);
				evaluador.setInstitucionEstudio(institucionEstudioEvaluador);
				FuenteFinanciacion institucionLaboraEvaluador = new FuenteFinanciacion();
				institucionLaboraEvaluador.setId(institucionLabora);
				evaluador.setInstitucionLabora(institucionLaboraEvaluador);
				evaluador.setAreaCiencia(areaCiencia);
				evaluador.setSubAreaCiencia(subAreaCiencia);
				evaluador.setCvlac(cvlac);
				evaluador.setFormacion(formacion);
				evaluador.setMinciencias(minciencias);
				evaluador.setTipo(tipo);

				try {
					if (esNuevo || !(person instanceof Investigador)) {
						Investigador registroInvestigador = new Investigador();
						registroInvestigador.setId(per);
						registroInvestigador.setCategoriaInvestigador(new CategoriaInvestigador((long) 3));
						registroInvestigador.setInterno("N");
						registroInvestigador.setEvaluador("S");
						registroInvestigador.setEsFuncionario("N");
						registroInvestigador.setUrlColciencias(cvlac);
						registroInvestigador.setAreaOcde(areaCiencia);
						registroInvestigador.setSubareaOcde(subAreaCiencia);
						servicioPersona.insertarInvestigador(registroInvestigador);
						servicioPersona.insertarNuevoEvaluador(evaluador);
					} else if(esNuevoEvaluador){
						servicioPersona.insertarNuevoEvaluador(evaluador);
					}else {
						servicioPersona.actualizarEvaluador(evaluador);
					}

					if (!(person instanceof InvestigadorExterno)) {
						InvestigadorExterno ie = new InvestigadorExterno();
						ie.setId(per);
						FuenteFinanciacion institucion = new FuenteFinanciacion();
						institucion.setId(institucionLabora);
						ie.setInstitucionInvestigador(institucion);
						servicioPersona.insertarNuevoInvestigadorExterno(ie);
					}

					if (listaLineasInvestigacionEvaluador != null && listaLineasInvestigacionEvaluador.size() > 0) {
						servicioPersona.guardarLineasInvestigacionEvaluadorExterno(listaLineasInvestigacionEvaluador,
								per);
					}
					
					mensajeInfo("Evaluador guardado");

				} catch (Exception e) {
					mensajeError("Ocurrió un problema al guardar el evaluador");
					e.printStackTrace();
				}

			}

			/*
			 * if (mensajeInv == null || mensajeInv.equals("")) { mensajeEvaluador =
			 * "El investigador fue creado con exito"; } else { mensajeEvaluador =
			 * "El investigador no pudo ser creado"; }
			 * 
			 * if (mensaje == null || mensaje.equals("")) {
			 * cancelarAgregarPosibleEvaluador(); List listaPosiblesEvaluadores11 =
			 * servicioGeneral .obtenerObjetos("select pe from PosibleEvaluador pe" +
			 * " where pe.documento='" + pos.getDocumento() + "' and pe.tipoDocumento = '" +
			 * pos.getTipoDocumento() + "'"); if (listaPosiblesEvaluadores11 != null &&
			 * listaPosiblesEvaluadores11.size() > 0) {
			 * listaPosiblesEvaluadores10.add((PosibleEvaluador)
			 * listaPosiblesEvaluadores11.get(0)); } posiblesEvaluadoresItem10 = new
			 * SelectItem[listaPosiblesEvaluadores10.size()]; int i = 0; for
			 * (PosibleEvaluador pose : listaPosiblesEvaluadores10) { SelectItem item = new
			 * SelectItem(pose.getId().toString(), pose.getNombreCompleto());
			 * posiblesEvaluadoresItem10[i++] = item; } mensajeEvaluador =
			 * "El evaluador externo ha sido creado"; } else { mensajeEvaluador = mensaje; }
			 */

		}
	}

	public void crearActualizarDatosEvaluador() {
		posibleEvaluadorExterno.setNombre1(nombre);
		posibleEvaluadorExterno.setNombre2(nombre2);
		posibleEvaluadorExterno.setApellido1(primerApellido);
		posibleEvaluadorExterno.setApellido2(segundoApellido);
	}

	public void crearHistoricoEdicionEvaluador(HistoricoInfoEvaluador historico, String comentario) {
		historico.setFechaCambio(new Date());
		historico.setResponsable(personaActual);
		historico.setObservacion(comentario);
		try {
			this.servicioGeneral.guardarObjeto(historico);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Boolean camposIguales(String campoOrigen, String campoFormulario) {
		boolean iguales = false;
		if (campoOrigen == null && campoFormulario == null) {
			iguales = true;
		} else if (campoOrigen != null && campoOrigen.equals(campoFormulario)) {
			iguales = true;
		} 

		return iguales;
	}

	public void verificarCambiosInfo() {
		if (!esNuevo && !esInterno && posibleEvaluadorExterno != null) {
			boolean modificacion = false;
			HistoricoInfoEvaluador historico = new HistoricoInfoEvaluador();
			if (!camposIguales(posibleEvaluadorExterno.getNombre11(), nombre)
					|| !camposIguales(posibleEvaluadorExterno.getNombre22(), nombre2)) {
				modificacion = true;
				historico.setNombres("S");
			}
			if (!camposIguales(posibleEvaluadorExterno.getApellido11(), primerApellido)
					|| !camposIguales(posibleEvaluadorExterno.getApellido22(), (segundoApellido))) {
				modificacion = true;
				historico.setApellidos("S");
			}
			if (!camposIguales(posibleEvaluadorExterno.getGenero(), genero)) {
				modificacion = true;
				historico.setGenero("S");
			}
			if (!camposIguales(posibleEvaluadorExterno.getFormacion(), formacion)) {
				modificacion = true;
				historico.setFormacion("S");
			}
			if (!camposIguales(posibleEvaluadorExterno.getTipo(), tipo)) {
				modificacion = true;
				historico.setTipo("S");
			}
			if (!camposIguales(posibleEvaluadorExterno.getInstitucionLabora().getId(), institucionLabora)) {
				modificacion = true;
				historico.setInstitucionLabora("S");
			}
			if (!camposIguales(posibleEvaluadorExterno.getAreaCiencia(), areaCiencia)) {
				modificacion = true;
				historico.setAreaCiencia("S");
			}
			if (!camposIguales(posibleEvaluadorExterno.getSubAreaCiencia(), subAreaCiencia)) {
				modificacion = true;
				historico.setSubAreaCiencia("S");
			}
			if (!camposIguales(posibleEvaluadorExterno.getCvlac(), cvlac)) {
				modificacion = true;
				historico.setCvlac("S");
			}
			if (!camposIguales(posibleEvaluadorExterno.getMinciencias(), minciencias)) {
				modificacion = true;
				historico.setMinciencias("S");
			}
			if (!camposIguales(posibleEvaluadorExterno.getEmail(), email)) {
				modificacion = true;
				historico.setEmail("S");
			}
			if (posibleEvaluadorExterno.getLineasInvestigacion() != null && listaLineasInvestigacionEvaluador!=null 
					&& (posibleEvaluadorExterno.getLineasInvestigacion().size() != listaLineasInvestigacionEvaluador.size())) {
				modificacion = true;
				historico.setLineaInvestigacion("S");
			}

			if (modificacion) {
				historico.setEvaluador(posibleEvaluadorExterno);
				crearHistoricoEdicionEvaluador(historico, "Modificación de datos");
			}
		} else if (esNuevo) {
			HistoricoInfoEvaluador historico = new HistoricoInfoEvaluador();
			Investigador evaluador = new Investigador();
			IdPersona id = new IdPersona();
			id.setTipoDocumento(tipoDocumentoNuevo);
			id.setDocumento(codigoEvaluador);
			evaluador.setId(id);
			historico.setEvaluador(evaluador);
			crearHistoricoEdicionEvaluador(historico, "Creación del evaluador");
		}
	}

	public Boolean verificarCambiosLineaInvestigacion() {
		boolean sinCambios = false;
		return sinCambios;
	}

	public void cargarAreasCiencia() {
		List<DominioDetalle> listaAreaCiencia = servicioGeneral.obtenerDominioDetalle(DOMINIO_AREA_CIENCIA, false);
		areaCienciaItems = crearListaItems(listaAreaCiencia);
	}

	public void cambiarArea() {

		String consultaAreasSec = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
				+ DOMINIO_SUB_AREA_CIENCIA + "' and  dd.estado = '" + areaCiencia + "' order by dd.descripcion";
		List<DominioDetalle> listaSubAreaCiencia = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				consultaAreasSec);
		setSubAreaCienciaItems(crearListaItems(listaSubAreaCiencia));
	}

	public void cargarLineasInvestigacion() {
		List listaLineas = servicioGeneral.obtenerObjetos("select l from LineaInvestigacion l ");
		if (!esListaVacia(listaLineas)) {
			listaLineasInvestigacionItems = new SelectItem[listaLineas.size()];
			for (int i = 0; i < listaLineas.size(); i++) {
				LineaInvestigacion li = (LineaInvestigacion) listaLineas.get(i);
				listaLineasInvestigacionItems[i] = new SelectItem(li.getId(), li.getNombre());
			}
		}
		lineaInvestigacion = "";
	}

	public void agregarLineaInvestigacion() {
		if (esCadenaVacia(lineaInvestigacion)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar un línea de investigación.", ""));
			return;
		} else {
			if (!esListaVacia(listaLineasInvestigacionEvaluador)) {
				for (int a = 0; a < listaLineasInvestigacionEvaluador.size(); a++) {
					if (listaLineasInvestigacionEvaluador.get(a).getId().toString().equals(lineaInvestigacion)) {
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"La línea de investigación seleccionada ya existe en la lista.", ""));
						return;
					}
				}
			}

			LineaInvestigacion evaluadorLinea = servicioGeneral.obtenerObjetos(LineaInvestigacion.class,
					"from LineaInvestigacion l where l.id=" + lineaInvestigacion).get(0);
			listaLineasInvestigacionEvaluador.add(evaluadorLinea);
		}
	}

	public void eliminarLinea() {
		listaLineasInvestigacionEvaluador.remove(lineaInvestigacionSeleccionada);
	}

	public void cargarProyectos() {

		mensajeBusqueProyecto2 = "";

		if (convocatoriaActual != null && convocatoriaActual.getId() != null) {
			Modalidad mod = servicioModalidad.obtenerModalidad(Long.parseLong(convocatoria));
			Boolean elegibles = true;
			if (convocatoriaActual.getPadre() != null && convocatoriaActual.getPadre().getEsPermanente() != null
					&& convocatoriaActual.getPadre().getEsPermanente().equals("Y"))
				elegibles = false;
			listaProyectos = servicioProyecto.obtenerProyectosCoordinador(mod, personaActual.getId().getDocumento(),
					personaActual.getId().getTipoDocumento(), elegibles);

			listaProyectosclone = new ArrayList(listaProyectos);
		} else {
			listaProyectos.clear();
		}
		if (listaProyectos.size() != 0) {
			coordinadorTieneProyectos = true;
			proyectoItem = new SelectItem[listaProyectos.size()];
			for (int i = 0; i < listaProyectos.size(); i++) {
				Proyecto p = (Proyecto) listaProyectos.get(i);
				String nombre = p.getId() + " - " + p.getNombre();
				if (nombre.length() > 200) {
					nombre = nombre.substring(0, 200) + "...";
				}
				proyectoItem[i] = new SelectItem(p.getId().toString(), nombre);
			}
			proyectoId = ((Proyecto) listaProyectos.get(0)).getId().toString();
			proyectoActual = (Proyecto) listaProyectos.get(0);
			List listaAreas = servicioProyecto.obtenerAreasTematicas(proyectoActual);
			areasExperticiaItem = new SelectItem[listaAreas.size() + 1];
			areasExperticiaItem[0] = new SelectItem("", "");
			for (int i = 1; i < listaAreas.size() + 1; i++) {
				ClasificacionConocimiento clasificacion = (ClasificacionConocimiento) listaAreas.get(i - 1);
				areasExperticiaItem[i] = new SelectItem(clasificacion.getId(), clasificacion.getNombre());
			}
			if (proyectoActual != null) {

				listaEvaluadorCorreo = servicioPersona.obtenerEvaluadoresCorreoProyecto(proyectoActual);
				List lEva;
				Set eva;
				Iterator iEva;
				ProyectoEvaluador pe;
				eva = new HashSet();
				lEva = servicioPersona.obtenerlistaProyectosEvaluador(proyectoActual);
				iEva = lEva.iterator();
				while (iEva.hasNext()) {
					pe = (ProyectoEvaluador) iEva.next();
					eva.add(pe);
				}
				proyectoActual.setEvaluadoresProyecto(eva);

				this.mostrado = true;

			}

		} else {
			listaProyectos.clear();
			coordinadorTieneProyectos = false;
			mensajeBusqueProyecto2 = "No se han encontrado proyectos asociados que se encuentren en estado elegible o que pertenezcan a una convocatoria permanente.";
		}
		panelRender[3] = false;
		panelRender[12] = false;
		cargarTiposDocumento();
	}

	private Modalidad buscarModalidadxId(String idModalidad) {
		Iterator it = listaConvocatorias.iterator();
		Modalidad m = null;
		boolean modalidadEncontrada = false;
		while (it.hasNext() && !modalidadEncontrada) {
			m = (Modalidad) it.next();
			if (m.getId().longValue() == Long.parseLong(idModalidad)) {
				modalidadEncontrada = true;
			}
		}
		return m;
	}

	private ConvocatoriaPadre buscarConvocatoriaPadrexId(String idModalidad) {
		Iterator it = this.listaConvocatoriasPadre.iterator();
		ConvocatoriaPadre m = null;
		boolean modalidadEncontrada = false;
		while (it.hasNext() && !modalidadEncontrada) {
			m = (ConvocatoriaPadre) it.next();
			if (m.getId().longValue() == Long.parseLong(idModalidad)) {
				modalidadEncontrada = true;
			}
		}
		return m;
	}

	public void cambiarConvocatoriaPadre(ValueChangeEvent event) {

		if (event.getNewValue() != null) {
			Long evento = Long.parseLong((String) event.getNewValue());

			List convPadre = servicioGeneral
					.obtenerObjetos("select cp from ConvocatoriaPadre cp" + " where cp.id = '" + evento + "'");
			ConvocatoriaPadre cp = (ConvocatoriaPadre) convPadre.get(0);

			convocatoriaPadre = cp.getTitulo();
			listaConvocatorias.clear();

			listaConvocatorias = servicioModalidad.obtenerConvocatoriasxPadre(cp);
			String documento = this.personaActual.getId().getDocumento();
			String tipoDocumento = this.personaActual.getId().getTipoDocumento();

			convocatoriaItem = new SelectItem[listaConvocatorias.size()];
			mensajeConvocatoria = "";

			try {
				for (int i = 0; i < listaConvocatorias.size(); i++) {
					Convocatoria con = (Convocatoria) listaConvocatorias.get(i);
					String nombre = con.getTitulo();
					if (con.getTitulo().length() > 140) {
						nombre = con.getTitulo().substring(0, 140) + "...";
					}
					convocatoriaItem[i] = new SelectItem(con.getId().toString(), nombre);
				}
				nombreModalidad = ((Convocatoria) listaConvocatorias.get(0)).getTitulo();
				convocatoriaActual = ((Convocatoria) listaConvocatorias.get(0));
				fechaFinalEvaluacion = convocatoriaActual.getFechaFinalEval();
				if (convocatoriaActual != null) {
					ConvocatoriaPadre padre = convocatoriaActual.getPadre();
					nombrePadre = padre.getTitulo();
				}
			} catch (IndexOutOfBoundsException ex) {
				mensajeConvocatoria = "La convocatoria " + convocatoriaPadre + " no posee modalidades asociadas";
			}

		}
	}

	public void cambiarModalidad(ValueChangeEvent event) {
		try {
			convocatoriaActual = ((Convocatoria) (buscarModalidadxId((String) event.getNewValue())));
			nombreModalidad = convocatoriaActual.getTitulo();
			fechaFinalEvaluacion = convocatoriaActual.getFechaFinalEval();
			if (convocatoriaActual != null) {
				ConvocatoriaPadre padre = convocatoriaActual.getPadre();
				nombrePadre = padre.getTitulo();
			}
		} catch (Exception ex) {
			mensajeConvocatoria = "La convocatoria " + nombrePadre + " no posee Modalidades Asociadas";
		}
	}

	public void cambiarModalidad() {
		try {
			nombreModalidad = ((Convocatoria) buscarModalidadxId(convocatoria)).getTitulo();
			nombreConvocatoria = buscarConvocatoriaPadrexId(convocatoriaPadre).getTitulo();
			fechaFinalEvaluacion = convocatoriaActual.getFechaFinalEval();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
	}

	public void cambiarProyecto(ValueChangeEvent event) {

		panelRender[12] = false;
		panelRender[4] = false;
		panelRender[5] = false;
		panelRender[14] = false;
		panelRender[15] = false;
		panelRender[10] = false;
		panelRender[11] = false;
		panelRender[8] = false;
		panelRender[9] = false;
		panelRender[17] = false;
		panelRender[18] = false;
		borrarMensajesError();

		proyectoActual = buscarProyecto((String) event.getNewValue());

		for (int i = 0; i < this.listaProyectosclone.size(); i++) {
			Proyecto temp = (Proyecto) listaProyectosclone.get(i);
			if (proyectoActual.getId().longValue() == temp.getId().longValue()) {
				proyectoActual = temp;
			}
		}

		List listaAreas = servicioProyecto.obtenerAreasTematicas(proyectoActual);
		areasExperticiaItem = new SelectItem[listaAreas.size() + 1];
		areasExperticiaItem[0] = new SelectItem("", "");
		for (int i = 1; i < listaAreas.size() + 1; i++) {
			ClasificacionConocimiento clasificacion = (ClasificacionConocimiento) listaAreas.get(i - 1);
			areasExperticiaItem[i] = new SelectItem(clasificacion.getId(), clasificacion.getNombre());
		}
		panelRender[12] = false;

	}

	private Proyecto buscarProyecto(String id) {
		// BUSCA UNA DEPENDENCIA DE ACUERDO A SU ID
		Proyecto p = new Proyecto();
		int i = 0;
		while (i < listaProyectos.size()) {
			p = (Proyecto) listaProyectos.get(i);
			if (id.equals(p.getId().toString()))
				break;
			i = i + 1;
		}
		return p;
	}

	public void asociarEvaluador3() {
		borrarMensajesError();
		panelRender[10] = false;
		panelRender[11] = false;
		String idPos3 = this.posibleEvaluadorSel3;

		if (idPos3 != null) {
			List posibles3 = servicioGeneral
					.obtenerObjetos("select pe from Investigador pe" + " where pe.id.documento  ='" + idPos3 + "' ");
			posevalu3 = (Investigador) posibles3.get(0);

			IdPersona id = new IdPersona();
			Investigador pev = posevalu3;
			id.setDocumento(pev.getId().getDocumento());
			id.setTipoDocumento(pev.getId().getTipoDocumento());

			Persona personaEval = servicioPersona.obtenerPersona(id);

			if (personaEval == null) {
				mensajeError3 = "La persona no existe.";
			} else if (servicioPersona.validarInvestigadorModalidad(proyectoActual.getModalidad().getId(), id)) {
				mensajeError3 = "La persona participa como investigador en esta misma convocatoria.";
			} else {

				String consulta = "Select e from ProyectoEvaluador e where e.evaluador.id.documento ='"
						+ personaEval.getId().getDocumento() + "' and e.evaluador.id.tipoDocumento = '"
						+ personaEval.getId().getTipoDocumento() + "' and " + "e.proyecto = '" + proyectoActual.getId()
						+ "' ";
				List listaEvaluadores = servicioGeneral.obtenerObjetos(consulta);

				if (listaEvaluadores.size() > 0) {
					mensajeError3 = "El evaluador ya se encuentra asociado al proyecto";
				} else {

					if (personaEval instanceof Investigador) {
						Convocatoria conv = servicioModalidad
								.obtenerConvocatoria(proyectoActual.getModalidad().getId());
						ConvocatoriaPadre cp = conv.getPadre();
						Long numeroProyectos = servicioPersona.obtenerNumeroProyectosEvaluador(personaEval, cp);
						if (personaEval instanceof InvestigadorExterno) {
							ProyectoEvaluador pe = new ProyectoEvaluador();
							pe.setEvaluador((Investigador) personaEval);
							if (mensajeError3.equals("")) {
								proyectoActual.adicionarEvaluadorProyecto(pe);
								servicioGeneral.guardarObjeto(pe);
								convertirEnEvaluador(personaEval);
							}
						}
						if (personaEval instanceof InvestigadorInterno) {
							ProyectoEvaluador pe = new ProyectoEvaluador();
							pe.setEvaluador((Investigador) personaEval);
							if (mensajeError3.equals("")) {
								proyectoActual.adicionarEvaluadorProyecto(pe);
								servicioGeneral.guardarObjeto(pe);
								convertirEnEvaluador(personaEval);
							}

						} else {

						}
					} else {
						mensajeError3 = "La persona no existe como investigador";
					}
				}
			}
		} else {

		}
	}

	public void asociarEvaluadorSeleccionado() {
		List posibles3 = servicioGeneral.obtenerObjetos(
				"select pe from Investigador pe" + " where pe.id.documento  ='" + posibleEvaluadorSel3 + "'");
		posevalu3 = (Investigador) posibles3.get(0);
		asociarEvaluador(posevalu3);
	}

	public void asociarEvaluadorBuscado() {
		List posibles3 = servicioGeneral.obtenerObjetos("select pe from Investigador pe" + " where pe.id.documento  ='"
				+ investigadorEvaluador.getId().getDocumento() + "' and pe.id.tipoDocumento ='"
				+ investigadorEvaluador.getId().getTipoDocumento() + "'");
		if(posibles3!=null && posibles3.size()>0) {
		posevalu3 = (Investigador) posibles3.get(0);
		asociarEvaluador(posevalu3);
		}else {
			mensajeError("Debe actualizar primero los datos del evaluador");
			mensajeEvaluador = "";
		}
	}

	public void asociarEvaluador(Investigador evaluadorAsociado) {

		borrarMensajesError();
		panelRender[14] = false;
		panelRender[15] = false;

		IdPersona id = new IdPersona();
		id.setDocumento(evaluadorAsociado.getId().getDocumento());
		id.setTipoDocumento(evaluadorAsociado.getId().getTipoDocumento());

		Persona personaEval = servicioPersona.obtenerPersona(id);

		if (personaEval == null) {
			mensajeError4 = "La persona no existe.";
		} else if (servicioPersona.validarInvestigadorModalidad(proyectoActual.getModalidad().getId(), id)) {
			mensajeError4 = "La persona participa como investigador en esta misma convocatoria.";
		} else {

			List listaEvaluadores = servicioGeneral
					.obtenerObjetos("Select e from ProyectoEvaluador e where e.evaluador.id.documento ='"
							+ personaEval.getId().getDocumento() + "' and e.evaluador.id.tipoDocumento = '"
							+ personaEval.getId().getTipoDocumento() + "' and e.proyecto='" + proyectoActual.getId()
							+ "'");

			if (listaEvaluadores.size() > 0) {
				mensajeError4 = "El evaluador ya se encuentra asociado al proyecto";
			} else {

				if (personaEval instanceof Investigador) {
					Convocatoria conv = servicioModalidad.obtenerConvocatoria(proyectoActual.getModalidad().getId());
					ConvocatoriaPadre cp = conv.getPadre();
					Long numeroProyectos = servicioPersona.obtenerNumeroProyectosEvaluador(personaEval, cp);
					if (personaEval instanceof InvestigadorExterno) {
						ProyectoEvaluador pe = new ProyectoEvaluador();
						pe.setEvaluador((Investigador) personaEval);
						if (mensajeError4.equals("")) {
							proyectoActual.adicionarEvaluadorProyecto(pe);
							servicioGeneral.guardarObjeto(pe);
							//((Investigador) personaEval).setEvaluador("S");
							convertirEnEvaluador(personaEval);
						}
					}
					if (personaEval instanceof InvestigadorInterno) {
						ProyectoEvaluador pe = new ProyectoEvaluador();
						pe.setEvaluador((Investigador) personaEval);
						if (mensajeError4.equals("")) {
							proyectoActual.adicionarEvaluadorProyecto(pe);
							servicioGeneral.guardarObjeto(pe);
							convertirEnEvaluador(personaEval);
						}

					} else {

					}
				} else {
					mensajeError4 = "La persona no existe como investigador";
				}
			}
		}
	}

	public void guardarFechaEvaluacion() {

		for (int i = 0; i < proyectoActual.getListaEvaluadoresProyecto().size(); i++) {
			ProyectoEvaluador eval = new ProyectoEvaluador();
			eval = (ProyectoEvaluador) proyectoActual.getListaEvaluadoresProyecto().get(i);
			servicioGeneral.guardarObjeto(eval);
			mensajeInfo("Fechas y comentarios guardados para los evaluadores");
		}
	}
	
	public Date getPrimerDiaSiguienteAno() {
		Calendar calendario = Calendar.getInstance();
		calendario.add(Calendar.YEAR, 1);
        
        // Configura el mes a enero (0 = enero)
        calendario.set(Calendar.MONTH, Calendar.JANUARY);
        
        // Configura el día del mes al 1
        calendario.set(Calendar.DAY_OF_MONTH, 1);
        
        // Obtén la fecha en formato Date
        Date fecha = calendario.getTime();
        
        return fecha;
	}

	/**
	 * @param personaEval
	 */
	private void convertirEnEvaluador(Persona personaEval) {
		if (personaEval != null) {
			Persona personaNueva = servicioPersona.obtenerPersonaRoles(personaEval.getId());
			if ((personaNueva == null || personaNueva.getId()==null) || (!personaNueva.esEvaluador())) {
				personaEval.adicionarRolEvaluador();
				servicioGeneral.guardarObjeto(personaEval);
				Rol rolE = new Rol();
				rolE.setId(Rol.EVALUADOR);
				servicioPersona.actualizarFechaVencimientoRol(personaEval.getId(),rolE,getPrimerDiaSiguienteAno() );
				if (personaEval instanceof InvestigadorExterno) {
					// enviar correo cuando es externo para saber q se creo uno
					// nuevo
					Correo correo = new Correo();
					correo.adicionarDireccion(Correo.CORREO_HERMES);
					correo.setAsunto("Nuevo evaluador");
					correo.setCuerpo(personaEval.getId().getTipoDocumento() + " " + personaEval.getId().getDocumento());
					correo.setOrigen(personaEval.getEmail());
					servicioCorreo.enviarCorreo(correo);
				}
			}
		}
	}

	public void cambiarCorreo3() {
		borrarMensajesError();
		String idCorreo3 = correoId3;
		if (correoId3 != null) {
			CorreoPlantilla a = new CorreoPlantilla();
			correoActual3 = (CorreoPlantilla) servicioGeneral.obtenerObjeto(a, Long.valueOf(idCorreo3));
			String correo = correoActual3.getCuerpo().replaceAll("<<FECHA>>", Fecha.fechaActual());
			correo = correo.replaceAll("<<TITULO>>",
					proyectoActual == null || proyectoActual.getNombre() == null ? "<<TITULO>>"
							: proyectoActual.getNombre());
			setCuerpoCorreo3(correo);
			correoActualCuerpo3 = correo;
			reemplazarCuerpo3();
		}
	}

	public void cambiarCorreo4() {
		borrarMensajesError();
		String idCorreo4 = correoId4;

		CorreoPlantilla a = new CorreoPlantilla();
		correoActual4 = (CorreoPlantilla) servicioGeneral.obtenerObjeto(a, Long.valueOf(idCorreo4));
		String correo = correoActual4.getCuerpo().replaceAll("<<FECHA>>", Fecha.fechaActual());
		correo = correo.replaceAll("<<TITULO>>",
				proyectoActual == null || proyectoActual.getNombre() == null ? "<<TITULO>>"
						: proyectoActual.getNombre());
		setCuerpoCorreo4(correo);
		correoActualCuerpo4 = correo;
		reemplazarCuerpo4();
	}

	private void borrarMensajesError() {
		mensajeCorreo = "";
		mensajeCorreo2 = "";
		mensajeCorreo3 = "";
		mensajeCorreo4 = "";
		mensajeError2 = "";
		mensajeError3 = "";
		mensajeError4 = "";
	}

	public void reemplazarCuerpo3() {
		String correo = correoActualCuerpo3;
		String evalTpDocumento;
		String evalDocumento;
		correo = correoActualCuerpo3.replaceAll("<<TITULO>>",
				proyectoActual == null || proyectoActual.getNombre() == null ? "<<TITULO>>"
						: proyectoActual.getNombre());

		correo = correo.replaceAll("<<FECHA>>", Fecha.fechaActual());

		if (proyectoActual.getModalidad() != null) {
			List<Convocatoria> convocatorias = servicioGeneral.obtenerObjetoXID("Convocatoria",
					proyectoActual.getModalidad().getId().toString());
			Convocatoria conv = new Convocatoria();
			if (convocatorias.size() > 0) {
				conv = convocatorias.get(0);
				ConvocatoriaPadre convPadre = servicioModalidad.obtenerConvocatoriaPadre(conv.getPadre().getId());
				correo = correo.replaceAll("<<CONVOCATORIA>>", convPadre.getTitulo());
			}

			if (conv.getSede() != null) {
				String ciudad = conv.getSede().getCiudad();
				correo = correo.replaceAll("<<CIUDAD>>", ciudad);
			}

			if (conv.getDependencia() != null) {
				List<Parametro> parametros = servicioGeneral.obtenerObjetos(Parametro.class,
						"from Parametro p where p.nombre = '" + Parametro.SEG_FIRMA + "' and p.profesion = '"
								+ conv.getDependencia().getId() + "'");

				if (parametros.size() > 0) {
					Parametro vicedecano = parametros.get(0);
					correo = correo.replaceAll("<<VICEDECANO>>", vicedecano.getValor());
					Dependencia facVicedecano = servicioDependencia.obtenerDependencia(vicedecano.getProfesion());
					correo = correo.replaceAll("<<FACULTAD_VICEDECANO>>", facVicedecano.getNombre());
				}
			}
		}

		Persona coord = servicioProyecto.obtenerCoordinadorEvaluacionProyecto(proyectoActual.getId());
		if (coord != null) {
			String nombreCoordinador = coord.getNombre1() + " " + coord.getNombre2() + " " + coord.getApellido1() + " "
					+ coord.getApellido2();
			correo = correo.replaceAll("<<COORDINADOR>>", nombreCoordinador);
			correo = correo.replaceAll("<<COORD_CORREO>>", coord.getEmail());
		} else {
			correo = correo.replaceAll("<<COORDINADOR>>", "");
			correo = correo.replaceAll("<<COORD_CORREO>>", "");
		}

		IdPersona id = new IdPersona(posevaluador3.getId().getDocumento(), posevaluador3.getId().getTipoDocumento());
		Persona p = servicioPersona.obtenerPersona(id);
		if (p != null && p.getId() != null && p.getId().getDocumento() != null) {
			correo = correo.replaceAll("<<EVALUADOR>>",
					(p.getApellido1() == null ? "" : p.getApellido1()) + (p.getApellido1() == null ? "" : " ")
							+ (p.getApellido2() == null ? "" : p.getApellido2()) + (p.getApellido2() == null ? "" : " ")
							+ (p.getNombre1() == null ? "" : p.getNombre1()) + (p.getNombre1() == null ? "" : " ")
							+ (p.getNombre2() == null ? "" : p.getNombre2()));
		}
		InvestigadorInterno ii = null;
		InvestigadorExterno ie = null;
		if (p != null && p.getId() != null && p.getId().getDocumento() != null
				&& p.getId().getTipoDocumento() != null) {
			ii = servicioPersona.obtenerInvestigadorInternoDependenciaYFacultad(p.getId());
			ie = servicioPersona.obtenerInvestigadorExterno(p.getId());

			evalTpDocumento = p.getId().getTipoDocumento();
			evalDocumento = p.getId().getDocumento();

			correo = correo.replaceAll("<<TIPO_DOCUMENTO>>", evalTpDocumento);
			correo = correo.replaceAll("<<DOCUMENTO>>", evalDocumento);
		}
		if (ii != null) {

			Dependencia dependenciaInterno = ii.getDependencia();
			Dependencia departamento = servicioDependencia.obtenerDependencia(
					dependenciaInterno == null || dependenciaInterno.getDepartamento() == null ? "sin departamento"
							: dependenciaInterno.getDepartamento());
			String nombreDependencia = (departamento == null ? "sin departamento" : departamento.getNombre());
			correo = correo.replaceAll("<<DEPARTAMENTO>>", nombreDependencia);
			correo = correo.replaceAll("<<FACULTAD>>",
					dependenciaInterno == null || dependenciaInterno.getFacultad() == null ? "sin facultad"
							: dependenciaInterno.getFacultad().getNombre());
		}

		if (ie != null) {
			if (ie.getContrasena() == null) {
				ie.setContrasena(new Long(servicioPersona.generarClaveExterno()));
			}
			correo = correo.replaceAll("<<USUARIO>>", servicioPersona.login(ie));
			correo = correo.replaceAll("<<CLAVE>>", ie.getContrasena().toString());
			correo = correo.replaceAll("<<INSTITUCION>>",((posibleEvaluadorExterno.getInstitucionLabora() == null) ? "Sin Institución":posibleEvaluadorExterno.getInstitucionLabora().getDescripcion()));

			servicioGeneral.guardarObjeto(ie);
		}
		cuerpoCorreo3 = correo;
		correoActualCuerpo3 = correo;
	}

	public void reemplazarCuerpo4() {
		String correo = correoActualCuerpo4;
		String evalTpDocumento;
		String evalDocumento;
		correo = correoActualCuerpo4.replaceAll("<<TITULO>>",
				proyectoActual == null || proyectoActual.getNombre() == null ? "<<TITULO>>"
						: proyectoActual.getNombre());

		correo = correo.replaceAll("<<FECHA>>", Fecha.fechaActual());

		if (proyectoActual.getModalidad() != null) {
			List<Convocatoria> convocatorias = servicioGeneral.obtenerObjetoXID("Convocatoria",
					proyectoActual.getModalidad().getId().toString());
			Convocatoria conv = new Convocatoria();
			if (convocatorias.size() > 0) {
				conv = convocatorias.get(0);
				ConvocatoriaPadre convPadre = servicioModalidad.obtenerConvocatoriaPadre(conv.getPadre().getId());
				correo = correo.replaceAll("<<CONVOCATORIA>>", convPadre.getTitulo());
			}

			if (conv.getSede() != null) {
				String ciudad = conv.getSede().getCiudad();
				correo = correo.replaceAll("<<CIUDAD>>", ciudad);
			}

			if (conv.getDependencia() != null) {
				List<Parametro> parametros = servicioGeneral.obtenerObjetos(Parametro.class,
						"from Parametro p where p.nombre = '" + Parametro.SEG_FIRMA + "' and p.profesion = '"
								+ conv.getDependencia().getId() + "'");

				if (parametros.size() > 0) {
					Parametro vicedecano = parametros.get(0);
					correo = correo.replaceAll("<<VICEDECANO>>", vicedecano.getValor());
					Dependencia facVicedecano = servicioDependencia.obtenerDependencia(vicedecano.getProfesion());
					correo = correo.replaceAll("<<FACULTAD_VICEDECANO>>", facVicedecano.getNombre());
				}
			}
		}

		if (proyectoActual.getModalidad() != null) {
			List<Convocatoria> convocatorias = servicioGeneral.obtenerObjetoXID("Convocatoria",
					proyectoActual.getModalidad().getId().toString());
			Convocatoria conv = new Convocatoria();
			if (convocatorias.size() > 0) {
				conv = convocatorias.get(0);
				ConvocatoriaPadre convPadre = servicioModalidad.obtenerConvocatoriaPadre(conv.getPadre().getId());
				correo = correo.replaceAll("<<CONVOCATORIA>>", convPadre.getTitulo());
			}

			if (conv.getSede() != null) {
				String ciudad = conv.getSede().getCiudad();
				correo = correo.replaceAll("<<CIUDAD>>", ciudad);
			}

			if (conv.getDependencia() != null) {
				List<Parametro> parametros = servicioGeneral.obtenerObjetos(Parametro.class,
						"from Parametro p where p.nombre = '" + Parametro.SEG_FIRMA + "' and p.profesion = '"
								+ conv.getDependencia().getId() + "'");

				if (parametros.size() > 0) {
					Parametro vicedecano = parametros.get(0);
					correo = correo.replaceAll("<<VICEDECANO>>", vicedecano.getValor());
					Dependencia facVicedecano = servicioDependencia.obtenerDependencia(vicedecano.getProfesion());
					correo = correo.replaceAll("<<FACULTAD_VICEDECANO>>", facVicedecano.getNombre());
				}
			}
		}

		Persona coord = servicioProyecto.obtenerCoordinadorEvaluacionProyecto(proyectoActual.getId());
		if (coord != null) {
			String nombreCoordinador = coord.getNombre1() + " " + coord.getNombre2() + " " + coord.getApellido1() + " "
					+ coord.getApellido2();
			correo = correo.replaceAll("<<COORDINADOR>>", nombreCoordinador);
			correo = correo.replaceAll("<<COORD_CORREO>>", coord.getEmail());
		} else {
			correo = correo.replaceAll("<<COORDINADOR>>", "");
			correo = correo.replaceAll("<<COORD_CORREO>>", "");
		}

		try {
			String documentoPosibleEvaluador = "";
			if (panelRender[7]) {
				documentoPosibleEvaluador = posibleEvaluadorSel3;
			} else {
				documentoPosibleEvaluador = codigoEvaluador;
			}
			
			List investigadores = (ArrayList<Investigador>) servicioGeneral.obtenerObjetos(
					"select pe from Investigador pe" + " where pe.id.documento  ='" + documentoPosibleEvaluador + "'");
			investigadorEvaluador = (Investigador) investigadores.get(0);

		}catch (Exception e){
			e.printStackTrace();
		}
		
		IdPersona id = new IdPersona(investigadorEvaluador.getId().getDocumento(),
				investigadorEvaluador.getId().getTipoDocumento());
		Persona p = servicioPersona.obtenerPersona(id);
		if (p != null && p.getId() != null && p.getId().getDocumento() != null) {
			correo = correo.replaceAll("<<EVALUADOR>>",
					(p.getApellido1() == null ? "" : p.getApellido1()) + (p.getApellido1() == null ? "" : " ")
							+ (p.getApellido2() == null ? "" : p.getApellido2()) + (p.getApellido2() == null ? "" : " ")
							+ (p.getNombre1() == null ? "" : p.getNombre1()) + (p.getNombre1() == null ? "" : " ")
							+ (p.getNombre2() == null ? "" : p.getNombre2()));
		}
		InvestigadorInterno ii = null;
		InvestigadorExterno ie = null;
		if (p != null && p.getId() != null && p.getId().getDocumento() != null
				&& p.getId().getTipoDocumento() != null) {
			ii = servicioPersona.obtenerInvestigadorInternoDependenciaYFacultad(p.getId());
			ie = servicioPersona.obtenerInvestigadorExterno(p.getId());

			evalTpDocumento = p.getId().getTipoDocumento();
			evalDocumento = p.getId().getDocumento();

			correo = correo.replaceAll("<<TIPO_DOCUMENTO>>", evalTpDocumento);
			correo = correo.replaceAll("<<DOCUMENTO>>", evalDocumento);
		}
		if (ii != null) {

			Dependencia dependenciaInterno = ii.getDependencia();
			Dependencia departamento = servicioDependencia.obtenerDependencia(
					dependenciaInterno == null || dependenciaInterno.getDepartamento() == null ? "sin departamento"
							: dependenciaInterno.getDepartamento());
			String nombreDependencia = (departamento == null ? "sin departamento" : departamento.getNombre());
			correo = correo.replaceAll("<<DEPARTAMENTO>>", nombreDependencia);
			correo = correo.replaceAll("<<FACULTAD>>",
					dependenciaInterno == null || dependenciaInterno.getFacultad() == null ? "sin facultad"
							: dependenciaInterno.getFacultad().getNombre());
			correo = correo.replaceAll("<<SEDE>>", dependenciaInterno.getSede().getNombre());
		}
		if (ie != null) {
			if (ie.getContrasena() == null) {
				ie.setContrasena(new Long(servicioPersona.generarClaveExterno()));
			}
			correo = correo.replaceAll("<<USUARIO>>", servicioPersona.login(ie));
			correo = correo.replaceAll("<<CLAVE>>", ie.getContrasena().toString());
			if (posibleEvaluadorExterno == null) {
				if (panelRender[7]) {
					verInfoEvaluadorSeleccionado();
				} else {
					verInfoEvaluador();
				}
			}
			correo = correo.replaceAll("<<INSTITUCION>>",((posibleEvaluadorExterno.getInstitucionLabora() == null) ? "Sin Institución":posibleEvaluadorExterno.getInstitucionLabora().getDescripcion()));
			servicioGeneral.guardarObjeto(ie);
		}
		cuerpoCorreo4 = correo;
		correoActualCuerpo4 = correo;
	}

	public String editarCorreo3() {
		borrarMensajesError();
		setDetalle3(!isDetalle3());
		setCorreoActualCuerpo3(
				cuerpoCorreo3.replaceAll("<<EVALUADOR>>", nombreDestino == null ? "<<EVALUADOR>>" : nombreDestino));
		reemplazarCuerpo3();
		return "";
	}

	public String editarCorreo4() {
		borrarMensajesError();
		setDetalle4(!isDetalle4());
		setCorreoActualCuerpo4(
				cuerpoCorreo4.replaceAll("<<EVALUADOR>>", nombreDestino == null ? "<<EVALUADOR>>" : nombreDestino));
		reemplazarCuerpo4();
		return "";
	}

	public void enviarCorreo3() {
		Investigador pev = this.posevalu3;
		mensajeCorreo3 = "";
		Correo correo = new Correo();
		correo.setOrigen(Correo.CORREO_HERMES);
		correo.adicionarDireccion(pev.getEmail());
		correo.adicionarDireccion(personaActual.getEmail());

		correo.setAsunto(correoActual3.getAsunto());
		servicioCorreo.aplicarPlantillaCorreoAdjunto(correo, correoActual3,
				System.getProperty("java.io.tmpdir") + File.separator + "adjunto.zip");
		correo.setCuerpo(correoActualCuerpo3);
		correo.setCuerpo(correoActualCuerpo3.replaceAll("<<codigoPr>>",
				getProyectoActual() == null || getProyectoActual().getId() == null ? "<<codigoPr>>"
						: getProyectoActual().getId().toString()));

		IdPersona id = new IdPersona();
		id.setDocumento(pev.getId().getDocumento());
		id.setTipoDocumento(pev.getId().getTipoDocumento());
		InvestigadorExterno ie = servicioPersona.obtenerInvestigadorExterno(id);

		if (ie != null) {
			if (ie.getContrasena() == null) {
				ie.setContrasena(new Long(servicioPersona.generarClaveExterno()));
			}
			correo.setCuerpo(correo.getCuerpo().replaceAll("<<USUARIO>>", servicioPersona.login(ie)));
			correo.setCuerpo(correo.getCuerpo().replaceAll("<<CLAVE>>", ie.getContrasena().toString()));
			servicioGeneral.guardarObjeto(ie);
		}

		FacesContext facesContext;
		facesContext = javax.faces.context.FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
		String r = request.getRealPath("plantillaCorreo");
		if (correoActual3.getPlantilla() != null) {
			try {

				FileReader fr = new FileReader(r + File.separator + (correoActual3.getPlantilla()));
				BufferedReader br = new BufferedReader(fr);
				String l = br.readLine();
				StringBuffer sb = new StringBuffer();
				while (l != null) {
					sb.append(l);
					l = br.readLine();
				}

				l = sb.toString();
				l = l.replaceAll("<<contenido>>", correo.getCuerpo());
				if (ie != null) {
					l = l.replaceAll("" + System.getProperty("line.separator"),
							"\\par" + System.getProperty("line.separator"));
					l = l.replaceAll("<<CLAVE>>", ie.getContrasena().toString());
					l = l.replaceAll("<<USUARIO>>", servicioPersona.login(ie));

				}
				l = l.replaceAll("<<TITULO>>",
						proyectoActual == null || proyectoActual.getNombre() == null ? "<<TITULO>>"
								: proyectoActual.getNombre());
				l = l.replaceAll("<<EVALUADOR>>", pev.getNombre1());
				l = l.replaceAll("<<FECHA>>", Fecha.fechaActual());
				Dependencia departamento = servicioDependencia.obtenerDependencia(
						pev.getDependencia() == null || pev.getDependencia().getDepartamento() == null
								? "sin departamento"
								: pev.getDependencia().getDepartamento());
				String nombreDependencia = departamento == null ? "sin departamento" : departamento.getNombre();
				l = l.replaceAll("<<DEPARTAMENTO>>", nombreDependencia);
				l = l.replaceAll("<<FACULTAD>>",
						pev.getDependencia() == null || pev.getDependencia().getFacultad() == null ? "sin facultad "
								: pev.getDependencia().getFacultad().getNombre());

				br.close();
				fr.close();

				File a = File.createTempFile("adjunto", "rtf", new File(System.getProperty("java.io.tmpdir")));
				FileWriter fw = new FileWriter(a);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(l);
				bw.close();
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (pev.getId().getDocumento() != null) {

			if (servicioCorreo.enviarCorreo(correo)) {
				mensajeCorreo3 = "Su correo ha sido enviado con exito al posible evaluador con copia a "
						+ personaActual.getEmail();
				contactado = new String[4];
				contactado[0] = "S";
				contactado[1] = "";
				contactado[2] = pev.getId().getDocumento().toString();
				contactado[3] = pev.getNombre1();
				List contactos = servicioGeneral.obtenerObjetos("select 1 from EvaluadorCorreo ec "
						+ " where  ec.investigador.id.documento='" + pev.getId().getDocumento()
						+ "' and ec.investigador.id.tipoDocumento='" + pev.getId().getTipoDocumento()
						+ "' and ec.proyecto.id = " + proyectoActual.getId().intValue());
				if (contactos.size() <= 0) {
					servicioPersona.insertaEvaluadorCorreo(proyectoActual, tipoDocumento, contactado);
				}
			} else {
				mensajeCorreo3 = "No se pudo enviar el correo, por favor verifique la dirección electrónica";
			}
		} else {
			if (servicioCorreo.enviarCorreo(correo)) {
				mensajeCorreo3 = "Su correo ha sido enviado con exito al posible evaluador con copia a "
						+ personaActual.getEmail();
			} else {
				mensajeCorreo3 = "No se pudo enviar el correo, por favor verifique la dirección electrónica";
			}
		}
	}

	public void enviarCorreo4() {
		Investigador pev = investigadorEvaluador;
		mensajeCorreo4 = "";
		Correo correo = new Correo();
		correo.setOrigen(Correo.CORREO_HERMES);
		String dirCorreo = Correo.CORREO_HERMES;
		correo.adicionarDireccion(pev.getEmail());
		correo.adicionarDireccion(personaActual.getEmail());

		correo.setAsunto(correoActual4.getAsunto());
		servicioCorreo.aplicarPlantillaCorreoAdjunto(correo, correoActual4,
				System.getProperty("java.io.tmpdir") + File.separator + "adjunto.zip");
		correo.setCuerpo(correoActualCuerpo4);
		correo.setCuerpo(correoActualCuerpo4.replaceAll("<<codigoPr>>",
				this.proyectoActual == null || this.proyectoActual.getId() == null ? "<<codigoPr>>"
						: this.proyectoActual.getId().toString()));

		IdPersona id = new IdPersona();
		id.setDocumento(pev.getId().getDocumento());
		id.setTipoDocumento(pev.getId().getTipoDocumento());
		InvestigadorExterno ie = servicioPersona.obtenerInvestigadorExterno(id);

		if (ie != null) {
			if (ie.getContrasena() == null) {
				ie.setContrasena(new Long(servicioPersona.generarClaveExterno()));
			}
			correo.setCuerpo(correo.getCuerpo().replaceAll("<<USUARIO>>", servicioPersona.login(ie)));
			correo.setCuerpo(correo.getCuerpo().replaceAll("<<CLAVE>>", ie.getContrasena().toString()));
			servicioGeneral.guardarObjeto(ie);
		}

		FacesContext facesContext;
		facesContext = javax.faces.context.FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
		String r = request.getRealPath("plantillaCorreo");
		if (correoActual4.getPlantilla() != null) {
			try {

				FileReader fr = new FileReader(r + File.separator + (correoActual4.getPlantilla()));
				BufferedReader br = new BufferedReader(fr);
				String l = br.readLine();
				StringBuffer sb = new StringBuffer();
				while (l != null) {
					sb.append(l);
					l = br.readLine();
				}

				l = sb.toString();
				l = l.replaceAll("<<contenido>>", correo.getCuerpo());
				if (ie != null) {
					l = l.replaceAll("" + System.getProperty("line.separator"),
							"\\par" + System.getProperty("line.separator"));
					l = l.replaceAll("<<CLAVE>>", ie.getContrasena().toString());// ie.getId().getDocumento()+"h");
					l = l.replaceAll("<<USUARIO>>", servicioPersona.login(ie));// ie.getContrasena().toString());

				}
				l = l.replaceAll("<<TITULO>>",
						this.proyectoActual == null || this.proyectoActual.getNombre() == null ? "<<TITULO>>"
								: this.proyectoActual.getNombre());
				l = l.replaceAll("<<EVALUADOR>>", pev.getNombre1());
				l = l.replaceAll("<<FECHA>>", Fecha.fechaActual());
				Dependencia departamento = servicioDependencia.obtenerDependencia(
						pev.getDependencia() == null || pev.getDependencia().getDepartamento() == null
								? "sin departamento"
								: pev.getDependencia().getDepartamento());
				String nombreDependencia = departamento == null ? "sin departamento" : departamento.getNombre();
				l = l.replaceAll("<<DEPARTAMENTO>>", nombreDependencia);
				l = l.replaceAll("<<FACULTAD>>",
						pev.getDependencia() == null || pev.getDependencia().getFacultad() == null ? "sin facultad "
								: pev.getDependencia().getFacultad().getNombre());

				br.close();
				fr.close();

				File a = File.createTempFile("adjunto", "rtf", new File(System.getProperty("java.io.tmpdir")));
				FileWriter fw = new FileWriter(a);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(l);
				bw.close();
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (pev.getId().getDocumento() != null) {

			if (servicioCorreo.enviarCorreo(correo)) {
				mensajeCorreo4 = "Su correo ha sido enviado con exito al posible evaluador con copia a "
						+ personaActual.getEmail();
				contactado = new String[4];
				contactado[0] = "S";
				contactado[1] = "";
				contactado[2] = pev.getId().getDocumento().toString();
				contactado[3] = pev.getNombre1();
				List contactos = servicioGeneral.obtenerObjetos("select 1 from EvaluadorCorreo ec "
						+ " where  ec.investigador.id.documento='" + pev.getId().getDocumento()
						+ "' and ec.investigador.id.tipoDocumento='" + pev.getId().getTipoDocumento()
						+ "' and ec.proyecto.id = " + proyectoActual.getId().intValue());
				if (contactos.size() <= 0) {
					servicioPersona.insertaEvaluadorCorreo(proyectoActual, tipoDocumento, contactado);
				}
			} else {
				mensajeCorreo4 = "No se pudo enviar el correo, por favor verifique la dirección electrónica";
			}
		} else {
			if (servicioCorreo.enviarCorreo(correo)) {
				mensajeCorreo4 = "Su correo ha sido enviado con exito al posible evaluador con copia a"
						+ personaActual.getEmail();
			} else {
				mensajeCorreo4 = "No se pudo enviar el correo, por favor verifique la dirección electrónica";
			}
		}
	}

	public void cerrar2() {
		panelRender[9] = false;
	}

	public void cerrar() {
		panelRender[5] = false;
	}

	public void cargarProyectoXId() {

		mensajeEvaluador = "";
		mensajeCorreo = "";
		mostrarNuevoEvaluador = false;
		mensajeBusqueProyecto = "";

		if (idProyecto != null && !idProyecto.trim().equals("")) {

			boolean errorValorIngresado = false;
			Long id = 0L;
			try {
				id = Long.parseLong(idProyecto.trim());
			} catch (NumberFormatException nfe) {
				errorValorIngresado = true;
			}
			if (!errorValorIngresado) {

				proyectoActual = (Proyecto) servicioProyecto.obtenerProyectoAsesorEvaluacion(id, personaActual.getId());
				if (proyectoActual == null) {
					proyectoActual = (Proyecto) servicioProyecto.obtenerProyectoEditorialEvaluacion(id,
							personaActual.getId());
				}

				if (proyectoActual != null) {
					if (!proyectoActual.getEstadoProyecto().getId().equals("E"))
						proyectoActual = null;
				}
				listaProyectos = new ArrayList();

				if (proyectoActual != null) {
					listaEvaluadorCorreo = servicioPersona.obtenerEvaluadoresCorreoProyecto(proyectoActual);
					List lEva;
					Set eva;
					Iterator iEva;
					ProyectoEvaluador pe;
					eva = new HashSet();
					lEva = servicioPersona.obtenerlistaProyectosEvaluador(proyectoActual);
					iEva = lEva.iterator();
					while (iEva.hasNext()) {
						pe = (ProyectoEvaluador) iEva.next();
						eva.add(pe);
					}
					proyectoActual.setEvaluadoresProyecto(eva);

					this.mostrado = true;

					listaProyectos.add(proyectoActual);
					convocatoriaActual = servicioModalidad.obtenerConvocatoria(proyectoActual.getModalidad().getId());
					ConvocatoriaPadre cp = convocatoriaActual.getPadre();
					nombreModalidad = convocatoriaActual.getTitulo();
					fechaFinalEvaluacion = convocatoriaActual.getFechaFinalEval();

					nombrePadre = cp.getTitulo();
					convocatoriaPadre = cp.getId().toString();

					listaConvocatorias = servicioModalidad.obtenerConvocatoriasXPadreYCoordinador(cp.getId(),
							personaActual.getId());
					convocatoriaItem = new SelectItem[listaConvocatorias.size()];
					for (int i = 0; i < listaConvocatorias.size(); i++) {
						Convocatoria con = (Convocatoria) listaConvocatorias.get(i);
						String nombre = con.getTitulo();
						if (con.getTitulo().length() > 60) {
							nombre = con.getTitulo().substring(0, 60) + "...";
						}
						convocatoriaItem[i] = new SelectItem(con.getId().toString(), nombre);
					}
					convocatoriaActual = ((Convocatoria) (buscarModalidadxId(
							((Convocatoria) proyectoActual.getModalidad()).getId().toString())));
					convocatoria = ((Convocatoria) proyectoActual.getModalidad()).getId().toString();

					List listaAreas = servicioProyecto.obtenerAreasTematicas(proyectoActual);
					areasExperticiaItem = new SelectItem[listaAreas.size() + 1];
					areasExperticiaItem[0] = new SelectItem("", "");
					for (int i = 1; i < listaAreas.size() + 1; i++) {
						ClasificacionConocimiento clasificacion = (ClasificacionConocimiento) listaAreas.get(i - 1);
						areasExperticiaItem[i] = new SelectItem(clasificacion.getId(), clasificacion.getNombre());
					}

				}

				if (listaProyectos.size() != 0) {
					for (int i = 0; i < listaProyectos.size(); i++) {
						Proyecto p = (Proyecto) listaProyectos.get(i);
						String nombre = p.getNombre();
						if (nombre.length() > 65) {
							nombre = nombre.substring(0, 40) + "...";
						}
						coordinadorTieneProyectos = true;
						panelRender[3] = true;

					}
					proyectoId = ((Proyecto) listaProyectos.get(0)).getId().toString();
					proyectoActual = (Proyecto) listaProyectos.get(0);
					setNombreProy(proyectoActual.getNombre());

					cargarTiposDocumento();

				} else {
					listaProyectos.clear();
					coordinadorTieneProyectos = false;
					mensajeBusqueProyecto = "No se ha encontrado ningún proyecto asociado que se encuentre en estado Elegible, o el proyecto no ha sido asignado ";
				}
				idProyecto = "";
				panelRender[12] = false;
			} else {
				mensajeBusqueProyecto = "Debe ingresar un valor númerico válido.";
			}
		}
	}

	public void buscarPorIdPersona(ActionEvent event) {
		String tipoDocumento = tipoDocumentoParaCorreo;
		String documento = documentoParaCorreo;
		IdPersona id = new IdPersona();
		id.setDocumento(documento);
		id.setTipoDocumento(tipoDocumento);
		Persona p = servicioPersona.obtenerPersona(id);
		mensajeCorreo = "";
		if (p != null) {
			mensajeBusqueda = "";
			correoDestino = p.getEmail();
			nombreDestino = (p.getApellido1() == null ? "" : p.getApellido1()) + (p.getApellido1() == null ? "" : " ")
					+ (p.getApellido2() == null ? "" : p.getApellido2()) + (p.getApellido2() == null ? "" : " ")
					+ (p.getNombre1() == null ? "" : p.getNombre1()) + (p.getNombre1() == null ? "" : " ")
					+ (p.getNombre2() == null ? "" : p.getNombre2());
		} else {
			mensajeBusqueda = "El evaluador no existe";
		}
	}

	public void agregarPosibleEvaluador() {
		this.mostrarNuevoEvaluador = true;
	}

	public void cancelarAgregarPosibleEvaluador() {
		this.mostrarNuevoEvaluador = false;
		permiteGuardar = false;
		editandoEvaluador = false;
		inicializarPosibleEvaluador();
	}

	public void buscarEvaluadorNombre() {
		listaPosiblesEvaluadores3.clear();
		List temp = new ArrayList();
		if (campoBusquedaNombreInvestigador.length() > 0 || campoBusquedaApellidoInvestigador.length() > 0) {
			temp = servicioPersona.obtenerInvestigadoresPorNombresYApellidos(campoBusquedaNombreInvestigador,
					campoBusquedaApellidoInvestigador, false);
		}
		if (temp == null) {
			setMensajeBusEval("No se encuentra registrado");
		} else {

			listaPosiblesEvaluadores3 = temp;
			posiblesEvaluadoresItem3 = new SelectItem[listaPosiblesEvaluadores3.size()];
			if (listaPosiblesEvaluadores3 != null && listaPosiblesEvaluadores3.size() > 0) {
				for (int i = 0; i < listaPosiblesEvaluadores3.size(); i++) {
					posibleEval3 = (Investigador) listaPosiblesEvaluadores3.get(i);
					String nombre = posibleEval3.getId().getTipoDocumento() + ". " + posibleEval3.getId().getDocumento()
							+ " - " + posibleEval3.getNombreCompleto();
					posiblesEvaluadoresItem3[i] = new SelectItem(posibleEval3.getId().getDocumento(), nombre);
				}
				posibleEvaluadorSel3 = (String) posiblesEvaluadoresItem3[0].getValue();
				panelRender[13] = true;
				activarBotones();
			}

			mensajeError2 = "";
		}

	}

	public void inicializarPosibleEvaluador() {
		nombre = "";
		nombre2 = "";
		primerApellido = "";
		segundoApellido = "";
		facultad = "";
		sede = "";
		telefono = "";
		email = "";
		genero = "";
		mensajeEvaluador = "";
		idPosibleEvaluador = 0;
		areaPorExperticia = "";
		idClasificacionConocimiento = "";
		tesis = "";
		institucionEstudio = "";
		perfil = "";
		areaCiencia = "";
		subAreaCiencia = "";
		experticia = "";
		cvlac = "";
		tipo = "";
		minciencias = "";
		formacion = "";
		panelRender[14] = false;
	}

	public void consultarConvocatorias() {

		panelRender[12] = false;
		if (nombreConvocatoria != null && nombreConvocatoria.length() > 0) {
			listaConvocatoriasPadre = servicioGeneral.obtenerListaObjetosWhere("ConvocatoriaPadre",
					"where titulo like '%" + nombreConvocatoria.toUpperCase()
							+ "%' and  ( cnp_estado='I' or (esPermanente = 'Y' AND cnp_estado='A')) order by id");
			if (listaConvocatoriasPadre != null && listaConvocatoriasPadre.size() > 0) {
				convocatoriaPadreItem = new SelectItem[listaConvocatoriasPadre.size()];

				for (int i = 0; i < listaConvocatoriasPadre.size(); i++) {
					ConvocatoriaPadre con = (ConvocatoriaPadre) listaConvocatoriasPadre.get(i);
					String nombre = con.getTitulo();
					if (con.getTitulo().length() > 200) {
						nombre = con.getTitulo().substring(0, 200) + "...";
					}
					convocatoriaPadreItem[i] = new SelectItem(con.getId().toString(), nombre);
				}

				if (listaConvocatoriasPadre != null && listaConvocatoriasPadre.size() > 0) {
					convocatoriaPadre = ((ConvocatoriaPadre) listaConvocatoriasPadre.get(0)).getTitulo();
				}

				if (listaConvocatoriasPadre != null && listaConvocatoriasPadre.size() > 0) {

					listaConvocatorias = servicioModalidad
							.obtenerConvocatoriasxPadre((ConvocatoriaPadre) listaConvocatoriasPadre.get(0));

					convocatoriaItem = new SelectItem[listaConvocatorias.size()];
					for (int i = 0; i < listaConvocatorias.size(); i++) {
						Convocatoria con = (Convocatoria) listaConvocatorias.get(i);
						String nombre = con.getTitulo();
						if (con.getTitulo().length() > 200) {
							nombre = con.getTitulo().substring(0, 200) + "...";
						}
						convocatoriaItem[i] = new SelectItem(con.getId().toString(), nombre);
					}
					if (listaConvocatorias != null && listaConvocatorias.size() > 0) {
						nombreModalidad = ((Convocatoria) listaConvocatorias.get(0)).getTitulo();
						convocatoriaActual = ((Convocatoria) listaConvocatorias.get(0));
						fechaFinalEvaluacion = convocatoriaActual.getFechaFinalEval();
						if (convocatoriaActual != null) {
							ConvocatoriaPadre padre = convocatoriaActual.getPadre();
							nombrePadre = padre.getTitulo();
						}
					}
				} else {
					listaConvocatorias = new ArrayList();
					convocatoriaItem = new SelectItem[listaConvocatorias.size()];
				}
			} else {
				listaConvocatoriasPadre = new ArrayList();
				listaConvocatorias = new ArrayList();
				convocatoriaPadreItem = new SelectItem[listaConvocatoriasPadre.size()];
				convocatoriaItem = new SelectItem[listaConvocatorias.size()];
			}

		} else {
			listaConvocatoriasPadre = new ArrayList();
			listaConvocatorias = new ArrayList();
			convocatoriaPadreItem = new SelectItem[listaConvocatoriasPadre.size()];
			convocatoriaItem = new SelectItem[listaConvocatorias.size()];
		}

		coordinadorTieneProyectos = false;

	}

	public void cambiarForm() {
		String tipoB = tipoBusqueda;
		if (tipoB.equals("Convocatoria")) {
			mostrarBusquedaConvocatoria = true;
			mostrarBusquedaProyecto = false;
			panelRender[12] = false;
			coordinadorTieneProyectos = false;
		}
		if (tipoB.equals("Individual")) {
			mostrarBusquedaProyecto = true;
			mostrarBusquedaConvocatoria = false;
			panelRender[12] = false;
			coordinadorTieneProyectos = false;
		}
	}

	public void cambiarFormEval() {
		panelRender[16] = false;
		panelRender[14] = false;
		panelRender[15] = false;
		panelRender[10] = false;
		panelRender[11] = false;
		panelRender[4] = false;
		panelRender[5] = false;
		panelRender[8] = false;
		panelRender[9] = false;
		panelRender[13] = false;
		investigadorRes = "";

		String tipoB = (String) tipoBusquedaEval;
		if (tipoB.equals("nombre")) {
			panelRender[7] = true;
		}
		if (tipoB.equals("documento")) {
			panelRender[7] = false;

		}
	}

	public void crearEval() {
		panelRender[6] = false;
	}

	public void buscarEval() {
		panelRender[6] = true;
	}

	public void visualizarPosiblesEvaluadores() {
		panelRender[12] = true;
		panelRender[4] = false;
		panelRender[5] = false;
		panelRender[14] = false;
		panelRender[15] = false;
		panelRender[10] = false;
		panelRender[11] = false;
		panelRender[8] = false;
		panelRender[9] = false;
		panelRender[17] = false;
		panelRender[18] = false;
		if (!panelRender[3]) {
			proyectoActual = buscarProyecto(proyectoId);
		}

	}

	public void verEvaluadores() {
		panelRender[12] = true;
		panelRender[4] = false;
		panelRender[5] = false;
		panelRender[14] = false;
		panelRender[15] = false;
		panelRender[10] = false;
		panelRender[11] = false;
		panelRender[8] = false;
		panelRender[9] = false;
		panelRender[17] = false;
		panelRender[18] = false;

	}

	public void activarBotones() {
		panelRender[14] = false;
		if (posibleEvaluadorSel3 != null && !posibleEvaluadorSel3.equals("N")) {
			esNuevo = false;
		} else {
			esNuevo = true;
		}
		panelRender[16] = true;
	}

	public void verInfo3() {
		mensajeError3 = "";
		mensajeCorreo3 = "";

		panelRender[11] = false;
		panelRender[14] = false;
		panelRender[15] = false;
		panelRender[4] = false;
		panelRender[5] = false;
		panelRender[8] = false;
		panelRender[9] = false;

		String idPos3 = this.posibleEvaluadorSel3;
		if (idPos3 != null) {
			List posibles3 = servicioGeneral
					.obtenerObjetos("select pe from Persona pe" + " where pe.id.documento  ='" + idPos3 + "' ");
			if (posibles3 != null && posibles3.size() > 0) {
				setPosevaluador3((Persona) posibles3.get(0));
				panelRender[10] = true;
			}
		}
	}

	public void verInfoEvaluadorSeleccionado() {
		verInfo(posibleEvaluadorSel3);
	}

	public void verInfoEvaluador() {
		verInfo(codigoEvaluador);
	}

	public void crearEvaluadorNuevo() {
		inicializarPosibleEvaluador();
		esNuevo = true;
		codigoEvaluador = "";
		tipoDocumentoNuevo = "";
		panelRender[14] = true; // Formulario evaluador
	}

	public void verInfo(String doucumentoEvaluador) {
		inicializarPosibleEvaluador();
		mensajeError4 = "";
		mensajeCorreo4 = "";
		panelRender[15] = false; // Enviar correo
		panelRender[11] = false; // Enviar correo
		panelRender[14] = true; // Formulario evaluador

		ArrayList<Investigador> investigadores = new ArrayList<Investigador>();

		List listaPosiblesEvaluadores = new ArrayList();		
		
		try {
			investigadores = (ArrayList<Investigador>) servicioGeneral.obtenerObjetos(
					"select pe from Investigador pe" + " where pe.id.documento  ='" + doucumentoEvaluador + "'");
			listaPosiblesEvaluadores = servicioGeneral.obtenerObjetos(
					"select e from Evaluador e" + " where e.id.documento='" + doucumentoEvaluador + "'");
		} catch (Exception e) {
			e.printStackTrace();
		}

		Investigador invesConsultado = new Investigador();

		if (investigadores != null && investigadores.size() > 0) {
			invesConsultado = (Investigador) investigadores.get(0);
			esNuevo = false;
			tipoDocumentoNuevo = invesConsultado.getId().getTipoDocumento();
			codigoEvaluador = invesConsultado.getId().getDocumento();
			nombre = invesConsultado.getNombre11();
			nombre2 = invesConsultado.getNombre22();
			primerApellido = invesConsultado.getApellido11();
			segundoApellido = invesConsultado.getApellido22();
			email = invesConsultado.getEmail();
			telefono = invesConsultado.getTelefono();
			genero = invesConsultado.getGenero();

			try {
				if (listaPosiblesEvaluadores != null && listaPosiblesEvaluadores.size() > 0) {
					posibleEvaluadorExterno = ((Evaluador) listaPosiblesEvaluadores.get(0));
					tesis = posibleEvaluadorExterno.getTesis();

					institucionEstudio = posibleEvaluadorExterno.getInstitucionEstudio().getId();
					institucionLabora = posibleEvaluadorExterno.getInstitucionLabora().getId();
					areaCiencia = posibleEvaluadorExterno.getAreaCiencia();
					cambiarArea();
					subAreaCiencia = posibleEvaluadorExterno.getSubAreaCiencia();
					cvlac = posibleEvaluadorExterno.getCvlac();
					formacion = posibleEvaluadorExterno.getFormacion();
					experticia = posibleEvaluadorExterno.getAreaExperticia();
					minciencias = posibleEvaluadorExterno.getMinciencias();
					tipo = posibleEvaluadorExterno.getTipo();
				}else {
					esNuevoEvaluador = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (invesConsultado.getInterno() != null && invesConsultado.getInterno().equals("S")
					&& invesConsultado.getTipoVinculacion().isEsDocente()) {
				esInterno = true;
				permiteGuardar = false;
				posibleEvaluadorInterno = servicioPersona.obtenerInvestigadorInternoCompleto(invesConsultado.getId());
				facultad = posibleEvaluadorInterno.getDependencia().getFacultad().getNombre();
				sede = posibleEvaluadorInterno.getDependencia().getSede().getNombre();
				perfil = posibleEvaluadorInterno.getPerfil();
				cvlac = posibleEvaluadorInterno.getUrlColciencias();
				areaCiencia = posibleEvaluadorInterno.getAreaOcde();
				subAreaCiencia = posibleEvaluadorInterno.getSubareaOcde();
				minciencias = posibleEvaluadorInterno.getEvaluadorMinciencias();
				experticia = posibleEvaluadorInterno.getAreaExperticia();
				
				List<InvestigadorLineaInvestigacion> lineasInvestigadorInterno = servicioGeneral
						.obtenerLineasInvestigacionInvestigador(invesConsultado.getId().getTipoDocumento(),
								invesConsultado.getId().getDocumento());
				if (lineasInvestigadorInterno != null && lineasInvestigadorInterno.size() > 0) {
					for (int i = 0; i > lineasInvestigadorInterno.size(); i++) {
						listaLineasInvestigacionEvaluador.add(lineasInvestigadorInterno.get(i).getLinea());
					}
				}
			} else {
				if (invesConsultado.getTipoVinculacion().isEsAdministrativo()) {
					esInternoAdministrativo = true;
				} else {
					esInterno = false;
				}
				listaLineasInvestigacionEvaluador
						.addAll(servicioPersona.obtenerLineasInvestigacionEvaluadorExterno(invesConsultado.getId()));
				permiteGuardar = true;
			}

			panelRender[16] = true;
			mensajeError2 = "";
		} else {
			
			IdPersona investigador = new IdPersona(doucumentoEvaluador, tipoDocumentoNuevo);
			Persona persona = new Persona();

			try {
				persona = servicioPersona.obtenerPersona(investigador);
			}catch (Exception e) {
				e.printStackTrace();
			}
			if(persona.getId()!=null) {
				esNuevo = false;
				tipoDocumentoNuevo = persona.getId().getTipoDocumento();
				codigoEvaluador = persona.getId().getDocumento();
				nombre = persona.getNombre11();
				nombre2 = persona.getNombre22();
				primerApellido = persona.getApellido11();
				segundoApellido = persona.getApellido22();
				email = persona.getEmail();
				telefono = persona.getTelefono();
				genero = persona.getGenero();
				
			}else {
				permiteGuardar = true;
				esInterno = false;
				esInternoAdministrativo = false;
				esNuevo = true;
			}

		}

	}

	public String verCorreo4() {
		mensajeError4 = "";
		panelRender[14] = false;
		panelRender[4] = false;
		panelRender[5] = false;
		panelRender[8] = false;
		panelRender[9] = false;

		correoActual4 = (CorreoPlantilla) (correosL.get(0));
		String correo = correoActual4.getCuerpo();
		correoActualCuerpo4 = correo;
		
		if(posibleEvaluadorExterno!=null) {
			reemplazarCuerpo4();
			panelRender[15] = true;
		}else {
			if(!esInterno && !esInternoAdministrativo) {
			String documentoPosibleEvaluador = "";
			if (panelRender[7]) {
				documentoPosibleEvaluador = posibleEvaluadorSel3;
			} else {
				documentoPosibleEvaluador = codigoEvaluador;
			}
			
				try {
					List listaPosiblesEvaluadores = servicioGeneral.obtenerObjetos(
							"select e from Evaluador e" + " where e.id.documento='" + documentoPosibleEvaluador + "'");
					if (listaPosiblesEvaluadores != null && listaPosiblesEvaluadores.size() > 0) {
						posibleEvaluadorExterno = ((Evaluador) listaPosiblesEvaluadores.get(0));
						reemplazarCuerpo4();
						panelRender[15] = true;
					}else {
						mensajeError("Debe actualizar primero los datos del evaluador");
						return "";
					}
				} catch (Exception e) {
					e.printStackTrace();
					mensajeError("Debe actualizar primero los datos del evaluador");
				}
			}else {
				reemplazarCuerpo4();
				panelRender[15] = true;
			}
		}
		
		return "";

		
	}

	public void cerrar4() {
		panelRender[15] = false;
	}

	public void cerrar3() {
		panelRender[11] = false;
	}

	public Persona getPosevaluador4() {
		return posevaluador4;
	}

	public void setPosevaluador4(Persona posevaluador4) {
		this.posevaluador4 = posevaluador4;
	}

	public Persona getPosevalu4() {
		return posevalu4;
	}

	public void setPosevalu4(Persona posevalu4) {
		this.posevalu4 = posevalu4;
	}

	public boolean validacion() {

		boolean bandera = true;
		if (!esInterno) {
			if (this.getNombre() == null || this.getNombre().length() <= 0 || this.getNombre().length() > 100) {
				bandera = false;
				mensajeError("Nombre NO válido");
			}

			if (this.getPrimerApellido() == null || this.getPrimerApellido().length() <= 0
					|| this.getPrimerApellido().length() > 100) {
				bandera = false;
				mensajeError("Primer apellido NO válido");

			}

			if (this.getEmail() == null || this.getEmail().length() <= 0) {
				bandera = false;
				mensajeError("Correo NO válido");
			}

			if (esCadenaVacia(this.getTelefono()) || this.getTelefono().trim() != null && this.getTelefono().trim().length() >= 20) {
				bandera = false;
				mensajeError("Teléfono NO válido");
			}

			if (esCadenaVacia(institucionLabora)) {
				bandera = false;
				mensajeError("Debe seleccionar la institución donde labora el evaluador.");
			}

			if (esCadenaVacia(tipo)) {
				bandera = false;
				mensajeError("Debe indicar si es evaluador nacional o internacional.");
			}

			if (esCadenaVacia(areaCiencia) || esCadenaVacia(subAreaCiencia)) {
				bandera = false;
				mensajeError("Debe indicar el área y subárea OCDE.");
			}

			if (esCadenaVacia(cvlac)) {
				bandera = false;
				mensajeError("Debe indicar la url del cvlac");
			}
			if (listaLineasInvestigacionEvaluador == null || listaLineasInvestigacionEvaluador.size() == 0) {
				bandera = false;
				mensajeError("Debe ingresar al menos una línea de investigación para el evaluador.");
			}
		}
		if (esCadenaVacia(minciencias)) {
			bandera = false;
			mensajeError("Debe indicar si el evaluador está reconocido por Minciencias.");

		}
		if (!aceptaTerminos) {
			bandera = false;
			mensajeError("Debe aceptar los términos para guardar los datos del evaluador.");
		}

		try {
			if (this.tipoDocumentoNuevo.equals("C") || this.tipoDocumentoNuevo.equals("T")) {
				int valor = Integer.parseInt(this.getCodigoEvaluador());
			}

		} catch (Exception e) {
			bandera = false;
			mensajeError("Documento NO válido");
		}

		return bandera;
	}

	public Proyecto getProyectoActual() {
		return proyectoActual;
	}

	public void setProyectoActual(Proyecto proyectoActual) {
		this.proyectoActual = proyectoActual;
	}

	public String getProyectoId() {
		return proyectoId;
	}

	public void setProyectoId(String proyectoId) {
		this.proyectoId = proyectoId;
	}

	public String getMensajeCorreo() {
		return mensajeCorreo;
	}

	public void setMensajeCorreo(String mensajeCorreo) {
		this.mensajeCorreo = mensajeCorreo;
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

	public SelectItem[] getConvocatoriaItem() {
		return convocatoriaItem;
	}

	public void setConvocatoriaItem(SelectItem[] convocatoriaItem) {
		this.convocatoriaItem = convocatoriaItem;
	}

	public String getConvocatoriaPadre() {
		return convocatoriaPadre;
	}

	public void setConvocatoriaPadre(String convocatoriaPadre) {
		this.convocatoriaPadre = convocatoriaPadre;
	}

	public SelectItem[] getConvocatoriaPadreItem() {
		return convocatoriaPadreItem;
	}

	public void setConvocatoriaPadreItem(SelectItem[] convocatoriaPadreItem) {
		this.convocatoriaPadreItem = convocatoriaPadreItem;
	}

	public List getListaConvocatorias() {
		return listaConvocatorias;
	}

	public void setListaConvocatorias(List listaConvocatorias) {
		this.listaConvocatorias = listaConvocatorias;
	}

	public List getListaConvocatoriasPadre() {
		return listaConvocatoriasPadre;
	}

	public void setListaConvocatoriasPadre(List listaConvocatoriasPadre) {
		this.listaConvocatoriasPadre = listaConvocatoriasPadre;
	}

	public String getNombreModalidad() {
		return nombreModalidad;
	}

	public void setNombreModalidad(String nombreModalidad) {
		this.nombreModalidad = nombreModalidad;
	}

	public String getConvocatoria() {
		return convocatoria;
	}

	public void setConvocatoria(String convocatoria) {
		this.convocatoria = convocatoria;
	}

	public String getNombrePadre() {
		return nombrePadre;
	}

	public void setNombrePadre(String nombrePadre) {
		this.nombrePadre = nombrePadre;
	}

	public SelectItem[] getCorreos() {
		return correos;
	}

	public void setCorreos(SelectItem[] correos) {
		this.correos = correos;
	}

	public String getCorreoDestino() {
		return correoDestino;
	}

	public void setCorreoDestino(String correoDestino) {
		this.correoDestino = correoDestino;
	}

	public boolean isDetalle() {
		return detalle;
	}

	public void setDetalle(boolean detalle) {
		this.detalle = detalle;
	}

	public String getNombreDestino() {
		return nombreDestino;
	}

	public void setNombreDestino(String nombreDestino) {
		this.nombreDestino = nombreDestino;
	}

	public String getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(String idProyecto) {
		this.idProyecto = idProyecto;
	}

	public boolean isVerEvaluadores() {
		return verEvaluadores;
	}

	public void setVerEvaluadores(boolean verEvaluadores) {
		this.verEvaluadores = verEvaluadores;
	}

	public boolean isVerPosiblesEvaluadores() {
		return verPosiblesEvaluadores;
	}

	public void setVerPosiblesEvaluadores(boolean verPosiblesEvaluadores) {
		this.verPosiblesEvaluadores = verPosiblesEvaluadores;
	}

	public String getTipoDocumentoParaCorreo() {
		return tipoDocumentoParaCorreo;
	}

	public void setTipoDocumentoParaCorreo(String tipoDocumentoParaCorreo) {
		this.tipoDocumentoParaCorreo = tipoDocumentoParaCorreo;
	}

	public SelectItem[] getTiposDocumentoItem() {
		return tiposDocumentoItem;
	}

	public void setTiposDocumentoItem(SelectItem[] tiposDocumentoItem) {
		this.tiposDocumentoItem = tiposDocumentoItem;
	}

	public String getDocumentoParaCorreo() {
		return documentoParaCorreo;
	}

	public void setDocumentoParaCorreo(String documentoParaCorreo) {
		this.documentoParaCorreo = documentoParaCorreo;
	}

	public String getMensajeBusqueda() {
		return mensajeBusqueda;
	}

	public void setMensajeBusqueda(String mensajeBusqueda) {
		this.mensajeBusqueda = mensajeBusqueda;
	}

	public String getDocEvaluador() {
		return docEvaluador;
	}

	public void setDocEvaluador(String docEvaluador) {
		this.docEvaluador = docEvaluador;
	}

	public List getEstadoEvaluador() {
		return estadoEvaluador;
	}

	public void setEstadoEvaluador(List estadoEvaluador) {
		this.estadoEvaluador = estadoEvaluador;
	}

	public boolean isMostrarEvaluador() {
		return mostrarEvaluador;
	}

	public void setMostrarEvaluador(boolean mostrarEvaluador) {
		this.mostrarEvaluador = mostrarEvaluador;
	}

	public String[] getEvaluadoresSeleccionadosArray() {
		return evaluadoresSeleccionadosArray;
	}

	public void setEvaluadoresSeleccionadosArray(String[] evaluadoresSeleccionadosArray) {
		this.evaluadoresSeleccionadosArray = evaluadoresSeleccionadosArray;
	}

	public SelectItem[] getEvaluadoresItemArray() {
		return evaluadoresItemArray;
	}

	public void setEvaluadoresItemArray(SelectItem[] evaluadoresItemArray) {
		this.evaluadoresItemArray = evaluadoresItemArray;
	}

	public List<Institucion> getListaInstituciones() {
		return listaInstituciones;
	}

	public void setListaInstituciones(List<Institucion> listaInstituciones) {
		this.listaInstituciones = listaInstituciones;
	}

	public ArrayList<SelectItem> getListaInstitucionesItem() {
		return listaInstitucionesItem;
	}

	public void setListaInstitucionesItem(ArrayList<SelectItem> listaInstitucionesItem) {
		this.listaInstitucionesItem = listaInstitucionesItem;
	}

	public List<String> obtenerInstituciones(String nombreBusqueda) {
		return (List<String>) servicioGeneral.obtenerInstitucionesQueContienen(nombreBusqueda);
	}

	public String getInstitucionEvaluador() {
		return institucionEvaluador;
	}

	public void setInstitucionEvaluador(String institucionEvaluador) {
		this.institucionEvaluador = institucionEvaluador;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
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

	public boolean isPermiteGuardar() {
		return permiteGuardar;
	}

	public void setPermiteGuardar(boolean permiteGuardar) {
		this.permiteGuardar = permiteGuardar;
	}

	public Institucion getInstitucionInicial() {
		return institucionInicial;
	}

	public void setInstitucionInicial(Institucion institucionInicial) {
		this.institucionInicial = institucionInicial;
	}

	public String getIdClasificacionConocimiento() {
		return idClasificacionConocimiento;
	}

	public void setIdClasificacionConocimiento(String idClasificacionConocimiento) {
		this.idClasificacionConocimiento = idClasificacionConocimiento;
	}

	public String getCampoBusquedaNombreInvestigador() {
		return campoBusquedaNombreInvestigador;
	}

	public void setCampoBusquedaNombreInvestigador(String campoBusquedaNombre) {
		this.campoBusquedaNombreInvestigador = campoBusquedaNombre;
	}

	public String getCampoBusquedaApellidoInvestigador() {
		return campoBusquedaApellidoInvestigador;
	}

	public void setCampoBusquedaApellidoInvestigador(String campoBusquedaApellido) {
		this.campoBusquedaApellidoInvestigador = campoBusquedaApellido;
	}

	public void setNombreConvocatoria(String nombreConvocatoria) {
		this.nombreConvocatoria = nombreConvocatoria;
	}

	public String getNombreConvocatoria() {
		return nombreConvocatoria;
	}

	public boolean[] getPanelRender() {
		return panelRender;
	}

	public void setPanelRender(boolean[] panelRender) {
		this.panelRender = panelRender;
	}

	public void setTipoBusqueda(String tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}

	public String getTipoBusqueda() {
		return tipoBusqueda;
	}

	public void setNombreProy(String nombreProy) {
		this.nombreProy = nombreProy;
	}

	public String getNombreProy() {
		return nombreProy;
	}

	public List getListaPosiblesEvaluadores2() {
		return listaPosiblesEvaluadores2;
	}

	public void setListaPosiblesEvaluadores2(List listaPosiblesEvaluadores2) {
		this.listaPosiblesEvaluadores2 = listaPosiblesEvaluadores2;
	}

	public void setMensajeCorreo2(String mensajeCorreo2) {
		this.mensajeCorreo2 = mensajeCorreo2;
	}

	public String getMensajeCorreo2() {
		return mensajeCorreo2;
	}

	public void setMensajeBusEval(String mensajeBusEval) {
		this.mensajeBusEval = mensajeBusEval;
	}

	public String getMensajeBusEval() {
		return mensajeBusEval;
	}

	public void setMensajeCrear(String mensajeCrear) {
		this.mensajeCrear = mensajeCrear;
	}

	public String getMensajeCrear() {
		return mensajeCrear;
	}

	public void setPosibleEvaluadorSel2(Long posibleEvaluadorSel2) {
		this.posibleEvaluadorSel2 = posibleEvaluadorSel2;
	}

	public Long getPosibleEvaluadorSel2() {
		return posibleEvaluadorSel2;
	}

	public void setPosibleEvaluadorSel3(String posibleEvaluadorSel3) {
		this.posibleEvaluadorSel3 = posibleEvaluadorSel3;
	}

	public String getPosibleEvaluadorSel3() {
		return posibleEvaluadorSel3;
	}

	public void setCorreoActualCuerpo2(String correoActualCuerpo2) {
		this.correoActualCuerpo2 = correoActualCuerpo2;
	}

	public String getCorreoActualCuerpo2() {
		return correoActualCuerpo2;
	}

	public void setCorreoId2(String correoId2) {
		this.correoId2 = correoId2;
	}

	public String getCorreoId2() {
		return correoId2;
	}

	public void setCuerpoCorreo2(String cuerpoCorreo2) {
		this.cuerpoCorreo2 = cuerpoCorreo2;
	}

	public String getCuerpoCorreo2() {
		return cuerpoCorreo2;
	}

	public void setMensajeCorreo3(String mensajeCorreo3) {
		this.mensajeCorreo3 = mensajeCorreo3;
	}

	public String getMensajeCorreo3() {
		return mensajeCorreo3;
	}

	public void setDetalle2(boolean detalle2) {
		this.detalle2 = detalle2;
	}

	public boolean isDetalle2() {
		return detalle2;
	}

	public void setDetalle3(boolean detalle3) {
		this.detalle3 = detalle3;
	}

	public boolean isDetalle3() {
		return detalle3;
	}

	public void setCorreoActualCuerpo4(String correoActualCuerpo4) {
		this.correoActualCuerpo4 = correoActualCuerpo4;
	}

	public String getCorreoActualCuerpo4() {
		return correoActualCuerpo4;
	}

	public void setCuerpoCorreo4(String cuerpoCorreo4) {
		this.cuerpoCorreo4 = cuerpoCorreo4;
	}

	public String getCuerpoCorreo4() {
		return cuerpoCorreo4;
	}

	public void setDetalle4(boolean detalle4) {
		this.detalle4 = detalle4;
	}

	public boolean isDetalle4() {
		return detalle4;
	}

	public void setPosevaluador3(Persona posevaluador3) {
		this.posevaluador3 = posevaluador3;
	}

	public Persona getPosevaluador3() {
		return posevaluador3;
	}

	public void setPosibleEvaluadorSel4(String posibleEvaluadorSel4) {
		this.posibleEvaluadorSel4 = posibleEvaluadorSel4;
	}

	public String getPosibleEvaluadorSel4() {
		return posibleEvaluadorSel4;
	}

	public Investigador getPosibleEval3() {
		return posibleEval3;
	}

	public void setPosibleEval3(Investigador posibleEval3) {
		this.posibleEval3 = posibleEval3;
	}

	public String getIdCorreo4() {
		return idCorreo4;
	}

	public void setIdCorreo4(String idCorreo4) {
		this.idCorreo4 = idCorreo4;
	}

	public String getMensajeCorreo4() {
		return mensajeCorreo4;
	}

	public void setMensajeCorreo4(String mensajeCorreo4) {
		this.mensajeCorreo4 = mensajeCorreo4;
	}

	public Investigador getInv3() {
		return investigadorEvaluador;
	}

	public void setInv3(Investigador inv3) {
		this.investigadorEvaluador = inv3;
	}

	private String investigadorRes = "";

	public String getInvestigadorRes() {
		return investigadorRes;
	}

	public void setInvestigadorRes(String investigadorRes) {
		this.investigadorRes = investigadorRes;
	}

	public String getCorreoActualCuerpo3() {
		return correoActualCuerpo3;
	}

	public void setCorreoActualCuerpo3(String correoActualCuerpo3) {
		this.correoActualCuerpo3 = correoActualCuerpo3;
	}

	public String getIdCorreo() {
		return idCorreo;
	}

	public void setIdCorreo(String idCorreo) {
		this.idCorreo = idCorreo;
	}

	public PosibleEvaluador getPosevalu2() {
		return posevalu2;
	}

	public void setPosevalu2(PosibleEvaluador posevalu2) {
		this.posevalu2 = posevalu2;
	}

	public Investigador getPosevalu3() {
		return posevalu3;
	}

	public void setPosevalu3(Investigador posevalu3) {
		this.posevalu3 = posevalu3;
	}

	public SelectItem[] getPosiblesEvaluadoresItem3() {
		return posiblesEvaluadoresItem3;
	}

	public void setPosiblesEvaluadoresItem3(SelectItem[] posiblesEvaluadoresItem3) {
		this.posiblesEvaluadoresItem3 = posiblesEvaluadoresItem3;
	}

	public SelectItem[] getPosiblesEvaluadoresItem4() {
		return posiblesEvaluadoresItem4;
	}

	public void setPosiblesEvaluadoresItem4(SelectItem[] posiblesEvaluadoresItem4) {
		this.posiblesEvaluadoresItem4 = posiblesEvaluadoresItem4;
	}

	public String getCorreoActualCuerpo() {
		return correoActualCuerpo;
	}

	public void setCorreoActualCuerpo(String correoActualCuerpo) {
		this.correoActualCuerpo = correoActualCuerpo;
	}

	public SelectItem[] getPosiblesEvaluadoresItem2() {
		return posiblesEvaluadoresItem2;
	}

	public void setPosiblesEvaluadoresItem2(SelectItem[] posiblesEvaluadoresItem2) {
		this.posiblesEvaluadoresItem2 = posiblesEvaluadoresItem2;
	}

	public List getListaPosiblesEvaluadores3() {
		return listaPosiblesEvaluadores3;
	}

	public void setListaPosiblesEvaluadores3(List listaPosiblesEvaluadores3) {
		this.listaPosiblesEvaluadores3 = listaPosiblesEvaluadores3;
	}

	public InvestigadorExterno getPersonaNueva() {
		return personaNueva;
	}

	public CorreoPlantilla getCorreoActual4() {
		return correoActual4;
	}

	public void setCorreoActual4(CorreoPlantilla correoActual4) {
		this.correoActual4 = correoActual4;
	}

	public String getTipoDocumentoNuevo() {
		return tipoDocumentoNuevo;
	}

	public void setTipoDocumentoNuevo(String tipoDocumentoNuevo) {
		this.tipoDocumentoNuevo = tipoDocumentoNuevo;
	}

	public void setPersonaNueva(InvestigadorExterno personaNueva) {
		this.personaNueva = personaNueva;
	}

	public String getMensajeError3() {
		return mensajeError3;
	}

	public void setMensajeError3(String mensajeError3) {
		this.mensajeError3 = mensajeError3;
	}

	public String getMensajeError4() {
		return mensajeError4;
	}

	public void setMensajeError4(String mensajeError4) {
		this.mensajeError4 = mensajeError4;
	}

	public List getAreasExperticia() {
		return areasExperticia;
	}

	public void setAreasExperticia(List areasExperticia) {
		this.areasExperticia = areasExperticia;
	}

	public String getAreaPorExperticia() {
		return areaPorExperticia;
	}

	public void setAreaPorExperticia(String areaPorExperticia) {
		this.areaPorExperticia = areaPorExperticia;
	}

	public SelectItem[] getAreasExperticiaItem() {
		return areasExperticiaItem;
	}

	public void setAreasExperticiaItem(SelectItem[] areasExperticiaItem) {
		this.areasExperticiaItem = areasExperticiaItem;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCodigoEvaluador() {
		return codigoEvaluador;
	}

	public void setCodigoEvaluador(String codigoEvaluador) {
		this.codigoEvaluador = codigoEvaluador;
	}

	public boolean isMostrarNuevoEvaluador() {
		return mostrarNuevoEvaluador;
	}

	public void setMostrarNuevoEvaluador(boolean mostrarNuevoEvaluador) {
		this.mostrarNuevoEvaluador = mostrarNuevoEvaluador;
	}

	public String getMensajeEvaluador() {
		return mensajeEvaluador;
	}

	public void setMensajeEvaluador(String mensajeEvaluador) {
		this.mensajeEvaluador = mensajeEvaluador;
	}

	public String getMensajeRespuesta() {
		return mensajeRespuesta;
	}

	public void setMensajeRespuesta(String mensajeRespuesta) {
		this.mensajeRespuesta = mensajeRespuesta;
	}

	public boolean isMostrado() {
		return mostrado;
	}

	public void setMostrado(boolean mostrado) {
		this.mostrado = mostrado;
	}

	public String getCorreoId4() {
		return correoId4;
	}

	public void setCorreoId4(String correoId4) {
		this.correoId4 = correoId4;
	}

	public List getListaEvaluadorCorreo() {
		return listaEvaluadorCorreo;
	}

	public void setListaEvaluadorCorreo(List listaEvaluadorCorreo) {
		this.listaEvaluadorCorreo = listaEvaluadorCorreo;
	}

	public boolean isCorrecto() {
		return correcto;
	}

	public void setCorrecto(boolean correcto) {
		this.correcto = correcto;
	}

	public String getMensajeConvocatoria() {
		return mensajeConvocatoria;
	}

	public void setMensajeConvocatoria(String mensajeConvocatoria) {
		this.mensajeConvocatoria = mensajeConvocatoria;
	}

	public String getTipoDocumentoB() {
		return tipoDocumentoB;
	}

	public void setTipoDocumentoB(String tipoDocumentoB) {
		this.tipoDocumentoB = tipoDocumentoB;
	}

	public String getDocumentoEvaluadorB() {
		return documentoEvaluadorB;
	}

	public void setDocumentoEvaluadorB(String documentoEvaluadorB) {
		this.documentoEvaluadorB = documentoEvaluadorB;
	}

	public String getTipoBusquedaEval() {
		return tipoBusquedaEval;
	}

	public void setTipoBusquedaEval(String tipoBusquedaEval) {
		this.tipoBusquedaEval = tipoBusquedaEval;
	}

	public String getMensajeError2() {
		return mensajeError2;
	}

	public void setMensajeError2(String mensajeError2) {
		this.mensajeError2 = mensajeError2;
	}

	public Institucion getInstitucionNueva() {
		return institucionNueva;
	}

	public void setInstitucionNueva(Institucion institucionNueva) {
		this.institucionNueva = institucionNueva;
	}

	public String getCorreoId3() {
		return correoId3;
	}

	public void setCorreoId3(String correoId3) {
		this.correoId3 = correoId3;
	}

	public CorreoPlantilla getCorreoActual3() {
		return correoActual3;
	}

	public void setCorreoActual3(CorreoPlantilla correoActual3) {
		this.correoActual3 = correoActual3;
	}

	public String getCuerpoCorreo3() {
		return cuerpoCorreo3;
	}

	public void setCuerpoCorreo3(String cuerpoCorreo3) {
		this.cuerpoCorreo3 = cuerpoCorreo3;
	}

	public CorreoPlantilla getCorreoActual2() {
		return correoActual2;
	}

	public void setCorreoActual2(CorreoPlantilla correoActual2) {
		this.correoActual2 = correoActual2;
	}

	public void setEvaluadorSeleccionado(ProyectoEvaluador evaluadorSeleccionado) {
		this.evaluadorSeleccionado = evaluadorSeleccionado;
	}

	public ProyectoEvaluador getEvaluadorSeleccionado() {
		return evaluadorSeleccionado;
	}

	public void setPosibleEvaluadorSel10(Long posibleEvaluadorSel10) {
		this.posibleEvaluadorSel10 = posibleEvaluadorSel10;
	}

	public Long getPosibleEvaluadorSel10() {
		return posibleEvaluadorSel10;
	}

	public void setPosiblesEvaluadoresItem10(SelectItem[] posiblesEvaluadoresItem10) {
		this.posiblesEvaluadoresItem10 = posiblesEvaluadoresItem10;
	}

	public SelectItem[] getPosiblesEvaluadoresItem10() {
		return posiblesEvaluadoresItem10;
	}

	public void setListaPosiblesEvaluadores10(List listaPosiblesEvaluadores10) {
		this.listaPosiblesEvaluadores10 = listaPosiblesEvaluadores10;
	}

	public List getListaPosiblesEvaluadores10() {
		return listaPosiblesEvaluadores10;
	}

	public void setMensajeBusqueProyecto(String mensajeBusqueProyecto) {
		this.mensajeBusqueProyecto = mensajeBusqueProyecto;
	}

	public String getMensajeBusqueProyecto() {
		return mensajeBusqueProyecto;
	}

	public void setMensajeBusqueProyecto2(String mensajeBusqueProyecto2) {
		this.mensajeBusqueProyecto2 = mensajeBusqueProyecto2;
	}

	public String getMensajeBusqueProyecto2() {
		return mensajeBusqueProyecto2;
	}

	public SelectItem[] getProyectoItem() {
		return proyectoItem;
	}

	public boolean isCoordinadorTieneProyectos() {
		return coordinadorTieneProyectos;
	}

	public boolean isMostrarBusquedaProyecto() {
		return mostrarBusquedaProyecto;
	}

	public boolean isMostrarBusquedaConvocatoria() {
		return mostrarBusquedaConvocatoria;
	}

	public boolean isEsInterno() {
		return esInterno;
	}

	public void setEsInterno(boolean esInterno) {
		this.esInterno = esInterno;
	}

	public String getNombre2() {
		return nombre2;
	}

	public void setNombre2(String nombre2) {
		this.nombre2 = nombre2;
	}

	public String getExperticia() {
		return experticia;
	}

	public void setExperticia(String experticia) {
		this.experticia = experticia;
	}

	public String getCvlac() {
		return cvlac;
	}

	public void setCvlac(String cvlac) {
		this.cvlac = cvlac;
	}

	public boolean isEsNuevo() {
		return esNuevo;
	}

	public void setEsNuevo(boolean esNuevo) {
		this.esNuevo = esNuevo;
	}

	public String getSubAreaCiencia() {
		return subAreaCiencia;
	}

	public void setSubAreaCiencia(String subAreaCiencia) {
		this.subAreaCiencia = subAreaCiencia;
	}

	public String getAreaCiencia() {
		return areaCiencia;
	}

	public void setAreaCiencia(String areaCiencia) {
		this.areaCiencia = areaCiencia;
	}

	public SelectItem[] getAreaCienciaItems() {
		return areaCienciaItems;
	}

	public void setAreaCienciaItems(SelectItem[] areaCienciaItems) {
		this.areaCienciaItems = areaCienciaItems;
	}

	public SelectItem[] getSubAreaCienciaItems() {
		return subAreaCienciaItems;
	}

	public void setSubAreaCienciaItems(SelectItem[] subAreaCienciaItems) {
		this.subAreaCienciaItems = subAreaCienciaItems;
	}

	public String getFacultad() {
		return facultad;
	}

	public void setFacultad(String facultad) {
		this.facultad = facultad;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	public Evaluador getPosibleEvaluadorExterno() {
		return posibleEvaluadorExterno;
	}

	public void setPosibleEvaluadorExterno(Evaluador posibleEvaluadorExterno) {
		this.posibleEvaluadorExterno = posibleEvaluadorExterno;
	}

	public InvestigadorInterno getPosibleEvaluadorInterno() {
		return posibleEvaluadorInterno;
	}

	public void setPosibleEvaluadorInterno(InvestigadorInterno posibleEvaluadorInterno) {
		this.posibleEvaluadorInterno = posibleEvaluadorInterno;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public String getTesis() {
		return tesis;
	}

	public void setTesis(String tesis) {
		this.tesis = tesis;
	}

	public String getInstitucionEstudio() {
		return institucionEstudio;
	}

	public void setInstitucionEstudio(String institucionEstudio) {
		this.institucionEstudio = institucionEstudio;
	}

	public boolean isAceptaTerminos() {
		return aceptaTerminos;
	}

	public void setAceptaTerminos(boolean aceptaTerminos) {
		this.aceptaTerminos = aceptaTerminos;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public SelectItem[] getTipoFormacionItem() {
		return tipoFormacionItem;
	}

	public void setTipoFormacionItem(SelectItem[] tipoFormacionItem) {
		this.tipoFormacionItem = tipoFormacionItem;
	}

	public String getFormacion() {
		return formacion;
	}

	public void setFormacion(String formacion) {
		this.formacion = formacion;
	}

	public String getMinciencias() {
		return minciencias;
	}

	public void setMinciencias(String minciencias) {
		this.minciencias = minciencias;
	}

	public String getLineaInvestigacion() {
		return lineaInvestigacion;
	}

	public void setLineaInvestigacion(String lineaInvestigacion) {
		this.lineaInvestigacion = lineaInvestigacion;
	}

	public String getInstitucionLabora() {
		return institucionLabora;
	}

	public void setInstitucionLabora(String institucionLabora) {
		this.institucionLabora = institucionLabora;
	}

	public List<LineaInvestigacion> getListaLineasInvestigacionEvaluador() {
		return listaLineasInvestigacionEvaluador;
	}

	public void setListaLineasInvestigacionEvaluador(List<LineaInvestigacion> listaLineasInvestigacionEvaluador) {
		this.listaLineasInvestigacionEvaluador = listaLineasInvestigacionEvaluador;
	}

	public LineaInvestigacion getLineaInvestigacionSeleccionada() {
		return lineaInvestigacionSeleccionada;
	}

	public void setLineaInvestigacionSeleccionada(LineaInvestigacion lineaInvestigacionSeleccionada) {
		this.lineaInvestigacionSeleccionada = lineaInvestigacionSeleccionada;
	}

	public SelectItem[] getListaLineasInvestigacionItems() {
		return listaLineasInvestigacionItems;
	}

	public void setListaLineasInvestigacionItems(SelectItem[] listaLineasInvestigacionItems) {
		this.listaLineasInvestigacionItems = listaLineasInvestigacionItems;
	}

	public SelectItem[] getSiNoItem() {
		return siNoItem;
	}

	public void setSiNoItem(SelectItem[] siNoItem) {
		this.siNoItem = siNoItem;
	}

	public SelectItem[] getTipoItem() {
		return tipoItem;
	}

	public void setTipoItem(SelectItem[] tipoItem) {
		this.tipoItem = tipoItem;
	}

	public boolean isEsInternoAdministrativo() {
		return esInternoAdministrativo;
	}

	public void setEsInternoAdministrativo(boolean esInternoAdministrativo) {
		this.esInternoAdministrativo = esInternoAdministrativo;
	}

	public boolean isRequiereOtraFuente() {
		return requiereOtraFuente;
	}

	public void setRequiereOtraFuente(boolean requiereOtraFuente) {
		this.requiereOtraFuente = requiereOtraFuente;
	}

	public SelectItem[] getTiposNaturalezaFuenteItem() {
		return crearListaItemDominioDetalle(Dominio.NAT_ENTIDAD);
	}

	public void setTiposNaturalezaFuenteItem(SelectItem[] tiposNaturalezaFuenteItem) {
		this.tiposNaturalezaFuenteItem = tiposNaturalezaFuenteItem;
	}

	public SelectItem[] getCaracterFuenteFinanciacionItem() {
		return crearListaItemDominioDetalle(Dominio.CARACTER_FUENTE_FINANCIACION);
	}

	public SelectItem[] getTiposFuenteFinanciacionItem() {
		return crearListaItemDominioDetalle(Dominio.TIPO_FUENTE_FINANCIACION);
	}

	public Convocatoria getConvocatoriaActual() {
		return convocatoriaActual;
	}

	public void setConvocatoriaActual(Convocatoria convocatoriaActual) {
		this.convocatoriaActual = convocatoriaActual;
	}

	public Date getFechaFinalEvaluacion() {
		return fechaFinalEvaluacion;
	}

	public void setFechaFinalEvaluacion(Date fechaFinalEvaluacion) {
		this.fechaFinalEvaluacion = fechaFinalEvaluacion;
	}

	public boolean isEsNuevoEvaluador() {
		return esNuevoEvaluador;
	}

	public void setEsNuevoEvaluador(boolean esNuevoEvaluador) {
		this.esNuevoEvaluador = esNuevoEvaluador;
	}

}