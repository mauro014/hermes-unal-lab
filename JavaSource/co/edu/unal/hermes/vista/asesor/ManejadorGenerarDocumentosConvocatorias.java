/**
 * @author Juan Guillermo Carvajal Patiño
 * @date 20/10/2016
 */

package co.edu.unal.hermes.vista.asesor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.ArchivoConvocatoriaPadre;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.CorteConvocatoria;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.ManejadorBase;
import co.edu.unal.hermes.vista.proyectos.ProyectosVistaOficiosConvocatoria;

public class ManejadorGenerarDocumentosConvocatorias extends ManejadorBase
{
	private static final long serialVersionUID = -1776544669849318071L;
	private String nombreConvocatoriaBusqueda;
	private List<ConvocatoriaPadre> listaConvocatoriasPadreBusqueda;
	private SelectItem[] convocatoriaPadreItem;
	private Long idConvocaPadre;
	private boolean mostrarSiConvPadreExiste = false;
	private Dependencia dep;
	private String tipoDocumento;
	private boolean mostrarSiProyectosElegibles = false;
	private boolean mostrarSiProyectosAprobados = false;
	private boolean mostrarSiDocCreacionConvocatoria = false;
	private List<ProyectosVistaOficiosConvocatoria> listaElegible;
	private List<CorteConvocatoria> listaCortesConvocatoria;
	private List<ConvocatoriaPadre> listaConvocatoriasPadre;
	private ConvocatoriaPadre convocatoriaPadreSeleccionada;
	private boolean mostrarCorteConvocatoria = false;
	private SelectItem[] selItemCortesConvocatoria;
	private Long numeroCorteConvocatoria;
	private String dependenciaConvocatoriaPadre;
	private String textoAdicionalOficioElegibles;
	private Persona per;
	private boolean mostrarFormularioElegibles = false;
	private boolean mostrarListaElegibles = false;
	private ArchivoConvocatoriaPadre archivoProcesoSeleccionado;
	private List<ArchivoConvocatoriaPadre> listaArchivos;
	private boolean mostrarSiOficio = true;
	private List<ProyectosVistaOficiosConvocatoria> listaAprobados;
	private boolean mostrarFormularioAprobados = false;
	private boolean mostrarListaAprobados = false;
	private boolean mostrarSiOficioAprobados = true;
	private String consecutivoOficioAprobados;
	private String textoAdicionalOficioAprobados;
	private String mostrarVigenciaAprobados;
	private String mostrarFirmaAprobados;
	private String responsableOficioAprobados;
	private String cargoResponsableOficioAprobados;
	private boolean mostrarSiFirma = false;
	private String tipoDocumentoAprobados;
	private String tipoDocumentoElegibles;
	private Long proyectoSeleccionado;
	private Long vigenciaSeleccionada;
	private boolean mostrarSiVigencia = false;
	private List<Dependencia> listaDependencias;
	private SelectItem[] selItemDependencia;
	private Date fechaResolucion;
	private String dependenciaResolucion;
	private boolean mostrarSiResolucion = false;
	private boolean esConvNacional = false;
	private boolean esAsesorConvNacional = false;
	private String tipoOficioPreDef;
	private UIComponent uploadArchivoProyecto;
	private UIComponent uploadArchivoProyectoAprobados;
	

	private static final String MENSAJE_SELECCION_CORTE_CONVOCATORIA = "Por favor seleccione el corte de la convocatoria.";

	public ManejadorGenerarDocumentosConvocatorias()
	{
		per = (Persona) sesion.getAttribute("persona");
		InvestigadorInterno invInt = servicioPersona.obtenerInvestigadorInternoCompleto(per.getId());

		if (invInt != null)
		{
			dep = invInt.getDependencia2();
		}
		listaDependencias = new ArrayList<Dependencia>();
	}

	public void consultarConvocatorias()
	{
		boolean encontrado = true;
		if (nombreConvocatoriaBusqueda.length() > 0)
		{
			String sql = "select #id p.id, #titulo p.titulo from ConvocatoriaPadre p where upper(p.titulo)" + " like '%"
					+ nombreConvocatoriaBusqueda.toUpperCase() + "%' order by p.id desc";
			listaConvocatoriasPadreBusqueda = servicioGeneral.obtenerObjetosLimitado(ConvocatoriaPadre.class, sql);
			if (!listaConvocatoriasPadreBusqueda.isEmpty())
			{
				convocatoriaPadreItem = new SelectItem[listaConvocatoriasPadreBusqueda.size()];
				for (int i = 0; i < listaConvocatoriasPadreBusqueda.size(); i++)
				{
					ConvocatoriaPadre con = (ConvocatoriaPadre) listaConvocatoriasPadreBusqueda.get(i);
					convocatoriaPadreItem[i] = new SelectItem(con.getId().toString(), con.getTitulo());
				}
				mostrarSiConvPadreExiste = true;
			}
			else
			{
				mostrarSiConvPadreExiste = false;
				encontrado = false;
			}
		}
		else
		{
			mostrarSiConvPadreExiste = false;
			encontrado = false;
		}
		if (!encontrado)
		{
			listaConvocatoriasPadreBusqueda = new ArrayList<ConvocatoriaPadre>();
			convocatoriaPadreItem = new SelectItem[0];
			mensajeError("Por favor verificar el nombre de la convocatoria, no se han encontrado resultados.");
		}
	}

	public void validarTipoDocumento()
	{
		if ("".equals(tipoDocumento))
		{
			mostrarSiProyectosElegibles = false;
			mostrarSiProyectosAprobados = false;
			mostrarSiDocCreacionConvocatoria = false;
			mensajeError("Por favor seleccione el tipo de documento que desea generar.");
		}
		else
		{
			convocatoriaPadreSeleccionada = servicioModalidad.obtenerConvocatoriaPadre(idConvocaPadre);

			if (validarDependenciaAsesor(convocatoriaPadreSeleccionada.getDependencia()))
			{
				switch (Integer.parseInt(tipoDocumento))
				{
				case 1:

					mostrarSiProyectosElegibles = true;
					mostrarSiProyectosAprobados = false;
					mostrarSiDocCreacionConvocatoria = false;
					tipoOficioPreDef = "";
					dependenciaConvocatoriaPadre = convocatoriaPadreSeleccionada.getDependencia().getNombre();

					if (convocatoriaPadreSeleccionada.getEsPermanente() != null && "Y".equals(convocatoriaPadreSeleccionada.getEsPermanente()))
					{
						validarOficiosCortesConvocatoria("OE");						
						mostrarCorteConvocatoria = true;
					}
					else
					{
						mostrarCorteConvocatoria = false;
					}

					cargarArchivosOficios("OE");

					break;

				case 2:

					mostrarSiProyectosElegibles = false;
					mostrarSiProyectosAprobados = true;
					mostrarSiDocCreacionConvocatoria = false;
					tipoDocumentoAprobados = "";
					tipoOficioPreDef = "";
					cargarListaDependencias(convocatoriaPadreSeleccionada.getDependencia());
					dependenciaConvocatoriaPadre = convocatoriaPadreSeleccionada.getDependencia().getNombre();
					
					if (convocatoriaPadreSeleccionada.getEsPermanente() != null && "Y".equals(convocatoriaPadreSeleccionada.getEsPermanente()))
					{
						validarOficiosCortesConvocatoria("OA");						
						mostrarCorteConvocatoria = true;
					}
					else
					{
						mostrarCorteConvocatoria = false;
					}

					cargarArchivosOficios("OA");

					break;
				}
			}
			else
			{
				mensajeError("Por favor verificar la dependendencia del asesor.");
			}
		}
	}

