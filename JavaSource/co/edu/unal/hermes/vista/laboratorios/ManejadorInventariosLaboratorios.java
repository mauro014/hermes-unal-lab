/**
 * @author dgbenitezc
 */

package co.edu.unal.hermes.vista.laboratorios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.Bien;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleEquipos;
import co.edu.unal.hermes.modelo.laboratorios.PersonaLaboratorio;
import co.edu.unal.hermes.vista.ManejadorBase;
import co.edu.unal.hermes.vista.utils.Util;

public class ManejadorInventariosLaboratorios extends ManejadorBase {

	private SelectItem[] selectItemSedes;
	private List<Laboratorio> listaLaboratorios;
	private List<Laboratorio> laboratoriosFiltrados;
	private String placaEquipo;
	private LaboratorioDetalleEquipos equipo;
	private Boolean mostrarEquipo;
	private Laboratorio laboratorioSeleccionado;
	private String placaEquipoEncontrado;
	private ManejadorUtilidadesLaboratorios mUL;

	public ManejadorInventariosLaboratorios() {
		String hql = "from Sede WHERE id NOT IN (1) ORDER BY id";
		List<Sede> listaSedes = servicioGeneral.obtenerObjetos(Sede.class, hql);
		selectItemSedes = new SelectItem[listaSedes.size() + 1];
		selectItemSedes[0] = new SelectItem("", "Todas");
		for (int i = 0; i < listaSedes.size(); i++) {
			Sede sede = (Sede) listaSedes.get(i);
			selectItemSedes[i + 1] = new SelectItem(sede.getId(),
					sede.getNombre());
		}

		listaLaboratorios = servicioGeneral.obtenerObjetos(Laboratorio.class,
				"FROM Laboratorio WHERE activo = " + Laboratorio.VERDADERO
						+ " order by id DESC");

		mUL = new ManejadorUtilidadesLaboratorios();
		personaActual = (Persona) sesion.getAttribute("persona");
		mostrarEquipo = false;
	}

	public void asociarEquipo() {
		System.out.println("equipo.getId():" + equipo.getId());
		if (placaEquipo.equals(placaEquipoEncontrado)) {
			equipo.setLaboratorio(laboratorioSeleccionado);
			equipo.setFechaRegistro(new Date());
			equipo.setDocumentoPersonaRegistro(personaActual.getId()
					.getDocumento());
			equipo.setTipoDocumentoPersonaRegistro(personaActual.getId()
					.getTipoDocumento());
			servicioGeneral.guardarObjeto(equipo);
			System.out.println("equipo.getId():" + equipo.getId());

			// enviar correo:
			Long PLANTILLA_NUEVO_EQUIPO_LABORATORIO = new Long(169);
			CorreoPlantilla correoPlantilla = (CorreoPlantilla) servicioGeneral
					.obtenerObjeto(new CorreoPlantilla(),
							PLANTILLA_NUEVO_EQUIPO_LABORATORIO);
			String cuerpo = correoPlantilla.getCuerpo();
			String asunto = correoPlantilla.getAsunto();

			Correo correo = new Correo();

			List<PersonaLaboratorio> lPL = mUL
					.listaPersonasLaboratorio(laboratorioSeleccionado.getId());
			for (PersonaLaboratorio pL : lPL) {
				correo.adicionarDireccion(pL.getPersona().getEmail());
			}

			correo.adicionarDireccion(laboratorioSeleccionado.getEmail());
			correo.adicionarDireccion(personaActual.getEmail());
			//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
			System.out.println("correo.getDirecciones():"
					+ correo.getDirecciones());

			cuerpo = cuerpo.replaceAll("<<LABORATORIO>>",
					laboratorioSeleccionado.getNombre());
			cuerpo = cuerpo.replaceAll("<<PLACA>>", equipo.getPlaca());
			cuerpo = cuerpo.replaceAll("<<DESCRIPCION>>", equipo.getEquipo());
			cuerpo = cuerpo.replaceAll("<<SERIAL>>", equipo.getSerial());
			cuerpo = cuerpo.replaceAll("<<MARCA>>", equipo.getMarca());
			cuerpo = cuerpo.replaceAll("<<MODELO>>", equipo.getModelo());

			System.out.println("asunto:" + asunto);
			System.out.println("cuerpo:" + cuerpo);

			correo.setAsunto(asunto);
			correo.setCuerpo(cuerpo);

			System.out.println("...enviando Correo...");
			if (servicioCorreo.enviarCorreo(correo)) {
				System.out.println("enviarCorreo OK");
			} else {
				System.out.println("enviarCorreo ERROR");
			}

			mostrarEquipo = false;
			placaEquipo = "";
			mensajeInfo("El equipo con placa " + equipo.getPlaca()
					+ " ha sido asociado exitosamente al laboratorio "
					+ laboratorioSeleccionado.getNombre() + ", de la sede "
					+ laboratorioSeleccionado.getSede().getNombre() + ".");

		} else {
			buscarEquipo();
		}
	}

