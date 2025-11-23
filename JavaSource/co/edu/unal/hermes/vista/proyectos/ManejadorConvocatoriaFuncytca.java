/*
 * Created on 11-jun-2013
 */
package co.edu.unal.hermes.vista.proyectos;

import java.io.IOException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.vista.proyectos.manejadorproyecto.ManejadorProyecto;

public class ManejadorConvocatoriaFuncytca extends ManejadorProyecto
{

	private String usrPassword;
	private String password1;
	private String password2;
	private String opcionesUsuario;
	private boolean mostrarRegistroUsuario = false;
	private boolean mostrarRecordarClave = false;
	private boolean deshabilitarBotonLogin = false;
	private TipoDocumento tipoDocumentoInv;
	private SelectItem[] tipoDocumentoItem;
	private List<TipoDocumento> listaTipoDocumento;
	private String documentoInv;
	private String documentoIngresoInv;
	private String primerNombre;
	private String segundoNombre;
	private String primerApellido;
	private String segundoApellido;
	private String correoElectronico;
	private String direccion;
	private String tel_fijo;
	private String idConvocatoria;
	private Proyecto proyectoUsuario;
	private String mensajeConvocatoria = "";
	private String modConvo = "";
	private String mensajeUsuariocreado = "";
	private boolean deshabilitarGuardarPersona = false;

	public ManejadorConvocatoriaFuncytca()
	{
		super();
		cargarValoresIniciales();

		idConvocatoria = request.getParameter("idConvocatoria");
		System.out.println("******************** ID  CONVOCATORIA ****************" + idConvocatoria);
		sesion.setAttribute("idConvocatoriaFuncytca", idConvocatoria);

	}

	public void validarOpcionesUsuario()
	{
		if (opcionesUsuario.equals("recordarClave"))
		{
			mostrarRecordarClave = true;
			mostrarRegistroUsuario = false;
			deshabilitarBotonLogin = true;
		}
		else
		{
			mostrarRecordarClave = false;
			mostrarRegistroUsuario = true;
			deshabilitarBotonLogin = true;
		}
	}

	public void guardarNuevoUsuario()
	{

		Persona per = servicioPersona.obtenerPersona(new IdPersona(documentoInv, tipoDocumentoInv.getId()));

		if (per == null)
		{
			per = new Persona();
			per.setId(new IdPersona(documentoInv, tipoDocumentoInv.getId()));
			per.setNombre1(this.primerNombre);
			per.setNombre2(this.segundoNombre);
			per.setApellido1(this.primerApellido);
			per.setApellido2(this.segundoApellido);
			per.setEmail(this.correoElectronico);
			per.setTelefono(tel_fijo);
			per.setDireccion(direccion);
			per.setTelefonoHojaVida(password1);
			per.setGenero("S");
			servicioPersona.insertarNuevaPersonaDatosBasicosFuncytca(per);
			sesion.removeAttribute("ManejadorConvocatoriaFuncytca");
			sesion.removeAttribute("ManejadorFormularioRegistroFuncytca");
		}
		else
		{
			if (per.getTelefonoHojaVida() != null && per.getTelefonoHojaVida().equals(password1))
			{
				deshabilitarGuardarPersona = true;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ya se encuentra un usuario registrado con ese número de identidad y contraseña", "Ya se encuentra un usuario registrado con ese número de identidad y contraseña"));
			}
			else
			{
				if (per.getTelefonoHojaVida() == null || per.getTelefonoHojaVida().equals(""))
				{
					per.setId(new IdPersona(documentoInv, tipoDocumentoInv.getId()));
					per.setNombre1(this.primerNombre);
					per.setNombre2(this.segundoNombre);
					per.setApellido1(this.primerApellido);
					per.setApellido2(this.segundoApellido);
					per.setEmail(this.correoElectronico);
					per.setTelefono(tel_fijo);
					per.setDireccion(direccion);
					per.setTelefonoHojaVida(password1);
					// per.setGenero("S");
					// servicioGeneral.guardarObjeto(per);
					servicioPersona.actualizarPersonaFuncytca(per);
					sesion.removeAttribute("ManejadorConvocatoriaFuncytca");
				//	sesion.removeAttribute("ManejadorFormularioRegistroFuncytca");

					mostrarRegistroUsuario = false;

					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Su usuario ya ha sido creado, en estos momentos puede ingresar al registro de la convocatoria con su número de identificación y contraseña.", "Su usuario ya ha sido creado, en estos momentos puede ingresar al registro de la convocatoria con su número de identificación y contraseña."));

					// FacesContext.getCurrentInstance().getExternalContext().redirect("/pages/funcytca/convocatorias.xhtml");

				}
				else
				{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El usuario ya tiene una contraseña asignada, por favor comunicarse a la extensión 20022 de la Universidad Nacional de Colombia", "El usuario ya tiene una contraseña asignada, por favor comunicarse a la extensión 20022 de la Universidad Nacional de Colombia"));
				}

			}

		}

	}

