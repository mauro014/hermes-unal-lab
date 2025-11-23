package co.edu.unal.hermes.vista.aval.base;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;

import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.ArchivoAval;
import co.edu.unal.hermes.modelo.ArchivoConvocatoriaExterna;
import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.modelo.AvalComiteEtica;
import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.Convenio;
import co.edu.unal.hermes.modelo.ConvocatoriaExterna;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Departamento;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EntidadArticulo;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.MontoAno;
import co.edu.unal.hermes.modelo.Pais;
import co.edu.unal.hermes.modelo.PalabraClave;
import co.edu.unal.hermes.modelo.Parametro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.TerminosConvocatoriaExterna;
import co.edu.unal.hermes.modelo.TipoArchivo;
import co.edu.unal.hermes.modelo.TipoCargo;
import co.edu.unal.hermes.modelo.TipoConvenio;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoRubro;
import co.edu.unal.hermes.modelo.TipoVinculacion;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.vista.ManejadorBase;

public class BaseManejadorSolicitarAval extends ManejadorBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected boolean esConsulta = false;
	protected boolean esEdicion = false;
	protected boolean esAvalNuevo = false;
	protected boolean noModificar = false;
	protected boolean nuevaConvocatoria;
	protected boolean aceptaTerminos;

	public static final String SESION_MODO_EDICION = "esEdicion";
	public static final String SESION_MODO_CONSULTA = "esConsulta";

	/**
	 * Los identificadores de las sedes que avalan a través de la facultad.
	 */
	protected List<Long> sedesIds;
	protected CorreoPlantilla correoActual = new CorreoPlantilla();
	protected Aval aval;
	protected Investigador investigadorActual;

	protected String anoMonto;
	protected String montoReg;
	protected String region;
	protected String subregion;
	protected List<SelectItem> listaFichasItems = new ArrayList<SelectItem>();

	protected boolean habilitarCampos = false;

	protected List<SelectItem> listaRegiones;
	protected List<SelectItem> listaSubRegiones;
	protected List<SelectItem> listaDepartamentos;
	protected List<SelectItem> avalesInvestigador;
	protected List<SelectItem> gruposInvestigacion;
	protected List<SelectItem> listaEntidadesExternas;
	protected List<SelectItem> conveniosItem;
	protected List<SelectItem> tipoDocumentoItem;
	protected List<SelectItem> convocatoriasExternasItem;
	protected List<ArchivoConvocatoriaExterna> listaArchivosConvocatoriaExterna;
	protected ConvocatoriaExterna convocatoria;
	protected TerminosConvocatoriaExterna terminos;
	protected List<Convenio> listaConvenios;
	protected Convenio convenioIngresado; // para nuevos convenios solicitados
	protected List<SelectItem> dependenciaItem;
	protected List<SelectItem> listaRubrosQuipu;
	protected Date fechaLimiteSede;

	protected Correo correo = new Correo();

	protected final static String RUTA_ADJUNTO = "/pages/aval/";

	protected SelectItem[] paisItem;
	protected SelectItem[] sedesItems;

	protected UploadedFile archivoCargar;
	protected List<ArchivoAval> listaArchivos = new ArrayList<ArchivoAval>();
	protected ArchivoAval archivoAval;

	protected String tipoDocumento;
	protected String documento;

	protected EntidadArticulo entidadSeleccionada;

	protected SelectItem[] ciudadItem;
	protected SelectItem[] categoriaItems;
	protected SelectItem[] fichasItems;

	protected SelectItem[] eventoItem = { new SelectItem("Participación en evento", "Participación en evento"),
			new SelectItem("Realización estancia o pasantía", "Realización estancia o pasantía"),
			new SelectItem("Evento", "Evento"),
			new SelectItem("Fortalecimiento de redes", "Fortalecimiento de redes") };

	protected SelectItem[] listaFases = { new SelectItem("Fase 1: Perfil", "Fase 1: Perfil"),
			new SelectItem("Fase 2: Prefactibilidad", "Fase 2: Prefactibilidad"),
			new SelectItem("Fase 3: Factibilidad", "Fase 3: Factibilidad") };

	protected SelectItem[] listaAno = { new SelectItem("2013", "2013"), new SelectItem("2014", "2014"),
			new SelectItem("2015", "2015"), new SelectItem("2016", "2016"), new SelectItem("2017", "2017"),
			new SelectItem("2018", "2018"), new SelectItem("2019", "2019"), new SelectItem("2020", "2020"),
			new SelectItem("2021", "2021"), new SelectItem("2022", "2022") };

	protected Proyecto fichaSeleccionada = null;
	
	protected List<SelectItem> listaCEPIsItem;
	
	protected SelectItem[] listaArtCientificosItem;
	
	protected SelectItem[] tiposRecursoAvalEtico;
	protected Boolean permitirInterponerRecurso = false;
	protected int diasFaltantesPermitirRecurso = 0;
	protected Boolean esConsultaRecurso = false;
	protected Boolean esConsultaQueja = false;
	
	protected boolean convocatoriaInternacional = false;
	protected SelectItem[] selTipoArchivoAval;
	protected List<TipoArchivo> listaTipoArchivo = new ArrayList<TipoArchivo>();
	
	public PalabraClave palabraClave;
	public List<PalabraClave> listaPalabrasClave;
	
	public List<PalabraClave> getListaPalabrasClave() {
		List<PalabraClave> listaPalabrasClave = new ArrayList<PalabraClave>();
		if (aval != null && aval.getPalabrasClaves().size() > 0) {
			for (Iterator<PalabraClave> iterador = aval
					.getPalabrasClaves().iterator(); iterador.hasNext();) {
				listaPalabrasClave.add(iterador.next());
			}
		}
		return listaPalabrasClave;
	   // return listaPalabrasClave;
	}

	public String getAnoMonto() {
		return anoMonto;
	}

	public void setAnoMonto(String anoMonto) {
		this.anoMonto = anoMonto;
	}
	
	protected ArchivoAval documentoSeleccionado;

	public void verificarRevisionFacultad() {

		/**
		 * Verificación de sedes en donde la facultad realiza revisión de la
		 * solicitud de aval antes que la dirección de investigación.
		 */

		List<Parametro> parametros = this.servicioGeneral.obtenerListaObjetosWhere(Parametro.class,
				"WHERE p.nombre = 'T_AVAL' AND p.descripcion = 'SOLICITAR FACULTAD'");
		sedesIds = new LinkedList<Long>();

		for (Parametro p : parametros) {
			for (String str : p.getValor().trim().split("[,]")) {
				sedesIds.add(Long.parseLong(str.trim()));
			}
		}
	}

	/**
	 * Lista los tipos de aval disponibles en el sistema
	 */
	public void tiposAval() {

		List<DominioDetalle> lista;
			String consulta = "select dd from Dominio d, DominioDetalle dd where "
					+ "d.id = dd.identificador.id and d.tipo like 'ACTIVIDAD_AVAL_NEW' "
					+ " and dd.identificador.tipo in ('"
					+ Aval.TIPO_JORNADA_DOCENTE + "','"
					+ Aval.TIPO_MOVILIDAD + "','"
					+ Aval.TIPO_INVESTIGACION + "','"
					+ Aval.TIPO_INVESTIGACION_CONT +"','"
					+ Aval.TIPO_REGALIAS + "','"
					+ Aval.TIPO_GRUPO_INVESTIGACION + "','"
					+ Aval.TIPO_INVESTIGADOR_INDEPENDIENTE + "','"
					+ Aval.TIPO_PAED_REGALIAS + "','"
					+ Aval.TIPO_CONVOCATORIA_REGALIAS + "','"
					+ Aval.TIPO_REGALIAS_VERIF_REQ + "','"
					+ Aval.TIPO_CENTRO_INVESTIGACION + "','"
					+ Aval.TIPO_ETICO + "','"
					+ Aval.TIPO_ARTICULO_INVESTIGACION
					+ "') order by dd.descripcion";
		if(!esConsulta) {
			 consulta = "select dd from Dominio d, DominioDetalle dd where "
					+ "d.id = dd.identificador.id and d.tipo like 'ACTIVIDAD_AVAL_NEW' "
					+ " and dd.identificador.tipo in ('"
					+ Aval.TIPO_JORNADA_DOCENTE + "','"
					+ Aval.TIPO_MOVILIDAD + "','"
					+ Aval.TIPO_INVESTIGACION + "','"
					+ Aval.TIPO_INVESTIGACION_CONT +"','"
					+ Aval.TIPO_CONVOCATORIA_REGALIAS + "','"
					+ Aval.TIPO_REGALIAS_VERIF_REQ + "','"
					//+ Aval.TIPO_PAED_REGALIAS + "','"
					//+ Aval.TIPO_REGALIAS + "','"
					+ Aval.TIPO_GRUPO_INVESTIGACION + "','"
					+ Aval.TIPO_INVESTIGADOR_INDEPENDIENTE + "','"
					+ Aval.TIPO_CENTRO_INVESTIGACION + "','"
					+ Aval.TIPO_ETICO + "','"
					+ Aval.TIPO_ARTICULO_INVESTIGACION
					+ "') order by dd.descripcion";
			TipoVinculacion tv = servicioPersona
					.obtenerInvestigadorInterno(((Persona) sesion.getAttribute("persona")).getId()).getTipoVinculacion();
			if (tv != null && tv.getId().equals("98")) {
				consulta = "select dd from Dominio d, DominioDetalle dd where "
						+ "d.id = dd.identificador.id and d.tipo like 'ACTIVIDAD_AVAL_NEW' "
						+ " and dd.identificador.tipo in ('" + Aval.TIPO_JORNADA_DOCENTE + "') order by dd.descripcion";
			}
			TipoCargo tc = servicioPersona
					.obtenerInvestigadorInterno(((Persona) sesion.getAttribute("persona")).getId()).getTipoCargo();
			if (tc != null && tc.getId().equals("D-ESPEMP")) {
				consulta = "select dd from Dominio d, DominioDetalle dd where "
						+ "d.id = dd.identificador.id and d.tipo like 'ACTIVIDAD_AVAL_NEW' "
						+ " and dd.identificador.tipo in ('" + Aval.TIPO_MOVILIDAD + "','"
						+ Aval.TIPO_INVESTIGACION + "','" + Aval.TIPO_INVESTIGACION_CONT + "','" + Aval.TIPO_CONVOCATORIA_REGALIAS + "','" + Aval.TIPO_REGALIAS_VERIF_REQ + "','"
						+ Aval.TIPO_GRUPO_INVESTIGACION + "','" + Aval.TIPO_INVESTIGADOR_INDEPENDIENTE + "','"
						+ Aval.TIPO_CENTRO_INVESTIGACION + "') order by dd.descripcion";
			}
		}
		
		lista = servicioGeneral.obtenerObjetos(DominioDetalle.class, consulta);
		

		if (!esListaVacia(lista)) {
			categoriaItems = new SelectItem[lista.size()];
			for (int i = 0; i < lista.size(); i++) {
				DominioDetalle dominio = (DominioDetalle) lista.get(i);
				categoriaItems[i] = new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion());
			}
		}
	}
	
	public void imprimirAval() {
        if (aval != null) {
            servicioAval.imprimirReporteAval(aval.getAviId(), sesion);
        }
    }
	
	public void imprimirProyecto() {
        Long id = aval.getIdProyecto();
        Proyecto proyectoActual = servicioProyecto.obtenerProyecto(id, ProyectoDAOHibernate.INFORMACION_GENERAL);
        if (proyectoActual != null) {
            servicioProyecto.imprimirReporteProyecto(proyectoActual, sesion, false);
        }
    }
	
