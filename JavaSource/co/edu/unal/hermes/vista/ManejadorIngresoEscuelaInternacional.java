package co.edu.unal.hermes.vista;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.myfaces.component.html.ext.HtmlDataTable;

import co.edu.unal.hermes.modelo.InscripcionEscuelaInternacional;
import co.edu.unal.hermes.modelo.ModuloCursoEscuela;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoPersonaEscuela;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.vista.evaluadores.ProyectoCoordinador;
public class ManejadorIngresoEscuelaInternacional extends ManejadorBase{
	
	
	//Ing. wam² - Para Escuela Internacional 2011
	private SelectItem[] tipoModulo;
	private String tipoModuloSel;
	
	private List listaModulos;
	
	private String convocatoriaSel;
	private boolean mostrarModulo;
	
	private Long totalPagar;
	private String respuesta;
	private boolean mostrarCursos;
	
	private boolean mostrarUniversidad;
	
	
	private String          convocatoria;
	private SelectItem[]    coordinadoresItem; 		//Posibles recomendaciones para el proyecto
	private SelectItem[]    convocatoriaItem;
	private SelectItem[]    convocatoriaPadreItem;
	private SelectItem[]    facultadItem;  			//20091021-Búsqueda de proyectos por facultad
	private SelectItem[]    sedeItem;
	private SelectItem[]    coordinadorNuevoItem;  	//20091021-Búsqueda de proyectos por facultad
	private List            listaCoordinadores;
	private List            listaConvocatorias;
	private List            listaConvocatoriasPadre;
	private List            listaProyectosCoordinador;
	private List            listaProyectoCoordinador;
	private List            listaProyectosCoordinadorAux;
	private List		    listaProyectosCoordinadorConsulta;
	private List			listaProyectosCoordinadorConsultaAux;
	private List            listaProyectos;	
    private HtmlDataTable   tablaProyectos; 
    private HtmlDataTable   tablaProyecto;
    private HtmlDataTable   tablaProyectoConsulta;
    private String          coordinadorActual;
    private String          nombreModalidad;
    private String          convocatoriaPadre;
    private String          mensajeConvocatoria;
    private boolean         mostrarTabla;
    private boolean         mostrarProyecto;
    private boolean         mostrarSelCoordinador;
    private boolean			mostrarProyectoCoordinador;
    private boolean			mostrarSumaProyectos;
    private String          mensajeAsignacion;
    private String          proyectoId;
    private String          mensajeAsignacionId;

    private String			sede;
    private Persona         person;
    private String          coordinadorNuevo;
    private int       		total;
    private int				totalAsignados;
    private int				totalSinAsignar;
    
    private String nombres;
    private String apellidos;
    private String tipoDocumento;
    private String documento;
    
    private String telefonoFijo;
	private String telefonoMovil;
	private String facultad;
    
    private SelectItem[] tipoDocumentoItem;
    private String tipoAspirante;
    private SelectItem[] tipoAspiranteItem;
    
    private Long semestre;
    private String carrera;
    private String universidad;
    private String promedio;
    private SelectItem[] promedioItem;
    
    private String estUn;
    private SelectItem[] estUnItem;
    
    private String semestreAux;
    private String completoAux;
    private SelectItem[] SemestreAuxItem;
    private SelectItem[] CompletoAuxItem;
    
    private String correo;
    private String actividad;
    private String institucion;
    private boolean mostrarOtros=false;
    private String mensajeInscripcion="";
    
    private String ciudad;
    private String direccion;
    
    
    
    private List listaValoresCursos;
    
    public SelectItem[] getCompletoAuxItem() {
		return CompletoAuxItem;
	}

	public void setCompletoAuxItem(SelectItem[] completoAuxItem) {
		CompletoAuxItem = completoAuxItem;
	}

	private HtmlDataTable tablaValoresCursos;
    
    
    public String getExperiencia() {
		return experiencia;
	}

	public void setExperiencia(String experiencia) {
		this.experiencia = experiencia;
	}

	public String getRelacion() {
		return relacion;
	}

	public void setRelacion(String relacion) {
		this.relacion = relacion;
	}

	private String experiencia;
    private String relacion;
    
    public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
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

	public String getMensajeAsignacionId() {
		return mensajeAsignacionId;
	}

	public void setMensajeAsignacionId(String mensajeAsignacionId) {
		this.mensajeAsignacionId = mensajeAsignacionId;
	}

	public String getProyectoId() {
		return proyectoId;
	}

	public void setProyectoId(String proyectoId) {
		this.proyectoId = proyectoId;
	}

	public String getMensajeAsignacion() {
		return mensajeAsignacion;
	}

	public void setMensajeAsignacion(String mensajeAsignacion) {
		this.mensajeAsignacion = mensajeAsignacion;
	}

	public boolean isMostrarTabla() {
		return mostrarTabla;
	}

	public void setMostrarTabla(boolean mostrarTabla) {
		this.mostrarTabla = mostrarTabla;
	}

	public String getMensajeConvocatoria() {
		return mensajeConvocatoria;
	}

	public void setMensajeConvocatoria(String mensajeConvocatoria) {
		this.mensajeConvocatoria = mensajeConvocatoria;
	}

