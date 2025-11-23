package co.edu.unal.hermes.vista.convocatorias;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.context.FacesContext;

import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.ConvocatoriaPadreParametrizacion;
import co.edu.unal.hermes.modelo.CorteConvocatoria;
import co.edu.unal.hermes.modelo.EstadoConvocatoria;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Modalidad;
import co.edu.unal.hermes.modelo.MovilidadDocentesExterior;
import co.edu.unal.hermes.modelo.MovilidadEstudiantesPosgrado;
import co.edu.unal.hermes.modelo.MovilidadVisitanteExterior;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.RestriccionConvocatoria;
import co.edu.unal.hermes.modelo.Rol;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.utils.Navegacion;
import co.edu.unal.hermes.vista.Error;

public class ManejadorConsultaConvocatorias extends ManejadorConsultaConvocatoriasBase {

    private static final long serialVersionUID = 2354823498305053340L;

    private List<ConvocatoriaPadre> listaConvocatoriasPregrado;
    private List<ConvocatoriaPadre> listaConvocatoriasPosgrado;
    private List<ConvocatoriaPadre> listaConvocatoriasProyectos;
    private List<ConvocatoriaPadre> listaConvocatoriasInnovacion;
    private List<ConvocatoriaPadre> listaConvocatoriasInternacionalizacion;
    private List<ConvocatoriaPadre> listaConvocatoriasDifusion;
    private List<ConvocatoriaPadre> listaConvocatoriasLaboratorios;
    private List<ConvocatoriaPadre> listaConvocatoriasPublicaciones;
    private List<ConvocatoriaPadre> listaConvocatoriasSedesFacultades;
    private List<ConvocatoriaPadre> listaConvocatoriasProgramasApoyo;
    private List<ConvocatoriaPadre> listaConvocatoriasOtras;

    private String informacionAdicionalConvocatoria;

    // Variables para la vista
    private boolean convocatoriasSistemaInvestigacion;

