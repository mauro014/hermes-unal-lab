package co.edu.unal.hermes.vista.oficinaExtension;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.model.DualListModel;

import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Modalidad;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorAdministrarModuloECP extends ManejadorBase{
	
	private Convocatoria moduloSeleccionado;
	private ArrayList<Convocatoria> listaModulos;
	private ArrayList<Convocatoria> modulosFiltrados;
	private Persona personaActual;
	
	//CURSOS
	private List<Proyecto> listaCursos;
	private List<Proyecto> listaCursosSeleccionados;
	
	//CONSULTAR
	private String modalidadECP;
	private String nombre;
	private String objGeneral;
	//private String cursosCerrados;
	private String claseEventoECP;
	private String subEventoECP;
	private String otroEvento;	
	private boolean mostrarAbierto;
	private boolean mostrarSubEvento;
	
	//VINCULAR CURSOS MODULO
	private String departamento;
	private List departamentoItem;
	private DualListModel<Proyecto> proyectosCurso;
	private Modalidad modalidad;
	public List<SelectItem> listaModalidadModulo;
	
	//CONSTRUCTOR
	public ManejadorAdministrarModuloECP(){		
		personaActual = (Persona) sesion.getAttribute("persona");
		cargarModulos();
		listaModalidadModulo = new ArrayList<SelectItem>();
		listaModalidadModulo.add(new SelectItem("A","Abierto"));
		listaModalidadModulo.add(new SelectItem("C","Cerrado"));
	}
	
	public void cargarModulos(){
		listaModulos = new ArrayList<Convocatoria>();
		
		InvestigadorInterno ii = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());

		List lista = servicioGeneral.obtenerObjetos("select c from Convocatoria c, ConvocatoriaPadre cp where c.padre.id = cp.id " +
				"and c.dependencia.id = cp.dependencia " +
				"and cp.tipoConvocatoria = 'ECP' " +
				"and c.dependencia.id = " + ii.getDependencia().getId() +
				" order by c.fechaInicio desc");	
	    
	    for (int i=0; i<lista.size();i++){
	    	Convocatoria conv = (Convocatoria) lista.get(i);	    	
	    	listaModulos.add(conv);
	    }
	}	

	
	public String consultarModulo(){				
		if(moduloSeleccionado.getModalidadModulo().equals("A")){
			modalidadECP = "Abierto";
			mostrarAbierto = true;
		}
		else{
			modalidadECP = "Cerrado";
			mostrarAbierto = false;
		}
				
		nombre = moduloSeleccionado.getTitulo();		
		objGeneral = moduloSeleccionado.getObjetivo();
		//cursosCerrados = moduloSeleccionado.getCursosCerrados();
		
		DominioDetalle clEv = new DominioDetalle();
		List listaClaseEventos;
		listaClaseEventos = servicioGeneral.obtenerObjetos("select dd from DominioDetalle dd where dd.identificador.tipo like '"+ moduloSeleccionado.getClaseEventoECP()+"' ");
		
		clEv= (DominioDetalle)listaClaseEventos.get(0);
		claseEventoECP = clEv.getDescripcion();
		
		if(clEv.getIdentificador().getTipo().equals("SUB_EVENTO")){		
			DominioDetalle suEv = new DominioDetalle();
			List listaSubEventos;
			listaSubEventos = servicioGeneral.obtenerObjetos("select dd from DominioDetalle dd where dd.identificador.tipo like '"+ moduloSeleccionado.getSubEventoECP()+"' ");	
			suEv= (DominioDetalle)listaSubEventos.get(0);
			subEventoECP = suEv.getDescripcion();
			mostrarSubEvento = true;			
			
		}else{
			subEventoECP="";
			mostrarSubEvento = false;
		}
		if(subEventoECP.equals("OTRA"))
			otroEvento = moduloSeleccionado.getOtroEvento();
		else
			otroEvento = "";
		
		return "consultarModuloECP";
	}		
	
	public String volverAdmin() {
		modalidadECP = "";
		nombre = "";
		objGeneral = "";
		//cursosCerrados = "";
		claseEventoECP = "";
		subEventoECP = "";
		otroEvento = "";
		moduloSeleccionado = null;
		return "AdministrarModuloECP";
	}
	
	public void cargarCursosDepto(){
		
		listaCursos = new ArrayList<Proyecto>();
		//listaCursosSeleccionados = new ArrayList<Proyecto>();		
		
		InvestigadorInterno ii = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());//TODO REVISAR DPN
		
		if(!departamento.equals("Todos los cursos disponibles")){		
			List lista = servicioGeneral.obtenerObjetos("select p " +
														"from Proyecto p " +
														"where p.tipoActividad = 'ECP' " +
														"and p.estadoProyecto.id = 'AP' " +
														"and to_char(p.fechaTentativaInicio, 'YYYY') = " +moduloSeleccionado.getPadre().getAno() + " "+
														"and p.modalidad.id = 405 " + //MODALIDAD FICHA MINIMA PRUEBAS = 405, PRUDUCCIÓN = 2
														"and p.dependencia.id = "+ departamento); //DEPARTAMENTO SELECCIONADO
			
		    for (int i=0; i<lista.size();i++){
		    	Proyecto pry = (Proyecto) lista.get(i);	    	
		    	listaCursos.add(pry);
		    }	    	    
		    
		    /*List listaBusqCursosSelec = servicioGeneral.obtenerObjetos("select p " +
					"from Proyecto p " +
					"where p.tipoActividad = 'ECP' " +
					"and p.estadoProyecto.id = 'AP' " +
					"and to_char(p.fechaTentativaInicio, 'YYYY') = " +moduloSeleccionado.getPadre().getAno() + " "+
					"and p.modalidad.id = " + moduloSeleccionado.getId() + //CURSOS DEL MODULO SELECCIONADO
					"and p.dependencia.id = "+ ii.getDependencia().getId());	    	 
		    
		    for (int i=0; i<listaBusqCursosSelec.size();i++){
		    	Proyecto pry = (Proyecto) listaBusqCursosSelec.get(i);	    	
		    	listaCursosSeleccionados.add(pry);
		    }*/
		}else{
			List lista = servicioGeneral.obtenerObjetos("select p " +
					"from Proyecto p " +
					"where p.tipoActividad = 'ECP' " +
					"and p.estadoProyecto.id = 'AP' " +
					"and to_char(p.fechaTentativaInicio, 'YYYY') = " +moduloSeleccionado.getPadre().getAno() + " "+
					"and p.modalidad.id = 405 " + //MODALIDAD FICHA MINIMA PRUEBAS = 405, PRUDUCCIÓN = 2
					"and p.dependencia.facultad.id = "+ ii.getDependencia().getId());

			for (int i=0; i<lista.size();i++){
				Proyecto pry = (Proyecto) lista.get(i);	    	
				listaCursos.add(pry);
			}	    	    

			/*List listaBusqCursosSelec = servicioGeneral.obtenerObjetos("select p " +
					"from Proyecto p " +
					"where p.tipoActividad = 'ECP' " +
					"and p.estadoProyecto.id = 'AP' " +
					"and to_char(p.fechaTentativaInicio, 'YYYY') = " +moduloSeleccionado.getPadre().getAno() + " "+
					"and p.modalidad.id = " + moduloSeleccionado.getId() + //CURSOS DEL MODULO SELECCIONADO
					"and p.dependencia.facultad.id = "+ ii.getDependencia().getId());	    	 

			for (int i=0; i<listaBusqCursosSelec.size();i++){
				Proyecto pry = (Proyecto) listaBusqCursosSelec.get(i);	    	
				listaCursosSeleccionados.add(pry);
			}*/	
		}
		
	    proyectosCurso = new DualListModel<Proyecto>(listaCursos, listaCursosSeleccionados);
		
	}
	
	public String cursosModulo(){
		cargarDepartamentos();
		
		listaCursos = new ArrayList<Proyecto>();
		listaCursosSeleccionados = new ArrayList<Proyecto>();
		/*modalidad = new Modalidad();
		
		List listaModalidad = servicioGeneral.obtenerObjetos("select p " +
				"from Modalidad m " +
				"where m.id = " + moduloSeleccionado.getId() +" "+
				"and m.tipo.id = 'ECP'");
		
		modalidad = (Modalidad)listaModalidad.get(0);*/
		
		InvestigadorInterno ii = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());//TODO REVISAR DPN
		
		List lista = servicioGeneral.obtenerObjetos("select p " +
													"from Proyecto p " +
													"where p.tipoActividad = 'ECP' " +
													"and p.estadoProyecto.id = 'AP' " +
													"and to_char(p.fechaTentativaInicio, 'YYYY') = " +moduloSeleccionado.getPadre().getAno() + " "+
													"and p.modalidad.id = 405 " + //MODALIDAD FICHA MINIMA PRUEBAS = 405, PRUDUCCIÓN = 2
													"and p.dependencia.facultad.id = "+ ii.getDependencia().getId());
		
	    for (int i=0; i<lista.size();i++){
	    	Proyecto pry = (Proyecto) lista.get(i);	    	
	    	listaCursos.add(pry);
	    }	    	    
	    
	    List listaBusqCursosSelec = servicioGeneral.obtenerObjetos("select p " +
				"from Proyecto p " +
				"where p.tipoActividad = 'ECP' " +
				"and p.estadoProyecto.id = 'AP' " +
				"and to_char(p.fechaTentativaInicio, 'YYYY') = " +moduloSeleccionado.getPadre().getAno() + " "+
				"and p.modalidad.id = " + moduloSeleccionado.getId() + //CURSOS DEL MODULO SELECCIONADO
				"and p.dependencia.facultad.id = "+ ii.getDependencia().getId());	    	 
	    
	    for (int i=0; i<listaBusqCursosSelec.size();i++){
	    	Proyecto pry = (Proyecto) listaBusqCursosSelec.get(i);	    	
	    	listaCursosSeleccionados.add(pry);
	    }	 
		
	    proyectosCurso = new DualListModel<Proyecto>(listaCursos, listaCursosSeleccionados);
	   
		return "SeleccionCursosModulo";
	}
	
	public void cargarDepartamentos(){
		departamentoItem = new ArrayList<SelectItem>();								

		String consulta = "select dd from Dependencia dd where dd.facultad.id =" + moduloSeleccionado.getDependencia().getId() +
				" and dd.esDepartamento = 'Y'";
		List lista = servicioGeneral.obtenerObjetos(consulta);

		if(lista.size() > 0){
			departamentoItem.add(0, new SelectItem("Todos los cursos disponibles"));

			for (int i=0; i<lista.size();i++){
				Dependencia depto = (Dependencia) lista.get(i);	    	
				departamentoItem.add(new SelectItem(depto.getId(), depto.getNombre()));
			}
		}else{
			departamento = "";
		}
	}
	
	public void vincularCursosModulo(){
		try{
			//guardar vinculados
			listaCursosSeleccionados = proyectosCurso.getTarget();
			if(listaCursosSeleccionados.size()>0){
				for(int i=0; i < listaCursosSeleccionados.size(); i++){
					Proyecto pry = new Proyecto();
					Long id;
					//pry = (Proyecto)listaCursosSeleccionados.get(i);//TRAER PROYECTOS !!! PORQUE TRAE STRING
					id = listaCursosSeleccionados.get(i).getId();
					List lista = servicioGeneral.obtenerObjetos("select p " +
							"from Proyecto p " +
							"where p.id = " + id);
					
					pry = (Proyecto) lista.get(0);
					if(!pry.getModalidad().getId().equals(moduloSeleccionado.getId())){
						pry.setModalidad(moduloSeleccionado);
						servicioGeneral.guardarObjeto(pry);
					}					
				}
				FacesContext.getCurrentInstance().addMessage(
						"msgs",
						new FacesMessage(
								FacesMessage.SEVERITY_INFO,
								"Los cursos se han adicionado exitosamente al módulo.",	""));
			}else
				FacesContext.getCurrentInstance().addMessage(
						"msgs",
						new FacesMessage(
								FacesMessage.SEVERITY_WARN,
								"No hay cursos seleccionados.",	""));
			
			//DESVINCULAR
			listaCursos = proyectosCurso.getSource();
			if(listaCursos.size()>0){
				Modalidad mod = new Modalidad();
				
				List listaMod = servicioGeneral.obtenerObjetos("select m " +
						"from Modalidad m " +
						"where m.id = " + 405);//ID CONVOCATORIA FICHA MINIMA PRUEBAS
				
				mod = (Modalidad)listaMod.get(0);
				
				for(int i=0; i < listaCursos.size(); i++){
					Proyecto pry = new Proyecto();
					Long id;																			
					
					id = listaCursos.get(i).getId();
					List lista = servicioGeneral.obtenerObjetos("select p " +
							"from Proyecto p " +
							"where p.id = " + id);
					
					pry = (Proyecto) lista.get(0);
					if(pry.getModalidad().getId().equals(moduloSeleccionado.getId())){
						pry.setModalidad(mod);
						servicioGeneral.guardarObjeto(pry);
					}				
				}			
			}
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					"msgs",
					new FacesMessage(
							FacesMessage.SEVERITY_WARN,
							"Muere!!.",	""));
			System.out.println("pailas");
		}
		
	}
	
	public String fichaQuipuModulo(){
		sesion.setAttribute("moduloECP", moduloSeleccionado);
		
		return "FichaQuipuModuloECP";
	}
	
	public String atras() {
		limpiar();
		return "ConsultarConvocatoriaExtension";
	}
	
	public void limpiar(){
		sesion.removeAttribute("ManejadorAdministrarModuloECP");
	}
	
	//GET & SET

	public Convocatoria getModuloSeleccionado() {
		return moduloSeleccionado;
	}

	public void setModuloSeleccionado(Convocatoria moduloSeleccionado) {
		this.moduloSeleccionado = moduloSeleccionado;
	}

	public ArrayList<Convocatoria> getListaModulos() {
		return listaModulos;
	}

	public void setListaModulos(ArrayList<Convocatoria> listaModulos) {
		this.listaModulos = listaModulos;
	}

	//GET CONSULTAR MODULO 
	
	public String getModalidadECP() {
		return modalidadECP;
	}

	public boolean isMostrarSubEvento() {
		return mostrarSubEvento;
	}

	public String getNombre() {
		return nombre;
	}

	public String getObjGeneral() {
		return objGeneral;
	}