	public ManejadorIngresoEscuelaInternacional(){
		
		mostrarModulo=true;
		mostrarUniversidad=true;
		totalPagar= new Long("0");
		respuesta="SI";
		mostrarCursos=false;
		
		tablaValoresCursos = new HtmlDataTable();
		//listaValoresCursos	     = new ArrayList();
		listaModulos             = new ArrayList();
    	listaConvocatorias       = new ArrayList();
    	listaConvocatoriasPadre  = new ArrayList();
    	listaProyectos           = new ArrayList();
    	listaProyectosCoordinadorAux= new ArrayList();
    	listaProyectosCoordinador= new ArrayList();
    	listaProyectoCoordinador = new ArrayList();
    	listaCoordinadores       = new ArrayList();
    	listaProyectosCoordinadorConsulta = new ArrayList();
    	listaProyectosCoordinadorConsultaAux = new ArrayList();
    	tablaProyectos           = new HtmlDataTable();  
    	tablaProyecto            = new HtmlDataTable();
    	tablaProyectoConsulta    = new HtmlDataTable();
    	personaActual = (Persona) sesion.getAttribute("persona");
    	total= 0;
    	mensajeInscripcion="";
    	List listaTipoDocumento = new ArrayList();
		List listaAspirante = new ArrayList();

		listaTipoDocumento = servicioGeneral
				.obtenerListaObjetos("TipoDocumento");
		
		
		if (listaTipoDocumento != null ){
			tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
		
			for (int i = 0; i < listaTipoDocumento.size(); i++) {
				TipoDocumento mce1 = (TipoDocumento) listaTipoDocumento.get(i);
				tipoDocumentoItem[i] = new SelectItem(mce1.getId(),
						mce1.getNombre());
			
			}
		}
		
		
		listaAspirante = servicioGeneral.obtenerListaObjetos("TipoPersonaEscuela");
	

		if (listaAspirante != null ){
			tipoAspiranteItem = new SelectItem[listaAspirante.size()];
		
			for (int i = 0; i < listaAspirante.size(); i++) {
				TipoPersonaEscuela mce1 = (TipoPersonaEscuela) listaAspirante.get(i);
				tipoAspiranteItem[i] = new SelectItem(mce1.getId().toString(),
						mce1.getNombre());
			
			}
		}
		
		
		
		promedioItem = new SelectItem[16];
		promedioItem[0] = new SelectItem("3,5", "3,5");
		promedioItem[1] = new SelectItem("3,6", "3,6");
		promedioItem[2] = new SelectItem("3,7", "3,7");
		promedioItem[3] = new SelectItem("3,8", "3,8");
		promedioItem[4] = new SelectItem("3,9", "3,9");
		promedioItem[5] = new SelectItem("4,0", "4,0");
		promedioItem[6] = new SelectItem("4,1", "4,1");
		promedioItem[7] = new SelectItem("4,2", "4,2");
		promedioItem[8] = new SelectItem("4,3", "4,3");
    
		promedioItem[9] = new SelectItem("4,4", "4,4");
		promedioItem[10] = new SelectItem("4,5", "4,5");
		promedioItem[11] = new SelectItem("4,6", "4,6");
		promedioItem[12] = new SelectItem("4,7", "4,7");
		promedioItem[13] = new SelectItem("4,8", "4,8");

		promedioItem[14] = new SelectItem("4,9", "4,9");
		promedioItem[15] = new SelectItem("5,0", "5,0");

		
		estUnItem = new SelectItem[3];
		estUnItem[0] = new SelectItem("0", "-----------");
		estUnItem[1] = new SelectItem("1", "Créditos");
		estUnItem[2] = new SelectItem("2", "Certificado");
		
		
		SemestreAuxItem = new SelectItem[10];
		SemestreAuxItem[0] = new SelectItem("1", "1");
		SemestreAuxItem[1] = new SelectItem("2", "2");
		SemestreAuxItem[2] = new SelectItem("3", "3");
		SemestreAuxItem[3] = new SelectItem("4", "4");
		SemestreAuxItem[4] = new SelectItem("5", "5");
		SemestreAuxItem[5] = new SelectItem("6", "6");
		SemestreAuxItem[6] = new SelectItem("7", "7");
		SemestreAuxItem[7] = new SelectItem("8", "8");
		SemestreAuxItem[8] = new SelectItem("9", "9");
		SemestreAuxItem[9] = new SelectItem("10", "10");
		
	
		CompletoAuxItem = new SelectItem[2];
		CompletoAuxItem[0] = new SelectItem("SI", "SI");
		CompletoAuxItem[1] = new SelectItem("NO", "NO");
		
		
		
    	cargarConvocatorias();
    	cargarTipoModulo();
    	
    	//wam²
    	//Para mostrar o no la selección de un coordinador
    	//General, para todas las convocatorias
    
    }
	
	
	

	public boolean isMostrarUniversidad() {
		return mostrarUniversidad;
	}

	public void setMostrarUniversidad(boolean mostrarUniversidad) {
		this.mostrarUniversidad = mostrarUniversidad;
	}

