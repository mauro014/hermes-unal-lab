package co.edu.unal.hermes.modelo;  
  
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;  
  
public class InscripcionEscuelaInternacionalDataModel extends ListDataModel<InscripcionEscuelaInternacional> implements SelectableDataModel<InscripcionEscuelaInternacional> {    
  
    public InscripcionEscuelaInternacionalDataModel() {  
    }  
  
    public InscripcionEscuelaInternacionalDataModel(List<InscripcionEscuelaInternacional> data) {  
        super(data);  
    }  
      
    public InscripcionEscuelaInternacional getRowData(String rowKey) {  
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  
          
        List<InscripcionEscuelaInternacional> wrappedData = (List<InscripcionEscuelaInternacional>) getWrappedData();
		List<InscripcionEscuelaInternacional> listaPreinscripciones = wrappedData;  
          
        for(InscripcionEscuelaInternacional preinscripcion : listaPreinscripciones) {  
            if(preinscripcion.getId() == Long.parseLong(rowKey))
                return preinscripcion;  
        }  
          
        return null;  
    }  
  
    public Object getRowKey(InscripcionEscuelaInternacional preinscripcion) {  
        return preinscripcion.getId();  
    }  
}