	public void validarOficiosCortesConvocatoria(final String tipoOficio)
	{
		String consultaCortes = "";
		if(tipoOficio.equals("OE")){
			consultaCortes = "select #id e.id, #numero e.numero, #tieneOficioElegibles e.tieneOficioElegibles, #tieneOficioAprobados e.tieneOficioAprobados from CorteConvocatoria e where e.convocatoriaPadre.id = "
					+ convocatoriaPadreSeleccionada.getId() + " and e.sede.id = " + dep.getSede().getId() + " and e.estado = 'A' and (e.tieneOficioElegibles = 'N' or e.tieneOficioElegibles is null)";
		}else{
			consultaCortes = "select #id e.id, #numero e.numero, #tieneOficioElegibles e.tieneOficioElegibles, #tieneOficioAprobados e.tieneOficioAprobados from CorteConvocatoria e where e.convocatoriaPadre.id = "
					+ convocatoriaPadreSeleccionada.getId() + " and e.sede.id = " + dep.getSede().getId() + " and e.estado = 'A' and (e.tieneOficioAprobados = 'N' or e.tieneOficioAprobados is null)";
		}
		
		listaCortesConvocatoria = servicioGeneral.obtenerObjetosLimitado(CorteConvocatoria.class, consultaCortes);
		
		if (!listaCortesConvocatoria.isEmpty())
		{
			setSelItemCortesConvocatoria(new SelectItem[listaCortesConvocatoria.size()]);

			for (int i = 0; i < listaCortesConvocatoria.size(); i++)
			{
				CorteConvocatoria dd = (CorteConvocatoria) listaCortesConvocatoria.get(i);				
				selItemCortesConvocatoria[i] = new SelectItem(dd.getId(), dd.getNumero().toString());
			}
		}else{
			
		}
	}

	
	public void cargarListaDependencias(final Dependencia depConv){
		String sqlDep = "select #id e.id, #facultad e.facultad, #nombre e.nombre from Dependencia e where e.facultad.id = '" + depConv.getFacultad().getId() + "'" ;
		listaDependencias = servicioGeneral.obtenerObjetosLimitado(Dependencia.class, sqlDep);
		setSelItemDependencia(new SelectItem[listaDependencias.size()]);
		for (int i = 0; i < listaDependencias.size(); i++)
		{
			selItemDependencia[i] = new SelectItem(listaDependencias.get(i).getId(), listaDependencias.get(i).getNombre());
		}
	}

	public boolean validarDependenciaAsesor(final Dependencia depConv)
	{
		if (depConv != null)
		{
			
			if(validarSiNacional(depConv.getSede().getId().toString())){
				String depInv;
				String facInv;
				esConvNacional = true;

				if (dep.getId() != null)
				{
					if(dep.getSede().getId().equals(1L)){
						depInv = dep.getSede().getId().toString();
					}else{
						depInv = dep.getId();
					}					
				}
				else
				{
					depInv = "";
				}

				if (dep.getFacultad().getId() != null)
				{
					facInv = dep.getFacultad().getId();
				}
				else
				{
					facInv = "";
				}
				
				if(validarSiSede(depInv) || validarSiNacional(depInv) || validarSiSede(facInv) || validarSiNacional(facInv)){
					esAsesorConvNacional = true;
				}				
			}			

			if (esConvNacional)
			{
				if (esAsesorConvNacional)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
				return true;
			}

		}
		else
		{
			mensajeError("Por favor verificar la dependencia de la convocatoria");
			return false;
		}
	}

	public boolean validarSiNacional(final String idDep)
	{
		boolean ret = false;
		if ("1".equals(idDep))
		{
			ret = true;
		}else{
			ret = false;
		}
		return ret;
	}

	public boolean validarSiSede(final String idDep)
	{
		boolean ret = false;

		switch (Integer.parseInt(idDep))
		{
		case 1:
			ret = false;
			break;

		case 2:
			ret = true;
			break;

		case 3:
			ret = true;
			break;

		case 4:
			ret = true;
			break;

		case 5:
			ret = true;
			break;

		case 6:
			ret = true;
			break;

		case 7:
			ret = true;
			break;

		case 8:
			ret = true;
			break;

		case 9:
			ret = true;
			break;

		default:
			ret = false;
			break;
		}

		return ret;
	}

	public boolean validarSiFacultad(final String idDep)
	{
		boolean ret = false;

		switch (Integer.parseInt(idDep))
		{
		case 1:
			ret = false;
			break;

		case 2:
			ret = false;
			break;

		case 3:
			ret = false;
			break;

		case 4:
			ret = false;
			break;

		case 5:
			ret = false;
			break;

		case 6:
			ret = false;
			break;

		case 7:
			ret = false;
			break;

		case 8:
			ret = false;
			break;

		case 9:
			ret = false;
			break;

		default:
			ret = true;
			break;
		}

		return ret;
	}

	public void cargarArchivosOficios(final String tipoOficio)
	{
		listaArchivos = servicioGeneral.obtenerObjetos(ArchivoConvocatoriaPadre.class, "from ArchivoConvocatoriaPadre a where " + " a.convocatoriaPadre = '"
				+ convocatoriaPadreSeleccionada.getId() + "' " + "and a.tipoArchivo = '" + tipoOficio + "' order by a.id asc ");
	}
	
