package co.edu.unal.hermes.vista;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PersonaTramiteBiodiversidad;
import co.edu.unal.hermes.modelo.TipoTramiteBiodiversidad;

public class ManejadorTramitesBiodiversidad extends ManejadorBasePermisosMarco {

    /**
     * 
     */
    private static final long serialVersionUID = 3139656841090707289L;
    private boolean esConsulta;
    private Long idTramite;
    private TipoTramiteBiodiversidad tramiteBiodiversidad;
    private List<TipoTramiteBiodiversidad> tiposTramiteBiodiversidad;
    private PersonaTramiteBiodiversidad personaEncargadaBiodiversidad;
    private List<InvestigadorInterno> coordinadores;
    private List<SelectItem> dependenciasItem;
    private List<SelectItem> listaCoordinadoresItem;
    private String personaSeleccionada;
    private String dependenciaSeleccionada;
    private boolean tieneControlEncargadosBiodiversidad;

    public ManejadorTramitesBiodiversidad() {
        esConsulta = true;
        tieneControlEncargadosBiodiversidad = false;
        cargarControlEncargadosBiodiversidad();
        cargarTiposTramitesBiodiversidad();
    }
    
	private void cargarControlEncargadosBiodiversidad() {
		setTieneControlEncargadosBiodiversidad(false);
		Persona personaBiodiversidad = servicioBiodiversidad.obtenerPersonaControlBiodiversidad();
		if (personaActual != null && personaBiodiversidad != null) {
			if (personaActual.getId().getDocumento().equals(personaBiodiversidad.getId().getDocumento())) {
				setTieneControlEncargadosBiodiversidad(true);
			}
		}
	}

    private void cargarTiposTramitesBiodiversidad() {
        tiposTramiteBiodiversidad = servicioGeneral.obtenerObjetosLimitado(TipoTramiteBiodiversidad.class,
                "select #id ttb.id, #nombre ttb.nombre from TipoTramiteBiodiversidad ttb where ttb.estado = 'A'");
    }

    public String modificarTramite() {
        esConsulta = false;
        obtenerTramite();
        return "irDetalleTramiteBiodiversidad";

    }

    public String consultarTramite() {
        esConsulta = true;
        obtenerTramite();
        return "irDetalleTramiteBiodiversidad";
    }

    private void obtenerTramite() {
        List<TipoTramiteBiodiversidad> tramites = servicioGeneral.obtenerObjetos(TipoTramiteBiodiversidad.class,
                "select ttb from TipoTramiteBiodiversidad ttb where ttb.id = '" + idTramite + "'");
        if (!esListaVacia(tramites)) {
            tramiteBiodiversidad = tramites.get(0);
            consultarDependencias();
        } else {
            tramiteBiodiversidad = new TipoTramiteBiodiversidad();
        }
    }

    public SelectItem[] getNivelTramiteItem() {
        SelectItem[] nivelesItem = new SelectItem[4];
        nivelesItem[0] = new SelectItem("", "");
        nivelesItem[1] = new SelectItem(TipoTramiteBiodiversidad.VICERRECTORIA, "Vicerrectoría de Investigación");
        nivelesItem[2] = new SelectItem(TipoTramiteBiodiversidad.SEDE, "Dirección de Investigación de Sede");
        nivelesItem[3] = new SelectItem(TipoTramiteBiodiversidad.FACULTAD,
                "Vicedecanatura de Investigación de Facultad");
        return nivelesItem;
    }

    public void consultarDependencias() {
        dependenciasItem = new ArrayList<SelectItem>();
        List<Dependencia> dependencias = new ArrayList<Dependencia>();
        String sql = "select d from Dependencia d where d.estado = 'A' and ";
        if (tramiteBiodiversidad.getNivelTramite().equals(TipoTramiteBiodiversidad.SEDE)) {
            dependencias = servicioGeneral.obtenerObjetos(Dependencia.class,
                    sql + "d.esSede = 'Y' order by d.nombre asc");
        } else if (tramiteBiodiversidad.getNivelTramite().equals(TipoTramiteBiodiversidad.FACULTAD)) {
            dependencias = servicioGeneral.obtenerObjetos(Dependencia.class,
                    sql + "d.esFacultad = 'Y' order by d.nombre asc");
        } else {
            dependenciaSeleccionada = "1096";
        }

        if (!esListaVacia(dependencias)) {
            for (int i = 0; i < dependencias.size(); i++) {
                Dependencia dep = (Dependencia) dependencias.get(i);
                dependenciasItem.add(new SelectItem(dep.getId(), dep.getNombre()));
            }
        }
        consultarCoordinadoresDependencia();
    }

