/**
 * Modified by Mauricio Amaya Ríos<br/>
 * Date:  12/12/2013<br/>
 */

package co.edu.unal.hermes.vista.movilidad.aprobacion;

import java.util.ArrayList;
import java.util.List;

import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.MovilidadDocentesExterior;
import co.edu.unal.hermes.modelo.MovilidadEstudiantesPosgrado;
import co.edu.unal.hermes.modelo.MovilidadVisitanteExterior;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.TipoMovilidad;
import co.edu.unal.hermes.vista.movilidad.ManejadorBaseMovilidad;

/**
 * The Class ManejadorAprobacionMovilidadSeguimiento.
 */
public class ManejadorAprobacionMovilidadSeguimientoBase extends ManejadorBaseMovilidad {

    private static final long serialVersionUID = -8543678886902289833L;

    /** The lista movilidades visitante. */
    protected List<MovilidadVisitanteExterior> listaMovilidadesVisitante;
    protected List<MovilidadVisitanteExterior> listaMovilidadesVisitanteFil;

    /** The lista movilidades docentes. */
    protected List<MovilidadDocentesExterior> listaMovilidadesDocentes;
    protected List<MovilidadDocentesExterior> listaMovilidadesDocentesFil;

    /** The lista movilidades estudiante evento. */
    protected List<MovilidadEstudiantesPosgrado> listaMovilidadesEstudianteEvento;
    protected List<MovilidadEstudiantesPosgrado> listaMovilidadesEstudianteEventoFil;

    /** The lista movilidades estudiante pasantia. */
    protected List<MovilidadEstudiantesPosgrado> listaMovilidadesEstudiantePasantia;
    protected List<MovilidadEstudiantesPosgrado> listaMovilidadesEstudiantePasantiaFil;

    /** The movilidad visitante seleccionada. */
    protected MovilidadVisitanteExterior movilidadVisitanteSeleccionada;

    /** The movilidad docentes seleccionada. */
    protected MovilidadDocentesExterior movilidadDocentesSeleccionada;

    /** The movilidad estudiantes seleccionada. */
    protected MovilidadEstudiantesPosgrado movilidadEstudiantesSeleccionada;

