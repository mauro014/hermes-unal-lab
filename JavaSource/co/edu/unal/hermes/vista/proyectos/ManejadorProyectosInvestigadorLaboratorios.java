package co.edu.unal.hermes.vista.proyectos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.TipoModalidad;

/**
 * The Class ManejadorProyectosInvestigadorLaboratorios.
 */
public class ManejadorProyectosInvestigadorLaboratorios extends ManejadorBaseProyectosInvestigador {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5748404708331504634L;

    /**
     * Instantiates a new manejador proyectos investigador laboratorios.
     */
    public ManejadorProyectosInvestigadorLaboratorios() {
        super();
    }

    @Override
    protected void cargarListasProyectos() {

        // Se cargan los proyectos del investigador principal
        List<InvestigadorProyecto> listaProyectosInvestigador = new ArrayList<InvestigadorProyecto>();
        try{
            listaProyectosInvestigador.addAll(investigadorActual.getProyectosInvestigador());
        }
        catch(Exception e){
            e.printStackTrace();
        }
        Iterator<InvestigadorProyecto> iter = listaProyectosInvestigador.iterator();

        List<InvestigadorProyecto> listaProyectosActivos = new ArrayList<InvestigadorProyecto>();
        while (iter.hasNext()) {
            InvestigadorProyecto investigadorProyecto = (InvestigadorProyecto) iter.next();
            Proyecto p = investigadorProyecto.getProyecto();

            if (!p.getEstadoProyecto().getId().equals(EstadoProyecto.BORRADO)
                    && !investigadorProyecto.getTipo().getId().equals("A") && (p.getModalidad().getTipo().getId().equals(TipoModalidad.REGISTRO_PROYECTOS_LABORATORIOS))) {
                listaProyectosActivos.add(investigadorProyecto);

            }
        }
        cargarProyectosVista(listaProyectosActivos);
        cargarConvocatoriasArtículos();
    }
    
    

}
