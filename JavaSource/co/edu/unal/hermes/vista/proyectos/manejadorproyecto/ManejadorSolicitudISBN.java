package co.edu.unal.hermes.vista.proyectos.manejadorproyecto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.component.menuitem.MenuItem;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.AreaTematica;
import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.Departamento;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DependenciaAreaResponsabilidad;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Estudiante;
import co.edu.unal.hermes.modelo.Formulario;
import co.edu.unal.hermes.modelo.HistoricoFormularioProyecto;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Institucion;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorExterno;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.Pais;
import co.edu.unal.hermes.modelo.PalabraClave;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.RestriccionConvocatoria;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoInvestigador;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.utils.VariablesEstaticas;
import co.edu.unal.hermes.vista.proyectos.ManejadorMenuFormularios;

public class ManejadorSolicitudISBN extends ManejadorProyecto {

    private Convocatoria convocatoriaActual;
    private PalabraClave palabraClave; // PALABRA CLAVE ACTUAL
    private PalabraClave palabraClaveTabla; // PALABRA CLAVE ACTUAL
    private PalabraClave keyWord; // PALABRA CLAVE salirGuardarACTUAL
    public List<PalabraClave> listaPalabrasClave;
    private String linkLineas;
    private String areaCiencia = "2701";
    private String areaCienciaSec = "2701";
    private String DOMINIO_AREA_CIENCIA = "AREA_CIENCIA";
    private List listaAreaCiencia;
    private SelectItem[] areaCienciaItems;
    private String dependenciaAdicionada;
    private List<Dependencia> dependenciasUN;
    public SelectItem[] dependenciaItem;
    public SelectItem[] autores;
    private DependenciaAreaResponsabilidad dependenciaAreaResponsabilidad;
    private DependenciaAreaResponsabilidad dependenciaAreaResponsabilidadSeleccionada;
    private InvestigadorProyecto investigadorProyectoNuevo;
    private List listaTipoDocumento;
    private SelectItem[] tipoDocumentoItem;
    public List listaInvestigadoresVista;
    private boolean investigadorExiste = true;
    private InvestigadorProyecto copiaValidacionInvestigador;
    private InvestigadorProyecto investigadorProyectoActual;
    private TipoDocumento tipoDocumentoCoInv2;
    private String documentoCoinv2;
    private String tipoInvestigador;
    private List<InvestigadorProyecto> listaParticipantes;
    private List<InvestigadorProyecto> listaParticipantesBorrados;
    private InvestigadorExterno investigadorExterno = new InvestigadorExterno();
    private String insitucionNombre;
    private boolean esOtraVinculacion = false;
    private SelectItem[] generoItem = {
            new SelectItem(VariablesEstaticas.GENERO_FEMENINO, VariablesEstaticas.GENERO_FEMENINO),
            new SelectItem(VariablesEstaticas.GENERO_MASCULINO, VariablesEstaticas.GENERO_MASCULINO) };
    private InvestigadorProyecto participante = new InvestigadorProyecto();
    private boolean proyectoExiste = false;
    private List listaAreasPrimSec;
    private SelectItem[] tipoEventoItems;
    private List<DominioDetalle> listaTipoEvento;
    private boolean mostrarOtroTipoEvento = false;
    private String DOMINIO_TIPO_COLECCION = "COLECCION_CONVOCATORIA_LIBROS";
    private boolean mostrarSiConvLibros = false;
    private boolean mostrarSiArticulosUno = false;
    private boolean mostrarSiArticulosDos = false;

    // CONV ARTICULOS
    public String documentoCoinv;
    public TipoDocumento tipoDocumentoCoInv;

    private String nombreCompletoPersonaActual;
    private String nombreSede;
    private String nombreFacultad;
    private String nombreDepartamento;
    private String emailPersonaActual;
    private String telefono;
    private InvestigadorInterno ii;

    private List listaAreas;

    private Persona personaActual;

    // ISBN
    private boolean mostrarSiSolicitudISBN = false;

    public static final String DOMINIO_ISBN_MATERIA = "ISBN_MATERIA";
    public static final String DOMINIO_ISBN_TIPO_CONTENIDO = "ISBN_TIPO_CONTENIDO";
    public static final String DOMINIO_ISBN_IDIOMA = "ISBN_IDIOMA";
    public static final String DOMINIO_ISBN_ROL_AUTOR = "ISBN_ROL_AUTOR";
    public static final String DOMINIO_ISBN_TIPO_SOPORTE = "ISBN_TIPO_SOPORTE";
    public static final String DOMINIO_ISBN_TIPO_ENCUADERNACION = "ISBN_TIPO_ENCUADERNACION";
    public static final String DOMINIO_ISBN_TIPO_PAPEL = "ISBN_TIPO_PAPEL";
    public static final String DOMINIO_ISBN_GRAMAJE = "ISBN_GRAMAJE";
    public static final String DOMINIO_ISBN_TIPO_IMPRESION = "ISBN_TIPO_IMPRESION";
    public static final String DOMINIO_ISBN_NUM_TINTAS = "ISBN_NUM_TINTAS";
    public static final String DOMINIO_ISBN_MEDIO_ELECTRONICO = "ISBN_MEDIO_ELECTRONICO";
    public static final String DOMINIO_ISBN_FORMATO = "ISBN_FORMATO";
    public static final String DOMINIO_ISBN_TAMAÑO = "ISBN_TAMANO";
    public static final String DOMINIO_ISBN_DESC_FISICA = "ISBN_DESC_FISICA";
    
    public static final String DOMINIO_ISBN_TIPO_OBRA_2018 = "ISBN_TIPO_OBRA_2018";
    public static final String DOMINIO_ISBN_TIPO_PUBLICACION_2018 = "ISBN_TIPO_PUBLICACION_2018";
    public static final String DOMINIO_ISBN_IDIOMA_2018 = "ISBN_IDIOMA_2018";
    public static final String DOMINIO_ISBN_AUDIENCIA_2018 = "ISBN_AUDIENCIA_2018";
    public static final String DOMINIO_ISBN_TIPO_ISBN_2018 = "ISBN_TIPO_ISBN_2018";
    public static final String DOMINIO_ISBN_TIPO_PRODUCTO_2018 = "ISBN_TIPO_PRODUCTO_2018";
    public static final String DOMINIO_ISBN_TIPO_ENCUADERNACION_2018 = "ISBN_TIPO_ENCUADERNACION_2018";
    public static final String DOMINIO_ISBN_TIPO_PAPEL_2018 = "ISBN_TIPO_PAPEL_2018";
    public static final String DOMINIO_ISBN_TIPO_IMPRESION_2018 = "ISBN_TIPO_IMPRESION_2018";
    public static final String DOMINIO_ISBN_TIPO_SOPORTE_2018 = "ISBN_TIPO_SOPORTE_2018";
    public static final String DOMINIO_ISBN_TIPO_FORMATO_2018 = "ISBN_TIPO_FORMATO_2018";
    public static final String DOMINIO_ISBN_TIPO_CONTENIDO_PRODUCTO_2018 = "ISBN_TIPO_CONTENIDO_PRODUCTO_2018";
    public static final String DOMINIO_ISBN_TIPO_PROC_TEC_ARC_2018 = "ISBN_TIPO_PROC_TEC_ARC_2018";
    public static final String DOMINIO_ISBN_PERMISOS_USO_2018 = "ISBN_PERMISOS_USO_2018";

    private List<SelectItem> listaMateriasISBN;
    private List<SelectItem> listaTipoContenidoISBN;
    private List<SelectItem> listaIdiomasISBN;
    private List<SelectItem> listaRolAutorISBN;
    private List<SelectItem> listaTipoSoporteISBN; // Posible borrar - Se cambia por Tipo ISBN
    private List<SelectItem> listaTipoEncuadernacionISBN;
    private List<SelectItem> listaTipoPapelISBN;
    private List<SelectItem> listaGramajeISBN;
    private List<SelectItem> listaTipoImpresionISBN;
    private List<SelectItem> listaNumTintasISBN;
    private List<SelectItem> listaMedElecISBN;
    private List<SelectItem> listaFormatoISBN;
    private List<SelectItem> listaTamañoISBN;
    private List<SelectItem> listaDescFisicaISBN;//Tipo producto
    
    private List<SelectItem> listaTipoSoporteISBN2018;
    
    private List<SelectItem> listaTipoObraISBN2018;
    private List<SelectItem> listaTipoPublicacionISBN;
    private List<SelectItem> listaAudienciaISBN;
    private List<SelectItem> listaTipoISBN;
    private List<SelectItem> listaTipoContenidoProducto;
    private List<SelectItem> listaProtTecnicaArcDigISBN;
    private List<SelectItem> listaPermisoUsoISBN;

    private List<SelectItem> listaCiudades;
    private List<SelectItem> listaDepartamentos;
    private boolean mostrarCoeditor = false;
    private boolean mostrarResena = false;

    private SelectItem[] paisItem;
    private String pais;
    private String rolAutorISBN;

    private Date fechaNacimiento;

    private String descripcionMateriasISBN;
    private String descripcionTipoContenidoISBN;
    private String descripcionIdiomaPrincipalISBN;
    private String descripcionIdiomaOriginalISBN;
    private String descripcionIdiomaOrigenISBN;
    private String descripcionIdiomaDestinoISBN;
    private String descripcionDepartamento;
    private String descripcionCiudad;

    private String descripcionRolAutorISBN;
    private String descripcionTipoSoporteISBN;
    private String descripcionTipoEncuadernacionISBN;
    private String descripcionTipoPapelISBN;
    private String descripcionGramajeISBN;
    private String descripcionTipoImpresionISBN;
    private String descripcionNumTintasISBN;
    private String descripcionMedElecISBN;
    private String descripcionFormatoISBN;
    private String descripcionTamañoISBN;
    private String descripcionDescFisicaISBN;
    
    //Clasificadores ISBN
    private SelectItem[] listaClasificacionTHEMA_N1;
    private SelectItem[] listaClasificacionTHEMA_N2;
    private SelectItem[] listaClasificacionTHEMA_N3;
    private SelectItem[] listaClasificacionTHEMA_N4;
    private SelectItem[] listaClasificacionTHEMA_N5;
    private SelectItem[] listaClasificacionTHEMA_N6;
    
    private SelectItem[] listaLugar_N1;
    private SelectItem[] listaLugar_N2;
    private SelectItem[] listaLugar_N3;
    private SelectItem[] listaLugar_N4;
    private SelectItem[] listaLugar_N5;
    private SelectItem[] listaLugar_N6;
    private SelectItem[] listaLugar_N7;
    private SelectItem[] listaLugar_N8;
    private SelectItem[] listaLugar_N9;
    
    private SelectItem[] listaIdioma_N1;
    private SelectItem[] listaIdioma_N2;
    private SelectItem[] listaIdioma_N3;
    private SelectItem[] listaIdioma_N4;
    private SelectItem[] listaIdioma_N5;
    
    private SelectItem[] listaPerHistorico_N1;
    private SelectItem[] listaPerHistorico_N2;
    private SelectItem[] listaPerHistorico_N3;
    private SelectItem[] listaPerHistorico_N4;
    private SelectItem[] listaPerHistorico_N5;
    
    private SelectItem[] listaFinDidactico_N1;
    private SelectItem[] listaFinDidactico_N2;
    private SelectItem[] listaFinDidactico_N3;
    private SelectItem[] listaFinDidactico_N4;
    private SelectItem[] listaFinDidactico_N5;
    
    private SelectItem[] listaEdadInteres_N1;
    private SelectItem[] listaEdadInteres_N2;
    private SelectItem[] listaEdadInteres_N3;
    private SelectItem[] listaEdadInteres_N4;
    
    private SelectItem[] listaEstilo_N1;
    private SelectItem[] listaEstilo_N2;
    
    private SelectItem[] listaDisponibilidad;
    private SelectItem[] listaTipoAcceso;
    
    public ManejadorSolicitudISBN() {
        super();
        idManejador = CONVOCATORIA_LIBROS;
        palabraClave = new PalabraClave();
        keyWord = new PalabraClave();

        investigadorProyectoNuevo = new InvestigadorProyecto();
        tipoDocumentoCoInv2 = new TipoDocumento();
        cargarTiposDocumento();
        cargarValoresIniciales();
        listaParticipantes = new ArrayList<InvestigadorProyecto>();
        listaParticipantesBorrados = new ArrayList<InvestigadorProyecto>();
        dependenciaAreaResponsabilidad = new DependenciaAreaResponsabilidad();
        listaAreasPrimSec = new ArrayList<AreaTematica>();

        listaAreas = new Vector();
        personaActual = new Persona();

        if ((proyectoActual.getModalidad().getTipo().getId().compareTo("CL") == 0)) {
            mostrarSiConvLibros = true;
            mostrarSiArticulosUno = false;
            mostrarSiArticulosDos = false;

            if (proyectoActual.getModalidad() instanceof Convocatoria) {

                RestriccionConvocatoria r = ((Convocatoria) proyectoActual.getModalidad()).getRestriccion();

                if (r != null) {
                    System.out.println(r.getId());
                    if (r.getId().equals("ART_MOD_1")) {
                        mostrarSiConvLibros = false;
                        mostrarSiArticulosUno = true;
                        mostrarSiArticulosDos = false;
                        cargarListaAreas();
                        constructorArticulosUno();
                    }
                    if (r.getId().equals("ART_MOD_2")) {
                        mostrarSiConvLibros = false;
                        mostrarSiArticulosDos = true;
                        mostrarSiArticulosUno = false;
                        cargarListaAreas();
                        constructorArticulosDos();
                    }

                }
            } else {
            }
        }

        if ((proyectoActual.getModalidad().getTipo().getId().equals("SIS"))) {
            mostrarSiConvLibros = false;
            mostrarSiArticulosUno = false;
            mostrarSiArticulosDos = false;
            mostrarSiSolicitudISBN = true;

            constructorSolicitudISBN();
        }

        if (proyectoActual.getId() != null) {

            proyectoActual = servicioProyecto.obtenerProyecto(proyectoActual.getId(), ProyectoDAOHibernate.TODO_POR_ID);

            proyectoExiste = true;
            // cargar area ciencia principal
            listaAreasPrimSec = servicioGeneral
                    .obtenerObjetos("select e from AreaTematica e where e.proyecto.id = " + proyectoActual.getId());

            for (int i = 0; i < listaAreasPrimSec.size(); i++) {
                AreaTematica at = (AreaTematica) listaAreasPrimSec.get(i);
                if (at.getTipo() == 1) {
                    areaCiencia = at.getProyectoAreaTematica().getIdentificador().getTipo();
                } else {
                    areaCienciaSec = at.getProyectoAreaTematica().getIdentificador().getTipo();
                }
            }

            // cargar area ciencia secundaria

            // cargar director del proyecto y participantes del proyecto

            listaInvestigadoresVista = proyectoActual.getObtenerListaInvestigadoresVista();

            if (listaInvestigadoresVista != null) {
                for (int i = 0; i < listaInvestigadoresVista.size(); i++) {
                    InvestigadorProyectoVista ipv = (InvestigadorProyectoVista) listaInvestigadoresVista.get(i);
                    InvestigadorProyecto ip = ipv.getInvestigadorProyecto();
                    listaParticipantes.add(ip);
                }
            } else {
                listaInvestigadoresVista = new ArrayList();
            }

            cambiarTipoEvento();

            // cargar descripciones dominios
            descripcionMateriasISBN = obtenerDescripcionDominioDetalle(proyectoActual.getMateriaISBN());
            descripcionTipoContenidoISBN = obtenerDescripcionDominioDetalle(proyectoActual.getTipoContenidoISBN());
            descripcionIdiomaPrincipalISBN = obtenerDescripcionDominioDetalle(proyectoActual.getIdiomaPrincipalISBN());
            descripcionIdiomaOriginalISBN = obtenerDescripcionDominioDetalle(proyectoActual.getIdiomaOriginalISBN());
            descripcionIdiomaOrigenISBN  = obtenerDescripcionDominioDetalle(proyectoActual.getIdiomaOrigen());
            descripcionIdiomaDestinoISBN = obtenerDescripcionDominioDetalle(proyectoActual.getIdiomaDestinoISBN());
            descripcionDepartamento = obtenerDescripcionDepartamento(proyectoActual.getDepartamentoEdicionISBN());
            descripcionCiudad = obtenerDescripcionCiudad(proyectoActual.getCiudadEdicionISBN());

            // descripcionRolAutorISBN =
            // obtenerDescripcionDominioDetalle(proyectoActual.getro);
            descripcionTipoSoporteISBN = obtenerDescripcionDominioDetalle(proyectoActual.getTipoSoporteISBN());
            descripcionTipoEncuadernacionISBN = obtenerDescripcionDominioDetalle(
                    proyectoActual.getTipoEncuadernacionISBN());
            descripcionTipoPapelISBN = obtenerDescripcionDominioDetalle(proyectoActual.getTipoPapelISBN());
            descripcionGramajeISBN = obtenerDescripcionDominioDetalle(proyectoActual.getGramajeISBN());
            descripcionTipoImpresionISBN = obtenerDescripcionDominioDetalle(proyectoActual.getTipoImpresionISBN());
            descripcionNumTintasISBN = obtenerDescripcionDominioDetalle(proyectoActual.getNumTintasISBN());
            descripcionMedElecISBN = obtenerDescripcionDominioDetalle(proyectoActual.getMedioElectronicoISBN());
            descripcionFormatoISBN = obtenerDescripcionDominioDetalle(proyectoActual.getFormatoISBN());
            descripcionTamañoISBN = obtenerDescripcionDominioDetalle(proyectoActual.getUnidadMedidaTamañoISBN());
            descripcionDescFisicaISBN = obtenerDescripcionDominioDetalle(proyectoActual.getDescripcionFisicaISBN());

        } else {
            proyectoExiste = false;
            proyectoActual.cambiarEstadoPersona(EstadoProyecto.INGRESANDO, cargarPersonaActual());
            proyectoActual.setFase(new Integer(0));
            proyectoActual.setDuracion(6);
            // ASIGNACION DEL INVESTIGADOR PRINCIPAL POR DEFECTO

            // InvestigadorProyecto investigadorProyecto = new
            // InvestigadorProyecto();
            // investigadorProyecto.setInvestigador(servicioPersona.obtenerInvestigador(((Persona)
            // sesion.getAttribute("persona")).getId()));
            // investigadorProyecto.setProyecto(proyectoActual);
            // TipoInvestigador ti = new TipoInvestigador();
            // ti = (TipoInvestigador) servicioGeneral.obtenerObjeto(new
            // TipoInvestigador(), InvestigadorProyecto.PRINCIPAL);
            // investigadorProyecto.setTipo(ti);
            // proyectoActual.adicionarInvestigadorProyecto(investigadorProyecto);
            // listaParticipantes.add(investigadorProyecto);
        }

    }

