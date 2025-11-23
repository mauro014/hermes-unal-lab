/*
 * Created on 28-abr-2006
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package co.edu.unal.hermes.vista.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jassar David Issa Co
 * 
 *         
 *         Window - Preferences - Java - Code Style - Code Templates
 * 
 */
public class ValidacionUtils {

	public static String validarCamposRequeridos(Object[] listaCampos, String[] listaNombresCampos) {
		String resul = new String();
		List<String> listaNombresCamposVacios = new ArrayList<String>();
		if (listaCampos.length != 0 && listaNombresCampos.length != 0) {
			for (int i = 0; i < listaCampos.length; i++) {
				Object campo = listaCampos[i];
				String nombreCampo = listaNombresCampos[i];
				if (!Util.validarNoVacio(campo)) {
					listaNombresCamposVacios.add(nombreCampo);
				}
			}
			if (!listaNombresCamposVacios.isEmpty()) {
				if (listaNombresCamposVacios.size() == 1) {
					resul = "El campo: " + StringUtils.colocarSeparador(listaNombresCamposVacios, ", ") + " es requerido";
				} else {
					resul = "Los campos: " + StringUtils.colocarSeparador(listaNombresCamposVacios, ", ") + " son requeridos";
				}
			} else {
				resul = StringUtils.STRING_VACIO;
			}

		}
		return resul;

	}

}
