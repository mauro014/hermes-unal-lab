package co.edu.unal.hermes.vista.laboratorios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.TipoRubro;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioRubro;

/**
 * @author dgbenitezc
 */
public class ManejadorLaboratorioPresupuesto extends ManejadorLaboratorios {

	private List<LaboratorioRubro> listaRubros;
	private List<TipoRubro> listaTiposRubros;
	private SelectItem[] selectItemTipoRubro;
	private SelectItem[] selectItemTipoFuente;
	private Boolean esFuenteInterna;
	private SelectItem[] selectItemFuentesInternas;
	private SelectItem[] selectItemFuentesExternas;
	private List<FuenteFinanciacion> listaFuentesInternas;
	private List<FuenteFinanciacion> listaFuentesExternas;
	private LaboratorioRubro nuevoRubro;
	private LaboratorioRubro rubroEliminar;
	private List<LaboratorioRubro> listaRubrosEliminar;
	private Long totalPresupuesto;

	public ManejadorLaboratorioPresupuesto() {
		System.out.println("ManejadorLaboratorioPresupuesto:");
		idManejador = PRESUPUESTO;
		nuevoRubro = new LaboratorioRubro();
		obtenerListaRubros();
		listaRubrosEliminar = new ArrayList<LaboratorioRubro>();
	}

	public void eliminarRubro() {
		System.out.println("eliminarRubro:");
		listaRubros.remove(rubroEliminar);
		listaRubrosEliminar.add(rubroEliminar);
		System.out.println("eliminarRubro: listaRubros.size: "
				+ listaRubros.size());
		System.out.println("eliminarRubro: listaRubrosEliminar.size: "
				+ listaRubrosEliminar.size());
	}

	public void agregarRubro() {
		Boolean validarRubro = true;
		System.out.println("agregarRubro in: listaRubros.size: "
				+ listaRubros.size());

		Long idRubro = nuevoRubro.getTipoRubro().getId();
		System.out.println("idRubro: " + idRubro);
		if (idRubro.equals(0L)) {
			validarRubro = false;
			mensajeError("form:siTipoRubro",
					"Debe seleccionar un tipo de Rubro.");
		}

		String idFuente = nuevoRubro.getFuenteFinanciacion().getId();
		System.out.println("idFuente: " + idFuente);
		if (idFuente.isEmpty()) {
			validarRubro = false;
			mensajeError("form:siFF",
					"Debe seleccionar una Fuente de Financiación.");
		}

		if (nuevoRubro.getValor().equals(0L)) {
			validarRubro = false;
			mensajeError("form:itValorRubro",
					"Debe ingresar un valor entero positivo.");
		}

		nuevoRubro.setDescripcion(nuevoRubro.getDescripcion().trim());
		if (nuevoRubro.getDescripcion().isEmpty()) {
			validarRubro = false;
			mensajeError("form:itDescripcionRubro",
					"Debe ingresar una descripción (máximo 200 caracteres).");
		}

		for (LaboratorioRubro lr : listaRubros) {
			if (idRubro.equals(lr.getTipoRubro().getId())
					&& idFuente.equals(lr.getFuenteFinanciacion().getId())) {
				mensajeError("form:siTipoRubro",
						"Ya está asociada una combinación Fuente/Rubro de ese tipo.");
				mensajeError("form:siFF",
						"Ya está asociada una combinación Fuente/Rubro de ese tipo.");
				validarRubro = false;
			}
		}

		if (validarRubro) {
			// fijar tipos
			forRubros: for (TipoRubro tr : listaTiposRubros) {
				if (idRubro.equals(tr.getId())) {
					nuevoRubro.setTipoRubro(tr);
					break forRubros;
				}
			}

			// fijar fuente:

			if (esFuenteInterna) {
				forFFI: for (FuenteFinanciacion ffi : listaFuentesInternas) {
					if (idFuente.equals(ffi.getId())) {
						nuevoRubro.setFuenteFinanciacion(ffi);
						break forFFI;
					}
				}
			} else {
				forFFE: for (FuenteFinanciacion ffe : listaFuentesExternas) {
					if (idFuente.equals(ffe.getId())) {
						nuevoRubro.setFuenteFinanciacion(ffe);
						break forFFE;
					}
				}
			}

			nuevoRubro.setIdLaboratorio(laboratorioActual.getId());
			nuevoRubro.setFechaRegistro(new Date());
			listaRubros.add(nuevoRubro);
			nuevoRubro = new LaboratorioRubro();
			calcularTotal();
		}

		System.out.println("agregarRubro out: listaRubros.size: "
				+ listaRubros.size());
	}

	private void calcularTotal() {
		totalPresupuesto = 0L;
		for (LaboratorioRubro lr : listaRubros) {
			totalPresupuesto += lr.getValor();
		}
	}

