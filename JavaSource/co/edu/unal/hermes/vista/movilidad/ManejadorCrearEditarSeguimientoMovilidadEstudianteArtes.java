package co.edu.unal.hermes.vista.movilidad;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.model.DefaultUploadedFile;

import co.edu.unal.hermes.modelo.ActividadMovilidad;
import co.edu.unal.hermes.modelo.ArchivoMovilidad;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.MovilidadArchivo;
import co.edu.unal.hermes.modelo.MovilidadEstudiantesArtes;
import co.edu.unal.hermes.modelo.Persona;

public class ManejadorCrearEditarSeguimientoMovilidadEstudianteArtes extends ManejadorBaseMovilidad {
	
    private static final long serialVersionUID = -6818167404482461798L;
    private MovilidadEstudiantesArtes mde;
	private boolean consulta;
	private String personaMovilidad = "";
	private String estudianteMovilidad = "";
	private String facultadDocente;
	private String sedeDocente;
	private String nombrePais;
	private String calificacion1Sel;
	private String calificacion2Sel;
	private String calificacion3Sel;
	private SelectItem[] tipoCalificacion1;
	private SelectItem[] tipoCalificacion2;
	private SelectItem[] tipoCalificacion3;
	private String descripcionActividad;
	private Date fechaActividad;
	private String duracionActividad;
	private List<ActividadMovilidad> listaActividades;
	private ActividadMovilidad actividadMovilidadSeleccionada;
	private String descripcionMovilidad;
	private String experiencia;
	private SelectItem[] tipoDocumentoSelItem;
	private String tipoDocumentoSel;
	private DefaultUploadedFile archivoObligatorio;
	private List<ArchivoMovilidad> listaArchivosObligatoriosSel;
	private List<ArchivoMovilidad> listaArchivosObligatoriosEliminados;
	private ArchivoMovilidad archivoTabla;
	private String realizacionMovilidad;
	private String razonesNoRealizacion;

	public ManejadorCrearEditarSeguimientoMovilidadEstudianteArtes() {

		cargarValoresIniciales();

		consulta = habilitarEdicion();
		mde = (MovilidadEstudiantesArtes) sesion
				.getAttribute("movilidadEstudianteArt");
		List<MovilidadEstudiantesArtes> movs = servicioGeneral.obtenerObjetos(
				MovilidadEstudiantesArtes.class,
				"from MovilidadEstudiantesArtes mov where mov.id = '"
						+ mde.getId() + "'");
		if (movs != null && movs.size() > 0) {
			mde = movs.get(0);
		}
		realizacionMovilidad = "SI";
		cargarTipoCalificacion();
		cargarTiposDocumentos();
		asignarInformacionMovilidadDocente();
		cargarArchivosMovilidad();

	}

	public boolean habilitarEdicion() {
		try {
			String consulta = (String) sesion
					.getAttribute("consultaMovilidadEstudiantesArtes");
			sesion.removeAttribute("consultaMovilidadEstudiantesArtes");
			if (consulta.equals("SI")) {
				return true;
			}
		} catch (NullPointerException npe) {
			return false;
		}
		return false;
	}

	public void cargarValoresIniciales() {
		personaActual = (Persona) sesion.getAttribute("persona");
		listaActividades = new ArrayList<ActividadMovilidad>();
		listaArchivosObligatoriosSel = new ArrayList<ArchivoMovilidad>();
	}

	public void asignarInformacionMovilidadDocente() {
		if (mde != null) {
			personaMovilidad = mde.getPersonaInv()
					.getNombreCompletoMinusculas();
			
			estudianteMovilidad = mde.getEstudianteInv().getNombreCompleto();
			
			if (mde.getCalificacionA() != null) {
				calificacion1Sel = mde.getCalificacionA();
			}
			if (mde.getCalificacionB() != null) {
				calificacion2Sel = mde.getCalificacionB();
			}
			if (mde.getCalificacionC() != null) {
				calificacion3Sel = mde.getCalificacionC();
			}
			descripcionMovilidad = mde.getSegMovDescripcion();
			experiencia = mde.getExperienciaResidencia();
			
			if (mde.getRealizacionMovilidad() != null) {
				realizacionMovilidad = mde.getRealizacionMovilidad();
			}
			razonesNoRealizacion = mde.getRazonesNoRealizacion();
			
			List listaAcCons = servicioGeneral.obtenerObjetos("select e from ActividadMovilidad e where e.tipoActividad = 'S' and e.movilidad = "
					+ mde.getId());
			
			if (listaAcCons.size() > 0) {
				listaActividades.addAll(listaAcCons);
			}

			InvestigadorInterno docInt = servicioPersona
					.obtenerInvestigadorInternoDependenciaYFacultad(mde
							.getPersonaInv().getId());

			if (docInt != null) {
				facultadDocente = docInt.getDependencia().getFacultad()
						.getNombre();
				sedeDocente = docInt.getDependencia().getSede().getNombre();
			} else {
				facultadDocente = "";
				sedeDocente = "";
			}

			nombrePais = mde.getPais().getNombre();
		}
	}

