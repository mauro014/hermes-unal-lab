package co.edu.unal.hermes.vista.laboratorios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.laboratorios.ArchivoLaboratorio;

/**
 * @author dgbenitezc
 */
public class ManejadorLaboratoriosGestion extends ManejadorLaboratorios {

	private SelectItem[] acreditacionItem;
	private SelectItem[] certificacionItem;
	private SelectItem[] habilitacionItem;
	private SelectItem[] registroIcaItem;
	private SelectItem[] licenciasItem;
	private SelectItem[] siTiposDocumentosGestion;
	private Tipos tipoArchivoSeleccionado;
	private List<ArchivoLaboratorio> listaArchivosGestion;
	private List<ArchivoLaboratorio> listaArchivosGestionEliminados;
	
	private SelectItem[] requiereNormaItem;
	private SelectItem[] interesadoNormaItem;

	private ArchivoLaboratorio archivoLaboratorioSeleccionado;

	public ManejadorLaboratoriosGestion() {
		System.out.println("CONSTRUCTOR ManejadorLaboratoriosGestion IN:");

		idManejador = GESTION;

		acreditacionItem = servicioGeneral
				.retornaSelectItemArregloDeHijosDeTipos(Tipos.TIPOS_GESTION_LABORATORIO);
		certificacionItem = servicioGeneral
				.retornaSelectItemArregloDeHijosDeTipos(Tipos.TIPOS_GESTION_LABORATORIO);
		habilitacionItem = servicioGeneral
				.retornaSelectItemArregloDeHijosDeTipos(Tipos.TIPOS_GESTION_LABORATORIO);
		registroIcaItem = servicioGeneral
				.retornaSelectItemArregloDeHijosDeTipos(Tipos.TIPOS_GESTION_LABORATORIO);
		licenciasItem = servicioGeneral
				.retornaSelectItemArregloDeHijosDeTipos(Tipos.TIPOS_GESTION_LABORATORIO);
		siTiposDocumentosGestion = servicioGeneral
				.selectItemHijosDeTiposValorObjeto(Tipos.TIPOS_DOCUMENTOS_GESTION_LABORATORIOS);
		tipoArchivoSeleccionado = (Tipos) siTiposDocumentosGestion[0]
				.getValue();
		
		requiereNormaItem = servicioGeneral
				.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPOS_LAB_GESTION_REQUIERE);
		interesadoNormaItem = servicioGeneral
				.retornaSelectItemArregloDeHijosDeTiposOrdenABC(Tipos.TIPOS_LAB_GESTION_INTERESADO);

		// carga listaArchivos
		String hql2 = "FROM ArchivoLaboratorio AL WHERE AL.idLab = '"
				+ laboratorioActual.getId()
				+ "' AND AL.tipoArchivo in (SELECT t.id FROM Tipos t WHERE t.padre = '"
				+ Tipos.TIPOS_DOCUMENTOS_GESTION_LABORATORIOS
				+ "') ORDER BY AL.id DESC";
		System.out.println("hql2:" + hql2);
		listaArchivosGestion = servicioGeneral.obtenerObjetos(
				ArchivoLaboratorio.class, hql2);

		listaArchivosGestionEliminados = new ArrayList<ArchivoLaboratorio>();