	public void buscarEquipo() {
		placaEquipo = placaEquipo.trim();
		mostrarEquipo = false;
		if (!Util.validarNoVacio(placaEquipo)) {
			mensajeError("inventariosLaboratorios:placaEquipo",
					"Debe ingresar un número de placa para buscar.");
			return;
		}

		List<LaboratorioDetalleEquipos> listaEquipos = new ArrayList<LaboratorioDetalleEquipos>();
		String hql = "from LaboratorioDetalleEquipos WHERE placa = '"
				+ placaEquipo + "'";
		System.out.println("hql" + hql);
		listaEquipos = servicioGeneral.obtenerObjetos(
				LaboratorioDetalleEquipos.class, hql);

		if (listaEquipos.size() > 0) {
			/*
			 * LaboratorioDetalleEquipos lde = listaEquipos.get(0);
			 * if(lde.getLaboratorio() != null) {}
			 */

			mensajeError(
					"inventariosLaboratorios:placaEquipo",
					"El equipo con placa '"
							+ placaEquipo
							+ "' ya existe en el censo HERMES, no puede asociarse de nuevo.");
			return;
		}

		List<Bien> bienes = servicioGeneral.consultaEquipos(placaEquipo);
		System.out.println("bienes.size: " + bienes.size());

		if (bienes.size() == 0) {
			mensajeError(
					"inventariosLaboratorios:placaEquipo",
					"El equipo con placa '"
							+ placaEquipo
							+ "' no aparece en la consulta de HERMES en la base de datos de Inventarios. Favor verifique y busque de nuevo.");
			return;
		}

		equipo = mUL.bienAEquipo(bienes.get(0));
		mostrarEquipo = true;
		mensajeInfo("Si la información del equipo es correcta, debe buscar en la parte inferior el laboratorio al que desea asociarlo.  Puede filtrar por sede y/o nombre.");
		placaEquipoEncontrado = placaEquipo;

	}

	public String salir() {
		mUL.limpiarSesionLaboratorios();
		return "misProyectos";
	}

	/**
	 * @return the listaLaboratorios
	 */
	public List<Laboratorio> getListaLaboratorios() {
		return listaLaboratorios;
	}

	/**
	 * @return the selectItemSedes
	 */
	public SelectItem[] getSelectItemSedes() {
		return selectItemSedes;
	}

	/**
	 * @return the laboratoriosFiltrados
	 */
	public List<Laboratorio> getLaboratoriosFiltrados() {
		return laboratoriosFiltrados;
	}

	/**
	 * @param laboratoriosFiltrados
	 *            the laboratoriosFiltrados to set
	 */
	public void setLaboratoriosFiltrados(List<Laboratorio> laboratoriosFiltrados) {
		this.laboratoriosFiltrados = laboratoriosFiltrados;
	}

	/**
	 * @return the placaEquipo
	 */
	public String getPlacaEquipo() {
		return placaEquipo;
	}

	/**
	 * @param placaEquipo
	 *            the placaEquipo to set
	 */
	public void setPlacaEquipo(String placaEquipo) {
		this.placaEquipo = placaEquipo;
	}

	/**
	 * @return the equipo
	 */
	public LaboratorioDetalleEquipos getEquipo() {
		return equipo;
	}

	/**
	 * @return the mostrarEquipo
	 */
	public Boolean getMostrarEquipo() {
		return mostrarEquipo;
	}

	/**
	 * @param laboratorioSeleccionado
	 *            the laboratorioSeleccionado to set
	 */
	public void setLaboratorioSeleccionado(Laboratorio laboratorioSeleccionado) {
		this.laboratorioSeleccionado = laboratorioSeleccionado;
	}

}
