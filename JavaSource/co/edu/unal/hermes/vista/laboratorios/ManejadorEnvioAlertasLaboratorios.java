package co.edu.unal.hermes.vista.laboratorios;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import co.edu.unal.hermes.modelo.Bien;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioActividadEquipo;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleEquipos;
import co.edu.unal.hermes.modelo.laboratorios.PersonaLaboratorio;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * @author dgbenitezc
 */
public class ManejadorEnvioAlertasLaboratorios extends ManejadorBase {

	private Boolean esLaboratoriosNacional;
	private ManejadorUtilidadesLaboratorios mUL;

	public ManejadorEnvioAlertasLaboratorios() {
		esLaboratoriosNacional = (Boolean) sesion
				.getAttribute("esLaboratorios");
		mUL = new ManejadorUtilidadesLaboratorios();
	}

	public void enviarAlertas2() {
		String hql =
		// "FROM LaboratorioDetalleEquipos eq WHERE eq.placa in ('4023283')";
		// "FROM LaboratorioDetalleEquipos eq WHERE eq.fechaBusquedaEnBienes IS NULL";
		// "FROM LaboratorioDetalleEquipos eq WHERE eq.fechaBusquedaEnBienes < (sysdate - 60) and eq.id <> 46476";
		// "FROM LaboratorioDetalleEquipos eq WHERE eq.fechaBusquedaEnBienes < (sysdate - 50) AND eq.laboratorio.id in (204)";
		"FROM LaboratorioDetalleEquipos eq WHERE eq.dadoDeBaja = '0' AND eq.laboratorio.id in (204)";

		System.out.println("hql: " + hql);
		List<LaboratorioDetalleEquipos> listaEquipos;
		listaEquipos = servicioGeneral.obtenerObjetos(
				LaboratorioDetalleEquipos.class, hql);

		Integer equiposEncontrados = listaEquipos.size();
		Integer equiposActualizados = 0;
		Integer equiposNoExisten = 0;
		Integer equiposSiExisten = 0;
		System.out.println("equiposEncontrados: " + equiposEncontrados);

		int numeroEquipo = 0;
		for (LaboratorioDetalleEquipos equipo : listaEquipos) {
			numeroEquipo++;
			System.out.println("****************** equipo: " + numeroEquipo
					+ " / " + equiposEncontrados);
			System.out.println("equipo.getPlaca(): " + equipo.getPlaca());
			List<Bien> bienes = servicioGeneral.consultaEquipos(equipo
					.getPlaca());
			System.out.println("bienes.size = " + bienes.size());

			Boolean equipoExisteEnBDInv = false;
			Bien bien;
			LaboratorioDetalleEquipos equipoV = new LaboratorioDetalleEquipos();
			ManejadorUtilidadesLaboratorios mul = new ManejadorUtilidadesLaboratorios();
			Boolean error = false;

			if (bienes.size() == 1) {
				equipoExisteEnBDInv = true;
				bien = bienes.get(0);

				try {
					BeanUtils.copyProperties(equipoV, bien);
				} catch (IllegalAccessException e1) {
					
					error = true;
					e1.printStackTrace();

				} catch (InvocationTargetException e1) {
					
					error = true;
					e1.printStackTrace();
				}

				equipoV.setEquipo(mul
						.buscarEnDescripcion("DESCRIPCION :", bien));
				equipoV.setMarca(mul.buscarEnDescripcion("MARCA :", bien));
				equipoV.setModelo(mul.buscarEnDescripcion("MODELO :", bien));

				Boolean actualizar = false;

				if (equipo.getEquipo() == null) {
					equipo.setEquipo(equipoV.getEquipo());
					actualizar = true;
				}
				if (equipo.getMarca() == null) {
					equipo.setMarca(equipoV.getMarca());
					actualizar = true;
				}
				if (equipo.getModelo() == null) {
					equipo.setModelo(equipoV.getModelo());
					actualizar = true;
				}
				if (equipo.getResponsable() == null) {
					equipo.setResponsable(equipoV.getResponsable());
					actualizar = true;
				}
				if (equipo.getIdResponsable() == null) {
					equipo.setIdResponsable(equipoV.getIdResponsable());
					actualizar = true;
				}

				if (!equipoV.getIdResponsable().equals(
						equipo.getIdResponsable())) {
					System.out.println("Se actualiza responsable");
					equipo.setIdResponsable(equipoV.getIdResponsable());
					equipo.setResponsable(equipoV.getResponsable());
					actualizar = true;
				}

				if (equipo.getValor() == null) {
					equipo.setValor(equipoV.getValor());
					actualizar = true;
				}
				if (equipo.getFechaServicio() == null) {
					equipo.setFechaServicio(equipoV.getFechaServicio());
					actualizar = true;
				}
				if (equipo.getFechaAdquisicion() == null) {
					equipo.setFechaAdquisicion(equipoV.getFechaAdquisicion());
					actualizar = true;
				}
				if (equipo.getSerial() == null) {
					equipo.setSerial(equipoV.getSerial());
					actualizar = true;
				}

				System.out.println("actualizar: " + actualizar);

				// comparar información:
				Boolean mostrarDiferencias = false;
				String diferencias = "";
				System.out.println("mostrarDiferencias in:"
						+ mostrarDiferencias);
				try {

					if (compararStrings(equipo.getEquipo(), equipoV.getEquipo())) {
						equipo.setEquipo(equipoV.getEquipo());
					}

					if (!equipo.getEquipo().equals(equipoV.getEquipo())) {
						mostrarDiferencias = true;
						System.out.println("Nombre de equipo diferente");
						diferencias += " -Nombre Equipo ";
					}
					if (!equipo.getMarca().equals(equipoV.getMarca())) {
						mostrarDiferencias = true;
						System.out.println("Marca de equipo diferente");
						diferencias += " -Marca ";
					}
					if (!equipo.getModelo().equals(equipoV.getModelo())) {
						mostrarDiferencias = true;
						System.out.println("Modelo de equipo diferente");
						diferencias += " -Modelo ";
					}
					if (!compararStrings(equipo.getSerial(),
							equipoV.getSerial())) {
						mostrarDiferencias = true;
						System.out.println("Serial de equipo diferente");
						diferencias += " -Serial ";
					}
				} catch (Exception e) {
					
					System.out.println("mostrarDiferencias ERROR");
					mostrarDiferencias = true;
					error = true;
					diferencias += " -Error al Comparar ";
				}
				System.out.println("mostrarDiferencias out:"
						+ mostrarDiferencias);

				System.out.println("error: " + error);
				if (!error) {
					System.out.println("equipo SI existe en bienes:");
					System.out.println("actualizando equipo:");
					equipo.setFechaBusquedaEnBienes(new Date());
					equipo.setExisteEnBienes(equipoExisteEnBDInv);
					System.out.println("equipo.getExisteEnBienes:"
							+ equipo.getExisteEnBienes());
					System.out.println("mostrarDiferencias:"
							+ mostrarDiferencias);
					equipo.setDiferenciasConBienes(diferencias);
					System.out.println("equipo.getDiferenciasConBienes:"
							+ equipo.getDiferenciasConBienes());
					servicioGeneral.guardarObjeto(equipo);
					System.out.println("equipo actualizado.");
					equiposActualizados++;
					equiposSiExisten++;
				}

			} else if (bienes.isEmpty()) {
				System.out.println("bienes vacio, equipo NO existe en bienes:");
				System.out.println("actualizando equipo:");
				equipo.setFechaBusquedaEnBienes(new Date());
				equipo.setExisteEnBienes(equipoExisteEnBDInv);
				servicioGeneral.guardarObjeto(equipo);
				equiposActualizados++;
				equiposNoExisten++;
			}

			else {
				System.out.println("Error, bienes.size = " + bienes.size());
			}

		}

		mensajeInfo("Equipos encontrados: " + equiposEncontrados
				+ ", actualizados: " + equiposActualizados + ", SI existen: "
				+ equiposSiExisten + ", NO existen: " + equiposNoExisten);
	}

