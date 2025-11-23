package co.edu.unal.hermes.vista.aval;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.vista.aval.base.BaseManejadorSolicitarAvalFacultad;

public class ManejadorAprobacionAvalUAB extends BaseManejadorSolicitarAvalFacultad {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Proyecto proyectoIngresado;
    private Aval avalOtorgado;
    private List<Aval> listaAvalesOtorgados;
    private int tamannioLista;
    private int tamannioListaOtorgados;
    private String nombreProfesorSolicitante;
    private Aval avalOtorgadoSeleccionado;
    boolean esConsulta = false;
    private InvestigadorInterno investigadorInterno;

    public ManejadorAprobacionAvalUAB() {
    	sesion.removeAttribute("manejadorSemillerosSolicitudUAB");
    	sesion.removeAttribute("manejadorSemillerosConsultaUAB");
        investigadorInterno = servicioPersona
                .obtenerInvestigadorInterno(investigadorActual.getId());

        // Avales por revisar
        String sql = "select av from Aval av where av.dependencia.id = '" + investigadorInterno.getDependencia2().getId()
                + "' and (av.tipo = '" + Aval.TIPO_JORNADA_DOCENTE + "')  and (av.aviEstado = '" + Aval.ENVIADO
                + "') order by av.aviId asc ";
        listaAval = servicioGeneral.obtenerObjetos(Aval.class, sql);
        if (!esListaVacia(listaAval)) {
            tamannioLista = listaAval.size();
            for (int i = 0; i < listaAval.size(); i++) {
                Persona per = servicioPersona.obtenerPersona(
                        new IdPersona(listaAval.get(i).getDocumento(), listaAval.get(i).getTipoDocumento()));
                listaAval.get(i).setNombreInvestigador(per.getNombreCompleto());
            }
        }

        // Avales otorgados en la dependencia
        String sqlOtorgado = "select av from Aval av where av.dependencia.id = '"
                + investigadorInterno.getDependencia2().getId() + "' and (av.tipo = '" + Aval.TIPO_JORNADA_DOCENTE
                + "') and (av.aviEstado = '" + Aval.REVISADO_UAB + "' and av.aviAvalUab = '" + Aval.APROBADO
                + "') order by av.aviId asc ";
        setListaAvalesOtorgados(servicioGeneral.obtenerObjetos(Aval.class, sqlOtorgado));

        if (!esListaVacia(listaAvalesOtorgados)) {
            tamannioListaOtorgados = listaAvalesOtorgados.size();
            for (int i = 0; i < listaAvalesOtorgados.size(); i++) {
                Persona per = servicioPersona.obtenerPersona(new IdPersona(listaAvalesOtorgados.get(i).getDocumento(),
                        listaAvalesOtorgados.get(i).getTipoDocumento()));
                listaAvalesOtorgados.get(i).setNombreInvestigador(per.getNombreCompleto());
            }
        }
    }

    public String editarAval() {
        consultarArchivosAdjuntos();
        return "AprobarAvalECP_UAB";
    }

    public String anterior() {
        eliminarManejadores();
        return "AprobarAval";
    }

    private void eliminarManejadores() {
        sesion.removeAttribute("ManejadorAprobacionAvalUAB");
        sesion.removeAttribute("manejadorAvalInformeUab");
    }

    public void descargarArchivoInformacionAdicional() {
        if (aval != null) {
            servicioAval.imprimirReporteAval(aval.getAviId(), sesion);
        }
    }

    public String guardarAvalGenerado() {

        if (this.aval == null) {
            return "";
        }
        if (verificarDecision()) {

            // Guardar Aval
            this.aval.setAviFechaAvalUab(new Date());
            this.servicioGeneral.guardarObjeto(this.aval);
            guardarArchivosSoporte();
            
            Persona personaAux = servicioPersona.obtenerPersona(new IdPersona(aval.getDocumento(),aval.getTipoDocumento()));

            if (this.aval.getAviAvalUab().equals(Aval.APROBADO)) {
                
                aprobarProyectoJornadaDocente();
                
                // ENVIAR CORREO
                correoActual = cargarPlantilla(184);
                editarCorreo(personaAux, aval);
                Correo correo = new Correo();
                correo.setOrigen(Correo.CORREO_HERMES);
                correo.adicionarDireccion(personaAux.getEmail());
                //correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
                correo.setAsunto(correoActual.getAsunto());
                correo.setCuerpo(cuerpoCorreo);
                
                if (investigadorInterno.getDependencia2().getSede().isEsSedePresenciaNacional()) {
                    String sql = "JOIN i.roles r " +
                                 "WHERE r.id = 'AD' " +
                                 "AND i.dependencia2.sede.id = '" + this.investigadorActual.getDependencia2().getSede().getId() + "'";

                    for (InvestigadorInterno inv : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class, sql)) {
                        if (tieneRolVigente(inv, "AD") && inv.getEmail() != null) {
                            correo.adicionarDireccion(inv.getEmail());
                        }
                    }
                } else {
                    String sql = "JOIN i.roles r " +
                                 "WHERE r.id = 'AF' " +
                                 "AND i.dependencia2.facultad.id = '" + this.investigadorActual.getDependencia2().getFacultad().getId() + "'";

                    for (InvestigadorInterno inv : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class, sql)) {
                        if (tieneRolVigente(inv, "AF") && inv.getEmail() != null) {
                            correo.adicionarDireccion(inv.getEmail());
                        }
                    }
                }

                
                servicioCorreo.enviarCorreo(correo);
            }

