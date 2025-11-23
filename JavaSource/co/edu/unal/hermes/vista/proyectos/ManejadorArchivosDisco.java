package co.edu.unal.hermes.vista.proyectos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import co.edu.unal.hermes.modelo.ArchivoAval;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * The Class ManejadorArchivosDisco.
 */
public class ManejadorArchivosDisco extends ManejadorBase {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3505147943180267474L;
	
	/** The faltantes avales. */
	private String faltantesAvales;

	// Descargar de base de datos y pasar a disco archivos de aval.

	/**
	 * Descargar archivos solicitud aval.
	 * Volver a crear el atributo blob, descomentalo del mapeo y crear método de bytes en clase aval si se quiere acceder a los posibles blob
	 * y descomentar las tres líneas comentadas en el método
	 */
	public void descargarArchivosSolicitudAval() {

		List<ArchivoAval> listaArchivos = servicioGeneral.obtenerObjetos(ArchivoAval.class,
				"from ArchivoAval a where a.aval > 1");

		if (listaArchivos != null && listaArchivos.size() > 0) {
			for (int i = 0; i < listaArchivos.size(); i++) {
				ArchivoAval archivo = (ArchivoAval) listaArchivos.get(i);
				boolean bandera = true;
				try {
					//archivo.getBytes();
				} catch (Exception e) {
					bandera = false;
				}
				try {
					//if (archivo != null && bandera && archivo.getBytes().length > 2) {
					if (archivo != null && bandera) {
						System.out.println(archivo.getNombre());
						String nombreCompletoString = archivo.getNombre();
						File file = new File(nombreCompletoString);
						String nombreArchivo = "D:\\aval-backup\\" + archivo.getAval() + "\\" + archivo.getId() + "\\"
								+ file.getName();
						String nombreArchivoCarpeta = "D:\\aval-backup\\" + archivo.getAval() + "\\" + archivo.getId();
						File archivoValida = new File(nombreArchivoCarpeta);
						File f = new File(nombreArchivo);
						if (!f.exists()) {
							try {
								archivoValida.mkdirs();
								f.createNewFile();
								FileOutputStream fos = new FileOutputStream(f);
								//fos.write(archivo.getBytes());
								fos.close();

							} catch (IOException e) {
								System.out.println("Error descargando archivo " + archivo.getId() + " del aval "
										+ archivo.getAval());
							}
						}
					}
				} catch (Exception e) {
					System.out
							.println("Error descargando archivo " + archivo.getId() + " del aval " + archivo.getAval());
				}
			}
		}
		System.out.println("fin de descarga de archivos de solicitud de aval");

	}

	/**
	 * Subir archivos aval.
	 */
	public void subirArchivosAval() {
		List<ArchivoAval> listaArchivos = servicioGeneral.obtenerObjetos(ArchivoAval.class, "from ArchivoAval a");
		if (listaArchivos != null && listaArchivos.size() > 0) {
			for (int i = 0; i < listaArchivos.size(); i++) {
				ArchivoAval archivo = (ArchivoAval) listaArchivos.get(i);
				String nombreArchivo = "D:\\aval-backup\\" + archivo.getAval() + "\\" + archivo.getId() + "\\"
						+ archivo.getNombre();
				File f = new File(nombreArchivo);
				if (f.exists()) {
					InputStream fos;
					try {
						fos = new FileInputStream(f);
						String carpeta = "";
						Long ubicacion = archivo.getId() / 10000;
						carpeta = ubicacion.toString() + "\\";
						File folder = new File("D:\\HER_ARCHIVO_AVAL\\" + carpeta);
						if (!folder.isDirectory()) {
							folder.mkdirs();
						}
						cargarArchivoDisco(fos, "D:\\HER_ARCHIVO_AVAL\\" + carpeta, archivo.getId().toString());
						System.out.println(archivo.getAval());

					} catch (Exception e) {

						e.printStackTrace();
					}
				}
			}
		}
		System.out.println("fin de carga de archivos a disco");
	}

	/**
	 * Verificar archivos aval.
	 */
	public void verificarArchivosAval() {
		faltantesAvales = "";
		List<ArchivoAval> listaArchivos = servicioGeneral.obtenerObjetos(ArchivoAval.class, "from ArchivoAval a");

		if (listaArchivos != null && listaArchivos.size() > 0) {
			for (int i = 0; i < listaArchivos.size(); i++) {
				ArchivoAval archivo = (ArchivoAval) listaArchivos.get(i);

				String nombreArchivo = "D:\\HER_ARCHIVO_AVAL2\\" + archivo.getId();
				File f = new File(nombreArchivo);

				if (f.exists()) {
				} else {
					faltantesAvales = faltantesAvales + " - " + archivo.getId().toString();
				}
			}
		}
		System.out.println("fin de verificar aval");

	}

	/**
	 * Dividir archivos solicitud aval.
	 */
	public void dividirArchivosSolicitudAval() {
		List<ArchivoAval> listaArchivos = servicioGeneral.obtenerObjetos(ArchivoAval.class, "from ArchivoAval a");

		if (listaArchivos != null && listaArchivos.size() > 0) {
			for (int i = 0; i < listaArchivos.size(); i++) {
				ArchivoAval archivo = (ArchivoAval) listaArchivos.get(i);

				String nombreArchivo = "D:\\HER_ARCHIVO_AVAL2\\" + archivo.getId();
				File f = new File(nombreArchivo);

				if (f.exists()) {

					InputStream fos;
					try {
						fos = new FileInputStream(f);
						String carpeta = "";
						Long ubicacion = archivo.getId() / 10000;
						carpeta = ubicacion.toString() + "\\";

						File folder = new File("D:\\HER_ARCHIVO_AVAL\\" + carpeta);
						if (!folder.isDirectory()) {
							folder.mkdirs();
						}

						cargarArchivoDisco(fos, "D:\\HER_ARCHIVO_AVAL\\" + carpeta, archivo.getId().toString());

						System.out.println(archivo.getAval());

					} catch (Exception e) {

						e.printStackTrace();
					}
				}
			}
		}
		System.out.println("fin de carga de archivos a disco");

	}

	/**
	 * Gets the faltantes avales.
	 *
	 * @return the faltantes avales
	 */
	public String getFaltantesAvales() {
		return faltantesAvales;
	}

	/**
	 * Sets the faltantes avales.
	 *
	 * @param faltantesAvales the new faltantes avales
	 */
	public void setFaltantesAvales(String faltantesAvales) {
		this.faltantesAvales = faltantesAvales;
	}

}
