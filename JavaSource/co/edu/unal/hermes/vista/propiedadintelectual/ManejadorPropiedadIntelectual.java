package co.edu.unal.hermes.vista.propiedadintelectual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.AmbitoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.ArchivoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.CaracterPropiedadIntelectual;
import co.edu.unal.hermes.modelo.CategoriaInvestigador;
import co.edu.unal.hermes.modelo.ClasePropiedadIntelectual;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.Dominio;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EstadoGrupo;
import co.edu.unal.hermes.modelo.EstadoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Estudiante;
import co.edu.unal.hermes.modelo.FormatoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.IdDominioDetalle;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.ObraFonogramaPropiedadIntelectual;
import co.edu.unal.hermes.modelo.Pais;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PersonaPropiedadIntelectual;
import co.edu.unal.hermes.modelo.PropiedadIntelectual;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.ProyectoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.ResultadoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.Rol;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.SubEstadoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.SubTipoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.TipoArchivo;
import co.edu.unal.hermes.modelo.TipoEdicionPropiedadIntelectual;
import co.edu.unal.hermes.modelo.TipoPersonaPropiedadIntelectual;
import co.edu.unal.hermes.modelo.TipoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.utils.VariablesEstaticas;

public class ManejadorPropiedadIntelectual extends ManejadorBasePropiedadIntelectual {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    org.primefaces.model.UploadedFile archivoCargado;

    private SelectItem[] tiposPropiedadIntelectualItem;
    private SelectItem[] sedesItem;
    private List<SelectItem> dependenciasItem;
    private String nivelSolicitante;
    private SelectItem[] proyectosItem;
    private SelectItem[] gruposItem;
    private String tipoPersona; // temporal
    private String calidad; // temporal
    private SelectItem[] listaCaracterItem;
    private String[] selectedCaracter;
    private SelectItem[] listaAmbitoItem;
    private String[] selectedAmbito;
    private SelectItem[] estadoObraItem;
    private SelectItem[] listaEdicionItem;
    private String[] selectedEdicion;
    private SelectItem[] listaClaseItem;
    private String[] selectedClase;
    private SelectItem[] listaFormatoItem;
    private String[] selectedFormato;
    private String otroCaracter;
    private String otroAmbito;
    private String otroTipoEdicion;
    private String otraClase;
    private String otroFormato;
    private SelectItem[] tiposPersonaItem;
    private Investigador investigadorAgregar;
    private PersonaPropiedadIntelectual personaEliminar;
    private String descripcionArchivo;
    private boolean datosExterno;
    private String entidadPersonaExterna;
    private SelectItem[] generoItem = { new SelectItem(VariablesEstaticas.GENERO_FEMENINO, "Femenino"),
            new SelectItem(VariablesEstaticas.GENERO_MASCULINO, "Masculino") };
    private String obraFonograma; // Utilizado para agregar las obras
    private ObraFonogramaPropiedadIntelectual obraFonogramaEliminar;
    private SelectItem[] obrasFijadasItem;
    private String obraFijada; // utilizado para asignar una obra a un autor
    private SelectItem[] solicitudesISBNItem;
    private String paisPersona;
    private String ciudadPersona;
    private String sitioWeb;
    private SelectItem[] listaResultadoItem;
    private String resultado;
    private String descripcionResultado;
    private ResultadoPropiedadIntelectual resultadoSeleccionado;
    private String tipoProyecto;
    private String proyectoAgregar;
    private String grupoAgregar;
    private ProyectoPropiedadIntelectual proyectoSeleccionado;
    private Grupo grupoSeleccionado;
    private String codigoProyectoExtension;
    private String nombreProyectoExtension;

    public ManejadorPropiedadIntelectual() {

        datosExterno = false;

        cargarCaracterPropiedadIntelectual();
        cargarAmbitoPropiedadIntelectual();
        cargarTiposEdicion();
        cargarClasePropiedadIntelectual();
        cargarMediosPublicacionPropiedadIntelectual();
        cargarResultadosPropiedadIntelectual();

        if (!isEsNueva() && sesion.getAttribute(ID_PROPIEDAD_INTELECTUAL) != null) {
            Long id = (Long) sesion.getAttribute(ID_PROPIEDAD_INTELECTUAL);
            propiedad = servicioPropiedadIntelectual.obtenerRegistroPropiedadIntelectual(id);

            asignarValoresListas();

        } else {
            propiedad = new PropiedadIntelectual();
        }

        cargarListaNaturalezaProyecto();
        cargarEstadosObra();
        cargarPaises();
        cargarCiudades();

        if (propiedad.getDependenciaSolicitante() == null) {
            Dependencia dependencia = new Dependencia();
            Sede sede = new Sede();
            dependencia.setSede(sede);
            propiedad.setDependenciaSolicitante(dependencia);
        }

        cargarNivelesSedes();

        if (propiedad.getPais() == null) {
            Pais pais = new Pais();
            propiedad.setPais(pais);
        }

        if (propiedad.getSubTipo() == null) {
            SubTipoPropiedadIntelectual subtipo = new SubTipoPropiedadIntelectual();
            TipoPropiedadIntelectual tipo = new TipoPropiedadIntelectual();
            subtipo.setTipo(tipo);
            propiedad.setSubTipo(subtipo);
        }

        if (propiedad.getGenero() == null) {
            Tipos genero = new Tipos();
            genero.setId(0L);
            propiedad.setGenero(genero);
        }

        tiposPropiedadIntelectual();
        cargarGenerosMusicales();
        cargarGenerosCinematograficos();
    }

    public void asignarValoresListas() {
        consultarGrupos();

        if (!esListaVacia(propiedad.getListaCaracter())) {
            for (int i = 0; i < propiedad.getListaCaracter().size(); i++) {
                CaracterPropiedadIntelectual caract = (CaracterPropiedadIntelectual) propiedad.getListaCaracter()
                        .get(i);
                selectedCaracter[i] = caract.getCaracter().getIdentificador().getTipo();
                if (caract.getCaracter().getIdentificador().getTipo().equals(DominioDetalle.OTRO_CARACTER_OBRA)) {
                    otroCaracter = caract.getObservaciones();
                }
            }
        }
        if (!esListaVacia(propiedad.getListaAmbito())) {
            for (int i = 0; i < propiedad.getListaAmbito().size(); i++) {
                AmbitoPropiedadIntelectual ambito = (AmbitoPropiedadIntelectual) propiedad.getListaAmbito().get(i);
                selectedAmbito[i] = ambito.getAmbito().getIdentificador().getTipo();
                if (ambito.getAmbito().getIdentificador().getTipo().equals(DominioDetalle.OTRO_AMBITO_OBRA)) {
                    otroAmbito = ambito.getObservaciones();
                }
            }
        }
        if (!esListaVacia(propiedad.getListaTiposEdicion())) {
            for (int i = 0; i < propiedad.getListaTiposEdicion().size(); i++) {
                TipoEdicionPropiedadIntelectual tipoEdicion = (TipoEdicionPropiedadIntelectual) propiedad
                        .getListaTiposEdicion().get(i);
                selectedEdicion[i] = tipoEdicion.getTipo().getIdentificador().getTipo();
                if (tipoEdicion.getTipo().getIdentificador().getTipo().equals(DominioDetalle.OTRO_TIPO_EDICION_OBRA)) {
                    otroTipoEdicion = tipoEdicion.getObservaciones();
                }
            }
        }
        if (!esListaVacia(propiedad.getListaClase())) {
            for (int i = 0; i < propiedad.getListaClase().size(); i++) {
                ClasePropiedadIntelectual clase = (ClasePropiedadIntelectual) propiedad.getListaClase().get(i);
                selectedClase[i] = clase.getClase().getIdentificador().getTipo();
                if (clase.getClase().getIdentificador().getTipo().equals(DominioDetalle.OTRO_CLASE_OBRA)) {
                    otraClase = clase.getObservaciones();
                }
            }
        }
        if (!esListaVacia(propiedad.getListaFormato())) {
            for (int i = 0; i < propiedad.getListaFormato().size(); i++) {
                FormatoPropiedadIntelectual medio = (FormatoPropiedadIntelectual) propiedad.getListaFormato().get(i);
                selectedFormato[i] = medio.getMedio().getIdentificador().getTipo();
                if (medio.getMedio().getIdentificador().getTipo().equals(DominioDetalle.OTRO_FORMATO_OBRA)) {
                    otroFormato = medio.getObservaciones();
                }
            }
        }
        if (propiedad.isEsFonograma()) {
            cargarObrasFijadas();
        }
        if (propiedad.isEsObraLiteraria() && propiedad.getDependenciaSolicitante()!=null && propiedad.getDependenciaSolicitante().isEsEditorial()) {
            consultarOpcionesAdicionales();
        }
    }

    /**
     * Lista los tipos de propiedad intelectual
     */
    public void tiposPropiedadIntelectual() {
        limpiarCamposPersonas();
        List<TipoPropiedadIntelectual> lista = servicioPropiedadIntelectual.obtenerTiposPropiedadIntelectual("A");

        if (!esListaVacia(lista)) {
            tiposPropiedadIntelectualItem = new SelectItem[lista.size()];
            for (int i = 0; i < lista.size(); i++) {
                TipoPropiedadIntelectual tipo = lista.get(i);
                tiposPropiedadIntelectualItem[i] = new SelectItem(tipo.getId(), tipo.getNombre());
            }
            subTiposPropiedadIntelectual();
            if (propiedad.getSubTipo() != null && propiedad.getSubTipo().getTipo().getId() != null) {
                cargarTiposPersona();
            }
        }
    }

    /**
     * Lista los tipos de persona de acuerdo al subtipo de propiedad intelectual
     * que se registrará
     */
    public void cargarTiposPersona() {
        tiposPersonaItem = new SelectItem[0];
        List<TipoPersonaPropiedadIntelectual> lista;
        if (propiedad.isEsDerechosAutor()) {
            lista = servicioPropiedadIntelectual
                    .obtenerTiposPersonaPropiedadIntelectual(propiedad.getSubTipo().getId().toString());
        } else {
            lista = servicioPropiedadIntelectual
                    .obtenerTiposPersonaPropiedadIntelectual(propiedad.getSubTipo().getTipo());
        }
        if (!esListaVacia(lista)) {
            tiposPersonaItem = new SelectItem[lista.size()];
            for (int i = 0; i < lista.size(); i++) {
                TipoPersonaPropiedadIntelectual tipo = lista.get(i);
                tiposPersonaItem[i] = new SelectItem(tipo.getId(), tipo.getNombre());
            }
        }
    }

