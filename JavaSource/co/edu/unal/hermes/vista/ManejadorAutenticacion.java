package co.edu.unal.hermes.vista;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.myfaces.custom.navmenu.NavigationMenuItem;
import org.hibernate.HibernateException;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PersonaAccesoHermes;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.Rol;
import co.edu.unal.hermes.modelo.Servicio;
import co.edu.unal.hermes.modelo.TipoVinculacion;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.seguridad.Seguridad;
import co.edu.unal.hermes.utils.Navegacion;

/**
 * @author Ing Hernán Darío Bernal Parra e Ing Juan Pablo Duque
 */

public class ManejadorAutenticacion extends ManejadorBase implements Serializable {

	private static final long serialVersionUID = 6350800385812440157L;
	private String login;
	private String password;
	private Seguridad seguridad;
	private boolean esInvestigador;
	private boolean esTienePermisoMarco;
	private boolean esDireccion;
	private boolean esFacultad;
	private boolean esEgresado;
	private boolean esVicerrectoria;
	private boolean esVicerrectoriaGrupo;
	private boolean esLaboratorios;
	private boolean esLaboratoriosSede;
	private boolean esLaboratoriosFacultad;
	private boolean esLaboratoriosDepto;
	private boolean esCoordinadorLaboratorio;
	private boolean esPersonalLaboratorio;
	private boolean esConsultaLaboratorios;
	private boolean esInventariosLaboratorios;
	private boolean esCoordinador;
	private boolean esUnidadAdministrativa;
	private boolean esUnidadAdministrativaExtension;
	private boolean esMovilidadFacultad;
	private boolean esMovilidadSede;
	private boolean esAsesor;
	private boolean esDNE;
	private boolean esCursosFormacion;
	private boolean esEvaluador;
	private boolean esOficinaExtension;
	private boolean esComunicaciones;
	private boolean esAvalDepartamento;
	private boolean esAvalUab;
	private boolean esComiteEticaPI;
	private boolean esComiteEticaSI;
	private boolean esDocumentosVice;
	private boolean esDiagnosticoSoftware;
	private boolean esEducacionContinuaFacultad;
	private boolean esEditorRevista;
	private boolean esEditorial;
	private boolean esCurador;
	private boolean esCentroExtension;
	private boolean esRequerimiento;
	private boolean esAdmRequerimiento;
	private boolean esDecano;
	private boolean esIndicadores;
	private boolean esVicerector;
	private boolean esBecadoDoctorado;
	private boolean esRevisionRenovacion;
	private boolean esInnovacion;
	private boolean esPropiedadIntelectualNacional;
	private boolean esJovenInvestigadorInformes;
	private boolean esAdmSolUsuario;
	private boolean esCrSolUsuario;
	private boolean esPropiedadIntelectual;
	private boolean esAdministradorHermes;
	private boolean esAdministradorConvocatorias;
	private boolean esConsulta;
	private boolean esCoordinadorBiodiversidad;
	private boolean esCorredorTecnologico;
	private boolean esPropiedadIntelectualSede;
	private boolean nuevaConvocatoriaExterna;
	private boolean esConsultaRequerimientos;
	private boolean esAsistenteLider;
	private boolean esEstudianteLider;
	
	private boolean esAvalDRE;
	private boolean esCoordinadorEditorial;
	private boolean esRectoria;
	private boolean esAsesorEditorial;
	private boolean esEditorialUN;
	
	private boolean esSuperUsuario;

	private final String dominioPreinscripcionCursos = "19";

	private boolean error;
	
	private Boolean esAmbienteProduccion;

	public ManejadorAutenticacion() {
		super();
		setError(false);
		nuevaConvocatoriaExterna = false;
		validarAmbienteProduccion();
	}
	
	public Boolean validarAmbienteProduccion() {
		try {
			esAmbienteProduccion = servicioGeneral.esAmbienteProduccion();
			return esAmbienteProduccion;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		}
	}

	private MenuModel simpleMenuModel;

	public String autenticarUsuario() {

		boolean noPermitirIngreso = false;

		setError(false);
		esInvestigador = false;
		setEsTienePermisoMarco(false);
		esDireccion = false;
		esFacultad = false;
		esEgresado = false;
		esVicerrectoria = false;
		esVicerrectoriaGrupo = false;
		esLaboratorios = false;
		esLaboratoriosSede = false;
		esLaboratoriosFacultad = false;
		esLaboratoriosDepto = false;
		esCoordinadorLaboratorio = false;
		esPersonalLaboratorio = false;
		esConsultaLaboratorios = false;
		esInventariosLaboratorios = false;
		esCoordinador = false;
		esUnidadAdministrativa = false;
		esUnidadAdministrativaExtension = false;
		esMovilidadFacultad = false;
		esMovilidadSede = false;
		esAsesor = false;
		esDNE = false;
		esCursosFormacion = false;
		esEvaluador = false;
		esOficinaExtension = false;
		esComunicaciones = false;
		esAvalDepartamento = false;
		esAvalUab = false;
		esComiteEticaPI = false;
		esComiteEticaSI = false;
		esDocumentosVice = false;
		esDiagnosticoSoftware = false;
		esEducacionContinuaFacultad = false;
		esEditorRevista = false;
		esEditorial = false;
		esCoordinadorEditorial = false;
		esCurador = false;
		esCentroExtension = false;
		esRequerimiento = false;
		esAdmRequerimiento = false;
		esDecano = false;
		esIndicadores = false;
		esVicerector = false;
		esBecadoDoctorado = false;
		esRevisionRenovacion = false;
		esInnovacion = false;
		esPropiedadIntelectualNacional = false;
		esJovenInvestigadorInformes = false;
		esCrSolUsuario = false;
		esAdmSolUsuario = false;
		esPropiedadIntelectual = false;
		esAdministradorConvocatorias = false;
		esAdministradorHermes = false;
		esConsulta = false;
		esCorredorTecnologico = false;
		esPropiedadIntelectualSede = false;
		esConsultaRequerimientos = false;
		esAsistenteLider = false;
		esEstudianteLider = false;
		esAsesorEditorial = false; 
		esEditorialUN = false;
		esSuperUsuario = false;
		
		setEsAvalDRE(false);
		setEsRectoria(false);

		// se borran los objetos que esten cargados en sesiones anteriores

		simpleMenuModel = new DefaultMenuModel();

		/*
		 * if (sesion != null) { Persona p = ((Persona) sesion.getAttribute("persona"));
		 * if (p != null) { String url = "pages/Proyectos/proyectosInvestigador.xhtml";
		 * FacesContext fc = FacesContext.getCurrentInstance(); try { //
		 * fc.getExternalContext().redirect(url); } catch (Exception e) {
		 * e.printStackTrace(); } } }
		 */

		borrarObjetosSesion();
		Error error = null;

		boolean isEscuelaInternacional = false;
		
		List usuariosPreinscripcionCursos = new ArrayList();
		String hql = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.id = '"
				+ dominioPreinscripcionCursos + "'";

		try {
			usuariosPreinscripcionCursos = servicioGeneral.obtenerObjetos(hql);
		} catch (Exception e) {
			System.out.println("Problemas con conexión a la base de datos.");
		}

		if (login != null && password != null) {
			login = login.trim();
			password = password.trim();
			seguridad = new Seguridad();
			System.out.println("U: "+login);
			Persona p = seguridad.permitir(login, password);
			if (p == null) {

				for (int i = 0; i < usuariosPreinscripcionCursos.size(); i++) {
					DominioDetalle dd = (DominioDetalle) usuariosPreinscripcionCursos.get(i);
					if (dd.getIdentificador().getTipo().equals(password)) {
						isEscuelaInternacional = true;
						IdPersona per = new IdPersona();
						per.setDocumento(password);
						per.setTipoDocumento("C");
						p = servicioPersona.obtenerPersona(per);
						break;
					}
				}

				if (isEscuelaInternacional) {
					personaActual = p;
					p.setApellido1(p.getApellido1().substring(0, 1).toUpperCase()
							+ p.getApellido1().substring(1, p.getApellido1().length()).toLowerCase());
					sesion.setAttribute("persona", p);
					if (StringUtils.isNotEmpty(login)) {
						sesion.setAttribute("userName", login);
					} else {
						sesion.setAttribute("userName", p.getId().getDocumento());
					}
				} else {
					error = new Error();
					error.setMensaje("No es usuario registrado");
					sesion.setAttribute("error", error);
					sesion.setAttribute("errorLogin", "Usuario no existe o contraseña incorrecta");
					return "salir";
				}

			} else {
				personaActual = p;
				if (p.getRoles() == null || p.getRoles().size() == 0) {
					error = new Error();
					error.setMensaje("No es usuario registrado");
					sesion.setAttribute("error", error);
					sesion.setAttribute("errorLogin", "Usuario no existe o contraseña incorrecta");
					return "salir";
				}
				p.setApellido1(p.getApellido1().substring(0, 1).toUpperCase()
						+ p.getApellido1().substring(1, p.getApellido1().length()).toLowerCase());
				sesion.setAttribute("persona", p);
				sesion.setAttribute("usuarioRegistrado", p.getId().getDocumento());
				if (StringUtils.isNotEmpty(login)) {
					sesion.setAttribute("userName", login);
				} else {
					sesion.setAttribute("userName", p.getId().getDocumento());
				}
				for (int i = 0; i < usuariosPreinscripcionCursos.size(); i++) {
					DominioDetalle dd = (DominioDetalle) usuariosPreinscripcionCursos.get(i);
					if (dd.getIdentificador().getTipo().equals(password)) {
						isEscuelaInternacional = true;
						break;
					}
				}
				cargarAutenticacionBiodiversidad();
			}
		} else {
			// No digito nada
			error = new Error();
			error.setMensaje("No es usuario registrado");
			sesion.setAttribute("error", error);
			sesion.setAttribute("errorLogin", "Debe ingresar los campos solicitados");
			return "salir";
		}

		// Modificado por Rodrigo Gallo
		boolean result = false;
		Iterator ite = personaActual.getRoles().iterator();
		if (personaActual.getRoles().size() == 1) {
			while (ite.hasNext()) {
				Rol rol = (Rol) ite.next();
				if (rol.getId().equals(Rol.EVALUADOR)) {
					result = true;
				}
			}
		}

		// dgbenitezc: guardar registro acceso
		if (personaActual != null) {
			PersonaAccesoHermes pah = new PersonaAccesoHermes();
			pah.setDocumento(personaActual.getId().getDocumento());
			pah.setTipoDocumento(personaActual.getId().getTipoDocumento());
			pah.setIp(obtenerIP());
			String personaUid = personaActual.getUid();
			if (personaUid != null && !personaUid.isEmpty()) {
				pah.setUsoLdap(true);
			} else {
				pah.setUsoLdap(false);
			}
			servicioGeneral.guardarObjeto(pah);
		}

		getItemRolesUno1();
		if (noPermitirIngreso) {
			return "salir";
		} else {

			if (result) {
				return "successProyectos";// "consultaInternacional"; //
				// return "successEvaluadorExterno";
			} else {
				if (isEscuelaInternacional) {
					return "preinscripcionEscuelaInternacional";
				} else {
					sesion.removeAttribute("manejadorProyectosInvestigador");
					return "successProyectos";
				}
			}
		}

	}

