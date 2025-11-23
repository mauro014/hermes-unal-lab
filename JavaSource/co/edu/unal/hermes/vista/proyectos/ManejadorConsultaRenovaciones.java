package co.edu.unal.hermes.vista.proyectos;

import java.util.ArrayList;
import java.util.List;

import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.ProyectoInforme;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorConsultaRenovaciones extends ManejadorBase {

	private List<Persona>  listadoPersonas;
	private Persona personaSeleccionada;
	public static final long ESTADOS_SOLICITUD_RENOVACION = 30;
	private Boolean consultarPorFacultad=false;


	public boolean isConsultarPorFacultad() {
		return consultarPorFacultad;
	}

	public void setConsultarPorFacultad(boolean consultarPorFacultad) {
		this.consultarPorFacultad = consultarPorFacultad;
	}

	public Persona getPersonaSeleccionada() {
		return personaSeleccionada;
	}

	public void setPersonaSeleccionada(Persona personaSeleccionada) {
		this.personaSeleccionada = personaSeleccionada;
	}

	public ManejadorConsultaRenovaciones() {
		super();		
		sesion.removeAttribute("manejadorSemillerosSolicitudVIF");
		sesion.removeAttribute("manejadorSemillerosConsultaVIF");
		String consulta="";
		if(sesion.getAttribute("consultarPorFacultad") != null){
			consultarPorFacultad =  (Boolean) sesion.getAttribute("consultarPorFacultad");		
			sesion.removeAttribute("consultarPorFacultad");
		}
				
		if (consultarPorFacultad){
			Persona persona2 = (Persona)sesion.getAttribute("persona");
			InvestigadorInterno investigador = servicioPersona.obtenerInvestigadorInterno(persona2.getId());
			String facultad = investigador.getDependencia().getFacultad().getId();
			
			consulta="select #id.documento  invInt.id.documento, #id.tipoDocumento invInt.id.tipoDocumento, #apellido1 invpr.investigador.apellido1, #apellido2  invpr.investigador.apellido2, #nombre1 invpr.investigador.nombre1,"+
					" #nombre2 invpr.investigador.nombre2, #direccion invInt.dependencia.sede.nombre, #email invInt.dependencia.nombre, #coorNombreDependencia dom.descripcion, #edad invpr.totalHorasVinculacion, #genero count(proInf)"+
					" from  InvestigadorProyecto invpr,  InvestigadorInterno invInt,  DominioDetalle dom,  Convocatoria conv, ProyectoInforme proInf"+
					" where invInt.dependencia='"+facultad+"' and invpr.investigador.id.documento= invInt.id.documento  and invpr.investigador.id.tipoDocumento= invInt.id.tipoDocumento  and dom.identificador.id ="+ESTADOS_SOLICITUD_RENOVACION+" and (dom.identificador.tipo= invpr.visible)"+
					" and invpr.proyecto.modalidad= conv.id and proInf.estadoInforme <> '"+ProyectoInforme.ESTADO_BORRADO+"' and invpr.proyecto.id="+PROYECTO_SOLICITUD_RENOVACION+" and proInf.proyecto.id= invpr.proyecto.id and proInf.numeroActoAdministrativoFacultad=invpr.investigador.id.documento and proInf.pryTieneSaldoEjecucion=invInt.id.tipoDocumento"+
					" group by  invInt.id.documento,  invInt.id.tipoDocumento,  invpr.investigador.apellido1,  invpr.investigador.apellido2,  invpr.investigador.nombre1,  invpr.investigador.nombre2,  invInt.dependencia.sede.nombre,  invInt.dependencia.nombre,  dom.descripcion,  conv.padre.ano, invpr.totalHorasVinculacion";
		}
		else{
			consulta="select #id.documento  invInt.id.documento, #id.tipoDocumento invInt.id.tipoDocumento, #apellido1 invpr.investigador.apellido1, #apellido2  invpr.investigador.apellido2, #nombre1 invpr.investigador.nombre1,"+
					" #nombre2 invpr.investigador.nombre2, #direccion invInt.dependencia.sede.nombre, #email invInt.dependencia.nombre, #coorNombreDependencia dom.descripcion, #edad invpr.totalHorasVinculacion, #genero count(proInf)"+
					" from  InvestigadorProyecto invpr,  InvestigadorInterno invInt,  DominioDetalle dom,  Convocatoria conv, ProyectoInforme proInf"+
					" where invpr.investigador.id.documento= invInt.id.documento  and invpr.investigador.id.tipoDocumento= invInt.id.tipoDocumento  and dom.identificador.id ="+ESTADOS_SOLICITUD_RENOVACION+" and (dom.identificador.tipo= invpr.visible)"+
					" and invpr.proyecto.modalidad= conv.id and proInf.estadoInforme <> "+ProyectoInforme.ESTADO_BORRADO+" and invpr.proyecto.id="+PROYECTO_SOLICITUD_RENOVACION+" and proInf.proyecto.id= invpr.proyecto.id and proInf.numeroActoAdministrativoFacultad=invpr.investigador.id.documento and proInf.pryTieneSaldoEjecucion=invInt.id.tipoDocumento"+
					" group by  invInt.id.documento,  invInt.id.tipoDocumento,  invpr.investigador.apellido1,  invpr.investigador.apellido2,  invpr.investigador.nombre1,  invpr.investigador.nombre2,  invInt.dependencia.sede.nombre,  invInt.dependencia.nombre,  dom.descripcion,  conv.padre.ano, invpr.totalHorasVinculacion";
					
		}
		listadoPersonas = new ArrayList<Persona>();
		listadoPersonas=servicioGeneral.obtenerObjetosLimitado(Persona.class, consulta);
		
		List listaInformes;
		List listaEstadoRenovacion=new ArrayList();
		Persona persona;		
	}

	public List<Persona> getListadoPersonas() {
		return listadoPersonas;
	}

	public void setListadoPersonas(List<Persona> listadoPersonas) {
		this.listadoPersonas = listadoPersonas;
	}
	
	public String cargarSolicitudes(){
		IdPersona idPer = personaSeleccionada.getId();
		sesion.setAttribute("idPersonaRenovacion", idPer);
		sesion.setAttribute("consultaFacultad", consultarPorFacultad);		
		sesion.removeAttribute("manejadorPrincipalInforme");
		return "principalInforme";	
		}
	
	
}