	private void obtenerListaRubros() {
		System.out.println("obtenerListaRubros in:");

		if (laboratorioActual.getId() != null) {
			String hql = "FROM LaboratorioRubro WHERE idLaboratorio = '"
					+ laboratorioActual.getId() + "' ORDER BY fechaRegistro";
			System.out.println("hql:" + hql);
			listaRubros = servicioGeneral.obtenerObjetos(
					LaboratorioRubro.class, hql);

		} else {
			listaRubros = new ArrayList<LaboratorioRubro>();
		}

		System.out.println("obtenerListaRubros out: listaRubros.size: "
				+ listaRubros.size());
		calcularTotal();

		// selectItemTipoRubro:
		String rubrosLabs = "(131,111,9,12,107,3,14,10,156)";
		String hql8 = "FROM TipoRubro WHERE id in " + rubrosLabs
				+ " ORDER BY nombre";
		System.out.println("hql8:" + hql8);
		listaTiposRubros = servicioGeneral
				.obtenerObjetos(TipoRubro.class, hql8);
		int i = 1;
		selectItemTipoRubro = new SelectItem[listaTiposRubros.size() + 1];
		selectItemTipoRubro[0] = new SelectItem("", "Seleccione...");
		for (TipoRubro lao : listaTiposRubros) {
			selectItemTipoRubro[i] = new SelectItem(lao.getId(),
					lao.getNombre());
			i++;
		}

		// fuentes financiación:
		String hql9 = "FROM FuenteFinanciacion WHERE internaExterna = '"
				+ FuenteFinanciacion.interna + "' ORDER BY descripcion";
		System.out.println("hql9:" + hql9);
		listaFuentesInternas = servicioGeneral.obtenerObjetos(
				FuenteFinanciacion.class, hql9);
		int j = 1;
		selectItemFuentesInternas = new SelectItem[listaFuentesInternas.size() + 1];
		selectItemFuentesInternas[0] = new SelectItem("", "Seleccione...");
		for (FuenteFinanciacion ff : listaFuentesInternas) {
			selectItemFuentesInternas[j] = new SelectItem(ff.getId(),
					ff.getDescripcion());
			j++;
		}

		String hql10 = "FROM FuenteFinanciacion WHERE internaExterna = '"
				+ FuenteFinanciacion.externa + "' ORDER BY descripcion";
		System.out.println("hql10:" + hql10);
		listaFuentesExternas = servicioGeneral.obtenerObjetos(
				FuenteFinanciacion.class, hql10);
		int k = 1;
		selectItemFuentesExternas = new SelectItem[listaFuentesExternas.size() + 1];
		selectItemFuentesExternas[0] = new SelectItem("", "Seleccione...");
		for (FuenteFinanciacion ff : listaFuentesExternas) {
			selectItemFuentesExternas[k] = new SelectItem(ff.getId(),
					ff.getDescripcion());
			k++;
		}

		selectItemTipoFuente = new SelectItem[2];
		selectItemTipoFuente[0] = new SelectItem(FuenteFinanciacion.interna,
				"Interna");
		selectItemTipoFuente[1] = new SelectItem(FuenteFinanciacion.externa,
				"Externa");

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
		laboratorioActual.setEtapaRegistro(PRESUPUESTO + 1);
		guardarLaboratorioActual(idManejador);

		System.out.println("guardando rubros in:");
		for (LaboratorioRubro lr : listaRubros) {
			servicioGeneral.guardarObjeto(lr);
		}

		System.out.println("eliminando rubros in:");
		for (LaboratorioRubro lre : listaRubrosEliminar) {
			if (lre.getId() != null) {
				servicioGeneral.eliminarObjeto(lre);
			}
		}
		System.out.println("guardando rubros out.");
	}

	@Override
	public String siguiente() {
		return "";
	}

	public Boolean validar() {
		boolean validar = true;
		return validar;
	}

	/**
	 * @return the listaRubros
	 */
	public List<LaboratorioRubro> getListaRubros() {
		return listaRubros;
	}

	/**
	 * @return the listaTiposRubros
	 */
	public List<TipoRubro> getListaTiposRubros() {
		return listaTiposRubros;
	}

	/**
	 * @return the selectItemTipoRubro
	 */
	public SelectItem[] getSelectItemTipoRubro() {
		return selectItemTipoRubro;
	}

	/**
	 * @return the nuevoRubro
	 */
	public LaboratorioRubro getNuevoRubro() {
		return nuevoRubro;
	}

	/**
	 * @return the selectItemTipoFuente
	 */
	public SelectItem[] getSelectItemTipoFuente() {
		return selectItemTipoFuente;
	}

	/**
	 * @return the esFuenteInterna
	 */
	public Boolean getEsFuenteInterna() {
		String internaExterna = nuevoRubro.getFuenteFinanciacion()
				.getInternaExterna();
		System.out.println("internaExterna:" + internaExterna);

		if (internaExterna.equals(FuenteFinanciacion.interna)) {
			esFuenteInterna = true;
		} else {
			esFuenteInterna = false;
		}
		System.out.println("esFuenteInterna:" + esFuenteInterna);
		return esFuenteInterna;
	}

	/**
	 * @return the selectItemFuentes
	 */
	public SelectItem[] getSelectItemFuentes() {
		if (getEsFuenteInterna()) {
			return selectItemFuentesInternas;
		} else {
			return selectItemFuentesExternas;
		}
	}

	/**
	 * @return the totalPresupuesto
	 */
	public Long getTotalPresupuesto() {
		return totalPresupuesto;
	}

	/**
	 * @param rubroEliminar
	 *            the rubroEliminar to set
	 */
	public void setRubroEliminar(LaboratorioRubro rubroEliminar) {
		this.rubroEliminar = rubroEliminar;
	}

}