/*
	public String getCursosCerrados() {
		return cursosCerrados;
	}
*/
	public String getClaseEventoECP() {
		return claseEventoECP;
	}

	public String getSubEventoECP() {
		return subEventoECP;
	}

	public String getOtroEvento() {
		return otroEvento;
	}

	public boolean isMostrarAbierto() {
		return mostrarAbierto;
	}

	public void setModalidadECP(String modalidadECP) {
		this.modalidadECP = modalidadECP;
	}
	
	// GET & SET PICKLIST

	public List<Proyecto> getListaCursos() {
		return listaCursos;
	}

	public void setListaCursos(List<Proyecto> listaCursos) {
		this.listaCursos = listaCursos;
	}

	public List<Proyecto> getListaCursosSeleccionados() {
		return listaCursosSeleccionados;
	}

	public void setListaCursosSeleccionados(
			List<Proyecto> listaCursosSeleccionados) {
		this.listaCursosSeleccionados = listaCursosSeleccionados;
	}

	public DualListModel<Proyecto> getProyectosCurso() {
		return proyectosCurso;
	}

	public void setProyectosCurso(DualListModel<Proyecto> proyectosCurso) {
		this.proyectosCurso = proyectosCurso;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public List getDepartamentoItem() {
		return departamentoItem;
	}

	public void setDepartamentoItem(List departamentoItem) {
		this.departamentoItem = departamentoItem;
	}

	public ArrayList<Convocatoria> getModulosFiltrados() {
		return modulosFiltrados;
	}

	public void setModulosFiltrados(ArrayList<Convocatoria> modulosFiltrados) {
		this.modulosFiltrados = modulosFiltrados;
	}

	public List<SelectItem> getListaModalidadModulo() {
		return listaModalidadModulo;
	}

	public void setListaModalidadModulo(List<SelectItem> listaModalidadModulo) {
		this.listaModalidadModulo = listaModalidadModulo;
	}		

}
