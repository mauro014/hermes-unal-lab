package co.edu.unal.hermes.vista.semilleros.registro;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.CategoriaInvestigador;
import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.Departamento;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EstadoCivil;
import co.edu.unal.hermes.modelo.Estudiante;
import co.edu.unal.hermes.modelo.HistoricoCambioIntegrantes;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Institucion;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorExterno;
import co.edu.unal.hermes.modelo.InvestigadorGrupo;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Pais;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PersonaRol;
import co.edu.unal.hermes.modelo.Rol;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.Semillero;
import co.edu.unal.hermes.modelo.SemilleroActividad;
import co.edu.unal.hermes.modelo.SemilleroIntegrante;
import co.edu.unal.hermes.modelo.SemilleroIntegranteTipo;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoFormacion;
import co.edu.unal.hermes.utils.VariablesEstaticas;

public class ManejadorSemilleroIntegrantes extends ManejadorSemilleroRegistro {

	private static final long serialVersionUID = 1L;
	private Semillero semilleroActual;
	private String tipoIntegrante;
	private SelectItem[] tipoVinculacionItem;
	private InvestigadorExterno investigadorExterno;
	private TipoDocumento tipoDocumentoInv;
	private String tipoFormacion;
	private String estadoCivil;
	private String paisSel;
	private String ciudadActual;
	private String areaCienciaInv;
	private String subAreaCienciaInv;
	private boolean investigadorActualExterno;
	private String documento;
	private boolean mostrarAgregarIntegrante;
	private SemilleroIntegrante investigadorActual;
	private boolean addEstLider;
	private SelectItem[] tipoDocumentoItem;
	private ArrayList<SemilleroIntegrante> listaInternos;
	private ArrayList<SemilleroIntegrante> listaExternos;
	private String institucionEvaluador;
	private String departamentoActual;
	private SelectItem[] subAreaCienciaItems;
	private List<Ciudad> listaCiudades;
	private SelectItem[] ciudadItem;
	private List<Departamento> listaDepartamentos;
	private SelectItem[] departamentoItem;
	private SelectItem[] generoItem = { new SelectItem(VariablesEstaticas.GENERO_FEMENINO, "Mujer - Femenino"),
			new SelectItem(VariablesEstaticas.GENERO_MASCULINO, "Hombre - Masculino") };
	private SelectItem[] maxNivelEstudioItem;
	private SelectItem[] estadoCivilItem;
	private SelectItem[] selectItemPaises;
	private ArrayList<SelectItem> listaInstitucionesItem;
	private SelectItem[] areaCienciaItems;
	private List<HistoricoCambioIntegrantes> historicoIntegrantes;
	private SemilleroIntegrante integranteEliminar;
	private String horasDedicacionLider;

	public ManejadorSemilleroIntegrantes() {
		init();
	}