		System.out.println("CONSTRUCTOR ManejadorLaboratoriosGestion OUT.");
	}

	public void descargarArchivo() {
		System.out.println("descargarArchivoGestion IN:");
		String idArchivo = archivoLaboratorioSeleccionado.getId().toString();
		String nombreArchivo = archivoLaboratorioSeleccionado
				.getNombreArchivo();
		System.out.println("idArchivo: " + idArchivo);
		System.out.println("nombreArchivo: " + nombreArchivo);
		descargarArchivoGenerico(
				ArchivoLaboratorio.TABLA_ARCHIVOS_LABORATORIOS, idArchivo,
				nombreArchivo);
		System.out.println("descargarArchivoGestion OUT.");
	}

	public void subirArchivo(FileUploadEvent event) {
		UploadedFile archivoSubir = event.getFile();
		System.out.println("subirArchivo:" + archivoSubir.getFileName());
		Long id = null;
		if (archivoSubir != null) {

			ArchivoLaboratorio archivoNuevo = new ArchivoLaboratorio();
			archivoNuevo.setNombreArchivo(archivoSubir.getFileName());
			archivoNuevo.setFechaArchivo(new Date());
			archivoNuevo.setIdLab(laboratorioActual.getId());

			System.out.println("subirArchivo tipoArchivoSeleccionadoid:"
					+ tipoArchivoSeleccionado.getId());
			System.out.println("subirArchivo tipoArchivoSeleccionadonombre:"
					+ tipoArchivoSeleccionado.getNombre());
			tipoArchivoSeleccionado = (Tipos) servicioGeneral.obtenerObjeto(
					new Tipos(), tipoArchivoSeleccionado.getId());
			System.out.println("subirArchivo tipoArchivoSeleccionadoid:"
					+ tipoArchivoSeleccionado.getId());
			System.out.println("subirArchivo tipoArchivoSeleccionadonombre:"
					+ tipoArchivoSeleccionado.getNombre());
			archivoNuevo.setTipoArchivo(tipoArchivoSeleccionado);

			System.out.println("archivoNuevo.getId():" + archivoNuevo.getId());

			// obtener secuencia:
			Long seq = servicioGeneral.consecutivoSecuencia("SEQ_ARCHIVO");
			System.out.println("consecutivoSecuencia:" + seq);

			servicioGeneral.insertarObjetoConIdLong(archivoNuevo, seq);

			id = archivoNuevo.getId();
			System.out.println("archivoNuevo.getId():" + id);

			if (id != null) {
				Boolean exitoSubir = cargarArchivoDisco(archivoSubir,
						ArchivoLaboratorio.TABLA_ARCHIVOS_LABORATORIOS,
						id.toString());
				if (exitoSubir) {
					listaArchivosGestion.add(archivoNuevo);
					System.out.println("Nuevo archivo creado.");
				} else {
					mensajeError("Error al subir archivo.");
				}
			}

		}
	}

	public void eliminarArchivo() {
		System.out.println("eliminarArchivoLaboratorioGestion IN:");
		listaArchivosGestion.remove(archivoLaboratorioSeleccionado);
		listaArchivosGestionEliminados.add(archivoLaboratorioSeleccionado);
		System.out.println("eliminarArchivoLaboratorioGestion OUT.");
	}

	public void seleccionarTipos() {
		System.out.println("ManejadorLaboratoriosGestion seleccionarTipos:");
	}

	@Override
	public String salirGuardar() {
		if (validar()) {
			guardar();
			return (salir());
		} else {
			return null;
		}
	}

	public void guardar() {

		for (ArchivoLaboratorio ale : listaArchivosGestionEliminados) {
			Boolean archivoBorrado = eliminarArchivoLaboratorio(ale.getId());
			System.out
					.println("eliminarArchivoLaboratorioGestion archivoBorrado: "
							+ archivoBorrado);
			try {
				servicioGeneral.eliminarObjeto(ale);
			} catch (Exception e) {
				
			}
		}

		guardarLaboratorioActual(idManejador);
		
		calcularCompletitud();
	}

	@Override
	public String siguiente() {
		if (validar()) {
			if (idManejador == laboratorioActual.getEtapaRegistro().intValue()) {
				laboratorioActual.setEtapaRegistro(new Integer(
						(laboratorioActual.getEtapaRegistro()).intValue() + 1));
			}
			guardar();
			limpiarSesion();
			sesion.setAttribute("Laboratorio", laboratorioActual);
			sesion.setAttribute("solicitudLaboratorio", solicitudLab);
			sesion.removeAttribute("ManejadorMenuFormularioLaboratorios");
			return "laboratorioEquipos";
		} else {
			return null;
		}
	}

	public Boolean validar() {
		boolean validar = true;
		listaMensajesValidacion = new ArrayList<String>();
//		laboratorioActual.trim();
		return validar;
	}

	/**
	 * @return the acreditacionItem
	 */
	public SelectItem[] getAcreditacionItem() {
		return acreditacionItem;
	}

	/**
	 * @return the certificacionItem
	 */
	public SelectItem[] getCertificacionItem() {
		return certificacionItem;
	}

	/**
	 * @return the habilitacionItem
	 */
	public SelectItem[] getHabilitacionItem() {
		return habilitacionItem;
	}

	/**
	 * @return the registroIcaItem
	 */
	public SelectItem[] getRegistroIcaItem() {
		return registroIcaItem;
	}

	/**
	 * @return the licenciasItem
	 */
	public SelectItem[] getLicenciasItem() {
		return licenciasItem;
	}

	/**
	 * @return the siTiposDocumentosGestion
	 */
	public SelectItem[] getSiTiposDocumentosGestion() {
		return siTiposDocumentosGestion;
	}

	/**
	 * @return the tipoArchivoSeleccionado
	 */
	public Tipos getTipoArchivoSeleccionado() {
		return tipoArchivoSeleccionado;
	}

	/**
	 * @param tipoArchivoSeleccionado
	 *            the tipoArchivoSeleccionado to set
	 */
	public void setTipoArchivoSeleccionado(Tipos tipoArchivoSeleccionado) {
		this.tipoArchivoSeleccionado = tipoArchivoSeleccionado;
	}

	/**
	 * @return the listaArchivosGestion
	 */
	public List<ArchivoLaboratorio> getListaArchivosGestion() {
		return listaArchivosGestion;
	}

	/**
	 * @return the archivoLaboratorioSeleccionado
	 */
	public ArchivoLaboratorio getArchivoLaboratorioSeleccionado() {
		return archivoLaboratorioSeleccionado;
	}

	/**
	 * @param archivoLaboratorioSeleccionado
	 *            the archivoLaboratorioSeleccionado to set
	 */
	public void setArchivoLaboratorioSeleccionado(
			ArchivoLaboratorio archivoLaboratorioSeleccionado) {
		this.archivoLaboratorioSeleccionado = archivoLaboratorioSeleccionado;
	}

	public SelectItem[] getRequiereNormaItem() {
		return requiereNormaItem;
	}

	public void setRequiereNormaItem(SelectItem[] requiereNormaItem) {
		this.requiereNormaItem = requiereNormaItem;
	}

	public SelectItem[] getInteresadoNormaItem() {
		return interesadoNormaItem;
	}

	public void setInteresadoNormaItem(SelectItem[] interesadoNormaItem) {
		this.interesadoNormaItem = interesadoNormaItem;
	}
	
	

}
