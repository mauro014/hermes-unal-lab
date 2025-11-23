package co.edu.unal.hermes.modelo.laboratorios;

import java.io.Serializable;

/**
 * Objeto que relaciona a un ensayo/servicio de laboratorio
 * (LaboratorioDetalleEnsayosServicios) con los equipos
 * (LaboratorioDetalleEquipos) que se usan en su realización.
 * 
 * @author dgbenitezc
 */
public class LaboratorioEnsayoEquipo implements Serializable {

	private static final long serialVersionUID = -2071345950034802930L;

	private LaboratorioDetalleEnsayosServicios servicio;
	private LaboratorioDetalleEquipos equipo;
	private Integer tiempoUsoMinutos;

	private Float valorkWh;

	/**
	 * @return the servicio
	 */
	public LaboratorioDetalleEnsayosServicios getServicio() {
		return servicio;
	}

	/**
	 * @param servicio
	 *            the servicio to set
	 */
	public void setServicio(LaboratorioDetalleEnsayosServicios servicio) {
		this.servicio = servicio;
	}

	/**
	 * @return the equipo
	 */
	public LaboratorioDetalleEquipos getEquipo() {
		return equipo;
	}

	/**
	 * @param equipo
	 *            the equipo to set
	 */
	public void setEquipo(LaboratorioDetalleEquipos equipo) {
		this.equipo = equipo;
	}

	/**
	 * @return the tiempoUsoMinutos
	 */
	public Integer getTiempoUsoMinutos() {
		return tiempoUsoMinutos;
	}

	/**
	 * @param tiempoUsoMinutos
	 *            the tiempoUsoMinutos to set
	 */
	public void setTiempoUsoMinutos(Integer tiempoUsoMinutos) {
		this.tiempoUsoMinutos = tiempoUsoMinutos;
	}

	/**
	 * @return the valorkWh
	 */
	public Float getValorkWh() {
		return valorkWh;
	}

	/**
	 * @param valorkWh
	 *            the valorkWh to set
	 */
	public void setValorkWh(Float valorkWh) {
		this.valorkWh = valorkWh;
	}

	public Float getCostoEnergia() {
		Integer potenciaEquipoW = equipo.getPotenciaW();
		if (potenciaEquipoW == null) {
			equipo.setPotenciaW(0);
		}
		if (tiempoUsoMinutos == null) {
			tiempoUsoMinutos = 0;
		}

		Float costo = (equipo.getPotenciaW().floatValue() * valorkWh)
				/ (60 * 1000) * tiempoUsoMinutos;
		return costo;
	}

	public Float getDepreciacion() {
		Float depreciacion;
		try {
			depreciacion = (equipo.getValor().floatValue() / (equipo
					.getVidaUtilAnnios().floatValue() * 12))
					/ 43200
					* tiempoUsoMinutos;
			// Float depreciacion = (equipo.getValor().floatValue() /
			// (equipo.getVidaUtilAnnios().floatValue()) ) / 43200 *
			// tiempoUsoMinutos;
		} catch (Exception e) {
			System.out.println("depreciacion ERROR:");
			depreciacion = 0F;
		}

		// división por cero:
		System.out.println("depreciacion in: " + depreciacion);
		if (depreciacion.isNaN()) {
			depreciacion = 0F;
		}
		System.out.println("depreciacion out: " + depreciacion);

		return depreciacion;
	}

}
