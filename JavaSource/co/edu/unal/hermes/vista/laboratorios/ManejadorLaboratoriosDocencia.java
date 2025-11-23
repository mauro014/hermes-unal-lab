package co.edu.unal.hermes.vista.laboratorios;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.event.ValueChangeEvent;

import co.edu.unal.hermes.modelo.VAsignaturasSIA;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleDocencia;

/**
 * @author dgbenitezc
 */
public class ManejadorLaboratoriosDocencia extends ManejadorLaboratorios {

	protected List<LaboratorioDetalleDocencia> listaAsignaturas;
	protected String codigoAsignatura;
	protected List<VAsignaturasSIA> asignaturasEncontradas;
	protected Boolean cambiosEnAsignaturas;
	protected VAsignaturasSIA vAsignaturasSIAseleccionada;
	protected LaboratorioDetalleDocencia laboratorioDetalleDocenciaSeleccionado;

	public ManejadorLaboratoriosDocencia() {
		idManejador = DOCENCIA;

		codigoAsignatura = "";
		cambiosEnAsignaturas = false;

		listaAsignaturas = new ArrayList<LaboratorioDetalleDocencia>();
		String hql = "from LaboratorioDetalleDocencia WHERE laboratorio = '"
				+ laboratorioActual.getId() + "' ORDER BY fechaRegistro DESC";
		listaAsignaturas = servicioGeneral.obtenerObjetos(
				LaboratorioDetalleDocencia.class, hql);
		if (listaAsignaturas.size() < 1) {
			listaAsignaturas = null;
		}
	}

	public void buscarAsignaturasPorCodigo() {
		codigoAsignatura = codigoAsignatura.trim();

		if (codigoAsignatura.length() < 4) {
			String error = "El código a buscar debe tener mínimo cuatro caracteres.";
			mensajeError("formLaboratoriosDocencia:udCodigoAsignatura", error);
			return;
		}

		// Busca asignaturas por Sede:
		String orderBy = " ORDER BY sia.codAsignatura, sia.annio DESC, sia.semestre DESC, sia.grupo";
		String hql = "from VAsignaturasSIA sia WHERE sia.codAsignatura LIKE '%"
				+ codigoAsignatura + "%' AND sia.sede.id = '"
				+ laboratorioActual.getSede().getId() + "' " + orderBy;

		System.out.println("hql:" + hql);
		asignaturasEncontradas = new ArrayList<VAsignaturasSIA>();
		asignaturasEncontradas = servicioGeneral.obtenerObjetos(
				VAsignaturasSIA.class, hql);
		if (asignaturasEncontradas.size() < 1) {
			// Si no encuentra ninguna, busca sin sede:
			hql = "from VAsignaturasSIA sia WHERE sia.codAsignatura LIKE '%"
					+ codigoAsignatura + "%' " + orderBy;
			System.out.println("hql:" + hql);
			asignaturasEncontradas = new ArrayList<VAsignaturasSIA>();
			asignaturasEncontradas = servicioGeneral.obtenerObjetos(
					VAsignaturasSIA.class, hql);

			if (asignaturasEncontradas.size() < 1) {
				mensajeError("formLaboratoriosDocencia:udCodigoAsignatura",
						"No se encontró ninguna asignatura con código "
								+ codigoAsignatura + ".  Favor verifíquelo.");
				asignaturasEncontradas = null;
				return;
			}
		}
	}

	public void adicionarAsignatura() {
		VAsignaturasSIA asignatura = vAsignaturasSIAseleccionada;
		LaboratorioDetalleDocencia detalle = new LaboratorioDetalleDocencia();

		// Se fija la asignatura, para poder comparar:
		detalle.setAsignatura(asignatura);

		if (listaAsignaturas != null && listaAsignaturas.contains(detalle)) {
			mensajeError("Ya está asociada la asignatura con código "
					+ asignatura.getCodAsignatura() + ", grupo "
					+ asignatura.getGrupo() + ", periodo "
					+ asignatura.getPeriodo());
			return;
		}

		detalle.setFechaRegistro(new Date());
		detalle.setHorasSemana(0F);
		detalle.setPracticasSemanales(0F);

		if (listaAsignaturas == null) {
			listaAsignaturas = new ArrayList<LaboratorioDetalleDocencia>();
		}

		listaAsignaturas.add(detalle);
		asignaturasEncontradas.remove(vAsignaturasSIAseleccionada);
		if (asignaturasEncontradas.size() < 1) {
			asignaturasEncontradas = null;
		}
		cambiosEnAsignaturas = true;
	}

