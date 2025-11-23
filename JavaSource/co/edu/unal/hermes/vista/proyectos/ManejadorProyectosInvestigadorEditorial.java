package co.edu.unal.hermes.vista.proyectos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.Proyecto;

/**
 * The Class ManejadorProyectosInvestigadorLaboratorios.
 */
public class ManejadorProyectosInvestigadorEditorial extends ManejadorBaseProyectosInvestigador {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5748404708331504634L;

    /**
     * Instantiates a new manejador proyectos investigador laboratorios.
     */
    public ManejadorProyectosInvestigadorEditorial() {
        super();
    }

    @Override
    protected void cargarListasProyectos() {

        // Se cargan los proyectos del investigador principal
        List<InvestigadorProyecto> listaProyectosInvestigador = new ArrayList<InvestigadorProyecto>();
        try {
            listaProyectosInvestigador.addAll(investigadorActual.getProyectosInvestigador());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Iterator<InvestigadorProyecto> iter = listaProyectosInvestigador.iterator();

        List<InvestigadorProyecto> listaProyectosActivos = new ArrayList<InvestigadorProyecto>();
        while (iter.hasNext()) {
            InvestigadorProyecto investigadorProyecto = (InvestigadorProyecto) iter.next();
            Proyecto p = investigadorProyecto.getProyecto();

            if (!p.getEstadoProyecto().getId().equals(EstadoProyecto.BORRADO)
                    && !investigadorProyecto.getTipo().getId().equals("A")
                    && (p.getModalidad().getTipo().getId().equals("SIS")
                            || p.getModalidad().getTipo().getId().equals("CLN")
                            || (p.getModalidad().getTipo().getId().equals("CL")))) {
                listaProyectosActivos.add(investigadorProyecto);

            }
        }
        
        try {
        	 
        	List<Proyecto> pryActual = servicioGeneral
            .obtenerObjetosLimitado(
                    Proyecto.class,
                    "select #id p.id from Proyecto p where p.estadoProyecto.id <> 'B' and p.creadorId = "+personaActual.getId().getDocumento());

        	if(pryActual!=null && pryActual.size()>0) {
        		for(int i=0;i<pryActual.size();i++) {
        			    Proyecto proyecto = servicioProyecto.obtenerProyecto(pryActual.get(i).getId(), ProyectoDAOHibernate.INFORMACION_GENERAL);
        			if ((proyecto.getModalidad().getTipo().getId().equals("SIS")
                                    || proyecto.getModalidad().getTipo().getId().equals("CLN")
                                    || (proyecto.getModalidad().getTipo().getId().equals("CL")))) {
                		boolean proyectoExiste = false;        				
        				
        				InvestigadorProyecto ip = proyecto.getInvestigadorPrincipalVista();
        				ip.setProyecto(proyecto);
        				
        				for(int j=0; j<listaProyectosActivos.size();j++) {
        					if(proyecto.getId().equals(listaProyectosActivos.get(j).getProyecto().getId())) {
        						proyectoExiste = true;
        						break;
        					}
        				}
        				
        				if(!proyectoExiste) {
        					listaProyectosActivos.add(ip);
        				}
        				

                    }

        		}

        	}
        
        }catch (Exception e){
        	e.printStackTrace();
        }

        
        
        cargarProyectosVista(listaProyectosActivos);
        cargarConvocatoriasArtículos();
    }

}