    public ManejadorConsultaConvocatorias() {

        super();

        // Se inicializan las listas de la vista.
        listaConvocatoriasPregrado = new ArrayList<ConvocatoriaPadre>();
        listaConvocatoriasPosgrado = new ArrayList<ConvocatoriaPadre>();
        listaConvocatoriasProyectos = new ArrayList<ConvocatoriaPadre>();
        listaConvocatoriasInnovacion = new ArrayList<ConvocatoriaPadre>();
        listaConvocatoriasInternacionalizacion = new ArrayList<ConvocatoriaPadre>();
        listaConvocatoriasDifusion = new ArrayList<ConvocatoriaPadre>();
        listaConvocatoriasLaboratorios = new ArrayList<ConvocatoriaPadre>();
        listaConvocatoriasPublicaciones = new ArrayList<ConvocatoriaPadre>();
        listaConvocatoriasSedesFacultades = new ArrayList<ConvocatoriaPadre>();
        listaConvocatoriasProgramasApoyo = new ArrayList<ConvocatoriaPadre>();
        listaConvocatoriasOtras = new ArrayList<ConvocatoriaPadre>();

        convocatoriasSistemaInvestigacion = false;

        // Se cargan las convocatorias padre.
        List<String> listaEstadoConvocatoriasPadre = new ArrayList<String>();
        listaEstadoConvocatoriasPadre.add(EstadoConvocatoria.ACTIVA);
//        listaEstadoConvocatoriasPadre.add(EstadoConvocatoria.CREACION);

        /****************************************************************
         * // listaEstadoConvocatoriasPadre.add(EstadoConvocatoria.CREACION); //
         * listaEstadoConvocatoriasPadre.add("313"); //
         * listaEstadoConvocatoriasPadre.add("246"); //
         * listaEstadoConvocatoriasPadre.add("234"); /* List <Parametro>
         * parametros = this.servicioGeneral
         * .obtenerListaObjetosWhere(Parametro.class, "WHERE p.nombre =
         * 'CAT_CONVOCATORIA'");
         ******************************************************************/

        List<ConvocatoriaPadre> listaConvocatoriasPadre = servicioModalidad
                .obtenerConvocatoriasPadreEnEstados(listaEstadoConvocatoriasPadre);

        if (listaConvocatoriasPadre != null) {

            boolean esISBN;
            boolean esConvLibrosEdiorial;

            for (Iterator<ConvocatoriaPadre> i = listaConvocatoriasPadre.iterator(); i.hasNext();) {

                esISBN = false;

                ConvocatoriaPadre convocatoriaPadre = i.next();

                List<Convocatoria> listaHijos = servicioModalidad.obtenerConvocatoriasxPadre(convocatoriaPadre);

                esISBN = verificarTipoConvocatoria("SIS", listaHijos);
                esConvLibrosEdiorial = verificarTipoConvocatoria("CLN", listaHijos);

                if (!esISBN && !esConvLibrosEdiorial) {

                    // Se asginan las convocatorias hijas.
                    convocatoriaPadre.setListaConvocatorias(obtenerListadeConvoctoriaActivasXPadre(convocatoriaPadre));
                    if (!esListaVacia(convocatoriaPadre.getListaConvocatorias())) {

                        if (convocatoriaPadre.getCategoria() != null) {

                            if (convocatoriaPadre.getCategoria().equals(PREGRADO)) {
                                listaConvocatoriasPregrado.add(convocatoriaPadre);
                                convocatoriasSistemaInvestigacion = true;
                            } else if (convocatoriaPadre.getCategoria().equals(POSGRADO)) {
                                listaConvocatoriasPosgrado.add(convocatoriaPadre);
                                convocatoriasSistemaInvestigacion = true;
                            } else if (convocatoriaPadre.getCategoria().equals(PROYECTOS)) {
                                listaConvocatoriasProyectos.add(convocatoriaPadre);
                                convocatoriasSistemaInvestigacion = true;
                            } else if (convocatoriaPadre.getCategoria().equals(INNOVACION)) {
                                listaConvocatoriasInnovacion.add(convocatoriaPadre);
                                convocatoriasSistemaInvestigacion = true;
                            } else if (convocatoriaPadre.getCategoria().equals(INTERNACIONALIZACION)) {
                                listaConvocatoriasInternacionalizacion.add(convocatoriaPadre);
                                convocatoriasSistemaInvestigacion = true;
                            } else if (convocatoriaPadre.getCategoria().equals(DIFUSION)) {
                                listaConvocatoriasDifusion.add(convocatoriaPadre);
                                convocatoriasSistemaInvestigacion = true;
                            } else if (convocatoriaPadre.getCategoria().equals(PUBLICACIONES)) {
                                listaConvocatoriasPublicaciones.add(convocatoriaPadre);
                                convocatoriasSistemaInvestigacion = true;
                            } else if (convocatoriaPadre.getCategoria().equals(SEDES_FACULTADES)) {
                                listaConvocatoriasSedesFacultades.add(convocatoriaPadre);
                            } else if (convocatoriaPadre.getCategoria().equals(PROGRAMA_APOYO)) {
                                listaConvocatoriasProgramasApoyo.add(convocatoriaPadre);
                            } else if (convocatoriaPadre.getTipoConvocatoria().equals(ConvocatoriaPadre.TIPO_CONVOCATORIA_LABORATORIOS)) {
                                listaConvocatoriasLaboratorios.add(convocatoriaPadre);
                            }else {
                                listaConvocatoriasOtras.add(convocatoriaPadre);
                            }

                        } else {
                            listaConvocatoriasOtras.add(convocatoriaPadre);
                        }
                    }
                }
            }
        }
    }