            if (this.aval.getAviAvalUab().equals(Aval.NEGADO)) {
                
                correoActual = cargarPlantilla(185);
                enviarCorreoGenerico(correoActual, personaAux, false);
            }

            if (this.aval.getAviEstado().equals(Aval.DEVUELTO)) {
                correoActual = cargarPlantilla(170);
                enviarCorreoGenerico(correoActual, personaAux, true);
            }
            crearHistoricoEstadoAval(aval, cargarPersonaActual(), "DE");
        }

        return anterior();
    }
    


    private boolean verificarDecision() {
        boolean temp = false;
        this.aval.setAviAvalUab("");
        
        if (this.selItem == 2) {
            this.aval.setAviEstado(Aval.REVISADO_UAB);
            this.aval.setAviAvalUab(Aval.APROBADO);
            temp = true;
        }
        if (this.selItem == 3) {
            this.aval.setAviEstado(Aval.REVISADO_UAB);
            this.aval.setAviAvalUab(Aval.NEGADO);
            temp = true;
        }
        if (this.selItem == 4) {
            this.aval.setAviEstado(Aval.DEVUELTO);

            Proyecto proyectoAval = servicioProyecto.obtenerProyecto(aval.getIdProyecto(),
                    ProyectoDAOHibernate.DATOS_BASICOS);

            if (edicionProyecto && proyectoAval.getModalidad().getId().equals(MODALIDAD_FICHA_MINIMA_ID)
                    && proyectoAval.getEstadoProyecto().getId().equals(EstadoProyecto.PROPUESTO)) {

                proyectoAval.cambiarEstadoPersona(EstadoProyecto.INGRESANDO, cargarPersonaActual(),
                        "Devolución aval jornada docente");
                servicioGeneral.guardarObjeto(proyectoAval);
            }
            temp = true;
        }
        return temp;
    }

    public String irListaAvalesSolicitados() {
        return anterior();
    }

    public String irListaAvalesOtorgados() {
        eliminarManejadores();
        return "ConsultaAvalesOtorgados";
    }

    public String irListaInformes() {
        eliminarManejadores();
        return "informesJornadaDocente";
    }

    public Proyecto getProyectoIngresado() {
        return proyectoIngresado;
    }

    public void setProyectoIngresado(Proyecto proyectoIngresado) {
        this.proyectoIngresado = proyectoIngresado;
    }

    public int getTamannioLista() {
        return tamannioLista;
    }

    public void setTamannioLista(int tamannioLista) {
        this.tamannioLista = tamannioLista;
    }

    public String getNombreProfesorSolicitante() {
        return nombreProfesorSolicitante;
    }

    public void setNombreProfesorSolicitante(String nombreProfesorSolicitante) {
        this.nombreProfesorSolicitante = nombreProfesorSolicitante;
    }

    public Aval getAvalOtorgado() {
        return avalOtorgado;
    }

    public void setAvalOtorgado(Aval avalOtorgado) {
        this.avalOtorgado = avalOtorgado;
    }

    public int getTamannioListaOtorgados() {
        return tamannioListaOtorgados;
    }

    public void setTamannioListaOtorgados(int tamannioListaOtorgados) {
        this.tamannioListaOtorgados = tamannioListaOtorgados;
    }

    public Aval getAvalOtorgadoSeleccionado() {
        return avalOtorgadoSeleccionado;
    }

    public void setAvalOtorgadoSeleccionado(Aval avalOtorgadoSeleccionado) {
        this.avalOtorgadoSeleccionado = avalOtorgadoSeleccionado;
    }

    public boolean isEsConsulta() {
        return esConsulta;
    }

    public void setEsConsulta(boolean esConsulta) {
        this.esConsulta = esConsulta;
    }

    public List<Aval> getListaAvalesOtorgados() {
        return listaAvalesOtorgados;
    }

    public void setListaAvalesOtorgados(List<Aval> listaAvalesOtorgados) {
        this.listaAvalesOtorgados = listaAvalesOtorgados;
    }

    public InvestigadorInterno getInvestigadorInterno() {
        return investigadorInterno;
    }

    public void setInvestigadorInterno(InvestigadorInterno investigadorInterno) {
        this.investigadorInterno = investigadorInterno;
    }

}