    /**
     * Lista de niveles, sedes de la universidad nacional
     */
    public void cargarNivelesSedes() {

        List<Dependencia> lista = servicioGeneral.obtenerSedes();

        if (!esListaVacia(lista)) {
            sedesItem = new SelectItem[lista.size()];
            for (int i = 0; i < lista.size(); i++) {
                Dependencia sede = lista.get(i);
                sedesItem[i] = new SelectItem(sede.getId(), sede.getNombre());
            }
        }
        // Revisar si es adecuado o se puede hacer mas optimo en la consulta
        if (propiedad.getDependenciaSolicitante() != null
                && propiedad.getDependenciaSolicitante().getSede().getId() != null
                && propiedad.getDependenciaSolicitante().getSede().getId() != 0L) {
            cargarDependencias();
        }
    }

    /**
     * Lista de dependencias activas de la universidad nacional
     */
    public void cargarDependencias() {
        List<Dependencia> lista = servicioDependencia
                .obtenerDependenciaXSede(propiedad.getDependenciaSolicitante().getSede().getId());
        dependenciasItem = servicioDependencia.crearSelectItem(lista);
    }

    public void consultarOpcionesAdicionales() {
        solicitudesISBNItem = new SelectItem[0];
        if (propiedad.getDependenciaSolicitante().isEsEditorial() && propiedad.isEsObraLiteraria()) {
            List<Proyecto> lista = servicioGeneral.obtenerObjetosLimitado(Proyecto.class,
                    "select #id p.id, #nombre p.nombre from Proyecto p where " + "p.estadoProyecto.id " + "in ('"
                            + EstadoProyecto.APROBADO + "','" + EstadoProyecto.ACTIVO + "','"
                            + EstadoProyecto.FINALIZADO + "') and p.modalidad.id in (" + MODALIDAD_ISBN
                            + ") and p.id not in (select pi.solicitudIsbn from PropiedadIntelectual pi where pi.solicitudIsbn is not null)");
            if (!esListaVacia(lista)) {
                solicitudesISBNItem = new SelectItem[lista.size()];
                for (int i = 0; i < lista.size(); i++) {
                    Proyecto proyecto = lista.get(i);
                    solicitudesISBNItem[i] = new SelectItem(proyecto.getId(),
                            proyecto.getId() + " - " + proyecto.getNombre());
                }
            }
        } else {
            propiedad.setSolicitudIsbn(null);
        }
    }

    public void consultarSolicitudIsbn() {
        if (propiedad.getSolicitudIsbn() != null && !propiedad.getSolicitudIsbn().equals(0L)) {
            List<Proyecto> solicitud = servicioGeneral.obtenerObjetoXID(Proyecto.class,
                    propiedad.getSolicitudIsbn().toString());
            if (!esListaVacia(solicitud)) {
                Proyecto solIsbn = solicitud.get(0);
                propiedad.setEstadoObra(PropiedadIntelectual.OBRA_EDITADA);
                propiedad.setTitulo(solIsbn.getNombre());
                propiedad.setNumeroEdicion(solIsbn.getNumeroEdicionISBN().toString());
                propiedad.setNumeroPaginas(solIsbn.getNumPaginasISBN());
                propiedad.setFechaPublicacion(solIsbn.getFechaAparicionISBN());
                if (!esListaVacia(propiedad.getListaPersonas())) {
                    propiedad.borrarListaPersonas(propiedad.getListaPersonas());
                }
                if (!esListaVacia(solIsbn.getListaInvestigadoresProyecto())) {
                    copiarAutoresCoautores(solIsbn.getListaInvestigadoresProyecto());
                }
            }
        }
    }

    private void copiarAutoresCoautores(List lista) {
        for (int i = 0; i < lista.size(); i++) {
            InvestigadorProyecto persona = (InvestigadorProyecto) lista.get(i);
            if (persona.getTipo().isEsAutorIsbn() || persona.getTipo().isEsCoautorIsbn()) {
                PersonaPropiedadIntelectual personaPI = new PersonaPropiedadIntelectual();
                personaPI.setPersona(persona.getInvestigador());
                personaPI.setPropiedad(propiedad);
                
                Dependencia dep = persona.getInvestigador().getDependencia();
                
                if(!dep.getNombre().equals(""))
                	personaPI.setDependencia(dep);
                else
                	personaPI.setDependencia(null);

                InvestigadorInterno ii = servicioPersona.obtenerInvestigadorInterno(persona.getInvestigador().getId());
                if (ii != null && ii.getInterno().equals(InvestigadorInterno.INTERNO)) {
                    personaPI.setCalidad(PersonaPropiedadIntelectual.DOCENTE.toString());
                } else if (ii != null && ii.getEsFuncionario().equals(InvestigadorInterno.INTERNO)) {
                    personaPI.setCalidad(PersonaPropiedadIntelectual.ADMINISTRATIVO.toString());
                } else {
                    personaPI.setCalidad(PersonaPropiedadIntelectual.EXTERNO.toString());
                    personaPI.setEntidad(obtenerEntidadExterna("0"));
                }

                if (persona.getTipo().isEsAutorIsbn()) {
                    personaPI.setTipoPersona(servicioPropiedadIntelectual.obtenerTipoPersonaPropiedadIntelectual(
                            TipoPersonaPropiedadIntelectual.AUTOR_OBRA_LITERARIA.toString()));
                } else {
                    personaPI.setTipoPersona(servicioPropiedadIntelectual.obtenerTipoPersonaPropiedadIntelectual(
                            TipoPersonaPropiedadIntelectual.COAUTOR_OBRA_LITERARIA.toString()));
                }
                propiedad.adicionarPersona(personaPI);
            }
        }
    }

    public String cargarCadenaParticipantesPropiedad() {
        Iterator<PersonaPropiedadIntelectual> i = propiedad.getListaPersonas().iterator();

        // Se crea una cadena con los id de las personas
        String personas = "";
        while (i.hasNext()) {
            PersonaPropiedadIntelectual ppi = i.next();
            if (ppi != null && ppi.getPersona() != null) {
                // se agrega el id de la persona a la cadena.
                if (personas.length() > 0) {
                    personas += ",";
                }
                personas += "'" + ppi.getPersona().getId().getDocumento() + "'";
            }
        }
        return personas;
    }

    public void consultarProyectos() {
        proyectosItem = new SelectItem[0];
        String documentos = cargarCadenaParticipantesPropiedad();
        
        documentos += ",'" + personaActual.getId().getDocumento() + "'";

        if (esCadenaVacia(documentos)) {
            mensajeError("No se han encontrado autores/inventores agregados a la propiedad intelectual");
            return;
        }

        if (tipoProyecto.equals(PropiedadIntelectual.TIPO_PROY_INV_LAB.toString())) {
            List<Proyecto> lista = servicioGeneral.obtenerObjetosLimitado(Proyecto.class,
                    "select #id p.id, #nombre p.nombre "
                    + "from Proyecto p, InvestigadorProyecto ip where "
                    + "ip.investigador.id.documento in (" + documentos + ") " 
                    + "and ip.proyecto.id=p.id "
                    + "and (p.estadoProyecto.id in ('" + EstadoProyecto.ACTIVO + "','"+ EstadoProyecto.FINALIZADO + "')) "
                    + "and p.modalidad.id not in ("+ MODALIDADES_PERMISO_CONTRATO + ")");
            if (!esListaVacia(lista)) {
                proyectosItem = new SelectItem[lista.size()];
                for (int i = 0; i < lista.size(); i++) {
                    Proyecto proyecto = lista.get(i);
                    proyectosItem[i] = new SelectItem(proyecto.getId(),
                            proyecto.getId() + " - " + proyecto.getNombre());
                }
            }
        } else {
            /*
             * Relacionar con vista de proyectos de extension
             */
        }
    }

    public void consultarGrupos() {
        gruposItem = new SelectItem[0];
        String documentos = cargarCadenaParticipantesPropiedad();

        if (esCadenaVacia(documentos)) {
            return;
        }
        List<Grupo> lista = servicioGeneral.obtenerObjetosLimitado(Grupo.class,
                "select distinct #id g.id, #nombre g.nombre from Grupo g, InvestigadorGrupo ig where "
                        + "g.id = ig.grupo.id and g.estadoGrupo.id in ('" + EstadoGrupo.ACTIVO
                        + "') and ig.investigador.id.documento in (" + documentos + ")");
        if (!esListaVacia(lista)) {
            gruposItem = new SelectItem[lista.size()];
            for (int i = 0; i < lista.size(); i++) {
                Grupo grupo = lista.get(i);
                gruposItem[i] = new SelectItem(grupo.getId(), grupo.getNombre());
            }
        }
    }

    public void agregarResultado() {

        if (esCadenaVacia(resultado)) {
            mensajeError("Debe seleccionar un tipo de resultado para agregarlo.");
            return;
        }
        if (esCadenaVacia(descripcionResultado)) {
            mensajeError("Debe ingresar una breve descripción del tipo de resultado.");
            return;
        }

        ResultadoPropiedadIntelectual resultadoAgregar = new ResultadoPropiedadIntelectual();
        resultadoAgregar.setPropiedad(propiedad);
        resultadoAgregar.setDescripcion(controlTamanoCadena(descripcionResultado, 2000));
        resultadoAgregar
                .setResultado(servicioGeneral.obtenerDominioDetalleUnico(Dominio.TIPO_RESULTADO_PROP_INT, resultado));
        propiedad.adicionarResultado(resultadoAgregar);
        resultado = "";
        descripcionResultado = "";
    }

    /**
     * Eliminar resultado
     */
    public void eliminarResultado() {
        if (resultadoSeleccionado != null) {
            propiedad.borrarResultado(resultadoSeleccionado);
        }
    }

    public void agregarProyecto() {

        if (!validarProyectoAgregar()) {
            return;
        }
        ProyectoPropiedadIntelectual proyectoAdicionar = new ProyectoPropiedadIntelectual();
        proyectoAdicionar.setPropiedad(propiedad);
        proyectoAdicionar.setTipoProyecto(tipoProyecto);

        if (tipoProyecto.equals(PropiedadIntelectual.TIPO_PROY_INV_LAB.toString())) {
            proyectoAdicionar.setProyecto(servicioProyecto.obtenerProyecto(Long.parseLong(proyectoAgregar),
                    ProyectoDAOHibernate.LIMPIO_SIN_CIUDAD));
            proyectoAdicionar.setNombreProyecto(proyectoAdicionar.getProyecto().getNombre());
        } else {
            proyectoAdicionar.setIdProyectoExtension(codigoProyectoExtension.trim());
            proyectoAdicionar.setNombreProyectoExtension(nombreProyectoExtension.trim());
        }
        propiedad.adicionarProyecto(proyectoAdicionar);
        tipoProyecto = "";
        proyectoAgregar = "";
        codigoProyectoExtension = "";
        nombreProyectoExtension = "";
    }