    public String asignarConvocatoriaProyecto() {

        sesion.removeAttribute("manejadorAceptarTerminosReferencia");
        sesion.removeAttribute("manejadorFichaMinimaHomeJovenes");
        sesion.removeAttribute("proyectoFichaMinina");
        sesion.removeAttribute("manejadorFichaMinimaHome");
        sesion.removeAttribute("esProyectoFichaMinimaNueva");
        sesion.removeAttribute("ManejadorCrearEditarMovilidadEventoConvFacGen");
        sesion.removeAttribute("ManejadorCrearMovilidadPosgradoInvPonenciasConvFac");
        sesion.removeAttribute("ManejadorTrabajoPrevioProyectoES_Inno");
        sesion.removeAttribute("ManejadorCrearMovilidadPosgradoInvModTres");
        sesion.removeAttribute("manejadorValidacionModalidades");
        sesion.removeAttribute(CorteConvocatoria.ID_CORTE_CONVOCATORIA_SESSION);
        sesion.removeAttribute("proyectoAsociar");
        sesion.setAttribute("proyecto", null);

        informacionAdicionalConvocatoria = "";

        String idModalidad = obtenerValorMapContext("idModalidad");
        Long id = Long.valueOf(idModalidad);

        // SE OBTIENE LA CONVOCATORIA PARA LA CUAL VA A SER CREADO UN PROYECTO
        Convocatoria convocatoriaActual = (Convocatoria) servicioGeneral.obtenerObjeto(new Convocatoria(), id);// id)(((Convocatoria)
        Boolean esConvocatoriaMovilidades = convocatoriaActual.getTipo().getId().equals(Convocatoria.TIPO_MODALIDAD_MOVILIDAD);
        String direccionComunicado = validarConvocatoriaComunicadoExterno(convocatoriaActual.getPadre().getId());
        if (!"".equals(direccionComunicado)) {
            String url = direccionComunicado;
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        } else {
            InvestigadorInterno invI = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());
            if (invI != null && validadCorteConvocatoriaPermanente(convocatoriaActual,
                    invI.getDependencia().getSede().getId().toString())) {
                
                if (convocatoriaActual.isHabilitarRegistro() != null && !convocatoriaActual.isHabilitarRegistro()) {
                    informacionAdicionalConvocatoria = convocatoriaActual.getInformacionAdicional();
                    return "";
                }
                
                if (convocatoriaActual.getRestriccion() != null
                        && convocatoriaActual.getRestriccion().getId().equals("ART_MOD_2")) {

                    Boolean esEditorRevista = (Boolean) sesion.getAttribute("esEditorRevista");

                    if (esEditorRevista == null || !esEditorRevista) {
                        Error error = new Error();
                        error.setMensaje("El usuario no tiene el rol Editor Revista.");
                        sesion.setAttribute("error", error);
                        return Navegacion.ERROR;
                    } else {
                        Proyecto p = new Proyecto();
                        p.setModalidad(convocatoriaActual);
                        sesion.setAttribute("proyecto", p);
                        borrarManejadoresInsercionProyecto();
                        sesion.setAttribute("idConvocatoriaActual", convocatoriaActual.getId());
                        return "crearProyectoConFichaMinima";
                    }
                }
                
                if (convocatoriaActual.getPadre().getId().equals(391L)|| (convocatoriaActual.getRestriccion() != null
                        && convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_CUND))) {
                    //validar que el docente no esté vinculado a otro proyecto como Investigador principal (Director) en una convocatoria
                    Modalidad mod = servicioModalidad.obtenerModalidad(convocatoriaActual.getId());
                    List listas = new ArrayList();
                    
                    List listaProyectosConvocatoriaIngresando = servicioProyecto.obtenerProyectosXModalidadEInvestigadorPrincipal(mod, invI, "I");
                    if(listaProyectosConvocatoriaIngresando != null){
                        listas.addAll(listaProyectosConvocatoriaIngresando);
                    }
                    List listaProyectosConvocatoriaPropuesto = servicioProyecto.obtenerProyectosXModalidadEInvestigadorPrincipal(mod, invI, "P");
                    if(listaProyectosConvocatoriaPropuesto != null){
                        listas.addAll(listaProyectosConvocatoriaPropuesto);
                    }
                    
                    if(!listas.isEmpty()){
                         String mensajeError = "El docente ya tiene un proyecto inscrito en la convocatoria.";
                         mensajeError(mensajeError);
                         return "";
                    }
                    if(!esNulo(listaProyectosConvocatoriaIngresando))
                    	listaProyectosConvocatoriaIngresando.clear();
                    
                    if(!esNulo(listaProyectosConvocatoriaPropuesto))
                    	listaProyectosConvocatoriaPropuesto.clear();
                    
                    if(!esNulo(listas))
                    	listas.clear();
                }

                if (convocatoriaActual.getRestriccion() != null
                        && (convocatoriaActual.getRestriccion().getId().equals("CONV_INNO")
                                || convocatoriaActual.getRestriccion().getId().equals("CONV_INNO_MOD_2")
                                || convocatoriaActual.getRestriccion().getId().equals("CONV_INNO_MOD_2_3"))) {

                    if (invI.getDependencia().getId() != null
                            && invI.getDependencia().getFacultad().getId().equals("5128")) {
                        Proyecto p = new Proyecto();
                        p.setModalidad(convocatoriaActual);
                        sesion.setAttribute("proyecto", p);
                        borrarManejadoresInsercionProyecto();
                        sesion.setAttribute("idConvocatoriaActual", convocatoriaActual.getId());
                        return "crearProyectoConFichaMinima";

                    } else {

                        mensajeError("El usuario no pertenece a la Facultad de Ciencias Agropecuarias - Palmira");
                        return "";
                    }
                }
                
                
                
                if (!convocatoriaActual.getPadre().getId().equals(318L))  {
                    if (!validarVinculacionPersona()) {
                        Error error = new Error();
                        error.setMensaje("El tipo de vinculación del investigador no es válido.");
                        sesion.setAttribute("error", error);
                        return Navegacion.ERROR;
                    }
                }

             // Convocatoria de Laboratorios
                if (convocatoriaActual.getRestriccion() != null && (convocatoriaActual.getRestriccion().getId()
                        .equals(RestriccionConvocatoria.CONV_LABORATORIOS)
                        || convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_LABORATORIOS_MOD_2))) {
                    Boolean esLaboratoriosSede = (Boolean) sesion.getAttribute("esLaboratoriosSede");
                    Boolean esLaboratoriosNacional = (Boolean) sesion.getAttribute("esLaboratorios");

                    if (esLaboratoriosSede || esLaboratoriosNacional) {
                        Proyecto p = new Proyecto();
                        p.setModalidad(convocatoriaActual);
                        sesion.setAttribute("proyecto", p);
                        borrarManejadoresInsercionProyecto();
                        sesion.setAttribute("idConvocatoriaActual", convocatoriaActual.getId());
                        return "crearProyectoConFichaMinima";
                    } else {
                        String mensajeError = "El usuario debe tener el rol Dirección Laboratorios Sede.";
                        mensajeError(mensajeError);
                        return "";
                    }
                }
                
