package co.edu.unal.hermes.vista.convocatorias;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.faces.model.SelectItem;

import org.primefaces.component.datatable.DataTable;

import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.EstadoConvocatoria;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorConsConvocatoriasCreacion extends ManejadorBase {

	private List<ConvocatoriaPadre> listaConvocatoriasPadre;
	private List<ConvocatoriaPadre> listaConvocatoriasPadreFiltro;
    private ConvocatoriaPadre convocatoriaPadreSeleccion;
    private Convocatoria convocatoriaModalidadSeleccion;
    private DataTable tablaConvocatorias;
    private List<Convocatoria> listaHijo;
    private Convocatoria convocatoriaActual;     
    private String nombreConvocatoriaBusqueda;
	private String idConvocaPadre;
	private List<ConvocatoriaPadre> listaConvocatoriasPadreBusqueda;
	private SelectItem[] convocatoriaPadreItem;
	private String opcionesBusqueda;
	private boolean mostrarBusquedaXNombre;
	private boolean mostrarBusqeudaXDependencia;
	private ConvocatoriaPadre convocatoriaPadre;
	private boolean mostrarResultadoConvocatoriasPadre;
	private boolean mostrarModalidadesConvocatoria = false;	
	private EstadoConvocatoria estadoConvocatoria;
	private List listaEstadosConvocatoria;
	private List listaNivelConvocatoria;
	private List listaSedeConvocatoria;
	private List listaFacultadConvocatoria;
	private SelectItem[] sedeConvocatoriaItem;
	private SelectItem[] facConvocatoriaItem;
	private Dependencia dependencia;
	private String nivelConvocatoria = "";
	private Long nivelSedeConvocatoria ;
	private String nivelFacConvocatoria = "";
	private boolean mostrarNivelFac;
	private boolean mostrarNivelSed;

	public ManejadorConsConvocatoriasCreacion() {
		
		
		String consultaConvPadre = "select #id e.id, #titulo e.titulo, #ano e.ano from ConvocatoriaPadre e where e.estadoConvocatoria.id = 'CR'";
		listaConvocatoriasPadre = servicioGeneral.obtenerObjetosLimitado(ConvocatoriaPadre.class, consultaConvPadre);
		
		
		if(listaConvocatoriasPadre != null){
			if(listaConvocatoriasPadre.size() >0 ){
				mostrarResultadoConvocatoriasPadre = true;
			}
		}
		
		listaEstadosConvocatoria = new ArrayList();
		listaSedeConvocatoria = new ArrayList();
		listaFacultadConvocatoria = new ArrayList();
		
		listaEstadosConvocatoria = servicioGeneral.obtenerListaObjetos("EstadoConvocatoria");
		listaSedeConvocatoria = servicioGeneral.obtenerListaObjetos("Sede");
		
		listaNivelConvocatoria = new Vector();
		listaNivelConvocatoria.add(new SelectItem("Nacional", "Nacional"));
		listaNivelConvocatoria.add(new SelectItem("Sede", "Sede"));
		listaNivelConvocatoria.add(new SelectItem("Facultad", "Facultad"));

		
		sedeConvocatoriaItem = new SelectItem[listaSedeConvocatoria.size()];
		for (int i = 0; i < listaSedeConvocatoria.size(); i++) {
			Sede s = (Sede) listaSedeConvocatoria.get(i);
			sedeConvocatoriaItem[i] = new SelectItem(s.getId(), s.getNombre());
		}
				
		facConvocatoriaItem = new SelectItem[1];
		facConvocatoriaItem[0] = new SelectItem("0", "---");
		
		estadoConvocatoria = new EstadoConvocatoria();
		estadoConvocatoria.setId("CR");

	}
	
	public void consultarHijos (){		
		listaHijo = servicioModalidad.obtenerConvocatoriasxPadre(convocatoriaPadreSeleccion);
		mostrarModalidadesConvocatoria = true;	
	}
	
	public void cambiarSedeConvocatoria(){
		
		if (nivelConvocatoria.equals("Nacional")){
			mostrarNivelFac = false;
			mostrarNivelSed = false;
		}
		if (nivelConvocatoria.equals("Sede")){
			mostrarNivelSed = true;
			mostrarNivelFac = false;
		}
		if (nivelConvocatoria.equals("Facultad")){
			mostrarNivelSed = true;
			mostrarNivelFac = true;
		}
	}
	

	public void buscarConvocatoriaXDependencia(){
		String consultaConvPadre = "";
		
		if(mostrarNivelSed && mostrarNivelFac){
			consultaConvPadre = "select #id e.id, #titulo e.titulo, #ano e.ano from ConvocatoriaPadre e where e.estadoConvocatoria.id = '" + estadoConvocatoria.getId() + "' and e.dependencia.id = '" + nivelFacConvocatoria + "'";
		}else{
			if(mostrarNivelSed && !mostrarNivelFac){
				consultaConvPadre = "select #id e.id, #titulo e.titulo, #ano e.ano from ConvocatoriaPadre e where e.estadoConvocatoria.id = '" + estadoConvocatoria.getId() + "' and e.dependencia.id = '" + nivelSedeConvocatoria + "'";
			}else{
				if(!mostrarNivelSed && !mostrarNivelFac){
					consultaConvPadre = "select #id e.id, #titulo e.titulo, #ano e.ano from ConvocatoriaPadre e where e.estadoConvocatoria.id = '" + estadoConvocatoria.getId() + "' and e.dependencia.id = '1'";
				}
			}
			
		}
		
		listaConvocatoriasPadre = servicioGeneral.obtenerObjetosLimitado(ConvocatoriaPadre.class, consultaConvPadre);
		mostrarResultadoConvocatoriasPadre = true;
	}
	
	public void cambiarFacConvocatoria(){
		
		listaFacultadConvocatoria = servicioGeneral.obtenerObjetos("select f from Dependencia f" +
		" where f.esFacultad = 'Y' and f.sede= '" +
		nivelSedeConvocatoria +"'  order by f.nombre");

		facConvocatoriaItem = new SelectItem[listaFacultadConvocatoria.size()];
		for (int i = 0; i < listaFacultadConvocatoria.size(); i++) {
			Dependencia d = (Dependencia) listaFacultadConvocatoria.get(i);
			facConvocatoriaItem[i] = new SelectItem(d.getId(), d.getNombre());
		}
		
	}
	
	public void cargarConvocatoriaPadreXNombre(){
		String consultaConvPadre = "select #id e.id, #titulo e.titulo, #ano e.ano from ConvocatoriaPadre e where e.id = ''";
		listaConvocatoriasPadre = servicioGeneral.obtenerObjetosLimitado(ConvocatoriaPadre.class, consultaConvPadre);

		if(listaConvocatoriasPadreBusqueda != null){
			obtenerConvocatoriaPadreSeleccionada();
			if(convocatoriaPadre != null){
				listaConvocatoriasPadre.add(convocatoriaPadre);
			}
		}
		mostrarResultadoConvocatoriasPadre = true;
	}
	
	private void obtenerConvocatoriaPadreSeleccionada(){
		for(ConvocatoriaPadre cp:listaConvocatoriasPadreBusqueda){
			if(cp.getId().toString().equals(idConvocaPadre) ){
				convocatoriaPadre = cp;
			}
		}
	}
	
	public void cambiarTipoBusqueda() {
		if (opcionesBusqueda.equals("nombre")) {
			mostrarBusquedaXNombre = true;
			mostrarBusqeudaXDependencia = false;
		} else {
			mostrarBusquedaXNombre = false;
			mostrarBusqeudaXDependencia = true;
		}

	}
	
	public String agregarConvocatoria()
    {
    	ConvocatoriaPadre convocatoriaPadreMod = new ConvocatoriaPadre();
    	convocatoriaPadreMod.setId(convocatoriaPadreSeleccion.getId());
    	convocatoriaPadreMod = (ConvocatoriaPadre) servicioGeneral.obtenerObjeto(new ConvocatoriaPadre(), convocatoriaPadreMod.getId());
    	convocatoriaActual = new Convocatoria();
    	convocatoriaActual.setPadre(convocatoriaPadreMod);
    	sesion.setAttribute("convocatoriaEdicion",convocatoriaActual);
    	sesion.setAttribute("siEdicion","N");
	    sesion.removeAttribute("manejadorConsultaConvocatorias");
	    sesion.removeAttribute("manejadorInsercionConvocatorias");
		return "irSeleccionarTipoModalidad";
	}
	
	public String editarConvocatoriaModalidad(){
		convocatoriaActual = servicioModalidad.obtenerConvocatoria(convocatoriaModalidadSeleccion.getId());
		sesion.setAttribute("convocatoriaEdicion", convocatoriaActual);
		sesion.setAttribute("siEdicion","S");
		sesion.removeAttribute("manejadorConsultaConvocatorias");
	    sesion.removeAttribute("manejadorInsercionConvocatorias");
		return "editarConvocatoriaModalidades";
	}
	
	
	public String editarConvocatoriaPadre(){
		ConvocatoriaPadre convocatoriaPadreMod = new ConvocatoriaPadre();
    	convocatoriaPadreMod.setId(convocatoriaPadreSeleccion.getId());
    	convocatoriaPadreMod = (ConvocatoriaPadre) servicioGeneral.obtenerObjeto(new ConvocatoriaPadre(), convocatoriaPadreMod.getId());
    	sesion.setAttribute("convocatoriaPadreEdicion", convocatoriaPadreMod);
    	return "crearConvocatoriaPadre";
	}
	
	public void  consultarConvocatorias(){	
		consultarConvocatorias(false);
	}
	
	public void  consultarConvocatorias(boolean soloPermanentes){		
		boolean encontrado = true;
		if(nombreConvocatoriaBusqueda.length()>0){
			String sql = "from ConvocatoriaPadre where upper(titulo)"
					+ " like '%"+nombreConvocatoriaBusqueda.toUpperCase()+"%' ";
			if(soloPermanentes){
				sql += " and esPermanente = '"+ConvocatoriaPadre.PERMANENTE+"' ";
			}
			sql += "order by id desc";
			listaConvocatoriasPadreBusqueda = 
					servicioGeneral.obtenerObjetos(ConvocatoriaPadre.class,sql);
			if(listaConvocatoriasPadreBusqueda!= null 
					&& listaConvocatoriasPadreBusqueda.size() > 0){
				convocatoriaPadreItem = 
						new SelectItem[listaConvocatoriasPadreBusqueda.size()];
				for (int i = 0; i < listaConvocatoriasPadreBusqueda.size(); i++) {
					ConvocatoriaPadre con = 
						(ConvocatoriaPadre)listaConvocatoriasPadreBusqueda.get(i);
					if(i == 0) idConvocaPadre = con.getId().toString();
					convocatoriaPadreItem[i] = 
						new SelectItem(con.getId().toString(),con.getTitulo());	       
				}
			}else{
				encontrado = false;
			}
		}else{
			encontrado = false;
		}	   
		if(!encontrado){
			listaConvocatoriasPadreBusqueda = new ArrayList<ConvocatoriaPadre>();
			convocatoriaPadreItem = new SelectItem[0];
		}
	}

	public List<ConvocatoriaPadre> getListaConvocatoriasPadre()
	{
		return listaConvocatoriasPadre;
	}

	public void setListaConvocatoriasPadre(List<ConvocatoriaPadre> listaConvocatoriasPadre)
	{
		this.listaConvocatoriasPadre = listaConvocatoriasPadre;
	}

	public List<ConvocatoriaPadre> getListaConvocatoriasPadreFiltro()
	{
		return listaConvocatoriasPadreFiltro;
	}

	public void setListaConvocatoriasPadreFiltro(List<ConvocatoriaPadre> listaConvocatoriasPadreFiltro)
	{
		this.listaConvocatoriasPadreFiltro = listaConvocatoriasPadreFiltro;
	}

	public DataTable getTablaConvocatorias()
	{
		return tablaConvocatorias;
	}

	public void setTablaConvocatorias(DataTable tablaConvocatorias)
	{
		this.tablaConvocatorias = tablaConvocatorias;
	}

	public List<Convocatoria> getListaHijo()
	{
		return listaHijo;
	}

	public void setListaHijo(List<Convocatoria> listaHijo)
	{
		this.listaHijo = listaHijo;
	}

	public Convocatoria getConvocatoriaActual()
	{
		return convocatoriaActual;
	}

	public void setConvocatoriaActual(Convocatoria convocatoriaActual)
	{
		this.convocatoriaActual = convocatoriaActual;
	}

	public ConvocatoriaPadre getConvocatoriaPadreSeleccion()
	{
		return convocatoriaPadreSeleccion;
	}

	public void setConvocatoriaPadreSeleccion(ConvocatoriaPadre convocatoriaPadreSeleccion)
	{
		this.convocatoriaPadreSeleccion = convocatoriaPadreSeleccion;
	}

	public String getNombreConvocatoriaBusqueda()
	{
		return nombreConvocatoriaBusqueda;
	}

	public void setNombreConvocatoriaBusqueda(String nombreConvocatoriaBusqueda)
	{
		this.nombreConvocatoriaBusqueda = nombreConvocatoriaBusqueda;
	}

	public String getIdConvocaPadre()
	{
		return idConvocaPadre;
	}

	public void setIdConvocaPadre(String idConvocaPadre)
	{
		this.idConvocaPadre = idConvocaPadre;
	}

	public List<ConvocatoriaPadre> getListaConvocatoriasPadreBusqueda()
	{
		return listaConvocatoriasPadreBusqueda;
	}

	public void setListaConvocatoriasPadreBusqueda(List<ConvocatoriaPadre> listaConvocatoriasPadreBusqueda)
	{
		this.listaConvocatoriasPadreBusqueda = listaConvocatoriasPadreBusqueda;
	}

	public SelectItem[] getConvocatoriaPadreItem()
	{
		return convocatoriaPadreItem;
	}

	public void setConvocatoriaPadreItem(SelectItem[] convocatoriaPadreItem)
	{
		this.convocatoriaPadreItem = convocatoriaPadreItem;
	}

	public String getOpcionesBusqueda()
	{
		return opcionesBusqueda;
	}

	public void setOpcionesBusqueda(String opcionesBusqueda)
	{
		this.opcionesBusqueda = opcionesBusqueda;
	}

	public boolean isMostrarBusquedaXNombre()
	{
		return mostrarBusquedaXNombre;
	}

	public void setMostrarBusquedaXNombre(boolean mostrarBusquedaXNombre)
	{
		this.mostrarBusquedaXNombre = mostrarBusquedaXNombre;
	}

	public boolean isMostrarBusqeudaXDependencia()
	{
		return mostrarBusqeudaXDependencia;
	}

	public void setMostrarBusqeudaXDependencia(boolean mostrarBusqeudaXDependencia)
	{
		this.mostrarBusqeudaXDependencia = mostrarBusqeudaXDependencia;
	}

	public ConvocatoriaPadre getConvocatoriaPadre()
	{
		return convocatoriaPadre;
	}

	public void setConvocatoriaPadre(ConvocatoriaPadre convocatoriaPadre)
	{
		this.convocatoriaPadre = convocatoriaPadre;
	}

	public boolean isMostrarResultadoConvocatoriasPadre()
	{
		return mostrarResultadoConvocatoriasPadre;
	}

	public void setMostrarResultadoConvocatoriasPadre(boolean mostrarResultadoConvocatoriasPadre)
	{
		this.mostrarResultadoConvocatoriasPadre = mostrarResultadoConvocatoriasPadre;
	}

	public boolean isMostrarModalidadesConvocatoria()
	{
		return mostrarModalidadesConvocatoria;
	}

	public void setMostrarModalidadesConvocatoria(boolean mostrarModalidadesConvocatoria)
	{
		this.mostrarModalidadesConvocatoria = mostrarModalidadesConvocatoria;
	}

	public EstadoConvocatoria getEstadoConvocatoria()
	{
		return estadoConvocatoria;
	}

	public void setEstadoConvocatoria(EstadoConvocatoria estadoConvocatoria)
	{
		this.estadoConvocatoria = estadoConvocatoria;
	}

	public List getListaEstadosConvocatoria()
	{
		return listaEstadosConvocatoria;
	}

	public void setListaEstadosConvocatoria(List listaEstadosConvocatoria)
	{
		this.listaEstadosConvocatoria = listaEstadosConvocatoria;
	}

	public List getListaNivelConvocatoria()
	{
		return listaNivelConvocatoria;
	}

	public void setListaNivelConvocatoria(List listaNivelConvocatoria)
	{
		this.listaNivelConvocatoria = listaNivelConvocatoria;
	}

	public List getListaSedeConvocatoria()
	{
		return listaSedeConvocatoria;
	}

	public void setListaSedeConvocatoria(List listaSedeConvocatoria)
	{
		this.listaSedeConvocatoria = listaSedeConvocatoria;
	}

	public List getListaFacultadConvocatoria()
	{
		return listaFacultadConvocatoria;
	}

	public void setListaFacultadConvocatoria(List listaFacultadConvocatoria)
	{
		this.listaFacultadConvocatoria = listaFacultadConvocatoria;
	}

	public SelectItem[] getSedeConvocatoriaItem()
	{
		return sedeConvocatoriaItem;
	}

	public void setSedeConvocatoriaItem(SelectItem[] sedeConvocatoriaItem)
	{
		this.sedeConvocatoriaItem = sedeConvocatoriaItem;
	}

	public SelectItem[] getFacConvocatoriaItem()
	{
		return facConvocatoriaItem;
	}

	public void setFacConvocatoriaItem(SelectItem[] facConvocatoriaItem)
	{
		this.facConvocatoriaItem = facConvocatoriaItem;
	}

	public Dependencia getDependencia()
	{
		return dependencia;
	}

	public void setDependencia(Dependencia dependencia)
	{
		this.dependencia = dependencia;
	}

	public String getNivelConvocatoria()
	{
		return nivelConvocatoria;
	}

	public void setNivelConvocatoria(String nivelConvocatoria)
	{
		this.nivelConvocatoria = nivelConvocatoria;
	}

	public Long getNivelSedeConvocatoria()
	{
		return nivelSedeConvocatoria;
	}

	public void setNivelSedeConvocatoria(Long nivelSedeConvocatoria)
	{
		this.nivelSedeConvocatoria = nivelSedeConvocatoria;
	}

	public String getNivelFacConvocatoria()
	{
		return nivelFacConvocatoria;
	}

	public void setNivelFacConvocatoria(String nivelFacConvocatoria)
	{
		this.nivelFacConvocatoria = nivelFacConvocatoria;
	}

	public boolean isMostrarNivelFac()
	{
		return mostrarNivelFac;
	}

	public void setMostrarNivelFac(boolean mostrarNivelFac)
	{
		this.mostrarNivelFac = mostrarNivelFac;
	}

	public boolean isMostrarNivelSed()
	{
		return mostrarNivelSed;
	}

	public void setMostrarNivelSed(boolean mostrarNivelSed)
	{
		this.mostrarNivelSed = mostrarNivelSed;
	}

	public Convocatoria getConvocatoriaModalidadSeleccion()
	{
		return convocatoriaModalidadSeleccion;
	}

	public void setConvocatoriaModalidadSeleccion(Convocatoria convocatoriaModalidadSeleccion)
	{
		this.convocatoriaModalidadSeleccion = convocatoriaModalidadSeleccion;
	}
	
}