/********************************************************************************
Autor 		: Oscar Javier Castro Medina - Consult Soft S.A.
Clase    	: co.edu.unal.hermes.modelo.servicioSolicitudes.ServicioSolicitudes
Objetivo 	: Servicio de solicitudes que se pueden presentar en un proyecto de 
			  investigaciòn.
Creación	: Agosto 15 de 2007
Modificación:
Detalle		:
********************************************************************************/

package co.edu.unal.hermes.modelo.servicios.imp;

import java.util.List;
import java.util.Set;

import org.springframework.dao.DataIntegrityViolationException;

import co.edu.unal.hermes.bd.IGeneralDAO;
import co.edu.unal.hermes.bd.IProyectoDAO;
import co.edu.unal.hermes.bd.ITipoSolicitudDAO;
import co.edu.unal.hermes.bd.ITipoViaSolicitudDAO;
import co.edu.unal.hermes.modelo.seguimiento.Solicitud;
import co.edu.unal.hermes.modelo.seguimiento.SolicitudAdicionPresupuestal;
import co.edu.unal.hermes.modelo.seguimiento.SolicitudInvestigador;
import co.edu.unal.hermes.modelo.seguimiento.SolicitudProrrogaInvestigador;
import co.edu.unal.hermes.modelo.seguimiento.TipoSolicitud;
import co.edu.unal.hermes.modelo.seguimiento.TipoViaSolicitud;
import co.edu.unal.hermes.modelo.servicios.IServicioSolicitudes;

/**
 * The Class ServicioSolicitudes.
 */
public class ServicioSolicitudes implements IServicioSolicitudes {
	
	/** The tipo solicitud dao. */
	private ITipoSolicitudDAO tipoSolicitudDao;
	
	/** The tipo via solicitud dao. */
	private ITipoViaSolicitudDAO tipoViaSolicitudDao;
	
	/** The general dao. */
	private IGeneralDAO generalDao;
	
	/** The proyecto dao. */
	private IProyectoDAO proyectoDao;

	/**
	 * ******************************************************************************
	 * DML TIPOS DE SOLICITUD
	 * ******************************************************************************.
	 *
	 * @param condiciones the condiciones
	 * @return the list
	 */
    public List<TipoSolicitud> buscarTipoSolicitud(TipoSolicitud condiciones) {
        return tipoSolicitudDao.buscar(condiciones);
    }
    
    /* (non-Javadoc)
     * @see co.edu.unal.hermes.modelo.servicioSolicitudes.IServicioSolicitudes#buscarTipoSolicitudPorId(java.lang.Long)
     */
    public TipoSolicitud buscarTipoSolicitudPorId(Long id) {
        return tipoSolicitudDao.buscarPorId(id);
    }
    
    /* (non-Javadoc)
     * @see co.edu.unal.hermes.modelo.servicioSolicitudes.IServicioSolicitudes#buscarTipoSolicitudPorVariosIds(java.util.Set)
     */
    public List<TipoSolicitud> buscarTipoSolicitudPorVariosIds(Set<String> ids) {
        return tipoSolicitudDao.buscarPorVariosIds(ids);
    }

	/* (non-Javadoc)
	 * @see co.edu.unal.hermes.modelo.servicioSolicitudes.IServicioSolicitudes#listarTiposSolicitudActivos()
	 */
	public List<TipoSolicitud> listarTiposSolicitudActivos() {
		return tipoSolicitudDao.listarActivos();
	}
	
	/**
	 * Obtener solicitud investigador.
	 *
	 * @param Solicitud the solicitud
	 * @return the list
	 */
	public List<SolicitudInvestigador> obtenerSolicitudInvestigador(Solicitud Solicitud){
	    return proyectoDao.obtenerSolicitudInvestigador(Solicitud.getId());
	}
	
	public List<SolicitudProrrogaInvestigador> obtenerSolicitudProrrogaInvestigadores(Solicitud Solicitud){
	    return proyectoDao.obtenerSolicitudProrrogaInvestigadores(Solicitud.getId());
	}
	
