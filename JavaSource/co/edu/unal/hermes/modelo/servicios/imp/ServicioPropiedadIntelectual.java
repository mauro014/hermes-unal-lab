/*
 * Created on 25-julio-2016
 */
package co.edu.unal.hermes.modelo.servicios.imp;

import java.util.List;

import org.springframework.dao.DataAccessException;

import co.edu.unal.hermes.bd.IPropiedadIntelectualDAO;
import co.edu.unal.hermes.modelo.CaracterPropiedadIntelectual;
import co.edu.unal.hermes.modelo.ClasificacionEstadoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.EstadoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.HistoricoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.ObraFonogramaPropiedadIntelectual;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PropiedadIntelectual;
import co.edu.unal.hermes.modelo.SubEstadoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.SubTipoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.TipoArchivo;
import co.edu.unal.hermes.modelo.TipoPersonaPropiedadIntelectual;
import co.edu.unal.hermes.modelo.TipoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.servicios.IServicioPropiedadIntelectual;

public class ServicioPropiedadIntelectual implements IServicioPropiedadIntelectual {	
    
    private IPropiedadIntelectualDAO propiedadIntelectualDAO;
    
    public void setPropiedadIntelectualDAO(IPropiedadIntelectualDAO propiedadIntelectualDAO) {
        this.propiedadIntelectualDAO = propiedadIntelectualDAO;
    }
	
	public List<TipoPropiedadIntelectual> obtenerTiposPropiedadIntelectual(String estado) throws DataAccessException{
		return propiedadIntelectualDAO.obtenerTiposPropiedadIntelectual(estado);		
	}
	
	public List<SubTipoPropiedadIntelectual> obtenerSubTiposPropiedadIntelectual(Long tipo, String estado, Boolean esGestor) throws DataAccessException{
        return propiedadIntelectualDAO.obtenerSubTiposPropiedadIntelectual(tipo, estado, esGestor);       
    }
	
	public PropiedadIntelectual obtenerRegistroPropiedadIntelectual(Long id) throws DataAccessException{
        return propiedadIntelectualDAO.obtenerRegistroPropiedadIntelectual(id);       
    }
	
	public List<PropiedadIntelectual> obtenerPropiedadesRegistradasPersona(Persona persona) throws DataAccessException{
        return propiedadIntelectualDAO.obtenerPropiedadesRegistradasPersona(persona);       
    }
	
	public CaracterPropiedadIntelectual obtenerCaracterPropiedadIntelectual(Long id) throws DataAccessException{
        return propiedadIntelectualDAO.obtenerCaracterPropiedadIntelectual(id);       
    }
	
	public List<TipoPersonaPropiedadIntelectual> obtenerTiposPersonaPropiedadIntelectual(String subTipoPropiedad) throws DataAccessException{
        return propiedadIntelectualDAO.obtenerTiposPersonaPropiedadIntelectual(subTipoPropiedad);       
	}
	
	public List<TipoPersonaPropiedadIntelectual> obtenerTiposPersonaPropiedadIntelectual(TipoPropiedadIntelectual tipo) throws DataAccessException{
        return propiedadIntelectualDAO.obtenerTiposPersonaPropiedadIntelectual(tipo);       
    }
	
	public TipoPersonaPropiedadIntelectual obtenerTipoPersonaPropiedadIntelectual(String id) throws DataAccessException{
        return propiedadIntelectualDAO.obtenerTipoPersonaPropiedadIntelectual(id);       
    }
	
	public List<PropiedadIntelectual> obtenerPropiedadesPendientesNacional() throws DataAccessException{
        return propiedadIntelectualDAO.obtenerPropiedadesPendientesNacional();       
    }
	
	public List<PropiedadIntelectual> obtenerPropiedadesPendientesSede(Long id) throws DataAccessException{
        return propiedadIntelectualDAO.obtenerPropiedadesPendientesSede(id);       
    }
	
	public List<ClasificacionEstadoPropiedadIntelectual> obtenerClasificacionEstadosPropiedadIntelectual(Long numeroOrden) throws DataAccessException{
        return propiedadIntelectualDAO.obtenerClasificacionEstadosPropiedadIntelectual(numeroOrden);       
    }
	
	public List<EstadoPropiedadIntelectual> obtenerEstadosPropiedadIntelectual(String clasificacion, Long tipoPropiedad) throws DataAccessException{
        return propiedadIntelectualDAO.obtenerEstadosPropiedadIntelectual(clasificacion, tipoPropiedad);       
    }
	
	public List<SubEstadoPropiedadIntelectual> obtenerSubEstadosPropiedadIntelectual(String estado) throws DataAccessException{
        return propiedadIntelectualDAO.obtenerSubEstadosPropiedadIntelectual(estado);       
    }
	
	public List<HistoricoPropiedadIntelectual> obtenerHistoricoPropiedadIntelectual(Long id) throws DataAccessException{
        return propiedadIntelectualDAO.obtenerHistoricoPropiedadIntelectual(id);       
    }
	
	public ObraFonogramaPropiedadIntelectual obtenerObraFijadaFonograma(Long id) throws DataAccessException{
        return propiedadIntelectualDAO.obtenerObraFijadaFonograma(id);       
    }
	
	public List<SubTipoPropiedadIntelectual> obtenerSubTiposPropiedadIntelectual() throws DataAccessException{
        return propiedadIntelectualDAO.obtenerSubTiposPropiedadIntelectual();       
    }
	
	public SubEstadoPropiedadIntelectual obtenerSubEstadoPropiedadIntelectual(Long id) throws DataAccessException{
        return propiedadIntelectualDAO.obtenerSubEstadoPropiedadIntelectual(id);       
    }

    public List<Object[]> obtenerIndicadoresRapidos() {
        return propiedadIntelectualDAO.obtenerIndicadoresRapidos();
    }
    
    public List<TipoArchivo> obtenerTiposArchivosPropiedadIntelectual() throws DataAccessException{
        return propiedadIntelectualDAO.obtenerTiposArchivosPropiedadIntelectual();       
    }
    
    public EstadoPropiedadIntelectual obtenerEstadoPropiedadIntelectual(Long id) throws DataAccessException{
        return propiedadIntelectualDAO.obtenerEstadoPropiedadIntelectual(id);       
    }
	
}
