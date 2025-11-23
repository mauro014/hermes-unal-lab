package co.edu.unal.hermes.vista.proyectos.manejadorproyecto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.component.menuitem.MenuItem;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.Departamento;
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
import co.edu.unal.hermes.modelo.ObjetivoEspecifico;
import co.edu.unal.hermes.modelo.Pais;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.ProyectoProducto;
import co.edu.unal.hermes.modelo.RubroFinanciable;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoInvestigador;
import co.edu.unal.hermes.modelo.TipoRubro;
import co.edu.unal.hermes.utils.VariablesEstaticas;
import co.edu.unal.hermes.vista.proyectos.ManejadorMenuFormularios;

public class ManejadorEscuelaInternacional extends ManejadorProyecto {

	private String objetivoEspecifico;
	private List<ObjetivoEspecifico> listaObjetivos;
	private UIComponent objetoEspecifico;
	private ObjetivoEspecifico objetivoTabla;
	private List listaTipoDocumento;
	private SelectItem[] tipoDocumentoItem;
	private TipoDocumento tipoDocumentoCoInv2;
	private String documentoCoinv2;
	private String tipoInvestigador;
	private TipoDocumento tipoDocumentoInvExt;
	private String documentoInvExt;
	private String tipoInvestigadorInvExt;
	private List<InvestigadorProyecto> listaParticipantes;
	private List<InvestigadorProyecto> listaParticipantesBorrados;
	private ArrayList<InvestigadorExternoEscuelaInternacional> listaParticipantesExternos;
	private ArrayList<InvestigadorExternoEscuelaInternacional> listaParticipantesExternosBorrados;

	private InvestigadorProyecto participante = new InvestigadorProyecto();
	private boolean proyectoExiste = false;
	public SelectItem[] docentesUN;
	public SelectItem[] docentesInvitados;
	public List listaInvestigadoresVista;
	private InvestigadorProyecto investigadorProyectoNuevo;

	private boolean mostrarFormIE = false;
	private boolean mostrarGuardarIE = false;

	private InvestigadorExterno investigadorExterno = new InvestigadorExterno();
	private String insitucionNombre;
	private boolean esOtraVinculacion = false;
	private SelectItem[] generoItem = {
			new SelectItem(VariablesEstaticas.GENERO_FEMENINO,
					VariablesEstaticas.GENERO_FEMENINO),
			new SelectItem(VariablesEstaticas.GENERO_MASCULINO,
					VariablesEstaticas.GENERO_MASCULINO) };

	private String pais;
	private String departamento;
	private String ciudad;
	private SelectItem[] paisItem;
	protected List<SelectItem> departamentoItemList;
	protected List<SelectItem> ciudadItemList;
	private InvestigadorExternoEscuelaInternacional participanteExternoSeleccionado;

	private List listaFinanciaciones;
	private Financiacion fuenteSeleccionadaParaRubroI;
	private Financiacion fuenteSeleccionadaParaRubroE;
	private boolean verFuenteExterna = false;
	private boolean verRubros = false;
	private boolean interna = true;
	private String nombreExterna = "";
	private String mensajeExterna = "";
	private String mensajeExternaUno = "";
	private List listaFuentesExternas; // LISTA GENERAL DE FUENTES DE
	private List listaFuentesExternasAdicionadas;
	private boolean banderaExterna = false;
	private List listaFuentesInternas; // LISTA GENERAL DE FUENTES DE
	private String nombreFuenteExterna;
	private FuenteFinanciacion fuenteFinanciacionSeleccionada;
	private Financiacion fuenteSeleccionada;
	private FuenteFinanciacion fuenteExternaSeleccionada;
	private SelectItem[] fuentesFinancierasItem;
	private FuenteFinanciacion fuenteFinancieraActual;
	private List listaAuxiliarFuentes;
	private Gasto gastoActual;
	private Long valorGasto;
	private int vigenciaGasto = 1;
	private String descripcionGasto = "";
	private List listaGastos; // LISTA DE GASTOS ASOCIDAS AL PROYECTO
	private Long idtipoRubro;
	private Gasto gastoSeleccionado;
	private List<Gasto> listaGastosBorrados;
	private List listaTiposRubros;
	private Long idTipoRubro;
	private Convocatoria convocatoriaActual;
	public List listaRubrosFinanciables;
	public SelectItem[] tiposRubroItem;
	private List listaSubRubrosFinanciables;
	private List listaSubRubros;
	public Long idsubtipoRubro;

