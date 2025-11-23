package co.edu.unal.hermes.vista.proyectos.manejadorproyecto;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.component.menuitem.MenuItem;

import co.edu.unal.hermes.modelo.ApropiacionES;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.ViabiliadES;
import co.edu.unal.hermes.vista.proyectos.ManejadorMenuFormularios;

public class ManejadorSostenibilidadProyecto extends ManejadorProyecto {

    private List<DominioDetalle> listaNivelSostenibilidad;
    private SelectItem[] nivelSostenibilidadItem;
    private List<DominioDetalle> listaNaturalezaEntidad;
    private SelectItem[] naturalezaEntidadItem;
    private NaturalezaIniciativas natIni;
    private NaturalezaIniciativas iniciativaAct;
    private ArrayList<NaturalezaIniciativas> listaIniciativas;    
    private UIComponent pqNivSos;
    private UIComponent pqContinuidad;
    private UIComponent idDescrInicia;    
    
    private ApropiacionES aproPry;
    private ApropiacionES apropAct;
    private ArrayList<ApropiacionES> listaApropiacion;
    private UIComponent idDescrAprobacion;
    
    private ViabiliadES viabPry;
    private ViabiliadES viabAct;
    private ArrayList<ViabiliadES> listaViabilidad;
    private UIComponent idDescrViabiliad;

    public ManejadorSostenibilidadProyecto() {
	super();
	cargarValoresIniciales();
    }

    @Override
    protected void cargarValoresIniciales() {
    	aproPry = new ApropiacionES();
    	viabPry = new ViabiliadES();
    	cargarListas();
    	natIni = new NaturalezaIniciativas();
    	listaIniciativas = new ArrayList<NaturalezaIniciativas>();
    	listaApropiacion = new ArrayList<ApropiacionES>();
    	listaViabilidad = new ArrayList<ViabiliadES>();
    	if (proyectoActual.getArticulacionIniciativas() != null && !proyectoActual.getArticulacionIniciativas().equals("")) {
    		String[] filIni = proyectoActual.getArticulacionIniciativas().split("&");
    		for (int i = 0; i < filIni.length; i++) {
    			NaturalezaIniciativas fsToNatIni = new NaturalezaIniciativas();
    			String[] colIni = filIni[i].split("~");
    			fsToNatIni.setNaturalezaIniciativa(colIni[0]);
    			fsToNatIni.setDescripcion(colIni[1]);
    			this.listaIniciativas.add(fsToNatIni);
    		}
    	}
    	if (proyectoActual.getViabilidad()!= null && !proyectoActual.getViabilidad().equals("")) {
    		String[] filIni = proyectoActual.getViabilidad().split("&");
    		for (int i = 0; i < filIni.length; i++) {
    			ViabiliadES fsToNatIni = new ViabiliadES();
    			String[] colIni = filIni[i].split("~");
    			fsToNatIni.setDescripcion(colIni[0]);
    			fsToNatIni.setEnlace(colIni[1]);
    			this.listaViabilidad.add(fsToNatIni);
    		}
    	}
    	if (proyectoActual.getApropiacion() != null && !proyectoActual.getApropiacion().equals("")) {
    		String[] filIni = proyectoActual.getApropiacion().split("&");
    		for (int i = 0; i < filIni.length; i++) {
    			ApropiacionES fsToNatIni = new ApropiacionES();
    			String[] colIni = filIni[i].split("~");    			
    			fsToNatIni.setDescripcionApro(colIni[0]);
    			fsToNatIni.setComunidad(colIni[1]);
    			fsToNatIni.setEnlace(colIni[2]);
    			this.listaApropiacion.add(fsToNatIni);
    		}
    	}
    }