    private boolean validarProyectoAgregar() {
        boolean valida = true;
        if (esCadenaVacia(tipoProyecto)) {
            mensajeError("Debe seleccionar un tipo de proyecto para que se listen las opciones.");
            valida = false;
        }
        if (!esCadenaVacia(tipoProyecto) && tipoProyecto.equals(PropiedadIntelectual.TIPO_PROY_INV_LAB.toString()) && esCadenaVacia(proyectoAgregar)) {
            mensajeError("Debe seleccionar un proyecto para que sea agregado.");
            valida = false;
        }
        
        if (!esCadenaVacia(tipoProyecto) && tipoProyecto.equals(PropiedadIntelectual.TIPO_PROY_EXT.toString()) && (esCadenaVacia(codigoProyectoExtension.trim()) || esCadenaVacia(nombreProyectoExtension.trim()))) {
            mensajeError("Debe ingresar el código y nombre del proyecto de extensión relacionado con la propiedad intelectual.");
            valida = false;
        }

        if (!esListaVacia(propiedad.getListaProyectos()) && !esCadenaVacia(tipoProyecto)
                && !esCadenaVacia(proyectoAgregar)) {
            for (int i = 0; i < propiedad.getListaProyectos().size(); i++) {
                ProyectoPropiedadIntelectual proyectoComparar = propiedad.getListaProyectos().get(i);
                if (tipoProyecto.equals(PropiedadIntelectual.TIPO_PROY_INV_LAB.toString()) 
                        && proyectoComparar.getProyecto().getId().equals(Long.parseLong(proyectoAgregar))) {
                    valida = false;
                    mensajeError("El proyecto ya se encuentra en el listado.");
                    break;
                }else if (tipoProyecto.equals(PropiedadIntelectual.TIPO_PROY_EXT.toString()) 
                        && proyectoComparar.getIdProyectoExtension().equals(codigoProyectoExtension.trim())) {
                    valida = false;
                    mensajeError("El proyecto ya se encuentra en el listado.");
                    break;
                }
            }
        }
        return valida;
    }

    public void eliminarProyecto() {
        if (proyectoSeleccionado != null) {
            propiedad.borrarProyecto(proyectoSeleccionado);
        }
    }

    public void agregarGrupo() {

        if (!validarGrupo()) {
            return;
        }
        propiedad.adicionarGrupo(servicioGrupo.obtenerGrupoDatosBasicos(Long.parseLong(grupoAgregar)));
        grupoAgregar = "";
    }
    
    private boolean validarGrupo(){
        boolean valida = true;
        if (esCadenaVacia(grupoAgregar)) {
            mensajeError("El grupo a relacionar ya se encuentra en el listado.");
            valida = false;
        }
        
        if (!esListaVacia(propiedad.getListaGrupos()) && !esCadenaVacia(grupoAgregar)) {
            for (int i = 0; i < propiedad.getListaGrupos().size(); i++) {
                Grupo grupoComparar = propiedad.getListaGrupos().get(i);
                if (grupoComparar.getId().toString().equals(grupoAgregar)) {
                    valida = false;
                    mensajeError("El grupo ya se encuentra en el listado.");
                    break;
                }
            }
        }
        
        return valida;
    }

    public void eliminarGrupo() {
        if (grupoSeleccionado != null) {
            propiedad.borrarGrupo(grupoSeleccionado);
        }
    }

    public void eliminarPersonaPropiedad() {
        if (personaEliminar != null) {
            propiedad.borrarPersona(personaEliminar);
//          Req 2433
//            if (personaEliminar.getTipoPersona().isEsAutorObraFijada()
//                    && personaEliminar.getObraFijadaFonograma() != null) {
//                ObraFonogramaPropiedadIntelectual obra = personaEliminar.getObraFijadaFonograma();
//                obra.borrarAutor(personaEliminar);
//            }
        }
    }

    public void agregarObraFonograma() {

        if (esCadenaVacia(obraFonograma)) {
            mensajeError("Debe ingresar el nombre de la obra para agregarla.");
            return;
        } else {
            obraFonograma = controlTamanoCadena(obraFonograma, 3000);
        }

        boolean obraYaesta = false;

        for (int i = 0; i < propiedad.getListaObrasFonograma().size(); i++) {
            ObraFonogramaPropiedadIntelectual obraAgregada = (ObraFonogramaPropiedadIntelectual) propiedad
                    .getListaObrasFonograma().get(i);
            if (obraAgregada.getNombreObra().equalsIgnoreCase(obraFonograma.trim())) {
                obraYaesta = true;
                break;
            }
        }

        if (obraYaesta) {
            mensajeError("Ya ha sido agregada una obra con el mismo nombre.");
            return;
        } else {
            ObraFonogramaPropiedadIntelectual nuevaObraFonograma = new ObraFonogramaPropiedadIntelectual();
            nuevaObraFonograma.setPropiedad(propiedad);
            nuevaObraFonograma.setNombreObra(controlTamanoCadena(obraFonograma, 2000));
            servicioGeneral.guardarObjeto(nuevaObraFonograma);
            propiedad.adicionarObraFonograma(nuevaObraFonograma);
            obraFonograma = "";
            cargarObrasFijadas();
        }
    }

    public void eliminarObraFonograma() {
        if (obraFonogramaEliminar != null) {
            if (!esListaVacia(obraFonogramaEliminar.getListaAutores())) {
                mensajeError("No es posible eliminar la obra, pues hay autores relacionados en la tabla 'AUTORES'");
            } else {
                propiedad.borrarObraFonograma(obraFonogramaEliminar);
                cargarObrasFijadas();
            }
        }
    }

    public void cargarObrasFijadas() {
        obrasFijadasItem = new SelectItem[0];
        if (!esListaVacia(propiedad.getListaObrasFonograma())) {
            obrasFijadasItem = new SelectItem[propiedad.getListaObrasFonograma().size()];
            for (int i = 0; i < propiedad.getListaObrasFonograma().size(); i++) {
                ObraFonogramaPropiedadIntelectual obra = propiedad.getListaObrasFonograma().get(i);
                obrasFijadasItem[i] = new SelectItem(obra.getId(), obra.getNombreObra());
            }
        }
    }

    public void agregarPersonaPropiedad() {
        datosExterno = false;
        documento = documento.trim();
        boolean ingresar = validarPersona();
        if (ingresar) {
            IdPersona id = new IdPersona();
            id.setTipoDocumento(tipoDocumento);
            id.setDocumento(documento);
            
//            if(servicioPersona.esInvestigadorInterno(id)){
//            	//Pendiente
//            }
            
            if (!personaRepetida(id, tipoPersona)) {
                investigadorAgregar = servicioPersona.obtenerInvestigador(id);
//                investigadorAgregar = servicioPersona.buscarInvestigadorParaAsociarAProyecto(id);
                if (validarVinculacion(investigadorAgregar)) {
                    PersonaPropiedadIntelectual persona = crearPersonaPropiedadIntelectual();
                    propiedad.adicionarPersona(persona);
                    consultarGrupos();
                    limpiarCamposPersonas();
                }
            } else {
                mensajeError("La persona ya se encuentra en la lista con el tipo de participación seleccionado.");
            }
        }
        return;
    }

    private PersonaPropiedadIntelectual crearPersonaPropiedadIntelectual() {
        PersonaPropiedadIntelectual persona = new PersonaPropiedadIntelectual();
//      Req 2433
//        persona.setTipoPersona(servicioPropiedadIntelectual.obtenerTipoPersonaPropiedadIntelectual(tipoPersona));
        persona.setPersona(investigadorAgregar);
        persona.setCalidad(calidad);
        persona.setPropiedad(propiedad);
        persona.setDependencia(investigadorAgregar.getDependencia());
        persona.setPlan(investigadorAgregar.getPlan());
        if (persona.isEsExterno()) {
            persona.setEntidad(obtenerEntidadExterna(entidadPersonaExterna));
        }
//        Req 2433
//        if (persona.getTipoPersona().isEsAutorObraFijada() || persona.getTipoPersona().isEsInterprete()) {
//
//            persona = asignarPersonaObra(persona);
//        }
        
        if (propiedad.isEsObraAudioVisual()) {
            persona.setCiudad(controlTamanoCadena(ciudadPersona, 1000));
            persona.setPais(new Pais(paisPersona));
            persona.setSitioWeb(controlTamanoCadena(sitioWeb, 2000));
        }
        return persona;
    }

    public PersonaPropiedadIntelectual asignarPersonaObra(PersonaPropiedadIntelectual autor) {
        if (!esListaVacia(propiedad.getListaObrasFonograma())) {
            for (int i = 0; i < propiedad.getListaObrasFonograma().size(); i++) {
                ObraFonogramaPropiedadIntelectual obra = propiedad.getListaObrasFonograma().get(i);
                if (obra.getId().equals(Long.parseLong(obraFijada))) {
                    autor.setObraFijadaFonograma(obra);
                    propiedad.getListaObrasFonograma().get(i).adicionarAutor(autor);
                }
            }
        }
        return autor;
    }

    private void limpiarCamposPersonas() {
        tipoPersona = "";
        calidad = "";
        tipoDocumento = "";
        documento = "";
        entidadPersonaExterna = "";
        sitioWeb = "";
        paisPersona = "";
        ciudadPersona = "";
        obraFijada = "";
    }

    private boolean validarPersona() {
        boolean completo = true;
//        if (esCadenaVacia(tipoPersona)) {
//            completo = false;
//            mensajeError("Debe seleccionar un tipo de personal");
//        } else if ((tipoPersona.equals(TipoPersonaPropiedadIntelectual.AUTOR_OBRA_FIJADA_FONOGRAMA.toString())
//                || tipoPersona.equals(TipoPersonaPropiedadIntelectual.INTERPRETE_OBRA_FIJADA_FONOGRAMA.toString()))
//                && esCadenaVacia(obraFijada)) {
//            completo = false;
//            mensajeError(
//                    "Debe seleccionar una de las obras fijadas en el fonograda, si no ha agregado las obras, hágalo en la sección anterior ('OBRAS FIJADAS EN EL FONOGRAMA').");
//        }
        if (esCadenaVacia(calidad)) {
            completo = false;
            mensajeError("Debe indicar el rol de la persona con respecto a la Universidad Nacional");
        } else if (calidad.equals(PersonaPropiedadIntelectual.EXTERNO.toString())
                && esCadenaVacia(entidadPersonaExterna)) {
            completo = false;
            mensajeError("Debe seleccionar la entidad a la que pertenece la persona");
        }
        if (esCadenaVacia(tipoDocumento)) {
            completo = false;
            mensajeError("Debe indicar el tipo de documento de la persona a agregar");
        }
        if (esCadenaVacia(documento)) {
            completo = false;
            mensajeError("Debe indicar el documento de la persona a agregar");
        }
        return completo;
    }

