package co.edu.unal.hermes.vista.editorial.proyectos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.vista.proyectos.ManejadorBaseProyectosInvestigador;
import co.edu.unal.hermes.vista.proyectos.ProyectoVista;

public class ManejadorEditorialProyectosHome extends ManejadorBaseProyectosInvestigador {

	private static final long serialVersionUID = 7930413326176690199L;

	public ManejadorEditorialProyectosHome() {
		super();
	}

    @Override
    protected void cargarListasProyectos() {

        // Se cargan los proyectos del investigador principal
        List<InvestigadorProyecto> listaProyectosInvestigador = new ArrayList<InvestigadorProyecto>();
        try {
        	
            investigadorActual = servicioPersona
                    .obtenerProyectosInvestigador(((Persona) sesion.getAttribute("persona")).getId(), true, -1);
//            listaProyectosInvestigador = servicioGeneral.obtenerObjetos(
//					"select ip from PlanGlobalDesarrollo dd where dd.periodo = '" + dd.getObservacion() + "' ");
        	
        	listaProyectosInvestigador.addAll(investigadorActual.getProyectosInvestigador());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        Iterator<InvestigadorProyecto> iter = listaProyectosInvestigador.iterator();

        List<InvestigadorProyecto> listaProyectos = new ArrayList<InvestigadorProyecto>();
        while (iter.hasNext()) {
            InvestigadorProyecto investigadorProyecto = (InvestigadorProyecto) iter.next();
            Proyecto p = investigadorProyecto.getProyecto();

            if (!p.getEstadoProyecto().getId().equals(EstadoProyecto.BORRADO)
                    && !investigadorProyecto.getTipo().getId().equals("A")
                    && (p.getModalidad().getId().equals(1L))) {
                listaProyectos.add(investigadorProyecto);
            }
        }
        cargarProyectosVista(listaProyectos);
    }
    
    public String consultarProyectoEditorial() {
        ProyectoVista proyectoActualAdm = getProyectoSeleccionado();

        sesion.setAttribute("Pro_Editorial_ID", proyectoActualAdm.getId());
        sesion.removeAttribute("ManejadorEditorialProyectosRegistro");
        
        return "registroProyectoEditorial";
    }
    
    public String editarProyectoEditorial() {
        ProyectoVista proyectoActualAdm = getProyectoSeleccionado();

        sesion.setAttribute("Pro_Editorial_ID", proyectoActualAdm.getId());
        sesion.setAttribute("Pro_Editorial_Editar", "Editar-"+proyectoActualAdm.getId());
        sesion.removeAttribute("ManejadorEditorialProyectosRegistro");
        
        return "registroProyectoEditorial";
    }

    public void imprimirReporteProyectoEditorial() {
    	ProyectoVista proyectoActualAdm = getProyectoSeleccionado();
            servicioProyecto.imprimirReporteProyectoEditorial(proyectoActualAdm.getId(), sesion,false); //false, no es evaluador
    }
    
    public void imprimirReporteConceptoEditorial() {
    	ProyectoVista proyectoActualAdm = getProyectoSeleccionado();
            servicioProyecto.imprimirReporteProyectoEditorial(proyectoActualAdm.getId(), sesion,false); //false, no es evaluador
    }
}