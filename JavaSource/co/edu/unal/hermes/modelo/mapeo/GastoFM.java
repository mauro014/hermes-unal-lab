package co.edu.unal.hermes.modelo.mapeo;

import co.edu.unal.hermes.modelo.Gasto;
import co.edu.unal.hermes.modelo.TipoRubro;

/**
 * The Class GastoFM.
 */
public class GastoFM {

	/** The id rubro. */
	private int idRubro;

	/** The anio1. */
	private Long anio1;

	/** The anio2. */
	private Long anio2;

	/** The anio3. */
	private Long anio3;

	/** The anio4. */
	private Long anio4;

	/** The anio5. */
	private Long anio5;

	/** The anio6. */
	private Long anio6;

	/** The total. */
	private Long total;

	/** The valor pendiente. */
	private Long valorPendiente;

	/** The nombre rubro. */
	private String nombreRubro;

	/** The editable. */
	private boolean editable = true;

	/** The gasto. */
	private Gasto gasto;

	/** The es contrapartida. */
	private boolean esContrapartida;

	private boolean esCP2022;

	/**
	 * Instantiates a new gasto fm.
	 *
	 * @param idRubro         the id rubro
	 * @param nombre          the nombre
	 * @param esContrapartida the esContrapartida
	 */
	public GastoFM(int idRubro, String nombre, boolean esContrapartida) {
		this.idRubro = idRubro;
		this.nombreRubro = nombre;
		this.anio1 = 0L;
		this.anio2 = 0L;
		this.anio3 = 0L;
		this.total = 0L;
		this.valorPendiente = 0L;
		this.esContrapartida = esContrapartida;
		this.gasto = new Gasto();
	}

	/**
	 * Instantiates a new gasto FM.
	 *
	 * @param tipoRubro the tipo rubro
	 */
	public GastoFM(TipoRubro tipoRubro) {
		this.idRubro = tipoRubro.getId().intValue();
		this.nombreRubro = tipoRubro.getNombre();
		this.anio1 = 0L;
		this.anio2 = 0L;
		this.anio3 = 0L;
		this.anio4 = 0L;
		this.anio5 = 0L;
		this.anio6 = 0L;
		this.total = 0L;
		this.valorPendiente = 0L;
		this.esCP2022 = tipoRubro.getDescripcion() != null && tipoRubro.getDescripcion().equals("GASTOS_CP_2022");
		this.gasto = new Gasto();
	}

	/**
	 * Instantiates a new gasto fm.
	 *
	 * @param idRubro the id rubro
	 * @param nombre  the nombre
	 */
	public GastoFM(int idRubro, String nombre) {

		this.idRubro = idRubro;
		this.nombreRubro = nombre;
		this.anio1 = 0L;
		this.anio2 = 0L;
		this.anio3 = 0L;
		this.anio4 = 0L;
		this.anio5 = 0L;
		this.anio6 = 0L;
		this.total = 0L;
		this.valorPendiente = 0L;
		this.esCP2022 = false;
		this.gasto = new Gasto();
	}

	/**
	 * Gets the anio1.
	 *
	 * @return the anio1
	 */
	public Long getAnio1() {
		return anio1;
	}

	/**
	 * Sets the anio1.
	 *
	 * @param anio1 the new anio1
	 */
	public void setAnio1(Long anio1) {
		this.anio1 = anio1;
	}

	/**
	 * Gets the anio2.
	 *
	 * @return the anio2
	 */
	public Long getAnio2() {
		return anio2 != null ? anio2 : 0L;
	}

	/**
	 * Sets the anio2.
	 *
	 * @param anio2 the new anio2
	 */
	public void setAnio2(Long anio2) {
		this.anio2 = anio2;
	}

	/**
	 * Gets the anio3.
	 *
	 * @return the anio3
	 */
	public Long getAnio3() {
		return anio3 != null ? anio3 : 0L;
	}

	/**
	 * Sets the anio3.
	 *
	 * @param anio3 the new anio3
	 */
	public void setAnio3(Long anio3) {
		this.anio3 = anio3;
	}

