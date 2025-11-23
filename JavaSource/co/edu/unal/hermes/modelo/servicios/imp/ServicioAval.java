/*
 * Created on 28-marzo-2017
 */
package co.edu.unal.hermes.modelo.servicios.imp;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import co.edu.unal.hermes.bd.IAvalDAO;
import co.edu.unal.hermes.bd.IPersonaDAO;
import co.edu.unal.hermes.modelo.AvalReporte;
import co.edu.unal.hermes.modelo.Reporte;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.modelo.servicios.IServicioAval;

/**
 * The Class ServicioAval.
 */
public class ServicioAval implements IServicioAval {

    /** The Aval DAO. */
    private IAvalDAO avalDAO;
    private IPersonaDAO personaDAO;

    /**
     * Sets the biodiversidad DAO.
     *
     * @param biodiversidadDAO
     *            the new biodiversidad DAO
     */
    public void setAvalDAO(IAvalDAO avalDAO) {
        this.avalDAO = avalDAO;
    }

    public void setPersonaDAO(IPersonaDAO personaDAO) {
        this.personaDAO = personaDAO;
    }
    
    public void imprimirReporteAval(Long id, HttpSession sesion) {

        ReporteBirt r = new ReporteBirt();
        r.adicionarParametro("id", id.toString());
        
        if (id <= 4855L) {
            r.setNombreReporte("/aval/reporteAval_FichaMin");
        } else {
            r.setNombreReporte("/aval/formato-aval");
        }

        r.setFormato(ReporteBirt.FORMATO_PDF);
        sesion.setAttribute("reporte", r);

        FacesContext context = FacesContext.getCurrentInstance();
        r.run(context);

    }
    
    public List<Reporte> obtenerListaCartas(String tipo){
        return avalDAO.obtenerListaCartas(tipo);        
    }

}