	private void cargarTipoCalificacion() {
		List<DominioDetalle> listaTipoCalificacion = servicioGeneral
				.obtenerObjetos(DominioDetalle.class,
						"from DominioDetalle where identificador.id = '4'");
		tipoCalificacion1 = new SelectItem[listaTipoCalificacion.size()];
		tipoCalificacion2 = new SelectItem[listaTipoCalificacion.size()];
		tipoCalificacion3 = new SelectItem[listaTipoCalificacion.size()];
		for (int i = 0; i < listaTipoCalificacion.size(); i++) {
			DominioDetalle td1 = (DominioDetalle) listaTipoCalificacion.get(i);
			DominioDetalle td2 = (DominioDetalle) listaTipoCalificacion.get(i);
			DominioDetalle td3 = (DominioDetalle) listaTipoCalificacion.get(i);
			tipoCalificacion1[i] = new SelectItem(td1.getIdentificador()
					.getTipo(), td1.getDescripcion());
			tipoCalificacion2[i] = new SelectItem(td2.getIdentificador()
					.getTipo(), td2.getDescripcion());
			tipoCalificacion3[i] = new SelectItem(td3.getIdentificador()
					.getTipo(), td3.getDescripcion());
		}
		calificacion1Sel = ((DominioDetalle) listaTipoCalificacion
				.get(listaTipoCalificacion.size() - 1)).getIdentificador()
				.getTipo();
		calificacion2Sel = ((DominioDetalle) listaTipoCalificacion
				.get(listaTipoCalificacion.size() - 1)).getIdentificador()
				.getTipo();
		calificacion3Sel = ((DominioDetalle) listaTipoCalificacion
				.get(listaTipoCalificacion.size() - 1)).getIdentificador()
				.getTipo();

	}

	private void cargarTiposDocumentos() {
		List<MovilidadArchivo> listaArchivosObligatorios;
		listaArchivosObligatorios = servicioGeneral
				.obtenerListaArchivosMovilidad("SMEA");
		if (listaArchivosObligatorios != null
				&& listaArchivosObligatorios.size() > 0) {
			tipoDocumentoSelItem = new SelectItem[listaArchivosObligatorios
					.size()];
			for (int i = 0; i < listaArchivosObligatorios.size(); i++) {
				MovilidadArchivo mea = (MovilidadArchivo) listaArchivosObligatorios
						.get(i);
				tipoDocumentoSelItem[i] = new SelectItem(mea.getTipoArchivo()
						.getId().toString(), mea.getTipoArchivo().getId()
						.toString()
						+ "-" + mea.getTipoArchivo().getNombre());
			}
		}
	}

	public void cargarArchivosMovilidad() {
		listaArchivosObligatoriosEliminados = new ArrayList<ArchivoMovilidad>();
		listaArchivosObligatoriosSel = servicioGeneral.obtenerObjetos(
				ArchivoMovilidad.class, "from ArchivoMovilidad am "
						+ "where am.movilidad = '" + mde.getId() + "' and am.tipoMovilidad.id = 'SMEA'");
	}

