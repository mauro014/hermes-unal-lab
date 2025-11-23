package co.edu.unal.hermes.vista.asesor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.ManejadorBase;
import co.edu.unal.hermes.vista.proyectos.ProyectoVista;

/**
 * @author Juan Pablo Duque
 * @modified Rodrigo Gallo, Mauricio Amaya Rios
 * Fecha ultima modificación: 26-05-2014
 */

public class ManejadorProyectosConvocatoria extends ManejadorBase implements Serializable  {

	private static final long serialVersionUID = -277417865592814990L;
	//Campo para saber si se muestra el boton de no encontrado al cargar la página
	//primera vez.
	private boolean noPrimerBusqueda = false;
	private boolean personaEsAdministrador = false;
	private boolean personaEsCoordinador = false;

	private List<ProyectoVista> listaProyectosVista;
	
	private boolean mostrarProyectos;
	
	// Variables para los campos de busqueda
	private String nombreProyecto;
	private String nombreInvestigador;
	private String apellidoInvestigador;
	private String idProyecto;
	private String codigoDib;
	private String documentoInvestigador;
	private String nombreConvocatoriaPadre;
	private String codigoQuipu;
	
	private ProyectoVista proyectoSeleccionado;
	
	private Persona coordinadorSeguimiento;
	
	private ProyectoVista proyectoVistaSeleccionado;
	
	private boolean busquedaExactaInv = false;
	private boolean busquedaExactaPry = false;
	
	// Proyectos para reporte
	
	private String proyectos;
	
	public ManejadorProyectosConvocatoria() {
		
		//Listas necesarias para mostrar los proyectos encontrados
		listaProyectosVista = new ArrayList<ProyectoVista>();		
		activarPaneles();
		
	}

	public void buscarProyectos() {
		
		noPrimerBusqueda = true;

		
		List<Proyecto> listaProyectosActivos = new ArrayList<Proyecto>();
		
		idProyecto = idProyecto.trim();
		if (!idProyecto.equals("")) {
            try {
                Proyecto proyecto = servicioProyecto.obtenerProyecto(new Long(idProyecto), ProyectoDAOHibernate.LIMPIO);
                if (proyecto != null && !(proyecto.getEstadoProyecto().getId().equals(EstadoProyecto.BORRADO)
                        || proyecto.getEstadoProyecto().getId().equals(EstadoProyecto.VERSION_INICIAL))) {
                    listaProyectosActivos.add(proyecto);
                }
            }
			catch(Exception e){
				e.printStackTrace();
			}
		} else if (documentoInvestigador != null && documentoInvestigador.trim().length() > 3) {

			IdPersona id = new IdPersona();
			id.setDocumento(documentoInvestigador);

			id.setTipoDocumento(TipoDocumento.CEDULA);

			Investigador investigadorActual = servicioPersona.obtenerInvestigadorProyectos(id);
			if (investigadorActual == null) {
				id.setTipoDocumento(TipoDocumento.CEDULA_EXTRANJERIA);
				investigadorActual = servicioPersona.obtenerInvestigadorProyectos(id);
				if (investigadorActual == null) {
					id.setTipoDocumento(TipoDocumento.NIP);
					investigadorActual = servicioPersona.obtenerInvestigadorProyectos(id);
					if (investigadorActual == null) {
						id.setTipoDocumento(TipoDocumento.PASAPORTE);
						investigadorActual = servicioPersona.obtenerInvestigadorProyectos(id);
						if (investigadorActual == null) {
							id.setTipoDocumento(TipoDocumento.TARJETA_IDENTIDAD);
							investigadorActual = servicioPersona.obtenerInvestigadorProyectos(id);
						}
					}
				}
			}
			if (investigadorActual != null) {
				List<Proyecto> proyectos = obtenerProyectosActivosInvestigador(investigadorActual);
				listaProyectosActivos.addAll(proyectos);
			}
		} else if ((nombreInvestigador != null && nombreInvestigador.trim().length() > 3)
				|| (apellidoInvestigador != null && apellidoInvestigador.trim().length() > 3)) {

			List<Investigador> investigadores = servicioPersona
					.obtenerInvestigadoresPorNombresYApellidos(nombreInvestigador, apellidoInvestigador, isBusquedaExactaInv());
			Iterator<Investigador> it = investigadores.iterator();
			while (it.hasNext()) {
				Investigador investigador = (Investigador) it.next();
				investigador = servicioPersona.obtenerInvestigadorProyectos(investigador.getId());
				List<Proyecto> proyectos = obtenerProyectosActivosInvestigador(investigador);
				listaProyectosActivos.addAll(proyectos);
			}
			
		} else if (nombreProyecto != null && nombreProyecto.trim().length() > 3) {
			listaProyectosActivos = servicioProyecto.obtenerProyectosPorNombre(nombreProyecto, isBusquedaExactaPry());
		} else if (codigoQuipu != null && codigoQuipu.trim().length() > 3) {
			listaProyectosActivos = servicioProyecto.obtenerProyectosPorCodigoQuipu(codigoQuipu);
		}

		cargarProyectosVista(listaProyectosActivos);
		activarPaneles();
		
		sesion.removeAttribute("proyecto");
	}

