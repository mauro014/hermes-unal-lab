package co.edu.unal.hermes.vista.requerimiento;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.ArchivoRequerimiento;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Requerimiento;
import co.edu.unal.hermes.modelo.TipoArchivo;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorCrearRequerimiento extends ManejadorBase implements Serializable {

	Requerimiento requerimiento;
	ArchivoRequerimiento archivoSeleccionado;

	ArchivoRequerimiento archivoRequerimiento;
	ArchivoRequerimiento archivo;
	List<Dependencia> dependenciasUN;
	List<TipoDocumento> listaTipoDocumento;

	List<DominioDetalle> listaEstReq;
	DominioDetalle estadosReq;
	String estadoSelReq;

	List<DominioDetalle> listaTipoArcReq;
	DominioDetalle tipoArcReq;
	String tipoArcSelReq;

	private UploadedFile archivoCargarUno;
	List<ArchivoRequerimiento> listaDocumentos;
	private String errorValidacion;
	private String mensajeAdjuntaDocumentos = "";

	Dependencia dependenciaReq;

	public SelectItem[] dependenciaItem;
	public SelectItem[] tipoDocumentoItem;
	public SelectItem[] estadosItem;
	public SelectItem[] tipoArcItem;

	public SelectItem[] moduloItems;
	public SelectItem[] subModuloItems;
	private String dependenciaAsociada = "HERMES";

	private UploadedFile archivoLeg;
	private TipoArchivo tipoArchivo;
	private List listaTipoArchivo;
	private SelectItem[] tipoArchivoItem;
	private DataTable tablaArchivosLeg;
	private ArchivoRequerimiento archivoSeleccionadoLeg = new ArchivoRequerimiento();

	public ManejadorCrearRequerimiento() {

		sesion.removeAttribute("ManejadorCrearRequerimiento");
		sesion.removeAttribute("ManejadorConsultarRequerimiento");

		cargarListas();

		requerimiento = new Requerimiento();
		archivoRequerimiento = new ArchivoRequerimiento();

		requerimiento.tipoDoc = new TipoDocumento();
		requerimiento.idDependencia = new Dependencia();
		archivoCargarUno = new UploadedFile() {

			public long getSize() {

				return 0;
			}

			public InputStream getInputstream() throws IOException {

				return null;
			}

			public String getFileName() {

				return null;
			}

			public byte[] getContents() {

				return null;
			}

			public String getContentType() {

				return null;
			}
		};

		listaDocumentos = new ArrayList<ArchivoRequerimiento>();
		estadosReq = new DominioDetalle();
		tipoArcReq = new DominioDetalle();

	}

	public void cargarListas() {

		// Tipos Documento

		listaTipoDocumento = new ArrayList<TipoDocumento>();
		listaTipoDocumento = servicioGeneral.obtenerListaObjetos(TipoDocumento.class);

		TipoDocumento tdAux = listaTipoDocumento.get(0);

		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];

		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			TipoDocumento td = (TipoDocumento) listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
			td = null;
		}

		// Dependencias

		dependenciasUN = new ArrayList<Dependencia>();
		dependenciasUN = servicioGeneral.obtenerListaObjetos(Dependencia.class);

		Dependencia dAux = dependenciasUN.get(0);

		dependenciaItem = new SelectItem[dependenciasUN.size()];

		for (int i = 0; i < dependenciasUN.size(); i++) {
			Dependencia dd = (Dependencia) dependenciasUN.get(i);
			dependenciaItem[i] = new SelectItem(dd.getId(), dd.getNombre());
			dd = null;
		}

		// Estados Requerimiento

		listaEstReq = new ArrayList<DominioDetalle>();
		listaEstReq = servicioGeneral.obtenerListaObjetos("DominioDetalle where identificador.id = '102'");

		DominioDetalle esAux = listaEstReq.get(0);

		estadosItem = new SelectItem[listaEstReq.size()];

		for (int i = 0; i < listaEstReq.size(); i++) {
			DominioDetalle es = (DominioDetalle) listaEstReq.get(i);
			estadosItem[i] = new SelectItem(es.getDescripcion(), es.getDescripcion());
			es = null;
		}

		estadoSelReq = estadosReq != null ? estadosReq.getDescripcion() : "R";

		// Tipos de Archivo Requerimiento

		/*
		 * listaTipoArcReq = new ArrayList<DominioDetalle>(); listaTipoArcReq =
		 * servicioGeneral
		 * .obtenerListaObjetos("DominioDetalle where identificador.id = '104'"
		 * );
		 * 
		 * DominioDetalle taAux = listaTipoArcReq.get(0);
		 * 
		 * if(listaTipoArcReq.size()>0){ tipoArcItem = new
		 * SelectItem[listaTipoArcReq.size()];
		 * 
		 * for (int i = 0; i < listaTipoArcReq.size(); i++) { DominioDetalle ta
		 * = (DominioDetalle) listaTipoArcReq.get(i); tipoArcItem[i] = new
		 * SelectItem(ta.getDescripcion(), ta.getDescripcion()); ta = null; }
		 * 
		 * tipoArcSelReq = (String)tipoArcItem[0].getValue(); tipoArchivo =
		 * (TipoArchivo) listaTipoArcReq.get(0); }
		 */

		listaTipoArchivo = servicioGeneral.obtenerListaObjetos("TipoArchivo e where e.id in (52)");
		tipoArchivoItem = new SelectItem[listaTipoArchivo.size()];
		for (int i = 0; i < listaTipoArchivo.size(); i++) {
			TipoArchivo ta = (TipoArchivo) listaTipoArchivo.get(i);
			tipoArchivoItem[i] = new SelectItem(ta.getId(), ta.getNombre());
		}
		tipoArchivo = (TipoArchivo) listaTipoArchivo.get(0);

		// Módulos
		List listaMod = new ArrayList();
		listaMod = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				"from DominioDetalle where identificador.id = '113' and estado='0' order by observacion");

		DominioDetalle aux = (DominioDetalle) listaMod.get(0);

		if (listaMod.size() > 0) {
			moduloItems = new SelectItem[listaMod.size()];

			for (int i = 0; i < listaMod.size(); i++) {
				DominioDetalle ta = (DominioDetalle) listaMod.get(i);
				moduloItems[i] = new SelectItem(ta.getIdentificador().getTipo(), ta.getDescripcion());
				ta = null;
			}

			// this.requerimiento.setModulo((String)moduloItems[0].getValue());
		}

		// Sub-Módulos
		List listasMod = new ArrayList();
		if (this.requerimiento == null) {
			this.requerimiento = new Requerimiento();
			if (this.requerimiento.getModulo() == null)
				this.requerimiento.setModulo("113_30");
		}

		listasMod = servicioGeneral.obtenerListaObjetos(
				"DominioDetalle where identificador.id = '113' and estado='" + this.requerimiento.getModulo() + "'");

		aux = (DominioDetalle) listasMod.get(0);

		if (listasMod.size() > 0) {
			subModuloItems = new SelectItem[listasMod.size()];

			for (int i = 0; i < listasMod.size(); i++) {
				DominioDetalle ta = (DominioDetalle) listasMod.get(i);
				subModuloItems[i] = new SelectItem(ta.getIdentificador().getTipo(), ta.getDescripcion());
				ta = null;
			}

			// this.requerimiento.setModulo((String)moduloItems[0].getValue());
		}

	}

	public void cargarSubModulo() {

		List listaMod = new ArrayList();
		listaMod = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				"from DominioDetalle where identificador.id = '113' and estado='" + this.requerimiento.getModulo()
						+ "' order by observacion ");

		DominioDetalle aux = (DominioDetalle) listaMod.get(0);

		if (listaMod.size() > 0) {
			subModuloItems = new SelectItem[listaMod.size()];

			for (int i = 0; i < listaMod.size(); i++) {
				DominioDetalle ta = (DominioDetalle) listaMod.get(i);
				subModuloItems[i] = new SelectItem(ta.getIdentificador().getTipo(), ta.getDescripcion());
				ta = null;
			}

			// this.requerimiento.setModulo((String)moduloItems[0].getValue());
		}
	}

	public void descargarArchivoReq() {
		Long id = archivoSeleccionadoLeg.getId();
		// Long id = ((ArchivoResumen) (tablaArchivos.getRowData())).getId();
		descargarArchivoRequerimientoGenerico(id, requerimiento.getId());
	}

	//
	public String verSolicAsignadas() {
		sesion.removeAttribute("ManejadorConsultarRequerimiento");

		return "consultarTodosReqAsignados";

	}

	public String verInconvAsignados() {
		sesion.removeAttribute("ManejadorConsultarRequerimiento");

		return "consultarInconvAsignados";

	}

	public String verSolucionados() {
		sesion.removeAttribute("ManejadorConsultarRequerimiento");
		return "consultarSolucionados";

	}

	public String verInconvSolucionados() {
		sesion.removeAttribute("ManejadorConsultarRequerimiento");
		return "consultarInconvSolucionados";

	}

	// ****************//
	public void actualizarTipoArchivo() {
		System.out.println(tipoArcSelReq);
	}

	// subir archivos lmom
	public String insertarArchivoReq() {

		TipoArchivo tipoAr = new TipoArchivo();
		List listaAr = servicioGeneral.obtenerObjetoXID("TipoArchivo", tipoArchivo.getId() + "");
		tipoAr = (TipoArchivo) listaAr.get(0);

		if (requerimiento.getId() != null) {
			if (archivoLeg != null) {
				return insertarArchivoRequerimiento(archivoLeg, requerimiento, listaDocumentos, tipoAr);
			} else {
				FacesContext context = FacesContext.getCurrentInstance();
				FacesMessage mensaje = new FacesMessage("Por favor revise el documento seleccionado.");
				context.addMessage("datosGuardados", mensaje);
				return "";
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage(
					"Por favor guarde primero su solicitud y luego adjunte su documento.");
			mensaje.setSeverity(mensaje.SEVERITY_ERROR);
			context.addMessage("datosGuardados", mensaje);
			return "";
		}
	}

	public void descargarArchivo() {
		Long id = archivoSeleccionadoLeg.getId();
		// Long id = ((ArchivoResumen) (tablaArchivos.getRowData())).getId();
		descargarArchivoRequerimientoGenerico(id, requerimiento.getId());
	}

	public String eliminarArchivoLeg() {
		// Long id = ((ArchivoResumen) (tablaArchivos.getRowData())).getId();
		Long id = archivoSeleccionadoLeg.getId();
		ArchivoRequerimiento archivo = servicioProyecto.obtenerArchivoRequerimiento(id);
		boolean entra = false;
		boolean elimina = false;
		if (archivo != null && (archivo.getArchivo() == null || archivo.getBytes().length <= 1)) {
			entra = true;
			elimina = eliminarArchivoRequerimientoGenerico(id);
		}
		if (!entra) {
			servicioGeneral.eliminarObjeto(archivo);
		} else if (elimina) {
			servicioGeneral.eliminarObjeto(archivo);
			listaDocumentos.remove(archivo);
		}
		return "";
	}

	// **********************************************************

	/*
	 * public void descargarArchivo() { FacesContext ctx =
	 * FacesContext.getCurrentInstance(); String path; path =
	 * ArchivoRequerimiento.DIRECTORIO_ARCHIVOS; path +=
	 * archivoSeleccionado.getId(); System.out.println("descargarArchivo path:"
	 * + path); File ficheroXLS = new File(path); FileInputStream fis; try { fis
	 * = new FileInputStream(ficheroXLS); byte[] bytes = new byte[1000]; int
	 * read = 0; if (!ctx.getResponseComplete()) { String fileName =
	 * archivoSeleccionado .getNombre(); String extension = fileName.substring(
	 * fileName.lastIndexOf("."), fileName.length());
	 * System.out.println("descargarArchivo extension:" + extension); // String
	 * contentType = "application/msword"; String contentType = "text/plain";
	 * System.out.println("descargarArchivo contentType:" + contentType);
	 * HttpServletResponse response = (HttpServletResponse) ctx
	 * .getExternalContext().getResponse();
	 * response.setContentType(contentType);
	 * response.setHeader("Content-Disposition", "attachment;filename=\"" +
	 * fileName + "\""); ServletOutputStream out = response.getOutputStream();
	 * while ((read = fis.read(bytes)) != -1) { out.write(bytes, 0, read); }
	 * out.flush(); out.close(); ctx.responseComplete(); } } catch (Exception e)
	 * { e.printStackTrace(); mensajeError("Error al descargar el archivo."); }
	 * }
	 * 
	 * public void eliminarArchivo() {
	 * 
	 * requerimiento.borrarArchivo(archivo);
	 * 
	 * if(archivo.getId()!=null){ String archivoBorrar =
	 * ArchivoRequerimiento.DIRECTORIO_ARCHIVOS + archivo.getId();
	 * System.out.println("eliminarArchivo archivoBorrar: " + archivoBorrar);
	 * File file = new File(archivoBorrar); if (file.delete()) {
	 * System.out.println(file.getName() + " is deleted!");
	 * 
	 * System.out.println("eliminarArchivo listaArchivos.size: " +
	 * listaDocumentos.size()); System.out.println("eliminarArchivo: " +
	 * archivo.getNombre()); String sql =
	 * "DELETE HER_ARCHIVO_REQUERIMIENTO WHERE HAL_ID = " + archivo.getId();
	 * System.out.println("Se eliminan registros: " + sql); try {
	 * servicioGeneral.eliminar(sql);
	 * System.out.println("Registros eliminados: " + sql); } catch (SQLException
	 * e) { mensajeError("Error eliminando archivo."); e.printStackTrace(); } }
	 * else { System.out.println("Ha ocurrido un error con el borrado."); } } }
	 */

	// **********************************************************

	// public void eliminarArchivo() {
	// ArchivoRequerimiento archivo = archivoSeleccionado;
	// requerimiento.borrarArchivo(archivo);
	// }

	// ********************

	public void guardar() {
		try {

			if (requerimiento != null && !requerimiento.getModulo().equals("0")) {

				if (requerimiento != null && !requerimiento.getSubModulo().equals("0")) {

					if (requerimiento != null && requerimiento.getDescripcionSolic() != null
							&& !requerimiento.getDescripcionSolic().equals("")
							&& !requerimiento.getDescripcionSolic().equals(" ")) {

						Persona personaActual = servicioPersona
								.obtenerPersona(((Persona) sesion.getAttribute("persona")).getId());

						if (personaActual != null) {

							Investigador investigadorActual = servicioPersona
									.obtenerInvestigadorInterno(personaActual.getId());

							// Id Persona
							this.requerimiento.setIdPersona(personaActual.getId().getDocumento());
							List listaDoc = servicioGeneral.obtenerObjetoXID("TipoDocumento",
									personaActual.getId().getTipoDocumento());
							this.requerimiento.setTipoDoc((TipoDocumento) listaDoc.get(0));

							// Nombre persona
							String nombrePersona = personaActual.getNombre1();
							if (personaActual.getNombre2() != null) {
								nombrePersona = nombrePersona + " " + personaActual.getNombre2();
							}
							nombrePersona = nombrePersona + " " + personaActual.getApellido1();
							if (personaActual.getApellido2() != null) {
								nombrePersona = nombrePersona + " " + personaActual.getApellido2();
							}
							this.requerimiento.setPersonaRequerimiento(nombrePersona);

							// email
							if (personaActual.getEmail() != null) {
								this.requerimiento.setEmailSolicitante(personaActual.getEmail());
							}

							// Prioridad / fecha
							this.requerimiento.setPrioridad("Urgente");
							this.requerimiento.setFechaSolicitud(new Date());
							this.requerimiento.setFechaTramite(new Date());
							this.requerimiento.setDependenciaAsociada(dependenciaAsociada);

							// Dependencia
							List listaDep = servicioGeneral.obtenerObjetoXID("Dependencia",
									investigadorActual.getDependencia().getId());
							this.requerimiento.setIdDependencia((Dependencia) listaDep.get(0));

							// Estado
							List listaEstReq = new ArrayList<DominioDetalle>();
							listaEstReq = servicioGeneral
									.obtenerListaObjetos("DominioDetalle where identificador.id = '102'");

							for (int i = 0; i < listaEstReq.size(); i++) {
								DominioDetalle es = (DominioDetalle) listaEstReq.get(i);
								if (es.getIdentificador().getTipo().equals("R")) {
									this.requerimiento.setEstadoRequerimiento(es);
									this.requerimiento.setEstSelRequerimiento(es.getDescripcion());
								}
							}

							// Identificador
							this.requerimiento.setTipo(requerimiento.getSOLICITUD_MEJORA());

							// Guardar objeto
							servicioGeneral.guardarObjeto(this.requerimiento);

							if (requerimiento.getId() != null) {
								FacesContext context = FacesContext.getCurrentInstance();
								FacesMessage mensaje = new FacesMessage(
										"La información ha sido guardada correctamente con el número "
												+ requerimiento.getId());
								context.addMessage("datosGuardados", mensaje);

								// requerimiento = new Requerimiento();

							} else {
								FacesContext context = FacesContext.getCurrentInstance();
								FacesMessage mensaje = new FacesMessage(
										"La información no ha sido guardada. Verifique todos los campos. ");
								mensaje.setSeverity(mensaje.SEVERITY_ERROR);
								context.addMessage("datosGuardados", mensaje);
							}

						}

					} else {
						FacesContext context = FacesContext.getCurrentInstance();
						FacesMessage mensaje = new FacesMessage(
								"La información no ha sido guardada. Ingrese la descripción. ");
						mensaje.setSeverity(mensaje.SEVERITY_ERROR);
						context.addMessage("datosGuardados", mensaje);
					}

				} else {
					FacesContext context = FacesContext.getCurrentInstance();
					FacesMessage mensaje = new FacesMessage(
							"La información no ha sido guardada. Seleccione el proceso. ");
					mensaje.setSeverity(mensaje.SEVERITY_ERROR);
					context.addMessage("datosGuardados", mensaje);
				}

			} else {
				FacesContext context = FacesContext.getCurrentInstance();
				FacesMessage mensaje = new FacesMessage("La información no ha sido guardada. Seleccione el módulo. ");
				mensaje.setSeverity(mensaje.SEVERITY_ERROR);
				context.addMessage("datosGuardados", mensaje);
			}

		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}

	}

	public void enviar() {
		try {

			// Guardar objeto
			guardar();
			requerimiento = new Requerimiento();
			listaDocumentos = new ArrayList<ArchivoRequerimiento>();

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage("La información no ha sido enviada. ");
			mensaje.setSeverity(mensaje.SEVERITY_ERROR);
			context.addMessage("datosGuardados", mensaje);
		}
	}

	// ****************

	public String cancelar() {
		sesion.removeAttribute("Requerimiento");
		return "consultarRequerimiento";
	}

	// *******

	public List<DominioDetalle> getListaEstReq() {
		return listaEstReq;
	}

	public void setListaEstReq(List<DominioDetalle> listaEstReq) {
		this.listaEstReq = listaEstReq;
	}

	// ****

	public DominioDetalle getEstadosReq() {
		return estadosReq;
	}

	public void setEstadosReq(DominioDetalle estadosReq) {
		this.estadosReq = estadosReq;
	}

	// ****

	public String getEstadoSelReq() {
		return estadoSelReq;
	}

	public void setEstadoSelReq(String estadoSelReq) {
		this.estadoSelReq = estadoSelReq;
	}

	public SelectItem[] getEstadosItem() {
		return estadosItem;
	}

	public void setEstadosItem(SelectItem[] estadosItem) {
		this.estadosItem = estadosItem;
	}

	// ****

	public Requerimiento getRequerimiento() {
		return requerimiento;
	}

	public void setRequerimiento(Requerimiento requerimiento) {
		this.requerimiento = requerimiento;
	}

	public Dependencia getDependenciaReq() {
		return dependenciaReq;
	}

	public void setDependenciaReq(Dependencia dependenciaReq) {
		this.dependenciaReq = dependenciaReq;
	}

	/*
	 * public TipoDocumento getTipoDocumentoReq() { return tipoDocumentoReq; }
	 * 
	 * public void setTipoDocumentoReq(TipoDocumento tipoDocumentoReq) {
	 * this.tipoDocumentoReq = tipoDocumentoReq; }
	 */

	public List<Dependencia> getDependenciasUN() {
		return dependenciasUN;
	}

	public void setDependenciasUN(List<Dependencia> dependenciasUN) {
		this.dependenciasUN = dependenciasUN;
	}

	public List<TipoDocumento> getListaTipoDocumento() {
		return listaTipoDocumento;
	}

	public void setListaTipoDocumento(List<TipoDocumento> listaTipoDocumento) {
		this.listaTipoDocumento = listaTipoDocumento;
	}

	public SelectItem[] getDependenciaItem() {
		return dependenciaItem;
	}

	public void setDependenciaItem(SelectItem[] dependenciaItem) {
		this.dependenciaItem = dependenciaItem;
	}

	public SelectItem[] getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}

	public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
		this.tipoDocumentoItem = tipoDocumentoItem;
	}

	public ArchivoRequerimiento getArchivoRequerimiento() {
		return archivoRequerimiento;
	}

	public void setArchivoRequerimiento(ArchivoRequerimiento archivoRequerimiento) {
		this.archivoRequerimiento = archivoRequerimiento;
	}

	// ****

	public DominioDetalle getTipoArcReq() {
		return tipoArcReq;
	}

	public List<DominioDetalle> getListaTipoArcReq() {
		return listaTipoArcReq;
	}

	public void setListaTipoArcReq(List<DominioDetalle> listaTipoArcReq) {
		this.listaTipoArcReq = listaTipoArcReq;
	}

	public void setTipoArcReq(DominioDetalle tipoArcReq) {
		this.tipoArcReq = tipoArcReq;
	}

	public String getTipoArcSelReq() {
		return tipoArcSelReq;
	}

	public void setTipoArcSelReq(String tipoArcSelReq) {
		System.out.println("set*****************" + tipoArcSelReq);
		this.tipoArcSelReq = tipoArcSelReq;
	}

	public SelectItem[] getTipoArcItem() {
		return tipoArcItem;
	}

	public void setTipoArcItem(SelectItem[] tipoArcItem) {
		this.tipoArcItem = tipoArcItem;
	}

	public UploadedFile getArchivoCargarUno() {
		return archivoCargarUno;
	}

	public void setArchivoCargarUno(UploadedFile archivoCargarUno) {
		this.archivoCargarUno = archivoCargarUno;
	}

	// ****

	public ArchivoRequerimiento getArchivoSeleccionado() {
		return archivoSeleccionado;
	}

	public void setArchivoSeleccionado(ArchivoRequerimiento archivoSeleccionado) {
		this.archivoSeleccionado = archivoSeleccionado;
	}

	// ****

	public List<ArchivoRequerimiento> getListaDocumentos() {
		return listaDocumentos;
	}

	public void setListaDocumentos(List<ArchivoRequerimiento> listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}

	public String getMensajeAdjuntaDocumentos() {
		return mensajeAdjuntaDocumentos;
	}

	public void setMensajeAdjuntaDocumentos(String mensajeAdjuntaDocumentos) {
		this.mensajeAdjuntaDocumentos = mensajeAdjuntaDocumentos;
	}

	public String getErrorValidacion() {
		return errorValidacion;
	}

	public void setErrorValidacion(String errorValidacion) {
		this.errorValidacion = errorValidacion;
	}

	//
	//
	// public List<DominioDetalle> getListaTipoArchivoReq() {
	// return listaTipoArchivoReq;
	// }
	//
	//
	//
	//
	// public void setListaTipoArchivoReq(List<DominioDetalle>
	// listaTipoArchivoReq) {
	// this.listaTipoArchivoReq = listaTipoArchivoReq;
	// }

	public String getNombrePrueba() {
		return "prueba";
	}

	public ArchivoRequerimiento getArchivo() {
		return archivo;
	}

	public void setArchivo(ArchivoRequerimiento archivo) {
		this.archivo = archivo;
	}

	public SelectItem[] getModuloItems() {
		return moduloItems;
	}

	public void setModuloItems(SelectItem[] moduloItems) {
		this.moduloItems = moduloItems;
	}

	public SelectItem[] getSubModuloItems() {
		return subModuloItems;
	}

	public void setSubModuloItems(SelectItem[] subModuloItems) {
		this.subModuloItems = subModuloItems;
	}

	public String getDependenciaAsociada() {
		return dependenciaAsociada;
	}

	public void setDependenciaAsociada(String dependenciaAsociada) {
		this.dependenciaAsociada = dependenciaAsociada;
	}

	public UploadedFile getArchivoLeg() {
		return archivoLeg;
	}

	public void setArchivoLeg(UploadedFile archivoLeg) {
		this.archivoLeg = archivoLeg;
	}

	public TipoArchivo getTipoArchivo() {
		return tipoArchivo;
	}

	public void setTipoArchivo(TipoArchivo tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}

	public List getListaTipoArchivo() {
		return listaTipoArchivo;
	}

	public void setListaTipoArchivo(List listaTipoArchivo) {
		this.listaTipoArchivo = listaTipoArchivo;
	}

	public SelectItem[] getTipoArchivoItem() {
		return tipoArchivoItem;
	}

	public void setTipoArchivoItem(SelectItem[] tipoArchivoItem) {
		this.tipoArchivoItem = tipoArchivoItem;
	}

	public DataTable getTablaArchivosLeg() {
		return tablaArchivosLeg;
	}

	public void setTablaArchivosLeg(DataTable tablaArchivosLeg) {
		this.tablaArchivosLeg = tablaArchivosLeg;
	}

	public ArchivoRequerimiento getArchivoSeleccionadoLeg() {
		return archivoSeleccionadoLeg;
	}

	public void setArchivoSeleccionadoLeg(ArchivoRequerimiento archivoSeleccionadoLeg) {
		this.archivoSeleccionadoLeg = archivoSeleccionadoLeg;
	}

}
