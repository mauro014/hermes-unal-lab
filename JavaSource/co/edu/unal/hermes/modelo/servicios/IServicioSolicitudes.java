/********************************************************************************
Autor 		: Oscar Javier Castro Medina - Consult Soft S.A.
Interface   : co.edu.unal.hermes.modelo.servicioSolicitudes.IServicioSolicitudes
Objetivo 	: Servicio de solicitudes  que  se pueden presentar en un proyecto de 
			  investigaciòn.
Creación	: Agosto 15 de 2007
Modificación:
Detalle		:
********************************************************************************/

package co.edu.unal.hermes.modelo.servicios;

import java.util.List;
import java.util.Set;

import co.edu.unal.hermes.modelo.seguimiento.Solicitud;
import co.edu.unal.hermes.modelo.seguimiento.SolicitudAdicionPresupuestal;
import co.edu.unal.hermes.modelo.seguimiento.SolicitudInvestigador;
import co.edu.unal.hermes.modelo.seguimiento.SolicitudProrrogaInvestigador;
import co.edu.unal.hermes.modelo.seguimiento.TipoSolicitud;
import co.edu.unal.hermes.modelo.seguimiento.TipoViaSolicitud;

/**
 * The Interface IServicioSolicitudes.
 */
public interface IServicioSolicitudes 
{
	
	/**
	 * ******************************************************************************
	 * 	DML TIPOS DE SOLICITUD 
	 * ******************************************************************************.
	 *
	 * @param condiciones the condiciones
	 * @return the list
	 */
    public List<TipoSolicitud> buscarTipoSolicitud(TipoSolicitud condiciones);    
    
    /**
     * Buscar tipo solicitud por id.
     *
     * @param id the id
     * @return the tipo solicitud
     */
    public TipoSolicitud buscarTipoSolicitudPorId(Long id);
	
	/**
	 * Listar tipos solicitud activos.
	 *
	 * @return the list
	 */
	public List<TipoSolicitud> listarTiposSolicitudActivos();
	
	/**
	 * Guardar tipo solicitud.
	 *
	 * @param tipoSolicitud the tipo solicitud
	 * @return the string
	 */
	public String guardarTipoSolicitud(TipoSolicitud tipoSolicitud);    
	
	/**
	 * Buscar tipo solicitud por varios ids.
	 *
	 * @param ids the ids
	 * @return the list
	 */
	public List<TipoSolicitud> buscarTipoSolicitudPorVariosIds(Set<String> ids);
	
	/**
	 * ******************************************************************************
	 * 	DML TIPOS DE VIA DE SOLICITUD 
	 * ******************************************************************************.
	 *
	 * @param condiciones the condiciones
	 * @return the list
	 */
	public List<TipoViaSolicitud> buscarTipoViaSolicitud(TipoViaSolicitud condiciones);
	
	/**
	 * Buscar tipo via solicitud por id.
	 *
	 * @param id the id
	 * @return the tipo via solicitud
	 */
	public TipoViaSolicitud buscarTipoViaSolicitudPorId(Long id);
	
	/**
	 * Listar tipos via solicitud activos.
	 *
	 * @return the list
	 */
	public List<TipoViaSolicitud> listarTiposViaSolicitudActivos();
	
	/**
	 * Guardar tipo via solicitud.
	 *
	 * @param tipoViaSolicitud the tipo via solicitud
	 * @return the string
	 */
	public String guardarTipoViaSolicitud(TipoViaSolicitud tipoViaSolicitud);
	
	/**
	 * Eliminar tipo via solicitud.
	 *
	 * @param tipoViaSolicitud the tipo via solicitud
	 * @return the string
	 */
	public String eliminarTipoViaSolicitud(TipoViaSolicitud tipoViaSolicitud);
	
	/**
	 * ******************************************************************************
	 * 	DML SOLICITUDES 
	 * ******************************************************************************.
	 *
	 * @param solicitud the solicitud
	 * @return the string
	 */
	public String validarSolicitud(Solicitud solicitud);
	
	/**
	 * Guardar solicitud.
	 *
	 * @param solicitud the solicitud
	 * @return the string
	 */
	public String guardarSolicitud(Solicitud solicitud);
	
	/**
	 * Obtener solicitud investigador.
	 *
	 * @param idSolicitud the id solicitud
	 * @return the list
	 */
	public List<SolicitudInvestigador> obtenerSolicitudInvestigador(Solicitud solicitud);
	public List<SolicitudProrrogaInvestigador> obtenerSolicitudProrrogaInvestigadores(Solicitud solicitud);
	
	public SolicitudAdicionPresupuestal obtenerSolicitudAdicionPresupuestal(Solicitud solicitud);
}
