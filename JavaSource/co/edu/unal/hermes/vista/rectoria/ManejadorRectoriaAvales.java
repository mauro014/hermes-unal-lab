package co.edu.unal.hermes.vista.rectoria;

import java.util.List;

import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorRectoriaAvales extends ManejadorBase {

    private static final long serialVersionUID = 1L;
    private List<Aval> listaAvalesPendientes;

    public ManejadorRectoriaAvales() {
        listaAvalesPendientes = servicioGeneral.obtenerAvalesRectoria();
    }

    public int getTamañoListaPendientes() {
        if (listaAvalesPendientes != null) {
            return listaAvalesPendientes.size();
        } else {
            return 0;
        }
    }

	public List<Aval> getListaAvalesPendientes() {
		return listaAvalesPendientes;
	}

	public void setListaAvalesPendientes(List<Aval> listaAvalesPendientes) {
		this.listaAvalesPendientes = listaAvalesPendientes;
	}
    
    
}
