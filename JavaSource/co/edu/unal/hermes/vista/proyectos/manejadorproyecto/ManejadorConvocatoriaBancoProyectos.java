package co.edu.unal.hermes.vista.proyectos.manejadorproyecto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.menuitem.MenuItem;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.AreaTematica;
import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.Departamento;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DependenciaAreaResponsabilidad;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Financiacion;
import co.edu.unal.hermes.modelo.Formulario;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.Gasto;
import co.edu.unal.hermes.modelo.HistoricoFormularioProyecto;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Institucion;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorExterno;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.Modalidad;
import co.edu.unal.hermes.modelo.ModalidadFuenteFinanciacion;
import co.edu.unal.hermes.modelo.Pais;
import co.edu.unal.hermes.modelo.PalabraClave;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.ResultadoProyecto;
import co.edu.unal.hermes.modelo.RubroFinanciable;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoInvestigador;
import co.edu.unal.hermes.modelo.TipoRubro;
import co.edu.unal.hermes.utils.VariablesEstaticas;
import co.edu.unal.hermes.vista.proyectos.ManejadorMenuFormularios;

public class ManejadorConvocatoriaBancoProyectos extends ManejadorProyecto {

	private Convocatoria convocatoriaActual;
	private PalabraClave palabraClave; // PALABRA CLAVE ACTUAL
	private PalabraClave palabraClaveTabla; // PALABRA CLAVE ACTUAL
	private PalabraClave keyWord; // PALABRA CLAVE ACTUAL
	public List<PalabraClave> listaPalabrasClave;
	private String linkLineas;
	private String areaCiencia = "2701";
	private String areaCienciaSec = "2701";
	private String DOMINIO_AREA_CIENCIA = "AREA_CIENCIA";
	private List listaAreaCiencia;
	private SelectItem[] areaCienciaItems;
	private String dependenciaAdicionada;
	private List<Dependencia> dependenciasUN;
	public SelectItem[] dependenciaItem;
	public SelectItem[] autores;
	private DependenciaAreaResponsabilidad dependenciaAreaResponsabilidad;
	private DependenciaAreaResponsabilidad dependenciaAreaResponsabilidadSeleccionada;
	private InvestigadorProyecto investigadorProyectoNuevo;
	private List listaTipoDocumento;
	private SelectItem[] tipoDocumentoItem;
	public List listaInvestigadoresVista;
	private boolean investigadorExiste = true;
	private InvestigadorProyecto copiaValidacionInvestigador;
	private InvestigadorProyecto investigadorProyectoActual;
	private TipoDocumento tipoDocumentoCoInv2;
	private String documentoCoinv2;
	private String tipoInvestigador;
	private List<InvestigadorProyecto> listaParticipantes;
	private List<InvestigadorProyecto> listaParticipantesBorrados;
	private InvestigadorExterno investigadorExterno = new InvestigadorExterno();
	private String insitucionNombre;
	private boolean esOtraVinculacion = false;
	private SelectItem[] generoItem = {
			new SelectItem(VariablesEstaticas.GENERO_FEMENINO,
					VariablesEstaticas.GENERO_FEMENINO),
			new SelectItem(VariablesEstaticas.GENERO_MASCULINO,
					VariablesEstaticas.GENERO_MASCULINO) };
	private InvestigadorProyecto participante = new InvestigadorProyecto();
	private boolean proyectoExiste = false;
	private List listaAreasPrimSec;
	private SelectItem[] tipoEventoItems;
	private List<DominioDetalle> listaTipoEvento;
	private boolean mostrarOtroTipoEvento = false;
	private String DOMINIO_TIPO_COLECCION = "COLECCION_CONVOCATORIA_LIBROS";
	private String DOMINIO_TIPO_ENTIDAD_EXTERNA = "TIPO_ENTIDAD_EXTERNA_CONV_BP";
	private boolean mostrarSiConvLibros = false;
	private boolean mostrarSiArticulosUno = false;
	private boolean mostrarSiArticulosDos = false;
	private String dedicacionInvPpal;

	// CONV ARTICULOS
	public String documentoCoinv;
	public TipoDocumento tipoDocumentoCoInv;

	private String nombreCompletoPersonaActual;
	private String nombreSede;
	private String nombreFacultad;
	private String nombreDepartamento;
	private String emailPersonaActual;
	private String telefono;
	private InvestigadorInterno ii;

	private List listaAreas;

	private Persona personaActual;

	// investigadores

	private TipoDocumento tipoDocumentoInvExt;
	private String documentoInvExt;
	private String tipoInvestigadorInvExt;
	private ArrayList<InvestigadorExternoEscuelaInternacional> listaParticipantesExternos;
	private ArrayList<InvestigadorExternoEscuelaInternacional> listaParticipantesExternosBorrados;
	public SelectItem[] docentesUN;
	public SelectItem[] docentesInvitados;

	private String pais;
	private String departamento;
	private String ciudad;
	private SelectItem[] paisItem;
	protected List<SelectItem> departamentoItemList;
	protected List<SelectItem> ciudadItemList;
	private InvestigadorExternoEscuelaInternacional participanteExternoSeleccionado;

	private boolean mostrarFormIE = false;
	private boolean mostrarGuardarIE = false;

	private List listaFinanciaciones; // LISTA DE FINANCIACIONES ASOCIDAS AL
										// PROYECTO
	private List listaGastos; // LISTA DE GASTOS ASOCIDAS AL PROYECTO
	private List<Gasto> listaGastosBorrados; // LISTA DE GASTOS ASOCIDAS AL
	// PROYECTO
	private DataTable tablaGastos; // TABLA DE RUBROS
	private List listaTiposRubros; // LISTA GENERAL DE TIPOS DE RUBROS
	private Long idTipoRubro;
	private Gasto gastoSeleccionado;
	private int vigenciaGasto = 1;
	private String descripcionGasto = "";
	public Gasto gastoActual;
	public Long valorGasto;

	public TipoRubro tipoRubro;
	public List listaRubrosFinanciables;
	public SelectItem[] tiposRubroItem;

	// FUENTES DE FINANCIACION
	private List listaFuentesInternas; // LISTA GENERAL DE FUENTES DE
	// FINANCIACION INTERNAS
	private List listaFuentesExternas; // LISTA GENERAL DE FUENTES DE
	// FINANCIACION EXTERNAS
	private List listaAuxiliarFuentes; // LISTA GENERAL DE FUENTES DE
	// FINANCIACION
	private FuenteFinanciacion fuenteFinancieraActual; // FUENTE FINANCIERA
	// ESCOGIDA
	private Financiacion financiacionActual; // DATOS DE LA FINANCIACION DADA
												// PROYECTO
	public Long idtipoRubro;
	public Long idsubtipoRubro;

	private String nombreEntidadExterna;
	private String nitEntidadExterna;
	private String tipoEntidadExterna;
	private String nombreContactpEntidadExterna;
	private String cargoPersonaEntidadExterna;
	private String alcanceEntidadExterna;

	private SelectItem[] listaTipoEntidadExternaItems;
	private List<DominioDetalle> listaTipoEntidadExterna;
	private ArrayList<EntidadesExternasConvocatoriaBancoProyectos> listaEntidadesExternas = new ArrayList<EntidadesExternasConvocatoriaBancoProyectos>();
	private EntidadesExternasConvocatoriaBancoProyectos entidadExternaSeleccionada;
	private int dedicacionHorasSemana;
	private int dedicacionHorasSemanaExt;

	private String resultado;
	private List<ResultadoProyecto> listaResultados;
	private DataTable tablaResultados;
	private UIComponent uiResultado;
	private ResultadoProyecto resultadoTabla;

	private String esContrapartida = "No";
	private boolean mostrarContrapartida = false;
	private Long contraEfectivo = 0L;
	private Long contraEspecie = 0L;

	public ManejadorConvocatoriaBancoProyectos() {
		super();
		idManejador = CONVOCATORIA_LIBROS;
		palabraClave = new PalabraClave();
		keyWord = new PalabraClave();

		listaEntidadesExternas = new ArrayList<EntidadesExternasConvocatoriaBancoProyectos>();
		investigadorProyectoNuevo = new InvestigadorProyecto();
		tipoDocumentoCoInv2 = new TipoDocumento();
		cargarTiposDocumento();
		cargarValoresIniciales();
		cargarListaParticipantes();
		listaParticipantes = new ArrayList<InvestigadorProyecto>();
		listaParticipantesBorrados = new ArrayList<InvestigadorProyecto>();
		dependenciaAreaResponsabilidad = new DependenciaAreaResponsabilidad();
		listaAreasPrimSec = new ArrayList<AreaTematica>();

		tipoDocumentoInvExt = new TipoDocumento();
		listaParticipantesExternos = new ArrayList<InvestigadorExternoEscuelaInternacional>();
		listaParticipantesExternosBorrados = new ArrayList<InvestigadorExternoEscuelaInternacional>();
		cargarPaises();
		listaAreas = new Vector();
		personaActual = new Persona();

		listaRubrosFinanciables = new ArrayList();

		listaGastos = new ArrayList();

		listaGastosBorrados = new ArrayList();
		listaTiposRubros = new ArrayList();
		gastoActual = new Gasto();
		cargarRubrosModalidad();
		// INICIO FINANCIERO
		fuenteFinancieraActual = new FuenteFinanciacion();
		financiacionActual = new Financiacion();
		listaFuentesExternas = new ArrayList();
		listaFuentesInternas = new ArrayList();
		listaAuxiliarFuentes = new ArrayList();
		listaFinanciaciones = new ArrayList();
		listaResultados = new ArrayList<ResultadoProyecto>();

		constructorConvBancoProyectos();
		cargarConvocatoriaActual();

		if (proyectoActual.getId() != null) {

			proyectoActual = servicioProyecto.obtenerProyecto(
					proyectoActual.getId(), ProyectoDAOHibernate.TODO_POR_ID);

			if (proyectoActual.getJustificacion() != null
					&& !proyectoActual.getJustificacion().equals("")) {
				String[] entidadesGeneral = proyectoActual.getJustificacion()
						.split("<===>");
				for (int i = 0; i < entidadesGeneral.length; i++) {
					String[] entEsp = entidadesGeneral[i].split("~");
					try{
						System.out.println(entEsp[0]);
						System.out.println(entEsp[1]);
						System.out.println(entEsp[2]);
						System.out.println(entEsp[3]);
						System.out.println(entEsp[4]);
						System.out.println(entEsp[5]);
						System.out.println(entEsp[6]);
						System.out.println(entEsp[7]);
						System.out.println(entEsp[8]);
						
						EntidadesExternasConvocatoriaBancoProyectos enEx = new EntidadesExternasConvocatoriaBancoProyectos(
								entEsp[0].replace("Nombre entidad: ", ""),
								entEsp[1].replace("NIT: ", ""),
								entEsp[2].replace("Tipo entidad: ", ""),
								entEsp[3].replace("Persona contacto: ", ""),
								entEsp[4].replace("Cargo persona contacto: ", ""),
								entEsp[5].replace("Alcance colaboración interinstitucional: ",""), 
								entEsp[6].replace(" Tiene contrapartida: ", "").replace(" ", ""), 
								new Long(entEsp[7].replace(" Contrapartida efectivo: ", "").replace(" ", "")), 
								new Long(entEsp[8].replace(" Contrapartida especie: ", "").replace(" ", "")));							
						
						listaEntidadesExternas.add(enEx);
					}catch(Exception e){
						EntidadesExternasConvocatoriaBancoProyectos enEx = new EntidadesExternasConvocatoriaBancoProyectos(
								entEsp[0].replace("Nombre entidad: ", ""),
								entEsp[1].replace("NIT: ", ""),
								entEsp[2].replace("Tipo entidad: ", ""),
								entEsp[3].replace("Persona contacto: ", ""),
								entEsp[4].replace("Cargo persona contacto: ", ""),
								entEsp[5].replace(
												"Alcance colaboración interinstitucional: ",
												""), "No", 0L, 0L);							
						
						listaEntidadesExternas.add(enEx);
					}
					
				}
			}

			listaInvestigadoresVista = proyectoActual
					.getObtenerListaInvestigadoresVista();

			listaResultados.addAll(proyectoActual.getResultados());

			if (listaInvestigadoresVista != null) {
				for (int i = 0; i < listaInvestigadoresVista.size(); i++) {
					InvestigadorProyectoVista ipv = (InvestigadorProyectoVista) listaInvestigadoresVista
							.get(i);
					InvestigadorProyecto ip = ipv.getInvestigadorProyecto();
					if (ip.getTipo().getId().equals("PIEI")
							|| ip.getTipo().getId().equals("P")) {
						listaParticipantes.add(ip);
					} else {
						InvestigadorExternoEscuelaInternacional ieei = new InvestigadorExternoEscuelaInternacional();
						ieei.setInvExtProyecto(ip);
						InvestigadorExterno iex = new InvestigadorExterno();
						List listaInvExt = servicioGeneral
								.obtenerObjetos("select e from InvestigadorExterno e where e.id.documento = '"
										+ ip.getInvestigador().getId()
												.getDocumento()
										+ "' and e.id.tipoDocumento = '"
										+ ip.getInvestigador().getId()
												.getTipoDocumento() + "'");

						if (listaInvExt != null && listaInvExt.size() > 0) {
							iex = (InvestigadorExterno) listaInvExt.get(0);
						}
						ieei.setInvExt(iex);
						listaParticipantesExternos.add(ieei);
					}

				}
			} else {
				listaInvestigadoresVista = new ArrayList();
			}

			// cargar funentes financieras

			if (proyectoActual.getFinanciaciones().size() > 0) {
				listaFinanciaciones.addAll(proyectoActual.getFinanciaciones());
				/*
				 * boolean val = false;
				 * 
				 * for (int j = 0; j < listaFinanciaciones.size(); j++) {
				 * Financiacion finPry = (Financiacion)
				 * listaFinanciaciones.get(j); if
				 * (finPry.getFuente().getId().equals("1")) { val = true; } }
				 * 
				 * if (!val) { ingresarFuenteEspecieUN(); }
				 */

				Financiacion f = new Financiacion();
				f = (Financiacion) listaFinanciaciones.get(0);

				List gastosList = servicioGeneral
						.obtenerObjetos("select e from Gasto e where e.financiacion.id = "
								+ f.getId());
				ArrayList gast = new ArrayList<Gasto>();
				gast.addAll(gastosList);
				f.setGastosListados(gast);
				if (gastosList.size() > 0) {
					listaGastos.addAll(gastosList);
				}

			} else {
				cargarFuentesFinanciacion();
				ingresarFuenteEspecieUN();
			}

		} else {
			proyectoExiste = false;
			proyectoActual.cambiarEstadoPersona(EstadoProyecto.INGRESANDO,cargarPersonaActual());
			proyectoActual.setFase(0);
			proyectoActual.setDuracion(6);
			// ASIGNACION DEL INVESTIGADOR PRINCIPAL POR DEFECTO
			InvestigadorProyecto investigadorProyecto = new InvestigadorProyecto();
			investigadorProyecto.setInvestigador(servicioPersona
					.obtenerInvestigador(((Persona) sesion
							.getAttribute("persona")).getId()));
			investigadorProyecto.setProyecto(proyectoActual);
			TipoInvestigador ti = new TipoInvestigador();
			ti = (TipoInvestigador) servicioGeneral.obtenerObjeto(
					new TipoInvestigador(), InvestigadorProyecto.PRINCIPAL);
			investigadorProyecto.setTipo(ti);
			proyectoActual.adicionarInvestigadorProyecto(investigadorProyecto);
			listaParticipantes.add(investigadorProyecto);

			cargarFuentesFinanciacion();
			boolean val = false;

			if (true) {
				ingresarFuenteEspecieUN();
			}
		}

	}

