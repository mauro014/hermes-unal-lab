package co.edu.unal.hermes.vista.aval;

import java.util.List;

import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorConsultaAvalDRE extends ManejadorBase {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<Aval> listaAval;

    public ManejadorConsultaAvalDRE() {
        listaAval = servicioGeneral.consultaAvalesDRE();
    }

    public int getTamañoLista() {
        if (listaAval != null) {
            return listaAval.size();
        } else {
            return 0;
        }
    }

    public List<Aval> getListaAval() {
        return listaAval;
    }

    public void setListaAval(List<Aval> listaAvales) {
        this.listaAval = listaAvales;
    }
}