	private void cargarTipoModulo() {

		tipoModulo = new SelectItem[listaConvocatorias.size()];
		for (int i = 0; i < listaConvocatorias.size(); i++) {
			ModuloCursoEscuela td = (ModuloCursoEscuela) listaConvocatorias.get(i);
				tipoModulo[i] = new SelectItem(td.getId(),
						td.getNombre());
		}

		tipoModuloSel = ((ModuloCursoEscuela) listaConvocatorias.get(0))
		.getId();
	}
	
	

    
    private void cargarConvocatorias(){	    
    	mensajeInscripcion="";
	    List listaCurso;
	    List listaCursoAux;
	    listaCurso = new ArrayList();
	    listaCursoAux = new ArrayList();
	    
	    ModuloCursoEscuela mce = new ModuloCursoEscuela();
	    mce.setId(ModuloCursoEscuela.RAIZ);
	    listaCursoAux = servicioGeneral.obtenerHijos(mce);	
	    
	    String moduloInicial="1";
	    mensajeInscripcion="";
	    
	    
	    
		if (listaCursoAux != null ){
			for (int i = 0; i < listaCursoAux.size(); i++) {
				ModuloCursoEscuela mce1 = (ModuloCursoEscuela) listaCursoAux.get(i);
				if( mce1.getTipo().equals("1")){
					listaCurso.add(mce1);
				}
			}
		}
	    	  		
		if (listaCurso != null ){
			convocatoriaPadreItem = new SelectItem[listaCurso.size()];
			int z=0;
			for (int i = 0; i < listaCurso.size(); i++) {
				ModuloCursoEscuela mce1 = (ModuloCursoEscuela) listaCurso.get(i);
				convocatoriaPadreItem[i] = new SelectItem(mce1.getId(),	mce1.getNombre());
				moduloInicial = mce1.getId();
				z=z+1;
			}
		}
	    
	    ModuloCursoEscuela mceAux = new ModuloCursoEscuela();
	    mceAux.setId(moduloInicial);
	   	 
    	List listaConvocatoriasAux;
    	listaConvocatoriasAux= new ArrayList();
    	
		listaConvocatorias.clear();
		//listaConvocatorias = servicioGeneral.obtenerHijos(mceAux);
		listaConvocatoriasAux = servicioGeneral.obtenerHijos(mceAux);
		
		if(listaConvocatoriasAux != null && listaConvocatoriasAux.size()>0){
		    for (int i = 0; i < listaConvocatoriasAux.size(); i++) {
		    	ModuloCursoEscuela mce2= (ModuloCursoEscuela) listaConvocatoriasAux.get(i);
		    	if( mce2.getTipo().equals("1")){
		    		listaConvocatorias.add(mce2);
		    	}
		    }	    
		}
    }
    
    
    private void cargarCursos(){	    
    	mensajeInscripcion="";
	    List listaCurso;
	    List listaCursoAux;
	    listaCurso = new ArrayList();
	    listaCursoAux = new ArrayList();
	    
	    ModuloCursoEscuela mce = new ModuloCursoEscuela();
	    mce.setId(ModuloCursoEscuela.RAIZ);
	    listaCursoAux = servicioGeneral.obtenerHijos(mce);	
	    
	    String moduloInicial="1";
	    mensajeInscripcion="";
	    
	    
	    
		if (listaCursoAux != null ){
			for (int i = 0; i < listaCursoAux.size(); i++) {
				ModuloCursoEscuela mce1 = (ModuloCursoEscuela) listaCursoAux.get(i);
				if( mce1.getTipo().equals("1")){
					listaCurso.add(mce1);
				}
			}
		}
	    	  		
		if (listaCurso != null ){
			convocatoriaPadreItem = new SelectItem[listaCurso.size()];
			int z=0;
			for (int i = 0; i < listaCurso.size(); i++) {
				ModuloCursoEscuela mce1 = (ModuloCursoEscuela) listaCurso.get(i);
				convocatoriaPadreItem[i] = new SelectItem(mce1.getId(),	mce1.getNombre());
				if(i==0){
					moduloInicial = mce1.getId();
				}
				
				z=z+1;
			}
		}
	    
	    ModuloCursoEscuela mceAux = new ModuloCursoEscuela();
	    mceAux.setId(moduloInicial);
	   	 
    	List listaConvocatoriasAux;
    	listaConvocatoriasAux= new ArrayList();
    	
		listaConvocatorias.clear();
		//listaConvocatorias = servicioGeneral.obtenerHijos(mceAux);
		listaConvocatoriasAux = servicioGeneral.obtenerHijos(mceAux);
		if(listaConvocatoriasAux != null && listaConvocatoriasAux.size()>0){
		    for (int i = 0; i < listaConvocatoriasAux.size(); i++) {
		    	ModuloCursoEscuela mce2= (ModuloCursoEscuela) listaConvocatoriasAux.get(i);
		    	if( mce2.getTipo().equals("1")){
		    		if (respuesta.equals("SI")){
			    		if( mce2.getTipo().equals("1") && mce2.getCompleto().equals("1")){
				    		
				    		listaConvocatorias.add(mce2);
				    	}
			    	}else{
			    			if( mce2.getTipo().equals("1") && mce2.getCompleto().equals("0")){
				    		
				    		listaConvocatorias.add(mce2);
				    	}
			    	}
		    	}
		    }	    
		}
    }

    
    public void cambiarPersona(ValueChangeEvent event) {
    	
    	String tPersona=(String)event.getNewValue();
    	mensajeInscripcion="";
    	
    	 if (tPersona.equals("0") || tPersona.equals("1")){
    		 universidad="UNIVERSIDAD NACIONAL DE COLOMBIA";
    		 mostrarUniversidad=false;
    	 }else{
    		 universidad="";
    		 mostrarUniversidad=true;
    	 }
    	
	    if (tPersona.equals("2") || tPersona.equals("5")){
	    	mostrarOtros= true;
	    	semestre=new Long("0");
	    	carrera="";
	    	universidad="";
	    	estUn="";
	    }else{
	    	mostrarOtros=false;
	    	actividad="";
	    	institucion="";
	    }
	   	
    }

    public boolean isMostrarOtros() {
		return mostrarOtros;
	}

	public void setMostrarOtros(boolean mostrarOtros) {
		this.mostrarOtros = mostrarOtros;
	}

