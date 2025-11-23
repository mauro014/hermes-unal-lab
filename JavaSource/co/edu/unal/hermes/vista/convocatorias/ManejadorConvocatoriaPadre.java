package co.edu.unal.hermes.vista.convocatorias;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.ConvocatoriaPadreParametrizacion;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EstadoConvocatoria;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorConvocatoriaPadre extends ManejadorBase 
{
	private ConvocatoriaPadre convocatoriaPadreActual;	
	private String mensajeTransaccion = "";
	private String nivelConvocatoria = "";
	private Long nivelSedeConvocatoria ;
	private String nivelFacConvocatoria = "";
	private EstadoConvocatoria estadoConvocatoria;
	private List listaEstadosConvocatoria;
	private List listaNivelConvocatoria;
	private List listaSedeConvocatoria;
	private List listaFacultadConvocatoria;
	private SelectItem[] sedeConvocatoriaItem;
	private SelectItem[] facConvocatoriaItem;
	private Dependencia dependencia;
	private boolean panelRender[] = new boolean[5];
	private List listaCategoriaConvocatoria;
	private SelectItem[] tipoConvocatoriaItem;
	private final String DOM_TIPO_CONVOCATORIA = "TIPO_CONVOCATORIAS";
	private String mostrarPropiedadIntelectual;
	
	private List<ConvocatoriaPadreParametrizacion> listaParametrosConvPadre;
	
	public List getListaSedeConvocatoria() {
		return listaSedeConvocatoria;
	}

	public void setListaSedeConvocatoria(List listaSedeConvocatoria) {
		this.listaSedeConvocatoria = listaSedeConvocatoria;
	}

	public List getListaFacultadConvocatoria() {
		return listaFacultadConvocatoria;
	}

	public void setListaFacultadConvocatoria(List listaFacultadConvocatoria) {
		this.listaFacultadConvocatoria = listaFacultadConvocatoria;
	}

	/********************************************************************************
	CONSTRUCTOR DE LA CLASE 
	********************************************************************************/
	public ManejadorConvocatoriaPadre()
	{
		panelRender[1] = false;
		panelRender[2] = false;
		
		listaEstadosConvocatoria = new ArrayList();
		listaSedeConvocatoria = new ArrayList();
		listaFacultadConvocatoria = new ArrayList();
		
		listaEstadosConvocatoria = servicioGeneral.obtenerListaObjetos("EstadoConvocatoria");
		listaSedeConvocatoria = servicioGeneral.obtenerListaObjetos("Sede");
		
		listaNivelConvocatoria = new Vector();
		listaNivelConvocatoria.add(new SelectItem("Nacional", "Nacional"));
		listaNivelConvocatoria.add(new SelectItem("Sede", "Sede"));
		listaNivelConvocatoria.add(new SelectItem("Facultad", "Facultad"));
		
		listaCategoriaConvocatoria = new Vector();
		listaCategoriaConvocatoria.add(new SelectItem(PREGRADO,"Convocatorias del Sistema de Investigación - Pregrado"));
		listaCategoriaConvocatoria.add(new SelectItem(POSGRADO,"Convocatorias del Sistema de Investigación - Posgrado"));
		listaCategoriaConvocatoria.add(new SelectItem(PROYECTOS,"Convocatorias del Sistema de Investigación - Proyecto"));
		listaCategoriaConvocatoria.add(new SelectItem(INNOVACION,"Convocatorias del Sistema de Investigación - Innovación"));
		listaCategoriaConvocatoria.add(new SelectItem(INTERNACIONALIZACION,"Convocatorias del Sistema de Investigación - Internacionalización"));
		listaCategoriaConvocatoria.add(new SelectItem(DIFUSION,"Convocatorias del Sistema de Investigación - Difusión del conocimiento"));
		listaCategoriaConvocatoria.add(new SelectItem(PUBLICACIONES,"Convocatorias del Sistema de Investigación - Publicaciones"));
		listaCategoriaConvocatoria.add(new SelectItem(SEDES_FACULTADES,"Convocatorias de sedes o facultades"));
		listaCategoriaConvocatoria.add(new SelectItem(PROGRAMA_APOYO,"Programas de apoyo"));
		listaCategoriaConvocatoria.add(new SelectItem(OTRAS,"Otras"));
		
		List<DominioDetalle> listaTipoConvocatoria = servicioGeneral.obtenerDominioDetalle(DOM_TIPO_CONVOCATORIA);
		tipoConvocatoriaItem = crearListaItems(listaTipoConvocatoria);
		
		
		sedeConvocatoriaItem = new SelectItem[listaSedeConvocatoria.size()];
		for (int i = 0; i < listaSedeConvocatoria.size(); i++) {
			Sede s = (Sede) listaSedeConvocatoria.get(i);
			sedeConvocatoriaItem[i] = new SelectItem(s.getId(), s.getNombre());
		}
				
		facConvocatoriaItem = new SelectItem[1];
		facConvocatoriaItem[0] = new SelectItem("0", "---");
		
		
		convocatoriaPadreActual = (ConvocatoriaPadre) sesion.getAttribute("convocatoriaPadreEdicion");
		if(convocatoriaPadreActual == null)
		{
			convocatoriaPadreActual = new ConvocatoriaPadre();
			estadoConvocatoria = new EstadoConvocatoria();
			estadoConvocatoria.setId("CR");
		}
		else{
			mostrarPropiedadIntelectual = convocatoriaPadreActual.isMostrarPropiedadIntelectual() == true ? "S":"N";
			estadoConvocatoria = convocatoriaPadreActual.getEstadoConvocatoria();
			String dependenc = convocatoriaPadreActual.getDependencia().getId();
			Dependencia dependenciaBusqueda = servicioDependencia.obtenerDependencia(dependenc);
			
			if(dependenciaBusqueda.getEsSede()){
				nivelConvocatoria = "Sede";
			}else{
				if(dependenciaBusqueda.getEsFacultad()){
					nivelConvocatoria = "Facultad";
				}else{
					nivelConvocatoria = "Nacional";
				}
			}
				
			if (nivelConvocatoria.equals("Nacional")){
				panelRender[1] = false;
				panelRender[2] = false;
			}
			if (nivelConvocatoria.equals("Sede")){
				panelRender[1] = true;
				panelRender[2] = false;
				nivelSedeConvocatoria = Long.parseLong(dependenciaBusqueda.getId());
			}
			if (nivelConvocatoria.equals("Facultad")){
				panelRender[1] = true;
				panelRender[2] = true;				
				nivelSedeConvocatoria = dependenciaBusqueda.getSede().getId();
				cambiarFacConvocatoria();
				nivelFacConvocatoria = dependenciaBusqueda.getId();
			}
			
			Long tipoParam = Tipos.TIPOS_PARAM_CONV_MOV_DOCENTE_CONV_PADRE_SEDE;
			listaParametrosConvPadre = servicioModalidad.obtenerParametrosConvocatoriasPadre(convocatoriaPadreActual, tipoParam);
			
		}
			
	}
	
	public SelectItem[] getFacConvocatoriaItem() {
		return facConvocatoriaItem;
	}

	public void setFacConvocatoriaItem(SelectItem[] facConvocatoriaItem) {
		this.facConvocatoriaItem = facConvocatoriaItem;
	}

	/********************************************************************************
	DML CONVOCATORIAS PADRE 
	********************************************************************************/
	public String guardar()
	{
		if (mostrarPropiedadIntelectual.equals("S")) {
			convocatoriaPadreActual.setMostrarPropiedadIntelectual(new Boolean(true));
		} else {
			convocatoriaPadreActual.setMostrarPropiedadIntelectual(new Boolean(false));
		}
		Long idDependencia = null;
		if (nivelConvocatoria.equals("Sede")){
			idDependencia = (nivelSedeConvocatoria);
			
		}
		if (nivelConvocatoria.equals("Facultad")){
			idDependencia = Long.parseLong(nivelFacConvocatoria);
		}
		if (nivelConvocatoria.equals("Nacional")){
			idDependencia = 1L;
		}
		String dependenc = idDependencia.toString();
		dependencia= servicioDependencia.obtenerDependencia(dependenc);
			
		convocatoriaPadreActual.setDependencia(dependencia);
		mensajeTransaccion = "";
		if(convocatoriaPadreActual.getTitulo().length() > 500)
			mensajeTransaccion = "El titulo no puede contener mas de 500 caracteres";
		if(convocatoriaPadreActual.getTitulo() == null || convocatoriaPadreActual.getTitulo().equals(""))
			mensajeTransaccion = "El titulo es obligatorio";
		
		try
		{
			new Integer(convocatoriaPadreActual.getAno());
		}
		catch(Exception ex)
		{
			mensajeTransaccion = "El año de la convocatoria no es valido";
		}
		
		if(mensajeTransaccion.equals(""))
		{
			convocatoriaPadreActual.setEstadoConvocatoria(estadoConvocatoria);
			servicioGeneral.guardarObjeto(convocatoriaPadreActual);
			sesion.removeAttribute("manejadorConvocatoriaPadre");
			sesion.removeAttribute("convocatoriaPadreEdicion");
			sesion.removeAttribute("manejadorConsultaConvocatorias");
			return "consultarConvocatoriaAdmon";
		}
		else
			return "";
	}
	
	
	public void cambiarSedeConvocatoria(){
	
		if (nivelConvocatoria.equals("Nacional")){
			panelRender[1] = false;
			panelRender[2] = false;
		}
		if (nivelConvocatoria.equals("Sede")){
			panelRender[1] = true;
			panelRender[2] = false;
		}
		if (nivelConvocatoria.equals("Facultad")){
			panelRender[1] = true;
			panelRender[2] = true;
		}
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
	
	/********************************************************************************
	METODOS ACCESORES
	********************************************************************************/
	public ConvocatoriaPadre getConvocatoriaPadreActual() 
	{
		return convocatoriaPadreActual;
	}

	public void setConvocatoriaPadreActual(ConvocatoriaPadre convocatoriaPadreActual) 
	{
		this.convocatoriaPadreActual = convocatoriaPadreActual;
	}

	public String getMensajeTransaccion() 
	{
		return mensajeTransaccion;
	}

	public void setMensajeTransaccion(String mensajeTransaccion) 
	{
		this.mensajeTransaccion = mensajeTransaccion;
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

	public void setListaNivelConvocatoria(List listaNivelConvocatoria) {
		this.listaNivelConvocatoria = listaNivelConvocatoria;
	}

	public List getListaNivelConvocatoria() {
		return listaNivelConvocatoria;
	}

	public void setNivelConvocatoria(String nivelConvocatoria) {
		this.nivelConvocatoria = nivelConvocatoria;
	}

	public String getNivelConvocatoria() {
		return nivelConvocatoria;
	}

	public void setNivelSedeConvocatoria(Long nivelSedeConvocatoria) {
		this.nivelSedeConvocatoria = nivelSedeConvocatoria;
	}

	public Long getNivelSedeConvocatoria() {
		return nivelSedeConvocatoria;
	}

	public void setNivelFacConvocatoria(String nivelFacConvocatoria) {
		this.nivelFacConvocatoria = nivelFacConvocatoria;
	}

	public String getNivelFacConvocatoria() {
		return nivelFacConvocatoria;
	}

	public void setSedeConvocatoriaItem(SelectItem[] sedeConvocatoriaItem) {
		this.sedeConvocatoriaItem = sedeConvocatoriaItem;
	}

	public SelectItem[] getSedeConvocatoriaItem() {
		return sedeConvocatoriaItem;
	}

	public void setPanelRender(boolean panelRender[]) {
		this.panelRender = panelRender;
	}

	public boolean[] getPanelRender() {
		return panelRender;
	}

	public void setDependencia(Dependencia dependencia) {
		this.dependencia = dependencia;
	}

	public Dependencia getDependencia() {
		return dependencia;
	}

	public List getListaCategoriaConvocatoria()
	{
		return listaCategoriaConvocatoria;
	}

	public void setListaCategoriaConvocatoria(List listaCategoriaConvocatoria)
	{
		this.listaCategoriaConvocatoria = listaCategoriaConvocatoria;
	}

	public SelectItem[] getTipoConvocatoriaItem() {
		return tipoConvocatoriaItem;
	}

	public void setTipoConvocatoriaItem(SelectItem[] tipoConvocatoriaItem) {
		this.tipoConvocatoriaItem = tipoConvocatoriaItem;
	}

	public String getMostrarPropiedadIntelectual() {
		return mostrarPropiedadIntelectual;
	}

	public void setMostrarPropiedadIntelectual(String mostrarPropiedadIntelectual) {
		this.mostrarPropiedadIntelectual = mostrarPropiedadIntelectual;
	}

	public List<ConvocatoriaPadreParametrizacion> getListaParametrosConvPadre() {
		return listaParametrosConvPadre;
	}

	public void setListaParametrosConvPadre(List<ConvocatoriaPadreParametrizacion> listaParametrosConvPadre) {
		this.listaParametrosConvPadre = listaParametrosConvPadre;
	}
	
	
}
