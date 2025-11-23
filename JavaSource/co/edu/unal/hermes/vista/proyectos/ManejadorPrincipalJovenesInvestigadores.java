package co.edu.unal.hermes.vista.proyectos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.ProyectoInforme;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorPrincipalJovenesInvestigadores  extends ManejadorBase {
	private List<ConvocatoriaPadre>  listadoConvocatoriaPadre;
	private Persona personaSeleccionada;
	private List<Sede> listaSedes;
	private boolean mostrarFacultades = false;
	private String sedeSel;
	private List<Dependencia> dependenciasUN;
	private SelectItem[] dependenciaItem;
	private List<Dependencia> facultadesUN;
	private SelectItem[] facultadItem;
	private String facultadSel;
	private SelectItem[] sedeItem;
	private String convocatoriaSel;
	private SelectItem[] convocatoriaItem;
	private List<Persona>  listadoPersonas;
	private String estudiantesMostrar;

	public ManejadorPrincipalJovenesInvestigadores() {
		super();	
		setListadoConvocatoriaPadre(new ArrayList<ConvocatoriaPadre>());
		ArrayList<Sede> sedesUN=new ArrayList<Sede>();
		ArrayList<ConvocatoriaPadre> convocatoriasPadre=new ArrayList<ConvocatoriaPadre>();
		try {			
			String sql = "select conv.padre from Convocatoria as conv inner join "
					+ "conv.padre where conv.restriccion.id in ('CONV_JI_COL','CONV_JI_COL_2014')";
			String sql1 = "select e from Sede e where  e.id<>0";			
			convocatoriasPadre=(ArrayList<ConvocatoriaPadre>) servicioGeneral.obtenerObjetos(sql);
			sedesUN = new ArrayList<Sede>();
			sedesUN = (ArrayList<Sede>) servicioGeneral.obtenerObjetos(sql1);
			
			sedeItem = new SelectItem[sedesUN.size()];
			convocatoriaItem= new SelectItem[convocatoriasPadre.size()];
			
			for (int i = 0; i < convocatoriasPadre.size(); i++) {
				ConvocatoriaPadre dd = (ConvocatoriaPadre) convocatoriasPadre.get(i);
				convocatoriaItem[i] = new SelectItem(dd.getId(), dd.getTitulo());
				dd = null;
			}
			
			for (int i = 0; i < sedesUN.size(); i++) {
				Sede dd = (Sede) sedesUN.get(i);
				sedeItem[i] = new SelectItem(dd.getId(), dd.getNombre());
				dd = null;
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}	
	}
	
	
	public void cambiarSede() {
		if (sedeSel.equals("1") || sedeSel.equals("6") || sedeSel.equals("7")
				|| sedeSel.equals("8") || sedeSel.equals("9")) {
			mostrarFacultades = false;
			// dependencias
			dependenciasUN = new ArrayList<Dependencia>();
			dependenciasUN = servicioGeneral
					.obtenerObjetos("select e from Dependencia e where e.sede.id = '"
							+ sedeSel + "' and e.estado='A' order by e.nombre");
			dependenciaItem = new SelectItem[dependenciasUN.size()];
			for (int i = 0; i < dependenciasUN.size(); i++) {
				Dependencia dd = (Dependencia) dependenciasUN.get(i);
				dependenciaItem[i] = new SelectItem(dd.getId(), dd.getNombre());
				dd = null;
			}

		} else {
			mostrarFacultades = true;
			// Facultades
			facultadesUN = new ArrayList<Dependencia>();
			facultadesUN = servicioGeneral
					.obtenerObjetos("select e from Dependencia e where e.sede.id = '"
							+ sedeSel
							+ "' and e.esFacultad = 'Y'  order by e.nombre");
			facultadItem = new SelectItem[facultadesUN.size()];
			for (int i = 0; i < facultadesUN.size(); i++) {
				Dependencia dd = (Dependencia) facultadesUN.get(i);
				facultadItem[i] = new SelectItem(dd.getId(), dd.getNombre());
				dd = null;
			}
//
//			facultadSel = ((Dependencia) facultadesUN.get(0)).getId()
//					.toString();
			facultadSel="0";

			dependenciasUN = new ArrayList<Dependencia>();
			dependenciasUN = servicioGeneral
					.obtenerObjetos("select e from Dependencia e where e.facultad.id = '"
							+ facultadSel
							+ "' and e.estado = 'A' order by e.nombre");

			System.out.println("El tamaño de las dependencias es: ");
			if (dependenciasUN != null && dependenciasUN.size() > 0) {
				dependenciaItem = new SelectItem[dependenciasUN.size()];
				for (int i = 0; i < dependenciasUN.size(); i++) {
					Dependencia dd = (Dependencia) dependenciasUN.get(i);
					dependenciaItem[i] = new SelectItem(dd.getId(),
							dd.getNombre());
					dd = null;
				}
			} else {
				dependenciasUN = servicioGeneral
						.obtenerObjetos("select e from Dependencia e where e.id = '"
								+ facultadSel + "'");
				dependenciaItem = new SelectItem[dependenciasUN.size()];
				for (int i = 0; i < dependenciasUN.size(); i++) {
					Dependencia dd = (Dependencia) dependenciasUN.get(i);
					dependenciaItem[i] = new SelectItem(dd.getId(),
							dd.getNombre());
					dd = null;
				}

			}
		}

  	}
	
	public void consultarProyectos(){
		listadoPersonas = new ArrayList<Persona>();
		String filtro="";

		if (mostrarFacultades){
			if(facultadSel.equals("0")){
				filtro=" and invInt.dependencia.sede.id="+sedeSel+" ";
			}
			else{
				filtro=" and invInt.dependencia.id='"+facultadSel+"' ";
			}			
		}
		else{
			if(sedeSel.equals("1")){
				filtro="";
			}
			else{
				filtro=" and invInt.dependencia.sede.id="+sedeSel+" ";
			}
		}
		
		String sql="select pi.idEstudiante as documento, pi.tipoDocEstudiante as tipoDocumento, concat(invInt.apellido1,' ', invInt.apellido2,' ', invInt.nombre1) as nombre, count(pi.proyecto) as numero, pi.proyecto.id as numProy, pi.proyecto.nombre  as nombreProy"+ 
		" from ProyectoInforme pi, Convocatoria conv, InvestigadorInterno invInt"+
		" where conv.padre="+convocatoriaSel+" and conv.id= pi.proyecto.modalidad.id and pi.idEstudiante = invInt.id.documento and pi.tipoDocEstudiante= invInt.id.tipoDocumento"+
		filtro+
		" group by pi.proyecto, pi.tipoDocEstudiante, pi.idEstudiante, invInt.dependencia.id, invInt.apellido1, invInt.apellido2, invInt.nombre1, invInt.nombre1, pi.proyecto.id, pi.proyecto.nombre";
				
		
		String sql1="select pi.proyecto.id, pi.idEstudiante, pi.tipoDocEstudiante, count(pi) as nroInfomes from ProyectoInforme pi where pi.estadoInforme <> '"+ProyectoInforme.ESTADO_BORRADO+"' and pi.proyecto.id is not null and  pi.idEstudiante is not null and pi.tipoDocEstudiante is not null group by pi.tipoDocEstudiante, pi.idEstudiante, pi.proyecto.id";			

		List listaEstudiantes= servicioGeneral
			.obtenerObjetos(sql1);
		
	
		listaEstudiantes= servicioGeneral
				.obtenerObjetos(sql);
		List<ProyectoInforme>  listaProyectoInforme=new ArrayList<ProyectoInforme>();
		listaProyectoInforme= servicioGeneral.obtenerObjetosLimitado(ProyectoInforme.class, "select  #id pi.id, #avanceResultados pi.avanceResultados, #resumenTecnico pi.resumenTecnico  from ProyectoInforme pi where pi.id=25");
		
		Iterator it = listaEstudiantes.iterator();			
		while(it.hasNext()){
			Object[] invPro = (Object[]) it.next();
			Persona persona = new Persona((String)invPro[0], (String) invPro[1]);
			persona.setNombre1((String) invPro[2]);			
			persona.setDireccion((String) invPro[4].toString()+" - "+(String) invPro[5].toString());
			persona.setEdad(invPro[3].toString());	
			persona.setCelular(invPro[4].toString());
			//persona.setEdad(invPro.);
			listadoPersonas.add(persona);
		}		
		
	}
	
	public String listarInformeEstudiante(){
		IdPersona idPer = personaSeleccionada.getId();		
		sesion.setAttribute("JovenInvestigador", personaSeleccionada);
		sesion.setAttribute("idProyecto", personaSeleccionada.getCelular());
		sesion.setAttribute("esConsultaJovenesInv", true);	
		sesion.removeAttribute("manejadorPrincipalInforme");
		return "principalInforme";
	}	

	public List<ConvocatoriaPadre> getListadoConvocatoriaPadre() {
		return listadoConvocatoriaPadre;
	}

	public void setListadoConvocatoriaPadre(List<ConvocatoriaPadre> listadoConvocatoriaPadre) {
		this.listadoConvocatoriaPadre = listadoConvocatoriaPadre;
	}

	public List<Sede> getListaSedes() {
		return listaSedes;
	}

	public void setListaSedes(List<Sede> listaSedes) {
		this.listaSedes = listaSedes;
	}
	
	public boolean isMostrarFacultades() {
		return mostrarFacultades;
	}

	public void setMostrarFacultades(boolean mostrarFacultades) {
		this.mostrarFacultades = mostrarFacultades;
	}

	public String getSedeSel() {
		return sedeSel;
	}

	public void setSedeSel(String sedeSel) {
		this.sedeSel = sedeSel;
	}

	public List<Dependencia> getDependenciasUN() {
		return dependenciasUN;
	}

	public void setDependenciasUN(List<Dependencia> dependenciasUN) {
		this.dependenciasUN = dependenciasUN;
	}

	public SelectItem[] getDependenciaItem() {
		return dependenciaItem;
	}

	public void setDependenciaItem(SelectItem[] dependenciaItem) {
		this.dependenciaItem = dependenciaItem;
	}

	public List<Dependencia> getFacultadesUN() {
		return facultadesUN;
	}

	public void setFacultadesUN(List<Dependencia> facultadesUN) {
		this.facultadesUN = facultadesUN;
	}

	public SelectItem[] getFacultadItem() {
		return facultadItem;
	}

	public void setFacultadItem(SelectItem[] facultadItem) {
		this.facultadItem = facultadItem;
	}

	public String getFacultadSel() {
		return facultadSel;
	}

	public void setFacultadSel(String facultadSel) {
		this.facultadSel = facultadSel;
	}

	public SelectItem[] getSedeItem() {
		return sedeItem;
	}

	public void setSedeItem(SelectItem[] sedeItem) {
		this.sedeItem = sedeItem;
	}

	

	public String getConvocatoriaSel() {
		return convocatoriaSel;
	}

	public void setConvocatoriaSel(String convocatoriaSel) {
		this.convocatoriaSel = convocatoriaSel;
	}
	
	public Persona getPersonaSeleccionada() {
		return personaSeleccionada;
	}

	public void setPersonaSeleccionada(Persona personaSeleccionada) {
		this.personaSeleccionada = personaSeleccionada;
	}


	public SelectItem[] getConvocatoriaItem() {
		return convocatoriaItem;
	}


	public void setConvocatoriaItem(SelectItem[] convocatoriaItem) {
		this.convocatoriaItem = convocatoriaItem;
	}


	public String getEstudiantesMostrar() {
		return estudiantesMostrar;
	}


	public void setEstudiantesMostrar(String estudiantesMostrar) {
		this.estudiantesMostrar = estudiantesMostrar;
	}
	
	public List<Persona> getListadoPersonas() {
		return listadoPersonas;
	}


	public void setListadoPersonas(List<Persona> listadoPersonas) {
		this.listadoPersonas = listadoPersonas;
	}


}