    public void consultarCoordinadoresDependencia() {
        listaCoordinadoresItem = new ArrayList<SelectItem>();
        String sql = "select ii from PersonaRol pr, InvestigadorInterno ii "
                + "where pr.nombre = 'C' and ii.id.documento = pr.documento and ii.id.tipoDocumento = pr.tipoDocumento and ";
        if (tramiteBiodiversidad.getNivelTramite().equals(TipoTramiteBiodiversidad.FACULTAD)) {
            coordinadores = servicioGeneral.obtenerObjetos(InvestigadorInterno.class,
                    sql + "ii.dependencia.facultad.id = '" + dependenciaSeleccionada + "'");
        } else if (tramiteBiodiversidad.getNivelTramite().equals(TipoTramiteBiodiversidad.SEDE)) {
            coordinadores = servicioGeneral.obtenerObjetos(InvestigadorInterno.class,
                    sql + "ii.dependencia.sede.id = '" + dependenciaSeleccionada + "'");
        } else {
            coordinadores = servicioGeneral.obtenerObjetos(InvestigadorInterno.class,
                    sql + "ii.dependencia.id = '" + dependenciaSeleccionada + "'");
        }

        if (!esListaVacia(coordinadores)) {
            for (int i = 0; i < coordinadores.size(); i++) {
                InvestigadorInterno coordinador = (InvestigadorInterno) coordinadores.get(i);
                listaCoordinadoresItem.add(
                        new SelectItem(coordinador.getId().getDocumento(), coordinador.getNombreCompletoMinusculas()));
            }
        }
    }

    public void agregarPersonaEncargada() {

        if (validarPersona(personaSeleccionada)) {
            PersonaTramiteBiodiversidad persona = new PersonaTramiteBiodiversidad();
            persona.setPersonaEncargada(consultarCoordinador(personaSeleccionada));
            persona.setDependencia(servicioDependencia.obtenerDependencia(dependenciaSeleccionada));
            persona.setTipoTramite(tramiteBiodiversidad);
            tramiteBiodiversidad.adicionarPersona(persona);
            servicioPersona.insertarCoordinadorAsesor(personaActual.getId(), persona.getPersonaEncargada().getId(), Dependencia.ID_VICERRECTORIA.toString());
        }
    }

    private InvestigadorInterno consultarCoordinador(String coordinador) {
        if (!esListaVacia(coordinadores)) {
            for (int i = 0; i < coordinadores.size(); i++) {
                InvestigadorInterno ii = (InvestigadorInterno) coordinadores.get(i);
                if (coordinador.equals(ii.getId().getDocumento())) {
                    return (InvestigadorInterno) coordinadores.get(i);
                }
            }
        }
        return null;
    }

    private boolean validarPersona(String documentoPersona) {
        boolean valida = true;

        if ((tramiteBiodiversidad.getNivelTramite().equals(TipoTramiteBiodiversidad.FACULTAD)
                || tramiteBiodiversidad.getNivelTramite().equals(TipoTramiteBiodiversidad.SEDE)
                        && esCadenaVacia(dependenciaSeleccionada))) {
            mensajeError("Debe seleccionar la dependencia para que se liste el personal disponible a asignar.");
            valida = false;
        }

        if (esCadenaVacia(documentoPersona)) {
            mensajeError(
                    "Debe seleccionar una persona para que sea agregada a la lista de personal encargado en la dependencia");
            valida = false;
        }

        if (!esListaVacia(tramiteBiodiversidad.getListaPersonas())) {
            for (int i = 0; i < tramiteBiodiversidad.getListaPersonas().size(); i++) {
                PersonaTramiteBiodiversidad persona = tramiteBiodiversidad.getListaPersonas().get(i);
                if (persona.getPersonaEncargada().getId().getDocumento().equals(documentoPersona)) {
                    mensajeError("La persona seleccionada ya se encuentra en la lista");
                    valida = false;
                    break;
                }

            }
        }

        if (!verificarCantidadPersonas()) {
            valida = false;
        }

        return valida;
    }

