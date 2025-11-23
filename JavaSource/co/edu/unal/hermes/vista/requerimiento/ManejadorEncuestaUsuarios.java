package co.edu.unal.hermes.vista.requerimiento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EncuestaSoporte;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorEncuestaUsuarios extends ManejadorBase implements Serializable  {

	EncuestaSoporte encuesta;
	
	public SelectItem[] atencionItems; //{new SelectItem("3", "Excelente"), new SelectItem("2", "Buena"), new SelectItem("1", "Debe mejorar") };
	public SelectItem[] moduloItems = { new SelectItem("SI", "SI"),new SelectItem("NO", "NO") };
	private SelectItem[] sedesItems;
	public SelectItem[] procesoItems;
	
	private String DOMINIO_ATENCION = "ATENCION_SOPORTE";
	private String DOMINIO_PROCESO = "PROCESO_SOPORTE";
	
	public ManejadorEncuestaUsuarios() {
		
		sesion.removeAttribute("ManejadorEncuestaUsuarios");
		
		encuesta = new EncuestaSoporte(); 
		
		//Cargar sedes
		if(sedesItems == null){
			//Sedes
			List<Sede> sedes = servicioGeneral.obtenerObjetos("select d from Sede d order by d.nombre");
			if(sedes.size()>0){
				sedesItems = new SelectItem[sedes.size()];
				for (int i = 0; i < sedes.size(); i++) {
					Sede dd = (Sede) sedes.get(i);
					sedesItems[i] = new SelectItem(dd.getId(), dd.getNombre());
					dd = null;
				}
				
				encuesta.setIdSede(sedes.get(0));
				
			}else{
				sedesItems = new SelectItem[0];
			}
			
		}
		
		
		//Cargar Atención
		List listaPrograma = new ArrayList<DominioDetalle>();
		listaPrograma = servicioGeneral.obtenerObjetos("select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
						+ DOMINIO_ATENCION + "' order by dd.estado");
		atencionItems = new SelectItem[listaPrograma.size()];
		for (int i = 0; i < listaPrograma.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaPrograma.get(i);
			atencionItems[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
			dd = null;
		}
		
		//Cargar Proceso
		List lista = new ArrayList<DominioDetalle>();
		lista = servicioGeneral.obtenerObjetos("select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
						+ DOMINIO_PROCESO + "' and dd.estado='A'  order by dd.descripcion");
		procesoItems = new SelectItem[lista.size()];
		for (int i = 0; i < lista.size(); i++) {
			DominioDetalle dd = (DominioDetalle) lista.get(i);
			
			procesoItems[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());			
			dd = null;			
		}
		
		
	}
	

	//**********************************************************	
	
	public SelectItem[] getProcesoItems() {
		return procesoItems;
	}


	public void setProcesoItems(SelectItem[] procesoItems) {
		this.procesoItems = procesoItems;
	}


	public void guardar() {
		
		if(encuesta!=null){
		
			try{
				Date date = new Date();
				
				encuesta.setFecha(date);
				
				if(encuesta.getProceso()==null){
					encuesta.setProceso("Extensión");	
				}
				
				servicioGeneral.guardarObjeto(encuesta);
				
				FacesContext context = FacesContext.getCurrentInstance();
				FacesMessage mensaje = new FacesMessage("La información ha sido enviada correctamente. Gracias por su colaboración");
				context.addMessage("datosGuardados", mensaje);
				
				encuesta = new EncuestaSoporte();
				
			}catch (Exception e){
				FacesContext context = FacesContext.getCurrentInstance();
				FacesMessage mensaje = new FacesMessage("La información NO ha sido enviada.");
				context.addMessage("datosGuardados", mensaje);
			}
			
	  }else{
		  FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage("La información NO ha sido enviada.");
			context.addMessage("datosGuardados", mensaje);
	  }
		
	}


	public EncuestaSoporte getEncuesta() {
		return encuesta;
	}


	public void setEncuesta(EncuestaSoporte encuesta) {
		this.encuesta = encuesta;
	}


	public SelectItem[] getAtencionItems() {
		return atencionItems;
	}


	public void setAtencionItems(SelectItem[] atencionItems) {
		this.atencionItems = atencionItems;
	}


	public SelectItem[] getModuloItems() {
		return moduloItems;
	}


	public void setModuloItems(SelectItem[] moduloItems) {
		this.moduloItems = moduloItems;
	}


	public SelectItem[] getSedesItems() {
		return sedesItems;
	}


	public void setSedesItems(SelectItem[] sedesItems) {
		this.sedesItems = sedesItems;
	}

	

}
