package co.edu.unal.hermes.modelo;  
  
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;  
  
public class PreinscripcionECPDataModel extends ListDataModel<Preinscripcion_ECP> implements SelectableDataModel<Preinscripcion_ECP> {    
  
    public PreinscripcionECPDataModel() {  
    }  
  
    public PreinscripcionECPDataModel(List<Preinscripcion_ECP> data) {  
        super(data);  
    }  
      
    public Preinscripcion_ECP getRowData(String rowKey) {  
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  
          
        List<Preinscripcion_ECP> wrappedData = (List<Preinscripcion_ECP>) getWrappedData();
		List<Preinscripcion_ECP> listaPreinscripciones = wrappedData;  
          
        for(Preinscripcion_ECP preinscripcion : listaPreinscripciones) {  
            if(preinscripcion.getId_pre() == Long.parseLong(rowKey))
                return preinscripcion;  
        }  
          
        return null;  
    }  
  
    public Object getRowKey(Preinscripcion_ECP preinscripcion) {  
        return preinscripcion.getId_pre();  
    }  
}