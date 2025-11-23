/*
 * Created on 01-sep-2005 
 */

package co.edu.unal.hermes.vista;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.DocumentoVice;

public class ManejadorDocumentosVice extends ManejadorBase {

	private TreeNode root;
	private List<DocumentoVice> listaDocumentos;
	private List<DocumentoVice> listaCarpetas;
	private DocumentoVice documentoSeleccionado;
	private DocumentoVice documentoNuevo;
	private SelectItem[] documentoNuevaItem;
	private SelectItem[] documentoTipoNuevaItem;
	private boolean esArchivoDocumentoNuevo;

	private UploadedFile file;

	public ManejadorDocumentosVice() {

		documentoNuevo = new DocumentoVice();
		documentoNuevo.setEsArchivo("N");
		setEsArchivoDocumentoNuevo(false);
		documentoNuevo.setIdPadre(Long.parseLong(String.valueOf(0)));

		cargarArchivos();

	}

	private void cargarArchivos() {
		listaCarpetas = new ArrayList<DocumentoVice>();
		listaDocumentos = servicioGeneral.obtenerListaDocumentosVice("0");
		root = new DefaultTreeNode("Root", null);

		for (DocumentoVice dv : listaDocumentos) {
			cargarNodos(dv, null);
		}
		cargarCarpetasItem();
	}

	private void cargarCarpetasItem() {
		documentoNuevaItem = new SelectItem[listaCarpetas.size() + 1];
		documentoNuevaItem[0] = new SelectItem("0", "(0) - RaÌz");
		for (int i = 1; i <= listaCarpetas.size(); i++) {
			DocumentoVice dv = (DocumentoVice) listaCarpetas.get(i - 1);
			documentoNuevaItem[i] = new SelectItem(dv.getId(), "(" + dv.getId()
					+ ") - " + dv.getNombre());
		}
	}

	private void cargarNodos(DocumentoVice dv, TreeNode padre) {
		if (padre == null) {
			if (dv.getEsArchivo().equals("Y")) {
				TreeNode node0 = new DefaultTreeNode("document", dv, root);
			} else {
				listaCarpetas.add(dv);
				TreeNode node0 = new DefaultTreeNode(dv, root);
				List<DocumentoVice> listaDocumentosHijos;
				listaDocumentosHijos = servicioGeneral
						.obtenerListaDocumentosVice(dv.getId().toString());
				for (DocumentoVice dvh : listaDocumentosHijos) {
					cargarNodos(dvh, node0);
				}
			}
		} else {
			if (dv.getEsArchivo().equals("Y")) {
				TreeNode node0 = new DefaultTreeNode("document", dv, padre);
			} else {
				listaCarpetas.add(dv);
				TreeNode node0 = new DefaultTreeNode(dv, padre);
				List<DocumentoVice> listaDocumentosHijos;
				listaDocumentosHijos = servicioGeneral
						.obtenerListaDocumentosVice(dv.getId().toString());
				for (DocumentoVice dvh : listaDocumentosHijos) {
					cargarNodos(dvh, node0);
				}
			}
		}
	}

	public TreeNode getRoot() {
		return root;
	}

	public List<DocumentoVice> getListaDocumentos() {
		return listaDocumentos;
	}

	public void descargarDocumentoEvento() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		String path = RUTA_ARCHIVOS+"HER_DOCUMENTO_VICE//"
				+ documentoSeleccionado.getId() + "//"
				+ documentoSeleccionado.getNombre();
		File ficheroXLS = new File(path);