    public void constructorSolicitudISBN() {
        System.out.println("Entra a   --> constructorSolicitudISBN >--");

        Investigador investigadorActual = servicioPersona.obtenerInvestigador(((Persona) sesion.getAttribute("persona")).getId());

        personaActual = (Persona) sesion.getAttribute("persona");

        if (investigadorActual != null) {

            this.documentoCoinv = investigadorActual.getId().getDocumento();

            List listaDoc = servicioGeneral.obtenerObjetoXID("TipoDocumento",
                    investigadorActual.getId().getTipoDocumento());
            this.tipoDocumentoCoInv = (TipoDocumento) listaDoc.get(0);
            nombreCompletoPersonaActual = investigadorActual.getNombre1() + " " + investigadorActual.getNombre2() + " "
                    + investigadorActual.getApellido1() + " " + investigadorActual.getApellido2();

            ii = servicioPersona.obtenerInvestigadorInterno(investigadorActual.getId());
            if (ii != null) {
                nombreSede = ii.getDependencia().getSede().getNombre();
                try {
                    nombreFacultad = ii.getDependencia().getFacultad().getNombre();
                } catch (Exception e) {
                    nombreFacultad = "Sin dependencia asignada";
                }
                nombreDepartamento = ii.getDependencia().getNombre();
                emailPersonaActual = ii.getEmail();
                telefono = ii.getTelefono();
            } else {
                nombreSede = "";
                nombreFacultad = "";
                nombreDepartamento = "";
                emailPersonaActual = "";
                telefono = "";
            }

        }
    }

    public String obtenerDescripcionDominioDetalle(String domDetTipo) {

        String descripcion = "INFORMACIÓN NO ENCONTRADA";

        String hql = "select pp from DominioDetalle pp where pp.identificador.tipo = '" + domDetTipo + "'";
        List listaDom = servicioGeneral.obtenerObjetos(hql);
        if (listaDom.size() > 0) {
            DominioDetalle dom = (DominioDetalle) listaDom.get(0);
            descripcion = dom.getDescripcion();
        }

        return descripcion;
    }

    public String obtenerDescripcionDepartamento(String depto) {

        String descripcion = "INFORMACIÓN NO ENCONTRADA";

        String hql = "select pp from Departamento pp where pp.id = '" + depto + "'";
        List listaDom = servicioGeneral.obtenerObjetos(hql);
        if (listaDom.size() > 0) {
            Departamento dep = (Departamento) listaDom.get(0);
            descripcion = dep.getNombre();
        }

        return descripcion;
    }

    public String obtenerDescripcionCiudad(String ciudad) {

        String descripcion = "INFORMACIÓN NO ENCONTRADA";

        String hql = "select pp from Ciudad pp where pp.id = '" + ciudad + "'";
        List listaDom = servicioGeneral.obtenerObjetos(hql);
        if (listaDom.size() > 0) {
            Ciudad dep = (Ciudad) listaDom.get(0);
            descripcion = dep.getNombre();
        }

        return descripcion;
    }

    public void constructorArticulosDos() {
        Investigador investigadorActual = servicioPersona
                .obtenerInvestigador(((Persona) sesion.getAttribute("persona")).getId());

        personaActual = (Persona) sesion.getAttribute("persona");

        if (investigadorActual != null) {

            this.documentoCoinv = investigadorActual.getId().getDocumento();

            List listaDoc = servicioGeneral.obtenerObjetoXID("TipoDocumento",
                    investigadorActual.getId().getTipoDocumento());
            this.tipoDocumentoCoInv = (TipoDocumento) listaDoc.get(0);
            nombreCompletoPersonaActual = investigadorActual.getNombre1() + " " + investigadorActual.getNombre2() + " "
                    + investigadorActual.getApellido1() + " " + investigadorActual.getApellido2();

            ii = servicioPersona.obtenerInvestigadorInterno(investigadorActual.getId());
            if (ii != null) {
                nombreSede = ii.getDependencia().getSede().getNombre();
                try {
                    nombreFacultad = ii.getDependencia().getFacultad().getNombre();
                } catch (Exception e) {
                    nombreFacultad = "Sin dependencia asignada";
                }
                nombreDepartamento = ii.getDependencia().getNombre();
                emailPersonaActual = ii.getEmail();
                telefono = ii.getTelefono();
            } else {
                nombreSede = "";
                nombreFacultad = "";
                nombreDepartamento = "";
                emailPersonaActual = "";
                telefono = "";
            }

        }
    }

    public void constructorArticulosUno() {
        Investigador investigadorActual = servicioPersona
                .obtenerInvestigador(((Persona) sesion.getAttribute("persona")).getId());

        if (investigadorActual != null) {

            this.documentoCoinv = investigadorActual.getId().getDocumento();

            List listaDoc = servicioGeneral.obtenerObjetoXID("TipoDocumento",
                    investigadorActual.getId().getTipoDocumento());
            this.tipoDocumentoCoInv = (TipoDocumento) listaDoc.get(0);
            nombreCompletoPersonaActual = investigadorActual.getNombre1() + " " + investigadorActual.getNombre2() + " "
                    + investigadorActual.getApellido1() + " " + investigadorActual.getApellido2();

            ii = servicioPersona.obtenerInvestigadorInterno(investigadorActual.getId());
            if (ii != null) {
                nombreSede = ii.getDependencia().getSede().getNombre();
                try {
                    nombreFacultad = ii.getDependencia().getFacultad().getNombre();
                } catch (Exception e) {
                    nombreFacultad = "Sin dependencia asignada";
                }
                nombreDepartamento = ii.getDependencia().getNombre();
                emailPersonaActual = ii.getEmail();
                telefono = ii.getTelefono();
            } else {
                nombreSede = "";
                nombreFacultad = "";
                nombreDepartamento = "";
                emailPersonaActual = "";
                telefono = "";
            }

        }
    }

    private void cargarTiposDocumento() {
        listaTipoDocumento = servicioGeneral.obtenerListaObjetos("TipoDocumento");
        tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
        for (int i = 0; i < listaTipoDocumento.size(); i++) {
            TipoDocumento td = (TipoDocumento) listaTipoDocumento.get(i);
            tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
        }
    }

    public boolean yaHayPrincipal() {
        // if(proyectoActual.getListaInvestigadoresProyecto().size()>0)
        if (listaParticipantes.size() > 0) {
            // for(Iterator
            // it=proyectoActual.getInvestigadoresProyecto().iterator();it.hasNext();)
            for (Iterator it = listaParticipantes.iterator(); it.hasNext();) {
                InvestigadorProyecto ipc = (InvestigadorProyecto) it.next();
                // it.next();
                if (ipc.getTipo().getId().equals(TipoInvestigador.Principal)) {
                    return true;
                }
            }
        }
        return false;
        // return
        // servicioProyecto.tienePrincipalProyecto(proyectoActual.getId());
    }

    public void cambiarVinculacion() {
        System.out.println("otra vinculacion");

        if (tipoInvestigador.equals("AEL")) {
            esOtraVinculacion = true;
        } else {
            esOtraVinculacion = false;
        }

    }

    public List obtenerPalabraClavesSugeridas(String nombre) {
        return servicioGeneral.obtenerPalabrasClaveEmpezandoCon(nombre);
    }

    // DEFINICION DE FUNCIONES ESPECIFICAS DE LA CLASE
    public void insertarPalabraClave() {
        System.out.println("inserta " + palabraClave.getPalabra());
        boolean existePalabra = palabraClave.existePalabraEnSet(proyectoActual.getPalabrasClaves());
        if ((!palabraClave.getPalabra().equals("")) && (!existePalabra)) {
            PalabraClave pc = new PalabraClave();
            pc.setPalabra(palabraClave.getPalabra().toUpperCase());
            pc.setPalabraOriginal(palabraClave.getPalabra());
            try {
                PalabraClave pc1 = servicioGeneral.obtenerPalabraClave(pc.getPalabra());
                if (pc1 == null) {
                    pc.setIdioma("ES");
                    servicioGeneral.guardarObjeto(pc);
                } else {
                    pc = (PalabraClave) pc1.clone();
                }
                proyectoActual.adicionarPalabraClave(pc);
                palabraClave.setPalabra("");
                pc1 = null;
                pc = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        palabraClave = new PalabraClave();
    }

    public void eliminarPalabraClave() {
        proyectoActual.borrarPalabraClave(palabraClaveTabla);
        palabraClaveTabla = new PalabraClave();
    }

    public void adicionarDependencia() {

        Dependencia dep = buscarDependencia(dependenciaAdicionada);
        dependenciaAreaResponsabilidad.setDependencia(dep);
        proyectoActual.adicionarDependencia(dependenciaAreaResponsabilidad);
        dependenciaAreaResponsabilidad = new DependenciaAreaResponsabilidad();
    }

    public void eliminarDependencia() {
        // proyectoActual.borrarDependencia((DependenciaAreaResponsabilidad)tablaDependencias.getRowData());
        proyectoActual.borrarDependencia(dependenciaAreaResponsabilidadSeleccionada);
    }

    private Dependencia buscarDependencia(String id) {
        // BUSCA UNA DEPENDENCIA DE ACUERDO A SU ID
        Dependencia d = new Dependencia();
        int i = 0;
        while (i < dependenciasUN.size()) {
            d = (Dependencia) dependenciasUN.get(i);
            if (id.equals(d.getId()))
                break;
            i = i + 1;
        }
        return d;
    }

    public void cargarListaAreas() {
        listaAreas.add(new SelectItem("ASTRONOMY AND PLANETARY SCIENCE  - Astronomy",
                "ASTRONOMY AND PLANETARY SCIENCE  - Astronomy"));
    }

    @Override
    protected void cargarValoresIniciales() {

        linkLineas = "http://www.hermes.unal.edu.co/pages/descargas/AreasCiencia.pdf";

        String consulta1 = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
                + DOMINIO_AREA_CIENCIA + "'  order by dd.descripcion";
        listaAreaCiencia = servicioGeneral.obtenerObjetos(consulta1);

        if (listaAreaCiencia.size() > 0) {
            areaCienciaItems = new SelectItem[listaAreaCiencia.size()];
            for (int i = 0; i < listaAreaCiencia.size(); i++) {
                DominioDetalle dominio = (DominioDetalle) listaAreaCiencia.get(i);
                areaCienciaItems[i] = new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion());
            }
        } else {
            areaCienciaItems = new SelectItem[1];
            areaCienciaItems[0] = new SelectItem("0", " - ");

        }

        dependenciasUN = new ArrayList<Dependencia>();
        dependenciasUN = servicioGeneral.obtenerObjetos("select e from Dependencia e");
        dependenciaItem = new SelectItem[dependenciasUN.size() + 1];
        for (int i = 0; i < dependenciasUN.size(); i++) {
            Dependencia dd = (Dependencia) dependenciasUN.get(i);
            dependenciaItem[i] = new SelectItem(dd.getId(), dd.getNombre());
            dd = null;
        }
        dependenciaItem[dependenciasUN.size()] = new SelectItem("--", "Por favor seleccione la dependencia");
        dependenciaAdicionada = "--";

        List listaAutores = servicioGeneral
                .obtenerObjetos("select e from TipoInvestigador e where e.tipoModalidad = 'CL'");
        autores = new SelectItem[listaAutores.size()];
        for (int i = 0; i < listaAutores.size(); i++) {
            TipoInvestigador ta = (TipoInvestigador) listaAutores.get(i);
            autores[i] = new SelectItem(ta.getId(), ta.getNombre());
            ta = null;
        }

        listaTipoEvento = new ArrayList<DominioDetalle>();
        listaTipoEvento = servicioGeneral.obtenerObjetos(
                "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
                        + DOMINIO_TIPO_COLECCION + "' order by dd.descripcion");
        tipoEventoItems = new SelectItem[listaTipoEvento.size()];
        for (int i = 0; i < listaTipoEvento.size(); i++) {
            DominioDetalle dd = (DominioDetalle) listaTipoEvento.get(i);
            tipoEventoItems[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
            dd = null;
        }

        // ISBN
        cargarListasISBN();
        cargarPaises();

    }

    public void cargarListasISBN() {
        // 1-Materia
        String consulta1 = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
                + DOMINIO_ISBN_MATERIA + "'  order by dd.identificador.id";
        List list1 = servicioGeneral.obtenerObjetos(consulta1);

        if (list1.size() > 0) {
            listaMateriasISBN = new ArrayList<SelectItem>();
            listaMateriasISBN.add(new SelectItem("0", "Seleccione una opción ..."));
            for (int i = 0; i < list1.size(); i++) {
                DominioDetalle dominio = (DominioDetalle) list1.get(i);
                listaMateriasISBN.add(new SelectItem(dominio.getIdentificador().getTipo(),
                        dominio.getIdentificador().getTipo().substring(3) + " - " + dominio.getDescripcion()));
            }
        } else
            listaMateriasISBN.add(new SelectItem("0", " - "));

        // 2-Tipo Contenido
        String consulta2 = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
                + DOMINIO_ISBN_TIPO_CONTENIDO + "'  order by dd.observacion";
        List list2 = servicioGeneral.obtenerObjetos(consulta2);

        if (list2.size() > 0) {
            listaTipoContenidoISBN = new ArrayList<SelectItem>();
            listaTipoContenidoISBN.add(new SelectItem("0", "Seleccione una opción ..."));
            for (int i = 0; i < list2.size(); i++) {
                DominioDetalle dominio = (DominioDetalle) list2.get(i);
                listaTipoContenidoISBN
                        .add(new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion()));
            }
        } else
            listaTipoContenidoISBN.add(new SelectItem("0", " - "));

        // 3-Idioma
//        String consulta3 = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
//                + DOMINIO_ISBN_IDIOMA + "'  order by dd.descripcion";
//        List list3 = servicioGeneral.obtenerObjetos(consulta3);
//
//        if (list3.size() > 0) {
//            listaIdiomasISBN = new ArrayList<SelectItem>();
//            listaIdiomasISBN.add(new SelectItem("0", "Seleccione una opción ..."));
//            for (int i = 0; i < list3.size(); i++) {
//                DominioDetalle dominio = (DominioDetalle) list3.get(i);
//                listaIdiomasISBN.add(new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion()));
//            }
//        } else
//            listaIdiomasISBN.add(new SelectItem("0", " - "));

        // 4-Rol Autor
        // String consulta4 =
        // "select dd from Dominio d, DominioDetalle dd where d.id =
        // dd.identificador.id and d.tipo ='"
        // + DOMINIO_ISBN_ROL_AUTOR + "' order by dd.descripcion";
        // List list4 = servicioGeneral.obtenerObjetos(consulta4);
        //
        // if (list4.size() > 0)
        // {
        // listaRolAutorISBN = new ArrayList<SelectItem>();
        // listaRolAutorISBN.add(new
        // SelectItem("0","Seleccione una opción ..."));
        // for (int i = 0; i < list4.size(); i++) {
        // DominioDetalle dominio = (DominioDetalle) list4.get(i);
        // listaRolAutorISBN.add(new
        // SelectItem(dominio.getIdentificador().getTipo(),dominio.getDescripcion()));
        // }
        // } else
        // listaRolAutorISBN.add(new SelectItem("0", " - "));

        String consulta4 = "select dd from TipoInvestigador dd where dd.tipoModalidad = 'SIS' and dd.esVisible = 1 order by dd.nombre";
        List list4 = servicioGeneral.obtenerObjetos(consulta4);

        if (list4.size() > 0) {
            listaRolAutorISBN = new ArrayList<SelectItem>();
            listaRolAutorISBN.add(new SelectItem("0", "Seleccione una opción ..."));
            for (int i = 0; i < list4.size(); i++) {
                TipoInvestigador tipoInv = (TipoInvestigador) list4.get(i);
                listaRolAutorISBN.add(new SelectItem(tipoInv.getId(), tipoInv.getNombre()));
            }
        } else
            listaRolAutorISBN.add(new SelectItem("0", " - "));

        // 5-Tipo Soporte
        String consulta5 = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
                + DOMINIO_ISBN_TIPO_SOPORTE + "'  order by dd.descripcion";
        List list5 = servicioGeneral.obtenerObjetos(consulta5);

        if (list5.size() > 0) {
            listaTipoSoporteISBN = new ArrayList<SelectItem>();
            // listaTipoSoporteISBN.add(new
            // SelectItem("0","Seleccione una opción ..."));
            for (int i = 0; i < list5.size(); i++) {
                DominioDetalle dominio = (DominioDetalle) list5.get(i);
                listaTipoSoporteISBN
                        .add(new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion()));
            }
        } else
            listaTipoSoporteISBN.add(new SelectItem("0", " - "));

        // 6-Tipo Encuadernacion
//        String consulta6 = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
//                + DOMINIO_ISBN_TIPO_ENCUADERNACION + "'  order by dd.descripcion";
//        List list6 = servicioGeneral.obtenerObjetos(consulta6);
//
//        if (list6.size() > 0) {
//            listaTipoEncuadernacionISBN = new ArrayList<SelectItem>();
//            listaTipoEncuadernacionISBN.add(new SelectItem("0", "Seleccione una opción ..."));
//            for (int i = 0; i < list6.size(); i++) {
//                DominioDetalle dominio = (DominioDetalle) list6.get(i);
//                listaTipoEncuadernacionISBN
//                        .add(new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion()));
//            }
//        } else
//            listaTipoEncuadernacionISBN.add(new SelectItem("0", " - "));

        // 7-Tipo Papel
//        String consulta7 = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
//                + DOMINIO_ISBN_TIPO_PAPEL + "'  order by dd.descripcion";
//        List list7 = servicioGeneral.obtenerObjetos(consulta7);
//
//        if (list7.size() > 0) {
//            listaTipoPapelISBN = new ArrayList<SelectItem>();
//            listaTipoPapelISBN.add(new SelectItem("0", "Seleccione una opción ..."));
//            for (int i = 0; i < list7.size(); i++) {
//                DominioDetalle dominio = (DominioDetalle) list7.get(i);
//                listaTipoPapelISBN.add(new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion()));
//            }
//        } else
//            listaTipoPapelISBN.add(new SelectItem("0", " - "));

        // 8-Gramaje
        String consulta8 = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
                + DOMINIO_ISBN_GRAMAJE + "'  order by dd.descripcion";
        List list8 = servicioGeneral.obtenerObjetos(consulta8);

        if (list8.size() > 0) {
            listaGramajeISBN = new ArrayList<SelectItem>();
            listaGramajeISBN.add(new SelectItem("0", "Seleccione una opción ..."));
            for (int i = 0; i < list8.size(); i++) {
                DominioDetalle dominio = (DominioDetalle) list8.get(i);
                listaGramajeISBN.add(new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion()));
            }
        } else
            listaGramajeISBN.add(new SelectItem("0", " - "));

