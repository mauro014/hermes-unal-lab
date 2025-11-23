package co.edu.unal.hermes.vista.reportes;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import co.edu.unal.hermes.modelo.reporte.ReporteBirt;

/**
 * Servlet implementation class ReporteEngineServlet.
 */
public class ReporteEngineServlet extends HttpServlet {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8548858129312919251L;

    /** The response. */
    private HttpServletResponse response;

    /** The reporte birt. */
    private ReporteBirt reporteBirt = null;

    /**
     * Instantiates a new reporte engine servlet.
     *
     * @see HttpServlet#HttpServlet()
     */
    public ReporteEngineServlet() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.
     * HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        service(req, res);
        super.doGet(req, res);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.
     * HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        response = res;

        HttpSession sesion = req.getSession(false);
        if (sesion != null) {
            reporteBirt = (ReporteBirt) sesion.getAttribute("reporte");
            if (reporteBirt != null) {

                // Se utiliza el viewer de birt
                reporteViewer();

                // Se borra el reporte de la sesion
                sesion.removeAttribute("reporte");
            }
        }
    }

    /**
     * Reporte viewer.
     */
    private void reporteViewer() {
        ServletContext sc = getServletContext();
        String path = ReporteBirt.RUTA_BIRT_VIEWER;
        path += "/run?";
        path += "__report=" + sc.getRealPath(reporteBirt.getNombreReporte());

		if (reporteBirt.getFormato().equals(ReporteBirt.FORMATO_PDF)) {
			path += adicionarOpcion("format", ReporteBirt.FORMATO_PDF);
		} else if (reporteBirt.getFormato().equals(ReporteBirt.FORMATO_XLS)) {
			path += adicionarOpcion("format", ReporteBirt.FORMATO_XLS);
		} else if (reporteBirt.getFormato().equals(ReporteBirt.FORMATO_WORD)) {
			path += adicionarOpcion("format", ReporteBirt.FORMATO_WORD);
		}

        path += adicionaParametros();

        System.out.println(path);

        try {
            response.sendRedirect(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adiciona parametros.
     *
     * @return the string
     */
    private String adicionaParametros() {
        String result = "";
        Iterator<Entry<String, String>> it = reporteBirt.getParametros().entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, String> entry = (Entry<String, String>) it.next();
            String name = (String) entry.getKey();
            String value = (String) entry.getValue();
            result += adicionarParametro(name, value);
        }
        return result;

    }

    /**
     * Adicionar opcion.
     *
     * @param oName
     *            the o name
     * @param oValue
     *            the o value
     * @return the string
     */
    private String adicionarOpcion(String oName, String oValue) {
        return "&__" + oName + "=" + oValue;
    }

    /**
     * Adicionar parametro.
     *
     * @param pName
     *            the name
     * @param pValue
     *            the value
     * @return the string
     */
    private String adicionarParametro(String pName, String pValue) {
        return "&" + pName + "=" + pValue;
    }

}
