/** 
* @author  Ing Hernán Darío Bernal Parra
*/

/**
 * ESTA CLASE ES LA CLASE BASE PARA LOS DIFERENTES MANEJADORES USADOS EN LA INSERCION DE PROYECTOS DE INVESTIGACION.
 * CONSTA DE TRES BOTONES UNO PARA IR ATRAS AL ANTERIOR MANEJADOR, UN BOTON PARA SALIR DEL MANEJADOR ACTUAL PARA 
 * CONTINUAR POSTERIORMENTE CON LA INSERCION DEL PROYECTO Y UN BOTON PARA IR AL SIGUIENTE MANEJADOR. CONSTA TAMBIEN
 * DE UN IDENTIFICADOR DEL MANEJADOR, EL CUAL SIRVE PARA SABER EN QUE PARTE DE LA INSERCION SE ENCUENTRA EL PROYECTO.
 * Y POR ULTIMO CONSTA DE UN OBJETO PROYECTO BASICO, AL CUAL LE SON MANIPULADAS LA DIFERENTES PROPIEDADES A LO LARGO
 * DE LA CADENA DE MANEJADORES
*/
package co.edu.unal.hermes.vista.proyectos.manejadorproyecto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.component.UICommand;

import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Estudiante;
import co.edu.unal.hermes.modelo.HistoricoCambioIntegrantes;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoModalidad;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.seguimiento.SolicitudProrrogaInvestigador;
import co.edu.unal.hermes.vista.ManejadorBase;

public abstract class ManejadorProyecto extends ManejadorBase {
	// IDENTIFICADORES ESTATICOS PARA LOS FORMULARIOS PARA INSERCION DE
	// PROYECTOS DE INVESTIGACION
	protected static int DATOS_BASICOS = 0;
	protected static int INVESTIGADORES = 1;
	protected static int LINEAS_INVESTIGACION = 2;
	protected static int OBJETIVOS_RESULTADOS = 3;
	protected static int INFO_ESPECIFICA = 4;
	protected static int ACTIVIDADES = 5;
	protected static int BIBLIOGRAFIA = 6;
	protected static int AREAS_TEMATICAS = 7;
	protected static int PRODUCTOS = 8;
	protected static int REQUISITOS = 9;
	protected static int COMPROMISOS = 10;
	protected static int EVALUADORES = 11;
	protected static int FUENTES_FINANCIERAS = 12;
	protected static int RUBROS = 13;
	protected static int ARCHIVOS = 14;
	protected static int AGENDAS = 15;
	protected static int FICHA_MINIMA = 16;
	protected static int CONVOCATORIA_LIBROS = 17;

	// DEFINICION DE LOS BOTONES
	protected UICommand botonAtras;
	protected UICommand botonSiguiente;
	protected UICommand botonSalir;
	protected UICommand botonSalirGuardar;

	// IDENTIFICADOR DE LA FASE DEL MANEJADOR
	protected int idManejador;
	// PROYECTO A SER MANIPULADO
	protected Proyecto proyectoActual;
	protected Boolean consultaEvaluacion;

	protected Date fechaFinalConvocatoria;

	private HistoricoCambioIntegrantes hci;
	private List<HistoricoCambioIntegrantes> historicoIntegrantes;
	private List<SolicitudProrrogaInvestigador> solicitudesInvestigador;

	// COSNTRUCTOR
	public ManejadorProyecto() {
		// INSTANCIACION DEL MANEJADOR BASE
		super();
		// DEFINICION DE LOS BOTONES
		botonAtras = new UICommand();
		botonSiguiente = new UICommand();
		botonSalir = new UICommand();
		botonSalirGuardar = new UICommand();
		// OBTENCION DEL PROYECTO A SER MANIPULADO
		proyectoActual = (Proyecto) sesion.getAttribute("proyecto");
		try {
			if (proyectoActual.getModalidad() instanceof Convocatoria) {
				fechaFinalConvocatoria = ((Convocatoria) proyectoActual.getModalidad()).getFechaFinal();
			}
		} catch (Exception e) {
			proyectoActual = new Proyecto();
		}

		consultaEvaluacion = (Boolean) sesion.getAttribute("consultaEvaluacion");
	}

	// FUNCIONES ABSTRACTAS PARA LA NAVEGACION ENTRE FORMULARIOS
	abstract protected void cargarValoresIniciales();

	abstract public String atras();

	abstract public String salir();

	abstract public String salirGuardar();

	abstract public String siguiente();