	public void insertarArchivoElegibles(FileUploadEvent event)
	{
		if("".equals(tipoOficioPreDef)){
			mensajeError(uploadArchivoProyecto , "Por favor seleccione el tipo de oficio a generar y genere el oficio haciendo clic en el enlace 'Descargar oficio lista de proyectos que cumplen requisitos (elegibles)'");
		}else{
			UploadedFile archivo = event.getFile();

			insertarArchivoOficioConvocatoriaPadre(convocatoriaPadreSeleccionada.getId().toString(), archivo, "OE");
			ConvocatoriaPadre convPadEle = servicioModalidad.obtenerConvocatoriaPadre(convocatoriaPadreSeleccionada.getId());

			if (convPadEle.getEsPermanente() != null)
			{
				if ("Y".equals(convPadEle.getEsPermanente()))
				{
					CorteConvocatoria corteConv = servicioModalidad.obtenerCorteConvocatoriaPadre(convocatoriaPadreSeleccionada.getId(), numeroCorteConvocatoria, dep.getSede() ,"A");
					if(tipoOficioPreDef.equals("D")){
						corteConv.setTieneOficioElegibles(new Boolean(true));					
					}
					corteConv.setFechaOficioElegibles(new Date());
					servicioGeneral.guardarObjeto(corteConv);
				}
			}
			else
			{
				if(tipoOficioPreDef.equals("D")){
					convPadEle.setTieneOficioElegibles(new Boolean(true));				
				}
				convPadEle.setFechaOficioElegibles(new Date());
				servicioGeneral.guardarObjeto(convPadEle);
			}

			cargarArchivosConvocatoriaPadre();

			mostrarSiOficio = false;
		}

	}


	public void insertarArchivoAprobados(FileUploadEvent event)
	{
		if("".equals(tipoDocumentoAprobados) || ("1".equals(tipoDocumentoAprobados) && "".equals(tipoOficioPreDef) )){
			mensajeError(uploadArchivoProyectoAprobados , "Por favor seleccione el documento, tipo de oficio a generar (para el caso en el que no sea resolución) y genere el oficio haciendo clic en el enlace 'Descargar oficio o resolución de lista de proyectos financiables (aprobados)'");
		}else{
			UploadedFile archivo = event.getFile();

			insertarArchivoOficioConvocatoriaPadre(convocatoriaPadreSeleccionada.getId().toString(), archivo, "OA");
			ConvocatoriaPadre convPadEle = servicioModalidad.obtenerConvocatoriaPadre(convocatoriaPadreSeleccionada.getId());

			if (convPadEle.getEsPermanente() != null)
			{
				if ("Y".equals(convPadEle.getEsPermanente()))
				{
					CorteConvocatoria corteConv = servicioModalidad.obtenerCorteConvocatoriaPadre(convocatoriaPadreSeleccionada.getId(), numeroCorteConvocatoria, dep.getSede() ,"A");
					if(tipoDocumentoAprobados.equals("1")){
						if(tipoOficioPreDef.equals("D")){
							corteConv.setTieneOficioAprobados(new Boolean(true));
						}	
					}else{
						corteConv.setTieneOficioAprobados(new Boolean(true));
					}							
					corteConv.setFechaOficioAprobados(new Date());
					servicioGeneral.guardarObjeto(corteConv);
				}
			}
			else
			{
				if(tipoDocumentoAprobados.equals("1")){
					if(tipoOficioPreDef.equals("D")){
						convPadEle.setTieneOficioAprobados(new Boolean(true));
					}
				}else{
					convPadEle.setTieneOficioAprobados(new Boolean(true));
				}						
				convPadEle.setFechaOficioAprobados(new Date());
				servicioGeneral.guardarObjeto(convPadEle);
			}

			cargarArchivosConvocatoriaPadreAprobados();

			mostrarSiOficioAprobados = false;			
		}	
	}

	public void cargarArchivosConvocatoriaPadre()
	{
		listaArchivos = servicioGeneral.obtenerObjetos(ArchivoConvocatoriaPadre.class, "from ArchivoConvocatoriaPadre a where " + " a.convocatoriaPadre = '"
				+ convocatoriaPadreSeleccionada.getId() + "' " + "and a.tipoArchivo = 'OE' order by a.id asc ");
	}

	public void cargarArchivosConvocatoriaPadreAprobados()
	{
		listaArchivos = servicioGeneral.obtenerObjetos(ArchivoConvocatoriaPadre.class, "from ArchivoConvocatoriaPadre a where " + " a.convocatoriaPadre = '"
				+ convocatoriaPadreSeleccionada.getId() + "' " + "and a.tipoArchivo = 'OA' order by a.id asc ");
	}

	public void consultarDocumentoProceso()
	{
		ArchivoConvocatoriaPadre archivo = archivoProcesoSeleccionado;
		String path = "HER_ARCHIVO_CONVOCATORIA_PADRE";
		descargarArchivoGenerico(path, archivo.getId().toString(), archivo.getNombre());
	}

	public void mostrarListaAprobados()
	{

		mostrarFormularioAprobados = false;

		if (convocatoriaPadreSeleccionada.getEsPermanente() != null)
		{
			if ("Y".equals(convocatoriaPadreSeleccionada.getEsPermanente()))
			{
				Long corteO = 0L;
				if (!corteO.equals(numeroCorteConvocatoria))
				{
					mostrarListaAprobados = true;
					listaAprobados = servicioProyecto.obtenerProyectosXEstadoYConvocatoriaPadreYCorte(convocatoriaPadreSeleccionada.getId(), "AP",
							numeroCorteConvocatoria);
				}
				else
				{
					mostrarListaAprobados = false;
					mensajeError(MENSAJE_SELECCION_CORTE_CONVOCATORIA);
				}
			}
			else
			{
				mostrarListaAprobados = true;
				listaAprobados = servicioProyecto.obtenerProyectosXEstadoYConvocatoriaPadre(convocatoriaPadreSeleccionada.getId(), "AP");
			}
		}
		else
		{
			mostrarListaAprobados = true;
			listaAprobados = servicioProyecto.obtenerProyectosXEstadoYConvocatoriaPadre(convocatoriaPadreSeleccionada.getId(), "AP");
		}

	}

	public void mostrarListaElegibles()
	{

		mostrarFormularioElegibles = false;

		if (convocatoriaPadreSeleccionada.getEsPermanente() != null)
		{
			if ("Y".equals(convocatoriaPadreSeleccionada.getEsPermanente()))
			{
				Long corteO = 0L;
				if (!corteO.equals(numeroCorteConvocatoria))
				{
					mostrarListaElegibles = true;
					listaElegible = servicioProyecto.obtenerProyectosXEstadoYConvocatoriaPadreYCorte(convocatoriaPadreSeleccionada.getId(), "E",
							numeroCorteConvocatoria);
				}
				else
				{
					mostrarListaElegibles = false;
					mensajeError(MENSAJE_SELECCION_CORTE_CONVOCATORIA);
				}
			}
			else
			{
				mostrarListaElegibles = true;
				listaElegible = servicioProyecto.obtenerProyectosXEstadoYConvocatoriaPadre(convocatoriaPadreSeleccionada.getId(), "E");
			}
		}
		else
		{
			mostrarListaElegibles = true;
			listaElegible = servicioProyecto.obtenerProyectosXEstadoYConvocatoriaPadre(convocatoriaPadreSeleccionada.getId(), "E");
		}

	}

