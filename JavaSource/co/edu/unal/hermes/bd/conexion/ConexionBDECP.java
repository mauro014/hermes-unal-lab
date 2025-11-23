package co.edu.unal.hermes.bd.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConexionBDECP {
	
	public static final String APEX_PROD = "http://www.extension.hermes.unal.edu.co/apex/";
	private static String USER = "ECP_SCHEMA";
    private static String PASSWORD = "ecp";
    
    public static void StoreProcedure(String sql) {
        System.out.println("Ejecutar SQL: " + sql);
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            String url = "jdbc:oracle:thin:@168.176.6.85:1521/siei";
            Connection con = DriverManager.getConnection(url, USER, PASSWORD);
            Statement s = con.createStatement();

            s.execute(sql);

            s.close();
            con.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static String execQueryLink(String sql) {
    	String link = "";
    	try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            String url = "jdbc:oracle:thin:@168.176.6.85:1521/siei";
            Connection con = DriverManager.getConnection(url, USER, PASSWORD);
            Statement s = con.createStatement();

            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
            	link = rs.getString("ENLACE");
            	
            }
            
            s.close();
            con.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
		return link;
    }


}
