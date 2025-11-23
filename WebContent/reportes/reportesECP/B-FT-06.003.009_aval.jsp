<!--/*
*************************************
* Formulario: B-FT-06.003.009_aval.jsp              *
* Autor: ocrodriguezc@unal.edu.co	*
* Fecha de Creacion: 10/07/2013     *
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
    <title>B-FT-06.003.009 Aval</title>
  </head>
  <% String AVI_ID= request.getParameter("AVI_ID" ); %>
  <% System.out.println( AVI_ID ); %>
  <% try{ 
    String driver = "oracle.jdbc.driver.OracleDriver";
    Class.forName(driver); 
    Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@168.176.161.11:1521:produnal","hermes","hermes");
    System.setProperty( "jasper.reports.compile.class.path", application.getRealPath("../../WEB-INF/lib/jasperreports-1.2.7.jar") 
	+ System.getProperty("path.separator") + application.getRealPath("../../WEB-INF/classes/") );
    File reportFile = new File(application.getRealPath("reportes/reportesECP/aval/B-FT-06.003.009_aval.jasper") );
    Map parameters = new HashMap();
    parameters.put("AVI_ID",AVI_ID);
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