	public void enviarAlertas() {
		// FUNCIONALIDAD OBSOLETA POR IMPLEMENTACIÓN DEL REQUERIMIENTO 2526 (ALERTAS AUTOMÁTICAS)
		if (esLaboratoriosNacional) {

			Long PLANTILLA_ACT_LAB_PROGRAMADA = new Long(166);
			CorreoPlantilla correoPlantilla = (CorreoPlantilla) servicioGeneral
					.obtenerObjeto(new CorreoPlantilla(),
							PLANTILLA_ACT_LAB_PROGRAMADA);

			String hql = "FROM LaboratorioActividadEquipo lae WHERE lae.estadoActividad = '"
					+ LaboratorioActividadEquipo.ESTADO_PROGRAMADA
					+ "' AND lae.equipo.laboratorio.activo = '1'";
			System.out.println(hql);

			List<LaboratorioActividadEquipo> listaActividades = new ArrayList<LaboratorioActividadEquipo>();

			listaActividades = servicioGeneral.obtenerObjetos(
					LaboratorioActividadEquipo.class, hql);

			int correosEnviados = 0;
			int correosAEnviar = 0;
			Date fechaActual = new Date();

			for (LaboratorioActividadEquipo lae : listaActividades) {
				Date fechaActividad = lae.getFechaActividad();

				boolean enviar = true;
				Date fechaUltimaAlerta = lae.getFechaAlerta();
				if (fechaUltimaAlerta != null) {
					long diasUltimaAlerta = (fechaActual.getTime() - fechaUltimaAlerta
							.getTime()) / 86400000; // 1000 * 60 * 60 * 24
					System.out.println("diasUltimaAlerta: " + diasUltimaAlerta);
					if (diasUltimaAlerta < 30) {
						enviar = false;
					}
				}
				System.out.println("aplicaEnviar: " + enviar);

				long diasFaltantes = 0;
				if (enviar) {
					diasFaltantes = (fechaActividad.getTime() - fechaActual
							.getTime()) / 86400000; // 1000 * 60 * 60 * 24
					System.out.println("diasFaltantes " + fechaActividad
							+ " - " + fechaActual + ": " + diasFaltantes);
				}

				// no se envían alertas con mas de dos meses de anticipación
				if (enviar && diasFaltantes <= 61 && diasFaltantes > 0) {

					correosAEnviar++;
					String cuerpo = correoPlantilla.getCuerpo();
					String asunto = correoPlantilla.getAsunto();
					Correo correo = new Correo();
					LaboratorioDetalleEquipos lde = lae.getEquipo();
					if (lde != null) {
						cuerpo = cuerpo.replaceAll("<<EQUIPO>>",
								lde.getEquipo());
						cuerpo = cuerpo.replaceAll("<<PLACA>>", lde.getPlaca());
						cuerpo = cuerpo.replaceAll("<<LABORATORIO>>", lde
								.getLaboratorio().getNombre());

						PersonaLaboratorio pL = mUL.coordinadorLaboratorio(lde
								.getLaboratorio().getId());
						if (pL != null) {
							correo.adicionarDireccion(pL.getPersona()
									.getEmail());
						}

						// Copia al email del laboratorio
						String emailLab = lde.getLaboratorio().getEmail();
						System.out.println("emailLab: " + emailLab);
						if (emailLab != null && emailLab != ""
								&& emailLab.contains("@")) {
							correo.adicionarDireccion(emailLab);
						}
					}

					String actividad = lae.getTipoActividad().getNombre();
					/*
					 * LaboratorioActividadMantenimiento lam = lae
					 * .getActividadMantto(); if (lam != null) { actividad +=
					 * "(" + lam.getNombreActividad() + ")"; }
					 */
					cuerpo = cuerpo.replaceAll("<<ACTIVIDAD>>", actividad);

					SimpleDateFormat formatter = new SimpleDateFormat(
							"dd/MM/yyyy");
					cuerpo = cuerpo.replaceAll("<<FECHA>>",
							formatter.format(fechaActividad));

					String observaciones = lae.getObservaciones();
					if (observaciones != null) {
						cuerpo = cuerpo.replaceAll("<<OBSERVACIONES>>",
								"Observaciones: " + observaciones);
					} else {
						cuerpo = cuerpo.replaceAll("<<OBSERVACIONES>>", "");
					}

					//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
					correo.setAsunto(asunto);
					correo.setCuerpo(cuerpo);

					System.out.println("...enviando Correo...");
					if (correo.getDirecciones().size() == 0) {
						correo.adicionarDireccion("dirnalab_nal@unal.edu.co");
					}

					System.out.println("asunto: " + asunto);
					System.out.println("asunto: " + cuerpo);
					System.out.println("direcciones: "
							+ correo.getDirecciones());

					if (servicioCorreo.enviarCorreo(correo)) {
						System.out.println("enviarCorreo OK");
						correosEnviados++;
//						lae.setFechaAlerta(new Date());
						servicioGeneral.guardarObjeto(lae);
					} else {
						System.out.println("enviarCorreo ERROR");
					}

				}

			}

			mensajeInfo("Correos Enviados " + correosEnviados + " / "
					+ correosAEnviar);

		} else {
			mensajeInfo("No tiene el rol necesario para ejecutar esta actividad.");
		}

	}
}