    /**
     * Cargar listas movilidades.
     */
    protected void cargarListasSeguimientoMovilidades(boolean sede) {
        Persona persona = (Persona) sesion.getAttribute("persona");
        Dependencia dependencia = null;
        try {
            persona = servicioPersona.obtenerInvestigadorInternoCompleto(persona.getId());
            InvestigadorInterno investigadorInterno = (InvestigadorInterno) persona;
            dependencia = servicioDependencia.obtenerDependencia(investigadorInterno.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        listaMovilidadesVisitante = new ArrayList<MovilidadVisitanteExterior>();
        listaMovilidadesDocentes = new ArrayList<MovilidadDocentesExterior>();
        listaMovilidadesEstudianteEvento = new ArrayList<MovilidadEstudiantesPosgrado>();
        listaMovilidadesEstudiantePasantia = new ArrayList<MovilidadEstudiantesPosgrado>();

        if (dependencia != null) {

        	listaMovilidadesVisitante.addAll(
        			cargarMovilidades(
        					MovilidadVisitanteExterior.class, 
        					dependencia, 
        					true,
        					false, 
        					"and mov.tipoMovilidad.tabla = '" + TipoMovilidad.HER_MOVILIDAD_VISITANTES_EXT + "'", 
        					false,
        					false, 
        					sede)
        			);

			listaMovilidadesVisitante.addAll(
					cargarMovilidades(
							MovilidadVisitanteExterior.class, 
							dependencia, 
							true,
							false, 
							"and mov.tipoMovilidad.tabla = '" + TipoMovilidad.HER_MOVILIDAD_VISITANTES_EXT + "'", 
							false,
							true, 
							sede)
					);

			listaMovilidadesDocentes.addAll(
					cargarMovilidades(
							MovilidadDocentesExterior.class, 
							dependencia, 
							true, 
							false,
							"and mov.tipoMovilidad.tabla = '" + TipoMovilidad.HER_MOVILIDAD_DOCENTES_EVENTOS + "'", 
							false,
							false, 
							sede)
					);

			listaMovilidadesDocentes.addAll(
					cargarMovilidades(
						MovilidadDocentesExterior.class, 
						dependencia, 
						true, 
						false,
						"and mov.tipoMovilidad.tabla = '" + TipoMovilidad.HER_MOVILIDAD_DOCENTES_EVENTOS + "'", 
						false, 
						true,
						sede)
					);

			listaMovilidadesEstudianteEvento.addAll(
					cargarMovilidades(
							MovilidadEstudiantesPosgrado.class, 
							dependencia, 
							true, 
							true,
							"and mov.tipoMovilidad.tabla = '" + TipoMovilidad.HER_MOVILIDAD_ESTUDIANTE_POS + "' and " + "(mov.tipoMovilidad.esPasantia is null or mov.tipoMovilidad.esPasantia = false) ",
							true, 
							false, 
							sede)
					);

			listaMovilidadesEstudianteEvento.addAll(
					cargarMovilidades(
							MovilidadEstudiantesPosgrado.class, 
							dependencia, 
							true, 
							true,
							"and mov.tipoMovilidad.tabla = '" + TipoMovilidad.HER_MOVILIDAD_ESTUDIANTE_POS + "' and "+ "(mov.tipoMovilidad.esPasantia is null or mov.tipoMovilidad.esPasantia = false) ",
							true, 
							true, 
							sede)
					);

			listaMovilidadesEstudiantePasantia.addAll(
					cargarMovilidades(
							MovilidadEstudiantesPosgrado.class, 
							dependencia,
							true, 
							true, 
							"and mov.tipoMovilidad.tabla = '" + TipoMovilidad.HER_MOVILIDAD_ESTUDIANTE_POS + "' and " + " mov.tipoMovilidad.esPasantia = true ",
							true, 
							false, 
							sede)
					);

			listaMovilidadesEstudiantePasantia.addAll(
					cargarMovilidades(
							MovilidadEstudiantesPosgrado.class, 
							dependencia,
							true, 
							true, 
							"and mov.tipoMovilidad.tabla = '" + TipoMovilidad.HER_MOVILIDAD_ESTUDIANTE_POS + "' and " + " mov.tipoMovilidad.esPasantia = true ",
							true, 
							true, 
							sede)
					);
			
        }

    }

    /**
     * Imprimir movilidad visitante.
     */
    public void imprimirMovilidadVisitante() {
        imprimirMovilidadVisitanteGenerico(movilidadVisitanteSeleccionada);
    }

    /**
     * Imprimir movilidad docentes.
     */
    public void imprimirMovilidadDocentes() {
        imprimirMovilidadEventoGenerico(movilidadDocentesSeleccionada);
    }

    /**
     * Imprimir movilidad estudiante.
     */
    public void imprimirMovilidadEstudiante() {
        imprimirMovilidadEstudiantesPosgradoGenerico(movilidadEstudiantesSeleccionada);
    }

    /**
     * Cargar movilidades.
     *
     * @param <T>
     *            the generic type
     * @param clase
     *            the clase
     * @param dependencia
     *            the dependencia
     * @param seg
     *            the seg
     * @param estudiante
     *            the estudiante
     * @param hqlWhereAdicional
     *            the hql where adicional
     * @param tipoM
     *            the tipo m
     * @param convFacultad
     *            the conv facultad
     * @return the list
     */
    private <T> List<T> cargarMovilidades(
    		Class<T> clase, Dependencia dependencia, 
    		boolean seg, 
    		boolean estudiante,
            String hqlWhereAdicional, 
            boolean tipoM, 
            boolean convFacultad, 
            boolean sede) {
        List<T> lista;
        String hql = "select #id mov.id, #fechainicial mov.fechainicial," + "#fechasolicitud mov.fechasolicitud,"
                + "#segMovFecha mov.segMovFecha ";
        if (seg) {
            hql += ",#estadoSeguimiento mov.estadoSeguimiento ";
        }
        if (estudiante) {
            hql += ",#estudianteInv mov.estudianteInv ";
        }
        if (tipoM) {
            hql += ",#tipoMovilidad mov.tipoMovilidad ";
        }
        hql += " from " + clase.getSimpleName() + " mov ";
        if (convFacultad && !sede) {
            if (clase == MovilidadVisitanteExterior.class) {
                hql += " where mov.movilidadConvocatoriaFacultad = 'S' " + " and mov.dependencia.id = '"
                        + dependencia.getFacultad().getId() + "' ";
            } else {
                hql += " where mov.movilidadConvocatoriaFacultad = 'S' "
                        + " and mov.convocatoria.padre.dependencia.id = '" + dependencia.getFacultad().getId() + "' ";
            }
        } else {
            if (estudiante) {
                hql += ", Estudiante e where " + " mov.estudianteInv.id.documento = e.id.documento and "
                        + " mov.estudianteInv.id.tipoDocumento = e.id.tipoDocumento " + " and e.";
            } else {
                if (clase == MovilidadVisitanteExterior.class) {
                    hql += " where mov.";
                } else {
                    hql += ", InvestigadorInterno ii where " + " mov.personaInv.id.documento = ii.id.documento and "
                            + " mov.personaInv.id.tipoDocumento = ii.id.tipoDocumento " + " and ii.";
                }
            }
            if (sede) {
                hql += "dependencia.sede.id ='" + dependencia.getSede().getId() + "' ";
            } else {
                hql += "dependencia.facultad.id ='" + dependencia.getFacultad().getId() + "' "
                        + "and (mov.movilidadConvocatoriaFacultad is null or mov.movilidadConvocatoriaFacultad = 'N')";
            }
        }
        if (!convFacultad && sede) {
            hql += " and mov.estadoSeguimiento = 'F'"
                    + " and ((mov.estadoRevisionSeguimiento = 'L' or mov.convocatoria.padre.informeSedeMovilidad = 'S') ";
            if (dependencia.getSede().isEsSedePresenciaNacional()) {
                hql += " or (mov.estadoRevisionSeguimiento is null or mov.estadoRevisionSeguimiento = 'D')";
            }
            hql += ")";
        } else {
            hql += " and mov.estadoSeguimiento = 'F'" + " and (mov.estadoRevisionSeguimiento is null or "
                    + "	mov.estadoRevisionSeguimiento = 'D') ";
        }
        hql += " and mov.personaInv.id.documento not in (" + DOCUMENTOS_PRUEBAS_DESARROLLO + ") " + hqlWhereAdicional;
        lista = servicioGeneral.obtenerObjetosLimitado(clase, hql);
        return lista;
    }

    /**
     * Gets the lista movilidades visitante.
     *
     * @return the lista movilidades visitante
     */
    public List<MovilidadVisitanteExterior> getListaMovilidadesVisitante() {
        return listaMovilidadesVisitante;
    }

    /**
     * Gets the lista movilidades docentes.
     *
     * @return the lista movilidades docentes
     */
    public List<MovilidadDocentesExterior> getListaMovilidadesDocentes() {
        return listaMovilidadesDocentes;
    }

    /**
     * Gets the lista movilidades estudiante evento.
     *
     * @return the lista movilidades estudiante evento
     */
    public List<MovilidadEstudiantesPosgrado> getListaMovilidadesEstudianteEvento() {
        return listaMovilidadesEstudianteEvento;
    }

    /**
     * Gets the lista movilidades estudiante pasantia.
     *
     * @return the lista movilidades estudiante pasantia
     */
    public List<MovilidadEstudiantesPosgrado> getListaMovilidadesEstudiantePasantia() {
        return listaMovilidadesEstudiantePasantia;
    }

    /**
     * Gets the movilidad docentes seleccionada.
     *
     * @return the movilidad docentes seleccionada
     */
    public MovilidadDocentesExterior getMovilidadDocentesSeleccionada() {
        return movilidadDocentesSeleccionada;
    }

    /**
     * Sets the movilidad docentes seleccionada.
     *
     * @param movilidadDocentesSeleccionada
     *            the new movilidad docentes seleccionada
     */
    public void setMovilidadDocentesSeleccionada(MovilidadDocentesExterior movilidadDocentesSeleccionada) {
        this.movilidadDocentesSeleccionada = movilidadDocentesSeleccionada;
    }

    /**
     * Gets the movilidad visitante seleccionada.
     *
     * @return the movilidad visitante seleccionada
     */
    public MovilidadVisitanteExterior getMovilidadVisitanteSeleccionada() {
        return movilidadVisitanteSeleccionada;
    }

    /**
     * Sets the movilidad visitante seleccionada.
     *
     * @param movilidadVisitanteSeleccionada
     *            the new movilidad visitante seleccionada
     */
    public void setMovilidadVisitanteSeleccionada(MovilidadVisitanteExterior movilidadVisitanteSeleccionada) {
        this.movilidadVisitanteSeleccionada = movilidadVisitanteSeleccionada;
    }

    /**
     * Gets the movilidad estudiantes seleccionada.
     *
     * @return the movilidad estudiantes seleccionada
     */
    public MovilidadEstudiantesPosgrado getMovilidadEstudiantesSeleccionada() {
        return movilidadEstudiantesSeleccionada;
    }

    /**
     * Sets the movilidad estudiantes seleccionada.
     *
     * @param movilidadEstudiantesSeleccionada
     *            the new movilidad estudiantes seleccionada
     */
    public void setMovilidadEstudiantesSeleccionada(MovilidadEstudiantesPosgrado movilidadEstudiantesSeleccionada) {
        this.movilidadEstudiantesSeleccionada = movilidadEstudiantesSeleccionada;
    }

	public List<MovilidadVisitanteExterior> getListaMovilidadesVisitanteFil() {
		return listaMovilidadesVisitanteFil;
	}

	public void setListaMovilidadesVisitanteFil(List<MovilidadVisitanteExterior> listaMovilidadesVisitanteFil) {
		this.listaMovilidadesVisitanteFil = listaMovilidadesVisitanteFil;
	}

	public List<MovilidadDocentesExterior> getListaMovilidadesDocentesFil() {
		return listaMovilidadesDocentesFil;
	}

	public void setListaMovilidadesDocentesFil(List<MovilidadDocentesExterior> listaMovilidadesDocentesFil) {
		this.listaMovilidadesDocentesFil = listaMovilidadesDocentesFil;
	}

	public List<MovilidadEstudiantesPosgrado> getListaMovilidadesEstudianteEventoFil() {
		return listaMovilidadesEstudianteEventoFil;
	}

	public void setListaMovilidadesEstudianteEventoFil(List<MovilidadEstudiantesPosgrado> listaMovilidadesEstudianteEventoFil) {
		this.listaMovilidadesEstudianteEventoFil = listaMovilidadesEstudianteEventoFil;
	}

	public List<MovilidadEstudiantesPosgrado> getListaMovilidadesEstudiantePasantiaFil() {
		return listaMovilidadesEstudiantePasantiaFil;
	}

	public void setListaMovilidadesEstudiantePasantiaFil(List<MovilidadEstudiantesPosgrado> listaMovilidadesEstudiantePasantiaFil) {
		this.listaMovilidadesEstudiantePasantiaFil = listaMovilidadesEstudiantePasantiaFil;
	}

}