	public void cambioSiFirma()
	{
		if ("S".equals(mostrarFirmaAprobados))
		{
			mostrarSiFirma = true;
		}
		else
		{
			mostrarSiFirma = false;
		}
	}
	
	public void cambioSiResolucion(){
		if("2".equals(tipoDocumentoAprobados)){
			mostrarSiResolucion = true;	
		}else{
			mostrarSiResolucion = false;
		}		
	}	

	public void mostrarGenerarOficioElegibles()
	{
		mostrarListaElegibles = false;

		if (convocatoriaPadreSeleccionada.getEsPermanente() != null)
		{
			if ("Y".equals(convocatoriaPadreSeleccionada.getEsPermanente()))
			{
				Long corteO = 0L;
				if (!corteO.equals(numeroCorteConvocatoria))
				{
					mostrarFormularioElegibles = true;
				}
				else
				{
					mostrarFormularioElegibles = false;
					mensajeError(MENSAJE_SELECCION_CORTE_CONVOCATORIA);
				}
			}
			else
			{
				mostrarFormularioElegibles = true;
			}
		}
		else
		{
			mostrarFormularioElegibles = true;
		}

	}

	public void mostrarGenerarOficioAprobados()
	{
		mostrarListaAprobados = false;
		mostrarVigenciaAprobados = "N";
		mostrarFirmaAprobados = "N";

		if (convocatoriaPadreSeleccionada.getEsPermanente() != null)
		{
			if ("Y".equals(convocatoriaPadreSeleccionada.getEsPermanente()))
			{
				Long corteO = 0L;
				if (!corteO.equals(numeroCorteConvocatoria))
				{
					mostrarFormularioAprobados = true;
				}
				else
				{
					mostrarFormularioAprobados = false;
					mensajeError(MENSAJE_SELECCION_CORTE_CONVOCATORIA);
				}
			}
			else
			{
				mostrarFormularioAprobados = true;
			}
		}
		else
		{
			mostrarFormularioAprobados = true;
		}

	}
	
	public void cambioSiVigencia(){
		if("S".equals(mostrarVigenciaAprobados)){
			mostrarSiVigencia = true;
		}else{
			mostrarSiVigencia = false;
		}
	}

