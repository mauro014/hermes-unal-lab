package co.edu.unal.hermes.vista.aval;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.hibernate.HibernateException;
import org.primefaces.event.FileUploadEvent;
import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.ArchivoAval;
import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Parametro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.Reporte;
import co.edu.unal.hermes.modelo.TipoArchivo;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.aval.base.BaseManejadorSolicitarAvalDireccion;

public class ManejadorSolicitarAvalDRE extends BaseManejadorSolicitarAvalDireccion {

	private static final long serialVersionUID = 1L;
	private String idTipoArchivo;
	private List<SelectItem> selTipoArchivoAvalInt;
	private boolean antecedentesCooperacion;
	private String consecutivoConcepto;
	private String numeroTramite;
	private String descripcion;
	private String antecedentes;
	private String directorDRE;
	private String directorDREOficial;

	public ManejadorSolicitarAvalDRE() {
		List<Aval> listaAvalAux = new ArrayList<Aval>();
		listaAvalAux = servicioGeneral.obtenerAvalesDRE();
		organizarListaRevisionAval(listaAvalAux);
		String consulta = "select dd from Dominio d, DominioDetalle dd where "
				+ "d.id = dd.identificador.id and d.id = 275 and dd.estado='2' order by dd.identificador.tipo";
		List<DominioDetalle> lista = servicioGeneral.obtenerObjetos(DominioDetalle.class, consulta);
		if (selTipoArchivoAvalInt == null) {
			selTipoArchivoAvalInt = new ArrayList<SelectItem>();
		}
		selTipoArchivoAvalInt.clear();
		for (int i = 0; i < lista.size(); i++) {
			DominioDetalle dominio = (DominioDetalle) lista.get(i);
			selTipoArchivoAvalInt.add(new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion()));
		}
		idTipoArchivo = selTipoArchivoAvalInt.get(0).getValue().toString();
		consulta = "select par from Parametro par where " + "par.nombre= '" + "DIRECTOR_DRE'";
		Parametro regDRE = servicioGeneral.obtenerObjetos(Parametro.class, consulta).get(0);
		directorDRE = regDRE.getValor();
		directorDREOficial = directorDRE;
		cargo = "Directora (E)";
	}

	public void actualizarTipo() {
	}

	public void guardarArchivoDRE(FileUploadEvent event) {
		for (ArchivoAval archivo : aval.getArchivosCoor()) {
			if (archivo.getTipoArchivo().getId() == Short.parseShort(idTipoArchivo)) {
				mensajeError("No se puede adjuntar el archivo, ya que existe un documento asociado a este tipo.");
				return;
			}
		}
		setArchivoDRECargar(event.getFile());
		ArchivoAval aa = insertarArchivoAvalGenerico(aval.getAviId(), getArchivoDRECargar(), false, idTipoArchivo);
		if (aa != null) {
			aval.getArchivosCoor().add(aa);
		}
	}

	public void mensajeErrorConcepto(String mensaje) {
		FacesContext.getCurrentInstance().addMessage("msgConcepto",
				new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, null));
	}

	private boolean validarFormatoConcepto() {
		boolean datosValidos = true;
		if (esCadenaVacia(consecutivoConcepto)) {
			mensajeErrorConcepto("Por favor, indique el consecutivo DRE asociado al formato.");
			datosValidos = false;
		}
		if (esCadenaVacia(numeroTramite)) {
			mensajeErrorConcepto("Por favor, indique el número de trámite DRE asociado al formato.");
			datosValidos = false;
		}
		if (esCadenaVacia(descripcion)) {
			mensajeErrorConcepto("Por favor, indique la descripción de la institución externa.");
			datosValidos = false;
		}
		if (antecedentesCooperacion && esCadenaVacia(antecedentes)) {
			mensajeErrorConcepto("Por favor, indique el detalle de los antecedentes de cooperación.");
			datosValidos = false;
		}
		if (encargado && esCadenaVacia(directorDRE)) {
			mensajeErrorConcepto("Por favor, indique el nombre del director(a) DRE.");
			datosValidos = false;
		}
		return datosValidos;
	}

	public void generarFormatoConcepto() {
		if (validarFormatoConcepto()) {
			ReporteBirt r = new ReporteBirt();
			r.setNombreReporte("/aval/formatoConcepto");
			r.setFormato(ReporteBirt.FORMATO_PDF);
			r.adicionarParametro("codigo", aval.getAviId().toString());
			r.adicionarParametro("consecutivo", consecutivoConcepto);
			r.adicionarParametro("tramite", numeroTramite);
			r.adicionarParametro("elaboro", personaActual.getId().getDocumento());
			r.adicionarParametro("descripcion", descripcion);
			r.adicionarParametro("antecedentes", antecedentesCooperacion ? antecedentes : "<SIN ANTECEDENTES>");
			if (encargado) {
				List<Parametro> encargado = servicioGeneral.obtenerObjetos(Parametro.class,
						"select p from Parametro p where " + "p.nombre = 'TEMPORAL_CARTAS_AVAL'");
				encargado.get(0).setValor(directorDRE);
				servicioGeneral.guardarObjeto(encargado.get(0));

				r.adicionarParametro("cargo", cargo);
				r.adicionarParametro("directorDRE", directorDRE);
			} else {
				r.adicionarParametro("cargo", "N");
				r.adicionarParametro("directorDRE", "N");
			}
			sesion.setAttribute("reporte", r);
			FacesContext context = FacesContext.getCurrentInstance();
			r.run(context);
		} else {
			return;
		}
	}

	public String editarAval() {
		consultarAvalTramitar();
		listaplantilla = servicioAval.obtenerListaCartas(aval.getTipo());
		if (!esListaVacia(listaplantilla)) {
			plantillas = new SelectItem[listaplantilla.size()];
			int i = 0;
			for (Iterator<Reporte> ic = listaplantilla.iterator(); ic.hasNext();) {
				Reporte p = ic.next();
				plantillas[i] = new SelectItem(p.getId().toString(), p.getNombreExterno());
				i++;
			}
		}
		List<ArchivoAval> docs = new ArrayList<ArchivoAval>();
		for (ArchivoAval archivo : listaArchivos) {
			if (archivo.getTipoArchivo() != null && (archivo.getTipoArchivo().getId() == 172
					|| archivo.getTipoArchivo().getId() == 173 || archivo.getTipoArchivo().getId() == 174)) {
				aval.getArchivosCoor().add(archivo);
			} else {
				docs.add(archivo);
			}

		}
		listaArchivos = docs;
		return "consultaAvalGenerarDRE";
	}

	public void reporteAval() throws SQLException {
		if (esCadenaVacia(avalId)) {
			mensajeError("Debe seleccionar una plantilla de carta para generarla.");
			return;
		}

		ReporteBirt r = new ReporteBirt();
		personaActual = (Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL);
		InvestigadorInterno invInterno = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());

		servicioGeneral.ejecutarSentencia("UPDATE HER_AVAL SET AVI_TEXTO_COMP_COOR = '" + aval.getAviTextoCompCoor()
				+ "' WHERE AVI_ID = '" + aval.getAviId().toString() + "'");
		String duracionMeses = "---";
		String valorContrapartidaLetras = "";
		if (aval.isEsRegalias()) {
			valorContrapartidaLetras = convertir(
					String.valueOf(aval.getAviEspecieunal() + aval.getValorPersonalTotal()), false);

			Proyecto proyecto = servicioProyecto.obtenerProyecto(aval.getIdProyecto(),
					ProyectoDAOHibernate.DATOS_BASICOS);
			duracionMeses = convertirDuracionMeses(proyecto.getDuracionTipo(), proyecto.getDuracion());
		}

		Calendar calendar = Calendar.getInstance();
		r.adicionarParametro("valorletras", valorContrapartidaLetras);
		r.adicionarParametro("mes", getNombreMes(calendar.get(Calendar.MONTH)));
		r.adicionarParametro("valorNumero", String.valueOf(aval.getAviEspecieunal() + (aval.getValorPersonalTotal())));
		r.adicionarParametro("Id", aval.getAviId().toString());
		r.adicionarParametro("Decano", "NO");
		r.adicionarParametro("Ciu", "Bogotá D.C.");
		r.adicionarParametro("Sed", invInterno.getDependencia().getSede().getNombre());
		r.adicionarParametro("meses", duracionMeses);
		r.adicionarParametro("d", "1");
		r.setNombreReporte(obtenerNombreCarta(avalId));
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
	}

	public String guardarRevision() {
		if (aval == null) {
			return "";
		}
		personaActual = (Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL);
		boolean temp = false;
		aval.setAvalDRE("");
		if (this.selItem == 2) {
			this.aval.setAviEstado(Aval.REVISADO_DRE);
			aval.setAvalDRE(Aval.APROBADO);
			aval.setFechaAvalDRE(getToday());
			temp = true;
			boolean tieneConcepto = false;
			boolean tieneFormato = false;
			for (ArchivoAval archivo : aval.getArchivosCoor()) {
				if (archivo.getTipoArchivo() != null) {
					if (archivo.getTipoArchivo().getId().equals(TipoArchivo.CONCEPTO_DRE)) {
						tieneConcepto = true;
					}
					
					if (archivo.getTipoArchivo().getId().equals(TipoArchivo.FORMATO_DRE_SPANISH)) {
						tieneFormato = true;
					}
				}
			}
			if (!tieneConcepto) {
				mensajeError(
						"Si la decisión es aprobar, por favor cargue el concepto favorable de la DRE para este aval.");
				return "";
			} else if (!tieneFormato) {
				mensajeError(
						"Si la decisión es aprobar, por favor cargue el formato estándar de la UNAL en español para este aval.");
				return "";
			}
		} else if (this.selItem == 3) {
			if (esCadenaVacia(aval.getTextoDRE())) {
				mensajeError("Debe ingresar los comentarios de la NO APROBACIÓN del aval");
				return "";
			}
			this.aval.setAviEstado(Aval.REVISADO_DRE);
			aval.setAvalDRE(Aval.NEGADO);
			aval.setFechaAvalDRE(getToday());
			temp = true;
		}

		else if (this.selItem == 4) {
			if (esCadenaVacia(aval.getTextoDRE())) {
				mensajeError("Debe ingresar los comentarios de la DEVOLUCIÓN del aval");
				return "";
			}
			if (aval.getEsAvalParaRevisionVice() != null && aval.getEsAvalParaRevisionVice().equals("S")) {
				aval.setAviEstado(Aval.REVISADO_DIRECCION);
				aval.setAviFechaAvalVice(null);
				aval.setAvalVice(null);

			} else {
				aval.setAviEstado(Aval.REVISADO_FACULTAD);
				aval.setAviAvaldireccion(null);
				aval.setAviFechaAvalCoor(null);
			}
			aval.setAvalDRE(null);
			aval.setEsAvalParaRevisionDRE(null);
			temp = true;
		}

		if (temp) {
			Persona personaAux = servicioPersona
					.obtenerPersona(new IdPersona(aval.getDocumento(), aval.getTipoDocumento()));
			aval.setFechaAvalDRE(new Date());

			this.servicioGeneral.guardarObjeto(this.aval);

			if (aval.getAvalDRE() != null && aval.getAvalDRE().equals(Aval.APROBADO)) {
				correoActual = cargarPlantilla(336);
				Correo correo = editarCorreo(personaAux, aval);
				InvestigadorInterno docenteAval = servicioPersona.obtenerInvestigadorInterno(personaAux.getId());
				String sql = "JOIN i.roles r " +
			             "WHERE r.id = 'AD' " +
			             "AND i.dependencia.sede.id = '" + docenteAval.getDependencia().getSede().getId() + "'";

			if ("S".equals(aval.getEsAvalParaRevisionVice())) {
			    correoActual = cargarPlantilla(341);
			    correo = editarCorreo(personaAux, aval);
			    sql = "JOIN i.roles r WHERE r.id = 'AV'";
			}

			for (InvestigadorInterno p : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class, sql)) {
			    if (tieneRolVigente(p, sql.contains("AV") ? "AV" : "AD") && p.getEmail() != null) {
			        correo.adicionarDireccion(p.getEmail());
			    }
			}

			servicioCorreo.enviarCorreo(correo);

			// Segundo correo (para rol RE)
			correoActual = cargarPlantilla(364);
			correo = editarCorreo(personaAux, aval);

			sql = "JOIN i.roles r WHERE r.id = 'RE'";

			for (InvestigadorInterno p : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class, sql)) {
			    if (tieneRolVigente(p, "RE") && p.getEmail() != null) {
			        correo.adicionarDireccion(p.getEmail());
			    }
			}

				try {
					if (servicioGeneral.esAmbienteProduccion()) {
						correo.adicionarDireccion("rectoriaun@unal.edu.co");
					}
				} catch (HibernateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				servicioCorreo.enviarCorreo(correo);
			} else if (aval.getAvalDRE() != null && aval.getAvalDRE().equals(Aval.NEGADO)) {
				correoActual = cargarPlantilla(337);
				Correo correo = editarCorreo(personaAux, aval);
				InvestigadorInterno docenteAval = servicioPersona.obtenerInvestigadorInterno(personaAux.getId());
				Long idSede = docenteAval.getDependencia().getSede().getId();
				String rolABuscar = "AD";

				if (aval.getEsAvalParaRevisionVice().equals("S")) {
				    correo = editarCorreo(personaAux, aval);
				    rolABuscar = "AV";
				}

				String sql = " JOIN i.roles r WHERE r.id = '" + rolABuscar + "' AND i.dependencia.sede.id = '" + idSede + "'";

				List<InvestigadorInterno> posiblesDestinatarios = servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class, sql);

				for (InvestigadorInterno p : posiblesDestinatarios) {
				    if (tieneRolVigente(p, rolABuscar) && p.getEmail() != null) {
				        correo.adicionarDireccion(p.getEmail());
				    }
				}

				servicioCorreo.enviarCorreo(correo);
			} else if (this.selItem == 4) {
				correoActual = cargarPlantilla(338);
				Correo correo = editarCorreo(personaAux, aval);
				InvestigadorInterno docenteAval = servicioPersona.obtenerInvestigadorInterno(personaAux.getId());
				Long idSede = docenteAval.getDependencia().getSede().getId();

				List<InvestigadorInterno> posiblesAD = servicioGeneral.obtenerListaObjetosWhere(
				    InvestigadorInterno.class,
				    " JOIN i.roles r WHERE r.id = 'AD' AND i.dependencia.sede.id = '" + idSede + "'"
				);

				for (InvestigadorInterno inv : posiblesAD) {
				    if (tieneRolVigente(inv, "AD") && inv.getEmail() != null) {
				        correo.adicionarDireccion(inv.getEmail());
				    }
				}

				// Si se requiere enviar también a los con rol AV
				if ("S".equals(aval.getEsAvalParaRevisionVice())) {
				    correoActual = cargarPlantilla(342);
				    correo = editarCorreo(personaAux, aval);

				    List<InvestigadorInterno> posiblesAV = servicioGeneral.obtenerListaObjetosWhere(
				        InvestigadorInterno.class,
				        " JOIN i.roles r WHERE r.id = 'AV'"
				    );

				    for (InvestigadorInterno inv : posiblesAV) {
				        if (tieneRolVigente(inv, "AV") && inv.getEmail() != null) {
				            correo.adicionarDireccion(inv.getEmail());
				        }
				    }
				}

				servicioCorreo.enviarCorreo(correo);
			}
			crearHistoricoEstadoAval(aval, personaActual, "DRE");
			listaAval = servicioGeneral.obtenerAvalesDRE();
			mensajeInfo("Guardado con éxito");
			sesion.removeAttribute("manejadorSolicitarAvalDRE");
		}
		return "avalarDRE";
	}

	public void generarFormatoStdDRE() {
		encargado = false;
		generarFormatoStd();
		return;
	}

	public Correo editarCorreo(Persona personaAux, Aval nAval) {

		Correo correoElectronico = new Correo();

		try {
			String correo = correoActual.getCuerpo();
			correo = correo.replaceAll("<<INVESTIGADOR>>", personaAux.getNombreCompletoMinusculas());
			correo = correo.replaceAll("<<TIPO>>", nAval.getNombreTipo());
			correo = correo.replaceAll("<<ID_AVAL>>", nAval.getAviId().toString());
			correo = correo.replaceAll("<<RAZON_RECHAZO>>", eliminarCaracterSinReplace("$", aval.getTextoDRE()));
			correo = correo.replaceAll("<<SEDE>>", aval.getDependencia().getSede().getNombre());
			correo = correo.replaceAll("<<SEDE_INV>>",
					(((InvestigadorInterno) personaAux).getDependencia().getSede().getNombre()));
			correo = correo.replaceAll("<<TIPOAVAL>>", nAval.getNombreTipo());
			correoElectronico.setOrigen(Correo.CORREO_HERMES);
			// correoElectronico.adicionarCopiaOculta(Correo.CORREO_HERMES);
			correoElectronico.adicionarCopiaOculta(personaActual.getEmail());
			if (!nAval.getAviEstado().equals(Aval.DEVUELTO)) {
				correoElectronico.adicionarDireccion(personaAux.getEmail());
			}
			correoElectronico.setAsunto(correoActual.getAsunto());
			correoElectronico.setCuerpo(correo);

		} catch (Exception e) {
			return correoElectronico;
		}
		return correoElectronico;
	}

	public String anterior() {
		sesion.removeAttribute("manejadorSolicitarAvalDRE");
		return "avalarDRE";
	}

	public int getTamañoLista() {
		if (listaAval != null) {
			return listaAval.size();
		}
		return 0;
	}

	public String getIdTipoArchivo() {
		return idTipoArchivo;
	}

	public void setIdTipoArchivo(String idTipoArchivo) {
		this.idTipoArchivo = idTipoArchivo;
	}

	public List<SelectItem> getSelTipoArchivoAvalInt() {
		return selTipoArchivoAvalInt;
	}

	public void setSelTipoArchivoAvalInt(List<SelectItem> selTipoArchivoAvalInt) {
		this.selTipoArchivoAvalInt = selTipoArchivoAvalInt;
	}

	public boolean isAntecedentesCooperacion() {
		return antecedentesCooperacion;
	}

	public void setAntecedentesCooperacion(boolean antecedentesCooperacion) {
		this.antecedentesCooperacion = antecedentesCooperacion;
	}

	public String getConsecutivoConcepto() {
		return consecutivoConcepto;
	}

	public void setConsecutivoConcepto(String consecutivoConcepto) {
		this.consecutivoConcepto = consecutivoConcepto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getAntecedentes() {
		return antecedentes;
	}

	public void setAntecedentes(String antecedentes) {
		this.antecedentes = antecedentes;
	}

	public String getDirectorDRE() {
		return directorDRE;
	}

	public void setDirectorDRE(String directorDRE) {
		this.directorDRE = directorDRE;
	}

	public String getNumeroTramite() {
		return numeroTramite;
	}

	public void setNumeroTramite(String numeroTramite) {
		this.numeroTramite = numeroTramite;
	}
}