    @SuppressWarnings({ "unchecked", "deprecation" })
    public void cargarListas() {

	listaNivelSostenibilidad = new ArrayList<DominioDetalle>();
	listaNivelSostenibilidad = servicioGeneral.obtenerObjetos("select e from DominioDetalle e where e.identificador.id = '23'");
	nivelSostenibilidadItem = new SelectItem[listaNivelSostenibilidad.size()];
	for (int i = 0; i < listaNivelSostenibilidad.size(); i++) {
	    DominioDetalle dd = (DominioDetalle) listaNivelSostenibilidad.get(i);
	    nivelSostenibilidadItem[i] = new SelectItem(dd.getDescripcion(), dd.getDescripcion());
	    dd = null;
	}

	listaNaturalezaEntidad = new ArrayList<DominioDetalle>();
	listaNaturalezaEntidad = servicioGeneral.obtenerObjetos("select e from DominioDetalle e where e.identificador.id = '26'");
	naturalezaEntidadItem = new SelectItem[listaNaturalezaEntidad.size()];
	for (int i = 0; i < listaNaturalezaEntidad.size(); i++) {
	    DominioDetalle dd = (DominioDetalle) listaNaturalezaEntidad.get(i);
	    naturalezaEntidadItem[i] = new SelectItem(dd.getDescripcion(), dd.getDescripcion());
	    dd = null;
	}

    }

    public boolean validarIniciativas() {
    	boolean val = true;

    	if (this.natIni.getDescripcion().trim().equals("") || this.natIni.getDescripcion() == null) {
    		val = false;
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Por favor ingresese la descripción de la iniciativa", "Por favor ingresese la descripción de la iniciativa");

    		mostrarMensaje(message, idDescrInicia);
    	}

    	return val;
    }

    public boolean validarCampos() {

    	boolean val = true;

    	if (this.proyectoActual.getPorqueNivelSostenibilidad().trim().equals("") || this.proyectoActual.getPorqueNivelSostenibilidad() == null) {
    		val = false;
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Por favor ingresese la razón por la cual seleccionó el nivel de sostenibilidad",
    				"Por favor ingresese la razón por la cual seleccionó el nivel de sostenibilidad");
    		mostrarMensaje(message, pqNivSos);
    	}

