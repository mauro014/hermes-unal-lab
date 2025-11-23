/**
 * @author dgbenitezc
 */

package co.edu.unal.hermes.vista.laboratorios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.ClasificacionConocimiento;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DiagnosticoSoftware;
import co.edu.unal.hermes.modelo.Empresa;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.VAsignaturasSIA;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorDiagnosticoSoftware extends ManejadorBase {

	private DiagnosticoSoftware diagnosticoSoftwareActual;

	private SelectItem[] selectItemSedes;
	private Sede sedeSeleccionada;
	private SelectItem[] selectItemFacultades;
	private Dependencia facultadSeleccionada;
	private SelectItem[] selectItemDepartamentos;
	private Dependencia departamentoSeleccionado;
	private SelectItem[] usaSoftwareDocenciaItem;
	private SelectItem[] usaSoftwareInvestigacionItem;

	private Boolean usaSoftwareDocenciaBoolean = false;
	private Boolean usaSoftwareInvestigacionBoolean = false;
	private Boolean guardado = false;

	private List<DiagnosticoSoftware> listaSoftwareDocencia;
	private List<DiagnosticoSoftware> listaSoftwareInvestigacion;

	private DiagnosticoSoftware dSagregar = null;
	private DiagnosticoSoftware nuevoSoftInv = null;

	protected String nombreEmpresa;

	private String asignaturaString;
	protected List<VAsignaturasSIA> asignaturasEncontradas;

	private VAsignaturasSIA vAsignaturasSIAseleccionada;

	private String nombreEmpresaInv;

	private ClasificacionConocimiento areaTematicaSeleccionada;

	private Sede sedeSeleccionadaINV;
	private SelectItem[] selectItemSedesINV;
	private SelectItem[] selectItemFacultadesINV;
	private Dependencia facultadSeleccionadaINV;
	private SelectItem[] selectItemDepartamentosINV;
	private Dependencia departamentoSeleccionadoINV;

	private DiagnosticoSoftware softwareDocenciaSeleccionado;
	private DiagnosticoSoftware asignaturaSeleccionada;
	private DiagnosticoSoftware softwareInvestigacionSeleccionado;

	private DiagnosticoSoftware clasificacionConocimientoSeleccionado;

	private SelectItem[] selectItemEjecucionINV;
	private SelectItem[] selectItemEjecucionDOC;

	public ManejadorDiagnosticoSoftware() {

		selectItemSedesINV = servicioGeneral.selectItemSedes();
		sedeSeleccionadaINV = new Sede((Long) selectItemSedesINV[0].getValue());
		cambiarSedeINV();

		personaActual = (Persona) sesion.getAttribute("persona");
		diagnosticoSoftwareActual = new DiagnosticoSoftware();

		selectItemSedes = servicioGeneral.selectItemSedes();
		sedeSeleccionada = new Sede((Long) selectItemSedes[0].getValue());
		selectItemFacultades = servicioGeneral
				.selectItemFacultades(sedeSeleccionada.getId());
		facultadSeleccionada = new Dependencia(
				(String) selectItemFacultades[0].getValue());
		selectItemDepartamentos = servicioGeneral
				.selectItemDepartamentos(facultadSeleccionada.getId());
		if (selectItemDepartamentos.length > 0) {
			departamentoSeleccionado = new Dependencia(
					(String) selectItemDepartamentos[0].getValue());
		} else {
			departamentoSeleccionado = new Dependencia();
		}

		if (personaActual instanceof InvestigadorInterno) {
			sedeSeleccionada = ((InvestigadorInterno) personaActual)
					.getDependencia().getSede();
			cambiarSede();
			if (((InvestigadorInterno) personaActual).getDependencia()
					.getFacultad() != null) {
				facultadSeleccionada = ((InvestigadorInterno) personaActual)
						.getDependencia().getFacultad();
				cambiarFacultad();
			}
			if (((InvestigadorInterno) personaActual).getDependencia()
					.getDepartamento() != null) {
				String departamentoSeleccionadoString = ((InvestigadorInterno) personaActual)
						.getDependencia().getDepartamento();
				departamentoSeleccionado = servicioDependencia
						.obtenerDependencia(departamentoSeleccionadoString);
			}
		}

		usaSoftwareDocenciaItem = new SelectItem[2];
		usaSoftwareDocenciaItem[0] = new SelectItem(true, "SI");
		usaSoftwareDocenciaItem[1] = new SelectItem(false, "NO");

		usaSoftwareInvestigacionItem = new SelectItem[2];
		usaSoftwareInvestigacionItem[0] = new SelectItem(true, "SI");
		usaSoftwareInvestigacionItem[1] = new SelectItem(false, "NO");

		listaSoftwareDocencia = new ArrayList<DiagnosticoSoftware>();
		listaSoftwareInvestigacion = new ArrayList<DiagnosticoSoftware>();

		selectItemEjecucionINV = servicioGeneral
				.selectItemHijosDeTiposValorObjeto(Tipos.TIPOS_EJECUCION_SOFTWARE);
		selectItemEjecucionDOC = servicioGeneral
				.selectItemHijosDeTiposValorObjeto(Tipos.TIPOS_EJECUCION_SOFTWARE);
	}

	public void adicionarEmpresaDocencia() {
		nombreEmpresa = nombreEmpresa.trim();
		nombreEmpresa = nombreEmpresa.toUpperCase();
		if (nombreEmpresa != "") {
			Empresa empresa = servicioGeneral
					.buscarEmpresaXNombre(nombreEmpresa);
			if (empresa == null) {
				empresa = new Empresa();
				empresa.setNombre(nombreEmpresa);
				empresa.setUbicacionCadenaProductiva("Software");
			}
			dSagregar.agregarEmpresaDistribuidora(empresa);
			nombreEmpresa = "";
		} else {
			mensajeError("Debe digitar el nombre de la empresa.");
		}
	}

	public void adicionarEmpresaInv() {
		nombreEmpresaInv = nombreEmpresaInv.trim().toUpperCase();
		if (nombreEmpresaInv != "") {
			Empresa empresa = servicioGeneral
					.buscarEmpresaXNombre(nombreEmpresaInv);
			if (empresa == null) {
				empresa = new Empresa();
				empresa.setNombre(nombreEmpresaInv);
				empresa.setUbicacionCadenaProductiva("Software");
			}
			nuevoSoftInv.agregarEmpresaDistribuidora(empresa);
			nombreEmpresaInv = "";
		} else {
			mensajeError("Debe digitar el nombre de la empresa.");
		}
	}

	public void adicionarAsignatura() {
		System.out.println("adicionarAsignatura");
		dSagregar.agregarAsignatura(vAsignaturasSIAseleccionada);
		asignaturaString = "";
		asignaturasEncontradas = null;
	}

	public void eliminarAsignatura() {
		System.out.println("eliminarAsignatura:"
				+ asignaturaSeleccionada.getAsignatura().getNombreAsignatura());
		System.out
				.println("eliminarAsignatura dSagregar.getAsignaturas().size():"
						+ dSagregar.getAsignaturas().size());
		dSagregar.eliminarAsignatura(asignaturaSeleccionada);
		System.out
				.println("eliminarAsignatura dSagregar.getAsignaturas().size():"
						+ dSagregar.getAsignaturas().size());
	}

	public void agregarAreaTematica() {
		System.out.println("agregarAreaTematica");
		if (areaTematicaSeleccionada != null) {
			nuevoSoftInv.agregarAreaConocimiento(areaTematicaSeleccionada,
					sedeSeleccionadaINV, facultadSeleccionadaINV,
					departamentoSeleccionadoINV);
		} else {
			mensajeError("Tiene que buscar (mínimo 4 caracteres) y seleccionar un área del conocimiento, no se pueden agregar nuevas.");
		}
	}

	public void eliminarAreaTematica() {
		System.out.println("eliminarAreaTematica:"
				+ clasificacionConocimientoSeleccionado.getAreaConocimiento()
						.getNombre());
		System.out
				.println("eliminarAreaTematica nuevoSoftInv.getAreasConocimiento().size():"
						+ nuevoSoftInv.getAreasConocimiento().size());
		nuevoSoftInv
				.eliminarAreaConocimiento(clasificacionConocimientoSeleccionado);
		System.out
				.println("eliminarAreaTematica nuevoSoftInv.getAreasConocimiento().size():"
						+ nuevoSoftInv.getAreasConocimiento().size());
	}

	public void nuevoSoftware() {
		dSagregar = new DiagnosticoSoftware();
	}

	public void nuevoSoftwareInvestigacion() {
		nuevoSoftInv = new DiagnosticoSoftware();
	}

	public void agregarSoftware() {
		Boolean validarSofwareDocencia = true;

		if (dSagregar.getNombreSoftware() == "") {
			mensajeError("formEDS:NombreSoftware",
					"El nombre del software es obligatorio.");
			validarSofwareDocencia = false;
		}

		if (dSagregar.getListaAsignaturas().size() < 1) {
			mensajeError("formEDS:udCodigoAsignatura",
					"Debe buscar y asociar al menos una asignatura al software de docencia.");
			validarSofwareDocencia = false;
		}

		if (validarSofwareDocencia) {
			// DiagnosticoSoftware ds = (DiagnosticoSoftware) dSagregar.clone();
			// listaSoftwareDocencia.add(ds);
			listaSoftwareDocencia.add(dSagregar);
			dSagregar = null;
		}
	}

	public void eliminarSoftwareDocencia() {
		System.out
				.println("eliminarSoftwareDocencia listaSoftwareDocencia.size: "
						+ listaSoftwareDocencia.size());
		listaSoftwareDocencia.remove(softwareDocenciaSeleccionado);
		System.out.println("eliminarSoftwareDocencia: "
				+ softwareDocenciaSeleccionado.getNombreSoftware());
		System.out
				.println("eliminarSoftwareDocencia listaSoftwareDocencia.size: "
						+ listaSoftwareDocencia.size());
	}

	public void agregarSoftwareInvestigacion() {
		Boolean validarSofwareInvestigación = true;

		if (nuevoSoftInv.getNombreSoftware() == "") {
			mensajeError("formEDS:NombreSoftwareInv",
					"El nombre del software es obligatorio.");
			validarSofwareInvestigación = false;
		}

		if (nuevoSoftInv.getAreasConocimiento().size() < 1) {
			mensajeError(
					"formEDS:clasificacionConocimiento",
					"Debe buscar y adicionar al menos un área del conocimiento al software de investigación.");
			validarSofwareInvestigación = false;
		}

		if (validarSofwareInvestigación) {
			listaSoftwareInvestigacion.add(nuevoSoftInv);
			nuevoSoftInv = null;
		}
	}

	public void eliminarSoftwareInvestigacion() {
		System.out
				.println("eliminarSoftwareInvestigacion listaSoftwareInvestigacion.size: "
						+ listaSoftwareInvestigacion.size());
		listaSoftwareInvestigacion.remove(softwareInvestigacionSeleccionado);
		System.out.println("eliminarSoftwareInvestigacion: "
				+ softwareInvestigacionSeleccionado.getNombreSoftware());
		System.out
				.println("eliminarSoftwareInvestigacion listaSoftwareInvestigacion.size: "
						+ listaSoftwareInvestigacion.size());
	}

	public void cancelarSoftware() {
		dSagregar = null;
	}

	public void cancelarSoftwareInvestigacion() {
		nuevoSoftInv = null;
	}

	public void cambiarSede() {
		System.out.println("sedeSeleccionada:" + sedeSeleccionada.getNombre());
		System.out.println("sedeSeleccionada:" + sedeSeleccionada.getId());
		selectItemFacultades = servicioGeneral
				.selectItemFacultades(sedeSeleccionada.getId());
		facultadSeleccionada = new Dependencia();
		departamentoSeleccionado = new Dependencia();
	}

	public void cambiarSedeINV() {
		System.out.println("cambiarSedeINV nombre:"
				+ sedeSeleccionadaINV.getNombre());
		System.out.println("cambiarSedeINV id:" + sedeSeleccionadaINV.getId());
		Long id = sedeSeleccionadaINV.getId();
		sedeSeleccionadaINV = new Sede(id);
		selectItemFacultadesINV = servicioGeneral
				.selectItemFacultades(sedeSeleccionadaINV.getId());
		facultadSeleccionadaINV = new Dependencia(
				(String) selectItemFacultadesINV[0].getValue());
		cambiarFacultadINV();
	}

	public void cambiarFacultad() {
		System.out.println("facultadSeleccionada:"
				+ facultadSeleccionada.getNombre());
		System.out.println("facultadSeleccionada:"
				+ facultadSeleccionada.getId());
		selectItemDepartamentos = servicioGeneral
				.selectItemDepartamentos(facultadSeleccionada.getId());
		departamentoSeleccionado = new Dependencia();
	}

	public void cambiarFacultadINV() {
		selectItemDepartamentosINV = servicioGeneral
				.selectItemDepartamentos(facultadSeleccionadaINV.getId());
		if (selectItemDepartamentosINV.length > 0) {
			departamentoSeleccionadoINV = new Dependencia(
					(String) selectItemDepartamentosINV[0].getValue());
		} else {
			departamentoSeleccionadoINV = new Dependencia();
		}
	}

	public void cambiaUsaSoftwareDocencia() {
		System.out.println("usaSoftwareDocenciaBoolean:"
				+ usaSoftwareDocenciaBoolean);
	}

	public void cambiaUsaSoftwareInvestigacion() {
		System.out.println("usaSoftwareInvestigacionBoolean:"
				+ usaSoftwareInvestigacionBoolean);
	}

	public void guardar() {
		if (validar()) {
			// guardar registro inicial
			diagnosticoSoftwareActual
					.setTipoRegistro(DiagnosticoSoftware.TIPO_REGISTRO_REGISTRO);
			diagnosticoSoftwareActual.setTipoDocumentoPersona(personaActual
					.getId().getTipoDocumento());
			diagnosticoSoftwareActual.setDocumentoPersona(personaActual.getId()
					.getDocumento());
			diagnosticoSoftwareActual.setSede(sedeSeleccionada);
			diagnosticoSoftwareActual.setFacultad(facultadSeleccionada);
			diagnosticoSoftwareActual.setDepartamento(departamentoSeleccionado);
			diagnosticoSoftwareActual.setFecha(new Date());
			servicioGeneral.guardarObjeto(diagnosticoSoftwareActual);

			// guardar software docencia:
			if (usaSoftwareDocenciaBoolean) {
				for (DiagnosticoSoftware dS : listaSoftwareDocencia) {
					dS.setTipoRegistro(DiagnosticoSoftware.TIPO_REGISTRO_DOCENCIA);
					dS.setTipoDocumentoPersona(personaActual.getId()
							.getTipoDocumento());
					dS.setDocumentoPersona(personaActual.getId().getDocumento());
					dS.setIdPadre(diagnosticoSoftwareActual.getId());
					dS.setFecha(new Date());
					servicioGeneral.guardarObjeto(dS);

					for (DiagnosticoSoftware dSE : dS
							.getEmpresasDistribuidoras()) {
						// guardar empresas nuevas:
						Empresa e = dSE.getEmpresa();
						if (e.getId() == null) {
							System.out.println("Guardando empresa: "
									+ e.getNombre());
							servicioGeneral.guardarObjeto(e);
						}
						dSE.setIdPadre(dS.getId());
						dSE.setEmpresa(e);
						servicioGeneral.guardarObjeto(dSE);
					}

					// asignaturas
					for (DiagnosticoSoftware dSE : dS.getAsignaturas()) {
						dSE.setIdPadre(dS.getId());
						servicioGeneral.guardarObjeto(dSE);
					}

				}

			}

			// guardar software investigación:
			if (usaSoftwareInvestigacionBoolean) {
				for (DiagnosticoSoftware dS : listaSoftwareInvestigacion) {
					dS.setTipoRegistro(DiagnosticoSoftware.TIPO_REGISTRO_INVESTIGACION);
					dS.setTipoDocumentoPersona(personaActual.getId()
							.getTipoDocumento());
					dS.setDocumentoPersona(personaActual.getId().getDocumento());
					dS.setIdPadre(diagnosticoSoftwareActual.getId());
					dS.setFecha(new Date());
					servicioGeneral.guardarObjeto(dS);

					for (DiagnosticoSoftware dSE : dS
							.getEmpresasDistribuidoras()) {
						// guardar empresas nuevas:
						Empresa e = dSE.getEmpresa();
						if (e.getId() == null) {
							System.out.println("Guardando empresa: "
									+ e.getNombre());
							servicioGeneral.guardarObjeto(e);
						}
						dSE.setIdPadre(dS.getId());
						dSE.setEmpresa(e);
						servicioGeneral.guardarObjeto(dSE);
					}

					// areas
					for (DiagnosticoSoftware dSE : dS.getAreasConocimiento()) {
						dSE.setIdPadre(dS.getId());
						servicioGeneral.guardarObjeto(dSE);
					}
				}
			}
			mensajeInfo("Encuesta guardada con el número "
					+ diagnosticoSoftwareActual.getId());
			guardado = true;
		}
	}

	public Boolean validar() {
		Boolean validar = true;
		diagnosticoSoftwareActual.setCargoPersona(diagnosticoSoftwareActual
				.getCargoPersona().trim());
		if (diagnosticoSoftwareActual.getCargoPersona() == "") {
			validar = false;
			mensajeError("formEDS:Cargo", "El campo Cargo es obligatorio.");
		}

		if (dSagregar != null || nuevoSoftInv != null) {
			mensajeError("Tiene un software inconcluso.  Debe agregarlo o cancelarlo.");
			validar = false;
		}

		if (usaSoftwareDocenciaBoolean && listaSoftwareDocencia.size() < 1) {
			mensajeError("No existen registros de software para Docencia, debe crear al menos uno, o seleccionar que NO lo usa.");
			validar = false;
		}

		if (usaSoftwareInvestigacionBoolean
				&& listaSoftwareInvestigacion.size() < 1) {
			mensajeError("No existen registros de software para Investigación, debe crear al menos uno, o seleccionar que NO lo usa.");
			validar = false;
		}

		return validar;
	}

	public void buscarAsignaturas() {
		asignaturaString = asignaturaString.trim().toUpperCase();

		if (asignaturaString.length() < 4) {
			String error = "El código o nombre a buscar debe tener mínimo cuatro caracteres.";
			mensajeError("formEDS:udCodigoAsignatura", error);
			return;
		}

		String hql = "from VAsignaturasSIA WHERE (UPPER(nombreAsignatura) LIKE '%"
				+ asignaturaString
				+ "%' OR codAsignatura LIKE '%"
				+ asignaturaString
				+ "%') AND codigoSede = '"
				+ sedeSeleccionada.getId()
				+ "'  ORDER BY codigoSede, codAsignatura, grupo";
		System.out.println("hql:" + hql);
		asignaturasEncontradas = new ArrayList<VAsignaturasSIA>();
		asignaturasEncontradas = servicioGeneral.obtenerObjetos(
				VAsignaturasSIA.class, hql);
		System.out.println("asignaturasEncontradas.size:"
				+ asignaturasEncontradas.size());
		List<VAsignaturasSIA> asignaturasEncontradas2 = new ArrayList<VAsignaturasSIA>();

		String nombreEquipoAnterior = "";
		int cantidad = 1;
		int inscritos = 0;
		VAsignaturasSIA asignaturaAnterior = new VAsignaturasSIA();
		int sedeAsignaturaAnterior = 0;
		String codAsignaturaAnterior = "";

		for (VAsignaturasSIA asignaturaAgregar : asignaturasEncontradas) {
			int sedeAsignaturaActual = Integer.parseInt(asignaturaAgregar.getSede().getId().toString());
			System.out.println("inscritos antes: " + inscritos);
			inscritos += asignaturaAgregar.getInscritos();
			System.out.println("inscritos despues: " + inscritos);

			String codAsignaturaActual = asignaturaAgregar.getCodAsignatura();
			System.out.println("nombreEquipoAnterior: " + nombreEquipoAnterior);
			if ((sedeAsignaturaActual != (sedeAsignaturaAnterior))
					|| (!codAsignaturaActual.equals(codAsignaturaAnterior))) {
				System.out.println("diferentes: cantidad: " + cantidad);
				cantidad = 1;
				inscritos = asignaturaAgregar.getInscritos();
				asignaturaAgregar.setGrupo(String.valueOf(cantidad));
				asignaturasEncontradas2.add(asignaturaAgregar);
				asignaturaAnterior = asignaturaAgregar;
			} else {
				cantidad++;
				System.out.println("iguales: cantidad: " + cantidad);
				asignaturaAnterior.setGrupo(String.valueOf(cantidad));
				asignaturaAnterior.setInscritos(inscritos);
			}
			codAsignaturaAnterior = codAsignaturaActual;
			sedeAsignaturaAnterior = sedeAsignaturaActual;
		}

		/*
		 * int sedeAsignaturaAnterior = 0; int numeroAsignaturas = 0; int
		 * inscritos = 0; String codAsignaturaAnterior = ""; for
		 * (VAsignaturasSIA vASia : asignaturasEncontradas) {
		 * numeroAsignaturas++; inscritos+=vASia.getInscritos(); int
		 * sedeAsignaturaActual = vASia.getCodigoSede().intValue(); String
		 * codAsignaturaActual = vASia.getCodAsignatura();
		 * System.out.print("\n vASia.getCodigoSede(): "+vASia.getCodigoSede());
		 * System.out.print("\n numeroAsignaturas: "+numeroAsignaturas);
		 * System.out.print("\n inscritos: "+inscritos);
		 * System.out.print("\n codAsignaturaAnterior: "+codAsignaturaAnterior);
		 * System.out.print("\n codAsignaturaActual: "+codAsignaturaActual);
		 * System
		 * .out.print("\n sedeAsignaturaAnterior: "+sedeAsignaturaAnterior);
		 * System.out.print("\n sedeAsignaturaActual: "+sedeAsignaturaActual);
		 * if ((sedeAsignaturaActual != (sedeAsignaturaAnterior)) ||
		 * (!codAsignaturaActual.equals(codAsignaturaAnterior))) {
		 * System.out.print
		 * ("\n se agrega codAsignaturaActual:"+codAsignaturaActual);
		 * System.out.
		 * print("\n se agrega sedeAsignaturaActual:"+sedeAsignaturaActual);
		 * vASia.setInscritos(inscritos);
		 * vASia.setGrupo(String.valueOf(numeroAsignaturas));
		 * asignaturasEncontradas2.add(vASia); inscritos=0; }
		 * codAsignaturaAnterior = codAsignaturaActual; sedeAsignaturaAnterior =
		 * sedeAsignaturaActual; }
		 */

		List<DiagnosticoSoftware> asignaturasEncontradas3 = new ArrayList<DiagnosticoSoftware>();

		for (VAsignaturasSIA asignatura : asignaturasEncontradas2) {
			DiagnosticoSoftware dSE = new DiagnosticoSoftware();
			dSE.setTipoRegistro(DiagnosticoSoftware.TIPO_REGISTRO_DETALLE_DOCENCIA);
			dSE.setSede(asignatura.getSede());
			Dependencia facultad = new Dependencia(String.valueOf(asignatura
					.getFacultad()));
			dSE.setFacultad(facultad);
			Dependencia departamento = new Dependencia(
					String.valueOf(asignatura.getUab()));
			dSE.setDepartamento(departamento);
			dSE.setAsignatura(asignatura);
			dSE.setFecha(new Date());
			dSE.setProfesores(1);
			dSE.setEstudiantesPregrado(0);
			dSE.setEstudiantesPosgrado(0);
			if (asignatura.getNivel().equals(VAsignaturasSIA.NIVEL_PREGRADO)) {
				dSE.setEstudiantesPregrado(asignatura.getInscritos());
			} else {
				dSE.setEstudiantesPosgrado(asignatura.getInscritos());
			}
			asignaturasEncontradas3.add(dSE);
		}

		System.out.println("asignaturasEncontradas2.size:"
				+ asignaturasEncontradas2.size());
		asignaturasEncontradas = asignaturasEncontradas2;

		if (asignaturasEncontradas2.size() < 1) {
			mensajeError("formEDS:udCodigoAsignatura",
					"No se encontró ninguna asignatura.");
			asignaturasEncontradas = null;
			asignaturasEncontradas2 = null;
			return;
		}
	}

	/**
	 * @return the diagnosticoSoftwareActual
	 */
	public DiagnosticoSoftware getDiagnosticoSoftwareActual() {
		return diagnosticoSoftwareActual;
	}

	/**
	 * @param diagnosticoSoftwareActual
	 *            the diagnosticoSoftwareActual to set
	 */
	public void setDiagnosticoSoftwareActual(
			DiagnosticoSoftware diagnosticoSoftwareActual) {
		this.diagnosticoSoftwareActual = diagnosticoSoftwareActual;
	}

	/**
	 * @return the sedeSeleccionada
	 */
	public Sede getSedeSeleccionada() {
		return sedeSeleccionada;
	}

	/**
	 * @param sedeSeleccionada
	 *            the sedeSeleccionada to set
	 */
	public void setSedeSeleccionada(Sede sedeSeleccionada) {
		this.sedeSeleccionada = sedeSeleccionada;
	}

	/**
	 * @return the selectItemSedes
	 */
	public SelectItem[] getSelectItemSedes() {
		return selectItemSedes;
	}

	/**
	 * @return the facultadSeleccionada
	 */
	public Dependencia getFacultadSeleccionada() {
		return facultadSeleccionada;
	}

	/**
	 * @param facultadSeleccionada
	 *            the facultadSeleccionada to set
	 */
	public void setFacultadSeleccionada(Dependencia facultadSeleccionada) {
		this.facultadSeleccionada = facultadSeleccionada;
	}

	/**
	 * @return the selectItemFacultades
	 */
	public SelectItem[] getSelectItemFacultades() {
		return selectItemFacultades;
	}

	/**
	 * @return the departamentoSeleccionado
	 */
	public Dependencia getDepartamentoSeleccionado() {
		return departamentoSeleccionado;
	}

	/**
	 * @param departamentoSeleccionado
	 *            the departamentoSeleccionado to set
	 */
	public void setDepartamentoSeleccionado(Dependencia departamentoSeleccionado) {
		this.departamentoSeleccionado = departamentoSeleccionado;
	}

	/**
	 * @return the selectItemDepartamentos
	 */
	public SelectItem[] getSelectItemDepartamentos() {
		return selectItemDepartamentos;
	}

	/**
	 * @return the usaSoftwareDocenciaItem
	 */
	public SelectItem[] getUsaSoftwareDocenciaItem() {
		return usaSoftwareDocenciaItem;
	}

	/**
	 * @return the usaSoftwareDocenciaBoolean
	 */
	public Boolean getUsaSoftwareDocenciaBoolean() {
		return usaSoftwareDocenciaBoolean;
	}

	/**
	 * @param usaSoftwareDocenciaBoolean
	 *            the usaSoftwareDocenciaBoolean to set
	 */
	public void setUsaSoftwareDocenciaBoolean(Boolean usaSoftwareDocenciaBoolean) {
		this.usaSoftwareDocenciaBoolean = usaSoftwareDocenciaBoolean;
	}

	/**
	 * @return the usaSoftwareInvestigacionBoolean
	 */
	public Boolean getUsaSoftwareInvestigacionBoolean() {
		return usaSoftwareInvestigacionBoolean;
	}

	/**
	 * @return the usaSoftwareInvestigacionItem
	 */
	public SelectItem[] getUsaSoftwareInvestigacionItem() {
		return usaSoftwareInvestigacionItem;
	}

	/**
	 * @param usaSoftwareInvestigacionBoolean
	 *            the usaSoftwareInvestigacionBoolean to set
	 */
	public void setUsaSoftwareInvestigacionBoolean(
			Boolean usaSoftwareInvestigacionBoolean) {
		this.usaSoftwareInvestigacionBoolean = usaSoftwareInvestigacionBoolean;
	}

	/**
	 * @return the listaSoftwareDocencia
	 */
	public List<DiagnosticoSoftware> getListaSoftwareDocencia() {
		return listaSoftwareDocencia;
	}

	/**
	 * @return the dSagregar
	 */
	public DiagnosticoSoftware getdSagregar() {
		return dSagregar;
	}

	/**
	 * @param dSagregar
	 *            the dSagregar to set
	 */
	public void setdSagregar(DiagnosticoSoftware dSagregar) {
		this.dSagregar = dSagregar;
	}

	/**
	 * @return the nombreEmpresa
	 */
	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	/**
	 * @param nombreEmpresa
	 *            the nombreEmpresa to set
	 */
	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	/**
	 * @return the asignaturaString
	 */
	public String getAsignaturaString() {
		return asignaturaString;
	}

	/**
	 * @param asignaturaString
	 *            the asignaturaString to set
	 */
	public void setAsignaturaString(String asignaturaString) {
		this.asignaturaString = asignaturaString;
	}

	/**
	 * @return the asignaturasEncontradas
	 */
	public List<VAsignaturasSIA> getAsignaturasEncontradas() {
		return asignaturasEncontradas;
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
	 * @return the listaSoftwareInvestigacion
	 */
	public List<DiagnosticoSoftware> getListaSoftwareInvestigacion() {
		return listaSoftwareInvestigacion;
	}

	/**
	 * @return the nuevoSoftInv
	 */
	public DiagnosticoSoftware getNuevoSoftInv() {
		return nuevoSoftInv;
	}

	/**
	 * @param nuevoSoftInv
	 *            the nuevoSoftInv to set
	 */
	public void setNuevoSoftInv(DiagnosticoSoftware nuevoSoftInv) {
		this.nuevoSoftInv = nuevoSoftInv;
	}

	/**
	 * @return the nombreEmpresaInv
	 */
	public String getNombreEmpresaInv() {
		return nombreEmpresaInv;
	}

	/**
	 * @param nombreEmpresaInv
	 *            the nombreEmpresaInv to set
	 */
	public void setNombreEmpresaInv(String nombreEmpresaInv) {
		this.nombreEmpresaInv = nombreEmpresaInv;
	}

	/**
	 * @param areaTematicaSeleccionada
	 *            the areaTematicaSeleccionada to set
	 */
	public void setAreaTematicaSeleccionada(
			ClasificacionConocimiento areaTematicaSeleccionada) {
		this.areaTematicaSeleccionada = areaTematicaSeleccionada;
	}

	/**
	 * @return the sedeSeleccionadaINV
	 */
	public Sede getSedeSeleccionadaINV() {
		return sedeSeleccionadaINV;
	}

	/**
	 * @param sedeSeleccionadaINV
	 *            the sedeSeleccionadaINV to set
	 */
	public void setSedeSeleccionadaINV(Sede sedeSeleccionadaINV) {
		this.sedeSeleccionadaINV = sedeSeleccionadaINV;
	}

	/**
	 * @return the selectItemSedesINV
	 */
	public SelectItem[] getSelectItemSedesINV() {
		return selectItemSedesINV;
	}

	/**
	 * @return the selectItemFacultadesINV
	 */
	public SelectItem[] getSelectItemFacultadesINV() {
		return selectItemFacultadesINV;
	}

	/**
	 * @param selectItemFacultadesINV
	 *            the selectItemFacultadesINV to set
	 */
	public void setSelectItemFacultadesINV(SelectItem[] selectItemFacultadesINV) {
		this.selectItemFacultadesINV = selectItemFacultadesINV;
	}

	/**
	 * @return the facultadSeleccionadaINV
	 */
	public Dependencia getFacultadSeleccionadaINV() {
		return facultadSeleccionadaINV;
	}

	/**
	 * @param facultadSeleccionadaINV
	 *            the facultadSeleccionadaINV to set
	 */
	public void setFacultadSeleccionadaINV(Dependencia facultadSeleccionadaINV) {
		this.facultadSeleccionadaINV = facultadSeleccionadaINV;
	}

	/**
	 * @return the selectItemDepartamentosINV
	 */
	public SelectItem[] getSelectItemDepartamentosINV() {
		return selectItemDepartamentosINV;
	}

	/**
	 * @param selectItemDepartamentosINV
	 *            the selectItemDepartamentosINV to set
	 */
	public void setSelectItemDepartamentosINV(
			SelectItem[] selectItemDepartamentosINV) {
		this.selectItemDepartamentosINV = selectItemDepartamentosINV;
	}

	/**
	 * @return the departamentoSeleccionadoINV
	 */
	public Dependencia getDepartamentoSeleccionadoINV() {
		return departamentoSeleccionadoINV;
	}

	/**
	 * @param departamentoSeleccionadoINV
	 *            the departamentoSeleccionadoINV to set
	 */
	public void setDepartamentoSeleccionadoINV(
			Dependencia departamentoSeleccionadoINV) {
		this.departamentoSeleccionadoINV = departamentoSeleccionadoINV;
	}

	/**
	 * @param selectItemSedesINV
	 *            the selectItemSedesINV to set
	 */
	public void setSelectItemSedesINV(SelectItem[] selectItemSedesINV) {
		this.selectItemSedesINV = selectItemSedesINV;
	}

	/**
	 * @return the guardado
	 */
	public Boolean getGuardado() {
		return guardado;
	}

	/**
	 * @return the softwareDocenciaSeleccionado
	 */
	public DiagnosticoSoftware getSoftwareDocenciaSeleccionado() {
		return softwareDocenciaSeleccionado;
	}

	/**
	 * @param softwareDocenciaSeleccionado
	 *            the softwareDocenciaSeleccionado to set
	 */
	public void setSoftwareDocenciaSeleccionado(
			DiagnosticoSoftware softwareDocenciaSeleccionado) {
		this.softwareDocenciaSeleccionado = softwareDocenciaSeleccionado;
	}

	/**
	 * @return the asignaturaSeleccionada
	 */
	public DiagnosticoSoftware getAsignaturaSeleccionada() {
		return asignaturaSeleccionada;
	}

	/**
	 * @param asignaturaSeleccionada
	 *            the asignaturaSeleccionada to set
	 */
	public void setAsignaturaSeleccionada(
			DiagnosticoSoftware asignaturaSeleccionada) {
		this.asignaturaSeleccionada = asignaturaSeleccionada;
	}

	/**
	 * @return the softwareInvestigacionSeleccionado
	 */
	public DiagnosticoSoftware getSoftwareInvestigacionSeleccionado() {
		return softwareInvestigacionSeleccionado;
	}

	/**
	 * @param softwareInvestigacionSeleccionado
	 *            the softwareInvestigacionSeleccionado to set
	 */
	public void setSoftwareInvestigacionSeleccionado(
			DiagnosticoSoftware softwareInvestigacionSeleccionado) {
		this.softwareInvestigacionSeleccionado = softwareInvestigacionSeleccionado;
	}

	/**
	 * @return the clasificacionConocimientoSeleccionado
	 */
	public DiagnosticoSoftware getClasificacionConocimientoSeleccionado() {
		return clasificacionConocimientoSeleccionado;
	}

	/**
	 * @param clasificacionConocimientoSeleccionado
	 *            the clasificacionConocimientoSeleccionado to set
	 */
	public void setClasificacionConocimientoSeleccionado(
			DiagnosticoSoftware clasificacionConocimientoSeleccionado) {
		this.clasificacionConocimientoSeleccionado = clasificacionConocimientoSeleccionado;
	}

	/**
	 * @return the selectItemEjecucionINV
	 */
	public SelectItem[] getSelectItemEjecucionINV() {
		return selectItemEjecucionINV;
	}

	/**
	 * @return the selectItemEjecucionDOC
	 */
	public SelectItem[] getSelectItemEjecucionDOC() {
		return selectItemEjecucionDOC;
	}

}
