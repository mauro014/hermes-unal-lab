package co.edu.unal.hermes.vista.proyectos.manejadorproyecto;

import java.util.List;

import javax.faces.model.SelectItem;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Programa;
import co.edu.unal.hermes.modelo.Sede;


public class ManejadorInformacionBecasDoctoradoColciencias extends ManejadorProyecto {

	List<Programa> programaPosgrado;
	Sede sede;
	Dependencia dependencia;
	Dependencia facultad;
	String doctoradoSeleccionado;
	SelectItem[] doctoradoItem;
	
	
	public ManejadorInformacionBecasDoctoradoColciencias() {
		super();
		cargarValoresIniciales();
		proyectoActual = servicioProyecto.obtenerProyecto(proyectoActual.getId(), ProyectoDAOHibernate.LINEAS_DEPENDENCIAS_UNICO);
	}

	@Override
	protected void cargarValoresIniciales() {
		//dependencia profesor
		personaActual = (Persona) sesion.getAttribute("persona");
		InvestigadorInterno ii = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());
		sede = ii.getDependencia().getSede();
		dependencia = ii.getDependencia();
		facultad = ii.getDependencia().getFacultad();		
		cargarDoctorados();		
	}
	
	public void cargarDoctorados(){		
		programaPosgrado = servicioGeneral.obtenerObjetos(Programa.class, "select p from Programa p where p.idTipoNivelPrograma = '1' and p.idEstadoPrograma = '1' and p.idDependencia = '" + facultad.getId() + "'" );
		System.out.println("tamaño lista doctorados = " + programaPosgrado.size());
		
		doctoradoItem = new SelectItem[programaPosgrado.size()];
		for (int i = 0; i < programaPosgrado.size(); i++) {
			Programa dd = (Programa) programaPosgrado.get(i);
			doctoradoItem[i] = new SelectItem(dd.getId(), dd.getNombre());
			dd = null;
		}
		doctoradoSeleccionado = ((Programa) programaPosgrado.get(0)).getId().toString();
	}

	@Override
	public String atras() {
		
		return null;
	}

	@Override
	public String salir() {
		
		return null;
	}

	@Override
	public String salirGuardar() {
		
		return null;
	}

	@Override
	public String siguiente() {
		
		return "irSubirArchivo";
	}

	public List<Programa> getProgramaPosgrado() {
		return programaPosgrado;
	}

	public void setProgramaPosgrado(List<Programa> programaPosgrado) {
		this.programaPosgrado = programaPosgrado;
	}

	public Sede getSede() {
		return sede;
	}

	public void setSede(Sede sede) {
		this.sede = sede;
	}

	public Dependencia getDependencia() {
		return dependencia;
	}

	public void setDependencia(Dependencia dependencia) {
		this.dependencia = dependencia;
	}

	public Dependencia getFacultad() {
		return facultad;
	}

	public void setFacultad(Dependencia facultad) {
		this.facultad = facultad;
	}

	public String getDoctoradoSeleccionado() {
		return doctoradoSeleccionado;
	}

	public void setDoctoradoSeleccionado(String doctoradoSeleccionado) {
		this.doctoradoSeleccionado = doctoradoSeleccionado;
	}

	public SelectItem[] getDoctoradoItem() {
		return doctoradoItem;
	}

	public void setDoctoradoItem(SelectItem[] doctoradoItem) {
		this.doctoradoItem = doctoradoItem;
	}



}