	public void adicionarActividad() {
		ActividadMovilidad actividad = new ActividadMovilidad();
		boolean val = true;
		try {

			if (this.descripcionActividad == null
					|| this.descripcionActividad.equals("")
					|| this.descripcionActividad.length() > 2000) {
				// this.errores[25] = "La descripción es NO valida";
			} else {
				actividad.setDescripcion(descripcionActividad);
			}

			if (this.fechaActividad == null) {
				// this.errores[26] = "La fecha es NO ida";
			} else {
				if (this.fechaActividad.before(this.mde.getFechainicial())) {
					// this.errores[26] =
					// "La fecha debe ser posterior a la fecha de inicio del viaje";

				} else if (this.fechaActividad.after(this.mde.getFechafinal())) {
					// this.errores[26] =
					// "La fecha debe ser anterior a la fecha de fin del viaje";
				} else {
					actividad.setFecha(fechaActividad);
				}
			}

			if (this.duracionActividad == null
					|| this.duracionActividad.length() == 0) {
				// this.errores[27] = "La duración es NO valida";
			} else {
				try {
					int cantidad = Integer.parseInt(this.duracionActividad);
					if (cantidad <= 0 || cantidad > 30) {
						// this.errores[27] =
						// "Duración fuera de rango (1 - 30)";
					} else {
						actividad.setDuracion(cantidad);
					}

				} catch (Exception e) {
					// this.errores[27] = "La duración es NO valida";
				}
			}

			if (val) {
				this.descripcionActividad = "";
				this.fechaActividad = null;
				this.duracionActividad = null;
				actividad.setTipoActividad("S");
				this.listaActividades.add(actividad);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void eliminarActividad() {
		listaActividades.remove(actividadMovilidadSeleccionada);
		actividadMovilidadSeleccionada = new ActividadMovilidad();
	}

	public void eliminarArchivoObligatorio() {
		listaArchivosObligatoriosSel.remove(archivoTabla);
		if (archivoTabla.getId() != null) {
			listaArchivosObligatoriosEliminados.add(archivoTabla);
		}
		archivoTabla = new ArchivoMovilidad();
	}

	public void guardarArchivoObligatorio() {
		ArchivoMovilidad archivoMovilidad = insertarArchivoMovilidadGenerico(
				mde.getId(), archivoObligatorio, tipoDocumentoSel, "SMEA");
		if (archivoMovilidad != null) {
			listaArchivosObligatoriosSel.add(archivoMovilidad);
		}
	}

	public void descargarArchivoObligatorio() {
		descargarArchivoMovilidadGenerico(archivoTabla.getId());
	}
	
	public String atras() {
		sesion.removeAttribute("ManejadorCrearEditarSeguimientoMovilidadEstudianteArtes");
		return "successProyectosMovilidad";
	}

	public void guardarNoRealizacionParcial() {
		guardarNoRealizacion("P", true);
	}

	public void guardarNoRealizacionEnviar() {
		boolean guardo = guardarNoRealizacion("F", false);
		if (guardo) {
			// enviarNotificacionEnvioInforme();
		}
	}

	public boolean guardarNoRealizacion(String estadoSeguimiento,
			boolean parcial) {
		if (validacionNoRealizado(parcial) || parcial) {
			mde.setRealizacionMovilidad(realizacionMovilidad);
			mde.setEstadoSeguimiento(estadoSeguimiento);
			mde.setSegMovFecha(new Date());
			mde.setRazonesNoRealizacion(razonesNoRealizacion);
			servicioGeneral.guardarObjeto(mde);
			for (int i = 0; i < listaArchivosObligatoriosSel.size(); i++) {
				ArchivoMovilidad archivo = new ArchivoMovilidad();
				archivo = (ArchivoMovilidad) listaArchivosObligatoriosSel
						.get(i);
				archivo.setMovilidad(String.valueOf(mde.getId()));
				servicioGeneral.guardarObjeto(archivo);
			}
			if (listaArchivosObligatoriosEliminados != null
					&& listaArchivosObligatoriosEliminados.size() > 0) {
				for (int i = 0; i < listaArchivosObligatoriosEliminados.size(); i++) {
					ArchivoMovilidad archivoMovilidad = listaArchivosObligatoriosEliminados
							.get(i);
					servicioGeneral.eliminarObjeto(archivoMovilidad);
				}
			}
			listaArchivosObligatoriosEliminados = new ArrayList<ArchivoMovilidad>();
			String mensaje;
			if (!parcial) {
				mensaje = "Seguimiento de movilidad guardado satisfactoriamente.";
				consulta = true;
			} else {
				mensaje = "Seguimiento de movilidad guardado parcialmente.";
			}
			FacesContext.getCurrentInstance().addMessage("msgs",
					new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, ""));
			sesion.removeAttribute("manejadorConsultaMovilidadesInvestigador");
			sesion.removeAttribute("manejadorAprobacionMovilidadSeguimiento");
		} else {
			return false;
		}
		return true;
	}

	public boolean validacionNoRealizado(boolean parcial) {
		if (!parcial) {
			boolean bandera = true;
			if (razonesNoRealizacion == null
					|| (razonesNoRealizacion != null && razonesNoRealizacion
							.length() == 0)) {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Debe ingresar las razones de no realización",
						"Debe ingresar las razones de no realización");
				bandera = false;
			}
			return bandera;
		} else {
			return true;
		}
	}

	public void guardarParcialmente() {
		guardar("P", true);
	}

	public void guardarEnviar() {
		boolean guardo = guardar("F", false);
		if (guardo) {
			// enviarNotificacionEnvioInforme();
		}
	}

	public boolean guardar(String estadoSeguimiento, boolean parcial) {
		if (validacion(parcial) || parcial) {

			listaArchivosObligatoriosEliminados = new ArrayList<ArchivoMovilidad>();
			mde.setCalificacionA(this.calificacion1Sel);
			mde.setCalificacionB(this.calificacion2Sel);
			mde.setCalificacionC(this.calificacion3Sel);

			Calendar actual = Calendar.getInstance();
			Date date = actual.getTime();
			mde.setSegMovFecha(date);
			mde.setSegMovDescripcion(this.descripcionMovilidad);
			mde.setEstadoSeguimiento(estadoSeguimiento);
			mde.setSegMovDescripcion(descripcionMovilidad);
			mde.setExperienciaResidencia(experiencia);
			
			Long totalApoyoSuma = Long.parseLong(mde.getTotalTiquetesUN()) + Long.parseLong(mde.getTotalTallerUN()) + Long.parseLong(mde.getTotalAlojamientoUN()) + Long.parseLong(mde.getTotalAlimentacionUN()) + Long.parseLong(mde.getTotalMaterialesUN()) + Long.parseLong(mde.getTotalTransporteUN()) + Long.parseLong(mde.getTotalSocializacionUN());

			mde.setTotalApoyoUN(totalApoyoSuma.toString());

			for (int i = 0; i < listaArchivosObligatoriosSel.size(); i++) {
				ArchivoMovilidad archivo = new ArchivoMovilidad();
				archivo = (ArchivoMovilidad) listaArchivosObligatoriosSel
						.get(i);
				archivo.setMovilidad(String.valueOf(mde.getId()));
				if (archivo.getId() == null) {
					servicioGeneral.guardarObjeto(archivo);
				}
			}
			
			Set act = new HashSet();
			for (Iterator it = listaActividades.iterator(); it.hasNext();) {
				ActividadMovilidad a = (ActividadMovilidad) it.next();

				act.add(a);
			}
			mde.setActividades(act);
			
			servicioGeneral.guardarObjeto(mde);
			String mensaje;
			if (!parcial) {
				mensaje = "Seguimiento de movilidad guardado satisfactoriamente.";
				consulta = true;
			} else {
				mensaje = "Seguimiento de movilidad guardado parcialmente.";
			}
			FacesContext.getCurrentInstance().addMessage("msgs",
					new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, ""));
			sesion.removeAttribute("manejadorConsultaMovilidadesInvestigador");
			sesion.removeAttribute("manejadorAprobacionMovilidadSeguimiento");
		} else {
			return false;
		}
		return true;
	}