	public void cambiarCompleto (ValueChangeEvent event) {
		
		respuesta ="";
		
		
		mostrarModulo=true;
		
		respuesta= (String) event.getNewValue();
		mensajeInscripcion="";
	    List listaCurso;
	    listaCurso = new ArrayList();
	    ModuloCursoEscuela mce = new ModuloCursoEscuela();
	    mce.setId((String) event.getNewValue());

    	    	
		List lst;
		lst = new ArrayList();

		String Qsql = "from ModuloCursoEscuela where id = '"
			+ convocatoriaSel + "'";
						
		lst=servicioGeneral.obtenerObjetos(Qsql);
		ModuloCursoEscuela dd = new ModuloCursoEscuela();
		dd = (ModuloCursoEscuela) lst.get(0);
	    
	    
	    List listaConvocatoriasAux;
    	listaConvocatoriasAux= new ArrayList();
    	
		listaConvocatorias.clear();
		//listaConvocatorias = servicioGeneral.obtenerHijos(mceAux);
		listaConvocatoriasAux = servicioGeneral.obtenerHijos(dd);
		if(listaConvocatoriasAux != null && listaConvocatoriasAux.size()>0){
		    for (int i = 0; i < listaConvocatoriasAux.size(); i++) {
		    	ModuloCursoEscuela mce2= (ModuloCursoEscuela) listaConvocatoriasAux.get(i);
		    	if (respuesta.equals("SI")){
		    		if( mce2.getTipo().equals("1") && mce2.getCompleto().equals("1")){
			    		
			    		listaConvocatorias.add(mce2);
			    	}
		    	}else{
		    			if( mce2.getTipo().equals("1") && mce2.getCompleto().equals("0")){
			    		
			    		listaConvocatorias.add(mce2);
			    	}
		    	}
		    	
		    	
		    }	    
		}
		
		
		//listaModulos= new ArrayList();
		cargarTipoModulo();
		
	}
	
	public void cambiarConvocatoriaPadre(ValueChangeEvent event) {

		//mostrarModulo=false;
		
		
		mensajeInscripcion="";
	    List listaCurso;
	    listaCurso = new ArrayList();
	    ModuloCursoEscuela mce = new ModuloCursoEscuela();
	    mce.setId((String) event.getNewValue());
	    convocatoriaSel= (String) event.getNewValue();
    	    	
	    List listaConvocatoriasAux;
    	listaConvocatoriasAux= new ArrayList();
    	
		listaConvocatorias.clear();
		//listaConvocatorias = servicioGeneral.obtenerHijos(mceAux);
		listaConvocatoriasAux = servicioGeneral.obtenerHijos(mce);
		if(listaConvocatoriasAux != null && listaConvocatoriasAux.size()>0){
		    for (int i = 0; i < listaConvocatoriasAux.size(); i++) {
		    	ModuloCursoEscuela mce2= (ModuloCursoEscuela) listaConvocatoriasAux.get(i);
		    	/*if( mce2.getTipo().equals("1")){
		    		listaConvocatorias.add(mce2);
		    	}*/
		    	
		    	if( mce2.getTipo().equals("1")){
		    		if (respuesta.equals("SI")){
			    		if( mce2.getTipo().equals("1") && mce2.getCompleto().equals("1")){
				    		
				    		listaConvocatorias.add(mce2);
				    	}
			    	}else{
			    			if( mce2.getTipo().equals("1") && mce2.getCompleto().equals("0")){
				    		
				    		listaConvocatorias.add(mce2);
				    	}
			    	}
		    	}
		    	
		    	
		    	
		    }	    
		}
		
		
		//listaModulos= new ArrayList();
		
		cargarTipoModulo();
		
		
		
		     
    }
	
	 public boolean isMostrarModulo() {
		return mostrarModulo;
	}

	public void setMostrarModulo(boolean mostrarModulo) {
		this.mostrarModulo = mostrarModulo;
	}

	public void calcularValores(){
		 
		listaValoresCursos = new ArrayList();
		totalPagar= new Long("0"); 
		 
		List lst2;
		lst2 = new ArrayList();
					List lst;
		lst = new ArrayList();
		 
			try {
				if (listaModulos != null) {
					for (int i = 0; i < listaModulos.size(); i++) {
						
						String Qsql = "from ModuloCursoEscuela where id = '"
							+ listaModulos.get(i).toString() + "'";
						
					
						
						lst=servicioGeneral.obtenerObjetos(Qsql);
						ModuloCursoEscuela dd = new ModuloCursoEscuela();
						 dd = (ModuloCursoEscuela) lst.get(0);
						
						
						
						String Qsql2 = "from ModuloCursoEscuela where curso = '" + dd.getCurso() + "'" +
						" and modulo = '" + dd.getModulo() + "'" + 
						" and tipoEstudiante = '" + tipoAspirante +"'";
						
						
						lst2=this.servicioGeneral.obtenerObjetos(Qsql2);
						
						
						if (lst2 != null) {
							for (int j = 0; j < lst2.size(); j++) {
								ModuloCursoEscuela dd2 = new ModuloCursoEscuela();
								dd2 = (ModuloCursoEscuela) lst2.get(j);
								listaValoresCursos.add(dd2);
								totalPagar = totalPagar + new Long(dd2.getValor());
							}
						}	
					}
				}
			} catch (Exception e) {
				
			}
						
					
    		
		
	 }
	
	  public void irCursos(){  
		  mostrarCursos=true;
		  cargarCursos();
		  cargarTipoModulo();
		
	  }
  
