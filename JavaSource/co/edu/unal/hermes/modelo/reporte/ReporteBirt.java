/*
 * Created on 19-abr-2006
 */
package co.edu.unal.hermes.modelo.reporte;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * The Class ReporteBirt.
 *
 * @author jpduqueg
 */
public class ReporteBirt extends ManejadorBase {

	private static final long serialVersionUID = 1L;

	/** The Constant logger. */
    private static final Logger LOGGER = Logger.getLogger(ReporteBirt.class);

    /** The Constant RUTA_REPORTES. */
    private static final String RUTA_REPORTES = "/reportes/";

    /** The Constant RUTA_BIRT_VIEWER. */
    public static final String RUTA_BIRT_VIEWER = "/birt-viewer";

    /** The Constant EXTENSION. */
    private static final String EXTENSION = "rptdesign";

    /** The Constant FORMATO_PDF. */
    public static final String FORMATO_PDF = "pdf";

    /** The Constant FORMATO_HTML. */
    public static final String FORMATO_HTML = "html";

    /** The Constant FORMATO_XLS. */
    public static final String FORMATO_XLS = "xls";
    
    public static final String FORMATO_WORD = "doc";

    /** The nombre reporte. */
    private String nombreReporte;

    /** The formato. */
    private String formato;

    /** The parametros. */
    private final HashMap<String, String> parametros = new HashMap<String, String>();

    /**
     * Adicionar parametro.
     *
     * @param name
     *            the name
     * @param value
     *            the value
     */
    public void adicionarParametro(String name, String value) {
        parametros.put(name, value);
    }

    /**
     * Crear ruta viewer.
     *
     * @return the string
     */
    public String crearRutaViewer() {
        return null;
    }

    /**
     * Gets the formato.
     *
     * @return the formato
     */
    public String getFormato() {
        return formato;
    }

    /**
     * Sets the formato.
     *
     * @param formato
     *            the new formato
     */
    public void setFormato(String formato) {
        this.formato = formato;
    }

    /**
     * Gets the nombre reporte.
     *
     * @return the nombre reporte
     */
    public String getNombreReporte() {
        return RUTA_REPORTES + nombreReporte + "." + EXTENSION;
    }

    /**
     * Sets the nombre reporte.
     *
     * @param nombreReporte
     *            the new nombre reporte
     */
    public void setNombreReporte(String nombreReporte) {
        this.nombreReporte = nombreReporte;
    }

    /**
     * Gets the parametros.
     *
     * @return the parametros
     */
    public Map<String, String> getParametros() {
        return parametros;
    }

    /**
     * Corre el reporte creado previamente.
     *
     * @param context
     *            the context
     */
    public void run(FacesContext context) {
    	try {
        	Boolean esBDPruebas = !servicioGeneral.esAmbienteProduccion();
        	adicionarParametro("BDPruebas",esBDPruebas.toString());
        	//Boolean esBDPruebas = !servicioGeneral.esAmbienteProduccion();
        	//adicionarParametro("BDPruebas",esBDPruebas.toString());
            context.getExternalContext().dispatch("/ReporteEngineServlet");
        } catch (IOException e) {
            LOGGER.error("Error report: " + nombreReporte, e);
        } catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            context.responseComplete();
        }
    }
}