                // Convocatoria de Laboratorios
                if (convocatoriaActual.getId().equals(931L)) {
                	Date fechaActual = new Date();
					Date fechaRol = new Date();
					Boolean esCoordinadorLaboratorio= false;
					Iterator it = invI.getRoles().iterator();
					while (it.hasNext()) {
						Rol r = (Rol) it.next();
						if (r.getId().equals(Rol.COORDINADOR_LABORATORIO)
						|| r.getId()
								.equals(Rol.COORDINADOR_TECNICO_LABORATORIO)
						|| r.getId().equals(Rol.TECNICO_LABORATORISTA)) {
							try {
								fechaRol = servicioPersona.obtenerFechaFinRol(invI.getId(), r.getId());
								if (fechaRol == null || fechaRol.equals("")) {
									fechaRol = new Date();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							if (fechaRol.after(fechaActual)
									|| fechaRol.equals(fechaActual)) {
								esCoordinadorLaboratorio = true;
							}
						}
					}
					
                    if (esCoordinadorLaboratorio) {
                        Proyecto p = new Proyecto();
                        p.setModalidad(convocatoriaActual);
                        sesion.setAttribute("proyecto", p);
                        borrarManejadoresInsercionProyecto();
                        sesion.setAttribute("idConvocatoriaActual", convocatoriaActual.getId());
                        return "crearProyectoConFichaMinima";
                    } else {
                        String mensajeError = "Para aplicar a la convocatoria, el usuario debe ser un coordinador de laboratorio.";
                        mensajeError(mensajeError);
                        return "";
                    }
                }
                
                //Parametrización convocatoria padre para proyectos
                //parametrizacionConvocatoriaPadre(convocatoriaActual, invI);
                
                if (!esConvocatoriaMovilidades && convocatoriaActual.getPadre().getNumeroProyectosPorProfesor() != null) {
        			Long limit = convocatoriaActual.getPadre().getNumeroProyectosPorProfesor();
        			List<Convocatoria> mods= servicioModalidad.obtenerConvocatoriasxPadre(convocatoriaActual.getPadre());
        			Long current = 0L;
        			for (Convocatoria convocatoria : mods) {
        				List listaProyectosConvocatoria = servicioProyecto.obtenerProyectosXModalidadEInvestigador(convocatoria, invI, "'I','P'");
        				current+=listaProyectosConvocatoria.size();
        			}
        			if (current < limit) {
        				Proyecto p = new Proyecto();
        				p.setModalidad(convocatoriaActual);
        				sesion.setAttribute("proyecto", p);
        				borrarManejadoresInsercionProyecto();
        				sesion.setAttribute("convocatoria", convocatoriaActual);
        				sesion.setAttribute("idConvocatoriaActual", convocatoriaActual.getId());
        				return "crearProyectoConFichaMinima";
        			} else {
        				String mensajeError = "";
        				if (current == 1) {
        					mensajeError = "El docente ya tiene " + current + " proyecto inscrito en la convocatoria.";
        				} else {
        					mensajeError = "El docente ya tiene " + current + " proyectos inscritos en la convocatoria.";
        				}
        				mensajeError(mensajeError);
        				return "";
        			}
        		}

				//Parametrización modalidad para proyectos
                if (!esConvocatoriaMovilidades && convocatoriaActual.getNumeroProyectosPorProfesor() != null ) {
 
                	//validar que el docente no tenga otro proyecto en una convocatoria
                    Modalidad mod = servicioModalidad.obtenerModalidad(convocatoriaActual.getId());
                    
                    if(convocatoriaActual.getPadre().getEsPermanente().equals("N"))
                    {	
                    	Long numProyectos = convocatoriaActual.getNumeroProyectosPorProfesor();
                    	List listaProyectosConvocatoria = servicioProyecto.obtenerProyectosXModalidadEInvestigadorPrincipal(mod, invI, "'I','P'");
	                    if(listaProyectosConvocatoria.isEmpty() || listaProyectosConvocatoria.size() < numProyectos){
	                    	 Proyecto p = new Proyecto();
	                         p.setModalidad(convocatoriaActual);
	                         sesion.setAttribute("proyecto", p);
	                         borrarManejadoresInsercionProyecto();
	                         sesion.setAttribute("convocatoria", convocatoriaActual);
	                         sesion.setAttribute("idConvocatoriaActual", convocatoriaActual.getId());
	                         return "crearProyectoConFichaMinima";
	                    }else{
	                    	String mensajeError = "";
	                    	if(listaProyectosConvocatoria.size() == 1){
	                    		mensajeError = "El docente ya tiene " + listaProyectosConvocatoria.size() + " proyecto inscrito en la modalidad.";
	                    	}else{
	                    		mensajeError = "El docente ya tiene " + listaProyectosConvocatoria.size() + " proyectos inscritos en la modalidad.";
	                    	}                    	 
	                         mensajeError(mensajeError);
	                         return "";
	                    }
                    }
                    else if(convocatoriaActual.getPadre().getEsPermanente().equals("Y"))
                    {
                    	CorteConvocatoria corte = consultarCorteConvocatoriaPermanenteVigente(convocatoriaActual,invI.getDependencia().getSede().getId().toString());
                    	Long numProyectos = esNulo(corte.getNumProyPorInvPorConvPorCorte()) ? 0L : corte.getNumProyPorInvPorConvPorCorte();
                    	List listaProyectosConvocatoriaCorte = servicioProyecto.obtenerProyectosXModalidadEInvestigadorCortes(mod, invI, "'I','P'", corte.getId());
	                    if(listaProyectosConvocatoriaCorte.isEmpty() || listaProyectosConvocatoriaCorte.size() < numProyectos){
	                    	 Proyecto p = new Proyecto();
	                         p.setModalidad(convocatoriaActual);
	                         sesion.setAttribute("proyecto", p);
	                         borrarManejadoresInsercionProyecto();
	                         sesion.setAttribute("convocatoria", convocatoriaActual);
	                         sesion.setAttribute("idConvocatoriaActual", convocatoriaActual.getId());
	                         return "crearProyectoConFichaMinima";
	                    }else{
	                    	String mensajeError = "";
	                    	if(listaProyectosConvocatoriaCorte.size() == 1){
	                    		mensajeError = "El docente ya tiene " + listaProyectosConvocatoriaCorte.size() + " proyecto inscrito en la convocatoria en el corte # "+corte.getNumero();
	                    	}else{
	                    		mensajeError = "El docente ya tiene " + listaProyectosConvocatoriaCorte.size() + " proyectos inscritos en la convocatoria en el corte # "+corte.getNumero();
	                    	}                    	 
	                         mensajeError(mensajeError);
	                         return "";
	                    }
                    }
                    
                }
                
                // Parametrizacion movilidades
                if(esConvocatoriaMovilidades) {
                	ConvocatoriaPadre convPadre = convocatoriaActual.getPadre();
                	List<Convocatoria> mods= servicioModalidad.obtenerConvocatoriasxPadre(convPadre);
                	
                	Long movilidadesConvocatoria = convocatoriaActual.getPadre().getNumeroProyectosPorProfesor();
                	
                	//Validaciones convocatoria padre
                	if(!esNulo(movilidadesConvocatoria)) {
                		Long movilidadesDocente = 0L;
            			for (Convocatoria convocatoria : mods)
            				movilidadesDocente+=obtenerNumMovilidadesXModadlidadXDocente(invI, convocatoria, null);
            			
            			if (movilidadesDocente < movilidadesConvocatoria) {
            				sesion.setAttribute("idConvocatoriaActual", convocatoriaActual.getId());
            				return "crearMovilidad";
            			} else {
            				String mensajeError = "";
            				if (movilidadesDocente == 1) {
            					mensajeError = "El docente ya tiene " + movilidadesDocente + " movilidad aprobada y/o ejecutada, y la CONVOCATORIA seleccionada sólo se permite tener " + movilidadesConvocatoria;
            				} else {
            					mensajeError = "El docente ya tiene " + movilidadesDocente + " movilidad aprobadas y/o ejecutadas, y la CONVOCATORIA seleccionada sólo se permite tener " + movilidadesConvocatoria;
            				}
            				mensajeError(mensajeError);
            				return "";
            			}
                	}
                	
                	//Validaciones modalidades
                	Long movilidadesModalidad = convocatoriaActual.getNumeroProyectosPorProfesor();
                	
                	if(!esNulo(movilidadesModalidad)) {
                		Long movilidadesDocente = 0L;
                		movilidadesDocente+=obtenerNumMovilidadesXModadlidadXDocente(invI, convocatoriaActual, null);
                		
                		if (movilidadesDocente < movilidadesModalidad) {
            				sesion.setAttribute("idConvocatoriaActual", convocatoriaActual.getId());
            				return "crearMovilidad";
            			} else {
            				String mensajeError = "";
            				if (movilidadesDocente == 1) {
            					mensajeError = "El docente ya tiene " + movilidadesDocente + " movilidad aprobada y/o ejecutada, y la MODALIDAD seleccionada sólo se permite tener " + movilidadesModalidad;
            				} else {
            					mensajeError = "El docente ya tiene " + movilidadesDocente + " movilidad aprobadas y/o ejecutadas, y la MODALIDAD seleccionada sólo se permite tener " + movilidadesModalidad;
            				}
            				mensajeError(mensajeError);
            				return "";
            			}
                	}
                }

                Proyecto p = new Proyecto();
                p.setModalidad(convocatoriaActual);

                Long corteId = (Long) sesion.getAttribute(CorteConvocatoria.ID_CORTE_CONVOCATORIA_SESSION);
                if(corteId != null){
                    CorteConvocatoria corteConvocatoria = new CorteConvocatoria(corteId);
                    p.setCorteConvocatoria(corteConvocatoria);
                }                
                
                sesion.setAttribute(VARIABLE_PROYECTO_SESION, p);
                borrarManejadoresInsercionProyecto();
				if ((convocatoriaActual.getEsParaGrupos() != null && convocatoriaActual.getEsParaGrupos().booleanValue())
						|| (convocatoriaActual.getEsParaSemilleros() != null && convocatoriaActual.getEsParaSemilleros().booleanValue())) {
                    sesion.removeAttribute("manejadorValidacionModalidades");
                    sesion.setAttribute("convocatoria", convocatoriaActual);
                    sesion.setAttribute("idConvocatoriaActual", convocatoriaActual.getId());
                    return "crearProyectoConFichaMinima";
                }
                if (convocatoriaActual.isFormularioFichaMinima()) {
                    sesion.setAttribute("idConvocatoriaActual", convocatoriaActual.getId());
                    return "crearProyectoConFichaMinima";
                } else {
                    return "crearProyecto";
                }
            } else {

                mensajeError("En este momento no se encuentra activa esta convocatoria para su sede.");
                return "";
            }
        }
    }
    
    public Integer obtenerNumMovilidadesXModadlidadXDocente(Persona persona, Convocatoria modalidad, Sede sede) {
    	Integer cantidad = 0;
    	
    	List<MovilidadVisitanteExterior> listaMovilidadesVisitante = cargarMovilidades(MovilidadVisitanteExterior.class, persona, modalidad, sede, "");
    	List<MovilidadDocentesExterior> listaMovilidadesEvento = cargarMovilidades(MovilidadDocentesExterior.class, persona, modalidad, sede, "");
    	List<MovilidadEstudiantesPosgrado> listaMovilidadesEstudiantePosgrado = cargarMovilidades(MovilidadEstudiantesPosgrado.class, persona, modalidad, sede, "");
    	
    	cantidad = listaMovilidadesVisitante.size() + listaMovilidadesEvento.size() + listaMovilidadesEstudiantePosgrado.size();
    	System.out.println("Cantidad: " + cantidad);
    	return cantidad;
    }
    
    public <T> List<T> cargarMovilidades(Class<T> clase, Persona persona, Convocatoria modalidad, Sede sede, String hqlWhereAdicional) {
    	List<T> lista;
    	String hqlWhereSede = !esNulo(sede) ? "AND mov.dependencia.sede.id = "  + sede.getId() : "";
		String hql = "SELECT #id mov.id, #estado mov.estado, #fechainicial mov.fechainicial, #convocatoria mov.convocatoria, #fechasolicitud mov.fechasolicitud ";
		hql += " FROM " + clase.getSimpleName() + " mov "
				+ "WHERE " 
				+ "mov.personaInv.id.documento = '" + persona.getId().getDocumento() + "' "
				+ "AND mov.personaInv.id.tipoDocumento = '" + persona.getId().getTipoDocumento() + "' "
				+ "AND mov.convocatoria.id = " + modalidad.getId() + " "
				+ "AND (mov.estado = 'P' AND mov.aprobacion = 'SI') "
				+ "AND (mov.estadoSeguimiento IS NULL OR (mov.estadoSeguimiento IS NOT NULL AND mov.realizacionMovilidad = 'SI' AND (mov.estadoSeguimiento = 'F' OR mov.estadoRevisionSeguimiento = 'L'))) "
				;
		hql += hqlWhereSede;
		hql += hqlWhereAdicional;
		System.out.println("hql: " + hql);
		lista = servicioGeneral.obtenerObjetosLimitado(clase, hql);
		return lista;
	}

    public List<ConvocatoriaPadre> getListaConvocatoriasDifusion() {
        return listaConvocatoriasDifusion;
    }

    public List<ConvocatoriaPadre> getListaConvocatoriasInnovacion() {
        return listaConvocatoriasInnovacion;
    }

    public List<ConvocatoriaPadre> getListaConvocatoriasInternacionalizacion() {
        return listaConvocatoriasInternacionalizacion;
    }

    public List<ConvocatoriaPadre> getListaConvocatoriasOtras() {
        return listaConvocatoriasOtras;
    }

    public List<ConvocatoriaPadre> getListaConvocatoriasPosgrado() {
        return listaConvocatoriasPosgrado;
    }

    public List<ConvocatoriaPadre> getListaConvocatoriasPregrado() {
        return listaConvocatoriasPregrado;
    }

    public List<ConvocatoriaPadre> getListaConvocatoriasProgramasApoyo() {
        return listaConvocatoriasProgramasApoyo;
    }

    public List<ConvocatoriaPadre> getListaConvocatoriasProyectos() {
        return listaConvocatoriasProyectos;
    }

    public List<ConvocatoriaPadre> getListaConvocatoriasPublicaciones() {
        return listaConvocatoriasPublicaciones;
    }

    public List<ConvocatoriaPadre> getListaConvocatoriasSedesFacultades() {
        return listaConvocatoriasSedesFacultades;
    }

    public boolean isConvocatoriasSistemaInvestigacion() {
        return convocatoriasSistemaInvestigacion;
    }

    /**
     * @return the informacionAdicionalConvocatoria
     */
    public String getInformacionAdicionalConvocatoria() {
        return informacionAdicionalConvocatoria;
    }

	public List<ConvocatoriaPadre> getListaConvocatoriasLaboratorios() {
		return listaConvocatoriasLaboratorios;
	}

	public void setListaConvocatoriasLaboratorios(List<ConvocatoriaPadre> listaConvocatoriasLaboratorios) {
		this.listaConvocatoriasLaboratorios = listaConvocatoriasLaboratorios;
	}

}