    private boolean personaRepetida(IdPersona id, String tipoPersona) {
        boolean existe = false;
        if (esListaVacia(propiedad.getListaPersonas())) {
            return existe;
        } else {
            for (int i = 0; i < propiedad.getListaPersonas().size(); i++) {
                IdPersona idPersona = propiedad.getListaPersonas().get(i).getPersona().getId();
                if(idPersona!=null && idPersona.getTipoDocumento()!=null && id!=null && id.getTipoDocumento()!=null) {
                if (((idPersona.getTipoDocumento().equals(id.getTipoDocumento()))
                        && (idPersona.getDocumento().equals(id.getDocumento())))
                        && propiedad.getListaPersonas().get(i).getTipoPersona().getId().toString()
                                .equals(tipoPersona)) {
                    existe = true;
                    break;
                }
                }
            }
        }
        return existe;
    }

    private boolean validarVinculacion(Investigador inv) {
        if (inv != null && !PersonaPropiedadIntelectual.ESTUDIANTE.toString().equals(calidad)
                && !PersonaPropiedadIntelectual.EXTERNO.toString().equals(calidad)) {
            if (PersonaPropiedadIntelectual.DOCENTE.toString().equals(calidad) && !verificarInvestigadorInterno(inv)) {
                mensajeError(
                        "El(La) docente no fue encontrado, debe encontrarse activo(a) en la Universidad Nacional.");
                return false;
            } else if (PersonaPropiedadIntelectual.ADMINISTRATIVO.toString().equals(calidad)
                    && !verificarFuncionarioUnal(inv)) {
                mensajeError(
                        "El(La) funcionario(a) no fue encontrado(a), debe encontrarse activo(a) en la Universidad Nacional.");
                return false;
            }
        } else {
            if (PersonaPropiedadIntelectual.ESTUDIANTE.toString().equals(calidad)
                    && !verificarEstudianteUnal(new IdPersona(documento, tipoDocumento))) {
                mensajeError("El(La) estudiante no fue encontrado(a) o no está activo.");
//                inv = investigadorAgregar;
                return false;

            } else if ((PersonaPropiedadIntelectual.EXTERNO.toString().equals(calidad)||PersonaPropiedadIntelectual.EGRESADO.toString().equals(calidad))
                    && !verificarPersona(new IdPersona(documento, tipoDocumento))) {
                mensajeError(
                        "Por favor haga clic en 'Ingresar datos' y luego vuelva a realizar el proceso para agregar la persona externa.");
                datosExterno = true;
                return false;
            }
//            else if(inv==null){
//                mensajeError(
//                        "No se encontró ninguna persona con el tipo y número de documento indicado, por favor verifique la vinculación y los datos ingresados.");
//                return false;
//            }
        }
        return true;

    }

    private boolean verificarInvestigadorInterno(Investigador inv) {
        if (inv != null && inv.getInterno() != null && Investigador.INTERNO.equals(inv.getInterno())) {
            return true;
        }
        return false;
    }

    private boolean verificarFuncionarioUnal(Investigador inv) {
        if (inv != null && inv.getEsFuncionario() != null && "S".equals(inv.getEsFuncionario())) {
            return true;
        }
        return false;
    }

    private boolean verificarEstudianteUnal(IdPersona id) {
        Estudiante estudiante = servicioPersona.obtenerEstudiante(id);
        
    	if(!esNulo(estudiante) && !esNulo(estudiante.getInterno()) && !estudiante.esInterno()) {
    		mensajeError("El estudiante ingresado no está activo para el plan de estudios: " + estudiante.getPlan().getNombre());
    		return false;
		}
    	
        if (estudiante != null) {
            if (servicioPersona.obtenerPersona(id) == null) {
                servicioPersona.insertarNuevaPersona(estudiante.convertirAPersona());
            }

            if (investigadorAgregar == null) {
                investigadorAgregar = new Investigador();
                investigadorAgregar.setEvaluador(Investigador.NO_EVALUADOR);
                investigadorAgregar.setEsFuncionario("N");
                investigadorAgregar.setInterno(Investigador.EXTERNO);
                investigadorAgregar.setId(id);
                CategoriaInvestigador categoria = new CategoriaInvestigador();
                categoria.setId(3L);
                investigadorAgregar.setCategoriaInvestigador(categoria);
                servicioPersona.insertarInvestigador(investigadorAgregar);
            }

            // Se obtiene el investigador interno
            InvestigadorInterno investigadoriInterno = servicioPersona.obtenerInvestigadorInterno(id);

            // Si no existe el interno se agrega
            if (investigadoriInterno == null) {
                InvestigadorInterno nuevoInvestigadorInterno = new InvestigadorInterno();
                nuevoInvestigadorInterno.setId(id);
                nuevoInvestigadorInterno.setDependencia(estudiante.getDependencia());
                servicioPersona.insertaInterno(nuevoInvestigadorInterno);
                InvestigadorInterno investigadorInsertado = servicioPersona.obtenerInvestigadorInterno(id);
                if (investigadorInsertado == null) {
                    mensajeError("Ha habido un error al agregar al estudiante.");
                    return false;
                } else {
                    investigadorAgregar = investigadorInsertado;
                }
            } else {
                investigadorAgregar = investigadoriInterno;
            }
            investigadorAgregar.setPlan(estudiante.getPlan());
            return true;
        }
        return false;
    }

    private boolean verificarPersona(IdPersona id) {
        Persona persona = servicioPersona.obtenerPersona(id);
        if (persona != null) {
            investigadorAgregar = (Investigador) persona;
            investigadorAgregar.setDependencia(new Dependencia("0"));
            return true;
        } else {
            getPersonaExterna().setId(id);
        }
        return false;
    }

    public void ingresarActualizarPersona() {
        if (getPersonaExterna() != null) {
            servicioPersona.insertarNuevaPersonaDatosBasicos(getPersonaExterna());
            Investigador investigadorNuevo = new Investigador();
            investigadorNuevo.setEvaluador(Investigador.NO_EVALUADOR);
            investigadorNuevo.setEsFuncionario("N");
            investigadorNuevo.setInterno(Investigador.EXTERNO);
            investigadorNuevo.setId(getPersonaExterna().getId());
            CategoriaInvestigador categoria = new CategoriaInvestigador();
            categoria.setId(3L);
            investigadorNuevo.setCategoriaInvestigador(categoria);
            servicioPersona.insertarInvestigador(investigadorNuevo);
        }
    }

    /**
     * Lista de tipos de caracter de la propiedad intelectual
     */
    public void cargarCaracterPropiedadIntelectual() {

        List<DominioDetalle> lista = servicioGeneral.obtenerDominioDetalle(TIPO_DOMINIO_CARACTER, true);

        if (!esListaVacia(lista)) {
            setSelectedCaracter(new String[lista.size()]);
            listaCaracterItem = new SelectItem[lista.size()];
            for (int i = 0; i < lista.size(); i++) {
                DominioDetalle caracter = lista.get(i);
                listaCaracterItem[i] = new SelectItem(caracter.getIdentificador().getTipo(), caracter.getDescripcion());
            }
        }
    }

    /**
     * Lista de ambitos de la propiedad intelectual
     */
    public void cargarAmbitoPropiedadIntelectual() {
        List<DominioDetalle> lista = servicioGeneral.obtenerDominioDetalle(TIPO_AMBITO_OBRA, true);

        if (!esListaVacia(lista)) {
            setSelectedAmbito(new String[lista.size()]);
            listaAmbitoItem = new SelectItem[lista.size()];
            for (int i = 0; i < lista.size(); i++) {
                DominioDetalle ambito = lista.get(i);
                listaAmbitoItem[i] = new SelectItem(ambito.getIdentificador().getTipo(), ambito.getDescripcion());
            }
        }
    }

    public void cargarClasePropiedadIntelectual() {
        List<DominioDetalle> lista = servicioGeneral.obtenerDominioDetalle(TIPO_CLASE_OBRA, true);

        if (!esListaVacia(lista)) {
            setSelectedClase(new String[lista.size()]);
            listaClaseItem = new SelectItem[lista.size()];
            for (int i = 0; i < lista.size(); i++) {
                DominioDetalle clase = lista.get(i);
                listaClaseItem[i] = new SelectItem(clase.getIdentificador().getTipo(), clase.getDescripcion());
            }
        }
    }

    public void cargarMediosPublicacionPropiedadIntelectual() {
        List<DominioDetalle> lista = servicioGeneral.obtenerDominioDetalle(TIPO_FORMATO_OBRA, true);

        if (!esListaVacia(lista)) {
            setSelectedFormato(new String[lista.size()]);
            listaFormatoItem = new SelectItem[lista.size()];
            for (int i = 0; i < lista.size(); i++) {
                DominioDetalle medio = lista.get(i);
                listaFormatoItem[i] = new SelectItem(medio.getIdentificador().getTipo(), medio.getDescripcion());
            }
        }
    }

    /**
     * Lista de resultados de la propiedad intelectual
     */
    public void cargarResultadosPropiedadIntelectual() {
        List<DominioDetalle> lista = servicioGeneral.obtenerDominioDetalle(TIPO_RESULTADO_PI, true);

        if (!esListaVacia(lista)) {
            listaResultadoItem = new SelectItem[lista.size()];
            for (int i = 0; i < lista.size(); i++) {
                DominioDetalle tipoResultado = lista.get(i);
                listaResultadoItem[i] = new SelectItem(tipoResultado.getIdentificador().getTipo(),
                        tipoResultado.getDescripcion());
            }
        }
    }

    private void cargarEstadosObra() {
        estadoObraItem = new SelectItem[2];
        estadoObraItem[0] = new SelectItem(PropiedadIntelectual.OBRA_EDITADA, "Editada / Publicada");
        estadoObraItem[1] = new SelectItem(PropiedadIntelectual.OBRA_INEDITA, "Inédita");
    }