    public void buscarProyectosxConvocatoriaxModalidad(){    	
    	//ACA SE DEBEN TRAER LOS PROYECTOS CON SUS RESPECTIVOS ASESORES, ADEMÁS DEBEN TENER EL INVESTIGADOR PRINCIPAL 
    	//PARA PODER OBTENER LA FACULTAD 
    	mensajeInscripcion="";
    	String modulosInsc ="";
    	
    	InscripcionEscuelaInternacional iei = new InscripcionEscuelaInternacional();
    	iei.setNombres(nombres);
    	iei.setApellidos(apellidos);
    	iei.setCarrera(carrera);
    	iei.setFacultad(facultad);
    	iei.setUniversidad(universidad);
    	iei.setCorreo(correo);
    	iei.setDocumento(documento);
    	iei.setTipoDocumento(tipoDocumento);
    	iei.setExperiencia(experiencia);
    	iei.setRelacion(relacion);
    	iei.setCurso(new Long(convocatoriaPadre));
    	//iei.setModulo(new Long(convocatoria));
    	
    	iei.setTelefonoFijo(telefonoFijo);
    	iei.setTelefonoMovil(telefonoMovil);
    	
    	iei.setCiudad(ciudad);
    	iei.setDireccion(direccion);
    	
    	
    	if(convocatoriaPadre.equals("2")){
    		iei.setCertificado("S");
    	}
    	
    	 if (tipoAspirante.equals("0") || tipoAspirante.equals("1")){
    		 iei.setUniversidad("UNIVERSIDAD NACIONAL DE COLOMBIA");
    	 }
    		
    	
    	if (tipoAspirante.equals("2") || tipoAspirante.equals("5")){
    		iei.setEstudianteUN("N");
    		iei.setSemestre(new Long(0));
    	}else{
    		iei.setEstudianteUN("S");
    		if(semestreAux == null){
    			iei.setSemestre(new Long(0));
    		}else{
    			iei.setSemestre(new Long(semestreAux));
    		}
    		
    		if (estUn.equals("1")){
    			iei.setCredito("S");
    		}else{
    			iei.setCertificado("S");
    		}
    	}

    	/*if(!tipoAspirante.equals("0") && !tipoAspirante.equals("1")){
	    	if (estUn.equals("0")){
	    		iei.setEstudianteUN("N");	
	    	}else{
	    		iei.setEstudianteUN("S");
	    		if (estUn.equals("1")){
	    			iei.setCredito("S");
	    		}else{
	    			iei.setCertificado("S");
	    		}
	    	}
    	}*/
    	   	
    	List listaAspirante;
    	listaAspirante= new ArrayList();
    	listaAspirante = servicioGeneral.obtenerListaObjetos("TipoPersonaEscuela where id ='"+ tipoAspirante+"'");
    	TipoPersonaEscuela tp= (TipoPersonaEscuela)listaAspirante.get(0);
		    	
    	iei.setTipoPersona(tp);
    	
    	
    	
    	//iei.setSemestre(new Long(semestreAux));
    	iei.setPromedio(promedio);
    	
    	iei.setActividad(actividad);
    	iei.setInstitucion(institucion);
    	
    	Date fechaAux = new Date();
    	iei.setFecha(fechaAux);
    	
    	if(validarDatos()){
    	    try {
    	    	List listaExiste;
    	    	listaExiste= new ArrayList();
    	    	//InscripcionEscuelaInternacional inscripcionEscuelaInternacional= new InscripcionEscuelaInternacional();
    	    	//try {
    	    		//listaExiste = servicioGeneral.obtenerListaObjetos("InscripcionEscuelaInternacional where curso ='"+ convocatoriaSel +"' and modulo ='"+ convocatoria +"'and documento ='"+ documento +"'and tipoDocumento ='"+ tipoDocumento +"'");
    	    	//	inscripcionEscuelaInternacional= (InscripcionEscuelaInternacional)listaExiste.get(0);
    	    	//}catch (Exception e){
    	    		
    	    	//}
    	    	
    	    	//if (listaExiste != null && listaExiste.size() >0){
    	    		//mensajeInscripcion = "Preinscripción ya se realizó anteriormente.";	
    	    	//}else{
    	    		//wamm²
    	    		try {
    					if (listaModulos != null) {
    						for (int i = 0; i < listaModulos.size(); i++) {
    							InscripcionEscuelaInternacional ieiAux= new InscripcionEscuelaInternacional();
    							
    							String Qsql = "ModuloCursoEscuela where id = '"
    									+ listaModulos.get(i).toString() + "'";
    							List lst = servicioGeneral.obtenerListaObjetos(Qsql);
    							ModuloCursoEscuela dd = (ModuloCursoEscuela) lst.get(0);
    							//tipoAsis += dd.getDescripcion() + ", ";
    							
    						//	ieiAux.set= iei.get;
    							
    							ieiAux.setNombres(iei.getNombres());
    							ieiAux.setApellidos(iei.getApellidos());
    							ieiAux.setCarrera(iei.getCarrera());
    							ieiAux.setFacultad(iei.getFacultad());
    							ieiAux.setUniversidad(iei.getUniversidad());
    							ieiAux.setCorreo(iei.getCorreo());
    							ieiAux.setDocumento(iei.getDocumento());
    							ieiAux.setTipoDocumento(iei.getTipoDocumento());
    							ieiAux.setExperiencia(iei.getExperiencia());
    							ieiAux.setRelacion(iei.getRelacion());
    							ieiAux.setCurso(iei.getCurso());
    					    	//iei.setModulo(new Long(convocatoria));
    					    	
    							ieiAux.setTelefonoFijo(iei.getTelefonoFijo());
    							ieiAux.setTelefonoMovil(iei.getTelefonoMovil());
    							ieiAux.setEstudianteUN(iei.getEstudianteUN());
    							ieiAux.setCredito(iei.getCredito());
    							ieiAux.setCertificado(iei.getCertificado());
    							ieiAux.setTipoPersona(iei.getTipoPersona());
    							ieiAux.setSemestre(iei.getSemestre());
    							ieiAux.setPromedio(iei.getPromedio());
    					    	ieiAux.setActividad(iei.getActividad());
    							ieiAux.setInstitucion(iei.getInstitucion());
    					    	ieiAux.setFecha(iei.getFecha());
    				
    					    	
    					    	ieiAux.setCiudad(iei.getCiudad());
    					    	ieiAux.setDireccion(iei.getDireccion());
    							    							
    					    	ieiAux.setModulo(new Long(dd.getId()));
    					    	
    					    	if (modulosInsc.equals("")){
    					    		modulosInsc = dd.getNombre() +  "\n";
    					    	}else{
    					    		modulosInsc =  modulosInsc +  "\n"
        					    	+  dd.getNombre() +  "\n";
    					    	}
    					    	
    					    	
    					    	
    							servicioGeneral.guardarObjeto(ieiAux);
    							
    						}
    					}
    					//evento[3] = tipoAsis;
    					//evento[4] = listaAsistentes;

    				} catch (Exception e) {
    				}
    	    		
    	    		
    	    		
    	    		mensajeInscripcion = "Preinscripción realizada con éxito.";	
    	    		
    	    		String mensajeConfirmacion="";
    	    		
    	    		
    	    		List listaCursoModulo;
    	    		listaCursoModulo= new ArrayList();
    	    		ModuloCursoEscuela mce = new ModuloCursoEscuela(); 
    	    		listaCursoModulo = servicioGeneral.obtenerListaObjetos("ModuloCursoEscuela where id ='"+ iei.getCurso()+"'");
    	    		mce= (ModuloCursoEscuela)listaCursoModulo.get(0);
    	    		
    	    			
    	    		
    	    	//	ModuloCursoEscuela mceAux = new ModuloCursoEscuela(); 
    	    		//listaCursoModulo = servicioGeneral.obtenerListaObjetos("ModuloCursoEscuela where id ='"+ iei.getModulo()+"'");
    	    		//mceAux= (ModuloCursoEscuela)listaCursoModulo.get(0);
    	    		
    	    		
    	    		TipoPersonaEscuela tpa = new TipoPersonaEscuela(); 
    	    		listaCursoModulo = servicioGeneral.obtenerListaObjetos("TipoPersonaEscuela where id ='"+ iei.getTipoPersona().getId()+"'");
    	    		tpa= (TipoPersonaEscuela)listaCursoModulo.get(0);
    	    		
    	    		String mensajeB="";
    	    		String mensajeC="";
    	    		
    	        	    		
    	    		if ( iei.getTipoPersona().getId() ==0 || iei.getTipoPersona().getId() ==1 ){
    	    			mensajeB = "Carrera: " + iei.getCarrera() +  "\n"
        	    		+ "Universidad: " + iei.getUniversidad() +  "\n"
        	    		+ "Semestre: " + iei.getSemestre().toString() +  "\n"
        	    		+ "Promedio: " + iei.getPromedio() +  "\n"
        	    		+ "¿Qué cursos ha realizado o cuál es su experiencia con los temas de los cursos/módulos que eligió?: " + iei.getExperiencia() +  "\n"
        	    		+ "Explique cómo están relacionados el curso/módulo que eligió con su trabajo o con su tema de investigación: " + iei.getRelacion() +  "\n";
          	    	}else{
        	    		mensajeB= "Actividad: " + iei.getActividad() +  "\n"
        	    		+ "Institución: " + iei.getInstitucion() +  "\n"
        	    		+ "¿Qué cursos ha realizado o cuál es su experiencia con los temas de los cursos/módulos que eligió?: " + iei.getExperiencia() +  "\n"
        	    		+ "Explique cómo están relacionados el curso/módulo que eligió con su trabajo o con su tema de investigación: " + iei.getRelacion() +  "\n";
        	    	}
    	    		
    	    		if(iei.getCertificado() != null && iei.getCertificado().equals("S")){
    	    			mensajeC="Certificado";
    	    		}else{
    	    			if(iei.getCredito() != null && iei.getCredito().equals("S")){
    	    				mensajeC="Créditos" ;
    	    			}else{
    	    				mensajeC="--------" ;
    	    			}
    	    		}
    	    		
    	    	
    	    		mensajeConfirmacion = "Su preinscipción ha sido exitosa"+ "\n"
					+ "Estos son los datos del curso que ha ingresado: " +  "\n" +  "\n"
					+ "Curso: " + mce.getNombre() +  "\n"
    	    		+ "Módulo: " + modulosInsc +  "\n"
    	    		+ "Nombre: " + iei.getNombres() + " " + iei.getApellidos() +  "\n"
    	    		+ "Documento: " + iei.getDocumento() +  "\n"
    	    		+ "Ciudad: " + iei.getCiudad() +  "\n"
    	    		+ "Dirección: " + iei.getDireccion() +  "\n"
    	    		+ "Teléfono fijo: " + iei.getTelefonoFijo() +  "\n"
    	    		+ "Teléfono móvil: " + iei.getTelefonoMovil() +  "\n"
    	    		+ "Correo: " + iei.getCorreo() +  "\n"
    	    		+ "Tipo aspirante: " + tpa.getNombre() +  "\n"
    	    		+ "Estuidante Universidad Nacional: " + mensajeC + "\n"
    	    	    + mensajeB;
    	    		
    	    		
    	    		Correo correo = new Correo();
					correo.setOrigen(Correo.CORREO_HERMES);
					
					correo.adicionarDireccion(iei.getCorreo());
					correo.adicionarCopiaOculta("escuelaint@unal.edu.co");
					//correo.adicionarCopiaOculta("sisii_nal@unal.edu.co");
					correo.setAsunto("Inscripción Escuela Internacional");
					correo.setCuerpo(mensajeConfirmacion);
					servicioCorreo.enviarCorreo(correo);
    	    		
    	    		
    	    		
    	    	//}
	    		
	    		//experiencia ="";
	    		//relacion="";
	    	}catch (Exception e){
	    		mensajeInscripcion = "Preinscripción no realizada. Por favor verifique los datos.";
	    	}
    	}
    	
    
    }
 
        
    public boolean isMostrarCursos() {
		return mostrarCursos;
	}

