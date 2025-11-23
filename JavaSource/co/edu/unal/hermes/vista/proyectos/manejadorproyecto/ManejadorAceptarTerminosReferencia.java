package co.edu.unal.hermes.vista.proyectos.manejadorproyecto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.component.datatable.DataTable;

import co.edu.unal.hermes.modelo.ArchivoConvocatoriaPadre;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.JornadaDocente;
import co.edu.unal.hermes.modelo.Modalidad;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.RestriccionConvocatoria;
import co.edu.unal.hermes.modelo.TipoModalidad;
import co.edu.unal.hermes.modelo.VAsignaturasSIA;

public class ManejadorAceptarTerminosReferencia extends ManejadorProyecto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ConvocatoriaPadre convocatoriaPadreActual;
	private Convocatoria convocatoriaActual;
	private DataTable tablaDocumentosProceso;
	private List<ArchivoConvocatoriaPadre> listaArchivosConvocatoriaPadreProceso;
	private ArchivoConvocatoriaPadre archivoProcesoSeleccionado;
	private Long idConvocatoriaPadre;
	private Long idConvocatoriaModalidad;
	private String nombrePersona = "";
	private Long codigoProyecto = 0L;
	private Proyecto proyectoAsociar;
	private boolean mostrarProyecto = false;
	private boolean mostrarInfoMov4 = false;
	private boolean mostrarInfoMov2 = false;
	private boolean noBioroyecto = false;
	private String nombreConvocatoria = "";
	private String nombreModalidad = "";
	private String opcionMovilidadModalidad2 = "";
	private String opcionMovilidadModalidad4 = "";
	private String idEntidadFinanciadora = "";
	private List<SelectItem> proyectosInvestigador;
	private int opcion;
	private String proyectoSeleccionado;
	private String proyectoUsado;
	boolean esPermisoMarco;
	boolean esPermisoMarcoAsignatura;
	List<FuenteFinanciacion> listaEntidadesParticipantesProyReg;
	boolean opcionalProyecto;
	boolean esProyectoObligatorio;
	List<SelectItem> listaEntidadesParticipantes = new ArrayList<SelectItem>();
	String usarProyecto;
	
	//Variables para asignaturas en permiso marco
	private String sedeUniversidad;
	private SelectItem[] listaSedesItem;
	private String dependenciaUniversidad;
    private SelectItem[] listaDependenciasItem;
    private String deptoAsignatura;
	private SelectItem[] listaDepartamentosItem;
	private String asignatura;
	private SelectItem[] listaAsignaturasDepartamento;
	List<VAsignaturasSIA> listaAsignaturas;
	private String programaAcademico;
	private List<SelectItem> programas;
	private String nombreNuevaAsignatura;

	public String getNombreModalidad() {
		return nombreModalidad;
	}

	public void setNombreModalidad(String nombreModalidad) {
		this.nombreModalidad = nombreModalidad;
	}

	public ManejadorAceptarTerminosReferencia() {
		super();
		listaAsignaturas = new ArrayList<VAsignaturasSIA>();
		usarProyecto = "si";
		proyectoUsado = "";
		esProyectoObligatorio = true;
		esPermisoMarco = false;
		esPermisoMarcoAsignatura = false;
		opcion = 1;
		Long idConvocatoria = (Long) sesion.getAttribute("idConvocatoriaActual");
		Convocatoria c = null;
		List<Convocatoria> listConvocatorias = servicioGeneral.obtenerObjetos(
				Convocatoria.class, "from Convocatoria e where e.id = " + idConvocatoria);

		if (listConvocatorias.size() > 0 && listConvocatorias != null) {
			c = (Convocatoria) listConvocatorias.get(0);
			convocatoriaActual = (Convocatoria) listConvocatorias.get(0);
			this.convocatoriaPadreActual = servicioModalidad
					.obtenerConvocatoriaPadre(c.getPadre().getId());
		}
		if (c != null && c.getTipo().getId().equals("PM")) {
			opcion = 2;
			esPermisoMarco = true;
		}else if (c != null && c.getTipo().getId().equals("PMA")) {
			opcion = 5;
			esProyectoObligatorio = false;
			usarProyecto = "";
			cargarSedes();
			programas = cargarPlanEstudios(programas);
		}
		
		if (convocatoriaActual.getEsAsignaturaObligatoria() != null
				&& convocatoriaActual.getEsAsignaturaObligatoria().equals("S")) {
			esPermisoMarcoAsignatura = true;
		}

		if (c != null && c.getPadre().getId().equals(243L)) {
			opcion = 4;
		}

		if (convocatoriaActual.getEsProyectoObligagorio() != null
				&& convocatoriaActual.getEsProyectoObligagorio().equals("N")) {
			esProyectoObligatorio = false;
		}

		if (c != null && c.getTipo().getId().equals("CJI")) {
			opcion = 3;
		}

        if (c != null && c.getTipo().getId().equals("RPL")) {
            opcion = 6;
        }

		cargarProyectos();

		tablaDocumentosProceso = new DataTable();
		listaArchivosConvocatoriaPadreProceso = servicioGeneral
				.obtenerObjetos(
						ArchivoConvocatoriaPadre.class,
						"from ArchivoConvocatoriaPadre a where (a.terminos = 'Y') and a.convocatoriaPadre = '"
								+ this.convocatoriaPadreActual.getId() + "' and a.tipoArchivo not in ('B') ");

		personaActual = (Persona) sesion.getAttribute("persona");
		nombrePersona = personaActual.getNombreCompleto();
		if (convocatoriaActual.getRestriccion() != null) {
			if (convocatoriaActual.getRestriccion().getId().equals("MOV2")) {
				mostrarInfoMov2 = true;
				mostrarInfoMov4 = false;
			} else {
				if (convocatoriaActual.getRestriccion().getId().equals("MOV4")) {
					mostrarInfoMov2 = false;
					mostrarInfoMov4 = true;
				}
			}
		}
		cargarEntidadesParticipantes();
	}
	
	private void cargarSedes() {
		List<Dependencia> listaSedes = (List<Dependencia>) servicioGeneral.obtenerSedes();
		listaSedesItem = new SelectItem[listaSedes.size() + 1];
		listaSedesItem[0] = new SelectItem("", "");

		for (int i = 1; i < listaSedes.size() + 1; i++) {
			Dependencia sede = (Dependencia) listaSedes.get(i - 1);
			listaSedesItem[i] = new SelectItem(sede.getId(), sede.getNombre()
					.trim().toUpperCase());
		}
	}
	
	public void cargarDependencias() {
		
		if(sedeUniversidad==null || "".equals(sedeUniversidad.trim())){
			listaDependenciasItem = null;
			listaDepartamentosItem = null;
			listaAsignaturasDepartamento = null;
			deptoAsignatura = null;
			asignatura = "";
			setDependenciaUniversidad(null);
			FacesContext
			.getCurrentInstance()
			.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Debe seleccionar una sede para que sean consultadas las asignaturas.",
							""));
			return;
		}
		
		try{
			List<Dependencia> listaDependencias = servicioGeneral
					.obtenerObjetos(Dependencia.class, "from Dependencia d where d.sede.id = "
							+ sedeUniversidad
							+ " AND d.esFacultad = 'Y' AND d.estado = 'A' ORDER BY d.id");
	
			if (listaDependencias != null && listaDependencias.size() > 0) {
				listaDependenciasItem = new SelectItem[listaDependencias.size()];
				for (int i = 0; i < listaDependencias.size(); i++) {
					Dependencia dep = (Dependencia) listaDependencias.get(i);
					listaDependenciasItem[i] = new SelectItem(dep.getId(),
							dep.getNombre());
				}
				setDependenciaUniversidad(listaDependencias.get(0).getId());
			}else{
				dependenciaUniversidad = "0";
			}
			asignatura = "0";
			cargarDepartamentos();
			
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public void cargarDepartamentos() {
	try{
		List<Dependencia> listaDepartamentos = servicioGeneral
				.obtenerObjetos(Dependencia.class,"from Dependencia d where d.facultad.id = '"
						+ dependenciaUniversidad
						+ "' AND d.esDepartamento = 'Y' ORDER BY d.id");

		if (listaDepartamentos != null && listaDepartamentos.size() > 0) {
			listaDepartamentosItem = new SelectItem[listaDepartamentos.size()];
			for (int i = 0; i < listaDepartamentos.size(); i++) {
				Dependencia dep = (Dependencia) listaDepartamentos.get(i);
				listaDepartamentosItem[i] = new SelectItem(dep.getId(),
						dep.getNombre());
			}
			deptoAsignatura=listaDepartamentos.get(0).getId();
		}else{
			deptoAsignatura="0";
		}
		cargarAsignaturas();
	} catch (Exception e) {
		System.out.println(e.toString());
	}
	}
	
	public void cargarAsignaturas() {
		try{
			 Calendar calendar = Calendar.getInstance();
		     int year = calendar.get(Calendar.YEAR);
		     int month = calendar.get(Calendar.MONTH)+1;
		     String semestre = "1S";
		     if(month>6) {
		    	 semestre = "2S";
		     }
		    
			listaAsignaturas = servicioGeneral
					.obtenerObjetos(VAsignaturasSIA.class,"from VAsignaturasSIA a where (a.sede.id = '"
							+ sedeUniversidad
							+ "' AND a.facultad.id = "+dependenciaUniversidad+" AND a.uab.id = '"+deptoAsignatura+"' and a.annio = '"+year+"' and a.semestre = '"+semestre+"') "
									+ "or a.id = '0' ORDER BY a.id desc");
			
			if (listaAsignaturas != null && listaAsignaturas.size() > 0) {
				listaAsignaturasDepartamento = new SelectItem[listaAsignaturas.size()];
				for (int i = 0; i < listaAsignaturas.size(); i++) {
					VAsignaturasSIA asi = (VAsignaturasSIA) listaAsignaturas.get(i);
					listaAsignaturasDepartamento[i] = new SelectItem(asi.getId(),
							asi.getAnnio()+"-"+asi.getSemestre()+"-"+asi.getCodAsignatura()+"-"+asi.getGrupo()+": "+asi.getNombreAsignatura());
				}
				asignatura = listaAsignaturas.get(0).getId().toString();
			}else{
				asignatura = "0";
			}
		} catch (Exception e) {
			asignatura = "0";
			System.out.println(e.toString());
		}
	}

	public void cargarProyectos() {
		proyectosInvestigador = new ArrayList<SelectItem>();
		personaActual = (Persona) sesion.getAttribute("persona");
		SelectItem item2 = new SelectItem("", "Seleccionar proyecto");
		proyectosInvestigador.add(item2);
		if (personaActual != null) {
			String consulta = "";
			consulta = "select e.id, e.nombre from Proyecto e, InvestigadorProyecto ip where"
					+ " ip.proyecto.id =  e.id and ip.investigador.id.documento = '"
					+ personaActual.getId().getDocumento()
					+ "' and e.estadoProyecto.id != 'B' "
					+ "and e.estadoProyecto.id != 'I' and e.estadoProyecto.id != 'R' and e.modalidad.id not in ("+MODALIDADES_PERMISO_CONTRATO+") order by e.id asc";
			if(esPermisoMarco){
				consulta = "select e.id, e.nombre from Proyecto e, InvestigadorProyecto ip where"
						+ " ip.proyecto.id =  e.id and ip.investigador.id.documento = '"
						+ personaActual.getId().getDocumento()
						+ "' and e.estadoProyecto.id in ('AP','A','OCAD') and e.modalidad.id not in ("+MODALIDADES_PERMISO_CONTRATO+") and e.id not in "
						+ "(select p.codigoDib from Proyecto p where p.modalidad.id in ('1344') and p.codigoDib is not null and p.estadoProyecto.id = 'A') order by e.id asc";
			}
			List<Object[]> listProyecto = servicioGeneral
					.obtenerObjetos(consulta);
			if (listProyecto.size() > 0) {
				for (int i = 0; i < listProyecto.size(); i++) {
					SelectItem item = new SelectItem(
							listProyecto.get(i)[0].toString(),
							listProyecto.get(i)[0].toString() + " - "
									+ listProyecto.get(i)[1].toString());
					proyectosInvestigador.add(item);
				}
			}
		}
	}

	public void buscarProyecto() {
		List<Proyecto> listProyecto = null;
		if (proyectoSeleccionado != null && !proyectoSeleccionado.equals("")) {
			listProyecto = servicioGeneral.obtenerObjetos(Proyecto.class,
					"from Proyecto e where e.id = " + proyectoSeleccionado);
		}
		if (listProyecto != null && listProyecto.size() > 0) {
			proyectoAsociar = (Proyecto) listProyecto.get(0);
			mostrarProyecto = true;
			noBioroyecto = false;
			
			if (convocatoriaActual.getTipo() != null
					&& convocatoriaActual.getTipo().getId() != null
					&& (convocatoriaActual.getTipo().getId().equals("PM") || convocatoriaActual.getTipo().getId().equals("PMA"))) {
				
				
				
				if(!"Si".equals(proyectoAsociar.getTieneBiodiversidad())) {
					mostrarProyecto = false;
					noBioroyecto = true;
				}
				
				
				
			}
				
				sesion.setAttribute("proyectoAsociar", proyectoAsociar);

			try {
				Modalidad mod = proyectoAsociar.getModalidad();

				if (mod instanceof Convocatoria) {
					Convocatoria con = (Convocatoria) mod;
					nombreModalidad = con.getTitulo();

					if (con != null && con.getPadre() != null) {

						nombreConvocatoria = con.getPadre().getTitulo();
					}
				}

				if (mod instanceof JornadaDocente) {
					JornadaDocente con = (JornadaDocente) mod;
					nombreModalidad = con.getDescripcion();
					nombreConvocatoria = con.getDescripcion();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			FacesContext.getCurrentInstance()
					.addMessage(
							"msgs",
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Proyecto no encontrado",
									"Proyecto no encontrado"));
			mostrarProyecto = false;
		}

	}

	public String asociarMovilidad() {

		if (opcionMovilidadModalidad2 != null
				&& opcionMovilidadModalidad2.equals("1")) {
			sesion.setAttribute("idSubmodalidadConv", "MOV_PONEN");
			return "irMovilidadPonenciaOral";
		} else {
			if (opcionMovilidadModalidad2 != null
					&& opcionMovilidadModalidad2.equals("2")) {
				return "irMovilidadResidencia";
			} else {
				if (opcionMovilidadModalidad2 != null && opcionMovilidadModalidad2.equals("3")) {
					sesion.setAttribute("idSubmodalidadConv", "MOV_MP_CON");
					return "irMovilidadPonenciaOral";
				}else{
					if (opcionMovilidadModalidad2 != null && opcionMovilidadModalidad2.equals("4")) {
						sesion.setAttribute("idSubmodalidadConv", "MOV_ESTAN");
						return "irMovilidadPonenciaOral";
						
					}else{
						FacesContext.getCurrentInstance().addMessage(
								"msgs",
								new FacesMessage(FacesMessage.SEVERITY_ERROR,
										"Por favor seleccione el tipo de solicitud",
										"Por favor seleccione el tipo de solicitud"));
						return "";
					}					
				}				
			}
		}

	}

	public String asociarMovilidadTipoCuatro() {

		if (opcionMovilidadModalidad4 != null
				&& opcionMovilidadModalidad4.equals("1")) {
			return "irMovilidadPosgradoInv";
		} else {
			if (opcionMovilidadModalidad4 != null
					&& opcionMovilidadModalidad4.equals("2")) {
				return "irMovilidadEstArt";
			} else {
				FacesContext.getCurrentInstance().addMessage(
						"msgs",
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Por favor seleccione el tipo de solicitud",
								"Por favor seleccione el tipo de solicitud"));
				return "";
			}
		}

	}

	public void consultarDocumento(
			ArchivoConvocatoriaPadre archivoConvocatoriaPadre) {
		String ext = obtenerExtensionArchivo(archivoConvocatoriaPadre
				.getNombre());
		descargarArchivoGenerico("HER_ARCHIVO_CONVOCATORIA_PADRE",
				archivoConvocatoriaPadre.getId().toString(),
				archivoConvocatoriaPadre.getId() + ext);
	}

	public void consultarDocumentoProceso() {
		ArchivoConvocatoriaPadre archivo = archivoProcesoSeleccionado;
		consultarDocumento(archivo);
	}

	@Override
	protected void cargarValoresIniciales() {
		
	}

	@Override
	public String siguiente() {

		sesion.removeAttribute("proyectoMovilidad");

		if ((convocatoriaActual.getRestriccion() != null && (convocatoriaActual
				.getRestriccion().getId().equals("CONV_INI") || convocatoriaActual
				.getRestriccion().getId().equals("CONV_POSG_3")))
				|| (convocatoriaActual.getTieneProyectoAsociado() != null && convocatoriaActual
						.getTieneProyectoAsociado())) {
			// Convocatorias con proyecto opcional
			if (convocatoriaActual != null
					&& convocatoriaActual.getPadre().getId() == 231) { // Eventos
				opcionalProyecto = true;
			}
			if (convocatoriaActual != null
					&& convocatoriaActual.getPadre().getId() == 233) { // Libros
				opcionalProyecto = true;
			}
			if (convocatoriaActual != null
					&& convocatoriaActual.getPadre().getId() == 234) { // Escuela
				// Internacional
				opcionalProyecto = true;
			}
			//

			return "asociarProyectoInv";
		} else if (convocatoriaActual.getTipo().getId().equals(Convocatoria.TIPO_MODALIDAD_MOVILIDAD)) {

			sesion.setAttribute("convocatoriaMovilidadResticcion",convocatoriaActual.getRestriccion().getId());
			sesion.removeAttribute("ManejadorCrearEditarMovilidadPosgradoInvestigacion");
			sesion.removeAttribute("ManejadorCrearEditarMovilidadVisitante");
			sesion.removeAttribute("ManejadorCrearEditarMovilidadEvento");

			if (convocatoriaActual.getPadre().getDependencia().getId().equals("1")
					|| convocatoriaActual.getPadre().getDependencia().getId().equals("2")
					|| convocatoriaActual.getPadre().getDependencia().getId().equals("3")
					|| convocatoriaActual.getPadre().getDependencia().getId().equals("4")
					|| convocatoriaActual.getPadre().getDependencia().getId().equals("5")
					|| convocatoriaActual.getPadre().getDependencia().getId().equals("6")
					|| convocatoriaActual.getPadre().getDependencia().getId().equals("7")
					|| convocatoriaActual.getPadre().getDependencia().getId().equals("8")) {

			} else {
				if (convocatoriaActual.getRestriccion().getId().equals("MOV1")) {
					return convocatoriaActual.getRestriccion().getNombre();
				} else {
					if (convocatoriaActual.getRestriccion().getId().equals("MOV2")) {
						return convocatoriaActual.getRestriccion().getNombre();
					}
				}

			}
			if (convocatoriaActual.getRestriccion().getId().equals("MOV2") || convocatoriaActual.getRestriccion().getId().equals("MOV4")) {
				return "asociarTipoMovilidad";
			} else if (convocatoriaActual.getRestriccion().getId().equals("MOV3"))
				return "irModalidad3Movilidad";
			else
				return convocatoriaActual.getRestriccion().getNombre();
		} else if ((convocatoriaActual.getEsParaGrupos() != null && convocatoriaActual.getEsParaGrupos().booleanValue())
				|| (convocatoriaActual.getEsParaSemilleros() != null && convocatoriaActual.getEsParaSemilleros().booleanValue())) {
			return "validarModalidad";
		} else if (convocatoriaActual.getTipo().getId()
				.equals(TIPO_MODALIDAD_PERMISO_MARCO) || convocatoriaActual.getTipo().getId()
				.equals(TIPO_MODALIDAD_PERMISO_MARCO_ASIGNATURA) ) {
			return "asociarProyectoInv";
		} else if ((convocatoriaActual.getRestriccion() != null && convocatoriaActual.getRestriccion().getId().equals("CE1"))
				|| (convocatoriaActual.getRestriccion() != null && convocatoriaActual.getRestriccion().getId().equals("CE2"))) {
			return "irFichaMinima";
		} else if (convocatoriaActual.getTipo().getId().equals("CL")) {
			if(convocatoriaActual.isUsarFormularioProyectoEditorial()) {
				sesion.removeAttribute("ManejadorEditorialProyectosRegistro");
				sesion.removeAttribute("Pro_Editorial_ID");
				sesion.removeAttribute("Pro_Editorial_Editar");
				return "nuevoProyectoEditorial";
			}else {
				return "irConvocatoriaLibros";
			}
		} else if (convocatoriaActual.getTipo().getId().equals("CLN")) {
			return "irConvocatoriaLibros";
		}else if (convocatoriaActual.getTipo().getId().equals("CPU")) {
			return "irFichaMinimaPurdue";
		} else if (convocatoriaActual.getTipo().getId().equals("CMP")
				|| convocatoriaActual.getTipo().getId().equals("CEQ")) {
			return "irFichaMinimaOtraConv";
		} else if (convocatoriaActual.getTipo().getId().equals("CEI")) {
			return "irConvocatoriaEscuelaInternacional";
		} else if (convocatoriaActual.getTipo().getId().equals("CBP")) {
			return "irConvocatoriaBancoProyectos";
		} else if (convocatoriaActual.getRestriccion()!=null && convocatoriaActual.getRestriccion().getId() != null
				&& RestriccionConvocatoria.EXT_SOL_2018.equals(convocatoriaActual.getRestriccion().getId())) {
			return "asociarProblemaInnovacionSocial";
		} else if (convocatoriaActual.getTipo().getId().equals("FMH")
				|| convocatoriaActual.getTipo().getId().equals("ESI")
				|| convocatoriaActual.getTipo().getId().equals("CTP")
				|| convocatoriaActual.getTipo().getId().equals("ES7")
				|| convocatoriaActual.getTipo().getId().equals("CSF")
				|| convocatoriaActual.getTipo().getId().equals("CTV")
				|| convocatoriaActual.getTipo().getId().equals(TipoModalidad.REGISTRO_PROYECTOS_LABORATORIOS)) {
			sesion.setAttribute("proyecto", null);
	        sesion.setAttribute("proyectoFichaMinina", null);	        
			return "fichaMinimaHome";
		} else if (convocatoriaActual.getTipo().getId().equals("SIS")) {
			sesion.removeAttribute("ManejadorSolicitudISBN");
			return "irSolicitudISBN";
		} else
			return "irFichaMinima";
	}

	public String atras() {
		return salir();
	}

	public String asociarProyectoNoHermes() {
		if (proyectoUsado != null && !(proyectoUsado.trim().length() > 0)) {
			FacesContext.getCurrentInstance()
					.addMessage(
							"msgs",
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Proyecto no encontrado",
									"Proyecto no encontrado"));
			return "";
		}
		proyectoActual.setProyectoUsadoNoHermes(proyectoUsado);
		proyectoActual.setEntidadFinancieraNoHermes(idEntidadFinanciadora);
		return asociarProyecto();
	}
	
	public String asociarAsignatura(){
		boolean valida = true;
		if (asignatura==null || (asignatura!=null && "".equals(asignatura.trim()))){
			FacesContext
			.getCurrentInstance()
			.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Debe seleccionar la asignatura, recuerde seleccionar, sede, facultad y dependencia a la que pertenece la asignatura para que sean listadas.",
							""));
			valida = false;
			return "";
		}
		
		if (asignatura==null || 
				((asignatura!=null && "0".equals(asignatura.trim()) && 
				(nombreNuevaAsignatura ==null || "".equals(nombreNuevaAsignatura.trim())))) ){
			FacesContext
			.getCurrentInstance()
			.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Debe indicar el nombre de la asignatura, por favor asegúrese de que no se encuentra en el listado.",
							""));
			valida = false;
			return "";
		}
		
		if (programaAcademico==null || (programaAcademico!=null && ("".equals(programaAcademico.trim()) || "0".equals(programaAcademico.trim())))){
			FacesContext
			.getCurrentInstance()
			.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Debe seleccionar el programa académico en el cual se orienta la asignatura.",
							""));
			valida = false;
			return "";
		}
		
		if(valida){
			Proyecto proyectoNuevo = (Proyecto) sesion.getAttribute("proyecto");
			if(proyectoNuevo!=null){
				proyectoNuevo.setProgramaAcademico(programaAcademico);
				proyectoNuevo.setFase(0);
				EstadoProyecto estado = new EstadoProyecto();
				estado.setId(EstadoProyecto.INGRESANDO);
				proyectoNuevo.setEstadoProyecto(estado);
				VAsignaturasSIA materia = buscarMateria(asignatura);
				proyectoNuevo.setAsignaturaSIA(materia);
				proyectoNuevo.setAsignatura(nombreNuevaAsignatura);
				
				if(materia !=null && !"0".equals(asignatura)){
					proyectoNuevo.setNombre("Registro asignatura Permiso Marco de recolección: '"+ materia.getNombreAsignatura()+"'");
				}else{
					proyectoNuevo.setNombre("Registro asignatura Permiso Marco de recolección: '"+nombreNuevaAsignatura+"'");
				}
				sesion.setAttribute("proyecto", proyectoNuevo);
			}
			return asociarProyecto();
		}
		return "";
	}
	
	private VAsignaturasSIA buscarMateria(String id) {
		if (!id.equals("")) {
			VAsignaturasSIA materia = new VAsignaturasSIA();
			int i = 0;
			while (i < listaAsignaturas.size()) {
				materia = (VAsignaturasSIA) listaAsignaturas.get(i);
				if (id.equals(materia.getId().toString())) {
					return materia;
				}
				i = i + 1;
			}
		}
		return null;
	}

	public String asociarProyecto() {

		if (convocatoriaActual.getRestriccion() != null
				&& convocatoriaActual.getRestriccion().getId()
						.equals("CONV_POSG_3")) {
			return "validarModalidad";
		}

		// Convocatorias con proyectos asociados: Eventos, Movilidades, Libros,
		// Escuela internacional, Artículos
		if (convocatoriaActual != null
				&& convocatoriaActual.getPadre().getId() == 231) {
			return "irFichaMinima";
		}

		if (convocatoriaActual != null
				&& convocatoriaActual.getPadre().getId() == 233) {
			return "irConvocatoriaLibros";
		}

		if (convocatoriaActual != null
				&& convocatoriaActual.getPadre().getId() == 236) {
			return "irConvocatoriaLibros";
		}

		if (convocatoriaActual.getRestriccion() != null
				&& (convocatoriaActual.getRestriccion().getId()
						.equals("CONV_INNO")
						|| convocatoriaActual.getRestriccion().getId()
								.equals("CONV_INNO_MOD_2") || convocatoriaActual
						.getRestriccion().getId().equals("CONV_INNO_MOD_2_3"))) {
			return "fichaMinimaHome";
		}

		// Fin Convocatorias con proyectos asociados: Eventos, Movilidades,
		// Libros, Escuela internacional, Artículos

		if (convocatoriaActual.getTipo() != null
				&& convocatoriaActual.getTipo().getId() != null
				&& (convocatoriaActual.getTipo().getId().equals("PM") || convocatoriaActual.getTipo().getId().equals("PMA"))) {
			
			sesion.removeAttribute("manejadorInformacionMarco");
			return "irInformacionEspecificaMarco";
		}

		if (convocatoriaActual.getTipo().getId().equals("CEI")) {
			return "irConvocatoriaEscuelaInternacional";
		}

		if (convocatoriaActual.getTipo().getId()
				.equals(Convocatoria.TIPO_MODALIDAD_MOVILIDAD)) {
			eliminarManejadoresMovilidades();
			sesion.setAttribute("proyectoMovilidad", proyectoAsociar);
			sesion.setAttribute("convocatoriaMovilidadResticcion",
					convocatoriaActual.getRestriccion().getId());
			sesion.removeAttribute("ManejadorCrearEditarMovilidadPosgradoInvestigacion");
			sesion.removeAttribute("ManejadorCrearEditarMovilidadVisitante");

			if (convocatoriaActual.getRestriccion().getId().equals("MOV2")
					|| convocatoriaActual.getRestriccion().getId()
							.equals("MOV4")) {
				return "asociarTipoMovilidad";
			} else if (convocatoriaActual.getRestriccion().getId()
					.equals("MOV3"))
				return "irModalidad3Movilidad";
			else
				return convocatoriaActual.getRestriccion().getNombre();
		} else {
			return "irFichaMinIni";
		}

	}

	public String noAsociarProyecto() {

		sesion.removeAttribute("proyectoAsociar");
		if (convocatoriaActual.getRestriccion() != null
				&& convocatoriaActual.getRestriccion().getId()
						.equals("CONV_POSG_3")) {
			return "validarModalidad";
		}
		if (convocatoriaActual.getTipo() != null
				&& convocatoriaActual.getTipo().getId() != null
				&& convocatoriaActual.getTipo().getId().equals("PM")) {
			return "fichaMinimaHome";
		} else {
			return "irFichaMinIni";
		}
	}

	public String salir() {
		sesion.removeAttribute("proyecto");
		borrarManejadoresInsercionProyecto();
		return "misProyectos";
	}

	@Override
	public String salirGuardar() {
		
		return null;
	}

	public ConvocatoriaPadre getConvocatoriaPadreActual() {
		return convocatoriaPadreActual;
	}

	public void setConvocatoriaPadreActual(
			ConvocatoriaPadre convocatoriaPadreActual) {
		this.convocatoriaPadreActual = convocatoriaPadreActual;
	}

	public DataTable getTablaDocumentosProceso() {
		return tablaDocumentosProceso;
	}

	public void setTablaDocumentosProceso(DataTable tablaDocumentosProceso) {
		this.tablaDocumentosProceso = tablaDocumentosProceso;
	}

	public List<ArchivoConvocatoriaPadre> getListaArchivosConvocatoriaPadreProceso() {
		return listaArchivosConvocatoriaPadreProceso;
	}

	public void setListaArchivosConvocatoriaPadreProceso(
			List<ArchivoConvocatoriaPadre> listaArchivosConvocatoriaPadreProceso) {
		this.listaArchivosConvocatoriaPadreProceso = listaArchivosConvocatoriaPadreProceso;
	}

	public ArchivoConvocatoriaPadre getArchivoProcesoSeleccionado() {
		return archivoProcesoSeleccionado;
	}

	public void setArchivoProcesoSeleccionado(
			ArchivoConvocatoriaPadre archivoProcesoSeleccionado) {
		this.archivoProcesoSeleccionado = archivoProcesoSeleccionado;
	}

	public Long getIdConvocatoriaPadre() {
		return idConvocatoriaPadre;
	}

	public void setIdConvocatoriaPadre(Long idConvocatoriaPadre) {
		this.idConvocatoriaPadre = idConvocatoriaPadre;
	}

	public Long getIdConvocatoriaModalidad() {
		return idConvocatoriaModalidad;
	}

	public void setIdConvocatoriaModalidad(Long idConvocatoriaModalidad) {
		this.idConvocatoriaModalidad = idConvocatoriaModalidad;
	}

	public Convocatoria getConvocatoriaActual() {
		return convocatoriaActual;
	}

	public void setConvocatoriaActual(Convocatoria convocatoriaActual) {
		this.convocatoriaActual = convocatoriaActual;
	}

	public String getNombrePersona() {
		return nombrePersona;
	}

	public void setNombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
	}

	public Long getCodigoProyecto() {
		return codigoProyecto;
	}

	public void setCodigoProyecto(Long codigoProyecto) {
		this.codigoProyecto = codigoProyecto;
	}

	public Proyecto getProyectoAsociar() {
		return proyectoAsociar;
	}

	public void setProyectoAsociar(Proyecto proyectoAsociar) {
		this.proyectoAsociar = proyectoAsociar;
	}

	public boolean isMostrarProyecto() {
		return mostrarProyecto;
	}

	public void setMostrarProyecto(boolean mostrarProyecto) {
		this.mostrarProyecto = mostrarProyecto;
	}

	public String getNombreConvocatoria() {
		return nombreConvocatoria;
	}

	public void setNombreConvocatoria(String nombreConvocatoria) {
		this.nombreConvocatoria = nombreConvocatoria;
	}

	public String getOpcionMovilidadModalidad2() {
		return opcionMovilidadModalidad2;
	}

	public void setOpcionMovilidadModalidad2(String opcionMovilidadModalidad2) {
		this.opcionMovilidadModalidad2 = opcionMovilidadModalidad2;
	}

	public String getOpcionMovilidadModalidad4() {
		return opcionMovilidadModalidad4;
	}

	public void setOpcionMovilidadModalidad4(String opcionMovilidadModalidad4) {
		this.opcionMovilidadModalidad4 = opcionMovilidadModalidad4;
	}

	public boolean isMostrarInfoMov4() {
		return mostrarInfoMov4;
	}

	public void setMostrarInfoMov4(boolean mostrarInfoMov4) {
		this.mostrarInfoMov4 = mostrarInfoMov4;
	}

	public boolean isMostrarInfoMov2() {
		return mostrarInfoMov2;
	}

	public void setMostrarInfoMov2(boolean mostrarInfoMov2) {
		this.mostrarInfoMov2 = mostrarInfoMov2;
	}

	public void setOpcion(int opcion) {
		this.opcion = opcion;
	}

	public int getOpcion() {
		return opcion;
	}

	public void setProyectosInvestigador(List<SelectItem> proyectosInvestigador) {
		this.proyectosInvestigador = proyectosInvestigador;
	}

	public List<SelectItem> getProyectosInvestigador() {
		return proyectosInvestigador;
	}

	public void setProyectoSeleccionado(String proyectoSeleccionado) {
		this.proyectoSeleccionado = proyectoSeleccionado;
	}

	public String getProyectoSeleccionado() {
		return proyectoSeleccionado;
	}

	public boolean isOpcionalProyecto() {
		return opcionalProyecto;
	}

	public void setOpcionalProyecto(boolean opcionalProyecto) {
		this.opcionalProyecto = opcionalProyecto;
	}

	public boolean isEsProyectoObligatorio() {
		return esProyectoObligatorio;
	}

	public void setEsProyectoObligatorio(boolean esProyectoObligatorio) {
		this.esProyectoObligatorio = esProyectoObligatorio;
	}

	public String getUsarProyecto() {
		return usarProyecto;
	}

	public void setUsarProyecto(String usarProyecto) {
		this.usarProyecto = usarProyecto;
	}

	public String getProyectoUsado() {
		return proyectoUsado;
	}

	public void setProyectoUsado(String proyectoUsado) {
		this.proyectoUsado = proyectoUsado;
	}

	public void cargarEntidadesParticipantes() {

		listaEntidadesParticipantes = new ArrayList<SelectItem>();
		FuenteFinanciacion entidadParticipante = new FuenteFinanciacion();

		try {
			String hqlentidadesParticipantes = "from FuenteFinanciacion e where e.internaExterna = 'E' and (e.naturaleza= 'NAT_PUBLIC' or e.naturaleza= 'NAT_PRIVAD') or e.id='280' ";
			listaEntidadesParticipantesProyReg = servicioGeneral
					.obtenerObjetos(FuenteFinanciacion.class,
							hqlentidadesParticipantes);

			if (listaEntidadesParticipantesProyReg.size() > 0) {
				for (int i = 0; i < listaEntidadesParticipantesProyReg.size(); i++) {

					entidadParticipante = (FuenteFinanciacion) listaEntidadesParticipantesProyReg
							.get(i);
					listaEntidadesParticipantes.add(new SelectItem(
							entidadParticipante.getId(), entidadParticipante
									.getDescripcion()));
				}
			}
			idEntidadFinanciadora = "280";
		} catch (Exception e) {
			System.out.println("Error al cargar entidades participantes");
			e.printStackTrace();
		}

	}

	public String getIdEntidadFinanciadora() {
		return idEntidadFinanciadora;
	}

	public void setIdEntidadFinanciadora(String idEntidadFinanciadora) {
		this.idEntidadFinanciadora = idEntidadFinanciadora;
	}

	public List<SelectItem> getListaEntidadesParticipantes() {
		return listaEntidadesParticipantes;
	}

	public boolean isEsPermisoMarco() {
		return esPermisoMarco;
	}

	public void setEsPermisoMarco(boolean esPermisoMarco) {
		this.esPermisoMarco = esPermisoMarco;
	}

	public boolean isEsPermisoMarcoAsignatura() {
		return esPermisoMarcoAsignatura;
	}

	public void setEsPermisoMarcoAsignatura(boolean esPermisoMarcoAsignatura) {
		this.esPermisoMarcoAsignatura = esPermisoMarcoAsignatura;
	}

	public String getSedeUniversidad() {
		return sedeUniversidad;
	}

	public void setSedeUniversidad(String sedeUniversidad) {
		this.sedeUniversidad = sedeUniversidad;
	}

	public SelectItem[] getListaSedesItem() {
		return listaSedesItem;
	}

	public void setListaSedesItem(SelectItem[] listaSedesItem) {
		this.listaSedesItem = listaSedesItem;
	}

	public String getDependenciaUniversidad() {
		return dependenciaUniversidad;
	}

	public void setDependenciaUniversidad(String dependenciaUniversidad) {
		this.dependenciaUniversidad = dependenciaUniversidad;
	}

	public SelectItem[] getListaDependenciasItem() {
		return listaDependenciasItem;
	}

	public void setListaDependenciasItem(SelectItem[] listaDependenciasItem) {
		this.listaDependenciasItem = listaDependenciasItem;
	}

	public String getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(String asignatura) {
		this.asignatura = asignatura;
	}

	public SelectItem[] getListaDepartamentosItem() {
		return listaDepartamentosItem;
	}

	public void setListaDepartamentosItem(SelectItem[] listaDepartamentosItem) {
		this.listaDepartamentosItem = listaDepartamentosItem;
	}

	public SelectItem[] getListaAsignaturasDepartamento() {
		return listaAsignaturasDepartamento;
	}

	public void setListaAsignaturasDepartamento(SelectItem[] listaAsignaturasDepartamento) {
		this.listaAsignaturasDepartamento = listaAsignaturasDepartamento;
	}

	public String getDeptoAsignatura() {
		return deptoAsignatura;
	}

	public void setDeptoAsignatura(String deptoAsignatura) {
		this.deptoAsignatura = deptoAsignatura;
	}

	public String getProgramaAcademico() {
		return programaAcademico;
	}

	public void setProgramaAcademico(String programaAcademico) {
		this.programaAcademico = programaAcademico;
	}

	public List<SelectItem> getProgramas() {
		return programas;
	}

	public void setProgramas(List<SelectItem> programas) {
		this.programas = programas;
	}

	public List<VAsignaturasSIA> getListaAsignaturas() {
		return listaAsignaturas;
	}

	public void setListaAsignaturas(List<VAsignaturasSIA> listaAsignaturas) {
		this.listaAsignaturas = listaAsignaturas;
	}

	public String getNombreNuevaAsignatura() {
		return nombreNuevaAsignatura;
	}

	public void setNombreNuevaAsignatura(String nombreNuevaAsignatura) {
		this.nombreNuevaAsignatura = nombreNuevaAsignatura;
	}

	public boolean isNoBioroyecto() {
		return noBioroyecto;
	}

	public void setNoBioroyecto(boolean noBioroyecto) {
		this.noBioroyecto = noBioroyecto;
	}

}