	public void eliminarAsignatura() {
		listaAsignaturas.remove(laboratorioDetalleDocenciaSeleccionado);
		if (listaAsignaturas.size() < 1) {
			listaAsignaturas = null;
		}
		cambiosEnAsignaturas = true;
	}

	public void cambiosAsignaturas(ValueChangeEvent event) {
		System.out.println("Se realizó un cambio de: " + event.getOldValue() + " a: " + event.getNewValue());
		cambiosEnAsignaturas = true;
	}

	@Override
	public String salirGuardar() {
		if (validar()) {
			guardar();
			return (salir());
		} else {
			return null;
		}
	}

	public void guardar() {
		guardarLaboratorioActual(idManejador);
		if (cambiosEnAsignaturas) {
			// Se eliminan todos los detalles
			String sql = "DELETE HER_LABORATORIO_DET_DOCENCIA WHERE LAB_ID = " + laboratorioActual.getId();
			try {
				servicioGeneral.eliminar(sql);
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			// guardar detalles
			if (listaAsignaturas != null) {
				for (LaboratorioDetalleDocencia d : listaAsignaturas) {
					d.setId(null);
					d.setLaboratorio(laboratorioActual);
					servicioGeneral.guardarObjeto(d);
				}
			}
			cambiosEnAsignaturas = false;
		}
		
		calcularCompletitud();

	}

	@Override
	public String siguiente() {

		if (validar()) {
			if (idManejador == laboratorioActual.getEtapaRegistro().intValue()) {
				laboratorioActual.setEtapaRegistro(new Integer(
						(laboratorioActual.getEtapaRegistro()).intValue() + 1));
			}
			guardar();
			limpiarSesion();
			sesion.setAttribute("Laboratorio", laboratorioActual);
			sesion.setAttribute("solicitudLaboratorio", solicitudLab);
			sesion.removeAttribute("ManejadorMenuFormularioLaboratorios");
			return "laboratorioEnsayosServicios";
		} else {
			return null;
		}

	}

	public Boolean validar() {
		boolean validar = true;
//		laboratorioActual.trim();

		// Se permite que el laboratorio no tenga asignaturas asociadas.
		/*
		 * if (listaAsignaturas == null) {
		 * mensajeError("No existe ninguna Asignatura asociada con el Laboratorio."
		 * ); validar = false; }
		 */
		return validar;
	}

	/**
	 * @return the listaAsignaturas
	 */
	public List<LaboratorioDetalleDocencia> getListaAsignaturas() {
		return listaAsignaturas;
	}

	/**
	 * @param listaAsignaturas
	 *            the listaAsignaturas to set
	 */
	public void setListaAsignaturas(
			List<LaboratorioDetalleDocencia> listaAsignaturas) {
		this.listaAsignaturas = listaAsignaturas;
	}

	/**
	 * @return the codigoAsignatura
	 */
	public String getCodigoAsignatura() {
		return codigoAsignatura;
	}

	/**
	 * @param codigoAsignatura
	 *            the codigoAsignatura to set
	 */
	public void setCodigoAsignatura(String codigoAsignatura) {
		this.codigoAsignatura = codigoAsignatura;
	}

	/**
	 * @return the asignaturasEncontradas
	 */
	public List<VAsignaturasSIA> getAsignaturasEncontradas() {
		return asignaturasEncontradas;
	}

	/**
	 * @param asignaturasEncontradas
	 *            the asignaturasEncontradas to set
	 */
	public void setAsignaturasEncontradas(
			List<VAsignaturasSIA> asignaturasEncontradas) {
		this.asignaturasEncontradas = asignaturasEncontradas;
	}

	/**
	 * @param vAsignaturasSIAseleccionada
	 *            the vAsignaturasSIAseleccionada to set
	 */
	public void setvAsignaturasSIAseleccionada(
			VAsignaturasSIA vAsignaturasSIAseleccionada) {
		this.vAsignaturasSIAseleccionada = vAsignaturasSIAseleccionada;
	}

	/**
	 * @param laboratorioDetalleDocenciaSeleccionado
	 *            the laboratorioDetalleDocenciaSeleccionado to set
	 */
	public void setLaboratorioDetalleDocenciaSeleccionado(
			LaboratorioDetalleDocencia laboratorioDetalleDocenciaSeleccionado) {
		this.laboratorioDetalleDocenciaSeleccionado = laboratorioDetalleDocenciaSeleccionado;
	}

}
