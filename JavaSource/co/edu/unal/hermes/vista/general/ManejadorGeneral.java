package co.edu.unal.hermes.vista.general;

import java.util.Collection;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import co.edu.unal.hermes.vista.ManejadorBase;
import co.edu.unal.hermes.vista.utils.Util;

public class ManejadorGeneral extends ManejadorBase {

	private static final String PREFIJO_CAMPO = "getUd";

	protected boolean mostrarError;

	protected void mostrarWarning(String msg, String field) {
		mostrarWarning(msg, field, "", PREFIJO_CAMPO);
	}

	protected void mostrarWarning(String msg, String field, String prefijoCampo) {
		mostrarWarning(msg, field, "", prefijoCampo);
	}

	protected void mostrarMensaje(FacesMessage msg, UIComponent component) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (component == null) {
			context.addMessage(null, msg);
		} else {
			context.addMessage(component.getClientId(context), msg);
		}

	}

	@SuppressWarnings("deprecation")
	protected void mostrarWarning(String msg, String field, String udId, String prefijoCampo) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, msg);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			if (!Util.getInstace().validarNoVacio(udId)) {
				context.addMessage(((UIComponent) getClass().getMethod(prefijoCampo + field.substring(0, 1).toUpperCase() + field.substring(1)).invoke(this))
						.getClientId(context), message);
			} else {
				context.addMessage(((UIComponent) getClass().getMethod(prefijoCampo + udId.substring(0, 1).toUpperCase() + udId.substring(1)).invoke(this))
						.getClientId(context), message);

			}
			mostrarError = true;
		} catch (Exception e) {
			context.addMessage(null, message);
		}
	}
	
	protected String substringTamanoMaximo(String cadena, int max){
		if(cadena != null){
			if(cadena.length()>max){
				return cadena.substring(0,max);
			}
		}
		return cadena;
	}

	public boolean isMostrarError() {
		FacesContext context = FacesContext.getCurrentInstance();
		return mostrarError && context.getMessages().hasNext();
	}

	public void setMostrarError(boolean mostrarError) {
		this.mostrarError = mostrarError;
	}

}