//	public void descargarArchivo() {
//        if (documentoSeleccionado != null && documentoSeleccionado.getId() != null) {
//            descargarArchivoAvalGenerico(documentoSeleccionado.getId());
//        } else {
//            descargarArchivoAvalGenerico(arCoor.getId());
//        }
//    }

	/**
	 * Lista entidades externas a partir de método del manejador base
	 */
	public void cargarEntidadesConvocantes() {

		listaEntidadesExternas = new ArrayList<SelectItem>();
		List<FuenteFinanciacion> listaEntidadesExt = cargarEntidadesExternas(CONSULTA_ENTIDADES_EXTERNAS_SIN_UNAL);

		if (!esListaVacia(listaEntidadesExt)) {
			for (int i = 0; i < listaEntidadesExt.size(); i++) {
				FuenteFinanciacion entidad = (FuenteFinanciacion) listaEntidadesExt.get(i);
				if(entidad.getVisibleAval()) {
					listaEntidadesExternas.add(new SelectItem(entidad.getId(), entidad.getDescripcion()));
				}
			}
		}
	}

	/**
	 * Consulta las convocatorias externas vigentes de acuerdo a la entidad
	 * seleccionada
	 */
	public void cargarConvocatoriasExternas() {
		convocatoriasExternasItem = new ArrayList<SelectItem>();
		aval.setAviFechaCierreConv(null);
		if (!esListaVacia(listaArchivosConvocatoriaExterna)) {
			listaArchivosConvocatoriaExterna.clear();
		}
		if (aval.getAviEntidad() != null && !"".equals(aval.getAviEntidad())) {
			List<ConvocatoriaExterna> listaConvocatoriasExternas;
			convocatoriasExternasItem.clear();
			String restriccionAdicionalConvocatoria = " and (ce.convocatoriaGrupos = 0 or ce.convocatoriaGrupos is null) ";
			if (aval.isEsGrupoInvestigacion() || aval.isEsInvestigadorIndependiente()) {
				restriccionAdicionalConvocatoria = " and (ce.convocatoriaGrupos = 1 or ce.convocatoriaGrupos is null) ";
			}

			if (esAvalNuevo && !aval.isEsProyectoContrapartida() && !aval.isEsConvocatoriaRegalias() && !aval.isEsRequisitosRegalias()) {
				listaConvocatoriasExternas = servicioGeneral.obtenerObjetosLimitado(ConvocatoriaExterna.class,
						"select #id ce.id, #nombre ce.nombre, #numero ce.numero, #fechaCierre ce.fechaCierre, #fechaMaxRegistro ce.fechaMaxRegistro, #urlExterna ce.urlExterna from "
								+ "ConvocatoriaExterna ce where ce.entidad.id in ('" + aval.getAviEntidad()
								+ "','0') and ce.estado = 'A' and ce.fechaMaxRegistro > SYSDATE - 1 and ce.visible = 1 "
								+ restriccionAdicionalConvocatoria + " and (ce.tipoAvalConvocatoria is null or (ce.tipoAvalConvocatoria in ('"+aval.getTipo()+"','NA'))) order by ce.fechaCierre,ce.nombre asc");
			} else if (esAvalNuevo && aval.isEsConvocatoriaRegalias()) {
				listaConvocatoriasExternas = servicioGeneral.obtenerObjetosLimitado(ConvocatoriaExterna.class,
						"select #id ce.id, #nombre ce.nombre, #numero ce.numero, #fechaCierre ce.fechaCierre, #fechaMaxRegistro ce.fechaMaxRegistro, #urlExterna ce.urlExterna from "
								+ "ConvocatoriaExterna ce where (ce.entidad.id in ('" + aval.getAviEntidad()
								+ "') and ce.estado = 'A' and ce.fechaMaxRegistro > SYSDATE - 1 and ce.visible = 1 "
								+ restriccionAdicionalConvocatoria + ") or ce.id in ('0') and (ce.tipoAvalConvocatoria is null or (ce.tipoAvalConvocatoria in ('"+aval.getTipo()+"','NA'))) order by ce.fechaCierre,ce.nombre asc");
			} else {
				listaConvocatoriasExternas = servicioGeneral.obtenerObjetosLimitado(ConvocatoriaExterna.class,
						"select #id ce.id, #nombre ce.nombre, #numero "
								+ "ce.numero, #fechaCierre ce.fechaCierre, #fechaMaxRegistro ce.fechaMaxRegistro, #urlExterna ce.urlExterna from "
								+ "ConvocatoriaExterna ce where ce.estado in ('A','I') and ce.visible = 1 " + " and ce.entidad.id in ('"
								+ aval.getAviEntidad() + "','0') order by ce.fechaCierre,ce.nombre desc");
			}

			if (!esListaVacia(listaConvocatoriasExternas)) {
				for (int i = 0; i < listaConvocatoriasExternas.size(); i++) {
					ConvocatoriaExterna c = (ConvocatoriaExterna) listaConvocatoriasExternas.get(i);
					convocatoriasExternasItem.add(new SelectItem(c.getId(), "#" + c.getNumero() + " - " + c.getNombre()));
				}
			}
			consultarConvocatoria();
		} else {
			convocatoria = new ConvocatoriaExterna();
			mensajeError(
					"Debe seleccionar una entidad convocante para que sean listadas las convocatorias disponibles");
		}
	}

	/**
	 * Consulta información de convocatoria externa seleccionada
	 */
	public void consultarConvocatoria() {

		convocatoria = new ConvocatoriaExterna();
		listaArchivosConvocatoriaExterna = new ArrayList<ArchivoConvocatoriaExterna>();
		if (aval.getAviConvocatoria() != null && !"".equals(aval.getAviConvocatoria())) {
			nuevaConvocatoria = false;
			if (!"0".equals(aval.getAviConvocatoria())) {
				List<ConvocatoriaExterna> convocaExt = servicioGeneral.obtenerObjetoXID(ConvocatoriaExterna.class,
						aval.getAviConvocatoria());
				if (!esListaVacia(convocaExt)) {
					convocatoria = convocaExt.get(0);
					aval.setAviFechaCierreConv(convocatoria.getFechaCierre());
					listaArchivosConvocatoriaExterna = servicioGeneral
							.obtenerObjetosLimitado(ArchivoConvocatoriaExterna.class,
									"select #id ace.id, #nombre ace.nombre from "
											+ "ArchivoConvocatoriaExterna ace where " + " ace.convocatoria = '"
											+ aval.getAviConvocatoria() + "' and ace.estado = 'V'");
					if(convocatoria.getListaCortes()!=null && !convocatoria.getListaCortes().isEmpty()) {
						for(int i=0; i < convocatoria.getListaCortes().size(); i++) {
							if(convocatoria.getListaCortes().get(i).getSede().getId().equals(investigadorActual.getDependencia().getSede().getId())) {
								fechaLimiteSede = convocatoria.getListaCortes().get(i).getFechaLimite();
								break;
							}
						}
					}
				}
			} else {
				nuevaConvocatoria = true;
			}
		} else {
			nuevaConvocatoria = false;
			//mensajeError("No se ha seleccionado una convocatoria");
		}
		
		if (convocatoria.getNaturaleza() != null
				&& (convocatoria.getNaturaleza().getId().equals(376L) || convocatoria.getId() <= -11)) {
			convocatoriaInternacional = true;
		} else {
			convocatoriaInternacional = false;
		}
		
		listaTipoArchivo = servicioGeneral
				.obtenerListaObjetos("TipoArchivo e where e.parametro in ('"+aval.getTipo()+"') or e.id = 0 and e.estado in ('A') or (e.convocatoriaExterna = "+convocatoria.getId()+") order by e.nombre");
		selTipoArchivoAval = new SelectItem[listaTipoArchivo.size()];
		for (int i = 0; i < listaTipoArchivo.size(); i++) {
			TipoArchivo ta = (TipoArchivo) listaTipoArchivo.get(i);
			String info = "";
			if(ta.isObligatorio()) {
				info = "(Obligatorio)";
			}else {
				info = "(Opcional)";
			}
			selTipoArchivoAval[i] = new SelectItem(ta.getId(), ta.getNombre()+" "+info);
		}
	}

	/**
	 * Carga regiones para aval de regalías de tabla HER_DOMINIO_DETALLE
	 */
	public void cargarRegiones() {
		listaRegiones = new ArrayList<SelectItem>();
		String consulta = "select dd from Dominio d, DominioDetalle dd where "
				+ "d.id = dd.identificador.id and d.id = 21 and dd.identificador.tipo "
				+ "like 'REG%' and dd.identificador.tipo <> 'REG3'";
		List<DominioDetalle> lista = servicioGeneral.obtenerObjetos(DominioDetalle.class, consulta);

		if (!esListaVacia(lista)) {
			for (int i = 0; i < lista.size(); i++) {
				DominioDetalle dominio = (DominioDetalle) lista.get(i);
				listaRegiones.add(new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion()));
			}
			DominioDetalle region1 = (DominioDetalle) lista.get(0);
			region = region1.getIdentificador().getTipo().toString();
			cargarSubRegiones();
		}
	}

	/**
	 * Carga departamentos asociados a la region seleccionada
	 */
	public void cargarSubRegiones() {
		listaSubRegiones = new ArrayList<SelectItem>();

		String consulta = "select dd from DominioDetalle dd where dd.estado = '" + region + "' ";
		List<DominioDetalle> lista = servicioGeneral.obtenerObjetos(DominioDetalle.class, consulta);

		if (!esListaVacia(lista)) {
			for (int i = 0; i < lista.size(); i++) {
				DominioDetalle dominio = (DominioDetalle) lista.get(i);
				listaSubRegiones.add(new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion()));
			}
		}
	}

	/**
	 * Carga paises para aval de movilidad/evento
	 */
	public void cargarPaises() {
		List<Pais> listaPaises = servicioGeneral.obtenerListaObjetosOrdenadosAscG(Pais.class, "nombre");
		paisItem = new SelectItem[listaPaises.size()];
		for (int i = 0; i < listaPaises.size(); i++) {
			Pais p = listaPaises.get(i);
			paisItem[i] = new SelectItem(p.getId(), p.getNombre());
		}
	}

	/**
	 * Carga departamentos para el aval de regalías
	 */
	public void cargarDepartamentos() {
		listaDepartamentos = new ArrayList<SelectItem>();
		String consulta = "select d from Departamento d where d.id like 'CO%' " + "order by d.nombre";
		List<Departamento> listaDepartament = servicioGeneral.obtenerObjetos(Departamento.class, consulta);

		if (!esListaVacia(listaDepartament)) {
			for (int i = 0; i < listaDepartament.size(); i++) {
				Departamento departamento = (Departamento) listaDepartament.get(i);
				listaDepartamentos.add(new SelectItem(departamento.getId(), departamento.getNombre()));
			}
		}
	}

	/**
	 * Cargar ciudades de acuerdo al departamento seleccionado
	 */
	public void cambiarCiudad() {
		ciudadItem = new SelectItem[0];
		if (aval.getDepartamento() == null || "".equals(aval.getDepartamento())) {
			mensajeError("Debe seleccionar un departamento para que sean listados los municipios.");
			return;
		} else {
			List<Ciudad> listaCiudades = servicioGeneral.obtenerObjetos(Ciudad.class,
					"select c from Ciudad c where c.departamento.id = '" + this.aval.getDepartamento() + "'");
			ciudadItem = new SelectItem[listaCiudades.size()];
			for (int i = 0; i < listaCiudades.size(); i++) {
				Ciudad ci = (Ciudad) listaCiudades.get(i);
				ciudadItem[i] = new SelectItem(ci.getId(), ci.getNombre());
			}
		}
	}

	/**
	 * Consulta grupos de investigación a los que pertenece el investigador
	 * actual
	 */
	public void cargarGruposInvestigacion() {
		gruposInvestigacion = new ArrayList<SelectItem>();
		List<Grupo> gruposAval = servicioGeneral.obtenerObjetosLimitado(Grupo.class,
				"select #id g.id, #nombre g.nombre from Grupo g, " + "InvestigadorGrupo ig where "
						+ " ig.investigador.id.tipoDocumento = '" + investigadorActual.getId().getTipoDocumento()
						+ "' and ig.investigador.id.documento = '" + investigadorActual.getId().getDocumento()
						+ "' and (g.estadoGrupo.id in ('S','P','A')) " + "and g.id = ig.grupo.id");

		if (!esListaVacia(gruposAval)) {
			for (int i = 0; i < gruposAval.size(); i++) {
				Grupo grupo = (Grupo) gruposAval.get(i);
				gruposInvestigacion.add(new SelectItem(grupo.getId(), grupo.getNombre()));

			}
		}
	}

	/**
	 * Listado de sedes de la universidad
	 */
	public void cargarSedes() {
		sedesItems = new SelectItem[0];
		List<Dependencia> sedes = servicioGeneral.obtenerObjetos(Dependencia.class,
				"select d from Dependencia d where d.esSede = 'Y' order by d.nombre");
		sedesItems = new SelectItem[sedes.size()];
		for (int i = 0; i < sedes.size(); i++) {
			Dependencia dd = (Dependencia) sedes.get(i);
			sedesItems[i] = new SelectItem(dd.getId(), dd.getNombre());
		}
	}

	/**
	 * Consulta de Listado de convenios marco vigentes
	 */
	public void cargarConvenios() {
		listaConvenios = new ArrayList<Convenio>();
		conveniosItem = new ArrayList<SelectItem>();
		if (esAvalNuevo) {
			Convenio convenio = new Convenio();
			aval.setConvenio(convenio);
		}

		if (esAvalNuevo || esEdicion) {
			listaConvenios = servicioGeneral.obtenerObjetosLimitado(Convenio.class,
					"select #id c.id, #nombre c.nombre from Convenio c where c.tipo.id = '1' and "
							+ "c.fechaInicio <= SYSDATE and c.fechaFinalizacion > SYSDATE and "
							+ "(c.estado = 'A') and c.nombre is not null order by c.nombre");
		} else {
			if (aval.getConvenio() != null && aval.getConvenio().getId() != null) {

				listaConvenios = servicioGeneral.obtenerObjetosLimitado(Convenio.class,
						"select #id c.id, #nombre c.nombre from Convenio c where c.tipo.id = 1 and "
								+ "c.nombre is not null order by c.nombre");
			}
		}

		if (!esListaVacia(listaConvenios)) {
			for (int i = 0; i < listaConvenios.size(); i++) {
				Convenio c = (Convenio) listaConvenios.get(i);
				conveniosItem.add(new SelectItem(c.getId(), c.getNombre()));
			}
		}
	}
	
	public void cargarListaComitesEtica() {
		List<AvalComiteEtica> listaCEPIs = new ArrayList<AvalComiteEtica>();
		listaCEPIsItem = new ArrayList<SelectItem>();
		if (esAvalNuevo)
			aval.setCepi(new AvalComiteEtica());
		
		listaCEPIs = servicioGeneral.obtenerObjetosLimitado(AvalComiteEtica.class,
				"select #id c.id, #nombre c.nombre from AvalComiteEtica c where c.tipo = 1 order by c.id");
		
		for (AvalComiteEtica cepi : listaCEPIs)
			listaCEPIsItem.add(new SelectItem(cepi.getId(), cepi.getNombre()));

	}
	
	public void cargarListaArticulosCientificos() {
		listaArtCientificosItem = servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccione(Tipos.TIPOS_TIPO_AVAL_ESTADO_ARTICULO);
	}

	/**
	 * Solicitud de habilitación de nuevo convenio marco
	 */
	public void solicitudCreacionConvenio() {
		TipoConvenio tc = new TipoConvenio();
		tc.setId(1L);

		List<FuenteFinanciacion> lista = cargarEntidadesExternas(CONSULTA_ENTIDADES_EXTERNAS_SIN_UNAL);
		if (!esListaVacia(lista)) {
			for (int i = 0; i < lista.size(); i++) {
				FuenteFinanciacion entidadConvenio = (FuenteFinanciacion) lista.get(i);
				if (convenioIngresado.getEntidad().getId().equals(entidadConvenio.getId())) {
					convenioIngresado.getEntidad().setDescripcion(entidadConvenio.getDescripcion());
					break;
				}
			}
		}

		convenioIngresado.setTipo(tc);
		convenioIngresado.setEstado("P");
		servicioGeneral.guardarObjeto(convenioIngresado);

		if (convenioIngresado.getId() != null) {
			Convenio convenioTemporal = new Convenio();
			convenioTemporal.setTipo(tc);
			convenioTemporal.setId(-1L);
			aval.setConvenio(convenioTemporal);

			Locale localidad = new Locale("sp", "co");
			SimpleDateFormat formatoFecha = new SimpleDateFormat("MMMM dd yyyy", localidad);

			String fechaInicio;
			String fechaFin;

			if (convenioIngresado.getFechaInicio() != null && !"".equals(convenioIngresado.getFechaInicio())) {
				fechaInicio = formatoFecha.format(convenioIngresado.getFechaInicio());
			} else {
				fechaInicio = "Sin información";
			}

			if (convenioIngresado.getFechaFinalizacion() != null
					&& !"".equals(convenioIngresado.getFechaFinalizacion())) {
				fechaFin = formatoFecha.format(convenioIngresado.getFechaFinalizacion());
			} else {
				fechaFin = "Sin información";
			}

			Correo correoConvenio = new Correo();
			correoConvenio.setOrigen(Correo.CORREO_HERMES_SOLICITUDES);
			correoConvenio.setAsunto("Ingreso de nuevo convenio");
			correoConvenio.setCuerpo("Se ha ingresado en el Sistema Hermes un nuevo "
					+ "convenio marco de la Universidad Nacional con la" + " siguiente información:"
					+ "\n\nNombre del convenio: " + convenioIngresado.getNombre() + " \n" + "Objeto del convenio: "
					+ convenioIngresado.getObjeto() + " \n" + "Entidad del convenio: "
					+ convenioIngresado.getEntidad().getDescripcion() + " \n" + "Fecha de inicio: " + fechaInicio
					+ " \n" + "Fecha de finalización " + fechaFin + "." + "\n\n Este convenio ha sido ingresado para "
					+ "relacionarse con el proyecto " + aval.getIdProyecto() + ". " + " \n"
					+ " El convenio fue ingresado por el profesor: " + investigadorActual.getNombreCompletoMinusculas()
					+ " (" + investigadorActual.getEmail() + ")" + "\n\nSi este convenio es aprobado para la "
					+ "solicitud, por favor solicite el cambio de "
					+ "estado del mismo a activo para que sea disponible " + "en la lista de selección, el código es: "
					+ convenioIngresado.getId() + ". De lo contrario seguirá inactivo.");

			correoConvenio.adicionarDireccion("hermes@unal.edu.co");

			if (convenioIngresado.getArchivo() != null) {
				// Se recorta el nombre del archivo para que no tenga la
				// ruta absoluta
				String extensionArchivo = obtenerExtensionArchivo(convenioIngresado.getArchivo());
				String nombreArchivo = convenioIngresado.getId().toString() + extensionArchivo;
				correoConvenio.setNombreAdjunto(nombreArchivo);

				FacesContext ctx = FacesContext.getCurrentInstance();
				ServletContext sc = (ServletContext) ctx.getExternalContext().getContext();
				String rutaArchivo = sc.getRealPath(RUTA_ADJUNTO);
				rutaArchivo += "\\" + convenioIngresado.getId().toString();
				servicioCorreo.crearArchivo(rutaArchivo, archivoCargar.getContents());
				correoConvenio.setAdjunto(rutaArchivo);
			}
			servicioCorreo.enviarCorreo(correoConvenio);
			mensajeInfo(
					"La información del convenio será validada por " + "personal de la Vicerrectoría de Investigación, "
							+ "en caso que no se pueda verificar, se estarán "
							+ "comunicando con usted para comprobar la " + "información del convenio.");
		}
	}

	/**
	 * Carga tipos de documentos para adición de personas
	 */
	public void cargarTiposDocumento() {
		tipoDocumentoItem = new ArrayList<SelectItem>();
		List<TipoDocumento> listaTipoDocumento = servicioGeneral.obtenerListaObjetos(TipoDocumento.class);
		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			TipoDocumento td = listaTipoDocumento.get(i);
			tipoDocumentoItem.add(new SelectItem(td.getId(), td.getNombre()));
		}
	}

	public void consultarListadoOpciones() {
		String tipoAval = aval.getTipo();
		aval = new Aval();
		aval.setTipo(tipoAval);
		List<Proyecto> listaP;
		aval.setIdProyecto(0L);

		if (!esCadenaVacia(aval.getTipo())) {

			if (!aval.getTipo().equals(Aval.TIPO_GRUPO_INVESTIGACION)
					&& !aval.getTipo().equals(Aval.TIPO_INVESTIGADOR_INDEPENDIENTE)
				) {
				/**
				 * Variables para personalizar la consulta de proyectos
				 */
				String estadosPermitidos = "";
				String modalidad = "";
				String adicional = "";
				String jornadaDocente = "";
				String financiacionPermitida = "";
				String restriccionAvalesPrevios = " or ((a.aviEstado in ('D') and a.aviAvaldireccion in ('S')) OR (a.aviEstado in ('F') and a.aviAvaldireccion in ('S')))";
				listaFichasItems.clear();

				if (aval.isEsProyectoInvestigacion() 
						|| aval.isEsRegalias() 
						|| aval.isEsPaedRegalias() 
						|| aval.isEsConvocatoriaRegalias()
						|| aval.isEsRequisitosRegalias()
						|| aval.isEsCentroInvestigacion() 
					) {
					estadosPermitidos = "'" + EstadoProyecto.PROPUESTO + "'";
					modalidad = "and p.modalidad.id = '" + MODALIDAD_FICHA_MINIMA_ID + "' ";
					if (aval.isEsRegalias()) {
						restriccionAvalesPrevios = " or (a.aviEstado in ('D') and a.aviAvaldireccion in ('S') and a.tipo != 'PSR')";
					}
					
					if (aval.isEsConvocatoriaRegalias()) {
						restriccionAvalesPrevios = " or (((a.aviEstado in ('D') and a.aviAvaldireccion in ('S')) OR (a.aviEstado in ('F') and a.aviAvaldireccion in ('S'))) and a.tipo = 'PCR')";
					}
					
					if (aval.isEsRequisitosRegalias()) {
						adicional = " and p.permitirAvalRegaliasRequisitos = 'S' and p.permitirModificacion not in ('S')";
						restriccionAvalesPrevios = "";
					}
				} else if (aval.isEsMovilidadEvento()) {
					estadosPermitidos = "'" + EstadoProyecto.ELEGIBLE + "','" + EstadoProyecto.APROBADO + "','"
							+ EstadoProyecto.ACTIVO + "','" + EstadoProyecto.FINALIZADO + "'";
				} else if (aval.isEsProyectoContrapartida()) {
					estadosPermitidos = "'" + EstadoProyecto.APROBADO + "','" + EstadoProyecto.ACTIVO + "'";
					modalidad = "and p.modalidad.id in ('" 
							+ MODALIDAD_FICHA_MINIMA_ID + "', '"
							+ MODALIDAD_CONVOCATORIA_EXTERNA_ID + "', '" + MODALIDAD_PROYECTOS_CONVOCATORIA_EXTERNA_ID
							+ "')";
					jornadaDocente = "N";
					restriccionAvalesPrevios = " or (a.aviEstado in ('D') and a.aviAvaldireccion in ('N'))";
				} else if (aval.isEsJornadaDocente()) {
					estadosPermitidos = "'" + EstadoProyecto.PROPUESTO + "'";
					modalidad = "and p.modalidad.id = '" + MODALIDAD_FICHA_MINIMA_ID + "' and p.tipoActividad in ('FM_PINV','FM_FL','FM_PT') ";
					jornadaDocente = "N";
					financiacionPermitida = "and p.id not in (select #idProyecto f.proyecto.id from Financiacion f, FuenteFinanciacion ff where f.fuente.id = ff.id and ff.internaExterna in ('E','OE') and f.esLegalizacion is null and f.tipoEntidad is null )";
				} else if (aval.isEsArticuloInvestigacion()) {
					estadosPermitidos = "'" + EstadoProyecto.SUSPENDIDO + "','" + EstadoProyecto.CANCELADO + "','"
							+ EstadoProyecto.ACTIVO + "','" + EstadoProyecto.FINALIZADO + "'";
				}

				/**
				 * Se verifica si tiene aval devuelto para correcciones, para
				 * controlar su seleccion.
				 */
				if(aval.isEsEtico()){
//					estadosPermitidos = "'" + EstadoProyecto.ELEGIBLE + "','" + EstadoProyecto.EN_LEGALIZACION + "','" + EstadoProyecto.APROBADO + "'";
					
					String convInterna = "p.modalidad.id not in (2, 10) and p.estadoProyecto.id in ('E','AP')";
					String convExterna = "p.modalidad.id in (10) and p.estadoProyecto.id in ('P','E','AP','EL')";
					String registroUnico = "p.modalidad.id = 2 and p.estadoProyecto.id in ('EL','AP') and p.id in (select #idProyecto a.idProyecto from Aval a where a.idProyecto = p.id and (a.aviAvaldireccion = 'S' or a.avalVice = 'S' or a.aviAvalUab = 'S' or a.avalDRE = 'S'))";
					String ruPropuesto = "p.modalidad.id = 2 and p.estadoProyecto.id in ('P') ";
					String ruPropuesto1 = ruPropuesto + "and p.id not in (select #idProyecto a.idProyecto from Aval a where a.idProyecto = p.id and a.aviEstado NOT IN ('B'))";
					String ruPropuesto2 = ruPropuesto + "and p.id in (select #idProyecto a.idProyecto from Aval a where a.idProyecto = p.id and a.tipo = 'PINV' and (a.aviEstado IN ('P','I','C') or a.aviAvalfacultad = 'S' or a.aviAvaldireccion = 'S' or a.avalVice = 'S'))";
					String ruPropuesto3 = ruPropuesto + "and p.id in (select #idProyecto a.idProyecto from Aval a where a.idProyecto = p.id and a.tipo IN ('PSR','SR','JD','PCR','VRSR'))";
					String avalesEticosExistentes = "p.id not in (select #idProyecto a.idProyecto from Aval a where a.idProyecto = p.id and a.tipo IN ('ETICO') and a.aviEstado IN('EP','SP','CP','ES','SS'))";

					
					listaP = servicioGeneral.obtenerObjetosLimitado(Proyecto.class,
						"SELECT distinct #id p.id, #nombre p.nombre "
						+ "FROM Proyecto p, "
						+ "InvestigadorProyecto ip "
						+ "WHERE "
						+ "p.id not in (select #idProyecto a.idProyecto from Aval a where a.idProyecto = p.id and (a.aviEstado = 'C') and a.tipo NOT IN ('PINV')) "
						+ "and ip.investigador.id.tipoDocumento like '%" + investigadorActual.getId().getTipoDocumento() + "%' "
						+ "and ip.investigador.id.documento like '%" + investigadorActual.getId().getDocumento() + "%' "
						+ "and ip.tipo.id='P' "
						+ "and ip.proyecto.id=p.id "
						+ "and (("+ convInterna + ") or ("+ registroUnico +") or ("+ ruPropuesto1 +") or ("+ ruPropuesto2 +") or ("+ ruPropuesto3 +") or ("+ convExterna +")) "
						+ "and ("+ avalesEticosExistentes +")"
						+ "and p.modalidad.id not in (" + MODALIDADES_PERMISO_CONTRATO + ") " 
						+ "order by p.id desc");
				} else {
					listaP = servicioGeneral.obtenerObjetosLimitado(Proyecto.class,
						"SELECT distinct #id p.id, #nombre p.nombre "
						+ "FROM Proyecto p, "
						+ "InvestigadorProyecto ip "
						+ "WHERE "
						+ "p.id not in (select #idProyecto a.idProyecto from Aval a where a.idProyecto = p.id and ((a.aviEstado in ('C','P','I','IF')) " + restriccionAvalesPrevios + " )) "
						+ "and ip.investigador.id.tipoDocumento like '%" + investigadorActual.getId().getTipoDocumento() + "%' "
						+ "and ip.investigador.id.documento like '%" + investigadorActual.getId().getDocumento() + "%' "
						+ "and ip.tipo.id='P' "
						+ "and ip.proyecto.id=p.id "
						+ "and (p.estadoProyecto.id in ("+ estadosPermitidos + ")) "
						+ "and p.modalidad.id not in (" + MODALIDADES_PERMISO_CONTRATO + ") " 
						+ adicional
						+ modalidad 
						+ "and (p.esJornadaDocente like '%" + jornadaDocente + "%' or p.esJornadaDocente is null) " 
						+ financiacionPermitida 
						+ " order by p.id desc");
				}
				construirListadoProyectos(listaP);
				if(aval.isEsCentroInvestigacion() || aval.isEsJornadaDocente() || aval.isEsArticuloInvestigacion()) {
					habilitarCampos = true;
				}
			} else {
				habilitarCampos = true;
			}
		} else {
			mensajeError("Debe seleccionar un tipo de aval para que se muestren las opciones permitidas.");
		}
	}

	public void construirListadoProyectos(List<Proyecto> listaP) {
		if (!esListaVacia(listaP)) {
			for (int i = 0; i < listaP.size(); i++) {
				Proyecto fichaProyecto = (Proyecto) listaP.get(i);
				listaFichasItems.add(new SelectItem(fichaProyecto.getId(),
						fichaProyecto.getId() + " - " + fichaProyecto.getNombre()));
			}
		} else {
			mensajeError("No se encontraron proyectos disponibles para el tipo de aval seleccionado.");
		}
	}

	public List<MontoAno> getListaMontoAnoo() {
		List<MontoAno> listamonto = new ArrayList<MontoAno>();
		if (aval != null && aval.getMontoAval().size() > 0) {
			for (Iterator<MontoAno> iterador = aval.getMontoAval().iterator(); iterador.hasNext();) {
				listamonto.add(iterador.next());
			}
		}
		return listamonto;
	}

	/**
	 * Aval de regalías intersede
	 * 
	 * @return
	 */
	public List<Dependencia> getListaDependencia() {
		List<Dependencia> listadepedencia = new ArrayList<Dependencia>();
		if (aval != null && aval.getDependencias().size() > 0) {
			for (Iterator<Dependencia> iterador = aval.getDependencias().iterator(); iterador.hasNext();) {
				listadepedencia.add(iterador.next());
			}
		}
		return listadepedencia;
	}

	/**
	 * Consulta archivos asociados al aval, en caso de que se encuentre en
	 * edición o para consulta
	 */
	public void consultarArchivos() {

		String consultaArchivos = "select a from ArchivoAval a where a.aval =" + aval.getAviId();
		List<ArchivoAval> listaA = (List<ArchivoAval>) servicioGeneral.obtenerObjetos(ArchivoAval.class,
				consultaArchivos);
		if (!esListaVacia(listaA)) {
			for (ArchivoAval archivoAval : listaA) {
				listaArchivos.add(archivoAval);
			}
		}

		List<ArchivoAval> archi = servicioGeneral.obtenerListaObjetosWhere(ArchivoAval.class,
				" where a.avalCoor='" + aval.getAviId() + "'");
		if (archi != null) {
			for (int i = 0; i < archi.size(); i++) {
				aval.getArchivosCoor().add(archi.get(i));
			}
		}
	}

	/**
	 * Descargar archivos de la solicitud de aval, ya sea de solicitud o
	 * revisión
	 */

	public void descargarArchivo() {
		if (archivoAval != null) {
			descargarArchivoAvalGenerico(archivoAval.getId());
		}
	}

	/**
	 * Elimina archivos de la solicitud de aval
	 */
	public void eliminarArchivo() {

		listaArchivos.remove(archivoAval);
		aval.eliminarArchivo(archivoAval);
		archivoAval.setAval(0L);
		archivoAval.setFechaBorrado(new Date());

		if (archivoAval.getDescripcion() != null) {
			archivoAval.setDescripcion(archivoAval.getDescripcion() + " - Eliminado por "
					+ investigadorActual.getId().getDocumento() + "-" + investigadorActual.getId().getTipoDocumento());
		} else {
			archivoAval.setDescripcion("Eliminado por " + investigadorActual.getId().getDocumento() + "-"
					+ investigadorActual.getId().getTipoDocumento());
		}
		if (aval.getAviId() != null) {
			archivoAval.setDescripcion(archivoAval.getDescripcion() + "- Aval previo = " + aval.getAviId());
		}
		servicioGeneral.guardarObjeto(archivoAval);
	}

	/**
	 * Avales en proceso o tramitados del mismo tipo por el investigador
	 */
	public void avalesInvestigador(String tipo) {
		if(tipo.equals(Aval.TIPO_REGALIAS_VERIF_REQ)) {
			tipo=Aval.TIPO_CONVOCATORIA_REGALIAS;
		}
		avalesInvestigador = new ArrayList<SelectItem>();
		List<Aval> listaAvalesInvestigador = servicioGeneral.obtenerObjetosLimitado(Aval.class,
				"select #aviId a.aviId, #aviTitulo a.aviTitulo from " + "Aval a where " + "a.tipoDocumento = '"
						+ investigadorActual.getId().getTipoDocumento() + "' and a.documento = '"
						+ investigadorActual.getId().getDocumento() + "' and a.tipo = '" + tipo
						+ "' and a.aviEstado != 'B' " + "and a.aviEstado != 'P' and a.aviEstado != 'C' "
						+ "order by a.aviId asc");

		if (!esListaVacia(listaAvalesInvestigador)) {
			for (int i = 0; i < listaAvalesInvestigador.size(); i++) {
				Aval avalInv = (Aval) listaAvalesInvestigador.get(i);
				avalesInvestigador
						.add(new SelectItem(avalInv.getAviId(), avalInv.getAviId() + " - " + avalInv.getAviTitulo()));
			}
		}
	}

	/**
	 * Carga terminos vigentes
	 */
	public void cargarTerminos() {

		List<TerminosConvocatoriaExterna> listaTerminosConvocatoriasExternas = null;
		if (esAvalNuevo || esEdicion) {
			if(aval.isEsEtico())
				aval.setTerminos(5L);
			else
				aval.setTerminos(4L);
			listaTerminosConvocatoriasExternas = servicioGeneral.obtenerObjetosLimitado(
					TerminosConvocatoriaExterna.class, "select #id tce.id, #descripcion tce.descripcion "
							+ "from TerminosConvocatoriaExterna tce where tce.id = '"+ aval.getTerminos() +"' and tce.estado = 'A'");
			if (!esListaVacia(listaTerminosConvocatoriasExternas)) {
				TerminosConvocatoriaExterna c = (TerminosConvocatoriaExterna) listaTerminosConvocatoriasExternas.get(0);
				aval.setTerminos(c.getId());
			} else {
				aval.setTerminos(0L);
				aceptaTerminos = false;
			}
		} else {
			if (aval.getTerminos() != null) {
				listaTerminosConvocatoriasExternas = servicioGeneral.obtenerObjetosLimitado(
						TerminosConvocatoriaExterna.class, "select #id tce.id, #descripcion tce.descripcion "
								+ "from TerminosConvocatoriaExterna tce where tce.id = '" + aval.getTerminos() + "'");
				if (aval.getTerminos() == 0L) {
					aceptaTerminos = false;
				} else {
					aceptaTerminos = true;
				}
			} else {
				aceptaTerminos = false;
			}
		}
		if (!esListaVacia(listaTerminosConvocatoriasExternas)) {
			terminos = (TerminosConvocatoriaExterna) listaTerminosConvocatoriasExternas.get(0);
		}
	}

	/**
	 * Carga lista de dependencias de ejecucion
	 */
	public void dependenciasEjecucion() {
		dependenciaItem = new ArrayList<SelectItem>();
		String dpnsql = "select e from Dependencia e where e.estado='A' and "
				+ "e.esDepartamento='N' and (e.esFacultad = 'Y' or e.esSede = 'Y') "
				+ "and e.nombre not like 'Centro%' and e.nombre not like 'Conta%' "
				+ "and e.nombre not like 'Consul%' and e.nombre not like 'Consej%' "
				+ "and e.nombre not like 'Di%' order by e.nombre";
		List<Dependencia> dependenciasUN = servicioGeneral.obtenerObjetos(Dependencia.class, dpnsql);
		if (!esListaVacia(dependenciasUN)) {
			for (int i = 0; i < dependenciasUN.size(); i++) {
				Dependencia dd = (Dependencia) dependenciasUN.get(i);
				dependenciaItem.add(new SelectItem(dd.getId(), dd.getNombre()));
			}
		}
	}

	/**
	 * Carga listado de rubros permitidos para aval de contrapartida y movilidad
	 */
	public void listaRubrosQuipu() {
		listaRubrosQuipu = new ArrayList<SelectItem>();
		String rubsql = "select e from TipoRubro e where e.id='14' or "
				+ "e.id='119' or e.id='3' or e.id='131' or e.id='107' or "
				+ "e.id='58' or e.id='124' or e.id='126' or e.id='9' or "
				+ "e.id='70' or e.id='10' or e.id='12' or e.id='13' or "
				+ "e.id='96' or e.id='111' or e.id='60' or e.id='155' or "
				+ "e.id='156' or e.id='120' or e.id='135' or e.id='141' or "
				+ "e.id='82' or e.id='143' or e.id='144' or e.id='145' or " + "e.id='142' order by e.nombre";
		List<TipoRubro> rubrosPosibles = servicioGeneral.obtenerObjetos(TipoRubro.class, rubsql);
		if (!esListaVacia(rubrosPosibles)) {
			for (int i = 0; i < rubrosPosibles.size(); i++) {
				TipoRubro tr = (TipoRubro) rubrosPosibles.get(i);
				listaRubrosQuipu.add(new SelectItem(tr.getId(), tr.getNombre()));
			}
		}
	}

	public Aval getAval() {
		return aval;
	}

	public void setAval(Aval aval) {
		this.aval = aval;
	}

	public SelectItem[] getCategoriaItems() {
		return categoriaItems;
	}

	public void setCategoriaItems(SelectItem[] categoriaItems) {
		this.categoriaItems = categoriaItems;
	}

	public Investigador getInvestigadorActual() {
		return investigadorActual;
	}

	public void setInvestigadorActual(Investigador investigadorActual) {
		this.investigadorActual = investigadorActual;
	}

	public UploadedFile getArchivoCargar() {
		return archivoCargar;
	}

	public void setArchivoCargar(UploadedFile archivoCargar) {
		this.archivoCargar = archivoCargar;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public SelectItem[] getPaisItem() {
		return paisItem;
	}

	public void setPaisItem(SelectItem[] paisItem) {
		this.paisItem = paisItem;
	}

	public CorreoPlantilla getCorreoActual() {
		return correoActual;
	}

	public void setCorreoActual(CorreoPlantilla correoActual) {
		this.correoActual = correoActual;
	}

	public Correo getCorreo() {
		return correo;
	}

	public void setCorreo(Correo correo) {
		this.correo = correo;
	}

	public void setListaArchivos(List<ArchivoAval> listaArchivos) {
		this.listaArchivos = listaArchivos;
	}

	public List<SelectItem> getListaRegiones() {
		return listaRegiones;
	}

	public void setListaRegiones(List<SelectItem> listaRegiones) {
		this.listaRegiones = listaRegiones;
	}

	public List<SelectItem> getListaSubRegiones() {
		return listaSubRegiones;
	}

	public List<EntidadArticulo> getListaEntidades() {
		List<EntidadArticulo> listaEntidadesActual = new ArrayList<EntidadArticulo>();
		if (aval != null && aval.getEntidadAval().size() > 0) {
			for (Iterator<EntidadArticulo> iterador = aval.getEntidadAval().iterator(); iterador.hasNext();) {
				listaEntidadesActual.add(iterador.next());
			}
		}
		return listaEntidadesActual;
	}

	public String getMontoReg() {
		return montoReg;
	}

	public void setMontoReg(String montoReg) {
		this.montoReg = montoReg;
	}

	public static String getRutaAdjunto() {
		return RUTA_ADJUNTO;
	}

	public List<SelectItem> getListaDepartamentos() {
		return listaDepartamentos;
	}

	public SelectItem[] getListaFases() {
		return listaFases;
	}

	public void setListaFases(SelectItem[] listaFases) {
		this.listaFases = listaFases;
	}

	public SelectItem[] getListaAno() {
		return listaAno;
	}

	public void setListaAno(SelectItem[] listaAno) {
		this.listaAno = listaAno;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSubregion() {
		return subregion;
	}

	public void setSubregion(String subregion) {
		this.subregion = subregion;
	}

	public SelectItem[] getEventoItem() {
		return eventoItem;
	}

	public void setEventoItem(SelectItem[] eventoItem) {
		this.eventoItem = eventoItem;
	}

	public List<ArchivoAval> getListaArchivos() {
		return listaArchivos;
	}

	public List<Long> getSedesIds() {
		return sedesIds;
	}

	public void setSedesIds(List<Long> sedesIds) {
		this.sedesIds = sedesIds;
	}

	public SelectItem[] getFichasItems() {
		return fichasItems;
	}

	public void setFichasItems(SelectItem[] fichasItems) {
		this.fichasItems = fichasItems;
	}

	public Proyecto getFichaSeleccionada() {
		return fichaSeleccionada;
	}

	public void setFichaSeleccionada(Proyecto fichaSeleccionada) {
		this.fichaSeleccionada = fichaSeleccionada;
	}

	public List<SelectItem> getListaFichasItems() {
		return listaFichasItems;
	}

	public SelectItem[] getCiudadItem() {
		return ciudadItem;
	}

	public void setCiudadItem(SelectItem[] ciudadItem) {
		this.ciudadItem = ciudadItem;
	}

	public EntidadArticulo getEntidadSeleccionada() {
		return entidadSeleccionada;
	}

	public void setEntidadSeleccionada(EntidadArticulo entidadSeleccionada) {
		this.entidadSeleccionada = entidadSeleccionada;
	}

	public boolean isHabilitarCampos() {
		return habilitarCampos;
	}

	public void setHabilitarCampos(boolean habilitarCampos) {
		this.habilitarCampos = habilitarCampos;
	}

	public List<SelectItem> getConvocatoriasExternasItem() {
		return convocatoriasExternasItem;
	}

	public List<ArchivoConvocatoriaExterna> getListaArchivosConvocatoriaExterna() {
		return listaArchivosConvocatoriaExterna;
	}

	public void setListaArchivosConvocatoriaExterna(List<ArchivoConvocatoriaExterna> listaArchivosConvocatoriaExterna) {
		this.listaArchivosConvocatoriaExterna = listaArchivosConvocatoriaExterna;
	}

	public ConvocatoriaExterna getConvocatoria() {
		return convocatoria;
	}

	public void setConvocatoria(ConvocatoriaExterna convocatoria) {
		this.convocatoria = convocatoria;
	}

	public boolean isEsConsulta() {
		return esConsulta;
	}

	public void setEsConsulta(boolean esConsulta) {
		this.esConsulta = esConsulta;
	}

	public boolean isEsEdicion() {
		return esEdicion;
	}

	public void setEsEdicion(boolean esEdicion) {
		this.esEdicion = esEdicion;
	}

	public boolean isEsAvalNuevo() {
		return esAvalNuevo;
	}

	public void setEsAvalNuevo(boolean esAvalNuevo) {
		this.esAvalNuevo = esAvalNuevo;
	}

	public boolean isNuevaConvocatoria() {
		return nuevaConvocatoria;
	}

	public void setNuevaConvocatoria(boolean nuevaConvocatoria) {
		this.nuevaConvocatoria = nuevaConvocatoria;
	}

	public List<SelectItem> getAvalesInvestigador() {
		return avalesInvestigador;
	}

	public List<SelectItem> getListaEntidadesExternas() {
		return listaEntidadesExternas;
	}

	public List<SelectItem> getGruposInvestigacion() {
		return gruposInvestigacion;
	}

	public void setGruposInvestigacion(List<SelectItem> gruposInvestigacion) {
		this.gruposInvestigacion = gruposInvestigacion;
	}

	public ArchivoAval getArchivoAval() {
		return archivoAval;
	}

	public void setArchivoAval(ArchivoAval archivoAval) {
		this.archivoAval = archivoAval;
	}

	public boolean isAceptaTerminos() {
		return aceptaTerminos;
	}

	public void setAceptaTerminos(boolean aceptaTerminos) {
		this.aceptaTerminos = aceptaTerminos;
	}

	public TerminosConvocatoriaExterna getTerminos() {
		return terminos;
	}

	public void setTerminos(TerminosConvocatoriaExterna terminos) {
		this.terminos = terminos;
	}

	public List<Convenio> getListaConvenios() {
		return listaConvenios;
	}

	public void setListaConvenios(List<Convenio> listaConvenios) {
		this.listaConvenios = listaConvenios;
	}

	public List<SelectItem> getConveniosItem() {
		return conveniosItem;
	}

	public Convenio getConvenioIngresado() {
		return convenioIngresado;
	}

	public void setConvenioIngresado(Convenio convenioIngresado) {
		this.convenioIngresado = convenioIngresado;
	}

	public List<SelectItem> getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}

	public List<SelectItem> getDependenciaItem() {
		return dependenciaItem;
	}

	public List<SelectItem> getListaRubrosQuipu() {
		return listaRubrosQuipu;
	}

	public SelectItem[] getSedesItems() {
		return sedesItems;
	}

	public void setSedesItems(SelectItem[] sedesItems) {
		this.sedesItems = sedesItems;
	}

	public List<SelectItem> getListaCEPIsItem() {
		return listaCEPIsItem;
	}

	public void setListaCEPIsItem(List<SelectItem> listaCEPIsItem) {
		this.listaCEPIsItem = listaCEPIsItem;
	}

	public ArchivoAval getDocumentoSeleccionado() {
		return documentoSeleccionado;
	}

	public void setDocumentoSeleccionado(ArchivoAval documentoSeleccionado) {
		this.documentoSeleccionado = documentoSeleccionado;
	}

	public SelectItem[] getTiposRecursoAvalEtico() {
		return tiposRecursoAvalEtico;
	}

	public void setTiposRecursoAvalEtico(SelectItem[] tiposRecursoAvalEtico) {
		this.tiposRecursoAvalEtico = tiposRecursoAvalEtico;
	}

	public Boolean getPermitirInterponerRecurso() {
		return permitirInterponerRecurso;
	}

	public void setPermitirInterponerRecurso(Boolean permitirInterponerRecurso) {
		this.permitirInterponerRecurso = permitirInterponerRecurso;
	}

	public int getDiasFaltantesPermitirRecurso() {
		return diasFaltantesPermitirRecurso;
	}

	public void setDiasFaltantesPermitirRecurso(int diasFaltantesPermitirRecurso) {
		this.diasFaltantesPermitirRecurso = diasFaltantesPermitirRecurso;
	}

	public Boolean getEsConsultaRecurso() {
		return esConsultaRecurso;
	}

	public void setEsConsultaRecurso(Boolean esConsultaRecurso) {
		this.esConsultaRecurso = esConsultaRecurso;
	}

	public Boolean getEsConsultaQueja() {
		return esConsultaQueja;
	}

	public void setEsConsultaQueja(Boolean esConsultaQueja) {
		this.esConsultaQueja = esConsultaQueja;
	}

	public SelectItem[] getListaArtCientificosItem() {
		return listaArtCientificosItem;
	}

	public void setListaArtCientificosItem(SelectItem[] listaArtCientificosItem) {
		this.listaArtCientificosItem = listaArtCientificosItem;
	}

	public PalabraClave getPalabraClave() {
		return palabraClave;
	}

	public void setPalabraClave(PalabraClave palabraClave) {
		this.palabraClave = palabraClave;
	}

	public void setListaPalabrasClave(List<PalabraClave> listaPalabrasClave) {
		this.listaPalabrasClave = listaPalabrasClave;
	}
		
	public boolean isConvocatoriaInternacional() {
		return convocatoriaInternacional;
	}

	public void setConvocatoriaInternacional(boolean convocatoriaExterna) {
		this.convocatoriaInternacional = convocatoriaExterna;
	}

	public Date getFechaLimiteSede() {
		return fechaLimiteSede;
	}

	public void setFechaLimiteSede(Date fechaLimiteSede) {
		this.fechaLimiteSede = fechaLimiteSede;
	}

	public boolean isNoModificar() {
		return noModificar;
	}

	public void setNoModificar(boolean noModificar) {
		this.noModificar = noModificar;
	}

	public List<TipoArchivo> getListaTipoArchivo() {
		return listaTipoArchivo;
	}

	public void setListaTipoArchivo(List<TipoArchivo> listaTipoArchivo) {
		this.listaTipoArchivo = listaTipoArchivo;
	}

	public SelectItem[] getSelTipoArchivoAval() {
		return selTipoArchivoAval;
	}

	public void setSelTipoArchivoAval(SelectItem[] selTipoArchivoAval) {
		this.selTipoArchivoAval = selTipoArchivoAval;
	}
}
