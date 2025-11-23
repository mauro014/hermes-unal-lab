package co.edu.unal.hermes.vista.seguimiento;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;

import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.Estudiante;
import co.edu.unal.hermes.modelo.HistoricoCambioIntegrantes;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoInvestigador;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorAgregarDetalleEstudiante extends ManejadorBase {
	
	private Proyecto proyectoActual;
	private List<InvestigadorProyecto> listaEstudiantesSinDetalle;
	private boolean mostrarOpcionesAdicion = false;
	private String tipoVinculacionId;
	private SelectItem[] tipoVinculacionItems;
	private String[] tipoVinculacionSinDetalle;
	private TipoDocumento tipoDocumentoCoInvEquipo;
	private SelectItem[] tipoDocumentoItem;
	private String documentoCoinvEquipo;
	private Integer horasParticipante;
	private Integer tiempoTotalParticipante;
	private String funcionInvestigador;
	private HistoricoCambioIntegrantes hci;
	
	public ManejadorAgregarDetalleEstudiante (){
		Long idPry = (Long) sesion.getAttribute("idProyectoDetalleEst");
		proyectoActual = (Proyecto) servicioProyecto.obtenerProyectosXId(idPry).get(0);
		listaEstudiantesSinDetalle = new ArrayList<InvestigadorProyecto>();
		tipoDocumentoCoInvEquipo = new TipoDocumento();
		
		if(!proyectoActual.getListaInvestigadoresProyectoSinDatos().isEmpty()){
			for (Iterator iterator = proyectoActual.getListaInvestigadoresProyectoSinDatos().iterator(); iterator.hasNext();) {
				InvestigadorProyecto invProyecto = (InvestigadorProyecto) iterator.next();
				if(invProyecto.getTipo().getDocumento().startsWith("ESPO")
					|| invProyecto.getTipo().getDocumento().startsWith("ESPR")
					|| invProyecto.getTipo().getDocumento().startsWith("EUDC")){
					listaEstudiantesSinDetalle.add(invProyecto);
				}
			}
			tipoVinculacionSinDetalle = new String[listaEstudiantesSinDetalle.size()];
			for (int i = 0; i < listaEstudiantesSinDetalle.size(); i++) {
				InvestigadorProyecto ip = listaEstudiantesSinDetalle.get(i);
				tipoVinculacionSinDetalle[i] = ip.getTipo().getDocumento().substring(0,4);
			}
			cargarListaTipoVinculacion();
			cargarListaTipoDocumento();
		}
	}
	
	public void cargarListaEstudiantesSinDetalle(){
		listaEstudiantesSinDetalle = new ArrayList<InvestigadorProyecto>();
		if(!proyectoActual.getListaInvestigadoresProyectoSinDatos().isEmpty()){
			for (Iterator iterator = proyectoActual.getListaInvestigadoresProyectoSinDatos().iterator(); iterator.hasNext();) {
				InvestigadorProyecto invProyecto = (InvestigadorProyecto) iterator.next();
				if(invProyecto.getTipo().getDocumento().startsWith("ESPO")
					|| invProyecto.getTipo().getDocumento().startsWith("ESPR")){
					listaEstudiantesSinDetalle.add(invProyecto);
				}
			}
		}
	}
	
	public void cargarListaTipoVinculacion(){
		if (tipoVinculacionItems != null && tipoVinculacionItems.length > 0) {
            return;
        } else {
            Convocatoria convocatoria = (Convocatoria) proyectoActual.getModalidad();
            // Tipo de vinculación
            List<TipoInvestigador> listaInvestigador = new ArrayList<TipoInvestigador>();
            List<TipoInvestigador> listaInvestigadorAux = new ArrayList<TipoInvestigador>();
            
            if(convocatoria.getId().equals(Convocatoria.MODALIDAD_CONVOCATORIA_EXTERNA)){
            	listaInvestigador = obtenerTipoVinculacionConvocatoria(convocatoria,false);	
            }else{
            	listaInvestigador = obtenerTipoVinculacionConvocatoria(convocatoria,true);
            }
            
            if(!listaInvestigador.isEmpty()){
            	for (int i = 0; i < listaInvestigador.size(); i++) {
            		//System.out.println("listaTipoVinculacion.size = : " + listaInvestigador.size());
                	TipoInvestigador tpInvestigador = new TipoInvestigador();
                	tpInvestigador = listaInvestigador.get(i);
                	if(tpInvestigador != null 
                			&& tpInvestigador.getDocumento() != null
                			&& (tpInvestigador.getDocumento().startsWith("ESPO")
                					|| tpInvestigador.getDocumento().startsWith("ESPR")
                					|| tpInvestigador.getDocumento().startsWith("EUDC"))){
                		for (int j = 0; j < tipoVinculacionSinDetalle.length; j++) {
                			//System.out.println("tpVinSinDetalle: " + tipoVinculacionSinDetalle[j]);
							if(tpInvestigador.getDocumento().startsWith(tipoVinculacionSinDetalle[j])){
								listaInvestigadorAux.add(tpInvestigador);
							}
						}
                	}
    			}
            	if(!listaInvestigadorAux.isEmpty()){
            		tipoVinculacionItems = new SelectItem[listaInvestigadorAux.size()];
                    for (int i = 0; i < listaInvestigadorAux.size(); i++) {
                        TipoInvestigador tipoInvestigador = (TipoInvestigador) listaInvestigadorAux.get(i);
                        tipoVinculacionItems[i] = new SelectItem(tipoInvestigador.getId(), tipoInvestigador.getNombre());
                    }
            	}
            }
        }
	}
	
	public boolean validarDatosEstudiante(){
		boolean valida = true;
		
		if(tipoVinculacionId == null || tipoVinculacionId.equals("")){
			mensajeError("Por favor seleccione el tipo de vinculación del estudiante.");
			valida = false;
		}
		
		if(tipoDocumentoCoInvEquipo.getId() == null || tipoDocumentoCoInvEquipo.getId().equals("")){
			mensajeError("Por favor seleccione el tipo de documento de identificación del estudiante.");
			valida = false;
		}
		
		if(documentoCoinvEquipo == null || documentoCoinvEquipo.equals("")){
			mensajeError("Por favor ingrese el número de identificación del estudiante.");
			valida = false;
		}
		
		if(horasParticipante == null
				|| horasParticipante.equals("")
				|| horasParticipante.equals(new Integer(0))){
			mensajeError("La dedicación del estudiante debe ser un valor numérico mayor a cero.");
			valida = false;
		}else if(horasParticipante > new Integer(99)){
			mensajeError("La dedicación del estudiante debe tener máximo dos caracteres.");
			valida = false;
		}
		
		if(tiempoTotalParticipante == null 
				|| tiempoTotalParticipante.equals("")
				|| tiempoTotalParticipante.equals(new Integer(0))){
			mensajeError("La vinculación total del estudiante debe ser un valor numérico mayor a cero.");
			valida = false;
		}else if(tiempoTotalParticipante > new Integer(99)){
			mensajeError("La vinculación total del estudiante debe tener máximo dos caracteres.");
			valida = false;
		}else if(tiempoTotalParticipante > proyectoActual.getDuracionAcumulada()){
			mensajeError("La vinculación total de estudiante debe ser menor a la duración total del proyecto.");
			valida = false;
		}
		
		return valida;
	}
	
	public void agregarEstudiante() {
		if(validarDatosEstudiante()){
			IdPersona idEst = new IdPersona(this.documentoCoinvEquipo, this.tipoDocumentoCoInvEquipo.getId());
			Estudiante e = servicioPersona.obtenerEstudiante(idEst);
			TipoInvestigador tpVincEst = (TipoInvestigador) servicioGeneral.obtenerObjetoXID(TipoInvestigador.class, tipoVinculacionId).get(0);
			System.out.println("tpVincEst.getTipoVinculacion() = "+tpVincEst.getTipoVinculacion());
			Convocatoria convocatoria = (Convocatoria) proyectoActual.getModalidad();

			if (e != null) { // Se encontró como estudiante

				if (!validarVinculacionInvestigador(e.getPlan().getTipo().toString(), tpVincEst)) {
					mensajeError("El estudiante ingresado no pertenece al nivel del tipo de vinculación seleccionado. El plan de estudios al que pertenece el estudiante es: " + e.getPlan().getNombre());
					return;
				}

				Investigador nuevoInvestigador = servicioPersona.obtenerInvestigador(idEst);

				if (nuevoInvestigador == null) {
					InvestigadorInterno nvoinv = e.convertirAInvestigador();

					if (e.getDependencia() != null) {
						nvoinv.setDependencia(e.getDependencia());
					} else {
						Dependencia dependencia = servicioDependencia.obtenerDependencia("1");
						e.setDependencia(dependencia);
						nvoinv.setDependencia(e.getDependencia());
					}

					Persona per = servicioPersona.obtenerPersona(nvoinv.getId());

					if (per != null) {
						servicioPersona.insertarNuevoInvestigador(nvoinv);
						servicioPersona.insertaInterno(nvoinv);
					} else {
						servicioPersona.guardarInvestigador(nvoinv);
					}
					nuevoInvestigador = servicioPersona.obtenerInvestigador(nvoinv.getId());
				}

				// Agregar participante a la listaparticipante
				InvestigadorProyecto participanteNuevo = new InvestigadorProyecto();
				participanteNuevo.setInvestigador(nuevoInvestigador);
				participanteNuevo.setDedicacionHorasSemana(Short.parseShort(horasParticipante.toString()));
				participanteNuevo.setTotalHorasVinculacion(Double.valueOf(tiempoTotalParticipante));
				if (convocatoria.isMostrarActividadesInvestigador()) {
					participanteNuevo.setFuncion("Estudiante de: " + e.getPlan().getNombre() + "\n " + funcionInvestigador);
				} else {
					participanteNuevo.setFuncion(InvestigadorProyecto.FUNCION_PARTICIPANTE_BASICO);
				}
				participanteNuevo.setProyecto(proyectoActual);
				participanteNuevo.setTipo(tpVincEst);
				participanteNuevo.setDependencia(e.getDependencia());
				participanteNuevo.setPlan(e.getPlan());
				participanteNuevo.setValorPagar(0L);
				
				proyectoActual.adicionarInvestigadorProyecto(participanteNuevo);
				servicioGeneral.guardarObjeto(proyectoActual);
				
				//ACTUALIZAR CANTIDAD DE ESTUDIANTES SIN DETALLES
				for(Iterator iterator = listaEstudiantesSinDetalle.iterator(); iterator.hasNext();) {
					InvestigadorProyecto participanteSinDetalle = (InvestigadorProyecto) iterator.next();
					if(participanteNuevo.getTipo().equals(participanteSinDetalle.getTipo())){
						double cantidadEstudiantes = participanteSinDetalle.getDedicacionHorasSemana();
						if((cantidadEstudiantes - 1) >= 0){
							participanteSinDetalle.setDedicacionHorasSemana((short) (cantidadEstudiantes - 1));
							servicioGeneral.guardarObjeto(participanteSinDetalle);
						}else{
							servicioGeneral.eliminarObjeto(participanteSinDetalle);
						}
					}
				}
				
				// CREACIÓN DE HISTÓRICO
				hci = new HistoricoCambioIntegrantes();
				hci.setProyecto(proyectoActual);
				hci.setFechaIngreso(getToday());
				hci.setIntegrante(participanteNuevo.getInvestigador());
				hci.setTipoInvestigadorProyecto(participanteNuevo.getTipo());
				hci.setMesesDedidacion(participanteNuevo.getTotalHorasVinculacion());
				hci.setHorasDedidacion(participanteNuevo.getDedicacionHorasSemana());
				
				hci.setEstadoCivil(participanteNuevo.getInvestigador().getEstadoCivil());
				Estudiante est = servicioPersona
						.obtenerEstudiante(participanteNuevo.getInvestigador().getId());
				hci.setPlanEstudios(est.getPlan());
				hci.setSemestreActual(est.getSemestreActual());
				hci.setDependencia(est.getDependencia());
				hci.setSede(est.getDependencia().getSede());
				servicioGeneral.guardarObjeto(hci);
				
				cargarListaEstudiantesSinDetalle();
				
				documentoCoinvEquipo = "";
				horasParticipante = 0;
				tiempoTotalParticipante = 0;
				funcionInvestigador = "";
				tipoVinculacionId = "";
				
				mostrarOpcionesAdicion = false;

			} else { // No se encontró como estudiante
				mensajeError("El documento ingresado no pertenece a un estudiante.");
			}
		}
	}
	
	public String atras() {
        //sesion.removeAttribute("idProyectoDetalleEst");
        sesion.removeAttribute("manejadorAgregarDetalleEstudiante");
        return "volverPrincipalProyectos";
    }
	
	private boolean esRepetidoEquipoConDatos() {
		// Validar que no se encuentre en participantes
		if (validarAgregadoEquipo(documentoCoinvEquipo, tipoDocumentoCoInvEquipo.getId(), proyectoActual.getListaInvestigadoresProyectoConDatos())) {
			mensajeError("La persona ingresada ya se encuentra vinculada al proyecto.");
			return true;
		}
		return false;
	}
	
	private boolean validarAgregadoEquipo(String id, String tipo, List<InvestigadorProyecto> listaParticipantes) {
		if (!esListaVacia(listaParticipantes)) {
			for (InvestigadorProyecto invPry : listaParticipantes) {
				if (invPry.getInvestigador().getId().getDocumento().equals(id) && invPry.getInvestigador().getId().getTipoDocumento().equals(tipo)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void mensajeError(String mensaje) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, mensaje);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	private boolean validarVinculacionInvestigador(String tipoVinculacion, TipoInvestigador tipoInvestigador) {
		if (!esCadenaVacia(tipoInvestigador.getTipoVinculacion())) {
			int encuentra = tipoInvestigador.getTipoVinculacion().indexOf("-" + tipoVinculacion + "-");
			return encuentra > -1;
		}
		return false;
	}
	
	public void cargarListaTipoDocumento(){
		List<TipoDocumento> listaTipoDocumento = servicioGeneral.obtenerTiposDeDocumento();
		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			TipoDocumento td = listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
		}
	}
	
	public boolean mostrarPanelDetalleEstudiante(){
		mostrarOpcionesAdicion = true;
		return mostrarOpcionesAdicion;
	}

	public Proyecto getProyectoActual() {
		return proyectoActual;
	}

	public void setProyectoActual(Proyecto proyectoActual) {
		this.proyectoActual = proyectoActual;
	}

	public List<InvestigadorProyecto> getListaEstudiantesSinDetalle() {
		return listaEstudiantesSinDetalle;
	}

	public void setListaEstudiantesSinDetalle(
			List<InvestigadorProyecto> listaEstudiantesSinDetalle) {
		this.listaEstudiantesSinDetalle = listaEstudiantesSinDetalle;
	}

	public boolean isMostrarOpcionesAdicion() {
		return mostrarOpcionesAdicion;
	}

	public void setMostrarOpcionesAdicion(boolean mostrarOpcionesAdicion) {
		this.mostrarOpcionesAdicion = mostrarOpcionesAdicion;
	}

	public String getTipoVinculacionId() {
		return tipoVinculacionId;
	}

	public void setTipoVinculacionId(String tipoVinculacionId) {
		this.tipoVinculacionId = tipoVinculacionId;
	}

	public SelectItem[] getTipoVinculacionItems() {
		return tipoVinculacionItems;
	}

	public void setTipoVinculacionItems(SelectItem[] tipoVinculacionItems) {
		this.tipoVinculacionItems = tipoVinculacionItems;
	}

	public TipoDocumento getTipoDocumentoCoInvEquipo() {
		return tipoDocumentoCoInvEquipo;
	}

	public void setTipoDocumentoCoInvEquipo(TipoDocumento tipoDocumentoCoInvEquipo) {
		this.tipoDocumentoCoInvEquipo = tipoDocumentoCoInvEquipo;
	}

	public SelectItem[] getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}

	public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
		this.tipoDocumentoItem = tipoDocumentoItem;
	}

	public String getDocumentoCoinvEquipo() {
		return documentoCoinvEquipo;
	}

	public void setDocumentoCoinvEquipo(String documentoCoinvEquipo) {
		this.documentoCoinvEquipo = documentoCoinvEquipo;
	}

	public Integer getHorasParticipante() {
		return horasParticipante;
	}

	public void setHorasParticipante(Integer horasParticipante) {
		this.horasParticipante = horasParticipante;
	}

	public Integer getTiempoTotalParticipante() {
		return tiempoTotalParticipante;
	}

	public void setTiempoTotalParticipante(Integer tiempoTotalParticipante) {
		this.tiempoTotalParticipante = tiempoTotalParticipante;
	}

	public String getFuncionInvestigador() {
		return funcionInvestigador;
	}

	public void setFuncionInvestigador(String funcionInvestigador) {
		this.funcionInvestigador = funcionInvestigador;
	}

}