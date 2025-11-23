/**
 * @author dgbenitezc
 */

package co.edu.unal.hermes.vista.laboratorios;

import java.util.List;

import co.edu.unal.hermes.modelo.Bien;
import co.edu.unal.hermes.modelo.Rol;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleEquipos;
import co.edu.unal.hermes.modelo.laboratorios.PersonaLaboratorio;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorUtilidadesLaboratorios extends ManejadorBase {

	public ManejadorUtilidadesLaboratorios() {
	}

	public void limpiarSesionLaboratorios() {
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
		sesion.removeAttribute("manejadorEvaluacionProveedores");
		sesion.removeAttribute("manejadorInventariosLaboratorios");
		sesion.removeAttribute("manejadorEnvioAlertasLaboratorios");
	}

	public String buscarEnDescripcion(String queBusca, Bien bien) {
		String descripcionBuscar;
		descripcionBuscar = bien.getDescripcion();
		System.out.println("descripcionBuscar: " + descripcionBuscar);
		if (descripcionBuscar != null) {
			int inicioBusca = descripcionBuscar.indexOf(queBusca);
			if (inicioBusca >= 0) {
				int finalBusca = descripcionBuscar.indexOf(";", inicioBusca);
				descripcionBuscar = descripcionBuscar.substring(inicioBusca
						+ queBusca.length(), finalBusca);
				System.out.println("descripcionBuscar: " + descripcionBuscar);
				descripcionBuscar = descripcionBuscar.trim();
				System.out.println("descripcionBuscar: " + descripcionBuscar);
			} else {
				descripcionBuscar = "";
			}
		}
		return descripcionBuscar;
	}

	public LaboratorioDetalleEquipos bienAEquipo(Bien bien) {
		LaboratorioDetalleEquipos detalle = new LaboratorioDetalleEquipos();
		detalle.setPlaca(bien.getPlaca());
		// valores por omisión:
		detalle.setEspecializado(false);
		detalle.setMayorDiezAnnios(false);
		detalle.setEnUso(true);
		detalle.setEstadoFisicoCenso(Tipos.TIPO_ESTADO_BUENO);
		detalle.setMantenimiento(Tipos.FRECUENCIA_MANTENIMIENTO_NO_APLICA);
		detalle.setCalibracion(Tipos.FRECUENCIA_MANTENIMIENTO_NO_APLICA);
		detalle.setSerial(bien.getSerial());
		detalle.setEquipo(buscarEnDescripcion("DESCRIPCION :", bien));
		detalle.setMarca(buscarEnDescripcion("MARCA :", bien));
		detalle.setModelo(buscarEnDescripcion("MODELO :", bien));
		detalle.setResponsable(bien.getResponsable());
		detalle.setIdResponsable(bien.getIdResponsable());
		detalle.setValor(bien.getValor());
		detalle.setFechaAdquisicion(bien.getFechaAdquisicion());
		detalle.setFechaServicio(bien.getFechaServicio());
		Long idTipo = Tipos.TIPOS_ESTADOS + bien.getIdEstadoFisico();
		detalle.setEstadoFisico(idTipo);
		return detalle;
	}

	public List<PersonaLaboratorio> listaPersonasLaboratorio(Long labId) {
		List<PersonaLaboratorio> listaPersonasLaboratorio;
		String hql = "FROM PersonaLaboratorio pl WHERE pl.laboratorio.id = '"
				+ labId + "' ORDER BY pl.rol.id";
		System.out.println("hql:" + hql);
		listaPersonasLaboratorio = servicioGeneral.obtenerObjetos(
				PersonaLaboratorio.class, hql);
		System.out.println("listaPersonasLaboratorio.size:"
				+ listaPersonasLaboratorio.size());
		return listaPersonasLaboratorio;
	}

	public PersonaLaboratorio coordinadorLaboratorio(Long labId) {
		List<PersonaLaboratorio> listaPersonasLaboratorio;
		String hql = "FROM PersonaLaboratorio pl WHERE pl.laboratorio.id = '"
				+ labId + "' AND pl.rol.id = '" + Rol.COORDINADOR_LABORATORIO
				+ "'";
		System.out.println("hql:" + hql);
		listaPersonasLaboratorio = servicioGeneral.obtenerObjetos(
				PersonaLaboratorio.class, hql);

		int tamannio = listaPersonasLaboratorio.size();
		System.out
				.println("coordinadorLaboratorio listaPersonasLaboratorio.size:"
						+ tamannio);

		if (tamannio == 1) {
			return listaPersonasLaboratorio.get(0);
		} else {
			return null;
		}
	}

}
