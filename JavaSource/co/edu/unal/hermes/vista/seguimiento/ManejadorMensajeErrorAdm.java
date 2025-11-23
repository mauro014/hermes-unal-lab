/*
 * Created on 17-oct-2006
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */

package co.edu.unal.hermes.vista.seguimiento;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Requerimiento;
import co.edu.unal.hermes.modelo.Rol;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorMensajeErrorAdm extends ManejadorBase {

	String nombre;
	String correo;
	String comentario;
	Requerimiento requerimiento;

	/*
	 * public SelectItem[] moduloItems = { new
	 * SelectItem("Registro único de proyectos", "Registro único de proyectos"),
	 * new SelectItem("Convocatoria interna", "Convocatoria interna"), new
	 * SelectItem("Grupos", "Grupos"), new SelectItem("Avales", "Avales")};
	 */

	public SelectItem[] moduloItems;
	public SelectItem[] subModuloItems;

	public SelectItem[] formItems = {
			new SelectItem("Información académico administrativa", "Información académico administrativa"),
			new SelectItem("Información financiera", "Información financiera") };

	public SelectItem[] prioridadItem = { new SelectItem("Urgente", "Urgente"), new SelectItem("Alta", "Alta"),
			new SelectItem("Media", "Media"), new SelectItem("Baja", "Baja") };

	public List<TipoDocumento> listaTipoDocumento;
	public SelectItem[] tipoDocumentoItem;
	private TipoDocumento tipoDocumentoReq;
	private String documento;
	private Persona personaSolicitante;
	private boolean mostrarForm = false;
	List<DominioDetalle> listaIngenieros;
	DominioDetalle tipoListIng;
	String tipoListIngSel;
	public SelectItem[] tipoIngItem;

	private int nPlantilla = 186;

	public ManejadorMensajeErrorAdm() {
		super();
		requerimiento = new Requerimiento();
		requerimiento.tipoDoc = new TipoDocumento();
		requerimiento.idDependencia = new Dependencia();

		// Módulos
		List listaMod = new ArrayList();
		listaMod = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				"from DominioDetalle where identificador.id = '113' and estado='0'");

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
		}
		if (this.requerimiento.getModulo() == null)
			this.requerimiento.setModulo("113_30");

		if (this.requerimiento.getSubModulo() == null) {

			listasMod = servicioGeneral.obtenerObjetos(DominioDetalle.class,
					"from DominioDetalle where identificador.id = '113' and estado='" + this.requerimiento.getModulo()
							+ "'");

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

		// Tipos Documento
		listaTipoDocumento = new ArrayList<TipoDocumento>();
		listaTipoDocumento = servicioGeneral.obtenerListaObjetos(TipoDocumento.class);

		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];

		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			TipoDocumento td = (TipoDocumento) listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
			td = null;
		}

		// Ingenieros
		List listaIngenieros = new ArrayList<DominioDetalle>();
		listaIngenieros = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				"from DominioDetalle where identificador.id = '107' and observacion='A'");

		if (listaIngenieros.size() > 0) {
			tipoIngItem = new SelectItem[listaIngenieros.size()];

			for (int i = 0; i < listaIngenieros.size(); i++) {
				DominioDetalle ta = (DominioDetalle) listaIngenieros.get(i);
				tipoIngItem[i] = new SelectItem(ta.getIdentificador().getTipo(), ta.getDescripcion());
				ta = null;
			}

			setTipoListIngSel((String) tipoIngItem[0].getValue());
		}
	}

	public void cargarSubModulo() {

		if (this.requerimiento.getModulo() == null)
			this.requerimiento.setModulo("113_30");

		List listaMod = new ArrayList();
		listaMod = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				"from DominioDetalle where identificador.id = '113' and estado='" + this.requerimiento.getModulo()
						+ "'");

		DominioDetalle aux = (DominioDetalle) listaMod.get(0);

		if (listaMod.size() > 0) {
			subModuloItems = new SelectItem[listaMod.size()];

			for (int i = 0; i < listaMod.size(); i++) {
				DominioDetalle ta = (DominioDetalle) listaMod.get(i);
				subModuloItems[i] = new SelectItem(ta.getIdentificador().getTipo(), ta.getDescripcion());
				ta = null;
			}

			this.requerimiento.setSubModulo((String) subModuloItems[0].getValue());
		}
	}

	// Buscar Persona
	public void buscarPersona() {

		personaSolicitante = new Persona();

		if (this.requerimiento.getIdPersona() != null) {

			try {

				List listaDoc = servicioGeneral.obtenerObjetoXID("TipoDocumento", requerimiento.getTipoDoc().getId());
				this.requerimiento.setTipoDoc((TipoDocumento) listaDoc.get(0));

				IdPersona id = new IdPersona();
				id.setDocumento(this.requerimiento.getIdPersona());
				id.setTipoDocumento(this.requerimiento.getTipoDoc().getId());

				personaSolicitante = servicioPersona.obtenerPersona(id);

				nombre = personaSolicitante.getNombre1();

				if (personaSolicitante.getNombre2() != null) {
					nombre = nombre + " " + personaSolicitante.getNombre2();
				}
				nombre = nombre + " " + personaSolicitante.getApellido1();
				if (personaSolicitante.getApellido2() != null) {
					nombre = nombre + " " + personaSolicitante.getApellido2();
				}
				this.requerimiento.setPersonaRequerimiento(nombre);

				// Dependencia
				Investigador investigadorActual = servicioPersona
						.obtenerInvestigadorInterno(personaSolicitante.getId());
				List listaDep = servicioGeneral.obtenerObjetoXID("Dependencia",
						investigadorActual.getDependencia().getId());
				this.requerimiento.setIdDependencia((Dependencia) listaDep.get(0));

				// email
				this.requerimiento.setEmailSolicitante(personaSolicitante.getEmail());
				// teléfono
				this.requerimiento.setTelefonoSolicitante(personaSolicitante.getTelefono());

				mostrarForm = true;

			} catch (Exception e) {
				mostrarForm = false;
			}

		} else {
			mostrarForm = false;
		}

	}

	public void mensajeIngeniero() {
		try {
			Correo correo = new Correo();
			CorreoPlantilla cp = cargarPlantilla(nPlantilla);
			correo.setOrigen(Correo.CORREO_HERMES);
			correo.setAsunto(cp.getAsunto());

			TipoDocumento tipoD = new TipoDocumento();
			tipoD.setId("C");
			IdPersona idP = new IdPersona(this.requerimiento.getIngenieroAsignado(), tipoD.getId());
			Persona perAsig = servicioPersona.obtenerPersona(idP);
			correo.setCuerpo(cp.getCuerpo().replaceAll("<<INGENIERO>>", perAsig.getNombreCompleto()));
			correo.setCuerpo(correo.getCuerpo().replaceAll("<<ID>>", this.requerimiento.getId().toString()));
			correo.setCuerpo(
					correo.getCuerpo().replaceAll("<<OBSERVACIONES>>", this.requerimiento.getComentariosIngeniero()));
			correo.setCuerpo(correo.getCuerpo().replaceAll("<<ENTREGA>>",
					new SimpleDateFormat("dd/MM/yyyy").format(this.requerimiento.getFechaEstimada())));
			correo.setCuerpo(correo.getCuerpo().replaceAll("<<TRAMITE>>",
					new SimpleDateFormat("dd/MM/yyyy").format(this.requerimiento.getFechaTramite())));

			System.out.println("correo ===" + correo.getCuerpo());

			String email = "hermes@unal.edu.co";

			if (perAsig.getEmail() != null) {
				email = perAsig.getEmail();
			}

			correo.adicionarDireccion(email);
			//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
			servicioCorreo.enviarCorreo(correo);

			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage("La información ha sido enviada correctamente.");
			context.addMessage("datosGuardados", mensaje);

		} catch (Exception e) {
			System.out.print(e);
		}
	}

	public void guardar() {
		try {

			System.out.println("Guardar!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

			if (mostrarForm) {

				// Estado
				List listaEstReq = new ArrayList<DominioDetalle>();
				listaEstReq = servicioGeneral.obtenerObjetos(DominioDetalle.class,
						"from DominioDetalle where identificador.id = '102'");

				for (int i = 0; i < listaEstReq.size(); i++) {
					DominioDetalle es = (DominioDetalle) listaEstReq.get(i);
					if (es.getIdentificador().getTipo().equals("R")) {
						this.requerimiento.setEstadoRequerimiento(es);
						this.requerimiento.setEstSelRequerimiento(es.getDescripcion());
					}
				}

				// Identificador
				this.requerimiento.setTipo(requerimiento.getSOLICITUD_ERROR());

				// Dependencia asociada
				this.requerimiento.setDependenciaAsociada("HERMES");

				this.requerimiento.setFechaSolicitud(getToday());

				this.requerimiento.setFechaTramite(getToday());

				// Guardar
				servicioGeneral.guardarObjeto(this.requerimiento);

				enviarCorreo(nombre, this.requerimiento.getId());

				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Envío realizado con id=" + this.requerimiento.getId(),
						". Gracias por su colaboración"));
			} else {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Por favor ingrese los datos de la persona solicitante"));
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	@SuppressWarnings("unchecked")
	private void enviarCorreo(String solicitante, Long id) {
		Rol r = new Rol();
		r = servicioPersona.obtenerRol("AR");
		List<Persona> personaRol = servicioPersona.obtenerPersonasxRol(r);
		CorreoPlantilla correoActual = new CorreoPlantilla();
		correoActual = cargarPlantilla(304);
		Persona coord = new Persona();
		coord.setNombre1("Coordinador");
		coord.setApellido1("HERMES");
		coord.setEmail("coordihrms_nal@unal.edu.co");
		personaRol.add(coord);
		for (Persona adm : personaRol) {
			String cuerpoCorreo = correoActual.getCuerpo();
			cuerpoCorreo = cuerpoCorreo.replaceAll("<<ADMINISTRADOR>>", adm.getNombreCompleto());
			cuerpoCorreo = cuerpoCorreo.replaceAll("<<SOLICITANTE>>", solicitante);
			cuerpoCorreo = cuerpoCorreo.replaceAll("<<ID>>", String.valueOf(id));
			Correo mensaje = new Correo();
			mensaje.setOrigen(Correo.CORREO_HERMES);
			//mensaje.adicionarCopiaOculta(Correo.CORREO_HERMES);
			mensaje.setAsunto(correoActual.getAsunto());
			mensaje.setCuerpo(cuerpoCorreo);
			mensaje.adicionarDireccion(adm.getEmail());
			mensaje.adicionarDireccion(Correo.CORREO_HERMES_COMUNICACIONES);
			servicioCorreo.enviarCorreo(mensaje);
		}
	}

	//
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Requerimiento getRequerimiento() {
		return requerimiento;
	}

	public void setRequerimiento(Requerimiento requerimiento) {
		this.requerimiento = requerimiento;
	}

	public SelectItem[] getModuloItems() {
		return moduloItems;
	}

	public void setModuloItems(SelectItem[] moduloItems) {
		this.moduloItems = moduloItems;
	}

	public SelectItem[] getFormItems() {
		return formItems;
	}

	public void setFormItems(SelectItem[] formItems) {
		this.formItems = formItems;
	}

	public SelectItem[] getSubModuloItems() {
		return subModuloItems;
	}

	public void setSubModuloItems(SelectItem[] subModuloItems) {
		this.subModuloItems = subModuloItems;
	}

	public List<TipoDocumento> getListaTipoDocumento() {
		return listaTipoDocumento;
	}

	public void setListaTipoDocumento(List<TipoDocumento> listaTipoDocumento) {
		this.listaTipoDocumento = listaTipoDocumento;
	}

	public SelectItem[] getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}

	public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
		this.tipoDocumentoItem = tipoDocumentoItem;
	}

	public TipoDocumento getTipoDocumentoReq() {
		return tipoDocumentoReq;
	}

	public void setTipoDocumentoReq(TipoDocumento tipoDocumentoReq) {
		this.tipoDocumentoReq = tipoDocumentoReq;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Persona getPersonaSolicitante() {
		return personaSolicitante;
	}

	public void setPersonaSolicitante(Persona personaSolicitante) {
		this.personaSolicitante = personaSolicitante;
	}

	public boolean isMostrarForm() {
		return mostrarForm;
	}

	public void setMostrarForm(boolean mostrarForm) {
		this.mostrarForm = mostrarForm;
	}

	public SelectItem[] getPrioridadItem() {
		return prioridadItem;
	}

	public void setPrioridadItem(SelectItem[] prioridadItem) {
		this.prioridadItem = prioridadItem;
	}

	public DominioDetalle getTipoListIng() {
		return tipoListIng;
	}

	public void setTipoListIng(DominioDetalle tipoListIng) {
		this.tipoListIng = tipoListIng;
	}

	public String getTipoListIngSel() {
		return tipoListIngSel;
	}

	public void setTipoListIngSel(String tipoListIngSel) {
		this.tipoListIngSel = tipoListIngSel;
	}

	public SelectItem[] getTipoIngItem() {
		return tipoIngItem;
	}

	public void setTipoIngItem(SelectItem[] tipoIngItem) {
		this.tipoIngItem = tipoIngItem;
	}
}