	public void setMostrarCursos(boolean mostrarCursos) {
		this.mostrarCursos = mostrarCursos;
	}

	public boolean validarDatos(){
    	
       	
    	if(nombres.equals("")){
    		mensajeInscripcion = "Preinscripción no realizada. Por favor ingrese sus nombres.";
    		return false;
    	}
    	
    	if(apellidos.equals("")){
    		mensajeInscripcion = "Preinscripción no realizada. Por favor ingrese sus apellidos.";
    		return false;
    	}
    	
    	if(documento.equals("")){
    		mensajeInscripcion = "Preinscripción no realizada. Por favor ingrese el documento de identificación.";
    		return false;
    	}
    	
    	if(correo.equals("")){
    		mensajeInscripcion = "Preinscripción no realizada. Por favor ingrese su dirección de correo electrónica.";
    		return false;
    	}
    	
    	if(listaModulos.size()<=0){
    		mensajeInscripcion = "Preinscripción no realizada. Por favor seleccione al menos un módulo en la preinscripción.";
    		return false;
    	}
    	
    	return true;
    	
    }
    
    
	public String getConvocatoria() {
		return convocatoria;
	}
	public void setConvocatoria(String convocatoria) {
		this.convocatoria = convocatoria;
	}
	public SelectItem[] getConvocatoriaItem() {
		return convocatoriaItem;
	}
	public void setConvocatoriaItem(SelectItem[] convocatoriaItem) {
		this.convocatoriaItem = convocatoriaItem;
	}
	public String getCoordinadorActual() {
		return coordinadorActual;
	}
	public void setCoordinadorActual(String coordinadorActual) {
		this.coordinadorActual = coordinadorActual;
	}
	public SelectItem[] getCoordinadoresItem() {
		return coordinadoresItem;
	}
	public void setCoordinadoresItem(SelectItem[] coordinadoresItem) {
		this.coordinadoresItem = coordinadoresItem;
	}
	public List getListaConvocatorias() {
		return listaConvocatorias;
	}
	public void setListaConvocatorias(List listaConvocatorias) {
		this.listaConvocatorias = listaConvocatorias;
	}
	public List getListaProyectos() {
		return listaProyectos;
	}
	public void setListaProyectos(List listaProyectos) {
		this.listaProyectos = listaProyectos;
	}
	public HtmlDataTable getTablaProyectos() {
		return tablaProyectos;
	}
	public void setTablaProyectos(HtmlDataTable tablaProyectos) {
		this.tablaProyectos = tablaProyectos;
	}	
	public String getNombreModalidad() {
		return nombreModalidad;
	}
	public void setNombreModalidad(String nombreModalidad) {
		this.nombreModalidad = nombreModalidad;
	}		
	public List getListaCoordinadores() {
		return listaCoordinadores;
	}
	public void setListaCoordinadores(List listaCoordinadores) {
		this.listaCoordinadores = listaCoordinadores;
	}
	public List getListaProyectosCoordinador() {
		return listaProyectosCoordinador;
	}
	public void setListaProyectosCoordinador(List listaProyectosCoordinador) {
		this.listaProyectosCoordinador = listaProyectosCoordinador;
	}
	