	public boolean validacion(boolean parcial) {
		if (!parcial) {
			boolean bandera = true;

			return bandera;

		}else {
			return true;
		}
	}

	public MovilidadEstudiantesArtes getMde() {
		return mde;
	}

	public void setMde(MovilidadEstudiantesArtes mde) {
		this.mde = mde;
	}

	public boolean isConsulta() {
		return consulta;
	}

	public void setConsulta(boolean consulta) {
		this.consulta = consulta;
	}

	public String getPersonaMovilidad() {
		return personaMovilidad;
	}

	public void setPersonaMovilidad(String personaMovilidad) {
		this.personaMovilidad = personaMovilidad;
	}

	public String getCalificacion1Sel() {
		return calificacion1Sel;
	}

	public void setCalificacion1Sel(String calificacion1Sel) {
		this.calificacion1Sel = calificacion1Sel;
	}

	public String getCalificacion2Sel() {
		return calificacion2Sel;
	}

	public void setCalificacion2Sel(String calificacion2Sel) {
		this.calificacion2Sel = calificacion2Sel;
	}

	public String getCalificacion3Sel() {
		return calificacion3Sel;
	}

	public void setCalificacion3Sel(String calificacion3Sel) {
		this.calificacion3Sel = calificacion3Sel;
	}

