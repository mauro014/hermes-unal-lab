
package co.edu.unal.hermes.modelo.servicios;

import co.edu.unal.hermes.modelo.ArchivoAdjunto;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.correo.Correo;


public interface IServicioCorreo {
	
	public boolean enviarCorreoSolicitud (Correo correo);
    
    public boolean enviarCorreo(Correo correo);
    
    public boolean enviarCorreo(Correo correo, boolean envioHtml);
    
    public boolean enviarCorreoBoletin(Correo correo);

    public ArchivoAdjunto obtenerArchivoAdjunto(CorreoPlantilla plantillaCorreoActual);

    public CorreoPlantilla obtenerPlantillaCorreoCompleta(Long id);
    
    public void aplicarPlantillaCorreo(Correo correo, CorreoPlantilla plantilla, String rutaAdjunto);
    
    public boolean crearArchivo(String ruta, byte[] bytes);
    public boolean eliminarArchivo(String rutaArchivo);
   
    public void aplicarPlantillaCorreoAdjunto(Correo correo, CorreoPlantilla plantilla, String rutaAdjunto);
}
  