    public void cargarTiposEdicion() {
        List<DominioDetalle> lista = servicioGeneral.obtenerDominioDetalle(TIPO_EDICION_OBRA, true);

        if (!esListaVacia(lista)) {
            setSelectedEdicion(new String[lista.size()]);
            listaEdicionItem = new SelectItem[lista.size()];
            for (int i = 0; i < lista.size(); i++) {
                DominioDetalle tipo = lista.get(i);
                listaEdicionItem[i] = new SelectItem(tipo.getIdentificador().getTipo(), tipo.getDescripcion());
            }
        }
    }

    public void guardarParcialmente() {
        if (propiedad.getSubEstado() == null || propiedad.getSubEstado().getId() == null
                || propiedad.getSubEstado().getId().equals(0L)) {
            EstadoPropiedadIntelectual estado = new EstadoPropiedadIntelectual();
            estado.setTipoPropiedad(propiedad.getSubTipo().getTipo());
            SubEstadoPropiedadIntelectual subEstado = new SubEstadoPropiedadIntelectual();
            subEstado.setEstado(estado);
            if (propiedad.isEsPropiedadIndustrial()) {
                subEstado.setId(SubEstadoPropiedadIntelectual.PROPIEDAD_INDUSTRIAL_FORMULACION);
            } else if (propiedad.isEsDerechosAutor()) {
                subEstado.setId(SubEstadoPropiedadIntelectual.DERECHOS_AUTOR_FORMULACION);
            } else {
                subEstado.setId(SubEstadoPropiedadIntelectual.DERECHOS_OBTENTOR_FORMULACION);
            }
            propiedad.setSubEstado(subEstado);
        }
        guardar();
        sesion.setAttribute(ID_PROPIEDAD_INTELECTUAL, propiedad.getId());
        sesion.setAttribute(TIPO_ACCESO, ManejadorBasePropiedadIntelectual.TIPO_EDICION);
        sesion.removeAttribute("manejadorPropiedadIntelectual");
        mensajeInfo("La solicitud de protección de propiedad intelectual número " + propiedad.getId()
                + " ha sido guardada.");
    }