        // 9-Tipo Impresion
//        String consulta9 = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
//                + DOMINIO_ISBN_TIPO_IMPRESION + "'  order by dd.descripcion";
//        List list9 = servicioGeneral.obtenerObjetos(consulta9);
//
//        if (list9.size() > 0) {
//            listaTipoImpresionISBN = new ArrayList<SelectItem>();
//            listaTipoImpresionISBN.add(new SelectItem("0", "Seleccione una opción ..."));
//            for (int i = 0; i < list9.size(); i++) {
//                DominioDetalle dominio = (DominioDetalle) list9.get(i);
//                listaTipoImpresionISBN
//                        .add(new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion()));
//            }
//        } else
//            listaTipoImpresionISBN.add(new SelectItem("0", " - "));

        // 10-Numero Tintas
        String consulta10 = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
                + DOMINIO_ISBN_NUM_TINTAS + "'  order by dd.descripcion";
        List list10 = servicioGeneral.obtenerObjetos(consulta10);

        if (list10.size() > 0) {
            listaNumTintasISBN = new ArrayList<SelectItem>();
            listaNumTintasISBN.add(new SelectItem("0", "Seleccione una opción ..."));
            for (int i = 0; i < list10.size(); i++) {
                DominioDetalle dominio = (DominioDetalle) list10.get(i);
                listaNumTintasISBN.add(new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion()));
            }
        } else
            listaNumTintasISBN.add(new SelectItem("0", " - "));

        // 11-Medio Electronico
        String consulta11 = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
                + DOMINIO_ISBN_MEDIO_ELECTRONICO + "'  order by dd.descripcion";
        List list11 = servicioGeneral.obtenerObjetos(consulta11);

        if (list11.size() > 0) {
            listaMedElecISBN = new ArrayList<SelectItem>();
            listaMedElecISBN.add(new SelectItem("0", "Seleccione una opción ..."));
            for (int i = 0; i < list11.size(); i++) {
                DominioDetalle dominio = (DominioDetalle) list11.get(i);
                listaMedElecISBN.add(new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion()));
            }
        } else
            listaMedElecISBN.add(new SelectItem("0", " - "));

        // 12-Formato
//        String consulta12 = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
//                + DOMINIO_ISBN_FORMATO + "'  order by dd.descripcion";
//        List list12 = servicioGeneral.obtenerObjetos(consulta12);
//
//        if (list12.size() > 0) {
//            listaFormatoISBN = new ArrayList<SelectItem>();
//            listaFormatoISBN.add(new SelectItem("0", "Seleccione una opción ..."));
//            for (int i = 0; i < list12.size(); i++) {
//                DominioDetalle dominio = (DominioDetalle) list12.get(i);
//                listaFormatoISBN.add(new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion()));
//            }
//        } else
//            listaFormatoISBN.add(new SelectItem("0", " - "));

        // 13-Tamaño
        String consulta13 = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
                + DOMINIO_ISBN_TAMAÑO + "'  order by dd.descripcion";
        List list13 = servicioGeneral.obtenerObjetos(consulta13);

        if (list13.size() > 0) {
            listaTamañoISBN = new ArrayList<SelectItem>();
            listaTamañoISBN.add(new SelectItem("0", "--"));
            for (int i = 0; i < list13.size(); i++) {
                DominioDetalle dominio = (DominioDetalle) list13.get(i);
                listaTamañoISBN.add(new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion()));
            }
        } else
            listaTamañoISBN.add(new SelectItem("0", " - "));

        // 14-Descripcion Fisica
