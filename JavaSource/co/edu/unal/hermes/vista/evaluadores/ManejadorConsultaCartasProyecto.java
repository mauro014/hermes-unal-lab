package co.edu.unal.hermes.vista.evaluadores;

import java.util.Date;
import java.util.List;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.Archivo;
import co.edu.unal.hermes.modelo.ArchivoResumen;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.ObservacionSeguimiento;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.ProyectoCompromiso;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.seguimiento.DetalleAdicionPresupuesto;
import co.edu.unal.hermes.vista.proyectos.ProyectoVista;
import co.edu.unal.hermes.vista.seguimiento.ManejadorBaseSeguimiento;

public class ManejadorConsultaCartasProyecto extends ManejadorBaseSeguimiento {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private ProyectoVista proyectoVista;
    private List<Archivo> listaArchivosProyecto;
    private ArchivoResumen archivoResumenSeleccionado;
    private List<ProyectoCompromiso> listaCompromisos;
    private ObservacionSeguimiento observacionSeleccionada;
    
    private List<DetalleAdicionPresupuesto> listaSolicitudAdicionPresupuesto;

    public ManejadorConsultaCartasProyecto() {
        boolean consultaProyecto = false;
        try {
            proyectoVista = (ProyectoVista) sesion.getAttribute("proyectoCarta");
            crearProyecto(proyectoVista);

            if (proyectoActual != null) {
                consultaProyecto = true;
                cargarObservacionesSeguimiento(proyectoVista.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (consultaProyecto) {
            cargarCartasProyecto(proyectoActual.getId());
            obtenerCoordinadorSeguimiento(proyectoActual.getId());
            obtenerCoordinadorEvaluacion(proyectoActual.getId());
            obtenerCoordinadorRequisitos(proyectoActual.getId());
            calcularPeriodoSuspension();
            listaArchivosProyecto = servicioProyecto.obtenerNombresArchivos(proyectoActual);
            cargarCompromisos();

            // Se carga la información adicional de las solicitudes.
            cargarDatosAdicionalesSolicitud(proyectoActual.getListaSolicitudesEnviadas());
            proyectoActual = servicioProyecto.obtenerProyecto(proyectoActual.getId(), ProyectoDAOHibernate.TODO_POR_ID,
                    true);
            cargarFechasProyecto(proyectoActual);
            detallesCambiosRubro = cargarDetallesCambioRubrosAprobados(proyectoActual.getListaSolicitudesEnviadas());
            listaSolicitudAdicionPresupuesto = cargarDetallesAdicionesPresupuestoAprobadas(proyectoActual.getListaSolicitudesEnviadas());
            informacionFinaciera = cargarFinanciacionActualProyecto(getDetallesCambiosRubro(), proyectoActual, listaSolicitudAdicionPresupuesto);
        }

    }
    
    public void guardarRespuestaObservacionSeg(){
    	if(observacionSeleccionada != null){
    		if(observacionSeleccionada.getRespuesta().trim().length()<=2000){
    			if(observacionSeleccionada.getRespuesta() != null && !observacionSeleccionada.getRespuesta().equals("")){
            		observacionSeleccionada.setFechaRespuesta(new Date());
            		servicioGeneral.guardarObjeto(observacionSeleccionada);
            		
        			CorreoPlantilla correoPlantillaTemporal = cargarPlantilla(297);
                    Correo correo = new Correo();
                    correo.setOrigen(Correo.CORREO_HERMES);
                    Persona coordinador = (Persona)  servicioProyecto.obtenerCoordinadorProyecto(proyectoActual.getId());
					if (coordinador != null) {
						correo.adicionarDireccion(coordinador.getEmail());
						//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
						String cuerpoCorreo = editarCorreo(coordinador, correoPlantillaTemporal,
								observacionSeleccionada.getRespuesta(), proyectoActual);
						correo.setCuerpo(cuerpoCorreo);
						correo.setAsunto(correoPlantillaTemporal.getAsunto());
						servicioCorreo.enviarCorreo(correo);
					}
                }
        	}
    	}
    }
    
    public String editarCorreo(Persona personaAux, CorreoPlantilla correo, String respuesta, Proyecto proyecto) {
        try {
            String correoAux = correo.getCuerpo();
            String coordinador = personaAux.getNombreCompleto();            
            correo.setAsunto(correo.getAsunto().replaceAll("<<PRY_ID>>", proyecto.getId().toString()));
            correoAux = correoAux.replaceAll("<<COORDINADOR>>", coordinador);
            correoAux = correoAux.replaceAll("<<PRY_ID>>", proyecto.getId().toString());
            correoAux = correoAux.replaceAll("<<RESPUESTA>>", respuesta);
            String[] parts = correoAux.split("<<RESPUESTA>>");
            if (parts.length > 1) {
                String concepto;
                if (esCadenaVacia(respuesta)) {
                    concepto = "No se ingresaron comentarios.";
                } else {
                    concepto = respuesta;
                }
                String part1 = parts[0];
                String part2 = parts[1];
                correoAux = part1 + concepto + part2;
            }
            return correoAux;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return "";
    }

    public void crearProyecto(ProyectoVista pry) {
        proyectoActual.setId(pry.getId());
        proyectoActual.setNombre(pry.getNombre());
        proyectoActual.setCodigoQuipu(pry.getCodigoQuipu());
        proyectoActual.setFechaTentativaInicio(pry.getFechaTentativaInicio());
        proyectoActual.setDuracion(pry.getDuracion());
        EstadoProyecto estpry = new EstadoProyecto();
        estpry.setId(pry.getEstadoProyectoId());
        estpry.setNombre(pry.getEstadoProyecto());
        proyectoActual.setEstadoProyecto(estpry);
        proyectoActual.setDuracionAcumulada(pry.getDuracionAcumulada());
        proyectoActual.setDuracionDiasAcumulada(pry.getDuracionDiasAcumulada());
        ConvocatoriaPadre convocatoriaPadre = new ConvocatoriaPadre();
        convocatoriaPadre.setTitulo(pry.getConvocatoriaPadre());
        Convocatoria convocatoria = new Convocatoria();
        convocatoria.setTitulo(pry.getNombreModalidad());
        convocatoria.setPadre(convocatoriaPadre);
        if (pry.isEsPermisoMarco()) {
            convocatoria.setId(150L);
        } else if (pry.isEsContratoAccesoBiodiversidad()) {
            convocatoria.setId(22L);
        }
        proyectoActual.setModalidad(convocatoria);

        if (pry.isEsJornadaDocente()) {
            proyectoActual.setEsJornadaDocente("Y");
        }
        if (pry.isEsPryContrapartida()) {
            proyectoActual.setEsPryContrapartida("Y");
        }

    }

    // Descargar archivo proyecto
    public void descargarArchivoProyecto() {
        Long id = archivoResumenSeleccionado.getId();
        descargarArchivoProyectoGenerico(id, proyectoActual.getId());
    }

    // Cargar compromisos proyecto
    public void cargarCompromisos() {
        String hql = "select #id pc.id, #tipoInforme pc.tipoInforme, #fechaVencimiento pc.fechaVencimiento, "
                + "#cumplido pc.cumplido, #numeroNotificaciones pc.numeroNotificaciones, "
                + "#fechaNotificacion pc.fechaNotificacion, #fechaVencProrroga pc.fechaVencProrroga"
                + " from ProyectoCompromiso pc WHERE " + "pc.proyecto = '" + proyectoActual.getId() + "'";
        listaCompromisos = servicioGeneral.obtenerObjetosLimitado(ProyectoCompromiso.class, hql);
    }

    public ProyectoVista getProyectoVista() {
        return proyectoVista;
    }

    public void setProyectoVista(ProyectoVista proyectoVista) {
        this.proyectoVista = proyectoVista;
    }

    public List<Archivo> getListaArchivosProyecto() {
        return listaArchivosProyecto;
    }

    public void setListaArchivosProyecto(List<Archivo> listaArchivosProyecto) {
        this.listaArchivosProyecto = listaArchivosProyecto;
    }

    public ArchivoResumen getArchivoResumenSeleccionado() {
        return archivoResumenSeleccionado;
    }

    public void setArchivoResumenSeleccionado(ArchivoResumen archivoResumenSeleccionado) {
        this.archivoResumenSeleccionado = archivoResumenSeleccionado;
    }

    public List<ProyectoCompromiso> getListaCompromisos() {
        return listaCompromisos;
    }

    public void setListaCompromisos(List<ProyectoCompromiso> listaCompromisos) {
        this.listaCompromisos = listaCompromisos;
    }

	public ObservacionSeguimiento getObservacionSeleccionada() {
		return observacionSeleccionada;
	}

	public void setObservacionSeleccionada(
			ObservacionSeguimiento observacionSeleccionada) {
		this.observacionSeleccionada = observacionSeleccionada;
	}

	public List<DetalleAdicionPresupuesto> getListaSolicitudAdicionPresupuesto() {
		return listaSolicitudAdicionPresupuesto;
	}

	public void setListaSolicitudAdicionPresupuesto(
			List<DetalleAdicionPresupuesto> listaSolicitudAdicionPresupuesto) {
		this.listaSolicitudAdicionPresupuesto = listaSolicitudAdicionPresupuesto;
	}
}