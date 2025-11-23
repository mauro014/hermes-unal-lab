package co.edu.unal.hermes.vista.convocatoriaAlianzas.modelo;

import java.util.List;

public abstract class DatosModelo<T> {

	public final static String SEPARADOR_COLUMNA = "~";
	public final static String SEPARADOR_REGISTRO = "\r\n";
	public static String SEPARADOR_FORMACION_UNO = "%";
	public static String SEPARADOR_FORMACION_DOS = ",";
	public static String SEPARADOR_PUBLICACIONES_UNO = "&";
	public static String SEPARADOR_PUBLICACIONES_DOS = ";";	
	public static String SEPARADOR_PARTICIPACIONES_UNO = "_";
	public static String SEPARADOR_PARTICIPACIONES_DOS = "<>";

	abstract boolean validar();

	/**
	 * Valida que los parámetros no contengan los separadores
	 * 
	 * @param obs
	 * @return
	 */
	protected boolean validarDatos(Object... obs) {
		for (Object o : obs) {
			if (o instanceof String && o != null) {
				String str = (String) o;
				if (str.contains(SEPARADOR_COLUMNA) || str.contains(SEPARADOR_REGISTRO)) {
					return false;
				}
			}
		}
		return true;
	}

	public String producirToString(List<T> listas) throws Exception {
		String ret = "";
		for (T ppa : listas) {
			/*if (!((DatosModelo<?>) ppa).validar()) {
				throw new Exception("No pasó la validación");
			}*/
			ret += ppa.toString() + SEPARADOR_REGISTRO;
		}
		return ret;
	}

}