	public boolean getProyectoAsignado(){
		ProyectoCoordinador pc=(ProyectoCoordinador)tablaProyectos.getRowData();
		if(pc.getPerId().equals("0")){
			return false;
		}
		return true;		
	}
		
	public String getConvocatoriaPadre() {
		return convocatoriaPadre;
	}
	public void setConvocatoriaPadre(String convocatoriaPadre) {
		this.convocatoriaPadre = convocatoriaPadre;
	}
	public SelectItem[] getConvocatoriaPadreItem() {
		return convocatoriaPadreItem;
	}
	public void setConvocatoriaPadreItem(SelectItem[] convocatoriaPadreItem) {
		this.convocatoriaPadreItem = convocatoriaPadreItem;
	}
	public List getListaConvocatoriasPadre() {
		return listaConvocatoriasPadre;
	}
	public void setListaConvocatoriasPadre(List listaConvocatoriasPadre) {
		this.listaConvocatoriasPadre = listaConvocatoriasPadre;
	}

	public HtmlDataTable getTablaProyecto() {
		return tablaProyecto;
	}

	public void setTablaProyecto(HtmlDataTable tablaProyecto) {
		this.tablaProyecto = tablaProyecto;
	}

	public List getListaProyectoCoordinador() {
		return listaProyectoCoordinador;
	}
	
	public int getListaProyectoCoordinadors() {
		return listaProyectoCoordinador.size();
	}

	public void setListaProyectoCoordinador(List listaProyectoCoordinador) {
		this.listaProyectoCoordinador = listaProyectoCoordinador;
	}

	public boolean isMostrarProyecto() {
		return mostrarProyecto;
	}

	public void setMostrarProyecto(boolean mostrarProyecto) {
		this.mostrarProyecto = mostrarProyecto;
	}

	public Persona getPerson() {
		return person;
	}

	public void setPerson(Persona person) {
		this.person = person;
	}

	public SelectItem[] getFacultadItem() {
		return facultadItem;
	}

	public void setFacultadItem(SelectItem[] facultadItem) {
		this.facultadItem = facultadItem;
	}

	public List getListaProyectosCoordinadorAux() {
		return listaProyectosCoordinadorAux;
	}

	public void setListaProyectosCoordinadorAux(List listaProyectosCoordinadorAux) {
		this.listaProyectosCoordinadorAux = listaProyectosCoordinadorAux;
	}

	public String getFacultad() {
		return facultad;
	}

	public void setFacultad(String facultad) {
		this.facultad = facultad;
	}

	public SelectItem[] getCoordinadorNuevoItem() {
		return coordinadorNuevoItem;
	}

	public void setCoordinadorNuevoItem(SelectItem[] coordinadorNuevoItem) {
		this.coordinadorNuevoItem = coordinadorNuevoItem;
	}

	public String getCoordinadorNuevo() {
		return coordinadorNuevo;
	}

	public void setCoordinadorNuevo(String coordinadorNuevo) {
		this.coordinadorNuevo = coordinadorNuevo;
	}

	public boolean isMostrarSelCoordinador() {
		return mostrarSelCoordinador;
	}

	public void setMostrarSelCoordinador(boolean mostrarSelCoordinador) {
		this.mostrarSelCoordinador = mostrarSelCoordinador;
	}

	public List getListaProyectosCoordinadorConsulta() {
		return listaProyectosCoordinadorConsulta;
	}

