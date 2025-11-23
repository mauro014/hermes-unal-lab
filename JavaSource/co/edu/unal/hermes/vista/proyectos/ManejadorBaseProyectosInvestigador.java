package co.edu.unal.hermes.vista.proyectos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.faces.context.FacesContext;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.modelo.Contrapartida;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaArticulo;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Institucion;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.JornadaDocente;
import co.edu.unal.hermes.modelo.Modalidad;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.Registro;
import co.edu.unal.hermes.modelo.TipoModalidad;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.ManejadorBase;
import co.edu.unal.hermes.vista.evaluadores.ProyectoCoordinador;
import co.edu.unal.hermes.vista.utils.Util;

public abstract class ManejadorBaseProyectosInvestigador extends ManejadorBase {

    private static final long serialVersionUID = -4778253422888038591L;

    private ProyectoVista proyectoSeleccionado;

    private List<ProyectoVista> filteredProyectos;
    private List<ProyectoVista> filteredRegistroBiodiversidad;
    protected Investigador investigadorActual;

    private List<ProyectoVista> listaVisibles;
    private List<ProyectoVista> listaArticulos;
    private List<ProyectoVista> listaBiodiversidad;

    // CONSTRUCTOR
    public ManejadorBaseProyectosInvestigador() {
        try {
            boolean esInvestigador = (Boolean) sesion.getAttribute("esInvestigador");

            if (!esInvestigador) {
                esInvestigador = (Boolean) sesion.getAttribute("esCorredorTecnologico");
            }

            borrarManejadoresInsercionProyecto();
            listaArticulos = new ArrayList<ProyectoVista>();

            personaActual = (Persona) sesion.getAttribute("persona");
            if (esInvestigador) {
                investigadorActual = servicioPersona
                        .obtenerProyectosInvestigador(((Persona) sesion.getAttribute("persona")).getId());
            } else {
                investigadorActual = servicioPersona
                        .obtenerInvestigadorSinProyectos(((Persona) sesion.getAttribute("persona")).getId());
            }

            sesion.setAttribute("investigadorProyectos", investigadorActual);

            // Se cargan los proyectos
            if (investigadorActual != null) {
                cargarListasProyectos();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected abstract void cargarListasProyectos();

    public String cargarCartasProyecto() {
        sesion.removeAttribute("manejadorConsultaCartasProyecto");
        sesion.setAttribute("proyectoCarta", proyectoSeleccionado);
        return "consultaCartaProyecto";
    }

    public void cargarConvocatoriasArtículos() {
        String hql = "select ca from ConvocatoriaArticulo ca where ca.personaInv.id.documento = '"
                + personaActual.getId().getDocumento() + "'";
        List<ConvocatoriaArticulo> listaArt = new ArrayList<ConvocatoriaArticulo>();
        listaArt = servicioGeneral.obtenerObjetos(ConvocatoriaArticulo.class, hql);
        for (ConvocatoriaArticulo convArt : listaArt) {
            ProyectoVista pv = new ProyectoVista();
            pv.setId(convArt.getId());
            pv.setNombre(convArt.getTitulo());
            pv.setConvocatoriaPadre(convArt.getConvocatoria());
            pv.setEstadoProyecto(convArt.getEstadoNombre());
            pv.setNombreModalidad(convArt.getModalidad());
            pv.setEsArticulo(true);
            listaArticulos.add(pv);
        }
    }

    protected void cargarProyectosVista(List<InvestigadorProyecto> listaProyectosActivos) {

        List<ProyectoVista> listaIngresando = new ArrayList<ProyectoVista>();
        List<ProyectoVista> listaPropuesto = new ArrayList<ProyectoVista>();
        List<ProyectoVista> listaAprobado = new ArrayList<ProyectoVista>();
        List<ProyectoVista> listaActivo = new ArrayList<ProyectoVista>();
        List<ProyectoVista> listaElegible = new ArrayList<ProyectoVista>();
        List<ProyectoVista> listaBancoFinanciable = new ArrayList<ProyectoVista>();
        List<ProyectoVista> listaEnLegalizacion = new ArrayList<ProyectoVista>();
        List<ProyectoVista> listaRechazado = new ArrayList<ProyectoVista>();
        List<ProyectoVista> listaNegado = new ArrayList<ProyectoVista>();
        List<ProyectoVista> listaBancoProyectos = new ArrayList<ProyectoVista>();
        List<ProyectoVista> listaSuspendido = new ArrayList<ProyectoVista>();
        List<ProyectoVista> listaCancelado = new ArrayList<ProyectoVista>();
        List<ProyectoVista> listaFinalizado = new ArrayList<ProyectoVista>();
        List<ProyectoVista> listaAmbientales = new ArrayList<ProyectoVista>();
        List<ProyectoVista> listaAvalAprobado = new ArrayList<ProyectoVista>();
        List<ProyectoVista> listaAprobadoOCAD = new ArrayList<ProyectoVista>();

        // Se recorren todos los proyectos activos y se crea un proyectoVista
        // por cada proyecto
        Iterator<InvestigadorProyecto> it_proyectos = listaProyectosActivos.iterator();
        while (it_proyectos.hasNext()) {
            ProyectoVista proyectoVista = new ProyectoVista();
            InvestigadorProyecto invPry = (InvestigadorProyecto) it_proyectos.next();
            Proyecto pry = invPry.getProyecto();
            
            String hql = "select count(ip.id) " +
            			"from InvestigadorProyecto ip " +
            			"where (ip.tipo.documento like 'ESPO%' or ip.tipo.documento like 'ESPR%') " +
            			"and (ip.investigador.id.documento like 'ESPO%' or ip.investigador.id.documento like 'ESPR%') " +
            			"and ip.proyecto.id = " + invPry.getProyecto().getId().toString();
            
            Integer count = (Integer) servicioGeneral.obtenerObjetos(hql).get(0);
            //System.out.println("Proyecto: "+ invPry.getProyecto().getId().toString() +" count = " + count);
            
            if(count > 0){
            	proyectoVista.setTieneEstudiantesSinDetalle(true);
            }

            // consulta avales relacionados con proyecto
            List avalesAsociadosProyecto = servicioGeneral.obtenerAvalesProyecto(pry.getId().toString());
            proyectoVista.setAvalesProyecto(avalesAsociadosProyecto);
            // fin consulta avales relacionados con proyecto

            proyectoVista.asignarValores(pry);
            if(invPry.getTipo()!=null) {
            	proyectoVista.setTipoInvestigadorActual(invPry.getTipo().getId());
            }else {
            	proyectoVista.setTipoInvestigadorActual(null);
            }
            proyectoVista.setPermitirModificacion(pry.getPermitirModificacion());
            
            
            try {
 
            	Convocatoria c = (Convocatoria) servicioModalidad.obtenerConvocatoria(pry.getModalidad().getId());

            
            if(c!=null && c.isEditaCreadorProyecto()) {
            	
            	List<Proyecto> pryActual = servicioGeneral
                .obtenerObjetosLimitado(
                        Proyecto.class,
                        "select #creadorId p.creadorId  "
                                + "from Proyecto p where p.id = "+pry.getId());

            	if(pryActual.size()>0 && pryActual.get(0).getCreadorId()!=null && pryActual.get(0).getCreadorId().equals(personaActual.getId().getDocumento())) {
            		proyectoVista.setEditaSoloCreador(true);
            		proyectoVista.setEditaPrincipal(false);
            	}
            }
            }catch (Exception e){
            	e.printStackTrace();
            }

            	if (pry.getTieneCompromisosPendientes() != null) {
                proyectoVista.setCompromisosPendientes(pry.getTieneCompromisosPendientes());
            }
            proyectoVista.setEstadoProyecto(pry.getEstadoProyecto().getNombre());
            // TODO se obtiene el nombre de la modalidad por cada modalidad
            Modalidad mod = pry.getModalidad();
            if (mod instanceof Convocatoria) {
                if (mod.getId().equals(2L)) {
                    String tipoActividad = proyectoVista.getTipoActividad();
                    if (tipoActividad != null) {
                        List<DominioDetalle> lista = servicioGeneral.obtenerObjetos(DominioDetalle.class,
                                "select d from DominioDetalle d where d.identificador.tipo='" + tipoActividad + "'");
                        if (lista.size() > 0) {
                            DominioDetalle dd = (DominioDetalle) lista.get(0);
                            proyectoVista.setNombreModalidad(dd.getDescripcion());
                        }
                    }

                } else {
                    proyectoVista.setNombreModalidad(((Convocatoria) mod).getTitulo());
                }

                Convocatoria con = (Convocatoria) mod;
                if (con != null && con.getPadre() != null) {
                    proyectoVista.setConvocatoriaPadre(con.getPadre().getTitulo());
                }
            } else if (mod instanceof JornadaDocente) {
                proyectoVista.setNombreModalidad(((JornadaDocente) mod).getDescripcion());
            } else if (mod instanceof Contrapartida) {
                proyectoVista.setNombreModalidad(((Contrapartida) mod).getNombre());
            } else if (mod instanceof Registro) {
                proyectoVista.setNombreModalidad(((Registro) mod).getNombre());
            } else {
                proyectoVista.setNombreModalidad("sin nombre");
            }

            if (proyectoVista.isCompleto()) {
                proyectoVista.setMensajeEstado("PROYECTO COMPLETO");
            } else {
                String mensajeFinal = "PENDIENTE: ";
                StringTokenizer st = new StringTokenizer(proyectoVista.getMensajeEstado(), ",");
                while (st.hasMoreTokens()) {
                    String next = st.nextToken();
                    if (st.hasMoreTokens()) {
                        mensajeFinal += next + ", ";
                    } else {
                        mensajeFinal += next + ".";
                    }
                }
                proyectoVista.setMensajeEstado(mensajeFinal);
            }

            if (invPry.getTipo()!=null && invPry.getTipo().getId().equals(InvestigadorProyecto.PRINCIPAL)) {
                proyectoVista.setEsPrincipal(true);
            } else {
                proyectoVista.setEsPrincipal(false);
            }

            List<InvestigadorProyecto> listaInvestigadoresProyecto = pry.getListaInvestigadoresProyecto();
            boolean esEstudianteLider = false;
            boolean esAsistenteLider = false;
            String nDocumento = "";
            String cTipoDocumento = "";            
            String nDocumentoAL = "";
            String cTipoDocumentoAL = "";
            for (InvestigadorProyecto invP : listaInvestigadoresProyecto) {
				if (invP.getTipo().getId().equals("AL") 
						|| invP.getTipo().getId().equals("AA")
						|| invP.getTipo().getId().equals("ALL") 
						|| invP.getTipo().getId().equals("PLDC") 
						|| invP.getTipo().getId().equals("IPUR") 
						|| invP.getTipo().getId().equals("DDUNM")) {
                	esEstudianteLider = true;
                    
					String consultaEstudianteLider = "select e from InvestigadorProyecto e where e.investigador.id.documento = '"
							+ personaActual.getId().getDocumento() + "' and e.investigador.id.tipoDocumento = '"
							+ personaActual.getId().getTipoDocumento() + "' and e.proyecto.id = "
							+ invP.getProyecto().getId() + " and e.tipo.id = '" + invP.getTipo().getId() + "'";
                	List<InvestigadorProyecto> listaConsultaEstudianteLider = servicioGeneral.obtenerObjetos(consultaEstudianteLider);
                	if(!listaConsultaEstudianteLider.isEmpty()){
                		InvestigadorProyecto estLidAc = listaConsultaEstudianteLider.get(0);
                		 nDocumento = estLidAc.getInvestigador().getId().getDocumento();
                         cTipoDocumento = estLidAc.getInvestigador().getId().getTipoDocumento();
                	}
                } else if (invP.getTipo().getId().equals("ADL")) {
            		esAsistenteLider = true;
					String consultaAsistenteLider = "select e from InvestigadorProyecto e where e.investigador.id.documento = '"
							+ personaActual.getId().getDocumento() + "' and e.investigador.id.tipoDocumento = '"
							+ personaActual.getId().getTipoDocumento() + "' and e.proyecto.id = "
							+ invP.getProyecto().getId() + " and e.tipo.id = '" + invP.getTipo().getId() + "'";
                 	List<InvestigadorProyecto> listaConsultaAsistenteLider = servicioGeneral.obtenerObjetos(consultaAsistenteLider);
                 	if(!listaConsultaAsistenteLider.isEmpty()){
                 		InvestigadorProyecto estLidAc = listaConsultaAsistenteLider.get(0);
                 		 nDocumentoAL = estLidAc.getInvestigador().getId().getDocumento();
                          cTipoDocumentoAL = estLidAc.getInvestigador().getId().getTipoDocumento();
                 	}
                }

            }
            
            if (esEstudianteLider) {
                if (personaActual.getId().getDocumento().equals(nDocumento)
                        && personaActual.getId().getTipoDocumento().equals(cTipoDocumento)) {
                    proyectoVista.setEsEstudianteLider(true);
                } else {
                    proyectoVista.setEsEstudianteLider(false);
                }
            }
            
            if (esAsistenteLider) {
                if (personaActual.getId().getDocumento().equals(nDocumentoAL)
                        && personaActual.getId().getTipoDocumento().equals(cTipoDocumentoAL)) {
                    proyectoVista.setEsAsistenteLider(true);
                } else {
                    proyectoVista.setEsAsistenteLider(false);
                }
            }

            if (pry.getModalidad().getTipo().getId().equals(TIPO_MODALIDAD_PERMISO_MARCO)
                    || pry.getModalidad().getTipo().getId().equals(TIPO_MODALIDAD_PERMISO_MARCO_ASIGNATURA)
                    || pry.getModalidad().getTipo().getId().equals(TIPO_MODALIDAD_CONTRATO_ACCESO)) {
                listaAmbientales.add(proyectoVista);
            } else {

                if (pry.getEstadoProyecto().getId().equals(EstadoProyecto.INGRESANDO)) {
                    listaIngresando.add(proyectoVista);
                } else if (pry.getEstadoProyecto().getId().equals(EstadoProyecto.PROPUESTO)) {

                    listaPropuesto.add(proyectoVista);
                } else if (pry.getEstadoProyecto().getId().equals(EstadoProyecto.APROBADO)) {
                    List<ProyectoCoordinador> nuevo = this.servicioGeneral.obtenerObjetos(ProyectoCoordinador.class,
                            "from ProyectoCoordinador p where p.idProyecto='" + proyectoVista.getId() + "'");
                    if (nuevo != null && nuevo.size() > 0) {
                        ProyectoCoordinador n = (ProyectoCoordinador) nuevo.get(0);
                        if (n.getSiAprobacion() != null && n.getSiAprobacion().equals("G")) {
                            proyectoVista.setEsCartaInicio(true);
                        }
                    }
                    listaAprobado.add(proyectoVista);
                } else if (pry.getEstadoProyecto().getId().equals(EstadoProyecto.ACTIVO)) {
                    listaActivo.add(proyectoVista);
                } else if (pry.getEstadoProyecto().getId().equals(EstadoProyecto.ELEGIBLE)) {
                    listaElegible.add(proyectoVista);
                } else if (pry.getEstadoProyecto().getId().equals(EstadoProyecto.BANCO_FINANCIABLE)) {
                	listaBancoFinanciable.add(proyectoVista);
                } else if (pry.getEstadoProyecto().getId().equals(EstadoProyecto.EN_LEGALIZACION)) {
                	listaEnLegalizacion.add(proyectoVista);
                } else if (pry.getEstadoProyecto().getId().equals(EstadoProyecto.RECHAZADO)) {
                    listaRechazado.add(proyectoVista);
                } else if (pry.getEstadoProyecto().getId().equals(EstadoProyecto.NEGADO)) {
                    listaNegado.add(proyectoVista);
                } else if (pry.getEstadoProyecto().getId().equals(EstadoProyecto.BANCOPROYECTO)) {
                    listaBancoProyectos.add(proyectoVista);
                } else if (pry.getEstadoProyecto().getId().equals(EstadoProyecto.SUSPENDIDO)) {
                    listaSuspendido.add(proyectoVista);
                } else if (pry.getEstadoProyecto().getId().equals(EstadoProyecto.CANCELADO)) {
                    listaCancelado.add(proyectoVista);
                } else if (pry.getEstadoProyecto().getId().equals(EstadoProyecto.FINALIZADO)
                        || pry.getEstadoProyecto().getId().equals(EstadoProyecto.POR_FINALIZAR)) {

                    List<ProyectoCoordinador> nuevo = this.servicioGeneral.obtenerObjetos(ProyectoCoordinador.class,
                            "from ProyectoCoordinador p where p.idProyecto='" + proyectoVista.getId() + "'");
                    if (nuevo != null && nuevo.size() > 0) {
                        ProyectoCoordinador n = (ProyectoCoordinador) nuevo.get(0);
                        if (n.getCartaFinalizacion() != null) {
                            proyectoVista.setEsCartaFin(true);
                        }
                    }

                    listaFinalizado.add(proyectoVista);
                } else if (pry.getEstadoProyecto().getId().equals(EstadoProyecto.AVAL_APROBADO)) {
                    listaAvalAprobado.add(proyectoVista);
                } else if (pry.getEstadoProyecto().getId().equals(EstadoProyecto.APROBADO_OCAD)) {
                    listaAvalAprobado.add(proyectoVista);
                }
            }

        }

        listaVisibles = new ArrayList<ProyectoVista>();
        Collections.sort(listaActivo);
        Collections.sort(listaAprobado);
        Collections.sort(listaElegible);
        Collections.sort(listaBancoFinanciable);
        Collections.sort(listaEnLegalizacion);
        Collections.sort(listaPropuesto);
        Collections.sort(listaIngresando);
        Collections.sort(listaFinalizado);
        Collections.sort(listaBancoProyectos);
        Collections.sort(listaRechazado);
        Collections.sort(listaNegado);
        Collections.sort(listaSuspendido);
        Collections.sort(listaCancelado);
        Collections.sort(listaAvalAprobado);
        Collections.sort(listaAprobadoOCAD);
        listaVisibles.addAll(listaActivo);
        listaVisibles.addAll(listaAprobado);
        listaVisibles.addAll(listaElegible);
        listaVisibles.addAll(listaBancoFinanciable);
        listaVisibles.addAll(listaEnLegalizacion);
        listaVisibles.addAll(listaPropuesto);
        listaVisibles.addAll(listaIngresando);
        listaVisibles.addAll(listaFinalizado);
        listaVisibles.addAll(listaBancoProyectos);
        listaVisibles.addAll(listaRechazado);
        listaVisibles.addAll(listaNegado);
        listaVisibles.addAll(listaSuspendido);
        listaVisibles.addAll(listaCancelado);
        listaVisibles.addAll(listaArticulos);
        listaVisibles.addAll(listaAvalAprobado);
        listaVisibles.addAll(listaAprobadoOCAD);
        listaBiodiversidad = new ArrayList<ProyectoVista>();
        listaBiodiversidad.addAll(listaAmbientales);

    }

    public String editarGenerico() {
        borrarManejadoresInsercionProyecto();

        sesion.removeAttribute("esProyectoFichaMinimaNueva");
        sesion.removeAttribute("proyectoFichaMinimaNueva");
        sesion.removeAttribute("proyectoFichaMinina");
        sesion.removeAttribute("ManejadorTrabajoPrevioProyectoES_Inno");

        Long id = proyectoSeleccionado.getId();

        Proyecto proyectoActual = servicioProyecto.obtenerProyecto(id, ProyectoDAOHibernate.INFORMACION_GENERAL,true); //incluir gastos

        sesion.setAttribute("proyecto", proyectoActual);
        sesion.removeAttribute("consultaFichaMinina");
        sesion.removeAttribute("manejadorDatosBasicos");
        
        if(((Convocatoria) proyectoActual.getModalidad()).isUsarFormularioProyectoEditorial()){
        	sesion.setAttribute("Pro_Editorial_ID", proyectoSeleccionado.getId());
            sesion.setAttribute("Pro_Editorial_Editar", "Editar-"+proyectoSeleccionado.getId());
            sesion.removeAttribute("ManejadorEditorialProyectosRegistro");
            return "registroProyectoEditorial";	
        }

        if ((proyectoActual.getModalidad().getTipo().getId().compareTo("CFM") == 0)) {
            return "editarAdmProyectoFichaMinima";
        }

        if ((proyectoActual.getModalidad().getTipo().getId().compareTo("CPU") == 0)) {
        	sesion.setAttribute("proyectoFichaMinina", proyectoSeleccionado);
            return "editarAdmProyectoFichaMinimaPurdue";
        }

        if ((proyectoActual.getModalidad().getTipo().getId().compareTo("CMP") == 0)
                || (proyectoActual.getModalidad().getTipo().getId().compareTo("CEQ") == 0)) {
            return "editarAdmProyectoFichaMinimaOtraConv";
        }

        if ((proyectoActual.getModalidad().getTipo().getId().compareTo("CJI") == 0)) {
            sesion.setAttribute("proyectoFichaMinina", proyectoSeleccionado);
            return "irMod_JI_SEM";
        }

        if ((proyectoActual.getModalidad().getTipo().getId().compareTo("RFE") == 0)
                || (proyectoActual.getModalidad().getTipo().getId().compareTo("SEB") == 0)
                || (proyectoActual.getModalidad().getTipo().getId().compareTo("CSF") == 0)
                || (proyectoActual.getModalidad().getTipo().getId().compareTo("ESI") == 0)
                || (proyectoActual.getModalidad().getTipo().getId().compareTo("ES7") == 0)
                || (proyectoActual.getModalidad().getTipo().getId().compareTo("CTV") == 0)
                || (proyectoActual.getModalidad().getTipo().getId().compareTo("FMH") == 0)
                || (proyectoActual.getModalidad().getTipo().getId().compareTo("CTP") == 0)
                || (proyectoActual.getModalidad().getTipo().getId().compareTo("CED") == 0)
                || (proyectoActual.getModalidad().getTipo().getId().compareTo("SEB") == 0)
                || (proyectoActual.getModalidad().getTipo().getId()
                        .compareTo(TipoModalidad.REGISTRO_PROYECTOS_LABORATORIOS) == 0)
                || (proyectoActual.getModalidad().getTipo().getId().compareTo("CCT") == 0)) {
            sesion.setAttribute("proyectoFichaMinina", proyectoSeleccionado);
            return "fichaMinimaHome";
        }

        if ((proyectoActual.getModalidad().getTipo().getId().compareTo("SIS") == 0)) {
            sesion.removeAttribute("ManejadorSolicitudISBN");
            return "irSolicitudISBNEditar";
        }

        if ((proyectoActual.getModalidad().getTipo().getId().compareTo("PM") == 0)
                || (proyectoActual.getModalidad().getTipo().getId().compareTo("PMA") == 0)) {
            sesion.removeAttribute("manejadorFichaMinima");
            sesion.setAttribute("proyectoFichaMinina", proyectoSeleccionado);
            sesion.setAttribute("idConvocatoriaActual", proyectoActual.getModalidad().getId());
            return "irInformacionEspecificaMarco";
        } else {
            if ((proyectoActual.getModalidad().getTipo().getId().compareTo("CL") == 0) || (proyectoActual.getModalidad().getTipo().getId().compareTo("CLN") == 0)) {
                return "editarAdmProyectoConvLibros";
            } else {
                if ((proyectoActual.getModalidad().getTipo().getId().compareTo("CEI") == 0)) {
                    return "editarAdmProyectoEscInt";
                } else {
                    if ((proyectoActual.getModalidad().getTipo().getId().compareTo("CBP") == 0)) {
                        return "editarAdmProyectoBanPro";
                    } else {
                        return "editarAdmProyecto";
                    }

                }
            }
        }
    }

    public String consultarGenerico() {
        borrarManejadoresInsercionProyecto();
        ProyectoVista proyectoActualAdm = proyectoSeleccionado;
        Long id = proyectoActualAdm.getId();
        sesion.removeAttribute("idConvocatoriaActual");

        System.out.println("id Proyecto editar con autorizacion" + id);
        Proyecto proyectoActual = servicioProyecto.obtenerProyecto(id, ProyectoDAOHibernate.INFORMACION_GENERAL);

        sesion.setAttribute("proyecto", proyectoActual);

        if ((proyectoActual.getModalidad().getTipo().getId().compareTo("CFM") == 0)) {
            return "consultarAdmProyectoFichaMinima";
        }
        if ((proyectoActual.getModalidad().getTipo().getId().compareTo("PM") == 0)) {
            sesion.removeAttribute("manejadorFichaMinima");
            sesion.setAttribute("proyectoFichaMinina", proyectoSeleccionado);
            sesion.setAttribute("idConvocatoriaActual", proyectoActual.getModalidad().getId());
            sesion.setAttribute("consultaPermisoMarco", true);
            return "irInformacionEspecificaMarco";
        } else {
            if ((proyectoActual.getModalidad().getTipo().getId().compareTo("CJI") == 0)) {
                return "consultarFichaMinimaHome_JI_SEM";
            } else {
                return "consultarAdmProyecto";
            }

        }

    }

    public String consultarAval() {
        sesion.setAttribute("idAval", null);
        sesion.removeAttribute("manejadorSolicitudAval");
        sesion.removeAttribute("manejadorConsultarAval");
        sesion.removeAttribute("manejadorSolicitudAvalHome");
        sesion.removeAttribute("manejadorConsultarAvalHome");
        sesion.removeAttribute("manejadorSolicitarAval");
        sesion.removeAttribute("manejadorSolicitarAvalFacultad");
        sesion.removeAttribute("manejadorSolicitarAvalDireccion");
        FacesContext context = FacesContext.getCurrentInstance();
        borrarManejadoresInsercionProyecto();
        Map map = context.getExternalContext().getRequestParameterMap();
        Object o = (Object) map.get("idAvalConsulta");
        Long id = Long.valueOf((String) o);
        sesion.setAttribute("idAval", id);
        sesion.setAttribute("centroExtensionAval", false);
        sesion.setAttribute("esCentroExtension", false);
        sesion.setAttribute("esConsulta", true);
        sesion.setAttribute("esEdicion", false);

        if ((Long) sesion.getAttribute("idAval") <= 4855) {
            return "avalarConsulta";
        } else {
            return "avalarConsultaHome";
        }

    }

    public String editarAvalProyecto() {
        sesion.setAttribute("idAval", null);
        sesion.removeAttribute("manejadorSolicitudAval");
        sesion.removeAttribute("manejadorConsultarAval");
        sesion.removeAttribute("manejadorSolicitudAvalHome");
        sesion.removeAttribute("manejadorConsultarAvalHome");
        sesion.removeAttribute("manejadorSolicitarAval");
        sesion.removeAttribute("manejadorSolicitarAvalFacultad");
        sesion.removeAttribute("manejadorSolicitarAvalDireccion");
        FacesContext context = FacesContext.getCurrentInstance();
        borrarManejadoresInsercionProyecto();
        Map map = context.getExternalContext().getRequestParameterMap();
        Object o = (Object) map.get("idAvalConsulta");
        Long id = Long.valueOf((String) o);

        try {

            List<Aval> lista = servicioGeneral.obtenerObjetosLimitado(Aval.class,
                    "select #documento a.documento from Aval a where " + " a.id = '" + id + "'");

            Aval aval = null;
            if (lista != null && lista.size() > 0) {
                aval = (Aval) lista.get(0);
            }

            if (aval != null && aval.getDocumento().equals(personaActual.getId().getDocumento())) {
                sesion.setAttribute("idAval", id);
                sesion.setAttribute("centroExtensionAval", false);
                sesion.setAttribute("esCentroExtension", false);
                sesion.setAttribute("esConsulta", false);
                sesion.setAttribute("esEdicion", true);

                return "avalarEditarHome";
            } else {
                sesion.setAttribute("idAval", id);
                sesion.setAttribute("centroExtensionAval", false);
                sesion.setAttribute("esCentroExtension", false);
                sesion.setAttribute("esConsulta", true);
                sesion.setAttribute("esEdicion", false);

                if ((Long) sesion.getAttribute("idAval") <= 4855) {
                    return "avalarConsulta";
                } else {
                    return "avalarConsultaHome";
                }
            }
        } catch (Exception e) {
            sesion.setAttribute("idAval", id);
            sesion.setAttribute("centroExtensionAval", false);
            sesion.setAttribute("esCentroExtension", false);
            sesion.setAttribute("esConsulta", false);
            sesion.setAttribute("esEdicion", true);
            return "avalarEditarHome";
        }

    }

    public void setFilteredProyectos(List<ProyectoVista> filteredProyectos) {
        this.filteredProyectos = filteredProyectos;
    }

    public List<ProyectoVista> getFilteredProyectos() {
        return filteredProyectos;
    }

    public List<ProyectoVista> getListaVisibles() {
        return listaVisibles;
    }

    public ProyectoVista getProyectoSeleccionado() {
        return proyectoSeleccionado;
    }

    public void setProyectoSeleccionado(ProyectoVista proyetoSeleccionado) {
        this.proyectoSeleccionado = proyetoSeleccionado;
    }

    public String listarSolicitudes() {
        super.sesion.setAttribute("idProyecto", proyectoSeleccionado.getId());
        sesion.removeAttribute("manejadorSolicitudes");
        return "solicitudesAdmProyecto";
    }

    public String ingresarInforme() {
        super.sesion.setAttribute("idProyecto", proyectoSeleccionado.getId());
        super.sesion.setAttribute("esPrincipalProyectoInforme", proyectoSeleccionado.isEsPrincipal());
        super.sesion.setAttribute("esTutorProyectoInforme", proyectoSeleccionado.isEsTutor());
        super.sesion.setAttribute("esPryContrapartida", proyectoSeleccionado.isEsPryContrapartida());
        super.sesion.setAttribute("esJornadaDocente", proyectoSeleccionado.isEsJornadaDocente());
        super.sesion.setAttribute("esPermisoMarco", proyectoSeleccionado.isEsPermisoMarco());
        super.sesion.setAttribute("esPermisoMarcoAsignatura", proyectoSeleccionado.isEsPermisoMarcoAsignatura());
        sesion.removeAttribute("manejadorPrincipalInforme");
        return "principalInformes";
    }

    public String editarProyectoActivo() {
        borrarManejadoresInsercionProyecto();
        Long id = (proyectoSeleccionado).getId();
        Proyecto proyectoActual = servicioProyecto.obtenerProyecto(id, ProyectoDAOHibernate.INFORMACION_GENERAL);
        Convocatoria conv = servicioModalidad.obtenerConvocatoria(proyectoActual.getModalidad().getId());
        if(conv.isUsarFormularioProyectoEditorial()) {
        	sesion.setAttribute("Pro_Editorial_ID", id);
            sesion.removeAttribute("ManejadorEditorialProyectosRegistro");
            return "registroProyectoEditorial";
        }else {
	        boolean consultaFichaMinina = true;
	        sesion.setAttribute("proyectoFichaMinina", proyectoSeleccionado);
	        sesion.setAttribute("consultaFichaMinina", consultaFichaMinina);
	        return servicioProyecto.consultarProyectoGenerico(proyectoActual, sesion, conv);
        }
    }

    public String consultarConvocatoriaArticulo() {
        borrarManejadoresInsercionProyecto();
        String idArticulo = (proyectoSeleccionado).getId().toString();
        sesion.removeAttribute("manejadorDatosBasicos");
        sesion.setAttribute("idArticulo", idArticulo);
        return "ConsultarSolicitudConvocatoriaArticuloXId";
    }

    public void reporteFichaQuipu() {

        ReporteBirt r = new ReporteBirt();
        r.adicionarParametro("id", proyectoSeleccionado.getId().toString());
        r.setFormato(ReporteBirt.FORMATO_PDF);
        r.setNombreReporte("/quipu/REPORTE_FICHA_QUIPU_CONVOCATORIAS");
        if (proyectoSeleccionado.getEsFichaMinima()) {
            r.adicionarParametro("ext", "S");
        } else {
            r.adicionarParametro("ext", "N");
        }
        sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
    }

    public void imprimirProyecto() {
        Long id = proyectoSeleccionado.getId();
        Proyecto proyectoActual = servicioProyecto.obtenerProyecto(id, ProyectoDAOHibernate.INFORMACION_GENERAL);
        if (proyectoActual != null) {
        	if(((Convocatoria) proyectoActual.getModalidad()).isUsarFormularioProyectoEditorial()) {
        		servicioProyecto.imprimirReporteProyectoEditorial(proyectoSeleccionado.getId(), sesion,false); //false, no es evaluador
        	}else {
        		servicioProyecto.imprimirReporteProyecto(proyectoActual, sesion, false);
        	}
        }

    }

    public void imprimirProyectoAsociado() {
        Proyecto proyectoActual2 = servicioProyecto.obtenerProyecto(Long.parseLong(proyectoSeleccionado.getCodigoDib()),
                ProyectoDAOHibernate.INFORMACION_GENERAL);

        servicioProyecto.imprimirReporteProyecto(proyectoActual2, sesion, false);

    }
    
    public void imprimirProyectoLegalizacion() {
        Long id = proyectoSeleccionado.getId();
        Proyecto proyectoActual = servicioProyecto.obtenerProyecto(id, ProyectoDAOHibernate.INFORMACION_GENERAL);
        if (proyectoActual != null) {
            servicioProyecto.imprimirReporteProyectoLegalizacion(proyectoActual, sesion);
        }
    }

    public String imprimirCartaInicio() {
        Long id = proyectoSeleccionado.getId();
        Investigador in = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(id);
        if (personaActual.getId().getDocumento().equals(in.getId().getDocumento())
                && personaActual.getId().getTipoDocumento().equals(in.getId().getTipoDocumento())) {
            sesion.setAttribute("idAceptacion", id);
            sesion.removeAttribute("manejadorAceptacionInvestigador");
            return "aceptacionAdmInvestigador";
        } else {
            try {
                List<InvestigadorProyecto> investigadores = servicioProyecto.obtenerInvestigadoresProyecto(id);
                String tipoTutor = proyectoSeleccionado.getHabilitarInformesTutor();
                Iterator<InvestigadorProyecto> it = investigadores.iterator();
                while (it.hasNext()) {
                    InvestigadorProyecto invPro = (InvestigadorProyecto) it.next();
                    if (tipoTutor.indexOf("-" + invPro.getTipo().getId() + "-") != -1
                            && personaActual.getId().getDocumento()
                                    .equals(invPro.getInvestigador().getId().getDocumento())
                            && personaActual.getId().getTipoDocumento()
                                    .equals(invPro.getInvestigador().getId().getTipoDocumento())) {
                        sesion.setAttribute("idAceptacion", id);
                        sesion.removeAttribute("manejadorAceptacionInvestigador");
                        return "aceptacionAdmInvestigador";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
        return "";
    }

    public void reporteRequisitos() {
        ProyectoVista pv = proyectoSeleccionado;
        ReporteBirt r = new ReporteBirt();
        r.adicionarParametro("id", pv.getId().toString());
        r.setFormato(ReporteBirt.FORMATO_PDF);
        r.setNombreReporte("proyecto/ReporteRequisitos");
        sesion.setAttribute("reporte", r);
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            context.getExternalContext().dispatch("/ReporteEngineServlet");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            context.responseComplete();
        }
    }

    public void reporteReclamacionRequisitos() {

        ProyectoVista pv = proyectoSeleccionado;
        Proyecto proyectoActual = servicioProyecto.obtenerProyecto(pv.getId(),
                ProyectoDAOHibernate.INFORMACION_GENERAL);

        ReporteBirt r = new ReporteBirt();
        r.adicionarParametro("id", pv.getId().toString());
        r.setFormato(ReporteBirt.FORMATO_PDF);

        r.setNombreReporte("proyecto/ReporteReclamaciones");

        sesion.setAttribute("reporte", r);
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            context.getExternalContext().dispatch("/ReporteEngineServlet");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            context.responseComplete();
        }
    }

    public void reporteReclamacionEvaluacion() {

        ProyectoVista pv = proyectoSeleccionado;
        Proyecto proyectoActual = servicioProyecto.obtenerProyecto(pv.getId(),
                ProyectoDAOHibernate.INFORMACION_GENERAL);

        ReporteBirt r = new ReporteBirt();
        r.adicionarParametro("id", pv.getId().toString());
        r.setFormato(ReporteBirt.FORMATO_PDF);

        r.setNombreReporte("proyecto/ReporteReclamacionesEvaluacion");

        sesion.setAttribute("reporte", r);
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            context.getExternalContext().dispatch("/ReporteEngineServlet");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            context.responseComplete();
        }
    }

    public String irReclamacionesProyecto() {
        Long idProyectoReclamacion = proyectoSeleccionado.getId();
        sesion.setAttribute("idProyectoReclamacion", idProyectoReclamacion);
        sesion.removeAttribute("ManejadorProyectosReclamacion");
        return "irReclamacionProyectos";
    }

    public String irReclamacionesProyectoEval() {
        Long idProyectoReclamacion = proyectoSeleccionado.getId();
        sesion.setAttribute("idProyectoReclamacionEval", idProyectoReclamacion);
        sesion.removeAttribute("ManejadorProyectosReclamacionEvaluacion");
        return "irReclamacionProyectosEval";
    }

    public String borrarPropuesto() {
        Long id = ((ProyectoVista) (proyectoSeleccionado)).getId();
        Proyecto pry = servicioProyecto.obtenerProyectoHistorico(id);
        pry.cambiarEstadoPersona(EstadoProyecto.BORRADO, cargarPersonaActual());
        servicioProyecto.ingresarProyecto(pry);
        sesion.removeAttribute("manejadorProyectosInvestigador");
        return "misProyectos";
    }

    public String irAEvaluadoresActivo() {
        Long id = proyectoSeleccionado.getId();
        Proyecto proyectoActual = servicioProyecto.obtenerProyecto(id, ProyectoDAOHibernate.EVALUADORES);
        sesion.setAttribute("proyecto", proyectoActual);
        sesion.removeAttribute("manejadorConsultaEvaluadores");
        return "evaluadoresAdm";
    }

    public void reporteGastosQUIPU() {
    	Proyecto proyectoActual = servicioGeneral.obtenerObjetoXID(Proyecto.class, proyectoSeleccionado.getId().toString()).get(0);
    	
        String codigoQuipuTemporal = proyectoSeleccionado.getCodigoQuipu().toString();
		String nombreReporte = "/quipu/ReporteQuipuEjecucionSEDE";
		Investigador inv = proyectoActual.getResponsable();
		ReporteBirt r = new ReporteBirt();

		if (null != inv.getDependencia()) {
			String sedeId = inv.getDependencia().getSede().getId().toString();
			r.adicionarParametro("idSede", sedeId);
			String invId = inv.getId().getDocumento();
			r.adicionarParametro("invID", invId);
		}

		r.adicionarParametro("codigoQuipu", codigoQuipuTemporal);
		r.setFormato(ReporteBirt.FORMATO_PDF);
		r.setNombreReporte(nombreReporte);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().dispatch("/ReporteEngineServlet");
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			context.responseComplete();
		}
    }

    public String editarFichaMinima() {
        sesion.removeAttribute("consultaFichaMinina");
        sesion.removeAttribute("esProyectoFichaMinimaNueva");
        sesion.removeAttribute("proyectoFichaMinimaNueva");
        sesion.removeAttribute("proyectoFichaMinina");
        sesion.removeAttribute("manejadorFichaMinima");
        sesion.removeAttribute("manejadorFichaMinimaHome");
        sesion.removeAttribute("manejadorFichaMinimaHome");
        sesion.setAttribute("proyectoFichaMinina", proyectoSeleccionado);
        
        Proyecto proyectoActual = servicioProyecto.obtenerProyecto(proyectoSeleccionado.getId(),
                ProyectoDAOHibernate.INFORMACION_GENERAL);
        if (proyectoActual != null) {
            try {
                sesion.setAttribute("idConvocatoriaActual", proyectoActual.getModalidad().getId());
            } catch (Exception exception) {
                sesion.removeAttribute("idConvocatoriaActual");
            }
        }
        sesion.setAttribute("consultaFichaMinina", false);
        sesion.removeAttribute("consultaFichaMinina");
        sesion.removeAttribute("proyecto");
        if(((Convocatoria) proyectoActual.getModalidad()).isUsarFormularioProyectoEditorial()){
        	sesion.setAttribute("Pro_Editorial_ID", proyectoSeleccionado.getId());
            sesion.setAttribute("Pro_Editorial_Editar", "Editar-"+proyectoSeleccionado.getId());
            sesion.removeAttribute("ManejadorEditorialProyectosRegistro");
            return "registroProyectoEditorial";	
        }
        return "fichaMinimaHome";
    }

    public String consultarFichaMinima() {

        sesion.removeAttribute("consultaFichaMinina");
        sesion.removeAttribute("proyectoFichaMinimaNueva");
        sesion.removeAttribute("proyectoFichaMinina");
        sesion.removeAttribute("manejadorDatosBasicos");
        sesion.removeAttribute("esProyectoFichaMinimaNueva");
        sesion.removeAttribute("manejadorFichaMinimaHome");
        sesion.setAttribute("proyectoFichaMinina", proyectoSeleccionado);
        sesion.setAttribute("consultaFichaMinina", true);
        sesion.removeAttribute("idConvocatoriaActual");
        sesion.removeAttribute("proyecto");
        if (proyectoSeleccionado.getProyectoPadre() != null && proyectoSeleccionado.getProyectoPadre() == 0) {
            Long id = (proyectoSeleccionado).getId();
            Proyecto proyectoActual = servicioProyecto.obtenerProyecto(id, ProyectoDAOHibernate.INFORMACION_GENERAL);
            Convocatoria conv = servicioModalidad.obtenerConvocatoria(proyectoActual.getModalidad().getId());
            sesion.setAttribute("idConvocatoriaActual", conv.getId());
            boolean consultaFichaMinina = true;
            sesion.setAttribute("proyectoFichaMinina", proyectoSeleccionado);
            sesion.setAttribute("consultaFichaMinina", consultaFichaMinina);
            return "consultarFichaMinimaHome";
        } else {
            return "fichaMinimaHome";
        }
    }

    public String crearVersionFicha() {
        sesion.removeAttribute("consultaFichaMinina");
        sesion.removeAttribute("esProyectoFichaMinimaNueva");
        sesion.removeAttribute("proyectoFichaMinimaNueva");
        sesion.removeAttribute("proyectoFichaMinina");
        // Crear copia del proyecto
        Long idNewPry = servicioGeneral.duplicarProyecto(proyectoSeleccionado.getId()); // id
                                                                                        // pry
                                                                                        // nuevo
                                                                                        // 23782L;
        String hql = "select p from Proyecto p where p.id =" + idNewPry;
        List proyectos = servicioGeneral.obtenerObjetos(hql);
        if (proyectos.size() > 0) {
            Proyecto proyectoNew = (Proyecto) proyectos.get(0); // Proyecto
                                                                // nuevo
                                                                // completo

            sesion.removeAttribute("manejadorFichaMinimaHome");
            sesion.setAttribute("proyectoFichaMinimaNueva", proyectoNew);
            sesion.setAttribute("consultaFichaMinina", false);
            sesion.removeAttribute("idConvocatoriaActual");
            sesion.removeAttribute("consultaFichaMinina");
            sesion.setAttribute("esProyectoFichaMinimaNueva", true);

            return "fichaMinimaHome";
        } else {
            return "";
        }

    }

    // lmom
    public String irProyectoPadre() {
        sesion.removeAttribute("consultaFichaMinina");
        sesion.removeAttribute("esProyectoFichaMinimaNueva");
        sesion.removeAttribute("proyectoFichaMinimaNueva");
        sesion.removeAttribute("proyectoFichaMinina");

        // Id del pry padre
        Long idNewPry = proyectoSeleccionado.getProyectoPadre();
        String hql = "select p from Proyecto p where p.id =" + idNewPry;
        List proyectos = servicioGeneral.obtenerObjetos(hql);
        Proyecto proyectoNew = (Proyecto) proyectos.get(0); // Proyecto nuevo
                                                            // completo
        sesion.removeAttribute("manejadorFichaMinima");
        sesion.removeAttribute("manejadorFichaMinimaHome");
        sesion.setAttribute("proyectoFichaMinimaNueva", proyectoNew);
        sesion.setAttribute("consultaFichaMinina", true);
        sesion.removeAttribute("idConvocatoriaActual");
        sesion.setAttribute("esProyectoFichaMinimaNueva", true);
        return "fichaMinimaHome";
    }

    // lmom
    public void imprimirProyectoHijo() {

        Long id = proyectoSeleccionado.getId();
        // obtener hijo
        String hql = "select p from Proyecto p where p.proyectoPadre =" + id;
        List proyectos = servicioGeneral.obtenerObjetos(hql);
        Proyecto proyectoNew = (Proyecto) proyectos.get(0); // Pry hijo

        // imprimir hijo
        Proyecto proyectoActual = servicioProyecto.obtenerProyecto(proyectoNew.getId(),
                ProyectoDAOHibernate.INFORMACION_GENERAL);
        if (proyectoActual != null) {
            servicioProyecto.imprimirReporteProyecto(proyectoActual, sesion, false);
        }

    }

    // lmom
    public String consultarFichaMinimaInicial() {
        sesion.removeAttribute("proyectoFichaMinina");
        sesion.setAttribute("consultaFichaMinina", true);
        sesion.removeAttribute("manejadorDatosBasicos");
        sesion.removeAttribute("consultaFichaMinina");
        sesion.removeAttribute("proyectoFichaMinimaNueva");
        sesion.removeAttribute("idConvocatoriaActual");
        sesion.removeAttribute("manejadorFichaMinimaHome");
        sesion.setAttribute("esProyectoFichaMinimaNueva", true);
        if (proyectoSeleccionado.getProyectoPadre() != null && proyectoSeleccionado.getProyectoPadre() == 0) {
            Long id = (proyectoSeleccionado).getId();
            String hql = "select p from Proyecto p where p.proyectoPadre =" + id;
            List proyectos = servicioGeneral.obtenerObjetos(hql);
            Proyecto proyectoActual = (Proyecto) proyectos.get(0);
            Convocatoria conv = servicioModalidad.obtenerConvocatoria(proyectoActual.getModalidad().getId());
            sesion.setAttribute("idConvocatoriaActual", conv.getId());
            boolean consultaFichaMinina = true;
            sesion.setAttribute("proyectoFichaMinina", proyectoActual);
            sesion.setAttribute("proyectoFichaMinimaNueva", proyectoActual);
            sesion.setAttribute("consultaFichaMinina", consultaFichaMinina);
            return "consultarFichaMinimaHome";
        } else {
            return "fichaMinimaHome";
        }

    }
    
    public String irAgregarDetalleEstudiantes(){
    	Long id = proyectoSeleccionado.getId();
    	sesion.setAttribute("idProyectoDetalleEst", id);
        sesion.removeAttribute("ManejadorAgregarDetalleEstProyecto");
    	return "agregarDetalleEstudiantes";
    }

    public List<ProyectoVista> getListaBiodiversidad() {
        return listaBiodiversidad;
    }

    public void setListaBiodiversidad(List<ProyectoVista> listaBiodiversidad) {
        this.listaBiodiversidad = listaBiodiversidad;
    }

    public List<ProyectoVista> getFilteredRegistroBiodiversidad() {
        return filteredRegistroBiodiversidad;
    }

    public void setFilteredRegistroBiodiversidad(List<ProyectoVista> filteredRegistroBiodiversidad) {
        this.filteredRegistroBiodiversidad = filteredRegistroBiodiversidad;
    }

}
