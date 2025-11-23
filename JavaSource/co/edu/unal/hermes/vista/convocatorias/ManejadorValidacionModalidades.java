package co.edu.unal.hermes.vista.convocatorias;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.jasper.tagplugins.jstl.core.ForEach;

import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorGrupo;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.RestriccionConvocatoria;
import co.edu.unal.hermes.modelo.Semillero;
import co.edu.unal.hermes.modelo.SemilleroIntegrante;
import co.edu.unal.hermes.modelo.SemilleroProyecto;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorValidacionModalidades extends ManejadorBase {

	private static final long serialVersionUID = 6568793992687723439L;

	private Grupo grupoEscogido;

	private SelectItem[] gruposItem;

	private SelectItem[] aprobacion = { new SelectItem("NO", "NO"), new SelectItem("SI", "SI") };
	private String sAprobacion = "NO";

	private Convocatoria convocatoriaActual;
	private Persona personaActual;

	private boolean tieneGrupos;
	private boolean noTieneGrupos;
	private boolean faltanDatos;
	private boolean mostrarGrupos;
	private boolean convocatoriaSinGrupos;
	private boolean grupoObligatorio;
	private boolean esConvocatoriaAlianzas2018 = false;
	private boolean esConvocatoriaAlianzas2019 = false;
	private SelectItem[] gruposInvItem;
	private List<Grupo> listaGruposAlianzas;
	private Grupo grupoSeleccionado;

	private boolean mostrarMsjConvManMed = false;

	private String mensajeValidacion;

	private String mensajeValidacion2;
	private boolean esConvocatoriaMultigrupo = false;

	List listaGrupos;
	private SelectItem[] semillerosItem;
	List<Semillero> listaSemilleros;
	private boolean errorSemilleros;
	private String idSemillero;

	public ManejadorValidacionModalidades() {
		grupoObligatorio = false;
		sAprobacion = "NO";
		mostrarGrupos = false;
		convocatoriaSinGrupos = false;

		try {
			grupoEscogido = new Grupo();
			mensajeValidacion = "";
			tieneGrupos = false;
			noTieneGrupos = false;
			faltanDatos = false;
			convocatoriaActual = (Convocatoria) sesion.getAttribute("convocatoria");
			personaActual = (Persona) sesion.getAttribute("persona");

			Investigador investigador = new Investigador();
			investigador.setId(personaActual.getId());

			if (convocatoriaActual.getEsIntersedes() != null && convocatoriaActual.getEsIntersedes().booleanValue()) {
				mostrarGrupos = true;

				SelectItem[] interSedes = { new SelectItem("GRUPOS_INTERSEDES", "GRUPOS_INTERSEDES") };
				this.aprobacion = interSedes;

				if (convocatoriaActual.getId().compareTo(new Long("290")) == 0) {
					SelectItem[] aprobacion = { new SelectItem("SI", "SI") };
					this.aprobacion = aprobacion;
				}

			} else {
				mostrarGrupos = false;
				if (convocatoriaActual.getRestriccion() != null && convocatoriaActual.getRestriccion().getId()
						.equals(RestriccionConvocatoria.PLAN_ACCION_GRUPOS)) {
					List listaGrupos = servicioPersona.obtenerGruposInvestigadorLider(investigador.getId());
					if (cargarListaGrupos(listaGrupos)) {
						tieneGrupos = true;
					} else {
						if (!(convocatoriaActual.getGruposRegistrados() != null
								&& convocatoriaActual.getGruposRegistrados().booleanValue()))
							noTieneGrupos = true;
						else
							convocatoriaSinGrupos = true;
						mensajeValidacion = "No posee grupos que cumplan con los requisitos de la convocatoria, usted tiene que ser el investigador principal de su grupo";
					}
				} else {
					if ((convocatoriaActual.getRestriccion() != null && convocatoriaActual.getRestriccion().getId()
							.equals(RestriccionConvocatoria.CONV_GRUPOS_ARQUITECTURA_MEDELLIN))
							|| (convocatoriaActual.getRestriccion() != null && convocatoriaActual.getRestriccion()
									.getId().equals(RestriccionConvocatoria.CONV_GRU_CARIBE))
							|| (convocatoriaActual.getRestriccion() != null && convocatoriaActual.getRestriccion()
									.getId().equals(RestriccionConvocatoria.CONV_GRU_CIEN_MED))
							|| (convocatoriaActual.getRestriccion() != null && convocatoriaActual.getRestriccion()
									.getId().equals(RestriccionConvocatoria.CONV_ADMON_MAN_2015))) {

						mostrarMsjConvManMed = true;
						String consultaGrupo = "select #id e.id, #nombre e.nombre from Grupo e, InvestigadorGrupo i where e.estadoGrupo.id not in ('I','N','D') and "
								+ "i.grupo.id = e.id and i.tipo = 'L' and i.investigador.id.documento = '"
								+ investigador.getId().getDocumento() + "' and i.investigador.id.tipoDocumento = '"
								+ investigador.getId().getTipoDocumento() + "'";

						List<Grupo> listaGrupos = servicioGeneral.obtenerObjetosLimitado(Grupo.class, consultaGrupo);
						if (cargarListaGruposConvMan(listaGrupos)) {
							tieneGrupos = true;
						} else {
							noTieneGrupos = true;
							mensajeValidacion = "No posee grupos que cumplan con los requisitos de la convocatoria. Para poder participar en esta convocatoria se requiere actualizar la información del grupo de investigación y ser el lider del mismo.";
						}

					} else if (convocatoriaActual.getEsParaGrupos()) {
						if ((convocatoriaActual.getRestriccion() != null && convocatoriaActual.getRestriccion().getId()
								.equals(RestriccionConvocatoria.CONV_PROY_2016_2018))
								|| (convocatoriaActual.getRestriccion() != null && convocatoriaActual.getRestriccion()
										.getId().equals(RestriccionConvocatoria.CONV_NAL_PRY_2017_18))
								|| (convocatoriaActual.getRestriccion() != null && convocatoriaActual.getRestriccion()
										.getId().equals(RestriccionConvocatoria.CONV_FCH_OFB_3))
								|| (convocatoriaActual.getRestriccion() != null && convocatoriaActual.getRestriccion()
										.getId().equals(RestriccionConvocatoria.CONV_FCH_OFB_4))
								|| (convocatoriaActual.getRestriccion() != null && convocatoriaActual.getRestriccion()
										.getId().equals(RestriccionConvocatoria.CONV_CUND))) {
							mostrarMsjConvManMed = true;
							String consultaGrupo = "select #id e.id, #nombre e.nombre from Grupo e, InvestigadorGrupo i where e.estadoGrupo.id in ('A') and i.grupo.id = e.id and i.investigador.id.documento = '"
									+ investigador.getId().getDocumento() + "' and i.investigador.id.tipoDocumento = '"
									+ investigador.getId().getTipoDocumento() + "'";

							List<Grupo> listaGrupos = servicioGeneral.obtenerObjetosLimitado(Grupo.class,
									consultaGrupo);
							if (cargarListaGruposConvMan(listaGrupos)) {
								tieneGrupos = true;
							} else {
								noTieneGrupos = true;
								mensajeValidacion = "No posee grupos que cumplan con los requisitos de la convocatoria. Para poder participar en esta convocatoria se requiere la actualización de la información del grupo de investigación por parte del director del mismo.";
							}
						} else {

							String consultaGrupo = "";
							String estadoGrupos = "";
							if (convocatoriaActual.getGruposRegistrados()) {
								estadoGrupos = "'A'";
							}

							if (convocatoriaActual.getGruposReconocidos()) {
								if (estadoGrupos.equals("")) {
									estadoGrupos += "'I'";
								} else {
									estadoGrupos += ",'I'";
								}

							}

							if (convocatoriaActual.getGruposCategoriaA()) {
								if (estadoGrupos.equals("")) {
									estadoGrupos += "'S'";
								} else {
									estadoGrupos += ",'S'";
								}
							}

							if (convocatoriaActual.getGruposCategoriaB()) {
								if (estadoGrupos.equals("")) {
									estadoGrupos += "'C'";
								} else {
									estadoGrupos += ",'C'";
								}
							}

							if (convocatoriaActual.getGruposCategoriaC()) {
								if (estadoGrupos.equals("")) {
									estadoGrupos += "'P'";
								} else {
									estadoGrupos += ",'P'";
								}
							}

							// Validación si se debe permitir solo al lider getGruposCategoriaD = 'Y' o a
							// cualquier integrante del grupo getGruposCategoriaD = 'N'
							if (convocatoriaActual.getGruposCategoriaD()) {
								consultaGrupo = "select #id e.id, #nombre e.nombre from Grupo e, InvestigadorGrupo i where e.estadoGrupo.id in ("
										+ estadoGrupos
										+ ") and i.grupo.id = e.id and i.tipo = 'L' and i.investigador.id.documento = '"
										+ investigador.getId().getDocumento()
										+ "' and i.investigador.id.tipoDocumento = '"
										+ investigador.getId().getTipoDocumento() + "'";
							} else {
								consultaGrupo = "select #id e.id, #nombre e.nombre from Grupo e, InvestigadorGrupo i where e.estadoGrupo.id in ("
										+ estadoGrupos + ") and i.grupo.id = e.id and i.investigador.id.documento = '"
										+ investigador.getId().getDocumento()
										+ "' and i.investigador.id.tipoDocumento = '"
										+ investigador.getId().getTipoDocumento() + "'";
							}
							
							if(convocatoriaActual.isGruposSinRestriccionIntegrante()) {
								consultaGrupo = "select #id e.id, #nombre e.nombre from Grupo e where e.estadoGrupo.id = 'A'";
							}

							List<Grupo> listaGrupos = servicioGeneral.obtenerObjetosLimitado(Grupo.class,
									consultaGrupo);

							if (cargarListaGruposConvMan(listaGrupos)) {
								tieneGrupos = true;
							} else {
								if (convocatoriaActual.getNumeroMinimoGrupos() <= 0L) {
									tieneGrupos = true;
								} else {
									noTieneGrupos = true;
									mensajeValidacion = "No posee grupos que cumplan con los requisitos de la convocatoria.";
								}
							}
						}
					}
					if (convocatoriaActual.getEsParaSemilleros()) {
						setErrorSemilleros(false);
						String consulta = "select distinct s from Semillero s,SemilleroIntegrante si where s.id=si.semillero.id and si.integrante.id.documento = '"+personaActual.getId().getDocumento()+"'";
						listaSemilleros = servicioGeneral.obtenerObjetos(Semillero.class, consulta);
						if (listaSemilleros.size() != 0) {
							Integer idx = 0;
							for (Semillero s : listaSemilleros) {
								if (convocatoriaActual.getLiderSemilleros()) {
									if (s.getLider().getId().getDocumento().equals(personaActual.getId().getDocumento())
											&& s.getLider().getId().getTipoDocumento()
													.equals(personaActual.getId().getTipoDocumento())
											&& s.getEstadoActual().getId().equals(5)) {
										idx++;
									}
								} else {
									for (SemilleroIntegrante si : s.getIntegrantes()) {
										if (si.getIntegrante().getId().getDocumento()
												.equals(personaActual.getId().getDocumento())
												&& si.getIntegrante().getId().getTipoDocumento()
														.equals(personaActual.getId().getTipoDocumento())
												&& s.getEstadoActual().getId().equals(5)) {
											idx++;
										}
									}
								}
							}
							semillerosItem = new SelectItem[idx];
							if (idx == 0 && !convocatoriaActual.getNumeroMinimoSemilleros().equals(0)) {
								String msg = "Usted no se encuentra vinculado como INTEGRANTE de un semillero. Por favor, verifique que haya sido vinculado a uno y reintente la postulación.";
								if (convocatoriaActual.getLiderSemilleros()) {
									msg = "Usted no se encuentra vinculado como LÍDER de un semillero. Por favor, registre un semillero para iniciar el trámite de activación.";
								}
								FacesContext.getCurrentInstance().addMessage("msgs",
										new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
								setErrorSemilleros(true);
							} else {
								idx = 0;
								for (Semillero s : listaSemilleros) {
									if (convocatoriaActual.getLiderSemilleros()) {
										if (s.getLider().getId().getDocumento()
												.equals(personaActual.getId().getDocumento())
												&& s.getLider().getId().getTipoDocumento()
														.equals(personaActual.getId().getTipoDocumento())
												&& s.getEstadoActual().getId().equals(5)) {
											semillerosItem[idx] = new SelectItem(s.getId(), s.getNombre());
											idx++;
										}
									} else {
										for (SemilleroIntegrante si : s.getIntegrantes()) {
											if (si.getIntegrante().getId().getDocumento()
													.equals(personaActual.getId().getDocumento())
													&& si.getIntegrante().getId().getTipoDocumento()
															.equals(personaActual.getId().getTipoDocumento())
													&& s.getEstadoActual().getId().equals(5)) {
												semillerosItem[idx] = new SelectItem(s.getId(), s.getNombre());
												idx++;
											}
										}
									}
								}
							}
						} else {
							FacesContext.getCurrentInstance().addMessage("msgsModal",
									new FacesMessage(FacesMessage.SEVERITY_INFO, "No", null));
						}
					}
				}
			}

			if (convocatoriaActual.getEsConvocatoriaMultigrupos() != null) {
				if (convocatoriaActual.getEsConvocatoriaMultigrupos()) {
					listaGruposAlianzas = new ArrayList<Grupo>();
					cargarGruposInvestigacionAlianzas();
					esConvocatoriaMultigrupo = true;
				}
			}

			if (convocatoriaActual.getRestriccion() != null
					&& convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_ALI_NAL_2018)) {
				esConvocatoriaAlianzas2018 = true;
				listaGruposAlianzas = new ArrayList<Grupo>();
				cargarGruposInvestigacionAlianzas();
			}

			if (convocatoriaActual.getRestriccion() != null
					&& convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_ALI_NAL_2019)) {
				listaGruposAlianzas = new ArrayList<Grupo>();
				cargarGruposInvestigacionAlianzas();
				setEsConvocatoriaAlianzas2019(true);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cargarGruposInvestigacionAlianzas() {
		String grupos = "select #id e.id, #nombre e.nombre from Grupo e where e.estadoGrupo.id = 'A'";
		List listaGruposActivos = servicioGeneral.obtenerObjetosLimitado(Grupo.class, grupos);

		gruposInvItem = new SelectItem[listaGruposActivos.size()];
		for (int i = 0; i < listaGruposActivos.size(); i++) {
			Grupo dd = (Grupo) listaGruposActivos.get(i);
			gruposInvItem[i] = new SelectItem(dd.getId(), dd.getNombre());
			dd = null;
		}
	}

	public void adicionarGrupoInv() {

		Grupo g = servicioGrupo.obtenerGrupo(grupoEscogido.getId());

		if (!g.getId().equals(0L)) {
			if (!listaGruposAlianzas.contains(g)) {
				listaGruposAlianzas.add(g);
			} else {
				FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"El grupo de investigación ya se encuentra asociado al proyecto", ""));
			}
		} else {
			FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Por favor seleccione un grupo de investigación", ""));
		}

	}

	public void eliminarGrupoInv() {
		listaGruposAlianzas.remove(grupoSeleccionado);
		grupoSeleccionado = new Grupo();
	}

	public void buscarGrupos() {

		Investigador investigador = new Investigador();
		investigador.setId(personaActual.getId());

		List listaGrupos = new ArrayList();
		Grupo grupoAux = new Grupo();
		List listaGrupoAux = new ArrayList();
		List listaGrupoAuxDef = new ArrayList();

		listaGrupos = servicioPersona.obtenerGruposInvestigadorLider(investigador.getId());
		if (sAprobacion.equals("GRUPOS_INTERSEDES")) {

			for (int i = 0; i < listaGrupos.size(); i++) {
				grupoAux = (Grupo) listaGrupos.get(i);
				listaGrupoAux = servicioGeneral.obtenerListaObjetosWhere("GrupoIntersedes ",
						"where grupoInt='" + grupoAux.getId() + "'");

				if (listaGrupoAux != null && listaGrupoAux.size() > 0) {
					// GrupoIntersedes grupoInt = new GrupoIntersedes();
					// grupoInt=(GrupoIntersedes) listaGrupoAux.get(0);
					listaGrupoAuxDef.add(grupoAux);
				} else {
					// listaGrupos.remove(grupoAux);
				}

				if (cargarListaGrupos(listaGrupoAuxDef)) {
					tieneGrupos = true;
					noTieneGrupos = false;
				} else {
					tieneGrupos = false;
					mostrarGrupos = false;
					noTieneGrupos = true;

					mensajeValidacion = "No posee grupos que cumplan con los requisitos de la convocatoria, usted tiene que ser el investigador principal de su grupo";
				}

			}
		} else {

			if (cargarListaGrupos(listaGrupos)) {
				tieneGrupos = true;
				noTieneGrupos = false;
			} else {
				tieneGrupos = false;
				mostrarGrupos = false;
				noTieneGrupos = true;
				mensajeValidacion = "No posee grupos que cumplan con los requisitos de la convocatoria, usted tiene que ser el investigador principal de su grupo";
			}
		}

	}

	private boolean cargarListaGrupos(List listaGrupAux) {
		listaGrupos = new ArrayList();

		// SE RECORREN LOS GRUPOS DEL INVESTIGADOR PRINCIPAL
		if (listaGrupAux != null && listaGrupAux.size() > 0) {

			for (int i = 0; i < listaGrupAux.size(); i++) {
				Object object = listaGrupAux.get(i);
				Grupo g = null;
				if (object instanceof InvestigadorGrupo) {
					g = ((InvestigadorGrupo) object).getGrupo();
				} else {
					g = (Grupo) object;
				}
				listaGrupos.add(g);
				if (convocatoriaActual.getGruposRegistrados() != null
						&& convocatoriaActual.getGruposRegistrados().booleanValue()) {
					grupoObligatorio = true;
				}

			}
			// SI LA CONVOCATORIA ES PARA GRUPOS VALIDAR QUE EL INVESTIGADOR
			// PRINCIPAL TENGA GRUPOS,
			// SI NO TIENE GRUPOS ENVIAR UN MENSAJE PARA CAMBIAR EL PRINCIPAL
			if (listaGrupos.size() > 0) {
				gruposItem = new SelectItem[listaGrupos.size()];
				for (int i = 0; i < listaGrupos.size(); i++) {
					Grupo g = (Grupo) listaGrupos.get(i);
					gruposItem[i] = new SelectItem(g.getId(), g.getNombre());
				}
				// grupoEscogido.setId(((Grupo) listaGrupos.get(0)).getId());
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private boolean cargarListaGruposConvMan(List listaGrupAux) {
		listaGrupos = new ArrayList();

		// SE RECORREN LOS GRUPOS DEL INVESTIGADOR PRINCIPAL
		for (Object object : listaGrupAux) {
			Grupo g = null;
			if (object instanceof InvestigadorGrupo) {
				g = ((InvestigadorGrupo) object).getGrupo();
			} else {
				g = (Grupo) object;
			}
			listaGrupos.add(g);
			if (convocatoriaActual.getGruposRegistrados() != null
					&& convocatoriaActual.getGruposRegistrados().booleanValue()) {
				grupoObligatorio = true;
			}
		}
		// SI LA CONVOCATORIA ES PARA GRUPOS VALIDAR QUE EL INVESTIGADOR
		// PRINCIPAL TENGA GRUPOS,
		// SI NO TIENE GRUPOS ENVIAR UN MENSAJE PARA CAMBIAR EL PRINCIPAL
		if (listaGrupos.isEmpty()) {
			return false;
		}
		gruposItem = new SelectItem[listaGrupos.size()];
		int i = 0;
		for (Object object : listaGrupos) {
			Grupo g = (Grupo) object;
			gruposItem[i] = new SelectItem(g.getId(), g.getNombre());
			i++;
		}
		return true;
	}

	private boolean existeProyectosGrupoConvocatoria(Grupo grupo, Long idModalidad) {

		// Se verifica si ya existe un proyecto registrado en esta convocatoria.
		List proyectos = null;
		if (convocatoriaActual.getPadre().getEsPermanente().equals("N")) {
			proyectos = servicioGeneral.obtenerObjetos("select p.id from Grupo g join g.proyectos p where g.id = '"
					+ grupo.getId() + "' and p.estadoProyecto.id <> '" + EstadoProyecto.BORRADO
					+ "' and p.modalidad.id = '" + idModalidad + "'");
		}
		if (convocatoriaActual.getRegistrosPorGrupo() == 0) {
			return proyectos != null && proyectos.size() > 0;
		} else {
			return proyectos != null && proyectos.size() > convocatoriaActual.getRegistrosPorGrupo();
		}
	}

	private boolean validacionGruposConvocatoria() {
		boolean ret = true;
		mensajeValidacion = "";
		boolean valGrupoProyecto = true;
		if (listaGruposAlianzas.isEmpty() || listaGruposAlianzas.size() < convocatoriaActual.getNumeroMinimoGrupos()) {
			if (convocatoriaActual.getNumeroMinimoGrupos() != 0) {
				ret = false;
				if (convocatoriaActual.getNumeroMinimoGrupos().equals(1L)) {
					mensajeValidacion = "Se deben asociar por lo menos " + convocatoriaActual.getNumeroMinimoGrupos()
							+ " grupo.";
				} else {
					mensajeValidacion = "Se deben asociar por lo menos " + convocatoriaActual.getNumeroMinimoGrupos()
							+ " grupos.";
				}
			}
		} else {
			if (!(convocatoriaActual.getRestriccion() != null && convocatoriaActual.getRestriccion().getId() != null
					&& convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_REDES))) {
				int numeroGrupoPersona = 0;
				for (int i = 0; i < listaGruposAlianzas.size(); i++) {
					Grupo gru = listaGruposAlianzas.get(i);
					if (listaGrupos.contains(gru)) {
						numeroGrupoPersona++;
					}

					if (convocatoriaActual.getRegistrosPorGrupo() != null
							&& convocatoriaActual.getRegistrosPorGrupo() != 0
							&& existeProyectosGrupoConvocatoria(gru, convocatoriaActual.getId())) {
						valGrupoProyecto = false;
					}
				}
				if (numeroGrupoPersona <= 0 && !listaGruposAlianzas.isEmpty() && !convocatoriaActual.isGruposSinRestriccionIntegrante()) {
					ret = false;
					mensajeValidacion += "Debe asociar por lo menos un grupo de investigación en el que esté vinculado";
				}

				if (!valGrupoProyecto) {
					ret = false;
					mensajeValidacion += "Por favor verifique que los grupos registrados no se encuentren vinculados a otro proyecto inscrito a esta misma convocatoria.";
				}
			}
		}
		return ret;
	}

	private boolean validacionConvocatoriaAlianzas() {
		boolean ret = true;
		mensajeValidacion = "";

		if (listaGruposAlianzas.isEmpty() || listaGruposAlianzas.size() < 3) {
			ret = false;
			mensajeValidacion = "Se deben asociar por lo menos tres grupos";
		} else {
			int numeroGrupoPersona = 0;
			for (int i = 0; i < listaGruposAlianzas.size(); i++) {
				Grupo gru = listaGruposAlianzas.get(i);
				if (listaGrupos.contains(gru)) {
					numeroGrupoPersona++;
				}
			}
			if (numeroGrupoPersona <= 0 && !convocatoriaActual.isGruposSinRestriccionIntegrante()) {
				ret = false;
				mensajeValidacion = "Debe asociar por lo menos un grupo de investigación en el que esté vinculado";
			}
		}

		return ret;
	}

	public String siguiente() {
		String retVal = "";
		mensajeValidacion2 = "";
		sesion.removeAttribute("consultaFichaMinina");
		sesion.removeAttribute("esProyectoFichaMinimaNueva");
		sesion.removeAttribute("proyectoFichaMinimaNueva");
		sesion.removeAttribute("proyectoFichaMinina");

		if (esConvocatoriaMultigrupo) {
			if (validacionGruposConvocatoria()) {

				sesion.setAttribute("gruposAlianzas", listaGruposAlianzas);

				sesion.removeAttribute("manejadorFichaMinimaHome");
				sesion.removeAttribute("manejadorProyectosInvestigador");
				sesion.removeAttribute("manejadorActividades");
				sesion.removeAttribute("manejadorArchivos");
				sesion.removeAttribute("manejadorBibliografia");
				sesion.removeAttribute("manejadorInformacionEspecifica");

				retVal = "fichaMinimaHome";
			} else {
				FacesContext.getCurrentInstance().addMessage("messages",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeValidacion, ""));
				return "";
			}
		} else if (convocatoriaActual.getEsParaGrupos()) {
//			if(grupoEscogido.getId().equals(0L) && convocatoriaActual.getNumeroMinimoGrupos()!=0) {
			if (convocatoriaActual.getNumeroMinimoGrupos() != 0 && !esNulo(grupoEscogido)
					&& grupoEscogido.getId().equals(0L)) {
				mensajeValidacion2 = "Debe seleccionar algun grupo para esta modalidad";
				return "";
			}
			Grupo g = buscarGrupo(grupoEscogido.getId());
			if (g != null) {

				// Validar que el grupo tenga la informacion basica actualizada
				if (validarCamposGrupo(g)) {
					if (convocatoriaActual.getPadre().getId().equals(524L)) {
						EstadoProyecto[] ep = { new EstadoProyecto("P") };
						int counter = 0;
						List<Proyecto> pry = servicioProyecto.obtenerProyectosConvocatoria(ep, convocatoriaActual);
						for (Proyecto proyecto : pry) {
							String query = "select #id e.id from Grupo e, Proyecto p where p.grupos.id = e.id and p.id = "
									+ proyecto.getId();
							List<Grupo> gru = servicioGeneral.obtenerObjetosLimitado(Grupo.class, query);
							for (Grupo gr : gru) {
								if (gr.equals(grupoEscogido)) {
									counter++;
								}
							}
						}
						if (counter >= 3) {
							String mensajeError = "No pueden haber más de 3 propuestas asociadas al mismo grupo de investigación.";
							FacesContext.getCurrentInstance().addMessage("Error",
									new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeError, ""));
							return "";
						}
					}
					if (convocatoriaActual.getRegistrosPorGrupo() != null
							&& convocatoriaActual.getRegistrosPorGrupo() != 0) {
						EstadoProyecto[] ep = { new EstadoProyecto("P") };
						int counter = 0;
						List<Proyecto> pry = servicioProyecto.obtenerProyectosConvocatoria(ep, convocatoriaActual);
						for (Proyecto proyecto : pry) {
							String query = "select #id e.id from Grupo e, Proyecto p where p.grupos.id = e.id and p.id = "
									+ proyecto.getId();
							List<Grupo> gru = servicioGeneral.obtenerObjetosLimitado(Grupo.class, query);
							for (Grupo gr : gru) {
								if (gr.equals(grupoEscogido)) {
									counter++;
								}
							}
						}
						if (counter >= convocatoriaActual.getRegistrosPorGrupo()) {
							String mensajeError = "No puede(n) haber más de "
									+ convocatoriaActual.getRegistrosPorGrupo()
									+ " propuesta(s) asociada(s) al mismo grupo de investigación.";
							FacesContext.getCurrentInstance().addMessage("Error",
									new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeError, ""));
							return "";
						}
					}
					Proyecto p;
					if (sesion.getAttribute("proyecto") != null) {
						p = (Proyecto) sesion.getAttribute("proyecto");
					} else
						p = new Proyecto();
					p.setModalidad(convocatoriaActual);
					p.adicionarGrupo(g);
					sesion.setAttribute("proyecto", p);
					sesion.removeAttribute("manejadorProyectosInvestigador");
					sesion.removeAttribute("manejadorActividades");
					sesion.removeAttribute("manejadorArchivos");
					sesion.removeAttribute("manejadorBibliografia");
					sesion.removeAttribute("manejadorDatosBasicos");
					sesion.removeAttribute("manejadorDetallesFinancieros");
					sesion.removeAttribute("manejadorEvaluadores");
					sesion.removeAttribute("manejadorFuentesFinancieras");
					sesion.removeAttribute("manejadorInformacionEspecifica");
					sesion.removeAttribute("manejadorInvestigadores");
					sesion.removeAttribute("manejadorLineas");
					sesion.removeAttribute("manejadorObjetivosResultados");
					sesion.removeAttribute("manejadorRubros");
					sesion.removeAttribute("manejadorVigencias");
					sesion.removeAttribute("manejadorMenuFormularios");
					sesion.removeAttribute("ManejadorElegirMod_JI_SEM_2014");

					if (convocatoriaActual.getRestriccion() != null
							&& (convocatoriaActual.getRestriccion().getId().equals("CONV_POSG")
									|| convocatoriaActual.getRestriccion().getId().equals("CONV_ADMON_MAN")
									|| convocatoriaActual.getRestriccion().getId().equals("CONV_POSG_3"))) {

						sesion.setAttribute("idConvocatoriaActual", convocatoriaActual.getId());
						retVal = "irFichaMinima";
					} else {
						if ((convocatoriaActual.getRestriccion() != null
								&& convocatoriaActual.getRestriccion().getId().equals("CONV_SEM_COL"))
								|| (convocatoriaActual.getRestriccion() != null
										&& convocatoriaActual.getRestriccion().getId().equals("CONV_JI_COL"))) {
							retVal = "irMod_JI_SEM";
						} else {
							if ((convocatoriaActual.getRestriccion() != null
									&& convocatoriaActual.getRestriccion().getId().equals("CONV_JI_COL_2014"))) {
								sesion.setAttribute("grupoJI", g);
								retVal = "irMod_JI_SEM_2014";
							} else {
								if ((convocatoriaActual.getEsParaGrupos())
										|| (convocatoriaActual.getRestriccion() != null
												&& convocatoriaActual.getRestriccion().getId()
														.equals(RestriccionConvocatoria.CONV_MED_INMLCF_2))
										|| (convocatoriaActual.getRestriccion() != null
												&& convocatoriaActual.getRestriccion().getId()
														.equals(RestriccionConvocatoria.CONV_PROY_2016_2018))
										|| (convocatoriaActual.getRestriccion() != null
												&& convocatoriaActual.getRestriccion().getId()
														.equals(RestriccionConvocatoria.CON_HUM_MAN_2016))
										|| (convocatoriaActual.getRestriccion() != null
												&& convocatoriaActual.getRestriccion().getId()
														.equals(RestriccionConvocatoria.CON_BIO_MAN_2016))
										|| (convocatoriaActual.getRestriccion() != null
												&& convocatoriaActual.getRestriccion().getId()
														.equals(RestriccionConvocatoria.CONV_MAN_CIEN_BA))
										|| (convocatoriaActual.getRestriccion() != null
												&& convocatoriaActual.getRestriccion().getId()
														.equals(RestriccionConvocatoria.CONV_MAN_INV_APL))
										|| (convocatoriaActual.getRestriccion() != null
												&& convocatoriaActual.getRestriccion().getId()
														.equals(RestriccionConvocatoria.CONV_MAN_CIEN_SO))
										|| (convocatoriaActual.getRestriccion() != null
												&& convocatoriaActual.getRestriccion().getId()
														.equals(RestriccionConvocatoria.CONV_FCH_OFB_4))
										|| (convocatoriaActual.getRestriccion() != null
												&& convocatoriaActual.getRestriccion().getId()
														.equals(RestriccionConvocatoria.CONV_FCH_OFB_3))
										|| (convocatoriaActual.getRestriccion() != null
												&& convocatoriaActual.getRestriccion().getId().equals(
														RestriccionConvocatoria.CONV_GRUPOS_ARQUITECTURA_MEDELLIN))
										|| (convocatoriaActual.getRestriccion() != null
												&& convocatoriaActual.getRestriccion().getId()
														.equals(RestriccionConvocatoria.CONV_GRU_CARIBE))
										|| (convocatoriaActual.getRestriccion() != null
												&& convocatoriaActual.getRestriccion().getId()
														.equals(RestriccionConvocatoria.CONV_GRU_CIEN_MED))
										|| (convocatoriaActual.getRestriccion() != null
												&& convocatoriaActual.getRestriccion().getId()
														.equals(RestriccionConvocatoria.CONV_ADMON_MAN_2015))
										|| (convocatoriaActual.getRestriccion() != null
												&& convocatoriaActual.getRestriccion().getId()
														.equals(RestriccionConvocatoria.CONV_ART_2015_2))
										|| (convocatoriaActual.getRestriccion() != null
												&& convocatoriaActual.getRestriccion().getId().equals("CONV_ART_2014"))
										|| (convocatoriaActual.getRestriccion() != null
												&& convocatoriaActual.getRestriccion().getId().equals("CONV_CONJ_MAN"))
										|| (convocatoriaActual.getRestriccion() != null
												&& convocatoriaActual.getRestriccion().getId().equals("CONV_DER"))
										|| (convocatoriaActual.getRestriccion() != null
												&& convocatoriaActual.getRestriccion().getId().equals("CONV_BEJARANO"))
										|| (convocatoriaActual.getRestriccion() != null && convocatoriaActual
												.getRestriccion().getId().equals("CONV_DNIPI_2017"))) {
									sesion.setAttribute("grupoJI", g);
									retVal = "fichaMinimaHome";
								} else {
									retVal = "crearProyecto";
								}
							}
						}

					}
				} else {
					faltanDatos = true;
					tieneGrupos = false;
					noTieneGrupos = false;
					mensajeValidacion = "FALTA INFORMACIÓN DEL GRUPO \n Recuerde comunicarse con el director del grupo al cual pertenece para actualizar la información del grupo de investigación";
					return "";
				}
			} else if (convocatoriaActual.getGruposRegistrados() != null
					&& convocatoriaActual.getGruposRegistrados().booleanValue()) {
				Proyecto p;
				if (sesion.getAttribute("proyecto") != null) {
					p = (Proyecto) sesion.getAttribute("proyecto");
				} else
					p = new Proyecto();
				p.setModalidad(convocatoriaActual);
				sesion.setAttribute("proyecto", p);
				sesion.removeAttribute("manejadorProyectosInvestigador");
				sesion.removeAttribute("manejadorActividades");
				sesion.removeAttribute("manejadorArchivos");
				sesion.removeAttribute("manejadorBibliografia");
				sesion.removeAttribute("manejadorDatosBasicos");
				sesion.removeAttribute("manejadorDetallesFinancieros");
				sesion.removeAttribute("manejadorEvaluadores");
				sesion.removeAttribute("manejadorFuentesFinancieras");
				sesion.removeAttribute("manejadorInformacionEspecifica");
				sesion.removeAttribute("manejadorInvestigadores");
				sesion.removeAttribute("manejadorLineas");
				sesion.removeAttribute("manejadorObjetivosResultados");
				sesion.removeAttribute("manejadorRubros");
				sesion.removeAttribute("manejadorVigencias");
				sesion.removeAttribute("manejadorMenuFormularios");
				if (!esNulo(convocatoriaActual.getRestriccion())) {
					if (convocatoriaActual.getRestriccion().getId().equals("CONV_POSG")) {
						sesion.setAttribute("idConvocatoriaActual", convocatoriaActual.getId());
						retVal = "irFichaMinima";
					} else if (convocatoriaActual.getRestriccion().getId().equals("CONV_DER"))
						retVal = "fichaMinimaHome";
					else
						retVal = "crearProyecto";
				} else
					retVal = "fichaMinimaHome";
			} else {
				mensajeValidacion2 = "Debe seleccionar algun grupo para esta modalidad";
				return "";
			}
		}
		if (convocatoriaActual.getEsParaSemilleros()) {
			if (esCadenaVacia(getIdSemillero()) && !convocatoriaActual.getNumeroMinimoSemilleros().equals(0)) {
				FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Por favor, indique el semillero a vincular al proyecto.", null));
				return "";
			}
			Proyecto p;
			if (sesion.getAttribute("proyecto") != null) {
				p = (Proyecto) sesion.getAttribute("proyecto");
			} else {
				p = new Proyecto();
			}
			
			p.setModalidad(convocatoriaActual);
			if(!esCadenaVacia(idSemillero)) {
				Semillero s = servicioGeneral.obtenerObjetoXID(Semillero.class, idSemillero).get(0);
				p.getSemilleros().add(s);
			}
			
			sesion.setAttribute("proyecto", p);
			sesion.removeAttribute("manejadorProyectosInvestigador");
			sesion.removeAttribute("manejadorActividades");
			sesion.removeAttribute("manejadorArchivos");
			sesion.removeAttribute("manejadorBibliografia");
			sesion.removeAttribute("manejadorDatosBasicos");
			sesion.removeAttribute("manejadorDetallesFinancieros");
			sesion.removeAttribute("manejadorEvaluadores");
			sesion.removeAttribute("manejadorFuentesFinancieras");
			sesion.removeAttribute("manejadorInformacionEspecifica");
			sesion.removeAttribute("manejadorInvestigadores");
			sesion.removeAttribute("manejadorLineas");
			sesion.removeAttribute("manejadorObjetivosResultados");
			sesion.removeAttribute("manejadorRubros");
			sesion.removeAttribute("manejadorVigencias");
			sesion.removeAttribute("manejadorMenuFormularios");
			sesion.removeAttribute("ManejadorElegirMod_JI_SEM_2014");
			retVal = "fichaMinimaHome";
		}
		return retVal;
	}

	public String siguienteSinGrupo() {

		Proyecto p;
		if (sesion.getAttribute("proyecto") != null) {
			p = (Proyecto) sesion.getAttribute("proyecto");
		} else
			p = new Proyecto();
		p.setModalidad(convocatoriaActual);
		sesion.setAttribute("proyecto", p);
		sesion.removeAttribute("manejadorProyectosInvestigador");
		sesion.removeAttribute("manejadorActividades");
		sesion.removeAttribute("manejadorArchivos");
		sesion.removeAttribute("manejadorBibliografia");
		sesion.removeAttribute("manejadorDatosBasicos");
		sesion.removeAttribute("manejadorDetallesFinancieros");
		sesion.removeAttribute("manejadorEvaluadores");
		sesion.removeAttribute("manejadorFuentesFinancieras");
		sesion.removeAttribute("manejadorInformacionEspecifica");
		sesion.removeAttribute("manejadorInvestigadores");
		sesion.removeAttribute("manejadorLineas");
		sesion.removeAttribute("manejadorObjetivosResultados");
		sesion.removeAttribute("manejadorRubros");
		sesion.removeAttribute("manejadorVigencias");
		sesion.removeAttribute("manejadorMenuFormularios");

		sesion.setAttribute("idConvocatoriaActual", convocatoriaActual.getId());
		return "irFichaMinima";

	}

	/**
	 * Validar los campos mínimos que debe tener el grupo diligenciados para poder
	 * inscribir un proyecto asociado al grupo
	 * 
	 * @param g
	 * @return
	 */
	private boolean validarCamposGrupo(Grupo grupo) {

		if (grupo.getAnoClasificacionColciencias() != null && grupo.getAnoClasificacionColciencias().intValue() != 0
				&& grupo.getPresentacion() != null && !grupo.getPresentacion().equals("")) {
			return true;
		} else
			return true;
	}

	private Grupo buscarGrupo(Long id) {
		if (listaGrupos != null) {
			Iterator it = listaGrupos.iterator();
			while (it.hasNext()) {
				Grupo g = (Grupo) it.next();
				if (g.getId().longValue() == id.longValue()) {
					return g;
				}
			}
		}
		return null;
	}

	public Grupo getGrupoEscogido() {
		return grupoEscogido;
	}

	public void setGrupoEscogido(Grupo grupoEscogido) {
		this.grupoEscogido = grupoEscogido;
	}

	public SelectItem[] getGruposItem() {
		return gruposItem;
	}

	public void setGruposItem(SelectItem[] gruposItem) {
		this.gruposItem = gruposItem;
	}

	public String getMensajeValidacion() {
		return mensajeValidacion;
	}

	public void setMensajeValidacion(String mensajeValidacion) {
		this.mensajeValidacion = mensajeValidacion;
	}

	public boolean isTieneGrupos() {
		return tieneGrupos;
	}

	public void setTieneGrupos(boolean tieneGrupos) {
		this.tieneGrupos = tieneGrupos;
	}

	/**
	 * @return Returns the faltanDatos.
	 */
	public boolean isFaltanDatos() {
		return faltanDatos;
	}

	/**
	 * @param faltanDatos The faltanDatos to set.
	 */
	public void setFaltanDatos(boolean faltanDatos) {
		this.faltanDatos = faltanDatos;
	}

	/**
	 * @return Returns the noTieneGrupos.
	 */
	public boolean isNoTieneGrupos() {
		return noTieneGrupos;
	}

	/**
	 * @param noTieneGrupos The noTieneGrupos to set.
	 */
	public void setNoTieneGrupos(boolean noTieneGrupos) {
		this.noTieneGrupos = noTieneGrupos;
	}

	public SelectItem[] getAprobacion() {
		return aprobacion;
	}

	public void setAprobacion(SelectItem[] aprobacion) {
		this.aprobacion = aprobacion;
	}

	public String getsAprobacion() {
		return sAprobacion;
	}

	public void setsAprobacion(String sAprobacion) {
		this.sAprobacion = sAprobacion;
	}

	public boolean isMostrarGrupos() {
		return mostrarGrupos;
	}

	public void setMostrarGrupos(boolean mostrarGrupos) {
		this.mostrarGrupos = mostrarGrupos;
	}

	public void setMensajeValidacion2(String mensajeValidacion2) {
		this.mensajeValidacion2 = mensajeValidacion2;
	}

	public String getMensajeValidacion2() {
		return mensajeValidacion2;
	}

	public boolean isConvocatoriaSinGrupos() {
		return convocatoriaSinGrupos;
	}

	public void setConvocatoriaSinGrupos(boolean convocatoriaSinGrupos) {
		this.convocatoriaSinGrupos = convocatoriaSinGrupos;
	}

	public boolean isMostrarMsjConvManMed() {
		return mostrarMsjConvManMed;
	}

	public void setMostrarMsjConvManMed(boolean mostrarMsjConvManMed) {
		this.mostrarMsjConvManMed = mostrarMsjConvManMed;
	}

	public boolean isEsConvocatoriaAlianzas2018() {
		return esConvocatoriaAlianzas2018;
	}

	public void setEsConvocatoriaAlianzas2018(boolean esConvocatoriaAlianzas2018) {
		this.esConvocatoriaAlianzas2018 = esConvocatoriaAlianzas2018;
	}

	public SelectItem[] getGruposInvItem() {
		return gruposInvItem;
	}

	public void setGruposInvItem(SelectItem[] gruposInvItem) {
		this.gruposInvItem = gruposInvItem;
	}

	public List<Grupo> getListaGruposAlianzas() {
		return listaGruposAlianzas;
	}

	public void setListaGruposAlianzas(List<Grupo> listaGruposAlianzas) {
		this.listaGruposAlianzas = listaGruposAlianzas;
	}

	public Grupo getGrupoSeleccionado() {
		return grupoSeleccionado;
	}

	public void setGrupoSeleccionado(Grupo grupoSeleccionado) {
		this.grupoSeleccionado = grupoSeleccionado;
	}

	public boolean isEsConvocatoriaMultigrupo() {
		return esConvocatoriaMultigrupo;
	}

	public void setEsConvocatoriaMultigrupo(boolean esConvocatoriaMultigrupo) {
		this.esConvocatoriaMultigrupo = esConvocatoriaMultigrupo;
	}

	public Convocatoria getConvocatoriaActual() {
		return convocatoriaActual;
	}

	public void setConvocatoriaActual(Convocatoria convocatoriaActual) {
		this.convocatoriaActual = convocatoriaActual;
	}

	public SelectItem[] getSemillerosItem() {
		return semillerosItem;
	}

	public void setSemillerosItem(SelectItem[] semillerosItem) {
		this.semillerosItem = semillerosItem;
	}

	public boolean isErrorSemilleros() {
		return errorSemilleros;
	}

	public void setErrorSemilleros(boolean errorSemilleros) {
		this.errorSemilleros = errorSemilleros;
	}

	public String getIdSemillero() {
		return idSemillero;
	}

	public void setIdSemillero(String idSemillero) {
		this.idSemillero = idSemillero;
	}

	public boolean isEsConvocatoriaAlianzas2019() {
		return esConvocatoriaAlianzas2019;
	}

	public void setEsConvocatoriaAlianzas2019(boolean esConvocatoriaAlianzas2019) {
		this.esConvocatoriaAlianzas2019 = esConvocatoriaAlianzas2019;
	}
}