	public boolean validarBotonGuardarConvocatoria(final Long idProyecto) {
		String consultaProyecto = "select #id e.id, #estadoProyecto e.estadoProyecto, #permitirModificacion e.permitirModificacion from Proyecto e where e.id = "
				+ idProyecto;
		List<Proyecto> listaProyectoAux = servicioGeneral.obtenerObjetosLimitado(Proyecto.class, consultaProyecto);

		if (listaProyectoAux.isEmpty()) {
			return true;
		} else {
			Proyecto pry = listaProyectoAux.get(0);
			if (pry.getEstadoProyecto().getId().equals("I")
					|| pry.getEstadoProyecto().getId().equals(EstadoProyecto.EN_LEGALIZACION)) {
				return true;
			} else {
				if (pry.getPermitirModificacion() != null) {
					if (pry.getPermitirModificacion().equals("S")) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
		}
	}

	public String salirConsulta() {
		borrarManejadoresInsercionProyecto();
		return "evaluarProyectos";
	}

	public String getInicioTitulo() {
		String inicioTitulo = proyectoActual.getNombre();
		return inicioTitulo;
	}

	public UICommand getBotonAtras() {
		return botonAtras;
	}

	public void setBotonAtras(UICommand botonAtras) {
		this.botonAtras = botonAtras;
	}

	public UICommand getBotonSalir() {
		return botonSalir;
	}

	public void setBotonSalir(UICommand botonSalir) {
		this.botonSalir = botonSalir;
	}

	public UICommand getBotonSiguiente() {
		return botonSiguiente;
	}

	public void setBotonSiguiente(UICommand botonSiguiente) {
		this.botonSiguiente = botonSiguiente;
	}

	public Proyecto getProyectoActual() {
		return proyectoActual;
	}

	public UICommand getBotonSalirGuardar() {
		return botonSalirGuardar;
	}

	public void setBotonSalirGuardar(UICommand botonSalirGuardar) {
		this.botonSalirGuardar = botonSalirGuardar;
	}

	public void setProyectoActual(Proyecto proyectoActual) {
		this.proyectoActual = proyectoActual;
	}

	/**
	 * @return Returns the consultaEvaluacion.
	 */
	public Boolean getConsultaEvaluacion() {
		return consultaEvaluacion;
	}

	/**
	 * @param consultaEvaluacion
	 *            The consultaEvaluacion to set.
	 */
	public void setConsultaEvaluacion(Boolean consultaEvaluacion) {
		this.consultaEvaluacion = consultaEvaluacion;
	}

	public boolean getEsSena() {
		return proyectoActual.getModalidad().getTipo().getId().equals(TipoModalidad.SENA);
	}

	public Date getFechaFinalConvocatoria() {
		return fechaFinalConvocatoria;
	}

	public void setFechaFinalConvocatoria(Date fechaFinalConvocatoria) {
		this.fechaFinalConvocatoria = fechaFinalConvocatoria;
	}

	public void generarHistoricoProyecto() {
		for (InvestigadorProyecto ip : proyectoActual.getInvestigadoresProyecto()) {
			hci = new HistoricoCambioIntegrantes();
			hci.setProyecto(proyectoActual);
			hci.setFechaIngreso(getToday());
			hci.setIntegrante(ip.getInvestigador());
			hci.setTipoInvestigadorProyecto(ip.getTipo());
			hci.setMesesDedidacion(ip.getTotalHorasVinculacion());
			hci.setHorasDedidacion(ip.getDedicacionHorasSemana());
			if (ip.getTipo().getId().equals("P")
					|| (!ip.getTipo().getId().equals("P") && ip.getTipo().getTipo().equals("F"))) {
				hci.setEstadoCivil(ip.getInvestigador().getEstadoCivil());
				hci.setTipoVinculacion(ip.getInvestigador().getTipoVinculacion());
				hci.setTipoDedicacion(ip.getInvestigador().getTipoDedicacion());
				hci.setTipoFormacion(ip.getInvestigador().getTipoFormacion());
				hci.setDependencia(ip.getInvestigador().getDependencia().getFacultad());
				hci.setSede(ip.getInvestigador().getDependencia().getFacultad().getSede());
			} else if (ip.getTipo().getTipo().equals("A")) {
				hci.setEstadoCivil(ip.getInvestigador().getEstadoCivil());
				Estudiante e = servicioPersona.obtenerEstudiante(ip.getInvestigador().getId());
				if (e != null) {
					hci.setPlanEstudios(e.getPlan());
					hci.setSemestreActual(e.getSemestreActual());
					hci.setDependencia(e.getDependencia());
					hci.setSede(e.getDependencia().getSede());
				}
			}
			servicioGeneral.guardarObjeto(hci);
		}
	}

	public void cargarHistorico() {
		setHistoricoIntegrantes(servicioGeneral.obtenerObjetos(HistoricoCambioIntegrantes.class,
				"from HistoricoCambioIntegrantes h where h.proyecto.id = '" + proyectoActual.getId()
						+ "' order by h.id asc"));
		for (HistoricoCambioIntegrantes item : historicoIntegrantes) {
			TipoDocumento td = servicioGeneral
					.obtenerObjetoXID(TipoDocumento.class, item.getIntegrante().getId().getTipoDocumento()).get(0);
			item.setDocumento(td.getNombre());
			if (item.getTipoInvestigadorProyecto().getId().equals("P")
					|| item.getTipoInvestigadorProyecto().getId().equals("C")
					|| item.getTipoInvestigadorProyecto().getId().equals("PCD")
					|| (!item.getTipoInvestigadorProyecto().getId().equals("P")
							&& !item.getTipoInvestigadorProyecto().getId().equals("C")
							&& item.getTipoInvestigadorProyecto().getTipo().equals("F"))) {
				item.setTipoVinculacionGrupo("Interno");
			} else if (item.getTipoInvestigadorProyecto().getTipo().equals("A") || item.getTipoInvestigadorProyecto().getTipo().equals("D")) {
				item.setTipoVinculacionGrupo("Interno");
			} else {
				item.setTipoVinculacionGrupo("Externo");
			}
		}
	}
	
	public void cargarHistoricoProrroga() {
		solicitudesInvestigador = new ArrayList<SolicitudProrrogaInvestigador>();
		solicitudesInvestigador = servicioGeneral.obtenerObjetos("select h from SolicitudProrrogaInvestigador h, Solicitud s where s.proyecto.id = '" + proyectoActual.getId()
						+ "' and s.respuesta in ('A') and s.id = h.solicitud.id and h.estado not in ('B','N') order by h.id asc");
		
	}

	public List<HistoricoCambioIntegrantes> getHistoricoIntegrantes() {
		return historicoIntegrantes;
	}

	public void setHistoricoIntegrantes(List<HistoricoCambioIntegrantes> historicoIntegrantes) {
		this.historicoIntegrantes = historicoIntegrantes;
	}
	
	public void enviarCorreoEdicionInfoPryEstLider(final Long idProyecto) {
		/*Investigador inv = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(idProyecto);
        Correo correo = new Correo();
        CorreoPlantilla cp = cargarPlantilla(326);
        correo.setOrigen(Correo.CORREO_HERMES);
        correo.setAsunto(cp.getAsunto().replaceAll("<<ID_PROYECTO>>", idProyecto.toString()));        
        String personaCorreo = "(" + personaActual.getId().getTipoDocumento() + "-"
                + personaActual.getId().getDocumento() + ") " + personaActual.getNombre1() + " "
                + personaActual.getApellido1();        
        correo.setCuerpo(cp.getCuerpo().replaceAll("<<ESTUDIANTE_LIDER>>", personaCorreo));
        correo.setCuerpo(cp.getCuerpo().replaceAll("<<ID_PROYECTO>>", idProyecto.toString()));
        correo.adicionarDireccion(inv.getEmail());
        correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
        servicioCorreo.enviarCorreo(correo);*/
	}

	public boolean validarSiEsEstudianteLiderProyecto(final Long idProyecto) {
		try {
			String hqlEL = "select #id e.id from InvestigadorProyecto e where e.proyecto.id = " + idProyecto + " and e.tipo.id IN ('AL','ADL') and e.investigador.id.documento = '" + personaActual.getId().getDocumento() + "' and e.investigador.id.tipoDocumento = '" + personaActual.getId().getTipoDocumento() + "'";
			List<InvestigadorProyecto> listEstudianteLider = servicioGeneral.obtenerObjetosLimitado(InvestigadorProyecto.class, hqlEL);
			if(!listEstudianteLider.isEmpty()){
				return true;
			}else{
				return false;
			}
		} catch (NullPointerException n) {
			return false;
		}
	}

	public List<SolicitudProrrogaInvestigador> getSolicitudesInvestigador() {
		return solicitudesInvestigador;
	}

	public void setSolicitudesInvestigador(List<SolicitudProrrogaInvestigador> solicitudesInvestigador) {
		this.solicitudesInvestigador = solicitudesInvestigador;
	}

}
