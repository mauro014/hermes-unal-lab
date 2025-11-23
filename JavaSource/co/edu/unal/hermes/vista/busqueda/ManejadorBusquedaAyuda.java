package co.edu.unal.hermes.vista.busqueda;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import co.edu.unal.hermes.modelo.ArchivoInstructivo;
import co.edu.unal.hermes.modelo.Instructivo;
import co.edu.unal.hermes.modelo.InstructivoClasificacion;
import co.edu.unal.hermes.modelo.NovedadClasificacion;
import co.edu.unal.hermes.modelo.Pregunta;
import co.edu.unal.hermes.modelo.PreguntaClasificacion;
import co.edu.unal.hermes.utils.ReemplazaAcentos;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorBusquedaAyuda extends ManejadorBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3619907160192666264L;

	// Determina si la busqueda ya fue realizada
	private String campoBusqueda;

	// Listados instructivos
	private List<Instructivo> listaInstructivos;
	private List<Object[]> instructivosClasificados;

	// Listados preguntas
	private List<Pregunta> listaPreguntas;
	private List<Object[]> preguntasClasificadas;

	// Objetos de busqueda
	private Instructivo instructivoSeleccionado;
	int resultadosInstructivos = 0;
	String path = RUTA_ARCHIVOS + File.separator + "HER_ARCHIVO_INSTRUCTIVO";
	File actual;
	private StreamedContent contentInstructivo;

	public ManejadorBusquedaAyuda() {
		getVerificaParametroUrl();
	}

	/**
	 * Se verifica si por url se envia algun parámetro para la búsqueda
	 * 
	 * @return
	 */
	public String getVerificaParametroUrl() {
		FacesContext context = FacesContext.getCurrentInstance();
		request = (HttpServletRequest) context.getExternalContext().getRequest();

		String url = request.getQueryString();

		if (!esCadenaVacia(url) && request.getParameter("id") != null && !"".equals(request.getParameter("id"))) {
			String id = new String(request.getParameter("id"));
			request = null;

			String consultaArchivos = "select a from ArchivoInstructivo a where a.instructivo.id = '" + id
					+ "' and a.estado = 'V' order by a.id desc";
			List<ArchivoInstructivo> listaA = (List<ArchivoInstructivo>) servicioGeneral
					.obtenerObjetos(ArchivoInstructivo.class, consultaArchivos);

			if (!esListaVacia(listaA)) {
				ArchivoInstructivo archivoSeleccionado = (ArchivoInstructivo) listaA.get(0);

				if (archivoSeleccionado != null) {
					String ext = obtenerExtensionArchivo(archivoSeleccionado.getNombre());
					if (!".PDF".equals(ext.toUpperCase())) {
						descargarArchivoInstructivo(archivoSeleccionado);
					} else {
						actual = new File(path + obtenerSubCarpetaArchivo(archivoSeleccionado.getId()) + File.separator
								+ archivoSeleccionado.getId());
						try {
							contentInstructivo = new DefaultStreamedContent(
									new ByteArrayInputStream(
											org.apache.commons.io.FileUtils.readFileToByteArray(actual)),
									"application/pdf");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (contentInstructivo != null) {
							sesion.setAttribute("instructivoPDF", contentInstructivo);
						}
					}
				}
			}

		}

		return "";

	}

	public String getBusquedaInstructivos() {
		busquedaInstructivos();
		return "";
	}

	/**
	 * Se buscan los instructivos, ya sea todos los activos o de acuerdo a
	 * palabra clave
	 */
	public void busquedaInstructivos() {
		String sqlInstructivos = "";

		try {
			if (this.campoBusqueda != null && this.campoBusqueda.trim().length() > 0) {
				campoBusqueda = campoBusqueda.trim();
				String tempKey = this.campoBusqueda.toUpperCase();
				String[] keyArray = tempKey.split(" ");

				ArrayList<String> array = eliminarPalabrasComunes(keyArray);

				String likeNombre = "";
				String likeDescripcion = "";
				String likeClasificacion = "";

				for (int i = 0; i < array.size(); i++) {
					if (i == 0) {
						likeNombre = likeNombre + " UPPER(HER_INSTRUCTIVO.INS_NOMBRE) like '%" + array.get(i)
								+ "%' or UPPER(HER_INSTRUCTIVO.INS_NOMBRE) like '%"
								+ ReemplazaAcentos.quitarTildes(array.get(i)) + "%'";
						likeDescripcion = likeDescripcion + " UPPER(HER_INSTRUCTIVO.INS_DESCRIPCION) like '%"
								+ (String) array.get(i) + "%' or UPPER(HER_INSTRUCTIVO.INS_DESCRIPCION) like '%"
								+ ReemplazaAcentos.quitarTildes(array.get(i)) + "%'";
						likeClasificacion = likeClasificacion
								+ " UPPER(HER_INSTRUCTIVO_CLASIFICACION.ICL_NOMBRE) like '%" + array.get(i)
								+ "%' or UPPER(HER_INSTRUCTIVO_CLASIFICACION.ICL_NOMBRE) like '%"
								+ ReemplazaAcentos.quitarTildes(array.get(i)) + "%'";
					} else {
						likeNombre = likeNombre + " or UPPER(HER_INSTRUCTIVO.INS_NOMBRE) like '%" + array.get(i)
								+ "%' or UPPER(HER_INSTRUCTIVO.INS_NOMBRE) like '%"
								+ ReemplazaAcentos.quitarTildes(array.get(i)) + "%'";
						likeDescripcion = likeDescripcion + " or UPPER(HER_INSTRUCTIVO.INS_DESCRIPCION) like '%"
								+ array.get(i) + "%' or UPPER(HER_INSTRUCTIVO.INS_DESCRIPCION) like '%"
								+ ReemplazaAcentos.quitarTildes(array.get(i)) + "%'";
						likeClasificacion = likeClasificacion
								+ " or UPPER(HER_INSTRUCTIVO_CLASIFICACION.ICL_NOMBRE) like '%" + array.get(i)
								+ "%' or UPPER(HER_INSTRUCTIVO_CLASIFICACION.ICL_NOMBRE) like '%"
								+ ReemplazaAcentos.quitarTildes(array.get(i)) + "%'";
					}
				}

				sqlInstructivos += " and ((upper(HER_INSTRUCTIVO.INS_NOMBRE) LIKE '%" + this.campoBusqueda.toUpperCase()
						+ "%' or " + "upper(HER_INSTRUCTIVO.INS_DESCRIPCION) LIKE '%" + this.campoBusqueda.toUpperCase()
						+ "%' or " + "upper(HER_INSTRUCTIVO_CLASIFICACION.ICL_NOMBRE) LIKE '%"
						+ this.campoBusqueda.toUpperCase() + "%') or " + "(" + likeNombre + ") or (" + likeDescripcion
						+ ") or (" + likeClasificacion + ")) ";

			}
			listaInstructivos = servicioGeneral.obtenerInstructivos(sqlInstructivos);
			instructivosClasificados = clasificarInstructivos(listaInstructivos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Se organiza lista de instructivos por componente y clasificación
	 * 
	 * @param list
	 * @return
	 */
	private List<Object[]> clasificarInstructivos(List<Instructivo> list) {
		List<Object[]> listaComponentes = new ArrayList<Object[]>();
		Iterator<Instructivo> i = list.iterator();

		while (i.hasNext()) {
			Instructivo instructivo = i.next();
			Object[] componenteEncontrado = null;
			boolean existeComponente = false;
			String nombreComponente = "No definido";
			if (instructivo.getComponente() != null) {
				if (instructivo.getComponente().equals(Instructivo.COMPONENTE_LINEAMIENTOS_PROTOCOLOS)) {
					nombreComponente = "Documentos de lineamientos y protocolos";
				} else if (instructivo.getComponente().equals(Instructivo.COMPONENTE_INVESTIGACION)) {
					nombreComponente = "Investigación";
				} else if (instructivo.getComponente().equals(Instructivo.COMPONENTE_EXTENSION)) {
					nombreComponente = "Extensión";
				} else if (instructivo.getComponente().equals(Instructivo.COMPONENTE_LABORATORIOS)) {
					nombreComponente = "Laboratorios";
				} else if (instructivo.getComponente().equals(Instructivo.COMPONENTE_EDITORIAL)) {
					nombreComponente = "Sistema de Gestión Editorial";
				} else if (instructivo.getComponente().equals(Instructivo.COMPONENTE_PROPIEDAD)) {
					nombreComponente = "Propiedad Intelectual";
				} else if (instructivo.getComponente().equals(Instructivo.COMPONENTE_COLECCIONES)) {
					nombreComponente = "Colecciones Científicas y Permisos de Biodiversidad";
				} else if (instructivo.getComponente().equals(Instructivo.COMPONENTE_OTROS)) {
					nombreComponente = "Otros";
				}
			}
			Iterator<Object[]> c = listaComponentes.iterator();
			while (c.hasNext()) {

				Object[] componente = c.next();
				if (((String) componente[0]).equals(nombreComponente)) {
					componenteEncontrado = componente;
					existeComponente = true;
					break;
				}
			}

			if (!existeComponente) {
				Object[] componente = new Object[2];
				componente[0] = nombreComponente;
				componente[1] = new ArrayList<Object[]>();
				componenteEncontrado = componente;
				listaComponentes.add(componente);
			}

			if (componenteEncontrado != null) {

				List<Object[]> listaClasificaciones = (ArrayList<Object[]>) componenteEncontrado[1];

				Object[] clasificacionEncontrada = null;
				boolean existeClasificacion = false;

				Iterator<Object[]> j = listaClasificaciones.iterator();
				while (j.hasNext()) {

					Object[] clasificacion = j.next();
					if (((InstructivoClasificacion) clasificacion[0]).getId()
							.equals(instructivo.getClasificacion().getId())) {
						clasificacionEncontrada = clasificacion;
						existeClasificacion = true;
						break;
					}

				}

				if (!existeClasificacion) {
					Object[] clasificacion = new Object[2];
					clasificacion[0] = instructivo.getClasificacion();
					clasificacion[1] = new ArrayList<Instructivo>();
					clasificacionEncontrada = clasificacion;
					listaClasificaciones.add(clasificacion);
				}
				if (clasificacionEncontrada != null) {
					List<Instructivo> listaInstructivos = (ArrayList<Instructivo>) clasificacionEncontrada[1];
					listaInstructivos.add(instructivo);
				}
			}
		}

		return listaComponentes;
	}

	/**
	 * Se transforma arreglo de palabras eliminando conectores y artículos
	 * 
	 * @param arreglo
	 * @return
	 */
	public ArrayList<String> eliminarPalabrasComunes(String[] arreglo) {

		ArrayList<String> palabrasComunes = new ArrayList<String>();
		String fuente = "a adonde al ante así aunque bajo bien cabe como con contra cuando de desde donde durante e el en entre ésta éstas éste esto éstos "
				+ "hacia hasta la las los luego más mediante ni o para pero por porque puesto que según si sin sino so sobre tan tras u un una unas unos y ya";
		String[] arregloComunes = fuente.split(" ");
		for (int i = 0; i < arregloComunes.length; i++) {
			palabrasComunes.add(arregloComunes[i].toUpperCase());
		}
		ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i < arreglo.length; i++) {
			if (!palabrasComunes.contains(arreglo[i])) {
				result.add(arreglo[i]);
			}
		}
		return result;
	}

	public void reiniciarBusqueda() {
		campoBusqueda = "";
		busquedaInstructivos();
	}

	public String getBusquedaPreguntas() {
		busquedaPreguntas();
		return "";
	}

	public void busquedaPreguntas() {
		String sqlPreguntas = "";

		try {
			if (this.campoBusqueda != null && this.campoBusqueda.trim().length() > 0) {
				campoBusqueda = campoBusqueda.trim();
				String tempKey = this.campoBusqueda.toUpperCase();
				String[] keyArray = tempKey.split(" ");

				ArrayList<String> Array = eliminarPalabrasComunes(keyArray);

				String likeDescripcion = "";
				String likeRespuesta = "";
				String likeClasificacion = "";
				String likeEtiqueta = "";

				for (int i = 0; i < Array.size(); i++) {
					if (i == 0) {
						likeDescripcion = likeDescripcion + " UPPER(HER_PREGUNTA.PRE_DESCRIPCION) like '%"
								+ (String) Array.get(i) + "%' or UPPER(HER_PREGUNTA.PRE_DESCRIPCION) like '%"
								+ ReemplazaAcentos.quitarTildes((String) Array.get(i)) + "%'";
						likeRespuesta = likeRespuesta + " UPPER(HER_PREGUNTA.PRE_RESPUESTA) like '%"
								+ (String) Array.get(i) + "%' or UPPER(HER_PREGUNTA.PRE_RESPUESTA) like '%"
								+ ReemplazaAcentos.quitarTildes((String) Array.get(i)) + "%'";
						likeClasificacion = likeClasificacion + " UPPER(HER_PREGUNTA_CLASIFICACION.PCL_NOMBRE) like '%"
								+ (String) Array.get(i) + "%' or UPPER(HER_PREGUNTA_CLASIFICACION.PCL_NOMBRE) like '%"
								+ ReemplazaAcentos.quitarTildes((String) Array.get(i)) + "%'";
						likeEtiqueta = likeEtiqueta + " UPPER(HER_PREGUNTA.PRE_ETIQUETA) like '%"
								+ (String) Array.get(i) + "%' or UPPER(HER_PREGUNTA.PRE_ETIQUETA) like '%"
								+ ReemplazaAcentos.quitarTildes((String) Array.get(i)) + "%'";
					} else {
						likeDescripcion = likeDescripcion + " or UPPER(HER_PREGUNTA.PRE_DESCRIPCION) like '%"
								+ (String) Array.get(i) + "%' or UPPER(HER_PREGUNTA.PRE_DESCRIPCION) like '%"
								+ ReemplazaAcentos.quitarTildes((String) Array.get(i)) + "%'";
						likeRespuesta = likeRespuesta + " or UPPER(HER_PREGUNTA.PRE_RESPUESTA) like '%"
								+ (String) Array.get(i) + "%' or UPPER(HER_PREGUNTA.PRE_RESPUESTA) like '%"
								+ ReemplazaAcentos.quitarTildes((String) Array.get(i)) + "%'";
						likeClasificacion = likeClasificacion
								+ " or UPPER(HER_PREGUNTA_CLASIFICACION.PCL_NOMBRE) like '%" + (String) Array.get(i)
								+ "%' or UPPER(HER_PREGUNTA_CLASIFICACION.PCL_NOMBRE) like '%"
								+ ReemplazaAcentos.quitarTildes((String) Array.get(i)) + "%'";
						likeEtiqueta = likeEtiqueta + " or UPPER(HER_PREGUNTA.PRE_ETIQUETA) like '%"
								+ (String) Array.get(i) + "%' or UPPER(HER_PREGUNTA.PRE_ETIQUETA) like '%"
								+ ReemplazaAcentos.quitarTildes((String) Array.get(i)) + "%'";
					}
				}

				sqlPreguntas += " and ((upper(HER_PREGUNTA.PRE_DESCRIPCION) LIKE '%" + this.campoBusqueda.toUpperCase()
						+ "%' or " + "upper(HER_PREGUNTA.PRE_RESPUESTA) LIKE '%" + this.campoBusqueda.toUpperCase()
						+ "%' or " + "upper(HER_PREGUNTA.PRE_ETIQUETA) LIKE '%" + this.campoBusqueda.toUpperCase()
						+ "%' or " + "upper(HER_PREGUNTA_CLASIFICACION.PCL_NOMBRE) LIKE '%"
						+ this.campoBusqueda.toUpperCase() + "%') or " + "(" + likeDescripcion + ") or ("
						+ likeRespuesta + ") or (" + likeClasificacion + ") or (" + likeEtiqueta + ")) ";

			}
			listaPreguntas = servicioGeneral.obtenerPreguntas(sqlPreguntas);
			preguntasClasificadas = clasificarPreguntas(listaPreguntas);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Se organiza lista de instructivos por componente y clasificación
	 * 
	 * @param list
	 * @return
	 */
	private List<Object[]> clasificarPreguntas(List<Pregunta> list) {
		List<Object[]> listaClasificaciones = new ArrayList<Object[]>();
		Iterator<Pregunta> i = list.iterator();

		while (i.hasNext()) {
			Pregunta pregunta = i.next();
			Object[] clasificacionEncontrada = null;
			boolean existeClasificacion = false;

			Iterator<Object[]> j = listaClasificaciones.iterator();
			while (j.hasNext()) {

				Object[] clasificacion = j.next();
				if (((PreguntaClasificacion) clasificacion[0]).getId().equals(pregunta.getClasificacion().getId())) {
					clasificacionEncontrada = clasificacion;
					existeClasificacion = true;
					break;
				}
			}

			if (!existeClasificacion) {
				Object[] clasificacion = new Object[2];
				clasificacion[0] = pregunta.getClasificacion();
				clasificacion[1] = new ArrayList<Pregunta>();
				clasificacionEncontrada = clasificacion;
				listaClasificaciones.add(clasificacion);
			}
			if (clasificacionEncontrada != null) {
				List<Pregunta> listaPreguntasFrec = (ArrayList<Pregunta>) clasificacionEncontrada[1];
				listaPreguntasFrec.add(pregunta);
			}

		}

		return listaClasificaciones;
	}

	public List<NovedadClasificacion> getNovedades() {
		List<NovedadClasificacion> listaClasificacionNovedades = new ArrayList<NovedadClasificacion>();
		listaClasificacionNovedades = servicioGeneral.obtenerObjetos(NovedadClasificacion.class,
				"select distinct clas from NovedadClasificacion clas, Novedad nov where nov.estado = 'A' and nov.clasificacion.id = clas.id "
						+ "order by clas.nombre asc");
		return listaClasificacionNovedades;
	}

	public int getNumeroResultados() {
		return listaInstructivos.size();
	}

	public void setCampoBusqueda(String campoBusqueda) {
		this.campoBusqueda = campoBusqueda;
	}

	public String getCampoBusqueda() {
		return campoBusqueda;
	}

	public List<Instructivo> getListaInstructivos() {
		return listaInstructivos;
	}

	public void setListaInstructivos(List<Instructivo> listaInstructivos) {
		this.listaInstructivos = listaInstructivos;
	}

	public int getResultadosInstructivos() {
		return resultadosInstructivos;
	}

	public void setResultadosInstructivos(int resultadosInstructivos) {
		this.resultadosInstructivos = resultadosInstructivos;
	}

	public List<Object[]> getInstructivosClasificados() {
		return instructivosClasificados;
	}

	public void setInstructivosClasificados(List<Object[]> instructivosClasificados) {
		this.instructivosClasificados = instructivosClasificados;
	}

	public Instructivo getInstructivoSeleccionado() {
		return instructivoSeleccionado;
	}

	public void setInstructivoSeleccionado(Instructivo instructivoSeleccionado) {
		this.instructivoSeleccionado = instructivoSeleccionado;
	}

	public StreamedContent getContentInstructivo() {
		StreamedContent content = null;
		try {
			content = (StreamedContent) sesion.getAttribute("instructivoPDF");
		} catch (Exception e) {

		}
		if (content != null) {
			return content;
		} else {
			try {
				contentInstructivo = new DefaultStreamedContent(
						new ByteArrayInputStream(org.apache.commons.io.FileUtils.readFileToByteArray(actual)),
						"application/pdf");
			} catch (IOException e) {

			}
			return contentInstructivo;
		}
	}

	public void setContentInstructivo(StreamedContent contentInstructivo) {
		this.contentInstructivo = contentInstructivo;
	}

	public List<Pregunta> getListaPreguntas() {
		return listaPreguntas;
	}

	public void setListaPreguntas(List<Pregunta> listaPreguntas) {
		this.listaPreguntas = listaPreguntas;
	}

	public List<Object[]> getPreguntasClasificadas() {
		return preguntasClasificadas;
	}

	public void setPreguntasClasificadas(List<Object[]> preguntasClasificados) {
		this.preguntasClasificadas = preguntasClasificados;
	}

}
