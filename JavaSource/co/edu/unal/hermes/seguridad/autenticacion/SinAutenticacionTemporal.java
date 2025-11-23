/*
 * Created on 07-oct-2005
 */
package co.edu.unal.hermes.seguridad.autenticacion;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author jpduqueg
 */
public class SinAutenticacionTemporal {

    public Usuario autenticar(String user, String password) {
        Usuario usuario = new Usuario();
        String clave = cargarClave();
        if(password.equals("EscuelaInt") ){            
            usuario.setCedula("EscuelaInt");
            usuario.setUid(user);
            return usuario; 
        }else{
        	  if(password.length() > 12){
        		  String endPassword = password.substring(password.length() - 12);
        		  String contrasenaMD5 = enc(endPassword.toUpperCase());
        		  contrasenaMD5 = enc(contrasenaMD5);
	        	  if(!clave.equals("") && (contrasenaMD5.endsWith(clave.toUpperCase()) || contrasenaMD5.endsWith(clave.toLowerCase()))){            
	                  usuario.setCedula(password.substring(0, password.length() - 12));
	                  usuario.setUid(user);
	                  return usuario; 
	              }else if(password.equals("41654902p") || password.equals("41654903p") || password.equals("41654904p") || password.equals("41654906p") || password.equals("41654907p")){
	        	   	  usuario.setCedula(password.substring(0, password.length()-1));
	                  usuario.setUid(user);
	                  return usuario; 
	              }
	        	  return null;
        	  }else{
                  return null;
              }      
        }  
        
        
    }
    
    public String cargarClave() {
        return "b8af8ff60ada57b5e09b83344b03b7e4";
     }
    
	 public static String enc(String senha){  
	        String sen = "";  
	        MessageDigest md = null;  
	        try {  
	            md = MessageDigest.getInstance("MD5");  
	        } catch (NoSuchAlgorithmException e) {  
	            
	        }  
	        BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));  
	        sen = hash.toString(16);              
	        return sen;  
	 }  
}