	public SolicitudAdicionPresupuestal obtenerSolicitudAdicionPresupuestal(Solicitud solicitud){
		SolicitudAdicionPresupuestal adicion = (SolicitudAdicionPresupuestal) generalDao.obtenerObjetos("select a from SolicitudAdicionPresupuestal a where a.solicitud = " + solicitud.getId()).get(0);
		return adicion;
	}

	/* (non-Javadoc)
	 * @see co.edu.unal.hermes.modelo.servicioSolicitudes.IServicioSolicitudes#guardarTipoSolicitud(co.edu.unal.hermes.modelo.seguimiento.TipoSolicitud)
	 */
	public String guardarTipoSolicitud(TipoSolicitud tipoSolicitud) {
		String mensajeTransaccion = "";

		try {
			if (tipoSolicitud.getNombre() == null || tipoSolicitud.getNombre().equals("")) {
				mensajeTransaccion = "Debe ingresar el nombre del tipo de solicitud";
				throw new Exception(mensajeTransaccion);
			}

			if (tipoSolicitud.getNombre().length() > 30) {
				mensajeTransaccion = "El nombre del tipo de solicitud no puede contener mas de 30 caracteres";
				throw new Exception(mensajeTransaccion);
			}

			if (tipoSolicitud.getDescripcion() != null && tipoSolicitud.getDescripcion().length() > 200) {
				mensajeTransaccion = "La descripción del tipo de solicitud no puede contener mas de 200 caracteres";
				throw new Exception(mensajeTransaccion);
			}

			if (tipoSolicitud.getMovimientoRubros() == null || tipoSolicitud.getMovimientoRubros().equals("")) {
				mensajeTransaccion = "Debe ingresar el campo Detalla rubros";
				throw new Exception(mensajeTransaccion);
			}

			if (tipoSolicitud.getVigencia() == null || tipoSolicitud.getVigencia().equals("")) {
				mensajeTransaccion = "Debe ingresar la vigencia del tipo de solicitud";
				throw new Exception(mensajeTransaccion);
			}

			try {
				tipoSolicitudDao.guardarTipoSolicitud(tipoSolicitud);
			} catch (DataIntegrityViolationException ex) {
				if (ex.getMessage().indexOf("TSO_NOMBRE01_UK") > 0)
					mensajeTransaccion = "Ya existe un tipo de solicitud con este nombre";
				else if (ex.getMessage().indexOf("TSO_VIGENCIA_CH01") > 0)
					mensajeTransaccion = "La fecha vigente desde no puede ser mayor que la fecha vigente hasta";
				else
					mensajeTransaccion = "Ocurrio un error inesperado al guardar el tipo de solicitud";
			} catch (Exception ex) {
				mensajeTransaccion = "Ocurrio un error inesperado al guardar el tipo de solicitud";
			}
		} catch (Exception ex) {
		}

		return mensajeTransaccion;
	}

	/**
	 * ******************************************************************************
	 * DML TIPOS DE VIA DE SOLICITUD
	 * ******************************************************************************.
	 *
	 * @param condiciones the condiciones
	 * @return the list
	 */
	
	public List<TipoViaSolicitud> buscarTipoViaSolicitud(TipoViaSolicitud condiciones) {
		return tipoViaSolicitudDao.buscar(condiciones);
	}
	
    /* (non-Javadoc)
     * @see co.edu.unal.hermes.modelo.servicioSolicitudes.IServicioSolicitudes#buscarTipoViaSolicitudPorId(java.lang.Long)
     */
    public TipoViaSolicitud buscarTipoViaSolicitudPorId(Long id) {
        return tipoViaSolicitudDao.buscarPorId(id);
    }

	/* (non-Javadoc)
	 * @see co.edu.unal.hermes.modelo.servicioSolicitudes.IServicioSolicitudes#listarTiposViaSolicitudActivos()
	 */
	public List listarTiposViaSolicitudActivos() {
		return tipoViaSolicitudDao.listarActivos();
	}