    private boolean verificarCantidadPersonas() {
        boolean valida = true;

        if (!tramiteBiodiversidad.isEsVariosEncargados()
                && !validarUnicoEncargado()) {
            valida = false;
        }

        return valida;
    }

    private boolean validarUnicoEncargado() {
        boolean valida = true;
        if (!esListaVacia(tramiteBiodiversidad.getListaPersonas())) {
            for (int i = 0; i < tramiteBiodiversidad.getListaPersonas().size(); i++) {
                PersonaTramiteBiodiversidad persona = tramiteBiodiversidad.getListaPersonas().get(i);
                if (persona.getDependencia().getId().equals(dependenciaSeleccionada)) {
                    mensajeError(
                            "Ya existe una persona asignada para el trámite en la dependencia, para el trámite actual no es posible asignar más de una persona por dependencia");
                    valida = false;
                    break;
                }
            }
        }
        return valida;
    }

    public void eliminarPersona() {
        if (personaEncargadaBiodiversidad != null) {
            tramiteBiodiversidad.borrarPersona(personaEncargadaBiodiversidad);
            servicioPersona.eliminarCoordinadorAsesor(personaActual.getId(), personaEncargadaBiodiversidad.getPersonaEncargada().getId(), Dependencia.ID_VICERRECTORIA.toString());
        }
    }

    public void guardarCambios() {
        servicioGeneral.guardarObjeto(tramiteBiodiversidad);
        mensajeInfo("El trámite ha sido actualizado");
    }

    public TipoTramiteBiodiversidad getTramiteBiodiversidad() {
        return tramiteBiodiversidad;
    }

    public void setTramiteBiodiversidad(TipoTramiteBiodiversidad tramiteBiodiversidad) {
        this.tramiteBiodiversidad = tramiteBiodiversidad;
    }

    public List<TipoTramiteBiodiversidad> getTiposTramiteBiodiversidad() {
        return tiposTramiteBiodiversidad;
    }

    public void setTiposTramiteBiodiversidad(List<TipoTramiteBiodiversidad> tiposTramiteBiodiversidad) {
        this.tiposTramiteBiodiversidad = tiposTramiteBiodiversidad;
    }

    public boolean isEsConsulta() {
        return esConsulta;
    }

    public void setEsConsulta(boolean esConsulta) {
        this.esConsulta = esConsulta;
    }

    public Long getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(Long idTramite) {
        this.idTramite = idTramite;
    }

    public PersonaTramiteBiodiversidad getPersonaEncargadaBiodiversidad() {
        return personaEncargadaBiodiversidad;
    }

    public void setPersonaEncargadaBiodiversidad(PersonaTramiteBiodiversidad personaEncargadaBiodiversidad) {
        this.personaEncargadaBiodiversidad = personaEncargadaBiodiversidad;
    }

    public List<SelectItem> getDependenciasItem() {
        return dependenciasItem;
    }

    public void setDependenciasItem(List<SelectItem> dependenciasItem) {
        this.dependenciasItem = dependenciasItem;
    }

    public List<SelectItem> getListaCoordinadoresItem() {
        return listaCoordinadoresItem;
    }

    public void setListaCoordinadoresItem(List<SelectItem> listaCoordinadoresItem) {
        this.listaCoordinadoresItem = listaCoordinadoresItem;
    }

    public String getPersonaSeleccionada() {
        return personaSeleccionada;
    }

    public void setPersonaSeleccionada(String personaSeleccionada) {
        this.personaSeleccionada = personaSeleccionada;
    }

    public String getDependenciaSeleccionada() {
        return dependenciaSeleccionada;
    }

    public void setDependenciaSeleccionada(String dependenciaSeleccionada) {
        this.dependenciaSeleccionada = dependenciaSeleccionada;
    }

    public List<InvestigadorInterno> getCoordinadores() {
        return coordinadores;
    }

    public void setCoordinadores(List<InvestigadorInterno> coordinadores) {
        this.coordinadores = coordinadores;
    }

	public boolean isTieneControlEncargadosBiodiversidad() {
		return tieneControlEncargadosBiodiversidad;
	}

	public void setTieneControlEncargadosBiodiversidad(boolean tieneControlEncargadosBiodiversidad) {
		this.tieneControlEncargadosBiodiversidad = tieneControlEncargadosBiodiversidad;
	}

}