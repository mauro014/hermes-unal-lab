package co.edu.unal.hermes.vista.oficinaExtension;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EstadoConvocatoria;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.TipoModalidad;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorCrearModuloECP extends ManejadorBase{
	
	private String modalidadECP;
	private String nombre;
	private String objGeneral;
	//private String cursosCerrados;
	private String claseEventoECP;
	private String subEventoECP;
	private String otroEvento;
	//protected List<SelectItem> claseEventoECPItem;
	protected SelectItem[] claseEventoECPItem;
	//protected List<SelectItem> subEventoECPItem;
	protected SelectItem[] subEventoECPItem;
	private Convocatoria modulo;
	private EstadoConvocatoria estadoConvocatoria;
	
	private boolean mostrarAbierto;
	private boolean guardarBloqueado = false;
	
	public ManejadorCrearModuloECP(){
		modalidadECP = "A";
		mostrarAbierto = true; // A
		cargarTipoEventoECP();
		System.out.println("Abierto");
		
	}
	
	public void cargarTipoModulo(){
		if(modalidadECP.equals("A")){
			mostrarAbierto = true; // A
			System.out.println("ABIERTO");			
		}
		else{
			mostrarAbierto = false; // C
			System.out.println("CERRADO");
		}
	}
	
	public void cargarTipoEventoECP(){
	    List listaClaseEventos = new ArrayList<DominioDetalle>();
	    
	    listaClaseEventos = servicioGeneral.obtenerObjetos("select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo like 'SUBMODALIDAD_ECP' ");
	 //  listaClaseEventos = servicioGeneral.obtenerObjetos("select e from DominioDetalle e where e.identificador.id = 26");
	    
	    claseEventoECPItem = new SelectItem[listaClaseEventos.size()];
	    
	    
		for (int i = 0; i < listaClaseEventos.size(); i++) {
		    DominioDetalle dd = (DominioDetalle) listaClaseEventos.get(i);
		    claseEventoECPItem[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
		    dd = null;
		}
	    
	    cargarSubEvento();
	}
	
	public void cargarSubEvento(){
		
		List listaSubEventos = new ArrayList<DominioDetalle>();
		
		String consulta = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo like 'TEVENTO' ";	    
		listaSubEventos= servicioGeneral.obtenerObjetos(consulta);
		
		subEventoECPItem = new SelectItem[listaSubEventos.size()];
	    
	    for (int i=0; i<listaSubEventos.size();i++){
	    	DominioDetalle domdet = (DominioDetalle) listaSubEventos.get(i);	    	
	    	subEventoECPItem[i] = new SelectItem(domdet.getIdentificador().getTipo(), domdet.getDescripcion());
	    	domdet = null;
	    }
	}
	
	public String atras() {
		limpiar();
		return "AdministrarModuloECP";
	}
	
	public void limpiar(){
		modalidadECP = "";
		nombre = "";
		objGeneral = "";
		//cursosCerrados = "";
		claseEventoECP = "";
		subEventoECP = "";
		otroEvento = "";
		modulo = null;
		sesion.removeAttribute("ManejadorCrearModuloECP");
	}
	
	public void guardar(){
		modulo = new Convocatoria();
		
		int anio;
		Date fecActual = new Date();		
		SimpleDateFormat formatNowYear = new SimpleDateFormat("yyyy");
		String currentYear = formatNowYear.format(fecActual);
		anio = Integer.parseInt(currentYear);
		
		personaActual = (Persona)sesion.getAttribute("persona");
		InvestigadorInterno ii = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());
		String dpn = ii.getDependencia().getId();
		
		estadoConvocatoria = new EstadoConvocatoria();
		estadoConvocatoria.setId("I");
		estadoConvocatoria.setNombre("Inactiva");
		
		
		Calendar c1 = GregorianCalendar.getInstance();
		c1.set(anio, 11, 31); 
		Date fechaFinal = c1.getTime();
		
		//Date fechaFinal = new Date(anio+1900,12,31);
		
		/*fechaFinal.setYear(fecActual.getYear());
		fechaFinal.setMonth(12);
		fechaFinal.setDate(31);*/
		
		TipoModalidad tpMod = new TipoModalidad();
		List listaTpMod = servicioGeneral.obtenerObjetos("select tp from TipoModalidad tp where tp.id = 'ECP'");
		tpMod = (TipoModalidad) listaTpMod.get(0);
		
		
		try{
			
			List lista= servicioGeneral.obtenerObjetos("select cp from ConvocatoriaPadre cp where cp.dependencia.id = "+ dpn +
					" and cp.tipoConvocatoria = 'ECP'" +
					" and cp.ano = "+ anio);
			
			if(lista.size()>0){		//SI PADRE EXISTE	
				ConvocatoriaPadre cp = (ConvocatoriaPadre) lista.get(0);								
				
				// SI MOD ES ABIERTO
				modulo.setPadre(cp);
				
				modulo.setTitulo(nombre);
				modulo.setObjetivo(objGeneral);
				modulo.setModalidadModulo(modalidadECP);
				modulo.setClaseEventoECP(claseEventoECP);
				modulo.setSubEventoECP(subEventoECP);
				modulo.setOtroEvento(otroEvento);
				
				modulo.setEstadoConvocatoria(estadoConvocatoria);
				modulo.setSede(ii.getDependencia().getSede());
				modulo.setTiempoEjecucionProyecto(12); // 1 AÑO VIGENCIA MODULO
				modulo.setDependencia(ii.getDependencia());
				modulo.setFechaInicio(fecActual);
				modulo.setFechaFinal(fechaFinal);
				
				//SI MODULO ES CERRADO
				//modulo.setCursosCerrados(cursosCerrados);//
				
				
				modulo.setTipo(tpMod);
				
				
				servicioGeneral.guardarObjeto(modulo);
				FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_INFO,"El módulo se ha creado exitosamente", ""));
				guardarBloqueado = true;
				
			}else{//PADRE NO EXISTE, TOCA CREARLO
				ConvocatoriaPadre cp = new ConvocatoriaPadre();
				
				String titulo = "ACTIVIDAD ECP "+ ii.getDependencia().getNombre().toUpperCase()+ " "+ anio;//DEPENDENCIA OF EXT
				cp.setTitulo(titulo);
				
				cp.setAno(anio+"");			
				cp.setEstadoConvocatoria(estadoConvocatoria);
				cp.setDependencia(ii.getDependencia());
				cp.setTipoConvocatoria("ECP");
				
				servicioGeneral.guardarObjeto(cp);
				
				ConvocatoriaPadre padre = new ConvocatoriaPadre();
				
				List listaPadre = servicioGeneral.obtenerObjetos("select cp from ConvocatoriaPadre cp where cp.dependencia.id = "+ dpn +
					" and cp.tipoConvocatoria = 'ECP'" +
					" and cp.ano = "+ anio);
				
				padre = (ConvocatoriaPadre)listaPadre.get(0);
				
				//GUARDA MODULO
				modulo.setPadre(padre);
	
				// SI MOD ES ABIERTO			
				modulo.setTitulo(nombre);
				modulo.setObjetivo(objGeneral);
				modulo.setModalidadModulo(modalidadECP);
				modulo.setClaseEventoECP(claseEventoECP);
				modulo.setSubEventoECP(subEventoECP);
				modulo.setOtroEvento(otroEvento);
				
				//SI MODULO ES CERRADO
				//modulo.setCursosCerrados(cursosCerrados);//
				
				modulo.setEstadoConvocatoria(estadoConvocatoria);
				modulo.setOferente(ii.getDependencia().getNombre());
				modulo.setSede(ii.getDependencia().getSede());
				modulo.setTiempoEjecucionProyecto(12);
				modulo.setFechaInicio(fecActual);
				
				modulo.setTipo(tpMod);
				
				servicioGeneral.guardarObjeto(modulo);
				FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_INFO,"El módulo se ha creado exitosamente", ""));
				guardarBloqueado = true;
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("CAUSA: "+ e.getCause());
			//NO GUARDO
		}
	}

	public String getModalidadECP() {
		return modalidadECP;
	}

	public void setModalidadECP(String modalidadECP) {
		this.modalidadECP = modalidadECP;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getObjGeneral() {
		return objGeneral;
	}

	public void setObjGeneral(String objGeneral) {
		this.objGeneral = objGeneral;
	}

	public boolean isMostrarAbierto() {
		return mostrarAbierto;
	}

	public void setMostrarAbierto(boolean mostrarAbierto) {
		this.mostrarAbierto = mostrarAbierto;
	}
/*
	public String getCursosCerrados() {
		return cursosCerrados;
	}

	public void setCursosCerrados(String cursosCerrados) {
		this.cursosCerrados = cursosCerrados;
	}*/

	public String getClaseEventoECP() {
		return claseEventoECP;
	}

	public void setClaseEventoECP(String claseEventoECP) {
		this.claseEventoECP = claseEventoECP;
	}

	public String getSubEventoECP() {
		return subEventoECP;
	}

	public void setSubEventoECP(String subEventoECP) {
		this.subEventoECP = subEventoECP;
	}
	
	public SelectItem[] getClaseEventoECPItem() {
		return claseEventoECPItem;
	}

	public void setClaseEventoECPItem(SelectItem[] claseEventoECPItem) {
		this.claseEventoECPItem = claseEventoECPItem;
	}

	public SelectItem[] getSubEventoECPItem() {
		return subEventoECPItem;
	}

	public void setSubEventoECPItem(SelectItem[] subEventoECPItem) {
		this.subEventoECPItem = subEventoECPItem;
	}

	public String getOtroEvento() {
		return otroEvento;
	}

	public void setOtroEvento(String otroEvento) {
		this.otroEvento = otroEvento;
	}

	public boolean isGuardarBloqueado() {
		return guardarBloqueado;
	}

	public void setGuardarBloqueado(boolean guardarBloqueado) {
		this.guardarBloqueado = guardarBloqueado;
	}
	
	
	
}
