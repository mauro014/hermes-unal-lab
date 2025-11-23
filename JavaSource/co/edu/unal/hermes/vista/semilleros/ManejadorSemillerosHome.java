package co.edu.unal.hermes.vista.semilleros;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.PersonaRol;
import co.edu.unal.hermes.modelo.ProductoTipo;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.Semillero;
import co.edu.unal.hermes.modelo.SemilleroActividad;
import co.edu.unal.hermes.modelo.SemilleroEstado;
import co.edu.unal.hermes.modelo.SemilleroInforme;
import co.edu.unal.hermes.modelo.SemilleroInformeActividad;
import co.edu.unal.hermes.modelo.SemilleroInformeArchivo;
import co.edu.unal.hermes.modelo.SemilleroInformeResultado;
import co.edu.unal.hermes.modelo.SemilleroIntegrante;
import co.edu.unal.hermes.modelo.SemilleroProyecto;
import co.edu.unal.hermes.modelo.SemilleroResultado;
import co.edu.unal.hermes.modelo.SemilleroSolicitud;
import co.edu.unal.hermes.modelo.SemilleroSolicitudArchivo;
import co.edu.unal.hermes.modelo.SemilleroSolicitudRespuesta;
import co.edu.unal.hermes.modelo.SemilleroSolicitudTipo;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorSemillerosHome extends ManejadorBase {

	private static final long serialVersionUID = 1L;
	private List<Semillero> listaSemilleros;
	private Semillero semilleroActual;
	private ArrayList<SelectItem> listaSolicitudes;
	private String msgDialog;
	private Boolean solicitudNueva;
	private SemilleroSolicitud solicitudActual;
	private ArrayList<SelectItem> listaEstadosDisponiblesSolicitud;
	private Integer nuevoEstadoId;
	private String[] cambiosContenido;
	private String tipoDocNuevoLider;
	private String documentoNuevoLider;
	private SelectItem[] tipoDocumentoItem;
	private ArrayList<SelectItem> listaCambiosContenido;
	private UploadedFile archivoSeleccionado;
	private ArrayList<UploadedFile> listaArchivos;
	private SemilleroSolicitudArchivo archivo;
	private SemilleroInforme informeActual;
	private ArrayList<SelectItem> listaPorcentajes;
	private SemilleroInformeResultado resultadoActual;
	private String prdNivel1;
	private String prdNivel2;
	private String prdNivel3;
	private SelectItem[] prdNivel1Item;
	private SelectItem[] prdNivel2Item;
	private SelectItem[] prdNivel3Item;
	private ArrayList<SelectItem> listaResultados;
	private String resultado;
	private ArrayList<SelectItem> listaProyectos;
	private String[] proyectosSeleccionados;
	private SemilleroInformeArchivo archivoInf;
	private ArrayList<SelectItem> listaNuevosProyectos;
	private String idProyecto;
	private boolean esConsulta;
	private Integer idInforme;
	private Long idSolicitudSeleccionada;

	public ManejadorSemillerosHome() {
		sesion.removeAttribute("manejadorSemilleroGeneral");
		sesion.removeAttribute("manejadorSemilleroIntegrantes");
		sesion.removeAttribute("manejadorSemilleroLineasAreas");
		sesion.removeAttribute("manejadorSemilleroPlanTrabajo");
		sesion.removeAttribute("manejadorSemilleroArchivosEnviar");
		sesion.removeAttribute("consultaSemillero");
		sesion.removeAttribute("semillero");
		init();
	}
	
	public String atras() {
		sesion.removeAttribute("manejadorSemilleroHome");
		return "irSemillerosHome";
	}

	public void guardarInforme() {
		informeActual.setProyectos("");
		informeActual.setRespuesta(servicioGeneral.obtenerObjetoXID(SemilleroSolicitudRespuesta.class, "1").get(0));
		if (proyectosSeleccionados != null) {
			for (String idProyecto : proyectosSeleccionados) {
				informeActual.setProyectos(informeActual.getProyectos() + idProyecto + ",");
			}
			if (!informeActual.getProyectos().isEmpty()) {
				informeActual.setProyectos(
						informeActual.getProyectos().substring(0, informeActual.getProyectos().length() - 1));
			}
		}
		informeActual.setGuardadoParcial("S");
		servicioGeneral.guardarObjeto(informeActual);
		guardarHistoricoInformeSemilleros(informeActual,"Edición por parte del director(a)");
		FacesContext.getCurrentInstance().addMessage("msgsModal",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Informe guardado Correctamente.", null));
	}

	public void enviarInforme() {
		if (validarInforme()) {
			informeActual.setProyectos("");
			informeActual.setRespuesta(servicioGeneral.obtenerObjetoXID(SemilleroSolicitudRespuesta.class, "1").get(0));
			if (proyectosSeleccionados != null && proyectosSeleccionados.length != 0) {
				for (String idProyecto : proyectosSeleccionados) {
					informeActual.setProyectos(informeActual.getProyectos() + idProyecto + ",");
				}
			}
			if (!informeActual.getProyectos().isEmpty()) {
				informeActual.setProyectos(
						informeActual.getProyectos().substring(0, informeActual.getProyectos().length() - 1));
			}
			informeActual.setFechaEntrega(getToday());
			informeActual.setGuardadoParcial("N");
			servicioGeneral.guardarObjeto(informeActual);
			guardarHistoricoInformeSemilleros(informeActual,"Envio informe para revisión");
			CorreoPlantilla cp = cargarPlantilla(361);
			Correo mail = editarCorreo(cp);
			mail.adicionarDireccion(
					(servicioPersona.obtenerInvestigador(new IdPersona(semilleroActual.getDocumentoCoordinador(),
							semilleroActual.getTipoDocumentoCoordinador())).getEmail()));
			if (!mail.getDirecciones().isEmpty()) {
				servicioCorreo.enviarCorreo(mail);
			}
			FacesContext.getCurrentInstance().addMessage("msgsModal",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Informe Enviado Correctamente.", null));
		}
	}

	private boolean validarInforme() {
		boolean returnValue = true;
		if (informeActual.getFechaDesde() == null) {
			FacesContext.getCurrentInstance().addMessage("msgsModal", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Ingrese la fecha desde que contempla el informe.", null));
			returnValue = false;
		} else if (informeActual.getFechaHasta() == null) {
			FacesContext.getCurrentInstance().addMessage("msgsModal", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Ingrese la fecha hasta que contempla el informe.", null));
			returnValue = false;
		}
		if(informeActual.getListaActividades()!=null && informeActual.getListaActividades().size()>0) {
			for(int i = 0; i<informeActual.getListaActividades().size(); i++) {
				if(esCadenaVacia(informeActual.getListaActividades().get(i).getAvance())) {
					FacesContext.getCurrentInstance().addMessage("msgsModal", new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Debe ingresar la justificación del avance de la actividad incluso si este es de cero.", null));
					returnValue = false;
					break;
				}
			}
		}
		if (!returnValue) {

		}
		return returnValue;
	}

	public void descargarArchivoInf() {
		try {
			descargarArchivoGenerico("HER_SEMILLERO_INF_ARCHIVO", archivoInf.getId().toString(),
					archivoInf.getNombre());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void eliminarArchivoInf() {
		Integer id = archivoInf.getId();
		String ruta = "HER_SEMILLERO_INF_ARCHIVO" + "//" + id;
		if (eliminarArchivoGenerico(ruta)) {
			informeActual.getArchivos().remove(archivoInf);
			servicioGeneral.eliminarObjeto(archivoInf);
		}
	}

	public void agregarArchivoInf(FileUploadEvent event) {
		setArchivoSeleccionado(event.getFile());
		cargarArchivoInf();
	}

	private void cargarArchivoInf() {
		SemilleroInformeArchivo ia = new SemilleroInformeArchivo();
		ia.setNombre(archivoSeleccionado.getFileName());
		ia.setInforme(informeActual);
		servicioGeneral.guardarObjeto(ia);
		cargarArchivoDisco(archivoSeleccionado, "HER_SEMILLERO_INF_ARCHIVO", ia.getId().toString());
		informeActual.getArchivos().add(ia);
	}

	public void eliminarResultado() {
		informeActual.getResultados().remove(resultadoActual);
		resultadoActual = new SemilleroInformeResultado();
		resultadoActual.setProteccion("SI");
	}

	public void agregarResultadoNuevo() {
		msgDialog = "";
		if (esCadenaVacia(resultadoActual.getNombreProducto())) {
			msgDialog = "Por favor, ingrese el nombre del producto.";
		}
		if (esCadenaVacia(resultadoActual.getAvanceResultado())) {
			msgDialog = "Por favor, ingrese el avance del resultado.";
		}
		if (resultadoActual.getFechaEntrega() == null) {
			msgDialog = "Por favor, ingrese la fecha de entrega del producto.";
		}
		if (!esCadenaVacia(msgDialog)) {
			FacesContext.getCurrentInstance().addMessage("msgsModal",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, msgDialog, null));
			return;
		} else {
			for (SemilleroResultado item : getSemilleroActual().getListaResultados()) {
				if (item.getResultado().equals(resultado)) {
					resultadoActual.setResultado(item);
					break;
				}
			}
			resultadoActual.setTipoProducto(prdNivel3);
			resultadoActual.setProducto(servicioGeneral.obtenerObjetoXID(ProductoTipo.class, prdNivel3).get(0));
			for (SelectItem item : prdNivel3Item) {
				if (item.getValue().equals(prdNivel3)) {
					resultadoActual.setNombreTipoProducto(item.getLabel());
					break;
				}
			}
			informeActual.getResultados().add(resultadoActual);
			resultadoActual = new SemilleroInformeResultado();
			resultadoActual.setProteccion("Si");
			resultadoActual.setInforme(informeActual);
		}
	}

	public boolean getInformeDisponible() {
		if (semilleroActual != null) {
			for (SemilleroInforme informe : semilleroActual.getListaInformes()) {
				if (informe.getGuardadoParcial() == null
						|| (informe.getGuardadoParcial() != null && informe.getGuardadoParcial().equals("S"))) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean getEditarInforme() {
		if (semilleroActual != null) {
			for (SemilleroInforme informe : semilleroActual.getListaInformes()) {
				if (informe.getGuardadoParcial() != null && informe.getGuardadoParcial().equals("S")) {
					return true;
				}
			}
		}
		return false;
	}

	public void vincularProyecto() {
		SemilleroProyecto sp = new SemilleroProyecto();
		sp.setProyecto((Proyecto) servicioProyecto.obtenerProyectosXId(Long.parseLong(idProyecto)).get(0));
		sp.setSemillero(semilleroActual);
		semilleroActual.getProyectos().add(sp);
		servicioGeneral.guardarObjeto(semilleroActual);
		informeActual.setProyectos(informeActual.getProyectos() + "," + idProyecto);
		setProyectosSeleccionados(informeActual.getProyectos().split(","));
		idProyecto = "";
		listaProyectos = new ArrayList<SelectItem>();
		for (SemilleroProyecto proyecto : semilleroActual.getProyectos()) {
			listaProyectos
					.add(new SelectItem(proyecto.getProyecto().getId().toString(), proyecto.getProyecto().getNombre()));
		}
		listaNuevosProyectos = new ArrayList<SelectItem>();
		for (Object proyecto : servicioPersona.obtenerProyectosInvestigador(personaActual.getId())
				.getProyectosInvestigador()) {
			InvestigadorProyecto investigadorProyecto = (InvestigadorProyecto) proyecto;
			if ("A S F".contains(investigadorProyecto.getProyecto().getEstadoProyecto().getId())) {
				boolean proyectoVinculado = false;
				for (SemilleroProyecto semProyecto : semilleroActual.getProyectos()) {
					if (semProyecto.getProyecto().getId().equals(investigadorProyecto.getProyecto().getId())) {
						proyectoVinculado = true;
						break;
					}
				}
				if (!proyectoVinculado) {
					listaNuevosProyectos.add(new SelectItem(investigadorProyecto.getProyecto().getId(),
							investigadorProyecto.getProyecto().getId() + "- "
									+ investigadorProyecto.getProyecto().getNombre()));
				}
			}

		}
	}
	
	public String consultarInforme() {
		esConsulta=true;
		return cargarInforme();
		
	}

	public String nuevoInforme() {
		esConsulta = false;
		return cargarInforme();
		
	}
	
	/**
	 * Carga el informe seleccionado del semillero, inicializando sus actividades,
	 * proyectos, resultados y configuraciones relacionadas. Si el informe aún no
	 * tiene actividades, se generan automáticamente aquellas que aún no han sido 
	 * reportadas con un 100% de avance en informes anteriores del mismo semillero.
	 *
	 * @return Navegación a la vista de informe del semillero.
	 */
	private String cargarInforme() {
	    // Buscar el informe actual por ID dentro del semillero
	    for (SemilleroInforme informe : semilleroActual.getListaInformes()) {
	        if (idInforme == informe.getId()) {
	            setInformeActual(informe);
	            break;
	        }
	    }

	    // Cargar actividades solo si el informe no tiene ninguna
	    if (informeActual.getActividades() == null || informeActual.getActividades().isEmpty()) {
	        informeActual.getActividades().clear();

	        // Obtener las actividades al 100%
	        List<Integer> idsActividadesYaReportadas = servicioGeneral
	            .obtenerIdsActividadesRegistradasPorSemilleroYPorcentaje(
	                semilleroActual.getId().toString(), "100");

	        Set<Integer> actividadesExcluidas = new HashSet<Integer>(idsActividadesYaReportadas);

	        // Generar nuevas actividades para el informe actual (si no están ya reportadas al 100%)
	        for (SemilleroIntegrante item : getSemilleroActual().getListaIntegrantes()) {
	            for (SemilleroActividad actividad : item.getListaPlanTrabajo()) {
	                if (!actividadesExcluidas.contains(actividad.getId())) {
	                    SemilleroInformeActividad sia = new SemilleroInformeActividad();
	                    sia.setActividad(actividad);
	                    sia.setInforme(informeActual);
	                    sia.setResponsableActividad(item.getId());
	                    sia.setDescripcionActividad(actividad.getDescripcion());
	                    informeActual.getActividades().add(sia);
	                }
	            }
	        }
	    } else {
	        // Si el informe ya tiene proyectos asociados, los separamos
	        if (informeActual.getProyectos() != null && !informeActual.getProyectos().isEmpty()) {
	            setProyectosSeleccionados(informeActual.getProyectos().split(","));
	        }
	    }

	    // Cargar proyectos actuales del semillero
	    listaProyectos = new ArrayList<SelectItem>();
	    for (SemilleroProyecto proyecto : semilleroActual.getProyectos()) {
	        listaProyectos.add(new SelectItem(
	            proyecto.getProyecto().getId().toString(),
	            proyecto.getProyecto().getNombre()));
	    }

	    // Asignar informe a resultado
	    resultadoActual.setInforme(informeActual);

	    // Cargar nuevos proyectos disponibles del investigador (que aún no están en el semillero)
	    listaNuevosProyectos = new ArrayList<SelectItem>();
	    for (Object proyecto : servicioPersona.obtenerProyectosInvestigador(personaActual.getId())
	            .getProyectosInvestigador()) {

	        InvestigadorProyecto investigadorProyecto = (InvestigadorProyecto) proyecto;
	        boolean registroExistente = false;

	        if ("A S F".contains(investigadorProyecto.getProyecto().getEstadoProyecto().getId())) {
	            for (SemilleroProyecto semProyecto : semilleroActual.getProyectos()) {
	                if (semProyecto.getProyecto().getId().equals(
	                        investigadorProyecto.getProyecto().getId())) {
	                    registroExistente = true;
	                    break;
	                }
	            }
	            if (!registroExistente) {
	                listaNuevosProyectos.add(new SelectItem(
	                    investigadorProyecto.getProyecto().getId(),
	                    investigadorProyecto.getProyecto().getId() + "- " +
	                    investigadorProyecto.getProyecto().getNombre()));
	            }
	        }
	    }

	    // Cargar resultados del semillero
	    listaResultados = new ArrayList<SelectItem>();
	    for (SemilleroResultado item : getSemilleroActual().getListaResultados()) {
	        listaResultados.add(new SelectItem(item.getResultado(), item.getResultado()));
	    }

	    // Cargar porcentajes del 0 al 100
	    listaPorcentajes = new ArrayList<SelectItem>();
	    for (int i = 0; i <= 100; i++) {
	        listaPorcentajes.add(new SelectItem(i, i + " %"));
	    }

	    // Cargar productos nivel 1 y valores iniciales
	    List<ProductoTipo> listaProductos = this.servicioGeneral.obtenerListaObjetosWhere(
	        ProductoTipo.class,
	        "where p.estado = 'A' and p.descripcion like 'LISTA_PRODUCTO_UNIFICADA_HERMES' and p.nivel = '0' order by p.nombre"
	    );
	    prdNivel1Item = crearSelectItem(listaProductos);
	    prdNivel1 = listaProductos.get(0).getId();
	    cambiarProductoNivel1();

	    return "informeSemillero";
	}


	@SuppressWarnings("unchecked")
	public void cambiarProductoNivel1() {
		ProductoTipo pro2 = new ProductoTipo();
		pro2.setId(prdNivel1);
		List<ProductoTipo> listaProductos = servicioGeneral.obtenerHijos(pro2);
		prdNivel2Item = crearSelectItem(listaProductos);
		prdNivel2 = ((ProductoTipo) listaProductos.get(0)).getId();
		cambiarProductoNivel2();
	}

	@SuppressWarnings("unchecked")
	public void cambiarProductoNivel2() {
		ProductoTipo pro3 = new ProductoTipo();
		pro3.setId(prdNivel2);
		List<ProductoTipo> listaProductos = servicioGeneral.obtenerHijos(pro3);
		prdNivel3Item = crearSelectItem(listaProductos);
		prdNivel3 = ((ProductoTipo) listaProductos.get(0)).getId();
	}

	private SelectItem[] crearSelectItem(List<ProductoTipo> listaProductosNivel1) {
		SelectItem[] elementos;
		elementos = new SelectItem[listaProductosNivel1.size()];
		for (int i = 0; i < listaProductosNivel1.size(); i++) {
			ProductoTipo pro = listaProductosNivel1.get(i);
			String nombre = pro.getNombre();

			elementos[i] = new SelectItem(pro.getId(), nombre);
		}
		return elementos;
	}

	private void init() {
		setListaSemilleros(servicioGeneral.obtenerObjetos(Semillero.class,
				"select s from Semillero s,SemilleroIntegrante si where s.id=si.semillero.id and si.tipo in ('DD','EL','D') and si.integrante.id.documento='"
						+ personaActual.getId().getDocumento() + "' and si.integrante.id.tipoDocumento='"
						+ personaActual.getId().getTipoDocumento() + "'"));
	
		if(listaSemilleros!=null && listaSemilleros.size()>0) {
			for(int i = 0; i<listaSemilleros.size(); i++) {
				Semillero semillero = listaSemilleros.get(i);
				try {
				listaSemilleros.get(i).setCoordinador(servicioPersona.obtenerInvestigador(
						new IdPersona(semillero.getDocumentoCoordinador(), semillero.getTipoDocumentoCoordinador())));
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		if (getListaSolicitudes() == null) {
			setListaSolicitudes(new ArrayList<SelectItem>());
		}
		if (getListaSolicitudes() != null && getListaSolicitudes().isEmpty()) { // Se elimina solicitud tipo 4 (modificación del plan de trabajo)
			List<SemilleroSolicitudTipo> ssr = servicioGeneral.obtenerObjetos(SemilleroSolicitudTipo.class,
					"select srr from SemilleroSolicitudTipo srr where srr.id != 1 and srr.estado = 1");
			for (SemilleroSolicitudTipo resp : ssr) {
				getListaSolicitudes().add(new SelectItem(resp.getId(), resp.getNombre()));
			}
		}
		List<TipoDocumento> listaTipoDocumento = servicioGeneral.obtenerTiposDeDocumento();
		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			TipoDocumento td = listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
		}
		setSolicitudNueva(false);
		setListaCambiosContenido(new ArrayList<SelectItem>());
		List<DominioDetalle> ssr = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				"select dd from DominioDetalle dd where dd.identificador.id='271' order by dd.identificador.tipo");
		for (DominioDetalle resp : ssr) {
			getListaCambiosContenido().add(new SelectItem(resp.getDescripcion(), resp.getDescripcion()));
		}
		listaArchivos = new ArrayList<UploadedFile>();
		resultadoActual = new SemilleroInformeResultado();
		resultadoActual.setProteccion("Si");
		informeActual = new SemilleroInforme();
	}

	public void agregarArchivo(FileUploadEvent event) {
		if (solicitudActual.getId() == null) {
			listaArchivos.add(event.getFile());
		} else {
			setArchivoSeleccionado(event.getFile());
			cargarArchivo();
		}
	}

	private void cargarArchivo() {
		SemilleroSolicitudArchivo sa = new SemilleroSolicitudArchivo();
		solicitudActual.setSemillero(semilleroActual);
		sa.setSolicitud(solicitudActual);
		sa.setNombre(archivoSeleccionado.getFileName());
		sa.setTipo("O");
		servicioGeneral.guardarObjeto(sa);
		cargarArchivoDisco(archivoSeleccionado, "HER_SEMILLERO_SOL_ARCHIVO", sa.getId().toString());
		solicitudActual.getArchivos().add(sa);
	}

	public void eliminarSolicitud() {
		for (SemilleroSolicitudArchivo archivoSolActual : solicitudActual.getArchivos()) {
			Integer id = archivoSolActual.getId();
			Long subdirectorio = id / NUMERO_ARCHIVOS_CARPETA;
			String ruta = "HER_SEMILLERO_SOL_ARCHIVO" + "//" + subdirectorio.toString() + "//" + id;
			if (eliminarArchivoGenerico(ruta)) {
				solicitudActual.getArchivos().remove(archivoSolActual);
				archivoSolActual.setSolicitud(null);
				servicioGeneral.eliminarObjeto(archivoSolActual);
			}
		}
		semilleroActual.getSolicitudes().remove(solicitudActual);
		servicioGeneral.eliminarObjeto(solicitudActual);
	}

	public void cargarSolicitud() {
		setSolicitudNueva(true);
		if (semilleroActual.getEstadoActual()!=null && semilleroActual.getEstadoActual().getId().equals(6)) {
			setListaSolicitudes(new ArrayList<SelectItem>());
			List<SemilleroSolicitudTipo> ssr = servicioGeneral.obtenerObjetos(SemilleroSolicitudTipo.class,
					"select srr from SemilleroSolicitudTipo srr where srr.id = 2");
			for (SemilleroSolicitudTipo resp : ssr) {
				getListaSolicitudes().add(new SelectItem(resp.getId(), resp.getNombre()));
			}
		}
		if (solicitudActual.getId() != null) {
			if (solicitudActual.getDetalle() != null) {
				switch (solicitudActual.getTipo().getId()) {
				case 2:
					actualizarDialogSolicitudes();
					nuevoEstadoId = Integer.parseInt(solicitudActual.getDetalle());
					break;
				case 3:
					cambiosContenido = solicitudActual.getDetalle().split("-");
					break;
				case 5:
					String[] data = solicitudActual.getDetalle().split("-");
					tipoDocNuevoLider = data[0];
					documentoNuevoLider = data[1];
					break;
				}
			}
		} else {
			actualizarDialogSolicitudes();
		}
	}

	public void descargarArchivo() {
		try {
			descargarArchivoGenerico("HER_SEMILLERO_SOL_ARCHIVO", archivo.getId().toString(), archivo.getNombre());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void eliminarArchivo() {
		Integer id = archivo.getId();
		String ruta = "HER_SEMILLERO_SOL_ARCHIVO" + "//" + id;
		if (eliminarArchivoGenerico(ruta)) {
			solicitudActual.getArchivos().remove(archivo);
			servicioGeneral.eliminarObjeto(archivo);
		}
	}

	public void nuevaSolicitud() {
		setSolicitudActual(new SemilleroSolicitud());
		listaArchivos.clear();
		cargarSolicitud();
	}

	public void cancelarSolicitud() {
		setSolicitudNueva(false);
		setSolicitudActual(new SemilleroSolicitud());
		listaArchivos.clear();
		nuevoEstadoId = null;
		cambiosContenido = null;
	}

	private void guardarSolicitud(boolean parcial) {
		String detalle = "";
		switch (solicitudActual.getTipo().getId()) {
		case 2:
			detalle += nuevoEstadoId;
			break;
		case 3:
			for (String item : cambiosContenido) {
				detalle += item + "-";
			}
			if (!esCadenaVacia(detalle)) {
				detalle = detalle.substring(0, detalle.length() - 1);
			}
			break;
		case 5:
			detalle += tipoDocNuevoLider + "-" + documentoNuevoLider;
			break;
		}
		solicitudActual.setDetalle(detalle);
		if (validarBasico()) {
			solicitudActual.setSemillero(getSemilleroActual());
			solicitudActual.setRespuestaVifDi(null);
			SemilleroSolicitudTipo tipo = servicioGeneral
					.obtenerObjetoXID(SemilleroSolicitudTipo.class, solicitudActual.getTipo().getId().toString())
					.get(0);
			solicitudActual.getTipo().setNombre(tipo.getNombre());
			solicitudActual.setFechaSolicitud(Calendar.getInstance().getTime());
			if (parcial) {
				solicitudActual.setRespuestaUAB(
						servicioGeneral.obtenerObjetoXID(SemilleroSolicitudRespuesta.class, "0").get(0));
				solicitudActual.setRespuestaCoordinador(null);
			} else {
				CorreoPlantilla cp;
				Correo mail;
				if (solicitudActual.getTipo().getId().equals(2) && Sede.SEDES_ANDINAS
						.contains(getSemilleroActual().getLider().getDependencia().getSede().getId().toString())) {
					solicitudActual.setRespuestaUAB(
							servicioGeneral.obtenerObjetoXID(SemilleroSolicitudRespuesta.class, "1").get(0));
					solicitudActual.setRespuestaCoordinador(null);
					cp = cargarPlantilla(331);
					mail = editarCorreo(cp);
					String sql = " JOIN i.roles r WHERE r.id = 'DD' AND i.dependencia2.id = '"
							+ semilleroActual.getLider().getDependencia().getId() + "' and (i.dependencia2.id <> '0')";
					for (InvestigadorInterno p : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class,
							sql)) {
						PersonaRol pr = (PersonaRol) servicioGeneral.obtenerPersonaRolXIdPersona(
								p.getId().getTipoDocumento(), p.getId().getDocumento(), "DD").get(0);
						if (p.getEmail() != null && pr.getFechaInicioRol().before(getToday())
								&& pr.getFechaFinRol().after(getToday())) {
							mail.adicionarDireccion(p.getEmail());
						}
					}
				} else {
					solicitudActual.setRespuestaCoordinador(
							servicioGeneral.obtenerObjetoXID(SemilleroSolicitudRespuesta.class, "1").get(0));
					solicitudActual.setRespuestaUAB(null);
					cp = cargarPlantilla(351);
					mail = editarCorreo(cp);
					mail.adicionarDireccion((servicioPersona
							.obtenerInvestigador(new IdPersona(semilleroActual.getDocumentoCoordinador(),
									semilleroActual.getTipoDocumentoCoordinador()))
							.getEmail()));
				}
				if (!mail.getDirecciones().isEmpty()) {
					servicioCorreo.enviarCorreo(mail);
				}
			}
			servicioGeneral.guardarObjeto(solicitudActual);
			if (listaArchivos != null) {
				for (UploadedFile file : listaArchivos) {
					setArchivoSeleccionado(file);
					cargarArchivo();
				}
				listaArchivos.clear();
				servicioGeneral.guardarObjeto(solicitudActual);
			}
			getSemilleroActual().getSolicitudes().add(solicitudActual);
			servicioGeneral.guardarObjeto(getSemilleroActual());
			msgDialog = "Solicitud guardada con ID " + solicitudActual.getId();
			FacesContext.getCurrentInstance().addMessage("msgsModal",
					new FacesMessage(FacesMessage.SEVERITY_INFO, msgDialog, null));
		} else {
			FacesContext.getCurrentInstance().addMessage("msgsModal",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, msgDialog, null));
		}
	}

	public Correo editarCorreo(CorreoPlantilla plantilla) {
		String cuerpo = plantilla.getCuerpo();
		cuerpo = cuerpo.replaceAll("<<NOMBRE>>", semilleroActual.getNombre());
		cuerpo = cuerpo.replaceAll("<<ID>>", semilleroActual.getId().toString());
		cuerpo = cuerpo.replaceAll("<<LIDER>>", semilleroActual.getLider().getNombreCompleto());
		Correo correo = new Correo();
		correo.setOrigen(Correo.CORREO_HERMES);
		//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
		correo.setAsunto(plantilla.getAsunto());
		if (solicitudActual != null && solicitudActual.getTipo()!=null && solicitudActual.getTipo().getNombre() !=null) {
			cuerpo = cuerpo.replaceAll("<<TIPO>>", solicitudActual.getTipo().getNombre().toUpperCase());
			if (solicitudActual.getTipo().getId().equals(2)) {
				cuerpo = cuerpo.replaceAll("<<ESTADO>>",
						servicioGeneral.obtenerObjetoXID(SemilleroEstado.class, nuevoEstadoId.toString()).get(0)
								.getNombre().toUpperCase());
			}
			correo.setAsunto(
					correo.getAsunto().replaceAll("<<TIPO>>", solicitudActual.getTipo().getNombre().toUpperCase()));
		}
		correo.setCuerpo(cuerpo);
		return correo;
	}

	private boolean validarBasico() {
		boolean returnValue = true;
		if (esCadenaVacia(solicitudActual.getJustificacion())) {
			returnValue = false;
			msgDialog = "Por favor, indicar la justificación de la solicitud.";
		}
		if (esCadenaVacia(solicitudActual.getDetalle().replace("-", ""))) {
			if (solicitudActual.getTipo().getId().equals(2)) {
				returnValue = false;
				msgDialog = "Por favor, indicar el nuevo estado del semillero.";
			}
			if (solicitudActual.getTipo().getId().equals(3)) {
				returnValue = false;
				msgDialog = "Por favor, indicar el contenido a cambiar del semillero.";
			}
			if (solicitudActual.getTipo().getId().equals(5)) {
				returnValue = false;
				msgDialog = "Por favor, indicar el documento del nuevo líder del semillero.";
			}
		}
		if (solicitudActual.getTipo().getId().equals(5)) {
			IdPersona id = new IdPersona();
			id.setTipoDocumento(tipoDocNuevoLider);
			id.setDocumento(documentoNuevoLider);
			Investigador lider = servicioPersona.obtenerInvestigador(id);
			if (lider == null || (lider != null && lider.getInterno().equals("N"))) {
				returnValue = false;
				msgDialog = "El documento indicado no se encuentra asociado a un investigador interno.";
			}
		}
		return returnValue;
	}

	public void enviarSolicitud() {
		guardarSolicitud(false);
	}

	public void guardarSolicitudParcialmente() {
		guardarSolicitud(true);
	}

	public void actualizarDialogSolicitudes() {
		for (SemilleroSolicitud sol : semilleroActual.getSolicitudes()) {
			if (sol.getTipo().getId().equals(getSolicitudActual().getTipo().getId())) {
				if ((sol.getRespuestaUAB() != null
						&& (sol.getRespuestaUAB().getId().equals(1) || sol.getRespuestaUAB().getId().equals(2)))) {
					msgDialog = "En este momento ya existe una solicitud de este tipo registrada. Por favor, espere a que sea tramitada por UAB para poder generar una nueva.";
					FacesContext.getCurrentInstance().addMessage("msgsModal",
							new FacesMessage(FacesMessage.SEVERITY_ERROR, msgDialog, null));
					// getSolicitudActual().getTipo().setId(null);
					return;
				} else if ((sol.getRespuestaCoordinador() != null && (!sol.getRespuestaCoordinador().getId().equals(7)
						&& !sol.getRespuestaCoordinador().getId().equals(4)))) {
					msgDialog = "En este momento ya existe una solicitud de este tipo registrada. Por favor, espere a que sea tramitada por el coordinador para poder generar una nueva.";
					FacesContext.getCurrentInstance().addMessage("msgsModal",
							new FacesMessage(FacesMessage.SEVERITY_ERROR, msgDialog, null));
					// getSolicitudActual().getTipo().setId(null);
					return;
				}
			}
		}
		if (getSolicitudActual().getTipo() != null && getSolicitudActual().getTipo().getId() != null
				&& getSolicitudActual().getTipo().getId().equals(2)) {
			setListaEstadosDisponiblesSolicitud(new ArrayList<SelectItem>());
			if (getSemilleroActual().getEstadoActual().getId().equals(5)) {
				getListaEstadosDisponiblesSolicitud().add(new SelectItem(6, "Suspendido"));
				getListaEstadosDisponiblesSolicitud().add(new SelectItem(7, "Inactivo"));
			} else if (getSemilleroActual().getEstadoActual().getId().equals(6)) {
				getListaEstadosDisponiblesSolicitud().add(new SelectItem(5, "Activo"));
			}
		}
	}

	public String consultarSemillero() {
		sesion.setAttribute("consultaSemillero", true);
		return irSemillero();
	}

	public String reporteSemillero() {
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("idSemillero", semilleroActual.getId().toString());
		r.setNombreReporte("/semilleros/reporteSemillero");
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
		return "";
	}

	public String editarSemillero() {
		sesion.setAttribute("consultaSemillero", false);
		return irSemillero();
	}

	private String irSemillero() {
		sesion.setAttribute("semillero", semilleroActual.getId());
		return "crearSemillero";
	}

	public List<Semillero> getListaSemilleros() {
		return listaSemilleros;
	}

	public void setListaSemilleros(List<Semillero> listaSemilleros) {
		this.listaSemilleros = listaSemilleros;
	}

	public int getSemillerosRegistrados() {
		return getListaSemilleros().size();
	}

	public Semillero getSemilleroActual() {
		return semilleroActual;
	}

	public void setSemilleroActual(Semillero semilleroActual) {
		this.semilleroActual = semilleroActual;
	}

	public ArrayList<SelectItem> getListaSolicitudes() {
		return listaSolicitudes;
	}

	public void setListaSolicitudes(ArrayList<SelectItem> listaSolicitudes) {
		this.listaSolicitudes = listaSolicitudes;
	}

	public String getMsgDialog() {
		return msgDialog;
	}

	public void setMsgDialog(String msgDialog) {
		this.msgDialog = msgDialog;
	}

	public Boolean getSolicitudNueva() {
		return solicitudNueva;
	}

	public void setSolicitudNueva(Boolean nuevaSolicitud) {
		this.solicitudNueva = nuevaSolicitud;
	}

	public SemilleroSolicitud getSolicitudActual() {
		return solicitudActual;
	}

	public void setSolicitudActual(SemilleroSolicitud solicitudActual) {
		this.solicitudActual = solicitudActual;
	}

	public ArrayList<SelectItem> getListaEstadosDisponiblesSolicitud() {
		return listaEstadosDisponiblesSolicitud;
	}

	public void setListaEstadosDisponiblesSolicitud(ArrayList<SelectItem> estadosDisponiblesSolicitud) {
		this.listaEstadosDisponiblesSolicitud = estadosDisponiblesSolicitud;
	}

	public Integer getNuevoEstadoId() {
		return nuevoEstadoId;
	}

	public void setNuevoEstadoId(Integer nuevoEstadoId) {
		this.nuevoEstadoId = nuevoEstadoId;
	}

	public String[] getCambiosContenido() {
		return cambiosContenido;
	}

	public void setCambiosContenido(String[] cambiosContenido) {
		this.cambiosContenido = cambiosContenido;
	}

	public String getTipoDocNuevoLider() {
		return tipoDocNuevoLider;
	}

	public void setTipoDocNuevoLider(String tipoDocNuevoLider) {
		this.tipoDocNuevoLider = tipoDocNuevoLider;
	}

	public String getDocumentoNuevoLider() {
		return documentoNuevoLider;
	}

	public void setDocumentoNuevoLider(String documentoNuevoLider) {
		this.documentoNuevoLider = documentoNuevoLider;
	}

	public SelectItem[] getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}

	public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
		this.tipoDocumentoItem = tipoDocumentoItem;
	}

	public ArrayList<SelectItem> getListaCambiosContenido() {
		return listaCambiosContenido;
	}

	public void setListaCambiosContenido(ArrayList<SelectItem> listaCambiosContenido) {
		this.listaCambiosContenido = listaCambiosContenido;
	}

	public UploadedFile getArchivoSeleccionado() {
		return archivoSeleccionado;
	}

	public void setArchivoSeleccionado(UploadedFile archivoSeleccionado) {
		this.archivoSeleccionado = archivoSeleccionado;
	}

	public ArrayList<UploadedFile> getListaArchivos() {
		return listaArchivos;
	}

	public void setListaArchivos(ArrayList<UploadedFile> listaArchivos) {
		this.listaArchivos = listaArchivos;
	}

	public SemilleroSolicitudArchivo getArchivo() {
		return archivo;
	}

	public void setArchivo(SemilleroSolicitudArchivo archivo) {
		this.archivo = archivo;
	}

	public SemilleroInforme getInformeActual() {
		return informeActual;
	}

	public void setInformeActual(SemilleroInforme informeActual) {
		this.informeActual = informeActual;
	}

	public ArrayList<SelectItem> getListaPorcentajes() {
		return listaPorcentajes;
	}

	public void setListaPorcentajes(ArrayList<SelectItem> listaPorcentajes) {
		this.listaPorcentajes = listaPorcentajes;
	}

	public SemilleroInformeResultado getResultadoActual() {
		return resultadoActual;
	}

	public void setResultadoActual(SemilleroInformeResultado resultadoActual) {
		this.resultadoActual = resultadoActual;
	}

	public String getPrdNivel1() {
		return prdNivel1;
	}

	public void setPrdNivel1(String productoNivel1) {
		this.prdNivel1 = productoNivel1;
	}

	public String getPrdNivel2() {
		return prdNivel2;
	}

	public void setPrdNivel2(String productoNivel2) {
		this.prdNivel2 = productoNivel2;
	}

	public String getPrdNivel3() {
		return prdNivel3;
	}

	public void setPrdNivel3(String productoNivel3) {
		this.prdNivel3 = productoNivel3;
	}

	public SelectItem[] getPrdNivel1Item() {
		return prdNivel1Item;
	}

	public void setPrdNivel1Item(SelectItem[] prdNivel1Item) {
		this.prdNivel1Item = prdNivel1Item;
	}

	public SelectItem[] getPrdNivel2Item() {
		return prdNivel2Item;
	}

	public void setPrdNivel2Item(SelectItem[] productoNivel2Item) {
		this.prdNivel2Item = productoNivel2Item;
	}

	public SelectItem[] getPrdNivel3Item() {
		return prdNivel3Item;
	}

	public void setPrdNivel3Item(SelectItem[] productoNivel3Item) {
		this.prdNivel3Item = productoNivel3Item;
	}

	public ArrayList<SelectItem> getListaResultados() {
		return listaResultados;
	}

	public void setListaResultados(ArrayList<SelectItem> listaResultados) {
		this.listaResultados = listaResultados;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public ArrayList<SelectItem> getListaProyectos() {
		return listaProyectos;
	}

	public void setListaProyectos(ArrayList<SelectItem> listaProyectos) {
		this.listaProyectos = listaProyectos;
	}

	public String[] getProyectosSeleccionados() {
		return proyectosSeleccionados;
	}

	public void setProyectosSeleccionados(String[] proyectosSeleccionados) {
		this.proyectosSeleccionados = proyectosSeleccionados;
	}

	public SemilleroInformeArchivo getArchivoInf() {
		return archivoInf;
	}

	public void setArchivoInf(SemilleroInformeArchivo archivoInf) {
		this.archivoInf = archivoInf;
	}

	public ArrayList<SelectItem> getListaNuevosProyectos() {
		return listaNuevosProyectos;
	}

	public void setListaNuevosProyectos(ArrayList<SelectItem> listaNuevosProyectos) {
		this.listaNuevosProyectos = listaNuevosProyectos;
	}

	public String getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(String idProyecto) {
		this.idProyecto = idProyecto;
	}

	public boolean isEsConsulta() {
		return esConsulta;
	}

	public void setEsConsulta(boolean esConsulta) {
		this.esConsulta = esConsulta;
	}

	public Integer getIdInforme() {
		return idInforme;
	}

	public void setIdInforme(Integer idInforme) {
		this.idInforme = idInforme;
	}
	
public void reporteSolicitudSemillero() {
		
		ReporteBirt r = new ReporteBirt(); 
		r.adicionarParametro("idsol", idSolicitudSeleccionada.toString());
		r.setFormato(ReporteBirt.FORMATO_PDF);

		r.setNombreReporte("semilleros/solicitudSemillero");

		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().dispatch("/ReporteEngineServlet");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.responseComplete();
		}
	}

public Long getIdSolicitudSeleccionada() {
	return idSolicitudSeleccionada;
}

public void setIdSolicitudSeleccionada(Long idSolicitudSeleccionada) {
	this.idSolicitudSeleccionada = idSolicitudSeleccionada;
}


}
