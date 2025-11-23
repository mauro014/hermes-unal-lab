package co.edu.unal.hermes.vista.aval;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.ArchivoAval;
import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.modelo.CoinvestigadorAval;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EntidadArticulo;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.PalabraClave;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.vista.aval.base.BaseManejadorSolicitarAval;

public class ManejadorSolicitudAval extends BaseManejadorSolicitarAval {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    

    private boolean verConvocatorias;
    private boolean verSistRegalias;
    private boolean verMovilidadEvento;
    private List<SelectItem> listaObjSocioeconomico;
    private List<SelectItem> listaAreaCiencia;
    private String categoria;
    private List<EntidadArticulo> listaEntidades;
    private List<CoinvestigadorAval> listaCoinvestigadores;
    ArchivoAval documentoSeleccionado;
    ArchivoAval archivoResumen;
    List listaEntidadesParticipantesSR = new ArrayList<SelectItem>();
    String ruta = "";
    List listaDependencia;

    public ManejadorSolicitudAval() {
        esConsulta = true;
        esAvalNuevo = false;
        esEdicion = false;
        listaObjSocioeconomico = new ArrayList<SelectItem>();
        listaAreaCiencia = new ArrayList<SelectItem>();
        listaEntidades = new ArrayList<EntidadArticulo>();
        cargarCategorias();
        Long id = (Long) sesion.getAttribute("idAval");
        List<Aval> lista = servicioGeneral.obtenerAval(id.toString());
        if (!esListaVacia(lista)) {
            aval = lista.get(0);
            categoria = aval.getTipo();
            if ("JI".equals(categoria)) {
                categoria = "PI";
            }
            cambiarTipo();
            listaEntidades = obtenerListaEntidades();

            consultarArchivos();
        }
        cargarEntidadesParticipantes();

        investigadorActual = servicioPersona
                .obtenerInvestigadorProyectos(((Persona) sesion.getAttribute("persona")).getId());
        if (investigadorActual != null) {
            if (investigadorActual.getDependencia() != null) {
                aval.setDependencia(investigadorActual.getDependencia());
            }
            cargarPaises();
            cargarObjetivoSocioEconomico();
            cargarAreaCiencia();
            cargarDepartamentos();
        }

    }

    public void cargarEntidadesParticipantes() {

        listaEntidadesParticipantesSR = new ArrayList<SelectItem>();

        String hqlentidadesParticipantes = "select e from FuenteFinanciacion e where e.internaExterna = 'E' and (e.naturaleza= 'NAT_PUBLIC' or e.naturaleza= 'NAT_PRIVAD' or e.naturaleza= 'NAT_MIXTA' or e.naturaleza= 'NAT_INTERNACIONAL') or e.id='280' order by e.descripcion asc";
        List<FuenteFinanciacion> listaEntidadesParticipantesProyReg = servicioGeneral
                .obtenerObjetos(FuenteFinanciacion.class, hqlentidadesParticipantes);

        if (!esListaVacia(listaEntidadesParticipantesProyReg)) {
            for (int i = 0; i < listaEntidadesParticipantesProyReg.size(); i++) {

                FuenteFinanciacion entidadParticipante = (FuenteFinanciacion) listaEntidadesParticipantesProyReg.get(i);
                listaEntidadesParticipantesSR
                        .add(new SelectItem(entidadParticipante.getId(), entidadParticipante.getDescripcion()));
            }
        }
        cargarConvocatoriasExternas();
    }

    public void descargarArchivoCoor() {

        if (documentoSeleccionado != null) {
            descargarArchivoAvalGenerico(documentoSeleccionado.getId());
        }

    }

    public List<EntidadArticulo> obtenerListaEntidades() {
        List<EntidadArticulo> listaEntidadesAval = new ArrayList<EntidadArticulo>();
        if (aval != null && aval.getEntidadAval().size() > 0) {
            for (Iterator<EntidadArticulo> iterador = aval.getEntidadAval().iterator(); iterador.hasNext();) {
                listaEntidadesAval.add(iterador.next());
            }
        }
        return listaEntidadesAval;
    }

