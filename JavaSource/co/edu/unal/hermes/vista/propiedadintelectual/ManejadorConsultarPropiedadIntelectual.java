package co.edu.unal.hermes.vista.propiedadintelectual;

import java.util.List;

import co.edu.unal.hermes.modelo.HistoricoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.InvestigadorInterno;

public class ManejadorConsultarPropiedadIntelectual extends ManejadorBasePropiedadIntelectual {

    /**
     * 
     */
    private static final long serialVersionUID = 6571907489586452571L;
    private Long codigo;
    private boolean ver;
    List<HistoricoPropiedadIntelectual> listaHistoricoPropiedad;
    private HistoricoPropiedadIntelectual historicoPropiedad;

    public ManejadorConsultarPropiedadIntelectual() {
        ver = false;
    }

    public void buscarPropiedadIntelectual() {
        propiedad = servicioPropiedadIntelectual.obtenerRegistroPropiedadIntelectual(codigo);
        if (propiedad != null) {
            if (sesion.getAttribute(ROL_INGRESO_PI) != null) {
                verificarAutorizacion();
            }
        } else {
            ver = false;
            mensajeError("No se encontró un registro de propiedad intelectual con el código ingresado.");
        }
    }
    
    public void verificarAutorizacion(){
        boolean autorizacion = false;
        if ("N".equals(sesion.getAttribute(ROL_INGRESO_PI))) {
            autorizacion = true;
        } else {
            InvestigadorInterno ii = servicioPersona.obtenerInvestigadorInterno(cargarPersonaActual().getId());
            if (ii != null) {
                if (propiedad.getDependenciaSolicitante()!=null && propiedad.getDependenciaSolicitante().getSede().getId()
                        .equals(ii.getDependencia2().getSede().getId())) {
                    autorizacion = true;
                } else if (propiedad.getDependenciaApoyo()!=null && propiedad.getDependenciaApoyo()
                        .equals(ii.getDependencia2().getSede().getId().toString())) {
                    autorizacion = true;
                } 
            }
        }
        if (autorizacion) {
            listaHistoricoPropiedad = servicioPropiedadIntelectual
                    .obtenerHistoricoPropiedadIntelectual(propiedad.getId());
            ver = true;
        } else {
            ver = false;
            mensajeError(
                    "No tiene autorización para consultar la información del registro de propiedad intelectual asociado al código ingresado.");
        }
    }
    
    public void cancelar(){
        codigo = null;
    }

    public boolean isVer() {
        return ver;
    }

    public void setVer(boolean ver) {
        this.ver = ver;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public List<HistoricoPropiedadIntelectual> getListaHistoricoPropiedad() {
        return listaHistoricoPropiedad;
    }

    public void setListaHistoricoPropiedad(List<HistoricoPropiedadIntelectual> listaHistoricoPropiedad) {
        this.listaHistoricoPropiedad = listaHistoricoPropiedad;
    }

    public HistoricoPropiedadIntelectual getHistoricoPropiedad() {
        return historicoPropiedad;
    }

    public void setHistoricoPropiedad(HistoricoPropiedadIntelectual historicoPropiedad) {
        this.historicoPropiedad = historicoPropiedad;
    }

}