	// public String validarAutenticacionUsuario()
	// {
	//
	// String consultaPersona =
	// "select #id e.id, #nombre1 e.nombre1, #nombre2 e.nombre2, #apellido1 e.apellido1, #apellido2 e.apellido2, #telefonoHojaVida e.telefonoHojaVida from Persona e where e.id.documento = '"
	// + documentoIngresoInv + "'";
	//
	// List<Persona> listaPersona =
	// servicioGeneral.obtenerObjetosLimitado(Persona.class, consultaPersona);
	// if (listaPersona != null && listaPersona.size() > 0)
	// {
	// Persona p = listaPersona.get(0);
	// if (usrPassword.equals(p.getTelefonoHojaVida()))
	// {
	// sesion.setAttribute("persona", p);
	//
	// try
	// {
	//
	//
	//
	// if (idConvocatoria.equals("523"))
	// {
	// FacesContext.getCurrentInstance().getExternalContext().redirect("/hermes/pages/funcytca/formularioRegistro.xhtml");
	// }
	// else
	// {
	// if (idConvocatoria.equals("524"))
	// {
	// FacesContext.getCurrentInstance().getExternalContext().redirect("/hermes/pages/funcytca/formularioRegistroConv2.xhtml");
	// }
	// else
	// {
	// if (idConvocatoria.equals("525"))
	// {
	// FacesContext.getCurrentInstance().getExternalContext().redirect("/hermes/pages/funcytca/formularioRegistroConv3.xhtml");
	// }else{
	// if(idConvocatoria.equals("524-2")){
	// FacesContext.getCurrentInstance().getExternalContext().redirect("/hermes/pages/funcytca/formularioRegistroConv2Emp.xhtml");
	// }
	// }
	// }
	// }
	//
	// }
	// catch (IOException e)
	// {
	// e.printStackTrace();
	// return "";
	// }
	// return "";
	// }
	// else
	// {
	// FacesContext.getCurrentInstance().addMessage("msgs", new
	// FacesMessage(FacesMessage.SEVERITY_ERROR,
	// "Por favor verifique la contraseña.",
	// "Por favor verifique la contraseña."));
	// return "";
	// }
	// }
	// else
	// {
	// FacesContext.getCurrentInstance().addMessage("msgs", new
	// FacesMessage(FacesMessage.SEVERITY_ERROR,
	// "No se encuentra registrado un usuario con el documento de identidad ingresado.",
	// "No se encuentra registrado un usuario con el documento de identidad ingresado."));
	// return "";
	// }
	//
	// }

