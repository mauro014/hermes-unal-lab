package co.edu.unal.hermes.modelo.seguimiento;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import co.edu.unal.hermes.modelo.Financiacion;

public class SolicitudAdicionPresupuestal {

	private Long id;
	private Long valorAdicionSolicitud;
	private Solicitud solicitud;
	private Financiacion financiacion;
	private Set<DetalleAdicionPresupuesto> detalleAdicionPresupuesto  = new HashSet<DetalleAdicionPresupuesto>();
	
	public SolicitudAdicionPresupuestal(){
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getValorAdicionSolicitud() {
		return valorAdicionSolicitud;
	}

	public void setValorAdicionSolicitud(Long valorAdicionSolicitud) {
		this.valorAdicionSolicitud = valorAdicionSolicitud;
	}

	public Solicitud getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	
	public Financiacion getFinanciacion() {
		return financiacion;
	}

	public void setFinanciacion(Financiacion financiacion) {
		this.financiacion = financiacion;
	}

	public Set<DetalleAdicionPresupuesto> getDetalleAdicionPresupuesto() {
		return detalleAdicionPresupuesto;
	}

	public void setDetalleAdicionPresupuesto(
			Set<DetalleAdicionPresupuesto> detalleAdicionPresupuesto) {
		this.detalleAdicionPresupuesto = detalleAdicionPresupuesto;
	}
	
    public List<DetalleAdicionPresupuesto> getListaDetallesAdicionPresupuesto(){
        List<DetalleAdicionPresupuesto> listaDetallesAdicionPresupuesto = new ArrayList<DetalleAdicionPresupuesto>(detalleAdicionPresupuesto);
        return listaDetallesAdicionPresupuesto;
    }
    
    public void adicionarDetalleAdicionPresupuesto(DetalleAdicionPresupuesto detalle) {
        detalle.setSolicitudAdicion(this);

        if (detalleAdicionPresupuesto == null) {
        	detalleAdicionPresupuesto = new HashSet<DetalleAdicionPresupuesto>();
        }

        detalleAdicionPresupuesto.add(detalle);
    }
    
}