package co.edu.unal.hermes.modelo.correo;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import co.edu.unal.hermes.modelo.Persona;

public class Correo {

	public static String CORREO_HERMES = "avisohermes_nal@unal.edu.co";
	public static String CORREO_HERMES_SOLICITUDES = "avisohermes_nal@unal.edu.co";
	public static String CORREO_HERMES_COMUNICACIONES = "sisii_nal@unal.edu.co";
	public static String CORREO_ANLA = "licencias@anla.gov.co";
	public static String CORREO_BIODIVERSIDAD_UNAL = "perminambiente@unal.edu.co";
	public static String CORREO_BOLETIN = "enviosvri_nal@unal.edu.co";

	boolean pruebas = false;
	private List<Persona> personas = new ArrayList<Persona>();
	private String asunto;
	private String cuerpo;
	private String adjunto;
	private String nombreAdjunto;
	private String origen;
	private List<String> direcciones = new ArrayList<String>();
	private List<String> copiaOculta = new ArrayList<String>();
	private List<String> adjuntos = new ArrayList<String>();
	private String copias;
	private List<String> envios = new ArrayList<String>();
	private URL url;

	public List<String> getEnvios() {
		return envios;
	}

	public void setEnvios(List<String> envios) {
		this.envios = envios;
	}

	public void adicionarPersona(Persona persona) {
		if(pruebas){
			persona.setEmail("hermes@unal.edu.co");
		}
		personas.add(persona);
	}

	public void adicionarDireccion(String direccion) {
		if(pruebas){
			direccion = "hermes@unal.edu.co";
		}
		if (direccion != null) {
			direcciones.add(direccion);
		}
	}

	public String getAdjunto() {
		return adjunto;
	}

	public void setAdjunto(String adjunto) {
		this.adjunto = adjunto;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

	public List<Persona> getPersonas() {
		return personas;
	}

	public String getNombreAdjunto() {
		return nombreAdjunto;
	}

	public void setNombreAdjunto(String nombreAdjunto) {
		this.nombreAdjunto = nombreAdjunto;
	}

	public List<String> getDirecciones() {
		return direcciones;
	}

	public void setDirecciones(List<String> direcciones) {
		this.direcciones = direcciones;
	}

	public void adicionarCopiaOculta(String copiaOculta) {
		this.copiaOculta.add(copiaOculta);
	}

	public List<String> getCopiaOculta() {
		return copiaOculta;
	}

	public void setCopiaOculta(List<String> copiaOculta) {
		this.copiaOculta = copiaOculta;
	}

	public List<String> getAdjuntos() {
		return adjuntos;
	}

	public void setAdjuntos(List<String> adjuntos) {
		this.adjuntos = adjuntos;
	}

	public void adicionarAdjunto(String a) {
		adjuntos.add(a);
	}

	public void eliminarAdjunto(String a) {
		adjuntos.remove(a);
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getCopias() {
		return copias;
	}

	public void setCopias(String copias) {

		int tamaño;
		int largo;
		int restante;
		List<String> contador = new ArrayList<String>();
		largo = copias.length();
		String cadena = new String();
		String partir = new String();
		tamaño = copias.indexOf(";");

		if (copias != null && tamaño == -1) {
			cadena = copias.substring(0, largo);
			contador.add(cadena);
		}
		while (tamaño != -1) {
			cadena = copias.substring(0, tamaño);
			contador.add(cadena);
			cadena = null;
			partir = copias.substring(tamaño + 1, largo);
			copias = partir;
			tamaño = partir.indexOf(";");
			restante = partir.indexOf(".");
			if (restante != -1 && tamaño == -1) {
				largo = partir.length();
				cadena = copias.substring(0, largo);
				contador.add(cadena);
			}
			largo = partir.length();
		}
		this.setEnvios(contador);
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

}