	private void cargarAutenticacionBiodiversidad() {
		esCoordinadorBiodiversidad = false;
		Persona personaBiodiversidad = servicioBiodiversidad.obtenerPersonaEncargadaBiodiversidadVicerrectoria();
		if (personaActual != null && personaBiodiversidad != null) {
			if (personaActual.getId().getDocumento().equals(personaBiodiversidad.getId().getDocumento())) {
				esCoordinadorBiodiversidad = true;
			}
		}
	}

	public boolean isEsMenuAdicionalBiodiversidadCoordinador() {
		if (personaActual != null) {
			if(servicioBiodiversidad.requiereMenuBiodiversidadCoordinador(personaActual)) {
				return true;
			}else {
				Persona personaControlBiodiversidad = servicioBiodiversidad.obtenerPersonaControlBiodiversidad();
				if (personaActual != null && personaControlBiodiversidad != null) {
					if (personaActual.getId().getDocumento().equals(personaControlBiodiversidad.getId().getDocumento())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void getItemRolesUno1() {

		Error error = null;
		Date fechaActual = new Date();
		Date fechaRol = new Date();

		Iterator it = personaActual.getRoles().iterator();
		if (!it.hasNext()) {
			// no tiene roles
			error = new Error();
			error.setMensaje("No tiene roles asignados");
			sesion.setAttribute("error", error);
		} else {
			while (it.hasNext()) {
				Rol r = (Rol) it.next();

				// /Items principales del menú (nivel 0) ----
				Submenu submenu = new Submenu();

				String nombre = r.getNombre().substring(0, 1).toUpperCase()
						+ r.getNombre().substring(1, r.getNombre().length()).toLowerCase();

				try {
					fechaRol = servicioPersona.obtenerFechaFinRol(personaActual.getId(), r.getId());
					if (fechaRol == null || fechaRol.equals("")) {
						fechaRol = new Date();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

				if (r.getId().equals(Rol.INVESTIGADOR)) {
					InvestigadorInterno invI = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());
					if ((fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) && (!esNulo(invI.getInterno()) && invI.getInterno().equals("S"))) {
						esInvestigador = true;
						if (invI != null) {
							if (invI.getTienePermisoMarco() != null) {
								if (invI.getTienePermisoMarco().equals("S")) {
									setEsTienePermisoMarco(true);
								}
							}
						}
					}
				} else if (r.getId().equals(Rol.DIRECCION_INVESTIGACION)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esDireccion = true;
					}
				} else if (r.getId().equals(Rol.VICEDECANATURA_INVESTIGACION)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esFacultad = true;
					}
				} else if (r.getId().equals(Rol.EGRESADO)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esEgresado = true;
					}
				} else if (r.getId().equals(Rol.VICERRECTORIA_INVESTIGACION)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esVicerrectoria = true;
					}
				} else if (r.getId().equals(Rol.AVALAR_GRUPOS)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esVicerrectoriaGrupo = true;
					}
				} else if (r.getId().equals(Rol.DIRECCION_NACIONAL_LABORATORIOS)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esLaboratorios = true;
					}
				} else if (r.getId().equals(Rol.DIRECCION_LABORATORIOS_SEDE)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esLaboratoriosSede = true;
					}
				} else if (r.getId().equals(Rol.COORDINADOR_LABORATORIO)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esCoordinadorLaboratorio = true;
					}
				} else if (r.getId().equals(Rol.COORDINADOR_TECNICO_LABORATORIO)
						|| r.getId().equals(Rol.TECNICO_LABORATORISTA)
						|| r.getId().equals(Rol.LAB_ESTUDIANTE_AUXILIAR)
						|| r.getId().equals(Rol.LAB_ESTUDIANTE_INVESTIGADOR)
						|| r.getId().equals(Rol.LAB_INVESTIGADOR_DOCENTE)
						|| r.getId().equals(Rol.LAB_PERSONAL_APOYO)
						|| r.getId().equals(Rol.DIRECTOR_CALIDAD_LABORATORIO)
						) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esPersonalLaboratorio = true;
					}
				} else if (r.getId().equals(Rol.LABORATORIOS_FACULTAD)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esLaboratoriosFacultad = true;
					}
				} else if (r.getId().equals(Rol.LABORATORIOS_DEPARTAMENTO)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esLaboratoriosDepto = true;
					}
				} else if (r.getId().equals(Rol.CONSULTA_LABORATORIOS)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esConsultaLaboratorios = true;
					}
				} else if (r.getId().equals(Rol.CONSULTA_REQUERIMIENTOS)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esConsultaRequerimientos = true;
					}
				} else if (r.getId().equals(Rol.INVENTARIOS_LABORATORIOS)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esInventariosLaboratorios = true;
					}
				} else if (r.getId().equals(Rol.COORDINADOR)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esCoordinador = true;
					}
				} else if (r.getId().equals(Rol.UNIDAD_ADMINISTRATIVA_INVESTIGACION)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esUnidadAdministrativa = true;
					}
				} else if (r.getId().equals(Rol.UNIDAD_ADMINISTRATIVA_EXTENSION)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esUnidadAdministrativaExtension = true;
					}
				} else if (r.getId().equals(Rol.MOVILIDADES_FACULTAD)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esMovilidadFacultad = true;
					}
				} else if (r.getId().equals(Rol.MOVILIDADES_SEDE)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esMovilidadSede = true;
					}
				} else if (r.getId().equals(Rol.ASESOR)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esAsesor = true;
					}
				} else if (r.getId().equals(Rol.DIR_NAL_EXTENSION)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esDNE = true;
					}
				} else if (r.getId().equals(Rol.CURSOS_FORMACION)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esCursosFormacion = true;
					}
				} else if (r.getId().equals(Rol.EVALUADOR)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esEvaluador = true;
					}
				} else if (r.getId().equals(Rol.COMUNICACIONES)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esComunicaciones = true;
					}
				} else if (r.getId().equals(Rol.OF_EXTENSION_FACULTAD)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esOficinaExtension = true;
					}
				} else if (r.getId().equals(Rol.DIRECTOR_UAB)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esAvalUab = true;
					}
				} else if (r.getId().equals(Rol.AVAL_CEPI)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esComiteEticaPI = true;
					}
				} else if (r.getId().equals(Rol.AVAL_CESI)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esComiteEticaSI = true;
					}
				} else if (r.getId().equals(Rol.AVAL_EXTENSION)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esAvalDepartamento = true;
					}
				} else if (r.getId().equals(Rol.ADMINISTRAR_DOCUMENTOS)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esDocumentosVice = true;
					}
				} else if (r.getId().equals(Rol.DIAGNOSTICO_SOFWARE)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esDiagnosticoSoftware = true;
					}
				} else if (r.getId().equals(Rol.ED_CONT_FACULTAD)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esEducacionContinuaFacultad = true;
					}
				} else if (r.getId().equals(Rol.EDITOR_REVISTA)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esEditorRevista = true;
					}
				} else if (r.getId().equals(Rol.EDITORIAL)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esEditorial = true;
					}
				} else if (r.getId().equals(Rol.COORDINADOR_EDITORIAL)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esCoordinadorEditorial = true;
					}
				} else if (r.getId().equals(Rol.CURADOR)) {
					//No se valida fecha de vencimiento puesto que no aplica.
						esCurador = true;
					
				} else if (r.getId().equals(Rol.CENTRO_EXTENSION)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esCentroExtension = true;
					}
				} else if (r.getId().equals(Rol.REQUERIMIENTO)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esRequerimiento = true;
					}
				} else if (r.getId().equals(Rol.ADM_REQUERIMIENTO)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esAdmRequerimiento = true;
					}
				} else if (r.getId().equals(Rol.DECANO)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esDecano = true;
					}
				} else if (r.getId().equals(Rol.INDICADORES)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esIndicadores = true;
					}
				} else if (r.getId().equals(Rol.VICERRECTOR)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esVicerector = true;
					}
				} else if (r.getId().equals(Rol.BECA_DOCTORADO)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esBecadoDoctorado = true;
					}
				} else if (r.getId().equals(Rol.REVISION_RENOVACION)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esRevisionRenovacion = true;
					}
				} else if (r.getId().equals(Rol.INNOVACION)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esInnovacion = true;
					}
				} else if (r.getId().equals(Rol.DIR_NAL_INNOVA_PI)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esPropiedadIntelectualNacional = true;
					}
				} else if (r.getId().equals(Rol.PROPIEDAD_INTELECTUAL)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esPropiedadIntelectual = true;
					}
				} else if (r.getId().equals(Rol.ADMINISTRADOR_CONVOCATORIAS)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esAdministradorConvocatorias = true;
					}
				} else if (r.getId().equals(Rol.CONSULTA)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esConsulta = true;
					}
				} else if (r.getId().equals(Rol.JOVEN_INVESTIGADOR)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esJovenInvestigadorInformes = true;
					}
				} else if (r.getId().equals(Rol.SOLICITUD_USUARIO)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esCrSolUsuario = true;
					}
				} else if (r.getId().equals(Rol.ADM_SOLICITUD_USUARIO)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esAdmSolUsuario = true;
					}
				} else if (r.getId().equals(Rol.CORREDOR_TECNOLOGICO_AGRO)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esCorredorTecnologico = true;
					}
				} else if (r.getId().equals(Rol.ADMINISTRADOR_HERMES)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esAdministradorHermes = true;
					}
				} else if (r.getId().equals(Rol.PROPIEDAD_INTELECTUAL_SEDE)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esPropiedadIntelectualSede = true;
					}
				} else if (r.getId().equals(Rol.ASISTENTE_LIDER)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esAsistenteLider = true;
					}
				} else if (r.getId().equals(Rol.ESTUDIANTE_LIDER)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esEstudianteLider = true;
					}
				} else if (r.getId().equals(Rol.AVAL_DRE)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						setEsAvalDRE(true);
					}
				}  else if (r.getId().equals(Rol.RECTORIA)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						setEsRectoria(true);
					}
				} else if (r.getId().equals(Rol.ASESOR_EDITORIAL)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esAsesorEditorial = true;
					}
				}else if (r.getId().equals(Rol.EDITORIAL_UN)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esEditorialUN = true;
					}
				}else if (r.getId().equals(Rol.SUPER_USUARIO)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esAsesor = true;
						esComunicaciones = true;
						esDireccion = true;
						esRevisionRenovacion = true;
						esPropiedadIntelectualNacional = true;
						esRequerimiento = true;
						esAdministradorHermes = true;
						esAvalUab = true;
						esComiteEticaPI = true;
						esComiteEticaSI = true;
						esIndicadores = true;
						esUnidadAdministrativaExtension = true;
						esAdmSolUsuario = true;
						esDocumentosVice = true;
						esMovilidadFacultad = true;
						esLaboratorios = true;
						esOficinaExtension = true;
						esMovilidadSede = true;
						esUnidadAdministrativa = true;
						esCrSolUsuario = true;
						esDNE = true;
						esJovenInvestigadorInformes = true;
						esVicerrectoria = true;
						esCentroExtension = true;
						esEditorRevista = true;
						esCursosFormacion = true;
						esCoordinador = true;
						esEducacionContinuaFacultad = true;
						esPropiedadIntelectualSede = true;
						esFacultad = true;
						esCurador = true;
						esEvaluador = true;
						esAdministradorConvocatorias = true;
						esAvalDepartamento = true;
						esInvestigador = true;
						InvestigadorInterno invI = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());
						if (invI != null) {
							if (invI.getTienePermisoMarco() != null) {
								if (invI.getTienePermisoMarco().equals("S")) {
									setEsTienePermisoMarco(true);
								}
							}
						}
						sesion.setAttribute("esEstudianteLider", false);
						setEsAvalDRE(true);
						setEsRectoria(true);
						esSuperUsuario = true;
					}
				}

				submenu.setLabel(nombre);
			}
		}
		sesion.setAttribute("esInvestigador", esInvestigador);
		sesion.setAttribute("esDireccion", esDireccion);
		sesion.setAttribute("esFacultad", esFacultad);
		sesion.setAttribute("esEgresado", esEgresado);
		sesion.setAttribute("esVicerrectoria", esVicerrectoria);
		sesion.setAttribute("esVicerrectoriaGrupo", esVicerrectoriaGrupo);
		sesion.setAttribute("esLaboratorios", esLaboratorios);
		sesion.setAttribute("esLaboratoriosSede", esLaboratoriosSede);
		sesion.setAttribute("esCoordinadorLaboratorio", esCoordinadorLaboratorio);
		sesion.setAttribute("esPersonalLaboratorio", esPersonalLaboratorio);
		sesion.setAttribute("esLaboratoriosFacultad", esLaboratoriosFacultad);
		sesion.setAttribute("esLaboratoriosDepto", esLaboratoriosDepto);
		sesion.setAttribute("esConsultaLaboratorios", esConsultaLaboratorios);
		sesion.setAttribute("esInventariosLaboratorios", esInventariosLaboratorios);
		sesion.setAttribute("esCoordinador2", esCoordinador);
		sesion.setAttribute("esUnidadAdministrativa", esUnidadAdministrativa);
		sesion.setAttribute("esUnidadAdministrativaExtension", esUnidadAdministrativaExtension);
		sesion.setAttribute("esMovilidadFacultad", esMovilidadFacultad);
		sesion.setAttribute("esMovilidadSede", esMovilidadSede);
		sesion.setAttribute("esAsesor", esAsesor);
		sesion.setAttribute("esDNE", esDNE);
		sesion.setAttribute("esCursosFormacion", esCursosFormacion);
		sesion.setAttribute("esEvaluador", esEvaluador);
		sesion.setAttribute("esComunicaciones", esComunicaciones);
		sesion.setAttribute("esOficinaExtension", esOficinaExtension);
		sesion.setAttribute("esAvalDepartamento", esAvalDepartamento);
		sesion.setAttribute("esAvalUab", esAvalUab);
		sesion.setAttribute("esComiteEticaPI", esComiteEticaPI);
		sesion.setAttribute("esComiteEticaSI", esComiteEticaSI);
		sesion.setAttribute("esDocumentosVice", esDocumentosVice);
		sesion.setAttribute("esDiagnosticoSoftware", esDiagnosticoSoftware);
		sesion.setAttribute("esEducacionContinuaFacultad", esEducacionContinuaFacultad);
		sesion.setAttribute("esEditorRevista", esEditorRevista);
		sesion.setAttribute("esEditorial", esEditorial);
		sesion.setAttribute("esCoordinadorEditorial", esCoordinadorEditorial);
		sesion.setAttribute("esCurador", esCurador);
		sesion.setAttribute("esCentroExtension", esCentroExtension);
		sesion.setAttribute("esRequerimiento", esRequerimiento);
		sesion.setAttribute("esAdmRequerimiento", esAdmRequerimiento);
		sesion.setAttribute("esDecano", esDecano);
		sesion.setAttribute("esIndicadores", esIndicadores);
		sesion.setAttribute("esVicerector", esVicerector);
		sesion.setAttribute("esBecadoDoctorado", esBecadoDoctorado);
		sesion.setAttribute("esRevisionRenovacion", esRevisionRenovacion);
		sesion.setAttribute("esInnovacion", esInnovacion);
		sesion.setAttribute("esPropiedadIntelectualNacional", esPropiedadIntelectualNacional);
		sesion.setAttribute("esJovenInvestigadorInformes", esJovenInvestigadorInformes);
		sesion.setAttribute("esCrSolUsuario", esCrSolUsuario);
		sesion.setAttribute("esAdmSolUsuario", esAdmSolUsuario);
		sesion.setAttribute("esPropiedadIntelectual", esPropiedadIntelectual);
		sesion.setAttribute("esAdministradorConvocatorias", esAdministradorConvocatorias);
		sesion.setAttribute("esAdministradorHermes", esAdministradorHermes);
		sesion.setAttribute("esConsulta", esConsulta);
		sesion.setAttribute("esCorredorTecnologico", esCorredorTecnologico);
		sesion.setAttribute("esPropiedadIntelectualSede", esPropiedadIntelectualSede);
		sesion.setAttribute("esConsultaRequerimientos", esConsultaRequerimientos);
		sesion.setAttribute("esAsistenteLider", esAsistenteLider);
		sesion.setAttribute("esEstudianteLider", esEstudianteLider);
		
		sesion.setAttribute("esAvalDRE", isEsAvalDRE());
		sesion.setAttribute("esRectoria", isEsRectoria());
		sesion.setAttribute("esAsesorEditorial", esAsesorEditorial);
		sesion.setAttribute("esEditorialUN", esEditorialUN);
		sesion.setAttribute("esSuperUsuario", esSuperUsuario);

	}

	public void setAction(MenuItem menuitem, String method_action) {
		if (method_action != null && method_action.length() > 0) {
			// menuitem.setAjax(false);
			FacesContext facesCtx = FacesContext.getCurrentInstance();
			ELContext elCtx = facesCtx.getELContext();
			ExpressionFactory expFact = facesCtx.getApplication().getExpressionFactory();

			menuitem.setActionExpression(
					expFact.createMethodExpression(elCtx, method_action, String.class, new Class[0]));
		}
	}

	public void adicionarItemMenuConServicioDado(List lista, Servicio s) {
		List listaHijosServicio = servicioGeneral.obtenerServiciosXPadre(s);
		if (listaHijosServicio != null && listaHijosServicio.size() > 0) {
			for (Iterator i = listaHijosServicio.iterator(); i.hasNext();) {
				Servicio servicio = (Servicio) i.next();
				ServicioRolVista srv = new ServicioRolVista();

				srv.setServicio(servicio);
				srv.setMenu(getMenuNavigationItem(servicio.getNombre(), servicio.getUrl()));
				lista.add(srv);
				adicionarItemMenuConServicioDado(srv.getListaHijosServicios(), servicio);
			}

		}

	}

	public void vuelveAMenuItem(NavigationMenuItem menu, List listaHijosServicio) {

		if (listaHijosServicio != null && listaHijosServicio.size() > 0) {
			for (Iterator i = listaHijosServicio.iterator(); i.hasNext();) {
				ServicioRolVista servicio = (ServicioRolVista) i.next();

				menu.add(servicio.getMenu());

				vuelveAMenuItem(servicio.getMenu(), servicio.getListaHijosServicios());
			}

		}

	}

	public String consultarPaginaSalida() {

		sesion.removeAttribute("persona");
		sesion.invalidate();
		irPaginaInicial();
		return null;
	}

	private void irPaginaInicial() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext extContext = context.getExternalContext();
		String viewId = "/";
		try {
			viewId = extContext.getRequestContextPath() + viewId;
			String urlLink = context.getExternalContext().encodeActionURL(viewId);
			extContext.redirect(urlLink);
		} catch (IOException e) {
			extContext.log(getClass().getName() + ".invokeRedirect", e);
		}
	}

	/*
	 * //ESTA ES LA FUNCION ORIGINAL PARA CARGA DEL MENU ANTIGUA public List
	 * getItemRoles(){
	 * 
	 * Error error = null; List menu = new ArrayList(); Iterator it =
	 * personaActual.getRoles().iterator(); if(!it.hasNext()){ // no tiene roles
	 * error = new Error(); error.setMensaje("No tiene roles asignados");
	 * sesion.setAttribute("error", error); return null; } else{
	 * while(it.hasNext()){ Rol r=(Rol)it.next(); NavigationMenuItem serv =
	 * getMenuNavigationItem("SERVICIOS PARA "+r.getNombre(),null);
	 * menu.add(serv); Iterator it2=r.getServicios().iterator();
	 * while(it2.hasNext()){ Servicio s=(Servicio)it2.next();
	 * serv.add(getMenuNavigationItem(s.getNombre(),s.getUrl())); } }
	 * menu.add(getMenuNavigationItem("Salir",
	 * "#{manejadorAutenticacion.salir}")); } return menu; }
	 */

	// ESTA FUNCION ESTA MODIFICADA SOLAMENTE PAAR QUE EN PRODUCCION LAS
	// PERSONAS PUEDAN ENTRAR
	// A MODIFICAR LA INFORMACION DE SUS GRUPOS DE INVESTIGACION
	/*
	 * public List getItemRoles() { Error error = null; List menu = new
	 * ArrayList(); Iterator it = personaActual.getRoles().iterator();
	 * if(!it.hasNext()){ // no tiene roles error = new Error();
	 * error.setMensaje("No tiene roles asignados");
	 * sesion.setAttribute("error", error); return null; } else{
	 * while(it.hasNext()){ Rol r=(Rol)it.next(); if(r.getId().equals("I")){
	 * NavigationMenuItem serv = getMenuNavigationItem("SERVICIOS PARA "
	 * +r.getNombre(),null); menu.add(serv); Iterator
	 * it2=r.getServicios().iterator(); while(it2.hasNext()){ Servicio
	 * s=(Servicio)it2.next(); if(s.getId().equals("9")){
	 * serv.add(getMenuNavigationItem(s.getNombre(),s.getUrl())); } } }else
	 * if(r.getId().equals("A")){ NavigationMenuItem serv =
	 * getMenuNavigationItem("SERVICIOS PARA "+r.getNombre(),null);
	 * menu.add(serv); Iterator it2=r.getServicios().iterator();
	 * while(it2.hasNext()){ Servicio s=(Servicio)it2.next();
	 * serv.add(getMenuNavigationItem(s.getNombre(),s.getUrl())); } }
	 * 
	 * } menu.add(getMenuNavigationItem("Salir",
	 * "#{manejadorAutenticacion.salir}")); } return menu; }
	 */

	public String salir() {
		sesion.removeAttribute("persona");
		sesion.invalidate();
		return "salir";
	}

	public String inicio() {
		sesion.removeAttribute("ManejadorListaSolicitudCreacionLab");
		sesion.removeAttribute("manejadorAdministrarGruposVicerrectoria");
		sesion.removeAttribute("manejadorSemillerosCoordinador");
		return "proyectosInvestigador";
	}

	// Funcion que borra todos los objetos de sesion menos el de autenticación
	private void borrarObjetosSesion() {
		if (sesion != null) {
			Enumeration e = sesion.getAttributeNames();
			while (e.hasMoreElements()) {
				String objeto = (String) e.nextElement();
				if (!objeto.equals("manejadorAutenticacion")) {
					sesion.removeAttribute(objeto);
				}
			}
		}
	}

	public MenuModel getSimpleMenuModel() {
		return simpleMenuModel;
	}

	public void setSimpleMenuModel(MenuModel simpleMenuModel) {
		this.simpleMenuModel = simpleMenuModel;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Seguridad getSeguridad() {
		return seguridad;
	}

	public void setSeguridad(Seguridad seguridad) {
		this.seguridad = seguridad;
	}

	private static NavigationMenuItem getMenuNavigationItem(String label, String action) {

		NavigationMenuItem item = new NavigationMenuItem(label, action);
		item.setValue(label);
		return item;
	}

	public boolean getVisibleErrorLogin() {
		String error = null;
		if (sesion != null) {
			Persona p = ((Persona) sesion.getAttribute("persona"));
			if (p != null) {
				String url = "pages/Proyectos/proyectosInvestigador.xhtml";
				FacesContext fc = FacesContext.getCurrentInstance();
				try {
					// fc.getExternalContext().redirect(url);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			error = (String) sesion.getAttribute("errorLogin");
		}
		if (error != null) {
			if (error.length() > 0)
				return true;
		}
		return false;
	}

	public String getErrorLogin() {

		String error = (String) sesion.getAttribute("errorLogin");
		sesion.removeAttribute("errorLogin");
		return error;
	}

	public String getNombrePersona() {
		Persona p = ((Persona) sesion.getAttribute("persona"));
		String nombre2 = "";
		String nombre1 = "";
		String apellido1 = "";
		if (p != null) {
			if (!p.getNombre1().equals("")) {
				nombre1 = p.getNombre1().substring(0, 1).toUpperCase()
						+ p.getNombre1().substring(1, p.getNombre1().length()).toLowerCase();
			}
			if (!p.getNombre2().equals("")) {
				nombre2 = p.getNombre2().substring(0, 1).toUpperCase()
						+ p.getNombre2().substring(1, p.getNombre2().length()).toLowerCase();
			}
			if (!p.getApellido1().equals("")) {
				apellido1 = p.getApellido1().substring(0, 1).toUpperCase()
						+ p.getApellido1().substring(1, p.getApellido1().length()).toLowerCase();
			}
			return nombre1 + " " + nombre2 + " " + apellido1;
		} else {
			error = true;
		}
		return "";
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public boolean isError() {
		error = false;
		if (sesion != null) {
			Persona p = ((Persona) sesion.getAttribute("persona"));
			if (p == null) {
				error = true;
			}
		} else
			error = true;
		if (error == true) {
		}
		return error;
	}

	public String obtenerIP() {
		String ip = "";
		try {
			Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
			if (request instanceof HttpServletRequest) {
				ip = ((HttpServletRequest) request).getRemoteAddr();
			} else {
				ip = "request not instanceof HttpServletRequest";
			}
		} catch (Exception e) {
			ip = "error";
			System.out.println("obtenerIP error:");
			e.printStackTrace();
		}

		if (ip.length() > 64) {
			ip = ip.substring(0, 64);
		}

		return ip;
	}

	public String obtenerDireccion() {
		Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (request instanceof HttpServletRequest) {
			return ((HttpServletRequest) request).getRequestURL().toString();
		} else {
			return "";
		}
	}

	public boolean getMostrarGrupos() {
		String direccion = obtenerDireccion();
		boolean esCoordinador = false;
		if (sesion.getAttribute("esCoordinadorGrupos") != null) {
			esCoordinador = (Boolean) sesion.getAttribute("esCoordinadorGrupos");
		}
		
		if(esEstudianteLider || esAsistenteLider)
			return false;
		
		if (direccion.indexOf("/pages/Grupos/") != -1 && !esCoordinador
				&& direccion.indexOf("Grupos/AdministrarGrupos.xhtml") == -1) {			
			return true;
		} else
			return false;
	}

	public boolean getMostrarLaboratorios() {
	
		String direccion = obtenerDireccion();
		
		Boolean esBusquedaEquipos;
		if(!esNulo(sesion.getAttribute("esBusquedaEquiposServicioInvestigador")))
			esBusquedaEquipos = (Boolean) sesion.getAttribute("esBusquedaEquiposServicioInvestigador");
		else
			esBusquedaEquipos = false;
		
		sesion.setAttribute("esBusquedaEquiposServicioInvestigador", false);
		
		if ((direccion.indexOf("/pages/laboratorios/") != -1) && !esBusquedaEquipos) {
			return true;
		} else
			return false;
	}

	public boolean getMostrarEducacionContinuaFacultad() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("/pages/PEC/oficinaExtension/AdministrarModuloECP") != -1) {
			return true;
		} else
			return false;
	}

	public boolean getMostrarConsultarModuloECP() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("/pages/PEC/oficinaExtension/ConsultarModuloECP") != -1) {
			return true;
		} else
			return false;
	}

	public boolean getMostrarOficinaExtension() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("/pages/ConvocatoriasExtension/ConsultarConvocatoriaExtension.xhtml") != -1) {
			return true;
		} else
			return false;
	}

	public boolean getMostrarAdministradorModuloECP() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("/pages/PEC/oficinaExtension/AdministrarModuloECP.xhtml") != -1) {
			return true;
		} else
			return false;
	}

	public boolean getMostrarOpcionesTerminosReferenciaConvocatoria() {
		String direccion = obtenerDireccion();
		if (direccion
				.indexOf("/pages/Convocatorias/convocatoriasInternas/administrarTRConvocatoriasInternas.xhtml") != -1) {
			return true;
		} else
			return false;
	}

	public boolean getMostrarOpcionesEditarCursosECP() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("/pages/PEC/oficinaExtension/editarCursoECP.xhtml") != -1) {
			return true;
		} else
			return false;
	}

	public boolean getMostrarOpcionesListaCursosECP() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("/pages/PEC/oficinaExtension/listaCursosECP.xhtml") != -1) {
			return true;
		} else
			return false;
	}

	public boolean getMostrarOpcionesListaInscritosCursoECP() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("/pages/PEC/ListaPreinscritosECP.xhtml") != -1) {
			return true;
		} else
			return false;
	}

	public boolean getMostrarOpcionesVolverCursosAsistente() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("/pages/PEC/asistente/FormalizarCurso.xhtml") != -1) {
			return true;
		} else
			return false;
	}

	public boolean getMostrarOpcionesEditorial() {
		/*String direccion = obtenerDireccion();
		if ((direccion.indexOf("/pages/Editorial/ConsultarConvocatoriasEditorial.xhtml") != -1
				|| direccion.indexOf("/pages/Editorial/proyectosInvestigadorProyectoEditorial.xhtml") != -1)
				&& esEditorial) {
			return true;
		} else*/
			return false;
	}

	public boolean getMostrarOpcionesProyectosEditorial() {
		String direccion = obtenerDireccion();
		if ((direccion.indexOf("/pages/Editorial/proyectos/home.xhtml") != -1
				|| direccion.indexOf("/pages/Editorial/proyectos/registro.xhtml") != -1) && esEditorial) {
			return true;
		} else
			return false;
	}

	public boolean getMostrarOpcionesEvaluador() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("/pages/Evaluadores/proyectosEvaluador.xhtml") != -1
				|| direccion.indexOf("pages/Evaluadores/consultarEvaluaciones.xhtml") != -1
				|| direccion.indexOf("pages/Evaluadores/certificadoEvaluacion.xhtml") != -1
				|| direccion.indexOf("pages/Evaluadores/consultarEvaluacionProyecto.xhtml") != -1) {
			return true;
		} else
			return false;
	}

	public boolean getMostrarAvales() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("Proyectos/proyectosInvestigadorAval.xhtml") != -1) {
			nuevaConvocatoriaExterna = false;
			return true;
		} else if (direccion.indexOf("pages/aval/solicitudAvalHome.xhtml") != -1) {
			nuevaConvocatoriaExterna = true;
			return true;
		} else
			return false;
	}
	
	public boolean getMostrarEditorialDocente() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/Editorial/proyectos/home.xhtml") != -1
				|| direccion.indexOf("pages/Editorial/proyectos/registro.xhtml") != -1
				|| direccion.indexOf("pages/Editorial/ConsultarConvocatoriasEditorial.xhtml") != -1
				|| direccion.indexOf("pages/Editorial/proyectosInvestigadorProyectoEditorial.xhtml") != -1) {
			return true;
		} else 
			return false;
	}

	public boolean getMostrarRequerimientos() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/Requerimiento/ConsultarInconvAsignados.xhtml") != -1
				|| direccion.indexOf("pages/Requerimiento/consultarTodosReqAsignados.xhtml") != -1
				|| direccion.indexOf("pages/Requerimiento/ConsultarSolucionados.xhtml") != -1
				|| direccion.indexOf("pages/Requerimiento/EditarRequerimientoAsignado.xhtml") != -1
				|| direccion.indexOf("pages/Requerimiento/ConsultarRequerimientoPorId.xhtml") != -1
				|| direccion.indexOf("pages/Requerimiento/EditarInconvenienteAsignado.xhtml") != -1
				|| direccion.indexOf("pages/Requerimiento/ConsultarInconvenienteAsignado.xhtml") != -1
				|| direccion.indexOf("pages/Requerimiento/EditarRequerimientoSolucionadoAsignado.xhtml") != -1
				|| direccion.indexOf("pages/Requerimiento/ConsultarRequerimientoSolucionadoAsignado.xhtml") != -1
				|| direccion.indexOf("pages/Requerimiento/ConsultarRequerimientoUsuario.xhtml") != -1) {
			return true;
		} else
			return false;
	}

	public boolean getMostrarAdmRequerimientos() {
		String direccion = obtenerDireccion();

		if (direccion.indexOf("pages/Requerimiento/consultarTodosRequerimientos.xhtml") != -1
				|| direccion.indexOf("pages/Requerimiento/ConsultarTodosReqPorRecurso.xhtml") != -1
				|| direccion.indexOf("pages/Requerimiento/ConsultarAsignadosOSolucionados.xhtml") != -1
				|| direccion.indexOf("pages/Requerimiento/consultarTodosInconvenientes.xhtml") != -1
				|| direccion.indexOf("pages/Requerimiento/ConsultarTodosInconvAsignados.xhtml") != -1
				|| direccion.indexOf("pages/Requerimiento/ConsultarTodosInconvAsignadosOSolucionados.xhtml") != -1
				|| direccion.indexOf("pages/Requerimiento/ConsultarEncuesta.xhtml") != -1
				|| direccion.indexOf("pages/Requerimiento/ReporteRequerimientos.xhtml") != -1
				|| direccion.indexOf("pages/Requerimiento/EditarRequerimientoCoord.xhtml") != -1
				|| direccion.indexOf("pages/Requerimiento/ConsultarRequerimientoCoord.xhtml") != -1
				|| direccion.indexOf("pages/Requerimiento/EditarInconvenienteCoord.xhtml") != -1
				|| direccion.indexOf("pages/Requerimiento/ConsultarInconvenienteCoord.xhtml") != -1
				|| direccion.indexOf("pages/html/ErrorDefectoAdm.xhtml") != -1) {
			return true;
		} else
			return false;
	}

	public boolean getMostrarMovilidadesFacultad() {
		String direccion = obtenerDireccion();

		if (direccion.indexOf("pages/Movilidad/aprobacion/ListadoRevision.xhtml") != -1
				|| direccion.indexOf("pages/Movilidad/aprobacion/ListadoRevisionCons.xhtml") != -1
				|| direccion.indexOf("pages/Movilidad/ListarMovilidadVisitantesArtes.xhtml") != -1
				|| direccion.indexOf("pages/Movilidad/ListarMovilidadVisitantesArtes2.xhtml") != -1
				|| direccion.indexOf("pages/Movilidad/aprobacion/ListadoRevisionSeguimiento.xhtml") != -1) {
			return true;
		}
		return false;
	}

	public boolean getMostrarMovilidadesSede() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/Movilidad/aprobacion/ListadoRevisionSede.xhtml") != -1
				|| direccion.indexOf("pages/Movilidad/aprobacion/ListadoRevisionConsSede.xhtml") != -1
				|| direccion.indexOf("pages/Movilidad/aprobacion/ListadoRevisionSeguimientoSede.xhtml") != -1) {
			return true;
		}
		return false;
	}

	public boolean getMostrarProyectos() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("/pages/Proyectos/") != -1
				|| direccion.indexOf("pages/Seguimiento/Proyectos/solicitudesProyecto.xhtml") != -1)
			if (direccion.indexOf("/pages/Proyectos/proyectosInvestigador.xhtml") == -1)
				if (direccion.indexOf("pages/Proyectos/proyectosInvestigadorAval.xhtml") == -1)
					if (direccion.indexOf("pages/Proyectos/proyectosInvestigadorAvalExtension.xhtml") == -1)
						if (direccion.indexOf("pages/Proyectos/proyectosInvestigadorMovilidad.xhtml") == -1)
							if (direccion.indexOf("pages/Proyectos/informes/principalSolicitudRenovacion.xhtml") == -1)
								if (direccion.indexOf("pages/Proyectos/informes/avalInformeFacultad.xhtml") == -1)
									if (direccion
											.indexOf("pages/Proyectos/informes/consultarInformesJornada.xhtml") == -1)
										if (direccion
												.indexOf("pages/Proyectos/Evaluacion/resumenEvaluacion.xhtml") == -1)
											if (direccion
													.indexOf("/pages/Proyectos/informes/principalInforme.xhtml") == -1)
												if (direccion.indexOf(
														"/pages/Proyectos/informes/jovenesInformes.xhtml") == -1)
													if (direccion.indexOf(
															"/pages/Proyectos/consultarReporteProyectos.xhtml") == -1)
														if (direccion.indexOf(
																"/pages/Proyectos/informes/registrarInforme.xhtml") == -1) {
															if (sesion.getAttribute(
																	"consultaInformeCoordinador") != null) {
																if (!(Boolean) sesion
																		.getAttribute("consultaInformeCoordinador")) {
																	return true;
																}
															} else
																return true;
														} else {
															return true;
														}
		return false;
	}

	public boolean getMostrarFacultad() {
		String direccion = obtenerDireccion();
		Boolean esRolRevisionAvalEtico = (Boolean) sesion.getAttribute("esRolRevisionAvalEtico");
		if (direccion.indexOf("pages/aval/avalarFacultad.xhtml") != -1
				|| direccion.indexOf("pages/Proyectos/informes/avalInformeFacultad.xhtml") != -1
				|| (direccion.indexOf("pages/aval/consultarReporteAvalFac.xhtml") != -1 && !esRolRevisionAvalEtico)
				|| direccion.indexOf("pages/Semilleros/Solicitudes/Revisar/VIF.xhtml") != -1
				|| direccion.indexOf("pages/Semilleros/Solicitudes/Consultar/VIF.xhtml") != -1)
			return true;
		return false;
	}
	
	public boolean getMostrarComiteEticaAvales() {
		String direccion = obtenerDireccion();
		Boolean esRolRevisionAvalEtico = (Boolean) sesion.getAttribute("esRolRevisionAvalEtico");
		if (direccion.indexOf("pages/aval/avalarCEPI.xhtml") != -1
			|| direccion.indexOf("pages/aval/avalarDireccionGenerarCEPI.xhtml") != -1
			|| direccion.indexOf("pages/aval/avalarCESI.xhtml") != -1
			|| direccion.indexOf("pages/aval/avalarDireccionGenerarCESI.xhtml") != -1
			|| (direccion.indexOf("pages/aval/consultarReporteAvalFac.xhtml") != -1 && esRolRevisionAvalEtico)
			)
			return true;
		return false;
	}

	public boolean getMostrarAdmnistracionConvInt() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/Convocatorias/convocatoriasInternas/crearConvocatoria.xhtml") != -1)
			return true;
		return false;
	}

	public boolean getMostrarOpcionesCoordinador() {
		String direccion = obtenerDireccion();		
		if ((((direccion.indexOf("pages/Seguimiento") != -1
				|| direccion.indexOf("pages/Evaluadores/revisarRequisitos.xhtml") != -1
				|| direccion.indexOf("pages/Semilleros/Solicitudes/Coordinador.xhtml") != -1
				|| direccion.indexOf("pages/Evaluadores/aprobarRequisitos.xhtml") != -1
				|| direccion.indexOf("pages/Evaluadores/asociarEvaluadoresProyecto.xhtml") != -1
				|| direccion.indexOf("pages/Evaluadores/asignacionEstadoProyectos.xhtml") != -1
				|| direccion.indexOf("pages/Documentos/consultaPermisosCoordinador.xhtml") != -1
				|| direccion.indexOf("pages/Asesor/proyectosConvocatoria.xhtml") != -1
				|| direccion.indexOf("pages/Asesor/consultarHistoricoProyecto.xhtml") != -1
				|| direccion.indexOf("pages/Asesor/consultarHistoricoEdicionProyecto.xhtml") != -1
				|| direccion.indexOf("pages/Asesor/consultarHistoricoAsignacionProyecto.xhtml") != -1
				|| direccion.indexOf("/pages/Proyectos/informes/registrarInforme.xhtml") != -1
				|| direccion.indexOf("pages/Proyectos/Evaluacion/resumenEvaluacion.xhtml") != -1)
				|| direccion.indexOf("pages/Asesor/consultarHistoricoLegalizacionProyecto.xhtml") != -1 
				|| direccion.indexOf("pages/Proyectos/consultarReporteProyectos.xhtml") != -1 ) 
					&& direccion.indexOf("pages/Seguimiento/Proyectos/solicitudesProyecto.xhtml") == -1) && (esCoordinador || esConsulta))
			
				if (direccion.indexOf("/pages/Proyectos/informes/registrarInforme.xhtml") != -1) {
					if (sesion.getAttribute("consultaInformeCoordinador") != null) {
						if ((Boolean) sesion.getAttribute("consultaInformeCoordinador")) {
							return true;
						}
					}
				} 
				else {
				if (direccion.indexOf("pages/Seguimiento/inboxUnidadAdministrativa.xhtml") == -1) {
					return true;
				}
			}

		return false;
	}
		
		public boolean getMostrarOpcionesConsulta() {
			String direccion = obtenerDireccion();
			if (direccion.indexOf("pages/Asesor/ConsultarConvocatorias.xhtml") != -1) {
				return true;
			} else {
				return false;
			}
		}

	public boolean getMostrarOpcionesDNE() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/ConvocatoriasExtension/AdministrarConvocatoriaExtension.xhtml") != -1
				|| direccion.indexOf("pages/ConvocatoriasExtension/RegistrarConvocatoriaExtension.xhtml") != -1)
			return true;
		return false;
	}

	public boolean getMostrarOpcionesCursosFormacion() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/Extension/ListaCursosAsistente.xhtml") != -1)
			return true;
		return false;
	}

	public boolean getMostrarOpcionesVicerrectoriaInvestigacion() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/aval/avalarVice.xhtml") != -1
				|| direccion.indexOf("pages/aval/consultaAvalarVice.xhtml") != -1)
			return true;
		return false;
	}
	
	public boolean getMostrarOpcionesRectoria() {
		String direccion = obtenerDireccion();
		if ((direccion.indexOf("pages/rectoria/home.xhtml") != -1
				|| direccion.indexOf("pages/rectoria/avalesPendientes.xhtml") != -1
				|| direccion.indexOf("pages/rectoria/revisarAval.xhtml") != -1
				|| direccion.indexOf("pages/aval/ConsultarAvalxId.xhtml") != -1
				|| direccion.indexOf("pages/Asesor/CrearEditarConvocatoriaExterna.xhtml") != -1
				|| direccion.indexOf("pages/aval/consultarReporteAvalDRERectoria.xhtml") != -1) && esRectoria)
			return true;
		return false;
	}

	public boolean getMostrarOpcionesDRE() {
		String direccion = obtenerDireccion();
		if ((direccion.indexOf("pages/aval/avalarDRE.xhtml") != -1
				|| direccion.indexOf("pages/aval/consultaAvalarDRE.xhtml") != -1
				|| direccion.indexOf("pages/aval/consultarReporteAvalDRERectoria.xhtml") != -1
				|| direccion.indexOf("pages/Asesor/CrearEditarConvocatoriaExterna.xhtml") != -1
				|| direccion.indexOf("pages/aval/avalarDireccionGenerarDRE.xhtml") != -1) && esAvalDRE)
			return true;
		return false;
	}

	public boolean getMostrarOpcionesEditorialCoordinador() {
		String direccion = obtenerDireccion();
		if ((direccion.indexOf("pages/Editorial/coordinador/home.xhtml") != -1
				|| direccion.indexOf("pages/Asesor/ConsultarConvocatorias.xhtml") != -1
				|| direccion.indexOf("pages/Evaluadores/asociarEvaluadoresProyecto.xhtml") != -1
				|| direccion.indexOf("/pages/Evaluadores/revisarRequisitosEditorial.xhtml") != -1
				|| direccion.indexOf("pages/Editorial/ConsultarConvocatoriasEditorial.xhtml") != -1)
				&& esCoordinadorEditorial)
			return true;
		return false;
	}

	public boolean getMostrarOpcionesAsesor() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/Asesor/coordinadorAsesor.xhtml") != -1
				|| direccion.indexOf("pages/Evaluadores/asignacionProyectos.xhtml") != -1
				|| direccion.indexOf("pages/Asesor/asignacionCoordinadorSemillero.xhtml") != -1)
			// || direccion.indexOf("pages/Asesor/crearSolicitudUsuario.xhtml")
			// != -1
			// ||
			// direccion.indexOf("pages/Asesor/consultaTodasSolicitudesUsuarios.xhtml")
			// != -1)
			return true;
		return false;
	}

	// *******ysv
	public boolean getMostrarOpcionesCrSolUsuario() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/Asesor/crearSolicitudUsuario.xhtml") != -1
				|| direccion.indexOf("pages/Asesor/consultaTodasSolicitudesUsuarios.xhtml") != -1)
			return true;
		return false;
	}

	// admin sol usuario
	public boolean getMostrarOpcionesAdmSolUsuario() {
		String direccion = obtenerDireccion();
		if ((direccion.indexOf("pages/Asesor/ConsultarSolicitudesUsuario.xhtml") != -1)
				|| (direccion.indexOf("pages/Asesor/AdministrarSolicitudesUsuario.xhtml") != -1)
				|| (direccion.indexOf("pages/Asesor/ConsultarHistoricoSolicitudesUsuario.xhtml") != -1)
				|| (direccion.indexOf("pages/Asesor/consultaTodasSolicitudesUsuariosAdm.xhtml") != -1)
				|| (direccion.indexOf("pages/Asesor/crearSolicitudUsuarioAdm.xhtml") != -1)
				|| (direccion.indexOf("pages/Asesor/buscarUsuario.xhtml") != -1)
				|| (direccion.indexOf("pages/Asesor/VerSolicitudHistoricoUsuario.xhtml") != -1))
			return true;
		return false;
	}

	public boolean getMostrarOpcionesComunicaciones() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/Correos/enviarCorreoBoletin.xhtml") != -1
				|| direccion.indexOf("pages/Correos/enviarAlertasCompromisos.xhtml") != -1
				|| direccion.indexOf("pages/Correos/enviarAlertasActasInicio.xhtml") != -1
				|| direccion.indexOf("pages/Correos/enviarAlertasConvExternas.xhtml") != -1
				|| direccion.indexOf("pages/Correos/enviarAlertasColecciones.xhtml") != -1
				|| direccion.indexOf("pages/Consultas/historicoBusquedas.xhtml") != -1
				|| direccion.indexOf("pages/Correos/enviarAlertasMovilidades.xhtml") != -1
				|| direccion.indexOf("pages/Correos/enviarAlertasInformes.xhtml") != -1
				|| direccion.indexOf("pages/Correos/enviarAlertasInformesDocente.xhtml") != -1)
			return true;
		return false;
	}

	// ysv
	public boolean getMostrarOpcionesAdministradorConvocatorias() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/Asesor/administrarConvocatorias.xhtml") != -1
				|| direccion.indexOf("pages/Convocatorias/crearConvocatoriaPadre.xhtml") != -1
				|| direccion.indexOf("pages/Asesor/configurarCortes.xhtml") != -1)
			return true;
		return false;
	}

	public boolean getMostrarOpcionesConvocatoriasExternas() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/Asesor/administrarConvocatoriasExternas.xhtml") != -1
				|| direccion.indexOf("pages/Asesor/administrarTerminosReferencia.xhtml") != -1
				|| direccion.indexOf("pages/Asesor/CrearConsultarTerminosReferencia.xhtml") != -1) {
			return true;
		} else if (esAdministradorConvocatorias
				&& direccion.indexOf("pages/Asesor/CrearEditarConvocatoriaExterna.xhtml") != -1) {
			return true;
		} else {
			return false;
		}
	}

	public boolean getMostrarOpcionesAdministradorHermes() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/Administrador/administrarPrincipal.xhtml") != -1
				|| direccion.indexOf("pages/Administrador/administrarFuentesFinanciacion.xhtml") != -1
				|| direccion.indexOf("pages/Administrador/revisarSolicitudFuente.xhtml") != -1
				|| direccion.indexOf("pages/Administrador/consultarTodasSolicitudesFuentes.xhtml") != -1) {
			return true;
		} else {
			return false;
		}
	}

	public boolean getMostrarOpcionesInstructivos() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/Administrador/administrarInstructivos.xhtml") != -1
				|| direccion.indexOf("pages/Administrador/instructivos/crearEditarInstructivo.xhtml") != -1
				|| direccion
						.indexOf("pages/Administrador/instructivos/administrarClasificacionInstructivos.xhtml") != -1
				|| direccion
						.indexOf("pages/Administrador/instructivos/crearEditarClasificacionInstructivos.xhtml") != -1) {
			return true;
		} else {
			return false;
		}
	}

	public boolean getMostrarOpcionesPreguntas() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/Administrador/administrarPreguntas.xhtml") != -1
				|| direccion.indexOf("pages/Administrador/preguntas/crearEditarPreguntas.xhtml") != -1
				|| direccion.indexOf("pages/Administrador/preguntas/administrarClasificacionPreguntas.xhtml") != -1
				|| direccion.indexOf("pages/Administrador/preguntas/crearEditarClasificacionPreguntas.xhtml") != -1) {
			return true;
		} else {
			return false;
		}
	}

	public boolean getMostrarOpcionesNovedades() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/Administrador/administrarNovedades.xhtml") != -1
				|| direccion.indexOf("pages/Administrador/novedades/crearEditarNovedades.xhtml") != -1
				|| direccion.indexOf("pages/Administrador/novedades/administrarClasificacionNovedades.xhtml") != -1
				|| direccion.indexOf("pages/Administrador/novedades/crearEditarClasificacionNovedades.xhtml") != -1) {
			return true;
		} else {
			return false;
		}
	}

	public boolean getMostrarOpcionesInformacionVicerrectoria() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/Documentos/documentosVice.xhtml") != -1)
			return true;
		return false;
	}

	public boolean getMostrarOpcionesPermisoMarco() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/Documentos/convenioMarco.xhtml") != -1
				|| direccion.indexOf("pages/Documentos/consultaPermisos.xhtml") != -1)
			if (esTienePermisoMarco)
				return true;
		return false;
	}

	public boolean getMostrarMenuPermisoMarco() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/Documentos/convenioMarco.xhtml") != -1
				|| direccion.indexOf("pages/Documentos/consultaPermisos.xhtml") != -1)
			return true;
		return false;
	}

	public boolean getMostrarOpcionesUnidadAdministrativa() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/Seguimiento/inboxUnidadAdministrativa.xhtml") != -1)
			return true;
		return false;
	}

	public boolean getEsMostrarUnidadAdministrativa() {
		return esUnidadAdministrativa;
	}

	public boolean getMostrarOpcionesAvalDireccion() {
		String direccion = obtenerDireccion();
		if (esDireccion) {
			if (direccion.indexOf("pages/aval/avalarDireccion.xhtml") != -1
					|| direccion.indexOf("pages/aval/consultaAvalarFacultad.xhtml") != -1
					|| direccion.indexOf("pages/aval/consultarReporteAvalCoord.xhtml") != -1
					|| direccion.indexOf("pages/Semilleros/Solicitudes/Revisar/DI.xhtml") != -1
					|| direccion.indexOf("pages/Semilleros/Solicitudes/Consultar/DI.xhtml") != -1) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean getMostrarOpcionesActualizarAval() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/aval/ConsultarImprimirAvales.xhtml") != -1
			|| direccion.indexOf("pages/aval/ConsultarAvalxId.xhtml") != -1
			)
			return true;
		return false;
	}

	public boolean getMostrarOpcionesOficinaExtension() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/aval/AprobacionAvalECP_UAB.xhtml") != -1
				|| direccion.indexOf("pages/aval/AprobacionAvalECP_UABIndex.xhtml") != -1
				|| direccion.indexOf("pages/aval/ConsultaAvalesECP_UAB.xhtml") != -1)
			return true;
		return false;
	}

	public boolean getMostrarOpcionesUAB() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/aval/AprobacionAvalECP_UAB.xhtml") != -1
				|| direccion.indexOf("pages/aval/AprobacionAvalECP_UABIndex.xhtml") != -1
				|| direccion.indexOf("pages/aval/ConsultaAvalesECP_UAB.xhtml") != -1
				|| direccion.indexOf("pages/Proyectos/informes/consultarInformesJornada.xhtml") != -1
				|| direccion.indexOf("pages/Semilleros/Solicitudes/Revisar/UAB.xhtml") != -1
				|| direccion.indexOf("pages/Semilleros/Solicitudes/Consultar/UAB.xhtml") != -1)
			return true;
		return false;
	}

	public boolean getMostrarOpcionesCertificados() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/certificaciones/certificacionProyectosInvestigador.xhtml") != -1
				|| direccion.indexOf("pages/certificaciones/certificacionProyectosCoinvestigadores.xhtml") != -1)
			return true;
		return false;
	}

	public boolean getMostrarOpcionesRequerimiento() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/Requerimiento/consultarRequerimiento.xhtml") != -1
				|| direccion.indexOf("pages/Requerimiento/crearRequerimiento.xhtml") != -1
				|| direccion.indexOf("pages/Requerimiento/editarRequerimiento.xhtml") != -1)
			return true;
		return false;
	}

	public boolean getMostrarOpcionesAdmRequerimiento() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/Requerimiento/consultarRequerimiento.xhtml") != -1
				|| direccion.indexOf("pages/Requerimiento/crearRequerimiento.xhtml") != -1
				|| direccion.indexOf("pages/Requerimiento/editarRequerimiento.xhtml") != -1)
			return true;
		return false;
	}

	public boolean getMostrarOpcionesInnovacion() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/BancoProblemas/ListaPreinscritosBancoProblemas.xhtml") != -1)
			return true;
		return false;
	}

	public boolean getMostrarColecciones() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/Colecciones/coleccionesBiologicas.xhtml") != -1
				|| direccion.indexOf("pages/Colecciones/gestionColecciones.xhtml") != -1) {
			return true;
		} else
			return false;
	}
	
	public boolean getMostrarOpcionesSemillerosHome() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/Semilleros/home.xhtml") != -1) {
			return true;
		} else
			return false;
	}
	
	public boolean getMostrarOpcionesSemillerosRegistro() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/Semilleros/Registro") != -1) {
			return true;
		} else
			return false;
	}
	
	public boolean getTienePermisosCrearGrupo() {
		Set<String> idsPermitidos = new HashSet<String>(Arrays.asList("16", "29", "30"));

        InvestigadorInterno invI = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());
        if (!esNulo(invI)) {
            TipoVinculacion tVinculacion = invI.getTipoVinculacion();
            if ((!esNulo(tVinculacion) && idsPermitidos.contains(tVinculacion.getId()) && !invI.getPeriodoPrueba()) || esSuperUsuario)
                return true;
        }
        return false;
	}
	
	public boolean getTienePermisosCrearSemillero() {
		Set<String> idsPermitidos = new HashSet<String>(Arrays.asList("16", "29", "30"));

        InvestigadorInterno invI = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());
        if (!esNulo(invI)) {
            TipoVinculacion tVinculacion = invI.getTipoVinculacion();
            if ((!esNulo(tVinculacion) && idsPermitidos.contains(tVinculacion.getId())) || esSuperUsuario)
                return true;
        }
        return false;
	}

	public void setEsInvestigador(boolean esInvestigador) {
		this.esInvestigador = esInvestigador;
	}

	public boolean isEsInvestigador() {
		return esInvestigador;
	}

	public boolean isEstudianteLider() {
		if ((Boolean) sesion.getAttribute("esEstudianteLider") != null) {
			boolean esEstudianteLider = (Boolean) sesion.getAttribute("esEstudianteLider");
			return esEstudianteLider;
		}
		return false;
	}

	public boolean isEsEstudianteLider() {
		return esEstudianteLider;
	}

	public void setEsEstudianteLider(boolean esEstudianteLider) {
		this.esEstudianteLider = esEstudianteLider;
	}

	/**
	 * @return the esConsultaLaboratorios
	 */
	public boolean isEsConsultaLaboratorios() {
		return esConsultaLaboratorios;
	}

	/**
	 * @return the esLaboratorios
	 */
	public boolean isEsLaboratorios() {
		return esLaboratorios;
	}

	public boolean isEsConsultaRequerimientos() {
		return esConsultaRequerimientos;
	}

	public void setEsConsultaRequerimientos(boolean esConsultaRequerimientos) {
		this.esConsultaRequerimientos = esConsultaRequerimientos;
	}

	public String cargarFichaMinima() {
		borrarManejadoresInsercionProyecto();
		sesion.setAttribute("proyecto", null);
		sesion.setAttribute("proyectoFichaMinina", null);
		consultarFichaMinima();
		return "crearfichaMinimaHome";
	}

	public String consultarFichaMinima() {
		sesion.removeAttribute("consultaFichaMinina");
		sesion.removeAttribute("esProyectoFichaMinimaNueva");
		sesion.removeAttribute("proyectoFichaMinimaNueva");
		sesion.removeAttribute("proyectoFichaMinina");
		sesion.removeAttribute("manejadorFichaMinimaHome");
		sesion.removeAttribute("idConvocatoriaActual");
		return "fichaMinimaHome";
	}

	public String cargarConvocatoriaPermisoMarco() {
		sesion.removeAttribute("manejadorAceptarTerminosReferencia");
		sesion.setAttribute("proyecto", null);
		sesion.removeAttribute("idConvocatoriaActual");

		Convocatoria convocatoriaActual;

		convocatoriaActual = (Convocatoria) servicioGeneral.obtenerObjeto(new Convocatoria(), new Long(MODALIDAD_PERMISO_MARCO_2024));

		InvestigadorInterno invI = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());

		if (!servicioProyecto.validarVinculacionPersona(invI, false)) {
			Error error = new Error();
			error.setMensaje("El tipo de vinculación del investigador no es válido.");
			sesion.setAttribute("error", error);
			return Navegacion.ERROR;
		}

		Proyecto p = new Proyecto();
		p.setModalidad(convocatoriaActual);
		sesion.setAttribute("proyecto", p);

		sesion.setAttribute("idConvocatoriaActual", convocatoriaActual.getId());

		borrarManejadoresInsercionProyecto();

		return "crearProyectoConFichaMinima";
	}

	public String cargarConvocatoriaPermisoMarcoAsignatura() {
		sesion.removeAttribute("manejadorAceptarTerminosReferencia");
		sesion.setAttribute("proyecto", null);

		Convocatoria convocatoriaActual;

		convocatoriaActual = (Convocatoria) servicioGeneral.obtenerObjeto(new Convocatoria(), new Long(MODALIDAD_PERMISO_MARCO_ASIGNATURA_2024));

		InvestigadorInterno invI = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());

		if (!servicioProyecto.validarVinculacionPersona(invI, false)) {
			Error error = new Error();
			error.setMensaje("El tipo de vinculación del investigador no es válido.");
			sesion.setAttribute("error", error);
			return Navegacion.ERROR;
		}

		Proyecto p = new Proyecto();
		p.setModalidad(convocatoriaActual);
		sesion.setAttribute("proyecto", p);

		sesion.setAttribute("idConvocatoriaActual", convocatoriaActual.getId());

		borrarManejadoresInsercionProyecto();

		return "crearProyectoConFichaMinima";
	}

	public String irAdministrarEquipos() {
		sesion.removeAttribute("manejadorHojaDeVidaEquipos");
		sesion.removeAttribute("fromEquipos");
		return "hojaDeVidaEquipos";
	}

	public String irNuevoProyectoEditorial() {
		sesion.removeAttribute("ManejadorEditorialProyectosRegistro");
		sesion.removeAttribute("Pro_Editorial_ID");
		sesion.removeAttribute("Pro_Editorial_Editar");
		return "nuevoProyectoEditorial";
	}

	public String irCrearLaboratorio() {
		sesion.removeAttribute("ManejadorLaboratoriosInformacionGeneral");
		sesion.removeAttribute("ManejadorLaboratoriosRecursoHumano");
		sesion.removeAttribute("ManejadorLaboratoriosRiesgos");
		sesion.removeAttribute("ManejadorLaboratoriosGestion");
		sesion.removeAttribute("ManejadorLaboratoriosEquipos");
		sesion.removeAttribute("ManejadorLaboratoriosInvestigacion");
		sesion.removeAttribute("ManejadorLaboratoriosProyectos");
		sesion.removeAttribute("ManejadorLaboratoriosDocencia");
		sesion.removeAttribute("ManejadorLaboratoriosEnsayosServicios");
		sesion.removeAttribute("ManejadorMenuFormularioLaboratorios");
		sesion.removeAttribute("ManejadorAdministrarLaboratorios");
		sesion.removeAttribute("manejadorHojaDeVidaEquipos");
		sesion.removeAttribute("Laboratorio");
		sesion.removeAttribute("soloLectura");
		sesion.removeAttribute("solicitudLaboratorio");
		sesion.removeAttribute("manejadorLaboratorioPresupuesto");
		return "CrearLaboratorio";
	}

	public String irReportesDNL() {
		sesion.removeAttribute("ManejadorLaboratoriosReportes");
		return "reportesDNL";
	}
	
	public String irReporteAvalComiteEtica() {
		sesion.setAttribute("esRolRevisionAvalEtico", true);
		borrarManejadoresReportesAvales();
		return "reporteAvalFacultad";
	}
	
	public String irReporteAvalFacultad() {
		sesion.setAttribute("esRolRevisionAvalEtico", false);
		borrarManejadoresReportesAvales();
		return "reporteAvalFacultad";
	}
	
	public String irReporteAvalCoord() {
		sesion.setAttribute("esRolRevisionAvalEtico", false);
		borrarManejadoresReportesAvales();
		return "reporteAvalCoord";
	}
	
	public String irReporteAvalVice() {
		sesion.setAttribute("esRolRevisionAvalEtico", false);
		borrarManejadoresReportesAvales();
		return "reporteAvalVice";
	}
	
	public String irReporteAvalDRERectoria() {
		sesion.setAttribute("esRolRevisionAvalEtico", false);
		borrarManejadoresReportesAvales();
		return "reporteAvalDRERectoria";
	}
	
	public void borrarManejadoresReportesAvales() {
	sesion.removeAttribute("manejadorAvalReporteFacultad");
	sesion.removeAttribute("manejadorReportesSede");
	sesion.removeAttribute("manejadorAvalReporteCoord");
}

	public String cargarSolicitudesRenovacionFacultad() {
		sesion.setAttribute("consultarPorFacultad", true);
		sesion.removeAttribute("manejadorConsultaRenovaciones");
		return "ConsultaPanelSolicitudes";
	}

	public String irGenerarCertificacionesInvVIF() {
		sesion.removeAttribute("ManejadorCertificaciones");
		return "certificacionesInvestigadorVIF";
	}

	public String cargarSolicitudesRenovacion() {
		sesion.removeAttribute("manejadorConsultaRenovaciones");
		return "ConsultaPanelSolicitudes";
	}

	public String cargarSolicitudesPermisoMarco() {
		sesion.removeAttribute("manejadorConsultaConvenioMarco");
		return "consultarPermisosMarcoCoordinador";
	}

	public String cargarProyectosInvestigador() {
		sesion.removeAttribute("manejadorProyectosInvestigador");
		return "successProyectosProyecto";
	}

	public String avalInstitucionalDireccion() {
		sesion.removeAttribute("manejadorSolicitarAvalDireccion");
		return "avalarDireccion";
	}

	public String cargarInformeBecas() {
		return "detalleInforme";
	}

	public boolean getMostrarRegistroPI() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/propiedadIntelectual/propiedadIntelectual.xhtml") != -1
				|| direccion.indexOf("pages/propiedadIntelectual/registroPropiedadIntelectual.xhtml") != -1) {
			return true;
		} else
			return false;
	}

	public boolean getMostrarRevisionPI() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/propiedadIntelectual/tramite/listadoRevisionPropiedadIntelectual.xhtml") != -1
				|| direccion.indexOf("pages/propiedadIntelectual/tramite/revisarPropiedadIntelectual.xhtml") != -1
				|| direccion.indexOf("pages/propiedadIntelectual/tramite/consultarPropiedadIntelectual.xhtml") != -1
				|| direccion.indexOf("pages/propiedadIntelectual/tramite/reportesPropiedadIntelectual.xhtml") != -1) {
			return true;
		} else
			return false;
	}

	public boolean getMostrarOpcionesCoordinadorEditorial() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/Editorial/coordinador/") != -1) {
			return true;
		} else
			return false;
	}
	
	public boolean getMostrarOpcionesAsesorEditorial() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/Editorial/asesor/") != -1)
			return true;
		return false;
	}
	
	public boolean getMostrarOpcionesEditorialUN() {
		String direccion = obtenerDireccion();
		if (direccion.indexOf("pages/Editorial/asesor/asesorEditorial.xhtml") != -1 && esEditorialUN)
			return true;
		return false;
	}

	/**
	 * @return the esLaboratoriosSede
	 */
	public boolean isEsLaboratoriosSede() {
		return esLaboratoriosSede;
	}

	public String cargarConvenioMarco() {
		sesion.removeAttribute("manejadorConvenioMarco");
		return "convenioMarco";
	}

	/**
	 * @return the esDiagnosticoSoftware
	 */
	public boolean isEsDiagnosticoSoftware() {
		return esDiagnosticoSoftware;
	}

	public boolean isEsEducacionContinuaFacultad() {
		return esEducacionContinuaFacultad;
	}

	public void setEsEducacionContinuaFacultad(boolean esEducacionContinuaFacultad) {
		this.esEducacionContinuaFacultad = esEducacionContinuaFacultad;
	}

	public void setEsTienePermisoMarco(boolean esTienePermisoMarco) {
		this.esTienePermisoMarco = esTienePermisoMarco;
	}

	public boolean isEsTienePermisoMarco() {
		return esTienePermisoMarco;
	}

	public boolean isEsEditorial() {
		return esEditorial;
	}

	public void setEsEditorial(boolean esEditorial) {
		this.esEditorial = esEditorial;
	}

	/**
	 * @return the esCoordinadorLaboratorio
	 */
	public boolean isEsCoordinadorLaboratorio() {
		return esCoordinadorLaboratorio;
	}

	public boolean isEsPersonalLaboratorio() {
		return esPersonalLaboratorio;
	}

	public void setEsPersonalLaboratorio(boolean esPersonalLaboratorio) {
		this.esPersonalLaboratorio = esPersonalLaboratorio;
	}

	public boolean isEsCentroExtension() {
		return esCentroExtension;
	}

	public boolean isEsRequerimiento() {
		return esRequerimiento;
	}

	public void setEsRequerimiento(boolean esRequerimiento) {
		this.esRequerimiento = esRequerimiento;
	}

	public void setEsCentroExtension(boolean esCentroExtension) {
		this.esCentroExtension = esCentroExtension;
	}

	public boolean isEsDecano() {
		return esDecano;
	}

	public boolean isEsIndicadores() {
		return esIndicadores;
	}

	public void setEsIndicadores(boolean esIndicadores) {
		this.esIndicadores = esIndicadores;
	}

	public void setEsDecano(boolean esDecano) {
		this.esDecano = esDecano;
	}

	public boolean isEsVicerector() {
		return esVicerector;
	}

	public void setEsVicerector(boolean esVicerector) {
		this.esVicerector = esVicerector;
	}

	/**
	 * @return the esInventariosLaboratorios
	 */
	public boolean isEsInventariosLaboratorios() {
		return esInventariosLaboratorios;
	}

	public boolean isEsRevisionRenovacion() {
		return esRevisionRenovacion;
	}

	public boolean isCoordinadorBiodiversidad() {
		return esCoordinadorBiodiversidad;
	}

	public void setEsRevisionRenovacion(boolean esRevisionRenovacion) {
		this.esRevisionRenovacion = esRevisionRenovacion;
	}

	public boolean isEsInnovacion() {
		return esInnovacion;
	}

	public void setEsInnovacion(boolean esInnovacion) {
		this.esInnovacion = esInnovacion;
	}

	public boolean isEsAdmSolUsuario() {
		return esAdmSolUsuario;
	}

	public void setEsAdmSolUsuario(boolean esAdmSolUsuario) {
		this.esAdmSolUsuario = esAdmSolUsuario;
	}

	public boolean isEsCrSolUsuario() {
		return esCrSolUsuario;
	}

	public void setEsCrSolUsuario(boolean esCrSolUsuario) {
		this.esCrSolUsuario = esCrSolUsuario;
	}

	/**
	 * @return the esLaboratoriosFacultad
	 */
	public boolean isEsLaboratoriosFacultad() {
		return esLaboratoriosFacultad;
	}

	/**
	 * @return the esLaboratoriosDepto
	 */
	public boolean isEsLaboratoriosDepto() {
		return esLaboratoriosDepto;
	}

	public boolean isEsAdmRequerimiento() {
		return esAdmRequerimiento;
	}

	public void setEsAdmRequerimiento(boolean esAdmRequerimiento) {
		this.esAdmRequerimiento = esAdmRequerimiento;
	}

	public String consultarPermisosMarco() {
		sesion.removeAttribute("manejadorConvenioMarco");
		return "consultarPermisosMarco";
	}

	public boolean isEsConsulta() {
		return esConsulta;
	}

	public boolean isEsUnidadAdministrativaExtension() {
		return esUnidadAdministrativaExtension;
	}

	public void setEsUnidadAdministrativaExtension(boolean esUnidadAdministrativaExtension) {
		this.esUnidadAdministrativaExtension = esUnidadAdministrativaExtension;
	}

	public boolean isEsCoordinador() {
		return esCoordinador;
	}

	public void setEsCoordinador(boolean esCoordinador) {
		this.esCoordinador = esCoordinador;
	}

	public void reportarError() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> param = context.getExternalContext().getRequestParameterMap();
		String problema = param.get("param");
		System.out.println(param.get("param"));

		HttpServletRequest origRequest = (HttpServletRequest) context.getExternalContext().getRequest();
		String url = origRequest.getRequestURL().toString();
		String port = origRequest.getSession().getId();

		Correo correoExcepcion = new Correo();
		correoExcepcion.setOrigen(Correo.CORREO_HERMES_SOLICITUDES);
		correoExcepcion.setAsunto("Problema con el aplicativo presentado a usuario");
		correoExcepcion
				.setCuerpo("El siguiente error ha ocurrido al usuario " + personaActual.getNombreCompletoMinusculas()
						+ " CC " + personaActual.getId().getDocumento() + " (" + personaActual.getEmail()
						+ ") en la vista " + url + "." + " Servidor: " + port + " \n" + " \n" + problema);

		correoExcepcion.adicionarDireccion("hermes@unal.edu.co");
		servicioCorreo.enviarCorreo(correoExcepcion);

	}
	
	public String irCrearRequerimiento() {
		sesion.removeAttribute("ManejadorCrearRequerimiento");
		return "crearRequerimiento";
	}

	public boolean isEsCorredorTecnologico() {
		return esCorredorTecnologico;
	}

	public void setEsCorredorTecnologico(boolean esCorredorTecnologico) {
		this.esCorredorTecnologico = esCorredorTecnologico;
	}

	public boolean isEsAdministradorConvocatorias() {
		return esAdministradorConvocatorias;
	}

	public void setEsAdministradorConvocatorias(boolean esAdministradorConvocatorias) {
		this.esAdministradorConvocatorias = esAdministradorConvocatorias;
	}

	public boolean isEsAdministradorHermes() {
		return esAdministradorHermes;
	}

	public void setEsAdministradorHermes(boolean esAdministradorHermes) {
		this.esAdministradorHermes = esAdministradorHermes;
	}

	public boolean isEsVicerrectoria() {
		return esVicerrectoria;
	}

	public void setEsConsulta(boolean esConsulta) {
		this.esConsulta = esConsulta;
	}

	public boolean isEsAvalUab() {
		return esAvalUab;
	}

	public void setEsAvalUab(boolean esAvalUab) {
		this.esAvalUab = esAvalUab;
	}

	public boolean isEsComiteEticaPI() {
		return esComiteEticaPI;
	}

	public void setEsComiteEticaPI(boolean esComiteEticaPI) {
		this.esComiteEticaPI = esComiteEticaPI;
	}

	public boolean isEsComiteEticaSI() {
		return esComiteEticaSI;
	}

	public void setEsComiteEticaSI(boolean esComiteEticaSI) {
		this.esComiteEticaSI = esComiteEticaSI;
	}

	public boolean isEsPropiedadIntelectualSede() {
		return esPropiedadIntelectualSede;
	}

	public void setEsPropiedadIntelectualSede(boolean esPropiedadIntelectualSede) {
		this.esPropiedadIntelectualSede = esPropiedadIntelectualSede;
	}

	public boolean isEsPropiedadIntelectualNacional() {
		return esPropiedadIntelectualNacional;
	}

	public void setEsPropiedadIntelectualNacional(boolean esPropiedadIntelectualNacional) {
		this.esPropiedadIntelectualNacional = esPropiedadIntelectualNacional;
	}

	public boolean isNuevaConvocatoriaExterna() {
		return nuevaConvocatoriaExterna;
	}

	public void setNuevaConvocatoriaExterna(boolean nuevaConvocatoriaExterna) {
		this.nuevaConvocatoriaExterna = nuevaConvocatoriaExterna;
	}

	public boolean isEsAsistenteLider() {
		return esAsistenteLider;
	}

	public void setEsAsistenteLider(boolean esAsistenteLider) {
		this.esAsistenteLider = esAsistenteLider;
	}

	public boolean isEsAvalDRE() {
		return esAvalDRE;
	}

	public void setEsAvalDRE(boolean esAvalDRE) {
		this.esAvalDRE = esAvalDRE;
	}

	public Boolean getEsAmbienteProduccion() {
		return esAmbienteProduccion;
	}

	public void setEsAmbienteProduccion(Boolean esAmbienteProduccion) {
		this.esAmbienteProduccion = esAmbienteProduccion;
	}

	public boolean isEsCoordinadorEditorial() {
		return esCoordinadorEditorial;
	}

	public void setEsCoordinadorEditorial(boolean esCoordinadorEditorial) {
		this.esCoordinadorEditorial = esCoordinadorEditorial;
	}
	public boolean isEsRectoria() {
		return esRectoria;
	}

	public void setEsRectoria(boolean esRectoria) {
		this.esRectoria = esRectoria;
	}

	public boolean isEsAsesorEditorial() {
		return esAsesorEditorial;
	}

	public void setEsAsesorEditorial(boolean esAsesorEditorial) {
		this.esAsesorEditorial = esAsesorEditorial;
	}

	public boolean isEsEditorialUN() {
		return esEditorialUN;
	}

	public void setEsEditorialUN(boolean esEditorialUN) {
		this.esEditorialUN = esEditorialUN;
	}

}