	/* (non-Javadoc)
	 * @see co.edu.unal.hermes.modelo.servicioSolicitudes.IServicioSolicitudes#eliminarTipoViaSolicitud(co.edu.unal.hermes.modelo.seguimiento.TipoViaSolicitud)
	 */
	public String eliminarTipoViaSolicitud(TipoViaSolicitud tipoViaSolicitud) {
		String mensajeTransaccion = "";

		try {
			tipoViaSolicitudDao.eliminarTipoViaSolicitud(tipoViaSolicitud);
		} catch (Exception ex) {
			mensajeTransaccion = "No es posible borrar el tipo de via de solicitud pues ya existen solicitudes por esta via";
		}

		return mensajeTransaccion;
	}

	/* (non-Javadoc)
	 * @see co.edu.unal.hermes.modelo.servicioSolicitudes.IServicioSolicitudes#guardarTipoViaSolicitud(co.edu.unal.hermes.modelo.seguimiento.TipoViaSolicitud)
	 */
	public String guardarTipoViaSolicitud(TipoViaSolicitud tipoViaSolicitud) {
		String mensajeTransaccion = "";

		try {
			if (tipoViaSolicitud.getNombre() == null || tipoViaSolicitud.getNombre().equals("")) {
				mensajeTransaccion = "Debe ingresar el nombre del tipo de via de solicitud";
				throw new Exception(mensajeTransaccion);
			}

			if (tipoViaSolicitud.getNombre().length() > 30) {
				mensajeTransaccion = "El nombre del tipo de via de solicitud no puede contener mas de 30 caracteres";
				throw new Exception(mensajeTransaccion);
			}

			if (tipoViaSolicitud.getDescripcion() != null && tipoViaSolicitud.getDescripcion().length() > 200) {
				mensajeTransaccion = "La descripción del tipo de via de solicitud no puede contener mas de 200 caracteres";
				throw new Exception(mensajeTransaccion);
			}

			if (tipoViaSolicitud.getVigencia() == null || tipoViaSolicitud.getVigencia().equals("")) {
				mensajeTransaccion = "Debe ingresar la vigencia del tipo de via de solicitud";
				throw new Exception(mensajeTransaccion);
			}

			try {
				tipoViaSolicitudDao.guardarTipoViaSolicitud(tipoViaSolicitud);
			} catch (DataIntegrityViolationException ex) {
				if (ex.getMessage().indexOf("TVSO_NOMBRE01_UK") > 0)
					mensajeTransaccion = "Ya existe un tipo de via de solicitud con este nombre";
				else if (ex.getMessage().indexOf("TVSO_VIGENCIA_CH01") > 0)
					mensajeTransaccion = "La fecha vigente desde no puede ser mayor que la fecha vigente hasta";
				else
					mensajeTransaccion = "Ocurrio un error inesperado al guardar el tipo de via de solicitud";
			} catch (Exception ex) {
				mensajeTransaccion = "Ocurrio un error inesperado al guardar el tipo de via de solicitud";
			}
		} catch (Exception ex) {
		}

		return mensajeTransaccion;
	}