	private void init() {
		setSemilleroActual(servicioGeneral.obtenerObjetos(Semillero.class,
				"from Semillero s where s.id=" + (Integer) sesion.getAttribute("semillero")).get(0));
		sesion.setAttribute("semillero", semilleroActual.getId());
		List<TipoDocumento> listaTipoDocumento = servicioGeneral.obtenerTiposDeDocumento();
		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			TipoDocumento td = listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
		}
		tipoDocumentoInv = listaTipoDocumento.get(0);
		investigadorActual = new SemilleroIntegrante();
		List<EstadoCivil> listaEstadoCivil = servicioGeneral.obtenerTiposDeEstadoCivil();
		estadoCivilItem = new SelectItem[listaEstadoCivil.size()];
		for (int i = 0; i < listaEstadoCivil.size(); i++) {
			EstadoCivil ec = listaEstadoCivil.get(i);
			estadoCivilItem[i] = new SelectItem(ec.getId(), ec.getNombre());
		}
		List<TipoFormacion> listaTipoFormacion = servicioGeneral.obtenerTiposDeFormacion();
		maxNivelEstudioItem = new SelectItem[listaTipoFormacion.size()];
		for (int i = 0; i < listaTipoFormacion.size(); i++) {
			TipoFormacion tf = listaTipoFormacion.get(i);
			maxNivelEstudioItem[i] = new SelectItem(tf.getId(), tf.getNombre());
		}
		cargarListasIntegrantes();
		cargarPaises();
		consultarListaInstituciones();
		List<DominioDetalle> listaAreaCiencia = servicioGeneral.obtenerDominioDetalle("AREA_CIENCIA", false);
		areaCienciaItems = crearListaItems(listaAreaCiencia);
		for (SemilleroIntegrante lider : semilleroActual.getIntegrantes()) {
			if (lider.esLider()) {
				horasDedicacionLider = lider.getHorasDedicacion() == null ? "" : lider.getHorasDedicacion().toString();
			}
		}
	}

	private void consultarListaInstituciones() {
		try {
			List<Institucion> listaInstituciones = servicioGeneral.obtenerObjetosLimitado(Institucion.class,
					"select #id ins.id, #nombre ins.nombre  " + "from Institucion ins " + "order by ins.nombre asc");

			listaInstitucionesItem = new ArrayList<SelectItem>();
			for (int i = 0; i < listaInstituciones.size(); i++) {
				Institucion institucion = (Institucion) listaInstituciones.get(i);
				listaInstitucionesItem.add(new SelectItem(institucion.getId(), institucion.getNombre()));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void cargarPaises() {
		List<Pais> listaPaises = servicioGeneral.obtenerListaObjetosOrdenadosAscG(Pais.class, "nombre");
		setSelectItemPaises(new SelectItem[listaPaises.size()]);
		for (int i = 0; i < listaPaises.size(); i++) {
			Pais p = listaPaises.get(i);
			getSelectItemPaises()[i] = new SelectItem(p.getId(), p.getNombre());
		}
	}

	@Override
	String guardar(boolean parcial) {
		if (esCadenaVacia(horasDedicacionLider)) {
			generarMsg(2, "Por favor, indique la dedicación del Director.");
			return "";
		} 
		if (getSemilleroActual().getFase().equals(1)) {
			getSemilleroActual().setFase(2);
		}
		for (SemilleroIntegrante lider : semilleroActual.getIntegrantes()) {
			if (lider.esLider()) {
				try {
					Integer horas = Integer.parseInt(horasDedicacionLider);
					lider.setHorasDedicacion(horas);
					servicioGeneral.guardarObjeto(lider);
					break;
				} catch (Exception e) {
					generarMsg(2, "El número de horas de dedicación del líder no es válido.");
					return "";
				}
			}
		}
		servicioGeneral.guardarObjeto(getSemilleroActual());
		if (!parcial) {
			String vinculacionesEstudiantes = "'EL','EV','EPO','EPR','EE'";
			int contadorValidacion = 0;
			Iterator<SemilleroIntegrante> it = semilleroActual.getIntegrantes().iterator();
			while (it.hasNext()) {
				SemilleroIntegrante si = it.next();
				if (vinculacionesEstudiantes.contains(si.getTipo().getId()) && !SemilleroIntegrante.ESTADO_RETIRADO_SEM.equals(si.getEstado())) {
					contadorValidacion++;
				}
//				if (si.getTipo().getId().equals("EL")) {
//					boolean rolInvestigador = false;
//					for (Rol r : si.getIntegrante().getRoles()) {
//						if (r.getId().equals("I")) {
//							rolInvestigador = true;
//							break;
//						}
//					}
//					if (!rolInvestigador) {
//						PersonaRol pr = new PersonaRol();
//						pr.setDocumento(si.getIntegrante().getId().getDocumento());
//						pr.setTipoDocumento(si.getIntegrante().getId().getTipoDocumento());
//						pr.setNombre("I");
//						pr.setFechaInicioRol(new Date());
//						Calendar c = Calendar.getInstance();
//						c.add(Calendar.YEAR, 1);
//						try {
//							pr.setFechaFinRol(
//									new SimpleDateFormat("dd/MM/yyyy").parse("01/01/" + c.get(Calendar.YEAR)));
//						} catch (ParseException e) {
//							e.printStackTrace();
//						}
//						servicioGeneral.guardarObjeto(pr);
//					}
//				}
			}
			if (Sede.SEDES_ANDINAS
					.contains(getSemilleroActual().getLider().getDependencia().getSede().getId().toString())
					&& contadorValidacion < 3) {
				generarMsg(2, "Para semilleros de SEDES ANDINAS, debe registrar mínimo 3 estudiantes.");
				return "";
			} else if (Sede.SEDES_PRESENCIA_NACIONAL
					.contains(getSemilleroActual().getLider().getDependencia().getSede().getId().toString())
					&& contadorValidacion < 1) {
				generarMsg(2, "Para semilleros de SEDES DE PRESENCIA NACIONAL, debe registrar mínimo 1 estudiante.");
				return "";

			}
			return irLineasAreas(getSemilleroActual().getId());
		} else {
			generarMsg(1, "Semillero registrado correctamente con el ID " + getSemilleroActual().getId());
			return "";
		}
	}

	public void crearListaVinculacion() {
		List<SemilleroIntegranteTipo> listaVinculaciones = servicioGeneral.obtenerObjetos(SemilleroIntegranteTipo.class,
				"from SemilleroIntegranteTipo d where d.tipo ='" + getTipoIntegrante() + "'");
		tipoVinculacionItem = new SelectItem[listaVinculaciones.size()];
		for (int i = 0; i < listaVinculaciones.size(); i++) {
			SemilleroIntegranteTipo dd = listaVinculaciones.get(i);
			tipoVinculacionItem[i] = new SelectItem(dd.getId(), dd.getNombre());
		}
		if (investigadorActualExterno && getTipoIntegrante().equals("I")) {
			investigadorActualExterno = false;
		}
	}

	public void agregarIntegrante() {
		setInvestigadorExterno(null);
		setTipoDocumentoInv(new TipoDocumento());
		setTipoFormacion("");
		setEstadoCivil("");
		setPaisSel("");
		setCiudadActual("");
		setAreaCienciaInv("");
		setSubAreaCienciaInv("");
		setInvestigadorActualExterno(false);
		setDocumento("");
		setMostrarAgregarIntegrante(true);
	}

	public void adicionarIntegrante() {
		boolean valido = true;
		if (investigadorActualExterno && !documento.equals(investigadorExterno.getId().getDocumento())) {
			investigadorActualExterno = false;
		}

		if (investigadorActualExterno && !documento.equals(investigadorExterno.getId().getDocumento())) {
			investigadorActualExterno = false;
		}
		if (esCadenaVacia(tipoIntegrante)) {
			generarMsg(2, "Debe indicar el tipo de asociación a la universidad.");
			valido = false;
		}
		if (esCadenaVacia(investigadorActual.getTipo().getId())) {
			generarMsg(2, "Debe indicar el tipo de integrante.");
			valido = false;
		}
		if (esCadenaVacia(tipoDocumentoInv.getId())) {
			generarMsg(2, "Debe indicar el tipo de documento del integrante.");
			valido = false;
		} else if (esCadenaVacia(documento)) {
			generarMsg(2, "Debe indicar el documento del integrante.");
			valido = false;
		} else if (!validarDocumentoParticipante(tipoDocumentoInv.getId(), documento)) {
			generarMsg(2, "El número de documento ingresado no es válido para el tipo de documento seleccionado.");
			valido = false;
		}
		if (!tipoIntegrante.equals("DD") && esCadenaVacia(investigadorActual.getActividades())) {
			generarMsg(2, "Debe indicar las actividades del integrante.");
			valido = false;
		}
		if (valido) {
			if (!investigadorActualExterno) {
				IdPersona id = new IdPersona();
				id.setTipoDocumento(tipoDocumentoInv.getId());
				id.setDocumento(documento);
				SemilleroIntegrante existente = buscarInvestigador(id);
				if (existente != null) {
				    if ("R".equals(existente.getEstado())) {
				        // Reactiva el integrante retirado
				        existente.setEstado("A");
				        existente.setActividades(investigadorActual.getActividades());
				        existente.setTipo((SemilleroIntegranteTipo) servicioGeneral
								.obtenerObjeto(new SemilleroIntegranteTipo(), investigadorActual.getTipo().getId()));
				        servicioGeneral.guardarObjeto(existente);
				        finalizarAdicionIntegranteValidado(existente.getIntegrante(), false);
				        generarMsg(1, "El integrante ha sido reactivado correctamente.");
				    } else {
				        generarMsg(2, "El investigador ya se encuentra asociado al semillero.");
				    }
				} else {
					Investigador nuevoInvestigadorAgregar = servicioPersona.obtenerInvestigador(id);
					if (tipoIntegrante.equals("I") || tipoIntegrante.equals("DD")) {
						agregarIntegranteInterno(nuevoInvestigadorAgregar, id);
					} else {
						if (nuevoInvestigadorAgregar instanceof InvestigadorInterno && (nuevoInvestigadorAgregar.getInterno()!=null && nuevoInvestigadorAgregar.getInterno().equals("S"))) {
							generarMsg(2,
									"No se puede agregar al integrante Externo, ya que el documento se encuentra asociado a un Investigador Interno.");
						} else {
							agregarIntegranteExterno(nuevoInvestigadorAgregar, 1);
						}
					}
				}
			} else if (validarExterno()) {
				TipoFormacion tf = new TipoFormacion();
				tf.setId(tipoFormacion);
				investigadorExterno.setTipoFormacion(tf);
				EstadoCivil ec = new EstadoCivil();
				ec.setId(estadoCivil);
				investigadorExterno.setEstadoCivil(ec);
				investigadorExterno.setPaisOrigen(paisSel);
				CategoriaInvestigador cat = new CategoriaInvestigador();
				cat.setId(Long.parseLong(CategoriaInvestigador.IDEXTERNO));
				investigadorExterno.setCategoriaInvestigador(cat);
				Ciudad c = new Ciudad();
				c.setId(ciudadActual);
				investigadorExterno.setCiudadNacimiento(c);
				investigadorExterno.setAreaOcde(areaCienciaInv);
				investigadorExterno.setSubareaOcde(subAreaCienciaInv);
				investigadorExterno.setInterno(Investigador.EXTERNO);
				investigadorExterno.setEvaluador(Investigador.NO_EVALUADOR);
				List<Institucion> instituciones = servicioGeneral.obtenerObjetos(Institucion.class,
						"select i from Institucion i where i.id = '" + institucionEvaluador + "'");
				investigadorExterno.setInstitucion((Institucion) (instituciones.get(0)));
				CategoriaInvestigador categoria = new CategoriaInvestigador();
				categoria.setId(Long.parseLong(CategoriaInvestigador.IDEXTERNO));
				investigadorExterno.setCategoriaInvestigador(categoria);
				investigadorExterno.setEsFuncionario("N");
				
				try {
					servicioPersona.guardarInvestigador(investigadorExterno, false);
				}catch (Exception e) {
					e.printStackTrace();
				}finally {
					Persona per = servicioPersona.obtenerPersona(investigadorExterno.getId());
					if(per==null || per.getId()== null) {
						servicioPersona.insertarNuevaPersona(investigadorExterno);
					}
					
					Investigador i = servicioPersona.obtenerInvestigador(investigadorExterno.getId());
					if (i == null || i.getId()==null) {
						servicioPersona.insertarInvestigador(investigadorExterno);
					}else {
						servicioPersona.guardarInvestigador(investigadorExterno, false);
					}
					
					InvestigadorExterno ie = servicioPersona.buscarInvestigadorExternoId(investigadorExterno.getId().getDocumento(),investigadorExterno.getId().getTipoDocumento());
					if(ie==null || ie.getId()==null || esCadenaVacia(ie.getId().getDocumento())) {
						servicioPersona.insertarExterno(investigadorExterno);
					}
				}
				
				Investigador nuevoInvestigadorAgregar = servicioPersona.obtenerInvestigador(investigadorExterno.getId());
				agregarIntegranteExterno(nuevoInvestigadorAgregar, 2);
			}
			cargarListasIntegrantes();
		}
	}

	public void cargarListasIntegrantes() {
		listaInternos = new ArrayList<SemilleroIntegrante>();
		listaExternos = new ArrayList<SemilleroIntegrante>();
		Iterator<SemilleroIntegrante> it = semilleroActual.getIntegrantes().iterator();
		while (it.hasNext()) {
			SemilleroIntegrante si = it.next();
			
			if (!"A".equals(si.getEstado())) {
	            continue; 
	        }
			
			if (!si.esExterno() && !si.esLider()) {
				if (si.getTipo().getId().equals("EL") || si.getTipo().getId().equals("EPR")
						|| si.getTipo().getId().equals("EPO") || si.getTipo().getId().equals("EV")) {
					Estudiante e = servicioPersona.obtenerEstudiante(si.getIntegrante().getId());
					try {
						si.setPlanEstudios(e.getPlan().getNombre());
						si.setNivelPlanEstudios(e.getPlan().getNombreTipo());
					}catch(Exception e1) {
						System.out.println(si.getIntegrante().getId().getDocumento());
						e1.printStackTrace();
					}
				}
				listaInternos.add(si);
			} else if (!si.esLider()) {
				listaExternos.add(si);
			}
		}
	}
	
	

	public void eliminarIntegranteViejo() {
		setHistoricoIntegrantes(servicioGeneral.obtenerObjetos(HistoricoCambioIntegrantes.class,
				"from HistoricoCambioIntegrantes h where h.semillero.id = '" + semilleroActual.getId()
						+ "' order by h.id asc"));
		for (HistoricoCambioIntegrantes item : historicoIntegrantes) {
			if (item.getIntegrante().getId().getDocumento().equals(integranteEliminar.getIntegrante().getId().getDocumento()) && item.getFechaRetiro() == null) {
				item.setFechaRetiro(new Date());
				servicioGeneral.guardarObjeto(item);
				break;
			}
		}
		getSemilleroActual().getIntegrantes().remove(integranteEliminar);
		servicioGeneral.eliminarObjeto(integranteEliminar);
		cargarListasIntegrantes();
	}

	private List<SelectItem> cargarPosiblesLideres() {
		Iterator<SemilleroIntegrante> i = listaInternos.iterator();
		List<SelectItem> listaPosiblesLideres = new ArrayList<SelectItem>();
		while (i.hasNext()) {
			SemilleroIntegrante investigador = i.next();
			if (investigador.getTipo().getId().equals("D")) {
				String consula = "from Investigador i where i.id.documento = '"
						+ investigador.getIntegrante().getId().getDocumento() + "' and i.id.tipoDocumento = '"
						+ investigador.getIntegrante().getId().getTipoDocumento() + "'";
				List<Investigador> investigadores = servicioGeneral.obtenerObjetos(Investigador.class, consula);
				if (!esListaVacia(investigadores)) {
					Investigador inv = investigadores.get(0);
					if (!esCadenaVacia(inv.getInterno()) && inv.getInterno().equals(Investigador.INTERNO)) {
						listaPosiblesLideres
								.add(new SelectItem(inv.getId().getDocumento(), inv.getNombreCompletoMinusculas()));
					}
				}
			}
		}
		return listaPosiblesLideres;
	}

	public boolean validarExterno() {
		boolean isOK = true;
		if (investigadorExterno.getNombre1().length() == 0) {
			generarMsg(2, "Es necesario ingresar el primer nombre del integrante.");
			isOK = false;
		} else if (!validarTextoSinNumeros(investigadorExterno.getNombre1())) {
			generarMsg(2, "Primer nombre del integrante inválido.");
			isOK = false;
		}
		if (investigadorExterno.getNombre2().length() != 0
				&& !validarTextoSinNumeros(investigadorExterno.getNombre2())) {
			generarMsg(2, "Segundo nombre del integrante inválido.");
			isOK = false;
		}
		if (investigadorExterno.getApellido1().length() == 0) {
			generarMsg(2, "Es necesario ingresar el primer apellido del integrante.");
			isOK = false;
		} else if (!validarTextoSinNumeros(investigadorExterno.getApellido1())) {
			generarMsg(2, "Primer apellido del integrante inválido.");
			isOK = false;
		}
		if (investigadorExterno.getApellido2().length() != 0
				&& !validarTextoSinNumeros(investigadorExterno.getApellido2())) {
			generarMsg(2, "Segundo apellido del integrante inválido.");
			isOK = false;
		}
		if (investigadorExterno.getGenero().length() == 0) {
			generarMsg(2, "Es necesario ingresar el género del integrante.");
			isOK = false;
		}
		if (tipoFormacion.length() == 0) {
			generarMsg(2, "Es necesario ingresar el máximo nivel de estudios del integrante.");
			isOK = false;
		}
		if (estadoCivil.length() == 0) {
			generarMsg(2, "Es necesario ingresar el estado civil del integrante.");
			isOK = false;
		}
		if (investigadorExterno.getFechaNacimiento() == null) {
			generarMsg(2, "Es necesario ingresar la fecha de nacimiento del integrante.");
			isOK = false;
		} else if (investigadorExterno.getFechaNacimiento().after(getToday())) {
			generarMsg(2, "La fecha de nacimiento del integrante no puede ser superior a HOY.");
			isOK = false;
		} else if (calcularEdad(investigadorExterno.getFechaNacimiento()) < 18
				&& investigadorExterno.getId().getTipoDocumento().equals(TipoDocumento.CEDULA)) {
			generarMsg(2, "El tipo de documento no coincide con la fecha de nacimiento del integrante.");
			isOK = false;
		} else if (calcularEdad(investigadorExterno.getFechaNacimiento()) >= 18
				&& calcularEdad(investigadorExterno.getFechaNacimiento()) < 10
				&& investigadorExterno.getId().getTipoDocumento().equals(TipoDocumento.TARJETA_IDENTIDAD)) {
			generarMsg(2, "El tipo de documento no coincide con la fecha de nacimiento del integrante.");
			isOK = false;
		}
		if (paisSel.length() == 0) {
			generarMsg(2, "Es necesario ingresar el país de nacimiento del integrante.");
			isOK = false;
		}
		if (ciudadActual.length() == 0) {
			generarMsg(2, "Es necesario ingresar la ciudad de nacimiento del integrante.");
			isOK = false;
		}
		if (investigadorExterno.getEmail().length() == 0) {
			generarMsg(2, "Es necesario ingresar el e-mail del integrante.");
			isOK = false;
		} else if (!validarEmail(investigadorExterno.getEmail())) {
			generarMsg(2, "E-mail del integrante inválido.");
			isOK = false;
		}
		if (investigadorExterno.getTelefono().length() == 0) {
			generarMsg(2, "Es necesario ingresar el teléfono del integrante.");
			isOK = false;
		} else if (investigadorExterno.getTelefono().length() < 7) {
			generarMsg(2, "Teléfono del integrante inválido.");
			isOK = false;
		}
		if (institucionEvaluador.length() == 0) {
			generarMsg(2, "Es necesario ingresar la institución del integrante.");
			isOK = false;
		}
		if (areaCienciaInv.length() == 0) {
			generarMsg(2, "Es necesario ingresar la área OCDE del integrante.");
			isOK = false;
		}
		if (subAreaCienciaInv.length() == 0) {
			generarMsg(2, "Es necesario ingresar la sub-área OCDE del integrante.");
			isOK = false;
		}
		if ((investigadorExterno.getId().getTipoDocumento().equals(TipoDocumento.CEDULA_EXTRANJERIA)
				|| investigadorExterno.getId().getTipoDocumento().equals(TipoDocumento.DOCUMENTO_IDENTIDAD_EXTRANJERA))
				&& paisSel.equals("CO")) {
			generarMsg(2,
					"Si el integrante nació en Colombia, su documento no puede ser de tipo CÉDULA DE EXTRANJERÍA o DOCUMENTO DE IDENTIDAD EXTRANJERA.");
			isOK = false;
		}
		return isOK;
	}

	private static Integer calcularEdad(Date fechaNac) {
		Calendar fechaNacimiento = Calendar.getInstance();
		Calendar fechaActual = Calendar.getInstance();
		fechaNacimiento.setTime(fechaNac);
		int anio = fechaActual.get(Calendar.YEAR) - fechaNacimiento.get(Calendar.YEAR);
		int mes = fechaActual.get(Calendar.MONTH) - fechaNacimiento.get(Calendar.MONTH);
		int dia = fechaActual.get(Calendar.DATE) - fechaNacimiento.get(Calendar.DATE);
		if (mes < 0 || (mes == 0 && dia < 0)) {
			anio--;
		}
		return anio;
	}

	private boolean agregarIntegranteExterno(Investigador nuevoInvestigadorAgregar, int process) {
		switch (process) {
		case 1:
			if (nuevoInvestigadorAgregar != null) {
				investigadorExterno = new InvestigadorExterno();
				investigadorExterno.setId(
						nuevoInvestigadorAgregar.getId() == null ? new IdPersona() : nuevoInvestigadorAgregar.getId());
				investigadorExterno.setNombre1(
						nuevoInvestigadorAgregar.getNombre1() == null ? "" : nuevoInvestigadorAgregar.getNombre1());
				investigadorExterno.setNombre2(
						nuevoInvestigadorAgregar.getNombre2() == null ? "" : nuevoInvestigadorAgregar.getNombre2());
				investigadorExterno.setApellido1(
						nuevoInvestigadorAgregar.getApellido1() == null ? "" : nuevoInvestigadorAgregar.getApellido1());
				investigadorExterno.setApellido2(
						nuevoInvestigadorAgregar.getApellido2() == null ? "" : nuevoInvestigadorAgregar.getApellido2());
				investigadorExterno.setGenero(
						nuevoInvestigadorAgregar.getGenero() == null ? "" : nuevoInvestigadorAgregar.getGenero());
				tipoFormacion = (nuevoInvestigadorAgregar.getTipoFormacion() == null ? ""
						: nuevoInvestigadorAgregar.getTipoFormacion().getId());
				estadoCivil = (nuevoInvestigadorAgregar.getEstadoCivil() == null ? ""
						: nuevoInvestigadorAgregar.getEstadoCivil().getId());
				investigadorExterno
						.setFechaNacimiento(nuevoInvestigadorAgregar.getFechaNacimiento() == null ? getToday()
								: nuevoInvestigadorAgregar.getFechaNacimiento());
				paisSel = (nuevoInvestigadorAgregar.getPaisOrigen() == null ? ""
						: nuevoInvestigadorAgregar.getPaisOrigen());
				if (!paisSel.equals("")) {
					revisarPais();
				}
				if (paisSel != null && !paisSel.equals("") && paisSel.equals("CO")) {
					departamentoActual = (nuevoInvestigadorAgregar.getCiudadNacimiento() == null ? ""
							: nuevoInvestigadorAgregar.getCiudadNacimiento().getDepartamento().getId());
					cambiarDepartamento();
				}
				ciudadActual = (nuevoInvestigadorAgregar.getCiudadNacimiento() == null ? ""
						: nuevoInvestigadorAgregar.getCiudadNacimiento().getId());
				investigadorExterno.setEmail(
						nuevoInvestigadorAgregar.getEmail() == null ? "" : nuevoInvestigadorAgregar.getEmail());
				investigadorExterno.setTelefono(
						nuevoInvestigadorAgregar.getTelefono() == null ? "" : nuevoInvestigadorAgregar.getTelefono());
				institucionEvaluador = (nuevoInvestigadorAgregar.getInstitucion() == null ? ""
						: nuevoInvestigadorAgregar.getInstitucion().getId());
				areaCienciaInv = (nuevoInvestigadorAgregar.getAreaOcde() == null ? ""
						: nuevoInvestigadorAgregar.getAreaOcde());
				cambiarAreaInv();
				subAreaCienciaInv = (nuevoInvestigadorAgregar.getSubareaOcde() == null ? ""
						: nuevoInvestigadorAgregar.getSubareaOcde());
				investigadorActualExterno = true;
			} else {
				investigadorExterno = new InvestigadorExterno();
				investigadorExterno.setInstitucion(new Institucion());
				investigadorExterno.setId(new IdPersona());
				CategoriaInvestigador ci = new CategoriaInvestigador();
				if (investigadorActual.getTipo().getId().equals("DE")) {
					ci.setId(new Long(CategoriaInvestigador.IDDOCENTE));
				}
				if (investigadorActual.getTipo().getId().equals("EE")) {
					ci.setId(new Long(CategoriaInvestigador.IDESTUDIANTE));
				}
				if (investigadorActual.getTipo().getId().equals("EG")) {
					ci.setId(new Long(CategoriaInvestigador.IDEXTERNO));
				}
				investigadorExterno.setCategoriaInvestigador(ci);
				investigadorExterno.getId().setTipoDocumento(tipoDocumentoInv.getId());
				investigadorExterno.getId().setDocumento(documento.trim());
				investigadorActualExterno = true;
			}
			return false;
		case 2:
			finalizarAdicionIntegranteValidado(nuevoInvestigadorAgregar, true);
			return true;
		default:
			return false;
		}
	}

	public void cargarHistorico() {
		setHistoricoIntegrantes(servicioGeneral.obtenerObjetos(HistoricoCambioIntegrantes.class,
				"from HistoricoCambioIntegrantes h where h.semillero.id = '" + semilleroActual.getId()
						+ "' order by h.id asc"));
		for (HistoricoCambioIntegrantes item : historicoIntegrantes) {
			TipoDocumento td = servicioGeneral
					.obtenerObjetoXID(TipoDocumento.class, item.getIntegrante().getId().getTipoDocumento()).get(0);
			item.setDocumento(td.getNombre());
			if (item.getTipoInvestigadorSemillero() == null) {
				item.setTipo("N/A");
			} else if (item.getTipoInvestigadorSemillero().getTipo().equals("DD")) {
				item.setTipoVinculacionGrupo("Docente Director");
			} else if (item.getTipoInvestigadorSemillero().getTipo().equals("I")) {
				item.setTipoVinculacionGrupo("Interno");
			} else {
				item.setTipoVinculacionGrupo("Externo");
			}
			item.setTipo(item.getTipoInvestigadorSemillero().getNombre());
		}
	}

	public void finalizarAdicionIntegranteValidado(Investigador nuevoInvestigador, boolean esNuevoInvestigador) {
		if (esNuevoInvestigador) {
			investigadorActual.setIntegrante(nuevoInvestigador);
			investigadorActual.setTipo((SemilleroIntegranteTipo) servicioGeneral
					.obtenerObjeto(new SemilleroIntegranteTipo(), investigadorActual.getTipo().getId()));
			investigadorActual.setSemillero(getSemilleroActual());
			getSemilleroActual().getIntegrantes().add(investigadorActual);
			servicioGeneral.guardarObjeto(investigadorActual);
		}
		hci = new HistoricoCambioIntegrantes();
		hci.setSemillero(getSemilleroActual());
		hci.setFechaIngreso(new Date());
		hci.setIntegrante(nuevoInvestigador);
		hci.setTipoInvestigadorSemillero(investigadorActual.getTipo());
		if (investigadorActual.getTipo().getId().equals("EL") || investigadorActual.getTipo().getId().equals("EPR")
				|| investigadorActual.getTipo().getId().equals("EPO")
				|| investigadorActual.getTipo().getId().equals("EV")) {
			Estudiante e = servicioPersona.obtenerEstudiante(nuevoInvestigador.getId());
			hci.setPlanEstudios(e.getPlan());
			hci.setSemestreActual(e.getSemestreActual());
			hci.setDependencia(e.getDependencia());
			hci.setSede(e.getDependencia().getSede());
		} else if (getTipoIntegrante().equals("I") || getTipoIntegrante().equals("DD")) {
			hci.setTipoVinculacion(investigadorActual.getIntegrante().getTipoVinculacion());
			hci.setTipoDedicacion(investigadorActual.getIntegrante().getTipoDedicacion());
			hci.setTipoFormacion(investigadorActual.getIntegrante().getTipoFormacion());
			hci.setDependencia(investigadorActual.getIntegrante().getDependencia().getFacultad());
			hci.setSede(investigadorActual.getIntegrante().getDependencia().getFacultad().getSede());
		}
		servicioGeneral.guardarObjeto(hci);
		servicioGeneral.guardarObjeto(investigadorActual);
		investigadorActual = new SemilleroIntegrante();
		mostrarAgregarIntegrante = false;
		investigadorActualExterno = false;
		cargarListasIntegrantes();
	}

	public void cambiarAreaInv() {
		setSubAreaCienciaItems(cambiarAreaCiencia(areaCienciaInv));
		setSubAreaCienciaInv("");
	}

	protected SelectItem[] cambiarAreaCiencia(String areaCiencia) {
		String consultaAreasSec = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='SUB_AREA_CIENCIA_FM' and  dd.estado = '"
				+ areaCiencia + "' order by dd.descripcion";
		List<DominioDetalle> listaSubAreaCiencia = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				consultaAreasSec);
		return crearListaItems(listaSubAreaCiencia);
	}

	public void cambiarDepartamento() {
		setListaCiudades(servicioGeneral.obtenerObjetosLimitado(Ciudad.class,
				"select #id e.id, #nombre e.nombre from Ciudad e where e.departamento.id like '" + departamentoActual
						+ "' and e.sigla is not null order by e.nombre asc"));
		setCiudadItem(new SelectItem[getListaCiudades().size()]);
		for (int i = 0; i < getListaCiudades().size(); i++) {
			Ciudad ci = (Ciudad) getListaCiudades().get(i);
			getCiudadItem()[i] = new SelectItem(ci.getId(), ci.getNombre());
			ci = null;
		}
	}

	public void revisarPais() {
		if (paisSel.equals("")) {
			setCiudadItem(null);
		} else if (paisSel != null && !paisSel.equals("") && paisSel.equals("CO")) {
			obtenerListaDepartamentos();
			setCiudadItem(null);
		} else {
			obtenerListaCiudadesDiferentesColombia();
		}
		departamentoActual = "";
		ciudadActual = "";
	}

	private void obtenerListaCiudadesDiferentesColombia() {
		String idPaisBus = "";
		try {
			idPaisBus = paisSel.substring(0, 2);
		} catch (Exception e) {
			idPaisBus = paisSel;
		}
		String hql = "select #id e.id, #nombre e.nombre from Ciudad e where e.id like ('" + idPaisBus + "%')";
		listaCiudades = servicioGeneral.obtenerObjetosLimitado(Ciudad.class, hql);
		ciudadItem = new SelectItem[listaCiudades.size()];
		for (int i = 0; i < listaCiudades.size(); i++) {
			Ciudad ci = (Ciudad) listaCiudades.get(i);
			ciudadItem[i] = new SelectItem(ci.getId(), ci.getNombre());
			ci = null;
		}
	}

	private void obtenerListaDepartamentos() {
		setListaDepartamentos(servicioGeneral.obtenerObjetosLimitado(Departamento.class,
				"select #id e.id, #nombre e.nombre from Departamento e where e.id like 'CO%' order by e.nombre asc"));
		setDepartamentoItem(new SelectItem[getListaDepartamentos().size()]);
		for (int i = 0; i < getListaDepartamentos().size(); i++) {
			Departamento dep = (Departamento) getListaDepartamentos().get(i);
			getDepartamentoItem()[i] = new SelectItem(dep.getId(), dep.getNombre());
			dep = null;
		}
	}

	private boolean agregarIntegranteInterno(Investigador nuevoInvestigadorAgregar, IdPersona id) {
		if (investigadorActual.getTipo().getId().equals("D") || investigadorActual.esLider()) {
			return agregarDocenteOLiderInterno(nuevoInvestigadorAgregar, true);
		} else if (investigadorActual.getTipo().getId().equals("EL")
				|| investigadorActual.getTipo().getId().equals("EPR")
				|| investigadorActual.getTipo().getId().equals("EPO")
				|| investigadorActual.getTipo().getId().equals("EV")) {
			Estudiante e = servicioPersona.obtenerEstudiante(id);
			
			if (esNulo(e)) {
				generarMsg(2, "El estudiante no ha sido encontrado");
				return false;
			}
			
			if(!esNulo(e.getInterno()) && !e.esInterno()) {
				generarMsg(2, "El estudiante ingresado no está activo para el plan de estudios: " + e.getPlan().getNombre());
				return false;
			}
			
			return agregarEstudianteInterno(nuevoInvestigadorAgregar, id);
		} else if (investigadorActual.getTipo().getId().equals("DI")) {
			if(!esNulo(nuevoInvestigadorAgregar) && !esNulo(nuevoInvestigadorAgregar.getTipoVinculacion())) {
				String tVinculacion = nuevoInvestigadorAgregar.getTipoVinculacion().getId();
				if(tVinculacion.equals("20") || tVinculacion.equals("21")) {
					finalizarAdicionIntegranteValidado(nuevoInvestigadorAgregar, true);
					return true;
				}
			}
			// Si no existe no se puede agregar como Docente de Enseñanza educación básica y media.
			mensajeError("No se encuentra información del Docente de Enseñanza educación básica y media o no tiene vinculación de correspondiente.");
		}
		return false;
	}

	private boolean agregarEstudianteInterno(Investigador nuevoInvestigadorAgregar, IdPersona id) {
		
		Estudiante e = servicioPersona.obtenerEstudiante(id);
		String tipoEstudiante = investigadorActual.getTipo().getId();
		
		if (tipoEstudiante.equals("EPR") && e.getPlan().getTipo() != 3L) {
			generarMsg(2, "ERROR: El estudiante no pertenece a un programa de pregrado.");
			return false;
		} else if (tipoEstudiante.equals("EPO") && e.getPlan().getTipo() != 4L
				&& e.getPlan().getTipo() != 5L && e.getPlan().getTipo() != 6L && e.getPlan().getTipo() != 7L) {
			generarMsg(2, "ERROR: El estudiante no pertenece a un programa de posgrado.");
			return false;
		} else if (tipoEstudiante.equals("EV") && e.getPlan().getTipo() != 8L) {
			generarMsg(2, "ERROR: El estudiante no pertenece a un programa de visitantes.");
			return false;
		}
		if (nuevoInvestigadorAgregar != null) {
			finalizarAdicionIntegranteValidado(nuevoInvestigadorAgregar, true);
		} else {
			if (servicioPersona.obtenerPersona(id) == null) {
				servicioPersona.insertarNuevaPersona(e.convertirAPersona());
			}
			Investigador investigadorNuevo = new Investigador();
			investigadorNuevo.setEvaluador("N");
			investigadorNuevo.setEsFuncionario("N");
			investigadorNuevo.setInterno("N");
			investigadorNuevo.setId(id);
			investigadorNuevo.setInterno("N");
			CategoriaInvestigador categoria = new CategoriaInvestigador();
			categoria.setId(3L);
			investigadorNuevo.setCategoriaInvestigador(categoria);
			servicioPersona.insertarInvestigador(investigadorNuevo);
			InvestigadorInterno investigadoriInterno = servicioPersona.obtenerInvestigadorInterno(id);
			if (investigadoriInterno == null) {
				InvestigadorInterno nuevoInvestigadorInterno = new InvestigadorInterno();
				nuevoInvestigadorInterno.setId(id);
				nuevoInvestigadorInterno.setDependencia(e.getDependencia());
				servicioPersona.insertaInterno(nuevoInvestigadorInterno);
				InvestigadorInterno investigadorInsertado = servicioPersona.obtenerInvestigadorInterno(id);
				if (investigadorInsertado == null) {
					generarMsg(2, "Ha habido un error al agregar al estudiante.");
					return false;
				} else {
					investigadorNuevo = investigadorInsertado;
				}
			} else {
				investigadorNuevo = investigadoriInterno;
			}
			finalizarAdicionIntegranteValidado(investigadorNuevo, true);			
		}
		
		if(tipoEstudiante.equals("EL"))
			crearUsuarioEstudianteAsistenteLider("AL", id);
		
		return true;
	}
	
	private void crearUsuarioEstudianteAsistenteLider(String tipo, IdPersona id) {
		try {
			PersonaRol pr = new PersonaRol();
			pr.setDocumento(id.getDocumento());
			pr.setTipoDocumento(id.getTipoDocumento());
			pr.setNombre(tipo);
			pr.setFechaInicioRol(getToday());
			Calendar c = Calendar.getInstance();
			c.add(Calendar.YEAR, 1);
			pr.setFechaFinRol(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/" + c.get(Calendar.YEAR)));
			servicioGeneral.guardarObjeto(pr);
		} catch (Exception ex) {
			System.out.println("Ya tenía el rol Estudiante Líder");
		}
	}

	private boolean agregarDocenteOLiderInterno(Investigador nuevoInvestigadorAgregar, boolean esInvestigadorNuevo) {
		SemilleroIntegrante si = new SemilleroIntegrante();
		if (nuevoInvestigadorAgregar != null && "S".equals(nuevoInvestigadorAgregar.getInterno())) {
			if (tipoIntegrante.equals("DD")) {
				SemilleroIntegranteTipo sitDD = new SemilleroIntegranteTipo();
				sitDD.setId("DD");
				investigadorActual.setTipo(sitDD);
				for (SemilleroIntegrante integrante : getSemilleroActual().getIntegrantes()) {
					if (integrante.getTipo().getId().equals("DD")
							&& !integrante.getIntegrante().equals(nuevoInvestigadorAgregar)) {
						setHistoricoIntegrantes(servicioGeneral.obtenerObjetos(HistoricoCambioIntegrantes.class,
								"from HistoricoCambioIntegrantes h where h.semillero.id = '" + semilleroActual.getId()
										+ "' order by h.id asc"));
						for (HistoricoCambioIntegrantes item : historicoIntegrantes) {
							if (item.getIntegrante().equals(integrante.getIntegrante())
									&& item.getTipoInvestigadorSemillero().getId().equals("DD")
									&& item.getFechaRetiro() == null) {
								item.setFechaRetiro(new Date());
								servicioGeneral.guardarObjeto(item);
								break;
							}
						}
						getSemilleroActual().getIntegrantes().remove(integrante);
						servicioGeneral.guardarObjeto(getSemilleroActual());
						String tempActividades = investigadorActual.getActividades();
						investigadorActual.setActividades(integrante.getActividades());
						if (tempActividades != null) {
							si.setActividades(tempActividades);
						} else {
							si.setActividades("Docente");
						}
						si.setSemillero(getSemilleroActual());
						si.setIntegrante(integrante.getIntegrante());
						si.setId(integrante.getId());
						si.setTipo((SemilleroIntegranteTipo) servicioGeneral
								.obtenerObjeto(new SemilleroIntegranteTipo(), "D"));
						getSemilleroActual().getIntegrantes().add(si);
						servicioGeneral.guardarObjeto(getSemilleroActual());
						servicioGeneral.guardarObjeto(si);
						hci = new HistoricoCambioIntegrantes();
						hci.setFechaIngreso(new Date());
						hci.setSemillero(getSemilleroActual());
						hci.setIntegrante(integrante.getIntegrante());
						hci.setTipoVinculacion(integrante.getIntegrante().getTipoVinculacion());
						hci.setTipoDedicacion(integrante.getIntegrante().getTipoDedicacion());
						hci.setTipoFormacion(integrante.getIntegrante().getTipoFormacion());
						hci.setDependencia(integrante.getIntegrante().getDependencia().getFacultad());
						hci.setSede(integrante.getIntegrante().getDependencia().getFacultad().getSede());
						hci.setTipoInvestigadorSemillero(si.getTipo());
						servicioGeneral.guardarObjeto(hci);
					} else if (integrante.getTipo().getId().equals("D")
							&& !integrante.getIntegrante().equals(investigadorActual.getIntegrante())) {
						setHistoricoIntegrantes(servicioGeneral.obtenerObjetos(HistoricoCambioIntegrantes.class,
								"from HistoricoCambioIntegrantes h where h.semillero.id = '" + semilleroActual.getId()
										+ "' order by h.id asc"));
						for (HistoricoCambioIntegrantes item : historicoIntegrantes) {
							if (item.getIntegrante().equals(investigadorActual.getIntegrante())
									&& item.getTipoInvestigadorSemillero().getId().equals("D")
									&& item.getFechaRetiro() == null) {
								item.setFechaRetiro(new Date());
								servicioGeneral.guardarObjeto(item);
								break;
							}
						}
					}
				}
			} else {
				SemilleroIntegranteTipo sitD = new SemilleroIntegranteTipo();
				sitD.setId("D");
				investigadorActual.setTipo(sitD);
			}
			finalizarAdicionIntegranteValidado(nuevoInvestigadorAgregar, esInvestigadorNuevo);
			servicioGeneral.guardarObjeto(getSemilleroActual());
			cargarPosiblesLideres();
			return true;
		} else {
			generarMsg(2, "El investigador no se encuentra como docente interno activo a la Universidad");
		}
		return false;
	}

	private SemilleroIntegrante buscarInvestigador(IdPersona id) {
		if (id != null) {
			Iterator<SemilleroIntegrante> i = semilleroActual.getIntegrantes().iterator();
			while (i.hasNext()) {
				SemilleroIntegrante investigador = i.next();
				if (investigador != null
						&& id.getDocumento().equals(investigador.getIntegrante().getId().getDocumento())
						&& id.getTipoDocumento().equals(investigador.getIntegrante().getId().getTipoDocumento())) {
					return investigador;
				}
			}
		}
		return null;
	}

	public void validarTipoIntegrante() {
		if (investigadorActual.getTipo().getId().equals("EL")) {
			setAddEstLider(true);
		} else {
			setAddEstLider(false);
		}
		return;
	}

	public String getTipoIntegrante() {
		return tipoIntegrante;
	}

	public void setTipoIntegrante(String tipoIntegrante) {
		this.tipoIntegrante = tipoIntegrante;
	}

	public SelectItem[] getTipoVinculacionItem() {
		return tipoVinculacionItem;
	}

	public void setTipoVinculacionItem(SelectItem[] tipoVinculacionItem) {
		this.tipoVinculacionItem = tipoVinculacionItem;
	}

	public InvestigadorExterno getInvestigadorExterno() {
		return investigadorExterno;
	}

	public void setInvestigadorExterno(InvestigadorExterno investigadorExterno) {
		this.investigadorExterno = investigadorExterno;
	}

	public TipoDocumento getTipoDocumentoInv() {
		return tipoDocumentoInv;
	}

	public void setTipoDocumentoInv(TipoDocumento tipoDocumentoInv) {
		this.tipoDocumentoInv = tipoDocumentoInv;
	}

	public String getTipoFormacion() {
		return tipoFormacion;
	}

	public void setTipoFormacion(String tipoFormacion) {
		this.tipoFormacion = tipoFormacion;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getPaisSel() {
		return paisSel;
	}

	public void setPaisSel(String paisSel) {
		this.paisSel = paisSel;
	}

	public String getCiudadActual() {
		return ciudadActual;
	}

	public void setCiudadActual(String ciudadActual) {
		this.ciudadActual = ciudadActual;
	}

	public String getAreaCienciaInv() {
		return areaCienciaInv;
	}

	public void setAreaCienciaInv(String areaCienciaInv) {
		this.areaCienciaInv = areaCienciaInv;
	}

	public String getSubAreaCienciaInv() {
		return subAreaCienciaInv;
	}

	public void setSubAreaCienciaInv(String subAreaCienciaInv) {
		this.subAreaCienciaInv = subAreaCienciaInv;
	}

	public boolean isInvestigadorActualExterno() {
		return investigadorActualExterno;
	}

	public void setInvestigadorActualExterno(boolean investigadorActualExterno) {
		this.investigadorActualExterno = investigadorActualExterno;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public boolean isMostrarAgregarIntegrante() {
		return mostrarAgregarIntegrante;
	}

	public void setMostrarAgregarIntegrante(boolean mostrarAgregarIntegrante) {
		this.mostrarAgregarIntegrante = mostrarAgregarIntegrante;
	}

	public SemilleroIntegrante getInvestigadorActual() {
		return investigadorActual;
	}

	public void setInvestigadorActual(SemilleroIntegrante investigadorActual) {
		this.investigadorActual = investigadorActual;
	}

	public boolean isAddEstLider() {
		return addEstLider;
	}

	public void setAddEstLider(boolean addEstLider) {
		this.addEstLider = addEstLider;
	}

	public SelectItem[] getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}

	public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
		this.tipoDocumentoItem = tipoDocumentoItem;
	}

	public ArrayList<SemilleroIntegrante> getListaInternos() {
		return listaInternos;
	}

	public void setListaInternos(ArrayList<SemilleroIntegrante> listaIntegrantes) {
		this.listaInternos = listaIntegrantes;
	}

	public String getInstitucionEvaluador() {
		return institucionEvaluador;
	}

	public void setInstitucionEvaluador(String institucionEvaluador) {
		this.institucionEvaluador = institucionEvaluador;
	}

	public String getDepartamentoActual() {
		return departamentoActual;
	}

	public void setDepartamentoActual(String departamentoActual) {
		this.departamentoActual = departamentoActual;
	}

	public SelectItem[] getSubAreaCienciaItems() {
		return subAreaCienciaItems;
	}

	public void setSubAreaCienciaItems(SelectItem[] subAreaCienciaItems) {
		this.subAreaCienciaItems = subAreaCienciaItems;
	}

	public List<Ciudad> getListaCiudades() {
		return listaCiudades;
	}

	public void setListaCiudades(List<Ciudad> listaCiudades) {
		this.listaCiudades = listaCiudades;
	}

	public SelectItem[] getCiudadItem() {
		return ciudadItem;
	}

	public void setCiudadItem(SelectItem[] ciudadItem) {
		this.ciudadItem = ciudadItem;
	}

	public List<Departamento> getListaDepartamentos() {
		return listaDepartamentos;
	}

	public void setListaDepartamentos(List<Departamento> listaDepartamentos) {
		this.listaDepartamentos = listaDepartamentos;
	}

	public SelectItem[] getDepartamentoItem() {
		return departamentoItem;
	}

	public void setDepartamentoItem(SelectItem[] departamentoItem) {
		this.departamentoItem = departamentoItem;
	}

	public SelectItem[] getGeneroItem() {
		return generoItem;
	}

	public void setGeneroItem(SelectItem[] generoItem) {
		this.generoItem = generoItem;
	}

	public SelectItem[] getMaxNivelEstudioItem() {
		return maxNivelEstudioItem;
	}

	public SelectItem[] getEstadoCivilItem() {
		return estadoCivilItem;
	}

	public void setMaxNivelEstudioItem(SelectItem[] maxNivelEstudioItem) {
		this.maxNivelEstudioItem = maxNivelEstudioItem;
	}

	public void setEstadoCivilItem(SelectItem[] estadoCivilItem) {
		this.estadoCivilItem = estadoCivilItem;
	}

	public SelectItem[] getSelectItemPaises() {
		return selectItemPaises;
	}

	public void setSelectItemPaises(SelectItem[] selectItemPaises) {
		this.selectItemPaises = selectItemPaises;
	}

	public ArrayList<SelectItem> getListaInstitucionesItem() {
		return listaInstitucionesItem;
	}

	public void setListaInstitucionesItem(ArrayList<SelectItem> listaInstitucionesItem) {
		this.listaInstitucionesItem = listaInstitucionesItem;
	}

	public SelectItem[] getAreaCienciaItems() {
		return areaCienciaItems;
	}

	public void setAreaCienciaItems(SelectItem[] areaCienciaItems) {
		this.areaCienciaItems = areaCienciaItems;
	}

	public ArrayList<SemilleroIntegrante> getListaExternos() {
		return listaExternos;
	}

	public void setListaExternos(ArrayList<SemilleroIntegrante> listaExternos) {
		this.listaExternos = listaExternos;
	}

	public List<HistoricoCambioIntegrantes> getHistoricoIntegrantes() {
		return historicoIntegrantes;
	}

	public void setHistoricoIntegrantes(List<HistoricoCambioIntegrantes> historicoIntegrantes) {
		this.historicoIntegrantes = historicoIntegrantes;
	}

	public SemilleroIntegrante getIntegranteEliminar() {
		return integranteEliminar;
	}

	public void setIntegranteEliminar(SemilleroIntegrante integranteEliminar) {
		this.integranteEliminar = integranteEliminar;
	}

	public Semillero getSemilleroActual() {
		return semilleroActual;
	}

	public void setSemilleroActual(Semillero semilleroActual) {
		this.semilleroActual = semilleroActual;
	}

	public String getHorasDedicacionLider() {
		return horasDedicacionLider;
	}

	public void setHorasDedicacionLider(String horasDedicacionLider) {
		this.horasDedicacionLider = horasDedicacionLider;
	}
	
	public String prepararEliminar() {
	    return null;
	}
	
	public void eliminarIntegrante() {
	    // Revisa en lista de internos
	    for (SemilleroIntegrante integrante : listaInternos) {
	        if (integrante.equals(integranteEliminar)) {
	            procesarEliminacion(integrante);
	            break;
	        }
	    }

	    // Revisa en lista de externos
	    for (SemilleroIntegrante integrante : listaExternos) {
	        if (integrante.equals(integranteEliminar)) {
	            procesarEliminacion(integrante);
	            break;
	        }
	    }

	    // Actualiza el histórico
	    setHistoricoIntegrantes(servicioGeneral.obtenerObjetos(HistoricoCambioIntegrantes.class,
	            "from HistoricoCambioIntegrantes h where h.semillero.id = '" + semilleroActual.getId()
	                    + "' order by h.id asc"));
	    for (HistoricoCambioIntegrantes item : historicoIntegrantes) {
	        if (item.getIntegrante().getId().getDocumento()
	                .equals(integranteEliminar.getIntegrante().getId().getDocumento()) && item.getFechaRetiro() == null) {
	            item.setFechaRetiro(new Date());
	            servicioGeneral.guardarObjeto(item);
	            break;
	        }
	    }

	    // Refresca las listas en pantalla
	    cargarListasIntegrantes();
	}
	
	private void procesarEliminacion(SemilleroIntegrante integrante) {

	    for (SemilleroActividad actividad : integrante.getPlanTrabajo()) {
	        if (actividadEstaVigente(actividad)) {
	            // Transfiere la actividad al director del semillero
	        	
	        	for (SemilleroIntegrante reponsable : semilleroActual.getIntegrantes()) {
	        	    if (reponsable.esLider()) {
	        	    	actividad.setResponsable(reponsable);
	        	    	reponsable.getPlanTrabajo().add(actividad);
	        	    	servicioGeneral.guardarObjeto(actividad);
	        	    	servicioGeneral.guardarObjeto(reponsable);
	        	    	
	        	    	
	        	        break;
	        	    }
	        	}
	        }
	    }
	    
	    integrante.setEstado("R");
        servicioGeneral.guardarObjeto(integrante);
        sesion.removeAttribute("manejadorSemilleroPlanTrabajo");

	}

	private boolean actividadEstaVigente(SemilleroActividad actividad) {
	    Date fechaFin = calcularFechaFin(actividad.getFechaInicio(), actividad.getDuracion());
	    return fechaFin.after(new Date());
	}

	private Date calcularFechaFin(Date fechaInicio, Integer meses) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(fechaInicio);
	    calendar.add(Calendar.MONTH, meses);
	    return calendar.getTime();
	}


}
