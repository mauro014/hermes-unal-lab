package co.edu.unal.hermes.vista.convertidores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import co.edu.unal.hermes.modelo.Proyecto;

public class ConvertidorProyecto implements Converter{
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		// Convert the unique String representation of Foo to the actual Foo object.
		if(value!=null ){
			System.out.println("valor long "+ value);
			Proyecto pry = new Proyecto();
			try{        		
				pry.setId(new Long(value));
			}
			catch(NumberFormatException e){
				e.printStackTrace();
				return value;
			}
			return pry;
		}
		System.out.println("nulo");
		return null;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException{
		// Convert the Foo object to its unique String representation.
		if(value!=null && value instanceof Proyecto)
		{
			System.out.println("valor string"+value);
			Proyecto pry = (Proyecto)value;
			return (pry.getId()).toString();        	
		}
		System.out.println("nulo");
		return null;
	}
}