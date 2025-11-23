package co.edu.unal.hermes.vista.convocatoriaPublicacionLibros;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.component.html.HtmlDataTable;
import javax.faces.model.SelectItem;

import org.apache.myfaces.custom.fileupload.UploadedFile;

import co.edu.unal.hermes.modelo.ArchivoConvocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaPublicacionLibros;
import co.edu.unal.hermes.modelo.ConvocatoriaPublicacionLibrosDetalle;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 ** @author dgbenitezc
 */

public class ManejadorCrearConvocatoriaPublicacionLibros extends ManejadorBase {

	private List<ArchivoConvocatoria> listaArchivosObligatoriosSel;
	private HtmlDataTable tablaArchivosObligatoriosSel;
	private UploadedFile archivoObligatorio;
	private ConvocatoriaPublicacionLibros convocatoriaPublicacionLibros;
	boolean guardado;
	private List<DominioDetalle> listaTipoArchivosObligatorios;
	private SelectItem[] tipoArchivosItem;
	private SelectItem[] tipoDocumentoItem;
	private String tipoArchivoSel;
	private String tipoDocumentoSel;
	private List<String> listaMensajesValidacion;
	private ConvocatoriaPublicacionLibrosDetalle convocatoriaPublicacionLibrosDetalle;
	private List<ConvocatoriaPublicacionLibrosDetalle> listaAutores;
	private HtmlDataTable tablaAutores;

	public ManejadorCrearConvocatoriaPublicacionLibros() {
		listaMensajesValidacion = new ArrayList<String>();
		try {
			InvestigadorInterno investigadorInterno = (InvestigadorInterno) sesion
					.getAttribute("persona");
			if (investigadorInterno.getTipoDedicacion() != null
					&& (investigadorInterno.getTipoDedicacion().getId()
							.equals(Investigador.EXCLUSIVA) || investigadorInterno
							.getTipoDedicacion().getId()
							.equals(Investigador.TIEMPOCOMPLETO))) {
				guardado = false;
				
			} else {
				guardado = true;
				listaMensajesValidacion
				.add("El investigador debe ser de dedicación exclusiva o tiempo completo de la Universidad Nacional de Colombia.");
			}
		} catch (Exception ex) { // java.lang.ClassCastException: co.edu.unal.hermes.modelo.InvestigadorExterno cannot be cast to co.edu.unal.hermes.modelo.InvestigadorInterno
			guardado = true;
			listaMensajesValidacion
			.add("El investigador debe ser de dedicación exclusiva o tiempo completo de la Universidad Nacional de Colombia.");
			System.out.println(ex.toString());
		}
		convocatoriaPublicacionLibros = new ConvocatoriaPublicacionLibros();
		convocatoriaPublicacionLibrosDetalle = new ConvocatoriaPublicacionLibrosDetalle();
		listaTipoArchivosObligatorios = new ArrayList<DominioDetalle>();
		listaArchivosObligatoriosSel = new ArrayList<ArchivoConvocatoria>();
		listaAutores = new ArrayList<ConvocatoriaPublicacionLibrosDetalle>();
		cargarTipoArchivos();
		cargarTiposDocumento();
	}