	private List<Proyecto> obtenerProyectosActivosInvestigador(Investigador investigador) {
		List<Proyecto> resultado = new ArrayList<Proyecto>();
		try{
			Set<InvestigadorProyecto> listaProyectosInvestigador = investigador.getProyectosInvestigador();
			Iterator<InvestigadorProyecto> iter = listaProyectosInvestigador.iterator();
			while (iter.hasNext()) {
				InvestigadorProyecto investigadorProyecto = (InvestigadorProyecto) iter.next();
				Proyecto p = investigadorProyecto.getProyecto();
				EstadoProyecto estado = p.getEstadoProyecto();
				if (estado != null && !estado.getId().equals(EstadoProyecto.BORRADO) && !estado.getId().equals(EstadoProyecto.VERSION_INICIAL)) {
					if (investigadorProyecto.getTipo().getId().equals(InvestigadorProyecto.PRINCIPAL)) {
						resultado.add(p);
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return resultado;
	}

	private void activarPaneles() {
		mostrarProyectos = true;
		
		if (listaProyectosVista.isEmpty()) {
			mostrarProyectos = false;
		}
	}

	private void cargarProyectosVista(List<Proyecto> listaProyectos) {

		listaProyectosVista.clear();
		Iterator<Proyecto> it_proyectos = listaProyectos.iterator();
		while (it_proyectos.hasNext()) {
			ProyectoVista proyectoVista = new ProyectoVista();
			Proyecto pry = (Proyecto) it_proyectos.next();
			proyectoVista.asignarValores(pry);

			proyectoVista.setCodigoQuipu(pry.getCodigoQuipu());

			proyectoVista.setNombreEstadoProyecto(pry.getEstadoProyecto().getNombre());
			
			listaProyectosVista.add(proyectoVista);
		}

	}
	
	public void proyectosEncontrados() {
		
		proyectos = "";
		
		for(int i=0; i < listaProyectosVista.size(); i++){

			proyectos = proyectos + " " + listaProyectosVista.get(i).getId();

		}

	}
	
	public String imprimirReporteProyectos() {

		proyectosEncontrados();

		ReporteBirt r = new ReporteBirt();
		
		r.setNombreReporte("/reportes-proyectos/reporteProyectosDocente");
		r.adicionarParametro("pry", proyectos);
		
		r.setFormato(ReporteBirt.FORMATO_XLS);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
		    context.getExternalContext().dispatch("/ReporteEngineServlet");
		} catch (Exception e) {
		    System.out.println(e);
		} finally {
		    context.responseComplete();
		}
		return "";
	}

	
	

	public HttpSession getSesion() {
		return sesion;
	}

	public void setSesion(HttpSession sesion) {
		this.sesion = sesion;
	}

	public List getListaProyectosVista() {
		return listaProyectosVista;
	}

	public void setListaProyectosVista(List listaProyectosVista) {
		this.listaProyectosVista = listaProyectosVista;
	}

	public String getNombreProyecto() {
		return nombreProyecto;
	}

	public void setNombreProyecto(String nombreProyecto) {
		this.nombreProyecto = nombreProyecto;
	}

	public String getDocumentoInvestigador() {
		return documentoInvestigador;
	}

	public void setDocumentoInvestigador(String documentoInvestigador) {
		this.documentoInvestigador = documentoInvestigador;
	}

	public String getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(String idProyecto) {
		this.idProyecto = idProyecto;
	}

	public String getCodigoDib() {
		return codigoDib;
	}

	public void setCodigoDib(String codigoDib) {
		this.codigoDib = codigoDib;
	}

	public String getApellidoInvestigador() {
		return apellidoInvestigador;
	}

	public void setApellidoInvestigador(String apellidoInvestigador) {
		this.apellidoInvestigador = apellidoInvestigador;
	}

	public String getNombreInvestigador() {
		return nombreInvestigador;
	}

	public void setNombreInvestigador(String nombreInvestigador) {
		this.nombreInvestigador = nombreInvestigador;
	}

	public boolean isPersonaEsAdministrador() {
		return personaEsAdministrador;
	}

	public void setPersonaEsAdministrador(boolean personaEsAdministrador) {
		this.personaEsAdministrador = personaEsAdministrador;
	}

	public String getNombreConvocatoriaPadre() {
		return nombreConvocatoriaPadre;
	}

	public void setNombreConvocatoriaPadre(String nombreConvocatoriaPadre) {
		this.nombreConvocatoriaPadre = nombreConvocatoriaPadre;
	}

	public boolean isPersonaEsCoordinador() {
		return personaEsCoordinador;
	}

	public void setPersonaEsCoordinador(boolean personaEsCoordinador) {
		this.personaEsCoordinador = personaEsCoordinador;
	}

	public Persona getCoordinadorSeguimiento() {
		return coordinadorSeguimiento;
	}

	public void setCoordinadorSeguimiento(Persona coordinadorSeguimiento) {
		this.coordinadorSeguimiento = coordinadorSeguimiento;
	}
	
	/**
	 * @return the proyectoVistaSeleccionado
	 */
	public ProyectoVista getProyectoVistaSeleccionado() {
		return proyectoVistaSeleccionado;
	}

	/**
	 * @param proyectoVistaSeleccionado the proyectoVistaSeleccionado to set
	 */
	public void setProyectoVistaSeleccionado(ProyectoVista proyectoVistaSeleccionado) {
		this.proyectoVistaSeleccionado = proyectoVistaSeleccionado;
	}
	
	public String cargarProyecto(){
		if(proyectoSeleccionado != null){
			sesion.setAttribute("idProyectoSeguimientoCoordinador",proyectoSeleccionado.getId());
			return "informacionProyecto";
		}
		return "";
	}
	
	public String cargarProyectoLeg(){
		if(proyectoSeleccionado != null){
			sesion.setAttribute("idProyectoSeguimientoCoordinador",proyectoSeleccionado.getId());
			sesion.removeAttribute("manejadorSeguimiento");
			return "informacionProyectoLeg";
		}
		return "";
	}

	public void setProyectoSeleccionado(ProyectoVista proyectoSeleccionado) {
		this.proyectoSeleccionado = proyectoSeleccionado;
	}

	public ProyectoVista getProyectoSeleccionado() {
		return proyectoSeleccionado;
	}

	public boolean isMostrarProyectos() {
		return mostrarProyectos;
	}

	public void setMostrarProyectos(boolean mostrarProyectos) {
		this.mostrarProyectos = mostrarProyectos;
	}

	public boolean isNoPrimerBusqueda() {
		return noPrimerBusqueda;
	}

	public void setNoPrimerBusqueda(boolean noPrimerBusqueda) {
		this.noPrimerBusqueda = noPrimerBusqueda;
	}

	public String getProyectos() {
		return proyectos;
	}

	public void setProyectos(String proyectos) {
		this.proyectos = proyectos;
	}

	public String getCodigoQuipu() {
		return codigoQuipu;
	}

	public void setCodigoQuipu(String codigoQuipu) {
		this.codigoQuipu = codigoQuipu;
	}

	public boolean isBusquedaExactaInv() {
		return busquedaExactaInv;
	}

	public void setBusquedaExactaInv(boolean busquedaExacta) {
		this.busquedaExactaInv = busquedaExacta;
	}

	public boolean isBusquedaExactaPry() {
		return busquedaExactaPry;
	}

	public void setBusquedaExactaPry(boolean busquedaExactaPry) {
		this.busquedaExactaPry = busquedaExactaPry;
	}

}