	public void setListaProyectosCoordinadorConsulta(
			List listaProyectosCoordinadorConsulta) {
		this.listaProyectosCoordinadorConsulta = listaProyectosCoordinadorConsulta;
	}

	public List getListaProyectosCoordinadorConsultaAux() {
		return listaProyectosCoordinadorConsultaAux;
	}

	public void setListaProyectosCoordinadorConsultaAux(
			List listaProyectosCoordinadorConsultaAux) {
		this.listaProyectosCoordinadorConsultaAux = listaProyectosCoordinadorConsultaAux;
	}

	public HtmlDataTable getTablaProyectoConsulta() {
		return tablaProyectoConsulta;
	}

	public void setTablaProyectoConsulta(HtmlDataTable tablaProyectoConsulta) {
		this.tablaProyectoConsulta = tablaProyectoConsulta;
	}

	public boolean isMostrarProyectoCoordinador() {
		return mostrarProyectoCoordinador;
	}

	public void setMostrarProyectoCoordinador(boolean mostrarProyectoCoordinador) {
		this.mostrarProyectoCoordinador = mostrarProyectoCoordinador;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public boolean isMostrarSumaProyectos() {
		return mostrarSumaProyectos;
	}

	public void setMostrarSumaProyectos(boolean mostrarSumaProyectos) {
		this.mostrarSumaProyectos = mostrarSumaProyectos;
	}

	public int getTotalAsignados() {
		return totalAsignados;
	}

	public void setTotalAsignados(int totalAsignados) {
		this.totalAsignados = totalAsignados;
	}

	public int getTotalSinAsignar() {
		return totalSinAsignar;
	}

	public void setTotalSinAsignar(int totalSinAsignar) {
		this.totalSinAsignar = totalSinAsignar;
	}

	public SelectItem[] getSedeItem() {
		return sedeItem;
	}

	public void setSedeItem(SelectItem[] sedeItem) {
		this.sedeItem = sedeItem;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	public SelectItem[] getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}

	public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
		this.tipoDocumentoItem = tipoDocumentoItem;
	}

	public String getTipoAspirante() {
		return tipoAspirante;
	}

	public void setTipoAspirante(String tipoAspirante) {
		this.tipoAspirante = tipoAspirante;
	}

	public SelectItem[] getTipoAspiranteItem() {
		return tipoAspiranteItem;
	}

	public void setTipoAspiranteItem(SelectItem[] tipoAspiranteItem) {
		this.tipoAspiranteItem = tipoAspiranteItem;
	}

	public Long getSemestre() {
		return semestre;
	}

	public void setSemestre(Long semestre) {
		this.semestre = semestre;
	}

	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	public String getUniversidad() {
		return universidad;
	}

	public void setUniversidad(String universidad) {
		this.universidad = universidad;
	}

	public String getPromedio() {
		return promedio;
	}

	public void setPromedio(String promedio) {
		this.promedio = promedio;
	}

	public SelectItem[] getPromedioItem() {
		return promedioItem;
	}

	public void setPromedioItem(SelectItem[] promedioItem) {
		this.promedioItem = promedioItem;
	}

	public String getEstUn() {
		return estUn;
	}

	public void setEstUn(String estUn) {
		this.estUn = estUn;
	}

	public SelectItem[] getEstUnItem() {
		return estUnItem;
	}

	public void setEstUnItem(SelectItem[] estUnItem) {
		this.estUnItem = estUnItem;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public String getMensajeInscripcion() {
		return mensajeInscripcion;
	}

	public void setMensajeInscripcion(String mensajeInscripcion) {
		this.mensajeInscripcion = mensajeInscripcion;
	}

	public String getSemestreAux() {
		return semestreAux;
	}

	public void setSemestreAux(String semestreAux) {
		this.semestreAux = semestreAux;
	}

	public SelectItem[] getSemestreAuxItem() {
		return SemestreAuxItem;
	}

	public void setSemestreAuxItem(SelectItem[] semestreAuxItem) {
		SemestreAuxItem = semestreAuxItem;
	}

	public SelectItem[] getTipoModulo() {
		return tipoModulo;
	}

	public void setTipoModulo(SelectItem[] tipoModulo) {
		this.tipoModulo = tipoModulo;
	}

	public String getTipoModuloSel() {
		return tipoModuloSel;
	}

	public void setTipoModuloSel(String tipoModuloSel) {
		this.tipoModuloSel = tipoModuloSel;
	}

	public List getListaModulos() {
		return listaModulos;
	}

	public void setListaModulos(List listaModulos) {
		this.listaModulos = listaModulos;
	}
	
    public String getTelefonoFijo() {
		return telefonoFijo;
	}

	public void setTelefonoFijo(String telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}

	public String getTelefonoMovil() {
		return telefonoMovil;
	}

	public void setTelefonoMovil(String telefonoMovil) {
		this.telefonoMovil = telefonoMovil;
	}

	public List getListaValoresCursos() {
		return listaValoresCursos;
	}

	public void setListaValoresCursos(List listaValoresCursos) {
		this.listaValoresCursos = listaValoresCursos;
	}

	public HtmlDataTable getTablaValoresCursos() {
		return tablaValoresCursos;
	}

	public void setTablaValoresCursos(HtmlDataTable tablaValoresCursos) {
		this.tablaValoresCursos = tablaValoresCursos;
	}

	public String getCompletoAux() {
		return completoAux;
	}

	public void setCompletoAux(String completoAux) {
		this.completoAux = completoAux;
	}

	public String getConvocatoriaSel() {
		return convocatoriaSel;
	}

	public void setConvocatoriaSel(String convocatoriaSel) {
		this.convocatoriaSel = convocatoriaSel;
	}

	public Long getTotalPagar() {
		return totalPagar;
	}

	public void setTotalPagar(Long totalPagar) {
		this.totalPagar = totalPagar;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	
	
}