//        String consulta14 = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
//                + DOMINIO_ISBN_DESC_FISICA + "'  order by dd.descripcion";
//        List list14 = servicioGeneral.obtenerObjetos(consulta14);
//
//        if (list14.size() > 0) {
//            listaDescFisicaISBN = new ArrayList<SelectItem>();
//            listaDescFisicaISBN.add(new SelectItem("0", "Seleccione una opción ..."));
//            for (int i = 0; i < list14.size(); i++) {
//                DominioDetalle dominio = (DominioDetalle) list14.get(i);
//                listaDescFisicaISBN.add(new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion()));
//            }
//        } else
//            listaDescFisicaISBN.add(new SelectItem("0", " - "));

        // A-Departamentos
        String pais = "CO";
        String consultaA = "select dd from Departamento dd where dd.id like '%" + pais + "%' ";
        List listA = servicioGeneral.obtenerObjetos(consultaA);

        if (listA.size() > 0) {
            listaDepartamentos = new ArrayList<SelectItem>();
            listaDepartamentos.add(new SelectItem("0", "Seleccione una opción ..."));
            for (int i = 0; i < listA.size(); i++) {
                Departamento depto = (Departamento) listA.get(i);
                listaDepartamentos.add(new SelectItem(depto.getId(), depto.getNombre()));
            }
        } else
            listaDepartamentos.add(new SelectItem("0", " - "));

        // B-Ciudades
        listaCiudades = new ArrayList<SelectItem>();

        if (proyectoActual.getDepartamentoEdicionISBN() == null
                || proyectoActual.getDepartamentoEdicionISBN().equals(""))
            listaCiudades.add(new SelectItem("0", "Seleccione un departmento ..."));
        else
            cargarListaCiudades();
        
      //Ajustes ISBN DIC 2018
        listaIdiomasISBN = servicioGeneral.obtenerDominioDetalleSelectItem(DOMINIO_ISBN_IDIOMA_2018,false);
        listaDescFisicaISBN = servicioGeneral.obtenerDominioDetalleSelectItem(DOMINIO_ISBN_TIPO_PRODUCTO_2018,false);
        listaTipoEncuadernacionISBN = servicioGeneral.obtenerDominioDetalleSelectItem(DOMINIO_ISBN_TIPO_ENCUADERNACION_2018,false);
        listaTipoPapelISBN = servicioGeneral.obtenerDominioDetalleSelectItem(DOMINIO_ISBN_TIPO_PAPEL_2018,false);
        listaTipoImpresionISBN = servicioGeneral.obtenerDominioDetalleSelectItem(DOMINIO_ISBN_TIPO_IMPRESION_2018,false);
        listaFormatoISBN = servicioGeneral.obtenerDominioDetalleSelectItem(DOMINIO_ISBN_TIPO_FORMATO_2018,false);
        
        
        listaTipoObraISBN2018 = servicioGeneral.obtenerDominioDetalleSelectItem(DOMINIO_ISBN_TIPO_OBRA_2018,false);
        listaTipoPublicacionISBN = servicioGeneral.obtenerDominioDetalleSelectItem(DOMINIO_ISBN_TIPO_PUBLICACION_2018,false);
        listaAudienciaISBN = servicioGeneral.obtenerDominioDetalleSelectItem(DOMINIO_ISBN_AUDIENCIA_2018,false);
        listaTipoISBN = servicioGeneral.obtenerDominioDetalleSelectItem(DOMINIO_ISBN_TIPO_ISBN_2018,false);
        listaTipoContenidoProducto = servicioGeneral.obtenerDominioDetalleSelectItem(DOMINIO_ISBN_TIPO_CONTENIDO_PRODUCTO_2018,false);
        listaProtTecnicaArcDigISBN = servicioGeneral.obtenerDominioDetalleSelectItem(DOMINIO_ISBN_TIPO_PROC_TEC_ARC_2018,false);
        listaPermisoUsoISBN = servicioGeneral.obtenerDominioDetalleSelectItem(DOMINIO_ISBN_PERMISOS_USO_2018,false);

        listaTipoSoporteISBN2018 = servicioGeneral.obtenerDominioDetalleSelectItem(DOMINIO_ISBN_TIPO_SOPORTE_2018,false);
        
        cargarListasCalificadores();
        
        listaDisponibilidad = servicioGeneral.retornaSelectItemArregloDeHijosDeTipos(Tipos.TIPOS_ISBN_DISPONIBILIDAD);
        listaTipoAcceso = servicioGeneral.retornaSelectItemArregloDeHijosDeTipos(Tipos.TIPOS_ISBN_TIPO_ACCESO);
				 	
    }
    
    public void cargarListasCalificadores()
    {
        //THEMA
    	listaClasificacionTHEMA_N1 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPOS_ISBN_CLASIFICACION_THEMA);
        
        if(proyectoActual.getClasificacionThemaISBNNivel2() != null)
        {	
        	listaClasificacionTHEMA_N2 = !proyectoActual.getClasificacionThemaISBNNivel2().equals(0L) 
        									|| (proyectoActual.getClasificacionThemaISBNNivel2().equals(0L) && !proyectoActual.getClasificacionThemaISBNNivel1().equals(0L))? 
        											servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionThemaISBNNivel1()) : null ;
        }
        
        if(proyectoActual.getClasificacionThemaISBNNivel3() != null)
        {
        	listaClasificacionTHEMA_N3 = !proyectoActual.getClasificacionThemaISBNNivel3().equals(0L)
        									|| (proyectoActual.getClasificacionThemaISBNNivel3().equals(0L) && !proyectoActual.getClasificacionThemaISBNNivel2().equals(0L))?
        											servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionThemaISBNNivel2()) : null ;
        }
		
		if(proyectoActual.getClasificacionThemaISBNNivel4() != null)
		{		 					 
			listaClasificacionTHEMA_N4 = !proyectoActual.getClasificacionThemaISBNNivel4().equals(0L)
											|| (proyectoActual.getClasificacionThemaISBNNivel4().equals(0L) && !proyectoActual.getClasificacionThemaISBNNivel3().equals(0L))?
													servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionThemaISBNNivel3()) : null ;
		}
		
		if(proyectoActual.getClasificacionThemaISBNNivel5() != null)
	    {
			listaClasificacionTHEMA_N5 = !proyectoActual.getClasificacionThemaISBNNivel5().equals(0L)
											|| (proyectoActual.getClasificacionThemaISBNNivel5().equals(0L) && !proyectoActual.getClasificacionThemaISBNNivel4().equals(0L))?
													servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionThemaISBNNivel4()) : null ;
	    }
		
		if(proyectoActual.getClasificacionThemaISBNNivel6() != null)
        {		 					 
			listaClasificacionTHEMA_N6 = !proyectoActual.getClasificacionThemaISBNNivel6().equals(0L)
											|| (proyectoActual.getClasificacionThemaISBNNivel6().equals(0L) && !proyectoActual.getClasificacionThemaISBNNivel5().equals(0L))?
													servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionThemaISBNNivel5()) : null ;
        }
		
		//LUGAR
		listaLugar_N1 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPOS_ISBN_CALIFICADOR_1_LUGAR);
        
        if(proyectoActual.getClasificacionLugarISBNNivel2() != null)
        {	
        	listaLugar_N2 = !proyectoActual.getClasificacionLugarISBNNivel2().equals(0L)
        						|| (proyectoActual.getClasificacionLugarISBNNivel2().equals(0L) && !proyectoActual.getClasificacionLugarISBNNivel1().equals(0L))?
        							 servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionLugarISBNNivel1()) : null ;
        }
        
        if(proyectoActual.getClasificacionLugarISBNNivel3() != null)
        {	
        	listaLugar_N3 = !proyectoActual.getClasificacionLugarISBNNivel3().equals(0L)
        						|| (proyectoActual.getClasificacionLugarISBNNivel3().equals(0L) && !proyectoActual.getClasificacionLugarISBNNivel2().equals(0L))?
        							 servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionLugarISBNNivel2()) : null ;
        }
        
        if(proyectoActual.getClasificacionLugarISBNNivel4() != null)
        {	
        	listaLugar_N4 = !proyectoActual.getClasificacionLugarISBNNivel4().equals(0L)
        						|| (proyectoActual.getClasificacionLugarISBNNivel4().equals(0L) && !proyectoActual.getClasificacionLugarISBNNivel3().equals(0L))?
        							 servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionLugarISBNNivel3()) : null ;
        }
        
        if(proyectoActual.getClasificacionLugarISBNNivel5() != null)
        {	
        	listaLugar_N5 = !proyectoActual.getClasificacionLugarISBNNivel5().equals(0L)
        						|| (proyectoActual.getClasificacionLugarISBNNivel5().equals(0L) && !proyectoActual.getClasificacionLugarISBNNivel4().equals(0L))?
        							 servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionLugarISBNNivel4()) : null ;
        }        
        
        if(proyectoActual.getClasificacionLugarISBNNivel6() != null)
        {	
        	listaLugar_N6 = !proyectoActual.getClasificacionLugarISBNNivel6().equals(0L)
        						|| (proyectoActual.getClasificacionLugarISBNNivel6().equals(0L) && !proyectoActual.getClasificacionLugarISBNNivel5().equals(0L))?
        							 servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionLugarISBNNivel5()) : null ;
        }
        
        if(proyectoActual.getClasificacionLugarISBNNivel7() != null)
        {	
        	listaLugar_N7 = !proyectoActual.getClasificacionLugarISBNNivel7().equals(0L)
        						|| (proyectoActual.getClasificacionLugarISBNNivel7().equals(0L) && !proyectoActual.getClasificacionLugarISBNNivel6().equals(0L))?
        							 servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionLugarISBNNivel6()) : null ;
        }
        
        if(proyectoActual.getClasificacionLugarISBNNivel8() != null)
        {	
        	listaLugar_N8 = !proyectoActual.getClasificacionLugarISBNNivel8().equals(0L)
        						|| (proyectoActual.getClasificacionLugarISBNNivel8().equals(0L) && !proyectoActual.getClasificacionLugarISBNNivel7().equals(0L))?
        							 servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionLugarISBNNivel7()) : null ;
        }
        
        if(proyectoActual.getClasificacionLugarISBNNivel9() != null)
        {	
        	listaLugar_N9 = !proyectoActual.getClasificacionLugarISBNNivel9().equals(0L)
        						|| (proyectoActual.getClasificacionLugarISBNNivel9().equals(0L) && !proyectoActual.getClasificacionLugarISBNNivel8().equals(0L))?
        							 servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionLugarISBNNivel8()) : null ;
        }
        
      //IDIOMA
    	listaIdioma_N1 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPOS_ISBN_CALIFICADOR_2_LENGUA);
        
        if(proyectoActual.getClasificacionIdiomaISBNNivel2() != null)
        {	
        	listaIdioma_N2 = !proyectoActual.getClasificacionIdiomaISBNNivel2().equals(0L)
        						|| (proyectoActual.getClasificacionIdiomaISBNNivel2().equals(0L) && !proyectoActual.getClasificacionIdiomaISBNNivel1().equals(0L))?
        							 	  servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionIdiomaISBNNivel1()) : null ;
        }
        
        if(proyectoActual.getClasificacionIdiomaISBNNivel3() != null)
        {
        	listaIdioma_N3 = !proyectoActual.getClasificacionIdiomaISBNNivel3().equals(0L)
        						|| (proyectoActual.getClasificacionIdiomaISBNNivel3().equals(0L) && !proyectoActual.getClasificacionIdiomaISBNNivel2().equals(0L))?
				 					 servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionIdiomaISBNNivel2()) : null ;
        }
		
		if(proyectoActual.getClasificacionIdiomaISBNNivel4() != null)
		{		 					 
			listaIdioma_N4 = !proyectoActual.getClasificacionIdiomaISBNNivel4().equals(0L)
								|| (proyectoActual.getClasificacionIdiomaISBNNivel4().equals(0L) && !proyectoActual.getClasificacionIdiomaISBNNivel3().equals(0L))?
				 					 servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionIdiomaISBNNivel3()) : null ;
		}
		
		if(proyectoActual.getClasificacionIdiomaISBNNivel5() != null)
	    {
			listaIdioma_N5 = !proyectoActual.getClasificacionIdiomaISBNNivel5().equals(0L)
								|| (proyectoActual.getClasificacionIdiomaISBNNivel5().equals(0L) && !proyectoActual.getClasificacionIdiomaISBNNivel4().equals(0L))?
				 					 servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionIdiomaISBNNivel4()) : null ;
	    }
		
		//PERIODO_HISTORICO
    	listaPerHistorico_N1 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPOS_ISBN_CALIFICADOR_3_PERIODO_HISTORICO);
        
        if(proyectoActual.getClasificacionPerHistoricoISBNNivel2() != null)
        {	
        	listaPerHistorico_N2 = !proyectoActual.getClasificacionPerHistoricoISBNNivel2().equals(0L)
        								|| (proyectoActual.getClasificacionPerHistoricoISBNNivel2().equals(0L) && !proyectoActual.getClasificacionPerHistoricoISBNNivel1().equals(0L))?
        										servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionPerHistoricoISBNNivel1()) : null ;
        }
        
        if(proyectoActual.getClasificacionPerHistoricoISBNNivel3() != null)
        {
        	listaPerHistorico_N3 = !proyectoActual.getClasificacionPerHistoricoISBNNivel3().equals(0L)
        								|| (proyectoActual.getClasificacionPerHistoricoISBNNivel3().equals(0L) && !proyectoActual.getClasificacionPerHistoricoISBNNivel2().equals(0L))?
        										servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionPerHistoricoISBNNivel2()) : null ;
        }
		
		if(proyectoActual.getClasificacionPerHistoricoISBNNivel4() != null)
		{		 					 
			listaPerHistorico_N4 = !proyectoActual.getClasificacionPerHistoricoISBNNivel4().equals(0L)
										|| (proyectoActual.getClasificacionPerHistoricoISBNNivel4().equals(0L) && !proyectoActual.getClasificacionPerHistoricoISBNNivel3().equals(0L))?
												servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionPerHistoricoISBNNivel3()) : null ;
		}
		
		if(proyectoActual.getClasificacionPerHistoricoISBNNivel5() != null)
	    {
			listaPerHistorico_N5 = !proyectoActual.getClasificacionPerHistoricoISBNNivel5().equals(0L)
										|| (proyectoActual.getClasificacionPerHistoricoISBNNivel5().equals(0L) && !proyectoActual.getClasificacionPerHistoricoISBNNivel4().equals(0L))?
												servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionPerHistoricoISBNNivel4()) : null ;
	    }
		
		//FIN_DIDACTICO
    	listaFinDidactico_N1 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPOS_ISBN_CALIFICADOR_4_FIN_DIDACTICO);
        
        if(proyectoActual.getClasificacionFinDidacticoISBNNivel2() != null)
        {	
        	listaFinDidactico_N2 = !proyectoActual.getClasificacionFinDidacticoISBNNivel2().equals(0L)
										|| (proyectoActual.getClasificacionFinDidacticoISBNNivel2().equals(0L) && !proyectoActual.getClasificacionFinDidacticoISBNNivel1().equals(0L))?
												servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionFinDidacticoISBNNivel1()) : null ;
        }
        
        if(proyectoActual.getClasificacionFinDidacticoISBNNivel3() != null)
        {
        	listaFinDidactico_N3 = !proyectoActual.getClasificacionFinDidacticoISBNNivel3().equals(0L)
        								|| (proyectoActual.getClasificacionFinDidacticoISBNNivel3().equals(0L) && !proyectoActual.getClasificacionFinDidacticoISBNNivel2().equals(0L))?
        										servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionFinDidacticoISBNNivel2()) : null ;
        }
		
		if(proyectoActual.getClasificacionFinDidacticoISBNNivel4() != null)
		{		 					 
			listaFinDidactico_N4 = !proyectoActual.getClasificacionFinDidacticoISBNNivel4().equals(0L)
										|| (proyectoActual.getClasificacionFinDidacticoISBNNivel4().equals(0L) && !proyectoActual.getClasificacionFinDidacticoISBNNivel3().equals(0L))?
												servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionFinDidacticoISBNNivel3()) : null ;
		}
		
		if(proyectoActual.getClasificacionFinDidacticoISBNNivel5() != null)
	    {
			listaFinDidactico_N5 = !proyectoActual.getClasificacionFinDidacticoISBNNivel5().equals(0L)
										|| (proyectoActual.getClasificacionFinDidacticoISBNNivel5().equals(0L) && !proyectoActual.getClasificacionFinDidacticoISBNNivel4().equals(0L))?
												servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionFinDidacticoISBNNivel4()) : null ;
	    }
		
		//EDAD_INTERES_INTE_ESPECIALES
    	listaEdadInteres_N1 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPOS_ISBN_CALIFICADOR_5_EDAD_INTERES_INTE_ESPECIALES);
        
        if(proyectoActual.getClasificacionEdadInteresISBNNivel2() != null)
        {	
        	listaEdadInteres_N2 = !proyectoActual.getClasificacionEdadInteresISBNNivel2().equals(0L)
        								|| (proyectoActual.getClasificacionEdadInteresISBNNivel2().equals(0L) && !proyectoActual.getClasificacionEdadInteresISBNNivel1().equals(0L))?
        							 	  servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionEdadInteresISBNNivel1()) : null ;
        }
        
        if(proyectoActual.getClasificacionEdadInteresISBNNivel3() != null)
        {
        	listaEdadInteres_N3 = !proyectoActual.getClasificacionEdadInteresISBNNivel3().equals(0L)
										|| (proyectoActual.getClasificacionEdadInteresISBNNivel3().equals(0L) && !proyectoActual.getClasificacionEdadInteresISBNNivel2().equals(0L))?			
												servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionEdadInteresISBNNivel2()) : null ;
        }
		
		if(proyectoActual.getClasificacionEdadInteresISBNNivel4() != null)
		{		 					 
			listaEdadInteres_N4 = !proyectoActual.getClasificacionEdadInteresISBNNivel4().equals(0L)
										|| (proyectoActual.getClasificacionEdadInteresISBNNivel4().equals(0L) && !proyectoActual.getClasificacionEdadInteresISBNNivel3().equals(0L))?
												servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionEdadInteresISBNNivel3()) : null ;
		}
		
		//ESTILO
    	listaEstilo_N1 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPOS_ISBN_CALIFICADOR_6_ESTILO);
        
        if(proyectoActual.getClasificacionEstiloISBNNivel2() != null)
        {	
        	listaEstilo_N2 = !proyectoActual.getClasificacionEstiloISBNNivel2().equals(0L)
								|| (proyectoActual.getClasificacionEstiloISBNNivel2().equals(0L) && !proyectoActual.getClasificacionEstiloISBNNivel1().equals(0L))?
        							 	  servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionEstiloISBNNivel1()) : null ;
        }		
		
    }
    
    public void cambiarClaThemaN1() {
		if (!proyectoActual.getClasificacionThemaISBNNivel1().equals("0"))
			listaClasificacionTHEMA_N2 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionThemaISBNNivel1());
		
		proyectoActual.setClasificacionThemaISBNNivel2(null);
		listaClasificacionTHEMA_N3 = null;
		cambiarClaThemaN2();
	}
    
    public void cambiarClaThemaN2() {
    	if(proyectoActual.getClasificacionThemaISBNNivel2() != null)
    	{	
			if (!proyectoActual.getClasificacionThemaISBNNivel2().equals("0"))
				listaClasificacionTHEMA_N3 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionThemaISBNNivel2());
    	}
		proyectoActual.setClasificacionThemaISBNNivel3(null);
		listaClasificacionTHEMA_N4 = null;
		cambiarClaThemaN3();
	}

    public void cambiarClaThemaN3() {
    	if(proyectoActual.getClasificacionThemaISBNNivel3() != null)
    	{
    		if (!proyectoActual.getClasificacionThemaISBNNivel3().equals("0"))
    			listaClasificacionTHEMA_N4 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionThemaISBNNivel3());
    	}	
		proyectoActual.setClasificacionThemaISBNNivel4(null);
		listaClasificacionTHEMA_N5 = null;
		cambiarClaThemaN4();
	}
    
    public void cambiarClaThemaN4() {
    	if(proyectoActual.getClasificacionThemaISBNNivel4() != null)
    	{
			if (!proyectoActual.getClasificacionThemaISBNNivel4().equals("0"))
				listaClasificacionTHEMA_N5 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionThemaISBNNivel4());
    	}		
		proyectoActual.setClasificacionThemaISBNNivel5(null);
		listaClasificacionTHEMA_N6 = null;
		cambiarClaThemaN5();
	}
    
    public void cambiarClaThemaN5() {
    	if(proyectoActual.getClasificacionThemaISBNNivel5() != null)
    	{
    		if (!proyectoActual.getClasificacionThemaISBNNivel5().equals("0"))
    			listaClasificacionTHEMA_N6 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionThemaISBNNivel5());
    	}
    	proyectoActual.setClasificacionThemaISBNNivel6(null);
	}
    
    public void cambiarClaLugarN1() {
		if (!proyectoActual.getClasificacionLugarISBNNivel1().equals("0"))
			listaLugar_N2 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionLugarISBNNivel1());
		
		proyectoActual.setClasificacionLugarISBNNivel2(null);
		listaLugar_N3 = null;
		cambiarClaLugarN2();
	}
    
    public void cambiarClaLugarN2() {
    	if(proyectoActual.getClasificacionLugarISBNNivel2() != null)
    	{	
			if (!proyectoActual.getClasificacionLugarISBNNivel2().equals("0"))
				listaLugar_N3 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionLugarISBNNivel2());
    	}
		proyectoActual.setClasificacionLugarISBNNivel3(null);
		listaLugar_N4 = null;
		cambiarClaLugarN3();
	}

    public void cambiarClaLugarN3() {
    	if(proyectoActual.getClasificacionLugarISBNNivel3() != null)
    	{
    		if (!proyectoActual.getClasificacionLugarISBNNivel3().equals("0"))
    			listaLugar_N4 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionLugarISBNNivel3());
    	}	
		proyectoActual.setClasificacionLugarISBNNivel4(null);
		listaLugar_N5 = null;
		cambiarClaLugarN4();
	}
    
    public void cambiarClaLugarN4() {
    	if(proyectoActual.getClasificacionLugarISBNNivel4() != null)
    	{
			if (!proyectoActual.getClasificacionLugarISBNNivel4().equals("0"))
				listaLugar_N5 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionLugarISBNNivel4());
    	}		
		proyectoActual.setClasificacionLugarISBNNivel5(null);
		listaLugar_N6 = null;
		cambiarClaLugarN5();
	}
	
	public void cambiarClaLugarN5() {
		if(proyectoActual.getClasificacionLugarISBNNivel5() != null)
    	{
			if (!proyectoActual.getClasificacionLugarISBNNivel5().equals("0"))
				listaLugar_N6 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionLugarISBNNivel5());
    	}		
		proyectoActual.setClasificacionLugarISBNNivel6(null);
		listaLugar_N7 = null;
		cambiarClaLugarN6();
	}
	
	public void cambiarClaLugarN6() {
		if(proyectoActual.getClasificacionLugarISBNNivel6() != null)
    	{
			if (!proyectoActual.getClasificacionLugarISBNNivel6().equals("0"))
				listaLugar_N7 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionLugarISBNNivel6());
    	}		
		proyectoActual.setClasificacionLugarISBNNivel7(null);
		listaLugar_N8 = null;
		cambiarClaLugarN7();
	}
	
	public void cambiarClaLugarN7() {
		if(proyectoActual.getClasificacionLugarISBNNivel7() != null)
    	{
			if (!proyectoActual.getClasificacionLugarISBNNivel7().equals("0"))
				listaLugar_N8 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionLugarISBNNivel7());
    	}		
		proyectoActual.setClasificacionLugarISBNNivel8(null);
		listaLugar_N9 = null;
		cambiarClaLugarN8();
	}
    
    public void cambiarClaLugarN8() {
    	if(proyectoActual.getClasificacionLugarISBNNivel8() != null)
    	{
    		if (!proyectoActual.getClasificacionLugarISBNNivel8().equals("0"))
    			listaLugar_N9 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionLugarISBNNivel8());
    	}
    	proyectoActual.setClasificacionLugarISBNNivel9(null);
	}
    
    public void cambiarClaIdiomaN1() {
		if (!proyectoActual.getClasificacionIdiomaISBNNivel1().equals("0"))
			listaIdioma_N2 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionIdiomaISBNNivel1());
		
		proyectoActual.setClasificacionIdiomaISBNNivel2(null);
		listaIdioma_N3 = null;
		cambiarClaIdiomaN2();
	}
    
    public void cambiarClaIdiomaN2() {
    	if(proyectoActual.getClasificacionIdiomaISBNNivel2() != null)
    	{	
			if (!proyectoActual.getClasificacionIdiomaISBNNivel2().equals("0"))
				listaIdioma_N3 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionIdiomaISBNNivel2());
    	}
		proyectoActual.setClasificacionIdiomaISBNNivel3(null);
		listaIdioma_N4 = null;
		cambiarClaIdiomaN3();
	}

    public void cambiarClaIdiomaN3() {
    	if(proyectoActual.getClasificacionIdiomaISBNNivel3() != null)
    	{
    		if (!proyectoActual.getClasificacionIdiomaISBNNivel3().equals("0"))
    			listaIdioma_N4 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionIdiomaISBNNivel3());
    	}	
		proyectoActual.setClasificacionIdiomaISBNNivel4(null);
		listaIdioma_N5 = null;
		cambiarClaIdiomaN4();
	}
    
    public void cambiarClaIdiomaN4() {
    	if(proyectoActual.getClasificacionIdiomaISBNNivel4() != null)
    	{
    		if (!proyectoActual.getClasificacionIdiomaISBNNivel4().equals("0"))
    			listaIdioma_N5 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionIdiomaISBNNivel4());
    	}
    	proyectoActual.setClasificacionIdiomaISBNNivel5(null);
	}
    
    public void cambiarPerHistoN1() {
		if (!proyectoActual.getClasificacionPerHistoricoISBNNivel1().equals("0"))
			listaPerHistorico_N2 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionPerHistoricoISBNNivel1());
		
		proyectoActual.setClasificacionPerHistoricoISBNNivel2(null);
		listaPerHistorico_N3 = null;
		cambiarPerHistoN2();
	}
    
    public void cambiarPerHistoN2() {
    	if(proyectoActual.getClasificacionPerHistoricoISBNNivel2() != null)
    	{	
			if (!proyectoActual.getClasificacionPerHistoricoISBNNivel2().equals("0"))
				listaPerHistorico_N3 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionPerHistoricoISBNNivel2());
    	}
		proyectoActual.setClasificacionPerHistoricoISBNNivel3(null);
		listaPerHistorico_N4 = null;
		cambiarPerHistoN3();
	}

    public void cambiarPerHistoN3() {
    	if(proyectoActual.getClasificacionPerHistoricoISBNNivel3() != null)
    	{
    		if (!proyectoActual.getClasificacionPerHistoricoISBNNivel3().equals("0"))
    			listaPerHistorico_N4 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionPerHistoricoISBNNivel3());
    	}	
		proyectoActual.setClasificacionPerHistoricoISBNNivel4(null);
		listaPerHistorico_N5 = null;
		cambiarPerHistoN4();
	}
    
    public void cambiarPerHistoN4() {
    	if(proyectoActual.getClasificacionPerHistoricoISBNNivel4() != null)
    	{
    		if (!proyectoActual.getClasificacionPerHistoricoISBNNivel4().equals("0"))
    			listaPerHistorico_N5 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionPerHistoricoISBNNivel4());
    	}
    	proyectoActual.setClasificacionPerHistoricoISBNNivel5(null);
	}
    
    public void cambiarClaFinDidacN1() {
		if (!proyectoActual.getClasificacionFinDidacticoISBNNivel1().equals("0"))
			listaFinDidactico_N2 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionFinDidacticoISBNNivel1());
		
		proyectoActual.setClasificacionFinDidacticoISBNNivel2(null);
		listaFinDidactico_N3 = null;
		cambiarClaFinDidacN2();
	}
    
    public void cambiarClaFinDidacN2() {
    	if(proyectoActual.getClasificacionFinDidacticoISBNNivel2() != null)
    	{	
			if (!proyectoActual.getClasificacionFinDidacticoISBNNivel2().equals("0"))
				listaFinDidactico_N3 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionFinDidacticoISBNNivel2());
    	}
		proyectoActual.setClasificacionFinDidacticoISBNNivel3(null);
		listaFinDidactico_N4 = null;
		cambiarClaFinDidacN3();
	}

    public void cambiarClaFinDidacN3() {
    	if(proyectoActual.getClasificacionFinDidacticoISBNNivel3() != null)
    	{
    		if (!proyectoActual.getClasificacionFinDidacticoISBNNivel3().equals("0"))
    			listaFinDidactico_N4 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionFinDidacticoISBNNivel3());
    	}	
		proyectoActual.setClasificacionFinDidacticoISBNNivel4(null);
		listaFinDidactico_N5 = null;
		cambiarClaFinDidacN4();
	}
    
    
    public void cambiarClaFinDidacN4() {
    	if(proyectoActual.getClasificacionFinDidacticoISBNNivel4() != null)
    	{
    		if (!proyectoActual.getClasificacionFinDidacticoISBNNivel4().equals("0"))
    			listaFinDidactico_N5 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionFinDidacticoISBNNivel4());
    	}
    	proyectoActual.setClasificacionFinDidacticoISBNNivel5(null);
	}
    
    public void cambiarClaEdadIntN1() {
		if (!proyectoActual.getClasificacionEdadInteresISBNNivel1().equals("0"))
			listaEdadInteres_N2 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionEdadInteresISBNNivel1());
		
		proyectoActual.setClasificacionEdadInteresISBNNivel2(null);
		listaEdadInteres_N3 = null;
		cambiarClaEdadIntN2();
	}
    
    public void cambiarClaEdadIntN2() {
    	if(proyectoActual.getClasificacionEdadInteresISBNNivel2() != null)
    	{	
			if (!proyectoActual.getClasificacionEdadInteresISBNNivel2().equals("0"))
				listaEdadInteres_N3 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionEdadInteresISBNNivel2());
    	}
		proyectoActual.setClasificacionEdadInteresISBNNivel3(null);
		listaEdadInteres_N4 = null;
		cambiarClaEdadIntN3();
	}
    
    public void cambiarClaEdadIntN3() {
    	if(proyectoActual.getClasificacionEdadInteresISBNNivel3() != null)
    	{
    		if (!proyectoActual.getClasificacionEdadInteresISBNNivel3().equals("0"))
    			listaEdadInteres_N4 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionEdadInteresISBNNivel3());
    	}
    	proyectoActual.setClasificacionEdadInteresISBNNivel4(null);
	}
    
    public void cambiarClaEstiloN1() {
		if (!proyectoActual.getClasificacionEstiloISBNNivel1().equals("0"))
			listaEstilo_N2 = servicioGeneral.retornaSelectItemArregloDeHijosDeTiposOrdenABC(proyectoActual.getClasificacionEstiloISBNNivel1());
		
		proyectoActual.setClasificacionEstiloISBNNivel2(null);
	}
    
    public void cargarListaCiudades() {
        // B-Ciudades
        // String pais2 = "CO";
        String consultaB = "select cc from Ciudad cc where cc.departamento like '%"
                + proyectoActual.getDepartamentoEdicionISBN() + "%' ";
        List listB = servicioGeneral.obtenerObjetos(consultaB);

        if (listB.size() > 0) {
            listaCiudades = new ArrayList<SelectItem>();
            listaCiudades.add(new SelectItem("0", "Seleccione una opción ..."));
            for (int i = 0; i < listB.size(); i++) {
                Ciudad ciudad = (Ciudad) listB.get(i);
                listaCiudades.add(new SelectItem(ciudad.getId(), ciudad.getNombre()));
            }
        } else
            listaCiudades.add(new SelectItem("0", " - "));
    }

    private void cargarPaises() {
        // JOptionPane.showMessageDialog(null, "Entro CP");
        List listaPaises = servicioGeneral.obtenerListaObjetos("Pais");
        paisItem = new SelectItem[listaPaises.size()];
        for (int i = 0; i < listaPaises.size(); i++) {
            Pais paisObjeto = (Pais) listaPaises.get(i);
            paisItem[i] = new SelectItem(paisObjeto.getId(), paisObjeto.getNombre());
        }
        // tipo_documento = (TipoDocumento) listaTipoDocumento.get(0);
    }

    public void mostrarCoeditor() {
        if (proyectoActual.getEsCoedicionISBN().equals("SI"))
            mostrarCoeditor = true;
        else if (proyectoActual.getEsCoedicionISBN().equals("NO"))
            mostrarCoeditor = false;
    }
    
    public void mostrarResena() {
        if (proyectoActual.getEsCamaraColLibro().equals("SI"))
            mostrarResena = true;
        else if (proyectoActual.getEsCamaraColLibro().equals("NO"))
        	mostrarResena = false;
    }

    public void cambiarTipoEvento() {

        if (proyectoActual.getClaseEvento() != null) {
            if (proyectoActual.getClaseEvento().equals("COL_OTRA")) {
                mostrarOtroTipoEvento = true;
            } else {
                mostrarOtroTipoEvento = false;
            }
        }

    }

    public void agregarParticipante() {
        Investigador nuevoInvestigador = new Investigador();
        boolean encuentraParticipante = false;

        if (documentoCoinv2 != null && !documentoCoinv2.equals("") && !documentoCoinv2.equals(" ") && !pais.equals(null)
                && !pais.equals("") && !pais.equals("00") && !rolAutorISBN.equals("0") && !rolAutorISBN.equals(null)
                && fechaNacimiento != null) {

            if (listaParticipantes.size() > 0) {

                for (int i = 0; i < listaParticipantes.size(); i++) {
                    InvestigadorProyecto invpry = listaParticipantes.get(i);
                    if (invpry.getInvestigador().getId().getDocumento().equals(documentoCoinv2) && invpry
                            .getInvestigador().getId().getTipoDocumento().equals(tipoDocumentoCoInv2.getId())) {

                        encuentraParticipante = true;
                        break;
                    } else {

                    }
                }

            }

            if (!encuentraParticipante) {
                try {

                    if (tipoInvestigador.equals("AI")) { // si es docente

                        InvestigadorInterno investigadorInterno = servicioPersona.obtenerInvestigadorInterno(
                                new IdPersona(this.documentoCoinv2, this.tipoDocumentoCoInv2.getId()));

                        if (investigadorInterno != null) {

                            // Nacionalidad y Rol - Si es interno
                            investigadorInterno.setPaisOrigen(pais);
                            investigadorInterno.setFechaNacimiento(fechaNacimiento);
                            Persona persona = servicioPersona.obtenerPersona(investigadorInterno.getId());
                            if (persona == null) {
                                servicioPersona.insertaInterno(investigadorInterno);
                                // servicioGeneral.guardarObjeto(investigadorInterno);
                            }
                            // Agregar participante a la listaparticipante
                            InvestigadorProyecto participante = new InvestigadorProyecto();
                            participante.setInvestigador(investigadorInterno);
                            participante.setDedicacionHorasSemana(Short.parseShort("0"));
                            participante.setProyecto(proyectoActual);

                            // Persona persona =
                            // servicioPersona.obtenerPersona(new
                            // IdPersona(personaActual.getId().getTipoDocumento(),personaActual.getId().getDocumento()));

                            if (personaActual.getId().getTipoDocumento().equals(tipoDocumentoCoInv2.getId())
                                    && personaActual.getId().getDocumento().equals(documentoCoinv2)) {
                                TipoInvestigador ti = (TipoInvestigador) servicioGeneral
                                        .obtenerObjeto(new TipoInvestigador(), InvestigadorProyecto.PRINCIPAL);
                                participante.setTipo(ti);

                                String sql = "select pp from TipoInvestigador pp where pp.id ='" + rolAutorISBN + "'";
                                List lista = servicioGeneral.obtenerObjetos(sql);

                                if (lista.size() > 0) {
                                    TipoInvestigador rol = (TipoInvestigador) lista.get(0);
                                    participante.setFuncion(rol.getNombre());
                                }
                                // TipoInvestigador rol = (TipoInvestigador)
                                // servicioGeneral.obtenerObjeto(TipoInvestigador.class,
                                // rolAutorISBN);
                            } else {
                                TipoInvestigador ti = new TipoInvestigador();
                                ti = (TipoInvestigador) servicioGeneral.obtenerObjeto(new TipoInvestigador(),
                                        rolAutorISBN);
                                participante.setTipo(ti);

                                participante.setFuncion("Autor Interno");
                            }

                            listaParticipantes.add(participante);

                        } else { // No se encontró como investigador interno
                                 // si es estudiante
                            IdPersona idEst = new IdPersona(this.documentoCoinv2, this.tipoDocumentoCoInv2.getId());
                            Estudiante e = servicioPersona.obtenerEstudiante(idEst);

                            if (e != null) {

                                nuevoInvestigador = servicioPersona.obtenerInvestigador(idEst);
                                if (nuevoInvestigador == null) {
                                    InvestigadorInterno nvoinv = e.convertirAInvestigador();
                                    // nvoinv.setDependencia(buscarFacultad(facultad));
                                    if (e != null && e.getDependencia() != null) {
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

                                InvestigadorInterno nvoinv = e.convertirAInvestigador();
                                nvoinv.setDependencia(e.getDependencia());

                                // Nacionalidad y Rol - Si es estudiante
                                nvoinv.setPaisOrigen(pais);
                                nvoinv.setFechaNacimiento(fechaNacimiento);
                                // servicioPersona.guardarInvestigador(nvoinv);
                                // servicioPersona.insertaInterno(nvoinv);
                                servicioGeneral.guardarObjeto(nvoinv);

                                // Agregar participante a la listaparticipante
                                InvestigadorProyecto participante = new InvestigadorProyecto();
                                participante.setInvestigador(nvoinv);
                                participante.setDedicacionHorasSemana(Short.parseShort("0"));
                                participante.setFuncion("Participante");
                                participante.setProyecto(proyectoActual);
                                TipoInvestigador ti = new TipoInvestigador();
                                ti = (TipoInvestigador) servicioGeneral.obtenerObjeto(new TipoInvestigador(),
                                        rolAutorISBN);
                                participante.setTipo(ti);

                                listaParticipantes.add(participante);

                            } else { // si no se encontró como estudiante
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                                "La persona con el número de documento " + documentoCoinv2
                                                        + " no fue encontrado",
                                                ""));
                            }

                        }

                    } else { // si es otro

                        if (investigadorExterno.getNombre1() != null && !investigadorExterno.getNombre1().equals("")
                                && investigadorExterno.getApellido1() != null
                                && !investigadorExterno.getApellido1().equals("")) {

                            IdPersona id = new IdPersona(this.documentoCoinv2, this.tipoDocumentoCoInv2.getId());
                            investigadorExterno.setId(id);
                            investigadorExterno.setInterno(Investigador.EXTERNO);
                            investigadorExterno.setEvaluador(Investigador.NO_EVALUADOR);
                            // Dependencia dep = servicioGeneral
                            // .obtenerDependencia(unidadEjecutoraPart);
                            // investigadorExterno.setDependencia(dep);

                            // if (insitucionNombre != null
                            // && !insitucionNombre.equals("")) {
                            // Institucion i = servicioGeneral
                            // .obtenerinstitucionPorNombre(insitucionNombre);
                            //
                            // if (i == null) {
                            // Institucion institucionNueva = new Institucion();
                            // institucionNueva.setNombre(insitucionNombre);
                            // servicioGeneral.guardarObjeto(institucionNueva);
                            // investigadorExterno
                            // .setInstitucion(institucionNueva);
                            // } else {
                            // investigadorExterno.setInstitucion(i);
                            // }
                            // } else {
                            // Institucion i = servicioGeneral
                            // .obtenerinstitucionPorNombre("--");
                            // investigadorExterno.setInstitucion(i);
                            // }

                            // Nacionalidad y Rol - Si es externo
                            investigadorExterno.setPaisOrigen(pais);
                            investigadorExterno.setFechaNacimiento(fechaNacimiento);

                            // Institucion i =
                            // servicioGeneral.obtenerinstitucionPorNombre("
                            // Otra Insitución");
                            // investigadorExterno.setInstitucion(i);

                            Institucion i = servicioGeneral.obtenerinstitucionPorNombre("--");
                            investigadorExterno.setInstitucion(i);

                            Persona nuevaPersona = servicioPersona.obtenerPersona(id);

                            if (nuevaPersona == null) {
                                try {
                                    // Si la persona no existe se guarda como
                                    // investigador
                                    servicioPersona.guardarInvestigador(investigadorExterno);
                                    // servicioPersona.insertarExterno(investigadorExterno);

                                } catch (Exception e) {

                                }

                            } else {

                                InvestigadorExterno persona = servicioPersona.obtenerInvestigadorExterno(id);
                                if (persona == null) {
                                    servicioPersona.insertarExterno(investigadorExterno);
                                }
                            }

                            // Agregar participante a la listaparticipante
                            InvestigadorProyecto participante = new InvestigadorProyecto();
                            participante.setInvestigador(investigadorExterno);
                            participante.setDedicacionHorasSemana(Short.parseShort("0"));
                            participante.setFuncion("Participante");
                            participante.setFuncion("Autor Externo");
                            participante.setProyecto(proyectoActual);
                            TipoInvestigador ti = new TipoInvestigador();
                            ti = (TipoInvestigador) servicioGeneral.obtenerObjeto(new TipoInvestigador(), rolAutorISBN);
                            participante.setTipo(ti);
                            listaParticipantes.add(participante);
                            investigadorExterno = new InvestigadorExterno();
                            esOtraVinculacion = false;

                        } else {
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                            "Por favor ingresar nombres y apellidos de la persona a registrar", ""));
                        }

                    }

                    documentoCoinv2 = "";
                    tipoInvestigador = "AI";

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL, "La persona indicada ya se encuentra registrada.",
                                "La persona indicada ya se encuentra registrada."));
            }

        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "Por favor ingrese el número de identificación, país de procedencia, rol y fecha de nacimiento",
                            ""));
        }

    }

    public void eliminarParticipante() {

        System.out.println("id del participante: " + participante.getInvestigador().getId().getDocumento());

        try {

            listaParticipantes.remove(participante);
            proyectoActual.getInvestigadoresProyecto().remove(participante);

            listaParticipantesBorrados.add(participante);

            participante = new InvestigadorProyecto();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean buscarInvestigador(IdPersona id) {
        // BUSCA UN INVESTIGADOR DE ACUERDO A SU ID
        boolean investigadorPresente = false;
        int i = 0;
        List listaInvestigadoresProyecto = listaInvestigadoresVista;// proyectoActual.getListaInvestigadoresProyecto();
        while (i < listaInvestigadoresProyecto.size()) {
            InvestigadorProyectoVista ipv = (InvestigadorProyectoVista) listaInvestigadoresVista.get(i);
            InvestigadorProyecto d = ipv.getIp();// (InvestigadorProyecto)listaInvestigadoresProyecto.get(i);
            if (id.getDocumento().equals(d.getInvestigador().getId().getDocumento())
                    && id.getTipoDocumento().equals(d.getInvestigador().getId().getTipoDocumento())) {
                investigadorPresente = true;
                // errorValidacion =
                // "El investigador ya se encuentra asociado al proyecto";
                break;
            }
            i = i + 1;
        }
        return investigadorPresente;
    }

    public boolean validarNombre_InvPpal() {
        boolean val = true;
        boolean valestud = false;

        if (!yaHayPrincipal()) {
            val = false;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "Por favor registre la información del director del proyecto",
                            "Por favor registre la información del director del proyecto"));
        }

        if (this.proyectoActual.getNombre() == null || this.proyectoActual.getNombre().equals("--")
                || this.proyectoActual.getNombre().equals("")) {
            val = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Por favor registre el título del libro", "Por favor registre el título del libro"));
        }

        return val;
    }

    public boolean validarCamposISBN() {
        boolean val = true;

        if (!yaHayPrincipal()) {
            // val = false;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "El solicitante (" + personaActual.getId().getTipoDocumento() + " "
                                    + personaActual.getId().getDocumento()
                                    + ") no se encuentra incluido en el listado de autores.",
                            ""));
            InvestigadorProyecto investigadorProyecto = new InvestigadorProyecto();
            Investigador investigador = servicioPersona.obtenerInvestigador(personaActual.getId());
            investigadorProyecto.setInvestigador(investigador);
            investigadorProyecto.setProyecto(proyectoActual);
            investigadorProyecto.setDedicacionHorasSemana(Short.parseShort(String.valueOf(0)));
            investigadorProyecto.setFuncion("Creador");
            TipoInvestigador tipoInvestigador = new TipoInvestigador();
            tipoInvestigador.setId("P");
            investigadorProyecto.setTipo(tipoInvestigador);
            listaParticipantes.add(investigadorProyecto);
            proyectoActual.setCreadorId(personaActual.getId().getDocumento());
            proyectoActual.setCreadorDocumento(personaActual.getId().getTipoDocumento());
        }

        if (this.proyectoActual.getNombre() == null 
        		|| this.proyectoActual.getNombre().equals("--")
                || this.proyectoActual.getNombre().equals("")) {
            val = false;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Por favor registre el título de obra",
                            "Por favor registre el título de obra"));
        }

        // CLASIFICACIÓN TEMÁTICA
        if (this.proyectoActual.getMateriaISBN() == null || this.proyectoActual.getMateriaISBN().equals("0")) {
            val = false;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Por favor seleccione la materia", ""));
        }

        if (this.proyectoActual.getTipoContenidoISBN() == null
                || this.proyectoActual.getTipoContenidoISBN().equals("0")) {
            val = false;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Por favor seleccione el tipo de contenido", ""));
        }
        
        if (this.proyectoActual.getNombreColeccionISBN() == null 
        		|| this.proyectoActual.getNombreColeccionISBN().equals("--")
                || this.proyectoActual.getNombreColeccionISBN().equals("")) {
            val = false;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Por favor registre la colección",
                            "Por favor registre la colección"));
        }

        if (this.proyectoActual.getIdiomaPrincipalISBN() == null
                || this.proyectoActual.getIdiomaPrincipalISBN().equals("0")) {
            val = false;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Por favor seleccione el idioma de la obra", ""));
        }
        
        if (this.proyectoActual.getClasificacionThemaISBNNivel1().equals(0L)) {
            val = false;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Por favor seleccione la clasificación THEMA", ""));
        }

        // TRADUCCIÓN
        if (this.proyectoActual.getEsTraduccionISBN() != null
                && this.proyectoActual.getEsTraduccionISBN().equals("SI")) {

            if (this.proyectoActual.getIdiomaOriginalISBN() == null
                    || this.proyectoActual.getIdiomaOriginalISBN().equals("0")) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL, "Por favor seleccione el idioma original", ""));
            }
            
            if (this.proyectoActual.getIdiomaOrigen() == null
                    || this.proyectoActual.getIdiomaOrigen().equals("0")) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL, "Por favor seleccione el idioma del cual se traduce", ""));
            }

            if (this.proyectoActual.getIdiomaDestinoISBN() == null
                    || this.proyectoActual.getIdiomaDestinoISBN().equals("0")) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "Por favor seleccione el idioma al cual se traduce", ""));
            }

            if (this.proyectoActual.getTituloIdiomaOriginalISBN() == null
                    || this.proyectoActual.getTituloIdiomaOriginalISBN().equals("--")
                    || this.proyectoActual.getTituloIdiomaOriginalISBN().equals("")) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "Por favor registre el título en el idioma original", ""));
            }

        } else if (this.proyectoActual.getEsTraduccionISBN().equals("NO")) {
            this.proyectoActual.setIdiomaOriginalISBN(null);
            this.proyectoActual.setIdiomaDestinoISBN(null);
            this.proyectoActual.setTituloIdiomaOriginalISBN(null);
        }

        // INFORMACIÓN DE LA EDICIÓN
        if (this.proyectoActual.getNumeroEdicionISBN() == null) {
            val = false;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Por favor registre el número de la edición", ""));
        } else if (this.proyectoActual.getNumeroEdicionISBN() < 0) {
            val = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "El valor para el número de la edición no puede ser negativo", ""));
        }

        if (this.proyectoActual.getDepartamentoEdicionISBN() == null
                || this.proyectoActual.getDepartamentoEdicionISBN().equals("0")) {
            val = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Por favor seleccione el departamento de edición", ""));
        }

        if (this.proyectoActual.getCiudadEdicionISBN() == null
                || this.proyectoActual.getCiudadEdicionISBN().equals("0")) {
            val = false;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Por favor seleccione la ciudad de edición", ""));
        }

        if (this.proyectoActual.getFechaAparicionISBN() == null) {
            val = false;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Por favor seleccione la fecha de publicación", ""));
        }

        if (this.proyectoActual.getEsCoedicionISBN().equals("SI")) {
            if (this.proyectoActual.getCoeditorISBN() == null || this.proyectoActual.getCoeditorISBN().equals("")) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL, "Por favor ingrese el coeditor", ""));
            }
        } else if (this.proyectoActual.getEsCoedicionISBN().equals("NO")) {
            this.proyectoActual.setCoeditorISBN(null);
        }

        // COMERCIALIZABLE
        if (this.proyectoActual.getEsComercializableISBN().equals("SI")) {

            if (this.proyectoActual.getNumEjemplaresNacionalISBN() == null) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "Por favor registre el número de ejemplares de oferta nacional", ""));
            } else if (this.proyectoActual.getNumEjemplaresNacionalISBN() < 0) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "El valor para el número de ejemplares de oferta nacional no puede ser negativo", ""));
            }

            if (this.proyectoActual.getPrecioCOPISBN() == null) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "Por favor registre el precio en moneda local - libro impreso", ""));
            } else if (this.proyectoActual.getPrecioCOPISBN() < 0) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "El valor para el precio en moneda local no puede ser negativo", ""));
            }

            if (this.proyectoActual.getPrecioUSDISBN() == null) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL, "Por favor registre el precio en dólares", ""));
            } else if (this.proyectoActual.getPrecioUSDISBN() < 0) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "El valor para el precio en dólares no puede ser negativo", ""));
            }

            if (this.proyectoActual.getOfertaTotalISBN() == null) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL, "Por favor registre la oferta total", ""));
            } else if (this.proyectoActual.getOfertaTotalISBN() < 0) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "El valor para la oferta total no puede ser negativo", ""));
            }
            
            if (this.proyectoActual.getDisponibleEnISBN() == null
                    || this.proyectoActual.getDisponibleEnISBN().equals("--")
                    || this.proyectoActual.getDisponibleEnISBN().equals("")) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "Por favor registre donde está disponible la publicacion", ""));
            }

            this.proyectoActual.setRazonesNoComercializableISBN(null);

        } else if (this.proyectoActual.getEsComercializableISBN().equals("NO")) {
            if (this.proyectoActual.getRazonesNoComercializableISBN() == null
                    || this.proyectoActual.getRazonesNoComercializableISBN().equals("--")
                    || this.proyectoActual.getRazonesNoComercializableISBN().equals("")) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "Por favor registre las razones por las cuales no es comercializable", ""));
            }
            
            if (this.proyectoActual.getDisponibleEnISBN() == null
                    || this.proyectoActual.getDisponibleEnISBN().equals("--")
                    || this.proyectoActual.getDisponibleEnISBN().equals("")) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "Por favor registre donde está disponible la publicacion", ""));
            }

