package co.edu.unal.hermes.vista.convertidores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

public class ConvertidorLong implements Converter {

    // Init ---------------------------------------------------------------------------------------
    

    // Actions ------------------------------------------------------------------------------------
    
    public Object getAsObject(FacesContext contexto, UIComponent componente, String valor) throws ConverterException {
        // Convert the unique String representation of Foo to the actual Foo object.
//        if(value!=null )
//        {
//        	System.out.println("valor long "+ value);
//        	return Long.valueOf(value);
//        }
//    	System.out.println("nulo");
//        return null;
    	Long nLong;
        try {
             nLong = new Long(valor);
        } catch (NumberFormatException  ex) {
            FacesMessage message = new FacesMessage();
            message.setDetail("El número ingresado no es válido, por favor ingrese el valor sin puntos ni comas.");
            message.setSummary("Número inválido.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ConverterException(message);
        }
        return nLong;
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException{
        // Convert the Foo object to its unique String representation.
    	if(value!=null && value instanceof Long)
        {
    		System.out.println("valor string"+value);
        	return ((Long)value).toString();        	
        }
    	System.out.println("nulo");
        return null;
    }

}