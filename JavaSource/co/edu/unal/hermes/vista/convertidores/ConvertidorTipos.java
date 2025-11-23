package co.edu.unal.hermes.vista.convertidores;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.Tipos;

public class ConvertidorTipos implements Converter {

    // Init ---------------------------------------------------------------------------------------
    

    // Actions ------------------------------------------------------------------------------------
    
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
        // Convert the unique String representation of Foo to the actual Foo object.
        if(value!=null && !value.equals(""))
        {       	
        	 List<UIComponent> children = component.getChildren();
             for (UIComponent child : children) {

                 if (child instanceof UISelectItem) {
                     UISelectItem si = (UISelectItem) child;
                     if(si.getValue() != null) {
	                	 if (si.getValue().toString().equals(value)) {
	                          return si.getValue();
	                      }
                     }
                 }

                 if (child instanceof UISelectItems) {
                    UISelectItems sis = (UISelectItems) child;
                   	Object objeto = sis.getValue();
                 	if (objeto instanceof SelectItem[]) {
                 		SelectItem[] itemHijos = (SelectItem[]) objeto;
                 		for(int i=0; i< itemHijos.length; i++) {
                 			SelectItem si = itemHijos[i];
                 			Object hijoTipo = si.getValue();
                 			if (hijoTipo instanceof Tipos) {
                 				Tipos tipo = (Tipos) hijoTipo;
                               	if (tipo.getIdString().equals(value)) {
                               		return tipo;
                               	}
                 			}
                 		}
                   	}
                   	
                   	if (objeto instanceof SelectItem) {
                   		Object objSI = ((SelectItem) objeto).getValue();
                   		if(objSI instanceof Tipos) {
                           	//TODO implementar retorno
                   		}
                   	}
                   	
                 }
             }
        	
        	Tipos tipo=new Tipos();
        	try
        	{
        		
        	tipo.setId(new Long(value));
        	}
        	catch(NumberFormatException e)
        	{
        		e.printStackTrace();
        		return value;
        	}
        	return tipo;
        }
        return null;
        
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException{
        // Convert the Foo object to its unique String representation.
    	if(value!=null && value instanceof Tipos)
        {
        	Tipos tipo=(Tipos) value;
        	if((tipo.getId()) != null)
        		return (tipo.getId()).toString();    	
        }
        return null;
    }

}