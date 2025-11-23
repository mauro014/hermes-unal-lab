package co.edu.unal.hermes.vista.evaluadores;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.EstadoCarta;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.HistoricoEstadoProyecto;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.ProyectoCarta;
import co.edu.unal.hermes.modelo.TipoCarta;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.seguimiento.AlertaProyecto;
import co.edu.unal.hermes.modelo.seguimiento.Solicitud;
import co.edu.unal.hermes.modelo.seguimiento.TipoSolicitud;
import co.edu.unal.hermes.modelo.seguimiento.TipoViaSolicitud;
import co.edu.unal.hermes.utils.Fecha;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorAceptacionInvestigador extends ManejadorBase {

    private static final long serialVersionUID = 5690165789165988091L;

    private boolean aceptacion = true;
    private ProyectoCoordinador proyectoCoordinador;
    private String idProyecto;
    String mensajeAprobacion;
    String descripcionNoAprobacion;

    public ManejadorAceptacionInvestigador() throws SQLException {

        String idAcep = String.valueOf(sesion.getAttribute("idAceptacion"));

        proyectoCoordinador = cargarDatosCarta(idAcep);
        
    }

    /**
     * Se carga plantilla segun codigo ingresado.
     *
     * @param cod_id
     *            the cod_id
     * @return the correo plantilla
     */
    public CorreoPlantilla cargarPlantilla(int cod_id) {
        CorreoPlantilla correoPlantilla = new CorreoPlantilla();
        List<CorreoPlantilla> lista = servicioGeneral.obtenerObjetos(CorreoPlantilla.class,
                "from CorreoPlantilla c where c.id='" + cod_id + "'");
        if (lista != null && lista.size() > 0) {
            correoPlantilla = (CorreoPlantilla) lista.get(0);
        }
        return correoPlantilla;
    }

    /**
     * Se descarga acta de inicio generada por el coordinador.
     */
    public void descargarCartaInicio() {
        descargarArchivoGenerico("HER_SEG_PROYECTO_PERSONA//SEG_CARTAINICIO", proyectoCoordinador.getId().toString(),
                proyectoCoordinador.getId().toString() + ".pdf");
    }

    /**
     * Metodo para revisar correo y modificarlo con los datos ingresados.
     */
    public String editarCorreo(String cuerpoCorreo) {
        String correo = cuerpoCorreo.replaceAll("<<FECHA>>", Fecha.fechaActual());
        String investigador = personaActual.getNombreCompletoMinusculas();
        correo = correo.replaceAll("<<INVESTIGADOR>>", investigador);
        String nombre = Matcher.quoteReplacement(proyectoCoordinador.getNombreProyecto());
        correo = correo.replaceAll("<<TITULO>>", nombre);
        correo = correo.replaceAll("<<CODIGO>>", proyectoCoordinador.getIdProyecto().toString());
        if(getDescripcionNoAprobacion() != null){
        	correo = correo.replaceAll("<<JUSTIFICACION>>", getDescripcionNoAprobacion());	
        }        

        return correo;
    }

    public ProyectoCoordinador cargarDatosCarta(String idAceptacion) throws SQLException {

        ProyectoCoordinador proyectoCoordinador = null;
        descripcionNoAprobacion = "";

        // Se carga proyecto coordaindor
        List<ProyectoCoordinador> listaProyectosCoordinador = servicioProyecto.obtenerProyectosxId(
                personaActual.getId().getDocumento(), personaActual.getId().getTipoDocumento(), idAceptacion);

        if (listaProyectosCoordinador != null && listaProyectosCoordinador.size() > 0) {
            for (int i = 0; i < listaProyectosCoordinador.size(); i++) {
                proyectoCoordinador = listaProyectosCoordinador.get(0);
            }
        }

        // Se verifica si no fue aprobado y se carga la descripción de la no
        // aprobación.
        if (proyectoCoordinador != null && proyectoCoordinador.getSiAprobacion() != null
                && !proyectoCoordinador.getSiAprobacion().equals(EstadoProyecto.APROBADO)) {
            descripcionNoAprobacion = servicioGeneral.consultaDescripcionSolicitud(proyectoCoordinador.getIdProyecto());
        }

        if ((proyectoCoordinador.getSiAprobacion() != null
                && (this.proyectoCoordinador.getSiAprobacion().equals(ProyectoCoordinador.APROBADO)))) {
            this.aceptacion = true;
        }

        return proyectoCoordinador;
    }

    public String guardarCompromisos() throws SQLException {

        proyectoCoordinador.setSiAprobacion(ProyectoCoordinador.APROBADO);
        servicioGeneral.guardarObjeto(proyectoCoordinador);

        CorreoPlantilla correoActual = cargarPlantilla(57);
        String cuerpoCorreo = editarCorreo(correoActual.getCuerpo());
        Correo correo = new Correo();
        correo.setOrigen(Correo.CORREO_HERMES_SOLICITUDES);
        Persona pe = servicioProyecto.obtenerCoordinadorProyecto(proyectoCoordinador.getIdProyecto());

        String dirCorreo = pe.getEmail();
        correo.adicionarDireccion(dirCorreo);
        correo.adicionarDireccion(Correo.CORREO_HERMES);
        correo.adicionarCopiaOculta(new String(pe.getEmail()));
        correo.setAsunto(correoActual.getAsunto());
        correo.setCuerpo(cuerpoCorreo);
        
        //adicionar direcciones de correo de personas con rol Unidad Administritiva Investigacion
        Dependencia dpn = (Dependencia) servicioDependencia.obtenerDependencia(proyectoCoordinador.getIdFacultad());
        List listaPersonasUAI = servicioPersona.obtenerPersonasUnidadAdministrativa(proyectoCoordinador.getIdFacultad(), dpn.getSede().getId().toString());
        
        for (Iterator iterator = listaPersonasUAI.iterator(); iterator
				.hasNext();) {
			Persona p = (Persona) iterator.next();
			correo.adicionarDireccion(p.getEmail());
		}
        
        servicioCorreo.enviarCorreo(correo);
        
        //ENVIO CORREO PROYECTO CON ESTUDIANTES SIN DETALLE
        boolean enviarCorreo = false;
        List<Proyecto> listPry = new ArrayList<Proyecto>();
        listPry = servicioGeneral.obtenerObjetoXID(Proyecto.class, proyectoCoordinador.idProyecto.toString());
        if(listPry != null && !listPry.isEmpty()){
        	Proyecto pry = new Proyecto(); 
            pry = listPry.get(0);
            if(pry != null){
            	if(!pry.getListaInvestigadoresProyectoSinDatos().isEmpty()){
            		for(int i=0; i < pry.getListaInvestigadoresProyectoSinDatos().size(); i++){
            			InvestigadorProyecto invProy = new InvestigadorProyecto();
            			invProy = pry.getListaInvestigadoresProyectoSinDatos().get(i);
            			if(invProy.getTipo().getDocumento().contains("ESPO")
            					|| invProy.getTipo().getDocumento().contains("ESPR")){
            				enviarCorreo = true;
            				break;
            			}
            		}
            		if(enviarCorreo){
            			CorreoPlantilla correoPlantillaInvSinDatos = cargarPlantilla(327);
                        String cuerpoCorreoInvSinDatos = editarCorreo(correoPlantillaInvSinDatos.getCuerpo());
                        String asuntoCorreoInvSinDatos = correoPlantillaInvSinDatos.getAsunto();
                        
                        asuntoCorreoInvSinDatos = asuntoCorreoInvSinDatos.replaceAll("<<CODIGO>>", proyectoCoordinador.idProyecto.toString());
                        correoPlantillaInvSinDatos.setAsunto(asuntoCorreoInvSinDatos);
                        
                        Correo correoInvSinDatos = new Correo();
                        correoInvSinDatos.setOrigen(Correo.CORREO_HERMES_SOLICITUDES);
                        //coordinador
                        correoInvSinDatos.adicionarDireccion(pe.getEmail());
                        //docente
                        correoInvSinDatos.adicionarDireccion(personaActual.getEmail());
                        correoInvSinDatos.adicionarDireccion(Correo.CORREO_HERMES);
                        correoInvSinDatos.setAsunto(correoPlantillaInvSinDatos.getAsunto());
                        correoInvSinDatos.setCuerpo(cuerpoCorreoInvSinDatos);
                        servicioCorreo.enviarCorreo(correoInvSinDatos);
            		}
                }
            }
        }

        try {
            String sql = "update HER_PROYECTO set EPR_ID = 'A' where PRY_ID = '" + proyectoCoordinador.idProyecto + "'";
            servicioGeneral.eliminar(sql);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }

        List listaEstadoProyecto = new ArrayList();
        List listaProyectoHistorico = new ArrayList();

        HistoricoEstadoProyecto ep = new HistoricoEstadoProyecto();
        Date fecha1 = new Date();
        ep.setFecha(fecha1);
        ep.setJustificacion("Por aceptación del acta de inicio");
        ep.setResponsable(cargarPersonaActual());

        listaProyectoHistorico = servicioGeneral
                .obtenerListaObjetos("Proyecto where id ='" + proyectoCoordinador.getIdProyecto() + "'");
        Proyecto pr = (Proyecto) listaProyectoHistorico.get(0);
        ep.setProyecto(pr);

        listaEstadoProyecto = servicioGeneral.obtenerListaObjetos("EstadoProyecto where id ='A'");
        EstadoProyecto es = (EstadoProyecto) listaEstadoProyecto.get(0);

        ep.setEstadoProyecto(es);

        servicioGeneral.guardarObjeto(ep);
        Long idSol;
        idSol = servicioGeneral.consultaIdSolicitud(proyectoCoordinador.getIdProyecto());

        String sql = "update HER_SOLICITUD set SOL_RESPUESTA = 'A' where SOL_ID = '" + idSol + "'";
        servicioGeneral.eliminar(sql);

        Proyecto pr2 = new Proyecto();

        pr2 = this.servicioProyecto.obtenerProyecto(pr.getId(), ProyectoDAOHibernate.ASESORES);

        List asesores1 = new ArrayList();
        asesores1.addAll(pr2.getAsesores());

        List listaSolicitudProyecto = new ArrayList();
        listaSolicitudProyecto = servicioGeneral.obtenerListaObjetos("Solicitud where id ='" + idSol + "'");

        if (listaSolicitudProyecto != null && listaSolicitudProyecto.size() > 0) {

            Solicitud sol2 = (Solicitud) listaSolicitudProyecto.get(0);

            AlertaProyecto ale2 = new AlertaProyecto();

            List<AlertaProyecto> lsit = servicioAlertas.obtenerAlertaProyectoSolicitud(pr2, sol2);

            if (!esListaVacia(lsit)) {
                ale2 = lsit.get(0);

                ale2.setEstado("C");
                servicioGeneral.guardarObjeto(ale2);
            }
        }

        List listaTipoCarta = new ArrayList();
        List listaCartaProyecto = new ArrayList();

        EstadoCarta estadoCarta = new EstadoCarta();
        List listaEstadoCarta = new ArrayList();

        listaTipoCarta = servicioGeneral.obtenerListaObjetos("TipoCarta where id ='1'");
        TipoCarta tipoCarta1 = (TipoCarta) listaTipoCarta.get(0);

        Dependencia dependencia;
        dependencia = servicioDependencia.obtenerDependencia(personaActual.getId());

        Date fechaSesion = new Date();
        String fechaS = "2011";

        if (fechaSesion != null) {

            SimpleDateFormat spy = new SimpleDateFormat("yyyy");
            fechaS = spy.format(fechaSesion);

        }

        Convocatoria conv = servicioModalidad.obtenerConvocatoria(pr.getModalidad().getId());
        ConvocatoriaPadre convp = conv.getPadre();
        Long dependenciaRes = Long.parseLong(convp.getDependencia().getId());

        Long idCarta = new Long("0");
        idCarta = servicioGeneral.consultaIdCarta(pr2.getId(), tipoCarta1.getId(), dependenciaRes.toString(), fechaS);

        listaCartaProyecto = servicioGeneral.obtenerListaObjetos(
                "ProyectoCarta P where P.seq ='" + idCarta + "' and P.sede ='" + String.valueOf(dependenciaRes)
                        + "' and P.carta.id ='" + tipoCarta1.getId() + "' and  P.proyecto.id= " + pr2.getId());
        if (listaCartaProyecto != null && listaCartaProyecto.size() > 0) {
            ProyectoCarta proyectoCarta = (ProyectoCarta) listaCartaProyecto.get(0);

            listaEstadoCarta = servicioGeneral.obtenerListaObjetos("EstadoCarta where id ='AP'");
            estadoCarta = (EstadoCarta) listaEstadoCarta.get(0);

            proyectoCarta.setEstadoCarta(estadoCarta);

            servicioGeneral.guardarObjeto(proyectoCarta);
        }

        FacesContext.getCurrentInstance().addMessage("msgs",
                new FacesMessage(FacesMessage.SEVERITY_INFO, "El proyecto ha sido aprobado satisfactoriamente.", ""));

        sesion.removeAttribute("manejadorProyectosInvestigador");

        return "proyectoInvestigadorAceptacion";
    }

    public String noAceptacionCarta() throws SQLException {
        
        if(StringUtils.isBlank(descripcionNoAprobacion)){
            mensajeError("Por favor ingrese motivo no aceptación.");
            return "";
        }

        proyectoCoordinador.setSiAprobacion(ProyectoCoordinador.NO_APROBADO);
        this.servicioGeneral.guardarObjeto(proyectoCoordinador);

        List listaSolicitud = new ArrayList();
        
        Date fecha = new Date();
        Solicitud sol = new Solicitud();
        sol.setFecha(fecha);
        sol.setRespuesta("");
        sol.setDescripcion(descripcionNoAprobacion);

        sol.setProyecto(proyectoCoordinador.getProyecto());
        listaSolicitud = servicioGeneral.obtenerListaObjetos("TipoSolicitud where id ='1'");
        TipoSolicitud ts = (TipoSolicitud) listaSolicitud.get(0);
        sol.setTipoSolicitud(ts);

        TipoViaSolicitud tipoViaSolicitud = servicioSolicitudes
                .buscarTipoViaSolicitudPorId(TipoViaSolicitud.VIA_HERMES);
        sol.setTipoViaSolicitud(tipoViaSolicitud);

        servicioGeneral.guardarObjeto(sol);

        Proyecto pr1 = new Proyecto();

        pr1 = this.servicioProyecto.obtenerProyecto(proyectoCoordinador.getIdProyecto(), ProyectoDAOHibernate.ASESORES);

        List asesores = new ArrayList();
        asesores.addAll(pr1.getAsesores());

        if (!asesores.isEmpty()) {

            AlertaProyecto ale = new AlertaProyecto();
            ale.setEstado("P");
            ale.setFechaGenera(fecha);
            ale.setMensaje(sol.getDescripcion());
            ale.setProyecto(proyectoCoordinador.getProyecto());
            ale.setSolicitud(sol);
            ale.setAsesor((Persona) asesores.get(0));
            servicioGeneral.guardarObjeto(ale);

            List listaTipoCarta = new ArrayList();
            List listaCartaProyecto = new ArrayList();
            EstadoCarta estadoCarta = new EstadoCarta();
            List listaEstadoCarta = new ArrayList();
            listaTipoCarta = servicioGeneral.obtenerListaObjetos("TipoCarta where id ='1'");
            TipoCarta tipoCarta1 = (TipoCarta) listaTipoCarta.get(0);
            Long idCarta = new Long("0");

            Dependencia dependencia;
            dependencia = servicioDependencia.obtenerDependencia(personaActual.getId());

            Date fechaSesion = new Date();
            String fechaS = "2011";

            if (fechaSesion != null) {

                SimpleDateFormat spy = new SimpleDateFormat("yyyy");
                fechaS = spy.format(fechaSesion);

            }

            idCarta = servicioGeneral.consultaIdCarta(pr1.getId(), tipoCarta1.getId(), dependencia.getSede().getId().toString(),
                    fechaS);
            if (idCarta.intValue() > 0) {
                listaCartaProyecto = servicioGeneral.obtenerListaObjetos(
                        "ProyectoCarta P where P.seq ='" + idCarta + "' and P.sede ='" + dependencia.getSede().getId()
                                + "' and P.carta.id ='" + tipoCarta1.getId() + "' and  P.proyecto.id= " + pr1.getId());
                ProyectoCarta proyectoCarta = (ProyectoCarta) listaCartaProyecto.get(0);

                listaEstadoCarta = servicioGeneral.obtenerListaObjetos("EstadoCarta where id ='NP'");
                estadoCarta = (EstadoCarta) listaEstadoCarta.get(0);

                proyectoCarta.setEstadoCarta(estadoCarta);
                servicioGeneral.guardarObjeto(proyectoCarta);
            }
            mensajeAprobacion = "El investigador no ha aceptado los compromisos de la carta de inicio.";
        }
        
        CorreoPlantilla correoActual = cargarPlantilla(288);
        String cuerpoCorreo = editarCorreo(correoActual.getCuerpo());
        
        Correo correo = new Correo();
        correo.setOrigen(Correo.CORREO_HERMES_SOLICITUDES);
        Persona pe = servicioProyecto.obtenerCoordinadorProyecto(proyectoCoordinador.getIdProyecto());        
        String dirCorreo = pe.getEmail();
        correo.adicionarDireccion(dirCorreo);
        correo.adicionarDireccion(Correo.CORREO_HERMES);
        correo.adicionarCopiaOculta(new String(pe.getEmail()));
        correo.setAsunto(correoActual.getAsunto().replaceAll("<<CODIGO>>", proyectoCoordinador.getIdProyecto().toString()));
        correo.setCuerpo(cuerpoCorreo);
        servicioCorreo.enviarCorreo(correo);
        
        FacesContext.getCurrentInstance().addMessage("msgs",
                new FacesMessage(FacesMessage.SEVERITY_WARN, "El proyecto NO ha sido aprobado.", ""));        
        return "";
    }

    public String getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(String idProyecto) {
        this.idProyecto = idProyecto;
    }

    public ProyectoCoordinador getProyectoCoordinador() {
        return proyectoCoordinador;
    }

    public void setProyectoCoordinador(ProyectoCoordinador proyectoUno) {
        this.proyectoCoordinador = proyectoUno;
    }

    public String getMensajeAprobacion() {
        return mensajeAprobacion;
    }

    public void setMensajeAprobacion(String mensajeAprobacion) {
        this.mensajeAprobacion = mensajeAprobacion;
    }

    public boolean isAceptacion() {
        return aceptacion;
    }

    public String getAceptacionVista() {
        if (aceptacion) {
            return "true";
        } else {
            return "false";
        }
    }

    public void setAceptacionVista(String value) {
        aceptacion = "true".equals(value);
    }

    public String getDescripcionNoAprobacion() {
        return descripcionNoAprobacion;
    }

    public void setDescripcionNoAprobacion(String descripcionNoAprobacion) {
        this.descripcionNoAprobacion = descripcionNoAprobacion;
    }

}