	public ManejadorEscuelaInternacional() {
		super();
		
		convocatoriaActual = (Convocatoria) servicioGeneral.obtenerObjeto(
				new Convocatoria(), Long.valueOf("437"));
		listaObjetivos = new ArrayList<ObjetivoEspecifico>();
		cargarListas();

		investigadorProyectoNuevo = new InvestigadorProyecto();
		tipoDocumentoCoInv2 = new TipoDocumento();
		tipoDocumentoInvExt = new TipoDocumento();
		listaParticipantes = new ArrayList<InvestigadorProyecto>();
		listaParticipantesExternos = new ArrayList<InvestigadorExternoEscuelaInternacional>();
		listaParticipantesExternosBorrados = new ArrayList<InvestigadorExternoEscuelaInternacional>();
		listaParticipantesBorrados = new ArrayList<InvestigadorProyecto>();
		cargarPaises();

		listaTiposRubros = new ArrayList();
		listaSubRubrosFinanciables = new ArrayList<SelectItem>();
		gastoActual = new Gasto();
		listaFuentesExternas = new ArrayList();
		listaFuentesInternas = new ArrayList();
		listaFuentesExternasAdicionadas = new ArrayList<Financiacion>();
		listaAuxiliarFuentes = new ArrayList();
		listaFinanciaciones = new ArrayList<Financiacion>();
		listaGastos = new ArrayList<Gasto>();
		listaSubRubros = new ArrayList<TipoRubro>();
		listaGastosBorrados = new ArrayList<Gasto>();
		cargarRubrosModalidad();
		cargarListas();
		cargarFuentesFinanciacion();

		

		if (proyectoActual.getId() != null) {

			proyectoActual = servicioProyecto.obtenerProyecto(
					proyectoActual.getId(), ProyectoDAOHibernate.TODO_POR_ID);

			proyectoExiste = true;
			
			listaObjetivos.addAll(proyectoActual.getObjetivosEspecificos());

			listaInvestigadoresVista = proyectoActual
					.getObtenerListaInvestigadoresVista();

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

			// Financiaciones

			//ingresarFuenteEspecieUN();

			List listaFinanciacionesProyecto = new ArrayList();
			listaFinanciacionesProyecto.addAll(proyectoActual
					.getFinanciaciones());

			if (listaFinanciacionesProyecto.size() > 0) {

				for (int j = 0; j < listaFinanciacionesProyecto.size(); j++) {

					Financiacion ff = (Financiacion) listaFinanciacionesProyecto
							.get(j);

					if (ff.getFuente().getInternaExterna() != null) {
						if (ff.getFuente().getInternaExterna()
								.equalsIgnoreCase(FuenteFinanciacion.interna))
							listaFinanciaciones.add(ff);
						else
							listaFuentesExternasAdicionadas.add(ff);
					}
				}

				for (int i = 0; i < listaFinanciacionesProyecto.size(); i++) {
					Financiacion f = new Financiacion();
					f = (Financiacion) listaFinanciacionesProyecto.get(i);

					List gastosList = servicioGeneral
							.obtenerObjetos("select e from Gasto e where e.financiacion.id = "
									+

									f.getId());

					if (gastosList.size() > 0) {
						listaGastos.addAll(gastosList);
					}
				}

			} else {
				cargarFuentesFinanciacion();
				//ingresarFuenteEspecieUN();
			}

		} else {
			ingresarFuenteEspecieUN();
			proyectoExiste = false;
			proyectoActual.cambiarEstadoPersona(EstadoProyecto.INGRESANDO,cargarPersonaActual());
			proyectoActual.setFase(new Integer(0));
			proyectoActual.setDuracion(6);
			proyectoActual.setDescripcion("");
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
		}
	}

	public void ingresarFuenteEspecieUN() {

		Financiacion f = new Financiacion();
		try {
			if (listaFuentesInternas != null && listaFuentesInternas.size() > 0) {
				for (int i = 0; i < listaFuentesInternas.size(); i++) {
					FuenteFinanciacion ff = (FuenteFinanciacion) listaFuentesInternas
							.get(i);
					f.setFuente(ff);
					f.setValor(new Long(0));
					f.setRol("ROL_FT_FIN");
					if (listaFinanciaciones == null) {
						listaFinanciaciones = new ArrayList();
						listaFinanciaciones.add(f);
					} else {
						listaFinanciaciones.add(f);
					}
				}
			} else {
				FuenteFinanciacion ff = (FuenteFinanciacion) servicioGeneral
						.obtenerObjetoXID("FuenteFinanciacion", "1");
				f.setFuente(ff);
				f.setValor(new Long(3000000));
				f.setRol("ROL_FT_FIN");
				if (listaFinanciaciones == null) {
					listaFinanciaciones = new ArrayList();
					listaFinanciaciones.add(f);
				} else {
					listaFinanciaciones.add(f);
				}
			}

		} catch (Exception e) {
			System.out
					.println("ManejadorFichaMinima:asociarFuente:Error hallando la fuente de financiacion especificada");
			e.printStackTrace();
		}

	}

