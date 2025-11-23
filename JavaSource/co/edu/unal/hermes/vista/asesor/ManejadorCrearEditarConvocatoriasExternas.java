/**
 * @author Martha Liliana Correa O.
 * @date 24/08/2015
 */

package co.edu.unal.hermes.vista.asesor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.Actividad;
import co.edu.unal.hermes.modelo.ArchivoConvocatoriaExterna;
import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.modelo.ConvocatoriaExterna;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.CorteConvocatoriaExterna;
import co.edu.unal.hermes.modelo.Dominio;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.TipoCargo;
import co.edu.unal.hermes.modelo.TipoVinculacion;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorCrearEditarConvocatoriasExternas extends ManejadorBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean esConsulta = false;
	private boolean esEdicion = false;
	private boolean permitirEdicion = false;
	private boolean esNueva = false;
	private String estadoInicialConvocatoria;

	private List<FuenteFinanciacion> listaEntidadesExternas;
	private ArrayList<SelectItem> listaEntidadesItem;
	private SelectItem[] naturalezaItem;
	private ConvocatoriaExterna convocatoriaExterna;
	private SelectItem[] estadoConvocatoriasItems = { new SelectItem("A", "Activa"), new SelectItem("I", "Inactiva") };
	private boolean estaAbierta;
	private ArchivoConvocatoriaExterna archivoSeleccionado;
	private List<ArchivoConvocatoriaExterna> listaArchivos;
	
	private boolean requiereOtraFuente;
	private SelectItem[] tiposNaturalezaFuenteItem;
	/** The tipos caracterFuenteFinanciacionItem. */
	public SelectItem[] caracterFuenteFinanciacionItem;
	/** The tipos tiposFuenteFinanciacionItem. */
	public SelectItem[] tiposFuenteFinanciacionItem;
	private SelectItem[] categoriaItemsAval;

	public ManejadorCrearEditarConvocatoriasExternas() {
		sesion.removeAttribute("manejadorSemillerosSolicitudVIF");
		sesion.removeAttribute("manejadorSemillerosSolicitudDI");
		sesion.removeAttribute("manejadorSemillerosConsultaVIF");
		sesion.removeAttribute("manejadorSemillerosConsultaDI");
		personaActual = (Persona) sesion.getAttribute("persona");
		try {
			setEsConsulta((Boolean) sesion.getAttribute("esConsulta"));
			setEsEdicion((Boolean) sesion.getAttribute("esEdicion"));

		} catch (Exception e) {
			return;
		}
		FuenteFinanciacion fuenteFinanciacion = new FuenteFinanciacion();
		if (esEdicion || esConsulta) {
			try {
				Long id = (Long) sesion.getAttribute("idConvocatoriaExterna");

				String consulta = "select ce from ConvocatoriaExterna ce where ce.id = '" + id + "'";
				List<ConvocatoriaExterna> convocatoriasExterna = servicioGeneral
						.obtenerObjetos(ConvocatoriaExterna.class, consulta);

				if (convocatoriasExterna != null && convocatoriasExterna.size() > 0) {
					convocatoriaExterna = (ConvocatoriaExterna) convocatoriasExterna.get(0);
					consultarArchivosConvocatoria();
					estadoInicialConvocatoria = convocatoriaExterna.getEstado();
					if (estadoInicialConvocatoria.equals("S")) {
						permitirEdicion = true;
					}
					if (convocatoriaExterna.getEstado().equals("A")) {
						estaAbierta = true;
					} else {
						estaAbierta = false;
					}
				} else {
					esNueva = true;
				}
				
				
			} catch (Exception e) {
				return;
			}

		} else {
			esNueva = true;
		}

		if (esNueva) {
			convocatoriaExterna = new ConvocatoriaExterna();
			listaArchivos = new ArrayList<ArchivoConvocatoriaExterna>();
			convocatoriaExterna.setEntidad(fuenteFinanciacion);
			estaAbierta = false;
			convocatoriaExterna.setExigeContrapartida(0L);
		} else {
			if(esNulo(convocatoriaExterna.getExigeContrapartida()))
				convocatoriaExterna.setExigeContrapartida(0L);
		}

		if (convocatoriaExterna.getNaturaleza() == null) {
			Tipos naturaleza = new Tipos();
			naturaleza.setId(0L);
			convocatoriaExterna.setNaturaleza(naturaleza);
		}
		
		if (convocatoriaExterna.getListaCortes() == null
				|| convocatoriaExterna.getListaCortes().isEmpty()) {
			List<Sede> listaA = servicioGeneral
					.obtenerObjetos("select e from Sede e");
			if (listaA != null && listaA.size() > 0) {
				for (int i = 0; i < listaA.size(); i++) {
					CorteConvocatoriaExterna ps = new CorteConvocatoriaExterna();
					ps.setSede(listaA.get(i));
					convocatoriaExterna.adicionarCorte(ps);
				}
			}
		}


		cargarEntidades();
		cargarTiposNaturaleza();
		tiposAval();
	}

	public void cargarEntidades() {

		listaEntidadesItem = new ArrayList<SelectItem>();
		String hqlentidades = "select e from FuenteFinanciacion e where e.internaExterna = 'E' order by e.descripcion asc";
		listaEntidadesExternas = servicioGeneral.obtenerObjetos(FuenteFinanciacion.class, hqlentidades);

		if (!esListaVacia(listaEntidadesExternas)) {
			for (int i = 0; i < listaEntidadesExternas.size(); i++) {

				FuenteFinanciacion entidadParticipante = (FuenteFinanciacion) listaEntidadesExternas.get(i);
				listaEntidadesItem
						.add(new SelectItem(entidadParticipante.getId(), entidadParticipante.getDescripcion()));
			}
		}
	}

	/**
	 * Lista Naturaleza de las convocatorias
	 */
	public void cargarTiposNaturaleza() {
		naturalezaItem = new SelectItem[0];
		List<Tipos> lista = servicioGeneral.obtenerObjetos(Tipos.class, "select t from Tipos t where t.padre.id = '"
				+ Tipos.NATURALEZA_CONVOCATORIA_EXTERNA + "' order by t.nombre asc");

		if (!esListaVacia(lista)) {
			naturalezaItem = new SelectItem[lista.size()];
			for (int i = 0; i < lista.size(); i++) {
				Tipos naturaleza = lista.get(i);
				naturalezaItem[i] = new SelectItem(naturaleza.getId(), naturaleza.getNombre());
			}
		}
	}

	public SelectItem[] getOpcionesSiNo() {
		SelectItem[] listaOpcionesItem = new SelectItem[2];
		listaOpcionesItem[0] = new SelectItem(0, "No");
		listaOpcionesItem[1] = new SelectItem(1, "Sí");
		return listaOpcionesItem;
	}

	public void consultarArchivosConvocatoria() {
		listaArchivos = new ArrayList<ArchivoConvocatoriaExterna>();
		String consultaArchivos = "select a from ArchivoConvocatoriaExterna a where a.convocatoria = '"
				+ convocatoriaExterna.getId() + "' and a.estado = 'V'";
		List<ArchivoConvocatoriaExterna> listaA = (List<ArchivoConvocatoriaExterna>) servicioGeneral
				.obtenerObjetos(ArchivoConvocatoriaExterna.class, consultaArchivos);
		if (!esListaVacia(listaA)) {
			listaArchivos.addAll(listaA);
		}
	}

	public void adjuntarArchivo(FileUploadEvent event) {
		UploadedFile archivoCargado = event.getFile();
		ArchivoConvocatoriaExterna a = insertarArchivoConvocatoriaExterna(0, archivoCargado);
		if (a != null) {
			listaArchivos.add(a);
		}
	}

	public void descargarArchivo() {
		if (archivoSeleccionado != null) {
			descargarArchivoConvocatoriaExterna(archivoSeleccionado);
		}
	}

	public void eliminarArchivo() {
		if (archivoSeleccionado != null) {
			listaArchivos.remove(archivoSeleccionado);
			archivoSeleccionado.setConvocatoria("0");
			archivoSeleccionado.setFechaElimina(new Date());
			archivoSeleccionado.setEstado("B");
			Persona personaElimina = (Persona) sesion.getAttribute("persona");
			archivoSeleccionado.setPersonaElimina(personaElimina);
			servicioGeneral.guardarObjeto(archivoSeleccionado);
		}
	}
	
	public void contrapartidaCriterioEvaluacionNulo() {
		if(convocatoriaExterna.getExigeContrapartida().equals(1L))
			convocatoriaExterna.setContrapartidaCriterioEvaluacion(null);
	}

	public void guardar() {

		if (validarDatosFormulario()) {

			if (convocatoriaExterna.getNumero() == null) {
				convocatoriaExterna.setNumero("-");
			}
			// Guardar persona que abre o cierra
			if (estaAbierta && "I".equals(convocatoriaExterna.getEstado())) {
				convocatoriaExterna.setPersonaCierra(personaActual);
			} else if (!estaAbierta && ("A".equals(convocatoriaExterna.getEstado()))) {
				convocatoriaExterna.setPersonaAbre(personaActual);
			} else if ("B".equals(convocatoriaExterna.getEstado())) {
				convocatoriaExterna.setPersonaCierra(personaActual);
			}

			if (estadoInicialConvocatoria != null && "S".equals(estadoInicialConvocatoria)
					&& "I".equals(convocatoriaExterna.getEstado())) {
				convocatoriaExterna.setEstado("S");
			}

			// GuardarConvocatoria
			servicioGeneral.guardarObjeto(convocatoriaExterna);
			

			// GuardarArchivos
			Iterator<ArchivoConvocatoriaExterna> itSet = listaArchivos.iterator();
			while (itSet.hasNext()) {
				ArchivoConvocatoriaExterna archivo = itSet.next();
				archivo.setConvocatoria(convocatoriaExterna.getId().toString());
				archivo.setEstado("V");
				this.servicioGeneral.guardarObjeto(archivo);
			}

			sesion.removeAttribute("manejadorAdministrarConvocatoriasExternas");
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "La convocatoria se ha guardado correctamente.", ""));
		} else {
			return;
		}

	}

	public void guardarSolicitudDependencia() {

		if (validarDatosFormulario()) {

			if (convocatoriaExterna.getNumero() == null) {
				convocatoriaExterna.setNumero("-");
			}

			List<FuenteFinanciacion> entidad = servicioGeneral.obtenerObjetoXID(FuenteFinanciacion.class,
					convocatoriaExterna.getEntidad().getId());
			FuenteFinanciacion ff = (entidad.get(0));
			convocatoriaExterna.setEntidad(ff);

			// Guardar persona que abre o cierra
			convocatoriaExterna.setEstado("S");
			convocatoriaExterna.setPersonaSolicita(personaActual);

			// GuardarConvocatoria
			servicioGeneral.guardarObjeto(convocatoriaExterna);

			// GuardarArchivos
			Iterator<ArchivoConvocatoriaExterna> itSet = listaArchivos.iterator();
			while (itSet.hasNext()) {
				ArchivoConvocatoriaExterna archivo = itSet.next();
				archivo.setConvocatoria(convocatoriaExterna.getId().toString());
				archivo.setEstado("V");
				this.servicioGeneral.guardarObjeto(archivo);
			}

			Locale localidad = new Locale("sp", "co");
			SimpleDateFormat formatoFecha = new SimpleDateFormat("MMMM dd yyyy", localidad);

			String fechaCierre;
			fechaCierre = formatoFecha.format(convocatoriaExterna.getFechaCierre());

			// Envio de correo
			Correo correo = new Correo();
			CorreoPlantilla cp = cargarPlantilla(247);
			correo.setOrigen(Correo.CORREO_HERMES);
			correo.setAsunto(cp.getAsunto().replaceAll("<<NOMBRE>>",
					convocatoriaExterna.getNombre() != null ? convocatoriaExterna.getNombre() : ""));
			correo.setCuerpo(cp.getCuerpo().replaceAll("<<NOMBRE>>",
					convocatoriaExterna.getNombre() != null ? convocatoriaExterna.getNombre() : ""));
			correo.setCuerpo(correo.getCuerpo().replaceAll("<<NUM>>",
					convocatoriaExterna.getNumero() != null ? convocatoriaExterna.getNumero() : ""));
			correo.setCuerpo(correo.getCuerpo().replaceAll("<<FECHA>>", fechaCierre));
			correo.setCuerpo(
					correo.getCuerpo().replaceAll("<<ENTIDAD>>", convocatoriaExterna.getEntidad().getDescripcion()));
			String personaCorreo = "(" + personaActual.getId().getTipoDocumento() + "-"
					+ personaActual.getId().getDocumento() + ") " + personaActual.getNombreCompletoMinusculas();
			correo.setCuerpo(correo.getCuerpo().replaceAll("<<PROFESOR>>", personaCorreo));
			correo.setCuerpo(correo.getCuerpo().replaceAll("<<CORREO>>", personaActual.getEmail()));
			correo.adicionarDireccion(Correo.CORREO_HERMES_COMUNICACIONES);
			servicioCorreo.enviarCorreo(correo);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "La convocatoria se ha guardado correctamente", ""));

			sesion.removeAttribute("manejadorAdministrarConvocatoriasExternas");

		} else {
			return;
		}
	}

	private boolean validarDatosFormulario() {

		boolean validada = true;

		if (convocatoriaExterna.getEntidad() == null || convocatoriaExterna.getEntidad().getId().trim().equals("")) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Debe seleccionar la entidad convocante.", ""));
			validada = false;
		} else if (esCadenaVacia(convocatoriaExterna.getNombre())) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Debe indicar el nombre de la convocatoria.", ""));
			validada = false;
		} else if (convocatoriaExterna.getFechaApertura() == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Debe indicar la fecha de apertura de la convocatoria.", ""));
			validada = false;
		} else if (convocatoriaExterna.getFechaCierre() == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Debe indicar la fecha de cierre de la convocatoria.", ""));
			validada = false;
		} else if (esNulo(convocatoriaExterna.getFechaResultados())) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Debe indicar la fecha de entrega de resultados de la convocatoria.", ""));
			validada = false;	
		} else if (convocatoriaExterna.getFechaCierre().before(convocatoriaExterna.getFechaApertura())) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"La fecha de cierre de la convocatoria no debe ser menor a la fecha de apertura.", ""));
			validada = false;
		} else if (convocatoriaExterna.getFechaResultados().before(convocatoriaExterna.getFechaCierre())) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"La fecha de entrega de resultados de la convocatoria no debe ser menor a la fecha de cierre.", ""));
			validada = false;
		} else if (convocatoriaExterna.getNaturaleza() == null || (convocatoriaExterna.getNaturaleza() != null
				&& ("".equals(convocatoriaExterna.getNaturaleza().getId())
						|| convocatoriaExterna.getNaturaleza().getId().equals(0L)))) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Debe indicar la naturaleza de la convocatoria.", ""));
			validada = false;
		}

		return validada;
	}

	public boolean isEsConsulta() {
		return esConsulta;
	}

	public void setEsConsulta(boolean esConsulta) {
		this.esConsulta = esConsulta;
	}

	public boolean isEsEdicion() {
		return esEdicion;
	}

	public void setEsEdicion(boolean esEdicion) {
		this.esEdicion = esEdicion;
	}

	public List<FuenteFinanciacion> getListaEntidadesExternas() {
		return listaEntidadesExternas;
	}

	public void setListaEntidadesExternas(List<FuenteFinanciacion> listaEntidadesExternas) {
		this.listaEntidadesExternas = listaEntidadesExternas;
	}

	public ArrayList<SelectItem> getListaEntidadesItem() {
		return listaEntidadesItem;
	}

	public void setListaEntidadesItem(ArrayList<SelectItem> listaEntidadesItem) {
		this.listaEntidadesItem = listaEntidadesItem;
	}

	public ConvocatoriaExterna getConvocatoriaExterna() {
		return convocatoriaExterna;
	}

	public void setConvocatoriaExterna(ConvocatoriaExterna convocatoriaExterna) {
		this.convocatoriaExterna = convocatoriaExterna;
	}

	public SelectItem[] getEstadoConvocatoriasItems() {
		return estadoConvocatoriasItems;
	}

	public void setEstadoConvocatoriasItems(SelectItem[] estadoConvocatoriasItems) {
		this.estadoConvocatoriasItems = estadoConvocatoriasItems;
	}

	public boolean isEstaAbierta() {
		return estaAbierta;
	}

	public void setEstaAbierta(boolean estaAbierta) {
		this.estaAbierta = estaAbierta;
	}

	public ArchivoConvocatoriaExterna getArchivoSeleccionado() {
		return archivoSeleccionado;
	}

	public void setArchivoSeleccionado(ArchivoConvocatoriaExterna archivoSeleccionado) {
		this.archivoSeleccionado = archivoSeleccionado;
	}

	public List<ArchivoConvocatoriaExterna> getListaArchivos() {
		return listaArchivos;
	}

	public void setListaArchivos(List<ArchivoConvocatoriaExterna> listaArchivos) {
		this.listaArchivos = listaArchivos;
	}

	public boolean isPermitirEdicion() {
		return permitirEdicion;
	}

	public void setPermitirEdicion(boolean permitirEdicion) {
		this.permitirEdicion = permitirEdicion;
	}

	public String getEstadoInicialConvocatoria() {
		return estadoInicialConvocatoria;
	}

	public void setEstadoInicialConvocatoria(String estadoInicialConvocatoria) {
		this.estadoInicialConvocatoria = estadoInicialConvocatoria;
	}

	public boolean isEsNueva() {
		return esNueva;
	}

	public void setEsNueva(boolean esNueva) {
		this.esNueva = esNueva;
	}

	public SelectItem[] getNaturalezaItem() {
		return naturalezaItem;
	}

	public void setNaturalezaItem(SelectItem[] naturalezaItem) {
		this.naturalezaItem = naturalezaItem;
	}

	public boolean isRequiereOtraFuente() {
		return requiereOtraFuente;
	}

	public void setRequiereOtraFuente(boolean requiereOtraFuente) {
		this.requiereOtraFuente = requiereOtraFuente;
	}
	
	public SelectItem[] getTiposNaturalezaFuenteItem() {
		return crearListaItemDominioDetalle(Dominio.NAT_ENTIDAD);
	}

	public void setTiposNaturalezaFuenteItem(SelectItem[] tiposNaturalezaFuenteItem) {
		this.tiposNaturalezaFuenteItem = tiposNaturalezaFuenteItem;
	}

	public SelectItem[] getCaracterFuenteFinanciacionItem() {
		return crearListaItemDominioDetalle(Dominio.CARACTER_FUENTE_FINANCIACION);
	}

	public SelectItem[] getTiposFuenteFinanciacionItem() {
		return crearListaItemDominioDetalle(Dominio.TIPO_FUENTE_FINANCIACION);
	}

	public void tiposAval() {

		List<DominioDetalle> lista;
			String consulta = "select dd from Dominio d, DominioDetalle dd where "
					+ "d.id = dd.identificador.id and d.tipo like 'ACTIVIDAD_AVAL_NEW' and dd.observacion is not null";
					
		lista = servicioGeneral.obtenerObjetos(DominioDetalle.class, consulta);
		

		if (!esListaVacia(lista)) {
			categoriaItemsAval = new SelectItem[lista.size()+1];
			for (int i = 0; i < lista.size(); i++) {
				DominioDetalle dominio = (DominioDetalle) lista.get(i);
				categoriaItemsAval[i] = new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion());
			}
			categoriaItemsAval[lista.size()] = new SelectItem("NA", "No aplica");
		}
	}

	public SelectItem[] getCategoriaItemsAval() {
		return categoriaItemsAval;
	}

	public void setCategoriaItemsAval(SelectItem[] categoriaItemsAval) {
		this.categoriaItemsAval = categoriaItemsAval;
	}

}
