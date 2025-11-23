package co.edu.unal.hermes.vista;

import java.io.File;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.ConvocatoriaAlianzas;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;

public class ManejadorInscripcionCesu extends ManejadorBase
{

	private InvestigadorInterno ii;
	private boolean mostrarPanel = false;
	private String nombreCompletoPersona = "";
	private UploadedFile file;
	private String nombreArchivoHojaVida = "";
	private ConvocatoriaAlianzas registroCesu;

	public ManejadorInscripcionCesu()
	{
		super();
		personaActual = (Persona) sesion.getAttribute("persona");
		ii = servicioPersona.obtenerInvestigadorInternoCompleto(personaActual.getId());

		if (ii != null)
		{

			if (ii.getInterno().equals("S"))
			{
				List<ConvocatoriaAlianzas> listaConvocatoriaAlianzas = servicioGeneral.obtenerObjetosLimitado(ConvocatoriaAlianzas.class, "select #id e.id from ConvocatoriaAlianzas e where e.investigador.id.documento = '" + ii.getId().getDocumento() + "' and e.investigador.id.tipoDocumento = '" + ii.getId().getTipoDocumento() + "' and e.coaModId = 'CESU'");

				if (listaConvocatoriaAlianzas != null && listaConvocatoriaAlianzas.size() > 0)
				{
					mostrarPanel = false;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El profesor ya se encuentra inscrito", "El profesor ya se encuentra inscrito"));
				}
				else
				{
					mostrarPanel = true;
					nombreCompletoPersona = ii.getNombre1() + " " + ii.getNombre2() + " " + ii.getApellido1() + " " + ii.getApellido2();
				}

			}
			else
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en el tipo de vinculación del profesor", "Error en el tipo de vinculación del profesor"));
			}
		}
		else
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en el tipo de vinculación del profesor", "Error en el tipo de vinculación del profesor"));
		}
	}

	public void guardarCartaProyecto(String idConvo)
	{
		// Cargar archivo a lista de archivos adjuntados
		// file = event.getFile();

		try
		{
			if (file != null)
			{
				new File(RUTA_ARCHIVOS+"HER_CONVOCATORIA_ALIANZAS//" + idConvo).mkdirs();
				// new File("D://HER_CONVOCATORIA_ALIANZAS//" +
				// idConvo).mkdirs();

				if (cargarArchivoDisco(file, "HER_CONVOCATORIA_ALIANZAS//" + idConvo, ii.getId().getDocumento()))
				{
				}
				else
				{
				}
			}
		}
		catch (Exception e)
		{
		}
	}

	public void obtenerArchivo(FileUploadEvent event)
	{
		file = event.getFile();
		int iii = file.getFileName().lastIndexOf("\\");
		nombreArchivoHojaVida = removeCaractEspeciales(file.getFileName().substring(iii + 1));
	}

	public void guardarGeneral()
	{

		if (nombreArchivoHojaVida.equals(""))
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Por favor adjuntar la hoja de vida del profesor", "Por favor adjuntar la hoja de vida del profesor"));
		}
		else
		{

			registroCesu = new ConvocatoriaAlianzas();
			registroCesu.setInvestigador(ii);
			registroCesu.setCoaNombreProyecto("Convocatoria a los representantes de los grupos o centros de investigación de las universidades estatales u oficiales reconocidos por COLCIENCIAS y a los representantes de los profesores en el Consejo Superior Universitario u órgano que haga sus veces, a participar en el proceso de elección de representante ante el CONSEJO NACIONAL DE EDUCACIÓN SUPERIOR-CESU");
			registroCesu.setCoaModId("CESU");
			registroCesu.setCoaPlanFormacion(nombreArchivoHojaVida);
			registroCesu.setCoaEtapa(1);
			registroCesu.setCoaEstado("I");
			Grupo grupo = new Grupo();
			grupo.setId(47L);

			registroCesu.setGrupoResponsable(grupo);
			servicioGeneral.guardarObjeto(registroCesu);

			String directorio = registroCesu.getId().toString();
			guardarCartaProyecto(directorio);

			mostrarPanel = false;

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La inscripción del profesor se ha realizado con el código " + registroCesu.getId() + ".", "La inscripción del profesor se ha realizado con el código " + registroCesu.getId() + "."));
		}

	}

	public String irInicio()
	{
		sesion.removeAttribute("ManejadorInscripcionCesu");
		return "misProyectos";
	}

	public InvestigadorInterno getIi()
	{
		return ii;
	}

	public void setIi(InvestigadorInterno ii)
	{
		this.ii = ii;
	}

	public boolean isMostrarPanel()
	{
		return mostrarPanel;
	}

	public void setMostrarPanel(boolean mostrarPanel)
	{
		this.mostrarPanel = mostrarPanel;
	}

	public String getNombreCompletoPersona()
	{
		return nombreCompletoPersona;
	}

	public void setNombreCompletoPersona(String nombreCompletoPersona)
	{
		this.nombreCompletoPersona = nombreCompletoPersona;
	}

	public UploadedFile getFile()
	{
		return file;
	}

	public void setFile(UploadedFile file)
	{
		this.file = file;
	}

	public String getNombreArchivoHojaVida()
	{
		return nombreArchivoHojaVida;
	}

	public void setNombreArchivoHojaVida(String nombreArchivoHojaVida)
	{
		this.nombreArchivoHojaVida = nombreArchivoHojaVida;
	}

	public ConvocatoriaAlianzas getRegistroCesu()
	{
		return registroCesu;
	}

	public void setRegistroCesu(ConvocatoriaAlianzas registroCesu)
	{
		this.registroCesu = registroCesu;
	}

}
