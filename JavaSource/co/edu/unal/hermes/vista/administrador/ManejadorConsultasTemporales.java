/**
 * @author Martha Liliana Correa O.
 * @date 24/05/2023
 */

package co.edu.unal.hermes.vista.administrador;

import java.util.ArrayList;
import java.util.List;
import java.io.File;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import co.edu.unal.hermes.modelo.Archivo;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorConsultasTemporales extends ManejadorBase {

	/**
	 * 
	 */
	private List<Archivo> listaArchivosISBN;
	private long totalPesoArchivos;
	

	public ManejadorConsultasTemporales() {
		// Inicialización de valores
		consultarTamanoArchivos();

	}

	public void consultarTamanoArchivos() {
		
		String hql = "select a from Archivo a where a.proyecto.modalidad.id in ('1080','805','452')";
		ArrayList<Archivo> lista = (ArrayList<Archivo>) servicioGeneral.obtenerObjetos(hql);
		
		
		
        //System.out.println("Size of " + file.getName() + ": " + formatSize(size));
    

		if (lista.size() > 0) {
			listaArchivosISBN = new ArrayList<Archivo>();
			totalPesoArchivos = 0;
			for (int i = 0; i < lista.size(); i++) {
				Archivo arc = (Archivo) lista.get(i);
				listaArchivosISBN.add(arc);
				
				if (arc != null && arc.getDatos() != null && arc.getBytes().length > 1) {
					totalPesoArchivos = totalPesoArchivos + arc.getBytes().length;
				}else {
				
					String path = RUTA_ARCHIVOS + "HER_ARCHIVO" + obtenerSubCarpetaArchivo(arc.getId())+"//" + arc.getId().toString();
					
					File file = new File(path);
					System.out.println("Pry: "+ arc.getProyecto().getId().toString() +" - Name of " + arc.getId()+"-"+arc.getNombre() + "Size: "+file.length());
					totalPesoArchivos = totalPesoArchivos + file.length();
				}
			}
		}

	}

	public long getTotalPesoArchivos() {
		return totalPesoArchivos / (1024 * 1024);
	}

	public void setTotalPesoArchivos(long totalPesoArchivos) {
		this.totalPesoArchivos = totalPesoArchivos;
	}

}
