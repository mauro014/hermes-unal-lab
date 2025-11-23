package co.edu.unal.hermes.vista.proyectos;

import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorRealizarSolicitudFuente extends ManejadorBase {

	private boolean mensajeError;
	private String nombreFuenteFinanciacion;

	public ManejadorRealizarSolicitudFuente() {
		try{
		/*String mostrar = (String) sesion.getAttribute("mensajeTramite");
        
		if(mostrar!=null){
			FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, mostrar,""));
			mensajeError=true;
			System.out.println(mostrar);
		}
		else{
			mensajeError=false;
		}
		*/
		
		} catch (Exception e) {
    	    System.out.println(e.toString());
        }
	}

	
    
	public String irSolicitudFuente(){
	  try{
		//sesion.removeAttribute("manejadorAdministrarSolicitudesUsuario");
		//sesion.removeAttribute("solicitudId");
		//sesion.setAttribute("solicitudId", solicitudSeleccionada.getId());
	   } catch (Exception e) {
  	    System.out.println(e.toString());
        }
	 
	  return "solFuenteFinanciacion";
	}
	
	public void crearSolicitudFuente(){
		  try{
			
		   } catch (Exception e) {
	  	    System.out.println(e.toString());
	        }
		 
		 
		}
	
	public Boolean buscarFuenteFinanciacion(){
		Boolean sql = true;  
		try{
			//sql = servicioGeneral.ejecutarSentencia("SELECT descripcion FROM FuenteFinanciacion WHERE"+"'"+getNombreFuenteFinanciacion()+"'"+"like "); 
			System.out.println("FUENTE FINANCIACION"+getNombreFuenteFinanciacion());
		   } catch (Exception e) {
	  	    System.out.println(e.toString());
	        }
		if(sql==true){
			return true;
		}
		else{
			return false;
		}
		 
		}

	
	
	public boolean isMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(boolean mensajeError) {
		this.mensajeError = mensajeError;
	}
	public String getNombreFuenteFinanciacion() {
		return nombreFuenteFinanciacion;
	}



	public void setNombreFuenteFinanciacion(String nombreFuenteFinanciacion) {
		this.nombreFuenteFinanciacion = nombreFuenteFinanciacion;
	}


}