    	if (this.proyectoActual.getPorqueContinuidadProyecto().trim().equals("") || this.proyectoActual.getPorqueContinuidadProyecto() == null) {
    		val = false;
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Por favor ingresese la razón por la cual seleccionó la continuidad del proyecto",
    				"Por favor ingresese la razón por la cual seleccionó la continuidad del proyecto");
    		mostrarMensaje(message, pqContinuidad);
    	}

    	return val;
    }

    protected void mostrarMensaje(FacesMessage msg, UIComponent component) {
    	FacesContext context = FacesContext.getCurrentInstance();
    	if (component == null) {
    		context.addMessage(null, msg);
    	} else {
    		context.addMessage(component.getClientId(context), msg);
    	}

    }

    public void agregarIniciativa() {
    	if (validarIniciativas()) {
    		listaIniciativas.add(natIni);
    		natIni = new NaturalezaIniciativas();
    	}
    }

    public void eliminarIniciativa() {
	listaIniciativas.remove(iniciativaAct);
	iniciativaAct = new NaturalezaIniciativas();
    }
    
    public void agregarApropiacion(){
    	if (validarApropiacion()) {
			listaApropiacion.add(aproPry);
			aproPry = new ApropiacionES();
    	}
    }
    
    public void eliminarApropiacion() {    	
    	listaApropiacion.remove(apropAct);
    	apropAct = new ApropiacionES();
    }
    
    public void agregarViabilidad(){
    	if (validarViabilidad()) {
			listaViabilidad.add(viabPry);
			viabPry = new ViabiliadES();
    	}
    }
    
    public void eliminarViabilidad() {    	
    	listaViabilidad.remove(viabAct);
    	viabAct = new ViabiliadES();
    }
    
    public boolean validarApropiacion() {
    	boolean val = true;

    	if (this.aproPry.getDescripcionApro().trim().equals("") || this.aproPry.getDescripcionApro() == null) {
    		val = false;
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Por favor ingresese la descripción del trabajo", "Por favor ingresese la descripción del trabajo.");

    		mostrarMensaje(message, idDescrAprobacion);
    	}
    	if (this.aproPry.getComunidad().trim().equals("") || this.aproPry.getComunidad() == null) {
    		this.aproPry.setComunidad("No registra información");
    	}
    	if (this.aproPry.getEnlace().trim().equals("") || this.aproPry.getEnlace() == null) {
    		this.aproPry.setEnlace("No registra información");
    	}

    	return val;
    }
    
    public boolean validarViabilidad() {
    	boolean val = true;

    	if (this.viabPry.getDescripcion().trim().equals("") || this.viabPry.getDescripcion() == null) {
    		val = false;
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Por favor ingresese la descripción del trabajo", "Por favor ingresese la descripción del trabajo.");

    		mostrarMensaje(message, idDescrViabiliad);
    	}
    	
    	if (this.viabPry.getEnlace().trim().equals("") || this.viabPry.getEnlace() == null) {
    		this.viabPry.setEnlace("No registra información");
    	}
    	

    	return val;
    }

    public String toStringArticulacionIniciativas(ArrayList<NaturalezaIniciativas> ini) {
    	String artIni = "";
    	for (int i = 0; i < ini.size(); i++) {
    		NaturalezaIniciativas natur = ini.get(i);
    		artIni += natur.getNaturalezaIniciativa() + "~" + natur.getDescripcion() + "&";
    	}
    	return artIni;
    }
    
    public String toStringViabilidad(ArrayList<ViabiliadES> ini) {
    	String viabilidad = "";
    	for (int i = 0; i < ini.size(); i++) {
    		ViabiliadES viab = ini.get(i);
    		viabilidad += viab.getDescripcion() + "~" + viab.getEnlace() + "&";
    	}
    	return viabilidad;
    }
    
    public String toStringApropiacion(ArrayList<ApropiacionES> ini) {
    	String apropiacion = "";
    	for (int i = 0; i < ini.size(); i++) {
    		ApropiacionES aprob = ini.get(i);
    		apropiacion += aprob.getDescripcionApro() + "~" + aprob.getComunidad() + "~" + aprob.getEnlace()+ "&";
    	}
    	return apropiacion;
    }

    @Override
    public String atras() {
	// ///////MODIFICADO GIOVANNI
	ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
	boolean bandera = false;
	if (man.getItemProyecto() != null) {
	    MenuItem lis[] = man.getMenuItemArray();
	    if (lis != null) {
		for (int i = lis.length - 1; i >= 0; i--) {
		    if (bandera) {
			if (lis[i].isRendered()) {
			    return lis[i].getOutcome();
			}
		    }

		    if (lis[i].getOutcome().equals("irSostenibilidadProyecto")) {
			bandera = true;
		    }

		}
	    }
	}
	// ////////////////

	return "irObjetivosResultados";
    }

    @Override
    public String salir() {
	sesion.removeAttribute("proyecto");
	borrarManejadoresInsercionProyecto();
	return "misProyectos";
    }

    @Override
    public String salirGuardar() {

	if (validarCampos()) {

	    // ///////MODIFICADO GIOVANNI
	    String link = "";
	    ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
	    boolean bandera = false;
	    int pos = 0;
	    if (man.getItemProyecto() != null) {
		// NavigationMenuItem lis[] =
		// man.getItemProyecto()[0].getNavigationMenuItems();
		MenuItem lis[] = man.getMenuItemArray();
		if (lis != null) {
		    for (int i = 0; i < lis.length; i++) {
			if (bandera) {
			    if (lis[i].isRendered()) {
				sesion.removeAttribute("manejadorMenuFormularios");
				link = lis[i].getOutcome();
				break;
			    }
			}

			if (lis[i].getOutcome().equals("irSostenibilidadProyecto")) {
			    bandera = true;
			}
			if (lis[i].isRendered()) {
			    pos++;
			}
		    }
		}
	    }

	    if ((proyectoActual.getEstadoProyecto().getId()).equals("I") && (pos - 1) >= proyectoActual.getFase().intValue()) {
		proyectoActual.setFase(new Integer((proyectoActual.getFase()).intValue() + 1));
	    }

	    if (proyectoActual.getId() != null) {

	    	String artiIniString = toStringArticulacionIniciativas(listaIniciativas);
	    	System.out.println("ARTICULACION INICIATIVAS:" + artiIniString);
	    	proyectoActual.setArticulacionIniciativas(artiIniString);
	    	
	    	String viabilidadString = toStringViabilidad(listaViabilidad);
	    	System.out.println("VIABILIDAD:" + viabilidadString);
	    	proyectoActual.setViabilidad(viabilidadString);
	    	
	    	String apropiacionString = toStringApropiacion(listaApropiacion);
	    	System.out.println("APROPIACION:" + apropiacionString);
	    	proyectoActual.setApropiacion(apropiacionString);

	    	servicioProyecto.ingresarProyecto(proyectoActual);
	    	sesion.setAttribute("proyecto", proyectoActual);
	    	sesion.removeAttribute("manejadorSostenibilidadProyecto");
	    	sesion.removeAttribute("manejadorMenuFormularios");
	    	borrarManejadoresInsercionProyecto();
	    }

	    servicioProyecto.ingresarProyecto(proyectoActual);
	    sesion.removeAttribute("proyecto");
	    sesion.removeAttribute("manejadorMenuFormularios");
	    borrarManejadoresInsercionProyecto();
	    return "misProyectos";
	}

	return "";
    }

    @Override
    public String siguiente() {

    	if (validarCampos()) {

    	}

    	String link = "";
    	ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
    	boolean bandera = false;
    	int pos = 0;
    	if (man.getItemProyecto() != null) {
    		MenuItem lis[] = man.getMenuItemArray();
    		if (lis != null) {
    			for (int i = 0; i < lis.length; i++) {
    				if (bandera) {
    					if (lis[i].isRendered()) {
    						sesion.removeAttribute("manejadorMenuFormularios");

    						link = lis[i].getOutcome();
    						break;
    					}
    				}

    				if (lis[i].getOutcome().equals("irSostenibilidadProyecto")) {
    					bandera = true;
    				}
    				if (lis[i].isRendered()) {
    					pos++;
    				}
    			}
    		}
    	}
    	// ////////////////

    	if (validarCampos()) {
    		if ((proyectoActual.getEstadoProyecto().getId()).equals("I") && (pos - 1) == proyectoActual.getFase().intValue()) {
    			proyectoActual.setFase(new Integer((proyectoActual.getFase()).intValue() + 1));
    		}

    		if (proyectoActual.getId() != null) {
    			String artiIniString = toStringArticulacionIniciativas(listaIniciativas);
    			System.out.println("ARTICULACION INICIATIVAS:" + artiIniString);
    			proyectoActual.setArticulacionIniciativas(artiIniString);
    			
    			String viabilidadString = toStringViabilidad(listaViabilidad);
    	    	System.out.println("VIABILIDAD:" + viabilidadString);
    	    	proyectoActual.setViabilidad(viabilidadString);
    	    	
    	    	String apropiacionString = toStringApropiacion(listaApropiacion);
    	    	System.out.println("APROPIACION:" + apropiacionString);
    	    	proyectoActual.setApropiacion(apropiacionString);
    			
    			servicioProyecto.ingresarProyecto(proyectoActual);
    			sesion.setAttribute("proyecto", proyectoActual);

    			sesion.removeAttribute("manejadorSostenibilidadProyecto");
    			sesion.removeAttribute("manejadorMenuFormularios");
    			borrarManejadoresInsercionProyecto();
    			return link;
    		} else {
    			return "";
    		}

    	} else {
    		return "";
    	}

    }

    public List<DominioDetalle> getListaNivelSostenibilidad() {
	return listaNivelSostenibilidad;
    }

    public void setListaNivelSostenibilidad(List<DominioDetalle> listaNivelSostenibilidad) {
	this.listaNivelSostenibilidad = listaNivelSostenibilidad;
    }

    public SelectItem[] getNivelSostenibilidadItem() {
	return nivelSostenibilidadItem;
    }

    public void setNivelSostenibilidadItem(SelectItem[] nivelSostenibilidadItem) {
	this.nivelSostenibilidadItem = nivelSostenibilidadItem;
    }

    public List<DominioDetalle> getListaNaturalezaEntidad() {
	return listaNaturalezaEntidad;
    }

    public void setListaNaturalezaEntidad(List<DominioDetalle> listaNaturalezaEntidad) {
	this.listaNaturalezaEntidad = listaNaturalezaEntidad;
    }

    public SelectItem[] getNaturalezaEntidadItem() {
	return naturalezaEntidadItem;
    }

    public void setNaturalezaEntidadItem(SelectItem[] naturalezaEntidadItem) {
	this.naturalezaEntidadItem = naturalezaEntidadItem;
    }

    public NaturalezaIniciativas getNatIni() {
	return natIni;
    }

    public void setNatIni(NaturalezaIniciativas natIni) {
	this.natIni = natIni;
    }

    public ArrayList<NaturalezaIniciativas> getListaIniciativas() {
	return listaIniciativas;
    }

    public void setListaIniciativas(ArrayList<NaturalezaIniciativas> listaIniciativas) {
	this.listaIniciativas = listaIniciativas;
    }

    public NaturalezaIniciativas getIniciativaAct() {
	return iniciativaAct;
    }

    public void setIniciativaAct(NaturalezaIniciativas iniciativaAct) {
	this.iniciativaAct = iniciativaAct;
    }

    public class NaturalezaIniciativas {

	private String naturalezaIniciativa;
	private String descripcion;

	public String getDescripcion() {
	    return descripcion;
	}

	public void setDescripcion(String descripcion) {
	    this.descripcion = descripcion;
	}

	public String getNaturalezaIniciativa() {
	    return naturalezaIniciativa;
	}

	public void setNaturalezaIniciativa(String naturalezaIniciativa) {
	    this.naturalezaIniciativa = naturalezaIniciativa;
	}

    }

    public UIComponent getPqNivSos() {
	return pqNivSos;
    }

    public void setPqNivSos(UIComponent pqNivSos) {
	this.pqNivSos = pqNivSos;
    }

    public UIComponent getPqContinuidad() {
	return pqContinuidad;
    }

    public void setPqContinuidad(UIComponent pqContinuidad) {
	this.pqContinuidad = pqContinuidad;
    }

    public UIComponent getIdDescrInicia() {
        return idDescrInicia;
    }

    public void setIdDescrInicia(UIComponent idDescrInicia) {
        this.idDescrInicia = idDescrInicia;
    }

	public ApropiacionES getAproPry() {
		return aproPry;
	}

	public void setAproPry(ApropiacionES aproPry) {
		this.aproPry = aproPry;
	}

	public ApropiacionES getApropAct() {
		return apropAct;
	}

	public void setApropAct(ApropiacionES apropAct) {
		this.apropAct = apropAct;
	}

	public ArrayList<ApropiacionES> getListaApropiacion() {
		return listaApropiacion;
	}

	public void setListaApropiacion(ArrayList<ApropiacionES> listaApropiacion) {
		this.listaApropiacion = listaApropiacion;
	}

	public UIComponent getIdDescrAprobacion() {
		return idDescrAprobacion;
	}

	public void setIdDescrAprobacion(UIComponent idDescrAprobacion) {
		this.idDescrAprobacion = idDescrAprobacion;
	}

	public ViabiliadES getViabPry() {
		return viabPry;
	}

	public void setViabPry(ViabiliadES viabPry) {
		this.viabPry = viabPry;
	}

	public ViabiliadES getViabAct() {
		return viabAct;
	}

	public void setViabAct(ViabiliadES viabAct) {
		this.viabAct = viabAct;
	}

	public ArrayList<ViabiliadES> getListaViabilidad() {
		return listaViabilidad;
	}

	public void setListaViabilidad(ArrayList<ViabiliadES> listaViabilidad) {
		this.listaViabilidad = listaViabilidad;
	}

	public UIComponent getIdDescrViabiliad() {
		return idDescrViabiliad;
	}

	public void setIdDescrViabiliad(UIComponent idDescrViabiliad) {
		this.idDescrViabiliad = idDescrViabiliad;
	}
	
	

}