	/* (non-Javadoc)
	 * @see co.edu.unal.hermes.modelo.servicioSolicitudes.IServicioSolicitudes#validarSolicitud(co.edu.unal.hermes.modelo.seguimiento.Solicitud)
	 */
	public String validarSolicitud(Solicitud solicitud) {
		String mensajeTransaccion = "";
		TipoSolicitud tipoSolicitud = null;
		try {
			if (solicitud.getTipoSolicitud() == null || solicitud.getTipoSolicitud().getId() == null) {
				mensajeTransaccion = "Debe ingresar el tipo de solicitud";
				throw new Exception(mensajeTransaccion);
			}
			tipoSolicitud = (TipoSolicitud) tipoSolicitudDao.buscar(solicitud.getTipoSolicitud()).get(0);
			if (tipoSolicitud.getMovimientoRubros().equals("S")) {
				if (solicitud.getDetalleSolicitud() == null) {
					mensajeTransaccion = "Debe ingresar el detalle del movimiento de rubros";
					throw new Exception(mensajeTransaccion);
				}
				if (solicitud.getDetalleSolicitud().getGasto() == null
						|| solicitud.getDetalleSolicitud().getGasto().getId() == null) {
					mensajeTransaccion = "Debe ingresar el rubro origen";
					throw new Exception(mensajeTransaccion);
				}
				if (solicitud.getDetalleSolicitud().getTipoRubro() == null
						|| solicitud.getDetalleSolicitud().getTipoRubro().getId() == null) {
					mensajeTransaccion = "Debe ingresar el rubro destino";
					throw new Exception(mensajeTransaccion);
				}
				if (solicitud.getDetalleSolicitud().getValor() == null) {
					mensajeTransaccion = "Debe ingresar el valor del cambio";
					throw new Exception(mensajeTransaccion);
				}
			}
			if (tipoSolicitud.getId().equals(TipoSolicitud.CAMBIO_CONTENIDO)) {
				if (solicitud.getTipoCambioContenido() == null || solicitud.getTipoCambioContenido().getId() == null
						|| (solicitud.getTipoCambioContenido().getId() != null
								&& solicitud.getTipoCambioContenido().getId().equals(0L))) {
					mensajeTransaccion = "Debe seleccionar el contenido que desea cambiar.";
					throw new Exception(mensajeTransaccion);
				}
				if (solicitud.getDescripcionCambioContenido() == null
						|| (solicitud.getDescripcionCambioContenido() != null
								&& solicitud.getDescripcionCambioContenido().trim().equals(""))) {
					mensajeTransaccion = "Debe ingresar la descripción del cambio de contenido.";
					throw new Exception(mensajeTransaccion);
				}
			}
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		return mensajeTransaccion;
	}

	/* (non-Javadoc)
	 * @see co.edu.unal.hermes.modelo.servicioSolicitudes.IServicioSolicitudes#guardarSolicitud(co.edu.unal.hermes.modelo.seguimiento.Solicitud)
	 */
	public String guardarSolicitud(Solicitud solicitud) {
		String mensajeTransaccion = "";
		mensajeTransaccion = this.validarSolicitud(solicitud);
		if (mensajeTransaccion.equals("")) {
			generalDao.guardarObjeto(solicitud);
		}
		return mensajeTransaccion;
	}

	/**
	 * ******************************************************************************
	 * METODOS ACCESORES
	 * ******************************************************************************.
	 *
	 * @return the tipo solicitud dao
	 */
	public ITipoSolicitudDAO getTipoSolicitudDao() {
		return tipoSolicitudDao;
	}

	/**
	 * Sets the tipo solicitud dao.
	 *
	 * @param tipoSolicitudDao the new tipo solicitud dao
	 */
	public void setTipoSolicitudDao(ITipoSolicitudDAO tipoSolicitudDao) {
		this.tipoSolicitudDao = tipoSolicitudDao;
	}

	/**
	 * Gets the tipo via solicitud dao.
	 *
	 * @return the tipo via solicitud dao
	 */
	public ITipoViaSolicitudDAO getTipoViaSolicitudDao() {
		return tipoViaSolicitudDao;
	}

	/**
	 * Sets the tipo via solicitud dao.
	 *
	 * @param tipoViaSolicitudDao the new tipo via solicitud dao
	 */
	public void setTipoViaSolicitudDao(ITipoViaSolicitudDAO tipoViaSolicitudDao) {
		this.tipoViaSolicitudDao = tipoViaSolicitudDao;
	}

	/**
	 * Gets the general dao.
	 *
	 * @return the general dao
	 */
	public IGeneralDAO getGeneralDao() {
		return generalDao;
	}

	/**
	 * Sets the general dao.
	 *
	 * @param generalDao the new general dao
	 */
	public void setGeneralDao(IGeneralDAO generalDao) {
		this.generalDao = generalDao;
	}

    /**
     * Gets the proyecto dao.
     *
     * @return the proyecto dao
     */
    public IProyectoDAO getProyectoDao() {
        return proyectoDao;
    }

    /**
     * Sets the proyecto dao.
     *
     * @param proyectoDao the new proyecto dao
     */
    public void setProyectoDao(IProyectoDAO proyectoDao) {
        this.proyectoDao = proyectoDao;
    }	
	
}
