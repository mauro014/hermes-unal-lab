package co.edu.unal.hermes.vista.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.hibernate.Hibernate;
import org.hibernate.Session;

/**
 * clase que relaciona utilidades para los DAO en Hibernate
 * 
 */
public class Util {

	private static Util util = null;

	private Util() {
	}

	public static Util getInstace() {
		if (util == null) {
			util = new Util();
		}
		return util;
	}

	public String quitarTildes(String name) {
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

	public String convertirPrimeraLetraMayuscula(String str) {
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

	public String adicionarPorcentajes(String valor) {
		return "%" + valor + "%";
	}

	public String convertirPrimerasLetrasMayuscula(String value) {
		StringTokenizer st = new StringTokenizer(value);
		String result = "";
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (result.equals("")) {
				result = convertirPrimeraLetraMayuscula(token);
			} else {
				result = result + " " + convertirPrimeraLetraMayuscula(token);
			}
		}
		return result;
	}

	public String mayusculaSinTildes(String valor) {
		String s_mayusculas = valor.toUpperCase();
		return quitarTildes(s_mayusculas);
	}

	public List valoresConsultar(String valor, boolean porcentajes) {
		List result = new ArrayList();
		if (porcentajes) {
			if (valor == null) {
				result.add(adicionarPorcentajes(""));
			} else {
				result.add(adicionarPorcentajes(valor));
				String valor_lower = valor.toLowerCase();
				String valor_upper = valor.toUpperCase();
				String valor_sinTildes = quitarTildes(valor);
				String valor_primeraMayuscula = convertirPrimeraLetraMayuscula(valor);
				if (!valor.equals(valor_upper)) {
					result.add(adicionarPorcentajes(valor_upper));
				}
				if (!valor.equals(valor_lower)) {
					result.add(adicionarPorcentajes(valor_lower));
				}
				if (!valor.equals(valor_sinTildes)) {
					result.add(adicionarPorcentajes(valor_sinTildes));
				}
				if (!valor.equals(valor_primeraMayuscula)) {
					result.add(adicionarPorcentajes(valor_primeraMayuscula));
				}
			}

		} else {
			if (valor == null) {
				result.add("");
			} else {
				result.add(valor);
				String valor_lower = valor.toLowerCase();
				String valor_upper = valor.toUpperCase();
				String valor_sinTildes = quitarTildes(valor);
				String valor_primeraMayuscula = convertirPrimeraLetraMayuscula(valor);
				if (!valor.equals(valor_upper)) {
					result.add(valor_upper);
				}
				if (!valor.equals(valor_lower)) {
					result.add(valor_lower);
				}
				if (!valor.equals(valor_sinTildes)) {
					result.add(valor_sinTildes);
				}
				if (!valor.equals(valor_primeraMayuscula)) {
					result.add(valor_primeraMayuscula);
				}
			}
		}
		return result;
	}

	public List consultaPadre1Parametro(Session session, String objeto, String atributo, String valorAtributo,
			String padre, Object valorPadre, boolean incluirTodos) {

		List l, result;

		result = new ArrayList();

		List valores = valoresConsultar(valorAtributo, true);
		Iterator itValores = valores.iterator();
		String extraFiltroSigla ="";
		if(!incluirTodos) {
			extraFiltroSigla = "and (sigla is not null and sigla <> 'TODO')";
		}

		if (valorPadre == null) {

			while (itValores.hasNext()) {
				String valorActual = (String) itValores.next();
				if (!objeto.toLowerCase().equals("pais") && !objeto.toLowerCase().equals("departamento")
						&& !objeto.toLowerCase().equals("ciudad")) {
					l = session.createQuery("from " + objeto + " where ( " + atributo + " like '" + valorActual + "') ")
							.list();
				} else {
					l = session.createQuery("from " + objeto + " where ( " + atributo + " like '" + valorActual
							+ "') and sigla is not null "+extraFiltroSigla).list();
				}
				result.addAll(l);
			}

		} else {
			String queryString = "from " + objeto + " where ( " + padre + " = :padre AND " + atributo
					+ " like :atributo ) and sigla is not null "+ extraFiltroSigla;
			if (!objeto.toLowerCase().equals("pais") && !objeto.toLowerCase().equals("departamento")
					&& !objeto.toLowerCase().equals("ciudad")) {
				queryString = "from " + objeto + " where ( " + padre + " = :padre AND " + atributo
						+ " like :atributo )";
			}

			while (itValores.hasNext()) {
				String valorActual = (String) itValores.next();
				l = session.createQuery(queryString)
						.setParameter("padre", valorPadre, Hibernate.entity(valorPadre.getClass()))
						.setParameter("atributo", valorActual, Hibernate.STRING).list();
				result.addAll(l);
			}
		}
		return result;
	}

	/**
	 * Cadena de caracteres vacía retorna falso, al igual que número igual a
	 * cero
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean validarNoVacio(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof String) {
			String str = (String) obj;
			return str.trim().length() > 0;
		}
		if (obj instanceof Number) {
			Number n = (Number) obj;
			return n.doubleValue() != 0.0;
		}
		return true;
	}

	/**
	 * 
	 * @param obj
	 * @param from
	 *            inclusive
	 * @param to
	 *            inclusive
	 * @return
	 */
	public boolean validarRango(Number obj, Number from, Number to) {
		if (obj == null) {
			return false;
		}
		return (from == null || obj.doubleValue() >= from.doubleValue())
				&& (to == null || obj.doubleValue() <= to.doubleValue());
	}

	/**
	 * Cambia el caracter ENTER de dos caracteres (\r\n) a uno solo (\n).
	 * 
	 * @author dgbenitezc
	 * @param cadena
	 * @return
	 */
	public static String removerEnter(String cadena) {
		if (cadena != null) {
			String copia = new String(cadena);
			System.out.println("removerEnter   antes:" + copia.length());
			copia = copia.replaceAll("\r\n", "\n");
			System.out.println("removerEnter despues:" + copia.length());
			return copia;
		} else {
			System.out.println("removerEnter null.");
			return cadena;
		}
	}

	/**
	 * Gets the random image name.
	 *
	 * @return the random image name
	 */
	public static String getRandomImageName() {
		int i = (int) (Math.random() * 100000);

		return String.valueOf(i);
	}

}