	public void guardarArchivoObligatorio() {
		try {
			if (archivoObligatorio.getBytes() != null) {
				int i = archivoObligatorio.getName().lastIndexOf("\\");
				ArchivoConvocatoria archivo = new ArchivoConvocatoria();
				archivo.setBytes(archivoObligatorio.getBytes());
				archivo.setNombre(archivoObligatorio.getName().substring(i + 1));
				archivo.setFecha(new Date());
				archivo.setTipoArchivo(tipoArchivoSel);
				listaArchivosObligatoriosSel.add(archivo);
				validar();
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}

	public void agregarAutor() {
		try {
			validar();
			if (convocatoriaPublicacionLibrosDetalle.validar()) {
				convocatoriaPublicacionLibrosDetalle
				.setFechaRegistro(new Date());
				listaAutores.add(convocatoriaPublicacionLibrosDetalle);
				convocatoriaPublicacionLibrosDetalle = new ConvocatoriaPublicacionLibrosDetalle();
			} else {
				listaMensajesValidacion
				.add("Todos los datos del Autor a Agregar son Obligatorios.");
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}

	public void cargarTipoArchivos() {
		listaTipoArchivosObligatorios = servicioGeneral
				.obtenerObjetos("FROM DominioDetalle WHERE identificador.id = '20'");
		if (listaTipoArchivosObligatorios != null
				&& listaTipoArchivosObligatorios.size() > 0) {
			tipoArchivosItem = new SelectItem[listaTipoArchivosObligatorios
			                                  .size()];
			for (int i = 0; i < listaTipoArchivosObligatorios.size(); i++) {
				DominioDetalle ta = listaTipoArchivosObligatorios.get(i);
				tipoArchivosItem[i] = new SelectItem(ta.getIdentificador()
						.getTipo(), ta.getIdentificador().getTipo() + " - "
								+ ta.getDescripcion());
			}
		}
	}

	public void guardar() {
		if (validar()) {
			Investigador investigador = (Investigador) sesion
					.getAttribute("persona");
			convocatoriaPublicacionLibros.setInvestigador(investigador);
			convocatoriaPublicacionLibros.setFechaRegistro(new Date());
			servicioGeneral.guardarObjeto(convocatoriaPublicacionLibros);

			// Guardar detalles:
			for (int i = 0; i < listaAutores.size(); i++) {
				ConvocatoriaPublicacionLibrosDetalle detalle = new ConvocatoriaPublicacionLibrosDetalle();
				detalle = (ConvocatoriaPublicacionLibrosDetalle) listaAutores
						.get(i);
				detalle.setConvocatoriaPublicacionLibros(convocatoriaPublicacionLibros
						.getId());
				servicioGeneral.guardarObjeto(detalle);
			}

			// Guardar archivos:
			for (int i = 0; i < listaArchivosObligatoriosSel.size(); i++) {
				ArchivoConvocatoria archivo = new ArchivoConvocatoria();
				archivo = (ArchivoConvocatoria) listaArchivosObligatoriosSel
						.get(i);
				archivo.setConvocatoria(String
						.valueOf(convocatoriaPublicacionLibros.getId()));
				servicioGeneral.guardarObjeto(archivo);
			}
			guardado = true;

			CorreoPlantilla correoPlantilla = cargarPlantilla(131);
			String cuerpo = correoPlantilla.getCuerpo();
			String asunto = correoPlantilla.getAsunto();
			Correo correo = new Correo();
			correo.setOrigen(Correo.CORREO_HERMES);
			String emailInvestigador = investigador.getEmail();
			correo.adicionarDireccion(emailInvestigador);
			//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
			correo.setAsunto(asunto);

			String nombreInvestigador = investigador.getNombre1() + " "
					+ investigador.getNombre2() + " "
					+ investigador.getApellido1() + " "
					+ investigador.getApellido2();
			String cuerpoCorreo = cuerpo.replaceAll("<<INVESTIGADOR>>",
					nombreInvestigador);
			String idSolicitud = convocatoriaPublicacionLibros.getId()
					.toString();
			cuerpoCorreo = cuerpoCorreo.replaceAll("<<ID>>", idSolicitud);

			correo.setCuerpo(cuerpoCorreo);

			if (servicioCorreo.enviarCorreo(correo)) {
				listaMensajesValidacion
				.add("El número asignado para la solicitud es: "
						+ idSolicitud);
				listaMensajesValidacion.add("Se envió notificación al email: "
						+ emailInvestigador);
			}

			convocatoriaPublicacionLibros = new ConvocatoriaPublicacionLibros();
			listaArchivosObligatoriosSel = new ArrayList<ArchivoConvocatoria>();
			listaAutores = new ArrayList<ConvocatoriaPublicacionLibrosDetalle>();
		}
	}

	public CorreoPlantilla cargarPlantilla(int cod_id) {
		CorreoPlantilla correoActualAux = new CorreoPlantilla();
		List<CorreoPlantilla> lista = servicioGeneral
				.obtenerObjetos("FROM CorreoPlantilla WHERE id = '" + cod_id
						+ "'");
		if (lista != null && lista.size() > 0) {
			correoActualAux = (CorreoPlantilla) lista.get(0);
		}
		return correoActualAux;
	}

	private boolean validar() {
		listaMensajesValidacion = new ArrayList<String>();
		boolean validar = true;
		convocatoriaPublicacionLibros
		.setNombreLibro(convocatoriaPublicacionLibros.getNombreLibro()
				.trim());
		if (convocatoriaPublicacionLibros.getNombreLibro().equals("")) {
			listaMensajesValidacion
			.add("El título del libro no puede ser nulo.");
			validar = false;
		}

		if (listaAutores.size() < 1) {
			listaMensajesValidacion
			.add("Debe existir al menos un autor registrado.");
			validar = false;
		}

		int cantidadArchivosAdjuntos = listaArchivosObligatoriosSel.size();
		int cantidadTiposArchivoObligatorios = listaTipoArchivosObligatorios
				.size();
		int[] arregloCantidadTiposArchivoObligatorios = new int[cantidadTiposArchivoObligatorios];

		if (cantidadArchivosAdjuntos >= cantidadTiposArchivoObligatorios) {
			for (int j = 0; j < cantidadTiposArchivoObligatorios; j++) {
				DominioDetalle ta = listaTipoArchivosObligatorios.get(j);
				arregloCantidadTiposArchivoObligatorios[j] = 0;
				for (int i = 0; i < cantidadArchivosAdjuntos; i++) {
					ArchivoConvocatoria archivo = new ArchivoConvocatoria();
					archivo = (ArchivoConvocatoria) listaArchivosObligatoriosSel
							.get(i);

					if (ta.getIdentificador().getTipo()
							.equals(archivo.getTipoArchivo())) {
						arregloCantidadTiposArchivoObligatorios[j]++;
					}
				}
				if (arregloCantidadTiposArchivoObligatorios[j] == 0) {
					listaMensajesValidacion
					.add("Debe existir al menos un archivo adjunto de cada tipo.");
					validar = false;
				}
			}
		} else {
			listaMensajesValidacion
			.add("Debe existir al menos un archivo adjunto de cada tipo.");
			validar = false;
		}

		if (validar) {
			listaMensajesValidacion.add("Validación Exitosa.");
		}
		return validar;
	}

	public void eliminarArchivoObligatorio() {
		listaArchivosObligatoriosSel.remove(tablaArchivosObligatoriosSel
				.getRowIndex());
	}

	public void eliminarAutor() {
		listaAutores.remove(tablaAutores.getRowIndex());
	}

	public String salir() {
		sesion.removeAttribute("ManejadorCrearConvocatoriaPublicacionLibros");
		return "misProyectos";
	}

	public void buscarAutor() {
		convocatoriaPublicacionLibrosDetalle.validar();
		String identificacion = convocatoriaPublicacionLibrosDetalle.getIdentificacion();
		if (!identificacion.equals("")) {
			IdPersona idPersona = new IdPersona(identificacion, convocatoriaPublicacionLibrosDetalle.getTipoDocumento());
			Persona persona = new Persona();
			persona = servicioPersona.obtenerPersona(idPersona);
			if (persona != null) {
				convocatoriaPublicacionLibrosDetalle.setAutor(persona.getNombreCompleto());
				convocatoriaPublicacionLibrosDetalle.setEmail(persona.getEmail());
				convocatoriaPublicacionLibrosDetalle.setDireccion(persona.getDireccion());
				convocatoriaPublicacionLibrosDetalle.setTelefono(persona.getTelefono());
				convocatoriaPublicacionLibrosDetalle.setUniversidad("Universidad Nacional de Colombia");
				InvestigadorInterno investigadorInterno = new InvestigadorInterno();
				investigadorInterno = servicioPersona.obtenerInvestigadorInterno(idPersona);
				if (investigadorInterno != null) {
					if (investigadorInterno.getDependencia().getFacultad() != null) {
						convocatoriaPublicacionLibrosDetalle.setFacultadDependencia(investigadorInterno.getDependencia().getFacultad().getNombre() + " / "+ investigadorInterno.getDependencia().getNombre());
					} else {
						convocatoriaPublicacionLibrosDetalle.setFacultadDependencia(investigadorInterno.getDependencia().getNombre());
					}
				}
			}
		}
	}

	private void cargarTiposDocumento() {
		List listaTipoDocumento = servicioGeneral
				.obtenerListaObjetos("TipoDocumento");
		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			TipoDocumento td = (TipoDocumento) listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getId() + " - " + td.getNombre());
		}
	}


	public void limpiarAutor() {
		convocatoriaPublicacionLibrosDetalle = new ConvocatoriaPublicacionLibrosDetalle();
	}

	/**
	 * @return the convocatoriaPublicacionLibros
	 */
	public ConvocatoriaPublicacionLibros getConvocatoriaPublicacionLibros() {
		return convocatoriaPublicacionLibros;
	}

	/**
	 * @param convocatoriaPublicacionLibros
	 *            the convocatoriaPublicacionLibros to set
	 */
	public void setConvocatoriaPublicacionLibros(
			ConvocatoriaPublicacionLibros convocatoriaPublicacionLibros) {
		this.convocatoriaPublicacionLibros = convocatoriaPublicacionLibros;
	}

	/**
	 * @return the guardado
	 */
	public boolean isGuardado() {
		return guardado;
	}

	/**
	 * @param guardado
	 *            the guardado to set
	 */
	public void setGuardado(boolean guardado) {
		this.guardado = guardado;
	}

	/**
	 * @return the tablaArchivosObligatoriosSel
	 */
	public HtmlDataTable getTablaArchivosObligatoriosSel() {
		return tablaArchivosObligatoriosSel;
	}

	/**
	 * @param tablaArchivosObligatoriosSel
	 *            the tablaArchivosObligatoriosSel to set
	 */
	public void setTablaArchivosObligatoriosSel(
			HtmlDataTable tablaArchivosObligatoriosSel) {
		this.tablaArchivosObligatoriosSel = tablaArchivosObligatoriosSel;
	}

	/**
	 * @return the archivoObligatorio
	 */
	public UploadedFile getArchivoObligatorio() {
		return archivoObligatorio;
	}

	/**
	 * @param archivoObligatorio
	 *            the archivoObligatorio to set
	 */
	public void setArchivoObligatorio(UploadedFile archivoObligatorio) {
		this.archivoObligatorio = archivoObligatorio;
	}

	/**
	 * @return the listaTipoArchivosObligatorios
	 */
	public List<DominioDetalle> getListaTipoArchivosObligatorios() {
		return listaTipoArchivosObligatorios;
	}

	/**
	 * @param listaTipoArchivosObligatorios
	 *            the listaTipoArchivosObligatorios to set
	 */
	public void setListaTipoArchivosObligatorios(
			List<DominioDetalle> listaTipoArchivosObligatorios) {
		this.listaTipoArchivosObligatorios = listaTipoArchivosObligatorios;
	}

	/**
	 * @return the tipoArchivosItem
	 */
	public SelectItem[] getTipoArchivosItem() {
		return tipoArchivosItem;
	}

	/**
	 * @param tipoArchivosItem
	 *            the tipoArchivosItem to set
	 */
	public void setTipoArchivosItem(SelectItem[] tipoArchivosItem) {
		this.tipoArchivosItem = tipoArchivosItem;
	}

	/**
	 * @return the tipoArchivoSel
	 */
	public String getTipoArchivoSel() {
		return tipoArchivoSel;
	}

	/**
	 * @param tipoArchivoSel
	 *            the tipoArchivoSel to set
	 */
	public void setTipoArchivoSel(String tipoArchivoSel) {
		this.tipoArchivoSel = tipoArchivoSel;
	}

	/**
	 * @return the listaArchivosObligatoriosSel
	 */
	public List<ArchivoConvocatoria> getListaArchivosObligatoriosSel() {
		return listaArchivosObligatoriosSel;
	}

	/**
	 * @param listaArchivosObligatoriosSel
	 *            the listaArchivosObligatoriosSel to set
	 */
	public void setListaArchivosObligatoriosSel(
			List<ArchivoConvocatoria> listaArchivosObligatoriosSel) {
		this.listaArchivosObligatoriosSel = listaArchivosObligatoriosSel;
	}

	/**
	 * @return the listaMensajesValidacion
	 */
	public List<String> getListaMensajesValidacion() {
		return listaMensajesValidacion;
	}

	/**
	 * @param listaMensajesValidacion
	 *            the listaMensajesValidacion to set
	 */
	public void setListaMensajesValidacion(List<String> listaMensajesValidacion) {
		this.listaMensajesValidacion = listaMensajesValidacion;
	}

	/**
	 * @return the convocatoriaPublicacionLibrosDetalle
	 */
	public ConvocatoriaPublicacionLibrosDetalle getConvocatoriaPublicacionLibrosDetalle() {
		return convocatoriaPublicacionLibrosDetalle;
	}

	/**
	 * @param convocatoriaPublicacionLibrosDetalle
	 *            the convocatoriaPublicacionLibrosDetalle to set
	 */
	public void setConvocatoriaPublicacionLibrosDetalle(
			ConvocatoriaPublicacionLibrosDetalle convocatoriaPublicacionLibrosDetalle) {
		this.convocatoriaPublicacionLibrosDetalle = convocatoriaPublicacionLibrosDetalle;
	}

	/**
	 * @return the listaAutores
	 */
	public List<ConvocatoriaPublicacionLibrosDetalle> getListaAutores() {
		return listaAutores;
	}

	/**
	 * @param listaAutores
	 *            the listaAutores to set
	 */
	public void setListaAutores(
			List<ConvocatoriaPublicacionLibrosDetalle> listaAutores) {
		this.listaAutores = listaAutores;
	}

	/**
	 * @return the tablaAutores
	 */
	public HtmlDataTable getTablaAutores() {
		return tablaAutores;
	}

	/**
	 * @param tablaAutores
	 *            the tablaAutores to set
	 */
	public void setTablaAutores(HtmlDataTable tablaAutores) {
		this.tablaAutores = tablaAutores;
	}

	/**
	 * @return the tipoDocumentoItem
	 */
	public SelectItem[] getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}

	/**
	 * @param tipoDocumentoItem
	 *            the tipoDocumentoItem to set
	 */
	public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
		this.tipoDocumentoItem = tipoDocumentoItem;
	}

	/**
	 * @return the tipoDocumentoSel
	 */
	public String getTipoDocumentoSel() {
		return tipoDocumentoSel;
	}

	/**
	 * @param tipoDocumentoSel
	 *            the tipoDocumentoSel to set
	 */
	public void setTipoDocumentoSel(String tipoDocumentoSel) {
		this.tipoDocumentoSel = tipoDocumentoSel;
	}

}