	public void insertarResultado() {
		if (this.resultado != null && !this.resultado.equals("")) {
			System.out.println("resultado: " + this.resultado);
			ResultadoProyecto re = new ResultadoProyecto();
			re.setDescripcion(this.resultado);
			listaResultados.add(re);
			this.resultado = "";
		} else {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Por favor escriba el resultado",
					"Por favor escriba el resultado");
			mostrarMensaje(message, uiResultado);
		}

	}

	public void eliminarResultado() {
		listaResultados.remove(resultadoTabla);
		proyectoActual.borrarResultado(resultadoTabla);
		resultadoTabla = new ResultadoProyecto();
	}

	public void cargarConvocatoriaActual() {

		if (proyectoActual.getId() != null) {

			List listConvocatorias = servicioGeneral
					.obtenerObjetos("select e from Convocatoria e where e.id = "
							+ proyectoActual.getModalidad().getId());
			if (listConvocatorias.size() > 0 && listConvocatorias != null) {
				convocatoriaActual = (Convocatoria) listConvocatorias.get(0);
			}

		} else {
			Long idConvocatoria = (Long) sesion
					.getAttribute("idConvocatoriaActual");

			List listConvocatorias = servicioGeneral
					.obtenerObjetos("select e from Convocatoria e where e.id = "
							+ idConvocatoria);
			if (listConvocatorias.size() > 0 && listConvocatorias != null) {
				convocatoriaActual = (Convocatoria) listConvocatorias.get(0);
			}

		}

	}

	public void constructorConvBancoProyectos() {

		if (proyectoActual.getId() != null) {

			Investigador invesPry = servicioProyecto
					.obtenerInvestigadorPrincipalXProyecto(proyectoActual
							.getId());

			if (invesPry != null) {

				this.documentoCoinv = invesPry.getId().getDocumento();

				List listaDoc = servicioGeneral.obtenerObjetoXID(
						"TipoDocumento", invesPry.getId().getTipoDocumento());
				this.tipoDocumentoCoInv = (TipoDocumento) listaDoc.get(0);
				nombreCompletoPersonaActual = invesPry.getNombre1() + " "
						+ invesPry.getNombre2() + " " + invesPry.getApellido1()
						+ " " + invesPry.getApellido2();

				ii = servicioPersona.obtenerInvestigadorInterno(invesPry
						.getId());
				if (ii != null) {
					nombreSede = ii.getDependencia().getSede().getNombre();
					try {
						nombreFacultad = ii.getDependencia().getFacultad()
								.getNombre();
					} catch (Exception e) {
						nombreFacultad = "Sin dependencia asignada";
					}
					nombreDepartamento = ii.getDependencia().getNombre();
					emailPersonaActual = ii.getEmail();
					telefono = ii.getTelefono();
					dedicacionInvPpal = ii.getTipoDedicacion().getNombre();
				} else {
					nombreSede = "";
					nombreFacultad = "";
					nombreDepartamento = "";
					emailPersonaActual = "";
					telefono = "";
					dedicacionInvPpal = "";
				}

			}

		} else {

			Investigador investigadorActual = servicioPersona
					.obtenerInvestigadorProyectos(((Persona) sesion
							.getAttribute("persona")).getId());

			if (investigadorActual != null) {

				this.documentoCoinv = investigadorActual.getId().getDocumento();

				List listaDoc = servicioGeneral.obtenerObjetoXID(
						"TipoDocumento", investigadorActual.getId()
								.getTipoDocumento());
				this.tipoDocumentoCoInv = (TipoDocumento) listaDoc.get(0);
				nombreCompletoPersonaActual = investigadorActual.getNombre1()
						+ " " + investigadorActual.getNombre2() + " "
						+ investigadorActual.getApellido1() + " "
						+ investigadorActual.getApellido2();

				ii = servicioPersona
						.obtenerInvestigadorInterno(investigadorActual.getId());
				if (ii != null) {
					nombreSede = ii.getDependencia().getSede().getNombre();
					try {
						nombreFacultad = ii.getDependencia().getFacultad()
								.getNombre();
					} catch (Exception e) {
						nombreFacultad = "Sin dependencia asignada";
					}
					nombreDepartamento = ii.getDependencia().getNombre();
					emailPersonaActual = ii.getEmail();
					telefono = ii.getTelefono();
					dedicacionInvPpal = ii.getTipoDedicacion().getNombre();
				} else {
					nombreSede = "";
					nombreFacultad = "";
					nombreDepartamento = "";
					emailPersonaActual = "";
					telefono = "";
					dedicacionInvPpal = "";
				}

			}

		}

	}

	private void cargarPaises() {
		// JOptionPane.showMessageDialog(null, "Entro CP");
		List listaPaises = servicioGeneral.obtenerListaObjetos("Pais order by nombre");
		paisItem = new SelectItem[listaPaises.size()];
		for (int i = 0; i < listaPaises.size(); i++) {
			Pais paisObjeto = (Pais) listaPaises.get(i);
			paisItem[i] = new SelectItem(paisObjeto.getId(),
					paisObjeto.getNombre());
		}
		// tipo_documento = (TipoDocumento) listaTipoDocumento.get(0);
	}

	public void cargarDepto() {
		departamentoItemList = new ArrayList<SelectItem>();
		// JOptionPane.showMessageDialog(null, "Entra a cargar depto");
		if (pais.equals("CO")) {
			String consulta = "select dd from Departamento dd where dd.id like '%"
					+ pais + "%' ";
			List lista = servicioGeneral.obtenerObjetos(consulta);
			// JOptionPane.showMessageDialog(null,
			// "Entra a cargar depto - [CO]  - "+ lista.size());
			for (int i = 0; i < lista.size(); i++) {
				Departamento depto = (Departamento) lista.get(i);
				departamentoItemList.add(new SelectItem(depto.getId(), depto
						.getNombre()));
			}
			departamento = "CO11";
			cargarCiudad();

		}
	}

	public void cargarCiudad() {
		ciudadItemList = new ArrayList<SelectItem>();
		// JOptionPane.showMessageDialog(null, "Entra a cargar ciudad");
		if (pais.equals("CO")) {
			String consulta = "select cc from Ciudad cc where cc.departamento like '%"
					+ departamento + "%' ";
			List lista = servicioGeneral.obtenerObjetos(consulta);
			// JOptionPane.showMessageDialog(null,
			// "Entra a cargar ciudad [CO]- "+ lista.size());
			for (int i = 0; i < lista.size(); i++) {
				Ciudad ciudad = (Ciudad) lista.get(i);
				ciudadItemList.add(new SelectItem(ciudad.getId(), ciudad
						.getNombre()));
			}
		}
	}

	private void cargarListaParticipantes() {
		List listaInterno = servicioGeneral
				.obtenerObjetos("select e from TipoInvestigador e where e.tipoModalidad = 'CEI' and e.id in ('PIEI')");
		docentesUN = new SelectItem[listaInterno.size()];
		for (int i = 0; i < listaInterno.size(); i++) {
			TipoInvestigador ta = (TipoInvestigador) listaInterno.get(i);
			docentesUN[i] = new SelectItem(ta.getId(), ta.getNombre());
			ta = null;
		}

		List listaExterno = servicioGeneral
				.obtenerObjetos("select e from TipoInvestigador e where e.tipoModalidad = 'CEI' and e.id in ('INEI','IIEI')");
		docentesInvitados = new SelectItem[listaExterno.size()];
		for (int i = 0; i < listaExterno.size(); i++) {
			TipoInvestigador ta = (TipoInvestigador) listaExterno.get(i);
			docentesInvitados[i] = new SelectItem(ta.getId(), ta.getNombre());
			ta = null;
		}
	}

	private void cargarTiposDocumento() {
		listaTipoDocumento = servicioGeneral
				.obtenerListaObjetos("TipoDocumento");
		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			TipoDocumento td = (TipoDocumento) listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
		}
	}

	public void buscarParticipanteExterno() {
		if (documentoInvExt != null && !documentoInvExt.equals("")
				&& !documentoInvExt.equals(" ")) {
			try {

				// investigadorExterno = null;

				List listaInvExt = servicioGeneral
						.obtenerObjetos("select e from InvestigadorExterno e where e.id.documento = '"
								+ documentoInvExt
								+ "' and e.id.tipoDocumento = '"
								+ tipoDocumentoInvExt.getId() + "'");

				if (listaInvExt != null && listaInvExt.size() > 0) {
					investigadorExterno = (InvestigadorExterno) listaInvExt
							.get(0);

					// investigadorExterno =
					// servicioPersona.buscarInvestigadorExternoId(documentoInvExt,
					// tipoDocumentoInvExt.getId());

					if (investigadorExterno != null) {

						List listaInst = servicioGeneral
								.obtenerObjetos("select e from Institucion e where e.id = '"
										+ investigadorExterno.getInstitucion()
												.getId() + "'");
						Institucion ins = new Institucion();
						if (listaInst != null && listaInst.size() > 0) {
							ins = (Institucion) listaInst.get(0);
						}

						insitucionNombre = ins.getNombre();
						pais = investigadorExterno.getPaisOrigen();
						mostrarFormIE = true;
						mostrarGuardarIE = true;
					}
				}

				else {
					mostrarFormIE = true;
					mostrarGuardarIE = true;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_FATAL,
									"Por favor ingrese el número de identificación de la persona a registrar",
									"Por favor ingrese el número de identificación de la persona a registrar"));
		}
	}

	public void guardarParticipanteExterno() {

		if (investigadorExterno.getNombre1() != null
				&& !investigadorExterno.getNombre1().equals("")
				&& investigadorExterno.getApellido1() != null
				&& !investigadorExterno.getApellido1().equals("")
				&& insitucionNombre != null && !insitucionNombre.equals("")) {

			IdPersona id = new IdPersona(this.documentoInvExt,
					this.tipoDocumentoInvExt.getId());
			investigadorExterno.setId(id);
			investigadorExterno.setInterno(Investigador.EXTERNO);
			investigadorExterno.setEvaluador(Investigador.NO_EVALUADOR);
			investigadorExterno.setPaisOrigen(pais);

			if (insitucionNombre != null && !insitucionNombre.equals("")) {
				Institucion i = servicioGeneral
						.obtenerinstitucionPorNombre(insitucionNombre);

				if (i == null) {
					Institucion institucionNueva = new Institucion();
					institucionNueva.setNombre(insitucionNombre);
					servicioGeneral.guardarObjeto(institucionNueva);
					investigadorExterno.setInstitucion(institucionNueva);
				} else {
					investigadorExterno.setInstitucion(i);
				}
			} else {
				Institucion i = servicioGeneral
						.obtenerinstitucionPorNombre("--");
				investigadorExterno.setInstitucion(i);
			}

			Persona nuevaPersona = servicioPersona.obtenerPersona(id);

			if (nuevaPersona == null) {
				try {
					// Si la persona no existe se guarda como
					// investigador
					servicioPersona.guardarInvestigador(investigadorExterno);

				} catch (Exception e) {

				}

			} else {

				InvestigadorExterno persona = servicioPersona
						.obtenerInvestigadorExterno(id);
				if (persona == null) {
					servicioPersona.insertarExterno(investigadorExterno);
				}
			}

			// Agregar participante a la listaparticipante
			InvestigadorProyecto participante = new InvestigadorProyecto();
			participante.setInvestigador(investigadorExterno);
			participante
					.setDedicacionHorasSemana((short) dedicacionHorasSemanaExt);
			participante.setFuncion("Participante");
			participante.setProyecto(proyectoActual);
			TipoInvestigador ti = new TipoInvestigador();
			ti = (TipoInvestigador) servicioGeneral.obtenerObjeto(
					new TipoInvestigador(), tipoInvestigadorInvExt.toString());
			participante.setTipo(ti);
			investigadorExterno.setPaisOrigen(pais);
			// investigadorExterno.setCiudadDomicilio(ciudadDomicilio);
			// listaParticipantes.add(participante);
			InvestigadorExternoEscuelaInternacional iex = new InvestigadorExternoEscuelaInternacional();
			iex.setInvExt(investigadorExterno);
			iex.setInvExtProyecto(participante);
			listaParticipantesExternos.add(iex);
			mostrarFormIE = false;
			mostrarGuardarIE = false;
			investigadorExterno = new InvestigadorExterno();
			insitucionNombre = "";
			documentoInvExt = "";
			tipoDocumentoInvExt.setId("C");

		} else {
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_FATAL,
									"Por favor ingresar nombres, apellidos e institución de la persona a registrar",
									"Por favor ingresar nombres, apellidos e institución de la persona a registrar"));
		}

	}

	public boolean yaHayPrincipal() {
		// if(proyectoActual.getListaInvestigadoresProyecto().size()>0)
		if (listaParticipantes.size() > 0) {
			// for(Iterator
			// it=proyectoActual.getInvestigadoresProyecto().iterator();it.hasNext();)
			for (Iterator it = listaParticipantes.iterator(); it.hasNext();) {
				InvestigadorProyecto ipc = (InvestigadorProyecto) it.next();
				// it.next();
				if (ipc.getTipo().getId().equals(TipoInvestigador.Principal)
						&& ipc.getDedicacionHorasSemana() > 0) {
					return true;
				}
			}
		}
		FacesContext
		.getCurrentInstance()
		.addMessage(
				null,
				new FacesMessage(
						FacesMessage.SEVERITY_FATAL,
						"Por favor verifique y actualice la información del docente principal proponente.",
						"Por favor verifique y actualice la información del docente principal proponente."));
		return false;
		
	}

	public void cambiarVinculacion() {
		System.out.println("otra vinculacion");

		if (tipoInvestigador.equals("AEL")) {
			esOtraVinculacion = true;
		} else {
			esOtraVinculacion = false;
		}

	}

	public void cargarRubrosModalidad() {
		// TODO ESTE CARGUE HAY QUE REVISARLO YA QUE NO TIENE EN CEUNTA LOS
		// RUBROS DE CUANDO
		// LA MODALIDAD NO ES UNA CONVOCATORIA
		try {
			Object o = proyectoActual.getModalidad();
			System.out.println(o.getClass().getName());
			Modalidad mod = proyectoActual.getModalidad();
			listaRubrosFinanciables = servicioModalidad
					.obtenerRubrosFinanciables(mod);

			if (listaRubrosFinanciables != null) {
				tiposRubroItem = new SelectItem[listaRubrosFinanciables.size()];
			} else {
				tiposRubroItem = new SelectItem[0];
			}

			for (int i = 0; listaRubrosFinanciables != null
					&& i < listaRubrosFinanciables.size(); i++) {
				RubroFinanciable rf = (RubroFinanciable) listaRubrosFinanciables
						.get(i);
				TipoRubro tr = rf.getTipoRubro();
				listaTiposRubros.add(tr);
				String nombre = tr.getNombre();
				if (nombre != null && nombre.length() > 120) {
					nombre = nombre.substring(0, 120) + "...";
				}
				tiposRubroItem[i] = new SelectItem(tr.getId(), nombre);
				tr = null;
			}
			gastoActual.setTipoRubro(new TipoRubro());
			if (listaTiposRubros != null && listaTiposRubros.size() > 0) { // If
				// creado
				// por
				// giovanni
				gastoActual.getTipoRubro().setId(
						((TipoRubro) listaTiposRubros.get(0)).getId());
			}
			// }

		} catch (Exception e) {
			System.out
					.println("ManejadorInfoFinanciera:cargarTiposRubro:Error Cargando Los Tipos de Rubro");
			e.printStackTrace();
		}
	}

	public void cargarTiposRubro() {
		try {
			String hql = "select r from TipoRubro r where r.quipu= 'S' and r.padre.id= 0";
			System.out.print(hql);
			List lista = servicioGeneral.obtenerObjetos(hql);

			if (lista.size() > 0) {
				for (int i = 0; i < lista.size(); i++) {
					TipoRubro dominio = (TipoRubro) lista.get(i);
					String nombre = dominio.getNombre();
					if (nombre != null && nombre.length() > 120) {
						nombre = nombre.substring(0, 120) + "...";
					}
					listaRubrosFinanciables.add(new SelectItem(dominio.getId(),
							nombre));
					idtipoRubro = dominio.getId();
				}
			} else {
				listaRubrosFinanciables.add(new SelectItem("0", " - "));
				idtipoRubro = 3L;
			}

		} catch (Exception e) {
			System.out.println("Error cargando tipos de Rubro");
			e.printStackTrace();
		}
	}

	public void agregarGasto2() {
		boolean bandera = true;

		if (bandera) {
			try {

				gastoActual.setTipoRubro(buscarTipoRubro(gastoActual
						.getTipoRubro().getId()));
				listaGastos.add(gastoActual);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		gastoActual = new Gasto();

	}

	public boolean validarMontoFuente() {
		boolean val = true;

		if (listaFinanciaciones.size() > 0) {

			Financiacion fin = (Financiacion) listaFinanciaciones.get(0);

			if (fin.getValor() != null) {
				if (fin.getValor() + valorGasto > Long
						.parseLong(convocatoriaActual.getMontoApoyoGanadores())) {

					val = false;
					FacesContext
							.getCurrentInstance()
							.addMessage(
									"msgs",
									new FacesMessage(
											FacesMessage.SEVERITY_ERROR,
											"El monto solicitado supera el máximo de la convocatoria",
											""));

				}
			}

		} else {

		}

		return val;

	}

	public boolean validarSumaRubros() {
		boolean ret = true;
		int sumRub = 0;

		if (listaGastos.size() > 0) {
			for (int i = 0; i < listaGastos.size(); i++) {
				Gasto g = (Gasto) listaGastos.get(i);
				sumRub += g.getValor();
			}

			Financiacion fin = (Financiacion) listaFinanciaciones.get(0);

			if (sumRub > fin.getValor()) {
				ret = false;
				FacesContext
						.getCurrentInstance()
						.addMessage(
								"msgs",
								new FacesMessage(
										FacesMessage.SEVERITY_ERROR,
										"La suma de los rubros supera el monto solicitado y supera el máximo permitido de la convocatoria",
										""));

			}

		}

		return ret;
	}

	public boolean validarRubro() {
		boolean val = true;

		for (int i = 0; i < listaGastos.size(); i++) {
			Gasto g = (Gasto) listaGastos.get(i);
			if (g.getCantidad() == this.valorGasto
					&& g.getTipoRubro().getId() == this.idTipoRubro
					&& g.getVigencia() == this.vigenciaGasto) {
				val = false;
				FacesContext.getCurrentInstance().addMessage(
						"msgs",
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"El rubro ya se encuentra registrado", ""));
				break;
			}
		}

		return val;
	}

	public void agregarGasto() {

		boolean ban = true;

		if (this.valorGasto == 0 || this.valorGasto < 0) {
			ban = false;
			FacesContext.getCurrentInstance().addMessage(
					"msgs",
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"El valor debe ser mayor a cero", ""));
		}

		if (descripcionGasto == null || descripcionGasto.length() <= 0) {
			ban = false;
			FacesContext.getCurrentInstance().addMessage(
					"msgs",
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"La descripción se encuentra vacio", ""));
		}

		if (validarMontoFuente() && validarRubro()) {

			if (ban) {

				try {

					gastoActual = new Gasto();
					gastoActual.setValor(valorGasto);
					// Se asigna por defecto 1 a la cantidad y la vigencia
					gastoActual.setCantidad(1);
					gastoActual.setVigencia(vigenciaGasto);
					gastoActual.setDescripcion(descripcionGasto);
					Financiacion fg = new Financiacion();
					fg = (Financiacion) listaFinanciaciones.get(0);
					fg.adicionarGasto2(gastoActual);
					gastoActual.setFinanciacion(fg);
					gastoActual.setTipoRubro(buscarTipoRubro(idTipoRubro));
					// gastoActual.getTipoRubro().setId(idTipoRubro);

					listaGastos.add(gastoActual);
					vigenciaGasto = 1;
					descripcionGasto = "";
					valorGasto = 0l;
					calcularValoresFinanciacion();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	public Financiacion calcularValorFinanciacion(Financiacion financiacion) {
		if (financiacion != null) {
			Long valorTotal = 0L;
			List gastosList = financiacion.getGastosListados();
			if (gastosList != null && gastosList.size() > 0) {
				for (Object gasto : gastosList) {
					Gasto gast = (Gasto) gasto;
					valorTotal += gast.getValor();
				}
			}
			financiacion.setValor(valorTotal);
			return financiacion;
		} else
			return null;
	}

	public void ingresarFuenteEspecieUN() {

		Financiacion f = new Financiacion();
		try {

			FuenteFinanciacion ff = (FuenteFinanciacion) buscarFuente("1");
			f.setFuente(ff);
			f.setValor(0L);
			f.setRol("ROL_FT_FIN");
			if (listaFinanciaciones == null) {
				listaFinanciaciones = new ArrayList();
				listaFinanciaciones.add(f);
			} else {
				listaFinanciaciones.add(f);
			}
		} catch (Exception e) {
			System.out
					.println("ManejadorFichaMinima:asociarFuente:Error hallando la fuente de financiacion especificada");
			e.printStackTrace();
		}

	}

	private FuenteFinanciacion buscarFuente(String id) {
		// BUSCA UNA FUENTE DE FINANCIACION DE ACUERDO AL ID
		int i = 0;
		FuenteFinanciacion ff = new FuenteFinanciacion();
		while (i < listaAuxiliarFuentes.size()) {
			ff = (FuenteFinanciacion) listaAuxiliarFuentes.get(i);
			if (id.equals(ff.getId()))
				break;
			else
				ff = null;
			i = i + 1;
		}
		return ff;
	}

	public void cargarFuentesFinanciacion() {
		Modalidad modalidad = proyectoActual.getModalidad();
		List listaFinanciacionesDeConvocatoria = servicioModalidad
				.listaModFuenteFinXModalidad(modalidad.getId());

		if (listaFinanciacionesDeConvocatoria.size() > 0) {

			for (Iterator it = listaFinanciacionesDeConvocatoria.iterator(); it
					.hasNext();) {
				ModalidadFuenteFinanciacion mff = (ModalidadFuenteFinanciacion) it
						.next();
				FuenteFinanciacion ff = (FuenteFinanciacion) (servicioGeneral
						.obtenerObjeto(new FuenteFinanciacion(), mff
								.getFuenteFinanciacion().getId()));

				if (ff.getInternaExterna() != null) {
					if (ff.getInternaExterna().equalsIgnoreCase(
							FuenteFinanciacion.interna))
						listaFuentesInternas.add(ff);
					else
						listaFuentesExternas.add(ff);
				}
			}

			listaAuxiliarFuentes.addAll(listaFuentesInternas);
			listaAuxiliarFuentes.addAll(listaFuentesExternas);

		}
	}

	private void calcularValoresFinanciacion() {
		if (listaFinanciaciones != null && listaFinanciaciones.size() > 0) {
			int listadoFinan = listaFinanciaciones.size();
			for (int i = 0; i < listadoFinan; i++) {
				Financiacion fin2 = (Financiacion) listaFinanciaciones.get(i);
				Financiacion fin3 = calcularValorFinanciacion(fin2);
				listaFinanciaciones.set(i, fin3);
			}
		}
	}

	private TipoRubro buscarTipoRubro(Long id) {
		// BUSCA EL TIPO DE RUBRO POR EL ID
		TipoRubro tr = new TipoRubro();
		int i = 0;
		while (i < listaTiposRubros.size()) {
			tr = (TipoRubro) listaTiposRubros.get(i);
			if (id.longValue() == (tr.getId()).longValue())
				break;
			i = i + 1;
		}
		return tr;
	}

	private RubroFinanciable buscarRubroFinanciable(TipoRubro tr) {
		for (int i = 0; i < listaRubrosFinanciables.size(); i++) {
			RubroFinanciable rf = (RubroFinanciable) listaRubrosFinanciables
					.get(i);
			TipoRubro traux = rf.getTipoRubro();
			if (tr.getId().longValue() == traux.getId().longValue()) {
				return rf;
			}
		}
		return null;
	}

	private boolean validarPorcentajeGasto(Gasto gastoActual) {
		System.out.println("valida" + gastoActual.getValor()
				+ " para la fuente " + gastoActual.getTipoRubro().getNombre()
				+ " con %");
		// Financiacion f =
		// buscarFinanciacion(gastoActual.getFinanciacion().getId());
		Financiacion f = (Financiacion) listaFinanciaciones.get(0);
		long total_financiacion = f.getValor().longValue();
		RubroFinanciable rf = buscarRubroFinanciable(gastoActual.getTipoRubro());
		System.out.println(rf.getPorcentajeMaximo());
		double maximoFinanciable = (rf.getPorcentajeMaximo() / 100)
				* total_financiacion;

		System.out.println("maximo financiable " + maximoFinanciable);
		if (servicioModalidad.validarModalidadFuenteFinanciacion(proyectoActual
				.getModalidad().getId(), f.getFuente().getId())) {
			// wam²
			long sumaGatos = 0;
			sumaGatos = gastoActual.getValor().longValue();
			for (int i = 0; i < f.getListaGastos().size(); i++) {
				Gasto g = (Gasto) f.getListaGastos().get(i);
				if (g.getTipoRubro().getId()
						.compareTo(gastoActual.getTipoRubro().getId()) == 0) {
					sumaGatos = sumaGatos + g.getValor().longValue();
				}
			}
			if (sumaGatos > maximoFinanciable) {
				// mensajeRubros="El valor del rubro excede el porcentaje máximo permitido por la convocatoria"+"("+rf.getPorcentajeMaximo()+")";
				FacesContext
						.getCurrentInstance()
						.addMessage(
								"msgs",
								new FacesMessage(
										FacesMessage.SEVERITY_ERROR,
										"El valor del rubro excede el porcentaje máximo permitido por la convocatoria"
												+ "("
												+ rf.getPorcentajeMaximo()
												+ ") ó excede el total de la fuente de financiación seleccionada",
										""));
				return false;
			}
		}
		// mensajeRubros = "";
		return true;
	}

	public void eliminarGasto() {
		Gasto GastoEliminarFinanciacion = null;
		Financiacion financiacionEliminarGasto = null;
		for (int i = 0; i < listaGastos.size(); i++) {
			Gasto g = (Gasto) listaGastos.get(i);
			if (g.getCantidad() == gastoSeleccionado.getCantidad()
					&& g.getVigencia() == gastoSeleccionado.getVigencia()
					&& g.getTipoRubro().getId() == gastoSeleccionado
							.getTipoRubro().getId()
					&& g.getFinanciacion().getId() == gastoSeleccionado
							.getFinanciacion().getId()) {
				listaGastos.remove(i);
				for (Object fin : listaFinanciaciones) {
					Financiacion fin2 = (Financiacion) fin;
					if (fin2.getId() != null) {
						if (fin2.getId().equals(g.getFinanciacion().getId())
								&& fin2.getFuente()
										.getId()
										.equals(g.getFinanciacion().getFuente()
												.getId()))
							financiacionEliminarGasto = fin2;
					} else if (fin2.getFuente().getId()
							.equals(g.getFinanciacion().getFuente().getId()))
						financiacionEliminarGasto = fin2;
				}
				GastoEliminarFinanciacion = g;
				if (g.getId() != null) {
					listaGastosBorrados.add(g);
				}
			}
		}
		if (financiacionEliminarGasto != null
				&& GastoEliminarFinanciacion != null) {
			financiacionEliminarGasto.eliminarGasto(GastoEliminarFinanciacion);
			calcularValoresFinanciacion();
		}

		gastoSeleccionado = new Gasto();
	}

	public List obtenerPalabraClavesSugeridas(String nombre) {
		return servicioGeneral.obtenerPalabrasClaveEmpezandoCon(nombre);
	}

	// DEFINICION DE FUNCIONES ESPECIFICAS DE LA CLASE
	public void insertarPalabraClave() {
		System.out.println("inserta " + palabraClave.getPalabra());
		boolean existePalabra = palabraClave.existePalabraEnSet(proyectoActual
				.getPalabrasClaves());
		if ((!palabraClave.getPalabra().equals("")) && (!existePalabra)) {
			PalabraClave pc = new PalabraClave();
			pc.setPalabra(palabraClave.getPalabra().toUpperCase());
			pc.setPalabraOriginal(palabraClave.getPalabra());
			try {
				PalabraClave pc1 = servicioGeneral.obtenerPalabraClave(pc
						.getPalabra());
				if (pc1 == null) {
					pc.setIdioma("ES");
					servicioGeneral.guardarObjeto(pc);
				} else {
					pc = (PalabraClave) pc1.clone();
				}
				proyectoActual.adicionarPalabraClave(pc);
				palabraClave.setPalabra("");
				pc1 = null;
				pc = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		palabraClave = new PalabraClave();
	}

	public void eliminarPalabraClave() {
		proyectoActual.borrarPalabraClave(palabraClaveTabla);
		palabraClaveTabla = new PalabraClave();
	}

	public void adicionarDependencia() {

		Dependencia dep = buscarDependencia(dependenciaAdicionada);
		dependenciaAreaResponsabilidad.setDependencia(dep);
		proyectoActual.adicionarDependencia(dependenciaAreaResponsabilidad);
		dependenciaAreaResponsabilidad = new DependenciaAreaResponsabilidad();
	}

	public void eliminarDependencia() {
		// proyectoActual.borrarDependencia((DependenciaAreaResponsabilidad)tablaDependencias.getRowData());
		proyectoActual
				.borrarDependencia(dependenciaAreaResponsabilidadSeleccionada);
	}

	private Dependencia buscarDependencia(String id) {
		// BUSCA UNA DEPENDENCIA DE ACUERDO A SU ID
		Dependencia d = new Dependencia();
		int i = 0;
		while (i < dependenciasUN.size()) {
			d = (Dependencia) dependenciasUN.get(i);
			if (id.equals(d.getId()))
				break;
			i = i + 1;
		}
		return d;
	}

	@Override
	protected void cargarValoresIniciales() {
		

		linkLineas = "http://www.hermes.unal.edu.co/pages/descargas/AreasCiencia.pdf";

		String consulta1 = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
				+ DOMINIO_AREA_CIENCIA + "'  order by dd.descripcion";
		listaAreaCiencia = servicioGeneral.obtenerObjetos(consulta1);

		if (listaAreaCiencia.size() > 0) {
			areaCienciaItems = new SelectItem[listaAreaCiencia.size()];
			for (int i = 0; i < listaAreaCiencia.size(); i++) {
				DominioDetalle dominio = (DominioDetalle) listaAreaCiencia
						.get(i);
				areaCienciaItems[i] = new SelectItem(dominio.getIdentificador()
						.getTipo(), dominio.getDescripcion());
			}
		} else {
			areaCienciaItems = new SelectItem[1];
			areaCienciaItems[0] = new SelectItem("0", " - ");

		}

		dependenciasUN = new ArrayList<Dependencia>();
		dependenciasUN = servicioGeneral
				.obtenerObjetos("select e from Dependencia e");
		dependenciaItem = new SelectItem[dependenciasUN.size() + 1];
		for (int i = 0; i < dependenciasUN.size(); i++) {
			Dependencia dd = (Dependencia) dependenciasUN.get(i);
			dependenciaItem[i] = new SelectItem(dd.getId(), dd.getNombre());
			dd = null;
		}
		dependenciaItem[dependenciasUN.size()] = new SelectItem("--",
				"Por favor seleccione la dependencia");
		dependenciaAdicionada = "--";

		List listaAutores = servicioGeneral
				.obtenerObjetos("select e from TipoInvestigador e where e.tipoModalidad = 'CL'");
		autores = new SelectItem[listaAutores.size()];
		for (int i = 0; i < listaAutores.size(); i++) {
			TipoInvestigador ta = (TipoInvestigador) listaAutores.get(i);
			autores[i] = new SelectItem(ta.getId(), ta.getNombre());
			ta = null;
		}

		listaTipoEvento = new ArrayList<DominioDetalle>();
		listaTipoEvento = servicioGeneral
				.obtenerObjetos("select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
						+ DOMINIO_TIPO_COLECCION + "' order by dd.descripcion");
		tipoEventoItems = new SelectItem[listaTipoEvento.size()];
		for (int i = 0; i < listaTipoEvento.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaTipoEvento.get(i);
			tipoEventoItems[i] = new SelectItem(
					dd.getIdentificador().getTipo(), dd.getDescripcion());
			dd = null;
		}

		listaTipoEntidadExterna = new ArrayList<DominioDetalle>();
		listaTipoEntidadExterna = servicioGeneral
				.obtenerObjetos("select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
						+ DOMINIO_TIPO_ENTIDAD_EXTERNA
						+ "' order by dd.descripcion");
		listaTipoEntidadExternaItems = new SelectItem[listaTipoEntidadExterna
				.size()];
		for (int i = 0; i < listaTipoEntidadExterna.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaTipoEntidadExterna.get(i);
			listaTipoEntidadExternaItems[i] = new SelectItem(
					dd.getDescripcion(), dd.getDescripcion());
			dd = null;
		}

	}

	public void agregarParticipante() {
		
		Iterator it = listaParticipantes.iterator();
		if (documentoCoinv2 != null && !documentoCoinv2.trim().equals("")){
			while(it.hasNext()){
				InvestigadorProyecto in = (InvestigadorProyecto)it.next();
				if(in.getInvestigador().getId().getDocumento().equals(documentoCoinv2)){
					FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_FATAL,
									"Este investigador ya ha sido agregado",
									"Este investigador ya ha sido agregado."));
					return;
				}
			}
		}
		
		Investigador nuevoInvestigador = new Investigador();

		if (documentoCoinv2 != null && !documentoCoinv2.equals("")
				&& !documentoCoinv2.equals(" ")) {

			try {

				if (tipoInvestigador.equals("PIEI")) { // si es docente

					InvestigadorInterno investigadorInterno = servicioPersona
							.obtenerInvestigadorInterno(new IdPersona

							(this.documentoCoinv2, this.tipoDocumentoCoInv2
									.getId()));

					if (investigadorInterno != null
							&& investigadorInterno.getTipoVinculacion().getId()
									.equals("30")) {

						// Agregar participante a la listaparticipante
						InvestigadorProyecto participante = new InvestigadorProyecto();
						participante.setInvestigador(investigadorInterno);
						participante
								.setDedicacionHorasSemana((short) this.dedicacionHorasSemana);
						participante.setFuncion("Docente UN");
						participante.setProyecto(proyectoActual);
						TipoInvestigador ti = new TipoInvestigador();
						ti = (TipoInvestigador) servicioGeneral.obtenerObjeto(
								new TipoInvestigador(), tipoInvestigador);
						participante.setTipo(ti);

						listaParticipantes.add(participante);

					} else { // No se encontró como investigador interno
						// si es estudiante
						FacesContext
								.getCurrentInstance()
								.addMessage(
										null,
										new FacesMessage(
												FacesMessage.SEVERITY_FATAL,
												"Por favor ingresar docentes de la UN.",
												"Por favor ingresar docentes de la UN."));

					}

				} else { // si es otro

					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_FATAL,
									"Por favor ingresar docentes de la UN.",
									"Por favor ingresar docentes de la UN."));

				}

				documentoCoinv2 = "";
				tipoInvestigador = "PIEI";

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_FATAL,
									"Por favor ingrese el número de identificación de la persona a registrar",
									"Por favor ingrese el número de identificación de la persona a registrar"));
		}

	}

	public void eliminarParticipante() {

		System.out.println("id del participante: "
				+ participante.getInvestigador().getId().getDocumento());

		try {

			listaParticipantes.remove(participante);
			proyectoActual.getInvestigadoresProyecto().remove(participante);

			listaParticipantesBorrados.add(participante);

			participante = new InvestigadorProyecto();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void eliminarParticipanteExterno() {

		try {

			listaParticipantesExternos.remove(participanteExternoSeleccionado);
			proyectoActual.getInvestigadoresProyecto().remove(
					participanteExternoSeleccionado.getInvExtProyecto());

			listaParticipantesExternosBorrados
					.add(participanteExternoSeleccionado);

			participanteExternoSeleccionado = new InvestigadorExternoEscuelaInternacional();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void mostrarMensaje(FacesMessage msg, UIComponent component) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (component == null) {
			context.addMessage(null, msg);
		} else {
			context.addMessage(component.getClientId(context), msg);
		}

	}

	private boolean buscarInvestigador(IdPersona id) {
		// BUSCA UN INVESTIGADOR DE ACUERDO A SU ID
		boolean investigadorPresente = false;
		int i = 0;
		List listaInvestigadoresProyecto = listaInvestigadoresVista;// proyectoActual.getListaInvestigadoresProyecto();
		while (i < listaInvestigadoresProyecto.size()) {
			InvestigadorProyectoVista ipv = (InvestigadorProyectoVista) listaInvestigadoresVista
					.get(i);
			InvestigadorProyecto d = ipv.getIp();// (InvestigadorProyecto)listaInvestigadoresProyecto.get(i);
			if (id.getDocumento().equals(
					d.getInvestigador().getId().getDocumento())
					&& id.getTipoDocumento().equals(
							d.getInvestigador().getId().getTipoDocumento())) {
				investigadorPresente = true;
				// errorValidacion =
				// "El investigador ya se encuentra asociado al proyecto";
				break;
			}
			i = i + 1;
		}
		return investigadorPresente;
	}

	public boolean validarNombre_InvPpal() {
		boolean val = true;
		boolean valestud = false;

		if (!yaHayPrincipal()) {
			val = false;
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_FATAL,
									"Por favor registre la información del director del proyecto",
									"Por favor registre la información del director del proyecto"));
		}

		if (this.proyectoActual.getNombre() == null
				|| this.proyectoActual.getNombre().equals("--")
				|| this.proyectoActual.getNombre().equals("")) {
			val = false;
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Por favor registre el título del proyecto",
							"Por favor registre el título del proyecto"));
		}

		return val;
	}

	public void guardarProyectoConvLibro() {
		System.out
				.println("<========== GUARDAR CONVOCATORIA LIBROS ==========>");

		if (proyectoExiste) {

			for (InvestigadorProyecto inv : listaParticipantesBorrados) {
				servicioGeneral.eliminarObjeto(inv);
			}

			servicioProyecto.ingresarProyecto(proyectoActual);
		}

		// áreas de la ciencia
		DominioDetalle arUno = new DominioDetalle();
		List listaDomDetUno = new ArrayList<DominioDetalle>();
		// String consulta1 =
		// "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
		// + DOMINIO_AREA_CIENCIA + "' and dd.identificador.tipo = '" +
		// areaCiencia + "'";
		listaDomDetUno = servicioGeneral
				.obtenerObjetos("select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
						+ DOMINIO_AREA_CIENCIA
						+ "' and dd.identificador.tipo = '" + areaCiencia + "'");
		arUno = (DominioDetalle) listaDomDetUno.get(0);

		AreaTematica arTemUno = new AreaTematica();
		arTemUno.setProyecto(proyectoActual);
		arTemUno.setProyectoAreaTematica(arUno);
		arTemUno.setTipo(1l);

		// áreas de la ciencia
		DominioDetalle arDos = new DominioDetalle();
		List listaDomDetarDos = new ArrayList<DominioDetalle>();
		listaDomDetarDos = servicioGeneral
				.obtenerObjetos("select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
						+ DOMINIO_AREA_CIENCIA
						+ "' and dd.identificador.tipo = '"
						+ areaCienciaSec
						+ "'");
		arDos = (DominioDetalle) listaDomDetarDos.get(0);

		AreaTematica arTemDos = new AreaTematica();
		arTemDos.setProyecto(proyectoActual);
		arTemDos.setProyectoAreaTematica(arDos);
		arTemDos.setTipo(2l);

		Set<AreaTematica> seAt = new HashSet<AreaTematica>();
		seAt.add(arTemUno);
		seAt.add(arTemDos);

		try {
			if (proyectoActual != null && proyectoActual.getId() != null) {
				servicioGeneral
						.eliminar("DELETE HER_PROYECTO_AREA_TEMATICA WHERE PRY_ID = "
								+ proyectoActual.getId());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		proyectoActual.setAreasTematicas(seAt);

		if (listaParticipantes.size() > 0) {
			for (int i = 0; i < listaParticipantes.size(); i++) {
				proyectoActual.adicionarInvestigadorProyecto(listaParticipantes
						.get(i));
			}
		}

		servicioProyecto.ingresarProyecto(proyectoActual);
	}

	public void siHayContrapartida() {
		System.out.println("si hay contrapartida");
		if (esContrapartida.equals("Si")) {
			mostrarContrapartida = true;
		} else {
			mostrarContrapartida = false;
		}
	}

	@Override
	public String atras() {
		sesion.removeAttribute("ManejadorConvocatoriaLibros");

		ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion
				.getAttribute("manejadorMenuFormularios");

		boolean bandera = false;
		sesion.removeAttribute("manejadorMenuFormularios");

		return "misProyectos";
	}

	@Override
	public String salir() {
		
		return null;
	}

	@Override
	public String salirGuardar() {
		if (validarNombre_InvPpal()) {

			if (proyectoActual.getId() != null) {
				// Ing. Wilver Alexander Martínez Martínez -wam²
				// Cambio - Registro de cambios
				Persona personaAux = new Persona();
				personaAux = (Persona) sesion.getAttribute("persona");

				Formulario formulario = new Formulario();
				List listaFormulario = new ArrayList();

				listaFormulario = servicioGeneral
						.obtenerListaObjetos("Formulario where id ='0'");
				formulario = (Formulario) listaFormulario.get(0);

				HistoricoFormularioProyecto historicoFormualrioProyecto = new HistoricoFormularioProyecto();
                historicoFormualrioProyecto.setDocPersona(personaAux.getId().getDocumento());
                historicoFormualrioProyecto.setTipoDocumentoPersona(personaAux.getId().getTipoDocumento());
                historicoFormualrioProyecto.setFormulario(formulario);
                historicoFormualrioProyecto.setProyecto(proyectoActual);
                historicoFormualrioProyecto.setFechaCambio(new Date());
				servicioGeneral.guardarObjeto(historicoFormualrioProyecto);
			}

			guardarConvocatoriaBancoProyectos();
			
			if(validarSiEsEstudianteLiderProyecto(proyectoActual.getId()) && proyectoActual.getId() != null){
          	  enviarCorreoEdicionInfoPryEstLider(proyectoActual.getId());
            }

			sesion.removeAttribute("manejadorFichaMinimaProyectos");

			ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion
					.getAttribute("manejadorMenuFormularios");

			boolean bandera = false;
			sesion.removeAttribute("manejadorMenuFormularios");

			return "misProyectos";

		} else {
			return "";
		}
	}

	@Override
	public String siguiente() {
		if (validarNombre_InvPpal()) {

			String link = "";
			ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion
					.getAttribute("manejadorMenuFormularios");
			boolean bandera = false;
			int pos = 0;
			if (man.getItemProyecto() != null) {
				MenuItem lis[] = man.getMenuItemArray();
				if (lis != null) {
					for (int i = 0; i < lis.length; i++) {
						if (bandera) {
							if (lis[i].isRendered()) {
								sesion.removeAttribute("manejadorMenuFormularios");

								link = lis[i].getOutcome();
								break;
							}
						}

						if (lis[i].getOutcome().equals(
								"irConvocatoriaBancoProyectos")) {
							bandera = true;
						}
						if (lis[i].isRendered()) {
							pos++;
						}
					}
				}
			}

			if ((proyectoActual.getEstadoProyecto().getId()).equals("I")
					&& (pos - 1) == proyectoActual.getFase().intValue()) {
				proyectoActual.setFase(new Integer((proyectoActual.getFase())
						.intValue() + 1));
			}
			// System.out.println("fase "+proyectoActual.getFase().intValue()+"modalidad"+proyectoActual.getModalidad().getId().toString()+"pgd"+(proyectoActual.getPlanGlobalDesarrollo()==null?"nulo:":proyectoActual.getPlanGlobalDesarrollo().getId().toString())+"resumen"+proyectoActual.getResumen()+"duracion"+(proyectoActual.getDuracion()==null?"nulo":String.valueOf(proyectoActual.getDuracion().intValue()))+"valor"+(proyectoActual.getValorSolicitado()==null?"nulo":String.valueOf(proyectoActual.getValorSolicitado().longValue()))+"otros"+(proyectoActual.getOtrosAportes()==null?"nulo":String.valueOf(proyectoActual.getOtrosAportes().longValue()))+"fech"+(proyectoActual.getFechaTentativaInicio()==null?"nulo":proyectoActual.getFechaTentativaInicio().toString()));

			if (proyectoActual.getId() != null) {
				// Ing. Wilver Alexander Martínez Martínez -wam²
				// Cambio - Registro de cambios
				Persona personaAux = new Persona();
				personaAux = (Persona) sesion.getAttribute("persona");

				Formulario formulario = new Formulario();
				List listaFormulario = new ArrayList();

				listaFormulario = servicioGeneral
						.obtenerListaObjetos("Formulario where id ='0'");
				formulario = (Formulario) listaFormulario.get(0);

				HistoricoFormularioProyecto historicoFormualrioProyecto = new HistoricoFormularioProyecto();
                historicoFormualrioProyecto.setDocPersona(personaAux.getId().getDocumento());
                historicoFormualrioProyecto.setTipoDocumentoPersona(personaAux.getId().getTipoDocumento());
                historicoFormualrioProyecto.setFormulario(formulario);
                historicoFormualrioProyecto.setProyecto(proyectoActual);
                historicoFormualrioProyecto.setFechaCambio(new Date());
                servicioGeneral.guardarObjeto(historicoFormualrioProyecto);
			}

			guardarConvocatoriaBancoProyectos();
			
			if(validarSiEsEstudianteLiderProyecto(proyectoActual.getId()) && proyectoActual.getId() != null){
          	  enviarCorreoEdicionInfoPryEstLider(proyectoActual.getId());
            }

			return "irActividades";

		} else {
			return "";
		}
	}

	public void agregarEntidadExterna() {

		if (this.nombreEntidadExterna != null && this.nitEntidadExterna != null
				&& this.nombreContactpEntidadExterna != null
				&& this.cargoPersonaEntidadExterna != null
				&& this.alcanceEntidadExterna != null
				&& !this.nombreEntidadExterna.trim().equals("")
				&& !this.nitEntidadExterna.trim().equals("")
				&& !this.nombreContactpEntidadExterna.trim().equals("")
				&& !this.cargoPersonaEntidadExterna.trim().equals("")
				&& !this.alcanceEntidadExterna.trim().equals("")) {
			EntidadesExternasConvocatoriaBancoProyectos ee = new EntidadesExternasConvocatoriaBancoProyectos(
					this.nombreEntidadExterna, this.nitEntidadExterna,
					this.tipoEntidadExterna, this.nombreContactpEntidadExterna,
					this.cargoPersonaEntidadExterna,
					this.alcanceEntidadExterna, this.esContrapartida, this.contraEfectivo,
					this.contraEspecie);
			listaEntidadesExternas.add(ee);
			this.nombreEntidadExterna = "";
			this.nitEntidadExterna = "";
			// this.tipoEntidadExterna = "";
			this.nombreContactpEntidadExterna = "";
			this.cargoPersonaEntidadExterna = "";
			this.alcanceEntidadExterna = "";
			this.esContrapartida = "No";
			this.contraEfectivo = 0L;
			this.contraEspecie = 0L;
			this.mostrarContrapartida = false;
		} else {
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_FATAL,
									"Por favor ingrese los campos necesarios para registrar una entidad externa.",
									"Por favor ingrese los campos necesarios para registrar una entidad externa."));
		}

	}

	public void eliminarEntidadExterna() {
		listaEntidadesExternas.remove(entidadExternaSeleccionada);
		entidadExternaSeleccionada = new EntidadesExternasConvocatoriaBancoProyectos();
	}

	public class EntidadesExternasConvocatoriaBancoProyectos {

		private String nombre;
		private String nit;
		private String tipoEntidad;
		private String nombrePersonaContacto;
		private String cargoPersonaContacto;
		private String alcanceColaboracion;
		private String contrapartida;
		private Long efectivo;
		private Long especie;

	
		public EntidadesExternasConvocatoriaBancoProyectos(String nombre,
				String nit, String tipoEntidad, String nombrePersonaContacto,
				String cargoPersonaContacto, String alcanceColaboracion,
				String contrapartida, Long efectivo, Long especie) {
			super();
			this.nombre = nombre;
			this.nit = nit;
			this.tipoEntidad = tipoEntidad;
			this.nombrePersonaContacto = nombrePersonaContacto;
			this.cargoPersonaContacto = cargoPersonaContacto;
			this.alcanceColaboracion = alcanceColaboracion;
			this.contrapartida = contrapartida;
			this.efectivo = efectivo;
			this.especie = especie;
		}

		public EntidadesExternasConvocatoriaBancoProyectos() {

		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getNit() {
			return nit;
		}

		public void setNit(String nit) {
			this.nit = nit;
		}

		public String getTipoEntidad() {
			return tipoEntidad;
		}

		public void setTipoEntidad(String tipoEntidad) {
			this.tipoEntidad = tipoEntidad;
		}

		public String getNombrePersonaContacto() {
			return nombrePersonaContacto;
		}

		public void setNombrePersonaContacto(String nombrePersonaContacto) {
			this.nombrePersonaContacto = nombrePersonaContacto;
		}

		public String getCargoPersonaContacto() {
			return cargoPersonaContacto;
		}

		public void setCargoPersonaContacto(String cargoPersonaContacto) {
			this.cargoPersonaContacto = cargoPersonaContacto;
		}

		public String getAlcanceColaboracion() {
			return alcanceColaboracion;
		}

		public void setAlcanceColaboracion(String alcanceColaboracion) {
			this.alcanceColaboracion = alcanceColaboracion;
		}

		public Long getEfectivo() {
			return efectivo;
		}

		public void setEfectivo(Long efectivo) {
			this.efectivo = efectivo;
		}

		public Long getEspecie() {
			return especie;
		}

		public void setEspecie(Long especie) {
			this.especie = especie;
		}

		public String getContrapartida() {
			return contrapartida;
		}

		public void setContrapartida(String contrapartida) {
			this.contrapartida = contrapartida;
		}

	}

	public class InvestigadorExternoEscuelaInternacional {

		private InvestigadorExterno invExt;
		private InvestigadorProyecto invExtProyecto;
		private String nombrePais;
		private String nombreInstitucion;

		public InvestigadorExterno getInvExt() {
			return invExt;
		}

		public void setInvExt(InvestigadorExterno invExt) {
			this.invExt = invExt;
		}

		public InvestigadorProyecto getInvExtProyecto() {
			return invExtProyecto;
		}

		public void setInvExtProyecto(InvestigadorProyecto invExtProyecto) {
			this.invExtProyecto = invExtProyecto;
		}

		public String getNombrePais() {

			try {
				Pais p = new Pais();
				String idPais = "";
				if (invExt.getPaisOrigen() != null) {
					idPais = invExt.getPaisOrigen().trim();
				}
				List listaPais = servicioGeneral
						.obtenerObjetos("select e from Pais e where e.id ='"
								+ invExt.getPaisOrigen().trim() + "'");
				System.out.println("esssssssssssssss:   "
						+ "select e from Pais e where e.id ='" + idPais + "'");
				if (listaPais.size() > 0) {
					p = (Pais) listaPais.get(0);
					nombrePais = p.getNombre();
				} else {
					nombrePais = "";
				}
			} catch (Exception e) {
				nombrePais = "";
			}

			return nombrePais;
		}

		public void setNombrePais(String nombrePais) {
			this.nombrePais = nombrePais;
		}

		public String getNombreInstitucion() {
			try {
				List listaInst = servicioGeneral
						.obtenerObjetos("select e from Institucion e where e.id = '"
								+ invExt.getInstitucion().getId() + "'");
				Institucion ins = new Institucion();
				if (listaInst != null && listaInst.size() > 0) {
					ins = (Institucion) listaInst.get(0);
				}

				nombreInstitucion = ins.getNombre();
			} catch (Exception e) {
				nombreInstitucion = "";
			}

			return nombreInstitucion;
		}

		public void setNombreInstitucion(String nombreInstitucion) {
			this.nombreInstitucion = nombreInstitucion;
		}

	}

	public String guardarEntidadesExternas() {
		String cad = "";

		for (int i = 0; i < listaEntidadesExternas.size(); i++) {

			EntidadesExternasConvocatoriaBancoProyectos e = (EntidadesExternasConvocatoriaBancoProyectos) listaEntidadesExternas
					.get(i);

			cad += "Nombre entidad: " + e.getNombre().replace(" ~ ", "")
					+ " ~ " + "NIT: " + e.getNit().replace(" ~ ", "") + " ~ "
					+ "Tipo entidad: " + e.getTipoEntidad().replace(" ~ ", "")
					+ " ~ " + "Persona contacto: "
					+ e.getNombrePersonaContacto().replace(" ~ ", "") + " ~ "
					+ "Cargo persona contacto: "
					+ e.getCargoPersonaContacto().replace(" ~ ", "") + " ~ "
					+ "Alcance colaboración interinstitucional: "
					+ e.getAlcanceColaboracion().replace(" ~ ", "") + " ~ "
					+ "Tiene contrapartida: "
					+ e.getContrapartida() + " ~ "
					+ "Contrapartida efectivo: "
					+ e.getEfectivo() + " ~ "
					+ "Contrapartida especie: "
					+ e.getEspecie();
			cad += "<===>";

		}

		return cad;
	}

	public void guardarConvocatoriaBancoProyectos() {
		System.out
				.println("<========== GUARDAR CONVOCATORIA BANCO DE PROYECTOS ==========>");
		if (proyectoActual.getId() != null) {

			for (Gasto gasto : listaGastosBorrados) {
				servicioGeneral.eliminarObjeto(gasto);
			}

			for (InvestigadorProyecto inv : listaParticipantesBorrados) {
				servicioGeneral.eliminarObjeto(inv);
			}

			servicioProyecto.ingresarProyecto(proyectoActual);
		}

		for (int i = 0; i < listaResultados.size(); i++) {

			proyectoActual.adicionarResultado(listaResultados.get(i));
		}

		String entiExte = guardarEntidadesExternas();

		if (listaParticipantes.size() > 0) {
			for (int i = 0; i < listaParticipantes.size(); i++) {
				proyectoActual.adicionarInvestigadorProyecto(listaParticipantes
						.get(i));
			}
		}

		if (listaParticipantesExternos.size() > 0) {
			for (int i = 0; i < listaParticipantesExternos.size(); i++) {
				proyectoActual
						.adicionarInvestigadorProyecto(listaParticipantesExternos
								.get(i).getInvExtProyecto());
			}
		}

		proyectoActual.setJustificacion(entiExte);

		Set<Financiacion> setFinanciacionProyecto = new HashSet<Financiacion>();
		for (int i = 0; i < listaFinanciaciones.size(); i++) {
			Financiacion fin = (Financiacion) listaFinanciaciones.get(i);
			fin.setProyecto(proyectoActual);

			Set gastoL = new HashSet();
			gastoL.addAll(listaGastos);

			fin.setGastos(gastoL);
			setFinanciacionProyecto.add(fin);

		}

		proyectoActual.setFinanciaciones(setFinanciacionProyecto);

		servicioProyecto.ingresarProyecto(proyectoActual);
	}

	public Convocatoria getConvocatoriaActual() {
		return convocatoriaActual;
	}

	public void setConvocatoriaActual(Convocatoria convocatoriaActual) {
		this.convocatoriaActual = convocatoriaActual;
	}

	public PalabraClave getPalabraClave() {
		return palabraClave;
	}

	public void setPalabraClave(PalabraClave palabraClave) {
		this.palabraClave = palabraClave;
	}

	public PalabraClave getPalabraClaveTabla() {
		return palabraClaveTabla;
	}

	public void setPalabraClaveTabla(PalabraClave palabraClaveTabla) {
		this.palabraClaveTabla = palabraClaveTabla;
	}

	public PalabraClave getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(PalabraClave keyWord) {
		this.keyWord = keyWord;
	}

	public List<PalabraClave> getListaPalabrasClave() {
		return listaPalabrasClave;
	}

	public void setListaPalabrasClave(List<PalabraClave> listaPalabrasClave) {
		this.listaPalabrasClave = listaPalabrasClave;
	}

	public String getLinkLineas() {
		return linkLineas;
	}

	public void setLinkLineas(String linkLineas) {
		this.linkLineas = linkLineas;
	}

	public String getAreaCiencia() {
		return areaCiencia;
	}

	public void setAreaCiencia(String areaCiencia) {
		this.areaCiencia = areaCiencia;
	}

	public String getAreaCienciaSec() {
		return areaCienciaSec;
	}

	public void setAreaCienciaSec(String areaCienciaSec) {
		this.areaCienciaSec = areaCienciaSec;
	}

	public List getListaAreaCiencia() {
		return listaAreaCiencia;
	}

	public void setListaAreaCiencia(List listaAreaCiencia) {
		this.listaAreaCiencia = listaAreaCiencia;
	}

	public SelectItem[] getAreaCienciaItems() {
		return areaCienciaItems;
	}

	public void setAreaCienciaItems(SelectItem[] areaCienciaItems) {
		this.areaCienciaItems = areaCienciaItems;
	}

	public String getDependenciaAdicionada() {
		return dependenciaAdicionada;
	}

	public void setDependenciaAdicionada(String dependenciaAdicionada) {
		this.dependenciaAdicionada = dependenciaAdicionada;
	}

	public List<Dependencia> getDependenciasUN() {
		return dependenciasUN;
	}

	public void setDependenciasUN(List<Dependencia> dependenciasUN) {
		this.dependenciasUN = dependenciasUN;
	}

	public SelectItem[] getDependenciaItem() {
		return dependenciaItem;
	}

	public void setDependenciaItem(SelectItem[] dependenciaItem) {
		this.dependenciaItem = dependenciaItem;
	}

	public DependenciaAreaResponsabilidad getDependenciaAreaResponsabilidad() {
		return dependenciaAreaResponsabilidad;
	}

	public void setDependenciaAreaResponsabilidad(
			DependenciaAreaResponsabilidad dependenciaAreaResponsabilidad) {
		this.dependenciaAreaResponsabilidad = dependenciaAreaResponsabilidad;
	}

	public DependenciaAreaResponsabilidad getDependenciaAreaResponsabilidadSeleccionada() {
		return dependenciaAreaResponsabilidadSeleccionada;
	}

	public void setDependenciaAreaResponsabilidadSeleccionada(
			DependenciaAreaResponsabilidad dependenciaAreaResponsabilidadSeleccionada) {
		this.dependenciaAreaResponsabilidadSeleccionada = dependenciaAreaResponsabilidadSeleccionada;
	}

	public InvestigadorProyecto getInvestigadorProyectoNuevo() {
		return investigadorProyectoNuevo;
	}

	public void setInvestigadorProyectoNuevo(
			InvestigadorProyecto investigadorProyectoNuevo) {
		this.investigadorProyectoNuevo = investigadorProyectoNuevo;
	}

	public List getListaTipoDocumento() {
		return listaTipoDocumento;
	}

	public void setListaTipoDocumento(List listaTipoDocumento) {
		this.listaTipoDocumento = listaTipoDocumento;
	}

	public SelectItem[] getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}

	public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
		this.tipoDocumentoItem = tipoDocumentoItem;
	}

	public List getListaInvestigadoresVista() {
		return listaInvestigadoresVista;
	}

	public void setListaInvestigadoresVista(List listaInvestigadoresVista) {
		this.listaInvestigadoresVista = listaInvestigadoresVista;
	}

	public boolean isInvestigadorExiste() {
		return investigadorExiste;
	}

	public void setInvestigadorExiste(boolean investigadorExiste) {
		this.investigadorExiste = investigadorExiste;
	}

	public SelectItem[] getAutores() {
		return autores;
	}

	public void setAutores(SelectItem[] autores) {
		this.autores = autores;
	}

	public InvestigadorProyecto getCopiaValidacionInvestigador() {
		return copiaValidacionInvestigador;
	}

	public void setCopiaValidacionInvestigador(
			InvestigadorProyecto copiaValidacionInvestigador) {
		this.copiaValidacionInvestigador = copiaValidacionInvestigador;
	}

	public InvestigadorProyecto getInvestigadorProyectoActual() {
		return investigadorProyectoActual;
	}

	public void setInvestigadorProyectoActual(
			InvestigadorProyecto investigadorProyectoActual) {
		this.investigadorProyectoActual = investigadorProyectoActual;
	}

	public TipoDocumento getTipoDocumentoCoInv2() {
		return tipoDocumentoCoInv2;
	}

	public void setTipoDocumentoCoInv2(TipoDocumento tipoDocumentoCoInv2) {
		this.tipoDocumentoCoInv2 = tipoDocumentoCoInv2;
	}

	public String getDocumentoCoinv2() {
		return documentoCoinv2;
	}

	public void setDocumentoCoinv2(String documentoCoinv2) {
		this.documentoCoinv2 = documentoCoinv2;
	}

	public String getTipoInvestigador() {
		return tipoInvestigador;
	}

	public void setTipoInvestigador(String tipoInvestigador) {
		this.tipoInvestigador = tipoInvestigador;
	}

	public List<InvestigadorProyecto> getListaParticipantes() {
		return listaParticipantes;
	}

	public void setListaParticipantes(
			List<InvestigadorProyecto> listaParticipantes) {
		this.listaParticipantes = listaParticipantes;
	}

	public List<InvestigadorProyecto> getListaParticipantesBorrados() {
		return listaParticipantesBorrados;
	}

	public void setListaParticipantesBorrados(
			List<InvestigadorProyecto> listaParticipantesBorrados) {
		this.listaParticipantesBorrados = listaParticipantesBorrados;
	}

	public InvestigadorExterno getInvestigadorExterno() {
		return investigadorExterno;
	}

	public void setInvestigadorExterno(InvestigadorExterno investigadorExterno) {
		this.investigadorExterno = investigadorExterno;
	}

	public String getInsitucionNombre() {
		return insitucionNombre;
	}

	public void setInsitucionNombre(String insitucionNombre) {
		this.insitucionNombre = insitucionNombre;
	}

	public boolean isEsOtraVinculacion() {
		return esOtraVinculacion;
	}

	public void setEsOtraVinculacion(boolean esOtraVinculacion) {
		this.esOtraVinculacion = esOtraVinculacion;
	}

	public SelectItem[] getGeneroItem() {
		return generoItem;
	}

	public void setGeneroItem(SelectItem[] generoItem) {
		this.generoItem = generoItem;
	}

	public InvestigadorProyecto getParticipante() {
		return participante;
	}

	public void setParticipante(InvestigadorProyecto participante) {
		this.participante = participante;
	}

	public boolean isProyectoExiste() {
		return proyectoExiste;
	}

	public void setProyectoExiste(boolean proyectoExiste) {
		this.proyectoExiste = proyectoExiste;
	}

	public List getListaAreasPrimSec() {
		return listaAreasPrimSec;
	}

	public void setListaAreasPrimSec(List listaAreasPrimSec) {
		this.listaAreasPrimSec = listaAreasPrimSec;
	}

	public SelectItem[] getTipoEventoItems() {
		return tipoEventoItems;
	}

	public void setTipoEventoItems(SelectItem[] tipoEventoItems) {
		this.tipoEventoItems = tipoEventoItems;
	}

	public List<DominioDetalle> getListaTipoEvento() {
		return listaTipoEvento;
	}

	public void setListaTipoEvento(List<DominioDetalle> listaTipoEvento) {
		this.listaTipoEvento = listaTipoEvento;
	}

	public boolean isMostrarOtroTipoEvento() {
		return mostrarOtroTipoEvento;
	}

	public void setMostrarOtroTipoEvento(boolean mostrarOtroTipoEvento) {
		this.mostrarOtroTipoEvento = mostrarOtroTipoEvento;
	}

	public boolean isMostrarSiConvLibros() {
		return mostrarSiConvLibros;
	}

	public void setMostrarSiConvLibros(boolean mostrarSiConvLibros) {
		this.mostrarSiConvLibros = mostrarSiConvLibros;
	}

	public boolean isMostrarSiArticulosUno() {
		return mostrarSiArticulosUno;
	}

	public void setMostrarSiArticulosUno(boolean mostrarSiArticulosUno) {
		this.mostrarSiArticulosUno = mostrarSiArticulosUno;
	}

	public boolean isMostrarSiArticulosDos() {
		return mostrarSiArticulosDos;
	}

	public void setMostrarSiArticulosDos(boolean mostrarSiArticulosDos) {
		this.mostrarSiArticulosDos = mostrarSiArticulosDos;
	}

	public String getDocumentoCoinv() {
		return documentoCoinv;
	}

	public void setDocumentoCoinv(String documentoCoinv) {
		this.documentoCoinv = documentoCoinv;
	}

	public TipoDocumento getTipoDocumentoCoInv() {
		return tipoDocumentoCoInv;
	}

	public void setTipoDocumentoCoInv(TipoDocumento tipoDocumentoCoInv) {
		this.tipoDocumentoCoInv = tipoDocumentoCoInv;
	}

	public String getNombreCompletoPersonaActual() {
		return nombreCompletoPersonaActual;
	}

	public void setNombreCompletoPersonaActual(
			String nombreCompletoPersonaActual) {
		this.nombreCompletoPersonaActual = nombreCompletoPersonaActual;
	}

	public String getNombreSede() {
		return nombreSede;
	}

	public void setNombreSede(String nombreSede) {
		this.nombreSede = nombreSede;
	}

	public String getNombreFacultad() {
		return nombreFacultad;
	}

	public void setNombreFacultad(String nombreFacultad) {
		this.nombreFacultad = nombreFacultad;
	}

	public String getNombreDepartamento() {
		return nombreDepartamento;
	}

	public void setNombreDepartamento(String nombreDepartamento) {
		this.nombreDepartamento = nombreDepartamento;
	}

	public String getEmailPersonaActual() {
		return emailPersonaActual;
	}

	public void setEmailPersonaActual(String emailPersonaActual) {
		this.emailPersonaActual = emailPersonaActual;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public List getListaAreas() {
		return listaAreas;
	}

	public void setListaAreas(List listaAreas) {
		this.listaAreas = listaAreas;
	}

	public Persona getPersonaActual() {
		return personaActual;
	}

	public void setPersonaActual(Persona personaActual) {
		this.personaActual = personaActual;
	}

	public String getDedicacionInvPpal() {
		return dedicacionInvPpal;
	}

	public void setDedicacionInvPpal(String dedicacionInvPpal) {
		this.dedicacionInvPpal = dedicacionInvPpal;
	}

	public TipoDocumento getTipoDocumentoInvExt() {
		return tipoDocumentoInvExt;
	}

	public void setTipoDocumentoInvExt(TipoDocumento tipoDocumentoInvExt) {
		this.tipoDocumentoInvExt = tipoDocumentoInvExt;
	}

	public String getTipoInvestigadorInvExt() {
		return tipoInvestigadorInvExt;
	}

	public void setTipoInvestigadorInvExt(String tipoInvestigadorInvExt) {
		this.tipoInvestigadorInvExt = tipoInvestigadorInvExt;
	}

	public ArrayList<InvestigadorExternoEscuelaInternacional> getListaParticipantesExternos() {
		return listaParticipantesExternos;
	}

	public void setListaParticipantesExternos(
			ArrayList<InvestigadorExternoEscuelaInternacional> listaParticipantesExternos) {
		this.listaParticipantesExternos = listaParticipantesExternos;
	}

	public ArrayList<InvestigadorExternoEscuelaInternacional> getListaParticipantesExternosBorrados() {
		return listaParticipantesExternosBorrados;
	}

	public void setListaParticipantesExternosBorrados(
			ArrayList<InvestigadorExternoEscuelaInternacional> listaParticipantesExternosBorrados) {
		this.listaParticipantesExternosBorrados = listaParticipantesExternosBorrados;
	}

	public String getDocumentoInvExt() {
		return documentoInvExt;
	}

	public void setDocumentoInvExt(String documentoInvExt) {
		this.documentoInvExt = documentoInvExt;
	}

	public SelectItem[] getDocentesUN() {
		return docentesUN;
	}

	public void setDocentesUN(SelectItem[] docentesUN) {
		this.docentesUN = docentesUN;
	}

	public SelectItem[] getDocentesInvitados() {
		return docentesInvitados;
	}

	public void setDocentesInvitados(SelectItem[] docentesInvitados) {
		this.docentesInvitados = docentesInvitados;
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

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public SelectItem[] getPaisItem() {
		return paisItem;
	}

	public void setPaisItem(SelectItem[] paisItem) {
		this.paisItem = paisItem;
	}

	public List<SelectItem> getDepartamentoItemList() {
		return departamentoItemList;
	}

	public void setDepartamentoItemList(List<SelectItem> departamentoItemList) {
		this.departamentoItemList = departamentoItemList;
	}

	public List<SelectItem> getCiudadItemList() {
		return ciudadItemList;
	}

	public void setCiudadItemList(List<SelectItem> ciudadItemList) {
		this.ciudadItemList = ciudadItemList;
	}

	public InvestigadorExternoEscuelaInternacional getParticipanteExternoSeleccionado() {
		return participanteExternoSeleccionado;
	}

	public void setParticipanteExternoSeleccionado(
			InvestigadorExternoEscuelaInternacional participanteExternoSeleccionado) {
		this.participanteExternoSeleccionado = participanteExternoSeleccionado;
	}

	public boolean isMostrarFormIE() {
		return mostrarFormIE;
	}

	public void setMostrarFormIE(boolean mostrarFormIE) {
		this.mostrarFormIE = mostrarFormIE;
	}

	public boolean isMostrarGuardarIE() {
		return mostrarGuardarIE;
	}

	public void setMostrarGuardarIE(boolean mostrarGuardarIE) {
		this.mostrarGuardarIE = mostrarGuardarIE;
	}

	public List getListaFinanciaciones() {
		return listaFinanciaciones;
	}

	public void setListaFinanciaciones(List listaFinanciaciones) {
		this.listaFinanciaciones = listaFinanciaciones;
	}

	public List getListaGastos() {
		return listaGastos;
	}

	public void setListaGastos(List listaGastos) {
		this.listaGastos = listaGastos;
	}

	public List<Gasto> getListaGastosBorrados() {
		return listaGastosBorrados;
	}

	public void setListaGastosBorrados(List<Gasto> listaGastosBorrados) {
		this.listaGastosBorrados = listaGastosBorrados;
	}

	public DataTable getTablaGastos() {
		return tablaGastos;
	}

	public void setTablaGastos(DataTable tablaGastos) {
		this.tablaGastos = tablaGastos;
	}

	public List getListaTiposRubros() {
		return listaTiposRubros;
	}

	public void setListaTiposRubros(List listaTiposRubros) {
		this.listaTiposRubros = listaTiposRubros;
	}

	public Long getIdTipoRubro() {
		return idTipoRubro;
	}

	public void setIdTipoRubro(Long idTipoRubro) {
		this.idTipoRubro = idTipoRubro;
	}

	public Gasto getGastoSeleccionado() {
		return gastoSeleccionado;
	}

	public void setGastoSeleccionado(Gasto gastoSeleccionado) {
		this.gastoSeleccionado = gastoSeleccionado;
	}

	public int getVigenciaGasto() {
		return vigenciaGasto;
	}

	public void setVigenciaGasto(int vigenciaGasto) {
		this.vigenciaGasto = vigenciaGasto;
	}

	public String getDescripcionGasto() {
		return descripcionGasto;
	}

	public void setDescripcionGasto(String descripcionGasto) {
		this.descripcionGasto = descripcionGasto;
	}

	public Gasto getGastoActual() {
		return gastoActual;
	}

	public void setGastoActual(Gasto gastoActual) {
		this.gastoActual = gastoActual;
	}

	public Long getValorGasto() {
		return valorGasto;
	}

	public void setValorGasto(Long valorGasto) {
		this.valorGasto = valorGasto;
	}

	public List getListaFuentesInternas() {
		return listaFuentesInternas;
	}

	public void setListaFuentesInternas(List listaFuentesInternas) {
		this.listaFuentesInternas = listaFuentesInternas;
	}

	public List getListaFuentesExternas() {
		return listaFuentesExternas;
	}

	public void setListaFuentesExternas(List listaFuentesExternas) {
		this.listaFuentesExternas = listaFuentesExternas;
	}

	public List getListaAuxiliarFuentes() {
		return listaAuxiliarFuentes;
	}

	public void setListaAuxiliarFuentes(List listaAuxiliarFuentes) {
		this.listaAuxiliarFuentes = listaAuxiliarFuentes;
	}

	public FuenteFinanciacion getFuenteFinancieraActual() {
		return fuenteFinancieraActual;
	}

	public void setFuenteFinancieraActual(
			FuenteFinanciacion fuenteFinancieraActual) {
		this.fuenteFinancieraActual = fuenteFinancieraActual;
	}

	public Financiacion getFinanciacionActual() {
		return financiacionActual;
	}

	public void setFinanciacionActual(Financiacion financiacionActual) {
		this.financiacionActual = financiacionActual;
	}

	public TipoRubro getTipoRubro() {
		return tipoRubro;
	}

	public void setTipoRubro(TipoRubro tipoRubro) {
		this.tipoRubro = tipoRubro;
	}

	public List getListaRubrosFinanciables() {
		return listaRubrosFinanciables;
	}

	public void setListaRubrosFinanciables(List listaRubrosFinanciables) {
		this.listaRubrosFinanciables = listaRubrosFinanciables;
	}

	public SelectItem[] getTiposRubroItem() {
		return tiposRubroItem;
	}

	public void setTiposRubroItem(SelectItem[] tiposRubroItem) {
		this.tiposRubroItem = tiposRubroItem;
	}

	public Long getIdtipoRubro() {
		return idtipoRubro;
	}

	public void setIdtipoRubro(Long idtipoRubro) {
		this.idtipoRubro = idtipoRubro;
	}

	public Long getIdsubtipoRubro() {
		return idsubtipoRubro;
	}

	public void setIdsubtipoRubro(Long idsubtipoRubro) {
		this.idsubtipoRubro = idsubtipoRubro;
	}

	public String getNombreEntidadExterna() {
		return nombreEntidadExterna;
	}

	public void setNombreEntidadExterna(String nombreEntidadExterna) {
		this.nombreEntidadExterna = nombreEntidadExterna;
	}

	public String getNitEntidadExterna() {
		return nitEntidadExterna;
	}

	public void setNitEntidadExterna(String nitEntidadExterna) {
		this.nitEntidadExterna = nitEntidadExterna;
	}

	public String getTipoEntidadExterna() {
		return tipoEntidadExterna;
	}

	public void setTipoEntidadExterna(String tipoEntidadExterna) {
		this.tipoEntidadExterna = tipoEntidadExterna;
	}

	public String getNombreContactpEntidadExterna() {
		return nombreContactpEntidadExterna;
	}

	public void setNombreContactpEntidadExterna(
			String nombreContactpEntidadExterna) {
		this.nombreContactpEntidadExterna = nombreContactpEntidadExterna;
	}

	public String getCargoPersonaEntidadExterna() {
		return cargoPersonaEntidadExterna;
	}

	public void setCargoPersonaEntidadExterna(String cargoPersonaEntidadExterna) {
		this.cargoPersonaEntidadExterna = cargoPersonaEntidadExterna;
	}

	public String getAlcanceEntidadExterna() {
		return alcanceEntidadExterna;
	}

	public void setAlcanceEntidadExterna(String alcanceEntidadExterna) {
		this.alcanceEntidadExterna = alcanceEntidadExterna;
	}

	public SelectItem[] getListaTipoEntidadExternaItems() {
		return listaTipoEntidadExternaItems;
	}

	public void setListaTipoEntidadExternaItems(
			SelectItem[] listaTipoEntidadExternaItems) {
		this.listaTipoEntidadExternaItems = listaTipoEntidadExternaItems;
	}

	public List<DominioDetalle> getListaTipoEntidadExterna() {
		return listaTipoEntidadExterna;
	}

	public void setListaTipoEntidadExterna(
			List<DominioDetalle> listaTipoEntidadExterna) {
		this.listaTipoEntidadExterna = listaTipoEntidadExterna;
	}

	public ArrayList<EntidadesExternasConvocatoriaBancoProyectos> getListaEntidadesExternas() {
		return listaEntidadesExternas;
	}

	public void setListaEntidadesExternas(
			ArrayList<EntidadesExternasConvocatoriaBancoProyectos> listaEntidadesExternas) {
		this.listaEntidadesExternas = listaEntidadesExternas;
	}

	public EntidadesExternasConvocatoriaBancoProyectos getEntidadExternaSeleccionada() {
		return entidadExternaSeleccionada;
	}

	public void setEntidadExternaSeleccionada(
			EntidadesExternasConvocatoriaBancoProyectos entidadExternaSeleccionada) {
		this.entidadExternaSeleccionada = entidadExternaSeleccionada;
	}

	public int getDedicacionHorasSemana() {
		return dedicacionHorasSemana;
	}

	public void setDedicacionHorasSemana(int dedicacionHorasSemana) {
		this.dedicacionHorasSemana = dedicacionHorasSemana;
	}

	public int getDedicacionHorasSemanaExt() {
		return dedicacionHorasSemanaExt;
	}

	public void setDedicacionHorasSemanaExt(int dedicacionHorasSemanaExt) {
		this.dedicacionHorasSemanaExt = dedicacionHorasSemanaExt;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public List<ResultadoProyecto> getListaResultados() {
		return listaResultados;
	}

	public void setListaResultados(List<ResultadoProyecto> listaResultados) {
		this.listaResultados = listaResultados;
	}

	public DataTable getTablaResultados() {
		return tablaResultados;
	}

	public void setTablaResultados(DataTable tablaResultados) {
		this.tablaResultados = tablaResultados;
	}

	public UIComponent getUiResultado() {
		return uiResultado;
	}

	public void setUiResultado(UIComponent uiResultado) {
		this.uiResultado = uiResultado;
	}

	public ResultadoProyecto getResultadoTabla() {
		return resultadoTabla;
	}

	public void setResultadoTabla(ResultadoProyecto resultadoTabla) {
		this.resultadoTabla = resultadoTabla;
	}

	public String getEsContrapartida() {
		return esContrapartida;
	}

	public void setEsContrapartida(String esContrapartida) {
		this.esContrapartida = esContrapartida;
	}

	public boolean isMostrarContrapartida() {
		return mostrarContrapartida;
	}

	public void setMostrarContrapartida(boolean mostrarContrapartida) {
		this.mostrarContrapartida = mostrarContrapartida;
	}

	public Long getContraEfectivo() {
		return contraEfectivo;
	}

	public void setContraEfectivo(Long contraEfectivo) {
		this.contraEfectivo = contraEfectivo;
	}

	public Long getContraEspecie() {
		return contraEspecie;
	}

	public void setContraEspecie(Long contraEspecie) {
		this.contraEspecie = contraEspecie;
	}

}
