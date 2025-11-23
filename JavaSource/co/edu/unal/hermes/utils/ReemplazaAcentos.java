package co.edu.unal.hermes.utils;

import java.util.ArrayList;
import java.util.List;

public class ReemplazaAcentos {

	public static String queryQuitaTildes(String nc) {
		return " replace(replace(replace(replace(replace(lower(" + nc
				+ "),'ó','o'),'ú','u'),'á','a'),'é','e'),'í','i') ";
	}
	
	public static String queryQuitaCaracteresEspeciales(String nombreCampo) {
		return " replace(replace(replace(replace(replace(replace(replace(lower(" + nombreCampo
				+ "),'ó','o'),'ú','u'),'á','a'),'é','e'),'í','i'),'ñ','n'),'ü','u') ";
	}

	/**
	 * @param nombreCampo
	 * @param textoBuscar
	 * @return query del tipo: nombreCampo like '%textoBuscar%'
	 */
	public static String queryLike(String nombreCampo, String textoBuscar) {
		String sql = "(" + queryQuitaCaracteresEspeciales(nombreCampo)
				+ " LIKE '%' || "
				+ queryQuitaCaracteresEspeciales("'" + textoBuscar + "'")
				+ "|| '%') ";
		System.out.println("queryLike sql:" + sql);
		return sql;
	}

	public static boolean esNumero(String cadena) {
		boolean esNumero = false;
		if (cadena == null) {
			esNumero = false;
		} else {
			try {
				new Long(cadena);
				esNumero = true;
			} catch (NumberFormatException ex) {
				esNumero = false;
			}
		}
		System.out.println("esNumero: " + cadena + " : " + esNumero);
		return esNumero;
	}

	public static String reemplaza(String y) {

		if (y == null) {
			return null;
		}

		String origen = y;
		origen = origen.trim();
		origen = origen.toUpperCase();
		origen = origen.replace('Á', 'A');
		origen = origen.replace('É', 'E');
		origen = origen.replace('Í', 'I');
		origen = origen.replace('Ó', 'O');
		origen = origen.replace('Ú', 'U');
		origen = origen.replace('Ñ', 'N');
		return origen;
	}

	public static List<String> listaPalabrasConTildes(String palabra) {

		List palabras = new ArrayList();

		if (!tieneTilde(palabra)) {
			char[] cadena = palabra.toCharArray();
			for (int i = 0; i < cadena.length; i++) {
				if (esVocalSinTilde(cadena[i])) {
					char letraTilde = ponerTilde(cadena[i]);
					String s_palabra = new String(cadena);
					char[] nueva = s_palabra.toCharArray();
					nueva[i] = letraTilde;
					palabras.add(String.copyValueOf(nueva));
				}
			}
		} else {
			palabras.add(palabra);
		}

		return palabras;
	}

	public static boolean tieneTilde(String palabra) {
		char[] cadena = palabra.toCharArray();
		for (int i = 0; i < cadena.length; i++) {
			if (esVocalConTilde(cadena[i])) {
				return true;
			}
		}
		return false;
	}

	private static boolean esVocalConTilde(char letra) {
		if (letra == 'á' || letra == 'é' || letra == 'í' || letra == 'ó'
				|| letra == 'ú' || letra == 'Á' || letra == 'É' || letra == 'Í'
				|| letra == 'Ó' || letra == 'Ú') {
			return true;
		}
		return false;
	}

	private static char ponerTilde(char c) {
		if (c == 'a') {
			return 'á';
		}
		if (c == 'e') {
			return 'é';
		}
		if (c == 'i') {
			return 'í';
		}
		if (c == 'o') {
			return 'ó';
		}
		if (c == 'u') {
			return 'ú';
		}
		if (c == 'A') {
			return 'Á';
		}
		if (c == 'E') {
			return 'É';
		}
		if (c == 'I') {
			return 'Í';
		}
		if (c == 'O') {
			return 'Ó';
		}
		if (c == 'U') {
			return 'Ú';
		}
		return c;
	}

	private static boolean esVocalSinTilde(char letra) {
		if (letra == 'a' || letra == 'e' || letra == 'i' || letra == 'o'
				|| letra == 'u' || letra == 'A' || letra == 'E' || letra == 'I'
				|| letra == 'O' || letra == 'U') {
			return true;
		} else {
			return false;
		}
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

	public static List listaPalabrasBusqueda(String palabra) {
		List palabras = new ArrayList();
		palabra = quitarTildes(palabra);
		palabras.add(palabra);
		palabras.addAll(listaPalabrasConTildes(palabra));
		return palabras;
	}

}