	private void cargarListas() {
		listaTipoDocumento = servicioGeneral
				.obtenerListaObjetos("TipoDocumento");
		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			TipoDocumento td = (TipoDocumento) listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
		}

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

	public void agregarRubroaFuentesI() {
		verRubros = true;
		interna = true;
	}

	public void agregarRubroaFuentesE() {
		verRubros = true;
		interna = false;
	}

	public void cargarFuentesFinanciacion() {
		List listaFinanciacionesDeConvocatoria = servicioModalidad
				.listaModFuenteFinXModalidad(proyectoActual.getModalidad()
						.getId());

		if (listaFinanciacionesDeConvocatoria.size() > 0) {

			for (Iterator it = listaFinanciacionesDeConvocatoria.iterator(); it
					.hasNext();) {
				ModalidadFuenteFinanciacion mff = (ModalidadFuenteFinanciacion) it
						.next();
				FuenteFinanciacion ff = (FuenteFinanciacion) (servicioGeneral
						.obtenerObjeto(new FuenteFinanciacion(),

						mff.getFuenteFinanciacion().getId()));

				if (ff.getInternaExterna() != null) {
					if (ff.getInternaExterna().equalsIgnoreCase(
							FuenteFinanciacion.interna))
						listaFuentesInternas.add(ff);
					else
						listaFuentesExternas.add(ff);
				}
			}

			/*
			 * listaAuxiliarFuentes.addAll(listaFuentesInternas);
			 * listaAuxiliarFuentes.addAll(listaFuentesExternas);
			 */

		}
	}

	public boolean existeFuenteEnSet(List fuentes,
			FuenteFinanciacion fuenteNueva) {
		Iterator it = fuentes.iterator();
		while (it.hasNext()) {
			Object obj = it.next();
			if (obj instanceof ProyectoProducto) {
				if (((FuenteFinanciacion) obj).getDescripcion().toUpperCase()
						.equals(fuenteNueva.getDescripcion().toUpperCase

						())) {
					return true;
				}
			}
		}
		return false;
	}

	public void eliminarGasto() {

		List listaAdicionar = new ArrayList();

		for (int i = 0; i < listaGastos.size(); i++) {
			Gasto g = (Gasto) listaGastos.get(i);

			if (g.getCantidad() == gastoSeleccionado.getCantidad()
					&& g.getVigencia() == gastoSeleccionado.getVigencia()

					&& g.getTipoRubro().getId() == gastoSeleccionado
							.getTipoRubro().getId()
					&& g.getFinanciacion()
							.getFuente()
							.getId()
							.equals(gastoSeleccionado.getFinanciacion()
									.getFuente

									().getId())) {

				/*
				 * Financiacion f = gastoSeleccionado.getFinanciacion();
				 * 
				 * f.getGastos().remove(gastoSeleccionado);
				 */

				listaGastosBorrados.add(gastoSeleccionado);

			} else {

				listaAdicionar.add(g);

			}

		}
		listaGastos = new ArrayList(listaAdicionar);

		gastoSeleccionado = new Gasto();

		/*
		 * FUNCIÓN ANTERIOR EN EL SERVIDOR
		 * 
		 * public void eliminarGasto() { for (int i = 0; i < listaGastos.size();
		 * i++) { Gasto g = (Gasto) listaGastos.get(i); if (g.getCantidad() ==
		 * gastoSeleccionado.getCantidad() && g.getVigencia() ==
		 * gastoSeleccionado.getVigencia() && g.getTipoRubro().getId() ==
		 * gastoSeleccionado .getTipoRubro().getId()) { listaGastos.remove(i);
		 * listaGastosBorrados.add(g); } } }
		 */

	}

