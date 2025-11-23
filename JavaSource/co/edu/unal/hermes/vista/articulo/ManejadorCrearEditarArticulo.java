package co.edu.unal.hermes.vista.articulo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.ActividadPosdoctorado;
import co.edu.unal.hermes.modelo.ArchivoConvocatoria;
import co.edu.unal.hermes.modelo.ArchivoMovilidadDA;
import co.edu.unal.hermes.modelo.CandidatoPosdoctorado;
import co.edu.unal.hermes.modelo.CoautorArticulo;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaArticulo;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EntidadArticulo;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.Parametro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoInvestigador;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorCrearEditarArticulo extends ManejadorBase {

	private ConvocatoriaArticulo conArt;

	private String errores[];
	private boolean panelRender[];
	private boolean panelRenderError[];
	private boolean valArchivos[];
	private String mensajeArchivos[];

	private String documento;
	private String tipoDocumento;
	// private String programa;
	private SelectItem[] tipoDocumentoItem;
	private SelectItem[] tipoDocumentoSelItem;

	private List gruposInvestigador;
	private List listaPrograma;
	private List listaMeses;
	private List listaArchivos;
	private List numeroApoyos;
	private String nombreModalidad;

	// private List modalidad;
	private List listaArchivosObligatoriosSel;
	private List listaArchivosObligatorios;

	private List listaTipoModalidad;
	private List listaTipoEntidad;
	private List listaTipoCoautor;
	private List listaIdiomas;
	private List listaIdiomas2;

	private List listaEntidades;
	private List listaCoautores;
	private List listaAreas;
	private HtmlDataTable tablaEntidadesSel;
	private String tipoEntidad;
	private String tipoCoautor;
	private String email;

	private String idioma;
	private String entidad;
	private String coautor;
	private String institucionCoautor;
	private String mensajeApoyo;
	private int apoyos;

	private List paises;
	private List paisesEstudio;
	private String valueGuardar;

	private List listaGruposSel;
	private List listaCandidatosSel;
	private List listaActividadesSel;

	private int estado = 0;

	private List listaGrupos;
	private InvestigadorProyecto investigadorProyectoNuevo;

	// VARIABLES TEMPORALES
	private String sede;
	private String facultadDocente;
	private String departamentoDocente;
	private String nombreDocente;
	private String documentoDocente;
	private String descripcionActividad;
	private String nombreActividad;
	private Date fechaActividad;
	private String duracionActividad;
	private String tipoDocumentoSel;
	private UploadedFile archivoObligatorio;
	private String paisProcedencia;
	private String paisEstudio;
	private boolean mostrarFormulario = false;

	CandidatoPosdoctorado candidato;
	ActividadPosdoctorado actividad;

	private Convocatoria convocatoria;

	CorreoPlantilla correoActual = new CorreoPlantilla();
	String cuerpoCorreo = "";

	private String documentoLiderGrupo;
	private String tipoDocumentoLiderGrupo;

	private HtmlDataTable tablaArchivos;
	private HtmlDataTable tablaActividadesSel;

	private HtmlDataTable tablaGruposSel;
	private HtmlDataTable tablaCandidatosSel;
	private HtmlDataTable tablaGrupos;
	private SelectItem[] tipoDocumentoGrupoItem;

	public HtmlDataTable getTablaGrupos() {
		return tablaGrupos;
	}

	public void setTablaGrupos(HtmlDataTable tablaGrupos) {
		this.tablaGrupos = tablaGrupos;
	}

	private boolean banderaModalidad = true;
	private boolean banderaAlianza = false;
	private boolean banderaCandidatos = false;

	private String msgError = "Su solicitud no ha sido enviada, por favor verifique la información que está ingresando.";

	private CoautorArticulo coautorArticuloSeleccionado;

	private ArchivoConvocatoria archivoConvocatoriaSeleccionado;

	private boolean puedeSubirArchivos;

	public ManejadorCrearEditarArticulo() {

		try {

			personaActual = (Persona) sesion.getAttribute("persona");
			convocatoria = (Convocatoria) sesion.getAttribute("convocatoria");

			reiniciarVariables();

			documento = this.getPersonaActual().getId().getDocumento();
			tipoDocumento = this.getPersonaActual().getId().getTipoDocumento();

			investigadorProyectoNuevo = new InvestigadorProyecto();
			TipoInvestigador tc = (TipoInvestigador) servicioGeneral
					.obtenerObjeto(new TipoInvestigador(),
							TipoInvestigador.coinvestigador);
			investigadorProyectoNuevo.setInvestigador(new Investigador());
			TipoDocumento tDocumento = new TipoDocumento();
			tDocumento = (TipoDocumento) servicioGeneral.obtenerObjeto(
					new TipoDocumento(), TipoDocumento.CEDULA);

			investigadorProyectoNuevo.getInvestigador().setId(new IdPersona());

			investigadorProyectoNuevo.setTipo(tc);

			listaEntidades = new ArrayList();
			listaCoautores = new ArrayList();
			listaTipoModalidad = new Vector();
			listaTipoEntidad = new Vector();
			listaTipoCoautor = new Vector();
			listaIdiomas = new Vector();
			listaIdiomas2 = new Vector();
			listaAreas = new Vector();

			listaTipoModalidad.add(new SelectItem("Revisión de estilo",
					"Revisión de estilo"));
			listaTipoModalidad.add(new SelectItem("Traducción", "Traducción"));

			listaTipoEntidad.add(new SelectItem("Interna", "Interna"));
			listaTipoEntidad.add(new SelectItem("Externa", "Externa"));
			listaTipoEntidad.add(new SelectItem("Otra", "Otra"));

			listaTipoCoautor.add(new SelectItem("Docente UN", "Docente UN"));
			listaTipoCoautor.add(new SelectItem("Estudiante UN",
					"Estudiante UN"));
			listaTipoCoautor.add(new SelectItem("Externo", "Externo"));

			listaIdiomas.add(new SelectItem("Inglés - Americano",
					"Inglés - Americano"));
			listaIdiomas.add(new SelectItem("Inglés - Británico",
					"Inglés - Británico"));
			listaIdiomas.add(new SelectItem("Francés", "Francés"));
			listaIdiomas.add(new SelectItem("Alemán", "Alemán"));
			listaIdiomas.add(new SelectItem("Portugués", "Portugués"));

			listaIdiomas2.add(new SelectItem("Inglés - Americano",
					"Inglés - Americano"));
			listaIdiomas2.add(new SelectItem("Inglés - Británico",
					"Inglés - Británico"));

			/*
			 * listaAreas.add(new
			 * SelectItem("ASTRONOMY AND PLANETARY SCIENCE-Astronomy",
			 * "ASTRONOMY AND PLANETARY SCIENCE-Astronomy")); listaAreas.add(new
			 * SelectItem("ASTRONOMY AND PLANETARY SCIENCE-Cosmology",
			 * "ASTRONOMY AND PLANETARY SCIENCE-Cosmology")); listaAreas.add(new
			 * SelectItem(
			 * "ASTRONOMY AND PLANETARY SCIENCE-Other Astronomy and Planetary Science"
			 * ,
			 * "ASTRONOMY AND PLANETARY SCIENCE-Other Astronomy and Planetary Science"
			 * )); listaAreas.add(new
			 * SelectItem("ASTRONOMY AND PLANETARY SCIENCE-Planetary Science",
			 * "ASTRONOMY AND PLANETARY SCIENCE-Planetary Science"));
			 * listaAreas.add(new
			 * SelectItem("ASTRONOMY AND PLANETARY SCIENCE-Theoretical Astrophysics"
			 * , "ASTRONOMY AND PLANETARY SCIENCE-Theoretical Astrophysics"));
			 * 
			 * listaAreas.add(new SelectItem("BIOLOGY - Biochemistry" ,
			 * "BIOLOGY - Biochemistry")); listaAreas.add(new
			 * SelectItem("BIOLOGY - Bioinformatics" ,
			 * "BIOLOGY - Bioinformatics")); listaAreas.add(new
			 * SelectItem("BIOLOGY - Biological Chemistry" ,
			 * "BIOLOGY - Biological Chemistry")); listaAreas.add(new
			 * SelectItem("BIOLOGY - Biophysics" , "BIOLOGY - Biophysics"));
			 * listaAreas.add(new SelectItem("BIOLOGY - Biotechnology" ,
			 * "BIOLOGY - Biotechnology")); listaAreas.add(new
			 * SelectItem("BIOLOGY - Cancer Research" ,
			 * "BIOLOGY - Cancer Research")); listaAreas.add(new
			 * SelectItem("BIOLOGY - Cardiovascular Biology" ,
			 * "BIOLOGY - Cardiovascular Biology")); listaAreas.add(new
			 * SelectItem("BIOLOGY - Cell Biology" , "BIOLOGY - Cell Biology"));
			 * listaAreas.add(new SelectItem("BIOLOGY - Computational Biology" ,
			 * "BIOLOGY - Computational Biology")); listaAreas.add(new
			 * SelectItem("BIOLOGY - Conservation Biology" ,
			 * "BIOLOGY - Conservation Biology")); listaAreas.add(new
			 * SelectItem("BIOLOGY - Developmental Biology" ,
			 * "BIOLOGY - Developmental Biology")); listaAreas.add(new
			 * SelectItem("BIOLOGY - Drug Discovery" ,
			 * "BIOLOGY - Drug Discovery")); listaAreas.add(new
			 * SelectItem("BIOLOGY - Ecology" , "BIOLOGY - Ecology"));
			 * listaAreas.add(new SelectItem("BIOLOGY - Evolution" ,
			 * "BIOLOGY - Evolution")); listaAreas.add(new
			 * SelectItem("BIOLOGY - Genetics" , "BIOLOGY - Genetics"));
			 * listaAreas.add(new SelectItem("BIOLOGY - Genomics" ,
			 * "BIOLOGY - Genomics")); listaAreas.add(new
			 * SelectItem("BIOLOGY - Immunology" , "BIOLOGY - Immunology"));
			 * listaAreas.add(new SelectItem("BIOLOGY - Industrial Microbiology"
			 * , "BIOLOGY - Industrial Microbiology")); listaAreas.add(new
			 * SelectItem("BIOLOGY - Marine Biology" ,
			 * "BIOLOGY - Marine Biology")); listaAreas.add(new
			 * SelectItem("BIOLOGY - Microbiology" , "BIOLOGY - Microbiology"));
			 * listaAreas.add(new SelectItem("BIOLOGY - Molecular Biology" ,
			 * "BIOLOGY - Molecular Biology")); listaAreas.add(new
			 * SelectItem("BIOLOGY - Neuroscience" , "BIOLOGY - Neuroscience"));
			 * listaAreas.add(new SelectItem("BIOLOGY - Other Biology" ,
			 * "BIOLOGY - Other Biology")); listaAreas.add(new
			 * SelectItem("BIOLOGY - Parasitology" , "BIOLOGY - Parasitology"));
			 * listaAreas.add(new SelectItem("BIOLOGY - Pharmacology" ,
			 * "BIOLOGY - Pharmacology")); listaAreas.add(new
			 * SelectItem("BIOLOGY - Plant Science" ,
			 * "BIOLOGY - Plant Science")); listaAreas.add(new
			 * SelectItem("BIOLOGY - Proteomics" , "BIOLOGY - Proteomics"));
			 * listaAreas.add(new SelectItem("BIOLOGY - Structural Biology" ,
			 * "BIOLOGY - Structural Biology")); listaAreas.add(new
			 * SelectItem("BIOLOGY - Systems Biology" ,
			 * "BIOLOGY - Systems Biology")); listaAreas.add(new
			 * SelectItem("BIOLOGY - Zoology" , "BIOLOGY - Zoology"));
			 * 
			 * 
			 * listaAreas.add(new SelectItem("BUSINESS - Accounting" ,
			 * "BUSINESS - Accounting")); listaAreas.add(new
			 * SelectItem("BUSINESS - Finance" , "BUSINESS - Finance"));
			 * listaAreas.add(new SelectItem("BUSINESS - International Business"
			 * , "BUSINESS - International Business")); listaAreas.add(new
			 * SelectItem("BUSINESS - Management" , "BUSINESS - Management"));
			 * listaAreas.add(new SelectItem("BUSINESS - Marketing/PR" ,
			 * "BUSINESS - Marketing/PR")); listaAreas.add(new
			 * SelectItem("BUSINESS - Other Business" ,
			 * "BUSINESS - Other Business")); listaAreas.add(new
			 * SelectItem("BUSINESS - Patents" , "BUSINESS - Patents"));
			 * 
			 * 
			 * listaAreas.add(new SelectItem("CHEMISTRY - Analytical Chemistry"
			 * , "CHEMISTRY - Analytical Chemistry")); listaAreas.add(new
			 * SelectItem("CHEMISTRY - Applied Chemistry" ,
			 * "CHEMISTRY - Applied Chemistry")); listaAreas.add(new
			 * SelectItem("CHEMISTRY - Catalysis" , "CHEMISTRY - Catalysis"));
			 * listaAreas.add(new SelectItem("CHEMISTRY - Chemical Biology" ,
			 * "CHEMISTRY - Chemical Biology")); listaAreas.add(new
			 * SelectItem("CHEMISTRY - Computational Chemistry" ,
			 * "CHEMISTRY - Computational Chemistry")); listaAreas.add(new
			 * SelectItem("CHEMISTRY - Environmental Chemistry" ,
			 * "CHEMISTRY - Environmental Chemistry")); listaAreas.add(new
			 * SelectItem("CHEMISTRY - Inorganic Chemistry" ,
			 * "CHEMISTRY - Inorganic Chemistry")); listaAreas.add(new
			 * SelectItem("CHEMISTRY - Materials Chemistry" ,
			 * "CHEMISTRY - Materials Chemistry")); listaAreas.add(new
			 * SelectItem("CHEMISTRY - Medicinal & Pharmaceutical Chemistry" ,
			 * "CHEMISTRY - Medicinal & Pharmaceutical Chemistry"));
			 * listaAreas.add(new SelectItem("CHEMISTRY - Nuclear Chemistry" ,
			 * "CHEMISTRY - Nuclear Chemistry")); listaAreas.add(new
			 * SelectItem("CHEMISTRY - Organic Chemistry" ,
			 * "CHEMISTRY - Organic Chemistry")); listaAreas.add(new
			 * SelectItem("CHEMISTRY - Other Chemistry" ,
			 * "CHEMISTRY - Other Chemistry")); listaAreas.add(new
			 * SelectItem("CHEMISTRY - Physical Chemistry" ,
			 * "CHEMISTRY - Physical Chemistry")); listaAreas.add(new
			 * SelectItem("CHEMISTRY - Spectroscopy" ,
			 * "CHEMISTRY - Spectroscopy")); listaAreas.add(new
			 * SelectItem("CHEMISTRY - Theoretical/Computational Chemistry" ,
			 * "CHEMISTRY - Theoretical/Computational Chemistry"));
			 * 
			 * 
			 * listaAreas.add(new SelectItem(
			 * "EARTH AND ENVIRONMENTAL SCIENCE - Atmospheric Science/Climate" ,
			 * "EARTH AND ENVIRONMENTAL SCIENCE - Atmospheric Science/Climate"
			 * )); listaAreas.add(new
			 * SelectItem("EARTH AND ENVIRONMENTAL SCIENCE - Geology" ,
			 * "EARTH AND ENVIRONMENTAL SCIENCE - Geology")); listaAreas.add(new
			 * SelectItem("EARTH AND ENVIRONMENTAL SCIENCE - Geophysics" ,
			 * "EARTH AND ENVIRONMENTAL SCIENCE - Geophysics"));
			 * listaAreas.add(new
			 * SelectItem("EARTH AND ENVIRONMENTAL SCIENCE - Geoscience" ,
			 * "EARTH AND ENVIRONMENTAL SCIENCE - Geoscience"));
			 * listaAreas.add(new
			 * SelectItem("EARTH AND ENVIRONMENTAL SCIENCE - Oceanography" ,
			 * "EARTH AND ENVIRONMENTAL SCIENCE - Oceanography"));
			 * listaAreas.add(new SelectItem(
			 * "EARTH AND ENVIRONMENTAL SCIENCE - Other Environmental Science" ,
			 * "EARTH AND ENVIRONMENTAL SCIENCE - Other Environmental Science"
			 * )); listaAreas.add(new
			 * SelectItem("EARTH AND ENVIRONMENTAL SCIENCE - Palaeoclimate" ,
			 * "EARTH AND ENVIRONMENTAL SCIENCE - Palaeoclimate"));
			 * listaAreas.add(new
			 * SelectItem("EARTH AND ENVIRONMENTAL SCIENCE - Palaeontology" ,
			 * "EARTH AND ENVIRONMENTAL SCIENCE - Palaeontology"));
			 * listaAreas.add(new
			 * SelectItem("EARTH AND ENVIRONMENTAL SCIENCE - Planetary Geology"
			 * , "EARTH AND ENVIRONMENTAL SCIENCE - Planetary Geology"));
			 * listaAreas.add(new
			 * SelectItem("EARTH AND ENVIRONMENTAL SCIENCE - Solid Earth Science"
			 * , "EARTH AND ENVIRONMENTAL SCIENCE - Solid Earth Science"));
			 * 
			 * 
			 * listaAreas.add(new
			 * SelectItem("ENGINEERING - Aeronautical Engineering" ,
			 * "ENGINEERING - Aeronautical Engineering")); listaAreas.add(new
			 * SelectItem("ENGINEERING - Biomedical Engineering" ,
			 * "ENGINEERING - Biomedical Engineering")); listaAreas.add(new
			 * SelectItem("ENGINEERING - Chemical Engineering" ,
			 * "ENGINEERING - Chemical Engineering")); listaAreas.add(new
			 * SelectItem("ENGINEERING - Civil Engineering" ,
			 * "ENGINEERING - Civil Engineering")); listaAreas.add(new
			 * SelectItem("ENGINEERING - Electrical/Electronic Engineering" ,
			 * "ENGINEERING - Electrical/Electronic Engineering"));
			 * listaAreas.add(new
			 * SelectItem("ENGINEERING - Environmental Engineering" ,
			 * "ENGINEERING - Environmental Engineering")); listaAreas.add(new
			 * SelectItem("ENGINEERING - Materials Engineering" ,
			 * "ENGINEERING - Materials Engineering")); listaAreas.add(new
			 * SelectItem("ENGINEERING - Mechanical Engineering" ,
			 * "ENGINEERING - Mechanical Engineering")); listaAreas.add(new
			 * SelectItem("ENGINEERING - Nuclear Engineering" ,
			 * "ENGINEERING - Nuclear Engineering")); listaAreas.add(new
			 * SelectItem("ENGINEERING - Other Engineering" ,
			 * "ENGINEERING - Other Engineering")); listaAreas.add(new
			 * SelectItem("ENGINEERING - Software Engineering" ,
			 * "ENGINEERING - Software Engineering")); listaAreas.add(new
			 * SelectItem("ENGINEERING - Systems/Industrial Engineering" ,
			 * "ENGINEERING - Systems/Industrial Engineering"));
			 * 
			 * 
			 * listaAreas.add(new SelectItem("MEDICINE - Allergy" ,
			 * "MEDICINE - Allergy")); listaAreas.add(new
			 * SelectItem("MEDICINE - Anesthesiology" ,
			 * "MEDICINE - Anesthesiology")); listaAreas.add(new
			 * SelectItem("MEDICINE - Cancer/Oncology" ,
			 * "MEDICINE - Cancer/Oncology")); listaAreas.add(new
			 * SelectItem("MEDICINE - Cardiac Electrophysiology" ,
			 * "MEDICINE - Cardiac Electrophysiology")); listaAreas.add(new
			 * SelectItem("MEDICINE - Cardiology - Interventional" ,
			 * "MEDICINE - Cardiology - Interventional")); listaAreas.add(new
			 * SelectItem("MEDICINE - Cardiology - Noninvasive" ,
			 * "MEDICINE - Cardiology - Noninvasive")); listaAreas.add(new
			 * SelectItem("MEDICINE - Cardiology and Circulation" ,
			 * "MEDICINE - Cardiology and Circulation")); listaAreas.add(new
			 * SelectItem("MEDICINE - Clinical Genetics" ,
			 * "MEDICINE - Clinical Genetics")); listaAreas.add(new
			 * SelectItem("MEDICINE - Clinical Immunology" ,
			 * "MEDICINE - Clinical Immunology")); listaAreas.add(new
			 * SelectItem("MEDICINE - Clinical Pharmacology" ,
			 * "MEDICINE - Clinical Pharmacology")); listaAreas.add(new
			 * SelectItem("MEDICINE - Clinical Psychology" ,
			 * "MEDICINE - Clinical Psychology")); listaAreas.add(new
			 * SelectItem("MEDICINE - Clinical Trials" ,
			 * "MEDICINE - Clinical Trials")); listaAreas.add(new
			 * SelectItem("MEDICINE - Critical Care Medicine" ,
			 * "MEDICINE - Critical Care Medicine")); listaAreas.add(new
			 * SelectItem("MEDICINE - Dentistry" , "MEDICINE - Dentistry"));
			 * listaAreas.add(new SelectItem("MEDICINE - Dermatology" ,
			 * "MEDICINE - Dermatology")); listaAreas.add(new
			 * SelectItem("MEDICINE - Dermatopathology" ,
			 * "MEDICINE - Dermatopathology")); listaAreas.add(new
			 * SelectItem("MEDICINE - Diabetes" , "MEDICINE - Diabetes"));
			 * listaAreas.add(new SelectItem("MEDICINE - Emergency Medicine" ,
			 * "MEDICINE - Emergency Medicine")); listaAreas.add(new
			 * SelectItem("MEDICINE - Endocrinology" ,
			 * "MEDICINE - Endocrinology")); listaAreas.add(new
			 * SelectItem("MEDICINE - Epidemiology" ,
			 * "MEDICINE - Epidemiology")); listaAreas.add(new
			 * SelectItem("MEDICINE - Family Practice" ,
			 * "MEDICINE - Family Practice")); listaAreas.add(new
			 * SelectItem("MEDICINE - Gastroenterology" ,
			 * "MEDICINE - Gastroenterology")); listaAreas.add(new
			 * SelectItem("MEDICINE - General Practice" ,
			 * "MEDICINE - General Practice")); listaAreas.add(new
			 * SelectItem("MEDICINE - Geriatrics" , "MEDICINE - Geriatrics"));
			 * listaAreas.add(new SelectItem("MEDICINE - Gynecology" ,
			 * "MEDICINE - Gynecology")); listaAreas.add(new
			 * SelectItem("MEDICINE - Health Economics and Outcomes Research" ,
			 * "MEDICINE - Health Economics and Outcomes Research"));
			 * listaAreas.add(new SelectItem("MEDICINE - Hematology" ,
			 * "MEDICINE - Hematology")); listaAreas.add(new
			 * SelectItem("MEDICINE - Hematology - Oncology" ,
			 * "MEDICINE - Hematology - Oncology")); listaAreas.add(new
			 * SelectItem("MEDICINE - Hepatology" , "MEDICINE - Hepatology"));
			 * listaAreas.add(new SelectItem("MEDICINE - Hypertension" ,
			 * "MEDICINE - Hypertension")); listaAreas.add(new
			 * SelectItem("MEDICINE - Infectious Diseases" ,
			 * "MEDICINE - Infectious Diseases")); listaAreas.add(new
			 * SelectItem("MEDICINE - Internal Medicine" ,
			 * "MEDICINE - Internal Medicine")); listaAreas.add(new
			 * SelectItem("MEDICINE - Maternal & Fetal Medicine" ,
			 * "MEDICINE - Maternal & Fetal Medicine")); listaAreas.add(new
			 * SelectItem("MEDICINE - Neonatal-Perinatal Medicine" ,
			 * "MEDICINE - Neonatal-Perinatal Medicine")); listaAreas.add(new
			 * SelectItem("MEDICINE - Nephrology" , "MEDICINE - Nephrology"));
			 * listaAreas.add(new SelectItem("MEDICINE - Neurology" ,
			 * "MEDICINE - Neurology")); listaAreas.add(new
			 * SelectItem("MEDICINE - Neurology - Child" ,
			 * "MEDICINE - Neurology - Child")); listaAreas.add(new
			 * SelectItem("MEDICINE - Nuclear Medicine" ,
			 * "MEDICINE - Nuclear Medicine")); listaAreas.add(new
			 * SelectItem("MEDICINE - Nursing" , "MEDICINE - Nursing"));
			 * listaAreas.add(new SelectItem("MEDICINE - Nutrition" ,
			 * "MEDICINE - Nutrition")); listaAreas.add(new
			 * SelectItem("MEDICINE - Obstetrics & Gynecology" ,
			 * "MEDICINE - Obstetrics & Gynecology")); listaAreas.add(new
			 * SelectItem("MEDICINE - Occupational Medicine" ,
			 * "MEDICINE - Occupational Medicine")); listaAreas.add(new
			 * SelectItem("MEDICINE - Oncology - Medical" ,
			 * "MEDICINE - Oncology - Medical")); listaAreas.add(new
			 * SelectItem("MEDICINE - Oncology - Radiation" ,
			 * "MEDICINE - Oncology - Radiation")); listaAreas.add(new
			 * SelectItem("MEDICINE - Oncology - Surgical" ,
			 * "MEDICINE - Oncology - Surgical")); listaAreas.add(new
			 * SelectItem("MEDICINE - Ophthalmology" ,
			 * "MEDICINE - Ophthalmology")); listaAreas.add(new
			 * SelectItem("MEDICINE - Other Clinical Medicine" ,
			 * "MEDICINE - Other Clinical Medicine")); listaAreas.add(new
			 * SelectItem("MEDICINE - Otorhinolaryngology" ,
			 * "MEDICINE - Otorhinolaryngology")); listaAreas.add(new
			 * SelectItem("MEDICINE - Pain Medicine" ,
			 * "MEDICINE - Pain Medicine")); listaAreas.add(new
			 * SelectItem("MEDICINE - Palliative Medicine" ,
			 * "MEDICINE - Palliative Medicine")); listaAreas.add(new
			 * SelectItem("MEDICINE - Pathology" , "MEDICINE - Pathology"));
			 * listaAreas.add(new SelectItem("MEDICINE - Pediatrics" ,
			 * "MEDICINE - Pediatrics")); listaAreas.add(new
			 * SelectItem("MEDICINE - Physical Medicine & Rehab" ,
			 * "MEDICINE - Physical Medicine & Rehab")); listaAreas.add(new
			 * SelectItem("MEDICINE - Physiology" , "MEDICINE - Physiology"));
			 * listaAreas.add(new SelectItem("MEDICINE - Preventive Medicine" ,
			 * "MEDICINE - Preventive Medicine")); listaAreas.add(new
			 * SelectItem("MEDICINE - Psychiatry" , "MEDICINE - Psychiatry"));
			 * listaAreas.add(new SelectItem("MEDICINE - Psychiatry - Addiction"
			 * , "MEDICINE - Psychiatry - Addiction")); listaAreas.add(new
			 * SelectItem("MEDICINE - Psychiatry - Child" ,
			 * "MEDICINE - Psychiatry - Child")); listaAreas.add(new
			 * SelectItem("MEDICINE - Psychiatry - General" ,
			 * "MEDICINE - Psychiatry - General")); listaAreas.add(new
			 * SelectItem("MEDICINE - Psychiatry - Geriatric" ,
			 * "MEDICINE - Psychiatry - Geriatric")); listaAreas.add(new
			 * SelectItem("MEDICINE - Psychology" , "MEDICINE - Psychology"));
			 * listaAreas.add(new SelectItem("MEDICINE - Public Health" ,
			 * "MEDICINE - Public Health")); listaAreas.add(new
			 * SelectItem("MEDICINE - Pulmonary Disease" ,
			 * "MEDICINE - Pulmonary Disease")); listaAreas.add(new
			 * SelectItem("MEDICINE - Radiation" , "MEDICINE - Radiation"));
			 * listaAreas.add(new SelectItem("MEDICINE - Radiology" ,
			 * "MEDICINE - Radiology")); listaAreas.add(new
			 * SelectItem("MEDICINE - Reproductive Endocrinology" ,
			 * "MEDICINE - Reproductive Endocrinology")); listaAreas.add(new
			 * SelectItem("MEDICINE - Rheumatology" ,
			 * "MEDICINE - Rheumatology")); listaAreas.add(new
			 * SelectItem("MEDICINE - Sexual Dysfunction" ,
			 * "MEDICINE - Sexual Dysfunction")); listaAreas.add(new
			 * SelectItem("MEDICINE - Spinal Cord Injury" ,
			 * "MEDICINE - Spinal Cord Injury")); listaAreas.add(new
			 * SelectItem("MEDICINE - Surgery - General" ,
			 * "MEDICINE - Surgery - General")); listaAreas.add(new
			 * SelectItem("MEDICINE - Surgery - Specialist" ,
			 * "MEDICINE - Surgery - Specialist")); listaAreas.add(new
			 * SelectItem("MEDICINE - Toxicology" , "MEDICINE - Toxicology"));
			 * listaAreas.add(new SelectItem("MEDICINE - Urology" ,
			 * "MEDICINE - Urology")); listaAreas.add(new
			 * SelectItem("MEDICINE - Veterinary Science" ,
			 * "MEDICINE - Veterinary Science")); listaAreas.add(new
			 * SelectItem("MEDICINE - Virology" , "MEDICINE - Virology"));
			 * 
			 * 
			 * listaAreas.add(new
			 * SelectItem("PHYSICS - Atomic and Molecular Physics" ,
			 * "PHYSICS - Atomic and Molecular Physics")); listaAreas.add(new
			 * SelectItem("PHYSICS - Biological Physics" ,
			 * "PHYSICS - Biological Physics")); listaAreas.add(new
			 * SelectItem("PHYSICS - Computational Physics" ,
			 * "PHYSICS - Computational Physics")); listaAreas.add(new
			 * SelectItem("PHYSICS - Condensed-matter Physics" ,
			 * "PHYSICS - Condensed-matter Physics")); listaAreas.add(new
			 * SelectItem("PHYSICS - High-energy Physics" ,
			 * "PHYSICS - High-energy Physics")); listaAreas.add(new
			 * SelectItem("PHYSICS - Materials Physics" ,
			 * "PHYSICS - Materials Physics")); listaAreas.add(new
			 * SelectItem("PHYSICS - Nuclear Physics" ,
			 * "PHYSICS - Nuclear Physics")); listaAreas.add(new
			 * SelectItem("PHYSICS - Optics/Lasers" ,
			 * "PHYSICS - Optics/Lasers")); listaAreas.add(new
			 * SelectItem("PHYSICS - Other Physics" ,
			 * "PHYSICS - Other Physics")); listaAreas.add(new
			 * SelectItem("PHYSICS - Plasma and Fluids" ,
			 * "PHYSICS - Plasma and Fluids"));
			 * 
			 * 
			 * listaAreas.add(new SelectItem("SOCIAL SCIENCES - Anthropology" ,
			 * "SOCIAL SCIENCES - Anthropology")); listaAreas.add(new
			 * SelectItem("SOCIAL SCIENCES - Communications" ,
			 * "SOCIAL SCIENCES - Communications")); listaAreas.add(new
			 * SelectItem("SOCIAL SCIENCES - Economics" ,
			 * "SOCIAL SCIENCES - Economics")); listaAreas.add(new
			 * SelectItem("SOCIAL SCIENCES - Economics - International " ,
			 * "SOCIAL SCIENCES - Economics - International "));
			 * listaAreas.add(new
			 * SelectItem("SOCIAL SCIENCES - Economics - Micro" ,
			 * "SOCIAL SCIENCES - Economics - Micro")); listaAreas.add(new
			 * SelectItem("SOCIAL SCIENCES - Law" , "SOCIAL SCIENCES - Law"));
			 * listaAreas.add(new
			 * SelectItem("SOCIAL SCIENCES - Policy & Social Sciences" ,
			 * "SOCIAL SCIENCES - Policy & Social Sciences"));
			 * listaAreas.add(new
			 * SelectItem("SOCIAL SCIENCES - Political Science" ,
			 * "SOCIAL SCIENCES - Political Science")); listaAreas.add(new
			 * SelectItem("SOCIAL SCIENCES - Political Science - Comparative" ,
			 * "SOCIAL SCIENCES - Political Science - Comparative"));
			 * listaAreas.add(new
			 * SelectItem("SOCIAL SCIENCES - Political Science - International "
			 * , "SOCIAL SCIENCES - Political Science - International "));
			 * listaAreas.add(new
			 * SelectItem("SOCIAL SCIENCES - Religious Studies" ,
			 * "SOCIAL SCIENCES - Religious Studies")); listaAreas.add(new
			 * SelectItem("SOCIAL SCIENCES - Sociology" ,
			 * "SOCIAL SCIENCES - Sociology"));
			 * 
			 * 
			 * listaAreas.add(new SelectItem("OTHER FIELDS - Agriculture" ,
			 * "OTHER FIELDS - Agriculture")); listaAreas.add(new
			 * SelectItem("OTHER FIELDS - Applied Mathematics" ,
			 * "OTHER FIELDS - Applied Mathematics")); listaAreas.add(new
			 * SelectItem("OTHER FIELDS - Archaeology" ,
			 * "OTHER FIELDS - Archaeology")); listaAreas.add(new
			 * SelectItem("OTHER FIELDS - Architecture" ,
			 * "OTHER FIELDS - Architecture")); listaAreas.add(new
			 * SelectItem("OTHER FIELDS - Computer Science" ,
			 * "OTHER FIELDS - Computer Science")); listaAreas.add(new
			 * SelectItem("OTHER FIELDS - Education" ,
			 * "OTHER FIELDS - Education")); listaAreas.add(new
			 * SelectItem("OTHER FIELDS - Food Science" ,
			 * "OTHER FIELDS - Food Science")); listaAreas.add(new
			 * SelectItem("OTHER FIELDS - General science (non-professional)" ,
			 * "OTHER FIELDS - General science (non-professional)"));
			 * listaAreas.add(new SelectItem("OTHER FIELDS - Humanities" ,
			 * "OTHER FIELDS - Humanities")); listaAreas.add(new
			 * SelectItem("OTHER FIELDS - Language/Linguistics" ,
			 * "OTHER FIELDS - Language/Linguistics")); listaAreas.add(new
			 * SelectItem("OTHER FIELDS - Materials Science" ,
			 * "OTHER FIELDS - Materials Science")); listaAreas.add(new
			 * SelectItem("OTHER FIELDS - Publishing/Media" ,
			 * "OTHER FIELDS - Publishing/Media")); listaAreas.add(new
			 * SelectItem("OTHER FIELDS - Pure Mathematics" ,
			 * "OTHER FIELDS - Pure Mathematics")); listaAreas.add(new
			 * SelectItem("OTHER FIELDS - Statistics" ,
			 * "OTHER FIELDS - Statistics"));
			 */

			listaAreas.add(new SelectItem(
					"ASTRONOMY AND PLANETARY SCIENCE  - Astronomy",
					"ASTRONOMY AND PLANETARY SCIENCE  - Astronomy"));
			listaAreas.add(new SelectItem(
					"ASTRONOMY AND PLANETARY SCIENCE  - Cosmology  ",
					"ASTRONOMY AND PLANETARY SCIENCE  - Cosmology  "));
			listaAreas.add(new SelectItem(
					"ASTRONOMY AND PLANETARY SCIENCE  - Planetary Science  ",
					"ASTRONOMY AND PLANETARY SCIENCE  - Planetary Science  "));
			listaAreas
					.add(new SelectItem(
							"ASTRONOMY AND PLANETARY SCIENCE  - Theoretical Astrophysics ",
							"ASTRONOMY AND PLANETARY SCIENCE  - Theoretical Astrophysics "));
			listaAreas
					.add(new SelectItem(
							"ASTRONOMY AND PLANETARY SCIENCE  - Other Astronomy and Planetary Science  ",
							"ASTRONOMY AND PLANETARY SCIENCE  - Other Astronomy and Planetary Science  "));

			listaAreas.add(new SelectItem("BIOLOGY - Agriculture",
					"BIOLOGY - Agriculture"));
			listaAreas.add(new SelectItem("BIOLOGY - Biochemistry  ",
					"BIOLOGY - Biochemistry  "));
			listaAreas.add(new SelectItem("BIOLOGY - Bioinformatics  ",
					"BIOLOGY - Bioinformatics  "));
			listaAreas.add(new SelectItem("BIOLOGY - Biological Chemistry  ",
					"BIOLOGY - Biological Chemistry  "));
			listaAreas.add(new SelectItem("BIOLOGY - Biological Systematics",
					"BIOLOGY - Biological Systematics"));
			listaAreas.add(new SelectItem("BIOLOGY - Biophysics  ",
					"BIOLOGY - Biophysics  "));
			listaAreas.add(new SelectItem("BIOLOGY - Biotechnology  ",
					"BIOLOGY - Biotechnology  "));
			listaAreas.add(new SelectItem("BIOLOGY - Cancer Research  ",
					"BIOLOGY - Cancer Research  "));
			listaAreas.add(new SelectItem("BIOLOGY - Cardiovascular Biology  ",
					"BIOLOGY - Cardiovascular Biology  "));
			listaAreas.add(new SelectItem("BIOLOGY - Cell Biology  ",
					"BIOLOGY - Cell Biology  "));
			listaAreas.add(new SelectItem("BIOLOGY - Computational Biology  ",
					"BIOLOGY - Computational Biology  "));
			listaAreas.add(new SelectItem("BIOLOGY - Conservation Biology  ",
					"BIOLOGY - Conservation Biology  "));
			listaAreas.add(new SelectItem("BIOLOGY - Developmental Biology  ",
					"BIOLOGY - Developmental Biology  "));
			listaAreas.add(new SelectItem("BIOLOGY - Developmental Genetics",
					"BIOLOGY - Developmental Genetics"));
			listaAreas.add(new SelectItem("BIOLOGY - Drug Discovery  ",
					"BIOLOGY - Drug Discovery  "));
			listaAreas.add(new SelectItem("BIOLOGY - Ecology  ",
					"BIOLOGY - Ecology  "));
			listaAreas.add(new SelectItem("BIOLOGY - Entomology",
					"BIOLOGY - Entomology"));
			listaAreas.add(new SelectItem("BIOLOGY - Epigenetics",
					"BIOLOGY - Epigenetics"));
			listaAreas.add(new SelectItem("BIOLOGY - Evolution  ",
					"BIOLOGY - Evolution  "));
			listaAreas.add(new SelectItem("BIOLOGY - Genetics  ",
					"BIOLOGY - Genetics  "));
			listaAreas.add(new SelectItem("BIOLOGY - Genomics  ",
					"BIOLOGY - Genomics  "));
			listaAreas.add(new SelectItem("BIOLOGY - Immunology  ",
					"BIOLOGY - Immunology  "));
			listaAreas.add(new SelectItem(
					"BIOLOGY - Industrial Microbiology  ",
					"BIOLOGY - Industrial Microbiology  "));
			listaAreas.add(new SelectItem("BIOLOGY - Marine Biology  ",
					"BIOLOGY - Marine Biology  "));
			listaAreas.add(new SelectItem("BIOLOGY - Microbiology  ",
					"BIOLOGY - Microbiology  "));
			listaAreas.add(new SelectItem("BIOLOGY - Molecular Biology",
					"BIOLOGY - Molecular Biology"));
			listaAreas.add(new SelectItem("BIOLOGY - Molecular Epidemiology",
					"BIOLOGY - Molecular Epidemiology"));
			listaAreas.add(new SelectItem("BIOLOGY - Mycology  ",
					"BIOLOGY - Mycology  "));
			listaAreas.add(new SelectItem("BIOLOGY - Neuroscience",
					"BIOLOGY - Neuroscience"));
			listaAreas
					.add(new SelectItem(
							"BIOLOGY - Neuroscience – Behavioral / Systems / Cognitive",
							"BIOLOGY - Neuroscience – Behavioral / Systems / Cognitive"));
			listaAreas.add(new SelectItem(
					"BIOLOGY - Neuroscience – Cellular / Molecular",
					"BIOLOGY - Neuroscience – Cellular / Molecular"));
			listaAreas.add(new SelectItem(
					"BIOLOGY - Neuroscience – Computational",
					"BIOLOGY - Neuroscience – Computational"));
			listaAreas
					.add(new SelectItem(
							"BIOLOGY - Neuroscience – Development / Plasticity / Repair",
							"BIOLOGY - Neuroscience – Development / Plasticity / Repair"));
			listaAreas.add(new SelectItem(
					"BIOLOGY - Neuroscience – Neurobiology of disease",
					"BIOLOGY - Neuroscience – Neurobiology of disease"));
			listaAreas.add(new SelectItem("BIOLOGY - Ornithology",
					"BIOLOGY - Ornithology"));
			listaAreas.add(new SelectItem("BIOLOGY - Parasitology  ",
					"BIOLOGY - Parasitology  "));
			listaAreas.add(new SelectItem("BIOLOGY - Pharmacology  ",
					"BIOLOGY - Pharmacology  "));
			listaAreas.add(new SelectItem(
					"BIOLOGY - Plant Science – cellular/morphological ",
					"BIOLOGY - Plant Science – cellular/morphological "));
			listaAreas.add(new SelectItem(
					"BIOLOGY - Plant Science – molecular/genetics",
					"BIOLOGY - Plant Science – molecular/genetics"));
			listaAreas.add(new SelectItem("BIOLOGY - Proteomics  ",
					"BIOLOGY - Proteomics  "));
			listaAreas.add(new SelectItem("BIOLOGY - Stem Cell Biology",
					"BIOLOGY - Stem Cell Biology"));
			listaAreas.add(new SelectItem("BIOLOGY - Structural Biology  ",
					"BIOLOGY - Structural Biology  "));
			listaAreas.add(new SelectItem("BIOLOGY - Systems Biology  ",
					"BIOLOGY - Systems Biology  "));
			listaAreas.add(new SelectItem("BIOLOGY - Taxonomy",
					"BIOLOGY - Taxonomy"));
			listaAreas.add(new SelectItem("BIOLOGY - Zoology  ",
					"BIOLOGY - Zoology  "));
			listaAreas.add(new SelectItem("BIOLOGY - Other Biology",
					"BIOLOGY - Other Biology"));

			listaAreas.add(new SelectItem("BUSINESS - Accounting  ",
					"BUSINESS - Accounting  "));
			listaAreas.add(new SelectItem("BUSINESS - Finance  ",
					"BUSINESS - Finance  "));
			listaAreas.add(new SelectItem("BUSINESS - Hospitality/Tourism",
					"BUSINESS - Hospitality/Tourism"));
			listaAreas.add(new SelectItem(
					"BUSINESS - International Business  ",
					"BUSINESS - International Business  "));
			listaAreas.add(new SelectItem("BUSINESS - Management  ",
					"BUSINESS - Management  "));
			listaAreas.add(new SelectItem("BUSINESS - Marketing/PR  ",
					"BUSINESS - Marketing/PR  "));
			listaAreas.add(new SelectItem("BUSINESS - Patents  ",
					"BUSINESS - Patents  "));
			listaAreas.add(new SelectItem("BUSINESS - Other Business  ",
					"BUSINESS - Other Business  "));

			listaAreas.add(new SelectItem("CHEMISTRY - Analytical Chemistry  ",
					"CHEMISTRY - Analytical Chemistry  "));
			listaAreas.add(new SelectItem("CHEMISTRY - Applied Chemistry  ",
					"CHEMISTRY - Applied Chemistry  "));
			listaAreas.add(new SelectItem("CHEMISTRY - Biotechnology",
					"CHEMISTRY - Biotechnology"));
			listaAreas.add(new SelectItem("CHEMISTRY - Catalysis  ",
					"CHEMISTRY - Catalysis  "));
			listaAreas.add(new SelectItem("CHEMISTRY - Chemical Biology  ",
					"CHEMISTRY - Chemical Biology  "));
			listaAreas.add(new SelectItem("CHEMISTRY - Chemical Engineering",
					"CHEMISTRY - Chemical Engineering"));
			listaAreas.add(new SelectItem(
					"CHEMISTRY - Computational Chemistry  ",
					"CHEMISTRY - Computational Chemistry  "));
			listaAreas.add(new SelectItem("CHEMISTRY - Crystallography",
					"CHEMISTRY - Crystallography"));
			listaAreas.add(new SelectItem("CHEMISTRY - Drug Discovery",
					"CHEMISTRY - Drug Discovery"));
			listaAreas.add(new SelectItem("CHEMISTRY - Electrochemistry",
					"CHEMISTRY - Electrochemistry"));
			listaAreas.add(new SelectItem(
					"CHEMISTRY - Environmental Chemistry  ",
					"CHEMISTRY - Environmental Chemistry  "));
			listaAreas.add(new SelectItem("CHEMISTRY - Food Science",
					"CHEMISTRY - Food Science"));
			listaAreas.add(new SelectItem("CHEMISTRY - Inorganic Chemistry  ",
					"CHEMISTRY - Inorganic Chemistry  "));
			listaAreas.add(new SelectItem("CHEMISTRY - Mass Spectrometry",
					"CHEMISTRY - Mass Spectrometry"));
			listaAreas.add(new SelectItem("CHEMISTRY - Materials Chemistry  ",
					"CHEMISTRY - Materials Chemistry  "));
			listaAreas.add(new SelectItem("CHEMISTRY - Materials Science",
					"CHEMISTRY - Materials Science"));
			listaAreas.add(new SelectItem(
					"CHEMISTRY - Medicinal & Pharmaceutical Chemistry  ",
					"CHEMISTRY - Medicinal & Pharmaceutical Chemistry  "));
			listaAreas.add(new SelectItem("CHEMISTRY - Nanoscience",
					"CHEMISTRY - Nanoscience"));
			listaAreas.add(new SelectItem("CHEMISTRY - Nuclear Chemistry  ",
					"CHEMISTRY - Nuclear Chemistry  "));
			listaAreas.add(new SelectItem("CHEMISTRY - Organic Chemistry  ",
					"CHEMISTRY - Organic Chemistry  "));
			listaAreas.add(new SelectItem("CHEMISTRY - Physical Chemistry  ",
					"CHEMISTRY - Physical Chemistry  "));
			listaAreas.add(new SelectItem("CHEMISTRY - Polymer Science",
					"CHEMISTRY - Polymer Science"));
			listaAreas.add(new SelectItem("CHEMISTRY - Spectroscopy  ",
					"CHEMISTRY - Spectroscopy  "));
			listaAreas.add(new SelectItem(
					"CHEMISTRY - Theoretical Chemistry  ",
					"CHEMISTRY - Theoretical Chemistry  "));
			listaAreas.add(new SelectItem("CHEMISTRY - Other Chemistry  ",
					"CHEMISTRY - Other Chemistry  "));

			listaAreas.add(new SelectItem(
					"EARTH AND ENVIRONMENTAL SCIENCE - Agriculture",
					"EARTH AND ENVIRONMENTAL SCIENCE - Agriculture"));
			listaAreas.add(new SelectItem(
					"EARTH AND ENVIRONMENTAL SCIENCE - Agronomy",
					"EARTH AND ENVIRONMENTAL SCIENCE - Agronomy"));
			listaAreas
					.add(new SelectItem(
							"EARTH AND ENVIRONMENTAL SCIENCE - Atmospheric Science/Climate ",
							"EARTH AND ENVIRONMENTAL SCIENCE - Atmospheric Science/Climate "));
			listaAreas.add(new SelectItem(
					"EARTH AND ENVIRONMENTAL SCIENCE - Forestry",
					"EARTH AND ENVIRONMENTAL SCIENCE - Forestry"));
			listaAreas.add(new SelectItem(
					"EARTH AND ENVIRONMENTAL SCIENCE - Geochemistry",
					"EARTH AND ENVIRONMENTAL SCIENCE - Geochemistry"));
			listaAreas
					.add(new SelectItem(
							"EARTH AND ENVIRONMENTAL SCIENCE - Geographic Information Systems",
							"EARTH AND ENVIRONMENTAL SCIENCE - Geographic Information Systems"));
			listaAreas.add(new SelectItem(
					"EARTH AND ENVIRONMENTAL SCIENCE - Geography ",
					"EARTH AND ENVIRONMENTAL SCIENCE - Geography "));
			listaAreas.add(new SelectItem(
					"EARTH AND ENVIRONMENTAL SCIENCE - Geology  ",
					"EARTH AND ENVIRONMENTAL SCIENCE - Geology  "));
			listaAreas.add(new SelectItem(
					"EARTH AND ENVIRONMENTAL SCIENCE - Geophysics  ",
					"EARTH AND ENVIRONMENTAL SCIENCE - Geophysics  "));
			listaAreas.add(new SelectItem(
					"EARTH AND ENVIRONMENTAL SCIENCE - Geoscience  ",
					"EARTH AND ENVIRONMENTAL SCIENCE - Geoscience  "));
			listaAreas.add(new SelectItem(
					"EARTH AND ENVIRONMENTAL SCIENCE - Meteorology",
					"EARTH AND ENVIRONMENTAL SCIENCE - Meteorology"));
			listaAreas.add(new SelectItem(
					"EARTH AND ENVIRONMENTAL SCIENCE - Oceanography  ",
					"EARTH AND ENVIRONMENTAL SCIENCE - Oceanography  "));
			listaAreas.add(new SelectItem(
					"EARTH AND ENVIRONMENTAL SCIENCE - Palaeoclimate  ",
					"EARTH AND ENVIRONMENTAL SCIENCE - Palaeoclimate  "));
			listaAreas.add(new SelectItem(
					"EARTH AND ENVIRONMENTAL SCIENCE - Palaeontology  ",
					"EARTH AND ENVIRONMENTAL SCIENCE - Palaeontology  "));
			listaAreas.add(new SelectItem(
					"EARTH AND ENVIRONMENTAL SCIENCE - Planetary Geology  ",
					"EARTH AND ENVIRONMENTAL SCIENCE - Planetary Geology  "));
			listaAreas.add(new SelectItem(
					"EARTH AND ENVIRONMENTAL SCIENCE - Solid Earth Science  ",
					"EARTH AND ENVIRONMENTAL SCIENCE - Solid Earth Science  "));
			listaAreas
					.add(new SelectItem(
							"EARTH AND ENVIRONMENTAL SCIENCE - Other Environmental Science  ",
							"EARTH AND ENVIRONMENTAL SCIENCE - Other Environmental Science  "));

			listaAreas.add(new SelectItem("ENGINEERING - Acoustics",
					"ENGINEERING - Acoustics"));
			listaAreas.add(new SelectItem(
					"ENGINEERING - Aeronautical Engineering  ",
					"ENGINEERING - Aeronautical Engineering  "));
			listaAreas.add(new SelectItem("ENGINEERING - Biomechanics",
					"ENGINEERING - Biomechanics"));
			listaAreas.add(new SelectItem(
					"ENGINEERING - Biomedical Engineering  ",
					"ENGINEERING - Biomedical Engineering  "));
			listaAreas.add(new SelectItem(
					"ENGINEERING - Chemical Engineering  ",
					"ENGINEERING - Chemical Engineering  "));
			listaAreas.add(new SelectItem("ENGINEERING - Civil Engineering  ",
					"ENGINEERING - Civil Engineering  "));
			listaAreas.add(new SelectItem(
					"ENGINEERING - Electrical/Electronic Engineering  ",
					"ENGINEERING - Electrical/Electronic Engineering  "));
			listaAreas.add(new SelectItem(
					"ENGINEERING - Environmental Engineering  ",
					"ENGINEERING - Environmental Engineering  "));
			listaAreas.add(new SelectItem(
					"ENGINEERING - Materials Engineering  ",
					"ENGINEERING - Materials Engineering  "));
			listaAreas.add(new SelectItem("ENGINEERING - Materials Science",
					"ENGINEERING - Materials Science"));
			listaAreas.add(new SelectItem(
					"ENGINEERING - Mechanical Engineering  ",
					"ENGINEERING - Mechanical Engineering  "));
			listaAreas.add(new SelectItem(
					"ENGINEERING - Nuclear Engineering  ",
					"ENGINEERING - Nuclear Engineering  "));
			listaAreas.add(new SelectItem("ENGINEERING - Robotics",
					"ENGINEERING - Robotics"));
			listaAreas.add(new SelectItem(
					"ENGINEERING - Software Engineering  ",
					"ENGINEERING - Software Engineering  "));
			listaAreas.add(new SelectItem(
					"ENGINEERING - Systems/Industrial Engineering  ",
					"ENGINEERING - Systems/Industrial Engineering  "));
			listaAreas.add(new SelectItem("ENGINEERING - Tissue Engineering",
					"ENGINEERING - Tissue Engineering"));
			listaAreas.add(new SelectItem("ENGINEERING - Other Engineering  ",
					"ENGINEERING - Other Engineering  "));

			listaAreas.add(new SelectItem(
					"HUMANITIES/SOCIAL SCIENCES - Anthropology",
					"HUMANITIES/SOCIAL SCIENCES - Anthropology"));
			listaAreas.add(new SelectItem(
					"HUMANITIES/SOCIAL SCIENCES - Archaeology",
					"HUMANITIES/SOCIAL SCIENCES - Archaeology"));
			listaAreas.add(new SelectItem(
					"HUMANITIES/SOCIAL SCIENCES - Architecture",
					"HUMANITIES/SOCIAL SCIENCES - Architecture"));
			listaAreas.add(new SelectItem(
					"HUMANITIES/SOCIAL SCIENCES - Communications",
					"HUMANITIES/SOCIAL SCIENCES - Communications"));
			listaAreas.add(new SelectItem(
					"HUMANITIES/SOCIAL SCIENCES - Criminology",
					"HUMANITIES/SOCIAL SCIENCES - Criminology"));
			listaAreas.add(new SelectItem(
					"HUMANITIES/SOCIAL SCIENCES - Economics",
					"HUMANITIES/SOCIAL SCIENCES - Economics"));
			listaAreas.add(new SelectItem(
					"HUMANITIES/SOCIAL SCIENCES - Economics – International",
					"HUMANITIES/SOCIAL SCIENCES - Economics – International"));
			listaAreas.add(new SelectItem(
					"HUMANITIES/SOCIAL SCIENCES - Economics – Micro",
					"HUMANITIES/SOCIAL SCIENCES - Economics – Micro"));
			listaAreas.add(new SelectItem(
					"HUMANITIES/SOCIAL SCIENCES - Education",
					"HUMANITIES/SOCIAL SCIENCES - Education"));
			listaAreas.add(new SelectItem(
					"HUMANITIES/SOCIAL SCIENCES - Environmental Economics",
					"HUMANITIES/SOCIAL SCIENCES - Environmental Economics"));
			listaAreas.add(new SelectItem(
					"HUMANITIES/SOCIAL SCIENCES - Environmental Policy",
					"HUMANITIES/SOCIAL SCIENCES - Environmental Policy"));
			listaAreas.add(new SelectItem(
					"HUMANITIES/SOCIAL SCIENCES - Ethics",
					"HUMANITIES/SOCIAL SCIENCES - Ethics"));
			listaAreas.add(new SelectItem(
					"HUMANITIES/SOCIAL SCIENCES - History",
					"HUMANITIES/SOCIAL SCIENCES - History"));
			listaAreas.add(new SelectItem(
					"HUMANITIES/SOCIAL SCIENCES - Humanities",
					"HUMANITIES/SOCIAL SCIENCES - Humanities"));
			listaAreas
					.add(new SelectItem(
							"HUMANITIES/SOCIAL SCIENCES - Information/Library Science",
							"HUMANITIES/SOCIAL SCIENCES - Information/Library Science"));
			listaAreas.add(new SelectItem(
					"HUMANITIES/SOCIAL SCIENCES - Language/Linguistics",
					"HUMANITIES/SOCIAL SCIENCES - Language/Linguistics"));
			listaAreas.add(new SelectItem("HUMANITIES/SOCIAL SCIENCES - Law",
					"HUMANITIES/SOCIAL SCIENCES - Law"));
			listaAreas.add(new SelectItem(
					"HUMANITIES/SOCIAL SCIENCES - Philosophy",
					"HUMANITIES/SOCIAL SCIENCES - Philosophy"));
			listaAreas.add(new SelectItem(
					"HUMANITIES/SOCIAL SCIENCES - Policy & Social Sciences",
					"HUMANITIES/SOCIAL SCIENCES - Policy & Social Sciences"));
			listaAreas.add(new SelectItem(
					"HUMANITIES/SOCIAL SCIENCES - Political Science",
					"HUMANITIES/SOCIAL SCIENCES - Political Science"));
			listaAreas
					.add(new SelectItem(
							"HUMANITIES/SOCIAL SCIENCES - Political Science – Comparative",
							"HUMANITIES/SOCIAL SCIENCES - Political Science – Comparative"));
			listaAreas
					.add(new SelectItem(
							"HUMANITIES/SOCIAL SCIENCES - Political Science – International",
							"HUMANITIES/SOCIAL SCIENCES - Political Science – International"));
			listaAreas.add(new SelectItem(
					"HUMANITIES/SOCIAL SCIENCES - Publishing/Media",
					"HUMANITIES/SOCIAL SCIENCES - Publishing/Media"));
			listaAreas.add(new SelectItem(
					"HUMANITIES/SOCIAL SCIENCES - Religious Studies",
					"HUMANITIES/SOCIAL SCIENCES - Religious Studies"));
			listaAreas.add(new SelectItem(
					"HUMANITIES/SOCIAL SCIENCES - Sociology",
					"HUMANITIES/SOCIAL SCIENCES - Sociology"));
			listaAreas.add(new SelectItem(
					"HUMANITIES/SOCIAL SCIENCES - Urban Studies",
					"HUMANITIES/SOCIAL SCIENCES - Urban Studies"));
			listaAreas.add(new SelectItem(
					"HUMANITIES/SOCIAL SCIENCES - Women´s Studies",
					"HUMANITIES/SOCIAL SCIENCES - Women´s Studies"));

			listaAreas.add(new SelectItem(
					"MATHEMATICS/COMPUTER SCIENCE - Applied Mathematics",
					"MATHEMATICS/COMPUTER SCIENCE - Applied Mathematics"));
			listaAreas.add(new SelectItem(
					"MATHEMATICS/COMPUTER SCIENCE - Computer Science",
					"MATHEMATICS/COMPUTER SCIENCE - Computer Science"));
			listaAreas.add(new SelectItem(
					"MATHEMATICS/COMPUTER SCIENCE - Pure Mathematics",
					"MATHEMATICS/COMPUTER SCIENCE - Pure Mathematics"));
			listaAreas.add(new SelectItem(
					"MATHEMATICS/COMPUTER SCIENCE - Statistics",
					"MATHEMATICS/COMPUTER SCIENCE - Statistics"));

			listaAreas.add(new SelectItem("MEDICINE - Allergy  ",
					"MEDICINE - Allergy  "));
			listaAreas.add(new SelectItem("MEDICINE - Anatomy",
					"MEDICINE - Anatomy"));
			listaAreas.add(new SelectItem("MEDICINE - Anesthesiology  ",
					"MEDICINE - Anesthesiology  "));
			listaAreas.add(new SelectItem("MEDICINE - Audiology",
					"MEDICINE - Audiology"));
			listaAreas.add(new SelectItem("MEDICINE - Cancer/Oncology  ",
					"MEDICINE - Cancer/Oncology  "));
			listaAreas.add(new SelectItem(
					"MEDICINE - Cardiac Electrophysiology  ",
					"MEDICINE - Cardiac Electrophysiology  "));
			listaAreas.add(new SelectItem(
					"MEDICINE - Cardiology - Interventional  ",
					"MEDICINE - Cardiology - Interventional  "));
			listaAreas.add(new SelectItem(
					"MEDICINE - Cardiology - Noninvasive  ",
					"MEDICINE - Cardiology - Noninvasive  "));
			listaAreas.add(new SelectItem(
					"MEDICINE - Cardiology and Circulation  ",
					"MEDICINE - Cardiology and Circulation  "));
			listaAreas.add(new SelectItem("MEDICINE - Childhood Development",
					"MEDICINE - Childhood Development"));
			listaAreas.add(new SelectItem("MEDICINE - Clinical Genetics  ",
					"MEDICINE - Clinical Genetics  "));
			listaAreas.add(new SelectItem("MEDICINE - Clinical Immunology  ",
					"MEDICINE - Clinical Immunology  "));
			listaAreas.add(new SelectItem("MEDICINE - Clinical Pharmacology  ",
					"MEDICINE - Clinical Pharmacology  "));
			listaAreas.add(new SelectItem("MEDICINE - Clinical Psychology  ",
					"MEDICINE - Clinical Psychology  "));
			listaAreas.add(new SelectItem("MEDICINE - Clinical Trials  ",
					"MEDICINE - Clinical Trials  "));
			listaAreas.add(new SelectItem(
					"MEDICINE - Critical Care Medicine  ",
					"MEDICINE - Critical Care Medicine  "));
			listaAreas.add(new SelectItem("MEDICINE - Dentistry /Oral Surgery",
					"MEDICINE - Dentistry /Oral Surgery"));
			listaAreas.add(new SelectItem("MEDICINE - Dermatology  ",
					"MEDICINE - Dermatology  "));
			listaAreas.add(new SelectItem("MEDICINE - Dermatopathology  ",
					"MEDICINE - Dermatopathology  "));
			listaAreas.add(new SelectItem(
					"MEDICINE - Developmental Psychology",
					"MEDICINE - Developmental Psychology"));
			listaAreas.add(new SelectItem("MEDICINE - Diabetes  ",
					"MEDICINE - Diabetes  "));
			listaAreas.add(new SelectItem("MEDICINE - Emergency Medicine  ",
					"MEDICINE - Emergency Medicine  "));
			listaAreas.add(new SelectItem("MEDICINE - Endocrinology  ",
					"MEDICINE - Endocrinology  "));
			listaAreas.add(new SelectItem("MEDICINE - Epidemiology  ",
					"MEDICINE - Epidemiology  "));
			listaAreas.add(new SelectItem("MEDICINE - Family Practice  ",
					"MEDICINE - Family Practice  "));
			listaAreas.add(new SelectItem("MEDICINE - Gastroenterology  ",
					"MEDICINE - Gastroenterology  "));
			listaAreas.add(new SelectItem("MEDICINE - General Practice  ",
					"MEDICINE - General Practice  "));
			listaAreas.add(new SelectItem("MEDICINE - Geriatrics  ",
					"MEDICINE - Geriatrics  "));
			listaAreas.add(new SelectItem("MEDICINE - Gynecology  ",
					"MEDICINE - Gynecology  "));
			listaAreas.add(new SelectItem(
					"MEDICINE - Health Economics and Outcomes Research  ",
					"MEDICINE - Health Economics and Outcomes Research  "));
			listaAreas.add(new SelectItem("MEDICINE - Hematology  ",
					"MEDICINE - Hematology  "));
			listaAreas.add(new SelectItem("MEDICINE - Hematology - Oncology  ",
					"MEDICINE - Hematology - Oncology  "));
			listaAreas.add(new SelectItem("MEDICINE - Hepatology  ",
					"MEDICINE - Hepatology  "));
			listaAreas.add(new SelectItem("MEDICINE - Hypertension  ",
					"MEDICINE - Hypertension  "));
			listaAreas.add(new SelectItem("MEDICINE - Infectious Diseases  ",
					"MEDICINE - Infectious Diseases  "));
			listaAreas.add(new SelectItem("MEDICINE - Internal Medicine  ",
					"MEDICINE - Internal Medicine  "));
			listaAreas.add(new SelectItem(
					"MEDICINE - Maternal & Fetal Medicine  ",
					"MEDICINE - Maternal & Fetal Medicine  "));
			listaAreas.add(new SelectItem("MEDICINE - Medical Physics",
					"MEDICINE - Medical Physics"));
			listaAreas.add(new SelectItem("MEDICINE - Metabolism",
					"MEDICINE - Metabolism"));
			listaAreas.add(new SelectItem("MEDICINE - Molecular Epidemiology",
					"MEDICINE - Molecular Epidemiology"));
			listaAreas.add(new SelectItem(
					"MEDICINE - Neonatal-Perinatal Medicine  ",
					"MEDICINE - Neonatal-Perinatal Medicine  "));
			listaAreas.add(new SelectItem("MEDICINE - Nephrology  ",
					"MEDICINE - Nephrology  "));
			listaAreas.add(new SelectItem("MEDICINE - Neurology  ",
					"MEDICINE - Neurology  "));
			listaAreas.add(new SelectItem("MEDICINE - Neurology - Child  ",
					"MEDICINE - Neurology - Child  "));
			listaAreas.add(new SelectItem("MEDICINE - Nuclear Medicine  ",
					"MEDICINE - Nuclear Medicine  "));
			listaAreas.add(new SelectItem("MEDICINE - Nursing  ",
					"MEDICINE - Nursing  "));
			listaAreas.add(new SelectItem("MEDICINE - Nutrition  ",
					"MEDICINE - Nutrition  "));
			listaAreas.add(new SelectItem(
					"MEDICINE - Obstetrics & Gynecology  ",
					"MEDICINE - Obstetrics & Gynecology  "));
			listaAreas.add(new SelectItem("MEDICINE - Occupational Medicine  ",
					"MEDICINE - Occupational Medicine  "));
			listaAreas.add(new SelectItem("MEDICINE - Oncology - Medical  ",
					"MEDICINE - Oncology - Medical  "));
			listaAreas.add(new SelectItem("MEDICINE - Oncology - Radiation  ",
					"MEDICINE - Oncology - Radiation  "));
			listaAreas.add(new SelectItem("MEDICINE - Oncology - Surgical  ",
					"MEDICINE - Oncology - Surgical  "));
			listaAreas.add(new SelectItem("MEDICINE - Ophthalmology  ",
					"MEDICINE - Ophthalmology  "));
			listaAreas.add(new SelectItem("MEDICINE - Orthopedics",
					"MEDICINE - Orthopedics"));
			listaAreas.add(new SelectItem("MEDICINE - Otorhinolaryngology  ",
					"MEDICINE - Otorhinolaryngology  "));
			listaAreas.add(new SelectItem("MEDICINE - Pain Medicine  ",
					"MEDICINE - Pain Medicine  "));
			listaAreas.add(new SelectItem("MEDICINE - Palliative Medicine  ",
					"MEDICINE - Palliative Medicine  "));
			listaAreas.add(new SelectItem("MEDICINE - Pathology  ",
					"MEDICINE - Pathology  "));
			listaAreas.add(new SelectItem("MEDICINE - Pediatrics  ",
					"MEDICINE - Pediatrics  "));
			listaAreas.add(new SelectItem(
					"MEDICINE - Physical Medicine & Rehab  ",
					"MEDICINE - Physical Medicine & Rehab  "));
			listaAreas.add(new SelectItem("MEDICINE - Physiology  ",
					"MEDICINE - Physiology  "));
			listaAreas.add(new SelectItem("MEDICINE - Preventive Medicine  ",
					"MEDICINE - Preventive Medicine  "));
			listaAreas.add(new SelectItem("MEDICINE - Psychiatry  ",
					"MEDICINE - Psychiatry  "));
			listaAreas.add(new SelectItem(
					"MEDICINE - Psychiatry - Addiction /Substance Abuse",
					"MEDICINE - Psychiatry - Addiction /Substance Abuse"));
			listaAreas.add(new SelectItem("MEDICINE - Psychiatry - Child  ",
					"MEDICINE - Psychiatry - Child  "));
			listaAreas.add(new SelectItem("MEDICINE - Psychiatry - General  ",
					"MEDICINE - Psychiatry - General  "));
			listaAreas.add(new SelectItem(
					"MEDICINE - Psychiatry - Geriatric  ",
					"MEDICINE - Psychiatry - Geriatric  "));
			listaAreas.add(new SelectItem("MEDICINE - Psychology  ",
					"MEDICINE - Psychology  "));
			listaAreas.add(new SelectItem("MEDICINE - Public Health  ",
					"MEDICINE - Public Health  "));
			listaAreas.add(new SelectItem("MEDICINE - Pulmonary Disease  ",
					"MEDICINE - Pulmonary Disease  "));
			listaAreas.add(new SelectItem("MEDICINE - Radiation  ",
					"MEDICINE - Radiation  "));
			listaAreas.add(new SelectItem("MEDICINE - Radiology  ",
					"MEDICINE - Radiology  "));
			listaAreas.add(new SelectItem(
					"MEDICINE - Reproductive Endocrinology  ",
					"MEDICINE - Reproductive Endocrinology  "));
			listaAreas.add(new SelectItem("MEDICINE - Rheumatology  ",
					"MEDICINE - Rheumatology  "));
			listaAreas.add(new SelectItem("MEDICINE - Sexual Dysfunction  ",
					"MEDICINE - Sexual Dysfunction  "));
			listaAreas.add(new SelectItem("MEDICINE - Social Work",
					"MEDICINE - Social Work"));
			listaAreas.add(new SelectItem(
					"MEDICINE - Speech/Language Pathology",
					"MEDICINE - Speech/Language Pathology"));
			listaAreas.add(new SelectItem("MEDICINE - Spinal Cord Injury ",
					"MEDICINE - Spinal Cord Injury "));
			listaAreas.add(new SelectItem("MEDICINE - Sports Medicine",
					"MEDICINE - Sports Medicine"));
			listaAreas.add(new SelectItem("MEDICINE - Stem Cell Biology ",
					"MEDICINE - Stem Cell Biology "));
			listaAreas.add(new SelectItem("MEDICINE - Surgery - General  ",
					"MEDICINE - Surgery - General  "));
			listaAreas.add(new SelectItem("MEDICINE - Surgery - Specialist  ",
					"MEDICINE - Surgery - Specialist  "));
			listaAreas.add(new SelectItem("MEDICINE - Toxicology  ",
					"MEDICINE - Toxicology  "));
			listaAreas.add(new SelectItem("MEDICINE - Transplantation",
					"MEDICINE - Transplantation"));
			listaAreas.add(new SelectItem("MEDICINE - Tropical Medicine",
					"MEDICINE - Tropical Medicine"));
			listaAreas.add(new SelectItem("MEDICINE - Urology  ",
					"MEDICINE - Urology  "));
			listaAreas.add(new SelectItem("MEDICINE - Veterinary Science  ",
					"MEDICINE - Veterinary Science  "));
			listaAreas.add(new SelectItem("MEDICINE - Virology  ",
					"MEDICINE - Virology  "));
			listaAreas.add(new SelectItem(
					"MEDICINE - Other Clinical Medicine  ",
					"MEDICINE - Other Clinical Medicine  "));

			listaAreas.add(new SelectItem(
					"PHYSICS - Atomic and Molecular Physics  ",
					"PHYSICS - Atomic and Molecular Physics  "));
			listaAreas.add(new SelectItem("PHYSICS - Biological Physics  ",
					"PHYSICS - Biological Physics  "));
			listaAreas.add(new SelectItem("PHYSICS - Computational Physics  ",
					"PHYSICS - Computational Physics  "));
			listaAreas.add(new SelectItem(
					"PHYSICS - Condensed-matter Physics  ",
					"PHYSICS - Condensed-matter Physics  "));
			listaAreas.add(new SelectItem("PHYSICS - High-energy Physics  ",
					"PHYSICS - High-energy Physics  "));
			listaAreas.add(new SelectItem("PHYSICS - Materials Physics  ",
					"PHYSICS - Materials Physics  "));
			listaAreas.add(new SelectItem("PHYSICS - Materials Science",
					"PHYSICS - Materials Science"));
			listaAreas.add(new SelectItem("PHYSICS - Medical Physics",
					"PHYSICS - Medical Physics"));
			listaAreas.add(new SelectItem("PHYSICS - Nanoscience",
					"PHYSICS - Nanoscience"));
			listaAreas.add(new SelectItem("PHYSICS - Nuclear Physics  ",
					"PHYSICS - Nuclear Physics  "));
			listaAreas.add(new SelectItem("PHYSICS - Optics/Lasers  ",
					"PHYSICS - Optics/Lasers  "));
			listaAreas.add(new SelectItem("PHYSICS - Plasma and Fluids  ",
					"PHYSICS - Plasma and Fluids  "));
			listaAreas.add(new SelectItem("PHYSICS - Other Physics  ",
					"PHYSICS - Other Physics  "));

			listaAreas.add(new SelectItem(
					"OTHER FIELDS - General science (non-professional)  ",
					"OTHER FIELDS - General science (non-professional)  "));
			listaAreas.add(new SelectItem("OTHER FIELDS - Other",
					"OTHER FIELDS - Other"));

			panelRender[1] = true;
			panelRender[8] = false;
			cargarValoresIniciales();
			buscarPersona();
			apoyos = apoyosAnteriores();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void cargarTipoArchivos() {
		listaArchivosObligatorios = new ArrayList();
		// if (banderaModalidad) {
		listaArchivosObligatorios = servicioGeneral
				.obtenerListaObjetos("DominioDetalle where identificador.id = '11' ORDER BY identificador.tipo");
		// }
		/*
		 * else { listaArchivosObligatorios = servicioGeneral
		 * .obtenerListaObjetos(
		 * "DominioDetalle where identificador.id = '6' ORDER BY identificador.tipo"
		 * ); }
		 */
		if (listaArchivosObligatorios != null
				&& listaArchivosObligatorios.size() > 0) {
			tipoDocumentoSelItem = new SelectItem[listaArchivosObligatorios
					.size() + 1];
			tipoDocumentoSelItem[0] = new SelectItem("",
					"Seleccione un tipo de documento");
			for (int i = 0; i < listaArchivosObligatorios.size(); i++) {
				DominioDetalle tae = (DominioDetalle) listaArchivosObligatorios
						.get(i);
				// tipoDocumentoSelItem[i] = new SelectItem(
				// tae.getIdentificador().getTipo(),tae.getIdentificador().getTipo()+
				// "-" + tae.getDescripcion());
				tipoDocumentoSelItem[i + 1] = new SelectItem(tae
						.getIdentificador().getTipo(), tae.getDescripcion()
						+ " - " + tae.getIdentificador().getTipo());

			}
		}
	}

	public void seleccionarTipoDocumento() {
		puedeSubirArchivos = true;
		if (tipoDocumentoSel.equals("")) {
			puedeSubirArchivos = false;
		}
		System.out.println("tipoDocumentoSel: " + tipoDocumentoSel);
	}

	private void reiniciarVariables() {
		mensajeApoyo = "";
		conArt = new ConvocatoriaArticulo();
		/*
		 * if (convocatoria != null && convocatoria.getTitulo() != null &&
		 * convocatoria.getId().intValue() == 264) {
		 * this.conArt.setModalidad("1");
		 * this.conArt.setConvocatoria(String.valueOf(convocatoria.getId()));
		 * banderaModalidad = false;
		 * 
		 * }
		 * 
		 * if (convocatoria != null && convocatoria.getTitulo() != null &&
		 * convocatoria.getId().intValue() == 265) {
		 * this.conArt.setModalidad("2");
		 * this.conArt.setConvocatoria(String.valueOf(convocatoria.getId()));
		 * banderaModalidad = true; }
		 */

		errores = new String[60];
		panelRender = new boolean[10];
		valArchivos = new boolean[3];
		mensajeArchivos = new String[3];
		panelRenderError = new boolean[60];
	}

	public void limpiar() {
		conArt = new ConvocatoriaArticulo();
		documento = new String("");
		cargarValoresIniciales();
		ocultarPaneles(0);
	}

	void processChild(List childList) {
		for (int i = 0; i < childList.size(); i++) {
			UIComponent component = (UIComponent) childList.get(i);
			try {
				UIInput input = (UIInput) component;
				input.setSubmittedValue(null);
			} catch (Exception ex) {

			}
			List childList2 = component.getChildren();
			processChild(childList2);
		}
	}

	public void cancelAction(ActionEvent event) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot viewRoot = facesContext.getViewRoot();
		List childList = viewRoot.getChildren();
		processChild(childList);
	}

	public void guardarArchivoObligatorio(FileUploadEvent event) {
		try {
			archivoObligatorio = event.getFile();
			if (archivoObligatorio.getContents() != null) {
				// dgbenitezc: se verifica que si es la copia del artículo,
				// CACA, sea .doc o .docx
				if (tipoDocumentoSel.equals("CACA")
						&& !(archivoObligatorio.getFileName().endsWith(".doc") || archivoObligatorio
								.getFileName().endsWith(".docx"))) {
					mensajeError("La copia del artículo DEBE ser adjuntada en formato Word (.doc o .docx).");
					return;
				}

				int i = archivoObligatorio.getFileName().lastIndexOf("\\");
				ArchivoConvocatoria archivo = new ArchivoConvocatoria();
				archivo.setBytes(archivoObligatorio.getContents());
				archivo.setNombre(archivoObligatorio.getFileName().substring(
						i + 1));
				archivo.setFecha(new Date());
				archivo.setTipoArchivo(tipoDocumentoSel);
				listaArchivosObligatoriosSel.add(archivo);
			}

		} catch (Exception x) {
			System.out.println(x.toString());

		}
	}

	public void eliminarArchivoObligatorio() {
		listaArchivosObligatoriosSel.remove(archivoConvocatoriaSeleccionado);
	}

	public void buscarPersona() throws SQLException {
		// reiniciarVariables();
		IdPersona id = new IdPersona();
		id.setDocumento(documento);
		id.setTipoDocumento(tipoDocumento);

		conArt.setPersonaInv((Persona) servicioPersona.obtenerPersona(id));

		if (conArt.getPersonaInv() != null) {
			if (conArt.getPersonaInv() instanceof Investigador) {
				if (conArt.getPersonaInv() instanceof InvestigadorInterno) {
					// conArt.setPersonaInv(servicioPersona
					// .obtenerInvestigadorInternoCompleto(conArt
					// .getPersonaInv().getId()));
					InvestigadorInterno investigadorInterno = (InvestigadorInterno) servicioPersona
							.obtenerInvestigadorInternoCompleto(conArt
									.getPersonaInv().getId());
					Dependencia dependencia;
					dependencia = servicioDependencia
							.obtenerDependencia(investigadorInterno.getId());
					String nombre1, nombre2, apellido1, apellido2;
					if (investigadorInterno.getTipoDedicacion() != null
							&& (investigadorInterno.getTipoDedicacion().getId()
									.equals(Investigador.EXCLUSIVA)
									|| investigadorInterno
											.getTipoDedicacion()
											.getId()
											.equals(Investigador.TIEMPOCOMPLETO)
									|| investigadorInterno.getTipoDedicacion()
											.getId()
											.equals(Investigador.MEDIOTIEMPO) || investigadorInterno
									.getTipoDedicacion().getId()
									.equals(Investigador.CATEDRA_0_4))) {
						panelRender[1] = true;
						if (investigadorInterno.getNombre1() != null) {
							nombre1 = investigadorInterno.getNombre1();
						} else {
							nombre1 = "";
						}
						if (investigadorInterno.getNombre2() != null) {
							nombre2 = investigadorInterno.getNombre2();
						} else {
							nombre2 = "";
						}
						if (investigadorInterno.getApellido1() != null) {
							apellido1 = investigadorInterno.getApellido1();
						} else {
							apellido1 = "";
						}
						if (investigadorInterno.getApellido2() != null) {
							apellido2 = investigadorInterno.getApellido2();
						} else {
							apellido2 = "";
						}
						if (investigadorInterno.getEmail() != null) {
							email = investigadorInterno.getEmail();
						} else {
							email = "";
						}
						this.nombreDocente = nombre1 + " " + nombre2 + " "
								+ apellido1 + " " + apellido2;

						this.documentoDocente = investigadorInterno.getId()
								.getDocumento();

						if (dependencia != null
								&& dependencia.getFacultad() != null) {
							this.sede = dependencia.getSede().getNombre();
							this.facultadDocente = dependencia.getFacultad()
									.getNombre();
							this.departamentoDocente = dependencia.getNombre();

							ocultarPaneles(1);
							estado = 1;
							panelRender[1] = true;

						} else {
							errores[0] = "La dependencia del investigador no tiene una facultad asociada";
							panelRenderError[0] = true;
							panelRender[1] = false;
							ocultarPaneles(0);
						}
					} else {
						errores[0] = "El investigador debe ser de dedicación exclusiva o tiempo completo de la Universidad Nacional de Colombia";
						panelRenderError[0] = true;
						ocultarPaneles(0);
						panelRender[1] = false;
					}
				} else {
					errores[0] = "El documento ingresado no corresponde a un investigador";
					panelRenderError[0] = true;
					ocultarPaneles(0);
					panelRender[1] = false;
				}
			} else {
				conArt.setPersonaInv(new Persona());
				IdPersona idP = new IdPersona();
				idP.setDocumento(documento);
				idP.setTipoDocumento(tipoDocumento);
				conArt.getPersonaInv().setId(idP);
				errores[0] = "El documento ingresado no corresponde a un investigador";
				panelRenderError[0] = true;
				ocultarPaneles(0);
				panelRender[1] = false;
			}
		} else {
			conArt.setPersonaInv(new Persona());
			IdPersona idP = new IdPersona();
			idP.setDocumento(documento);
			idP.setTipoDocumento(tipoDocumento);
			conArt.getPersonaInv().setId(idP);
			errores[0] = "El documento ingresado no existe";
			panelRenderError[0] = true;
			ocultarPaneles(0);
			panelRender[1] = false;
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void cambiarAlianza(ValueChangeEvent event) {

		String valor = (String) event.getNewValue();

		if (valor.equals("0")) {
			banderaAlianza = true;
			panelRenderError[2] = true;
			errores[2] = "";
			this.ocultarPaneles(2);

		}

		if (valor.equals("1") || valor.equals("2")) {
			banderaAlianza = true;
			panelRenderError[2] = true;
			errores[2] = "Debe anexar certificado de la radicación de(l) artículo(s) en los archivos adjuntos.";
			this.ocultarPaneles(2);
		}

		if (valor.equals("3")) {
			banderaAlianza = false;
			panelRenderError[2] = true;
			errores[2] = "No se permite diligenciar la solicitud por superar el máximo de apoyos por año.";
		}

	}

	public void verArchivo() {

		ArchivoMovilidadDA ain = (ArchivoMovilidadDA) tablaArchivos
				.getRowData();
		FacesContext ctx = FacesContext.getCurrentInstance();

		if (ain != null && ain.getArchivo() != null) {

			try {
				if (!ctx.getResponseComplete()) {
					HttpServletResponse response = (HttpServletResponse) ctx
							.getExternalContext().getResponse();
					response.setContentType("text/plain");
					response.setHeader("Content-Disposition",
							"attachment;filename=\"" + ain.getNombre() + "\"");
					ServletOutputStream out = response.getOutputStream();
					out.write(ain.getBytes());
					out.flush();
					ctx.responseComplete();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void eliminarArchivo() {
		listaArchivos.remove(tablaArchivos.getRowIndex());
	}

	public void limpiarErores() {
		for (int i = 0; i < 60; i++) {
			this.errores[i] = "";
			this.panelRenderError[i] = false;
		}
		for (int i = 0; i <= 3; i++) {
			this.mensajeArchivos[i] = "";
			this.valArchivos[i] = false;
		}
		mensajeApoyo = "";
	}

	public void limpiarErores(int min, int max) {
		for (int i = min; i <= max; i++) {
			this.errores[i] = "";
			this.panelRenderError[i] = false;
		}
	}

	public boolean validacion(int estado) {
		boolean bandera = true;

		if (estado >= 1) {
			limpiarErores(0, 10);
			/*
			 * if (this.conArt.getModalidad().equals("1")) { if
			 * (this.conArt.getDependencia() == null ||
			 * this.conArt.getDependencia().length() <= 0 ||
			 * this.conArt.getDependencia().length() > 600) { this.errores[1] =
			 * "Constrapartida NO válida"; this.panelRenderError[1] = true;
			 * bandera = false; } }
			 * 
			 * if(this.conArt.getModalidad().equals("1")){
			 * 
			 * if (this.listaEntidades == null || listaEntidades.size() <=0 ) {
			 * this.errores[1] = "Es necesario tener una contrapartida";
			 * this.panelRenderError[1] = true; bandera = false; } }
			 */

			/*
			 * if (this.conArt.getNumeroApoyos().equals("-1")) { this.errores[2]
			 * = "Número de apoyos NO válido"; this.panelRenderError[2] = true;
			 * bandera = false; }
			 * 
			 * if (this.conArt.getNumeroApoyos().equals("3")) { this.errores[2]
			 * =
			 * "No se permite diligenciar la solicitud por superar el máximo de apoyos por año"
			 * ; this.panelRenderError[2] = true; bandera = false; }
			 */

			if (this.conArt.getTitulo() == null
					|| this.conArt.getTitulo().length() <= 0
					|| this.conArt.getTitulo().length() > 1000) {
				this.errores[3] = "Título NO válido";
				this.panelRenderError[3] = true;
				bandera = false;
			}

			try {
				int palabras = Integer.parseInt(this.conArt.getPalabras());

			} catch (Exception e) {
				this.errores[4] = "Número de palabras NO válido";
				this.panelRenderError[4] = true;
				bandera = false;
			}

			/*
			 * if (this.conArt.getCoautores() == null ||
			 * this.conArt.getCoautores().length() <= 0 ||
			 * this.conArt.getCoautores().length() > 1000) { this.errores[5] =
			 * "Coautores NO válido"; this.panelRenderError[5] = true; bandera =
			 * false; }
			 */

			if (this.conArt.getIssn() == null
					|| this.conArt.getIssn().length() <= 0
					|| this.conArt.getIssn().length() > 500) {
				this.errores[6] = "ISSN NO válido";
				this.panelRenderError[6] = true;
				bandera = false;
			}

			if (this.conArt.getResumen() == null
					|| this.conArt.getResumen().length() <= 0
					|| this.conArt.getResumen().length() > 4000) {
				this.errores[7] = "Resumen NO válido";
				this.panelRenderError[7] = true;
				bandera = false;
			}

			/*
			 * if (this.coautor == null || this.coautor.equals("") ||
			 * this.coautor.length() > 2000) { this.errores[10] =
			 * "Coautores NO válidos"; this.panelRenderError[10] = true; bandera
			 * = false; }
			 */

			// archivos

			List<String> tiposArchivosAdjuntos = new ArrayList<String>();
			for (int i = 0; i < listaArchivosObligatoriosSel.size(); i++) {
				ArchivoConvocatoria archivo = new ArchivoConvocatoria();
				archivo = (ArchivoConvocatoria) listaArchivosObligatoriosSel
						.get(i);
				tiposArchivosAdjuntos.add(archivo.getTipoArchivo());

			}
			System.out.println("apoyos=" + apoyos);
			if (apoyos == 0) {
				if (tiposArchivosAdjuntos.contains("CACA")) {
					valArchivos[0] = false;
					mensajeArchivos[0] = "";
				} else {
					valArchivos[0] = true;
					bandera = false;
					mensajeError(
							"formCrearRevisionArticulo:somTiposDocumentos",
							"Por favor adjunte la copia del artículo.");
				}
				System.out.println("mensajeArchivos[0] " + mensajeArchivos[0]);
			}
			if (apoyos == 1 || apoyos == 2) {
				if (tiposArchivosAdjuntos.contains("CACA")) {
					valArchivos[0] = false;
					mensajeArchivos[0] = "";
				} else {
					mensajeError(
							"formCrearRevisionArticulo:somTiposDocumentos",
							"Por favor adjunte la copia del artículo.");
					valArchivos[0] = true;
					bandera = false;
				}

				if (tiposArchivosAdjuntos.contains("CACR")) {
					valArchivos[1] = false;
					mensajeArchivos[1] = "";
				} else {
					mensajeError(
							"formCrearRevisionArticulo:somTiposDocumentos",
							"Por favor adjunte el Certificado de Radicación.");
					valArchivos[1] = true;
					bandera = false;
				}
				System.out.println("mensajeArchivos[1] " + mensajeArchivos[1]);
			}
		}

		return bandera;

	}

	public int apoyosAnteriores() {
		int apoyos = 0;

		List listaApoyosAnteriores = servicioGeneral
				.obtenerObjetos("select ca from ConvocatoriaArticulo ca where ca.personaInv.id.documento = '"
						+ documentoDocente
						+ "' and to_char(ca.fechaRegistro,'YYYY') = to_char(sysdate,'YYYY') and ca.estado = 'APV'");

		apoyos = listaApoyosAnteriores.size();

		return apoyos;
	}

	public void validarApoyo() {
		// apoyos = apoyosAnteriores();
		/*
		 * if(apoyos > 2){ setMensajeApoyo(
		 * "Lo sentimos, el número máximo permitido de solicitudes es de tres (3) por año"
		 * ); }else{ mostrarFormulario = true; }
		 */

		mostrarFormulario = true;
	}

	public void guardar() {

		if (validacion(estado)) {

			if (estado >= 1) {
				if (this.conArt.getId() == null) {

					if (this.conArt.getModalidad().equals("0")) {
						this.conArt.setIdioma("Inglés");
					} else {
						this.conArt.setIdioma(idioma);
					}

					EntidadArticulo entidadArt = new EntidadArticulo();
					String entidadesArt = "";
					if (listaEntidades.size() > 0) {
						for (int i = 0; i < listaEntidades.size(); i++) {
							entidadArt = (EntidadArticulo) listaEntidades
									.get(i);

							entidadesArt = entidadesArt
									+ entidadArt.getNombre() + " , "
									+ entidadArt.getTipo() + "\r\n";

						}
						this.conArt.setDependencia(entidadesArt);
					} else {
						// g=false;
					}

					CoautorArticulo coautorArt = new CoautorArticulo();
					String coautoresArt = "";
					if (listaCoautores.size() > 0) {
						for (int i = 0; i < listaCoautores.size(); i++) {
							coautorArt = (CoautorArticulo) listaCoautores
									.get(i);

							coautoresArt = coautoresArt
									+ coautorArt.getNombre() + " , "
									+ coautorArt.getInstitucion() + " , "
									+ coautorArt.getTipoCoautor() + "\r\n";

						}
						this.conArt.setCoautores(coautoresArt);
					} else {
						// g=false;
					}

					Set archivoSet = new HashSet();
					for (int i = 0; i < listaArchivosObligatoriosSel.size(); i++) {
						ArchivoConvocatoria archivo = new ArchivoConvocatoria();
						archivo = (ArchivoConvocatoria) listaArchivosObligatoriosSel
								.get(i);
						archivo.setConvocatoria(String.valueOf(this.conArt
								.getId()));
						archivoSet.add(archivo);

					}
					this.conArt.setArchivos(archivoSet);

					Calendar actual = Calendar.getInstance();
					Date date = actual.getTime();
					this.conArt.setFechaRegistro(date);
					this.conArt.setEstado(EstadoProyecto.PROPUESTO);
					this.conArt.setConvocatoria(convocatoria.getId().toString());
					servicioGeneral.guardarObjeto(this.conArt);

					List listaCorreoEncargado = new ArrayList();
					List listaParametroAux;
					listaParametroAux = new ArrayList();

					Dependencia dependencia;
					dependencia = servicioDependencia
							.obtenerDependencia(personaActual.getId());

					listaCorreoEncargado = this.servicioGeneral
							.obtenerObjetos("FROM Parametro WHERE nombre = 'CON_ART_COORD'    AND DESCRIPCION= '"
									+ dependencia.getSede().getId() + "'");

					String correoEnvio = "sisii_nal@unal.edu.co";
					Persona personaActualAux2 = new Persona();
					String dirCorreo = "";

					if (listaCorreoEncargado != null
							&& listaCorreoEncargado.size() > 0) {
						Parametro paActual = (Parametro) listaCorreoEncargado
								.get(0);
						String numeroDocumento = "0";
						numeroDocumento = paActual.getValor();

						IdPersona id = new IdPersona();
						id.setDocumento(numeroDocumento);
						id.setTipoDocumento(paActual.getProfesion());
						personaActualAux2 = servicioPersona.obtenerPersona(id);
						if (personaActualAux2.getEmail() != null
								&& !personaActualAux2.getEmail().equals("")) {

							correoEnvio = personaActualAux2.getEmail();
							correoActual = cargarPlantilla(99);
						} else {
							correoActual = cargarPlantilla(100);
						}

					} else {
						correoActual = cargarPlantilla(101);
					}

					editarCorreo(personaActualAux2, this.conArt);
					Correo correo = new Correo();
					correo.setOrigen(Correo.CORREO_HERMES);
					dirCorreo = correoEnvio;
					correo.adicionarDireccion(dirCorreo);
					//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
					correo.setAsunto(correoActual.getAsunto());
					correo.setCuerpo(cuerpoCorreo);
					servicioCorreo.enviarCorreo(correo);

					// ///
					CorreoPlantilla correoActual2 = new CorreoPlantilla();
					if (personaActual.getEmail() != null
							&& !personaActual.getEmail().equals("")) {
						correoActual2 = cargarPlantilla(102);
					} else {
						correoActual2 = cargarPlantilla(100);
					}

					Correo correo2 = new Correo();
					correo2.setOrigen(Correo.CORREO_HERMES);
					correo2.adicionarDireccion(personaActual.getEmail());
					//correo2.adicionarCopiaOculta(Correo.CORREO_HERMES);
					correo2.setAsunto(correoActual2.getAsunto());
					String cuerpoCorreo2 = correoActual2.getCuerpo();
					cuerpoCorreo2 = cuerpoCorreo2.replaceAll("<<IDARTICULO>>",
							String.valueOf(conArt.getId()));
					String investigador = personaActual.getNombre1() + " "
							+ personaActual.getApellido1();
					cuerpoCorreo2 = cuerpoCorreo2.replaceAll(
							"<<INVESTIGADOR>>", investigador);
					correo2.setCuerpo(cuerpoCorreo2);
					servicioCorreo.enviarCorreo(correo2);

					mensajeInfo("Solicitud creada satisfactoriamente, con el identificador "
							+ conArt.getId() + ".");
					this.limpiar();
					personaActual = (Persona) sesion.getAttribute("persona");
					// this.errores[0] = "Solicitud creada satisfactoriamente.";
					this.panelRenderError[0] = true;
					mostrarFormulario = false;
					panelRender[8] = false;
					panelRender[1] = false;

				}

			}

		} else {
			mensajeError(msgError);
		}

	}

	public String editarCorreo(Persona personaAux, ConvocatoriaArticulo con) {
		try {
			String coinvNombre = "";

			String correo = correoActual.getCuerpo();

			String investigador = "";

			investigador = personaAux.getNombre1() + " "
					+ personaAux.getApellido1() + " "
					+ personaAux.getApellido2();

			correo = correo.replaceAll("<<INVESTIGADOR>>", investigador);

			correo = correo
					.replaceAll("<<IDARTICULO>>", con.getId().toString());
			cuerpoCorreo = correo;

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return "";
	}

	public String editarCorreo(Persona personaAux, String id) {
		try {
			String coinvNombre = "";

			String correo = correoActual.getCuerpo();

			String investigador = "";

			investigador = personaAux.getNombre1() + " "
					+ personaAux.getApellido1() + " "
					+ personaAux.getApellido2();
			correo = correo.replaceAll("<<INVESTIGADOR>>", investigador);

			correo = correo.replaceAll("<<IDAVAL>>", id);
			// correo = correo.replaceAll("<<TIPO>>", mov.getTipoMovilidad()
			// .getNombre());

			cuerpoCorreo = correo;

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return "";
	}

	public CorreoPlantilla cargarPlantilla(int cod_id) {

		CorreoPlantilla correoActualAux = new CorreoPlantilla();
		CorreoPlantilla a = new CorreoPlantilla();

		List lista = servicioGeneral
				.obtenerObjetos("select c from CorreoPlantilla c where c.id='"
						+ cod_id + "'");
		if (lista != null && lista.size() > 0) {
			correoActualAux = (CorreoPlantilla) lista.get(0);
		}

		return correoActualAux;
	}

	// CREADO Y PATENTADO POR ING. CANTOR
	public void ocultarPaneles(int nivel) {
		for (int i = 0; i < 10; i++) {
			if (i < nivel) {
				panelRender[i] = true;
				;
			} else {
				panelRender[i] = false;

			}

		}
	}

	public String atras() {
		sesion.removeAttribute("ManejadorCrearEditarArticulo");
		return "misProyectos";
	}

	private void cargarValoresIniciales() {
		errores = new String[60];
		panelRender = new boolean[10];
		panelRenderError = new boolean[60];
		valArchivos = new boolean[3];
		mensajeArchivos = new String[3];
		personaActual = (Persona) sesion.getAttribute("persona");
		// programa = "";

		listaArchivos = new ArrayList();
		listaArchivosObligatoriosSel = new ArrayList();
		listaArchivosObligatorios = new ArrayList();

		valueGuardar = "Guardar";

		ocultarPaneles(0);
		cargarTipoArchivos();

		numeroApoyos = new Vector();
		numeroApoyos.add(new SelectItem("-1", "Seleccione"));
		numeroApoyos.add(new SelectItem("0", "0"));
		numeroApoyos.add(new SelectItem("1", "1"));
		numeroApoyos.add(new SelectItem("2", "2"));
		numeroApoyos.add(new SelectItem("3", "3 o más"));

	}

	public void adicionarEntidad() {
		EntidadArticulo entidadArt = new EntidadArticulo();

		try {

			if (this.entidad == null || this.entidad.equals("")
					|| this.entidad.length() > 2000) {
				// this.errores[27] = "La actividad NO es válida";
				// this.panelRenderError[27] = true;
			} else {
				entidadArt.setNombre(entidad);
			}

			if (this.tipoEntidad == null || this.tipoEntidad.equals("")
					|| this.tipoEntidad.length() > 2000) {
				// this.errores[27] = "La actividad NO es válida";
				// this.panelRenderError[27] = true;
			} else {
				entidadArt.setTipo(tipoEntidad);
			}

			// if (this.panelRenderError[27] == false
			// && this.panelRenderError[28] == false) {
			this.listaEntidades.add(entidadArt);
			// }
			this.entidad = "";

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void eliminarEntidad() {
		listaEntidades.remove(tablaEntidadesSel.getRowIndex());
	}

	public void adicionarCoautor() {
		CoautorArticulo coautorArt = new CoautorArticulo();

		try {
			this.errores[10] = "";
			this.panelRenderError[10] = false;

			if (this.coautor == null || this.coautor.equals("")
					|| this.coautor.length() > 2000) {
				this.errores[10] = "El nombre no es válido. ";
				this.panelRenderError[10] = true;
			} else {
				coautorArt.setNombre(coautor);
			}

			if (this.institucionCoautor == null
					|| this.institucionCoautor.equals("")
					|| this.institucionCoautor.length() > 2000) {
				this.errores[10] += "La institución no es válida. ";
				this.panelRenderError[10] = true;
			} else {
				coautorArt.setInstitucion(institucionCoautor);
			}

			coautorArt.setTipoCoautor(tipoCoautor);

			if (!this.panelRenderError[10]) {
				this.listaCoautores.add(coautorArt);
				this.coautor = "";
				this.institucionCoautor = "";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void eliminarCoautor() {
		listaCoautores.remove(coautorArticuloSeleccionado);
	}

	public void cambiarForm() {
		String modalidadEv = conArt.getModalidad();
		System.out.println("modalidadEv: " + modalidadEv);
		if (modalidadEv.equals("Revisión de estilo")) {
			panelRender[8] = false;
		} else if (modalidadEv.equals("Traducción")) {
			panelRender[8] = true;
		}
	}

	public String[] getErrores() {
		return errores;
	}

	public void setErrores(String[] errores) {
		this.errores = errores;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public SelectItem[] getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}

	public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
		this.tipoDocumentoItem = tipoDocumentoItem;
	}

	public HtmlDataTable getTablaArchivos() {
		return tablaArchivos;
	}

	public void setTablaArchivos(HtmlDataTable tablaArchivos) {
		this.tablaArchivos = tablaArchivos;
	}

	public List getListaArchivos() {
		return listaArchivos;
	}

	public void setListaArchivos(List listaArchivos) {
		this.listaArchivos = listaArchivos;
	}

	public boolean[] getPanelRender() {
		return panelRender;
	}

	public void setPanelRender(boolean[] panelRender) {
		this.panelRender = panelRender;
	}

	public boolean[] getPanelRenderError() {
		return panelRenderError;
	}

	public void setPanelRenderError(boolean[] panelRenderError) {
		this.panelRenderError = panelRenderError;
	}

	/*
	 * public List getModalidad() { return modalidad; }
	 * 
	 * public void setModalidad(List modalidad) { this.modalidad = modalidad; }
	 */

	public HtmlDataTable getTablaActividadesSel() {
		return tablaActividadesSel;
	}

	public void setTablaActividadesSel(HtmlDataTable tablaActividadesSel) {
		this.tablaActividadesSel = tablaActividadesSel;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	public String getFacultadDocente() {
		return facultadDocente;
	}

	public void setFacultadDocente(String facultadDocente) {
		this.facultadDocente = facultadDocente;
	}

	public String getDepartamentoDocente() {
		return departamentoDocente;
	}

	public void setDepartamentoDocente(String departamentoDocente) {
		this.departamentoDocente = departamentoDocente;
	}

	public String getNombreDocente() {
		return nombreDocente;
	}

	public void setNombreDocente(String nombreDocente) {
		this.nombreDocente = nombreDocente;
	}

	public String getDocumentoDocente() {
		return documentoDocente;
	}

	public void setDocumentoDocente(String documentoDocente) {
		this.documentoDocente = documentoDocente;
	}

	public String getDescripcionActividad() {
		return descripcionActividad;
	}

	public void setDescripcionActividad(String descripcionActividad) {
		this.descripcionActividad = descripcionActividad;
	}

	public String getNombreActividad() {
		return nombreActividad;
	}

	public void setNombreActividad(String nombreActividad) {
		this.nombreActividad = nombreActividad;
	}

	public Date getFechaActividad() {
		return fechaActividad;
	}

	public void setFechaActividad(Date fechaActividad) {
		this.fechaActividad = fechaActividad;
	}

	public String getDuracionActividad() {
		return duracionActividad;
	}

	public void setDuracionActividad(String duracionActividad) {
		this.duracionActividad = duracionActividad;
	}

	public SelectItem[] getTipoDocumentoSelItem() {
		return tipoDocumentoSelItem;
	}

	public void setTipoDocumentoSelItem(SelectItem[] tipoDocumentoSelItem) {
		this.tipoDocumentoSelItem = tipoDocumentoSelItem;
	}

	public String getTipoDocumentoSel() {
		return tipoDocumentoSel;
	}

	public void setTipoDocumentoSel(String tipoDocumentoSel) {
		this.tipoDocumentoSel = tipoDocumentoSel;
	}

	public UploadedFile getArchivoObligatorio() {
		return archivoObligatorio;
	}

	public void setArchivoObligatorio(UploadedFile archivoObligatorio) {
		this.archivoObligatorio = archivoObligatorio;
	}

	public List getListaArchivosObligatoriosSel() {
		return listaArchivosObligatoriosSel;
	}

	public void setListaArchivosObligatoriosSel(
			List listaArchivosObligatoriosSel) {
		this.listaArchivosObligatoriosSel = listaArchivosObligatoriosSel;
	}

	public List getGruposInvestigador() {
		return gruposInvestigador;
	}

	public void setGruposInvestigador(List gruposInvestigador) {
		this.gruposInvestigador = gruposInvestigador;
	}

	public ConvocatoriaArticulo getConArt() {
		return conArt;
	}

	public void setConArt(ConvocatoriaArticulo conArt) {
		this.conArt = conArt;
	}

	public List getnumeroApoyos() {
		return numeroApoyos;
	}

	public void setnumeroApoyos(List numeroApoyos) {
		this.numeroApoyos = numeroApoyos;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public boolean isBanderaModalidad() {
		return banderaModalidad;
	}

	public void setBanderaModalidad(boolean banderaModalidad) {
		this.banderaModalidad = banderaModalidad;
	}

	public boolean isBanderaAlianza() {
		return banderaAlianza;
	}

	public void setBanderaAlianza(boolean banderaAlianza) {
		this.banderaAlianza = banderaAlianza;
	}

	public List getListaGrupos() {
		return listaGrupos;
	}

	public void setListaGrupos(List listaGrupos) {
		this.listaGrupos = listaGrupos;
	}

	public HtmlDataTable getTablaGruposSel() {
		return tablaGruposSel;
	}

	public void setTablaGruposSel(HtmlDataTable tablaGruposSel) {
		this.tablaGruposSel = tablaGruposSel;
	}

	public List getListaPrograma() {
		return listaPrograma;
	}

	public void setListaPrograma(List listaPrograma) {
		this.listaPrograma = listaPrograma;
	}

	public InvestigadorProyecto getInvestigadorProyectoNuevo() {
		return investigadorProyectoNuevo;
	}

	public void setInvestigadorProyectoNuevo(
			InvestigadorProyecto investigadorProyectoNuevo) {
		this.investigadorProyectoNuevo = investigadorProyectoNuevo;
	}

	public SelectItem[] getTipoDocumentoGrupoItem() {
		return tipoDocumentoGrupoItem;
	}

	public void setTipoDocumentoGrupoItem(SelectItem[] tipoDocumentoGrupoItem) {
		this.tipoDocumentoGrupoItem = tipoDocumentoGrupoItem;
	}

	public String getDocumentoLiderGrupo() {
		return documentoLiderGrupo;
	}

	public void setDocumentoLiderGrupo(String documentoLiderGrupo) {
		this.documentoLiderGrupo = documentoLiderGrupo;
	}

	public String getTipoDocumentoLiderGrupo() {
		return tipoDocumentoLiderGrupo;
	}

	public void setTipoDocumentoLiderGrupo(String tipoDocumentoLiderGrupo) {
		this.tipoDocumentoLiderGrupo = tipoDocumentoLiderGrupo;
	}

	public List getListaGruposSel() {
		return listaGruposSel;
	}

	public void setListaGruposSel(List listaGruposSel) {
		this.listaGruposSel = listaGruposSel;
	}

	public boolean isBanderaCandidatos() {
		return banderaCandidatos;
	}

	public void setBanderaCandidatos(boolean banderaCandidatos) {
		this.banderaCandidatos = banderaCandidatos;
	}

	public List getPaises() {
		return paises;
	}

	public void setPaises(List paises) {
		this.paises = paises;
	}

	public String getPaisProcedencia() {
		return paisProcedencia;
	}

	public void setPaisProcedencia(String paisProcedencia) {
		this.paisProcedencia = paisProcedencia;
	}

	public List getPaisesEstudio() {
		return paisesEstudio;
	}

	public void setPaisesEstudio(List paisesEstudio) {
		this.paisesEstudio = paisesEstudio;
	}

	public String getPaisEstudio() {
		return paisEstudio;
	}

	public void setPaisEstudio(String paisEstudio) {
		this.paisEstudio = paisEstudio;
	}

	public List getListaCandidatosSel() {
		return listaCandidatosSel;
	}

	public void setListaCandidatosSel(List listaCandidatosSel) {
		this.listaCandidatosSel = listaCandidatosSel;
	}

	public HtmlDataTable getTablaCandidatosSel() {
		return tablaCandidatosSel;
	}

	public void setTablaCandidatosSel(HtmlDataTable tablaCandidatosSel) {
		this.tablaCandidatosSel = tablaCandidatosSel;
	}

	public CandidatoPosdoctorado getCandidato() {
		return candidato;
	}

	public void setCandidato(CandidatoPosdoctorado candidato) {
		this.candidato = candidato;
	}

	public ActividadPosdoctorado getActividad() {
		return actividad;
	}

	public void setActividad(ActividadPosdoctorado actividad) {
		this.actividad = actividad;
	}

	public List getListaActividadesSel() {
		return listaActividadesSel;
	}

	public void setListaActividadesSel(List listaActividadesSel) {
		this.listaActividadesSel = listaActividadesSel;
	}

	public List getListaMeses() {
		return listaMeses;
	}

	public void setListaMeses(List listaMeses) {
		this.listaMeses = listaMeses;
	}

	public String getValueGuardar() {
		return valueGuardar;
	}

	public void setValueGuardar(String valueGuardar) {
		this.valueGuardar = valueGuardar;
	}

	public String getNombreModalidad() {
		return nombreModalidad;
	}

	public void setNombreModalidad(String nombreModalidad) {
		this.nombreModalidad = nombreModalidad;
	}

	public CorreoPlantilla getCorreoActual() {
		return correoActual;
	}

	public void setCorreoActual(CorreoPlantilla correoActual) {
		this.correoActual = correoActual;
	}

	public String getCuerpoCorreo() {
		return cuerpoCorreo;
	}

	public void setCuerpoCorreo(String cuerpoCorreo) {
		this.cuerpoCorreo = cuerpoCorreo;
	}

	public List getListaArchivosObligatorios() {
		return listaArchivosObligatorios;
	}

	public void setListaArchivosObligatorios(List listaArchivosObligatorios) {
		this.listaArchivosObligatorios = listaArchivosObligatorios;
	}

	public void setListaTipoModalidad(List listaTipoModalidad) {
		this.listaTipoModalidad = listaTipoModalidad;
	}

	public List getListaTipoModalidad() {
		return listaTipoModalidad;
	}

	public List getListaTipoEntidad() {
		return listaTipoEntidad;
	}

	public void setListaTipoEntidad(List listaTipoEntidad) {
		this.listaTipoEntidad = listaTipoEntidad;
	}

	public List getListaIdiomas() {
		return listaIdiomas;
	}

	public void setListaIdiomas(List listaIdiomas) {
		this.listaIdiomas = listaIdiomas;
	}

	public void setTablaEntidadesSel(HtmlDataTable tablaEntidadesSel) {
		this.tablaEntidadesSel = tablaEntidadesSel;
	}

	public HtmlDataTable getTablaEntidadesSel() {
		return tablaEntidadesSel;
	}

	public void setTipoEntidad(String tipoEntidad) {
		this.tipoEntidad = tipoEntidad;
	}

	public String getTipoEntidad() {
		return tipoEntidad;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public String getIdioma() {
		return idioma;
	}

	public List getListaEntidades() {
		return listaEntidades;
	}

	public void setListaEntidades(List listaEntidades) {
		this.listaEntidades = listaEntidades;
	}

	public List getListaCoautores() {
		return listaCoautores;
	}

	public void setListaCoautores(List listaCoautores) {
		this.listaCoautores = listaCoautores;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public String getCoautor() {
		return coautor;
	}

	public void setCoautor(String coautor) {
		this.coautor = coautor;
	}

	public String getInstitucionCoautor() {
		return institucionCoautor;
	}

	public void setInstitucionCoautor(String institucionCoautor) {
		this.institucionCoautor = institucionCoautor;
	}

	public boolean isMostrarFormulario() {
		return mostrarFormulario;
	}

	public void setMostrarFormulario(boolean mostrarFormulario) {
		this.mostrarFormulario = mostrarFormulario;
	}

	public void setMensajeApoyo(String mensajeApoyo) {
		this.mensajeApoyo = mensajeApoyo;
	}

	public String getMensajeApoyo() {
		return mensajeApoyo;
	}

	public void setValArchivos(boolean valArchivos[]) {
		this.valArchivos = valArchivos;
	}

	public boolean[] getValArchivos() {
		return valArchivos;
	}

	public void setMensajeArchivos(String mensajeArchivos[]) {
		this.mensajeArchivos = mensajeArchivos;
	}

	public String[] getMensajeArchivos() {
		return mensajeArchivos;
	}

	public List getNumeroApoyos() {
		return numeroApoyos;
	}

	public void setNumeroApoyos(List numeroApoyos) {
		this.numeroApoyos = numeroApoyos;
	}

	public List getListaTipoCoautor() {
		return listaTipoCoautor;
	}

	public void setListaTipoCoautor(List listaTipoCoautor) {
		this.listaTipoCoautor = listaTipoCoautor;
	}

	public String getTipoCoautor() {
		return tipoCoautor;
	}

	public void setTipoCoautor(String tipoCoautor) {
		this.tipoCoautor = tipoCoautor;
	}

	public int getApoyos() {
		return apoyos;
	}

	public void setApoyos(int apoyos) {
		this.apoyos = apoyos;
	}

	public List getListaAreas() {
		return listaAreas;
	}

	public void setListaAreas(List listaAreas) {
		this.listaAreas = listaAreas;
	}

	public List getListaIdiomas2() {
		return listaIdiomas2;
	}

	public void setListaIdiomas2(List listaIdiomas2) {
		this.listaIdiomas2 = listaIdiomas2;
	}

	/**
	 * @param coautorArticuloSeleccionado
	 *            the coautorArticuloSeleccionado to set
	 */
	public void setCoautorArticuloSeleccionado(
			CoautorArticulo coautorArticuloSeleccionado) {
		this.coautorArticuloSeleccionado = coautorArticuloSeleccionado;
	}

	/**
	 * @param archivoConvocatoriaSeleccionado
	 *            the archivoConvocatoriaSeleccionado to set
	 */
	public void setArchivoConvocatoriaSeleccionado(
			ArchivoConvocatoria archivoConvocatoriaSeleccionado) {
		this.archivoConvocatoriaSeleccionado = archivoConvocatoriaSeleccionado;
	}

	/**
	 * @return the puedeSubirArchivos
	 */
	public boolean isPuedeSubirArchivos() {
		return puedeSubirArchivos;
	}

}