	public void generarOficioElegibles()
	{
		if("".equals(tipoOficioPreDef)){
			mensajeError("Por favor seleccione si el oficio es preliminar o definitivo y haga clic en 'Descargar oficio lista de proyectos que cumplen requisitos (elegibles)'.");
		}else{
			ConvocatoriaPadre convPadEle = servicioModalidad.obtenerConvocatoriaPadre(convocatoriaPadreSeleccionada.getId());
			ReporteBirt r = new ReporteBirt();
			r.adicionarParametro("cnp_id", convPadEle.getId().toString());
			if (convPadEle.getEsPermanente() != null)
			{
				if ("Y".equals(convPadEle.getEsPermanente()))
				{
					CorteConvocatoria corteConv = servicioModalidad.obtenerCorteConvocatoriaPadre(convocatoriaPadreSeleccionada.getId(), numeroCorteConvocatoria, dep.getSede() ,"A");
					corteConv.setTextoOficioElegibles(textoAdicionalOficioElegibles);
					corteConv.setIdPersonaOficioElegibles(per.getId().getDocumento());
					corteConv.setTipoIdPersonaOficioElegibles(per.getId().getTipoDocumento());
					corteConv.setFechaOficioElegibles(new Date());
					servicioGeneral.guardarObjeto(corteConv);
					r.adicionarParametro("corte", numeroCorteConvocatoria.toString());
					r.setNombreReporte("/convocatoria/oficioProyectosElegiblesCortes");
				}else{
					convPadEle.setTextoOficioElegibles(textoAdicionalOficioElegibles);
					convPadEle.setIdPersonaOficioElegibles(per.getId().getDocumento());
					convPadEle.setTipoIdPersonaOficioElegibles(per.getId().getTipoDocumento());
					convPadEle.setFechaOficioElegibles(new Date());
					servicioGeneral.guardarObjeto(convPadEle);
					r.setNombreReporte("/convocatoria/oficioProyectosElegibles");
				}
			}
			else
			{
				convPadEle.setTextoOficioElegibles(textoAdicionalOficioElegibles);
				convPadEle.setIdPersonaOficioElegibles(per.getId().getDocumento());
				convPadEle.setTipoIdPersonaOficioElegibles(per.getId().getTipoDocumento());
				convPadEle.setFechaOficioElegibles(new Date());
				servicioGeneral.guardarObjeto(convPadEle);
				r.setNombreReporte("/convocatoria/oficioProyectosElegibles");
			}
			
			if(tipoOficioPreDef.equals("P")){
				r.adicionarParametro("tipoOficio", "P");
			}else{
				r.adicionarParametro("tipoOficio", "D");
			}
			r.setFormato(ReporteBirt.FORMATO_PDF);
			sesion.setAttribute("reporte", r);
			FacesContext context = FacesContext.getCurrentInstance();
			try
			{
				context.getExternalContext().dispatch("/ReporteEngineServlet");
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
			finally
			{
				context.responseComplete();
			}

		}

	}

	public void generarOficioAprobados()
	{
		if("".equals(tipoOficioPreDef) && "1".equals(tipoDocumentoAprobados)){
			mensajeError("Por favor seleccione si el oficio es preliminar o definitivo.");
		}else{
			ConvocatoriaPadre convPadEle = servicioModalidad.obtenerConvocatoriaPadre(convocatoriaPadreSeleccionada.getId());
			ReporteBirt r = new ReporteBirt();
			r.adicionarParametro("cnp_id", convPadEle.getId().toString());

			if (convPadEle.getEsPermanente() != null)
			{
				if ("Y".equals(convPadEle.getEsPermanente()))
				{
					CorteConvocatoria corteConv = servicioModalidad.obtenerCorteConvocatoriaPadre(convocatoriaPadreSeleccionada.getId(), numeroCorteConvocatoria, dep.getSede() ,"A");
					corteConv.setNumeroOficioAprobados(consecutivoOficioAprobados);
					corteConv.setTextoOficioAprobados(textoAdicionalOficioAprobados);
					if ("S".equals(mostrarVigenciaAprobados))
					{
						corteConv.setMostrarVigenciaAprobados(new Boolean(true));
						corteConv.setVigenciaAprobados(vigenciaSeleccionada);
					}
					else
					{
						corteConv.setMostrarVigenciaAprobados(new Boolean(false));
					}

					if ("S".equals(mostrarFirmaAprobados))
					{
						corteConv.setMostrarFirmaAprobados(new Boolean(true));
					}
					else
					{
						corteConv.setMostrarFirmaAprobados(new Boolean(false));
					}

					corteConv.setResponsableOficioAprobados(responsableOficioAprobados);
					corteConv.setCargoResponsableOficioAprobados(cargoResponsableOficioAprobados);
					corteConv.setFechaGeneracionOficioAprobados(fechaResolucion);
					Dependencia depRes = servicioDependencia.obtenerDependencia(dependenciaResolucion);
					corteConv.setDependenciaGeneradoraOficioAprobados(depRes);
					corteConv.setIdPersonaOficioAprobados(per.getId().getDocumento());
					corteConv.setTipoIdPersonaOficioAprobados(per.getId().getTipoDocumento());
					corteConv.setFechaOficioAprobados(new Date());
					servicioGeneral.guardarObjeto(corteConv);
					r.adicionarParametro("corte", numeroCorteConvocatoria.toString());
						
					if ("1".equals(tipoDocumentoAprobados))
					{
						if(tipoOficioPreDef.equals("P")){
							r.adicionarParametro("tipoOficio", "P");
						}else{
							r.adicionarParametro("tipoOficio", "D");
						}
						r.setNombreReporte("/convocatoria/oficioProyectosAprobadosCortes");						
					}
					else
					{
						r.setNombreReporte("/convocatoria/resolucionProyectosAprobadosCortes");
					}

				}else{

					convPadEle.setNumeroOficioAprobados(consecutivoOficioAprobados);
					convPadEle.setTextoOficioAprobados(textoAdicionalOficioAprobados);
					if ("S".equals(mostrarVigenciaAprobados))
					{
						convPadEle.setMostrarVigenciaAprobados(new Boolean(true));
						convPadEle.setVigenciaAprobados(vigenciaSeleccionada);
					}
					else
					{
						convPadEle.setMostrarVigenciaAprobados(new Boolean(false));
					}

					if ("S".equals(mostrarFirmaAprobados))
					{
						convPadEle.setMostrarFirmaAprobados(new Boolean(true));
					}
					else
					{
						convPadEle.setMostrarFirmaAprobados(new Boolean(false));
					}

					convPadEle.setResponsableOficioAprobados(responsableOficioAprobados);
					convPadEle.setCargoResponsableOficioAprobados(cargoResponsableOficioAprobados);
					convPadEle.setFechaGeneracionOficioAprobados(fechaResolucion);
					Dependencia depRes = servicioDependencia.obtenerDependencia(dependenciaResolucion);
					convPadEle.setDependenciaGeneradoraOficioAprobados(depRes);
					convPadEle.setIdPersonaOficioAprobados(per.getId().getDocumento());
					convPadEle.setTipoIdPersonaOficioAprobados(per.getId().getTipoDocumento());
					convPadEle.setFechaOficioAprobados(new Date());
					servicioGeneral.guardarObjeto(convPadEle);				

					if ("1".equals(tipoDocumentoAprobados))
					{
						if(tipoOficioPreDef.equals("P")){
							r.adicionarParametro("tipoOficio", "P");
						}else{
							r.adicionarParametro("tipoOficio", "D");
						}
						
						r.setNombreReporte("/convocatoria/oficioProyectosAprobados");
					}
					else
					{
						r.setNombreReporte("/convocatoria/resolucionProyectosAprobados");
					}
				
				}
			}
			else
			{
				convPadEle.setNumeroOficioAprobados(consecutivoOficioAprobados);
				convPadEle.setTextoOficioAprobados(textoAdicionalOficioAprobados);
				if ("S".equals(mostrarVigenciaAprobados))
				{
					convPadEle.setMostrarVigenciaAprobados(new Boolean(true));
					convPadEle.setVigenciaAprobados(vigenciaSeleccionada);
				}
				else
				{
					convPadEle.setMostrarVigenciaAprobados(new Boolean(false));
				}

				if ("S".equals(mostrarFirmaAprobados))
				{
					convPadEle.setMostrarFirmaAprobados(new Boolean(true));
				}
				else
				{
					convPadEle.setMostrarFirmaAprobados(new Boolean(false));
				}

				convPadEle.setResponsableOficioAprobados(responsableOficioAprobados);
				convPadEle.setCargoResponsableOficioAprobados(cargoResponsableOficioAprobados);
				convPadEle.setFechaGeneracionOficioAprobados(fechaResolucion);
				Dependencia depRes = servicioDependencia.obtenerDependencia(dependenciaResolucion);
				convPadEle.setDependenciaGeneradoraOficioAprobados(depRes);
				convPadEle.setIdPersonaOficioAprobados(per.getId().getDocumento());
				convPadEle.setTipoIdPersonaOficioAprobados(per.getId().getTipoDocumento());
				convPadEle.setFechaOficioAprobados(new Date());
				servicioGeneral.guardarObjeto(convPadEle);		

				if ("1".equals(tipoDocumentoAprobados))
				{
					if(tipoOficioPreDef.equals("P")){
						r.adicionarParametro("tipoOficio", "P");
					}else{
						r.adicionarParametro("tipoOficio", "D");
					}
					
					r.setNombreReporte("/convocatoria/oficioProyectosAprobados");
				}
				else
				{
					r.setNombreReporte("/convocatoria/resolucionProyectosAprobados");
				}
			}

			r.setFormato(ReporteBirt.FORMATO_PDF);
			sesion.setAttribute("reporte", r);
			FacesContext context = FacesContext.getCurrentInstance();
			try
			{
				context.getExternalContext().dispatch("/ReporteEngineServlet");
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
			finally
			{
				context.responseComplete();
			}
		}
	}

	public String cargarProyecto()
	{
		if (proyectoSeleccionado != null)
		{
			sesion.setAttribute("idProyectoSeguimientoCoordinador", proyectoSeleccionado);
			return "informacionProyecto";
		}
		return "";
	}

	/**
	 * @return the nombreConvocatoriaBusqueda
	 */
	public String getNombreConvocatoriaBusqueda()
	{
		return nombreConvocatoriaBusqueda;
	}

	/**
	 * @param nombreConvocatoriaBusqueda
	 *            the nombreConvocatoriaBusqueda to set
	 */
	public void setNombreConvocatoriaBusqueda(String nombreConvocatoriaBusqueda)
	{
		this.nombreConvocatoriaBusqueda = nombreConvocatoriaBusqueda;
	}

	/**
	 * @return the listaConvocatoriasPadreBusqueda
	 */
	public List<ConvocatoriaPadre> getListaConvocatoriasPadreBusqueda()
	{
		return listaConvocatoriasPadreBusqueda;
	}

	/**
	 * @param listaConvocatoriasPadreBusqueda
	 *            the listaConvocatoriasPadreBusqueda to set
	 */
	public void setListaConvocatoriasPadreBusqueda(List<ConvocatoriaPadre> listaConvocatoriasPadreBusqueda)
	{
		this.listaConvocatoriasPadreBusqueda = listaConvocatoriasPadreBusqueda;
	}

	/**
	 * @return the convocatoriaPadreItem
	 */
	public SelectItem[] getConvocatoriaPadreItem()
	{
		return convocatoriaPadreItem;
	}

	/**
	 * @param convocatoriaPadreItem
	 *            the convocatoriaPadreItem to set
	 */
	public void setConvocatoriaPadreItem(SelectItem[] convocatoriaPadreItem)
	{
		this.convocatoriaPadreItem = convocatoriaPadreItem;
	}

	/**
	 * @return the idConvocaPadre
	 */
	public Long getIdConvocaPadre()
	{
		return idConvocaPadre;
	}

	/**
	 * @param idConvocaPadre
	 *            the idConvocaPadre to set
	 */
	public void setIdConvocaPadre(Long idConvocaPadre)
	{
		this.idConvocaPadre = idConvocaPadre;
	}

	/**
	 * @return the mostrarSiConvPadreExiste
	 */
	public boolean isMostrarSiConvPadreExiste()
	{
		return mostrarSiConvPadreExiste;
	}

	/**
	 * @param mostrarSiConvPadreExiste
	 *            the mostrarSiConvPadreExiste to set
	 */
	public void setMostrarSiConvPadreExiste(boolean mostrarSiConvPadreExiste)
	{
		this.mostrarSiConvPadreExiste = mostrarSiConvPadreExiste;
	}

	/**
	 * @return the dep
	 */
	public Dependencia getDep()
	{
		return dep;
	}

	/**
	 * @param dep
	 *            the dep to set
	 */
	public void setDep(Dependencia dep)
	{
		this.dep = dep;
	}

	/**
	 * @return the tipoDocumento
	 */
	public String getTipoDocumento()
	{
		return tipoDocumento;
	}

	/**
	 * @param tipoDocumento
	 *            the tipoDocumento to set
	 */
	public void setTipoDocumento(String tipoDocumento)
	{
		this.tipoDocumento = tipoDocumento;
	}

	/**
	 * @return the mostrarSiProyectosElegibles
	 */
	public boolean isMostrarSiProyectosElegibles()
	{
		return mostrarSiProyectosElegibles;
	}

	/**
	 * @param mostrarSiProyectosElegibles
	 *            the mostrarSiProyectosElegibles to set
	 */
	public void setMostrarSiProyectosElegibles(boolean mostrarSiProyectosElegibles)
	{
		this.mostrarSiProyectosElegibles = mostrarSiProyectosElegibles;
	}

	/**
	 * @return the mostrarSiProyectosAprobados
	 */
	public boolean isMostrarSiProyectosAprobados()
	{
		return mostrarSiProyectosAprobados;
	}

	/**
	 * @param mostrarSiProyectosAprobados
	 *            the mostrarSiProyectosAprobados to set
	 */
	public void setMostrarSiProyectosAprobados(boolean mostrarSiProyectosAprobados)
	{
		this.mostrarSiProyectosAprobados = mostrarSiProyectosAprobados;
	}

	/**
	 * @return the mostrarSiDocCreacionConvocatoria
	 */
	public boolean isMostrarSiDocCreacionConvocatoria()
	{
		return mostrarSiDocCreacionConvocatoria;
	}

	/**
	 * @param mostrarSiDocCreacionConvocatoria
	 *            the mostrarSiDocCreacionConvocatoria to set
	 */
	public void setMostrarSiDocCreacionConvocatoria(boolean mostrarSiDocCreacionConvocatoria)
	{
		this.mostrarSiDocCreacionConvocatoria = mostrarSiDocCreacionConvocatoria;
	}

	/**
	 * @return the listaCortesConvocatoria
	 */
	public List<CorteConvocatoria> getListaCortesConvocatoria()
	{
		return listaCortesConvocatoria;
	}

	/**
	 * @param listaCortesConvocatoria
	 *            the listaCortesConvocatoria to set
	 */
	public void setListaCortesConvocatoria(List<CorteConvocatoria> listaCortesConvocatoria)
	{
		this.listaCortesConvocatoria = listaCortesConvocatoria;
	}

	/**
	 * @return the listaConvocatoriasPadre
	 */
	public List<ConvocatoriaPadre> getListaConvocatoriasPadre()
	{
		return listaConvocatoriasPadre;
	}

	/**
	 * @param listaConvocatoriasPadre
	 *            the listaConvocatoriasPadre to set
	 */
	public void setListaConvocatoriasPadre(List<ConvocatoriaPadre> listaConvocatoriasPadre)
	{
		this.listaConvocatoriasPadre = listaConvocatoriasPadre;
	}

	/**
	 * @return the convocatoriaPadreSeleccionada
	 */
	public ConvocatoriaPadre getConvocatoriaPadreSeleccionada()
	{
		return convocatoriaPadreSeleccionada;
	}

	/**
	 * @param convocatoriaPadreSeleccionada
	 *            the convocatoriaPadreSeleccionada to set
	 */
	public void setConvocatoriaPadreSeleccionada(ConvocatoriaPadre convocatoriaPadreSeleccionada)
	{
		this.convocatoriaPadreSeleccionada = convocatoriaPadreSeleccionada;
	}

	/**
	 * @return the mostrarCorteConvocatoria
	 */
	public boolean isMostrarCorteConvocatoria()
	{
		return mostrarCorteConvocatoria;
	}

	/**
	 * @param mostrarCorteConvocatoria
	 *            the mostrarCorteConvocatoria to set
	 */
	public void setMostrarCorteConvocatoria(boolean mostrarCorteConvocatoria)
	{
		this.mostrarCorteConvocatoria = mostrarCorteConvocatoria;
	}

	/**
	 * @return the selItemCortesConvocatoria
	 */
	public SelectItem[] getSelItemCortesConvocatoria()
	{
		return selItemCortesConvocatoria;
	}

	/**
	 * @param selItemCortesConvocatoria
	 *            the selItemCortesConvocatoria to set
	 */
	public void setSelItemCortesConvocatoria(SelectItem[] selItemCortesConvocatoria)
	{
		this.selItemCortesConvocatoria = selItemCortesConvocatoria;
	}

	/**
	 * @return the numeroCorteConvocatoria
	 */
	public Long getNumeroCorteConvocatoria()
	{
		return numeroCorteConvocatoria;
	}

	/**
	 * @param numeroCorteConvocatoria
	 *            the numeroCorteConvocatoria to set
	 */
	public void setNumeroCorteConvocatoria(Long numeroCorteConvocatoria)
	{
		this.numeroCorteConvocatoria = numeroCorteConvocatoria;
	}

	/**
	 * @return the dependenciaConvocatoriaPadre
	 */
	public String getDependenciaConvocatoriaPadre()
	{
		return dependenciaConvocatoriaPadre;
	}

	/**
	 * @param dependenciaConvocatoriaPadre
	 *            the dependenciaConvocatoriaPadre to set
	 */
	public void setDependenciaConvocatoriaPadre(String dependenciaConvocatoriaPadre)
	{
		this.dependenciaConvocatoriaPadre = dependenciaConvocatoriaPadre;
	}

	/**
	 * @return the textoAdicionalOficioElegibles
	 */
	public String getTextoAdicionalOficioElegibles()
	{
		return textoAdicionalOficioElegibles;
	}

	/**
	 * @param textoAdicionalOficioElegibles
	 *            the textoAdicionalOficioElegibles to set
	 */
	public void setTextoAdicionalOficioElegibles(String textoAdicionalOficioElegibles)
	{
		this.textoAdicionalOficioElegibles = textoAdicionalOficioElegibles;
	}

	/**
	 * @return the per
	 */
	public Persona getPer()
	{
		return per;
	}

	/**
	 * @param per
	 *            the per to set
	 */
	public void setPer(Persona per)
	{
		this.per = per;
	}

	/**
	 * @return the mostrarFormularioElegibles
	 */
	public boolean isMostrarFormularioElegibles()
	{
		return mostrarFormularioElegibles;
	}

	/**
	 * @param mostrarFormularioElegibles
	 *            the mostrarFormularioElegibles to set
	 */
	public void setMostrarFormularioElegibles(boolean mostrarFormularioElegibles)
	{
		this.mostrarFormularioElegibles = mostrarFormularioElegibles;
	}

	/**
	 * @return the mostrarListaElegibles
	 */
	public boolean isMostrarListaElegibles()
	{
		return mostrarListaElegibles;
	}

	/**
	 * @param mostrarListaElegibles
	 *            the mostrarListaElegibles to set
	 */
	public void setMostrarListaElegibles(boolean mostrarListaElegibles)
	{
		this.mostrarListaElegibles = mostrarListaElegibles;
	}

	/**
	 * @return the listaElegible
	 */
	public List<ProyectosVistaOficiosConvocatoria> getListaElegible()
	{
		return listaElegible;
	}

	/**
	 * @param listaElegible
	 *            the listaElegible to set
	 */
	public void setListaElegible(List<ProyectosVistaOficiosConvocatoria> listaElegible)
	{
		this.listaElegible = listaElegible;
	}

	/**
	 * @return the listaArchivos
	 */
	public List<ArchivoConvocatoriaPadre> getListaArchivos()
	{
		return listaArchivos;
	}

	/**
	 * @param listaArchivos
	 *            the listaArchivos to set
	 */
	public void setListaArchivos(List<ArchivoConvocatoriaPadre> listaArchivos)
	{
		this.listaArchivos = listaArchivos;
	}

	/**
	 * @return the archivoProcesoSeleccionado
	 */
	public ArchivoConvocatoriaPadre getArchivoProcesoSeleccionado()
	{
		return archivoProcesoSeleccionado;
	}

	/**
	 * @param archivoProcesoSeleccionado
	 *            the archivoProcesoSeleccionado to set
	 */
	public void setArchivoProcesoSeleccionado(ArchivoConvocatoriaPadre archivoProcesoSeleccionado)
	{
		this.archivoProcesoSeleccionado = archivoProcesoSeleccionado;
	}

	/**
	 * @return the mostrarSiOficio
	 */
	public boolean isMostrarSiOficio()
	{
		return mostrarSiOficio;
	}

	/**
	 * @param mostrarSiOficio
	 *            the mostrarSiOficio to set
	 */
	public void setMostrarSiOficio(boolean mostrarSiOficio)
	{
		this.mostrarSiOficio = mostrarSiOficio;
	}

	/**
	 * @return the listaAprobados
	 */
	public List<ProyectosVistaOficiosConvocatoria> getListaAprobados()
	{
		return listaAprobados;
	}

	/**
	 * @param listaAprobados
	 *            the listaAprobados to set
	 */
	public void setListaAprobados(List<ProyectosVistaOficiosConvocatoria> listaAprobados)
	{
		this.listaAprobados = listaAprobados;
	}

	/**
	 * @return the mostrarFormularioAprobados
	 */
	public boolean isMostrarFormularioAprobados()
	{
		return mostrarFormularioAprobados;
	}

	/**
	 * @param mostrarFormularioAprobados
	 *            the mostrarFormularioAprobados to set
	 */
	public void setMostrarFormularioAprobados(boolean mostrarFormularioAprobados)
	{
		this.mostrarFormularioAprobados = mostrarFormularioAprobados;
	}

	/**
	 * @return the mostrarListaAprobados
	 */
	public boolean isMostrarListaAprobados()
	{
		return mostrarListaAprobados;
	}

	/**
	 * @param mostrarListaAprobados
	 *            the mostrarListaAprobados to set
	 */
	public void setMostrarListaAprobados(boolean mostrarListaAprobados)
	{
		this.mostrarListaAprobados = mostrarListaAprobados;
	}

	/**
	 * @return the mostrarSiOficioAprobados
	 */
	public boolean isMostrarSiOficioAprobados()
	{
		return mostrarSiOficioAprobados;
	}

	/**
	 * @param mostrarSiOficioAprobados
	 *            the mostrarSiOficioAprobados to set
	 */
	public void setMostrarSiOficioAprobados(boolean mostrarSiOficioAprobados)
	{
		this.mostrarSiOficioAprobados = mostrarSiOficioAprobados;
	}

	/**
	 * @return the textoAdicionalOficioAprobados
	 */
	public String getTextoAdicionalOficioAprobados()
	{
		return textoAdicionalOficioAprobados;
	}

	/**
	 * @param textoAdicionalOficioAprobados
	 *            the textoAdicionalOficioAprobados to set
	 */
	public void setTextoAdicionalOficioAprobados(String textoAdicionalOficioAprobados)
	{
		this.textoAdicionalOficioAprobados = textoAdicionalOficioAprobados;
	}

	/**
	 * @return the responsableOficioAprobados
	 */
	public String getResponsableOficioAprobados()
	{
		return responsableOficioAprobados;
	}

	/**
	 * @param responsableOficioAprobados
	 *            the responsableOficioAprobados to set
	 */
	public void setResponsableOficioAprobados(String responsableOficioAprobados)
	{
		this.responsableOficioAprobados = responsableOficioAprobados;
	}

	/**
	 * @return the cargoResponsableOficioAprobados
	 */
	public String getCargoResponsableOficioAprobados()
	{
		return cargoResponsableOficioAprobados;
	}

	/**
	 * @param cargoResponsableOficioAprobados
	 *            the cargoResponsableOficioAprobados to set
	 */
	public void setCargoResponsableOficioAprobados(String cargoResponsableOficioAprobados)
	{
		this.cargoResponsableOficioAprobados = cargoResponsableOficioAprobados;
	}

	/**
	 * @return the consecutivoOficioAprobados
	 */
	public String getConsecutivoOficioAprobados()
	{
		return consecutivoOficioAprobados;
	}

	/**
	 * @param consecutivoOficioAprobados
	 *            the consecutivoOficioAprobados to set
	 */
	public void setConsecutivoOficioAprobados(String consecutivoOficioAprobados)
	{
		this.consecutivoOficioAprobados = consecutivoOficioAprobados;
	}

	/**
	 * @return the mostrarSiFirma
	 */
	public boolean isMostrarSiFirma()
	{
		return mostrarSiFirma;
	}

	/**
	 * @param mostrarSiFirma
	 *            the mostrarSiFirma to set
	 */
	public void setMostrarSiFirma(boolean mostrarSiFirma)
	{
		this.mostrarSiFirma = mostrarSiFirma;
	}

	/**
	 * @return the mostrarVigenciaAprobados
	 */
	public String getMostrarVigenciaAprobados()
	{
		return mostrarVigenciaAprobados;
	}

	/**
	 * @param mostrarVigenciaAprobados
	 *            the mostrarVigenciaAprobados to set
	 */
	public void setMostrarVigenciaAprobados(String mostrarVigenciaAprobados)
	{
		this.mostrarVigenciaAprobados = mostrarVigenciaAprobados;
	}

	/**
	 * @return the mostrarFirmaAprobados
	 */
	public String getMostrarFirmaAprobados()
	{
		return mostrarFirmaAprobados;
	}

	/**
	 * @param mostrarFirmaAprobados
	 *            the mostrarFirmaAprobados to set
	 */
	public void setMostrarFirmaAprobados(String mostrarFirmaAprobados)
	{
		this.mostrarFirmaAprobados = mostrarFirmaAprobados;
	}

	/**
	 * @return the tipoDocumentoAprobados
	 */
	public String getTipoDocumentoAprobados()
	{
		return tipoDocumentoAprobados;
	}

	/**
	 * @param tipoDocumentoAprobados
	 *            the tipoDocumentoAprobados to set
	 */
	public void setTipoDocumentoAprobados(String tipoDocumentoAprobados)
	{
		this.tipoDocumentoAprobados = tipoDocumentoAprobados;
	}

	/**
	 * @return the proyectoSeleccionado
	 */
	public Long getProyectoSeleccionado()
	{
		return proyectoSeleccionado;
	}

	/**
	 * @param proyectoSeleccionado
	 *            the proyectoSeleccionado to set
	 */
	public void setProyectoSeleccionado(Long proyectoSeleccionado)
	{
		this.proyectoSeleccionado = proyectoSeleccionado;
	}

	/**
	 * @return the vigenciaSeleccionada
	 */
	public Long getVigenciaSeleccionada()
	{
		return vigenciaSeleccionada;
	}

	/**
	 * @param vigenciaSeleccionada the vigenciaSeleccionada to set
	 */
	public void setVigenciaSeleccionada(Long vigenciaSeleccionada)
	{
		this.vigenciaSeleccionada = vigenciaSeleccionada;
	}

	/**
	 * @return the mostrarSiVigencia
	 */
	public boolean isMostrarSiVigencia()
	{
		return mostrarSiVigencia;
	}

	/**
	 * @param mostrarSiVigencia the mostrarSiVigencia to set
	 */
	public void setMostrarSiVigencia(boolean mostrarSiVigencia)
	{
		this.mostrarSiVigencia = mostrarSiVigencia;
	}

	/**
	 * @return the listaDependencias
	 */
	public List<Dependencia> getListaDependencias()
	{
		return listaDependencias;
	}

	/**
	 * @param listaDependencias the listaDependencias to set
	 */
	public void setListaDependencias(List<Dependencia> listaDependencias)
	{
		this.listaDependencias = listaDependencias;
	}

	/**
	 * @return the selItemDependencia
	 */
	public SelectItem[] getSelItemDependencia()
	{
		return selItemDependencia;
	}

	/**
	 * @param selItemDependencia the selItemDependencia to set
	 */
	public void setSelItemDependencia(SelectItem[] selItemDependencia)
	{
		this.selItemDependencia = selItemDependencia;
	}

	/**
	 * @return the fechaResolucion
	 */
	public Date getFechaResolucion()
	{
		return fechaResolucion;
	}

	/**
	 * @param fechaResolucion the fechaResolucion to set
	 */
	public void setFechaResolucion(Date fechaResolucion)
	{
		this.fechaResolucion = fechaResolucion;
	}

	/**
	 * @return the dependenciaResolucion
	 */
	public String getDependenciaResolucion()
	{
		return dependenciaResolucion;
	}

	/**
	 * @param dependenciaResolucion the dependenciaResolucion to set
	 */
	public void setDependenciaResolucion(String dependenciaResolucion)
	{
		this.dependenciaResolucion = dependenciaResolucion;
	}

	/**
	 * @return the mostrarSiResolucion
	 */
	public boolean isMostrarSiResolucion()
	{
		return mostrarSiResolucion;
	}

	/**
	 * @param mostrarSiResolucion the mostrarSiResolucion to set
	 */
	public void setMostrarSiResolucion(boolean mostrarSiResolucion)
	{
		this.mostrarSiResolucion = mostrarSiResolucion;
	}

	public String getTipoDocumentoElegibles() {
		return tipoDocumentoElegibles;
	}

	public void setTipoDocumentoElegibles(String tipoDocumentoElegibles) {
		this.tipoDocumentoElegibles = tipoDocumentoElegibles;
	}

	public boolean isEsConvNacional() {
		return esConvNacional;
	}

	public void setEsConvNacional(boolean esConvNacional) {
		this.esConvNacional = esConvNacional;
	}

	public boolean isEsAsesorConvNacional() {
		return esAsesorConvNacional;
	}

	public void setEsAsesorConvNacional(boolean esAsesorConvNacional) {
		this.esAsesorConvNacional = esAsesorConvNacional;
	}

	public String getTipoOficioPreDef() {
		return tipoOficioPreDef;
	}

	public void setTipoOficioPreDef(String tipoOficioPreDef) {
		this.tipoOficioPreDef = tipoOficioPreDef;
	}

	public UIComponent getUploadArchivoProyecto() {
		return uploadArchivoProyecto;
	}

	public void setUploadArchivoProyecto(UIComponent uploadArchivoProyecto) {
		this.uploadArchivoProyecto = uploadArchivoProyecto;
	}

	public UIComponent getUploadArchivoProyectoAprobados() {
		return uploadArchivoProyectoAprobados;
	}

	public void setUploadArchivoProyectoAprobados(UIComponent uploadArchivoProyectoAprobados) {
		this.uploadArchivoProyectoAprobados = uploadArchivoProyectoAprobados;
	}
}
