/*
 * Created on 02-may-2006
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package co.edu.unal.hermes.vista.utils;

import java.util.List;

/**
 * @author Jassar David Issa Co
 *
 * 
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class StringUtils {
    public static final String STRING_VACIO = "";

    public static String colocarSeparador(List listaStrings, String separador) {
        String StringConSeparador = new String();
        if (listaStrings != null) {
            if (!listaStrings.isEmpty()) {
                for (int i = 0; i < listaStrings.size(); i++) {
                    StringConSeparador += listaStrings.get(i) + separador;
                }

            }
        }

        return StringConSeparador;
    }

}