	public String validarAutenticacionUsuario()
	{

		String consultaPersona = "select #id e.id, #nombre1 e.nombre1, #nombre2 e.nombre2, #apellido1 e.apellido1, #apellido2 e.apellido2, #telefonoHojaVida e.telefonoHojaVida from Persona e where e.id.documento = '" + documentoIngresoInv + "'";

		List<Persona> listaPersona = servicioGeneral.obtenerObjetosLimitado(Persona.class, consultaPersona);
		if (listaPersona != null && listaPersona.size() > 0)
		{
			Persona p = listaPersona.get(0);
			if (usrPassword.equals(p.getTelefonoHojaVida()))
			{
				sesion.setAttribute("persona", p);

				try
				{

					String consultaProyecto = "select #id e.id, #tipoActividad e.tipoActividad from Proyecto e where e.creadorId = '" + documentoIngresoInv + "' and e.modalidad.id in (523, 524, 525)";
					List<Proyecto> listaPersonaGuardar = servicioGeneral.obtenerObjetosLimitado(Proyecto.class, consultaProyecto);
					if (listaPersonaGuardar != null && listaPersonaGuardar.size() > 0)
					{
						proyectoUsuario = listaPersonaGuardar.get(0);

						if (proyectoUsuario.getTipoActividad() != null && !proyectoUsuario.getTipoActividad().equals(""))
						{
							modConvo = proyectoUsuario.getTipoActividad();
						}

						if (modConvo.equals("523"))
						{
							sesion.setAttribute("idConvocatoriaFuncytca", modConvo);
							FacesContext.getCurrentInstance().getExternalContext().redirect("/pages/funcytca/formularioRegistro.xhtml");
						}
						else
						{
							if (modConvo.equals("524"))
							{
								sesion.setAttribute("idConvocatoriaFuncytca", modConvo);
								FacesContext.getCurrentInstance().getExternalContext().redirect("/pages/funcytca/formularioRegistroConv2.xhtml");
							}
							else
							{
								if (modConvo.equals("525"))
								{
									sesion.setAttribute("idConvocatoriaFuncytca", modConvo);
									FacesContext.getCurrentInstance().getExternalContext().redirect("/pages/funcytca/formularioRegistroConv3.xhtml");
								}
								else
								{
									if (modConvo.equals("524-2"))
									{
										sesion.setAttribute("idConvocatoriaFuncytca", modConvo);
										FacesContext.getCurrentInstance().getExternalContext().redirect("/pages/funcytca/formularioRegistroConv2Emp.xhtml");
									}
								}
							}
						}

					}
					else
					{
					//	String idConv = (String) sesion.getAttribute("idConvocatoriaFuncytca");
						if (idConvocatoria.equals("523"))
						{
							FacesContext.getCurrentInstance().getExternalContext().redirect("/pages/funcytca/formularioRegistro.xhtml");
						}
						else
						{
							if (idConvocatoria.equals("524"))
							{
								FacesContext.getCurrentInstance().getExternalContext().redirect("/pages/funcytca/formularioRegistroConv2.xhtml");
							}
							else
							{
								if (idConvocatoria.equals("525"))
								{
									FacesContext.getCurrentInstance().getExternalContext().redirect("/pages/funcytca/formularioRegistroConv3.xhtml");
								}
								else
								{
									if (idConvocatoria.equals("524-2"))
									{
										FacesContext.getCurrentInstance().getExternalContext().redirect("/pages/funcytca/formularioRegistroConv2Emp.xhtml");
									}
								}
							}
						}

					}

				}
				catch (IOException e)
				{
					e.printStackTrace();
					return "";
				}
				return "";
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Por favor verifique la contraseña.", "Por favor verifique la contraseña."));
				return "";
			}
		}
		else
		{
			FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se encuentra registrado un usuario con el documento de identidad ingresado.", "No se encuentra registrado un usuario con el documento de identidad ingresado."));
			return "";
		}

	}

	public void cancelarCreacionUsuario()
	{
		sesion.removeAttribute("ManejadorConvocatoriaFuncytca");
		sesion.removeAttribute("ManejadorFormularioRegistroFuncytca");
		try
		{
			FacesContext.getCurrentInstance().getExternalContext().redirect("/pages/funcytca/convocatorias.xhtml");
		}
		catch (IOException e)
		{
			
			e.printStackTrace();
		}
	}

	public void cancelarRecordarClave()
	{
		sesion.removeAttribute("ManejadorConvocatoriaFuncytca");
	}

	@Override
	protected void cargarValoresIniciales()
	{

		sesion.removeAttribute("ManejadorConvocatoriaFuncytca");

		tipoDocumentoInv = new TipoDocumento();

		listaTipoDocumento = servicioGeneral.obtenerListaObjetos(TipoDocumento.class);

		TipoDocumento tdAux = listaTipoDocumento.get(0);

		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];