    public void enviarSolicitud() {
        if (validarEnvio()) {
            EstadoPropiedadIntelectual estado = new EstadoPropiedadIntelectual();
            estado.setTipoPropiedad(propiedad.getSubTipo().getTipo());
            SubEstadoPropiedadIntelectual subEstado = new SubEstadoPropiedadIntelectual();
            subEstado.setEstado(estado);
            if (propiedad.isEsPropiedadIndustrial()) {
                subEstado.setId(SubEstadoPropiedadIntelectual.PROPIEDAD_INDUSTRIAL_ENVIADO);
            } else if (propiedad.isEsDerechosAutor()) {
                subEstado.setId(SubEstadoPropiedadIntelectual.DERECHOS_AUTOR_ENVIADO);
            } else {
                subEstado.setId(SubEstadoPropiedadIntelectual.DERECHOS_OBTENTOR_ENVIADO);
            }
            propiedad.setSubEstado(subEstado);
            if (esCadenaVacia(propiedad.getDependenciaApoyo())) {
                propiedad.setDependenciaApoyo(propiedad.getDependenciaSolicitante().getId().substring(0, 1));
                if(propiedad.isEsSolicitudIntersedes()){
                    propiedad.setDependenciaApoyo("1");
                }
            }
            guardar();
            
            
            Persona personaActual = cargarPersonaActual();
            TipoPropiedadIntelectual tipoActual = (TipoPropiedadIntelectual) servicioGeneral
                    .obtenerObjeto(new TipoPropiedadIntelectual(), propiedad.getSubTipo().getTipo().getId());
            
            String id = (!esNulo(propiedad.getId())) ? propiedad.getId().toString() : "";
            String tipo = (!esNulo(tipoActual)) ? tipoActual.getNombre() : "";
            
            Correo correoConfirmacion = new Correo();
            CorreoPlantilla cpConfirmacion = cargarPlantilla(276);
            correoConfirmacion.setOrigen(Correo.CORREO_HERMES_SOLICITUDES);
            //correoConfirmacion.adicionarCopiaOculta(Correo.CORREO_HERMES_SOLICITUDES);
            correoConfirmacion.adicionarDireccion(personaActual.getEmail());
            correoConfirmacion.setAsunto(cpConfirmacion.getAsunto());
            correoConfirmacion.setCuerpo(cpConfirmacion.getCuerpo()
                    .replaceAll("<<PERSONA>>", personaActual.getNombreCompletoMinusculas())
                    .replaceAll("<<ID>>", id)
                    .replaceAll("<<TIPO>>", tipo));
            if (correoConfirmacion.getDirecciones() != null) {
                servicioCorreo.enviarCorreo(correoConfirmacion);
            }

            servicioGeneral.obtenerObjeto(new TipoPropiedadIntelectual(), propiedad.getSubTipo().getTipo().getId());

            Dependencia dep = servicioDependencia
                    .obtenerDependencia(propiedad.getDependenciaSolicitante().getId().toString());
            String nivel;
            if(!propiedad.getDependenciaSolicitante().getSede().getId().equals(1L) && !propiedad.isEsSolicitudIntersedes()){
                nivel = "Sede "+dep.getSede().getNombre();
            }else{
                nivel = dep.getSede().getNombre();
            }

            Correo correoNotificacion = new Correo();
            CorreoPlantilla cpNotificacion = cargarPlantilla(277);
            correoNotificacion.setOrigen(Correo.CORREO_HERMES_SOLICITUDES);
            //correoNotificacion.adicionarCopiaOculta(Correo.CORREO_HERMES_SOLICITUDES);

            correoNotificacion.setAsunto(cpNotificacion.getAsunto());
            correoNotificacion.setCuerpo(cpNotificacion.getCuerpo()
                    .replaceAll("<<PERSONA>>", personaActual.getNombreCompletoMinusculas())
                    .replaceAll("<<ID>>", propiedad.getId().toString())
                    .replaceAll("<<NIVEL>>", nivel).replaceAll("<<TIPO>>", tipoActual.getNombre()));

            for (Persona per : servicioGeneral.obtenerListaObjetosWhere(Persona.class,
                    " JOIN p.roles r WHERE r.id = '" + Rol.DIR_NAL_INNOVA_PI + "'")) {
                correoNotificacion.adicionarDireccion(per.getEmail());
            }
            if (!propiedad.getDependenciaSolicitante().getSede().getId().equals(1L) && !propiedad.isEsSolicitudIntersedes()) {
                for (InvestigadorInterno inv : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class,
                        " JOIN i.roles r WHERE r.id = '" + Rol.PROPIEDAD_INTELECTUAL_SEDE
                                + "' and i.dependencia2.sede.id = '"
                                + propiedad.getDependenciaSolicitante().getSede().getId() + "'")) {
                    correoNotificacion.adicionarDireccion(inv.getEmail());
                }
            }
            if (correoNotificacion.getDirecciones() != null) {
                servicioCorreo.enviarCorreo(correoNotificacion);
            }

            sesion.setAttribute(TIPO_ACCESO, ManejadorBasePropiedadIntelectual.TIPO_CONSULTA);
            sesion.removeAttribute("manejadorPropiedadIntelectual");
            mensajeInfo("La solicitud número " + propiedad.getId() + " ha sido enviada correctamente");
        }
    }

    public void guardar() {
        propiedad.setFechaRegistro(new Date());
        if (propiedad.getPais() == null || propiedad.getPais().getId() == null) {
            propiedad.setPais(null);
        }
        propiedad.setResponsableRegistro(servicioPersona.obtenerInvestigadorInterno(cargarPersonaActual().getId()));
        actualizarListaCaracter();
        actualizarListaAmbito();
        actualizarTiposEdicion();
        actualizarClase();
        actualizarFormatoObra();

        /* Control de cantidad de caracteres para la base de datos */
        propiedad.setTitulo(controlTamanoCadena(propiedad.getTitulo(), 3000));
        propiedad.setTituloEspanol(controlTamanoCadena(propiedad.getTituloEspanol(), 3000));
        propiedad.setDescripcion(controlTamanoCadena(propiedad.getDescripcion(), 3000));
        propiedad.setImpactoSolucion(controlTamanoCadena(propiedad.getImpactoSolucion(), 3000));
        propiedad.setVentajas(controlTamanoCadena(propiedad.getVentajas(), 3000));
        propiedad.setMercadoPoblacion(controlTamanoCadena(propiedad.getMercadoPoblacion(), 3000));
        propiedad.setInformacionEmail(controlTamanoCadena(propiedad.getInformacionEmail(), 2000));
        propiedad.setRitmo(controlTamanoCadena(propiedad.getRitmo(), 3000));
        propiedad.setOtrosDatos(controlTamanoCadena(propiedad.getOtrosDatos(), 3000));
        propiedad.setMonitoreo(controlTamanoCadena(propiedad.getMonitoreo(), 3000));
        propiedad.setDifusion(controlTamanoCadena(propiedad.getDifusion(), 3000));

        servicioGeneral.guardarObjeto(propiedad);
        crearHistoricoEstadoPropiedadIntelectual(propiedad, cargarPersonaActual(),
                "Ingreso/edición de datos por parte del solicitante");
    }

    private boolean validarEnvio() {
        boolean valida = true;

        if (propiedad.getDependenciaSolicitante() == null || propiedad.getDependenciaSolicitante().getId() == null
                || "".equals(propiedad.getDependenciaSolicitante().getId())) {
            mensajeError("Debe indicar la dependencia desde la cual se realiza la solicitud.");
            valida = false;
        }
        
        
        /* Se quita validación por que se quita TIPO PARTICIPACIÓN - Requerimiento 2433
        if (!propiedad.isHayPrincipal() && !propiedad.isEsMarca()) {
            if (propiedad.isEsDerechosAutor() && isEsObraIndividual()) {
                mensajeError(
                        "Dado que la obra es de caracter individual debe agregar el autor(es) de la propiedad que está registrando, en caso de obra audiovisual, debe existir mínimo un productor.");
                valida = false;
            } else if (propiedad.isEsDerechosAutor() && esListaVacia(propiedad.getListaPersonas())) {
                mensajeError("Debe agregar el autor(es) o coautores de la propiedad que está registrando, en caso de obra audiovisual, debe existir mínimo un productor.");
                valida = false;
            }else if (propiedad.isEsPropiedadIndustrial()) {
                mensajeError("Debe agregar el inventor(es) o diseñador(es) de la propiedad que está registrando.");
                valida = false;
            } else if (propiedad.isEsDerechosObtentor()){
                mensajeError("Debe agregar el obtentor(es) de la propiedad que está registrando.");
                valida = false;
            }
        }
        */

        if (esCadenaVacia(propiedad.getTitulo())) {
            mensajeError("Debe ingresar el título de la propiedad intelectual");
            valida = false;
        }

        if (propiedad.isEsDerechosAutor() && !validarDerechosAutor()) {
            valida = false;
        }

        if (propiedad.isEsPropiedadIndustrial() && !validarPropiedadIndustrial()) {
            valida = false;
        }

        return valida;
    }

    private boolean validarPropiedadIndustrial() {
        boolean valida = true;
        	
//        Req 2591 - Se eliminan validaciones sobre estos campos
        
//        if (esCadenaVacia(propiedad.getObjetoProteccion())) {
//            mensajeError("Debe describir el objeto de protección (producto, proceso, diseño, marca).");
//            valida = false;
//        }
//
//        if (esCadenaVacia(propiedad.getDescripcion())) {
//            mensajeError(
//                    "Debe describir la invención en términos que permitan establecer el problema técnico que soluciona y como contribuye a resolverlo.");
//            valida = false;
//        }
//        
//        if (esCadenaVacia(propiedad.getVentajas())) {
//            mensajeError(
//                    "Debe ingresar las ventajas y diferencias de la invención frente a otras del mismo tipo, en caso de que no cuente con la información puede indicar que no existen datos al respecto.");
//            valida = false;
//        }
//        
//        if (esCadenaVacia(propiedad.getImpactoSolucion())) {
//            mensajeError(
//                    "Debe definir como la invención supera los problemas y dificultades, en caso de que no cuente con la información puede indicar que no existen datos al respecto.");
//            valida = false;
//        }
//        
//        if (esCadenaVacia(propiedad.getMonitoreo())) {
//            mensajeError(
//                    "Si ha realizado monitoreo tecnológico mencionar los hallazgos, en caso de que no cuente con la información puede indicar que no existen datos al respecto.");
//            valida = false;
//        }
//        
//        if (esCadenaVacia(propiedad.getDifusion())) {
//            mensajeError(
//                    "Si ha realizado publicaciones o divulgaciones de la invención mencionar cual fue el medio de divulgación, en caso de que no cuente con la información puede indicar que no existen datos al respecto.");
//            valida = false;
//        }
//        
//        if (esCadenaVacia(propiedad.getMercadoPoblacion())) {
//            mensajeError(
//                    "En caso de conocer las posibilidades del mercado de la invención indíquelas, si no cuenta con la información puede indicar que no existen datos al respecto.");
//            valida = false;
//        }
        
        if (propiedad.getEstadoDesarrollo() == null || (propiedad.getEstadoDesarrollo() != null
                && ("".equals(propiedad.getEstadoDesarrollo().getId()) || propiedad.getEstadoDesarrollo().getId().equals(0L)))) {
            mensajeError("Debe seleccionar el estado de desarrollo.");
            valida = false;
        }
        
        if (propiedad.getSectorTecnologico() == null || (propiedad.getSectorTecnologico() != null
                && ("".equals(propiedad.getSectorTecnologico().getId()) || propiedad.getSectorTecnologico().getId().equals(0L)))) {
            mensajeError("Debe seleccionar la clasificación de la obra audiovisual.");
            valida = false;
        }

        return valida;
    }

    private boolean validarDerechosAutor() {
        boolean valida = true;

        if (propiedad.getAnoTerminacion() == null || propiedad.getAnoTerminacion() == 0L
                || "".equals(propiedad.getAnoTerminacion())) {
            mensajeError("Debe ingresar el año de creación");
            valida = false;
        }

        if (esCadenaVacia(propiedad.getEstadoObra()) && !propiedad.isEsObraAudioVisual()) {
            mensajeError("Debe indicar el estado de la propiedad intelectual 'Inédita o Editada/Publicada'.");
            valida = false;
        }

        if (cleanArrayString(selectedCaracter).length == 0 && !propiedad.isEsObraAudioVisual()
                && !propiedad.isEsFonograma()) {
            mensajeError("Debe indicar el carácter de la obra, puede seleccionar una o varias opciones");
            valida = false;
        }

        if (!propiedad.isEsObraAudioVisual() && propiedad.isEsEditada()) {
            if (propiedad.getFechaPublicacion() == null || "".equals(propiedad.getFechaPublicacion())) {
                mensajeError("Debe ingresar la fecha de publicación.");
                valida = false;
            }
        } else {
            propiedad.setFechaPublicacion(null);
        }

        if (propiedad.isEsObraLiteraria() && !validarObraLiteraria()) {

            valida = false;

        } else if (propiedad.isEsObraArtistica() && !validarObraArtistica()) {

            valida = false;

        } else if (propiedad.isEsSoftware() && !validarSoftware()) {

            valida = false;

        } else if (propiedad.isEsObraMusical() && !validarObraMusical()) {

            valida = false;

        } else if (propiedad.isEsFonograma() && !validarFonograma()) {

            valida = false;

        } else if (propiedad.isEsObraAudioVisual() && !validarObraAudioVisual()) {

            valida = false;
        }

        return valida;
    }

    public boolean validarObraLiteraria() {
        boolean valida = true;

        if (cleanArrayString(selectedAmbito).length == 0) {
            mensajeError("Debe indicar el ámbito de la obra literaria, puede seleccionar una o varias opciones");
            valida = false;
        }
        if (propiedad.isEsEditada()) {

            if (esCadenaVacia(propiedad.getEditor())) {
                mensajeError("Debe ingresar el editor de la obra literaria");
                valida = false;
            }
            if (esCadenaVacia(propiedad.getImpresor())) {
                mensajeError("Debe ingresar el impresor de la obra literaria");
                valida = false;
            }
            if (cleanArrayString(selectedEdicion).length == 0) {
                mensajeError(
                        "Debe indicar el tipo de edición de la obra literaria, puede seleccionar una o varias opciones");
                valida = false;
            }
            if (propiedad.getPais() == null || esCadenaVacia(propiedad.getPais().getId())
                    || "0".equals(propiedad.getPais().getId())) {
                mensajeError("Debe seleccionar el país de la primera publicación");
                valida = false;
            }
            if (esCadenaVacia(propiedad.getNumeroEdicion())) {
                mensajeError("Debe ingresar el número de la edición de la obra literaria");
                valida = false;
            }
            if (esCadenaVacia(propiedad.getTiraje())) {
                mensajeError("Debe ingresar el tiraje de la obra literaria");
                valida = false;
            }
            if (propiedad.getNumeroPaginas() == null || "".equals(propiedad.getNumeroPaginas())) {
                mensajeError("Debe ingresar el número de páginas de la obra literaria");
                valida = false;
            }
            if (esCadenaVacia(propiedad.getIsbn())) {
                mensajeError("Debe ingresar el número de isbn de la obra literaria");
                valida = false;
            }
        } else {
            propiedad.setEditor("");
            propiedad.setImpresor("");
            propiedad.borrarListaTiposEdicion(propiedad.getListaTiposEdicion());
            propiedad.setFechaPublicacion(null);
            propiedad.setPais(null);
            propiedad.setNumeroEdicion("");
            propiedad.setTiraje("");
            propiedad.setNumeroPaginas(null);
            propiedad.setIsbn("");
        }
        return valida;
    }

    public boolean validarObraArtistica() {
        boolean valida = true;
        if (cleanArrayString(selectedClase).length == 0) {
            mensajeError("Debe indicar la clase de obra, puede seleccionar una o varias opciones");
            valida = false;
        }
        return valida;
    }

    public boolean validarObraAudioVisual() {
        boolean valida = true;
        if (propiedad.getPais() == null || esCadenaVacia(propiedad.getPais().getId())
                || "0".equals(propiedad.getPais().getId())) {
            mensajeError("Debe seleccionar el país donde fue creada la obra");
            valida = false;
        }
        if (propiedad.getDuracion() == null || propiedad.getDuracion() <= 0) {
            mensajeError("Debe ingresar la duración de la obra");
            valida = false;
        }
        if (esCadenaVacia(propiedad.getDescripcion())) {
            mensajeError("Debe ingresar la sinopsis de la obra");
            valida = false;
        }
        if (cleanArrayString(selectedFormato).length == 0) {
            mensajeError(
                    "Debe indicar el formato en el que se encuentra la obra, puede seleccionar una o varias opciones");
            valida = false;
        }
        if (propiedad.getGenero() == null || (propiedad.getGenero() != null
                && ("".equals(propiedad.getGenero().getId()) || propiedad.getGenero().getId().equals(0L)))) {
            mensajeError("Debe seleccionar el género de la obra audiovisual.");
            valida = false;
        }
//        if (propiedad.getClasificacion() == null || (propiedad.getClasificacion() != null
//                && ("".equals(propiedad.getClasificacion().getId()) || propiedad.getClasificacion().getId().equals(0L)))) {
//            mensajeError("Debe seleccionar la clasificación de la obra audiovisual.");
//            valida = false;
//        }
        return valida;
    }

    public boolean validarFonograma() {
        boolean valida = true;
        if (propiedad.getPais() == null || esCadenaVacia(propiedad.getPais().getId())
                || "0".equals(propiedad.getPais().getId())) {
            mensajeError("Debe seleccionar el país donde fue creado el fonograma.");
            valida = false;
        }
        if (cleanArrayString(selectedFormato).length == 0) {
            mensajeError(
                    "Debe indicar el formato en el cual se encuentra el fonograma, puede seleccionar una o varias opciones.");
            valida = false;
        }

        if (esListaVacia(propiedad.getListaObrasFonograma())) {
            mensajeError("Debe ingresar las obras fijadas en el fonograma.");
            valida = false;
        } else {
            for (int i = 0; i < propiedad.getListaObrasFonograma().size(); i++) {
                if (esListaVacia(propiedad.getListaObrasFonograma().get(i).getListaAutores())) {
                    mensajeError("Debe ingresar el autor(es) de la obra '"
                            + propiedad.getListaObrasFonograma().get(i).getNombreObra() + "'.");
                    valida = false;
                }
            }
        }
        return valida;
    }

    public boolean validarSoftware() {
        boolean valida = true;
        if (propiedad.getPais() == null || propiedad.getPais().getId() == null
                || "".equals(propiedad.getPais().getId())) {
            mensajeError("Debe seleccionar el país de origen del software");
            valida = false;
        }
        if (esCadenaVacia(propiedad.getDescripcion())) {
            mensajeError("Debe ingresar una breve descripción de las funciones del software");
            valida = false;
        } else if (propiedad.getDescripcion().length() < 50) {
            mensajeError("El texto de las funciones es muy corto.");
            valida = false;
        }
        if (esCadenaVacia(propiedad.getVentajas())) {
            mensajeError("Debe diligenciar las ventajas que proporciona el software");
            valida = false;
        }
        if (esCadenaVacia(propiedad.getImpactoSolucion())) {
            mensajeError("Debe diligenciar brevemente los problemas que soluciona el software");
            valida = false;
        }
        if (esCadenaVacia(propiedad.getMercadoPoblacion())) {
            mensajeError("Debe ingresar una breve descripción del sector o población interesada en el software");
            valida = false;
        }
        return valida;
    }

    public boolean validarObraMusical() {
        boolean valida = true;
        if (esCadenaVacia(propiedad.getRitmo())) {
            mensajeError("Debe ingresar el ritmo al que pertenece la obra musical");
            valida = false;
        }
        if (propiedad.getGenero() == null || (propiedad.getGenero() != null
                && ("".equals(propiedad.getGenero().getId()) || propiedad.getGenero().getId().equals(0L)))) {
            mensajeError("Debe seleccionar el género de la obra musical.");
            valida = false;
        }
        return valida;
    }

    public void actualizarListaCaracter() {
        List<CaracterPropiedadIntelectual> listaAlmacenar = new ArrayList<CaracterPropiedadIntelectual>();
        List<String> listaOficial = Arrays.asList(cleanArrayString(selectedCaracter));

        for (int i = 0; i < listaOficial.size(); i++) {
            boolean existe = false;
            for (int a = 0; a < propiedad.getListaCaracter().size(); a++) {
                if (listaOficial.get(i)
                        .equals(propiedad.getListaCaracter().get(a).getCaracter().getIdentificador().getTipo())) {
                    existe = true;
                    if (listaOficial.get(i).equals(DominioDetalle.OTRO_CARACTER_OBRA)) {
                        propiedad.getListaCaracter().get(a).setObservaciones(otroCaracter);
                    }
                    listaAlmacenar.add(propiedad.getListaCaracter().get(a));
                    propiedad.borrarCaracter(propiedad.getListaCaracter().get(a));
                    break;
                }
            }
            if (!existe) {
                CaracterPropiedadIntelectual caracterNuevo = new CaracterPropiedadIntelectual();
                DominioDetalle caracterDominioDetalle = new DominioDetalle();
                IdDominioDetalle id = new IdDominioDetalle();
                id.setId(Dominio.CARACTER_OBRA_PROP_INT);
                id.setTipo(listaOficial.get(i));
                caracterDominioDetalle.setIdentificador(id);
                caracterNuevo.setCaracter(caracterDominioDetalle);
                caracterNuevo.setPropiedad(propiedad);
                if (listaOficial.get(i).equals(DominioDetalle.OTRO_CARACTER_OBRA)) {
                    caracterNuevo.setObservaciones(otroCaracter);
                }
                listaAlmacenar.add(caracterNuevo);
            }
        }
        propiedad.borrarListaCaracter(propiedad.getListaCaracter());
        propiedad.setListaCaracter(listaAlmacenar);
    }
    
    public boolean isEsObraIndividual(){
        List<String> listaOficial = Arrays.asList(cleanArrayString(selectedCaracter));
        for (int i = 0; i < listaOficial.size(); i++) {
            if (listaOficial.get(i)
                    .equals(CaracterPropiedadIntelectual.OBRA_INDIVIDUAL)) {
                return true;
            }
        }
        return false;
    }

    public void actualizarListaAmbito() {
        List<AmbitoPropiedadIntelectual> listaAlmacenar = new ArrayList<AmbitoPropiedadIntelectual>();
        List<String> listaOficial = Arrays.asList(cleanArrayString(selectedAmbito));

        for (int i = 0; i < listaOficial.size(); i++) {
            boolean existe = false;
            for (int a = 0; a < propiedad.getListaAmbito().size(); a++) {
                if (listaOficial.get(i)
                        .equals(propiedad.getListaAmbito().get(a).getAmbito().getIdentificador().getTipo())) {
                    existe = true;
                    if (listaOficial.get(i).equals(DominioDetalle.OTRO_AMBITO_OBRA)) {
                        propiedad.getListaAmbito().get(a).setObservaciones(otroCaracter);
                    }
                    listaAlmacenar.add(propiedad.getListaAmbito().get(a));
                    propiedad.borrarAmbito(propiedad.getListaAmbito().get(a));
                    break;
                }
            }
            if (!existe) {
                AmbitoPropiedadIntelectual ambitoNuevo = new AmbitoPropiedadIntelectual();
                DominioDetalle dominioDetalle = new DominioDetalle();
                IdDominioDetalle id = new IdDominioDetalle();
                id.setId(Dominio.AMBITO_OBRA_LITERARIA_PROP_INT);
                id.setTipo(listaOficial.get(i));
                dominioDetalle.setIdentificador(id);
                ambitoNuevo.setAmbito(dominioDetalle);
                ambitoNuevo.setPropiedad(propiedad);
                if (listaOficial.get(i).equals(DominioDetalle.OTRO_AMBITO_OBRA)) {
                    ambitoNuevo.setObservaciones(otroAmbito);
                }
                listaAlmacenar.add(ambitoNuevo);
            }
        }
        propiedad.borrarListaAmbito(propiedad.getListaAmbito());
        propiedad.setListaAmbito(listaAlmacenar);
    }

    public void actualizarTiposEdicion() {
        List<TipoEdicionPropiedadIntelectual> listaAlmacenar = new ArrayList<TipoEdicionPropiedadIntelectual>();
        List<String> listaOficial = Arrays.asList(cleanArrayString(selectedEdicion));

        for (int i = 0; i < listaOficial.size(); i++) {
            boolean existe = false;
            for (int a = 0; a < propiedad.getListaTiposEdicion().size(); a++) {
                if (listaOficial.get(i)
                        .equals(propiedad.getListaTiposEdicion().get(a).getTipo().getIdentificador().getTipo())) {
                    existe = true;
                    if (listaOficial.get(i).equals(DominioDetalle.OTRO_TIPO_EDICION_OBRA)) {
                        propiedad.getListaTiposEdicion().get(a).setObservaciones(otroTipoEdicion);
                    }
                    listaAlmacenar.add(propiedad.getListaTiposEdicion().get(a));
                    propiedad.borrarTipoEdicion(propiedad.getListaTiposEdicion().get(a));
                    break;
                }
            }
            if (!existe) {
                TipoEdicionPropiedadIntelectual tipoEdicionNuevo = new TipoEdicionPropiedadIntelectual();
                DominioDetalle dominioDetalle = new DominioDetalle();
                IdDominioDetalle id = new IdDominioDetalle();
                id.setId(Dominio.TIPO_EDICION_OBRA_PROP_INT);
                id.setTipo(listaOficial.get(i));
                dominioDetalle.setIdentificador(id);
                tipoEdicionNuevo.setTipo(dominioDetalle);
                tipoEdicionNuevo.setPropiedad(propiedad);
                if (listaOficial.get(i).equals(DominioDetalle.OTRO_TIPO_EDICION_OBRA)) {
                    tipoEdicionNuevo.setObservaciones(otroTipoEdicion);
                }
                listaAlmacenar.add(tipoEdicionNuevo);
            }
        }
        propiedad.borrarListaTiposEdicion(propiedad.getListaTiposEdicion());
        propiedad.setListaTiposEdicion(listaAlmacenar);
    }

    public void actualizarClase() {
        List<ClasePropiedadIntelectual> listaAlmacenar = new ArrayList<ClasePropiedadIntelectual>();
        List<String> listaOficial = Arrays.asList(cleanArrayString(selectedClase));

        for (int i = 0; i < listaOficial.size(); i++) {
            boolean existe = false;
            for (int a = 0; a < propiedad.getListaClase().size(); a++) {
                if (listaOficial.get(i)
                        .equals(propiedad.getListaClase().get(a).getClase().getIdentificador().getTipo())) {
                    existe = true;
                    if (listaOficial.get(i).equals(DominioDetalle.OTRO_CLASE_OBRA)) {
                        propiedad.getListaClase().get(a).setObservaciones(otraClase);
                    }
                    listaAlmacenar.add(propiedad.getListaClase().get(a));
                    propiedad.borrarClase(propiedad.getListaClase().get(a));
                    break;
                }
            }
            if (!existe) {
                ClasePropiedadIntelectual claseNueva = new ClasePropiedadIntelectual();
                DominioDetalle dominioDetalle = new DominioDetalle();
                IdDominioDetalle id = new IdDominioDetalle();
                id.setId(Dominio.CLASE_OBRA_PROP_INT);
                id.setTipo(listaOficial.get(i));
                dominioDetalle.setIdentificador(id);
                claseNueva.setClase(dominioDetalle);
                claseNueva.setPropiedad(propiedad);
                if (listaOficial.get(i).equals(DominioDetalle.OTRO_CLASE_OBRA)) {
                    claseNueva.setObservaciones(otraClase);
                }
                listaAlmacenar.add(claseNueva);
            }
        }
        propiedad.borrarListaClase(propiedad.getListaClase());
        propiedad.setListaClase(listaAlmacenar);
    }

    public void actualizarFormatoObra() {
        List<FormatoPropiedadIntelectual> listaAlmacenar = new ArrayList<FormatoPropiedadIntelectual>();
        List<String> listaOficial = Arrays.asList(cleanArrayString(selectedFormato));

        for (int i = 0; i < listaOficial.size(); i++) {
            boolean existe = false;
            for (int a = 0; a < propiedad.getListaFormato().size(); a++) {
                if (listaOficial.get(i)
                        .equals(propiedad.getListaFormato().get(a).getMedio().getIdentificador().getTipo())) {
                    existe = true;
                    if (listaOficial.get(i).equals(DominioDetalle.OTRO_FORMATO_OBRA)) {
                        propiedad.getListaFormato().get(a).setObservaciones(otroFormato);
                    }
                    listaAlmacenar.add(propiedad.getListaFormato().get(a));
                    propiedad.borrarFormato(propiedad.getListaFormato().get(a));
                    break;
                }
            }
            if (!existe) {
                FormatoPropiedadIntelectual formatoNuevo = new FormatoPropiedadIntelectual();
                DominioDetalle dominioDetalle = new DominioDetalle();
                IdDominioDetalle id = new IdDominioDetalle();
                id.setId(Dominio.TIPO_FORMATO_PROP_INT);
                id.setTipo(listaOficial.get(i));
                dominioDetalle.setIdentificador(id);
                formatoNuevo.setMedio(dominioDetalle);
                formatoNuevo.setPropiedad(propiedad);
                if (listaOficial.get(i).equals(DominioDetalle.OTRO_FORMATO_OBRA)) {
                    formatoNuevo.setObservaciones(otroFormato);
                }
                listaAlmacenar.add(formatoNuevo);
            }
        }
        propiedad.borrarListaFormato(propiedad.getListaFormato());
        propiedad.setListaFormato(listaAlmacenar);
    }

    public void adjuntarArchivo(FileUploadEvent event) {
        archivoCargado = event.getFile();
        ArchivoPropiedadIntelectual ap = insertarArchivoPropiedadIntelectual(archivoCargado);

        if (ap != null && (ap.getId() != null || ap.getId() != 0L)) {
            ap.setPropiedad(propiedad);
            ap.setEstado("V");
            ap.setTipo(new TipoArchivo(ArchivoPropiedadIntelectual.ARCHIVO_SOLICITUD));
            ap.setDescripcion(descripcionArchivo);
            ap.setVisibleDocente(true);
            ap.setFechaCreacion(new Date());
            ap.setPersonaCarga(cargarPersonaActual());
            propiedad.adicionarArchivo(ap);
        }
    }

    public SelectItem[] getTiposPropiedadIntelectualItem() {
        return tiposPropiedadIntelectualItem;
    }

    public void setTiposPropiedadIntelectualItem(SelectItem[] tiposPropiedadIntelectualItem) {
        this.tiposPropiedadIntelectualItem = tiposPropiedadIntelectualItem;
    }

    public SelectItem[] getSedesItem() {
        return sedesItem;
    }

    public void setSedesItem(SelectItem[] sedesItem) {
        this.sedesItem = sedesItem;
    }

    public List<SelectItem> getDependenciasItem() {
        return dependenciasItem;
    }

    public String getNivelSolicitante() {
        return nivelSolicitante;
    }

    public void setNivelSolicitante(String nivelSolicitante) {
        this.nivelSolicitante = nivelSolicitante;
    }

    public SelectItem[] getProyectosItem() {
        return proyectosItem;
    }

    public void setProyectosItem(SelectItem[] proyectosItem) {
        this.proyectosItem = proyectosItem;
    }

    public SelectItem[] getGruposItem() {
        return gruposItem;
    }

    public void setGruposItem(SelectItem[] gruposItem) {
        this.gruposItem = gruposItem;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public SelectItem[] getListaCaracterItem() {
        return listaCaracterItem;
    }

    public void setListaCaracterItem(SelectItem[] listaCaracterItem) {
        this.listaCaracterItem = listaCaracterItem;
    }

    public String[] getSelectedCaracter() {
        return selectedCaracter;
    }

    public void setSelectedCaracter(String[] selectedCaracter) {
        this.selectedCaracter = selectedCaracter;
    }

    public SelectItem[] getListaAmbitoItem() {
        return listaAmbitoItem;
    }

    public void setListaAmbitoItem(SelectItem[] listaAmbitoItem) {
        this.listaAmbitoItem = listaAmbitoItem;
    }

    public String[] getSelectedAmbito() {
        return selectedAmbito;
    }

    public void setSelectedAmbito(String[] selectedAmbito) {
        this.selectedAmbito = selectedAmbito;
    }

    public SelectItem[] getEstadoObraItem() {
        return estadoObraItem;
    }

    public void setEstadoObraItem(SelectItem[] estadoObraItem) {
        this.estadoObraItem = estadoObraItem;
    }

    public SelectItem[] getListaEdicionItem() {
        return listaEdicionItem;
    }

    public void setListaEdicionItem(SelectItem[] listaEdicionItem) {
        this.listaEdicionItem = listaEdicionItem;
    }

    public String[] getSelectedEdicion() {
        return selectedEdicion;
    }

    public void setSelectedEdicion(String[] selectedEdicion) {
        this.selectedEdicion = selectedEdicion;
    }

    public String getOtroCaracter() {
        return otroCaracter;
    }

    public void setOtroCaracter(String otroCaracter) {
        this.otroCaracter = otroCaracter;
    }

    public String getOtroTipoEdicion() {
        return otroTipoEdicion;
    }

    public void setOtroTipoEdicion(String otroTipoEdicion) {
        this.otroTipoEdicion = otroTipoEdicion;
    }

    public String getOtroAmbito() {
        return otroAmbito;
    }

    public void setOtroAmbito(String otroAmbito) {
        this.otroAmbito = otroAmbito;
    }

    public SelectItem[] getTiposPersonaItem() {
        return tiposPersonaItem;
    }

    public void setTiposPersonaItem(SelectItem[] tiposPersonaItem) {
        this.tiposPersonaItem = tiposPersonaItem;
    }

    public String getCalidad() {
        return calidad;
    }

    public void setCalidad(String calidad) {
        this.calidad = calidad;
    }

    public Investigador getInvestigadorAgregar() {
        return investigadorAgregar;
    }

    public void setInvestigadorAgregar(Investigador investigadorAgregar) {
        this.investigadorAgregar = investigadorAgregar;
    }

    public PersonaPropiedadIntelectual getPersonaEliminar() {
        return personaEliminar;
    }

    public void setPersonaEliminar(PersonaPropiedadIntelectual personaEliminar) {
        this.personaEliminar = personaEliminar;
    }

    public String getDescripcionArchivo() {
        return descripcionArchivo;
    }

    public void setDescripcionArchivo(String descripcionArchivo) {
        this.descripcionArchivo = descripcionArchivo;
    }

    public boolean isDatosExterno() {
        return datosExterno;
    }

    public void setDatosExterno(boolean datosExterno) {
        this.datosExterno = datosExterno;
    }

    public SelectItem[] getGeneroItem() {
        return generoItem;
    }

    public void setGeneroItem(SelectItem[] generoItem) {
        this.generoItem = generoItem;
    }

    public SelectItem[] getListaClaseItem() {
        return listaClaseItem;
    }

    public void setListaClaseItem(SelectItem[] listaClaseItem) {
        this.listaClaseItem = listaClaseItem;
    }

    public String[] getSelectedClase() {
        return selectedClase;
    }

    public void setSelectedClase(String[] selectedClase) {
        this.selectedClase = selectedClase;
    }

    public String getOtraClase() {
        return otraClase;
    }

    public void setOtraClase(String otraClase) {
        this.otraClase = otraClase;
    }

    public String getOtroFormato() {
        return otroFormato;
    }

    public void setOtroFormato(String formato) {
        this.otroFormato = formato;
    }

    public String[] getSelectedFormato() {
        return selectedFormato;
    }

    public void setSelectedFormato(String[] selectedFormato) {
        this.selectedFormato = selectedFormato;
    }

    public SelectItem[] getListaFormatoItem() {
        return listaFormatoItem;
    }

    public void setListaPublicacionItem(SelectItem[] listaPublicacionItem) {
        this.listaFormatoItem = listaPublicacionItem;
    }

    public String getEntidadPersonaExterna() {
        return entidadPersonaExterna;
    }

    public void setEntidadPersonaExterna(String entidadPersonaExterna) {
        this.entidadPersonaExterna = entidadPersonaExterna;
    }

    public String getObraFonograma() {
        return obraFonograma;
    }

    public void setObraFonograma(String obraFonograma) {
        this.obraFonograma = obraFonograma;
    }

    public ObraFonogramaPropiedadIntelectual getObraFonogramaEliminar() {
        return obraFonogramaEliminar;
    }

    public void setObraFonogramaEliminar(ObraFonogramaPropiedadIntelectual obraFonogramaEliminar) {
        this.obraFonogramaEliminar = obraFonogramaEliminar;
    }

    public SelectItem[] getObrasFijadasItem() {
        return obrasFijadasItem;
    }

    public void setObrasFijadasItem(SelectItem[] obrasFijadasItem) {
        this.obrasFijadasItem = obrasFijadasItem;
    }

    public String getObraFijada() {
        return obraFijada;
    }

    public void setObraFijada(String obraFijada) {
        this.obraFijada = obraFijada;
    }

    public SelectItem[] getSolicitudesISBNItem() {
        return solicitudesISBNItem;
    }

    public void setSolicitudesISBNItem(SelectItem[] solicitudesISBNItem) {
        this.solicitudesISBNItem = solicitudesISBNItem;
    }

    public String getPaisPersona() {
        return paisPersona;
    }

    public void setPaisPersona(String paisPersona) {
        this.paisPersona = paisPersona;
    }

    public String getCiudadPersona() {
        return ciudadPersona;
    }

    public void setCiudadPersona(String ciudadPersona) {
        this.ciudadPersona = ciudadPersona;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public SelectItem[] getListaResultadoItem() {
        return listaResultadoItem;
    }

    public void setListaResultadoItem(SelectItem[] listaResultadoItem) {
        this.listaResultadoItem = listaResultadoItem;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getDescripcionResultado() {
        return descripcionResultado;
    }

    public void setDescripcionResultado(String descripcionResultado) {
        this.descripcionResultado = descripcionResultado;
    }

    public ResultadoPropiedadIntelectual getResultadoSeleccionado() {
        return resultadoSeleccionado;
    }

    public void setResultadoSeleccionado(ResultadoPropiedadIntelectual resultadoSeleccionado) {
        this.resultadoSeleccionado = resultadoSeleccionado;
    }

    public String getProyectoAgregar() {
        return proyectoAgregar;
    }

    public void setProyectoAgregar(String proyectoAgregar) {
        this.proyectoAgregar = proyectoAgregar;
    }

    public String getGrupoAgregar() {
        return grupoAgregar;
    }

    public void setGrupoAgregar(String grupoAgregar) {
        this.grupoAgregar = grupoAgregar;
    }

    public String getTipoProyecto() {
        return tipoProyecto;
    }

    public void setTipoProyecto(String tipoProyecto) {
        this.tipoProyecto = tipoProyecto;
    }

    public ProyectoPropiedadIntelectual getProyectoSeleccionado() {
        return proyectoSeleccionado;
    }

    public void setProyectoSeleccionado(ProyectoPropiedadIntelectual proyectoSeleccionado) {
        this.proyectoSeleccionado = proyectoSeleccionado;
    }

    public Grupo getGrupoSeleccionado() {
        return grupoSeleccionado;
    }

    public void setGrupoSeleccionado(Grupo grupoSeleccionado) {
        this.grupoSeleccionado = grupoSeleccionado;
    }

    public String getCodigoProyectoExtension() {
        return codigoProyectoExtension;
    }

    public void setCodigoProyectoExtension(String codigoProyectoExtension) {
        this.codigoProyectoExtension = codigoProyectoExtension;
    }

    public String getNombreProyectoExtension() {
        return nombreProyectoExtension;
    }

    public void setNombreProyectoExtension(String nombreProyectoExtension) {
        this.nombreProyectoExtension = nombreProyectoExtension;
    }

}