	public void agregarNuevaFuentesExterna() {
		this.mensajeExterna = "";
		if (nombreFuenteExterna != null && nombreFuenteExterna.length() > 0) {
			FuenteFinanciacion fuenteNueva = new FuenteFinanciacion();
			fuenteNueva.setInternaExterna("E");
			fuenteNueva.setDescripcion(nombreFuenteExterna.toUpperCase());

			// String hql =
			// "select ff Fuente_financiacion ff where UPPER(ff.FFI_DESCRIPCION) = UPPER('"
			// + nombreFuenteExterna + "')";
			String hql = "select ff from FuenteFinanciacion ff where UPPER(ff.descripcion) = UPPER('"
					+ nombreFuenteExterna +

					"')";
			List lista = servicioGeneral.obtenerObjetos(hql);
			if (lista.size() > 0) {
				this.mensajeExterna = "La fuente ya se encuentra registrada en el sistema. Por favor vaya a la opción de 'Buscar fuente'.";
				FacesContext
						.getCurrentInstance()
						.addMessage(
								"msgs",
								new FacesMessage(
										FacesMessage.SEVERITY_INFO,
										"La fuente ya se encuentra registrada en el sistema. Por favor vaya a la opción de 'Buscar fuente'",
										""));
				banderaExterna = false;
			} else {

				// if(listaFinanciaciones !=null &&
				// listaFinanciaciones.size()>0){
				try { // Financiacion finan = (Financiacion)
						// listaFinanciaciones.get(0);
					servicioGeneral.guardarObjeto(fuenteNueva);

					ModalidadFuenteFinanciacion modalidadFuente = new ModalidadFuenteFinanciacion();

					modalidadFuente.setFuenteFinanciacion(fuenteNueva);
					modalidadFuente.setModalidad(this.proyectoActual
							.getModalidad());
					modalidadFuente.setPorcentaje(new Double("100"));
					this.servicioGeneral.guardarObjeto(modalidadFuente);

					boolean existeFuente = existeFuenteEnSet(
							listaAuxiliarFuentes, fuenteNueva);
					if (!existeFuente) {
						listaAuxiliarFuentes.add(fuenteNueva);
						listaFuentesExternas.add(fuenteNueva);

					}

					fuentesFinancierasItem = new SelectItem[listaFuentesExternas
							.size()];

					for (int i = 0; i < listaFuentesExternas.size(); i++) {
						FuenteFinanciacion ff = (FuenteFinanciacion) listaFuentesExternas
								.get(i);
						fuentesFinancierasItem[i] = new SelectItem(ff.getId(),
								ff.getDescripcion());
						ff = null;
					}
					fuenteFinancieraActual = (FuenteFinanciacion) (((FuenteFinanciacion) (listaFuentesExternas
							.get(0))).clone

					());

					this.mensajeExterna = "La fuente se agrego satisfactoriamente";
					FacesContext.getCurrentInstance().addMessage(
							"msgs",
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"La fuente se agrego satisfactoriamente",
									""));
					banderaExterna = false;

				} catch (CloneNotSupportedException e) {
					System.out.println(e.toString());
				}
				// }

			}
		}
	}

	public void agregarFuentesExternas() {
		// FuenteFinanciacion fuente = (FuenteFinanciacion)
		// listaExternas.get(tablaExternas.getRowIndex());
		boolean banderaExiste = true;

		if (listaFuentesExternasAdicionadas != null
				&& listaFuentesExternasAdicionadas.size() > 0) {
			for (int i = 0; i < listaFuentesExternasAdicionadas.size(); i++) {
				Financiacion actual = (Financiacion) listaFuentesExternasAdicionadas
						.get(i);
				if (fuenteFinanciacionSeleccionada.getId().equals(
						actual.getId())
						|| fuenteFinanciacionSeleccionada.getDescripcion()
								.toUpperCase().equals(actual.getFuente

								().getDescripcion().toUpperCase())) {
					banderaExiste = false;
				}
			}
		}

		if (banderaExiste) {
			try {

				Financiacion f = new Financiacion();

				f.setFuente(fuenteFinanciacionSeleccionada);
				f.setValor(new Long(0));
				f.setRol("ROL_FT_FIN");

				listaFuentesExternasAdicionadas.add(f);
				verFuenteExterna = false;

				banderaExterna = false;
				listaAuxiliarFuentes.add(fuenteFinanciacionSeleccionada);

			} catch (Exception e) {
				System.out.println(e.toString());
			}
		} else {
			this.mensajeExterna = "La fuente ya se encuentra agregada";
			FacesContext.getCurrentInstance().addMessage(
					"msgs",
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"La fuente ya se encuentra agregada", ""));
		}

	}

	public void ingresarFuenteExterna() {
		banderaExterna = true;
	}

	public void consultarFuentesExternas() {
		this.mensajeExterna = "";
		if (nombreExterna != null && nombreExterna.length() > 0) {
			listaFuentesExternas = servicioGeneral
					.obtenerListaObjetos("FuenteFinanciacion where upper(descripcion) like '%"
							+ nombreExterna.toUpperCase() + "%' order by id");
		} else {
			listaFuentesExternas = new ArrayList();
		}

	}

	public void agregarFuenteExterna() {
		System.out.println("hola mundo");
		banderaExterna = true;
		setVerFuenteExterna(true);
	}

	private Financiacion buscarFinanciacion(String id) {
		// BUSCA UNA FINANCIACION POR EL ID
		Financiacion f = null;
		int i = 0;
		List listaFin = new ArrayList<Financiacion>();
		listaFin.addAll(listaFinanciaciones);
		listaFin.addAll(listaFuentesExternasAdicionadas);

		while (i < listaFin.size()) {
			f = (Financiacion) listaFin.get(i);
			if (id.equals(f.getFuente().getId()))
				break;
			i = i + 1;
		}
		return f;
	}

	public void agregarGasto() {

		// this.mensajeRubros = "";
		boolean bandera = true;
		if (bandera) {

			try {
				Financiacion fg = null;

				if (this.interna) {
					fg = buscarFinanciacion(fuenteSeleccionadaParaRubroI
							.getFuente().getId());
				} else {
					fg = buscarFinanciacion(fuenteSeleccionadaParaRubroE
							.getFuente().getId());
				}

				// fg.adicionarGasto(gastoActual);

				gastoActual = new Gasto();
				gastoActual.setValor(valorGasto);
				// Se asigna por defecto 1 a la cantidad y la vigencia
				gastoActual.setCantidad(1);
				gastoActual.setVigencia(vigenciaGasto);
				gastoActual.setDescripcion(descripcionGasto);

				// Financiacion fg = new Financiacion();
				// fg = fuenteSeleccionadaParaRubro;

				System.out
						.println("*********0======================== id de la fuente de fin "
								+ fg.getFuente().getId());
				// TipoRubro tipoRbr = buscarTipoRubro(idsubtipoRubro);
				TipoRubro tipoRbr = buscarTipoRubro(idTipoRubro);
				// gastoActual.getTipoRubro().setId(idTipoRubro);

				gastoActual.setFinanciacion(new Financiacion());
				// gastoActual.setFinanciacion(fg);
				gastoActual.getFinanciacion().setId(fg.getId());
				gastoActual.getFinanciacion().setFuente(fg.getFuente());
				gastoActual.setTipoRubro(new TipoRubro());
				gastoActual.setTipoRubro(tipoRbr);

				listaGastos.add(gastoActual);
				verRubros = false;

				valorGasto = 0L;
				vigenciaGasto = 1;
				descripcionGasto = "";

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private TipoRubro buscarTipoRubro(Long id) {
		// BUSCA EL TIPO DE RUBRO POR EL ID
		TipoRubro tr = new TipoRubro();
		int i = 0;
		while (i < listaTiposRubros.size()) {
			tr = (TipoRubro) listaTiposRubros.get(i);
			if (id.compareTo(tr.getId()) == 0)
				break;
			i = i + 1;
		}
		return tr;
	}

	public void cargarRubrosModalidad() {
		// TODO ESTE CARGUE HAY QUE REVISARLO YA QUE NO TIENE EN CEUNTA LOS
		// RUBROS DE CUANDO
		// LA MODALIDAD NO ES UNA CONVOCATORIA
		try {
			Modalidad mod = servicioModalidad
					.obtenerModalidad(convocatoriaActual.getId());
			listaRubrosFinanciables = servicioModalidad
					.obtenerRubrosFinanciables(mod);

			if (listaRubrosFinanciables != null
					&& listaRubrosFinanciables.size() > 0) {
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
				idtipoRubro = tr.getId();
				tr = null;
			}
			gastoActual.setTipoRubro(new TipoRubro());
			if (listaTiposRubros != null && listaTiposRubros.size() > 0) { // If
				// creado// por // giovanni
				gastoActual.getTipoRubro().setId(
						((TipoRubro) listaTiposRubros.get(0)).getId());
			}
			// }

		} catch (Exception e) {
			System.out
					.println("ManejadorFichaMinima:cargarTiposRubro:Error Cargando Los Tipos de Rubro");
			e.printStackTrace();
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
			participante.setDedicacionHorasSemana(Short.parseShort("0"));
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

	public void agregarParticipante() {
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
						participante.setDedicacionHorasSemana(Short
								.parseShort("0"));
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

	public void insertarObjetivo() {
		if (this.objetivoEspecifico != null
				&& !this.objetivoEspecifico.equals("")) {
			System.out.println("objetivo específico: "
					+ this.objetivoEspecifico);
			ObjetivoEspecifico oe = new ObjetivoEspecifico();
			oe.setNombre(this.objetivoEspecifico);
			listaObjetivos.add(oe);
			this.objetivoEspecifico = "";
		} else {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Por favor escriba el objetivo específico",
					"Por favor escriba el objetivo específico");
			mostrarMensaje(message, objetoEspecifico);
		}

	}

	public void eliminarObjetivo() {
		listaObjetivos.remove(objetivoTabla);
		proyectoActual.borrarObjetivoEspecifico(objetivoTabla);
		objetivoTabla = new ObjetivoEspecifico();
	}

	private void cargarPaises() {
		// JOptionPane.showMessageDialog(null, "Entro CP");
		List listaPaises = servicioGeneral.obtenerListaObjetos("Pais");
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

	@Override
	protected void cargarValoresIniciales() {
		

	}

	public boolean yaHayPrincipal() {
		// if(proyectoActual.getListaInvestigadoresProyecto().size()>0)
		if (listaParticipantes.size() > 0) {
			// for(Iterator
			// it=proyectoActual.getInvestigadoresProyecto().iterator();it.hasNext();)
			for (Iterator it = listaParticipantes.iterator(); it.hasNext();) {
				InvestigadorProyecto ipc = (InvestigadorProyecto) it.next();
				// it.next();
				if (ipc.getTipo().getId().equals(TipoInvestigador.Principal)) {
					return true;
				}
			}
		}
		return false;
		// return
		// servicioProyecto.tienePrincipalProyecto(proyectoActual.getId());
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
							"Por favor registre el nombre del curso",
							"Por favor registre el nombre del curso"));
		}

		return val;
	}

	public void guardarProyectoEscuelaInternacional() {
		System.out
				.println("<========== GUARDAR CONVOCATORIA LIBROS ==========>");

		if (proyectoExiste) {

			for (InvestigadorProyecto inv : listaParticipantesBorrados) {
				servicioGeneral.eliminarObjeto(inv);
			}

			for (int i = 0; i < listaParticipantesExternosBorrados.size(); i++) {
				InvestigadorProyecto inv = listaParticipantesExternosBorrados
						.get(i).getInvExtProyecto();
				servicioGeneral.eliminarObjeto(inv);
			}
			
			for (Gasto gasto : listaGastosBorrados) {
				servicioGeneral.eliminarObjeto(gasto);
			}
			for (InvestigadorProyecto inv : listaParticipantesBorrados) {
				servicioGeneral.eliminarObjeto(inv);
			}
			

			servicioProyecto.ingresarProyecto(proyectoActual);
		}

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
		
		for (int i = 0; i < listaObjetivos.size(); i++) {

			proyectoActual.adicionarObjetivoEspecifico(listaObjetivos.get(i));
		}
		
		//Financiaciones
		List<Financiacion> listaFinanciacionesComplete = new ArrayList<Financiacion>();

		listaFinanciacionesComplete.addAll(listaFinanciaciones);
		listaFinanciacionesComplete.addAll(listaFuentesExternasAdicionadas);

		for (int j = 0; j < listaFinanciacionesComplete.size(); j++) {
			Financiacion fin = listaFinanciacionesComplete.get(j);
			fin.setGastos(new HashSet());
			for (int i = 0; i < listaGastos.size(); i++) {
				Gasto gastoSel = (Gasto) listaGastos.get(i);

				if (fin.getFuente().getId()
						.equals(gastoSel.getFinanciacion().getFuente().getId())) {
					listaFinanciacionesComplete.get(j).adicionarGasto(gastoSel);
				}
			}

			proyectoActual.adicionarFinanciacion(fin);
		}
		
		

		servicioProyecto.ingresarProyecto(proyectoActual);

	}

	@Override
	public String atras() {
		sesion.removeAttribute("ManejadorEscuelaInternacional");

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

			guardarProyectoEscuelaInternacional();
			
			if(validarSiEsEstudianteLiderProyecto(proyectoActual.getId()) && proyectoActual.getId() != null){
          	  enviarCorreoEdicionInfoPryEstLider(proyectoActual.getId());
            }

			sesion.removeAttribute("ManejadorEscuelaInternacional");

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
								"irConvocatoriaEscuelaInternacional")) {
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

			guardarProyectoEscuelaInternacional();
			
			if(validarSiEsEstudianteLiderProyecto(proyectoActual.getId()) && proyectoActual.getId() != null){
          	  enviarCorreoEdicionInfoPryEstLider(proyectoActual.getId());
            }

			return "irInformacionEspecifica";

		} else {
			return "";
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
			Pais p = new Pais();
			String idPais = "";
			if(invExt.getPaisOrigen() != null){
				idPais = invExt.getPaisOrigen().trim() ;
			}
			List listaPais = servicioGeneral
					.obtenerObjetos("select e from Pais e where e.id ='"
							+ invExt.getPaisOrigen().trim() + "'");
			System.out.println("esssssssssssssss:   " + "select e from Pais e where e.id ='"
					+ idPais + "'");
			if (listaPais.size() > 0) {
				p = (Pais) listaPais.get(0);
				nombrePais = p.getNombre();
			} else {
				nombrePais = "";
			}

			return nombrePais;
		}

		public void setNombrePais(String nombrePais) {
			this.nombrePais = nombrePais;
		}

		public String getNombreInstitucion() {

			List listaInst = servicioGeneral
					.obtenerObjetos("select e from Institucion e where e.id = '"
							+ invExt.getInstitucion().getId() + "'");
			Institucion ins = new Institucion();
			if (listaInst != null && listaInst.size() > 0) {
				ins = (Institucion) listaInst.get(0);
			}

			nombreInstitucion = ins.getNombre();

			return nombreInstitucion;
		}

		public void setNombreInstitucion(String nombreInstitucion) {
			this.nombreInstitucion = nombreInstitucion;
		}

	}

	public String getObjetivoEspecifico() {
		return objetivoEspecifico;
	}

	public void setObjetivoEspecifico(String objetivoEspecifico) {
		this.objetivoEspecifico = objetivoEspecifico;
	}

	public List<ObjetivoEspecifico> getListaObjetivos() {
		return listaObjetivos;
	}

	public void setListaObjetivos(List<ObjetivoEspecifico> listaObjetivos) {
		this.listaObjetivos = listaObjetivos;
	}

	public UIComponent getObjetoEspecifico() {
		return objetoEspecifico;
	}

	public void setObjetoEspecifico(UIComponent objetoEspecifico) {
		this.objetoEspecifico = objetoEspecifico;
	}

	public ObjetivoEspecifico getObjetivoTabla() {
		return objetivoTabla;
	}

	public void setObjetivoTabla(ObjetivoEspecifico objetivoTabla) {
		this.objetivoTabla = objetivoTabla;
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

	public List getListaInvestigadoresVista() {
		return listaInvestigadoresVista;
	}

	public void setListaInvestigadoresVista(List listaInvestigadoresVista) {
		this.listaInvestigadoresVista = listaInvestigadoresVista;
	}

	public InvestigadorProyecto getInvestigadorProyectoNuevo() {
		return investigadorProyectoNuevo;
	}

	public void setInvestigadorProyectoNuevo(
			InvestigadorProyecto investigadorProyectoNuevo) {
		this.investigadorProyectoNuevo = investigadorProyectoNuevo;
	}

	public TipoDocumento getTipoDocumentoInvExt() {
		return tipoDocumentoInvExt;
	}

	public void setTipoDocumentoInvExt(TipoDocumento tipoDocumentoInvExt) {
		this.tipoDocumentoInvExt = tipoDocumentoInvExt;
	}

	public String getDocumentoInvExt() {
		return documentoInvExt;
	}

	public void setDocumentoInvExt(String documentoInvExt) {
		this.documentoInvExt = documentoInvExt;
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

	public ArrayList<InvestigadorExternoEscuelaInternacional> getListaParticipantesExternosBorrados() {
		return listaParticipantesExternosBorrados;
	}

	public void setListaParticipantesExternosBorrados(
			ArrayList<InvestigadorExternoEscuelaInternacional> listaParticipantesExternosBorrados) {
		this.listaParticipantesExternosBorrados = listaParticipantesExternosBorrados;
	}

	public List getListaFinanciaciones() {
		return listaFinanciaciones;
	}

	public void setListaFinanciaciones(List listaFinanciaciones) {
		this.listaFinanciaciones = listaFinanciaciones;
	}

	public Financiacion getFuenteSeleccionadaParaRubroI() {
		return fuenteSeleccionadaParaRubroI;
	}

	public void setFuenteSeleccionadaParaRubroI(
			Financiacion fuenteSeleccionadaParaRubroI) {
		this.fuenteSeleccionadaParaRubroI = fuenteSeleccionadaParaRubroI;
	}

	public Financiacion getFuenteSeleccionadaParaRubroE() {
		return fuenteSeleccionadaParaRubroE;
	}

	public void setFuenteSeleccionadaParaRubroE(
			Financiacion fuenteSeleccionadaParaRubroE) {
		this.fuenteSeleccionadaParaRubroE = fuenteSeleccionadaParaRubroE;
	}

	public boolean isVerFuenteExterna() {
		return verFuenteExterna;
	}

	public void setVerFuenteExterna(boolean verFuenteExterna) {
		this.verFuenteExterna = verFuenteExterna;
	}

	public boolean isVerRubros() {
		return verRubros;
	}

	public void setVerRubros(boolean verRubros) {
		this.verRubros = verRubros;
	}

	public boolean isInterna() {
		return interna;
	}

	public void setInterna(boolean interna) {
		this.interna = interna;
	}

	public String getNombreExterna() {
		return nombreExterna;
	}

	public void setNombreExterna(String nombreExterna) {
		this.nombreExterna = nombreExterna;
	}

	public String getMensajeExterna() {
		return mensajeExterna;
	}

	public void setMensajeExterna(String mensajeExterna) {
		this.mensajeExterna = mensajeExterna;
	}

	public String getMensajeExternaUno() {
		return mensajeExternaUno;
	}

	public void setMensajeExternaUno(String mensajeExternaUno) {
		this.mensajeExternaUno = mensajeExternaUno;
	}

	public List getListaFuentesExternas() {
		return listaFuentesExternas;
	}

	public void setListaFuentesExternas(List listaFuentesExternas) {
		this.listaFuentesExternas = listaFuentesExternas;
	}

	public List getListaFuentesExternasAdicionadas() {
		return listaFuentesExternasAdicionadas;
	}

	public void setListaFuentesExternasAdicionadas(
			List listaFuentesExternasAdicionadas) {
		this.listaFuentesExternasAdicionadas = listaFuentesExternasAdicionadas;
	}

	public boolean isBanderaExterna() {
		return banderaExterna;
	}

	public void setBanderaExterna(boolean banderaExterna) {
		this.banderaExterna = banderaExterna;
	}

	public List getListaFuentesInternas() {
		return listaFuentesInternas;
	}

	public void setListaFuentesInternas(List listaFuentesInternas) {
		this.listaFuentesInternas = listaFuentesInternas;
	}

	public String getNombreFuenteExterna() {
		return nombreFuenteExterna;
	}

	public void setNombreFuenteExterna(String nombreFuenteExterna) {
		this.nombreFuenteExterna = nombreFuenteExterna;
	}

	public FuenteFinanciacion getFuenteFinanciacionSeleccionada() {
		return fuenteFinanciacionSeleccionada;
	}

	public void setFuenteFinanciacionSeleccionada(
			FuenteFinanciacion fuenteFinanciacionSeleccionada) {
		this.fuenteFinanciacionSeleccionada = fuenteFinanciacionSeleccionada;
	}

	public Financiacion getFuenteSeleccionada() {
		return fuenteSeleccionada;
	}

	public void setFuenteSeleccionada(Financiacion fuenteSeleccionada) {
		this.fuenteSeleccionada = fuenteSeleccionada;
	}

	public FuenteFinanciacion getFuenteExternaSeleccionada() {
		return fuenteExternaSeleccionada;
	}

	public void setFuenteExternaSeleccionada(
			FuenteFinanciacion fuenteExternaSeleccionada) {
		this.fuenteExternaSeleccionada = fuenteExternaSeleccionada;
	}

	public SelectItem[] getFuentesFinancierasItem() {
		return fuentesFinancierasItem;
	}

	public void setFuentesFinancierasItem(SelectItem[] fuentesFinancierasItem) {
		this.fuentesFinancierasItem = fuentesFinancierasItem;
	}

	public FuenteFinanciacion getFuenteFinancieraActual() {
		return fuenteFinancieraActual;
	}

	public void setFuenteFinancieraActual(
			FuenteFinanciacion fuenteFinancieraActual) {
		this.fuenteFinancieraActual = fuenteFinancieraActual;
	}

	public List getListaAuxiliarFuentes() {
		return listaAuxiliarFuentes;
	}

	public void setListaAuxiliarFuentes(List listaAuxiliarFuentes) {
		this.listaAuxiliarFuentes = listaAuxiliarFuentes;
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

	public List getListaGastos() {
		return listaGastos;
	}

	public void setListaGastos(List listaGastos) {
		this.listaGastos = listaGastos;
	}

	public Long getIdtipoRubro() {
		return idtipoRubro;
	}

	public void setIdtipoRubro(Long idtipoRubro) {
		this.idtipoRubro = idtipoRubro;
	}

	public Gasto getGastoSeleccionado() {
		return gastoSeleccionado;
	}

	public void setGastoSeleccionado(Gasto gastoSeleccionado) {
		this.gastoSeleccionado = gastoSeleccionado;
	}

	public List<Gasto> getListaGastosBorrados() {
		return listaGastosBorrados;
	}

	public void setListaGastosBorrados(List<Gasto> listaGastosBorrados) {
		this.listaGastosBorrados = listaGastosBorrados;
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

	public Convocatoria getConvocatoriaActual() {
		return convocatoriaActual;
	}

	public void setConvocatoriaActual(Convocatoria convocatoriaActual) {
		this.convocatoriaActual = convocatoriaActual;
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

	public List getListaSubRubrosFinanciables() {
		return listaSubRubrosFinanciables;
	}

	public void setListaSubRubrosFinanciables(List listaSubRubrosFinanciables) {
		this.listaSubRubrosFinanciables = listaSubRubrosFinanciables;
	}

	public List getListaSubRubros() {
		return listaSubRubros;
	}

	public void setListaSubRubros(List listaSubRubros) {
		this.listaSubRubros = listaSubRubros;
	}

	public Long getIdsubtipoRubro() {
		return idsubtipoRubro;
	}

	public void setIdsubtipoRubro(Long idsubtipoRubro) {
		this.idsubtipoRubro = idsubtipoRubro;
	}

}