//            this.proyectoActual.setNumEjemplaresNacionalISBN(null);
            this.proyectoActual.setPrecioCOPISBN(null);
            this.proyectoActual.setPrecioUSDISBN(null);
//            this.proyectoActual.setOfertaTotalISBN(null);
//            this.proyectoActual.setNumEjemplaresExternosISBN(null);
        }

        // DESCRIPCIÓN FÍSICA DEL MATERIAL
        if (this.proyectoActual.getEsISBNImpreso()) {

            if (this.proyectoActual.getDescripcionFisicaISBN() == null
                    || this.proyectoActual.getDescripcionFisicaISBN().equals("0")) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "Por favor seleccione el tipo de producto", ""));
            }

            if (this.proyectoActual.getTipoEncuadernacionISBN() == null
                    || this.proyectoActual.getTipoEncuadernacionISBN().equals("0")) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "Por favor seleccione el tipo de encuadernación", ""));
            }

            if (this.proyectoActual.getTipoPapelISBN() == null 
            		|| this.proyectoActual.getTipoPapelISBN().equals("0")) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL, "Por favor seleccione el tipo de papel", ""));
            }

            if (this.proyectoActual.getGramajeISBN() == null || this.proyectoActual.getGramajeISBN().equals("0")) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL, "Por favor seleccione el gramaje", ""));
            }
            
            if (this.proyectoActual.getPesoGramosObra() == null) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL, "Por favor registre el peso en gramos de la obra", ""));
            } else if (this.proyectoActual.getPesoGramosObra() < 0) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL, "El valor para el peso en gramos de la obra no puede ser negativo", ""));
            }

            if (this.proyectoActual.getTipoImpresionISBN() == null
                    || this.proyectoActual.getTipoImpresionISBN().equals("0")) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL, "Por favor seleccione el tipo de impresión", ""));
            }

            if (this.proyectoActual.getNumPaginasISBN() == null) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL, "Por favor registre el número de páginas", ""));
            } else if (this.proyectoActual.getNumPaginasISBN() < 0) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "El valor para el número de páginas no puede ser negativo", ""));
            }

            if (this.proyectoActual.getNumTintasISBN() == null || this.proyectoActual.getNumTintasISBN().equals("0")) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL, "Por favor seleccione el número de tintas", ""));
            }

            if (this.proyectoActual.getAnchoISBN() == null) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL, "Por favor registre el ancho", ""));
            } else if (this.proyectoActual.getAnchoISBN() < 0) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "El valor para el ancho no puede ser negativo", ""));
            }

            if (this.proyectoActual.getAltoISBN() == null) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL, "Por favor registre el alto", ""));
            } else if (this.proyectoActual.getAltoISBN() < 0) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "El valor para el alto no puede ser negativo", ""));
            }

        }else
        {
        	proyectoActual.setDescripcionFisicaISBN(null);
        	proyectoActual.setTipoEncuadernacionISBN(null);
        	proyectoActual.setTipoPapelISBN(null);
        	proyectoActual.setGramajeISBN(null);
        	proyectoActual.setPesoGramosObra(null);
        	proyectoActual.setTipoImpresionISBN(null);
        	proyectoActual.setNumPaginasISBN(null);
        	proyectoActual.setNumTintasISBN(null);
        	proyectoActual.setAnchoISBN(null);
        	proyectoActual.setAltoISBN(null);
        }
        
        if (this.proyectoActual.getEsISBNDigital()) {
        	
            //Tipo Soporte
            if (this.proyectoActual.getTipoSoporteDigital() == null
                    || this.proyectoActual.getTipoSoporteDigital().equals("0")) {
                val = false;
                mensajeError("Por favor seleccione el tipo de soporte");
            }
          
            //Formato de producto
            if (this.proyectoActual.getFormatoISBN() == null
                    || this.proyectoActual.getFormatoISBN().equals("0")) {
                val = false;
                mensajeError("Por favor seleccione el formato de producto");
            }
            
            //Medio electronico
            if (this.proyectoActual.getMedioElectronicoISBN() == null
                    || this.proyectoActual.getMedioElectronicoISBN().equals("0")) {
                val = false;
                mensajeError("Por favor seleccione el medio electrónico o digital");
            }
            
        	//Tipo Contenido
            if (this.proyectoActual.getTipoContenidoProducto() == null
                    || this.proyectoActual.getTipoContenidoProducto().equals("0")) {
                val = false;
                mensajeError("Por favor seleccione el tipo de contenido del producto");
            }
            
          //Proteccion tecnica de los archivos
            if (this.proyectoActual.getProteccionTecnicaArchivos() == null
                    || this.proyectoActual.getProteccionTecnicaArchivos().equals("0")) {
                val = false;
                mensajeError("Por favor seleccione la protección técnica de los archivos digitales");
            }
            
          //Permiso uso
            if (this.proyectoActual.getPermisoUso() == null
                    || this.proyectoActual.getPermisoUso().equals("0")) {
                val = false;
                mensajeError("Por favor seleccione el permiso de uso");
            }
           
            //Tipo acceso
            if (this.proyectoActual.getTipoAccesoISBNDigital() == null
                    || this.proyectoActual.getTipoAccesoISBNDigital().equals(0L)) {
                val = false;
                mensajeError("Por favor seleccione el tipo de acceso");
            }
            
            //Tamaño
            if (this.proyectoActual.getTamañoISBN() == null
                    || this.proyectoActual.getTamañoISBN().equals(0L)
                    || this.proyectoActual.getUnidadMedidaTamañoISBN().equals(0L)
                    || esNulo(this.proyectoActual.getUnidadMedidaTamañoISBN())) {
                val = false;
                mensajeError("Por favor seleccione el tamaño");
            }
        }
        else
        {
        	 proyectoActual.setTipoSoporteDigital(null);
             proyectoActual.setMedioElectronicoISBN(null);
             proyectoActual.setFormatoISBN(null);
             proyectoActual.setTipoContenidoProducto(null);
             proyectoActual.setProteccionTecnicaArchivos(null);
             proyectoActual.setPermisoUso(null);
             proyectoActual.setTamañoISBN(null);
             proyectoActual.setUnidadMedidaTamañoISBN(null);
             proyectoActual.setTipoAccesoISBNDigital(null);
        }
        	
        
        if (this.proyectoActual.getEsISBNIBD()) {
        	
            if (this.proyectoActual.getDescripcionFisicaISBNIBD() == null
                    || this.proyectoActual.getDescripcionFisicaISBNIBD().equals("0")) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "Por favor seleccione el tipo de producto para ISBN IBD (Impresión bajo demanda)", ""));
            }

            if (this.proyectoActual.getTipoEncuadernacionISBNIBD() == null
                    || this.proyectoActual.getTipoEncuadernacionISBNIBD().equals("0")) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "Por favor seleccione el tipo de encuadernación para ISBN IBD (Impresión bajo demanda)", ""));
            }

            if (this.proyectoActual.getTipoPapelISBNIBD() == null 
            		|| this.proyectoActual.getTipoPapelISBNIBD().equals("0")) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL, "Por favor seleccione el tipo de papel para ISBN IBD (Impresión bajo demanda)", ""));
            }

            if (this.proyectoActual.getGramajeISBNIBD() == null || this.proyectoActual.getGramajeISBNIBD().equals("0")) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL, "Por favor seleccione el gramaje para ISBN IBD (Impresión bajo demanda)", ""));
            }
            
            if (this.proyectoActual.getPesoGramosObraIBD() == null) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "Por favor registre el peso en gramos de la obra para ISBN IBD (Impresión bajo demanda)", ""));
            } else if (this.proyectoActual.getPesoGramosObraIBD() < 0) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "El valor para el peso en gramos de la obra no puede ser negativo para ISBN IBD (Impresión bajo demanda)", ""));
            }

            if (this.proyectoActual.getTipoImpresionISBNIBD() == null
                    || this.proyectoActual.getTipoImpresionISBNIBD().equals("0")) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL, "Por favor seleccione el tipo de impresión para ISBN IBD (Impresión bajo demanda)", ""));
            }

            if (this.proyectoActual.getNumPaginasISBNIBD() == null) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL, "Por favor registre el número de páginas para ISBN IBD (Impresión bajo demanda)", ""));
            } else if (this.proyectoActual.getNumPaginasISBNIBD() < 0) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "El valor para el número de páginas no puede ser negativo para ISBN IBD (Impresión bajo demanda)", ""));
            }

            if (this.proyectoActual.getNumTintasISBNIBD() == null || this.proyectoActual.getNumTintasISBNIBD().equals("0")) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL, "Por favor seleccione el número de tintas para ISBN IBD (Impresión bajo demanda)", ""));
            }

            if (this.proyectoActual.getAnchoISBNIBD() == null) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL, "Por favor registre el ancho para ISBN IBD (Impresión bajo demanda)", ""));
            } else if (this.proyectoActual.getAnchoISBNIBD() < 0) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "El valor para el ancho no puede ser negativo", ""));
            }

            if (this.proyectoActual.getAltoISBNIBD() == null) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL, "Por favor registre el alto para ISBN IBD (Impresión bajo demanda)", ""));
            } else if (this.proyectoActual.getAltoISBNIBD() < 0) {
                val = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "El valor para el alto no puede ser negativo para ISBN IBD (Impresión bajo demanda)", ""));
            }
        	
        }else
        {
        	proyectoActual.setDescripcionFisicaISBNIBD(null);
        	proyectoActual.setTipoEncuadernacionISBNIBD(null);
        	proyectoActual.setTipoPapelISBNIBD(null);
        	proyectoActual.setGramajeISBNIBD(null);
        	proyectoActual.setPesoGramosObraIBD(null);
        	proyectoActual.setTipoImpresionISBNIBD(null);
        	proyectoActual.setNumPaginasISBNIBD(null);
        	proyectoActual.setNumTintasISBNIBD(null);
        	proyectoActual.setAnchoISBNIBD(null);
        	proyectoActual.setAltoISBNIBD(null);
        }
        
        //SISTEMAS DE INFORMACIÓN DE LA EDITORIAL UNAL
        
        //Reseña
    	if (this.proyectoActual.getResena() == null || this.proyectoActual.getResena().equals("")) {
            val = false;
            mensajeError("Por favor ingrese la reseña");
        }
    	
    	// Palabras Clave
		if (proyectoActual.getListaPalabrasES().size() < 5) {
			val = false;
			mensajeError("Por favor agregue mínimo 5 palabras clave");
		}
		
		if (this.proyectoActual.getTablaContenidoISBN() == null || this.proyectoActual.getTablaContenidoISBN().equals("")) {
            val = false;
            mensajeError("Por favor ingrese la tabla de contenido");
        }else {
        	if(proyectoActual.getTablaContenidoISBN().length() > 3900)
        		proyectoActual.setTablaContenidoISBN(cortarCadena(proyectoActual.getTablaContenidoISBN(), 3900));
        }

        return val;
    }

    // CAZ Guardar proyecto de la convocatoria Solicitud ISBN
    public void guardarProyectoSolISBN() {
        System.out.println("<========== GUARDAR CONVOCATORIA SOLICITUD ISBN ==========>");

        if (proyectoExiste) {
            for (InvestigadorProyecto inv : listaParticipantesBorrados)
                servicioGeneral.eliminarObjeto(inv);
            servicioProyecto.ingresarProyecto(proyectoActual);
        }

        if (listaParticipantes.size() > 0) {
            for (int i = 0; i < listaParticipantes.size(); i++)
                proyectoActual.adicionarInvestigadorProyecto(listaParticipantes.get(i));
        }
        servicioProyecto.ingresarProyecto(proyectoActual);
    }

    public void guardarProyectoConvLibro() {
        System.out.println("<========== GUARDAR CONVOCATORIA LIBROS ==========>");

        if (proyectoExiste) {

            for (InvestigadorProyecto inv : listaParticipantesBorrados) {
                servicioGeneral.eliminarObjeto(inv);
            }

            servicioProyecto.ingresarProyecto(proyectoActual);
        }

        // áreas de la ciencia
        DominioDetalle arUno = new DominioDetalle();
        List listaDomDetUno = new ArrayList<DominioDetalle>();
        // String consulta1 =
        // "select dd from Dominio d, DominioDetalle dd where d.id =
        // dd.identificador.id and d.tipo ='"
        // + DOMINIO_AREA_CIENCIA + "' and dd.identificador.tipo = '" +
        // areaCiencia + "'";
        listaDomDetUno = servicioGeneral.obtenerObjetos(
                "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
                        + DOMINIO_AREA_CIENCIA + "' and dd.identificador.tipo = '" + areaCiencia + "'");
        arUno = (DominioDetalle) listaDomDetUno.get(0);

        AreaTematica arTemUno = new AreaTematica();
        arTemUno.setProyecto(proyectoActual);
        arTemUno.setProyectoAreaTematica(arUno);
        arTemUno.setTipo(1l);

        // áreas de la ciencia
        DominioDetalle arDos = new DominioDetalle();
        List listaDomDetarDos = new ArrayList<DominioDetalle>();
        listaDomDetarDos = servicioGeneral.obtenerObjetos(
                "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
                        + DOMINIO_AREA_CIENCIA + "' and dd.identificador.tipo = '" + areaCienciaSec + "'");
        arDos = (DominioDetalle) listaDomDetarDos.get(0);

        AreaTematica arTemDos = new AreaTematica();
        arTemDos.setProyecto(proyectoActual);
        arTemDos.setProyectoAreaTematica(arDos);
        arTemDos.setTipo(2l);

        Set<AreaTematica> seAt = new HashSet<AreaTematica>();
        seAt.add(arTemUno);
        seAt.add(arTemDos);

        try {
            if (proyectoActual != null && proyectoActual.getId() != null) {
                servicioGeneral.eliminar("DELETE HER_PROYECTO_AREA_TEMATICA WHERE PRY_ID = " + proyectoActual.getId());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        proyectoActual.setAreasTematicas(seAt);

        if (listaParticipantes.size() > 0) {
            for (int i = 0; i < listaParticipantes.size(); i++) {
                proyectoActual.adicionarInvestigadorProyecto(listaParticipantes.get(i));
            }
        }

        servicioProyecto.ingresarProyecto(proyectoActual);
    }

    @Override
    public String atras() {
        sesion.removeAttribute("ManejadorConvocatoriaLibros");

        ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");

        boolean bandera = false;
        sesion.removeAttribute("manejadorMenuFormularios");

        return "misProyectos";
    }

    @Override
    public String salir() {

        return null;
    }

    @Override
    public String salirGuardar() {
        // if (validarNombre_InvPpal()) {
        if (validarCamposISBN()) {

            if (proyectoActual.getId() != null) {
                // Ing. Wilver Alexander Martínez Martínez -wam²
                // Cambio - Registro de cambios
                Persona personaAux = new Persona();
                personaAux = (Persona) sesion.getAttribute("persona");

                Formulario formulario = new Formulario();
                List listaFormulario = new ArrayList();

                listaFormulario = servicioGeneral.obtenerListaObjetos("Formulario where id ='0'");
                formulario = (Formulario) listaFormulario.get(0);

                HistoricoFormularioProyecto historicoFormualrioProyecto = new HistoricoFormularioProyecto();
                historicoFormualrioProyecto.setDocPersona(personaAux.getId().getDocumento());
                historicoFormualrioProyecto.setTipoDocumentoPersona(personaAux.getId().getTipoDocumento());
                historicoFormualrioProyecto.setFormulario(formulario);
                historicoFormualrioProyecto.setProyecto(proyectoActual);
                historicoFormualrioProyecto.setFechaCambio(new Date());
                servicioGeneral.guardarObjeto(historicoFormualrioProyecto);
            }

            if (mostrarSiConvLibros) {
                guardarProyectoConvLibro();
            } else if (mostrarSiArticulosUno) {
                guardarProyectoConvArticulos();
            } else if (mostrarSiArticulosDos) {
                guardarProyectoConvArticulos();
            } else if (mostrarSiSolicitudISBN) {
                guardarProyectoSolISBN();
            }
            
            if(validarSiEsEstudianteLiderProyecto(proyectoActual.getId()) && proyectoActual.getId() != null){
          	  enviarCorreoEdicionInfoPryEstLider(proyectoActual.getId());
            }

            sesion.removeAttribute("manejadorFichaMinimaProyectos");

            ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");

            boolean bandera = false;
            sesion.removeAttribute("manejadorMenuFormularios");
            sesion.removeAttribute("ManejadorSolicitudISBN");

            return "misProyectos";

        } else {
            return "";
        }
    }

    @Override
    public String siguiente() {
        if (validarCamposISBN()) {

            String link = "";
            ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
            boolean bandera = false;
            int pos = 0;
            if (man != null) {
                if (man.getItemProyecto() != null) {
                    MenuItem lis[] = man.getMenuItemArray();
                    if (lis != null) {
                        for (int i = 0; i < lis.length; i++) {
                            if (bandera) {
                                if (lis[i].isRendered()) {
                                    sesion.removeAttribute("manejadorMenuFormularios");

                                    link = lis[i].getOutcome();
                                    break;
                                }
                            }

                            if (lis[i].getOutcome().equals("irSolicitudISBN")) {
                                bandera = true;
                            }
                            if (lis[i].isRendered()) {
                                pos++;
                            }
                        }
                    }
                }
            }

            if ((proyectoActual.getEstadoProyecto().getId()).equals("I")
                    && (pos - 1) == proyectoActual.getFase().intValue()) {
                proyectoActual.setFase(new Integer((proyectoActual.getFase()).intValue() + 1));
            }
            // System.out.println("fase
            // "+proyectoActual.getFase().intValue()+"modalidad"+proyectoActual.getModalidad().getId().toString()+"pgd"+(proyectoActual.getPlanGlobalDesarrollo()==null?"nulo:":proyectoActual.getPlanGlobalDesarrollo().getId().toString())+"resumen"+proyectoActual.getResumen()+"duracion"+(proyectoActual.getDuracion()==null?"nulo":String.valueOf(proyectoActual.getDuracion().intValue()))+"valor"+(proyectoActual.getValorSolicitado()==null?"nulo":String.valueOf(proyectoActual.getValorSolicitado().longValue()))+"otros"+(proyectoActual.getOtrosAportes()==null?"nulo":String.valueOf(proyectoActual.getOtrosAportes().longValue()))+"fech"+(proyectoActual.getFechaTentativaInicio()==null?"nulo":proyectoActual.getFechaTentativaInicio().toString()));

            if (proyectoActual.getId() != null) {
                // Ing. Wilver Alexander Martínez Martínez -wam²
                // Cambio - Registro de cambios
                Persona personaAux = new Persona();
                personaAux = (Persona) sesion.getAttribute("persona");

                Formulario formulario = new Formulario();
                List listaFormulario = new ArrayList();

                listaFormulario = servicioGeneral.obtenerListaObjetos("Formulario where id ='0'");
                formulario = (Formulario) listaFormulario.get(0);

                HistoricoFormularioProyecto historicoFormualrioProyecto = new HistoricoFormularioProyecto();
                historicoFormualrioProyecto.setDocPersona(personaAux.getId().getDocumento());
                historicoFormualrioProyecto.setTipoDocumentoPersona(personaAux.getId().getTipoDocumento());
                historicoFormualrioProyecto.setFormulario(formulario);
                historicoFormualrioProyecto.setProyecto(proyectoActual);
                historicoFormualrioProyecto.setFechaCambio(new Date());
                servicioGeneral.guardarObjeto(historicoFormualrioProyecto);
            }

            if (mostrarSiConvLibros) {
                guardarProyectoConvLibro();
            } else if (mostrarSiArticulosUno) {
                guardarProyectoConvArticulos();
            } else if (mostrarSiArticulosDos) {
                guardarProyectoConvArticulos();
            } else if (mostrarSiSolicitudISBN) {
                guardarProyectoSolISBN();
            }
            
            if(validarSiEsEstudianteLiderProyecto(proyectoActual.getId()) && proyectoActual.getId() != null){
          	  enviarCorreoEdicionInfoPryEstLider(proyectoActual.getId());
            }

            return "irSubirArchivo";

        } else {
            return "";
        }
    }

    public void guardarProyectoConvArticulos() {
        System.out.println("<========== GUARDAR CONVOCATORIA ARTICULOS UNO ==========>");

        if (proyectoExiste) {

            for (InvestigadorProyecto inv : listaParticipantesBorrados) {
                servicioGeneral.eliminarObjeto(inv);
            }

            servicioProyecto.ingresarProyecto(proyectoActual);
        }

        if (listaParticipantes.size() > 0) {
            for (int i = 0; i < listaParticipantes.size(); i++) {
                proyectoActual.adicionarInvestigadorProyecto(listaParticipantes.get(i));
            }
        }

        servicioProyecto.ingresarProyecto(proyectoActual);
    }

    public Convocatoria getConvocatoriaActual() {
        return convocatoriaActual;
    }

    public void setConvocatoriaActual(Convocatoria convocatoriaActual) {
        this.convocatoriaActual = convocatoriaActual;
    }

    public PalabraClave getPalabraClave() {
        return palabraClave;
    }

    public void setPalabraClave(PalabraClave palabraClave) {
        this.palabraClave = palabraClave;
    }

    public PalabraClave getPalabraClaveTabla() {
        return palabraClaveTabla;
    }

    public void setPalabraClaveTabla(PalabraClave palabraClaveTabla) {
        this.palabraClaveTabla = palabraClaveTabla;
    }

    public PalabraClave getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(PalabraClave keyWord) {
        this.keyWord = keyWord;
    }

    public List<PalabraClave> getListaPalabrasClave() {
        return listaPalabrasClave;
    }

    public void setListaPalabrasClave(List<PalabraClave> listaPalabrasClave) {
        this.listaPalabrasClave = listaPalabrasClave;
    }

    public String getLinkLineas() {
        return linkLineas;
    }

    public void setLinkLineas(String linkLineas) {
        this.linkLineas = linkLineas;
    }

    public String getAreaCiencia() {
        return areaCiencia;
    }

    public void setAreaCiencia(String areaCiencia) {
        this.areaCiencia = areaCiencia;
    }

    public String getAreaCienciaSec() {
        return areaCienciaSec;
    }

    public void setAreaCienciaSec(String areaCienciaSec) {
        this.areaCienciaSec = areaCienciaSec;
    }

    public List getListaAreaCiencia() {
        return listaAreaCiencia;
    }

    public void setListaAreaCiencia(List listaAreaCiencia) {
        this.listaAreaCiencia = listaAreaCiencia;
    }

    public SelectItem[] getAreaCienciaItems() {
        return areaCienciaItems;
    }

    public void setAreaCienciaItems(SelectItem[] areaCienciaItems) {
        this.areaCienciaItems = areaCienciaItems;
    }

    public String getDependenciaAdicionada() {
        return dependenciaAdicionada;
    }

    public void setDependenciaAdicionada(String dependenciaAdicionada) {
        this.dependenciaAdicionada = dependenciaAdicionada;
    }

    public List<Dependencia> getDependenciasUN() {
        return dependenciasUN;
    }

    public void setDependenciasUN(List<Dependencia> dependenciasUN) {
        this.dependenciasUN = dependenciasUN;
    }

    public SelectItem[] getDependenciaItem() {
        return dependenciaItem;
    }

    public void setDependenciaItem(SelectItem[] dependenciaItem) {
        this.dependenciaItem = dependenciaItem;
    }

    public DependenciaAreaResponsabilidad getDependenciaAreaResponsabilidad() {
        return dependenciaAreaResponsabilidad;
    }

    public void setDependenciaAreaResponsabilidad(DependenciaAreaResponsabilidad dependenciaAreaResponsabilidad) {
        this.dependenciaAreaResponsabilidad = dependenciaAreaResponsabilidad;
    }

    public DependenciaAreaResponsabilidad getDependenciaAreaResponsabilidadSeleccionada() {
        return dependenciaAreaResponsabilidadSeleccionada;
    }

    public void setDependenciaAreaResponsabilidadSeleccionada(
            DependenciaAreaResponsabilidad dependenciaAreaResponsabilidadSeleccionada) {
        this.dependenciaAreaResponsabilidadSeleccionada = dependenciaAreaResponsabilidadSeleccionada;
    }

    public InvestigadorProyecto getInvestigadorProyectoNuevo() {
        return investigadorProyectoNuevo;
    }

    public void setInvestigadorProyectoNuevo(InvestigadorProyecto investigadorProyectoNuevo) {
        this.investigadorProyectoNuevo = investigadorProyectoNuevo;
    }

    public List getListaTipoDocumento() {
        return listaTipoDocumento;
    }

    public void setListaTipoDocumento(List listaTipoDocumento) {
        this.listaTipoDocumento = listaTipoDocumento;
    }

    public SelectItem[] getTipoDocumentoItem() {
        return tipoDocumentoItem;
    }

    public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
        this.tipoDocumentoItem = tipoDocumentoItem;
    }

    public List getListaInvestigadoresVista() {
        return listaInvestigadoresVista;
    }

    public void setListaInvestigadoresVista(List listaInvestigadoresVista) {
        this.listaInvestigadoresVista = listaInvestigadoresVista;
    }

    public boolean isInvestigadorExiste() {
        return investigadorExiste;
    }

    public void setInvestigadorExiste(boolean investigadorExiste) {
        this.investigadorExiste = investigadorExiste;
    }

    public SelectItem[] getAutores() {
        return autores;
    }

    public void setAutores(SelectItem[] autores) {
        this.autores = autores;
    }

    public InvestigadorProyecto getCopiaValidacionInvestigador() {
        return copiaValidacionInvestigador;
    }

    public void setCopiaValidacionInvestigador(InvestigadorProyecto copiaValidacionInvestigador) {
        this.copiaValidacionInvestigador = copiaValidacionInvestigador;
    }

    public InvestigadorProyecto getInvestigadorProyectoActual() {
        return investigadorProyectoActual;
    }

    public void setInvestigadorProyectoActual(InvestigadorProyecto investigadorProyectoActual) {
        this.investigadorProyectoActual = investigadorProyectoActual;
    }

    public TipoDocumento getTipoDocumentoCoInv2() {
        return tipoDocumentoCoInv2;
    }

    public void setTipoDocumentoCoInv2(TipoDocumento tipoDocumentoCoInv2) {
        this.tipoDocumentoCoInv2 = tipoDocumentoCoInv2;
    }

    public String getDocumentoCoinv2() {
        return documentoCoinv2;
    }

    public void setDocumentoCoinv2(String documentoCoinv2) {
        this.documentoCoinv2 = documentoCoinv2;
    }

    public String getTipoInvestigador() {
        return tipoInvestigador;
    }

    public void setTipoInvestigador(String tipoInvestigador) {
        this.tipoInvestigador = tipoInvestigador;
    }

    public List<InvestigadorProyecto> getListaParticipantes() {
        List<InvestigadorProyecto> list = new ArrayList<InvestigadorProyecto>();
        Iterator it = listaParticipantes.iterator();
        while (it.hasNext()) {
            InvestigadorProyecto ip = (InvestigadorProyecto) it.next();
            if (ip.getFuncion() != null && !ip.getFuncion().equals("Creador")) {
                list.add(ip);
            }
        }
        return list;
    }

    public void setListaParticipantes(List<InvestigadorProyecto> listaParticipantes) {
        this.listaParticipantes = listaParticipantes;
    }

    public List<InvestigadorProyecto> getListaParticipantesBorrados() {
        return listaParticipantesBorrados;
    }

    public void setListaParticipantesBorrados(List<InvestigadorProyecto> listaParticipantesBorrados) {
        this.listaParticipantesBorrados = listaParticipantesBorrados;
    }

    public InvestigadorExterno getInvestigadorExterno() {
        return investigadorExterno;
    }

    public void setInvestigadorExterno(InvestigadorExterno investigadorExterno) {
        this.investigadorExterno = investigadorExterno;
    }

    public String getInsitucionNombre() {
        return insitucionNombre;
    }

    public void setInsitucionNombre(String insitucionNombre) {
        this.insitucionNombre = insitucionNombre;
    }

    public boolean isEsOtraVinculacion() {
        return esOtraVinculacion;
    }

    public void setEsOtraVinculacion(boolean esOtraVinculacion) {
        this.esOtraVinculacion = esOtraVinculacion;
    }

    public SelectItem[] getGeneroItem() {
        return generoItem;
    }

    public void setGeneroItem(SelectItem[] generoItem) {
        this.generoItem = generoItem;
    }

    public InvestigadorProyecto getParticipante() {
        return participante;
    }

    public void setParticipante(InvestigadorProyecto participante) {
        this.participante = participante;
    }

    public boolean isProyectoExiste() {
        return proyectoExiste;
    }

    public void setProyectoExiste(boolean proyectoExiste) {
        this.proyectoExiste = proyectoExiste;
    }

    public List getListaAreasPrimSec() {
        return listaAreasPrimSec;
    }

    public void setListaAreasPrimSec(List listaAreasPrimSec) {
        this.listaAreasPrimSec = listaAreasPrimSec;
    }

    public SelectItem[] getTipoEventoItems() {
        return tipoEventoItems;
    }

    public void setTipoEventoItems(SelectItem[] tipoEventoItems) {
        this.tipoEventoItems = tipoEventoItems;
    }

    public List<DominioDetalle> getListaTipoEvento() {
        return listaTipoEvento;
    }

    public void setListaTipoEvento(List<DominioDetalle> listaTipoEvento) {
        this.listaTipoEvento = listaTipoEvento;
    }

    public boolean isMostrarOtroTipoEvento() {
        return mostrarOtroTipoEvento;
    }

    public void setMostrarOtroTipoEvento(boolean mostrarOtroTipoEvento) {
        this.mostrarOtroTipoEvento = mostrarOtroTipoEvento;
    }

    public boolean isMostrarSiConvLibros() {
        return mostrarSiConvLibros;
    }

    public void setMostrarSiConvLibros(boolean mostrarSiConvLibros) {
        this.mostrarSiConvLibros = mostrarSiConvLibros;
    }

    public boolean isMostrarSiArticulosUno() {
        return mostrarSiArticulosUno;
    }

    public void setMostrarSiArticulosUno(boolean mostrarSiArticulosUno) {
        this.mostrarSiArticulosUno = mostrarSiArticulosUno;
    }

    public boolean isMostrarSiArticulosDos() {
        return mostrarSiArticulosDos;
    }

    public void setMostrarSiArticulosDos(boolean mostrarSiArticulosDos) {
        this.mostrarSiArticulosDos = mostrarSiArticulosDos;
    }

    public String getDocumentoCoinv() {
        return documentoCoinv;
    }

    public void setDocumentoCoinv(String documentoCoinv) {
        this.documentoCoinv = documentoCoinv;
    }

    public TipoDocumento getTipoDocumentoCoInv() {
        return tipoDocumentoCoInv;
    }

    public void setTipoDocumentoCoInv(TipoDocumento tipoDocumentoCoInv) {
        this.tipoDocumentoCoInv = tipoDocumentoCoInv;
    }

    public String getNombreCompletoPersonaActual() {
        return nombreCompletoPersonaActual;
    }

    public void setNombreCompletoPersonaActual(String nombreCompletoPersonaActual) {
        this.nombreCompletoPersonaActual = nombreCompletoPersonaActual;
    }

    public String getNombreSede() {
        return nombreSede;
    }

    public void setNombreSede(String nombreSede) {
        this.nombreSede = nombreSede;
    }

    public String getNombreFacultad() {
        return nombreFacultad;
    }

    public void setNombreFacultad(String nombreFacultad) {
        this.nombreFacultad = nombreFacultad;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public String getEmailPersonaActual() {
        return emailPersonaActual;
    }

    public void setEmailPersonaActual(String emailPersonaActual) {
        this.emailPersonaActual = emailPersonaActual;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List getListaAreas() {
        return listaAreas;
    }

    public void setListaAreas(List listaAreas) {
        this.listaAreas = listaAreas;
    }

    public Persona getPersonaActual() {
        return personaActual;
    }

    public void setPersonaActual(Persona personaActual) {
        this.personaActual = personaActual;
    }

    public boolean isMostrarSiSolicitudISBN() {
        return mostrarSiSolicitudISBN;
    }

    public void setMostrarSiSolicitudISBN(boolean mostrarSiSolicitudISBN) {
        this.mostrarSiSolicitudISBN = mostrarSiSolicitudISBN;
    }

    public List<SelectItem> getListaMateriasISBN() {
        return listaMateriasISBN;
    }

    public void setListaMateriasISBN(List<SelectItem> listaMateriasISBN) {
        this.listaMateriasISBN = listaMateriasISBN;
    }

    public List<SelectItem> getListaTipoContenidoISBN() {
        return listaTipoContenidoISBN;
    }

    public void setListaTipoContenidoISBN(List<SelectItem> listaTipoContenidoISBN) {
        this.listaTipoContenidoISBN = listaTipoContenidoISBN;
    }

    public List<SelectItem> getListaIdiomasISBN() {
        return listaIdiomasISBN;
    }

    public void setListaIdiomasISBN(List<SelectItem> listaIdiomasISBN) {
        this.listaIdiomasISBN = listaIdiomasISBN;
    }

    public List<SelectItem> getListaRolAutorISBN() {
        return listaRolAutorISBN;
    }

    public void setListaRolAutorISBN(List<SelectItem> listaRolAutorISBN) {
        this.listaRolAutorISBN = listaRolAutorISBN;
    }

    public List<SelectItem> getListaTipoSoporteISBN() {
        return listaTipoSoporteISBN;
    }

    public void setListaTipoSoporteISBN(List<SelectItem> listaTipoSoporteISBN) {
        this.listaTipoSoporteISBN = listaTipoSoporteISBN;
    }

    public List<SelectItem> getListaTipoEncuadernacionISBN() {
        return listaTipoEncuadernacionISBN;
    }

    public void setListaTipoEncuadernacionISBN(List<SelectItem> listaTipoEncuadernacionISBN) {
        this.listaTipoEncuadernacionISBN = listaTipoEncuadernacionISBN;
    }

    public List<SelectItem> getListaTipoPapelISBN() {
        return listaTipoPapelISBN;
    }

    public void setListaTipoPapelISBN(List<SelectItem> listaTipoPapelISBN) {
        this.listaTipoPapelISBN = listaTipoPapelISBN;
    }

    public List<SelectItem> getListaGramajeISBN() {
        return listaGramajeISBN;
    }

    public void setListaGramajeISBN(List<SelectItem> listaGramajeISBN) {
        this.listaGramajeISBN = listaGramajeISBN;
    }

    public List<SelectItem> getListaTipoImpresionISBN() {
        return listaTipoImpresionISBN;
    }

    public void setListaTipoImpresionISBN(List<SelectItem> listaTipoImpresionISBN) {
        this.listaTipoImpresionISBN = listaTipoImpresionISBN;
    }

    public List<SelectItem> getListaNumTintasISBN() {
        return listaNumTintasISBN;
    }

    public void setListaNumTintasISBN(List<SelectItem> listaNumTintasISBN) {
        this.listaNumTintasISBN = listaNumTintasISBN;
    }

    public List<SelectItem> getListaMedElecISBN() {
        return listaMedElecISBN;
    }

    public void setListaMedElecISBN(List<SelectItem> listaMedElecISBN) {
        this.listaMedElecISBN = listaMedElecISBN;
    }

    public List<SelectItem> getListaFormatoISBN() {
        return listaFormatoISBN;
    }

    public void setListaFormatoISBN(List<SelectItem> listaFormatoISBN) {
        this.listaFormatoISBN = listaFormatoISBN;
    }

    public List<SelectItem> getListaTamañoISBN() {
        return listaTamañoISBN;
    }

    public void setListaTamañoISBN(List<SelectItem> listaTamañoISBN) {
        this.listaTamañoISBN = listaTamañoISBN;
    }

    public List<SelectItem> getListaDescFisicaISBN() {
        return listaDescFisicaISBN;
    }

    public void setListaDescFisicaISBN(List<SelectItem> listaDescFisicaISBN) {
        this.listaDescFisicaISBN = listaDescFisicaISBN;
    }

    public List<SelectItem> getListaCiudades() {
        return listaCiudades;
    }

    public void setListaCiudades(List<SelectItem> listaCiudades) {
        this.listaCiudades = listaCiudades;
    }

    public List<SelectItem> getListaDepartamentos() {
        return listaDepartamentos;
    }

    public void setListaDepartamentos(List<SelectItem> listaDepartamentos) {
        this.listaDepartamentos = listaDepartamentos;
    }

    public boolean isMostrarCoeditor() {
        return mostrarCoeditor;
    }

    public void setMostrarCoeditor(boolean mostrarCoeditor) {
        this.mostrarCoeditor = mostrarCoeditor;
    }

    public SelectItem[] getPaisItem() {
        return paisItem;
    }

    public void setPaisItem(SelectItem[] paisItem) {
        this.paisItem = paisItem;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getRolAutorISBN() {
        return rolAutorISBN;
    }

    public void setRolAutorISBN(String rolAutorISBN) {
        this.rolAutorISBN = rolAutorISBN;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDescripcionMateriasISBN() {
        return descripcionMateriasISBN;
    }

    public void setDescripcionMateriasISBN(String descripcionMateriasISBN) {
        this.descripcionMateriasISBN = descripcionMateriasISBN;
    }

    public String getDescripcionTipoContenidoISBN() {
        return descripcionTipoContenidoISBN;
    }

    public void setDescripcionTipoContenidoISBN(String descripcionTipoContenidoISBN) {
        this.descripcionTipoContenidoISBN = descripcionTipoContenidoISBN;
    }

    public String getDescripcionIdiomasISBN() {
        return descripcionIdiomaPrincipalISBN;
    }

    public void setDescripcionIdiomasISBN(String descripcionIdiomasISBN) {
        this.descripcionIdiomaPrincipalISBN = descripcionIdiomasISBN;
    }

    public String getDescripcionRolAutorISBN() {
        return descripcionRolAutorISBN;
    }

    public void setDescripcionRolAutorISBN(String descripcionRolAutorISBN) {
        this.descripcionRolAutorISBN = descripcionRolAutorISBN;
    }

    public String getDescripcionTipoSoporteISBN() {
        return descripcionTipoSoporteISBN;
    }

    public void setDescripcionTipoSoporteISBN(String descripcionTipoSoporteISBN) {
        this.descripcionTipoSoporteISBN = descripcionTipoSoporteISBN;
    }

    public String getDescripcionTipoEncuadernacionISBN() {
        return descripcionTipoEncuadernacionISBN;
    }

    public void setDescripcionTipoEncuadernacionISBN(String descripcionTipoEncuadernacionISBN) {
        this.descripcionTipoEncuadernacionISBN = descripcionTipoEncuadernacionISBN;
    }

    public String getDescripcionTipoPapelISBN() {
        return descripcionTipoPapelISBN;
    }

    public void setDescripcionTipoPapelISBN(String descripcionTipoPapelISBN) {
        this.descripcionTipoPapelISBN = descripcionTipoPapelISBN;
    }

    public String getDescripcionGramajeISBN() {
        return descripcionGramajeISBN;
    }

    public void setDescripcionGramajeISBN(String descripcionGramajeISBN) {
        this.descripcionGramajeISBN = descripcionGramajeISBN;
    }

    public String getDescripcionTipoImpresionISBN() {
        return descripcionTipoImpresionISBN;
    }

    public void setDescripcionTipoImpresionISBN(String descripcionTipoImpresionISBN) {
        this.descripcionTipoImpresionISBN = descripcionTipoImpresionISBN;
    }

    public String getDescripcionNumTintasISBN() {
        return descripcionNumTintasISBN;
    }

    public void setDescripcionNumTintasISBN(String descripcionNumTintasISBN) {
        this.descripcionNumTintasISBN = descripcionNumTintasISBN;
    }

    public String getDescripcionMedElecISBN() {
        return descripcionMedElecISBN;
    }

    public void setDescripcionMedElecISBN(String descripcionMedElecISBN) {
        this.descripcionMedElecISBN = descripcionMedElecISBN;
    }

    public String getDescripcionFormatoISBN() {
        return descripcionFormatoISBN;
    }

    public void setDescripcionFormatoISBN(String descripcionFormatoISBN) {
        this.descripcionFormatoISBN = descripcionFormatoISBN;
    }

    public String getDescripcionTamañoISBN() {
        return descripcionTamañoISBN;
    }

    public void setDescripcionTamañoISBN(String descripcionTamañoISBN) {
        this.descripcionTamañoISBN = descripcionTamañoISBN;
    }

    public String getDescripcionDescFisicaISBN() {
        return descripcionDescFisicaISBN;
    }

    public void setDescripcionDescFisicaISBN(String descripcionDescFisicaISBN) {
        this.descripcionDescFisicaISBN = descripcionDescFisicaISBN;
    }

    public String getDescripcionIdiomaPrincipalISBN() {
        return descripcionIdiomaPrincipalISBN;
    }

    public void setDescripcionIdiomaPrincipalISBN(String descripcionIdiomaPrincipalISBN) {
        this.descripcionIdiomaPrincipalISBN = descripcionIdiomaPrincipalISBN;
    }

    public String getDescripcionIdiomaOriginalISBN() {
        return descripcionIdiomaOriginalISBN;
    }

    public void setDescripcionIdiomaOriginalISBN(String descripcionIdiomaOriginalISBN) {
        this.descripcionIdiomaOriginalISBN = descripcionIdiomaOriginalISBN;
    }

    public String getDescripcionIdiomaDestinoISBN() {
        return descripcionIdiomaDestinoISBN;
    }

    public void setDescripcionIdiomaDestinoISBN(String descripcionIdiomaDestinoISBN) {
        this.descripcionIdiomaDestinoISBN = descripcionIdiomaDestinoISBN;
    }

    public String getDescripcionDepartamento() {
        return descripcionDepartamento;
    }

    public void setDescripcionDepartamento(String descripcionDepartamento) {
        this.descripcionDepartamento = descripcionDepartamento;
    }

    public String getDescripcionCiudad() {
        return descripcionCiudad;
    }

    public void setDescripcionCiudad(String descripcionCiudad) {
        this.descripcionCiudad = descripcionCiudad;
    }

	public List<SelectItem> getListaTipoObraISBN2018() {
		return listaTipoObraISBN2018;
	}

	public void setListaTipoObraISBN2018(List<SelectItem> listaTipoObraISBN2018) {
		this.listaTipoObraISBN2018 = listaTipoObraISBN2018;
	}

	public List<SelectItem> getListaTipoPublicacionISBN() {
		return listaTipoPublicacionISBN;
	}

	public void setListaTipoPublicacionISBN(List<SelectItem> listaTipoPublicacionISBN) {
		this.listaTipoPublicacionISBN = listaTipoPublicacionISBN;
	}

	public List<SelectItem> getListaAudienciaISBN() {
		return listaAudienciaISBN;
	}

	public void setListaAudienciaISBN(List<SelectItem> listaAudienciaISBN) {
		this.listaAudienciaISBN = listaAudienciaISBN;
	}

	public List<SelectItem> getListaTipoISBN() {
		return listaTipoISBN;
	}

	public void setListaTipoISBN(List<SelectItem> listaTipoISBN) {
		this.listaTipoISBN = listaTipoISBN;
	}

	public List<SelectItem> getListaTipoContenidoProducto() {
		return listaTipoContenidoProducto;
	}

	public void setListaTipoContenidoProducto(List<SelectItem> listaTipoContenidoProducto) {
		this.listaTipoContenidoProducto = listaTipoContenidoProducto;
	}

	public List<SelectItem> getListaProtTecnicaArcDigISBN() {
		return listaProtTecnicaArcDigISBN;
	}

	public void setListaProtTecnicaArcDigISBN(List<SelectItem> listaProtTecnicaArcDigISBN) {
		this.listaProtTecnicaArcDigISBN = listaProtTecnicaArcDigISBN;
	}

	public List<SelectItem> getListaPermisoUsoISBN() {
		return listaPermisoUsoISBN;
	}

	public void setListaPermisoUsoISBN(List<SelectItem> listaPermisoUsoISBN) {
		this.listaPermisoUsoISBN = listaPermisoUsoISBN;
	}

	public List<SelectItem> getListaTipoSoporteISBN2018() {
		return listaTipoSoporteISBN2018;
	}

	public void setListaTipoSoporteISBN2018(List<SelectItem> listaTipoSoporteISBN2018) {
		this.listaTipoSoporteISBN2018 = listaTipoSoporteISBN2018;
	}

	public String getDescripcionIdiomaOrigenISBN() {
		return descripcionIdiomaOrigenISBN;
	}

	public void setDescripcionIdiomaOrigenISBN(String descripcionIdiomaOrigenISBN) {
		this.descripcionIdiomaOrigenISBN = descripcionIdiomaOrigenISBN;
	}

	public boolean isMostrarResena() {
		return mostrarResena;
	}

	public void setMostrarResena(boolean mostrarResena) {
		this.mostrarResena = mostrarResena;
	}

	public SelectItem[] getListaClasificacionTHEMA_N1() {
		return listaClasificacionTHEMA_N1;
	}

	public void setListaClasificacionTHEMA_N1(SelectItem[] listaClasificacionTHEMA_N1) {
		this.listaClasificacionTHEMA_N1 = listaClasificacionTHEMA_N1;
	}

	public SelectItem[] getListaClasificacionTHEMA_N2() {
		return listaClasificacionTHEMA_N2;
	}

	public void setListaClasificacionTHEMA_N2(SelectItem[] listaClasificacionTHEMA_N2) {
		this.listaClasificacionTHEMA_N2 = listaClasificacionTHEMA_N2;
	}

	public SelectItem[] getListaClasificacionTHEMA_N3() {
		return listaClasificacionTHEMA_N3;
	}

	public void setListaClasificacionTHEMA_N3(SelectItem[] listaClasificacionTHEMA_N3) {
		this.listaClasificacionTHEMA_N3 = listaClasificacionTHEMA_N3;
	}

	public SelectItem[] getListaClasificacionTHEMA_N4() {
		return listaClasificacionTHEMA_N4;
	}

	public void setListaClasificacionTHEMA_N4(SelectItem[] listaClasificacionTHEMA_N4) {
		this.listaClasificacionTHEMA_N4 = listaClasificacionTHEMA_N4;
	}

	public SelectItem[] getListaClasificacionTHEMA_N5() {
		return listaClasificacionTHEMA_N5;
	}

	public void setListaClasificacionTHEMA_N5(SelectItem[] listaClasificacionTHEMA_N5) {
		this.listaClasificacionTHEMA_N5 = listaClasificacionTHEMA_N5;
	}

	public SelectItem[] getListaClasificacionTHEMA_N6() {
		return listaClasificacionTHEMA_N6;
	}

	public void setListaClasificacionTHEMA_N6(SelectItem[] listaClasificacionTHEMA_N6) {
		this.listaClasificacionTHEMA_N6 = listaClasificacionTHEMA_N6;
	}

	public SelectItem[] getListaLugar_N1() {
		return listaLugar_N1;
	}

	public void setListaLugar_N1(SelectItem[] listaLugar_N1) {
		this.listaLugar_N1 = listaLugar_N1;
	}

	public SelectItem[] getListaLugar_N2() {
		return listaLugar_N2;
	}

	public void setListaLugar_N2(SelectItem[] listaLugar_N2) {
		this.listaLugar_N2 = listaLugar_N2;
	}

	public SelectItem[] getListaLugar_N3() {
		return listaLugar_N3;
	}

	public void setListaLugar_N3(SelectItem[] listaLugar_N3) {
		this.listaLugar_N3 = listaLugar_N3;
	}

	public SelectItem[] getListaLugar_N4() {
		return listaLugar_N4;
	}

	public void setListaLugar_N4(SelectItem[] listaLugar_N4) {
		this.listaLugar_N4 = listaLugar_N4;
	}

	public SelectItem[] getListaLugar_N5() {
		return listaLugar_N5;
	}

	public void setListaLugar_N5(SelectItem[] listaLugar_N5) {
		this.listaLugar_N5 = listaLugar_N5;
	}

	public SelectItem[] getListaLugar_N6() {
		return listaLugar_N6;
	}

	public void setListaLugar_N6(SelectItem[] listaLugar_N6) {
		this.listaLugar_N6 = listaLugar_N6;
	}

	public SelectItem[] getListaLugar_N7() {
		return listaLugar_N7;
	}

	public void setListaLugar_N7(SelectItem[] listaLugar_N7) {
		this.listaLugar_N7 = listaLugar_N7;
	}

	public SelectItem[] getListaLugar_N8() {
		return listaLugar_N8;
	}

	public void setListaLugar_N8(SelectItem[] listaLugar_N8) {
		this.listaLugar_N8 = listaLugar_N8;
	}

	public SelectItem[] getListaLugar_N9() {
		return listaLugar_N9;
	}

	public void setListaLugar_N9(SelectItem[] listaLugar_N9) {
		this.listaLugar_N9 = listaLugar_N9;
	}

	public SelectItem[] getListaIdioma_N1() {
		return listaIdioma_N1;
	}

	public void setListaIdioma_N1(SelectItem[] listaIdioma_N1) {
		this.listaIdioma_N1 = listaIdioma_N1;
	}

	public SelectItem[] getListaIdioma_N2() {
		return listaIdioma_N2;
	}

	public void setListaIdioma_N2(SelectItem[] listaIdioma_N2) {
		this.listaIdioma_N2 = listaIdioma_N2;
	}

	public SelectItem[] getListaIdioma_N3() {
		return listaIdioma_N3;
	}

	public void setListaIdioma_N3(SelectItem[] listaIdioma_N3) {
		this.listaIdioma_N3 = listaIdioma_N3;
	}

	public SelectItem[] getListaIdioma_N4() {
		return listaIdioma_N4;
	}

	public void setListaIdioma_N4(SelectItem[] listaIdioma_N4) {
		this.listaIdioma_N4 = listaIdioma_N4;
	}

	public SelectItem[] getListaIdioma_N5() {
		return listaIdioma_N5;
	}

	public void setListaIdioma_N5(SelectItem[] listaIdioma_N5) {
		this.listaIdioma_N5 = listaIdioma_N5;
	}

	public SelectItem[] getListaPerHistorico_N1() {
		return listaPerHistorico_N1;
	}

	public void setListaPerHistorico_N1(SelectItem[] listaPerHistorico_N1) {
		this.listaPerHistorico_N1 = listaPerHistorico_N1;
	}

	public SelectItem[] getListaPerHistorico_N2() {
		return listaPerHistorico_N2;
	}

	public void setListaPerHistorico_N2(SelectItem[] listaPerHistorico_N2) {
		this.listaPerHistorico_N2 = listaPerHistorico_N2;
	}

	public SelectItem[] getListaPerHistorico_N3() {
		return listaPerHistorico_N3;
	}

	public void setListaPerHistorico_N3(SelectItem[] listaPerHistorico_N3) {
		this.listaPerHistorico_N3 = listaPerHistorico_N3;
	}

	public SelectItem[] getListaPerHistorico_N4() {
		return listaPerHistorico_N4;
	}

	public void setListaPerHistorico_N4(SelectItem[] listaPerHistorico_N4) {
		this.listaPerHistorico_N4 = listaPerHistorico_N4;
	}

	public SelectItem[] getListaPerHistorico_N5() {
		return listaPerHistorico_N5;
	}

	public void setListaPerHistorico_N5(SelectItem[] listaPerHistorico_N5) {
		this.listaPerHistorico_N5 = listaPerHistorico_N5;
	}

	public SelectItem[] getListaFinDidactico_N1() {
		return listaFinDidactico_N1;
	}

	public void setListaFinDidactico_N1(SelectItem[] listaFinDidactico_N1) {
		this.listaFinDidactico_N1 = listaFinDidactico_N1;
	}

	public SelectItem[] getListaFinDidactico_N2() {
		return listaFinDidactico_N2;
	}

	public void setListaFinDidactico_N2(SelectItem[] listaFinDidactico_N2) {
		this.listaFinDidactico_N2 = listaFinDidactico_N2;
	}

	public SelectItem[] getListaFinDidactico_N3() {
		return listaFinDidactico_N3;
	}

	public void setListaFinDidactico_N3(SelectItem[] listaFinDidactico_N3) {
		this.listaFinDidactico_N3 = listaFinDidactico_N3;
	}

	public SelectItem[] getListaFinDidactico_N4() {
		return listaFinDidactico_N4;
	}

	public void setListaFinDidactico_N4(SelectItem[] listaFinDidactico_N4) {
		this.listaFinDidactico_N4 = listaFinDidactico_N4;
	}

	public SelectItem[] getListaFinDidactico_N5() {
		return listaFinDidactico_N5;
	}

	public void setListaFinDidactico_N5(SelectItem[] listaFinDidactico_N5) {
		this.listaFinDidactico_N5 = listaFinDidactico_N5;
	}

	public SelectItem[] getListaEdadInteres_N1() {
		return listaEdadInteres_N1;
	}

	public void setListaEdadInteres_N1(SelectItem[] listaEdadInteres_N1) {
		this.listaEdadInteres_N1 = listaEdadInteres_N1;
	}

	public SelectItem[] getListaEdadInteres_N2() {
		return listaEdadInteres_N2;
	}

	public void setListaEdadInteres_N2(SelectItem[] listaEdadInteres_N2) {
		this.listaEdadInteres_N2 = listaEdadInteres_N2;
	}

	public SelectItem[] getListaEdadInteres_N3() {
		return listaEdadInteres_N3;
	}

	public void setListaEdadInteres_N3(SelectItem[] listaEdadInteres_N3) {
		this.listaEdadInteres_N3 = listaEdadInteres_N3;
	}

	public SelectItem[] getListaEdadInteres_N4() {
		return listaEdadInteres_N4;
	}

	public void setListaEdadInteres_N4(SelectItem[] listaEdadInteres_N4) {
		this.listaEdadInteres_N4 = listaEdadInteres_N4;
	}

	public SelectItem[] getListaEstilo_N1() {
		return listaEstilo_N1;
	}

	public void setListaEstilo_N1(SelectItem[] listaEstilo_N1) {
		this.listaEstilo_N1 = listaEstilo_N1;
	}

	public SelectItem[] getListaEstilo_N2() {
		return listaEstilo_N2;
	}

	public void setListaEstilo_N2(SelectItem[] listaEstilo_N2) {
		this.listaEstilo_N2 = listaEstilo_N2;
	}

	public SelectItem[] getListaDisponibilidad() {
		return listaDisponibilidad;
	}

	public void setListaDisponibilidad(SelectItem[] listaDisponibilidad) {
		this.listaDisponibilidad = listaDisponibilidad;
	}

	public SelectItem[] getListaTipoAcceso() {
		return listaTipoAcceso;
	}

	public void setListaTipoAcceso(SelectItem[] listaTipoAcceso) {
		this.listaTipoAcceso = listaTipoAcceso;
	}
}