		FileInputStream fis;
		try {
			fis = new FileInputStream(ficheroXLS);

			byte[] bytes = new byte[1000];
			int read = 0;

			if (!ctx.getResponseComplete()) {
				String fileName = ficheroXLS.getName();
				String contentType = "application/msword";
				HttpServletResponse response = (HttpServletResponse) ctx
						.getExternalContext().getResponse();

				response.setContentType(contentType);

				response.setHeader("Content-Disposition",
						"attachment;filename=\"" + fileName + "\"");

				ServletOutputStream out = response.getOutputStream();

				while ((read = fis.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}

				out.flush();
				out.close();
				ctx.responseComplete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setDocumentoSeleccionado(DocumentoVice documentoSeleccionado) {
		this.documentoSeleccionado = documentoSeleccionado;
	}

	public DocumentoVice getDocumentoSeleccionado() {
		return documentoSeleccionado;
	}

	public DocumentoVice getDocumentoNuevo() {
		return documentoNuevo;
	}

	public void setDocumentoNuevo(DocumentoVice documentoNuevo) {
		this.documentoNuevo = documentoNuevo;
	}

	public void setDocumentoNuevaItem(SelectItem[] documentoNuevaItem) {
		this.documentoNuevaItem = documentoNuevaItem;
	}

	public SelectItem[] getDocumentoNuevaItem() {
		return documentoNuevaItem;
	}

	public void setDocumentoTipoNuevaItem(SelectItem[] documentoTipoNuevaItem) {
		this.documentoTipoNuevaItem = documentoTipoNuevaItem;
	}

	public SelectItem[] getDocumentoTipoNuevaItem() {
		return documentoTipoNuevaItem;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void copyFile(String fileName, InputStream in) {
		try {

			// write the inputStream to a FileOutputStream
			OutputStream out = new FileOutputStream(new File(fileName));

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			in.close();
			out.flush();
			out.close();

			System.out.println("New file created!");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void cargarArchivo() {
		if (esArchivoDocumentoNuevo) {
			Long id = null;
			if (file != null) {
				documentoNuevo.setEstado("A");
				String name = remove(file.getFileName());
				documentoNuevo.setNombre(name);
				servicioGeneral.guardarObjeto(documentoNuevo);
				id = documentoNuevo.getId();
				if (id != null) {
					String directorio = "RUTA_ARCHIVOSHER_DOCUMENTO_VICE//" + documentoNuevo.getId();
					String destination = directorio + "//"	+ name; 
					new File(directorio).mkdirs();
					try {
						copyFile(destination, file.getInputstream());
					} catch (IOException e) {
						
						e.printStackTrace();
					}
				}
				documentoNuevo = new DocumentoVice();
				documentoNuevo.setEsArchivo("N");
				esArchivoDocumentoNuevo = false;
				cargarArchivos();
			}
		} else {
			try {
				documentoNuevo.setEstado("A");
				servicioGeneral.guardarObjeto(documentoNuevo);
				documentoNuevo = new DocumentoVice();
				documentoNuevo.setEsArchivo("Y");
				esArchivoDocumentoNuevo = false;
				cargarArchivos();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void cambiarVisible() {
		if (esArchivoDocumentoNuevo)
			documentoNuevo.setEsArchivo("Y");
		else
			documentoNuevo.setEsArchivo("N");
	}

	public void setEsArchivoDocumentoNuevo(boolean esArchivoDocumentoNuevo) {
		this.esArchivoDocumentoNuevo = esArchivoDocumentoNuevo;
	}

	public boolean isEsArchivoDocumentoNuevo() {
		return esArchivoDocumentoNuevo;
	}

	public String remove(String input) {
		// Cadena de caracteres original a sustituir.
		String original = "·‡‰ÈËÎÌÏÔÛÚˆ˙˘uÒ¡¿ƒ…»ÀÕÃœ”“÷⁄Ÿ‹—Á« ";
		// Cadena de caracteres ASCII que reemplazar·n los originales.
		String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC_";
		String output = input;
		for (int i = 0; i < original.length(); i++) {
			// Reemplazamos los caracteres especiales.
			output = output.replace(original.charAt(i), ascii.charAt(i));
		}// for i
		return output;
	}
	
	public void inactivarItem(){
		documentoSeleccionado.setEstado("I");
		servicioGeneral.guardarObjeto(documentoSeleccionado);
		cargarArchivos();
	}

}