		for (int i = 0; i < listaTipoDocumento.size(); i++)
		{
			TipoDocumento td = listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
		}

		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
		for (int i = 0; i < listaTipoDocumento.size(); i++)
		{
			TipoDocumento td = listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
		}

	}

	@Override
	public String atras()
	{
		
		return null;
	}

	@Override
	public String salir()
	{
		
		return null;
	}

	@Override
	public String salirGuardar()
	{
		
		return null;
	}

	@Override
	public String siguiente()
	{
		
		return null;
	}

	public String getUsrPassword()
	{
		return usrPassword;
	}

	public void setUsrPassword(String usrPassword)
	{
		this.usrPassword = usrPassword;
	}

	public String getOpcionesUsuario()
	{
		return opcionesUsuario;
	}

	public void setOpcionesUsuario(String opcionesUsuario)
	{
		this.opcionesUsuario = opcionesUsuario;
	}

	public boolean isMostrarRegistroUsuario()
	{
		return mostrarRegistroUsuario;
	}

	public void setMostrarRegistroUsuario(boolean mostrarRegistroUsuario)
	{
		this.mostrarRegistroUsuario = mostrarRegistroUsuario;
	}

	public boolean isMostrarRecordarClave()
	{
		return mostrarRecordarClave;
	}

	public void setMostrarRecordarClave(boolean mostrarRecordarClave)
	{
		this.mostrarRecordarClave = mostrarRecordarClave;
	}

	public TipoDocumento getTipoDocumentoInv()
	{
		return tipoDocumentoInv;
	}

	public void setTipoDocumentoInv(TipoDocumento tipoDocumentoInv)
	{
		this.tipoDocumentoInv = tipoDocumentoInv;
	}

	public SelectItem[] getTipoDocumentoItem()
	{
		return tipoDocumentoItem;
	}

	public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem)
	{
		this.tipoDocumentoItem = tipoDocumentoItem;
	}

	public List<TipoDocumento> getListaTipoDocumento()
	{
		return listaTipoDocumento;
	}

	public void setListaTipoDocumento(List<TipoDocumento> listaTipoDocumento)
	{
		this.listaTipoDocumento = listaTipoDocumento;
	}

	public String getDocumentoInv()
	{
		return documentoInv;
	}

	public void setDocumentoInv(String documentoInv)
	{
		this.documentoInv = documentoInv;
	}

	public String getPrimerNombre()
	{
		return primerNombre;
	}

	public void setPrimerNombre(String primerNombre)
	{
		this.primerNombre = primerNombre;
	}

	public String getSegundoNombre()
	{
		return segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre)
	{
		this.segundoNombre = segundoNombre;
	}

	public String getPrimerApellido()
	{
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido)
	{
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido()
	{
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido)
	{
		this.segundoApellido = segundoApellido;
	}

	public String getCorreoElectronico()
	{
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico)
	{
		this.correoElectronico = correoElectronico;
	}

	public String getPassword1()
	{
		return password1;
	}

	public void setPassword1(String password1)
	{
		this.password1 = password1;
	}

	public String getPassword2()
	{
		return password2;
	}

	public void setPassword2(String password2)
	{
		this.password2 = password2;
	}

	public boolean isDeshabilitarBotonLogin()
	{
		return deshabilitarBotonLogin;
	}

	public void setDeshabilitarBotonLogin(boolean deshabilitarBotonLogin)
	{
		this.deshabilitarBotonLogin = deshabilitarBotonLogin;
	}

	public String getDocumentoIngresoInv()
	{
		return documentoIngresoInv;
	}

	public void setDocumentoIngresoInv(String documentoIngresoInv)
	{
		this.documentoIngresoInv = documentoIngresoInv;
	}

	public String getDireccion()
	{
		return direccion;
	}

	public void setDireccion(String direccion)
	{
		this.direccion = direccion;
	}

	public String getTel_fijo()
	{
		return tel_fijo;
	}

	public void setTel_fijo(String tel_fijo)
	{
		this.tel_fijo = tel_fijo;
	}

	public String getIdConvocatoria()
	{
		return idConvocatoria;
	}

	public void setIdConvocatoria(String idConvocatoria)
	{
		this.idConvocatoria = idConvocatoria;
	}

	public Proyecto getProyectoUsuario()
	{
		return proyectoUsuario;
	}

	public void setProyectoUsuario(Proyecto proyectoUsuario)
	{
		this.proyectoUsuario = proyectoUsuario;
	}

	public String getMensajeConvocatoria()
	{
		return mensajeConvocatoria;
	}

	public void setMensajeConvocatoria(String mensajeConvocatoria)
	{
		this.mensajeConvocatoria = mensajeConvocatoria;
	}

	public String getModConvo()
	{
		return modConvo;
	}

	public void setModConvo(String modConvo)
	{
		this.modConvo = modConvo;
	}

	public String getMensajeUsuariocreado()
	{
		return mensajeUsuariocreado;
	}

	public void setMensajeUsuariocreado(String mensajeUsuariocreado)
	{
		this.mensajeUsuariocreado = mensajeUsuariocreado;
	}

	public boolean isDeshabilitarGuardarPersona()
	{
		return deshabilitarGuardarPersona;
	}

	public void setDeshabilitarGuardarPersona(boolean deshabilitarGuardarPersona)
	{
		this.deshabilitarGuardarPersona = deshabilitarGuardarPersona;
	}

}
