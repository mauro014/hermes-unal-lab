/*
 * Created on 15-sep-2005
 */
package co.edu.unal.hermes.utils;

import co.edu.unal.hermes.modelo.PalabraClave;

/**
 * @author jpduqueg
 */
public class UtilPalabraClave {
    
    public static void limpiaPalabraClave(PalabraClave palabraClave){
        String palabra = palabraClave.getPalabra();
        String palabraLimpia = palabra.toUpperCase();
        palabraClave.setPalabra(palabraLimpia);
    }
    
    public static String quitarTildes(String name) {
        char[] cadena = name.toCharArray();
        for (int i = 0; i < cadena.length; i++) {
            if (cadena[i] == 'á') {
                cadena[i] = 'a';
            } else if (cadena[i] == 'é') {
                cadena[i] = 'e';
            } else if (cadena[i] == 'í') {
                cadena[i] = 'i';
            } else if (cadena[i] == 'ó') {
                cadena[i] = 'o';
            } else if (cadena[i] == 'ú') {
                cadena[i] = 'u';
            } else if (cadena[i] == 'Á') {
                cadena[i] = 'A';
            } else if (cadena[i] == 'É') {
                cadena[i] = 'E';
            } else if (cadena[i] == 'Í') {
                cadena[i] = 'I';
            } else if (cadena[i] == 'Ó') {
                cadena[i] = 'O';
            } else if (cadena[i] == 'Ú') {
                cadena[i] = 'U';
            }
        }
        String name_sinTildes = new String(cadena);
        return name_sinTildes;
    }
    
    public static String convertirPrimeraLetraMayuscula(String str) {
        if (str.equals("")) {
            return "";
        }
        String str2 = str.toLowerCase();
        char[] c = new char[str2.length()];
        str2.getChars(0, str2.length(), c, 0);
        String s2 = new String(c, 0, 1);
        String s3 = new String(c, 1, str2.length() - 1);
        return s2.toUpperCase() + s3;
    }
     

}