	/**
	 * Gets the nombre rubro.
	 *
	 * @return the nombre rubro
	 */
	public String getNombreRubro() {
		return nombreRubro;
	}

	/**
	 * Sets the nombre rubro.
	 *
	 * @param nombreRubro the new nombre rubro
	 */
	public void setNombreRubro(String nombreRubro) {
		this.nombreRubro = nombreRubro;
	}

	/**
	 * Checks if is editable.
	 *
	 * @return true, if is editable
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * Sets the editable.
	 *
	 * @param editable the new editable
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	/**
	 * Gets the id rubro.
	 *
	 * @return the id rubro
	 */
	public int getIdRubro() {
		return idRubro;
	}

	/**
	 * Sets the id rubro.
	 *
	 * @param idRubro the new id rubro
	 */
	public void setIdRubro(int idRubro) {
		this.idRubro = idRubro;
	}

	/**
	 * Gets the total.
	 *
	 * @return the total
	 */
	public Long getTotal() {
		return total;
	}

	/**
	 * Sets the total.
	 *
	 * @param total the new total
	 */
	public void setTotal(Long total) {
		this.total = total;
	}

	/**
	 * Gets the valor pendiente.
	 *
	 * @return the valor pendiente
	 */
	public Long getValorPendiente() {
		return valorPendiente;
	}

	/**
	 * Sets the valor pendiente.
	 *
	 * @param valorPendiente the new valor pendiente
	 */
	public void setValorPendiente(Long valorPendiente) {
		this.valorPendiente = valorPendiente;
	}

	/**
	 * Gets the anio4.
	 *
	 * @return the anio4
	 */
	public Long getAnio4() {
		return anio4 != null ? anio4 : 0L;
	}

	/**
	 * Sets the anio4.
	 *
	 * @param anio4 the new anio4
	 */
	public void setAnio4(Long anio4) {
		this.anio4 = anio4;
	}

	/**
	 * Gets the anio5.
	 *
	 * @return the anio5
	 */
	public Long getAnio5() {
		return anio5 != null ? anio5 : 0L;
	}

	/**
	 * Sets the anio5.
	 *
	 * @param anio5 the new anio5
	 */
	public void setAnio5(Long anio5) {
		this.anio5 = anio5;
	}

	/**
	 * Gets the anio6.
	 *
	 * @return the anio6
	 */
	public Long getAnio6() {
		return anio6 != null ? anio6 : 0L;
	}

	/**
	 * Sets the anio6.
	 *
	 * @param anio6 the new anio6
	 */
	public void setAnio6(Long anio6) {
		this.anio6 = anio6;
	}

	/**
	 * Gets the gasto.
	 *
	 * @return the gasto
	 */
	public Gasto getGasto() {
		return gasto;
	}

	/**
	 * Sets the gasto.
	 *
	 * @param gasto the gasto to set
	 */
	public void setGasto(Gasto gasto) {
		this.gasto = gasto;
	}

	/**
	 * Gets the suma anios.
	 *
	 * @return the suma anios
	 */
	public Long getSumaAnios() {
		return getAnio1() + getAnio2() + getAnio3() + getAnio4() + getAnio5() + getAnio6();
	}

	/**
	 * Checks if is es contrapartida.
	 *
	 * @return the esContrapartida
	 */
	public boolean isEsContrapartida() {
		if(idRubro == 1) { // Rubro de gastos de personal se hace esto para la convocatoria conjunta con la universidad milagro de ecuador
			return true;
		}
		return esContrapartida;
	}

	/**
	 * Sets the es contrapartida.
	 *
	 * @param esContrapartida the new es contrapartida
	 */
	public void setEsContrapartida(boolean esContrapartida) {
		this.esContrapartida = esContrapartida;
	}

	public boolean getEsCP2022() {
		return esCP2022;
	}

	public void setEsCP2022(boolean esCP2022) {
		this.esCP2022 = esCP2022;
	}
}