<!--/*
*************************************
* Formulario: B-FT-06.003.008_docentes.jsp              *
* Autor: ocrodriguezc@unal.edu.co	*
* Fecha de Creacion: 22/07/2013     *
*************************************
* Funcion: Web server JasperReports *
*************************************
*/-->

<%@ page import="net.sf.jasperreports.engine.*"%>
<%@ page import="net.sf.jasperreports.engine.design.*"%>
<%@ page import="net.sf.jasperreports.engine.data.*"%>
<%@ page import="net.sf.jasperreports.engine.export.*"%>
<%@ page import="net.sf.jasperreports.engine.util.*"%>
<%@ page import="net.sf.jasperreports.view.*"%>
<%@ page import="net.sf.jasperreports.view.save.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<html>
  <head>
    <title>B-FT-06.003.008 Ficha Curso</title>
  </head>
  <% String ID_CURSO= request.getParameter("ID_CURSO" ); %>
  <% try{ 
    String driver = "oracle.jdbc.driver.OracleDriver";
    Class.forName(driver); 
    Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@168.176.161.11:1521:produnal","hermes","hermes");
    System.setProperty( "jasper.reports.compile.class.path", application.getRealPath("../../WEB-INF/lib/jasperreports-1.2.7.jar") 
	+ System.getProperty("path.separator") + application.getRealPath("../../WEB-INF/classes/") );
    File reportFile = new File(application.getRealPath("reportes/reportesECP/ficha_curso/B-FT-06.003.008_ficha_curso.jasper") );
    Map parameters = new HashMap();
    parameters.put("ID_CURSO",ID_CURSO);
    parameters.put("SUBREPORT_DIR",application.getRealPath("reportes/reportesECP/ficha_curso/")+"/");	
    byte[] bytes = JasperRunManager.runReportToPdf( reportFile.getPath(), parameters, conn );
    response.setContentType("application/pdf" );
    response.setContentLength(bytes.length);
    ServletOutputStream ouputStream = response.getOutputStream();
    ouputStream.write( bytes, 0, bytes.length );
    ouputStream.flush();
    ouputStream.close(); 
    System.out.println( "*" );
    System.out.println( " ...........Fin del reporte............. "+parameters );
    System.out.println( "*" );
    }catch (JRException e) {
        System.out.println( "Error:" +e.getMessage() );
        } 
        catch (Exception e) {
        e.printStackTrace();
        System.out.println( "Error2:" +e.getMessage() );
    }
 %>
  <body>ERROR EN EL PARAMETRO</body>
</html>