	public SelectItem[] getTipoCalificacion1() {
		return tipoCalificacion1;
	}

	public void setTipoCalificacion1(SelectItem[] tipoCalificacion1) {
		this.tipoCalificacion1 = tipoCalificacion1;
	}

	public SelectItem[] getTipoCalificacion2() {
		return tipoCalificacion2;
	}

	public void setTipoCalificacion2(SelectItem[] tipoCalificacion2) {
		this.tipoCalificacion2 = tipoCalificacion2;
	}

	public SelectItem[] getTipoCalificacion3() {
		return tipoCalificacion3;
	}

	public void setTipoCalificacion3(SelectItem[] tipoCalificacion3) {
		this.tipoCalificacion3 = tipoCalificacion3;
	}

	public String getFacultadDocente() {
		return facultadDocente;
	}

	public void setFacultadDocente(String facultadDocente) {
		this.facultadDocente = facultadDocente;
	}

	public String getSedeDocente() {
		return sedeDocente;
	}

	public void setSedeDocente(String sedeDocente) {
		this.sedeDocente = sedeDocente;
	}

	public String getNombrePais() {
		return nombrePais;
	}

	public void setNombrePais(String nombrePais) {
		this.nombrePais = nombrePais;
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

	public List<ActividadMovilidad> getListaActividades() {
		return listaActividades;
	}

	public void setListaActividades(List<ActividadMovilidad> listaActividades) {
		this.listaActividades = listaActividades;
	}

	public ActividadMovilidad getActividadMovilidadSeleccionada() {
		return actividadMovilidadSeleccionada;
	}

	public void setActividadMovilidadSeleccionada(
			ActividadMovilidad actividadMovilidadSeleccionada) {
		this.actividadMovilidadSeleccionada = actividadMovilidadSeleccionada;
	}

	public String getDescripcionMovilidad() {
		return descripcionMovilidad;
	}

	public void setDescripcionMovilidad(String descripcionMovilidad) {
		this.descripcionMovilidad = descripcionMovilidad;
	}

	public String getExperiencia() {
		return experiencia;
	}

	public void setExperiencia(String experiencia) {
		this.experiencia = experiencia;
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

	public DefaultUploadedFile getArchivoObligatorio() {
		return archivoObligatorio;
	}

	public void setArchivoObligatorio(DefaultUploadedFile archivoObligatorio) {
		this.archivoObligatorio = archivoObligatorio;
	}

	public List<ArchivoMovilidad> getListaArchivosObligatoriosSel() {
		return listaArchivosObligatoriosSel;
	}

	public void setListaArchivosObligatoriosSel(
			List<ArchivoMovilidad> listaArchivosObligatoriosSel) {
		this.listaArchivosObligatoriosSel = listaArchivosObligatoriosSel;
	}

	public List<ArchivoMovilidad> getListaArchivosObligatoriosEliminados() {
		return listaArchivosObligatoriosEliminados;
	}

	public void setListaArchivosObligatoriosEliminados(
			List<ArchivoMovilidad> listaArchivosObligatoriosEliminados) {
		this.listaArchivosObligatoriosEliminados = listaArchivosObligatoriosEliminados;
	}

	public ArchivoMovilidad getArchivoTabla() {
		return archivoTabla;
	}

	public void setArchivoTabla(ArchivoMovilidad archivoTabla) {
		this.archivoTabla = archivoTabla;
	}

	public String getRealizacionMovilidad() {
		return realizacionMovilidad;
	}

	public void setRealizacionMovilidad(String realizacionMovilidad) {
		this.realizacionMovilidad = realizacionMovilidad;
	}

	public String getRazonesNoRealizacion() {
		return razonesNoRealizacion;
	}

	public void setRazonesNoRealizacion(String razonesNoRealizacion) {
		this.razonesNoRealizacion = razonesNoRealizacion;
	}

	public String getEstudianteMovilidad() {
		return estudianteMovilidad;
	}

	public void setEstudianteMovilidad(String estudianteMovilidad) {
		this.estudianteMovilidad = estudianteMovilidad;
	}


}