    public void cargarCategorias() {
        String consulta = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo like 'ACTIVIDAD_AVAL' order by dd.descripcion";
        List<DominioDetalle> lista = servicioGeneral.obtenerObjetos(DominioDetalle.class, consulta);

        if (!esListaVacia(lista)) {
            categoriaItems = new SelectItem[lista.size()];
            for (int i = 0; i < lista.size(); i++) {
                DominioDetalle dominio = (DominioDetalle) lista.get(i);
                categoriaItems[i] = new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion());
                categoria = "PI";
            }
        } else {
            categoriaItems = new SelectItem[1];
            categoriaItems[0] = new SelectItem("0", " - ");
        }
    }

    public void cargarObjetivoSocioEconomico() {

        String consulta = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.id = 28 order by dd.descripcion";
        List<DominioDetalle> lista = servicioGeneral.obtenerObjetos(DominioDetalle.class, consulta);

        if (!esListaVacia(lista)) {
            for (int i = 0; i < lista.size(); i++) {
                DominioDetalle dominio = (DominioDetalle) lista.get(i);
                listaObjSocioeconomico
                        .add(new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion()));
            }
        } else {
            listaObjSocioeconomico.add(new SelectItem("0", " - "));
        }

    }

    public void cargarAreaCiencia() {

        String consulta = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.id = 27 order by dd.descripcion";
        List<DominioDetalle> lista = servicioGeneral.obtenerObjetos(DominioDetalle.class, consulta);

        if (!esListaVacia(lista)) {
            for (int i = 0; i < lista.size(); i++) {
                DominioDetalle dominio = (DominioDetalle) lista.get(i);
                listaAreaCiencia.add(new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion()));
            }
        } else {
            listaAreaCiencia.add(new SelectItem("0", " - "));
        }

    }

    public void descargarAval() throws IOException, SQLException {
        descargarArchivoDocumentoAvalGenerico(aval);
    }

    public void cambiarTipo() {

        if (categoria == null) { // Valor por defecto
            categoria = "PI";
        }

        if ("C".equals(categoria) || "PI".equals(categoria) || "PIN".equals(categoria) || "JI".equals(categoria)) {
            setVerConvocatorias(true);
            setVerSistRegalias(false);
            setVerMovilidadEvento(false);
        }

        if ("SR".equals(categoria)) {
            setVerConvocatorias(false);
            setVerSistRegalias(true);
            setVerMovilidadEvento(false);
        }

        if ("ME".equals(categoria)) {
            setVerConvocatorias(false);
            setVerSistRegalias(false);
            setVerMovilidadEvento(true);
        }
    }

    public String volver() {

        sesion.removeAttribute("ManejadorAvalMenu");
        sesion.removeAttribute("manejadorSolicitudAval");

        return "successProyectosAval";
    }

    public List<CoinvestigadorAval> getListaCoinvestigadores() {

        listaCoinvestigadores = new ArrayList<CoinvestigadorAval>();
        if (aval != null && aval.getCoinvestigador().size() > 0) {
            for (Iterator<CoinvestigadorAval> iterador = aval.getCoinvestigador().iterator(); iterador.hasNext();) {
                listaCoinvestigadores.add(iterador.next());
            }
        }
        return listaCoinvestigadores;
    }
    

    public List<PalabraClave> getListaPalabrasClave() {
        List<PalabraClave> listaPalabrasClaveActual = new ArrayList<PalabraClave>();
        if (aval != null && aval.getPalabrasClaves().size() > 0) {
            for (Iterator<PalabraClave> iterador = aval.getPalabrasClaves().iterator(); iterador.hasNext();) {
                listaPalabrasClaveActual.add(iterador.next());
            }
        }
        return listaPalabrasClaveActual;
    }

    public ArchivoAval getDocumentoSeleccionado() {
        return documentoSeleccionado;
    }

    public void setDocumentoSeleccionado(ArchivoAval documentoSeleccionado) {
        this.documentoSeleccionado = documentoSeleccionado;
    }

    public ArchivoAval getArchivoResumen() {
        return archivoResumen;
    }

    public void setArchivoResumen(ArchivoAval archivoResumen) {
        this.archivoResumen = archivoResumen;
    }

    public List getListaEntidadesParticipantesSR() {
        return listaEntidadesParticipantesSR;
    }

    public void setListaEntidadesParticipantesSR(List listaEntidadesParticipantesSR) {
        this.listaEntidadesParticipantesSR = listaEntidadesParticipantesSR;
    }

    public boolean isVerConvocatorias() {
        return verConvocatorias;
    }

    public void setVerConvocatorias(boolean verConvocatorias) {
        this.verConvocatorias = verConvocatorias;
    }

    public boolean isVerSistRegalias() {
        return verSistRegalias;
    }

    public void setVerSistRegalias(boolean verSistRegalias) {
        this.verSistRegalias = verSistRegalias;
    }

    public boolean isVerMovilidadEvento() {
        return verMovilidadEvento;
    }

    public void setVerMovilidadEvento(boolean verMovilidadEvento) {
        this.verMovilidadEvento = verMovilidadEvento;
    }

    public List<SelectItem> getListaObjSocioeconomico() {
        return listaObjSocioeconomico;
    }

    public List<SelectItem> getListaAreaCiencia() {
        return listaAreaCiencia;
    }

    public List getListaDependencia() {
        return listaDependencia;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public List<EntidadArticulo> getListaEntidades() {
        return listaEntidades;
    }

    public void setListaEntidades(List<EntidadArticulo> listaEntidades) {
        this.listaEntidades = listaEntidades;
    }

    public void setListaCoinvestigadores(List<CoinvestigadorAval> listaCoinvestigadores) {
        this.listaCoinvestigadores = listaCoinvestigadores;
    }

}
